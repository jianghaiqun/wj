package com.sinosoft.ibrms;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

public class BOMDataQueryUI  extends Page{

	
	public static void BODataBind(DataGridAction dga){
		
		String bomEName = dga.getParam("eName");
		String bomCName = dga.getParam("cName");
		
		String sql = "select name as Name,cnname as CNName,fbom as FBom,localitem as BomLocalItem ,fatheritem as FatherItem ,bomlevel as BomLevel,business as Business,discription as Discription,source as Source,valid as Valid from LRBOM " +
				"where 1=1 ";
		if(!StringUtil.isEmpty(bomEName))
	    {
			sql = sql+" and name like '%"+bomEName.trim()+"%' ";
	    }
		if(!StringUtil.isEmpty(bomCName))
	    {
			sql = sql+" and cnname like '%"+bomCName.trim()+"%' ";
	    }
		sql = sql + " order by name";
		
		dga.setTotal(new QueryBuilder(sql));
		
		DataTable dt = new QueryBuilder(sql).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dt.getDataColumn("Name").setColumnType(DataColumn.STRING);
        dt.getDataColumn("CNName").setColumnType(DataColumn.STRING);
        dt.getDataColumn("FBom").setColumnType(DataColumn.STRING);
        dt.getDataColumn("BomLocalItem").setColumnType(DataColumn.STRING);
        dt.getDataColumn("FatherItem").setColumnType(DataColumn.STRING);
        dt.getDataColumn("BomLevel").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Business").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Discription").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Source").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Valid").setColumnType(DataColumn.STRING);
        dga.bindData(dt);
	}
	
	
	public static void BIDataBind(DataGridAction dga){
		if(StringUtil.isEmpty(dga.getParam("eName")))
	     {
	        dga.setTotal(0);
	        dga.bindData(new DataTable());
	        return;
	     }
		String sql = "select name as Name,                        "+
					 "       bomname as BomName,                  "+
					 "       cnname as CnName,                    "+
					 "       connector as Connectoer,             "+
					 "       ishierarchical as IsHierarchical,    "+
					 "       isbase as IsBase,                    "+
					 "       sourcetype as SourceType,            "+
					 "       source as Source,                    "+
					 "       commandtype as CommandType,          "+
					 "       precheck as PreCheck,                "+
					 "       valid as Valid,                      "+
					 "       description as Description           "+
					 "  from lrbomitem                            "+
					 " where 1 = 1                                "+
					 "   and bomname like '%"+dga.getParam("eName").trim()+"%' "+
					 " order by name";
		
		dga.setTotal(new QueryBuilder(sql));
		
		DataTable dt = new QueryBuilder(sql).executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		dt.getDataColumn("Name").setColumnType(DataColumn.STRING);
        dt.getDataColumn("BomName").setColumnType(DataColumn.STRING);
        dt.getDataColumn("CnName").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Connectoer").setColumnType(DataColumn.STRING);
        dt.getDataColumn("IsHierarchical").setColumnType(DataColumn.STRING);
        dt.getDataColumn("IsBase").setColumnType(DataColumn.STRING);
        dt.getDataColumn("SourceType").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Source").setColumnType(DataColumn.STRING);
        dt.getDataColumn("CommandType").setColumnType(DataColumn.STRING);
        dt.getDataColumn("PreCheck").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Valid").setColumnType(DataColumn.STRING);
        dt.getDataColumn("Description").setColumnType(DataColumn.STRING);
        dga.bindData(dt);
	}
	
	


	public static void dgDataBind(DataGridAction dga) {
		String name = dga.getParam("LRName").trim();
		String commandtype=dga.getParam("CommandType").trim();
		String valid = dga.getParam("Valid").trim();
		QueryBuilder qb = new QueryBuilder( "select name, display, Replace(implenmation, '''', '\"') "
				+"implenmation, valid, commandtype, resulttype, paratype, paranum, "
				+" description from LRCOMMAND where 1=1 ");
		if(!StringUtil.isEmpty(name))
	    {
			qb.append(" and name like '%"+name+"%' ");
	    }
		if(!StringUtil.isEmpty(commandtype))
	    {
			qb.append(" and commandtype='"+commandtype+"' ");
	    }
		if(!StringUtil.isEmpty(valid))
	    {
			qb.append(" and valid='"+valid+"' ");
	    }
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
		
	}
}
