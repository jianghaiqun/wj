package com.sinosoft.cms.resource;

import java.io.File;
import java.util.Date;

import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCImageRelaSchema;
import com.sinosoft.schema.ZCImageRelaSet;
import com.sinosoft.schema.ZCImageSchema;
import com.sinosoft.schema.ZCImageSet;

public class Image extends Page {

	public static Mapx pathMap = new Mapx(10000);

	public void setTempPath() {
		String Time = $V("Time");
		String Path = $V("Path");
		pathMap.put(Time, Path);
	}

	public static Mapx initEditDialog(Mapx params) {
		String alias = Config.getValue("StaticResourcePath");
		String ids = params.getString("IDs");
		if (StringUtil.isNotEmpty(ids)) {
			String[] IDs = ids.split(",");
			ZCImageSchema Image = new ZCImageSchema();
			Image.setID(Long.parseLong(IDs[0]));
			Image.fill();
			params.putAll(Image.toMapx());
			params.put("IDCount", IDs.length + "");
			params.put("Alias", alias);
			params.put("IDs", ids);
			params.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, Image.getCatalogID() + "", ColumnUtil.RELA_TYPE_DOCID, Image.getID() + ""));
		}
		return params;
	}

	public static Mapx initViewDialog(Mapx params) {
		String Alias = Config.getValue("StaticResourcePath")+"/";
		Mapx map = ConfigImageLib.getImageLibConfig(Application.getCurrentSiteID());
		ZCImageSchema Image = new ZCImageSchema();
		Image.setID(params.getString("ID"));
		Image.fill();
		params.putAll(Image.toMapx());
		params.put("Alias", Alias);
		params.put("backID", new QueryBuilder("select id from zcimage where CatalogID=? and id>? order by id asc", Image.getCatalogID(), Image.getID()).executeString());
		params.put("nextID", new QueryBuilder("select id from zcimage where CatalogID=? and id<? order by id desc", Image.getCatalogID(), Image.getID()).executeString());
		params.put("imageID", Image.getID() + "");
		String XunHuan1 = "";
		String XunHuan2 = "";
		for (int i = 1; i <= Image.getCount(); i++) {
			String Width = map.getString("Width" + i);
			String Height = map.getString("Height" + i);
			XunHuan1 += "<a href='#;' hidefocus id='" + i + "' name='tab' onClick='onTabClick(this);'>缩略图" + i + "<br>" + "(" + Width + "×" + Height + ")</a>";
			XunHuan2 += "<tr id='img" + i + "' name='Image' style='display:none' align='center'><td align='center' valign='middle' height='500'><img src='" + Alias + Image.getPath() + i + "_"
					+ Image.getFileName() + "?" + Image.getModifyTime() + "' ></td></tr>";
		}
		params.put("XunHuan1", XunHuan1);
		params.put("XunHuan2", XunHuan2);
		return params;
	}

	public void edit() {
		ZCImageSchema image = new ZCImageSchema();
		image.setID(Long.parseLong($V("ID")));
		image.fill();
		image.setValue(Request);
		image.setModifyTime(new Date());
		image.setModifyUser(User.getUserName());
		if (image.update()) {
			Response.setLogInfo(1, "修改成功");
		} else {
			Response.setLogInfo(0, "修改失败");
		}
	}

	public void imagesEdit() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCImageSchema images = new ZCImageSchema();
		ZCImageSet set = images.query(new QueryBuilder("where id in (" + ids + ")"));
		StringBuffer logs = new StringBuffer("编辑图片:");
		for (int i = 0; i < set.size(); i++) {
			images = set.get(i);
			images.setValue(Request);
			DataTable columnDT = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, images.getID());
			if (columnDT.getRowCount() > 0) {
				trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?", ColumnUtil.RELA_TYPE_DOCID, images.getID() + ""));
			}
			trans.add(images, Transaction.UPDATE);
			trans.add(ColumnUtil.getValueFromRequest(images.getCatalogID(), images.getID(), Request), Transaction.INSERT);
			logs.append(images.getName() + ",");
		}
		if (trans.commit()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_EDITIMAGE, logs + "成功", Request.getClientIP());
			Response.setStatus(1);
			Response.setMessage("修改成功！");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_EDITIMAGE, logs + "失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}

	}

	/**
	 * 图片的转移，值得商榷 是否真正移动图片
	 */
	public void transfer() {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEIMAGE, "转移图片失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		long catalogID = Long.parseLong($V("CatalogID"));
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("update ZCImage set catalogid=? ,CatalogInnerCode=? where ID in (" + $V("IDs") + ")", catalogID, CatalogUtil.getInnerCode(catalogID)));
		StringBuffer logs = new StringBuffer("转移图片:");
		DataTable dt = new QueryBuilder("select Name from ZCImage where id in(" + IDs + ")").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			logs.append(dt.get(i, "Name") + ",");
		}
		if (trans.commit()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEIMAGE, logs + "成功", Request.getClientIP());
			Response.setLogInfo(1, "转移成功");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEIMAGE, logs + "失败", Request.getClientIP());
			Response.setLogInfo(0, "转移失败");
		}
	}

	public void copy() {
		String Alias = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		long catalogID = Long.parseLong($V("CatalogID"));
		String catalogPath = CatalogUtil.getPath(catalogID);
		String InnerCode = new QueryBuilder("select InnerCode from zccatalog where ID = ?", catalogID).executeString();
		String newPath = "upload/Image/" + catalogPath;
		String contextRealPath = Config.getContextRealPath() + Alias;
		/*File dir = new File(contextRealPath + Alias + newPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}*/
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYIMAGE, "复制视频失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		ZCImageSet imageSet = new ZCImageSchema().query(new QueryBuilder(" where ID in (" + IDs + ")"));
		boolean flag = true;
		String msg = "";
		StringBuffer logs = new StringBuffer("复制图片:");
		for (int i = 0; i < imageSet.size(); i++) {
			ZCImageSchema image = imageSet.get(i);
			String oldPath = image.getPath();
			String oldFileName = image.getFileName();
			String oldSrcFileName = image.getSrcFileName();
			image.setID(NoUtil.getMaxID("DocID"));
			image.setCatalogID(catalogID);
			image.setCatalogInnerCode(InnerCode);
			image.setPath(newPath);
			image.setFileName(image.getID() + "" + NumberUtil.getRandomInt(10000) + oldFileName.substring(oldFileName.lastIndexOf(".")));
			image.setSrcFileName(image.getID() + "" + NumberUtil.getRandomInt(10000) + oldSrcFileName.substring(oldSrcFileName.lastIndexOf(".")));
			try{
				OSSUploadFile.copyObject(contextRealPath+oldPath + oldSrcFileName, contextRealPath+newPath + image.getSrcFileName());
			}catch(Exception e){
				msg = "复制失败，原因是：'" + image.getName() + "'的原图复制失败";
				flag = false;
				break;
			}
			try{
				OSSUploadFile.copyObject(contextRealPath+oldPath + "s_" + oldFileName, contextRealPath+newPath + "s_" + image.getFileName());
			}catch(Exception e){
				msg = "复制失败，原因是：'" + image.getName() + "'的系统缩略图复制失败";
				flag = false;
				break;
			}
			/*if (!FileUtil.copy(oldPath + oldSrcFileName, newPath + image.getSrcFileName())) {
				msg = "复制失败，原因是：'" + image.getName() + "'的原图复制失败";
				flag = false;
				break;
			}
			if (!FileUtil.copy(contextRealPath + oldPath + "s_" + oldFileName, contextRealPath + Alias + newPath + "s_" + image.getFileName())) {
				msg = "复制失败，原因是：'" + image.getName() + "'的系统缩略图复制失败";
				flag = false;
				break;
			}*/
			int count = (int) image.getCount();
			for (int j = 1; j <= count; j++) {
				/*if (!FileUtil.copy(contextRealPath + oldPath + j + "_" + oldFileName, contextRealPath + Alias + newPath + j + "_" + image.getFileName())) {
					msg = "复制失败，原因是：'" + image.getName() + "'的第" + j + "张缩略图复制失败";
					flag = false;
					break;
				}*/
				try{
					OSSUploadFile.copyObject(contextRealPath+oldPath + j + "_" + oldFileName, contextRealPath+newPath + j + "_" + image.getFileName());
				}catch(Exception e){
					msg = "复制失败，原因是：'" + image.getName() + "'的第" + j + "张缩略图复制失败";
					flag = false;
					break;
				}
			}
			if (!flag) {
				break;
			}

			image.setAddTime(new Date());
			image.setAddUser(User.getUserName());
			logs.append(image.getName() + ",");
		}
		if (flag && imageSet.insert()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYIMAGE, logs + "成功", Request.getClientIP());
			this.Response.setLogInfo(1, "复制图片成功");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYIMAGE, msg, Request.getClientIP());
			this.Response.setLogInfo(0, msg);
		}
	}

	public void del() throws Exception {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String Alias =Config.getContextRealPath()+ Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		StringBuffer logs = new StringBuffer("删除图片:");
		Transaction trans = new Transaction();
		ZCImageSet ImageSet = new ZCImageSchema().query(new QueryBuilder(" where id in (" + IDs + ")"));
		ZCImageRelaSet ImageRelaSet = new ZCImageRelaSchema().query(new QueryBuilder(" where id in (" + IDs + ")"));
		for (int i = 0; i < ImageSet.size(); i++) {
			ZCImageSchema image = ImageSet.get(i);
			OSSUploadFile.deleteObject(Alias+image.getPath() + image.getSrcFileName());
			OSSUploadFile.deleteObject(Alias+image.getPath() + "s_" + image.getFileName());
			int count = (int) image.getCount();
			for (int j = 1; j <= count; j++) {
				OSSUploadFile.deleteObject(Alias+image.getPath() + j + "_" + image.getFileName());
			}
			logs.append(image.getName() + ",");
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid=?", ColumnUtil.RELA_TYPE_DOCID, "" + image.getID() + ""));
		}
		trans.add(ImageSet, Transaction.DELETE_AND_BACKUP);
		trans.add(ImageRelaSet, Transaction.DELETE_AND_BACKUP);
		trans.add(new QueryBuilder("update zccatalog set total=(select count(*) from zcimage where catalogID=?) where ID=?", ImageSet.get(0).getCatalogID(), ImageSet.get(0).getCatalogID()));
		if (trans.commit()) {
			// 删除发布文件，更新列表
			Publisher p = new Publisher();
			p.deleteFileTask(ImageSet);

			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELIMAGE, logs + "成功", Request.getClientIP());
			this.Response.setLogInfo(1, "删除图片成功");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELIMAGE, logs + "失败", Request.getClientIP());
			this.Response.setLogInfo(0, "删除图片失败");
		}
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		DataTable dt = new QueryBuilder("select siteid,path,filename from zcimage where id=?", Long.parseLong(ID)).executeDataTable();
		if (dt.getRowCount() > 0) {
			this.Response.put("ImagePath", Config.getContextPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(dt.getLong(0, "siteid")) + "/" + dt.get(0, "path").toString() + "1_"
					+ dt.get(0, "filename").toString());
		}
	}
}
