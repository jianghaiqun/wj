package com.sinosoft.search.crawl;

import com.sinosoft.search.DocumentList;
import com.sinosoft.search.SearchUtil;
import com.sinosoft.search.WebDocument;

public class UrlExtracterThread extends Thread {
	private DocumentList list;

	private int level;

	private boolean isBusy;

	private UrlExtracter extracter;

	private int threadIndex;

	private WebDocument doc;

	public void run() {
		doc = list.next();
		int maxPageCount = list.getCrawler().getConfig().getMaxPageCount();
		int maxListCount = list.getCrawler().getConfig().getMaxListCount();
		int maxUrlLevel = list.getCrawler().getConfig().getUrlLevels().length;
		while (doc != null) {
			if (!list.getCrawler().task.checkStop()) {
				if (!isBusy) {
					synchronized (extracter) {
						this.extracter.busyThreadCount++;
					}
				}
				isBusy = true;
				if (doc.getLevel() == level - 1 && doc.isTextContent()) {
					String[] urls = SearchUtil.getUrlFromHtml(doc.getContentText());
					for (int k = 0; k < urls.length; k++) {
						if (maxPageCount > 0 && maxUrlLevel == doc.getLevel() + 2
								&& extracter.extractOutCount >= maxPageCount) {
							break;// 只在最一个层级判断文章内容页数量
						}
						if (maxListCount > 0 && maxUrlLevel == doc.getLevel() + 3
								&& extracter.extractOutCount >= maxListCount) {
							break;// 只在倒数第二个层级判断文章列表页数量
						}
						String url2 = urls[k];
						url2 = SearchUtil.normalizeUrl(url2, doc.getUrl());
						url2 = SearchUtil.escapeUrl(url2);
						if (extracter.isMatchedUrl(url2, doc.getUrl())) {
							if (!list.containsKey(url2)) {
								WebDocument doc2 = new WebDocument();
								doc2.setUrl(url2);
								doc2.setLevel(level);
								doc2.setRefUrl(doc.getUrl());
								list.put(doc2);
								CrawlScriptUtil util = new CrawlScriptUtil();
								util.doc = doc2;
								util.list = list;
								list.getCrawler().executeScript("before", util);
								extracter.extractOutCount++;
							} else {
								if (list.get(url2).getContent() != null) {// 如果已经下载了，也要加1
									extracter.extractOutCount++;
								}
							}
						}
					}
					extracter.extractedCount++;
					long percent = Math.round(extracter.extractedCount * 10000.0 / extracter.size);
					long p = percent % 100;
					String pStr = "" + p;
					if (p < 10) {
						pStr = "0" + p;
					}
				} else if (doc.getLevel() == level) {// 也需要检测同级，如果不符合则要删除
					if (!extracter.isMatchedUrl(doc.getUrl(), doc.getRefUrl())) {
						list.remove(doc.getUrl());
					}
				}
				doc = list.next();
			} else {
				if (isBusy) {
					synchronized (extracter) {
						this.extracter.busyThreadCount--;
					}
				}
				doc = null;// 此语句会导致循环退出，所以不需要break;
			}
		}
		synchronized (extracter) {
			isBusy = false;
			this.extracter.busyThreadCount--;
			this.extracter.aliveThreadCount--;
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public DocumentList getList() {
		return list;
	}

	public void setList(DocumentList list) {
		this.list = list;
	}

	public UrlExtracter getExtracter() {
		return extracter;
	}

	public void setExtracter(UrlExtracter extracter) {
		this.extracter = extracter;
	}

	public int getThreadIndex() {
		return threadIndex;
	}

	public void setThreadIndex(int threadIndex) {
		this.threadIndex = threadIndex;
	}
}
