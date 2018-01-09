package com.sinosoft.search.crawl;

import com.sinosoft.framework.utility.RegexParser;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.search.DocumentList;
import com.sinosoft.search.SearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class UrlExtracter extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(UrlExtracter.class);

	private int threadCount = 1;// 只有在定制时才能大于1

	protected int aliveThreadCount = 0;

	protected int busyThreadCount = 0;// 主要是为了afterScript

	protected FileDownloader fileDownloader;

	protected int size;

	protected int extractOutCount;// 本层级解析出来的下一层级的URL数量

	protected int extractedCount;// 本层级已经解析过的数量

	ArrayList urlArr = new ArrayList();

	ArrayList rpArr = new ArrayList();

	ArrayList rpFilterArr = new ArrayList();

	CrawlConfig cc;

	protected void init(DocumentList list, int level, FileDownloader fileDownloader) {
		this.fileDownloader = fileDownloader;
		extractedCount = 0;
		cc = list.getCrawler().getConfig();
		String[] arr = cc.getUrlLevels()[level].trim().split("\n");
		urlArr.clear();
		rpArr.clear();
		for (int i = 0; i < arr.length; i++) {
			String url = arr[i].trim();
			if (StringUtil.isEmpty(url)) {
				continue;
			}
			url = url.trim();
			url = SearchUtil.escapeUrl(url);
			RegexParser rp = new RegexParser(url);
			urlArr.add(url);
			rpArr.add(rp);
		}
		if (cc.isFilterFlag()) {
			arr = cc.getFilterExpr().trim().split("\n");
			for (int i = 0; i < arr.length; i++) {
				String url = arr[i].trim();
				if (StringUtil.isEmpty(url)) {
					continue;
				}
				url = url.trim();
				RegexParser rp = new RegexParser(url);
				rpFilterArr.add(rp);
			}
		}
	}

	public void extract(DocumentList list, int level, FileDownloader fileDownloader) {
		init(list, level, fileDownloader);
		aliveThreadCount = threadCount;
		size = list.size();
		list.moveFirst();
		for (int i = 0; i < threadCount; i++) {
			UrlExtracterThread edt = new UrlExtracterThread();
			edt.setExtracter(this);
			edt.setList(list);
			edt.setLevel(level);
			edt.setThreadIndex(i);
			edt.start();
		}
		try {
			while (aliveThreadCount != 0) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public boolean isMatchedUrl(String url2, String refUrl) {
		// 过滤掉特定后缀
		String ext = ServletUtil.getUrlExtension(url2);
		if (StringUtil.isNotEmpty(ext)) {
			if (StringUtil.isEmpty(fileDownloader.getAllowExtension())
					|| fileDownloader.getAllowExtension().indexOf(ext) < 0) {
				if (fileDownloader.getDenyExtension().indexOf(ext) >= 0) {
					return false;
				}
			}
		}
		boolean matchedFlag = false;
		for (int i = 0; i < rpFilterArr.size(); i++) {
			RegexParser rp = (RegexParser) rpFilterArr.get(i);
			// 判断是否需要过滤
			synchronized (rp) {
				rp.setText(url2);
				if (rp.match()) {
					return false;
				}
			}
		}
		for (int i = 0; i < rpArr.size(); i++) {
			String url = (String) urlArr.get(i);
			RegexParser rp = (RegexParser) rpArr.get(i);
			// 比较两个URL的前端是否一致，如果不一致，则不需要判断
			if (url.indexOf('/', 8) > 0) {
				String prefix = url.substring(0, url.indexOf('/', 8));
				if (prefix.indexOf('$') < 0) {
					if (!url2.startsWith(prefix)) {
						continue;
					}
				}
			}

			// 判断是否符合规则
			synchronized (rp) {
				rp.setText(url2);
				if (rp.match()) {
					matchedFlag = true;
				}
			}
		}
		return matchedFlag;
	}
}
