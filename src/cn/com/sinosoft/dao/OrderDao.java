package cn.com.sinosoft.dao;

import java.util.Date;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Order;

/**
 * Dao接口 - 订单
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT9B6182BB453DB3970191ECBF6F4D8AD0
 * ============================================================================
 */

public interface OrderDao extends BaseDao<Order, String> {
	
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
	
	public Order getOrderByOrderSn(String orderSn);
	
	public Order getOrderById(String orderId);
	//====吴高强添加结束===============
}