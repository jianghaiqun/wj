package com.sinosoft.jdt;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.util.Constant;
import cn.com.sinosoft.util.DownloadNet;
import com.activemq.ProducerMQ;
import com.alibaba.fastjson.JSON;
import com.sinosoft.cms.resend.ResendMain;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.tb.TBDealInterfaceNew;
import com.sinosoft.lis.f1print.ElectronicPolicyPrint;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.InsuredCompanyReturnDataSchema;
import com.sinosoft.schema.InsuredCompanyReturnDataSet;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDInformationSet;
import com.sinosoft.utility.VData;
import com.wangjin.cms.orders.QueryOrders;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
public class InsureTransferNew {
	private static final Logger logger = LoggerFactory.getLogger(InsureTransferNew.class);

	private String ErrMsg ;
	/**
	 * 通过orderID获取交易信息
	 * 
	 * @param orderID   
	 * @return
	 */
	private List<HashMap<String, String>> getInfoTradeListByOrderId(String orderID) {
		List<HashMap<String, String>> infoTradeList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from TradeInformation where ordId = ?";
			String[] sqltemp = {orderID};
			infoTradeList = new GetDBdata().query(sql,sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoTradeList = null;
		}
		return infoTradeList;
	}

	/**
	 * 通过information_id获得被保人信息列表
	 * 
	 * @param info
	 * @return
	 */
	private List<HashMap<String, String>> getInfoInsuredListByInfo(String infomationID) {
		List<HashMap<String, String>> infoInsuredList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from InformationInsured where information_id = ?";
			String[] sqltemp = {infomationID};
			infoInsuredList = new GetDBdata().query(sql,sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoInsuredList = null;
		}
		return infoInsuredList;
	}

	/**
	 * 通过information_id获得投保人信息列表
	 * 
	 * @param info
	 * @return
	 */
	private List<HashMap<String, String>> getInfoAppntListByInfo(String infomationID) {
		List<HashMap<String, String>> infoAppntList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from InformationAppnt where information_id = ?";
			String[] sqltemp = {infomationID};
			infoAppntList = new GetDBdata().query(sql,sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoAppntList = null;
		}
		return infoAppntList;
	}

	/**
	 * 通过订单项查询公共属性信息列表
	 * 
	 * @param order
	 * @return
	 */
	private List<HashMap<String, String>> getInfoListByOrderItem(String orderItemID) {
		List<HashMap<String, String>> infoList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from Information where orderItem_id = ?";
			String[] sqltemp = {orderItemID};
			infoList = new GetDBdata().query(sql,sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoList = null;
		}
		return infoList;
	}

	/**
	 * 通过订单查询订单项列表
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	private List<HashMap<String, String>> getOrderItemListByOrder(String orderID) {
		List<HashMap<String, String>> orderItemList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from OrderItem where order_id = ?";
			String[] sqltemp = {orderID};
			orderItemList = new GetDBdata().query(sql,sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			orderItemList = null;
		}
		return orderItemList;
	}

	/**
	 * 通过订单号查询订单对象
	 * 
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	private List<HashMap<String, String>> getOrderListByID(String orderID) {
		List<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from Orders where orderSn = ?";  
			String[] sqltemp = {orderID};
			orderList = new GetDBdata().query(sql,sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			orderList = null;
		}
		return orderList;
	}

	/**
	 * 按指定的format输出日期字符串
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
	
	public String fomatBirthday(String dateStr, String format){
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

	/**
	 * 通过目的地编码获得目的地(平安)
	 * 
	 * @param countryCode
	 * @return
	 */
	private String getDestByCountryCode(String insureCode, String countryCode) {
		List<HashMap<String, String>> countryList = new ArrayList<HashMap<String, String>>();
		String shenGenEn = "";
		String shenGenCh = "";
		String unShenGenEn = "";
		String unShenGenCh = "";
		String dest = "";
		try {
			String sql = "select codeEnName,codeName,flagType from dictionary where codetype='CountryCode' and insuranceCode = ? and codeValue in (?)";
			String[] sqltemp = {insureCode,countryCode};
			countryList = new GetDBdata().query(sql,sqltemp);
			Iterator<HashMap<String, String>> countryIt = countryList.iterator();
			while (countryIt.hasNext()) {
				HashMap<String, String> countryMap = (HashMap<String, String>) countryIt.next();
				if ("Y".equals(countryMap.get("flagType"))) {
					shenGenEn += countryMap.get("codeEnName") + ",";
					shenGenCh += countryMap.get("codeName") + ",";
				} else if ("N".equals(countryMap.get("flagType"))) {
					unShenGenEn += countryMap.get("codeEnName") + ",";
					unShenGenCh += countryMap.get("codeName") + ",";
				}
			}
			if (StringUtil.isNotEmpty(unShenGenCh) && StringUtil.isNotEmpty(unShenGenEn)) {
				dest = shenGenCh + "申根协议国家" + " " + shenGenEn + "SCHENGEN STATES" + " " + unShenGenCh.substring(0, unShenGenCh.lastIndexOf(",")) + " "
						+ unShenGenEn.substring(0, unShenGenEn.lastIndexOf(","));
			} else {
				dest = shenGenCh + "申根协议国家" + " " + shenGenEn + "SCHENGEN STATES" + " ";
			}
		} catch (Exception e) {
			logger.error("经代通接口InsureTransfer查询目的地异常" + e.getMessage(), e);
		}
		return dest;
	}
	

