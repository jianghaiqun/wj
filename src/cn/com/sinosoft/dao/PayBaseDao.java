package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.PayBase;

/**
 * Dao接口 - 支付基础信息管理
 * ============================================================================
 * KEY:SINOSOFT6FB6866ABE8BB8E7249ABB5DCF4C570F
 * ============================================================================
 */
public interface PayBaseDao extends BaseDao<PayBase, String> {
	/**
	 * 通过支付类型获取支付基础信息对象
	 * 
	 * @param payType
	 * @return
	 */
	public PayBase getPayBaseByPayType(String payType);

	public List<PayBase> getPayBaseList(String payType);
}
