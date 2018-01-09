package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.SDBillTitle;


/**
 * Dao接口 - 发票台头
 *
 */
public interface SDBillTitleDao extends BaseDao<SDBillTitle, String> {
	public List<SDBillTitle> getSDBillTitle(String memberId);
	
	public int updateDefault(String Id);
}