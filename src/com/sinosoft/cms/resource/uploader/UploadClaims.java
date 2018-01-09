/**
 * 
 */
package com.sinosoft.cms.resource.uploader;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.UploadStatus;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ClaimsInfoSchema;

/**
 * @author wangcaiyun
 *
 */
public class UploadClaims {

	public static void upload(Mapx files, Mapx fields) throws Exception {
		String absolutePath = (String) fields.get("AbsolutePath");
		String fileType = (String) fields.get("FileType");
		String fileName = (String) fields.get("FileName");
		String comCode = (String) fields.get("comCode");
		String classifyId = (String) fields.get("ClassifyType");
		String isHot = (String) fields.get("isHot");
		String orderFlag = (String) fields.get("orderFlag");
		if (StringUtil.isEmpty(orderFlag)) {
			orderFlag = String.valueOf(System.currentTimeMillis());
		}
		if (StringUtil.isEmpty(isHot)) {
			isHot = "N";
		}
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			String fileNameLong = uplFile.getName();
			fileNameLong = fileNameLong.replaceAll("\\\\", "/");
			String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1); // 原文件名
			String ext = ZUploaderServlet.getExtension(oldName); // 后缀名
			
			long id = NoUtil.getMaxID("ClaimsID");
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = id + "" + random + "." + ext;
			uplFile.write(new File(absolutePath, srcFileName));
			ClaimsInfoSchema schema = new ClaimsInfoSchema();
			schema.setid(String.valueOf(id));
			schema.setfileName(srcFileName);
			schema.setfileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
			schema.setsuffix(ext);
			schema.setpath(fields.getString("PathData"));
			schema.setname(fileName);
			schema.setcomCode(comCode);
			schema.setclassifyId(classifyId);
			schema.setisHot(isHot);
			schema.setorderFlag(orderFlag);
			schema.setCreateUser(User.getUserName());
			schema.setCreateDate(new Date());
			schema.insert();
			OSSUploadFile.uploadFile(absolutePath+srcFileName);
			UploadStatus.setTask(fields.getString("taskID"), id + "", fields.getString("FileType"), fields
					.getString("PathData")
					+ srcFileName, "文件上传完成");
		}
	}
	
	public static void repeatUpload(Mapx files, Mapx fields) throws Exception {
		ClaimsInfoSchema schema = new ClaimsInfoSchema();
		schema.setid(fields.getString("RepeatID"));
		if (!schema.fill()) {
			UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"),
					fields.getString("FileType"), "empty", "上传失败，未找到文件,完成");
			return;
		}
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			String AbsolutePath = fields.getString("AbsolutePath");
			String path = Config.getContextRealPath()+ Config.getValue("UploadDir") + "/kxb/";
			OSSUploadFile.deleteObject(path+schema.getpath()+schema.getfileName());
			uplFile.write(new File(AbsolutePath + schema.getfileName()));
			schema.setpath(fields.getString("PathData"));
			schema.setfileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
			schema.setModifyDate(new Date());
			schema.setModifyUser(User.getUserName());
			schema.update();
			OSSUploadFile.uploadFile(AbsolutePath+schema.getfileName());
			UploadStatus.setTask(fields.getString("taskID"), schema.getid() + "", fields.getString("FileType"), fields
					.getString("PathData")
					+ schema.getfileName(), "文件上传完成");
		}
	}
}
