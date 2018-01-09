package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.SystemConfig;
import cn.com.sinosoft.entity.Footer;
import cn.com.sinosoft.entity.FriendLink;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Navigation;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.FooterService;
import cn.com.sinosoft.service.FriendLinkService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.NavigationService;
import cn.com.sinosoft.util.SystemConfigUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台Action类 - 前台基类
 * ============================================================================
 * 
 * 
 * 
 * 
 * 
 *
 * KEY:SINOSOFT08EDCB64FEA84DDFDCD64F97F83B43F1
 * ============================================================================
 */ 

public class BaseShopAction extends ActionSupport {

	private static final long serialVersionUID = 6718838811223344556L;

	public static final Logger logger = LoggerFactory.getLogger(BaseShopAction.class);

	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String CONTENT = "content";
	public static final String RESULTCODE = "resultCode";
	public static final String DATA = "data";

	protected String id;
	protected String[] ids;
	protected Pager pager;
	protected Pager pagerhome;
	protected String logInfo;// 日志记录信息
	protected String redirectionUrl;// 操作提示后的跳转URL,为null则返回前一页
	protected String staticPath;
	protected String shopStaticPath;
	protected String siteUrl;
	protected String CpsAgentURL;
	protected String CpsUnionURL;

	/**
	 * 前台返回当前页码
	 */
	protected String page_Index;

	/**
	 * 条数
	 */
	private String totalCounts;
	/**
	 * 总页数
	 */
	protected String page_count;

	/**
	 * 分页数据集合
	 */
	private List<Map<String, String>> pageFootList;
	/* 请求名称 */
	private String actionAlias;

	@Resource
	protected MemberService memberService;
	@Resource
	private BindInfoForLoginService bindInfoForLoginService;
	@Resource
	protected NavigationService navigationService;
	@Resource
	protected FriendLinkService friendLinkService;
	@Resource
	protected FooterService footerService;

	// ==============首页产品中心============================
	public Pager getPagerhome() {

		return pagerhome;
	}

	public void setPagerhome(Pager pagerhome) {

		this.pagerhome = pagerhome;
	}

	// ==============首页产品中心============================

	public String input() {

		return null;
	}

	// 获取系统配置信息
	public SystemConfig getSystemConfig() {

		return SystemConfigUtil.getSystemConfig();
	}

	// 获取商品价格货币格式
	public String getPriceCurrencyFormat() {

		return SystemConfigUtil.getPriceCurrencyFormat();
	}

	// 获取商品价格货币格式（包含货币单位）
	public String getPriceUnitCurrencyFormat() {

		return SystemConfigUtil.getPriceUnitCurrencyFormat();
	}

	// 获取订单货币格式
	public String getOrderCurrencyFormat() {

		return SystemConfigUtil.getOrderCurrencyFormat();
	}

	// 获取订单货币格式（包含货币单位）
	public String getOrderUnitCurrencyFormat() {

		return SystemConfigUtil.getOrderUnitCurrencyFormat();
	}

	// 获取当前登录会员，若未登录则返回null
	public Member getLoginMember() {
		
		
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		
		
		if (StringUtils.isEmpty(id)) {
			
			
			id = MemberCookieUtil.searchMember(getRequest(), getResponse());
			
			
			if (StringUtils.isEmpty(id)) {
				return null;

			}
		}
		

		MemberCookieUtil.checkMember(getRequest(), id);

		Member loginMember = null;
		if (!"tencent".equals(id)) {
			loginMember = memberService.get(id);

		} else {
			loginMember = bindInfoForLoginService.get(String.valueOf(getSession("loginBindId"))).getmMember();
		}
		return loginMember;
	}

	// 获取Attribute
	public Object getAttribute(String name) {

		return ServletActionContext.getRequest().getAttribute(name);
	}

	// 设置Attribute
	public void setAttribute(String name, Object value) {

		ServletActionContext.getRequest().setAttribute(name, value);
	}

