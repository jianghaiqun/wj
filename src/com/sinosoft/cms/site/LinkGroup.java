package com.sinosoft.cms.site;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.controls.TreeItem;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCLinkGroupSchema;
import com.sinosoft.schema.ZCLinkGroupSet;
import com.sinosoft.schema.ZCLinkSchema;
import com.sinosoft.schema.ZCLinkSet;


/**
 * 链接分组管理-------全维智码平台wisePlatform)样板程序
 * 
 */

public class LinkGroup extends Page {

	// 链接分类类型
	public static final String TYPE_TEXT = "text";

	public static final String TYPE_IMAGE = "image";

	public final static Mapx TYPE_MAP = new Mapx();

	static {
		TYPE_MAP.put(TYPE_TEXT, "文字链接");
		TYPE_MAP.put(TYPE_IMAGE, "图片链接");
	}

	public static void dg1DataBind(DataGridAction dga) {
		String LinkTeamID = dga.getParam("id");
		String sql = "select id,name,Type from ZCLinkGroup";
		QueryBuilder qb = new QueryBuilder(sql);
		if (StringUtil.isNotEmpty(LinkTeamID)) {
			qb.append(" where Prop1=? and SiteID=? order by OrderFlag asc,id desc");
			qb.add(Long.parseLong(LinkTeamID));
			qb.add(Application.getCurrentSiteID());
		} 
		else {
			qb.append(" where SiteID=?");
			qb.add(Application.getCurrentSiteID());
		}
		DataTable dt = qb.executeDataTable();
		dt.decodeColumn("Type", TYPE_MAP);
		dga.bindData(qb);
	}
	
	public static void treeDataBind(TreeAction ta) {
		long siteID = Application.getCurrentSiteID();
		DataTable dt = null;
		QueryBuilder qb = new QueryBuilder("select ID,Name from ZCLinkTeam Where SiteID=? order by ID", siteID);
		dt = qb.executeDataTable();
		ta.setRootText("热点词汇库");
		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
		}
	}
	

	public static Mapx initDialog(Mapx params) {
		params.put("Type", HtmlUtil.mapxToRadios("Type", TYPE_MAP, TYPE_TEXT));
		return params;
	}
	
	public void update() {
		Transaction trans = new Transaction();
		update(trans, Request);
		
	}
	/**
	 * modify by liuc 站点管理友情链接 点击修改时可修改链接分组名称及该链接所在菜单栏
	 * @param trans
	 * @param dc
	 */
	public void update (Transaction trans, DataCollection dc){
		String nameold = dc.getString("NAME");
		String name=dc.getString("LinkName");
		String oldid=dc.getString("OLDID");
		String logMessage = "保存成功！";
		
		if (!nameold.equals(name)){
			DataTable dt = new QueryBuilder("select * from zclinkgroup where name=?", nameold).executeDataTable();
			ArrayList list = new ArrayList();
			for (int i = 0; i < dt.getRowCount(); i++) {
				DataTable checkdt = new QueryBuilder("select * from zclinkgroup where name=?", name)
						.executeDataTable();
				if (checkdt.getRowCount() > 0) {
					list.add(dt.getString(i, "Name"));
					continue;
				}
				else {
					QueryBuilder qb = new QueryBuilder("update ZCLinkGroup set Name=?,ModifyUser=?,ModifyTime=? where ID=?");
					qb.add(name);
					qb.add(User.getUserName());
					qb.add(new Date());
					qb.add(Long.parseLong(dt.getString(i, "ID")));
					trans.add(qb);
				}
			}
			if (list.size() > 0) {
				logMessage += "链接分类名称";
				logMessage += StringUtil.join(list.toArray(), "、");
				logMessage += "已存在,请更换...";
			}
		}
		
		
		String Menus = dc.getString("MENU");
		
		if (!StringUtil.isEmpty(Menus)) {
			
		
		String[] ids = Menus.split(",");
			 for (int i = 0; i < ids.length; i++) {
				if (StringUtil.isEmpty(ids[i])) {
					continue;
				}
				QueryBuilder qb = new QueryBuilder("update ZCLinkGroup set prop1=?,ModifyUser=?,ModifyTime=? where ID=?");
				qb.add(ids[i]);
				qb.add(User.getUserName());
				qb.add(new Date());
				qb.add(oldid);
				trans.add(qb);
			}
		}

		if (trans.commit()) {
			Response.setLogInfo(1, logMessage);
		} else {
			Response.setLogInfo(0, "保存失败！");
		}
	}
	
	public void save() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		String logMessage = "保存成功！";
		ArrayList list = new ArrayList();
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataTable checkdt = new QueryBuilder("select * from zclinkgroup where name=?", dt.getString(i, "Name"))
					.executeDataTable();
			if (checkdt.getRowCount() > 0) {
				list.add(dt.getString(i, "Name"));
				continue;
			}
			QueryBuilder qb = new QueryBuilder("update ZCLinkGroup set Name=?,ModifyUser=?,ModifyTime=? where ID=?");
			qb.add(dt.getString(i, "Name"));
			qb.add(User.getUserName());
			qb.add(new Date());
			qb.add(Long.parseLong(dt.getString(i, "ID")));
			trans.add(qb);
		}
		if (list.size() > 0) {
			logMessage += "链接分类名称";
			logMessage += StringUtil.join(list.toArray(), "、");
			logMessage += "已存在,请更换...";
		}
		if (trans.commit()) {
			Response.setLogInfo(1, logMessage);
		} else {
			Response.setLogInfo(0, "保存失败！");
		}
	}

	public void add() {
		String name = $V("Name");
		String prop1 = $V("cid");
		DataTable dt = new QueryBuilder("select * from zclinkgroup where name=?", name).executeDataTable();
		if (dt.getRowCount() > 0) {
			Response.setLogInfo(0, "该链接分类名称已存在，请更换...");
			return;
		}
		ZCLinkGroupSchema linkGroup = new ZCLinkGroupSchema();
		linkGroup.setValue(Request);
		linkGroup.setID(NoUtil.getMaxID("LinkGroupID"));
		linkGroup.setOrderFlag(OrderUtil.getDefaultOrder());
		linkGroup.setSiteID(Application.getCurrentSiteID());
		linkGroup.setProp1(prop1);
		linkGroup.setAddTime(new Date());
		linkGroup.setAddUser(User.getUserName());
		if (linkGroup.insert()) {
			Response.setLogInfo(1, "新增成功");
		} else {
			Response.setLogInfo(0, "新增" + linkGroup.getName() + "失败!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setLogInfo(0, "传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCLinkGroupSchema linkGroup = new ZCLinkGroupSchema();
		ZCLinkGroupSet set = linkGroup.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE);

		// 删除分组下的所有链接
		for (int i = 0; i < set.size(); i++) {
			linkGroup = set.get(i);
			ZCLinkSchema link = new ZCLinkSchema();
			link.setLinkGroupID(linkGroup.getID());
			ZCLinkSet LinkSet = link.query();
			trans.add(LinkSet, Transaction.DELETE);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}
}
