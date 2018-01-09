package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.RefundBaseDao;
import cn.com.sinosoft.entity.RefundBase;
@Repository
public class RefundBaseDaoImpl extends BaseDaoImpl<RefundBase, String> implements RefundBaseDao{

	@Override
	public RefundBase getRefundBaseByReturnType(String returnType) {
		String hql = " from RefundBase refundBase where refundBase.returnType = ? ";
		RefundBase refundBase = (RefundBase) getSession().createQuery(hql).setParameter(0, returnType).uniqueResult();
		return refundBase;
	}

}
