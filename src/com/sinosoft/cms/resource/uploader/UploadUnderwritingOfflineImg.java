/**
 * 
 */
package com.sinosoft.cms.resource.uploader;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.UploadStatus;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.ImageJDKUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;

/**
 * @author wangcaiyun
 *
 */
public class UploadUnderwritingOfflineImg {
	
	public static void upload(Mapx files, Mapx fields) throws Exception {
		String pathData = (String) fields.get("PathData");
		String infoID = (String) fields.get("infoID");
		String operator = User.getUserName();
		
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			String fileNameLong = uplFile.getName();
			fileNameLong = fileNameLong.replaceAll("\\\\", "/");
			String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1); // 原文件名
			String ext = ZUploaderServlet.getExtension(oldName); // 后缀名
			
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = System.currentTimeMillis() + "" + random + "." + ext;
			uplFile.write(new File(pathData, srcFileName));
			String fileSize = FileUtils.byteCountToDisplaySize(uplFile.getSize()).replace(" ", "");
			String filePath = pathData + srcFileName;
			QueryBuilder qb = new QueryBuilder("insert into underwriting_offline_images (info_id,image_url,image_size,image_desc,thum_url1,thum_url2,operator,create_time,modify_time) values(?,?,?,?,?,?,?,now(),now())");
			qb.add(infoID);
			qb.add(filePath);
			qb.add(fileSize);
			qb.add("");
			if(filePath.toLowerCase().endsWith(".bmp")){
				qb.add(ImageJDKUtil.compressPic(filePath, "1", 0.1));
			}else{
				qb.add(UploadCustomClaimsData.scaleRateImageFile(filePath, "1", 0.1));
			}
			if (UploadCustomClaimsData.isCondenseFile(fileSize)) {
				if(filePath.toLowerCase().endsWith(".bmp")){
					qb.add(ImageJDKUtil.compressPic(filePath, "2", 0.6));
				}else{
					qb.add(UploadCustomClaimsData.scaleRateImageFile(filePath, "2", 0.6));
				}
			} else {
				qb.add(filePath);
			}
			qb.add(operator);
			qb.executeNoQuery();
			
			UploadStatus.setTask(fields.getString("taskID"), infoID, fields.getString("FileType"), fields
					.getString("PathData")
					+ srcFileName, "文件上传完成");
		}
	}
}
