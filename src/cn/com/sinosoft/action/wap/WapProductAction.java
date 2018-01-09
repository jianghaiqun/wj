package cn.com.sinosoft.action.wap;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.json.JSONObject;
import cn.com.sinosoft.action.shop.BaseWapShopAction;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.util.WapProductUtil;

@ParentPackage("wap")
public class WapProductAction extends BaseWapShopAction {

	/**
	 * wap站会员信息接口类 请求头request中增加用户名/秘密、版本号信息
	 */
	private static final long serialVersionUID = 5109415857980705568L;
	@Resource
	protected SDOrderService mSDOrderService;

	/**
	 * wap站筛选产品列表接口
	 * 
	 * @return
	 */
	public String getProductList() {

		Map<String, Object> resultMap = WapProductUtil
				.wapGetProductList(PARAMETERS);
		resultMap.put("USER", USER);
		JSONObject jsonObject = new JSONObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 代理人wap站产品列表接口
	 * 
	 * @return
	 */
	public String getDLRProductList() {

		Map<String, Object> resultMap = WapProductUtil
				.wapGetDlrRecProductList(PARAMETERS);
		resultMap.put("USER", USER);
		JSONObject jsonObject = new JSONObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 合作平台上线产品列表接口
	 * 
	 * @return
	 */
	public String getOnLineProductList() {

		Map<String, Object> resultMap = WapProductUtil
				.wapGetOnLineProductList(PARAMETERS);
		resultMap.put("USER", USER);
		JSONObject jsonObject = new JSONObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 产品信息实时接口
	 * 
	 * @return
	 */
	public String getProductRealTimeInfo() {

		Map<String, Object> resultMap = WapProductUtil
				.wapGetProductRealTimeInfo(PARAMETERS, getServiceMap());
		resultMap.put("USER", USER);
		JSONObject jsonObject = new JSONObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 支付可用优惠劵接口
	 * 
	 * @return
	 */
	public String wapGetPayCouponInfo() {

		Map<String, Object> resultMap = WapProductUtil.getPayCouponInfo(
				PARAMETERS, getServiceMap());
		resultMap.put("USER", USER);
		JSONObject jsonObject = new JSONObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 产品筛选条件
	 * 
	 * @return
	 */
	public String getProductFilters() {
		// JSONObject jsonObject = new
		// JSONObject(WapTestUtil.wapGetProductFilters());

		Map<String, Object> resultMap = WapProductUtil
				.wapGetProductFilters(PARAMETERS);
		resultMap.put("USER", USER);
		JSONObject jsonObject = new JSONObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 产品评论
	 * 
	 * @return
	 */
	public String getProductComment() {

		Map<String, Object> resultMap = WapProductUtil.productCommentInfo(
				PARAMETERS, USER);
		JSONObject jsonObject = new JSONObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 产品特殊标识信息接口 add by wangej 20150625
	 * 
	 * @return
	 */
	public String getProductSpecialInfo() {

		Map<String, Object> resultMap = WapProductUtil.getProductSpecialInfo(
				PARAMETERS, USER);
		JSONObject jsonObject = new JSONObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 封装入参
	 * 
	 * @return
	 */
	public Map<String, Object> getServiceMap() {
		Map<String, Object> mServiceMap = new HashMap<String, Object>();
		mServiceMap.put("MemberService", memberService);
		mServiceMap.put("SDOrderService", mSDOrderService);
		return mServiceMap;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