	/**
	 * 百年投保报文
	 * 
	 * @param insureTypeCode
	 * @param orderID
	 * @return
	 */
	private Document reqBeanToXMLBN(String insureTypeCode, String orderID) {// 建立XML节点
		Document doc = null;
		try {
			Element root = new Element("SQTBRequestXml");
			// 头节点
			LinkedHashMap<String, String> Header_Map = new LinkedHashMap<String, String>();

			List<HashMap<String, String>> tradeInfoList = this.getInfoTradeListByOrderId(orderID);
			if (tradeInfoList.isEmpty() || tradeInfoList.size() <= 0) {
				logger.error("调用经代通接口发生异常：该单尚未交易！");
				return null;// 该单尚未交易
			}
			Iterator<HashMap<String, String>> tradeIt = tradeInfoList.iterator();
			while (tradeIt.hasNext()) {
				HashMap<String, String> tradeMap = (HashMap<String, String>) tradeIt.next();
				Header_Map.put("TRAN_CODE", "00030006"); // 交易码（必填）
				if (StringUtil.isNotEmpty(tradeMap.get("receiveDate"))) {
					Header_Map.put("BK_ACCT_DATE", stringToDate(tradeMap.get("receiveDate"), "yyyy-MM-dd"));// 交易日期（必填）投保日期（太平）
					Header_Map.put("BK_ACCT_TIME", stringToDate(tradeMap.get("receiveDate"), "HH:mm:ss"));// 交易时间（必填）投保时间（太平）
				}
				Header_Map.put("BK_SERIAL", orderID);// 交易流水号, 第三方定单号码（内部订单号）
				Header_Map.put("INSURENCE_CODE", insureTypeCode);// 保险公司（必填）
				root.addContent(splitEle("Header", Header_Map));
			}

			// 请求节点
			Element Request = new Element("Request");

			/*************************** 意健险保险信息 *******************************/
			Element ahsPolicy = new Element("ahsPolicy");
			/*************************** 保单基本信息,非空 *******************************/
			List<HashMap<String, String>> orderList = this.getOrderListByID(orderID);
			if (orderList.isEmpty() || orderList.size() <= 0) {
				logger.error("调用经代通接口发生异常：不存在该订单信息！");
				return null;
			}
			Iterator<HashMap<String, String>> orderIt = orderList.iterator();
			while (orderIt.hasNext()) {
				HashMap<String, String> orderMap = (HashMap<String, String>) orderIt.next();
				List<HashMap<String, String>> orderItemList = this.getOrderItemListByOrder(orderMap.get("id"));
				Iterator<HashMap<String, String>> orderItemIt = orderItemList.iterator();
				while (orderItemIt.hasNext()) {
					HashMap<String, String> orderItemMap = (HashMap<String, String>) orderItemIt.next();
					List<HashMap<String, String>> infoList = this.getInfoListByOrderItem(orderItemMap.get("id"));
					Iterator<HashMap<String, String>> infoIt = infoList.iterator();
					while (infoIt.hasNext()) {
						HashMap<String, String> infoMap = (HashMap<String, String>) infoIt.next();
						LinkedHashMap<String, String> policyBaseInfo_Map = new LinkedHashMap<String, String>();
						policyBaseInfo_Map.put("plan-validate", stringToDate(infoMap.get("effective"), "yyyy-MM-dd")); // 保单生效日期
						if (StringUtil.isNotEmpty(infoMap.get("protectionPeriodTy"))) {
							if ("D".equalsIgnoreCase(infoMap.get("protectionPeriodTy"))) {// 按天
								policyBaseInfo_Map.put("coverage-period", "D");// 保障类型
								policyBaseInfo_Map.put("coverage-year", infoMap.get("protectionPeriod"));// 保障年限
							} else if ("M".equalsIgnoreCase(infoMap.get("protectionPeriodTy"))) {// 按月
								policyBaseInfo_Map.put("coverage-period", "M");// 保障类型
								policyBaseInfo_Map.put("coverage-year", infoMap.get("protectionPeriod"));// 保障年限
							} else if ("Y".equalsIgnoreCase(infoMap.get("protectionPeriodTy"))) {// 按年
								policyBaseInfo_Map.put("coverage-period", "Y");// 保障类型
								policyBaseInfo_Map.put("coverage-year", infoMap.get("protectionPeriod"));// 保障年限
							}
						}
						policyBaseInfo_Map.put("amount", orderMap.get("amntPrice"));// 购买保单的保额
						ahsPolicy.addContent(splitEle("policyBaseInfo", policyBaseInfo_Map));

						/*********** 投保人信息，个人投保单或者团体投保单两项需要为其他一项 非空 *************/
						Element insuranceApplicantInfo = new Element("insuranceApplicantInfo");
						List<HashMap<String, String>> infoAppntListI = this.getInfoAppntListByInfo(infoMap.get("id"));
						Iterator<HashMap<String, String>> infoAppntItI = infoAppntListI.iterator();
						while (infoAppntItI.hasNext()) {
							HashMap<String, String> infoAppntMapI = (HashMap<String, String>) infoAppntItI.next();
							LinkedHashMap<String, String> individualPersonnelInfo_Map = new LinkedHashMap<String, String>();
							individualPersonnelInfo_Map.put("personnelName", infoAppntMapI.get("applicantName"));// 人员名称，非空
							individualPersonnelInfo_Map.put("sexCode", infoAppntMapI.get("applicantSex"));// 性别
							// M：男，F：女，非空
							// 证件类型，01：身份证 02：护照 03：军人证 05：驾驶证 06：港澳回乡证或台胞证
							// 99：其他，非空。
							individualPersonnelInfo_Map.put("certificateType", infoAppntMapI.get("applicantIdentityType"));
							individualPersonnelInfo_Map.put("certificateNo", infoAppntMapI.get("applicantIdentityId"));// 证件号码，非空
							individualPersonnelInfo_Map.put("birthday", infoAppntMapI.get("applicantBirthday"));// 生日，非空，格式YYYY-MM-DD
							individualPersonnelInfo_Map.put("mobileTelephone", infoAppntMapI.get("applicantMobile"));// 手机号码，可空
							insuranceApplicantInfo.addContent(splitEle("individualPersonnelInfo", individualPersonnelInfo_Map));
						}
						ahsPolicy.addContent(insuranceApplicantInfo);
					}
				}
			}

			/************************** 层级信息列表，可包含1或n个subjectInfo，非空 **************************/
			Element subjectInfo = new Element("subjectInfo");
			Element subjectInfo_ = new Element("subjectInfo");
			Iterator<HashMap<String, String>> orderItJ = orderList.iterator();
			while (orderItJ.hasNext()) {
				HashMap<String, String> orderMapJ = (HashMap<String, String>) orderItJ.next();
				List<HashMap<String, String>> orderItemListJ = this.getOrderItemListByOrder(orderMapJ.get("id"));
				Iterator<HashMap<String, String>> orderItemItJ = orderItemListJ.iterator();
				while (orderItemItJ.hasNext()) {
					HashMap<String, String> orderItemMapJ = (HashMap<String, String>) orderItemItJ.next();

					// 组合产品列表，若是组合产品非空，可包含1或n个productInfo,目前只涉及一种产品
					Element productInfo_ = new Element("productInfo");
					LinkedHashMap<String, String> productInfo_Map = new LinkedHashMap<String, String>();
					productInfo_Map.put("productCode", orderMapJ.get("outRiskCode"));// 组合产品代码，套餐编号
					// 非空
					productInfo_Map.put("applyNum", "1");// 投保份数，非空
					productInfo_Map.put("totalModalPremium", orderMapJ.get("productTotalPrice"));// 实际保费合计，非空
					productInfo_.addContent(splitEle("productInfo", productInfo_Map));
					subjectInfo_.addContent(productInfo_);

					List<HashMap<String, String>> infoListJ = this.getInfoListByOrderItem(orderItemMapJ.get("id"));
					Iterator<HashMap<String, String>> infoItJ = infoListJ.iterator();
					while (infoItJ.hasNext()) {
						HashMap<String, String> infoMapJ = (HashMap<String, String>) infoItJ.next();
						// 被保险人列表，非空。包含1个或多个insurantInfo
						List<HashMap<String, String>> infoInsuredListJ = this.getInfoInsuredListByInfo(infoMapJ.get("id"));
						Iterator<HashMap<String, String>> infoInsuredItJ = infoInsuredListJ.iterator();
						Element insurantInfo = new Element("insurantInfo");
						while (infoInsuredItJ.hasNext()) {
							HashMap<String, String> infoInsuredMapJ = (HashMap<String, String>) infoInsuredItJ.next();
							Element insurantInfo_ = new Element("insurantInfo");
							insurantInfo_.addContent(addEle("personnelName", infoInsuredMapJ.get("recognizeeName")));// 人员名称，非空
							// 证件类型，01：身份证 02：护照 03：军人证 05：驾驶证 06：港澳回乡证或台胞证
							// 99：其他，非空
							insurantInfo_.addContent(addEle("certificateType", infoInsuredMapJ.get("recognizeeIdentityType")));//
							insurantInfo_.addContent(addEle("certificateNo", infoInsuredMapJ.get("recognizeeIdentityId")));// 证件号码，非空
							insurantInfo.addContent(insurantInfo_);
						}
						subjectInfo_.addContent(insurantInfo);
					}
				}
			}

			subjectInfo.addContent(subjectInfo_);
			ahsPolicy.addContent(subjectInfo);
			Request.addContent(ahsPolicy);
			root.addContent(Request);

			doc = new Document(root);
		} catch (Exception e) {
			logger.error("调用经代通接口组织报文异常" + e.getMessage(), e);
			return null;
		}
		return doc;
	}

