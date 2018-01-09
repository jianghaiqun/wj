/**
 * Project Name:wj
 * File Name:OrderManage.java
 * Package Name:com.sinosoft.cms.travel
 * Date:2015年12月30日下午7:20:46
 * Copyright (c) 2015, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.cms.travel;

import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.OtOrderInfoSchema;

/**
 * ClassName:OrderManage <br/>
 * Function:TODO ADD 订单管理. <br/>
 * Date:2015年12月30日 下午7:20:46 <br/>
 *
 * @author:郭斌
 */
public class OrderManage extends Page {

	/**
	 * 
	 * init:(订单管理初始化). <br/>
	 *
	 * @author 郭斌
	 * @param params
	 * @return
	 */
	public static Mapx<String, String> init(Mapx<String, String> params) {

		params.put("startDate", DateUtil.getFirstDayOfMonth(DateUtil.toString(new Date())));
		params.put("endDate", DateUtil.toString(new Date()));
		params.put("orderStatus", HtmlUtil.codeToOptions("TripOrderStatus", true));
		return params;
	}

	/**
	 * 
	 * initEditInfo:(订单修改初始化). <br/>
	 *
	 * @author 郭斌
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx<String, String> initEditInfo(Mapx<String, String> params) {

		String id = params.getString("id");

		OtOrderInfoSchema tOtOrderInfo = new OtOrderInfoSchema();
		tOtOrderInfo.setid(id);
		if (tOtOrderInfo.fill()) {
			params.putAll(tOtOrderInfo.toMapx());
			params.put("travelDate", tOtOrderInfo.gettravelDate() + "");
		}

		params.put("sexList", HtmlUtil.codeToOptions("Gender", false));
		params.put("identityList", HtmlUtil.codeToOptions("OtProduct.IdentityType", false));

		return params;
	}

	/**
	 * 
	 * orderInquery:(订单修改出行人查询). <br/>
	 *
	 * @author 郭斌
	 * @param dga
	 */
	public void initEditInfo2(DataGridAction dga) {

		String id = dga.getParams().getString("id");
		String sql = " select o2.*  ";
		sql += "  from  OtOrderInfo o1,OtTravelPeopleInfo o2 ";
		sql += " where o1.ordersn=o2.ordersn and o1.id=?";
		QueryBuilder qb = new QueryBuilder(sql, id);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 
	 * orderInquery:(订单查询). <br/>
	 *
	 * @author 郭斌
	 * @param dga
	 */
	public void orderInquery(DataGridAction dga) {

		String orderSn = dga.getParams().getString("orderSn");
		String contactName = dga.getParams().getString("contactName");
		String productName = dga.getParams().getString("productName");
		String orderStatus = dga.getParams().getString("orderStatus");
		String startDate = dga.getParams().getString("startDate");
		String endDate = dga.getParams().getString("endDate");

		String sql = " select id,orderSn,codeName as orderStatusName,totalPrice,travelNum,productName,contactName,contactPhone";
		sql += "  from OtOrderInfo , zdcode   where codetype='TripOrderStatus' and orderStatus=codevalue and ParentCode='TripOrderStatus' ";
		// 订单号
		if (StringUtil.isNotEmpty(orderSn)) {
			sql += " and orderSn like '%" + orderSn.trim() + "%'";

		}
		// 联系人姓名
		if (StringUtil.isNotEmpty(contactName)) {
			sql += " and contactName like '%" + contactName.trim() + "%'";

		}
		// 产品名称
		if (StringUtil.isNotEmpty(productName)) {
			sql += " and productName like '%" + productName.trim() + "%'";

		}
		// 订单状态
		if (StringUtil.isNotEmpty(orderStatus)) {
			sql += " and orderStatus = '" + orderStatus.trim() + "'";

		}

		// 开始时间
		if (StringUtil.isNotEmpty(startDate)) {
			sql += " and createdate >= '" + startDate.trim() + " 00:00:00'";

		}
		// 结束时间
		if (StringUtil.isNotEmpty(endDate)) {
			sql += " and createdate <= '" + endDate.trim() + " 23:59:59'";

		}

		sql += " order by modifyDate desc , createDate desc ";

		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 
	 * doExamine:(批量审核). <br/>
	 *
	 * @author 郭斌
	 */
	public void doExamine() {

		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String orderStatus = $V("orderStatus");

		new QueryBuilder(" update OtOrderInfo set orderstatus=?,modifyDate=now() where id in ('"
				+ ids.replaceAll(",", "','") + "')", orderStatus).executeNoQuery();

		Response.setLogInfo(1, "操作成功!");
	}

	/**
	 * 订单修改
	 */
	public void doModify() {

		DataTable dt = (DataTable) Request.get("DT");
		if (dt == null) {
			Response.setError("出行人不能为空！");
			return;
		}

		String id = $V("id");

		if (StringUtil.isEmpty(id)) {
			Response.setError("订单编码不能为空！");
			return;
		}

		Transaction tran = new Transaction();

		for (int i = 0; i < dt.getRowCount(); i++) {

			DataRow dr = dt.get(i);

			String updatesql = " update OtTravelPeopleInfo set travelName=?,travelEnName=?,identityType=?,identityTypeName=?,identityId=?,identityStartDate=? ,";
			updatesql += " identityEndDate=?,sex=?,sexName=?,birthday=?,mobile=?,email=? where id=? ";

			QueryBuilder update_qb = new QueryBuilder(updatesql);
			update_qb.add(dr.get("travelName"));
			update_qb.add(dr.get("travelEnName"));
			update_qb.add(dr.get("identityType"));
			update_qb.add(dr.get("identityTypeName"));
			update_qb.add(dr.get("identityId"));
			update_qb.add(dr.get("identityStartDate"));
			update_qb.add(dr.get("identityEndDate"));
			update_qb.add(dr.get("sex"));
			update_qb.add(dr.get("sexName"));
			update_qb.add(dr.get("birthday"));
			update_qb.add(dr.get("mobile"));
			update_qb.add(dr.get("email"));
			update_qb.add(dr.get("id"));

			tran.add(update_qb);

		}

		if (tran.commit()) {
			Response.setLogInfo(1, "保存成功");

		} else {
			Response.setLogInfo(0, "保存失败");
		}
	}

}
