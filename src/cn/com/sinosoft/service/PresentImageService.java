package cn.com.sinosoft.service;

import java.io.File;

import cn.com.sinosoft.bean.PresentDutyDownLoad;
import cn.com.sinosoft.bean.PresentImage;



/**
 * Service接口 - 礼品图片
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT89343F3383F923F8C0F63F568A7EC7A7
 * ============================================================================
 */

public interface PresentImageService {
	
	/**
	 * 生成礼品图片（包括原图、大图、小图、缩略图）
	 * 
	 * @param uploadPresentImageFile
	 *            上传图片文件
	 * 
	 */
	public PresentImage buildPresentImage(File uploadPresentImageFile);
	
	/**
	 * 根据PresentImage对象删除图片文件
	 * 
	 * @param presentImage
	 *            PresentImage
	 * 
	 */
	public void deleteFile(PresentImage presentImage);
	public void deleteFile(PresentDutyDownLoad dutyDownLoad);
//==============保险条款=================
	public PresentDutyDownLoad buildDutyDownLoad(File file);
//==============保险条款=================

}