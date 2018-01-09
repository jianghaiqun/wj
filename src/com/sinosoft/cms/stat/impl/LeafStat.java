package com.sinosoft.cms.stat.impl;

import java.util.Date;

import com.sinosoft.cms.stat.AbstractStat;
import com.sinosoft.cms.stat.Counter;
import com.sinosoft.cms.stat.Visit;
import com.sinosoft.cms.stat.VisitCount;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.Queuex;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.District;
import com.sinosoft.schema.ZCVisitLogSchema;
import com.sinosoft.schema.ZCVisitLogSet;

/**
 */
public class LeafStat extends AbstractStat {
	private Mapx siteMap = new Mapx();

	private static Mapx lastVisitMap = new Mapx();

	private static long LastVisitLogID = System.currentTimeMillis();

	private static long LastVisitLogUpdateTime = System.currentTimeMillis();

	private static final int MaxLastLogCount = 100;// 默认最多保存100条

	public void init() {// 不需要从持久层加载
	}

	public String getStatType() {
		return "Leaf";
	}

	public void deal(Visit v) {
		if (v.LeafID != 0) {
			if (!siteMap.containsKey(new Long(v.SiteID))) {
				VisitCount.getInstance().initLRUMap(v.SiteID, "Article", "StickTime", 10000,
						new LeafExitEventListener("Article", "StickTime"));
				VisitCount.getInstance().initLRUMap(v.SiteID, "Image", "StickTime", 5000,
						new LeafExitEventListener("Image", "StickTime"));
				VisitCount.getInstance().initLRUMap(v.SiteID, "Video", "StickTime", 5000,
						new LeafExitEventListener("Video", "StickTime"));
				VisitCount.getInstance().initLRUMap(v.SiteID, "Article", "PV", 10000,
						new LeafExitEventListener("Article", "PV"));
				VisitCount.getInstance().initLRUMap(v.SiteID, "Image", "PV", 5000,
						new LeafExitEventListener("Image", "PV"));
				VisitCount.getInstance().initLRUMap(v.SiteID, "Video", "PV", 5000,
						new LeafExitEventListener("Video", "PV"));
				siteMap.put(new Long(v.SiteID), "");
			}
			if ("Unload".equals(v.Event)) {
				VisitCount.getInstance().addAverage(v.SiteID, v.Type, "StickTime", "" + v.LeafID, v.StickTime);// 页均停留时间;
			} else {
				if (StringUtil.isNotEmpty(v.Type)) {
					if (v.Type.equals("AD")) {
						VisitCount.getInstance().add(v.SiteID, "Leaf", "AD", "" + v.LeafID);
						// 广告特别处理,类型为Leaf子类型为AD,因为类型必须=getStatType()才能由super.update()写入数据库
					} else {
						VisitCount.getInstance().add(v.SiteID, v.Type, "PV", "" + v.LeafID);// 点击量
						Counter.add(v.Type, v.LeafID);
						Counter.addTotalHitCount(v.SiteID);
						Counter.addTodayHitCount(v.SiteID);
					}
				}
			}
		}
		if (!"Unload".equals(v.Event)) {
			synchronized (lastVisitMap) {
				Queuex q = (Queuex) lastVisitMap.get(new Long(v.SiteID));
				if (q == null) {
					q = new Queuex(MaxLastLogCount);
					lastVisitMap.put(new Long(v.SiteID), q);
				}
				q.push(v);
				if (v.VisitTime - LastVisitLogUpdateTime > 180000) {
					updateAllLastVisitlog();
					LastVisitLogUpdateTime = v.VisitTime;
				}
			}
		}
	}

	public static void dealADClick(String siteID, String ADID) {
		VisitCount.getInstance().add(Long.parseLong(siteID), "Leaf", "AD", ADID);// 广告点击量
	}

	public void onPeriodChange(int type, long current) {
		if (type == AbstractStat.PERIOD_DAY) {
			VisitCount.getInstance().clearType("AD", true);
			VisitCount.getInstance().clearType("Article", true);
			VisitCount.getInstance().clearType("Image", true);
			VisitCount.getInstance().clearType("Video", true);
			long[] sites = VisitCount.getInstance().getSites();
			for (int i = 0; i < sites.length; i++) {
				Counter.clearTodayHitCount(sites[i]);
			}
		}
	}

