package com.sinosoft.utility;

import com.sinosoft.cms.template.CmsTemplateData;
import com.sinosoft.cms.template.TagParser;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCSiteSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 发布文章缓存-定时计划
 * 
 * @author guob
 * 
 */
public class ArticleCachePlan {
	private static final Logger logger = LoggerFactory.getLogger(ArticleCachePlan.class);

	// 关键字段解析
	private static final Pattern pField = Pattern.compile("\\$\\{(\\w+?)\\.(\\w+?)(\\|(.*?))??\\}");

	protected static int threadCount = 8;

	protected static int aliveThreadCount;

	/**
	 * 文章模块缓存
	 * 
	 * @return
	 */
	public boolean cacheBlock(String catalogID) {
		// 遍历栏目、获取栏目模版
		String content = getTemplateContent(catalogID);
		if (StringUtil.isEmpty(content)) {
			return false;
		}

		// 解析模版标签 cms:list
		Map<String, Object> cms_list_key = getListKey(content, null);
		if (cms_list_key == null || cms_list_key.isEmpty()) {
			logger.warn("栏目不存在可以解析的cms:list.{}", catalogID);
			return false;
		}

		// 获取栏目对应数据、生成缓存文件
		if (proBlock(catalogID, cms_list_key)) {
			return false;
		}

		return true;
	}

	/**
	 * 文章模块缓存单独缓存
	 * 
	 * @return
	 */
	public static boolean cacheBlock(String catalogID, String signlePublish) {
		// 遍历栏目、获取栏目模版
		String content = getTemplateContent(catalogID);
		if (StringUtil.isEmpty(content)) {
			return false;
		}

		// 解析模版标签 cms:list
		Map<String, Object> cms_list_key = getListKey(content, signlePublish);

		if (cms_list_key == null || cms_list_key.isEmpty()) {
			logger.warn("栏目不存在可以解析的cms:list.{}", catalogID);
			return false;
		}

		// 获取栏目对应数据、生成缓存文件
		if (!proBlock(catalogID, cms_list_key)) {
			return false;
		}

		return true;
	}

	/**
	 * 获取模版内容
	 * 
	 * @param catalogID
	 * @return
	 */
	public static String getTemplateContent(String catalogID) {
		try {
			// 遍历栏目、获取栏目模版
			ZCCatalogSchema tZCCatalogSchema = new ZCCatalogSchema();
			tZCCatalogSchema.setID(catalogID);
			if (!tZCCatalogSchema.fill()) {
				logger.warn("栏目不存在.{}", catalogID);
				return null;
			}

			// 解析模版：详细页面、单页模式？ 并且列表页面、详细页面是可以发布的状态
			String detailTemplate = "";
			if ("Y".equals(tZCCatalogSchema.getSingleFlag()) && !"off".equals(tZCCatalogSchema.getPublishLT())) {
				detailTemplate = tZCCatalogSchema.getListTemplate();

			} else if (!"off".equals(tZCCatalogSchema.getPublishDT())) {
				detailTemplate = tZCCatalogSchema.getDetailTemplate();

			}

			if (StringUtil.isEmpty(detailTemplate)) {
				logger.warn("栏目不存在可以缓存的模版.{}", catalogID);
				return null;
			}
			// 读取模版文件内容
			// String catalogPath = Config.getContextRealPath() +
			// Config.getValue("Statical.TemplateDir") + File.pathSeparator +
			// Application.getCurrentSiteAlias();
			String catalogPath = Config.getContextRealPath() + "/wwwroot" + File.separator + "kxb";
			return FileUtil.readText(catalogPath + "" + detailTemplate);

		} catch (Exception e) {
			logger.error("获取模版内容异常." + catalogID + e.getMessage(), e);
			return null;
		}

	}

