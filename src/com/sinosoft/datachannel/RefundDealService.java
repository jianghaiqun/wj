package com.sinosoft.datachannel;

import cn.com.sinosoft.util.ExcelWriteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.ZipUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.PolicyChangeInfoSchema;
import com.sinosoft.schema.PolicyChangeInfoSet;
import com.sinosoft.schema.RefundFileInfoSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class RefundDealService{
	private static final Logger logger = LoggerFactory.getLogger(RefundDealService.class);

	public String refundFileCreate (String startDate, String endDate) {
		
		try {
			String sql = " SELECT distinct a.datatype,a.orderSn,a.paySn,a.refundAmount,a.payType,a.insureDate,a.policyNo,a.cancelDate,a.channelsn, a.tradeCheckSeriNo, a.tradeSeriNO, a.applicantName FROM ( "
					+ " SELECT 1 AS datatype, o.orderSn AS orderSn, o.paySn AS paySn , "
					// 退款总额减去 淘宝返现金额
					+ " (CONVERT(t.payPrice, DECIMAL(10,2)) - IFNULL(CONVERT((SELECT i.RefundAmount FROM InitiateRefund i WHERE i.RefundType = '0' AND i.OrderSn = o.orderSn AND i.PolicyNo = t.policyNo), DECIMAL(10,2)), 0)) + '' AS refundAmount, "
					+ " o.payType AS payType, t.insureDate AS insureDate, t.policyNo AS policyNo, t.cancelDate AS cancelDate, o.channelsn as channelsn, tr.tradeCheckSeriNo, tr.tradeSeriNO, appnt.applicantName "
					+ " FROM sdorders o, sdinformationrisktype t, tradeinformation tr, sdinformation info, sdinformationappnt appnt WHERE o.orderStatus > 7 AND o.orderStatus NOT IN ('8','13','14') "
					+ " AND t.appStatus in ('2','3','4') AND o.ordersn = t.ordersn AND o.ordersn = info.ordersn "
					+ " AND o.ordersn = tr.ordId "
					+ " AND info.informationSn = appnt.informationSn "
					// 排除淘宝刷单
					+ " AND o.channelsn <> 'tbsd' "
					+ " AND (t.cancelDate IS NOT NULL OR t.cancelDate <> '') "
					+ " AND NOT EXISTS "
					// 排除 代客支付数据
					+ " (SELECT 1 FROM sdremark r WHERE o.ordersn = r.ordersn AND "
					+ " (r.remark LIKE '%代客投保%' OR r.remark LIKE '%重出订单%' "
					+ " OR r.remark LIKE '%协助客户投保%' OR r.remark LIKE '%代客出单%' OR r.remark LIKE '%代客支付%' OR r.remark LIKE '%返还代支付%' )) "
					+ " AND NOT EXISTS "
					+ " (SELECT 1 FROM BuyForCustomerOldSn b WHERE ((b.ordersn = o.ordersn AND b.BuyForCustomerFlag IN ('Y','N')) OR find_in_set(o.ordersn, b.OldOrderSn))) "
					// 排除特定投保人证件号数据 测试用的证件号
					+ " AND NOT EXISTS "
					+ " (SELECT 1 FROM sdinformationappnt appnt WHERE appnt.informationsn = info.informationsn AND appnt.applicantIdentityId like 'kxb%') "
					// 合并手动发起退款部分
					+ " UNION ALL "
					+ " SELECT 2 AS datatype, o.orderSn AS orderSn, o.paySn AS paySn, i.RefundAmount AS refundAmount, "
					+ " o.payType AS payType, t.insureDate AS insureDate, t.policyNo AS policyNo, i.AddTime AS cancelDate, o.ChannelSn as channelsn, tr.tradeCheckSeriNo, tr.tradeSeriNO, appnt.applicantName "
					+ " FROM InitiateRefund i JOIN sdorders o ON i.OrderSn = o.orderSn "
					+ " JOIN sdinformationrisktype t ON i.orderSn = t.orderSn AND i.policyNo = t.policyNo "
					+ " JOIN sdinformation info ON info.orderSn = o.orderSn "
					+ " JOIN sdinformationappnt appnt ON info.informationSn = appnt.informationSn "
					// 淘宝测试数据删除掉 需要手动退款，根据提交的 支付宝账号和支付宝名称
					+ " JOIN tradeinformation tr ON o.orderSn = tr.ordId WHERE i.Approval = '1' AND i.RefundType <> '2') a WHERE 1=1 "
					+ " AND (a.paySn <> NULL OR a.paySn <> '') AND (a.policyNo <> NULL OR a.policyNo <> '') ";
			
			if (StringUtil.isNotEmpty(startDate)) {
				sql += " AND UNIX_TIMESTAMP(DATE_FORMAT(a.cancelDate,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT('" + startDate.trim() + "','%Y-%m-%d %H:%i:%s')) ";
			}
			if (StringUtil.isNotEmpty(endDate)) {
				sql += " AND UNIX_TIMESTAMP(DATE_FORMAT(a.cancelDate,'%Y-%m-%d %H:%i:%s'))<UNIX_TIMESTAMP(DATE_FORMAT('" + endDate.trim() + "','%Y-%m-%d %H:%i:%s')) ";
			}
			// 排序
			sql += " order by a.paySn ";
			
			// @ TODO 处理已经生成退款文件的订单信息。 需要在本次查询中删除掉。 (暂时没有去重对应，查询时间为过去时间，数据唯一固定，没有增加)
			
			QueryBuilder qb = new QueryBuilder(sql);
			DataTable dt = qb.executeDataTable();
			
			if (dt.getRowCount() > 0) {
				
				// 生产文件夹
				String filePath = "/RefundFile/" + DateUtil.getCurrentDate("yyyyMMdd") + "/";
				if (!new File(Config.getValue("RefundFilePath") + filePath)
						.exists()) {
					new File(Config.getValue("RefundFilePath") + filePath)
							.mkdirs();
				}
				
				// 金额格式化
				DecimalFormat df = new DecimalFormat("0.00");
				// +++++++++++++++++++++++++银联变量++++++++++++++++++++++
				// 银联PC支付内容
				String pc_ylzf_detail = "";
				Map<String, List<String>> pc_ylzf_detail_Map = null;
				
				// 银联PC合计退款个数
				int pc_ylzf_count = 0;
				BigDecimal pc_ylzf_Total = new BigDecimal(0);
				// +++++++++++++++++++++++++银联变量++++++++++++++++++++++
				// +++++++++++++++++++++++++易宝支付变量++++++++++++++++++++++
				// 易宝支付
				List<List<String>> ybzf_lines = null;
				// +++++++++++++++++++++++++易宝支付变量++++++++++++++++++++++
				// +++++++++++++++++++++++++微信变量++++++++++++++++++++++
				// 银联PC支付内容
				String wx_detail = "";
				Map<String, BigDecimal> wx_detail_Map =  null;
				// +++++++++++++++++++++++++微信变量++++++++++++++++++++++
				// +++++++++++++++++++++++++支付宝变量++++++++++++++++++++++
				// 支付宝网站
				List<List<String>> zfb_detail = null;
				// 支付宝支付总计
				BigDecimal zfb_Total = new BigDecimal(0);
				// 支付宝合计退款个数
				int zfb_count = 0; 
				//-------------------------支付宝淘宝----
				// 支付宝淘宝
				List<List<String>> zfb_tb_detail = null;
				// 支付宝淘宝支付总计
				BigDecimal zfb_tb_Total = new BigDecimal(0);
				// 支付宝淘宝合计退款个数
				int zfb_tb_count = 0;
				//-------------------------支付宝淘宝----
				//-------------------------支付宝wap----
				// 支付宝wap
				List<List<String>> zfb_wap_detail = null;
				// 支付宝wap支付总计
				BigDecimal zfb_wap_Total = new BigDecimal(0);
				// 支付宝wap合计退款个数
				int zfb_wap_count = 0;
				//-------------------------支付宝wap----
				// +++++++++++++++++++++++++支付宝变量++++++++++++++++++++++
				// +++++++++++++++++++++++++银联wap变量++++++++++++++++++++++
				// 银联wap
				List<List<String>> wap_ylzf_detail = null;
				// 银联wap总计
				BigDecimal wap_ylzf_Total = new BigDecimal(0);
				// +++++++++++++++++++++++++银联wap变量++++++++++++++++++++++
				// +++++++++++++++++++++++++其他支付方式++++++++++++++++++++++
				List<List<String>> other_detail = null;
				// +++++++++++++++++++++++++其他支付方式++++++++++++++++++++++
				
				for (int i = 0; i < dt.getRowCount(); i++) {
					String orderSn = dt.getString(i, "orderSn");
					String payType = dt.getString(i, "payType");
					String channelsn = dt.getString(i, "channelsn");
					String refundAmount = dt.getString(i, "refundAmount");
					String paySn = dt.getString(i, "paySn");
					//String tradeCheckSeriNo = dt.getString(i, "tradeCheckSeriNo");
					String tradeSeriNO = dt.getString(i, "tradeSeriNO");
					String applicantName = dt.getString(i, "applicantName");
					String policyNo = dt.getString(i, "policyNo");
					if (StringUtil.isEmpty(orderSn) || StringUtil.isEmpty(payType) 
							|| StringUtil.isEmpty(refundAmount) || StringUtil.isEmpty(paySn)) {
						continue;
					}
					
					/*// 查询出淘宝返现金额
					QueryBuilder qba = new QueryBuilder(" SELECT RefundAmount FROM InitiateRefund WHERE RefundType = '0' AND OrderSn = ? AND PolicyNo = ?", orderSn, policyNo);
					String tbBackAmount = qba.executeString();
					// 退款总额减去 淘宝返现金额
					if (StringUtil.isNotEmpty(tbBackAmount)) {
						refundAmount = new BigDecimal(refundAmount).subtract(new BigDecimal(tbBackAmount)).toString();
					}*/
					
					// 金额转化为分为单位
					String amountToPoint = new BigDecimal(refundAmount).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN).toString();
					String insureDate = dt.getString(i, "insureDate");
					String formatDate = DateUtil.toString(DateUtil.parse(insureDate, DateUtil.Format_DateTime),
							DateUtil.Format_Date2);
					
					if (StringUtil.isNotEmpty(payType)){
						// 银联PC
						if ("ylzf".equalsIgnoreCase(payType)) {
							pc_ylzf_Total = pc_ylzf_Total.add(new BigDecimal(refundAmount));
							pc_ylzf_count++;
							
							if (null == pc_ylzf_detail_Map) {
								pc_ylzf_detail_Map = new HashMap<String, List<String>>();
							}
							if (pc_ylzf_detail_Map.containsKey(paySn)) {
								List<String> oldList = pc_ylzf_detail_Map.get(paySn);
								List<String> tempList = new ArrayList<String>();
								tempList.add(formatDate);
								tempList.add(paySn);
								tempList.add(new BigDecimal(oldList.get(2)).add(new BigDecimal(refundAmount)).toString());
								pc_ylzf_detail_Map.put(paySn, tempList);
								pc_ylzf_count--;
							} else {
								List<String> tempList = new ArrayList<String>();
								tempList.add(formatDate);
								tempList.add(paySn);
								tempList.add(refundAmount);
								pc_ylzf_detail_Map.put(paySn, tempList);
							}
							// pc_ylzf_detail += formatDate + "|" + paySn + "|" + df.format(new BigDecimal(refundAmount)) + "\r\n";
						}
						// 易宝支付
						else if (payType.startsWith("ybzf")) {
							if (null == ybzf_lines) {
								ybzf_lines = new ArrayList<List<String>>();
								List<String> row0 = createRowForYBZF("订单号", "支付日期(格式：yyyyMMdd、YYYY-MM-DD、YYYY/MM/DD)", "退货金额（整数金额 单位：分）", "退款号(不能重复)", "扩展信息（POS物流商户运单号，非必填） ");
								ybzf_lines.add(row0);
							}
							List<String> row =  createRowForYBZF(paySn, formatDate, amountToPoint, String.valueOf(NoUtil.getMaxID("refund_ybzf")), "");
							ybzf_lines.add(row);
						}
						// 微信支付
						else if ("wx".equalsIgnoreCase(payType) || "wap_wx".equalsIgnoreCase(payType)) {
							if (null == wx_detail_Map) {
								wx_detail_Map = new HashMap<String, BigDecimal>();
							}
							if (wx_detail_Map.containsKey(paySn)) {
								wx_detail_Map.put(paySn, wx_detail_Map.get(paySn).add(new BigDecimal(refundAmount)));
							} else {
								wx_detail_Map.put(paySn, new BigDecimal(refundAmount));
							}
							//wx_detail += paySn + "    " + df.format(new BigDecimal(refundAmount)) + "    退款说明" + "\r\n";
						}
						// 支付宝
						else if ("zfb".equalsIgnoreCase(payType) || "tbzfb".equalsIgnoreCase(payType)) {
							
							if (StringUtil.isNotEmpty(channelsn) && channelsn.startsWith("tb")) {
								// 渠道为淘宝并且以2012开头不是变更的订单，在主站支付宝的批量退款文件中生成。以2012开头并且是已变更的订单仍在淘宝支付宝的批量文件中生成。
								if (orderSn.startsWith("2012")) {
									PolicyChangeInfoSchema pcis = new PolicyChangeInfoSchema();
									PolicyChangeInfoSet set = pcis.query(new QueryBuilder(" where newOrderSn = ?", orderSn));
									if (set.size() > 0) {
										if (null == zfb_tb_detail) {
											zfb_tb_detail = new ArrayList<List<String>>();
										}
										zfb_tb_count++;
										zfb_tb_Total = zfb_tb_Total.add(new BigDecimal(refundAmount));
										
										List<String> row =  createRowForZFB_TB(paySn, tradeSeriNO, df.format(new BigDecimal(refundAmount)), applicantName);
										zfb_tb_detail.add(row);
									} else {
										if (null == zfb_detail) {
											zfb_detail = new ArrayList<List<String>>();
										}
										zfb_count++;
										zfb_Total = zfb_Total.add(new BigDecimal(refundAmount));
										
										List<String> row =  createRowForZFB(paySn, tradeSeriNO, df.format(new BigDecimal(refundAmount)), "");
										zfb_detail.add(row);
									}
								}else{
									if (null == zfb_tb_detail) {
										zfb_tb_detail = new ArrayList<List<String>>();
									}
									zfb_tb_count++;
									zfb_tb_Total = zfb_tb_Total.add(new BigDecimal(refundAmount));
									
									List<String> row =  createRowForZFB_TB(paySn, tradeSeriNO, df.format(new BigDecimal(refundAmount)), applicantName);
									zfb_tb_detail.add(row);
								}
							} else {
								if (null == zfb_detail) {
									zfb_detail = new ArrayList<List<String>>();
								}
								zfb_count++;
								zfb_Total = zfb_Total.add(new BigDecimal(refundAmount));
								
								List<String> row =  createRowForZFB(paySn, tradeSeriNO, df.format(new BigDecimal(refundAmount)), "");
								zfb_detail.add(row);
							}
						}
						// 支付宝wap
						else if ("wap_zfb".equalsIgnoreCase(payType)) {
							if (StringUtil.isNotEmpty(channelsn) && channelsn.startsWith("tb")) {
								if (null == zfb_tb_detail) {
									zfb_tb_detail = new ArrayList<List<String>>();
								}
								zfb_tb_count++;
								zfb_tb_Total = zfb_tb_Total.add(new BigDecimal(refundAmount));
								
								List<String> row =  createRowForZFB_TB(paySn, tradeSeriNO, df.format(new BigDecimal(refundAmount)), applicantName);
								zfb_tb_detail.add(row);
							} else {
								if (null == zfb_wap_detail) {
									zfb_wap_detail = new ArrayList<List<String>>();
								}
								zfb_wap_count++;
								zfb_wap_Total = zfb_wap_Total.add(new BigDecimal(refundAmount));
								
								List<String> row =  createRowForZFB(paySn, tradeSeriNO, df.format(new BigDecimal(refundAmount)), "");
								zfb_wap_detail.add(row);
							}
						}
						// wap银联
						else if ("wap_yl".equalsIgnoreCase(payType)) {
							if (null == wap_ylzf_detail) {
								wap_ylzf_detail = new ArrayList<List<String>>();
							}
							wap_ylzf_Total = wap_ylzf_Total.add(new BigDecimal(amountToPoint));
							
							List<String> row =  createRowForYLWAP(String.valueOf(wap_ylzf_detail.size() + 1), paySn, 
									insureDate, amountToPoint, "", "");
							wap_ylzf_detail.add(row);
						}
						else {
							logger.info("未统计支付方式。payType:{}", payType);
							if (null == other_detail) {
								other_detail = new ArrayList<List<String>>();
							}
							List<String> row =  new ArrayList<String>();
							row.add(orderSn);
							row.add(paySn);
							row.add(policyNo);
							row.add(refundAmount);
							row.add(payType);
							other_detail.add(row);
						}
					}
				}
				
				File pc_ylzf_file = null;
				String ybzf_fileName = "";
				File wx_file = null;
				String zfb_fileName = "";
				String zfb_tb_fileName = "";
				String zfb_wap_fileName = "";
				String wap_ylzf_fileName = "";
				String other_fileName = "";
				List<String> fileNames = new ArrayList<String>();
				// 银联PC
				if (null != pc_ylzf_detail_Map && !pc_ylzf_detail_Map.isEmpty()) {
	
					// 银联PC支付总计
					String pc_ylzf_Amount = pc_ylzf_count + "|" + df.format(pc_ylzf_Total);
					
					pc_ylzf_file = new File(Config.getValue("RefundFilePath") + filePath, "yl_pc_" + DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".txt");
					
					Iterator<String> it = pc_ylzf_detail_Map.keySet().iterator();
					while (it.hasNext()) {
						String paySn_key = it.next();
						List<String> tempList = pc_ylzf_detail_Map.get(paySn_key);
						pc_ylzf_detail += tempList.get(0) + "|" + tempList.get(1) + "|" + df.format(new BigDecimal(tempList.get(2))) + "\r\n";
					}
					
					pc_ylzf_detail = pc_ylzf_detail.substring(0, pc_ylzf_detail.lastIndexOf("\r\n"));
					FileUtil.writeText(pc_ylzf_file.getPath(), pc_ylzf_Amount + "\r\n" + pc_ylzf_detail);
					
					fileNames.add(pc_ylzf_file.getPath());
				}
				// 易宝支付
				if (null != ybzf_lines && ybzf_lines.size() > 0) {
					ybzf_fileName = Config.getValue("RefundFilePath") + filePath + "ybzf_" + DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".xls";
					
					ybzf_lines = mergeData_ybzf(ybzf_lines);
					
					ExcelWriteUtil.writeLines(ybzf_fileName, ybzf_lines);
	
					fileNames.add(ybzf_fileName);
				}
				// 微信支付
				// if (StringUtil.isNotEmpty(wx_detail)) {
				if (null != wx_detail_Map && !wx_detail_Map.isEmpty()) {
					
					wx_file = new File(Config.getValue("RefundFilePath") + filePath, "wx_" + DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".txt");
					Iterator<String> it = wx_detail_Map.keySet().iterator();
					while (it.hasNext()) {
						String paySn_key = it.next();
						wx_detail += paySn_key + "    " + df.format(wx_detail_Map.get(paySn_key)) + "    退款说明" + "\r\n";
					}
					wx_detail = wx_detail.substring(0, wx_detail.lastIndexOf("\r\n"));
					FileUtil.writeText(wx_file.getPath(), wx_detail);
					
					fileNames.add(wx_file.getPath());
				}
				// 支付宝网站
				if (null != zfb_detail) {
	
					// 合并数据
					zfb_detail = mergeData_zfb(zfb_detail);
					
					List<List<String>> zfb_data = new ArrayList<List<String>>();
					List<String> row0 = createRowForZFB("批次号", "总金额（元）", "总笔数", "");
					zfb_data.add(row0);
	
					List<String> row1 = createRowForZFB(DateUtil.getCurrentDate("yyyyMMddHHmmss"), zfb_Total.toString(), String.valueOf(zfb_detail.size()), "");
					zfb_data.add(row1);
	
					List<String> row2 = createRowForZFB("商户退款流水号", "支付宝交易号", "退款金额", "退款备注");
					zfb_data.add(row2);
					
					zfb_data.addAll(zfb_detail);
					
					zfb_fileName = Config.getValue("RefundFilePath") + filePath + "zfb_" + DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".xls";
					ExcelWriteUtil.writeLines(zfb_fileName, zfb_data);
					
					fileNames.add(zfb_fileName);
					
				}
				
				// 支付宝淘宝
				if (null != zfb_tb_detail) {
					List<List<String>> zfb_tb_data = new ArrayList<List<String>>();
					List<String> row0 = createRowForZFB("批次号", "总金额（元）", "总笔数", "");
					zfb_tb_data.add(row0);
	
					List<String> row1 = createRowForZFB(DateUtil.getCurrentDate("yyyyMMddHHmmss"), zfb_tb_Total.toString(), String.valueOf(zfb_tb_count), "");
					zfb_tb_data.add(row1);
	
					List<String> row2 = createRowForZFB("商户退款流水号", "支付宝交易号", "退款金额", "退款备注");
					zfb_tb_data.add(row2);
					
					zfb_tb_data.addAll(zfb_tb_detail);
					
					zfb_tb_fileName = Config.getValue("RefundFilePath") + filePath + "zfb_tb_" + DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".xls";
					ExcelWriteUtil.writeLines(zfb_tb_fileName, zfb_tb_data);
					
					fileNames.add(zfb_tb_fileName);
					
				}
				
				// 支付宝wap
				if (null != zfb_wap_detail) {
					// 合并数据
					zfb_wap_detail = mergeData_zfb(zfb_wap_detail);
					
					List<List<String>> zfb_wap_data = new ArrayList<List<String>>();
					List<String> row0 = createRowForZFB("批次号", "总金额（元）", "总笔数", "");
					zfb_wap_data.add(row0);
	
					List<String> row1 = createRowForZFB(DateUtil.getCurrentDate("yyyyMMddHHmmss"), zfb_wap_Total.toString(), String.valueOf(zfb_wap_detail.size()), "");
					zfb_wap_data.add(row1);
	
					List<String> row2 = createRowForZFB("商户退款流水号", "支付宝交易号", "退款金额", "退款备注");
					zfb_wap_data.add(row2);
	
					zfb_wap_data.addAll(zfb_wap_detail);
					
					zfb_wap_fileName = Config.getValue("RefundFilePath") + filePath + "zfb_wap_" + DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".xls";
					ExcelWriteUtil.writeLines(zfb_wap_fileName, zfb_wap_data);
					
					fileNames.add(zfb_wap_fileName);
					
				}
				// wap 银联支付
				if (null != wap_ylzf_detail) {
	
					// 合并数据
					wap_ylzf_detail = mergeData_yl(wap_ylzf_detail);
					
					String maxID = NoUtil.getMaxID("wap_ylzf_fileName") + "";
					List<List<String>> wap_ylzf_data = new ArrayList<List<String>>();
					List<String> row0 = createRowForYLWAP(DateUtil.getCurrentDate(DateUtil.Format_Date2), maxID,
							String.valueOf(wap_ylzf_detail.size()), String.valueOf(wap_ylzf_Total), "预留字段1", "预留字段2");
					wap_ylzf_data.add(row0);
					
					wap_ylzf_data.addAll(wap_ylzf_detail);
					
					QueryBuilder qbPayBase = new QueryBuilder(" select merId from payBase where payType = 'wap_yl' ");
					
					String merId = qbPayBase.executeString();
					
					String name = "TH" + merId + "_" + DateUtil.getCurrentDate(DateUtil.Format_Date2) + "_" + maxID;
					wap_ylzf_fileName = Config.getValue("RefundFilePath")	+ filePath + name + ".xls";
					ExcelWriteUtil.writeLines(wap_ylzf_fileName, wap_ylzf_data);
					
					fileNames.add(wap_ylzf_fileName);
				}
				// 其他支付方式
				if (null != other_detail) {
					
					other_detail = mergeData_other(other_detail);
					
					List<List<String>> other_data = new ArrayList<List<String>>();
					List<String> row0 = new ArrayList<String>();
					row0.add("订单号");
					row0.add("商家订单号");
					row0.add("保单号");
					row0.add("退款金额（元）");
					row0.add("支付类型");
					other_data.add(row0);
					
					other_data.addAll(other_detail);
					
					other_fileName = Config.getValue("RefundFilePath")	+ filePath + "other_data" + DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".xls";
					ExcelWriteUtil.writeLines(other_fileName, other_data);
					
					fileNames.add(other_fileName);
				}
				
				if (fileNames.size() > 0) {
					String[] srcFiles = new String[fileNames.size()];
					fileNames.toArray(srcFiles);
	
					String name = DateUtil.getCurrentDate("yyyyMMddHHmmss") + ".zip";
					String zipFilePath = "/RefundFile/" + name;
					String destFile = Config.getValue("RefundFilePath") + zipFilePath;
					
					try {
						ZipUtil.zipBatch(srcFiles, destFile);
						InputStream in = new FileInputStream(new File(destFile));
						//OSSUploadFile.upload(in, );
						in.close();
						// 数据库记录
						insertRefundFileRecode(name, zipFilePath, startDate, endDate);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			} else {
				return "没有符合条件数据.";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			java.io.ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
			String expMessage = buf.toString();
			try {
				buf.close();
			} catch (Exception e1) {
			}
			return expMessage;
		}
		return "SUCCESS";
	}
	
	/**
	 * 插入退款文件记录
	 * 
	 * @param fileName
	 * @param filePath
	 * @return
	 */
	private boolean insertRefundFileRecode (String fileName, String filePath, String startTime, String endTime) {
		RefundFileInfoSchema info = new RefundFileInfoSchema();
		info.setCreateDate(new Date());
		info.setCreateUser(User.getUserName());
		info.setid(NoUtil.getMaxNo("RefundFileInfo"));
		info.setFileName(fileName);
		info.setFilePath(filePath);
		info.setProp1(startTime);
		info.setProp2(endTime);
		return info.insert();
	}
	
	/**
	 * 创建一行易宝支付
	 * 
	 * @param paySn
	 * @param payDate
	 * @param amnout
	 * @param rowNo
	 * @param remark
	 * @return
	 */
	private List<String> createRowForYBZF (String paySn, String payDate, String amount, String rowNo, String remark) {
		List<String> row = new ArrayList<String>();
		
		row.add(paySn);
		row.add(payDate);
		row.add(amount);
		row.add(rowNo);
		row.add(remark);
		return row;
	}
	
	/**
	 * 创建一行支付宝支付数据
	 * 
	 * @param paySn
	 * @param payDate
	 * @param amnout
	 * @param rowNo
	 * @param remark
	 * @return
	 */
	private List<String> createRowForZFB (String rowNo, String paySn, String amount, String remark) {
		List<String> row = new ArrayList<String>();

		row.add(rowNo);
		row.add(paySn);
		row.add(amount);
		row.add(remark);
		return row;
	}
	
	/**
	 * createRowForZFB_TB:支付宝淘宝. <br/>
	 *
	 * @author wwy
	 * @param rowNo
	 * @param paySn
	 * @param amount
	 * @param remark
	 * @return
	 */
	private List<String> createRowForZFB_TB (String rowNo, String paySn, String amount, String applicantName) {
		List<String> row = new ArrayList<String>();

		row.add(rowNo);
		row.add(paySn);
		row.add(amount);
		row.add(applicantName);
		return row;
	}
	
	/**
	 * 创建一行wap银联支付数据
	 * 
	 * @param paySn
	 * @param payDate
	 * @param amnout
	 * @param rowNo
	 * @param remark
	 * @return
	 */
	private List<String> createRowForYLWAP (String rowNo, String paySn, String payDate, String amount, String remark1, String remark2) {
		List<String> row = new ArrayList<String>();

		row.add(rowNo);
		row.add(paySn);
		row.add(payDate);
		row.add(amount);
		row.add(remark1);
		row.add(remark2);
		return row;
	}
	
	/**
	 * mergeData_zfb:支付宝合并数据(商家订单号相同的合并). <br/>
	 *
	 * @author wwy
	 * @param data
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<List<String>> mergeData_zfb(List<List<String>> data) {

		List<List<String>> resutl = new ArrayList<List<String>>();
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		for (int i = 0; i < data.size(); i++) {
			List<String> row = data.get(i);
			// 商家订单号
			String paySn = row.get(0);
			if (map.containsKey(paySn)) {
				List<String> oldRow = map.get(paySn);
				List<String> newRow = new ArrayList<String>();
				newRow.add(oldRow.get(0));
				newRow.add(oldRow.get(1));
				newRow.add((new BigDecimal(oldRow.get(2)).add(new BigDecimal(row.get(2)))).toString());
				map.put(paySn, newRow);
			} else {
				map.put(paySn, row);
			}
		}
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			resutl.add(map.get(it.next()));
		}
		return resutl;
	}
	
	/**
	 * mergeData_yl:银联合并数据(商家订单号相同的合并). <br/>
	 *
	 * @author wwy
	 * @param data
	 * @return
	 */
	private List<List<String>> mergeData_yl(List<List<String>> data) {

		List<List<String>> resutl = new ArrayList<List<String>>();
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		for (int i = 0; i < data.size(); i++) {
			List<String> row = data.get(i);
			// 商家订单号
			String paySn = row.get(1);
			if (map.containsKey(paySn)) {
				List<String> oldRow = map.get(paySn);
				List<String> newRow = new ArrayList<String>();
				newRow.add(String.valueOf(map.size() + 1));
				newRow.add(row.get(1));
				newRow.add(row.get(2));
				newRow.add((new BigDecimal(oldRow.get(3)).add(new BigDecimal(row.get(3)))).toString());
				newRow.add(row.get(4));
				newRow.add(row.get(5));
				map.put(paySn, newRow);
			} else {
				map.put(paySn, row);
			}
		}
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			resutl.add(map.get(it.next()));
		}
		return resutl;
	}
	
	/**
	 * mergeData_ybzf:易宝支付合并数据(商家订单号相同的合并). <br/>
	 *
	 * @author wwy
	 * @param data
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<List<String>> mergeData_ybzf(List<List<String>> data) {

		List<List<String>> resutl = new ArrayList<List<String>>();
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		for (int i = 0; i < data.size(); i++) {
			List<String> row = data.get(i);
			// 商家订单号
			String paySn = row.get(0);
			if (map.containsKey(paySn)) {
				List<String> oldRow = map.get(paySn);
				List<String> newRow = new ArrayList<String>();
				newRow.add(row.get(0));
				newRow.add(row.get(1));
				newRow.add((new BigDecimal(oldRow.get(2)).add(new BigDecimal(row.get(2)))).toString());
				newRow.add(row.get(3));
				newRow.add(row.get(4));
				map.put(paySn, newRow);
			} else {
				map.put(paySn, row);
			}
		}
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			resutl.add(map.get(it.next()));
		}
		return resutl;
	}
	
	/**
	 * mergeData_other:其他支付数据 合并数据(商家订单号相同的合并). <br/>
	 *
	 * @author wwy
	 * @param data
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<List<String>> mergeData_other(List<List<String>> data) {

		List<List<String>> resutl = new ArrayList<List<String>>();
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		for (int i = 0; i < data.size(); i++) {
			List<String> row = data.get(i);
			// 商家订单号
			String paySn = row.get(1);
			if (map.containsKey(paySn)) {
				List<String> oldRow = map.get(paySn);
				List<String> newRow = new ArrayList<String>();
				newRow.add(row.get(0));
				newRow.add(row.get(1));
				newRow.add(oldRow.get(2) + "," + row.get(2));
				newRow.add((new BigDecimal(oldRow.get(3)).add(new BigDecimal(row.get(3)))).toString());
				newRow.add(row.get(4));
				map.put(paySn, newRow);
			} else {
				map.put(paySn, row);
			}
		}
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			resutl.add(map.get(it.next()));
		}
		return resutl;
	}
}
