package com.sinosoft.cms.workflow;

import com.sinosoft.cms.document.Article;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.workflow.Context;
import com.sinosoft.workflow.methods.NodeMethod;

public class WaitPublishMethod extends NodeMethod {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sinosoft.workflow.methods.NodeMethod#execute(com.sinosoft.workflow
	 * .Context)
	 */
	public void execute(Context context) {
		// QueryBuilder qb = new
		// QueryBuilder("update ZCArticle set Status=? where ID=?",
		// Article.STATUS_TOPUBLISH, context
		// .getInstance().getDataID());
		// context.getTransaction().add(qb);
		QueryBuilder qb = new QueryBuilder("update ZCArticle set Status=? where ID=?", Article.STATUS_TOPUBLISH, Long.parseLong(context.getInstance().getDataID()));
		context.getTransaction().add(qb);
	}

}