	/**
	 * 解析模版获取 cms:list标签
	 * 
	 * @param content
	 * @return
	 */
	public static Map<String, Object> getListKey(String content, String signlePublish) {
		Pattern cmsList = Pattern.compile("<cms:list\\s(.*?)>(.*?)</cms:List>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = cmsList.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;

		Map<String, Object> result = new HashMap<String, Object>();
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = TagParser.getAttrMap(m.group(1));
			String cmsorder = map.getString("cmsorder");
			// 如果cmsorder为空，则不缓存
			if (StringUtil.isEmpty(cmsorder)) {
				logger.warn("cms:list不存在排序字段cmsorder,无法缓存. -- {}", m.group(1));
				continue;
			}

			// 一个模版内cmsorder重复，则不缓存
			if (result.containsKey("cmslist_" + cmsorder)) {
				logger.warn("cms:list排序字段cmsorder,重复. -- cmsorder：{}", cmsorder);
				continue;
			}

			if (!StringUtil.isEmpty(signlePublish) && ("cmslist_" + cmsorder).equalsIgnoreCase(signlePublish)) {
				Map<String, Object> result_signlePublish = new HashMap<String, Object>();
				result_signlePublish.put("cmslist_" + cmsorder, map);
				return result_signlePublish;

			} else {
				result.put("cmslist_" + cmsorder, map);
			}
		}
		return result;
	}

	/**
	 * 根据栏目生成缓存模块
	 * 
	 * @param catalogID
	 * @param keyMap
	 * @return
	 */
	private static boolean proBlock(String CatalogID, Map<String, Object> keyMap) {
		ZCArticleSet articleSet = new ZCArticleSchema().query(new QueryBuilder("where CatalogID=? ", CatalogID));
		if (articleSet == null || articleSet.size() == 0) {
			logger.warn("栏目不存在可以缓存的数据.{}", CatalogID);
			return false;
		}

		CmsTemplateData ct = new CmsTemplateData();

		ZCSiteSchema site = new ZCSiteSchema();
		site.setID("221");
		site.fill();

		ct.setSite(site);

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(CatalogID);
		catalog.fill();
		ct.setCatalog(catalog);

		for (int i = 0; i < articleSet.size(); i++) {
			ZCArticleSchema tZCArticleSchema = articleSet.get(i);
			for (String key : keyMap.keySet()) {
				writeCache(keyMap, key, site, catalog, tZCArticleSchema, CatalogID, ct);
			}
		}
		return true;
	}

	/**
	 * 缓存文件
	 * 
	 * @param keyMap
	 * @param key
	 * @param site
	 * @param catalog
	 * @param tZCArticleSchema
	 * @param CatalogID
	 * @param ct
	 * @return
	 */

