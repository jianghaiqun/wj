package com.sinosoft.shop;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.member.Member;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZDColumnValueSchema;
import com.sinosoft.schema.ZSFavoriteSchema;
import com.sinosoft.schema.ZSFavoriteSet;
import com.sinosoft.schema.ZSGoodsSchema;
import com.sinosoft.schema.ZSGoodsSet;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.util.Date;

public class Goods extends Page {

	public static Mapx initDialog(Mapx params) {
		if (StringUtil.isNotEmpty(params.getString("ID"))) {
			ZSGoodsSchema goods = new ZSGoodsSchema();
			goods.setID(params.getLong("ID"));
			if (!goods.fill()) {
				return params;
			}

			params.put("GoodsLibID", goods.getCatalogID());
			params.put("CatalogName", new QueryBuilder("select Name from ZCCatalog where ID = ?", goods.getCatalogID())
					.executeString());
			params.putAll(goods.toMapx());
			params.put("PublishDate", DateUtil.toString(goods.getPublishDate()));
			params.put("PicSrc1", goods.getImage0());
			params.put("PicSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + goods.getImage0())
					.replaceAll("//", "/"));
			DataTable dt = new QueryBuilder("select Name, ID from ZSBrand where SiteID = ? order by ID", Application
					.getCurrentSiteID()).executeDataTable();
			params.put("BrandOptions", HtmlUtil.dataTableToOptions(dt, String.valueOf(goods.getBrandID()), false));

			// params.put("CustomColumn",
			// ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN,
			// String.valueOf(goods.getCatalogID())));
			params.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, String.valueOf(goods
					.getCatalogID()), ColumnUtil.RELA_TYPE_DOCID, goods.getID() + ""));
		} else {
			params.put("PicSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + "upload/Image/nopicture.jpg")
					.replaceAll("//", "/"));
			DataTable dt = new QueryBuilder("select Name, ID from ZSBrand where SiteID = ? order by ID", Application
					.getCurrentSiteID()).executeDataTable();
			params.put("BrandOptions", HtmlUtil.dataTableToOptions(dt, false));
			String catalogID = params.getString("CatalogID");
			params.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID));
			params.put("UpTime", DateUtil.getCurrentDate());
		}
		return params;
	}

	public void add() {
		Transaction trans = new Transaction();
		long ID = NoUtil.getMaxID("GoodsID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		// String good = $V("goodsParentID");
		String good = $V("CatalogID");

		if (good.equals(null) && good.length() > 0) {
			Response.setLogInfo(0, "请选择商品类别");
			return;
		}
		catalog.setID(good);
		catalog.fill();
		ZSGoodsSchema goods = new ZSGoodsSchema();
		goods.setCatalogID(good);
		goods.setCatalogInnerCode(catalog.getInnerCode());
		goods.setType("1");
		goods.setTopFlag("1");
		goods.setStickTime(123213);
		goods.setBranchInnerCode(catalog.getBranchInnerCode());
		goods.setID(ID);
		goods.setStatus(Article.STATUS_DRAFT + "");
		goods.setSiteID(Application.getCurrentSiteID());
		goods.setCommentCount(0);
		goods.setHitCount(0);

		String ImageID = $V("ImageID");
		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
					.executeDataTable();
			goods.setImage0((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
					.toString());
		} else {
			goods.setImage0("upload/Image/nopicture.jpg");
		}
		goods.setAddTime(new Date());
		goods.setAddUser(User.getUserName());
		goods.setValue(Request);
		goods.setOrderFlag(OrderUtil.getDefaultOrder());

		SchemaSet ss = ColumnUtil.getValueFromRequest(goods.getCatalogID(), goods.getID(), this.Request);

		trans.add(ss, Transaction.INSERT);
		trans.add(goods, Transaction.INSERT);

		// 处理自定义字段
		if (trans.commit()) {
			Response.setLogInfo(1, "新建成功");
		} else {
			Response.setLogInfo(0, "新建失败");
		}
	}

	public void save() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZSGoodsSchema goods = new ZSGoodsSchema();
			goods.setID(dt.getString(i, "ID"));
			if (!goods.fill()) {
				Response.setLogInfo(0, "您要修改的项" + goods.getID() + "不存在!");
				return;
			}
			goods.setValue(dt.getDataRow(i));
			goods.setModifyUser(User.getUserName());
			goods.setModifyTime(new Date());
			trans.add(goods, Transaction.UPDATE);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}

	public void dg1Edit() {
		String ImageID = $V("ImageID");
		String imagePath = $V("PicSrc1");
		ZSGoodsSchema goods = new ZSGoodsSchema();
		String ID = $V("ID");
		goods.setID(ID);
		if (!goods.fill()) {
			Response.setLogInfo(0, "您要修改的商品" + goods.getName() + "不存在!");
			return;
		}
		Transaction trans = new Transaction();
		if(goods.getPrice() > Double.parseDouble(Request.getString("Price"))) {
			//降价提醒
			DataTable dt = new QueryBuilder("select a.Name, b.UserName from ZSFavorite b, ZSGoods a where b.GoodsID = ? and b.GoodsID = a.ID and b.SiteID = ? and a.SiteID = b.SiteID and b.PriceNoteFlag = 'Y'", 
					ID, Application.getCurrentSiteID()).executeDataTable();
			
			for(int i = 0; i < dt.getRowCount(); i++) {
				Member member = new Member(dt.getString(i, "UserName"));
				if(!member.fill()) {
					continue;
				}
				SimpleEmail email = new SimpleEmail();
				email.setHostName("smtp.163.com");
				try {
					String siteName = SiteUtil.getName(Application.getCurrentSiteID());
					StringBuffer sb = new StringBuffer();
					sb.append("尊敬的" + siteName + "用户：<br/>");
					sb.append("你好！<br/>");
					sb.append("您关注的商品" + dt.getString(i, "Name") + "已经降价，请点击一下链接查看相关信息：<br/>");
//					sb.append();
					sb.append("<br/><br/>注：此邮件为系统自动发送，请勿回复。<br/>");
					sb.append("　　　　　　　　　　　　　　　　　　　　　　　————" + siteName);
					email.setAuthentication("0871huhu@163.com","08715121182");
					email.addTo(member.getEmail(),member.getUserName());
					email.setFrom("0871huhu@163.com", siteName);
					email.setSubject(siteName + "：商品降价提醒！");
					email.setContent(sb.toString(), "text/html;charset=utf-8");
					
				} catch (EmailException e) {
					Response.setLogInfo(0,"邮件发送错误");
					logger.error(e.getMessage(), e);
				}
			}
		}
		
		goods.setValue(Request);
		goods.setModifyUser(User.getUserName());
		goods.setModifyTime(new Date());
		goods.setStatus(Article.STATUS_EDITING + "");

		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
					.executeDataTable();
			String path = (imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
					.toString();
			goods.setImage0(path);
		} else {
			goods.setImage0(imagePath);
		}

		trans.add(goods, Transaction.UPDATE);
		// 处理自定义字段
		DataTable dt = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, goods.getID());
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDColumnValueSchema value = new ZDColumnValueSchema();
			value.setValue(dt.getDataRow(i));
			value.setTextValue($V(ColumnUtil.PREFIX + value.getColumnCode()));
			trans.add(value, Transaction.UPDATE);
		}

		if (trans.commit()) {
			Response.setLogInfo(1, "修改成功");
		} else {
			Response.setLogInfo(0, "修改" + goods.getID() + "失败!");
		}
	}

	public void del() {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			return;// 检查SQL注入
		}
		Transaction trans = new Transaction();
		ZSGoodsSchema goods = new ZSGoodsSchema();
		ZSGoodsSet set = goods.query(new QueryBuilder("where id in (" + IDs + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		
		//检查相关商品关联
		ZSFavoriteSchema fav = new ZSFavoriteSchema();
		ZSFavoriteSet favs = fav.query(new QueryBuilder("where GoodsID in (" + IDs + ")"));
		trans.add(favs, Transaction.DELETE);
		
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}

	public boolean checkSN() {
		String SN = $V("SN");
		if (StringUtil.isEmpty(SN)) {
			Response.setLogInfo(0, "不能为空");
			return false;
		}
		int count = new QueryBuilder("select count(1) from ZSGoods where SN=?", SN).executeInt();
		if (count > 0) {
			Response.setLogInfo(0, "已经存在此类编号的药品:" + $V("SN"));
			return false;
		}
		return true;
	}
	/*
	 * public void setNewTopper(){ ZSGoodsSchema goods = new ZSGoodsSchema();
	 * goods.setID($V("ID")); goods.fill(); goods.setIsNew("Y");
	 * goods.setOrderNew(0); if(goods.update()){
	 * this.Response.setLogInfo(1,"置顶成功！"); }else{
	 * this.Response.setLogInfo(0,"置顶失败！"); } }
	 * 
	 * public void setSpecialTopper(){ ZSGoodsSchema goods = new
	 * ZSGoodsSchema(); goods.setID($V("ID")); goods.fill();
	 * goods.setIsSpecial("Y"); goods.setOrderSpecial(0); if(goods.update()){
	 * this.Response.setLogInfo(1,"置顶成功！"); }else{
	 * this.Response.setLogInfo(0,"置顶失败！"); } }
	 * 
	 * public void setHotTopper(){ ZSGoodsSchema goods = new ZSGoodsSchema();
	 * goods.setID($V("ID")); goods.fill(); goods.setIsHot("Y");
	 * goods.setOrderHot(0); if(goods.update()){
	 * this.Response.setLogInfo(1,"置顶成功！"); }else{
	 * this.Response.setLogInfo(0,"置顶失败！"); } }
	 * 
	 * public void setOrderTopper(){ ZSGoodsSchema goods = new ZSGoodsSchema();
	 * goods.setID($V("ID")); goods.fill(); goods.setIsSale("Y");
	 * goods.setOrderSale(0); if(goods.update()){
	 * this.Response.setLogInfo(1,"置顶成功！"); }else{
	 * this.Response.setLogInfo(0,"置顶失败！"); } }
	 */
}
