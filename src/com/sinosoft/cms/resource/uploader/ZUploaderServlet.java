/*
 * FCKeditor - The text editor for internet
 * Copyright (C) 2003-2005 Frederico Caldeira Knabben
 * 
 * Licensed under the terms of the GNU Lesser General Public License:
 * 		http://www.opensource.org/licenses/lgpl-license.php
 * 
 * For further information visit:
 * 		http://www.fckeditor.net/
 * 
 * File Name: SimpleUploaderServlet.java
 * 	Java File Uploader class.
 * 
 * Version:  2.3
 * Modified: 2005-08-11 16:29:00
 * 
 * File Authors:
 * 		Simone Chiaretta (simo@users.sourceforge.net)
 */

package com.sinosoft.cms.resource.uploader;

import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.resource.Attachment;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.cms.template.HtmlNameParser;
import com.sinosoft.cms.template.HtmlNameRule;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.schema.ZCCatalogSchema;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class ZUploaderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ZUploaderServlet.class);

	public final static String IMAGE = "Image";

	public final static String VIDEO = "Video";

	public final static String AUDIO = "Audio";

	public final static String ATTACH = "Attach";
	
	public final static String Claims = "Claims";

	private static String baseDir;

	private static boolean debug = false;

	public void init() throws ServletException {
		debug = (new Boolean(getInitParameter("debug"))).booleanValue();
		baseDir = Config.getValue("UploadDir");
		if (baseDir == null) {
			baseDir = "";
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (StringUtil.isEmpty(baseDir)) {
			baseDir = Config.getValue("UploadDir");
		}
		logger.info("--- BEGIN DOPOST ---");
		response.setContentType("text/html; charset=" + Constant.GlobalCharset);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		FileItemFactory fileFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fileFactory);
		upload.setHeaderEncoding("UTF-8");
		upload.setSizeMax(1024 * 1024 * 200 * 10);
		try {
			List items = upload.parseRequest(request);
			Mapx fields = new Mapx();
			Mapx files = new Mapx();
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					fields.put(item.getFieldName(), item.getString("UTF-8"));
				} else {
					String OldFileName = item.getName();
					long size = item.getSize();
					if ((OldFileName == null || OldFileName.equals("")) && size == 0) {
						continue;
					} else {
						logger.info("-----UploadFileName:-----{}", OldFileName);
						files.put(item.getFieldName(), item);
					}
				}
			}

			String CatalogID = fields.getString("CatalogID");
			String AbsolutePath = fields.getString("AbsolutePath");
			String FileType = fields.getString("FileType");
			// 理赔资料上传静态资源服务器
			if (Claims.equals(FileType)) {
				String repeat = (String) fields.get("Repeat");
				String PathData = "upload/" + FileType + "/" + DateUtil.getCurrentDate("yyyy") + "/" + DateUtil.getCurrentDate("MM") + "/";
				AbsolutePath = (getServletContext().getRealPath(baseDir + "/kxb/" + PathData) + "/").replaceAll("//", "/");
				// 如果没有AbsolutePath的目录，自动创建
				if (!"".equals(AbsolutePath) && AbsolutePath != null) {
					File dir = new File(AbsolutePath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
				}
				fields.put("AbsolutePath", AbsolutePath);
				fields.put("PathData", PathData);
				if ("1".equals(repeat)) {
					UploadClaims.repeatUpload(files, fields);
				} else {
					UploadClaims.upload(files, fields);
				}
				
			} else if ("CustomClaimsData".equals(FileType)) {
				String repeat = (String) fields.get("Repeat");
				String PathData = Config.getValue("newPolicyPath")+"/EPolicy/CustomClaimsData/" + DateUtil.getCurrentDate("yyyy") + "/" + DateUtil.getCurrentDate("MM") + "/" + DateUtil.getCurrentDate("dd") + "/";
				// 如果没有目录，自动创建
				if (!"".equals(PathData) && PathData != null) {
					File dir = new File(PathData);
					if (!dir.exists()) {
						dir.mkdirs();
					}
				}
				fields.put("PathData", PathData);
				if ("1".equals(repeat)) {
					UploadCustomClaimsData.repeatUpload(files, fields);
				} else {
					UploadCustomClaimsData.upload(files, fields);
				}
				
			} else if ("UnderwritingOffline".equals(FileType)) {
				String PathData = Config.getValue("newPolicyPath")+"/EPolicy/UnderwritingOffline/" + DateUtil.getCurrentDate("yyyy") + "/" + DateUtil.getCurrentDate("MM") + "/" + DateUtil.getCurrentDate("dd") + "/";
				// 如果没有目录，自动创建
				if (!"".equals(PathData) && PathData != null) {
					File dir = new File(PathData);
					if (!dir.exists()) {
						dir.mkdirs();
					}
				}
				fields.put("PathData", PathData);
				UploadUnderwritingOfflineImg.upload(files, fields);
				
			} else {
			
				ZCCatalogSchema catalog = new ZCCatalogSchema();
				String SiteAlias = "";
				String PathData = "";
				long SiteID = 0L;
	
				if (CatalogID == null) {
					if (IMAGE.equals(FileType)) {
						CatalogID = new QueryBuilder("select ID from ZCCatalog  where type='" + Catalog.IMAGELIB + "' and Name ='默认图片'  and siteid=?", Application.getCurrentSiteID()).executeString();
					} else if (VIDEO.equals(FileType)) {
						CatalogID = new QueryBuilder("select ID from ZCCatalog  where type='" + Catalog.VIDEOLIB + "' and Name ='默认视频' and siteid=?", Application.getCurrentSiteID()).executeString();
					} else if (ATTACH.equals(FileType)) {
						CatalogID = new QueryBuilder("select ID from ZCCatalog  where type='" + Catalog.ATTACHMENTLIB + "' and Name ='默认附件' and siteid=?", Application.getCurrentSiteID()).executeString();
					} else if (AUDIO.equals(FileType)) {
						CatalogID = new QueryBuilder("select ID from ZCCatalog  where type='" + Catalog.AUDIOLIB + "' and Name ='默认音频' and siteid=?", Application.getCurrentSiteID()).executeString();
					}
				}
				catalog.setID(CatalogID);
				if (!catalog.fill()) {
					logger.error("没有找到上传库");
				}
				SiteID = catalog.getSiteID();
				SiteAlias = SiteUtil.getAlias(SiteID);
				HtmlNameParser nameParser = new HtmlNameParser(null, catalog.toDataRow(), null, catalog.getListNameRule());
				HtmlNameRule h = nameParser.getNameRule();
				PathData = "upload/" + FileType + "/" + h.getDirPath() + "/";
				AbsolutePath = (getServletContext().getRealPath(baseDir + "/" + SiteAlias + "/" + PathData) + "/").replaceAll("//", "/");
				logger.info("文件上传path：{}", PathData);
				logger.info("文件上传AbsolutePath：{}", AbsolutePath);
	
				// 如果没有AbsolutePath的目录，自动创建
				if (!"".equals(AbsolutePath) && AbsolutePath != null) {
					File dir = new File(AbsolutePath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
				}
				fields.put("CatalogID", CatalogID);
				fields.put("SiteID", SiteID);
				fields.put("SiteAlias", SiteAlias);
				fields.put("PathData", PathData);
				fields.put("AbsolutePath", AbsolutePath);
				fields.put("tempPath", (getServletContext().getRealPath(baseDir + "/" + SiteAlias) + "/Temp/").replaceAll("//", "/"));
				String repeat = (String) fields.get("Repeat");
				if (IMAGE.equals(FileType)) {
	
				} else if (VIDEO.equals(FileType)) {
					UploadVideo.upload(files, fields);
				} else if (ATTACH.equals(FileType)) {
					if ("1".equals(repeat)) {
						UploadAttachment.repeatUpload(files, fields);
					} else {
						UploadAttachment.upload(files, fields);
					}
					//如果附件是理赔申请书类型，那么打包文件 add by guozhichao 2016-11-16
					Attachment at = new Attachment();
					at.packageClaimfiles(Long.valueOf(CatalogID));
					 
				} else if (AUDIO.equals(FileType)) {
					if ("1".equals(repeat)) {
						UploadAudio.repeatUpload(files, fields);
					} else {
						UploadAudio.upload(files, fields);
					}
				}
			}
		} catch (Exception ex) {
			if (debug) {
				logger.error(ex.getMessage(), ex);
				
			}
		}
		out.flush();
		out.close();

		if (debug) {
			logger.info("--- END DOPOST ---");
		}
	}

	public static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

}