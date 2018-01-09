package cn.com.sinosoft.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.AdminDao;
import cn.com.sinosoft.dao.AppInsuredDao;
import cn.com.sinosoft.entity.AppInsured;

import cn.com.sinosoft.service.AppInsuredService;
@Service
public class AppInsuredServiceImpl extends BaseServiceImpl<AppInsured, String> implements AppInsuredService{

	@Resource
	private AppInsuredDao appInsuredDao;

	@Resource
	public void setBaseDao(AppInsuredDao appInsuredDao) {
		super.setBaseDao(appInsuredDao);
	}
}
