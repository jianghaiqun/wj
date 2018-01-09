package com.sinosoft.cms.pub;

import java.util.ArrayList;

import com.sinosoft.framework.utility.Mapx;

/**
 */
public class RSSChannel extends RSSItem {
	private Mapx attrMap = new Mapx();

	private ArrayList items = new ArrayList();

	public void addItem(RSSItem item) {
		items.add(item);
	}

	public String getTitle() {
		return attrMap.getString("title");
	}

	public void setTitle(String title) {
		attrMap.put("title", title);
	}

	public int size() {
		return items.size();
	}

	public RSSItem getItem(int i) {
		return (RSSItem) items.get(i);
	}

	public String getLink() {
		return attrMap.getString("link");
	}

	public void setLink(String link) {
		attrMap.put("link", link);
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
