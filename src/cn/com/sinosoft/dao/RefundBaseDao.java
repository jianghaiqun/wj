package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.RefundBase;



public interface RefundBaseDao extends BaseDao<RefundBase, String>{
	public RefundBase getRefundBaseByReturnType(String returnType);

}
