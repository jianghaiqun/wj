package cn.com.sinosoft.service;

import java.util.Date;
import java.util.List;

import cn.com.sinosoft.entity.CarMenu;
import cn.com.sinosoft.entity.CarRateInformation;
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.Dict;
import cn.com.sinosoft.entity.FDInsCom;
import cn.com.sinosoft.entity.Insurance;
import cn.com.sinosoft.entity.SDtargetInformation;

/**
 * @author LiuXin
 *
 */
public interface PremiumTrialService {
	public Date addYear(Date start, int i);
	public long getYearNO(Date dayE, Date dayS);
	public List<Dict> getDictsByCode(String code);
	public List<City> getRegionCode();
	public List<FDInsCom> getCompanys();
	public List<CarRateInformation> getCarRateInformations(String regionCode);
	public List<CarMenu> getCarMenus();
	public Insurance getAllPremium(SDtargetInformation information,CarRateInformation carRI ,CarMenu cm);
	public Insurance getPremiumDatail(String comCode,String cmType,String serialNumber);
	public void deleteOldPremium(String sql);
}
