package com.sinosoft.zas.client;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sinosoft.zas.ClientConfig;
import com.sinosoft.zas.Util;

public class PGTUtil extends ServiceTicketValidator {
	private static HashMap<String, String> ticketMap = new HashMap<String, String>();

	public static void add(String username, String pgt) {
		synchronized (PGTUtil.class) {
			ticketMap.put(username, pgt);
		}
	}

	public static boolean existPGT(String username) {
		return ticketMap.containsKey(username);
	}

	public static String getPGT(String username) {
		return (String) ticketMap.get(username);
	}

	public static void removePGT(String username) {
		ticketMap.remove(username);
	}

	public static String getProxyTicket(String userName, String targetServiceID) throws ServletException, IOException {
		String pgt = getPGT(userName);
		if (pgt == null) {
			throw new ServletException("用户己注销");
		}
		return getProxyTicketInner(pgt, targetServiceID);
	}

	public static String getProxyTicket(HttpSession session, String targetServiceID) throws ServletException, IOException {
		String pgt = (String) session.getAttribute("_PGT");
		if (pgt == null) {
			throw new ServletException("用户己注销");
		}
		return getProxyTicketInner(pgt, targetServiceID);
	}

	private static String getProxyTicketInner(String pgt, String targetServiceID) throws ServletException, IOException {
		if (ClientConfig.getMode() == 4) {
			StringBuffer sb = new StringBuffer();
			sb.append(Util.getTransactionId());
			sb.append(",");
			sb.append(System.currentTimeMillis());
			sb.append(",");
			sb.append(pgt);
			String str = ServiceTicketValidator.encrypt(sb.toString());
			return URLEncoder.encode(str, "UTF-8");
		}
		StringBuffer sb = new StringBuffer();
		sb.append(ClientConfig.getServerURL() + "ProxyTicketApply.jsp");
		sb.append("?");
		sb.append("TargetID=" + targetServiceID + "&ServiceID=" + ClientConfig.getServiceID() + "&PGT=" + pgt);
		String response = Util.getURLContent(sb.toString());

		HashMap<String, String> map = Util.parseXML(response);
		if (map.get("Status").equals("OK")) {
			return (String) map.get("PT");
		}
		throw new ServletException("获取PT时发生错误");
	}

	public static void dealCallback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pgt = request.getParameter("PGT");
		String username = request.getParameter("UserName");
		request.getSession().setAttribute("_PGT", pgt);
		add(username, pgt);
	}
}
