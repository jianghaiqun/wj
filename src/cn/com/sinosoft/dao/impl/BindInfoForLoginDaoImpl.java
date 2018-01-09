package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.BindInfoForLoginDao;
import cn.com.sinosoft.entity.BindInfoForLogin;

/**
 * Dao实现类 - 绑定信息
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT8061830D6037E81F0D148994EFFC9C78
 * ============================================================================
 */

@Repository
public class BindInfoForLoginDaoImpl extends BaseDaoImpl<BindInfoForLogin, String> implements BindInfoForLoginDao {
	
	@SuppressWarnings("unchecked")
	public BindInfoForLogin getBindInfoForLoginByOpenID(String openid){
		String hql = "from BindInfoForLogin as fifl where fifl.openID=?";
		BindInfoForLogin bindInfoForLogin = new BindInfoForLogin();
		bindInfoForLogin = (BindInfoForLogin) getSession().createQuery(hql).setParameter(0, openid).uniqueResult();
		if(bindInfoForLogin == null){
			return null;
		}
		return bindInfoForLogin;
	}
}