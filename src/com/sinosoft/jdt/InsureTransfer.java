package com.sinosoft.jdt;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.util.DownloadNet;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.tb.TBDealInterface;
import com.sinosoft.lis.f1print.ElectronicPolicyPrint;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.InsuredCompanyReturnDataSchema;
import com.sinosoft.schema.InsuredCompanyReturnDataSet;
import com.sinosoft.utility.VData;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
 
public class InsureTransfer {
	private static final Logger logger = LoggerFactory.getLogger(InsureTransfer.class);

	private String tradeSeriNO = "";
	private String ErrMsg ;
	/**
	 * 通过orderID获取交易信息.
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
	 * 通过informationInsured_id获得险种信息列表
	 * 
	 * @param info
	 * @return
	 */
	private List<HashMap<String, String>> getInfoInsuredRiskListByInfo(String informationInsuredId) {
		List<HashMap<String, String>> infoInsuredRiskList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from InformationRiskType where informationInsured_id = ?";
			String[] sqltemp = {informationInsuredId};
			infoInsuredRiskList = new GetDBdata().query(sql,sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoInsuredRiskList = null ;
		}
		return infoInsuredRiskList;
	}

	/**
	 * 通过informationInsured_id获得收益人信息列表
	 * 
	 * @param info
	 * @return
	 */
	private List<HashMap<String, String>> getInfoInsuredBnfListByInfo(String informationInsuredId) {
		List<HashMap<String, String>> infoInsuredBnfList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from InformationBnf where informationInsured_id = ?";
			String[] sqltemp = {informationInsuredId};
			infoInsuredBnfList = new GetDBdata().query(sql,sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoInsuredBnfList = null;
		}
		return infoInsuredBnfList;
	}

