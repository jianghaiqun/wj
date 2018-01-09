package com.wangjin.underwriting;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.DataTable;

/**
 * 线下核保图片信息管理
 * @author guozc
 * @date 2017-06-14
 */
public class UnderwritingOfflineImages extends Page {

	public static void dg1DataList(DataListAction dga) {
		
		StringBuffer listSql = new StringBuffer();
		listSql.append("select a.id,a.thum_url1 from underwriting_offline_images a where a.info_id = ? order by a.id desc ");
		QueryBuilder qb = new QueryBuilder(listSql.toString(), dga.getParam("infoID"));
		dga.setTotal(qb);
		DataTable dt = qb.executeDataTable();
		if (dt != null) {
			int rowCount = dt.getRowCount();
			int i = 0;
			String thum_url1 = "";
			for (; i < rowCount; i++) {
				thum_url1 = dt.getString(i, 1);
				thum_url1 = thum_url1.replace(Config.getValue("newPolicyPath"), Config.getFrontServerContextPath());
				dt.set(i, "thum_url1", thum_url1);
			}
		}
		dga.bindData(dt);
	}

	public static Mapx<String, Object> initDialog(Mapx<String, Object> params) {
		params.put("allowType", "jpg,gif,jpeg,png,bmp,tif");
		
		//允许上传附件大小设置
		String fileSize = "5242880"; //默认5M;
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
	
	public static Mapx<String, String> initViewDialog(Mapx<String, String> params) {
		String imageID = params.getString("ID");
		String infoID = params.getString("infoID");
		String thum_url2 = new QueryBuilder("select thum_url2 from underwriting_offline_images where id=?", imageID).executeString();
		thum_url2 = thum_url2.replace(Config.getValue("newPolicyPath"), Config.getFrontServerContextPath());
		params.put("imageID", imageID);
		params.put("backID", new QueryBuilder("select id from underwriting_offline_images where info_id=? and id>? order by id asc limit 1", infoID, imageID).executeString());
		params.put("nextID", new QueryBuilder("select id from underwriting_offline_images where info_id=? and id<? order by id desc limit 1", infoID, imageID).executeString());
		String XunHuan = "<tr id='img1' name='Image' style='display:none' align='center'><td align='center' valign='middle' height='500'><img src='" + thum_url2 + "' ></td></tr>";
		params.put("XunHuan", XunHuan);
		return params;
	}
	
	public void del() {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		
		StringBuffer listSql = new StringBuffer();
		listSql.append("select a.image_url,a.thum_url1,a.thum_url2");
		listSql.append(" from underwriting_offline_images a where a.id in (" + IDs + ")");
		DataTable dt = new QueryBuilder(listSql.toString()).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			// 图片地址
			String imageUrl = "";
			// 缩略图1（0.1比例压缩）
			String thumUrl1 = "";
			// 缩略图2（0.6比例压缩）
			String thumUrl2 = "";
			// 删除图片
			for (int i = 0; i < count; i++) {
				imageUrl = dt.getString(i, 0);
				thumUrl1 = dt.getString(i, 1);
				thumUrl2 = dt.getString(i, 2);
				if (StringUtil.isNotEmpty(imageUrl)) {
					FileUtil.delete(imageUrl);
				}
				if (StringUtil.isNotEmpty(thumUrl1)) {
					FileUtil.delete(thumUrl1);
				}
				if (StringUtil.isNotEmpty(thumUrl2)) {
					FileUtil.delete(thumUrl2);
				}
			}
			int resultCount = new QueryBuilder("delete from underwriting_offline_images where id in (" + IDs + ")").executeNoQuery();
			if (resultCount < 1) {
				Response.setStatus(0);
				Response.setMessage("删除图片数据失败!");
			} else {
				Response.setStatus(1);
				Response.setMessage("删除成功!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("后台未查找到要删除的图片信息!");
		}
	}
}