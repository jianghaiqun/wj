package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.service.LicaiBaoxianService;
import cn.com.sinosoft.util.Constant;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.jdt.cc.CCDealInterface;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDCardBindSchema;
import com.sinosoft.schema.SDCardBindSet;
import com.sinosoft.schema.SDInformationAppntSchema;
import com.sinosoft.schema.SDInformationAppntSet;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDInformationSet;
import com.sinosoft.schema.SDOrdersSchema;
import com.sinosoft.schema.SDOrdersSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service实现类
 * ============================================================================
 * 
 * ============================================================================
 */

@Service
public class LicaiBaoxianServiceImpl implements LicaiBaoxianService {

	private static final Logger logger = LoggerFactory.getLogger(LicaiBaoxianServiceImpl.class);
	@Override
	public Map<String, Object> cardCheckSend(Map<String, Object> param) {

		String orderSn = (String) param.get("orderSn");
		String bankCode = (String) param.get("bankCode");
		String bankAccNo = (String) param.get("bankAccNo");

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// 订单信息
		SDOrdersSchema order = new SDOrdersSchema();
		SDOrdersSet orderSet = order.query(new QueryBuilder(" where orderSn = ?", orderSn));

		if (orderSet.size() > 0) {
			order = orderSet.get(0);
		} else {
			jsonMap.put("status", "1");
			jsonMap.put("MSG", "小额验签异常，请联系客服！");
			return jsonMap;
		}
		
		// 订单详细信息
		SDInformationSchema sDInformation = new SDInformationSchema();
		
		SDInformationSet informationSet = sDInformation.query(new QueryBuilder(" where orderSn = ?", orderSn));
		sDInformation = informationSet.get(0);
		
		// 投保人信息
		SDInformationAppntSchema sDInformationAppnt = new SDInformationAppntSchema();
		SDInformationAppntSet sDInformationAppntSet = sDInformationAppnt.query(new QueryBuilder(" where informationSn = ?", sDInformation.getinformationSn()));
		sDInformationAppnt = sDInformationAppntSet.get(0);

		SDCardBindSchema cb = new SDCardBindSchema();
		QueryBuilder qb = new QueryBuilder(" where MemberId = ? and AccName = ? and BankAccNo = ? and BankCode = ?");
		qb.add(order.getmemberId());
		qb.add(sDInformationAppnt.getapplicantName());
		qb.add(bankAccNo);
		qb.add(bankCode);
		SDCardBindSet cbSet = cb.query(qb);
		// 返回错误信息
		String MSG = "";
		// 业务号
		String bussNo = "";
		// 当天日期时间
		Date currentDate = DateUtil.strToDate(DateUtil.getCurrentDate("yyyy-MM-dd"), "yyyy-MM-dd");
		if (cbSet.size() > 0) {
			cb = cbSet.get(0);
			// 大于等于三次 提示 明天再验证
			if ("Y".equals(cb.getBindFlag()) && "Y".equals(cb.getUseFlag())) {
				jsonMap.put("status", "1");
				jsonMap.put("MSG", "您已绑定当前银行卡, 无需再次绑定。");
				return jsonMap;
			}
			if (Integer.valueOf(cb.getSendCheckCount()) >= 3) {
				jsonMap.put("status", "1");
				jsonMap.put("MSG", "您已发送<em> 3 </em>次，超过次数需换卡支付<br />或联系客服4009-789-789。");
				return jsonMap;
			} else {
				cb.setSendCheckCount((Integer.valueOf(cb.getSendCheckCount()) + 1) + "");
				cb.setCheckCount("0");
				cb.setModifyDate(new Date());
				// 业务号
				bussNo = "KX" + NoUtil.getMaxNo("SDCardBindBussNo", 14);
				cb.setBussNo(bussNo);
				cb.setBindFlag("N");
				cb.setUseFlag("Y");
				cb.update();

				MSG = "60秒后还可以发送<em> " + (3 - Integer.valueOf(cb.getSendCheckCount())) + " </em>次，超过次数需换卡支付<br />或联系客服4009-789-789。";
			}
		} else {
			cb.setId(NoUtil.getMaxNo("SDCardBind", 10));
			cb.setMemberId(order.getmemberId());
			cb.setChannelsn("wj");
			cb.setAccName(sDInformationAppnt.getapplicantName());
			cb.setBankAccNo(bankAccNo);
			cb.setBankCode(bankCode);
			// 业务号
			bussNo = "KX" + NoUtil.getMaxNo("SDCardBindBussNo", 14);
			cb.setBussNo(bussNo);
			cb.setValidateStatus("0");
			cb.setValidatedDate(currentDate);
			cb.setBindFlag("N");
			cb.setSendCheckCount("1");
			cb.setCreateDate(new Date());
			cb.setUseFlag("Y");
			cb.setProp1("1");

			cb.insert();

			MSG = "60秒后还可以发送<em> 2 </em>次，超过次数需换卡支付<br />或联系客服4009-789-789。";
		}

		Map<String, Object> sendMap = new HashMap<String, Object>();

		String comCode = sDInformation.getproductId();
		comCode = comCode.substring(0, 4);

		String transDate = DateUtil.getCurrentDate("yyyy-MM-dd");
		sendMap.put("TransDate", transDate);
		String transTime = DateUtil.getCurrentDate("HH:mm:ss");
		sendMap.put("TransTime", transTime);
		sendMap.put("TransCode", Constant.CARD_CHECK_SEND);
		sendMap.put("ComCode", comCode);

		sendMap.put("BussNo", bussNo);
		sendMap.put("AccName", sDInformationAppnt.getapplicantName());
		sendMap.put("BankAccNo", bankAccNo);
		sendMap.put("BankCode", bankCode);

		Map<String, Object> serviceBackMap = service(sendMap, comCode);

		if ("pass".equals(serviceBackMap.get("passFlag"))) {
			jsonMap.put("status", "0");
			jsonMap.put("MSG", MSG);
			if ("0".equals(serviceBackMap.get("ResultCode"))) {
				cb.setValidateMoney((String) serviceBackMap.get("PayMoney"));
				cb.setValidateStatus("1");
				cb.update();
				jsonMap.put("validateStatus", "1");
			}
		} else {
			jsonMap.put("status", "1");
			jsonMap.put("MSG", (String) serviceBackMap.get("MSG"));
		}
		if (StringUtil.isNotEmpty(bussNo)) {
			jsonMap.put("BussNo", bussNo);
		}

		return jsonMap;
	}

