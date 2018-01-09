package cn.com.sinosoft.dao.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.dao.SDOrderDao;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.SDOrder;

/**
 * Dao实现类 - 订单
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT8061830D6037E81F0D148994EFFC9C78
 * ============================================================================
 */

@Repository
public class SDOrderDaoImpl extends BaseDaoImpl<SDOrder, String> implements SDOrderDao {
	
	@SuppressWarnings("unchecked")
	public String getLastOrderSn() {
		String hql = "from Order as order order by order.createDate desc";
		List<Order> orderList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (orderList != null && orderList.size() > 0) {
//			String lastOrderSn = orderList.get(0).getOrderSn();
//			System.out.println("------"+lastOrderSn);
//			Double temp = Double.parseDouble(lastOrderSn);
//			temp = temp+2;
//			lastOrderSn = String.valueOf(temp);
			return orderList.get(0).getOrderSn();
		} else {
			return null;
		}
	}
	//默认得到一年的记录
	public Pager getOrderPager(Member member, Pager pager) {
		if (pager == null) {
			pager = new Pager();
		}
		if (pager.getPageSize() == null) {
			pager.setPageSize(Order.DEFAULT_ORDER_LIST_PAGE_SIZE);
		}
	
		Date currentDate = new Date();
		Calendar ca =Calendar.getInstance();
		ca.setTime(currentDate);   //设置时间为当前时间  
		ca.add(Calendar.YEAR, -1); //年份减1  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String cdn=sdf.format(currentDate);
		String cdo=sdf.format(ca.getTime());
		Date cdnew=null;
		Date cdold=null;
		try {
			cdnew = sdf.parse(cdn);
			cdold=sdf.parse(cdo);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
	    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Order.class);
		detachedCriteria.add(Restrictions.eq("member", member));
		detachedCriteria.add(Restrictions.lt("createDate",cdnew)) ;
		detachedCriteria.add(Restrictions.gt("createDate",cdold)) ;
		return super.findByPager(pager, detachedCriteria);
	}
	
	//=================吴高强添加开始=====================================
	
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
	public Pager getOrderPager(String orderSn,String orderStatus, String applicant,Date hdate,Date ldate, Pager pager,Member member) {
		if (pager == null) {
			pager = new Pager();
		}
		if (pager.getPageSize() == null) {
			pager.setPageSize(Order.DEFAULT_ORDER_LIST_PAGE_SIZE);
		}
		//查询条件        订单编号、下单时间、订单状态、投保人
	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Order.class);
		detachedCriteria.add(Restrictions.eq("member", member));
		if(!(orderSn==null||"".equals(orderSn))){
		detachedCriteria.add(Restrictions.eq("orderSn", orderSn));}
		if(!(orderStatus==null||"".equals(orderStatus)))
		detachedCriteria.add(Restrictions.eq("order.orderStatus", orderStatus));
		if(!(applicant==null||"".equals(applicant)))
		detachedCriteria.add(Restrictions.eq("order.member.realName", applicant));
		if(hdate!=null)
		detachedCriteria.add(Restrictions.lt("createDate", hdate));
		if(ldate!=null)
		detachedCriteria.add(Restrictions.gt("createDate", ldate));
		return super.findByPager(pager, detachedCriteria);
	}	
	
	
	//=================吴高强添加结束=====================================
	
//	public Long getUnprocessedOrderCount() {
//		String hql = "select count(*) from Order as order where order.orderStatus = ?";
//		return (Long) getSession().createQuery(hql).setParameter(0, OrderStatus.unprocessed).uniqueResult();
//	}
	
//	public Long getPaidUnshippedOrderCount() {
//		String hql = "select count(*) from Order as order where order.paymentStatus = ? and order.orderStatus != ? and order.orderStatus != ?";
//		return (Long) getSession().createQuery(hql).setParameter(0, PaymentStatus.paid).setParameter(1, OrderStatus.completed).setParameter(2, OrderStatus.invalid).uniqueResult();
//	}

	@Override
	public Order getLastOrder() {
		String hql = "from Order as order order by order.createDate desc";
		Order order = new Order();
		order = (Order) getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).uniqueResult();
		if(order == null){
			return null;
		}
		return order;
			
	}
	public SDOrder getOrderByOrderSn(String orderSn){
		String hql = "from SDOrder as order where order.orderSn=?";
		SDOrder sdorder = new SDOrder();
		sdorder = (SDOrder) getSession().createQuery(hql).setParameter(0, orderSn).uniqueResult();
		if(sdorder == null){
			return null;
		}
		return sdorder;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SDOrder> getOrderByPaySn(String paySn) {
		String hql = "from SDOrder as order where order.paySn like ?";
		return (List<SDOrder>) getSession().createQuery(hql).setParameter(0, paySn + "%").list();
		//.setParameter(1, area.getPath() + "%")
	}
	
	public Order getOrderById(String orderId){
		String hql = "from Order as order where order.id=?";
		Order order = new Order();
		order = (Order) getSession().createQuery(hql).setParameter(0, orderId).uniqueResult();
		if(order == null){
			return null;
		}
		return order;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SDOrder> getOrderByOrderSns(String orderSns) {
		String hql = "from SDOrder where orderSn in ('"+orderSns+"')";

		return (List<SDOrder>) getSession().createQuery(hql).list();
	}
	
	/**
	 * 
	 * selectPartnerInfoByPaySn:(通过交易流水号，关联渠道编号channelsn，查询合作商户信息表是否有数据). <br/> 
	 *
	 * @author xialianfu
	 * @param strPaySn
	 * @return
	 */
	public int selectPartnerInfoByPaySn(String strPaySn) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(1) ");
		sql.append("FROM sdorders s, partnerinfo p ");
		sql.append(" WHERE s.channelsn = p.channelSn  ");
		sql.append(" AND s.paySn = '"+strPaySn+"'  ");
		BigInteger id = (BigInteger) getSession().createSQLQuery(sql.toString()).uniqueResult();
		return id.intValue(); 
	} 
	
	/**
	 * 
	 * selectPartnerInfoByOrderSn:(通过订单号号，关联渠道编号channelsn，查询合作商户信息表是否有数据). <br/>
	 *
	 * @author xialianfu
	 * @param strOrderSn
	 * @return
	 */
	public int selectPartnerInfoByOrderSn(String strOrder) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(1) ");
		sql.append("FROM sdorders s, partnerinfo p ");
		sql.append(" WHERE s.channelsn = p.channelSn  ");
		sql.append(" AND s.orderSn = '"+strOrder+"'  ");
		BigInteger id = (BigInteger) getSession().createSQLQuery(sql.toString()).uniqueResult();
		return id.intValue(); 
	} 

}