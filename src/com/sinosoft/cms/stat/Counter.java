package com.sinosoft.cms.stat;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文章\图片\视频点击数
 * 
 */
public class Counter {
	private static final Logger logger = LoggerFactory.getLogger(Counter.class);

	private static Mapx articleMap = new Mapx(10000);
	private static Mapx videoMap = new Mapx(10000);
	private static Mapx imageMap = new Mapx(10000);
	private static Mapx totalMap = new Mapx();
	private static Mapx todayMap = new Mapx();

	public synchronized static int getArticleHitCount(long leafID) {
		Object o = articleMap.get(new Long(leafID));
		if (o == null) {
			int c = new QueryBuilder("select HitCount from ZCArticle where ID=?", leafID).executeInt();
			articleMap.put(new Long(leafID), new Integer(c));
			return c;
		} else {
			return ((Integer) o).intValue();
		}
	}

	public synchronized static int getImageHitCount(long leafID) {
		Object o = imageMap.get(new Long(leafID));
		if (o == null) {
			int c = new QueryBuilder("select HitCount from ZCImage where ID=?", leafID).executeInt();
			imageMap.put(new Long(leafID), new Integer(c));
			return c;
		} else {
			return ((Integer) o).intValue();
		}
	}

	public synchronized static int getVideoHitCount(long leafID) {
		Object o = videoMap.get(new Long(leafID));
		if (o == null) {
			int c = new QueryBuilder("select HitCount from ZCVideo where ID=?", leafID).executeInt();
			videoMap.put(new Long(leafID), new Integer(c));
			return c;
		} else {
			return ((Integer) o).intValue();
		}
	}

	public synchronized static int getCount(String type, long leafID) {
		if ("Article".equalsIgnoreCase(type)) {
			return getArticleHitCount(leafID);
		} else if ("Image".equalsIgnoreCase(type)) {
			return getImageHitCount(leafID);
		} else if ("Video".equalsIgnoreCase(type)) {
			return getVideoHitCount(leafID);
		}
		return 0;
	}

	public synchronized static void add(String type, long leafID) {
		if ("Article".equalsIgnoreCase(type)) {
			Object o = articleMap.get(new Long(leafID));
			if (o == null) {
				int c = new QueryBuilder("select HitCount from ZCArticle where ID=?", leafID).executeInt();
				articleMap.put(new Long(leafID), new Integer(c + 1));
			} else {
				articleMap.put(new Long(leafID), new Integer(((Integer) o).intValue() + 1));
			}
		} else if ("Image".equalsIgnoreCase(type)) {
			Object o = imageMap.get(new Long(leafID));
			if (o == null) {
				int c = new QueryBuilder("select HitCount from ZCImage where ID=?", leafID).executeInt();
				imageMap.put(new Long(leafID), new Integer(c + 1));
			} else {
				imageMap.put(new Long(leafID), new Integer(((Integer) o).intValue() + 1));
			}
		} else if ("Video".equalsIgnoreCase(type)) {
			Object o = videoMap.get(new Long(leafID));
			if (o == null) {
				int c = new QueryBuilder("select HitCount from ZCVideo where ID=?", leafID).executeInt();
				videoMap.put(new Long(leafID), new Integer(c + 1));
			} else {
				videoMap.put(new Long(leafID), new Integer(((Integer) o).intValue() + 1));
			}
		}
	}

	public static void deal(HttpServletRequest request, HttpServletResponse response) {
		Mapx map = ServletUtil.getParameterMap(request, false);
		String type = map.getString("Type");
		try {
			long id = Long.parseLong(map.getString("ID"));
			long count = 0;
			if ("Total".equalsIgnoreCase(type)) {
				count = getTotalHitCount(id);
			} else if ("Today".equalsIgnoreCase(type)) {
				count = getTodayHitCount(id);
			} else {
				count = getCount(type, id);
			}
			response.getWriter().print("document.write(" + count + ");");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public synchronized static int getTotalHitCount(long siteID) {
		Object o = totalMap.get(new Long(siteID));
		if (o == null) {
			int c = initTotalHitCount(siteID);
			totalMap.put(new Long(siteID), new Integer(c));
			return c;
		} else {
			return ((Integer) o).intValue();
		}
	}

	public synchronized static long getTodayHitCount(long siteID) {
		Object o = todayMap.get(new Long(siteID));
		if (o == null) {
			int c = initTodayHitCount(siteID);
			todayMap.put(new Long(siteID), new Integer(c));
			return c;
		} else {
			return ((Integer) o).intValue();
		}
	}

	public synchronized static void addTotalHitCount(long siteID) {
		Object o = totalMap.get(new Long(siteID));
		if (o == null) {
			int c = initTotalHitCount(siteID);
			totalMap.put(new Long(siteID), new Integer(c + 1));
		} else {
			totalMap.put(new Long(siteID), new Integer(((Integer) o).intValue() + 1));
		}
	}

	public synchronized static void addTodayHitCount(long siteID) {
		Object o = todayMap.get(new Long(siteID));
		if (o == null) {
			int c = initTodayHitCount(siteID);
			todayMap.put(new Long(siteID), new Integer(c + 1));
		} else {
			todayMap.put(new Long(siteID), new Integer(((Integer) o).intValue() + 1));
		}
	}

	public synchronized static void clearTodayHitCount(long siteID) {// 更换统计周期时调用此方法
		todayMap.put(new Long(siteID), new Integer(0));
	}

	private static int initTotalHitCount(long siteID) {
		DataTable dt = new QueryBuilder("select * from ZCStatItem where SiteID=? and Type='Global' and SubType='PV'",
				siteID).executeDataTable();
		int c = 0;
		String month = DateUtil.getCurrentDate("yyyyMM");
		int d = DateUtil.getDayOfMonth();
		for (int i = 0; i < dt.getRowCount(); i++) {
			for (int j = 5; j < dt.getColCount(); j++) {
				if (dt.getString(i, "Period").equals(month) && i - 4 == d) {
					continue;
				}
				c += dt.getInt(i, j);
			}
		}
		c += getTodayHitCount(siteID);
		return c;
	}

	private static int initTodayHitCount(long siteID) {
		String month = DateUtil.getCurrentDate("yyyyMM");
		DataTable dt = new QueryBuilder(
				"select * from ZCStatItem where SiteID=? and Type='Global' and Period=? and SubType='PV'", siteID,
				month).executeDataTable();
		if (dt == null || dt.getRowCount() == 0) {
			return 0;
		}
		return dt.getInt(0, DateUtil.getDayOfMonth() + 4);
	}
}
