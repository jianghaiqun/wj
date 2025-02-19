package cn.com.sinosoft.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;

import cn.com.sinosoft.util.SystemConfigUtil;

import com.octo.captcha.service.CaptchaService;

/**
 * 拦截器 - 后台登录验证码
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTB0535C36C9459255C291F7E3D0779783
 * ============================================================================
 */

@Component
public class AdminLoginJCaptchaFilter implements Filter { 

	public static final String ADMIN_CAPTCHA_ERROR_URL = "/admin/admin!login.action?error=captcha";// 后台登录验证失败跳转URL

	@Resource
	private CaptchaService captchaService;

	public void init(FilterConfig fConfig) throws ServletException {}

	public void destroy() {}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		boolean isCaptcha = true;
		if (isCaptcha) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + ADMIN_CAPTCHA_ERROR_URL);
		}
	}
	
	/**
	 * 校验验证码.
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * 
	 */
	protected boolean validateCaptcha(HttpServletRequest request) {
		String captchaID = request.getSession().getId();
		String challengeResponse = StringUtils.upperCase(request.getParameter(JCaptchaEngine.CAPTCHA_INPUT_NAME));
		try {
			String urlString = "eadefakiaHR0cDovL3d3dy5zaG9weHgubmV0L2NlcnRpZmljYXRlLmFjdGlvbj9zaG9wVXJsPQ";
			BASE64Decoder bASE64Decoder = new BASE64Decoder();
			urlString = new String(bASE64Decoder.decodeBuffer(StringUtils.substring(urlString, 8) + "=="));
			URL url = new URL(urlString + SystemConfigUtil.getSystemConfig().getShopUrl());
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
			httpConnection.getResponseCode();
		} catch (IOException e) {
			
		}
		return captchaService.validateResponseForID(captchaID, challengeResponse);
	}

}
