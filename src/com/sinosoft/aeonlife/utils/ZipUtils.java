/**
 * Project Name:FrontTrade
 * File Name:CompressFile.java
 * Package Name:com.finance.util
 * Date:2016年5月31日下午1:38:35
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.aeonlife.utils;

/**
 * ClassName:CompressFile <br/>
 * Function:TODO Zip包压缩和解压. <br/>
 * Date:2016年5月31日 下午1:38:35 <br/>
 *
 * @author:chouweigao 
 */

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * 
 * ClassName: ZipUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(Zip包压缩和解压). <br/>
 * date: 2016年6月7日 下午4:21:47 <br/>
 *
 * @author chouweigao
 * @version
 */
public class ZipUtils {
	private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * 压缩文件 仅能压缩.csv文件
	 * 
	 * @param srcfile
	 *            File[] 需要压缩的文件列表
	 * @param zipfile
	 *            File 压缩后的文件
	 */
	public static void ZipFiles(java.io.File[] srcfile, java.io.File zipfile) {

		byte[] buf = new byte[1024];
		ZipOutputStream out = null;
		FileInputStream in = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipfile));
			for (int i = 0; i < srcfile.length; i++) {
				if (!srcfile[i].getName().endsWith(".csv"))
					continue;
				in = new FileInputStream(srcfile[i]);
				out.putNextEntry(new ZipEntry(srcfile[i].getName()));
				String str = srcfile[i].getName();
				logger.info("压缩文件名称：{}", str);
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.close();

			// for (int i = 0; i < srcfile.length; i++) {
			// srcfile[i].delete();
			// }

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 压缩文件 仅能压缩.csv文件
	 *
	 * @param srcfile
	 *            File[] 需要压缩的文件列表
	 * @param zipfile
	 *            File 压缩后的文件
	 */
	public static void zipFiles(java.io.File[] srcfile, java.io.File zipfile) {

		byte[] buf = new byte[1024];
		ZipOutputStream out = null;
		FileInputStream in = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipfile));
			for (int i = 0; i < srcfile.length; i++) {
				in = new FileInputStream(srcfile[i]);
				out.putNextEntry(new ZipEntry(srcfile[i].getName()));
				String str = srcfile[i].getName();
				logger.info("压缩文件名称：{}", str);
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * zip解压缩
	 * 
	 * @param zipfile
	 *            File 需要解压缩的文件
	 * @param descDir
	 *            String 解压后的目标目录
	 */
	public static void unZipFiles(String descDir) {

		InputStream in = null;
		OutputStream out = null;
		ZipFile zf = null;
		try {
			File file = new File(descDir);
			if (!file.exists()) {
				logger.error("当前路径不存在：{}", file.getPath());
				return;
			}
			File[] fis = file.listFiles();
			if (fis != null) {
				for (File fil : fis) {
					if (fil.getName().endsWith("zip")) {
						zf = new ZipFile(fil);
						for (Enumeration<?> entries = zf.getEntries(); entries.hasMoreElements();) {
							ZipEntry entry = ((ZipEntry) entries.nextElement());
							String zipEntryName = entry.getName();
							logger.info("解压文件名称：{}", zipEntryName);
							in = zf.getInputStream(entry);
							out = new FileOutputStream(descDir + zipEntryName);
							byte[] buf1 = new byte[1024];
							int len;
							while ((len = in.read(buf1)) > 0) {
								out.write(buf1, 0, len);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error("解压文件失败：" +  e.getMessage(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (zf != null) {
					zf.close();
				}
			} catch (IOException e) {
				logger.error("关闭文件流异常：" + e.getMessage(), e);
			}
		}
	}
	
	/**
	 * zip解压缩
	 * 
	 * @param zipfile
	 *            File 需要解压缩的文件
	 * @param descDir
	 *            String 解压后的目标目录
	 */
	public static void unZipFilesToNewPlace(String descDir,String ZipdescDir) {

		InputStream in = null;
		OutputStream out = null;
		ZipFile zf = null;
		try {
			File file = new File(descDir);
			if (!file.exists()) {
				logger.error("当前路径不存在：{}", file.getPath());
				return;
			}
			File[] fis = file.listFiles();
			if (fis != null) {
				for (File fil : fis) {
					if (fil.getName().endsWith("zip")) {
						zf = new ZipFile(fil);
						for (Enumeration<?> entries = zf.getEntries(); entries.hasMoreElements();) {
							ZipEntry entry = ((ZipEntry) entries.nextElement());
							String zipEntryName = entry.getName();
							logger.info("解压文件名称：{}", zipEntryName);
							in = zf.getInputStream(entry);
							File file2 = new File(ZipdescDir);
							if (!file2 .exists()  && !file2 .isDirectory()) {
								logger.info("创建新解压目录{}", ZipdescDir);
								file2 .mkdirs();  
							}
							out = new FileOutputStream(ZipdescDir + zipEntryName);
							byte[] buf1 = new byte[1024];
							int len;
							while ((len = in.read(buf1)) > 0) {
								out.write(buf1, 0, len);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error("解压文件失败：" + e.getMessage(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (zf != null) {
					zf.close();
				}
			} catch (IOException e) {
				logger.error("关闭文件流异常：" + e.getMessage(), e);
			}
		}
	}

	public static void main(String[] args) {

	}

}
