package cn.com.sinosoft.action.wap;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.json.JSONObject;

import cn.com.sinosoft.action.shop.BaseWapShopAction;
import cn.com.sinosoft.bean.SystemConfig;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.DealDataService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.MemberRankService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.OccupationService;
import cn.com.sinosoft.service.SDRelationAppntService;
import cn.com.sinosoft.service.SDRelationRecognizeeService;
import cn.com.sinosoft.service.WxbindService;
import cn.com.sinosoft.util.WapMemberUtil;


/**
 * wap站会员信息接口类 请求头request中增加用户名/秘密、版本号信息
 */
@ParentPackage("wap") 
public class WapMemberAction extends BaseWapShopAction {
	
	private static final long serialVersionUID = 5109415857980705568L;
	
	@Resource
	private MemberService memberService;
	@Resource
	private MemberRankService memberRankService;
	@Resource
	private WxbindService mWxbindService;
	@Resource
	private BindInfoForLoginService mBindInfoForLoginService;
	@Resource
	private DealDataService mDealDataService;
	@Resource
	private SDRelationAppntService sdRelationAppntService;
	@Resource
	private SDRelationRecognizeeService sdRelationRecognizeeService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource 
	private OccupationService occupationService;
	@Resource
	private AreaService areaService;
	
	private SystemConfig sc = getSystemConfig();

	/**
	 * 
	 * wap站会员登陆接口
	 * @return
	 */
	public String login() {
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapLogin(PARAMETERS,memberService,getRequest()));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	} 
	/**
	 * wap站会员信息接口
	 * @return
	 */
	public String getUserInfo(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapUserInfo(PARAMETERS,memberService));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站会员注册接口
	 * @return
	 */
	public String register(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapRegister(PARAMETERS,memberService,memberRankService,getRequest(),sc));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站修改密码接口
	 * @return
	 */
	public String changePassword(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapChangePassword(PARAMETERS,memberService));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站会员信息修改接口
	 * @return
	 */
	public String updateUserBaseInfo(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapUpdateUserBaseInfo(PARAMETERS,memberService,getRequest()));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站找回密码接口
	 * @return
	 */
	public String fetchPassword(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapFetchPassword(PARAMETERS,memberService,getRequest()));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站邮件找回密码接口
	 * @return
	 */
	public String doRetrieveByEmail(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapDoRetrieveByEmail(PARAMETERS,memberService,getRequest()));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站重新发送邮件链接接口
	 * @return
	 */
	public String reSendRetrieveMail(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapReSendRetrieveMail(PARAMETERS,memberService,getRequest()));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站手机找回密码接口
	 * @return
	 */
	public String mobileRetrievePassword(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapMobileRetrievePassword(PARAMETERS,memberService,getRequest()));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站重新发送验证码接口
	 * @return
	 */
	public String reSendSVCToPhone(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapReSendSVCToPhone(PARAMETERS,memberService,getRequest()));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站根据memberId获取会员信息接口，用于wap站会员微信绑定
	 * @return
	 */
	public String getUserInfoById(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapUserInfoById(PARAMETERS,memberService));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * 微信绑定接口
	 * @return
	 */
	public String weiXinBind(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapWeiXinBind(PARAMETERS,memberService,mWxbindService));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站根据微信openid获取会员信息接口，用于wap站会员微信绑定
	 * @return
	 */
	public String getUserInfoByOpenID(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapUserInfoByOpenID(PARAMETERS,memberService,mWxbindService));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站根据微信openid获取会员信息接口，用于判断wap站会员是否已微信绑定
	 * @return
	 */
	public String checkLogin(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.wapCheckLogin(PARAMETERS,memberService,mWxbindService));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * 优惠劵激活接口
	* @Title: couponVerify 
	 */
	public String couponVerify(){
		
		JSONObject jsonObject = new JSONObject(WapMemberUtil.couponVerify(PARAMETERS, getServiceMap(), USER));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * 会员积分接口
	* @Title: memberCoupon 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author XXX
	 */
	public String memberPoint(){
		
		JSONObject jsonObject = new JSONObject(WapMemberUtil.memberPointInfo(PARAMETERS, getServiceMap(), USER));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * 会员优惠劵接口
	* @Title: memberCoupon 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author XXX
	 */
	public String memberCoupon(){
		
		JSONObject jsonObject = new JSONObject(WapMemberUtil.memberCouponInfo(PARAMETERS, getServiceMap(), USER));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * 联合登录（QQ、新浪微博、支付宝）判断是否已绑定接口
	* @Title: memberCoupon 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author XXX
	 */
	public String unitBind(){
		
		JSONObject jsonObject = new JSONObject(WapMemberUtil.unitBind(PARAMETERS, getServiceMap(), USER));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * 联合登录（QQ、新浪微博、支付宝）绑定接口
	* @Title: memberCoupon 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author XXX
	 */
	public String unitBindingInfo(){
		
		JSONObject jsonObject = new JSONObject(WapMemberUtil.unitBindingInfo(PARAMETERS, getServiceMap(), USER));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * 常用投、被保人信息维护（增、删、改）
	 * @return
	 */
	public String dealRelaInfo(){
		
		JSONObject jsonObject = new JSONObject(WapMemberUtil.dealRelaInfo(PARAMETERS, getServiceMap(), USER));
		return ajax(jsonObject.toString(), "text/html");
		
	}
	/**
	 * 常用投、被保人信息查询
	 * @return
	 */
	public String queryRelaInfo(){
		
		JSONObject jsonObject = new JSONObject(WapMemberUtil.queryRelaInfo(PARAMETERS, getServiceMap(), USER));
		return ajax(jsonObject.toString(), "text/html");
		
	}
	
	/**
	 * wap站积分规则
	 * add by wangej 20150515
	 * @return
	 */
	public String getRolePionts(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.getRolePionts(PARAMETERS, getServiceMap(), USER));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站 会员推荐积分信息
	 * add by wangej 20150522
	 * @return
	 */
	public String getMemRecomInfo(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.getMemRecomInfo(PARAMETERS, getServiceMap(), USER));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站会员等级信息
	 * add by wangej 20150617
	 * @return
	 */
	public String getMemberGradeInfo(){
		JSONObject jsonObject = new JSONObject(WapMemberUtil.getMemberGradeInfo(PARAMETERS, getServiceMap(), USER));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * 封装入参
	 * @return
	 */
	public Map<String, Object> getServiceMap() {
		Map<String, Object> mServiceMap = new HashMap<String, Object>();
		mServiceMap.put("MemberService", memberService);
		mServiceMap.put("BindInfoForLoginService", mBindInfoForLoginService);
		mServiceMap.put("DealDataService", mDealDataService);
		mServiceMap.put("SDRelationAppntService", sdRelationAppntService);
		mServiceMap.put("SDRelationRecognizeeService", sdRelationRecognizeeService);
		mServiceMap.put("DictionaryService", dictionaryService);
		mServiceMap.put("OccupationService", occupationService);
		mServiceMap.put("AreaService", areaService);
		
		return mServiceMap;
	}
}
