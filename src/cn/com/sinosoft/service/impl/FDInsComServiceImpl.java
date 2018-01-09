package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.FDInsComDao;
import cn.com.sinosoft.entity.FDInsCom;
import cn.com.sinosoft.service.FDInsComService;

/**
 * @author LiuXin
 *
 */
@Service
public class FDInsComServiceImpl extends BaseServiceImpl<FDInsCom,String> implements FDInsComService{
	@SuppressWarnings("unused")
	@Resource
	private FDInsComDao fdInsComDao;

	@Resource
	public void setFdInsComDao(FDInsComDao fdInsComDao) {
		super.setBaseDao(fdInsComDao);
	}
}
