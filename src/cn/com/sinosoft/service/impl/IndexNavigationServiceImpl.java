/**
 * Project Name:wj-code
 * File Name:IndexNavigationServiceImpl.java
 * Package Name:cn.com.sinosoft.service.impl
 * Date:2017年5月12日下午5:40:25
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;

import cn.com.sinosoft.common.annotation.Cache;
import cn.com.sinosoft.entity.ZDCode;
import cn.com.sinosoft.service.IndexNavigationService;
import cn.com.sinosoft.util.RedisConsts;
import net.sf.json.JSONObject;

/**
 * ClassName:IndexNavigationServiceImpl <br/>
 * Function:TODO 用于首页快速导航查询. <br/>
 * Date:2017年5月12日 下午5:40:25 <br/>
 *
 * @author:guozc
 */
@Service
public class IndexNavigationServiceImpl implements IndexNavigationService {

	@Cache(keyPrefix = RedisConsts.REDIS_KEY_TRAVEL_ADDR)
	@Override
	public String getAddressList() {
		QueryBuilder qb = new QueryBuilder("Select * from ZDCode where CodeType='TravelAddress' ");
		DataTable dt = qb.executeDataTable();
		List<ZDCode> codeList = new ArrayList<ZDCode>();
		List<ZDCode> jingneicodeList = new ArrayList<ZDCode>();
		List<ZDCode> jingwaicodeList = new ArrayList<ZDCode>();
		List<ZDCode> remencodeList = new ArrayList<ZDCode>();
		for(int i=0;i<dt.getRowCount();i++){
			codeList.add(dealZDCode(dt.get(i)));
			if("jingnei".equals(dt.getString(i, "Prop4"))){
				jingneicodeList.add(dealZDCode(dt.get(i)));
			}
			if("jingwai".equals(dt.getString(i, "Prop4"))){
				jingwaicodeList.add(dealZDCode(dt.get(i)));
			}
			if("remen".equals(dt.getString(i, "Prop4"))){
				remencodeList.add(dealZDCode(dt.get(i)));
			}
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("address", codeList);
		String travlehtml = "";
		StringBuffer sb = new StringBuffer("<p>");
		for(ZDCode zd:jingneicodeList){
			sb.append("<em class=\"f-ib\">"+zd.getCodeValue()+"</em>");
		}
		sb.append("</p><p style=\"display:none;\">");
		for(ZDCode zd:jingwaicodeList){
			sb.append("<em class=\"f-ib\">"+zd.getCodeValue()+"</em>");
		}
		sb.append("</p><p style=\"display:none;\">");
		for(ZDCode zd:remencodeList){
			sb.append("<em class=\"f-ib\">"+zd.getCodeValue()+"</em>");
		}
		sb.append("</p>");
		travlehtml = sb.toString();
		jsonMap.put("tradeaddress", travlehtml);
		return JSONObject.fromObject(jsonMap).toString();
	}
	
	public ZDCode dealZDCode(DataRow dr){
		ZDCode zdcode = new ZDCode();
		zdcode.setCodeValue(dr.getString("CodeValue"));//中文
		zdcode.setCodeName(dr.getString("CodeName"));
		zdcode.setProp1(dr.getString("Prop1"));//全拼
		zdcode.setProp3(dr.getString("Prop3"));//简写
		return zdcode;
	}

}

