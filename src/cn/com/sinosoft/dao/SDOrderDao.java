package cn.com.sinosoft.dao;

import java.util.Date;
import java.util.List;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.SDOrder;

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

public interface SDOrderDao extends BaseDao<SDOrder, String> {

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
	// public Long getUnprocessedOrderCount();

	/**
	 * 获取已支付未发货订单数（不包含已完成或已作废订单）
	 * 
	 * @return 已支付未发货订单数
	 */
	// public Long getPaidUnshippedOrderCount();

	public Order getLastOrder();

	// ====吴高强添加开始===============
	/**
	 * 
	 * @param orderSn
	 *            订单编号
	 * @param orderStatus
	 *            订单状态
	 * @param applicant
	 *            投保人
	 * @param hdate
	 *            下单止期
	 * @param ldate
	 *            下单起期
	 * @param pager
	 *            Pager对象
	 * @param member
	 *            Member对象
	 * @return 根据条件筛选订单
	 */

	public Pager getOrderPager(String orderSn, String orderStatus,
			String applicant, Date hdate, Date ldate, Pager pager, Member member);

	public SDOrder getOrderByOrderSn(String orderSn);

	public Order getOrderById(String orderId);

	// ====吴高强添加结束===============

	public List<SDOrder> getOrderByPaySn(String paySn);

	public List<SDOrder> getOrderByOrderSns(String OrderSns);

	/**
	 * 
	 * selectPartnerInfoByPaySn:(通过交易流水号，关联渠道编号channelsn，查询合作商户信息表是否有数据). <br/>
	 *
	 * @author xialianfu
	 * @param strPaySn
	 * @return
	 */
	public int selectPartnerInfoByPaySn(String strPaySn);

	/**
	 * 
	 * selectPartnerInfoByOrderSn:(通过订单号号，关联渠道编号channelsn，查询合作商户信息表是否有数据). <br/>
	 *
	 * @author xialianfu
	 * @param strOrderSn
	 * @return
	 */
	public int selectPartnerInfoByOrderSn(String strOrderSn);

}