	@SuppressWarnings("unchecked")
	private static boolean writeCache(Map<String, Object> keyMap, String key, ZCSiteSchema site, ZCCatalogSchema catalog, ZCArticleSchema tZCArticleSchema, String CatalogID, CmsTemplateData ct) {
		Mapx map = (Mapx) keyMap.get(key);
		if (map == null || map.isEmpty()) {
			logger.warn("栏目不存在可以缓存的数据异常. key-{}|CatalogID-{}", key, CatalogID);
			return false;
		}

		String item = ((String) map.get("item")).toLowerCase();
		String type = (String) map.get("type");
		String countStr = (String) map.get("count");
		String pagesizeStr = (String) map.get("pagesize");
		String condition = (String) map.get("condition");
		String displayLevel = (String) map.get("level");
		String attribute = (String) map.get("hasattribute");
		String tag = (String) map.get("tag");
		String catalogName = (String) map.get("name");
		//String cmsorder = (String) map.get("name");

		DataTable dt = null;

		// 缓存list模块
		if (countStr == null || "".equals(countStr) || "null".equals(countStr)) {
			countStr = "50";
		}
		int pageSize = Integer.parseInt(countStr);

		// 解析tag中的变量表达式
		String tagStr = null;
		if (StringUtil.isNotEmpty(tag) && tag.indexOf("${") != -1) {
			tagStr = parsePlaceHolderStr(tag, site.toCaseIgnoreMapx(), catalog.toCaseIgnoreMapx(), tZCArticleSchema.toCaseIgnoreMapx());
		} else {
			tagStr = tag;
		}

		if (StringUtil.isNotEmpty(displayLevel)) {
			displayLevel = displayLevel.toLowerCase();
		} else {
			displayLevel = "current";
		}

		if (StringUtil.isEmpty(pagesizeStr) || !StringUtil.isDigit(pagesizeStr)) {
			pagesizeStr = "0";
		}

		// 解析Name中的变量表达式、转换为具体值
		String catalogStr = null;
		if (StringUtil.isNotEmpty(catalogName) && catalogName.indexOf("${") != -1) {
			catalogStr = parsePlaceHolderStr(catalogName, site.toCaseIgnoreMapx(), catalog.toCaseIgnoreMapx(), tZCArticleSchema.toCaseIgnoreMapx());
		} else {
			catalogStr = catalogName;

		}
		// 解析condtion中的变量表达式
		String conditionStr = null;
		if (StringUtil.isNotEmpty(condition) && ((condition.indexOf("${") != -1) || (condition.toLowerCase().indexOf("$g{") != -1))) {
			conditionStr = parsePlaceHolderStr(condition, site.toCaseIgnoreMapx(), catalog.toCaseIgnoreMapx(), tZCArticleSchema.toCaseIgnoreMapx());
		} else {
			conditionStr = condition;
		}

		ct.setArticle(tZCArticleSchema);

		// 文档列表
		if ("article".equalsIgnoreCase(item) || "image".equalsIgnoreCase(item) || "video".equalsIgnoreCase(item) || "audio".equalsIgnoreCase(item) || "attachment".equalsIgnoreCase(item)) {
			try {
				dt = ct.getPagedDocList(item, catalogStr, displayLevel, attribute, type, conditionStr, tagStr, pageSize, 0);

			} catch (Exception e) {
				logger.error("缓存数据异常. key:" + key + "|CatalogID:"
						+ CatalogID + "|ArticleID:" + tZCArticleSchema.getID() + "|" + map.toString()
						+ e.getMessage(), e);
				return false;
			}

		} else if ("hotopic".equalsIgnoreCase(item)) {

			// 热点专题
			String group = map.getString("group");
			if (group == null) {
				group = catalogName;

			}
			if (group != null) {
				group = group.toLowerCase();
				group = parsePlaceHolderStr(group, site.toMapx(), catalog.toMapx(), tZCArticleSchema.toMapx());
			}

			try {
				dt = ct.getHoTopicList(item, group, countStr, conditionStr,null);

			} catch (Exception e) {
				logger.error("缓存数据异常. key:" + key + "|CatalogID:"
						+ CatalogID + "|ArticleID:" + tZCArticleSchema.getID() + "|" + map.toString()
						+ e.getMessage(), e);
				return false;
			}

		} else if ("relazx".equalsIgnoreCase(item)) {
			try {
				dt = ct.getZxArticle(type, tagStr, pageSize,null);

			} catch (Exception e) {
				logger.error("缓存数据异常. key:" + key + "|CatalogID:"
						+ CatalogID + "|ArticleID:" + tZCArticleSchema.getID() + "|" + map.toString()
						+ e.getMessage(), e);
				return false;
			}

		} else if ("hotlabels".equalsIgnoreCase(item)) {
			try {
				dt = ct.getHotLabels(type, tagStr, pageSize,null);

			} catch (Exception e) {
				logger.error("缓存数据异常. key:" + key + "|CatalogID:"
						+ CatalogID + "|ArticleID:" + tZCArticleSchema.getID() + "|" + map.toString()
						+ e.getMessage(), e);
				return false;
			}

		} else if ("attention".equalsIgnoreCase(item)) {
			try {
				dt = ct.getAttentionArticle(type, tagStr, pageSize,null);

			} catch (Exception e) {
				logger.error("缓存数据异常. key:" + key + "|CatalogID:"
						+ CatalogID + "|ArticleID:" + tZCArticleSchema.getID() + "|" + map.toString()
						+ e.getMessage(), e);
				return false;
			}

		} else {
			logger.error("未知类型. {}", map.toString());
			return false;
		}

		if (dt == null || dt.getRowCount() == 0) {
			Object[] argArr = {key, CatalogID, tZCArticleSchema.getID(), map.toString(), dt.getRowCount()};
			logger.error("获取数据异常. key:{}|CatalogID:{}|ArticleID:{}|{}|DT:{}", argArr);
		}

		ArticleCache tCache = new ArticleCache();
		tCache.writeCache(key, tZCArticleSchema.getID() + "", dt);
		return true;
	}

