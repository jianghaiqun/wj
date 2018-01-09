package com.sinosoft.cms.stat.impl;

import java.util.Date;

import com.sinosoft.cms.stat.AbstractStat;
import com.sinosoft.cms.stat.Visit;
import com.sinosoft.cms.stat.VisitCount;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;

/**
 * @Author 王育春
 * @Date 2009-5-3
 * @Mail wyuch@midding.com
 */
public class URLStat extends AbstractStat {
	private Mapx siteMap = new Mapx();

	private static final String Type = "URL";

	public String getStatType() {
		return Type;
	}

	public void deal(Visit v) {
		if (!siteMap.containsKey(new Long(v.SiteID))) {
			VisitCount.getInstance().initLRUMap(v.SiteID, Type, "Exit", 1000, null);
			VisitCount.getInstance().initLRUMap(v.SiteID, Type, "Entrance", 1000, null);
			VisitCount.getInstance().initLRUMap(v.SiteID, Type, "Top", 2000, null);
			siteMap.put(new Long(v.SiteID), "");
		}
		if (!"Unload".equals(v.Event)) {
			if (v.UVFlag) {
				VisitCount.getInstance().add(v.SiteID, Type, "Entrance", v.URL);// 入口
			}
			VisitCount.getInstance().add(v.SiteID, Type, "Top", v.URL);
		}
	}

	public void onPeriodChange(int type, long current) {
		if (type == AbstractStat.PERIOD_DAY) {
			VisitCount.getInstance().clearType(getStatType(), true);
			String period = DateUtil.toString(new Date(current), "yyyyMMdd");
			if (period.endsWith("01")) {
				isNewMonth = true;
			}
		}
	}

	public void update(Transaction tran, VisitCount vc, long current, boolean newMonthFlag, boolean isNewPeriod) {
		if (!newMonthFlag) {
			Date today = new Date(current);
			if (isNewPeriod) {
				today = DateUtil.addDay(today, -1);// 当天零点更新前天数据，此时isNewMonth尚未置为ture
			}
			String period = DateUtil.toString(today, "yyyyMM");
			long[] sites = vc.getSites();
			for (int i = 0; i < sites.length; i++) {
				dealNotNeedInsertItem(vc, period, sites[i], Type, "Exit");
				dealNotNeedInsertItem(vc, period, sites[i], Type, "Entrance");
				dealNotNeedInsertItem(vc, period, sites[i], Type, "Top");
			}
		}
		super.update(tran, vc, current, newMonthFlag, isNewPeriod);
	}

	/**
	 * SourceStat也用到了这个方法
	 */
	public static void dealNotNeedInsertItem(VisitCount vc, String period, long site, String type, String subType) {
		String[] items = vc.getItems(site, type, subType);
		Mapx map = new Mapx();
		boolean isNewBatch = true;
		StringBuffer sb = null;
		int batchCount = 0;
		for (int i = 0; i < items.length; i++) {
			if (isNewBatch) {
				sb = new StringBuffer("select Item,Count" + DateUtil.getDayOfMonth()
						+ " from ZCStatItem where SiteID=? and Type=? and SubType=? and Period=? and Item in (''");
				isNewBatch = false;
			}
			if (vc.isNeedInsert(site, type, subType, items[i])) {
				sb.append(",");
				sb.append("'");
				sb.append(items[i]);
				sb.append("'");
				batchCount++;
				if (batchCount >= 50) {
					batchCount = 0;
					isNewBatch = true;

					sb.append(")");
					QueryBuilder qb = new QueryBuilder(sb.toString());
					qb.add(site);
					qb.add(type);
					qb.add(subType);
					qb.add(period);
					map.putAll(qb.executeDataTable().toMapx(0, 1));
				}
			}
		}
		if (batchCount > 0) {
			sb.append(")");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			qb.add(site);
			qb.add(type);
			qb.add(subType);
			qb.add(period);
			map.putAll(qb.executeDataTable().toMapx(0, 1));
		}

		Object[] arr = map.keyArray();
		for (int j = 0; j < arr.length; j++) {
			String item = arr[j].toString();
			long c = vc.get(site, type, subType, item);
			vc.set(site, type, subType, item, c + map.getLong(item), false);
			VisitCount.getInstance().set(site, type, subType, item, c + map.getLong(item), false);
		}
	}
}
