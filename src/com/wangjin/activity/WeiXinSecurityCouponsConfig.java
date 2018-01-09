package com.wangjin.activity;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.schema.SDCouponInfoSchema;
import com.sinosoft.schema.SDCouponInfoSet;
import com.sinosoft.schema.ZDCodeSchema;

import java.util.Date;

public class WeiXinSecurityCouponsConfig extends Page {

	public static void dg1DataBind(DataGridAction dga) {

		QueryBuilder qb = new QueryBuilder(
				"select CodeValue,CodeName,Memo,CodeType,AddTime,AddUser,ParentCode from ZDCode " +
						"where CodeType ='WeiXinSecurityCouponConfig' and ParentCode ='WeiXinSecurityCouponConfig' ");
		dga.bindData(qb);
	}

	public void add() {
		ZDCodeSchema code = new ZDCodeSchema();
		code.setValue(Request);
		if (code.fill()) {
			Response.setLogInfo(0, "优惠券" + code.getCodeValue() + "已经存在于配置中了!");
			return;
		}
		code.setCodeOrder(System.currentTimeMillis());
		code.setAddTime(new Date());
		code.setAddUser(User.getUserName());
		String couponBatch=code.getCodeValue();
		SDCouponInfoSchema couponInfo=new SDCouponInfoSchema();
		QueryBuilder qb = new QueryBuilder("where batch=? and status='0' limit 1",couponBatch);
		SDCouponInfoSet set = couponInfo.query(qb);
		if (set==null || set.size()==0){
			Response.setLogInfo(0, "优惠券" + code.getCodeValue() + "不存在!");
			return;
		}
		couponInfo= set.get(0);
		String riskCode=couponInfo.getRiskCode();
		if (riskCode!=null && riskCode.split(",").length>1){
			Response.setLogInfo(0, "优惠券只能配置一个适用的险种类型!");
			return;
		}
		if(!"01".equals(couponInfo.getProp3())){
			Response.setLogInfo(0, "优惠券只能配置非折扣券!");
			return;
		}

		if (code.insert()) {
			CacheManager.set("Code", code.getCodeType(), code.getCodeValue(), code.getCodeName());
			Response.setLogInfo(1, "配置优惠券成功!");
		} else {
			Response.setLogInfo(0, "配置优惠券失败!");
		}
	}

}
