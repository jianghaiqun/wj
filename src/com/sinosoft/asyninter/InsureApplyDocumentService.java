/**
 * Project Name:wj File Name:InsureApplyDocumentService.java Package
 * Name:com.sinosoft.asyninter Date:2016年9月8日下午3:14:07 Copyright (c) 2016,
 * www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.asyninter;

import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.service.impl.BaseServiceImpl;
import cn.com.sinosoft.util.CommonUtil;
import com.activemq.ProducerMQ;
import com.alibaba.fastjson.JSON;
import com.sinosoft.aeonlife.utils.SftpCommon;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.IdcardUtils;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.ZipUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.PartnerInfoSchema;
import com.sinosoft.schema.PartnerInfoSet;
import com.sinosoft.schema.PartnerPolicyReqSchema;
import com.sinosoft.schema.PartnerPolicyReqSet;
import com.sinosoft.schema.SDInformationAppntSchema;
import com.sinosoft.schema.SDInformationInsuredSchema;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDInformationSet;
import com.sinosoft.schema.SDOrderItemSchema;
import com.sinosoft.schema.SDOrdersSchema;
import com.sinosoft.schema.SDOrdersSet;
import com.sinosoft.schema.TradeInformationSchema;
import com.sinosoft.schema.TradeInformationSet;
import com.sinosoft.schema.TradeSummaryInfoSchema;
import com.sinosoft.schema.TradeSummaryInfoSet;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FERiskAppFactor;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:InsureApplyDocumentService <br/>
 * Function:处理合作方投保申请文件. <br/>
 * Date:2016年9月8日 下午3:14:07 <br/>
 *
 * @author:dongsheng
 */
public class InsureApplyDocumentService extends BaseServiceImpl<PartnerInfoSchema, String> {
	private static final Logger logger = LoggerFactory.getLogger(InsureApplyDocumentService.class);
	private final static String CLASS_NAME = InsureApplyDocumentService.class.getName();

	private final static int LIMIT = 10000;
	private final static String SPLIT_STRING = "<-->";
	private static List<InsuranceCompany> companies = InsuranceCompany.build();

	/**
	 * getPartnerFolder:获取合作方对应的文件存储路径. <br/>
	 * 
	 * @author dongsheng
	 * @param loadType
	 * @param date
	 * @param strType
	 * @return
	 */
	protected PartnerInfoSet getPartnerInfoSetWithFullFtpPath(String loadType, Date date, String strType) {

		String sql = "where type='asyn'";
		QueryBuilder queryBuilder = new QueryBuilder(sql);
		PartnerInfoSchema parterInfo = new PartnerInfoSchema();
		PartnerInfoSet set = parterInfo.query(queryBuilder);
		for (int i = 0; i < set.size(); i++) {
			PartnerInfoSchema info = set.get(i);
			StringBuilder builder = new StringBuilder(50);
			builder.append(info.getFtpPath())
					.append(loadType)
					.append(DateUtil.toString(date, DateUtil.Format_Date2))
					.append("/")
					.append(strType)
					.append("/");
			String result = builder.toString();
			info.setFtpPath(result);
		}
		return set;
	}

	protected PartnerInfoSet getPartnerInfoSetWithFullFtpPath(String loadType, String strType) {

		return this.getPartnerInfoSetWithFullFtpPath(loadType, new Date(), strType);

	}

	protected String getInsuranceCompanyFolderPath(String loadType, String strType, InsuranceCompany company) {

		return getInsuranceCompanyFolderPath(loadType, new Date(), strType, company);
	}

