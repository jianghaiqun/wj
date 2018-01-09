package com.sinosoft.framework.script;

import com.sinosoft.framework.Page;

public class CheckScript extends Page {
	/**
	 * 用于前台界面用户输入脚本后检查
	 */
	public void check() {
		String script = $V("Script");
		String lang = $V("Language");
		ScriptEngine se = new ScriptEngine(lang.equalsIgnoreCase("java") ? ScriptEngine.LANG_JAVA : ScriptEngine.LANG_JAVASCRIPT);
		try {
			se.compileFunction("Test", script);
		} catch (EvalException ex) {
			StringBuffer sb = new StringBuffer();
			sb.append("第");
			sb.append(ex.getRowNo());
			sb.append("有语法错误:<br><font color='red'>");
			sb.append(ex.getLineSource());
			sb.append("</font>");
			Response.setError(sb.toString());
			return;
		}
		Response.setStatus(1);
	}
}
