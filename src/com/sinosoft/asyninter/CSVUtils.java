package com.sinosoft.asyninter;

/**
 * Project Name:wj File Name:CSVUtils.java Package Name:com.sinosoft.asyninter
 * Date:2016年9月19日下午6:15:36 Copyright (c) 2016, www.kaixinbao.com All Rights
 * Reserved.
 *
 */

import com.Ostermiller.util.CSVParse;
import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.CSVPrint;
import com.Ostermiller.util.ExcelCSVPrinter;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * ClassName: CSVUtils <br/>
 * date: 2016年9月19日 下午6:15:36 <br/>
 *
 * @author dongsheng
 * @version
 */
public class CSVUtils {

	private static final Logger logger = LoggerFactory.getLogger(CSVUtils.class);

	public static List<List<String>> readCSV(String pathString) throws IOException {

		return readCSV(pathString, "utf-8");
	}

	/**
	 * readCSV:读取一个或者多个csv文件到一个list中.<br/>
	 *
	 * @author dongsheng
	 * @param pathString
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static List<List<String>> readCSV(String pathString, String encoding) throws IOException {

		List<List<String>> list = new ArrayList<List<String>>();
		if (StringUtils.isEmpty(pathString)) {
			logger.warn("入参路径{}为空", pathString);
			return null;
		}
		File path = new File(pathString);
		if (!path.exists()) {
			logger.warn("{}没有这个路径", pathString);
			return null;
		}
		List<File> fileList = new ArrayList<File>();
		if (path.isDirectory()) {
			// 如果传入的是一个文件夹路径,则遍历该文件夹.
			File[] files = path.listFiles();
			if (files == null || files.length == 0) {
				logger.warn("{}路径下面没有内容", pathString);
				return null;
			} else {
				fileList = Arrays.asList(files);
			}
		} else {
			// 如果传入的是一个文件.
			if (path.isFile()) {
				fileList.add(path);
			}
		}
		for (File file : fileList) {
			String name = file.getName();
			if (!file.isFile() || !name.endsWith(".csv")) {
				logger.warn("{}不是一个csv文件", file.getName());
				continue;
			}
			InputStream in = new FileInputStream(file);
			list.addAll(readCSV(in, encoding));
			in.close();

		}
		return list;

	}

	public static List<List<String>> readCSV(InputStream in, String encoding) throws IOException {

		List<List<String>> list = new ArrayList<List<String>>();
		if (in == null) {
			return list;
		}
		InputStreamReader inReader = null;
		if (StringUtils.isEmpty(encoding)) {
			inReader = new InputStreamReader(in);
		} else {
			inReader = new InputStreamReader(in, encoding);
		}
		CSVParse parser = new CSVParser(inReader);
		String[][] values = parser.getAllValues();
		if (values != null) {
			for (String[] strings : values) {
				List<String> valueList = new ArrayList<String>();
				valueList = Arrays.asList(strings);
				list.add(valueList);
			}
		}
		inReader.close();

		return list;

	}

	public static void writeCSV(List<List<String>> rows, String pathString) throws IOException {

		writeCSV(rows, pathString, "utf-8");
	}

	/**
	 * writeCSV:讲数据写到一个csv文件中. <br/>
	 *
	 * @author dongsheng
	 * @param rows
	 * @param pathString
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static void writeCSV(List<List<String>> rows, String pathString, String encoding) throws IOException {

		if (StringUtils.isEmpty(encoding)) {
			logger.warn("入参编码类型为空");
			return;
		}
		File path = new File(pathString);
		if (path.isDirectory()) {
			logger.warn("入参文件路径为空{}不合法", pathString);
			return;
		}
		File dir = path.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		path.createNewFile();
		OutputStream out = new FileOutputStream(path, false);
		OutputStreamWriter outWriter;
		if (StringUtils.isEmpty(encoding)) {
			outWriter = new OutputStreamWriter(out);
		} else {
			outWriter = new OutputStreamWriter(out, encoding);
		}

		CSVPrint printer = new ExcelCSVPrinter(outWriter);
		printer.setAlwaysQuote(true);
		String[][] rowArr = new String[][] {};
		if (rows != null && rows.size() > 0) {
			rowArr = new String[rows.size()][];
			for (int i = 0; i < rowArr.length; i++) {
				List<String> valueList = rows.get(i);
				String[] valueArr = new String[valueList.size()];
				valueArr = valueList.toArray(valueArr);
				rowArr[i] = valueArr;
			}
		}
		printer.writeln(rowArr);
		out.close();
		outWriter.close();
	}

	/**
	 * readCSVFromZip:(从zip文件中读取csv.不解压). <br/>
	 *
	 * @author dongsheng
	 * @param pathString
	 *            ,目录或者文件路径.
	 * @return
	 * @throws IOException
	 */
	public static List<List<String>> readCSVFromZip(String pathString) throws IOException {

		if (StringUtils.isEmpty(pathString)) {
			logger.warn("入参路径{}为空", pathString);
			return null;
		}
		File path = new File(pathString);
		if (!path.exists()) {
			logger.warn("{}没有这个路径", pathString);
			return null;
		}
		List<File> fileList = new ArrayList<File>();

		if (path.isDirectory()) {
			// 如果传入的是一个文件夹路径,则遍历该文件夹.
			File[] files = path.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {

					return name.endsWith(".zip");
				}
			});
			if (files == null || files.length == 0) {
				logger.warn("{}路径下面没有内容", pathString);
				return null;
			} else {
				fileList = Arrays.asList(files);
			}
		} else {
			// 如果传入的是一个文件.
			if (path.isFile() && path.getName().endsWith(".zip")) {
				fileList.add(path);
			}
		}
		List<List<String>> list = new ArrayList<List<String>>();
		for (File file : fileList) {
			list.addAll(readCSVFromZip(file));
		}

		return list;

	}

	@SuppressWarnings("unchecked")
	public static List<List<String>> readCSVFromZip(File file) throws IOException {

		List<List<String>> list = new ArrayList<List<String>>();
		if (file == null || !file.getName().endsWith(".zip")) {
			return list;
		}
		ZipFile zipFile = new ZipFile(file);
		Enumeration<ZipEntry> entries = zipFile.getEntries();
		ZipEntry zipEntry = null;
		while (entries.hasMoreElements()) {
			zipEntry = (ZipEntry) entries.nextElement();
			if (!zipEntry.getName().endsWith(".csv")) {
				continue;
			}
			InputStream entryInputStream = zipFile.getInputStream(zipEntry);
			list.addAll(readCSV(entryInputStream, "UTF-8"));
			entryInputStream.close();
		}
		zipFile.close();
		return list;
	}

	public static void main(String[] args) throws Exception {

		String path = "D:\\alidata\\sftpFinance\\sftp_asyn_test8\\download\\20170106\\policyReq";
		List<List<String>> resultList = CSVUtils.readCSVFromZip(path);
//		for (List<String> list : resultList) {
//			System.out.println(list);
//		}

		// List<List<String>> resultList = new ArrayList<List<String>>();
		// String path2 = "D:/test.csv";
		// CSVUtils.writeCSV(resultList, path2, "utf-8");
	}

}
