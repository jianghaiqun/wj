package com.sinosoft.cms.stat;

import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRecordStat {
	protected static final Logger logger = LoggerFactory.getLogger(AbstractRecordStat.class);
	/**
	 * 是否是一个新的时间段，新时间段需要插入记录
	 */
	protected boolean isNewMonth = false;

	/**
	 * 初始化统计项，主要是从持久层获取记录项的值
	 * 此处不需要从持久层加载
	 */
	public void init() {

	}

	/**
	 * 根据一次访问的页面参数，记录各明细项
	 */
	public abstract void deal(Visit v);

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
			"Windows Mobile", "MontaVista Linux"};
	public String checkMobile(Visit v) {
		String terminalType = "";
		String userAgent = v.UserAgent;
		String url = v.URL;
		if(url != null){
			for (String mobile : MOBILE_SPECIFIC_SUBSTRING) {
				if (userAgent.contains(mobile) || userAgent.contains(mobile.toUpperCase()) || userAgent.contains(mobile.toLowerCase())) {
					if (url.contains("wap")) {
						terminalType = "010";// Mobile-wap
						break;
					} else if(url.contains("web")){
						terminalType = "011";// Mobile-web
						break;
					} else {
						terminalType = "012";// Mobile-application
						break;
					}
				} else {
					if (url.contains("wap")) {
						terminalType = "020";// PC-wap
						break;
					} else if(url.contains("web")){
						terminalType = "021";// PC-web
						break;
					} else {
						terminalType = "022";// PC-application
						break;
					}
				}
			}
		}
		return terminalType;
	}
	
	//获得访问入口
	public static String[] getSearchEngine(Visit v) {
		String url = v.URL;
		if (StringUtil.isEmpty(url)) {
			return null;
		}
		// 先处理本站搜索
		if (url.indexOf("Result.jsp") > 0) {
			String keyword = SearchAPI.getParameter(url, "query");
			return new String[] { "Host", keyword };
		}
		url = v.Referer;
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
}
