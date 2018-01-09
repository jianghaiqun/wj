package com.sinosoft.workflow.methods;

import com.sinosoft.framework.script.EvalException;
import com.sinosoft.framework.script.ScriptEngine;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.workflow.Context;

/**
 */
public class ConditionScript extends ConditionMethod {
	private String script;

	public void setScript(String script) {
		this.script = script;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.flow.Condition#passesCondition(java.lang.String)
	 */
	public boolean validate(Context context) throws EvalException {
		if (StringUtil.isEmpty(script)) {
			return true;
		}
		ScriptEngine se = new ScriptEngine(ScriptEngine.LANG_JAVASCRIPT);
		se.importPackage("com.sinosoft.framework.cache");
		se.importPackage("com.sinosoft.framework.data");
		se.importPackage("com.sinosoft.framework.utility");
		se.compileFunction("_tmp", "return " + script);
		se.setVar("context", context);
		Object obj = se.executeFunction("_tmp");
		if (obj instanceof Boolean) {
			return ((Boolean) obj).booleanValue();
		}
		throw new RuntimeException("流程条件脚本返回的不是布尔型!");
	}
}
