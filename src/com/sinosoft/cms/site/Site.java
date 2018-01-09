package com.sinosoft.cms.site;

import com.sinosoft.cms.dataservice.FullText;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteExporter;
import com.sinosoft.cms.pub.SiteImporter;
import com.sinosoft.cms.pub.SiteTableRela;
import com.sinosoft.cms.pub.SiteTableRela.TableRela;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.resource.ConfigImageLib;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.BlockingTransaction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.license.LicenseInfo;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.RolePriv;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCCatalogConfigSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCPageBlockSchema;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZCSiteSet;
import com.sinosoft.schema.ZDPrivilegeSchema;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 站点管理
 * 
 */
public class Site extends Page {

	public static Mapx init(Mapx params) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(Application.getCurrentSiteID());
		site.fill();

		return site.toMapx();
	}
	public static Mapx initDialog(Mapx params) {
		Object o1 = params.get("ID");
		if (o1 != null) {
			long ID = Long.parseLong(params.get("ID").toString());
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(ID);
			site.fill();

			params.putAll(site.toMapx());

			String indexFlag = site.getAutoIndexFlag();
			if (StringUtil.isEmpty(indexFlag)) {
				indexFlag = "Y";
			}
			params.put("radioAutoIndexFlag", HtmlUtil.codeToRadios("AutoIndexFlag", "YesOrNo", indexFlag));

			String statFlag = site.getAutoStatFlag();
			if (StringUtil.isEmpty(statFlag)) {
				statFlag = "Y";
			}
			params.put("radioAutoStatFlag", HtmlUtil.codeToRadios("AutoStatFlag", "YesOrNo", statFlag));

			params
					.put("radioBBSEnableFlag", HtmlUtil.codeToRadios("BBSEnableFlag", "YesOrNo", site
							.getBBSEnableFlag()));
			params.put("radioShopEnableFlag", HtmlUtil.codeToRadios("ShopEnableFlag", "YesOrNo", site
					.getShopEnableFlag()));

			String auditFlag = site.getCommentAuditFlag();
			params.put("radioCommentAuditFlag", HtmlUtil.codeToRadios("CommentAuditFlag", "YesOrNo", auditFlag));
			params.put("radioAllowContribute", HtmlUtil.codeToRadios("AllowContribute", "YesOrNo", site
					.getAllowContribute()));
			return params;
		} else {
			params.put("Prop1", "shtml");//扩展名
			params.put("URL", "http://");
			params.put("WapURL", "http://");
			params.put("Prop6", "http://");
			params.put("Prop5", "http://");
			params.put("Prop4", "http://");
			params.put("Prop3", "http://");
			params.put("Prop2", "http://");
			params.put("radioAutoIndexFlag", HtmlUtil.codeToRadios("AutoIndexFlag", "YesOrNo", "Y"));
			params.put("radioAutoStatFlag", HtmlUtil.codeToRadios("AutoStatFlag", "YesOrNo", "Y"));
			params.put("radioCommentAuditFlag", HtmlUtil.codeToRadios("CommentAuditFlag", "YesOrNo", "Y"));
			params.put("radioAllowContribute", HtmlUtil.codeToRadios("AllowContribute", "YesOrNo", "N"));
			return params;
		}
	}

	public void saveTemplate() {
		Transaction trans = new Transaction();
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(Long.parseLong($V("ID")));
		site.fill();
		site.setValue(Request);
		site.setModifyUser(User.getUserName());
		site.setModifyTime(new Date());

		trans.add(site, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("保存数据发生错误!");
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from ZCSite order by orderflag");
		dga.bindData(qb);
	}

	/**
	 * 界面添加时使用本方法
	 */
	public void add() {
		Transaction trans = new Transaction();
		long id = NoUtil.getMaxID("SiteID");
		if (new QueryBuilder("select count(*) from zcsite where Alias = ?", $V("Alias")).executeInt() > 0) {
			Response.setLogInfo(0, "新建站点失败,重复的目录名");
			return;
		}
		if (new QueryBuilder("select count(*) from zcsite where WapAlias = ?", $V("WapAlias")).executeInt() > 0) {
			Response.setLogInfo(0, "新建站点失败,重复的Wap站目录名");
			return;
		}
		if (!add(trans, id) || !trans.commit()) {
			Response.setLogInfo(0, "新建站点失败");
			UserLog.log(UserLog.SITE, UserLog.SITE_ADDSITE, "增加站点失败！", Request.getClientIP());
		} else {
			//TODO:wap站目录设置
			updatePrivAndFile($V("Alias"));

			ZCSiteSchema site = SiteUtil.getSchema(id + "");
			String path = SiteUtil.getAbsolutePath(site.getID());
			if (StringUtil.isNotEmpty(site.getHeaderTemplate())) {
				addDefaultPageBlock(site.getID() + "", path, site.getHeaderTemplate(), "动态应用头部引用", "include");
			}
			if (StringUtil.isNotEmpty(site.getTopTemplate())) {
				addDefaultPageBlock(site.getID() + "", path, site.getTopTemplate(), "动态应用顶部", "top");
			}
			if (StringUtil.isNotEmpty(site.getBottomTemplate())) {
				addDefaultPageBlock(site.getID() + "", path, site.getBottomTemplate(), "动态应用底部", "bottom");
			}

			// 更新站点缓存
			SiteUtil.update(id);
			Response.setLogInfo(1, "新建站点成功");
			UserLog.log(UserLog.SITE, UserLog.SITE_ADDSITE, "增加站点:" + $V("Name") + ",成功！", Request.getClientIP());
		}
	}

	public boolean addDefaultPageBlock(String SiteID, String AbsolutePath, String template, String PageBlockName,
			String PageBlockCode) {
		File file = new File(AbsolutePath + template);
		if (!file.exists()) {
			file.mkdirs();
		}
		if (new QueryBuilder("select count(*) from ZCPageBlock where SiteID = " + SiteID + " and Name=? and Code =?",
				PageBlockName, PageBlockCode).executeInt() > 0) {
			return true;
		} else {
			ZCPageBlockSchema block = new ZCPageBlockSchema();
			block.setID(NoUtil.getMaxID("PageBlockID"));
			block.setSiteID(SiteID);
			block.setName(PageBlockName);
			block.setCode(PageBlockCode);
			block.setFileName("/include/" + PageBlockCode + ".html");
			block.setTemplate(template);
			block.setType(1);
			block.setAddTime(new Date());
			block.setAddUser(User.getUserName());
			return block.insert();
		}
	}

	/**
	 * 站点导入时要使用本方法
	 */
	public boolean add(Transaction trans, long siteID) {
		if (LicenseInfo.getName().equals("TrailUser")
				&& new QueryBuilder("select count(*) from ZCSite").executeInt() >= 1) {
			Response.setError("站点数超出限制，请联系全维智码软件更换License！");
			return false;
		}
		if (new QueryBuilder("select count(*) from zcsite where Alias = ?", $V("Alias")).executeInt() > 0) {
			Response.setLogInfo(0, "新建站点失败,重复的目录名");
			Response.setError("新建站点失败,已存在的目录名");
			return false;
		}
		if (new QueryBuilder("select count(*) from zcsite where WapAlias = ?", $V("WapAlias")).executeInt() > 0) {
			Response.setLogInfo(0, "新建站点失败,重复的wap站目录名");
			Response.setError("新建站点失败,已存在的wap站目录名");
			return false;
		}
		ZCSiteSchema site = new ZCSiteSchema();
		site.setValue(Request);
		site.setID(siteID);
		site.setHitCount(0);
		site.setChannelCount(0);
		site.setSpecialCount(0);
		site.setMagzineCount(0);
		site.setArticleCount(0);
		site.setImageLibCount(1);
		site.setVideoLibCount(1);
		site.setAudioLibCount(1);
		site.setAttachmentLibCount(1);
		site.setOrderFlag(OrderUtil.getDefaultOrder());
		site.setBranchInnerCode(User.getBranchInnerCode());
		site.setAddTime(new Date());
		site.setAddUser(User.getUserName());
		site.setConfigXML(ConfigImageLib.imageLibConfigDefault);
		trans.add(site, Transaction.INSERT);

		// 新建站点默认栏目
		addDefaultResourceLib(site.getID(), trans);
		// 设置站点默认权限
		addDefaultPriv(site.getID(), trans);
		// 站点发布设置初始化
		initSiteConfig(site.getID(), trans);
		return true;
	}

	/**
	 * 更新内存中的权限信息，建立站点下的必要目录，复制必要的文件
	 */
	public static void updatePrivAndFile(String alias) {
		RolePriv.updateAllPriv("admin");

		// 复制一些必要的图片
		String oldPath = Config.getContextRealPath() + "Images";
		String path = (Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + alias + "/upload/Image")
				.replaceAll("//", "/");
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileUtil.copy(oldPath + "/nocover.jpg", path + "/nocover.jpg");
		FileUtil.copy(oldPath + "/nophoto.jpg", path + "/nophoto.jpg");
		FileUtil.copy(oldPath + "/nopicture.jpg", path + "/nopicture.jpg");
		FileUtil.copy(oldPath + "/WaterMarkImage1.png", path + "/WaterMarkImage1.png");
		FileUtil.copy(oldPath + "/WaterMarkImage.png", path + "/WaterMarkImage.png");

		// 建立模板目录
		String templatePath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/" + alias
				+ "/template";
		dir = new File(templatePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 建立图片目录
		String imagePath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/" + alias
				+ "/images";
		dir = new File(imagePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 图片播放器和视屏播放
		FileUtil.copy(Config.getContextRealPath() + "/Tools/ImagePlayer.swf", imagePath + "/ImagePlayer.swf");
		FileUtil.copy(Config.getContextRealPath() + "/Tools/player.swf", imagePath + "/player.swf");
		FileUtil.copy(Config.getContextRealPath() + "/Tools/Swfobject.js", imagePath + "/Swfobject.js");

		FileUtil.copy(Config.getContextRealPath() + "/Tools/vote.js", imagePath + "/vote.js");
		FileUtil.copy(Config.getContextRealPath() + "/Tools/vote.css", imagePath + "/vote.css");

	}

	public static void addDefaultCatalogLib(long siteID, Transaction trans, String catalogType, String[] catalogNames) {
		for (int i = 0; i < catalogNames.length; i++) {
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(NoUtil.getMaxID("CatalogID"));
			catalog.setSiteID(siteID);
			catalog.setParentID(0);
			catalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
			catalog.setTreeLevel(1);
			catalog.setName(catalogNames[i]);
			catalog.setURL("");
			catalog.setAlias(StringUtil.getChineseFirstAlpha(catalogNames[i]));
			catalog.setType(catalogType);
			catalog.setListTemplate("");
			catalog.setListNameRule("");
			catalog.setDetailTemplate("");
			catalog.setDetailNameRule("");
			catalog.setChildCount(0);
			catalog.setIsLeaf(1);
			catalog.setTotal(0);
			catalog.setOrderFlag(Catalog.getCatalogOrderFlag(0, catalog.getType()));
			catalog.setLogo("");
			catalog.setListPageSize(10);
			catalog.setPublishFlag("Y");
			catalog.setHitCount(0);
			catalog.setMeta_Keywords("");
			catalog.setMeta_Description("");
			catalog.setOrderColumn("");
			catalog.setAddUser(User.getUserName());
			catalog.setAddTime(new Date());
			trans.add(catalog, Transaction.INSERT);
		}
	}

	public static void addDefaultResourceLib(long siteID, Transaction trans) {
		// 给媒体库建默认专辑
		ZCCatalogSchema imageCatalog = new ZCCatalogSchema();
		imageCatalog.setID(NoUtil.getMaxID("CatalogID"));
		imageCatalog.setSiteID(siteID);
		imageCatalog.setParentID(0);
		imageCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		imageCatalog.setTreeLevel(1);
		imageCatalog.setName("默认图片");
		imageCatalog.setURL("");
		imageCatalog.setAlias(StringUtil.getChineseFirstAlpha(imageCatalog.getName()));
		imageCatalog.setType(Catalog.IMAGELIB);
		imageCatalog.setListTemplate("");
		imageCatalog.setListNameRule("");
		imageCatalog.setDetailTemplate("");
		imageCatalog.setDetailNameRule("");
		imageCatalog.setChildCount(0);
		imageCatalog.setIsLeaf(1);
		imageCatalog.setTotal(0);
		imageCatalog.setOrderFlag(Catalog.getCatalogOrderFlag(0, imageCatalog.getType()));
		imageCatalog.setLogo("");
		imageCatalog.setListPageSize(10);
		imageCatalog.setListPage(-1);
		imageCatalog.setPublishFlag("Y");
		imageCatalog.setHitCount(0);
		imageCatalog.setMeta_Keywords("");
		imageCatalog.setMeta_Description("");
		imageCatalog.setOrderColumn("");
		imageCatalog.setAddUser(User.getUserName());
		imageCatalog.setAddTime(new Date());
		trans.add(imageCatalog, Transaction.INSERT);

		ZCCatalogSchema videoCatalog = (ZCCatalogSchema) imageCatalog.clone();
		videoCatalog.setID(NoUtil.getMaxID("CatalogID"));
		videoCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		videoCatalog.setType(Catalog.VIDEOLIB);
		videoCatalog.setName("默认视频");
		videoCatalog.setAlias(StringUtil.getChineseFirstAlpha(videoCatalog.getName()));
		trans.add(videoCatalog, Transaction.INSERT);

		ZCCatalogSchema audioCatalog = (ZCCatalogSchema) imageCatalog.clone();
		audioCatalog.setID(NoUtil.getMaxID("CatalogID"));
		audioCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		audioCatalog.setType(Catalog.AUDIOLIB);
		audioCatalog.setName("默认音频");
		audioCatalog.setAlias(StringUtil.getChineseFirstAlpha(audioCatalog.getName()));
		trans.add(audioCatalog, Transaction.INSERT);

		ZCCatalogSchema attachCatalog = (ZCCatalogSchema) imageCatalog.clone();
		attachCatalog.setID(NoUtil.getMaxID("CatalogID"));
		attachCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		attachCatalog.setType(Catalog.ATTACHMENTLIB);
		attachCatalog.setName("默认附件");
		attachCatalog.setAlias(StringUtil.getChineseFirstAlpha(attachCatalog.getName()));
		trans.add(attachCatalog, Transaction.INSERT);
	}

	public static void addDefaultPriv(long siteID, Transaction trans) {
		// 添加admin站点权限
		Set set = Priv.SITE_MAP.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String code = (String) iter.next();
			ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
			priv.setOwnerType(RolePriv.OWNERTYPE_ROLE);
			priv.setOwner("admin");
			priv.setID(siteID + "");
			priv.setPrivType(Priv.SITE);
			priv.setCode(code);
			priv.setValue("1");
			trans.add(priv, Transaction.INSERT);
		}
		// 添加admin文档权限、
		set = Priv.ARTICLE_MAP.keySet();
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			String code = (String) iter.next();
			ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
			priv.setOwnerType(RolePriv.OWNERTYPE_ROLE);
			priv.setOwner("admin");
			priv.setID(siteID + "");
			priv.setPrivType(Priv.SITE);
			priv.setCode(code);
			priv.setValue("1");
			trans.add(priv, Transaction.INSERT);
		}
	}

	public static void initSiteConfig(long siteID, Transaction trans) {
		ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
		config.setID(NoUtil.getMaxID("CatalogConfigID"));
		config.setAddTime(new Date());
		config.setAddUser(User.getUserName());
		config.setStartTime(new Date());
		config.setArchiveTime("12");
		config.setAttachDownFlag("Y");
		config.setSiteID(siteID);
		config.setCatalogID(0);
		config.setIsUsing("N");
		config.setPlanType("Period");
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 22, 0, 0);
		StringBuffer sb = new StringBuffer();
		int minute = c.get(Calendar.MINUTE);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int day = c.get(Calendar.DAY_OF_MONTH);
		sb.append(minute);
		sb.append(" ");
		sb.append(hour);
		sb.append(" ");
		sb.append(day);
		sb.append("-");
		sb.append(day - 1);
		sb.append("/1");
		sb.append(" * *");
		config.setCronExpression(sb.toString());
		trans.add(config, Transaction.INSERT);
	}

	public void save() {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setValue(Request);
		site.fill();
		String oldAlias = site.getAlias();
		if (new QueryBuilder("select count(*) from zcsite where ID != ? and Alias = ?", site.getID(), $V("Alias"))
				.executeInt() > 0) {
			Response.setLogInfo(0, "保存失败,重复的目录名");
			return;
		}
		if (new QueryBuilder("select count(*) from zcsite where ID != ? and WapAlias = ?", site.getID(), $V("WapAlias"))
				.executeInt() > 0) {
			Response.setLogInfo(0, "保存失败,重复的Wap目录名");
			return;
		}
		site.setValue(Request);
		site.setModifyUser(User.getUserName());
		site.setModifyTime(new Date());

		if (site.update()) {
			String path = SiteUtil.getAbsolutePath(site.getID());
			if (StringUtil.isNotEmpty(site.getHeaderTemplate())) {
				addDefaultPageBlock(site.getID() + "", path, site.getHeaderTemplate(), "动态应用头部引用", "include");
			}
			if (StringUtil.isNotEmpty(site.getTopTemplate())) {
				addDefaultPageBlock(site.getID() + "", path, site.getTopTemplate(), "动态应用顶部", "top");
			}
			if (StringUtil.isNotEmpty(site.getBottomTemplate())) {
				addDefaultPageBlock(site.getID() + "", path, site.getBottomTemplate(), "动态应用底部", "bottom");
			}
			SiteUtil.update(site.getID());
			Response.setLogInfo(1, "保存成功");
			UserLog.log(UserLog.SITE, UserLog.SITE_UPDATESITE, "修改站点:" + site.getName() + "成功！", Request.getClientIP());
			final String indexFlag = site.getAutoIndexFlag();
			final long siteID = site.getID();
			new Thread() {
				public void run() {
					FullText.dealAutoIndex(siteID, "Y".equals(indexFlag));
				}
			}.start();

			// 尝试修改文件夹名字
			String oldPath = Config.getContextRealPath() + Config.getValue("Statical.TargetDir") + "/" + oldAlias + "/";
			String newPath = Config.getContextRealPath() + Config.getValue("Statical.TargetDir") + "/"
					+ site.getAlias() + "/";
			if (!oldAlias.equals(site.getAlias())) {// 别名被修改，需要重命名文件夹
				// 修改所有文章中的别名
				updateSiteAlias(siteID, oldAlias, site.getAlias());
				File f = new File(oldPath);
				if (!f.renameTo(new File(newPath))) {
					Response.setLogInfo(1, "数据保存成功，但根据站点英文名重命名站点文件夹失败，请手工修改站点文件夹!");
				}
			}
		} else {
			UserLog.log(UserLog.SITE, UserLog.SITE_UPDATESITE, "修改站点:" + site.getName() + "失败！", Request.getClientIP());
			Response.setLogInfo(0, "保存失败");
		}
	}

	public static void updateSiteAlias(long siteID, String oldAlias, String newAlias) {
		int total = new QueryBuilder("select count(1) from ZCArticle where SiteID=?", siteID).executeInt();
		int size = 200;
		QueryBuilder qb = new QueryBuilder("select id,content from ZCArticle where SiteID=?", siteID);
		for (int i = 0; i <= total * 1.0 / size; i++) {
			DataTable dt = qb.executePagedDataTable(size, i);
			QueryBuilder qb2 = new QueryBuilder("update ZCArticle set Content=? where ID=?");
			qb2.setBatchMode(true);
			for (int j = 0; j < dt.getRowCount(); j++) {
				String content = dt.getString(j, "Content");
				content = StringUtil.replaceEx(content, "/" + oldAlias + "/", "/" + newAlias + "/");
				qb2.add(content);
				qb2.add(dt.getString(j, "ID"));
				qb2.addBatch();
			}
			qb2.executeNoQuery();
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}

		ZCSiteSchema site = new ZCSiteSchema();
		ZCSiteSet set = site.query(new QueryBuilder("where id in (" + ids + ")"));
		StringBuffer siteLog = new StringBuffer("删除站点:");
		for (int i = 0; i < set.size(); i++) {
			site = set.get(i);
			siteLog.append(site.getName() + ",");
			// 如果有备份标记，则先备份数据和文件
			if ("Y".equals($V("BackupFlag"))) {
				SiteExporter se = new SiteExporter(site.getID());
				se.exportSite(Config.getContextRealPath() + "WEB-INF/data/backup/" + site.getAlias() + "_"
						+ System.currentTimeMillis() + ".dat");
			}
		}

		BlockingTransaction trans = new BlockingTransaction();
		trans.add(set, Transaction.DELETE_AND_BACKUP);// 删除站点表本身
		delSiteRela(ids, trans);// 删除其他和本站点相关的表

		if (trans.commit()) {
			for (int i = 0; i < set.size(); i++) {
				site = set.get(i);
				SiteUtil.update(site.getID());
				if (ids.indexOf(Application.getCurrentSiteID() + "") >= 0) {// 如果当前站点被删除了
					DataTable dt = new QueryBuilder(
							"select name,id from zcsite order by BranchInnerCode ,orderflag ,id").executeDataTable();
					dt = dt.filter(new Filter() {
						public boolean filter(Object obj) {
							DataRow dr = (DataRow) obj;
							return Priv.getPriv(User.getUserName(), Priv.SITE, dr.getString("ID"), Priv.SITE_BROWSE);
						}
					});
					if (dt.getRowCount() > 0) {
						Application.setCurrentSiteID(dt.getString(0, 1));
					} else {
						Application.setCurrentSiteID("");
					}
				}
				// 删除文件
				String sitePath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/"
						+ site.getAlias();
				FileUtil.delete(sitePath);
			}
			UserLog.log(UserLog.SITE, UserLog.SITE_DELSITE, siteLog + "成功", Request.getClientIP());
			Response.setLogInfo(1, "删除成功");
		} else {
			UserLog.log(UserLog.SITE, UserLog.SITE_DELSITE, siteLog + "失败", Request.getClientIP());
			Response.setLogInfo(0, "删除失败");
		}
	}

	/**
	 * 删除带有SiteID字段的表中的数据,以及关联了这些表的表中的数据
	 * 
	 * @param siteIDs
	 * @param trans
	 */
	public static void delSiteRela(String siteIDs, BlockingTransaction trans) {
		if (!StringUtil.checkID(siteIDs)) {
			return;
		}
		String[] tables = SiteTableRela.getSiteIDTables();
		for (int i = 0; i < tables.length; i++) {
			deleteTableData(tables[i], siteIDs, trans);
		}
		TableRela[] trs = SiteTableRela.getRelas();
		for (int i = 0; i < trs.length; i++) {
			TableRela tr = trs[i];
			if (tr.isExportData) {
				deleteRelaTableData(tr.TableCode, tr.KeyField, tr.RelaTable, tr.RelaField, siteIDs, trans);
			}
		}
	}

	/**
	 * 删除具有SiteID字段的表中的数据
	 */
	public static void deleteTableData(String tableName, String siteIDs, BlockingTransaction trans) {
		String backupNO = String.valueOf(System.currentTimeMillis()).substring(1, 11);
		String backupOperator = User.getUserName();
		String backupTime = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String backup = "insert into b" + tableName + " select " + tableName + ".*,'" + backupNO + "','"
				+ backupOperator + "','" + backupTime + "',null from " + tableName + " where SiteID in(" + siteIDs
				+ ")";
		String delete = "delete from " + tableName + " where SiteID in (" + siteIDs + ")";
		try {
			trans.addWithException(new QueryBuilder(backup));
			trans.addWithException(new QueryBuilder(delete));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);// 有可能表被手工删除了
		}
	}

	/**
	 * 删除关联了 具有SiteID字段的表中的主键ID或其他字段的 表中的数据
	 */
	public static void deleteRelaTableData(String tableName, String foreinKey, String relaTableName, String relaField,
			String siteIDs, BlockingTransaction trans) {
		String wherePart = " where exists(select '' from " + relaTableName + " where " + relaField + "=" + tableName
				+ "." + foreinKey + " and SiteID in (" + siteIDs + "))";
		if (Config.isSybase() && foreinKey.equalsIgnoreCase("RelaID")
				&& (tableName.equalsIgnoreCase("ZDColumnRela") || tableName.equalsIgnoreCase("ZDColumnValue"))) {
			wherePart = " where exists(select '' from " + relaTableName + " where " + relaField + "=convert(bigint,"
					+ tableName + "." + foreinKey + ") and SiteID in (" + siteIDs + "))";
		}
		String backupNO = String.valueOf(System.currentTimeMillis()).substring(1, 11);
		String backupOperator = User.getUserName();
		String backupTime = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String backup = "insert into b" + tableName + " select " + tableName + ".*,'" + backupNO + "','"
				+ backupOperator + "','" + backupTime + "',null from " + tableName + wherePart;
		String delete = "delete from " + tableName + wherePart;
		try {
			trans.addWithException(new QueryBuilder(backup));
			trans.addWithException(new QueryBuilder(delete));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);// 有可能表被手工删除了
		}
	}

	/**
	 * 上传导出文件
	 */
	public static void uploadSite(HttpServletRequest request, HttpServletResponse response) {
		try {
			DiskFileItemFactory fileFactory = new DiskFileItemFactory();
			ServletFileUpload fu = new ServletFileUpload(fileFactory);
			List fileItems = fu.parseRequest(request);
			fu.setHeaderEncoding("UTF-8");
			Iterator iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					String OldFileName = item.getName();
					logger.info("Upload Site FileName:{}", OldFileName);
					long size = item.getSize();
					if ((OldFileName == null || OldFileName.equals("")) && size == 0) {
						continue;
					}
					OldFileName = OldFileName.substring(OldFileName.lastIndexOf("\\") + 1);
					String ext = OldFileName.substring(OldFileName.lastIndexOf("."));
					if (!ext.toLowerCase().equals(".dat")) {
						response.sendRedirect("SiteImportStep1.jsp?Error=1");
						return;
					}
					String FileName = "SiteUpload_" + DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".dat";
					String Path = Config.getContextRealPath() + "WEB-INF/data/backup/";
					File dir = new File(Path);
					if(!dir.exists()){
						dir.mkdirs();
					}
					
					item.write(new File(Path + FileName));
					try{
						Mapx map = SiteImporter.getSiteInfo(Path + FileName);
						if(null==map||map.isEmpty()){
							response.sendRedirect("SiteImportStep1.jsp?Error=2");
							return;
						}
					}catch(Exception e){
						response.sendRedirect("SiteImportStep1.jsp?Error=2");
						return;
					}
					
					response.sendRedirect("SiteImportStep2.jsp?FileName=" + FileName);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 导入站点第二进选择是覆盖某站点还是新建站点时展现的DataGrid
	 */
	public void importStep2DataBind(DataGridAction dga) {
		String sql = "select * from ZCSite order by orderflag ";
		dga.setTotal(new QueryBuilder("select * from ZCSite"));
		DataTable dt = new QueryBuilder(sql).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("Type");// 导入方式
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "Type", "<font class='red'>替换站点</font>");
		}
		if (dga.getPageIndex() == 0) {
			dt.insertRow((Object[]) null, 0);
			String Path = Config.getContextRealPath() + "WEB-INF/data/backup/";
			Mapx map = new Mapx();
			try {
				map = SiteImporter.getSiteInfo(Path + $V("FileName"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			dt.set(0, "ID", "0");
			dt.set(0, "Type", "<font class='green'>新建站点</font>");
			dt.set(0, "Name", "<input class='inputText' style='width:150px' id='Name' value='" + map.getString("Name")
					+ "'>");
			dt.set(0, "Alias", "<input class='inputText' style='width:100px' id='Alias' value='"
					+ map.getString("Alias") + "'>");
			dt.set(0, "URL", "<input class='inputText' style='width:250px' id='URL' value='" + map.getString("URL")
					+ "'>");
		}
		dga.bindData(dt);
	}

	/**
	 * 导入站点时检查新建站点是否与现有站点别名/站点名称重复，如果不重复，则开始导入
	 */
	public void checkImportSite() {
		String ID = $V("ID");
		String name = $V("Name").trim();
		String alias = $V("Alias").trim();
		QueryBuilder qb = new QueryBuilder("select count(1) from ZCSite where Alias=? and ID<>?", alias, Long.parseLong(ID));
		if (qb.executeInt() != 0) {
			Response.setError("别名 <font class='red'>" + alias + "</font> 已经被占用，请修改!");
			return;
		}
		qb = new QueryBuilder("select count(1) from ZCSite where Name=? and ID<>?", name, Long.parseLong(ID));
		if (qb.executeInt() != 0) {
			Response.setError("站点名称 <font class='red'>" + name + "</font> 已经被占用，请修改称!");
			return;
		}
		LongTimeTask ltt = LongTimeTask.getInstanceByType("SiteImport");
		if (ltt != null) {
			Response.setError("目前有站点导入任务正在运行中，请等待！");
			return;
		}
		final String Path = Config.getContextRealPath() + "WEB-INF/data/backup/" + $V("FileName");
		final Mapx map = Request;
		ltt = new LongTimeTask() {
			public void execute() {
				SiteImporter si = new SiteImporter(Path, this);
				si.setSiteInfo(map);
				si.importSite();
				setPercent(100);
			}
		};
		ltt.setType("SiteImport");
		ltt.setUser(User.getCurrent());
		ltt.start();
		$S("TaskID", "" + ltt.getTaskID());

	}

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		if (OrderUtil.updateOrder("ZCSite", type, target, orders, "1 = 1")) {
			Response.setMessage("排序成功");
		} else {
			Response.setError("排序失败");
		}
	}
}
