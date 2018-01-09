package com.sinosoft.cms.site;

import java.util.Date;

import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.template.PageGenerator;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCLinkSchema;
import com.sinosoft.schema.ZCLinkSet;
import com.sinosoft.schema.ZCPageBlockSchema;
import com.sinosoft.schema.ZCPageBlockSet;

/**
 * 链接分组管理
 * 
 */

public class Link extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String groupID = dga.getParam("LinkGroupID");
		String sql = "select ZCLink.*,(select name from zclinkgroup where id=zclink.linkgroupID) as LinkGroupName from ZCLink ";
		QueryBuilder qb = new QueryBuilder(sql);
		if (StringUtil.isNotEmpty(groupID)) {
			qb.append(" where LinkGroupID=?", Long.parseLong(groupID));
		} else {
			qb.append(" where exists (select '' from ZCLinkGroup where ID=ZCLink.LinkGroupID and SiteID=?)");
			qb.add(Application.getCurrentSiteID());
		}
		qb.append(" order by OrderFlag desc,id desc");
		dga.bindData(qb);
	}

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		String type = params.getString("Type");
		if (StringUtil.isNotEmpty(ID)) {
			ZCLinkSchema link = new ZCLinkSchema();
			link.setID(ID);
			link.fill();
			Mapx map = link.toMapx();
			map.put("Type", type);
			map.put("ImageSrc", (Config.getValue("StaticResourcePath") + "/" + link.getImagePath()).replaceAll("//","/"));
			return map;
		} else {
			params.put("LinkGroupID", params.getString("LinkGroupID"));
			params.put("Type", type);
			params.put("URL", "http://");
			params.put("ImageSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/upload/Image/nopicture.jpg").replaceAll(
					"//", "/"));
			return params;
		}
	}

	public void save() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			QueryBuilder qb = new QueryBuilder("update ZCLink set Name=?,URL=?,OrderFlag=?,Prop3=?,ModifyUser=?,ModifyTime=? where ID=?");
			qb.add(dt.getString(i, "Name"));
			qb.add(dt.getString(i, "URL"));
			qb.add(dt.getString(i, "OrderFlag"));
			qb.add(dt.getString(i, "Prop3"));
			qb.add(User.getUserName());
			qb.add(new Date());
			qb.add(Long.parseLong(dt.getString(i, "ID")));
			trans.add(qb);
		}
		if (trans.commit()) {
			updateStatLink();
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}

	public void edit() {
		Transaction trans = new Transaction();
		String ID = $V("ID");
		if (StringUtil.isEmpty(ID) || !StringUtil.isDigit(ID)) {
			Response.setLogInfo(0, "传入ID错误");
			return;
		}

		ZCLinkSchema link = new ZCLinkSchema();
		link.setID(ID);
		link.fill();
		link.setValue(Request);
		String ImageID = $V("ImageID");
		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable dt = new QueryBuilder("select path,srcfilename from zcimage where id=?", ImageID)
					.executeDataTable();
			link.setImagePath((dt.get(0, "path").toString() + dt.get(0, "srcfilename")).replaceAll("//", "/")
					.toString());
		}
		link.setModifyUser(User.getUserName());
		link.setModifyTime(new Date());
		trans.add(link, Transaction.UPDATE);
		if (trans.commit()) {
			updateStatLink();
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}

	public void add() {
		ZCLinkSchema link = new ZCLinkSchema();
		link.setID(NoUtil.getMaxID("LinkID"));
		link.setValue(Request);
		String ImageID = $V("ImageID");
		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable dt = new QueryBuilder("select path,srcfilename from zcimage where id=?", Long.parseLong(ImageID)).executeDataTable();
			link.setImagePath((dt.get(0, "path").toString() + dt.get(0, "srcfilename")).replaceAll("//", "/").toString());
		} else {
			link.setImagePath("upload/Image/nopicture.jpg");
		}
		link.setOrderFlag(OrderUtil.getDefaultOrder());
		link.setSiteID(Application.getCurrentSiteID());
		link.setAddUser(User.getUserName());
		link.setAddTime(new Date());
		if (link.insert()) {
			Response.setLogInfo(1, "新增成功!");
		} else {
			Response.setLogInfo(0, "新增失败!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setLogInfo(0, "传入ID时发生错误！");
			return;
		}
		Transaction trans = new Transaction();
		ZCLinkSchema link = new ZCLinkSchema();
		ZCLinkSet set = link.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE);

		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功！");
		} else {
			Response.setLogInfo(0, "删除数据时发生错误!");
		}
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		DataTable dt = new QueryBuilder("select path,srcfilename from zcimage where id=?", Long.parseLong(ID)).executeDataTable();
		if (dt.getRowCount() > 0) {
			this.Response.put("picSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + dt.get(
					0, "srcfilename")).replaceAll("//", "/").toString());
		}
	}

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String linkGroupID = $V("LinkGroupID");
		if (!StringUtil.checkID(target) || !StringUtil.checkID(orders)) {
			return;
		}
		if (OrderUtil.updateOrder("ZCLink", type, target, orders, " LinkGroupID = " + linkGroupID)) {
			Response.setMessage("排序成功");
		} else {
			Response.setError("排序失败");
		}
	}

	public static void updateStatLink() {
		ZCPageBlockSet set = new ZCPageBlockSchema().query(new QueryBuilder(
				" where SiteID = ? and code like '%friendlink%'", Application.getCurrentSiteID()));
		PageGenerator p = new PageGenerator();
		p.staticPageBlock(set);
	}

}
