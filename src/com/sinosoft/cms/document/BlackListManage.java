package com.sinosoft.cms.document;
 
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.FMAppntBlackListSchema;
import com.sinosoft.schema.FMBlackListSchema;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: BlackListManage
 * @Description: (黑名单管理)
 * @author CongZN
 * @date 2015-1-13 下午04:36:24
 * 
 *       <p>
 *       修改历史
 *       </p>
 *       <p>
 *       序号 日期 修改人 修改原因
 *       </p>
 */
public class BlackListManage extends Page {

	private final int COLUMN_NUMBER = 3;
	
	
	/**
	 * @Title: initAddDialog
	 * @Description: TODO(合作商管理-ADD 初始化选项).
	 * @return Mapx 返回类型
	 * @author CongZN.
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initAddDialog(Mapx params) {
		DataTable dt = new QueryBuilder(
				"SELECT '全部公司','ALL' FROM DUAL UNION SELECT CodeName,CodeValue FROM zdcode WHERE codeType = 'SupplierCode' AND codeValue !='System' ")
				.executeDataTable();
		params.put("SupplierCode", HtmlUtil.dataTableToOptions(dt, "ALL"));
		return params;
	}

	/**
	 * @Title: initAddDialogEditor.
	 * @Description: TODO(合作商管理-Editor 初始化选项).
	 * @return Mapx 返回类型.
	 * @author CongZN.
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initDialogEditor(Mapx params) {
		String id = params.getString("ID");
		FMBlackListSchema pms = new FMBlackListSchema();
		pms.setID(id);
		if (pms.fill()) {

			DataTable dt = new QueryBuilder(
					"SELECT '全部公司','ALL' FROM DUAL "
							+ "UNION "
							+ "SELECT CodeName,CodeValue FROM zdcode WHERE codeType = 'SupplierCode' AND codeValue !='System' ")
					.executeDataTable();

			params.put("recognizeeName", pms.getRecognizeeName());
			params.put("SupplierCode", HtmlUtil.dataTableToOptions(dt, pms
					.getInsuredCompanySn()));
			params.put("insIDType", pms.getInsIDType());
			params.put("insIDNo", pms.getInsIDNo());
			params.put("remark", pms.getRemark());
		}
		return params;
	}
	/**
	 * @Title: initAppntDialogEditor.
	 * @Description: TODO(合作商管理-Editor 初始化选项).
	 * @return Mapx 返回类型.
	 * @author liuhongyu
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initAppntDialogEditor(Mapx params) {
		String id = params.getString("ID");
		FMAppntBlackListSchema pms = new FMAppntBlackListSchema();
		pms.setID(id);
		if (pms.fill()) {
			
			DataTable dt = new QueryBuilder(
					"SELECT '全部公司','ALL' FROM DUAL "
							+ "UNION "
							+ "SELECT CodeName,CodeValue FROM zdcode WHERE codeType = 'SupplierCode' AND codeValue !='System' ")
			.executeDataTable();
			
			params.put("applicantName", pms.getapplicantName());
			params.put("SupplierCode", HtmlUtil.dataTableToOptions(dt, pms
					.getappntCompanySn()));
			params.put("appntIDType", pms.getappntIDType());
			params.put("appntIDNo", pms.getappntIDNo());
			params.put("remark", pms.getRemark());
		}
		return params;
	}

	/**
	 * @Title: saveBlackListManage.
	 * @Description: TODO(黑名单-新增).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void saveBlackListManage() {
		String recognizeeName = $V("RecognizeeName");
		String insuredCompanySn = $V("InsuredCompanySn");
		String insIDType = $V("InsIDType");
		String insIDNo = $V("InsIDNo");
		String remark = $V("Remark");

		String insuredCompanyName = "";
		if ("ALL".equals(insuredCompanySn)) {
			insuredCompanyName = "全部公司";
		} else {
			insuredCompanyName = queryCompanyName(insuredCompanySn);
		}

		try {
			Transaction ts = new Transaction();
			FMBlackListSchema fm = new FMBlackListSchema();

			QueryBuilder query_BlackList = new QueryBuilder(
					"select InsIDNo from fmblacklist where InsIDNo = ? and insuredCompanySn = ?");
			query_BlackList.add(insIDNo);
			query_BlackList.add(insuredCompanySn);
			String result = query_BlackList.executeString();

			if (StringUtil.isNotEmpty(result)) {
				Response.setStatus(2);
				Response.setMessage("用户已存在!");
				return;
			}

			fm.setID(NoUtil.getMaxID("FMBlackID") + "");
			fm.setRecognizeeName(recognizeeName);
			fm.setInsuredCompanySn(insuredCompanySn);
			fm.setInsuredCompanyName(insuredCompanyName);
			fm.setInsIDNo(insIDNo);
			fm.setInsIDType(insIDType);
			fm.setRemark(remark);
			fm.setcreateDate(PubFun.getCurrent());
			ts.add(fm, Transaction.INSERT);

			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("保存失败! error:提交事务失败!");
			}

		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("保存失败! error:" + e.getMessage());
			logger.error("savePartnersManage 方法异常! error:" + e.getMessage(), e);
		}

	}
	/**
	 * @Title: saveAppntBlackListManage.
	 * @Description: TODO(黑名单-新增).
	 * @return void 返回类型.
	 * @author liuhongyu
	 */
	public void saveAppntBlackListManage() {
		String applicantName = $V("applicantName");
		String appntCompanySn = $V("appntCompanySn");
		String appntIDType = $V("appntIDType");
		String appntIDNo = $V("appntIDNo");
		String remark = $V("Remark");
		
		String insuredCompanyName = "";
		if ("ALL".equals(appntCompanySn)) {
			insuredCompanyName = "全部公司";
		} else {
			insuredCompanyName = queryCompanyName(appntCompanySn);
		}
		
		try {
			Transaction ts = new Transaction();
			FMAppntBlackListSchema fm = new FMAppntBlackListSchema();
			
			QueryBuilder query_BlackList = new QueryBuilder(
					"select InsIDNo from fmblacklist where InsIDNo = ? and insuredCompanySn = ?");
			query_BlackList.add(appntIDNo);
			query_BlackList.add(appntCompanySn);
			String result = query_BlackList.executeString();
			
			if (StringUtil.isNotEmpty(result)) {
				Response.setStatus(2);
				Response.setMessage("用户已存在!");
				return;
			}
			
			fm.setID(NoUtil.getMaxID("FMBlackID") + "");
			fm.setapplicantName(applicantName);
			fm.setappntCompanySn(appntCompanySn);
			fm.setappntCompanyName(insuredCompanyName);
			fm.setappntIDNo(appntIDNo);
			fm.setappntIDType(appntIDType);
			fm.setRemark(remark);
			fm.setcreateDate(PubFun.getCurrent());
			ts.add(fm, Transaction.INSERT);
			
			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("保存失败! error:提交事务失败!");
			}
			
		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("保存失败! error:" + e.getMessage());
			logger.error("savePartnersManage 方法异常! error:" + e.getMessage(), e);
		}
		
	}

	/**
	 * 获取保险公司名称.
	 * 
	 * @param p_CompanyNo
	 * @return
	 */
	public String queryCompanyName(String p_CompanyNo) {
		if (StringUtil.isEmpty(p_CompanyNo)) {
			return "全部";
		}
		QueryBuilder query_BlackList = new QueryBuilder(
				"SELECT CodeName FROM zdcode WHERE codeType = 'SupplierCode' AND codeValue = ? ");
		query_BlackList.add(p_CompanyNo);
		String result = query_BlackList.executeString();
		return result;
	}

	/**
	 * @Title: update.
	 * @Description: TODO(黑名单-编辑).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void update() {
		String id = $V("ID");
		String recognizeeName = $V("RecognizeeName");
		String insuredCompanySn = $V("InsuredCompanySn");
		String insIDType = $V("InsIDType");
		String insIDNo = $V("InsIDNo");
		String remark = $V("Remark");

		String insuredCompanyName = "";
		if ("ALL".equals(insuredCompanySn)) {
			insuredCompanyName = "全部公司";
		} else {
			insuredCompanyName = queryCompanyName(insuredCompanySn);
		}

		QueryBuilder query_BlackList = new QueryBuilder(
				"select InsIDNo from fmblacklist where InsIDNo = ? and id != ? and insuredCompanySn = ?");
		query_BlackList.add(insIDNo);
		query_BlackList.add(id);
		query_BlackList.add(insuredCompanySn);
		String result = query_BlackList.executeString();

		if (StringUtil.isNotEmpty(result)) {
			Response.setStatus(2);
			Response.setMessage("用户已存在!");
			return;
		}

		try {
			Transaction ts = new Transaction();

			FMBlackListSchema fm = new FMBlackListSchema();
			fm.setID(id);
			if (fm.fill()) {

				fm.setRecognizeeName(recognizeeName);
				fm.setInsuredCompanySn(insuredCompanySn);
				fm.setInsuredCompanyName(insuredCompanyName);
				fm.setInsIDType(insIDType);
				fm.setInsIDNo(insIDNo);
				fm.setRemark(remark);
				fm.setcreateDate(PubFun.getCurrent());
			}

			ts.add(fm, Transaction.UPDATE);

			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("修改成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("修改失败! error:提交事务失败!");
			}

		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("修改失败! error:" + e.getMessage());
			logger.error("update 方法异常! error:" + e.getMessage(), e);
		}

	}
	/**
	 * @Title: appntUpdate.
	 * @Description: TODO(黑名单-编辑).
	 * @return void 返回类型.
	 * @author liuhongyu
	 */
	public void appntUpdate() {
		String id = $V("ID");
		String applicantName = $V("applicantName");
		String appntCompanySn = $V("appntCompanySn");
		String appntIDType = $V("appntIDType");
		String appntIDNo = $V("appntIDNo");
		String remark = $V("Remark");
		
		String appntCompanyName = "";
		if ("ALL".equals(appntCompanySn)) {
			appntCompanyName = "全部公司";
		} else {
			appntCompanyName = queryCompanyName(appntCompanySn);
		}
		
		QueryBuilder query_BlackList = new QueryBuilder(
				"select appntIDNo from fmappntblacklist where appntIDNo = ? and id != ? and appntCompanySn = ?");
		query_BlackList.add(appntIDNo);
		query_BlackList.add(id);
		query_BlackList.add(appntCompanySn);
		String result = query_BlackList.executeString();
		
		if (StringUtil.isNotEmpty(result)) {
			Response.setStatus(2);
			Response.setMessage("用户已存在!");
			return;
		}
		
		try {
			Transaction ts = new Transaction();
			
			FMAppntBlackListSchema fm = new FMAppntBlackListSchema();
			fm.setID(id);
			if (fm.fill()) {
				
				fm.setapplicantName(applicantName);
				fm.setappntCompanySn(appntCompanySn);
				fm.setappntCompanyName(appntCompanyName);
				fm.setappntIDType(appntIDType);
				fm.setappntIDNo(appntIDNo);
				fm.setRemark(remark);
				fm.setcreateDate(PubFun.getCurrent());
			}
			
			ts.add(fm, Transaction.UPDATE);
			
			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("修改成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("修改失败! error:提交事务失败!");
			}
			
		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("修改失败! error:" + e.getMessage());
			logger.error("update 方法异常! error:" + e.getMessage(), e);
		}
		
	}

	/**
	 * @Title: delete.
	 * @Description: TODO(黑名单-删除).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void delete() {
		String id = $V("ID");
		try {

			Transaction ts = new Transaction();
			String[] partnersCodeArr = id.split(",");

			for (int i = 0; i < partnersCodeArr.length; i++) {
				id = partnersCodeArr[i];
				FMBlackListSchema psm = new FMBlackListSchema();
				psm.setID(id);
				ts.add(psm, Transaction.DELETE);
			}

			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("删除成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("删除失败! error:提交事务失败!");
			}
		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("删除失败! error:" + e.getMessage());
			logger.error("delete 方法异常! error:" + e.getMessage(), e);
		}

	}
	/**
	 * @Title: appntDelete.
	 * @Description: TODO(黑名单-删除).
	 * @return void 返回类型.
	 * @author liuhongyu
	 */
	public void appntDelete() {
		String id = $V("ID");
		try {
			
			Transaction ts = new Transaction();
			String[] partnersCodeArr = id.split(",");
			
			for (int i = 0; i < partnersCodeArr.length; i++) {
				id = partnersCodeArr[i];
				FMAppntBlackListSchema psm = new FMAppntBlackListSchema();
				psm.setID(id);
				ts.add(psm, Transaction.DELETE);
			}
			
			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("删除成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("删除失败! error:提交事务失败!");
			}
		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("删除失败! error:" + e.getMessage());
			logger.error("delete 方法异常! error:" + e.getMessage(), e);
		}
		
	}

	/**
	 * @Title: queryPartnersManage.
	 * @Description: TODO(黑名单-查询).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void queryBlackList(DataGridAction dga) {

		String RecognizeeName = dga.getParams().getString("RecognizeeName");
		String InsIDNo = dga.getParams().getString("InsIDNo");

		StringBuffer query_Sql = new StringBuffer("");
		query_Sql.append("select * from fmblacklist where 1=1 ");

		if (StringUtil.isNotEmpty(RecognizeeName)) {
			query_Sql.append(" and RecognizeeName like '%"
					+ RecognizeeName.trim() + "%'");
		}
		if (StringUtil.isNotEmpty(InsIDNo)) {
			query_Sql.append(" and InsIDNo like '%"
					+ InsIDNo.trim() + "%'");
		}
		query_Sql.append(" order by createDate desc ");
		QueryBuilder qb = new QueryBuilder(query_Sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		Map<String,String> tempMap = new HashMap<String, String>();
		tempMap.put("ALL", "全部证件");
		dt.decodeColumn("State", HtmlUtil.codeToMapx("YesOrNo"));
		dt.decodeColumn("InsIDType", tempMap);
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * queryAppntBlackList:(投保人黑名单数据查询). <br/>
	 *
	 * @author liuhongyu
	 * @param dga
	 */
	public void queryAppntBlackList(DataGridAction dga) {
		
		String applicantName = dga.getParams().getString("applicantName");
		String appntIDNo = dga.getParams().getString("appntIDNo");
		
		StringBuffer query_Sql = new StringBuffer("");
		query_Sql.append("select * from fmappntblacklist where 1=1 ");
		
		if (StringUtil.isNotEmpty(applicantName)) {
			query_Sql.append(" and applicantName like '%"
					+ applicantName.trim() + "%'");
		}
		if (StringUtil.isNotEmpty(appntIDNo)) {
			query_Sql.append(" and appntIDNo like '%"
					+ appntIDNo.trim() + "%'");
		}
		query_Sql.append(" order by createDate desc ");
		QueryBuilder qb = new QueryBuilder(query_Sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		Map<String,String> tempMap = new HashMap<String, String>();
		tempMap.put("ALL", "全部证件");
		dt.decodeColumn("State", HtmlUtil.codeToMapx("YesOrNo"));
		dt.decodeColumn("appntIDType", tempMap);
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * @Title: CombinationConditions.
	 * @Description: TODO(组合条件).
	 * @return String 返回类型.
	 * @author CongZN.
	 */
	public static String CombinationConditions(String[] StrArray) {
		StringBuffer sb = new StringBuffer("");
		if (StrArray.length > 0) {
			for (int i = 0; i < StrArray.length; i++) {
				sb.append("'" + StrArray[i] + "',");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * @Title: importExcel.
	 * @Description: TODO(导入Excel模板).
	 * @return String 返回类型.
	 * @author CongZN.
	 */
	public void importExcel() {
		try {

			String fileaddress = $V("fileaddress");
			int sum = 0;

			FileInputStream finput = null;
			try {
				finput = new FileInputStream(fileaddress);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入失败！请选择正确模板");
			}
			POIFSFileSystem fs = null;
			try {
				fs = new POIFSFileSystem(finput);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入失败！请选择正确模板");
			}
			HSSFWorkbook wb = null;
			try {
				wb = new HSSFWorkbook(fs);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入失败！请选择正确模板");
			}
			HSSFSheet sheet = wb.getSheetAt(0);
			try {
				finput.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入失败！请选择正确模板");
			}
			HSSFRow row = null;

			short i = 0;

			int maxRow = sheet.getLastRowNum();

			if (maxRow <= 0) {
				this.Response.setError("导入失败！导入的文件中不包含有效数据！");
				return;
			}
			if (maxRow > 1001) {
				this.Response.setError("导入失败！最大1000条记录！");
				return;
			}
			
			FMBlackListSchema fm = null;
			Transaction trans = new Transaction();
			for (i = 1; i <= maxRow; i++) {
				row = sheet.getRow(i);
				if (row.getLastCellNum() > COLUMN_NUMBER) {
					this.Response.setError("导入失败！请选择正确模板！");
					return;
				}

				String companyNo = "";
				String name = "";
				String idNo = "";
				try {

					if (row.getCell((short) 0) == null) {
						this.Response.setError("导入失败！第" + (i + 1)
								+ "行保险公司编码为空，请检查！");
						return;
					}
					
					try {
						row.getCell((short)0).setCellType(HSSFCell.CELL_TYPE_STRING);
						companyNo = row.getCell((short) 0).getStringCellValue().trim();
					
					} catch (Exception e) {
						companyNo = String.valueOf((long)row.getCell((short) 2).getNumericCellValue());
					}
					
					if (row.getCell((short) 1) == null) {
						this.Response.setError("导入失败！第" + (i + 1)
								+ "行姓名为空，请检查！");
						return;
					}
					
					try {
						row.getCell((short)1).setCellType(HSSFCell.CELL_TYPE_STRING);
						name = row.getCell((short) 1).getStringCellValue().trim();
					} catch (Exception e) {
						name = String.valueOf((long)row.getCell((short) 2).getNumericCellValue());
					}
					
					
					if (row.getCell((short) 2) == null) {
						this.Response.setError("导入失败！第" + (i + 1)
								+ "行证件号码为空，请检查！");
						return;
					}
				
					try {
						row.getCell((short)2).setCellType(HSSFCell.CELL_TYPE_STRING);
						idNo = String.valueOf(row.getCell((short) 2).getStringCellValue());
					} catch (Exception e) {
						idNo = String.valueOf((long)row.getCell((short) 2).getNumericCellValue());
					}
					idNo = idNo.replaceAll(" ", "");
					
					QueryBuilder query_BlackList = new QueryBuilder(
					"select InsIDNo from fmblacklist where InsIDNo = ?");
					query_BlackList.add(idNo);
					String result = query_BlackList.executeString();
					
					if (StringUtil.isNotEmpty(result)) {
						Response.setStatus(2);
						Response.setError("导入失败！第" + (i + 1)+ "行证件号码已存在，不能重复添加！");
						return;
					}
					
					
					String insuredCompanyName = "";
					if ("ALL".equals(companyNo)) {
						insuredCompanyName = "全部公司";
					} else {
						insuredCompanyName = queryCompanyName(companyNo);
					}

					fm = new FMBlackListSchema();
					fm.setID(NoUtil.getMaxID("FMBlackID") + "");
					fm.setRecognizeeName(name);
					fm.setInsuredCompanySn(companyNo);
					fm.setInsuredCompanyName(insuredCompanyName);
					fm.setInsIDNo(idNo);
					fm.setInsIDType("ALL");
					fm.setRemark("");
					fm.setcreateDate(PubFun.getCurrent());
					trans.add(fm, Transaction.INSERT);
				} catch (Exception e) {
					this.Response.setError("导入失败！请选择正确模板");
					logger.error(e.getMessage(), e);
					return;
				}
				sum++;
			}

			this.Response.setStatus(1);
			this.Response.setMessage("操作成功！共导入" + sum + " 条记录！");
			this.Response.put("Count", sum);
			
			trans.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.Response.setError("导入异常！请选择正确模板！");
		}
	}
	/**
	 * @Title: AppntImportExcel.
	 * @Description: TODO(导入Excel模板).
	 * @return String 返回类型.
	 * @author CongZN.
	 */
	public void AppntImportExcel() {
		try {
			
			String fileaddress = $V("fileaddress");
			int sum = 0;
			
			FileInputStream finput = null;
			try {
				finput = new FileInputStream(fileaddress);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入失败！请选择正确模板");
			}
			POIFSFileSystem fs = null;
			try {
				fs = new POIFSFileSystem(finput);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入失败！请选择正确模板");
			}
			HSSFWorkbook wb = null;
			try {
				wb = new HSSFWorkbook(fs);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入失败！请选择正确模板");
			}
			HSSFSheet sheet = wb.getSheetAt(0);
			try {
				finput.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入失败！请选择正确模板");
			}
			HSSFRow row = null;
			
			short i = 0;
			
			int maxRow = sheet.getLastRowNum();
			
			if (maxRow <= 0) {
				this.Response.setError("导入失败！导入的文件中不包含有效数据！");
				return;
			}
			if (maxRow > 1001) {
				this.Response.setError("导入失败！最大1000条记录！");
				return;
			}
			
			FMAppntBlackListSchema fm = null;
			Transaction trans = new Transaction();
			for (i = 1; i <= maxRow; i++) {
				row = sheet.getRow(i);
				if (row.getLastCellNum() > COLUMN_NUMBER) {
					this.Response.setError("导入失败！请选择正确模板！");
					return;
				}
				
				String companyNo = "";
				String name = "";
				String idNo = "";
				try {
					
					if (row.getCell((short) 0) == null) {
						this.Response.setError("导入失败！第" + (i + 1)
								+ "行保险公司编码为空，请检查！");
						return;
					}
					
					try {
						row.getCell((short)0).setCellType(HSSFCell.CELL_TYPE_STRING);
						companyNo = row.getCell((short) 0).getStringCellValue().trim();
						
					} catch (Exception e) {
						companyNo = String.valueOf((long)row.getCell((short) 2).getNumericCellValue());
					}
					
					if (row.getCell((short) 1) == null) {
						this.Response.setError("导入失败！第" + (i + 1)
								+ "行姓名为空，请检查！");
						return;
					}
					
					try {
						row.getCell((short)1).setCellType(HSSFCell.CELL_TYPE_STRING);
						name = row.getCell((short) 1).getStringCellValue().trim();
					} catch (Exception e) {
						name = String.valueOf((long)row.getCell((short) 2).getNumericCellValue());
					}
					
					
					if (row.getCell((short) 2) == null) {
						this.Response.setError("导入失败！第" + (i + 1)
								+ "行证件号码为空，请检查！");
						return;
					}
					
					try {
						row.getCell((short)2).setCellType(HSSFCell.CELL_TYPE_STRING);
						idNo = String.valueOf(row.getCell((short) 2).getStringCellValue());
					} catch (Exception e) {
						idNo = String.valueOf((long)row.getCell((short) 2).getNumericCellValue());
					}
					idNo = idNo.replaceAll(" ", "");
					
					QueryBuilder query_BlackList = new QueryBuilder(
							"select appntIDNo from fmappntblacklist where appntIDNo = ?");
					query_BlackList.add(idNo);
					String result = query_BlackList.executeString();
					
					if (StringUtil.isNotEmpty(result)) {
						Response.setStatus(2);
						Response.setError("导入失败！第" + (i + 1)+ "行证件号码已存在，不能重复添加！");
						return;
					}
					
					
					String appntCompanyName = "";
					if ("ALL".equals(companyNo)) {
						appntCompanyName = "全部公司";
					} else {
						appntCompanyName = queryCompanyName(companyNo);
					}
					
					fm = new FMAppntBlackListSchema();
					fm.setID(NoUtil.getMaxID("FMBlackID") + "");
					fm.setapplicantName(name);
					fm.setappntCompanySn(companyNo);
					fm.setappntCompanyName(appntCompanyName);
					fm.setappntIDNo(idNo);
					fm.setappntIDType("ALL");
					fm.setRemark("");
					fm.setcreateDate(PubFun.getCurrent());
					trans.add(fm, Transaction.INSERT);
				} catch (Exception e) {
					this.Response.setError("导入失败！请选择正确模板");
					logger.error(e.getMessage(), e);
					return;
				}
				sum++;
			}
			
			this.Response.setStatus(1);
			this.Response.setMessage("操作成功！共导入" + sum + " 条记录！");
			this.Response.put("Count", sum);
			
			trans.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.Response.setError("导入异常！请选择正确模板！");
		}
	}
}
