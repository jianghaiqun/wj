package com.sinosoft.sms.messageinterface;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.sms.messageinterface.business.MessageBusiness;
import com.sinosoft.sms.messageinterface.business.MessageBusinessFactory;
import com.sinosoft.sms.messageinterface.parse.MessageCode;
import com.sinosoft.sms.messageinterface.parse.MessageParse;
import com.sinosoft.sms.messageinterface.parse.MessageParseFactory;
import com.sinosoft.sms.messageinterface.process.MessageSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author周翔
 * @Date 2012-7-31
 * @Mail zhouxiang@sinosoft.com.cn
 */

public class MessageSinoService implements MessageService {
	private static final Logger logger = LoggerFactory.getLogger(MessageSinoService.class);
	private Mapx<String, Object> mBusinessResult = new Mapx<String, Object>();

	private Mapx<String, Object> mParseResult = new Mapx<String, Object>();

	// private VData mBusinessResult = new VData();

	/** 发送方式：0-及时发送，1-定时发送 */
	private String SendWay = "";

	// VData mParseResult = new VData();

	private MessageBusinessFactory mMessageBusinessFactory = MessageBusinessFactory.newInstance();

	private MessageParse mMessageParse = MessageParseFactory.newInstance().getParse();

	public boolean doService(Mapx<String, Object> mMap, String tServiceCode) {
		MessageBusiness tMessageBusiness = mMessageBusinessFactory.getBusiness(tServiceCode);//获取短信类型类 查询limessageservice
		SendWay = mMessageBusinessFactory.getSendWay();
		logger.info("SendWay=={}", SendWay);
		if (tMessageBusiness == null) {
			logger.error("该服务代码没有找到相应业务处理");// 错误信息
			return false;
		} else {
			try {
				if (!tMessageBusiness.submitData(mMap, tServiceCode)) {
					// this.mErrors.copyAllErrors(tMessageBusiness.mErrors);
					return false;
				}
			} catch (Exception ex) {
				// this.mErrors.addOneError("服务处理失败");
				logger.error(ex.getMessage());
				return false;
			}
		}
		mBusinessResult = tMessageBusiness.getResult();
		try {

			if (checkVData(mBusinessResult))// 检验手机号码的正确性---Added by ZhuTao Started----2010-6-23----//
			{
//				if (SendWay.equals("0")) {//定时通过即时发送
					MessageSend mMessageSend = new MessageSend();
					mParseResult = mMessageParse.parseVData(mBusinessResult, tServiceCode);
					if (!mMessageSend.submitData(mParseResult, SendWay)) {
						// this.mErrors.copyAllErrors(mMessageSend.mErrors);
						return false;
					}
//				} else {
//					MessageBatchDeal mMessageBatchDeal = new MessageBatchDeal();
//					if (!mMessageBatchDeal.submitData(mBusinessResult, tServiceCode)) {
//						// this.mErrors.copyAllErrors(mMessageBatchDeal.mErrors);
//						return false;
//					}
//
//				}
			}
		} catch (Exception e) {
			// this.mErrors.addOneError("服务处理失败");
			logger.error(e.getMessage(), e);
			return false;
		}

		return true;
	}

	/**
	 * 检验并去除VDATA中无效手机号码的数据
	 */
	@SuppressWarnings("unchecked")
	public boolean checkVData(Mapx<String, Object> mMap) {
		Mapx<String, Object> transferData = (Mapx<String, Object>) mMap.get("TransferData");
		// TransferData transferData = (TransferFilter) tVData.getObjectByObjectName("TransferData", 0);
		if (transferData == null || transferData.size() < 1) {
			return false;

		} else {
			// 获得手机号码
			String mobile = (String) transferData.get(MessageCode.MOBILENUM);
			if (mobile == null || (mobile.length() != 11) || !"1".equals(mobile.substring(0, 1))) {
				return false;
			}

		}
		return true;
	}

}