package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.*;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface OrderConfigNewService {
	public boolean validateOccup(String occupId, String productId);
	public boolean validateAge(String BirthdayId, String productId);
	
	public Map<String,String>  CalPrice(String RiskCode,Map<String, String> map);
	public String totleAmount(List<OrderDutyFactor> dutyFactor);
	/**
	 * 根据页面返回值修改原订单表
	 */
	public SDOrder updateOrder(SDOrder oldSDOrder, SDOrder sdorder);
	/**
	 * 
	 * 根据页面返回值修改原投保人信息
	 */
	public SDInformationAppnt updateInformationAppnt(SDInformation sdinf,SDInformationAppnt ifa,
			SDInformationAppnt sdinformationAppnt);
	/**
	 * 根据页面返回值修改原被保人信息
	 */
	public SDInformationInsured updateInformationInsured(SDInformation sdinf,
			SDInformationInsured infs1, SDInformationInsured informationInsureds);
	/**
	 * 根据页面返回值修改原订单详细表信息
	 */
	public SDInformation updateInformation(
			SDInformation infs, SDInformation information);
	/**
	 * 根据投保要素年龄范围给出默认年龄
	 */
	public String getBrithdayByFactor(String startdate,String age);
	/**
	 * 处理太平洋旅游目的地
	 */
	public String getCountryText2007(String cComCode,String cCountryCode);
	public String getCountryText1015(String string, String destinationCountry);
	public String getSchengenCountryText(String cComCode, String cCountryCode,String cProductID);
	public String getSectionAge(String cProductId);
	public String getRealSectionAge(String BirthdayId,String sectionage,String effective);
	public Map<String,Object> getInsuredList(String comCode,String productID,String cPath,Map<String,Object> cMap, String memberId);
	public Map<String,Object> calPrem(Map<String,Object> cMap, String memberId);
	public Map<String,Object> copySDInsured(SDInformationInsured oldInsured,SDInformationRiskType oldRiskType,int index);
	public boolean buyCountFlag(SDInformation sdinf);
	public List<SDInsuredHealth> getInsuredHealthy(HealthyInfoService healthyInfoService, SDInsuredHealthService sdinsuredHealthService, SDInformation sdinf);
	public String getInsuredSn(String orderSn,int index,String comCode);
	public Object getCellString(Cell cell);
	public boolean checkCellValue(String cell,String temp);
	public boolean isIdentityCard(String idno);
	public boolean validateAgeNew( String BirthdayId , String sectionage);
	public boolean validateAgeNewBaseOnEffectiveDate( String BirthdayId , String sectionage,Date effectiveDate);
	public String getExcelKeyValue(String productId);
    public boolean isMobile(String mobile);
    public String checkEnName(String cell,String temp);
    public String getChannelSn(HttpServletRequest request,String cChannelSn);


}
