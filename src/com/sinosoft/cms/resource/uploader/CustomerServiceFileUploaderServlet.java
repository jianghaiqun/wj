package com.sinosoft.cms.resource.uploader;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.CustomServiceFileSchema;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
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

/**
 * @ClassName: CustomerServiceFileUploaderServlet
 * @Description: 
 * @author wangwenying
 * @date 2016-1-21 下午02:35:40
 *
 *       <p>
 *       修改历史
 *       </p>
 *       <p>
 *       序号 日期 修改人 修改原因
 *       </p>
 */
public class CustomerServiceFileUploaderServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceFileUploaderServlet.class);

	private static final long serialVersionUID = 1L;

	private static boolean enabled = false;

	public void init() throws ServletException {
		enabled = (new Boolean(getInitParameter("enabled"))).booleanValue();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=" + Constant.GlobalCharset);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String returnPath = "";
		String returnValue = "0";
		String errorMessage = "";

		if (!enabled) {
			returnValue = "1";
			errorMessage = "This file uploader is disabled. Please check the WEB-INF/web.xml file";

		} else {
			FileItemFactory fileFactory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fileFactory);
			upload.setHeaderEncoding(Constant.GlobalCharset);
			upload.setSizeMax(1024 * 1024 * 80);// 20M
			try {
				List items = upload.parseRequest(request);
				Mapx fields = new Mapx();
				Mapx files = new Mapx();
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField()) {
						fields.put(item.getFieldName(),
								item.getString(Constant.GlobalCharset));
					} else {
						String OldFileName = item.getName();
						long size = item.getSize();
						if ((OldFileName == null || OldFileName.equals(""))
								&& size == 0) {
							continue;
						} else {
							logger.info("-----UploadFileName:-----{}", OldFileName);
							files.put(item.getFieldName(), item);
						}
					}
				}

				String[] arr = this.upload(files, fields);

				returnValue = arr[0];
				errorMessage = arr[1];
				returnPath = arr[2];
			} catch (SizeLimitExceededException ex) {
				logger.error(ex.getMessage(), ex);
				returnValue = "-1";
				errorMessage = "文件过大，最大允许上传20M文件";
			} catch (Throwable ex) {
				logger.error(ex.getMessage(), ex);
				returnValue = "-1";
				errorMessage = "上传文件失败:" + ex.getMessage();
			}
		}
		logger.info("-----UploadSuccess:-----" + returnPath);

		out.println("<script type=\"text/javascript\">");
		out.println("window.parent.onUploadCompleted(" + returnValue + ",'"
				+ errorMessage + "','" + returnPath
				+ "');");
		out.println("</script>");
		out.flush();
		out.close();
	}

	/**
	 * 上传
	 * 
	 * @param files
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private String[] upload(Mapx files, Mapx fields)
			throws Exception {

		String returnPath = "";
		String returnPaths = "";
		String returnValue = "0";
		String errorMessage = "";

		String filePath = "";
		String srcFileName = "";
		int i = 1;
		Object[] vs = files.valueArray();
		for (int m = 0; m < files.size(); m++) {
			FileItem uplFile = (FileItem) vs[m];
			String fileNameLong = uplFile.getName();
			fileNameLong = fileNameLong.replaceAll("\\\\", "/");
			String oldName = fileNameLong.substring(fileNameLong
					.lastIndexOf("/") + 1); // 原文件名
			String ext = getExtension(oldName); // 后缀名

			if ("xls,xlsx,csv,xml,txt".indexOf(ext.toLowerCase()) >= 0) {
				long fileID = NoUtil.getMaxID("CustomServiceFile");
				int random = NumberUtil.getRandomInt(10000);
				srcFileName = fileID + "" + random + "." + ext;
				filePath = "CustomServiceFile/" + PubFun.getCurrentYear() + "/";
				if (!new File(Config.getContextRealPath() + "/temp/" + filePath)
						.exists()) {
					new File(Config.getContextRealPath() + "/temp/" + filePath)
							.mkdirs();
				}
				uplFile.write(new File(Config.getContextRealPath() + "/temp/"
						+ filePath, srcFileName));
				boolean result = false;
				if (StringUtil.isNotEmpty(fields.getString("File" + i + "Name")) && StringUtil.isNotEmpty(fields.getString("File" + i + "Info"))) {
					result = saveServiceInfoFile(fields.getString("File" + i + "Name"), fields.getString("File" + i + "Info"), filePath
							+ srcFileName, ext.toLowerCase());
				} else {
					result = true;
				}

				if (result) {
					i++;
				} else {
					returnValue = "2";
					errorMessage = "保存失败!";
					returnPath = filePath + srcFileName;
					return new String[] { returnValue, errorMessage, returnPath };
				}
			} else {
				returnValue = "2";
				errorMessage = "格式不支持!";
				return new String[] { returnValue, errorMessage, returnPath };
			}
			if (StringUtil.isNotEmpty(returnPaths)) {
				returnPaths += "," +  filePath + srcFileName;
			} else {
				returnPaths = filePath + srcFileName;
			}
		}
		errorMessage = "保存成功!";
		returnValue = "1";
		returnPath = returnPaths;

		return new String[] { returnValue, errorMessage, returnPath };
	}

	/**
	 * @Title: saveServiceInfoFile.
	 * @Description: (保存文件).
	 * @return boolean 返回类型.
	 * @author CongZN.
	 */
	public boolean saveServiceInfoFile(String fileName, String fileDesc, String filePath, String fileType) {
		boolean result = false;
		try {
			Transaction ts = new Transaction();
			CustomServiceFileSchema schema = new CustomServiceFileSchema();
			schema.setid(NoUtil.getMaxNo("CustomServiceFile"));
			schema.setFileName(fileName);
			schema.setFileDesc(fileDesc);
			schema.setFilePath(filePath);
			schema.setFileType(fileType);
			schema.setAddTime(new Date());
			schema.setAddUser(User.getUserName());
			schema.setuseFlag("N");
			ts.add(schema, Transaction.INSERT);

			if (ts.commit()) {
				result = true;
			}
		} catch (Exception e) {
			logger.error("客服询单转化-上传附件,保存 Table CustomServiceFileSchema 出现异常!" + e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 获取后缀名
	 * 
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
}