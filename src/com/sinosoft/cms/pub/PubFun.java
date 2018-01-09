package com.sinosoft.cms.pub;

import cn.com.sinosoft.util.Constant;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.site.ImagePlayerBasic;
import com.sinosoft.cms.site.ImagePlayerRela;
import com.sinosoft.cms.template.HtmlNameParser;
import com.sinosoft.cms.template.HtmlNameRule;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.MaxNoUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.PlatformCache;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCImagePlayerSchema;
import com.sinosoft.schema.ZCImagePlayerSet;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZDBranchSchema;
import com.sinosoft.schema.ZDDistrictSchema;
import com.sinosoft.schema.ZDRoleSchema;
import com.sinosoft.schema.ZDUserSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

/**
 * cms公共函数
 * 
 */
public class PubFun {

	private static final Logger logger = LoggerFactory.getLogger(PubFun.class);

	public static final String INDENT = "　"; // 缩进

	public static final String SEPARATE = "|";

	public static String getBranchOptions() { 
		return getBranchOptions(null);
	}

	public static String getGroupIDOptions() {
		return getGroupIDOptions(null, null);
	}

	public static String getSysCodeOptions() {
		return getSysCodeOptions(null);
	}

	public static String getCatTypeOptions() {
		return getCatTypeOptions(null);
	}

	public static String getCTValueOptions() {
		return getCTValueOptions(null);
	}

	/**
	 * 得到机构下拉框，供别的地方调用
	 * 
	 * @return
	 */
	public static String getBranchOptions(Object checkedValue) {
		// DataTable dt = new QueryBuilder(
		// "select Name,BranchInnerCode,TreeLevel from ZDBranch where BranchInnerCode like ? order by OrderFlag",
		// User.getBranchInnerCode() + "%").executeDataTable();
		DataTable dt = new QueryBuilder(
				"select Name,BranchInnerCode,TreeLevel from ZDBranch where BranchInnerCode like ? order by OrderFlag",
				"0001%").executeDataTable();
		PubFun.indentDataTable(dt);
		return HtmlUtil.dataTableToOptions(dt, checkedValue);
	}

	/**
	 * 得到字段分组下拉框，供别的地方调用
	 * 
	 * @return
	 */
	public static String getGroupIDOptions(Object checkedValue, String str) {
		DataTable dt = new QueryBuilder(
				"select name,id ,1 from ZDMetaColumnGroup where ModelID= ? ",
				str).executeDataTable();
		PubFun.indentDataTable(dt);
		return HtmlUtil.dataTableToOptions(dt, checkedValue.toString());
	}

	public static String getSysCodeOptions(Object checkedValue) {
		DataTable dt = new QueryBuilder(
				"select CodeName,CodeType,1 from ZDCode where ParentCode=?",
				"System").executeDataTable();
		PubFun.indentDataTable(dt);
		return HtmlUtil.dataTableToOptions(dt, checkedValue);
	}

	public static String getCatTypeOptions(Object checkedValue) {
		DataTable dt = new QueryBuilder(
				"select TypeName,id,1 from SCCatalogType ").executeDataTable();
		PubFun.indentDataTable(dt);
		return HtmlUtil.dataTableToOptions(dt, checkedValue);
	}

	public static String getCTValueOptions(Object checkedValue) {
		DataTable dt = new QueryBuilder(
				"select ValueName,ValueCode,1 from SCCatalogValue where CTId=?",
				checkedValue).executeDataTable();
		PubFun.indentDataTable(dt);
		return HtmlUtil.dataTableToOptions(dt, checkedValue);
	}

	public static List getRoleCodesByUserName(String userName) {
		String roles = (String) CacheManager.get(PlatformCache.ProviderName,
				"UserRole", userName);
		if (roles == null) {
			return null;
		}
		String[] arr = roles.split(",");
		ArrayList list = new ArrayList();
		for (int i = 0; i < arr.length; i++) {
			if (StringUtil.isNotEmpty(arr[i])) {
				list.add(arr[i]);
			}
		}
		return list;
	}

	public static String getRoleName(String roleCode) {
		ZDRoleSchema role = (ZDRoleSchema) CacheManager.get(
				PlatformCache.ProviderName, "Role", roleCode);
		if (role == null) {
			return null;
		}
		return role.getRoleName();
	}

	public static ZDBranchSchema getBranch(String innerCode) {
		return PlatformCache.getBranch(innerCode);
	}

	public static String getBranchName(String innerCode) {
		ZDBranchSchema branch = PlatformCache.getBranch(innerCode);
		if (branch == null) {
			branch = PlatformCache.getBranch("0001");
		}
		if (branch != null) {
			return branch.getName();
		}
		return null;
	}

