/**
 * 
 */
package cn.com.sinosoft.util;

import cn.com.sinosoft.entity.GiftClassify;
import cn.com.sinosoft.entity.Member;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.GoodsStockSchema;
import com.sinosoft.schema.GoodsStockSet;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.TradeInformationSchema;
import com.sinosoft.schema.TradeSummaryInfoSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保险产品兑换
 * @author wangcaiyun
 *
 */
public class Points1 extends PointExchangeInterface {
	private static final Logger logger = LoggerFactory.getLogger(Points1.class);
	@Override
	public Map<String,String> checkParams(String orderSn) {
		HashMap<String, String> map = new HashMap<String, String>();
		DataTable dt = new QueryBuilder("select o.OffsetPoint, i.productId, o.orderStatus, o.payStatus, o.paySn, o.orderIntegral, o.totalAmount from sdorders o, sdinformation i where o.orderSn=? and o.orderSn=i.orderSn ", orderSn).executeDataTable();
		if (dt == null || dt.getRowCount() < 1) {
			map.put(STATUS, IntegralConstant.FAIL);
			logger.error("积分兑换保险产品支付时未查询到订单信息，会员ID：{} 订单号：{}", memberid, orderSn);
			return map;
		}
		String offsetPoint = dt.getString(0, 0);
		String productId = dt.getString(0, 1);
		String orderStatus = dt.getString(0, 2);
		String payStatus = dt.getString(0, 3);
		String paySn = dt.getString(0, 4);
		String orderIntegral = dt.getString(0, 5);
		String totalAmount = dt.getString(0, 6);
		
		// 订单兑换积分必须有效
		if (StringUtil.isEmpty(offsetPoint) || "0".equals(offsetPoint)) {
			map.put(STATUS, IntegralConstant.FAIL);
			logger.info("积分兑换保险产品支付时订单兑换积分为空或是0，会员ID：{} 订单号：{}",memberid, orderSn);
			return map;
		}
		Member member = memberService.load(memberid);
		// 判断会员积分是否足够支付
		if (member.getCurrentValidatePoint() == null
				|| member.getCurrentValidatePoint() == 0) {
			map.put(STATUS, IntegralConstant.FAIL);
			map.put(MESSAGE, "您的积分不够了，快去赚吧！");
			logger.info("会员无可用积分进行兑换保险产品，会员ID：{} 订单号：{}", memberid, orderSn);
			return map;
		}

		if (member.getCurrentValidatePoint().compareTo(Integer.valueOf(offsetPoint)) < 0) {
			map.put(STATUS, IntegralConstant.FAIL);
			map.put(MESSAGE, "您的积分不够了，快去赚吧！");
			Object[] argArr = {member.getCurrentValidatePoint(), offsetPoint, memberid, orderSn};
			logger.info("会员可用积分（{}）小于兑换保险产品积分（{}），会员ID：{} 订单号：{}", argArr);
			return map;
		}
		
		String sql = "select count(1) from sdinformationrisktype where ordersn = ? ";
		// 兑换保单数量
		int count = new QueryBuilder(sql, orderSn).executeInt();
		// 查看库存量
		int lastnum = mGiftClassifyService.getLastNum(productId);
		
		if (lastnum == 0 || lastnum < count) {
			map.put(STATUS, IntegralConstant.FAIL);
			map.put(MESSAGE, "对不起，库存不足！");
			Object[] argArr = {lastnum, memberid, orderSn};
			logger.info("会员用积分进行兑换保险产品库存（{}个）不足，会员ID：{} 订单号：{}", orderSn);
			return map;
		}
		
		if (!"5".equals(orderStatus)) {
			map.put(STATUS, IntegralConstant.FAIL);
			map.put(MESSAGE, "该订单 （" + orderSn + "） 不是待支付状态，不允许重新支付！");
			logger.warn("该订单不是待支付状态，不允许重新支付，orderSn:{}", orderSn);
			return map;
		}
		
		if ("2".equals(payStatus)) {
			map.put(STATUS, IntegralConstant.SUCCESS);
			map.put(MESSAGE, "已支付成功！");
			return map;
		}
		
		String totalPayPrice = new QueryBuilder("SELECT ROUND(SUM(a.payprice),2) FROM sdinformationrisktype a,sdorders b WHERE a.`orderSn` = b.`orderSn` AND a.appstatus = '1' AND b.memberid =?", memberid).executeString();
		if(StringUtil.isEmpty(totalPayPrice)){
			totalPayPrice = "0.00";
		}
		double shortPrice = ArithUtil.sub(new BigDecimal(totalPayPrice).doubleValue(), new BigDecimal(Config.getValue("exchangeBoundary")).doubleValue());
		if(shortPrice < 0){
			map.put(STATUS, IntegralConstant.FAIL);
			map.put(MESSAGE, "会员需在网站累计购物满"+String.valueOf(Config.getValue("exchangeBoundary"))+"元后，方可在积分商城兑换商品。");
			logger.info("会员需在网站累计购物满{}元后，方可在积分商城兑换商品。订单号：{}", String.valueOf(Config.getValue("exchangeBoundary")), orderSn);
			return map;
		}
		
		map.put("count", count+"");
		map.put("productId", productId);
		map.put("usedPoint", offsetPoint);
		map.put("paySn", paySn);
		map.put("orderIntegral", orderIntegral);
		map.put("totalAmount", totalAmount);
		
		return map;
	}
	
