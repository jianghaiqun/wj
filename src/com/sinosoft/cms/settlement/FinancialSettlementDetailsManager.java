package com.sinosoft.cms.settlement;

import cn.com.sinosoft.util.CommonUtil;
import com.sinosoft.cms.plan.statistics.FieldNameMatchable;
import com.sinosoft.cms.plan.statistics.FieldsNameMatcher;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.financialsettlementdetailsSchema;
import com.sinosoft.schema.financialsuredataSUMSchema;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 财务结算明细查询。 Created by dongsheng on 2017/5/26.
 */
public class FinancialSettlementDetailsManager extends Page implements FieldNameMatchable {

	private final static Map<String, String> dataInfoMap;

	static {
		dataInfoMap = new HashMap<String, String>();
		QueryBuilder qb = new QueryBuilder("select distinct innercode,name from datainfo");
		DataTable dataRows = qb.executeDataTable();
		for (DataRow dataRow : dataRows) {
			dataInfoMap.put(dataRow.getString(0), dataRow.getString(1));
		}
	}

	public static Mapx<String, String> init(Mapx<String, String> params) {

		Date now = new Date();
		params.put("insureStartDate", DateUtil.getCurrentDate());
		params.put("insureEndDate", DateUtil.getCurrentDate());
		params.put("svalidateBeforeDate", DateUtil.getCurrentDate());
		params.put("evalidateAfterDate", DateUtil.getCurrentDate());
		params.put("invoiceStartDate", DateUtil.getCurrentDate());
		params.put("invoiceEndDate", DateUtil.getCurrentDate());
		params.put("invoiceDate", DateUtil.getCurrentDate());
		params.put("startDate", DateUtil.getCurrentDate());
		params.put("endDate", DateUtil.getCurrentDate());
		params.put("batchNumber",DateUtil.getCurrentDateTime() + "-" + RandomUtils.nextInt(1000) );

		QueryBuilder qb = new QueryBuilder();
		StringBuilder sb = new StringBuilder(
				" select Memo `CodeName`, BranchInnerCode `CodeValue` from zdbranch ");
		sb.append(" where Memo is not null ");

		String branchCode = User.getBranchInnerCode();
		if (!"0001".equals(branchCode)) {
			sb.append(" and BranchInnerCode like ?");
			qb.add(branchCode);
		}
		qb.setSQL(sb.toString());
		params.put("branchSelect", HtmlUtil.queryToOptions(qb, false));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {

		String insureStartDate = dga.getParam("insureStartDate");
		String insureEndDate = dga.getParam("insureEndDate");
		String svalidateBeforeDate = dga.getParam("svalidateBeforeDate");
		String evalidateAfterDate = dga.getParam("evalidateAfterDate");
		String insuranceType = dga.getParam("insuranceType");
		String branchCode = dga.getParam("branchCode");
		String companyName = dga.getParam("companyName");
		String companyType = dga.getParam("companyType");
		String policyNo = dga.getParam("policyNo");
		String invoiceStartDate = dga.getParam("invoiceStartDate");
		String invoiceEndDate = dga.getParam("invoiceEndDate");
		QueryBuilder qb = new QueryBuilder();
		StringBuilder sb = new StringBuilder("select batchnumber,policyno,insurancetype,insurancesubtype,if(state = 0,'未确认','确认') as state,")
				.append(" channel,subchannel,channeltype,insuredate,svalidate,evalidate,appstatus,premium1st,premium2nd,")
				.append(" feeratio1st,feeratio2nd,fee1st,fee2nd,premiumbatchnumber,feebatchnumber,insurancecompanyname,")
				.append(" insurancecompanytype,invoicedate,branchcode,modifieddatetime,createddatetime,createduser")
				.append(" from financialsettlementdetails f")
				.append(" where 1=1");
		if (StringUtils.isNotEmpty(companyName)) {
			sb.append(" and FIND_IN_SET(insurancecompanyname,?)");
			qb.add(companyName);
		}
		if (StringUtils.isNotEmpty(companyType)) {
			sb.append(" and FIND_IN_SET(insurancecompanytype,?)");
			qb.add(companyType);
		}
		if (StringUtils.isNotEmpty(policyNo)) {
			sb.append(" and FIND_IN_SET(policyno,?)");
			qb.add(policyNo);
		}

		if (StringUtils.isNotEmpty(insureStartDate)) {
			sb.append(" and Date(f.insuredate) >= ?");
			qb.add(insureStartDate);
		}
		if (StringUtils.isNotEmpty(insureEndDate)) {
			sb.append(" and Date(f.insuredate) <= ?");
			qb.add(insureEndDate);
		}
		if (StringUtils.isNotEmpty(svalidateBeforeDate)) {
			sb.append(" and Date(f.svalidate) >= ?");
			qb.add(svalidateBeforeDate);
		}
		if (StringUtils.isNotEmpty(evalidateAfterDate)) {
			sb.append(" and Date(f.evalidate) <= ?");
			qb.add(evalidateAfterDate);
		}
		if (StringUtils.isNotEmpty(insuranceType)) {
			sb.append(" and (FIND_IN_SET(insurancetype,?) or FIND_IN_SET(insurancesubtype,?))");
			qb.add(insuranceType);
			qb.add(insuranceType);
		}
		if (StringUtils.isNotEmpty(branchCode)) {
			sb.append(" and FIND_IN_SET(branchcode,?)");
			qb.add(branchCode);
		}
		if (StringUtils.isNotEmpty(invoiceStartDate)) {
			sb.append(" and Date(invoicedate) >= ?\n");
			qb.add(invoiceStartDate);
		}
		if (StringUtils.isNotEmpty(invoiceEndDate)) {
			sb.append(" and Date(invoicedate) <= ?\n");
			qb.add(invoiceEndDate);
		}
		String sql = sb.toString();
		// System.out.println(sql);
		qb.setSQL(sql);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeDateColumn("insuredate", "yyyy年MM月dd日");
		dt.decodeDateColumn("svalidate", "yyyy年MM月dd日");
		dt.decodeDateColumn("evalidate", "yyyy年MM月dd日");
		dt.decodeDateColumn("invoicedate", "yyyy年MM月dd日");
		dt.decodeColumn("insurancetype", dataInfoMap);
		dt.decodeColumn("insurancesubtype", dataInfoMap);
		dga.bindData(dt);
	}

	public static void dg2DataBind(DataGridAction dga) {

		String invoiceStartDate = dga.getParam("invoiceStartDate");
		String invoiceEndDate = dga.getParam("invoiceEndDate");
		String companyName = dga.getParam("companyName");
		String branchCode = dga.getParam("branchCode");
		dga.bindData(createStatisticsDataTable(invoiceStartDate, invoiceEndDate, companyName, branchCode));
	}

	private static DataTable createStatisticsDataTable(String invoiceStartDate, String invoiceEndDate, String companyName,
			String branchCode) {

		QueryBuilder qb = new QueryBuilder();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT")
				.append("  insurancecompanyname,")
				.append("  channel,")
				.append("  IFNULL(SUM(premium1st + premium2nd), 0) sump, ")
				.append("  IFNULL(SUM(fee1st + fee2nd), 0)         sumf, ")
				.append("  IFNULL(sum(coupondiscount), 0) sumcd,  ")
				.append("  IFNULL(sum(activitydiscount), 0) sumad,  ")
				.append("  IFNULL(sum(pointdiscount), 0) sumpd,  ")
				.append("  IFNULL(sum(totaldiscount), 0) sumtd  ")
				.append("FROM")
				.append("  financialsettlementdetails where FIND_IN_SET(branchcode,?)");
			qb.add(branchCode);
		if (StringUtils.isNotEmpty(invoiceStartDate)) {
			sb.append(" and Date(invoicedate) >= ?\n");
			qb.add(invoiceStartDate);
		}
		if (StringUtils.isNotEmpty(invoiceEndDate)) {
			sb.append(" and Date(invoicedate) <= ?\n");
			qb.add(invoiceEndDate);
		}
		if (StringUtils.isNotEmpty(companyName)) {
			sb.append(" and FIND_IN_SET(insurancecompanyname,?)\n");
			qb.add(companyName);
		}
		sb.append("GROUP BY")
				.append("  insurancecompanyname,")
				.append("  channel;");
		String sql = sb.toString();
		// System.out.println(sql);
		qb.setSQL(sql);
		DataTable dt = qb.executeDataTable();

		return dt;
	}

	public static void dgRSXDataBind(DataGridAction dga) {

		String invoiceStartDate = dga.getParam("invoiceStartDate");
		String invoiceEndDate = dga.getParam("invoiceEndDate");
		String companyName = dga.getParam("companyName");
		String branchCode = dga.getParam("branchCode");
		String coverage = dga.getParam("coverage");

		FinancialSettlementStatisticsDataTable dtHandler = new FinancialSettlementStatisticsDataTable();
		DataTable dataTable = dtHandler.queryRSX(invoiceStartDate, invoiceEndDate, companyName, branchCode);
		dataTable = dtHandler.calculateSumRSX(dataTable);
		dga.bindData(dataTable);
	}

	public static void dgCXDataBind(DataGridAction dga) {

		String invoiceStartDate = dga.getParam("invoiceStartDate");
		String invoiceEndDate = dga.getParam("invoiceEndDate");
		String companyName = dga.getParam("companyName");
		String branchCode = dga.getParam("branchCode");
		String coverage = dga.getParam("coverage");

		FinancialSettlementStatisticsDataTable dtHandler = new FinancialSettlementStatisticsDataTable();
		DataTable dataTable = dtHandler.queryCX(invoiceStartDate, invoiceEndDate, companyName, branchCode);
		dataTable = dtHandler.calculateSumCX(dataTable);
		dga.bindData(dataTable);
	}

	/**
	 * 机构（分子公司）
	 *
	 * @param ta
	 */
	public static void branchTreeDataBind(TreeAction ta) {

		QueryBuilder qb = new QueryBuilder();
		StringBuilder sb = new StringBuilder(
				" select BranchInnerCode `ID`,ParentInnerCode `ParentID`,Memo `Name` from zdbranch ");
		sb.append(" where Memo is not null ");

		String branchCode = User.getBranchInnerCode();
		if (!"0001".equals(branchCode)) {
			sb.append(" and BranchInnerCode like ?");
			qb.add(branchCode);
		}
		qb.setSQL(sb.toString());
		DataTable dt = qb.executeDataTable();
		ta.setRootText("机构列表");
		ta.setIdentifierColumnName("ID");
		ta.setBranchIcon("Icons/icon025a1.gif");
		ta.setLeafIcon("Icons/icon025a1.gif");
		ta.bindData(dt);
	}

	public void delete() {

		String branchCode = User.getBranchInnerCode();
		final String batchNumber = $V("batchNumber");
		//判断数据是否已确认
		int checkCount = 0;
		try {
			checkCount = new QueryBuilder("select count(*) from financialsettlementdetails where state = '1' and FIND_IN_SET(batchnumber,'" + batchNumber + "')").executeInt();
		} catch (Exception e) {
			e.printStackTrace();
			Response.setMessage("数据库查询错误，请检查填入的值");
		}
		if (checkCount != 0) {
			Response.setMessage("数据已确定，不可删除");
			return;
		}
		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder();
		StringBuilder sb = new StringBuilder("delete from financialsettlementdetails where 1=1 ");
		if (!"0001".equals(branchCode)) {
			sb.append(" and branchcode = ? ");
			qb.add(branchCode);
		}
		sb.append("and  batchnumber = ?");
		qb.add(batchNumber);
		qb.setSQL(sb.toString());
		trans.add(qb);
		
		//删除总和表对应批次号
		QueryBuilder qb2 = new QueryBuilder();
		StringBuilder sb2 = new StringBuilder("delete from financialsuredatasum where 1=1 ");
		sb2.append("and  batchnumber = ?");
		qb2.add(batchNumber);
		qb2.setSQL(sb2.toString());
		trans.add(qb2);
		
		//删除申请记录表对应批次号
		QueryBuilder qb3 = new QueryBuilder();
		StringBuilder sb3 = new StringBuilder("delete from authorityinfo where 1=1 ");
		sb3.append("and  batchnumber = ?");
		qb3.add(batchNumber);
		qb3.setSQL(sb3.toString());
		trans.add(qb3);
		
		try {
			trans.commit();
			Response.setMessage("成功删除数据！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setError("删除数据发生错误！");
		}

	}

	public void excelImport() {

		final String userName = User.getUserName();
		final String branchCode = $V("branchCode");
		final String invoiceDate = $V("invoiceDate");
		final String filePath = $V("filePath");
		final String batchNumber = $V("batchNumber");
		int checkCount = 0;
		try {
			checkCount = new QueryBuilder("select count(*) from financialsettlementdetails where batchNumber = '" + batchNumber + "' and state = '1'").executeInt();			
		} catch (Exception e) {
			e.printStackTrace();
			Response.setError("数据库查询错误，请检查填入的值");
		}
		if (checkCount != 0) {
			Response.setError("该批次号已确认,不可导入，如需导入，请提起授权申请");
			return;
		}
		if (StringUtils.isAnyEmpty(branchCode, invoiceDate, filePath, batchNumber)) {
			Response.setError("请确认数据的必填项。");
			return;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			Response.setError("未找到您上传的文件。");
			return;
		}
		LongTimeTask ltt = LongTimeTask.getInstanceByType("financial_settlement_details");
		if (ltt != null) {
			Response.setError("后台正在执行一个导入任务。");
		} else {
			ltt = new LongTimeTask() {

				@Override
				public void execute() {

					setCurrentInfo("正在加载文件...");
					setPercent(20);
					financialsettlementdetailsSchema defaultSchema = new financialsettlementdetailsSchema();
					defaultSchema.setbatchnumber(batchNumber);
					defaultSchema.setbranchcode(branchCode);
					defaultSchema.setinvoicedate(DateUtil.parse(invoiceDate));
					defaultSchema.setcreateduser(userName);
					Date date = new Date();
					defaultSchema.setcreateddatetime(date);
					defaultSchema.setmodifieddatetime(date);
					setCurrentInfo("正在初始化默认数据...");
					setPercent(30);
					OPCPackage p = null;
					try {
						p = OPCPackage.open(filePath, PackageAccess.READ);
						SheetToDatabase sheetToDatabase = new SheetToDatabase(5, defaultSchema);
						ExcelBigSheetProcessor processor = new ExcelBigSheetProcessor(p, sheetToDatabase);
						setCurrentInfo("正在初始化默认数据.....");
						setPercent(50);
						processor.process();

						setCurrentInfo("正在保存数据...");
						setPercent(100);
					} catch (Exception e) {
						logger.error("数据保存错误！" + e.getMessage(), e);
						this.addError(e.getMessage());
						setCurrentInfo("数据操作发生错误：" + e.getMessage() + "\n正在回滚数据");
						setPercent(80);
						new QueryBuilder("delete from financialsettlementdetails where batchnumber=?", batchNumber)
								.executeNoQuery();
						setCurrentInfo("数据回滚完毕");
						setPercent(100);
						Response.setError(e.getMessage());
					} finally {
						if (p != null) {
							try {
								p.close();
							} catch (IOException e) {
								logger.error("OPCPackage流关闭错误。" + e.getMessage(), e);
								Response.setError(e.getMessage());
								this.addError(e.getMessage());
							}

						}
					}
				}
			};
			ltt.start();
			ltt.setType("financial_settlement_details");
			$S("TaskID", "" + ltt.getTaskID());
		}
	}

	public static void fileDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setCharacterEncoding(Constant.GlobalCharset);
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=Excel_"
				+ com.sinosoft.framework.utility.DateUtil.getCurrentDateTime("yyyyMMddHHmmss") + ".xlsx");

		OutputStream os = response.getOutputStream();
		String invoiceStartDate = request.getParameter("downloadFile_invoiceStartDate");
		String invoiceEndDate = request.getParameter("downloadFile_invoiceEndDate");
		String companyName = request.getParameter("downloadFile_companyName");
		String branchCode = request.getParameter("downloadFile_branchCode");
		FinancialSettlementStatisticsDataTable dtHandler = new FinancialSettlementStatisticsDataTable();
		DataTable dataTableRSX = dtHandler.queryRSX(invoiceStartDate, invoiceEndDate, companyName, branchCode);
		dataTableRSX = dtHandler.calculateSumRSX(dataTableRSX);
		DataTable dataTableCX = dtHandler.queryCX(invoiceStartDate, invoiceEndDate, companyName, branchCode);
		dataTableCX = dtHandler.calculateSumCX(dataTableCX);
		FinancialSettlementStatisticsExport export = new FinancialSettlementStatisticsExport(os);
		FinancialSettlementStatisticsExport.SheetBean rsxBean = new FinancialSettlementStatisticsExport.SheetBean("人身险公司业务");
		rsxBean.setDataTable(dataTableRSX);
		FinancialSettlementStatisticsExport.SheetBean cxBean = new FinancialSettlementStatisticsExport.SheetBean("产险公司业务");
		cxBean.setDataTable(dataTableCX);
		export.exportExcel(rsxBean, cxBean);
		os.flush();
		os.close();
		response.flushBuffer();
	}

