package com.sinosoft.cms.document;

import com.sinosoft.framework.schedule.SystemTask;

public class ArticleRelaTask extends SystemTask {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.schedule.GeneralTask#execute()
	 */
	public void execute() {
		ArticleRelaIndexer ari = new ArticleRelaIndexer();
		ari.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.schedule.AbstractTask#getCronExpression()
	 */
	public String getCronExpression() {
		return "* * * * *";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.schedule.AbstractTask#getID()
	 */
	public String getID() {
		return "com.sinosoft.cms.document.ArticleRelaTask";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.schedule.AbstractTask#getName()
	 */
	public String getName() {
		return "相关文章索引";
	}

}
