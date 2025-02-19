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
/**
 * 1014-新华核保处理接口
 * @author heyang 
 *
 */
public class UWCheck1014 implements UWCheckInterfaceNew {

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
			/*if ("Y".equals(UsersUWCheck.getUWCheckFlagByOrderSn(orderSn,sdinformation.getinformationSn(),insuredSn))) {
				strFlag = "0102";
			} else {
				strFlag = "01";
				sdOrderItemOth.settpySn(String.valueOf(System.currentTimeMillis()));
				UsersUWCheck.updateSDOrderItemOth(sdOrderItemOth);
			}*/
			boolean isB2b = PubFun.getChannelsnByOrdersn(orderSn);
			if(isB2b){
				strFlag = "03";
			}else{
				strFlag = "01";
			}
			sdOrderItemOth.settpySn(String.valueOf(System.currentTimeMillis()));
			UsersUWCheck.updateSDOrderItemOth(sdOrderItemOth);
			Map<String, Object> resMap = parse.dealData(strFlag, sdinformation.getinsuranceCompany(), orderSn,insuredSn);
			String passFlag = resMap.get("passFlag")+"";
			if ("pass".equals(passFlag)) {
				String ActalPayAmount = resMap.get("totalPremium").toString();
				if (StringUtil.isEmpty(ActalPayAmount)) {
					rtnMessage = "您的投保信息未通过审核";
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
					map.put("passFlag", "0");
					map.put("rtnMessage", rtnMessage);
					return map;
				}
				UsersUWCheck.updateUWCheckFlag(orderSn,sdinformation.getinformationSn(),insuredSn,"Y");
				sdOrderItemOth.settpySysSn(resMap.get("TpySysSn").toString());
				sdOrderItemOth.settpySysPaySn(resMap.get("TpySysPaySn").toString());
				UsersUWCheck.updateSDOrderItemOth(sdOrderItemOth);
				sdInformationRiskType.setreturnPremiums(BUWBackAmount.toString());
				sdInformationRiskType.setapplyPolicyNo(resMap.get("applyPolicyNo") + "");
				UsersUWCheck.updateSDInformationRiskType(sdInformationRiskType);
			} else if ("nopass".equals(passFlag)) {
				rtnMessage = "您的投保信息未通过审核，失败原因为【" + resMap.get("rtnMessage").toString() + "】";
				UsersUWCheck.updateUWCheckFlag(orderSn,sdinformation.getinformationSn(),insuredSn,"N");
				sdInformationRiskType.setreturnPremiums("0");
				UsersUWCheck.updateSDInformationRiskType(sdInformationRiskType);
				map.put("passFlag", "0");
				map.put("rtnMessage", rtnMessage);
				return map;
			}
			map.put("passFlag", "1");
			map.put("rtnMessage", "恭喜你，你的投保信息成功通过核保！");
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			UsersUWCheck.updateUWCheckFlag(orderSn,sdinformation.getinformationSn(),insuredSn,"N");
			sdInformationRiskType.setreturnPremiums("0");
			UsersUWCheck.updateSDInformationRiskType(sdInformationRiskType);
			map.put("passFlag", "0");
			map.put("rtnMessage", "核保异常");
			return map;
		}
	}

}
