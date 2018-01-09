package com.sinosoft.cms.stat;

import java.util.Date;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCStatItemSchema;
import com.sinosoft.schema.ZCStatItemSet;

public abstract class AbstractStat {
	public static final int PERIOD_DAY = 1;

	public static final int PERIOD_HOUR = 2;

	/**
	 * 是否是一个新的时间段，新时间段需要插入记录
	 */
	protected boolean isNewMonth = false;

	/**
	 * 初始化统计项，主要是从持久层获取统计项的值
	 */
	public void init() {
		String period = DateUtil.toString(new Date(), "yyyyMM");
		DataTable dt = new QueryBuilder("select * from ZCStatItem where Period=? and Type=?", period, getStatType())
				.executeDataTable();
		if (dt.getRowCount() == 0) {// 本月尚未开始统计
			isNewMonth = true;
		} else {
			int day = Integer.parseInt(DateUtil.getCurrentDate("dd")) + 5 - 1;// 前面有SiteID,Period,Type,SubType,Item五项
			for (int i = 0; i < dt.getRowCount(); i++) {
				String str = dt.getString(i, day);
				if (StringUtil.isEmpty(str)) {
					str = "0";
				}
				long siteID = Long.parseLong(dt.getString(i, "SiteID"));
				String type = dt.getString(i, "Type");
				String subtype = dt.getString(i, "SubType");
				String item = dt.getString(i, "Item");

				String[] avgSubTypes = getAverageSubTypes();
				boolean flag = false;
				if (avgSubTypes != null) {
					for (int j = 0; j < avgSubTypes.length; j++) {
						if (avgSubTypes[j].equals(subtype)) {
							VisitCount.getInstance().addAverage(siteID, type, subtype, item, 0, false);
							flag = true;
							break;
						}
					}
				}
				if (!flag) {
					VisitCount.getInstance().add(siteID, type, subtype, item, 0, false);
				}
			}
		}
	}

	/**
	 * 根据一次访问的参数Map，更新本统计项的值
	 */
	public abstract void deal(Visit v);

	/**
	 * 根据一次访问的参数Map，更新本统计项的值
	 */
	public void onPeriodChange(int type, long current) {
		if (type == AbstractStat.PERIOD_DAY) {
			String period = DateUtil.toString(new Date(current), "yyyyMMdd");
			if (period.endsWith("01")) {
				VisitCount.getInstance().clearType(getStatType(), true);
				isNewMonth = true;
			} else {
				VisitCount.getInstance().clearType(getStatType(), false);
			}
		}
	}

	/**
	 * 将本统计项的值写入持久层
	 */
	public void update(Transaction tran, VisitCount vc, long current, boolean newMonthFlag, boolean isNewPeriod) {
		Date today = new Date(current);
		if (isNewPeriod) {
			today = DateUtil.addDay(today, -1);// 当天零点更新前天数据，此时isNewMonth尚未置为true
		}
		String period = DateUtil.toString(today, "yyyyMM");
		int day = Integer.parseInt(DateUtil.toString(today, "dd"));
		String type = getStatType();
		if (newMonthFlag) {
			ZCStatItemSet set = new ZCStatItemSet();
			long[] sites = vc.getSites();
			for (int i = 0; i < sites.length; i++) {
				String[] subtypes = vc.getSubTypes(sites[i], type);
				for (int j = 0; j < subtypes.length; j++) {
					String[] items = vc.getItems(sites[i], type, subtypes[j]);
					for (int k = 0; k < items.length; k++) {
						long count = vc.get(sites[i], type, subtypes[j], items[k]);
						if (count == 0) {
							continue;
						}
						ZCStatItemSchema si = new ZCStatItemSchema();
						si.setSiteID(sites[i]);
						si.setPeriod(period);
						si.setType(type);
						si.setSubType(subtypes[j]);
						si.setItem(items[k]);
						for (int m = 5; m < si.getColumnCount(); m++) {
							si.setV(m, new Integer(0));
						}
						si.setV(day + 4, new Long(count));
						set.add(si);
						VisitCount.getInstance().set(sites[i], type, subtypes[j], items[k], 0, false);
					}
				}
			}
			tran.add(set, Transaction.DELETE_AND_INSERT);
			this.isNewMonth = false;
		} else {
			QueryBuilder qb = new QueryBuilder("update ZCStatItem set Count" + day + "=Count" + day
					+ "+? where SiteID=? and " + "Period=? and Type=? and SubType=? and Item=?");
			qb.setBatchMode(true);
			long[] sites = vc.getSites();
			ZCStatItemSet set = new ZCStatItemSet();
			for (int i = 0; i < sites.length; i++) {
				String[] subtypes = vc.getSubTypes(sites[i], type);
				for (int j = 0; j < subtypes.length; j++) {
					String[] items = vc.getItems(sites[i], type, subtypes[j]);
					for (int k = 0; k < items.length; k++) {
						long count = vc.get(sites[i], type, subtypes[j], items[k]);
						boolean isNeedInsert = vc.isNeedInsert(sites[i], type, subtypes[j], items[k]);
						if (count == 0) {
							continue;// 访问数为0的不需要更新
						}
						if (!isNeedInsert) {
							qb.add(count);
							qb.add(sites[i]);
							qb.add(period);
							qb.add(type);
							qb.add(subtypes[j]);
							qb.add(items[k]);
							qb.addBatch();
						} else {
							if (StringUtil.isNotEmpty(items[k])) {
								ZCStatItemSchema si = new ZCStatItemSchema();
								si.setSiteID(sites[i]);
								si.setPeriod(period);
								si.setType(type);
								si.setSubType(subtypes[j]);
								si.setItem(items[k]);
								for (int m = 5; m < si.getColumnCount(); m++) {
									si.setV(m, new Integer(0));
								}
								si.setV(day + 4, new Long(count));
								set.add(si);
							}
						}
						VisitCount.getInstance().set(sites[i], type, subtypes[j], items[k], 0, false);
					}
				}
			}
			tran.add(set, Transaction.DELETE_AND_INSERT);
			tran.add(qb);
		}
	}

	/**
	 * 返回统计类型
	 */
	public abstract String getStatType();

	/**
	 * 返回统计值为平均值的子项
	 */
	public String[] getAverageSubTypes() {
		return null;
	}
}
