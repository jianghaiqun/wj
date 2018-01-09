/**
 * Project Name:wj
 * File Name:WeiXinCommonAction.java
 * Package Name:com.sinosoft.weixin
 * Date:2015年11月27日上午9:50:44
 * Copyright (c) 2015, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.weixin;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDConfigSchema;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * ClassName:WeiXinCommonAction <br/>
 * Function:TODO ADD 干什么的. <br/>
 * Date:2015年11月27日 上午9:50:44 <br/>
 *
 * @author:郭斌
 */
public class WeiXinCommon {
	private static final Logger logger = LoggerFactory.getLogger(WeiXinCommon.class);

	// 生产环境
	//static final String appid = "wx8604d814682e9f12";
	//static final String secret = "0671b7847081c0e45757806735b17e74";
	// 测试环境
	//static final String appid = "wxb11ee61d34cc6e0f";
	//static final String secret = "00cba84910df30a3a30f903c1eb0a489";
	//static final String appid = "wxe1c29f74bea5845d";
	//static final String secret = "d4624c36b6795d1d99dcf0547af5443d";
	
	public static final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
	static final String ticket_no = "3";
	static Properties props = new Properties();
	
	static {
		try {
		    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("weixin.properties");
		    InputStreamReader rd = new InputStreamReader(is, "UTF-8");
		    props.load(rd);
		    rd.close();
		    is.close();
		} catch (IOException e) {
		    logger.error(e.getMessage(), e);
		}
	}
	
	// 2挂历，3，长白山
	public static void main1(String[] args) {

//		String token = ajaxtoken();
//		System.out.println("token:" + token);
//
//		String ticket = ajaxticket(token, ticket_no);
//		System.out.println("ticket:" + ticket);

		//String pic = getPic(ticket);
	}
	
	// 获取关注会员信息
	public static void main(String[] args) {

//		HttpClientUtil httpClientUtil = new HttpClientUtil();
//		String charset = "utf-8";
//		String httpOrgCreateTest = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8604d814682e9f12&secret=0671b7847081c0e45757806735b17e74&jsoncallback=?";
//		Map<String, String> createMap = new HashMap<String, String>();
//		String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest, createMap, charset);
//		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
//		String token = jsonObject.getString("access_token");
		String token = "EPmrw9Ue7lnWVaogLOJPXa9kY4ABx18zHWC63NHuEKM_U4tTBuJTjCaekjJiMbw9meGCEHboWfvWGyE1wWlfRPq6nI_eJ3R9ajzWZjbqxz4FJgea1_aGeASppBEoJSikRWAdAFAAFJ";
	//	token = "SxVNOPtVv6DNXCWTk-nbh3gWnIKQiUej5tocIG9Q1R7CcG5roHEBgzvizUUnz6NLsK96PwEVtQahcixJmtbYEsKco1K51hbJSRXpiajn7mTnr_ZVSUg8mZLV7AKpFzytVQIbABAOJB";
//		System.out.println("token:" + token);
//		String openId = "o1u4Ks9jJNBG716ilw1neWNIdaYA";
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("touser", openId);
//		param.put("template_id", "IVR2NMxfuuuc3jRQtfPXqOst7ZfpD87ku8cVWlbrjHA");
//		param.put("url", "http://www.kaixinbao.com");
//
//		// 微信推送 拼装参数
//		Map<String, Object> dataParam = new HashMap<String, Object>();
//		dataParam.put("first", getWXDataMap("value", "您好，您的理赔申请发生状态变更。"));
//		dataParam.put("keyword1", getWXDataMap("value", "2015-12-10"));
//		dataParam.put("keyword2", getWXDataMap("value", "测试"));
//		dataParam.put("keyword3", getWXDataMap("value", "理赔资料已签收"));
//		dataParam.put("remark", getWXDataMap("value", "备注：一般2-3个工作日将有理赔反馈，请随时登陆开心保，关注理赔进度。如有疑问，请拨打4009789789咨询，感谢您的使用。"));
//		param.put("data", dataParam);
//
//		System.out.println(ajaxSendInfoToUser(token, openId,param));
		
//		String nickname = ajaxFromUserInfo(token, "oaxeEs_DxbAeMijBQ6jZ3EWirwpQ");
//		System.out.println("nickname:" + nickname);

	}
	
	public static Map<String, Object> getWXDataMap(String key, String value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		map.put("color", "#000000");
		return map;
	}
	
