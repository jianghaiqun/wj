package com.sinosoft.ibrms;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

public class MarketingCampaignUI  extends Page{
	
	public static void MarketingCampaignDataBind(DataGridAction dga){
		
		String MarketingCampaignNum = dga.getParam("MarketingCampaignNumT");
		String MarketingCampaignName = dga.getParam("MarketingCampaignNameT");
		String RealTime = dga.getParam("RealTimeT");
		String StartDate = dga.getParam("StartDateT");
		String EndDate = dga.getParam("EndDateT");
		String isIssue = dga.getParam("isIssueT");
		String MarketingDes = dga.getParam("MarketingDesT");
		
		String sql = "select MarketingNum,MarketingName,RealTime,StartDate ,EndDate ,isIssue,MarketingDes from ZDMarketingCampaign " +
					"where 1=1 and Approval='00' ";
		
		if(!StringUtil.isEmpty(MarketingCampaignNum))
	    {
			sql = sql+" and MarketingNum like '%"+MarketingCampaignNum.trim()+"%' ";
	    }
		if(!StringUtil.isEmpty(MarketingCampaignName))
	    {
			sql = sql+" and MarketingName like '%"+MarketingCampaignName.trim()+"%' ";
	    }
		if(!StringUtil.isEmpty(RealTime))
	    {
			sql = sql+" and RealTime = '"+RealTime.trim()+"' ";
	    }
		if(!StringUtil.isEmpty(StartDate))
	    {
			sql = sql+" and name StartDate = '"+StartDate.trim()+"' ";
	    }
		if(!StringUtil.isEmpty(EndDate))
	    {
			sql = sql+" and EndDate = '"+EndDate.trim()+"' ";
	    }
		if(!StringUtil.isEmpty(isIssue))
	    {
			sql = sql+" and isIssue = '"+isIssue.trim()+"' ";
	    }
		if(!StringUtil.isEmpty(MarketingDes))
	    {
			sql = sql+" and MarketingDes like '%"+MarketingDes.trim()+"%' ";
	    }
		
		dga.setTotal(new QueryBuilder(sql));
		
		DataTable dt = new QueryBuilder(sql).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dt.getDataColumn("MarketingNum").setColumnType(DataColumn.STRING);
        dt.getDataColumn("MarketingName").setColumnType(DataColumn.STRING);
        dt.getDataColumn("RealTime").setColumnType(DataColumn.STRING);
        dt.getDataColumn("StartDate").setColumnType(DataColumn.STRING);
        dt.getDataColumn("EndDate").setColumnType(DataColumn.STRING);
        dt.getDataColumn("isIssue").setColumnType(DataColumn.STRING);
        dt.getDataColumn("MarketingDes").setColumnType(DataColumn.STRING);
		
		dga.bindData(dt);
	}
}
