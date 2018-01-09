package com.sinosoft.cms.template;

public class HtmlNameRule {
	private int level; //根据路径计算出的文件目录层级
		
	private String nameRuleStr; //原始字符串 形如 /${Year}/${Month}/${Day}/${catalog.ID}-${document.ID}.shtml
		
	private String fullPath; //解析后的全路径
		
	private String dirPath; //不包含文件名的路径
	
	private String fileName; //文件名

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getNameRuleStr() {
		return nameRuleStr;
	}

	public void setNameRuleStr(String nameRuleStr) {
		this.nameRuleStr = nameRuleStr;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
