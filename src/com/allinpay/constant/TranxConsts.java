/**
 * Project Name:wj-code
 * File Name:TranxConsts.java
 * Package Name:com.allinpay.constant
 * Date:2017年11月14日上午9:23:48
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.allinpay.constant;

import com.sinosoft.framework.Config;

/**
 * ClassName:TranxConsts <br/>
 * Function:TODO 描述类是干什么的. <br/>
 * Date:2017年11月14日 上午9:23:48 <br/>
 *
 * @author:guozc
 */
public class TranxConsts {

	/**
	 * 商户证书
	 */
	public static final String pfxPath = Config.getClassesPath() + "resources/tlpay/20022200001030704-ds.p12";
	/**
	 * 商户证书密码
	 */
	public static final String pfxPassword = "111111";
	/**
	 * 通联公钥
	 */
	public static final String tltcerPath = Config.getClassesPath() + "resources/tlpay/allinpay-pds-ds.cer";
	/**
	 * 商户id
	 */
	public static final String merchantId = "200222000010307";
	/**
	 * 用户名
	 */
	public static final String userName = "20022200001030704";
	/**
	 * 用户密码
	 */
	public static final String userPassword = "111111";
	
	/**
	 * 接口请求地址
	 */
	public static final String INTERFACE_URL = "https://tlt.allinpay.com/aipg/ProcessServlet";
	
	/**
	 * 接口调用成功后返回编码
	 */
	public static final String SUCCESS_CODE = "0000";
	
	/**
	 * 报文日志存储路径
	 */
	public static final String LOG_PATH = "/alidata/log/allipay";
}