	public void refreshBatchNumber(){
		String s = DateUtil.getCurrentDateTime() + "-" + RandomUtils.nextInt(1000);
		Response.put("batchNumber",s);
	}


	@Override
	public FieldsNameMatcher fieldNameMatch(FieldsNameMatcher matcher) {
		return matcher.setStartDatetime("invoiceStartDate")
				.setEndDatetime("invoiceEndDate");
	}
	
	public void orderCount(){
		String branchCode = $V("branchCode");
		String invoiceStartDate = $V("invoiceStartDate");
		String invoiceEndDate = $V("invoiceEndDate");
		
		String sql1 = "select SUM(fee1st)+SUM(fee2nd)as typea from financialsettlementdetails where channeltype = '线下' ";
		
		String sql2 = "select SUM(fee1st)+SUM(fee2nd)as typea from financialsettlementdetails where channeltype != '线下' ";
		
		String sql3 = "select SUM(fee1st)+SUM(fee2nd)as typea from financialsettlementdetails where 1=1 ";
		
		if (StringUtil.isNotEmpty(branchCode)) {
			sql1 = sql1 + " and FIND_IN_SET(branchcode,'" + branchCode + "')";
			sql2 = sql2 + " and FIND_IN_SET(branchcode,'" + branchCode + "')";
			sql3 = sql3 + " and FIND_IN_SET(branchcode,'" + branchCode + "')";
		}
		if (StringUtil.isNotEmpty(invoiceStartDate)) {
			sql1 = sql1 + " and Date(invoicedate) >= '" + invoiceStartDate + "'";
			sql2 = sql2 + " and Date(invoicedate) >= '" + invoiceStartDate + "'";
			sql3 = sql3 + " and Date(invoicedate) >= '" + invoiceStartDate + "'";
		}
		if (StringUtil.isNotEmpty(invoiceEndDate)) {
			sql1 = sql1 + " and Date(invoicedate) <= '" + invoiceEndDate + "'";
			sql2 = sql2 + " and Date(invoicedate) <= '" + invoiceEndDate + "'";
			sql3 = sql3 + " and Date(invoicedate) <= '" + invoiceEndDate + "'";
		}
		DataTable dt1 = new QueryBuilder(sql1).executeDataTable();
		DataTable dt2 = new QueryBuilder(sql2).executeDataTable();
		DataTable dt3 = new QueryBuilder(sql3).executeDataTable();
		
		String a = dt1.getString(0, 0);
		String b = dt2.getString(0, 0);
		String c = dt3.getString(0, 0);
		Response.setMessage(a + "&" + b + "&" + c);

	}
	
