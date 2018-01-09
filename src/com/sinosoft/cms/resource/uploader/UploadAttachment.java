package com.sinosoft.cms.resource.uploader;

import com.sinosoft.cms.datachannel.Deploy;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.document.Article;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.UploadStatus;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCAttachmentSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class UploadAttachment {

	private static final Logger logger = LoggerFactory.getLogger(UploadAttachment.class);

	public static void repeatUpload(Mapx files, Mapx fields) throws Exception {
		ZCAttachmentSchema attach = new ZCAttachmentSchema();
		attach.setID(fields.getString("RepeatID"));
		if (!attach.fill()) {
			UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"),
					fields.getString("FileType"), "empty", "上传失败，未找到文件,完成");
			return;
		}
		ArrayList fileList = new ArrayList(); // 存储处理过的文件，添加到分发队列中
		Transaction trans = new Transaction();
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"),
					fields.getString("FileType"), fields.getString("PathData"), "处理中");
			String AbsolutePath = fields.getString("AbsolutePath");
			uplFile.write(new File(AbsolutePath + attach.getSrcFileName()));
			attach.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
		}
		attach.setModifyTime(new Date());
		attach.setModifyUser(User.getUserName());
		attach.setStatus(Article.STATUS_EDITING);
		trans.add(attach,Transaction.UPDATE);
		
		// 分发任务
		Deploy d = new Deploy();
		trans.add(d.getJobs(Application.getCurrentSiteID(), fileList, Deploy.OPERATION_COPY), Transaction.INSERT);
        trans.commit();
        
		UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"), fields.getString("FileType"),
				fields.getString("PathData") + attach.getSrcFileName(), "文件上传完成");
	}

	public static void upload(Mapx files, Mapx fields) throws Exception {
		String CatalogID = fields.getString("CatalogID");
		//根据类别名称获取保险公司编码 add by guozhichao 20161214
		QueryBuilder qb = new QueryBuilder("SELECT b.SupplierCode FROM zccatalog a,fdinscom b "
				+ "where a.`Name` = b.ShortName and a.ID = ? limit 1",CatalogID);
		String companyCode = qb.executeString();//保险公司编码
		
		String AbsolutePath = fields.getString("AbsolutePath");

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(CatalogID);
		if (!catalog.fill()) {
			logger.error("没有找到上传库");
		}

		// 支持上传多个文件
		ArrayList fileList = new ArrayList(); // 存储处理过的文件，添加到分发队列中
		Transaction trans = new Transaction();
		DataCollection dc = new DataCollection();
		dc.putAll(fields);
		// 存储文件信息到数据库中
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			String fileNameLong = uplFile.getName();
			fileNameLong = fileNameLong.replaceAll("\\\\", "/");
			String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1); // 原文件名
			String ext = ZUploaderServlet.getExtension(oldName); // 后缀名

			long attachID = NoUtil.getMaxID("DocID");
			UploadStatus.setTask(fields.getString("taskID"), attachID + "", fields.getString("FileType"), fields
					.getString("PathData"), "处理中");
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = attachID + "" + random + "." + ext;
			uplFile.write(new File(AbsolutePath, srcFileName));

			fileList.add(AbsolutePath + srcFileName);
			if (StringUtil.isEmpty(fields.getString("FileName"))) {
				fields.put("FileName", oldName.substring(0, oldName.lastIndexOf(".")));
			}

			ZCAttachmentSchema attachment = new ZCAttachmentSchema();
			attachment.setID(attachID);
			attachment.setName(fields.getString("FileName"));
			attachment.setOldName(oldName.substring(0, oldName.lastIndexOf(".")));
			attachment.setSiteID(fields.getString("SiteID"));
			attachment.setInfo(fields.getString("Info"));
			attachment.setCatalogID(Long.parseLong(CatalogID));
			attachment.setCatalogInnerCode(catalog.getInnerCode());
			attachment.setBranchInnerCode(User.getBranchInnerCode());
			attachment.setPath(fields.getString("PathData"));
			attachment.setImagePath(fields.getString("ImagePath"));
			attachment.setFileName(srcFileName);
			attachment.setSrcFileName(srcFileName);
			attachment.setSuffix(ext);
			attachment.setIsLocked(fields.getString(uplFile.getFieldName() + "Locked") + "");
			if ("Y".equals(attachment.getIsLocked())) {
				attachment.setPassword(StringUtil.md5Hex(fields.getString(uplFile.getFieldName() + "Password")));
			}
			attachment.setSystem("CMS");
			attachment.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
			attachment.setAddTime(new Date());
			attachment.setAddUser("admin");
			attachment.setOrderFlag(OrderUtil.getDefaultOrder());
			attachment.setModifyTime(new Date());
			attachment.setModifyUser(User.getUserName());
			attachment.setProp1("0");
			attachment.setStatus(Article.STATUS_TOPUBLISH);
			//设置附件关联保险公司 add by guozhicao 20161214
			attachment.setProp2(companyCode);
			
			trans.add(attachment, Transaction.INSERT);
			trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(CatalogID), attachment.getID(), dc),Transaction.INSERT);
			UploadStatus.setTask(fields.getString("taskID"), attachID + "", fields.getString("FileType"), fields
					.getString("PathData")
					+ srcFileName, "文件上传完成");
		}
		trans
				.add(new QueryBuilder(
						"update zccatalog set isdirty = 1,total = (select count(*) from zcattachment where catalogID =?) where ID =?",
						catalog.getID(), catalog.getID()));
		
		// 分发任务
		Deploy d = new Deploy();
		trans.add(d.getJobs(Application.getCurrentSiteID(), fileList, Deploy.OPERATION_COPY), Transaction.INSERT);

		trans.commit();
	}

}