	/**
	 * 拼装投保报文
	 * 
	 * @param insureTypeCode
	 *            ,ordID
	 * @return
	 */
	public Document reqBeanToXML(String insureTypeCode, String orderID) {
		Document doc = null;
		if (StringUtil.isNotEmpty(insureTypeCode) && StringUtil.isNotEmpty(orderID)) {
			// 百年承保
			if (insureTypeCode.startsWith("0005")) {
				doc = reqBeanToXMLBN(insureTypeCode, orderID);
			}
		}
		return doc;
	}
	
	public void DealWithSendExcel(String orderID,String insuredSn){ 
		 WritableWorkbook wwb = null;
		try {
			String policyPath = null;
			File path = new File(InsureTransferNew.class.getResource("/")
					.getFile());
			String folderPath = path.getParentFile().getParentFile().toString();
			policyPath = folderPath + "/template/2034/"+PubFun.getCurrentDate()+"/";
			File folderIn = new File(policyPath);
			if (!folderIn.exists()) {
				folderIn.mkdirs();
			}
			 wwb  = Workbook.createWorkbook(new File(policyPath+insuredSn+".xls"));
			 WritableSheet sheet = wwb.createSheet("sheet", 0);
			 Label label;
	            WritableCellFormat font = new WritableCellFormat();
	            WritableCellFormat background = new WritableCellFormat();
	            font.setAlignment(Alignment.CENTRE);
	            background.setBackground(Colour.YELLOW);
			 	label = new Label(0, 0, "产品代码",font);
	            sheet.setColumnView(0, 15);
	            sheet.addCell(label);
	            label = new Label(1, 0, "产品名称",font);
	            sheet.setColumnView(1, 30);
	            sheet.addCell(label);
	            label = new Label(2, 0, "保险起期",font);
	            sheet.setColumnView(2, 10);
	            sheet.addCell(label);
	            label = new Label(3, 0, "保险止期",font);
	            sheet.setColumnView(3, 10);
	            sheet.addCell(label);
	            label = new Label(4, 0, "投保人姓名",font);
	            sheet.setColumnView(4, 10);
	            sheet.addCell(label);
	            label = new Label(5, 0, "投保人生日",font);
	            sheet.setColumnView(5, 10);
	            sheet.addCell(label);
	            label = new Label(6, 0, "投保人证件类型",font);
	            sheet.setColumnView(6, 15);
	            sheet.addCell(label);
	            label = new Label(7, 0, "投保人证件号",font);
	            sheet.setColumnView(7,20);
	            sheet.addCell(label);
	            label = new Label(8, 0, "投保人手机号",font);
	            sheet.setColumnView(8, 15);
	            sheet.addCell(label);
	            label = new Label(9, 0, "投保人与被保人关系",font);
	            sheet.setColumnView(9, 20);
	            sheet.addCell(label);
	            label = new Label(10, 0, "投保人邮箱",font);
	            sheet.setColumnView(10, 25);
	            sheet.addCell(label);
	            label = new Label(11, 0, "被保人姓名",font);
	            sheet.setColumnView(11, 10);
	            sheet.addCell(label);
	            label = new Label(12, 0, "被保人证件类型",font);
	            sheet.setColumnView(12, 15);
	            sheet.addCell(label);
	            label = new Label(13, 0, "被保人证件号",font);
	            sheet.setColumnView(13, 20);
	            sheet.addCell(label);
	            label = new Label(14, 0, "被保人生日",font);
	            sheet.setColumnView(14, 10);
	            sheet.addCell(label);
	            label = new Label(15, 0, "被保人邮箱",font);
	            sheet.setColumnView(15, 25);
	            sheet.addCell(label);
			 String sql = "select a.ProductId,a.ProductName,a.StartDate,a.EndDate,b.ApplicantName,b.ApplicantBirthday,b.ApplicantIdentityType,b.ApplicantIdentityId,b.ApplicantMobile,c.RecognizeeAppntRelation,b.ApplicantMail,c.RecognizeeName,c.RecognizeeIdentityType,c.RecognizeeIdentityId,c.RecognizeeBirthday,c.RecognizeeMail " +
			 		" from SDInformation a ,SDInformationAppnt b, SDInformationInsured c " +
			 		"where a.InformationSn = b.InformationSn and a.InformationSn=c.InformationSn  and  c.OrderSn = ? and c.InsuredSn = ?";	
			 DataTable dt = new QueryBuilder(sql,orderID,insuredSn).executeDataTable();
			 if(dt.getRowCount()>0){
				 label = new Label(0, 1, dt.getString(0, 0),font);
				 sheet.addCell(label);
				 label = new Label(1, 1, dt.getString(0, 1),font);
				 sheet.addCell(label);
				 label = new Label(2, 1, stringToDate(dt.getString(0, 2),"yyyy/MM/dd"),font);
				 sheet.addCell(label);
				 label = new Label(3, 1, stringToDate(dt.getString(0, 3),"yyyy/MM/dd"),font);
				 sheet.addCell(label);
				 label = new Label(4, 1, dt.getString(0, 4),font);
				 sheet.addCell(label);
				 label = new Label(5, 1, fomatBirthday(dt.getString(0, 5),"yyyy/MM/dd"),font);
				 sheet.addCell(label);
				 if(dt.getString(0, 6)!=null&&!"".equals(dt.getString(0, 6))){
					 String cType = "";
					 if("0".equals(dt.getString(0, 6))){
						 cType="身份证";
					 }else if("7".equals(dt.getString(0, 6))){
						 cType="护照";
					 }else if("99".equals(dt.getString(0, 6))){
						 cType="其他";
					 }
					 label = new Label(6, 1, cType,font);
					 sheet.addCell(label);
				 }else{
					 label = new Label(6, 1, dt.getString(0, 6),font);
					 sheet.addCell(label);
				 }
				 label = new Label(7, 1, dt.getString(0, 7),font);
				 sheet.addCell(label);
				 label = new Label(8, 1, dt.getString(0, 8),font);
				 sheet.addCell(label);
				 if(dt.getString(0, 9)!=null&&!"".equals(dt.getString(0, 9))){
					 String reShip="";
					 if("00".equals(dt.getString(0, 9))){
						 reShip="本人";
					 }else if("01".equals(dt.getString(0, 9))){
						 reShip="配偶";
					 }else if("02".equals(dt.getString(0, 9))){
						 reShip="父母";
					 }else if("03".equals(dt.getString(0, 9))){
						 reShip="子女";
					 }else if("99".equals(dt.getString(0, 9))){
						 reShip="其他";
					 }
					 label = new Label(9, 1, reShip,font);
					 sheet.addCell(label);
				 }else{
					 label = new Label(9, 1, dt.getString(0, 9),font);
					 sheet.addCell(label);
				 }
				 label = new Label(10, 1, dt.getString(0, 10),font);
				 sheet.addCell(label);
				 label = new Label(11, 1, dt.getString(0, 11),font);
				 sheet.addCell(label);
				 if(dt.getString(0, 12)!=null&&!"".equals(dt.getString(0, 12))){
					 String cType1 = "";
					 if("0".equals(dt.getString(0, 12))){
						 cType1="身份证";
					 }else if("7".equals(dt.getString(0, 12))){
						 cType1="护照";
					 }else if("99".equals(dt.getString(0, 12))){
						 cType1="其他";
					 }
					 label = new Label(12, 1, cType1,font);
					 sheet.addCell(label);
				 }else{
					 label = new Label(12, 1, dt.getString(0, 12),font);
					 sheet.addCell(label);
				 }
				 label = new Label(13, 1, dt.getString(0, 13),font);
				 sheet.addCell(label);
				 label = new Label(14, 1, fomatBirthday(dt.getString(0, 14),"yyyy/MM/dd"),font);
				 sheet.addCell(label);
				 label = new Label(15, 1, dt.getString(0, 15),font);
				 sheet.addCell(label);
			 }else{
					logger.warn("未查询到投被保人相关信息");
			 }
			 wwb.write();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
		        try {
		            // 关闭文件   
		            wwb.close();
		        }
		        catch (Exception e) {
		            logger.error(e.getMessage(), e);
		        }
		    }
		}
	
