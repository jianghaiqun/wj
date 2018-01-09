package com.sinosoft.shop.web;

import java.util.Date;

import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.schema.ZSFavoriteSchema;
import com.sinosoft.schema.ZSFavoriteSet;


public class MemberFavorite extends Ajax {
	
	public static void dg1DataBind(DataGridAction dga) {
		
		if (dga.getTotal() == 0) {
			String sql2 = "select count(*) from ZSGoods a, ZSFavorite b where a.SiteID = ? and a.ID = b.GoodsID and b.UserName = ?";
			QueryBuilder qb = new QueryBuilder(sql2);
			qb.add(Application.getCurrentSiteID());
			qb.add(User.getUserName());		//前台应该用User.getValue("Name");获取会员姓名
			dga.setTotal(qb.executeInt());
		}
		
		String sql1 = "select a.ID, a.Name, a.Image0, a.Price, b.AddTime, b.PriceNoteFlag from ZSGoods a, ZSFavorite b where a.SiteID = ? and a.ID = b.GoodsID and b.UserName = ? order by AddTime";
		QueryBuilder qb = new QueryBuilder(sql1);
		qb.add(dga.getParam("SiteID"));
		qb.add(User.getUserName());		//前台应该用User.getValue("Name");获取会员姓名
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("Image");
		for(int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "Image", (Config.getContextPath() + Config.getValue("UploadDir") + "/" 
					+ SiteUtil.getAlias(dga.getParam("SiteID")) + "/" + dt.getString(i, "Image0")).replaceAll("//", "/"));
		}
		
		dga.bindData(dt);
	}
	
	/**
	 * 商品降价提醒
	 */
	public void priceNote() {
		String ids = $V("IDs");
		String siteID = $V("SiteID");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZSFavoriteSchema favorite = new ZSFavoriteSchema();
		ZSFavoriteSet set = favorite.query(new QueryBuilder(" where SiteID=? and GoodsID in (" + ids + ") and UserName = '" + User.getValue("UserName") + "'", siteID)); 
		for(int i = 0; i < set.size(); i++) {
			set.get(i).setPriceNoteFlag("Y");
		}
		trans.add(set, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("降价提醒设置成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("对不起，发生错误!请您联系客服!");
		}
	}
	
	/**
	 * 从收藏夹移出指定商品
	 */
	public void removeFromFavorite() {
		String ids = $V("IDs");
		String siteID = $V("SiteID");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZSFavoriteSchema favorite = new ZSFavoriteSchema();
		//前台应该用User.getValue("Name");获取会员姓名
		ZSFavoriteSet set = favorite.query(new QueryBuilder(" where SiteID=? and GoodsID in (" + ids + ") and UserName = '" + User.getUserName() + "'", siteID)); 
		trans.add(set, Transaction.DELETE);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("移出商品成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("对不起，发生错误!请您联系客服!");
		}
	}
	
	/**
	 * 添加收藏夹商品进购物车
	 */
	public void addToCart() {
		String GoodsID = Request.getString("GoodsID");
		String buyNowFlag = Request.getString("BuyNowFlag");
		Mapx map = new Mapx();
		String shopCartCookie = this.Cookie.getCookie("ShopCart");
		if(StringUtil.isNotEmpty(shopCartCookie)) {
			String[] goodsArray = shopCartCookie.split("X");
			for(int i = 0; i < goodsArray.length; i++) {
				map.put(goodsArray[i].split("-")[0], goodsArray[i].split("-")[1]);
			}
		}
		for(int i = 0; i < GoodsID.split("-").length; i++) {
			if(map.containsKey(GoodsID.split("-")[i])) {
				map.put(GoodsID.split("-")[i], map.getInt(GoodsID.split("-")[i]) + 1);
			} else {
				map.put(GoodsID.split("-")[i], 1);
			}
		}
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < map.keyArray().length; i++) {
			sb.append(map.keyArray()[i]);
			sb.append("-");
			sb.append(map.getString(map.keyArray()[i]));
			if(i < (map.keyArray().length - 1)) {
				sb.append("X");
			}
		}
		this.Cookie.setCookie("ShopCart", sb.toString());
		if("1".equals(buyNowFlag)) {
			Response.setLogInfo(1, "成功加入购物车!");
		} else {
			Response.setLogInfo(2, "成功加入购物车!");
		}
	}
}


