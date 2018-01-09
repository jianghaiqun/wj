package com.sinosoft.cms.resend;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.util.Constant;
import cn.com.sinosoft.util.DownloadNet;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.jdt.tb.TBDealInterfaceNew;
import com.sinosoft.lis.f1print.ElectronicPolicyPrint;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import com.sinosoft.utility.VData;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ResendInsureTransfer
 * @Description: TODO(重发-调用经代通.)
 * @author CongZN
 * @date 2014-10-16 下午02:06:44
 * 
 *       <p>
 *       修改历史
 *       </p>
 *       <p>
 *       序号 日期 修改人 修改原因
 *       </p>
 */
public class ResendInsureTransfer {
	private static final Logger logger = LoggerFactory.getLogger(ResendInsureTransfer.class);

	private String ErrMsg;

	/**
	 * @Title: callInsTransInterface.
	 * @Description: TODO(调用经代通,按被保人调用).
	 * @param p_orderSn
	 * @param insureTypeCode
	 * @param p_insuredSn
	 *            void.
	 * @author CongZN.
	 */
	@SuppressWarnings("static-access")
	public void callInsTransInterface(String p_orderSn, String insureTypeCode,
			String p_insuredSn) {

		String policyPath = "";
		HashMap<String, Object> toMap = null;
		List<HashMap<String, String>> insuredSnList = null;
		List<HashMap<String, String>> pathList = new ArrayList<HashMap<String, String>>();
		DownloadNet db = new DownloadNet();
		try {
			String channelsn = "wj";
			DataTable result_insuredDetail = new DataTable();
			String sqlGroupType = "SELECT a.ordersn,b.GroupType,a.ChannelSn FROM sdorders a LEFT JOIN sdorderitemoth b ON a.ordersn=b.ordersn WHERE a.ordersn=? limit 1";
			QueryBuilder queryBuilder = new QueryBuilder(sqlGroupType);
			queryBuilder.add(p_orderSn);
			DataTable dt = queryBuilder.executeDataTable();
			// 美行保团单 按照订单号更新数据 bug	0003102: 美行保新平台团单自动从发只更新一条risktype表的记录
			if(dt != null && dt.getRowCount() > 0){
				channelsn = dt.getString(0, "ChannelSn");
				String groupType = dt.getString(0, 1);
				if((Constant.B2B_HT_CHANNELSN.equals(channelsn)||
						Constant.B2B_HT_CHANNELSN01.equals(channelsn)||
						Constant.B2B_HT_CHANNELSN02.equals(channelsn)||
						Constant.B2C_PC_CHANNELSN.equals(channelsn)||
						Constant.B2C_WAP_CHANNELSN.equals(channelsn)) &&
						(StringUtil.isNotEmpty(groupType)&&"g".equals(groupType))){

					String query_insuredDetailSql = "select id,recognizeeSn, insuredSn,recognizeeName from SDInformationInsured where ordersn=?";
					QueryBuilder query_insuredDetail = new QueryBuilder(
							query_insuredDetailSql);
					query_insuredDetail.add(p_orderSn);
					result_insuredDetail = query_insuredDetail
							.executeDataTable();
				}else{
					String query_insuredDetailSql = "select id,recognizeeSn, insuredSn,recognizeeName from SDInformationInsured where insuredSn=?";
					QueryBuilder query_insuredDetail = new QueryBuilder(
							query_insuredDetailSql);
					query_insuredDetail.add(p_insuredSn);
					result_insuredDetail = query_insuredDetail
							.executeDataTable();
				}
				
			}

			for (int i = 0; i < result_insuredDetail.getRowCount(); i++) {

				//toMap = new HashMap<String, Object>();
				String recognizeeSn = result_insuredDetail.getString(i,
						"recognizeeSn");
				String recognizeeName = result_insuredDetail.getString(i,
						"recognizeeName");

				if (p_insuredSn != null && !"".equals(p_insuredSn)) {

					SDInformationRiskTypeSchema sdInformationSchema = new SDInformationRiskTypeSchema();
					SDInformationRiskTypeSet sdInformationSet = new SDInformationRiskTypeSet();

					sdInformationSet = sdInformationSchema
							.query(new QueryBuilder(
									"where OrderSn = ? and RecognizeeSn = ?",
									p_orderSn, recognizeeSn));
					if (sdInformationSet != null && sdInformationSet.size() > 0) {
						sdInformationSchema = sdInformationSet.get(0);

						if (sdInformationSchema == null) {
							continue;
						}

						String id = sdInformationSchema.getId();
						String sql2 = "select Memo from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
						QueryBuilder qb = new QueryBuilder(sql2);

						qb.add("TBDealClassName");
						qb.add("TBDealClassName");
						qb.add(insureTypeCode);

						String className = qb.executeString();
						if (StringUtil.isNotEmpty(className)) {
							this.writeTXT("承保：调用金代通开始" + " - 订单号" + p_orderSn);

							if(dt != null && dt.getRowCount() > 0){
								if(StringUtil.isNotEmpty(dt.getString(0, 1))&&"g".equals(dt.getString(0, 1))){
									if(i == 0){
										toMap = new HashMap<String, Object>();
										Class<?> tbDeal = Class.forName(className);
										TBDealInterfaceNew tbDI = (TBDealInterfaceNew) tbDeal
												.newInstance();
										tbDI.dealData(toMap, insureTypeCode, p_orderSn,
												p_insuredSn);
									}
								}else{
									toMap = new HashMap<String, Object>();
									Class<?> tbDeal = Class.forName(className);
									TBDealInterfaceNew tbDI = (TBDealInterfaceNew) tbDeal
											.newInstance();
									tbDI.dealData(toMap, insureTypeCode, p_orderSn,
											p_insuredSn);
								}
							}
						
							this.writeTXT("承保：调用金代通结束" + " - 订单号" + p_orderSn);
						
						} else {
							if (insureTypeCode.startsWith("2034")) {
								sendExcel(p_orderSn, p_insuredSn);
								// 模拟map结果
								toMap.put("TRAN_CODE", "");
								toMap.put("BK_SERIAL", "");
								toMap.put("PA_RSLT_CODE", "");
								toMap.put("PA_RSLT_MESG", "");
								toMap.put("applyPolicyNo", "");
								toMap.put("policyNo", "");
								toMap.put("BDZT", "");
								toMap.put("appStatus", "1");// 标记成功或失败状态
							}
						}

						// 1015华泰人寿、2049安联、2096安盛天平
						if (insureTypeCode.equals("1015")
								|| insureTypeCode.equals("2049")
								|| insureTypeCode.equals("2096")) {
							sdInformationSet = sdInformationSchema
									.query(new QueryBuilder(
											"where orderSn = ? and RecognizeeSn = ?",
											p_orderSn, recognizeeSn));
							if (sdInformationSet != null
									&& sdInformationSet.size() > 0) {
								sdInformationSchema = sdInformationSet.get(0);
								Double timePrem = 0.0;
								Double returnPremiums = 0.0;
								if (StringUtil.isNotEmpty(String.valueOf(toMap
										.get("totalPremium")))) {
									returnPremiums = Double.parseDouble(toMap
											.get("totalPremium").toString());
								}
								if (StringUtil.isNotEmpty(sdInformationSchema
										.gettimePrem())) {
									timePrem = Double
											.parseDouble(sdInformationSchema
													.gettimePrem());
								}
								// 只有已承保的订单才校验返回保费是否一致
								if ("1".equals(String.valueOf(toMap
										.get("appStatus")))) {
									if (timePrem - returnPremiums != 0.0) {
										DownloadNet down = new DownloadNet();
										down.sendPrintErrorMail(p_orderSn,
												p_insuredSn,
												"网站计算出的保费与保险公司返回的保费不一致", toMap);
									}
								}
							}
						}

						// 2007平安财险
						if ("2007".equals(insureTypeCode)
								&& "1".equals(toMap.get("appStatus"))) {
							String policyNo = toMap.get("policyNo") + "";
							String validateCode = toMap.get("validateCode")
									+ "";
							if (StringUtil.isNotEmpty(policyNo)
									&& StringUtil.isNotEmpty(validateCode)) {
								policyPath = checkElectronicPolicyIsSuccess(
										p_orderSn, p_insuredSn, policyNo,
										validateCode);
								if (StringUtil.isNotEmpty(policyPath)) {
									HashMap<String, String> map = new HashMap<String, String>();
									map.put("recognizeeName", recognizeeName);
									map.put("path", policyPath);
									map.put("insuredSn", p_insuredSn);
									pathList.add(map);
									toMap.put("remark1", policyPath);
								}
							}

							// 2071大众、2074太阳联合、2049安联
						} else if ("2071".equals(insureTypeCode)
								|| "2074".equals(insureTypeCode)
								|| "2049".equals(insureTypeCode)
								&& "1".equals(toMap.get("appStatus"))) {
							policyPath = toMap.get("remark1") + "";
							if (StringUtil.isNotEmpty(policyPath)) {
								HashMap<String, String> map = new HashMap<String, String>();
								map.put("path", policyPath);
								map.put("recognizeeName", recognizeeName);
								map.put("insuredSn", p_insuredSn);
								pathList.add(map);
								int j = policyPath.lastIndexOf("/") + 1;
								String name = policyPath.substring(j,
										policyPath.length());
								db.saveOrdersPrint(p_orderSn, name, policyPath,
										p_insuredSn, "下载开始");
							}

						}

						this.updateResultMap(toMap, id);
					}
				}
			}
		} catch (Exception e) {
			logger.error("调用经代通接口出现异常" + e.getMessage(), e);
			ErrMsg = "调用经代通接口出现异常";
			ActionUtil.sendInsureAlarmMailByOrderSn(p_orderSn, ErrMsg);
		} finally {
			if (insuredSnList != null && insuredSnList.size() > 0
					&& pathList.size() > 0) {
				db.getGeneratepolicy(insuredSnList, pathList, p_orderSn,
						insureTypeCode);
			}
		}

	}

