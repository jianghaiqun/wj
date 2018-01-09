package com.sinosoft.shop;

import java.util.Date;

import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZSPaymentPropSchema;
import com.sinosoft.schema.ZSPaymentPropSet;
import com.sinosoft.schema.ZSPaymentSchema;
import com.sinosoft.schema.ZSPaymentSet;

public class PaymentService extends Page {

	public void save() {
		String paymentName = new QueryBuilder("select CodeName from ZDCode where ParentCode='Payment' and CodeValue=?", 
				Request.get("PaymentPath")).executeOneValue().toString();
		ZSPaymentSet payments = new ZSPaymentSchema().query(new QueryBuilder("where SiteID = ? and name = ?", 
				Application.getCurrentSiteID(), paymentName));
		if(payments.size() > 0) {
			ZSPaymentSchema payment = payments.get(0);
			payment.setValue(Request);
			payment.setModifyUser(User.getUserName());
			payment.setModifyTime(new Date());
			
			String ImageID = $V("ImageID");
			if (StringUtil.isNotEmpty(ImageID)) {
				DataTable imageDT = new QueryBuilder("select path,srcfilename from zcimage where id = ? ", ImageID)
						.executeDataTable();
				payment.setImagePath((imageDT.get(0, "path").toString() + imageDT.get(0, "srcfilename")).replaceAll("//", "/")
						.toString());
			} else {
				payment.setImagePath("upload/Image/nopicture.jpg");
			}
			
			//支付方式属性(id以Pmt_为前缀的input值)
			Object[] reqKeys = Request.keyArray();
			for(int i = 0; i < reqKeys.length; i++) {
				if(reqKeys[i].toString().split("_")[0].equals("Pmt")) {
					ZSPaymentPropSet pmtProp = new ZSPaymentPropSchema().query(new QueryBuilder("where PropName = ? and PaymentID = ?", Request.get(reqKeys[i]), payment.getID()));
					//存在属性则更新，不存在添加
					if(pmtProp.size() > 0) {
						pmtProp.get(0).setPropName(reqKeys[i].toString().split("_")[1]);
						pmtProp.get(0).setPropValue(Request.getString(reqKeys[i]));
						pmtProp.get(0).setModifyUser(User.getUserName());
						pmtProp.get(0).setModifyTime(new Date());
						pmtProp.get(0).update();
					} else {
						ZSPaymentPropSchema prop = new ZSPaymentPropSchema();
						prop.setID(NoUtil.getMaxNo("PaymentPropID"));
						prop.setPaymentID(payment.getID());
						prop.setPropName(reqKeys[i].toString().split("_")[1]);
						prop.setPropValue(Request.getString(reqKeys[i]));
						prop.setAddUser(User.getUserName());
						prop.setAddTime(new Date());
						prop.insert();
					}
				}
			}
			
			if (payment.update()) {
				Response.setStatus(1);
				Response.setMessage("保存成功！");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		} else {
			ZSPaymentSchema payment = new ZSPaymentSchema();
			payment.setValue(Request);
			payment.setID(NoUtil.getMaxNo("paymentID"));
			payment.setName(paymentName);
			payment.setSiteID(Application.getCurrentSiteID());
			payment.setAddUser(User.getUserName());
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
			
			//支付方式属性(id以Pmt_为前缀的input值)
			Object[] reqKeys = Request.keyArray();
			for(int i = 0; i < reqKeys.length; i++) {
				if(reqKeys[i].toString().split("_")[0].equals("Pmt")) {
					ZSPaymentPropSet pmtProp = new ZSPaymentPropSchema().query(new QueryBuilder("where PropName = ? and PaymentID = ?", Request.get(reqKeys[i]), payment.getID()));
					//存在属性则更新，不存在添加
					if(pmtProp.size() > 0) {
						pmtProp.get(0).setPropName(reqKeys[i].toString().split("_")[1]);
						pmtProp.get(0).setPropValue(Request.getString(reqKeys[i]));
						pmtProp.get(0).setModifyUser(User.getUserName());
						pmtProp.get(0).setModifyTime(new Date());
						pmtProp.get(0).update();
					} else {
						ZSPaymentPropSchema prop = new ZSPaymentPropSchema();
						prop.setID(NoUtil.getMaxNo("PaymentPropID"));
						prop.setPaymentID(payment.getID());
						prop.setPropName(reqKeys[i].toString().split("_")[1]);
						prop.setPropValue(Request.getString(reqKeys[i]));
						prop.setAddUser(User.getUserName());
						prop.setAddTime(new Date());
						prop.insert();
					}
				}
			}
			
			if (payment.insert()) {
				Response.setStatus(1);
				Response.setMessage("保存成功！");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		}
	}
	
	public static Mapx init(Mapx params) {
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
			ZSPaymentPropSet props = new ZSPaymentPropSchema().query(new QueryBuilder("where PaymentID = ?", ID));
			for(int i = 0; i < props.size(); i++) {
				map.put(props.get(i).getPropName(), props.get(i).getPropValue());
			}
			map.put("PaymentOptions", HtmlUtil.codeToOptions("Payment", 
					StringUtil.isEmpty(params.get("PaymentSelect").toString()) ? "Shop/PaymentService/PmtAliPay.jsp" : params.get("PaymentSelect").toString(), 
					false));
			return map;
		} else {
			params.put("PaymentOptions", HtmlUtil.codeToOptions("Payment", 
					StringUtil.isEmpty(params.get("PaymentSelect").toString()) ? "Shop/PaymentService/PmtAliPay.jsp" : params.get("PaymentSelect").toString(), 
					false));
			params.put("ImagePath", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + "upload/Image/nopicture.jpg")
					.replaceAll("//", "/"));
			return params;
		}
	}
	
	public void getPicSrc() {
		String ID = $V("PicID");
		DataTable dt = new QueryBuilder("select path,srcfilename from zcimage where id=?",Long.parseLong(ID)).executeDataTable();
		if (dt.getRowCount() > 0) {
			this.Response.put("picSrc", (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + dt.get(
					0, "srcfilename")).replaceAll("//", "/").toString());
		}
	}
}
