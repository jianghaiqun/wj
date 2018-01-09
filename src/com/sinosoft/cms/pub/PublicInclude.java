package com.sinosoft.cms.pub;

import java.io.File;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import com.sinosoft.cms.template.TagParser;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Current;
import com.sinosoft.framework.User;
import com.sinosoft.framework.extend.ExtendManager;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCSiteSchema;

/**
 * 公共包含类，对于于Include/Top.jsp,Include/Bottom.jsp,Include/Head.jsp<br>
 * 用于留言、搜索、论坛、商城等动态应用统一头部和尾部
 * 
 */
public class PublicInclude {
	private static Mapx timeMap = new Mapx(3000);// 最多缓存3000个文件
	private static Mapx fileMap = new Mapx(3000);
	private static Object mutex = new Object();

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
	 * 返回会员中心左侧菜单
	 */
	public static void getMenu(HttpServletRequest request, JspWriter out) throws Exception {
		String cur = request.getParameter("cur");
		if (StringUtil.isEmpty(cur)) {
			cur = "Menu_MI";
		}
		String siteID = getSiteID(request);
		if (StringUtil.isEmpty(siteID)) {
			out.println("URL中必须带有SiteID参数!");
			return;
		}
		StringBuffer sb = new StringBuffer();
		if ("Y".equals(CMSCache.getSite(siteID).getAllowContribute())) {
			sb.append("<ul class='sidemenu'>");
			sb.append("<li id='Menu_WA'><a href='" + Config.getContextPath()
					+ "Member/WriteArticle.jsp?cur=Menu_WA&SiteID=" + siteID + "'>网站文章投稿</a></li>");
			sb.append("<li id='Menu_MA'><a href='" + Config.getContextPath()
					+ "Member/MemberArticles.jsp?cur=Menu_MA&SiteID=" + siteID + "'>我投稿的文章</a></li>");
			sb.append("</ul>");
		}
		sb.append("<ul class='sidemenu'>");
		ExtendManager.executeAll("Member.Menu", new Object[] { sb, siteID, request });
		sb.append("<li id='Menu_MI'><a href='" + Config.getContextPath() + "Member/MemberInfo.jsp?cur=Menu_MI&SiteID="
				+ siteID + "'>编辑个人资料</a></li>");
		sb.append("<li id='Menu_PW'><a href='" + Config.getContextPath() + "Member/Password.jsp?cur=Menu_PW&SiteID="
				+ siteID + "'>修改密码</a></li>");
		sb.append("</ul>");
		sb.append("<hr class='shadowline'/>");
		sb.append("<ul class='sidemenu'>");
		sb.append("<li id='Menu_Logout'><a href='javascript:void(0)' onclick='doLogout();'>退出</a></li>");
		sb.append("</ul>");
		sb.append("<script type='text/javascript'>");
		sb.append("document.getElementById('" + cur + "').className='current'");
		sb.append("</script>");
		out.write(sb.toString());
	}

	public static String getSiteID(HttpServletRequest request) {
		String siteID = request.getParameter("SiteID");
		if (StringUtil.isEmpty(siteID)) {
			siteID = request.getParameter("site");
			if (StringUtil.isEmpty(siteID)) {
				siteID = (String) User.getValue("SiteID");
				if (StringUtil.isEmpty(siteID)) {
					siteID = (String) Current.getVariable("SiteID");
				}
			}
		}
		return siteID;
	}

	/**
	 * 返回公共头部HTML
	 */
	public static void getHead(HttpServletRequest request, JspWriter out) throws Exception {
		String siteID = getSiteID(request);
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
		String template = site.getHeaderTemplate();
		if (StringUtil.isEmpty(template)) {
			return;// 不输出
		} else {
			if (template.startsWith("/")) {
				template = template.substring(1);
			}
			template = template.substring(template.indexOf('/') + 1);
		}
		path = path + "/include/" + template;
		String html = getHtml(site.getAlias(), path);
		out.write(html);
	}

	/**
	 * 返回公共顶部HTML
	 */
	public static void getTop(HttpServletRequest request, JspWriter out) throws Exception {
		String siteID = getSiteID(request);
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
		String template = site.getTopTemplate();
		if (StringUtil.isEmpty(template)) {
			template = "head.html";
		} else {
			if (template.startsWith("/")) {
				template = template.substring(1);
			}
			template = template.substring(template.indexOf('/') + 1);
		}
		path = path + "/include/" + template;
		path = Config.getContextRealPath()+"wwwroot/kxb/block/kxb_header.shtml";
		String html = getHtml(site.getAlias(), path);
		out.write(html);
	}

	/**
	 * 返回区块HTML
	 */
	public static void getBlock(HttpServletRequest request, String fileName, JspWriter out) throws Exception {
		String siteID = getSiteID(request);
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
		String html = getHtml(site.getAlias(), path + fileName);
		out.write(html);
	}

	/**
	 * 返回公共底部HTML
	 */
	public static void getBottom(HttpServletRequest request, JspWriter out) throws Exception {
		String siteID = getSiteID(request);
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
		path = Config.getContextRealPath()+"wwwroot/kxb/block/kxb_footer.shtml";
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
		content = StringUtil.replaceAllIgnoreCase(content, "\\$\\{level\\}", "");
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
			sb.append(" " + m.group(1) + "=" + separator + dealPath + separator);
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
		String prefix = (Config.getContextPath() + Config.getValue("Statical.TargetDir") + "/" + alias + "/")
				.replaceAll("/+", "/");
		if (url.startsWith("../")) {
			return prefix + url.substring(3);
		} else {
			if (url.indexOf(':') > 0 || url.indexOf(')') > 0 || url.indexOf('<') > 0 || url.indexOf('#') > 0) {
				return url;
			}
			return prefix + url;
		}
	}
}
