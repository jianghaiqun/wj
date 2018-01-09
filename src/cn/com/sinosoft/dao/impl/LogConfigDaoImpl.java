package cn.com.sinosoft.dao.impl;

import java.util.List;


import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.LogConfigDao;
import cn.com.sinosoft.entity.LogConfig;

/**
 * Dao实现类 - 日志设置
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT64BEB194ABE78EC64BF035CE35DD1585
 * ============================================================================
 */

@Repository
public class LogConfigDaoImpl extends BaseDaoImpl<LogConfig, String> implements LogConfigDao {

	@SuppressWarnings("unchecked")
	public List<LogConfig> getLogConfigList(String actionClassName) {
		String hql = "from LogConfig as logConfig where logConfig.actionClassName = ?";
		return getSession().createQuery(hql).setParameter(0, actionClassName).list();
	}

}
