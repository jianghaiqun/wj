package com.wangjin.payment;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

/**
 * 在线回访内容配置管理
 * 
 * @author guozc
 * @date 2017-08-21
 */
public class OnlineRevisitContent extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String productName = dga.getParams().getString("ProductName");
		String productId = dga.getParams().getString("productId");
		StringBuffer listSql = new StringBuffer();
		listSql.append("select a.ProductID,a.ProductName");
		listSql.append(" from sdproduct a where a.IsPublish = 'Y'");
		QueryBuilder qb = new QueryBuilder(listSql.toString());
		if (StringUtil.isNotEmpty(productName)) {
			qb.append(" and a.ProductName like ? ", "%" + productName + "%");
		}
		if (StringUtil.isNotEmpty(productId)) {
			qb.append(" and a.productId = ? ", productId);
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public static Mapx<String, Object> initDialog(Mapx<String, Object> params) {
		String productId = params.getString("productId");
		QueryBuilder qb = new QueryBuilder("select content from onlinerevisitcontent " + "where productId = ?",
				productId);
		String content = qb.executeString();
		params.put("content", content);
		return params;
	}

	public void saveContent() {
		String productId = $V("productId");
		String content = $V("content");
		QueryBuilder qb = new QueryBuilder("select count(*) from onlinerevisitcontent " + "where productId = ?",
				productId);
		if (qb.executeInt() > 0) {
			qb = new QueryBuilder("update onlinerevisitcontent set content = ? where productId = ?", content,
					productId);
		} else {
			qb = new QueryBuilder("insert into onlinerevisitcontent(content,productId) values(?,?)", content,
					productId);
		}
		int rows = qb.executeNoQuery();
		if (rows > 0) {
			Response.setStatus(1);
			Response.setMessage("保存成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("保存失败！");
		}
	}
}
