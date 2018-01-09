package com.sinosoft.cms.resource;

import java.io.File;
import java.util.Date;

import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCAudioRelaSchema;
import com.sinosoft.schema.ZCAudioRelaSet;
import com.sinosoft.schema.ZCAudioSchema;
import com.sinosoft.schema.ZCAudioSet;
import com.sinosoft.schema.ZCCatalogSchema;


public class Audio extends Page {

	public static Mapx initEditDialog(Mapx params) {
		ZCAudioSchema Audio = new ZCAudioSchema();
		Audio.setID(params.getString("ID"));
		Audio.fill();
		params.putAll(Audio.toMapx());
		params.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, Audio.getCatalogID() + "",
				ColumnUtil.RELA_TYPE_DOCID, Audio.getID() + ""));
		return params;
	}

	public static Mapx initUploadDialog(Mapx params) {
		String CatalogID = params.getString("CatalogID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(CatalogID);
		catalog.fill();
		params.putAll(catalog.toMapx());
		params.put("allowType", Config.getValue("AllowAudioExt"));
		return params;
	}

	public void dialogEdit() {
		String AudioID = $V("ID");
		Transaction trans = new Transaction();
		ZCAudioSchema Audio = new ZCAudioSchema();
		Audio.setID(AudioID);
		Audio.fill();
		Audio.setValue(Request);
		DataTable columnDT = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, Audio.getID());
		if (columnDT.getRowCount() > 0) {
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?",
					ColumnUtil.RELA_TYPE_DOCID, Audio.getID()));
		}
		trans.add(ColumnUtil.getValueFromRequest(Audio.getCatalogID(), Audio.getID(), Request), Transaction.INSERT);
		trans.add(Audio, Transaction.UPDATE);
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
		String InnerCode = new QueryBuilder("select InnerCode from zccatalog where ID = ?", catalogID).executeString();
		String newPath = "Audio/" + catalogPath;
		File dir = new File(Config.getContextRealPath() + Alias + newPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_COPYAUDIO, "复制音频失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		StringBuffer logs = new StringBuffer("复制音频:");
		ZCAudioSet AudioSet = new ZCAudioSchema().query(new QueryBuilder(" where ID in (" + IDs + ")"));
		boolean flag = true;
		for (int i = 0; i < AudioSet.size(); i++) {
			ZCAudioSchema Audio = AudioSet.get(i);
			String oldPath = Alias + Audio.getPath();
			String oldFileName = oldPath + Audio.getFileName();
			Audio.setID(NoUtil.getMaxID("DocID"));
			Audio.setCatalogID(catalogID);
			Audio.setCatalogInnerCode(InnerCode);
			Audio.setPath(newPath);
			Audio.setFileName(Audio.getID() + Audio.getFileName().substring(Audio.getFileName().lastIndexOf(".")));
			Audio.setAddTime(new Date());
			Audio.setAddUser(User.getUserName());
			File directory = new File(Config.getContextRealPath() + Alias + newPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			if (!FileUtil.copy(Config.getContextRealPath() + oldFileName, Config.getContextRealPath() + Alias + newPath
					+ Audio.getFileName())) {
				flag = false;
				break;
			}
			logs.append(Audio.getName() + ",");
		}
		if (flag && AudioSet.insert()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_EDITAUDIO, logs + "成功", Request.getClientIP());
			this.Response.setLogInfo(1, "复制音频成功");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_EDITAUDIO, logs + "失败", Request.getClientIP());
			this.Response.setLogInfo(0, "复制音频失败");
		}
	}

	public void transfer() {
		long catalogID = Long.parseLong($V("CatalogID"));
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEAUDIO, "音频转移失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("update ZCAudio set catalogid=? ,CatalogInnerCode=? where ID in (" + IDs + ")",
				catalogID, CatalogUtil.getInnerCode(catalogID)));

		StringBuffer logs = new StringBuffer("转移音频:");
		DataTable dt = new QueryBuilder("select Name from ZCAudio where id in(" + IDs + ")").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			logs.append(dt.get(i, "Name") + ",");
		}
		if (trans.commit()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEAUDIO, logs + "成功", Request.getClientIP());
			Response.setLogInfo(1, "转移成功");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_MOVEAUDIO, logs + "失败", Request.getClientIP());
			Response.setLogInfo(0, "转移失败");
		}
	}

	public void del() {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELAUDIO, "删除音频失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		StringBuffer logs = new StringBuffer("删除音频:");
		String Alias = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		ZCAudioSet AudioSet = new ZCAudioSchema().query(new QueryBuilder(" where id in (" + IDs + ")"));
		ZCAudioRelaSet AudioRelaSet = new ZCAudioRelaSchema().query(new QueryBuilder(" where id in (" + IDs + ")"));
		Transaction trans = new Transaction();
		for (int i = 0; i < AudioSet.size(); i++) {
			FileUtil.delete(Config.getContextRealPath() + Alias + AudioSet.get(i).getPath()
					+ AudioSet.get(i).getFileName());
			trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid=?",
					ColumnUtil.RELA_TYPE_DOCID, "" + AudioSet.get(i).getID()));
			logs.append(AudioSet.get(i).getName() + ",");
		}
		trans.add(AudioSet, Transaction.DELETE_AND_BACKUP);
		trans.add(AudioRelaSet, Transaction.DELETE_AND_BACKUP);
		trans.add(new QueryBuilder(
				"update zccatalog set total=(select count(*) from zcaudio where catalogID=?) where ID=?", AudioSet.get(
						0).getCatalogID(), AudioSet.get(0).getCatalogID()));
		if (trans.commit()) {
			// 删除发布文件，更新列表
			Publisher p = new Publisher();
			p.deleteFileTask(AudioSet);

			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELAUDIO, logs + "成功", Request.getClientIP());
			this.Response.setLogInfo(1, "删除音频成功");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_DELAUDIO, logs + "成功", Request.getClientIP());
			this.Response.setLogInfo(0, "删除音频失败");
		}
	}

}
