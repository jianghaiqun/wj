package com.wangjin.cms.orders;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.SDInformationAppntSchema;
import com.sinosoft.schema.SDInformationInsuredSchema;

public class QueryMember extends Page {
	public static Mapx initDialog5(Mapx params) {
		String orderSn = params.getString("orderSn");
		String recognizeeSn = params.getString("recognizeeSn");
		String informationSn = params.getString("informationSn");
		String productId = "";
		String insuranceCompany = "";
		if (StringUtils.isNotEmpty(orderSn)) {
			try {
				String sql = "select informationSn , productId ,insuranceCompany from SDInformation where orderSn='" + orderSn + "'";
				QueryBuilder qb = new QueryBuilder(sql);
				DataTable dt = qb.executeDataTable();
				if (null != dt & dt.getRowCount() > 0) {
					informationSn = dt.getString(0, 0);
					productId = dt.getString(0, 1);
					insuranceCompany = dt.getString(0, 2);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select a.name,a.id from area a,sdinformation b where 1=1 ");
		sb.append("and a.insuranceCompany = b.insuranceCompany ");
		sb.append("and (a.parent_id = '' or a.parent_id is null) ");
		sb.append("and ordersn='"+ orderSn +"' ");
		
		if(!"2011".equals(insuranceCompany)&&!"1061".equals(insuranceCompany)){
			sb.append("and a.productid = b.productid");
		}
		Mapx<String, Object> map = new Mapx<String, Object>();
		String sql = "select * from SDInformationAppnt where informationSn='"
				+ informationSn + "'";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		SDInformationAppntSchema app = new SDInformationAppntSchema();
		if (null != dt & dt.getRowCount() > 0) {
			app.setValue(dt.getDataRow(0));

			// 投保人姓名
			map.put("applicantName", app.getapplicantName());
			// 投保人性别
			map.put("applicantSex",
					HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select codeName,codeValue from dictionary where codeType = 'Sex' and insuranceCode = (select insuranceCompany from SDInformation where orderSn='"
											+ orderSn
											+ "' and informationSn='"
											+ informationSn + "')"), app
									.getapplicantSex()));
			// 投保人电话
			map.put("applicantMobile", app.getapplicantMobile());
			// 投保人出生日期
			map.put("applicantBirthday", app.getapplicantBirthday());
			// 投保人证件类型
			map.put("applicantIdentityType",
					HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select codeName,codeValue from dictionary where codeType = 'certificate' and insuranceCode = (select insuranceCompany from SDInformation where orderSn='"
											+ orderSn
											+ "' and informationSn='"
											+ informationSn + "')"), app
									.getapplicantIdentityType()));
			// 投保人证件号码
			map.put("applicantIdentityId", app.getapplicantIdentityId());
			// 投保人电子邮箱
			map.put("applicantMail", app.getapplicantMail());
			// 投保人所在省/直辖市
			if(StringUtil.isNotEmpty(app.getapplicantArea1())){
				map.put("applicantArea1",
						HtmlUtil.queryToOptions(new QueryBuilder(sb.toString()), app.getapplicantArea1()));
				// 投保人所在地级市/区
				if(StringUtil.isNotEmpty(app.getapplicantArea2())){
					map.put("applicantArea2",
							HtmlUtil.queryToOptions(
									new QueryBuilder(
											"select name,id from area where parent_id = '"+app.getapplicantArea1()+"'"),
											app.getapplicantArea2()));
				}
				map.put("appParentArea", app.getapplicantArea1());
			}
			map.put("id_appnt", app.getId());

		}
		// 被保人基本信息展示
		String sql_rec = "select * from SDInformationInsured where orderSn='"
				+ orderSn + "' and informationSn='" + informationSn
				+ "' and recognizeeSn='" + recognizeeSn + "'";
		QueryBuilder qb_rec = new QueryBuilder(sql_rec);
		DataTable dt_rec = qb_rec.executeDataTable();
		SDInformationInsuredSchema rec = new SDInformationInsuredSchema();
		// 被保人姓名
		if (null != dt_rec & dt_rec.getRowCount() > 0) {
			rec.setValue(dt_rec.getDataRow(0));
			// 被保人姓名
			map.put("recognizeeName", rec.getrecognizeeName());
			// 被保人性别

			map.put("recognizeeSex",
					HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select codeName,codeValue from dictionary where codeType = 'Sex' and insuranceCode = (select insuranceCompany from SDInformation where orderSn='"
											+ orderSn
											+ "' and informationSn='"
											+ informationSn + "')"), rec
									.getrecognizeeSex()));
			// 被保人电话
			map.put("recognizeeMobile", rec.getrecognizeeMobile());
			// 被保人出生日期
			map.put("recognizeeBirthday", rec.getrecognizeeBirthday());
			// 被保人证件类型
			map.put("recognizeeIdentityType",
					HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select codeName,codeValue from dictionary where codeType = 'certificate' and insuranceCode = (select insuranceCompany from SDInformation where orderSn='"
											+ orderSn
											+ "' and informationSn='"
											+ informationSn + "')"), rec
									.getrecognizeeIdentityType()));
			// 被保人证件号码
			map.put("recognizeeIdentityId", rec.getrecognizeeIdentityId());
			// 被保人电子邮箱
			map.put("recognizeeMail", rec.getrecognizeeMail());
			// 被保人所在省/直辖市
			if(StringUtil.isNotEmpty(rec.getrecognizeeArea1())){
				map.put("recognizeeArea1",
						HtmlUtil.queryToOptions(new QueryBuilder(sb.toString()), rec.getrecognizeeArea1()));
				if(StringUtil.isNotEmpty(rec.getrecognizeeArea2())){
					// 被保人所在地级市/区
					map.put("recognizeeArea2", HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select name,id from area where parent_id = '"+rec.getrecognizeeArea1()+"'"),
									rec.getrecognizeeArea2()));
				}
			}
			map.put("id_insured", rec.getId());
			map.put("orderSn", orderSn);
			map.put("informationSn", informationSn);
			map.put("productId", productId);

		}
		return map;
	}

	public void dg1Edit4() {
		String id_appnt = $V("id_appnt");
		// 投保人
		SDInformationAppntSchema informationAppntSchema = new SDInformationAppntSchema();
		// informationSchema.setValue(Request);
		informationAppntSchema.setId(id_appnt);
		informationAppntSchema.fill();
		informationAppntSchema.setValue(Request);

		String insuranceCompany = "";
		GetDBdata db0 = new GetDBdata();
		try {
			insuranceCompany = db0
					.getOneValue("select insuranceCompany from SDInformation where informationSn='"
							+ informationAppntSchema.getinformationSn() + "'");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// 将性别名称,证件名称同步修改 ---ysc
		GetDBdata db = new GetDBdata();
		try {
			String aResult = db
					.getOneValue("select codeName from dictionary where codeType = 'Sex' and codeValue = '"
							+ informationAppntSchema.getapplicantSex()
							+ "' and insuranceCode ='" + insuranceCompany + "'");
			String aResult1 = db
					.getOneValue("select codeName from dictionary where codeType = 'certificate' and codeValue = '"
							+ informationAppntSchema.getapplicantIdentityType()
							+ "' and insuranceCode ='" + insuranceCompany + "'");
			informationAppntSchema.setapplicantSexName(aResult);
			informationAppntSchema.setapplicantIdentityTypeName(aResult1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		informationAppntSchema.setId(id_appnt);

		// 被保人
		String id_insured = $V("id_insured");
		SDInformationInsuredSchema informationinsuredSchema = new SDInformationInsuredSchema();
		informationinsuredSchema.setId(id_insured);
		informationinsuredSchema.fill();
		informationinsuredSchema.setValue(Request);
		// 将性别名称,证件名称同步修改 ---ysc
		GetDBdata db1 = new GetDBdata();
		try {
			String rResult = db1
					.getOneValue("select codeName from dictionary where codeType = 'Sex' and codeValue = '"
							+ informationinsuredSchema.getrecognizeeSex()
							+ "' and insuranceCode ='" + insuranceCompany + "'");
			String rResult1 = db1
					.getOneValue("select codeName from dictionary where codeType = 'certificate' and codeValue = '"
							+ informationinsuredSchema
									.getrecognizeeIdentityType()
							+ "' and insuranceCode ='" + insuranceCompany + "'");
			informationinsuredSchema.setrecognizeeSexName(rResult);
			informationinsuredSchema.setrecognizeeIdentityTypeName(rResult1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		informationinsuredSchema.setId(id_insured);

		if (informationAppntSchema.update()
				&& informationinsuredSchema.update()) {
			//add by wangej 20130904  更新SDOrders修改时间
			editOrdersDate(informationinsuredSchema.getorderSn());
			Response.setLogInfo(1, "修改成功");
		} else {
			Response.setLogInfo(0, "修改" + informationAppntSchema.getId()
					+ "失败!");
		}
	}
	/**
	 * 修改订单信息 update moddate  by wangej 20130904
	 * 
	 * */
	public void editOrdersDate(String orderSn) {

				GetDBdata db = new GetDBdata();
				String updateSql = "update SDOrders set modifyDate = SYSDATE()  where orderSn = '"+orderSn+"'";
				try {
					db.execUpdateSQL(updateSql,null);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

	}
	
	
}
