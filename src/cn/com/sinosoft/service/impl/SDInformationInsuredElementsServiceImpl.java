package cn.com.sinosoft.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.SDInformationInsuredElementsDao;
import cn.com.sinosoft.entity.SDInformationInsuredElements;
import cn.com.sinosoft.service.SDInformationInsuredElementsService;

/**
 * Service实现类 - 被保人投保要素
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
public class SDInformationInsuredElementsServiceImpl extends BaseServiceImpl<SDInformationInsuredElements, String> implements SDInformationInsuredElementsService {

	@Resource
	private SDInformationInsuredElementsDao sdinformationInsuredElementsDao;
	@Resource
	public void setBaseDao(SDInformationInsuredElementsDao sdinformationInsuredElementsDao) {
		super.setBaseDao(sdinformationInsuredElementsDao);
	}
	@Override
	public SDInformationInsuredElements findIsExist(String appFactorCode, String informationInsured_id) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("informationInsured_id","=",informationInsured_id));
		qbs.add(createQB("elementsSn","=",appFactorCode));
		List<SDInformationInsuredElements> list = sdinformationInsuredElementsDao.findByQBs(qbs, "id", "desc");
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
	
}