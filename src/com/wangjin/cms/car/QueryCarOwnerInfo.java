package com.wangjin.cms.car;


import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
public class QueryCarOwnerInfo extends Page {
	/**
	 * 
	* @Title: init 
	* @Description: TODO(初始化页面信息) 
	* @return Mapx<String,String>    返回类型 
	* @author XXX
	 */
	public static Mapx<String, String> init(Mapx<String, String> params) {
		params.put("prop1Init", HtmlUtil.codeToOptions("CarCompany", true));//车险公司
		return params;
	}
	/**
	 * 
	* @Title: ownerInfoInquery 
	* @Description: TODO(查询车主信息) 
	* @return void    返回类型 
	* @author XXX
	 */
	public void ownerInfoInquery(DataGridAction dga) {
		//保险公司
		String prop1 = (String) dga.getParams().get("prop1");
		//车主姓名
		String CarOwner = (String) dga.getParams().get("CarOwner");
		//联系电话
		String ContactPhone = (String) dga.getParams().get("ContactPhone");
		//邮箱
		String ContactEmail = (String) dga.getParams().get("ContactEmail");
		//省份
		String prop2 = (String) dga.getParams().get("prop2");
		//城市
		String Address = (String) dga.getParams().get("Address");
		
		String startdate = (String) dga.getParams().get("startdate");
		String starttime = (String) dga.getParams().get("starttime");
		String enddate = (String) dga.getParams().get("enddate");
		String endtime = (String) dga.getParams().get("endtime");
		QueryBuilder sql = new QueryBuilder ("SELECT ID,Address,PlateNo,InsuranceDate,BuyDate,CarValue,CarOwner,ContactPhone,ContactEmail,CarProperty,CreateDate,Prop1,Prop2 FROM sdcartransition where  1=1");
		if (StringUtil.isNotEmpty(prop1)) {
			sql.append(" and prop1 like " + "'%" + prop1 + "%'");
		}
		if (StringUtil.isNotEmpty(CarOwner)) {
			sql.append(" and CarOwner like " + "'%" + CarOwner + "%'");
		}
		if (StringUtil.isNotEmpty(ContactPhone)) {
			sql.append(" and ContactPhone like " + "'%" + ContactPhone + "%'");
		}
		if (StringUtil.isNotEmpty(prop2)) {
			sql.append(" and prop2 like " + "'%" + prop2 + "%'");
		}
		if (StringUtil.isNotEmpty(Address)) {
			sql.append(" and Address like " + "'%" + Address + "%'");
		}
		if (StringUtil.isNotEmpty(ContactEmail)) {
			sql.append(" and ContactEmail like " + "'%" + ContactEmail + "%'");
		}
		// 活动开始时间查询
		if (StringUtil.isNotEmpty(startdate)) {
			if (StringUtil.isEmpty(starttime)) {
				starttime = "00:00:00";
			} else {
				if (starttime.length() == 7) {
					starttime = "0" + starttime;
				}
			}
			sql.append(" and UNIX_TIMESTAMP(DATE_FORMAT(CreateDate,'%Y-%m-%d %H:%i:%s')) >=  UNIX_TIMESTAMP( '"+ startdate.trim() + " " + starttime.trim() + "')");
		}
		// 活动结束时间
		if (StringUtil.isNotEmpty(enddate)) {
			if (StringUtil.isEmpty(endtime)) {
				endtime = "23:59:59";
			} else {
				if (endtime.length() == 7) {
					endtime = "0" + endtime;
				}
			}
			sql.append(" and UNIX_TIMESTAMP(DATE_FORMAT(CreateDate,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP('"+ enddate.trim() + " " + endtime.trim() + "')");
		}
		sql.append("  ORDER BY createdate DESC ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (StringUtil.isEmpty(dt.getString(i, "Prop2"))) {
				dt.set(i, "Prop2", "无省份");
			} else {
				dt.set(i, "Prop2", dt.getString(i, "Prop2"));
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}
}
