package com.sinosoft.cms.pub;

import com.sinosoft.cms.template.TagParser;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCSiteSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspWriter;
import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * 公共包含类，对于于Include/Top.jsp,Include/Bottom.jsp,Include/Head.jsp<br>
 * 用于留言、搜索、论坛、商城等动态应用统一头部和尾部
 * 
 */
public class PublicHead {
	private static final Logger logger = LoggerFactory.getLogger(PublicHead.class);
	private static Mapx timeMap = new Mapx(3000);// 最多缓存3000个文件
	private static Mapx fileMap = new Mapx(3000);
	private static Object mutex = new Object();
	private static int beginIndex = 0;

	// 缓存栏目名称.
	public static HashMap<String, String> catalogName = new HashMap<String, String>();
	// 缓存文章标题.
	public static HashMap<String, String> acticleTile = new HashMap<String, String>();
	// 缓存关键字(列表页、详细页共用).
	public static HashMap<String, Object> MetaKeywords = new HashMap<String, Object>();
	// 缓存描述(列表页、详细页共用).
	public static HashMap<String, Object> MetaDescription = new HashMap<String, Object>();
	// zcsite meta_keyWords.
	public static String meta_keyWords;

	static{
		// 查询站点关键字.
		if (StringUtil.isEmpty(meta_keyWords)) {
			QueryBuilder qb = new QueryBuilder(
					"select meta_keywords from zcsite where id = '221'");
			meta_keyWords = (String) qb.executeOneValue();
		}
	}
	/**
	 * 获取文件全路径对应的HTML，实现了缓存
	 */
	public static String getHtml(String alias, String path) {
		long current = System.currentTimeMillis();
		Long lastTime = (Long) timeMap.get(path);
		String html = null;
		if (lastTime == null) {
			synchronized (mutex) {
				lastTime = new Long(current);
				timeMap.put(path, lastTime);
				if (new File(path).exists()) {
					html = FileUtil.readText(path, Constant.GlobalCharset);
					html = dealResource(alias, html);
				} else {
					html = "Include file is not exists!";
				}
				fileMap.put(path, html);
			}
		} else if (lastTime.longValue() < current - 3000) {// 3秒检查一次
			if (new File(path).lastModified() > lastTime.longValue()) {
				synchronized (mutex) {
					lastTime = new Long(current);
					timeMap.put(path, lastTime);
					if (new File(path).exists()) {
						html = FileUtil.readText(path, Constant.GlobalCharset);
						html = dealResource(alias, html);
					} else {
						html = "Include file is not exists!";
					}
					fileMap.put(path, html);
				}
			} else {
				synchronized (mutex) {
					lastTime = new Long(current);
					html = fileMap.getString(path);
					timeMap.put(path, lastTime);
				}
			}
		} else {
			html = fileMap.getString(path);
		}
		return html;
	}

	/**
	 * 返回公共顶部HTML
	 */
	public static void getTop(String SiteID, JspWriter out) throws Exception {
		if (StringUtil.isEmpty(SiteID)) {
			out.println("URL中必须带有SiteID参数!");
			return;
		}
		String path = SiteUtil.getAbsolutePath(SiteID);
		ZCSiteSchema site = SiteUtil.getSchema(SiteID);
		if (site == null) {
			out.println("Site=" + SiteID + "的站点未找到!");
			return;
		}
		
		path = Config.getContextRealPath()
				+ "wwwroot/kxb/block/kxb_header_index_new.shtml";
		String html = getHtml(site.getAlias(), path);
		out.write(html);
	}

