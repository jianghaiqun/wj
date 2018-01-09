package cn.com.sinosoft.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.SDOrder;

/**
 * Service接口 - 订单
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTC25D50D18A27A8E1B4A11F7643DAA055
 * ============================================================================
 */

public interface SDOrderService extends BaseService<SDOrder, String> {

	/**
	 * 获取最后生成的订单编号
	 * 
	 * @return 订单编号
	 */
	public String getLastOrderSn();
	
	/**
	 * 根据Member、Pager获取订单分页对象
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @param pager
	 *            Pager对象
	 *            
	 * @return 订单分页对象
	 */
	public Pager getOrderPager(Member member, Pager pager);
	
	/**
	 * 获取未处理订单数
	 *            
	 * @return 未处理订单数
	 */
//	public Long getUnprocessedOrderCount();
	
	/**
	 * 获取已支付未发货订单数（不包含已完成或已作废订单）
	 *            
	 * @return 已支付未发货订单数
	 */
//	public Long getPaidUnshippedOrderCount();

	public Order getLastOrder();
	//====吴高强添加开始===============
	/**
	 * 
	 * @param orderSn 订单编号
	 * @param orderStatus 订单状态
	 * @param applicant  投保人
	 * @param hdate     下单止期
	 * @param ldate     下单起期
	 * @param pager    Pager对象
	 * @param member   Member对象
	 * @return  根据条件筛选订单
	 */
	
	public Pager getOrderPager(String orderSn,String orderStatus, String applicant,Date hdate,Date ldate, Pager pager,Member member) ;
	public SDOrder getOrderByOrderSn(String orderSn);
	public Order getOrderById(String orderId);
	//====吴高强添加结束===============
	/**
	 * 产品基本信息
	 */
	public Map<String,Object> getProductInformation(String productCode, String BU1,String channelCode);
	/**
	 * 产品保费计算
	 */
	public Map<String,Object> getProductPrem(Map<String,Object> mp);
	/**
	 * 产品保费计算(自动配置用)
	 */
	public Map<String,Object> getProductPremDutyAmounts(Map<String,Object> mp);
	/**
	 * 产品保费计算
	 */
	public String  getCompanyName(String comCode) ;

	/**
	 * 
	 * 重新组装一个责任对象
	 */
	public OrderDutyFactor getNewDutyFactor(OrderDutyFactor df, String dutyValueTemp);

	public String getJRHSURLByConfig(String channelCode);

	public void sendErrorMail(String orderSn, String errMsg, String string,
			HttpServletRequest request);
	public void sendErrorMailByPaySn(String paySn, String errMsg, String string,
			HttpServletRequest request);

	public List<String> checkUnderwriting(SDOrder sorder,HttpServletRequest request);
	
	/**
	 * 自动出单的核保
	 * @param insuranceCompany
	 * @param orderSn
	 * @param request
	 * @return
	 */
	public List<String> checkUnderwriting(String insuranceCompany, String orderSn, HttpServletRequest request);

	/**
	 * 
	 * 根据支付流水号查出订单
	 */
	public List<SDOrder> getOrderByPaySn(String paySn);

	/**
	 * 根据订单号判断订单是否需要核保交易
	 * 
	 */
	public String getUWFlagByPaySn(String paySn);   
	
	/**
	 * 
	 * 根据订单号查出订单
	 */
	public List<SDOrder> getOrderByOrderSns(String OrderSns);
}