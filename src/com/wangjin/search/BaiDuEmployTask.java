package com.wangjin.search;

import com.sinosoft.framework.User;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCKeywordSchema;
import com.sinosoft.schema.ZCKeywordSet;
import com.sinosoft.schema.ZCKeywordTypeSchema;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import java.io.IOException;
import java.util.Date;

public class BaiDuEmployTask extends ConfigEanbleTaskManager {
	
	public static final String CODE = "com.wangjin.search.BaiDuEmployTask";
	
	private final static String URL_BAIDU = "http://www.baidu.com/s?wd=";
	
	private HttpClient httpClient;
	
	private void init() {
		if (httpClient == null) {
			SimpleHttpConnectionManager cm = new SimpleHttpConnectionManager();
			HttpConnectionManagerParams hcmp = new HttpConnectionManagerParams();
			hcmp.setDefaultMaxConnectionsPerHost(10);
			hcmp.setConnectionTimeout(3000);// 三秒之内必须返回
			hcmp.setSoTimeout(5000);
			hcmp.setTcpNoDelay(true);
			cm.setParams(hcmp);
			httpClient = new HttpClient(cm);
		}
	}
	
	public Mapx<String,String> getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("-1", "百度收录定时更新");
		map.put("0", "着陆页404定时检索");
		return map;
	}
	
	public void execute(long id) {
		
		init();
		
		if ("-1".equals(id + "")) {
			logger.info("百度收录定时更新任务开始执行...");
			ZCKeywordSet set = new ZCKeywordSet();
			set = new ZCKeywordSchema().query();
			for (int i = 0; i < set.size(); i++) {
				ZCKeywordSchema keyword = set.get(i);
				if("".equals(keyword.getLinkUrl()) || "http://".equals(keyword.getLinkUrl())){
					keyword.setEmployFlag("02");
				}else{
					if(baiDuEmploy(keyword.getLinkUrl())){
						//被百度成功收录
						keyword.setEmployFlag("01");
					}else{
						//收录失败
						keyword.setEmployFlag("02");
					}
				}
				keyword.update();
			}
			logger.info("百度收录定时更新任务执行结束");
		}else if ("0".equals(id + "")) {
			logger.info("着陆页404定时检索任务开始执行...");
			ZCKeywordSet set = new ZCKeywordSet();
			set = new ZCKeywordSchema().query();
			
			String keyWordType = new QueryBuilder("select ID from ZCKeywordType where siteID = ? and TypeName = '404'", Application.getCurrentSiteID()).executeString();
			
			if(StringUtil.isEmpty(keyWordType)){
				ZCKeywordTypeSchema type = new ZCKeywordTypeSchema();
				
				long keyWordTypeId = NoUtil.getMaxID("KeyWordTypeID");
				
				type.setID(keyWordTypeId);
				type.setTypeName("404");
				type.setSiteID(Application.getCurrentSiteID());
				type.setAddTime(new Date());
				type.setAddUser(User.getUserName());
				type.insert();
				
				keyWordType = "," + keyWordTypeId + ",";
			}else{
				keyWordType = "," + keyWordType + ",";
			}
			
			for (int i = 0; i < set.size(); i++) {
				ZCKeywordSchema keyword = set.get(i);
				if("".equals(keyword.getLinkUrl()) || "http://".equals(keyword.getLinkUrl())){
					keyword.setKeywordType(keyWordType);
				}else{
					if(!check404(keyword.getLinkUrl())){
						keyword.setKeywordType(keyWordType);
					}
				}
				keyword.update();
			}

			logger.info("着陆页404定时检索任务执行结束");
		}
		((SimpleHttpConnectionManager)httpClient.getHttpConnectionManager()).shutdown();
	}
	
	public boolean isRunning(long arg0) {
		return false;
	}
	
	public String getCode() {
		return CODE;
	}
	
	public String getName() {
		return "关键词更新任务";
	}
	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}
	@Override
	public String getID() {
		return "com.wangjin.search.BaiDuEmployTask";
	}
	
	@SuppressWarnings("finally")
	private boolean baiDuEmploy (String url){
		
		boolean result = false;
		
	    HttpMethod method = new GetMethod();
	    
	    try {
	    
		    //把参数按照UTF-8格式编码  
		    URI uri = new URI(url,false,"UTF-8");
		    url = uri.toString(); 
			
			//创建一个HttpMethod
		    method = new GetMethod(URL_BAIDU+url);
		      
		    //执行方法  
		    httpClient.executeMethod(method);
			//解决百度二次跳转问题
			int statusCode = httpClient.executeMethod(method);
			
			if (statusCode != HttpStatus.SC_OK) {  
			        System.err.println("Method failed: " + method.getStatusLine());
			}  
			   
			//获得返回的结果  
			byte[] responseBody = method.getResponseBody();
			
			String body = new String(responseBody,"UTF-8");
			
			if(body.indexOf("抱歉，没有找到与<span style=\"font-family:宋体\">“</span><em>") > -1){
				result = false;
			}
			else if (body.indexOf("<p><strong class=f14>没有找到该URL。您可以直接访问&nbsp;<a href=\"") > -1){
				result = false;
			}else{
				result = true;
			}
	  
	    } catch (HttpException e) {
			logger.error("发生错误:网络验证异常！" + e.getMessage(), e);
	  
	    } catch (IOException e) {
	    	logger.error("发生错误:连接超时！" + e.getMessage(), e);
			
	    }
	    finally {
	        //释放链接资源 
	    	method.releaseConnection();
	    	return result;
	    	
	    }
	}
	
	/**
	 * 模拟浏览器发送Http请求,检查着陆页是否404
	 * 
	 * @param url 验证地址
	 * @param num 数据序号
	 * 
	 * @return 验证结果
	 */
	@SuppressWarnings("finally")
	public boolean check404 (String url) {
		
		boolean result = false;
		 
	    //创建一个HttpMethod
	    HttpMethod method = new GetMethod();
	    
	    //执行方法  
	    try {
	    
		    //把参数按照UTF-8格式编码  
		    URI uri = new URI(url,false,"UTF-8");
		    url = uri.toString();
		    
		    method = new GetMethod(url);
		    
			int statusCode = httpClient.executeMethod(method);
			
			
			if (statusCode != HttpStatus.SC_OK) {
				result = false;
			} else {
				result = true;
			}
	  
	    } catch (HttpException e) {
	    	logger.error("网络验证异常，请联系管理员!" + e.getMessage(), e);
	  
	    } catch (IOException e) {
	    	logger.error("请检查网络是否畅通!" + e.getMessage(), e);
	  
	    } finally {
	        //释放链接资源 
	    	method.releaseConnection();
	    	return result;
	  
	    }
	}
	
}
