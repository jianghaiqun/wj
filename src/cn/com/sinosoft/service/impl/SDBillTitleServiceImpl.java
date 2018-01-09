package cn.com.sinosoft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.SDBillTitleDao;
import cn.com.sinosoft.entity.SDBillTitle;
import cn.com.sinosoft.service.SDBillTitleService;

/**
 * Service实现类 - 发票台头
 *  
 */

@Service
public class SDBillTitleServiceImpl extends BaseServiceImpl<SDBillTitle, String> implements SDBillTitleService {
	@Resource
	private SDBillTitleDao mSDBillTitleDao;
	
	@Resource
	public void setBaseDao(SDBillTitleDao mSDBillTitleDao) { 
		super.setBaseDao(mSDBillTitleDao);
	}

	@Override
	public List<SDBillTitle> getSDBillTitle(String memberId) {
		return mSDBillTitleDao.getSDBillTitle(memberId);
	}

	@Override
	public int updateDefault(String Id) {
		return mSDBillTitleDao.updateDefault(Id);
	}
}