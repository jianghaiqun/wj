package com.sinosoft.inter;

/**
 * 活动保费失算，根据折扣或者优惠计算。
 */

import cn.com.sinosoft.action.shop.MemberCenterAction;
import cn.com.sinosoft.entity.SDOrder;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActivityCalculate {
	private static final Logger logger = LoggerFactory.getLogger(ActivityCalculate.class);

	/**
	 * 单个产品计算活动保费-折扣<br/>
	 * 
	 * <b>@param ProductID 产品信息 </b><br/>
	 * 
	 * <b>@param Amount 原始保费</b><br/>
	 * 
	 * <b>@param Channel 渠道 配置表内进行配置</b><br/>
	 * 
	 * @return 单个产品保费
	 */
	public static String ProductCalculate(String ProductID,String ordersn,String Amount,
			String Channel, String memberId) {
		if (StringUtil.isEmpty(ProductID)) {
			return null;
		}

		if (StringUtil.isEmpty(Channel)) {
			return null;
		}

		ActivityCalculateDetail acd = new ActivityCalculateDetail();

		try {
			List<Map<String, String>> ProductInfo = new ArrayList<Map<String, String>>();
			Map<String, String> param = new HashMap<String, String>();
			param.put("ProductID", ProductID);
			param.put("Amount", Amount);
			param.put("ordersn", ordersn);
			ProductInfo.add(param);

			List<Map<String, String>> result = acd.ProductDiscountCalculate(
					ProductInfo, Channel, memberId);
			if (result == null || result.size() != 1) {
				logger.error("ActivityCalculate.ProductCalculate 单个产品计算活动保费异常返回原始保费.ProductID->"
						+ ProductID
						+ "  Amount->"
						+ Amount
						+ "  Channel->"
						+ Channel
						+ " memberId->"
						+ memberId);
				return Amount;
			}

			return result.get(0).get("Amount");
		} catch (Exception e) {
			logger.error("ActivityCalculate.ProductCalculate 单个产品计算活动保费异常.ProductID->"
					+ ProductID
					+ "  Amount->"
					+ Amount
					+ "  Channel->"
					+ Channel
					+ " memberId->"
					+ memberId + e.getMessage(), e);
			return Amount;

		}
	}

	/**
	 * 多个产品计算活动保费-折扣<br/>
	 * 
	 * <b>@param ProductInfo 产品信息 :ProductID、Amount</b><br/>
	 * 
	 * <b>@param Channel 渠道 配置表内进行配置</b><br/>
	 * 
	 * <b>@return 多个产品保费[{Amount=50.00, ProductID=101001001}, ...]</b>
	 */
	public static List<Map<String, String>> ProductCalculate(
			List<Map<String, String>> ProductInfo, String Channel, String memberId) {
		if (ProductInfo == null || ProductInfo.isEmpty()) {
			return null;
		}

		if (StringUtil.isEmpty(Channel)) {
			return null;
		}

		ActivityCalculateDetail acd = new ActivityCalculateDetail();
		try {

			List<Map<String, String>> result = acd.ProductDiscountCalculate(
					ProductInfo, Channel, memberId);
			if (result == null || result.size() != ProductInfo.size()) {

				logger.error("ActivityCalculate.ProductCalculate 多个产品计算活动保费异常,返回原始保费.Channel->{}", Channel);
				for (int i = 0; i < ProductInfo.size(); i++) {
					Map<String, String> ProductInfoMap = ProductInfo.get(i);
					logger.info("产品信息==>{} 保费==>{}", ProductInfoMap.get("ProductID"), ProductInfoMap.get("Amount"));
				}

				return ProductInfo;
			}

			return result;
		} catch (Exception e) {
			logger.error("ActivityCalculate.ProductCalculate 多个产品计算活动保费异常.Channel->"
					+ Channel + e.getMessage(), e);
			for (int i = 0; i < ProductInfo.size(); i++) {
				Map<String, String> ProductInfoMap = ProductInfo.get(i);
				logger.info("产品信息==>{} 保费==>{}", ProductInfoMap.get("ProductID"), ProductInfoMap.get("Amount"));
			}
			return ProductInfo;

		}
	}

	/**
	 * 产品计算活动保费-折扣和积分<br/>
	 * 
	 * <b>@param ProductInfo 产品信息 :{ProductID=Amount}</b><br/>
	 * 
	 * <b>@param Channel 渠道 配置表内进行配置</b><br/>
	 * 
	 * <b>@return 多个产品保费[ProductID={Amount=50.00, Integral=101001001}...]</b>
	 */
	public static Map<String, Map<String, String>> ProductCalculate(
			Map<String, String> ProductInfo,String ordersn, String Channel) {
		if (ProductInfo == null || ProductInfo.isEmpty()) {
			return null;
		}

		if (StringUtil.isEmpty(Channel)) {
			return null;
		}

		ActivityCalculateDetail acd = new ActivityCalculateDetail();

		try {
			Map<String, Map<String, String>> result = acd
					.ProductDiscountIntegralCalculate(ProductInfo,ordersn, Channel);
			if (result == null || result.size() != ProductInfo.size()) {
				logger.info("ActivityCalculate.ProductCalculate 产品计算活动保费-折扣和积分异常 -1.Channel->{}",Channel);
				Set<Map.Entry<String, String>> sqlProductInfo = ProductInfo
						.entrySet();
				for (Iterator<Map.Entry<String, String>> it = sqlProductInfo
						.iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) it
							.next();
					logger.info("产品信息==>{} 保费==>{}", entry.getKey(), entry.getValue());
				}
				return null;
			}

			return result;
		} catch (Exception e) {
			logger.error("ActivityCalculate.ProductCalculate 产品计算活动保费-折扣和积分异常-2.Channel->{}", Channel);
			Set<Map.Entry<String, String>> sqlProductInfo = ProductInfo
					.entrySet();
			for (Iterator<Map.Entry<String, String>> it = sqlProductInfo
					.iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it
						.next();
				logger.info("产品信息==>{} 保费==>{}", entry.getKey(), entry.getValue());
			}
			return null;

		}
	}

	/**
	 * 产品购物车活动信息<br/>
	 * 
	 * <b>@param data 产品信息 :ProductID、Amount</b><br/>
	 * 
	 * <b>@param Channel 渠道 配置表内进行配置</b><br/>
	 * 
	 * <b>@return ActivitySn(活动编码)-{ProductInfo(产品信息)[{订单号，原始保费，活动保费，优惠金额}]
	 * ,ActivityInfo(活动信息){活动标题，活动描述，活动类型}} </b>
	 */
	public static Map<String, Map<String, Object>> ProductShoppingCartInfo(
			List<SDOrder> orderList, String Channel,boolean groupFlag) {
		if (orderList == null || orderList.size() == 0) {
			return null;
		}

		if (StringUtil.isEmpty(Channel)) {
			return null;
		}

		ActivityCalculateDetail acd = new ActivityCalculateDetail();
		try {
			Map<String, Map<String, Object>> result = acd
					.ProductDiscountOrFuLLCalculate(orderList, Channel,groupFlag);
			if (result == null || result.size() == 0) {
				logger.info("ActivityCalculate.ProductShoppingCartInfo 产品购物车活动信息异常 -1.Channel->{}", Channel);
				for (int i = 0; i < orderList.size(); i++) {
					logger.info("产品信息==> {}", orderList.get(i));
				}
				return null;
			}
			return result;

		} catch (Exception e) {
			logger.error("ActivityCalculate.ProductShoppingCartInfo 产品购物车活动信息异常-2.Channel->{}", Channel);
			for (int i = 0; i < orderList.size(); i++) {
				logger.info("产品信息==>{}", orderList.get(i));
			}
			return null;

		}
	}

	/**
	 * 交易完成后，优惠拆分
	 * 
	 * <b>@param paySn 交易号</b>
	 * 
	 */
	public static void activityeSplit(String paySn, String Channel) {
		try {

			if (StringUtil.isEmpty(paySn)) {
				return;
			}

			ActivityGiveDetail agd = new ActivityGiveDetail();

			// 订单拆分
			boolean orderSplitResult = agd.orderPriceSplit(paySn, Channel);
			if (!orderSplitResult) {
				logger.error("由于订单费用拆分异常,保单费用拆分异常.paySn==>{}", paySn);
				return;
			}

			// 保单费用拆分
			agd.policyPriceSplit(paySn);
		} catch (Exception e) {
			logger.error("交易完成后，优惠拆分异常.paySn==>" + paySn + e.getMessage(), e);
		}
	}

	/**
	 * 产品详细页面活动信息
	 * 
	 * <b>@param productID 产品编码</b> <br/>
	 * 
	 * <b>@param Channel 渠道</b>
	 * 
	 * @return 处理结果[{ProductID,title,description, type,typeName}]
	 */
	public static List<Map<String, String>> ProductActivityInfo(
			String productID, String Channel) {

		if (StringUtil.isEmpty(productID) || StringUtil.isEmpty(Channel)) {
			return null;
		}

		ActivityCalculateDetail acd = new ActivityCalculateDetail();
		List<String> ProductList = new ArrayList<String>();
		ProductList.add(productID);

		try {
			return acd.ProductActivityInfo(ProductList, Channel);

		} catch (Exception e) {
			logger.error("ActivityCalculate.ProductActivityInfo 获取产品详细页面活动信息异常-2.Channel->"
					+ Channel + " 产品编码==>" + productID + e.getMessage(), e);
			return null;

		}

	}

	/**
	 * 交易完成后，积分赠送
	 * 
	 * <b>@param paySn 交易号</b> <br/>
	 * 
	 * <b>@param Channel 渠道</b>
	 * 
	 */
	public static void TransactionDealIntegral(String paySn, String Channel) {

		try {
			if (StringUtil.isEmpty(Channel)) {
				logger.error("交易完成后，积分赠送  == >渠道为空.");
				return;
			}

			ActivityGiveDetail agd = new ActivityGiveDetail();

			agd.buyCalculateIntegral(paySn, Channel);

		} catch (Exception e) {
			logger.error("交易完成后，  积分赠送异常.  Channel==>" + Channel + " paySn==>"
					+ paySn + e.getMessage(), e);

		}

	}

	/**
	 * 交易完成后，积分转赠到用户表
	 * 
	 * <b>@param paySn 交易号</b> <br/>
	 * 
	 * <b>@param Channel 渠道</b>
	 * 
	 */
	public static void transPointToMember(String paySn) {

		try {
			if (StringUtil.isEmpty(paySn)) {
				logger.error("交易完成后，积分转赠到用户表  ==>交易号为空.");
				return;
			}

			ActivityGiveDetail agd = new ActivityGiveDetail();
			agd.transPointToMember(paySn);

		} catch (Exception e) {
			logger.error("交易完成后，积分转赠到用户表异常.paySn==>" + paySn + e.getMessage(), e);
		}

	}

	/**
	 * 交易完成后，满送、买送
	 * 
	 * <b>@param sdorderList 订单列表</b><br/>
	 * 
	 * <b>@param memberID 会员编码</b><br/>
	 * 
	 * <b>@param Channel 渠道</b>
	 * 
	 */
	public static void TransactionDeal(List<SDOrder> sdorderList,
			String memberID, String Channel) {

		try {
			if (StringUtil.isEmpty(Channel)) {
				logger.warn("交易完成后，满送发送   == >渠道为空.");
				return;
			}
			ActivityGiveDetail agd = new ActivityGiveDetail();

			agd.buySendActivityInfo(sdorderList, memberID, Channel);

		} catch (Exception e) {
			logger.error("交易完成后，满送发送 异常.memberID==>" + memberID
					+ " Channel==>" + Channel + e.getMessage(), e);
			for (int i = 0; i < sdorderList.size(); i++) {
				logger.error("交易完成后，满送发送 异常. 订单号==>{}", sdorderList.get(i).getOrderSn());
			}

		}

	}

	/**
	 * 支付页面，满送、买送信息获取
	 * 
	 * <b>@param sdorderList 订单列表</b>
	 * 
	 * <b>@param Channel 渠道</b>
	 * 
	 * return [{index(序列)、title(活动标题)、type(活动类型)}]
	 * 
	 */
	public static List<Map<String, String>> buySendActivityInfo(
			List<SDOrder> sdorderList, String Channel) {
		try {
			if (sdorderList == null || sdorderList.size() == 0) {
				return null;
			}
			if (StringUtil.isEmpty(Channel)) {
				return null;
			}

			ActivityGiveDetail agd = new ActivityGiveDetail();

			return agd.buySendActivityInfo(sdorderList, Channel);

		} catch (Exception e) {
			logger.error("支付页面，满送、买送信息获取异常. Channel==>" + Channel + e.getMessage(), e);
			for (int i = 0; i < sdorderList.size(); i++) {
				logger.error("支付页面，满送、买送信息获取异常. 订单号==>{}", sdorderList.get(i).getOrderSn());
			}
			return null;

		}
	}

	/**
	 * 支付页面，买成功您将获得、积分抵消部分总额
	 * 
	 * <b>@param sdorderList 订单列表</b>
	 * 
	 * <b>@param Channel 渠道</b>
	 * 
	 * <b>@param userpoints 使用积分</b>
	 * 
	 * <b>@param couponinfo 优惠券</b>
	 * 
	 * return
	 * [totalamount(总保费)、givepoint(赠送的积分)、canusepoint(可以使用的积分)、pointscalerunit
	 * （积分单位）]
	 * 
	 */
	public static Map<String, String> payPointInfo(List<SDOrder> sdorderList,
			String Channel, int userpoints, String couponinfo, String memberid) {
		try {
			if (sdorderList == null || sdorderList.size() == 0) {
				return null;
			}
			if (StringUtil.isEmpty(Channel)) {
				return null;
			}

			ActivityGiveDetail agd = new ActivityGiveDetail();
			return agd.payPointInfo(sdorderList, Channel, userpoints,
					couponinfo, memberid);

		} catch (Exception e) {
			logger.error("支付页面，买成功您将获得、积分抵消部分总额异常. Channel==>" + Channel + e.getMessage(), e);
			for (int i = 0; i < sdorderList.size(); i++) {
				logger.error("支付页面，买成功您将获得、积分抵消部分总额. 订单号==>{}", sdorderList.get(i).getOrderSn());
			}
			return null;

		}
	}
	
	public static Map<String,String> getMemberGradeBirthdayPoints(String memberact,String memberid, String points,String productGivePointsPercent) {
		List<Map<String,Object>> productToPointRates = new ArrayList<Map<String,Object>>();
		Map<String,Object> productToPointRate = new HashMap<String,Object>();
		productToPointRate.put("points", points);
		productToPointRate.put("rate", productGivePointsPercent);
		productToPointRates.add(productToPointRate);
		return getMemberGradeBirthdayPoints(memberact,memberid,points,productToPointRates);
	}
	
	/**
	 * 会员等级或者会员生日月积分累加计算
	 * 
	 * <b>@param memberid 会员ID</b> <br/>
	 * 
	 * <b>@param points 会员积分</b>
	 * 
	 * @return 累加后积分
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> getMemberGradeBirthdayPoints(String memberact,String memberid, String points, List<Map<String,Object>> productToPointRates) {
		Map<String,String> map_result=new HashMap<String,String>();
		memberSchema smemberSchema = new memberSchema();
		smemberSchema.setid(memberid);
		memberSet smemberSet = smemberSchema.query();
		String addpoints = "0";
		if (smemberSet.size() > 0) {
			//获取会员信息
			smemberSchema=smemberSet.get(0);
			//会员生日月判断
			if(IntegralConstant.POINT_SOURCE_BIRTH_MONTH.equals(memberact)){
				if(!new MemberCenterAction().isMemBirthMonth(smemberSchema.getbirthday(), smemberSchema.getbirthYear())){
					map_result.put("points", points);
					map_result.put("addpoints", addpoints);
					map_result.put("flag", "false");
					return map_result;
				}
			}
			Map<String, Object> param =new HashMap<String, Object>();
			String vipflag=smemberSchema.getvipFlag();
			if("Y".equals(vipflag)){
				param.put("MemberGrade",IntegralConstant.VIP_GRADE);
			}else{
				//modfy by wangej 20151013 grade如果取不出来，默认为K0
				if(smemberSchema.getgrade() == null || "".equals(smemberSchema.getgrade())){
					param.put("MemberGrade", "K0");
				}else{
					param.put("MemberGrade", smemberSchema.getgrade());
				}
			}
			try {
				Map map=new PointsCalculate().pointsManage(IntegralConstant.POINT_SEARCH,memberact,param);
				if(IntegralConstant.FAIL.equals(String.valueOf(map.get(IntegralConstant.STATUS)))){
					map_result.put("flag", "false");
					map_result.put("points", points);
					map_result.put("addpoints", addpoints);
					return  map_result;
				}
				List list=(List) map.get(IntegralConstant.DATA);
				if(list.size()>0){
					Map map_data=(Map) list.get(0);
					String PointsNum=StringUtil.isNullToZero(String.valueOf(map_data.get("PointsNum")));

					BigDecimal add_point = new BigDecimal("0.0");
					// 赠送积分乘以产品赠送比例
					for(Map<String,Object> productToPointRate : productToPointRates){
						String rate = (String)productToPointRate.get("rate");
						if(StringUtil.isEmpty(rate)){
							rate = "0.0";
						}
						BigDecimal baseIntegral = null;
						String amount = (String)productToPointRate.get("amount");
						if(StringUtil.isNotEmpty(amount)){
							baseIntegral = new BigDecimal(amount).multiply(new BigDecimal(Config.getValue("PointScalerUnit"))).setScale(0, BigDecimal.ROUND_HALF_UP);
						}else{
							baseIntegral = new BigDecimal(points);
						}
						BigDecimal addPoint = new BigDecimal(PointsNum).multiply(baseIntegral);
						addPoint = addPoint.multiply(new BigDecimal(rate));
						add_point = add_point.add(addPoint);
					}
					
					BigDecimal addresult_point=new BigDecimal(points).add(add_point);
					//无取舍操作的积分值
					map_result.put("pointsAll", String.valueOf(addresult_point));
					points=String.valueOf(addresult_point.setScale(0,BigDecimal.ROUND_HALF_UP ));
					addpoints = String.valueOf(add_point.setScale(0,BigDecimal.ROUND_HALF_UP ));
					map_result.put("PointDes", String.valueOf(map_data.get("PointDes")));
					map_result.put("MemberGrade", String.valueOf(param.get("MemberGrade")));
				}else{
					map_result.put("flag", "false");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			map_result.put("flag", "true");
		}
		map_result.put("addpoints", addpoints);
		map_result.put("points", points);
		return  map_result;
	}
	public static void main(String[] args) {
		// System.out.println("============================交易完成后，积分赠送 start===============================");
		// TransactionDealIntegral("W140800000770193700G", "wj");
		// System.out.println("============================交易完成后，积分赠送 end=================================");

		// System.out.println("============================单个产品计算活动保费-折扣 start===============================");
		// System.out.println(ProductCalculate("203401001", "17", "wj"));
		// System.out.println("============================单个产品计算活动保费-折扣 end=================================");
		// System.out.println();
		// System.out.println("============================多个产品计算活动保费-折扣 start===============================");
//		List<Map<String, String>> paramList = new ArrayList<Map<String, String>>();
//		Map<String, String> param = new HashMap<String, String>();
//		param.put("ProductID", "203401001");
//		param.put("Amount", "17");
//		paramList.add(param);
//		List<Map<String, String>> ProductList = ProductCalculate(paramList,
//				"wj");
//		for (int i = 0; i < ProductList.size(); i++) {
//			Map<String, String> mm = ProductList.get(i);
//			System.out.println("ProductID=>" + mm.get("ProductID") + "Amount=>"
//					+ mm.get("Amount")+ "PointAmount=>"
//							+ mm.get("Amount"));
//		}
		// System.out.println("============================多个产品计算活动保费-折扣 end=================================");
		// System.out.println();
//		 System.out.println("============================产品计算活动保费-折扣和积分 start===============================");
//		 HashMap<String, String> param = new HashMap<String, String>();
//		 param.put("203401001", "17");
//		 Map<String, Map<String, String>> mResult = ProductCalculate(param,"",
//		 "wj");
//		 Set<Map.Entry<String, Map<String, String>>> sResult =
//		 mResult.entrySet();
//		 Iterator<Map.Entry<String, Map<String, String>>> sResult_Iter =
//		 sResult.iterator();
//		 while (sResult_Iter.hasNext()) {
//		 Map.Entry<String, Map<String, String>> entry = sResult_Iter.next();
//		 System.out.println("ProductID=>" + entry.getKey() + "Amount=>" +
//		 entry.getValue().get("Amount") + "Integral=>" +
//		 entry.getValue().get("Integral")+ "PointAmount=>"
//			+ entry.getValue().get("PointAmount"));
//		 }
//
//		 System.out.println("============================产品计算活动保费-折扣和积分 end===============================");
		// System.out.println();
		//
		// System.out.println("============================产品购物车活动信息 end=================================");
		// System.out.println();
		//
		// System.out.println("============================产品活动信息 start===============================");
		// List<String> paramter = new ArrayList<String>();
		// paramter.add("101001001");
		//
		// List<Map<String, String>> resultList = ProductActivityInfo(paramter,
		// "wj");
		// for (int i = 0; i < resultList.size(); i++) {
		// Map<String, String> mm = resultList.get(i);
		// System.out.println("ProductID=>" + mm.get("ProductID") + " - title=>"
		// + mm.get("title") + " - description=>" + mm.get("description") +
		// " - type=>" + mm.get("type"));
		// }
		//
		// System.out.println("============================产品活动信息 end=================================");

		// System.out.println();
//		System.out
//				.println("============================产品购物车活动信息 start=================================");
//		List<SDOrder> paramterList = new ArrayList<SDOrder>();
//		SDOrder tSDOrder = new SDOrder();
//		tSDOrder.setOrderSn("2013000011115958");
//		tSDOrder.setTotalAmount(new BigDecimal("1270"));
//		paramterList.add(tSDOrder);
//
//		tSDOrder = new SDOrder();
//		tSDOrder.setOrderSn("2013000011115959");
//		tSDOrder.setTotalAmount(new BigDecimal("35"));
//		paramterList.add(tSDOrder);
//
//		System.out.println(payPointInfo(paramterList, "wj", 49, "", "")
//				.toString());

		// System.out.println("============================产品购物车活动信息 end=================================");
		//
		// System.out.println();
		// System.out.println("============================交易完成后，优惠拆分 end=================================");
		// activityeSplit("W140800003990328900G", "wj");
		// System.out.println("============================交易完成后，优惠拆分 end=================================");
		// System.out.println();

//		System.out.println();
		// System.out.println("============================支付页面，满送、买送信息获取 start=================================");
		// List<SDOrder> paramterList = new ArrayList<SDOrder>();
		// SDOrder tSDOrder = new SDOrder();
		// tSDOrder.setOrderSn("2013000011113135");
		// tSDOrder.setPayPrice("410");
		// paramterList.add(tSDOrder);
		//
		// List<Map<String, String>> reuslt = buySendActivityInfo(paramterList,
		// "wj");
		// for (int i = 0; i < reuslt.size(); i++) {
		// Map<String, String> mm = reuslt.get(i);
		// System.out.println(mm.get("index") + "  类型：" + mm.get("type") +
		// "  标题：" + mm.get("title"));
		// }
		//
		// System.out.println("============================支付页面，满送、买送信息获取 end=================================");
		// System.out.println();
	}
}
