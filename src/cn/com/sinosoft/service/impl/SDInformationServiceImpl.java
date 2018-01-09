package cn.com.sinosoft.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.SDInformationDao;
import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.service.SDInformationService;

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
public class SDInformationServiceImpl extends BaseServiceImpl<SDInformation, String> implements SDInformationService {

	@Resource
	private SDInformationDao sdinformationDao;
	@Resource
	public void setBaseDao(SDInformationDao sdinformationDao) {
		super.setBaseDao(sdinformationDao);
	}


	@Override
	public SDInformation getByProduct(String id,String orderId) {
		return sdinformationDao.getByProduct(id,orderId);
	}
	@Override
	public SDInformation getByOrP(String orderItemId) {
		return sdinformationDao.getByOrP(orderItemId);
	}
	@Override 
	public List<List<InsuredShow>> createShowInformationPeriod(SDInformation tSDInformation) {
		List<List<InsuredShow>> period = new ArrayList<List<InsuredShow>>();
		if(tSDInformation != null){ 
			period.add(getPeirodShow(tSDInformation)); 
		}
		return period;
	}
	private List<InsuredShow> getPeirodShow(SDInformation i) {   
		List<InsuredShow> is = new ArrayList<InsuredShow>();
		SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
		if(i.getPlanName()!=null && !"".equals(i.getPlanName())){
			is.add(createInsuredShow("投保计划:",i.getPlanName()));
		}
		if(i.getEnsureLimit()!=null && !"".equals(i.getEnsureLimit())){
			if("D".equals(i.getEnsureLimitType().toUpperCase())){
				is.add(createInsuredShow("保险期限:",i.getEnsureLimit()+"天"));
			}else if("Y".equals(i.getEnsureLimitType().toUpperCase())){
				is.add(createInsuredShow("保险期限:",i.getEnsureLimit()+"年"));
			}else if("A".equals(i.getEnsureLimitType().toUpperCase())){
				is.add(createInsuredShow("保险期限:",i.getEnsureLimit()+"岁"));
			}else if("M".equals(i.getEnsureLimitType().toUpperCase())){
				is.add(createInsuredShow("保险期限:",i.getEnsureLimit()+"个月"));
			}
			
		}
		if(i.getStartDate()!=null && !"".equals(i.getStartDate())){
			is.add(createInsuredShow("保单起保日期:",f2.format(i.getStartDate())));
		}
		if(i.getEndDate()!=null && !"".equals(i.getEndDate())){
			is.add(createInsuredShow("保单终止日期:",f2.format(i.getEndDate())));
		}
		
		return is;
	}
	private InsuredShow createInsuredShow(String name, String value) {
		InsuredShow is = new InsuredShow();
		is.setShowName(name);
		is.setShowValue(value);
		return is;
	}


	@Override
	public SDInformation getByOrderId(String orderId) {
		return sdinformationDao.getByOrderId(orderId);
	}
	
	@Override
	public SDInformation getByOrderSn(String orderSn) {
		return sdinformationDao.getByOrderSn(orderSn);
	}
}