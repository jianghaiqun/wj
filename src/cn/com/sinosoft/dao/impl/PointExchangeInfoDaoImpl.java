/**
 * 
 */
package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.PointExchangeInfoDao;
import cn.com.sinosoft.entity.PointExchangeInfo;

/**
 * @author Administrator
 *
 */
@Repository
public class PointExchangeInfoDaoImpl extends BaseDaoImpl<PointExchangeInfo, String> implements PointExchangeInfoDao {

	@SuppressWarnings("unchecked")
	public List<PointExchangeInfo> pointexchangelist(String orderSn){
		
		
		String hql = "from PointExchangeInfo as PointExchangeInfo where  PointExchangeInfo.orderSn=?";
		List<PointExchangeInfo> pointlist =  getSession().createQuery(hql).setParameter(0, orderSn).list();
		
		return pointlist;
		
	}
	
}
