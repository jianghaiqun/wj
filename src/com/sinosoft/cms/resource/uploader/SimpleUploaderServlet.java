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

import com.sinosoft.cms.datachannel.Deploy;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.cms.template.HtmlNameParser;
import com.sinosoft.cms.template.HtmlNameRule;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.ZipUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.ImageUtilEx;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCImageSchema;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 */

public class SimpleUploaderServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(SimpleUploaderServlet.class);
	private static final long serialVersionUID = 1L;

	public final static String IMAGE = "Image";

	private static String baseDir;

	private static boolean enabled = false;

	public void init() throws ServletException {
		baseDir = Config.getValue("UploadDir");
		if (baseDir == null) {
			baseDir = "";
		}
		enabled = (new Boolean(getInitParameter("enabled"))).booleanValue();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (StringUtil.isEmpty(baseDir)) {
			baseDir = Config.getValue("UploadDir");
		}
		response.setContentType("text/html; charset=" + Constant.GlobalCharset);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		String returnIDs = "";
		String returnPaths = "";
		String returnValue = "0";
		String errorMessage = "";
		String taskID = "";
		String filePath = "";
		String fromObjec = null;

		if (!enabled) {
			returnValue = "1";
			errorMessage = "This file uploader is disabled. Please check the WEB-INF/web.xml file";
		} else {
			FileItemFactory fileFactory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fileFactory);
			upload.setHeaderEncoding(Constant.GlobalCharset);
			upload.setSizeMax(1024 * 1024 * 200 * 10);
			try {
				List items = upload.parseRequest(request);
				Mapx fields = new Mapx();
				Mapx files = new Mapx();
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField()) {
						fields.put(item.getFieldName(), item.getString(Constant.GlobalCharset));
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

				String repeat = (String) fields.get("Repeat");

				fromObjec = (String) fields.get("FromObject");
				String flag = fields.getString("flag");
				String FileType = fields.getString("FileType");
				
				// 非保险产品图片上传
				if ("OtProductImage".equals(flag)) {
					String PathData = "upload/" + FileType + "/OtProduct/" + DateUtil.getCurrentDate("yyyy") + "/" + DateUtil.getCurrentDate("MM") + "/";
					String absolutePath = (getServletContext().getRealPath(baseDir + "/kxb/" + PathData) + "/").replaceAll("//", "/");
					// 如果没有AbsolutePath的目录，自动创建
					if (StringUtil.isNotEmpty(absolutePath)) {
						File dir = new File(absolutePath);
						if (!dir.exists()) {
							dir.mkdirs();
						}
					}
					fields.put("AbsolutePath", absolutePath);
					fields.put("PathData", PathData);
					UploadOtProductImg.upload(files, fields);
					
				} else {
					String[] arr = null;
					if ("1".equals(repeat)) {
						arr = this.repeatUpload(files, fields);// 重新上传
					} else {
						arr = this.upload(files, fields);
					}
					returnIDs = arr[0];
					returnValue = arr[1];
					errorMessage = arr[2];
					taskID = arr[3];
					if (arr.length > 4) {
						filePath = arr[4];
						returnPaths = arr[5];
					}
				}
				
			} catch (SizeLimitExceededException ex) {
				logger.error(ex.getMessage(), ex);
				returnValue = "-1";
				errorMessage = "文件过大：";
			} catch (Throwable ex) {
				logger.error(ex.getMessage(), ex);
				returnValue = "-1";
				errorMessage = "上传文件失败:" + ex.getMessage();
			}
		}
		if ("activex".equalsIgnoreCase(fromObjec)) {
			out.print(filePath);
			out.close();
		} else {
			out.println("taskid:" + taskID);
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.onUploadCompleted(" + returnValue + ",'" + returnIDs.toString() + "','" + errorMessage + "','" + returnPaths + "');");
			out.println("</script>");
			out.flush();
			out.close();
		}
	}

	private String[] repeatUpload(Mapx files, Mapx fields) throws Throwable {
		StringBuffer returnIDs = new StringBuffer();
		String returnValue = "0";
		String errorMessage = "";
		String taskID = "";
		if (IMAGE.equalsIgnoreCase((String) fields.get("FileType"))) {
			ZCImageSchema image = new ZCImageSchema();
			image.setID(Long.parseLong(fields.get("RepeatID").toString()));
			if (!image.fill()) {
				returnValue = "";
				errorMessage = "没有查找到原图片";
			}
			String SiteAlias = SiteUtil.getAlias(image.getSiteID());
			Iterator it = files.keySet().iterator();
			while (it.hasNext()) {
				FileItem uplFile = (FileItem) files.get(it.next());
				String AbsolutePath = (getServletContext().getRealPath(baseDir + "/" + SiteAlias + "/" + image.getPath()) + "/").replaceAll("//", "/");
				if(!new File(AbsolutePath).exists()){
					new File(AbsolutePath).mkdirs();
				}
				uplFile.write(new File(AbsolutePath + image.getSrcFileName()));
				ImageUtilEx.afterUploadImage(image, AbsolutePath, fields);
				//OSSUploadFile.uploadFile(AbsolutePath + image.getSrcFileName());
			}
			image.setModifyTime(new Date());
			image.setModifyUser(User.getUserName());
			image.update();
		}
		return new String[] { returnIDs.toString(), returnValue, errorMessage, taskID };
	}

	private String[] upload(Mapx files, Mapx fields) throws Exception {
		StringBuffer returnIDs = new StringBuffer();
		String returnPaths = "";
		String returnValue = "0";
		String errorMessage = "";
		String taskID = "";
		String filePath = "";// 文件路径 多个用|隔开
		String catalogID = (String) fields.get("CatalogID");
		String absolutePath = (String) fields.get("AbsolutePath");
		String fileType = (String) fields.get("FileType");

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		String innerCode = "";
		String siteAlias = "";
		String path = "";
		long siteID = 0L;

		if (catalogID == null) {
			if (IMAGE.equals(fileType)) {
				catalogID = new QueryBuilder("select ID from ZCCatalog  where type='" + Catalog.IMAGELIB + "' and Name ='默认图片'  and siteid=?", Application.getCurrentSiteID()).executeString();
				if (StringUtil.isEmpty(catalogID)) {
					catalogID = new QueryBuilder("select ID from ZCCatalog  where type='" + Catalog.IMAGELIB + "' and siteid=?", Application.getCurrentSiteID()).executeString();
				}
			}
		}
		if (StringUtil.isEmpty(catalogID)) {
			logger.warn("没有找到选择分类库");
			return new String[] { returnIDs.toString(), "1", "没有找到选择分类库", taskID, filePath, returnPaths };
		}
		catalog.setID(Long.parseLong(catalogID));
		if (!catalog.fill()) {
			logger.warn("没有找到上传分类库:{}", catalogID);
			return new String[] { returnIDs.toString(), "1", "没有找到上传分类库:" + catalogID, taskID, filePath, returnPaths };
		}
		siteID = catalog.getSiteID();
		siteAlias = SiteUtil.getAlias(siteID);
		innerCode = catalog.getInnerCode();
		HtmlNameParser nameParser = new HtmlNameParser(null, catalog.toDataRow(), null, catalog.getListNameRule());
		HtmlNameRule h = nameParser.getNameRule();
		path = "upload/" + fileType + "/" + h.getDirPath() + "/";
		absolutePath = (getServletContext().getRealPath(baseDir + "/" + siteAlias + "/" + path) + "/").replaceAll("//", "/");
		logger.info("文件上传AbsolutePath：{}", absolutePath);

		// 如果没有AbsolutePath的目录，自动创建
		if (!"".equals(absolutePath) && absolutePath != null) {
			File dir = new File(absolutePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		fields.put("AbsolutePath", absolutePath);

		// 支持上传多个文件
		ArrayList fileList = new ArrayList(); // 存储处理过的文件，添加到分发队列中
		Transaction trans = new Transaction();
		DataCollection dc = new DataCollection();
		dc.putAll(fields);
		// 存储文件信息到数据库中
		if (IMAGE.equalsIgnoreCase(fileType)) {
			String rootPath = Config.getContextPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
			rootPath = rootPath.replaceAll("///", "/");
			rootPath = rootPath.replaceAll("//", "/");

			Object[] vs = files.valueArray();
			for (int m = files.size() - 1; m >= 0; m--) {
				FileItem uplFile = (FileItem) vs[m];
				String fileNameLong = uplFile.getName();
				fileNameLong = fileNameLong.replaceAll("\\\\", "/");
				String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1); // 原文件名
				String ext = getExtension(oldName); // 后缀名

				if (PubFun.isAllowExt(ext, fileType)) {
					if ("zip".equalsIgnoreCase(ext)) {
						String tempPath = (getServletContext().getRealPath(baseDir + "/" + siteAlias) + "/Temp/").replaceAll("//", "/");
						new File(tempPath).mkdirs();
						File oldZipFile = new File(tempPath, oldName);
						uplFile.write(oldZipFile);
						String unZipDir = tempPath + oldName.substring(0, oldName.lastIndexOf("."));
						FileUtil.deleteFromDir(unZipDir);
						ZipUtil.unzip(tempPath + oldName, unZipDir, false);
						File[] imageFiles = new File(unZipDir).listFiles();
						Arrays.sort(imageFiles, new Comparator() {
							public int compare(Object o1, Object o2) {
								File file1 = (File) o1;
								File file2 = (File) o2;
								return -file1.getName().compareTo(file2.getName());
							}
						});
						for (int i = 0; i < imageFiles.length; i++) {
							oldName = imageFiles[i].getName(); // 原文件名
							ext = getExtension(oldName); // 后缀名
							oldName = oldName.substring(0, oldName.indexOf("."));
							if (PubFun.isAllowExt(ext, fileType)) {
								// 存储文件信息到数据库中
								if (IMAGE.equalsIgnoreCase(fileType)) {
									long imageID = NoUtil.getMaxID("DocID");
									int random = NumberUtil.getRandomInt(10000);
									String srcFileName = imageID + "" + random + "." + ext;
									FileUtil.copy(imageFiles[i], absolutePath + srcFileName);

									ZCImageSchema image = new ZCImageSchema();
									image.setID(imageID);
									returnIDs.append(imageID + ",");
									String fileinfo = fields.get(uplFile.getFieldName() + "Info").toString();
									image.setName(oldName);
									if (fileinfo.equals("")) {
										image.setInfo(oldName);
									} else {
										image.setInfo(fileinfo);
									}
									image.setOldName(oldName);
									image.setSiteID(siteID);
									image.setCatalogID(Long.parseLong(catalogID));
									image.setCatalogInnerCode(innerCode);
									image.setBranchInnerCode(User.getBranchInnerCode());
									image.setPath(path);
									if ("tif".equalsIgnoreCase(ext) || "tiff".equalsIgnoreCase(ext)) {
										image.setFileName(imageID + "" + NumberUtil.getRandomInt(10000) + ".jpg");
									} else {
										image.setFileName(imageID + "" + NumberUtil.getRandomInt(10000) + "." + ext);
									}
									image.setSrcFileName(srcFileName);
									image.setSuffix(ext);
									image.setWidth(0);
									image.setHeight(0);
									image.setOrderFlag(OrderUtil.getDefaultOrder());
									image.setFileSize(FileUtils.byteCountToDisplaySize(imageFiles[i].length()));
									image.setHitCount(0);
									image.setStickTime(0);
									String ProduceTime = (String) fields.get("ProduceTime");
									if (StringUtil.isNotEmpty(ProduceTime)) {
										image.setProduceTime(DateUtil.parse(ProduceTime));
									}
									image.setAuthor((String) fields.get("Author"));
									image.setSystem("CMS");
									image.setAddTime(new Date());
									image.setAddUser(User.getUserName());
									image.setModifyTime(new Date());
									image.setModifyUser(User.getUserName());
									image.setStatus(Article.STATUS_TOPUBLISH);
									trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(catalogID), image.getID(), fields), Transaction.INSERT);
									trans.add(image, Transaction.INSERT);
									ArrayList imageList = null;
									try {
										imageList = ImageUtilEx.afterUploadImage(image, absolutePath, fields);
									} catch (Throwable e) {
										logger.error(e.getMessage(), e);
										if ("Unsupported Image Type".equals(e.getMessage())) {
											errorMessage = oldName + "该图片格式不支持，此图片的色彩模式可能为CMYK，请把图片的色彩模式调整为RGB模式后再上传！";
										} else {
											errorMessage = e.getMessage();
										}
										logger.warn("-------------读取图片文件{} 失败！-------------", srcFileName);
										return new String[] { returnIDs.toString(), "1", errorMessage, taskID, filePath, returnPaths };
									}

									// OtherID begin
									if (StringUtil.isNotEmpty((String) fields.get("OtherIDs"))) {
										String[] OtherIDs = StringUtil.splitEx((String) fields.get("OtherIDs"), ",");
										for (int j = 0; j < OtherIDs.length; j++) {
											ZCImageSchema imageClone = (ZCImageSchema) image.clone();
											imageClone.setID(NoUtil.getMaxID("DocID"));
											imageClone.setCatalogID(OtherIDs[j]);
											imageClone.setCatalogInnerCode(CatalogUtil.getInnerCode(OtherIDs[j]));
											trans.add(imageClone, Transaction.INSERT);
										}
									}
									// OtherID end

									fileList.add(absolutePath + srcFileName);
									fileList.addAll(imageList);

									returnValue = "0";
									errorMessage = "";
								}
							}
						}
						oldZipFile.delete();
						FileUtil.delete(unZipDir);
					} else {
						long imageID = NoUtil.getMaxID("DocID");
						int random = NumberUtil.getRandomInt(10000);
						String srcFileName = imageID + "" + random + "." + ext;
						uplFile.write(new File(absolutePath, srcFileName));
 						ZCImageSchema image = new ZCImageSchema();
						image.setID(imageID);
						returnIDs.append(imageID + ",");
						Object obj = fields.get(uplFile.getFieldName() + "Name");
						String fileName = obj == null ? "无标题" : obj.toString();
						image.setName(fileName);
						image.setOldName(oldName.substring(0, oldName.lastIndexOf(".")));
						image.setSiteID(siteID);
						image.setCatalogID(Long.parseLong(catalogID));
						image.setCatalogInnerCode(innerCode);
						image.setBranchInnerCode(User.getBranchInnerCode());
						image.setPath(path);
						image.setStatus(Article.STATUS_TOPUBLISH);
						if ("tif".equalsIgnoreCase(ext) || "tiff".equalsIgnoreCase(ext)) {
							image.setFileName(imageID + "" + NumberUtil.getRandomInt(10000) + ".jpg");
						} else {
							image.setFileName(imageID + "" + NumberUtil.getRandomInt(10000) + "." + ext);
						}

						image.setSrcFileName(srcFileName);
						image.setSuffix(ext);
						image.setWidth(0);
						image.setHeight(0);
						image.setOrderFlag(OrderUtil.getDefaultOrder());
						image.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
						image.setHitCount(0);
						image.setStickTime(0);
						image.setInfo((String) fields.get(uplFile.getFieldName() + "Info"));
						String ProduceTime = (String) fields.get("ProduceTime");
						if (StringUtil.isNotEmpty(ProduceTime)) {
							image.setProduceTime(DateUtil.parse(ProduceTime));
						}
						image.setAuthor((String) fields.get("Author"));
						image.setSystem("CMS");
						image.setAddTime(new Date());
						image.setAddUser(User.getUserName());
						image.setModifyTime(new Date());
						image.setModifyUser(User.getUserName());
						trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(catalogID), image.getID(), fields), Transaction.INSERT);
						trans.add(image, Transaction.INSERT);
						ArrayList imageList = null;
						try {
							imageList = ImageUtilEx.afterUploadImage(image, absolutePath, fields);
						} catch (Throwable e) {
							logger.error(e.getMessage(), e);
							if ("Unsupported Image Type".equals(e.getMessage())) {
								errorMessage = oldName + "该图片格式不支持，此图片的色彩模式可能为CMYK，请把图片的色彩模式调整为RGB模式后再上传！";
							} else {
								errorMessage = e.getMessage();
							}
							logger.warn("-------------读取图片文件{}失败！-------------", srcFileName);
							return new String[] { returnIDs.toString(), "1", errorMessage, taskID, filePath, returnPaths };
						}

						// OtherID begin
						if (StringUtil.isNotEmpty((String) fields.get("OtherIDs"))) {
							String[] OtherIDs = StringUtil.splitEx((String) fields.get("OtherIDs"), ",");
							for (int j = 0; j < OtherIDs.length; j++) {
								ZCImageSchema imageClone = (ZCImageSchema) image.clone();
								imageClone.setID(NoUtil.getMaxID("DocID"));
								imageClone.setCatalogID(OtherIDs[j]);
								imageClone.setCatalogInnerCode(CatalogUtil.getInnerCode(OtherIDs[j]));
								trans.add(imageClone, Transaction.INSERT);
							}
						}
						// OtherID end

						fileList.add(absolutePath + srcFileName);
						fileList.addAll(imageList);

						returnValue = "0";
						errorMessage = "";

						if (StringUtil.isEmpty(returnPaths)) {
							returnPaths += rootPath + path + "1_" + image.getFileName();
						} else {
							returnPaths += "," + rootPath + path + "1_" + image.getFileName();
						}

						// 为word上传图片使用
						if (m == 0 || files.size() == 1) {
							filePath = rootPath + path + "1_" + image.getFileName();
						} else {
							filePath = rootPath + path + "1_" + image.getFileName() + "|";
						}
					}
				}
			}
			trans.add(new QueryBuilder("update zccatalog set isdirty = 1,total = (select count(*) from zcimage where catalogID =?) where ID =?", catalog.getID(), catalog.getID()));
		}

		/*
		 * 去掉图片上传完后自动发布的逻辑，影响图片上传速度。 lanjun 2010/7/8 try { // 发布栏目 if
		 * (StringUtil.isNotEmpty(catalog.getIndexTemplate()) ||
		 * StringUtil.isNotEmpty(catalog.getListTemplate()) ||
		 * StringUtil.isNotEmpty(catalog.getDetailTemplate())) { Publisher p =
		 * new Publisher(); p.publishCatalog(catalog.getID(), false, true,1); }
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

		// 分发任务
		Deploy d = new Deploy();
		trans.add(d.getJobs(siteID, fileList, Deploy.OPERATION_COPY), Transaction.INSERT);

		trans.commit();
		int t = returnIDs.lastIndexOf(",");
		if (t != -1) {
			returnIDs.deleteCharAt(t);
		}
		return new String[] { returnIDs.toString(), returnValue, errorMessage, taskID, filePath, returnPaths };
	}

	/**
	 * ，生成多幅打水印的缩略图
	 * 
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

}