package com.sinosoft.framework.utility.weixin.common;

import com.sinosoft.framework.Config;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
public class ApiTools {
    private static final Logger logger = LoggerFactory.getLogger(ApiTools.class);
	
	public static void main(String[] args) {
		//jokeApi();
		//创建微信自定义菜单
		//createMenu();
		String s = "{\"button\":[{\"name\":\"我的账户\",\"sub_button\":[{\"type\":\"click\",\"name\":\"账户绑定\",\"key\":\"M1001\"},{\"type\":\"click\",\"name\":\"我的资产\",\"key\":\"M1002\"}]},{\"type\":\"click\",\"name\":\"我的资产\",\"key\":\"M2001\"},{\"type\":\"click\",\"name\":\"其它\",\"key\":\"M3001\"}]}";
		s=         "{\"button\":[{\"name\":\"快速投保\",\"sub_button\":[{\"name\":\"旅游保险\",\"type\":\"view\",\"url\":\""+Config.getValue("ContextPath")+"/lvyou-baoxian/index.shtml\"},{\"name\":\"意外保险\",\"type\":\"view\",\"url\":\""+Config.getValue("ContextPath")+"/yiwai-baoxian/index.shtml\"}]},{\"name\":\"我的账户\",\"sub_button\":[{\"key\":\"\",\"name\":\"绑定账号\",\"type\":\"click\"}]},{\"name\":\"最新动态\",\"sub_button\":[]}]}";

        String accessToken = "fYspFfi3QoKc6mQRcnQGKkTipxv_IxJXNxgBsVN5Tr8eGTcDwtGLpZdOxDJqwV7RH1ljY966PuzR_105uxmUqA";// 你自己的token

        createMenu(s,accessToken);
	}
	
	/**
	 * 笑话api
	 * @return
	 */
	public static String jokeApi() {
		String json = HttpRequestTools.getHttpClientHtml("http://api.xiaojianjian.net/api/show.action?m=joke");
		JSONObject obj = (JSONObject) JSONObject.fromObject(json);
		return obj.get("contextText").toString();
	}
	
	/**
	 * 段子api
	 * @return
	 */
	public static String duanziApi() {
		String json = HttpRequestTools.getHttpClientHtml("http://api.xiaojianjian.net/api/show.action?m=duanzi");
		JSONObject obj = (JSONObject) JSONObject.fromObject(json);
		return obj.get("context").toString();
	}
	/**
	 * 创建自定义菜单
	 */
	public static void createMenu(String params,String accessToken){
		/*//组装菜单
		String jsonMenu = JSONObject.fromObject("").toString();
		//路径
		String url = "";
		//组装参数
		String parameters="";
		// 调用接口创建菜单
		String json = HttpRequestTools.getHttpClientHtml(url);
		
		JSONObject obj = (JSONObject) JSONObject.fromObject(json);*/
		
		 StringBuffer bufferRes = new StringBuffer();

         try {

                 URL realUrl = new URL("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+ accessToken);

                 HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();

                 // 连接超时

                 conn.setConnectTimeout(25000);

                 // 读取超时 --服务器响应比较慢,增大时间

                 conn.setReadTimeout(25000);

                 HttpURLConnection.setFollowRedirects(true);

                 // 请求方式

                 conn.setRequestMethod("GET");

                 conn.setDoOutput(true);

                 conn.setDoInput(true);

                 conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");

                 conn.setRequestProperty("Referer", "https://api.weixin.qq.com/");

                 conn.connect();

                 // 获取URLConnection对象对应的输出流

                 OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());

                 // 发送请求参数

                 //out.write(URLEncoder.encode(params,"UTF-8"));

                 out.write(params);

                 out.flush();

                 out.close();

                 InputStream in = conn.getInputStream();

                 BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));

                 String valueString = null;

                 while ((valueString=read.readLine())!=null){

                         bufferRes.append(valueString);

                 }

                 in.close();

                 if (conn != null) {

                         // 关闭连接

                         conn.disconnect();

                 }

         } catch (Exception e) {

                logger.error(e.getMessage(), e);

         }
		
	}
}
