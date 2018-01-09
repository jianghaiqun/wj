package cn.com.sinosoft.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 积分商城--兑换电话卡处理类
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * ============================================================================
 */

/**
 * @author sunny
 *
 */
public class Points2 extends PointExchangeInterface {
	
	@Override
	public Map<String,String> checkPoint(String cInfo) {
		return super.checkPoint(cInfo);
	}
	
	@Override
	public Map<String, String> Exchange(String cInfo) {
		return super.Exchange(cInfo);
	}
	
	/**
	 * 校验手机的运营商
	 * 
	 * @param mobile
	 * @return
	 */
	@Override
	public Map<String, String> validateMobile(String mobile) {

		Map<String, String> map = new HashMap<String, String>();
		String returnString = "";
		String returnMessage = "";
		String htmlFilePath = "";
		if (mobile == null || mobile.trim().length() != 11) {
			returnString = "-1";
			returnMessage = "";
			map.put("result", returnString);
			map.put("message", returnMessage);
			return map;
		}
		// 移动
		if (mobile.trim().substring(0, 3).equals("134") || mobile.trim().substring(0, 3).equals("135")
				|| mobile.trim().substring(0, 3).equals("136") || mobile.trim().substring(0, 3).equals("137")
				|| mobile.trim().substring(0, 3).equals("138") || mobile.trim().substring(0, 3).equals("139")
				|| mobile.trim().substring(0, 3).equals("150")
				|| mobile.trim().substring(0, 3).equals("151") || mobile.trim().substring(0, 3).equals("152")
				|| mobile.trim().substring(0, 3).equals("157")
				|| mobile.trim().substring(0, 3).equals("158") || mobile.trim().substring(0, 3).equals("159")
				|| mobile.trim().substring(0, 3).equals("187")
				|| mobile.trim().substring(0, 3).equals("188") || mobile.trim().substring(0, 3).equals("182")
				|| mobile.trim().substring(0, 3).equals("183")
				|| mobile.trim().substring(0, 3).equals("184") || mobile.trim().substring(0, 3).equals("147")
				|| mobile.trim().substring(0, 3).equals("178")) {
			returnString = "1";
			returnMessage = "(中国移动)";
			htmlFilePath = "A";
		}
		// 联通
		if (mobile.trim().substring(0, 3).equals("130") || mobile.trim().substring(0, 3).equals("131")
				|| mobile.trim().substring(0, 3).equals("132") || mobile.trim().substring(0, 3).equals("145")
				|| mobile.trim().substring(0, 3).equals("155") || mobile.trim().substring(0, 3).equals("156")
				|| mobile.trim().substring(0, 3).equals("176")
				|| mobile.trim().substring(0, 3).equals("185") || mobile.trim().substring(0, 3).equals("186")) {
			returnString = "2";
			returnMessage = "(中国联通)";
			htmlFilePath = "B";
		}
		// 电信
		if (mobile.trim().substring(0, 3).equals("133") || mobile.trim().substring(0, 3).equals("153")
				|| mobile.trim().substring(0, 3).equals("180") || mobile.trim().substring(0, 3).equals("181")
				|| mobile.trim().substring(0, 3).equals("177") || mobile.trim().substring(0, 3).equals("189")) {
			returnString = "3";
			returnMessage = "(中国电信)";
			htmlFilePath = "C";
		}
		if (returnString.trim().equals("")) {
			returnString = "0"; // 未知运营商
		}
		map.put("result", returnString);
		map.put("message", returnMessage);
		map.put("htmlFilePath", htmlFilePath);
		return map;
	}
}