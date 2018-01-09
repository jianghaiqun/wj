package com.sinosoft.cms.resource;

import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCAttachmentRelaSchema;
import com.sinosoft.schema.ZCAttachmentRelaSet;
import com.sinosoft.schema.ZCAttachmentSchema;
import com.sinosoft.schema.ZCAttachmentSet;
import com.sinosoft.schema.ZCCatalogSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Xuzhe
 * 
 */

public class Attachment extends Page {

	public static Mapx init(Mapx params) {
		String CatalogID = params.getString("CatalogID");
		if (StringUtil.isEmpty(CatalogID)) {
			CatalogID = new QueryBuilder("select ID from ZCCatalog where Type = " + Catalog.ATTACHMENTLIB
					+ " and SiteID =? and Name = '默认附件'", Application.getCurrentSiteID()).executeOneValue()
					+ "";
		}
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(CatalogID);
		catalog.fill();
		DataTable dt = new QueryBuilder("select name,id from zccatalog ").executePagedDataTable(100, 0);
		params.put("Who", HtmlUtil.dataTableToOptions(dt));
		params.putAll(catalog.toMapx());

		String imagePath = "upload/Image/nopicture.jpg";
		params.put("ImagePath", imagePath);
		params.put("PicSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + imagePath);
		params.put("allowType", Config.getValue("AllowAttachExt"));
		
		//允许上传附件大小设置
		String fileSize = Config.getValue("AllowAttachSize");
		if(StringUtil.isEmpty(fileSize)){
			fileSize = "20971520"; //默认20M
		}
		params.put("fileMaxSize", fileSize);
		long size = Long.parseLong(fileSize);
		String fileMaxSizeHuman = "";
		if (size < 1048576) {
			fileMaxSizeHuman = NumberUtil.round(size * 1.0 / 1024, 1) + "K";
		} else if (size < 1073741824) {
			fileMaxSizeHuman = NumberUtil.round(size * 1.0 / 1048576, 1) + "M";
		}
		params.put("fileMaxSizeHuman", fileMaxSizeHuman);
		return params;
	}
	
	/**
	 * 初始化附件对话框
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initDialog(Mapx params) {
		long attachID = Long.parseLong(params.getString("ID"));
		String imagePath = "upload/Image/nopicture.jpg";
		DataTable dt = new QueryBuilder("select imagepath from ZCAttachment where id=?", attachID).executeDataTable();
		if (dt.getRowCount() == 0 || StringUtil.isEmpty((String) dt.get(0, 0))) {
			params.put("Name", new QueryBuilder("select Name from ZCAttachment where id=?", attachID).executeString());
			params.put("Info", new QueryBuilder("select Info from ZCAttachment where id=?", attachID).executeString());
			params.put("ImagePath", imagePath);
			params.put("PicSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + imagePath);
			return params;
		}
		ZCAttachmentSchema attach = new ZCAttachmentSchema();
		attach.setID(attachID);
		attach.fill();
		params = attach.toMapx();
		imagePath = attach.getImagePath();
		params.put("PicSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + imagePath);
		params.put("CustomColumn",ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, attach.getCatalogID()+"",ColumnUtil.RELA_TYPE_DOCID, attach.getID() + ""));
		params.put("AttachmentLink", (Config.getContextPath() + "/Services/AttachDownLoad.jsp?id=" + attach.getID()).replaceAll("//", "/"));
		return params;
	}

	public static Mapx initEditDialog(Mapx params) {
		ZCAttachmentSchema Attachment = new ZCAttachmentSchema();
		Attachment.setID(params.getString("ID"));
		Attachment.fill();
		params.putAll(Attachment.toMapx());
		
		//允许上传附件大小设置
		String fileSize = Config.getValue("AllowAttachSize");
		if(StringUtil.isEmpty(fileSize)){
			fileSize = "20971520"; //默认20M
		}
		params.put("fileMaxSize", fileSize);
		return params;
	}
	
	public void dialogEdit() {
		String attachID = $V("ID");
		Transaction trans = new Transaction();
		ZCAttachmentSchema attach = new ZCAttachmentSchema();
		attach.setID(attachID);
		attach.fill();
		attach.setValue(Request);
		DataTable columnDT = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, attach.getID());
		if (columnDT.getRowCount() > 0) {
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",
					ColumnUtil.RELA_TYPE_DOCID, attach.getID()+""));
		}
		trans.add(ColumnUtil.getValueFromRequest(attach.getCatalogID(), attach.getID(), Request), Transaction.INSERT);
		trans.add(attach, Transaction.UPDATE);
		if (trans.commit()) {
			this.Response.setLogInfo(1, "编辑成功！");
		} else {
			this.Response.setLogInfo(1, "编辑失败！");
		}

	}

	public void copy() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String Alias = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		String catalogPath = CatalogUtil.getPath(catalogID);
		String newPath = "Attachment/" + catalogPath;
		String InnerCode = new QueryBuilder("select InnerCode from zccatalog where ID = ?", catalogID).executeString();
		File dir = new File(Config.getContextRealPath() + Alias + newPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYATTACHMENT, "复制附件失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		ZCAttachmentSet AttachmentSet = new ZCAttachmentSchema().query(new QueryBuilder(" where ID in (" + IDs + ")"));
		boolean flag = true;
		StringBuffer logs = new StringBuffer("复制附件:");
		for (int i = 0; i < AttachmentSet.size(); i++) {
			ZCAttachmentSchema Attachment = AttachmentSet.get(i);
			String oldFileName = Alias + Attachment.getPath() + Attachment.getFileName();
			Attachment.setID(NoUtil.getMaxID("DocID"));
			Attachment.setCatalogID(catalogID);
			Attachment.setCatalogInnerCode(InnerCode);
			Attachment.setPath(newPath);
			Attachment.setFileName(Attachment.getID()
					+ Attachment.getFileName().substring(Attachment.getFileName().lastIndexOf(".")));
			Attachment.setAddTime(new Date());
			Attachment.setAddUser(User.getUserName());
			File directory = new File(Config.getContextRealPath() + Alias + newPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			if (!FileUtil.copy(Config.getContextRealPath() + oldFileName, Config.getContextRealPath() + Alias + newPath
					+ Attachment.getFileName())) {
				flag = false;
				break;
			}
			logs.append(Attachment.getName() + ",");
		}
		if (flag && AttachmentSet.insert()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYATTACHMENT, logs + "成功", Request.getClientIP());
			this.Response.setLogInfo(1, "复制附件成功！");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYATTACHMENT, logs + "失败", Request.getClientIP());
			this.Response.setLogInfo(0, "复制附件失败！");
		}
	}
	
	public void getAttachSrc() {
		String ID = $V("AttachID");
		DataTable dt = new QueryBuilder("select name,siteid,path,srcfilename from zcattachment where id=?", ID).executeDataTable();
		if (dt.getRowCount() > 0) {
			this.Response.put("AttachPath",(Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(dt.getLong(0,"siteid")) + "/" + dt.get(0, "path").toString()
					+ dt.get(0, "srcfilename").toString()+"|"+dt.getString(0, "name")).replaceAll("//", "/"));
		}
	}

	public void transfer() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("update ZCAttachment set catalogid=? ,CatalogInnerCode=? where ID in (" + IDs + ")",
				catalogID, CatalogUtil.getInnerCode(catalogID)));
		StringBuffer logs = new StringBuffer("转移附件:");
		DataTable dt = new QueryBuilder("select Name from ZCAttachment where id in(" + IDs + ")").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			logs.append(dt.get(i, "Name") + ",");
		}
		if (trans.commit()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEATTACHMENT, logs + "成功", Request.getClientIP());
			Response.setLogInfo(1, "转移成功");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEATTACHMENT, logs + "成功", Request.getClientIP());
			Response.setLogInfo(0, "转移失败");
		}
	}

	public void del() {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELATTACHMENT, "删除附件失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		StringBuffer logs = new StringBuffer("删除附件:");
		String Alias = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		ZCAttachmentSet AttachmentSet = new ZCAttachmentSchema().query(new QueryBuilder(" where id in (" + IDs + ")"));
		ZCAttachmentRelaSet AttachmentRelaSet = new ZCAttachmentRelaSchema().query(new QueryBuilder(" where id in ("
				+ IDs + ")"));
		Transaction trans = new Transaction();
		for (int i = 0; i < AttachmentSet.size(); i++) {
			FileUtil.delete(Config.getContextRealPath() + Alias + AttachmentSet.get(i).getPath()
					+ AttachmentSet.get(i).getFileName());
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",ColumnUtil.RELA_TYPE_DOCID, AttachmentSet.get(i).getID()+""));
			logs.append(AttachmentSet.get(i).getName() + ",");
		}
		trans.add(AttachmentSet, Transaction.DELETE_AND_BACKUP);
		trans.add(AttachmentRelaSet, Transaction.DELETE_AND_BACKUP);
		trans.add(new QueryBuilder(
				"update zccatalog set total = (select count(*) from zcattachment where catalogID =?) where ID =?",
				AttachmentSet.get(0).getCatalogID(), AttachmentSet.get(0).getCatalogID()));
		if (trans.commit()) {
			//打包文件并上传到静态资源服务器 add by guozhichao 2016-11-16
			packageClaimfiles(AttachmentSet.get(0).getCatalogID());
			// 删除发布文件，更新列表
			Publisher p = new Publisher();
			p.deleteFileTask(AttachmentSet);
			
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELATTACHMENT, logs + "成功", Request.getClientIP());
			this.Response.setLogInfo(1, "删除附件成功！");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELATTACHMENT, logs + "失败", Request.getClientIP());
			this.Response.setLogInfo(0, "删除附件失败！");
		}
	}
	
	/**
	 * 打包理赔申请书并上传到静态资源服务器
	 * @param catalogID 类别id
	 * @author guozhichao
	 * @date 2016-11-16 
	 */
	public void packageClaimfiles(Long catalogID){
		//检查附件类型，只打包理赔申请书
		DataTable dtCatalog = new QueryBuilder("select b.Name as parentName,a.Type,a.Name,a.Alias from zccatalog a LEFT OUTER JOIN "
				+ "zccatalog b on a.ParentID = b.ID where a.ID = ?",catalogID).executeDataTable();//附件类型
		String parentName = dtCatalog.getString(0, "parentName");
		String name = dtCatalog.getString(0, "Name");
		String alias = dtCatalog.getString(0, "Alias");
		if(parentName == null || !parentName.equals("理赔申请书")){
			return;
		}
		//查询所有理赔申请附件，如果附件数量不为0则打包
		DataTable dt = new QueryBuilder("select Name,Path,FileName FROM zcattachment WHERE CatalogID = ?",
				catalogID).executeDataTable();
		DataTable dts = new QueryBuilder("select distinct SupplierCode FROM fdinscom WHERE shortName = ?",
				name).executeDataTable();
		if(dt.getRowCount() > 0){
			String Alias = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
			List<File> waitPackedFiles = new ArrayList<File>();
			for (int i = 0; i < dt.getRowCount(); i++) {
				String fName = Config.getContextRealPath() + Alias + dt.get(i, "Path")+dt.get(i, "FileName");
				File packedFile = new File(fName);
				if(packedFile.exists()){
					waitPackedFiles.add(packedFile);
					logger.info("打包文件->{}", fName);
				}else{
					logger.warn("文件->{}不存在，不执行打包", fName);
				}
			}
			if(dts.getRowCount() > 0 && waitPackedFiles.size() > 0){
				for(int j=0;j<dts.getRowCount();j++){
					String supplierCode = (String)dts.get(j, 0);
					String targetZipFileName = Config.getContextRealPath() + Alias + dt.get(0, "Path")+
							supplierCode+".zip";
					try {
						//把文件打包成zip包
						FileUtil.zipPackage(waitPackedFiles, targetZipFileName);
						//将zip包上传到静态资源服务器
						OSSUploadFile.uploadFile(targetZipFileName);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}else{
			if(dts.getRowCount() > 0){
				for(int j=0;j<dts.getRowCount();j++){
					String supplierCode = (String)dts.get(j, 0);
					try{
						OSSUploadFile.deleteObject("/upload/Attach/lpsqs/"+alias+
								"/"+supplierCode+".zip");
					}catch(Exception e){
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}


}