	public static String getRoleNames(List roleCodes) {
		if (roleCodes == null || roleCodes.size() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		boolean first = false;
		for (int i = 0; i < roleCodes.size(); i++) {
			String roleName = getRoleName((String) roleCodes.get(i));
			if (StringUtil.isNotEmpty(roleName)) {
				if (first) {
					sb.append(",");
				}
				sb.append(roleName);
				first = true;
			}
		}
		return sb.toString();
	}

	/**
	 * 缩进dataTable中的第一列
	 * 
	 * @param dt
	 *            默认形式类似于 select name,id,level from table
	 */
	public static void indentDataTable(DataTable dt) {
		indentDataTable(dt, 0, 2, 1);
	}

	/**
	 * @param dt
	 * @param n
	 *            哪一列需要缩进
	 * @param m
	 *            根据哪一列缩进
	 * @param firstLevel
	 *            第一层级
	 * @return
	 */
	public static void indentDataTable(DataTable dt, int n, int m,
			int firstLevel) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			int level = Integer.parseInt(dt.getString(i, m));
			StringBuffer sb = new StringBuffer();
			for (int j = firstLevel; j < level; j++) {
				sb.append(PubFun.INDENT);
			}
			dt.set(i, n, sb.toString() + dt.getString(i, n));
		}
	}

	/**
	 * 当前页面路径
	 * 
	 * @param catalogID
	 * @return
	 */
	public static String getCurrentPage(long catalogID, int level, String name,
			String separator, String target) {
		String levelString = (level == 0) ? "./" : "";
		for (int i = 0; i < level; i++) {
			levelString += "../";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		long parentID = catalog.getParentID();

		String linkText = catalog.getName();
		String href = CatalogUtil.getLink(catalogID, levelString);
		String text = "";
		if (!"N".equals(catalog.getPublishFlag())) {
			text = "<span class=\"separator\">" + separator
					+ "</span><a href='" + href + "' target='" + target + "'>"
					+ linkText + "</a>";
		}
		if (parentID == 0) {
			text = "<a href='" + levelString + "' target='" + target
					+ "' ><span class=\"orange\">您所在的位置：首页</span></a>" + text;
		} else {
			text = getCurrentPage(parentID, level, name, separator, target)
					+ text;
		}
		return text;
	}

	/**
	 * 当前页面路径 列表页
	 * 
	 * @param catalogID
	 * @return
	 */
	public static String getCurrentPage(long catalogID, int level, String name,
			String separator, String target, String sign) {
		String levelString = (level == 0) ? "./" : "";
		for (int i = 0; i < level; i++) {
			levelString += "../";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		long parentID = catalog.getParentID();

		String linkText = catalog.getName();
		String href = CatalogUtil.getLink(catalogID, levelString);
		String text = "";
		String name1 = "";

		if ("S".equals(sign)) {

			if (!"N".equals(catalog.getPublishFlag())) {
				text = "<span class=\"separator\">" + separator
						+ "</span><a href='" + href + "' target='" + target
						+ "'>" + linkText + "</a>";
			}
		} else {
			if (!"N".equals(catalog.getPublishFlag())) {
				text = "<span class=\"separator\">" + separator
						+ "</span><span class=\"separator1\">" + linkText
						+ "</span>";
			}
		}
		if (parentID == 0) {
			text = "<a href='" + levelString + "' target='" + target
					+ "' ><span class=\"orange\">您的现在位置：首页</span></a>" + text;
		} else {
			text = getCurrentPage(parentID, level, name, separator, target, "S")
					+ text;
		}
		return text;
	}

	/**
	 * 
	 * @param article
	 * @return
	 */
	public static String getArticleURL(ZCArticleSchema article) {
		ZCCatalogSchema catalog = CatalogUtil.getSchema(article.getCatalogID());
		ZCSiteSchema site = SiteUtil.getSchema("" + article.getSiteID());
		return getArticleURL(site, catalog, article);
	}

	public static String getDocURL(DataRow doc) {
		ZCCatalogSchema catalog = CatalogUtil.getSchema(doc
				.getLong("CatalogID"));
		ZCSiteSchema site = SiteUtil.getSchema(doc.getLong("SiteID"));
		HtmlNameParser nameParser = new HtmlNameParser(site.toDataRow(),
				catalog.toDataRow(), doc, catalog.getDetailNameRule());
		HtmlNameRule h = nameParser.getNameRule();
		String url = h.getFullPath();
		if (url.indexOf(".") != -1) {
			url = url.substring(0, url.indexOf(".") + 1)
					+ SiteUtil.getExtensionType(site.getID());
		}
		return url;
	}

	public static String getArticleURL(ZCSiteSchema site,
			ZCCatalogSchema catalog, ZCArticleSchema article) {
		HtmlNameParser nameParser = new HtmlNameParser(site.toDataRow(),
				catalog.toDataRow(), article.toDataRow(), catalog
						.getDetailNameRule());
		HtmlNameRule h = nameParser.getNameRule();
		String url = h.getFullPath();
		if (url.indexOf(".") != -1) {
			url = url.substring(0, url.indexOf(".") + 1)
					+ SiteUtil.getExtensionType(site.getID());
		}
		return url;
	}

	public static String getGoodsURL(String ID) {
		DataTable dt = new QueryBuilder(
				"select catalogID from zsgoods where ID=?", ID)
				.executeDataTable();
		long catalogID = Long.parseLong(dt.get(0, "catalogID").toString());
		String url = CatalogUtil.getPath(catalogID) + ID + ".shtml";
		return url;
	}

	/**
	 * 根据图片ID得到图片的路径
	 * 
	 * @param imageID
	 * @return
	 */
	public static String getImagePath(String imageID, String imageIndex) {
		String imagePath = null;
		if (StringUtil.isEmpty(imageIndex)) {
			imageIndex = "src";
		}
		if (StringUtil.isEmpty(imageID)) {
			imageID = "0";
		}
		String imageSql = "select id,path,catalogID,filename,SrcFileName from zcimage where id=?";
		DataTable dtImage = new QueryBuilder(imageSql, imageID)
				.executeDataTable();
		if (dtImage != null && dtImage.getRowCount() > 0) {
			if ("src".equals(imageIndex)) {
				imagePath = dtImage.getString(0, "path")
						+ dtImage.getString(0, "SrcFileName");
			} else {
				imagePath = dtImage.getString(0, "path") + imageIndex + "_"
						+ dtImage.getString(0, "fileName");
			}
		}
		return imagePath;
	}

	public static String getImagePath(long imageID, String imageIndex) {
		return getImagePath(imageID + "", imageIndex);
	}

	public static String getImagePath(long imageID) {
		return getImagePath(imageID + "", null);
	}

	public static String getUserRealName(String userName) {
		ZDUserSchema user = PlatformCache.getUser(userName);
		if (user == null) {
			return "";
		} else {
			return user.getRealName();
		}
	}

	public static String getImagePath(String imageID) {
		return getImagePath(imageID, null);
	}

	public static String getImagePlayer(String ImagePlayCode) {
		ZCImagePlayerSchema imagePlayer = new ZCImagePlayerSchema();
		imagePlayer.setCode(ImagePlayCode);
		ZCImagePlayerSet set = imagePlayer.query();
		if (set.size() > 0) {
			imagePlayer = set.get(0);
		} else {
			logger.info("没有{}对应的图片播放器，请检查{}是否正确。", ImagePlayCode, ImagePlayCode);
		}
		return getImagePlayer(imagePlayer);
	}

	/**
	 * 获取图片播放器html代码
	 * 
	 * @param imagePlayer
	 * @return
	 */

	public static String getImagePlayer(ZCImagePlayerSchema imagePlayer) {
		String id = "flashcontent";
		String Alias = Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		Alias = Alias.replaceAll("///", "/");
		Alias = Alias.replaceAll("//", "/");
		StringBuffer pics = new StringBuffer();
		StringBuffer links = new StringBuffer();
		StringBuffer texts = new StringBuffer();
		pics.append("'");
		links.append("'");
		texts.append("'");
		DataTable dt = null;
		int shortTextLen = Integer
				.parseInt((imagePlayer.getWidth() / 10 - imagePlayer.getWidth() / 60 * 2)
						+ "");

		int imageCount = (int) imagePlayer.getDisplayCount();
		if (imageCount == 0) {
			imageCount = 6;
		}
		if (ImagePlayerBasic.IMAGESOURCE_CATALOG_FIRST.equals(imagePlayer
				.getImageSource())) {
			String catalogStr = " and cataloginnercode like '%'";
			if (StringUtil.isNotEmpty(imagePlayer.getRelaCatalogInnerCode())
					&& !"null".equalsIgnoreCase(imagePlayer
							.getRelaCatalogInnerCode())) {
				catalogStr = " and cataloginnercode like '"
						+ imagePlayer.getRelaCatalogInnerCode() + "%'";
			}

			String attributeSql = " and attribute like '%image%'";
			String typeStr = " order by publishdate desc,orderflag desc, id desc";
			dt = new QueryBuilder("select * from zcarticle where siteID=?"
					+ catalogStr + " and status in(" + Article.STATUS_TOPUBLISH
					+ "," + Article.STATUS_PUBLISHED
					+ ") and (publishdate<=? or publishdate is null) "
					+ attributeSql + typeStr, imagePlayer.getSiteID(),
					new Date()).executePagedDataTable(imageCount, 0);
			dt.insertColumns(new String[] { "FirstImagePath" });
			dealArticleMedia(dt, null, "image");

			for (int i = 0; i < dt.getRowCount(); i++) {
				if (i != 0) {
					pics.append(SEPARATE);
					links.append(SEPARATE);
					texts.append(SEPARATE);
				}
				String imagePath = "";
				if (StringUtil.isNotEmpty(dt.getString(i, "FirstImagePath"))) {
					imagePath = dt.getString(i, "FirstImagePath").substring(
							dt.getString(i, "FirstImagePath")
									.indexOf("upload/"));
				}
				pics.append(".." + Alias + imagePath);
				String siteUrl = SiteUtil.getURL(dt.getString(i, "SiteID"));
				if (siteUrl.endsWith("shtml")) {
					siteUrl = siteUrl.substring(0, siteUrl.lastIndexOf("/"));
				}

				if (!siteUrl.endsWith("/")) {
					siteUrl = siteUrl + "/";
				}

				links.append(siteUrl + getDocURL(dt.getDataRow(i)));
				String text = dt.getString(i, "ShortTitle");
				if (StringUtil.isEmpty(text)) {
					text = dt.getString(i, "Title");
				}
				if (text.length() > shortTextLen) {
					text = text.substring(0, shortTextLen) + "...";
				}
				texts.append(text);
			}

		} else {
			String sql = "select b.* from ZCImageRela a,zcimage b where a.id = b.id  and a.RelaID="
					+ imagePlayer.getID()
					+ " and a.RelaType='"
					+ ImagePlayerRela.RELATYPE_IMAGEPLAYER
					+ "' order by a.orderflag desc, a.addtime desc";
			dt = new QueryBuilder(sql).executePagedDataTable(imageCount, 0);

			for (int i = 0; i < dt.getRowCount(); i++) {
				if (i != 0) {
					pics.append(SEPARATE);
					links.append(SEPARATE);
					texts.append(SEPARATE);
				}
				if (StringUtil.isEmpty(imagePlayer.getOriginalPicture())
						|| "N".equalsIgnoreCase(imagePlayer
								.getOriginalPicture())) {
					pics.append(".." + Alias + dt.getString(i, "path") + "1_"
							+ dt.getString(i, "FileName"));
				} else {
					pics.append(".." + Alias + dt.getString(i, "path")
							+ dt.getString(i, "SrcFileName"));
				}
				if (StringUtil.isNotEmpty(Alias + dt.getString(i, "LinkURL"))) {
					links.append(dt.getString(i, "LinkURL"));
				}
				String text = dt.getString(i, "LinkText");
				if (StringUtil.isNotEmpty(text)) {
					if (text.length() > shortTextLen) {
						text = text.substring(0, shortTextLen) + "...";
					}
					texts.append(text);
				}
			}
		}
		pics.append("'");
		links.append("'");
		texts.append("'");

		StringBuffer sb = new StringBuffer();
		sb.append("<script type=\"text/javascript\" src=\""
				+ Config.getContextPath() + "Tools/Swfobject.js\"></script>\n");
		sb.append("<div id='" + id + "'>\n");
		sb.append("  This text is replaced by the Flash movie.\n");
		sb.append("</div>\n");
		sb.append("<script type='text/javascript'>\n");
		sb.append("var so=new SWFObject('" + Config.getContextPath()
				+ "Tools/ImagePlayer.swf','ImagePlayer1',"
				+ imagePlayer.getWidth() + "," + (imagePlayer.getHeight() + 22)
				+ ",'7','#FFFFFF','high');\n");
		sb.append("so.addVariable('wmode','transparent');\n");
		sb.append("so.addVariable('pics'," + pics.toString() + ");\n");
		sb.append("so.addVariable('links'," + links.toString() + ");\n");
		sb.append("so.addVariable('texts'," + texts.toString() + ");\n");
		sb.append("so.addVariable('borderwidth'," + imagePlayer.getWidth()
				+ ");\n");
		sb.append("so.addVariable('borderheight'," + imagePlayer.getHeight()
				+ ");\n");
		if ("Y".equals(imagePlayer.getIsShowText())) {
			sb.append("so.addVariable('textheight'," + 22 + ");\n");
			sb.append("so.addVariable('show_text','1');\n");
		} else {
			sb.append("so.addVariable('show_text','0');\n");
		}

		sb.append("so.addVariable('txtcolor','FFFF00');\n");
		sb.append("so.addVariable('overtxtcolor','FFFF00');\n");
		sb.append("so.addVariable('overtxtcolor','FFFF00');\n");
		// sb.append("so.addVariable('stop_time',3000);\n");
		sb.append("so.write('" + id + "');\n");
		sb.append("</script>");
		// System.out.println(sb);
		return sb.toString();
	}

	/**
	 * 处理文章相关附件
	 * 
	 * @param dt
	 */

	public static void dealArticleMedia(DataTable dt) {
		dealArticleMedia(dt, null, "image,attchment,video");
	}

	/**
	 * 处理文章相关附件
	 * 
	 * @param dt
	 * @param levelString
	 * @param hasAttribute
	 */

	public static void dealArticleMedia(DataTable dt, String levelString,
			String hasAttribute) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			dealArticleMedia(dt.getDataRow(i), levelString, hasAttribute);
		}
	}

	/**
	 * 处理文章相关附件
	 * 
	 * @param dr
	 */

	public static void dealArticleMedia(DataRow dr) {
		dealArticleMedia(dr, null, "image,attchment,video");
	}

	/**
	 * 处理文章相关附件
	 * 
	 * @param dr
	 * @param hasAttribute
	 */

	public static void dealArticleMedia(DataRow dr, String hasAttribute) {
		dealArticleMedia(dr, null, hasAttribute);
	}

	/**
	 * 处理文章相关附件
	 * 
	 * @param dr
	 * @param levelString
	 * @param hasAttribute
	 */

	public static void dealArticleMedia(DataRow dr, String levelString,
			String hasAttribute) {
		String attribute = dr.getString("Attribute");

		if (levelString == null || "null".equalsIgnoreCase(levelString)) {
			levelString = PubFun.getLevelStr(CatalogUtil.getDetailLevel(dr
					.getString("CatalogID")));
		}

		if (StringUtil.isNotEmpty(attribute)
				&& StringUtil.isNotEmpty(hasAttribute)) {
			// 处理图片新闻
			if (hasAttribute.indexOf("image") != -1) {
				if (attribute.indexOf("image") != -1) {
					DataTable imageRelaDT = new QueryBuilder(
							"select b.path,b.filename from zcimagerela a,zcimage b where "
									+ "a.relaID=? and a.relatype=? and a.ID=b.ID order by a.orderflag",
							dr.getLong("ID"), Article.RELA_IMAGE)
							.executeDataTable();
					if (imageRelaDT != null && imageRelaDT.getRowCount() > 0) {
						dr.set("FirstImagePath", levelString
								+ imageRelaDT.getString(0, "path") + "1_"
								+ imageRelaDT.getString(0, "filename"));
					} else {
						dr.set("FirstImagePath", levelString
								+ "upload/Image/nopicture.jpg");
					}
				} else {
					dr.set("FirstImagePath", levelString
							+ "upload/Image/nopicture.jpg");
				}
			}

			// 处理附件新闻
			// if (hasAttribute.indexOf("attchment")!=-1) {
			// if (attribute.indexOf("attchment")!=-1) {
			// DataTable attachRelaDT = new
			// QueryBuilder("select b.ID,b.Name,b.suffix,b.path,b.filename,b.imagepath from "
			// +
			// "zcattachmentrela a,zcattachment b where a.relaID=? and a.relatype=? and a.ID=b.ID"
			// , dr.getString("ID"), Article.RELA_ATTACH).executeDataTable();
			// if (attachRelaDT!=null && attachRelaDT.getRowCount()>0) {
			// dr.set("RelaAttach", attachRelaDT.getString(0, "name") + "." +
			// attachRelaDT.getString(0, "suffix"));
			// dr.set("RelaAttachImage", levelString + attachRelaDT.getString(0,
			// "imagepath"));
			// if (StringUtil.isNotEmpty(dr.getString("catalogID"))
			// &&
			// "N".equals(CatalogUtil.getAttachDownFlag(dr.getString("catalogID"))))
			// {
			// dr.set("RelaAttachPath", levelString + attachRelaDT.getString(0,
			// "path")
			// + attachRelaDT.getString(0, "filename"));
			// } else if
			// ("N".equals(SiteUtil.getAttachDownFlag(dr.getString("siteID"))))
			// {
			// dr.set("RelaAttachPath", levelString + attachRelaDT.getString(0,
			// "path")
			// + attachRelaDT.getString(0, "filename"));
			// } else {
			// dr.set("RelaAttachPath", Config.getValue("ServicesContext") +
			// "/AttachDownLoad.jsp?id="
			// + attachRelaDT.getString(0, "id"));
			// }
			// }
			// } else {
			// dr.set("RelaAttach", "");
			// dr.set("RelaAttachPath", "");
			// dr.set("RelaAttachImage", "");
			// }
			// }

			// 处理视频新闻
			if (hasAttribute.indexOf("video") != -1) {
				if (attribute.indexOf("video") != -1) {
					DataTable videoRelaDT = new QueryBuilder(
							"select ID from zcvideorela where relaID=? and relatype=?",
							dr.getLong("ID"), Article.RELA_VIDEO)
							.executeDataTable();
					if (videoRelaDT != null && videoRelaDT.getRowCount() > 0) {
						DataTable videoDT = new QueryBuilder(
								"select id,name,suffix,path,filename,srcfilename,imageName "
										+ "from zcvideo where id=?",
								videoRelaDT.getString(0, "ID"))
								.executeDataTable();
						if (videoDT != null && videoDT.getRowCount() > 0) {
							dr.set("FirstVideoImage", levelString
									+ videoDT.getString(0, "path")
									+ videoDT.getString(0, "imageName"));
							dr.set("FirstVideoHtml", getVideoHtml(videoDT
									.getString(0, "ID"), levelString));
						}
					}
				} else {
					dr.set("FirstVideoImage", "");
					dr.set("FirstVideoHtml", "");
				}
			}
		} else {
			dr.set("FirstImagePath", "");
			// dr.set("RelaAttach", "");
			// dr.set("RelaAttachPath", "");
			// dr.set("RelaAttachImage", "");
			dr.set("FirstVideoImage", "");
			dr.set("FirstVideoHtml", "");
		}
	}

	/**
	 * 根据视频ID获取播放视频的html代码
	 * 
	 * @param videoID
	 * @return
	 */

	public static String getVideoHtml(long videoID) {
		return getVideoHtml(videoID, null);
	}

	/**
	 * 根据视频ID获取播放视频的html代码
	 * 
	 * @param videoID
	 * @param levelString
	 * @return
	 */

	public static String getVideoHtml(long videoID, String levelString) {
		return getVideoHtml(videoID + "", levelString);
	}

	/**
	 * 根据视频ID获取播放视频的html代码
	 * 
	 * @param videoID
	 * @param levelString
	 * @return relaVideoHtml
	 */

	public static String getVideoHtml(String videoID, String levelString) {
		DataTable videoDT = new QueryBuilder(
				"select id,catalogID,name,suffix,path,filename,srcfilename,imageName "
						+ "from zcvideo where id=?", videoID)
				.executeDataTable();
		if (videoDT != null && videoDT.getRowCount() > 0) {
			return getVideoHtml(videoDT.getDataRow(0), levelString);
		} else {
			return "";
		}
	}

	public static String getVideoHtml(DataRow dr) {
		return getVideoHtml(dr, null);
	}

	public static String getVideoHtml(DataRow dr, String levelString) {
		if (StringUtil.isEmpty(levelString)
				|| "null".equalsIgnoreCase(levelString)) {
			levelString = getLevelStr(CatalogUtil.getDetailLevel(dr
					.getString("CatalogID")));
		}

		String files = "../" + dr.getString("path") + dr.getString("filename");
		String relaVideoHtml = "<script type='text/javascript' src='"
				+ levelString
				+ "images/Swfobject.js'></script><script type='text/javascript'>"
				+ "var s1 = new SWFObject('" + levelString
				+ "images/player.swf','player','270','225','9','#FFFFFF');"
				+ "s1.addParam('allowfullscreen','true');"
				+ "s1.addParam('allowscriptaccess','always');"
				+ "s1.addParam('flashvars','file=" + files + "');"
				+ "s1.write('container');</script>";
		return relaVideoHtml;
	}

	/**
	 * 根据地区代码得到省市区的下拉列表
	 */
	public static DataTable District = null;

	private static Mapx DistrictMap = null;

	private static Object mutex = new Object();

	public static void initDistrict() {
		District = new QueryBuilder(
				"select Name,Code,TreeLevel,Type from zddistrict")
				.executeDataTable();
		Mapx map = new Mapx();
		for (int i = 0; i < District.getRowCount(); i++) {
			map.put(District.get(i, 1), District.get(i, 0));
		}
		DistrictMap = map;
	}

	public static Mapx getDistrictMap() {
		if (DistrictMap == null) {
			synchronized (mutex) {
				if (DistrictMap == null) {
					initDistrict();
				}
			}
		}
		return DistrictMap;
	}

	public static DataTable getProvince() {
		if (District == null) {
			initDistrict();
		}
		return District.filter(new Filter() {
			public boolean filter(Object o) {
				DataRow dr = (DataRow) o;
				if ("1".equals(dr.get("TreeLevel"))) {
					return true;
				}
				return false;
			}
		});
	}

	public static DataTable getCity(String Province) {
		if (District == null) {
			initDistrict();
		}
		if (StringUtil.isNotEmpty(Province)) {
			ZDDistrictSchema district = new ZDDistrictSchema();
			district.setCode(Province);
			district.fill();
			if ("0".equals(district.getType())) {
				return District.filter(new Filter(Province) {
					public boolean filter(Object o) {
						DataRow dr = (DataRow) o;
						if (((String) this.Param).equals(dr.getString("Code"))) {
							return true;
						}
						return false;
					}
				});
			} else {
				return District.filter(new Filter(Province) {
					public boolean filter(Object o) {
						DataRow dr = (DataRow) o;
						if ("2".equals(dr.getString("TreeLevel"))
								&& dr.getString("Code").substring(0, 2).equals(
										((String) this.Param).substring(0, 2))) {
							return true;
						}
						return false;
					}
				});
			}
		} else {
			return new DataTable();
		}
	}

	public static DataTable getDistrict(String City) {
		if (District == null) {
			initDistrict();
		}
		if (StringUtil.isNotEmpty(City)) {
			ZDDistrictSchema district = new ZDDistrictSchema();
			district.setCode(City);
			district.fill();
			if ("0".equalsIgnoreCase(district.getType())) {
				return District.filter(new Filter(City) {
					public boolean filter(Object o) {
						DataRow dr = (DataRow) o;
						if ("3".equals(dr.get("TreeLevel").toString())
								&& dr.get("Code").toString().substring(0, 3)
										.equals(
												((String) this.Param)
														.substring(0, 3))) {
							return true;
						}
						return false;
					}
				});
			} else {
				return District.filter(new Filter(City) {
					public boolean filter(Object o) {
						DataRow dr = (DataRow) o;
						if ("3".equals(dr.getString("TreeLevel"))
								&& dr.get("Code").toString().substring(0, 4)
										.equals(
												((String) this.Param)
														.substring(0, 4))) {
							return true;
						}
						return false;
					}
				});
			}
		} else {
			return new DataTable();
		}
	}

	/**
	 * @param ext
	 *            文件后缀名
	 * @return
	 */
	public static boolean isAllowExt(String ext) {
		return isAllowExt(ext, "All");
	}

	/**
	 * @param ext
	 *            文件后缀名
	 * @param extType
	 *            后缀名类型 附件-Attach,图片-Image,视频-Video,音频-Audio,所有-All
	 * @return
	 */
	public static boolean isAllowExt(String ext, String fileType) {
		boolean allowed = false;
		if (StringUtil.isNotEmpty(ext)) {
			String[] extArr = null;
			ArrayList allowList = new ArrayList();
			String[] typeArr = new String[1];
			if (fileType.equalsIgnoreCase("All")) {
				typeArr = "Attach,Image,Video,Audio".split(",");
			} else {
				typeArr[0] = fileType;
			}
			for (int i = 0; i < typeArr.length; i++) {
				String allowExt = Config.getValue("Allow" + typeArr[i] + "Ext");
				if (StringUtil.isNotEmpty(allowExt)) {
					extArr = allowExt.split(",");
					for (int j = 0; j < extArr.length; j++) {
						allowList.add(extArr[j].toLowerCase());
					}
				}
			}
			allowed = allowList.contains(ext.toLowerCase());
		}
		return allowed;
	}

	public static String getLevelStr(long level) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < level; i++) {
			sb.append("../");
		}
		return sb.toString();
	}

	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	
	public static Date StringToDate(String str) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		return date;
	}
	
	/**
	 * 字符串转换到时间格式
	 * 
	 * @param dateStr
	 *            需要转换的字符串
	 * @param formatStr
	 *            需要格式的目标字符串 举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date StringToDate(String dateStr, String formatStr) {

		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return date;
	}
	
	/**
	 * @Title: getCurrentDateStandard.
	 * @Description: TODO(yyyy年-MM月-dd日).
	 * @return String    返回类型.
	 * @author CongZN.
	 */
	public static String getCurrentDateStandard() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(new Date());
	}
	
	public static String getCurrentYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(new Date());
	}

	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date());
	}

	public static String getCurrent() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	public static String getCurrentPaySnDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
		return sdf.format(new Date());
	}

	/**
	 *如果一个字符串数字中小数点后全为零，则去掉小数点及零
	 */
	public static String getInt(String Value) {
		String result = "";
		boolean mflag = true;
		int m = 0;
		m = Value.lastIndexOf(".");
		if (m == -1) {
			result = Value;
		} else {
			for (int i = m + 1; i <= Value.length() - 1; i++) {
				if (Value.charAt(i) != '0') {
					result = Value;
					mflag = false;
					break;
				}
			}
			if (mflag == true) {
				result = Value.substring(0, m);
			}
		}
		return result;
	}

	public static String cTrim(String Value) {
		if (Value == null) {
			Value = "";
		}
		return Value;
	}

	private static final Properties p = new Properties();
	static {
		try {
			InputStream ins = PubFun.class
					.getResourceAsStream("/OrderMessage.properties");
			p.load(ins);
			ins = null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static String GetOrderSn() {
		try {
			String OrderSn = p.getProperty("OrderSn");
			if ("test".equals(OrderSn)) {
				return "2013" + NoUtil.getMaxNo("OrderTestSn", 12);
			} else {
				return "2012" + NoUtil.getMaxNo("OrderSn", 12);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "2013" + NoUtil.getMaxNo("OrderTestSn", 12);
		}
	}
	
	public static String GetTBOrderSn(String Type){
		String tMaxNo = MaxNoUtil.JDMaxNo(Type, "SN");
		String strHeader = "tb";
		if ("TB".equals(Type)) {
			strHeader = "TB";
		}
		String SpecialNo = strHeader + PubFun.getCurrentDate().substring(0, 4)
				+ StringUtil.LCh(tMaxNo.toString(), "0", 10);

		return SpecialNo;
	}
	
	/**
	 * 积分兑换订单号
	 * @return
	 */
	public static String GetPointsOrderSn() {
		
		try {
			return "2014" + NoUtil.getMaxNo("PointOrderSn", 12);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "2013" + NoUtil.getMaxNo("PointOrderSn", 12);
		}
	}
	public static String getKeyValue(){
		
		return "WebFinance2011";
	}
	public static String GetWapSn(String NoType) {
		try {
			String OrderSn = p.getProperty("OrderSn");
			if ("test".equals(OrderSn)) {
				return "wptest" + NoUtil.getMaxNo(NoType, 10);
			} else {
				return "wp"+getCurrentDate().substring(0, 4) + NoUtil.getMaxNo(NoType, 10);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "wptest" + NoUtil.getMaxNo(NoType, 10);
		}
	}

	public static String GetPaySn() {
		try {
			String OrderSn = p.getProperty("OrderSn");
			if ("test".equals(OrderSn)) {
				return "WJ" + NoUtil.getMaxNo("PaySn", 17);
			} else {
				return "WJ" + NoUtil.getMaxNo("PaySn", 17);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "WJ" + NoUtil.getMaxNo("PaySn", 17);
		}
	}

	public static String GetInformationID(String OrderSn) {
		return OrderSn + "info" + NoUtil.getMaxNo("CmsInformationID", 12);

	}

	public static String GetOrderID(String OrderSn) {
		return OrderSn + "orde" + NoUtil.getMaxNo("CmsOrderID", 12);

	}

	public static String GetInformationAppntID(String OrderSn) {
		return OrderSn + "appn" + NoUtil.getMaxNo("CmsInforAppntID", 12);

	}

	public static String GetInformationInsuredID(String OrderSn) {
		return OrderSn + "insu" + NoUtil.getMaxNo("CmsInforInsuredID", 12);

	}

	public static String GetOrderItemOthID(String OrderSn) {
		return OrderSn + "iOth" + NoUtil.getMaxNo("CmsOrderItemOthID", 12);

	}

	public static String GetInformationInsuredElementsID(String OrderSn) {
		return OrderSn + "iele" + NoUtil.getMaxNo("CmsInforInsuredEleID", 12);

	}

	public static String GetInformationDutyID(String OrderSn) {
		return OrderSn + "duty" + NoUtil.getMaxNo("CmsInforDutyID", 12);

	}

	public static String GetOrderItemID(String OrderSn) {
		return OrderSn + "item" + NoUtil.getMaxNo("CmsOrderItemID", 12);

	}

	public static String GetInformationRiskTypeID(String OrderSn) {
		return OrderSn + "type" + NoUtil.getMaxNo("CmsInforRiskTypeID", 12);

	}

	public static String GetTradeInformationID(String OrderSn) {
		return OrderSn + "trade" + NoUtil.getMaxNo("CmsTradeID", 11);
	}
	
	public static String GetInformationPropID(String OrderSn) {
		return OrderSn + "prop" + NoUtil.getMaxNo("CmsInfoPropertyID", 12);

	}
	/**
	 * 取得一键变更的变更记录ID
	 * @return
	 */
	public static String GetChangeInfoID() {
		return getCurrentPaySnDate() + NoUtil.getMaxNo("CmsChangeInfoID", 12);
	}
	
	public static String GetTpySn() {
		try {
			String TpySn = p.getProperty("TpySn");
			if ("test".equals(TpySn)) {
				String prefix = p.getProperty("snprefix");
				if ((null != prefix) && !("".equals(prefix))) {
					return prefix + NoUtil.getMaxNo("TpyTestOrderSn", 9);
				} else {
					return "tpytest" + NoUtil.getMaxNo("TpyTestOrderSn", 9);
				}
			} else {
				return "tpy" + NoUtil.getMaxNo("TpyOrderSn", 13);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "tpytest" + NoUtil.getMaxNo("TpyTestOrderSn", 9);
		}
	}

	public static String getHTSN(String riskcode) {
		String hTSn = p.getProperty("hTSn");
		if ("test".equals(hTSn)) {
			String prefix = p.getProperty("snprefix");
			if ((null != prefix) && !("".equals(prefix))) {
				return prefix + NoUtil.getMaxNo("TpyTestOrderSn", 9);
			} else {
				return "tpytest" + NoUtil.getMaxNo("TpyTestOrderSn", 9);
			}
		} else {
			return NoUtil.HtSerialNO(riskcode);
		}
	}
	
	public static String randomChar() {

		Random r1 = new Random();
		return StringUtil.leftPad(r1.nextInt(9999) + "", '0', 4);
		
	}

	public static String GetSDInformationSn() {

		return "2013" + randomChar() + "" + NoUtil.getMaxNo("FDINFORMATIONNO", 12);

	}

	public static String GetSDAppntSn() {

		return "2013" + randomChar() + "" + NoUtil.getMaxNo("SDANNPNTNO", 12);

	}

	public static String GetSDInsuredSn() {

		return "2013" + randomChar() + "" +  NoUtil.getMaxNo("SDINSUREDNO", 12);

	}

	public static String GetSDElementSn() {

		return "2013" + randomChar() + "" +  NoUtil.getMaxNo("ELEMENTNO", 12);

	}

	public static String GetItemNo() {

		return "2013" + randomChar() + "" +  NoUtil.getMaxNo("SDITEMNO", 12);

	}

	public static String GetItemOthNo() {

		return "2013" + randomChar() + "" +  NoUtil.getMaxNo("SDITEMTHONO", 12);

	}
	 

	public static String GetInsSeriNo(String orderSn) {

		return orderSn + "_" + NoUtil.getMaxNo("INSSERINO", 3);

	}

	public static String GetPaymentNO() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return sdf.format(new Date()) + NoUtil.getMaxNo("PaymentNO", 12);
	}
	
	public static String getImagePlayer1(ZCImagePlayerSchema imagePlayer) {
		String id = "flashcontent";
		StringBuffer sb = new StringBuffer();
		String Alias = Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		Alias = Alias.replaceAll("///", "/");
		Alias = Alias.replaceAll("//", "/");
		StringBuffer pics = new StringBuffer();
		StringBuffer links = new StringBuffer();
		StringBuffer texts = new StringBuffer();
		pics.append("'");
		links.append("'");
		texts.append("'");
		DataTable dt = null;
		int shortTextLen = Integer
				.parseInt((imagePlayer.getWidth() / 10 - imagePlayer.getWidth() / 60 * 2)
						+ "");

		int imageCount = (int) imagePlayer.getDisplayCount();
		if (imageCount == 0) {
			imageCount = 6;
		}
		if (ImagePlayerBasic.IMAGESOURCE_CATALOG_FIRST.equals(imagePlayer
				.getImageSource())) {
			String catalogStr = " and cataloginnercode like '%'";
			if (StringUtil.isNotEmpty(imagePlayer.getRelaCatalogInnerCode())
					&& !"null".equalsIgnoreCase(imagePlayer
							.getRelaCatalogInnerCode())) {
				catalogStr = " and cataloginnercode like '"
						+ imagePlayer.getRelaCatalogInnerCode() + "%'";
			}

			String attributeSql = " and attribute like '%image%'";
			String typeStr = " order by publishdate desc,orderflag desc, id desc";
			dt = new QueryBuilder("select * from zcarticle where siteID=?"
					+ catalogStr + " and status in(" + Article.STATUS_TOPUBLISH
					+ "," + Article.STATUS_PUBLISHED
					+ ") and (publishdate<=? or publishdate is null) "
					+ attributeSql + typeStr, imagePlayer.getSiteID(),
					new Date()).executePagedDataTable(imageCount, 0);
			dt.insertColumns(new String[] { "FirstImagePath" });
			dealArticleMedia(dt, null, "image");

			for (int i = 0; i < dt.getRowCount(); i++) {
				if (i != 0) {
					pics.append(SEPARATE);
					links.append(SEPARATE);
					texts.append(SEPARATE);
				}
				String imagePath = "";
				if (StringUtil.isNotEmpty(dt.getString(i, "FirstImagePath"))) {
					imagePath = dt.getString(i, "FirstImagePath").substring(
							dt.getString(i, "FirstImagePath")
									.indexOf("upload/"));
				}
				pics.append(".." + Alias + imagePath);
				String siteUrl = SiteUtil.getURL(dt.getString(i, "SiteID"));
				if (siteUrl.endsWith("shtml")) {
					siteUrl = siteUrl.substring(0, siteUrl.lastIndexOf("/"));
				}

				if (!siteUrl.endsWith("/")) {
					siteUrl = siteUrl + "/";
				}

				links.append(siteUrl + getDocURL(dt.getDataRow(i)));
				String text = dt.getString(i, "ShortTitle");
				if (StringUtil.isEmpty(text)) {
					text = dt.getString(i, "Title");
				}
				if (text.length() > shortTextLen) {
					text = text.substring(0, shortTextLen) + "...";
				}
				texts.append(text);
			}

		} else {
			String sql = "select b.* from ZCImageRela a,zcimage b where a.id = b.id  and a.RelaID="
					+ imagePlayer.getID()
					+ " and a.RelaType='"
					+ ImagePlayerRela.RELATYPE_IMAGEPLAYER
					+ "' order by a.orderflag desc, a.addtime desc";
			dt = new QueryBuilder(sql).executePagedDataTable(imageCount, 0);
			sb.append("<div class=\"ad_jiaodian\">\n");
			sb.append("<div class=\"changeBox_a1\" id=\"change_33\">\n");
			String sql1 = "select * from zdconfig where Type=?";
			QueryBuilder qb1 = new QueryBuilder(sql1, "StaticResourcePath");
			DataTable dt1 = qb1.executeDataTable();
			String sql2 = "select * from ZCImagePlayer where ID="
					+ imagePlayer.getID() + "";
			QueryBuilder qb2 = new QueryBuilder(sql2);
			DataTable dt2 = qb2.executeDataTable();
			String Alias1 = "/";
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (i != 0) {
					pics.append(SEPARATE);
					links.append(SEPARATE);
					texts.append(SEPARATE);
				}
				String imgpath = "";
				String imglink = "";
				if (StringUtil.isEmpty(imagePlayer.getOriginalPicture())
						|| "N".equalsIgnoreCase(imagePlayer
								.getOriginalPicture())) {
					imgpath = dt1.getString(0, "Value") + Alias1
							+ dt.getString(i, "path") + "s_"
							+ dt.getString(i, "FileName");
				} else {
					imgpath = dt1.getString(0, "Value") + Alias1
							+ dt.getString(i, "path") + "1_"
							+ dt.getString(i, "FileName");
				}
				if (StringUtil.isNotEmpty(Alias + dt.getString(i, "LinkURL"))) {
					imglink = dt.getString(i, "LinkURL");
				}
				String text = "";
				sb.append("<div class=\"changeDiv\">\n");
				if ("Y".equals(imagePlayer.getIsShowText())) {
					text = dt.getString(i, "Info");
					sb.append("<h3><a href=\" " + imglink
							+ " \"  target=\"_blank\">" + text + "</a></h3>");
				}
				sb.append("<a href=\"" + imglink
						+ "\"  target=\"_blank\"><img src=\"" + imgpath
						+ "\" width=\"" + dt2.getString(0, "Width")
						+ "\" height=\"" + dt2.getString(0, "Height")
						+ "\" alt=\"\" /></a>");
				sb.append("</div>\n");
			}
			if (dt.getRowCount() > 0) {
				sb.append("<ul class=\"ul_change_a2\">\n");
				for (int i = 0; i < dt.getRowCount(); i++) {

					sb.append("<li><span>" + (i + 1) + "</span></li>\n");

				}
				sb.append("</ul>\n");
			}
			sb.append("</div>\n");
			sb.append("</div>\n");
		}
		// pics.append("'");
		// links.append("'");
		// texts.append("'");
		// StringBuffer sb = new StringBuffer();
		// for (int i = 0; i < dt.getRowCount(); i++)
		// {
		// sb.append("<div class=\"changeDiv\">\n");
		// sb.append("<h3><a href=\" " + links.toString() +
		// " \"  target=\"_blank\">"+ texts.toString() +"</a></h3>");
		// sb.append("<a href=\""+links.toString()+"\"  target=\"_blank\"><img src=\""+pics.toString()+"\" width=\"580\" height=\"260\" alt=\"\" /></a>");
		// sb.append("</div>\n");
		// }
		// System.out.println(sb);
		// sb.append("<script type=\"text/javascript\" src=\"" +
		// Config.getContextPath() + "Tools/Swfobject.js\"></script>\n");
		// sb.append("<div id='" + id + "'>\n");
		// sb.append("  This text is replaced by the Flash movie.\n");
		// sb.append("</div>\n");
		// sb.append("<script type='text/javascript'>\n");
		// sb.append("var so=new SWFObject('" + Config.getContextPath() +
		// "Tools/ImagePlayer.swf','ImagePlayer1'," + imagePlayer.getWidth() +
		// "," + (imagePlayer.getHeight() + 22)
		// + ",'7','#FFFFFF','high');\n");
		// sb.append("so.addVariable('wmode','transparent');\n");
		// sb.append("so.addVariable('pics'," + pics.toString() + ");\n");
		// sb.append("so.addVariable('links'," + links.toString() + ");\n");
		// sb.append("so.addVariable('texts'," + texts.toString() + ");\n");
		// sb.append("so.addVariable('borderwidth'," + imagePlayer.getWidth() +
		// ");\n");
		// sb.append("so.addVariable('borderheight'," + imagePlayer.getHeight()
		// + ");\n");
		// if ("Y".equals(imagePlayer.getIsShowText())) {
		// sb.append("so.addVariable('textheight'," + 22 + ");\n");
		// sb.append("so.addVariable('show_text','1');\n");
		// } else {
		// sb.append("so.addVariable('show_text','0');\n");
		// }
		// sb.append("so.addVariable('txtcolor','FFFF00');\n");
		// sb.append("so.addVariable('overtxtcolor','FFFF00');\n");
		// sb.append("so.addVariable('overtxtcolor','FFFF00');\n");
		// // sb.append("so.addVariable('stop_time',3000);\n");
		// sb.append("so.write('" + id + "');\n");
		// sb.append("</script>");
		// System.out.println(sb);
		return sb.toString();
	}

	/**
	 * 获取支付流水号
	 * 
	 * @return
	 */
	public static String GetPaySn(String money , String DG) {
		String PaysnDate = getCurrentPaySnDate();
		try {
			// 转换金额为分
			if (StringUtil.isNotEmpty(money)) {
				money = changeFen(new BigDecimal(money));
				money = StringUtil.leftPad(money, '0', 7);
			} else {
				money = "0000000";
			}

			String OrderSn = p.getProperty("OrderSn");
			if ("test".equals(OrderSn)) {
				return "W" + PaysnDate + NoUtil.getMaxNo("PaySn" + PaysnDate, 7) + money + DG;
			} else {
				return "W" + PaysnDate + NoUtil.getMaxNo("PaySn" + PaysnDate, 7) + money + DG;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "W" + NoUtil.getMaxNo("PaySn" + PaysnDate, 7) + money + DG;
		}

	}

	/**
	 * 获取变更订单的支付流水号
	 * @return
	 */
	public static String GetBGPaySn() {
		return "BG" + DateUtil.getCurrentDate("yyyyMM") + NoUtil.getMaxNo("PaySn" + getCurrentPaySnDate(), 12);
	}
	
	/**
	 * 取得订单表ID
	 * @return
	 */
	public static String GetOrderID() {
		return getCurrentPaySnDate() + NoUtil.getMaxNo("OrderID", 10);
	}
	
	/**
	 * 获取支付流水号
	 * 
	 * @return
	 */
	public static String GetYlPaySn(String DG) {
		String PaysnDate = getCurrentPaySnDate();
		return PaysnDate + NoUtil.getMaxNo("PaySn" + PaysnDate, 10) + DG;
	}
	
	public static void main(String[] args) {
//		System.out.println(getEvaliDate(DateUtil.parse("2016-07-18 00:00:00", "yyyy-MM-dd HH:mm:ss"),"180","D"));
//		String paysn = GetPaySn("1.23","W");
//		System.out.println(paysn);
		/*System.out.println(replacePaySn("12345.67",paysn));
		System.out.println(getPaySnUnion(paysn));
		System.out.println(getPaySnLast(paysn));
		System.out.println(GetWapSn("WPOrderSn"));*/
		
	}

	/**
	 * 转换钱为分
	 * 
	 * @param money
	 * @return
	 */
	public static String changeFen(BigDecimal money) {
		money = money.setScale(2, BigDecimal.ROUND_HALF_UP);
		String mm = money.divide(new BigDecimal("0.01")).toString();
		return  StringUtil.leftPad(mm, '0', 7);
	}

	/**
	 * 根据钱数替换流水号
	 * 
	 * @return
	 */
	public static String replacePaySn(String money, String paySn) {
		if (StringUtil.isEmpty(paySn) || paySn.length() != 20) {
			return paySn;
		}
		
		return getPaySnUnion(paySn) +changeFen(new BigDecimal(money)) + getPaySnLast(paySn);
	}
	
	/**
	 * 获取主要标识
	 * @return
	 */
	public static String getPaySnUnion(String paySn){
		return paySn.substring(0, 12);
	}
	
	/**
	 * 获取最后一位标识
	 * @return
	 */
	public static String getPaySnLast(String paySn){
		return paySn.substring(19, 20);
	}

	/**
	 * 获取公告ID
	 */
	public static String getPubInfoID() {
		String id = String.valueOf(NoUtil.getMaxID("PubInfoID"));
		if (id.length() > 15) {
			return id.substring(0, 15);
		}
		return StringUtil.leftPad(id, '0', 15);
	}
	
	/**
	 * 根据生效日期算出积分失效日期
	 * 
	 * @param svaliDate
	 * @param count
	 * @return
	 */
	public static Date getPointEvaliDate(Date svaliDate) {
		String PointValidityPeriod = Config.getValue("PointValidityPeriod");
		if (StringUtil.isEmpty(PointValidityPeriod)) {
			PointValidityPeriod = "2Y";
		}
		int count = Integer.valueOf(PointValidityPeriod.substring(0,
				PointValidityPeriod.length() - 1));
		String unit = PointValidityPeriod.substring(PointValidityPeriod
				.length() - 1);
		if ("Y".equalsIgnoreCase(unit)) {
			// 取得该月最后一天
			return DateUtil.getLastDayOfMonth(DateUtil.toString(DateUtil
					.addYear(svaliDate, count)) + " 23:59:59");
		} else if ("M".equalsIgnoreCase(unit)) {
			return DateUtil.addMonth(svaliDate, count);
		} else if ("D".equalsIgnoreCase(unit)) {
			return DateUtil.addDay(svaliDate, count);
		} else {
			logger.error("计算积分失效日期错误！有效期设置错误：{}", PointValidityPeriod);
			return svaliDate;
		}
	}
	
	/**
	 * 取得VIP到期时间
	 * 
	 * @return
	 */
	public static String getVIPExpireDate() {
		String VIPValidityPeriod = Config.getValue("VIPValidityPeriod");
		if (StringUtil.isEmpty(VIPValidityPeriod)) {
			VIPValidityPeriod = "1Y";
		}
		Date date = new Date();
		int count = Integer.valueOf(VIPValidityPeriod.substring(0,
				VIPValidityPeriod.length() - 1));
		String unit = VIPValidityPeriod
				.substring(VIPValidityPeriod.length() - 1);
		String expireDate = "";
		if ("Y".equalsIgnoreCase(unit)) {
			expireDate = DateUtil.toDateTimeString(DateUtil
					.addYear(date, count));
		} else if ("M".equalsIgnoreCase(unit)) {
			expireDate = DateUtil.toDateTimeString(DateUtil.addMonth(date,
					count));
		} else if ("D".equalsIgnoreCase(unit)) {
			expireDate = DateUtil
					.toDateTimeString(DateUtil.addDay(date, count));
		}
		return expireDate;
	}
	
	/**
	 * 根据起保日期、保障期限、保障期限类型计算失效日期
	 * 
	 * @param svalidate
	 *            起保日期
	 * @param ensureLimit
	 *            保障期限
	 * @param ensureLimitType
	 *            保障期限类型
	 * @return 失效日期
	 */
	public static String getEvaliDate(Date svalidate, String ensureLimit,
			String ensureLimitType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cdS = Calendar.getInstance();
		int limitInt = 1;
		if (StringUtil.isNotEmpty(ensureLimit)) {
			limitInt = Integer.valueOf(ensureLimit);
		} else {
			return null;
		}
		try {
			cdS.setTime(sdf.parse(sdf.format(svalidate)));
			if ("Y".equals(ensureLimitType.trim())) {
				cdS.add(Calendar.YEAR, limitInt);
				cdS.add(Calendar.DATE, -1);

			} else if ("M".equals(ensureLimitType.trim())) {
				cdS.add(Calendar.MONTH, limitInt);
				cdS.add(Calendar.DATE, -1);

			} else if ("D".equals(ensureLimitType.trim())) {
				cdS.add(Calendar.DATE, limitInt);
				cdS.add(Calendar.DATE, -1);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return null;
		}
		return DateUtil.toString(cdS.getTime()) + " 23:59:59";
	}
	
	/**
	 * 
	 * errMsg:(拼装异常信息). <br/>
	 * 
	 * @author 郭斌
	 * @param errorMsg
	 * @return
	 */
	public static Map<String, Object> errMsg(String errorMsg) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.STATUS, Constant.FAIL);
		result.put(Constant.MSG, errorMsg);
		return result;

	}
	
	/**
	 * 
	 * errMsg:(拼装异常信息和错误码). <br/>
	 * 
	 * @author 李景川
	 * @param errorMsg
	 * @return
	 */
	public static Map<String, Object> errMsg(String errorCode , String errorMsg) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.STATUS, Constant.FAIL);// 接口状态
		result.put(Constant.RESULTCODE, errorCode);// 业务状态
		result.put(Constant.RESULTMESSAGE, errorMsg);
		result.put(Constant.MSG, errorMsg);
		
		return result;

	}
	
	/**
	 * 
	 * errMsg:(拼装成功信息和成功码). <br/>
	 * 
	 * @author 李景川
	 * @param errorMsg
	 * @return
	 */
	public static Map<String, Object> sucMsg(String code , String msg) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.STATUS, Constant.SUCCESS);// 接口状态
		result.put(Constant.RESULTCODE, code);// 业务状态
		result.put(Constant.RESULTMESSAGE, msg);
		result.put(Constant.MSG, msg);
		return result;


	}

	/**
	 * 
	 * errMsg:(拼装异常信息). <br/>
	 * 
	 * @author 郭斌
	 * @param errorMsg
	 * @return
	 */
	public static Map<String, Object> errMsg(String errorMsg, Object data) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.STATUS, Constant.FAIL);
		result.put(Constant.MSG, errorMsg);
		result.put(Constant.DATA, data);
		return result;

	}

	/**
	 * 
	 * errMsg:(拼装成功信息). <br/>
	 * 
	 * @author 郭斌
	 * @param errorMsg
	 * @return
	 */
	public static Map<String, Object> sucMsg() {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.STATUS, Constant.SUCCESS);
		return result;

	}

	/**
	 * 
	 * errMsg:(拼装成功信息). <br/>
	 * 
	 * @author 郭斌
	 * @param errorMsg
	 * @return
	 */
	public static Map<String, Object> sucMsg(Object data) {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.STATUS, Constant.SUCCESS);
		result.put(Constant.DATA, data);
		return result;
	}
	
	/**
	 * getUUID:(获取UUID). <br/>
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
