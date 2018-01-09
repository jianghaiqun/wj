package com.sinosoft.workflow.methods;

import com.sinosoft.framework.script.EvalException;
import com.sinosoft.framework.script.ScriptEngine;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.workflow.Context;

/**
 */
public class MethodScript extends NodeMethod {
	private String script;

	public MethodScript(String script) {
		this.script = script;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.flow.NodeAction#excute()
	 */
	public void execute(Context context) throws EvalException {
		if (StringUtil.isEmpty(script)) {
			return;
		}
		ScriptEngine se = new ScriptEngine(ScriptEngine.LANG_JAVASCRIPT);
		se.importPackage("com.sinosoft.framework.cache");
		se.importPackage("com.sinosoft.framework.data");
		se.importPackage("com.sinosoft.framework.utility");
		se.importPackage("com.sinosoft.workflow");
		se.compileFunction("_tmp", "return " + script);
		se.setVar("context", context);
		se.executeFunction("_tmp");
	}

}
