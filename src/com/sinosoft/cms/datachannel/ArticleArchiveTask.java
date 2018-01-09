package com.sinosoft.cms.datachannel;

import com.sinosoft.cms.document.Article;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.schedule.SystemTask;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.schema.ZCArticleSchema;

public class ArticleArchiveTask extends SystemTask {

	public static void main(String[] args) {
		ArticleArchiveTask t = new ArticleArchiveTask();
		t.execute();
	}

	public void execute() {
		logger.info("定时归档任务");
		Transaction trans = new Transaction();
		int size = 500;
		int count = new QueryBuilder("select count(*) from ZCArticle where ArchiveDate<=?", DateUtil.getCurrentDateTime()).executeInt();
		int page = count / size;
		if (count % size == 0) {
			page = page - 1;
		}
		DataTable articleDt = new DataTable();
		for (int i = 0; i < page; i++) {
			articleDt = new QueryBuilder("select * from ZCArticle where ArchiveDate<=?", DateUtil.getCurrentDateTime()).executePagedDataTable(size, page);
			for (int j = 0; j < articleDt.getRowCount(); j++) {
				ZCArticleSchema article = new ZCArticleSchema();
				article.setID(articleDt.getString(j, "ID"));
				article.fill();
				article.setStatus(Article.STATUS_ARCHIVE);
				trans.add(article, Transaction.DELETE_AND_BACKUP);
			}
			// trans.setBackupOperator("CronTask");//手动执行的话需要设置，自动执行系统默认是置为
			// CronTask
			trans.setBackupMemo("Archive");
			trans.commit();
			trans.clear();
		}
		logger.info("扫描定时归档任务结束");
	}

	public String getName() {
		return "对文章进行归档";
	}

	public String getID() {
		return "com.sinosoft.article.ArticleArchiveTask";
	}

	public String getCronExpression() {
		return "30 * * * * *";
	}
}
