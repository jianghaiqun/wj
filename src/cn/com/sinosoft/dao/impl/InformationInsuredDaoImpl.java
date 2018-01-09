package cn.com.sinosoft.dao.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.InformationAppntDao;
import cn.com.sinosoft.dao.InformationInsuredDao;
import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.entity.InformationAppnt;
import cn.com.sinosoft.entity.InformationInsured;

/**
 * Dao实现类 - 受益人信息
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT64B5A05594CB1C3B74C8A9B4F94F5991
 * ============================================================================
 */

@Repository
public class InformationInsuredDaoImpl extends BaseDaoImpl<InformationInsured, String> implements InformationInsuredDao{
	@Override
	public InformationInsured getByOrParentId(String informationId) {
		String hql = "from InformationInsured as informationInsured where informationInsured.information.id = ?";
		return (InformationInsured)getSession().createQuery(hql).setParameter(0, informationId).uniqueResult();
	}
}