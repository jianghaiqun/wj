package com.sinosoft.zas.client;

import com.sinosoft.zas.ClientConfig;
import com.sinosoft.zas.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

public class PasswordApplicant {
	public static String[] getUserNameAndPassword(HttpSession session, String targetServiceID) throws ServletException, IOException {
		String pgt = (String) session.getAttribute("_PGT");
		if (pgt == null) {
			throw new ServletException("用户己注销");
		}
		StringBuffer sb = new StringBuffer();
		sb.append(ClientConfig.getServerURL() + "PasswordApply.jsp");
		sb.append("?");
		sb.append("TargetID=" + targetServiceID + "&ServiceID=" + ClientConfig.getServiceID());
		if (ClientConfig.getMode() == 4) {
			StringBuffer sb2 = new StringBuffer();
			sb2.append(Util.getTransactionId());
			sb2.append(",");
			sb2.append(System.currentTimeMillis());
			sb2.append(",");
			sb2.append(pgt);
			String str = URLEncoder.encode(ServiceTicketValidator.encrypt(sb2.toString()), "UTF-8");
			sb.append("&PGT=" + str);
		} else {
			sb.append("&PGT=" + pgt);
		}
		String response = Util.getURLContent(sb.toString());

		HashMap<String,String> map = Util.parseXML(response);
		if ("OK".equals(map.get("Status"))) {
			return new String[] { (String) map.get("UserName"), (String) map.get("Password") };
		}
		throw new ServletException("获取密码时发生错误");
	}
}
