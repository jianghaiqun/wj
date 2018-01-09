package com.sinosoft.weixin;

import com.sinosoft.framework.utility.weixin.common.MyX509TrustManager;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* 
 * 利用HttpClient进行post请求的工具类 
 */
public class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public String doGet(String url, String charset) {

		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String result = null;
		try {
			httpClient = new DefaultHttpClient();
			httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public String doPost(String url, Map<String, String> map, String charset) {

		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new DefaultHttpClient();
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
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public String doPost(String url, String content) {

		DataOutputStream out = null;
		BufferedReader reader = null;
		HttpURLConnection connection = null;
		try {
			// Post请求的url，与get不同的是不需要带参数
			URL postUrl = new URL(url);
			// 打开连接
			connection = (HttpURLConnection) postUrl.openConnection();

			// 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true
			connection.setDoOutput(true);
			// Read from the connection. Default is true.
			connection.setDoInput(true);
			// 默认是 GET方式
			connection.setRequestMethod("POST");

			// Post 请求不能使用缓存
			connection.setUseCaches(false);

			connection.setInstanceFollowRedirects(true);

			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
			// 进行编码
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			connection.connect();
			out = new DataOutputStream(connection.getOutputStream());
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
			out.writeBytes(content);

			out.flush();

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			return sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;

		} finally {
			try {
				if (out != null)
					out.close();
				if (reader != null)
					reader.close();
				if (connection != null)
					connection.disconnect();

			} catch (IOException e) {
				logger.error(e.getMessage(), e);

			}
		}
	}
	
	/**
	 * 
	 * 发起https请求并获取结果
	 * 
	 *
	 * 
	 * @param requestUrl
	 *            请求地址
	 * 
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * 
	 * @param outputStr
	 *            提交的数据
	 * 
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */

	public JSONObject httpRequest(String requestUrl, String requestMethod,
			String outputStr) {

		JSONObject jsonObject = null;

		StringBuffer buffer = new StringBuffer();

		try {

			// 创建SSLContext对象，并使用我们指定的信任管理器初始化

			TrustManager[] tm = { new MyX509TrustManager() };

			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");

			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);

			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();

			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);

			httpUrlConn.setDoInput(true);

			httpUrlConn.setUseCaches(false);

			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {

				OutputStream outputStream = httpUrlConn.getOutputStream();

				// 注意编码格式，防止中文乱码

				outputStream.write(outputStr.getBytes("UTF-8"));

				outputStream.close();

			}

			// 将返回的输入流转换成字符串

			InputStream inputStream = httpUrlConn.getInputStream();

			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");

			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;

			while ((str = bufferedReader.readLine()) != null) {

				buffer.append(str);

			}

			bufferedReader.close();

			inputStreamReader.close();

			// 释放资源

			inputStream.close();

			inputStream = null;

			httpUrlConn.disconnect();

			jsonObject = JSONObject.fromObject(buffer.toString());

		} catch (ConnectException ce) {
			logger.error(ce.getMessage(), ce);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return jsonObject;

	}

}