package com.sinosoft.inter;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDOrder;
import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.memberSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 活动费用计算
 *
 */
public class ActivityCalculateDetail {
	private static final Logger logger = LoggerFactory.getLogger(ActivityCalculateDetail.class);

	/**
	 * 产品折扣比例
	 */
	private final static int DISCOUNT_RATE = 10;

	/**
	 * 产品积分赠送比例
	 */
	public final static int PRODUCT_INTEGRAL_RATE = 1;

	/**
	 * 产品折扣类型
	 */
	private final static int PRODUCT_DISCOUNT_ACTION = 1;

	public final static String NO_ACTIVITY = "_no_activity";

	private String getMemberGrade(String memberId) {
		String grade = "";
		if (StringUtil.isNotEmpty(memberId)) {
			memberSchema memberSchema = new memberSchema();
			memberSchema.setid(memberId);
			if (memberSchema.fill()) {
				grade = memberSchema.getgrade();
				if ("Y".equals(memberSchema.getvipFlag())) {
					grade = "VIP";
				}
			}
		}

		return grade;
	}
	/**
	 * 产品折扣计算
	 *
	 * @param ProductInfo
	 * @param Channel
	 * @return
	 */
	public List<Map<String, String>> ProductDiscountCalculate(
			List<Map<String, String>> ProductInfoList, String Channel, String memberId) {
		try {
			String ProductActivitySQL = getProductActivitySql(ProductInfoList,
					PRODUCT_DISCOUNT_ACTION, Channel);
			QueryBuilder qb = new QueryBuilder(ProductActivitySQL);
			DataTable dt = qb.executeDataTable();
			// 取得会员等级
			String grade = getMemberGrade(memberId);
			for (int i = 0; i < ProductInfoList.size(); i++) {
				Map<String, String> ProductInfo = ProductInfoList.get(i);

				for (int j = 0; j < dt.getRowCount(); j++) {

					String ActivityData = dt.get(j).getString("ActivityData");
					String ProductID = dt.get(j).getString("ProductID");
					if (StringUtil.isNotEmpty(ProductID)
							&& ProductID.equals(ProductInfo.get("ProductID"))) {
						// 会员频道
						if ("Y".equalsIgnoreCase(dt.get(j).getString("memberChannel"))) {
							if (StringUtil.isEmpty(memberId) || StringUtil.isEmpty(grade) || (StringUtil.isNotEmpty(dt.get(j).getString("MemberRule")) && !dt.get(j).getString("MemberRule").contains(grade))) {
								continue;
							}
						}
//						System.out.println("产品==>" + ProductID
//								+ " 满足折扣活动,折扣率为==>" + ActivityData);
						String GroupbuyWhether = dt.get(j).getString("GroupbuyWhether");
						String GroupbuyNum = dt.get(j).getString("GroupbuyNum");
						if("1".equals(GroupbuyWhether)){
							String ordersn=ProductInfo.get("ordersn");
							//保单数
							String risktype_num="0";
							if(StringUtil.isNotEmpty(ordersn)){
								risktype_num=new QueryBuilder("select sum(1)  from   sdinformationrisktype where ordersn =?",ordersn).executeString();
								if(StringUtil.isEmpty(risktype_num)){
									risktype_num="0";
								}
							}
							if(Integer.parseInt(risktype_num)>=Integer.parseInt(GroupbuyNum)){
								ProductInfo.put("Amount", CalculateDiscount(ActivityData, ProductInfo.get("Amount")) + "");
							}
						}else{
							ProductInfo.put( "Amount", CalculateDiscount(ActivityData, ProductInfo.get("Amount")) + "");
						}
					}
				}
			}
			return ProductInfoList;
		} catch (Exception e) {
			logger.error("ActivityCalculateDetail.ProductDiscountCalculate方法产品折扣计算异常." + e.getMessage(), e);
			return ProductInfoList;

		}
	}

	/**
	 * 获取产品活动信息
	 *
	 * @param ProductID
	 * @param Channel
	 * @return
	 */
	public List<Map<String, String>> ProductActivityInfo(
			List<String> ProductList, String Channel) {
		String ProductIDStr = "";
		for (int i = 0; i < ProductList.size(); i++) {
			ProductIDStr += " s1.ProductId = '" + ProductList.get(i) + "' ";
			if (i != ProductList.size() - 1) {
				ProductIDStr += " or ";
			}
		}
		StringBuffer ProductActivityInfo = new StringBuffer();
		ProductActivityInfo
				.append(" select s1.ProductID,s2.title,s2.description, s2.type ,z1.CodeName typeName ,s2.GroupbuyWhether , s2.activitySn,s2.memberChannel ");
		ProductActivityInfo
				.append(" from SdProductActivityLink s1,sdcouponactivityinfo s2 ,zdcode z1 ");
		ProductActivityInfo
				.append(" where  z1.parentcode='Activity.type' and ( ")
				.append(ProductIDStr).append(" )");
		ProductActivityInfo
				.append(" and  status='3' and  s1.ActivitySn=s2.activitySn and s2.type=z1.codevalue  ");
		ProductActivityInfo.append(" and  s2.starttime <='")
				.append(PubFun.getCurrent()).append("'");
		ProductActivityInfo.append(" and  s2.endtime >='")
				.append(PubFun.getCurrent()).append("'");
		ProductActivityInfo.append(" and  s1.ActivityChannel = '")
				.append(Channel).append("' ");

		if ("wj".equalsIgnoreCase(Channel)) {
			ProductActivityInfo.append(" order by  find_in_set(s2.type,'6,3,2,1,7,8,9,12')");

		} else {
			ProductActivityInfo.append(" order by  find_in_set(s2.type,'6,3,2,1,7,8')");

		}

		QueryBuilder qb = new QueryBuilder(ProductActivityInfo.toString());
		DataTable dt = qb.executeDataTable();

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (int i = 0; i < dt.getRowCount(); i++) {
			result.add(dt.get(i).toMapx());
		}
		return result;
	}

