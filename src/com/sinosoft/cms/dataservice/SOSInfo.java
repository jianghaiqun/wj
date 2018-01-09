package com.sinosoft.cms.dataservice;

import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

public class SOSInfo extends Page {
	
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static Mapx init(Mapx params) {
		// 下拉框
		Mapx map = new Mapx();
		params.put("ChannelTypeCode", HtmlUtil.codeToOptions("SOS.ChannelCode", true));// 所属渠道
		
		params.put("StartDate", DateUtil.toString(new Date(), "yyyy-MM-dd"));// 起始时间
		params.put("EndDate", DateUtil.toString(new Date(), "yyyy-MM-dd"));// 结束时间
		return params;
	}	
	/**
	 * 查询
	 */
	public static void dg1DataBind(DataGridAction dga) {
		
		String StartDate = dga.getParam("StartDate")+" 00:00:00";
		String EndDate = dga.getParam("EndDate")+" 23:59:59";
		String ChannelTypeCode = dga.getParam("ChannelTypeCode");
		StringBuffer sb = new StringBuffer(" SELECT appntName,appntSexName,appntBirthDay,CONCAT(appntArea1,'-',appntArea2) appntArea,appntMobile,appntEmail,(SELECT codename FROM zdcode WHERE codetype='SOS.ChannelCode' AND codevalue=channelSn) channelSn FROM SDAppnt WHERE 1=1  ");
		
		if(StringUtil.isNotEmpty(StartDate)){
			sb.append(" AND createDate >= '"+StartDate+"'");
		}
		if(StringUtil.isNotEmpty(EndDate)){
			sb.append(" AND createDate <= '"+EndDate+"'");
		}
		if(StringUtil.isNotEmpty(ChannelTypeCode)){
			sb.append(" AND channelSn = '"+ChannelTypeCode+"'");
		}
		QueryBuilder qb = new QueryBuilder(sb.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
}