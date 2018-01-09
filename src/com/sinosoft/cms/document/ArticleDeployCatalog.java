package com.sinosoft.cms.document;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.HtmlTable;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.DataTableUtil;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

public class ArticleDeployCatalog extends Page {
	public static void articleDialogDataBind(DataGridAction dga) {
		String innerCode = dga.getParam("CatalogInnerCode");
		String data = new QueryBuilder("select targetCatalog from ZCInnerDeploy where CatalogInnerCode=?", innerCode)
				.executeString();
		DataTable dt = null;
		if (StringUtil.isEmpty(data)) {
			dt = new DataTable();
			dt.insertColumn("ServerAddr");
			dt.insertColumn("SiteID");
			dt.insertColumn("SiteName");
			dt.insertColumn("CatalogID");
			dt.insertColumn("CatalogName");
			dt.insertColumn("Password");
		} else {
			dt = DataTableUtil.txtToDataTable(data, (String[]) null, "\t", "\n");
		}
		if (dt.getDataColumn("MD5") == null) {
			dt.insertColumn("MD5");
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "MD5", StringUtil.md5Hex(dt.getString(i, "ServerAddr") + "," + dt.getString(i, "SiteID") + ","
					+ dt.getString(i, "CatalogID")));
		}
		dga.bindData(dt);
		HtmlTable table = dga.getTable();
		for (int i = 1; i < table.getChildren().size(); i++) {
			table.getTR(i).removeAttribute("onclick");// 默认单击一行其他行的选择状态取消，需要去掉
		}
	}
}