	protected String getInsuranceCompanyFolderPath(String loadType, Date date, String strType, InsuranceCompany company) {

		if (company == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder(50);
		builder.append(company.getDir())
				.append(loadType)
				.append(DateUtil.toString(date, DateUtil.Format_Date2))
				.append("/")
				.append(strType)
				.append("/");

		return builder.toString();
	}

	/**
	 * getFilePath:获取下载文件,所需的文件路径.(不包含文件类型.e.g.".csv") <br/>
	 *
	 * @author dongsheng
	 * @param partnerName
	 * @param strType
	 * @return
	 */
	protected String getFileName(String partnerName, String strType, int index) {

		StringBuilder builder = new StringBuilder(50);
		builder.append(partnerName)
				.append(SftpCommon.FILE_SPLIT)
				.append(strType)
				.append(SftpCommon.FILE_SPLIT)
				.append(DateUtil.getCurrentDate(DateUtil.Format_Date2))
				.append(SftpCommon.FILE_SPLIT)
				.append(index);
		return builder.toString();
	}

	/**
	 * getInsureResultFromAeonlife:从合作方获取投保申请文件. <br/>
	 *
	 * @author dongsheng
	 * @throws IOException
	 */
	public void getInsureApplyFromPartner() throws IOException {

		PartnerInfoSet set = getPartnerInfoSetWithFullFtpPath(SftpCommon.DOWNLOAD, SftpCommon.STR_POLICYREQ);
		// upZipFiles()方法的参数是文件所在的目录.注意结尾须是"/".
		if (set == null) {
			logger.warn("数据错误,没找到合作方!");
			return;
		}
		for (int i = 0; i < set.size(); i++) {
			PartnerInfoSchema info = set.get(i);
			// 遍历每个合作方对应的文件中的csv文件.
			List<List<String>> rows = CSVUtils.readCSVFromZip(info.getFtpPath());
			if (rows == null) {
				logger.warn("{}文件没有内容!", info.getFtpPath());
				continue;
			}
			// 一个供应商一个处理
			for (List<String> row : rows) {
				PartnerPolicyReqSchema req = savePartnerPolicyReq(row, info);
				if (req != null) {
					saveProcedure(req, info);
				}
			}

		}

	}

	/**
	 * savePartnerPolicyReq:(保存读取的数据到临时表). <br/>
	 * 
	 * @author dongsheng
	 * @param row
	 * @param info
	 */
	private PartnerPolicyReqSchema savePartnerPolicyReq(List<String> row, PartnerInfoSchema partnerInfo) {

		// 保存到临时表中.
		PartnerPolicyReqSchema policyReqSchema = new PartnerPolicyReqSchema();
		policyReqSchema.setid(CommonUtil.getUUID());
		// paysn里面存放的是第三房传过来的订单号.
		policyReqSchema.setpaySn(row.get(0));
		policyReqSchema.setproductId(row.get(1));
		policyReqSchema.setaccountNo(row.get(2));
		policyReqSchema.setaccountName(row.get(3));
		policyReqSchema.setidCardNo(row.get(4));
		policyReqSchema.setpolicyholderMobile(row.get(5));
		policyReqSchema.setpolicyholderEmail(row.get(6));
		policyReqSchema.setpolicyholderAddress(row.get(7));
		policyReqSchema.setpayAmount(row.get(8));
		policyReqSchema.setexpiredType(row.get(9));
		if (StringUtil.isNotEmpty(row.get(10))) {
			if (isLegalDate(row.get(10))) {
				policyReqSchema.setpayDateTime(DateUtil.parse(row.get(10), "yyyyMMdd HH:mm:ss"));
			}
		}
		if (StringUtil.isNotEmpty(row.get(11))) {
			if (isLegalDate(row.get(11))) {
				policyReqSchema.setdealConfirmDateTime(DateUtil.parse(row.get(11), "yyyyMMdd HH:mm:ss"));
			}
		}
		policyReqSchema.setfundTransferSn(row.get(12));
		policyReqSchema.setcreateDatetime(new Date());
		policyReqSchema.setmodifyDatetime(policyReqSchema.getcreateDatetime());
		policyReqSchema.setpartnerId(partnerInfo.getid());
		// 对数据进行初步校验.并在对象中设置校验结果和信息.
		policyReqSchema = checkDataLegal(policyReqSchema);
		if (policyReqSchema != null) {
			Transaction transaction = new Transaction();
			transaction.add(policyReqSchema, Transaction.INSERT);
			if (!transaction.commit()) {
				logger.error("数据保存出错{}", policyReqSchema);
				return null;
			}
		}
		return policyReqSchema;
	}

	private void saveProcedure(PartnerPolicyReqSchema policyReqSchema, PartnerInfoSchema partnerInfo) {

		if (!"Y".equals(policyReqSchema.getisDataCorrect())) {
			// 数据有误,不进行下一步操作.
			return;
		}
		Transaction transaction = new Transaction();

		String orderSn = PubFun.GetOrderSn();
		// 投保人编号
		String applicantSn = PubFun.GetSDAppntSn();
		// 被保人编号
		String insuredSn = PubFun.GetSDInsuredSn();
		// 支付金额
		String aeonAmount = policyReqSchema.getpayAmount();
		policyReqSchema.setorderSn(orderSn);

		// 根据产品id查询
		// Map<String, Object> productMap =
		// getProduct(policyReqSchema.getproductId());
		FMRisk fmrisk = getFMRisk(policyReqSchema.getproductId());
		policyReqSchema.setproductOutId(fmrisk.getOutRiskCode());

		/**
		 * ********************************* 保存订单表
		 * ***********************************
		 */
		SDOrdersSchema sdorders = new SDOrdersSchema();
		// 获取订单号
		sdorders.setorderSn(orderSn);
		sdorders.setid(PubFun.GetOrderID(orderSn));
		// 创建时间
		sdorders.setcreateDate(new Date());
		// 新订单修改时间与创建时间保持一致
		sdorders.setmodifyDate(sdorders.getcreateDate());
		// 会员ID
		sdorders.setmemberId(partnerInfo.getchannelSn());
		// 订单状态为为支付
		sdorders.setorderStatus(SDOrder.SDOrderStatus.prepay.ordinal());
		// 支付类型
		sdorders.setpayType("zjzf");
		// 待付状态为已支付
		sdorders.setpayStatus(SDOrder.SDPayStatus.unpaid.ordinal());
		// 折扣率
		sdorders.setdiscountRates("");
		// 金额
		// 订单实际支付金额
		sdorders.setpayPrice(aeonAmount);
		// 已付金额
		sdorders.setpayAmount(aeonAmount);
		// 商品总价格
		sdorders.setproductTotalPrice(aeonAmount);
		// 订单总额
		sdorders.settotalAmount(aeonAmount);
		sdorders.setoffsetPoint("0");
		sdorders.setorderCoupon("0");
		sdorders.setorderIntegral("0");
		sdorders.setorderActivity("0.00");
		sdorders.setsumActivity("0.00");
		// 记录百年订单号 BNRS-151106151238143076
		sdorders.setpaySn(policyReqSchema.getpaySn());
		// 产品个数 默认为1
		sdorders.setproductNum("1");
		// 记录渠道
		sdorders.setchannelsn(partnerInfo.getchannelSn());
		transaction.add(sdorders, Transaction.INSERT);
		/**
		 * ********************************* 保存订单信息表
		 * ***********************************
		 */
		SDInformationSchema sdinformation = new SDInformationSchema();
		sdinformation.setId(PubFun.GetInformationID(orderSn));
		sdinformation.setcreateDate(new Date());
		sdinformation.setmodifyDate(sdinformation.getcreateDate());
		// 订单明细表编号
		sdinformation.setinformationSn(PubFun.GetSDInformationSn());
		// 订单表
		sdinformation.setorderSn(orderSn);
		// 产品编码
		sdinformation.setproductId(fmrisk.getRiskCode());
		// 保险公司产品编码
		sdinformation.setproductOutCode(fmrisk.getOutRiskCode());
		// 产品名称
		sdinformation.setproductName(fmrisk.getRiskName());
		// 产品折扣率
		sdinformation.setdiscountRates("");
		// 商品原价(剔除产品中心折扣)
		sdinformation.setproductPrice(aeonAmount);
		sdinformation.setproductDiscountPrice(aeonAmount);
		// 产品详细页地址
		sdinformation.setproductHtmlFilePath("");
		sdinformation.setproductQuantity("1");
		// 保险公司编码
		sdinformation.setinsuranceCompany(fmrisk.getSupplierCode());

		// 内部统计险种中类别
		sdinformation.setriskType(fmrisk.getRiskKind2());
		// 内部统计险种小类别
		sdinformation.setsubRiskType(fmrisk.getRiskKind3());
		// 订单id
		sdinformation.setsdorder_id(sdorders.getid());
		sdinformation.setpolicyNum("1");
		// 订单份数
		BigDecimal payAmount = new BigDecimal(aeonAmount);
		BigDecimal productPrice = new BigDecimal(fmrisk.getDiscountPrice());
		int appMulti = payAmount.divide(productPrice).intValue();
		sdinformation.setappMult(String.valueOf(appMulti));

		transaction.add(sdinformation, Transaction.INSERT);

		/**
		 * ********************************* 保存投保人表
		 * ***********************************
		 */
		SDInformationAppntSchema appntSchema = new SDInformationAppntSchema();
		appntSchema.setId(PubFun.GetInformationAppntID(orderSn));
		// 创建时间
		appntSchema.setcreateDate(new Date());
		appntSchema.setmodifyDate(appntSchema.getcreateDate());
		// 订单详细表编号
		appntSchema.setinformationSn(sdinformation.getinformationSn());
		// 投保人编号
		appntSchema.setapplicantSn(applicantSn);
		// 投保人姓名
		appntSchema.setapplicantName(policyReqSchema.getaccountName());
		// 身份类型
		appntSchema.setapplicantIdentityType("0");
		appntSchema.setapplicantIdentityTypeName("身份证");
		appntSchema.setapplicantIdentityId(policyReqSchema.getidCardNo());
		// 性别
		String genderName = IdcardUtils.getGenderByIdCard(policyReqSchema.getidCardNo());
		Gender gender = Gender.getGenderByName(genderName);
		appntSchema.setapplicantSex(gender.getCode());
		appntSchema.setapplicantSexName(gender.getName());
		// 生日
		appntSchema.setapplicantBirthday(IdcardUtils.getBirthByIdCard(policyReqSchema.getidCardNo()));
		// 年龄
		appntSchema.setapplicantAge(String.valueOf(IdcardUtils.getAgeByIdCard(policyReqSchema.getidCardNo())));
		// 邮箱
		appntSchema.setapplicantMail(policyReqSchema.getpolicyholderEmail());
		// 地址
		appntSchema.setapplicantAddress(policyReqSchema.getpolicyholderAddress());
		// 电话
		appntSchema.setapplicantMobile(policyReqSchema.getpolicyholderMobile());
		// 订单详细表id
		appntSchema.setsdinformaiton_id(sdinformation.getId());

		transaction.add(appntSchema, Transaction.INSERT);

		/**
		 * ********************************* 保存被保人表
		 * ***********************************
		 */

		SDInformationInsuredSchema sdinformationinsured = new SDInformationInsuredSchema();
		sdinformationinsured.setId(PubFun.GetInformationInsuredID(orderSn));
		// 创建时间
		sdinformationinsured.setcreateDate(new Date());
		sdinformationinsured.setmodifyDate(sdinformationinsured.getcreateDate());
		// 订单号
		sdinformationinsured.setorderSn(orderSn);
		// 详细表号
		sdinformationinsured.setinformationSn(sdinformation.getinformationSn());
		// 被保人编号
		sdinformationinsured.setrecognizeeSn(insuredSn);
		// 投被保人关系
		sdinformationinsured.setrecognizeeAppntRelation("00");
		sdinformationinsured.setrecognizeeAppntRelationName("本人");
		// 人员姓名
		sdinformationinsured.setrecognizeeName(appntSchema.getapplicantName());
		// 身份类型
		sdinformationinsured.setrecognizeeIdentityType(appntSchema.getapplicantIdentityType());
		sdinformationinsured.setrecognizeeIdentityTypeName(appntSchema.getapplicantIdentityTypeName());
		sdinformationinsured.setrecognizeeIdentityId(appntSchema.getapplicantIdentityId());
		// 性别
		sdinformationinsured.setrecognizeeSex(appntSchema.getapplicantSex());
		sdinformationinsured.setrecognizeeSexName(appntSchema.getapplicantSexName());
		// 生日
		sdinformationinsured.setrecognizeeBirthday(appntSchema.getapplicantBirthday());
		sdinformationinsured.setrecognizeeAge(appntSchema.getapplicantAge());
		// 电话
		sdinformationinsured.setrecognizeeMobile(appntSchema.getapplicantMobile());
		// 邮件
		sdinformationinsured.setrecognizeeMail(appntSchema.getapplicantMail());
		// 详细地址
		sdinformationinsured.setrecognizeeAddress(appntSchema.getapplicantAddress());
		// 关系为本人标志 rid_td 批量上传 rid_orther 其他方式
		sdinformationinsured.setisSelf("Y");
		// 被保人唯一编码
		String index = "1";
		sdinformationinsured.setinsuredSn(orderSn + "_" + index);
		// 保险费
		sdinformationinsured.setrecognizeePrem(aeonAmount);
		// 操作
		sdinformationinsured.setrecognizeeOperate("1");
		sdinformationinsured.setmulInsuredFlag("rid_me");
		// 详细表
		sdinformationinsured.setsdinformation_id(sdinformation.getId());
		// 总保险费
		sdinformationinsured.setrecognizeeTotalPrem(aeonAmount);
		// key
		sdinformationinsured.setrecognizeeKey(orderSn + "_" + index);
		// 折后价格
		sdinformationinsured.setdiscountPrice(aeonAmount);

		transaction.add(sdinformationinsured, Transaction.INSERT);
		/**
		 * 保单
		 * 
		 */
		SDInformationRiskTypeSchema sdinformationRiskType = new SDInformationRiskTypeSchema();
		sdinformationRiskType.setId(PubFun.GetInformationRiskTypeID(orderSn));
		sdinformationRiskType.setcreateDate(new Date());
		sdinformationRiskType.setmodifyDate(sdinformationRiskType.getcreateDate());
		sdinformationRiskType.setorderSn(orderSn);// 订单号
		sdinformationRiskType.setinformationSn(sdinformation.getinformationSn());// 订单明细编号
		sdinformationRiskType.setrecognizeeSn(insuredSn);// 被保人编号
		sdinformationRiskType.setapplicantSn(applicantSn);// 投保人编号
		sdinformationRiskType.setriskCode(sdinformation.getproductId());// 产品编码
		sdinformationRiskType.setriskName(sdinformation.getproductName());// 产品名称
		sdinformationRiskType.setamnt(aeonAmount);// 保额
		sdinformationRiskType.setmult("1");// 份数 默认为1
		sdinformationRiskType.setsvaliDate(sdinformation.getstartDate());// 生效日期
		sdinformationRiskType.setevaliDate(sdinformation.getendDate());// 失效日期
		sdinformationRiskType.setperiodFlag(sdinformation.getchargeType());// 缴费年期类型
		sdinformationRiskType.setperiod(sdinformation.getchargeYear());// 缴费年期

		sdinformationRiskType.setelectronicPath("");// 电子保单物理路径
		sdinformationRiskType.setinsurerFlag("");
		sdinformationRiskType.setinsureDate(DateUtil.toDateTimeString(policyReqSchema.getpayDateTime()));
		sdinformationRiskType.setcouponValue("0.00");
		sdinformationRiskType.setintegralValue("0.00");
		sdinformationRiskType.setactivityValue("0.00");
		sdinformationRiskType.setpayPrice(aeonAmount);
		// 结算中心 暂时不在保存程序处理 由定时计划执行
		// sdinformationRiskType.setBalanceStatus("0");
		// sdinformationRiskType.setBalanceFlag("1");

		sdinformationRiskType.setsdinformationinsured_id(sdinformationinsured.getId());
		sdinformationRiskType.setsdorder_id(sdorders.getid());
		sdinformationRiskType.setinsureMsg("");
		sdinformationRiskType.setcreateDate(new Date());
		sdinformationRiskType.setmodifyDate(sdinformationRiskType.getcreateDate());
		sdinformationRiskType.settimePrem(aeonAmount);
		sdinformationRiskType.setproductPrice(aeonAmount);
		transaction.add(sdinformationRiskType, Transaction.INSERT);
		/**
		 * 交易汇总信息表
		 */

		TradeSummaryInfoSchema tradeSummaryInfoSchema = new TradeSummaryInfoSchema();
		String tradeSummaryId = NoUtil.getMaxNo("TradeSummaryID", 11);
		tradeSummaryInfoSchema.setid(tradeSummaryId);
		tradeSummaryInfoSchema.setCreateDate(new Date());
		tradeSummaryInfoSchema.setModifyDate(tradeSummaryInfoSchema.getCreateDate());
		tradeSummaryInfoSchema.setPaySn(sdorders.getpaySn());
		tradeSummaryInfoSchema.setTradeSn(policyReqSchema.getfundTransferSn());
		tradeSummaryInfoSchema.setTradeResult("1");
		tradeSummaryInfoSchema.setOrderSns(orderSn);
		tradeSummaryInfoSchema.setPayType("zjzf");
		tradeSummaryInfoSchema.setPayTypeName("直接支付");
		tradeSummaryInfoSchema.setTradeAmount(aeonAmount);
		tradeSummaryInfoSchema.setTotalAmount(aeonAmount);
		transaction.add(tradeSummaryInfoSchema, Transaction.INSERT);

		/**
		 * 交易信息表
		 */
		TradeInformationSchema tradeInformationSchema = new TradeInformationSchema();
		tradeInformationSchema.setid(PubFun.GetTradeInformationID(orderSn));
		tradeInformationSchema.setcreateDate(new Date());
		tradeInformationSchema.setmodifyDate(tradeInformationSchema.getcreateDate());
		tradeInformationSchema.setordAmt(aeonAmount);
		tradeInformationSchema.setordID(orderSn);
		tradeInformationSchema.setpayStatus(String.valueOf(SDOrder.SDOrderStatus.prepay.ordinal()));
		tradeInformationSchema.setpayType("zjzf");
		tradeInformationSchema.settradeSeriNO(policyReqSchema.getfundTransferSn());
		tradeInformationSchema.settradeCheckSeriNo(policyReqSchema.getfundTransferSn());
		tradeInformationSchema.settradeResult("1");
		Date dealComfirmDateTime = policyReqSchema.getdealConfirmDateTime();
		if (dealComfirmDateTime != null) {
			tradeInformationSchema.setreceiveDate(DateUtil.toDateTimeString(dealComfirmDateTime));
		}
		tradeInformationSchema.setsendDate(DateUtil.getCurrentDateTime());
		transaction.add(tradeInformationSchema, Transaction.INSERT);

		/**
		 * 订单项表
		 * 
		 */
		SDOrderItemSchema sdOrderItemSchema = new SDOrderItemSchema();
		sdOrderItemSchema.setid(PubFun.GetOrderItemID(orderSn));
		sdOrderItemSchema.setorderitemSn(PubFun.GetItemNo());
		sdOrderItemSchema.setcreateDate(new Date());
		sdOrderItemSchema.setmodifyDate(sdOrderItemSchema.getcreateDate());
		sdOrderItemSchema.setorderSn(orderSn);
		sdOrderItemSchema.setchannelCode(partnerInfo.getchannelSn());
		sdOrderItemSchema.setorderPoint("0");
		sdOrderItemSchema.setpointStatus("1");
		sdOrderItemSchema.setsdorder_id(sdorders.getid());

		transaction.add(sdOrderItemSchema, Transaction.INSERT);
		transaction.add(policyReqSchema, Transaction.UPDATE);
		transaction.commit();

	}

	/**
	 * getInsureResultFromAeonlife:从保险公司获取投保结果文件. <br/>
	 *
	 * @author dongsheng
	 * @throws IOException
	 */
	protected void getInsureResultFromInsuranceCompany(InsuranceCompany company) throws IOException {

		if (company == null) {
			return;
		}
		String folderPath = getInsuranceCompanyFolderPath(SftpCommon.UPLOAD, new Date(), SftpCommon.STR_POLICYRESULT,
				company);
		List<List<String>> rows = CSVUtils.readCSVFromZip(folderPath);
		if (rows == null) {
			logger.warn("文件没有数据");
			return;
		}
		// 一个一个处理
		for (List<String> row : rows) {
			PartnerPolicyReqSchema req = updatePartnerPolicyReq(row);
			if (req != null) {
				updateProcedure(req);
			}
		}

	}

	public void getInsureResultFromEachInsuranceCompany() throws IOException {

		for (InsuranceCompany company : companies) {

			getInsureResultFromInsuranceCompany(company);

		}
	}

	private PartnerPolicyReqSchema updatePartnerPolicyReq(List<String> row) {

		if (row == null || row.size() == 0) {
			logger.warn("入参不能为空.");
			return null;
		}
		Transaction transaction = new Transaction();
		PartnerPolicyReqSchema schema = new PartnerPolicyReqSchema();
		// e.g 2013000011124151_1
		String str = row.get(0);
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		str = str.split("_")[0];
		QueryBuilder qb = new QueryBuilder("where orderSn=?", str);
		PartnerPolicyReqSet set = schema.query(qb);
		if (set != null && set.size() > 0) {
			schema = set.get(0);
			if (schema.getisInsureSuccess() == null) {

				schema.setpolicyNo(row.get(1));
				schema.setfundType(row.get(2));
				schema.setisInsureSuccess(row.get(3));
				schema.settotalAmount(row.get(4));
				schema.setprincipalAmount(row.get(5));
				schema.setincomeAmount(row.get(6));
				if (StringUtil.isNotEmpty(row.get(7))) {
					schema.setsignDate(DateUtil.parse(row.get(7), DateUtil.Format_Date2));
				}
				if (StringUtil.isNotEmpty(row.get(8))) {
					schema.sethesitationEndDate(DateUtil.parse(row.get(8), DateUtil.Format_Date2));
				}
				schema.setcomment(row.get(9));
				schema.setelectronicPolicyURL(row.get(10));
				schema.setmodifyDatetime(new Date());
				transaction.add(schema, Transaction.UPDATE);
				if (!transaction.commit()) {
					return null;
				}
				return schema;
			}
		}
		return null;
	}

	private void updateProcedure(PartnerPolicyReqSchema schema) {

		FMRisk fmrisk = getFMRisk(schema.getproductId());
		FERiskAppFactor feRiskAppFactor = null;
		if (fmrisk == null) {
			return;
		}
		for (FERiskAppFactor item : fmrisk.getFERiskAppFactor()) {
			if ("Period".equals(item.getFactorType())) {
				feRiskAppFactor = item;
				break;
			}
		}
		FEMRiskFactorList fEMRiskFactorList = feRiskAppFactor.getFEMRiskFactorList()[0];
		String periods = fEMRiskFactorList.getFactorValue();
		String periodA = periods.substring(0, periods.length() - 1);
		String periodB = periods.substring(periods.length() - 1);
		String periodDis = fEMRiskFactorList.getFactorDisplayValue();
		Date endDate = null;
		if (schema.getsignDate() != null) {
			String endDateStr = PubFun.getEvaliDate(schema.getsignDate(), periodA, periodB);
			endDate = DateUtil.parse(endDateStr, DateUtil.Format_DateTime);
		}

		Transaction transaction = new Transaction();
		SDOrdersSchema sdOrder = new SDOrdersSchema();
		String sqlString = "where ordersn=?";
		QueryBuilder qb = new QueryBuilder(sqlString);
		qb.add(schema.getorderSn());
		SDOrdersSet set = sdOrder.query(qb);
		if (set != null && set.size() != 0) {
			sdOrder = set.get(0);
		}
		if ("1".equals(schema.getisInsureSuccess())) {
			sdOrder.setorderStatus(SDOrder.SDOrderStatus.paid.ordinal());
			sdOrder.setpayStatus(SDOrder.SDPayStatus.paid.ordinal());
		} else {
			sdOrder.setorderStatus(SDOrder.SDOrderStatus.prepay.ordinal());
			sdOrder.setpayStatus(SDOrder.SDPayStatus.unpaid.ordinal());
		}

		sdOrder.setmodifyDate(new Date());
		transaction.add(sdOrder, Transaction.UPDATE);

		sqlString = "where ordersn=?";
		qb = new QueryBuilder(sqlString);
		qb.add(schema.getorderSn());
		SDInformationSchema sdInformationSchema = new SDInformationSchema();
		SDInformationSet sdInformationSet = sdInformationSchema.query(qb);
		if (sdInformationSet != null && sdInformationSet.size() != 0) {
			sdInformationSchema = sdInformationSet.get(0);
		}
		// 开始时间
		sdInformationSchema.setstartDate(schema.getsignDate());
		sdInformationSchema.setendDate(endDate);
		sdInformationSchema.setensureLimit(periodA);
		sdInformationSchema.setensureLimitType(periodB);
		sdInformationSchema.setensure(periods);
		sdInformationSchema.setensureDisplay(periodDis);
		sdInformationSchema.setmodifyDate(new Date());
		transaction.add(sdInformationSchema, Transaction.UPDATE);
		// ////
		sqlString = "where ordersn=? and informationSn=?";
		qb = new QueryBuilder(sqlString, sdOrder.getorderSn(), sdInformationSchema.getinformationSn());
		SDInformationRiskTypeSchema sdinformationRiskType = new SDInformationRiskTypeSchema();
		SDInformationRiskTypeSet sdInformationRiskSet = sdinformationRiskType.query(qb);
		if (sdInformationRiskSet != null && sdInformationRiskSet.size() != 0) {
			sdinformationRiskType = sdInformationRiskSet.get(0);
		}
		sdinformationRiskType.setappStatus(schema.getisInsureSuccess());
		sdinformationRiskType.setpolicyNo(schema.getpolicyNo());// 保单号
		sdinformationRiskType.setelectronicCout(schema.getelectronicPolicyURL());// 电子保单保险公司路径
		sdinformationRiskType.setmodifyDate(new Date());
		sdinformationRiskType.setsvaliDate(schema.getsignDate());
		sdinformationRiskType.setevaliDate(endDate);
		sdinformationRiskType.setinsuranceBankSeriNO(schema.getorderSn() + "_1");
		transaction.add(sdinformationRiskType, Transaction.UPDATE);
		// ////
		sqlString = "where ordID=?";
		qb = new QueryBuilder(sqlString, sdOrder.getorderSn());
		TradeInformationSchema tradeInformationSchema = new TradeInformationSchema();
		TradeInformationSet tradeInformationSet = tradeInformationSchema.query(qb);
		if (tradeInformationSet != null && tradeInformationSet.size() != 0) {
			tradeInformationSchema = tradeInformationSet.get(0);
		}
		if ("1".equals(schema.getisInsureSuccess())) {
			tradeInformationSchema.setpayStatus(String.valueOf(SDOrder.SDPayStatus.paid.ordinal()));
			tradeInformationSchema.settradeResult("0");
		} else {
			tradeInformationSchema.setpayStatus(String.valueOf(SDOrder.SDPayStatus.unpaid.ordinal()));
			tradeInformationSchema.settradeResult("1");
		}

		tradeInformationSchema.setmodifyDate(new Date());
		transaction.add(tradeInformationSchema, Transaction.UPDATE);
		// //
		sqlString = "where paySn=?";
		qb = new QueryBuilder(sqlString, sdOrder.getpaySn());
		TradeSummaryInfoSchema tradeSummaryInfoSchema = new TradeSummaryInfoSchema();
		TradeSummaryInfoSet tradeSummaryInfoSet = tradeSummaryInfoSchema.query(qb);
		if (tradeSummaryInfoSet != null && tradeSummaryInfoSet.size() != 0) {
			tradeSummaryInfoSchema = tradeSummaryInfoSet.get(0);
		}
		if ("1".equals(schema.getisInsureSuccess())) {
			tradeSummaryInfoSchema.setTradeResult("0");
		} else {
			tradeSummaryInfoSchema.setTradeResult("1");
		}
		tradeSummaryInfoSchema.setModifyDate(new Date());
		transaction.add(tradeSummaryInfoSchema, Transaction.UPDATE);
		/*** add by dongs .2016年12月5日 09:13:19. ***/
		/*if (StringUtil.isNotEmpty(schema.getpolicyNo())) {
			// 投保成功.才初始化资产同步信息
			String sql = "INSERT INTO financinginfo (PolicyNo, OrderSn,  Total, Principal, Income, InsStatus, Prop2, CreateDate, ModifyDate)"
					+ "VALUES (?,?,?,?,?,?,?,NOW(),NOW())";
			logger.info("资产同步sql语句=========" + sql);
			QueryBuilder dt = new QueryBuilder(sql);
			dt.add(schema.getpolicyNo());
			dt.add(schema.getorderSn());
			dt.add(schema.gettotalAmount());
			dt.add(schema.getprincipalAmount());
			dt.add(schema.getincomeAmount());
			dt.add("1");
			dt.add("0.00");
			transaction.add(dt);
		}*/
		/*** add by dongs .2016年12月5日 09:13:19. ***/
		boolean isCommitSuccess = transaction.commit();

		if (isCommitSuccess && "1".equals(schema.getisInsureSuccess())) {
			try {
				// 发送消息队列到经代通下载电子保单
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderSn", schema.getorderSn());
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

	/**
	 * getInsureResultFromAeonlife:把投保结果发给合作方<br/>
	 *
	 * @author dongsheng
	 * @throws IOException
	 */
	public void putInsureResultToPartner() throws IOException {

		PartnerInfoSet partners = getPartnerInfoSetWithFullFtpPath(SftpCommon.UPLOAD,
				SftpCommon.STR_POLICYRESULT);

		for (int i = 0; i < partners.size(); i++) {
			PartnerInfoSchema partner = partners.get(i);
			if (partner == null || StringUtil.isEmpty(partner.getFtpPath())) {
				continue;
			}
			String partnerIdStr = partner.getpartnerId();
			String pathStr = partner.getFtpPath();
			String sql = "SELECT\n" +
					"	p.paySn,\n" +
					"	p.policyNo,\n" +
					"	p.fundType,\n" +
					"	p.isInsureSuccess,\n" +
					"	p.totalAmount,\n" +
					"	p.principalAmount,\n" +
					"	p.incomeAmount,\n" +
					"	p.signDate,\n" +
					"	p.hesitationEndDate,\n" +
					"	p.`comment`,\n" +
					"	p.electronicPolicyURL,\n" +
					"	s.electronicpath\n" +
					"FROM\n" +
					"	partnerpolicyreq p\n" +
					"LEFT JOIN sdinformationrisktype s ON p.orderSn = s.orderSn\n" +
					"WHERE\n" +
					"	Date(createDatetime) = Date(?)\n" +
					"AND partnerId =?";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(new Date());
			qb.add(partner.getid());
			DataTable dt = qb.executeDataTable();
			List<List<String>> exportData = new ArrayList<List<String>>();
			if (dt != null) {
				for (int j = 0, length = dt.getRowCount(); j < length; j++) {
					List<String> row = new ArrayList<String>();
					DataRow item = dt.getDataRow(j);
					row.add(item.getString("paySn"));
					row.add(item.getString("policyNo"));
					row.add(item.getString("fundType"));
					row.add(item.getString("isInsureSuccess"));
					row.add(item.getString("totalAmount"));
					row.add(item.getString("principalAmount"));
					row.add(item.getString("incomeAmount"));
					Date signDate = item.getDate("signDate");
					if (signDate != null) {
						row.add(DateUtil.toString(signDate, "yyyyMMdd"));
					} else {
						row.add("");
					}
					Date hesitationEndDate = item.getDate("hesitationEndDate");
					if (hesitationEndDate != null) {
						row.add(DateUtil.toString(hesitationEndDate, "yyyyMMdd"));
					} else {
						row.add("");
					}
					row.add(item.getString("comment"));
					String electronicPolicyURL = item.getString("electronicPolicyURL");
					String electronicpath = item.getString("electronicpath");
					String policyUrl = null;
					if (StringUtil.isNotEmpty(electronicpath)) {
						policyUrl = electronicpath.replace(Config.getValue("newPolicyPath"),
								Config.getFrontServerContextPath());
					} else {
						policyUrl = electronicPolicyURL;
					}
					row.add(policyUrl);

					exportData.add(row);
				}
			}
			String fileNameString = getFileName(partnerIdStr, SftpCommon.STR_POLICYRESULT, 1);
			boolean isSuccess = writeCSV(pathStr, fileNameString, exportData);
			logger.info("发送结果到第三方:{}", isSuccess);
		}
	}

	/**
	 * putInsureApplyToInsuranceCompany(根据保险公司售卖的产品id,前缀.将对应的投保申请发送给该保险公司.)<br/>
	 *
	 * @author dongsheng
	 * @throws IOException
	 */
	protected void putInsureApplyToInsuranceCompany(InsuranceCompany company) throws IOException {

		if (company == null) {
			return;
		}
		String productPrefix = company.getProductPrefix();
		String sql = "where Date(createDatetime) = Date(?) and isDataCorrect='Y' and isInsureSuccess is null and productId like '"
				+ productPrefix + "%'";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(new Date());

		PartnerPolicyReqSchema schema = new PartnerPolicyReqSchema();
		PartnerPolicyReqSet set = schema.query(qb);

		List<List<String>> exportData = new ArrayList<List<String>>();
		if (set != null) {

			for (int i = 0; i < set.size(); i++) {
				List<String> row = new ArrayList<String>();
				PartnerPolicyReqSchema item = set.get(i);
				row.add(item.getorderSn() + "_1");
				row.add(item.getproductOutId());
				row.add(item.getaccountNo());
				row.add(item.getaccountName());
				row.add(item.getidCardNo());
				row.add(item.getpolicyholderMobile());
				row.add(item.getpolicyholderEmail());
				row.add(item.getpolicyholderAddress());
				row.add(item.getpayAmount());
				row.add(item.getexpiredType());
				row.add(DateUtil.toString(item.getpayDateTime(), "yyyyMMdd HH:mm:ss"));
				row.add(DateUtil.toString(item.getdealConfirmDateTime(), "yyyyMMdd HH:mm:ss"));
				row.add(item.getfundTransferSn());
				exportData.add(row);
			}
		}

		String pathStr = getInsuranceCompanyFolderPath(SftpCommon.DOWNLOAD, SftpCommon.STR_POLICYREQ, company);
		boolean isSuccess = writeCSV(pathStr, company.getName(), SftpCommon.STR_POLICYREQ, exportData, LIMIT);
		logger.info("发送申请到{}", company.getName() + isSuccess);
	}

	/**
	 * putInsureApplyToEachInsuranceCompany:(把对应的投保申请发给各个保险公司 ). <br/>
	 *
	 * @author dongsheng
	 * @throws IOException
	 */
	public void putInsureApplyToEachInsuranceCompany() throws IOException {

		for (InsuranceCompany insuranceCompany : companies) {
			putInsureApplyToInsuranceCompany(insuranceCompany);
		}

	}

	public void putFinancingToPartner() throws IOException {

		PartnerInfoSet partners = getPartnerInfoSetWithFullFtpPath(SftpCommon.UPLOAD,
				SftpCommon.STR_POLICYVALUE);

		for (int i = 0; i < partners.size(); i++) {
			PartnerInfoSchema partner = partners.get(i);
			if (partner == null || StringUtil.isEmpty(partner.getFtpPath())) {
				continue;
			}
			String partnerIdStr = partner.getpartnerId();
			String pathStr = partner.getFtpPath();
			String sql = "select f.*,s.paysn,p.partnerid,partnername,ftppath "
					+ "from financinginfo f,sdorders s,partnerinfo p "
					+ "where f.ordersn = s.ordersn and s.channelsn = p.channelsn and p.type = 'asyn' "
					+ "and (insstatus in('1','2') or f.CancelResult = '2') and Date(f.modifydate)=Date(?) and  p.id=?";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(new Date());
			qb.add(partner.getid());
			DataTable dt = qb.executeDataTable();
			List<List<String>> exportData = new ArrayList<List<String>>();
			if (dt != null) {
				for (int j = 0; j < dt.getRowCount(); j++) {
					List<String> row = new ArrayList<String>();
					row.add(dt.getString(j, "paysn"));
					row.add(dt.getString(j, "policyno"));
					row.add("1");
					// 线下理赔情况
					if ("2".equals(dt.getString(j, "CancelResult"))) {
						row.add("2");
					} else {
						row.add("1");
					}
					row.add(dt.getString(j, "total"));
					row.add(dt.getString(j, "principal"));
					row.add(dt.getString(j, "income"));
					// 当日收益.
					row.add(dt.getString(j, "prop2"));
					row.add(DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyyMMdd"));
					row.add(dt.getString(j, "CancelMsg"));
					exportData.add(row);
				}
			}
			String fileName = getFileName(partnerIdStr, SftpCommon.STR_POLICYVALUE, 1);
			boolean isSuccess = writeCSV(pathStr, fileName, exportData);
			logger.info("发送资产同步到第三方:{}", isSuccess);

		}

	}

	private PartnerPolicyReqSchema checkDataLegal(PartnerPolicyReqSchema partnerPolicyReqSchema) {

		if (partnerPolicyReqSchema == null) {
			logger.warn("数据为空");
			return null;
		}

		QueryBuilder qb = new QueryBuilder("where paySn like ?", partnerPolicyReqSchema.getpaySn());
		PartnerPolicyReqSet set = partnerPolicyReqSchema.query(qb);
		if (set != null && set.size() > 0) {
			logger.warn("源文件投保订单号重复,或定时任务重复执行:{},数据直接忽略.", partnerPolicyReqSchema.getpaySn());
			// partnerPolicyReqSchema.setcomment("投保订单号不唯一,已经存在一个相同的投保订单号.");
			// partnerPolicyReqSchema.setisDataCorrect("N");
			// partnerPolicyReqSchema.setisInsureSuccess("0");
			// return partnerPolicyReqSchema;
			return null;
		}

		if (StringUtil.isEmpty(partnerPolicyReqSchema.getproductId())
				|| StringUtil.isEmpty(partnerPolicyReqSchema.getaccountNo())
				|| StringUtil.isEmpty(partnerPolicyReqSchema.getaccountName())
				|| StringUtil.isEmpty(partnerPolicyReqSchema.getpayAmount())
				|| StringUtil.isEmpty(partnerPolicyReqSchema.getexpiredType())
				|| StringUtil.isEmpty(partnerPolicyReqSchema.getpayDateTime())
				|| StringUtil.isEmpty(partnerPolicyReqSchema.getdealConfirmDateTime())
				|| StringUtil.isEmpty(partnerPolicyReqSchema.getfundTransferSn())
				|| StringUtil.isEmpty(partnerPolicyReqSchema.getidCardNo())) {
			logger.warn("数据项目不全面.");
			partnerPolicyReqSchema.setcomment("数据项目不全面.必填项不能为空");
			partnerPolicyReqSchema.setisDataCorrect("N");
			partnerPolicyReqSchema.setisInsureSuccess("0");
			return partnerPolicyReqSchema;
		}

		String idCard = partnerPolicyReqSchema.getidCardNo();
		if (!IdcardUtils.validateCard(idCard)) {
			logger.warn("身份证不合法");
			partnerPolicyReqSchema.setcomment("身份证不合法");
			partnerPolicyReqSchema.setisDataCorrect("N");
			partnerPolicyReqSchema.setisInsureSuccess("0");
			return partnerPolicyReqSchema;
		}
		String birthDayStr = IdcardUtils.getBirthByIdCard(idCard);
		Date birthDay = DateUtil.parse(birthDayStr);
		if (birthDay.compareTo(DateUtil.addYear(new Date(), -18)) > 0) {
			// 年龄小于18岁
			logger.warn("年龄小于18");
			partnerPolicyReqSchema.setcomment("年龄小于18不能购买");
			partnerPolicyReqSchema.setisDataCorrect("N");
			partnerPolicyReqSchema.setisInsureSuccess("0");
			return partnerPolicyReqSchema;
		}
		if (StringUtil.isNotEmpty(partnerPolicyReqSchema.getpolicyholderEmail())
				&& !StringUtil.isMail(partnerPolicyReqSchema.getpolicyholderEmail())) {
			logger.warn("email格式有误");
			partnerPolicyReqSchema.setcomment("email格式有误");
			partnerPolicyReqSchema.setisDataCorrect("N");
			partnerPolicyReqSchema.setisInsureSuccess("0");
			return partnerPolicyReqSchema;
		}
		if (StringUtil.isNotEmpty(partnerPolicyReqSchema.getpolicyholderMobile())
				&& !StringUtil.isMobileNO(partnerPolicyReqSchema.getpolicyholderMobile())) {
			logger.warn("手机号格式有误");
			partnerPolicyReqSchema.setcomment("手机号格式有误");
			partnerPolicyReqSchema.setisDataCorrect("N");
			partnerPolicyReqSchema.setisInsureSuccess("0");
			return partnerPolicyReqSchema;
		}
		String productId = partnerPolicyReqSchema.getproductId();
		FMRisk fmrisk = getFMRisk(productId);
		if (fmrisk == null) {
			logger.warn("产品id有误");
			partnerPolicyReqSchema.setcomment("产品id有误,找不到对应的产品");
			partnerPolicyReqSchema.setisDataCorrect("N");
			partnerPolicyReqSchema.setisInsureSuccess("0");
			return partnerPolicyReqSchema;
		}
		if (!"Y".equals(fmrisk.getIsPublish())) {
			logger.warn("产品已下架");
			partnerPolicyReqSchema.setcomment("产品已下架,无法购买");
			partnerPolicyReqSchema.setisDataCorrect("N");
			partnerPolicyReqSchema.setisInsureSuccess("0");
			return partnerPolicyReqSchema;
		}

		if (productId.startsWith("1095")) {
			// 前海保险公司产品特殊处理
			String str = partnerPolicyReqSchema.getaccountNo();
			String[] strs = str.split(SPLIT_STRING);
			if (strs.length != 2) {
				logger.warn("支付账号没有传入支付银行编号.");
				partnerPolicyReqSchema.setcomment("支付账号没有传入支付银行编号.");
				partnerPolicyReqSchema.setisDataCorrect("N");
				partnerPolicyReqSchema.setisInsureSuccess("0");
				return partnerPolicyReqSchema;
			} else {
				String bankCode = strs[0];
				String accountNo = strs[1];
				if (StringUtil.isEmpty(bankCode) || StringUtil.isEmpty(accountNo)) {
					logger.warn("支付银行或者支付账号都不能为空.");
					partnerPolicyReqSchema.setcomment("支付银行或者支付账号都不能为空.");
					partnerPolicyReqSchema.setisDataCorrect("N");
					partnerPolicyReqSchema.setisInsureSuccess("0");
					return partnerPolicyReqSchema;
				}
				// TODO 后面添加前海的银行编号的校验.

			}
		}

		Date date1, date2;
		// Date date3;
		// String str2, str3;
		date1 = partnerPolicyReqSchema.getpayDateTime();
		date2 = partnerPolicyReqSchema.getdealConfirmDateTime();
		// date3 = DateUtil.addDay(new Date(), -1);
		// str2 = DateUtil.toString(date2);
		// str3 = DateUtil.toString(date3);
		if (!date1.equals(date2)) {
			logger.warn("数据错误,支付时间和支付确认时间不符");
			partnerPolicyReqSchema.setcomment("数据错误,支付时间和支付确认时间不符");
			partnerPolicyReqSchema.setisDataCorrect("N");
			partnerPolicyReqSchema.setisInsureSuccess("0");
			return partnerPolicyReqSchema;
		}
		// if (!str3.equals(str2)) {
		// logger.error("数据错误,支付确认时间,必须是当前日期的前一天");
		// partnerPolicyReqSchema.setcomment("数据错误,支付确认时间,必须是当前日期的前一天");
		// partnerPolicyReqSchema.setisDataCorrect("N");
		// partnerPolicyReqSchema.setisInsureSuccess("0");
		// return partnerPolicyReqSchema;
		// }

		partnerPolicyReqSchema.setcomment("");
		partnerPolicyReqSchema.setisDataCorrect("Y");
		return partnerPolicyReqSchema;
	}

	/**
	 * 
	 * getFMRisk:(从产品库获得FMRisk). <br/>
	 **/
	private FMRisk getFMRisk(String productId) {

		if (StringUtil.isEmpty(productId)) {
			return null;
		}
		FMRisk[] fmrisk = null;
		String[] products = new String[] { productId };
		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter.put("RiskCode", products);
		try {
			ProductInfoWebServiceStub.ProductInfoResponse prifr = ProductWebservice.ProductInfoSereviceImpl(paramter, null);
			fmrisk = prifr.getFMRisk();
		} catch (Exception e) {
			logger.error("获取产品中心端产品数据失败！" + productId + e.getMessage(), e);
		}
		if (fmrisk != null && fmrisk.length > 0) {
			return fmrisk[0];
		} else {
			return null;
		}
	}

	/**
	 * getProduct:在数据库产品表根据产品id获取产品信息.(同步数据,信息不如产品库全面) <br/>
	 *
	 * @author dongsheng
	 * @param productId
	 * @return
	 */
	@Deprecated
	protected Map<String, Object> getProduct(String productId) {

		if (StringUtil.isEmpty(productId)) {
			logger.warn("入参产品id不能为空");
			return null;
		}

		String sql = "select * from sdproduct where ProductID=?";

		QueryBuilder qb = new QueryBuilder(sql, productId);

		DataTable dt = qb.executeDataTable();
		if (dt == null || dt.getRowCount() != 1) {
			logger.warn("没有找到对应的商品");
			return null;
		}
		Map<String, Object> productMap = new LinkedHashMap<String, Object>();
		for (int i = 0; i < dt.getColCount(); i++) {
			productMap.put(dt.getColumnName(i), dt.get(0, i));
		}
		// System.out.println(productMap);
		return productMap;
	}

	public static void main(String[] args) throws IOException {

		InsureApplyDocumentService service = new InsureApplyDocumentService();
		service.getInsureApplyFromPartner();
		// service.putInsureApplyToEachInsuranceCompany();

	}

	private boolean writeCSV(String pathStr, String partnerName, String strType, List<List<String>> dataAll, int splitLimit) {

		if (StringUtil.isEmpty(partnerName) || StringUtil.isEmpty(partnerName) || StringUtil.isEmpty(strType)) {
			return false;
		}
		boolean isSuccess = false;
		int fileIndex = 1;
		if (dataAll == null || dataAll.size() == 0) {
			String fileNameStr = getFileName(partnerName, strType, fileIndex);
			isSuccess = writeCSV(pathStr, fileNameStr, dataAll);

		} else {
			List<List<String>> exportData = new ArrayList<List<String>>();
			for (int i = 0; i < dataAll.size(); i++) {
				exportData.add(dataAll.get(i));
				if (exportData.size() >= splitLimit || i == dataAll.size() - 1) {
					String fileNameStr = getFileName(partnerName, strType, fileIndex++);
					isSuccess = writeCSV(pathStr, fileNameStr, exportData);
					exportData.clear();
					if (!isSuccess) {
						break;
					}
				}
			}
		}
		return isSuccess;
	}

	private boolean writeCSV(String pathStr, String fileNameStr, List<List<String>> data) {

		boolean isSuccess = false;
		if (StringUtil.isEmpty(pathStr) || StringUtil.isEmpty(fileNameStr)) {
			return false;
		}
		// System.out.println(pathStr + fileNameStr);
		try {
			CSVUtils.writeCSV(data, pathStr + fileNameStr + ".csv", "utf-8");
			ZipUtil.zip(pathStr + fileNameStr + ".csv", pathStr + fileNameStr + ".zip");
			FileUtil.delete(pathStr + fileNameStr + ".csv");
			isSuccess = true;
		} catch (Exception e) {
			logger.error(pathStr + fileNameStr + ".csv压缩失败" +e.getMessage(), e);
			isSuccess = false;
		}
		return isSuccess;
	}

	private boolean isLegalDate(String date, String pattern) {

		try {
			DateFormat dateFormat;
			dateFormat = new SimpleDateFormat(pattern);

			dateFormat.setLenient(false);
			dateFormat.parse(date);
			return true;
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return false;
		}

	}

	private boolean isLegalDate(String date) {

		return isLegalDate(date, "yyyyMMdd HH:mm:ss");
	}
}
