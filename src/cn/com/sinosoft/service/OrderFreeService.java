package cn.com.sinosoft.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrderItem;
import cn.com.sinosoft.entity.SDOrderItemOth;
import cn.com.sinosoft.entity.TradeInformation;


public interface OrderFreeService {
	
	//身份证校验
	public boolean checkCardID(String cProductID,String cardID);
	public SDOrder getSDOrder(SDOrder sdorder,String[] BaseInformation,String cFrom);
	public SDInformationAppnt getSDInformationAppnt(SDInformationAppnt sdInformationAppnt);
	public SDInformation getSDInformation(SDInformation sdinformation,String[] BaseInformation);
	public SDOrderItem getSDOrderItem(SDOrderItem tSDOrderItem,SDInformation sdinformation,SDOrder sdorder); 
	public List<SDInformationInsured> getSDInformationInsured(SDInformationInsured sdInformationInsured,
			SDOrder order,SDInformationAppnt sdInformationAppnt);
	public SDOrderItemOth getSDOrderItemOth(SDOrderItemOth tSDOrderItem,SDInformationInsured sdInformationInsured,
			SDOrder sdorder);
	public TradeInformation dealTradeInfo(SDOrder order,TradeInformation tradeInformation);
	public boolean dealTrade(SDOrder order);
	public void setProp(Properties cprops);
}
