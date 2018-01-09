package com.sinosoft.framework.utility;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * ZIP压缩工具类
 * 
 * @Author 王育春
 * @Date 2006-11-29
 * @Mail wyuch@midding.com
 */
public class ZipUtil {
	private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);

	/**
	 * 以ZIP方式压缩二进制数组
	 */
	public static byte[] zip(byte[] bs) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Deflater def = new Deflater();
		DeflaterOutputStream dos = new DeflaterOutputStream(bos, def);
		try {
			dos.write(bs);
			dos.finish();
			dos.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		byte[] r = bos.toByteArray();
		return r;
	}

	/**
	 * 以GZIP方式压缩二进制数组
	 */
	public static byte[] gzip(byte[] bs) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			GZIPOutputStream dos = new GZIPOutputStream(bos);
			dos.write(bs);
			dos.finish();
			dos.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		byte[] r = bos.toByteArray();
		return r;
	}

	/**
	 * 以ZIP方式压缩文件
	 */
	public static void zip(String srcFile, String destFile) throws Exception {
		OutputStream os = new FileOutputStream(destFile);
		zip(new File(srcFile), os);
		os.flush();
		os.close();
	}

	/**
	 * 以ZIP方式压缩文件并输出到指定流
	 */
	public static void zip(File srcFile, OutputStream destStream) throws Exception {
		List fileList = getSubFiles(srcFile);
		ZipOutputStream zos = new ZipOutputStream(destStream);
		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		int readLen = 0;
		for (int i = 0; i < fileList.size(); i++) {
			File f = (File) fileList.get(i);
			// 创建一个ZipEntry，并设置Name和其它的一些属性
			ze = new ZipEntry(getAbsFileName(srcFile, f));
			ze.setSize(f.length());
			ze.setTime(f.lastModified());
			logger.info("正在压缩: {}", f.getPath());

			// 将ZipEntry加到zos中，再写入实际的文件内容
			zos.putNextEntry(ze);
			if (f.isFile()) {
				InputStream is = new BufferedInputStream(new FileInputStream(f));
				while ((readLen = is.read(buf, 0, 1024)) != -1) {
					zos.write(buf, 0, readLen);
				}
				is.close();
				zos.flush();
			}
		}
		zos.close();
		logger.info("压缩完毕！");
	}

	/**
	 * 将一批文件压缩到一个ZIP文件里
	 */
	public static void zipBatch(String[] srcFiles, String destFile) throws Exception {
		OutputStream os = new FileOutputStream(destFile);
		zipBatch(srcFiles, os);
		os.flush();
		os.close();
	}

	/**
	 * 将一批文件压缩到一个流里
	 */
	public static void zipBatch(String[] srcFiles, OutputStream destStream) throws Exception {
		File[] files = new File[srcFiles.length];
		for (int i = 0; i < srcFiles.length; i++) {
			files[i] = new File(srcFiles[i]);
		}
		zipBatch(files, destStream);
	}

	public static void zipBatch(File[] srcFiles, OutputStream destStream) throws Exception {
		ZipOutputStream zos = new ZipOutputStream(destStream);
		for (int k = 0; k < srcFiles.length; k++) {
			List fileList = getSubFiles(srcFiles[k]);
			ZipEntry ze = null;
			byte[] buf = new byte[1024];
			int readLen = 0;
			for (int i = 0; i < fileList.size(); i++) {
				File f = (File) fileList.get(i);
				// 创建一个ZipEntry，并设置Name和其它的一些属性
				ze = new ZipEntry(getAbsFileName(srcFiles[k], f));
				ze.setSize(f.length());
				ze.setTime(f.lastModified());
				logger.info("正在压缩: {}", f.getPath());

				// 将ZipEntry加到zos中，再写入实际的文件内容
				zos.putNextEntry(ze);
				if (f.isFile()) {
					InputStream is = new BufferedInputStream(new FileInputStream(f));
					while ((readLen = is.read(buf, 0, 1024)) != -1) {
						zos.write(buf, 0, readLen);
					}
					is.close();
				}
			}
		}
		zos.close();
		logger.info("压缩完毕！");
	}

	/**
	 * 将InputStream以指定的文件名fileName压缩到一个OutputStream
	 */
	public static void zipStream(InputStream is, OutputStream os, String fileName) throws Exception {
		ZipOutputStream zos = new ZipOutputStream(os);
		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		int readLen = 0;
		ze = new ZipEntry(fileName);
		ze.setTime(System.currentTimeMillis());
		logger.info("正在压缩流:{}", fileName);

		// 将ZipEntry加到zos中，再写入实际的文件内容
		zos.putNextEntry(ze);
		long total = 0;
		while ((readLen = is.read(buf, 0, 1024)) != -1) {
			zos.write(buf, 0, readLen);
			total += readLen;
		}
		ze.setSize(total);
		zos.flush();
		zos.close();
		logger.info("压缩完毕！");
	}

	/**
	 * 将二进制数组解压缩
	 */
	public static byte[] unzip(byte[] bs) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ByteArrayInputStream bis = new ByteArrayInputStream(bs);
		bos = new ByteArrayOutputStream();
		Inflater inf = new Inflater();
		InflaterInputStream dis = new InflaterInputStream(bis, inf);
		byte[] buf = new byte[1024];
		int c;
		try {
			while ((c = dis.read(buf)) != -1) {
				bos.write(buf, 0, c);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		byte[] r = bos.toByteArray();
		return r;
	}

	/**
	 * GZIP解压缩
	 */
	public static byte[] ungzip(byte[] bs) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ByteArrayInputStream bis = new ByteArrayInputStream(bs);
		bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int c;
		try {
			GZIPInputStream gis = new GZIPInputStream(bis);
			while ((c = gis.read(buf)) != -1) {
				bos.write(buf, 0, c);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		byte[] r = bos.toByteArray();
		return r;
	}

	/**
	 * 文件解压缩
	 */
	public static boolean unzip(String srcFileName, String destPath) {
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(srcFileName);
			Enumeration e = zipFile.getEntries();
			ZipEntry zipEntry = null;
			new File(destPath).mkdirs();
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				logger.info("正在解压 {}", zipEntry.getName());
				if (zipEntry.isDirectory()) {
					new File(destPath + File.separator + zipEntry.getName()).mkdirs();
				} else {
					File f = new File(destPath + File.separator + zipEntry.getName());
					f.getParentFile().mkdirs();
					InputStream in = null;
					OutputStream out = null;
					try {
						in = zipFile.getInputStream(zipEntry);
						out = new BufferedOutputStream(new FileOutputStream(f));

						byte[] buf = new byte[1024];
						int c;
						while ((c = in.read(buf)) != -1) {
							out.write(buf, 0, c);
						}

					} catch (Exception ex) {
						logger.error(ex.getMessage(), ex);
						return false;

					} finally {
						if (out != null)
							out.close();
						if (in != null)
							in.close();
					}
				}
			}
			logger.info("解压完毕！");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return false;
		} finally {
			try {
				if (zipFile != null)
					zipFile.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return true;
	}

	/**
	 * 解压缩到指定目录 如果isPath为true:生成相对路径和目录 false:解压缩时不包含路径解压缩
	 */
	public static boolean unzip(String srcFileName, String destPath, boolean isPath) {
		if (isPath) {
			return unzip(srcFileName, destPath);
		}
		try {
			ZipFile zipFile = new ZipFile(srcFileName);
			java.util.Enumeration e = zipFile.getEntries();
			ZipEntry zipEntry = null;
			new File(destPath).mkdirs();
			while (e.hasMoreElements()) {
				zipEntry = (org.apache.tools.zip.ZipEntry) e.nextElement();
				logger.info("正在解压 {}", zipEntry.getName());
				if (!zipEntry.isDirectory()) {
					String fileName = zipEntry.getName();
					if (fileName.lastIndexOf("/") != -1) {
						fileName = fileName.substring(fileName.lastIndexOf("/"));
					}
					File f = new File(destPath + "/" + fileName);
					InputStream in = zipFile.getInputStream(zipEntry);
					OutputStream out = new BufferedOutputStream(new FileOutputStream(f));

					byte[] buf = new byte[1024];
					int c;
					while ((c = in.read(buf)) != -1) {
						out.write(buf, 0, c);
					}
					out.flush();
					out.close();
					in.close();
				}
			}
			zipFile.close();
			logger.info("解压完毕！");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return false;
		}

		return true;
	}

	/**
	 * 递归获取一个文件夹下的所有文件及子文件
	 */
	private static List getSubFiles(File baseDir) {
		List ret = new ArrayList();
		ret.add(baseDir);
		if (baseDir.isDirectory()) {
			File[] tmp = baseDir.listFiles();
			for (int i = 0; i < tmp.length; i++) {
				ret.addAll(getSubFiles(tmp[i]));
			}
		}
		return ret;
	}

	/**
	 * 获取一个文件相对于某个目录的相对路径
	 */
	private static String getAbsFileName(File baseDir, File realFileName) {
		File real = realFileName;
		File base = baseDir;
		String ret = real.getName();
		if (real.isDirectory()) {
			ret += "/";
		}
		while (true) {
			if (real == base) {
				break;
			}
			real = real.getParentFile();
			if (real == null) {
				break;
			}
			if (real.equals(base)) {
				ret = real.getName() + "/" + ret;
				break;
			} else {
				ret = real.getName() + "/" + ret;
			}
		}
		return ret;
	}

	public static void main(String[] args) {
		byte[] bs = FileUtil.readByte("H:/shop.html");
		try {
			for (int i = 0; i < 10000; i++) {
				bs = ZipUtil.zip(bs);
				bs = ZipUtil.unzip(bs);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
