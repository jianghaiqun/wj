package com.sinosoft.cms.stat.impl;

import java.util.Date;

import com.sinosoft.cms.stat.AbstractStat;
import com.sinosoft.cms.stat.StatUtil;
import com.sinosoft.cms.stat.Visit;
import com.sinosoft.cms.stat.VisitCount;
import com.sinosoft.cms.stat.VisitHandler;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;

/**
 */
public class GlobalStat extends AbstractStat {
	private static final String[] avgSubTypes = new String[] { "StickTime", "VisitDepth", "StickTotalTime" };

	private static final String Type = "Global";

	public String getStatType() {
		return Type;
	}

	public String[] getAverageSubTypes() {
		return avgSubTypes;
	}

	public void deal(Visit v) {
		if ("Unload".equals(v.Event)) {
			VisitCount.getInstance().addAverage(v.SiteID, Type, "StickTime", "0", v.StickTime);// 更新今日页均停留时间
		} else {
			VisitCount.getInstance().add(v.SiteID, Type, "PV", "0");
			if (v.UVFlag) {
				VisitCount.getInstance().add(v.SiteID, Type, "UV", "0");
				if (v.RVFlag) {
					VisitCount.getInstance().add(v.SiteID, Type, "NewVisitor", "0");
				} else {
					VisitCount.getInstance().add(v.SiteID, Type, "ReturnVisitor", "0");
				}
				if (v.Frequency > 0) {
					String item = "" + v.Frequency;
					if (v.Frequency > 30) {
						item = "&gt;30";
					}
					VisitCount.getInstance().add(v.SiteID, Type, "VisitFreq", item);
				}
			}
			if (v.IPFlag) {
				VisitCount.getInstance().add(v.SiteID, Type, "IP", "0");
			}
		}
	}

	private static Mapx ipMap = new Mapx();

	private static long lastIPClearTime;

	/**
	 * Type通常是SiteID,也可以是调查ID
	 */
	public static boolean isTodayVisited(String type, String strIP, long current) {
		Long ip = new Long(StatUtil.convertIP(strIP));
		synchronized (ipMap) {
			if (!ipMap.containsKey(type)) {
				ipMap.put(type, new Mapx(100000));
			}
		}
		Mapx map = (Mapx) ipMap.get(type);
		synchronized (map) {
			Object obj = map.get(ip);
			if (obj != null) {
				long t = ((Long) obj).longValue();
				if (current - t < VisitHandler.DAY) {// 有可能未及时清理
					return true;
				} else {
					map.remove(ip);
					map.put(ip, new Long(current));
					return false;
				}
			} else {
				if (current - lastIPClearTime > 900000) {
					Object[] ks = map.keyArray();
					Object[] vs = map.valueArray();
					for (int i = 0; i < ks.length; i++) {
						long t = ((Long) vs[i]).longValue();
						if (current - t < VisitHandler.DAY) {
							break;
						} else {
							map.remove(ks[i]);
						}
					}
					lastIPClearTime = current;
				}
				map.put(ip, new Long(current));
			}
		}
		return false;
	}

	private static Mapx idMap = new Mapx();

	private static long lastIDClearTime;

	private static long lastOnlineCheckTime;

	private static final long AliveTimeOut = 600000;// 10分钟没有动作,则认为退出

	public static void keepAlive(long siteID, String id, long time) {
		Mapx map = (Mapx) idMap.get(new Long(siteID));
		synchronized (map) {
			Object obj = map.get(id);
			if (obj != null) {
				VisitHistory vh = (VisitHistory) obj;
				vh.LastTime = time;
				map.put(id, vh);
			}
		}

	}

