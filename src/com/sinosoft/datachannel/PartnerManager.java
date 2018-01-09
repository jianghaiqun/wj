package com.sinosoft.datachannel;

import com.sinosoft.aeonlife.DealAccountBalanceService;
import com.sinosoft.aeonlife.DealRefundFundsService;
import com.sinosoft.aeonlife.LoadCSV2List;
import com.sinosoft.aeonlife.utils.SftpCommon;
import com.sinosoft.asyninter.InsureApplyDocumentService;
import com.sinosoft.asyninter.RefundApplyResultService;
import com.sinosoft.asyninter.RefundApplyService;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

import java.io.IOException;

public class PartnerManager extends ConfigEanbleTaskManager {

	public static final String CODE = "com.sinosoft.datachannel.PartnerManager";

	public boolean isRunning(long id) {

		return false;
	}

	@SuppressWarnings("rawtypes")
	public Mapx getConfigEnableTasks() {

		Mapx<String, String> map = new Mapx<String, String>();
		map.put("1", "读取投保申请文件");
		map.put("2", "提交投保结果文件");
		map.put("3", "提交资产同步文件");
		map.put("4", "读取退保申请文件");
		map.put("5", "提交退保结果文件");
		map.put("10", "读取退保资金划拨结果文件");
		map.put("11", "读取承保资金对账文件");
		map.put("12", "提交投保申请文件");
		map.put("13", "读取投保结果文件");

		return map;
	}

	public void execute(long id) {

		// 读取合作方退保资金划拨结果文件
		if (id == 1) {
			InsureApplyDocumentService insureApplyDocumentService = new InsureApplyDocumentService();
			try {
				insureApplyDocumentService.getInsureApplyFromPartner();
			} catch (IOException e) {
				logger.error("读取投保申请文件定时任务出错" + e.getMessage(), e);

			}

		}
		if (id == 2) {
			InsureApplyDocumentService insureApplyDocumentService = new InsureApplyDocumentService();
			try {
				insureApplyDocumentService.putInsureResultToPartner();
			} catch (IOException e) {
				logger.error("提交投保结果文件定时任务出错" + e.getMessage(), e);
			}

		}
		if (id == 3) {
			InsureApplyDocumentService insureApplyDocumentService = new InsureApplyDocumentService();
			try {
				insureApplyDocumentService.putFinancingToPartner();
			} catch (IOException e) {
				logger.error("提交资产同步文件定时任务出错" + e.getMessage(), e);

			}

		}
		if (id == 4) {
			RefundApplyService refundApplyService = new RefundApplyService();
			refundApplyService.execute();
			return;
		}
		if (id == 5) {
			RefundApplyResultService refundApplyResultService = new RefundApplyResultService();
			refundApplyResultService.execute();
			return;
		}
		if (10 == id) {
			DealRefundFundsService.downloadData();
			return;
		}
		// 读取承保资金对账文件
		if (11 == id) {
			DealAccountBalanceService.downloadData();
			return;
		} else {
			logger.info("处理异步理财保险定时任务，未知定时任务");
		}
		if (id == 12) {
			InsureApplyDocumentService insureApplyDocumentService = new InsureApplyDocumentService();
			try {
				insureApplyDocumentService.putInsureApplyToEachInsuranceCompany();
			} catch (IOException e) {
				logger.error("提交投保申请文件定时任务出错" + e.getMessage(), e);

			}

		}
		if (id == 13) {
			InsureApplyDocumentService insureApplyDocumentService = new InsureApplyDocumentService();
			try {
				insureApplyDocumentService.getInsureResultFromEachInsuranceCompany();
			} catch (IOException e) {
				logger.error("读取投保结果文件定时任务出错" + e.getMessage(), e);
			}
			
			try {
				insureApplyDocumentService.putInsureResultToPartner();
			} catch (IOException e) {
				logger.error("提交投保结果文件定时任务出错" + e.getMessage(), e);
			}
		}
	}

	public String getCode() {

		return CODE;
	}

	public String getName() {

		return "处理异步理财保险定时任务";
	}

	@Override
	public void execute(String paramString) {

		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {

		return false;
	}

	@Override
	public String getID() {

		return CODE;
	}

	public static void main(String[] args) {

		LoadCSV2List.refundRes("c:/alidata/sftpFinance/sftp_own/upload/20160906/refundResult/",
				SftpCommon.STR_REFUNDRESULT, "cps_own");

	}
}
