/**
 * 
 */
package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.dao.SDInformationDutyTempDao;
import cn.com.sinosoft.entity.SDInformationDutyTemp;
import cn.com.sinosoft.service.SDInformationDutyTempService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangcaiyun
 *
 */
@Service
public class SDInformationDutyTempServiceImpl extends BaseServiceImpl<SDInformationDutyTemp, String> implements SDInformationDutyTempService {

	@Resource
	private SDInformationDutyTempDao dao;
	
	@Resource
	public void setBaseDao(SDInformationDutyTempDao dao) {
		super.setBaseDao(dao);
	}


	public List<SDInformationDutyTemp> getDutyTemp(String Serials) {
		return dao.getDutyTemp(Serials);
	}
}
