package cn.com.sinosoft.common;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.StringUtil;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 拦截器 - 去除页面参数字符串两端的空格
 * ============================================================================
 * 
 * 
 * 
 * 
 * 
 * 
 * KEY:SINOSOFTF08210E5AFEDF7D9575CD16EF7D4704D
 * ============================================================================
 */

public class TrimInterceptor extends AbstractInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(TrimInterceptor.class);
	private static final long serialVersionUID = 2365641900033439481L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		Map<String, Object> parameters = invocation.getInvocationContext()
				.getParameters();

		HttpServletRequest request = ServletActionContext.getRequest();

		try {

			// 防止跨站攻击,部分action特殊处理
			if (request.getRequestURI().indexOf("member_center!index.action") != -1
					|| request.getRequestURI().indexOf("member_shopcart!getShopCartINF.action") != -1) {
				if (request.getQueryString() != null && request.getQueryString().contains(";")) {
					if (isAjaxRequest(request)) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("status", "error");
						map.put("msgtype", "5");
						map.put("message", "你输入的内容有误！");
						return ajaxJson(map);
					} else {
						return "error";
					}
				}
			}

			if (request != null && request.getRequestURL().indexOf("send_mail!deal.action") == -1
					&& request.getRequestURL().indexOf("present!save.action") == -1
					&& request.getRequestURL().indexOf("present!update.action") == -1) {

				for (String key : parameters.keySet()) {
					Object value = parameters.get(key);
					if (value instanceof String[]) {
						String[] valueArray = (String[]) value;
						for (int i = 0; i < valueArray.length; i++) {
							valueArray[i] = StringUtil.trim(valueArray[i]);

							// 防止跨站攻击，登陆页面特殊处理
							if ((request.getRequestURI().indexOf("member_center!index.action") != -1
									|| request.getRequestURI().indexOf("member!newLogin.action") != -1)
									&& "tagid".equals(key)) {
								if (!"logintag".equals(valueArray[i]) && !"regtag".equals(valueArray[i])) {
									if (isAjaxRequest(request)) {
										HashMap<String, String> map = new HashMap<String, String>();
										map.put("status", "error");
										map.put("msgtype", "5");
										map.put("message", "你输入的内容有误！");
										return ajaxJson(map);
									} else {
										return "error";
									}
								}
							}

							if (request.getRequestURI().indexOf("/shop/") != -1
									|| request.getRequestURI().indexOf("member_register!home.action") != -1) {
								 
								// 购买流程不校验的字段
								String no_validate_field = Config.getValue("_no_validate_field");
								if (no_validate_field.toLowerCase().indexOf("," + key.toLowerCase() + ",") != -1) {
									continue;
								}
								if (!isIllegalCurrent(valueArray[i])) {
									logger.error("error key:{},错误值：{}", key,  valueArray[i]);
									
									if (isAjaxRequest(request)) {
										HashMap<String, String> map = new HashMap<String, String>();
										map.put("status", "error");
										map.put("msgtype", "5");
										map.put("message", "你输入的内容有误！");
										return ajaxJson(map);
									} else {
										return "error";
									}
								}
							} else {
								if (!isIllegal(valueArray[i])) {
									if (isAjaxRequest(request)) {
										HashMap<String, String> map = new HashMap<String, String>();
										map.put("status", "error");
										map.put("msgtype", "5");
										map.put("message", "你输入的内容有误！");
										return ajaxJson(map);
									} else {
										return "error";
									}
								}
							}
						}
						parameters.put(key, valueArray);
					}
				}
			}
		} catch (Exception e) {
			logger.error("struts2拦截器异常!" + e.getMessage(), e);
		}

		return invocation.invoke();
	}
	
	
	private static boolean isIllegal(String temp) {

		String regex = "script|prompt|iframe|<";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(temp.toLowerCase());
		if (m.find()) {
			return false;
		}
		return true;

	}

	private static boolean isIllegalCurrent(String temp) {

		String regex = "script|prompt|iframe|\'|\"";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(temp.toLowerCase());
		if (m.find()) {
			return false;
		}
		return true;

	}

	private boolean isAjaxRequest(HttpServletRequest request) {

		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header)) {
			return true;
		} else {
			return false;
		}
	}

	// 根据Map输出JSON，返回null
	public String ajaxJson(Map<String, String> jsonMap) {

		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
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

}
