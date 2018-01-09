package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.entity.SDOrder.SDPayStatus;
import cn.com.sinosoft.entity.SDOrderItem;
import cn.com.sinosoft.entity.SDOrderItemOth;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.OrderFreeService;
import cn.com.sinosoft.service.SDOrderService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.IdcardUtils;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class OrderFreeServiceImpl implements OrderFreeService{
	private static final Logger logger = LoggerFactory.getLogger(OrderFreeServiceImpl.class);
	@Resource 
	private DictionaryService dictionaryService; 
	@Resource
	private AreaService areaService; 
	@Resource
	private SDOrderService sdorderService; 
	public Properties props = new Properties();
	@Override
	//限购校验
	public boolean checkCardID(String cProductID,String cardID) {
		QueryBuilder qb = new QueryBuilder("SELECT K_ValidateProductlimit(?,?,?,?,?,?,?) FROM DUAL");
		//QueryBuilder qb = new QueryBuilder("SELECT K_ValidateProductlimit('?ProductId?','?InsIDType?','?InsIDNo?','?EffectiveDate?','?FailDate?','?BuyCount?','?ComRiskCode?') FROM DUAL");
		qb.add(cProductID);
		qb.add(String.valueOf(props.get("IDType")));//身份证
		qb.add(cardID);
		qb.add(Config.getValue("FreeInsuredDate"));
		qb.add(Config.getValue("FreeEndDate"));
		qb.add("1");
		qb.add(String.valueOf(props.get("OutProductID")));
		String tFlag = qb.executeString();
		if("Y".equals(tFlag)){
			return true;
		}
		return false;
	}
	/**
	 * 获取订单信息
	 * 
	 * @param oPolicyInfo
	 * @param paramter
	 * @return
	 */
	public SDOrder getSDOrder(SDOrder sdorder,String[] BaseInformation,String cFrom) {
		BigDecimal tProductTotalPrice = new BigDecimal(
				String.valueOf(BaseInformation[11]));
		sdorder.setProductTotalPrice(tProductTotalPrice);// 商品总价打折前
		sdorder.setTotalAmount(tProductTotalPrice);// 保费即打折后商品价格
		sdorder.setDiscountRates(BaseInformation[10].toString());// 订单折扣费率
		sdorder.setChannelsn(cFrom);
		sdorder.setPayPrice("0");//暂存活动打折后金额
		sdorder.setOrderStatus(SDOrderStatus.prepay);
		sdorder.setPayStatus(SDPayStatus.unpaid);
		sdorder.setProductNum(1);
		sdorder.setPayType("zerozf");
		sdorder.setModifyDate(new Date());
		sdorder.setActivitySn(String.valueOf(props.get("ActivitySn")));//?
		sdorder.setOrderActivity(String.valueOf(props.get("OrderActivity")));//优惠金额
		sdorder.setDiscountAmount(String.valueOf(props.get("OrderActivity")));//优惠金额
		
		return sdorder;

	}
	/**
	 * 获取订单明细信息
	 * 
	 * @param oPolicyInfo
	 * @param paramter
	 * @return
	 */
	public SDInformation getSDInformation(SDInformation sdinformation,String[] BaseInformation) {
		
		sdinformation.setProductPrice(String.valueOf(BaseInformation[6]));// 商品原价
		sdinformation.setProductDiscountPrice(String.valueOf(BaseInformation[11]));// 产品折扣价
		sdinformation.setInsuranceCompany(String.valueOf(BaseInformation[2]));
		sdinformation.setProductId(String.valueOf(BaseInformation[0]));
		sdinformation.setProductName(BaseInformation[1]);
		sdinformation.setProductQuantity("1");
		SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startdate = sdf_2.parse(Config.getValue("FreeInsuredDate"));
			Date enddate = sdf_2.parse(Config.getValue("FreeEndDate"));
			sdinformation.setStartDate(startdate);
			sdinformation.setEndDate(enddate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		sdinformation.setEnsureLimit(String.valueOf(props.get("EnsureLimit")));
		sdinformation.setEnsureLimitType(String.valueOf(props.get("EnsureLimitType")));
		sdinformation.setEnsure(String.valueOf(props.get("Ensure")));
		sdinformation.setEnsureDisplay(String.valueOf(props.get("EnsureDisplay")));
		sdinformation.setPlanCode(String.valueOf(props.get("PlanCode")));
		sdinformation.setPlanName(String.valueOf(props.get("PlanName")));
		sdinformation.setProductOutCode(BaseInformation[4].toString());// 产品外部编码
		sdinformation.setDiscountRates(BaseInformation[10].toString());// 产品折扣率
		sdinformation.setSupKindCode(BaseInformation[12].toString());
		sdinformation.setSupRiskCode(BaseInformation[13].toString());
		sdinformation.setRiskType(BaseInformation[5].toString());// 内部统计险种中类别
		sdinformation.setPrimitiveProductTitle(String.valueOf(BaseInformation[16]));//保险公司原名称
		return sdinformation;

	}
	/**
	 * 获取投保人信息
	 * 
	 * @param tHolderInfo
	 * @param paramter
	 * @return
	 */
	public SDInformationAppnt getSDInformationAppnt(SDInformationAppnt sdInformationAppnt) {
		String tApplicantSn = PubFun.GetWapSn("WPAppntSn");
		sdInformationAppnt.setApplicantSn(tApplicantSn);
		sdInformationAppnt.setApplicantIdentityType(String.valueOf(props.get("IDType")));
		sdInformationAppnt.setApplicantIdentityTypeName("身份证");
		 
		sdInformationAppnt.setApplicantBirthday(String.valueOf(IdcardUtils.getBirthByIdCard(sdInformationAppnt.getApplicantIdentityId())));
		int appAge = com.sinosoft.sms.messageinterface.pubfun.PubFun
				.getAge(sdInformationAppnt.getApplicantBirthday());// 获取投保人年龄
		sdInformationAppnt.setApplicantAge(String.valueOf(appAge));// 投保人年龄
		sdInformationAppnt.setApplicantSexName(String.valueOf(IdcardUtils.getGenderByIdCard(sdInformationAppnt.getApplicantIdentityId())));
		sdInformationAppnt.setApplicantSex(String.valueOf(dictionaryService.getCodeValueByCodeName(String.valueOf(props.get("ComCode")), "Sex", sdInformationAppnt.getApplicantSexName())));

		return sdInformationAppnt;
	}
	/**
	 * 获取订单项1信息
	 * 
	 * @param sdinformation
	 * @param sdorder
	 * @return
	 */
	public SDOrderItem getSDOrderItem(SDOrderItem tSDOrderItem,SDInformation sdinformation,
			SDOrder sdorder) {
		tSDOrderItem.setOrderSn(sdorder.getOrderSn());
		tSDOrderItem.setSdorder(sdorder);
		tSDOrderItem.setOrderPoint("0");
		tSDOrderItem.setPointStatus("1");
		tSDOrderItem.setTypeFlag("02");
		return tSDOrderItem;
	}
	/**
	 * 获取订单项2信息
	 * 
	 * @param sdinformation
	 * @param sdorder
	 * @return
	 */
	public SDOrderItemOth getSDOrderItemOth(SDOrderItemOth tSDOrderItem,SDInformationInsured sdInformationInsured,
			SDOrder sdorder) {
		tSDOrderItem.setOrderSn(sdorder.getOrderSn());
		tSDOrderItem.setRecognizeeSn(sdInformationInsured
				.getRecognizeeSn());
		tSDOrderItem.setOrderitemSn(PubFun.GetWapSn("WPItemOthSn"));
		tSDOrderItem.setTpySn("");// 存入华泰特殊编号
		tSDOrderItem.setSdinformationinsured(sdInformationInsured);
		return tSDOrderItem;
	}
	/**
	 * 获取被保人信息
	 * 
	 * @param tInsuredInfos
	 * @param paramter
	 * @return
	 */
	public List<SDInformationInsured> getSDInformationInsured(SDInformationInsured sdInformationInsured,
			SDOrder order,SDInformationAppnt sdInformationAppnt) {
		
		List<SDInformationInsured> Insuredlist = new ArrayList<SDInformationInsured>();

		sdInformationInsured.setRecognizeeAppntRelation(String.valueOf(props.get("Relation")));
		sdInformationInsured.setRecognizeeAppntRelationName("本人");
		sdInformationInsured.setRecognizeeName(String
				.valueOf(sdInformationAppnt.getApplicantName()));
		sdInformationInsured
		.setRecognizeeIdentityType(String.valueOf(sdInformationAppnt.getApplicantIdentityType()));
		sdInformationInsured.setRecognizeeIdentityTypeName(String
				.valueOf(sdInformationAppnt.getApplicantIdentityTypeName()));
		sdInformationInsured.setRecognizeeIdentityId(String
				.valueOf(sdInformationAppnt.getApplicantIdentityId()));
		sdInformationInsured.setRecognizeeSex(String
				.valueOf(sdInformationAppnt.getApplicantSex()));
		sdInformationInsured.setRecognizeeSexName(String
				.valueOf(sdInformationAppnt.getApplicantSexName()));
		sdInformationInsured.setRecognizeeBirthday(String
				.valueOf(sdInformationAppnt.getApplicantBirthday()));
		sdInformationInsured.setRecognizeeAge(sdInformationAppnt.getApplicantAge());
		sdInformationInsured.setRecognizeeMail(String
				.valueOf(sdInformationAppnt.getApplicantMail()));
		sdInformationInsured.setRecognizeeMobile(String
				.valueOf(sdInformationAppnt.getApplicantMobile()));
		
		sdInformationInsured.setRecognizeeMul("1");
		sdInformationInsured.setIsSelf("Y");
		sdInformationInsured.setRecognizeePrem(String.valueOf(order.getTotalAmount()));
		sdInformationInsured.setDiscountPrice(String.valueOf(order.getPayPrice()));//活动折后价
		sdInformationInsured.setRecognizeeTotalPrem(String.valueOf(order.getProductTotalPrice()));
		
		Insuredlist.add(sdInformationInsured);
	

		return Insuredlist;
	}
	/**
	 * 处理交易信息
	* @Title: dealTradeInfo 
	* @return void    返回类型 
	 */
	public TradeInformation dealTradeInfo(SDOrder order,TradeInformation tradeInformation){
		
		tradeInformation.setTradeSeriNO(order.getPaySn());
		tradeInformation.setTradeCheckSeriNo(order.getPaySn());
		tradeInformation.setPayType("zerozf");
		tradeInformation.setPayStatus("1");
		tradeInformation.setOrdAmt(String.valueOf(order.getTotalAmount()));
		tradeInformation.setSendDate(PubFun.getCurrent());
		tradeInformation.setTradeResult("0");
		tradeInformation.setReceiveDate(PubFun.getCurrent());
		tradeInformation.setModifyDate(new Date());

		return tradeInformation;
		
	}
	/**
	 * 处理交易信息
	* @Title: dealTrade
	* @return void    返回类型 
	 */
	public boolean dealTrade(SDOrder order){
		
		String DiscActiveNumber = order.getActivitySn(); // 活动编码
		String ActiveAmount = order.getTotalAmount().toString();
		String OrderNumber = order.getOrderSn();
		String PayAmount = "0";
		String TradeSeriNo = order.getPaySn();
		String PayType = "zerozf";
		String cTotalAmount = order.getTotalAmount().toString();
		
		Transaction trans = new Transaction();
		try{
			QueryBuilder tradesummaryinfocountQB = new QueryBuilder(" SELECT COUNT(1) FROM tradesummaryinfo WHERE PaySn=? ");
			tradesummaryinfocountQB.add(TradeSeriNo);
			int tradesummaryinfocount = tradesummaryinfocountQB.executeInt();
			if(tradesummaryinfocount<=0){
				QueryBuilder inserttradesummaryinfoQB = new QueryBuilder(" INSERT INTO tradesummaryinfo VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
				inserttradesummaryinfoQB.add(NoUtil.getMaxNo("TradeSummaryID",11));
				inserttradesummaryinfoQB.add(TradeSeriNo);
				inserttradesummaryinfoQB.add(TradeSeriNo);
				inserttradesummaryinfoQB.add("0");
				inserttradesummaryinfoQB.add(OrderNumber);
				inserttradesummaryinfoQB.add("");
				if(StringUtil.isNotEmpty(ActiveAmount)){
					inserttradesummaryinfoQB.add(ActiveAmount);
				}else{
					inserttradesummaryinfoQB.add("");
				}
				inserttradesummaryinfoQB.add("");
				inserttradesummaryinfoQB.add("");
				inserttradesummaryinfoQB.add(PayType);
				QueryBuilder paytypeqb = new QueryBuilder(" SELECT description FROM paybase WHERE payType=? LIMIT 1 ");
				paytypeqb.add(PayType);
				inserttradesummaryinfoQB.add(paytypeqb.executeString());
				inserttradesummaryinfoQB.add(cTotalAmount);
				inserttradesummaryinfoQB.add(PayAmount);
				inserttradesummaryinfoQB.add(PubFun.getCurrent());
				inserttradesummaryinfoQB.add(PubFun.getCurrent());
				
				trans.add(inserttradesummaryinfoQB);
			}else{
				QueryBuilder upadtetradesummaryinfoQB = new QueryBuilder(" UPDATE tradesummaryinfo SET TradeResult=?,OrderSns=?,CouponSumAmount=?,ActivitySumAmount=?,PointSumAmount=?,PayType=?,TotalAmount=?,TradeAmount=?,ModifyDate=?,CouponSn=? WHERE paysn=? ");
				upadtetradesummaryinfoQB.add("0");
				upadtetradesummaryinfoQB.add(OrderNumber);
				upadtetradesummaryinfoQB.add("");
				if(StringUtil.isNotEmpty(ActiveAmount)){
					upadtetradesummaryinfoQB.add(ActiveAmount);
				}else{
					upadtetradesummaryinfoQB.add("");
				}
				upadtetradesummaryinfoQB.add("");
				upadtetradesummaryinfoQB.add(PayType);
				upadtetradesummaryinfoQB.add(cTotalAmount);
				upadtetradesummaryinfoQB.add(PayAmount);
				upadtetradesummaryinfoQB.add(PubFun.getCurrent());
				upadtetradesummaryinfoQB.add("");
				upadtetradesummaryinfoQB.add(TradeSeriNo);
				
				trans.add(upadtetradesummaryinfoQB);
			}
			QueryBuilder tradeinfocountQB = new QueryBuilder(" SELECT COUNT(1) FROM tradeinfo WHERE PaySn=? ");
			tradeinfocountQB.add(TradeSeriNo);
			int tradeinfocount = tradeinfocountQB.executeInt();
			if(tradeinfocount<=0){
				QueryBuilder inserttradeinfoQB = new QueryBuilder(" INSERT INTO tradeinfo VALUES(?,?,?,?,?,?,?,?,?,?) ");
				inserttradeinfoQB.add(TradeSeriNo);
				inserttradeinfoQB.add(OrderNumber);
				inserttradeinfoQB.add("");
				inserttradeinfoQB.add(cTotalAmount);
				inserttradeinfoQB.add("");
				inserttradeinfoQB.add(PayType);
				inserttradeinfoQB.add("已支付");
				if(StringUtil.isNotEmpty(DiscActiveNumber)){
					inserttradeinfoQB.add(DiscActiveNumber);
				}else{
					inserttradeinfoQB.add("");
				}
				inserttradeinfoQB.add(PubFun.getCurrent());
				inserttradeinfoQB.add(PubFun.getCurrent());
				
				trans.add(inserttradeinfoQB);
			}else{
				QueryBuilder upadtetradeinfoQB = new QueryBuilder(" UPDATE tradeinfo SET couponSn=?,totalAmnout=?,integral=?,payType=?,modifyDate=? WHERE paySn=? ");
				upadtetradeinfoQB.add("");
				upadtetradeinfoQB.add(cTotalAmount);
				upadtetradeinfoQB.add("");
				upadtetradeinfoQB.add(PayType);
				upadtetradeinfoQB.add(PubFun.getCurrent());
				upadtetradeinfoQB.add(TradeSeriNo);
				
				trans.add(upadtetradeinfoQB);
			}
			if(!trans.commit()){
				logger.error("类（OrderFreeServiceImpl.dealTrade）主站春节赠险活动，平台生成交易流水数据失败，trans:{}", trans.getExceptionMessage());
				return false;
			}
		}catch(Exception e){
			logger.error("类（OrderFreeServiceImpl.dealTradeInfo）主站春节赠险活动，生成交易流水数据失败，eMsg:"+e.getMessage(), e);
			return false;
		}
		return true;
		
	}
	@Override
	public void setProp(Properties cprops) {
		this.props = cprops;
	}
}
