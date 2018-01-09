/**
 * Project Name:FtpFile File Name:CheckPolicyResult.java Package
 * Name:com.finance.util.aeonlife Date:2016年6月1日下午3:40:19 Copyright (c) 2016,
 * www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.aeonlife;

import com.sinosoft.aeonlife.model.Aeon;
import com.sinosoft.aeonlife.utils.SftpCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:LoadAeonCSV<br/>
 * Function:TODO 根据请求结果取得订单信息. <br/>
 * Date:2016年6月1日 下午3:40:19 <br/>
 *
 * @author:chouweigao
 */
public class CompareCSV {

	private static final Logger logger = LoggerFactory.getLogger(CompareCSV.class);

	
	/**
	 * 
	 * checkPolicyReq:(获得投保结果). <br/>
	 *
	 * @author chouweigao
	 * @param req
	 * @param res
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Object> getResultPolicyReq(List<String> req, List<String> res, String channels) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 遍历req的list
		for (String resstr : res) {
			String[] resarr = arrays(resstr);
			// 取得返回结果【投保订单号】
			String res_ordersn = resarr[0];
			for (String reqstr : req) {
				String[] reqarr = arrays(reqstr);
				// 取得请求【投保订单号】
				String req_ordersn = reqarr[0];
				// 判断请求和请求结果的数据一样
				if (res_ordersn.equals(req_ordersn)) {
					Aeon aeon = new Aeon();
					aeon.setAeonOrderSn(req_ordersn);
					aeon.setAeonName(reqarr[3]);
					aeon.setAeonCardNum(reqarr[4]);
					aeon.setAeonPhone(reqarr[5]);
					aeon.setAeonMail(reqarr[6]);
					aeon.setAeonAdd(reqarr[7]);
					aeon.setAeonAmount(reqarr[8]);
					aeon.setAeonPolicyNo(resarr[1]);
					aeon.setAeonProductName(reqarr[1]);
					aeon.setAeonPolicyPath(resarr[10]);
					aeon.setAeonStartDate(getDate(resarr[7], true));
					aeon.setAeonEndDate(getDate("", false));
					aeon.setAeonPolicyStauts(resarr[3]);
					aeon.setChannels(channels);
					map.put(res_ordersn, aeon);
				}
			}
		}
		return map;
	}

	/**
	 * 
	 * getRefund:(获得保单撤单状态). <br/>
	 *
	 * @author chouweigao
	 * @param req
	 * @param res
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Object> getRefund(List<String> listRefunds, String flag, String channels) {

		Map<String, Object> map = new HashMap<String, Object>();
		int inx = 0;
		int can_date = 0;
		// 总额
		int t = 0;
		// 本金
		int p = 0;
		// 利息
		int i = 0;

		boolean isRes = false;
		// 根据不同的文件获得不同的列的结果 列的结果为保单撤单状态
		if (flag.equals(SftpCommon.STR_POLICYVALUE)) {
			inx = 3;
			t = 4;
			p = 5;
			i = 6;
			can_date = 8;
		} else if (flag.equals(SftpCommon.STR_REFUNDRESULT)) {
			inx = 4;
			can_date = 10;
			t = 6;
			p = 7;
			i = 8;
			isRes = true;
		}

		// 遍历req的list
		for (String ref : listRefunds) {
			String[] refarr = arrays(ref);
			String req_ordersn = refarr[0].split("_")[0];
			String order_status = refarr[inx];
			String candate = refarr[can_date];
			String total = refarr[t];
			String principal = refarr[p];
			String income = refarr[i];

			/**
			 * refundResult 退保结果：1成功 0失败 policyValue 资产同步：1承保成功 2线下退保/理赔
			 */
			if (isRes || (!isRes && order_status.equals("2"))) {
				Aeon aeon = new Aeon();
				aeon.setAeonOrderSn(req_ordersn);
				aeon.setCancelDate(getDate(candate, true));
				aeon.setTotal(total);
				aeon.setPrincipal(principal);
				aeon.setIncome(income);
				aeon.setChannels(channels);
				// 退保结果 0:失败 1:成功 2线下退保/理赔
				aeon.setAeonPolicyResult(order_status);
				if (isRes) {
					// 手续费
					aeon.setFee(refarr[9]);
					// 退保还款类型 1：犹豫期外不收手续费 2：犹豫期外收手续费 4：犹豫期内不收手续费。
					aeon.setReturnType(refarr[5]);
					// 退保失败原因
					aeon.setAeonMsg(refarr[11]);
					// 保全号
					aeon.setAeonPolicyNo(refarr[2]);
				} else {
					// 描述
					aeon.setAeonMsg(refarr[9]);
				}

				map.put(req_ordersn, aeon);
			}
		}
		return map;
	}

	/**
	 * 
	 * getPolicyValue:(资产同步). <br/>
	 * 
	 * @author chouweigao
	 * @param listRefunds
	 * @param channels
	 * @return
	 */
	public static Map<String, Object> getPolicyValue(List<String> listPolicyValue, String channels) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 读取资产同步文件的全部属性
		for (String vals : listPolicyValue) {
			String[] arr = arrays(vals);
			// 截取ordersn的_
			String ordersn = arr[0].split("_")[0];
			String policyno = arr[1];
			String orderstatus = arr[3];
			String total = arr[4];
			String principal = arr[5];
			String income = arr[6];
			String todayincome = "";
			String startdate = "";
			// 避免版本不一致 增加当日收益
			if (arr.length > 8) {
				todayincome = arr[7];
				startdate = arr[8];
			} else {
				startdate = arr[7];
			}

			if (orderstatus.equals("1")) {
				// 保存资产同步对象
				Aeon aeon = new Aeon();
				aeon.setAeonOrderSn(ordersn);
				aeon.setAeonPolicyNo(policyno);
				aeon.setAeonPolicyStauts(orderstatus);
				aeon.setTotal(total);
				aeon.setIncome(income);
				aeon.setPrincipal(principal);
				aeon.setChannels(channels);
				aeon.setAeonStartDate(getDate(startdate, true));
				aeon.setTodayincome(todayincome);
				map.put(ordersn, aeon);
			}
		}
		return map;
	}

	/**
	 * 分隔csv文件中的行.
	 * arrays
	 * 原方法采用先取出引号,在用逗号分隔,因此会出现,数据分隔错误,或者,前后空串数据丢失.
	 * 由于该方法引用地方较多.
	 * 我为了尽可能不改变前面开发者所写的代码.采用再原有基础上最小成本的保证功能的正常使用,但这种方式并不安全.
	 * 修改后,利用引号的包裹性,使用(",")先进行分割,再对开头结尾的数据去除引号.
	 * modified by:董升.
	 */
	private static String[] arrays(String str) {

		String[] strs = str.split("\",\"");
		if (strs.length > 0) {
			str = strs[0].replace(SftpCommon.REPLACE_STR,
					SftpCommon.SPACE_STR);
			strs[0] = str;
			str = strs[strs.length - 1];
			strs[strs.length - 1] = str.replace(SftpCommon.REPLACE_STR,
					SftpCommon.SPACE_STR);
		}
		return strs;

	}

	/**
	 * 
	 * getDate:(字符串转时间类型). <br/>
	 *
	 * @author chouweigao
	 * @param date
	 * @param isStart
	 * @return
	 * @throws ParseException
	 */
	private static Date getDate(String date, boolean isStart) {

		String format = "";
		if (isStart) {
			format = SftpCommon.FORMAT_YMD;
		} else {
			date = "9999-01-01 23:59:59";
			format = SftpCommon.FORMAT_YMDHMS;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		Date date1 = null;

		try {
			date1 = sdf.parse(date);
		} catch (ParseException e) {
			logger.error("时间类型转换出错:" + e.getMessage(), e);
		}
		return date1;
	}

}
