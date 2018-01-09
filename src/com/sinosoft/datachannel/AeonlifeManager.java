package com.sinosoft.datachannel;

import com.sinosoft.aeonlife.AeonLifeDealRefundFundsService;
import com.sinosoft.aeonlife.CreateRefundApply;
import com.sinosoft.aeonlife.LcbxSearchOrderService;
import com.sinosoft.aeonlife.LoadCSV2List;
import com.sinosoft.aeonlife.utils.CreateFilePath;
import com.sinosoft.aeonlife.utils.SftpCommon;
import com.sinosoft.aeonlife.utils.UnZipFile;
import com.sinosoft.asyninter.RefundApplyResultService;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.schema.ZDCodeSchema;
import com.sinosoft.schema.ZDCodeSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AeonlifeManager extends ConfigEanbleTaskManager {

	public static final String CODE = "com.sinosoft.datachannel.AeonlifeManager";

	public boolean isRunning(long id) {

		return false;
	}

	@SuppressWarnings("rawtypes")
	public Mapx getConfigEnableTasks() {

		Mapx<String, String> map = new Mapx<String, String>();
		//读取投保结果
		map.put("1", "读取投保文件");
		//读取退保结果
		map.put("2", "读取退保文件");
		map.put("3", "读取资产同步文件");
		//提交退保申请
		map.put("4", "提交退保文件");
		map.put("5", "百年理财保单查询");
		map.put("10", "提交退保资金划拨结果文件");
		map.put("11", "百年爆品康惠退保文件读取");
		return map;
	}

	public void execute(long id) {
		// 订单查询
		if (5 == id) {
			LcbxSearchOrderService.deal();
			return;
		}

		// 构建sftp文件路径
		Map<String, List<String>> path_map = CreateFilePath.createFilePath();
		// 解压zip文件
		UnZipFile.unZip(path_map);

		if (4 == id) {
			CreateRefundApply.createRefundApply();
			return;
		}
		
		// 百年和前海的退保和资产同步
		if (2 == id || 3 == id) {
			// 2：退保 3：资产
			String flag = "";
			String flagPath = "";
			if (2 == id) {
				flag = SftpCommon.STR_REFUNDRESULT;
				flagPath = SftpCommon.PATH_REFUNDRESULT;
			} else if (3 == id) {
				flag = SftpCommon.STR_POLICYVALUE;
				flagPath = SftpCommon.PATH_POLICYVALUE;
			}
			
			// 保险公司配置
			ZDCodeSchema code = new ZDCodeSchema();
			ZDCodeSet codeSet = code.query(new QueryBuilder("where CodeType = 'SFTP_PATHS' and ParentCode = 'SFTP_PATHS'"));
			
			for (int i = 0; i < codeSet.size(); i++) {
				code = codeSet.get(i);
				// 路径：如/alidata/sftpFile/sftp_own
				String path = code.getCodeValue();
				
				String descDir = path + CreateFilePath.cfp(SftpCommon.UPLOAD, flagPath);
				
				LoadCSV2List.refundRes(descDir, flag, "");
			}
		}
		
		// 读取退保结果后，同时发送退保结果给合作方
		if (2 == id) {
			RefundApplyResultService refundApplyResultService = new RefundApplyResultService();
			refundApplyResultService.execute();
			return;
		}
		
		Set<String> set = path_map.keySet();
		for (String key : set) {
			List<String> listpath = path_map.get(key);
			String channels = "";
			String reqpath = "";
			String respath = "";

			if (key.contains(SftpCommon.STR_BILL)) {// 快钱
				channels = SftpCommon.CHA_BILL;
			} else if (key.contains(SftpCommon.STR_FEIFAN)) {// 非凡
				channels = SftpCommon.CHA_FEIFAN;
			} else if (key.contains(SftpCommon.STR_QUNA)) {// 去哪
				channels = SftpCommon.CHA_QUNA;
			}
			for (String descDir : listpath) {
				if (1 == id) {
					// 快钱只为落数据用，实际投保直接百年读取。
					if (key.contains(SftpCommon.STR_BILL)) {
						if (descDir.contains(SftpCommon.STR_POLICYREQ)) {
							reqpath = descDir;
						} else if (descDir.contains(SftpCommon.STR_POLICYRESULT)) {
							respath = descDir;
						}
						if (!StringUtil.isEmpty(reqpath) && !StringUtil.isEmpty(respath)) {
							LoadCSV2List.policyReq_Res(reqpath, respath, channels);
							break;
						}
					}
				} else if (2 == id) {
					// 2：退保 
					String flag = SftpCommon.STR_REFUNDRESULT;
					if (descDir.contains(flag)) {
						LoadCSV2List.refundRes(descDir, flag, channels);
					}
				}
			}
		}
		
		if(10 == id){
			AeonLifeDealRefundFundsService.createRefundfunds();
		}
		
		if(11==id){
			// 保险公司配置
			ZDCodeSchema code = new ZDCodeSchema();
			ZDCodeSet codeSet = code.query(new QueryBuilder("where CodeType = 'SFTP_PATHS' and ParentCode = 'SFTP_PATHS' and prop2 = '2248'"));
			for (int i = 0; i < codeSet.size(); i++) {
				code = codeSet.get(i);
				String path = code.getCodeValue();
				String descDir = path + CreateFilePath.cfp(SftpCommon.UPLOAD,  SftpCommon.PATH_POLICYCT)+SftpCommon.STR_POLICYCT+"_"+DateUtil.getCurrentDate(SftpCommon.FORMAT_YMD)+".txt";

				LoadCSV2List.refundResForBaoPin(descDir, SftpCommon.STR_POLICYCT, "");
			}
		}
	}

	public String getCode() {

		return CODE;
	}

	public String getName() {

		return "百年理财保险定时任务";
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
}
