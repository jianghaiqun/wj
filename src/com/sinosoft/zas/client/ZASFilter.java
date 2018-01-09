package com.sinosoft.zas.client;

import com.sinosoft.zas.ClientConfig;
import com.sinosoft.zas.UserData;
import com.sinosoft.zas.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Provider;
import java.security.Security;

public class ZASFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(ZASFilter.class);
	public void init(FilterConfig config) throws ServletException {
		try {
			Class<?> c = Class.forName("org.bouncycastle.jce.provider.BouncyCastleProvider");
			Object o = c.newInstance();
			Security.addProvider((Provider) o);
		} catch (Exception e) {
			logger.error("未能加载BouncyCastleProvider，请使用JDK自带的Provider。");
		}
		PGTUtil.initCert();
	}

	public void doFilter(ServletRequest sr, ServletResponse sp, FilterChain fc) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) sr;
		HttpServletResponse response = (HttpServletResponse) sp;
		HttpSession session = request.getSession();
		if ((session != null) && (session.getAttribute("com.zving.zas.User") != null)) {
			fc.doFilter(request, response);
			return;
		}
		String referer = getReferer(request);
		referer = URLDecoder.decode(referer, "UTF-8");
		if ((ClientConfig.isProxyEnable()) && (ClientConfig.getMode() == 3) && (referer.startsWith(ClientConfig.getProxyCallbackURL()))) {
			fc.doFilter(request, response);
			return;
		}

		if (ClientConfig.getMode() == 4) {
			String data = request.getParameter("UserData");
			String proxyticket = request.getParameter("PT");
			if ((data == null) || (data.equals(""))) {
				if ((proxyticket != null) && (!proxyticket.equals(""))) {
					UserData user = getProxyedAuthenticatedUser(request);
					if (user == null) {
						throw new ServletException("ProxyTicket未通过ZAS验证,请检查ServiceURL是否正确!");
					}
					if (session != null) {
						session.setAttribute("com.zving.zas.User", user);
					}
					fc.doFilter(request, response);
				} else {
					if (ClientConfig.getServerURL() == null) {
						throw new ServletException("未设置ZASServerURL!");
					}
					String renew = ClientConfig.isNeedNewLogin() ? "&NeedNewLogin=" + ClientConfig.isNeedNewLogin() : "";
					referer = URLEncoder.encode(referer, "UTF-8");
					if (referer.indexOf("161.207.1.10") != -1)
						response.sendRedirect("http://161.207.1.10/zas/Login.jsp?ServiceID=" + ClientConfig.getServiceID() + "&Referer=" + referer + renew);
					else {
						response.sendRedirect(ClientConfig.getServerURL() + "Login.jsp" + "?ServiceID=" + ClientConfig.getServiceID() + "&Referer=" + referer + renew);
					}

					return;
				}
			} else {
				UserData user = getAuthenticatedUserInTrustMode(request);
				if (user == null) {
					throw new ServletException("UserData未正确解密,请检查ServiceURL是否正确!");
				}
				if (session != null) {
					session.setAttribute("com.zving.zas.User", user);
				}
				fc.doFilter(request, response);
			}
		} else {
			String ticket = request.getParameter("ST");
			String proxyticket = request.getParameter("PT");
			if ((ticket == null) || (ticket.equals(""))) {
				if ((proxyticket != null) && (!proxyticket.equals(""))) {
					UserData user = getProxyedAuthenticatedUser(request);
					if (user == null) {
						throw new ServletException("ProxyTicket未通过ZAS验证,请检查ServiceURL是否正确!");
					}
					if (session != null) {
						session.setAttribute("com.zving.zas.User", user);
						if (ClientConfig.isProxyEnable()) {
							session.setAttribute("_PGT", PGTUtil.getPGT(user.getUserName()));
						}
					}
					fc.doFilter(request, response);
				} else {
					if (ClientConfig.getServerURL() == null) {
						throw new ServletException("未设置ZASServerURL!");
					}
					String renew = ClientConfig.isNeedNewLogin() ? "&NeedNewLogin=" + ClientConfig.isNeedNewLogin() : "";
					response.sendRedirect(ClientConfig.getServerURL() + "Login.jsp" + "?ServiceID=" + ClientConfig.getServiceID() + "&Referer=" + referer + renew);
					return;
				}
			} else {
				UserData user = getAuthenticatedUserInCommonMode(ticket);
				if (user == null) {
					throw new ServletException("ServiceTicket未通过ZAS验证,请检查ServiceURL是否正确!");
				}
				if (session != null) {
					session.setAttribute("com.zving.zas.User", user);
					if (ClientConfig.isProxyEnable()) {
						session.setAttribute("_PGT", PGTUtil.getPGT(user.getUserName()));
						PGTUtil.removePGT(user.getUserName());
					}
				}
				fc.doFilter(request, response);
			}
		}
	}

	public void destroy() {
	}

	private UserData getAuthenticatedUserInCommonMode(String ticket) throws ServletException, IOException {
		ServiceTicketValidator stv = new ServiceTicketValidator();
		stv.setTicket(ticket);
		stv.validate();
		return stv.getUser();
	}

	private UserData getAuthenticatedUserInTrustMode(HttpServletRequest request) throws ServletException, IOException {
		ServiceTicketValidator stv = new ServiceTicketValidator();
		String data = request.getParameter("UserData");
		data = new String(Util.base64Decode(data), "UTF-8");
		stv.response = data;
		stv.parseResponse();
		if (ClientConfig.isProxyEnable()) {
			request.getSession().setAttribute("_PGT", stv.getProxyGrantingTicket());
			PGTUtil.add(stv.getUser().getUserName(), stv.getProxyGrantingTicket());
		}
		return stv.getUser();
	}

	private UserData getProxyedAuthenticatedUser(HttpServletRequest request) throws ServletException, IOException {
		ProxyTicketValidator ptv = new ProxyTicketValidator();
		ptv.setTicket(request.getParameter("PT"));
		ptv.setProxyServiceID(request.getParameter("ServiceID"));
		String username = request.getParameter("UserName");
		if ((username != null) && (ClientConfig.isProxyEnable()) && (!PGTUtil.existPGT(username))) {
			ptv.setNeedPGT(true);
		}

		ptv.validate();
		if ((ClientConfig.isProxyEnable()) && (ptv.getProxyGrantingTicket() != null)) {
			request.getSession().setAttribute("_PGT", ptv.getProxyGrantingTicket());
			PGTUtil.add(ptv.getUser().getUserName(), ptv.getProxyGrantingTicket());
		}
		return ptv.getUser();
	}

	public static String getReferer(HttpServletRequest request) throws ServletException {
		StringBuffer sb = new StringBuffer();
		sb.append(request.getScheme());
		sb.append("://");
		sb.append(request.getServerName());
		if ((request.getServerPort() != 80) && (request.getScheme().equals("http"))) {
			sb.append(":");
			sb.append(request.getServerPort());
		}
		if ((request.getServerPort() != 443) && (request.getScheme().equals("https"))) {
			sb.append(":");
			sb.append(request.getServerPort());
		}
		sb.append(request.getRequestURI());
		if (request.getQueryString() != null) {
			sb.append("?");
			sb.append(request.getQueryString());
		}
		try {
			return URLEncoder.encode(sb.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		throw new ServletException("未能正确得到ServiceURL!");
	}
}
