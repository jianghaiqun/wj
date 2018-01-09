package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.SDBillTitle;

/**
 * Service接口 - 发票台头
 *
 */
public interface SDBillTitleService extends
		BaseService<SDBillTitle, String> {
	public List<SDBillTitle> getSDBillTitle(String memberId);
	
	public int updateDefault(String Id);
}
