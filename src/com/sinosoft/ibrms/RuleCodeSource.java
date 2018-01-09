package com.sinosoft.ibrms;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;

public class RuleCodeSource extends Page{

	public static DataTable getRuleTemplateID(Mapx params) {
		StringBuilder builder = new StringBuilder();
		builder.append("select ruletemplatename,description from lrruletemplate");
		builder.append(" order by ruletemplatename");
		return new QueryBuilder(builder.toString()).executeDataTable();
	}

}
