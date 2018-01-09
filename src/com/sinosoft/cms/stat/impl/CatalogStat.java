package com.sinosoft.cms.stat.impl;

import java.util.ArrayList;

import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.stat.AbstractStat;
import com.sinosoft.cms.stat.Visit;
import com.sinosoft.cms.stat.VisitCount;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCStatItemSchema;
import com.sinosoft.schema.ZCStatItemSet;

/**
 * 统计各栏目的点击量和平均停留时间
 * 
 */
public class CatalogStat extends AbstractStat {
	private static final String[] avgSubTypes = new String[] { "StickTime" };

	private static final String Type = "Catalog";

	public String getStatType() {
		return Type;
	}

	public String[] getAverageSubTypes() {
		return avgSubTypes;
	}

	public void deal(Visit v) {
		if (StringUtil.isEmpty(v.Type) || "AD".equals(v.Type) || "Other".equals(v.Type)) {
			return;
		}
		String code = v.CatalogInnerCode;
		if (code == null || code.length() % 6 != 0) {
			code = "";
		}
		if (StringUtil.isNotEmpty(code) && CatalogUtil.getSiteIDByInnerCode(v.CatalogInnerCode) == null) {
			return;// 可能栏目已经被删除了或者正在移动中
		}
		if (v.LeafID == 0) {// 如果当前URL是栏目首页，则更新栏目首页的访问量
			if ("Unload".equals(v.Event)) {
				VisitCount.getInstance().addAverage(v.SiteID, Type, "StickTime", code + "Index", v.StickTime);
			} else {
				VisitCount.getInstance().add(v.SiteID, Type, "PV", code + "Index");
			}
		}
		String[] codes = new String[code.length() / 6];
		for (int i = 0; i < codes.length; i++) {
			codes[i] = code.substring(0, i * 6 + 6);
		}
		for (int i = 0; i < codes.length; i++) {
			code = codes[i];
			if ("Unload".equals(v.Event)) {
				VisitCount.getInstance().addAverage(v.SiteID, Type, "StickTime", code, v.StickTime);// 更新今日页均停留时间
			} else {
				VisitCount.getInstance().add(v.SiteID, Type, "PV", code);
			}
			code = code.substring(0, code.length() - 6);
		}
	}

	/**
	 * 栏目转移时更新统计表中的内部编码
	 */
	public static void updateInnerCode(Transaction tran, long siteID, String oldInnerCode, String newInnerCode) {
		// 更新统计表中的编码
		QueryBuilder qb = new QueryBuilder("where SiteID=? and type=? and item like ?");
		qb.add(siteID);
		qb.add("Catalog");
		qb.add(oldInnerCode + "%");// 旧内部编码
		ZCStatItemSet statSet = new ZCStatItemSchema().query(qb);
		ZCStatItemSet childSet = new ZCStatItemSet();
		for (int i = 0; i < statSet.size(); i++) {
			String item = statSet.get(i).getItem();
			if (item.equals(oldInnerCode)) {
				childSet.add(statSet.get(i));
			}
			item = StringUtil.replaceEx(item, oldInnerCode, newInnerCode);// 替换成新的内部编码
			statSet.get(i).setItem(item);
		}

		// 原来的父级栏目
		ArrayList parentList = new ArrayList();
		ZCStatItemSet parentSet = null;
		String code = oldInnerCode;
		while (code.length() > 6) {
			code = code.substring(0, code.length() - 6);
			parentList.add(code);
		}
		if (parentList.size() > 0) {
			qb = new QueryBuilder("where SiteID=? and type=? and item in ('" + StringUtil.join(parentList).replaceAll(",", "','") + "')");
			qb.add(siteID);
			qb.add("Catalog");
			parentSet = new ZCStatItemSchema().query(qb);
		}

		// 新的父级栏目
		parentList = new ArrayList();
		ZCStatItemSet newParentSet = null;
		code = newInnerCode;
		while (code.length() > 6) {
			code = code.substring(0, code.length() - 6);
			parentList.add(code);
		}
		if (parentList.size() > 0) {
			qb = new QueryBuilder("where SiteID=? and type=? and item in ('" + StringUtil.join(parentList).replaceAll(",", "','") + "')");
			qb.add(siteID);
			qb.add("Catalog");
			newParentSet = new ZCStatItemSchema().query(qb);
		}
		// 旧的父级栏目的统计项中必须减掉相应数值
		if (parentSet != null) {
			for (int i = 0; i < parentSet.size(); i++) {
				ZCStatItemSchema parent = parentSet.get(i);
				for (int j = 0; j < childSet.size(); j++) {
					ZCStatItemSchema child = childSet.get(j);
					if (child.getItem().endsWith("Index")) {
						continue;
					}
					if (child.getPeriod().equals(parent.getPeriod()) && child.getSubType().equals(parent.getSubType())) {
						for (int k = 5; k < child.getColumnCount(); k++) {
							Long n1 = (Long) child.getV(k);
							Long n2 = (Long) parent.getV(k);
							long v = n2.longValue() - n1.longValue();
							if (v < 0) {
								v = 0;
							}
							parent.setV(k, new Long(v));// 减去被移走的栏目的统计值
						}
					}
				}
			}
		}
		// 新的父级栏目的统计项中必须减掉相应数值
		ZCStatItemSet noExistsParentSet = new ZCStatItemSet();
		if (newParentSet != null) {
			for (int j = 0; j < childSet.size(); j++) {
				ZCStatItemSchema child = childSet.get(j);
				if (child.getItem().endsWith("Index")) {
					continue;
				}
				ArrayList list = (ArrayList) parentList.clone();
				for (int i = 0; i < newParentSet.size(); i++) {
					ZCStatItemSchema parent = newParentSet.get(i);
					if (child.getPeriod().equals(parent.getPeriod()) && child.getSubType().equals(parent.getSubType())) {
						for (int k = 5; k < child.getColumnCount(); k++) {
							Long n1 = (Long) child.getV(k);
							Long n2 = (Long) parent.getV(k);
							parent.setV(k, new Long(n2.longValue() + n1.longValue()));// 加上移来的栏目的统计值
						}
						String item = parent.getItem();
						list.remove(item);
					}
				}
				for (int i = 0; i < list.size(); i++) {
					code = (String) list.get(i);
					ZCStatItemSchema si = (ZCStatItemSchema) child.clone();
					si.setItem(code);
					noExistsParentSet.add(si);
				}
			}
		}
		tran.add(noExistsParentSet, Transaction.INSERT);
		tran.add(parentSet, Transaction.UPDATE);
		tran.add(newParentSet, Transaction.UPDATE);
		tran.add(statSet, Transaction.UPDATE);
	}
}
