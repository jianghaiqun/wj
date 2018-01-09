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

import com.sinosoft.framework.Config;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class UploaderImgServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(UploaderImgServlet.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		String saveSubDir = "//EPolicy//UnderwritingOffline//";//存储线下核保图片子目录
		String BrowserIsFireFox =""; //FireFox多传一张图
		String Content = "Succ";
		String FlagStr = "";
		String cus = "";
		String imgUrls = "";
		String ServerPath = Config.getValue("newPolicyPath");//测试//Config.getContextRealPath();
		String thistime = DateUtil.toString(new Date(), "yyyy/MM/dd/");
		File file =new File(ServerPath + saveSubDir + thistime);     
		//如果文件夹不存在则创建    
		if  (!file .exists()  && !file .isDirectory())      
		{       
		    file .mkdirs();    
		}
		// 得到静态资源服务器的访问路径
		response.setContentType("text/html"); 
		// 设置字符编码为UTF-8, 这样支持汉字显示
		response.setCharacterEncoding("UTF-8");
		// 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		// 设置存放临时文件的目录,web根目录下的temp目录
		dfif.setRepository(new File(ServerPath + saveSubDir + thistime));
		// 用以上工厂实例化上传组件
		ServletFileUpload sfu = new ServletFileUpload(dfif);
		PrintWriter out = response.getWriter();
		// 从request得到 所有 上传域的列表
		List<?> fileList = null;
		try {
			fileList = sfu.parseRequest(request);
		} catch (FileUploadException e) {
			logger.error(e.getMessage(), e);
			out.println("<script>parent.afterUpLoad('Fail', '图片保存失败，原因是:'" + e.getMessage() + "')</script>");
		 return;
		}


		// 得到所有上传的文件
		Iterator<?> fileItr = fileList.iterator();
		// 循环处理所有文件
		int whileI =0;
		while (fileItr.hasNext()) {
			FileItem fileItem = null;
			String path = null;
			long size = 0;
			// 得到当前文件
			fileItem = (FileItem) fileItr.next();
			if("BrowserIsFireFox".equals(fileItem.getString())){
				BrowserIsFireFox = "FireFox";
			}
			if("underwritingOfflineInfo.productId".equals(fileItem.getFieldName())){
				Mapx<String, String> underwritingOfflineProducts =  CacheManager.getMapx("Code", "underwritingOfflineProducts");
				if(!underwritingOfflineProducts.containsKey(fileItem.getString())){
					Content = "Wrong";
					FlagStr = "该产品不支持线下核保！";
					out.print("<script>window.parent.afterUpLoad('" + FlagStr + "', '" + Content + "','"+ cus +"');</script>");
					return;
				}
			}
			if (fileItem == null || fileItem.isFormField()) {
				continue;
			}
			// 得到文件的完整路径
			path = fileItem.getName();
			if(StringUtil.isEmpty(path)){
				continue;
			}
			if(path.length()<3 || !("bmp".equals(path.substring(path.length()-3,path.length()))||
					"png".equals(path.substring(path.length()-3,path.length()))||"jpg".equals(path.substring(path.length()-3,path.length())))){
				Content = "Wrong";
				FlagStr = "请上传.jpg,png和bmp格式的文件！";
				out.print("<script>window.parent.afterUpLoad('" + FlagStr + "', '" + Content + "','"+ cus +"');</script>");
				return;
			}
			// 得到文件的大小
			size = fileItem.getSize();
			if ("".equals(path) || size == 0) {
				continue;
			}
			if (size>5242880) {
				Content = "Wrong";
				FlagStr = "上传图片不得大于5M";
				out.print("<script>window.parent.afterUpLoad('" + FlagStr + "', '" + Content + "','"+ cus +"');</script>");
				return;
			}
			// 系统默认文件存放处
			String savePathName =  System.currentTimeMillis()+ whileI + path.substring(path.length()-4,path.length());
			whileI++;
			try {
				fileItem.write(new File( ServerPath + saveSubDir + thistime + savePathName));
				if(StringUtil.isEmpty(imgUrls)){
					imgUrls = savePathName;
				}else{
					imgUrls = imgUrls + ","+savePathName;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				Content = "Wrong";
				FlagStr =  e.getMessage();
				out.print("<script>window.parent.afterUpLoad('" + FlagStr + "', '" + Content + "','"+ cus +"');</script>");
				return;
			}
 
			 
			
		}
		if(("FireFox").equals(BrowserIsFireFox)){
			  String imgUrl[] = imgUrls.split(",");
			  new File(ServerPath + saveSubDir + thistime + imgUrl[imgUrl.length-1]).delete();
			  cus = imgUrls.replace(","+imgUrl[imgUrl.length-1], "");
		}else{
			 cus = imgUrls;
		}
		 if(StringUtil.isEmpty(cus)){
			Content = "Wrong";
			FlagStr = "请上传报告！";
			out.print("<script>window.parent.afterUpLoad('" + FlagStr + "', '" + Content + "','"+ cus +"');</script>");
			return;
		 }
		out.print("<script>window.parent.afterUpLoad('" + FlagStr + "', '" + Content + "','"+ cus +"');</script>");
		return;
	}


}