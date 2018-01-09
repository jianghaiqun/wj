package com.sinosoft.ibrms;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;


public class RuleDataQueryUI  extends Page{
	
	public static void RuleTemplateTDataBind(DataGridAction dga){
		
		String ruleName = dga.getParam("RuleName");
		String ruleDes = dga.getParam("RuleDes");
		String business = dga.getParam("Business");
		String ruleStartDate = dga.getParam("RuleStartDate");
		String ruleEndDate = dga.getParam("RuleEndDate");
		String queryState = dga.getParam("QueryState");
		String LRTemplateName = dga.getParam("LRTemplateName");

		String sql = "select t.rulename, t.business, (select codename from zdcode where codetype = 'ibrmsbusiness' and parentcode=t.business) cbusiness,t.templatelevel,t.startdate,t.enddate,t.creator,t.state,t.description,t.id ,t.rulech,'"+LRTemplateName.trim()+"' tbname,marketingnum from "+LRTemplateName.trim()+" t where state in ('"+queryState.trim()+"')" ;
		
		if(!StringUtil.isEmpty(ruleName))
	    {
			sql = sql+" and rulename like'%"+ruleName.trim()+"%' ";
	    }
		if(!StringUtil.isEmpty(ruleDes))
	    {
			sql = sql+" and description like '%"+ruleDes.trim()+"%' ";
	    }
		if(!StringUtil.isEmpty(business))
	    {
			sql = sql+" and business = '"+business.trim()+"' ";
	    }
		if(!StringUtil.isEmpty(ruleStartDate))
	    {
			sql = sql+" and StartDate = '"+ruleStartDate.trim()+"' ";
	    }
		if(!StringUtil.isEmpty(ruleEndDate))
	    {
			sql = sql+" and EndDate = '"+ruleEndDate.trim()+"' ";
	    }
		sql = sql + " order by id";
		
		
		dga.setTotal(new QueryBuilder(sql));
		
		DataTable dt = new QueryBuilder(sql).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dt.getDataColumn("RuleName").setColumnType(DataColumn.STRING);
        dt.getDataColumn("cBusiness").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Templatelevel").setColumnType(DataColumn.STRING);
        dt.getDataColumn("StartDate").setColumnType(DataColumn.STRING);
        dt.getDataColumn("EndDate").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Creator").setColumnType(DataColumn.STRING);
        dt.getDataColumn("State").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Description").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Id").setColumnType(DataColumn.STRING);
        dt.getDataColumn("TbName").setColumnType(DataColumn.STRING);
        dga.bindData(dt);
	}
	
	
	public static void LRRuleTemplateDataBind(DataGridAction dga){
		String tRuleTemplateName = dga.getParam("RuleTemplateNameT");
		String tRuleTemplateDes = dga.getParam("RuleTemplateDesT");
		
		String sql = "select id,ruletemplatename,description,creator,'lrruletemplate' TbName from lrruletemplate where 1=1 ";
		
		if(!StringUtil.isEmpty(tRuleTemplateName))
	    {
			sql = sql+" and ruletemplatename like '%"+tRuleTemplateName.trim()+"%' ";
	    }
		if(!StringUtil.isEmpty(tRuleTemplateDes))
	    {
			sql = sql+" and description like '%"+tRuleTemplateDes.trim()+"%' ";
	    }
		
		sql = sql + " order by id";
		
		dga.setTotal(new QueryBuilder(sql));
		
		DataTable dt = new QueryBuilder(sql).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dt.getDataColumn("RuleTemplateName").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Creator").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Description").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Id").setColumnType(DataColumn.STRING);
        dt.getDataColumn("TbName").setColumnType(DataColumn.STRING);
        dga.bindData(dt);
	}
}
