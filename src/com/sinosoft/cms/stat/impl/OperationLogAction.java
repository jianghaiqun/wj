package com.sinosoft.cms.stat.impl;

import cn.com.sinosoft.entity.Member;
import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.cms.stat.StatUtil;
import com.sinosoft.cms.stat.Visit;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDInterActionSchema;
import com.sinosoft.schema.SDInterActionSet;
import com.sinosoft.schema.UserOperLogSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 用户操作日志调用类
 */
public class OperationLogAction {
	private static final Logger logger = LoggerFactory.getLogger(OperationLogAction.class);
	/**
	 * 获得日志参数
	 * @param request
	 * @param operCode
	 * @return
	 */
	private Visit getVisitData(HttpServletRequest request, String operCode) {
		Visit tVisit = new Visit();
		long current = System.currentTimeMillis();
		Mapx map = ServletUtil.getParameterMap(request, false);
		map.put("IP", StatUtil.getIP(request));
		map.put("UserAgent", request.getHeader("User-Agent"));
		tVisit.UserAgent = map.getString("UserAgent");

		tVisit.IP = map.getString("IP");
		if(StringUtil.isNotEmpty(map.getString("sDate"))){
			tVisit.StartTime = Long.parseLong(map.getString("sDate"));
		} else{
			tVisit.StartTime = current;
		}
		tVisit.LeaveTime = current;
		if(StringUtil.isEmpty(map.getString("URL"))){
			tVisit.URL = request.getRequestURL().toString();
		}else{
			tVisit.URL = map.getString("URL");
		}
		tVisit.SessionID = request.getSession().getId();
		HttpSession session = request.getSession();
		tVisit.MemberID = session.getAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME) == null ? "000000" : session.getAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME).toString();
		//获取渠道编码
		if(StringUtil.isNotEmpty(tVisit.URL)){
			tVisit.channel = SearchAPI.getParameter(tVisit.URL, "channel");
			if(StringUtil.isEmpty(tVisit.channel)){
				tVisit.channel = getChannelData(tVisit, request);
			} else {
				request.getSession().setAttribute("channel", tVisit.channel);
			}
		} else{
			tVisit.channel = getChannelData(tVisit, request);
		}

		if (StringUtil.isNotEmpty(tVisit.URL)) {
			tVisit.URL = tVisit.URL.replace('\'', '0').replace('\\', '0');
			String prefix = tVisit.URL.substring(0, 8);
			String tail = tVisit.URL.substring(8);
			tail = tail.replaceAll("/+", "/");
			tVisit.URL = prefix + tail;
		}
		if (StringUtil.isEmpty(tVisit.UserAgent)) {
			tVisit.UserAgent = "Unknow";
		}
		tVisit.District = StatUtil.getDistrictCode(tVisit.IP);
		tVisit.StayTime = map.getString("StayTime");//明细记录页面停留时间
		tVisit.IsJumpOrClose = map.getString("IsJOrC");
		tVisit.OrderSn=map.getString("OrderSn");
		return tVisit;
	}

	public void saveOperLog(HttpServletRequest request,	String operCode) {
		try {
			Visit v = this.getVisitData(request, operCode);
			SDInterActionSchema tSDInterActionSchema = new SDInterActionSchema();
			//判断动作存在性
			QueryBuilder qb = new QueryBuilder("where ActionId=?");
			qb.add(operCode);
			SDInterActionSet tSDInterActionSet = tSDInterActionSchema.query(qb);
			if (tSDInterActionSet.size() > 0) {
				Transaction transaction = new Transaction();
				UserOperLogSchema tUserOperLogSchema = new UserOperLogSchema();
				tUserOperLogSchema.setID(NoUtil.getMaxID("UserOperLogID") + "");
				tUserOperLogSchema.setIP(v.IP);
				if(v.StartTime != null){
					tUserOperLogSchema.setAccessDate(DateUtil.toString(new Date(v.StartTime), "yyyy-MM-dd"));
					tUserOperLogSchema.setAccessTime(DateUtil.toTimeString(new Date(v.StartTime)));
				}
				if(v.LeaveTime != null){
					tUserOperLogSchema.setLeaveDate(DateUtil.toString(new Date(v.LeaveTime), "yyyy-MM-dd"));
					tUserOperLogSchema.setLeaveTime(DateUtil.toTimeString(new Date(v.LeaveTime)));
				}
				tUserOperLogSchema.setAccessPage(v.URL);// 访问页面
				tUserOperLogSchema.setSessionID(v.SessionID);
				tUserOperLogSchema.setArea(v.District);
				tUserOperLogSchema.setMemberID(v.MemberID);
				tUserOperLogSchema.setTerminalType(this.checkMobile(v));
				tUserOperLogSchema.setChannel(v.channel);
				tUserOperLogSchema.setStayTime(v.StayTime);
				tUserOperLogSchema.setOperation(operCode);// 具体操作
				tUserOperLogSchema.setIsJumpOrClose(v.IsJumpOrClose);
				tUserOperLogSchema.setOrderNo(v.OrderSn);
				transaction.add(tUserOperLogSchema, Transaction.INSERT);
				transaction.commit();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	// 通过url和userAgent判断终端访问类型
	static final String[] MOBILE_SPECIFIC_SUBSTRING = { "iPad", "iPhone",
			"Android", "MIDP", "Opera Mobi", "Opera Mini", "BlackBerry",
			"HP iPAQ", "IEMobile", "MSIEMobile", "Windows Phone", "HTC", "LG",
			"MOT", "Nokia", "Symbian", "Fennec", "Maemo", "Tear", "Midori",
			"armv", "Windows CE", "WindowsCE", "Smartphone", "240x320",
			"176x220", "320x320", "160x160", "webOS", "Palm", "Sagem",
			"Samsung", "SGH", "SIE", "SonyEricsson", "MMP", "UCWEB",
			"Windows Mobile", "MontaVista Linux" };

	private String checkMobile(Visit v) {
		String terminalType = "";
		String userAgent = v.UserAgent;
		String url = v.URL;
		if(StringUtil.isNotEmpty(url)){
			for (String mobile : MOBILE_SPECIFIC_SUBSTRING) {
				if (userAgent.contains(mobile)
						|| userAgent.contains(mobile.toUpperCase())
						|| userAgent.contains(mobile.toLowerCase())) {
					if (url.contains("wap")) {
						terminalType = "010";// Mobile-wap
						break;
					} else if(url.contains("www")){
						terminalType = "011";// Mobile-web
						break;
					} else{
						terminalType = "012";// Mobile-app
						break;
					}
				} else {
					if (url.contains("wap")) {
						terminalType = "020";// PC-wap
						break;
					} else if(url.contains("www")) {
						terminalType = "021";// PC-web
						break;
					} else {
						terminalType = "022";// PC-app
						break;
					}
				}
			}
		}
		return terminalType;
	}
	
	/**
	 * 获取渠道编码，并存入session中
	 */
	private String getChannelData(Visit v,HttpServletRequest request){
		String channel = "";
		try {
			if(request.getSession().getAttribute("channel") != null){
				channel = request.getSession().getAttribute("channel").toString();
			} else {
				String engine = getSearchEngine(v);
				if(engine != null){
					String tType = checkMobile(v);
					channel = tType+"-"+engine;
				}
				request.getSession().setAttribute("channel",channel);
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return channel;
	}
	
	/**
	 * 解析URL,Referer,获取搜索引擎类型
	 * @param v
	 * @return
	 */
	public static String getSearchEngine(Visit v) {
		String url = v.URL;
		if (StringUtil.isEmpty(url)) {
			return null;
		}
		// 先处理本站搜索
		if (url.indexOf("Result.jsp") > 0) {
			return "Host";
		}
		if (StringUtil.isEmpty(v.Referer)) {
			return null;
		}
		url = v.Referer;
		String domain = StatUtil.getDomain(url);
		String name = null;
		if (domain.indexOf("baidu.") > 0) {
			name = "baidu";
		} else if (domain.indexOf("google.") > 0) {
			name = "google";
		} else if (domain.indexOf("yahoo.") > 0) {
			name = "yahoo";
		} else if (domain.indexOf("msn.") > 0) {
			name = "MSN";
		} else if (domain.indexOf("soso.") > 0) {
			name = "soso";
		} else if (domain.indexOf("sogou.") > 0) {
			name = "sogou";
		} else if (domain.indexOf("zhongsou.") > 0) {
			name = "zhongsou";
		} else if (domain.indexOf("youdao.") > 0) {
			name = "youdao";
		} else if (domain.indexOf("live.") > 0) {
			name = "Live";
		} else if (domain.indexOf("bing.") > 0) {
			name = "Bing";
		} else if (domain.indexOf("jike.") > 0) {
			name = "jike";
		} else if (domain.indexOf("pangusou.") > 0) {
			name = "pangusou";
		} else if (domain.indexOf("gougou.") > 0) {
			name = "gougou";
		} else if (domain.indexOf("qihoo.") > 0) {
			name = "qihoo";
		} else if (domain.indexOf("360.") > 0) {
			name = "360";
		} else {
			name = "Host";
		}
		return name;
	}
}
