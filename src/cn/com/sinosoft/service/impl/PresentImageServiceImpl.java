package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.bean.PresentConfig;
import cn.com.sinosoft.bean.PresentDutyDownLoad;
import cn.com.sinosoft.bean.PresentImage;
import cn.com.sinosoft.bean.SystemConfig;
import cn.com.sinosoft.service.PresentImageService;
import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.ImageUtil;
import cn.com.sinosoft.util.PresentConfigUtil;
import cn.com.sinosoft.util.SystemConfigUtil;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Service实现类 - 商品图片
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT69F2EBC7A6A837BABDA5487C75D38611
 * ============================================================================
 */

@Service
public class PresentImageServiceImpl implements PresentImageService {
	private static final Logger logger = LoggerFactory.getLogger(PresentImageServiceImpl.class);

	public PresentImage buildPresentImage(File uploadPresentImageFile) {
		PresentConfig systemConfig = PresentConfigUtil.getPresentConfig();
		String sourcePresentImageFormatName = ImageUtil.getImageFormatName(uploadPresentImageFile);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = simpleDateFormat.format(new Date());
		String uuid = CommonUtil.getUUID();
		
		String sourcePresentImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + "." + sourcePresentImageFormatName;
		String bigPresentImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + PresentImage.BIG_PRESENT_IMAGE_FILE_NAME_SUFFIX + "." + PresentImage.PRESENT_IMAGE_FILE_EXTENSION;
		String smallPresentImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + PresentImage.SMALL_PRESENT_IMAGE_FILE_NAME_SUFFIX + "." + PresentImage.PRESENT_IMAGE_FILE_EXTENSION;
		String thumbnailPresentImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + PresentImage.THUMBNAIL_PRESENT_IMAGE_FILE_NAME_SUFFIX + "." + PresentImage.PRESENT_IMAGE_FILE_EXTENSION;

		File sourcePresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(sourcePresentImagePath));
		File bigPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(bigPresentImagePath));
		File smallPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(smallPresentImagePath));
		File thumbnailPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(thumbnailPresentImagePath));
		File watermarkImageFile = new File(ServletActionContext.getServletContext().getRealPath(systemConfig.getWatermarkImagePath()));

		File sourcePresentImageParentFile = sourcePresentImageFile.getParentFile();
		File bigPresentImageParentFile = bigPresentImageFile.getParentFile();
		File smallPresentImageParentFile = smallPresentImageFile.getParentFile();
		File thumbnailPresentImageParentFile = thumbnailPresentImageFile.getParentFile();

		if (!sourcePresentImageParentFile.exists()) {
			sourcePresentImageParentFile.mkdirs();
		}
		if (!bigPresentImageParentFile.exists()) {
			bigPresentImageParentFile.mkdirs();
		}
		if (!smallPresentImageParentFile.exists()) {
			smallPresentImageParentFile.mkdirs();
		}
		if (!thumbnailPresentImageParentFile.exists()) {
			thumbnailPresentImageParentFile.mkdirs();
		}

		try {
			BufferedImage srcBufferedImage = ImageIO.read(uploadPresentImageFile);
			// 将上传图片复制到原图片目录
			FileUtils.copyFile(uploadPresentImageFile, sourcePresentImageFile);
			// 商品图片（大）缩放、水印处理
//			ImageUtil.zoomAndWatermark(srcBufferedImage, bigPresentImageFile, systemConfig.getBigPresentImageHeight(), systemConfig.getBigPresentImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());
			// 商品图片（小）缩放、水印处理
//			ImageUtil.zoomAndWatermark(srcBufferedImage, smallPresentImageFile, systemConfig.getSmallPresentImageHeight(), systemConfig.getSmallPresentImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());
			// 商品图片缩略图处理
			ImageUtil.zoom(srcBufferedImage, thumbnailPresentImageFile, systemConfig.getThumbnailPresentImageHeight(), systemConfig.getThumbnailPresentImageWidth());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		PresentImage presentImage = new PresentImage();
		presentImage.setId(uuid);
		presentImage.setSourcePresentImagePath(sourcePresentImagePath);
		presentImage.setBigPresentImagePath(bigPresentImagePath);
		presentImage.setSmallPresentImagePath(smallPresentImagePath);
		presentImage.setThumbnailPresentImagePath(thumbnailPresentImagePath);
		return presentImage;
	}
	@Override
