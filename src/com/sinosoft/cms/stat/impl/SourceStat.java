package com.sinosoft.cms.stat.impl;

import java.util.Date;

import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.cms.stat.AbstractStat;
import com.sinosoft.cms.stat.StatUtil;
import com.sinosoft.cms.stat.Visit;
import com.sinosoft.cms.stat.VisitCount;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;

/**
 */
public class SourceStat extends AbstractStat {
	private static final String Type = "Source";

	private Mapx siteMap = new Mapx();// 用于判断相应站点是否已经初始过了

	public String getStatType() {
		return Type;
	}

	public void deal(Visit v) {
		if ("Unload".equals(v.Event)) {
			return;
		}
		if (!siteMap.containsKey(new Long(v.SiteID))) {
			VisitCount.getInstance().initLRUMap(v.SiteID, Type, "Keyword", 1000, null);
			VisitCount.getInstance().initLRUMap(v.SiteID, Type, "Referer", 1000, null);
			siteMap.put(new Long(v.SiteID), "");
		}
		VisitCount.getInstance().add(v.SiteID, Type, "Host", v.Host);
		if (v.UVFlag) {
			if (StringUtil.isEmpty(v.Referer) && v.URL.indexOf("Result.jsp") < 0) {
				VisitCount.getInstance().add(v.SiteID, Type, "Direct", "0");
			} else {
				String[] se = getSearchEngine(v);
				if (se == null) {
					String domain = StatUtil.getDomain(v.Referer);
					if (!domain.equalsIgnoreCase(v.Host)) {
						VisitCount.getInstance().add(v.SiteID, Type, "Referer", domain);
					} else {
						VisitCount.getInstance().add(v.SiteID, Type, "Direct", "0");
					}
				} else {
					VisitCount.getInstance().add(v.SiteID, Type, "SearchEngine", se[0]);
					VisitCount.getInstance().add(v.SiteID, Type, "Keyword", se[1]);
				}
			}
		}
	}

	public void onPeriodChange(int type, long current) {
		if (type == AbstractStat.PERIOD_DAY) {
			String period = DateUtil.toString(new Date(current), "yyyyMMdd");
			if (period.endsWith("01")) {
				isNewMonth = true;
				VisitCount.getInstance().clearType(getStatType(), true);
			} else {
				VisitCount.getInstance().clearType(getStatType(), false);
				VisitCount.getInstance().clearSubType(getStatType(), "Keyword", true);
				VisitCount.getInstance().clearSubType(getStatType(), "Referer", true);
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
				URLStat.dealNotNeedInsertItem(vc, period, sites[i], Type, "Referer");
				URLStat.dealNotNeedInsertItem(vc, period, sites[i], Type, "Keyword");
			}
		}
		super.update(tran, vc, current, newMonthFlag, isNewPeriod);
	}

	public static String[] getSearchEngine(Visit v) {
		String url = v.URL;
		if (StringUtil.isEmpty(url)) {
			return null;
		}
		// 先处理本站搜索
		if (url.indexOf("Result.jsp") > 0) {
			String keyword = SearchAPI.getParameter(url, "query");
			return new String[] { "站内搜索", keyword };
		}
		url = v.Referer;
		String domain = StatUtil.getDomain(url);
		Mapx map = ServletUtil.getMapFromQueryString(url);
		String name = null;
		String keyword = null;
		if (domain.indexOf("baidu.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("wd"), "GBK");
			name = "百度";
		} else if (domain.indexOf("google.") > 0) {
			String charset = map.getString("ie");
			if (StringUtil.isEmpty(charset)) {
				charset = "UTF-8";
			}
			keyword = StringUtil.urlDecode(map.getString("q"), charset);
			name = "谷歌";
		} else if (domain.indexOf("yahoo.") > 0) {
			String charset = map.getString("ei");
			if (StringUtil.isEmpty(charset)) {
				charset = "UTF-8";
			}
			keyword = StringUtil.urlDecode(map.getString("p"), charset);
			name = "雅虎";
		} else if (domain.indexOf("msn.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("q"), "UTF-8");
			name = "MSN";
		} else if (domain.indexOf("soso.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("w"), "GBK");
			name = "搜搜";
		} else if (domain.indexOf("sogou.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("query"), "GBK");
			name = "搜狗";
		} else if (domain.indexOf("zhongsou.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("word"), "GBK");
			name = "中搜";
		} else if (domain.indexOf("youdao.") > 0) {
			String charset = map.getString("ue");
			if (StringUtil.isEmpty(charset)) {
				charset = "UTF-8";
			}
			keyword = StringUtil.urlDecode(map.getString("q"), charset);
			name = "有道";
		} else if (domain.indexOf("live.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("q"), "UTF-8");
			name = "Live.com";
		}
		if (StringUtil.isNotEmpty(keyword)) {
			return new String[] { name, keyword };
		}
		return null;
	}
}
