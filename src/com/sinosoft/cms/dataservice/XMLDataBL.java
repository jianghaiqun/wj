package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class XMLDataBL extends Page {

	/**
	 * 导入数据库
	 */
	public static void uploadDB(HttpServletRequest request, HttpServletResponse response) {
		try {
			DiskFileItemFactory fileFactory = new DiskFileItemFactory();
			ServletFileUpload fu = new ServletFileUpload(fileFactory);
			List fileItems = fu.parseRequest(request);
			fu.setHeaderEncoding("UTF-8");
			Iterator iter = fileItems.iterator();

			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					String OldFileName = item.getName();
					
					logger.info("Upload XML FileName:{}", OldFileName);
					long size = item.getSize();
					if ((OldFileName == null || OldFileName.equals("")) && size == 0) {
						continue;
					}
					OldFileName = OldFileName.substring(OldFileName.lastIndexOf("\\") + 1);
					
					String ext = OldFileName.substring(OldFileName.lastIndexOf("."));
					if (!ext.toLowerCase().equals(".xml")) {
						response.sendRedirect("XMLData.jsp?upStatus=0&Error=上传失败，只能导入xml格式的文件");
						return;
					}
					if (!OldFileName.equals("01.xml") && !OldFileName.equals("09.xml")) {
						response.sendRedirect("XMLData.jsp?upStatus=0&Error=上传失败，只能导入01.xml(核保)和09.xml(承保)的xml");
						return;
					}
					
					final String FileName = Config.getContextRealPath() + "WEB-INF/data/backup/"+OldFileName;
					item.write(new File(FileName));

			        // 读取XML文件
					XMLParseIN tXMLParseIN = new XMLParseIN();
					if(!tXMLParseIN.xmlParseIN(FileName,OldFileName,"2011")){ //2011代表机构编码
						response.sendRedirect("XMLData.jsp?upStatus=0&Error=上传失败，解析xml文件出错");
						return;
					}									
					response.sendRedirect("XMLData.jsp?upStatus=1");
				}
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {

		}
	}
	
}

