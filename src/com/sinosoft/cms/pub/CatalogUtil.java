package com.sinosoft.cms.pub;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.cms.site.CatalogConfig;
import com.sinosoft.cms.template.HtmlNameParser;
import com.sinosoft.cms.template.HtmlNameRule;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCCatalogConfigSchema;
import com.sinosoft.schema.ZCCatalogConfigSet;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCSiteSchema;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 缓存管理栏目的相关信息
 * 
 */

public class CatalogUtil {

	static {
		CatalogConfig.initCatalogConfig();
	}

	/**
	 * 根据ID取Schema
	 */
	public static ZCCatalogSchema getSchema(String catalogID) {
		return CMSCache.getCatalog(catalogID);
	}

	public static ZCCatalogSchema getSchema(long catalogID) {
		return getSchema("" + catalogID);
	}

	/**
	 * 根据ID取栏目名称
	 */
	public static String getName(String catalogID) {
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog == null) {
			return null;
		}
		return catalog.getName();
	}

	/**
	 * 根据ID取栏目名称
	 */
	public static String getName(long catalogID) {
		return getName(catalogID + "");
	}
	
	public static String getFullName(String catalogID,String separete) {
		StringBuffer sb = new StringBuffer();
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog == null) {
			return null;
		}
		
		if(catalog.getParentID() != 0){
			sb.append(getFullName(catalog.getParentID()+"",separete));
		}
		if(sb.toString().length()>0){
			sb.append(separete);
		}
		
		sb.append(catalog.getName());
		
		return sb.toString();
	}
	
	public static String getFullName(long catalogID,String separete){
		return getFullName(catalogID+"",separete);
	}
	
	public static String getFullName(long catalogID){
		return getFullName(catalogID+"","/");
	}

	/**
	 * 根据InnerCode取栏目名称
	 */
	public static String getNameByInnerCode(String catalogInnerCode) {
		ZCCatalogSchema catalog = CMSCache.getCatalogByInnerCode(catalogInnerCode);
		if (catalog == null) {
			return null;
		}
		return catalog.getName();
	}

	/**
	 * 取形如 "新闻动态/国际新闻/中东局势"的 中东局势 栏目的id
	 */
	public static String getIDByNames(String names) {
		return getIDByNames(Application.getCurrentSiteID(), names);
	}

	/**
	 * 取形如 "新闻动态/国际新闻/中东局势"的 中东局势 栏目的id
	 */
	public static String getIDByNames(long siteID, String names) {
		return getIDByNames(siteID + "", names);
	}

	/**
	 * 取形如 "新闻动态/国际新闻/中东局势"的 中东局势 栏目的id
	 */
	public static String getIDByNames(String siteID, String names) {
		if (StringUtil.isEmpty(names)) {
			return null;
		}
		if (names.startsWith("/")) {
			names = names.substring(1);
		}
		if (names.endsWith("/")) {
			names = names.substring(0, names.length() - 1);
		}
		String[] catalogNames = names.split("/");
		int catalogLenth = catalogNames.length;
		String id = "";
		if (catalogLenth > 0) {
			if (catalogLenth > 1) {
				String catalogStr = StringUtil.join(catalogNames, "_");
				id = getCatalogIDByNames(siteID, catalogStr);
			} else {
				id = getCatalogIDByNames(siteID, catalogNames[0]);
			}
		}
		return id;
	}

	private static String getCatalogIDByNames(String siteID, String names) {
		if (StringUtil.isEmpty(names)) {
			return null;
		}
		if (names.startsWith("_")) {
			names = names.substring(1);
		}
		if (names.endsWith("_")) {
			names = names.substring(0, names.length() - 1);
		}
		long catalogID = 0;
		String[] catalogNames = names.split("_");
		if (catalogNames.length <= 0) {
			return null;
		} else if (catalogNames.length == 1) {
			ZCCatalogSchema catalog = null;
			if (StringUtil.isDigit(catalogNames[0])) {
				catalog = CMSCache.getCatalog(Long.parseLong(catalogNames[0]));
			}
			
			if (catalog == null) {
				catalog = CMSCache.getCatalog(siteID, catalogNames[0]);
			}
			
			if (catalog == null) {
				return null;
			}
			catalogID = catalog.getID();
		} else if (catalogNames.length > 1) {
			for (int i = 0; i < catalogNames.length; i++) {
				if (i == 0) {
					ZCCatalogSchema catalog = null;
					if (StringUtil.isDigit(catalogNames[i])) {
						catalog = CMSCache.getCatalog(Long.parseLong(catalogNames[i]));
					}
					
					if (catalog == null) {
						catalog = CMSCache.getCatalog(siteID, catalogNames[i]);
					}
					
					if (catalog == null) {
						return null;
					}
					catalogID = catalog.getID();
				} else {
					ZCCatalogSchema catalog = null;
					if (StringUtil.isDigit(catalogNames[i])) {
						catalog = CMSCache.getCatalog(Long.parseLong(catalogNames[i]));
					}
					
					if (catalog == null) {
						catalog = CMSCache.getCatalog(siteID, catalogID, catalogNames[i]);
					}
					
					if (catalog == null) {
						return null;
					}
					catalogID = catalog.getID();
				}
			}
		}
		return String.valueOf(catalogID);
	}

	/**
	 * 取形如 "国际新闻,国内新闻"的栏目的id，以,隔开各个ID
	 */
	public static String getIDsByName(String names) {
		return getIDsByName(Application.getCurrentSiteID(), names);
	}

	/**
	 * 取形如 "国际新闻,国内新闻"的栏目的id，以,隔开各个ID
	 */
	public static String getIDsByName(long siteID, String names) {
		return getIDsByName(siteID + "", names);
	}

	/**
	 * 取形如 "国际新闻,国内新闻"的栏目的id，以,隔开各个ID
	 */
	public static String getIDsByName(String siteID, String names) {
		if (StringUtil.isEmpty(names)) {
			return null;
		}
		if (names.startsWith(",")) {
			names = names.substring(1);
		}
		if (names.endsWith(",")) {
			names = names.substring(0, names.length() - 1);
		}
		String[] catalogNames = names.split(",");
		int catalogLenth = catalogNames.length;
		StringBuffer sb = new StringBuffer(40);
		if (catalogLenth > 0) {
			for (int i = 0; i < catalogLenth; i++) {
				String catalogID = getIDByNames(siteID, catalogNames[i]);
				if(StringUtil.isEmpty(catalogID)){
					continue;
				}
				if (i != 0) {
					sb.append(",");
				}
				sb.append(catalogID);
			}
		}
		return sb.toString();
	}

	/**
	 * 取形如 "政府动态"的栏目的id
	 */
	public static String getIDByName(String catalogName) {
		return getIDByName(Application.getCurrentSiteID(), catalogName);
	}

	/**
	 * 取形如 "政府动态"的栏目的id
	 */
	public static String getIDByName(long siteID, String catalogName) {
		return getIDByName(siteID + "", catalogName);
	}

	/**
	 * 取形如 "政府动态"的栏目的id
	 */
	public static String getIDByName(String siteID, String catalogName) {
		ZCCatalogSchema catalog = null;
		if (StringUtil.isDigit(catalogName)) {
			catalog = CMSCache.getCatalog(Long.parseLong(catalogName));
		}
		if (catalog == null) {
			catalog = CMSCache.getCatalog(siteID, catalogName);
		}
		if (catalog == null) {
			return null;
		}
		return String.valueOf(catalog.getID());
	}

	/**
	 * 取栏目id
	 */
	public static String getIDByName(long siteID, long parentID, String catalogName) {
		return getIDByName(siteID + "", parentID + "", catalogName);
	}

	/**
	 * 取栏目id
	 */
	public static String getIDByName(long siteID, String parentID, String catalogName) {
		return getIDByName(siteID + "", parentID, catalogName);
	}

	/**
	 * 取栏目id
	 */
	public static String getIDByName(String siteID, long parentID, String catalogName) {
		return getIDByName(siteID, parentID + "", catalogName);
	}

	/**
	 * 取栏目id
	 */
	public static String getIDByName(String siteID, String parentID, String catalogName) {
		ZCCatalogSchema catalog = null;
		if (StringUtil.isDigit(catalogName)) {
			catalog = CMSCache.getCatalog(Long.parseLong(catalogName));
		}
		if (catalog == null) {
			catalog = CMSCache.getCatalog(siteID, Long.parseLong(parentID), catalogName);
		}
		if (catalog == null) {
			return null;
		}
		return String.valueOf(catalog.getID());
	}

	/**
	 * 根据栏目的InnerCode取得栏目的id
	 */

	public static String getIDByInnerCode(String catalogInnerCode) {
		ZCCatalogSchema catalog = CMSCache.getCatalogByInnerCode(catalogInnerCode);
		if (catalog != null) {
			return "" + catalog.getID();
		}
		return null;
	}

	/**
	 * 获取栏目路径
	 */
	public static String getPath(long catalogID) {
		return getPath(catalogID + "");
	}

	/**
	 * 根据CatalogID取得栏目的相对路径，该路径由别名组成或使用自定义路径
	 */
	public static String getPath(String catalogID) {
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		String fullPath = "";
		String url = catalog.getURL();
		if(url != null){
			url = url.trim();
		}
		if(StringUtil.isNotEmpty(url)){
			if(url.startsWith("http://") || url.startsWith("https://")){
				fullPath = getFullPath(catalogID);
			}else if(url.startsWith("#")){//支持“#” 
				fullPath = getFullPath(catalogID);
			}else if(url.startsWith("/") //支持 “/include/about.html”，存在遗漏的情况
					&& (url.endsWith(".html") || url.endsWith(".htm") || url.endsWith(".shtml") || url.endsWith(".jsp") || url.endsWith(".do"))){
				fullPath = getFullPath(catalogID);
			}else{
				HtmlNameParser h = new HtmlNameParser(site.toDataRow(),catalog.toDataRow(),null,url);
				HtmlNameRule rule = h.getNameRule();
				fullPath = rule.getFullPath()+"/";
				fullPath = fullPath.replaceAll("/+", "/");
			}
		}else{
			fullPath = getFullPath(catalogID);
		}
		
		return fullPath;
	}
	
	public static String getLink(long catalogID,String levelStr) {
		return getLink(catalogID + "",levelStr);
	}
	
	/**
	 * 取栏目的链接路径，供当前位置、栏目跳转使用
	 * @param catalogID
	 * @return
	 */
	public static String getLink(String catalogID,String levelStr) {
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		String ext = ServletUtil.getUrlExtension(CatalogUtil.getSchema(catalogID).getListTemplate());
		String siteExt = SiteUtil.getExtensionType(catalog.getSiteID());
		String indexPage = "";
		if (ext.equals(".jsp") || siteExt.equals("jsp")) {
			indexPage += "index.jsp";
		} else {
			indexPage += "";
		}
		String linkUrl = "";
		String url = catalog.getURL();
		if(StringUtil.isNotEmpty(url)){
			if(url.startsWith("http://") || url.startsWith("https://")){//外部连接
				linkUrl = url;
			}else if(url.startsWith("#")){//支持“#” 
				linkUrl = url;
			}else if(url.startsWith("/") //支持 “/include/about.html”，存在遗漏的情况
					&& (url.endsWith(".html") || url.endsWith(".htm") || url.endsWith(".shtml") || url.endsWith(".jsp") || url.endsWith(".do"))){
				url = url.substring(1);
				linkUrl = levelStr + url;
				linkUrl.replaceAll("/+", "/");
			} else {
				HtmlNameParser h = new HtmlNameParser(site.toDataRow(),catalog.toDataRow(),null,url);
				HtmlNameRule rule = h.getNameRule();
				linkUrl = levelStr + rule.getFullPath() +"/"+indexPage;
				linkUrl = linkUrl.replaceAll("/+", "/");
			}
		}else{
			linkUrl = levelStr + getFullPath(catalogID) + indexPage;
		}
		
		return linkUrl;
	}

	/**
	 * 根据CatalogID取道栏目相对路径,该路径由栏目的ID组成
	 */
	public static String getCatalogIDPath(long catalogID) {
		return getCatalogIDPath(catalogID);
	}

	/**
	 * 根据CatalogID取道栏目相对路径,该路径由栏目的ID组成
	 */
	public static String getCatalogIDPath(String catalogID) {
		return getIDPath(catalogID);
	}

	/**
	 * 取栏目Innercode
	 */
	public static String getInnerCode(long catalogID) {
		return getInnerCode(catalogID + "");
	}

	/**
	 * 取栏目InnerCode
	 */
	public static String getInnerCode(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getInnerCode();
		}
		return null;
	}

	/**
	 * 取栏目catalogType
	 */
	public static long getCatalogType(long catalogID) {
		return getCatalogType(catalogID + "");
	}

	/**
	 * 取栏目catalogType
	 */
	public static long getCatalogType(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return 0;
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getType();
		}
		return 0;

	}

	/**
	 * 取栏目所属站点siteID
	 */
	public static String getSiteID(long catalogID) {
		return getSiteID(catalogID + "");
	}

	/**
	 * 取栏目所属站点siteID
	 */
	public static String getSiteID(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return "" + catalog.getSiteID();
		}
		return null;
	}

	/**
	 * 取栏目所属站点siteID
	 */
	public static String getSiteIDByInnerCode(String catalogInnerCode) {
		ZCCatalogSchema catalog = CMSCache.getCatalogByInnerCode(catalogInnerCode);
		if (catalog != null) {
			return "" + catalog.getSiteID();
		}
		return null;
	}

	/**
	 * 获取栏目绝对路径
	 */
	public static String getAbsolutePath(long catalogID) {
		return getAbsolutePath(catalogID + "");
	}

	/**
	 * 获取栏目绝对路径
	 */
	public static String getAbsolutePath(String catalogID) {
		return SiteUtil.getAbsolutePath(getSiteID(catalogID)) + getPath(catalogID);
	}

	/**
	 * 获取以id的形式构成的栏目绝对路径
	 */
	public static String getAbsoluteIDPath(long catalogID) {
		return getAbsoluteIDPath(catalogID + "");
	}

	/**
	 * 获取以id的形式构成的栏目绝对路径
	 */
	public static String getAbsoluteIDPath(String catalogID) {
		return SiteUtil.getAbsolutePath(getSiteID(catalogID)) + getCatalogIDPath(catalogID);
	}

	/**
	 * 获取栏目别名
	 */
	public static String getAlias(long catalogID) {
		return getAlias(catalogID + "");
	}

	/**
	 * 获取栏目别名
	 */
	public static String getAlias(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getAlias().toLowerCase();
		}
		return null;
	}

	/**
	 * 获取商品类别
	 */
	public static String getGoodsTypeID(long catalogID) {
		return getGoodsTypeID(catalogID + "");
	}

	/**
	 * 获取商品类别,对于ZCCatalog的Prop4字段
	 */
	public static String getGoodsTypeID(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getProp4();
		}
		return null;
	}

	/**
	 * 获取上级栏目ID
	 */
	public static String getParentID(long catalogID) {
		return getParentID(catalogID + "");
	}

	/**
	 * 获取上级栏目ID
	 */
	public static String getParentID(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "0";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return "" + catalog.getParentID();
		}
		return null;
	}

	/**
	 * 获取子栏目数
	 */
	public static String getChildCount(long catalogID) {
		if (catalogID == 0) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return "" + catalog.getChildCount();
		}
		return null;
	}

	/**
	 * 获取子栏目数
	 */
	public static String getChildCount(String catalogID) {
		return getChildCount(Long.parseLong(catalogID));
	}

	/**
	 * 得到栏目下拉框
	 */
	public static DataTable getCatalogOptions(long type) {
		DataTable dt = new QueryBuilder(
				"select Name,ID,TreeLevel,ParentID from ZCCatalog where SiteID = ? and Type = ? order by ID",
				Application.getCurrentSiteID(), type).executeDataTable();
		PubFun.indentDataTable(dt, 0, 2, 0);
		return dt;
	}

	/**
	 * 取得带缩进的栏目列表,默认第一层级为1. added by huanglei
	 */
	public static DataTable getList(int type) {
		return CatalogUtil.getList(type, 1);
	}

	/**
	 * 取得带缩进的栏目列表，因为各个地方都要调栏目的下拉树。added by huanglei
	 */
	public static DataTable getList(int type, int firstLevel) {
		String sql = "select Name,ID ,Level from ZCCatalog where Type=? and siteID =? order by InnerCode";
		DataTable dt = new QueryBuilder(sql, type, Application.getCurrentSiteID()).executeDataTable();
		PubFun.indentDataTable(dt, 0, 2, firstLevel);
		return dt;
	}

	/**
	 * 获取工作流
	 */
	public static String getWorkflow(long catalogID) {
		return getWorkflow(catalogID + "");
	}

	/**
	 * 获取工作流
	 */
	public static String getWorkflow(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getWorkflow();
		}
		return null;
	}

	/**
	 * 获取栏目附件下载配置
	 */

	public static String getAttachDownFlag(long catalogID) {
		return getAttachDownFlag(catalogID + "");
	}

	/**
	 * 获取栏目附件下载配置
	 */
	public static String getAttachDownFlag(String catalogID) {
		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
		if (config != null) {
			return config.getAttachDownFlag();
		}
		return null;
	}

	/**
	 * 获取栏目允许发布状态
	 */

	public static Date getArchiveTime(long catalogID) {
		return getArchiveTime(catalogID + "");
	}

	/**
	 * 获取栏目归档时间
	 */
	public static Date getArchiveTime(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return null;
		}
		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
		if (config != null) {
			String archive = config.getArchiveTime();
			if(archive.equals("0")){
				return null;
			}else{
				return DateUtil.addMonth(new Date(),Integer.parseInt(archive));
			}
		}
		return null;
	}


	/**
	 * 取得栏目单页栏目标记
	 */
	public static String getSingleFlag(long catalogID) {
		return getSingleFlag(catalogID + "");
	}

	/**
	 * 取得栏目单页栏目标记
	 */
	public static String getSingleFlag(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getSingleFlag();
		}
		return null;
	}

	/**
	 * 拆分本栏目及上级栏目内部编码串 按照六位编码进行
	 */
	public static String getParentCatalogCode(String innerCode) {
		if (innerCode == null) {
			return "";
		}
		String[] arr = new String[innerCode.length() / 6];
		int i = 0;
		while (innerCode.length() >= 6) {
			arr[i++] = innerCode;
			innerCode = innerCode.substring(0, innerCode.length() - 6);
		}
		return "'" + StringUtil.join(arr, "','") + "'";
	}
	
	public static int getLevel(long catalogID){
		return getLevel(catalogID+"");
	}

	/**
	 * 获取栏目层级
	 */
	public static int getLevel(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return 0;
		}
		int level = 0;
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			String url = catalog.getURL();
			if(StringUtil.isNotEmpty(url)){
				url = url+"/";
				Pattern p = Pattern.compile("\\$\\{CatalogPath\\}", Pattern.CASE_INSENSITIVE);
				Matcher matcher = p.matcher(url);
				
				if (matcher.find()) {
					level = (int)catalog.getTreeLevel();
				}

				url = matcher.replaceAll("").replaceAll("/+", "/");
				if (url.startsWith("/")) {
					url = url.substring(1);
				}
				level += StringUtil.count(url, "/");
			}else{
				level =  (int) catalog.getTreeLevel();
			}
		}
		return level;
	}
	
	public static int getDetailLevel(long catalogID){
		return getDetailLevel(catalogID+"");
	}
	
	/**
	 * 获取详细页相对于站点根目录的路径
	 * @param catalogID
	 * @return
	 */
	public static int getDetailLevel(String catalogID) {
		int level = 0;
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		String detailTemplateNameRule = catalog.getDetailNameRule();
		if (StringUtil.isNotEmpty(detailTemplateNameRule)) {
			Pattern p = Pattern.compile("\\$\\{CatalogPath\\}", Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(detailTemplateNameRule);
			if (matcher.find()) {
				level = CatalogUtil.getLevel(catalogID);
			}

			detailTemplateNameRule = matcher.replaceAll("");
			detailTemplateNameRule = detailTemplateNameRule.replaceAll("/+", "/");
			if (detailTemplateNameRule.startsWith("/")) {
				detailTemplateNameRule = detailTemplateNameRule.substring(1);
			}

			level += StringUtil.count(detailTemplateNameRule, "/");
		}else{
			level =  CatalogUtil.getLevel(catalogID);
		}
		return level;
	}

	/**
	 * 获取栏目路径
	 */
	public static String getFullPath(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		String path = "";
		String parentID = getParentID(catalogID);
		if (StringUtil.isEmpty(parentID)) {
			return "";
		} else {
			path = getAlias(catalogID + "").toLowerCase() + "/";
			if (!"0".equals(parentID)) {
				path = getFullPath(parentID) + path;
			}
		}
		return path;
	}

	/**
	 * 获取以ID表示的栏目路径
	 */
	private static String getIDPath(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		String path = "";
		String parentID = getParentID(catalogID);
		if (StringUtil.isEmpty(parentID)) {
			return "";
		} else {
			path = catalogID + "/";
			if (!"0".equals(parentID)) {
				path = getIDPath(parentID) + path;
			}
		}
		return path;
	}

	/**
	 * 获取栏目及其附属信息
	 */
	public static DataRow getData(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return null;
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			DataRow dr = catalog.toDataRow();
			ColumnUtil.extendCatalogColumnData(dr, catalog.getSiteID(), "");
			return dr;
		} else {
			return null;
		}
	}

	/**
	 * 获取保存ID和热点词汇处理标志的栏目数据 栏目及其附属信息
	 */
	public static String getHotWordType(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return "";
		}
		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
		if (config != null) {
			return "" + config.getHotWordType();
		}
		return null;
	}

	/**
	 * 初始化栏目缓存
	 */
	public static void update(long catalogID) {
		update(catalogID + "");
	}

	/**
	 * 初始化栏目缓存
	 */
	public static void update(String catalogID) {
		if (StringUtil.isEmpty(catalogID) || catalogID.equals("0")) {
			return;
		}
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(catalogID);

		if (catalog.fill()) {
			// 处理栏目发布配置相关信息
			ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
			ZCCatalogConfigSet configSet = config.query(new QueryBuilder("where CatalogID=?", catalog.getID()));
			CacheManager.set("CMS", "Catalog", catalog.getID(), catalog);
			if (configSet != null && configSet.size() > 0) {
				config = configSet.get(0);
				if (config.getCatalogID() == 0) {
					CacheManager.set("CMS", "CatalogConfig", config.getSiteID() + ",0", config);
				} else {
					CacheManager.set("CMS", "CatalogConfig", config.getCatalogID(), config);
				}
			}
		} else {
			CacheManager.remove("CMS", "Catalog", catalogID);
		}
	}

	public static String createCatalogInnerCode(String parentCode) {
		if (StringUtil.isNotEmpty(parentCode)) {
			return NoUtil.getMaxNo("CatalogInnerCode", parentCode, 6);
		} else {
			return NoUtil.getMaxNo("CatalogInnerCode", 6);
		}
	}

	/**
	 * 返回用户有权限的栏目id
	 */
	public static String getPrivCatalog(int type, String properties) {
		DataTable dt = new QueryBuilder("select * from zccatalog where siteid =? and type = ?", Application
				.getCurrentSiteID(), type).executeDataTable();
		ArrayList list = new ArrayList();
		String PrivType = Priv.ARTICLE;
		if (type == Catalog.CATALOG) {
			PrivType = Priv.ARTICLE;
		} else if (type == Catalog.IMAGELIB) {
			PrivType = Priv.IMAGE;
		} else if (type == Catalog.VIDEOLIB) {
			PrivType = Priv.VIDEO;
		} else if (type == Catalog.ATTACHMENTLIB) {
			PrivType = Priv.ATTACH;
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (Priv.getPriv(User.getUserName(), PrivType, dt.getString(i, "InnerCode"), Priv.ARTICLE_BROWSE)) {
				if (properties.equals("ID")) {
					list.add(dt.getString(i, "ID"));
				} else if (properties.equals("InnerCode")) {
					list.add(dt.getString(i, "InnerCode"));
				}
			}
		}
		if (!list.isEmpty()) {
			return StringUtil.join(list);
		}
		return "0";
	}

	/**
	 * 为DataTable增加CatalogName列
	 */
	public static void addCatalogName(DataTable dt, String catalogIDColumn) {
		dt.insertColumn("CatalogName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String name = CatalogUtil.getName(dt.getString(i, catalogIDColumn));
			dt.set(i, "CatalogName", name);
		}
	}
	
	public static void main(String[] args) {
//		System.out.println(CatalogUtil.getFullName(10742));
	}
}