	public void makeSure(){
		String sureBatchNumber = $V("sureBatchNumber");
		//判断是否已确认
		int checkCount = 0;
		try {
			checkCount = new QueryBuilder("select count(*) from financialsettlementdetails where state = '1' and FIND_IN_SET(batchnumber,'" + sureBatchNumber + "')").executeInt();			
		} catch (Exception e) {
			e.printStackTrace();
			Response.setMessage("数据库查询错误，请检查填入的值");
		}
		if (checkCount != 0) {
			Response.setMessage("数据已确定，无需再次确认");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("UPDATE  financialsettlementdetails  SET state = '1' where FIND_IN_SET(batchnumber,'" + sureBatchNumber + "')"));
		String SUM = "select SUM(premium1st) as premium1st,SUM(premium2nd) as premium2nd,SUM(fee1st) as fee1st,SUM(fee2nd) as fee2nd,batchNumber,invoicedate,insurancetype,insurancesubtype,branchcode "
				+ "from financialsettlementdetails where FIND_IN_SET(batchnumber,'" + sureBatchNumber + "') group by batchnumber,insurancesubtype";
		DataTable SUMdt = new QueryBuilder(SUM).executeDataTable();
		for (int i = 0; i < SUMdt.getRowCount(); i++) {
			String premium1stSUM = SUMdt.getString(i, "premium1st");
			String premium2ndSUM = SUMdt.getString(i, "premium2nd");
			String fee1stSUM = SUMdt.getString(i, "fee1st");
			String fee2ndSUM = SUMdt.getString(i, "fee2nd");
			String batchNumber = SUMdt.getString(i, "batchNumber");
			String invoicedate = SUMdt.getString(i, "invoicedate");
			String insurancetype = SUMdt.getString(i, "insurancetype");
			String insurancesubtype = SUMdt.getString(i, "insurancesubtype");
			String branchcode = SUMdt.getString(i, "branchcode");
			financialsuredataSUMSchema schema = new financialsuredataSUMSchema();
			schema.setID(CommonUtil.getUUID());
			schema.setbatchNumber(batchNumber);
			schema.setbranchcode(branchcode);
			schema.setinvoicedate(DateUtil.parse(invoicedate));
			schema.setinsurancetype(insurancetype);
			schema.setinsurancesubtype(insurancesubtype);
			schema.setstate("1");
			schema.setpremium1stSUM(premium1stSUM);
			schema.setpremium2ndSUM(premium2ndSUM);
			schema.setfee1stSUM(fee1stSUM);
			schema.setfee2ndSUM(fee2ndSUM);
			schema.setcreatedate(new Date());
			schema.setcreateuser(User.getUserName());
			trans.add(schema, Transaction.INSERT);
		}
		try {
			trans.commit();
			Response.setMessage("确认数据成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setError("删除数据发生错误！");
		}		
	}
	public void dgDataBindApplication (DataGridAction dga){
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String branchCode = User.getBranchInnerCode();
		QueryBuilder qb = new QueryBuilder();
		StringBuilder sb = new StringBuilder("select IF (a.state = 0,'待审核',IF(a.state = -1,'未通过','通过')) AS state,ID,batchnumber,branchCode,reason,createdate,createuser,modifydate,modifyuser from authorityinfo a where 1=1");
		if (StringUtils.isNotEmpty(branchCode)) {
			sb.append(" and branchCode = ?");
			qb.add(branchCode);
		}
		if (StringUtils.isNotEmpty(startDate)) {
			sb.append(" and Date(createdate) >= ?");
			qb.add(startDate);
		}
		if (StringUtils.isNotEmpty(endDate)) {
			sb.append(" and Date(createdate) <= ?");
			qb.add(endDate);
		}
		String sql = sb.toString();
		qb.setSQL(sql);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);	
	}
	
