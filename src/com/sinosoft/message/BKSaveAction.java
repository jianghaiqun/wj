package com.sinosoft.message;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.sinosoft.forward.ShopDispatchAction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SCBkEditSchema;
import com.sinosoft.schema.SCBkEntrySchema;
import com.sinosoft.schema.ZDCodeSchema;
import com.sinosoft.schema.ZDCodeSet;

/**
 * @Author 周翔
 * @Date 2012-5-24
 * @Mail zhouxiang@sinosoft.com
 */

public class BKSaveAction extends ShopDispatchAction {

	public String save(HttpServletRequest request) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String tTitle=URLDecoder.decode(request.getParameter("Title"), "UTF-8");
		String tContent=URLDecoder.decode(request.getParameter("Content"), "UTF-8");
		String tType=URLDecoder.decode(request.getParameter("Type"), "UTF-8");
		if(StringUtil.isEmpty(tTitle)){
			map.put("test", "问题不能为空！");
			JSONObject json1 = JSONObject.fromObject(map);
			return json1.toString();
		}
		//执行存储
		Transaction trans = new Transaction();
		SCBkEntrySchema tSCBkEntrySchema=new SCBkEntrySchema();
		tSCBkEntrySchema.setId(NoUtil.getMaxID("EnTitleID")+"");
		if(StringUtil.isEmpty((String) request.getSession().getAttribute("loginMemberId"))){
			map.put("test", "请先登录用户！");
			JSONObject json1 = JSONObject.fromObject(map);
			return json1.toString();
		}else{
			tSCBkEntrySchema.setUserId((String) request.getSession().getAttribute("loginMemberId"));
		}
		tSCBkEntrySchema.setAddUserIP(request.getRemoteAddr());
		tSCBkEntrySchema.setEntryName(tTitle);
		tSCBkEntrySchema.setEntryType(tType);
		tSCBkEntrySchema.setCreateDate(new Date());
		trans.add(tSCBkEntrySchema, Transaction.INSERT);
		
		SCBkEditSchema tSCBkEditSchema=new SCBkEditSchema();
		tSCBkEditSchema.setId(NoUtil.getMaxID("EnContentID")+"");
		tSCBkEditSchema.setTitleId(tSCBkEntrySchema.getId());
		if(StringUtil.isEmpty((String) request.getSession().getAttribute("loginMemberId"))){
			map.put("test", "请先登录用户！");
			JSONObject json1 = JSONObject.fromObject(map);
			return json1.toString();
		}else{
			tSCBkEditSchema.setUserId((String) request.getSession().getAttribute("loginMemberId"));
		}
		tSCBkEditSchema.setAddUserIP(request.getRemoteAddr());
		tSCBkEditSchema.setEntryContent(tContent);
		tSCBkEditSchema.setCreateDate(new Date());
		tSCBkEditSchema.setaState("00");
		trans.add(tSCBkEditSchema, Transaction.INSERT);

		
		if(trans.commit()){
			map.put("test", "提交成功！");
			//return "提交成功！";
		}else{
			map.put("test", "提交失败！");
			//return "提交失败！";
		}
		JSONObject json1 = JSONObject.fromObject(map);
		return json1.toString();
	}
	public String getList(ServletRequest request) throws Exception {
		ZDCodeSchema tZDCodeSchema=new ZDCodeSchema();
		ZDCodeSet tZDCodeSet = tZDCodeSchema.query(new QueryBuilder("where parentcode='BaiKeType' "));
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map> list_select = new ArrayList<Map>();
		for(int i=0;i<tZDCodeSet.size();i++){
			Map<String,Object> select = new HashMap<String,Object>();
			select.put("key", tZDCodeSet.get(i).getCodeValue());
			select.put("value", tZDCodeSet.get(i).getCodeName());
			list_select.add(select);
		}
		map.put("map", list_select);
		JSONObject json1 = JSONObject.fromObject(map);
		return json1.toString();
	}

}
