package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.PrecontractCouponConfigSchema;
import com.sinosoft.schema.PrecontractCouponConfigSet;

import java.util.Date;

public class PrecontractInfo extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("SELECT * FROM precontractInfo where 1=1");
		
		if (StringUtil.isNotEmpty(dga.getParam("createDate"))) {
			qb.append(" and createDate >='"+dga.getParam("createDate").trim()+" 00:00:00'");
		}
		if (StringUtil.isNotEmpty(dga.getParam("endDate"))) {
			qb.append(" and createDate <='"+dga.getParam("endDate").trim()+" 23:59:59'");
		}
		if (StringUtil.isNotEmpty(dga.getParam("PreconURL"))) {
			qb.append(" and PreconURL like ?","%"+dga.getParam("PreconURL").trim()+"%");
		}
		qb.append(" ORDER BY createDate DESC");
		
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public static void dg2DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("SELECT StencilUrl,CouponBatch,Remark,Prop1 FROM PrecontractCouponConfig WHERE 1=1 ");
		
		if (StringUtil.isNotEmpty(dga.getParam("couponBatch"))) {
			qb.append(" AND CouponBatch ='" + dga.getParam("couponBatch") + "'");
		}
		if (StringUtil.isNotEmpty(dga.getParam("stencilUrl"))) {
			qb.append(" AND StencilUrl LIKE '%" + dga.getParam("stencilUrl") + "%'");
		}
		qb.append(" ORDER BY ModifyDate DESC");
		
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public void addConfig() {
		String couponBatch = $V("CouponBatch");
		String stencilUrl = $V("StencilUrl");
		String initOrderNum = $V("Prop1");
		String remark = $V("Remark");
		
		try {
			PrecontractCouponConfigSchema config = new PrecontractCouponConfigSchema();
			config.setStencilUrl(stencilUrl);
			
			PrecontractCouponConfigSet set = config.query();
			if (set != null && set.toDataTable().getRowCount() > 0) {
				Response.setLogInfo(0, "新增失败，当前栏目URL已创建对应优惠券！");
				return;
			}
			
			config.setCouponBatch(couponBatch);
			config.setRemark(remark);
			config.setProp1(initOrderNum);
			config.setCreateDate(new Date());
			config.setCreateUser(User.getUserName());
			config.setModifyDate(new Date());
			config.setModifyUser(User.getUserName());
			
			boolean result = config.insert();
			if (result) {
				Response.setLogInfo(1, "新增成功！");
			} else {
				Response.setLogInfo(0, "新增失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "新增失败！");
		}
	}
	
	public void delConfig() {
		DataTable dt = (DataTable) Request.get("DT");
		PrecontractCouponConfigSet set = new PrecontractCouponConfigSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			PrecontractCouponConfigSchema config = new PrecontractCouponConfigSchema();
			config.setValue(dt.getDataRow(i));
			config.fill();
			set.add(config);
		}
		if (set.delete()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "删除失败!");
		}
	}

}