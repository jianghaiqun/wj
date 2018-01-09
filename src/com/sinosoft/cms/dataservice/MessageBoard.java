package com.sinosoft.cms.dataservice;

import java.util.Date;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCBoardMessageSchema;
import com.sinosoft.schema.ZCMessageBoardSchema;

public class MessageBoard extends Page {

	public static void treeDataBind(TreeAction ta) {
		long siteID = Application.getCurrentSiteID();
		DataTable dt = null;
		QueryBuilder qb = new QueryBuilder("select * from ZCMessageBoard where SiteID=? order by ID desc", siteID);
		dt = qb.executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				if (Priv.getPriv(User.getUserName(), Priv.SITE, Application.getCurrentSiteID() + "", Priv.SITE_MANAGE)) {
					return true;
				}
				DataRow dr = (DataRow) obj;
				String RelaCatalogID = dr.getString("RelaCatalogID");
				if ("0".equals(RelaCatalogID)) {
					return Priv.getPriv(User.getUserName(), Priv.SITE, Application.getCurrentSiteID() + "",
							Priv.ARTICLE_MANAGE);
				} else {
					return Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(RelaCatalogID),
							Priv.ARTICLE_MODIFY);
				}
			}
		});
		ta.setRootText("留言板列表");
		ta.setLeafIcon("Icons/icon034a1.gif");
		ta.bindData(dt);
	}

	public static void MessageDataBind(DataListAction dla) {
		String BoardID = dla.getParam("BoardID");
		if (StringUtil.isEmpty(BoardID)) {
			BoardID = "0";
		}
		String ReplyFlag = dla.getParam("ReplyFlag");
		String PublishFlag = dla.getParam("PublishFlag");
		QueryBuilder qb = new QueryBuilder("select * from ZCBoardMessage where BoardID=?", Long.parseLong(BoardID));
		if (StringUtil.isNotEmpty(ReplyFlag)) {
			qb.append(" and ReplyFlag =?", ReplyFlag);
		}
		if (StringUtil.isNotEmpty(PublishFlag)) {
			qb.append(" and PublishFlag =?", PublishFlag);
		}
		dla.setTotal(qb);
		qb.append(" order by AddTime DESC");
		Mapx map = new Mapx();
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("ReplyAreaDisplay");
		dt.insertColumn("ReplyContentAreaDisplay");
		dt.insertColumn("DO");
		dt.insertColumn("PublishFlagName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "ReplyFlag").equals("N") || StringUtil.isEmpty(dt.getString(i, "ReplyContent"))) {
				dt.set(i, "ReplyAreaDisplay", "");
				dt.set(i, "ReplyContentAreaDisplay", "none");
			} else {
				dt.set(i, "ReplyAreaDisplay", "none");
				dt.set(i, "ReplyContentAreaDisplay", "");
				dt.set(i, "DO", "<div id='DO_" + dt.getString(i, "ID") + "'><input type='button' onClick='editReply("
						+ dt.getString(i, "ID")
						+ ")' value='修改'/>&nbsp;<input type='button' value='删除' onClick='delReply("
						+ dt.getString(i, "ID") + ")' /></div>");
			}
			if (dt.getString(i, "PublishFlag").equals("Y")) {
				dt.set(i, "PublishFlagName", "<font color='#00ff00'>审核通过</font>");
			} else {
				dt.set(i, "PublishFlagName", "<font color='#ffcc00'>等待审核</font>");
			}
		}
		map.put("Y", "已回复");
		map.put("N", "未回复");
		dt.decodeColumn("ReplyFlag", map);
		if (dt.getRowCount() == 0) {
			dt.insertRow((Object[]) null);
			dt.set(0, "ID", "0");
		}
		dla.bindData(dt);
	}

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		if (StringUtil.isNotEmpty(ID)) {
			ZCMessageBoardSchema messageboard = new ZCMessageBoardSchema();
			messageboard.setID(ID);
			messageboard.fill();
			params.putAll(messageboard.toMapx());
			params.put("RadioIsOpen", HtmlUtil.codeToRadios("IsOpen", "YesOrNo", messageboard.getIsOpen()));
		} else {
			params.put("RadioIsOpen", HtmlUtil.codeToRadios("IsOpen", "YesOrNo", "N"));
		}
		return params;
	}

	public static Mapx initMessageDialog(Mapx params) {
		String ID = params.getString("ID");
		if (StringUtil.isNotEmpty(ID)) {
			ZCBoardMessageSchema message = new ZCBoardMessageSchema();
			message.setID(ID);
			message.fill();
			params.putAll(message.toMapx());
		}
		return params;
	}

	public void save() {
		String ID = $V("ID");
		String Name = $V("Name");
		String Description = $V("Description");
		String IsOpen = $V("IsOpen");
		ZCMessageBoardSchema messageboard = new ZCMessageBoardSchema();
		Transaction trans = new Transaction();
		boolean exists = false;
		if (StringUtil.isNotEmpty(ID)) {
			messageboard.setID(ID);
			messageboard.fill();
			messageboard.setModifyTime(new Date());
			messageboard.setModifyUser(User.getUserName());
			exists = true;
		} else {
			messageboard.setID(NoUtil.getMaxID("MessageBoardID"));
			messageboard.setSiteID(Application.getCurrentSiteID());
			messageboard.setAddTime(new Date());
			messageboard.setAddUser(User.getUserName());
		}
		if (!Name.equals(messageboard.getName())) {
			if (new QueryBuilder("select Count(*) from ZCMessageBoard where Name = ? and SiteID = ?", Name, Application.getCurrentSiteID()).executeInt() > 0) {
				Response.setLogInfo(0, "已有相同名称留言板，请更换名称");
				return;
			}
		}
		messageboard.setName(Name);
		messageboard.setDescription(Description);
		messageboard.setIsOpen(IsOpen);
		messageboard.setRelaCatalogID($V("RelaCatalogID"));
		if (exists) {
			trans.add(messageboard, Transaction.UPDATE);
		} else {
			trans.add(messageboard, Transaction.INSERT);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功");
		} else {
			Response.setLogInfo(0, "保存失败");
		}
	}

	public void saveReply() {
		String ID = $V("MsgID");
		String ReplyContent = $V("ReplyContent");
		ZCBoardMessageSchema message = new ZCBoardMessageSchema();
		message.setID(ID);
		message.fill();
		if (StringUtil.isEmpty(ReplyContent) && StringUtil.isEmpty(message.getReplyContent())) {
			message.setReplyFlag("N");
		} else {
			message.setReplyFlag("Y");
		}
		message.setReplyContent(ReplyContent);
		message.setModifyTime(new Date());
		message.setModifyUser(User.getUserName());
		if (message.update()) {
			Response.setLogInfo(1, "回复成功");
		} else {
			Response.setLogInfo(0, "回复失败");
		}
	}

	public void delReply() {
		String MsgID = $V("MsgID");
		ZCBoardMessageSchema boardmessage = new ZCBoardMessageSchema();
		boardmessage.setID(MsgID);
		boardmessage.fill();
		boardmessage.setReplyContent("");
		if (boardmessage.update()) {
			Response.setLogInfo(1, "删除回复成功");
		} else {
			Response.setLogInfo(0, "删除回复失败");
		}
	}

	public void del() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		Transaction trans = new Transaction();
		ZCMessageBoardSchema board = new ZCMessageBoardSchema();
		ZCBoardMessageSchema message = new ZCBoardMessageSchema();
		for (int i = 0; i < ids.length; i++) {
			trans.add(board.query(new QueryBuilder(" where ID = ?", Long.parseLong(ids[i]))), Transaction.DELETE_AND_BACKUP);
			trans.add(message.query(new QueryBuilder(" where BoardID = ?", Long.parseLong(ids[i]))), Transaction.DELETE_AND_BACKUP);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}

	public void delMessage() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		Transaction trans = new Transaction();
		ZCBoardMessageSchema boardmessage = new ZCBoardMessageSchema();
		for (int i = 0; i < ids.length; i++) {
			trans.add(boardmessage.query(new QueryBuilder(" where ID = ?", Long.parseLong(ids[i]))), Transaction.DELETE_AND_BACKUP);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}

	public void doCheck() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		Transaction trans = new Transaction();
		ZCBoardMessageSchema boardmessage = new ZCBoardMessageSchema();
		for (int i = 0; i < ids.length; i++) {
			boardmessage = new ZCBoardMessageSchema();
			boardmessage.setID(ids[i]);
			boardmessage.fill();
			boardmessage.setPublishFlag("Y");
			trans.add(boardmessage, Transaction.UPDATE);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "审核成功");
		} else {
			Response.setLogInfo(0, "审核失败");
		}
	}

}
