package cn.com.sinosoft.util;

import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.TradeInformationService;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;


/**
 * 频道活动专用特殊处理方法类
 * @author gaohaijun
 *
 */
public class BrandDeal {

	private static final Logger logger = LoggerFactory.getLogger(BrandDeal.class);
	/**
	 * 黄泥川越野跑(207101082:史带开心赛事含高风险运动WAP)
	 */
	public boolean brand207101082(Map<String, Object> mServiceMap, String orderSn) {
		String channelSn = "brand_02";
		SDOrderService sdOrderService = (SDOrderService) mServiceMap.get("SDOrderService");
		TradeInformationService tradeInformationService = (TradeInformationService) mServiceMap.get("TradeInformationService");
		
		try {
			// 通过产品中心获取产品初始价格
			Map<String, Object> paramter = sdOrderService.getProductInformation("207101074", "N", "");// 使用主站“史带开心赛事含高风险运动”产品获取
			String initPrem = ((String[])paramter.get("baseInformation"))[6];
			BigDecimal price = BigDecimal.valueOf(Double.valueOf(initPrem));
			
			SDOrder sdOrder = sdOrderService.getOrderByOrderSn(orderSn);
			// 通过配置项获取活动名称，设置活动流水号
			String activityName = Config.getValue("Brand02ActivityName");
			String actSql = "SELECT activitySn FROM sdCouponActivityInfo WHERE status <> 1 AND title = ?";
			String activitySn = new QueryBuilder(actSql, activityName).executeString();
			if (StringUtil.isEmpty(activitySn)) {
				logger.info("------黄泥川越野跑零折活动未设置！");
			} else {
				sdOrder.setActivitySn(activitySn);
			}
			sdOrder.setProductTotalPrice(price);// 商品总价打折前
			sdOrder.setTotalAmount(price);// 保费即打折后商品价格
			sdOrder.setOrderActivity(initPrem);// 单个订单优惠金额
			sdOrder.setSumActivity(initPrem);// 订单对应活动优惠的总金额
			sdOrder.setChannelsn(channelSn);
			sdOrderService.update(sdOrder);
			
			// 支付信息表更新订单金额
			TradeInformation tradeInformation = tradeInformationService.getTradeInformationByOrdSn(orderSn);
			tradeInformation.setOrdAmt(initPrem);
			tradeInformationService.update(tradeInformation);
			
			// 订单详细表和被保人表的订单原价、折扣价设置为产品原价
			QueryBuilder qb = new QueryBuilder("UPDATE SDInformation SET ProductPrice=?, ProductDiscountPrice=? WHERE orderSn=?");
			qb.add(initPrem);
			qb.add(initPrem);
			qb.add(orderSn);
			qb.executeNoQuery();
			
			QueryBuilder qb2 = new QueryBuilder("UPDATE SDInformationInsured SET RecognizeePrem=?, DiscountPrice=? WHERE orderSn=?");
			qb2.add(initPrem);
			qb2.add(initPrem);
			qb2.add(orderSn);
			qb2.executeNoQuery();
			
			// 保单表保费设置为产品原价，设置活动优惠价格
			QueryBuilder qb3 = new QueryBuilder("UPDATE SDInformationRiskType SET TimePrem=?, ProductPrice=?, CouponValue=?, IntegralValue=?,"
					+ "ActivityValue=?, PayPrice=? WHERE orderSn=?");
			qb3.add(initPrem);
			qb3.add(initPrem);
			qb3.add("0");
			qb3.add("0");
			qb3.add(initPrem);
			qb3.add("0.00");
			qb3.add(orderSn);
			qb3.executeNoQuery();
			
			QueryBuilder qb4 = new QueryBuilder("UPDATE TradeSummaryInfo SET ActivitySumAmount=?, TotalAmount=? WHERE OrderSns=?");
			qb4.add(initPrem);
			qb4.add(initPrem);
			qb4.add(orderSn);
			qb4.executeNoQuery();
			
			return true;
		} catch (Exception e) {
			logger.error("------黄泥川越野跑数据特殊处理过程异常！" + e.getMessage(), e);
			return false;
		}
	}
}
