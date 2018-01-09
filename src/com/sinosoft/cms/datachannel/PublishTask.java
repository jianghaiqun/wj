package com.sinosoft.cms.datachannel;

import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.schedule.SystemTask;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZCSiteSet;

import java.util.ArrayList;
import java.util.Date;

public class PublishTask extends SystemTask {

	public void execute() {
		logger.info("PublishTask定时发布任务");
		Publisher p = new Publisher();
		// 发布文章
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder(
				" where publishdate<=? and status =" + Article.STATUS_TOPUBLISH ,new Date()));

		if (set != null && set.size() > 0) {
			logger.info("需发布文章篇数：{}", set.size());
			for(int i = 0;i<set.size();i ++){
				if(set.get(i).getFirstPublishDate()==null){
					Date pubDate = set.get(i).getPublishDate();
					set.get(i).setFirstPublishDate(pubDate);
				}
			}
			

			p.publishArticle(set);
		}

		// 下线文章
		set = article.query(new QueryBuilder("where downlinedate<=? and status=?", 
				new Date(), Article.STATUS_PUBLISHED));
		if (set != null && set.size() > 0) {
			logger.info("需下线文章篇数：{}", set.size());

			Mapx catalogMap = new Mapx();
			for (int i = 0; i < set.size(); i++) {
				set.get(i).setStatus(Article.STATUS_OFFLINE); // 更新状态
				set.get(i).setTopFlag("0");
				catalogMap.put(set.get(i).getCatalogID() + "", set.get(i).getCatalogID());
			}

			if (set.update()) {
				p.deletePubishedFile(set);
				Object[] vs = catalogMap.valueArray();
				for (int i = 0; i < catalogMap.size(); i++) {
					p.publishCatalog(Long.parseLong(vs[i].toString()), false, false ,true);
				}
			}
		}
		
        //归档文章
//		DataTable dt = new QueryBuilder("select distinct catalogid from zcarticle where status=?",Article.STATUS_ARCHIVE).executeDataTable();
//		if (dt != null && dt.getRowCount() > 0) {
//			for (int j = 0; j < dt.getRowCount(); j++) {
//				p.publishCatalog(dt.getLong(j, "catalogid"), false, false);
//			}
//		}
		
		//将站点首页生成为index.html
		if("Y".equals(Config.getValue("RewriteIndex"))){
			ZCSiteSet sites = new ZCSiteSchema().query();
			for (int i = 0; i < sites.size(); i++) {
				long siteID = sites.get(i).getID();
				String url = SiteUtil.getURL(siteID)+"/index.shtml";
				String content = FileUtil.readURLText(url);
				if(StringUtil.isNotEmpty(content)){
					FileUtil.writeText(SiteUtil.getAbsolutePath(siteID)+"/index.html", content);
					ArrayList list = new ArrayList();
					list.add(SiteUtil.getAbsolutePath(siteID)+"/index.html");
					Deploy d = new Deploy();
					d.addJobs(siteID, list);
				}
			}
		}

		logger.info("PublishTask发布任务结束");
	}

	public String getID() {
		return "com.sinosoft.cms.datachannel.PublishTask";
	}

	public String getName() {
		return "发布指定日期的文章";
	}

	public static void main(String[] args) {
		PublishTask p = new PublishTask();
		p.execute();
	}
}
