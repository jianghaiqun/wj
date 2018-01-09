package cn.com.sinosoft.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.SDInformationBnfDao;
import cn.com.sinosoft.entity.SDInformationBnf;
import cn.com.sinosoft.service.SDInformationBnfService;

@Service
public class SDInformationBnfServiceImpl extends BaseServiceImpl<SDInformationBnf, String> implements SDInformationBnfService {
	@Resource
	private SDInformationBnfDao dao;
	
	@Resource
	public void setBaseDao(SDInformationBnfDao dao) {
		super.setBaseDao(dao);
	}
}