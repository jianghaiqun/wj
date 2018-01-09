/**
 * Project Name:wj-code
 * File Name:RenewalInsuranceAction.java
 * Package Name:cn.com.sinosoft.action.shop
 * Date:2017年11月2日上午9:33:23
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.action.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.captcha.service.CaptchaService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.enums.RiskCompanyCodeEnum;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZhenAiRenewalSchema;

import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.RenewalInsuranceService;
import cn.com.sinosoft.service.RenewalService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.util.Constant;
import net.sf.json.JSONObject;

/**
 * ClassName:RenewalInsuranceAction <br/>
 * Function:TODO 续保. <br/>
 * Date:2017年11月2日 上午9:33:23 <br/>
 *
 * @author:guozc
 */
@ParentPackage("shop")
public class RenewalInsuranceAction extends BaseShopAction {
	private static final long serialVersionUID = 4903840845280609420L;

	private static final Logger logger = LoggerFactory.getLogger(RenewalInsuranceAction.class);

	@Resource
	private RenewalInsuranceService allianzRenewalInsuranceService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private SDOrderService sdorderService;
	@Resource
	public CaptchaService captchaService;

	private String needUWCheckFlag = "0";// 是否需要核保的标记
	private String mid;// 中间表id
	private String kid;// 中间表id的kid
	private Map<String, Object> initInsureData = new HashMap<String, Object>();// 投保信息录入页面初始化数据
	private String activityInfo;// 活动信息
	private String isLogin; // 是否登录
	private String policyNo;// 保单号
	private String productIdLV;//// 升级计划产品编码
	private String planCodeLV;// 升级计划计划编码
	private String cpsNo; // CPS码
	private String errorMassage; // 错误信息
	private String zhenAiChoseProductPlanFlag; // 用于记录那里进入的（网销，url,续费链接）
	private String zhenAiRenewalID;// zhenAiRenewal的id
	private String zhenAiRenewalKID;// zhenAiRenewal的id
	private ZhenAiRenewalSchema zhenAiRenewalSchema;// 臻爱产品续费中间表

