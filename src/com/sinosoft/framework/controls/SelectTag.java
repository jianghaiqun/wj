package com.sinosoft.framework.controls;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectTag extends BodyTagSupport {
	private static final Logger logger = LoggerFactory.getLogger(SelectTag.class);

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String onChange;

	private String style;

	private int listWidth;

	private int listHeight;

	private String listURL;

	private String verify;

	private String condition;

	private String value;

	private String className;

	private boolean disabled;

	private boolean input;

	private String code;

	private String conditionField;

	private String conditionValue;

	private boolean showValue;

	private boolean lazy;

	private boolean defaultblank;

	private String method;

	private static CodeSource codeSourceInstance;

	private int selectedIndex = 0;

	private int optionCount = 0;

	private static Object mutex = new Object();

	public static final Pattern POption = Pattern.compile(
			"<(span|option).*?value=(\\\"|\\\')(.*?)\\2.*?>(.*?)</(span|option)>", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);

	public void setPageContext(PageContext pc) {
		super.setPageContext(pc);
		this.id = null;
		this.name = null;
		this.className = null;
		this.code = null;
		this.method = null;
		this.condition = null;
		this.conditionField = null;
		this.conditionValue = null;
		this.disabled = false;
		this.input = false;
		this.showValue = false;
		this.value = null;
		this.verify = null;
		this.onChange = null;
		this.style = null;
		this.defaultblank = false;
		this.listWidth = 0;
		this.listHeight = 0;
		this.listURL = null;
		this.lazy = false;
		this.selectedIndex = 0;
		this.optionCount = 0;
	}

	public int doAfterBody() throws JspException {
		String content = this.getBodyContent().getString();
		try {
			this.getPreviousOut().print(getHtml(content));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {// 没有body时也要执行
		if (bodyContent == null || StringUtil.isEmpty(bodyContent.getString())) {
			try {
				this.pageContext.getOut().print(getHtml(""));
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return EVAL_PAGE;
	}

	/**
	 * 便于在Java文件中调用
	 */
	public String getHtml(String content) {
		content = parseOptions(content);
		String codeData = "";
		if (StringUtil.isNotEmpty(code) || StringUtil.isNotEmpty(method)) {
			codeData += getCodeData();
		}
		if (StringUtil.isEmpty(id)) {// 产生随机ID
			id = "_ZVING_NOID_";
		}
		if (StringUtil.isEmpty(name)) {
			name = id;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<div id='" + id + "' name='" + name + "' selectedIndex='" + selectedIndex + "' ztype='Select'");
		if (StringUtil.isNotEmpty(className)) {
			sb.append("  class='" + className + " ");
			sb.append(" zSelect'");
		} else {
			sb.append(" class='zSelect'");
		}
		if (StringUtil.isNotEmpty(style)) {
			sb.append(" style=\"display:inline-block; *zoom: 1;*display: inline;vertical-align:middle;"
					+ "position:relative;height:20px;white-space: nowrap;" + "" + style + "\"");
			sb.append(" styleOriginal=\"" + style + "\"");
		} else {
			sb.append(" style=\"display:inline-block; *zoom: 1;*display: inline;vertical-align:middle;"
					+ "position:relative;height:20px;white-space: nowrap;" + "\"");
			sb.append(" styleOriginal='NULL'");
		}
		if (StringUtil.isNotEmpty(onChange)) {
			sb.append(" onChange=\"" + onChange + "\"");
		}
		if (disabled) {
			sb.append(" disabled=\"" + disabled + "\"");
		}
		if (StringUtil.isNotEmpty(className)) {
			sb.append(" zclass=\"" + className + "\"");
		}
		if (listWidth > 0) {
			sb.append(" listWidth=\"" + listWidth + "\"");
		}
		if (listHeight > 0) {
			sb.append(" listHeight=\"" + listHeight + "\"");
		}
		if (StringUtil.isNotEmpty(listURL)) {
			sb.append(" listURL=\"" + listURL + "\"");
		}
		if (StringUtil.isNotEmpty(verify)) {
			sb.append(" verify=\"" + verify + "\"");
		}
		if (StringUtil.isNotEmpty(condition)) {
			sb.append(" condition=\"" + condition + "\"");
		}
		if (input) {
			sb.append(" input=\"" + input + "\"");
		}
		if (lazy) {
			sb.append(" lazy=\"" + lazy + "\"");
		}
		if (showValue) {
			sb.append(" showValue=\"" + showValue + "\"");
		}
		if (StringUtil.isNotEmpty(code)) {
			sb.append(" code=\"" + code + "\"");
		}
		if (StringUtil.isNotEmpty(method)) {
			sb.append(" method=\"" + method + "\"");
		}
		sb.append(" defaultblank=\"" + defaultblank + "\"");
		if (StringUtil.isNotEmpty(conditionField)) {
			sb.append(" conditionField=\"" + conditionField + "\"");
		}
		if (StringUtil.isNotEmpty(conditionValue)) {
			sb.append(" conditionValue=\"" + conditionValue + "\"");
		}
		if (StringUtil.isNotEmpty(value)) {
			sb.append(" value=\"" + value + "\"");
			sb.append(" initValue=\"" + value + "\"");
		}
		sb.append(">");

		// 以下为文本框的属性
		sb.append("<input type='text' id='" + id + "_textField' ztype='select' autocomplete='off'");
		if (StringUtil.isNotEmpty(verify)) {
			sb.append(" verify=\"" + verify + "\"");
		}
		sb.append(" name=\"" + name + "\"");
		if (StringUtil.isNotEmpty(className)) {
			sb.append(" class=\"" + className + "Input\"");
		}
		if (StringUtil.isNotEmpty(condition)) {
			sb.append(" condition=\"" + condition + "\"");
		}
		if (StringUtil.isNotEmpty(style)) {
			sb.append(" style=\"vertical-align:top; background:transparent none; cursor:default;" + style + "\"");
		} else {
			sb.append(" style=\"vertical-align:top; background:transparent none; cursor:default;\"");
		}
		sb.append(" value=''/>");
		sb.append("<img class='arrowimg' src='" + Config.getContextPath()
				+ "Framework/Images/blank.gif' width='18' height='20' id='" + id
				+ "_arrow' style='position:relative; left:-17px; margin-right:-18px; cursor:pointer; "
				+ "width:18px; height:20px;vertical-align:top;'/>");
		sb.append("<div id='" + id + "_list' class='optgroup' style='text-align:left;display:none;'>");
		sb.append("<div id='" + id + "_ul' style='left:-1px; width:-1px;'>");

		if (defaultblank) {// 加空的选项
			sb.append(getOption("", ""));
		}
		sb.append(content);

		if (StringUtil.isNotEmpty(codeData)) {
			sb.append(codeData);
		}
		sb.append("</div></div></div>");
		return sb.toString();
	}

	private String getOption(String text, String value) {
		optionCount++;
		return getOptionHtml(text, value, false);
	}

	public static String getOptionHtml(String text, String value, boolean flag) {
		return "<a href=\"javascript:void(0);\" onclick=\"Selector.onItemClick(this);\""
				+ " onmouseover='Selector.onItemMouseOver(this)' " + (flag ? "selected='true'" : "")
				+ " hidefocus value=\"" + value + "\">" + text + "</a>";
	}

	private String parseOptions(String content) {
		if (content.indexOf("select") >= 0) {// 旧的用法直接输出options了
			HtmlSelect select = new HtmlSelect();
			try {
				select.parseHtml(content);
				if (StringUtil.isEmpty(id)) {
					id = select.getID();
				}
				if (StringUtil.isEmpty(className)) {
					className = select.getClassName();
				}
				if (StringUtil.isEmpty(style)) {
					style = select.getStyle();
				}
				if (StringUtil.isEmpty(code)) {
					code = select.getAttribute("code");
				}
				if (StringUtil.isEmpty(code)) {
					code = select.getAttribute("code");
				}
				if (StringUtil.isEmpty(condition)) {
					condition = select.getAttribute("condition");
				}
				if (StringUtil.isEmpty(conditionField)) {
					conditionField = select.getAttribute("conditionfield");
				}
				if (StringUtil.isEmpty(conditionValue)) {
					conditionValue = select.getAttribute("conditionvalue");
				}
				if ("true".equals(select.getAttribute("disabled"))) {
					disabled = true;
				}
				if ("true".equals(select.getAttribute("input"))) {
					input = true;
				}
				if ("true".equals(select.getAttribute("defaultblank"))) {
					defaultblank = true;
				}
				if ("true".equals(select.getAttribute("showvalue"))) {
					showValue = true;
				}
				if ("true".equals(select.getAttribute("lazy"))) {
					lazy = true;
				}
				try {
					if (Integer.parseInt(select.getAttribute("listwidth")) > 0) {
						listWidth = Integer.parseInt(select.getAttribute("listwidth"));
					}
				} catch (Exception e) {
				}
				try {
					if (Integer.parseInt(select.getAttribute("listheight")) > 0) {
						listHeight = Integer.parseInt(select.getAttribute("listheight"));
					}
				} catch (Exception e) {
				}
				if (StringUtil.isEmpty(value)) {
					value = select.getAttribute("value");
				}
				if (StringUtil.isEmpty(verify)) {
					verify = select.getAttribute("verify");
				}
				if (StringUtil.isEmpty(onChange)) {
					onChange = select.getAttribute("onchange");
				}
				if (StringUtil.isEmpty(listURL)) {
					listURL = select.getAttribute("listurl");
				}
				content = select.getInnerHTML();
			} catch (Exception e) {
				if (StringUtil.isEmpty(id)) {
					throw new RuntimeException("必须为<z:select>标签或<select>元素定义ID");
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		Matcher m = POption.matcher(content);
		int lastIndex = 0;
		int i = 0;
		while (m.find(lastIndex)) {
			String tmp = content.substring(lastIndex, m.start());
			if (StringUtil.isNotEmpty(tmp.trim())) {// 防止${}符号被漂没
				sb.append(tmp);
			}
			if (showValue) {
				sb.append(getOption(m.group(3) + (StringUtil.isNotEmpty(m.group(4)) ? "-" + m.group(4) : ""), m
						.group(3)));
			} else {
				sb.append(getOption(m.group(4), m.group(3)));
			}
			if (m.group().toLowerCase().substring(0, m.group().indexOf(">")).indexOf("selected") > 0) {
				selectedIndex = i;
			}
			if (m.group(3).equals(value)) {
				selectedIndex = i;
			}
			lastIndex = m.end();
			i++;
		}
		if (lastIndex != content.length() - 1) {
			sb.append(content.substring(lastIndex));
		}
		if (i != 0) {
			content = sb.toString();
		}
		return content;
	}

	private String getCodeData() {
		if (lazy) {// 延迟加载，前台页面通过JS加载
			return "";
		}
		DataTable dt = null;
		Mapx params = ServletUtil.getParameterMap((HttpServletRequest) pageContext.getRequest());
		if (StringUtil.isNotEmpty(conditionField)) {
			params.put("ConditionField", conditionField);
			params.put("ConditionValue", conditionValue);
		} else {
			params.put("ConditionField", "1");
			params.put("ConditionValue", "1");
		}
		if (StringUtil.isEmpty(method) && code.startsWith("#")) {
			method = code.substring(1);
		}
		if (StringUtil.isNotEmpty(method)) {
			String className = method.substring(0, method.lastIndexOf("."));
			String methodName = method.substring(method.lastIndexOf(".") + 1);
			try {
				Class c = Class.forName(className);
				Object o = c.getMethod(methodName, new Class[] { Mapx.class }).invoke(null, new Object[] { params });
				dt = (DataTable) o;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException("确认类" + className + "的方法" + methodName + "返回值是DataTable类型!");
			}
		} else {
			initCodeSource();
			dt = codeSourceInstance.getCodeData(code, params);
		}
		StringBuffer sb = new StringBuffer();
		if (dt != null) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (dt.getString(i, 0).equals(value)) {
					selectedIndex = optionCount;
				}
				if (!showValue) {
					sb.append(getOption(dt.getString(i, 1), dt.getString(i, 0)));
				} else {
					sb.append(getOption(dt.getString(i, 0) + "-" + dt.getString(i, 1), dt.getString(i, 0)));
				}
			}
			if (dt.getColCount() > 2) {// 需要添加脚本
				sb.append("<script>Page.onLoad(Selector_" + id + "_Init,10);");
				sb.append("function Selector_" + id + "_Init(){");
				sb.append(DataCollection.dataTableToJS(dt));
				sb.append("$('" + id + "').DataSource = new DataTable();;");
				sb.append("$('" + id + "').DataSource.init(_Zving_Cols,_Zving_Values);");
				sb.append("}\n</script>\n");
			}
		}
		return sb.toString();
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getListWidth() {
		return listWidth;
	}

	public void setListWidth(int listWidth) {
		this.listWidth = listWidth;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public int getListHeight() {
		return listHeight;
	}

	public void setListHeight(int listHeight) {
		this.listHeight = listHeight;
	}

	public String getListURL() {
		return listURL;
	}

	public void setListURL(String listURL) {
		this.listURL = listURL;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInput() {
		return input;
	}

	public void setInput(boolean input) {
		this.input = input;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getConditionField() {
		return conditionField;
	}

	public void setConditionField(String conditionField) {
		this.conditionField = conditionField;
	}

	public String getConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public boolean getShowValue() {
		return showValue;
	}

	public void setShowValue(boolean showValue) {
		this.showValue = showValue;
	}

	public boolean getInput() {
		return input;
	}

	public static void initCodeSource() {
		if (codeSourceInstance == null) {
			synchronized (mutex) {
				if (codeSourceInstance == null) {
					String className = Config.getValue("App.CodeSource");
					if (StringUtil.isEmpty(className)) {
						logger.warn("framework.xml中未配置CodeSource类!");
						return;
					}
					try {
						Class c = Class.forName(className);
						Object o = c.newInstance();
						codeSourceInstance = (CodeSource) o;
					} catch (Exception e) {
						throw new RuntimeException("framework.xml中的CodeSource配置不正确,请确认类" + className + "存在!");
					}
				}
			}
		}
	}

	public static CodeSource getCodeSourceInstance() {
		initCodeSource();
		return codeSourceInstance;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public boolean isDefaultblank() {
		return defaultblank;
	}

	public void setDefaultblank(boolean defaultblank) {
		this.defaultblank = defaultblank;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
