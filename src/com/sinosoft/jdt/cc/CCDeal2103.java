package com.sinosoft.jdt.cc;

import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.jdt.ParseXMLToMapCC;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: CCDeal2103 <br/>
 * Function: 百年理财险小额验签. <br/>
 * date: 2016年5月25日 上午10:21:36 <br/>
 *
 * @author wwy
 * @version
 */
public class CCDeal2103 implements CCDealInterface {

	@Override
	public Map<String, Object> cardCheck(Map<String, Object> paramMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			ParseXMLToMapCC parse = new ParseXMLToMapCC();
			resultMap = parse.cardCheck(paramMap);
		} catch (Exception e) {
			resultMap.put("MSG", "小额验签异常，请联系客服。");
			resultMap.put("passFlag", "nopass");// 标记失败
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> canceltry(Map<String, Object> paramMap) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			ParseXMLToMapCC parse = new ParseXMLToMapCC();
			resultMap = parse.canceltry(paramMap);
		} catch (Exception e) {
			resultMap.put("MSG", "异常，请联系客服。");
			resultMap.put("passFlag", "nopass");// 标记失败
		}
		return resultMap;
	}

	public static void main(String[] args) {

		CCDealInterface ccdi = null;
		try {
			Class<?> tbDeal = Class.forName("com.sinosoft.jdt.cc.CCDeal2103");
			ccdi = (CCDealInterface) tbDeal.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> inputInfo = new HashMap<String, Object>();
		inputInfo.put("ComCode", "2103");
		inputInfo.put("TransCode", "00070002");
		// 订单号加上随机数，保存到小额验签绑定银行卡表中，需要后期做查询用
		//inputInfo.put("BussNo", "2013000011123125" + "cc" + String.valueOf(NoUtil.getMaxNo("CardCheck", 5)));
		inputInfo.put("BussNo", "2013000011123125cc00012");
		// 尹红瑛
		inputInfo.put("AccName", "王文英");
		String transDate = DateUtil.getCurrentDate("yyyy-MM-dd");
		inputInfo.put("TransDate", transDate);
		String transTime = DateUtil.getCurrentDate("HH:mm:ss");
		inputInfo.put("TransTime", transTime);
		inputInfo.put("BankCode", "01");
		// 6222083400006084830 /6212263400017917005
		inputInfo.put("BankAccNo", "6212263400017917005");


	}
}
