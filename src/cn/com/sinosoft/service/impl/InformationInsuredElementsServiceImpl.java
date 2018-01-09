package cn.com.sinosoft.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.InformationInsuredElementsDao;
import cn.com.sinosoft.entity.InformationInsuredElements;
import cn.com.sinosoft.service.InformationInsuredElementsService;

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
public class InformationInsuredElementsServiceImpl extends BaseServiceImpl<InformationInsuredElements, String> implements InformationInsuredElementsService {

	@Resource
	private InformationInsuredElementsDao informationInsuredElementsDao;
	@Resource
	public void setBaseDao(InformationInsuredElementsDao informationInsuredElementsDao) {
		super.setBaseDao(informationInsuredElementsDao);
	}
	@Override
	public InformationInsuredElements findIsExist(String appFactorCode, String informationInsured_id) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("informationInsured_id","=",informationInsured_id));
		qbs.add(createQB("elementsSn","=",appFactorCode));
		List<InformationInsuredElements> list = informationInsuredElementsDao.findByQBs(qbs, "id", "desc");
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