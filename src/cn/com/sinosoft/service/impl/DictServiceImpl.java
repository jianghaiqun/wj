package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.DictDao;
import cn.com.sinosoft.entity.Dict;
import cn.com.sinosoft.service.DictService;

/**
 * @author LiuXin
 *
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<Dict,String> implements DictService{
	@Resource
	private DictDao dictDao;

	@Resource
	public void setDictDao(DictDao dictDao) {
		super.setBaseDao(dictDao);
	}
	

}
