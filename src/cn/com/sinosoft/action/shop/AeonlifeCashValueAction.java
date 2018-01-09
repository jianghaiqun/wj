/**
 * Project Name:wj
 * File Name:AeonlifeCashValueAction.java
 * Package Name:cn.com.sinosoft.action.shop
 * Date:2017年8月4日下午2:52:49
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.util.HttpClientUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.StringUtil;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * ClassName:AeonlifeCashValueAction <br/>
 * Function:TODO 现金计算. <br/>
 * Date:2017年8月4日 下午2:52:49 <br/>
 *
 * @author:guozc
 */
public class AeonlifeCashValueAction extends BaseShopAction{

	private static final long serialVersionUID = 1L; 
	
	@SuppressWarnings("unchecked")
	public String getAeonlifeCashValue(){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, String[]> map = request.getParameterMap();
		Set<Entry<String, String[]>> set = map.entrySet();
		Iterator<Entry<String, String[]>> it = set.iterator();
		while (it.hasNext()) {
			Entry<String, String[]> entry = it.next();
			for (String i : entry.getValue()) {
				param.put(entry.getKey(), i);
			}
		}
		param.put("ChannelSn", "wj");
		String url = Config.interfaceMap.getString("get_aeonlife_cashValue_interface");
		try {
			String content = HttpClientUtil.doPost(url, param);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> cashValueInfo = mapper.readValue(content, HashMap.class);
			if (cashValueInfo == null || cashValueInfo.size() == 0) {
				jsonMap.put("status", "2");
				jsonMap.put("msg", "查询失败");
				return ajaxJson(JSONObject.fromObject(jsonMap).toString());
			}
			Map<String, Object> resultDTO = (Map<String, Object>) cashValueInfo.get("resultDTO");
			if (resultDTO == null || resultDTO.size() == 0 || !"0".equals(resultDTO.get("resultCode"))) {
				jsonMap.put("status", "2");
				if(resultDTO != null &&  StringUtil.isNotEmpty((String)resultDTO.get("resultInfoDesc"))){
					jsonMap.put("msg", (String)resultDTO.get("resultInfoDesc"));
				}else{
					jsonMap.put("msg", "查询失败");
				}
				return ajaxJson(JSONObject.fromObject(jsonMap).toString());
			}
			List<Map<String, Object>> cashValues = (List<Map<String, Object>>) cashValueInfo.get("cashValues");
			jsonMap.put("status", "1");
			jsonMap.put("data", cashValues);
			jsonMap.put("prem", cashValueInfo.get("prem"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return ajaxJson(JSONObject.fromObject(jsonMap).toString());
	}
	
	
}

