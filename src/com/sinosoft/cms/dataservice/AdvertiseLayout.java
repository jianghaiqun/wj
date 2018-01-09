package com.sinosoft.cms.dataservice;

import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.controls.TreeItem;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCAdPositionSchema;
import com.sinosoft.schema.ZCAdPositionSet;
import com.sinosoft.schema.ZCAdvertisementSchema;
import com.sinosoft.schema.ZCAdvertisementSet;

import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
public class AdvertiseLayout extends Page {

	@SuppressWarnings("rawtypes")
	public static Mapx PosTypes = new Mapx();
	static {
		PosTypes.put("banner", "矩形横幅");
		PosTypes.put("fixure", "固定位置");
		PosTypes.put("float", "漂浮移动");
		PosTypes.put("couplet", "对联广告");
		PosTypes.put("imagechange", "图片轮换广告");
		PosTypes.put("imagelist", "图片列表广告");
		PosTypes.put("text", "文字广告");
		PosTypes.put("code", "代码调用");
		PosTypes.put("wap_banner", "WAP-矩形横幅");
	}

	public static void dg1DataBind(DataGridAction dga) {
		String RelaCatalogID = dga.getParam("CatalogID");
		if (StringUtil.isEmpty(RelaCatalogID) || RelaCatalogID == null || RelaCatalogID.equalsIgnoreCase("null")) {
			RelaCatalogID = "0";
		}
		String SearchContent = (String) dga.getParam("SearchContent");
		QueryBuilder qb = new QueryBuilder(
				"select a.id id,a.SiteID SiteID,a.PositionName PositionName,a.PositionType PositionType,a.Description Description,a.RelaCatalogID RelaCatalogID,"
						+ "'' as AdType,a.JSName,a.PositionWidth PositionSizeWidth,"
						+ "a.PositionHeight PositionSizeHeight,(SELECT Name from ZCCatalog where ZCCatalog.ID = a.RelaCatalogID) as CatalogName from zcadposition a ");
		qb.append(" where SiteID=?", Application.getCurrentSiteID());
		if (StringUtil.isNotEmpty(SearchContent)) {
			qb.append(" and PositionName like ?", "%" + SearchContent.trim() + "%");
		}
		if (!RelaCatalogID.equals("0")) {
			qb.append(" and RelaCatalogID=?  order by a.id desc", Long.parseLong(RelaCatalogID));
		} else {
			qb.append("  order by a.RelaCatalogID asc");
		}
		DataTable dt = qb.executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				if (Priv.getPriv(User.getUserName(), Priv.SITE, Application.getCurrentSiteID() + "", Priv.SITE_MANAGE)) {
					return true;
				}
				DataRow dr = (DataRow) obj;
				String RelaCatalogID = dr.getString("RelaCatalogID");
				if ("0".equals(RelaCatalogID)) {
					return Priv.getPriv(User.getUserName(), Priv.SITE, Application.getCurrentSiteID() + "",
							Priv.ARTICLE_MANAGE);
				} else {
					return Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(RelaCatalogID),
							Priv.ARTICLE_MODIFY);
				}
			}
		});
		DataTable newdt = new DataTable(dt.getDataColumns(), null);
		for (int i = dga.getPageIndex() * dga.getPageSize(); i < dt.getRowCount()
				&& i < (dga.getPageIndex() + 1) * dga.getPageSize(); i++) {
			newdt.insertRow(dt.getDataRow(i));
		}
		for (int i = 0; i < newdt.getRowCount(); i++) {
			if (StringUtil.isEmpty(newdt.getString(i, "CatalogName"))) {
				newdt.set(i, "CatalogName", "文档库");
			}
			String AdType = new QueryBuilder("select AdType from zcadvertisement  where positionid=? and isopen='Y'",
					dt.getLong(i, "ID")).executeOneValue()
					+ "";
			if (AdType.equalsIgnoreCase("null")) {
				AdType = "";
			}
			newdt.set(i, "ADType", AdType);
		}
		newdt.decodeColumn("ADType", Advertise.ADTypes);
		newdt.decodeColumn("PositionType", PosTypes);
		dga.setTotal(dt.getRowCount());
		dga.bindData(newdt);
	}

	public static void treeDataBind(TreeAction ta) {
		long siteID = Application.getCurrentSiteID();
		Object typeObj = ta.getParams().get("CatalogType");
		int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : Catalog.CATALOG;
		String parentTreeLevel = ta.getParams().getString("ParentLevel");
		String parentID = ta.getParams().getString("ParentID");
		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? and exists (select 1 from ZCAdposition where RelaCatalogID=ZCCatalog.ID) order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(parentTreeLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel-1 <=? and exists (select 1 from ZCAdposition where RelaCatalogID=ZCCatalog.ID) order by orderflag,innercode");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
		}

		String siteName = "文档库";
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("InnerCode"), Priv.ARTICLE_BROWSE);
			}
		});
		ta.setRootText(siteName);
		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
		}
	}

	public static Mapx DialogInit(Mapx params) {
		String id = (String) params.get("ID");
		// 修改
		if (StringUtil.isNotEmpty(id)) {
			ZCAdPositionSchema position = new ZCAdPositionSchema();
			position.setID(id);
			position.fill();
			params.putAll(position.toMapx());
			if (position.getPositionType().equals("text")) {
				params.put("PositionWidth", "0");
				params.put("PositionHeight", "0");
			}
		} else {
			params.put("Align", "Y");
			params.put("Scroll", "Y");
		}
		return params;
	}

	public void add() {
		ZCAdPositionSchema position = new ZCAdPositionSchema();
		String id = $V("ID");
		String PositionName = $V("PositionName");
		// 修改
		if (StringUtil.isNotEmpty(id)) {
			position.setID(id);
			position.fill();
			if (!PositionName.equals(position.getPositionName())) {
				int NameCount = new QueryBuilder("select count(*) from zcadposition where PositionName = ? and SiteID = ?",PositionName,Application.getCurrentSiteID()).executeInt();
				if (NameCount > 0) {
					Response.setLogInfo(0, "已经有同名的版位，请您重新填写版位名");
					return;
				}
			}
			position.setCode(id);
			position.setModifyUser(User.getUserName());
			position.setModifyTime(new Date());
			position.setJsName(createJS("modify", position)); // 修改js
		} else { // 新增
			int NameCount = new QueryBuilder("select count(*) from zcadposition where PositionName = ? and SiteID = ? ", PositionName,Application.getCurrentSiteID()).executeInt();
			if (NameCount > 0) {
				Response.setLogInfo(0, "已经有同名的版位，请您重新填写版位名");
				return;
			}
			position.setID(NoUtil.getMaxID("AdPositionID"));
			position.setCode(position.getID() + "");
			position.setAddUser(User.getUserName());
			position.setAddTime(new Date());
			position.setJsName(createJS("add", position)); // 新增js
		}
		position.setSiteID(Application.getCurrentSiteID());
		position.setValue(Request);
		position.setRelaCatalogID($V("RelaCatalogID"));
		if (position.getAlign().equals("Y")) {
			position.setPaddingLeft(0);
			position.setPaddingTop(0);
		}
		if (position.getPositionType().equals("text")) {
			position.setPositionWidth("0");
			position.setPositionHeight("0");
		}

		// 如果修改版位时发现已生成广告，则生成广告jscode
		if (StringUtil.isNotEmpty(id)) {
			ZCAdvertisementSchema adv = new ZCAdvertisementSchema();
			if ($V("hPositionType").equals($V("PositionType"))) {
				ZCAdvertisementSet advset = new ZCAdvertisementSchema().query(new QueryBuilder("where positionid=? and isopen='Y'",Long.parseLong(id)));
				// 如果没有添加广告，则不需要生成js
				if (advset.size() != 0) {
					adv = advset.get(0);
					if (!Advertise.CreateJSCode(adv, position)) {
						Response.setStatus(0);
						Response.setMessage("生成广告js发生错误!");
					}
				}
			} else {
				ZCAdvertisementSet advset = new ZCAdvertisementSchema().query(new QueryBuilder("where positionid=?", Long.parseLong(id)));
				if (advset.size() > 0) {
					Transaction trans = new Transaction();
					trans.add(advset, Transaction.DELETE_AND_BACKUP);
					trans.commit();
				}
			}
		}

		// 修改
		if (StringUtil.isNotEmpty(id)) {
			if (position.update()) {
				Response.setLogInfo(1, "修改成功");
			} else {
				Response.setLogInfo(0, "发生错误");
			}
		} else { // 新增
			if (position.insert()) {
				Response.setLogInfo(1, "新增成功");
			} else {
				Response.setLogInfo(0, "发生错误");
			}
		}
	}

	public void del() throws Exception {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCAdPositionSchema ad = new ZCAdPositionSchema();
		ZCAdvertisementSchema adv = new ZCAdvertisementSchema();
		ZCAdPositionSet set = ad.query(new QueryBuilder("where id in (" + ids + ")"));
		ZCAdvertisementSet adSet = adv.query(new QueryBuilder("where PositionID in (" + ids + ")"));
		trans.add(set, Transaction.DELETE);
		trans.add(adSet, Transaction.DELETE);
		if (trans.commit()) {
			for (int i = 0; i < set.size(); i++) {
				ZCAdPositionSchema position = set.get(i);
				String path = Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
						+ SiteUtil.getAlias(position.getSiteID()) + "/" + position.getJsName();
				OSSUploadFile.deleteObject(path);
			}
			Response.setLogInfo(1, "删除版位成功！");
		} else {
			Response.setLogInfo(0, "操作数据库时发生错误!");
		}
	}

	public void copy() {
		String id = $V("ID");
		ZCAdPositionSchema ad = new ZCAdPositionSchema();
		ad.setID(Long.parseLong(id));
		ad.fill();
		String PositionName = ad.getPositionName();
		PositionName = "复制  " + PositionName;
		ad.setID(NoUtil.getMaxID("AdPositionID"));
		int count = 0;
		String Code = ad.getCode();
		do {
			Code = "CopyOf" + Code;
			count = new QueryBuilder("select count(*) from zcadposition where Code = ?", Code).executeInt();
		} while (count > 0);
		ad.setCode(Code);
		ad.setAddUser(User.getUserName());
		ad.setAddTime(new Date());
		ad.setJsName(createJS("copy", ad));
		ad.setPositionName(PositionName);
		if (ad.insert()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	/**
	 * @param type
	 *            =add 新增，type=modify 修改，type=copy 复制 @return js路径
	 */
	public static String createJS(String type, ZCAdPositionSchema adp) {
		
		String path = Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
		+ Application.getCurrentSiteAlias() + "/js/";
		
		String oldPath = adp.getJsName();
		
		String filename = "";
		// add
		if (type.equalsIgnoreCase("add")) {
			filename = adp.getCode() + ".js";
			path += filename;
			try {
				OSSUploadFile.upload(null, path);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			path = "js/" + filename;
		} else if (type.equalsIgnoreCase("modify")) { // modify
			if ("".equals(oldPath)) {
				filename = adp.getCode() + ".js";
				path += filename;
				try {
					OSSUploadFile.upload(null, path);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				path = "js/" + filename;
			} else {
				path += oldPath;
				try {
					OSSUploadFile.upload(null, path);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				path = oldPath;
			}
		} else if (type.equalsIgnoreCase("copy")) { // copy
			filename = adp.getCode() + ".js";
			path += filename;
			try {
				OSSUploadFile.upload(null, path);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			path = "js/" + filename;
		}
		return path;
	}
}
