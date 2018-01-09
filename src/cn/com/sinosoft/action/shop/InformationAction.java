package cn.com.sinosoft.action.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.sinosoft.entity.Area;
import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.InformationService;

/**
 * 后台Action类 - 地区
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTAFBAC38E9CCED8343FEA91C4E57AB4B7
 * ============================================================================
 */

@ParentPackage("member")
@InterceptorRefs({
	@InterceptorRef(value = "token", params = {"excludeMethods", "info,list,view"}),
	@InterceptorRef(value = "memberStack")
})
public class InformationAction extends BaseShopAction {
	private static final long serialVersionUID = -526558193244517863L;
	private String orderId;
	private Information information ;
	@Resource
	private InformationService informationService;
	
	// 根据地区Path值获取下级地区JSON数据
	// 每个产品的投保详情
	public String info() {
//		System.out.println("orderId="+orderId);
		information = informationService.getByProduct(id,orderId);
		return "info";
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}