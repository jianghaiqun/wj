package cn.com.sinosoft.action.shop;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 前台Action类 - 前台基类
 * ============================================================================
 * 
 * 
 * 
 * 
 * 
 * 
 * KEY:SINOSOFT08EDCB64FEA84DDFDCD64F97F83B43F1
 * ============================================================================
 */

@SuppressWarnings("unused")
public class BaseWapShopAction extends BaseShopAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1046290281633839577L;

    private static Properties props = new Properties();

    static {
	try {
	    InputStream is = Thread.currentThread().getContextClassLoader()
		    .getResourceAsStream("platformbutt.properties");
	    InputStreamReader rd = new InputStreamReader(is, "UTF-8");
	    props.load(rd);
	    // props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("waperrorinfos.properties"));
	} catch (IOException e) {
		logger.error(e.getMessage(), e);
	}
    }

    public static String GNAME = String.valueOf(props.get("WapUserName"));
    public static String GPASSWORD = String.valueOf(props.get("WapPassWord"));

    protected String jsonRequest;

    public String getJsonRequest() {
	return jsonRequest;
    }

    @SuppressWarnings("unchecked")
    public void setJsonRequest(String jsonRequest) {
	this.jsonRequest = jsonRequest;
	JSONObject jsonObject;
	try {
	    // 本地不要注释掉这一行代码
	    // jsonRequest = new
	    // String(jsonRequest.getBytes("ISO-8859-1"),"UTF-8");
	    // jsonRequest = java.net.URLDecoder.decode(jsonRequest,"UTF-8");
	    jsonObject = new JSONObject(jsonRequest);
	    try {
		this.USER = (String) jsonObject.get("USER");
	    } catch (Exception e) {
			this.USER = "";
			logger.error("wap站解析USER参数错误,以默认置空--" + e.getMessage(), e);
	    }

	    try {
		this.REQUESTTYPE = (String) jsonObject.get("REQUESTTYPE");
	    } catch (Exception e) {
			logger.error("wap站解析USER参数错误--" + e.getMessage(), e);
	    }

	    try {
		JSONObject tJSONObject = (JSONObject) jsonObject
			.get("PARAMETERS");
		PARAMETERS = new HashMap<String, Object>();
		Iterator<String> iterator = tJSONObject.keys();
		while (iterator.hasNext()) {
		    String key = iterator.next();
		    Object value = tJSONObject.get(key);
		    PARAMETERS.put(key.toString(), value);
		}
	    } catch (Exception e) {
			logger.error("wap站解析USER参数错误--" + e.getMessage(), e);
	    }
	} catch (Exception e) {
		logger.error(e.getMessage(), e);
	}
    }

    public static Map<String, Object> requestTypeMap = new HashMap<String, Object>() {
	/**
	 * 请求类型容器
	 */
	private static final long serialVersionUID = -3480507229861371916L;

	{

	    put("KXBJRT0001", "Login");
	    put("KXBJRT0002", "Register");
	    put("KXBJRT0003", "GetMainPageNavInfo");
	    put("KXBJRT0004", "GetMainPageInfo");
	    put("KXBJRT0005", "FetchPassword");
	    put("KXBJRT0006", "UnitLogin");
	    put("KXBJRT0007", "GetProductList");
	    put("KXBJRT0008", "GetProductFilters");
	    put("KXBJRT0009", "GetProductDetail");
	    put("KXBJRT0010", "PremiumCal");
	    put("KXBJRT0011", "GetApplicationTemp");
	    put("KXBJRT0012", "GetAreaInfo");
	    put("KXBJRT0013", "GetOccupationInfo");
	    put("KXBJRT0014", "ApplicationView");
	    put("KXBJRT0015", "ApplicationSubmit");
	    put("KXBJRT0016", "GetDiscCouponInfo");
	    put("KXBJRT0017", "GetPayResultInfo");
	    put("KXBJRT0018", "GetUserInfo");
	    put("KXBJRT0019", "UpdateUserBaseInfo");
	    put("KXBJRT0020", "ChangePassword");
	    put("KXBJRT0021", "GetDiscCouponList");
	    put("KXBJRT0022", "GetApplicationInfo");
	    put("KXBJRT0023", "CancelApplication");
	    put("KXBJRT0024", "PaymentCheck");
	    put("KXBJRT0025", "DoRetrieveByEmail");
	    put("KXBJRT0026", "ReSendRetrieveMail");
	    put("KXBJRT0027", "MobileRetrievePassword");
	    put("KXBJRT0028", "ReSendSVCToPhone");
	    put("KXBJRT0030", "GetRelationShip");
	    put("KXBJRT0031", "GetCardInfo");
	    put("KXBJRT0032", "GetSexInfo");
	    put("KXBJRT0033", "GetCountryInfo");
	    put("KXBJRT0034", "GetNationalityInfo");
	    put("KXBJRT0035", "OnLineProductList");
	    put("KXBJRT0036", "ProductRealTimeInfo");
	    put("KXBJRT0037", "GetUserInfoByID");
	    put("KXBJRT0038", "WeiXinBind");
	    put("KXBJRT0039", "GetUserInfoByOpenID");
	    put("KXBJRT0040", "checkLogin");
	    put("KXBJRT0041", "wapPouponVerify");// 优惠劵激活接口
	    put("KXBJRT0042", "wapMemberPonponInfo");// 会员优惠劵信息接口
	    put("KXBJRT0043", "wapMemberPointInfo");// 会员积分信息接口
	    put("KXBJRT0044", "wapGetPayCouponInfo");// 支付优惠劵接口
	    put("KXBJRT0045", "getProductComment");// 产品评论接口
	    put("KXBJRT0046", "unitBind");// （QQ、新浪微博、支付宝）判断是否已绑定接口
	    put("KXBJRT0047", "unitBindingInfo");// （QQ、新浪微博、支付宝）信息绑定接口
	    put("KXBJRT0048", "delaRelaInfo");// 常用投、被保人信息接口
	    put("KXBJRT0049", "queryRelaInfo");// 常用投、被保人信息查询接口

	    // 家财信息接口
	    put("KXBJRT0050", "getHourseTypeInfo");// 家财险房屋类型信息查询接口
	    put("KXBJRT0051", "getPTORInfo");// 家财险房屋所有者信息查询接口
	    put("KXBJRT0052", "getHourseAgeInfo");// 家财险房龄信息查询接口

	    put("KXBJRT0053", "getDLRProductList");// 家财险房龄信息查询接口

	    put("KXBJRT0054", "getRolePionts");// 积分规则
	    put("KXBJRT0055", "getMemRecomInfo");// 会员推荐积分信息
	    put("KXBJRT0056", "getMemberGradeInfo");// 会员等级信息
	    put("KXBJRT0057", "getProductSpecialInfo");//产品特殊标识信息接口
	}
    };
    protected String USER;// 当前登录用户
    protected String REQUESTTYPE;// 请求类型
    protected Map<String, Object> PARAMETERS;// 请求参数

    public String getUSER() {
	return USER;
    }

    public void setUSER(String uSER) {
	USER = uSER;
    }

    public String getREQUESTTYPE() {
	return REQUESTTYPE;
    }

    public void setREQUESTTYPE(String rEQUESTTYPE) {
	REQUESTTYPE = rEQUESTTYPE;
    }

    public Map<String, Object> getPARAMETERS() {
	return PARAMETERS;
    }

    public void setPARAMETERS(Map<String, Object> pARAMETERS) {
	PARAMETERS = pARAMETERS;
    }

    /**
     * JSON---Map
     * 
     * @param cJson
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getJsonToMap(JSONObject cJson) {

	JSONObject tJson = cJson;
	Map<String, Object> mMap = new HashMap<String, Object>();
	try {
	    Iterator<String> iterator = tJson.keys();
	    while (iterator.hasNext()) {
		String key = iterator.next();
		String value = tJson.getString(key);
		mMap.put(key.toString(), value.toString());
	    }
	} catch (Exception e) {
		logger.error(e.getMessage(), e);
	}

	return mMap;
    }

    /**
     * JSON---Map
     * 
     * @param cJson
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> getJsonToListMap(
	    String tPARAMETERS, Map<String, Object> mPARAMETERS) {
	List<Map<String, Object>> mlistmap = new ArrayList<Map<String, Object>>();
	JSONArray mJSONArray = (JSONArray) mPARAMETERS.get(tPARAMETERS);
	if (mJSONArray != null && mJSONArray.length() > 0) {
	    for (int i = 0; i < mJSONArray.length(); i++) {
		try {
		    JSONObject mJSONObject = new JSONObject(mJSONArray.get(i)
			    .toString());
		    Map<String, Object> mMap = new HashMap<String, Object>();
		    Iterator<String> iterator = mJSONObject.keys();
		    while (iterator.hasNext()) {
			String key = iterator.next();
			String value = mJSONObject.getString(key);
			mMap.put(key.toString(), value.toString());
		    }
		    mlistmap.add(mMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	    }

	}

	return mlistmap;
    }
}