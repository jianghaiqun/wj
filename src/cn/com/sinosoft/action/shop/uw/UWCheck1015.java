package cn.com.sinosoft.action.shop.uw;

import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.jdt.ParseXMLToMapNew;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDOrderItemOthSchema;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class UWCheck1015 implements UWCheckInterfaceNew {

	@Override
	public Map<String,String> dealData(SDInformationSchema sdinformation,String insuredSn){
		String orderSn = sdinformation.getorderSn();
		Map<String, String> map = new HashMap<String, String>();
		SDInformationRiskTypeSchema sdInformationRiskType = UsersUWCheck.getSDInformationRiskType(orderSn,sdinformation.getinformationSn(),insuredSn);
		SDOrderItemOthSchema sdOrderItemOth = UsersUWCheck.getSDOrderItemOth(orderSn, sdinformation.getinformationSn(),
				insuredSn);
		if (StringUtils.isEmpty(sdinformation.getorderSn())) {
			throw new NullPointerException();
		}
		String rtnMessage = "";
		try {
			ParseXMLToMapNew parse = new ParseXMLToMapNew();
			String strFlag = null;
			if ("Y".equals(UsersUWCheck.getUWCheckFlagByOrderSn(orderSn,sdinformation.getinformationSn(),insuredSn))) {
				strFlag = "0102";
			} else {
				boolean isB2b = PubFun.getChannelsnByOrdersn(orderSn);
				if(isB2b){
					strFlag = "03";
				}else{
					strFlag = "01";
				}
				/* 首次核保换流水号 */
				// order.setTpySn(String.valueOf(System.currentTimeMillis()));
				// orderService.update(order);
				sdOrderItemOth.settpySn(String.valueOf(System.currentTimeMillis()));
				UsersUWCheck.updateSDOrderItemOth(sdOrderItemOth);
			}
			Map<String, Object> resMap = parse.dealData(strFlag, sdinformation.getinsuranceCompany(), orderSn,insuredSn);
			String passFlag = resMap.get("passFlag")+"";
			//JSONObject jsonArray = new JSONObject();
			//String passFlags = null;
			if ("pass".equals(passFlag)) {
				String ActalPayAmount = resMap.get("ActalPayAmount").toString();
				if (StringUtil.isEmpty(ActalPayAmount)) {
					rtnMessage = "您的投保信息未通过审核";
					//passFlags = "{passFlag:'nopass',rtnMessage:'" + rtnMessage + "'}";
					logger.error("错误信息：订单号={}，产品名称:{}，返回金额不能为空！", orderSn, sdinformation.getproductName());
					map.put("passFlag", "0");
					map.put("rtnMessage", rtnMessage);
					return map;
				}
				BigDecimal BOrderAmount = new BigDecimal(sdInformationRiskType.gettimePrem().replaceAll(",", ""));// 电商系统订单金额
				BigDecimal BUWBackAmount = new BigDecimal(ActalPayAmount.replaceAll(",", ""));// 保险公司返回的金额
				if (0 != BOrderAmount.compareTo(BUWBackAmount)) {
					rtnMessage = "您的投保信息未通过审核";
					String detailMsg = "，核保返回金额与订单金额不一致，订单金额=" + BOrderAmount.doubleValue() + "，返回金额=" + ActalPayAmount;
					logger.error("错误信息：订单号={}，产品名称:{}", orderSn, sdinformation.getproductName() + detailMsg);
					//passFlags = "{passFlag:'nopass',rtnMessage:'" + rtnMessage + "'}";
					map.put("passFlag", "0");
					map.put("rtnMessage", rtnMessage);
					return map;
				}
				//passFlags = "{passFlag:'" + passFlag + "'}";
				// 核保通过
				// order.setUWCheckFlag("Y");
				UsersUWCheck.updateUWCheckFlag(orderSn,sdinformation.getinformationSn(),insuredSn,"Y");
				// 保存泰康系统订单号
				// order.setTpySysSn(resMap.get("tpySysSn").toString());
				// order.setPaidAmount(BUWBackAmount);
				// orderService.update(order);
				sdOrderItemOth.settpySysSn(resMap.get("tpySysSn").toString());
				UsersUWCheck.updateSDOrderItemOth(sdOrderItemOth);
				sdInformationRiskType.setreturnPremiums(BUWBackAmount.toString());
				UsersUWCheck.updateSDInformationRiskType(sdInformationRiskType);
			} else if ("nopass".equals(passFlag)) {
				rtnMessage = "您的投保信息未通过审核，失败原因为【" + resMap.get("rtnMessage").toString() + "】";
				//passFlags = "{passFlag:'nopass',rtnMessage:'" + rtnMessage + "'}";
				// order.setUWCheckFlag("N");
				UsersUWCheck.updateUWCheckFlag(orderSn,sdinformation.getinformationSn(),insuredSn,"N");
				// order.setPaidAmount(new BigDecimal("0"));
				// orderService.update(order);
				sdInformationRiskType.setreturnPremiums("0");
				UsersUWCheck.updateSDInformationRiskType(sdInformationRiskType);
				map.put("passFlag", "0");
				map.put("rtnMessage", rtnMessage);
				return map;
			}
			//jsonArray = JSONObject.fromObject(passFlags);
			map.put("passFlag", "1");
			map.put("rtnMessage", "恭喜你，你的投保信息成功通过核保！");
			return map;
		} catch (Exception e) {
			//String passFlags = "{passFlag:'nopass',rtnMessage:'noRtnMssage'}";
			//JSONObject jsonArrays = JSONObject.fromObject(passFlags);
			//String jsonstrs = jsonArrays.toString();
			logger.error(e.getMessage(), e);
			// order.setUWCheckFlag("N");
			UsersUWCheck.updateUWCheckFlag(orderSn,sdinformation.getinformationSn(),insuredSn,"N");
			// order.setPaidAmount(new BigDecimal("0"));
			// orderService.update(order);
			sdInformationRiskType.setreturnPremiums("0");
			UsersUWCheck.updateSDInformationRiskType(sdInformationRiskType);
			//return UsersUWCheck.ajaxJson(jsonstrs);
			map.put("passFlag", "0");
			map.put("rtnMessage", "核保异常");
			return map;
		}
	}

}
