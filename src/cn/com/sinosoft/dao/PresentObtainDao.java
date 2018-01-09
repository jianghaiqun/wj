package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.PresentObtain;

/**
 * Dao接口 - 礼品兑换记录项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTD5A534BF01E9EC35D3DC732D4C8FF498
 * ============================================================================
 */

public interface PresentObtainDao extends BaseDao<PresentObtain, String> {

	public PresentObtain getByOrP(String presentId);

}