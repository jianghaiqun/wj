/**
 * Project Name:FrontTrade
 * File Name:ReadFtpFile.java
 * Package Name:com.finance.util
 * Date:2016年5月26日下午5:30:31
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.aeonlife.utils;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:ReadFtpFile <br/>
 * Function:TODO 读取csv文件 <br/>
 * Date:2016年5月26日 下午5:30:31 <br/>
 *
 * @author:chouweigao
 */
public class ReadCSVUtils {
	private static final Logger logger = LoggerFactory.getLogger(ReadCSVUtils.class);

	@SuppressWarnings("finally")
	public static Map<String,List<String>> loadCSV(String path) {
		
		File file = new File(path);
		if (!file.exists()) {
			logger.error("文件不存在：{}", file.getPath());
			return null;
		}
		File[] fis = file.listFiles();
		if (fis == null || fis.length == 0) {
			logger.error("{}没有文件.", file.getPath());
			return null;
		}
		
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		InputStream ins = null;
		BufferedReader reader = null;
		try {
			for (int i = 0; i < fis.length; i++) {
				String name = fis[i].getName();
				if (name.endsWith(SftpCommon.CSV)) {
					ins = new FileInputStream(fis[i]);
					reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));// 换成你的文件名
					String line = null;
					while ((line = reader.readLine()) != null) {
						if (line != null && !"".equals(line)) {
							list.add(line);
						}
						logger.error("文件名称：{}|文件内容：{}", name, line);
					}
					if (name.contains(SftpCommon.STR_POLICYREQ)) {
						map.put(SftpCommon.STR_POLICYREQ, list);
					} else if (name.contains(SftpCommon.STR_POLICYRESULT)) {
						map.put(SftpCommon.STR_POLICYRESULT, list);
					} else if (name.contains(SftpCommon.STR_POLICYVALUE)) {
						map.put(SftpCommon.STR_POLICYVALUE, list);
					} else if (name.contains(SftpCommon.STR_REFUNDRESULT)) {
						map.put(SftpCommon.STR_REFUNDRESULT, list);
					}
				}
			}
		} catch (Exception e) {
			logger.error("读取文件异常" +  e.getMessage(), e);
		} finally {
			try {
				if (ins != null) {
					ins.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				logger.error("关闭文件流异常" + e.getMessage(), e);
			}
			return map;
		}
	}

	/**
	 * loadCSVFromZip:(从文件路径加载csv文件). <br/>
	 *
	 * @author liuhongyu
	 * @param path
	 *            ,zip压缩包所在的文件夹。
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, List<String>> loadCSVFromZip(String path) {

		File file = new File(path);
		if (!file.exists()) {
			logger.error("文件不存在：{}", file.getPath());
			return null;
		}
		if (!file.isDirectory()) {
			logger.error("文件不是一个路径：{}", file.getPath());
			return null;
		}
		File[] fis = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {

				return name.endsWith(".zip");
			}
		});
		if (fis == null || fis.length == 0) {
			logger.error("{}下没有文件.", file.getPath());
			return null;
		}
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < fis.length; i++) {
			ZipFile zipFile = null;
			InputStream entryInputStream = null;
			BufferedReader reader = null;
			try {
				zipFile = new ZipFile(fis[i]);
				Enumeration<ZipEntry> entries = zipFile.getEntries();
				ZipEntry zipEntry = null;
				while (entries.hasMoreElements()) {
					zipEntry = (ZipEntry) entries.nextElement();
					String name = zipEntry.getName();
					if (!name.endsWith(".csv")) {
						continue;
					}
					entryInputStream = zipFile.getInputStream(zipEntry);
					reader = new BufferedReader(new InputStreamReader(entryInputStream, "UTF-8"));// 换成你的文件名
					String line = null;
					while ((line = reader.readLine()) != null) {
						if (line != null && !"".equals(line)) {
							list.add(line);
						}
						logger.info("文件名称：{}|文件内容：{}",name ,line);
					}
				}

			} catch (IOException e) {
				logger.error(file.getName() + "文件读取失败。" + e.getMessage(), e);
			} finally {
				try {
					if (entryInputStream != null)
						entryInputStream.close();
					if (reader != null)
						reader.close();
					if (zipFile != null)
						zipFile.close();
				} catch (IOException e) {
					logger.error("文件或流关闭异常" + e.getMessage(), e);
				}

			}
		}
		String name = file.getName();
		if (name.contains(SftpCommon.STR_POLICYREQ)) {
			map.put(SftpCommon.STR_POLICYREQ, list);
		} else if (name.contains(SftpCommon.STR_POLICYRESULT)) {
			map.put(SftpCommon.STR_POLICYRESULT, list);
		} else if (name.contains(SftpCommon.STR_POLICYVALUE)) {
			map.put(SftpCommon.STR_POLICYVALUE, list);
		} else if (name.contains(SftpCommon.STR_REFUNDRESULT)) {
			map.put(SftpCommon.STR_REFUNDRESULT, list);
		}

		return map;

	}
	
	/**
	 * loadTxt:(从文件路径加载txt文件). <br/>
	 *
	 * @author yuzaijiang
	 * @param path
	 * @return 文件数据集合
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static List<String> loadTxt(String path) {
		List<String> dataCol = new ArrayList<String>();
		File file = new File(path);
		BufferedReader reader = null;
		try {
			if(file.exists()){
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	            	dataCol.add(tempString);
	            }
			}
        } catch (IOException e) {
			logger.error(e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
					logger.error(e1.getMessage(), e1);
                }
            }
        }
		return dataCol;

	}

	public static void main(String[] args) {

		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map = ReadCSVUtils.loadCSVFromZip("D:\\test\\policyReq");

//		System.out.println(map);

	}
}