	public void update(Transaction tran, VisitCount vc, long current, boolean isNewMonth, boolean isNewPeriod) {
		QueryBuilder qbAD = new QueryBuilder("update ZCAdvertisement set HitCount=HitCount+? where ID=?");
		QueryBuilder qbArticle = new QueryBuilder(
				"update ZCArticle set StickTime=(HitCount*StickTime+?*?)/(HitCount+?),HitCount=HitCount+? where ID=?");
		QueryBuilder qbImage = new QueryBuilder(
				"update ZCImage set StickTime=(HitCount*StickTime+?*?)/(HitCount+?),HitCount=HitCount+? where ID=?");
		QueryBuilder qbVideo = new QueryBuilder(
				"update ZCVideo set StickTime=(HitCount*StickTime+?*?)/(HitCount+?),HitCount=HitCount+? where ID=?");
		qbAD.setBatchMode(true);
		qbArticle.setBatchMode(true);
		qbImage.setBatchMode(true);
		qbVideo.setBatchMode(true);
		long[] sites = vc.getSites();
		for (int i = 0; i < sites.length; i++) {
			String[] items = vc.getItems(sites[i], "Leaf", "AD");
			for (int k = 0; k < items.length; k++) {
				long count = vc.get(sites[i], "Leaf", "AD", items[k]);
				if (count == 0) {
					continue;// 访问数为0的不需要更新
				}
				qbAD.add(count);
				qbAD.add(items[k]);
				qbAD.addBatch();
				// 不清空数值,以保证广告的访问量可以逐日查看
			}
			items = vc.getItems(sites[i], "Article", "PV");
			for (int k = 0; k < items.length; k++) {
				long count = vc.get(sites[i], "Article", "PV", items[k]);
				if (count == 0) {
					continue;// 访问数为0的不需要更新
				}
				long sticktime = vc.get(sites[i], "Article", "StickTime", items[k]);
				qbArticle.add(sticktime);
				qbArticle.add(count);
				qbArticle.add(count);
				qbArticle.add(count);
				qbArticle.add(items[k]);
				qbArticle.addBatch();
				VisitCount.getInstance().set(sites[i], "Article", "PV", items[k], 0);
			}
			items = vc.getItems(sites[i], "Image", "PV");
			for (int k = 0; k < items.length; k++) {
				long count = vc.get(sites[i], "Image", "PV", items[k]);
				if (count == 0) {
					continue;// 访问数为0的不需要更新
				}
				long sticktime = vc.get(sites[i], "Image", "StickTime", items[k]);
				qbImage.add(sticktime);
				qbImage.add(count);
				qbImage.add(count);
				qbImage.add(count);
				qbImage.add(items[k]);
				qbImage.addBatch();
				VisitCount.getInstance().set(sites[i], "Image", "PV", items[k], 0);
			}
			items = vc.getItems(sites[i], "Video", "PV");
			for (int k = 0; k < items.length; k++) {
				long count = vc.get(sites[i], "Video", "PV", items[k]);
				if (count == 0) {
					continue;// 访问数为0的不需要更新
				}
				long sticktime = vc.get(sites[i], "Video", "StickTime", items[k]);
				qbVideo.add(sticktime);
				qbVideo.add(count);
				qbVideo.add(count);
				qbVideo.add(count);
				qbVideo.add(items[k]);
				qbVideo.addBatch();
				VisitCount.getInstance().set(sites[i], "Video", "PV", items[k], 0);
			}
		}
		tran.add(qbAD);
		tran.add(qbArticle);
		tran.add(qbImage);
		tran.add(qbVideo);
		super.update(tran, vc, current, isNewMonth, isNewPeriod);
	}

	public synchronized void updateAllLastVisitlog() {
		if ("Y".equals(Config.getValue("RetainAllVisitLog"))) {// 不保留日志
			Object[] sites = lastVisitMap.keyArray();
			ZCVisitLogSet set = new ZCVisitLogSet();
			for (int i = 0; i < sites.length; i++) {
				set.add(getVisitlogSet(((Long) sites[i]).longValue(), true));
			}
			set.insert();
		}
	}

	public static ZCVisitLogSet getVisitlogSet(long siteID, boolean clearFlag) {
		ZCVisitLogSet set = new ZCVisitLogSet();
		Queuex q = getLastVisitQueue(siteID);
		if (q == null) {
			return set;
		}
		Visit[] arr = new Visit[q.size()];
		for (int i = q.size(); i > 0; i--) {
			arr[arr.length - i] = (Visit) q.get(i - 1);
		}
		for (int i = 0; i < arr.length; i++) {
			Visit v = arr[i];
			String district = District.getDistrictName(v.District);
			if (StringUtil.isNotEmpty(v.URL)) {
				ZCVisitLogSchema zv = new ZCVisitLogSchema();
				zv.setURL(v.URL);
				zv.setID(String.valueOf(LastVisitLogID++));
				zv.setIP(v.IP);
				zv.setAddTime(new Date(v.VisitTime));
				zv.setOS(v.OS);
				zv.setBrowser(v.Browser);
				zv.setScreen(v.Screen);
				zv.setDistrict(district);
				zv.setLanguage(v.Language);
				zv.setColorDepth(v.ColorDepth);
				zv.setCookieEnabled("" + v.CookieEnabled);
				zv.setJavaEnabled("" + v.JavaEnabled);
				zv.setFlashVersion(v.FlashVersion);
				zv.setSiteID(siteID);
				zv.setReferer(v.Referer);
				set.add(zv);
			}
		}
		if (clearFlag) {
			q.clear();// 必须清空
		}
		return set;
	}

	public static Queuex getLastVisitQueue(long siteID) {
		return (Queuex) lastVisitMap.get(new Long(siteID));
	}
}
