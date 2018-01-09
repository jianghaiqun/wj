package cn.com.sinosoft.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.SDInformationRiskTypeDao;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationRiskType;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.service.SDInformationRiskTypeService;

/**
 * Service实现类 - 险种
 * ============================================================================
 *  
 *
 *
 * KEY:SINOSOFTA46293E39B40E5C54C6BC841B973A701
 * ============================================================================
 */

@Service
public class SDInformationRiskTypeServiceImpl extends BaseServiceImpl<SDInformationRiskType, String> implements SDInformationRiskTypeService {

	@Resource
	private SDInformationRiskTypeDao sdinformationRiskTypeDao;
	@Resource
	public void setBaseDao(SDInformationRiskTypeDao sdinformationRiskTypeDao) {
		super.setBaseDao(sdinformationRiskTypeDao);
	}
	
	@Override
	public SDInformationRiskType getInformationRiskType(SDOrder sdor,
			SDInformation sdinf, SDInformationAppnt sdapp, String recognizeeSn) {
		SDInformationRiskType sdinformationRiskTypes = new SDInformationRiskType();
		sdinformationRiskTypes.setOrderSn(sdor.getOrderSn());
		sdinformationRiskTypes.setInformationSn(sdinf.getInformationSn());
		sdinformationRiskTypes.setRecognizeeSn(recognizeeSn);
		sdinformationRiskTypes.setApplicantSn(sdapp.getApplicantSn());
		sdinformationRiskTypes.setPolicyNo("");
		sdinformationRiskTypes.setRiskCode(sdinf.getProductId());
		sdinformationRiskTypes.setRiskName(sdinf.getProductName());
		sdinformationRiskTypes.setMult("1");
		sdinformationRiskTypes.setSvaliDate(sdinf.getStartDate());
		sdinformationRiskTypes.setEvaliDate(sdinf.getEndDate());
		sdinformationRiskTypes.setPeriodFlag(sdinf.getChargeType());
		sdinformationRiskTypes.setPeriod(sdinf.getChargeYear());// 缴费年期
		sdinformationRiskTypes.setElectronicCout("");// 电子保单保险公司路径
		sdinformationRiskTypes.setElectronicPath("");// 电子保单物理路径
		sdinformationRiskTypes.setInsurerFlag("");
		sdinformationRiskTypes.setInsureMsg("");
		sdinformationRiskTypes.setSdorder(sdor);
		return sdinformationRiskTypes;
		
	}

	@Override
	public SDInformationRiskType getByOrderSnAndRecognizeeSn(String ordersn,
			String recognizeeSn) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("orderSn","=",ordersn));
		qbs.add(createQB("recognizeeSn","=",recognizeeSn));
		List<SDInformationRiskType> list = sdinformationRiskTypeDao.findByQBs(qbs, "id", "asc");
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
	
	public List<SDInformationRiskType> getInformationRiskTypeByOrderSn(String ordersn) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("orderSn","=",ordersn));
		List<SDInformationRiskType> list = sdinformationRiskTypeDao.findByQBs(qbs, "id", "asc");
		
		return list;
	}
}
