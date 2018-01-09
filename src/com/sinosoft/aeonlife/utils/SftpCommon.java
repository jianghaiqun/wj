/**
 * Project Name:wj
 * File Name:SftpCommon.java
 * Package Name:com.sinosoft.aeonlife.utils
 * Date:2016年6月12日上午9:26:16
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.aeonlife.utils;

import com.sinosoft.framework.utility.DateUtil;

/**
 * ClassName:SftpCommon <br/>
 * Function:TODO 描述类是干什么的. <br/>
 * Date:2016年6月12日 上午9:26:16 <br/>
 *
 * @author:chouweigao 
 */
public class SftpCommon {
	public static final String DOWNLOAD = "/download/";
	public static final String DOWNLOAD_BAK_WJ = "/download_bak_wj/";
	public static final String UPLOAD = "/upload/";
	
	public static final String STR_POLICYREQ = "policyReq";
	public static final String STR_POLICYCT = "policyCT";
	public static final String STR_POLICYRESULT = "policyResult";
	public static final String STR_REFUNDAPPLY = "refundApply";
	public static final String STR_REFUNDRESULT = "refundResult";
	public static final String STR_REFUNDFUNDS = "refundFunds";
	public static final String STR_POLICYVALUE = "policyValue";
	public static final String STR_ACCOUNTBALANCE = "accountBalance";
	
	
	public static String FORMAT_YMD = "yyyyMMdd";
	public static String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String SPLIT_STR = ",";
	public static final String REPLACE_STR = "\"";
	public static final String SPACE_STR = "";
	public static final String AEONLIFE = "aeonlife";
	public static final String FILE_SPLIT = "_";
	public static final String CSV = ".csv";
	public static final String ZIP = ".zip";
	
	/**读取配置项*/
	public static final String FINANCING = "FinancingList";
	public static final String SFTP_PATHS = "SFTP_PATHS";
	
	/**渠道*/
	public static final String CHA_BILL = "cps_bill";
	public static final String CHA_FEIFAN = "cpsffanflc";
	public static final String CHA_OWN = "cps_own";
	public static final String CHA_QUNA = "cps_quna";
	
	/**渠道字符串*/
	public static final String STR_BILL = "bill";
	public static final String STR_FEIFAN = "ffan";
	public static final String STR_OWN = "own";
	public static final String STR_QUNA = "quna";
	public static final String STR_FSL = "sftp_fsl";
	
	/**投保申请文件*/
	public static final String PATH_POLICYREQ = "/"+STR_POLICYREQ+"/";
	/**投保结果文件*/
	public static final String PATH_POLICYRESULT = "/"+STR_POLICYRESULT+"/";
	/**退保申请文件*/
	public static final String PATH_REFUNDAPPLY = "/"+STR_REFUNDAPPLY+"/";
	/**退保结果文件*/
	public static final String PATH_REFUNDRESULT = "/"+STR_REFUNDRESULT+"/";
	/**百年爆品退保结果文件*/
	public static final String PATH_POLICYCT = "/"+STR_POLICYCT+"/";
	/**资金划拨文件*/
	public static final String PATH_REFUNDFUNDS = "/"+STR_REFUNDFUNDS+"/";
	/**用户资产同步*/
	public static final String PATH_POLICYVALUE = "/"+STR_POLICYVALUE+"/";
	/**对账文件*/
	public static final String PATH_ACCOUNTBALANCE = "/"+STR_ACCOUNTBALANCE+"/";
	/**当前时间*/
	public static String CURDATE = DateUtil.getCurrentDate(FORMAT_YMD);
	
	/**亿账通对账文件解密用字段*/
	public static final String CHANNEL = "900000021857";
	public static final String FILETYPE = "CF";
	
}

