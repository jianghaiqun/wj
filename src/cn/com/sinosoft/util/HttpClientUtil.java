package cn.com.sinosoft.util;

import com.sinosoft.framework.utility.StringUtil;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* 
 * 利用HttpClient进行post请求的工具类 
 */
public class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public String doPost(String url, Map<String, String> map, String charset) {

		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return result;
	}

	/**
	 * 调用 API
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception 
	 */
	public static String doPost(String url, Map<String, Object> param) throws Exception {

		String result = "";
		HttpClient httpCLient = null;
		try {
			httpCLient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(JSONObject.fromObject(param).toString(), "UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			httpCLient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,5000);//连接时间
			httpCLient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,6000);
			HttpResponse response = httpCLient.execute(httpPost);
			if (entity != null) {
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			EntityUtils.consume(entity);

		} finally {
			if (httpCLient != null) {
				httpCLient.getConnectionManager().shutdown();
			}
		}
		return result;
	}

	/**
	 * 调用 API
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception 
	 */
	public static String doPost(String url, String param) throws Exception {

		String result = "";
		HttpClient httpCLient = null;
		try {
			httpCLient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(param, "UTF-8");
			entity.setContentType("text/plain; charset=utf-8");
			httpPost.setEntity(entity);
			httpCLient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);//连接时间
			httpCLient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,10000);
			HttpResponse response = httpCLient.execute(httpPost);
			if (entity != null) {
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			EntityUtils.consume(entity);

		} finally {
			if (httpCLient != null) {
				httpCLient.getConnectionManager().shutdown();
			}
		}
		return result;
	}
	
	/**
	 * 调用 API
	 * 
	 * @param parameters
	 * @return
	 */
	public static String doPostByStream(String url, Map<String, Object> param) {

		String result = "";

		try {
			HttpClient httpCLient = new SSLClient();
			HttpPost httpPost = new HttpPost(url);
			String str = JSONObject.fromObject(param).toString();
			InputStream is = new ByteArrayInputStream(str.getBytes("UTF-8"));
			InputStreamEntity entity = new InputStreamEntity(is, is.available());
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			HttpResponse response = httpCLient.execute(httpPost);
			if (entity != null) {
				result = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
		}
		return result;
	}
	
	public static Map<String, Object> parserToMap(String s) {

		if (StringUtil.isEmpty(s)) {
			return new HashMap<String, Object>();
		}

		Map<String, Object> doResult = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(s);
		Iterator<String> keys = json.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			doResult.put(key, json.get(key));

		}

		return doResult;
	}
}