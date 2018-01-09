package com.sinosoft.cms.stat;

import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统计模块入口，前台会调用本类的deal()方法
 * 
 */
public class VisitHandlerC {
	private static final Logger logger = LoggerFactory.getLogger(VisitHandlerC.class);

	/**
	 * 解析URL,Referer,获取搜索引擎类型
	 * 
	 * @param v
	 * @return
	 */
	public static String[] getSearchEngine(Visit v) {
		String url = v.Referer;
		String domain = StatUtil.getDomain(url);
		Mapx map = ServletUtil.getMapFromQueryString(url);
		String name = null;
		String keyword = null;
		if (domain.indexOf("baidu.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("wd"), "UTF-8");
			name = "baidu";
		} else if (domain.indexOf("google.") > 0) {
			String charset = map.getString("ie");
			if (StringUtil.isEmpty(charset)) {
				charset = "UTF-8";
			}
			keyword = StringUtil.urlDecode(map.getString("q"), charset);
			name = "google";
		} else if (domain.indexOf("yahoo.") > 0) {
			String charset = map.getString("ei");
			if (StringUtil.isEmpty(charset)) {
				charset = "UTF-8";
			}
			keyword = StringUtil.urlDecode(map.getString("p"), charset);
			name = "yahoo";
		} else if (domain.indexOf("msn.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("q"), "UTF-8");
			name = "MSN";
		} else if (domain.indexOf("soso.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("w"), "GBK");
			name = "soso";
		} else if (domain.indexOf("sogou.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("query"), "GBK");
			name = "sogou";
		} else if (domain.indexOf("zhongsou.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("w"), "GBK");
			name = "zhongsou";
		} else if (domain.indexOf("youdao.") > 0) {
			String charset = map.getString("ue");
			if (StringUtil.isEmpty(charset)) {
				charset = "UTF-8";
			}
			keyword = StringUtil.urlDecode(map.getString("q"), charset);
			name = "youdao";
		} else if (domain.indexOf("live.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("q"), "UTF-8");
			name = "Live";
		} else if (domain.indexOf("bing.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("q"), "UTF-8");
			name = "Bing";
		} else if (domain.indexOf("jike.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("q"), "UTF-8");
			name = "jike";
		} else if (domain.indexOf("pangusou.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("q"), "UTF-8");
			name = "pangusou";
		} else if (domain.indexOf("gougou.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("search"), "UTF-8");
			name = "gougou";
		} else if (domain.indexOf("qihoo.") > 0) {
			keyword = StringUtil.urlDecode(map.getString("kw"), "GBK");
			name = "qihoo";
		} else {
			return new String[] { "Host", "none"};
		}
		if (StringUtil.isNotEmpty(keyword)) {
			return new String[] { name, keyword };
		}
		
		return null;
	}
	/**
	 * 判断终端访问类型
	 */
	static final String[] MOBILE_SPECIFIC_SUBSTRING = { "iPad", "iPhone",
			"Android", "MIDP", "Opera Mobi", "Opera Mini", "BlackBerry",
			"HP iPAQ", "IEMobile", "MSIEMobile", "Windows Phone", "HTC", "LG",
			"MOT", "Nokia", "Symbian", "Fennec", "Maemo", "Tear", "Midori",
			"armv", "Windows CE", "WindowsCE", "Smartphone", "240x320",
			"176x220", "320x320", "160x160", "webOS", "Palm", "Sagem",
			"Samsung", "SGH", "SIE", "SonyEricsson", "MMP", "UCWEB",
			"Windows Mobile", "MontaVista Linux" };
	public static String checkMobile(Visit v) {
		String terminalType = "";
		String userAgent = v.UserAgent;
		String url = v.URL;
		if (StringUtil.isNotEmpty(url)) {
			for (String mobile : MOBILE_SPECIFIC_SUBSTRING) {
				if (userAgent.contains(mobile)
						|| userAgent.contains(mobile.toUpperCase())
						|| userAgent.contains(mobile.toLowerCase())) {
					if (url.contains("wap")) {
						terminalType = "mobileWap";// Mobile-wap
						break;
					} else if (url.contains("www")) {
						terminalType = "mobileWeb";// Mobile-web
						break;
					} else {
						terminalType = "mobileApp";// Mobile-application
						break;
					}
				} else {
					if (url.contains("wap")) {
						terminalType = "PCWap";// PC-wap
						break;
					} else if (url.contains("www")) {
						terminalType = "PCWeb";// PC-web
						break;
					} else {
						terminalType = "PCApp";// PC-application
						break;
					}
				}
			}
		}
		return terminalType;
	}
	/**
	 * 处理一次HTTP请求
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void deal(HttpServletRequest request,
			HttpServletResponse response)
	{
		try {
			Mapx map = ServletUtil.getParameterMap(request, false);
			map.put("UserAgent", request.getHeader("User-Agent"));
			// 此处必须特别处理，不然攻击者可能构造特别的Referer和URL来进行SQL注入
			Visit v = new Visit();
			v.URL = map.getString("URL");
			if (StringUtil.isNotEmpty(v.URL)) {
				v.URL = v.URL.replace('\'', '0').replace('\\', '0');
				String prefix = v.URL.substring(0, 8);
				String tail = v.URL.substring(8);
				tail = tail.replaceAll("/+", "/");
				v.URL = prefix + tail;
			}
//			if (StringUtil.isEmpty(v.UserAgent)) {
//				v.UserAgent = "Unknow";
//			}
//			v.OS = StatUtil.getOS(v.UserAgent);
//			v.Browser = StatUtil.getBrowser(v.UserAgent);
//			v.ColorDepth = map.getString("cd");
//			v.FlashVersion = map.getString("fv");
//			v.FlashEnabled = StringUtil.isEmpty(v.FlashVersion);
//			v.Host = map.getString("Host");
//			if (StringUtil.isNotEmpty(v.Host)) {
//				v.Host = v.Host.toLowerCase();
//			} else {
//				v.Host = "无";
//			}
//			v.CookieEnabled = "1".equals(map.getString("ce")) ? true : false;
//			v.JavaEnabled = "1".equals(map.getString("je")) ? true : false;
//			v.Language = StatUtil.getLanguage(map.getString("la"));
//			if (v.Language.equals("其他")) {
//				v.Language = StatUtil.getLanguage("; "
//						+ request.getHeader("accept-language") + ";");
//			}
//			v.Screen = map.getString("sr");
			// 获取渠道编码，渠道名称，关键字
			v.channelId = SearchAPI.getParameter(v.URL, "channelId");
			v.Referer = map.getString("Referer");
			if (StringUtil.isNotEmpty(v.Referer)) {
				String[] engine = getSearchEngine(v);
				v.channelWay =engine[0];//统计渠道名称
				String keyWord=engine[1];//搜索关键字
				request.getSession().setAttribute("channelWay",
						v.channelWay);
				request.getSession().setAttribute("keyWord", keyWord);
			} else {
				request.getSession().setAttribute("channelId", v.channelId);

				Cookie cookie = new Cookie("channelId", v.channelId);
				cookie.setMaxAge(60 * 60 * 24);
				cookie.setPath("/");
				response.addCookie(cookie);

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
