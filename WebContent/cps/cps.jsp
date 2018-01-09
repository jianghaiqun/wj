
<%@page import="cn.com.sinosoft.action.shop.CpsAction"%>
<%@page import="cn.com.sinosoft.util.CookieUtil"%>
<%@page import="com.sinosoft.framework.utility.LogUtil"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%
	Logger logger = LoggerFactory.getLogger(this.getClass());
	String cpsUserId = "";
	String cpsUserUrl = "";
	String banner_id = "";
	String cpsUserSource = "";
	String partners_uid = "";
	String articleid = "";
	String channelSn = "";
	// 多麦 星盟 盘石 亿起发 领克特
    String cps_cps_code = " code_dm001 code_dm002 code_linkstars code_ps_pc code_ps_wap code_01 code_02 code_6666 code_8888 ";
	int CookieDay = 30;

	//分销联盟渠道记录
	cpsUserId = request.getParameter("cpsUserId");
	//cps用户URL
	cpsUserUrl = request.getParameter("cpsUserUrl");
	//维系统计用
	banner_id = request.getParameter("banner_id");
	// articleid 详细页 链接地址 变成 文章id 如果文章id存在，URL=文章对应的链接
	articleid = request.getParameter("aid");
	
	if (StringUtil.isNotEmpty(articleid)) {
		cpsUserUrl = CpsAction.articleURL(articleid);
	}

	//新渠道接入
	if (StringUtil.isEmpty(cpsUserId)) {

		String partners_UserId = request.getParameter("source");
		String partners_Url = request.getParameter("url");
		partners_uid = request.getParameter("uid");

		String CookieTime = CpsAction.isPartners(partners_UserId, "1");

		if (StringUtil.isNotEmpty(CookieTime)) {
			cpsUserId = partners_UserId;
			cpsUserUrl = partners_Url;
			try {
				CookieDay = Integer.valueOf(CookieTime.trim());
			} catch (Exception e) {
				CookieDay = 30;
			}
		} else {
			logger.error("source:{}|url:{} 未查询到合作商信息!查看 Table : PartnersManage 核对信息!", partners_UserId, partners_Url);
			return;
		}

	}

	//cps用户链接来源  链接转换
	cpsUserSource = request.getParameter("cpsUserSource");
	//获取访问路径
	String URL = request.getParameter("reUrl");
	// 获取访问用户
	String LastUrl = request.getParameter("LastUrl");
	// 合作社标志
	String globalflag = request.getParameter("globalflag");

	if (StringUtil.isNotEmpty(LastUrl)) {
		session.setAttribute("LastUrl", LastUrl);
	}
	// cpsUserSource 只有网站商务联盟有cpsUserSource； 合作方、广告联盟没有cpsUserSource 
	if (StringUtil.isNotEmpty(cpsUserSource)) {
		if (cpsUserSource.endsWith("dlr")) {
			session.setAttribute("channel", "AGENT");
			channelSn = "cps_dlr";
	
		} else {
			session.setAttribute("channel", "CPS");
			channelSn = "cps_swpt";
			
		}
	}

	try {

		if (StringUtil.isEmpty(cpsUserId) || StringUtil.isEmpty(cpsUserUrl)) {
			System.out.println("CPS用户ID：" + cpsUserId);
			System.out.println("用户跳转链接：" + cpsUserSource);

			System.out.println("**************客户端基本信息*****************");
			String uri = request.getRequestURI();//返回请求行中的资源名称
			String url = request.getRequestURL().toString();//获得客户端发送请求的完整url
			String ip = request.getRemoteAddr();//返回发出请求的IP地址
			String params = request.getQueryString();//返回请求行中的参数部分
			String referer = request.getHeader("referer");
			String host = request.getRemoteHost();//返回发出请求的客户机的主机名
			int port = request.getRemotePort();//返回发出请求的客户机的端口号。

			System.out.println("referer：" + referer);
			System.out.println("请求URL：" + url);
			System.out.println("请求IP：" + ip);
			System.out.println("请求参数：" + params);
			System.out.println("请求主机名：" + host + "||端口：" + port);

			System.out.println("**************客户端基本信息打印完***********");

			// referer 作用.
			//1，防止盗连，比如我是个下载软件的网站，在下载页面我先用referer来判断上一页面是不是自己网站，如果不是，说明有人盗连了你的下载地址。
			//2，电子商务网站的安全，我在提交信用卡等重要信息的页面用referer来判断上一页是不是自己的网站，如果不是，可能是黑客用自己写的一个表单，来提交，为了能跳过你上一页里的javascript的验证等目的。

		}
	} catch (Exception e) {
		System.out.println("打印参数异常!");
	}

	if (StringUtil.isNotEmpty(globalflag)) {
		session.setAttribute("globalflag", globalflag); 
	}

	//新进渠道
	if (StringUtil.isNotEmpty(partners_uid)) {
		channelSn = "cps_"+cpsUserId;
	}

	//取得cookie中原有的channelSn
	Cookie ck = CookieUtil.getCookieByName(request, "channelSn");
	
	String oldChannelSn = "";
	if(!StringUtil.isEmpty(ck) && StringUtil.isNotEmpty(ck.getValue())){
		oldChannelSn = ck.getValue();
	} 
	
	if(StringUtil.isNotEmpty(oldChannelSn) && cps_cps_code.indexOf("code_"+cpsUserId)!=-1){
		//如果新进渠道为分销联盟
		if(channelSn.endsWith("dlr") || channelSn.endsWith("dlr_wap") || channelSn.endsWith("swpt") || channelSn.endsWith("swpt_wap")){
				CookieUtil.addCookie(response, "channelSn", channelSn, (3600 * 24 * CookieDay));
				CookieUtil.addCookie(response, "cpsUserId", cpsUserId, (3600 * 24 * CookieDay));
				CookieUtil.addCookie(response, "cpsUserSource", cpsUserSource, (3600 * 24 * CookieDay));
				CookieUtil.addCookie(response, "partners_uid", "", (3600 * 24 * CookieDay));
		//如果cookie中渠道为分销联盟
		}else if(oldChannelSn.endsWith("dlr") || oldChannelSn.endsWith("dlr_wap") || oldChannelSn.endsWith("swpt") || oldChannelSn.endsWith("swpt_wap")){
				CookieUtil.addCookie(response, "channelSn", oldChannelSn, (3600 * 24 * CookieDay));
				CookieUtil.addCookie(response, "cpsUserId", cpsUserId, (3600 * 24 * CookieDay));
				CookieUtil.addCookie(response, "cpsUserSource", cpsUserSource, (3600 * 24 * CookieDay));
				CookieUtil.addCookie(response, "partners_uid", "", (3600 * 24 * CookieDay));
		} 
	}else{
		CookieUtil.addCookie(response, "cpsUserId", cpsUserId, (3600 * 24 * CookieDay));
		CookieUtil.addCookie(response, "cpsUserSource", cpsUserSource, (3600 * 24 * CookieDay));
		CookieUtil.addCookie(response, "partners_uid", partners_uid, (3600 * 24 * CookieDay));
		CookieUtil.addCookie(response, "channelSn", channelSn, (3600 * 24 * CookieDay));
	}
	
	StringBuffer SURL = new StringBuffer(cpsUserUrl);
	
	if (StringUtil.isNotEmpty(cpsUserSource)) {
	//渠道分类
			if (cpsUserSource.endsWith("dlr")) {
				CookieUtil.addCookie(response, "cpstype", "AGENT", (3600 * 24 * CookieDay));
				SURL.append("?AGENT");
	
			} else {
				CookieUtil.addCookie(response, "cpstype", "CPS", (3600 * 24 * CookieDay));
				if (StringUtil.isNotEmpty(banner_id) && !"null".equals(banner_id)) {
					SURL.append("?banner_id=" + banner_id);
					SURL.append("&cpsUserCode=" + cpsUserId);//达人文章 适配wap 
					SURL.append("&cpsUserSource=" + cpsUserSource);
				} else {
					SURL.append("?1=1");
					SURL.append("&cpsUserCode=" + cpsUserId);
					SURL.append("&cpsUserSource=" + cpsUserSource);
				}
		
			}
		
		if (StringUtil.isNotEmpty(URL) && !"null".equals(URL)) {
				SURL.append("@" + URL);
		}
		if (StringUtil.isNotEmpty(LastUrl) && !"null".equals(LastUrl)) {
				SURL.append("@" + LastUrl);
		}
		
	}
	
	response.sendRedirect(SURL.toString());
%>