	/**
	 * 解析变量表达式
	 * 
	 * @param content
	 * @param site
	 * @param catalog
	 * @param article
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String parsePlaceHolderStr(String content, Mapx site, Mapx catalog, Mapx article) {
		Matcher m = pField.matcher(content);
		int lastEndIndex = 0;
		StringBuffer sb = new StringBuffer();

		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));

			if (StringUtil.isEmpty(m.group(1)) || StringUtil.isEmpty(m.group(2))) {
				logger.error("解析变量表达式异常.  {}", content);
				return null;
			}
			String temp = "";
			if ("article".equalsIgnoreCase(m.group(1))) {
				temp = (String) article.get(m.group(2).toLowerCase());

			} else if ("catalog".equalsIgnoreCase(m.group(1))) {
				temp = (String) catalog.get(m.group(2).toLowerCase());

			} else if ("site".equalsIgnoreCase(m.group(1))) {
				temp = (String) site.get(m.group(2).toLowerCase());
			}

			sb.append(temp);

			lastEndIndex = m.end();
		}

		sb.append(content.substring(lastEndIndex));

		return sb.toString();
	}

	public static void main(String[] args) {
		
		//资讯：9444（bxzx_new_detail.html），行业资讯9609，实事资讯9610，最新活动9823
		//问答：9445（insqa_new_detail.html），保险咨询9450 ，险种咨询9451，网站相关9452，我要提问9479 
		//知识：9443（insknowledge_article.html） ，认识保险：9519，购买保险：9520，报下理赔：9521 人寿保险知识：9813 
		           //意外保险知识：9814 车辆保险知识：9816，旅游保险知识：9818  健康保险知识：9817 家财保险知识：9994
		//百科：9710（bxbk_bk_new_Detail.html），知识：9711，产品：9712，法规：9713，业务9714，条款：9715，合同：9716，考试：9717，公司：9719，名人：9721
		 new ArticleCachePlan().cacheBlock("9609");

		// ArticleCache tArticleCache = new ArticleCache();
		// DataTable dt = tArticleCache.readCacheByDataTable("255720",
		// "cmslist_1");
		// System.out.println(dt.toString());

		// dt = tArticleCache.readCacheByDataTable("255720", "cmslist_2");
		// System.out.println(dt.toString());
		//				
		// dt = tArticleCache.readCacheByDataTable("255720", "cmslist_3");
		// System.out.println(dt.toString());
		//				
		// dt = tArticleCache.readCacheByDataTable("255720", "cmslist_4");
		// System.out.println(dt.toString());
		//				
		// dt = tArticleCache.readCacheByDataTable("255720", "cmslist_5");
		// System.out.println(dt.toString());
		//				
		// dt = tArticleCache.readCacheByDataTable("255720", "cmslist_6");
		// System.out.println(dt.toString());
		//				
		// dt = tArticleCache.readCacheByDataTable("255720", "cmslist_11");
		// System.out.println(dt.toString());
		//				
		// dt = tArticleCache.readCacheByDataTable("255720", "cmslist_12");
		// System.out.println(dt.toString());
		//				
		// dt = tArticleCache.readCacheByDataTable("255720", "cmslist_13");
		// System.out.println(dt.toString());
	}
}
