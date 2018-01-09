package cn.com.sinosoft.action.shop;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * congzn.
 * 
 * @author Administrator
 * 
 */
public class ImgTool {
	private static final Logger logger = LoggerFactory.getLogger(ImgTool.class);

	/**
	 * 图片压缩.
	 * 
	 * @param oldPath
	 * @param FileName
	 * @param width
	 * @param height
	 * @param percentage (true)自动调整比例.
	 * @param fileDir
	 * @param newImg
	 * @return
	 */
	public static String reduce(String oldPath, String FileName, int width,
			int height, boolean percentage, String fileDir, String newImg) {
		try {
			String serverPath = findServerPath();
			String mkpath = serverPath + fileDir + File.separator + newImg;
			File img = new File(mkpath);
			if (!img.exists()) {
				img.mkdirs();
			}
			mkpath = mkpath + File.separator + FileName;
			File srcfile = new File(oldPath);
			BufferedImage src = ImageIO.read(srcfile);
			if (percentage) {
				double rate1 = ((double) src.getWidth(null)) / (double) width
						+ 0.1;
				double rate2 = ((double) src.getHeight(null)) / (double) height
						+ 0.1;
				double rate = rate1 > rate2 ? rate1 : rate2;
				width = (int) (((double) src.getWidth(null)) / rate);
				height = (int) (((double) src.getHeight(null)) / rate);
			}
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(
					src.getScaledInstance(width, height,
							Image.SCALE_AREA_AVERAGING), 0, 0, null);
			FileOutputStream out = new FileOutputStream(mkpath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			image.flush();
			out.flush();
			out.close();
			src.flush();

			return mkpath;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return "";
	}
	
	public static void main(String[] args) {
		 try {  
			 	
	            File f = new File("D:\new.jpg");  
	            f.canRead();  
	            BufferedImage src = ImageIO.read(f);  
	            ImageIO.write(src, "png", new File("D:\new1.jpg"));  
	        } catch (Exception e) {
			 logger.error(e.getMessage(), e);
	        }  
	}

	/**
	 * 保存文件.
	 * 
	 * @param uploads
	 * @param uploadsFileName
	 * @param fileDir
	 * @param oldImg
	 * @return
	 * @throws IOException
	 */
	public static List<String> saveImgFile(List<File> uploads,
			List<String> uploadsFileName, String fileDir, String oldImg)
			throws IOException {
		List dbSaveSrc = new ArrayList();
		// 指定文件目标路径
		String serverPath = findServerPath();

		String mkpath = serverPath + fileDir + File.separator + oldImg;
		File img = new File(mkpath);
		if (!img.exists()) {
			img.mkdirs();
		}

		for (int i = 0; i < uploads.size(); i++) {
			String copyImgPath = mkpath
					+ File.separator
					+ uploads.get(i).toString().substring(
							uploads.get(i).toString().lastIndexOf(
									File.separator) + 1,
							uploads.get(i).toString().lastIndexOf("."));

			String hz = uploadsFileName.get(i).substring(
					uploadsFileName.get(i).lastIndexOf("."),
					uploadsFileName.get(i).length());

			copyImgPath += hz;

			File out = new File(copyImgPath);

			BufferedInputStream inBuff = null;
			BufferedOutputStream outBuff = null;
			try {
				inBuff = new BufferedInputStream(new FileInputStream(uploads
						.get(i)));

				outBuff = new BufferedOutputStream(new FileOutputStream(out));

				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = inBuff.read(b)) != -1) {
					outBuff.write(b, 0, len);
				}
				outBuff.flush();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				if (inBuff != null)
					inBuff.close();
				if (outBuff != null)
					outBuff.close();
			}
			dbSaveSrc.add(i, copyImgPath);
		}
		return dbSaveSrc;
	}

	/**
	 * 删除文件.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String delFile(String fileName) {
		String tempPath = findServerPath();
		File file = new File(tempPath + fileName);
		if (file.exists()) {
			file.delete();
		}
		return "true";
	}

	/**
	 * 服务器路径.
	 * 
	 * @return
	 */
	public static String findServerPath() {
		String path = null;
		try {
			String folderPath = ImgTool.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			if (folderPath.indexOf("WEB-INF") > 0) {
				path = folderPath.substring(0, folderPath
						.indexOf("WEB-INF/classes"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return path;
	}
	
	/**
	 * 判断上传文件是否为图片.
	 * 返回null 表示没找到.
	 * @param filePath
	 * @return
	 */
	public static String isImg(File filePath) {
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(filePath);
			Iterator iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				return null;
			}
			ImageReader reader = (ImageReader) iter.next();
			iis.close();
			return reader.getFormatName();
		} catch (IOException e) {
			
		}
		return null;
	}

}
