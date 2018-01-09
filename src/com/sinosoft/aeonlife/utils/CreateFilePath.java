/**
 * Project Name:wj
 * File Name:CreateFilePath.java
 * Package Name:com.sinosoft.aeonlife.utils
 * Date:2016年6月12日上午9:23:42
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.aeonlife.utils;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:CreateFilePath <br/>
 * Function:TODO 组装SFTP的路径 <br/>
 * Date:2016年6月12日 上午9:23:42 <br/>
 *
 * @author:chouweigao
 */
public class CreateFilePath {
	private static final Logger logger = LoggerFactory.getLogger(CreateFilePath.class);
	/**
	 * 
	 * createFilePath:(创建文件路径). <br/>
	 * 
	 * @author chouweigao
	 * @return
	 */
	public static Map<String, List<String>> createFilePath() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> filepath = new ArrayList<String>();
		//取得配置文件中的sftp路径
		String val = Config.getValue(SftpCommon.SFTP_PATHS);
		filepath = Arrays.asList(val.split(SftpCommon.SPLIT_STR));
		/**
		 * 组装路径
		 * 快钱：bill
		 * 非凡：feifan
		 * 自用：own
		 * 去哪：quna
		 */
		for (String source : filepath) {
			/**
			 * windows 系统测试用
			 * source = "d:"+source;
			 */
			//source = "d:"+source; 
			List<String> paths = new ArrayList<String>();
			String path = source + cfp(SftpCommon.DOWNLOAD, SftpCommon.PATH_POLICYREQ);
			paths.add(path);
			String path1 = source + cfp(SftpCommon.UPLOAD, SftpCommon.PATH_POLICYRESULT);
			paths.add(path1);
			String path2 = source + cfp(SftpCommon.UPLOAD, SftpCommon.PATH_REFUNDRESULT);
			paths.add(path2);
			String path3 = source + cfp(SftpCommon.UPLOAD, SftpCommon.PATH_POLICYVALUE);
			paths.add(path3);
			String path4 = source + cfp(SftpCommon.DOWNLOAD, SftpCommon.PATH_REFUNDFUNDS);
			paths.add(path4);
			String path5 = source + cfp(SftpCommon.DOWNLOAD, SftpCommon.PATH_REFUNDAPPLY);
			paths.add(path5);
			String path6 = source + cfp(SftpCommon.UPLOAD, SftpCommon.STR_POLICYCT);
			paths.add(path6);
			
			if (source.contains(SftpCommon.STR_BILL)) {//快钱
				map.put(SftpCommon.STR_BILL, paths);
			}else if (source.contains(SftpCommon.STR_FEIFAN)) {//非凡
				map.put(SftpCommon.STR_FEIFAN, paths);
			}else if (source.contains(SftpCommon.STR_OWN)) {//自用
				map.put(SftpCommon.STR_OWN, paths);
			}else if (source.contains(SftpCommon.STR_QUNA)) {//去哪
				map.put(SftpCommon.STR_QUNA, paths);
			}else if (source.contains(SftpCommon.STR_FSL)) {//前海
				map.put(SftpCommon.STR_FSL, paths);
			}
		}
		logger.info("组装文件路径：{}", map);
		return map;
	}

	public static String cfp(String root, String buis) {

		StringBuffer sb = new StringBuffer();
		sb.append(root);
		sb.append(DateUtil.getCurrentDate(SftpCommon.FORMAT_YMD));
		sb.append(buis);
		return sb.toString();
	}

	/**
	 * 
	 * createFileName:(创建文件名称). <br/>
	 * 
	 * @author chouweigao
	 * @param size
	 * @return
	 */
	public static Map<String, List<String>> createFileName(String buis, int size, String fileType) {

		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = cfn(buis, size, fileType);
		map.put(buis, list);
		return map;
	}

	private static List<String> cfn(String buis, int size, String fileType) {

		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= size; i++) {
			StringBuffer sb = new StringBuffer();
			sb.append(SftpCommon.AEONLIFE);
			sb.append(SftpCommon.FILE_SPLIT);
			sb.append(buis);
			sb.append(SftpCommon.FILE_SPLIT);
			sb.append(SftpCommon.CURDATE);
			sb.append(SftpCommon.FILE_SPLIT);
			sb.append(String.valueOf(i));
			sb.append(fileType);
			list.add(sb.toString());
		}
		return list;
	}
}
