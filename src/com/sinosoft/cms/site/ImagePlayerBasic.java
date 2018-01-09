package com.sinosoft.cms.site;

import java.util.Date;

import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCImagePlayerSchema;
import com.sinosoft.schema.ZCImagePlayerSet;

/**
 * 图片拨放器
 * 
 */
public class ImagePlayerBasic extends Page {

	public final static String IMAGESOURCE_LOCAL = "0";
	public final static String IMAGESOURCE_CATALOG_FIRST = "1";
	public final static String IMAGESOURCE_CATALOG_SELECT = "2";

	public final static Mapx IMAGESOURCE_MAP = new Mapx();

	static {
		IMAGESOURCE_MAP.put(IMAGESOURCE_LOCAL, "本地上传");
		IMAGESOURCE_MAP.put(IMAGESOURCE_CATALOG_FIRST, "所属栏目文章中的图片(自动取第一张)");
		IMAGESOURCE_MAP.put(IMAGESOURCE_CATALOG_SELECT, "所属栏目文章中的图片(编辑手工选择)");
	}

	public static Mapx init(Mapx params) {
		String imagePlayerID = params.getString("ImagePlayerID");
		if (StringUtil.isNotEmpty(imagePlayerID)) {
			long ID = Long.parseLong(imagePlayerID);
			ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
			ImagePlayer.setID(ID);
			ImagePlayer.fill();
			Mapx map = ImagePlayer.toMapx();
			map.put("ImagePlayerID", ImagePlayer.getID() + "");
			map.put("radiosShowText", HtmlUtil.codeToRadios("IsShowText", "YesOrNo", ImagePlayer.getIsShowText()));
			map.put("originalPicture", HtmlUtil.codeToRadios("OriginalPicture", "YesOrNo", ImagePlayer.getOriginalPicture()));
			map.put("radiosShowNofollow", HtmlUtil.codeToRadios("IsShowNofollow", "YesOrNo", ImagePlayer.getIsShowNofollow()));
			map.put("radiosImageSource", HtmlUtil.mapxToRadios("ImageSource", IMAGESOURCE_MAP, ImagePlayer
					.getImageSource()));
			map.put("PlayerShow", HtmlUtil.codeToOptions("PlayerShow" ,StringUtil.isEmpty( ImagePlayer.getRemark1()) ? "01": ImagePlayer.getRemark1()));
			
			return map;
		} else {
			params.put("radiosShowText", HtmlUtil.codeToRadios("IsShowText", "YesOrNo", "Y"));
			params.put("originalPicture", HtmlUtil.codeToRadios("OriginalPicture", "YesOrNo", "N"));
			params.put("radiosShowNofollow", HtmlUtil.codeToRadios("IsShowNofollow", "YesOrNo", "N"));
			params.put("radiosImageSource", HtmlUtil.mapxToRadios("ImageSource", IMAGESOURCE_MAP, IMAGESOURCE_LOCAL));
			params.put("display", "none");
			params.put("PlayerShow", HtmlUtil.codeToOptions("PlayerShow"  ));
		}
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select ID,Name,Code,SiteID,DisplayType,ImageSource,Height,Width,Displaycount from ZCImagePlayer order by ID ");
		dga.bindData(qb);
	}

	public void add() {
		String imagePlayerID = $V("ImagePlayerID");
		ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
		if (StringUtil.isNotEmpty(imagePlayerID)) {
			// 编辑
			ImagePlayer.setID(imagePlayerID);
			ImagePlayer.fill();
			ImagePlayer.setValue(Request);
			ImagePlayer.setModifyTime(new Date());
			ImagePlayer.setModifyUser(User.getUserName());
			ImagePlayer.setDisplayType("1");
			ImagePlayer.setRemark1($V("PlayerShow"));
			if (StringUtil.isNotEmpty($V("RelaCatalogID"))) {
				ImagePlayer.setRelaCatalogInnerCode(CatalogUtil.getInnerCode($V("RelaCatalogID")));
			} else {
				ImagePlayer.setRelaCatalogInnerCode("0");
			}
			if (ImagePlayer.update()) {
				Response.setStatus(1);
				Response.put("ImagePlayerUrl", "ImagePlayerID=" + ImagePlayer.getID() + "&ImageSource="
						+ ImagePlayer.getImageSource() + "&RelaCatalog=" + ImagePlayer.getRelaCatalogInnerCode());
				Response.setMessage("保存成功,您可以去‘预览’查看修改后的效果!");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		} else {
			// 新建
			DataTable checkDT = new QueryBuilder("select * from zcimageplayer where code=? and siteID=?", $V("Code"),
					Application.getCurrentSiteID()).executeDataTable();
			if (checkDT.getRowCount() > 0) {
				Response.setLogInfo(0, "已经存在代码为‘ <b style='color:#F00'>" + $V("Code") + "</b>’ 的图片播放器，请更换播放器代码！");
				return;
			}
			ImagePlayer.setID(NoUtil.getMaxID("ImagePlayerID"));
			ImagePlayer.setValue(Request);
			ImagePlayer.setDisplayType("1");
			ImagePlayer.setSiteID(Application.getCurrentSiteID());
			ImagePlayer.setAddTime(new Date());
			ImagePlayer.setAddUser(User.getUserName());
			ImagePlayer.setRemark1($V("PlayerShow"));
			if (StringUtil.isNotEmpty($V("RelaCatalogID"))) {
				ImagePlayer.setRelaCatalogInnerCode(CatalogUtil.getInnerCode($V("RelaCatalogID")));
			} else {
				ImagePlayer.setRelaCatalogInnerCode("0");
			}

			if (ImagePlayer.insert()) {
				Response.put("ImagePlayerUrl", "ImagePlayerID=" + ImagePlayer.getID() + "&ImageSource="
						+ ImagePlayer.getImageSource() + "&RelaCatalog=" + ImagePlayer.getRelaCatalogInnerCode());
				Response.setStatus(1);
				Response.setMessage("新建成功,您现在可以关联图片了!");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		}

	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
		ZCImagePlayerSet set = ImagePlayer.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			Response.setMessage("删除成功,您可以去‘预览’查看删除后的效果!");
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
}