	// 获取Parameter
	public String getParameter(String name) {

		return getRequest().getParameter(name);
	}

	// 获取Parameter数组
	public String[] getParameterValues(String name) {

		return getRequest().getParameterValues(name);
	}

	public Object removeSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.remove(name);
	}
	// 获取Session
	public Object getSession(String name) {

		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(name);
	}

	// 获取Session
	public Map<String, Object> getSession() {

		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session;
	}

	// 设置Session
	public void setSession(String name, Object value) {

		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(name, value);
	}

	// 获取Request
	public HttpServletRequest getRequest() {

		return ServletActionContext.getRequest();
	}

	// 获取Response
	public HttpServletResponse getResponse() {

		return ServletActionContext.getResponse();
	}

	// 获取Application
	public ServletContext getApplication() {

		return ServletActionContext.getServletContext();
	}

	// AJAX输出，返回null
	public String ajax(String content, String type) {

		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	// AJAX输出，返回null
		public String ajaxJsonp(String content, String type) {

			try {
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType(type + ";charset=UTF-8");
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setDateHeader("Expires", 0);
				response.getWriter().write(content);
				response.getWriter().flush();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			return null;
		}

	// AJAX输出文本，返回null
	public String ajaxText(String text) {

		return ajax(text, "text/plain");
	}

	// AJAX输出HTML，返回null
	public String ajaxHtml(String html) {

		return ajax(html, "text/html");
	}

	// AJAX输出XML，返回null
	public String ajaxXml(String xml) {

		return ajax(xml, "text/xml");
	}

	// 根据字符串输出JSON，返回null
	public String ajaxJson(String jsonString) {

		return ajax(jsonString, "text/html");
	}

	// 根据Map输出JSON，返回null
	public String ajaxMap2Json(Map<String, Object> jsonMap) {

		return ajax(JSONObject.fromObject(jsonMap).toString(), "text/html");
	}

	// 根据Map输出JSON，返回null
	public String ajaxJson(Map<String, String> jsonMap) {

		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	// 将Map转换为JSON字符串
	public String formatJson(Map<String, String> jsonMap) {

		return JSONObject.fromObject(jsonMap).toString();
	}

	// 输出JSON警告消息，返回null
	public String ajaxJsonWarnMessage(String message) {

		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	// 输出JSON成功消息，返回null
	public String ajaxJsonSuccessMessage(String message) {

		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	public String ajaxJsonSuccessMessage(Map<String,Object> data) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(DATA, data);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	// 输出JSON错误消息，返回null
	public String ajaxJsonErrorMessage(String message) {

		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	public String ajaxJsonErrorMessage(String resultCode, String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		jsonMap.put(RESULTCODE, resultCode);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	// 设置页面不缓存
	public void setResponseNoCache() {

		getResponse().setHeader("progma", "no-cache");
		getResponse().setHeader("Cache-Control", "no-cache");
		getResponse().setHeader("Cache-Control", "no-store");
		getResponse().setDateHeader("Expires", 0);
	}

	/**
	 * 
	 * @Title: getPageDataList
	 * @Description: TODO(分页数据)
	 * @return List 返回类型
	 * @author
	 */
	public void getPageDataList(Map<String, String> param_map) {

		// 当前分页访问的URL请求
		String URL = getRequest().getRequestURL().toString();
		if (URL.indexOf(".action") == -1) {
			logger.error("分页访问的请求有异常，异常URL为：{}", URL);
		}
		// actionAlias赋值
		if (StringUtil.isEmpty(actionAlias)) {
			actionAlias = URL.substring(URL.lastIndexOf("/") + 1, URL.indexOf(".action") + 7);
		}
		
		pageFootList = new ArrayList<Map<String, String>>();
		if (StringUtil.isEmpty(page_Index)) {
			page_Index = "0";
		}
		// 判断显示分页数字
		int pageindex = Integer.parseInt(page_Index);
		if (pageindex > 0) {
			pageindex = pageindex - 1;
		}
		
		// 总条数
		totalCounts = param_map.get("totalCounts");
		String idalias = param_map.get("idalias");
		if (URL.indexOf("points!integralMallModelInfo.action") >= 0) {
			if (Integer.parseInt(totalCounts) > 5) {
				page_count = String.valueOf(Integer.parseInt(totalCounts) < 6 ? 1
						: ((Integer.parseInt(totalCounts) - 5) % 6 == 0 ? (Integer.parseInt(totalCounts) - 5) / 6 : (Integer
								.parseInt(totalCounts) - 5) / 6 + 1) + 1);
			} else {
				// 获取总页数
				page_count = String.valueOf(Integer.parseInt(totalCounts) < 5 ? 1
						: (Integer.parseInt(totalCounts) % 5 == 0 ? Integer.parseInt(totalCounts) / 5 : Integer
								.parseInt(totalCounts) / 5 + 1));
			}
			List<Map<String, String>> pageList = PageDataList(Integer.parseInt(page_count), pageindex);
			if (pageList != null && pageList.size() > 0) {
				for (int i = pageList.size()-1; i >= 0; i--) {
					if (StringUtil.isNotEmpty(idalias)) {
						pageList.get(i).put("idalias", idalias);
					}
					pageFootList.add(pageList.get(i));
						
				}
			}
		} else {
			
			List<Map<String, String>> pageList = PageDataList(Integer.parseInt(page_count), pageindex);
			if (pageList != null && pageList.size() > 0) {
				for (int i = 0; i < pageList.size(); i++) {
					if (StringUtil.isNotEmpty(idalias)) {
						pageList.get(i).put("idalias", idalias);
					}
					pageFootList.add(pageList.get(i));
						
				}
			}
		}
	}
	
	public List<Map<String, String>> PageDataList(int pageCount, int pageIndex) {
		List<Map<String, String>> pageList = new ArrayList<Map<String, String>>();
		// 分页
		Map<String, String> map;
		
		if (pageIndex > 0) {
			map = new HashMap<String, String>();
			map.put("class", "");
			map.put("index", "1");
			pageList.add(map);
		} else {
			map = new HashMap<String, String>();
			map.put("class", "now");
			map.put("index", "1");
			pageList.add(map);
		}
		
		if (pageCount > 0) {
			int j = 2;
			for (j = 2;j< pageCount; j++) {
				if(pageCount>6){
					if (pageIndex >= pageCount - 4) {
						if (j >= pageCount - 3) {
							if (j == pageCount - 3) {
								map = new HashMap<String, String>();
								map.put("class", "omit");
								map.put("index", "...");
								pageList.add(map);
							}
							map = new HashMap<String, String>();
							if (j==(pageIndex+1)) {
								map.put("class", "now");
							} else {
								map.put("class", "");
							}
							map.put("index", ""+j);
							pageList.add(map);
							
						}
					} else if(pageIndex<3){
							if(j<5){
								map = new HashMap<String, String>();
								if (j==(pageIndex+1)) {
									map.put("class", "now");
								} else {
									map.put("class", "");
								}
								map.put("index", ""+j);
								pageList.add(map);
								if(j==4){
									map = new HashMap<String, String>();
									map.put("class", "omit");
									map.put("index", "...");
									pageList.add(map);
								}
							}
					} else {
						if(pageIndex>2 && pageCount>(pageIndex+1)){
							if(j>(pageIndex-1)&&j<(pageIndex+3)){
								map = new HashMap<String, String>();
								if (j==(pageIndex+1)) {
									map.put("class", "now");
								} else {
									map.put("class", "");
								}
								map.put("index", ""+j);
								pageList.add(map);
							}
							if(j==(pageIndex+2)&&j<pageCount-2){
								map = new HashMap<String, String>();
								map.put("class", "omit");
								map.put("index", "...");
								pageList.add(map);
							}
						}
					}
				}else if(pageCount<7){
					map = new HashMap<String, String>();
					if (j==(pageIndex+1)) {
						map.put("class", "now");
					} else {
						map.put("class", "");
					}
					map.put("index", ""+j);
					pageList.add(map);
				}
			}
			map = new HashMap<String, String>();
			if (pageIndex + 1 == pageCount) {
				if (pageCount > 1) {
					map.put("class", "now");
					map.put("index", ""+pageCount);
					pageList.add(map);
				}
			} else {
				map.put("class", "");
				map.put("index", ""+pageCount);
				pageList.add(map);
			}
		}
		
		return pageList;
	}
	
	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public String[] getIds() {

		return ids;
	}

	public void setIds(String[] ids) {

		this.ids = ids;
	}

	public Pager getPager() {

		return pager;
	}

	public void setPager(Pager pager) {

		this.pager = pager;
	}

	public String getLogInfo() {

		return logInfo;
	}

	public void setLogInfo(String logInfo) {

		this.logInfo = logInfo;
	}

	public String getRedirectionUrl() {

		return redirectionUrl;
	}

	public void setRedirectionUrl(String redirectionUrl) {

		this.redirectionUrl = redirectionUrl;
	}

	public List<Navigation> getTopNavigationList() {

		return navigationService.getTopNavigationList();
	}

	public List<Navigation> getMiddleNavigationList() {

		return navigationService.getMiddleNavigationList();
	}

	public List<Navigation> getBottomNavigationList() {

		return navigationService.getBottomNavigationList();
	}

	public List<FriendLink> getFriendLinkList() {

		return friendLinkService.getAll();
	}

	public List<FriendLink> getPictureFriendLinkList() {

		return friendLinkService.getPictureFriendLinkList();
	}

	public List<FriendLink> getTextFriendLinkList() {

		return friendLinkService.getTextFriendLinkList();
	}

	public Footer getFooter() {

		return footerService.getFooter();
	}

	public String getStaticPath() {

		staticPath = Config.getValue("StaticResourcePath");
		return staticPath;
	}

	public void setStaticPath(String staticPath) {

		this.staticPath = staticPath;
	}

	public String getShopStaticPath() {

		shopStaticPath = Config.getValue("JsResourcePath") + "/js";
		return shopStaticPath;
	}

	public void setShopStaticPath(String shopStaticPath) {

		this.shopStaticPath = shopStaticPath;
	}

	public String getSiteUrl() {

		siteUrl = Config.getValue("FrontServerContextPath");
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {

		this.siteUrl = siteUrl;
	}

	public String getCpsAgentURL() {

		CpsAgentURL = Config.getValue("CpsAgent.URL");
		return CpsAgentURL;
	}

	public void setCpsAgentURL(String cpsAgentURL) {

		CpsAgentURL = cpsAgentURL;
	}

	public String getCpsUnionURL() {

		CpsUnionURL = Config.getValue("CpsUnion.URL");
		return CpsUnionURL;
	}

	public void setCpsUnionURL(String cpsUnionURL) {

		CpsUnionURL = cpsUnionURL;
	}

	public String getPage_Index() {

		return page_Index;
	}

	public void setPage_Index(String page_Index) {

		this.page_Index = page_Index;
	}

	public String getTotalCounts() {

		return totalCounts;
	}

	public void setTotalCounts(String totalCounts) {

		this.totalCounts = totalCounts;
	}

	public List<Map<String, String>> getPageFootList() {

		return pageFootList;
	}

	public void setPageFootList(List<Map<String, String>> pageFootList) {

		this.pageFootList = pageFootList;
	}

	public String getActionAlias() {

		return actionAlias;
	}

	public void setActionAlias(String actionAlias) {

		this.actionAlias = actionAlias;
	}

	public String getPage_count() {

		return page_count;
	}

	public void setPage_count(String page_count) {

		this.page_count = page_count;
	}

}