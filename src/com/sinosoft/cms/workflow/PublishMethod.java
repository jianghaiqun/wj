package com.sinosoft.cms.workflow;

import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.site.CatalogSite;
import com.sinosoft.framework.utility.Executor;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.workflow.Context;
import com.sinosoft.workflow.methods.NodeMethod;

public class PublishMethod extends NodeMethod {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sinosoft.workflow.methods.NodeMethod#execute(com.sinosoft.workflow.Context)
	 */
	public void execute(Context context) {
		context.getTransaction().addExecutor(new Executor(context.getInstance().getDataID()) {
			public boolean execute() {
				ZCArticleSet set = new ZCArticleSet();
				ZCArticleSchema article = new ZCArticleSchema();
				article.setID(this.param.toString());
				article.fill();
				if (article.getPublishDate() != null && article.getPublishDate().getTime() > System.currentTimeMillis()) {
					article.setStatus(Article.STATUS_TOPUBLISH);// 末到时间则是待发布状态
				} else {
					article.setStatus(Article.STATUS_PUBLISHED);
				}
				set.add(article);
				Publisher p = new Publisher();
				p.publishArticle(set);
				CatalogSite catalogSite=new CatalogSite();
				catalogSite.publishIndex();
				return true;
			}
		});
	}
}
