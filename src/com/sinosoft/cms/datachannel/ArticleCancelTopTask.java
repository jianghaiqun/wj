package com.sinosoft.cms.datachannel;

import java.util.Date;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.schedule.SystemTask;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;

public class ArticleCancelTopTask extends SystemTask {

	public void execute() {
		QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='0' where topflag='1' " + "and topdate is not null and topdate<=?", new Date());
		qb.executeNoQuery();
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where topflag='1' and topdate is not null and topdate<=?", new Date()));
		if (set != null && set.size() > 0) {
			Publisher p = new Publisher();
			p.publishArticle(set);
		}
	}

	public String getName() {
		return "过期的置顶文章取消置顶";
	}

	public String getID() {
		return "com.sinosoft.article.ArticleCancelTopTask";
	}

	public String getCronExpression() {
		return "30 * * * * *";
	}
}
