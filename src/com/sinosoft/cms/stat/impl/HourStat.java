package com.sinosoft.cms.stat.impl;

import java.util.Date;

import com.sinosoft.cms.stat.AbstractStat;
import com.sinosoft.cms.stat.Visit;
import com.sinosoft.cms.stat.VisitCount;
import com.sinosoft.framework.utility.DateUtil;

/**
 */
public class HourStat extends AbstractStat {
	private static final String[] avgSubTypes = new String[] { "StickTime" };

	private static final String Type = "Hour";

	public String getStatType() {
		return Type;
	}

	public String[] getAverageSubTypes() {
		return avgSubTypes;
	}

	public void deal(Visit v) {
		String h = DateUtil.toString(new Date(v.VisitTime), "HH");
		if ("Unload".equals(v.Event)) {
			VisitCount.getInstance().addAverage(v.SiteID, Type, "StickTime", h, v.StickTime);// 更新页均停留时间
		} else {
			VisitCount.getInstance().add(v.SiteID, Type, "PV", h);
			if (v.UVFlag) {
				VisitCount.getInstance().add(v.SiteID, Type, "UV", h);
				if (v.RVFlag) {
					VisitCount.getInstance().add(v.SiteID, Type, "NewVisitor", h);
				} else {
					VisitCount.getInstance().add(v.SiteID, Type, "ReturnVisitor", h);
				}
			}
			if (v.IPFlag) {
				VisitCount.getInstance().add(v.SiteID, Type, "IP", h);
			}
		}
	}
}