	/**
	 * cardCheckSearch:小额验签查询. <br/>
	 *
	 * @author wwy
	 * @param param
	 * @return
	 */
	public Map<String, Object> cardCheckSearch(Map<String, Object> param) {

		String orderSn = (String) param.get("orderSn");
		
		Map<String, Object> returnMap = new HashMap<String, Object>();

		// 订单信息// 订单信息
		SDOrdersSchema order = new SDOrdersSchema();
		SDOrdersSet orderSet = order.query(new QueryBuilder(" where orderSn = ?", orderSn));

		if (orderSet.size() > 0) {
			order = orderSet.get(0);
		} else {
			returnMap.put("status", "1");
			returnMap.put("MSG", "小额验签异常，请联系客服！");
			return returnMap;
		}
		
		// 订单详细信息// 订单详细信息
		SDInformationSchema sDInformation = new SDInformationSchema();
		
		SDInformationSet informationSet = sDInformation.query(new QueryBuilder(" where orderSn = ?", orderSn));

		sDInformation = informationSet.get(0);
		
		String comCode = sDInformation.getproductId();
		comCode = comCode.substring(0, 4);

		Map<String, Object> sendMap = new HashMap<String, Object>();
		String transDate = DateUtil.getCurrentDate("yyyy-MM-dd");
		sendMap.put("TransDate", transDate);
		String transTime = DateUtil.getCurrentDate("HH:mm:ss");
		sendMap.put("TransTime", transTime);
		sendMap.put("TransCode", Constant.CARD_CHECK_SEARCH);
		sendMap.put("ComCode", comCode);
		sendMap.put("BussType", Constant.BUSS_TYPE_CARD);

		SDCardBindSchema cb = new SDCardBindSchema();
		QueryBuilder qb = new QueryBuilder(" where bussno = ?");
		qb.add((String) param.get("bussNo"));
		SDCardBindSet cbSet = cb.query(qb);
		if (null == cbSet || cbSet.size() == 0 || "Y".equals(cbSet.get(0).getBindFlag())) {
			returnMap.put("status", "1");
			returnMap.put("MSG", "当前业务号不存在或已绑定！");
			return returnMap;
		}
		cb = cbSet.get(0);

		sendMap.put("BussNo", cb.getBussNo());
		sendMap.put("AccName", cb.getAccName());
		sendMap.put("BankAccNo", cb.getBankAccNo());
		sendMap.put("BankCode", cb.getBankCode());

		Map<String, Object> serviceBackMap = service(sendMap, comCode);
		if ("0".equals(serviceBackMap.get("ResultCode"))) {
			cb.setValidateMoney((String) serviceBackMap.get("PayMoney"));
			cb.setValidateStatus("1");
			cb.update();
			returnMap.put("status", "0");
			returnMap.put("MSG", "查询完成，小额验签成功！");
		} else {
			returnMap.put("status", "1");
			returnMap.put("MSG", "查询完成，小额验签失败或者处理中！");
		}

		return returnMap;
	}

