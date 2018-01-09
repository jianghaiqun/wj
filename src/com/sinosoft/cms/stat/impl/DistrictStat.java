package com.sinosoft.cms.stat.impl;

import com.sinosoft.cms.stat.AbstractStat;
import com.sinosoft.cms.stat.Visit;
import com.sinosoft.cms.stat.VisitCount;

/**
 */
public class DistrictStat extends AbstractStat {
	private static final String Type = "District";

	public String getStatType() {
		return Type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.cms.stat.AbstractStat#deal(com.sinosoft.cms.stat.Visit)
	 */
	public void deal(Visit v) {
		VisitCount.getInstance().add(v.SiteID, Type, "PV", v.District);
		if (v.IPFlag) {
			VisitCount.getInstance().add(v.SiteID, Type, "IP", v.District);
		}
		if (v.UVFlag) {
			VisitCount.getInstance().add(v.SiteID, Type, "UV", v.District);
		}
	}
}
