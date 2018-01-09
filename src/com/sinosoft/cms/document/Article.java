package com.sinosoft.cms.document;
 
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sinosoft.cms.datachannel.PublishMonitor;
import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.dataservice.Vote;
import com.sinosoft.cms.pub.CMSCache;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.site.BadWord;
import com.sinosoft.cms.site.ImagePlayerRela;
import com.sinosoft.cms.site.Tag;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.controls.TButtonTag;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.DataTableUtil;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.extend.ExtendManager;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.UserList;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCArticleLogSchema;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCAttachmentRelaSchema;
import com.sinosoft.schema.ZCCatalogConfigSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCImageRelaSchema;
import com.sinosoft.schema.ZCImageSchema;
import com.sinosoft.schema.ZCImageSet;
import com.sinosoft.schema.ZCInnerDeploySchema;
import com.sinosoft.schema.ZCInnerDeploySet;
import com.sinosoft.schema.ZCKeywordSchema;
import com.sinosoft.schema.ZCKeywordTypeSchema;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZCTagSchema;
import com.sinosoft.schema.ZCTagSet;
import com.sinosoft.schema.ZCVideoRelaSchema;
import com.sinosoft.schema.ZCVoteItemSchema;
import com.sinosoft.schema.ZCVoteSchema;
import com.sinosoft.schema.ZCVoteSet;
import com.sinosoft.search.SearchResult;
import com.sinosoft.search.index.IndexUtil;
import com.sinosoft.workflow.Context;
import com.sinosoft.workflow.WorkflowAction;
import com.sinosoft.workflow.WorkflowUtil;

/**
 * 文章管理
 * 
 */
public class Article extends Page {

	public static final String TYPE_COMMON = "1"; // 普通新闻

	public static final String TYPE_IMAGE = "2"; // 图片

	public static final String TYPE_VIDEO = "3"; // 视频

	public static final String TYPE_URL = "4"; // 跳转链接

	public static final String TYPE_ATTACH = "5"; // 附件

	public static final String RELA_ATTACH = "ArticleAttach"; // 附件关联

	public static final String RELA_VIDEO = "ArticleVideo"; // 视频关联

	public static final String RELA_IMAGE = "ArticleImage"; // 图片关联

	// 文章状态
	public static final int STATUS_DRAFT = 0; // 初稿

	public static final int STATUS_WORKFLOW = 10; // 流转中

	public static final int STATUS_TOPUBLISH = 20; // 待发布
	
	public static final int STATUS_ONPUBLISH = 25; // 发布中

	public static final int STATUS_PUBLISHED = 30; // 已发布

	public static final int STATUS_OFFLINE = 40; // 已下线

	public static final int STATUS_ARCHIVE = 50; // 已归档

	public static final int STATUS_EDITING = 60; // 重新编辑

	public final static Mapx STATUS_MAP = new Mapx();

	public boolean cashValueFlag = false; // 现金价值标识

	public boolean showBenefitFlag = false; // 利益演示标识

	static {
		STATUS_MAP.put(STATUS_DRAFT + "", "初稿");
		STATUS_MAP.put(STATUS_WORKFLOW + "", "流转中");
		STATUS_MAP.put(STATUS_TOPUBLISH + "", "待发布");
		STATUS_MAP.put(STATUS_ONPUBLISH + "", "发布中");
		STATUS_MAP.put(STATUS_PUBLISHED + "", "已发布");
		STATUS_MAP.put(STATUS_OFFLINE + "", "已下线");
		STATUS_MAP.put(STATUS_ARCHIVE + "", "已归档");
		STATUS_MAP.put(STATUS_EDITING + "", "重新编辑");
	}

	private String siteAlias = SiteUtil.getAlias(Application.getCurrentSiteID());

	// 编辑器页面初始化
	public static Mapx init(Mapx params) {
		Mapx map = new Mapx();
		String catalogID = (String) params.get("CatalogID");
		String issueID = (String) params.get("IssueID");
		String articleID = (String) params.get("ArticleID");
		String title = (String) params.get("Title");
		try {
			if (StringUtil.isNotEmpty(title)) {
				title = URLDecoder.decode(title, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
		}
		if (articleID != null && !"".equals(articleID) && !"null".equals(articleID)) {
			ZCArticleSchema article = new ZCArticleSchema();
			article.setID(Integer.parseInt(articleID));
			ZCArticleSet set = article.query();
			if (set.size() < 1) {
				return null;
			}
			article = set.get(0);
			catalogID = article.getCatalogID() + "";
			map.putAll(article.toMapx());
			if (StringUtil.isNotEmpty(article.getMetaTitle())) {
				map.put("MetaTitle", article.getMetaTitle());

			}

			String content = article.getContent();
			if (content == null) {
				content = "";
			}
			String[] pages = content.split(Constant.PAGE_BREAK, -1);

			map.put("Pages", new Integer(pages.length));
			map.put("Content", pages[0]);
			map.put("Contents", content);

			String pageTitle = article.getPageTitle();
			if (pageTitle == null) {
				pageTitle = "";
			}
			String[] pageTitles = pageTitle.split("\\|", -1);
			map.put("PageTitle", pageTitles[0]);
			map.put("PageTitles", pageTitle);

			// 自定义字段
			map.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID, ColumnUtil.RELA_TYPE_DOCID, article.getID() + ""));

			// 文档属性
			if (article.getAttribute() != null) {
				map.put("Attribute", HtmlUtil.codeToCheckboxes("Attribute", "ArticleAttribute", article.getAttribute().split(",")));
			} else {
				map.put("Attribute", HtmlUtil.codeToCheckboxes("Attribute", "ArticleAttribute"));
			}
			// 险种
			if (article.getRiskType() != null) {
				map.put("RiskType", HtmlUtil.codeToOptions("RiskType", article.getRiskType(), false ));
			} else {
				map.put("RiskType", HtmlUtil.codeToOptions("RiskType","G00" ));
			}
			
			// 保险公司
			if (article.getRiskCompany() != null) {
				map.put("CompanyID", HtmlUtil.codeToOptions("SupplierCode", article.getRiskCompany(), true ));
			} else {
				map.put("CompanyID", HtmlUtil.codeToOptions("SupplierCode"  ));
			}
			
			map.put("SourceSign", HtmlUtil.codeToRadios("SourceSign", "SourceSign", article.getSourceSign(), false, true));
			Date publishDate = article.getPublishDate();
			Date downlineDate = article.getDownlineDate();
			Date archiveDate = article.getArchiveDate();
			if (publishDate != null) {
				map.put("PublishDate", DateUtil.toString(publishDate, DateUtil.Format_Date));
				map.put("PublishTime", DateUtil.toString(publishDate, DateUtil.Format_Time));
			}

			if (downlineDate != null) {
				map.put("DownlineDate", DateUtil.toString(downlineDate, DateUtil.Format_Date));
				map.put("DownlineTime", DateUtil.toString(downlineDate, DateUtil.Format_Time));
			}

			if (archiveDate != null) {
				map.put("ArchiveDate", DateUtil.toString(archiveDate, DateUtil.Format_Date));
				map.put("ArchiveTime", DateUtil.toString(archiveDate, DateUtil.Format_Time));
			}

			// 最后修改时间
			Date lastModify = article.getModifyTime() == null ? article.getAddTime() : article.getModifyTime();
			map.put("LastModify", lastModify);

			map.put("Method", "UPDATE");

			// 工作流
			boolean publishFlag = Article.STATUS_PUBLISHED == article.getStatus() || Article.STATUS_TOPUBLISH == article.getStatus();
			String html = getInitButtons(Long.parseLong(catalogID), article.getWorkFlowID(), publishFlag);
			map.put("buttonDiv", html);

			// 图片播放器
			DataTable imgDt = new QueryBuilder("select ID from ZCImageRela where RelaType = 'ArticleImage' and RelaID = ?", article.getID()).executeDataTable();
			String imageIDs = "";
			for (int i = 0; i < imgDt.getRowCount(); i++) {
				if (i != 0) {
					imageIDs += ",";
				}
				imageIDs += imgDt.getString(i, 0);
			}
			String relaImagePlayerID = article.getProp3();
			String relaImageIDs = "";
			if (StringUtil.isEmpty(relaImagePlayerID)) {
				relaImagePlayerID = "";
			} else if (StringUtil.isNotEmpty(imageIDs)) {
				String sql = "select ID from ZCImageRela where RelaType='ImagePlayerImage' and RelaID=? and ID in (" + imageIDs + ")";
				imgDt = new QueryBuilder(sql, Long.parseLong(relaImagePlayerID)).executeDataTable();// sybase
				// imgDt = new QueryBuilder(sql,
				// relaImagePlayerID).executeDataTable();//除sybase之外的
				for (int i = 0; i < imgDt.getRowCount(); i++) {
					if (i != 0) {
						relaImageIDs += ",";
					}
					relaImageIDs += imgDt.getString(i, 0);
				}
			}
			map.put("ImageIDs", imageIDs);
			map.put("RelaImageIDs", relaImageIDs);
			map.put("RelaImagePlayerID", relaImagePlayerID);

			// 初始化文章引导图
			String imgLogo = Config.getValue("StaticResourcePath")+"/Images/addpicture.jpg";
			String sitePath = Config.getValue("StaticResourcePath");
			String logo = article.getLogo();
			if (StringUtil.isNotEmpty(logo)) {
				logo = sitePath + "/" + logo;
				imgLogo = logo;
			}
			map.put("Logo", logo);
			map.put("ImgLogo", imgLogo);

			// 本站内复制
			if (StringUtil.isNotEmpty(article.getReferTarget())) {
				map.put("ReferTargetCount", StringUtil.count(article.getReferTarget(), ",") + 1);
			} else {
				map.put("ReferTargetCount", 0);
			}
			// 站点群复制
			if (StringUtil.isNotEmpty(article.getClusterTarget())) {
				map.put("ClusterTargetCount", StringUtil.count(article.getClusterTarget(), ",") + 1);
				map.put("ClusterTarget", article.getClusterTarget());
			} else {
				map.put("ClusterTargetCount", 0);
			}

			// 如果文章为复制或引用过来的，显示来源栏目
			long referCatalogID = new QueryBuilder("select catalogID from zcarticle where id=?", article.getReferSourceID()).executeLong();
			if (referCatalogID != 0L) {
				map.put("ReferDisplay", "style='display:'");
				map.put("ReferSourceName", CatalogUtil.getFullName(referCatalogID));
			} else {
				map.put("ReferDisplay", "style='display:none'");
			}

			// Prop1表示文档是否作废 modify by wyli 2010-07-28
			// map.put("Prop1", HtmlUtil.codeToRadios("Prop1", "YesOrNo",
			// article.getProp1()));
			if ("作废".equals(article.getProp1())) {
				map.put("Prop1", HtmlUtil.codeToRadios("Prop1", "YesOrNo", "Y"));
			} else {
				map.put("Prop1", HtmlUtil.codeToRadios("Prop1", "YesOrNo", "N"));
			}
		} else {
			map.put("CatalogID", catalogID);
			articleID = NoUtil.getMaxID("DocID") + "";
			map.put("ID", articleID);
			map.put("Method", "ADD");
			map.put("CatalogID", catalogID);
			map.put("CommentFlag", "1");

			map.put("DownlineDate", "2099-12-31");
			map.put("DownlineTime", "23:59:59");

			String archiveTime = "";
			if (CatalogUtil.getArchiveTime(catalogID) != null) {
				archiveTime = DateUtil.toDateTimeString(CatalogUtil.getArchiveTime(catalogID));
			}

			if (StringUtil.isNotEmpty(archiveTime)) {
				map.put("ArchiveDate", archiveTime.substring(0, 10));
				map.put("ArchiveTime", archiveTime.substring(11));
			}

			map.put("Pages", new Integer(1));
			map.put("ContentPages", "''");
			map.put("Title", title);

			map.put("ReferTarget", "");
			map.put("ReferType", "1");

			// 作者默认置为真实姓名
			map.put("Author", User.getRealName());

			// 工作流
			String html = getInitButtons(Long.parseLong(catalogID), 0, false);
			map.put("buttonDiv", html);

			// 自定义字段
			map.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID));

			// 文档属性
			map.put("Attribute", HtmlUtil.codeToCheckboxes("Attribute", "ArticleAttribute"));

			map.put("RiskType", HtmlUtil.codeToOptions("RiskType" , "G00"));
			map.put("CompanyID", HtmlUtil.codeToOptions("SupplierCode", true ));
			

			map.put("SourceSign", HtmlUtil.codeToRadios("SourceSign", "SourceSign", "A"));

			// 初始化文章引导图
			String imageLogo = "../Images/addpicture.jpg";
			map.put("Logo", "");
			map.put("ImgLogo", imageLogo);

