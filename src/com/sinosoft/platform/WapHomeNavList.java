/**
 * Project Name:wj
 * File Name:ProductRedisCache.java
 * Package Name:com.sinosoft.product
 * Date:2016年3月24日上午9:48:08
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.platform;

import com.finance.util.JedisCommonUtil;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

/**
 * ClassName:WapHomeNavList <br/>
 * Function:产品redis缓存管理类 <br/>
 * Date:2016年3月24日 上午9:48:08 <br/>
 *
 * @author:yuzaijiang
 */
public class WapHomeNavList extends Page {
	
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select t.CodeType,t.ParentCode,t.CodeValue,t.CodeName,t.prop1 from zdcode t where t.ParentCode = 'wapHomeNav' order by t.CodeOrder");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	/**
	 * 修改会员等级信息
	 */
	public void edit() {
		String codeType = $V("CodeType");
		String codeValue = $V("CodeValue");
		String codeName = $V("CodeName");
		String prop1 = $V("prop1");
		
		if (StringUtil.isEmpty(codeType)) {
			Response.setLogInfo(0, "请选择条目！");
			return;
		}
		if (StringUtil.isEmpty(codeValue)) {
			Response.setLogInfo(0, "请设置链接地址！");
			return;
		}
		if (StringUtil.isEmpty(codeName)) {
			Response.setLogInfo(0, "请设置显示名称！");
			return;
		}
		if (StringUtil.isEmpty(prop1)) {
			Response.setLogInfo(0, "请设置图片地址！");
			return;
		}
		QueryBuilder qb = new QueryBuilder(
				"update ZDCode set CodeValue=?,CodeName=?,prop1=? where CodeType=?");
		qb.add(codeValue);
		qb.add(codeName);
		qb.add(prop1);
		qb.add(codeType);
		int result = qb.executeNoQuery();
		if((result-1)==0){
			Response.setLogInfo(1, "修改成功!");
		}else{
			Response.setLogInfo(0, "修改失败!");
		}
	}
	/**
	 * 清理首页导航的缓存
	 */
	public void clearCache() {
		boolean removed = false;
		removed = JedisCommonUtil.remove(1,new String[]{Constant.WAPHOMENAVLIST});
		
		if(removed){
			this.Response.setMessage("操作成功,已成功删除记录");
			
		}else{
			//表示出错了
			this.Response.setError("清除缓存出错");
		}
	}
	
	/**
	 * 清理首页广告缓存
	 */
	public void clearCacheForWapCampaigns() {
		boolean removed = false;
		removed = JedisCommonUtil.remove(1, new String[]{Constant.HOMEIMAGEPLAYER});
		
		if(removed){
			this.Response.setMessage("操作成功,已成功删除缓存！");
			
		}else{
			//表示出错了
			this.Response.setError("清除缓存出错！");
		}
	}
}