	/**
	 * @Title: toGenerateElectronicPolicy.
	 * @Description: TODO(toGenerateElectronicPolicy).
	 * @param orderSN
	 * @param insuredSn
	 * @param policyNo
	 * @param validateCode
	 * @return String.
	 * @author CongZN.
	 */
	@SuppressWarnings("unchecked")
	public String toGenerateElectronicPolicy(String orderSN, String insuredSn,
			String policyNo, String validateCode) {
		try {
			GlobalInput tG = new GlobalInput();
			tG.Operator = "SysAuto";
			// 将用户信息与保单信息放入容器
			VData cInputData = new VData();
			cInputData.addElement(tG);
			cInputData.addElement(orderSN);// OrderSN
			cInputData.addElement(insuredSn);
			cInputData.addElement(policyNo);
			cInputData.addElement(validateCode);
			ElectronicPolicyPrint tElectronicPolicyPrint = new ElectronicPolicyPrint();
			// 调用保单打印程序进行打印
			if (tElectronicPolicyPrint.submitData(cInputData, "")) {
				logger.info("保单打印成功！保单存储路径为：{}", tElectronicPolicyPrint.getResult().get("ResultPath"));
				return tElectronicPolicyPrint.getResult().get("ResultPath")
						.toString();
			} else {
				logger.info("保单打印失败！原因是：{}", tElectronicPolicyPrint.getError().getFirstError());
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * @Title: checkElectronicPolicyIsSuccess.
	 * @Description: TODO(checkElectronicPolicyIsSuccess).
	 * @param orderID
	 * @param insuredSn
	 * @param policyNo
	 * @param validateCode
	 * @return String.
	 * @author CongZN.
	 */
	public String checkElectronicPolicyIsSuccess(String orderID,
			String insuredSn, String policyNo, String validateCode) {
		String returnFlag = toGenerateElectronicPolicy(orderID, insuredSn,
				policyNo, validateCode);
		if (returnFlag == null || "".equals(returnFlag)) {
			int i = 0;
			while (i < 5) {
				String returnFlag1 = toGenerateElectronicPolicy(orderID,
						insuredSn, policyNo, validateCode);
				if (returnFlag1 != null && !"".equals(returnFlag1)) {
					return returnFlag1;
				}
				i++;
			}
			return null;
		} else {
			return returnFlag;
		}
	}

	/**
	 * @Title: updateResultMap.
	 * @Description: TODO(更新返回数据).
	 * @param reMap
	 * @param ID
	 *            void.
	 * @author CongZN.
	 */
	private void updateResultMap(HashMap<String, Object> reMap, String ID) {
		try {
			Transaction transaction = new Transaction();
			SDInformationRiskTypeSchema sdInformationSchema = new SDInformationRiskTypeSchema();
			SDInformationRiskTypeSet sdInformationSet = new SDInformationRiskTypeSet();
			sdInformationSet = sdInformationSchema.query(new QueryBuilder(
					"where id = ? ", ID));
			SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String insureDateString = sdf_2.format(new Date());
			if (!sdInformationSet.isEmpty() && sdInformationSet.size() > 0) {
				sdInformationSchema = sdInformationSet.get(0);
				sdInformationSchema.setmodifyDate(new Date());
				sdInformationSchema.setinsureDate(insureDateString);
				
				if(!StringUtil.isEmpty(reMap.get("applyPolicyNo"))){
					sdInformationSchema.setapplyPolicyNo(reMap.get("applyPolicyNo").toString());// 投保单号
				}
				
				sdInformationSchema.setpolicyNo(reMap.get("policyNo") == null ? "" : reMap .get("policyNo").toString());// 保单号
				sdInformationSchema
						.setnoticeNo(reMap.get("noticeNo") == null ? "" : reMap
								.get("noticeNo").toString());// 财务通知单号
				sdInformationSchema
						.setvalidateCode(reMap.get("validateCode") == null ? ""
								: reMap.get("validateCode").toString());// 保单验证码
				if (reMap.get("totalPremium") != null) {
					sdInformationSchema.setreturnPremiums(reMap.get(
							"totalPremium").toString());// 保单保费
				}
				sdInformationSchema
						.setappStatus(reMap.get("appStatus") == null ? ""
								: reMap.get("appStatus").toString());// 投保状态
				sdInformationSchema.setinsuranceTransCode(reMap
						.get("TRAN_CODE") == null ? "" : reMap.get("TRAN_CODE")
						.toString());// 保险公司返回交易流水号
				sdInformationSchema
						.setinsuranceBankCode(reMap.get("BANK_CODE") == null ? ""
								: reMap.get("BANK_CODE").toString());// 保险公司返回银行编码
				sdInformationSchema.setinsuranceBankSeriNO(reMap
						.get("BK_SERIAL") == null ? "" : reMap.get("BK_SERIAL")
						.toString());// 保险公司返回银行交易流水号
				sdInformationSchema
						.setinsuranceBRNO(reMap.get("BRNO") == null ? ""
								: reMap.get("BRNO").toString());// 保险公司返回系列号
				sdInformationSchema
						.setinsuranceTELLERNO(reMap.get("TELLERNO") == null ? ""
								: reMap.get("TELLERNO").toString());// 保险公司反回商户号
				sdInformationSchema
						.setinsurerFlag(reMap.get("PA_RSLT_CODE") == null ? ""
								: reMap.get("PA_RSLT_CODE").toString());// 保险公司返回投保结果编码
				sdInformationSchema
						.setinsureMsg(reMap.get("PA_RSLT_MESG") == null ? ""
								: reMap.get("PA_RSLT_MESG").toString());// 保险公司返回投保结果信息
				sdInformationSchema
						.setelectronicCout(reMap.get("policyPath") == null ? ""
								: reMap.get("policyPath").toString());// 保险公司返回的保单下载路径
				sdInformationSchema
						.setelectronicPath(reMap.get("remark1") == null ? ""
								: reMap.get("remark1").toString());// 保险公司返回保单下载物理路径
				sdInformationSchema.setbalanceFlag("0");
				
				//added by yuzj @20160830 for 泰康在线住院保===begin===
				if (reMap.get("startDate") != null) {
					sdInformationSchema.setsvaliDate(DateUtil.parseDateTime(reMap.get("startDate").toString()));
				}
				if (reMap.get("endDate") != null) {
					sdInformationSchema.setevaliDate(DateUtil.parseDateTime(reMap.get("endDate").toString()));
				}
				
				transaction.add(sdInformationSchema, Transaction.UPDATE);
				transaction.commit();
			}
		} catch (Exception e) {
			logger.error("类InsureTransfer方法updateResultMap()出现异常"
					+ e.getMessage(), e);
		}
	}

	/**
	 * @Title: writeTXT.
	 * @Description: TODO(记录金代通发送时间、返回金代通时间).
	 * @param content
	 *            void.
	 * @author CongZN.
	 */
	public void writeTXT(String content) {
		FileWriter fw = null;
		try {
			String filepath = Config.getContextRealPath() + File.separator
					+ "wjtojdt" + File.separator + PubFun.getCurrentDate()
					+ ".txt";
			File tFile = new File(filepath);
			File parent = tFile.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}

			if (!tFile.exists()) {
				tFile.createNewFile();
			}
			fw = new FileWriter(tFile, true);
			fw.write(PubFun.getCurrentTime() + " : " + content + "\r\n");
			fw.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);

		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * @Title: sendExcel.
	 * @Description: TODO(sendExcel).
	 * @param orderID
	 * @param insuredSn
	 *            void.
	 * @author CongZN.
	 */
	public void sendExcel(String orderID, String insuredSn) {
		try {
			DealWithSendExcel(orderID, insuredSn);
			Map<String, Object> emailMap = new HashMap<String, Object>();
			File path = new File(InsureTransferNew.class.getResource("/")
					.getFile());
			String folderPath = path.getParentFile().getParentFile().toString();
			String policyPath = folderPath + "/template/2034/"
					+ PubFun.getCurrentDate() + "/" + insuredSn + ".xls";
			GetDBdata db1 = new GetDBdata();
			Member m1 = new Member();
			m1
					.setEmail(db1
							.getOneValue("select value from zdconfig where type='MYExcelEmail'"));
			String sql = "select a.ProductName as productName,a.ProductOutCode,a.PlanCode,a.StartDate,a.EndDate from SDInformation a where a.orderSn = ?";
			DataTable dt = new QueryBuilder(sql, orderID).executeDataTable();
			if (dt.getRowCount() > 0) {
				emailMap.put("ProductName", dt.getString(0, 0));
				if (dt.getString(0, 0) != null
						&& !"".equals(dt.getString(0, 2))) {
					emailMap.put("ProductCode", dt.getString(0, 2));
				} else {
					emailMap.put("ProductCode", dt.getString(0, 1));
				}
				emailMap.put("Effective", stringToDate(dt.getString(0, 3),
						"yyyy/MM/dd"));
				emailMap.put("Fail", stringToDate(dt.getString(0, 4),
						"yyyy/MM/dd"));

			} else {
				logger.warn("未查询到订单及保单相关信息");
			}
			emailMap.put("FilePath", policyPath);
			emailMap.put("FileName", insuredSn + ".xls");
			emailMap.put("Member", m1);
			ActionUtil.dealAction("wj00053", emailMap, null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @Title: stringToDate.
	 * @Description: TODO(按指定的format输出日期字符串).
	 * @param dateStr
	 * @param format
	 * @return String.
	 * @author CongZN.
	 */
	public String stringToDate(String dateStr, String format) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat t = new SimpleDateFormat(format);
		String formatString = "";
		try {
			formatString = t.format(sdf.parse(dateStr));
		} catch (ParseException e) {
			formatString = null;
			logger.error(e.getMessage(), e);
		}
		return formatString;
	}

	/**
	 * @Title: DealWithSendExcel.
	 * @Description: TODO(DealWithSendExcel).
	 * @param orderID
	 * @param insuredSn
	 *            void.
	 * @author CongZN.
	 */
	public void DealWithSendExcel(String orderID, String insuredSn) {
		WritableWorkbook wwb = null;
		try {
			String policyPath = null;
			File path = new File(InsureTransferNew.class.getResource("/")
					.getFile());
			String folderPath = path.getParentFile().getParentFile().toString();
			policyPath = folderPath + "/template/2034/"
					+ PubFun.getCurrentDate() + "/";
			File folderIn = new File(policyPath);
			if (!folderIn.exists()) {
				folderIn.mkdirs();
			}
			wwb = Workbook.createWorkbook(new File(policyPath + insuredSn
					+ ".xls"));
			WritableSheet sheet = wwb.createSheet("sheet", 0);
			Label label;
			WritableCellFormat font = new WritableCellFormat();
			WritableCellFormat background = new WritableCellFormat();
			font.setAlignment(Alignment.CENTRE);
			background.setBackground(Colour.YELLOW);
			label = new Label(0, 0, "产品代码", font);
			sheet.setColumnView(0, 15);
			sheet.addCell(label);
			label = new Label(1, 0, "产品名称", font);
			sheet.setColumnView(1, 30);
			sheet.addCell(label);
			label = new Label(2, 0, "保险起期", font);
			sheet.setColumnView(2, 10);
			sheet.addCell(label);
			label = new Label(3, 0, "保险止期", font);
			sheet.setColumnView(3, 10);
			sheet.addCell(label);
			label = new Label(4, 0, "投保人姓名", font);
			sheet.setColumnView(4, 10);
			sheet.addCell(label);
			label = new Label(5, 0, "投保人生日", font);
			sheet.setColumnView(5, 10);
			sheet.addCell(label);
			label = new Label(6, 0, "投保人证件类型", font);
			sheet.setColumnView(6, 15);
			sheet.addCell(label);
			label = new Label(7, 0, "投保人证件号", font);
			sheet.setColumnView(7, 20);
			sheet.addCell(label);
			label = new Label(8, 0, "投保人手机号", font);
			sheet.setColumnView(8, 15);
			sheet.addCell(label);
			label = new Label(9, 0, "投保人与被保人关系", font);
			sheet.setColumnView(9, 20);
			sheet.addCell(label);
			label = new Label(10, 0, "投保人邮箱", font);
			sheet.setColumnView(10, 25);
			sheet.addCell(label);
			label = new Label(11, 0, "被保人姓名", font);
			sheet.setColumnView(11, 10);
			sheet.addCell(label);
			label = new Label(12, 0, "被保人证件类型", font);
			sheet.setColumnView(12, 15);
			sheet.addCell(label);
			label = new Label(13, 0, "被保人证件号", font);
			sheet.setColumnView(13, 20);
			sheet.addCell(label);
			label = new Label(14, 0, "被保人生日", font);
			sheet.setColumnView(14, 10);
			sheet.addCell(label);
			label = new Label(15, 0, "被保人邮箱", font);
			sheet.setColumnView(15, 25);
			sheet.addCell(label);
			String sql = "select a.ProductId,a.ProductName,a.StartDate,a.EndDate,b.ApplicantName,b.ApplicantBirthday,b.ApplicantIdentityType,b.ApplicantIdentityId,b.ApplicantMobile,c.RecognizeeAppntRelation,b.ApplicantMail,c.RecognizeeName,c.RecognizeeIdentityType,c.RecognizeeIdentityId,c.RecognizeeBirthday,c.RecognizeeMail "
					+ " from SDInformation a ,SDInformationAppnt b, SDInformationInsured c "
					+ "where a.InformationSn = b.InformationSn and a.InformationSn=c.InformationSn  and  c.OrderSn = ? and c.InsuredSn = ?";
			DataTable dt = new QueryBuilder(sql, orderID, insuredSn)
					.executeDataTable();
			if (dt.getRowCount() > 0) {
				label = new Label(0, 1, dt.getString(0, 0), font);
				sheet.addCell(label);
				label = new Label(1, 1, dt.getString(0, 1), font);
				sheet.addCell(label);
				label = new Label(2, 1, stringToDate(dt.getString(0, 2),
						"yyyy/MM/dd"), font);
				sheet.addCell(label);
				label = new Label(3, 1, stringToDate(dt.getString(0, 3),
						"yyyy/MM/dd"), font);
				sheet.addCell(label);
				label = new Label(4, 1, dt.getString(0, 4), font);
				sheet.addCell(label);
				label = new Label(5, 1, fomatBirthday(dt.getString(0, 5),
						"yyyy/MM/dd"), font);
				sheet.addCell(label);
				if (dt.getString(0, 6) != null
						&& !"".equals(dt.getString(0, 6))) {
					String cType = "";
					if ("0".equals(dt.getString(0, 6))) {
						cType = "身份证";
					} else if ("7".equals(dt.getString(0, 6))) {
						cType = "护照";
					} else if ("99".equals(dt.getString(0, 6))) {
						cType = "其他";
					}
					label = new Label(6, 1, cType, font);
					sheet.addCell(label);
				} else {
					label = new Label(6, 1, dt.getString(0, 6), font);
					sheet.addCell(label);
				}
				label = new Label(7, 1, dt.getString(0, 7), font);
				sheet.addCell(label);
				label = new Label(8, 1, dt.getString(0, 8), font);
				sheet.addCell(label);
				if (dt.getString(0, 9) != null
						&& !"".equals(dt.getString(0, 9))) {
					String reShip = "";
					if ("00".equals(dt.getString(0, 9))) {
						reShip = "本人";
					} else if ("01".equals(dt.getString(0, 9))) {
						reShip = "配偶";
					} else if ("02".equals(dt.getString(0, 9))) {
						reShip = "父母";
					} else if ("03".equals(dt.getString(0, 9))) {
						reShip = "子女";
					} else if ("99".equals(dt.getString(0, 9))) {
						reShip = "其他";
					}
					label = new Label(9, 1, reShip, font);
					sheet.addCell(label);
				} else {
					label = new Label(9, 1, dt.getString(0, 9), font);
					sheet.addCell(label);
				}
				label = new Label(10, 1, dt.getString(0, 10), font);
				sheet.addCell(label);
				label = new Label(11, 1, dt.getString(0, 11), font);
				sheet.addCell(label);
				if (dt.getString(0, 12) != null
						&& !"".equals(dt.getString(0, 12))) {
					String cType1 = "";
					if ("0".equals(dt.getString(0, 12))) {
						cType1 = "身份证";
					} else if ("7".equals(dt.getString(0, 12))) {
						cType1 = "护照";
					} else if ("99".equals(dt.getString(0, 12))) {
						cType1 = "其他";
					}
					label = new Label(12, 1, cType1, font);
					sheet.addCell(label);
				} else {
					label = new Label(12, 1, dt.getString(0, 12), font);
					sheet.addCell(label);
				}
				label = new Label(13, 1, dt.getString(0, 13), font);
				sheet.addCell(label);
				label = new Label(14, 1, fomatBirthday(dt.getString(0, 14),
						"yyyy/MM/dd"), font);
				sheet.addCell(label);
				label = new Label(15, 1, dt.getString(0, 15), font);
				sheet.addCell(label);
			} else {
				logger.warn("未查询到投被保人相关信息");
			}
			wwb.write();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				// 关闭文件
				wwb.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * @Title: fomatBirthday.
	 * @Description: TODO(格式化生日).
	 * @param dateStr
	 * @param format
	 * @return String.
	 * @author CongZN.
	 */
	public String fomatBirthday(String dateStr, String format) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat t = new SimpleDateFormat(format);
		String formatString = "";
		try {
			formatString = t.format(sdf.parse(dateStr));
		} catch (ParseException e) {
			formatString = null;
			logger.error(e.getMessage(), e);
		}
		return formatString;
	}
}
