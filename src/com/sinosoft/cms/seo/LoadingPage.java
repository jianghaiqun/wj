package com.sinosoft.cms.seo;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 加载页面.
 * 
 * @author congzn.
 * @date 2013-10-29
 */
public class LoadingPage {
	private static final Logger logger = LoggerFactory.getLogger(LoadingPage.class);

	/**
	 * 通过URL 获取页面内容.
	 * 
	 * @param url
	 * @return
	 */
	public static String getContentFormUrl(String url) {

		/* 实例化一个HttpClient客户端 */
		HttpClient client = new DefaultHttpClient();
		String content = null;

		try {
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 360000);
			URI newUrl = new URI(url);
			CharsetHandler handler = new CharsetHandler("gb2312");
			HttpGet httpget = new HttpGet(newUrl);
			httpget
					.setHeader(
							"User-Agent",
							"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.63 Safari/535.7 360EE");
			content = client.execute(httpget, handler);

		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (URISyntaxException e) {
			logger.error(e.getMessage(), e);
		} finally {
			client.getConnectionManager().shutdown();
		}

		return content;
	}

}
