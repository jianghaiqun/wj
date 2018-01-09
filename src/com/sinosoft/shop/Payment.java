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
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZSPaymentPropSchema;
import com.sinosoft.schema.ZSPaymentPropSet;
import com.sinosoft.schema.ZSPaymentSchema;
import com.sinosoft.schema.ZSPaymentSet;

public class Payment extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from ZSPayment where siteid = ?", Application.getCurrentSiteID());
		dga.setTotal(new QueryBuilder("select count(1) from ZSPayment where siteid = ?", Application.getCurrentSiteID()));
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("PmtLink", dt.getColumnValues("Name"));
		Mapx map = new QueryBuilder("select CodeName, CodeValue from zdcode where ParentCode='Payment'").executeDataTable().toMapx("CodeName", "CodeValue");
		dt.decodeColumn("PmtLink", map);
		dga.bindData(dt);
	}

	public void add() {
		ZSPaymentSchema payment = new ZSPaymentSchema();
		payment.setValue(Request);
		payment.setID(NoUtil.getMaxNo("paymentID"));
		payment.setSiteID(Application.getCurrentSiteID());
		payment.setAddUser("");
		payment.setAddTime(new Date());

		String ImageID = $V("ImageID");
		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
					.executeDataTable();
			payment.setImagePath((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
					.toString());
		} else {
			payment.setImagePath("upload/Image/nopicture.jpg");
		}

		if (payment.insert()) {
			Response.setStatus(1);
			Response.setMessage("新增支付方式信息项成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public void dg1Edit() {
		ZSPaymentSchema payment = new ZSPaymentSchema();
		String ID = $V("ID");
		payment.setID(ID);
		payment.fill();
		payment.setValue(Request);
		payment.setModifyTime(new Date());
		payment.setModifyUser(User.getUserName());

		String ImageID = $V("ImageID");
		String imagePath = $V("PicSrc1");
		if (StringUtil.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
					.executeDataTable();
			String path = (imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
					.toString();
			payment.setImagePath(path);
		} else {
			payment.setImagePath(imagePath);
		}

		if (payment.update()) {
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
		ZSPaymentSchema payment = new ZSPaymentSchema();
		ZSPaymentSet set = payment.query(new QueryBuilder("where ID in ('" + ids + "')"));
		trans.add(set, Transaction.DELETE);
		
		ZSPaymentPropSet props = new ZSPaymentPropSchema().query(new QueryBuilder("where PaymentID in ('" + ids + "')"));
		trans.add(props, Transaction.DELETE);
		
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public static Mapx initDialog(Mapx params) {
		params.put("PaymentOptions", HtmlUtil.codeToOptions("Payment", false));
		String ID = params.getString("ID");
		if (StringUtil.isNotEmpty(ID)) {
			ZSPaymentSchema zspayment = new ZSPaymentSchema();
			zspayment.setID(ID);
			zspayment.fill();
			Mapx map = zspayment.toMapx();
			map.put("PicSrc1", zspayment.getImagePath());
			map.put("ImagePath", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + zspayment.getImagePath()).replaceAll("//",
					"/"));
			return map;
		} else {
			params.put("ImagePath", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + "upload/Image/nopicture.jpg")
					.replaceAll("//", "/"));
			return params;
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
}