	/**
	 * initInsure:初始化投保信息录入页面. <br/>
	 * 
	 * @author guozc
	 * @return 返回页面名称
	 */
	@SuppressWarnings("unchecked")
	public String initInsure() {
		// 参数校验没通过提示
		String verifyParamFailTips = "参数错误";
		// 系统异常提示
		String exceptionTips = "系统错误";
		// 返回页面名称
		String viewName = null;

		if (StringUtil.isEmpty(mid)) {
			logger.error("mid不能为空.");
			addActionError(verifyParamFailTips);
			return ERROR;
		}
		if (StringUtil.isEmpty(kid)) {
			logger.error("kid不能为空.");
			addActionError(verifyParamFailTips);
			return ERROR;
		}
		String mKid = StringUtil.md5Hex(PubFun.getKeyValue() + mid);
		if (!mKid.equals(kid)) {
			logger.error("kid不合法.");
			addActionError(verifyParamFailTips);
			return ERROR;
		}

		try {
			/* 获取中间表信息 */
			ZhenAiRenewalSchema renewal = new ZhenAiRenewalSchema();
			renewal.setID(mid);
			renewal.fill();
			String productId = renewal.getChoseProductId();
			if (productId == null) {
				logger.error("中间表数据不存在.");
				addActionError(verifyParamFailTips);
				return ERROR;
			}
			initInsureData.putAll(new org.apache.commons.beanutils.BeanMap(renewal));
			String applicantIdentityTypeName = this.dictionaryService.getNameByCodeTypePro(productId,
					productId.substring(0, 4), "certificate", renewal.getApplicantIdentityType());
			String recognizeeIdentityTypeName = this.dictionaryService.getNameByCodeTypePro(productId,
					productId.substring(0, 4), "certificate", renewal.getRecognizeeIdentityType());
			initInsureData.put("applicantIdentityTypeName", applicantIdentityTypeName);
			initInsureData.put("recognizeeIdentityTypeName", recognizeeIdentityTypeName);
			String complicatedFlag = new QueryBuilder("select complicatedFlag from sdproduct where productId=?",
					productId).executeString();
			initInsureData.put("complicatedFlag", complicatedFlag);
			Map<String, Object> productInfo = sdorderService.getProductInformation(productId, "N", "wj");
			// 产品基础数据
			String[] baseInformation = (String[]) productInfo.get("baseInformation");
			// 投保要素信息
			List<OrderRiskAppFactor> riskAppFactior = (List<OrderRiskAppFactor>) productInfo.get("riskAppFactor");
			// 责任信息
			List<OrderDutyFactor> dutyFactor = (List<OrderDutyFactor>) productInfo.get("dutyFactor");
			setSession(productId + "baseInformation", baseInformation);
			setSession(productId + "riskAppFactior", riskAppFactior);
			setSession(productId + "dutyFactor", dutyFactor);
			SalesVolumeAction salesVolumeAction = new SalesVolumeAction();
			Map<String, String> map = salesVolumeAction.shoppingShowActivity(productId, "wj");
			activityInfo = map.get("activityInfo");

			/* 根据产品类型调用service处理 */
			String companyCode = productId.substring(0, 4);
			if (RiskCompanyCodeEnum.ALLIANZ.getCode().equals(companyCode)) {
				Map<String, Object> ret = allianzRenewalInsuranceService.initInsure(initInsureData, productInfo);
				if (Constant.FAIL.equals(ret.get(Constant.STATUS))) {
					addActionError((String) ret.get(Constant.MSG));
					return ERROR;
				}
				viewName = "allianzinsure";
			}
		} catch (Exception e) {
			logger.error("initInsure异常.", e);
			addActionError(exceptionTips);
			return ERROR;
		}
		return viewName;
	}

	
	public String checkRenewal() {
		String policyNo = getParameter("policyNo");
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "Y");
		if (StringUtil.isEmpty(policyNo)) {
			jsonMap.put("status", "N");
			jsonMap.put("message", "未取到保单号，请确定保单是否承保成功！");
		} else {
			Map<String, Object> jingDaiTongReturnMap = queryRenewalByPolicyNo(policyNo);
			if (StringUtil.isEmpty(jingDaiTongReturnMap)) {// 安联接口查询不到订单号
				jsonMap.put("status", "N");
				jsonMap.put("message", "保险公司无响应，请稍后再试！如有疑问，请联系客服和保险公司确认！");
			} else if (jingDaiTongReturnMap.containsKey("responseCode")) {
				jsonMap.put("status", "N");
				jsonMap.put("message", (String)jingDaiTongReturnMap.get("responseMessage"));
			}
		}
		
