package com.sinosoft.cms.site;

import java.io.File;

import com.sinosoft.cms.document.Article;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DBUtil;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.schedule.SystemTask;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

public class TagUpdateTask extends SystemTask {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.schedule.GeneralTask#execute()
	 */
	public void execute() {
		new Thread() {// 耗时可能会很长，需要单开线程，以免影响其他定时任务的执行
			public void run() {
				// 分站点执行
				Object[] sites = new QueryBuilder("select ID from ZCSite").executeDataTable().getColumnValues(0);
				for (int m = 0; m < sites.length; m++) {
					Mapx countMap = new Mapx();// 存放各标签被使用次数
					QueryBuilder qb = new QueryBuilder("select Tag from ZCArticle where Status=? and SiteID=?", Article.STATUS_PUBLISHED, sites[m]);
					int total = DBUtil.getCount(qb);
					int pageSize = 20000;
					// 计算次数
					for (int i = 0; i * pageSize < total; i++) {
						DataTable dt = qb.executePagedDataTable(pageSize, i);
						cache(dt, i);
						for (int j = 0; j < dt.getRowCount(); j++) {
							String tag = dt.getString(j, "Tag");
							String[] arr = tag.split("\\s");
							for (int k = 0; k < arr.length; k++) {
								if (StringUtil.isNotEmpty(arr[k])) {
									countMap.put(arr[k], countMap.getInt(arr[k]) + 1);
								}
							}
						}
					}
					Object[] tags = countMap.keyArray();

					// 更新引用次数到数据库
					QueryBuilder updater = new QueryBuilder("Update ZCTag set UsedCount=? where SiteID=? and Tag=?");
					updater.setBatchMode(true);
					for (int i = 0; i < tags.length; i++) {
						updater.add(countMap.getInt(tags[i]));
						updater.add(sites[m]);
						updater.add(tags[i]);
						updater.addBatch();
					}
					updater.executeNoQuery();

					// 计算关联关系
					int TagPageSize = 100;// 考虑到内存消耗，每次只计算100个Tag的关联关系
					Mapx[] relas = new Mapx[TagPageSize];
					for (int p = 0; p < tags.length; p += TagPageSize) {
						for (int i = 0; i * pageSize < total; i++) {
							DataTable dt = getCache(i);
							for (int j = 0; j < dt.getRowCount(); j++) {
								String articleTag = " " + dt.getString(j, "Tag") + " ";
								for (int k = p; k < tags.length && k < p + TagPageSize; k++) {
									Mapx map = relas[k - p];
									if (map == null) {
										relas[k - p] = map = new Mapx(1000);// 不存所有，只存前1000个
									}
									if (articleTag.indexOf(" " + tags[k] + " ") >= 0) {
										String[] arr = articleTag.split("\\s");
										for (int q = 0; q < arr.length; q++) {
											if (StringUtil.isNotEmpty(arr[q]) && !arr[q].equals(tags[k])) {
												map.put(arr[q], map.getInt(arr[q]) + 1);
											}
										}
									}
								}
							}
						}
						// 更新100个关联关系
						updater = new QueryBuilder("Update ZCTag set RelaTag=? where SiteID=? and Tag=?");
						updater.setBatchMode(true);
						for (int i = 0; i < relas.length; i++) {
							Mapx map = relas[i];
							if (map == null) {
								break;
							}
							DataTable dt = map.toDataTable();
							dt.sort("Value", "desc");
							StringBuffer sb = new StringBuffer();
							for (int j = 0; j < 30 && j < dt.getRowCount(); j++) {// 只存前30个关联TAG
								if (j != 0) {
									sb.append(" ");
								}
								sb.append(dt.getString(j, "Key"));
							}
							updater.add(sb.toString());
							updater.add(sites[m]);
							updater.add(tags[i]);
							updater.addBatch();
						}
						updater.executeNoQuery();
					}
					for (int i = 0; i * pageSize < total; i++) {
						FileUtil.deleteEx(Config.getContextRealPath() + "WEB-INF/cache/TagCache" + i + ".dat");
					}
				}
			}
		}.start();
	}

	private void cache(DataTable dt, int i) {
		FileUtil.serialize(dt, Config.getContextRealPath() + "WEB-INF/cache/TagCache" + i + ".dat");
	}

	private DataTable getCache(int i) {
		File f = new File(Config.getContextRealPath() + "WEB-INF/cache/TagCache" + i + ".dat");
		if (f.exists()) {
			return (DataTable) FileUtil.unserialize(f.getAbsolutePath());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.schedule.AbstractTask#getID()
	 */
	public String getID() {
		return "com.sinosoft.cms.site.TagUpdateTask";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.schedule.AbstractTask#getName()
	 */
	public String getName() {
		return "Tag热度/关联性计算";
	}

	public static void main(String[] args) {
		TagUpdateTask tut = new TagUpdateTask();
		tut.execute();
	}
}