	/**
	 * 每次访问时都会调用本方法,用于检验是否是一UV
	 */
	public static void dealUniqueID(Visit v) {
		Long site = new Long(v.SiteID);
		synchronized (idMap) {
			if (!idMap.containsKey(site)) {
				idMap.put(site, new Mapx());
			}
		}
		Mapx map = (Mapx) idMap.get(site);
		synchronized (map) {
			long time = v.VisitTime;
			Object obj = map.get(v.UniqueID);
			if (obj == null) {
				map.put(v.UniqueID, new VisitHistory(time, time, 1, v.URL));
				v.UVFlag = true;
			} else {
				VisitHistory vh = (VisitHistory) obj;
				if (time - vh.LastTime < AliveTimeOut) {
					vh.LastTime = time;
					vh.Depth++;
					vh.URL = v.URL;
					v.UVFlag = false;
				} else {// 超过15分钟没有动作,然后再次访问,算作另一次UV
					long avgStickTime = VisitCount.getInstance().get(time, Type, "StickTime", "0");
					String item = "" + vh.Depth;
					if (vh.Depth > 30) {
						item = "&gt;30";
					}
					VisitCount.getInstance().add(v.SiteID, Type, "VisitDepth", item);
					VisitCount.getInstance().addAverage(v.SiteID, Type, "StickTotalTime", "0",
							avgStickTime + (vh.LastTime - vh.StartTime) / 1000);
					VisitCount.getInstance().add(v.SiteID, "URL", "Exit", vh.URL);
					vh.StartTime = time;
					vh.LastTime = time;
					vh.Depth = 1;
					vh.URL = v.URL;
					v.UVFlag = true;
				}
			}
		}
	}

	/**
	 * 定时任务调用本方法,以便于特殊时段前台任何人访问时,也能及时保存数据
	 */
	public static void dealVisitHistory(long time) {
		Object[] sites = idMap.keyArray();
		for (int k = 0; k < sites.length; k++) {
			long siteID = Long.parseLong(sites[k].toString());
			Mapx map = (Mapx) idMap.get(new Long(siteID));
			synchronized (map) {
				if (time - lastOnlineCheckTime >= 60000) {// 每分钟检查一次在线人数
					int c15 = 0, c10 = 0, c5 = 0;
					Object[] vs = map.valueArray();
					for (int i = 0; i < vs.length; i++) {
						VisitHistory vh = (VisitHistory) vs[i];
						if (time - vh.LastTime < 900000) {
							c15++;
							if (time - vh.LastTime < 600000) {
								c10++;
								if (time - vh.LastTime < 300000) {
									c5++;
								}
							}
						}
					}
					String h = DateUtil.toString(new Date(time), "HH");
					long m15 = VisitCount.getInstance().get(siteID, "Hour", "15Min", h);
					long m10 = VisitCount.getInstance().get(siteID, "Hour", "10Min", h);
					long m5 = VisitCount.getInstance().get(siteID, "Hour", "5Min", h);

					if (m15 < c15) {
						VisitCount.getInstance().set(siteID, "Hour", "15Min", h, c15);
					}
					if (m10 < c10) {
						VisitCount.getInstance().set(siteID, "Hour", "10Min", h, c10);
					}
					if (m5 < c5) {
						VisitCount.getInstance().set(siteID, "Hour", "5Min", h, c5);
					}
					lastOnlineCheckTime = time;
				}
				if (time - lastIDClearTime >= 30000) {
					Object[] ks = map.keyArray();
					Object[] vs = map.valueArray();
					for (int i = 0; i < ks.length; i++) {
						VisitHistory vh = (VisitHistory) vs[i];
						if (time - vh.LastTime < AliveTimeOut) {// 5分钟算退出
							break;
						} else {
							long avgStickTime = VisitCount.getInstance().get(time, Type, "StickTime", "0");
							String item = "" + vh.Depth;
							if (vh.Depth > 30) {
								item = "&gt;30";
							}
							VisitCount.getInstance().add(siteID, Type, "VisitDepth", item);
							VisitCount.getInstance().addAverage(siteID, Type, "StickTotalTime", "0",
									avgStickTime + (vh.LastTime - vh.StartTime) / 1000);
							VisitCount.getInstance().add(siteID, "URL", "Exit", vh.URL);
							map.remove(ks[i]);
						}
					}
					lastIDClearTime = time;
				}
			}
		}
	}

	static class VisitHistory {
		public long StartTime;// 本次会话开始时间

		public long LastTime;// 本次会话结束时间

		public int Depth;// 本次会话访问页面深度

		public String URL;// 最后访问的URL

		public VisitHistory(long startTime, long lastTime, int depth, String url) {
			StartTime = startTime;
			LastTime = lastTime;
			Depth = depth;
			URL = url;
		}
	}
}
