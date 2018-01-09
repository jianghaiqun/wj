/**
 * Project Name:wj-code
 * File Name:AllianzRenewalInsuranceServiceImpl.java
 * Package Name:cn.com.sinosoft.service.impl
 * Date:2017年11月2日上午9:43:31
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZhenAiRenewalSchema;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.service.RenewalInsuranceService;

/**
 * ClassName:AllianzRenewalInsuranceServiceImpl <br/>
 * Function:TODO 安联续保. <br/>
 * Date:2017年11月2日 上午9:43:31 <br/>
 *
 * @author:guozc
 */
@Service("allianzRenewalInsuranceService")
public class AllianzRenewalInsuranceServiceImpl implements RenewalInsuranceService {

	private static final Logger LOG = LoggerFactory.getLogger(AllianzRenewalInsuranceServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> initInsure(Map<String, Object> initInsureData, Map<String, Object> productInfo)
			throws Exception {
		// 存储保费试算参数
		Map<String, Object> premcalFactors = new HashMap<String, Object>();
		// 产品基础数据
		String[] BaseInformation = (String[]) productInfo.get("baseInformation");
		// 投保要素信息
		List<OrderRiskAppFactor> riskAppFactior = (List<OrderRiskAppFactor>) productInfo.get("riskAppFactor");
		for (OrderRiskAppFactor factor : riskAppFactior) {
			String factoryType = factor.getFactorType();
			List<FEMRiskFactorList> factorValues = factor.getFactorValue();
			if ("Period".equals(factoryType)) {
				initInsureData.put("period", factorValues.get(0).getFactorDisplayValue());
				premcalFactors.put("Period", factorValues.get(0).getFactorValue());
			} else if ("Plan".equals(factoryType)) {
				for (FEMRiskFactorList value : factorValues) {
					if (value.getFactorValue().equals(initInsureData.get("chosePlanCode"))) {
						initInsureData.put("planName", value.getFactorDisplayValue());
						premcalFactors.put("Plan", value.getFactorValue());
						break;
					}
				}
			}
		}
		premcalFactors.put("Sex", initInsureData.get("recognizeeSex"));
		premcalFactors.put("TextAge", initInsureData.get("recognizeeBirthDate"));
		initInsureData.put("premcalFactors", premcalFactors);
		initInsureData.put("productName", BaseInformation[1]);
		// 责任信息
		List<OrderDutyFactor> dutyFactor = (List<OrderDutyFactor>) productInfo.get("dutyFactor");
		String planCode = (String) initInsureData.get("chosePlanCode");
		StringBuffer dutys = new StringBuffer();
		StringBuffer dutyDis = new StringBuffer();
		dutys.append("{");
		dutyDis.append("{");
		for (OrderDutyFactor duty : dutyFactor) {
			String dutyVal = null;
			String dutyDisVal = null;
			for (FEMDutyAmntPremList fEMDutyAmntPremList : duty.getFdAmntPremList()) {
				if (planCode.equals(fEMDutyAmntPremList.getAppFactorValue())) {
					dutyVal = fEMDutyAmntPremList.getBackUp1();
					dutyDisVal = fEMDutyAmntPremList.getAmnt();
					break;
				}
			}
			if ((StringUtil.isEmpty(dutyVal))) {
				dutyVal = "nvalue";
			}
			dutys.append("'");
			dutys.append(duty.getDutyCode());
			dutys.append("':");
			dutys.append("'" + dutyVal + "',");

			dutyDis.append("'");
			dutyDis.append(duty.getDutyCode());
			dutyDis.append("':");
			dutyDis.append("'" + dutyDisVal + "',");
		}
		dutys = dutys.deleteCharAt(dutys.length() - 1);
		dutys.append("}");
		premcalFactors.put("Dutys", dutys.toString());
		dutyDis = dutyDis.deleteCharAt(dutyDis.length() - 1);
		dutyDis.append("}");
		premcalFactors.put("DutyDis", dutyDis.toString());
		
		// 查询原保单对应投被保人职业信息
		String sql = "SELECT a.applicantName,a.applicantMobile,a.applicantMail,a.applicantIdentityType,a.applicantIdentityId,a.applicantBirthday,a.applicantSex"+
		",i.recognizeeName,i.recognizeeMobile,i.recognizeeMail,i.recognizeeIdentityType,i.recognizeeIdentityId,i.recognizeeBirthday,i.recognizeeSex"+
		",i.recognizeeAppntRelation,r.evaliDate,a.applicantOccupation1,a.applicantOccupation2,a.applicantOccupation3,i.recognizeeOccupation1,"+
		"i.recognizeeOccupation2,i.recognizeeOccupation3"+
		" FROM sdinformationrisktype r LEFT JOIN SDInformationInsured i ON r.recognizeeSn=i.recognizeeSn  LEFT JOIN SDInformationAppnt a ON r.applicantSn=a.applicantSn"+
		" WHERE r.policyNo= ?"+
		" LIMIT 1";
		QueryBuilder qb = new QueryBuilder(sql, initInsureData.get("policyNo"));
		DataTable dt = qb.executeDataTable();
		if(dt.getRowCount() > 0){
			DataRow dr = dt.get(0);
			initInsureData.put("applicantOccupation1", dr.getString("applicantOccupation1"));
			initInsureData.put("applicantOccupation2", dr.getString("applicantOccupation2"));
			initInsureData.put("applicantOccupation3", dr.getString("applicantOccupation3"));
			initInsureData.put("recognizeeOccupation1", dr.getString("recognizeeOccupation1"));
			initInsureData.put("recognizeeOccupation2", dr.getString("recognizeeOccupation2"));
			initInsureData.put("recognizeeOccupation3", dr.getString("recognizeeOccupation3"));
		}

		// 保单终止日期
		Date endDate = DateUtil.strToDate((String) initInsureData.get("endDate"), "yyyy-MM-dd");
		// 终止日期后6天
		Date afterEndDateSixData = DateUtil.addDay(endDate, +6);
		// 终止日期后一天
		Date afterEndDateOneData = DateUtil.addDay(endDate, +1);
		// 终止日期前两个月
		Date beforeEndDateTwoMonth = DateUtil.addMonth(endDate, -2);
		// 当前日期
		Date now = DateUtil.strToDate(DateUtil.toString(new Date()), "yyyy-MM-dd");
		String policyStartTime = null;
		if (now.getTime() >= beforeEndDateTwoMonth.getTime() && now.getTime() <= endDate.getTime()) {
			policyStartTime = DateUtil.toString(DateUtil.addDay(endDate, +1), "yyyy-MM-dd");
		} else if (now.getTime() >= afterEndDateOneData.getTime() && now.getTime() < afterEndDateSixData.getTime()) {
			policyStartTime = DateUtil.toString(DateUtil.addDay(now, +1), "yyyy-MM-dd");
		} else {
			return PubFun.errMsg("当前日期不在续保时间内.");
		}
		String policyEndTime = DateUtil.toString(afterEndDateSixData, "yyyy-MM-dd");
		initInsureData.put("policyStartTime", policyStartTime);
		initInsureData.put("policyEndTime", policyEndTime);
		LOG.info("起保开始时间：{} 起保结束时间：{}", policyStartTime, policyEndTime);

		return PubFun.sucMsg(initInsureData);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ZhenAiRenewalSchema jsonInitZhenAiRenewal(Map<String, Object> jingDaiTongReturnMap ,String policyNo, String errorMassage){
		delFrompolicyNo(policyNo);
		String planCode = "",planCodeLV = "",productId = "",productIdLV = "";
		ZhenAiRenewalSchema zhenAiRenewalSchema = new ZhenAiRenewalSchema();
		Map<String, Object> resPolicyInfo = (Map<String, Object>) jingDaiTongReturnMap.get("resPolicyInfo");//保单基本信息
		//Map<String, Object> resPolicyHolder = (Map<String, Object>) jingDaiTongReturnMap.get("resPolicyHolder");//投保人信息
		//List<Map<String, Object>> responseInsureds =(List<Map<String, Object>>) jingDaiTongReturnMap.get("responseInsureds");//被保人信息
		planCode = resPolicyInfo.get("planCode").toString();
		try{
			String planCodeType[] = new QueryBuilder("SELECT memo FROM zdcode  WHERE  codetype='2049ZAPlanLeveUp' AND codevalue!='System' AND codevalue='"+planCode+"'").executeString().split(",");
			productId = planCodeType[0];
			if(planCodeType.length>1){
				productIdLV = planCodeType[1];
				planCodeLV = planCodeType[2];
			}else{
				productIdLV = "";
				planCodeLV = "";
			}
		} catch (Exception e) {
			 errorMassage ="安联臻爱升级计划有误，请与管理员联系！";//安联臻爱升级计划表配置错误
			 e.printStackTrace();
		}
		//policyNo="000003881734158";
		String dtsql = " SELECT a.applicantName,a.applicantMobile,a.applicantMail,a.applicantIdentityType,a.applicantIdentityId,a.applicantBirthday,a.applicantSex "
				+"  ,i.recognizeeName,i.recognizeeMobile,i.recognizeeMail,i.recognizeeIdentityType,i.recognizeeIdentityId,i.recognizeeBirthday,i.recognizeeSex  "
				+ " ,i.recognizeeAppntRelation ,r.evaliDate "
				+" FROM sdinformationrisktype r LEFT JOIN SDInformationInsured i ON r.recognizeeSn=i.recognizeeSn  LEFT JOIN SDInformationAppnt a ON r.applicantSn=a.applicantSn " 
				+" WHERE r.policyNo='"+policyNo+"' LIMIT 1 ";
		DataTable dt = new QueryBuilder(dtsql) .executeDataTable();
		if(dt == null ||  dt.getRowCount() < 1){
			 errorMassage ="单号"+policyNo+"不存在！";
			 return null;
		}

		zhenAiRenewalSchema.setID(NoUtil.getMaxNo("ZhenAiRenewalID").toString());
		zhenAiRenewalSchema.setCreateDate(new Date());
		zhenAiRenewalSchema.setPolicyNo(policyNo);
		zhenAiRenewalSchema.setProductId(productId);
		zhenAiRenewalSchema.setPlanCode(planCode);
		zhenAiRenewalSchema.setChoseProductId("");
		zhenAiRenewalSchema.setChosePlanCode("");
		zhenAiRenewalSchema.setEndDate(dt.getString(0, "evaliDate"));
		zhenAiRenewalSchema.setWithPolicyholderRelation(dt.getString(0, "recognizeeAppntRelation"));
		zhenAiRenewalSchema.setApplicantName(dt.getString(0, "applicantName"));
		zhenAiRenewalSchema.setApplicantMobile(dt.getString(0, "applicantMobile"));
		zhenAiRenewalSchema.setApplicantMail(dt.getString(0, "applicantMail"));
		zhenAiRenewalSchema.setApplicantIdentityType(dt.getString(0, "applicantIdentityType"));
		zhenAiRenewalSchema.setApplicantIdentityId(dt.getString(0, "applicantIdentityId"));
		zhenAiRenewalSchema.setApplicantBirthDate(dt.getString(0, "applicantBirthday"));
		zhenAiRenewalSchema.setApplicantSex(dt.getString(0, "applicantSex"));
		zhenAiRenewalSchema.setRecognizeeName(dt.getString(0, "recognizeeName"));
		zhenAiRenewalSchema.setRecognizeeMobile(dt.getString(0, "recognizeeMobile"));
		zhenAiRenewalSchema.setRecognizeeMail(dt.getString(0, "recognizeeMail"));
		zhenAiRenewalSchema.setRecognizeeIdentityType(dt.getString(0, "recognizeeIdentityType"));
		zhenAiRenewalSchema.setRecognizeeIdentityId(dt.getString(0, "recognizeeIdentityId"));
		zhenAiRenewalSchema.setRecognizeeBirthDate(dt.getString(0, "recognizeeBirthday"));
		zhenAiRenewalSchema.setRecognizeeSex(dt.getString(0, "recognizeeSex"));
		zhenAiRenewalSchema.setChannelsn("");
		zhenAiRenewalSchema.setCspNo("");
		zhenAiRenewalSchema.setProp1(productIdLV);//对应新计划 productId暂存 
		zhenAiRenewalSchema.setProp2(planCodeLV);//对应新计划planCode暂存 
		zhenAiRenewalSchema.setProp3("");
		zhenAiRenewalSchema.setProp4("");
		zhenAiRenewalSchema.setProp5("");
		zhenAiRenewalSchema.setProp6("");
		zhenAiRenewalSchema.setProp7("");
		
		return zhenAiRenewalSchema;
	}
	
	/*  
	 * 删除已存在单号数据
	 * 
	 * */
	public void delFrompolicyNo (String policyNo){
		String sql = "DELETE FROM zhenAiRenewal WHERE policyNo= ?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(policyNo);
		qb.executeNoQuery();
	}
	
}