	/**
	 * 返回公共底部HTML.
	 */
	public static void getBottom(String siteID, JspWriter out) throws Exception {
		if (StringUtil.isEmpty(siteID)) {
			out.println("URL中必须带有SiteID参数!");
			return;
		}
		String path = SiteUtil.getAbsolutePath(siteID);
		ZCSiteSchema site = SiteUtil.getSchema(siteID);
		if (site == null) {
			out.println("Site=" + siteID + "的站点未找到!");
			return;
		}
		String template = site.getBottomTemplate();
		if (StringUtil.isEmpty(template)) {
			template = "foot.html";
		} else {
			if (template.startsWith("/")) {
				template = template.substring(1);
			}
			template = template.substring(template.indexOf('/') + 1);
		}
		path = path + "/include/" + template;
		path = Config.getContextRealPath()
				+ "wwwroot/kxb/block/kxb_footer_new_css.shtml";
		String html = getHtml(site.getAlias(), path);
		out.write(html);
	}

	/**
	 * 将HTML源代码中的相对路径替换成绝对路径
	 */
	public static String dealResource(String alias, String content) {
		if (content == null) {
			return "";
		}
		content = StringUtil
				.replaceAllIgnoreCase(content, "\\$\\{level\\}", "");
		Matcher m = TagParser.resourcePattern1.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			String dealPath = dealURL(alias, m.group(2));
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(" " + m.group(1) + "=" + dealPath + m.group(3));
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = TagParser.resourcePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = dealURL(alias, m.group(3));
			sb.append(content.substring(lastEndIndex, m.start()));
			String separator = m.group(2);
			sb
					.append(" " + m.group(1) + "=" + separator + dealPath
							+ separator);
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = TagParser.resourcePatternCss.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = dealURL(alias, m.group(2));
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + dealPath + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = TagParser.resourcePatternCss2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = dealURL(alias, m.group(3));
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + dealPath + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	/**
	 * 将URL改写成绝对路径
	 */
	private static String dealURL(String alias, String url) {
		//### 不处理 首页头部登录使用.
		if("###".equals(url)){
			return url;
		}
		String prefix = (Config.getContextPath()
				+ Config.getValue("Statical.TargetDir") + "/" + alias + "/")
				.replaceAll("/+", "/");
		if (url.startsWith("../")) {
			return prefix + url.substring(3);
		} else {
			if (url.indexOf(':') > 0 || url.indexOf(')') > 0
					|| url.indexOf('<') > 0 || url.indexOf("#") > 0) {
				return url;
			}
			return prefix + url;
		}
	}

	/**
	 * 将HTML转换为SRCIPT write.
	 * 
	 * @param str
	 * @return
	 */
	public static String htmlToSrcipt(String str) {
		String strTemp = str.replaceAll("\"", "\\\\\"");
		str = strTemp.replaceAll("'", "\\\\\'").trim();
		StringBuffer sb = new StringBuffer();
		String begin = "document.write('";
		String end = "');";
		while (str.indexOf("<") != -1) {
			String temp = str.substring(0, str.indexOf(">") + 1);
			sb.append(begin + temp.trim() + end + "\n");
			String str1 = str.substring(str.indexOf(">") + 1, str.length());
			str = str1;
		}
		sb.append(begin + str + end);
		return sb.toString();
	}
	
	/**
	 * 通过URL 返回首页标题.
	 * 
	 * @param url
	 * @return
	 */
	public static String queryIndexTitle(String url) {
		
		String results = null;
		//首页查询标题
		QueryBuilder qb_title = new QueryBuilder("select meta_title from zccatalog where url = ?");
		qb_title.add(url);
		String title = (String) qb_title.executeOneValue();
			
		if (StringUtil.isEmpty(title)) {
			title = "开心保网欢迎您";
		}
		
		results = title + "-" + meta_keyWords;

		return results;
	}

	/**
	 * 通过URL 返回文章标题.
	 * 
	 * @param url
	 * @return
	 */
	public static String queryDetailTitle(String url) {
		
		String results = null;

		//详细页 查询文章标题
		if (StringUtil.isEmpty(acticleTile.get(url))) {
			QueryBuilder qb_title = new QueryBuilder(
						"select Title from zcarticle where url like '%" + url
								+ "%'");
			String title = (String) qb_title.executeOneValue();
			acticleTile.put(url, formatString(title));
		}
			results = acticleTile.get(url);
			
		if (StringUtil.isEmpty(results)) {
			results = "开心保网欢迎您";
		}
		
		results = results + "-" + meta_keyWords;

		return results;
	}
	
	/**
	 * 通过URL,查询TITLE.
	 * @param url
	 * @return
	 */
	public static String queryListTitle(String url) {
		String results = null;
		try {
			//问答单独处理 list
			if(url.indexOf("list") != -1){
				url = url.substring(0,url.indexOf("list"));
			}
			// 查询栏目名称,判断缓存是否存在,不存在进入
			if (StringUtil.isEmpty(catalogName.get(url))) {
				String tempUrl = "";
				if (url.indexOf(".shtml") != -1) {
					tempUrl = url.substring(0, url.lastIndexOf("/"));
				}else{
					tempUrl = url;
				}
				QueryBuilder qb_catalogName = new QueryBuilder(
						"select meta_title from zccatalog where url like '%" + tempUrl
								+ "%'");
				results = (String) qb_catalogName.executeOneValue();
				if(StringUtil.isEmpty(results)){
					results = "开心保";
				}
				catalogName.put(url, formatString(results));
			}
			
			if (!StringUtil.isEmpty(catalogName.get(url))) {
				results = catalogName.get(url);
			}
			//提问单独处理
			if(url.indexOf("tiwen/") != -1){
				results = results + "-" + meta_keyWords;
				return results;
			}
			
			// 判断URL 是否存在 index_ 列表页 分页
			if (url.indexOf("index_") != -1) {
				String page = url.substring(url.indexOf("index_") + 6, url
						.indexOf("."));
				results = results + "_第" + page + "页_" + meta_keyWords;
			}else if(url.indexOf("list") != -1){
				String page = url.substring(url.indexOf("list_") + 5, url
						.indexOf("."));
				results = results + "_第" + page + "页_" + meta_keyWords;
			}else{
				results = results + "_第1页_" + meta_keyWords;
			}
		} catch (Exception e) {
			logger.error("动态头和尾(方法：queryListTitle) url:"+url + e.getMessage(), e);
		}
		
		
		return results;
	}
	
	/**
	 * 根据url获取列表页 关键词、描述.
	 * 
	 * @param url
	 */
	public static boolean getListMkMd(String url) {
		if (StringUtil.isEmpty(MetaKeywords.get(url))) {
			QueryBuilder qb = new QueryBuilder(
					"select meta_Keywords,Meta_Description from zccatalog where url like '%"+ url +"%' ");
			DataTable dt = qb.executeDataTable();
			if(dt.getRowCount() > 0){
				MetaKeywords.put(url,formatString(dt.getString(0, "meta_Keywords")));
				MetaDescription.put(url, formatString(dt.getString(0, "Meta_Description")));
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据url获取详细页 关键词、描述.
	 * 
	 * @param url
	 */
	public static boolean getDetailMkMd(String url) {
		if (StringUtil.isEmpty(MetaKeywords.get(url))) {
			QueryBuilder qb = new QueryBuilder(
					"select MetaKeywords,MetaDescription from zcarticle where url like '%"
							+ url + "%' and status = '30'");
			DataTable dt = qb.executeDataTable();
			if(dt.getRowCount() > 0){
				MetaKeywords.put(url,formatString(dt.getString(0, "MetaKeywords")));
				MetaDescription.put(url, formatString(dt.getString(0, "MetaDescription")));
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 处理字符串null值. 
	 * @param text
	 * @return
	 */
	private static String formatString(String text) {
		if (StringUtil.isEmpty(text)) {
			return "";
		}
		return text;
	}
	
	public static void clean(){
		
		if (catalogName != null && catalogName.size()>=1) {
	        catalogName.clear();
		}
		if (acticleTile != null && acticleTile.size()>=1) {
			acticleTile.clear();
		}
		if (MetaKeywords != null && MetaKeywords.size()>=1) {
			MetaKeywords.clear();
		}
		if (MetaDescription != null && MetaDescription.size()>=1) {
			MetaDescription.clear();
		}
	}

}