	public static boolean ajaxSendInfoToUser(String token, String openid, Map<String, Object> param) {
		String content = JSONObject.fromObject(param).toString();
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
		JSONObject jsonObject = httpClientUtil.httpRequest(url,"GET",content);
		if (jsonObject == null) {
			logger.error("向用户（"+openid+"）推送模板消息失败！和微信交互返回空");
			return false;
		}
		if ("0".equals(jsonObject.getString("errcode"))) {
			return true;
		} else {
			if ("40001".equals(jsonObject.getString("errcode")) || "42001".equals(jsonObject.getString("errcode"))) {
				url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + ajaxtoken();
				jsonObject = httpClientUtil.httpRequest(url,"GET",content);
				if ("0".equals(jsonObject.getString("errcode"))) {
					return true;
				}
			}
			logger.error("向用户（"+openid+"）推送模板消息失败！返回码：{} 错误信息：{}",
					jsonObject.getString("errcode"), jsonObject.getString("errmsg"));
			
			// 删除取消关注的openid
			if("43004".equals(jsonObject.getString("errcode"))){
				new QueryBuilder("delete from wxbind where openId = ?",openid).executeNoQuery();
				logger.error("删除表wxbind中取消关注的openid:{}",openid);
			}
			
			return false;
		}
	}
	
	public static String ajaxFromUserInfo(String token, String openid){
		Map<String, String> param = new HashMap<String, String>();
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String charset = "utf-8";
		String json = httpClientUtil.doPost("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=" + openid, param, charset);
		return json;
	}
	
	public static String ajaxFromUserName(String token, String openid){
		Map<String, String> param = new HashMap<String, String>();
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String charset = "utf-8";
		String json = httpClientUtil.doPost("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid=" + openid, param, charset);
		JSONObject jsonObject = JSONObject.fromObject(json);
		return jsonObject.getString("nickname");
	}
	
	/**
	 * ajax请求获取token
	 * 
	 * @return
	 */
	public static String ajaxtoken() {
		DataTable dt = new QueryBuilder("select Value, ModifyTime from zdconfig where Type='weixinToken'").executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			String token = dt.getString(0, "Value");
			String tokenGetTime = dt.getString(0, "ModifyTime");
			if (StringUtil.isNotEmpty(token) && StringUtil.isNotEmpty(tokenGetTime) && DateUtil.toDateTimeString(DateUtil.addHour(new Date(), -1)).compareTo(tokenGetTime) < 0) {
				return token;
			}
		}
		
		return getToken();
	}
	
	/**
	 * 私有方法，禁止外类调用
	 * @return
	 */
	private static String getToken() {
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String charset = "utf-8";
		String httpOrgCreateTest = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + props.get("appId")
				+ "&secret=" + props.get("appSecret") + "&jsoncallback=?";
		logger.info("token地址：{}", httpOrgCreateTest);
		Map<String, String> createMap = new HashMap<String, String>();
		String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest, createMap, charset);
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		String token = jsonObject.getString("access_token");
		
		DataTable dt = new QueryBuilder("select Value, ModifyTime from zdconfig where Type='weixinToken'").executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			new QueryBuilder("update zdconfig set Value=?, ModifyTime=? where Type='weixinToken'", token, DateUtil.getCurrentDateTime()).executeNoQuery();
		} else {
			Date date = new Date();
			ZDConfigSchema zdconfig = new ZDConfigSchema();
			zdconfig.setType("weixinToken");
			zdconfig.setName("微信token");
			zdconfig.setValue(token);
			zdconfig.setAddTime(date);
			zdconfig.setAddUser("admin");
			zdconfig.setModifyTime(date);
			zdconfig.setModifyUser("admin");
			zdconfig.insert();
		}
		
		return token;
	}

	/**
	 * 
	 * ajaxticket:(获取ticket). <br/>
	 *
	 * @author 郭斌
	 * @param token
	 * @param sceneid
	 * @return
	 */
	public static String ajaxticket(String token, String sceneid) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("action_name", "QR_LIMIT_SCENE");
		Map<String, Object> action_info = new HashMap<String, Object>();
		Map<String, Object> scene_id = new HashMap<String, Object>();
		scene_id.put("scene_id", sceneid);
		action_info.put("scene", scene_id);
		param.put("action_info", action_info);

		String content = JSONObject.fromObject(param).toString();
		logger.info("ticket入参：{}", content);
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		String httpOrgCreateTestRtn = httpClientUtil.doPost(TICKET_URL + token, content);
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		return jsonObject.getString("ticket");

	}

	/**
	 * 
	 * getPic:(获取二维码图片). <br/>
	 *
	 * @author 郭斌
	 * @param ticket
	 * @return
	 */
	public static String getPic(String ticket) {

		try {
			HttpClientUtil httpClientUtil = new HttpClientUtil();
			String charset = "utf-8";
			String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket, "utf-8");
			String httpOrgCreateTestRtn = httpClientUtil.doGet(url, charset);
			return httpOrgCreateTestRtn;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}