	/**
	 * 遍历map,将结果添加到root节点
	 * 
	 * @param root
	 * @param map
	 * @return
	 */
	private static Element splitEle(String root, LinkedHashMap<String, String> map) {
		Element rootEle = new Element(root);
		Set<Map.Entry<String, String>> set = map.entrySet();
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			rootEle.addContent(addEle(entry.getKey(), entry.getValue()));
		}
		return rootEle;
	}

	/**
	 * 组装element元素
	 * 
	 * @param ele
	 * @param value
	 * @return
	 */
	private static Element addEle(String ele, String value) {
		Element temp = new Element(ele);
		temp.setText(value);
		return temp;
	}

	/**
	 * 根据出生日期计算年龄
	 * 
	 * @param birthDay
	 * @return 未来日期返回0
	 * @throws Exception
	 */
	public static int getAge(Date birthDay) throws Exception {

		Calendar cal = Calendar.getInstance(); 
		if (cal.before(birthDay)) {
			return 0;
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}

	/**
	 * 提供外部调用经代通接口,并返回结果
	 * 为了解决交易的调用问题，采取先Insert(appStatus=-1，特殊标记)，后Update的形式，提供appStatus状态变化
	 * ，作为判断条件
	 * 
	 * @param insureTypeCode
	 * @param orderID
	 */
	@SuppressWarnings("static-access")
	public Map<String, Object> callInsTransInterface(String insureTypeCode, String orderSn ,List<String> unSuccess,String sendEmailFlag) {
		HashMap<String, Object> toMap = null;
		List<HashMap<String,String>> insuredSnList = null;
		List<HashMap<String,String>> pathList = new ArrayList<HashMap<String,String>>();
		DownloadNet db=new DownloadNet();
		String channelsn = "wj";
		try {
			// 调用经代通之前判断，如果该订单返回信息已存在，不允许再访问，防止重复提交
			String sql = "select id,recognizeeSn, insuredSn,recognizeeName,informationSn from SDInformationInsured where ordersn=?";
			String[] sqltemp = {orderSn};
			insuredSnList = new GetDBdata().query(sql,sqltemp);
			int count=0;
			if(insuredSnList != null && insuredSnList.size()>0){
				for(HashMap<String,String> insuredSnMap : insuredSnList){
					
					String insuredSn = insuredSnMap.get("insuredSn");
					if(unSuccess != null && unSuccess.contains(insuredSn)){
						continue;
					}
					String recognizeeSn = insuredSnMap.get("recognizeeSn");
					String informationSn = insuredSnMap.get("informationSn");
					if(insuredSn != null && !"".equals(insuredSn)){
						SDInformationRiskTypeSchema sdInformationSchema = new SDInformationRiskTypeSchema();
						SDInformationRiskTypeSet sdInformationSet = new SDInformationRiskTypeSet();
						sdInformationSet = sdInformationSchema.query(new QueryBuilder("where OrderSn = ? and RecognizeeSn = ?",orderSn ,recognizeeSn));
						if(sdInformationSet!=null && sdInformationSet.size()>0){
							sdInformationSchema = sdInformationSet.get(0);
//							if(sdInformationSchema==null || (sdInformationSchema!=null && "1".equals(sdInformationSchema.getappStatus()))){
//								continue;
//							}
							if (sdInformationSchema == null || "1".equals(sdInformationSchema.getappStatus()) || "2".equals(sdInformationSchema.getappStatus())) {
								continue;
							}
							// 承包时间
							String insureDate = sdInformationSchema.getinsureDate();
							// 承包时间为空 则用创建时间
							if (StringUtil.isEmpty(insureDate)) {
								insureDate = DateUtil.toDateTimeString(sdInformationSchema.getcreateDate());
							}
							String id = sdInformationSchema.getId();
							String sql2 = "select Memo from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
							QueryBuilder qb = new QueryBuilder(sql2);
							qb.add("TBDealClassName");
							qb.add("TBDealClassName");
							qb.add(insureTypeCode);
							String className = qb.executeString();
							if (StringUtil.isNotEmpty(className)) {
								this.writeTXT("承保：调用金代通开始"+ " - 订单号" +orderSn);
								String sqlGroupType = "SELECT a.ordersn,b.GroupType,a.ChannelSn FROM sdorders a LEFT JOIN sdorderitemoth b ON a.ordersn=b.ordersn WHERE a.ordersn=? limit 1";
								QueryBuilder queryBuilder = new QueryBuilder(sqlGroupType);
								queryBuilder.add(orderSn);
								DataTable dt = queryBuilder.executeDataTable();
								if(dt != null && dt.getRowCount() > 0){
									channelsn = dt.getString(0, "ChannelSn");
									if(StringUtil.isNotEmpty(dt.getString(0, 1))&&"g".equals(dt.getString(0, 1))){
										if(count == 0){
											toMap = new HashMap<String, Object>();
											count++;
											Class<?> tbDeal = Class.forName(className);
											TBDealInterfaceNew tbDI = (TBDealInterfaceNew)tbDeal.newInstance();
											tbDI.dealData(toMap, insureTypeCode, orderSn,insuredSn);	
										}
									}else{
										toMap = new HashMap<String, Object>();
										Class<?> tbDeal = Class.forName(className);
										TBDealInterfaceNew tbDI = (TBDealInterfaceNew)tbDeal.newInstance();
										tbDI.dealData(toMap, insureTypeCode, orderSn,insuredSn);
									}
								}
	
								this.writeTXT("承保：调用金代通结束"+ " - 订单号" +orderSn);	
							} else {
								if(insureTypeCode.startsWith("2034")) {
									sendExcel(orderSn,insuredSn);      
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
							// 保险公司返回报文中有保费的校验：保险公司返回的保费与网站计算的保费不一致时增加邮件通知
							if(insureTypeCode.startsWith("2105")||insureTypeCode.startsWith("1015")||insureTypeCode.startsWith("2096")||insureTypeCode.startsWith("2049")){
								sdInformationSet = sdInformationSchema.query(new QueryBuilder("where OrderSn = ? and RecognizeeSn = ?",orderSn ,recognizeeSn));
								if(sdInformationSet != null && sdInformationSet.size()>0){
									sdInformationSchema = sdInformationSet.get(0);
									Double timePrem = 0.0;
									Double returnPremiums = 0.0;
									if(StringUtil.isNotEmpty(String.valueOf(toMap.get("totalPremium")))){
										 returnPremiums = Double.parseDouble(toMap.get("totalPremium").toString());
									}
									if(StringUtil.isNotEmpty(sdInformationSchema.gettimePrem())){
										timePrem = Double.parseDouble(sdInformationSchema.gettimePrem());
									}
									//只有已承保的订单才校验返回保费是否一致
									if("1".equals(String.valueOf(toMap.get("appStatus")))){
										if(timePrem-returnPremiums != 0.0){
											DownloadNet down=new DownloadNet();
											down.sendPrintErrorMail(orderSn, insuredSn ,"网站计算出的保费与保险公司返回的保费不一致",toMap);
										}
									}
								}
							}
							// 后更新，将保险公司返回结果保存至数据库
							this.updateResultMap(toMap, id);
							/** added by yuzj for 泰康在线住院保起保和止保日期是返回给我们的===begin===*/
							if("2220".equals(insureTypeCode)){
								this.updateInfomation(toMap, informationSn);
							}
							/** added by yuzj for 泰康在线住院保起保和止保日期是返回给我们的===end===*/
						}
					}
				}
				
				try {

					// 发送消息队列到经代通下载电子保单
					Map<String, String> map = new HashMap<String, String>();
					map.put("orderSn", orderSn);
					map.put("policyNo", "");
					if(Constant.B2B_HT_CHANNELSN.equals(channelsn)||
							Constant.B2B_HT_CHANNELSN01.equals(channelsn)||Constant.B2B_HT_CHANNELSN02.equals(channelsn)||
							Constant.B2C_PC_CHANNELSN.equals(channelsn)||Constant.B2C_WAP_CHANNELSN.equals(channelsn)){
						map.put("channelCode", "mxbnew");
					}else{
						map.put("channelCode", "wj");
					}
					// cps_58bb不发送短信和邮件
					if(StringUtil.isNotEmpty(Config.getValue("NoSendChannelsn")) && Config.getValue("NoSendChannelsn").indexOf(channelsn) != -1){
						map.put("isSendEmail", "N");
					}else{
						map.put("isSendEmail", sendEmailFlag);
					}
					map.put("isRewrite", "Y");
					map.put("paramMap", null);
					ProducerMQ mq = new ProducerMQ();
					mq.sendToJDT(JSON.toJSONString(map));
				
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				
				// 理财险
				if (insureTypeCode.startsWith("2103")) {
					if (!"1".equals(toMap.get("appStatus"))) {
						return com.sinosoft.cms.pub.PubFun.errMsg(null, toMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("调用经代通接口出现异常" + e.getMessage(), e);
			ErrMsg = "调用经代通接口出现异常";
			ActionUtil.sendInsureAlarmMailByOrderSn(orderSn,ErrMsg);
			ResendMain.resendCacheAdd(orderSn, insureTypeCode, ErrMsg);
		}finally{
			if(insuredSnList!=null && insuredSnList.size()>0 && pathList.size()>0){
				db.getGeneratepolicy(insuredSnList,pathList,orderSn ,insureTypeCode);
			}
		}
		return com.sinosoft.cms.pub.PubFun.sucMsg();
	
		
	}
	/**
	 * 提供外部调用经代通接口,并返回结果
	 * 为了解决交易的调用问题，采取先Insert(appStatus=-1，特殊标记)，后Update的形式，提供appStatus状态变化
	 * ，作为判断条件
	 * 
	 * @param insureTypeCode
	 * @param orderID
	 */
	public Map<String, Object> callInsTransInterface(String insureTypeCode, String orderSn ,List<String> unSuccess) {
		return callInsTransInterface(insureTypeCode,orderSn,unSuccess,"Y");
	}

	public class ElecDownThread extends Thread { 
		Map<String, String> paramInfo;
		String orderSn;
		String insuredSn;
		String recognizeeName;
		String riskId;
		public ElecDownThread(Map<String, String> param, String ordSn, String insuSn, String recoName, String id) {
			paramInfo = param;
			orderSn = ordSn;
			insuredSn = insuSn;
			recognizeeName = recoName;
			riskId = id;
		}
		
		public void run() {
			boolean flag = true;
			int sendCount = 0;
			Map<String, String> result;
			do {
				try {
					Thread.sleep(25000);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
				QueryOrders qo = new QueryOrders();
				result = qo.download(paramInfo);
				if ("Y".equals(result.get("Success"))) {
					byte[] info = PubFun.getPdfBytes(result.get("PolicyPath"));
					if (info != null && info.length > 0) {
						flag = false;
					}
				}
				sendCount ++;
			} while (flag && sendCount < 3);
			
			if ("N".equals(result.get("Success"))) {
				DownloadNet.sendPrintErrorMail(orderSn, insuredSn, result.get("Message")+" 未发送给客户电子保单！", null);
				logger.error("订单({})的保单下载电子保单失败！{}", orderSn, result.get("Message"));
			} else {
				byte[] info = PubFun.getPdfBytes(result.get("PolicyPath"));
				if (info == null || info.length == 0) {
					DownloadNet.sendPrintErrorMail(orderSn, insuredSn, "订单("+orderSn+")的保单下载电子保单失败！下载电子保单信息为空！ 未发送给客户电子保单！", null);
					logger.error("订单({})的保单下载电子保单失败！下载电子保单信息为空！", orderSn);
				} else {
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("path", result.get("PolicyPath"));
					map.put("insuredSn",insuredSn);
					map.put("recognizeeName", recognizeeName);
					DownloadNet DownloadNet = new DownloadNet();
					DownloadNet.getGeneratepolicy(map, orderSn);
					Transaction ts = new Transaction();
					ts.add(new QueryBuilder("update sdinformationrisktype set electronicPath = ? where id = ? ", result.get("PolicyPath"), riskId));
					ts.commit();
				}
			}
			
		}
	}
	
	public void sendExcel(String orderID,String insuredSn){
		try {
			DealWithSendExcel(orderID,insuredSn);
			Map<String, Object> emailMap = new HashMap<String, Object>();
			File path = new File(InsureTransferNew.class.getResource("/").getFile());
			String folderPath = path.getParentFile().getParentFile().toString();
			String policyPath = folderPath + "/template/2034/"+PubFun.getCurrentDate()+"/"+insuredSn+".xls";
			GetDBdata db1 = new GetDBdata();
			Member m1 = new Member();
			m1.setEmail(db1.getOneValue("select value from zdconfig where type='MYExcelEmail'"));
			String sql="select a.ProductName as productName,a.ProductOutCode,a.PlanCode,a.StartDate,a.EndDate from SDInformation a where a.orderSn = ?";
			DataTable dt = new QueryBuilder(sql,orderID).executeDataTable();
			if(dt.getRowCount()>0){
				emailMap.put("ProductName", dt.getString(0, 0));
				if(dt.getString(0, 0)!=null&&!"".equals(dt.getString(0, 2))){
					emailMap.put("ProductCode",dt.getString(0, 2));
				}else{
					emailMap.put("ProductCode",dt.getString(0, 1));
				}
				emailMap.put("Effective", stringToDate(dt.getString(0, 3),"yyyy/MM/dd"));
				emailMap.put("Fail", stringToDate(dt.getString(0, 4),"yyyy/MM/dd"));
				
			}else{
				logger.warn("未查询到订单及保单相关信息");
			}
			emailMap.put("FilePath", policyPath);
			emailMap.put("FileName", insuredSn+".xls");
			emailMap.put("Member", m1);
			ActionUtil.dealAction("wj00053", emailMap, null);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
	}

	public Map<String, Object> findInformationAppntByOrderSn(String orderID) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select a.ProductName as productname, b.ApplicantName as applicantname,b.ApplicantMail as mail " +
				"from SDInformation a , SDInformationAppnt b " +
				"where a.InformationSn = b.InformationSn and  a.orderSn = ?";
		try {
			DataTable dt = new QueryBuilder(sql,orderID).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				for (int j = 0; j < 1; j++) {
					String productname = dt.getString(j, 0);
					String applicantname = dt.getString(j, 1);
					String mail = dt.getString(j, 2);
					map.put("ProductName", productname);
					map.put("ApplicantName", applicantname);
					map.put("mail", mail);
				}
			}
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public String checkElectronicPolicyIsSuccess(String orderID,String insuredSn,String policyNo,String validateCode,String path,String insureDate) {
		String returnFlag = toGenerateElectronicPolicy(orderID ,insuredSn,policyNo,validateCode,path,insureDate);
		if (returnFlag == null || "".equals(returnFlag)) {
			int i = 0;
			while (i < 5) {
				String returnFlag1 = toGenerateElectronicPolicy(orderID ,insuredSn,policyNo,validateCode,path,insureDate);
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

	@SuppressWarnings("unchecked")
	public String toGenerateElectronicPolicy(String orderSN,String insuredSn,String policyNo,String validateCode,String path,String insureDate) {
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
			cInputData.addElement(path);
			cInputData.addElement(insureDate);
			ElectronicPolicyPrint tElectronicPolicyPrint = new ElectronicPolicyPrint();
			// 调用保单打印程序进行打印
			if (tElectronicPolicyPrint.submitData(cInputData, "")) {
				logger.info("保单打印成功！保单存储路径为：{}", tElectronicPolicyPrint.getResult().get("ResultPath"));
				return tElectronicPolicyPrint.getResult().get("ResultPath").toString();
			} else {
				logger.error("保单打印失败！原因是：{}", tElectronicPolicyPrint.getError().getFirstError());
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public boolean findOrderSuccessByCom(String comCode, String orderSn) {
		StringBuffer sb = new StringBuffer();
		sb.append(" where 1=1 ");
		sb.append(" and orderSn = '" + orderSn + "'");
		sb.append(" and insuranceCode = '" + comCode + "'");
		sb.append(" and appStatus = '1'");
		InsuredCompanyReturnDataSet tICRDSet = new InsuredCompanyReturnDataSet();
		InsuredCompanyReturnDataSchema tICRDSchema = new InsuredCompanyReturnDataSchema();
		tICRDSet = tICRDSchema.query(new QueryBuilder(sb.toString()));
		return tICRDSet.isEmpty();
	}

	/**
	 * 修改数据方法
	 * 
	 * @param reMap
	 * @param insureTypeCode
	 * @param orderID
	 * @param ID
	 */
	private void updateResultMap(HashMap<String, Object> reMap, String ID) {
		try {
			Transaction transaction = new Transaction();
			SDInformationRiskTypeSchema sdInformationSchema = new SDInformationRiskTypeSchema();
			SDInformationRiskTypeSet sdInformationSet = new SDInformationRiskTypeSet();
			sdInformationSet = sdInformationSchema.query(new QueryBuilder("where id = ? " , ID));
			SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String insureDateString=sdf_2.format(new Date());
			if (!sdInformationSet.isEmpty() && sdInformationSet.size() > 0) {
				sdInformationSchema = sdInformationSet.get(0);
				sdInformationSchema.setmodifyDate(new Date());
				sdInformationSchema.setinsureDate(insureDateString);
				if(!StringUtil.isEmpty(reMap.get("applyPolicyNo"))){
					sdInformationSchema.setapplyPolicyNo(reMap.get("applyPolicyNo").toString());// 投保单号
				}
				
				sdInformationSchema.setpolicyNo(reMap.get("policyNo") == null ? "" : reMap.get("policyNo").toString());// 保单号
				sdInformationSchema.setnoticeNo(reMap.get("noticeNo") == null ? "" : reMap.get("noticeNo").toString());// 财务通知单号
				sdInformationSchema.setvalidateCode(reMap.get("validateCode") == null ? "" : reMap.get("validateCode").toString());// 保单验证码
				if (reMap.get("totalPremium") != null) {
					sdInformationSchema.setreturnPremiums(reMap.get("totalPremium").toString());// 保单保费
				}
				sdInformationSchema.setappStatus(reMap.get("appStatus") == null ? "" : reMap.get("appStatus").toString());// 投保状态
				sdInformationSchema.setinsuranceTransCode(reMap.get("TRAN_CODE") == null ? "" : reMap.get("TRAN_CODE").toString());// 保险公司返回交易流水号
				sdInformationSchema.setinsuranceBankCode(reMap.get("BANK_CODE") == null ? "" : reMap.get("BANK_CODE").toString());// 保险公司返回银行编码
				sdInformationSchema.setinsuranceBankSeriNO(reMap.get("BK_SERIAL") == null ? "" : reMap.get("BK_SERIAL").toString());// 保险公司返回银行交易流水号
				sdInformationSchema.setinsuranceBRNO(reMap.get("BRNO") == null ? "" : reMap.get("BRNO").toString());// 保险公司返回系列号
				sdInformationSchema.setinsuranceTELLERNO(reMap.get("TELLERNO") == null ? "" : reMap.get("TELLERNO").toString());// 保险公司反回商户号
				sdInformationSchema.setinsurerFlag(reMap.get("PA_RSLT_CODE") == null ? "" : reMap.get("PA_RSLT_CODE").toString());// 保险公司返回投保结果编码
				sdInformationSchema.setinsureMsg(reMap.get("PA_RSLT_MESG") == null ? "" : reMap.get("PA_RSLT_MESG").toString());// 保险公司返回投保结果信息
				sdInformationSchema.setelectronicCout(reMap.get("policyPath") == null ? "" : reMap.get("policyPath").toString());// 保险公司返回的保单下载路径
				sdInformationSchema.setelectronicPath(reMap.get("remark1") == null ? "" : reMap.get("remark1").toString());//保险公司返回保单下载物理路径
				sdInformationSchema.setbalanceFlag("0");
				//added by yuzj @20160830 for 泰康在线住院保===begin===
				if (reMap.get("startDate") != null) {
					sdInformationSchema.setsvaliDate(DateUtil.parseDateTime(reMap.get("startDate").toString()));// 保单保费
				}
				if (reMap.get("endDate") != null) {
					sdInformationSchema.setevaliDate(DateUtil.parseDateTime(reMap.get("endDate").toString()));// 保单保费
				}
				//added by yuzj @20160830 for 泰康在线住院保===end===
				transaction.add(sdInformationSchema, Transaction.UPDATE);
				transaction.commit();
			}
		} catch (Exception e) {
			logger.error("类InsureTransfer方法updateResultMap()出现异常" + e.getMessage(), e);
		}
	}
	/**
	 * 修改数据方法
	 * 
	 * @param reMap
	 * @param infomationSn
	 */
	private void updateInfomation(HashMap<String, Object> reMap, String infomationSn) {
		try {
			Transaction transaction = new Transaction();
			SDInformationSchema sdInformationSchema = new SDInformationSchema();
			SDInformationSet sdInformationSet = new SDInformationSet();
			sdInformationSet = sdInformationSchema.query(new QueryBuilder("where informationSn = ? " , infomationSn));
			SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String insureDateString=sdf_2.format(new Date());
			if (!sdInformationSet.isEmpty() && sdInformationSet.size() > 0) {
				if (reMap.get("startDate") != null) {
					sdInformationSchema.setstartDate(DateUtil.parseDateTime(reMap.get("startDate").toString()));// 保单保费
				}
				if (reMap.get("endDate") != null) {
					sdInformationSchema.setendDate(DateUtil.parseDateTime(reMap.get("endDate").toString()));// 保单保费
				}
				transaction.add(sdInformationSchema, Transaction.UPDATE);
				transaction.commit();
			}
		} catch (Exception e) {
			logger.error("类InsureTransfer方法updateResultMap()出现异常" + e.getMessage(), e);
		}
	}

	/************************************************************************************/

	@SuppressWarnings("static-access")
	public static void logToFile(String path, String file, InputStream in) {
		try {
			if (!path.endsWith("/")) {
				path = path + "/";
			}
			File p = new File(path);
			File f = new File(path + file);

			if (f.exists()) {
				f.delete();
			} else {
				p.mkdirs();
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			InsureTransferNew Fileadds = new InsureTransferNew();
			Fileadds.copyStream(in, fos, 64 * 1024);
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static int copyStream(InputStream in, OutputStream out, int bufferSize) throws IOException {
		int copyCnt = 0;
		byte[] buffer = new byte[bufferSize];
		try {
			int n = in.read(buffer);
			while (n > 0) {
				copyCnt += n;
				out.write(buffer, 0, n);
				n = in.read(buffer);
			}
			return copyCnt;
		} finally {
			in.close();
		}
	}

	public static String calSDate(String tbaseDate, int interval, String unit) {
		Date returnDate = null;
		String ReturnDate = null;

		Date baseDate = new Date();
		try {
			baseDate = StringToDate(tbaseDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}

		GregorianCalendar mCalendar = new GregorianCalendar();
		mCalendar.setTime(baseDate);
		if (unit.equals("Y"))
			mCalendar.add(Calendar.YEAR, interval);
		if (unit.equals("M"))
			mCalendar.add(Calendar.MONTH, interval);
		if (unit.equals("D"))
			mCalendar.add(Calendar.DATE, interval);

		returnDate = mCalendar.getTime();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		ReturnDate = df.format(returnDate);

		return ReturnDate;
	}

	public static Date StringToDate(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		return date;
	}
	/**
	 * 记录金代通发送时间、返回金代通时间
	 * 
	 * @param content
	 *            记录内容
	 */
	public void writeTXT(String content) {
		FileWriter fw = null;
		try {
			String filepath = Config.getContextRealPath() + File.separator + "wjtojdt" + File.separator + PubFun.getCurrentDate() + ".txt";
			File tFile = new File(filepath);
			File parent = tFile.getParentFile(); 
			if(!parent.exists()){ 
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
	public static void main(String[] arg){
		
//		InsureTransferNew itn = new InsureTransferNew();
//		itn.checkElectronicPolicyIsSuccess("2013000000008289", "2013000000008289_1","10202011900057842922","CcIEelKHCoKmxecOPt", "", "2014-01-30 12:00:00");
		
		try {

			// 发送消息队列到经代通下载电子保单
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderSn", "2013000013064550");
			map.put("policyNo", "");
			map.put("channelCode", "wj");
			map.put("isSendEmail", "Y");
			map.put("isRewrite", "Y");
			map.put("paramMap", null);
			ProducerMQ mq = new ProducerMQ();
			mq.sendToJDT(JSON.toJSONString(map));
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