//===============保险条款=======================
	public PresentDutyDownLoad buildDutyDownLoad(File file) {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		String sourcePresentImageFormatName = ImageUtil.getImageFormatName(file);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = simpleDateFormat.format(new Date());
		String uuid = CommonUtil.getUUID();
		
		String sourcePresentImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + "." + sourcePresentImageFormatName;
		String bigPresentImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + PresentImage.BIG_PRESENT_IMAGE_FILE_NAME_SUFFIX + "." + PresentImage.PRESENT_IMAGE_FILE_EXTENSION;
		String smallPresentImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + PresentImage.SMALL_PRESENT_IMAGE_FILE_NAME_SUFFIX + "." + PresentImage.PRESENT_IMAGE_FILE_EXTENSION;
		String thumbnailPresentImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + PresentImage.THUMBNAIL_PRESENT_IMAGE_FILE_NAME_SUFFIX + "." + PresentImage.PRESENT_IMAGE_FILE_EXTENSION;

		File sourcePresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(sourcePresentImagePath));
		File bigPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(bigPresentImagePath));
		File smallPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(smallPresentImagePath));
		File thumbnailPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(thumbnailPresentImagePath));
		File watermarkImageFile = new File(ServletActionContext.getServletContext().getRealPath(systemConfig.getWatermarkImagePath()));

		File sourcePresentImageParentFile = sourcePresentImageFile.getParentFile();
		File bigPresentImageParentFile = bigPresentImageFile.getParentFile();
		File smallPresentImageParentFile = smallPresentImageFile.getParentFile();
		File thumbnailPresentImageParentFile = thumbnailPresentImageFile.getParentFile();

		if (!sourcePresentImageParentFile.exists()) {
			sourcePresentImageParentFile.mkdirs();
		}
		if (!bigPresentImageParentFile.exists()) {
			bigPresentImageParentFile.mkdirs();
		}
		if (!smallPresentImageParentFile.exists()) {
			smallPresentImageParentFile.mkdirs();
		}
		if (!thumbnailPresentImageParentFile.exists()) {
			thumbnailPresentImageParentFile.mkdirs();
		}

		try {
			BufferedImage srcBufferedImage = ImageIO.read(file);
			// 将上传图片复制到原图片目录
			FileUtils.copyFile(file, sourcePresentImageFile);
			// 商品图片（大）缩放、水印处理
//			ImageUtil.zoomAndWatermark(srcBufferedImage, bigPresentImageFile, systemConfig.getBigPresentImageHeight(), systemConfig.getBigPresentImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());
			// 商品图片（小）缩放、水印处理
//			ImageUtil.zoomAndWatermark(srcBufferedImage, smallPresentImageFile, systemConfig.getSmallPresentImageHeight(), systemConfig.getSmallPresentImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());
			// 商品图片缩略图处理
//			ImageUtil.zoom(srcBufferedImage, thumbnailPresentImageFile, systemConfig.getThumbnailPresentImageHeight(), systemConfig.getThumbnailPresentImageWidth());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		PresentDutyDownLoad dutyDownLoad = new PresentDutyDownLoad();
		dutyDownLoad.setId(uuid);
		dutyDownLoad.setSourcePresentImagePath(sourcePresentImagePath);
		dutyDownLoad.setBigPresentImagePath(bigPresentImagePath);
		dutyDownLoad.setSmallPresentImagePath(smallPresentImagePath);
		dutyDownLoad.setThumbnailPresentImagePath(thumbnailPresentImagePath);
		return dutyDownLoad;
	}
	public void deleteFile(PresentDutyDownLoad dutyDownLoad) {
		File sourcePresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(dutyDownLoad.getSourcePresentImagePath()));
		if (sourcePresentImageFile.exists()) {
			sourcePresentImageFile.delete();
		}
		File bigPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(dutyDownLoad.getBigPresentImagePath()));
		if (bigPresentImageFile.exists()) {
			bigPresentImageFile.delete();
		}
		File smallPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(dutyDownLoad.getSmallPresentImagePath()));
		if (smallPresentImageFile.exists()) {
			smallPresentImageFile.delete();
		}
		File thumbnailPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(dutyDownLoad.getThumbnailPresentImagePath()));
		if (thumbnailPresentImageFile.exists()) {
			thumbnailPresentImageFile.delete();
		}
	}
//===============保险条款=======================
	public void deleteFile(PresentImage presentImage) {
		File sourcePresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(presentImage.getSourcePresentImagePath()));
		if (sourcePresentImageFile.exists()) {
			sourcePresentImageFile.delete();
		}
		File bigPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(presentImage.getBigPresentImagePath()));
		if (bigPresentImageFile.exists()) {
			bigPresentImageFile.delete();
		}
		File smallPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(presentImage.getSmallPresentImagePath()));
		if (smallPresentImageFile.exists()) {
			smallPresentImageFile.delete();
		}
		File thumbnailPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(presentImage.getThumbnailPresentImagePath()));
		if (thumbnailPresentImageFile.exists()) {
			thumbnailPresentImageFile.delete();
		}
	}



}