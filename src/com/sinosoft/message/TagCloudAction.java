package com.sinosoft.message;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.sinosoft.forward.ShopDispatchAction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCTagSchema;
import com.sinosoft.schema.ZCTagSet;


public class TagCloudAction extends ShopDispatchAction {


	private static final long serialVersionUID = -7031695721764039045L;

	public String save(HttpServletRequest request) throws Exception {
		String count=request.getParameter("count");
		String like=URLDecoder.decode(request.getParameter("like"), "UTF-8");
		String condition="";
		if(StringUtil.isNotEmpty(like)){
			condition=" and tag like '%"+like+"%'";
			
		}
		ZCTagSchema tZCTagSchema=new ZCTagSchema();
		ZCTagSet tZCTagSet=tZCTagSchema.query(new QueryBuilder("where 1=1"+condition));
		int n=0;
		if(tZCTagSet.size()>Integer.parseInt(count)){
			n=Integer.parseInt(count);
		}else{
			n=tZCTagSet.size();
		}
		ArrayList<Category> categoriesList = new ArrayList<Category>();
		for (int i = 0; i <n-1 ; i++) {
			tZCTagSchema=tZCTagSet.get(i);
			Category item = new Category();
			item.setCategoryID(i);
			item.setCategoryName(tZCTagSchema.getTag());
			item.setUrl(tZCTagSchema.getLinkURL());
			item.setRelatedPosts(tZCTagSchema.getUsedCount());
			categoriesList.add(item);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("test", categoriesList);
		//System.err.println( JSONSerializer.toJSON(categoriesList).toString(2) );
		
		//return JSONSerializer.toJSON(categoriesList).toString(2);
		JSONObject json1 = JSONObject.fromObject(map);
		return json1.toString();
	}
	
}
