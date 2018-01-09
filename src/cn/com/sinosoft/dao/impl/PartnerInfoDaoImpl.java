/**
 * Project Name:wj
 * File Name:PartnerInfoDaoImpl.java
 * Package Name:cn.com.sinosoft.dao.impl
 * Date:2016年8月16日上午9:25:11
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.dao.impl;


import java.util.List;
import java.util.Map;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.PartnerInfoDao;
import cn.com.sinosoft.entity.Partnerinfo;

/**
 * ClassName:PartnerInfoDaoImpl <br/>
 * Function:合作商户DAO层实现类. <br/>
 * Date:2016年8月16日 上午9:25:11 <br/>
 *
 * @author:xialianfu
 */

@Repository
public class PartnerInfoDaoImpl extends BaseDaoImpl<Partnerinfo, String>
		implements PartnerInfoDao {

	@Override
	public List<Partnerinfo> selectPartnerInfoByPaySn(String strPaySn) {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT p.*  ");
		sql.append(" FROM sdorders s, partnerinfo p ");
		sql.append(" WHERE s.channelsn = p.channelSn  ");
		sql.append(" AND s.paySn = '" + strPaySn + "'  ");
		List<Partnerinfo> detailList = getSession().createSQLQuery(sql.toString()).addEntity(Partnerinfo.class).list();
		return detailList;
	}

	@Override
	public List<Map<String, Object>> selectDetailByPaySn(String strPaySn) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT p.returnUrl,p.returnErrorUrl,p.bgReturnUrl,s.orderSn  ");
		sql.append(" FROM sdorders s, partnerinfo p ");
		sql.append(" WHERE s.channelsn = p.channelSn  ");
		sql.append(" AND s.paySn = '"+strPaySn+"'  ");
		
		List<Map<String, Object>> detailList = getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		 
		return detailList;
	}

}
