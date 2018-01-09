/**
 * 
 */
package com.sinosoft.cms.resource.uploader;

import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.UploadStatus;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.ImageJDKUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ClaimsFileSchema;
import com.sinosoft.schema.CustomClaimsDataInfoSchema;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * @author wangcaiyun
 *
 */
public class UploadCustomClaimsData {
	private static final Logger logger = LoggerFactory.getLogger(UploadCustomClaimsData.class);
	
	public static void upload(Mapx files, Mapx fields) throws Exception {
		String pathData = (String) fields.get("PathData");
		String claimsNo = (String) fields.get("claimsNo");
		// 产品中心理赔资料ID
		String claimsData = (String) fields.get("claimsData");
		String claimsDataId = new QueryBuilder("select id from CustomClaimsDataInfo where femClaimsDataId = ? and claimsNo = ? ", claimsData, claimsNo).executeString();
		if (StringUtil.isEmpty(claimsDataId)) {
			DataTable dt = new QueryBuilder("select ClaimsDataName, PhotoNum, IsMust, ClaimsForm, ClaimsItemsId from FemClaimsData where Id = ?", claimsData).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				CustomClaimsDataInfoSchema schema = new CustomClaimsDataInfoSchema();
				claimsDataId = String.valueOf(NoUtil.getMaxID("CustomClaimsDataID"));
				schema.setid(claimsDataId);
				schema.setclaimsNo(claimsNo);
				schema.setfemClaimsDataId(claimsData);
				schema.setclaimsDataName(dt.getString(0, "ClaimsDataName"));
				schema.setphotoNum(dt.getString(0, "PhotoNum"));
				schema.setisMust(dt.getString(0, "IsMust"));
				schema.setclaimsForm(dt.getString(0, "ClaimsForm"));
				schema.setclaimsItemsId(dt.getString(0, "ClaimsItemsId"));
				schema.setclaimsDataType("00");
				schema.setcreateUser(User.getUserName());
				schema.setcreateDate(new Date());
				if (!schema.insert()) {
					UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"),
							fields.getString("FileType"), "empty", "上传失败，保存理赔资料信息失败");
					return;
				}
			} else {
				UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"),
						fields.getString("FileType"), "empty", "上传失败，未找到理赔资料信息");
				return;
			}
			
		}
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			String fileNameLong = uplFile.getName();
			fileNameLong = fileNameLong.replaceAll("\\\\", "/");
			String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1); // 原文件名
			String ext = ZUploaderServlet.getExtension(oldName); // 后缀名
			
			long id = NoUtil.getMaxID("CustomClaimsFileID");
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = System.currentTimeMillis() + "" + random + "." + ext;
			uplFile.write(new File(pathData, srcFileName));
			
			ClaimsFileSchema fileSchema = new ClaimsFileSchema();
			String filePath = pathData + srcFileName;
			fileSchema.setid(String.valueOf(id));
			fileSchema.setfileName(srcFileName);
			fileSchema.setfileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()).replace(" ", ""));
			fileSchema.setfileSuffix(ext);
			fileSchema.setfilePath(filePath);
			if (isCondenseFile(fileSchema.getfileSize())) {
				fileSchema.setremark1(scaleRateImageFile(filePath, "1", 0.6));
			} else {
				fileSchema.setremark1(filePath);
			}
			fileSchema.setremark2(scaleRateImageFile(filePath, "2", 0.1));
			fileSchema.setclaimsDataId(claimsDataId);
			fileSchema.setcreateUser(User.getUserName());
			fileSchema.setcreateDate(new Date());
			fileSchema.insert();
			UploadStatus.setTask(fields.getString("taskID"), id + "", fields.getString("FileType"), fields
					.getString("PathData")
					+ srcFileName, "文件上传完成");
		}
	}
	
	public static void repeatUpload(Mapx files, Mapx fields) throws Exception {
		ClaimsFileSchema schema = new ClaimsFileSchema();
		schema.setid(fields.getString("RepeatID"));
		if (!schema.fill()) {
			UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"),
					fields.getString("FileType"), "empty", "上传失败，未找到文件信息");
			return;
		}
		String oldFilePath = schema.getfilePath();
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			
			FileItem uplFile = (FileItem) files.get(it.next());
			String fileNameLong = uplFile.getName();
			fileNameLong = fileNameLong.replaceAll("\\\\", "/");
			String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1); // 原文件名
			String ext = ZUploaderServlet.getExtension(oldName); // 后缀名
			
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = System.currentTimeMillis() + "" + random + "." + ext;
			
			String PathData = fields.getString("PathData") + srcFileName;
			uplFile.write(new File(PathData));
			schema.setfileName(srcFileName);
			schema.setfilePath(PathData);
			schema.setfileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()).replace(" ", ""));
			if (isCondenseFile(schema.getfileSize())) {
				schema.setremark1(scaleRateImageFile(PathData, "1", 0.6));
			} else {
				schema.setremark1(PathData);
			}
			schema.setremark2(scaleRateImageFile(PathData, "2", 0.1));
			schema.setfileSuffix(ext);
			schema.setmodifyDate(new Date());
			schema.setmodifyUser(User.getUserName());
			if (schema.update()) {
				FileUtil.delete(oldFilePath);
			}
			UploadStatus.setTask(fields.getString("taskID"), schema.getid() + "", fields.getString("FileType"), fields
					.getString("PathData")
					+ schema.getfileName(), "文件上传完成");
		}
	}
	
	/**
	 * 
	 * isCondenseFile:(判断是否压缩文件). <br/>
	 * TODO(文件大小超过1MB需要压缩).<br/>
	 *
	 * @author wangcaiyun
	 * @param fileSize 文件大小
	 * @return
	 */
	public static boolean isCondenseFile(String fileSize) {
		try {
			int len = fileSize.length();
			String unit = fileSize.substring(len - 2);
			String size = fileSize.substring(0, len - 2);
			if ("KB".equalsIgnoreCase(unit)) {
				return false;
			} else if ("MB".equalsIgnoreCase(unit) && Double.valueOf(size) < 1) {
				return false;
			}
			
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return true;
	}
	
	
	public static String scaleRateImageFile(String filePath, String index, double rate) {
		try {
			int sc = filePath.lastIndexOf(".");
			String newFile = filePath.substring(0, sc);
			String fileSuffix = filePath.substring(sc);
			String newFilePath = (newFile + "_" + index + fileSuffix);
			ImageJDKUtil.scaleRateImageFile(filePath, newFilePath, rate);
			return newFilePath;
		} catch (IOException ioe) {
			logger.error("MemberSaveClaimsService-压缩文件错误："+filePath+"、压缩比率："+rate + ioe.getMessage(), ioe);
		} catch (Exception e) {
			logger.error("MemberSaveClaimsService-压缩文件错误："+filePath+"、压缩比率："+rate + e.getMessage(), e);
		}
		return null;
	}
}
