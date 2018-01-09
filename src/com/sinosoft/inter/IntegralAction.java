package com.sinosoft.inter;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Order;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.ibrms.RuleIntegral;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.memberSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 横向积分接口
 * ============================================================================
 * 
 * KEY:SINOSOFT9A02B310F538BFE58B8F04583E341B6E
 * ============================================================================
 */

public class IntegralAction {
	private static final Logger logger = LoggerFactory.getLogger(IntegralAction.class);
	/**
	 * 
	 * @param actionId
	 *            动作id.
	 * @param map
	 * 
	 * @return boolean
	 */
	public static boolean deal(String actionId, Map<String, Object> data, String actionType) {
		if ("REGISTER".equals(actionType)) {// 成功注册赠送积分
			registerSuccess(actionId, data);

		} else if ("ZFS".equals(actionType)) {// 支付完成赠送积分(member对象、order对象)
			logger.info("支付完成调用积分接口====================");
			paySuccess(actionId, data);

		} else if ("COMMENT".equals(actionType)) {// 评论送积分
			commentSuccess(actionId, data);
		}

		return true;
	}

	private static void commentSuccess(String actionId, Map<String, Object> data) {
		Member member = (Member) data.get("Member");
		int point = (Integer) data.get("Points");
		String orderSn = (String) data.get("orderSn");
		SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
		tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
		tSDIntCalendarSchema.setMemberId(member.getId());
		tSDIntCalendarSchema.setIntegral(point + "");
		tSDIntCalendarSchema.setSource("1");// 评论
		tSDIntCalendarSchema.setStatus("0");// 正常
		tSDIntCalendarSchema.setManner("0");// 表示收入
		tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDIntCalendarSchema.setBusinessid(orderSn);// 订单号
		Map param=new HashMap();
		param.put("PointsGive","01");
		try {
			Map<String, Object> map = new PointsCalculate().pointsManage(IntegralConstant.POINT_SEARCH,"1", param);
			List<Map<String, Object>> list=(List<Map<String, Object>>) map.get(IntegralConstant.DATA);
			if(list.size()>0){
				Map map_data=list.get(0);
				tSDIntCalendarSchema.setDescription(String.valueOf(map_data.get("PointDes")));
			}else{
				tSDIntCalendarSchema.setDescription("评论产品");
				logger.warn("评论产品查询规则无数据，");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		tSDIntCalendarSchema.insert();
		
		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(member.getId());
		tmemberSchema.fill();
		Integer currentPoint = tmemberSchema.getcurrentValidatePoint();
		if (currentPoint != null) {
			point += currentPoint;
		}
		tmemberSchema.setcurrentValidatePoint(point);
		tmemberSchema.update();
	}
	
	private static void registerSuccess(String actionId, Map<String, Object> data) {
		Member member = (Member) data.get("Member");
		// int rigisterDate=(Integer)data.get("rigisterDate");
		// PayBOM tPayBOM = new PayBOM();
		// tPayBOM.setDate(rigisterDate);//注册时间
		// tPayBOM.setIsRegister(1);//1表示注册
		// String sql = RuleSqlAssemble.REGISTERSENDPOINT;//注册规则
		// RuleInlet tRuleInlet = new RuleInlet(sql, tPayBOM);
		// tRuleInlet.excuteAppoint();

		int newIntegral = RuleIntegral.getIntegral(member.getId(), 0, "", 0);

		SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
		tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
		tSDIntCalendarSchema.setMemberId(member.getId());
		tSDIntCalendarSchema.setIntegral(newIntegral + "");
		tSDIntCalendarSchema.setSource(actionId);
		tSDIntCalendarSchema.setManner("0");// 表示收入
		tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		Map param=new HashMap();
		param.put("PointsGive","01");
		try {
			Map<String, Object> map = new PointsCalculate().pointsManage(IntegralConstant.POINT_SEARCH,"4", param);
			List<Map<String, Object>> list=(List<Map<String, Object>>) map.get(IntegralConstant.DATA);
			if(list.size()>0){
				Map map_data=list.get(0);
				tSDIntCalendarSchema.setDescription(String.valueOf(map_data.get("PointDes")));
			}else{
				tSDIntCalendarSchema.setDescription("注册送积分");
				logger.warn("注册送积分查询规则无数据，");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		tSDIntCalendarSchema.insert();

		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(member.getId());
		tmemberSchema.fill();
		DecimalFormat myformat = new DecimalFormat("#####0");
		tmemberSchema.setcurrentValidatePoint(myformat.format(newIntegral));
		tmemberSchema.update();
	}

	private static void paySuccess(String actionId, Map<String, Object> data) {
		try {
			Member member = (Member) data.get("Member");// 登录后支付
			double rate = Double.parseDouble(data.get("rate") + "");
			String productType = (String) data.get("productType");
			double paidOrdAmt = Double.parseDouble(data.get("paidOrdAmt") + "");
			String orderSn = (String) data.get("OrderSn");
			Order order = (Order) data.get("Order");
			int newIntegral = 0;
			Object[] argArr = {paidOrdAmt, productType, rate, member.getId()};
			logger.info("规则计算参数：paidOrdAmt:{} productType:{} rate:{} member.getId():{}", argArr);
			if (!"".equals(member.getId()) && member.getId() != null) {
				newIntegral = RuleIntegral.getIntegral(member.getId(), paidOrdAmt, productType, rate);

				// SDIntCalendarSchema tSDIntCalendarSchema = new
				// SDIntCalendarSchema();
				// tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
				// tSDIntCalendarSchema.setMemberId(member.getId());
				// tSDIntCalendarSchema.setIntegral(newIntegral + "");
				// tSDIntCalendarSchema.setSource("0");// 表示购买产品
				// tSDIntCalendarSchema.setBusinessid(order.getOrderSn());// 业务号
				// tSDIntCalendarSchema.setManner("0");// 表示收入
				// tSDIntCalendarSchema.setStatus("1"); // 保单没有生效冻结
				// tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
				// tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
				// tSDIntCalendarSchema.insert();
				//				
				// memberSchema tmemberSchema = new memberSchema();
				// tmemberSchema.setid(member.getId());
				// tmemberSchema.fill();
				// DecimalFormat myformat = new DecimalFormat("#####0");
				// tmemberSchema.setpoint(Integer.parseInt(myformat.format(newIntegral)));
				// tmemberSchema.update();

			} else {
				newIntegral = RuleIntegral.getIntegral(null, paidOrdAmt, productType, rate);
			}
			logger.info("规则计算出的积分为：{}", newIntegral);
			logger.info("规则订单为：{} order.getOrderSn():{}", orderSn, order.getOrderSn());
			if (StringUtil.isEmpty(orderSn)) {
				orderSn = order.getOrderSn();
			}

			GetDBdata db = new GetDBdata();
			logger.info("规则计算出的积分为1：{}", db.getOneValue("select point from orders where ordersn='" + orderSn + "'"));
			QueryBuilder qb = new QueryBuilder("update orders set point = ?, pointFrom = ? where orderSn = ?");
			qb.add(newIntegral);
			qb.add(actionId);
			qb.add(orderSn);
			int a = qb.executeNoQuery();
			logger.info("规则计算出的积分为2：{}",  db.getOneValue("select point from orders where ordersn='" + orderSn + "'"));
			Transaction trans = new Transaction();
			trans.add(new QueryBuilder("update orders set point = " + newIntegral + ", pointFrom = '" + actionId + "' where orderSn = ?", orderSn));
			boolean b = trans.commit();
			logger.info("更改订单积分   {} --------------- {}", a, b);
			logger.info("规则计算出的积分为3：{}", db.getOneValue("select point from orders where ordersn='" + orderSn + "'"));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public int paySuccess(double poundAge, double paidAmt, String productType) {
		int newIntegral = RuleIntegral.getIntegral("", paidAmt, productType, poundAge);
		DecimalFormat myformat = new DecimalFormat("#####0");
		return Integer.parseInt(myformat.format(newIntegral));
	}

	public static int paySuccess_(String id, String poundAge, String paidAmt, String productType) {
		int newIntegral = RuleIntegral.getIntegral(id, Double.parseDouble(paidAmt), productType, Double.parseDouble(poundAge));
		return newIntegral;
	}

	public static void main(String[] args) {
		Map<String, Object> data = new HashMap<String, Object>();
		Member member = new Member();
		// member.setId("zhou");
		Order order = new Order();
		order.setOrderSn("2012000000000717");
		String appName = "123";
		data.put("rate", 0.3);
		data.put("paidOrdAmt", 2);
		data.put("Member", member);
		data.put("Order", order);
		data.put("appName", appName);
		data.put("IntType", "ZFS");
		data.put("productType", "A");
		deal("ttttttttt", data, "ZFS");
	}
}