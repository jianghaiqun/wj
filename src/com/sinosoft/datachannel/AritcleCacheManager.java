package com.sinosoft.datachannel;

import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.utility.ArticleCachePlan;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AritcleCacheManager extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.AritcleCacheManager";

	public boolean isRunning(long id) {
		return false;
	}

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "文章模块缓存");
		map.put("1", "栏目自动发布");

		return map;
	}

	public void execute(long id) {
		if ("0".equals(id + "")) {
			try {
				// QueryBuilder qb = new
				// QueryBuilder(" select CronExpression from zdSchedule where TypeCode=? and SourceID=? ");
				// qb.add(CODE);
				// qb.add("0");
				// String CronExpression = qb.executeOneValue() + "";
				// if (StringUtil.isEmpty(CronExpression)) {
				// LogUtil.error("定时计划" + CODE + "时间表达式为空.");
				// return;
				// }
				//
				// String PreviousRunTime =
				// FormatDate(CronMonitor.getPreviousRunTime(CronExpression));
				// String NextRunTime =
				// FormatDate(CronMonitor.getNextRunTime(CronExpression));
				// System.out.println(PreviousRunTime);
				// System.out.println(NextRunTime);

				String sql = "select CodeValue from zdcode  where ParentCode='ArticlePublish'  order by modifytime ,addtime  limit 0,1";
				String catalogID = (String) new QueryBuilder(sql).executeOneValue();

				// 修改发布时间
				new QueryBuilder("update zdcode set modifytime = ? where ParentCode='ArticlePublish' and CodeType='ArticlePublish'  and CodeValue= ?", PubFun.getCurrent(), catalogID).executeNoQuery();

				ArticleCachePlan activityService = new ArticleCachePlan();
				activityService.cacheBlock(catalogID);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		} else if ("1".equals(id + "")) {
			try {
				String sql = "select CodeValue from zdcode  where ParentCode='PublishList' ";
				DataTable dt = new QueryBuilder(sql).executeDataTable();

				for (int i = 0; i < dt.getRowCount(); i++) {
					DataRow dr = dt.get(i);
					Publisher p = new Publisher();
					p.publishCatalog(dr.getLong(0), false, false, true);

				}
			} catch (Exception e) {
				logger.error("定时发布列表页面存在问题." + e.getMessage(), e);
			}

		}
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "文章缓存定时任务";
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	public static String FormatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return CODE;
	}

}
