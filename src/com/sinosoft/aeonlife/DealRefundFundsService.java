package com.sinosoft.aeonlife;

import cn.com.sinosoft.util.CsvFileParser2;
import com.sinosoft.aeonlife.utils.SftpCommon;
import com.sinosoft.aeonlife.utils.ZipUtils;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.schema.PartnerInfoSchema;
import com.sinosoft.schema.PartnerInfoSet;
import com.sinosoft.schema.PartnerRefundFundsRecordSchema;
import com.sinosoft.schema.PartnerRefundFundsRecordSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DealRefundFundsService <br/>
 * Function: 退保资金划拨结果. <br/>
 * date: 2016年9月12日 上午9:23:54 <br/>
 *
 * @author wwy
 * @version 
 */
public class DealRefundFundsService {

	private static final Logger logger = LoggerFactory.getLogger(DealRefundFundsService.class);
	
	/**
	 * downloadData:读取并保存资金划拨数据. <br/>
	 *
	 * @author wwy
	 */
	public static void downloadData(){
		try {
			Transaction tr = new Transaction();
			
			// 获取合作方 读取资金划拨数据
			PartnerInfoSchema pi = new PartnerInfoSchema();
			PartnerInfoSet pset = pi.query(new QueryBuilder(" where type = 'asyn' and ftpPath IS NOT NULL and ftpPath <> '' "));
			
			if (pset.size() > 0) {
				for (int i = 0; i < pset.size(); i++) {
					PartnerRefundFundsRecordSet prfrSet = new PartnerRefundFundsRecordSet();
					PartnerInfoSchema tempPI = pset.get(i);
					List<Map<String, String>> data = getData(tempPI.getFtpPath(), "UTF-8");
					if (null != data && data.size() > 0) {
						PartnerRefundFundsRecordSchema prfr = null;
						for (Map<String, String> map : data) {
							prfr = prfrSchema(map, tempPI.getchannelSn());
							
							prfrSet.add(prfr);
						}
						tr.add(prfrSet, Transaction.DELETE_AND_INSERT);
						
						if (!tr.commit()) {
							logger.error("资金划拨数据记录表存储失败");
						}
						tr.clear();
					}
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ActionUtil.sendAlarmMail("读取并保存资金划拨数据异常，" + e.getMessage());
		}
	}
	
	/**
	 * getData:获取退保资金划拨结果. <br/>
	 *
	 * @author wwy
	 * @param ftpPath
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	private static List<Map<String, String>> getData(String ftpPath, String encoding) throws Exception {
		
		// 测试用
		//ftpPath = "c:" + ftpPath;
		// 退保资金划拨结果
		String path = ftpPath + cfp(SftpCommon.DOWNLOAD, SftpCommon.PATH_REFUNDFUNDS);
		// 解压
		ZipUtils.unZipFiles(path);
		
		File file = new File(path);
		if (!file.exists()) {
			logger.error("文件不存在：{}", file.getPath());
			return null;
		}
		File[] fis = file.listFiles();
		if (fis == null || fis.length == 0) {
			logger.error("{}没有文件.",file.getPath());
			return null;
		}
		
		List<Map<String, String>> allData = new ArrayList<Map<String, String>>();
		CsvFileParser2 cfp = new CsvFileParser2();
		for (File tempFile : fis) {
			String name = tempFile.getName();
			if (name.endsWith(SftpCommon.CSV)) {
				allData.addAll(cfp.ReadFileToList(tempFile, encoding));
			}
		}

		return allData;
	}
	
	/**
	 * cfp:拼接zip文件路径. <br/>
	 *
	 * @author wwy
	 * @param root
	 * @param buis
	 * @return
	 */
	private static String cfp(String root, String buis) {

		StringBuffer sb = new StringBuffer();
		sb.append(root);
		sb.append(DateUtil.getCurrentDate(SftpCommon.FORMAT_YMD));
		sb.append(buis);
		return sb.toString();
	}
	
	/**
	 * prfrSchema:封装资金划拨对象. <br/>
	 *
	 * @author wwy
	 * @param map
	 * @param channelsn
	 * @return
	 */
	private static PartnerRefundFundsRecordSchema prfrSchema (Map<String, String> map, String channelsn) {
		PartnerRefundFundsRecordSchema prfr = new PartnerRefundFundsRecordSchema();
		
		prfr.setPOrderSn(map.get("0"));
		prfr.setRefundAplaySn(map.get("1"));
		prfr.setResultRemarkSn(map.get("2"));
		prfr.setAplaySn(map.get("3"));
		prfr.setRiskcode(map.get("4"));
		prfr.setTotal(map.get("5"));
		prfr.setPrincipal(map.get("6"));
		prfr.setIncome(map.get("7"));
		prfr.setFee(map.get("8"));
		prfr.setTradeDate(map.get("9"));
		prfr.setDealResult(map.get("10"));
		prfr.setDealResultDesc(map.get("11"));
		prfr.setChannelsn(channelsn);
		prfr.setCreateDate(new Date());
		
		return prfr;
	}
	
}