	/**
	 * 通过informationInsured_id获得被保人缴费方式元素信息列表
	 * 
	 * @param info
	 * @return
	 */
	private List<HashMap<String, String>> getInfoInsuredEleListByInfo(String informationInsuredId) {
		List<HashMap<String, String>> infoInsuredEleList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from InformationInsuredElements where elementsType = 'appLevel' and informationInsured_id = ?";
			String[] sqltemp = {informationInsuredId};
			infoInsuredEleList = new GetDBdata().query(sql,sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			infoInsuredEleList = null;
		}
		return infoInsuredEleList;
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
	 * 通过被保人ID查询健康告知信息（和众开始） 别删了，暂时不用
	 * @param insuredID
	 * @author fhz   
	 * **/
	private List<HashMap<String, String>> getInsuredHealthyListByInsId(String insuredID) {
		List<HashMap<String, String>> inhList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from insuredhealth where informationInsured_id = ? and selectflag <>''";
			String[] sqltemp = {insuredID};
			inhList = new GetDBdata().query(sql,sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			inhList = null;
		}
		return inhList;
		
	}
	
	

	/**
	 * 平安投保报文
	 * 
	 * @param orderID
	 * @return
	 */
	private Document reqBeanToXMLPA(String insureTypeCode, String orderID) {// 建立XML节点
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
				Header_Map.put("TRAN_CODE", "100083"); // 交易码（必填）
				Header_Map.put("BANK_CODE", "9966");// 银行代码（必填）除平安外，其他保险公司该字段为
				// 中介公司编码
				Header_Map.put("BRNO", "99660000");// 系列号
				Header_Map.put("TELLERNO", "");// 商户号
				if (StringUtil.isNotEmpty(tradeMap.get("receiveDate"))) {
					Header_Map.put("BK_ACCT_DATE", stringToDate(tradeMap.get("receiveDate"), "yyyyMMdd"));// 交易日期（必填）投保日期（太平）
					Header_Map.put("BK_ACCT_TIME", stringToDate(tradeMap.get("receiveDate"), "HH:mm:ss"));// 交易时间（必填）投保时间（太平）
				}
				Header_Map.put("BK_SERIAL", orderID);// 交易流水号, 第三方定单号码（内部订单号）
				this.tradeSeriNO = tradeMap.get("tradeSeriNO");
				Header_Map.put("BK_TRAN_CHNL", "WEB");// 交易渠道（必填）
				Header_Map.put("REGION_CODE", "000000");// 地区码（必填）
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
						List<HashMap<String, String>> infoInsuredList = this.getInfoInsuredListByInfo(infoMap.get("id"));
						Iterator<HashMap<String, String>> infoInsuredIt = infoInsuredList.iterator();
						while (infoInsuredIt.hasNext()) {
							HashMap<String, String> infoInsuredMap = (HashMap<String, String>) infoInsuredIt.next();
							LinkedHashMap<String, String> policyBaseInfo_Map = new LinkedHashMap<String, String>();
							policyBaseInfo_Map.put("applyPersonnelNum", "1");// 投保人数
							// 非空。1:本人 2:配偶3 :父子 4:父女 5:受益人 6:被保人 7:投保人 A:母子
							// B:母女 C:兄弟 D:姐弟 G:祖孙
							// H:雇佣 I:子女 9:其他 8:转换不详
							policyBaseInfo_Map.put("relationshipWithInsured", infoInsuredMap.get("recognizeeAppntRelation"));// 与被保人关系recognizeeAppntRelation
							policyBaseInfo_Map.put("totalModalPremium", orderMap.get("totalAmount"));// 实际保费合计
							policyBaseInfo_Map.put("insuranceBeginTime", infoMap.get("effective"));// 保险起期
							policyBaseInfo_Map.put("insuranceEndTime", stringToDate(infoMap.get("fail"), "yyyy-MM-dd 23:59:59"));// 保险止期
							if (StringUtil.isNotEmpty(infoMap.get("protectionPeriodTy"))) {
								if ("D".equalsIgnoreCase(infoMap.get("protectionPeriodTy"))) {// 按天
									policyBaseInfo_Map.put("applyMonth", "");// 保险期限
									policyBaseInfo_Map.put("applyDay", infoMap.get("protectionPeriod"));// 保险期限
									// （天产品，月产品）
								} else if ("M".equalsIgnoreCase(infoMap.get("protectionPeriodTy"))) {// 按月
									policyBaseInfo_Map.put("applyMonth", infoMap.get("protectionPeriod"));// 保险期限
									policyBaseInfo_Map.put("applyDay", "");// 保险期限
									// （天产品，月产品）
								} else if ("Y".equalsIgnoreCase(infoMap.get("protectionPeriodTy"))) {
									policyBaseInfo_Map.put("applyMonth", String.valueOf(Integer.parseInt(infoMap.get("protectionPeriod")) * 12));// 保险期限
									policyBaseInfo_Map.put("applyDay", "");// 保险期限
									// （天产品，月产品）
								}
							}
							// 务类型 1：个人，2：团体 非空 客户类型 1-个人;2-企业（太平）
							policyBaseInfo_Map.put("businessType", "1");// 业务类型（）--固定值
							policyBaseInfo_Map.put("currecyCode", "01");// 币种--固定值
							ahsPolicy.addContent(splitEle("policyBaseInfo", policyBaseInfo_Map));
						}
					}
				}
			}
			/*************************** 保单扩展信息,非空 *******************************/
			LinkedHashMap<String, String> policyExtendInfo_Map = new LinkedHashMap<String, String>();
			policyExtendInfo_Map.put("partnerName", "KAIXINBAO"); // 用于保存外部合作伙伴名称(最长12字节)，平安分配的合作方代码,(平安固定)
			policyExtendInfo_Map.put("partnerSystemSeriesNo", orderID); // 用于保存外部合作伙伴与保单号对应的号码，如合作伙伴
			ahsPolicy.addContent(splitEle("policyExtendInfo", policyExtendInfo_Map));

			/*********** 投保人信息，个人投保单或者团体投保单两项需要为其他一项 非空 *************/
			Element insuranceApplicantInfo = new Element("insuranceApplicantInfo");
			Iterator<HashMap<String, String>> orderItI = orderList.iterator();
			while (orderItI.hasNext()) {
				HashMap<String, String> orderMapI = (HashMap<String, String>) orderItI.next();
				List<HashMap<String, String>> orderItemListI = this.getOrderItemListByOrder(orderMapI.get("id"));
				Iterator<HashMap<String, String>> orderItemItI = orderItemListI.iterator();
				while (orderItemItI.hasNext()) {
					HashMap<String, String> orderItemMapI = (HashMap<String, String>) orderItemItI.next();
					List<HashMap<String, String>> infoListI = this.getInfoListByOrderItem(orderItemMapI.get("id"));
					Iterator<HashMap<String, String>> infoItI = infoListI.iterator();
					while (infoItI.hasNext()) {
						HashMap<String, String> infoMapI = (HashMap<String, String>) infoItI.next();
						List<HashMap<String, String>> infoAppntListI = this.getInfoAppntListByInfo(infoMapI.get("id"));
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
							individualPersonnelInfo_Map.put("familyNameSpell", infoAppntMapI.get("applicantFirstEnName"));// 姓拼音，可空,目前未记录
							individualPersonnelInfo_Map.put("firstNameSpell", infoAppntMapI.get("applicantLastEnName"));// 名拼音，可空，目前未记录
							if (StringUtil.isNotEmpty(infoAppntMapI.get("applicantBirthday"))) {
								individualPersonnelInfo_Map.put("personnelAge", getAge(new SimpleDateFormat("yyyy-MM-dd").parse(infoAppntMapI.get("applicantBirthday"))) + "");// 人员年龄，可空
							}
							individualPersonnelInfo_Map.put("birthday", infoAppntMapI.get("applicantBirthday"));// 生日，非空，格式YYYY-MM-DD
							individualPersonnelInfo_Map.put("mobileTelephone", infoAppntMapI.get("applicantMobile"));// 手机号码，可空
							individualPersonnelInfo_Map.put("email", infoAppntMapI.get("applicantMail"));// email地址，可空
							insuranceApplicantInfo.addContent(splitEle("individualPersonnelInfo", individualPersonnelInfo_Map));
						}
						Element groupPersonnelInfo = new Element("groupPersonnelInfo");
						insuranceApplicantInfo.addContent(groupPersonnelInfo);
					}
				}
			}
			ahsPolicy.addContent(insuranceApplicantInfo);

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
					List<HashMap<String, String>> infoListJ = this.getInfoListByOrderItem(orderItemMapJ.get("id"));
					Iterator<HashMap<String, String>> infoItJ = infoListJ.iterator();
					while (infoItJ.hasNext()) {
						HashMap<String, String> infoMapJ = (HashMap<String, String>) infoItJ.next();
						subjectInfo_.addContent(addEle("totalModalPremium", orderMapJ.get("productTotalPrice")));

						// 组合产品列表，若是组合产品非空，可包含1或n个productInfo,目前只涉及一种产品
						Element productInfo_ = new Element("productInfo");
						LinkedHashMap<String, String> productInfo_Map = new LinkedHashMap<String, String>();
						
						if(orderMapJ.get("brkRiskCode")!=null&&!"".equals(orderMapJ.get("brkRiskCode")))
						{
							productInfo_Map.put("productCode", orderMapJ.get("brkRiskCode"));// 如果计划编码不为空则传计划编码    modify by fhz 20121212
						}
						else
						{
							productInfo_Map.put("productCode", orderMapJ.get("outRiskCode"));// 组合产品代码，套餐编号
						}
						// 非空
						productInfo_Map.put("applyNum", "1");// 投保份数，非空
						productInfo_Map.put("totalModalPremium", orderMapJ.get("productTotalPrice"));// 实际保费合计，非空
						productInfo_.addContent(splitEle("productInfo", productInfo_Map));
						subjectInfo_.addContent(productInfo_);
						List<HashMap<String, String>> infoInsuredListJ = this.getInfoInsuredListByInfo(infoMapJ.get("id"));
						Iterator<HashMap<String, String>> infoInsuredItJ = infoInsuredListJ.iterator();
						// 被保险人列表，非空。包含1个或多个insurantInfo
						Element insurantInfo = new Element("insurantInfo");
						while (infoInsuredItJ.hasNext()) {
							HashMap<String, String> infoInsuredMapJ = (HashMap<String, String>) infoInsuredItJ.next();
							Element insurantInfo_ = new Element("insurantInfo");
							// 人员属性 200被保人 100真实被保人 非空
							insurantInfo_.addContent(addEle("personnelAttribute", "100"));
							insurantInfo_.addContent(addEle("personnelName", infoInsuredMapJ.get("recognizeeName")));// 人员名称，非空
							insurantInfo_.addContent(addEle("sexCode", infoInsuredMapJ.get("recognizeeSex")));// 性别
							// M：男，F：女，非空
							// 证件类型，01：身份证 02：护照 03：军人证 05：驾驶证 06：港澳回乡证或台胞证
							// 99：其他，非空
							insurantInfo_.addContent(addEle("certificateType", infoInsuredMapJ.get("recognizeeIdentityType")));//
							insurantInfo_.addContent(addEle("certificateNo", infoInsuredMapJ.get("recognizeeIdentityId")));// 证件号码，非空
							insurantInfo_.addContent(addEle("familyNameSpell", infoInsuredMapJ.get("recognizeeFirstEnName")));// 姓拼音，可空
							insurantInfo_.addContent(addEle("firstNameSpell", infoInsuredMapJ.get("recognizeeLastEnName")));
							// 长度必须为2~38个字符
							// 只能由半角英文字母、空格、/、.组成，且不能包含连续两个空格，首尾不能有空格
							insurantInfo_.addContent(addEle("personnelAge", getAge(new SimpleDateFormat("yyyy-MM-dd").parse(infoInsuredMapJ.get("recognizeeBirthday"))) + ""));// 人员年龄，可空
							insurantInfo_.addContent(addEle("birthday", infoInsuredMapJ.get("recognizeeBirthday")));// 生日，非空，格式YYYY-MM-DD
							// 和众不可为空
							String destinationCountry = infoInsuredMapJ.get("destinationCountry");
							if (StringUtil.isNotEmpty(destinationCountry)) {
								String temp = spiltAdd(destinationCountry);// 添加单引号
								insurantInfo_.addContent(addEle("destinationCountry", this.getDestByCountryCode(insureTypeCode, temp)));// 目的地国家或地区，用于境外产品，可空-
							} else {
								insurantInfo_.addContent(addEle("destinationCountry", ""));
							}
							
							insurantInfo_.addContent(addEle("overseasOccupation", infoInsuredMapJ.get("occupation")));// 境外工作职业，用于境外产品，可空
							insurantInfo_.addContent(addEle("schoolOrCompany", infoInsuredMapJ.get("schoolOrCompany")));// 境外留学学校或境外工作公司，用于境外产品，可空
							insurantInfo_.addContent(addEle("outgoingPurpose", infoInsuredMapJ.get("outGoingParpose")));// 出行目的，用于境外产品，可空
							insurantInfo_.addContent(addEle("mobileTelephone", infoInsuredMapJ.get("recognizeeTel")));// 手机号码，可空
							// 当有需求给被保人发短信时为非空
							insurantInfo_.addContent(addEle("email", infoInsuredMapJ.get("recognizeeMail")));// email地址，可空
							// 借意险信息
							insurantInfo_.addContent(addEle("borrowerName", ""));// 借款人姓名
							insurantInfo_.addContent(addEle("borrowerCertificateNo", ""));// 款人证件号码
							insurantInfo_.addContent(addEle("loanName", ""));// 贷款人姓名
							insurantInfo_.addContent(addEle("loanPactNo", ""));// 贷款合同号
							insurantInfo_.addContent(addEle("loanBeginTime", ""));// 贷款起期
							insurantInfo_.addContent(addEle("loanEndTime", ""));// 贷款止期
							insurantInfo_.addContent(addEle("loanSum", ""));// 贷款金额
							// 驾驶人意外险信息
							insurantInfo_.addContent(addEle("driverSchoolName", infoInsuredMapJ.get("driverSchoolName")));// 驾校名称
							insurantInfo_.addContent(addEle("driverNo", infoInsuredMapJ.get("driverNo")));// 学员编号
							// 航意险信息
							insurantInfo_.addContent(addEle("flightNo", infoInsuredMapJ.get("flightNo")));// 航班号
							insurantInfo_.addContent(addEle("flightTime", infoInsuredMapJ.get("flightTime")));// 起飞时间

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
				this.tradeSeriNO = tradeMap.get("tradeSeriNO");
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
	 * 合众投保报文
	 * 
	 * @param orderID  
	 * @return
	 */
	private Document reqBeanToXMLHZ(String insureTypeCode, String orderID) {// 建立XML节点
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
				Header_Map.put("TRAN_CODE", "1"); // 交易码（必填）
				Header_Map.put("BANK_CODE", "");// 银行代码（必填）除平安外，其他保险公司该字段为
				// 中介公司编码
				if (StringUtil.isNotEmpty(tradeMap.get("receiveDate"))) {
					Header_Map.put("BK_ACCT_DATE", stringToDate(tradeMap.get("receiveDate"), "yyyy-MM-dd"));// 交易日期（必填）投保日期（太平）
					Header_Map.put("BK_ACCT_TIME", stringToDate(tradeMap.get("receiveDate"), "HH:mm:ss"));// 交易时间（必填）投保时间（太平）
				}
				Header_Map.put("BK_SERIAL", orderID);// 交易流水号, 第三方定单号码（内部订单号）
				this.tradeSeriNO = tradeMap.get("tradeSeriNO");
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
				List<HashMap<String, String>> tradeInfoListI = this.getInfoTradeListByOrderId(orderID);
				Iterator<HashMap<String, String>> tradeItI = tradeInfoListI.iterator();
				while (tradeItI.hasNext()) {
					HashMap<String, String> tradeMapI = (HashMap<String, String>) tradeItI.next();
					ahsPolicy.addContent(addEle("gate-way", "8"));// 支付方式
					// ahsPolicy.addContent(addEle("pay-trans-no",
					// tradeMapI.get("tradeSeriNO")));//支付平台交易流水号
					ahsPolicy.addContent(addEle("pay-trans-no", ""));
					ahsPolicy.addContent(addEle("trans-source", "11"));// -交易来源
				}
				List<HashMap<String, String>> orderItemList = this.getOrderItemListByOrder(orderMap.get("id"));
				Iterator<HashMap<String, String>> orderItemIt = orderItemList.iterator();
				while (orderItemIt.hasNext()) {
					HashMap<String, String> orderItemMap = (HashMap<String, String>) orderItemIt.next();
					List<HashMap<String, String>> infoList = this.getInfoListByOrderItem(orderItemMap.get("id"));
					Iterator<HashMap<String, String>> infoIt = infoList.iterator();
					while (infoIt.hasNext()) {
						HashMap<String, String> infoMap = (HashMap<String, String>) infoIt.next();
						// List<HashMap<String, String>> infoInsuredList =
						// this.getInfoInsuredListByInfo(infoMap.get("id"));
						// Iterator<HashMap<String, String>> infoInsuredIt =
						// infoInsuredList.iterator();
						// while(infoInsuredIt.hasNext()){
						// HashMap<String , String> infoInsuredMap =
						// (HashMap<String, String>) infoInsuredIt.next();

						LinkedHashMap<String, String> policyBaseInfo_Map = new LinkedHashMap<String, String>();
						policyBaseInfo_Map.put("plan-validate", stringToDate(infoMap.get("effective"), "yyyy-MM-dd")); // 保单生效日期
						policyBaseInfo_Map.put("businessType", "1");// 业务类型--个人
						policyBaseInfo_Map.put("amount", orderMap.get("amntPrice"));// 购买保单的保额
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
							} else if ("A".equalsIgnoreCase(infoMap.get("protectionPeriodTy"))){//按岁
								policyBaseInfo_Map.put("coverage-period", "A");// 保障类型
								policyBaseInfo_Map.put("coverage-year", infoMap.get("protectionPeriod"));// 保障年限
							}
						}
						if(StringUtil.isNotEmpty(infoMap.get("feeYear"))){
							String value = infoMap.get("feeYear").substring(0, 1);
							String type = infoMap.get("feeYear").substring(1, 2);
							policyBaseInfo_Map.put("charge-period", type);// 缴费类型(二期)
							policyBaseInfo_Map.put("charge-year", value);// 缴费年限 (二期)
						}

						ahsPolicy.addContent(splitEle("policyBaseInfo", policyBaseInfo_Map));
						// }
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
							// 英文名（境外险时专用，必须）: 长度必须为2~38个字符
							// 只能由半角英文字母、空格、/、.组成，且不能包含连续两个空格，首尾不能有空格
							individualPersonnelInfo_Map.put("englishName", infoAppntMapI.get("applicantEnName"));
							individualPersonnelInfo_Map.put("birthday", infoAppntMapI.get("applicantBirthday"));// 生日，非空，格式YYYY-MM-DD
							individualPersonnelInfo_Map.put("mobileTelephone", infoAppntMapI.get("applicantMobile"));// 手机号码，可空
							individualPersonnelInfo_Map.put("email", infoAppntMapI.get("applicantMail"));// email地址，可空
							individualPersonnelInfo_Map.put("region-code", infoAppntMapI.get("applicantArea"));// 投保人居住区域
							individualPersonnelInfo_Map.put("address", infoAppntMapI.get("applicantAddress"));// 投保人联系地址
							individualPersonnelInfo_Map.put("post-code", infoAppntMapI.get("applicantZipCode"));// 投保人邮政编码
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
						Element planInfo = new Element("planInfo");
						List<HashMap<String,String>> dutyMap = this.getDutyListByInformation(infoMapJ.get("id"));
						Iterator<HashMap<String,String>> dutyMapItJ = dutyMap.iterator();
						while(dutyMapItJ.hasNext()){
							HashMap<String,String> dutyMapJ = (HashMap<String,String>)dutyMapItJ.next();
							if(dutyMapJ.get("timePrem")!=null && !"".equals(dutyMapJ.get("timePrem"))){
								Element planInfo_ = new Element("planInfo");
								planInfo_.addContent(addEle("planCode" , dutyMapJ.get("dutySn")));
								planInfo_.addContent(addEle("DutyAount" , dutyMapJ.get("amnt")));
								planInfo_.addContent(addEle("totalModalPremium" , dutyMapJ.get("timePrem")));
								planInfo.addContent(planInfo_);
							}
						}
						subjectInfo_.addContent(planInfo);

						// 被保险人列表，非空。包含1个或多个insurantInfo
						List<HashMap<String, String>> infoInsuredListJ = this.getInfoInsuredListByInfo(infoMapJ.get("id"));
						Iterator<HashMap<String, String>> infoInsuredItJ = infoInsuredListJ.iterator();
						Element insurantInfo = new Element("insurantInfo");
						while (infoInsuredItJ.hasNext()) {
							HashMap<String, String> infoInsuredMapJ = (HashMap<String, String>) infoInsuredItJ.next();
							Element insurantInfo_ = new Element("insurantInfo");
							insurantInfo_.addContent(addEle("personnelName", infoInsuredMapJ.get("recognizeeName")));// 人员名称，非空
							insurantInfo_.addContent(addEle("sexCode", infoInsuredMapJ.get("recognizeeSex")));// 性别
							// M：男，F：女，非空
							// 证件类型，01：身份证 02：护照 03：军人证 05：驾驶证 06：港澳回乡证或台胞证
							// 99：其他，非空
							insurantInfo_.addContent(addEle("certificateType", infoInsuredMapJ.get("recognizeeIdentityType")));//
							insurantInfo_.addContent(addEle("certificateNo", infoInsuredMapJ.get("recognizeeIdentityId")));// 证件号码，非空
							// 长度必须为2~38个字符
							// 只能由半角英文字母、空格、/、.组成，且不能包含连续两个空格，首尾不能有空格
							insurantInfo_.addContent(addEle("englishName", infoInsuredMapJ.get("englishName")));
							insurantInfo_.addContent(addEle("birthday", infoInsuredMapJ.get("recognizeeBirthday")));// 生日，非空，格式YYYY-MM-DD
							//和众产品加入被保人的身高体重信息 add by  fhz 20121205
							insurantInfo_.addContent(addEle("height", infoInsuredMapJ.get("height")));
							insurantInfo_.addContent(addEle("weight", infoInsuredMapJ.get("weight")));
							// 职业代码，可空 被保人职业代码2012-7-26（因太平需要添加） 和众不可为空
							
							String sql = "select code from occupation where id=?";
							String[] sqltemp = {infoInsuredMapJ.get("occupation3")};
							GetDBdata db = new GetDBdata();
							String code = db.getOneValue(sql,sqltemp);
							String areaSql = "select areaValue from area where id=?";
							String[] areaSqltemp = {infoInsuredMapJ.get("recognizeeArea2")} ;
							String areaCode = db.getOneValue(areaSql,areaSqltemp);
							insurantInfo_.addContent(addEle("professionCode", code));//传入职业代码
							//insurantInfo_.addContent(addEle("professionCode", infoInsuredMapJ.get("occupation3")));
							insurantInfo_.addContent(addEle("mobileTelephone", infoInsuredMapJ.get("recognizeeMobile")));// 手机号码，可空
							insurantInfo_.addContent(addEle("email", infoInsuredMapJ.get("recognizeeMail")));// email地址，可空
							insurantInfo_.addContent(addEle("region-code",areaCode ));// 被保人居住区域
							insurantInfo_.addContent(addEle("address", infoInsuredMapJ.get("recognizeeAddress")));// 被保人联系地址
							insurantInfo_.addContent(addEle("post-code", infoInsuredMapJ.get("recognizeeZipCode")));// 被保人邮政编码
							// 航意险信息
							insurantInfo_.addContent(addEle("flightNo", infoInsuredMapJ.get("flightNo")));// 航班号
							insurantInfo_.addContent(addEle("flightTime", infoInsuredMapJ.get("flightTime")));// 起飞时间
							// 旅意险险信息
							insurantInfo_.addContent(addEle("destination", ""));// 旅游目的地
							insurantInfo_.addContent(addEle("destination-en", ""));// 旅游目的地英文名称
							insurantInfo_.addContent(addEle("benefit-level", ""));// 旅意险的投保档次
							// 受益人信息，可空（太平，和众需要）
							List<HashMap<String, String>> infoInsuredBnfListJ = this.getInfoInsuredBnfListByInfo(infoInsuredMapJ.get("id"));
							Iterator<HashMap<String, String>> infoInsuredBnfItJ = infoInsuredBnfListJ.iterator();
							while (infoInsuredBnfItJ.hasNext()) {
								HashMap<String, String> infoInsuredBnfMapJ = (HashMap<String, String>) infoInsuredBnfItJ.next();
								// 受益人信息，可空（太平，和众需要）
								LinkedHashMap<String, String> beneficiaryInfo_Map = new LinkedHashMap<String, String>();
								beneficiaryInfo_Map.put("personnelCode", infoInsuredBnfMapJ.get("bnfNo"));// 人员代码，非空。默认平安承保系统自动生成
								beneficiaryInfo_Map.put("personnelName", infoInsuredBnfMapJ.get("name"));// 人员名称，非空
								beneficiaryInfo_Map.put("sexCode", infoInsuredBnfMapJ.get("sex"));// 性别
								// M：男，F：女，非空
								// 证件类型，01：身份证 02：护照 03：军人证 05：驾驶证 06：港澳回乡证或台胞证
								// 99：其他，非空。
								beneficiaryInfo_Map.put("certificateType", infoInsuredBnfMapJ.get("iDType"));
								beneficiaryInfo_Map.put("certificateNo", infoInsuredBnfMapJ.get("iDNo"));// 证件号码，非空
								beneficiaryInfo_Map.put("birthday", infoInsuredBnfMapJ.get("birthday"));// 生日，非空
								beneficiaryInfo_Map.put("professionCode", infoInsuredBnfMapJ.get(""));// 职业代码，可空
								beneficiaryInfo_Map.put("mobileTelephone", infoInsuredBnfMapJ.get(""));// 手机号码，可空
								beneficiaryInfo_Map.put("email", infoInsuredBnfMapJ.get("email"));// email地址，可空
								beneficiaryInfo_Map.put("tel-phone", infoInsuredBnfMapJ.get("phone"));// 受益人联系电话
								// （和众）
								beneficiaryInfo_Map.put("post-code", infoInsuredBnfMapJ.get("zipCode"));// 受益人邮政编码
								// （和众）
								beneficiaryInfo_Map.put("address", infoInsuredBnfMapJ.get("postalAddress"));// 受益人联系地址
								// （和众）
								beneficiaryInfo_Map.put("relation", infoInsuredBnfMapJ.get("relationToInsured"));// 受益人与被保人关系
								// 不可为空
								// （和众）
								beneficiaryInfo_Map.put("bene-type", infoInsuredBnfMapJ.get("bnfType"));// 受益人类型,默认为2，身故受益人
								// （和众）
								beneficiaryInfo_Map.put("bene-rate", infoInsuredBnfMapJ.get("benePer"));// 受益比例，如果有多人，那受益比例的总和应该为1
								// 不可为空（和众）
								beneficiaryInfo_Map.put("bene-order", "bnfNo");// 受益顺序，默认为1
								// （和众）
								insurantInfo_.addContent(splitEle("beneficiaryInfo", beneficiaryInfo_Map));
							}
				
							//简单产品先注释掉不加
							//和众健康告知信息    Begin add by fhz 20121205   
							Element notifs = new Element("notifs");
							Element notif = new Element("notif");//这个区分健康告知类别的标签，和众这里不做区分，不用加入循环
							//通过被保人ID 查询他的健康告知信息
							List<HashMap<String, String>> insHealthyNotifList = this.getInsuredHealthyListByInsId(infoInsuredMapJ.get("id"));
							Iterator<HashMap<String, String>> insHealthyNotif = insHealthyNotifList.iterator();
							//进入循环吧 骚年
							while (insHealthyNotif.hasNext()) {
								Element snotif = new Element("snotif");//一条健康告知信息
								HashMap<String, String> inhMap = (HashMap<String, String>) insHealthyNotif.next();
								String selectFlag = "";
								String childInfoValue = inhMap.get("childInfoValue");
								if(inhMap.get("selectFlag")!=null){
									selectFlag = inhMap.get("selectFlag");
								}
								if(inhMap.get("showInfoCode")!=null && !"".equals(inhMap.get("showInfoCode"))){
									snotif.addContent(addEle("question-code", inhMap.get("showInfoCode")));//注意大小写
									snotif.addContent(addEle("yes-no", inhMap.get("selectFlag")));
									if("Y".equals(selectFlag)){
										if(childInfoValue.length()>1){
											childInfoValue = childInfoValue.substring(0, childInfoValue.length()-1);
											childInfoValue = childInfoValue.replace("#", ",");
											snotif.addContent(addEle("answer-text", childInfoValue));
										}
									}else{
										snotif.addContent(addEle("answer-text", ""));//详细信息，这里还没有确定怎么保存
									}
									notif.addContent(snotif);
								}
							}
							notifs.addContent(notif);//加入类别标签
							insurantInfo_.addContent(notifs);//加入健康告知根标签
							//和众健康告知信息    End 
						
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

	private List<HashMap<String, String>> getDutyListByInformation(String informationId) {
		List<HashMap<String, String>> dutyList = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select * from InformationDuty where information_id= ?";
			String[] sqltemp = {informationId};
			dutyList = new GetDBdata().query(sql,sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			dutyList = null;
		}
		return dutyList;
	}

	/**
	 * 太平养老报文
	 * 
	 * @param orderID
	 * @return
	 */
	private Document reqBeanToXMLTP(String insureTypeCode, String orderID) {// 建立XML节点
		Document doc = null;
		try {
			Element root = new Element("SQTBRequestXml");
			// 头节点
			LinkedHashMap<String, String> Header_Map = new LinkedHashMap<String, String>();

			List<HashMap<String, String>> tradeInfoList = this.getInfoTradeListByOrderId(orderID);
			Iterator<HashMap<String, String>> tradeIt = tradeInfoList.iterator();
			if (tradeInfoList.isEmpty() || tradeInfoList.size() <= 0) {
				logger.error("调用经代通接口发生异常：该单尚未交易！");
				return null;// 该单尚未交易
			}
			while (tradeIt.hasNext()) {
				HashMap<String, String> tradeMap = (HashMap<String, String>) tradeIt.next();
				Header_Map.put("TRAN_CODE", "1"); // 交易码（必填）--承保
				Header_Map.put("BANK_CODE", "B0200600");// 银行代码（必填）除平安外，其他保险公司该字段为
				// 中介公司编码
				if (StringUtil.isNotEmpty(tradeMap.get("receiveDate"))) {
					Header_Map.put("BK_ACCT_DATE", stringToDate(tradeMap.get("receiveDate"), "yyyy-MM-dd"));// 交易日期（必填）投保日期（太平）
					Header_Map.put("BK_ACCT_TIME", stringToDate(tradeMap.get("receiveDate"), "HH:mm:ss"));// 交易时间（必填）投保时间（太平）
				}
				Header_Map.put("BK_SERIAL", orderID);// 交易流水号, 第三方定单号码（内部订单号）
				this.tradeSeriNO = tradeMap.get("tradeSeriNO");
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
						// List<HashMap<String, String>> infoInsuredList =
						// this.getInfoInsuredListByInfo(infoMap.get("id"));
						// Iterator<HashMap<String, String>> infoInsuredIt =
						// infoInsuredList.iterator();
						// while(infoInsuredIt.hasNext()){
						LinkedHashMap<String, String> policyBaseInfo_Map = new LinkedHashMap<String, String>();
						policyBaseInfo_Map.put("plan-validate", stringToDate(infoMap.get("effective"), "yyyy-MM-dd")); // 保单生效日期
						policyBaseInfo_Map.put("businessType", "1");// 业务类型--个人
						ahsPolicy.addContent(splitEle("policyBaseInfo", policyBaseInfo_Map));
						// }

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
							individualPersonnelInfo_Map.put("email", infoAppntMapI.get("applicantMail"));// email地址，可空
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
					List<HashMap<String, String>> infoListJ = this.getInfoListByOrderItem(orderItemMapJ.get("id"));
					Iterator<HashMap<String, String>> infoItJ = infoListJ.iterator();
					while (infoItJ.hasNext()) {
						HashMap<String, String> infoMapJ = (HashMap<String, String>) infoItJ.next();

						// 被保险人列表，非空。包含1个或多个insurantInfo
						List<HashMap<String, String>> infoInsuredListJ = this.getInfoInsuredListByInfo(infoMapJ.get("id"));
						Iterator<HashMap<String, String>> infoInsuredItJ = infoInsuredListJ.iterator();
						Element planInfo = new Element("planInfo");
						Element insurantInfo = new Element("insurantInfo");
						while (infoInsuredItJ.hasNext()) {
							HashMap<String, String> infoInsuredMapJ = (HashMap<String, String>) infoInsuredItJ.next();

							/*
							 * List<HashMap<String, String>>
							 * infoInsuredRiskListJ =
							 * this.getInfoInsuredRiskListByInfo
							 * (infoInsuredMapJ.get("id"));
							 * Iterator<HashMap<String, String>>
							 * infoInsuredRiskItJ =
							 * infoInsuredRiskListJ.iterator();
							 * while(infoInsuredRiskItJ.hasNext()){
							 * HashMap<String, String> infoInsuredRiskMapJ =
							 * (HashMap<String, String>)
							 * infoInsuredRiskItJ.next();
							 * （如果按照险种出单，则planInfo节点不能为空
							 * ，productInfo为空，保费和保险期限为各个险种的累加）险种信息 Element
							 * planInfo_ = new Element("planInfo");
							 * planInfo_.addContent(addEle("planCode",
							 * infoInsuredRiskMapJ.get("riskCode")));// 险种代码，非空
							 * planInfo_.addContent(addEle("XZMC",
							 * infoInsuredRiskMapJ.get("riskName")));//
							 * 险种名称2012-7-26（太平）
							 * planInfo_.addContent(addEle("BXZRQSSJ",
							 * stringToDate
							 * (infoInsuredRiskMapJ.get("cValiDate"),
							 * "yyyy-MM-dd")));// 保险责任起始日2012-7-26（太平）
							 * planInfo_.addContent(addEle("BXZRZZSJ",
							 * stringToDate
							 * (infoInsuredRiskMapJ.get("eValiDate"),
							 * "yyyy-MM-dd")));// 保险责任终止日2012-7-26（太平）
							 * planInfo_.addContent(addEle("JHDM",
							 * infoInsuredRiskMapJ.get("")));//
							 * 计划代码2012-7-26（太平）
							 * planInfo_.addContent(addEle("DutyAount",
							 * infoInsuredRiskMapJ.get("amnt")));// 保额（太平）
							 * planInfo_.addContent(addEle("applyNum",
							 * infoInsuredRiskMapJ.get("mult")));// 投保份数，非空
							 * planInfo_.addContent(addEle("totalModalPremium",
							 * infoInsuredRiskMapJ.get("timePrem")));//
							 * 实际保费合计，非空
							 * 
							 * planInfo.addContent(planInfo_); }
							 */

							Element planInfo_ = new Element("planInfo");
							planInfo_.addContent(addEle("planCode", orderMapJ.get("outRiskCode")));// 险种代码，非空
							planInfo_.addContent(addEle("XZMC", orderMapJ.get("productName")));// 险种名称2012-7-26（太平）
							planInfo_.addContent(addEle("BXZRQSSJ", stringToDate(infoMapJ.get("effective"), "yyyy-MM-dd")));// 保险责任起始日2012-7-26（太平）
							planInfo_.addContent(addEle("BXZRZZSJ", stringToDate(infoMapJ.get("fail"), "yyyy-MM-dd")));// 保险责任终止日2012-7-26（太平）
							planInfo_.addContent(addEle("JHDM", "600"));// 计划代码2012-7-26（太平）
							planInfo_.addContent(addEle("DutyAount", infoMapJ.get("amntPrice")));// 保额（太平）
							planInfo_.addContent(addEle("applyNum", "1"));// 投保份数，非空
							planInfo_.addContent(addEle("totalModalPremium", orderMapJ.get("totalAmount")));// 实际保费合计，非空

							planInfo.addContent(planInfo_);

							Element insurantInfo_ = new Element("insurantInfo");
							insurantInfo_.addContent(addEle("personnelName", infoInsuredMapJ.get("recognizeeName")));// 人员名称，非空
							insurantInfo_.addContent(addEle("sexCode", infoInsuredMapJ.get("recognizeeSex")));// 性别
							// M：男，F：女，非空
							// 证件类型，01：身份证 02：护照 03：军人证 05：驾驶证 06：港澳回乡证或台胞证
							// 99：其他，非空
							insurantInfo_.addContent(addEle("certificateType", infoInsuredMapJ.get("recognizeeIdentityType")));//
							insurantInfo_.addContent(addEle("certificateNo", infoInsuredMapJ.get("recognizeeIdentityId")));// 证件号码，非空
							insurantInfo_.addContent(addEle("birthday", infoInsuredMapJ.get("recognizeeBirthday")));// 生日，非空，格式YYYY-MM-DD

							
							// 职业代码，可空 被保人职业代码2012-7-26（因太平需要添加） 和众不可为空
							insurantInfo_.addContent(addEle("professionCode", infoInsuredMapJ.get("occupation")));
							insurantInfo_.addContent(addEle("destinationCountry", infoInsuredMapJ.get("destinationCountry")));// 目的地国家或地区，用于境外产品，可空
							insurantInfo_.addContent(addEle("mobileTelephone", infoInsuredMapJ.get("recognizeeMobile")));// 手机号码，可空
							insurantInfo_.addContent(addEle("email", infoInsuredMapJ.get("recognizeeMail")));// email地址，可空
							// 借意险信息
							insurantInfo_.addContent(addEle("loanName", ""));// 贷款人姓名
							insurantInfo_.addContent(addEle("loanPactNo", ""));// 贷款合同号
							insurantInfo_.addContent(addEle("loanBeginTime", ""));// 贷款起期
							insurantInfo_.addContent(addEle("loanEndTime", ""));// 贷款止期
							insurantInfo_.addContent(addEle("loanSum", ""));// 贷款金额
							insurantInfo_.addContent(addEle("ACCT", ""));// 借款账号（太平）
							insurantInfo_.addContent(addEle("BANKNAME", ""));// 贷款机构（太平）
							// 航意险信息
							insurantInfo_.addContent(addEle("flightNo", infoInsuredMapJ.get("flightNo")));// 航班号
							insurantInfo_.addContent(addEle("flightTime", infoInsuredMapJ.get("flightTime")));// 起飞时间
							insurantInfo_.addContent(addEle("FCHBH", ""));// 返程航班号太平
							insurantInfo_.addContent(addEle("FCHBRQ", ""));// 返程航班日期（太平）
							insurantInfo_.addContent(addEle("KPHM", ""));// 客票号码（太平）
							insurantInfo_.addContent(addEle("CFD", ""));// 出发地（太平)
							insurantInfo_.addContent(addEle("CGMD", ""));// 出国目的（太平）

							// 受益人节点
							Element beneficiaryInfo = new Element("beneficiaryInfo");
							beneficiaryInfo.addContent(addEle("personnelName", ""));
							insurantInfo_.addContent(beneficiaryInfo);
							
							insurantInfo.addContent(insurantInfo_);
							
						}
						subjectInfo_.addContent(planInfo);
						subjectInfo_.addContent(insurantInfo);

						// 联系人信息 可空
						LinkedHashMap<String, String> linkManInfo_Map = new LinkedHashMap<String, String>();
						linkManInfo_Map.put("linkManName", "");// 姓名
						linkManInfo_Map.put("linkManMobile", "");// 手机号
						linkManInfo_Map.put("linkManEmail", "");// email
						Element linkManInfo = splitEle("linkManInfo", linkManInfo_Map);
						subjectInfo_.addContent(linkManInfo);
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
			// 平安保险
			if (insureTypeCode.startsWith("2007")) {
				doc = reqBeanToXMLPA(insureTypeCode, orderID);
			}
			// 合众承保
			else if (insureTypeCode.startsWith("1061")) {
				doc = reqBeanToXMLHZ(insureTypeCode, orderID);
			}
			// 太平养老
			else if (insureTypeCode.startsWith("1018")) {
				doc = reqBeanToXMLTP(insureTypeCode, orderID);
			}
			// 百年承保
			else if (insureTypeCode.startsWith("0005")) {
				doc = reqBeanToXMLBN(insureTypeCode, orderID);
			}
		}
		return doc;
	}
	
	public void DealWithSendExcel(String orderID){ 
		 WritableWorkbook wwb = null;
		try {
			String policyPath = null;
			File path = new File(InsureTransfer.class.getResource("/")
					.getFile());
			String folderPath = path.getParentFile().getParentFile().toString();
			policyPath = folderPath + "/template/2034/"+PubFun.getCurrentDate()+"/";
			File folderIn = new File(policyPath);
			if (!folderIn.exists()) {
				folderIn.mkdirs();
			}
			 wwb  = Workbook.createWorkbook(new File(policyPath+orderID+".xls"));
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
			 String sql = "select o.productId,o.productName,fi.effective,fi.fail,ia.applicantName,ia.applicantBirthday,ia.applicantIdentityType,ia.applicantIdentityId,"
			            +" ia.applicantMobile,ii.recognizeeAppntRelation,ia.applicantMail,ii.recognizeeName,ii.recognizeeIdentityType,ii.recognizeeIdentityId,ii.recognizeeBirthday,ii.recognizeeMail"
			            +" from orders o , information fi , informationappnt ia , orderitem oi,informationinsured ii"
			            +" where o.id = oi.order_id and fi.orderitem_id=oi.id and fi.id = ia.information_id and ia.information_id = ii.information_id and  o.orderSn = '"+orderID+"'";	
			 DataTable dt = new QueryBuilder(sql).executeDataTable();
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
					 if("01".equals(dt.getString(0, 6))){
						 cType="身份证";
					 }else if("07".equals(dt.getString(0, 6))){
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
					 if("01".equals(dt.getString(0, 9))){
						 reShip="本人";
					 }else if("02".equals(dt.getString(0, 9))){
						 reShip="配偶";
					 }else if("03".equals(dt.getString(0, 9))){
						 reShip="父母";
					 }else if("04".equals(dt.getString(0, 9))){
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
					 if("01".equals(dt.getString(0, 12))){
						 cType1="身份证";
					 }else if("07".equals(dt.getString(0, 12))){
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
	public void callInsTransInterface(String insureTypeCode, String orderID) {
		// 调用经代通之前判断，如果该订单返回信息已存在，不允许再访问，防止重复提交
		InsuredCompanyReturnDataSchema tICRDSchema = new InsuredCompanyReturnDataSchema();
		InsuredCompanyReturnDataSet tICRDSet = new InsuredCompanyReturnDataSet();
		tICRDSet = tICRDSchema.query(new QueryBuilder("where insuranceCode = '" + insureTypeCode + "' and orderSn = '" + orderID + "'"));
		if (!tICRDSet.isEmpty() && tICRDSet.size() > 0) {
			return;
		}
		// 先保存
		String ID = this.saveResultMap(insureTypeCode, orderID);
		if (ID == null) {
			return;
		}

		HashMap<String, Object> toMap = new HashMap<String, Object>();
		try
		{
			// zhangjinquan 11180 从泰康开始使用java反射处理承保报文发送和返回处理
			String sql = "select Memo from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add("TBDealClassName");
			qb.add("TBDealClassName");
			qb.add(insureTypeCode);
			String className = qb.executeString();
			if (StringUtil.isNotEmpty(className))
			{
				Class<?> tbDeal = Class.forName(className);
				TBDealInterface tbDI = (TBDealInterface)tbDeal.newInstance();
				tbDI.dealData(toMap, insureTypeCode, orderID);	
				
			}
			else {
				if(insureTypeCode.startsWith("2034"))
				{
					sendExcel(orderID);      
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
				else{
					Format format = Format.getPrettyFormat();
					format.setEncoding("UTF-8");// 设置xml文件的字符, 解决中文问题
					
					InsureTransfer it = new InsureTransfer();
					
					XMLOutputter xmlout = new XMLOutputter(format);
					Document doc = it.reqBeanToXML(insureTypeCode, orderID);
					// xmlout.output(doc, System.out);//request报文输出
	
					// LogUtil.info("#########################################3"+doc.toString());
					ByteArrayOutputStream bo1 = new ByteArrayOutputStream();
					xmlout.output(doc, new PrintWriter(bo1));
					// ByteArrayInputStream is = new
					// ByteArrayInputStream(bo1.toString().getBytes("UTF-8"));
					// this.logToFile("c:/", "123.xml", is);
	
					Document rultDoc = ServiceClient.execute(doc, "UTF-8");
					// xmlout.output(rultDoc, System.out);//response报文输出
	
					ByteArrayOutputStream bo2 = new ByteArrayOutputStream();
					// xmlout.output(rultDoc, new PrintWriter(bo2));

					if (rultDoc.hasRootElement()) {
						// 平安保险
						if (insureTypeCode.startsWith("2007")) {
							Element eleRoot = rultDoc.getRootElement();
							Element eleHeader = eleRoot.getChild("Header");
							String strRSLT = eleHeader.getChildText("PA_RSLT_CODE");
							List<Element> headList = (List<Element>) eleHeader.getChildren();
							if ("999999".equals(strRSLT)) {
								// 投保成功
								for (Iterator<Element> iter = headList.iterator(); iter.hasNext();) {
									Element eleNodH = (Element) iter.next();
									toMap.put(eleNodH.getName(), eleNodH.getValue());
								}
								Element strPolicyInfo = eleRoot.getChild("Response").getChild("policyInfo");
								toMap.put("applyPolicyNo", strPolicyInfo.getChildText("applyPolicyNo"));
								toMap.put("policyNo", strPolicyInfo.getChildText("policyNo"));
								toMap.put("noticeNo", strPolicyInfo.getChildText("noticeNo"));
								toMap.put("validateCode", strPolicyInfo.getChildText("validateCode"));
								toMap.put("totalPremium", strPolicyInfo.getChildText("totalPremium"));
								toMap.put("appStatus", "1");// 标记成功或失败状态
							} else {
								// 投保失败
								for (Iterator<Element> iter = headList.iterator(); iter.hasNext();) {
									Element eleNodH = (Element) iter.next();
									toMap.put(eleNodH.getName(), eleNodH.getValue());
								}
								toMap.put("appStatus", "0");// 标记成功或失败状态
							}
						}
						// 合众承保
						else if (insureTypeCode.startsWith("1061")) {
							Element strRoot = rultDoc.getRootElement();
							Element strHeader = strRoot.getChild("Header");
							String strRSLT = strHeader.getChildText("PA_RSLT_CODE");
							if ("0".equals(strRSLT)) {
								// 投保成功
								toMap.put("TRAN_CODE", strHeader.getChildText("TRAN_CODE"));
								toMap.put("BK_SERIAL", strHeader.getChildText("BK_SERIAL"));
								toMap.put("PA_RSLT_CODE", strHeader.getChildText("PA_RSLT_CODE"));
								toMap.put("PA_RSLT_MESG", strHeader.getChildText("PA_RSLT_MESG"));
								Element strPolicyInfo = strRoot.getChild("Response").getChild("policyInfo");
								toMap.put("applyPolicyNo", strPolicyInfo.getChildText("applyPolicyNo"));
								toMap.put("policyNo", strPolicyInfo.getChildText("policyNo"));
								toMap.put("inusred-name", strPolicyInfo.getChildText("inusred-name"));
								toMap.put("certi-type", strPolicyInfo.getChildText("certi-type"));
								toMap.put("gender", strPolicyInfo.getChildText("gender"));
								toMap.put("certi-code", strPolicyInfo.getChildText("certi-code"));
								toMap.put("birthday", strPolicyInfo.getChildText("birthday"));
								toMap.put("appStatus", "1");// 标记成功或失败状态
							} else {
								// 投保失败
								toMap.put("PA_RSLT_CODE", strHeader.getChildText("PA_RSLT_CODE"));
								toMap.put("PA_RSLT_MESG", strHeader.getChildText("PA_RSLT_MESG"));
								toMap.put("appStatus", "0");// 标记成功或失败状态
							}
							// 太平养老
						} else if (insureTypeCode.startsWith("1018")) {
							Element eleRoot = rultDoc.getRootElement();
							Element eleHeader = eleRoot.getChild("Header");
							String strRSLT = eleHeader.getChildText("PA_RSLT_CODE");
							if ("0000".equals(strRSLT)) {
								// 投保成功
								toMap.put("TRAN_CODE", eleHeader.getChildText("TRAN_CODE"));
								toMap.put("BK_SERIAL", eleHeader.getChildText("BK_SERIAL"));
								toMap.put("PA_RSLT_CODE", eleHeader.getChildText("PA_RSLT_CODE"));
								toMap.put("PA_RSLT_MESG", eleHeader.getChildText("PA_RSLT_MESG"));
								Element strPolicyInfo = eleRoot.getChild("Response").getChild("policyInfo");
								toMap.put("applyPolicyNo", strPolicyInfo.getChildText("applyPolicyNo"));
								toMap.put("policyNo", strPolicyInfo.getChildText("policyNo"));
								toMap.put("BDZT", strPolicyInfo.getChildText("BDZT"));
								toMap.put("appStatus", "1");// 标记成功或失败状态
							} else {
								toMap.put("TRAN_CODE", eleHeader.getChildText("TRAN_CODE"));
								toMap.put("BK_SERIAL", eleHeader.getChildText("BK_SERIAL"));
								toMap.put("PA_RSLT_CODE", eleHeader.getChildText("PA_RSLT_CODE"));
								toMap.put("PA_RSLT_MESG", eleHeader.getChildText("PA_RSLT_MESG"));
								toMap.put("appStatus", "0");// 标记成功或失败状态
							}
						}
						// 百年保险
						else if (insureTypeCode.startsWith("0005")) {
							Element eleRoot = rultDoc.getRootElement();
							Element eleHeader = eleRoot.getChild("Header");
							String strRSLT = eleHeader.getChildText("PA_RSLT_CODE");
							List<Element> headList = (List<Element>) eleHeader.getChildren();
							if ("0".equals(strRSLT)) {
								// 投保成功
								for (Iterator<Element> iter = headList.iterator(); iter.hasNext();) {
									Element eleNodH = (Element) iter.next();
									toMap.put(eleNodH.getName(), eleNodH.getValue());
								}
								Element strPolicyInfo = eleRoot.getChild("Response").getChild("policyInfo");
								toMap.put("policyNo", strPolicyInfo.getChildText("policyNo"));
								toMap.put("appStatus", "1");// 标记成功或失败状态
							} else {
								// 投保失败
								for (Iterator<Element> iter = headList.iterator(); iter.hasNext();) {
									Element eleNodH = (Element) iter.next();
									toMap.put(eleNodH.getName(), eleNodH.getValue());
								}
								toMap.put("appStatus", "0");// 标记成功或失败状态
							}
						}
					}
				}
			}
			// 后更新，将保险公司返回结果保存至数据库
			this.updateResultMap(toMap, ID);
			// 保险公司返回报文中有保费的校验：保险公司返回的保费与网站计算的保费不一致时增加邮件通知
			if(insureTypeCode.startsWith("1015")||insureTypeCode.startsWith("2011")||insureTypeCode.startsWith("2096")){
			QueryBuilder qboders = new QueryBuilder("select totalAmount from orders where  ordersn = ?",orderID);
			QueryBuilder qbicrd = new QueryBuilder("select totalPremium from insuredcompanyreturndata where  ordersn= ? ",orderID);
			double totalAmount = qboders.executeDouble();
			double totalPremium =qbicrd.executeDouble();
			if(totalAmount!=totalPremium){
				DownloadNet down=new DownloadNet();
//				down.sendPrintErrorMail(orderID, "网站计算出的保费与保险公司返回的保费不一致");
			}
			}
			
		} catch (Exception e) {
			logger.error("调用经代通接口出现异常" + e.getMessage(), e);
			ErrMsg = "调用经代通接口出现异常";
			sendErrorMail(orderID,ErrMsg);
		}
		if ("2007".equals(insureTypeCode)
				&& (!this.findOrderSuccessByCom(insureTypeCode, orderID)))
		{
			sendEpolicy(orderID);
		}
		
		//判断华泰电子保单
		if ("2023".equals(insureTypeCode) && "1".equals(toMap.get("appStatus")) )
		{
			
			DownloadNet db=new DownloadNet();
			try {
//				db.EpolicyDeal(orderID ,toMap.get("policyPath") + "" , insureTypeCode);
			} catch (Exception e) {
				logger.error("电子发送保单异常：" + e.getMessage(), e);
			}
		} else 
		//判断大众电子保单
		if ("2071".equals(insureTypeCode) && "1".equals(toMap.get("appStatus")) )
		{
			try {
				Map<String, Object> emailMap = findInformationAppntByOrderSn(orderID);
				emailMap.put("FilePath", toMap.get("remark1"));
				emailMap.put("FileName", "电子保单.pdf");
				Member meber = new Member();
				meber.setEmail(emailMap.get("mail").toString());
				emailMap.put("Member", meber);
				emailMap.put("OrderSn", orderID);
				ActionUtil.dealAction("wj00041", emailMap, null);

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		//判断大都会电子保单
		if ("1048".equals(insureTypeCode) && "1".equals(toMap.get("appStatus")) )
		{
			
				try {
					Map<String, Object> emailMap = findInformationAppntByOrderSn(orderID);
					emailMap.put("FilePath", toMap.get("remark1"));
					emailMap.put("FileName", "电子保单.pdf");
					Member meber = new Member();
					meber.setEmail(emailMap.get("mail").toString());
					emailMap.put("Member", meber);
					emailMap.put("OrderSn", orderID);
					ActionUtil.dealAction("wj00041", emailMap, null);   

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
		}
	}
	
	private void sendErrorMail(String ordsn,String ErrMsg){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			GetDBdata db1 = new GetDBdata();
			Member m1 = new Member();
			m1.setEmail(db1.getOneValue("select value from zdconfig where type='InsureErrorMail'"));
			String sql = "select productName,totalAmount,(select effective from information where orderItem_id in (select id from orderitem where order_id in(select id from orders where ordersn=?))) from orders where orderSn = ?";
			map.put("Member", m1);
			map.put("OrderSn", ordsn);
			map.put("ErrMsg",ErrMsg);
			DataTable dt = new QueryBuilder(sql,ordsn,ordsn).executeDataTable();
			if(dt.getRowCount()>0){                     
				map.put("ProductName", dt.getString(0, 0));
				map.put("TotalAmount", dt.getString(0, 1));
				map.put("Effective",dt.getString(0, 2).substring(0, 11));
			}else{
				logger.warn("未查询到订单相关信息");
			}
			ActionUtil tActionUtil = new ActionUtil();
			tActionUtil.dealAction("wj00047", map, null);
		} catch (Exception e) {
			logger.error("出现异常:" + "sendErrorMail方法出现异常"+e.getMessage(), e);
		}
	}
	
	private void sendPrintErrorMail(String ordsn){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			GetDBdata db1 = new GetDBdata();
			Member m1 = new Member();
			m1.setEmail(db1.getOneValue("select value from zdconfig where type='PrintErrorMail'"));
			String sql = "select productName,totalAmount,(select effective from information where orderItem_id in (select id from orderitem where order_id in(select id from orders where ordersn=?))),policyNo,insuranceResultMsg from orders a,InsuredCompanyReturnData b where a.orderSn = b.orderSn and a.orderSn = ?";
			map.put("Member", m1);
			map.put("OrderSn", ordsn);
			DataTable dt = new QueryBuilder(sql,ordsn,ordsn).executeDataTable();
			if(dt.getRowCount()>0){
				map.put("ProductName", dt.getString(0, 0));
				map.put("TotalAmount", dt.getString(0, 1));
				map.put("Effective",dt.getString(0, 2).substring(0, 11));
				map.put("PolicyNo",dt.getString(0,3));
				map.put("ErrMsg",dt.getString(0,4));
			}else{
				logger.warn("未查询到订单及保单相关信息");
			}
			ActionUtil tActionUtil = new ActionUtil();
			tActionUtil.dealAction("wj00046", map, null);
		} catch (Exception e) {
			logger.error("出现异常:" + "sendPrintErrorMail方法出现异常"+e.getMessage(), e);
		}
	}
	
	public void sendEpolicy(String orderID) {
		Map<String, Object> emailMap = findInformationAppntByOrderSn(orderID);
		String policyPath = checkElectronicPolicyIsSuccess(orderID);
		if (policyPath != null && !"".equals(policyPath)) {
			emailMap.put("FilePath", policyPath);
			emailMap.put("FileName", "电子保单.pdf");
			Member meber = new Member();
			meber.setEmail(emailMap.get("mail").toString());
			emailMap.put("Member", meber);
			emailMap.put("OrderSn", orderID);
			ActionUtil.dealAction("wj00041", emailMap, null);
		} else {
			logger.error("电子保单打印出现异常");
			sendPrintErrorMail(orderID);
		}
	}
	
	public void sendExcel(String orderID){
		try {
			DealWithSendExcel(orderID);
			Map<String, Object> emailMap = new HashMap<String, Object>();
			File path = new File(InsureTransfer.class.getResource("/").getFile());
			String folderPath = path.getParentFile().getParentFile().toString();
			String policyPath = folderPath + "/template/2034/"+PubFun.getCurrentDate()+"/"+orderID+".xls";
			GetDBdata db1 = new GetDBdata();
			Member m1 = new Member();
			m1.setEmail(db1.getOneValue("select value from zdconfig where type='MYExcelEmail'"));
			String sql="select o.productName as productName,outRiskCode,brkRiskCode,effective,fail from orders o,orderitem oi,information fi where o.id = oi.order_id and fi.orderitem_id=oi.id and o.orderSn = ?";
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
			emailMap.put("FileName", orderID+".xls");
			emailMap.put("Member", m1);
			ActionUtil.dealAction("wj00053", emailMap, null);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
	}

	public Map<String, Object> findInformationAppntByOrderSn(String orderID) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select o.productName as productname, ia.applicantName as applicantname,ia.applicantMail as mail from orders o , information fi , informationappnt ia , orderitem oi "
				+ "where o.id = oi.order_id and fi.orderitem_id=oi.id and fi.id = ia.information_id and  o.orderSn = ?";
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

	public String checkElectronicPolicyIsSuccess(String orderID) {
		String returnFlag = toGenerateElectronicPolicy(orderID);
		if (returnFlag == null || "".equals(returnFlag)) {
			int i = 0;
			while (i < 5) {
				String returnFlag1 = toGenerateElectronicPolicy(orderID);
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

	public String toGenerateElectronicPolicy(String orderSN) {
		try {
			GlobalInput tG = new GlobalInput();
			tG.Operator = "SysAuto";
			// 将用户信息与保单信息放入容器
			VData cInputData = new VData();
			cInputData.addElement(tG);
			cInputData.addElement(orderSN);// OrderSN
			ElectronicPolicyPrint tElectronicPolicyPrint = new ElectronicPolicyPrint();
			// 调用保单打印程序进行打印
			if (tElectronicPolicyPrint.submitData(cInputData, "")) {
				logger.info("保单打印成功！保单存储路径为：{}", tElectronicPolicyPrint.getResult().get("ResultPath"));
				return tElectronicPolicyPrint.getResult().get("ResultPath").toString();
			} else {
				logger.info("保单打印失败！原因是：{}", tElectronicPolicyPrint.getError().getFirstError());
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
	 * 保存信息记录
	 * 
	 * @param insureTypeCode
	 * @param orderID
	 * @return
	 */
	private String saveResultMap(String insureTypeCode, String orderID) {
		String ID = "";
		try {
			Transaction transaction = new Transaction();
			InsuredCompanyReturnDataSchema tICRDSchema = new InsuredCompanyReturnDataSchema();
			if (StringUtil.isNotEmpty(insureTypeCode) && StringUtil.isNotEmpty(orderID)) {
				// 新增前删除，保证数据正确性
				JdbcTemplateData jtd = new JdbcTemplateData();
				String sql = "delete from InsuredCompanyReturnData where orderSn = ?";
				String[] sqltemp = {orderID};
				if (!jtd.updateOrSaveOrDelete(sql,sqltemp)) {
					logger.error("类InsureTransfer执行方法saveResultMap()发生异常！");
					return null;
				}
				ID = NoUtil.getMaxID("ICRDS") + "";
				tICRDSchema.setID(ID);// 记录ID
				tICRDSchema.setorderSn(orderID);// 订单号
				tICRDSchema.setinsuranceCode(insureTypeCode);// 保险公司编码
				tICRDSchema.setappStatus("0");
				tICRDSchema.setcurrentDate(DateUtil.getCurrentDateTime());// 当前日期
				tICRDSchema.setcreateDate(new Date());
				transaction.add(tICRDSchema, Transaction.INSERT);
				transaction.commit();
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("类InsureTransfer方法saveResultMap()出现异常" + e.getMessage(), e);
		}
		return ID;
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
			String sql = "where ID = '" + ID + "'";// 当前时间之前未发生结算交互
			QueryBuilder qb = new QueryBuilder(sql);
			InsuredCompanyReturnDataSet tICRDSet = new InsuredCompanyReturnDataSet();
			InsuredCompanyReturnDataSchema tICRDSchema = new InsuredCompanyReturnDataSchema();
			tICRDSet = tICRDSchema.query(qb);
			if (!tICRDSet.isEmpty() && tICRDSet.size() > 0) {
				tICRDSchema = tICRDSet.get(0);
				tICRDSchema.setapplyPolicyNo(reMap.get("applyPolicyNo") == null ? "" : reMap.get("applyPolicyNo").toString());// 投保单号
				tICRDSchema.setpolicyNo(reMap.get("policyNo") == null ? "" : reMap.get("policyNo").toString());// 保单号
				tICRDSchema.setnoticeNo(reMap.get("noticeNo") == null ? "" : reMap.get("noticeNo").toString());// 财务通知单号
				tICRDSchema.setvalidateCode(reMap.get("validateCode") == null ? "" : reMap.get("validateCode").toString());// 保单验证码
				if (reMap.get("totalPremium") != null && reMap.get("totalPremium").toString() != "") {
					tICRDSchema.settotalPremium(Double.parseDouble(reMap.get("totalPremium").toString()));// 保单保费
				}
				tICRDSchema.settradeSeriNO(this.tradeSeriNO);// 支付银行或平台返回的交易的流水号
				tICRDSchema.setappStatus(reMap.get("appStatus") == null ? "" : reMap.get("appStatus").toString());// 投保状态
				tICRDSchema.setinsuranceTransCode(reMap.get("TRAN_CODE") == null ? "" : reMap.get("TRAN_CODE").toString());// 保险公司返回交易流水号
				tICRDSchema.setinsuranceBankCode(reMap.get("BANK_CODE") == null ? "" : reMap.get("BANK_CODE").toString());// 保险公司返回银行编码
				tICRDSchema.setinsuranceBankSeriNO(reMap.get("BK_SERIAL") == null ? "" : reMap.get("BK_SERIAL").toString());// 保险公司返回银行交易流水号
				tICRDSchema.setinsuranceBRNO(reMap.get("BRNO") == null ? "" : reMap.get("BRNO").toString());// 保险公司返回系列号
				tICRDSchema.setinsuranceTELLERNO(reMap.get("TELLERNO") == null ? "" : reMap.get("TELLERNO").toString());// 保险公司反水商户号
				tICRDSchema.setinsuranceAreaCode(reMap.get("REGION_CODE") == null ? "" : reMap.get("REGION_CODE").toString());// 保险公司返回地区编码
				tICRDSchema.setinsuranceACCTDate(reMap.get("BK_ACCT_DATE") == null ? "" : reMap.get("BK_ACCT_DATE").toString());// 保险公司返回投保日期
				tICRDSchema.setinsuranceACCTTime(reMap.get("BK_ACCT_TIME") == null ? "" : reMap.get("BK_ACCT_TIME").toString());// 保险公司返回投保日期
				tICRDSchema.setinsuranceResultCode(reMap.get("PA_RSLT_CODE") == null ? "" : reMap.get("PA_RSLT_CODE").toString());// 保险公司返回投保结果编码
				tICRDSchema.setinsuranceResultMsg(reMap.get("PA_RSLT_MESG") == null ? "" : reMap.get("PA_RSLT_MESG").toString());// 保险公司返回投保结果信息
				tICRDSchema.setpolicyPath(reMap.get("policyPath") == null ? "" : reMap.get("policyPath").toString());// 保险公司返回的保单下载路径
				tICRDSchema.setremark1(reMap.get("remark1") == null ? "" : reMap.get("remark1").toString());//保险公司返回保单下载物理路径
				tICRDSchema.setmodifyDate(new Date());
				tICRDSchema.setsettleState("0");// 未与结算中心交互
				transaction.add(tICRDSchema, Transaction.UPDATE);
				transaction.commit();
			}
		} catch (Exception e) {
			logger.error("类InsureTransfer方法updateResultMap()出现异常" + e.getMessage(), e);
		}
	}

	/************************************************************************************/

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
			InsureTransfer Fileadds = new InsureTransfer();
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

	/***********************************************************************************/
	private static String spiltAdd(String temp) {
		String[] str = temp.split(",");
		String newStr = "";
		for (int i = 0; i < str.length; i++) {
			newStr = newStr + "'" + str[i] + "'" + ",";
		}
		return newStr.substring(0, newStr.length() - 1);
	}

	public static void main(String[] args) {
		try {
//			 Format format = Format.getPrettyFormat();
//			 format.setEncoding("UTF-8");// 设置xml文件的字符, 解决中文问题
			// format.setExpandEmptyElements(true);
//			 XMLOutputter xmlout = new XMLOutputter(format);
//			 InsureTransfer it = new InsureTransfer();
//			 Document doc = it.reqBeanToXML("1061","2013000000000288");
//			 xmlout.output(doc, System.out);//request报文输出
// 		     ByteArrayOutputStream bo = new ByteArrayOutputStream();
// 		     xmlout.output(doc, bo);
// 		     File xmlFile=new File("D:\\HZXml.xml");
// 		     FileWriter fw=new FileWriter(xmlFile);
// 		     fw.write(bo.toString());
// 		     fw.flush();
// 		     fw.close();
			
//			 SAXBuilder builder=new SAXBuilder(false);
//			 Document sendDoc=builder.build("D:\\Example.xml");
//			// xmlout.output(sendDoc, System.out);
//			 Document rultDoc = ServiceClient.execute(doc, "UTF-8");
//			 xmlout.output(rultDoc, System.out);//response报文输出
//		
//			 it.saveResultMap("2007", "13469502996712478");
//			 it.callInsTransInterface("2007","13469502996712478");
//			 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			 Date birthDay = df.parse("1989-09-27");
//			 System.out.println("==============="+getAge(birthDay));
//			 java.text.Format formatter=new
//			 java.text.SimpleDateFormat("yyyy-MM-dd");
//			 java.util.Date todayDate=new java.util.Date();
			//
			// System.out.println(formatter.format(todayDate));
			// Information info = new Information();
			// info.setEffective(new java.util.Date());
			// System.out.println(new
			// java.text.SimpleDateFormat("yyyy-MM-dd").format(info.getEffective()));
			// System.out.println("==============================="+it.getDestByCountryCode("2007","'PA0025','PA0026','PA0028','PA0030'"));
			// System.out.println(new Date());
			// System.out.println(it.calSDate(new
			// java.text.SimpleDateFormat("yyyy-MM-dd").format(new
			// Date()),1,"D"));
			// System.out.println(it.stringToDate("2012-09-18 00:00:00","yyyy-MM-dd 24:00:00"));
			 
			 
			 try {
				    InsureTransfer in = new InsureTransfer();
					Map<String, Object> emailMap = in.findInformationAppntByOrderSn("2013000011112550");
					emailMap.put("FilePath", "c:/a.txt");
					emailMap.put("FileName", "电子保单.pdf");
					Member meber = new Member();
					meber.setEmail(emailMap.get("mail").toString());
					emailMap.put("Member", meber);
					ActionUtil.dealAction("wj00041", emailMap, null);

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	//	InsureTransfer it = new InsureTransfer();
		//it.sendEpolicy("2012000000000085");

	}
	
	


}
