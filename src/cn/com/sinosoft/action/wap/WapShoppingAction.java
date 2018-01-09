package cn.com.sinosoft.action.wap;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.json.JSONObject;

import cn.com.sinosoft.action.shop.BaseWapShopAction;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.DealDataService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.HealthyInfoService;
import cn.com.sinosoft.service.OrderConfigNewService;
import cn.com.sinosoft.service.SDInformationAppntService;
import cn.com.sinosoft.service.SDInformationInsuredService;
import cn.com.sinosoft.service.SDInformationService;
import cn.com.sinosoft.service.SDInsuredHealthService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.SDRelationAppntService;
import cn.com.sinosoft.service.SDRelationRecognizeeService;
import cn.com.sinosoft.service.TradeInformationService;
import cn.com.sinosoft.util.WapShoppingUtil;

@ParentPackage("wap")
public class WapShoppingAction extends BaseWapShopAction {

	/**
	 * wap站会员信息接口类 请求头request中增加用户名/秘密、版本号信息
	 */
	private static final long serialVersionUID = 5109415857980705568L;

	@Resource
	private TradeInformationService tradeInformationService;
	@Resource
	private SDOrderService sdorderService;
	@Resource
	private SDInformationService sdinformationService;
	@Resource
	private SDInformationAppntService sdinformationAppntService;
	@Resource
	private SDInformationInsuredService sdinformationInsuredService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private OrderConfigNewService orderConfigNewService;
	@Resource
	private DealDataService mDealDataService;
	@Resource
	private HealthyInfoService healthyInfoService;
	@Resource
	private SDInsuredHealthService insuredHealthService;
	@Resource
	private BindInfoForLoginService mBindInfoForLoginService;
	@Resource
	private AreaService areaService;
	@Resource
	private SDRelationAppntService sdRelationAppntService;
	@Resource
	private SDRelationRecognizeeService sdRelationRecognizeeService;
	

	/**
	 * 支付确认接口
	 * @return
	 */
	public String getPayConfInfo() { 
	    HttpServletRequest request = getRequest();
	    WapShoppingUtil tWapShoppingUtil = new WapShoppingUtil();
		Map<String, Object> resultMap = tWapShoppingUtil.getPayConfInfo(
				sdorderService, orderConfigNewService, PARAMETERS,request);
		resultMap.put("USER", USER); 
		JSONObject jsonObject = new JSONObject(resultMap);
		//System.out.println("***return 信息**是**|" + jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 支付同步接口
	 * @return
	 */
	public String getPayResultInfo() {
		Map<String, Object> mServiceMap = getServiceMap();
		Member member = null;
		if(USER!=null && !"".equals(USER)){
			try{
				member = memberService.getMemberByLoginNameNoBinding(USER);
			}catch(Exception e){
				member = null;
			}
		}
		HttpServletRequest request = getRequest();

		Map<String, Object> resultMap = WapShoppingUtil.getPayResultInfo(
				mServiceMap, PARAMETERS, member, request);
		
		// 频道活动特殊处理
		if ("true".equals(resultMap.get("STATYS"))) {
			WapShoppingUtil.dealBrandProcedure(resultMap, mServiceMap, PARAMETERS);
		}
		
		resultMap.put("USER", USER);
		JSONObject jsonObject = new JSONObject(resultMap);
		//System.out.println("***return 信息**是**|" + jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 获取投保模版接口
	 * 
	 * @return
	 */
	public String getApplicationTempMap() {
		Map<String, Object> resultMap = WapShoppingUtil
				.wapApplicationTempMap(PARAMETERS);
		resultMap.put("USER", USER);
		JSONObject jsonObject = new JSONObject(resultMap);
		//System.out.println("***return 信息**是**|" + jsonObject.toString());
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 订单生成接口信息
	 * @return
	 */
	public String saveOrderInfo() {
		Map<String, Object> mServiceMap = getServiceMap();
		JSONObject jsonObject = new JSONObject(
				WapShoppingUtil.wapSaveOrderInfo(PARAMETERS, mServiceMap, USER));
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 封装入参
	 * @return
	 */
	public Map<String, Object> getServiceMap() {
		Map<String, Object> mServiceMap = new HashMap<String, Object>();
		mServiceMap.put("SDOrderService", sdorderService);
		mServiceMap.put("SDInformationService", sdinformationService);
		mServiceMap.put("SDInformationAppntService", sdinformationAppntService);
		mServiceMap.put("SDInformationInsuredService",sdinformationInsuredService);
		mServiceMap.put("DictionaryService", dictionaryService);
		mServiceMap.put("OrderConfigNewService", orderConfigNewService);
		mServiceMap.put("DealDataService", mDealDataService);
		mServiceMap.put("TradeInformationService", tradeInformationService);
		mServiceMap.put("MemberService", memberService);
		mServiceMap.put("HealthyInfoService", healthyInfoService);
		mServiceMap.put("SDInsuredHealthService", insuredHealthService);
		mServiceMap.put("BindInfoForLoginService", mBindInfoForLoginService);
		mServiceMap.put("AreaService", areaService);
		mServiceMap.put("SDRelationAppntService", sdRelationAppntService);
		mServiceMap.put("SDRelationRecognizeeService", sdRelationRecognizeeService);
		return mServiceMap;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