	@Override
	public Map<String, String> Exchange(String orderSn) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		synchronized (orderSn) {
			int dealCount = 0;
			do {
				Map<String, String> resultCheck = checkParams(orderSn);
				if (resultCheck.containsKey(STATUS)) {
					return resultCheck;
				}
				// 产品id
				String productId = resultCheck.get("productId");
				// 购买份数
				int count = Integer.valueOf(resultCheck.get("count"));
				// 使用积分数
				String usedPoint = resultCheck.get("usedPoint");
			
				GiftClassify giftClassify = mGiftClassifyService.getGiftClassifyByProductId(productId);
				if (giftClassify == null) {
					jsonMap.put(MESSAGE, "兑换的礼品不存在,请联系客服！");
					jsonMap.put(STATUS, IntegralConstant.FAIL);
					return jsonMap;
				}
				
				synchronized (giftClassify.getId()) {
					if (StringUtil.isEmpty(giftClassify.getLastNum()) || Integer.valueOf(giftClassify.getLastNum()) < count) {
						jsonMap.put(STATUS, IntegralConstant.FAIL);
						jsonMap.put(MESSAGE, "对不起，库存不足！");
						Object[] argArr = {giftClassify.getLastNum(), memberid, orderSn};
						logger.info("会员用积分进行兑换保险产品库存（{}个）不足，会员ID：{} 订单号：{}", argArr);
						return jsonMap;
					}
					
					synchronized (memberid) {
						Transaction trans = new Transaction();
						// 扣除积分、生成积分明细
						Map<String, String> resultMap = subtractPoint(memberid, giftClassify, orderSn, usedPoint, resultCheck.get("totalAmount"), trans);
						if (IntegralConstant.FAIL.equals(resultMap.get(STATUS))) {
							return resultMap;
						}
						
						// 减少库存
						updateGiftNum(giftClassify, count, trans);
						
						// 更新订单状态
						updateOrder(orderSn, trans);
						
						// 生成交易信息
						writeBackPayInfo(orderSn, memberid, resultCheck, trans);
						
						// 更新积分兑换表状态
						dealPointExchangeInfo(orderSn, memberid, usedPoint, trans);
						
						// 生成库存记录
						makeGoodsStock(orderSn, memberid, giftClassify.getId(), giftClassify.getGiftTitle(), usedPoint, trans);
						
						if (trans.commitRoll()) {
							// 少量库存发送告警邮件
							super.lowStocksWarn(Integer.valueOf(giftClassify.getLastNum()) - count, giftClassify.getGiftTitle(), orderSn);
							// 用户消耗大于等于500积分时，给用户发送积分扣除提醒
							super.warn(memberid, usedPoint, giftClassify.getGiftTitle(), orderSn);
							
							jsonMap.put(STATUS, IntegralConstant.SUCCESS);
							jsonMap.put(MESSAGE, "支付成功！请在会员中心-我的积分中查询您的积分兑换订单");
							return jsonMap;
						} else {
							dealCount++;
						}
					}
				}
			
			}while(dealCount < Constant.UPDATE_MEMBER_DEAL_COUNT);
			
			if (dealCount >= Constant.UPDATE_MEMBER_DEAL_COUNT) {
				logger.error("兑换保险产品更新数据失败，orderSn:{}", orderSn);
				jsonMap.put(STATUS, IntegralConstant.FAIL);
			}
		}
		
