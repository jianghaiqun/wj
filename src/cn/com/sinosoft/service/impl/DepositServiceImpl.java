package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.dao.DepositDao;
import cn.com.sinosoft.entity.Deposit;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.DepositService;

/**
 * Service实现类 - 预存款记录
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT622F01BF112F3C630E9962A378F07787
 * ============================================================================
 */

@Service
public class DepositServiceImpl extends BaseServiceImpl<Deposit, String> implements DepositService {

	@Resource
	private DepositDao depositDao;
	
	@Resource
	public void setBaseDao(DepositDao depositDao) {
		super.setBaseDao(depositDao);
	}
	
	public Pager getDepositPager(Member member, Pager pager) {
		return depositDao.getDepositPager(member, pager);
	}

}