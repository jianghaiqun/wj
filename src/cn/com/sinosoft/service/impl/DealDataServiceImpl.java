package cn.com.sinosoft.service.impl;

import java.util.LinkedHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.DealDataDao;
import cn.com.sinosoft.service.DealDataService;

@Service
public class DealDataServiceImpl extends BaseServiceImpl<Object, String> implements DealDataService<Object> {
	@Resource
	private DealDataDao tDealDataDao;

	@Resource
	public void setBaseDao(DealDataDao tDealDataDao) {
		super.setBaseDao(tDealDataDao);
	}

	public boolean submitModel(LinkedHashMap<Object, String> lmap) throws Exception {
		return tDealDataDao.submitModel(lmap);
	}
	
	public boolean saveAll(LinkedHashMap<Object, String> lmap) throws Exception {
		return tDealDataDao.saveAll(lmap);
	}
}
