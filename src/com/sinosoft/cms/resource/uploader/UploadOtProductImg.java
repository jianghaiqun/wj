/**
 * 
 */
package com.sinosoft.cms.resource.uploader;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.fileupload.FileItem;

import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.UploadStatus;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.OtProductImageInfoSchema;

/**
 * @author wangcaiyun
 *
 */
public class UploadOtProductImg {

	public static void upload(Mapx files, Mapx fields) throws Exception {
		String absolutePath = (String) fields.get("AbsolutePath");
		String productId = fields.getString("productId");
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			String fileNameLong = uplFile.getName();
			fileNameLong = fileNameLong.replaceAll("\\\\", "/");
			String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1); // 原文件名
			String ext = ZUploaderServlet.getExtension(oldName); // 后缀名
			
			long id = NoUtil.getMaxID("OtProductImgID");
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = id + "" + random + "." + ext;
			uplFile.write(new File(absolutePath, srcFileName));
			OtProductImageInfoSchema schema = new OtProductImageInfoSchema();
			schema.setid(String.valueOf(id));
			schema.setimagePath(fields.getString("PathData"));
			schema.setimageName(srcFileName);
			schema.setproductId(productId);
			schema.setcreateUser(User.getUserName());
			schema.setcreateDate(new Date());
			schema.setmodifyUser(User.getUserName());
			schema.setmodifyDate(new Date());
			schema.insert();
			OSSUploadFile.uploadFile(absolutePath+srcFileName);
			UploadStatus.setTask(fields.getString("taskID"), id + "", fields.getString("FileType"), fields
					.getString("PathData")
					+ srcFileName, "文件上传完成");
		}
	}
	
}