			map.put("ReferTargetCount", 0);
			map.put("ClusterTargetCount", 0);

			map.put("ReferDisplay", "style='display:none'");

			// Prop1表示文档是否作废 modify by wyli 2010-07-28
			map.put("Prop1", HtmlUtil.codeToRadios("Prop1", "YesOrNo", "N"));
		}
		ZCInnerDeploySchema deploy = new ZCInnerDeploySchema();
		deploy.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
		ZCInnerDeploySet set = deploy.query();
		if (set.size() > 0) {
			DataTable dt = DataTableUtil.txtToDataTable(set.get(0).getTargetCatalog(), (String[]) null, "\t", "\n");
			if (dt.getDataColumn("MD5") == null) {
				dt.insertColumn("MD5");
			}
			// 如果是自动分发，则全选上
			if ("1".equals(set.get(0).getDeployType())) {
				map.put("ClusterTargetCount", dt.getRowCount() + "/" + dt.getRowCount());
				for (int i = 0; i < dt.getRowCount(); i++) {
					dt.set(i, "MD5", StringUtil.md5Hex(dt.getString(i, "ServerAddr") + "," + dt.getString(i, "SiteID") + "," + dt.getString(i, "CatalogID")));
				}
				map.put("ClusterTarget", StringUtil.join(dt.getColumnValues("MD5")));
			} else {
				map.put("ClusterTargetCount", "0/" + dt.getRowCount());
			}
		}

		long siteID = Long.parseLong(CatalogUtil.getSiteID(catalogID));
		map.put("SiteID", CatalogUtil.getSiteID(catalogID));
		map.put("CatalogName", CatalogUtil.getName(catalogID));
		map.put("IssueID", issueID);
		map.put("InnerCode", CatalogUtil.getInnerCode(catalogID));
		map.put("URL", CatalogUtil.getPath(catalogID) + articleID + ".shtml");

		// css样式
		String cssFile = new QueryBuilder("select editorcss from zcsite where id=?", siteID).executeString();
		if (StringUtil.isNotEmpty(cssFile)) {
			String staticDir = Config.getContextPath() + Config.getValue("Statical.TargetDir").replace('\\', '/');
			staticDir = staticDir + "/" + Application.getCurrentSiteAlias() + "/" + cssFile;
			staticDir = staticDir.replaceAll("/+", "/");
			map.put("CssPath", staticDir);
		} else {
			map.put("CssPath", Config.getContextPath() + "Editor/editor/css/fck_editorarea.css");
		}

		String defaultImageValue = Config.getContextPath() + Config.getValue("UploadDir") + "/" + Application.getCurrentSiteAlias() + "/upload/Image/nopicture.jpg";
		defaultImageValue = defaultImageValue.replaceAll("/+", "/");
		map.put("defaultImageValue", defaultImageValue);
		map.put("Title", StringUtil.htmlEncode(map.getString("Title")));
		return map;
	}

	public void applyStep() {
		long instanceID = Long.parseLong($V("InstanceID"));
		int nodeID = Integer.parseInt($V("NodeID"));
		String catalogID = $V("CatalogID");
		try {
			WorkflowUtil.applyStep(instanceID, nodeID);
			WorkflowAction[] actions;
			try {
				actions = WorkflowUtil.findAvaiableActions(instanceID);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				Response.setMessage("申请失败：" + e.getMessage());
				return;
			}
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < actions.length; i++) {
				WorkflowAction action = actions[i];
				if (action.getID() == WorkflowAction.TEMPORARYSAVE_ACTIONID) {
					if (Priv.getPriv(Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_MODIFY)) {
						sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';save('" + instanceID + "','" + action.getID() + "')", null, "<img src='../Icons/icon003a16.gif'/>", action
								.getName()));
					}
				} else {
					boolean allowSelectUser = "1".equals(action.getData().get("AllowSelectUser"));
					sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';save('" + instanceID + "','" + action.getID() + "'," + allowSelectUser + ")", null,
							"<img src='../Icons/icon003a16.gif'/>", action.getName()));
				}
			}
			$S("buttonDiv", sb.toString());
			Response.setMessage("申请成功");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setMessage("申请失败:" + e.getMessage());
		}
	}

	private static String getInitButtons(long catalogID, long instanceID, boolean publishFlag) {
		String workflowID = CatalogUtil.getWorkflow(catalogID);
		if (StringUtil.isNotEmpty(workflowID)) {
			if (WorkflowUtil.findWorkflow(Long.parseLong(workflowID)) == null) {
				workflowID = null;// 没有找到则认为没有工作流
			}
		}
		if (publishFlag) {// 已经处于发布或待发布状态了，可以直接显示发布按钮
			String buttonDiv = null;
			if (StringUtil.isNotEmpty(workflowID)) {
				if (UserList.ADMINISTRATOR.equals(User.getUserName())) {
					buttonDiv = TButtonTag.getHtml("publish(true)", null, "<img src='../Icons/icon003a13.gif'/>", "发布");
				} else {
					buttonDiv = "";
				}
				if (Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_MODIFY)) {
					buttonDiv += TButtonTag.getHtml("ClickMethod='重新修改';save('" + instanceID + "','" + WorkflowAction.RESTART_ACTIONID + "')", null, "<img src='../Icons/icon003a16.gif'/>", "申请修改");
				}
			} else {
				if (Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_MODIFY)) {
					// buttonDiv = TButtonTag.getHtml("save()",
					// "article_modify", "<img src='../Icons/icon003a16.gif'/>",
					// "保存")
					// + TButtonTag.getHtml("topublish()", "article_modify",
					// "<img src='../Icons/icon003a6.gif'/>", "待发布")
					// + TButtonTag.getHtml("publish()", "article_modify",
					// "<img src='../Icons/icon003a13.gif'/>",
					// "发布");
					buttonDiv = TButtonTag.getHtml("topublish()", "article_modify", "<img src='../Icons/icon003a6.gif'/>", "待发布")
							+ TButtonTag.getHtml("publish()", "article_modify", "<img src='../Icons/icon003a13.gif'/>", "发布");
				} else {
					buttonDiv = "";
				}
			}
			return buttonDiv;
		}

		if (StringUtil.isNotEmpty(workflowID)) {
			// 指定了工作流，按工作流方式处理
			WorkflowAction[] actions = null;
			if (instanceID != 0) {
				try {

					if (WorkflowUtil.isStartStep(instanceID) || Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_AUDIT)) {
						actions = WorkflowUtil.findAvaiableActions(instanceID);
					}
				} catch (Exception e) {
					logger.error("Article.init:" + e.getMessage(), e);
					return "";
				}
			} else {
				try {
					actions = WorkflowUtil.findInitActions(Long.parseLong(workflowID));
				} catch (NumberFormatException e) {
					logger.error(e.getMessage(), e);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					return "<script>Page.onLoad(function(){Dialog.alert('初始化工具流按钮出错：" + e.getMessage() + "');});</script>";
				}
			}
			StringBuffer sb = new StringBuffer();
			for (int i = 0; actions != null && i < actions.length; i++) {
				WorkflowAction action = actions[i];
				if (action.getID() == WorkflowAction.APPLY_ACTIONID) {
					sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';applyStep('" + instanceID + "','" + action.getData().getInt("NodeID") + "')", null,
							"<img src='../Icons/icon003a16.gif'/>", action.getName()));
				} else if (action.getID() == WorkflowAction.TEMPORARYSAVE_ACTIONID) {
					if (instanceID == 0 // 新建未保存
							|| Priv.getPriv(Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_MODIFY)) {
						sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';save('" + instanceID + "','" + action.getID() + "')", null, "<img src='../Icons/icon003a16.gif'/>", action
								.getName()));
					}
				} else {
					boolean allowSelectUser = "1".equals(action.getData().get("AllowSelectUser"));
					sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';save('" + instanceID + "','" + action.getID() + "'," + allowSelectUser + ")", null,
							"<img src='../Icons/icon003a16.gif'/>", action.getName()));

				}
			}
			return sb.toString();
		} else {
			// 没有工作流
			if (Priv.getPriv(User.getUserName(), Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_MODIFY)) {
				String buttonDiv = TButtonTag.getHtml("save()", "article_modify", "<img src='../Icons/icon003a16.gif'/>", "保存")
						+ TButtonTag.getHtml("topublish()", "article_modify", "<img src='../Icons/icon003a6.gif'/>", "待发布")
						+ TButtonTag.getHtml("publish()", "article_modify", "<img src='../Icons/icon003a13.gif'/>", "发布");
				return buttonDiv;
			}
		}
		return "";
	}

	public static Mapx initPreview(Mapx params) {
		String articleID = (String) params.get("ArticleID");
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(Integer.parseInt(articleID));
		article.fill();
		params = article.toDataRow().toCaseIgnoreMapx();
		return params;
	}

	/**
	 * 初始化相关文章及推荐文章列表
	 */
	public static void relativeDg1DataBind(DataGridAction dga) {
		String relaIDs = (String) dga.getParams().get("RelativeArticle");
		if (!StringUtil.checkID(relaIDs)) {
			return;
		}
		if ("".equals(relaIDs)) {
			dga.bindData(new DataTable());
			return;
		}
		DataTable dt = new QueryBuilder("select title,id from zcarticle where id in (" + relaIDs + ")").executeDataTable();

		// 按ID顺序来显示
		String[] ids = relaIDs.split("\\,");
		DataTable result = new DataTable(dt.getDataColumns(), null);
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < dt.getRowCount(); j++) {
				if (dt.getString(j, "ID").equals(ids[i])) {
					result.insertRow(dt.getDataRow(j));
					break;
				}
			}
		}

		dga.bindData(result);
	}

	public static Mapx initPlayerList(Mapx params) {
		String catalogID = params.getString("CatalogID");
		DataTable dt = new DataTable();
		String defaultPlayer = params.getString("ImagePlayerID");
		if (StringUtil.isNotEmpty(catalogID)) {
			String tempCode = "";
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(catalogID);
			catalog.fill();
			tempCode = "'" + catalog.getInnerCode() + "'";
			if (StringUtil.isEmpty(defaultPlayer)) {
				defaultPlayer = new QueryBuilder("select ID from ZCImagePlayer where SiteID = ? and RelaCatalogInnerCode = ?", Application.getCurrentSiteID(), catalog.getInnerCode()).executeString();
			}
			while (catalog.getParentID() != 0) {
				String temp = catalog.getParentID() + "";
				catalog = new ZCCatalogSchema();
				catalog.setID(temp);
				catalog.fill();
				tempCode += ",'" + catalog.getInnerCode() + "'";
			}
			tempCode += ",'0'";
			dt = new QueryBuilder("select Name,ID from ZCImagePlayer where SiteID = ? and RelaCatalogInnerCode in(" + tempCode + ")", Application.getCurrentSiteID()).executeDataTable();
		}
		if (StringUtil.isEmpty(defaultPlayer)) {
			defaultPlayer = "";
		}
		params.put("ImagePlayers", HtmlUtil.dataTableToOptions(dt, defaultPlayer));
		return params;
	}

	public void saveRelaImage() {
		String articleID = $V("ArticleID");
		String imageIDs = $V("ImageIDs");
		String imagePlayerID = $V("ImagePlayerID");
		String[] ids = imageIDs.split(",");
		Transaction trans = new Transaction();
		int count = new QueryBuilder("select count(*) from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ?", imagePlayerID).executeInt();
		count = 6 - count;
		String tempID = new QueryBuilder("select ID from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ? order by OrderFlag asc", imagePlayerID).executeOneValue() + "";
		if (count == 0) {
			trans.add(new QueryBuilder("delete from ZCImageRela where RelaType = 'ImagePlayerImage' and ID=?", tempID));
			count = 1;
		}
		ZCImageSchema image = new ZCImageSchema();
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(articleID);
		article.fill();
		for (int i = 0; i < ids.length; i++) {
			if (i >= count) {
				break;
			}
			trans.add(new QueryBuilder("delete from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ? and ID=?", imagePlayerID, ids[i]));
			image = new ZCImageSchema();
			image.setID(ids[i]);
			image.fill();
			String isSingle = CatalogUtil.getSingleFlag(article.getCatalogID());
			String path = "";
			if ("Y".equals(isSingle)) {
				path = (SiteUtil.getURL(Application.getCurrentSiteID()) + "/" + CatalogUtil.getPath(article.getCatalogID())).replaceAll("/+", "/");
			} else {
				path = (SiteUtil.getURL(Application.getCurrentSiteID()) + "/" + PubFun.getArticleURL(article)).replaceAll("/+", "/");
			}
			image.setLinkURL(path);
			image.setLinkText(article.getTitle());
			trans.add(image, Transaction.UPDATE);
			ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
			imageRela.setID(image.getID());
			imageRela.setRelaID(imagePlayerID);
			imageRela.setRelaType(ImagePlayerRela.RELATYPE_IMAGEPLAYER);
			imageRela.setOrderFlag(OrderUtil.getDefaultOrder());
			imageRela.setAddUser(User.getUserName());
			imageRela.setAddTime(new Date());
			trans.add(imageRela, Transaction.INSERT);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "关联至图片播放器成功");
		} else {
			Response.setLogInfo(0, "关联失败");
		}
	}

	public static void relaImageDataBind(DataListAction dla) {
		String ImageIDs = dla.getParam("ImageIDs");
		String RelaImageIDs = dla.getParam("RelaImageIDs");
		if (!StringUtil.checkID(ImageIDs) || !StringUtil.checkID(RelaImageIDs)) {
			return;
		}
		DataTable dt = new DataTable();
		if (StringUtil.isNotEmpty(ImageIDs)) {
			String path = Config.getValue("StaticResourcePath")+"/";
			dt = new QueryBuilder("select * from zcimage where ID in(" + ImageIDs + ")").executeDataTable();
			dt.insertColumn("Alias");
			dt.insertColumn("checkStatus");
			String[] IDs = RelaImageIDs.split(",");
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "Alias", path);
				for (int j = 0; j < IDs.length; j++) {
					if (IDs[j].equals(dt.getString(i, "ID"))) {
						dt.set(i, "checkStatus", "checked");
					}
				}
			}
		}
		dla.bindData(dt);
	}

	public static void dealVote(ZCArticleSchema article, Transaction trans) {
		DataTable vote = new QueryBuilder("select * from zcvote where siteid=? and votecatalogid = ?", article.getSiteID(), article.getCatalogID()).executeDataTable();
		if (vote.getRowCount() > 0) {
			DataTable subject = new QueryBuilder("select * from zcvotesubject where voteid=? and votecatalogid = ?", vote.getString(0, "ID"), article.getCatalogID()).executeDataTable();
			if (subject.getRowCount() > 0) {
				ZCVoteItemSchema item = new ZCVoteItemSchema();
				item.setID(NoUtil.getMaxID("VoteItemID"));
				item.setVoteID(subject.getString(0, "VoteID"));
				item.setSubjectID(subject.getString(0, "ID"));
				item.setItem(article.getTitle());
				item.setScore(0);
				item.setItemType("0");
				item.setVoteDocID(article.getID());
				item.setOrderFlag(OrderUtil.getDefaultOrder());
				trans.add(item, Transaction.INSERT);
			}
		}
	}

	/**
	 * 系统自动推荐的相关文章
	 */
	public static void autoRelativeDataBind(DataGridAction dga) {
		String relaIDs = (String) dga.getParams().get("IDs");
		if (!StringUtil.checkID(relaIDs)) {
			return;
		}
		if ("".equals(relaIDs)) {
			relaIDs = "''";
		}
		DataTable dt = new QueryBuilder("select title,id from zcarticle where id in (" + relaIDs + ")").executeDataTable();
		dga.bindData(dt);

	}

	public static void recommendDg1DataBind(DataGridAction dga) {
		String recIDs = (String) dga.getParams().get("RecommendArticle");
		if (!StringUtil.checkID(recIDs)) {
			return;
		}
		if ("".equals(recIDs)) {
			dga.bindData(new DataTable());
			return;
		}
		DataTable dt = new QueryBuilder("select title,id,addtime,author from zcarticle where id in (" + recIDs + ")").executeDataTable();

		// 按ID顺序来显示
		String[] ids = recIDs.split("\\,");
		DataTable result = new DataTable(dt.getDataColumns(), null);
		for (int i = 0; i < ids.length; i++) {
			for (int j = 0; j < dt.getRowCount(); j++) {
				if (dt.getString(j, "ID").equals(ids[i])) {
					result.insertRow(dt.getDataRow(j));
					break;
				}
			}
		}

		dga.bindData(result);
	}

	/**
	 * 保存文章
	 *
	 * @return
	 */
	public boolean save() {
		String clickMethod = $V("ClickMethod");
		String logMessage = "";
		try {
			Transaction trans = new Transaction();
			
			ZCArticleSchema article = new ZCArticleSchema();
			long articleID = Long.parseLong((String) Request.get("ArticleID"));
			article.setID(articleID);

			String method = $V("Method");

			if ("UPDATE".equals(method)) {
				article.fill();
			}
			long catalogID = Long.parseLong($V("CatalogID"));
			article.setCatalogID(catalogID);
			String siteID = CatalogUtil.getSiteID(catalogID);
			article.setSiteID(siteID);
	/**		
				// 增加新建消息
				StringFormat sf = new StringFormat("标题为 ? 的文档己被创建");
				sf.add("<font class='red'>" + article.getTitle() + "</font>");
				String subject = sf.toString();

				sf = new StringFormat("创建的标题为 ? 的文档，己于 ? 由 ? 创建。");
				sf.add("<font class='red'>" + article.getTitle() + "</font>");//ArticleTitle
				sf.add("<font class='red'>" + DateUtil.getCurrentDateTime() + "</font>");
				sf.add("<font class='red'>" + User.getRealName() + "</font>");

				MessageCache.addMessage(trans, subject, sf.toString(), new String[] { article.getAddUser()}, "SYSTEM",
						false);
	**/

			// 判断栏目是否是本机构独立管理
			ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
			if ("Y".equals(config.getBranchManageFlag()) && !UserList.ADMINISTRATOR.equals(User.getUserName())) {
				String branchInnerCode = article.getBranchInnerCode();
				if (StringUtil.isNotEmpty(branchInnerCode) && !User.getBranchInnerCode().equals(branchInnerCode)) {
					Response.setStatus(0);
					Response.setMessage("发生错误：您没有操作本文档权限！");
					return false;
				}
			}
			GetDBdata db = new GetDBdata();
			String[] tempParame = new String[1];
			tempParame[0] = $V("RiskType");
			String sql = "select CodeName,Memo from zdcode where codetype='RiskType' and CodeValue = ?";
			List<HashMap<String, String>> list =  db.query(sql, tempParame);
			if(list.size() > 0 ){
				String ContentSign = list.get(0).get("Memo");
				if("".equals(ContentSign)){
					Response.setStatus(0);
					UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_SAVEARTICLE, logMessage + "文章失败！", Request.getClientIP());
					Response.setMessage("发生错误：险种:"+list.get(0).get("CodeName")+" Memo 字段为空！");
					return false;
				}
				article.setContentSign(ContentSign);
			}
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			article.setCatalogInnerCode(innerCode);
			article.setType($V("Type"));
			String title = $V("Title");
			article.setTitle(title);
			article.setTitleStyle($V("TitleStyle"));
			article.setShortTitle($V("ShortTitle"));
			article.setShortTitleStyle($V("ShortTitleStyle"));
			article.setSubTitle($V("SubTitle"));
			article.setReferURL($V("ReferURL"));
			article.setReferName($V("ReferName"));
			article.setRelativeArticle($V("RelativeArticle"));
			article.setRecommendArticle($V("RecommendArticle"));
			article.setTopFlag($V("TopFlag"));
			article.setCommentFlag($V("CommentFlag"));
			article.setPriority($V("Priority"));
			article.setAttribute($V("Attribute"));
		
			article.setProp2($V("Prop2"));
			article.setProp4($V("Prop4"));
			article.setSourceSign($V("SourceSign"));
			article.setMetaKeywords($V("MetaKeywords"));
			article.setMetaDescription($V("MetaDescription"));
			article.setRiskType($V("RiskType"));
			article.setRiskCompany($V("CompanyID"));
//			if ("".equals($V("MetaTitle")) || $V("MetaTitle") == null) {
//				String temp = "";
//				// ZCCatalogSchema catalog = CMSCache.getCatalog(tCatalogID);
//				ZCCatalogSchema catalog = new ZCCatalogSchema();
//				catalog.setID(catalogID);
//				catalog.fill();
//				ZCSiteSchema tZCSiteSchema = new ZCSiteSchema();
//				tZCSiteSchema.setID(catalog.getSiteID());
//				tZCSiteSchema.fill();
//				String tSiteName = tZCSiteSchema.getName();
//				temp = catalog.getName();
//				long tParentID = catalog.getParentID();
//				while (tParentID != 0) {
//					catalog = new ZCCatalogSchema();
//					catalog.setID(tParentID);
//					catalog.fill();
//					temp = temp + "-" + catalog.getName();
//					tParentID = catalog.getParentID();
//				}
//
//				String sql = "select value from zdconfig where type=\'ArticleTitle\'";
//				Object title_ = new QueryBuilder(sql).executeOneValue();
//				String tMetaTitle = null;
//
//				if (StringUtil.isEmpty(title_ + "")) {
//					tMetaTitle = title + "-" + temp + "-" + tSiteName;
//				} else {
//					tMetaTitle = title + " - " + title_;
//				}
//
//				article.setMetaTitle(tMetaTitle);
//			} else {
//				String temp = "";
//				// ZCCatalogSchema catalog = CMSCache.getCatalog(tCatalogID);
//				ZCCatalogSchema catalog = new ZCCatalogSchema();
//				catalog.setID(catalogID);
//				catalog.fill();
//				ZCSiteSchema tZCSiteSchema = new ZCSiteSchema();
//				tZCSiteSchema.setID(catalog.getSiteID());
//				tZCSiteSchema.fill();
//				String tSiteName = tZCSiteSchema.getName();
//				temp = catalog.getName();
//				long tParentID = catalog.getParentID();
//				while (tParentID != 0) {
//					catalog = new ZCCatalogSchema();
//					catalog.setID(tParentID);
//					catalog.fill();
//					temp = temp + "-" + catalog.getName();
//					tParentID = catalog.getParentID();
//				}
//				String tMetaTitle = $V("MetaTitle") + "-" + temp + "-" + tSiteName;
//				article.setMetaTitle(tMetaTitle);
//			}
			article.setMetaTitle($V("MetaTitle"));
			if (("".equals(article.getFirstPublishDate()) || article.getFirstPublishDate() == null) && article.getStatus() == STATUS_PUBLISHED ) {
				article.setFirstPublishDate(new Date());
			}
			Date tDate = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String temp1 = formatter.format(tDate);
			temp1 = temp1.substring(0, 10).replace("-", "");
			if (article.getEveryDayNo() == 0) {
				DataTable nextDT = new QueryBuilder("select max(EveryDayNo)as aNo from zcarticle where  date_format(addtime,'%Y%m%d') =?", temp1).executePagedDataTable(1, 0);
				if ("".equals(nextDT.getString(0, "aNo"))) {
					article.setEveryDayNo(1);
				} else {
					article.setEveryDayNo(Long.parseLong(nextDT.getString(0, "aNo")) + 1);
				}
			}
			// 2010-10-29 在文章表Article 的Prop1中放入作废，便于页面取数据
			if ("Y".equals($V("Prop1"))) {
				article.setProp1("作废");
			} else {
				article.setProp1(null);
			}
			// article.setProp1($V("Prop1"));//Prop1表示文档是否作废 modify by wyli
			// 2010-07-28

			// 文章引导图，存储相对于站点目录的路径,如upload/default/1_84938439.jpg
			String sitePath = Config.getValue("StaticResourcePath");
			String logo = $V("Logo");
			if (StringUtil.isNotEmpty(logo)) {
				logo = logo.replaceAll(sitePath, "");
			}
			article.setLogo(logo);

			// 站点群分发
			article.setClusterTarget($V("ClusterTarget").trim());

			// 文章复制
			if($V("ReferTarget")!=null){
				article.setReferTarget($V("ReferTarget").trim());
			}

			String author = $V("Author");
			article.setAuthor(author);
			article.setSummary($V("Summary"));

			String content = $V("Content");
			if (StringUtil.isEmpty(content)) {
				content = " ";
			}

			String pageTitles = $V("PageTitles");
			if (StringUtil.isEmpty(pageTitles)) {
				pageTitles = " ";
			}
			article.setPageTitle(pageTitles);

			/*// 保存远程图片到本地
			if ("1".equals($V("LocalImageFlag"))) {
				String tempContent = content;
				if (Priv.getPriv(Priv.ARTICLE, innerCode, Priv.ARTICLE_MODIFY)) {
					content = copyRemoteFiles(content, trans, articleID);
				}
				if (!tempContent.equals(content)) {
					$S("ContentChanged", "Y");
					$S("Content", content);// 需要以替换后的内容更新前台编辑器，以免下次保存时再次上传文件
				}
				article.setContent(content);
				article.setCopyImageFlag("Y");
			} else {
				article.setContent(content);
				article.setCopyImageFlag("N");
			}*/
			article.setContent(content);
			article.setCopyImageFlag("N");
			if (Priv.getPriv(Priv.ARTICLE, innerCode, Priv.ARTICLE_MODIFY)) {
				// 处理关键词
				String keyword = $V("Keyword");
				dealKeyword(trans, article, keyword);

				// 处理Tag内容
				dealTag(trans, article);

				// 处理关联图片
				dealRelaImage(trans, article);

				// 处理关联附件
				dealRelaAttach(trans, article);

				// 处理关联视频
				dealRelaVideo(trans, article);
			}

			$S("Keyword", article.getKeyword()); // 返回给前台
			$S("TagWord", article.getTag());
			// 发布时间
			if (StringUtil.isNotEmpty($V("PublishDate"))) {
				String publishTime = $V("PublishTime");
				if (StringUtil.isEmpty(publishTime)) {
					publishTime = "00:00:00";
				}
				article.setPublishDate(DateUtil.parse($V("PublishDate") + " " + publishTime, DateUtil.Format_DateTime));
			}

			// 下线时间
			if (StringUtil.isEmpty($V("DownlineDate"))) {
				article.setDownlineDate(DateUtil.parse("2099-12-31 23:59:59", DateUtil.Format_DateTime));
			} else {
				String downTime = $V("DownlineTime");
				if (StringUtil.isEmpty(downTime)) {
					downTime = "00:00:00";
				}
				article.setDownlineDate(DateUtil.parse($V("DownlineDate") + " " + downTime, DateUtil.Format_DateTime));
			}

			// 归档时间
			if (StringUtil.isEmpty($V("ArchiveDate"))) {
				article.setArchiveDate(null);
			} else {
				String archiveTime = $V("ArchiveTime");
				if (StringUtil.isEmpty(archiveTime)) {
					archiveTime = "00:00:00";
				}
				article.setArchiveDate(DateUtil.parse($V("ArchiveDate") + " " + archiveTime, DateUtil.Format_DateTime));
			}
			// 设置url
			if ("ADD".equals(method)) {
				article.setAddUser(User.getUserName());
			}
			if(StringUtil.isEmpty(article.getURL())){
				// 判断产品是否有现金价值页
				if (cashValueFlag) {
					String sqlTemp = "select memo from zdcode where CodeType = 'BuyFlow.CodeFlag' and CodeValue = 'ShowCashValueProducts'";
					QueryBuilder qb = new QueryBuilder(sqlTemp);
					String riskCodes = qb.executeString();
					if (StringUtil.isNotEmpty(riskCodes) && riskCodes.contains(article.getProp4())) {
						article.setURL("cashvalue/" + article.getProp4() + ".shtml");
					}
				}
				else if (showBenefitFlag) {
					// 年金险标识
					int count = new QueryBuilder("SELECT COUNT(*) FROM zdcode WHERE codetype = 'yearCash.riskCode' AND FIND_IN_SET('"+ article.getProp4() +"', CodeValue);").executeInt();
					if (count > 0) {
						article.setURL("showBenefit/" + article.getProp4() + ".shtml");
					}
				} else {
					article.setURL(PubFun.getArticleURL(article));
				}

			}
			article.setRedirectURL($V("RedirectURL"));
			article.setTemplateFlag($V("TemplateFlag"));
			article.setTemplate($V("Template"));

			String issueStr = $V("IssueID");
			if (issueStr != null && !"".equals(issueStr)) {
				article.setIssueID(Long.parseLong($V("IssueID")));
			}

			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setArticleID(articleID);

			String entryId = $V("entryId");// 工作流实例ID
			String actionId = $V("actionId");// 工作流动作ID

			String workflowID = CatalogUtil.getWorkflow(catalogID);
			Context context = null;
			if ("UPDATE".equals(method)) { // 更新
				article.setModifyTime(new Date());
				article.setModifyUser(User.getUserName());
				articleLog.setAction("UPDATE");
				if (StringUtil.isNotEmpty(clickMethod)) {
					articleLog.setActionDetail(clickMethod);
				} else {
					articleLog.setActionDetail("更新文章");
				}
				if (Priv.getPriv(Priv.ARTICLE, innerCode, Priv.ARTICLE_MODIFY)) {
					trans.add(article, Transaction.UPDATE);

					QueryBuilder qb = new QueryBuilder("update zccatalog set isdirty=1 where id=?", catalogID);
					trans.add(qb);
				}

				// 处理工作流
				if (StringUtil.isNotEmpty(workflowID)) {
					trans.add(new QueryBuilder("update zcarticle set Status=" + STATUS_WORKFLOW + " where id=?", article.getID()));
					if ((StringUtil.isEmpty(entryId) || entryId.equals("0"))) {
						// 重新申请时如果以前没有工作流则entryId=0
						context = WorkflowUtil.createInstance(trans, Long.parseLong(workflowID), "CMS", "" + article.getID(), "0");
						article.setWorkFlowID(context.getInstance().getID());
					} else {
						if (StringUtil.isEmpty(actionId)) {
							Response.setError("工作流执行错误：entryId=" + entryId + ",actionId=" + actionId);
							return false;
						} else {
							WorkflowAction action = WorkflowUtil.findAction(Long.parseLong(workflowID), Integer.parseInt(actionId));
							action.execute(trans, Long.parseLong(entryId), $V("NextStepUser"), $V("Memo"));
						}
					}
				} else {
					if (article.getStatus() == STATUS_PUBLISHED || article.getStatus() == STATUS_TOPUBLISH) {
						article.setStatus(STATUS_EDITING);
					}
				}
				logMessage = "更新";
			} else if ("ADD".equals(method)) { // 新增
				// 处理投票
				dealVote(article, trans);

				article.setPublishFlag("1");
				//article.setOrderFlag(OrderUtil.getDefaultOrder());
				article.setOrderFlag(0);
				article.setHitCount(0);
				article.setStickTime(0);
				article.setStatus(Article.STATUS_DRAFT);
				article.setAddTime(new Date());
				article.setModifyTime(new Date());

				articleLog.setAction("INSERT");
				articleLog.setActionDetail("新建文章");

				article.setAddUser(User.getUserName());
				article.setBranchInnerCode(User.getBranchInnerCode());
				if (StringUtil.isNotEmpty(workflowID)) {
					// 加入工作流
					context = WorkflowUtil.createInstance(trans, Long.parseLong(workflowID), "CMS", "" + article.getID(), "0");
					article.setWorkFlowID(context.getInstance().getID());
					// 如果为暂存，不写入工作流状态，认为仍然是初稿
					if (StringUtil.isNotEmpty(actionId) && !(WorkflowAction.TEMPORARYSAVE_ACTIONID + "").equals(actionId)) {
						article.setStatus(STATUS_WORKFLOW);
					}
				}

				trans.add(article, Transaction.INSERT);
				String sqlArticleCount = "update zccatalog set total = total+1,isdirty=1 where id=?";
				trans.add(new QueryBuilder(sqlArticleCount, catalogID));
				logMessage = "增加";
			}
			// 2011-1-14 处理历史显示真实姓名
			articleLog.setAddUser(User.getRealName());
			articleLog.setAddTime(new Date());
			if (StringUtil.isEmpty(workflowID)) {// 有工作流时不需要，工作流的Adapter会加处理历史记录
				trans.add(articleLog, Transaction.INSERT);
			}

			// 如果有批注，则增加批注记录
			if (StringUtil.isNotEmpty($V("NoteContent"))) {
				ArticleNote.add(trans, "" + articleID, $V("NoteContent"));
			}

			if (Priv.getPriv(Priv.ARTICLE, innerCode, Priv.ARTICLE_MODIFY)) {
				// 处理自定义字段
				Article.saveCustomColumn(trans, Request, catalogID, articleID, "ADD".equals(method));

				// 复制、引用文章
				if($V("ReferTarget")!=null){
					copy(Request, trans, article);
				}
			}
			article.setProp3($V("RelaImagePlayerID"));
			if (ExtendManager.hasAction("Article.BeforeSave_Tuis")) {
				ExtendManager.executeAll("Article.BeforeSave_Tuis", new Object[] { trans, article });
			}
			
			//关键词插入
			String keywordTemp = $V("InternalLinkWords");
			Transaction keyWordTran = new Transaction();
			
			if(StringUtil.isNotEmpty(keywordTemp)){
				
				try{
					
					keywordTemp = keywordTemp.trim().replaceAll("\\s+", ",");
					keywordTemp = keywordTemp.replaceAll("，", ",");
					String[] keyWords = StringUtil.splitEx(keywordTemp, ",");
					//取出站点地址
					String siteUrl = new QueryBuilder("select Value from zdconfig where type='FrontServerContextPath'").executeString();
					long SiteID = Application.getCurrentSiteID();
					
					String BelongCategory = "";
					
					String keyWordType = "";
					
					if(judgeRepeat(keyWords)){
						Response.setStatus(0);
						Response.setMessage("内链词编辑错误，存在重复的内链词，请检查并重新录入数据！");
						return false;
					}
					
					for (int i = 0; i < keyWords.length; i++) {
						
						if(StringUtil.isEmpty(keyWords[i])){
							Response.setStatus(0);
							Response.setMessage("内链词编辑错误，存在为空的内链词，请检查并重新录入数据！");
							return false;
						}
						
						ZCKeywordSchema keyword = new ZCKeywordSchema();
						
						keyWordType = new QueryBuilder("select ID from ZCKeywordType where siteID = ? and TypeName = '编辑文章添加'", SiteID).executeString();
						
						if(StringUtil.isEmpty(keyWordType)){
							ZCKeywordTypeSchema type = new ZCKeywordTypeSchema();
							
							long keyWordTypeId = NoUtil.getMaxID("KeyWordTypeID");
							
							type.setID(keyWordTypeId);
							type.setTypeName("编辑文章添加");
							type.setSiteID(Application.getCurrentSiteID());
							type.setAddTime(new Date());
							type.setAddUser(User.getUserName());
							type.insert();
							
							keyword.setKeywordType("," + keyWordTypeId + ",");
						}else{
							keyword.setKeywordType("," + keyWordType + ",");
						}
							
						keyword.setID(NoUtil.getMaxID("KeywordID"));
						keyword.setKeyword(keyWords[i]);
						keyword.setSiteId(siteID);
						keyword.setLinkUrl(siteUrl+"/"+PubFun.getArticleURL(article));
						keyword.setLinkTarget("_blank");
						keyword.setLinkAlt(keyWords[i]);
						keyword.setSearchCode(Long.parseLong("0"));
						keyword.setPriorityLevel(Long.parseLong("0"));
						keyword.setAddTime(new Date());
						keyword.setAddUser(User.getUserName());
						
						BelongCategory = new QueryBuilder("select BelongCategory from ZCCatalogConfig where CatalogID=?",catalogID).executeString();
						keyword.setBelongCategory(BelongCategory);
						
						keyWordTran.add(keyword, Transaction.INSERT);
					}
					
				}catch(Exception e){
					Response.setStatus(0);
					Response.setMessage("内链词编辑错误，请严格按照要求录入，每个词之间请用“,”隔开！");
					return false;
				}
				
				if (keyWordTran.commit()) {
					Response.setLogInfo(1, "导入成功");
				} else {
					Response.setStatus(0);
					String errMsg = keyWordTran.getExceptionMessage();
					Response.setLogInfo(0, "导入失败:"+errMsg);
					return false;
				}
				
			}
			
			if (trans.commit()) {
				Transaction relaImgTrans = new Transaction();
				String relaChange = $V("RelaChange");
				if (StringUtil.isNotEmpty(relaChange) && relaChange.equals("true")) {
					String relaImageIDs = $V("RelaImageIDs");
					long relaImagePlayerID = Long.parseLong($V("RelaImagePlayerID"));
					String[] ids = relaImageIDs.split(",");
					int count = new QueryBuilder("select count(*) from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ?", relaImagePlayerID).executeInt();
					int delCount = 0;
					delCount = count - (6 - ids.length);
					String[] tempID = new QueryBuilder("select ID from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ? order by OrderFlag asc", relaImagePlayerID).executeDataTable()
							.toString().split("\n");
					if (delCount > 0) {
						String temp = "";
						for (int i = 0; i < delCount; i++) {
							if (i != 0) {
								temp += ",";
							}
							temp += tempID[i + 1];
						}
						relaImgTrans.add(new QueryBuilder("delete from ZCImageRela where RelaType = 'ImagePlayerImage' and ID in (" + temp + ")"));
					}
					ZCImageSchema image = new ZCImageSchema();
					for (int i = 0; i < ids.length; i++) {
						relaImgTrans.add(new QueryBuilder("delete from ZCImageRela where RelaType = 'ImagePlayerImage' and RelaID = ? and ID=?", relaImagePlayerID, Long.parseLong(ids[i])));
						image = new ZCImageSchema();
						image.setID(ids[i]);
						image.fill();
						String isSingle = CatalogUtil.getSingleFlag(article.getCatalogID());
						if ("Y".equals(isSingle)) {
							sitePath = SiteUtil.getURL(Application.getCurrentSiteID());
							if (!sitePath.endsWith("/")) {
								sitePath += "/";
							}
							sitePath += CatalogUtil.getPath(article.getCatalogID());
						} else {
							sitePath = SiteUtil.getURL(Application.getCurrentSiteID());
							if (!sitePath.endsWith("/")) {
								sitePath += "/";
							}
							sitePath += PubFun.getArticleURL(article);
						}
						image.setLinkURL(sitePath);
						image.setLinkText(article.getTitle());
						relaImgTrans.add(image, Transaction.UPDATE);
						ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
						imageRela.setID(image.getID());
						imageRela.setRelaID(relaImagePlayerID);
						imageRela.setRelaType(ImagePlayerRela.RELATYPE_IMAGEPLAYER);
						imageRela.setOrderFlag(OrderUtil.getDefaultOrder());
						imageRela.setAddUser(User.getUserName());
						imageRela.setAddTime(new Date());
						relaImgTrans.add(imageRela, Transaction.INSERT);
					}
				}
				relaImgTrans.commit();
				Response.put("SaveTime", DateUtil.getCurrentDateTime());
				UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_SAVEARTICLE, logMessage + "文章:" + article.getTitle() + "成功！", Request.getClientIP());

				
				/*
				 * 如果工作流有动作，但entryID=0，则是尚未建立实例，需在提交后执行（提交前执行会找不到数据）
				 * 有几种情况可能有出现这种现象：1、新建文章之后再为栏目加上工作流
				 * 2、新建文章时不暂存直接提交动作3、从其他途径进入系统的文章，如WebService
				 */
				if (StringUtil.isNotEmpty(workflowID) && (StringUtil.isEmpty(entryId) || "0".equals(entryId))) {
					if (StringUtil.isNotEmpty(actionId) && Integer.parseInt(actionId) != WorkflowAction.TEMPORARYSAVE_ACTIONID) {
						WorkflowAction action = WorkflowUtil.findAction(Long.parseLong(workflowID), Integer.parseInt(actionId));
						action.execute(context, $V("NextStepUser"), $V("Memo"));
						context.getTransaction().commit();
					}
				}
				// 获得文章状态
				String buttonDiv = "";
				QueryBuilder qb = new QueryBuilder("select Status from ZCArticle where ID=?", article.getID());
				int status = qb.executeInt();
				if (status == Article.STATUS_TOPUBLISH || status == Article.STATUS_PUBLISHED) {
					if (StringUtil.isNotEmpty(workflowID)) {
						buttonDiv = "";
						if (Priv.getPriv(Priv.ARTICLE, CatalogUtil.getInnerCode(catalogID), Priv.ARTICLE_MODIFY)) {
							buttonDiv += TButtonTag.getHtml("publish(true)", null, "<img src='../Icons/icon003a13.gif'/>", "发布");
							buttonDiv += TButtonTag.getHtml("ClickMethod='重新修改';save('" + article.getWorkFlowID() + "','" + WorkflowAction.RESTART_ACTIONID + "')", null,
									"<img src='../Icons/icon003a16.gif'/>", "申请修改");
						}
					} else { // 没有工作流
						buttonDiv += TButtonTag.getHtml("save()", "article_modify", "<img src='../Icons/icon003a16.gif'/>", "保存")
								+ TButtonTag.getHtml("publish()", "article_modify", "<img src='../Icons/icon003a13.gif'/>", "发布");
						Response.put("buttonDiv", buttonDiv);
					}

					Response.put("buttonDiv", buttonDiv);
				} else {
					if (StringUtil.isNotEmpty(workflowID)) {
						// 指定了工作流，按工作流方式处理
						WorkflowAction[] actions = null;
						actions = WorkflowUtil.findAvaiableActions(article.getWorkFlowID());
						if (null == actions) {
							Response.setStatus(10);
							Response.setMessage("删除成功！");
							return false;
						}
						StringBuffer sb = new StringBuffer();
						for (int i = 0; i < actions.length; i++) {
							WorkflowAction action = actions[i];
							if (action.getID() == WorkflowAction.APPLY_ACTIONID) {
								sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';applyStep('" + article.getWorkFlowID() + "','" + action.getData().getInt("NodeID") + "')", null,
										"<img src='../Icons/icon003a16.gif'/>", action.getName()));
							} else {
								boolean allowSelectUser = "1".equals(action.getData().get("AllowSelectUser"));
								sb.append(TButtonTag.getHtml("ClickMethod='" + action.getName() + "';save('" + article.getWorkFlowID() + "','" + action.getID() + "'," + allowSelectUser + ")", null,
										"<img src='../Icons/icon003a16.gif'/>", action.getName()));
							}
						}
						Response.put("buttonDiv", sb.toString());
					} else { // 没有工作流
						buttonDiv = TButtonTag.getHtml("save()", "article_modify", "<img src='../Icons/icon003a16.gif'/>", "保存")
								+ TButtonTag.getHtml("topublish()", "article_modify", "<img src='../Icons/icon003a6.gif'/>", "待发布")
								+ TButtonTag.getHtml("publish()", "article_modify", "<img src='../Icons/icon003a13.gif'/>", "发布");
						Response.put("buttonDiv", buttonDiv);
					}
				}
			} else {
				Response.setStatus(0);
				UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_SAVEARTICLE, logMessage + "文章:" + article.getTitle() + "失败！", Request.getClientIP());
				Response.setMessage("发生错误：文档保存失败！");
				return false;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_SAVEARTICLE, logMessage + "文章失败！", Request.getClientIP());
			Response.setMessage("发生错误：" + e.getMessage());
			return false;
			
		}
		return true;
		
	}

	public static void saveCustomColumn(Transaction trans, Mapx map, long catalogID, long articleID, boolean newFlag) {
		if (!newFlag) {
			DataTable columnDT = ColumnUtil.getColumnValue(ColumnUtil.RELA_TYPE_DOCID, articleID);
			if (columnDT.getRowCount() > 0) {
				trans.add(new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?", ColumnUtil.RELA_TYPE_DOCID, articleID + ""));
			}
			trans.add(ColumnUtil.getValueFromRequest(catalogID, articleID, map), Transaction.INSERT);
		} else {
			trans.add(ColumnUtil.getValueFromRequest(catalogID, articleID, map), Transaction.INSERT);
		}
	}

	public void checkBadWord() {
		String priority = $V("Priority");
		String title = $V("Title");

		String badMsg = "";
		String badTitle = BadWord.checkBadWord(title, priority);
		if (StringUtil.isNotEmpty(badTitle)) {
			badMsg += "标题:" + badTitle + ",";
		}

		String shortTitle = $V("ShortTitle");
		String badShortTitle = BadWord.checkBadWord(shortTitle, priority);
		if (StringUtil.isNotEmpty(badShortTitle)) {
			badMsg += "短标题:" + badShortTitle + ",";
		}

		String badSubTitle = BadWord.checkBadWord($V("SubTitle"), priority);
		if (StringUtil.isNotEmpty(badSubTitle)) {
			badMsg += "副标题:" + badSubTitle + ",";
		}

		String badAuthor = BadWord.checkBadWord($V("Author"), priority);
		if (StringUtil.isNotEmpty(badAuthor)) {
			badMsg += "作者:" + badAuthor + ",";
		}

		String badKeyword = BadWord.checkBadWord($V("Keyword"), priority);
		if (StringUtil.isNotEmpty(badKeyword)) {
			badMsg += "关键词:" + badKeyword + ",";
		}

		String badReferName = BadWord.checkBadWord($V("ReferName"), priority);
		if (StringUtil.isNotEmpty(badReferName)) {
			badMsg += "来源:" + badReferName + ",";
		}

		String badContent = BadWord.checkBadWord($V("Content"), priority);
		if (StringUtil.isNotEmpty(badContent)) {
			badMsg += "正文:" + badContent + ",";
		}

		String badSummary = BadWord.checkBadWord($V("Summary"), priority);
		if (StringUtil.isNotEmpty(badSummary)) {
			badMsg += "摘要:" + badSummary + ",";
		}

		if (StringUtil.isNotEmpty(badMsg)) {
			Response.setStatus(1);
			Response.put("BadMsg", badMsg);
		} else {
			Response.setStatus(0);
		}
	}

	public static void dealKeyword(Transaction trans, ZCArticleSchema article, String keyword) {
		if (StringUtil.isNotEmpty(keyword)) {
			keyword = keyword.trim().replaceAll("\\s+", " ");
			keyword = keyword.replaceAll("，", " ");
			keyword = keyword.replaceAll("；", " ");
			keyword = keyword.replaceAll(";", " ");
			keyword = keyword.replaceAll("\\,+", " ");
			if (",".equals(keyword) || StringUtil.isEmpty(keyword)) {
				keyword = "";
			} else {
				if (keyword.indexOf(",") == 0) {
					keyword = keyword.substring(1);
				}
				if (keyword.lastIndexOf(",") == keyword.length() - 1) {
					keyword = keyword.substring(0, keyword.length() - 1);
				}
			}
		} else {
			// 判断是否自动获取关键字
			String keywordFlag = new QueryBuilder("select keywordFlag from zccatalog where id=?", article.getCatalogID()).executeString();
			if (StringUtil.isNotEmpty(keywordFlag) && !"N".equals(keywordFlag)) {
				keyword = StringUtil.join(IndexUtil.getKeyword(article.getTitle() + " " + article.getContent()), " ");
			}
		}
		article.setKeyword(keyword);

	}

	private void dealTag(Transaction trans, ZCArticleSchema article) {
		String tagword = $V("TagWord");
		if (StringUtil.isNotEmpty(tagword)) {
			tagword = tagword.trim().replaceAll("\\s+", " ");
			tagword = tagword.replaceAll("，", " ");
			tagword = tagword.replaceAll("；", " ");
			tagword = tagword.replaceAll(";", " ");
			tagword = tagword.replaceAll("\\,+", " ");
			if (",".equals(tagword) || StringUtil.isEmpty(tagword)) {
				tagword = "";
			} else {
				if (tagword.indexOf(",") == 0) {
					tagword = tagword.substring(1);
				}
				if (tagword.lastIndexOf(",") == tagword.length() - 1) {
					tagword = tagword.substring(0, tagword.length() - 1);
				}
			}
			if (StringUtil.isNotEmpty(tagword)) {
				QueryBuilder qb = new QueryBuilder("select value from zdconfig where  type = 'defaultmemo' ");
				Object defaultmemo = qb.executeOneValue();
				String[] tagwords = tagword.split(" ");
				for (int i = 0; i < tagwords.length; i++) {
					ZCTagSet tZCTagSet = Tag.checkTagWord(Application.getCurrentSiteID(), tagwords[i]);
					if (tZCTagSet.size() == 0) {
						ZCTagSchema tag = new ZCTagSchema();
						tag.setID(NoUtil.getMaxNo("TagID",6));
						tag.setTag(tagwords[i]);
						tag.setAddTime(new Date());
						tag.setAddUser(User.getUserName());
						tag.setUsedCount(1);
						tag.setSiteID(Application.getCurrentSiteID());
						String tagId=tag.getID()+"";
						if (tagId.length() < 6) {
							tagId = StringUtil.leftPad(tagId, '0', 6);
						}
						String tagHtml = "/tag/" + tagId + "000001-" + defaultmemo + ".html";
						tag.setLinkURL(tagHtml);
						trans.add(tag, Transaction.INSERT);
					} else {// 如果重复 被引用次数+1
						tZCTagSet.get(0).setUsedCount(tZCTagSet.get(0).getUsedCount() + 1);
						trans.add(tZCTagSet, Transaction.UPDATE);
					}
				}
			}
		}

		article.setTag(tagword);

	}

	/**
	 * 处理复制/引用文章
	 * 
	 * @param trans
	 * @param article
	 */
	public static void copy(DataCollection dc, Transaction trans, ZCArticleSchema article) {
		long articleID = article.getID();
		long catalogID = article.getCatalogID();
		// 复制文章
		String catalogIDs = dc.getString("ReferTarget");
		String referType = dc.getString("ReferType");
		// 自定义字段DestCatalog 用于复制到指定列表栏目中
		String otherCatalogID = dc.getString(ColumnUtil.PREFIX + "DestCatalog");
		if (StringUtil.isNotEmpty(otherCatalogID)) {
			if (StringUtil.isEmpty(catalogIDs)) {
				catalogIDs = otherCatalogID;
				referType = "2";
			} else {
				catalogIDs = catalogIDs + "," + otherCatalogID;
			}
		}

		// 1.复制全部 2 引用
		if (StringUtil.isEmpty(referType)) {
			referType = "1";
		}
		int refer = Integer.parseInt(referType);
		if (StringUtil.isNotEmpty(catalogIDs)) {
			String[] catalogArr = catalogIDs.split(",");
			String copySiteID = null;
			for (int j = 0; j < catalogArr.length; j++) {
				if (StringUtil.isEmpty(catalogArr[j].trim())) {
					continue;
				}
				long toCatalogID = Long.parseLong(catalogArr[j]);
				if (toCatalogID == article.getCatalogID()) {
					continue;
				}
				ZCArticleSchema articleRefer = new ZCArticleSchema();
				articleRefer.setReferSourceID(articleID);
				articleRefer.setCatalogID(toCatalogID);
				ZCArticleSet set = articleRefer.query();

				// 如果该栏目存在此篇文章的引用，更新文章相关内容,否则新建文章
				if (set != null && set.size() > 0) {
					articleRefer = set.get(0);
					articleRefer.setTitle(article.getTitle());
					articleRefer.setShortTitle(article.getShortTitle());
					articleRefer.setSubTitle(article.getSubTitle());
					articleRefer.setAuthor(article.getAuthor());
					articleRefer.setKeyword(article.getKeyword());
					articleRefer.setTag(article.getTag());
					articleRefer.setSummary(articleRefer.getSummary());
					articleRefer.setReferTarget("");
					articleRefer.setProp4(article.getProp4());
					//更新后复制引用变为 重新编辑状态 add by fhz 20130108
					if (articleRefer.getStatus() == STATUS_PUBLISHED || articleRefer.getStatus() == STATUS_TOPUBLISH) {
						articleRefer.setStatus(STATUS_EDITING);
					}
					if (refer == 1) {
						articleRefer.setContent(article.getContent());
					}

					// 处理文章关联属性
					if (Priv.getPriv(Priv.ARTICLE, CatalogUtil.getInnerCode(toCatalogID), Priv.ARTICLE_MODIFY)) {
						// 处理关联图片
						dealRelaImage(trans, articleRefer);

						// 处理关联附件
						dealRelaAttach(trans, articleRefer);

						// 处理关联视频
						dealRelaVideo(trans, articleRefer);

						// 处理投票
						dealVote(articleRefer, trans);

						// 如果有批注，则增加批注记录
						if (StringUtil.isNotEmpty(dc.getString("NoteContent"))) {
							ArticleNote.add(trans, "" + articleRefer.getID(), dc.getString("NoteContent"));
						}
						// 处理自定义字段
						Article.saveCustomColumn(trans, dc, toCatalogID, articleRefer.getID(), false);
					}

					trans.add(articleRefer, Transaction.UPDATE);
				} else {
					ZCArticleSchema articleColone = (ZCArticleSchema) article.clone();
					articleColone.setID(NoUtil.getMaxID("DocID"));
					articleColone.setCatalogID(toCatalogID);
					articleColone.setCatalogInnerCode(CatalogUtil.getInnerCode(toCatalogID));
					articleColone.setReferType(refer);
					articleColone.setReferSourceID(articleID);
					articleColone.setReferTarget("");

					// 允许多站点复制
					if (StringUtil.isEmpty(copySiteID)) {
						copySiteID = CatalogUtil.getSiteID(catalogArr[j]);
					}
					articleColone.setSiteID(copySiteID);

					// 置空置顶字段、单独模板标记
					articleColone.setTopFlag("0");
					articleColone.setTemplateFlag("0");
					articleColone.setTemplate("");

					// 引用链接
					if (refer == 2) {
						articleColone.setType("4");
						String siteId = SiteUtil.getURL(articleColone.getSiteID());
						//我不懂你为啥要查数据库呢~~~这不是传进来 article了么~~！FHZ 
						//在同步时 这个事务没有提交，查询数据库就会失败，直接从传进来的articel中取不就行了？ 
						//QueryBuilder qb1 = new QueryBuilder("select URL from ZCArticle  where id = ?");
						//qb1.add(articleID);
						//String catalogPath2 = qb1.executeOneValue()+"";
						
						//fhz 得到复制源的URL 
						String catalogPath2=article.getURL();
						String catalogPath = CatalogUtil.getPath(catalogID);
					
						if(StringUtil.isEmpty(catalogPath2)){
							logger.error("获取地址异常");
						}
						catalogPath2 = catalogPath2.substring(catalogPath2.lastIndexOf("/") + 1);					
						String referURL = (siteId + "/" + catalogPath + catalogPath2 ).replaceAll("///", "/");
						articleColone.setRedirectURL(referURL);
						articleColone.setContent("");
					}

					// 处理文章关联属性
					if (Priv.getPriv(Priv.ARTICLE, CatalogUtil.getInnerCode(toCatalogID), Priv.ARTICLE_MODIFY)) {
						// 处理关联图片
						dealRelaImage(trans, articleColone);

						// 处理关联附件
						dealRelaAttach(trans, articleColone);

						// 处理关联视频
						dealRelaVideo(trans, articleColone);

						// 处理投票
						dealVote(articleColone, trans);

						// 如果有批注，则增加批注记录
						if (StringUtil.isNotEmpty(dc.getString("NoteContent"))) {
							ArticleNote.add(trans, "" + articleColone.getID(), dc.getString("NoteContent"));
						}

						// 处理自定义字段
						Article.saveCustomColumn(trans, dc, toCatalogID, articleColone.getID(), true);
					}

					trans.add(articleColone, Transaction.INSERT);
				}

				String sqlArticleCount = "update zccatalog set total=total+1 where id=?";
				trans.add(new QueryBuilder(sqlArticleCount, Long.parseLong(catalogArr[j])));

				// 操作日志
				ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
				articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
				articleLog.setArticleID(article.getID());
				articleLog.setAction("COPY");
				articleLog.setActionDetail("复制。从" + CatalogUtil.getName(article.getCatalogID()) + "复制到" + CatalogUtil.getName(toCatalogID) + "。");
				// 2011-1-14 处理历史显示真实姓名
				articleLog.setAddUser(User.getRealName());
				articleLog.setAddTime(new Date());
				trans.add(articleLog, Transaction.INSERT);
			}
		}
	}

	/**
	 * 处理相关图片 将相关图片插入到文章图片关联表中
	 * 
	 * @param content
	 * @param articleID
	 * @param trans
	 */
	public static void dealRelaImage(Transaction trans, ZCArticleSchema article) {
		// 处理文章中的图片,关联文章和图片
		String regex = "zcmsimagerela=\"(.*?)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(article.getContent());
		int imgIndex = 0;
		Mapx map = new Mapx();
		boolean flag = false;

		// 得到所有图片的fileName,拼接成字符串
		while (matcher.find(imgIndex)) {
			int s = matcher.start();
			int e = matcher.end();
			imgIndex = e;
			String picSrc = article.getContent().substring(s, e);
			String imageID = picSrc.substring("zcmsimagerela=".length() + 1, picSrc.length() - 1);
			if (StringUtil.isDigit(imageID)) {
				map.put(imageID, imageID);
			}
			flag = true;
		}

		// 如果文章中有图片
		if (flag && map.size() > 0) {
			String str = StringUtil.join(map.keyArray(), ",");
			if (!StringUtil.checkID(str)) {
				logger.warn("Article.dealRelaImage:相关图片ID中有恶意字符");
				return;
			}
			trans.add(new QueryBuilder("delete from zcimagerela where relaid=? and relaType=?", article.getID(), RELA_IMAGE));

			// 更新图片表的linkUrl和linkText
			String siteUrl = SiteUtil.getURL(article.getSiteID());
			if (siteUrl.endsWith("shtml")) {
				siteUrl = siteUrl.substring(0, siteUrl.lastIndexOf("/"));
			}

			if (!siteUrl.endsWith("/")) {
				siteUrl = siteUrl + "/";
			}
			trans.add(new QueryBuilder("update zcimage set linkurl='" + siteUrl + PubFun.getArticleURL(article) + "',linktext='" + article.getTitle() + "' where ID in (" + str + ")"));
			String[] IDs = str.split(",");
			for (int i = 0; IDs != null && i < IDs.length; i++) {
				if (StringUtil.isEmpty(IDs[i]) || !StringUtil.isDigit(IDs[i])) {
					continue;
				}
				ZCImageRelaSchema rela = new ZCImageRelaSchema();
				rela.setID(IDs[i]);
				rela.setRelaID(article.getID());
				rela.setRelaType(RELA_IMAGE);
				rela.setOrderFlag(OrderUtil.getDefaultOrder());
				rela.setAddUser(User.getUserName());
				rela.setAddTime(new Date());
				trans.add(rela, Transaction.INSERT);
			}
		} else {
			trans.add(new QueryBuilder("delete from zcimagerela where relaid=? and relaType=?", article.getID(), RELA_IMAGE));
		}
	}

	/**
	 * 处理相关附件 将相关附件插入到文章附件关联表中
	 * 
	 * @param content
	 * @param articleID
	 * @param trans
	 */
	public static void dealRelaAttach(Transaction trans, ZCArticleSchema article) {
		if (StringUtil.isEmpty(article.getContent())) {
			return;
		}

		// 处理文章中的附件,关联文章和附件
		String regex = "zcmsattachrela=\"(.*?)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(article.getContent());
		int imgIndex = 0;
		Mapx map = new Mapx();
		boolean flag = false;

		// 得到所有附件的ID,拼接成字符串
		while (matcher.find(imgIndex)) {
			int s = matcher.start();
			int e = matcher.end();
			imgIndex = e;
			String picSrc = article.getContent().substring(s, e);
			String attachID = picSrc.substring("zcmsattachrela=".length() + 1, picSrc.length() - 1);
			if (StringUtil.isDigit(attachID)) {
				map.put(attachID, attachID);
			}
			flag = true;
		}

		// 如果文章中有附件
		if (flag) {
			String str = StringUtil.join(map.keyArray(), ",");
			if (!StringUtil.checkID(str)) {
				logger.warn("Article.dealRelaVideo:相关附件ID中有恶意字符");
				return;
			}
			if (StringUtil.isEmpty(str)) {
				str = "''";
			}
			trans.add(new QueryBuilder("delete from zcattachmentrela where relaid=? and relaType=? and ID not in (" + str + ")", article.getID(), RELA_ATTACH));
			String[] IDs = str.split(",");
			for (int i = 0; IDs != null && i < IDs.length; i++) {
				ZCAttachmentRelaSchema rela = new ZCAttachmentRelaSchema();
				rela.setID(IDs[i]);
				rela.setRelaID(article.getID());
				rela.setRelaType(RELA_ATTACH);
				if (!rela.fill()) {
					trans.add(rela, Transaction.INSERT);
				}
			}
		} else {
			trans.add(new QueryBuilder("delete from ZCAttachmentRela where relaid=? and relaType=?", article.getID(), RELA_ATTACH));
		}
	}

	/**
	 * 处理相关视频 将相关附件插入到文章视频关联表中
	 * 
	 * @param content
	 * @param articleID
	 * @param trans
	 */
	public static void dealRelaVideo(Transaction trans, ZCArticleSchema article) {
		if (StringUtil.isEmpty(article.getContent())) {
			return;
		}

		// 处理文章中的视频,关联文章和视频
		String regex = "zcmsvideorela=\"(.*?)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(article.getContent());
		int imgIndex = 0;
		Mapx map = new Mapx();
		boolean flag = false;

		// 得到所有视频的ID,拼接成字符串
		while (matcher.find(imgIndex)) {
			int s = matcher.start();
			int e = matcher.end();
			imgIndex = e;
			String picSrc = article.getContent().substring(s, e);
			String videoID = picSrc.substring("zcmsvideorela=".length() + 1, picSrc.length() - 1);
			if (StringUtil.isDigit(videoID)) {
				map.put(videoID, videoID);
			}
			flag = true;
		}

		// 如果文章中有视频
		if (flag) {
			String str = StringUtil.join(map.keyArray(), ",");
			if (!StringUtil.checkID(str)) {
				logger.warn("Article.dealRelaVideo:相关附件ID中有恶意字符");
				return;
			}
			if (StringUtil.isEmpty(str)) {
				str = "''";
			}
			trans.add(new QueryBuilder("delete from zcvideorela where relaid=? and relaType=? and ID not in (" + str + ")", article.getID(), RELA_VIDEO));
			String[] IDs = str.split(",");
			for (int i = 0; IDs != null && i < IDs.length; i++) {
				ZCVideoRelaSchema rela = new ZCVideoRelaSchema();
				rela.setID(IDs[i]);
				rela.setRelaID(article.getID());
				rela.setRelaType(RELA_VIDEO);
				if (!rela.fill()) {
					trans.add(rela, Transaction.INSERT);
				}
			}
		} else {
			trans.add(new QueryBuilder("delete from zcvideorela where relaid=? and relaType=?", article.getID(), RELA_VIDEO));
		}
	}

	/**
	 * 复制远程文件
	 * 
	 * @param destUrl
	 * @param fileName
	 * @return
	 */
	private boolean getRemoteFile(String destUrl, String fileName) {
		// 使用代理下载图片
		if ("Y".equalsIgnoreCase(Config.getValue("Proxy.IsUseProxy"))) {
			System.setProperty("http.proxyHost", Config.getValue("Proxy.Host"));
			System.setProperty("http.proxyPort", Config.getValue("Proxy.Port"));
			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(Config.getValue("Proxy.UserName"), new String(Config.getValue("Proxy.Password")).toCharArray());
				}
			});
		}

		try {
			byte buf[] = new byte[8096];
			int size = 0;
			URL url;
			url = new URL(destUrl);

			BufferedInputStream bis = new BufferedInputStream(url.openStream());
			bis.toString();
			FileOutputStream fos;
			fos = new FileOutputStream(fileName);
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}

			fos.close();
			bis.close();
		} catch (MalformedURLException e) {
			logger.error("非法地址" + e.getMessage(), e);
			return false;
		} catch (IOException e) {
			logger.error("没有找到对应的文件" + e.getMessage(), e);
			return false;
		}
		return true;

	}

	private String copyRemoteFiles(String content, Transaction trans, long articleID) {
		Pattern varPattern = Pattern.compile("src=([\"|\'| ])*?(http.*?\\.(gif|jpg|jpeg|bmp|png))\\1", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

		Matcher m = varPattern.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			int s = m.start();
			int e = m.end();
			sb.append(content.substring(lastEndIndex, s));
			String imagePath = m.group(2);
			String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
			String tempDir = "defaultTemp/";
			String local = Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + siteAlias + "/upload/Image/";
			File file = new File(local + tempDir);
			if (!file.exists()) {
				file.mkdirs();
			}

			String imageID = "";
			if (getRemoteFile(imagePath, local + tempDir + fileName)) {
				ZCImageSchema image = AutoUpload.dealImage(local, fileName, trans);
				if (StringUtil.isNotEmpty(image.getFileName())) {
					String uploadPath = Config.getContextPath() + Config.getValue("UploadDir") + "/" + siteAlias + "/" + image.getPath();
					imagePath = uploadPath.replaceAll("//", "/") + image.getFileName();
					logger.info("复制成功{}", m.group(2));
				} else {
					logger.error("复制失败{}", imagePath);
				}
				imageID = image.getID() + "";
			} else {
				logger.error("复制失败{}", imagePath);
			}
			sb.append("zcmsimagerela=\"" + imageID + "\" src=\"" + imagePath + "\"");
			lastEndIndex = e;
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	/**
	 * 保存文章历史版本，自动保存
	 */
	public void saveVersion() {
		String articleID = $V("ArticleID");
		long id = Long.parseLong(articleID);
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(id);

		if (!article.fill()) {
			article.setID(articleID);
			article.setValue(Request);
			long catalogID = Long.parseLong($V("CatalogID"));
			article.setCatalogID(catalogID);

			String siteID = CatalogUtil.getSiteID(catalogID);
			article.setSiteID(siteID);

			article.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			article.setHitCount(0);
			article.setPublishFlag("Y");
			article.setStickTime(0);

			article.setOrderFlag(OrderUtil.getDefaultOrder());
			article.setAddTime(new Date());
			article.setAddUser(User.getUserName());
		}

		Transaction trans = new Transaction();
		trans.add(article, Transaction.BACKUP);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.put("SaveTime", DateUtil.getCurrentDateTime());
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void topublish() {
		String ArticleID = $V("ArticleID");
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(ArticleID);
		if (!article.fill()) {
			Response.setLogInfo(0, "请先保存文档，再进行此项操作");
			return;
		}
		if ((article.getStatus() == Article.STATUS_DRAFT || article.getStatus() == Article.STATUS_EDITING) && article.getWorkFlowID() == 0) {
			ZCArticleSet set = article.query(new QueryBuilder("where workflowid is null and  status in (" + Article.STATUS_DRAFT + "," + Article.STATUS_EDITING + ") and id =" + ArticleID
					+ " or id in(select id from zcarticle where refersourceid =" + ArticleID + " )"));
			for (int i = 0; i < set.size(); i++) {
				set.get(i).setStatus(Article.STATUS_TOPUBLISH);
			}
			set.update();
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_TOPUBLISHARTICLE, "转为待发布操作成功,id:" + ArticleID, Request.getClientIP());
			Response.setLogInfo(1, "转为待发布操作成功。");
		} else if (article.getWorkFlowID() != 0) {
			Response.setLogInfo(0, "操作失败，此文章的状态为：" + STATUS_MAP.getString(article.getStatus() + "") + "，此文档在工作流转中，不能转为待发布");
		} else {
			Response.setLogInfo(0, "操作失败，此文章的状态为：" + STATUS_MAP.getString(article.getStatus() + "") + "，只有‘初稿’和‘重新编辑’的文章转为待发布状态了");
		}
	}

	/**
	 * 发布
	 * 
	 */
	public boolean publish() {
		ZCArticleSchema article = new ZCArticleSchema();
		int articleID = Integer.parseInt((String) Request.get("ArticleID"));

		ZCArticleSet set = new ZCArticleSet();
		article.setID(articleID);
		if (article.fill()) {
			Date FirstPubDate = article.getFirstPublishDate();
			Date PubDate = article.getPublishDate();
			if(FirstPubDate==null ){
				article.setFirstPublishDate(PubDate);
			}
			set.add(article);
		}
		ZCArticleSet referset = article.query(new QueryBuilder(" where refersourceid=? ", articleID));
		if (referset.size() > 0) {
			for (int i = 0; i < referset.size(); i++) {
				String catalogInnerCode = referset.get(i).getCatalogInnerCode();
				boolean hasPriv = Priv.getPriv(User.getUserName(), Priv.ARTICLE, catalogInnerCode, Priv.ARTICLE_MANAGE);
				String workflow = CatalogUtil.getWorkflow(referset.get(i).getCatalogID());
				// 如果目标栏目没有工作流且用户有目标栏目的文章管理权限，则发布时一起更新为发布状态
				if (hasPriv && StringUtil.isEmpty(workflow)) {
					set.add(referset.get(i));
				}
			}
		}

		// 更新发布状态
		Transaction trans = new Transaction();
		for (int i = 0; i < set.size(); i++) {
			Date date = new Date();// 必须更新modifyTime，全文检索以此为标记查找更新
			ZCArticleSchema article2 = set.get(i);
			QueryBuilder qb = new QueryBuilder("update zcarticle set modifyuser=?,modifytime=?,status=? where id=?");
			if (article2.getPublishDate() == null) {
				qb = new QueryBuilder("update zcarticle set publishdate=?,FirstPublishDate=?,modifyuser=?,modifytime=?,status=? where id=?");
				qb.add(date);
				qb.add(date);
//			}else{
				article2.setPublishDate(date);
				article2.setFirstPublishDate(date);
			}
			qb.add(User.getUserName());
			qb.add(date);
			if (article2.getPublishDate().getTime() > System.currentTimeMillis()) {
				qb.add(Article.STATUS_TOPUBLISH);
			} else {
				qb.add(Article.STATUS_PUBLISHED);
			}
			qb.add(article2.getID());
			qb.executeNoQuery();
			article2.setModifyUser(User.getUserName());
			article2.setModifyTime(date);
			article2.setStatus(Article.STATUS_PUBLISHED);
			trans.add(article2, Transaction.UPDATE);
		}

		UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_PUBLISHARTICLE, "发布文章:" + article.getTitle() + "成功！", Request.getClientIP(), trans);

		if (trans.commit()) {
			// 将生成文件的动作交给后台进程执行
			PublishMonitor m = new PublishMonitor();
			m.addJob(set);

			Response.setStatus(1);
			return true;
		} else {
			Response.setStatus(0);
			return false;
		}
	}

	/**
	 * 保存文章并发布
	 */
	public void saveAndPublish() {
		if (save()) {
			if (!publish()) {
				Response.setStatus(1);
				Response.setMessage("操作成功，" + Response.Message);
			}
		}
	}

	public void getArticle() {
		ZCArticleSchema article = new ZCArticleSchema();
		long articleID = Long.parseLong((String) Request.get("ArticleID"));
		article.setID(articleID);
		if (article.fill()) {
			String content = article.getContent();
			String[] pages = content.split(Constant.PAGE_BREAK, -1);
			// StringBuffer pageStr = new StringBuffer();
			// for (int i = 0; i < pages.length; i++) {
			// pageStr.append("'" + StringUtil.htmlEncode(pages[i]) + "'");
			// if (i != pages.length - 1) {
			// pageStr.append(",");
			// }
			// }

			Response.setStatus(1);
			Response.put("NewsType", article.getType());
			Response.put("TopFlag", article.getTopFlag());
			Response.put("CommentFlag", article.getCommentFlag());
			Response.put("Priority", article.getPriority());
			Response.put("TemplateFlag", article.getTemplate());
			Response.put("ShortTitle", article.getShortTitle());
			Response.put("Title", article.getTitle());
			Response.put("SubTitle", article.getSubTitle());
			Response.put("Keyword", article.getKeyword());
			Response.put("Summary", article.getSummary());
			Response.put("Template", article.getTemplate());
			Response.put("Author", article.getAuthor());
			Response.put("RedirectURL", article.getRedirectURL());
			Response.put("TagWord", article.getTag());

			Response.put("Pages", new Integer(pages.length));
			// Response.put("ContentPages", pageStr.toString());
			Response.put("ContentPages", pages);
		}
	}

	public void getPlayerName() {
		String relaImagePlayerID = $V("RelaImagePlayerID");
		String PlayerName = new QueryBuilder("select Name from zcimageplayer where ID = ?", Long.parseLong(relaImagePlayerID)).executeOneValue() + "";
		PlayerName = "[" + PlayerName + "]";
		Response.put("PlayerName", PlayerName);
	}

	/**
	 * 编辑器中插入图片
	 */
	public void getPicSrc() {
		String ids = $V("PicID");
		String customFlag = $V("Custom");
		String isRela = $V("isRela");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(ids) || !StringUtil.checkID(catalogID)) {
			return;
		}
		if (StringUtil.isEmpty(ids)) {
			ids = "''";
		}
		ZCImageSet imageSet = new ZCImageSchema().query(new QueryBuilder(" where id in (" + ids + ") order by id desc"));

		String path = Config.getValue("StaticResourcePath");
		StringBuffer images = new StringBuffer();
		// 分自定义图片和文章内容中的图片
		if (StringUtil.isNotEmpty(customFlag) && imageSet.size() > 0) {
			images.append(path + "/" + imageSet.get(0).getPath() + "1_" + imageSet.get(0).getFileName());
			this.Response.put("1_picSrc", images.toString());
			images = new StringBuffer();
			images.append(path + "/" + imageSet.get(0).getPath() + "s_" + imageSet.get(0).getFileName());
			this.Response.put("s_picSrc", images.toString());
			images = new StringBuffer();
			images.append(path + "/" + imageSet.get(0).getPath() + imageSet.get(0).getSrcFileName());
			this.Response.put("picSrc", images.toString());
		} else {
			for (int i = 0; i < imageSet.size(); i++) {
				if (StringUtil.isEmpty(isRela)) {
					images.append("<p style='text-align: center;'>");
					if ((StringUtil.isNotEmpty(Config.getValue("ArticleImageWidth")) && imageSet.get(i).getWidth() > Integer.parseInt(Config.getValue("ArticleImageWidth")))
							|| (StringUtil.isNotEmpty(Config.getValue("ArticleImageHeight")) && imageSet.get(i).getHeight() > Integer.parseInt(Config.getValue("ArticleImageHeight")))) {
						images.append("<a alt='点击查看大图' href='" + path + "/" + imageSet.get(i).getPath() + imageSet.get(i).getSrcFileName() + "' target='_blank' >");
					}
					images.append("<img border=0 zcmsimagerela='" + imageSet.get(i).getID() + "' alt='" + imageSet.get(i).getInfo() + "' src='" + path + "/" + imageSet.get(i).getPath() + "1_"
							+ imageSet.get(i).getFileName() + "'>");
					if ((StringUtil.isNotEmpty(Config.getValue("ArticleImageWidth")) && imageSet.get(i).getWidth() > Integer.parseInt(Config.getValue("ArticleImageWidth")))
							|| (StringUtil.isNotEmpty(Config.getValue("ArticleImageHeight")) && imageSet.get(i).getHeight() > Integer.parseInt(Config.getValue("ArticleImageHeight")))) {
						images.append("</a>");
					}
					images.append("</p>");
					images.append("<p style='text-align: center;'>" + imageSet.get(i).getName() + "</p>");
				} else {
					images.append("<img src='" + path + "/" + imageSet.get(i).getPath() + "s_" + imageSet.get(i).getFileName() + "' width='80' onload=\"if(this.width>80)this.width='80';\" >");
				}
			}
			this.Response.put("1_picSrc", images.toString());
			images = new StringBuffer();
			for (int i = 0; i < imageSet.size(); i++) {
				if (StringUtil.isEmpty(isRela)) {
					images.append("<p style='text-align: center;'>");
					if ((StringUtil.isNotEmpty(Config.getValue("ArticleImageWidth")) && imageSet.get(i).getWidth() > Integer.parseInt(Config.getValue("ArticleImageWidth")))
							|| (StringUtil.isNotEmpty(Config.getValue("ArticleImageHeight")) && imageSet.get(i).getHeight() > Integer.parseInt(Config.getValue("ArticleImageHeight")))) {
						images.append("<a alt='点击查看大图' href='" + path + "/" + imageSet.get(i).getPath() + imageSet.get(i).getSrcFileName() + "' target='_blank' >");
					}
					images.append("<img border=0 zcmsimagerela='" + imageSet.get(i).getID() + "' alt='" + imageSet.get(i).getInfo() + "' src='" + path + "/" + imageSet.get(i).getPath()  
							+ imageSet.get(i).getSrcFileName() + "'>");
					if ((StringUtil.isNotEmpty(Config.getValue("ArticleImageWidth")) && imageSet.get(i).getWidth() > Integer.parseInt(Config.getValue("ArticleImageWidth")))
							|| (StringUtil.isNotEmpty(Config.getValue("ArticleImageHeight")) && imageSet.get(i).getHeight() > Integer.parseInt(Config.getValue("ArticleImageHeight")))) {
						images.append("</a>");
					}
					images.append("</p>");
					images.append("<p style='text-align: center;'>" + imageSet.get(i).getName() + "</p>");
				} else {
					images.append("<img src='" + path + "/" + imageSet.get(i).getPath() + imageSet.get(i).getSrcFileName() + "' width='80' onload=\"if(this.width>80)this.width='80';\" >");
				}
			}
			this.Response.put("picSrc", images.toString());
		}
	}

	/**
	 * 编辑器中插入文件
	 */
	public void getFilePath() {
		String ids = $V("FileID");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(ids) || !StringUtil.checkID(catalogID)) {
			return;
		}
		if (StringUtil.isEmpty(ids)) {
			ids = "''";
		}
		DataTable dt = new QueryBuilder("select id,name,suffix,path,filename,srcfilename from zcattachment where id in (" + ids + ")").executeDataTable();

		StringBuffer filePath = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String path = Config.getContextPath() + Config.getValue("UploadDir") + "/" + Application.getCurrentSiteAlias();
			path = path.replaceAll("/+", "/");
			if (StringUtil.isNotEmpty(catalogID) && "N".equals(CatalogUtil.getAttachDownFlag(catalogID))) {
				filePath.append("<a href='" + (path + "/" + dt.getString(i, "path") + dt.getString(i, "filename")).replaceAll("/+", "/") + "' zcmsattachrela='" + dt.getString(i, "id") + "'>"
						+ dt.getString(i, "name") + "." + dt.getString(i, "suffix") + "</a>");
			} else if ("N".equals(SiteUtil.getAttachDownFlag(Application.getCurrentSiteID()))) {
				filePath.append("<a href='" + (path + "/" + dt.getString(i, "path") + dt.getString(i, "filename")).replaceAll("/+", "/") + "' zcmsattachrela='" + dt.getString(i, "id") + "'>"
						+ dt.getString(i, "name") + "." + dt.getString(i, "suffix") + "</a>");
			} else {
				filePath.append("<a href='" + Config.getContextPath() + "Services/AttachDownLoad.jsp?id=" + dt.getString(i, "id") + "' zcmsattachrela='" + dt.getString(i, "id") + "'>"
						+ dt.getString(i, "name") + "." + dt.getString(i, "suffix") + "</a>");
			}
		}
		this.Response.put("FilePath", filePath.toString());
	}

	/**
	 * 编辑器中插入Flash
	 */
	public void getFlashPath() {
		String ids = $V("FlashID");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(ids) || !StringUtil.checkID(catalogID)) {
			return;
		}
		if (StringUtil.isEmpty(ids)) {
			ids = "''";
		}
		String path = Config.getContextPath() + Config.getValue("UploadDir") + "/" + Application.getCurrentSiteAlias();
		path = path.replaceAll("/+", "/");
		DataTable dt = new QueryBuilder("select id,name,suffix,path,filename,srcfilename from zcattachment where id in (" + ids + ")").executeDataTable();

		StringBuffer flashs = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			flashs.append("<embed src='" + (path + "/" + dt.get(i, "Path") + dt.get(i, "FileName")).replaceAll("/+", "/")
					+ "' type='application/x-shockwave-flash' width='320' height='240' play='true' loop='true' menu='true'></embed>");
		}
		this.Response.put("FlashPath", flashs.toString());
	}

	/**
	 * 编辑器中插入视频
	 */
	public void getVideoPath() {
		String ids = $V("VideoID");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(ids) || !StringUtil.checkID(catalogID)) {
			return;
		}
		if (StringUtil.isEmpty(ids)) {
			ids = "''";
		}
		DataTable dt = new QueryBuilder("select id,name,suffix,path,filename,srcfilename,imageName from zcvideo where id in (" + ids + ")").executeDataTable();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String fileName = "../" + dt.getString(i, "path") + dt.getString(i, "filename");
			String imageName = "../" + dt.getString(i, "path") + dt.getString(i, "imageName");
			String playerPath = SiteUtil.getURL(CatalogUtil.getSiteID(catalogID)) + "/images/player.swf";
			fileName = fileName.replaceAll("/+", "/");
			imageName = imageName.replaceAll("/+", "/");
			playerPath = playerPath.replaceAll("http://", StringUtil.md5Hex("http://"));
			playerPath = playerPath.replaceAll("/+", "/");
			playerPath = playerPath.replaceAll(StringUtil.md5Hex("http://"), "http://");
			sb.append("<div style='text-align: center;'><embed wmode=\"transparent\" ");
			sb.append("flashvars=\"file=" + fileName + "&image=" + imageName + "&stretching=fill\"");
			sb.append(" zcmsvideorela=\"" + dt.getString(i, "id") + "\" src=\"" + playerPath + "\" quality=\"high\" allowfullscreen=\"true\" type=\"application/x-shockwave-flash\"");
			sb.append(" width=\"480\" height=\"360\"></embed><br/>");
			sb.append(dt.getString(i, "Name") + "<br/></div>");
		}
		this.Response.put("VideoPath", sb.toString());
	}

	public void getKeywordOrSummary() {
		String type = $V("Type");
		String title = $V("Title");
		String content = $V("Content");
		if ("Keyword".equals(type)) {
			$S("Text", StringUtil.join(IndexUtil.getKeyword(title + " " + content), " "));
		} else {
			$S("Text", IndexUtil.getTextAbstract(title, content));
		}
	}

	public void getMetaTitle() {
		String title = $V("Title");
		String tCatalogID = $V("CatalogID");
		String temp = "";
		// ZCCatalogSchema catalog = CMSCache.getCatalog(tCatalogID);
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(tCatalogID);
		catalog.fill();
		ZCSiteSchema tZCSiteSchema = new ZCSiteSchema();
		tZCSiteSchema.setID(catalog.getSiteID());
		tZCSiteSchema.fill();
		String tSiteName = tZCSiteSchema.getName();
		temp = catalog.getName();
		long tParentID = catalog.getParentID();
		while (tParentID != 0) {
			catalog = new ZCCatalogSchema();
			catalog.setID(tParentID);
			catalog.fill();
			temp = temp + "-" + catalog.getName();
			tParentID = catalog.getParentID();
		}
		String tMetaTitle = title + "-" + temp + "-" + tSiteName;
		$S("MetaTitle", tMetaTitle);
	}

	public static String getKeywordSetting(String catalogID) {
		String flag = CatalogUtil.getSchema(catalogID).getKeywordFlag();
		while (!"Y".equals(flag)) {
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			if (innerCode.length() > 6) {
				innerCode = innerCode.substring(0, innerCode.length() - 6);
				flag = CatalogUtil.getSchema(CatalogUtil.getIDByInnerCode(innerCode)).getKeywordFlag();
				return CatalogUtil.getSchema(CatalogUtil.getIDByInnerCode(innerCode)).getKeywordSetting();
			} else {
				return null;
			}
		}
		return CatalogUtil.getSchema(catalogID).getKeywordSetting();
	}

	/**
	 * 获得通过全文检索技术关联的文章的ID集合
	 */
	public void getAutoRelaIDs() {
		String catalogID = $V("CatalogID");
		String articleID = $V("ArticleID");
		String title = $V("Title");
		String content = $V("Content");
		String keyword = $V("Keyword");
		String setting = getKeywordSetting(catalogID);
		if (setting == null) {// 可以是空字符串
			Response.setError("不能进行智能相关文章匹配，请为本栏目开启“抽取关键词并自动关联文章”");
			return;
		}
		if (StringUtil.isEmpty(keyword)) {
			keyword = StringUtil.join(IndexUtil.getKeyword(title + " " + content), " ");
		}
		SearchResult sr = null;
		if (setting.equals("")) {// 没有设类别，则只在本栏目下匹配
			sr = ArticleRelaIndexer.getRelaArticles(catalogID, articleID, "", title, keyword);
		} else {
			sr = ArticleRelaIndexer.getRelaArticles(articleID, setting, title, keyword);
		}
		StringBuffer sb = new StringBuffer();
		if (sr != null && sr.Data != null) {
			for (int i = 0; i < sr.Data.getRowCount(); i++) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(sr.Data.getString(i, "ID"));
			}
		}
		$S("IDs", sb.toString());
	}

	public void getJSCode() {
		String ids = $V("ID");
		String catalogID = $V("CatalogID");
		if (!StringUtil.checkID(ids) || !StringUtil.checkID(catalogID)) {
			return;
		}
		if (StringUtil.isEmpty(ids)) {
			ids = "''";
		}
		ZCVoteSet set = new ZCVoteSchema().query(new QueryBuilder("where id in (" + ids + ")"));
		StringBuffer jsCodes = new StringBuffer();
		String levelStr = PubFun.getLevelStr(CatalogUtil.getDetailLevel(catalogID));
		for (int i = 0; i < set.size(); i++) {
			ZCVoteSchema vote = set.get(i);
			String code = "";
			code += "<div>调查：" + vote.getTitle() + "\n";
			code += "<!--" + vote.getTitle() + "-->\n";
			code += "<link href=\"" + levelStr + "images/vote.css\" type=\"text/css\" rel=\"stylesheet\" />";
			code += "<script language='javascript' src='" + levelStr + "images/vote.js'></script>";
			code += "<script language='javascript' src='" + (levelStr + "js/vote_" + vote.getID()).replaceAll("/+", "/") + ".js'></script>";
			code += "\n</div>";
			jsCodes.append(code);
			if (!new File((Config.getContextRealPath() + Config.getValue("Statical.TargetDir") + "/" + SiteUtil.getAlias(vote.getSiteID()) + "/js/").replaceAll("/+", "/") + "vote_" + vote.getID()
					+ ".js").exists()) {
				Vote.generateJS(vote.getID());
			}
		}
		$S("JSCode", jsCodes.toString());
	}

	public void verifySameTitle() {
		String Title = $V("Title");
		String CatalogID = $V("CatalogID");
		QueryBuilder qb = new QueryBuilder("select count(*) from ZCArticle where CatalogID=? and Title=? and ID<>?", Long.parseLong(CatalogID), Title);
		qb.add(Long.parseLong($V("ID")));
		int Count = qb.executeInt();
		Response.setStatus(Count);
	}
	
	/**
	 * 判断数组中是否存在重复的元素
	 * 
	 * @param array
	 * @return
	 */
	public boolean judgeRepeat(String array[])
	{
	for(int i=0; i<array.length; i++){
	    for(int j=i+1; j<array.length;j++){
	        if(array[i].equals(array[j]) ){
	            return true;
	        }
	    }
	}
	return false;

	}
}
