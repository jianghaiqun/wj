package cn.com.sinosoft.dao.impl;



import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.InformationAppntDao;
import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.entity.InformationAppnt;

/**
 * Dao实现类 - 运输信息
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
public class InformationAppntDaoImpl extends BaseDaoImpl<InformationAppnt, String> implements InformationAppntDao {
	@Override
	public InformationAppnt getByOrParentId(String informationId) {
		String hql = "from InformationAppnt as informationAppnt where informationAppnt.information.id = ?";
		return (InformationAppnt)getSession().createQuery(hql).setParameter(0, informationId).uniqueResult();
	}
}