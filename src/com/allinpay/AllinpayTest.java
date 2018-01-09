/**
 * Project Name:wj-code
 * File Name:Test.java
 * Package Name:com.allinpay.bean
 * Date:2017年11月14日上午9:04:08
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.allinpay;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.allinpay.bean.Rnpa;
import com.allinpay.bean.Trans;
import com.allinpay.service.AllinpayService;
import cn.com.sinosoft.util.Constant;

/**
 * ClassName:Test <br/>
 * Function:TODO 通联支付接口测试. <br/>
 * Date:2017年11月14日 上午9:04:08 <br/>
 *
 * @author:guozc
 */
public class AllinpayTest {
	private static final Logger LOG = LoggerFactory.getLogger(AllinpayTest.class);

	public static void main(String[] args) throws Exception {
		AllinpayService allinpayService = new AllinpayService();

		Map<String, Object> ret = null;
		String resultCode = null;
		String msg = null;
		String status = null;

		// 实名付申请流水号
		String applyReqSn = "200222000010307-1511508906770"; //

		// 实名付申请
		Rnpa rnpa = new Rnpa();
		rnpa.setBANK_CODE("0301");
		rnpa.setACCOUNT_NO("6222620410004213414");
		rnpa.setACCOUNT_NAME("国志超");
		rnpa.setID_TYPE("0");
		rnpa.setID("152223198805250039");
		rnpa.setTEL("18642856168");
		ret = allinpayService.payApply(rnpa);
		resultCode = (String) ret.get(Constant.RESULTCODE);
		msg = (String) ret.get(Constant.MSG);
		status = (String) ret.get(Constant.STATUS);
		if (Constant.SUCCESS.equals(status)) {
			applyReqSn = (String) ret.get(Constant.DATA);
			LOG.info("实名付申请成功,流水号是:{}", applyReqSn);
		} else {
			LOG.error("实名付申请失败,返回状态码:{},错误描述:{}", resultCode, msg);
			return;
		}

		// // 实名付短信重发
		// ret = AllinpayService.payApplyAgainByRealName(applyReqSn);
		// resultCode = (String) ret.get(Constant.RESULTCODE);
		// msg = (String) ret.get(Constant.MSG);
		// status = (String) ret.get(Constant.STATUS);
		// if (Constant.SUCCESS.equals(status)) {
		// LOG.info("实名付短信重发成功");
		// applyReqSn = (String) ret.get(Constant.DATA);
		// } else {
		// if ("4001".equals(resultCode) || "4002".equals(resultCode)) {
		// LOG.info("已验证成功或无需验证,继续往下走");
		// return;
		// } else {
		// LOG.error("实名付短信重发失败,返回状态码:{},错误描述:{}", resultCode, msg);
		// return;
		// }
		// }
		//
		// // 实名付确认
//		ret = allinpayService.payConfirm(applyReqSn, "8888");
//		resultCode = (String) ret.get(Constant.RESULTCODE);
//		msg = (String) ret.get(Constant.MSG);
//		status = (String) ret.get(Constant.STATUS);
//		if (Constant.SUCCESS.equals(status)) {
//			LOG.info("实名确认成功");
//		} else {
//			if ("4001".equals(resultCode) || "4002".equals(resultCode)) {
//				LOG.info("已验证成功或无需验证,继续往下走");
//			} else {
//				LOG.error("实名确认失败,返回状态码:{},错误描述:{}", resultCode, msg);
//				return;
//			}
//		}
		// //
		// // // 单笔代收
		// Trans trans = new Trans();
		// trans.setACCOUNT_NAME("国志超");
		// trans.setACCOUNT_NO("6222620410004213414");
		// trans.setACCOUNT_PROP("0");
		// trans.setAMOUNT("1");
		// trans.setBANK_CODE("0301");
		// trans.setCURRENCY("CNY");
		// // trans.setCUST_USERID("252523524253xx");
		// trans.setTEL("");
		// ret = AllinpayService.singleTranx(trans);
		// if (Constant.SUCCESS.equals(ret.get(Constant.STATUS))) {
		// LOG.info("支付成功");
		// } else {
		// if ("WAIT_POLLING".equals(ret.get(Constant.RESULTCODE))) {
		// LOG.info("轮询交易结果查询");
		// }
		// }

	}

}
