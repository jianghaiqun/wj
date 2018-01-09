package com.sinosoft.cms.seo;

/**
 * 处理URL数据.
 * @author congzn.
 * @date 2013-10-29
 */
public class UrlDataHanding implements Runnable {
	
	private String type;
	
	/**
	 * 处理数据.
	 * @param url
	 */
	public void dataHanding(String url,String type) {
		HandleHTML.getContent(LoadingPage.getContentFormUrl(url),type,url);
	}

	public void run() {
		while (!UrlQueue.isEmpty()) {
			dataHanding(UrlQueue.outElem(),type);
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
