package cn.com.sinosoft.action.shop.uw;

import com.sinosoft.jdt.ParseXMLToMapNew;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationSchema;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 2241-平安健康核保处理接口
 * 
 * @author ncc
 * 
 */
public class UWCheck2241 implements UWCheckInterfaceNew {
 
	@Override 
	public Map<String,String> dealData(SDInformationSchema sdinformation,String insuredSn){
		String orderSn = sdinformation.getorderSn();
		Map<String, String> map = new HashMap<String, String>();
		SDInformationRiskTypeSchema sdInformationRiskType = UsersUWCheck.getSDInformationRiskType(orderSn,sdinformation.getinformationSn(),insuredSn);
		if (StringUtils.isEmpty(sdinformation.getorderSn())) {
			throw new NullPointerException(); 
		}
		String rtnMessage = "";
		try {
			ParseXMLToMapNew parse = new ParseXMLToMapNew();
			String strFlag = "";
			boolean isB2b = PubFun.getChannelsnByOrdersn(orderSn);
			if(isB2b){
				strFlag = "03";
			}else{
				strFlag = "01";
			}
			Map<String, Object> resMap = parse.dealData(strFlag, sdinformation.getinsuranceCompany(), orderSn,insuredSn);
			String passFlag = resMap.get("passFlag").toString();
			if ("pass".equals(passFlag)) {
				UsersUWCheck.updateUWCheckFlag(orderSn,sdinformation.getinformationSn(),insuredSn,"Y");
				sdInformationRiskType.setapplyPolicyNo(resMap.get("applyPolicyNo").toString());
				UsersUWCheck.updateSDInformationRiskType(sdInformationRiskType);
			} else if ("nopass".equals(passFlag)) {
				rtnMessage = "您的投保信息未通过审核，失败原因为【" + resMap.get("rtnMessage").toString() + "】";
				UsersUWCheck.updateUWCheckFlag(orderSn,sdinformation.getinformationSn(),insuredSn,"N");
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
			map.put("passFlag", "0");
			map.put("rtnMessage", "核保异常");
			return map;
		}
	}
	

}
