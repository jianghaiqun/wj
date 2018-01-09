package cn.com.sinosoft.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.SDRelationRecognizeeDao;
import cn.com.sinosoft.entity.SDRelationRecognizee;
import cn.com.sinosoft.service.SDRelationRecognizeeService;

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
public class SDRelationRecognizeeServiceImpl extends BaseServiceImpl<SDRelationRecognizee, String> implements SDRelationRecognizeeService {
	@Resource
	private SDRelationRecognizeeDao mSDRelationRecognizeeDao;
	
	@Override
	public SDRelationRecognizee getSDRelationRecognizeeInfo(String comCode,String productID,String memberId,String recognizeeName){
		return mSDRelationRecognizeeDao.getSDRelationRecognizeeInfo(comCode,productID, memberId, recognizeeName);
	}
	@Override
	public List<SDRelationRecognizee> getSDRelationRecognizeeInfoList(String comCode,String productID,String memberId,String recognizeeName){
		return mSDRelationRecognizeeDao.getSDRelationRecognizeeInfoList(comCode,productID, memberId, recognizeeName);
	}
	@Resource
	public void setBaseDao(SDRelationRecognizeeDao mSDRelationRecognizeeDao) { 
		super.setBaseDao(mSDRelationRecognizeeDao);
	}
	
	
}