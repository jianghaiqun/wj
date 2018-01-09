package cn.com.sinosoft.dao.impl;


import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.AgreementDao;
import cn.com.sinosoft.entity.Agreement;

/**
 * Dao实现类 - 会员注册协议
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTD5FBB56980C60A9D4D676BC334C9E5F6
 * ============================================================================
 */

@Repository
public class AgreementDaoImpl extends BaseDaoImpl<Agreement, String> implements AgreementDao {

	public Agreement getAgreement() {
		return load(Agreement.AGREEMENT_ID);
	}

}