package com.wangjin.activity;

import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;

public class PointsActivityInfo extends Page {

	public static void dg1DataBind(DataGridAction dga) { 
		QueryBuilder qb = new QueryBuilder(
				"select a.Id, a.ModifyDate, a.ModifyUser, a.Points, "
						+ "(select CodeName from zdcode where  CodeType='PointsType' and ParentCode='PointsType' "
						+ "and CodeValue = a.PointsType) PointsTypeName from zdPointInfo a ");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 设置积分
	 */
	public void setPoints() {
		String id = $V("Id");
		String points = $V("Points");
		GetDBdata db = new GetDBdata();
		if (StringUtil.isEmpty(id)) {
			Response.setLogInfo(0, "请选择条目！");
			return;
		}
		if (StringUtil.isEmpty(points)) {
			Response.setLogInfo(0, "请设置积分！");
			return;
		}
		if (!NumberUtil.isInt(points)) {
			Response.setLogInfo(0, "积分必须是整数！");
			return;
		}
		int intPoints = Integer.valueOf(points);
		if (intPoints < 0) {
			Response.setLogInfo(0, "积分不能是负数！");
			return;
		}
		String updateSql = "update zdPointInfo set Points = '" + points
				+ "', ModifyDate = SYSDATE(), ModifyUser='"
				+ User.getUserName() + "' where Id = '" + id + "'";
		try {
			db.execUpdateSQL(updateSql, null);
			Response.setLogInfo(1, "设置成功！");
		} catch (Exception e) {
			Response.setLogInfo(0, "设置时出错！" + e.getMessage());
			logger.error(e.getMessage(), e);
		}
	}

}
