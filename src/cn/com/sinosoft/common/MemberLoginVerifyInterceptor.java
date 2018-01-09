package cn.com.sinosoft.common;

import cn.com.sinosoft.entity.Member;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器 - 判断会员是否登录
 * ============================================================================
 * 
 * 
 * 
 * 
 * 
 * 
 * KEY:SINOSOFTCF908840E8D9DB3A559EB83A2FE5F57A
 * ============================================================================
 */

public class MemberLoginVerifyInterceptor extends AbstractInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(MemberLoginVerifyInterceptor.class);
	private static final long serialVersionUID = -86246303854807787L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String loginMemberId = (String) invocation.getInvocationContext().getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		logger.info("进入拦截器，会员id：{}", loginMemberId);
		if (StringUtil.isEmpty(loginMemberId)) {
			HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
			HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);

			Cookie cookie = new Cookie(Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, null);
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(0);
			response.addCookie(cookie);

			GetDBdata db = new GetDBdata();
			String FrontServerContextPath = db.getOneValue("select value from zdconfig where type='FrontServerContextPath'");
			String redirectionUrl = FrontServerContextPath + request.getRequestURI();

			String queryString = request.getQueryString();
			if (StringUtils.isNotEmpty(queryString)) {
				redirectionUrl += "?" + queryString;
			}
			request.getSession().setAttribute(Member.LOGIN_REDIRECTION_URL_SESSION_NAME, redirectionUrl);
			logger.info("进入拦截器，回调地址：{}", redirectionUrl);
			return "login";
		}
		return invocation.invoke();
	}

}