/**
 * @CopyRight:Sinosoft
 * @Project:ZKING-PORTAL
 * @File:RateTempServiceImpl.java
 * @CreateTime:2012-4-26 上午7:26:16
 * @Package:cn.com.sinosoft.zking.service.impl
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.RateTempDao;
import cn.com.sinosoft.entity.PremiumRateTemp;
import cn.com.sinosoft.service.RateTempService;

/**
 * @author LiuXin
 *
 */
@Service
public class RateTempServiceImpl extends BaseServiceImpl<PremiumRateTemp,Long> implements RateTempService{
	@Resource
	private RateTempDao rateTempDao;
	@Resource
	public void setRateTempDao(RateTempDao rateTempDao) {
		super.setBaseDao(rateTempDao);
	}
	@Override
	public boolean deleteRateTempBySql(String sql) {
		return rateTempDao.deleteRateTempBySql(sql);
	}
	@Override
	public boolean savePrt(PremiumRateTemp prt) {
		return rateTempDao.savePrt(prt);
	}
	
}
