package com.sinosoft.framework.script;

import bsh.EvalError;
import bsh.Interpreter;
import com.sinosoft.framework.utility.Mapx;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptEngine {
	private static final Logger logger = LoggerFactory.getLogger(ScriptEngine.class);

	public static final int LANG_JAVASCRIPT = 0;
	public static final int LANG_JAVA = 1;
	private int language;
	private ArrayList carr = new ArrayList();

	private ArrayList parr = new ArrayList();

	private Mapx funcMap = new Mapx();
	private Mapx exceptionMap = new Mapx();

	private Mapx varMap = new Mapx();
	private boolean isNeedCheck;
	private static final Pattern JavaLineInfoPattern = Pattern.compile("error at line (\\d*?)\\, column (\\d*?)\\.", 34);

	public ScriptEngine(int language) {
		this.language = language;
	}

	public void importClass(String className) {
		this.carr.add(className);
	}

	public void importPackage(String pacckageName) {
		this.parr.add(pacckageName);
	}

	public void compileFunction(String funcName, String script) throws EvalException {
		if ((this.isNeedCheck) && (!SecurityChecker.check(script))) {
			EvalException ee = new EvalException("脚本中引用了被禁止的包或者类!", "", "", 0, 0);
			this.exceptionMap.put(funcName, ee);
			throw ee;
		}
		this.exceptionMap.remove(funcName);

		StringBuffer sb = new StringBuffer();
		if (this.language == 1) {
			for (int i = 0; i < this.carr.size(); i++) {
				sb.append("import " + this.carr.get(i) + ";\n");
			}
			for (int i = 0; i < this.parr.size(); i++) {
				sb.append("import " + this.parr.get(i) + ".*;\n");
			}
			sb.append(funcName + "(){\n");
			sb.append(script);
			sb.append("}\n");
			Interpreter itp = new Interpreter();
			try {
				itp.eval(sb.toString());
			} catch (EvalError e) {
				logger.error(e.getMessage(), e);
				String message = e.getMessage();
				Matcher m = JavaLineInfoPattern.matcher(message);
				int row = 0;
				int col = 0;
				String lineSource = "";
				if (m.find()) {
					row = Integer.parseInt(m.group(1));
					if (row <= this.carr.size()) {
						message = "引入类发生错误!";
						lineSource = this.carr.get(row - 1).toString();
					} else if (row <= this.parr.size()) {
						message = "引入包发生错误!";
						lineSource = this.parr.get(row - 1).toString();
					} else {
						row = row - this.carr.size() - this.parr.size() - 1;
						lineSource = script.split("\\n")[(row - 1)];
						col = Integer.parseInt(m.group(2));
					}
				}
				throw new EvalException("第" + row + "行有语法错误: " + lineSource, message, lineSource, row, col);
			}
			this.funcMap.put(funcName, itp);
		} else {
			for (int i = 0; i < this.carr.size(); i++) {
				sb.append("importClass(Packages." + this.carr.get(i) + ");\n");
			}
			for (int i = 0; i < this.parr.size(); i++) {
				sb.append("importPackage(Packages." + this.parr.get(i) + ");\n");
			}
			sb.append("function " + funcName + "(){\n");
			sb.append(script);
			sb.append("}\n");
			sb.append(funcName + "();\n");
			Context ctx = Context.enter();
			ctx.setOptimizationLevel(1);
			Script compiledScript = null;
			try {
				compiledScript = ctx.compileString(sb.toString(), "", 1, null);
			} catch (EvaluatorException e) {
				int row = e.lineNumber() - 1;
				throw new EvalException("第" + row + "行有语法错误: " + e.lineSource(), e.getMessage(), e.lineSource(), row, e.columnNumber());
			}
			this.funcMap.put(funcName, compiledScript);
		}
	}

	public Object executeFunction(String funcName) throws EvalException {
		Object ee = this.exceptionMap.get(funcName);
		if (ee != null) {
			throw ((EvalException) ee);
		}

		Object o = this.funcMap.get(funcName);
		if (this.language == 1)
			try {
				Interpreter itp = (Interpreter) o;
				Object[] ks = this.varMap.keyArray();
				Object[] vs = this.varMap.valueArray();
				for (int i = 0; i < this.varMap.size(); i++) {
					itp.set(ks[i].toString(), vs[i]);
				}
				return itp.eval(funcName + "();");
			} catch (EvalError e) {
				logger.error(e.getMessage(), e);
				String message = e.getMessage();
				int col = 0;
				int row = e.getErrorLineNumber() - 1;
				throw new EvalException("第" + row + "行有语法错误: " + e.getErrorText(), message, e.getErrorText(), row, col);
			}
		try {
			Script compiledScript = (Script) o;
			Context ctx = Context.enter();
			ImporterTopLevel scope = new ImporterTopLevel(ctx);
			Object[] ks = this.varMap.keyArray();
			Object[] vs = this.varMap.valueArray();
			for (int i = 0; i < this.varMap.size(); i++) {
				ScriptableObject.putProperty(scope, ks[i].toString(), vs[i]);
			}
			return compiledScript.exec(ctx, scope);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	public void setVar(String name, Object value) {
		this.varMap.put(name, value);
	}

	public int getLanguage() {
		return this.language;
	}

	public static Object evalJavaScript(String js) throws EvalException {
		Context ctx = Context.enter();
		ImporterTopLevel scope = new ImporterTopLevel(ctx);
		ctx.setOptimizationLevel(1);
		Script compiledScript = null;
		int row;
		try {
			compiledScript = ctx.compileString(js, "", 1, null);
			return compiledScript.exec(ctx, scope);
		} catch (EvaluatorException e) {
			row = e.lineNumber() - 1;
			throw new EvalException("第" + row + "行有语法错误: " + e.lineSource(), e.getMessage(), e.lineSource(), row, e.columnNumber());
		}
	}

	public static Object evalJava(String java) throws EvalException {
		Interpreter itp = new Interpreter();
		String message;
		int row;
		int col;
		String lineSource;
		try {
			return itp.eval(java);
		} catch (EvalError e) {
			message = e.getMessage();
			Matcher m = JavaLineInfoPattern.matcher(message);
			row = 0;
			col = 0;
			lineSource = "";
			if (m.find()) {
				row = Integer.parseInt(m.group(1));
				lineSource = java.split("\\n")[(row - 1)];
				col = Integer.parseInt(m.group(2));
			}
		}
		throw new EvalException("第" + row + "行有语法错误: " + lineSource, message, lineSource, row, col);
	}

	public void exit() {
		if (this.language == 0)
			Context.exit();
	}

	public boolean isNeedCheck() {
		return this.isNeedCheck;
	}

	public void setNeedCheck(boolean isNeedCheck) {
		this.isNeedCheck = isNeedCheck;
	}
}