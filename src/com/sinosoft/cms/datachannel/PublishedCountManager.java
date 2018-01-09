package com.sinosoft.cms.datachannel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.document.MessageCache;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Priv;

public class PublishedCountManager extends TimerTask {
	/**
	 * 在com.sinosoft.framework.MainContextListener类中的contextInitialized方法中，添加
	 * 
	 * new Timer().schedule(new PublishedCountManager(), 0, 300000);3分钟
	 * 完成每天运行一次监测程序
	 * 
	 * 监测程序，主要任务是监测在指定的“文章更新的最长时间间隔” t ，以此栏目发布的最后一篇文章为标准，
	 * 超过t时间段后，如果此栏目还没有添加发布新的文章，发短消息提示该栏目的编辑、审核和管理员， 以及系统管理员
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PublishedCountManager pc = new PublishedCountManager();
	}

	public void run() {
		try {
			sendMessage();
		} catch (Exception ex) {
			ex.getStackTrace();
		}
	}

	/**
	 * @return 超过t时间段没有发布新文章的栏目
	 */
	public Map findCatalog() {
		QueryBuilder qb = new QueryBuilder(
				"select ca.siteid,ca.id,ca.name,datediff(now(),b.addtime) as time"
						+ " from zccatalog ca right join "
						+ " (select c.siteid,c.id cid,c.type,c.name cname,max(a.addtime) addtime"
						+ " from zccatalog c left join zcarticle a "
						+ " on c.id=a.catalogid and a.status=?"
						+ " where c.type=1" + " group by c.siteid,c.id) b"
						+ " on ca.id = b.cid" + " where ca.type=1"
						+ " group by ca.siteid,ca.id", Article.STATUS_PUBLISHED);
		DataTable dt = qb.executeDataTable();
		Map result = new HashMap();
		Map map = new HashMap();
		for (int i = 0; i < dt.getRowCount(); i++) {
			Object obj = dt.get(i, "time");
			if (null != obj) {
				map.put(dt.getString(i, "id"), obj.toString());
			}
		}

		if (null != map) {
			Set keys = map.keySet();
			Iterator it = keys.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				long id = Long.parseLong((String) obj);
				int set = getTime(id);
				int src = Integer.parseInt((String) map.get(obj));
				if (set != 0) {
					if (src > set || src == set) {
						result.put(obj, String.valueOf(src));
					}
				}
			}
		}
		return result;
	}

	// 读取数据库中catalogconfig 中的prop3的值
	public int getTime(long catalogID) {
		QueryBuilder qb = new QueryBuilder(
				"select prop3 from zccatalogconfig where catalogid=?",
				catalogID);
		DataTable dt = qb.executeDataTable();
		String str = (String) dt.get(0, "prop3");
		if (StringUtil.isNotEmpty(str)) {
			int time = 0;
			try {
				time = Integer.parseInt(str);
				if (time > 0) {
					return time;
				}
			} catch (NumberFormatException ex) {
				return time;
			}
		}
		return 0;
	}

	// 发消息给该栏目的编辑、审核和管理员，以及系统管理员
	public void sendMessage() {
		QueryBuilder qb = new QueryBuilder("select * from zduser");
		DataTable dt = qb.executeDataTable();
		Map map = findCatalog();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String id = (String) it.next();
			List list = new ArrayList();
			String pname = CatalogUtil.getName(CatalogUtil.getParentID(id));
			for (int i = 0; i < dt.getRowCount(); i++) {
				String name = (String) dt.get(i, "username");
				boolean audit = Priv.getPriv(name, Priv.ARTICLE, CatalogUtil
						.getInnerCode(id), Priv.ARTICLE_AUDIT);
				boolean browse = Priv.getPriv(name, Priv.ARTICLE, CatalogUtil
						.getInnerCode(id), Priv.ARTICLE_BROWSE);
				boolean manager = Priv.getPriv(name, Priv.ARTICLE, CatalogUtil
						.getInnerCode(id), Priv.ARTICLE_MANAGE);
				if (audit || browse || manager) {
					list.add(name);
				}
			}
			if (list.contains("admin")) {
				list.remove("admin");
			}
			String[] names = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				names[i] = (String) list.get(i);
			}
			String[] admin = { "admin" };
			
			QueryBuilder qb2 = new QueryBuilder(
					"select id,touser from zcmessage where readflag='0' and content like ?",
					"%" + pname + "-" + CatalogUtil.getName(id) + "%");
			DataTable dt2 = qb2.executeDataTable();
			if (dt2.getRowCount() > 0) {
				for(int j=0;j<dt2.getRowCount();j++){
					long mid = dt.getLong(j,"id");
					String touser = dt.getString(j, "touser");
					if("admin".equals(touser)){
						if (StringUtil.isNotEmpty(pname)) {
							new QueryBuilder("update zcmessage set content=? where id =?",
									"系统管理员，您好，"
									+ SiteUtil.getName(CatalogUtil.getSiteID(id))
									+ "站点下的" + pname + "-" + CatalogUtil.getName(id)
									+ "栏目，已经" + map.get(id) + "天没有更新文章了。",
									mid);
						}else{
							new QueryBuilder("update zcmessage set content=? where id =?",
									"系统管理员，您好，"
									+ SiteUtil.getName(CatalogUtil.getSiteID(id))
									+ "站点下的" + CatalogUtil.getName(id)
									+ "栏目，已经" + map.get(id) + "天没有更新文章了。",
									mid);
						}
					}else{
						if (StringUtil.isNotEmpty(pname)) {
							new QueryBuilder("update zcmessage set content=? where id =?",
									"您好，" + pname + "-"
									+ CatalogUtil.getName(id) + "栏目，已经" + map.get(id)
									+ "天没有更新文章了。",
									mid);
						}else{
							new QueryBuilder("update zcmessage set content=? where id =?",
									"您好，" + CatalogUtil.getName(id) 
									+ "栏目，已经" + map.get(id)
									+ "天没有更新文章了。",
									mid);
						}
					}
					
				}
			} else {
				if (StringUtil.isNotEmpty(pname)) {
					MessageCache.addMessage("文章更新提示", "您好，" + pname + "-"
							+ CatalogUtil.getName(id) + "栏目，已经" + map.get(id)
							+ "天没有更新文章了。", names, "System");

					MessageCache.addMessage("文章更新提示", "系统管理员，您好，"
							+ SiteUtil.getName(CatalogUtil.getSiteID(id))
							+ "站点下的" + pname + "-" + CatalogUtil.getName(id)
							+ "栏目，已经" + map.get(id) + "天没有更新文章了。", admin,
							"System");
				} else {
					MessageCache.addMessage("文章更新提示", "您好，"
							+ CatalogUtil.getName(id) + "栏目，已经" + map.get(id)
							+ "天没有更新文章了。", names, "System");

					MessageCache.addMessage("文章更新提示", "系统管理员，您好，"
							+ SiteUtil.getName(CatalogUtil.getSiteID(id))
							+ "站点下的" + CatalogUtil.getName(id) + "栏目，已经"
							+ map.get(id) + "天没有更新文章了。", admin, "System");
				}
			}
		}
	}
}
