package com.sinosoft.zas.client;

import com.sinosoft.zas.ClientConfig;
import com.sinosoft.zas.Util;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;

public class ProxyTicketValidator extends ServiceTicketValidator {
	private String proxyServiceID;
	private boolean needPGT;

	public String getProxyServiceID() {
		return this.proxyServiceID;
	}

	public void setProxyServiceID(String proxyServiceID) {
		this.proxyServiceID = proxyServiceID;
	}

	public void validate() throws ServletException, IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(ClientConfig.getServerURL() + "ProxyValidate.jsp");
		sb.append("?");
		sb.append("ProxyID=" + this.proxyServiceID + "&ServiceID=" + ClientConfig.getServiceID() + "&PT=" + URLEncoder.encode(this.ticket, "UTF-8"));
		if (ClientConfig.isNeedNewLogin()) {
			sb.append("&NeedNewLogin=true");
		}
		if (this.needPGT) {
			sb.append("&NeedPGT=true");
		}
		this.response = Util.getURLContent(sb.toString());
		parseResponse();
	}

	public boolean isNeedPGT() {
		return this.needPGT;
	}

	public void setNeedPGT(boolean needPGT) {
		this.needPGT = needPGT;
	}
}
