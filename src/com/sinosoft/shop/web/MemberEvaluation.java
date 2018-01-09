package com.sinosoft.shop.web;

import java.util.Date;

import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCCommentSchema;
import com.sinosoft.schema.ZSGoodsSchema;

public class MemberEvaluation extends Ajax {

	public static void dg1DataBind(DataGridAction dga) {
		if (dga.getTotal() == 0) {
			String sql2 = "select count(*) from ZSOrderItem where SiteID = ? and UserName = ?";
			QueryBuilder qb = new QueryBuilder(sql2);
			qb.add(Application.getCurrentSiteID());
			qb.add(User.getValue("UserName")); 
			dga.setTotal(qb.executeInt());
		}

		String sql1 = "select a.*, b.Image0 from ZSOrderItem a, ZSGoods b, zsorder c where a.SiteID = ? and a.UserName = ? and a.orderid = c.id and c.status='7' and a.GoodsID = b.ID order by AddTime";
		QueryBuilder qb = new QueryBuilder(sql1);
		qb.add(dga.getParam("SiteID"));
		qb.add(User.getValue("UserName"));
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dt.insertColumn("Image");
		dt.insertColumn("Evaluate");
		for (int i = dt.getRowCount() - 1; i >= 0 ; i--) {
			dt.set(i, "Image", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(dga.getParam("SiteID")) + "/" + dt.getString(i, "Image0")).replaceAll(
					"//", "/"));
			int flag = new QueryBuilder("select count(*) from ZCComment where RelaID = ? and AddUser = ?", dt.get(i,
					"GoodsID"), dt.get(i, "UserName")).executeInt();

			if (DateUtil.addMonth(dt.getDate(i, "AddTime"), 6).before(new Date())) {
				dt.set(i, "Evaluate", flag > 0 ? "已评价" : "未评价");
			} else {
				dt.set(i, "Evaluate", flag > 0 ? "已评价" : "<button type='button' onClick='evaluate("
						+ dt.getString(i, "GoodsID") + ");'>评价</button>");
			}
		}
		dga.bindData(dt);
	}

	/**
	 * 添加指定商品的评价
	 */
	public void evaluate() {
		String goodsID = $V("GoodsID");
		ZSGoodsSchema goods = new ZSGoodsSchema();
		goods.setID(goodsID);
		if (goods.fill()) {
			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setID(NoUtil.getMaxID("ZCCommentID"));
			comment.setValue(Request);

			comment.setRelaID(goodsID);
			comment.setCatalogID(goods.getCatalogID());
			comment.setSiteID(goods.getSiteID());
			comment.setCatalogType("9");
			comment.setVerifyFlag("X");

			comment.setAddUser(User.getValue("UserName").toString()); // 前台应该用User.getValue("Name");获取会员姓名
			comment.setAddTime(new Date());
			comment.setAddUserIP($V("MemberIP"));
			if (comment.insert()) {
				Response.setStatus(1);
				Response.setMessage("添加评价成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("对不起!发生错误!请您联系客服!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("对不起!发生错误!请您联系客服!");
		}
	}
}
