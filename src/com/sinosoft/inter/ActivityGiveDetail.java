package com.sinosoft.inter;

import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDOrder;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.SDCouponInfoSchema;
import com.sinosoft.schema.SDCouponInfoSet;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.SDIntCalendarSet;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.weixin.WeiXinCommon;
import net.sf.json.JSONArray;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ActivityGiveDetail {
	private static final Logger logger = LoggerFactory.getLogger(ActivityGiveDetail.class);

	private static String authorizeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=!!!&redirect_uri=@@@&response_type=code&scope=snsapi_base&state=WDBD#wechat_redirect";

	static Properties props = new Properties();
	
	static {
		try {
		    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("weixin.properties");
		    InputStreamReader rd = new InputStreamReader(is, "UTF-8");
		    props.load(rd);
		    rd.close();
		    is.close();
		} catch (IOException e) {
		    logger.error(e.getMessage(), e);
		}
	}
	
public static void main(String[] args) {
		
		List<SDOrder> paramterList = new ArrayList<SDOrder>();
		SDOrder tSDOrder = new SDOrder();
		tSDOrder.setOrderSn("2013000011112710");
		tSDOrder.setPayPrice("120");
		paramterList.add(tSDOrder);

		// tSDOrder = new SDOrder();
		// tSDOrder.setOrderSn("2013000011112698");
		// tSDOrder.setPayPrice("262.25");
		// paramterList.add(tSDOrder);
		//
		// tSDOrder = new SDOrder();
		// tSDOrder.setOrderSn("2013000011112699");
		// tSDOrder.setPayPrice("1647.75");
		// paramterList.add(tSDOrder);

		ActivityGiveDetail agd = new ActivityGiveDetail();
		// 满送
		// agd.buySendActivityInfo(paramterList,
		// "8a48715a3eeeaa0f013f02ca71e80955", "wj");

		// 积分
		//agd.buyCalculateIntegral("W140800002080012000D", "wj");

		// 支付页面，满送、买送信息获取
		// agd.buySendActivityInfo(paramterList,
		// "8a48715a3b896e07013b8a7779270001", "wj");

		// agd.policyPriceSplit("W140800001490000000D");

		agd.transPointToMember("W150900000080009150G");
	}

	/**
	 * <b>积分赠送、数据直接落到会员信息上 .</b> <br/>
	 * 
	 * <b>积分去整，舍</b>
	 */
	public void buyCalculateIntegral(String paySn, String Channel) {
		// 1、根据订单获取产品信息，判断产品是否满足活动
		String ordersql = " select s1.orderSn,s1.payPrice,s3.GivePoints,s2.ProductID ";
		ordersql += " from sdorders s1,sdinformation s2 left join SDProductPointRate s3 on s2.productid = s3.ProductID ";
		ordersql += " where paysn=? and s1.ordersn=s2.ordersn ";

		QueryBuilder orderQB = new QueryBuilder(ordersql, paySn);
		DataTable orderDT = orderQB.executeDataTable();

		if (orderDT == null || orderDT.getRowCount() == 0) {
			return;
		}

		String ordersn = "";
		for (int i = 0; i < orderDT.getRowCount(); i++) {
			ordersn += " s0.ordersn='" + orderDT.get(i).getString("orderSn") + "'";
			if (i != orderDT.getRowCount() - 1) {
				ordersn += " or";
			}
		}

		StringBuffer sb = new StringBuffer();
		sb.append(" select s0.orderSn, s5.ActivityData,s5.ActivitySn,s2.GroupbuyWhether,s2.GroupbuyNum,sd.memberId,s2.memberChannel,s5.MemberRule  ");
		sb.append(" from sdinformation s0, sdorders sd, SdProductActivityLink s1,sdcouponactivityinfo s2 ,SdActivityRule s5  ");
		sb.append(" where  (").append(ordersn);
		sb.append(" ) and s0.orderSn=sd.orderSn and s2.type='7' and  s2.status='3' and s0.productid=s1.productid  ");
		sb.append(" and  s1.ActivitySn=s2.activitySn    and s2.activitySn=s5.activitysn  ");
		sb.append(" and  s2.starttime <='").append(PubFun.getCurrent()).append("'");
		sb.append(" and  s2.endtime >='").append(PubFun.getCurrent()).append("'");
		sb.append(" and  s1.ActivityChannel='").append(Channel).append("'");

		DataTable OrderActivityData = new QueryBuilder(sb.toString()).executeDataTable();

		Mapx<String, String> GiveMap = orderDT.toMapx("orderSn", "GivePoints");

		// 2、首先根据正常计算积分
		Map<String, Integer> IntegralMap = new HashMap<String, Integer>();
		Map<String, String> OrderPrice = new HashMap<String, String>();
		for (int i = 0; i < orderDT.getRowCount(); i++) {
			DataRow orderDR = orderDT.get(i);

			IntegralMap.put(orderDR.getString("orderSn"), calculateIntegral(orderDR.getString("payPrice") + "", ActivityCalculateDetail.PRODUCT_INTEGRAL_RATE, orderDR.getString("GivePoints")));

			OrderPrice.put(orderDR.getString("orderSn"), orderDR.getString("payPrice"));

		}
		//以活动为单位，统计同一活动的订单集合
		Map<String,String> map_group=new HashMap<String,String>();
		Mapx<String, String> GroupNumMap=OrderActivityData.toMapx("orderSn", "ActivitySn");
		Set sGroupNumMap = GroupNumMap.entrySet();
		Iterator<Map.Entry<String, String>> GroupNumMap_Iter = sGroupNumMap.iterator();
		while (GroupNumMap_Iter.hasNext()) {
			Map.Entry<String, String> entry = GroupNumMap_Iter.next();
			String value=entry.getValue();
			String key=entry.getKey();
			if(map_group.containsKey(value)){
				String orderstr=map_group.get(value);
				map_group.put(value, orderstr+"'"+key+"',");
			}else{
				map_group.put(value, "'"+key+"',");
			}
		}
		// 3、根据活动计算积分
		if (OrderActivityData != null && OrderActivityData.getRowCount() > 0) {
			String memberId = OrderActivityData.getString(0, "memberId");
			int rowCount = OrderActivityData.getRowCount();
			// 取得会员等级
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
		
			for (int i = 0; i < rowCount; i++) {
				DataRow dr = OrderActivityData.get(i);
				// 会员频道的活动
				if ("Y".equalsIgnoreCase(dr.getString("memberChannel"))) {
					// 无会员等级的或会员等级不满足活动设置不享受此活动
					if (StringUtil.isEmpty(grade) || (StringUtil.isNotEmpty(dr.getString("MemberRule")) && !dr.getString("MemberRule").contains(grade))) {
						OrderActivityData.deleteRow(i);
						i--;
						rowCount--;
						continue;
					}
				}
				
							
				String ActivitySn=dr.getString("ActivitySn");
				String GroupbuyWhether=dr.getString("GroupbuyWhether");
				String GroupbuyNum=dr.getString("GroupbuyNum");
				if("1".equals(GroupbuyWhether)){
					//保单数
					String risktype_num="0";
					if(StringUtil.isNotEmpty(dr.getString("orderSn"))){
						String ordersns=map_group.get(ActivitySn);
						if(StringUtil.isNotEmpty(ordersns)){
							risktype_num=new QueryBuilder("select sum(1)  from   sdinformationrisktype where ordersn in ("+ordersns.substring(0,ordersns.length()-1)+")").executeString();
						}
						if(StringUtil.isEmpty(risktype_num)){
							risktype_num="0";
						}
					}
					if(Integer.parseInt(risktype_num)>=Integer.parseInt(GroupbuyNum)){
						IntegralMap.put(dr.getString("orderSn"), calculateIntegral(OrderPrice.get(dr.getString("orderSn")), dr.getDouble("ActivityData"), GiveMap.getString(dr.getString("orderSn"))));
					}
				}else{
					IntegralMap.put(dr.getString("orderSn"), calculateIntegral(OrderPrice.get(dr.getString("orderSn")), dr.getDouble("ActivityData"), GiveMap.getString(dr.getString("orderSn"))));
				}
	
			}
		}

		// 4、更新订单相关数据
		Transaction tran = new Transaction();
		Set<Map.Entry<String, Integer>> sIntegralMap = IntegralMap.entrySet();
		Iterator<Map.Entry<String, Integer>> sIntegralMap_Iter = sIntegralMap.iterator();
		while (sIntegralMap_Iter.hasNext()) {
			Map.Entry<String, Integer> entry = sIntegralMap_Iter.next();
			tran.add(new QueryBuilder("update  sdinformation set point=? where  ordersn=?", entry.getValue(), entry.getKey()));
			logger.info("积分赠送 订单号==>{} 赠送的积分==>{}", entry.getKey(), entry.getValue());
		}

		if (!tran.commit()) {
			logger.error("积分赠送提交异常");
			return;
		}

	}
	
	/**
	 * 下单后，冻结积分处理 sdorderList 订单
	 * 
	 */
	public void transPointToMember(String paySn) {
		try {
			String sql = "select s2.id,s1.orderSn,s2.point,s2.pointStatus,s1.memberId,s1.OrderStatus,s2.startDate,s1.paySn,s1.payPrice,s2.productId from sdorders s1,SDinformation  s2 where s1.OrderStatus='7' ";
			sql += "and memberid is not null  and  s1.ordersn=s2.ordersn ";
			sql += " and s1.paysn=? ";
	
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(paySn);
	
			DataTable dt = qb.executeDataTable();
	
			if (dt == null || dt.getRowCount() == 0) {
				return;
			}
			// 同一个交易为一个会员
			memberSchema tmemberSchema = new memberSchema();
			tmemberSchema.setid(dt.getString(0, "memberId"));
			if (!tmemberSchema.fill()) {
				return;
			}
			String recommendMemId = tmemberSchema.getrecommendMemId();
			// 取得总支付金额
			BigDecimal unit = new BigDecimal(Config.getValue("PointScalerUnit"));
			BigDecimal tradeAmount = new BigDecimal(0);
			List<Map<String,Object>> productToPointRates = new ArrayList<Map<String,Object>>();
			List<String> products = new ArrayList<String>();
			for (int i = 0; i < dt.getRowCount(); i++) {
				String point_result=dt.getString(i, "point");
				if (StringUtil.isNotEmpty(point_result) && Integer.valueOf(point_result) > 0) {
					tradeAmount = tradeAmount.add(new BigDecimal(dt.getString(i, "payPrice")));
				}
				Map<String,Object> productToPointRate = new HashMap<String,Object>();
				productToPointRate.put("amount", dt.getString(i, "payPrice"));
				productToPointRate.put("productid", dt.getString(i, "productId"));
				products.add(dt.getString(i, "productId"));
				productToPointRates.add(productToPointRate);
			}
			String basePoint = tradeAmount.multiply(unit).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			int totalpoint = 0;
			
			// 计算会员等级加成
			//会员等级
			
			// 查询产品积分规则信息
			DataTable dtProductPointRule = PointsCalculate.getProductList(products);
			Map<String,Object> rateMap = dtProductPointRule.toMapx("ProductID", "GivePoints");
			
			for(Map<String,Object> pr : productToPointRates){
				pr.put("rate", rateMap.get(pr.get("productid")));
			}
			
			Map<String,String>  map_result_grade=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BUY,dt.getString(0, "memberId"), basePoint, productToPointRates);
			String flag_grade=map_result_grade.get("flag");
			String addpoints_grade=map_result_grade.get("addpoints");
			String PointDes_grade=map_result_grade.get("PointDes");
			String MemberGrade_grade=map_result_grade.get("MemberGrade");
			int addpoin_grade = 0;
			int addpoin_grade_last = 0;
			if("true".equals(flag_grade)){
				addpoin_grade = Integer.valueOf(addpoints_grade);
				addpoin_grade_last = Integer.valueOf(addpoints_grade);
				totalpoint += addpoin_grade_last;
			}
			//会员生日月
			Map<String,String>  map_result=ActivityCalculate.getMemberGradeBirthdayPoints(IntegralConstant.POINT_SOURCE_BIRTH_MONTH,dt.getString(0, "memberId"), basePoint, productToPointRates);
			String flag=map_result.get("flag");
			String addpoints=map_result.get("addpoints");
			String PointDes=map_result.get("PointDes");
			int addpoin = 0;
			int addpoin_last = 0;
			if("true".equals(flag)){
				addpoin = Integer.valueOf(addpoints);
				addpoin_last = Integer.valueOf(addpoints);
				totalpoint += addpoin_last;
			}
			
			int dealCount=0;
			do {
				Transaction transaction = new Transaction();
				SDIntCalendarSet tSDIntCalendarSet = new SDIntCalendarSet();
				String memberId;
				String orderSn;
				String startDate;
				String memSql = "";
				// 取得积分描述
				String pointsDesc = getPointDesc(IntegralConstant.POINT_SOURCE_BUY);
				for (int i = 0; i < dt.getRowCount(); i++) {
					String point_result=dt.getString(i, "point");
					memberId = dt.getString(i, "memberId");
					orderSn = dt.getString(i, "orderSn");
					startDate = dt.getString(i, "startDate");
					// 赠送当前会员
					tSDIntCalendarSet.add(dealSDIntCalendar(memberId, point_result, IntegralConstant.POINT_SOURCE_BUY, orderSn, pointsDesc, startDate, null));
					// 查询会员更新会员冻结积分
					if (StringUtil.isNotEmpty(point_result)) {
						totalpoint += Integer.parseInt(point_result);
					} else {
						point_result = "0";
					}
					try {
						if ("true".equals(flag_grade) && Integer.valueOf(point_result) > 0) {
							
							int integral = (new BigDecimal(dt.getString(i, "payPrice")).multiply(new BigDecimal(addpoin_grade))).divide(tradeAmount, 0, BigDecimal.ROUND_HALF_UP).intValue();
							if (i == dt.getRowCount() - 1) {
								integral = addpoin_grade_last;
							} else {
								addpoin_grade_last -= integral;
							}
							
							point_result = String.valueOf(Integer.parseInt(point_result)+integral);
							tSDIntCalendarSet.add(dealSDIntCalendar(memberId, integral + "", IntegralConstant.POINT_SOURCE_BUY, orderSn, PointDes_grade, startDate, MemberGrade_grade));
							
							//拆分订单会员生日月累加积分
							orderIntegerSplit(orderSn, integral+"",transaction,tmemberSchema);
						}
					} catch (Exception e) {
						logger.error("会员等级查询更新有异常，memberid="+dt.getString(0, "memberId") + e.getMessage(), e);
					}
					try {
						if("true".equals(flag) && Integer.valueOf(point_result) > 0){
							int integral = (new BigDecimal(dt.getString(i, "payPrice")).multiply(new BigDecimal(addpoin))).divide(tradeAmount, 0, BigDecimal.ROUND_HALF_UP).intValue();
							if (i == dt.getRowCount() - 1) {
								integral = addpoin_last;
							} else {
								addpoin_last -= integral;
							}
							
							point_result = String.valueOf(Integer.parseInt(point_result)+integral);
							memSql = ", birthYear='"+DateUtil.getCurrentDate("yyyy-MM")+"' ";
							tSDIntCalendarSet.add(dealSDIntCalendar(memberId, integral+"", IntegralConstant.POINT_SOURCE_BIRTH_MONTH, orderSn, PointDes, startDate, DateUtil.getCurrentDate("yyyy-mm")));
							//拆分订单会员生日月累加积分
							orderIntegerSplit(dt.getString(i, "orderSn"), addpoints,transaction,tmemberSchema);
						}
					} catch (Exception e) {
						logger.error("会员生日月查询更新有异常，memberid="+dt.getString(0, "memberId") + e.getMessage(), e);
					}
					// 同时更新积分状态
					transaction.add(new QueryBuilder("update SDinformation set pointStatus = '1',point=? where id =  ? ",point_result,dt.getString(i, "id")));
				}
			
				QueryBuilder memQb;
				if (StringUtil.isEmpty(tmemberSchema.getversion())) {
					memQb = new QueryBuilder("update member set point=?, version='1', modifyDate=?"+memSql+" where id=? and (version is null or version='')");
					memQb.add(tmemberSchema.getpoint() + totalpoint);
					memQb.add(DateUtil.getCurrentDateTime());
					memQb.add(tmemberSchema.getid());
				} else {
					memQb = new QueryBuilder("update member set point=?, version=version+1, modifyDate=?"+memSql+" where id=? and version=?");
					memQb.add(tmemberSchema.getpoint() + totalpoint);
					memQb.add(DateUtil.getCurrentDateTime());
					memQb.add(tmemberSchema.getid());
					memQb.add(tmemberSchema.getversion());
				}
				transaction.add(memQb);
				transaction.add(tSDIntCalendarSet, Transaction.INSERT);
				if (!transaction.commitRoll()) {
					dealCount++;
					tmemberSchema = new memberSchema();
					tmemberSchema.setid(dt.getString(0, "memberId"));
					tmemberSchema.fill();
					recommendMemId = tmemberSchema.getrecommendMemId();
				} else {
					break;
				}
			
			} while(dealCount < Constant.UPDATE_MEMBER_DEAL_COUNT);
			
			if (dealCount >= Constant.UPDATE_MEMBER_DEAL_COUNT) {
				logger.error("类ActivityGiveDetail执行方法transPointToMember()冻结积分处理更新数据库失败！");
			}
			
			if (StringUtil.isNotEmpty(recommendMemId)) {
				dealRecommMemPoints(recommendMemId, paySn, dt);
			}
		} catch(Exception e) {
			logger.error("类ActivityGiveDetail执行方法transPointToMember()冻结积分处理发生异常！" + e.getMessage(), e);
		}
	}
	
	private boolean dealRecommMemPoints(String recommendMemId, String paySn, DataTable dt) {
		try {
			int dealCount = 0;
			do {
				// 人数限制空为不限制，0为不送，其他正常
				String RecommBuyCount=Config.getValue("RecommBuyCount");
				if(StringUtil.isNotEmpty(RecommBuyCount)){
					int RecommBuyCounts = Integer.parseInt(RecommBuyCount);
					if (RecommBuyCounts == 0) {
						return true;
					}
					// 判断其推荐的用户已经购买的人数，isBuy
					String isBuyCount = "select count(1) from member where isBuy='Y' and recommendMemId=? ";
					QueryBuilder isBuyCount_qb = new QueryBuilder(isBuyCount);
					isBuyCount_qb.add(recommendMemId);
					if (isBuyCount_qb.executeInt() >= RecommBuyCounts) {
						return true;
					}
				}
				memberSchema tmemberSchema = new memberSchema();
				tmemberSchema.setid(dt.getString(0, "memberId"));
				if (!tmemberSchema.fill()) {
					return true;
				}
				
				// 判断会员是否为第一次购买
				String firstpay = "select count(1) from sdorders where orderstatus in (7,10,12) and paysn!=? and memberid=?";
				QueryBuilder firstpay_qb = new QueryBuilder(firstpay);
				firstpay_qb.add(paySn);
				firstpay_qb.add(tmemberSchema.getid());
				if (firstpay_qb.executeInt() != 0) {
					return true;
				}
		
				memberSchema tmemberschema_recommendmem = new memberSchema();
				tmemberschema_recommendmem.setid(recommendMemId);
				if (!tmemberschema_recommendmem.fill()) {
					return true;
				}
				// 推荐比例
				String RecommBuyPoint = Config.getValue("RecommBuyPoints");
				Map<String, String> RecommSpecialProductPoint = new HashMap<String, String>();
				
				
				double RecommBuyPoints=0;
				if(StringUtil.isNotEmpty(RecommBuyPoint)){
					RecommBuyPoints=Double.parseDouble(RecommBuyPoint);
					DataTable zdcodeDt = new QueryBuilder(" SELECT codevalue,memo FROM zdcode  WHERE codetype='RecommSpecialProductPoint' AND parentcode='RecommSpecialProductPoint' ").executeDataTable();
					if (zdcodeDt != null && zdcodeDt.getRowCount() > 0) {
						for(int x=0; x<zdcodeDt.getRowCount(); x++){
							RecommSpecialProductPoint.put(zdcodeDt.getString(x, "codevalue"), zdcodeDt.getString(x, "memo"));
						}
					}
				}else{
					return true;
				}
				// 推荐购买赠送积分总数限制
				String RecommBuyTotalPoint = Config.getValue("RecommBuyTotalPoints");
		
				Transaction trans_recommend = new Transaction();
				SDIntCalendarSet ic_recommend = new SDIntCalendarSet();
				int totalpoint_recommend = 0;
				String orderSn;
				String startDate;
				SDIntCalendarSchema tSDIntCalendarSchema;
				String pointsDesc = getPointDesc(IntegralConstant.POINT_SOURCE_RECOMMEND_BUY);
				String tmembermobile = tmemberSchema.getmobileNO();
				if (StringUtil.isNotEmpty(tmembermobile)) {
					pointsDesc += " 账号：" + tmembermobile;
				}
				for (int i = 0; i < dt.getRowCount(); i++) {
					orderSn = dt.getString(i, "orderSn");
					startDate = dt.getString(i, "startDate");
					// 赠送当前会员
					
					if(RecommSpecialProductPoint.containsKey(dt.getString(i, "productId"))){
						RecommBuyPoints = Double.parseDouble( RecommSpecialProductPoint.get(dt.getString(i, "productId")));
					}
					int currentpoint = Integer.parseInt(formatDouble(Integer.parseInt(dt.getString(i, "point")) * RecommBuyPoints, 0));
					tSDIntCalendarSchema = dealSDIntCalendar(recommendMemId, currentpoint + "", IntegralConstant.POINT_SOURCE_RECOMMEND_BUY, orderSn, pointsDesc, startDate, null);
					// 查询会员更新会员冻结积分
					if (StringUtil.isNotEmpty(dt.getString(i, "point"))) {
						totalpoint_recommend += currentpoint;
					}
					if(StringUtil.isNotEmpty(RecommBuyTotalPoint)){
						int RecommBuyTotalPoints=Integer.parseInt(RecommBuyTotalPoint);
						if (RecommBuyTotalPoints < totalpoint_recommend) {
							tSDIntCalendarSchema.setIntegral((RecommBuyTotalPoints - totalpoint_recommend + currentpoint) + "");// 积分值
		
							ic_recommend.add(tSDIntCalendarSchema);
							totalpoint_recommend = RecommBuyTotalPoints;
							break;
		
						} else {
							ic_recommend.add(tSDIntCalendarSchema);
						}
					}else{
						ic_recommend.add(tSDIntCalendarSchema);
					}
				}
				
				QueryBuilder memQb;
				if (StringUtil.isEmpty(tmemberschema_recommendmem.getversion())) {
					memQb = new QueryBuilder("update member set point=?, version='1' and modifyDate=? where id=? and (version is null or version='')");
					memQb.add(tmemberschema_recommendmem.getpoint() + totalpoint_recommend);
					memQb.add(DateUtil.getCurrentDateTime());
					memQb.add(tmemberschema_recommendmem.getid());
				} else {
					memQb = new QueryBuilder("update member set point=?, version=version+1, modifyDate=? where id=? and version=?");
					memQb.add(tmemberschema_recommendmem.getpoint() + totalpoint_recommend);
					memQb.add(DateUtil.getCurrentDateTime());
					memQb.add(tmemberschema_recommendmem.getid());
					memQb.add(tmemberschema_recommendmem.getversion());
				}
				
				trans_recommend.add(memQb);
				trans_recommend.add(new QueryBuilder("update member set isBuy='Y' where id=? ", tmemberSchema.getid()));
				trans_recommend.add(ic_recommend, Transaction.INSERT);
				if (!trans_recommend.commitRoll()) {
					dealCount++;
				} else {
					break;
				}
			}while(dealCount < Constant.UPDATE_MEMBER_DEAL_COUNT);
			
			if (dealCount >= Constant.UPDATE_MEMBER_DEAL_COUNT) {
				logger.error("ActivityGiveDetail执行方法dealRecommMemPoints()赠送推荐人积分更新数据库失败.paySn==>{}", paySn);
				return false;
			} else {
				return true;
			}
		}catch(Exception e) {
			logger.error("ActivityGiveDetail执行方法dealRecommMemPoints()赠送推荐人积分处理失败.paySn==>" + paySn + e.getMessage(), e);
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	private String getPointDesc(String act) {
		String pointDesc = "";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PointsGive", IntegralConstant.POINT_GIVE_01);
		try {
			Map<String, Object> map = new PointsCalculate().pointsManage(IntegralConstant.POINT_SEARCH,
					act, param);
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get(IntegralConstant.DATA);
			if (list.size() > 0) {
				pointDesc = String.valueOf(list.get(0).get("PointDes"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return pointDesc;
	}
	
	private SDIntCalendarSchema dealSDIntCalendar(String memberId, String integral, String source, String businessId, String description, String svalidate, String prop3) {
		SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
		tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
		tSDIntCalendarSchema.setMemberId(memberId);// 会员号
		tSDIntCalendarSchema.setIntegral(integral);// 积分值
		tSDIntCalendarSchema.setSource(source);
		tSDIntCalendarSchema.setBusinessid(businessId);// 业务号
		tSDIntCalendarSchema.setManner("0");// 表示收入
		tSDIntCalendarSchema.setStatus("1"); // 保单没有生效冻结
		tSDIntCalendarSchema.setDescription("购买产品冻结积分 订单号："+businessId);
		if (StringUtil.isNotEmpty(description)) {
			tSDIntCalendarSchema.setDescription(description+" 订单号："+businessId);
		}
		
		tSDIntCalendarSchema.setsvaliDate(svalidate);// 有效期起始时间
		tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDIntCalendarSchema.setProp3(prop3);
		
		return tSDIntCalendarSchema;
	}
	
	
	private Map<String, String> tranGradePoint(String paySn, String memberId) {
		Map<String, String> map = new HashMap<String, String>();
		String tradeAmount = new QueryBuilder("select TradeAmount from TradeSummaryInfo where paysn=?", paySn).executeString();
		
		
		return map;
	}
	
	/**
	 * 支付后满送、买送
	 * 
	 * @param sdorderList
	 * @param memberID
	 */
	public void buySendActivityInfo(List<SDOrder> sdorderList, String memberID, String Channel) {
		// 1、根据订单获取产品信息，判断产品是否满足活动
		Map<String, List<DataRow>> mActivityData = getActivityDataByOrderSn(sdorderList, Channel);

		if (mActivityData == null || mActivityData.isEmpty()) {
			return;
		}

		String email = getMemberEmail(sdorderList, memberID);

		// 遍历不同活动
		Set<Map.Entry<String, List<DataRow>>> sActivityData = mActivityData.entrySet();
		Iterator<Map.Entry<String, List<DataRow>>> ssActivityData_Iter = sActivityData.iterator();
		while (ssActivityData_Iter.hasNext()) {
			Map.Entry<String, List<DataRow>> entry = ssActivityData_Iter.next();
			double dActivityTotal = 0f;
			double dActivityStartAmount = 10000000f;
			List<DataRow> dList = entry.getValue();
			String odersn = "";
			String type = "";
			String ActivitySn = "";
			String description = "";
			for (int i = 0; i < dList.size(); i++) {
				dActivityTotal += stirngToDouble(dList.get(i).getString("totalAmount"));
				odersn += dList.get(i).getString("orderSn") + ",";
				if (i == 0) {
					dActivityStartAmount = dList.get(i).getDouble("StartAmount");
					type = dList.get(i).getString("type");
					ActivitySn = dList.get(i).getString("ActivitySn");
					description = dList.get(i).getString("description");
				}
			}

			// 判断金额是否满足活动要求
			if (dActivityTotal >= dActivityStartAmount) {
				//LogUtil.info("满送、买送   活动编码==>" + entry.getKey() + " 订单编码==>" + odersn + " 订单总金额==>" + dActivityTotal + " 活动金额==>" + dActivityStartAmount);
				if ("8".equals(type)) {
					String orders[] = odersn.split(",");
					for (int i = 0; i < orders.length; i++) {
						DataTable dt_order = new QueryBuilder("SELECT diyActivitySn,diyActivityDescription FROM sdorders WHERE ordersn=?", orders[i]).executeDataTable();
						String diyActivitySn = "";
						String diyActivityDescription = "";
						for (int j = 0; j < dt_order.getRowCount(); j++) {
							diyActivitySn = dt_order.getString(0, 0);
							diyActivityDescription = dt_order.getString(0, 1);
						}
						QueryBuilder qb_update_order = new QueryBuilder("update sdorders set diyActivitySn=?,diyActivityDescription=? where ordersn=?");
						if (StringUtil.isEmpty(diyActivitySn)) {
							qb_update_order.add(ActivitySn);
						} else {
							qb_update_order.add(diyActivitySn + "," + ActivitySn);
						}
						if (StringUtil.isEmpty(diyActivityDescription)) {
							qb_update_order.add(description);
						} else {
							qb_update_order.add(diyActivityDescription + ";" + description);
						}
						qb_update_order.add(orders[i]);
						qb_update_order.executeNoQuery();
					}
				} else {
					if(!"7".equals(type)){
						activityDeal(entry.getKey(), email, memberID);
					}
				}
			}
		}
	}

	/**
	 * 支付页面，满送、买送信息获取
	 * 
	 * @param sdorderList
	 * @param memberID
	 */
	public List<Map<String, String>> buySendActivityInfo(List<SDOrder> sdorderList, String Channel) {
		// 1、根据订单获取产品信息，判断产品是否满足活动
		Map<String, List<DataRow>> mActivityData = getActivityDataByOrderSn(sdorderList, Channel);

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		if (mActivityData == null || mActivityData.isEmpty()) {
			return result;
		}

		// 遍历不同活动
		int index = 0;
		Set<Map.Entry<String, List<DataRow>>> sActivityData = mActivityData.entrySet();
		Iterator<Map.Entry<String, List<DataRow>>> ssActivityData_Iter = sActivityData.iterator();
		while (ssActivityData_Iter.hasNext()) {
			Map.Entry<String, List<DataRow>> entry = ssActivityData_Iter.next();
			double dActivityTotal = 0f;
			double dActivityStartAmount = 10000000f;
			List<DataRow> dList = entry.getValue();
			String odersn = "";
			for (int i = 0; i < dList.size(); i++) {
				dActivityTotal += stirngToDouble(dList.get(i).getString("totalAmount"));
				odersn += dList.get(i).getString("orderSn") + ",";
				if (i == 0) {
					dActivityStartAmount = dList.get(i).getDouble("StartAmount");
				}
			}

			if (!"7".equals(dList.get(0).getString("type"))) {
				if (dActivityTotal >= dActivityStartAmount) {// 判断金额是否满足活动要求
					index++;
					logger.info("自定义活动   活动编码==>{} 订单编码==>{}",entry.getKey(), odersn);
					Map<String, String> temp = new HashMap<String, String>();
					temp.put("index", index + "");
					// 自定义活动
					if ("8".equals(dList.get(0).getString("type"))) {
						temp.put("title", dList.get(0).getString("description"));
					} else {
						temp.put("title", dList.get(0).getString("title"));
					}
					temp.put("type", dList.get(0).getString("type"));
					result.add(temp);
				}
			} else {
				index++;
				logger.info("满送、买送、高倍积分   活动编码==>{} 订单编码==>{}", entry.getKey(), odersn);
				Map<String, String> temp = new HashMap<String, String>();
				temp.put("index", index + "");
				temp.put("title", dList.get(0).getString("title"));
				temp.put("type", dList.get(0).getString("type"));
				result.add(temp);

			}
		}

		return result;
	}

	private Map<String, List<DataRow>> getActivityDataByOrderSn(List<SDOrder> sdorderList, String Channel) {
		String ordersn = "";
		for (int i = 0; i < sdorderList.size(); i++) {
			ordersn += " s0.ordersn='" + sdorderList.get(i).getOrderSn() + "'";
			if (i != sdorderList.size() - 1) {
				ordersn += " or";
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" select s4.orderSn ,s4.totalAmount, s4.payPrice ,s5.StartAmount,s5.ActivityData,s5.ActivitySn,s2.title ,s2.type,s2.description,s2.GroupbuyWhether,s2.GroupbuyNum  ");
		sb.append(" from sdinformation s0, SdProductActivityLink s1,sdcouponactivityinfo s2 ,sdorders s4,SdActivityRule s5");
		sb.append(" where  (").append(ordersn);
		sb.append(" ) and s2.type in ('1','2','8','7')");
		sb.append(" and  s2.status='3' and s0.productid=s1.productid  and  s1.ActivitySn=s2.activitySn ");
		sb.append(" and s4.ordersn=s0.ordersn and s2.activitySn=s5.activitysn ");
		sb.append(" and  s2.starttime <='").append(PubFun.getCurrent()).append("'");
		sb.append(" and  s2.endtime >='").append(PubFun.getCurrent()).append("'");
		sb.append(" and  s1.ActivityChannel='").append(Channel).append("'");
		sb.append(" order by createtime ");

		DataTable OrderActivityData = new QueryBuilder(sb.toString()).executeDataTable();
		//以活动为单位，统计同一活动的订单集合
		Map<String,String> map_group=new HashMap<String,String>();
		Mapx<String, String> GroupNumMap=OrderActivityData.toMapx("ActivitySn","orderSn" );
		Set sGroupNumMap = GroupNumMap.entrySet();
		Iterator<Map.Entry<String, String>> GroupNumMap_Iter = sGroupNumMap.iterator();
		while (GroupNumMap_Iter.hasNext()) {
			Map.Entry<String, String> entry = GroupNumMap_Iter.next();
			String value=entry.getValue();
			String key=entry.getKey();
			if(map_group.containsKey(key)){
				String orderstr=map_group.get(key);
				map_group.put(key, orderstr+"'"+value+"',");
			}else{
				map_group.put( key, "'"+value+"',");
			}
		}
		// 2、根据活动，判断产品合计是否满足，如果满足，则赠送优惠卷
		// 3、判断优惠卷类型（autocreate 0 表示自动穿件 1表示非自动创建）
		Map<String, List<DataRow>> mActivityData = new HashMap<String, List<DataRow>>();
		List<DataRow> sActivityDataList = null;
		for (int i = 0; i < OrderActivityData.getRowCount(); i++) {
			DataRow dr = OrderActivityData.get(i);
			String ActivitySn = dr.getString("ActivitySn");
			//String orderSn = dr.getString("orderSn");
			String GroupbuyWhether = dr.getString("GroupbuyWhether");
			String GroupbuyNum = dr.getString("GroupbuyNum");
			if("1".equals(GroupbuyWhether)){
				//保单数
				String risktype_num="0";
				if(StringUtil.isNotEmpty(ordersn)){
					String ordersns=map_group.get(ActivitySn);
					if(StringUtil.isNotEmpty(ordersns)){
						risktype_num=new QueryBuilder("select sum(1)  from   sdinformationrisktype where ordersn in ("+ordersns.substring(0,ordersns.length()-1)+")").executeString();
					}
					if(StringUtil.isEmpty(risktype_num)){
						risktype_num="0";
					}
				}
				if(Integer.parseInt(risktype_num)>=Integer.parseInt(GroupbuyNum)){
					if (mActivityData.get("ActivitySn") == null || mActivityData.get("ActivitySn").size() == 0) {
						sActivityDataList = new ArrayList<DataRow>();
					} else {
						sActivityDataList = mActivityData.get(ActivitySn);
					}
					sActivityDataList.add(dr);
					mActivityData.put(ActivitySn, sActivityDataList);
				}
			}else{
				if (mActivityData.get("ActivitySn") == null || mActivityData.get("ActivitySn").size() == 0) {
					sActivityDataList = new ArrayList<DataRow>();
				} else {
					sActivityDataList = mActivityData.get(ActivitySn);
				}
				sActivityDataList.add(dr);
				mActivityData.put(ActivitySn, sActivityDataList);
			}
		}
		return mActivityData;
	}

	/**
	 * 获取会员，或者投保人邮箱
	 * 
	 * @param sdorderList
	 * @param memberID
	 * @return
	 */
	private String getMemberEmail(List<SDOrder> sdorderList, String memberID) {
		// 判断是否登陆、已登录以及用户邮箱信息不为空的情况
		String email = "";
		if (StringUtil.isNotEmpty(memberID)) {
			QueryBuilder qb_member = new QueryBuilder("SELECT email FROM member WHERE id=?", memberID);
			email = qb_member.executeString();
		}

		// 未登陆或邮箱信息为空，取得投保人邮箱信息，以便发送邮件
		if (StringUtil.isEmpty(email)) {
			String emailSql = " select s1.ApplicantMail ";
			emailSql += " from sdinformationappnt s1,SDInformation s2 ";
			emailSql += " where s2.ordersn=? and s1.informationSn=s2.informationSn   limit 1 ";
			QueryBuilder qb = new QueryBuilder(emailSql);
			qb.add(sdorderList.get(0).getOrderSn());
			email = qb.executeString();
		}
		return email;
	}

	/**
	 * couponRemind:优惠券消息提醒顺序 微信 手机短信 邮箱. <br/>
	 *
	 * @author wwy
	 * @param couponSn
	 * @param memberid
	 * @return
	 */
	public Map<String, Object> couponRemind(String couponSn, String memberid, String name) {

		String msg = "发放成功！";
		SDCouponInfoSchema sdcouponschema = new SDCouponInfoSchema();
		SDCouponInfoSet couponInfoSet = sdcouponschema.query(new QueryBuilder("where couponSn = ?", couponSn));
		// 查询
		if (null == couponInfoSet || couponInfoSet.size() == 0) {
			return PubFun.errMsg("无效优惠券!");
		}
		sdcouponschema = couponInfoSet.get(0);

		// 是否发送提醒
		String remindFlag = sdcouponschema.getRemindFlag();
		if (!"Y".equals(remindFlag)) {
			return PubFun.sucMsg(msg);
		}

		memberSchema memberSchema = new memberSchema();
		memberSchema.setid(memberid);
		if (!memberSchema.fill()) {
			return PubFun.errMsg("无效会员id!");
		}
		
		if (StringUtil.isEmpty(name)) {
			name = StringUtil.isNotEmpty(memberSchema.getusername()) ? memberSchema.getusername() :
				StringUtil.isNotEmpty(memberSchema.getmobileNO()) ? memberSchema.getmobileNO() : memberSchema.getemail();
		}
		
		Transaction trans = new Transaction();
		// 微信提醒
		Map<String, Object> wxMap = wxSend(memberid, sdcouponschema, name);
		
		if (cn.com.sinosoft.util.Constant.SUCCESS.equals(wxMap.get(cn.com.sinosoft.util.Constant.STATUS))) {
			msg = "发放成功, 微信消息提醒！";
			sdcouponschema.setMail((String) wxMap.get(cn.com.sinosoft.util.Constant.DATA));
		} else {
			if (StringUtil.isNotEmpty(memberSchema.getmobileNO()) && StringUtil.isMobileNO(memberSchema.getmobileNO())) {
				// 手机短信提醒
				Map<String, Object> mobileMap = sendMobileMessage(sdcouponschema, memberSchema.getmobileNO());
				if (cn.com.sinosoft.util.Constant.SUCCESS.equals(mobileMap.get(cn.com.sinosoft.util.Constant.STATUS))) {
					msg = "发放成功, 短信消息提醒！";
					sdcouponschema.setMail(memberSchema.getmobileNO());
				}
				// 邮件提醒
				else {
					Map<String, Object> mailMap = activitySendMail(sdcouponschema, memberSchema.getemail());

					if (cn.com.sinosoft.util.Constant.SUCCESS.equals(mailMap.get(cn.com.sinosoft.util.Constant.STATUS))) {
						msg = "发放成功, 邮件消息提醒！";
						sdcouponschema.setMail(memberSchema.getemail());
					} else {
						return PubFun.errMsg("消息提醒失败!");
					}
				}
			} else {
				// 邮件提醒
				Map<String, Object> mailMap = activitySendMail(sdcouponschema, memberSchema.getemail());

				if (cn.com.sinosoft.util.Constant.SUCCESS.equals(mailMap.get(cn.com.sinosoft.util.Constant.STATUS))) {
					msg = "发放成功, 邮件消息提醒！";
				} else {
					return PubFun.errMsg("消息提醒失败!");
				}
			}
		}
		trans.add(sdcouponschema, Transaction.UPDATE);
		trans.commit();
		return PubFun.sucMsg(msg);
	}
	
	/**
	 * wxSend:优惠券微信公众号提醒. <br/>
	 *
	 * @author wwy
	 * @return
	 */
	private Map<String, Object> wxSend (String memberid, SDCouponInfoSchema sdcouponschema, String name) {
		// 微信推送
		QueryBuilder qbWxbind = new QueryBuilder("select openid from wxbind where memberid = ? ", memberid);
		String openid = qbWxbind.executeString();
		if (StringUtil.isNotEmpty(openid)) {

			// 发送微信消息数据
			Map<String, Object> sendData = new HashMap<String, Object>();
			sendData.put("openid", openid);
			sendData.put("name", name);

			return sendWXMessage(sdcouponschema, sendData);
		}
		return PubFun.errMsg("");
	}

	private Map<String, Object> sendMobileMessage(SDCouponInfoSchema sdcouponschema, String mobileNO){
		String endtime = String.valueOf(sdcouponschema.getEndTime());
		String month = endtime.substring(5, 7);
		if (month.startsWith("0")) {
			month = month.substring(1, 2);
		}
		String day = endtime.substring(8, 10);
		if (day.startsWith("0")) {
			day = day.substring(1, 2);
		}
		String sendData = null;
		String actionId = null;
		// 非折扣券
		if (StringUtil.isEmpty(sdcouponschema.getProp3()) || "01".equals(sdcouponschema.getProp3())) {
			sendData = sdcouponschema.getParValue() 
					+ ";" + endtime.substring(0, 4) + "-" + month + "-" + day;
			actionId = "wj00090";
		} else {
			sendData = sdcouponschema.getProp4() 
					+ ";" + endtime.substring(0, 4) + "-" + month + "-" + day;
			actionId = "wj00112";
		}
		if (ActionUtil.sendSms(actionId, mobileNO, sendData)) {
			return PubFun.sucMsg();
		} else {
			return PubFun.errMsg("短信发送失败！");
		}
	}

	/**
	 * sendWXMessage:发送微信模板消息. <br/>
	 *
	 * @author wwy
	 * @param sdcouponschema
	 * @param memberid
	 * @param openid
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Map<String, Object> sendWXMessage(SDCouponInfoSchema sdcouponschema, Map<String, Object> sendData) {

		String openid = (String) sendData.get("openid");
		String name = (String) sendData.get("name");

		// 模板id
		QueryBuilder qbZDcode = new QueryBuilder(
				"SELECT Memo FROM ZDCode WHERE CodeType = 'WX.MessageTemplate' AND CodeValue = 'couponSend'");
		String templateId = qbZDcode.executeString();
		if (StringUtil.isEmpty(templateId)) {
			templateId = "w0spIZkaENVcezkba3h8ZHgnAPAp6YxCoPUNWlqU-T0";
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("touser", openid);
		param.put("template_id", templateId);
		String wxLoginUrl = Config.getValue("WapServerContext") + "/weixinLogin.shtml";
		wxLoginUrl = URLEncoder.encode(wxLoginUrl);
		param.put("url", authorizeUrl.replace("!!!", (String) props.get("appId")).replace("@@@", wxLoginUrl));
		param.put("topcolor", "#000000");

		String productName = "";
		if (StringUtil.isEmpty(sdcouponschema.getProp3()) || "01".equals(sdcouponschema.getProp3())) {
			productName = sdcouponschema.getParValue() + "元优惠券";
		} else {
			productName = sdcouponschema.getProp4() + "折优惠券";
		}
		
		Map<String, Object> dataParam = new HashMap<String, Object>();
		dataParam = new HashMap<String, Object>();
		dataParam.put(
				"first",
				WeiXinCommon.getWXDataMap("value", "开心保会员:您获得一张" + productName + ",将于"
						+ DateUtil.toString(sdcouponschema.getEndTime(), "yyyy/MM/dd") + "到期"));
		dataParam.put("productType",
				WeiXinCommon.getWXDataMap("value", "开心保"));
		
		dataParam.put("productName",
				WeiXinCommon.getWXDataMap("value", productName));

		dataParam.put("name",
				WeiXinCommon.getWXDataMap("value", name));
		dataParam.put("remark",
				WeiXinCommon.getWXDataMap("value", "请点击我的服务-会员中心或点击详情进行查看"));
		param.put("data", dataParam);
		// 取得token
		String token = WeiXinCommon.ajaxtoken();
		if (!WeiXinCommon.ajaxSendInfoToUser(token, openid, param)) {
			return PubFun.errMsg("微信通知优惠券发送失败");
		} else {
			return PubFun.sucMsg(openid);
		}
	}
	
	/**
	 * 优惠卷邮件发送
	 * 
	 * @param dr
	 * @param email
	 * @return
	 */
	private Map<String, Object> activitySendMail(SDCouponInfoSchema sdcouponschema, String email) {
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			// 发送邮件
			Member member = new Member();
			data.put("Member", member);
			// 优惠券说明
			try {
				data.put("direction", java.net.URLDecoder.decode(sdcouponschema.getDirection(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}

			// 优惠券开始时间
			data.put("starttime", sdcouponschema.getStartTime().toString().substring(0, 10));
			// 优惠券结束时间
			data.put("endtime", sdcouponschema.getEndTime().toString().substring(0, 10));

			// 优惠券编号
			data.put("couponsn", sdcouponschema.getCouponSn());
			data.put("url", Config.getValue("FrontServerContextPath") + "/wj/shop/coupon!queryCoupon.action");
			// 邮箱地址
			member.setEmail(email);

			boolean result = false;
			data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");//邮件中显示活动信息
			// 非折扣券
			if (StringUtil.isEmpty(sdcouponschema.getProp3()) || "01".equals(sdcouponschema.getProp3())) {
				// 优惠金额
				data.put("parValue", sdcouponschema.getParValue());
				// 优惠金额显示
				data.put("parValueShow", sdcouponschema.getParValue() + "元");
				
				result = ActionUtil.sendMail("wj00088", email,data);

			} else {// 折扣券
				// 优惠金额显示
				data.put("parValueShow", sdcouponschema.getProp4() + "折");

				result = ActionUtil.sendMail("wj00113",email, data);
			}

			if (!result) {
				logger.error("优惠卷邮件发送异常. 优惠卷号==>{}", sdcouponschema.getCouponSn());
				return PubFun.errMsg("优惠卷邮件发送异常");
			} else {
				return PubFun.sucMsg();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return PubFun.errMsg("优惠卷邮件发送异常");
		}
	}

	/**
	 * 优惠卷邮件发送
	 * 
	 * @param dr
	 * @param email
	 * @return
	 */
	private void activityDeal(String activitySn, String email, String memberID) {

		// 通过活动id查询该批次的优惠券
		String activityCouponInfoSQL = "select cou.prop3 as couponType,cou.prop4 as discount,cou.parValue as parValue,cou.endtime as endtime,cou.starttime as starttime,cou.direction as direction, cou.couponsn as couponsn,activ.autocreate as autocreate, cou.status as status,cou.id ";
		activityCouponInfoSQL += "from couponinfo cou,sdcouponactivityinfo activ where cou.batch=activ.batch and activ.activitysn=? order by cou.status limit 0,1";
		QueryBuilder querybuilder = new QueryBuilder(activityCouponInfoSQL, activitySn);

		// 结果内至少存在一条数据，有可能是已经送完
		DataTable datatable = querybuilder.executeDataTable();
		DataRow dr = datatable.getDataRow(0);

		String autocreate = dr.getString("autocreate");
		String status = dr.getString("status");
		String id = dr.getString("id");
		String now = DateUtil.getCurrentDateTime();

		// autocreate 1 表示优惠卷发放完毕后，则不处理；0 表示发放完毕后，自动创建
		// status 0 未使用 2 已发放
		if ("1".equals(autocreate) && "0".equals(status)) {
			// 更新数据库状态
			QueryBuilder qb_update = new QueryBuilder("update couponinfo set status='2',mail=?,memberid=?,modifyuser='system',modifydate=now(),prop2=? where couponsn=?");
			qb_update.add(email);
			qb_update.add(memberID);
			qb_update.add(now);
			qb_update.add(dr.getString("couponsn").trim());
			int result = qb_update.executeNoQuery();

			// 发送消息提醒
			if (result == 1) {
				//activitySendMail(dr, email, dr.getString("couponsn"));
				couponRemind(dr.getString("couponsn"), memberID, "");
				logger.info("会员==>{}发放优惠卷==>{}", memberID, dr.getString("couponsn"));
			}
		}
		// 自动创建
		else if ("0".equals(autocreate)) {
			SDCouponInfoSchema sdcouponinfoschema = new SDCouponInfoSchema();
			sdcouponinfoschema.setId(id);

			if (sdcouponinfoschema.fill()) {
				// 如果没有优惠卷则生成新的
				if (!"0".equals(status)) {
					// 生成优惠劵ID
					String coupon_id = DateUtil.getCurrentDateTime("yyyy") + NoUtil.getMaxNo("CouponSn", 12);
					// 优惠券ID值
					sdcouponinfoschema.setId(coupon_id);
					// 优惠券号
					sdcouponinfoschema.setCouponSn(DigestUtils.md5Hex(coupon_id));

					sdcouponinfoschema.setCreateDate(new Date());
					sdcouponinfoschema.setOrderSn("");
				}
				sdcouponinfoschema.setStatus("2");
				// 发放时间
				sdcouponinfoschema.setProp2(now);
				// 将会员id关联到优惠券表中
				sdcouponinfoschema.setMemberId(memberID);
				sdcouponinfoschema.setMail(email);
				sdcouponinfoschema.setModifyUser("system");
				sdcouponinfoschema.setModifyDate(new Date());

				boolean result = false;
				if (!"0".equals(status)) {
					result = sdcouponinfoschema.insert();
				} else {
					result = sdcouponinfoschema.update();
				}

				// 发送邮件
				if (result) {
					logger.info("会员==>{}发放优惠券==>{}", memberID, sdcouponinfoschema.getCouponSn());
					//activitySendMail(dr, email, sdcouponinfoschema.getCouponSn());
					couponRemind(sdcouponinfoschema.getCouponSn(), memberID, "");
				}

			}
		}

	}

	/**
	 * 订单费用拆分
	 */
	public boolean orderPriceSplit(String paySn, String Channel) {
		List<SDOrder> orderList = new ArrayList<SDOrder>();
		QueryBuilder orderQB = new QueryBuilder("select orderSn,totalAmount from sdorders where paysn=?", paySn);
		DataTable orderDT = orderQB.executeDataTable();
		double allOrderTotalAmount = 0.0f;
		for (int i = 0; i < orderDT.getRowCount(); i++) {
			SDOrder mOrder = new SDOrder();
			mOrder.setOrderSn(orderDT.get(i).getString("orderSn"));
			mOrder.setTotalAmount(new BigDecimal(orderDT.get(i).getString("totalAmount")));
			allOrderTotalAmount += new BigDecimal(orderDT.get(i).getString("totalAmount")).doubleValue();
			orderList.add(mOrder);
		}

		// 1、订单使用活动拆分
		orderUsedActivityeSplit(orderList, paySn, Channel);

		// 2、订单使用优惠卷拆分
		orderUsedCouponSplit(paySn, Channel);

		// 3、积分拆分
		orderUsedPointSplit(paySn);

		// 4、订单实际支付金额
		orderPayPriceSplit(paySn);

		return true;
	}

	/**
	 * 
	 * @Title: checkCoupon
	 * @Description: TODO(购物车每个订单校验是否使用优惠券)
	 * @return boolean 返回类型
	 * @author
	 */
	private boolean checkCoupon(String orderid, String couponSn, String channelsn) {
		QueryBuilder qb_productid = new QueryBuilder("SELECT productid FROM sdinformation WHERE ordersn=?");
		qb_productid.add(orderid);
		DataTable dt_productid = qb_productid.executeDataTable();
		
		String productId = dt_productid.getString(0, 0);
		
		QueryBuilder qb = new QueryBuilder("SELECT  ac.riskcode  FROM couponinfo ac WHERE  IF (ac.insurancecompany IS NOT NULL AND ac.insurancecompany != '', FIND_IN_SET(?, ac.insurancecompany) != 0, 1=1) AND IF (ac.product IS NOT NULL AND ac.product != '', FIND_IN_SET(?, ac.product) != 0, 1=1) AND couponsn =? and channelsn like '%" + channelsn + "%'");

		qb.add(productId.substring(0, 4));
		qb.add(productId);
		qb.add(couponSn);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			String riskcode = dt.getString(0, 0);
			if (StringUtil.isNotEmpty(riskcode)) {
				DataTable riskTypeDt = new QueryBuilder("select DISTINCT c.subRisktype from (" +
						"select SubRiskTypeCode as subRisktype from femrisktypeb a,femriskb b where a.RiskCode=b.RiskCode and b.IsPublish='Y' and a.RiskCode = ?" +
						" union all " + 
						"select a.BelongFlag as subRisktype from fmriskb a,femriskb b where a.RiskCode=b.RiskCode and b.IsPublish='Y' and a.RiskCode = ?) c"
						,productId,productId).executeDataTable();
				
				String[] couponRiskCodeArr = riskcode.split(",");
				for(int i=0; i<couponRiskCodeArr.length; i++){
					for(int j=0; j<riskTypeDt.getRowCount(); j++){
						if(couponRiskCodeArr[i].equals(riskTypeDt.get(j, "subRisktype"))){
							return true;
						}
					}
				}
				return false;
			}
			
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 订单实际支付金额
	 * 
	 * @param paySn
	 * @return
	 */
	public boolean orderPayPriceSplit(String paySn) {
		String sql = "update sdorders set payprice=CONVERT(totalamount-ordercoupon-orderintegral-orderActivity ,decimal(18,2)),payAmount=CONVERT(totalamount-ordercoupon-orderintegral-orderActivity ,decimal(18,2)) where paysn=? and totalamount > ordercoupon + orderintegral + orderActivity";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(paySn);
		qb.executeNoQuery();

		String sdorders_sql = "update sdorders set payprice=0.00,payAmount=0.00 where paysn=? and totalamount <= ordercoupon + orderintegral + orderActivity";
		QueryBuilder sdorders_qb = new QueryBuilder(sdorders_sql);
		sdorders_qb.add(paySn);
		sdorders_qb.executeNoQuery();

		return false;
	}

	/**
	 * 订单使用活动拆分
	 */
	public boolean orderUsedActivityeSplit(List<SDOrder> orderList, String paySn, String Channel) {

		ActivityCalculateDetail acd = new ActivityCalculateDetail();
		Map<String, Map<String, Object>> activity_product_info = acd.ProductDiscountOrFuLLCalculate(orderList, Channel,true);
		if (activity_product_info != null && !activity_product_info.isEmpty()) {
			// 1、活动费用拆分
			Transaction orderTran = new Transaction();
			Set<Map.Entry<String, Map<String, Object>>> mActivity_product_info = activity_product_info.entrySet();
			Iterator<Map.Entry<String, Map<String, Object>>> iActivity_product_info_Itor = mActivity_product_info.iterator();
			while (iActivity_product_info_Itor.hasNext()) {
				Map.Entry<String, Map<String, Object>> entry = (Map.Entry<String, Map<String, Object>>) iActivity_product_info_Itor.next();
				Map<String, Object> mActivityInfo = (Map<String, Object>) entry.getValue().get("ActivityInfo");
				Map<String, Object> mActivityAmont = (Map<String, Object>) entry.getValue().get("ActivityAmont");
				List<Map<String, String>> mProductList = (List<Map<String, String>>) entry.getValue().get("ProductInfo");
				for (int i = 0; i < mProductList.size(); i++) {
					Map<String, String> mm = mProductList.get(i);
					String order_update = "update sdorders set orderActivity=? , sumActivity=? , activitySn=? , ordercoupon=0, orderintegral=0 where ordersn=?";
					QueryBuilder order_update_qb = new QueryBuilder(order_update);

					order_update_qb.add(mm.get("DiscountAmount"));// 单个订单优惠金额
					order_update_qb.add(mActivityAmont.get("DiscountAmount"));// 订单对应活动优惠的总金额

					if (entry.getKey().equalsIgnoreCase(ActivityCalculateDetail.NO_ACTIVITY)) {
						order_update_qb.add("");// 活动号

					} else if (Double.parseDouble(mm.get("DiscountAmount")) <= 0 || Double.parseDouble(mActivityAmont.get("DiscountAmount") + "") <= 0) {
						order_update_qb.add("");// 活动号

					} else {
						order_update_qb.add(entry.getKey());// 活动号

					}
					order_update_qb.add(mm.get("OrderSn"));
					orderTran.add(order_update_qb);
				}
			}
			if (!orderTran.commit()) {
				logger.error("订单活动费用拆分异常. paysn==>{} Channel==>{}", paySn, Channel);
				return false;
			}
		}

		return true;
	}

	/**
	 * 订单使用优惠卷拆分
	 */
	public boolean orderUsedCouponSplit(String paySn, String Channel) {
		// 2、优惠卷拆分
		String couponSn = new QueryBuilder("select couponsn from  TradeSummaryInfo where paysn=?", paySn).executeString();

		if (StringUtil.isNotEmpty(couponSn)) {
			Transaction order_coupon_tran = new Transaction();

			List<SDOrder> orderList = new ArrayList<SDOrder>();
			QueryBuilder orderQB = new QueryBuilder("select orderSn,(totalAmount-orderActivity) as totalAmount  from sdorders where paysn=?", paySn);
			DataTable orderDT = orderQB.executeDataTable();
			double allOrderTotalAmount = 0.0f;
			for (int i = 0; i < orderDT.getRowCount(); i++) {
				SDOrder mOrder = new SDOrder();
				mOrder.setOrderSn(orderDT.get(i).getString("orderSn"));
				mOrder.setTotalAmount(new BigDecimal(orderDT.get(i).getString("totalAmount")));
				allOrderTotalAmount += new BigDecimal(orderDT.get(i).getString("totalAmount")).doubleValue();
				orderList.add(mOrder);
			}

			// 优惠卷对于的订单总额
			double total_coupon_order = 0.0f;
			List<SDOrder> orderCouponList = new ArrayList<SDOrder>();
			for (int i = 0; i < orderList.size(); i++) {
				boolean flag = checkCoupon(orderList.get(i).getOrderSn(), couponSn, Channel);
				if (flag) {
					orderCouponList.add(orderList.get(i));
					total_coupon_order += orderList.get(i).getTotalAmount().doubleValue();
				}
			}

			// 获取总优惠卷优惠金额,进行订单比例分层
			QueryBuilder tradeSummaryInfoQB = new QueryBuilder("select CouponSumAmount from TradeSummaryInfo where paysn=?", paySn);
			String sCouponSumAmount = tradeSummaryInfoQB.executeString();
			if (StringUtil.isEmpty(sCouponSumAmount)) {
				return true;
			}

			// 优惠卷总优惠金额
			double CouponSumAmount = Double.parseDouble(sCouponSumAmount);
			double orderCoupon_last = CouponSumAmount;
			for (int i = 0; i < orderCouponList.size(); i++) {
				QueryBuilder order_update_qb = new QueryBuilder("update sdorders set orderCoupon=?,couponsn=? where ordersn=? ");
				if (i == orderCouponList.size() - 1) {
					order_update_qb.add(formatToDouble(orderCoupon_last, 2));// 单个订单优惠金额

				} else {
					double orderCoupon = formatToDouble((orderCouponList.get(i).getTotalAmount().doubleValue() / total_coupon_order) * CouponSumAmount, 2);
					order_update_qb.add(orderCoupon);// 单个订单优惠金额
					orderCoupon_last -= orderCoupon;
				}

				order_update_qb.add(couponSn);
				order_update_qb.add(orderCouponList.get(i).getOrderSn());
				order_coupon_tran.add(order_update_qb);
			}

			if (!order_coupon_tran.commit()) {
				logger.error("订单优惠卷费用拆分异常. paysn==>{} Channel==>{}", paySn, Channel);
				return false;
			}
		}

		return true;
	}

	/**
	 * 订单使用积分拆分
	 */
	public boolean orderUsedPointSplit(String paySn) {
		try {
			// 1、根据交易号，查询此次交易订单信息，进行订单比例分寸。
			QueryBuilder tradeSummaryInfoQB = new QueryBuilder("select PointSumAmount  from TradeSummaryInfo where paysn=?", paySn);

			DataTable tradeSummaryInfoDT = tradeSummaryInfoQB.executeDataTable();
			if (tradeSummaryInfoDT == null || tradeSummaryInfoDT.getRowCount() != 1) {
				logger.error("订单费用拆分异常. TradeSummaryInfo 表未查询到支付数据 .paySn==>{}", paySn);
				return false;
			}

			DataRow tradeSummaryInfoDR = tradeSummaryInfoDT.get(0);
			if (StringUtil.isEmpty(tradeSummaryInfoDR.getString("PointSumAmount")) || Double.parseDouble(tradeSummaryInfoDR.getString("PointSumAmount")) == 0) {
				return false;
			}

			String order_sql = "select  s3.BuyPoints,s2.ordersn,CONVERT(totalamount-ordercoupon-orderActivity ,decimal(18,2)) as real_price";
			order_sql += " from sdorders s1,sdinformation s2 left join SDProductPointRate s3 on s2.productid = s3.ProductID ";
			order_sql += " where paysn=? and s1.ordersn=s2.ordersn  ";

			DataTable orderDT = new QueryBuilder(order_sql, paySn).executeDataTable();

			double TotalAmount = 0.0f; // 所有可用积分比例
			for (int i = 0; i < orderDT.getRowCount(); i++) {
				TotalAmount += stirngToDouble(orderDT.get(i).getString("BuyPoints")) * stirngToDouble(orderDT.get(i).getString("real_price"));
			}

			Transaction tran = new Transaction();

			// 总订单数据
			double PointSumAmount = stirngToDouble(tradeSummaryInfoDR.getString("PointSumAmount")); // 积分优惠总金额

			// 最后一个订单数据
			double PointSumAmount_Last = stirngToDouble(tradeSummaryInfoDR.getString("PointSumAmount")); // 最后一个订单积分优惠总金额

			for (int i = 0; i < orderDT.getRowCount(); i++) {
				// 2、更新订单表分单数据
				double orderTotalAmount = stirngToDouble(orderDT.get(i).getString("BuyPoints")) * stirngToDouble(orderDT.get(i).getString("real_price")) ; // 当前订单的积分比例

				QueryBuilder qb = new QueryBuilder("update sdorders set  orderIntegral=?  where ordersn=?");

				String PointSumAmount_Current = "";
				if (TotalAmount != 0) {
					PointSumAmount_Current = formatDouble(PointSumAmount * orderTotalAmount / TotalAmount, 1); // 订单积分优惠总金额
				}
				
				// 最后一个订单数据
				if (i == orderDT.getRowCount() - 1) {
					qb.add(formatDouble(PointSumAmount_Last, 2));// 订单积分抵值金额

				} else {
					qb.add(PointSumAmount_Current);// 订单积分抵值金额
					PointSumAmount_Last -= stirngToDouble(PointSumAmount_Current);
				}

				qb.add(orderDT.get(i).getString("orderSn"));// 订单号

				tran.add(qb);
			}

			return tran.commit();

		} catch (Exception e) {
			logger.error("订单费用拆分异常.paySn==>" + paySn + e.getMessage(), e);

			return false;
		}
	}

	/**
	 * 保单费用拆分
	 */
	public boolean policyPriceSplit(String paySn) {
		try {
			// 1、根据交易号，查询此次交易订单信息。
			QueryBuilder orderQB = new QueryBuilder("select orderCoupon,orderIntegral,orderActivity,payPrice,totalAmount,orderSn  from sdorders where paysn=?", paySn);
			DataTable orderDT = orderQB.executeDataTable();
			if (orderDT == null || orderDT.getRowCount() == 0) {
				logger.error("保单费用拆分异常. sdorders 表未查询到支付数据 .paySn==>{}", paySn);
				return false;
			}
			Transaction tran = new Transaction();

			for (int i = 0; i < orderDT.getRowCount(); i++) {
				DataRow order = orderDT.get(i);

				double totalAmount = stirngToDouble(order.getString("totalAmount")); // 交易实际金额

				// 总订单数据
				double orderCoupon = stirngToDouble(order.getString("orderCoupon")); // 订单优惠劵优惠总金额
				double orderIntegral = stirngToDouble(order.getString("orderIntegral")); // 订单积分优惠总金额
				double orderActivity = stirngToDouble(order.getString("orderActivity")); // 订单活动优惠总金额

				// 最后一个保单数据
				double orderCoupon_Last = stirngToDouble(order.getString("orderCoupon")); // 订单优惠劵优惠总金额
				double orderIntegral_Last = stirngToDouble(order.getString("orderIntegral")); // 订单积分优惠总金额
				double orderActivity_Last = stirngToDouble(order.getString("orderActivity")); // 订单活动优惠总金额
				double payPrice_Last = stirngToDouble(order.getString("payPrice")); // 订单交易支付总金额

				QueryBuilder policyQB = new QueryBuilder("select id ,timePrem from SDInformationRiskType where orderSn=?", order.getString("orderSn"));
				DataTable policyDT = policyQB.executeDataTable();
				for (int j = 0; j < policyDT.getRowCount(); j++) {

					double timePrem = stirngToDouble(policyDT.get(j).getString("timePrem")); // 交易内单笔保单实际金额

					// 3、更新保单表进行分单数据处理
					QueryBuilder qb = new QueryBuilder("update SDInformationRiskType set couponValue=?,integralValue=?,activityValue=?,payPrice=?  where id=?");
					// 保单优惠券优惠金额 couponValue
					// 保单积分抵值金额 integralValue
					// 保单活动优惠金额 activityValue
					// 保单支付金额 payPrice

					String orderCoupon_Current = formatDouble(timePrem * orderCoupon / totalAmount, 2); // 保单优惠劵优惠总金额
					String orderIntegral_Current = formatDouble(timePrem * orderIntegral / totalAmount, 2); // 保单积分优惠总金额
					String orderActivity_Current = formatDouble(timePrem * orderActivity / totalAmount, 2); // 保单活动优惠总金额
					String payPrice_Current = formatDouble(timePrem - Double.parseDouble(orderCoupon_Current) - Double.parseDouble(orderIntegral_Current) - Double.parseDouble(orderActivity_Current),
							2); // 保单支付总金额

					// 最后一个订单数据
					if (j == policyDT.getRowCount() - 1) {
						qb.add(formatDouble(orderCoupon_Last, 2));// 保单优惠券优惠金额
						qb.add(formatDouble(orderIntegral_Last, 2));// 保单积分抵值金额
						qb.add(formatDouble(orderActivity_Last, 2));// 保单活动优惠金额-满减、折扣
						qb.add(formatDouble(payPrice_Last, 2));// 保单实际支付金额

					} else {
						qb.add(orderCoupon_Current);// 保单优惠券优惠金额
						qb.add(orderIntegral_Current);// 保单积分抵值金额
						qb.add(orderActivity_Current);// 保单活动优惠金额-满减、折扣
						qb.add(payPrice_Current);// 保单实际支付金额

						orderCoupon_Last -= stirngToDouble(orderCoupon_Current);
						orderIntegral_Last -= stirngToDouble(orderIntegral_Current);
						orderActivity_Last -= stirngToDouble(orderActivity_Current);
						payPrice_Last -= stirngToDouble(payPrice_Current);
					}

					qb.add(policyDT.get(j).getString("id"));// 订单号

					tran.add(qb);
				}
			}

			return tran.commit();

		} catch (Exception e) {
			logger.error("保单费用拆分异常.paySn==>" + paySn + e.getMessage(), e);

			return false;

		}
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	private String formatDate(String date) {
		try {
			SimpleDateFormat sdf_Date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf_Format_Date = new SimpleDateFormat("yyyy/MM/dd");
			return sdf_Format_Date.format(sdf_Date.parse(date));

		} catch (ParseException e) {
			logger.error("格式化活动日期异常.日期为：" + date + e.getMessage(), e);

			String month_start = date.substring(5, 7);
			if (month_start.startsWith("0")) {
				month_start = month_start.substring(1, 2);
			}
			String day_start = date.substring(8, 10);
			if (day_start.startsWith("0")) {
				day_start = day_start.substring(1, 2);
			}
			return date.substring(0, 4) + "/" + month_start + "/" + day_start;
		}
	}

	/**
	 * 积分计算，全舍
	 */
	private int calculateIntegral(String amount, double IntegralRate, String GivePoints) {
		try {
			if (StringUtil.isEmpty(GivePoints)) {
				return 0;
			}

			double d1 = stirngToDouble(amount) * stirngToDouble(GivePoints) * stirngToDouble(Config.getValue("PointScalerUnit")) * IntegralRate;
			return new BigDecimal(d1).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();

		} catch (Exception e) {
			logger.error("积分计算异常,保费=>" + amount + " 积分比例=>" + IntegralRate + e.getMessage(), e);
			return new BigDecimal(amount).setScale(0, BigDecimal.ROUND_DOWN).intValue();
		}
	}

	public String formatDouble(double amount, int newScale) {
		return new BigDecimal(amount).setScale(newScale, BigDecimal.ROUND_HALF_UP).toString();
	}

	public double formatToDouble(double amount, int newScale) {
		return new BigDecimal(amount).setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public double stirngToDouble(String data) {
		if (StringUtil.isEmpty(data)) {
			return 0;
		}
		return Double.parseDouble(data);
	}

	/**
	 * 支付页面，买成功您将获得、积分抵消部分总额
	 * 
	 */
	public Map<String, String> payPointInfo(List<SDOrder> sdorderList, String channel, int userpoints, String couponinfo, String memberid) {
		Map<String, String> result = new HashMap<String, String>();

		List<String> product_list = new ArrayList<String>();
		// 1、计算订单的活动费用
		Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(sdorderList, channel,true);
		Set<Map.Entry<String, Map<String, Object>>> mActivity_product_info1 = activity_product_info1.entrySet();
		Iterator<Map.Entry<String, Map<String, Object>>> iActivity_product_info_Itor1 = mActivity_product_info1.iterator();
		// 分订单费用
		List<Map<String, String>> productMoney = new ArrayList<Map<String, String>>();
		double total = 0.0f;
		
		String ProductIDStr = "";
		Map<String,String> map_group=new HashMap<String,String>();
		while (iActivity_product_info_Itor1.hasNext()) {
			Map.Entry<String, Map<String, Object>> entry = (Map.Entry<String, Map<String, Object>>) iActivity_product_info_Itor1.next();

			List<Map<String, String>> mProductList = (List<Map<String, String>>) entry.getValue().get("ProductInfo");
			for (Map<String, String> mm : mProductList) {
				productMoney.add(mm);
				total += Double.parseDouble(mm.get("ActivityeAmount"));
				product_list.add(mm.get("ProductID"));
				ProductIDStr += " s1.ProductId = '" + mm.get("ProductID") + "' or";
				if(map_group.containsKey(mm.get("ProductID"))){
					String orderstr=map_group.get(mm.get("ProductID"));
					map_group.put(mm.get("ProductID"), orderstr+"'"+mm.get("OrderSn")+"',");
				}else{
					map_group.put(mm.get("ProductID"), "'"+mm.get("OrderSn")+"',");
				}	
			}
		}
		if(ProductIDStr.endsWith("or")){
			ProductIDStr=ProductIDStr.substring(0,ProductIDStr.length()-2);
		}
		// 满足优惠卷活动的产品
		List<Map<String, String>> productMoneyList = new ArrayList<Map<String, String>>();
		// 不满足优惠卷活动的产品
		List<Map<String, String>> noProductMoneyList = new ArrayList<Map<String, String>>();

		// 2、如果存在优惠卷，则处理保费
		double total_couoninfo = 0.0f;
		if (StringUtil.isNotEmpty(couponinfo)) {
			QueryBuilder cou_qb = new QueryBuilder("select parValue,riskcode,insuranceCompany,prop3,prop4 from couponinfo where couponSn=?");
			cou_qb.add(couponinfo);
			DataTable dt = cou_qb.executeDataTable();
			String coutype = dt.getString(0, "prop3");// 优惠卷类型
			String parValue = dt.getString(0, "parValue");// 非折扣价值
			String courate = dt.getString(0, "prop4");// 折扣比例

			if (StringUtil.isNotEmpty(parValue)) {
				// 判断产品是否满足获得活动 OrderSn
				for (int i = 0; i < productMoney.size(); i++) {
					boolean flag = checkCoupon(productMoney.get(i).get("OrderSn"), couponinfo, channel);
					if (flag) {
						productMoneyList.add(productMoney.get(i));

					} else {
						noProductMoneyList.add(productMoney.get(i));
					}
				}

				for (int i = 0; i < productMoneyList.size(); i++) {

					Map<String, String> pro = productMoneyList.get(i);

					// 订单活动保费
					double ActivityeAmount = Double.parseDouble(pro.get("ActivityeAmount"));

					// 折扣优惠券
					if ("02".equals(coutype)) {
						pro.put("ActivityeAmount", formatDouble(ActivityeAmount * Double.parseDouble(courate)/10, 2));

						// 非折扣优惠券
					} else if ("01".equals(coutype)) {
						if (i == productMoneyList.size() - 1) {
							pro.put("ActivityeAmount", formatDouble(ActivityeAmount - (Double.parseDouble(parValue) - total_couoninfo), 2));

						} else {
							String sub=formatDouble(ActivityeAmount / total * Double.parseDouble(parValue),2);
							String d = formatDouble(ActivityeAmount - ((Double.valueOf(sub))), 2);
							pro.put("ActivityeAmount", d);

							total_couoninfo += Double.valueOf(sub);

						}
					}
				}

				if (noProductMoneyList != null && noProductMoneyList.size() > 0) {
					for (int i = 0; i < noProductMoneyList.size(); i++) {
						productMoneyList.add(noProductMoneyList.get(i));
					}
				}
			}
		} else {
			productMoneyList = productMoney;
		}

		// 3、计算积分抵消部分总额
		PointsCalculate pc = new PointsCalculate();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ProductList", product_list);
		Map<String, Object> give_map = null;
		try {
			give_map = pc.pointsManage(IntegralConstant.POINT_PRODUCT, null, param);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		DataTable give_dt = (DataTable) give_map.get(IntegralConstant.DATA);

		Mapx<String, String> GiveMap = give_dt.toMapx("ProductID", "GivePoints");
		Mapx<String, String> BuyMap = give_dt.toMapx("ProductID", "BuyPoints");

		int givetotalpoint = 0;
		double totalbuypoints = 0.0f;

		for (int i = 0; i < productMoneyList.size(); i++) {
			Map<String, String> orderMap = productMoneyList.get(i);
			String ordermoney = orderMap.get("ActivityeAmount");
			givetotalpoint += calculateIntegral(ordermoney, ActivityCalculateDetail.PRODUCT_INTEGRAL_RATE, BuyMap.getString(orderMap.get("ProductID")));

			// 判断产品最多可以使用积分比例
			if (BuyMap != null) {
				String buyPoint = BuyMap.getString(orderMap.get("ProductID"));
				if (StringUtil.isNotEmpty(buyPoint)) {
					totalbuypoints += Double.parseDouble(buyPoint) * Double.parseDouble(orderMap.get("ActivityeAmount"));
				}
			}
		}

		result.put("canusepoint", givetotalpoint + "");
		result.put("pointscalerunit", Config.getValue("PointScalerUnit"));
		try {
			if (userpoints > 0) {
				double pointmoney = userpoints / stirngToDouble(Config.getValue("PointScalerUnit"));
				double last_pointmoney = pointmoney;

				for (int i = 0; i < productMoneyList.size(); i++) {
					Map<String, String> orderMap = productMoneyList.get(i);
					//String ordermoney = orderMap.get("ActivityeAmount");
					// givetotalpoint += calculateIntegral(ordermoney, ActivityCalculateDetail.PRODUCT_INTEGRAL_RATE, GiveMap.getString(orderMap.get("ProductID")));

					// 判断产品最多可以使用积分比例
					//totalbuypoints += Double.parseDouble(BuyMap.getString(orderMap.get("ProductID")));

					if (i == productMoneyList.size() - 1) {
						orderMap.put("ActivityeAmount", formatToDouble(Double.parseDouble(orderMap.get("ActivityeAmount")) - last_pointmoney, 2) + "");

					} else {
						double curentproductmoney = formatToDouble(pointmoney * (Double.parseDouble(BuyMap.getString(orderMap.get("ProductID"))) * Double.parseDouble(orderMap.get("ActivityeAmount"))) / totalbuypoints,1);
						last_pointmoney -= curentproductmoney;
						orderMap.put("ActivityeAmount", formatToDouble(Double.parseDouble(orderMap.get("ActivityeAmount")) - curentproductmoney, 2) + "");

					}
				}

			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// 4、计算可以获得的积分
		
		// 计算高倍积分
		StringBuffer ProductActivityInfo = new StringBuffer();
		ProductActivityInfo.append(" select s1.ProductID,s2.title,s2.description, s2.type ,s3.ActivityData,s2.GroupbuyWhether,s2.GroupbuyNum,s2.memberChannel,s3.MemberRule   ");
		ProductActivityInfo.append(" from SdProductActivityLink s1,sdcouponactivityinfo s2,SdActivityRule s3");
		ProductActivityInfo.append(" where  ( ").append(ProductIDStr).append(" )");
		ProductActivityInfo.append(" and   s2.type='7' "); // 积分
		ProductActivityInfo.append(" and  status='3' and  s1.ActivitySn=s2.activitySn  and s2.activitySn=s3.activitysn ");
		ProductActivityInfo.append(" and  s2.starttime <='").append(PubFun.getCurrent()).append("'");
		ProductActivityInfo.append(" and  s2.endtime >='").append(PubFun.getCurrent()).append("'");
		ProductActivityInfo.append(" and  s1.ActivityChannel = '").append(channel).append("'");
		ProductActivityInfo.append(" order by s1.ProductID  ");
		QueryBuilder qb = new QueryBuilder(ProductActivityInfo.toString());
		DataTable dt = qb.executeDataTable();
		Mapx<String, String> productPointMap = null;
		Mapx<String, String> GroupbuyWhetherMap = null;
		Mapx<String, String> GroupbuyNumMap = null;
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			int i = 0;
			String grade = "";
			if (StringUtil.isEmpty(memberid) && sdorderList != null && sdorderList.size() > 0) {
				memberid = sdorderList.get(0).getMemberId();
			}
			// 取得会员等级
			if (StringUtil.isNotEmpty(memberid)) {
				memberSchema memberSchema = new memberSchema();
				memberSchema.setid(memberid);
				if (memberSchema.fill()) {
					grade = memberSchema.getgrade();
					if ("Y".equals(memberSchema.getvipFlag())) {
						grade = "VIP";
					}
				}
			}
						
			for (; i < rowCount; i++) {
				// 会员频道的活动
				if ("Y".equalsIgnoreCase(dt.getString(i, "memberChannel"))) {
					// 无会员等级的或会员等级不满足活动设置不享受此活动
					if (StringUtil.isEmpty(grade) || (StringUtil.isNotEmpty(dt.getString(i, "MemberRule")) && !dt.getString(i, "MemberRule").contains(grade))) {
						dt.deleteRow(i);
						i--;
						rowCount--;
					}
				}
			}
			
			productPointMap = dt.toMapx("ProductID", "ActivityData");
			GroupbuyWhetherMap = dt.toMapx("ProductID", "GroupbuyWhether");
			GroupbuyNumMap = dt.toMapx("ProductID", "GroupbuyNum");
		}
		
		int givepoints = 0;
		List<Map<String,Object>> productToPointRates = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < productMoneyList.size(); i++) {
			Map<String, String> orderMap = productMoneyList.get(i);
			
			double IntegralRate  = ActivityCalculateDetail.PRODUCT_INTEGRAL_RATE;

			if (productPointMap != null && !productPointMap.isEmpty()&&productPointMap.containsKey(orderMap.get("ProductID"))) {
				String GroupbuyWhether=GroupbuyWhetherMap.get(orderMap.get("ProductID"));
				String GroupbuyNum=GroupbuyNumMap.get(orderMap.get("ProductID"));
				if("1".equals(GroupbuyWhether)){
					//保单数
					String risktype_num="0";
					if(StringUtil.isNotEmpty(orderMap.get("ProductID"))){
						String ordersns=map_group.get(orderMap.get("ProductID"));
						if(StringUtil.isNotEmpty(ordersns)){
							risktype_num=new QueryBuilder("select sum(1)  from   sdinformationrisktype where ordersn in ("+ordersns.substring(0,ordersns.length()-1)+")").executeString();
							if(StringUtil.isEmpty(risktype_num)){
								risktype_num="0";
							}
						}
					}
					if(Integer.parseInt(risktype_num)>=Integer.parseInt(GroupbuyNum)){
						IntegralRate = Double.parseDouble(productPointMap.getString(orderMap.get("ProductID")));
					}
				}else{
					IntegralRate = Double.parseDouble(productPointMap.getString(orderMap.get("ProductID")));
				}
			}
			
			givepoints += calculateIntegral(orderMap.get("ActivityeAmount"), IntegralRate, GiveMap.getString(orderMap.get("ProductID")));
			Map<String,Object> productToPointRate = new HashMap<String,Object>();
			productToPointRate.put("amount", orderMap.get("ActivityeAmount"));
			productToPointRate.put("rate", GiveMap.getString(orderMap.get("ProductID")));
			productToPointRates.add(productToPointRate);
		}

		result.put("givepoint", givepoints + "");
		result.put("productToPointRates", JSONArray.fromObject(productToPointRates).toString());
		return result;
	}
	/**
	 * 
	* @Title: orderIntegerSplit 
	* @Description: TODO(会员等级或生日月累加积分分单处理) 
	* @return boolean    返回类型 
	* @author
	 */
	private boolean orderIntegerSplit(String ordersn,String point,Transaction tran,memberSchema member){
		SDInformationSchema SDInformation=new SDInformationSchema();
		SDInformation=SDInformation.query(new QueryBuilder(" where ordersn=?",ordersn)).get(0);
		String points=SDInformation.getpoint();
		if(StringUtil.isEmpty(points)){
			points="0";
		}
		SDInformation.setpoint(String.valueOf(new BigDecimal(points).add(new BigDecimal(point))));
		//冻结积分累加
		String point_value=String.valueOf(member.getpoint());
		if(StringUtil.isEmpty(point_value)){
			point_value="0";
		}
		tran.add(SDInformation, Transaction.UPDATE);
		return true;
	}
}
