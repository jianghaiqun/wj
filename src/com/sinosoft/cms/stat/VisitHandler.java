package com.sinosoft.cms.stat;

import cn.com.sinosoft.entity.Member;
import com.sinosoft.cms.api.SearchAPI;
import com.sinosoft.cms.stat.impl.CatalogStat;
import com.sinosoft.cms.stat.impl.ClientStat;
import com.sinosoft.cms.stat.impl.DistrictStat;
import com.sinosoft.cms.stat.impl.GlobalStat;
import com.sinosoft.cms.stat.impl.HourStat;
import com.sinosoft.cms.stat.impl.LeafStat;
import com.sinosoft.cms.stat.impl.PVStatisticsStat;
import com.sinosoft.cms.stat.impl.SourceStat;
import com.sinosoft.cms.stat.impl.URLStat;
import com.sinosoft.cms.stat.impl.UVAccessStatisticsStat;
import com.sinosoft.cms.stat.impl.UserOperLogStat;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 统计模块入口，前台会调用本类的deal()方法
 * 
 */
public class VisitHandler {
	private static final Logger logger = LoggerFactory.getLogger(VisitHandler.class);

	private static final long serialVersionUID = 1L;

	private static AbstractStat[] items;// 各个统计项
	
	private static AbstractRecordStat[] itemRs;//各个记录项

	private static long lastTime = System.currentTimeMillis();// 上次访问时间

	public static final long HOUR = 60 * 60 * 1000;

	public static final long DAY = 24 * HOUR;

	private static boolean isUpdating;// 是否正在更新中

	private static boolean isSimulating;//

	private static Object mutex = new Object();

