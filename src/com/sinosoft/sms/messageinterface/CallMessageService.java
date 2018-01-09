package com.sinosoft.sms.messageinterface;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.sms.messageinterface.parse.MessageCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author周翔
 * @Date 2012-7-31
 * @Mail zhouxiang@sinosoft.com.cn
 */

public class CallMessageService {
	private static final Logger logger = LoggerFactory.getLogger(CallMessageService.class);
	private static MessageServiceFactory cMessageServiceFactory = MessageServiceFactory.newInstance();

	@SuppressWarnings("static-access")
	public boolean CallMessageService(Mapx<String, Object> mMap, String tServiceCode) {
		logger.info("短信接口服务开始");
		boolean SuccFlag = false;
		try {
			MessageService service = cMessageServiceFactory.getService();
			SuccFlag = service.doService(mMap, tServiceCode);
			if (SuccFlag == false) {
				//抛错误
				return false;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("短信接口服务结束");
		return true;
	}
	public static void main(String[] args) {
		Mapx<String, Object> mMap=new Mapx<String, Object>();
		
		
		Mapx<String, Object> tTransferData=new Mapx<String, Object>();
		//VData tVData = new VData();
		//TransferData tTransferData = new TransferData();
//		GlobalInput tGlobalInput = new GlobalInput();
//		tGlobalInput.ComCode = "86";
//		tGlobalInput.Operator = "001";

		// servicecode = LIS000002
		// tTransferData.setNameAndValue(MessageCode.SERIALNO,"00000000000000001314");
		// tTransferData.setNameAndValue(MessageCode.DEALTYPE,"S");
		// tTransferData.setNameAndValue(MessageCode.PAYCODE,"1016010000006668");
		// tTransferData.setNameAndValue(MessageCode.POLNO,"1001200808280001");

		// servicecode = LIS000003
		// tTransferData.setNameAndValue(MessageCode.PRTNO,"2009040300000001");

		// servicecode = LIS000001
		// tTransferData.setNameAndValue(MessageCode.PRTNO,"2009051300000003");

		// servicecode = LIS000005
		// tTransferData.setNameAndValue(MessageCode.PRTSEQ,"1055010000008538");

		// servicecode = 0006
		// tTransferData.setNameAndValue(MessageCode.CLMNO,"8060000000004768");

		// servicecode = LIS000009
		// tTransferData.setNameAndValue(MessageCode.EDORACCEPTNO,"8041000000000468");
		// tTransferData.setNameAndValue(MessageCode.EDORTYPE,"BM");

//		tTransferData.put(MessageCode.SERIALNO, "00000000000000001828");
//		tTransferData.put(MessageCode.PAYCODE, "2492");
//		tTransferData.put(MessageCode.POLNO, "27");
//		tTransferData.put(MessageCode.DEALTYPE, "S");
		
		
		tTransferData.put(MessageCode.MOBILENUM, "15201094470");//存储信息
		tTransferData.put(MessageCode.MEMBERNAME, "zhou");
		tTransferData.put(MessageCode.MANAGECOM, "86");
		tTransferData.put(MessageCode.FIXEDDATE, "2012-08-15");
		tTransferData.put(MessageCode.FIXEDTIME, "10:30:00");

		// tTransferData.setNameAndValue(MessageCode.AGENTNAME,"刘峰");
		// tTransferData.setNameAndValue(MessageCode.APPTYPE,"1");
		// tTransferData.setNameAndValue(MessageCode.OTHERNOTYPE,"3");

		// servicecode = 0010
		// tTransferData.setNameAndValue(MessageCode.AGENTCODE,"1210000001");
		// tTransferData.setNameAndValue(MessageCode.CONTNO,"8026000000011018");

		//tVData.add(tGlobalInput);
		mMap.put("TransferData",tTransferData);
		CallMessageService tCallMessageService = new CallMessageService();
		if (!tCallMessageService.CallMessageService(mMap, "WF0001 ")) {
			logger.error("操作失败！");
		}

	}

}