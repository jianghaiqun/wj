package com.sinosoft.forward;

import com.sinosoft.framework.utility.LogUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestAction extends ShopDispatchAction {
	public String test(ServletRequest request) throws Exception {
		request.getParameter("param1");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("test", "test");
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("test", "map1");
		
		List list = new ArrayList();
		list.add(map1);
		list.add(map1);
		list.add(map1);
		map.put("map", list);
		JSONObject json1 = JSONObject.fromObject(map);
		return json1.toString();
	}
	
	public String test1(ServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("test", "test");
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("test", "map1");
		
		List list = new ArrayList();
		list.add(map1);
		list.add(map1);
		list.add(map1);
		map.put("map", list);
		JSONObject json1 = JSONObject.fromObject(map);
		return json1.toString();
	}
	
}
