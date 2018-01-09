package com.sinosoft.aeonlife;

import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.Constant;
import cn.com.sinosoft.util.CsvFileParser2;
import com.sinosoft.aeonlife.utils.SftpCommon;
import com.sinosoft.aeonlife.utils.ZipUtils;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.schema.PartnerAccountBalanceRecordSchema;
import com.sinosoft.schema.PartnerAccountBalanceRecordSet;
import com.sinosoft.schema.PartnerInfoSchema;
import com.sinosoft.schema.PartnerInfoSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: DealRefundFundsService <br/>
 * Function: 承保资金对账. <br/>
 * date: 2016年9月12日 上午9:23:54 <br/>
 * 
 * @author wwy
 * @version 
 */


public class DealAccountBalanceService {

	private static final Logger logger = LoggerFactory.getLogger(DealAccountBalanceService.class);

	/**
	 * downloadData:读取并保存承保资金对账数据. <br/>
	 *
	 * @author wwy
	 */
	public static void downloadData() {

		try {
			Transaction tr = new Transaction();
			// 获取合作方 读取资金划拨数据
			PartnerInfoSchema pi = new PartnerInfoSchema();
			PartnerInfoSet pset = pi.query(new
					QueryBuilder(" where type = 'asyn' and ftpPath IS NOT NULL and ftpPath <> '' "));
			if (pset.size() > 0) {
				for (int i = 0; i < pset.size(); i++) {
					PartnerAccountBalanceRecordSet pabrSet = new
							PartnerAccountBalanceRecordSet();
					PartnerInfoSchema tempPI = pset.get(i);

					if (Constant.YZT_CHANNELSN.equals(tempPI.getchannelSn())) {
						logger.info("执行一账通{}", tempPI.getchannelSn());
						List<Map<String, String>> data = getDataForYzt(tempPI.getFtpPath(), "");
						logger.info("获得数据{}", String.valueOf(data.size()));
						if (null != data && data.size() > 0) {
							PartnerAccountBalanceRecordSchema pabr = null;
							for (Map<String, String> map : data) {
								pabr = pabrSchemaForYzt(map, tempPI.getchannelSn());
								logger.info("投保人：{}", pabr.getCustermor());
								if(StringUtil.isNotEmpty(pabr.getid())){
									pabrSet.add(pabr);
								}
							}
							tr.add(pabrSet, Transaction.INSERT);
							// 循环内部，避免不同合作方彼此影响
							if (!tr.commit()) {
								logger.error("承保资金对账记录表存储失败，合作方文件地址{}", tempPI.getFtpPath());
							}
							tr.clear();
						}

					} else {
						List<Map<String, String>> data = getData(tempPI.getFtpPath(), "");
						if (null != data && data.size() > 0) {
							PartnerAccountBalanceRecordSchema pabr = null;
							for (Map<String, String> map : data) {
								pabr = pabrSchema(map, tempPI.getchannelSn());
								pabrSet.add(pabr);
							}
							tr.add(pabrSet, Transaction.INSERT);
							// 循环内部，避免不同合作方彼此影响
							if (!tr.commit()) {
								logger.error("承保资金对账记录表存储失败，合作方文件地址{}", tempPI.getFtpPath());
							}
							tr.clear();
						}

					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ActionUtil.sendAlarmMail("理财险保单查询异常，" + e.getMessage());
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
		// ftpPath = "c:" + ftpPath;
		// 退保资金划拨结果
		String path = ftpPath + cfp(SftpCommon.DOWNLOAD, SftpCommon.PATH_ACCOUNTBALANCE);
		// 解压
		ZipUtils.unZipFiles(path);

		File file = new File(path);
		if (!file.exists()) {
			logger.error("文件不存在：{}", file.getPath());
			return null;
		}
		File[] fis = file.listFiles();
		if (fis == null || fis.length == 0) {
			logger.error("{}没有文件.", file.getPath());
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

	//亿账通读取数据（平安付对账文件）
	private static List<Map<String, String>> getDataForYzt(String ftpPath, String encoding) throws Exception {

		// 测试用
		// ftpPath = "c:" + ftpPath;
		// 退保资金划拨结果
		String path = ftpPath + cfp(SftpCommon.DOWNLOAD, SftpCommon.PATH_ACCOUNTBALANCE);
		logger.info("拼接路径{}", path);
		
		ZipUtils.unZipFiles(path);
	
        String channel = SftpCommon.CHANNEL;
        String fileType = SftpCommon.FILETYPE;
        String checkDate= DateUtil.toString(DateUtil.addDay((new Date()), -1), SftpCommon.FORMAT_YMD) ;
        logger.info("日期：{}", checkDate);
        String destPath = path;
        logger.info("路径：{}", destPath);
        
        // 读取md5文件中的MD5字符串
        String md5FileNm = channel + SftpCommon.FILE_SPLIT + fileType + SftpCommon.FILE_SPLIT + checkDate + "_md5";
        logger.info("md5FileNm : {}", md5FileNm);
        String md5Pama = PayCheckUtils.readFile(destPath + md5FileNm);
        logger.info("md5Pama :{}", md5Pama);
        if (md5Pama == null) {
           throw new Exception("没有读取到主账户对账文件的md5文件：" + destPath + md5FileNm);
        }
        // 计算3des文件的MD5值
        String des3FileNm = channel + SftpCommon.FILE_SPLIT + fileType + SftpCommon.FILE_SPLIT + checkDate + "_3des";
        File des3File = new File(destPath + des3FileNm);
        if (!des3File.exists() || des3File.isDirectory()) {
           throw new Exception("没有读取到主账户对账文件的3des文件：" + destPath + des3FileNm);
        }
        String md5Value = PayCheckUtils.getMd5ByFile(des3File);
        logger.info("md5Value :{}", md5Value);
        // 比较2个MD5值判断3des文件是否传完整一致
        if (!md5Pama.equals(md5Value)) {
           throw new Exception("☆☆☆MD5值不一致，对账文件可能被篡改，终止对账！");
        }
        logger.info("比较成功！");
        // 读取RSA文件中的签名串
        String rsaFileNm = channel + SftpCommon.FILE_SPLIT + fileType + SftpCommon.FILE_SPLIT + checkDate + "_rsa";
        String signPama = PayCheckUtils.readFile(destPath + rsaFileNm);
        logger.info("signPama :{}", signPama);
        if (signPama == null) {
           throw new Exception("没有读取到主账户对账文件的rsa文件：" + destPath + rsaFileNm);
        }
		// 验证RSA签名 测试
		// String rsaPubKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCta21Fj0dgD0kVljqX1unNtvA3QfVAwZHbqbhFLR4t1sn0aRt6mqUUW3HKxnMYTYAffw4yrE8H7RqTyjbMIzrHcV+nNEd7qYYCmjXdbB8ly2RIMGKsiARLURsazIsu9mICNJz59f4LJLGF9B0ylbQZP9slXH0ghCUTQuBW+E4ErQIDAQAB";
		// 验证RSA签名 生产
		String rsaPubKey =  Config.getValue("yzt_rsaPubKey");;//"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcOhDB1QhPSz1Y4p3wLpt13jKSRX2qTpVTkxDhoIJ0p5vCi988gUhPwNpqyA1MHm9UIntvMJ02okKlMqjKlCPq1gD3bE2bRsXDWov7eekHwmRfRahAMCQAe6fSoPgS4qWj5aI0IFNKKYYPeFlDzmecQCfAz+2rS+vps1cMUZFo8wIDAQAB";
        byte[] md5ByteArr = md5Pama.getBytes();
        boolean checkSign = PayCheckUtils.verify(md5ByteArr, rsaPubKey, signPama);
        if (!checkSign) {
           throw new Exception("RSA签名不一致，终止对账！");
        }
		// 解密3des文件 测试
		// String keyGenStr = "77f1459a8b66899f1b2aaef0";
		// 解密3des文件 生产
		String keyGenStr = Config.getValue("yzt_keyGenStr");// "dcou6itvvuzjr3lx8otjj28v";
        
        String retFileNm = channel + "_" + fileType + "_" + checkDate + ".zip";
        PayCheckUtils.encryptOrDecryptBy3DES(path + des3FileNm,
                path + retFileNm, keyGenStr, 2);
       
        PayCheckUtils.unzip(path + retFileNm, path);
		
        String retFileNm1 = "PAMA_B0007_" + channel + "_" + checkDate + "_1" + ".txt";
        
        File retFile = new File(path + retFileNm1);
        
		File file = new File(path);
		if (!file.exists()) {
			logger.error("文件不存在：{}", file.getPath());
			return null;
		}
		
		File[] fis = file.listFiles();
//		for (File item : fis) {
//			if(item.getName().endsWith(".txt")){
//				
//			}
//		}
		
		if (fis == null || fis.length == 0) {
			logger.error("{}没有文件.", file.getPath());
			return null;
		}
		
		InputStreamReader read = new InputStreamReader(
				new FileInputStream(retFile), "utf-8");// 考虑到编码格式
		
		
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		ArrayList<String> line = new ArrayList<String>();
		while ((lineTxt = bufferedReader.readLine()) != null) {
			line.add(lineTxt);
		}
		bufferedReader.close();
		read.close();
		logger.info("得到数据:{}", line);
		List<Map<String, String>> allData = new ArrayList<Map<String,String>>();
		for (int i = 1; i < line.size(); i++) {
			Map<String,String> mapStr = new HashMap<String, String>();
			String[] data = line.get(i).split("\\|");
			logger.info("data数据数组长度为{}", String.valueOf(data.length));
			for(int j = 0;j < data.length;j++){
				mapStr.put(String.valueOf(j), data[j]);
			}
			allData.add(mapStr);
		}
		return allData;
	}

	private static String Date(Date addDay) {

		
		// TODO Auto-generated method stub
		return null;
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

	private static PartnerAccountBalanceRecordSchema pabrSchema(Map<String, String> map, String channelsn) {

		PartnerAccountBalanceRecordSchema prfr = new PartnerAccountBalanceRecordSchema();
		prfr.setid(CommonUtil.getUUID());
		prfr.setCustermor(map.get("0"));
		prfr.setBillingTime(map.get("1"));
		prfr.setFundsSn(map.get("2"));
		prfr.setFundTransferSn(map.get("3"));
		prfr.setTradeTime(map.get("4"));
		prfr.setTradeType(map.get("5"));
		prfr.setScene(map.get("6"));
		prfr.setPayAmount(map.get("7"));
		prfr.setIncomeAmount(map.get("8"));
		prfr.setAccountBalance(map.get("9"));
		prfr.setAccountType(map.get("10"));
		prfr.setMerchantSn(map.get("11"));
		prfr.setChannelSn(channelsn);
		prfr.setCreateDate(new Date());

		return prfr;
	}

	// 亿账通对账文件读取（平安付对账文件）
	private static PartnerAccountBalanceRecordSchema pabrSchemaForYzt(Map<String, String> map, String channelsn) {

		PartnerAccountBalanceRecordSchema prfr = new PartnerAccountBalanceRecordSchema();
		if ("00".equals(map.get("22"))) {
			prfr.setid(CommonUtil.getUUID());
			prfr.setCustermor(map.get("12"));
			prfr.setBillingTime(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			prfr.setFundsSn("");
			prfr.setFundTransferSn(map.get("28"));
			prfr.setTradeTime(map.get("26")+map.get("27"));
			prfr.setTradeType("");
			prfr.setScene("");
			prfr.setPayAmount("");
			prfr.setIncomeAmount(String.valueOf(Double.parseDouble((String) map.get("6"))/100));
			prfr.setAccountBalance("");
			prfr.setAccountType("");
			prfr.setMerchantSn("");
			prfr.setChannelSn(channelsn);
			prfr.setCreateDate(new Date());
		}
		return prfr;
	}

}
