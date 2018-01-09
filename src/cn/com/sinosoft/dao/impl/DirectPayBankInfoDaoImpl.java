/**
 * 
 */
package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.DirectPayBankInfoDao;
import cn.com.sinosoft.entity.DirectPayBankInfo;

/**
 * @author gaohaijun
 */
@Repository
public class DirectPayBankInfoDaoImpl extends BaseDaoImpl<DirectPayBankInfo, String> implements DirectPayBankInfoDao {

	@Override
	public DirectPayBankInfo getByOrderSn(String orderSn) {
		String hql = "from DirectPayBankInfo as bankInfo where bankInfo.orderSn = ?"; 
		return (DirectPayBankInfo) getSession().createQuery(hql).setParameter(0, orderSn).uniqueResult();
	}
}
