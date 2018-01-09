package com.sinosoft.framework.clustering.client;

import com.sinosoft.framework.clustering.Clustering.Server;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClusteringClient extends ClusteringClient {
	private static final Logger logger = LoggerFactory.getLogger(HttpClusteringClient.class);

	private HttpClient httpClient;
	private Server server;

	public HttpClusteringClient(Server server) {
		this.server = server;
		if (!server.URL.startsWith("http://")) {
			throw new RuntimeException("错误的集群服务器URL：" + server.URL);
		}
	}

	private void init() {
		if (httpClient == null) {
			SimpleHttpConnectionManager cm = new SimpleHttpConnectionManager();
			HttpConnectionManagerParams hcmp = new HttpConnectionManagerParams();
			hcmp.setDefaultMaxConnectionsPerHost(1);
			hcmp.setConnectionTimeout(3000);// 三秒之内必须返回
			hcmp.setSoTimeout(3000);
			cm.setParams(hcmp);
			httpClient = new HttpClient(cm);
		}
	}

	public String executeMethod(String type, String key, String action, String value) {
		init();
		for (int i = 0; i < server.RetryCount; i++) {
			try {
				PostMethod pm = new PostMethod(server.URL);
				pm.addParameter("Type", type);
				pm.addParameter("Action", action);
				pm.addParameter("Key", key);
				if (value != null) {
					pm.addParameter("Value", value);
				}
				httpClient.executeMethod(pm);
				if (pm.getStatusCode() != 200) {
					logger.error("HttpClusteringClient.get()状态代码异常:{};URL={}", pm.getStatusCode(), server.URL);
					continue;
				}
				String result = pm.getResponseBodyAsString();
				return result.trim();
			} catch (Exception e) {
				logger.error("HttpClusteringClient.get()发生异常:" + e.getMessage() + ";URL=" + server.URL, e);
			}
		}
		synchronized (server) {
			server.isAlive = false;
		}
		throw new RuntimeException("HttpClusteringClient.get()发生错误，重试" + server.RetryCount + "次皆失败;URL=" + server.URL);
	}
}