		return jsonMap;
	}
	
	/**
	 * 生成库存记录
	 * @param orderSn
	 * @param memberId
	 * @param giftId
	 * @param giftName
	 * @param offsetPoint
	 * @param trans
	 */
	private void makeGoodsStock(String orderSn, String memberId, String giftId, String giftName, String offsetPoint, Transaction trans) {
		
		DataTable dt = new QueryBuilder("select timePrem, sdinformationinsured_id from sdinformationrisktype where orderSn=?", orderSn).executeDataTable();

		if (dt != null && dt.getRowCount() > 0) {
			GoodsStockSet set = new GoodsStockSet();
			String Unit = Config.getValue("PointScalerUnit");
			BigDecimal pointUnit = new BigDecimal(10);
			if (StringUtil.isNotEmpty(Unit)) {
				pointUnit = new BigDecimal(Unit);
			}
			int size = dt.getRowCount();
			int sumPoints = Integer.valueOf(offsetPoint);
			int addPoints = 0;
			int point = 0;
			for (int i = 0; i < size; i++) {
				if (i == size - 1) {
					point = sumPoints - addPoints;
				} else {
					point = new BigDecimal(dt.getString(i, 0)).multiply(pointUnit).setScale(0, BigDecimal.ROUND_UP).intValue();
					addPoints += point;
				}
				
				GoodsStockSchema goodsStockSchema = new GoodsStockSchema();
				goodsStockSchema.setid(NoUtil.getMaxNo("GoodsStockID", 11));
				goodsStockSchema.setgiftID(giftId);
				goodsStockSchema.setgoodsName(giftName);
				goodsStockSchema.setmemberid(memberId);
				goodsStockSchema.setpayPoints(String.valueOf(point));
				goodsStockSchema.setstatus("1");
				// 被保人Id 
				goodsStockSchema.setinsuredId(dt.getString(i, 1));
				goodsStockSchema.setcreateDate(new Date());
				goodsStockSchema.setcreateUser("system");
				set.add(goodsStockSchema);
			}
			trans.add(set, Transaction.INSERT);
		}
	}
	
	/**
	 * 扣除积分、生成积分明细
	 * @param memberId
	 * @param giftClassify
	 * @param orderSn
	 * @param usedPoint
	 * @param totalAmount
	 * @param trans
	 * @return
	 */
	private Map<String, String> subtractPoint(String memberId, GiftClassify giftClassify, String orderSn, String usedPoint, String totalAmount, Transaction trans) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, IntegralConstant.SUCCESS);
		try {
			Member member = memberService.load(memberId);
			if (member == null) {
				jsonMap.put(STATUS, IntegralConstant.FAIL);
				jsonMap.put(IntegralConstant.MESSAGE, "会员信息不存在，请重新登录！");
				return jsonMap;
			}
			
			if (member.getCurrentValidatePoint() <= 0 || member.getCurrentValidatePoint() < Integer.parseInt(usedPoint)) {
				jsonMap.put(STATUS, IntegralConstant.FAIL);
				jsonMap.put(MESSAGE, "您的积分不够了，快去赚吧！");
				Object[] argArr = {orderSn, member.getCurrentValidatePoint(), usedPoint};
				logger.info("兑换保险产品可用积分不足。订单号：{}会员积分为：{} 兑换商品需要积分:{}", argArr);
				return jsonMap;
			}
			QueryBuilder qb;
			if (StringUtil.isEmpty(member.getVersion())) {
				qb = new QueryBuilder("update member set currentValidatePoint=?, usedPoint=?, version='1', modifyDate=? where id=? and (version is null or version='')");
				qb.add(member.getCurrentValidatePoint() - Integer.parseInt(usedPoint));
				qb.add(member.getUsedPoint() + Integer.parseInt(usedPoint));
				qb.add(DateUtil.getCurrentDateTime());
				qb.add(member.getId());
			} else {
				qb = new QueryBuilder("update member set currentValidatePoint=?, usedPoint=?, version=version+1, modifyDate=? where id=? and version=?");
				qb.add(member.getCurrentValidatePoint() - Integer.parseInt(usedPoint));
				qb.add(member.getUsedPoint() + Integer.parseInt(usedPoint));
				qb.add(DateUtil.getCurrentDateTime());
				qb.add(member.getId());
				qb.add(member.getVersion());
			}
			trans.add(qb);
			trans.add(dealSDIntCalendar(giftClassify, memberId, usedPoint, totalAmount), Transaction.INSERT);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonMap.put(STATUS, IntegralConstant.FAIL);
			jsonMap.put(IntegralConstant.MESSAGE, "扣除积分操作失败，请重新兑换或联系客服！");
		}
		return jsonMap;
	}
	
	/**
	 * 生成积分明细
	 * @param giftClassify
	 * @param memberId
	 * @param usedPoint
	 * @param totalAmount
	 * @return
	 */
	private SDIntCalendarSchema dealSDIntCalendar(GiftClassify giftClassify, String memberId, String usedPoint, String totalAmount) {
		SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
		tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
		tSDIntCalendarSchema.setMemberId(memberId);
		tSDIntCalendarSchema.setIntegral(usedPoint);
		tSDIntCalendarSchema.setSource(cn.com.sinosoft.util.Constant.POINT_SOURCE_EXCHANGE);// 积分来源
		tSDIntCalendarSchema.setManner("1");// 表示收入
		tSDIntCalendarSchema.setStatus("0");
		tSDIntCalendarSchema.setProp1("point");
		tSDIntCalendarSchema.setProp2(totalAmount);
		tSDIntCalendarSchema.setDescription(giftClassify.getGiftTitle());
		tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDIntCalendarSchema.setsvaliDate(PubFun.getCurrentDate());
		tSDIntCalendarSchema.setBusinessid(giftClassify.getId());

		try {
			Map<String, Object> map = new PointsCalculate().pointsManage(IntegralConstant.POINT_SEARCH,
					IntegralConstant.POINT_SOURCE_EXCHANGE, null);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get(IntegralConstant.DATA);
			if (list.size() > 0) {
				Map<String, Object> map_data = list.get(0);
				tSDIntCalendarSchema.setDescription(String.valueOf(map_data.get("PointDes")) + " 使用积分：" + usedPoint);
			} else {
				tSDIntCalendarSchema.setDescription(IntegralConstant.POINT_SOURCE_EXCHANGE_DES + " 使用积分：" + usedPoint);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return tSDIntCalendarSchema;
	}
	
	/**
	 * 更新积分兑换表状态
	 * @param orderSn
	 * @param memberId
	 * @param usedPoint
	 * @param trans
	 */
	private void dealPointExchangeInfo(String orderSn, String memberId, String usedPoint, Transaction trans) {
		QueryBuilder qb = new QueryBuilder("update PointExchangeInfo set status = ?, points = ? , memberid = ? where orderSn = ?");
		qb.add("7");
		qb.add(usedPoint);
		qb.add(memberId);
		qb.add(orderSn);
		trans.add(qb);
	}
	
	/**
	 * 生成交易信息
	 * @param orderSn
	 * @param memberId
	 * @param param
	 * @param trans
	 */
	private void writeBackPayInfo(String orderSn, String memberId, Map<String, String> param, Transaction trans) {
		String payType = "zerozf";
		String paySn = param.get("paySn");
		String orderIntegral = param.get("orderIntegral");
		String totalAmount = param.get("totalAmount");
	
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();// 得到当前系统时间
		String currTime = formatter.format(currentTime);
		
		String id = new QueryBuilder("select id from tradeInformation where ordID=?", orderSn).executeString();
		if (StringUtil.isEmpty(id)) {
			TradeInformationSchema tradeInformation = new TradeInformationSchema();
			tradeInformation.setid(PubFun.GetTradeInformationID(orderSn));
			tradeInformation.setcreateDate(currentTime);
			tradeInformation.setmodifyDate(currentTime);
			tradeInformation.setordAmt(totalAmount);
			tradeInformation.setordID(orderSn);
			tradeInformation.setpayType(payType);
			tradeInformation.setmerId(memberId);
			tradeInformation.setpayStatus("1");
			tradeInformation.setsendDate(currTime);
			tradeInformation.setreceiveDate(currTime);
			tradeInformation.settradeCheckSeriNo(paySn);
			tradeInformation.settradeSeriNO(paySn);
			tradeInformation.settradeResult("0");
			trans.add(tradeInformation, Transaction.INSERT);

		} else {
			QueryBuilder qb = new QueryBuilder("update tradeInformation set ordAmt=?, payType=?, merId=?, payStatus='1', tradeCheckSeriNo=?, tradeSeriNO=?, sendDate=?, receiveDate=?, tradeResult='0'  where id=?");
			qb.add(totalAmount);
			qb.add(payType);
			qb.add(memberId);
			qb.add(paySn);
			qb.add(paySn);
			qb.add(currTime);
			qb.add(currTime);
			qb.add(id);
			trans.add(qb);
		}
		
		TradeSummaryInfoSchema tradesummaryinfoschema = new TradeSummaryInfoSchema();
		tradesummaryinfoschema.setid(NoUtil.getMaxNo("TradeSummaryID", 11));
		tradesummaryinfoschema.setPaySn(paySn);
		tradesummaryinfoschema.setTradeSn(paySn);
		tradesummaryinfoschema.setTradeResult("0");
		tradesummaryinfoschema.setPointSumAmount(orderIntegral);
		tradesummaryinfoschema.setOrderSns(orderSn);
		tradesummaryinfoschema.setPayType(payType);
		DataTable dt_paytypename = new QueryBuilder(
				"SELECT description FROM paybase WHERE paytype=?", payType)
				.executeDataTable();
		if (dt_paytypename.getRowCount() > 0) {
			tradesummaryinfoschema.setPayTypeName(dt_paytypename
					.getString(0, 0));
		} else {
			tradesummaryinfoschema.setPayTypeName("无支付名称");
		}
		tradesummaryinfoschema.setTotalAmount(totalAmount);
		// 支付金额
		tradesummaryinfoschema.setTradeAmount("0");
		tradesummaryinfoschema.setCreateDate(new Date());
		trans.add(tradesummaryinfoschema, Transaction.INSERT);
	}
	
	/**
	 * 更新订单表状态
	 * @param orderSn
	 * @param trans
	 */
	private void updateOrder(String orderSn, Transaction trans) {
		trans.add(new QueryBuilder("update sdorders set orderStatus='7', payStatus='2' where orderSn=? ", orderSn));
	}
	
	/**
	 * 更新礼品表库存、人气
	 * @param mGiftClassifySchema
	 * @return 
	 */
	private void updateGiftNum(GiftClassify giftClassify, int count, Transaction trans) {
		QueryBuilder qb;
		String lastNum = String.valueOf(Integer.parseInt(giftClassify.getLastNum()) - count);
		String popularity = String.valueOf(Integer.parseInt(giftClassify.getPopularity()) + count);
		if (StringUtil.isEmpty(giftClassify.getVersion())) {
			qb = new QueryBuilder("update GiftClassify set lastNum=?, popularity=?, version='1' where id=? and (version is null or version='') ");
			qb.add(lastNum);
			qb.add(popularity);
			qb.add(giftClassify.getId());
		} else {
			qb = new QueryBuilder("update GiftClassify set lastNum=?, popularity=?, version=version+1 where id=? and version=? ");
			qb.add(lastNum);
			qb.add(popularity);
			qb.add(giftClassify.getId());
			qb.add(giftClassify.getVersion());
		}
		trans.add(qb);
	}
}
