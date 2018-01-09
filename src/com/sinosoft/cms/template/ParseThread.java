package com.sinosoft.cms.template;

import java.io.File;
import java.util.ArrayList;

class ParseThread implements Runnable {

	private long siteID;
	private File template;
	private int level;
	private boolean isPageBlock;
	private ArrayList list;
	public ParseThread(long cSiteID,File cTemplate,int cLevel,boolean mIsPageBlock,ArrayList cList){
		
		siteID = cSiteID;
		template = cTemplate;
		level = cLevel;
		isPageBlock = mIsPageBlock;
		list = cList;
	}
	public ParseThread(){
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ParserCache pc = new ParserCache();
		pc.update(siteID, template, level, isPageBlock, list);
		
	}
	public long getSiteID() {
		return siteID;
	}
	public void setSiteID(long siteID) {
		this.siteID = siteID;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isPageBlock() {
		return isPageBlock;
	}
	public void setPageBlock(boolean isPageBlock) {
		this.isPageBlock = isPageBlock;
	}
	public ArrayList getList() {
		return list;
	}
	public void setList(ArrayList list) {
		this.list = list;
	}
	public File getTemplate() {
		return template;
	}
	public void setTemplate(File template) {
		this.template = template;
	}
	
	
}
