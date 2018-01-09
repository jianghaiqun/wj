/**
 * Project Name:wj
 * File Name:UnZipFile.java
 * Package Name:com.sinosoft.aeonlife.utils
 * Date:2016年6月12日上午11:12:28
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.aeonlife.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ClassName:UnZipFile <br/>
 * Function:TODO 解压zip文件. <br/>
 * Date:2016年6月12日 上午11:12:28 <br/>
 *
 * @author:chouweigao 
 */
public class UnZipFile {

	/**
	 * 
	 * unZip:(解压zip文件). <br/>
	 *
	 * @author chouweigao
	 * @param list
	 */
	public static void unZip(Map<String,List<String>> map){
		Set<String> set = map.keySet();
		for (String key : set) {
			List<String> listpath = map.get(key);
			for (String descDir :listpath) {
				ZipUtils.unZipFiles(descDir);
			}
		}
	}
}

