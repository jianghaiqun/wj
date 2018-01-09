package com.wangjin.underwriting;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.DataTable;

/**
 * 线下核保健康信息管理
 * @author guozc
 * @date 2017-07-13
 */
public class UnderwritingOfflineHealthinfo extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		StringBuffer listSql = new StringBuffer();
		listSql.append("select a.id,a.info_id,a.offlineCode,a.orderSn,a.sex,a.age,a.birthday,a.IdType,a.IdNo,a.height,a.weight,a.firsOnsetTime,a.mainSymptoms,a.diseaseName,a.attackDate,a.attackFrequency,a.attackLastDate,a.isTreat,a.stopTreat,a.treatSurgery,a.treatDrug,a.treatPhysical,a.treatOther,a.treatEffect,a.otherSupplement,a.prop1,a.prop2");
		listSql.append(" from underwriting_offline_healthinfo a");
		QueryBuilder qb = new QueryBuilder(listSql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public static Mapx<String, Object> initDialog(Mapx<String, Object> params) {

		return params;
	}
}