	/**
	 * canceltry:退保试算. <br/>
	 *
	 * @author wwy
	 * @param param
	 * @return
	 */
	public Map<String, Object> canceltry (Map<String, Object> param) {

		String orderSn = (String) param.get("orderSn");
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> result = null;
		
		Map<String, Object> toMap = new HashMap<String, Object>();


		// 订单信息
		SDOrdersSchema order = new SDOrdersSchema();
		SDOrdersSet orderSet = order.query(new QueryBuilder(" where orderSn = ?", orderSn));

		if (orderSet.size() > 0) {
			order = orderSet.get(0);
		} else {
			returnMap.put("status", "1");
			returnMap.put("MSG", "小额验签异常，请联系客服！");
			return returnMap;
		}
		
		SDInformationRiskTypeSchema riskTypeSchema = new SDInformationRiskTypeSchema();
		SDInformationRiskTypeSet riskTypeSet = riskTypeSchema.query(new QueryBuilder(" where ordersn = ?", orderSn));
		riskTypeSchema = riskTypeSet.get(0);
		
		String comCode = riskTypeSchema.getriskCode().substring(0, 4);
		toMap.put("ComCode", comCode);
		toMap.put("ContNo", riskTypeSchema.getpolicyNo());
		String transDate = DateUtil.getCurrentDate("yyyy-MM-dd");
		toMap.put("TransDate", transDate);
		String transTime = DateUtil.getCurrentDate("HH:mm:ss");
		toMap.put("TransTime", transTime);
		toMap.put("TransCode",  Constant.CANCEL_TRY);
		toMap.put("EdorAppDate",  DateUtil.getCurrentDate("yyyy-MM-dd"));
		
		try {
			Class<?> ccDeal = Class.forName("com.sinosoft.jdt.cc.CCDeal"+ comCode);
			CCDealInterface ccdi = (CCDealInterface) ccDeal.newInstance();
			result = ccdi.canceltry(toMap);
		} catch (Exception e) {
			logger.error("调用经代通接口出现异常" + e.getMessage(), e);
		}
		
		returnMap.put("status", "0");
		returnMap.putAll(result);
		return returnMap;
	}

