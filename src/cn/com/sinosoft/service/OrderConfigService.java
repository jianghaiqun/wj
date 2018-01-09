package cn.com.sinosoft.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.InformationAppnt;
import cn.com.sinosoft.entity.InformationInsured;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.OrderDutyFactor;

public interface OrderConfigService {
	public boolean validateOccup(String occupId, String productId);
	public boolean validateAge(String BirthdayId, String productId,Date effective);
	
	public Map<String,String>  CalPrice(String RiskCode,Map<String, String> map);
	public String totleAmount(List<OrderDutyFactor> dutyFactor);
	/**
	 * 根据页面返回值修改原订单表
	 */
	public Order updateOrder(Order oldOrder, Order order);
	/**
	 * 
	 * 根据页面返回值修改原投保人信息
	 */
	public InformationAppnt updateInformationAppnt(InformationAppnt ifa,
			InformationAppnt informationAppnt);
	/**
	 * 根据页面返回值修改原被保人信息
	 */
	public InformationInsured updateInformationInsured(
			InformationInsured infs1, InformationInsured informationInsureds);
	/**
	 * 根据投保要素年龄范围给出默认年龄
	 */
	public String getBrithdayByFactor(String age);
}
