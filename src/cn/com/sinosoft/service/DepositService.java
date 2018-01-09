package cn.com.sinosoft.service;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.entity.Deposit;
import cn.com.sinosoft.entity.Member;

/**
 * Service接口 - 预存款记录
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTF47E6B6DF0CEBF23BACB777361377447
 * ============================================================================
 */

public interface DepositService extends BaseService<Deposit, String> {
	
	
	/**
	 * 根据Member、Pager获取充值记录分页对象
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @param pager
	 *            Pager对象
	 *            
	 * @return 充值记录分页对象
	 */
	public Pager getDepositPager(Member member, Pager pager);
	
}