	/**
	 * service:调用经代通 小额验签. <br/>
	 *
	 * @author wwy
	 * @param toMap
	 * @param comCode
	 * @return
	 */
	private Map<String, Object> service(Map<String, Object> toMap, String comCode) {

		Map<String, Object> result = null;
		try {
			Class<?> ccDeal = Class.forName("com.sinosoft.jdt.cc.CCDeal" + comCode);
			CCDealInterface ccdi = (CCDealInterface) ccDeal.newInstance();
			result = ccdi.cardCheck(toMap);
		} catch (Exception e) {
			logger.error("调用经代通接口出现异常{}" + e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 校验银行卡.
	 * @see cn.com.sinosoft.service.LicaiBaoxianService#checkBankInfo(java.util.Map)
	 */
	@Override
	public Map<String, Object> checkBankInfo(Map<String, Object> params) {

		String memberid = String.valueOf(params.get("memberId"));
		String orderSn = String.valueOf(params.get("orderSn"));
		String accName = String.valueOf(params.get("accName"));
		String bankAccNo = String.valueOf(params.get("bankAccNo"));
		String bankCode = String.valueOf(params.get("bankCode"));
		String validateMoney = String.valueOf(params.get("validateMoney"));
		// 理财险 
		if (StringUtil.isEmpty(bankCode)) {
			return PubFun.errMsg("银行编号不能为空！");
		}
		if (StringUtil.isEmpty(bankAccNo)) {
			return PubFun.errMsg("银行卡号不能为空！");
		}
		if (StringUtil.isEmpty(accName)) {
			return PubFun.errMsg("银行卡账号名不能为空！");
		}
	
		Map<String, String> param = new HashMap<String, String>();
		param.put("MemberId", memberid);
		param.put("AccName", accName);
		param.put("BankAccNo", bankAccNo);
		param.put("BankCode", bankCode);
		SDCardBindSchema cb = new SDCardBindSchema();
		QueryBuilder qb = new QueryBuilder(" where MemberId = ? and AccName = ? and BankAccNo = ? and BankCode = ?");
		qb.add(memberid);
		qb.add(accName);
		qb.add(bankAccNo);
		qb.add(bankCode);
		SDCardBindSet cbSet = cb.query(qb);

		if (null != cbSet && cbSet.size() > 0) {
			
			cb = cbSet.get(0);
			// 判断已经绑定的账号
			if ("Y".equals(cb.getBindFlag())) {
				return PubFun.sucMsg();
			} else {
				if ("1".equals(cb.getValidateStatus())) {
					// 判断验证次数
					if ("1".equals(cb.getCheckCount())) {
						return PubFun.errMsg("上次验证金额已失败，请重新发送支付验证。");
					}
					if (validateMoney.equals(cb.getValidateMoney())) {
						cb.setBindFlag("Y");
						cb.setCheckCount("0");
						cb.update();
						return PubFun.sucMsg();
					} else {
						cb.setCheckCount("1");
						cb.update();
						return PubFun.errMsg("验证金额输入错误，请重新发送支付验证。");
					}
				} else {
					
					// 调用小额验签查询
					Map<String, Object> searchParam = new HashMap<String, Object>();
					searchParam.put("ordersn", orderSn);
					searchParam.put("bussno", cb.getBussNo());
					Map<String, Object> result = cardCheckSearch(searchParam);
					if ("0".equals(result.get("ResultCode"))) {
						// 验证金额正确
						if (validateMoney.equals(result.get("PayMoney"))) {
							cb.setBindFlag("Y");
							cb.setValidateStatus("1");
							cb.setValidateMoney((String) result.get("PayMoney"));
							cb.setCheckCount("0");
							cb.update();
							return PubFun.sucMsg();
						} 
						// 验证金额错误
						else {
							cb.setValidateStatus("1");
							cb.setCheckCount("1");
							cb.update();
							return PubFun.errMsg("验证金额输入错误，请重新发送验证。");
						}
					} else if ("2".equals(result.get("ResultCode"))) {
						return PubFun.errMsg("银行卡验证中请稍后。");
					} else {
						return PubFun.errMsg("银行卡验证不通过,请更换银行卡。失败信息：" + result.get("MSG"));
					}
				}
			}
		}
		return PubFun.errMsg("银行卡验证不通过，请重新发送验证。");
	}
	
	@Override
	public Map<String, Object> obtainVCode(Map<String, Object> param) {

		String orderSn = (String) param.get("orderSn");
		String bankCode = (String) param.get("bankCode");
		String bankAccNo = (String) param.get("bankAccNo"); 

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		// 订单信息
		SDOrdersSchema order = new SDOrdersSchema();
		SDOrdersSet orderSet = order.query(new QueryBuilder(" where orderSn = ?", orderSn));

		if (orderSet.size() > 0) {
			order = orderSet.get(0);
		} else {
			jsonMap.put("status", "1");
			jsonMap.put("MSG", "获取验证码异常，请联系客服！");
			return jsonMap;
		}
		
		// 订单详细信息
		SDInformationSchema sDInformation = new SDInformationSchema();
		
		SDInformationSet informationSet = sDInformation.query(new QueryBuilder(" where orderSn = ?", orderSn));
		sDInformation = informationSet.get(0);
		
		// 投保人信息
		SDInformationAppntSchema sDInformationAppnt = new SDInformationAppntSchema();
		SDInformationAppntSet sDInformationAppntSet = sDInformationAppnt.query(new QueryBuilder(" where informationSn = ?", sDInformation.getinformationSn()));
		sDInformationAppnt = sDInformationAppntSet.get(0);
		
		SDCardBindSchema cb = new SDCardBindSchema();
		QueryBuilder qb = new QueryBuilder(" where MemberId = ? and AccName = ? and BankAccNo = ? and BankCode = ?");
		qb.add(order.getmemberId());
		qb.add(sDInformationAppnt.getapplicantName());
		qb.add(bankAccNo);
		qb.add(bankCode);
		SDCardBindSet cbSet = cb.query(qb);
		
		// 返回错误信息
		String MSG = "";
		// 业务号
		String bussNo = orderSn + "_1";
		// 当天日期时间
		Date currentDate = DateUtil.strToDate(DateUtil.getCurrentDate("yyyy-MM-dd"), "yyyy-MM-dd");
		if (cbSet.size() > 0) {
			cb = cbSet.get(0);
			cb.setModifyDate(new Date());
			// 业务号
			cb.setBussNo(bussNo);
			cb.setBindFlag("N");
			cb.update();
			MSG = "验证码发送成功。";
		} else {
			cb.setId(NoUtil.getMaxNo("SDCardBind", 10));
			cb.setMemberId(order.getmemberId());
			cb.setChannelsn("wj");
			cb.setAccName(sDInformationAppnt.getapplicantName());
			cb.setBankAccNo(bankAccNo);
			cb.setBankCode(bankCode);
			// 业务号
			cb.setBussNo(bussNo);
			cb.setValidateStatus("0");
			cb.setValidatedDate(currentDate);
			cb.setBindFlag("N");
			cb.setSendCheckCount("1");
			cb.setCreateDate(new Date());
			cb.setUseFlag("Y");
			cb.insert();

			MSG = "验证码发送成功。";
		}

		Map<String, Object> sendMap = new HashMap<String, Object>();

		String comCode = sDInformation.getproductId();
		comCode = comCode.substring(0, 4);

		String transDate = DateUtil.getCurrentDate("yyyy-MM-dd");
		sendMap.put("TransDate", transDate);
		String transTime = DateUtil.getCurrentDate("HH:mm:ss");
		sendMap.put("TransTime", transTime);
		sendMap.put("TransCode", Constant.CARD_CHECK_VCODE);
		sendMap.put("ComCode", comCode);

		sendMap.put("BussNo", bussNo);
		sendMap.put("AccName", sDInformationAppnt.getapplicantName());
		sendMap.put("BankAccNo", bankAccNo);
		sendMap.put("BankCode", bankCode);
		sendMap.put("Mobile", sDInformationAppnt.getapplicantMobile());
		sendMap.put("IdCard", sDInformationAppnt.getapplicantIdentityId());

		Map<String, Object> serviceBackMap = service(sendMap, comCode);

		if ("pass".equals(serviceBackMap.get("passFlag"))) {
			jsonMap.put("status", "0");
			jsonMap.put("MSG", MSG);
			if ("0".equals(serviceBackMap.get("ResultCode"))) {
				cb.setProp2((String) serviceBackMap.get("vCode"));
				cb.setValidateStatus("1");
				cb.update();
				jsonMap.put("validateStatus", "1");
			}
		} else {
			jsonMap.put("status", "1");
			jsonMap.put("MSG", (String) serviceBackMap.get("MSG"));
		}
		if (StringUtil.isNotEmpty(bussNo)) {
			jsonMap.put("BussNo", bussNo);
		}

		return jsonMap;
	}

	@Override
	public Map<String, Object> checkVCode(Map<String, Object> params) {
		String memberid = String.valueOf(params.get("memberId"));
		String orderSn = String.valueOf(params.get("orderSn"));
		String accName = String.valueOf(params.get("accName"));
		String bankAccNo = String.valueOf(params.get("bankAccNo"));
		String bankCode = String.valueOf(params.get("bankCode"));
		String vCode = String.valueOf(params.get("vCode"));
		// 理财险 
		if (StringUtil.isEmpty(bankCode)) {
			return PubFun.errMsg("银行编号不能为空！");
		}
		if (StringUtil.isEmpty(bankAccNo)) {
			return PubFun.errMsg("银行卡号不能为空！");
		}
		if (StringUtil.isEmpty(accName)) {
			return PubFun.errMsg("银行卡账号名不能为空！");
		}
		if (StringUtil.isEmpty(orderSn)) {
			return PubFun.errMsg("订单号不能为空！");
		}
		if (StringUtil.isEmpty(vCode)) {
			return PubFun.errMsg("验证码不能为空！");
		}
	
		Map<String, String> param = new HashMap<String, String>();
		param.put("MemberId", memberid);
		param.put("AccName", accName);
		param.put("BankAccNo", bankAccNo);
		param.put("BankCode", bankCode);
		SDCardBindSchema cb = new SDCardBindSchema();
		QueryBuilder qb = new QueryBuilder(" where MemberId = ? and AccName = ? and BankAccNo = ? and BankCode = ?");
		qb.add(memberid);
		qb.add(accName);
		qb.add(bankAccNo);
		qb.add(bankCode);
		SDCardBindSet cbSet = cb.query(qb);

		if (null != cbSet && cbSet.size() > 0) {
			
			cb = cbSet.get(0);
			if (vCode.equals(cb.getProp2())) {
				cb.setBindFlag("Y");
				cb.update();
				return PubFun.sucMsg();
			} else {
				return PubFun.errMsg("验证码错误，请重新发送或者联系客服处理。");
			}
		}
		return PubFun.errMsg("银行卡验证不通过，请重新发送验证。");
	}
}
