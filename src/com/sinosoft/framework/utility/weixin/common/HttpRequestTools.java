package com.sinosoft.framework.utility.weixin.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestTools {
	private static final Logger logger = LoggerFactory.getLogger(HttpRequestTools.class);
	/**
	 * 根据URL获得所有的html信息
	 * 
	 * @param url
	 * @return
	 */
	public static String getHttpClientHtml(String url,String code) {
		String html = null;
		HttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
		HttpGet httpget = new HttpGet(url);// 以get方式请求该URL
		try {
			HttpResponse responce = httpClient.execute(httpget);// 得到responce对象
			int resStatu = responce.getStatusLine().getStatusCode();// 返回码
			if (resStatu == HttpStatus.SC_OK) {// 200正常 其他就不对
				// 获得相应实体
				HttpEntity entity = responce.getEntity();
				if (entity != null) {
					html = new String(EntityUtils.toString(entity).getBytes("ISO-8859-1"),code);// 获得html源代码
				}
			}
		} catch (Exception e) {
			logger.error("访问【"+url+"】出现异常!" + e.getMessage(), e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return html;
	}
	
	
	/**
	 * 默认编码获取HTML代码
	 * @param url
	 * @return
	 */
	public static String getHttpClientHtml(String url) {
		String html = null;
		HttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
		HttpGet httpget = new HttpGet(url);// 以get方式请求该URL
		
		try {
			HttpResponse responce = httpClient.execute(httpget);// 得到responce对象
			int resStatu = responce.getStatusLine().getStatusCode();// 返回码
			if (resStatu == HttpStatus.SC_OK) {// 200正常 其他就不对
				// 获得相应实体
				HttpEntity entity = responce.getEntity();
				if (entity != null) {
					html = EntityUtils.toString(entity);// 获得html源代码
				}
			}
		} catch (Exception e) {
			logger.error("访问【" + url + "】出现异常!" + e.getMessage(), e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return html;
	}
	/**
	 * 默认编码获取HTML代码--POST
	 * @param url
	 * @return
	 */
	public static String getHttpClientHtmlByPost(String url) {
		String html = null;
		HttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
		HttpPost httppost = new HttpPost(url);// 以get方式请求该URL
		
		try {
			HttpResponse responce = httpClient.execute(httppost);// 得到responce对象
			int resStatu = responce.getStatusLine().getStatusCode();// 返回码
			if (resStatu == HttpStatus.SC_OK) {// 200正常 其他就不对
				// 获得相应实体
				HttpEntity entity = responce.getEntity();
				if (entity != null) {
					html = EntityUtils.toString(entity);// 获得html源代码
				}
			}
		} catch (Exception e) {
			logger.error("访问【" + url + "】出现异常!" + e.getMessage(), e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return html;
	}
}
