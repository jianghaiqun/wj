package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.bean.DutyDownLoad;
import cn.com.sinosoft.bean.ProductImage;
import cn.com.sinosoft.bean.SystemConfig;
import cn.com.sinosoft.service.ProductImageService;
import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.ImageUtil;
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
public class ProductImageServiceImpl implements ProductImageService {
	private static final Logger logger = LoggerFactory.getLogger(ProductImageServiceImpl.class);

	public ProductImage buildProductImage(File uploadProductImageFile) {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		String sourceProductImageFormatName = ImageUtil.getImageFormatName(uploadProductImageFile);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = simpleDateFormat.format(new Date());
		String uuid = CommonUtil.getUUID();
		
		String sourceProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + "." + sourceProductImageFormatName;
		String bigProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + ProductImage.BIG_PRODUCT_IMAGE_FILE_NAME_SUFFIX + "." + ProductImage.PRODUCT_IMAGE_FILE_EXTENSION;
		String smallProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + ProductImage.SMALL_PRODUCT_IMAGE_FILE_NAME_SUFFIX + "." + ProductImage.PRODUCT_IMAGE_FILE_EXTENSION;
		String thumbnailProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + ProductImage.THUMBNAIL_PRODUCT_IMAGE_FILE_NAME_SUFFIX + "." + ProductImage.PRODUCT_IMAGE_FILE_EXTENSION;

		File sourceProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(sourceProductImagePath));
		File bigProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(bigProductImagePath));
		File smallProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(smallProductImagePath));
		File thumbnailProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(thumbnailProductImagePath));
		File watermarkImageFile = new File(ServletActionContext.getServletContext().getRealPath(systemConfig.getWatermarkImagePath()));

		File sourceProductImageParentFile = sourceProductImageFile.getParentFile();
		File bigProductImageParentFile = bigProductImageFile.getParentFile();
		File smallProductImageParentFile = smallProductImageFile.getParentFile();
		File thumbnailProductImageParentFile = thumbnailProductImageFile.getParentFile();

		if (!sourceProductImageParentFile.exists()) {
			sourceProductImageParentFile.mkdirs();
		}
		if (!bigProductImageParentFile.exists()) {
			bigProductImageParentFile.mkdirs();
		}
		if (!smallProductImageParentFile.exists()) {
			smallProductImageParentFile.mkdirs();
		}
		if (!thumbnailProductImageParentFile.exists()) {
			thumbnailProductImageParentFile.mkdirs();
		}

		try {
			BufferedImage srcBufferedImage = ImageIO.read(uploadProductImageFile);
			// 将上传图片复制到原图片目录
			FileUtils.copyFile(uploadProductImageFile, sourceProductImageFile);
			// 商品图片（大）缩放、水印处理
			ImageUtil.zoomAndWatermark(srcBufferedImage, bigProductImageFile, systemConfig.getBigProductImageHeight(), systemConfig.getBigProductImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());
			// 商品图片（小）缩放、水印处理
			ImageUtil.zoomAndWatermark(srcBufferedImage, smallProductImageFile, systemConfig.getSmallProductImageHeight(), systemConfig.getSmallProductImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());
			// 商品图片缩略图处理
			ImageUtil.zoom(srcBufferedImage, thumbnailProductImageFile, systemConfig.getThumbnailProductImageHeight(), systemConfig.getThumbnailProductImageWidth());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		ProductImage productImage = new ProductImage();
		productImage.setId(uuid);
		productImage.setSourceProductImagePath(sourceProductImagePath);
		productImage.setBigProductImagePath(bigProductImagePath);
		productImage.setSmallProductImagePath(smallProductImagePath);
		productImage.setThumbnailProductImagePath(thumbnailProductImagePath);
		return productImage;
	}
	@Override
