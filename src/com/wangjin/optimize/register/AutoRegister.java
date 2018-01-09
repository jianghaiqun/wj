package com.wangjin.optimize.register;

import cn.com.sinosoft.action.shop.BaseShopAction;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.TradeInformation;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDInformationSet;
import com.sinosoft.schema.ZDConfigSchema;
import com.sinosoft.schema.memberSchema;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@ParentPackage("shop")
public class AutoRegister extends BaseShopAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2373373524860250132L;
	
	public Map<String, Object> userRegistedCheck(SDOrder sdorder,String ordId,TradeInformation tradeInformation) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 操作手机，操作邮箱
			String ss = "select b.applicantMail,b.applicantMobile from SDInformation a , SDInformationAppnt b where a.informationSn=b.informationSn and a.orderSn=?";
			QueryBuilder qb = new QueryBuilder(ss, ordId);
			DataTable dt = qb.executeDataTable();
			String email = dt.getString(0, "applicantmail");
			String mobileNo = dt.getString(0, "applicantMobile");
			String Mobilesql = "select id from member where mobileNO=? ";
			String mailsql = "select id from member where email=?  ";

			String username = "";
			String memberid = "";
			boolean userisExist = false;
			
			// wap站优先使用手机号注册
			if (ordId.startsWith("wp")) {
				if (StringUtil.isNotEmpty(mobileNo)) {
					qb = new QueryBuilder(Mobilesql, mobileNo);
					if (qb.executeOneValue() != null) {
						userisExist = true;
						username = mobileNo;
						memberid = String.valueOf(qb.executeOneValue());
					}
				}
				
				if (StringUtil.isEmpty(username) && StringUtil.isNotEmpty(email)) {
					qb = new QueryBuilder(mailsql, email);
					if (qb.executeOneValue() != null) {
						if (!userisExist) {
							username = email;
							memberid = String.valueOf(qb.executeOneValue());
						}
						userisExist = true;
					}
				}
				
			} else {
				if (StringUtil.isNotEmpty(mobileNo)) {
					qb = new QueryBuilder(Mobilesql, mobileNo);
					if (qb.executeOneValue() != null) {
						if (!userisExist) {
							username = mobileNo;
							memberid = String.valueOf(qb.executeOneValue());
						}
						userisExist = true;
					}
				}
				
				if (StringUtil.isEmpty(username) && StringUtil.isNotEmpty(email)) {
					qb = new QueryBuilder(mailsql, email);
					if (qb.executeOneValue() != null) {
						userisExist = true;
						username = email;
						memberid = String.valueOf(qb.executeOneValue());
					}
				}
			}
			
			if (!userisExist) {
				setSession(ordId, email);
				Map<String, Object> map = userRegister(email, mobileNo, ordId,sdorder,tradeInformation,getRequest());
				result.putAll(map);
			} else {
				userRegisted(email, username,sdorder,tradeInformation, mobileNo,getRequest());
				changeDateIp(memberid, getRequest());
				orderBelongToMemberID(ordId, memberid, "");
			}
			result.put("userisExist", userisExist);
			
		} catch (Exception e) {
			logger.error("程序出现异常:" + e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 淘宝专用自动注册
	 * @param sdorder
	 * @param ordId
	 * @param tradeInformation
	 * @param request
	 */
	public Map<String, Object> userRegistedCheck(SDOrder sdorder,String ordId,TradeInformation tradeInformation, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 操作手机，操作邮箱
			String mobileNo = "";
			String email = "";
			String ss = "select b.applicantMail,b.applicantMobile from SDInformation a , SDInformationAppnt b where a.informationSn=b.informationSn and a.orderSn=?";
			QueryBuilder qb = new QueryBuilder(ss, ordId);
			DataTable dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				mobileNo = dt.getString(0, "applicantMobile");
				email = dt.getString(0, "applicantMail");
			}
			
			String Mobilesql = "select id from member where mobileNO=? limit 1";
			String mailsql = "select id from member where email=?   limit 1";

			String username = "";
			String memberid = "";
			boolean userisExist = false;
			
			synchronized(this) { 
				if (StringUtil.isNotEmpty(mobileNo)) {
					qb = new QueryBuilder(Mobilesql, mobileNo);
					if (qb.executeOneValue() != null) {
						userisExist = true;
						username = mobileNo;
						memberid = String.valueOf(qb.executeOneValue());
					}
				}
				
				if (StringUtil.isEmpty(username) && StringUtil.isNotEmpty(email)) {
					qb = new QueryBuilder(mailsql, email);
					if (qb.executeOneValue() != null) {
						if (!userisExist) {
							username = email;
							memberid = String.valueOf(qb.executeOneValue());
						}
						userisExist = true;
					}
				}
			
			}
			if (!userisExist) {
				userRegister(email, mobileNo, ordId, sdorder, tradeInformation, request);
			} else {
				userRegisted(email, username, sdorder, tradeInformation,
						mobileNo, request);
				changeDateIp(memberid, request);
				orderBelongToMemberID(ordId, memberid, "tb");

			}
			result.put("sendMessage", true);

		} catch (Exception e) {
			logger.error("程序出现异常:" + e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 订单归属
	 * @param orderID
	 * @param memberID
	 */
    public void orderBelongToMemberID(String orderID, String memberID, String channelsn) {

        Transaction trans = new Transaction();

        trans.add(new QueryBuilder("update sdorders set memberid=? where ordersn=?" ,memberID,orderID));

        if (!trans.commit()) {
            logger.error( "orderSn：{}归属" + "MemberID：{}没有成功！", orderID, memberID);
        }
        // 支付成功后不自动登陆
//        if (!"tb".equals(channelsn)) {
//        	dealMemberInfo(memberID);
//        }
     }

	/**
	 * 更新登陆反查到会员后，更新modifydate和登陆loingip
	 * @param orderID
	 * @param memberID
	 */
    public void changeDateIp(String memberID,HttpServletRequest request) {

        Transaction trans = new Transaction();
        trans.add(new QueryBuilder("update member set modifydate=? where id=?" ,new Date(),memberID));
        trans.add(new QueryBuilder("update member set loginDate=? where id=?" ,new Date(),memberID));
        trans.add(new QueryBuilder("update member set loginIp=? where id=?" ,request.getRemoteAddr(),memberID));

        if (!trans.commit()) {
            logger.error("memberID：{}登陆没有成功！", memberID);
        }
        
     }
	
    //处理会员未登录购买，会员信息显示错误问题 bug0000995
    private void dealMemberInfo(String memberid){
    	Cookie cookie1 = new Cookie(Member.LOGIN_MEMBER_ID_SESSION_NAME,
    			memberid);
		cookie1.setPath(getRequest().getContextPath() + "/");
		cookie1.setMaxAge(24 * 3600);
		getResponse().addCookie(cookie1);
		setSession(Member.LOGIN_MEMBER_ID_SESSION_NAME, memberid);
		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(memberid);
		if(tmemberSchema.fill()){
			// 写入会员登录Cookie
			String loginName = "";
			if(StringUtil.isEmpty(loginName)){
				loginName = tmemberSchema.getusername();
			}
			if(StringUtil.isEmpty(loginName)&&StringUtil.isNotEmpty(tmemberSchema.getmobileNO()) && 
					("Y".equals(tmemberSchema.getisMobileNOBinding()) || "1".endsWith(tmemberSchema.getregisterType()))){
				loginName = tmemberSchema.getmobileNO();
			}
			if(StringUtil.isNotEmpty(tmemberSchema.getemail()) && 
					("Y".equals(tmemberSchema.getisEmailBinding()) || "0".endsWith(tmemberSchema.getregisterType()))){
				loginName = tmemberSchema.getemail();
			}
			Cookie loginMemberUsernameCookie;
			try {
				loginMemberUsernameCookie = new Cookie(
						Member.LOGIN_MEMBER_USERNAME_COOKIE_NAME, URLEncoder.encode(
								loginName.toLowerCase(), "UTF-8"));
				loginMemberUsernameCookie.setPath(getRequest().getContextPath() + "/");
				getResponse().addCookie(loginMemberUsernameCookie);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
			}
		}
    }

	private String getDefaultMemberRank() {
		String defaultMemberRank_id = "";
		String sql1 = "select * from MemberRank as memberRank where memberRank.isDefault = ?";
		String sql2 = "select * from MemberRank as memberRank order by memberRank.createDate asc";
		QueryBuilder qb = new QueryBuilder(sql1, "0");
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			defaultMemberRank_id = dt.getString(0, "id");
		} else {
			qb = new QueryBuilder(sql2);
			dt = qb.executeDataTable();
			defaultMemberRank_id = dt.getString(0, "id");
		}

		return defaultMemberRank_id;
	}

	private Map<String, Object> userRegister(String email, String mobileNo, String orderID,SDOrder sdorder,TradeInformation tradeInformation,HttpServletRequest request)
			throws UnsupportedEncodingException {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Transaction trans = new Transaction();
		memberSchema members = new memberSchema();
		double a = (2 / 20.0) * 100;
		DecimalFormat myformat = new DecimalFormat("#####0");
		String fullDegree = myformat.format(a) + "%";
		long menberID = NoUtil.getMaxID("MemberID");
		members.setid(menberID + "");
		members.setcreateDate(new Date());
		members.setmodifyDate(new Date());
		members.setfullDegree(fullDegree);
		
		// 频道渠道出单产品自动注册用户初始密码
		String productId = new QueryBuilder("SELECT productId FROM SDInformation WHERE orderSn=?", orderID).executeString();
		String password = "207101082".indexOf(productId) > -1 ? "123456" : randomPW();
		members.setpassword(DigestUtils.md5Hex(password));
		
		result.put("password", password);
		members.setVIPFrom("0");
		members.setsafeQuestion(null);
		members.setsafeAnswer(null);
		//CMS报表设定RegisterType的值为2时认为是自动注册。
		members.setregisterType("2");
		members.setmemberRank_id(getDefaultMemberRank());
//		if (StringUtil.isEmpty(email)) {
			members.setisEmailBinding("N");
//		} else {
//			members.setisEmailBinding("Y");
//		}
//		members.setisMobileNOBinding("Y");
		members.setisMobileNOBinding("N");
		members.setpoint(0);
		members.setdeposit(new BigDecimal("0"));
		members.setisAccountEnabled("Y");
		members.setisAccountLocked("N");
		members.setloginFailureCount(0);
		members.setpasswordRecoverKey(null);
		members.setlockedDate(null);
		members.setregisterIp(request.getRemoteAddr());
		members.setloginIp(request.getRemoteAddr());
		members.setusedPoint(0);
		members.setcurrentValidatePoint(0);
		members.setexpiricalValue(0);
		members.setemail(email);
		members.setmobileNO(mobileNo);
		// req20160331701-未注册用户匹配账号优化
		if (StringUtil.isNotEmpty(mobileNo)) {
			members.setusername(mobileNo);
		} else {
			members.setusername(email);
		}
		result.put("username", members.getusername());
		
		members.setverifyEmailSendDate(new Date());
		if (StringUtil.isNotEmpty(sdorder.getChannelsn())) {
			members.setFromWap(sdorder.getChannelsn());
		} else {
			members.setFromWap("wj");
		}
		
		members.setloginDateAfterUngrade(new Date());
		members.setpreLoginPoints(0);
		members.setgrade("K0");
		members.setvipFlag("N");
		members.setpreLoginPoints(0);
		trans.add(members, Transaction.INSERT);

		if (trans.commit()) {
			String channelsn = "";
			try {
				SDInformationSchema sdInformationSchema = new SDInformationSchema();
				SDInformationSet sdinformationSet = new SDInformationSet();
				sdinformationSet = sdInformationSchema.query(new QueryBuilder("where OrderSn = ?",sdorder.getOrderSn()));
				if(sdinformationSet!=null && sdinformationSet.size()>0){
					sdInformationSchema= sdinformationSet.get(0);
				}
				Map<String, Object> map = new HashMap<String, Object>();
				// 淘宝自动注册 无邮箱有手机号时  发送短信
				if (StringUtil.isNotEmpty(sdorder.getChannelsn())
						&& sdorder.getChannelsn().startsWith("tb")
						&& StringUtil.isEmpty(email)
						&& StringUtil.isNotEmpty(mobileNo)) {
					channelsn = "tb";
					ActionUtil.sendSms("wj00117", mobileNo, password);
				} else {
					map.put("orderSn", sdorder.getOrderSn());
					map.put("password", password);
					map.put("username", email);
					ActionUtil.sendMessage("wj00051", map);
					ActionUtil.dealAction("wj00051", map, request);
				}
				
			} catch (Exception e) {
				logger.error("用户名：" + email + "发送自动注册用户信息邮件/短信没有成功！" + e.getMessage(), e);
			}
			orderBelongToMemberID(orderID, String.valueOf(menberID), channelsn);
			sdorder.setMemberId(String.valueOf(menberID));
		} else {
			logger.error("用户名：{}自动注册用户信息没有注册成功！", email);
		}
		return result;
	}

	private void userRegisted(String email, String username,SDOrder sdorder,TradeInformation tradeInformation, String mobileNo, HttpServletRequest request) {
		try {
			SDInformationSchema sdInformationSchema = new SDInformationSchema();
			SDInformationSet sdinformationSet = new SDInformationSet();
			sdinformationSet = sdInformationSchema.query(new QueryBuilder("where OrderSn = ?",sdorder.getOrderSn()));
			if(sdinformationSet!=null && sdinformationSet.size()>0){
				sdInformationSchema= sdinformationSet.get(0);
			}
			ZDConfigSchema tZDConfigSchema = new ZDConfigSchema();
			tZDConfigSchema.setType("ServerContext");
			tZDConfigSchema.fill();
			Map<String, Object> map = new HashMap<String, Object>();
			
			// 淘宝自动注册 无邮箱有手机号时  发送短信
			if (StringUtil.isNotEmpty(sdorder.getChannelsn())
					&& sdorder.getChannelsn().startsWith("tb")
					&& StringUtil.isEmpty(email)
					&& StringUtil.isNotEmpty(mobileNo)) {
				ActionUtil.sendSms("wj00118", mobileNo, "");
			} else {
				// cps_58bb不发送短信和邮件
				if(StringUtil.isNotEmpty(Config.getValue("NoSendChannelsn")) && Config.getValue("NoSendChannelsn").indexOf(sdorder.getChannelsn()) == -1){
					request.setCharacterEncoding("UTF-8");
					map.put("orderSn", sdorder.getOrderSn());
					map.put("username", username);
					ActionUtil.sendMessage("wj00052", map);
					ActionUtil.dealAction("wj00052", map, request);
				}
			}
		} catch (Exception e) {
			logger.error("用户名：" + email + "发送该注册用户信息邮件/短信没有成功！" + e.getMessage(), e);
		}
	}

	private String randomPW() {
		Random r = new Random();
		int i = 0;
		while (i < 100000) {
			i = r.nextInt(1000000);
		}
		return String.valueOf(i);
	}
	
	/**
	 * 获取手机号对应的MemberId，若未注册，则返回null
	 * @param mobileNo
	 * @return
	 */
	public String getMobileMemberId(String mobileNo) {
		if (StringUtil.isNotEmpty(mobileNo)) {
			String sql = "SELECT * FROM member WHERE mobileNO = ? ORDER BY createDate LIMIT 0,1;";
			QueryBuilder qb = new QueryBuilder(sql, mobileNo);
			return qb.executeString();
		}
		return null;
	}
	
	/**
	 * 获取邮箱对应的MemberId，若未注册，则返回null
	 * @param mobileNo
	 * @return
	 */
	public String getEmailMemberId(String email) {
		if (StringUtil.isNotEmpty(email)) {
			String sql = "SELECT * FROM member WHERE email = ? ORDER BY createDate LIMIT 0,1;";
			QueryBuilder qb = new QueryBuilder(sql, email);
			return qb.executeString();
		}
		return null;
	}
	
	/**
	 * 为H5频道活动专用的注册方法，只使用手机号注册手机会员，不发送邮件或短信通知，初始密码为123456
	 * @param mobileNo
	 * @param sdorder
	 */
	public  Map<String, String> registForBrand(String email, String mobileNo, SDOrder sdorder) {
		Transaction trans = new Transaction();
		Map<String, String> result = new HashMap<String, String>();
		try {
			memberSchema members = new memberSchema();
			double a = (2 / 20.0) * 100;
			DecimalFormat myformat = new DecimalFormat("#####0");
			String fullDegree = myformat.format(a) + "%";
			long menberID = NoUtil.getMaxID("MemberID");
			members.setid(menberID + "");
			members.setcreateDate(new Date());
			members.setmodifyDate(new Date());
			members.setfullDegree(fullDegree);
			String password = randomPW();
			members.setpassword(DigestUtils.md5Hex(password));
			result.put("password", password);
			members.setVIPFrom("0");
			members.setsafeQuestion(null);
			members.setsafeAnswer(null);
			//CMS报表设定RegisterType的值为2时认为是自动注册。
			members.setregisterType("2");
			members.setmemberRank_id(getDefaultMemberRank());
			members.setisEmailBinding("N");
			members.setisMobileNOBinding("N");
			members.setpoint(0);
			members.setdeposit(new BigDecimal("0"));
			members.setisAccountEnabled("Y");
			members.setisAccountLocked("N");
			members.setloginFailureCount(0);
			members.setpasswordRecoverKey(null);
			members.setlockedDate(null);
			members.setregisterIp("");
			members.setloginIp("");
			members.setusedPoint(0);
			members.setcurrentValidatePoint(0);
			members.setexpiricalValue(0);
			members.setemail(email);
			members.setmobileNO(mobileNo);
			if (StringUtil.isNotEmpty(mobileNo)) {
				members.setusername(mobileNo);
			} else {
				members.setusername(email);
			}
			result.put("username", members.getusername());
			members.setverifyEmailSendDate(new Date());
			if (StringUtil.isNotEmpty(sdorder.getChannelsn())) {
				members.setFromWap(sdorder.getChannelsn());
			} else {
				members.setFromWap("wj");
			}
			members.setloginDateAfterUngrade(new Date());
			members.setpreLoginPoints(0);
			members.setgrade("K0");
			members.setvipFlag("N");
	 		if(StringUtil.isEmpty(members.getemail())){
	 			members.setemail("");
			}
			if(StringUtil.isEmpty(members.getmobileNO())){
				members.setmobileNO("");
			}
			if(StringUtil.isEmpty(members.getmBindInfoForLogin_id())){
				members.setmBindInfoForLogin_id("");
			}
			trans.add(members, Transaction.INSERT);

			if (!trans.commit()) {
				logger.error("邮箱：{}、手机：{} 自动注册用户信息没有注册成功！", email, mobileNo);
			} else {
				orderBelongToMemberID(sdorder.getOrderSn(), menberID + "", sdorder.getChannelsn());
			}
		} catch (Exception e) {
			logger.error("邮箱："+email+"、手机：" + mobileNo + " 自动注册用户信息没有注册成功！" + e.getMessage(), e);
		}
		return result;
	}
}
