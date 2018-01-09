package com.sinosoft.cms.pub;

import com.sinosoft.framework.utility.Mapx;

/**
 */
public class RSSItem {
	private Mapx attrMap = new Mapx();

	public String getAuthor() {
		return attrMap.getString("author");
	}

	public void setAuthor(String author) {
		attrMap.put("author", author);
	}

	public String getDescription() {
		return attrMap.getString("description");
	}

	public void setDescription(String description) {
		attrMap.put("description", description);
	}

	public String getLink() {
		return attrMap.getString("link");
	}

	public void setLink(String link) {
		attrMap.put("link", link);
	}

	public String getPubDate() {
		return attrMap.getString("pubDate");
	}

	public void setPubDate(String pubDate) {
		attrMap.put("pubDate", pubDate);
	}

	public String getTitle() {
		return attrMap.getString("title");
	}

	public void setTitle(String title) {
		attrMap.put("title", title);
	}

	public String getComments() {
		return attrMap.getString("comments");
	}

	public void setComments(String comments) {
		attrMap.put("comments", comments);
	}

	public void setAttribute(String attrName, String attrValue) {
		attrMap.put(attrName, attrValue);
	}

	public String getAttribute(String attrName) {
		return attrMap.getString(attrName);
	}

	public Mapx getAttributeMap() {
		return attrMap;
	}

}
