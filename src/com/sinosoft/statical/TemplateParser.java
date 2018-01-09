package com.sinosoft.statical;

import com.sinosoft.framework.script.EvalException;
import com.sinosoft.framework.script.ScriptEngine;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParser {
	private static final Logger logger = LoggerFactory.getLogger(TemplateParser.class);
	private static final Pattern pInclude = Pattern.compile(
			"<z:include *?file=\\\"(.*?)\\\" *?((/>)|(.*?</z:include>))", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private static final Pattern pConfig = Pattern.compile("<z:config *?(.*?) *?((/>)|(.*?</z:config>))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private static final Pattern pDefine = Pattern.compile("<z:define *?(.*?) *?((/>)|(.*?</z:define>))",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private static final Pattern pField = Pattern.compile("\\$\\{(\\w+?)\\.(\\w+?)(\\|(.*?))??\\}");

	private static final Pattern pFieldProp1 = Pattern.compile("\\&??(\\w+?)=([^\\|\\\"]*)");

	private static final Pattern pFieldProp2 = Pattern.compile("\\&??(\\w+?)=(\\\"|\\\')(.*?)\\2");

	public static final Pattern pAttr = Pattern.compile("\\s*?(\\w+?)\\s*?=\\s*?(\\\"|\\\')(.*?)\\2",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	/**
	 * @author zhangjinquan 11180
	 * @creadate 2012-12-19
	 * @description 增加全局数据搜索匹配模式
	 */
	private static final Pattern pGlobalField = Pattern.compile("\\$[gG]\\{(\\w+?)\\.(\\w+?)\\}");

	private String fileName;

	private String template;   

	private String includePathBase;

	private String language;

	private String prefix;

	private String script;

	private String result;

	private Mapx defineMap = new Mapx();

	private Mapx pageListPrams = new Mapx();

	private ArrayList packageArr = new ArrayList();

	private ArrayList classArr = new ArrayList();

	private ScriptEngine se;

	public void addPackage(String packageName) {
		packageArr.add(packageName);
	}

	public void addClass(String className) {
		classArr.add(className);
	}

	public void removePackage(String packageName) {
		packageArr.remove(packageName);
	}

	public void removeClass(String className) {
		classArr.remove(className);
	}

	public String getIncludePathBase() {
		return includePathBase;
	}

	public void setIncludePathBase(String includePathBase) {
		this.includePathBase = includePathBase;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String html) {
		this.template = html;
	}

	public String getLanguage() {
		return language;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getResult() {
		return result;
	}

	public void parse() throws EvalException {
		parseInclude();
		parseHead();
		parsePlaceHolder();
		parseGlobalField();
		parseExpression();
		parseScript();
		StringBuffer sb = new StringBuffer();

		if (this.language.equalsIgnoreCase("java")) {
			se = new ScriptEngine(ScriptEngine.LANG_JAVA);
			sb.append("StringBuffer _Result = new StringBuffer();\n");
			sb.append("void write(String str){if(str==null)str=\"\"; _Result.append(str);}\n");
			sb.append("void write(int i){ _Result.append(i);}\n");
			sb.append("void write(long i){ _Result.append(i);}\n");
			sb.append("void write(float i){ _Result.append(i);}\n");
			sb.append("void writeln(String str){if(str==null)str=\"\"; _Result.append(str+\"\\n\");}\n");
			sb.append("void writeln(int str){_Result.append(str+\"\\n\");}\n");
			sb.append("void writeln(long str){_Result.append(str+\"\\n\");}\n");
			sb.append("void writeln(float str){ _Result.append(str+\"\\n\");}\n");
			sb.append(script);
			sb.append("return _Result.toString();\n");
		} else {
			se = new ScriptEngine(ScriptEngine.LANG_JAVASCRIPT);
			sb.append("var _Result = [];\n");
			sb.append("function write(str){_Result.push(str);}\n");
			sb.append("function writeln(str){_Result.push(str+\"\\n\");}\n");
			sb.append(script);
			sb.append("return _Result.join('');\n");
		}
		se.importClass("com.sinosoft.framework.data.DataTable");
		se.importClass("com.sinosoft.framework.data.DataRow");
		se.importClass("com.sinosoft.framework.data.DataColumn");
		se.importClass("com.sinosoft.framework.utility.Mapx");
		se.importClass("com.sinosoft.framework.utility.StringUtil");
		se.importClass("com.sinosoft.framework.utility.DateUtil");
		se.importClass("com.sinosoft.schema.ZCArticleSchema");
		se.importPackage("com.sinosoft.framework.cache");

		for (int i = 0; i < packageArr.size(); i++) {
			se.importPackage((String) packageArr.get(i));
		}

		for (int i = 0; i < classArr.size(); i++) {
			se.importClass((String) classArr.get(i));
		}

		script = sb.toString();
		se.setNeedCheck(true);
		se.compileFunction("_EvalTemplate", script);
	}
	
	/**
	 * @author zhangjinquan 11180
	 * @createdate 2012-12-19
	 * @description 增加解析全局数据，数据格式：$G{ProductList.Total}
	 */
	private void parseGlobalField()
	{
		StringBuffer sb = new StringBuffer();
		Matcher m = pGlobalField.matcher(template);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex))
		{
			sb.append(template.substring(lastEndIndex, m.start()));
			sb.append("<" + prefix);
			String id = m.group(1);
			String key = m.group(2);

			// 将所有的属性转换为小写，增加模板的容错性
			if (id != null)
			{
				id = id.toLowerCase();
			}
			if (key != null)
			{
				key = key.toLowerCase();
			}
			sb.append("write(TemplateData.getGlobalData(\"" + id + "\", \"" + key + "\"));");
			lastEndIndex = m.end();
			sb.append(prefix + ">");
		}
		sb.append(template.substring(lastEndIndex));
		template = sb.toString();
	}

	/**
	 * 处理<z:include>
	 */
	private void parseInclude() {
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isEmpty(template)) {
			return;
		}
		Matcher m = pInclude.matcher(template);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			int s = m.start();
			int e = m.end();
			sb.append(template.substring(lastEndIndex, s));
			sb.append(FileUtil.readText(getFullPath(this.includePathBase, m.group(1))));
			lastEndIndex = e;
		}
		sb.append(template.substring(lastEndIndex));
		template = sb.toString();
	}

	private void parseHead() {
		Matcher m = pConfig.matcher(template);
		if (m.find()) {
			Mapx map = getAttrMap(m.group(1));
			language = (String) map.get("language");
			if (StringUtil.isEmpty(language)) {
				language = "java";
			}
			prefix = (String) map.get("prefix");
			if (StringUtil.isEmpty(prefix)) {
				prefix = "%";
			}
			template = template.substring(0, m.start()) + template.substring(m.end());
		}
		m = pDefine.matcher(template);
		int lastIndex = 0;
		StringBuffer sb = new StringBuffer();
		while (m.find(lastIndex)) {
			Mapx map = getAttrMap(m.group(1));
			String var = (String) map.get("var");
			if (!StringUtil.isEmpty(var)) {
				defineMap.put(var, null);
			}
			sb.append(template.substring(lastIndex, m.start()));
			lastIndex = m.end();
		}
		sb.append(template.substring(lastIndex));
		template = sb.toString().trim();
	}

	/**
	 * 处理模板文件中形如${Table.Field|Prop=Value}的占位符
	 */
	private void parsePlaceHolder() {
		StringBuffer sb = new StringBuffer();
		Matcher m = pField.matcher(template);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			boolean flag = true;
			sb.append(template.substring(lastEndIndex, m.start()));
			sb.append("<" + prefix);
			String table = m.group(1);
			String field = m.group(2);

			// 将所有的属性转换为小写，增加模板的容错性
			if (table != null) {
				table = table.toLowerCase();
			}
			if (field != null) {
				field = field.toLowerCase();
			}
			String prop = m.group(3);
			if (StringUtil.isNotEmpty(prop)) {
				Mapx map = parseFieldProp(prop);
				if (StringUtil.isNotEmpty((String) map.get("charwidth"))) {// 处理CharCount属性
					int c = 0;
					try {
						c = Integer.parseInt(map.get("charwidth").toString());
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					if (c != 0) {
						sb.append("if(" + table + ".getString(\"" + field + "\").length()>" + c + "){\n");
						sb.append("\twrite(StringUtil.subStringEx(" + table + ".getString(\"" + field + "\"),"
								+ (c - 1) + ")+\"...\");\n");
						sb.append("}else{\n");
						sb.append("\twrite(" + table + ".getString(\"" + field + "\"));\n");
						sb.append("}\n");
						flag = false;
					}
				} else if (StringUtil.isNotEmpty((String) map.get("substrwidth"))) {// 处理substrwidth属性
					int c = 0;
					try {
						c = Integer.parseInt(map.get("substrwidth").toString());
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					if (c != 0) {
						sb.append("if(" + table + ".getString(\"" + field + "\").length()>" + c + "){\n");
						sb.append("\twrite(StringUtil.subString(" + table + ".getString(\"" + field + "\"),"
								+ (c) + ")+\"...\");\n");
						sb.append("}else{\n");
						sb.append("\twrite(" + table + ".getString(\"" + field + "\"));\n");
						sb.append("}\n");
						flag = false;
					}
					
				} else if(StringUtil.isNotEmpty((String) map.get("taglink"))){
					if("y".equalsIgnoreCase(map.get("taglink").toString())){
						sb.append("write(StringUtil.tagToHtml( " + table + ".getString(\"" + field + "\") ));\n");
						flag = false;
					} else if("m".equalsIgnoreCase(map.get("taglink").toString())){
						sb.append("write(StringUtil.tagToHtml1( " + table + ".getString(\"" + field + "\") ));\n");
						flag = false;
					} 
				}
				if (map.get("format") != null) {// 处理Format属性，处理日期、html
					sb.append("if(" + table + ".getDataColumn(\"" + field
							+ "\").getColumnType()==DataColumn.DATETIME){\n");
					sb.append("\tDate date = (Date)" + table + ".get(\"" + field + "\");\n");
					sb.append("\tif(date == null){\n\t\tdate= new Date();\n\t}\n");
					sb.append("\twrite(DateUtil.toString(date,\"" + (String) map.get("format") + "\"));\n");
					sb.append("}else if(\"html\".equals(\"" + map.get("format") + "\")){\n");
					sb.append("\twrite(StringUtil.getTextFromHtml(" + table + ".getString(\"" + field + "\")));\n");
					sb.append("}else{\n");
					sb.append("\twrite(" + table + ".getString(\"" + field + "\"));\n");
					sb.append("}\n");
					flag = false;
				}
				if (map.get("lowercase") != null) {
					sb.append("if(" + table + ".getString(\"" + field + "\")!=null){\n");
					sb.append("\twrite(" + table + ".getString(\"" + field + "\").toLowerCase());\n");
					sb.append("}\n");
					flag = false;
				}
				// 处理图片
				if (map.get("imagetype") != null) {
					sb.append("if(" + table + ".getString(\"" + field + "\")!=null){\n");
					sb.append("\tString fieldValue = " + table + ".getString(\"" + field + "\");\n");
					sb.append("\tint index1 = fieldValue.lastIndexOf(\"/\");\n");
					sb.append("\tif(index1>0){\n");
					sb.append("\t\tint index2 = fieldValue.indexOf(\"_\",index1);\n");
					sb.append("\t\tif(index2>0){\n");
					sb.append("\t\t\tString imageType = fieldValue.substring(index1+1,index2);\n");
					sb.append("\t\t\tif(imageType.equals(\"s\")||Character.isDigit(imageType.charAt(0))){\n");
					sb.append("\t\t\t\tfieldValue = fieldValue.substring(0,index1+1)+\""+map.get("imagetype")+"\"+fieldValue.substring(index2);\n");
					sb.append("\t\t\t}\n");
					sb.append("\t\t}\n");
					sb.append("\t}\n");
					sb.append("\twrite(fieldValue);\n");
					sb.append("}\n");
					flag = false;
				}
			}
			if (flag) {
				sb.append("write(" + table + ".getString(\"" + field + "\"));");
			}
			lastEndIndex = m.end();
			sb.append(prefix + ">");
		}
		sb.append(template.substring(lastEndIndex));
		template = sb.toString();
	}

	/**
	 * 解析形如 <%=data.getString("test")%>
	 * 
	 */
	private void parseExpression() {
		StringBuffer sb = new StringBuffer();
		Pattern pJavaExpression = Pattern.compile("<\\"+prefix+"=(.*?)\\"+prefix+">", Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher m = pJavaExpression.matcher(template);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			int e = m.end();
			sb.append(template.substring(lastEndIndex, m.start()));
			sb.append("<"+prefix+"write(" + m.group(1) + ");"+prefix+">");
			lastEndIndex = e;
		}
		sb.append(template.substring(lastEndIndex));
		template = sb.toString();
	}

	private void parseScript() {
		StringBuffer sb = new StringBuffer();
		Pattern pJava = Pattern.compile("<\\"+prefix+"(.*?)\\"+prefix+">", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = pJava.matcher(template);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			int s = m.start();
			int e = m.end();
			javaEncode(sb, template.substring(lastEndIndex, s));
			sb.append(m.group(1));
			lastEndIndex = e;
		}
		javaEncode(sb, template.substring(lastEndIndex));
		script = sb.toString();
	}

	/**
	 * 转换一个形如 a="1" c="2" 的字符串为Mapx
	 */
	public static Mapx getAttrMap(String str) {
		Mapx map = new Mapx();
		Matcher m = pAttr.matcher(str);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			map.put(m.group(1).toLowerCase(), m.group(3));
			lastEndIndex = m.end();
		}
		return map;
	}

	/**
	 * 根据基路径和相对路径得到全路径，其中相对路径允许有类似以../../表示上上级目录的写法
	 */
	private static String getFullPath(String pathBase, String path) {
		path = path.replace('\\', '/');
		pathBase = pathBase.replace('\\', '/');
		while (path.startsWith(".")) {
			if (path.startsWith("./")) {
				path = path.substring(2);
				continue;
			}
			if (path.startsWith("../")) {
				pathBase = pathBase.substring(0, pathBase.lastIndexOf('/') + 1);
				path = path.substring(3);
			}
		}
		return pathBase + path;
	}

	/**
	 * 将html代码转化成Java输出
	 */
	private void javaEncode(StringBuffer sb, String str) {
		if ("".equals(str)) {
			return;
		}
		sb.append("write(\"");
		sb.append(StringUtil.javaEncode(str));
		sb.append("\");\n");
	}

	private static Mapx parseFieldProp(String str) {
		Mapx map = new Mapx();
		str = str.substring(1);
		Matcher m = pFieldProp1.matcher(str);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			map.put(m.group(1).toLowerCase(), m.group(2));
			lastEndIndex = m.end();
		}
		m = pFieldProp2.matcher(str);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			map.put(m.group(1).toLowerCase(), m.group(3));
			lastEndIndex = m.end();
		}
		// System.out.println(map);
		return map;
	}

	public boolean hasError() {
		return false;
	}

	public void define(String varName, Object var) {
		defineMap.put(varName, var);
	}

	public void generate() throws TemplateException {
		Object[] ks = defineMap.keyArray();
		Object[] vs = defineMap.valueArray();
		for (int i = 0; i < ks.length; i++) {
			se.setVar(ks[i].toString(), vs[i]);
		}
		try {
			synchronized (se) {
				result = (String) se.executeFunction("_EvalTemplate");
				// 清空最好，但是怕有问题
				// defineMap.clear();
			}
		} catch (EvalException e) {
			logger.error("错误脚本：\n{}", getScript());
			throw new TemplateException(e);
		}
	}

	public void clear() {
		se.exit();
	}

	public Mapx getDefineMap() {
		return defineMap;
	}

	public void setDefineMap(Mapx defineMap) {
		this.defineMap = defineMap;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Mapx getPageListPrams() {
		return pageListPrams;
	}

	public void setPageListPrams(Mapx pageListPrams) {
		this.pageListPrams = pageListPrams;
	}
}
