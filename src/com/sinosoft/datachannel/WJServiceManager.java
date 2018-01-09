package com.sinosoft.datachannel;

import com.alipay.util.fpfClient.SFtpAction;
import com.jcraft.jsch.ChannelSftp;
import com.sinosoft.asyninter.CSVUtils;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.CancelContService;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDInformationSet;
import com.sinosoft.webservice.FCContServiceImpl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WJServiceManager extends ConfigEanbleTaskManager {

	public static final String CODE = "com.sinosoft.datachannel.WJServiceManager";
	

	public boolean isRunning(long id) {

		return false;
	}

	public Mapx getConfigEnableTasks() {

		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "结算中心交互接口定时调用");
		map.put("1", "百年结算数据备份定时任务");
		map.put("2", "百年理财险生成对账文件定时任务");
		map.put("3", "理赔异常告警定时任务");
		map.put("4", "客服指定人员定时任务");
		map.put("5", "合作方生成对账文件定时任务");
		map.put("6", "平安团险财务对账");
		map.put("7", "充值信息结算定时任务");

		return map;
	}

	public void execute(long id) {

		if ("0".equals(id + "")) {

			SDInformationRiskTypeSchema sdIRiskTypeSchema = new SDInformationRiskTypeSchema();
			SDInformationRiskTypeSet sdIRiskTypeSet = new SDInformationRiskTypeSet();
			/**
			 * and riskcode <> '210301001' 百年稳赢保终身寿险 暂时不做定时任务
			 * <req:百年理财保险对接-快钱、飞凡>
			 * 
			 * @author chouweigao
			 * 
			 */
			String sql = "where modifyDate < now() and appStatus='1' and (balanceStatus is null or (balanceStatus != '0' and balanceStatus != '2')) and NOT FIND_IN_SET(riskcode, (SELECT VALUE FROM zdconfig WHERE TYPE = 'LicaiBaoxianProducts'))  group by policyno ";// 当前时间之前未发生结算交互,BalanceFlag表示未与结算中心发生交互，appStatus表示经代通与保险公司交互成功
			QueryBuilder qb = new QueryBuilder(sql);
			sdIRiskTypeSet = sdIRiskTypeSchema.query(qb);
			Transaction transaction = new Transaction();

			// 需要结算的保单的结算状态更新成发送中(2)
			if (sdIRiskTypeSet != null && sdIRiskTypeSet.size() > 0) {
				sql = "update sdinformationrisktype set balanceStatus = '2', modifyDate = now() where modifyDate < now() and appStatus='1' and (balanceStatus is null or (balanceStatus != '0' and balanceStatus != '2')) and NOT FIND_IN_SET(riskcode, (SELECT VALUE FROM zdconfig WHERE TYPE = 'LicaiBaoxianProducts'))";
				transaction.add(new QueryBuilder(sql));
				transaction.commit();
			}

			FCContServiceImpl tFCContServiceImpl = new FCContServiceImpl();
			transaction.clear();
			for (int i = 0; i < sdIRiskTypeSet.size(); i++) {
				sdIRiskTypeSchema = sdIRiskTypeSet.get(i);
				String ordersn = sdIRiskTypeSchema.getorderSn();
				String recognizeeSn = sdIRiskTypeSchema.getrecognizeeSn();
				String informationSn = sdIRiskTypeSchema.getinformationSn();
				String comCode = "";
				if (StringUtil.isNotEmpty(informationSn)) {
					SDInformationSchema sdInformationSchema = new SDInformationSchema();
					SDInformationSet sdInformationSet = new SDInformationSet();
					sdInformationSet = sdInformationSchema.query(new QueryBuilder(
							"where ordersn = ? and informationSn = ? ", ordersn, informationSn));
					if (sdInformationSet != null && sdInformationSet.size() == 1) {
						sdInformationSchema = sdInformationSet.get(0);
						comCode = sdInformationSchema.getinsuranceCompany();
					}
				}
				DataTable dt = new QueryBuilder("select tradeCheckSeriNo from TradeInformation where ordID = ?", ordersn)
						.executeDataTable();
				String paySn = "";
				if (dt.getRowCount() > 0) {
					paySn = dt.getString(0, 0);
				}
				if (StringUtil.isNotEmpty(ordersn) && StringUtil.isNotEmpty(comCode) && StringUtil.isNotEmpty(informationSn)) {
					tFCContServiceImpl.callFCContService(comCode, ordersn, recognizeeSn, informationSn, paySn);
				} else {
					// 信息不全的保单结算状态更新成空
					sdIRiskTypeSchema.setbalanceStatus("");
					sdIRiskTypeSchema.setmodifyDate(new Date());
					transaction.add(sdIRiskTypeSchema, Transaction.UPDATE);
				}
			}
			transaction.commit();
			
			// 退款同步到结算中心
			try {
				SDInformationRiskTypeSchema refundRiskTypeSchema = new SDInformationRiskTypeSchema();
				SDInformationRiskTypeSet refundRiskTypeSet = new SDInformationRiskTypeSet();
				/**
				 * 不是理财险，所有保单状态为4的进行同步
				 * 
				 */
				String refundSql = "where modifyDate < now() and appStatus='4' and balanceStatus != '2' and NOT FIND_IN_SET(riskcode, (SELECT VALUE FROM zdconfig WHERE TYPE = 'LicaiBaoxianProducts'))  group by policyno ";
				QueryBuilder refundqb = new QueryBuilder(refundSql);
				refundRiskTypeSet = refundRiskTypeSchema.query(refundqb);
				if (refundRiskTypeSet.size() > 0) {
					// 需要结算的保单的结算状态更新成发送中(2)
					sql = "update sdinformationrisktype set balanceStatus = '2', modifyDate = now() where modifyDate < now() and appStatus='4' and balanceStatus != '2' and NOT FIND_IN_SET(riskcode, (SELECT VALUE FROM zdconfig WHERE TYPE = 'LicaiBaoxianProducts'))";
					transaction.add(new QueryBuilder(sql));
					transaction.commit();
					transaction.clear();

					for (int i = 0; i < refundRiskTypeSet.size(); i++) {
						String ordersn = refundRiskTypeSet.get(i).getorderSn();
						String riskTypeId = refundRiskTypeSet.get(i).getId();
						String returnIntegral = "";
						String returnActivity = "";
						if (StringUtil.isNotEmpty(riskTypeId)) {
							
							// 取得撤单回退积分数
							String Integral = new QueryBuilder(
									"select Integral from SDIntCalendar where prop1 = ? and Source = ?",
									riskTypeId,
									IntegralConstant.POINT_SOURCE_OFFSET_POINT)
									.executeString();
							if (StringUtil.isNotEmpty(Integral)) {
								BigDecimal PointScalerUnit = new BigDecimal(
										Config.getConfigValue("PointScalerUnit"));
								returnIntegral = new BigDecimal(Integral).divide(
										PointScalerUnit, 2, BigDecimal.ROUND_HALF_UP)
										.toString();
							}
							
							if (ordersn.indexOf("TB") == 0) {
								returnActivity = refundRiskTypeSet.get(i).getactivityValue();
							}
							CancelContService tCancelContService = new CancelContService();
							if (tCancelContService.callProductInterface(ordersn, "", PubFun.getCurrentDate(), "02",
									refundRiskTypeSet.get(i).getpolicyNo(), riskTypeId, returnIntegral, returnActivity)) {
								logger.info("成功ordersn:{}riskTypeId:{}", ordersn, riskTypeId);
							} else {
								logger.error("撤单失败ordersn:{}riskTypeId:{}", ordersn, riskTypeId);
							}
						}
					}
				} else {
					logger.error("没有未同步撤单数据");
				}
				
			} catch (Exception e) {
				logger.error("浙金所渠道退款同步到结算中心异常" + e.getMessage(), e);
			}
		} else if ("1".equals(id + "")) {
			String whereSql = "where a.SupplierCode = '0005' and a.SignDate < DATE_SUB(DATE_ADD(CURDATE(), INTERVAL - DAY(CURDATE()) + 1 DAY), INTERVAL 1 MONTH) ";
			String deleWhereSql = whereSql.replace("a.", "FCContNew.");
			DataTable dt = new QueryBuilder("select DISTINCT YEAR(MAKEDATE) from fccontnew a " + whereSql)
					.executeDataTable();

			whereSql += " and YEAR(a.MAKEDATE) = ? ";
			if (dt != null && dt.getRowCount() > 0) {
				Transaction transaction = new Transaction();
				StringBuffer fccontbInseSql = new StringBuffer();
				fccontbInseSql.append("INSERT INTO FCContNewB{year} (InnerContNo,ContNo,ProposalContNo,SupplierCode,");
				fccontbInseSql.append("BizType,State,SignDate,SignTime,CValiDate,CValiDateTime,CInValiDate,CInValiTime,");
				fccontbInseSql.append("Mult,SumPrem,RiskCode,OrderNo,InnerOrderNo,ThirdPlatformCode,ThirdPlatformCodeName,");
				fccontbInseSql.append("ThirdPlatformTrandeNo,BackUp1,BackUp2,BackUp3,BackUp4,BackUp5,BackUp6,BackUp7,");
				fccontbInseSql.append("BackUp8,BackUp9,BackUp10,Operator,MakeDate,MakeTime,ModifyDate,ModifyTime) ");
				fccontbInseSql.append("select InnerContNo,ContNo,ProposalContNo,SupplierCode,BizType,State,SignDate,");
				fccontbInseSql.append("SignTime,CValiDate,CValiDateTime,CInValiDate,CInValiTime,Mult,SumPrem,RiskCode,");
				fccontbInseSql.append("OrderNo,InnerOrderNo,ThirdPlatformCode,ThirdPlatformCodeName,ThirdPlatformTrandeNo,");
				fccontbInseSql.append("BackUp1,BackUp2,BackUp3,BackUp4,BackUp5,BackUp6,BackUp7,BackUp8,BackUp9,BackUp10,");
				fccontbInseSql.append("Operator,MakeDate,MakeTime,ModifyDate,ModifyTime from FCContNew a " + whereSql);

				StringBuffer fjbInseSql = new StringBuffer();
				fjbInseSql.append("INSERT INTO FJBGetSupplierNew{year} (GetNoticeNo,BizType,SupplierCode,InnerContNo,");
				fjbInseSql.append("SettleDate,PayMoney,RiskCode,ProtocolNo,BFBatchNo,SXFBatchNo,GetMoney,Rate,BackUp1,");
				fjbInseSql.append("BackUp2,BackUp3,BackUp4,BackUp5,Operator,MakeDate,MakeTime,ModifyDate,ModifyTime) ");
				fjbInseSql.append("select b.GetNoticeNo,b.BizType,b.SupplierCode,b.InnerContNo,b.SettleDate,b.PayMoney,");
				fjbInseSql.append("b.RiskCode,b.ProtocolNo,b.BFBatchNo,b.SXFBatchNo,b.GetMoney,b.Rate,b.BackUp1,b.BackUp2,");
				fjbInseSql
						.append("b.BackUp3,b.BackUp4,b.BackUp5,b.Operator,b.MakeDate,b.MakeTime,b.ModifyDate,b.ModifyTime ");
				fjbInseSql.append("from FCContNew a, fjsgetsuppliernew b " + whereSql + " and b.InnerContNo=a.InnerContNo");

				StringBuffer fcorderbSql = new StringBuffer();
				fcorderbSql.append("INSERT INTO FCOrderB{year} (OrderNo,SupplierCode,SumMoney,PayMoney,State,");
				fcorderbSql.append("PayType,PayBankAccNo,InnerOrderNo,CouponID,CouponSn,CouponParValue,ProvideUser,");
				fcorderbSql.append("CouponDesc,OrderMakeDate,BackUp1,BackUp2,BackUp3,BackUp4,BackUp5,Operator,");
				fcorderbSql.append("MakeDate,MakeTime,ModifyDate,ModifyTime,ActvityID,ActivitySn,ActivityValue,");
				fcorderbSql
						.append("ActivityUser,ActivityDesc,OrderPayMoney,OrderActivity,SumCouponValue,SumActivityValue) ");
				fcorderbSql.append("select b.OrderNo,b.SupplierCode,b.SumMoney,b.PayMoney,b.State,b.PayType,");
				fcorderbSql.append("b.PayBankAccNo,b.InnerOrderNo,b.CouponID,b.CouponSn,b.CouponParValue,b.ProvideUser,");
				fcorderbSql.append("b.CouponDesc,b.OrderMakeDate,b.BackUp1,b.BackUp2,b.BackUp3,b.BackUp4,b.BackUp5,");
				fcorderbSql.append("b.Operator,b.MakeDate,b.MakeTime,b.ModifyDate,b.ModifyTime,b.ActvityID,b.ActivitySn,");
				fcorderbSql.append("b.ActivityValue,b.ActivityUser,b.ActivityDesc,b.OrderPayMoney,b.OrderActivity,");
				fcorderbSql.append("b.SumCouponValue,b.SumActivityValue from FCOrderNew b, FCContNew a ");
				fcorderbSql.append(whereSql + " and a.orderno = b.orderno group by b.orderno");

				int rowCount = dt.getRowCount();
				String year = "";

				for (int i = 0; i < rowCount; i++) {
					year = dt.getString(i, 0);
					if (StringUtil.isNotEmpty(year)) {
						transaction.add(new QueryBuilder(fccontbInseSql.toString().replace("{year}", year), year));
						transaction.add(new QueryBuilder(fjbInseSql.toString().replace("{year}", year), year));
						transaction.add(new QueryBuilder(fcorderbSql.toString().replace("{year}", year), year));
					}
				}
				transaction.add(new QueryBuilder("delete FJSGetSupplierNew from FJSGetSupplierNew, FCContNew "
						+ deleWhereSql + " and FCContNew.InnerContNo=FJSGetSupplierNew.InnerContNo "));
				transaction.add(new QueryBuilder("delete FCOrderNew from FCOrderNew, FCContNew " + deleWhereSql
						+ " and FCContNew.OrderNo=FCOrderNew.OrderNo "));
				transaction.add(new QueryBuilder("delete from FCContNew " + deleWhereSql));

				transaction.commit();
			}
		} else if ("2".equals(id + "")) {
			if (!makeAccountFile()) {
				logger.error("百年理财险生成对账文件定时任务发生异常！");
			}
		} else if ("3".equals(id + "")) {
			if (!paymentExcep()) {
				logger.error("理赔异常告警定时任务发生异常！");
			}
		} else if ("4".equals(id + "")){
			
			if (! BatchDelMark()) {
				logger.error("客服指定人员定时任务发生异常！");
			}
		}else if ("6".equals(id + "")){
			
			if (! pinganReconciliation()) {
				logger.error("平安定时任务发生异常！");
			}
		}
		else if ("5".equals(id + "")) {
			if (!zjfaeAccountFile()) {
				logger.error("生成浙金所对账文件定时任务发生异常！");
			}
		}else if ("7".equals(id + "")){
			// 充值信息结算定时任务
			rechargeInfoBalance();
		} else {
			logger.warn("未知定时任务，未处理！");
		}
	}

	private void rechargeInfoBalance() {
		// 各支付方式手续费率信息
		Map<String, String> rateMap = new QueryBuilder("select code,otherSign from fdcode where codetype ='paymethod'").executeDataTable().toMapx(0, 1);
		// 当前日期
		String date = PubFun.getCurrentDate();
		// 当前时间
		String time = PubFun.getCurrentTime();
		// 美行保充值信息结算
		rechargeInfoBalanceB2B(rateMap, date, time);
		// group充值信息结算
		rechargeInfoBalanceGroup(rateMap, date, time);
	}

	private boolean rechargeInfoBalanceGroup(Map<String, String> rateMap, String date, String time) {
		try {
			DataTable dt = new QueryBuilder("SELECT  c2.Static , c.amount, c.PayNo, c.ADDTIME, t.payType,c2.id FROM  sdtradeinformation t, ConsumeInformation c   LEFT JOIN ConsumeInformationTeammate c2  ON c2.PayNo = c.PayNo "
					+ " WHERE c.constatus = '2'   AND c.PayNo = t.tradeSeriNO  AND ( c2.Static != '0' OR c2.Static IS NULL )  AND c.ADDTIME >= '2017-01-01 00:00:00'  ORDER BY c.ADDTIME DESC ").executeDataTable("Group");
			if (dt != null && dt.getRowCount() > 0) {
				int rowcount = dt.getRowCount();
				int i = 0;
				Transaction tran = new Transaction();
				String sql = "insert into AccountFeeInfo (ID,PayTime,PaySn,PayMoney,PayType,Rate,FeeMoney,ChannelCode,BackUp1,BackUp2,BackUp3,BackUp4,BackUp5,Operator,MakeDate,MakeTime,ModifyDate,ModifyTime) values  ";
				String id = "";
				String payMoney = "";
				String paysn = "";
				String payTime = "";
				String payType = "";
				String channelCode = "b2b_ht";
				String rate = "";
				String feeMoney = "";
				BigDecimal zero = new BigDecimal(0);
				String paynos = "";
				int cou = 0;
				String sqlwhere ="";
				for (; i < rowcount; i++) {
					id = "Group"+NoUtil.getMaxID("AccountFeeInfoID");
					payMoney = dt.getString(i, 1);
					paysn = dt.getString(i, 2);
					payTime = dt.getString(i, 3);
					payType = dt.getString(i, 4);
					rate = "0";
					feeMoney = "0";
					if (payType.startsWith("ybzf_")) {
						payType = payType.substring(0, payType.indexOf("_"));
					}else if (payType.equals("wx")){
						payType="wx601";
					}else if (payType.equals("zfb")){
						payType="wzfb";
					}
					if (rateMap.containsKey(payType)) {
						rate = rateMap.get(payType);
					}
					if (StringUtil.isEmpty(rate) || zero.compareTo(new BigDecimal(rate)) == 0) {
						rate = "0";
						feeMoney = "0";
					} else {
						feeMoney = new BigDecimal(rate).multiply(new BigDecimal(payMoney))
								.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
					}
					if (new BigDecimal(rate).compareTo(new BigDecimal("0")) == 0)
						continue;
					sqlwhere = "('" + id + "','" + payTime + "','" + paysn + "','" + payMoney + "','" + payType + "','"
							+ rate + "','" + feeMoney + "','" + channelCode + "','','','','','','WJ','" + date + "','"
							+ time + "','" + date + "','" + time + "');";
					tran.add(new QueryBuilder(sql + sqlwhere));
					paynos += (",'" + paysn + "'");
					if (StringUtil.isEmpty(dt.getString(i, 5))) {
						new QueryBuilder(
								"INSERT INTO ConsumeInformationTeammate (`ID`, `PayNo`, `Static`, `MakeDate`, `BackUp1`, `BackUp2`, `BackUp3`, `BackUp4`, `BackUp5`, `BackUp6`, `BackUp7`) VALUES('"
										+ id
										+ "','"
										+ paysn
										+ "','"
										+ "1"
										+ "','"
										+ date
										+ " "
										+ time
										+ "',NULL,NULL,NULL,NULL,NULL,NULL,NULL)").executeNoQuery("Group");
					}
				}

				if (StringUtil.isNotEmpty(paynos)) {
					cou = new QueryBuilder("UPDATE ConsumeInformationTeammate SET static='0'  where payno in ("
							+ paynos.substring(1) + ")").executeNoQuery("Group");
				}
				if (cou > 0) {
					if (tran.commit()) {
						return true;
					} else {
						logger.error("Group充值信息结算数据插入失败！");
						new QueryBuilder("UPDATE ConsumeInformationTeammate SET static='1'  where payno in ("
								+ paynos.substring(1) + ")").executeNoQuery("Group");
						return false;
					}
				} else {
					logger.error("Group充值信息结算数据美行保更新结算状态失败！");
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("Group充值信息结算失败！错误信息如下：" + e.getMessage(), e);
			return false;
		}
		return true;
	}

	private boolean rechargeInfoBalanceB2B(Map<String, String> rateMap, String date, String time) {

		try {
			DataTable dt = new QueryBuilder(
					"SELECT ID,amount,paysn,createdate,prop1 FROM ConsumeInformation WHERE status = '1' AND paysn LIKE 'MXCZ%' AND (prop2!='0' OR prop2 IS NULL) and createdate >= '2017-01-01' order by prop1")
					.executeDataTable("B2B");
			if (dt != null && dt.getRowCount() > 0) {
				int rowcount = dt.getRowCount();
				int i = 0;
				Transaction tran = new Transaction();
				String sql = "insert into AccountFeeInfo (ID,PayTime,PaySn,PayMoney,PayType,Rate,FeeMoney,ChannelCode,BackUp1,BackUp2,BackUp3,BackUp4,BackUp5,Operator,MakeDate,MakeTime,ModifyDate,ModifyTime) values  ";
				String id = "";
				String payMoney = "";
				String paysn = "";
				String payTime = "";
				String payType = "";
				String channelCode = "b2b_ht";
				String rate = "";
				String feeMoney = "";
				List<Object> params;
				BigDecimal zero = new BigDecimal(0);
				String ids = "";
				String sqlwhere = "";
				for (; i < rowcount; i++) {
					id = dt.getString(i, 0);
					payMoney = dt.getString(i, 1);
					paysn = dt.getString(i, 2);
					payTime = dt.getString(i, 3);
					payType = dt.getString(i, 4);
					rate = "0";
					feeMoney = "0";
					if (payType.startsWith("ybzf_")) {
						payType = payType.substring(0, payType.indexOf("_"));
					} else if (payType.equals("wx")) {
						payType = "wx601";
					} else if (payType.equals("zfb")) {
						payType = "wzfb";
					}
					if (rateMap.containsKey(payType)) {
						rate = rateMap.get(payType);
					}
					if (StringUtil.isEmpty(rate) || zero.compareTo(new BigDecimal(rate)) == 0) {
						rate = "0";
						feeMoney = "0";
					} else {
						feeMoney = new BigDecimal(rate).multiply(new BigDecimal(payMoney))
								.setScale(4, BigDecimal.ROUND_HALF_UP).toString();
					}
					if (new BigDecimal(rate).compareTo(new BigDecimal("0")) == 0)
						continue;
					sqlwhere = "('" + "B2B" + id + "','" + payTime + "','" + paysn + "','" + payMoney + "','" + payType
							+ "','" + rate + "','" + feeMoney + "','" + channelCode + "','','','','','','WJ','" + date
							+ "','" + time + "','" + date + "','" + time + "');";
					tran.add(new QueryBuilder(sql + sqlwhere));
					ids += (",'" + id + "'");
				}
				int cou = new QueryBuilder("update ConsumeInformation set prop2='0' where id in (" + ids.substring(1) + ")")
						.executeNoQuery("B2B");
				if (cou > 0) {
					if (tran.commit()) {
						return true;
					} else {
						logger.error("美行保充值信息结算数据插入失败！");
						new QueryBuilder("update ConsumeInformation set prop2='1' where id in (" + ids.substring(1) + ")")
								.executeNoQuery("B2B");
						return false;
					}
				} else {
					logger.error("充值信息结算数据美行保更新结算状态失败！");
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("美行保充值信息结算失败！错误信息如下：" + e.getMessage(), e);
			return false;
		}
		return true;
	}

	private boolean paymentExcep() {

		boolean flag = true;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT p.insureIdentityId, p.claimsItemsType, COUNT(1) appCount, GROUP_CONCAT(claimsNo) claimsNos, MIN(t.LimitCount) LimitCount, ");
			sb.append("GROUP_CONCAT(productId) productIds, GROUP_CONCAT(caseType) caseTypes, GROUP_CONCAT(claimsItemsName) claimsItemsNames,");
			sb.append("GROUP_CONCAT(policyNo) policyNos, MIN(p.insureName) insureName, MIN(t.TypeName) TypeName ");
			sb.append("FROM paymentclaimsinfo p, FemClaimsItemsType t WHERE STATUS !='00' AND p.claimsItemsType=t.TypeCode ");
			sb.append("AND t.LimitDay > 0 AND t.LimitCount > 0 AND LEFT(p.applicationDate,10) >= DATE_SUB(LEFT(NOW(),10),INTERVAL t.LimitDay DAY) ");
			sb.append("AND LEFT(p.applicationDate,10) < LEFT(NOW(),10) GROUP BY p.insureIdentityId, p.claimsItemsType HAVING appCount > LimitCount ");
			sb.append("");
			DataTable dt = new QueryBuilder(sb.toString()).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {

				Map<String, String> caseTypeMap = getCaseTypeMap();

				int rowCount = dt.getRowCount();
				int i = 0;
				String policyNos = "";
				// 循环取得保单号
				for (; i < rowCount; i++) {
					policyNos += ("," + dt.getString(i, "policyNos"));
				}
				policyNos = policyNos.substring(1).replace(",", "','");
				// 取得产品名称、购买日期、生效日期、保费、报案保险公司
				Map<String, Map<String, String>> policyInfos = getPolicyInfo(policyNos);
				Map<String, String> policyInfo;
				// 邮件参数
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				Map<String, String> map;
				String[] policyNoArr;
				List<Map<String, String>> policyList = new ArrayList<Map<String, String>>();
				Map<String, String> policyMap;
				String claimsNos = "";
				String[] claimsNoArr;
				String claimsItemsNames = "";
				String[] claimsItemsNameArr;
				String caseTypes = "";
				String[] caseTypeArr;
				i = 0;
				int j = 0;
				for (; i < rowCount; i++) {

					caseTypes = dt.getString(i, "caseTypes");
					caseTypeArr = caseTypes.split(",");
					claimsNos = dt.getString(i, "claimsNos");
					claimsNoArr = claimsNos.split(",");
					claimsItemsNames = dt.getString(i, "claimsItemsNames");
					claimsItemsNameArr = claimsItemsNames.split(",");
					policyNos = dt.getString(i, "policyNos");
					policyNoArr = policyNos.split(",");
					policyList = new ArrayList<Map<String, String>>();
					j = 0;
					for (String policyNo : policyNoArr) {
						map = new HashMap<String, String>();
						map.put("insureIdentityId", dt.getString(i, "insureIdentityId"));
						map.put("insureName", dt.getString(i, "insureName"));
						map.put("claimsItemsTypeName", dt.getString(i, "TypeName"));
						map.put("appCount", dt.getString(i, "appCount"));
						map.put("rowspan", "");
						if (j == 0) {
							map.put("rowspan", policyNoArr.length + "");
						}
						policyInfo = policyInfos.get(policyNo);
						map.put("productName", policyInfo.get("productName"));
						map.put("orderSn", policyInfo.get("orderSn"));
						map.put("policyNo", policyNo);
						map.put("insureDate", policyInfo.get("insureDate"));
						map.put("svaliDate", policyInfo.get("svaliDate"));
						map.put("evaliDate", policyInfo.get("evaliDate"));
						map.put("timePrem", policyInfo.get("timePrem"));
						map.put("company", policyInfo.get("company"));
						map.put("claimsNo", claimsNoArr[j]);
						map.put("claimsItemsName", claimsItemsNameArr[j]);
						map.put("caseType", caseTypeMap.get(caseTypeArr[j]));
						list.add(map);
						j++;
					}
					// map.put("policyList", policyList);

				}

				if (list.size() > 0) {
					// 取得发送邮箱
					String toMail = new QueryBuilder(
							"select GROUP_CONCAT(CodeName SEPARATOR ',') from zdcode where CodeType='Payment.AbnormalEMail' and ParentCode='Payment.AbnormalEMail' and CodeName is not null and CodeName != ''")
							.executeString();
					if (StringUtil.isNotEmpty(toMail)) {
						Map<String, Object> data = new HashMap<String, Object>();
						data.put("claimsInfo", list);
						ActionUtil.sendMail("wj00309", toMail, data);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
		}
		return flag;
	}

	private Map<String, String> getCaseTypeMap() {

		Map<String, String> result = new HashMap<String, String>();
		DataTable dt = new QueryBuilder(
				"select CodeValue, CodeName from zdcode where CodeType='ClaimsCaseType' and ParentCode='ClaimsCaseType' ")
				.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				result.put(dt.getString(i, 0), dt.getString(i, 1));
			}
		}
		return result;
	}

	private Map<String, Map<String, String>> getPolicyInfo(String policyNos) {

		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT r.policyNo, i.productName, r.insureDate, svaliDate, evaliDate, r.timePrem, r.orderSn, ");
		sb.append("(SELECT CodeName FROM zdcode WHERE CodeType='SupplierCode' AND ParentCode='SupplierCode' AND CodeValue=i.insuranceCompany) company ");
		sb.append("FROM sdinformationrisktype r, sdinformation i WHERE r.orderSn=i.orderSn AND r.policyNo IN ('" + policyNos
				+ "')");
		sb.append("");
		DataTable dt = new QueryBuilder(sb.toString()).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			int i = 0;
			Map<String, String> map;
			for (; i < rowCount; i++) {
				map = new HashMap<String, String>();
				map.put("productName", dt.getString(i, "productName"));
				map.put("insureDate", dt.getString(i, "insureDate"));
				map.put("svaliDate", dt.getString(i, "svaliDate"));
				map.put("evaliDate", dt.getString(i, "evaliDate"));
				map.put("timePrem", dt.getString(i, "timePrem"));
				map.put("company", dt.getString(i, "company"));
				map.put("orderSn", dt.getString(i, "orderSn"));
				result.put(dt.getString(i, "policyNo"), map);
			}
		}
		return result;
	}

	private boolean makeAccountFile() {

		boolean flag = false;
		/**
		 * 获取合作方渠道.
		 */
		// QueryBuilder queryBuilder = new QueryBuilder("where type='asyn'");
		// PartnerInfoSchema parterInfo = new PartnerInfoSchema();
		// PartnerInfoSet parterInfoSet = parterInfo.query(queryBuilder);

		String sql = "select t.insuranceBankSeriNO,o.totalAmount,left(t.insureDate, 10) "
				+ "from sdinformationrisktype t "
				+ "inner join sdorders o on o.ordersn=t.ordersn "
				+ "where ((FIND_IN_SET(riskcode, (SELECT VALUE FROM zdconfig WHERE TYPE = 'LicaiBaoxianProducts')) "
				+ "and o.channelsn in (SELECT partnerinfo.channelsn FROM partnerinfo where type='asyn')) or FIND_IN_SET(riskcode, (SELECT VALUE FROM zdconfig WHERE TYPE = 'BaiNianBaoPinProducts')))"
				+ "and t.appstatus ='1' "
				+ "and left(insureDate, 10) = DATE_SUB(CURDATE(),INTERVAL 1 DAY) ";
		QueryBuilder queryBuilder = new QueryBuilder(sql);
		DataTable dt = queryBuilder.executeDataTable();
		
		String filePath = Config.getValue("BaiNianAccountPath");
		// String filePath = "D:\\document\\account\\";
		int rowCount = dt.getRowCount();
		if (StringUtil.isNotEmpty(filePath)) {
			if (dt == null || dt.getRowCount() == 0) {
				// 没有数据.
				filePath = FileUtil.normalizePath(filePath);
				File fileDir = new File(filePath);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				Date insureDate=DateUtil.addDay(new Date(),-1);
				String fileName=DateUtil.toString(insureDate);
				File file = new File(fileDir, fileName+".txt");
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
				return true;
			}
			String info = "";
			String insureDate = dt.getString(0, 2);
			String fileName = insureDate + ".txt";
			BufferedOutputStream fos = null;
			try {
				int run_total = 1000;
				int count = 0;
				filePath = FileUtil.normalizePath(filePath);
				File fileDir = new File(filePath);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				File file = new File(fileDir, fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				fos = new BufferedOutputStream(new FileOutputStream(file));
				for (int i = 0; i < rowCount; i++) {
					info += (dt.getString(i, "insuranceBankSeriNO") + "," + dt.getString(i, "totalAmount") + "," + insureDate + "\r\n");
					count++;
					if (count == run_total) {
						fos.write(info.getBytes("UTF-8"));
						count = 0;
						info = "";
					}
				}

				if (count > 0 && count < run_total) {
					fos.write(info.getBytes("UTF-8"));
				}
				fos.flush();
				flag = true;
				fos.close();
				fos = null;
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}

		return flag;

		/*
		 * // 取得百年理财险产品 String productIds = new QueryBuilder(
		 * "select GROUP_CONCAT(CodeValue) from zdcode where CodeType='BaiNian.FinancialRisk' and ParentCode='BaiNian.FinancialRisk' "
		 * ) .executeString(); if (StringUtil.isNotEmpty(productIds) &&
		 * !",".equals(productIds)) { productIds = ("'" +
		 * productIds.replace(",", "','") + "'");
		 * 
		 * DataTable dt = new QueryBuilder(
		 * "select applyPolicyNo, timePrem, left(insureDate, 10) from sdinformationrisktype where riskCode in ("
		 * + productIds +
		 * ") and appstatus = '1' and left(insureDate, 10) = DATE_SUB(CURDATE(),INTERVAL 1 DAY) "
		 * ) .executeDataTable(); if (dt != null && dt.getRowCount() > 0) { int
		 * rowCount = dt.getRowCount(); String filePath =
		 * Config.getValue("BaiNianAccountPath"); if
		 * (StringUtil.isNotEmpty(filePath)) { String info = ""; String
		 * insureDate = dt.getString(0, 2); String fileName = insureDate +
		 * ".txt"; BufferedOutputStream fos = null; try { int run_total = 1000;
		 * int count = 0; filePath = FileUtil.normalizePath(filePath); File
		 * fileDir = new File(filePath); if (!fileDir.exists()) {
		 * fileDir.mkdirs(); } fos = new BufferedOutputStream(new
		 * FileOutputStream(filePath + fileName)); for (int i = 0; i < rowCount;
		 * i++) { info += (dt.getString(i, "applyPolicyNo") + "," +
		 * dt.getString(i, "timePrem") + "," + insureDate + "\r\n"); count++; if
		 * (count == run_total) { fos.write(info.getBytes("UTF-8")); count = 0;
		 * info = ""; } }
		 * 
		 * if (count > 0 && count < run_total) {
		 * fos.write(info.getBytes("UTF-8")); } fos.flush(); flag = true;
		 * fos.close(); fos = null; } catch (FileNotFoundException e) {
		 * e.printStackTrace(); } catch (UnsupportedEncodingException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } finally { if (fos != null) { try {
		 * fos.close(); } catch (IOException e) { e.printStackTrace(); } } } } }
		 * } return flag;
		 */
	}

	private boolean BatchDelMark() {

		boolean flag = false;
		try {

			QueryBuilder qb = new QueryBuilder(
					"	SELECT a.id, a.ADDTIME FROM  sdmark  a   LEFT JOIN ServiceCallCollection b ON a.ordersn = b.oldordersn   WHERE TYPE='2' AND b.callConnect ='2'  ");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
			Transaction trans = new Transaction();

			DataTable dt = qb.executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++) {
				int num = daysOfTwo(new Date(), sdf.parse(dt.get(i, "ADDTIME").toString()));

				if (num > 30) {
					QueryBuilder qb1 = new QueryBuilder(" DELETE FROM  sdmark WHERE id = ? and type ='2' ");
					qb1.add(dt.get(i, "id").toString());
					trans.add(qb1);
				}
			}
			if (trans.commit()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return flag;
	}

	public static int daysOfTwo(Date fDate, Date oDate) {

		Calendar aCalendar = Calendar.getInstance();

		aCalendar.setTime(fDate);

		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(oDate);

		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

		return day1 - day2;
	}

	// 平安SFTP 路径
	public static final String SFTP_URL = "/wls/paicsftp/sftp4fkxb/";

	private boolean pinganReconciliation() {

		boolean flag = false;
		try {

			ChannelSftp sftp = SFtpAction.getRateFileInfoByFtp("pingAnTuanXian");

			// 记录文件
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			Date beginDate = new Date();
			Calendar date = Calendar.getInstance();
			date.setTime(beginDate);
			date.set(Calendar.DATE, date.get(Calendar.DATE) - 3);
			Date endDate = sdf.parse(sdf.format(date.getTime()));
			String dateStr = sdf.format(endDate);

			SFtpAction.download(SFTP_URL, "epcisahs_kxb" + dateStr, "ftp/2234/", "epcisahs_kxb" + dateStr + ".txt", sftp);
			try {
				String encoding = "GBK";
				File file = new File("ftp/2234/epcisahs_kxb" + dateStr + ".txt");

				if (file.isFile() && file.exists()) { // 判断文件是否存在
					InputStreamReader read = new InputStreamReader(
							new FileInputStream(file), encoding);// 考虑到编码格式
					BufferedReader bufferedReader = new BufferedReader(read);
					String lineTxt = null;
					Transaction trans = new Transaction();

					while ((lineTxt = bufferedReader.readLine()) != null) {
						String[] valFpt = lineTxt.split("\\|");
						QueryBuilder qb = new QueryBuilder(
								"  INSERT INTO sdftpbackup VALUE (?,?,?,?,?,?,?,?,?,now(),'2234','');");
						String id_index = NoUtil.getMaxNo("DICTIONARYID") + "";
						qb.add(id_index);
						for (int j = 0; j < valFpt.length; j++) {

							qb.add(valFpt[j]);
						}
						trans.add(qb);
					}
					if (trans.commit()) {
						flag = true;
					}
					read.close();
				} else {
					logger.error("找不到指定的文件");
				}
			} catch (Exception e) {
				logger.error("读取文件内容出错" + e.getMessage(), e);
			}

		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error(e.getMessage(), e);
		}
		return flag;
	}

	/**
	 * zjfaeAccountFile:浙金所对账文件. <br/>
	 *
	 * @author wwy
	 * @return
	 */
	private boolean zjfaeAccountFile() {

		boolean flag = false;

		String sql = "select distinct m.mBindInfoForLogin_id as fundAccount, '1001' as tradeType, '00' as tradeFlag, t.PaySn as orderId, t.SubOrderId, r.policyNo, "
				+ " '1' AS appStatus, r.cancelDate, t.TradeDate, r.svaliDate, r.payPrice, t.tradeid as tradeid "
				+ " from ZjfaeTradeInfo t, sdorders o, member m, sdinformationrisktype r "
				+ " where t.OrderSn = o.ordersn and o.memberid = m.id and r.orderSn = o.orderSn and t.suborderId = r.recognizeeSn and o.channelsn = 'zjfae' "
				+ " and DATE_FORMAT(r.insureDate, '%Y-%m-%d') = DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY), '%Y-%m-%d') "
				+ " and o.orderStatus >=7 ";
		sql += " UNION ALL "
				+ "select distinct m.mBindInfoForLogin_id as fundAccount, '2000' as tradeType, IF(t.Refundflag = '00', '00', '99') as tradeFlag, t.RefundId as orderId, t.SubOrderId, r.policyNo, "
				+ " '2' AS appStatus, r.cancelDate, t.TradeDate, r.svaliDate, r.payPrice, t.Receiverefundid as tradeid "
				+ " from ZjfaeTradeInfo t, sdorders o, member m, sdinformationrisktype r "
				+ " where t.OrderSn = o.ordersn and o.memberid = m.id and r.orderSn = o.orderSn and t.suborderId = r.recognizeeSn and o.channelsn = 'zjfae' "
				+ " and DATE_FORMAT(r.cancelDate, '%Y-%m-%d') = DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY), '%Y-%m-%d') "
				+ " and (r.appStatus in ('2','4') or t.Refundflag = '00') ";
		QueryBuilder queryBuilder = new QueryBuilder(sql);
		DataTable dt = queryBuilder.executeDataTable();

		/*
		 * if (dt == null || dt.getRowCount() == 0) { // 没有数据. return true; }
		 */
		String filePath = Config.getValue("ZjfaeAccountPath");
		// String filePath = "D:\\document\\zjfaeaccount\\";
		if (StringUtil.isNotEmpty(filePath)) {
			String fileName = DateUtil.getCurrentDate(DateUtil.Format_Date2) + ".csv";
			try {
				filePath = filePath + "/" + DateUtil.getCurrentDate(DateUtil.Format_Date2);
				filePath = FileUtil.normalizePath(filePath);
				File fileDir = new File(filePath);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				File file = new File(fileDir, fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				List<List<String>> rows = new ArrayList<List<String>>();

				for (int i = 0; i < dt.getRowCount(); i++) {
					List<String> row = new ArrayList<String>();

					row.add(dt.getString(i, "fundAccount"));
					row.add(dt.getString(i, "tradeType"));
					row.add(dt.getString(i, "tradeFlag"));
					row.add(dt.getString(i, "orderId"));
					row.add(dt.getString(i, "SubOrderId"));
					row.add(dt.getString(i, "policyNo"));
					row.add(dt.getString(i, "appStatus"));
					row.add(dt.getString(i, "cancelDate"));
					row.add(dt.getString(i, "TradeDate"));
					row.add(dt.getString(i, "svaliDate"));
					row.add(dt.getString(i, "payPrice"));
					row.add(dt.getString(i, "tradeid"));

					rows.add(row);
				}

				logger.info("浙金所对账文件生成地址：{}", file.getPath());
				CSVUtils.writeCSV(rows, file.getPath(), "UTF-8");

				File fileSuccess = new File(fileDir, fileName.replace("csv", "success"));
				if (!fileSuccess.exists()) {
					fileSuccess.createNewFile();
				}
				flag = true;
			} catch (Exception e) {
				logger.error("浙金所对账文件处理异常" + e.getMessage(), e);
			}
		}
//		partnerAccountFile();

		return flag;
	}

	private boolean partnerAccountFile() {

		boolean flag = false;

		String yesDay = DateUtil.toString(DateUtil.addDay(new Date(), -1), DateUtil.Format_Date);

		DataTable partnerInfo = new QueryBuilder("select  * from partnerInfo where prop3 is not null").executeDataTable();
		for (int i = 0; i < partnerInfo.getRowCount(); i++) {
			String sql = "SELECT DISTINCT o.payType,o.PaySn,r.recognizeeSn,r.policyNo,'1' AS appStatus,r.cancelDate,r.insureDate,r.svaliDate,r.evaliDate, "
					+ " r.payPrice,t.tradeSeriNO,t.receiveDate,i.recognizeeName,i.recognizeeIdentityType,i.recognizeeIdentityId "
					+ " FROM sdorders o,sdinformationrisktype r,tradeinformation t,sdinformationinsured i "
					+ " WHERE r.orderSn = o.orderSn AND r.orderSn = t.ordID AND i.recognizeeSn = r.recognizeeSn AND DATE_FORMAT(t.receiveDate, '%Y-%m-%d') = '"
					+ yesDay
					+ "' AND o.orderStatus >= 7 "
					+ " and o.channelsn = '"
					+ partnerInfo.get(i).getString("channelsn")
					+ "' "
					+ " UNION ALL "
					+ "SELECT DISTINCT o.payType,o.PaySn,r.recognizeeSn,r.policyNo,'1' AS appStatus,r.cancelDate,r.insureDate,r.svaliDate,r.evaliDate, "
					+ " r.payPrice,t.tradeSeriNO,t.receiveDate,i.recognizeeName,i.recognizeeIdentityType,i.recognizeeIdentityId "
					+ " FROM sdorders o,sdinformationrisktype r,tradeinformation t,sdinformationinsured i "
					+ " WHERE r.orderSn = o.orderSn AND r.orderSn = t.ordID AND i.recognizeeSn = r.recognizeeSn AND DATE_FORMAT(t.receiveDate, '%Y-%m-%d') = '"
					+ yesDay
					+ "' AND ( r.appStatus IN ('2', '4') )"
					+ " and o.channelsn = '" 
					+ partnerInfo.get(i).getString("channelsn") 
					+ "' ";
			DataTable tradeinfo = new QueryBuilder(sql).executeDataTable();
			try {
				String filepath = (String) partnerInfo.get(i).get("prop3");
				String fileName = DateUtil.getCurrentDate(DateUtil.Format_Date2) + ".csv";
				filepath = filepath + "/" + DateUtil.getCurrentDate(DateUtil.Format_Date2);
				filepath = FileUtil.normalizePath(filepath);
				File fileDir = new File(filepath);
				if (!fileDir.exists()) {
					fileDir.mkdirs();
				}
				File file = new File(fileDir, fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				List<List<String>> rows = new ArrayList<List<String>>();
				for (int j = 0; j < tradeinfo.getRowCount(); j++) {
					List<String> row = new ArrayList<String>();
					row.add(tradeinfo.get(j).getString("payType"));
					row.add(tradeinfo.get(j).getString("PaySn"));
					row.add(tradeinfo.get(j).getString("recognizeeSn"));
					row.add(tradeinfo.get(j).getString("policyNo"));
					row.add(tradeinfo.get(j).getString("appStatus"));
					row.add(tradeinfo.get(j).getString("cancelDate"));
					row.add(tradeinfo.get(j).getString("insureDate"));
					row.add(tradeinfo.get(j).getString("receiveDate"));
					row.add(tradeinfo.get(j).getString("svaliDate"));
					row.add(tradeinfo.get(j).getString("evaliDate"));
					row.add(tradeinfo.get(j).getString("payPrice"));
					row.add(tradeinfo.get(j).getString("tradeSeriNO"));
					row.add(tradeinfo.get(j).getString("recognizeeName"));
					row.add(tradeinfo.get(j).getString("recognizeeIdentityType"));
					row.add(tradeinfo.get(j).getString("recognizeeIdentityId"));
					rows.add(row);
				}
				if (rows.size() > 0) {
					logger.info("{}对账文件生成地址：{}",partnerInfo.get(i).getString("channelsn"), file.getPath());
					CSVUtils.writeCSV(rows, file.getPath(), "UTF-8");
				}
				File fileSuccess = new File(fileDir, fileName.replace("csv", "success"));
				if (!fileSuccess.exists()) {
					fileSuccess.createNewFile();
				}
				flag = true;
			} catch (Exception e) {
				logger.error("浙金所对账文件处理异常" + e.getMessage(), e);
			}
		}
		return flag;
	}

	public String getCode() {

		return CODE;
	}

	public String getName() {

		return "网金定时任务调用";
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

		return "com.sinosoft.datachannel.WJServiceManager";
	}
}