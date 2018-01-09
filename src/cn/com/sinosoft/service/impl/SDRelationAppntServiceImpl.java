package cn.com.sinosoft.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDRelationAppnt;
import cn.com.sinosoft.service.SDRelationAppntService;
import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.dao.SDOrderDao;
import cn.com.sinosoft.dao.SDRelationAppntDao;

/**
 * Service实现类 - 品牌
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTA46293E39B40E5C54C6BC841B973A701
 * ============================================================================
 */

@Service
public class SDRelationAppntServiceImpl extends BaseServiceImpl<SDRelationAppnt, String> implements SDRelationAppntService {
	@Resource
	private SDRelationAppntDao mSDRelationAppntDao;
	
	@Override
	public SDRelationAppnt getSDRelationAppntInfo(String comCode,String productID,String memberId,String appntName){
		return mSDRelationAppntDao.getSDRelationAppntInfo(comCode,productID, memberId, appntName);
	}
	@Override
	public List<SDRelationAppnt> getSDRelationAppntInfoList(String comCode,String productID,String memberId,String appntName){
		return mSDRelationAppntDao.getSDRelationAppntInfoList(comCode,productID, memberId, appntName);
	}

	@Resource
	public void setBaseDao(SDRelationAppntDao mSDRelationAppntDao) { 
		super.setBaseDao(mSDRelationAppntDao);
	}
	
	
}