package cn.com.sinosoft.action.shop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.sinosoft.weixin.WeiXinCommon;
/**
 * 
* @ClassName: WeixinTokenAction 
* @Description: TODO(用于小能调用，微信返回Token值！！！！！) 
* @author guanyulong 
* @date 
*
 */
@ParentPackage("shop")
public class WeixinTokenAction extends BaseShopAction {

	public String backToken() {

		String expires_in,access_token;
		Map<String, Object> result = new HashMap<String, Object>();
		  try {
			    expires_in="3600";//1h更新一次token
				WeiXinCommon weixinCommon = new WeiXinCommon();
				access_token = weixinCommon.ajaxtoken();
			  } catch (NumberFormatException e) {
				    Date date = new Date();
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String thisTime = sf.format(date).toString();
					result.put("massage", thisTime+"   "+"服务器异常，清稍后再试！");
					return ajaxMap2Json(result);
			  } 
		result.put("access_token", access_token); 
		result.put("expires_in", expires_in);
		return ajaxMap2Json(result);
		}
		
	

}