	/**
	 * 被StatUpdateTask定时调用
	 */
	public static void update(final long current, final boolean isNewPeriod, boolean waitFlag) {// Wait标记只有在模拟产生数据时才有用
		VisitCount vc = null;
		while (waitFlag && isUpdating) {
			try {
				vc = (VisitCount) VisitCount.getInstance().clone();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
		if (!isUpdating) {
			synchronized (mutex) {
				if (!isUpdating) {
					if (vc == null) {
						vc = (VisitCount) VisitCount.getInstance().clone();
					}
					isUpdating = true;
					final VisitCount vc2 = vc;
					final boolean[] mArr = new boolean[items.length];
					for (int i = 0; i < items.length; i++) {
						mArr[i] = items[i].isNewMonth;
					}
					new Thread() {
						public void run() {
							logger.info("正在更新统计信息......");
							long current = System.currentTimeMillis();
							Transaction tran = new Transaction();
							try {
								for (int i = 0; i < items.length; i++) {
									items[i].update(tran, vc2, current, mArr[i], isNewPeriod);// 必须传入isNewMonth，因为原值可以己改变
								}
								tran.commit();
							} catch (Throwable t) {
								logger.error(t.getMessage(), t);
							} finally {
								isUpdating = false;
							}
							logger.info("更新统计信息耗时 {} 毫秒.", (System.currentTimeMillis() - current));
						}
					}.start();
				}
			}
		}
	}

	private static String CurrentPeriod;

	/**
	 * 更换统计时段
	 */
	public static void changePeriod(int type, long current) {
		synchronized (mutex) {
			String str = DateUtil.toString(new Date(current));
			if (str.equals(CurrentPeriod)) {
				return;
			}
			CurrentPeriod = str;
			if (isSimulating) {
				update(current, true, true);
			} else {
				update(current, true, false);
			}
			for (int i = 0; i < items.length; i++) {
				items[i].onPeriodChange(type, current);
			}
		}
	}

	/**
	 * 初始化统计项
	 */
	public static void init(long current) {
		if (items == null) {
			synchronized (mutex) {
				if (items == null) {
					AbstractStat[] arr = new AbstractStat[] { new ClientStat(), new GlobalStat(), new LeafStat(),
							new HourStat(), new SourceStat(), new CatalogStat(), new URLStat(), new DistrictStat()};
					for (int i = 0; i < arr.length; i++) {
						arr[i].init();
					}
					lastTime = current;
					items = arr;// 不能一开始就给items赋值，否则一部分线程会在统计项未初始化完毕之前开始调用统计项的deal()方法lastTime
					// = v.VisitTime;
				}
			}
		}
	}

	/**
	 * 处理一次点击,本方法仅用于测试,以产生历史数据
	 */
	public static void deal(Visit v) {
		init(v.VisitTime);
		long current = v.VisitTime;
		synchronized (mutex) {
			if (!DateUtil.toString(new Date(current)).equals(DateUtil.toString(new Date(lastTime)))) {
				changePeriod(AbstractStat.PERIOD_DAY, current);
			}
			lastTime = current;
		}
		for (int i = 0; i < items.length; i++) {
			items[i].deal(v);
		}
	}
	
	/**
	 * 获取渠道编码，并存入session中
	 */
	private static String getChannelData(Visit v,HttpServletRequest request){
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
		if(StringUtil.isNotEmpty(url)){
			for (String mobile : MOBILE_SPECIFIC_SUBSTRING) {
				if (userAgent.contains(mobile) || userAgent.contains(mobile.toUpperCase()) || userAgent.contains(mobile.toLowerCase())) {
					if (url.contains("wap")) {
						terminalType = "mobileWap";// Mobile-wap
						break;
					} else if(url.contains("www")){
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
					} else if(url.contains("www")){
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
	 * 获得两时间点之间的时间间隔
	 * @param startTime
	 * @param endTime
	 * @return 秒
	 */
	private static String getTimeInterval(String startTime, String endTime){
		String timeInterval = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 将截取到的时间字符串转化为时间格式的字符串
			Date sTime = sdf.parse(startTime);
			Date eTime = sdf.parse(endTime);
			// 默认为毫秒，除以1000是为了转换成秒
			timeInterval = String.valueOf(((eTime.getTime() - sTime.getTime())/1000));// 秒
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return timeInterval;
	}
	
	/**
	 * 处理一次HTTP请求
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void deal(HttpServletRequest request, HttpServletResponse response) {
		try {
			long current = System.currentTimeMillis();
			/*if (items == null) {
				synchronized (mutex) {
					if (items == null) {
						init(current);
					}
				}
			}
			synchronized (mutex) {
				if (!DateUtil.toString(new Date(current)).equals(DateUtil.toString(new Date(lastTime)))) {
					changePeriod(AbstractStat.PERIOD_DAY, current);
				}
				lastTime = current;
			}*/
			/*
			 * try { v.SiteID = Long.parseLong(map.getString("SiteID")); } catch
			 * (Exception e) { try {
			 * response.getWriter().println("alert('统计时发生错误:" +
			 * map.getString("SiteID") + "不是正确的SiteID')"); } catch (IOException
			 * e1) { e1.printStackTrace(); } return; }
			 */
			/*
			 * v.UniqueID = ServletUtil.getCookieValue(request, "UniqueID"); if
			 * ("KeepAlive".equalsIgnoreCase(map.getString("Event"))) {
			 * GlobalStat.keepAlive(v.SiteID, v.UniqueID, current); return;//
			 * 仅仅是表明本次访问尚未结束 } v.Type = map.getString("Type"); if
			 * (StringUtil.isEmpty(v.Type)) { v.Type = "Other"; } v.LeafID =
			 * StringUtil.isNotEmpty(map.getString("LeafID")) ?
			 * Long.parseLong(map.getString("LeafID")) : 0;
			 * if(StringUtil.isNotEmpty(map.getString("vq"))){ v.Frequency =
			 * Integer.parseInt(map.getString("vq")); }
			 */

			Mapx map = ServletUtil.getParameterMap(request, false);
			map.put("IP", StatUtil.getIP(request));
			map.put("UserAgent", request.getHeader("User-Agent"));
			Visit v = new Visit();
			v.UserAgent = map.getString("UserAgent");
			v.Operation = map.getString("Operation");//用来保存查看产品动作编码
			v.Event = map.getString("Event");
			v.IP = map.getString("IP");
			v.VisitTime = current;
			v.SessionID = request.getSession().getId();
			HttpSession session = request.getSession();
			v.MemberID = session.getAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME) == null ? "000000" : session.getAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME).toString();

			// 此处必须特别处理，不然攻击者可能构造特别的Referer和URL来进行SQL注入
			v.URL = map.getString("URL");
			if (StringUtil.isNotEmpty(v.URL)) {
				v.URL = v.URL.replace('\'', '0').replace('\\', '0');
				String prefix = v.URL.substring(0, 8);
				String tail = v.URL.substring(8);
				tail = tail.replaceAll("/+", "/");
				v.URL = prefix + tail;
			}

			v.Referer = map.getString("Referer");
			if (StringUtil.isNotEmpty(v.Referer)) {
				v.Referer = v.Referer.replace('\'', '0').replace('\\', '0');
			}else {
				v.Referer = "Host";
			}
			
			if (StringUtil.isEmpty(v.UserAgent)) {
				v.UserAgent = "Unknow";
			}
			v.OS = StatUtil.getOS(v.UserAgent);
			v.Browser = StatUtil.getBrowser(v.UserAgent);
			v.ColorDepth = map.getString("cd");
			v.District = StatUtil.getDistrictCode(v.IP);
			v.FlashVersion = map.getString("fv");
			v.FlashEnabled = StringUtil.isEmpty(v.FlashVersion);
			v.Host = map.getString("Host");
			if (StringUtil.isNotEmpty(v.Host)) {
				v.Host = v.Host.toLowerCase();
			} else {
				v.Host = "无";
			}
			v.CookieEnabled = "1".equals(map.getString("ce")) ? true : false;
			v.JavaEnabled = "1".equals(map.getString("je")) ? true : false;
			v.Language = StatUtil.getLanguage(map.getString("la"));
			if (v.Language.equals("其他")) {
				v.Language = StatUtil.getLanguage("; " + request.getHeader("accept-language") + ";");
			}
			v.Screen = map.getString("sr");
			if (!"Unload".equalsIgnoreCase(map.getString("Event"))) {// 正常点击
				/*try {
					String sites = ServletUtil.getCookieValue(request, "Sites");
					if (StringUtil.isEmpty(v.UniqueID)) {
						v.UniqueID = StatUtil.getUniqueID();
						v.RVFlag = false;
						ServletUtil.setCookieValue(request, response, "UniqueID", -1, v.UniqueID);
						ServletUtil.setCookieValue(request, response, "Sites", -1, "_" + v.SiteID);
					} else {
						if (StringUtil.isNotEmpty(sites) && sites.indexOf("_" + v.SiteID) >= 0) {
							v.RVFlag = true;
						} else {
							v.RVFlag = false;
							ServletUtil.setCookieValue(request, response, "Sites", -1, sites + "_" + v.SiteID);
						}
					}
					GlobalStat.dealUniqueID(v);
					v.IPFlag = !GlobalStat.isTodayVisited(String.valueOf(v.SiteID), v.IP, v.VisitTime);
				} catch (Exception ex) {
					ex.printStackTrace();
				}*/
			} else {
				//统计项页面停留时间
				/*try {
					if(map.getString("StickTime") != null){
						v.StickTime = new Double(Math.ceil(Double.parseDouble(map.getString("StickTime")))).intValue();
					}
				} catch (Exception e) {
					v.StickTime = 10;
				}
				if (v.StickTime < 0) {
					v.StickTime = 10;
				}*/
				
				if(StringUtil.isNotEmpty(map.getString("sDate"))){
					v.StartTime = Long.parseLong(map.getString("sDate"));
				}
				v.LeaveTime = current;
				if(StringUtil.isNotEmpty(map.getString("StayTime"))){
					v.StayTime = map.getString("StayTime");//明细记录页面停留时间 
				} else {
					v.StayTime = getTimeInterval(DateUtil.toString(new Date(map.getString("sDate")), "yyyy-MM-dd HH:mm:ss"),DateUtil.toString(new Date(v.LeaveTime), "yyyy-MM-dd HH:mm:ss"));
				}
				v.IsJumpOrClose = map.getString("IsJOrC");
				
				//获取渠道编码
				if(StringUtil.isNotEmpty(v.URL)){
					v.channel = SearchAPI.getParameter(v.URL, "channel");
					if(StringUtil.isEmpty(v.channel)){
						v.channel = getChannelData(v, request);
					} else {
						request.getSession().setAttribute("channel", v.channel);
					}
				} else{
					v.channel = getChannelData(v, request);
				}
				
				//每次页面跳转或关闭时，处理各记录项（PV统计量、UV访问量）。
				itemRs = new AbstractRecordStat[] { new PVStatisticsStat(), new UVAccessStatisticsStat(), new UserOperLogStat()};
				for (int j = 0; j < itemRs.length; j++){
					itemRs[j].deal(v);
				}
			}

			//处理各统计项
//			for (int i = 0; i < items.length; i++) {
//				items[i].deal(v);
//			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void setSimulating(boolean isSimulating) {
		VisitHandler.isSimulating = isSimulating;
	}
}
