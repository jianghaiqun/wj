package com.sinosoft.cms.stat.report;

import com.sinosoft.cms.stat.impl.LeafStat;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;

public class LastVisitReport extends Page {
	public static void dg1DataBind(DataListAction dga) {
		DataTable dt = null;
		if ("Y".equals(Config.getValue("RetainAllVisitLog"))) {
			QueryBuilder qb = new QueryBuilder("select * from ZCVisitLog where SiteID=? order by AddTime desc",
					Application.getCurrentSiteID());
			dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
			dga.setTotal(qb);
		} else {
			dt = LeafStat.getVisitlogSet(Application.getCurrentSiteID(), false).toDataTable();
			dga.setTotal(dt.getRowCount());
			DataTable paged = new DataTable(dt.getDataColumns(), null);
			for (int i = dga.getPageIndex() * dga.getPageSize(); i < (dga.getPageIndex() + 1) * dga.getPageSize()
					&& i < dt.getRowCount(); i++) {
				paged.insertRow(dt.getDataRow(i));
			}
			dt = paged;
		}
		dt.insertColumn("RowNo");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (StringUtil.isEmpty(dt.getString(i, "District"))) {
				dt.set(i, "District", "未知地区");
			}
			if (StringUtil.isEmpty(dt.getString(i, "Language"))) {
				dt.set(i, "Language", "未知");
			}
			if (StringUtil.isEmpty(dt.getString(i, "FlashVersion"))) {
				dt.set(i, "Language", "未知");
			}
			if (StringUtil.isEmpty(dt.getString(i, "Referer"))) {
				dt.set(i, "Referer", "无(直接进入)");
			}
			dt.set(i, "RowNo", dga.getPageSize() * dga.getPageIndex() + i + 1);
		}
		dga.bindData(dt);
	}
}
