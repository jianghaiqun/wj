package com.sinosoft.message;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.sinosoft.forward.ShopDispatchAction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SCBkEditSchema;
import com.sinosoft.schema.SCBkEntrySchema;
import com.sinosoft.schema.SCBkEntrySet;

/**
 * @Author 周翔
 * @Date 2012-5-24
 * @Mail zhouxiang@sinosoft.com
 */

public class BKEditSaveAction extends ShopDispatchAction {

	public String save(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String tContent=URLDecoder.decode(request.getParameter("Content"), "UTF-8");
		String tId=URLDecoder.decode(request.getParameter("ID"), "UTF-8");
		if(StringUtil.isEmpty(tContent)){
			map.put("test", "内容不能为空！");
			JSONObject json1 = JSONObject.fromObject(map);
			return json1.toString();
		}
		//执行存储
		SCBkEntrySchema tSCBkEntrySchema=new SCBkEntrySchema();
		SCBkEntrySet tSCBkEntrySet=tSCBkEntrySchema.query(new QueryBuilder("where EntryId in (" + tId + ")"));
		Transaction trans = new Transaction();
		
		SCBkEditSchema tSCBkEditSchema=new SCBkEditSchema();
		tSCBkEditSchema.setId(NoUtil.getMaxID("EntryEditID")+"");
		tSCBkEditSchema.setTitleId(tSCBkEntrySet.get(0).getId());
		if(StringUtil.isEmpty((String) request.getSession().getAttribute("loginMemberId"))){
			map.put("test", "请先登录用户！");
			JSONObject json1 = JSONObject.fromObject(map);
			return json1.toString();
		}else{
			tSCBkEditSchema.setUserId((String) request.getSession().getAttribute("loginMemberId"));
		}
		tSCBkEditSchema.setAddUserIP(request.getRemoteAddr());
		tSCBkEditSchema.setEntryContent(tContent);
		tSCBkEditSchema.setaState("00");
		tSCBkEditSchema.setCreateDate(new Date());
		
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
	

}