	public void dgDataBindApproval (DataGridAction dga){
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		QueryBuilder qb = new QueryBuilder();
		StringBuilder sb = new StringBuilder("select IF (a.state = 0,'待审核',IF(a.state = -1,'未通过','通过')) AS state,ID,batchnumber,branchCode,reason,createdate,createuser,modifydate,modifyuser from authorityinfo a where 1=1");
		if (StringUtils.isNotEmpty(startDate)) {
			sb.append(" and Date(createdate) >= ?");
			qb.add(startDate);
		}
		if (StringUtils.isNotEmpty(endDate)) {
			sb.append(" and Date(createdate) <= ?");
			qb.add(endDate);
		}
		String sql = sb.toString();
		qb.setSQL(sql);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);		
	}
	
	public void application(){
		String applicationBatchNumber = $V("applicationBatchNumber");
		String reason = $V("reason");
		String ID = CommonUtil.getUUID();
		String branchCode = User.getBranchInnerCode();
		String state = "0";
		String createdate = DateUtil.getCurrentDate();
		String createuser = User.getUserName();
		
		//判断批次号是否正确
		int checkCount = 0;
		try {			
			checkCount = new QueryBuilder("select count(*) from financialsettlementdetails where BatchNumber = '" + applicationBatchNumber + "'").executeInt();
		} catch (Exception e) {
			e.printStackTrace();
			Response.setError("数据库查询错误，请检查填入的值");
		}
		if (checkCount == 0) {
			Response.setError("查无此批次号，请检查");
			return;
		}
		//判断该批次号是否有未处理的申请
		int checkAuthorityinfoCount = 0;
		try {			
			checkAuthorityinfoCount= new QueryBuilder("select count(*) from authorityinfo where BatchNumber = '" + applicationBatchNumber + "' and state = '0'").executeInt();
		} catch (Exception e) {
			e.printStackTrace();
			Response.setError("数据库查询错误，请检查填入的值");
		}
		if (checkAuthorityinfoCount != 0) {
			Response.setError("该批次号已经提交过申请，不可多次提交");
			return;
		}
		String sql = "INSERT INTO authorityinfo (ID,BatchNumber,branchCode,reason,state,createdate,createuser) VALUES('"
				+ ID + "','" + applicationBatchNumber + "','" + branchCode + "','" + reason + "','" + state + "','" + createdate + "','" + createuser + "');";
		try {
			int num = new QueryBuilder(sql).executeNoQuery();
			Response.setMessage("成功提交申请！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setError("提交申请发生错误！");
		}
	}
	public void approvalPass(){
		String ID = $V("ID");
		String modifyDate = DateUtil.getCurrentDate();
		String modifyUser = User.getUserName();
		
		int checkAuthorityinfoCount = 0;
		try {			
			checkAuthorityinfoCount= new QueryBuilder("select count(*) from authorityinfo where FIND_IN_SET(ID,'" + ID + "') and state in ('1','-1')").executeInt();
		} catch (Exception e) {
			e.printStackTrace();
			Response.setError("数据库查询错误，请检查填入的值");
		}
		if (checkAuthorityinfoCount != 0) {
			Response.setError("该批次号已经通过审核，不可重复审核");
			return;
		}
		Transaction trans = new Transaction();
		QueryBuilder qb1 = new QueryBuilder("UPDATE authorityinfo SET  state = '1',modifyDate = '" + modifyDate + "', modifyUser = '" + modifyUser + "' where FIND_IN_SET(ID,'" + ID + "') and state = '0' ");
		QueryBuilder qb2 = new QueryBuilder("UPDATE financialsettlementdetails f,authorityinfo a SET f.state = '0' WHERE a.batchNumber=f.batchNumber and FIND_IN_SET(a.ID,'" + ID + "')");
		QueryBuilder qb3 = new QueryBuilder("delete from f USING financialsuredatasum f,authorityinfo a where a.batchNumber=f.batchNumber and FIND_IN_SET(a.ID,'" + ID + "')");
		trans.add(qb1);
		trans.add(qb2);
		trans.add(qb3);
		try {
			trans.commit();
			Response.setMessage("成功恢复权限");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setError("确认申请发生错误！");
		}
	}
	public void approvalRefuse(){
		String ID = $V("ID");
		String modifyDate = DateUtil.getCurrentDate();
		String modifyUser = User.getUserName();
		int checkAuthorityinfoCount = 0;
		try {			
			checkAuthorityinfoCount= new QueryBuilder("select count(*) from authorityinfo where FIND_IN_SET(ID,'" + ID + "') and state in ('1','-1')").executeInt();
		} catch (Exception e) {
			e.printStackTrace();
			Response.setError("数据库查询错误，请检查填入的值");
		}
		if (checkAuthorityinfoCount != 0) {
			Response.setError("该批次号已经通过审核，不可重复审核");
			return;
		}
		QueryBuilder qb1 = new QueryBuilder("UPDATE authorityinfo SET  state = '-1',modifyDate = '" + modifyDate + "', modifyUser = '" + modifyUser + "' where FIND_IN_SET(ID,'" + ID + "') and state = '0' ");
		try {
			int num = qb1.executeNoQuery();
			Response.setMessage("已拒绝申请");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setError("拒绝申请发生错误！");
		}
	}
	//TODO
	public static void dgDataBindStatistics(DataGridAction dga) {

		String branchCode = dga.getParam("branchCode");
		String invoiceStartDate = dga.getParam("invoiceStartDate");
		String invoiceEndDate = dga.getParam("invoiceEndDate");
		QueryBuilder qb = new QueryBuilder();
		StringBuilder sb = new StringBuilder("select insurancetype,insurancesubtype,premium1stSUM,premium2ndSUM,fee1stSUM,fee2ndSUM from financialsuredataSUM where 1=1");
		
		if (StringUtils.isNotEmpty(branchCode)) {
			sb.append(" and FIND_IN_SET(branchcode,?)");
			qb.add(branchCode);
		}
		if (StringUtils.isNotEmpty(invoiceStartDate)) {
			sb.append(" and Date(invoicedate) >= ?");
			qb.add(invoiceStartDate);
		}
		if (StringUtils.isNotEmpty(invoiceEndDate)) {
			sb.append(" and Date(invoicedate) <= ?");
			qb.add(invoiceEndDate);
		}
		String sql = sb.toString();
		qb.setSQL(sql);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("insurancetype", dataInfoMap);
		dt.decodeColumn("insurancesubtype", dataInfoMap);
		dga.bindData(dt);
	}
	public void orderCountStatistics(){
		String branchCode = $V("branchCode");
		String invoiceStartDate = $V("invoiceStartDate");
		String invoiceEndDate = $V("invoiceEndDate");
		
		String sql1 = "select ROUND(SUM(premium1stSUM),2) + ROUND(SUM(premium2ndSUM),2) as typea from financialsuredataSUM where  1=1 ";
		
		String sql2 = "select ROUND(SUM(fee1stSUM),2) + ROUND(SUM(fee2ndSUM),2) as typea from financialsuredataSUM where  1=1 ";
		
		String sql3 = "select ROUND(SUM(premium1stSUM) + SUM(premium2ndSUM) + SUM(fee1stSUM) + SUM(fee2ndSUM),2) as typea from financialsuredataSUM where 1=1 ";
		
		if (StringUtil.isNotEmpty(branchCode)) {
			sql1 = sql1 + " and FIND_IN_SET(branchcode,'" + branchCode + "')";
			sql2 = sql2 + " and FIND_IN_SET(branchcode,'" + branchCode + "')";
			sql3 = sql3 + " and FIND_IN_SET(branchcode,'" + branchCode + "')";
		}
		if (StringUtil.isNotEmpty(invoiceStartDate)) {
			sql1 = sql1 + " and Date(invoicedate) >= '" + invoiceStartDate + "'";
			sql2 = sql2 + " and Date(invoicedate) >= '" + invoiceStartDate + "'";
			sql3 = sql3 + " and Date(invoicedate) >= '" + invoiceStartDate + "'";
		}
		if (StringUtil.isNotEmpty(invoiceEndDate)) {
			sql1 = sql1 + " and Date(invoicedate) <= '" + invoiceEndDate + "'";
			sql2 = sql2 + " and Date(invoicedate) <= '" + invoiceEndDate + "'";
			sql3 = sql3 + " and Date(invoicedate) <= '" + invoiceEndDate + "'";
		}
		DataTable dt1 = new QueryBuilder(sql1).executeDataTable();
		DataTable dt2 = new QueryBuilder(sql2).executeDataTable();
		DataTable dt3 = new QueryBuilder(sql3).executeDataTable();
		
		String a = dt1.getString(0, 0);
		String b = dt2.getString(0, 0);
		String c = dt3.getString(0, 0);
		Response.setMessage(a + "&" + b + "&" + c);
		
	}
}
