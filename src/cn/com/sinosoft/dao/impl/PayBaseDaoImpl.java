package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.PayBaseDao;
import cn.com.sinosoft.entity.PayBase;
import cn.com.sinosoft.entity.Product;

@Repository
public class PayBaseDaoImpl extends BaseDaoImpl<PayBase, String> implements PayBaseDao {

	/**
	 * 通过支付类型获取支付基础信息对象
	 * 
	 * @param payType
	 * @return
	 */
	public PayBase getPayBaseByPayType(String payType ) {
		String hql = "from PayBase as payBase where payBase.payType = ? ";
		return (PayBase) getSession().createQuery(hql).setParameter(0, payType).uniqueResult();
	}

	public List<PayBase> getPayBaseList(String type) {
		String hql = "from PayBase where type=? order by orderflag";
		return getSession().createQuery(hql).setParameter(0, type).list();
	}
}
