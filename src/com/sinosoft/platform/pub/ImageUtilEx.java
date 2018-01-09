package com.sinosoft.platform.pub;

import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.resource.ConfigImageLib;
import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.ImageJDKUtil;
import com.sinosoft.framework.utility.ImageUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.gif.BmpUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.schema.ZCImageSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 */
public class ImageUtilEx {
	private static final Logger logger = LoggerFactory.getLogger(ImageUtilEx.class);

	public static ArrayList afterUploadImage(ZCImageSchema image, String absolutePath) throws Throwable {
		return afterUploadImage(image, absolutePath, null);
	}

	public static ArrayList afterUploadImage(ZCImageSchema image, String absolutePath, Mapx fields) throws Throwable {
		long t = System.currentTimeMillis();
		ArrayList imageList = new ArrayList();
		String imageFile = absolutePath + image.getSrcFileName();
		Dimension dim = null;
		try {
			dim = ImageUtil.getDimension(imageFile); 
		} catch (Throwable ex) {
			throw ex;// 必须捕获再抛出，否则前台不能接收到提示信息
		}
		image.setWidth((int) dim.getWidth());
		image.setHeight((int) dim.getHeight());

		// BMP需要改变后缀为jpg
		String destFile = image.getFileName();
		destFile = destFile.substring(0, destFile.lastIndexOf(".")) + ".jpg";
		image.setFileName(destFile);

		Mapx configFields = new Mapx();
		configFields.putAll(ConfigImageLib.getImageLibConfig(image.getSiteID()));
		if (fields != null) {
			configFields.putAll(fields);
		}

		// 缩略图
		int count = Integer.parseInt(configFields.get("Count").toString());
        String pathAll = absolutePath+image.getSrcFileName()+",";
		for (int i = 1; i <= count; i++) {
			if (configFields == null || "1".equals(configFields.get("HasAbbrImage" + i))) {
				String SizeType = (String) configFields.get("SizeType" + i);
				int Width = Integer.parseInt((String) configFields.get("Width" + i));
				int Height = Integer.parseInt((String) configFields.get("Height" + i));
				if ("1".equals(SizeType)) {
					Height = 0;
				} else if ("2".equals(SizeType)) {
					Width = 0;
				}
				String abbrImage = absolutePath + i + "_" + image.getFileName();
				// 固定大小
				if ("3".equals(SizeType)) {
					ImageUtil.scaleFixedImageFile(imageFile, abbrImage, Width, Height);
				} else {
					ImageUtil.scaleRateImageFile(imageFile, abbrImage, Width, Height);
				}
				pathAll += abbrImage+",";
				image.setCount(count);

				if (i == 1) {
					// 利用第一个缩略图来自动生成系统缩略图
					String thumbFileName = absolutePath + "s_" + image.getFileName();
					ImageUtil.scaleRateImageFile(absolutePath + "1_" + image.getFileName(), thumbFileName, 120, 120);
					pathAll += thumbFileName+",";
					imageList.add(thumbFileName);
				}

				if ("1".equals(configFields.get("HasWaterMark" + i))) {
					if ("Image".equals(configFields.get("WaterMarkType" + i))) {// 加水印修改2009.03.27
						String url = Config.getContextRealPath() + Config.getValue("UploadDir")
								+ "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/"
								+ configFields.get("Image" + i);
						ImageUtil.pressImage(abbrImage,url,Integer.parseInt(configFields.get("Position" + i)
								.toString()));
						pathAll += url+",";
					} else {
						ImageUtil.pressText(abbrImage, (String) configFields.get("Text" + i), Integer
								.parseInt(configFields.get("FontColor" + i).toString()), Integer.parseInt(configFields
								.get("FontSize" + i).toString()), Integer.parseInt(configFields.get("Position" + i)
								.toString()));
					}
				}

				imageList.add(abbrImage);
			}
		}

		// 如果一个缩略图都没有生成，则拷贝一份原图作为缩略图
		if (image.getCount() == 0) {
			if (image.getFileName().toLowerCase().endsWith(".bmp")) {
				BufferedImage img = BmpUtil.read(imageFile);
				ImageJDKUtil.writeImageFile(absolutePath + "1_" + image.getFileName(), img);
			} else {
				FileUtil.copy(imageFile, absolutePath + "1_" + image.getFileName());
			}
			pathAll += absolutePath + "1_" + image.getFileName()+",";
			image.setCount(1);
			imageList.add(absolutePath + "1_" + image.getFileName());

			// 利用第一个缩略图来自动生成系统缩略图，
			String thumbFileName = absolutePath + "s_" + image.getFileName();
			ImageUtil.scaleRateImageFile(absolutePath + "1_" + image.getFileName(), thumbFileName, 120, 120);
			pathAll += thumbFileName+",";
			imageList.add(thumbFileName);
		}

		// 给原图加水印
		if (configFields == null || "1".equals(configFields.get("HasWaterMark"))) {
			if ("Image".equals(configFields.get("WaterMarkType"))) {// 加水印修改2009.03.27
				String url = Config.getContextRealPath()
						+ Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/"
						+ configFields.get("Image");
				ImageUtil.pressImage(absolutePath + image.getSrcFileName(),url , Integer.parseInt(configFields.get("Position").toString()));
				pathAll += url+",";
			} else {
				ImageUtil.pressText(absolutePath + image.getSrcFileName(), (String) configFields.get("Text"), Integer
						.parseInt(configFields.get("FontColor").toString()), Integer.parseInt(configFields.get(
						"FontSize").toString()), Integer.parseInt(configFields.get("Position").toString()));
			}
		}
		OSSUploadFile.uploadFile(pathAll);
		// if (bakFlag) {
		// new File(imageFile).delete();
		// }
		logger.info("上传图片处理花费：{}毫秒", (System.currentTimeMillis() - t));
		return imageList;
	}
}
