package com.sinosoft.ibrms;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.Errorx;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RuleMakeUI extends Page {

	public Errorx mErrors = new Errorx();

	public RuleMakeUI() {

	}

	public boolean submitData() {
		Map<String,Object> tMap = new HashMap<String,Object>();
		tMap.put("flag", $V("flag"));
		tMap.put("Operation",  $V("Operation"));
		tMap.put("TableName",  $V("TableName"));
		tMap.put("LRTemplate_ID",  $V("LRTemplate_ID"));
		tMap.put("BOMS",  $V("BOMS"));
		tMap.put("SQLPara",  $V("SQLPara"));
		tMap.put("ViewPara",  $V("ViewPara"));
		tMap.put("SQLStatement",  $V("SQLStatement"));
		tMap.put("CreateTable",  $V("CreateTable"));
		tMap.put("RuleCh",  $V("RuleCh"));
		
		try {
			java.net.URLDecoder.decode($V("RuleName"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		
		tMap.put("RuleName",  $V("RuleName"));
		tMap.put("RuleDes",  $V("RuleDes"));
		tMap.put("Creator",  $V("Creator"));
		tMap.put("RuleStartDate",  $V("RuleStartDate"));
		tMap.put("RuleEndDate",  $V("RuleEndDate"));
		tMap.put("TempalteLevel",  $V("TempalteLevel"));
		tMap.put("Business",  $V("Business"));
		tMap.put("State",  $V("State"));
		tMap.put("Valid",  $V("Valid"));
		tMap.put("DTData",  $V("DTData"));
		tMap.put("DTColumnTypeMap",  $V("DTColumnTypeMap"));
		tMap.put("MarketingNum",  $V("MarketingNum"));
		String[] TableColumnNameArray = $V("TableColumnName").split(",");
		String[] ColumnDataTypeArray = $V("ColumnDataType").split(",");
		Map<String,String> DTColumnTypeMap = new HashMap<String,String>();

		for (int i = 0; i < TableColumnNameArray.length; i++) {
			DTColumnTypeMap.put(TableColumnNameArray[i],
					ColumnDataTypeArray[i]);
		}
		tMap.put("DTColumnTypeMap", DTColumnTypeMap);
		RuleMakeBL tRuleMakeBL = new RuleMakeBL();
		if(!tRuleMakeBL.submitData(tMap)){
			this.mErrors = tRuleMakeBL.mErrors;
			Response.setMessage(this.mErrors.getMessages()[0]);
			return false;
		}
		Response.setMessage("规则生成成功！");
		return true;
	}
}