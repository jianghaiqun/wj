package com.sinosoft.cms.dataservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.sinosoft.util.ExcelWriteUtil;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.ZipUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ProductHelpInfoSchema;
import com.sinosoft.schema.ProductHelpInfoSet;

/**
 * 
 * @author wangwenying
 * 
 */
public class ExportProductInfo extends Page {
	
	/**
	 * createFile:生成辅助材料. <br/>
	 *
	 * @author wwy
	 */
	public void createFile(){
		
		String productId = (String) Request.get("productId");

		List<String> fileNames = new ArrayList<String>();
		// 投保要素配置信息
		QueryBuilder qb = new QueryBuilder("select factordisplayValue,factorvalue,factortype from femriskfactorlistb where riskcode=?", productId);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			File file = new File(Config.getValue("RefundFilePath") + "/ProductHelpFile/" + productId);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		else {
			Response.setLogInfo(1, "创建失败，没有该产品数据！");
			return;
		}
		List<List<String>> femriskfactorlist_data = dataTableToList(dt);
		String femriskfactorlist_fileName = Config.getValue("RefundFilePath") + "/ProductHelpFile/" + productId + "/femriskfactorlist.xls";
		ExcelWriteUtil.writeLines(femriskfactorlist_fileName, femriskfactorlist_data);
		fileNames.add(femriskfactorlist_fileName);
		
		boolean occup = false;
		for (List<String> list : femriskfactorlist_data) {
			String factortype = list.get(2);
			if ("Occup".equals(factortype)) {
				occup = true;
			}
		}
		// 职业
		if (occup) {
			QueryBuilder qbOccup = new QueryBuilder("select id,name,parent_id from occupation where insurancecompany = ?", productId.subSequence(0, 4));
			DataTable dtOccup = qbOccup.executeDataTable();
			if (dtOccup.getRowCount() > 0) {
				List<List<String>> occup_data = dataTableToList(dtOccup);
				
				String occup_fileName = Config.getValue("RefundFilePath") + "/ProductHelpFile/" + productId + "/occup.xls";
				ExcelWriteUtil.writeLines(occup_fileName, occup_data);
				fileNames.add(occup_fileName);
			}
		}
		
		// 地区
		QueryBuilder qbArea = new QueryBuilder("select id,name,parent_id from area where productId = ?", productId);
		DataTable dtArea = qbArea.executeDataTable();
		if (dtArea.getRowCount() > 0) {
			List<List<String>> aera_data = dataTableToList(dtArea);
			String area_fileName = Config.getValue("RefundFilePath") + "/ProductHelpFile/" + productId + "/area.xls";
			ExcelWriteUtil.writeLines(area_fileName, aera_data);
			fileNames.add(area_fileName);
		}
		
		// 参考网址
		QueryBuilder productInfo = new QueryBuilder("select HtmlPath from sdproduct where productId = ?", productId);
		String htmlPath = productInfo.executeString();
		if (StringUtil.isNotEmpty(htmlPath)) {
			String htmlPath_fileName = Config.getValue("RefundFilePath") + "/ProductHelpFile/" + productId + "/htmlPath.txt";
			FileUtil.writeText(htmlPath_fileName, htmlPath);
			fileNames.add(htmlPath_fileName);
		}
		
		String result = "FAIL";
		if (fileNames.size() > 0) {
			String[] srcFiles = new String[fileNames.size()];
			fileNames.toArray(srcFiles);

			String name = productId + ".zip";
			String zipFilePath = "/ProductHelpFile/" + name;
			String destFile = Config.getValue("RefundFilePath") + zipFilePath;
			
			try {
				ZipUtil.zipBatch(srcFiles, destFile);
				InputStream in = new FileInputStream(new File(destFile));
				in.close();
				// 数据库记录
				inserFileRecode(zipFilePath, productId);
				result = "SUCCESS";
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		if ("SUCCESS".equals(result)) {
			Response.setLogInfo(0, "创建完成！");
		}
		else {
			Response.setLogInfo(1, result);
		}
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		String productId = (String) dga.getParams().get("productId");
		QueryBuilder qb = new QueryBuilder("select * from ProductHelpInfo where 1=1 ");
		if (StringUtil.isNotEmpty(productId)) {
			qb.append(" and productId = ?", productId.trim());
		}
	
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		qb.append(" order by ID ");
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * inserFileRecode:插入到数据中. <br/>
	 *
	 * @author wwy
	 * @param filePath
	 * @param productId
	 * @return
	 */
	private boolean inserFileRecode (String filePath, String productId) {
		
		ProductHelpInfoSchema info = new ProductHelpInfoSchema();
		ProductHelpInfoSet set = info.query(new QueryBuilder("where productId = ?", productId));
		if (set.size() > 0) {
			info = set.get(0);
			info.setModifyTime(new Date());
			info.setModifyUser(User.getUserName());
			return info.update();
		}
		info.setAddTime(new Date());
		info.setAddUser(User.getUserName());
		info.setId(NoUtil.getMaxNo("ProductHelpInfo"));
		info.setFilePath(filePath);
		info.setProductId(productId);
		return info.insert();
	}
	
	/**
	 * dataTableToList:DataTable转换成List<List<String>>. <br/>
	 *
	 * @author wwy
	 * @param dt
	 * @return
	 */
	private List<List<String>> dataTableToList(DataTable dt) {

		List<List<String>> data = new ArrayList<List<String>>();
		
		for (int i = 0; i < dt.getRowCount(); i++) {
			List<String> rowList = new ArrayList<String>();
			for (int j = 0; j < dt.getColCount(); j++) {
				rowList.add((String) dt.getString(i, j));
			}
			data.add(rowList);
		}
		
		return data;
	}

	/**
	 * 删除 
	 */
	public void delFileInfo() {
		String ids = $V("IDs");
		String[] idArr = ids.split(",");
		String sqlIds = "";
		for (int i = 0; i < idArr.length; i++) {
			sqlIds += "'" + idArr[i] + "',";
		}
		sqlIds = sqlIds.substring(0, sqlIds.length() - 1);
		
		Transaction trans = new Transaction();
		ProductHelpInfoSchema schema = new ProductHelpInfoSchema();
		ProductHelpInfoSet set = schema.query(new QueryBuilder("where id in (" + sqlIds + ")"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		}
		else{
			Response.setLogInfo(0, "删除失败");
		}
		for (int i = 0; i < set.size(); i++) {
			FileUtil.deleteFromDir(Config.getValue("RefundFilePath") + "/ProductHelpFile/" + set.get(i).getProductId());
		}
	}
}
