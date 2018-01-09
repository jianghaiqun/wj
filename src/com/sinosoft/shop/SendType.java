package com.sinosoft.shop;

import java.util.Date;

import com.sinosoft.cms.pub.SiteUtil;
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
import com.sinosoft.schema.ZSSendSchema;
import com.sinosoft.schema.ZSSendSet;


public class SendType extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select * from ZSSend where siteid = ?";
		QueryBuilder qb = new QueryBuilder(sql, Application.getCurrentSiteID());
		dga.bindData(qb);
	}

	public void add() {
		ZSSendSchema send = new ZSSendSchema();
		send.setValue(Request);
		send.setID(NoUtil.getMaxNo("SendID"));
		send.setSiteID(Application.getCurrentSiteID());
		send.setAddUser(User.getUserName());
		send.setAddTime(new Date());

		String ImageID = $V("ImageID");
		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
					.executeDataTable();
			send.setProp1((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
					.toString());
		} else {
			send.setProp1("upload/Image/nopicture.jpg");
		}

		if (send.insert()) {
			Response.setStatus(1);
			Response.setMessage("新增配送项成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public void dg1Edit() {
		ZSSendSchema send = new ZSSendSchema();
		String ID = $V("ID");
		send.setID(ID);
		send.fill();
		send.setValue(Request);
		send.setModifyTime(new Date());
		send.setModifyUser(User.getUserName());

		String ImageID = $V("ImageID");
		String imagePath = $V("PicSrc1");
		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
					.executeDataTable();
			String path = (imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
					.toString();
			send.setProp1(path);
		} else {
			send.setProp1(imagePath);
		}

		if (send.update()) {
			Response.setLogInfo(1, "修改成功");
		} else {
			Response.setLogInfo(0, "保存失败");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		ids = ids.replaceAll(",", "','");
		Transaction trans = new Transaction();
		ZSSendSchema send = new ZSSendSchema();
		ZSSendSet set = send.query(new QueryBuilder("where ID in ('" + ids + "')"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public static Mapx initEditDialog(Mapx params) {
		String ID = params.getString("ID");
		ZSSendSchema send = new ZSSendSchema();
		if (StringUtil.isNotEmpty(ID)) {
			send.setID(ID);
			send.fill();
			Mapx map = send.toMapx();
			map.put("PicSrc1", send.getProp1());
			map.put("Prop1", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + send.getProp1()).replaceAll("//", "/"));
			return map;
		} else {
			params.put("Prop1", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + "upload/Image/nopicture.jpg")
					.replaceAll("//", "/"));
			return params;
		}

	}
}
