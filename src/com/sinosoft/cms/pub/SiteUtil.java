package com.sinosoft.cms.pub;

import com.sinosoft.cms.site.CatalogConfig;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCCatalogConfigSchema;
import com.sinosoft.schema.ZCCatalogConfigSet;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZCTagSchema;

/**
 * 缓存管理站点的相关信息
 * 
 */

public class SiteUtil {

	static {
		CatalogConfig.initCatalogConfig();
	}

	/**
	 * 获取站点相对路径
	 */
	public static String getPath(long siteID) {
		return getPath(siteID + "");
	}

	/**
	 * 获取站点相对路径
	 */
	public static String getPath(String siteID) {
		String path = Config.getContextPath() + Config.getValue("Statical.TargetDir") + "/" + getAlias(siteID) + "/";
		return path.replaceAll("/+", "/");
	}

	/**
	 * 获取站点绝对路径
	 */
	public static String getAbsolutePath(long siteID) {
		return getAbsolutePath(siteID + "");
	}

	/**
	 * 获取站点绝对路径
	 */
	public static String getAbsolutePath(String siteID) {
		String path = Config.getContextRealPath() + Config.getValue("Statical.TargetDir") + "/" + getAlias(siteID)
				+ "/";
		return path.replaceAll("/+", "/");
	}

	/**
	 * 获取站点名称
	 */
	public static String getName(long siteID) {
		return getName(siteID + "");
	}

	/**
	 * 获取站点名称
	 */
	public static String getName(String siteID) {
		if (StringUtil.isEmpty(siteID)) {
			return null;
		}
		ZCSiteSchema site = getSchema(siteID);
		if (site == null) {
			return null;
		}
		return site.getName();
	}

	public static synchronized ZCSiteSchema getSchema(String siteID) {
		return CMSCache.getSite(siteID);
	}

	public static synchronized ZCSiteSchema getSchema(long siteID) {
		return CMSCache.getSite(siteID + "");
	}

	/**
	 * 获取站点别名
	 */
	public static String getAlias(long siteID) {
		return getAlias(siteID + "");
	}

	/**
	 * 获取站点别名
	 */
	public static String getAlias(String siteID) {
		if (StringUtil.isEmpty(siteID)) {
			return null;
		}
		ZCSiteSchema site = getSchema(siteID);
		if (site == null) {
			return null;
		}
		return site.getAlias();
	}

	/**
	 * 获取站点代码
	 */
	public static String getCode(long siteID) {
		return getAlias(siteID + "");
	}

	/**
	 * 获取站点代码
	 */
	public static String getCode(String siteID) {
		return getAlias(siteID + "");
	}

	/**
	 * 获取网站链接地址
	 */
	public static String getURL(long siteID) {
		return getURL(siteID + "");
	}

	/**
	 * 获取网站链接地址
	 */
	public static String getURL(String siteID) {
		ZCSiteSchema site = getSchema(siteID);
		if (site == null) {
			return "";
		}
		String url = site.getURL();
		if (StringUtil.isEmpty(url)) {
			return "";
		}
		// 如果使用的默认的http://，返回也为空
		if ("http://".equals(url)) {
			return "";
		}
		if (!url.startsWith("http://")) {
			url = "http://" + url;
		}
		return url;
	}

	/**
	 * 获取网站各栏目归档期限
	 */
	public static String getArchiveTime(long siteID) {
		return getArchiveTime(siteID + "");
	}

	/**
	 * 获取网站各栏目归档期限
	 */
	public synchronized static String getArchiveTime(String siteID) {
		return CMSCache.getCatalogConfig(siteID + ",0").getArchiveTime();
	}

	/**
	 * 获取网站附件下载配置
	 */
	public static String getAttachDownFlag(long siteID) {
		return getAttachDownFlag(siteID + "");
	}

	/**
	 * 获取网站附件下载配置
	 */
	public synchronized static String getAttachDownFlag(String siteID) {
		return CMSCache.getCatalogConfig(siteID + ",0").getAttachDownFlag();
	}

	public static void update(long siteID) {
		update(siteID + "");
	}

	/**
	 * 获取站点评论是否需要审核
	 */
	public synchronized static boolean getCommentAuditFlag(String siteID) {
		ZCSiteSchema site = getSchema(siteID);
		if (site == null) {
			return false;
		}
		String commentAuditFlag = site.getCommentAuditFlag();
		return "Y".equalsIgnoreCase(commentAuditFlag);
	}

	public static boolean getCommentAuditFlag(long siteID) {
		return getCommentAuditFlag(siteID + "");
	}

	public synchronized static void update(String siteID) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(siteID);
		if (site.fill()) {
			// 处理站点发布配置相关信息
			ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
			ZCCatalogConfigSet configSet = config.query(new QueryBuilder(" where CatalogID=0 and SiteID=?", Long
					.parseLong(siteID)));
			if (configSet != null && configSet.size() > 0) {
				config = configSet.get(0);
				CacheManager.set("CMS", "Config", siteID + ",0", config);
			}
			CacheManager.set("CMS", "Site", siteID, site);
		}
	}

	public static boolean isBBSEnable(String siteID) {
		ZCSiteSchema site = getSchema(siteID);
		if (site == null) {
			return false;
		}
		String flag = site.getBBSEnableFlag();
		return "Y".equalsIgnoreCase(flag);
	}

	public static boolean isShopEnable(String siteID) {
		ZCSiteSchema site = getSchema(siteID);
		if (site == null) {
			return false;
		}
		String flag = site.getShopEnableFlag();
		return "Y".equalsIgnoreCase(flag);
	}

	public static ZCTagSchema getTag(long siteID, String tag) {
		return CMSCache.getTag(siteID, tag);
	}
	
	public static String getExtensionType(String siteID){
		String ext = "shtml";
		ZCSiteSchema site =  getSchema(siteID);
		if(site != null){
			ext = site.getProp1();
			if(StringUtil.isEmpty(ext)){
				ext = "shtml";
			}
		}
		
		return ext;
	}
	
	public static String getExtensionType(long siteID){
		return getExtensionType(siteID+"");
	}
}
