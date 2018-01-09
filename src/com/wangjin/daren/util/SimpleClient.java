package com.wangjin.daren.util;

import com.sinosoft.framework.utility.StringUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 最简单的HTTP客户端,用来演示通过GET或者POST方式访问某个页面
 */
public class SimpleClient {
	private static final Logger logger = LoggerFactory.getLogger(SimpleClient.class);
	public static String pageContent_Get(String url, String charset) {
		
		HttpClient client = new HttpClient();
		// 设置代理服务器地址和端口

		// client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
		// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
		HttpMethod method = new GetMethod(url);
		String result = "";
		try {
			// 使用POST方法
			// HttpMethod method = new PostMethod("http://java.sun.com");
			client.executeMethod(method);

			// 打印服务器返回的状态
			//System.out.println(method.getStatusLine());
			//result = method.getResponseBodyAsString();
			if (StringUtil.isNotEmpty(charset)) {
				result = new String(method.getResponseBody(), charset);
			} else {
				result = new String(method.getResponseBody());
			}
			
			// 释放连接
			method.releaseConnection();
		} catch (Exception e) {
			logger.error("达人帖子关键字检查异常！" + e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * doGet:. <br/>
	 *
	 * @author wwy
	 * @param url
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url, String charset) throws Exception {
		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", "gbk");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		Integer responseCode = httpURLConnection.getResponseCode();
		if (responseCode >= 300) {
			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}

		try {
			inputStream = httpURLConnection.getInputStream();
			if (StringUtil.isNotEmpty(charset)) {
				inputStreamReader = new InputStreamReader(inputStream, charset);
			} else {
				inputStreamReader = new InputStreamReader(inputStream);
			}
			
			reader = new BufferedReader(inputStreamReader);
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return resultBuffer.toString();
	}
	
//	public static void main(String[] args) throws IOException {
//		HttpClient client = new HttpClient();
//		// 设置代理服务器地址和端口
//
//		// client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
//		// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https
//		//HttpMethod method1 = new GetMethod("http://bc.qunar.com/clk?s=1270&a=zhankai_rest_imgs&t=0.00565875560131035");
//		// 使用POST方法
//		// HttpMethod method = new PostMethod("http://java.sun.com");
//		//client.executeMethod(method1);
//		HttpMethod method2 = new GetMethod("http://travel.qunar.com/youji/6432863");
//		// 使用POST方法
//		// HttpMethod method = new PostMethod("http://java.sun.com");
//		client.executeMethod(method2);
//
//		// 打印服务器返回的状态
//		System.out.println(method2.getStatusLine());
//		// 打印返回的信息
//		System.out.println(method2.getResponseBodyAsString());
//		// 释放连接
//		//method1.releaseConnection();
//		method2.releaseConnection();
//	}
	
	/**
	 * doGetBrowser:模拟浏览器. <br/>
	 *
	 * @author wwy
	 * @param url
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String doGetBrowser(String url, String charset, Map<String, String> param) throws Exception {
		// 请求信息
		HttpMethod method = new GetMethod(url);
		setHeaders(method);
		
		HttpClient httpclient = new HttpClient();

		// 设置访问编码
		// httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF8");
		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
		httpclient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		// 让服务器知道访问源为浏览器
		// httpclient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; rv:8.0.1) Gecko/20100101 Firefox/8.0.1");
		httpclient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
		
		if (null != param && param.size() > 0) {
			Set<String> set = param.keySet();
			
			Iterator<String> it = set.iterator();
			
			while (it.hasNext()) {
				String key = it.next();
				httpclient.getParams().setParameter(key, param.get(key));
			}
		}
		
		httpclient.executeMethod(method);

		String result = "";
		if (method.getStatusCode() == 200) {
			if (StringUtil.isNotEmpty(charset)) {
				result = new String(method.getResponseBody(), charset);
			} else {
				result = new String(method.getResponseBody());
			}
		} else {
			logger.info("StatusCode:{}", method.getStatusCode());
		}
		
		return result;
	}
	
	private static void setHeaders(HttpMethod method) { 
	    method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;"); 
	    method.setRequestHeader("Accept-Language", "zh-cn"); 
	    method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3"); 
	    method.setRequestHeader("Accept-Charset", "UTF-8"); 
	    method.setRequestHeader("Keep-Alive", "300"); 
	    method.setRequestHeader("Connection", "Keep-Alive"); 
	    method.setRequestHeader("Cache-Control", "no-cache"); 
	}
}