package com.sinosoft.datachannel; 

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SiteMapFilenameFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		return isListSHTML(name);
	}
	
	private boolean isListSHTML(String name){
		if (name.indexOf(".") < 0) {
			return true;
		}
		return matcher(name);
	}
	
	private boolean matcher(String temp) {
		String regEx = "(index_|list_|list)[0-9]*(.shtml)$";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(temp);
		return mat.find();
	}
}
