package com.sinosoft.platform;

import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;

public class TBDataCount extends Page {

	private static String TODAY = DateUtil.toString(new Date(), "yyyy-MM-dd");
	private static String YESTERDAY = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
	private static String TMINUTES = " 23:59:59";
	
	/**
	 * 淘宝数据统计左侧筛选条件树生成（保险公司）
	 * @param ta
	 */
	public static void treeDataBind(TreeAction ta) {
		// 获取所有保险公司店铺名称
		String sql = "SELECT Id,ParentID,ShopName,Level AS TreeLevel FROM ZDTBShopInfo;";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		ta.setRootText("保险公司");
		dt.setWebMode(false);
		ta.bindData(dt);
	}
	
	/**
	 * 淘宝数据统计左侧筛选条件树生成（保险类别）
	 * @param ta
	 */
	public static void treeDataBind2(TreeAction ta) {
		DataTable dt = new DataTable();
		DataColumn[] columns = { new DataColumn("Id", DataColumn.STRING),
				new DataColumn("ParentID", DataColumn.STRING),
				new DataColumn("Name", DataColumn.STRING),
				new DataColumn("TreeLevel", DataColumn.STRING)};
		dt.insertRow(new DataRow(columns, new Object[] { "001", "0", "保险类别", "0" }));
		dt.insertRow(new DataRow(columns, new Object[] { "100", "001", "意外险", "1" }));
		dt.insertRow(new DataRow(columns, new Object[] { "200", "001", "旅游险", "1" }));
		dt.insertRow(new DataRow(columns, new Object[] { "300", "001", "财产险", "1" }));
		dt.insertRow(new DataRow(columns, new Object[] { "400", "001", "健康险", "1" }));
		dt.insertRow(new DataRow(columns, new Object[] { "500", "001", "寿险", "1" }));
		ta.setRootText("保险类别");
		dt.setWebMode(false);
		ta.bindData(dt);
	}
	
	/**
	 * 获得发布统计的数据
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String companys = dga.getParam("company");
		String insTypes = dga.getParam("insType");
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ItemName,ItemID,ShopName,InsTypeName,TSellCount,SellCount,Price,ModifyDate FROM ZDTBShopData WHERE 1=1 ");
		if (StringUtil.isNotEmpty(companys)) {
			sql.append(" AND ShopId IN (" + companys + ")");
		}
		if (StringUtil.isNotEmpty(insTypes)) {
			sql.append(" AND InsType IN (" + insTypes + ")");
		}
		if (StringUtil.isNotEmpty(startDate)) {
			sql.append(" AND ModifyDate >= '" + startDate + "'");
		} else {
			sql.append(" AND ModifyDate >= '" + YESTERDAY + "'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			sql.append(" AND ModifyDate <='" + endDate + TMINUTES + "'");
		} else {
			sql.append(" AND ModifyDate <='" + TODAY + TMINUTES + "'");
		}
		sql.append(" ORDER BY ModifyDate DESC");
		
		DataTable dt = new QueryBuilder(sql.toString()).executeDataTable();
		dga.bindData(dt);
	}
}
