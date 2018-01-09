package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.BindInfoForLogin;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.MemberService;
import com.alipay.util.AlipayNotify;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.Avatar;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.UnionLoginUtil;
import weibo4j.Users;
import weibo4j.model.User;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class TencentLoginAfterAction extends BaseShopAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4937197641273486971L;
	//openId:Tencent用户唯一标标识
    String openID = "";
    //windowAddr:注册前地址
    private String windowAddr = null;
    //第三方机构编码
	private String comCode = "";
	//第三方机构名称
	private String comName = "";
	//第三方Email
	private String userEmail = "";
	//第三方手机号
	private String userPhone = "";
	//头像
	private String Avatar = "";
	//昵称
	private String nickName = "";
	//性别
	private String gender = "";
	//登陆成功后回调地址
	private String loginBackURL;
	//返回地址
	private String returnURL;
	private BindInfoForLogin bindInfoForLogin = new BindInfoForLogin();
	private UnionLoginUtil tUnionLoginUtil = new UnionLoginUtil();
	public String getWindowAddr() {
		return windowAddr;
	}

	public void setWindowAddr(String windowAddr) {
		this.windowAddr = windowAddr;
	}
	@Resource
	private MemberService memberService;
	@Resource
	private BindInfoForLoginService bindInfoForLoginService;
	
	public String loginAfter() throws Exception{
        this.getResponse().setContentType("text/html; charset=utf-8");
        loginBackURL = (String)this.getRequest().getSession().getAttribute("backurl");
        //if(loginBackURL.endsWith("/")){loginBackURL = loginBackURL+"index.shtml";}
        comCode = (String)this.getRequest().getSession().getAttribute("comCode");
        bindInfoForLogin.setComCode(comCode);
        if(windowAddr==null||"".equals(windowAddr)){
			windowAddr=getRequest().getHeader("Referer");
			//若windowAddr 来源页面 是用POST方式提交，那么URL中会有？,或者action结束，这样直接跳回去必然报错，还是返回首页~
			if(windowAddr==null||"".equals(windowAddr)||windowAddr.endsWith("?")||windowAddr.endsWith("action")){
				windowAddr=getRequest().getScheme() + "://" +  getRequest().getServerName() + ":"
			    + getRequest().getServerPort()+ "/";//没有找到来源 返回首页
			}
		}
        if("Tencent".equals(comCode)){
        	dealTencent(this.getRequest(),this.getResponse());
        	 
        }else if("Sina".equals(comCode)){
        	dealSina(this.getRequest(),this.getResponse());
        }else if("Alipay".equals(comCode)){
        	dealAlipay(this.getRequest(),this.getResponse());
        }
        return returnURL;
    }

	/**
	 * 
	 * @Title: dealQQCB
	 * @Description: TODO(处理QQ彩贝登陆信息)
	 * @param @param tRequest
	 * @param @param tResponse
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @author zhangjing
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String dealQQCB() throws Exception {
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		//验证MD5加密
		//将form参数按照字典序进行排序
		Enumeration params = request.getParameterNames();
		Vector vc = new Vector();
		while (params.hasMoreElements()) {
			vc.add((String) params.nextElement());
		}
		String[] paramArr = (String[]) vc.toArray(new String[1]);
		int paramLen = paramArr.length;
		int tempLen = paramLen - 1;
		int i, j;
		String tempStr = "";
		// 从小到大排序
		for (i = 0; i < tempLen; i++) {
			for (j = i + 1; j < paramLen; j++) {
				if (paramArr[i].compareTo(paramArr[j]) > 0) {
					tempStr = paramArr[i];
					paramArr[i] = paramArr[j];
					paramArr[j] = tempStr;
				}
			}
		}
		// 进行md5加密比较
		String rawMd5Str = "";
		for (i = 0; i < paramLen; i++) {
			if (paramArr[i].compareTo("Vkey") != 0) { // 签名串不要Vkey这个参数
				rawMd5Str += request.getParameter(paramArr[i]);
			}
		}
		String md5_1 = (MD5(rawMd5Str + Config.map_property.get("QQ_FANLI_CONNECT_KEY1"))).toLowerCase();
		String md5_2 = (MD5(md5_1 + Config.map_property.get("QQ_FANLI_CONNECT_KEY2"))).toLowerCase();
		// 取得Vkey值
		String vkey = request.getParameter("Vkey");
		if (vkey != null && md5_2.compareTo(vkey) != 0) {
			// 如果vkey检测不通过，跳转到开心保首页
			response.sendRedirect(Config.map_property.get("HOME_PAGE_URL"));
			return null;
		}
		//获得彩贝参数
		//String acct = request.getParameter("Acct");
		String url = request.getParameter("Url"); //目标地址
		openID = request.getParameter("OpenId");
		String viewinfo = request.getParameter("ViewInfo");
		String[] arr1 = viewinfo.split("&");
		String[] arr2 = {};
		HashMap viewinfoMap = new HashMap();
		int arrLen = arr1.length;
		for (i = 0; i < arrLen; i++) {
			arr2 = arr1[i].split("=");
			if (arr2.length > 1) {
				viewinfoMap.put(arr2[0], URLDecoder.decode(arr2[1], "UTF-8"));
			}
		}
		// 用户的QQ昵称根据需要保存cookie
		String nickname = (String) viewinfoMap.get("NickName");
		//供前台显示昵称
		
		// 彩贝提示条，需要显示在站点顶部
		String headshow = (String) viewinfoMap.get("HeadShow");
		String showmsg = (String) viewinfoMap.get("ShowMsg");
		this.nickName=showmsg;
		String JifenUrl = (String) viewinfoMap.get("JifenUrl");
		bindInfoForLogin.setComCode("Tencent");
		bindInfoForLogin.setUserNickName(replaceHTML(nickname).replaceAll("\\+", "%20"));
		bindInfoForLogin.setOpenID(openID);
		//保存showmsg到cookies
		Cookie ck = new Cookie(Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(replaceHTML(showmsg), "UTF-8").replaceAll("\\+", "%20"));
		//保存headshow到cookies
		Cookie ck_headshow = new Cookie("QQCB_headshow", URLEncoder.encode(headshow, "UTF-8").replaceAll("\\+", "%20"));
		//保存JifenUrl到cookies
		Cookie ck_JifenUrl = new Cookie("QQCB_JifenUrl", URLEncoder.encode(JifenUrl, "UTF-8").replaceAll("\\+", "%20"));
		//检查QQ绑定信息
		boolean bindFlag = tUnionLoginUtil.checkBind(openID, "Tencent");
		// 写入会员登录Cookie
		Cookie loginMemberUsernameCookie;
		Cookie loginFlagCookie;
		try {
			loginMemberUsernameCookie = new Cookie(Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(replaceHTML(showmsg), "UTF-8").replaceAll("\\+", "%20"));
			loginMemberUsernameCookie.setPath(getRequest().getContextPath() + "/");
			response.addCookie(loginMemberUsernameCookie);
			setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, "tencent");
			loginFlagCookie = new Cookie(Member.LOGIN_MEMBER_ID_SESSION_NAME, URLEncoder.encode("tencent", "UTF-8"));
			loginFlagCookie.setPath(getRequest().getContextPath() + "/");
			response.addCookie(loginFlagCookie);
			// 获取服务器应用地址
			GetDBdata db = new GetDBdata();
			String[] temp = { "FrontServerContext" };
			String url_cookie = db.getOneValue("select value from zdconfig where type=?", temp);
			if (url_cookie.endsWith("/")) {
				loginMemberUsernameCookie.setPath(url_cookie);
				loginFlagCookie.setPath(url_cookie);
				ck.setPath(url_cookie);
				ck_headshow.setPath(url_cookie);
				ck_JifenUrl.setPath(url_cookie);
			} else {
				loginMemberUsernameCookie.setPath(url_cookie + "/");
				loginFlagCookie.setPath(url_cookie + "/");
				ck.setPath(url_cookie + "/");
				ck_headshow.setPath(url_cookie + "/");
				ck_JifenUrl.setPath(url_cookie + "/");
			}
			// 设置cookie时长为1天
			ck.setMaxAge(10);
			ck_headshow.setMaxAge(86400);
			ck_JifenUrl.setMaxAge(86400);
			loginMemberUsernameCookie.setMaxAge(86400);
			loginFlagCookie.setMaxAge(86400);
			response.addCookie(ck);
			response.addCookie(ck_headshow);
			response.addCookie(ck_JifenUrl);
			response.addCookie(loginFlagCookie);
			response.addCookie(loginMemberUsernameCookie);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (bindFlag) {
			String bindID = bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID).getId();
			setSession("loginBindId", bindID);
			Cookie loginBindCookie = new Cookie("loginBindId", URLEncoder.encode(bindID, "UTF-8"));
			loginBindCookie.setMaxAge(30 * 100000);
			response.addCookie(loginBindCookie);
		} else {
			String bindID = "";
			//非第一次登陆但未注册
			if (bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID) != null) {
				bindInfoForLogin = bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
				bindID = bindInfoForLogin.getId();
			}
			//第一次登陆
			if ("".equals(bindID)) {
				otherloginUseMailAndPhone();
				bindInfoForLoginService.save(bindInfoForLogin);
				bindInfoForLogin = bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
				bindID = bindInfoForLogin.getId();
			}
			setSession("loginBindId", bindID);
			Cookie loginBindCookie = new Cookie("loginBindId", URLEncoder.encode(bindID, "UTF-8"));
			loginBindCookie.setMaxAge(30 * 100000);
			response.addCookie(loginBindCookie);
			//防止用戶名包含alert等特殊字
			bindInfoForLogin.setUserNickName("");
			// 开心保注册页
		}
		response.sendRedirect(url);
		return null;
	}

	private String replaceHTML(String str) {
		String s1 = str.replaceAll("</?[^>]+>", "");
		s1 = s1.replaceAll("\\&nbsp;", " ");
		s1 = s1.replaceAll("\\&lt;", "<");
		s1 = s1.replaceAll("\\&gt;", ">");
		s1 = s1.replaceAll("\\&mdash;", "");
		s1 = s1.replaceAll("\\&deg;", "");
		s1 = s1.replaceAll("\\&ldquo;", "");
		s1 = s1.replaceAll("\\&rdquo;", "");
		s1 = s1.replaceAll("\\&middot;", "");
		s1 = s1.replaceAll("\\&lsquo;", "‘");
		s1 = s1.replaceAll("\\&rsquo;", "’");
		s1 = s1.replaceAll("\\&hellip;", "…");
		s1 = s1.replaceAll("&#039;", "'");
		s1 = s1.replaceAll("&amp;", "&");
		return s1;
	}

	private String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}
	/**
	 * 騰訊聯合登錄
	 * @param tRequest
	 * @param tResponse
	 * @return
	 * @throws Exception
	 */
	public String dealTencent(HttpServletRequest tRequest,HttpServletResponse tResponse) throws Exception{
		
		 try {
	            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(this.getRequest());

	            String accessToken   = null;
	            long tokenExpireIn = 0L;

	            if (accessTokenObj.getAccessToken().equals("")) {
//	                我们的网站被CSRF攻击了或者用户取消了授权
//	                做一些数据统计工作
	                logger.warn("没有获取到响应参数");
	                tResponse.sendRedirect(loginBackURL);
	            } else {
	                accessToken = accessTokenObj.getAccessToken();
	                tokenExpireIn = accessTokenObj.getExpireIn();

	                tRequest.getSession().setAttribute("qq_access_token", accessToken);
	                tRequest.getSession().setAttribute("qq_token_expirein", String.valueOf(tokenExpireIn));

	                // 利用获取到的accessToken 去获取当前用的openid -------- start
	                OpenID openIDObj =  new OpenID(accessToken);
	                openID = openIDObj.getUserOpenID();
	                bindInfoForLogin.setOpenID(openID);
	                tRequest.getSession().setAttribute("qq_openid", openID);
	                // 利用获取到的accessToken 去获取当前用户的openid --------- end

	                //利用openID与开心保网站进行绑定;
	               
	                logger.info("start -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息 ---------------------------- start");
	                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
	                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
	                this.comName="QQ";
	                bindInfoForLogin.setComName(comName);
	                if (userInfoBean.getRet() == 0) {
	                	Avatar tAvatar = userInfoBean.getAvatar();
	                	logger.info("昵称：{}", userInfoBean.getNickname());
	                	this.nickName = StringUtil.deleteEmoji(userInfoBean.getNickname());
	                	bindInfoForLogin.setUserNickName(nickName);
	                	logger.info("头像：{}", tAvatar.getAvatarURL30());
	                	this.Avatar = tAvatar.getAvatarURL30();
	                	bindInfoForLogin.setAvatar(Avatar);
	                	logger.info("性别：{}", userInfoBean.getGender());
	                	this.gender = userInfoBean.getGender();
	                    boolean bindFlag = tUnionLoginUtil.checkBind(openID,"Tencent");
	                 // 写入会员登录Cookie
	 	       			Cookie loginMemberUsernameCookie;
	 	       		    Cookie loginFlagCookie;
	 	       		    String userName = userInfoBean.getNickname();
	 	       		    if (bindFlag) {
		 	       		    Member member = new Member();
	 	                	BindInfoForLogin bindInfoForLogin = bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
	 	                	if (!"1".equals(bindInfoForLogin.getRegisterType()) && StringUtil.isNotEmpty(bindInfoForLogin.getKxbUserEmail())) {
	 	                		userName = bindInfoForLogin.getKxbUserEmail();
	 	                	} else if (StringUtil.isNotEmpty(bindInfoForLogin.getKxbUserPhone())) {
	 	                		userName = bindInfoForLogin.getKxbUserPhone();
	 	                	}
	 	                	try {
		 	                	member = bindInfoForLogin.getmMember();
		 	                	if (member != null && StringUtil.isNotEmpty(member.getUsername())) {
		 	                		userName = member.getUsername();
		 	                	}
	 	                	
	 	                	}catch(Exception e) {
	 	                		logger.error("第三方QQ登陆取会员信息错误！BindInfoForLogin的id:"
										+bindInfoForLogin.getId() + e.getMessage(), e);
	 	                	}
	 	       		    }
	 	       			try {
	 	       				loginMemberUsernameCookie = new Cookie(
	 	       						Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder
	 	       								.encode(userName, "UTF-8"));
	 	       				loginMemberUsernameCookie.setPath(getRequest().getContextPath()+ "/");
	 	       			    tResponse.addCookie(loginMemberUsernameCookie);
	 	       				// FrontServerContext
	 	       			setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME,"tencent");
	 	       			loginFlagCookie = new Cookie(Member.LOGIN_MEMBER_ID_SESSION_NAME, URLEncoder.encode("tencent", "UTF-8"));
	 	       		    loginFlagCookie.setPath(getRequest().getContextPath()+ "/");
	 	       		    tResponse.addCookie(loginFlagCookie);
	 	       				// 获取服务器应用地址
	 	       				GetDBdata db = new GetDBdata();
	 	       				String[] temp = { "FrontServerContext" };
	 	       				String url = db.getOneValue(
	 	       						"select value from zdconfig where type=?", temp);
	 	       				if (url.endsWith("/")) {
	 	       					loginMemberUsernameCookie.setPath(url);
	 	       				    loginFlagCookie.setPath(url);
	 	       				} else {
	 	       					loginMemberUsernameCookie.setPath(url + "/");
	 	       				    loginFlagCookie.setPath(url + "/");
	 	       				}
	 	       				loginMemberUsernameCookie.setMaxAge(86400);
	 	       			    tResponse.addCookie(loginMemberUsernameCookie);
	 	       			    loginFlagCookie.setMaxAge(86400);//设置cookie时长为1天
 	       			        tResponse.addCookie(loginFlagCookie);

	 	       			} catch (Exception e) {
							logger.error(e.getMessage(), e);
	 	       			}
	 	                if(bindFlag){
	 	                	/*Member member = new Member();
	 	                	BindInfoForLogin bindInfoForLogin = bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
	 	                	member = bindInfoForLogin.getmMember();
	 	                   // 写入会员登录Session
	 	       			   setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, member.getId());*/
	 	                	String bindID = bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID).getId();
	 	                	setSession("loginBindId",bindID);
	 	                	Cookie loginBindCookie = new Cookie("loginBindId", URLEncoder.encode(bindID, "UTF-8"));
	 	                	loginBindCookie.setMaxAge(30 * 100000);
 	       			        tResponse.addCookie(loginBindCookie);
	 	       		    tResponse.sendRedirect(loginBackURL);
	 	                }else{
	 	                	String bindID = "";
	 	                	if(bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID)!=null){
	 	                		bindInfoForLogin =  bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
	 	                		bindID = bindInfoForLogin.getId();
	 	                		
	 	                	}
	 	                	
	 	                	if("".equals(bindID)){
	 	                		otherloginUseMailAndPhone();
	 	                		bindInfoForLoginService.save(bindInfoForLogin);
	 	                		bindInfoForLogin =  bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
	 	                		bindID = bindInfoForLogin.getId(); 
	 	                		
	 	                	}
	 	                	setSession("loginBindId",bindID);
	 	                	Cookie loginBindCookie = new Cookie("loginBindId", URLEncoder.encode(bindID, "UTF-8"));
	 	                	loginBindCookie.setMaxAge(30 * 100000);
 	       			        tResponse.addCookie(loginBindCookie);
	 	                	returnURL =  "register";
	 	                	//开心保第三方注册页
	 	                }
	                } else {
                    logger.warn("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
	                }
	                logger.info("end -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息 ---------------------------- end");

	            }
	        } catch (QQConnectException e) {
	        	tResponse.sendRedirect(Config.getFrontServerContextPath());
	        	logger.error(" 获取QQ用户信息失败！ " + e.getMessage(), e);
	        	
	        }
		
	    
		
		return null;
	}
	/**
	 * 新浪聯合登錄
	 * @param tRequest
	 * @param tResponse
	 * @return
	 * @throws Exception
	 */
	public String dealSina(HttpServletRequest tRequest,HttpServletResponse tResponse) throws Exception{

		weibo4j.Oauth oauth = new weibo4j.Oauth();
		String sinoCode = tRequest.getParameter("code");
		weibo4j.http.AccessToken tAccessToken=null;
		try{
			tAccessToken = oauth.getAccessTokenByCode(sinoCode);
			if(tAccessToken!=null){
				String uid = tAccessToken.getUid();
				openID = uid;
				bindInfoForLogin.setOpenID(openID);
				String access_token = tAccessToken.getAccessToken();
				Users um = new Users();
				um.client.setToken(access_token);
				User user = um.showUserById(uid);
				if(user!=null){
					String tAvatar = user.getavatarLarge();
					this.comName="新浪微博";
					bindInfoForLogin.setComName(comName);
		        	logger.info("昵称：{}", user.getName());
		        	this.nickName = user.getName();
		        	bindInfoForLogin.setUserNickName(nickName);
					logger.info("头像：{}", tAvatar);
		        	this.Avatar = tAvatar;
		        	bindInfoForLogin.setAvatar(Avatar);
		        	logger.info("性别：{}", user.getGender());
		        	this.gender = user.getGender();
		            boolean bindFlag = tUnionLoginUtil.checkBind(openID,"Sina");
		            // 写入会员登录Cookie
 	       			Cookie loginMemberUsernameCookie;
 	       		    Cookie loginFlagCookie;
 	       		    String userName = user.getName();
	       		    if (bindFlag) {
	       		    	Member member = new Member();
	                	BindInfoForLogin bindInfoForLogin = bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
	                	if (!"1".equals(bindInfoForLogin.getRegisterType()) && StringUtil.isNotEmpty(bindInfoForLogin.getKxbUserEmail())) {
	                		userName = bindInfoForLogin.getKxbUserEmail();
	                	} else if (StringUtil.isNotEmpty(bindInfoForLogin.getKxbUserPhone())) {
	                		userName = bindInfoForLogin.getKxbUserPhone();
	                	}
	                	try {
	 	                	member = bindInfoForLogin.getmMember();
	 	                	if (member != null && StringUtil.isNotEmpty(member.getUsername())) {
	 	                		userName = member.getUsername();
	 	                	}
	                	}catch(Exception e) {
	                		logger.error("第三方新浪微博登陆取会员信息错误！BindInfoForLogin的id:"+bindInfoForLogin.getId() + e.getMessage(), e);
	                	}
	       		    }
 	       			try {
 	       				loginMemberUsernameCookie = new Cookie(
 	       						Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder
 	       								.encode(userName, "UTF-8"));
 	       				loginMemberUsernameCookie.setPath(getRequest().getContextPath()+ "/");
 	       			    tResponse.addCookie(loginMemberUsernameCookie);
 	       				// FrontServerContext
 	       			setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME,"tencent");
 	       			loginFlagCookie = new Cookie(Member.LOGIN_MEMBER_ID_SESSION_NAME, URLEncoder.encode("tencent", "UTF-8"));
 	       		    loginFlagCookie.setPath(getRequest().getContextPath()+ "/");
 	       		    tResponse.addCookie(loginFlagCookie);
 	       				// 获取服务器应用地址
 	       				GetDBdata db = new GetDBdata();
 	       				String[] temp = { "FrontServerContext" };
 	       				String url = db.getOneValue(
 	       						"select value from zdconfig where type=?", temp);
 	       				if (url.endsWith("/")) {
 	       					loginMemberUsernameCookie.setPath(url);
 	       				    loginFlagCookie.setPath(url);
 	       				} else {
 	       					loginMemberUsernameCookie.setPath(url + "/");
 	       				    loginFlagCookie.setPath(url + "/");
 	       				}
 	       				loginMemberUsernameCookie.setMaxAge(86400);
 	       			    tResponse.addCookie(loginMemberUsernameCookie);
 	       			    loginFlagCookie.setMaxAge(86400);//设置cookie时长为1天
	       			        tResponse.addCookie(loginFlagCookie);

 	       			} catch (Exception e) {
						logger.error(e.getMessage(), e);
 	       			}
	 	       		if(bindFlag){
		                	/*Member member = new Member();
		                	BindInfoForLogin bindInfoForLogin = bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
		                	member = bindInfoForLogin.getmMember();
		                   // 写入会员登录Session
		       			   setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, member.getId());*/
		                	String bindID = bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID).getId();
		                	setSession("loginBindId",bindID);
		                	Cookie loginBindCookie = new Cookie("loginBindId", URLEncoder.encode(bindID, "UTF-8"));
		                	loginBindCookie.setMaxAge(30 * 100000);
	    			        tResponse.addCookie(loginBindCookie);
		       		    tResponse.sendRedirect(loginBackURL);
		                }else{
		                	String bindID = "";
		                	if(bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID)!=null){
		                		bindInfoForLogin =  bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
	 	                		bindID = bindInfoForLogin.getId();
	 	                		
		                	}
		                	
		                	if("".equals(bindID)){
		                		otherloginUseMailAndPhone();
		                		bindInfoForLoginService.save(bindInfoForLogin);
		                		bindInfoForLogin =  bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
	 	                		bindID = bindInfoForLogin.getId();
	 	                		
		                	}
		                	setSession("loginBindId",bindID);
		                	Cookie loginBindCookie = new Cookie("loginBindId", URLEncoder.encode(bindID, "UTF-8"));
		                	loginBindCookie.setMaxAge(30 * 100000);
	    			        tResponse.addCookie(loginBindCookie);
		                	returnURL =  "register";
		                	//开心保第三方注册页
		                }
		         } else {
		                logger.warn("很抱歉，我们没能正确获取到您的信息");
		         }
		         logger.info("end -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息 ---------------------------- end");
			}else{
				tResponse.sendRedirect(loginBackURL);
			}
			
		}catch(Exception e){
			if(loginBackURL==null || "".equals(loginBackURL)){
				loginBackURL = Config.getFrontServerContextPath();
			}
			tResponse.sendRedirect(loginBackURL);
		}
		
		
		return null;
	}
	
	/**
	 * 支付宝快捷登錄
	 * @param tRequest
	 * @param tResponse
	 * @return
	 * @throws Exception
	 */
	public String dealAlipay(HttpServletRequest tRequest,HttpServletResponse tResponse) throws Exception{
		
		try{

			
			//获取支付宝GET过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = tRequest.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//本地不要注释掉这段代码
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
				params.put(name, valueStr);
			}
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//支付宝用户号

			String user_id = new String(tRequest.getParameter("user_id").getBytes("ISO-8859-1"),"UTF-8");
			String real_name = tRequest.getParameter("real_name");//new String(tRequest.getParameter("real_name").getBytes("ISO-8859-1"),"UTF-8");
			openID = user_id;
			nickName=real_name;
			nickName = StringUtil.deleteEmoji(nickName);
			this.comName="支付宝";
			bindInfoForLogin.setComName(comName);
			bindInfoForLogin.setOpenID(openID);
			bindInfoForLogin.setUserNickName(nickName);
			//授权令牌
			String token = new String(tRequest.getParameter("token").getBytes("ISO-8859-1"),"UTF-8");

			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			
			//计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			if(verify_result){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码
				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				
				//判断是否在商户网站中已经做过了这次通知返回的处理
					//如果没有做过处理，那么执行商户的业务程序
					//如果有做过处理，那么不执行商户的业务程序
				
				//该页面可做页面美工编辑
				logger.info("验证成功");

				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

				//////////////////////////////////////////////////////////////////////////////////////////
				  boolean bindFlag = tUnionLoginUtil.checkBind(openID,"Alipay");
				// 写入会员登录Cookie
	    			Cookie loginMemberUsernameCookie;
	    		    Cookie loginFlagCookie;
 	       		    String userName = real_name;
	       		    if (bindFlag) {
	       		    	Member member = new Member();
	                	BindInfoForLogin bindInfoForLogin = bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
	                	if (!"1".equals(bindInfoForLogin.getRegisterType()) && StringUtil.isNotEmpty(bindInfoForLogin.getKxbUserEmail())) {
	                		userName = bindInfoForLogin.getKxbUserEmail();
	                	} else if (StringUtil.isNotEmpty(bindInfoForLogin.getKxbUserPhone())) {
	                		userName = bindInfoForLogin.getKxbUserPhone();
	                	}
	                	try {
	 	                	member = bindInfoForLogin.getmMember();
	 	                	if (member != null && StringUtil.isNotEmpty(member.getUsername())) {
	 	                		userName = member.getUsername();
	 	                	}
	                	
	                	}catch(Exception e) {
	                		logger.error("第三方支付宝登陆取会员信息错误！BindInfoForLogin的id:"+bindInfoForLogin.getId() + e.getMessage(), e);
	                	}
	       		    }
	    			try {
	    				loginMemberUsernameCookie = new Cookie(
	    						Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder
	    								.encode(userName, "UTF-8"));
	    				loginMemberUsernameCookie.setPath(getRequest().getContextPath()+ "/");
	    			    tResponse.addCookie(loginMemberUsernameCookie);
	    				// FrontServerContext
	    			setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME,"tencent");
	    			loginFlagCookie = new Cookie(Member.LOGIN_MEMBER_ID_SESSION_NAME, URLEncoder.encode("tencent", "UTF-8"));
	    		    loginFlagCookie.setPath(getRequest().getContextPath()+ "/");
	    		    tResponse.addCookie(loginFlagCookie);
	    				// 获取服务器应用地址
	    				GetDBdata db = new GetDBdata();
	    				String[] temp = { "FrontServerContext" };
	    				String url = db.getOneValue(
	    						"select value from zdconfig where type=?", temp);
	    				if (url.endsWith("/")) {
	    					loginMemberUsernameCookie.setPath(url);
	    				    loginFlagCookie.setPath(url);
	    				} else {
	    					loginMemberUsernameCookie.setPath(url + "/");
	    				    loginFlagCookie.setPath(url + "/");
	    				}
	    				loginMemberUsernameCookie.setMaxAge(86400);
	    			    tResponse.addCookie(loginMemberUsernameCookie);
	    			    loginFlagCookie.setMaxAge(86400);//设置cookie时长为1天
	   			        tResponse.addCookie(loginFlagCookie);

	    			} catch (Exception e) {
						logger.error(e.getMessage(), e);
	    			}
	    			
		       		if(bindFlag){
	                	/*Member member = new Member();
	                	BindInfoForLogin bindInfoForLogin = bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
	                	member = bindInfoForLogin.getmMember();
	                   // 写入会员登录Session
	       			   setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, member.getId());*/
	                	String bindID = bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID).getId();
	                	setSession("loginBindId",bindID);
	                	Cookie loginBindCookie = new Cookie("loginBindId", URLEncoder.encode(bindID, "UTF-8"));
	                	loginBindCookie.setMaxAge(30 * 100000);
				        tResponse.addCookie(loginBindCookie);
	       		    tResponse.sendRedirect(loginBackURL);
	                }else{
	                	String bindID = "";
	                	if(bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID)!=null){
	                		bindInfoForLogin =  bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
		                	bindID = bindInfoForLogin.getId();
	                	}
	                	
	                	if("".equals(bindID)){
	                		otherloginUseMailAndPhone();
	                		bindInfoForLoginService.save(bindInfoForLogin);
	                		bindInfoForLogin =  bindInfoForLoginService.getBindInfoForLoginByOpenID(this.openID);
		                	bindID = bindInfoForLogin.getId();
	                	}
	                	setSession("loginBindId",bindID);
	                	Cookie loginBindCookie = new Cookie("loginBindId", URLEncoder.encode(bindID, "UTF-8"));
	                	loginBindCookie.setMaxAge(30 * 100000);
				        tResponse.addCookie(loginBindCookie);
	                	returnURL =  "register";
	                	//开心保第三方注册页
	                }
			}else{
				returnURL =  "register";
				//该页面可做页面美工编辑
				logger.error("验证失败");
			}
		
		}catch(Exception e){
			tResponse.sendRedirect(Config.getFrontServerContextPath());
		}
		
		return null;
		
		
	}
	
	/**
	 * 联合登陆---电话号和邮箱不能为null
	 * 
	 * @return
	 * @throws Exception
	 */
	public void otherloginUseMailAndPhone(){
		if(bindInfoForLogin != null && StringUtil.isNotEmpty(bindInfoForLogin.getOpenID())){
			if(StringUtil.isEmpty(bindInfoForLogin.getKxbUserEmail())){
				bindInfoForLogin.setKxbUserEmail("");
			}
			if(StringUtil.isEmpty(bindInfoForLogin.getKxbUserPhone())){
				bindInfoForLogin.setKxbUserPhone("");
			}
			if(StringUtil.isEmpty(bindInfoForLogin.getUserEmail())){
				bindInfoForLogin.setUserEmail("");
			}
			if(StringUtil.isEmpty(bindInfoForLogin.getUserPhone())){
				bindInfoForLogin.setUserPhone("");
			}
		}
	
	}
	
	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		Avatar = avatar;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLoginBackURL() {
		return loginBackURL;
	}

	public void setLoginBackURL(String loginBackURL) {
		this.loginBackURL = loginBackURL;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	public BindInfoForLogin getBindInfoForLogin() {
		return bindInfoForLogin;
	}

	public void setBindInfoForLogin(BindInfoForLogin bindInfoForLogin) {
		this.bindInfoForLogin = bindInfoForLogin;
	}


	
	
}
