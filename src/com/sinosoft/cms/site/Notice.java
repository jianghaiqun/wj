package com.sinosoft.cms.site;

import com.sinosoft.cms.template.PageGenerator;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCLinkSchema;
import com.sinosoft.schema.ZCLinkSet;
import com.sinosoft.schema.ZCPageBlockSchema;
import com.sinosoft.schema.ZCPageBlockSet;

import java.util.Date;

/**
 * 导航管理
 * 
 */

public class Notice extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select e.*,(select name from zclinkgroup where id=e.linkgroupID) as LinkGroupName,(select a.CodeName from zdcode a where CodeType='NoticeType' and a.codevalue=e.Prop1)as NoticeTypeName from ZCLink  e  ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.append(" where exists (select '' from ZCLinkGroup where ID=e.LinkGroupID and name='最新公告' and SiteID=?)");
		qb.add(Application.getCurrentSiteID());
		qb.append(" order by OrderFlag desc,id desc");
		dga.bindData(qb);

	}

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		if (StringUtil.isNotEmpty(ID)) {
			ZCLinkSchema link = new ZCLinkSchema();
			link.setID(ID);
			link.fill();
			Mapx map = link.toMapx();
			map.put("NoticeType", link.getProp1());
			map.put("NoticeLink", link.getProp2());
			map.put("NoticeType", HtmlUtil.codeToRadios("NoticeType", "NoticeType", link.getProp1(), false, false));
			map.put("NoticeLink", HtmlUtil.codeToRadios("NoticeLink", "NoticeLink", link.getProp2(), false, false));
			return map;
		} else {
			params.put("URL", "http://");
			
			params.put("NoticeType", HtmlUtil.codeToRadios("NoticeType", "NoticeType","latest"));
			params.put("NoticeLink", HtmlUtil.codeToRadios("NoticeLink", "NoticeLink","_blank"));
			return params;
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
		link.setProp1($V("NoticeType"));
		link.setProp2($V("NoticeLink"));
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
		String linkGroupId = "";
		try {
			GetDBdata db = new GetDBdata();
			linkGroupId = db
					.getOneValue("select id from zclinkgroup where name='最新公告'");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		ZCLinkSchema link = new ZCLinkSchema();
		link.setID(NoUtil.getMaxID("LinkID"));
		link.setValue(Request);
		link.setLinkGroupID(linkGroupId);
		link.setProp1($V("NoticeType"));
		link.setProp2($V("NoticeLink"));
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
		ZCLinkSet set = link
				.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE);

		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功！");
		} else {
			Response.setLogInfo(0, "删除数据时发生错误!");
		}
	}
	public void save() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			QueryBuilder qb = new QueryBuilder("update ZCLink set Orderflag=?,ModifyUser=?,ModifyTime=? where ID=?");
			qb.add(dt.getString(i, "Orderflag"));
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

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String linkGroupId = "";
		try {
			GetDBdata db = new GetDBdata();
			linkGroupId = db
					.getOneValue("select id from zclinkgroup where name='最新公告'");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (!StringUtil.checkID(target) || !StringUtil.checkID(orders)) {
			return;
		}
		if (OrderUtil.updateOrder("ZCLink", type, target, orders, " LinkGroupID = " + linkGroupId)) {
			Response.setMessage("排序成功");
		} else {
			Response.setError("排序失败");
		}
	}


	public static void updateStatLink() {
		ZCPageBlockSet set = new ZCPageBlockSchema().query(new QueryBuilder(
				" where SiteID = ? and code like '%friendlink%'", Application
						.getCurrentSiteID()));
		PageGenerator p = new PageGenerator();
		p.staticPageBlock(set);
	}

}
