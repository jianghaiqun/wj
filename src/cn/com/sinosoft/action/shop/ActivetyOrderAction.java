package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.SDAppnt;
import cn.com.sinosoft.service.DealDataService;
import cn.com.sinosoft.service.SDAppntService;
import cn.com.sinosoft.util.HttpClientUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.weixin.WeiXinCommon;
import com.wxpay.wxap.util.Sha1Util;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 购买流程改造后核心类
 * 
 * @author sinosoft
 * 
 */
@ParentPackage("shop")
public class ActivetyOrderAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SDAppnt sdappnt = new SDAppnt();//领取人信息
	private String channelSn = "";//渠道码
	private String token="";//微信 标志
	private String jsapi_ticket=""; //微信接口列表
	private String noncestr = "";//随机串
	private String timestamp ="";//时间戳
	private String url ="";//分享的url
	private String serverContext = Config.getValue("ServerContext");
	LinkedHashMap<Object, String> mLMap = new LinkedHashMap<Object, String>();
	@Resource
	private SDAppntService mSDAppntService;
	@Resource
	private DealDataService mDealDataService;

	
	/**
	 * 首页
	 * @return
	 */
	public String initinfo(){
		
		if(StringUtil.isEmpty(channelSn)){
			channelSn="wx";
		}
		return "initinfo";
	}
	/**
	 * 活动规则页
	 * @return
	 */
	public String ruleinfo(){
		
		if(StringUtil.isEmpty(channelSn)){
			channelSn="wx";
		}
		return "ruleinfo";
	}
	/**
	 * 温馨提示页
	 * @return
	 */
	public String forinfo(){
		
		if(StringUtil.isEmpty(channelSn)){
			channelSn="wx";
		}
		return "forinfo";
	}
	/**
	 * 服务条款页
	 * @return
	 */
	public String serveinfo(){
		
		if(StringUtil.isEmpty(channelSn)){
			channelSn="wx";
		}
		return "serveinfo";
	}
	/**
	 * 领取页
	 * @return
	 */
	public String inputinfo(){
		
		if(StringUtil.isEmpty(channelSn)){
			channelSn="wx";
		}
		return "inputinfo";
	}
	/**
	 * 成功页
	 * @return
	 */
	public String resultinfo(){
		
		if(StringUtil.isEmpty(channelSn)){
			channelSn="wx";
		}
		return "resultinfo";
	}
	/**
	 * 赠险页-提交按钮
	 * @return
	 */
	public String submitinfo() {
		
		mLMap = new LinkedHashMap<Object, String>();
		Map<String, Object> tData = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.fromObject(tData);
		if(!checkInfo(sdappnt.getAppntMobile())){
			tData.put("DealFlag", "false");//错误代码 false,true
			tData.put("Msg", "对不起，亲，您已经领取过了，如有疑问，请致电95545咨询，感谢参与！");// 错误信息
			jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		if("F".equals(sdappnt.getAppntSex())){
			sdappnt.setAppntSexName("女");
		}else{
			sdappnt.setAppntSexName("男");
		}
		//格式化生日
		sdappnt.setAppntBirthDay(DateUtil.toString(sdappnt.getAppntBirthDay()));
		mLMap.put(sdappnt, "insert");
		try {
			//保存信息
			if (!mDealDataService.saveAll(mLMap)) {
				tData.put("DealFlag", "false");//错误代码 false,true
				tData.put("Msg", "对不起，赠险领取失败！");// 错误信息
				logger.error("对不起，赠险领取失败,保存信息失败！");
				jsonObject = JSONObject.fromObject(tData);
				return ajax(jsonObject.toString(), "text/html");
			}else{
				//发短信
				sendSMS(sdappnt,this.getRequest());
				//自动注册
				mSDAppntService.userRegisted(sdappnt, getRequest());
				
			}
		} catch (Exception e) {
			tData.put("DealFlag", "false");//错误代码 false,true
			tData.put("Msg", "对不起，赠险领取失败！");// 错误信息
			logger.error("对不起，赠险领取失败,错误信息："+e.getMessage(), e);
			jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		tData.put("DealFlag", "true");//错误代码 false,true
		tData.put("Msg", "");// 错误信息
		jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 判断是否已领取
	 * @param cMobile
	 * @return
	 */
	public boolean checkInfo(String cMobile){
		
		int tCount = new QueryBuilder(" SELECT COUNT(1) FROM SDAppnt WHERE appntMobile=? ",cMobile).executeInt();
		if(tCount>=1){
			return false;
		}
		return true;
	}

	/**
	 * 短信发送
	 * @param cSDAppnt
	 * @param request
	 */
	public void sendSMS(SDAppnt cSDAppnt,HttpServletRequest request){
		ActionUtil.sendSms("wj00200", cSDAppnt.getAppntMobile(), cSDAppnt.getAppntName());
	}
	
	/**
	 * ajax请求获取token
	 * @return
	 */
	public String ajaxtoken(){
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("access_token", WeiXinCommon.ajaxtoken());
		JSONObject jsonObject = JSONObject.fromObject(map);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	public String ajaxticket(){
		HttpClientUtil httpClientUtil = new HttpClientUtil();  
		String charset = "utf-8";  
		String httpOrgCreateTest = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ token + "&type=jsapi&callback=?";
        Map<String,String> createMap = new HashMap<String,String>();  
        //createMap.put("authuser","*****");  
        //createMap.put("authpass","*****");  
        //createMap.put("orgkey","****");  
        //createMap.put("orgname","****");  
        String httpOrgCreateTestRtn = httpClientUtil.doPost(httpOrgCreateTest,createMap,charset);  
        //System.out.println("result:"+httpOrgCreateTestRtn);  
		JSONObject jsonObject = JSONObject.fromObject(httpOrgCreateTestRtn);
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * 生成签名
	 * @return
	 */
	@SuppressWarnings({ "unused", "static-access" })
	public String ajaxSign(){
		try {
			com.wxpay.wxap.RequestHandler queryReq = new com.wxpay.wxap.RequestHandler(null, null);
			SortedMap<String, String> signParams = new TreeMap<String, String>();
			signParams.put("jsapi_ticket", this.jsapi_ticket);
			signParams.put("noncestr", this.noncestr);
			signParams.put("timestamp", this.timestamp);
			signParams.put("url", URLDecoder.decode(url, "UTF-8"));
			Sha1Util tSha1Util = new Sha1Util();
			String sha1 = tSha1Util.createSHA1Sign(signParams);
			Map<String, Object> tData = new HashMap<String, Object>();
			tData.put("sign", sha1);
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "";
		}
	}
	public SDAppnt getSdappnt() {
		return sdappnt;
	}
	public void setSdappnt(SDAppnt sdappnt) {
		this.sdappnt = sdappnt;
	}
	public String getChannelSn() {
		return channelSn;
	}
	public void setChannelSn(String channelSn) {
		this.channelSn = channelSn;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getServerContext() {
		return serverContext;
	}
	public void setServerContext(String serverContext) {
		this.serverContext = serverContext;
	}
	public static void main(String[] args) {
		/*Map<String, Object> data = new HashMap<String, Object>();
		data.put("MobileNum", "18610806713");
		data.put("appntname", "sunny");
		ActionUtil actionUtil = new ActionUtil();
		actionUtil.deal("wj00200", data, null);*/
		
		/*Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int rigisterDate = Integer.parseInt(sdf.format(new Date()));
		Member member = new Member();
		member.setEmail("747416867@qq.com");
		
		map.put("username", "sunny");
		map.put("PassWord", "111111");
		map.put("pwReset", "shop/password!edit.action");
		map.put("rigisterDate", rigisterDate);
		map.put("Member", member);	
		String realName = "尊敬的sunny先生/女士";
		map.put("SOSMemberRealName", realName);
		map.put("descInfo", "感谢您参与开心保网联合中英人寿、国际SOS援助中心推出的赠险活动。");
		ActionUtil.deal("wj00201", map, null);*/
	}
	public String getJsapi_ticket() {
		return jsapi_ticket;
	}
	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
