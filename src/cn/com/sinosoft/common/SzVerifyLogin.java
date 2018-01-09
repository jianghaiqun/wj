package cn.com.sinosoft.common;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MemberRank;
import cn.com.sinosoft.service.MemberRankService;
import cn.com.sinosoft.service.MemberService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SzVerifyLogin extends AbstractInterceptor{
	private final static Logger logger = LoggerFactory.getLogger(SzVerifyLogin.class);
	@Resource
	private MemberService memberService;
	
	@Resource
	private MemberRankService memberRankService;
	
	private Member member;

	// 获取Request
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	// 获取Response
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
		String flag="";
		try {
		SzPayUtil szPayUtil = new SzPayUtil();
		Cookie mycookies[] = request.getCookies();
		String sid = "";
		if (mycookies!= null) {
			for (int i = 0; i < mycookies.length; i++) {
			if ("LMSH_SID".equalsIgnoreCase(mycookies[i].getName())) {
			sid=mycookies[i].getValue();
			}
			}
		}
		HttpURLConnection conn = null;
		String secKey = "bamjgipetdlcrwqfzokxuysh"; //移动商城登录用密钥
		logger.info("登录接口用密钥:{}", secKey);
		JSONObject resMsg = new JSONObject();
	    String ckey = "insurance";//客户端唯一标识码
	    String timestampstr = szPayUtil.getTimestamp();
	    String noncestr = szPayUtil.getNonce();
	    resMsg.put("ckey", ckey);
	    resMsg.put("timestamp", timestampstr);
	    resMsg.put("nonce", noncestr);
	    String baseStr = "";
	    baseStr = "ckey="+ckey+"&timestamp="+timestampstr+"&nonce="+noncestr;
	    
//	    for (Object key : new TreeSet(resMsg.keySet())) {
//			baseStr += ("&" + key + "=" + resMsg.get(key));
//			System.out.println("---"+baseStr);
//		}
//		if(!StringUtils.isEmpty(baseStr)){
//			baseStr = baseStr.substring(1, baseStr.length());
//		}
		
		logger.info("签名源串：{}", baseStr);
		String sign_ = szPayUtil.doSign(baseStr, secKey);
		logger.info("签名后的报文：{}", sign_);
		resMsg.put("signature", sign_);
		logger.info("request: {}", resMsg.toString());
		logger.info("cookie值：{}", sid);
//	    Properties prop = System.getProperties();
//	    prop.setProperty("http.proxyHost", "10.182.0.2");
//	    prop.setProperty("http.proxyPort", "3128");
	    //	    URL url = new URL("http://127.0.0.1:8080/httpServer"); 
	    URL url = new URL("http://www.12580777.com/partners/verifylogin.do");
	    conn = (HttpURLConnection)url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setDoOutput(true); 
	    conn.setDoInput(true);
	    conn.setRequestProperty("Charset", "UTF-8");
	    conn.setRequestProperty("Cookie" , "LMSH_SID="+sid+";"); 
	    OutputStream buf = conn.getOutputStream();
	    buf = new BufferedOutputStream(buf);
	    OutputStreamWriter out = new  OutputStreamWriter (buf);
	    out.write(resMsg.toString());//json串
	    out.flush();
	    out.close();
	    logger.info("conn.getResponseCode():{}", conn.getResponseCode());
		logger.info("HttpURLConnection.HTTP_OK:{}", HttpURLConnection.HTTP_OK);
	    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
             logger.info( "connect failed!");
	        //接收数据
	         InputStream in = conn.getInputStream();
	         in = new BufferedInputStream(in);
	         InputStreamReader rData = new InputStreamReader(in);
	         BufferedReader br=new BufferedReader(rData); //提供缓冲读取  提高速度
	         String str=br.readLine() ;//读取一行
	         logger.info("返回的报文:{}", str);
	         Map<String, String> map = new HashMap<String, String>();
	         map =  getJsonBackStr(str);
	         flag = memberFlag(map);
	         in.close();
	         br.close();
	         }catch (Exception e) {
				logger.error("发送POST请求出现异常！" + e.getMessage(), e);
			 }
		if("0".equals(flag)||"".equals(flag)){//未登录
			return "szlogin";
		}else{//已经登录
			return invocation.invoke();
		}
	}
	
	/**   
     * 调用登录验证  接口
     *    
     * @param
     * @param
     */  
	public String postHttp(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String responseMsg = "";
		String flag="";
		try {
		SzPayUtil szPayUtil = new SzPayUtil();
		//取cookie-LMSH_SID值
//		Cookie cookieSz = new Cookie("LMSH_SID", null);
		Cookie mycookies[] = request.getCookies();
//		String cookie = cookieSz.getValue();
		String sid = "";
		if (mycookies!= null) {
			for (int i = 0; i < mycookies.length; i++) {
			if ("LMSH_SID".equalsIgnoreCase(mycookies[i].getName())) {
			sid=mycookies[i].getValue();
			}
			}
		}
		logger.info("LMSH_SID==={}", sid);
		HttpURLConnection conn = null;
		String secKey = "bamjgipetdlcrwqfzokxuysh"; //移动商城登录用密钥
		logger.info("登录接口用密钥:{}", secKey);
		JSONObject resMsg = new JSONObject();
		//生成int类型，随机数
		String nonceStr = szPayUtil.getNonce();
		//时间戳
	    String timestamp = szPayUtil.getTimestamp();
	    //客户端唯一标识码
	    String ckey = "insurance";
	    //签名串
	    String timestampstr = szPayUtil.getTimestamp();
	    String noncestr = szPayUtil.getNonce();
	    resMsg.put("ckey", ckey);
	    resMsg.put("timestamp", timestampstr);
	    resMsg.put("nonce", noncestr);
	    String baseStr = "";
	    baseStr = "ckey="+ckey+"&timestamp="+timestampstr+"&nonce="+noncestr;
	    
//	    for (Object key : new TreeSet(resMsg.keySet())) {
//			baseStr += ("&" + key + "=" + resMsg.get(key));
//			System.out.println("---"+baseStr);
//		}
//		if(!StringUtils.isEmpty(baseStr)){
//			baseStr = baseStr.substring(1, baseStr.length());
//		}
		
		logger.info("签名源串：{}", baseStr);
		String sign_ = szPayUtil.doSign(baseStr, secKey);
		logger.info("签名后的报文：{}", sign_);
		resMsg.put("signature", sign_);
		logger.info("request: {}", resMsg.toString());
	    logger.info("cookie值：{}", sid);
//	    Properties prop = System.getProperties();
//	    prop.setProperty("http.proxyHost", "10.182.0.2");
//	    prop.setProperty("http.proxyPort", "3128");
	    //	    URL url = new URL("http://127.0.0.1:8080/httpServer"); 
	    URL url = new URL("http://www.12580777.com/partners/verifylogin.do");
	    conn = (HttpURLConnection)url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setDoOutput(true); 
	    conn.setDoInput(true);
	    conn.setRequestProperty("Charset", "UTF-8");
	    conn.setRequestProperty("Cookie" , "LMSH_SID="+sid+";"); 
	    OutputStream buf = conn.getOutputStream();
	    buf = new BufferedOutputStream(buf);
	    OutputStreamWriter out = new  OutputStreamWriter (buf);
	    out.write(resMsg.toString());//json串
	    out.flush();
	    out.close();
	    logger.info("conn.getResponseCode():{}", conn.getResponseCode());
	    logger.info("HttpURLConnection.HTTP_OK:{}", HttpURLConnection.HTTP_OK);
	    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
             logger.info( "connect failed!");
	        //接收数据
	         InputStream in = conn.getInputStream();
	         in = new BufferedInputStream(in);
	         InputStreamReader rData = new InputStreamReader(in);
	         BufferedReader br=new BufferedReader(rData); //提供缓冲读取  提高速度
	         String str=br.readLine() ;//读取一行
	         logger.info("返回的报文:{}", str);
	         Map<String, String> map = new HashMap<String, String>();
	         map =  getJsonBackStr(str);
	         flag = memberFlag(map);
	         in.close();
	         br.close();
	         }catch (Exception e) {
				logger.error("发送POST请求出现异常！" + e.getMessage(), e);
			 }
		
		return responseMsg;
	}
	
	/**   
     * 构造Json串   
     *    
     * @param String ckey,Long timestamp,int nonce,String signature   
     * @param key  加密使用的key   
     */   
	public String getJsonStr(String ckey,String timestamp,String nonce,String signature){
		JSONObject json = new JSONObject();
		json.put("ckey", ckey);
	    json.put("timestamp", timestamp);
	    json.put("nonce", nonce);
	    json.put("signature", signature);
	    String jsonString = json.toString();
	    return jsonString;
	}
	
	/**   
     * 解析Json串  （登陆接口返回报文） 
     *    
     * @param String str json报文
     * @param key  加密使用的key   
     */   
	public Map getJsonBackStr(String str){
		JSONObject jsonBack = JSONObject.fromObject(str);
        Map<String, String> map = new HashMap<String, String>();
        try{
        	String flag = jsonBack.getString("flag");
        	if("1".equals(flag)){//成功登陆
        		map.put("flag",jsonBack.getString("flag"));
        		map.put("user_id",jsonBack.getString("user_id"));//用户唯一标识
				map.put("user_nick",  URLEncoder.encode(jsonBack.getString("user_nick"), "UTF-8"));//用户昵称
				map.put("user_email", jsonBack.getString("user_email"));//用户邮箱
				map.put("user_mobile", jsonBack.getString("user_mobile"));//用户手机号
				map.put("user_type", jsonBack.getString("user_type"));//用户类型 1：普通会员 2：定制会员
        	}else{
        		map.put("flag","0");
        	}
//			if(jsonBack.getString("user_id").length()>0){
//				map.put("user_id",jsonBack.getString("user_id"));//用户唯一标识
//				map.put("user_nick",  URLEncoder.encode(jsonBack.getString("user_nick"), "UTF-8"));//用户昵称
//				map.put("user_email", jsonBack.getString("user_email"));//用户邮箱
//				map.put("user_mobile", jsonBack.getString("user_mobile"));//用户手机号
//				map.put("user_type", jsonBack.getString("user_type"));//用户类型 1：普通会员 2：定制会员
//			}        
		}catch (Exception e) {
			logger.error("返回报文解析异常！" + e.getMessage(), e);
		}
        
	    return map;
	}
	
    /**   
     * 生成签名数据   
     *    
     * @param data 待加密的数据   
     * @param key  加密使用的key   
     * @throws InvalidKeyException   
     * @throws NoSuchAlgorithmException   
     */    
    public String getSignature(String data,String key) throws Exception{  
    		String HMAC_SHA1 = "HmacSHA1"; 
    		byte[] keyBytes=key.getBytes();  
	        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);     
	        Mac mac = Mac.getInstance(HMAC_SHA1);     
	        mac.init(signingKey);     
	        byte[] rawHmac = mac.doFinal(data.getBytes());  
	        StringBuilder sb=new StringBuilder();  
	        for(byte b:rawHmac){  
	         sb.append(byteToHexString(b));  
	        }  
	        return sb.toString();     
    }  
      
    private String byteToHexString(byte ib){  
	     char[] Digit={  
	       '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'  
	     };  
	     char[] ob=new char[2];  
	     ob[0]=Digit[(ib>>>4)& 0X0f];  
	     ob[1]=Digit[ib & 0X0F];  
	     String s=new String(ob);  
	     return s;           
    }          
    /**   
     * 进行Base64编码   
     *    
     * @param String s   
     * @param key  加密使用的key   
     * @throws InvalidKeyException   
     * @throws NoSuchAlgorithmException   
     */  
    public String getBASE64(String s) {   
    	if (s == null) return null;   
    	return (new sun.misc.BASE64Encoder()).encode( s.getBytes() );   
    	}   
    	  
    	// 将 BASE64 编码的字符串 s 进行解码   
    public String getFromBASE64(String s) {   
    	if (s == null) return null;   
    	BASE64Decoder decoder = new BASE64Decoder();   
    	try {   
    	byte[] b = decoder.decodeBuffer(s);   
    	return new String(b);   
    	} catch (Exception e) {   
    	return null;   
    	}   
    }  
    public String memberFlag(Map map)throws Exception  {
    	//根据用户登录状态进行后续操作
    	String userId = "";
    	String flag = map.get("flag").toString();
//    	Member memberForSz = new Member();
//    	String memberMobile = "";
    	if("1".equals(flag)){//已登录
       	 	//查询bbx中的用户sission及cookie
    		userId = map.get("user_id").toString();
    		String memberMobile = map.get("user_mobile").toString();
    		String memberEmail = map.get("user_email").toString();
    		Member memberForSz = new Member();
    		Member memberForSzModel = new Member();
    		memberForSz = memberService.getMemberByUsername(memberMobile);
//    		memberForSz = (Member)memberService.get("szMemberUserId", userId);
    		
    		if(memberForSz == null){
    			//存储用户数据
    			BigDecimal decimal = new BigDecimal(0);
    			
    			memberForSzModel.setUsername(memberMobile); //手机号存为bbx用户名
    			memberForSzModel.setEmail(memberEmail);
    			memberForSzModel.setPassword(DigestUtils.md5Hex("szyd"));
    			memberForSzModel.setSafeQuestion(null);
    			memberForSzModel.setSafeAnswer(null);
    			memberForSzModel.setIsAccountLocked("N");
    			memberForSzModel.setLoginFailureCount(0);
    			memberForSzModel.setPasswordRecoverKey(null);
    			memberForSzModel.setLockedDate(null);
    			memberForSzModel.setLoginDate(new Date());
    			memberForSzModel.setLoginIp(null);
    			memberForSzModel.setMemberAttributeMap(null);
    			memberForSzModel.setReceiverSet(null);
    			memberForSzModel.setFavoriteProductSet(null);
    			memberForSzModel.setSzMemberUserId(userId);
    			memberForSzModel.setDeposit(decimal);
    			memberForSzModel.setIsAccountEnabled("Y");
    			memberForSzModel.setIsAccountLocked("N");
    			memberForSzModel.setPoint(0);
    			memberForSzModel.setRegisterIp("127.0.0.1");
    			MemberRank memberRank = new MemberRank();
    			memberRank =  memberRankService.getMemberRankByPoint(0);
    			memberForSzModel.setMemberRank(memberRank);
    			memberService.save(memberForSzModel);
    			memberForSz = memberForSzModel;
    		}
    			//执行登录操作
    			// 写入会员登录Session
    			setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, memberForSz.getId());
    			// 写入会员登录Cookie
    			Cookie loginMemberUsernameCookie = new Cookie(Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(memberForSz.getUsername().toLowerCase(), "UTF-8"));
    			loginMemberUsernameCookie.setPath(getRequest().getContextPath() + "/");
    			getResponse().addCookie(loginMemberUsernameCookie);
    		
    		/*ActionContext actionContext = ActionContext.getContext();   
    		Map session= actionContext.getSession();  
    		String loginMemberId=(String)session.get(Member.LOGIN_MEMBER_ID_SESSION_NAME); 
//    		String loginMemberId = (String) invocation.getInvocationContext().getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME);
    		if(!"".equals(loginMemberId)){
    			loginMember = memberService.load(loginMemberId);
    			String loginMemberUserId = loginMember.getSzMemberUserId();
	    			if(loginMember.getSzMemberUserId() != userId){//如果session的userid不等于返回报文中的user_id,
	    				
	    			}
    			
    			}
    		
    		//无sission则查询是否存在该用户
    		memberForSz = memberService.get("szMemberUserId", userId);
    		if(memberForSz != null){
    		}*/
    		
    		//有-进行登录处理并写sission，无-创建新用户
        }
    	return flag;
    }
	// 设置Session
	public void setSession(String name, Object value) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(name, value);
	}


}
