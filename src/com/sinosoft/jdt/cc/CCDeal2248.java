/**
 * 
 */
package com.sinosoft.jdt.cc;

import java.util.HashMap;
import java.util.Map;

import com.sinosoft.jdt.ParseXMLToMapCC;

/**
 * @author wangcaiyun
 *
 */
public class CCDeal2248 implements CCDealInterface {
	@Override
	public Map<String, Object> cardCheck(Map<String, Object> paramMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			ParseXMLToMapCC parse = new ParseXMLToMapCC();
			resultMap = parse.cardCheck(paramMap);
		} catch (Exception e) {
			resultMap.put("MSG", "在线回访异常，请联系客服。");
			resultMap.put("passFlag", "nopass");// 标记失败
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> canceltry(Map<String, Object> paramMap) {
		
		return null;
	}
}