		return ajaxJson(jsonMap);
	}
	
	public String zhenAiChoseProductPlan() {
		if (getLoginMember() != null) {
			isLogin = "Y";
		}
		zhenAiRenewalSchema = new ZhenAiRenewalSchema();
		zhenAiChoseProductPlanFlag = "WJIN";
		if (StringUtil.isNotEmpty(cpsNo)) {
			zhenAiChoseProductPlanFlag = "CPS";
		}
		if (StringUtil.isEmpty(policyNo)) {
			zhenAiChoseProductPlanFlag = "URL";
			return "zhenAiChoseProductPlan";
		}
		Map<String, Object> jingDaiTongReturnMap = queryRenewalByPolicyNo(policyNo);
		if (StringUtil.isEmpty(jingDaiTongReturnMap)) {// 安联接口查询不到订单号
			errorMassage = "保险公司无响应，请稍后再试！如有疑问，请联系客服和保险公司确认！";
			zhenAiChoseProductPlanFlag = "URL";
			return "zhenAiChoseProductPlan";
		} else if (jingDaiTongReturnMap.containsKey("responseCode")) {
			errorMassage = (String)jingDaiTongReturnMap.get("responseMessage");
			zhenAiChoseProductPlanFlag = "URL";
			return "zhenAiChoseProductPlan";
		}
		
		zhenAiRenewalSchema = allianzRenewalInsuranceService.jsonInitZhenAiRenewal(jingDaiTongReturnMap, policyNo, errorMassage);
		if (zhenAiRenewalSchema==null) {// 存储信息到中间表异常
			errorMassage = "系统未找到保单号"+policyNo+"！";
			zhenAiChoseProductPlanFlag = "URL";
			return "zhenAiChoseProductPlan";
		}
		if (StringUtil.isNotEmpty(errorMassage)) {// 存储信息到中间表异常
			zhenAiChoseProductPlanFlag = "URL";
			return "zhenAiChoseProductPlan";
		}
		productIdLV = zhenAiRenewalSchema.getProp1();
		planCodeLV = zhenAiRenewalSchema.getProp2();
		zhenAiRenewalSchema.setProp1("");
		zhenAiRenewalSchema.setProp2("");
		if ("CPS".equals(zhenAiChoseProductPlanFlag) && StringUtil.isNotEmpty(cpsNo)) {
			zhenAiRenewalSchema.setCspNo(cpsNo);
			zhenAiRenewalSchema.setChannelsn("CPS");
		} else if ("WJIN".equals(zhenAiChoseProductPlanFlag)) {
			zhenAiRenewalSchema.setChannelsn("WJ");
		}
		zhenAiRenewalID = zhenAiRenewalSchema.getID();
		zhenAiRenewalKID = StringUtil.md5Hex(PubFun.getKeyValue() + zhenAiRenewalID);
		zhenAiRenewalSchema.insert();

		return "zhenAiChoseProductPlan";
	}

	// 选择计划提交
	public String chosePlanFormSubmit() throws Exception {
		String zhenAiRenewalId = "";
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "error");
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("jsonpCallback");
		try {
			String captchaID = getRequest().getSession().getId();
			String challengeResponse = StringUtils.upperCase(getRequest().getParameter("subMitImage"));
			if (StringUtils.isEmpty(challengeResponse)
					|| captchaService.validateResponseForID(captchaID, challengeResponse) == false) {
				jsonMap.put("message", "验证码没有写对哟");
				return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
			}
		} catch (Exception e) {
			jsonMap.put("message", "验证码没有写对哟");
			return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
		}

		String zhenAiChoseProductPlanFlag = getRequest().getParameter("zhenAiChoseProductPlanFlag");
		String choseProduct = getRequest().getParameter("choseProduct");// 选择的计划
		String MyPolicyNo = getRequest().getParameter("policyNo");
		String MyApplicantMobile = getRequest().getParameter("applicantMobile");
		productIdLV = getRequest().getParameter("productIdLV");
		planCodeLV = getRequest().getParameter("planCodeLV");
		zhenAiRenewalID = getRequest().getParameter("zhenAiRenewalID");

		if ("URL".equals(zhenAiChoseProductPlanFlag)) {
			Map<String, Object> jingDaiTongReturnMap = queryRenewalByPolicyNo(policyNo);
			if (StringUtil.isEmpty(jingDaiTongReturnMap)) {// 安联接口查询不到订单号
				jsonMap.put("message", "保险公司无响应，请稍后再试！如有疑问，请联系客服和保险公司确认！");
				return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
			} else if (jingDaiTongReturnMap.containsKey("responseCode")) {
				jsonMap.put("message", (String)jingDaiTongReturnMap.get("responseMessage"));
				return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
			}
			
			zhenAiRenewalSchema = allianzRenewalInsuranceService.jsonInitZhenAiRenewal(jingDaiTongReturnMap, policyNo,
					errorMassage);
			if (StringUtil.isNotEmpty(errorMassage)) {// 存储信息到中间表异常
				jsonMap.put("message", errorMassage);
				errorMassage = "";
				return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
			}
			productIdLV = zhenAiRenewalSchema.getProp1();
			planCodeLV = zhenAiRenewalSchema.getProp2();
			zhenAiRenewalSchema.setProp1("");
			zhenAiRenewalSchema.setProp2("");
		} else {
			zhenAiRenewalSchema = new ZhenAiRenewalSchema();
			zhenAiRenewalSchema.setID(zhenAiRenewalID);
			zhenAiRenewalSchema.fill();
		}

		if (!zhenAiRenewalSchema.getPolicyNo().equals(MyPolicyNo)) {
			jsonMap.put("message", "保单信息有误！。");
			return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
		} else if (!zhenAiRenewalSchema.getApplicantMobile().equals(MyApplicantMobile)) {
			jsonMap.put("message", "投保人电话与原单信息不匹配！。");
			return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
		}

		if ("up_btns".equals(choseProduct)) {
			if (StringUtil.isEmpty(productIdLV)) {// 已是最高级计划
				jsonMap.put("message", "您的保障已经是最高级别哦！请选择原计划续保即可~");
				return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
			} else {
				zhenAiRenewalSchema.setChoseProductId(productIdLV);
				zhenAiRenewalSchema.setChosePlanCode(planCodeLV);
			}
		} else {// 原计划
			zhenAiRenewalSchema.setChoseProductId(zhenAiRenewalSchema.getProductId());
			zhenAiRenewalSchema.setChosePlanCode(zhenAiRenewalSchema.getPlanCode());
		}
		zhenAiRenewalId = zhenAiRenewalSchema.getID();
		if ("URL".equals(zhenAiChoseProductPlanFlag)) {
			zhenAiRenewalSchema.insert();
		} else {
			zhenAiRenewalSchema.update();
		}
		jsonMap.put("status", "success");
		jsonMap.put("message", zhenAiRenewalId);
		return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
	}

	// 输出JSON错误消息，返回null
	public String ajaxJsonErrorMessageContent(String message) {

		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return jsonObject.toString();
	}

	@Resource
	private RenewalService renewalService;

	/**
	 * 
	 * 续保查询
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryRenewalByPolicyNo(String policyNo) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("policyNo", policyNo);
			Map<String, Object> rwResultsMap = renewalService.queryRenewal(map);
			Map<String, Object> responseMap = (Map<String, Object>) rwResultsMap.get("head");
			if (StringUtil.isNotEmpty(policyNo) && responseMap != null && responseMap.size() > 0) {
				if ("00".equals(responseMap.get("responseCode"))) {
					return (Map<String, Object>) rwResultsMap.get("body");
					
				} else {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					resultMap.put("responseCode", responseMap.get("responseCode"));
					if ("03".equals(responseMap.get("responseCode"))) {
						resultMap.put("responseMessage", "您已完成续保，无需重复操作！");
					} else {
						resultMap.put("responseMessage", responseMap.get("responseMessage")+" 如有疑问，请联系客服和保险公司确认！");
					}
					Object[] argArr = {policyNo, responseMap.get("responseMessage")};
					logger.error("保单（{}）不满足续保条件：{}", argArr);
					return resultMap;
					
				}
			} else {
				return null;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ZhenAiRenewalSchema getZhenAiRenewalSchema() {
		return zhenAiRenewalSchema;
	}

	public void setZhenAiRenewalSchema(ZhenAiRenewalSchema zhenAiRenewalSchema) {
		this.zhenAiRenewalSchema = zhenAiRenewalSchema;
	}

	public String getZhenAiRenewalID() {
		return zhenAiRenewalID;
	}

	public void setZhenAiRenewalID(String zhenAiRenewalID) {
		this.zhenAiRenewalID = zhenAiRenewalID;
	}

	public String getZhenAiRenewalKID() {
		return zhenAiRenewalKID;
	}

	public void setZhenAiRenewalKID(String zhenAiRenewalKID) {
		this.zhenAiRenewalKID = zhenAiRenewalKID;
	}

	public String getCpsNo() {
		return cpsNo;
	}

	public void setCpsNo(String cpsNo) {
		this.cpsNo = cpsNo;
	}

	public String getZhenAiChoseProductPlanFlag() {
		return zhenAiChoseProductPlanFlag;
	}

	public void setZhenAiChoseProductPlanFlag(String zhenAiChoseProductPlanFlag) {
		this.zhenAiChoseProductPlanFlag = zhenAiChoseProductPlanFlag;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getProductIdLV() {
		return productIdLV;
	}

	public void setProductIdLV(String productIdLV) {
		this.productIdLV = productIdLV;
	}

	public String getPlanCodeLV() {
		return planCodeLV;
	}

	public void setPlanCodeLV(String planCodeLV) {
		this.planCodeLV = planCodeLV;
	}

	public String getErrorMassage() {
		return errorMassage;
	}

	public void setErrorMassage(String errorMassage) {
		this.errorMassage = errorMassage;
	}

	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public Map<String, Object> getInitInsureData() {
		return initInsureData;
	}

	public void setInitInsureData(Map<String, Object> initInsureData) {
		this.initInsureData = initInsureData;
	}

	public String getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(String activityInfo) {
		this.activityInfo = activityInfo;
	}

	public String getNeedUWCheckFlag() {
		return needUWCheckFlag;
	}

	public void setNeedUWCheckFlag(String needUWCheckFlag) {
		this.needUWCheckFlag = needUWCheckFlag;
	}

}
