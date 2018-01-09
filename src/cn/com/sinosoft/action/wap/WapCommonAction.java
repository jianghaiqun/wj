package cn.com.sinosoft.action.wap;

import java.util.Map;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.json.JSONObject;

import cn.com.sinosoft.action.shop.BaseWapShopAction;
import cn.com.sinosoft.util.WapCommonUtil;

@ParentPackage("wap")
public class WapCommonAction extends BaseWapShopAction {
  
	/**
	 * wap站会员信息接口类 请求头request中增加用户名/秘密、版本号信息
	 */
	private static final long serialVersionUID = 5109415857980705568L;

	/**
	 * wap站主页信息接口
	 * @return
	 */
	public String getMainPageInfo() {
		//JSONObject jsonObject = new JSONObject(WapTestUtil.wapGetMainPageInfo());
		Map<String, Object> resultMap = WapCommonUtil.wapGetMainPageInfo(PARAMETERS);
		resultMap.put("USER", USER);
		JSONObject jsonObject = new JSONObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站地区信息接口
	 * @return
	 */
	public String getAreaInfo() {
		JSONObject jsonObject = new JSONObject(WapCommonUtil.wapGetAreaInfo(USER,PARAMETERS));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站国籍信息接口
	 * @return
	 */
	public String getNationalityInfo() {
		JSONObject jsonObject = new JSONObject(WapCommonUtil.wapGetNationalityInfo(USER,PARAMETERS));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站职业信息接口
	 * @return
	 */
	public String getOccupationInfo() {
		JSONObject jsonObject = new JSONObject(WapCommonUtil.wapGetOccuInfo(USER,PARAMETERS));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站投保人关系接口
	 * @return
	 */
	public String getRelationShip() {
		JSONObject jsonObject = new JSONObject(WapCommonUtil.wapGetRelationShip(USER,PARAMETERS));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站证件类型接口
	 * @return
	 */
	public String getCardInfo() {
		JSONObject jsonObject = new JSONObject(WapCommonUtil.wapGetCardInfo(USER,PARAMETERS));
		return ajax(jsonObject.toString(), "text/html"); 
	}
	/**
	 * wap站性别接口
	 * @return
	 */
	public String getSexInfo() {
		JSONObject jsonObject = new JSONObject(WapCommonUtil.wapGetSexInfo(USER,PARAMETERS));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站房屋类型
	 * @return
	 */
	public String getHourseTypeInfo() {
		JSONObject jsonObject = new JSONObject(WapCommonUtil.wapHouseTypeInfo(USER,PARAMETERS));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站房屋所有者信息
	 * @return
	 */
	public String getPTORInfo() {
		JSONObject jsonObject = new JSONObject(WapCommonUtil.wapPTORInfo(USER,PARAMETERS));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站房龄信息
	 * @return
	 */
	public String getHourseAgeInfo() {
		JSONObject jsonObject = new JSONObject(WapCommonUtil.wapPHourseAgeInfo(USER,PARAMETERS));
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * wap站旅游目的地信息接口
	 * @return
	 */
	public String getCountryInfo() {
		JSONObject jsonObject = new JSONObject(WapCommonUtil.wapGetCountryInfo(USER,PARAMETERS));
		return ajax(jsonObject.toString(), "text/html");
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	}

	 

}
