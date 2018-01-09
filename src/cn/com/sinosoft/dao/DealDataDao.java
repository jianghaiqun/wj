package cn.com.sinosoft.dao;

import java.util.LinkedHashMap;

/**
 * Dao接口 - 管理员
 * ============================================================================
 * 
 * 
 * 
 * 
 * 
 * 
 * KEY:SINOSOFT6EC45B0FCAF1A33579C2CA7460906C3F
 * ============================================================================
 */

public interface DealDataDao<T> extends BaseDao<T, String> {

	public boolean submitModel(LinkedHashMap<Object, String> lmap) throws Exception;
	
	public boolean saveAll(LinkedHashMap<Object, String> lmap) throws Exception ;

}