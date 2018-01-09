package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

public class LotteryInfo extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("SELECT * FROM lotterywinner where winnerid <> ''");
		if (StringUtil.isNotEmpty(dga.getParam("WinnerName"))) {
			qb.append(" and WinnerName like ?","%"+dga.getParam("WinnerName").trim()+"%");
		}
		if (StringUtil.isNotEmpty(dga.getParam("WinnerEmail"))) {
			qb.append(" and WinnerEmail like ?","%"+dga.getParam("WinnerEmail").trim()+"%");
		}
		if (StringUtil.isNotEmpty(dga.getParam("Award"))) {
			qb.append(" and Award like ?","%"+dga.getParam("Award").trim()+"%");
		}
		if (StringUtil.isNotEmpty(dga.getParam("WinnerMobil"))) {
			qb.append(" and WinnerMobil like ?","%"+dga.getParam("WinnerMobil").trim()+"%");
		}
		qb.append(" ORDER BY wintime DESC");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
}