	public Map<String, Map<String, String>> ProductIntegralCalculate(
			Map<String, String> ProductInfo,String risktypeNum, String Channel, String memberId) {
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		List<String> product_list = new ArrayList<String>();
		try {
			// 取得会员等级
			String grade = getMemberGrade(memberId);
			String ProductIDStr = "";
			if (StringUtil.isEmpty(risktypeNum)) {
				risktypeNum = "1";
			}
			Set<Map.Entry<String, String>> sqlProductInfo = ProductInfo
					.entrySet();
			for (Iterator<Map.Entry<String, String>> it = sqlProductInfo
					.iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it
						.next();
				ProductIDStr += " s1.ProductId = '" + entry.getKey() + "' or";
				product_list.add(entry.getKey());
			}

			if (ProductIDStr.endsWith("or")) {
				ProductIDStr = ProductIDStr.substring(0,
						ProductIDStr.length() - 2);
			}

			StringBuffer ProductActivityInfo = new StringBuffer();
			ProductActivityInfo
					.append(" select s1.ProductID,s2.title,s2.description, s2.type ,s3.ActivityData ,s2.GroupbuyWhether,s2.GroupbuyNum,s2.memberChannel,s3.MemberRule ");
			ProductActivityInfo
					.append(" from SdProductActivityLink s1,sdcouponactivityinfo s2,SdActivityRule s3");
			ProductActivityInfo.append(" where  ( ").append(ProductIDStr)
					.append(" )");
			ProductActivityInfo.append(" and s2.type='7' "); //积分
			ProductActivityInfo
					.append(" and  status='3' and  s1.ActivitySn=s2.activitySn  and s2.activitySn=s3.activitysn ");
			ProductActivityInfo.append(" and  s2.starttime <='")
					.append(PubFun.getCurrent()).append("'");
			ProductActivityInfo.append(" and  s2.endtime >='")
					.append(PubFun.getCurrent()).append("'");
			ProductActivityInfo.append(" and  s1.ActivityChannel = '")
					.append(Channel).append("'");
			ProductActivityInfo
					.append(" order by s1.ProductID , find_in_set(s2.type,'7') ");

			QueryBuilder qb = new QueryBuilder(ProductActivityInfo.toString());
			DataTable dt = qb.executeDataTable();

			// 根据产品编码查询对应的赠送积分比例
			// 会员编码？？？？后续如何处理
			PointsCalculate pc = new PointsCalculate();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ProductList", product_list);
			Map<String, Object> give_map = pc.pointsManage(
					IntegralConstant.POINT_PRODUCT, null, param);
			DataTable give_dt = (DataTable) give_map.get(IntegralConstant.DATA);

			Mapx<String, String> GiveMap = give_dt.toMapx("ProductID",
					"GivePoints");
			Mapx<String, String> BuyMap = give_dt.toMapx("ProductID",
					"BuyPoints");

			Map<String, String> ProductCalculateResult;
			for (int i = 0; i < dt.getRowCount(); i++) {

				String type = dt.get(i).getString("type");
				String ActivityData = dt.get(i).getString("ActivityData");
				String ProductID = dt.get(i).getString("ProductID");
				String GroupbuyWhether = dt.get(i).getString("GroupbuyWhether");
				String GroupbuyNum = dt.get(i).getString("GroupbuyNum");

				ProductCalculateResult = result.get(ProductID);
				if (ProductCalculateResult == null)
					ProductCalculateResult = new HashMap<String, String>();

				if ("7".equals(type)) {
					// 会员频道
					if ("Y".equalsIgnoreCase(dt.getString(i, "memberChannel"))) {
						if (StringUtil.isEmpty(memberId) || StringUtil.isEmpty(grade) || (StringUtil.isNotEmpty(dt.getString(i, "MemberRule")) && !dt.getString(i, "MemberRule").contains(grade))) {
							continue;
						}
					}

					String Amount = ProductInfo.get(ProductID);
					ProductCalculateResult.put("Amount", Amount);

					// 赠送产品的积分比率，如果为空，则默认为0
					String pointrate = GiveMap.getString(ProductID);
					if (StringUtil.isEmpty(pointrate)) {
						pointrate = "0.0";

					}

					if("1".equals(GroupbuyWhether)){
						//保单数
						if(Integer.parseInt(risktypeNum)>=Integer.parseInt(GroupbuyNum)){
							ProductCalculateResult.put("Integral",CalculateIntegral(ActivityData, pointrate, Amount+ "")+ "");
						}
					}else{
						ProductCalculateResult.put("Integral",CalculateIntegral(ActivityData, pointrate, Amount+ "")+ "");
					}
				}
				result.put(ProductID, ProductCalculateResult);
			}

			// 如果产品保费或者积分为空，则添加默认值
			Set<Map.Entry<String, String>> sProductInfo = ProductInfo
					.entrySet();
			for (Iterator<Map.Entry<String, String>> it = sProductInfo
					.iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it
						.next();
				Map<String, String> mProductResult = result.get(entry.getKey());

				// 赠送产品的积分比率，如果为空，则默认为0
				String pointrate = GiveMap.getString(entry.getKey());
				if (StringUtil.isEmpty(pointrate)) {
					pointrate = "0.0";

				}

				if (mProductResult != null && !mProductResult.isEmpty()) {

					if (StringUtil.isEmpty(mProductResult.get("Integral"))) {
						mProductResult.put(
								"Integral",
								CalculateIntegral(PRODUCT_INTEGRAL_RATE + "",
										pointrate, mProductResult.get("Amount")
												+ "")
										+ "");
					}
				} else {
					mProductResult = new HashMap<String, String>();
					mProductResult.put("Amount", entry.getValue());

					mProductResult.put(
							"Integral",
							CalculateIntegral(PRODUCT_INTEGRAL_RATE + "",
									pointrate, mProductResult.get("Amount")
											+ "")
									+ "");
				}
				mProductResult.put("pointrate", pointrate);
				result.put(entry.getKey(), mProductResult);
			}

			return result;
		} catch (Exception e) {
			logger.error("ActivityCalculateDetail.ProductIntegralCalculate方法产品折扣计算异常." + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 产品折扣、积分计算
	 *
	 * @param ProductInfo
	 * @param Channel
	 * @return
	 */
	public Map<String, Map<String, String>> ProductDiscountIntegralCalculate(
			Map<String, String> ProductInfo,String ordersn, String Channel) {
		try {

			Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
			List<String> product_list = new ArrayList<String>();

			String ProductIDStr = "";
			Set<Map.Entry<String, String>> sqlProductInfo = ProductInfo
					.entrySet();
			for (Iterator<Map.Entry<String, String>> it = sqlProductInfo
					.iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it
						.next();
				ProductIDStr += " s1.ProductId = '" + entry.getKey() + "' or";
				product_list.add(entry.getKey());
			}

			if (ProductIDStr.endsWith("or")) {
				ProductIDStr = ProductIDStr.substring(0,
						ProductIDStr.length() - 2);
			}

			StringBuffer ProductActivityInfo = new StringBuffer();
			ProductActivityInfo
					.append(" select s1.ProductID,s2.title,s2.description, s2.type ,s3.ActivityData ,s2.GroupbuyWhether,s2.GroupbuyNum ,s3.MemberRule ,s2.memberChannel ");
			ProductActivityInfo
					.append(" from SdProductActivityLink s1,sdcouponactivityinfo s2,SdActivityRule s3");
			ProductActivityInfo.append(" where  ( ").append(ProductIDStr)
					.append(" )");
			ProductActivityInfo.append(" and  ( s2.type='6' or s2.type='7') "); // 折扣、积分
			ProductActivityInfo
					.append(" and  status='3' and  s1.ActivitySn=s2.activitySn  and s2.activitySn=s3.activitysn ");
			ProductActivityInfo.append(" and  s2.starttime <='")
					.append(PubFun.getCurrent()).append("'");
			ProductActivityInfo.append(" and  s2.endtime >='")
					.append(PubFun.getCurrent()).append("'");
			ProductActivityInfo.append(" and  s1.ActivityChannel = '")
					.append(Channel).append("'");
			ProductActivityInfo
					.append(" order by s1.ProductID , find_in_set(s2.type,'6,7') ");

			QueryBuilder qb = new QueryBuilder(ProductActivityInfo.toString());
			DataTable dt = qb.executeDataTable();

			// 根据产品编码查询对应的赠送积分比例
			// 会员编码？？？？后续如何处理
			PointsCalculate pc = new PointsCalculate();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("ProductList", product_list);
			Map<String, Object> give_map = pc.pointsManage(
					IntegralConstant.POINT_PRODUCT, null, param);
			DataTable give_dt = (DataTable) give_map.get(IntegralConstant.DATA);

			Mapx<String, String> GiveMap = give_dt.toMapx("ProductID",
					"GivePoints");
			Mapx<String, String> BuyMap = give_dt.toMapx("ProductID",
					"BuyPoints");

			Map<String, String> ProductCalculateResult;
			boolean judgementMemberGrade =false;
			String memberid ="";
			try{
				memberid = (String) ActionContext.getContext().getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME);
			} catch (Exception e) {
				memberid="";
			}
			if (StringUtil.isEmpty(memberid) && StringUtil.isNotEmpty(ordersn)) {
				memberid = new QueryBuilder("select memberId from sdorders where orderSn=?", ordersn).executeString();
			}
			String memberGrade = getMemberGrade(memberid);
			for (int i = 0; i < dt.getRowCount(); i++) {
				String type = dt.get(i).getString("type");
				String ActivityData = dt.get(i).getString("ActivityData");
				String ProductID = dt.get(i).getString("ProductID");
				String GroupbuyWhether = dt.get(i).getString("GroupbuyWhether");
				String GroupbuyNum = dt.get(i).getString("GroupbuyNum");
				String MemberRule = dt.get(i).getString("MemberRule");
				String memberChannel = dt.get(i).getString("memberChannel");
				judgementMemberGrade = memberGradeShow(MemberRule,memberChannel,memberGrade);
				if(!judgementMemberGrade &&( "6".equals(type)||"7".equals(type))) continue;
				ProductCalculateResult = result.get(ProductID);
				if (ProductCalculateResult == null)
					ProductCalculateResult = new HashMap<String, String>();

				if ("6".equals(type) && judgementMemberGrade) {
					// 赠送产品的积分比率，如果为空，则默认为0
					String buyrate = BuyMap.getString(ProductID);
					if (StringUtil.isEmpty(buyrate)) {
						buyrate = "0.0";

					}
					if("1".equals(GroupbuyWhether)){
						//保单数
						String risktype_num="0";
						if(StringUtil.isNotEmpty(ordersn)){
							risktype_num=new QueryBuilder("select sum(1)  from   sdinformationrisktype where ordersn =?",ordersn).executeString();
							if(StringUtil.isEmpty(risktype_num)){
								risktype_num="0";
							}
						}
						if(Integer.parseInt(risktype_num)>=Integer.parseInt(GroupbuyNum)){
							ProductCalculateResult.put("Amount",CalculateDiscount(ActivityData, ProductInfo.get(ProductID))+"");
						}
					}else{
						ProductCalculateResult.put("Amount",CalculateDiscount(ActivityData, ProductInfo.get(ProductID))+"");
					}
					ProductCalculateResult.put("PointAmount",CalculateDiscountPrice(ActivityData, buyrate,ProductInfo.get(ProductID)) + "");

				} else if ("7".equals(type)  && judgementMemberGrade ) {

					String Amount = ProductCalculateResult.get("Amount");
					if (StringUtil.isEmpty(Amount)) {
						Amount = ProductInfo.get(ProductID);
						ProductCalculateResult.put("Amount", Amount);
					}

					// 赠送产品的积分比率，如果为空，则默认为0
					String pointrate = GiveMap.getString(ProductID);
					if (StringUtil.isEmpty(pointrate)) {
						pointrate = "0.0";

					}

					// 赠送产品的积分比率，如果为空，则默认为0
					String buyrate = BuyMap.getString(ProductID);
					if (StringUtil.isEmpty(buyrate)) {
						buyrate = "0.0";

					}
					if("1".equals(GroupbuyWhether)){
						//保单数
						String risktype_num="0";
						if(StringUtil.isNotEmpty(ordersn)){
							risktype_num=new QueryBuilder("select sum(1)  from   sdinformationrisktype where ordersn =?",ordersn).executeString();
							if(StringUtil.isEmpty(risktype_num)){
								risktype_num="0";
							}
						}
						if(Integer.parseInt(risktype_num)>=Integer.parseInt(GroupbuyNum)){
							ProductCalculateResult.put("Integral",CalculateIntegral(ActivityData, pointrate, Amount+ "")+ "");
						}
					}else{
						ProductCalculateResult.put("Integral",CalculateIntegral(ActivityData, pointrate, Amount+ "")+ "");
					}
					ProductCalculateResult.put("PointAmount",CalculateDiscount(PRODUCT_INTEGRAL_RATE+"", buyrate,ProductInfo.get(ProductID)) + "");
				}
				result.put(ProductID, ProductCalculateResult);
			}

			// 如果产品保费或者积分为空，则添加默认值
			Set<Map.Entry<String, String>> sProductInfo = ProductInfo
					.entrySet();
			for (Iterator<Map.Entry<String, String>> it = sProductInfo
					.iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it
						.next();
				Map<String, String> mProductResult = result.get(entry.getKey());

				// 赠送产品的积分比率，如果为空，则默认为0
				String pointrate = GiveMap.getString(entry.getKey());
				if (StringUtil.isEmpty(pointrate)) {
					pointrate = "0.0";

				}

				// 赠送产品的积分比率，如果为空，则默认为0
				String buyrate = BuyMap.getString(entry.getKey());
				if (StringUtil.isEmpty(buyrate)) {
					buyrate = "0.0";

				}

				if (mProductResult != null && !mProductResult.isEmpty()) {
					if (StringUtil.isEmpty(mProductResult.get("Amount"))) {
						mProductResult.put("Amount", entry.getValue());
						mProductResult.put(
								"PointAmount",
								CalculateDiscount(PRODUCT_INTEGRAL_RATE + "", buyrate,
										entry.getValue()) + "");
					}

					if (StringUtil.isEmpty(mProductResult.get("Integral"))) {
						mProductResult.put(
								"Integral",
								CalculateIntegral(PRODUCT_INTEGRAL_RATE + "",
										pointrate, mProductResult.get("Amount")
												+ "")
										+ "");
					}
				} else {
					mProductResult = new HashMap<String, String>();
					mProductResult.put("Amount", entry.getValue());
					mProductResult.put(
							"PointAmount",
							CalculateDiscount(PRODUCT_INTEGRAL_RATE + "", buyrate,
									entry.getValue()) + "");

					mProductResult.put(
							"Integral",
							CalculateIntegral(PRODUCT_INTEGRAL_RATE + "",
									pointrate, mProductResult.get("Amount")
											+ "")
									+ "");
				}
				mProductResult.put("pointrate", pointrate);
				result.put(entry.getKey(), mProductResult);
			}

			return result;
		} catch (Exception e) {
			logger.error("ActivityCalculateDetail.ProductDiscountCalculate方法产品折扣计算异常." + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 产品折扣、满减计算
	 */
	public Map<String, Map<String, Object>> ProductDiscountOrFuLLCalculate(
			List<SDOrder> OrderList, String Channel,boolean groupFlag) {
		// 记录订单对应的产品
		Map<String, String> productMap = new HashMap<String, String>();
		// 记录最终满足活动的产品
		List<String> isExistActivityProduct = new ArrayList<String>();

		// 获取所有产品活动
		Map<String, Map<String, Object>> activity_product_info = getActivityInfoByOrder(OrderList, Channel, productMap);

		// 根据活动计算优惠价格
		dealPriceByActivity(activity_product_info, OrderList, groupFlag, isExistActivityProduct);

		// 订单不参加活动的处理
		dealNoActivity(activity_product_info, OrderList, isExistActivityProduct, productMap);

		return activity_product_info;

	}

	/**
	 * 获取所有产品活动
	 * @param OrderList 订单
	 * @param Channel 渠道
	 * @param productMap 记录订单对应的产品
	 * @return 所有产品活动
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Map<String, Object>> getActivityInfoByOrder(List<SDOrder> OrderList, String Channel, Map<String, String> productMap) {
		String orderSNStr = "";
		for (SDOrder sdorder : OrderList) {
			orderSNStr += ",'" + sdorder.getOrderSn()+ "'";
		}
		orderSNStr =  (" s3.ordersn in (" + orderSNStr.substring(1)+") ");

		String product_sql = " select s3.ordersn,s3.ProductID  ";
		product_sql += " from  sdinformation s3   ";
		product_sql += "where " + orderSNStr;
		QueryBuilder product_qb = new QueryBuilder(product_sql);
		DataTable product_dt = product_qb.executeDataTable();
		productMap.putAll(product_dt.toMapx("ordersn", "ProductID"));


		// accumulation 1为不可累计，0为可累计
		StringBuffer ProductActivityInfo = new StringBuffer();
		ProductActivityInfo
				.append(" select s4.orderSn  ,s4.totalAmount, s1.ProductID,s2.title,s2.description,s2.accumulation,s2.type ,z1.CodeName typeName ,s2.activitySn,s2.GroupbuyWhether,s2.GroupbuyNum,s2.memberChannel  ");
		ProductActivityInfo
				.append(" from  SdProductActivityLink s1,sdcouponactivityinfo s2 ,zdcode z1, sdinformation s3 ,sdorders s4  ");
		ProductActivityInfo.append(" where  ( ").append(orderSNStr)
				.append(" )");
		ProductActivityInfo
				.append(" and  (s2.type='3' or  s2.type='6')   and s4.ordersn=s3.ordersn and z1.parentcode='Activity.type' "); // 满减
		ProductActivityInfo
				.append(" and  s2.status='3' and s2.type=z1.codevalue and  s1.ActivitySn=s2.activitySn  and s3.productid=s1.productid  ");
		ProductActivityInfo.append(" and  s2.starttime <='")
				.append(PubFun.getCurrent()).append("'");
		ProductActivityInfo.append(" and  s2.endtime >='")
				.append(PubFun.getCurrent()).append("'");
		ProductActivityInfo.append(" and  s1.ActivityChannel='")
				.append(Channel).append("'");
		ProductActivityInfo.append(" order by s1.ActivitySn  ");
		QueryBuilder qb = new QueryBuilder(ProductActivityInfo.toString());
		DataTable dt = qb.executeDataTable();

		// 整理产品形态为：活动编码-[{产品信息}、{活动信息}]
		Map<String, Map<String, Object>> activity_product_info = new HashMap<String, Map<String, Object>>();
		List<Map<String, String>> ProductInfoList = null;
		for (int i = 0; i < dt.getRowCount(); i++) {
			String temp = dt.get(i).getString("activitySn");

			Map<String, Object> mActivityInfo = new HashMap<String, Object>();

			if (activity_product_info.get(temp) == null
					|| activity_product_info.get(temp).size() == 0
					|| activity_product_info.get(temp).get("ProductInfo") == null) {
				ProductInfoList = new ArrayList<Map<String, String>>();

			} else {
				ProductInfoList = (List<Map<String, String>>) activity_product_info
						.get(temp).get("ProductInfo");
			}

			Map<String, String> mProcuntInfo = new HashMap<String, String>();
			mProcuntInfo.put("OrderSn", dt.get(i).getString("orderSn"));
			mProcuntInfo.put(
					"Amount",
					formatDouble(
							Double.parseDouble(dt.get(i).getString(
									"totalAmount")), 2));// 原始保费
			mProcuntInfo.put(
					"ActivityeAmount",
					formatDouble(
							Double.parseDouble(dt.get(i).getString(
									"totalAmount")), 2));// 活动保费
			mProcuntInfo.put("DiscountAmount", "0.00");// 优惠金额
			mProcuntInfo.put("ProductID", dt.get(i).getString("ProductID"));// 产品编码
			ProductInfoList.add(mProcuntInfo);

			mActivityInfo.put("ProductInfo", ProductInfoList);

			// 获取活动信息
			if (activity_product_info.get(temp) == null
					|| activity_product_info.get(temp).size() == 0
					|| activity_product_info.get(temp).get("ActivityInfo") == null) {
				Map<String, String> mActivityContent = new HashMap<String, String>();
				mActivityContent.put("activitySn",
						dt.get(i).getString("activitySn"));
				mActivityContent.put("title", dt.get(i).getString("title"));
				mActivityContent.put("description",
						dt.get(i).getString("description"));
				mActivityContent.put("accumulation",
						dt.get(i).getString("accumulation"));
				mActivityContent.put("type", dt.get(i).getString("type"));
				mActivityContent.put("typeName", dt.get(i).getString("typeName"));
				mActivityContent.put("GroupbuyWhether", dt.get(i).getString("GroupbuyWhether"));
				mActivityContent.put("GroupbuyNum", dt.get(i).getString("GroupbuyNum"));
				mActivityContent.put("memberChannel", dt.get(i).getString("memberChannel"));
				mActivityInfo.put("ActivityInfo", mActivityContent);

			} else {
				mActivityInfo.put("ActivityInfo",
						activity_product_info.get(temp).get("ActivityInfo"));
			}
			activity_product_info.put(temp, mActivityInfo);

		}
		return activity_product_info;
	}

	/**
	 * 根据活动计算优惠价格
	 * @param activity_product_info 所有产品活动信息
	 * @param OrderList 订单
	 * @param groupFlag 是否参加团购
	 * @param isExistActivityProduct 记录最终满足活动的产品
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean dealPriceByActivity(Map<String, Map<String, Object>> activity_product_info, List<SDOrder> OrderList, boolean groupFlag, List<String> isExistActivityProduct) {
		Set<Map.Entry<String, Map<String, Object>>> sActivity_product_info = activity_product_info
				.entrySet();
		Iterator<Map.Entry<String, Map<String, Object>>> Activity_product_info_Itor = sActivity_product_info
				.iterator();
		while (Activity_product_info_Itor.hasNext()) {
			Map.Entry<String, Map<String, Object>> entry = (Map.Entry<String, Map<String, Object>>) Activity_product_info_Itor
					.next();
			String activitysn = entry.getKey();

			if (activitysn.equalsIgnoreCase(NO_ACTIVITY)) {
				continue;
			}
			Map<String, Object> mActivityValue = entry.getValue();
			Map<String, String> mActivityInfo = (Map<String, String>) mActivityValue
					.get("ActivityInfo");
			List<Map<String, String>> mProductList = (List<Map<String, String>>) mActivityValue
					.get("ProductInfo");
			String type = mActivityInfo.get("type");
			String ordersns="";
			for (Map<String, String> mm : mProductList) {
				ordersns += (",'"+mm.get("OrderSn")+"'");
			}
			ordersns=ordersns.substring(1);
			//保单数
			String  risktype_num=new QueryBuilder("select sum(1) from sdinformationrisktype where ordersn  in ("+ordersns+")").executeString();
			if(StringUtil.isEmpty(risktype_num)){
				risktype_num="0";
			}
			String memberChannel = mActivityInfo.get("memberChannel");
			String memberId = OrderList.get(0).getMemberId();
			// 会员频道活动
			if ("Y".equalsIgnoreCase(memberChannel)) {
				// 会员频道 满减对应
				if (StringUtil.isEmpty(memberId)) {
					String tempOrderSn = OrderList.get(0).getOrderSn();
					QueryBuilder qbOrders = new QueryBuilder(" select memberId from sdorders where ordersn = ?", tempOrderSn);
					memberId = qbOrders.executeString();
				}
				// 未登录的不参加活动
				if (StringUtil.isEmpty(memberId)) {
					activity_product_info.remove(activitysn);
					continue;
				}
			}
			// 折扣 6 ，满减 3
			if ("6".equals(type)) {
				if (!dealDiscount(mActivityValue, activitysn, memberId, risktype_num, groupFlag)) {
					continue;
				}

			} else if ("3".equals(type)) {
				// 满减活动计算处理
				if (!dealFullCalculate(mActivityValue, activitysn, memberId, risktype_num, groupFlag)) {
					activity_product_info.remove(activitysn);
					continue;
				}
			}

			for (Map<String, String> mm : mProductList) {
				isExistActivityProduct.add(mm.get("OrderSn"));
			}

		}

		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean dealDiscount(Map<String, Object> mActivityValue, String activitysn, String memberId, String risktype_num, boolean groupFlag) {
		// 活动详情信息
		Map<String, String> mActivityInfo = (Map<String, String>) mActivityValue
				.get("ActivityInfo");
		// 订单信息
		List<Map<String, String>> mProductList = (List<Map<String, String>>) mActivityValue
				.get("ProductInfo");
		//是否有团购
		String GroupbuyWhether = mActivityInfo.get("GroupbuyWhether");
		String GroupbuyNum = mActivityInfo.get("GroupbuyNum");
		String memberChannel = mActivityInfo.get("memberChannel");
		DataTable ActivityRule_DT;
		// 会员频道
		if ("Y".equals(memberChannel)) {
			ActivityRule_DT = getActivityRuleData(0, activitysn, memberId);
		} else {
			ActivityRule_DT = getActivityRuleData(0, activitysn, null);
		}
		if (ActivityRule_DT == null
				|| ActivityRule_DT.getRowCount() != 1) {
			logger.error("活动数据异常,折扣对应（SdActivityRule）的数据不准确.{}", activitysn);
			return false;
		}

		String ActivityData = ActivityRule_DT.get(0).getString(
				"ActivityData");
		double TotalAmount = 0.0f;// 原始价格
		double ActivityeAmount = 0.0f; // 活动价格
		for (Map<String, String> productMap : mProductList) {
			//团购个数
			if(groupFlag){
				if("1".equals(GroupbuyWhether)){
					if(Integer.parseInt(risktype_num)>=Integer.parseInt(GroupbuyNum)){
						productMap.put("ActivityeAmount",
								CalculateDiscount(ActivityData, productMap.get("Amount"))
										+ "");// 活动保费
					}else{
						productMap.put("ActivityeAmount",productMap.get("Amount")+"");// 活动保费
					}
				}else{
					productMap.put("ActivityeAmount",
							CalculateDiscount(ActivityData, productMap.get("Amount"))
									+ "");// 活动保费
				}
			}
			productMap.put("DiscountAmount",
					formatDouble(
							Double.parseDouble(productMap.get("Amount"))
									- Double.parseDouble(productMap
											.get("ActivityeAmount")), 2));// 活动保费

			TotalAmount += Double.parseDouble(productMap.get("Amount"));
			ActivityeAmount += Double.parseDouble(productMap
					.get("ActivityeAmount"));
		}
		Map<String, Object> mActivityAmont = new HashMap<String, Object>();
		mActivityAmont.put("TotalAmount", formatDouble(TotalAmount, 2));
		mActivityAmont.put("DiscountAmount",
				formatDouble(TotalAmount - ActivityeAmount, 2));
		mActivityAmont.put("RealAmount",
				formatDouble(ActivityeAmount, 2));
		mActivityValue.put("ActivityAmont", mActivityAmont);

		return true;
	}

	/**
	 * 满减活动计算处理
	 * @param mActivityValue 活动相关信息（订单信息、活动详情信息）
	 * @param activitysn 活动编码
	 * @param memberId 会员id
	 * @param risktype_num 保单数
	 * @param groupFlag 是否团购
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean dealFullCalculate(Map<String, Object> mActivityValue, String activitysn, String memberId, String risktype_num, boolean groupFlag) {
		// 活动详情信息
		Map<String, String> mActivityInfo = (Map<String, String>) mActivityValue
				.get("ActivityInfo");
		// 订单信息
		List<Map<String, String>> mProductList = (List<Map<String, String>>) mActivityValue
				.get("ProductInfo");
		//是否有团购
		String GroupbuyWhether = mActivityInfo.get("GroupbuyWhether");
		String GroupbuyNum = mActivityInfo.get("GroupbuyNum");
		String memberChannel = mActivityInfo.get("memberChannel");
		// 活动总金额
		double TotalAmount = 0.0f;
		if (mProductList != null && mProductList.size() > 0) {
			for (Map<String, String> productMap : mProductList) {
				TotalAmount += Double.parseDouble(productMap.get("Amount"));
			}
		}

		// 优惠金额
		double DiscountAmount = 0.0f;
		DataTable ActivityRule_DT = null;

		if ("Y".equalsIgnoreCase(memberChannel)) {
			// 会员频道 满减对应
			if ("0".equals(mActivityInfo.get("accumulation"))) {
				ActivityRule_DT = getActivityRuleData(0, activitysn, memberId);
			} else {
				ActivityRule_DT = getActivityRuleData(TotalAmount, activitysn, memberId);
			}

		} else {
			if ("0".equals(mActivityInfo.get("accumulation"))) {
				ActivityRule_DT = getActivityRuleData(0, activitysn, null);
			} else {
				ActivityRule_DT = getActivityRuleData(TotalAmount, activitysn, null);
			}
		}

		// 结果不对
		if (ActivityRule_DT == null
				|| ActivityRule_DT.getRowCount() != 1) {
			return false;
		}

		// accumulation 1为不可累计，0为可累计
		if ("0".equals(mActivityInfo.get("accumulation"))) {

			String StartAmount = ActivityRule_DT.get(0).getString(
					"StartAmount");
			double ActivityData = ActivityRule_DT.get(0).getDouble(
					"ActivityData");

			// 如果满足活动金额
			if (TotalAmount >= Double.parseDouble(StartAmount)) {
				//团购个数
				if("1".equals(GroupbuyWhether)&&groupFlag){
					if(Integer.parseInt(risktype_num)>=Integer.parseInt(GroupbuyNum)){
						// 整数倍
						DiscountAmount = new BigDecimal(TotalAmount
								/ Double.parseDouble(StartAmount)).setScale(0,
								BigDecimal.ROUND_DOWN).intValue()
								* ActivityData;
					}
				}else{
					// 整数倍
					DiscountAmount = new BigDecimal(TotalAmount
							/ Double.parseDouble(StartAmount)).setScale(0,
							BigDecimal.ROUND_DOWN).intValue()
							* ActivityData;
				}
			}

		} else if ("1".equals(mActivityInfo.get("accumulation"))) {
			//团购个数
			if("1".equals(GroupbuyWhether)){
				if(Integer.parseInt(risktype_num)>=Integer.parseInt(GroupbuyNum)){
					DiscountAmount = ActivityRule_DT.get(0).getDouble(
							"ActivityData");
				}
			}else{
				DiscountAmount = ActivityRule_DT.get(0).getDouble(
						"ActivityData");
			}

		} else {
			logger.info("未知满减活动类型.accumulation==>{}  activitysn==>{}",mActivityInfo.get("accumulation"), activitysn);
		}

		// 实际价格
		Map<String, Object> mActivityAmont = new HashMap<String, Object>();
		mActivityAmont.put("TotalAmount", formatDouble(TotalAmount, 2));
		mActivityAmont.put("DiscountAmount",
				formatDouble(DiscountAmount, 2));
		mActivityAmont.put("RealAmount",
				formatDouble((TotalAmount - DiscountAmount), 2));
		mActivityValue.put("ActivityAmont", mActivityAmont);

		// 部分产品优惠费用
		double PartTotalDiscountAmount = 0.0f;
		double Amount = 0.0f;
		Map<String, String> mm;
		for (int k = 0; mProductList != null && k < mProductList.size(); k++) {
			mm = mProductList.get(k);
			Amount = Double.parseDouble(mm.get("Amount"));
			if (k == mProductList.size() - 1) {
				mm.put("ActivityeAmount",
						formatDouble(
								Amount
										- (DiscountAmount - PartTotalDiscountAmount),
								2));// 活动保费
				mm.put("DiscountAmount",
						formatDouble(DiscountAmount
								- PartTotalDiscountAmount, 2));// 优惠保费

			} else {
				double CurerentProducntDiscountAmount = Double
						.parseDouble(formatDouble(Amount
								* DiscountAmount / TotalAmount, 2)); // 当前产品优惠的保费
				mm.put("ActivityeAmount",
						formatDouble(Amount
								- CurerentProducntDiscountAmount, 2));// 活动保费
				mm.put("DiscountAmount", CurerentProducntDiscountAmount
						+ "");// 优惠保费
				PartTotalDiscountAmount += CurerentProducntDiscountAmount;
			}
		}
		return true;
	}

	/**
	 * 订单不参加活动的处理
	 * @param activity_product_info
	 * @param OrderList
	 * @param isExistActivityProduct
	 * @param productMap
	 * @return
	 */
	private boolean dealNoActivity(Map<String, Map<String, Object>> activity_product_info, List<SDOrder> OrderList, List<String> isExistActivityProduct, Map<String, String> productMap) {
		List<Map<String, String>> NoActivityList = new ArrayList<Map<String, String>>();
		Map<String, Object> noJoinActivityInfo = new HashMap<String, Object>();
		Map<String, String> product;
		double TotalAmount = 0.0f;
		String tempTotalAmount;
		for (SDOrder sdorder : OrderList) {
			// 订单不参加活动
			if (!isExistActivityProduct.contains(sdorder.getOrderSn())) {
				product = new HashMap<String, String>();
				tempTotalAmount = formatDouble(sdorder.getTotalAmount().doubleValue(), 2);
				product.put("Amount", tempTotalAmount);
				product.put("ActivityeAmount", tempTotalAmount);
				product.put("DiscountAmount", "0.00");
				product.put("OrderSn", sdorder.getOrderSn());
				product.put("ProductID", productMap.get(sdorder.getOrderSn()));
				TotalAmount += sdorder.getTotalAmount().doubleValue();
				NoActivityList.add(product);
			}
			Map<String, Object> mActivityAmont = new HashMap<String, Object>();
			mActivityAmont.put("TotalAmount", formatDouble(TotalAmount, 2));
			mActivityAmont.put("DiscountAmount", "0.00");
			mActivityAmont.put("RealAmount", formatDouble(TotalAmount, 2));
			noJoinActivityInfo.put("ActivityAmont", mActivityAmont);
		}
		// 如果产品不存在活动则默认活动号为“no-activity”
		if (NoActivityList != null && NoActivityList.size() != 0) {
			noJoinActivityInfo.put("ProductInfo", NoActivityList);

			Map<String, String> mActivityContent = new HashMap<String, String>();
			mActivityContent.put("activitySn", "无");
			mActivityContent.put("title", "无");
			mActivityContent.put("description", "无");
			mActivityContent.put("accumulation", "-1");
			mActivityContent.put("type", "-1");
			noJoinActivityInfo.put("ActivityInfo", mActivityContent);

			activity_product_info.put(NO_ACTIVITY, noJoinActivityInfo);
		}
		return true;
	}

	/**
	 * 拼装活动保费计算sql-折扣、积分
	 *
	 * @param ProductInfo
	 * @param ActionID
	 * @param Channel
	 * @return
	 */
	private String getProductActivitySql(
			List<Map<String, String>> ProductInfoList, int ActionID,
			String Channel) {
		String ProductIDStr = "";
		for (int i = 0; i < ProductInfoList.size(); i++) {
			Map<String, String> ProductInfo = ProductInfoList.get(i);
			ProductIDStr += " s1.ProductId = '" + ProductInfo.get("ProductID")
					+ "' ";
			if (i != ProductInfoList.size() - 1) {
				ProductIDStr += " or ";
			}
		}

		StringBuffer ProductActivityInfo = new StringBuffer();
		ProductActivityInfo
				.append(" select s1.ProductID,s2.title,s2.description, s2.type ,s3.ActivityData,s2.GroupbuyWhether,s2.GroupbuyNum,s2.memberChannel,s3.MemberRule  ");
		ProductActivityInfo
				.append(" from SdProductActivityLink s1,sdcouponactivityinfo s2,SdActivityRule s3");
		ProductActivityInfo.append(" where  ( ").append(ProductIDStr)
				.append(" )");
		// 1表示计算全部活动,3表示计算折扣和积分 ,其他余留以后使用
		switch (ActionID) {
		case 1:
			ProductActivityInfo.append(" and  s2.type='6' "); // 只限折扣
			break;
		case 3:
			ProductActivityInfo.append(" and  ( s2.type='6' or s2.type='7') "); // 折扣、积分
			break;
		default:
			break;
		}
		ProductActivityInfo
				.append(" and  status='3' and  s1.ActivitySn=s2.activitySn  and s2.activitySn=s3.activitysn ");
		ProductActivityInfo.append(" and  s2.starttime <='")
				.append(PubFun.getCurrent()).append("'");
		ProductActivityInfo.append(" and  s2.endtime >='")
				.append(PubFun.getCurrent()).append("'");
		ProductActivityInfo.append(" and  s1.ActivityChannel = '")
				.append(Channel).append("'");
		ProductActivityInfo
				.append(" order by s1.ProductID , find_in_set(s2.type,'6,3,2,1,7') ");
		return ProductActivityInfo.toString();
	}

	/**
	 * 根据活动编码，判断活动信息
	 *
	 * @param activitySn
	 * @return
	 */
	/*private DataTable getActivityRuleData(double totalAmount, String activitySn) {
		QueryBuilder qb = new QueryBuilder(
				"select StartAmount,EndAmount,ActivityData from SdActivityRule where activitySn = ?");
		qb.add(activitySn);
		if (totalAmount > 0) {
			qb.append(" and StartAmount<=?", totalAmount);
			qb.append(" and EndAmount>?", totalAmount);
		}
		return qb.executeDataTable();
	}*/

	/**
	 * 根据活动编码，判断活动信息 (会员频道 特价产品 价格对应)
	 *
	 * @param totalAmount
	 * @param activitySn
	 * @param memberId
	 * @return
	 */
	private DataTable getActivityRuleData(double totalAmount, String activitySn, String memberId) {
		QueryBuilder qb = new QueryBuilder(
				"select StartAmount,EndAmount,ActivityData from SdActivityRule where activitySn = ?");
		qb.add(activitySn);
		if (totalAmount > 0) {
			qb.append(" and StartAmount<=?", totalAmount);
			qb.append(" and EndAmount>?", totalAmount);
		}
		// 会员频道 特价产品 优惠对应
		if (StringUtil.isNotEmpty(memberId)) {
			memberSchema memberSchema = new memberSchema();
			memberSchema.setid(memberId);
			if (memberSchema.fill()) {
				String grade = memberSchema.getgrade();
				if ("Y".equals(memberSchema.getvipFlag())) {
					grade = "VIP";
				}
				qb.append(" and (find_in_set(?, MemberRule) or MemberRule is null or MemberRule = '')", grade);
			}
		}
		qb.append(" order by ActivityData desc limit 1");
		return qb.executeDataTable();
	}

	/**
	 * <b>产品折扣计算,保留小数点后2位 </b>
	 *
	 * <b>@param Discount 折扣率</b>
	 *
	 * <b>@param Amount 保费 </b>
	 *
	 * @return
	 */
	public BigDecimal CalculateDiscount(String Discount, String Amount) {
		return new BigDecimal(Double.parseDouble(Discount)
				* Double.parseDouble(Amount) / DISCOUNT_RATE).setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * <b>产品积分计算,保留整数 </b>
	 *
	 * <b>@param Discount 折扣率</b>
	 *
	 * <b>@param Amount 保费 </b>
	 *
	 * @return
	 */
	public BigDecimal CalculateIntegral(String Discount, String Amount) {
		return new BigDecimal(Double.parseDouble(Discount)
				* Double.parseDouble(Amount))
				.setScale(0, BigDecimal.ROUND_DOWN);
	}

	/**
	 * <b>产品积分计算,保留整数 ,四舍五入</b>
	 *
	 * <b>@param Discount 折扣率</b>
	 *
	 * <b>@param Amount 保费 </b>
	 *
	 * @return
	 */
	public BigDecimal CalculateIntegral(String Discount, String GiveDiscount,
			String Amount) {
		return new BigDecimal(Double.parseDouble(Discount)
				* Double.parseDouble(GiveDiscount) * Double.parseDouble(Amount)
				* Double.parseDouble(Config.getValue("PointScalerUnit")))
				.setScale(0, BigDecimal.ROUND_HALF_UP);
	}


	/**
	 * <b>产品折扣计算,入 </b>
	 *
	 * <b>@param Discount 折扣率</b>
	 *
	 * <b>@param Amount 保费 </b>
	 *
	 * @return
	 */
	public BigDecimal CalculateDiscount(String Discount, String GiveDiscount, String Amount) {

		java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.000");

		String str = myformat.format(new BigDecimal(Double
				.parseDouble(Discount)
				* Double.parseDouble(Amount)
				* Double.parseDouble(GiveDiscount) ));

		return new BigDecimal(str).setScale(1, BigDecimal.ROUND_UP);
	}
	/**
	 * <b>产品折扣计算,入 </b>
	 *
	 * <b>@param Discount 折扣率</b>
	 *
	 * <b>@param Amount 保费 </b>
	 *
	 * @return
	 */
	public BigDecimal CalculateDiscountPrice(String Discount, String GiveDiscount, String Amount) {

		java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.000");

		String str = myformat.format(new BigDecimal(Double
				.parseDouble(Discount)
				* Double.parseDouble(Amount)
				* Double.parseDouble(GiveDiscount) / DISCOUNT_RATE));

		return new BigDecimal(str).setScale(1, BigDecimal.ROUND_UP);
	}

	public String formatDouble(double amount, int newScale) {
		return new BigDecimal(amount).setScale(newScale,
				BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * <b>折扣，高倍积分判断</b>
	 *
	 * <b>判断登录用户是否满足活动参加标准</b>
	 * <b>@param memberrule  活动所包含等级</b>
	 * <b>@param memberChannel 是否按照会员渠道划分 是：Y 不是N/null </b>
	 * @return
	 */
	public boolean memberGradeShow(String memberrule,String memberChannel,String memberGrade){
		if(StringUtil.isEmpty(memberChannel)&&!"Y".equals(memberChannel)){
			return true; //老逻辑，不按会员等级份折扣/高积分
		}
		if(StringUtil.isEmpty(memberGrade)){//未登录
			return false;
		 }else if(StringUtil.isEmpty(memberrule)){//未设置权限
			 return false;
		 }else{
			 if(memberrule.indexOf(memberGrade)==-1){//登陆的会员等级不符合活动设置的会员等级
				 return false;
			 }
		 }
		return true;
	}


}
