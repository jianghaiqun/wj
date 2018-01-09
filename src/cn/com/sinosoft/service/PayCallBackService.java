/**
 * Project Name:wj
 * File Name:PayCallBackService.java
 * Package Name:cn.com.sinosoft.service
 * Date:2016年6月30日下午2:14:07
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.service;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName:PayCallBackService <br/>
 * Function:TODO ADD 支付成功回调. <br/>
 * Date:2016年6月30日 下午2:14:07 <br/>
 *
 * @author:郭斌
 */
public interface PayCallBackService {

	public String doPay(String paySn, String payAmount, String payType, String TrxId, String ChkValue, String btye,
			String openId, HttpServletRequest request);

}
