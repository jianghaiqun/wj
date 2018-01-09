package cn.com.sinosoft.action.wap;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.json.JSONObject;

import cn.com.sinosoft.action.shop.BaseWapShopAction;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.OccupationService;
import cn.com.sinosoft.service.SDInformationAppntService;
import cn.com.sinosoft.service.SDInformationInsuredService;
import cn.com.sinosoft.service.SDInformationService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.util.WapOrderUtil;

/**
 * wap站订单信息接口类 请求头request中增加用户名/秘密、版本号信息
 */
@ParentPackage("wap")
public class WapOrderAction extends BaseWapShopAction {
	
	private static final long serialVersionUID = 5109415857980705568L;
	
	@Resource 
	private SDOrderService sdorderService; 
	@Resource
	private SDInformationService sdinformationService;
	@Resource
	private SDInformationInsuredService sdinformationInsuredService;
	@Resource
	private SDInformationAppntService sdinformationAppntService;
	@Resource
	private AreaService areaService;
	@Resource
	private OccupationService occupationService;
	@Resource
	private DictionaryService dictionaryService;
	
	/**
	 * wap订单信息查询接口
	 * @return
	 */
	public String getApplicationInfo(){
		JSONObject jsonObject = new JSONObject(WapOrderUtil.wapGetApplicationInfo(PARAMETERS,sdorderService,sdinformationService,
				sdinformationInsuredService,sdinformationAppntService,areaService,occupationService,dictionaryService));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	
	/**
	 * wap取消订单接口
	 * @return
	 */
	public String cancelApplication() {
		JSONObject jsonObject = new JSONObject(WapOrderUtil.wapCancelApplication(PARAMETERS,sdorderService));
		//System.out.println(jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}
	
}