//===============保险条款=======================
	public DutyDownLoad buildDutyDownLoad(File file) {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		String sourceProductImageFormatName = ImageUtil.getImageFormatName(file);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = simpleDateFormat.format(new Date());
		String uuid = CommonUtil.getUUID();
		
		String sourceProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + "." + sourceProductImageFormatName;
		String bigProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + ProductImage.BIG_PRODUCT_IMAGE_FILE_NAME_SUFFIX + "." + ProductImage.PRODUCT_IMAGE_FILE_EXTENSION;
		String smallProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + ProductImage.SMALL_PRODUCT_IMAGE_FILE_NAME_SUFFIX + "." + ProductImage.PRODUCT_IMAGE_FILE_EXTENSION;
		String thumbnailProductImagePath = SystemConfig.UPLOAD_IMAGE_DIR + dateString + "/" + uuid + ProductImage.THUMBNAIL_PRODUCT_IMAGE_FILE_NAME_SUFFIX + "." + ProductImage.PRODUCT_IMAGE_FILE_EXTENSION;

		File sourceProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(sourceProductImagePath));
		File bigProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(bigProductImagePath));
		File smallProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(smallProductImagePath));
		File thumbnailProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(thumbnailProductImagePath));
		File watermarkImageFile = new File(ServletActionContext.getServletContext().getRealPath(systemConfig.getWatermarkImagePath()));

		File sourceProductImageParentFile = sourceProductImageFile.getParentFile();
		File bigProductImageParentFile = bigProductImageFile.getParentFile();
		File smallProductImageParentFile = smallProductImageFile.getParentFile();
		File thumbnailProductImageParentFile = thumbnailProductImageFile.getParentFile();

		if (!sourceProductImageParentFile.exists()) {
			sourceProductImageParentFile.mkdirs();
		}
		if (!bigProductImageParentFile.exists()) {
			bigProductImageParentFile.mkdirs();
		}
		if (!smallProductImageParentFile.exists()) {
			smallProductImageParentFile.mkdirs();
		}
		if (!thumbnailProductImageParentFile.exists()) {
			thumbnailProductImageParentFile.mkdirs();
		}

		try {
			BufferedImage srcBufferedImage = ImageIO.read(file);
			// 将上传图片复制到原图片目录
			FileUtils.copyFile(file, sourceProductImageFile);
			// 商品图片（大）缩放、水印处理
			ImageUtil.zoomAndWatermark(srcBufferedImage, bigProductImageFile, systemConfig.getBigProductImageHeight(), systemConfig.getBigProductImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());
			// 商品图片（小）缩放、水印处理
			ImageUtil.zoomAndWatermark(srcBufferedImage, smallProductImageFile, systemConfig.getSmallProductImageHeight(), systemConfig.getSmallProductImageWidth(), watermarkImageFile, systemConfig.getWatermarkPosition(), systemConfig.getWatermarkAlpha().intValue());
			// 商品图片缩略图处理
			ImageUtil.zoom(srcBufferedImage, thumbnailProductImageFile, systemConfig.getThumbnailProductImageHeight(), systemConfig.getThumbnailProductImageWidth());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		DutyDownLoad dutyDownLoad = new DutyDownLoad();
		dutyDownLoad.setId(uuid);
		dutyDownLoad.setSourceProductImagePath(sourceProductImagePath);
		dutyDownLoad.setBigProductImagePath(bigProductImagePath);
		dutyDownLoad.setSmallProductImagePath(smallProductImagePath);
		dutyDownLoad.setThumbnailProductImagePath(thumbnailProductImagePath);
		return dutyDownLoad;
	}
	public void deleteFile(DutyDownLoad dutyDownLoad) {
		File sourceProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(dutyDownLoad.getSourceProductImagePath()));
		if (sourceProductImageFile.exists()) {
			sourceProductImageFile.delete();
		}
		File bigProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(dutyDownLoad.getBigProductImagePath()));
		if (bigProductImageFile.exists()) {
			bigProductImageFile.delete();
		}
		File smallProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(dutyDownLoad.getSmallProductImagePath()));
		if (smallProductImageFile.exists()) {
			smallProductImageFile.delete();
		}
		File thumbnailProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(dutyDownLoad.getThumbnailProductImagePath()));
		if (thumbnailProductImageFile.exists()) {
			thumbnailProductImageFile.delete();
		}
	}
//===============保险条款=======================
	public void deleteFile(ProductImage productImage) {
		File sourceProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getSourceProductImagePath()));
		if (sourceProductImageFile.exists()) {
			sourceProductImageFile.delete();
		}
		File bigProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getBigProductImagePath()));
		if (bigProductImageFile.exists()) {
			bigProductImageFile.delete();
		}
		File smallProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getSmallProductImagePath()));
		if (smallProductImageFile.exists()) {
			smallProductImageFile.delete();
		}
		File thumbnailProductImageFile = new File(ServletActionContext.getServletContext().getRealPath(productImage.getThumbnailProductImagePath()));
		if (thumbnailProductImageFile.exists()) {
			thumbnailProductImageFile.delete();
		}
	}



}