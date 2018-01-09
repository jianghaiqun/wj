package com.sinosoft.ibrms;

import java.util.HashMap;
import java.util.Map;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.Errorx;


public class RuleTemplateMakeUI extends Page{

	public Errorx mErrors = new Errorx();

	public RuleTemplateMakeUI() {

	}

	public boolean submitData() {
		Map<String,String> tMap = new HashMap<String,String>();
		
		tMap.put("RuleTemplateName", $V("RuleTemplateName"));
		tMap.put("RuleTemplateDes", $V("RuleTemplateDes"));
		tMap.put("flag", $V("flag"));
		tMap.put("Operation", $V("Operation"));
		tMap.put("BOMS", $V("BOMS"));
		tMap.put("SQLPara", $V("SQLPara"));
		tMap.put("ViewPara", $V("ViewPara"));
		tMap.put("RuleCh", $V("RuleCh"));
		tMap.put("Creator", $V("Creator"));
		tMap.put("LRTemplate_ID", $V("LRTemplate_ID"));

		
		RuleTemplateMakeBL tRuleMakeBL = new RuleTemplateMakeBL();
		if(!tRuleMakeBL.submitData(tMap,$V("Operate"))){
			mErrors = tRuleMakeBL.mErrors;
			Response.setMessage(mErrors.getMessages()[0]);
			return false;
		}
		Response.setMessage("操作成功！");
		return true;
	}

}