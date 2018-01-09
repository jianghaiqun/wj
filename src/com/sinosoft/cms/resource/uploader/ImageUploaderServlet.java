package com.sinosoft.cms.resource.uploader;

import chinapay.Base64;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZDMailConfigImageSchema;
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
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName: ImageUploaderServlet 
 * @Description: TODO(邮件配置-图片上传类) 
 * @author XXX 
 * @date 2014-8-8 下午02:35:40 
 *
 * <p> 修改历史</p>
 * <p> 序号 日期 修改人 修改原因</p>
 */
public class ImageUploaderServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(ImageUploaderServlet.class);
	private static final long	serialVersionUID	= 1L;

	public final static String	IMAGE				= "Image";

	private static boolean		enabled				= false;

	public void init() throws ServletException {
		enabled = (new Boolean(getInitParameter("enabled"))).booleanValue();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=" + Constant.GlobalCharset);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String emailType = request.getParameter("emailType");
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
			upload.setSizeMax(1024 * 1024 * 1);// 1M
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

				String[] arr = this.upload(files, fields,emailType);

				returnValue = arr[0];
				errorMessage = arr[1];
				returnPath = arr[2];
			} catch (SizeLimitExceededException ex) {
				logger.error(ex.getMessage(), ex);
				returnValue = "-1";
				errorMessage = "文件过大，最大允许上传1M图片";
			} catch (Throwable ex) {
				logger.error(ex.getMessage(), ex);
				returnValue = "-1";
				errorMessage = "上传文件失败:" + ex.getMessage();
			}
		}
		logger.info("-----UploadSuccess:-----{}", returnPath);

		out.println("<script type=\"text/javascript\">");
		out.println("window.parent.onUploadCompleted(" + returnValue + ",'" + errorMessage + "','" + Base64.encode(returnPath.getBytes()) + "');");
		out.println("</script>");
		out.flush();
		out.close();
	}
	
	/**
	 * @Title: upload.
	 * @Description: TODO(上传).
	 * @return String[]    返回类型.
	 * @author CongZN.
	 */
	private String[] upload(Mapx files, Mapx fields,String emailType) throws Exception {

		String returnPath = "";
		String returnValue = "0";
		String errorMessage = "";

		String filePath = "";
		String srcFileName = "";
		int i = 1;
		Object[] vs = files.valueArray();
		for (int m = files.size() - 1; m >= 0; m--) {
			FileItem uplFile = (FileItem) vs[m];
			String fileNameLong = uplFile.getName();
			fileNameLong = fileNameLong.replaceAll("\\\\", "/");
			String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1); // 原文件名
			String ext = getExtension(oldName); // 后缀名

			if (PubFun.isAllowExt(ext, IMAGE)) {
				long imageID = NoUtil.getMaxID("MemberSculpture");
				int random = NumberUtil.getRandomInt(10000);
				srcFileName = imageID + "" + random + "." + ext;
				filePath = "Sculpture/" + PubFun.getCurrentYear() + "/";
				if (!new File(Config.getContextRealPath() + "/" + filePath).exists()) {
					new File(Config.getContextRealPath() + "/" + filePath).mkdirs();
				}
				uplFile.write(new File(Config.getContextRealPath() + "/" + filePath, srcFileName));
				
				boolean result = saveMailConfigImage(emailType, oldName,fields.getString("File"+i+"Name"), fields.getString("File"+i+"Info"), filePath+srcFileName,fields.getString("RepeatID"));
			
				if(result){
					i++;
				}else{
					returnValue = "2";
					errorMessage = "保存失败!";
					returnPath = filePath + srcFileName;
					return new String[] { returnValue, errorMessage, returnPath };
				}
			} else {
				returnValue = "2";
				errorMessage = "格式不支持!";
			}
		}
		returnValue = "1";
		errorMessage = "保存成功!";
		returnPath = filePath + srcFileName;

		return new String[] { returnValue, errorMessage, returnPath };
	}
	
	/**
	 * @Title: saveMailConfigImage.
	 * @Description: TODO(保存图片).
	 * @return boolean    返回类型.
	 * @author CongZN.
	 */
	public boolean saveMailConfigImage(String emailType,String imageName,String imageLink,String imageDesc,String imagePath,String repeatID){
		boolean result = false;
		try {
			Transaction ts = new Transaction();
			ZDMailConfigImageSchema zdm = new ZDMailConfigImageSchema();
			if(StringUtil.isEmpty(repeatID)){
				zdm.setID(NoUtil.getMaxNo("MailConfigImage"));
				zdm.setEmailType(emailType);
				
				zdm.setImageName(imageName);
				zdm.setImageLink(imageLink);
				zdm.setImageDesc(imageDesc);
				zdm.setImagePath(imagePath);
				zdm.setOperator(User.getUserName());
				zdm.setCreateDate(PubFun.getCurrent());
				zdm.setModifyDate(PubFun.getCurrent());
				ts.add(zdm, Transaction.INSERT);
			}else{
				zdm.setID(repeatID);
				zdm.fill();
				
				zdm.setImageName(imageName);
				zdm.setImageLink(imageLink);
				zdm.setImageDesc(imageDesc);
				zdm.setImagePath(imagePath);
				zdm.setOperator(User.getUserName());
				zdm.setModifyDate(PubFun.getCurrent());
				ts.add(zdm, Transaction.UPDATE);
			}
			
			
			if(ts.commit()){
				result = true;
			}
		} catch (Exception e) {
			logger.error("邮件模板配置-上传图片,保存 Table ZDMailConfigImage 出现异常!" + e.getMessage(), e);
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