package com.sinosoft.framework;

import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.utility.IOUtil;
import com.sinosoft.framework.utility.Mapx;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import java.io.InputStream;
import java.lang.reflect.Method;

public class Framework extends Page {
	/**
	 * 处理select标签中的code属性
	 */
	public void getCodeData() {
		String CodeType = Request.getString("_ZVING_CODETYPE");
		String className = Config.getValue("App.CodeSource");
		String methodName = className.substring(className.lastIndexOf(".") + 1);
		className = className.substring(0, className.lastIndexOf("."));
		try {
			Class c = Class.forName(className);
			Method m = c.getMethod(methodName, new Class[] { String.class, DataCollection.class });
			Object d = m.invoke(null, new Object[] { CodeType, Request });
			if (d != null) {
				Response.put("CodeData", (DataTable) d);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 调用远程服务器上的方法
	 */
	public static Mapx callRemoteMethod(String url, String method, RequestImpl request) {
		SimpleHttpConnectionManager cm = new SimpleHttpConnectionManager();
		HttpConnectionManagerParams hcmp = new HttpConnectionManagerParams();
		hcmp.setDefaultMaxConnectionsPerHost(1);
		hcmp.setConnectionTimeout(3000);// 三秒之内必须返回
		hcmp.setSoTimeout(3000);
		cm.setParams(hcmp);
		HttpClient httpClient = new HttpClient(cm);
		if (!url.startsWith("/")) {
			url = url + "/";
		}
		for (int i = 0; i < 3; i++) {// 重试三次
			try {
				PostMethod pm = new PostMethod(url + "MainServlet.jsp");
				pm.addParameter(Constant.Method, method);
				pm.addParameter(Constant.Data, request.toXML());
				pm.addParameter(Constant.URL, url);
				httpClient.executeMethod(pm);
				if (pm.getStatusCode() != 200) {
					logger.error("Framework.callRemoteMethod()状态代码异常:{};URL={}", pm.getStatusCode(), url);
					continue;
				}
				InputStream is = pm.getResponseBodyAsStream();
				byte[] body = IOUtil.getBytesFromStream(is);
				String result = new String(body, "UTF-8");
				DataCollection dc = new DataCollection();
				dc.parseXML(result);
				return dc;
			} catch (Exception e) {
				logger.error("Framework.callRemoteMethod()发生异常:" + e.getMessage() + ";URL=" + url, e);
			}
		}
		return null;
	}
}
