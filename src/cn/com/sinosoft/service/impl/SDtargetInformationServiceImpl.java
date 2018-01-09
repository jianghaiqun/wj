package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.SDtargetInformationDao;
import cn.com.sinosoft.entity.SDtargetInformation;
import cn.com.sinosoft.service.SDtargetInformationService;

/**
 * @author LiuXin
 *
 */
@Service
public class SDtargetInformationServiceImpl extends BaseServiceImpl<SDtargetInformation,String> implements SDtargetInformationService{
	@Resource
	private SDtargetInformationDao sdTargetInformationDao;

	@Resource
	public void setSdTargetInformationDao(
			SDtargetInformationDao sdTargetInformationDao) {
		this.setBaseDao(sdTargetInformationDao);
	}

}
