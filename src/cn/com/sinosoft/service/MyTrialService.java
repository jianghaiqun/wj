package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.FDInsCom;
import cn.com.sinosoft.entity.SDtargetInformation;
import cn.com.sinosoft.entity.ShowInsurance;

/**
 * @author LiuXin
 *
 */
public interface MyTrialService {
	public List<SDtargetInformation> getsSDInformations(String memberId,SDtargetInformation sdInformation);
	public List<City> getRegionCode();
	public List<FDInsCom> getCompanys();
	public List<ShowInsurance> getShowInsurances(String serialNumber);

}
