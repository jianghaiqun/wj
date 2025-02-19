package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.HtmlTD;
import com.sinosoft.framework.controls.HtmlTR;
import com.sinosoft.framework.controls.HtmlTable;
import com.sinosoft.framework.controls.SelectTag;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.XMLUtil;
import com.sinosoft.schema.ZCCustomTableColumnSchema;
import com.sinosoft.schema.ZCCustomTableColumnSet;
import com.sinosoft.schema.ZCCustomTableSchema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.html.HTMLDivElement;
import org.w3c.dom.html.HTMLOptionElement;
import org.w3c.dom.html.HTMLSelectElement;

import java.io.IOException;
import java.util.regex.Pattern;

public class Form extends Page {
	public static final String FieldPrefix = "_Form_";

	public static Mapx initContentDialog(Mapx params) {
		String id = params.getString(("ID"));
		if (StringUtil.isNotEmpty(id)) {
			params.put("Content", getEditorFormContent(id).replace('\"', '\''));
		}
		return params;
	}

	public static String getEditorFormContent(String ID) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(ID);
		if (!table.fill()) {
			return null;
		}
		return getEditorFormContent(table);
	}

	public static String getEditorFormContent(ZCCustomTableSchema table) {
		String content = table.getFormContent();
		if (StringUtil.isEmpty(content)) {
			StringBuffer sb = new StringBuffer();
			HtmlTable ht = new HtmlTable();
			ht.setStyle("width:600px;border-collapse:collapse;");
			ht.setAttribute("class", "CustomTableForm");
			ht.setAttribute("align", "center");
			ht.setAttribute("cellpadding", "3");

			// 表头
			HtmlTR tr = new HtmlTR();
			tr.setHeight(30);
			HtmlTD td = new HtmlTD();
			td.setColSpan("2");
			td.InnerHTML = "<b>" + table.getName() + "</b>";
			tr.addTD(td);
			ht.addTR(tr);

			ZCCustomTableColumnSchema column = new ZCCustomTableColumnSchema();
			column.setTableID(table.getID());
			ZCCustomTableColumnSet set = column.query();
			for (int i = 0; i < set.size(); i++) {
				column = set.get(i);
				if ("Y".equals(column.getIsAutoID())) {// 自动编号不需要填写
					continue;
				}
				tr = new HtmlTR();
				tr.setHeight(30);

				td = new HtmlTD();
				td.setAlign("right");
				td.InnerHTML = column.getName() + ": ";
				td.setAttribute("width", "25%");
				tr.addTD(td);

				td = new HtmlTD();
				td.setAttribute("width", "75%");
				td.setAlign("left");
				String verify = "";
				if ("Y".equals(column.getIsMandatory())) {
					verify = "NotNull";
				}
				int type = Integer.parseInt(column.getDataType());
				if (type == DataColumn.DATETIME) {
					if (StringUtil.isNotEmpty(verify)) {
						verify = verify + "&&DateTime' ztype='Date";
					} else {
						verify = "DateTime' ztype='Date";
					}
				} else if (type == DataColumn.INTEGER || type == DataColumn.LONG || type == DataColumn.SMALLINT) {
					if (StringUtil.isNotEmpty(verify)) {
						verify = verify + "&&Int";
					} else {
						verify = "Int";
					}
				} else if (type == DataColumn.FLOAT || type == DataColumn.DECIMAL || type == DataColumn.DOUBLE
						|| type == DataColumn.BIGDECIMAL) {
					if (StringUtil.isNotEmpty(verify)) {
						verify = verify + "&&Number";
					} else {
						verify = "Number";
					}
				}
				String inputType = column.getInputType();
				String options = column.getListOptions();
				String[] arr = null;
				if (StringUtil.isNotEmpty(options)) {
					arr = StringUtil.splitEx(options, "\n");
				}
				String id = FieldPrefix + column.getCode();
				if ("S".equals(inputType)) {// 下拉框
					SelectTag st = new SelectTag();
					st.setId(column.getCode());
					td.InnerHTML = "<input ztype='Select' style='width:180px' verify='" + verify + "' id='" + id
							+ "' name='" + id + "'>";
				} else if ("C".equals(inputType)) {// 多选框
					td.InnerHTML = arr == null ? "" : HtmlUtil.arrayToCheckboxes(id, arr);
				} else if ("D".equals(inputType)) {// 日期框
					td.InnerHTML = "<input ztype='Date' style='width:180px' verify='" + verify + "' id='" + id
							+ "' name='" + id + "'>";
				} else if ("C".equals(inputType)) {// 日期时间选择框
					String html = "<input ztype='Date' style='width:80px' verify='" + verify + "' id='" + id
							+ "_Date' name='" + id + "_Date' onchange='_onDateTimeChange(this)'>";
					html += "<input ztype='Time' style='width:80px' verify='" + verify + "' id='" + id
							+ "_Time' name='" + id + "_Time'  onchange='_onDateTimeChange(this)'>";
					html += "<input type='hidden' style='width:150px' verify='" + verify + "' id='" + id + "' name='" + id + "'>";
					td.InnerHTML = html;
				} else if ("R".equals(inputType)) {// 单选框框
					td.InnerHTML = arr == null ? "" : HtmlUtil.arrayToRadios(id, arr, arr[0]);
				} else if ("A".equals(inputType) || column.getLength() > 1000) {// 文本框
					td.InnerHTML = "<textarea style='width:400px;height:150px' verify='" + verify + "' id='" + id
							+ "' name='" + id + "'></textarea>";
				} else {
					td.InnerHTML = "<input class='inputText' style='width:180px' verify='" + verify + "' id='" + id
							+ "' name='" + id + "'>";
				}
				tr.addTD(td);
				ht.addTR(tr);
			}
			// 增加按钮行
			tr = new HtmlTR();
			td = new HtmlTD();
			td.setColSpan("2");
			td.InnerHTML = "<input class='inputButton' id='_CustomTableSubmit_" + table.getID()
					+ "' type='button' value='保存' onclick='_submitCustomTableForm()' /> &nbsp;&nbsp;&nbsp;&nbsp;"
					+ " <input class='inputButton' id='_CustomTableReset_" + table.getID()
					+ "' type='reset' value='重置' />";
			td.setAlign("center");
			tr.addTD(td);
			ht.addTR(tr);

			sb.append(ht.getOuterHtml());
			content = sb.toString();
		}
		return content;
	}

	public static String getManageFormContent(String ID) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(ID);
		if (!table.fill()) {
			return null;
		}
		return getManageFormContent(table);
	}

	public static String getManageFormContent(ZCCustomTableSchema table) {
		table.setFormContent(null);// 必须设为空
		String content = getEditorFormContent(table);
		content = replaceSelector(content, table.getID(), true);
		StringBuffer sb = new StringBuffer();
		sb.append("<style>\n");
		sb.append(".CustomTableForm table{width:600px;font-size:12px;border-collapse:collapse;}\n");
		sb.append(".CustomTableForm td {padding:4px 8px;text-indent:0;background-color:#FFFFFF;	color:#363636;}\n");
		sb.append(".CustomTableForm td {border-collapse:collapse;border-color:#D5D9D8;"
				+ "border-style:solid;border-width:1px;}\n");
		sb.append("</style>\n");
		String sc = Config.getValue("ServicesContext");
		sc = sc.substring(0, sc.indexOf("Services"));
		sb.append("<link href='" + sc + "Include/Default.css' rel='stylesheet' type='text/css' />\n");
		sb.append("<script src='" + sc + "Framework/Main.js'></script>\n");
		sb.append("<script>\n");
		sb.append("function _submitCustomTableForm(){\n");
		sb.append("	if(Verify.hasError(null,'_CustomTableForm_" + table.getID() + "')){return;}\n");
		sb.append("	var dc = Form.getData('_CustomTableForm_" + table.getID() + "');\n");
		sb.append("	Server.sendRequest(\"com.sinosoft.cms.dataservice.CustomTableAjax.processSubmit\","
				+ "dc,function(response){\n");
		sb.append("		alert(response.Message);\n");
		sb.append("		if(response.Status==1){$('_CustomTableForm_" + table.getID() + "').reset();}\n");
		sb.append("	});\n");
		sb.append("}\n");
		sb.append("</script>\n");
		sb.append("<form id='_CustomTableForm_" + table.getID() + "'>\n");
		sb.append("<input id='_TableID' name='_TableID' type='hidden' value='" + table.getID() + "' />\n");
		sb.append(content);
		sb.append("\n</form>\n");
		content = sb.toString();
		return content;
	}

	public static String getRuntimeFormContent(String ID) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(ID);
		if (!table.fill()) {
			return null;
		}
		return getRuntimeFormContent(table);
	}

	public static String getRuntimeFormContent(ZCCustomTableSchema table) {
		String content = table.getFormContent();
		ZCCustomTableColumnSchema column = new ZCCustomTableColumnSchema();
		column.setTableID(table.getID());
		ZCCustomTableColumnSet cols = column.query();
		if (StringUtil.isEmpty(content)) {
			content = getEditorFormContent(table);
		}
		content = replaceSelector(content, cols, table.getID(), false);
		StringBuffer sb = new StringBuffer();
		sb.append("<style>\n");
		sb.append(".CustomTableForm table{width:600px;font-size:12px;border-collapse:collapse;}\n");
		sb.append(".CustomTableForm td {padding:4px 8px;text-indent:0;background-color:#FFFFFF;	color:#363636;}\n");
		sb.append(".CustomTableForm td {border-collapse:collapse;border-color:#D5D9D8;"
				+ "border-style:solid;border-width:1px;}\n");
		sb.append("</style>\n");
		String sc = Config.getValue("ServicesContext");
		if (!sc.endsWith("/")) {
			sc = sc + "/";
		}
		sb.append("<script>\n");

		try {
			String template = FileUtil.readText(Form.class.getResource("FormScript.template").openStream(), "UTF-8");
			sb.append(template);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		for (int i = 0; i < cols.size(); i++) {
			column = cols.get(i);
			String verify = null;
			if ("Y".equals(column.getIsAutoID())) {// 自动编号不需要填写
				continue;
			}
			if ("Y".equals(column.getIsMandatory())) {
				verify = "NotNull";
			}
			int type = Integer.parseInt(column.getDataType());
			if (type == DataColumn.DATETIME) {
				if (StringUtil.isNotEmpty(verify)) {
					verify = verify + "&&DateTime";
				} else {
					verify = "DateTime";
				}
			} else if (type == DataColumn.INTEGER || type == DataColumn.LONG || type == DataColumn.SMALLINT) {
				if (StringUtil.isNotEmpty(verify)) {
					verify = verify + "&&Int";
				} else {
					verify = "Int";
				}
			} else if (type == DataColumn.FLOAT || type == DataColumn.DECIMAL || type == DataColumn.DOUBLE
					|| type == DataColumn.BIGDECIMAL) {
				if (StringUtil.isNotEmpty(verify)) {
					verify = verify + "&&Number";
				} else {
					verify = "Number";
				}
			}
			sb.append("_addVerifyRule('" + FieldPrefix + column.getCode() + "','" + column.getName() + "','" + verify
					+ "');\n");
		}

		sb.append("\nfunction _submitCustomTableForm(){\n");
		sb.append("\tvar f = document.getElementById('_CustomTableForm_" + table.getID() + "');\n");
		sb.append("\tif(_onFormSubmit(f)){\n");
		sb.append("\t\tf.submit();\n");
		sb.append("\t}\n");
		sb.append("}\n");

		sb.append("</script>\n");
		sb.append("<form method='post' id='_CustomTableForm_" + table.getID() + "' action='" + sc
				+ "FormProcess.jsp'>\n");
		sb.append("<input id='_TableID' name='_TableID' type='hidden' value='" + table.getID() + "' />\n");
		sb.append(content);
		sb.append("\n</form>\n");
		content = sb.toString();
		return content;
	}

	/**
	 * 后台以表单方式浏览数据时展现的表单
	 */
	public static String getViewFormContent(ZCCustomTableSchema table, int index) {
		String sc = Config.getValue("ServicesContext");
		if (!sc.endsWith("/")) {
			sc = sc + "/";
		}
		String content = table.getFormContent();
		ZCCustomTableColumnSchema column = new ZCCustomTableColumnSchema();
		column.setTableID(table.getID());
		ZCCustomTableColumnSet cols = column.query();
		if (StringUtil.isEmpty(content)) {
			content = getEditorFormContent(table);
		}
		DataTable dt = CustomTableUtil.getData(table.getID(), 1, index);
		if (dt.getRowCount() == 0) {
			return "未获取到数据";
		}
		DataRow dr = dt.getDataRow(0);
		content = replaceSelector(content, cols, table.getID(), false);
		StringBuffer sb = new StringBuffer();
		sb.append("<style>\n");
		sb.append(".CustomTableForm table{width:600px;font-size:12px;border-collapse:collapse;}\n");
		sb.append(".CustomTableForm td {padding:4px 8px;text-indent:0;background-color:#FFFFFF;	color:#363636;}\n");
		sb.append(".CustomTableForm td {border-collapse:collapse;border-color:#D5D9D8;"
				+ "border-style:solid;border-width:1px;}\n");
		sb.append("</style>\n");
		sb.append("<form method='post' id='_CustomTableForm_" + table.getID() + "' action='" + sc
				+ "FormProcess.jsp'>\n");
		sb.append("<input id='_TableID' name='_TableID' type='hidden' value='" + table.getID() + "' />\n");
		for (int i = 0; i < cols.size(); i++) {
			column = cols.get(i);
			String id = FieldPrefix + column.getCode();
			if ("Y".equals(column.getIsAutoID())) {// 自动编号未显示
				sb.append("<input id='" + id + "' name='" + id + "' type='hidden' value='"
						+ dr.getString(column.getCode()) + "' />\n");
			}
			if ("Y".equals(column.getIsPrimaryKey())) {// 主键需要保存旧值
				sb.append("<input id='" + id + "_Old' name='" + id + "_Old' type='hidden' value='"
						+ dr.getString(column.getCode()) + "' />\n");
			}
		}
		sb.append(content);
		sb.append("\n</form>\n");
		sb.append("<script>\n");

		sb.append("\n");
		sb.append("Effect.initChildren('_CustomTableForm_" + table.getID() + "');\n");
		for (int i = 0; i < cols.size(); i++) {
			column = cols.get(i);
			String inputType = column.getInputType();
			if ("Y".equals(column.getIsAutoID())) {// 自动编号未显示
				continue;
			}
			if ("C".equals(inputType) || "R".equals(inputType)) {// 单选/多选框
				sb.append("$NS(\"" + FieldPrefix + column.getCode() + "\",\""
						+ StringUtil.javaEncode(dr.getString(column.getCode())) + "\");\n");
			} else {
				sb.append("$S(\"" + FieldPrefix + column.getCode() + "\",\""
						+ StringUtil.javaEncode(dr.getString(column.getCode())) + "\");\n");
			}
		}

		sb.append("\nfunction _submitCustomTableForm(){\n");
		sb.append("\tvar f = document.getElementById('_CustomTableForm_" + table.getID() + "');\n");
		sb.append("\tif(!Verify.hasError(f)){\n");
		sb.append("\t\tf.submit();\n");
		sb.append("\t}\n");
		sb.append("}\n");

		sb.append("</script>\n");
		content = sb.toString();
		content = Pattern.compile("<select", Pattern.CASE_INSENSITIVE).matcher(content).replaceAll(
				"<div ztype='select'");
		content = Pattern.compile("\\/select>", Pattern.CASE_INSENSITIVE).matcher(content).replaceAll("/div>");
		content = Pattern.compile("<option", Pattern.CASE_INSENSITIVE).matcher(content).replaceAll("<span");
		content = Pattern.compile("\\/option>", Pattern.CASE_INSENSITIVE).matcher(content).replaceAll("/span>");
		return content;
	}

	public static String replaceSelector(String content, long tableID, boolean divSelectorFlag) {
		ZCCustomTableColumnSchema column = new ZCCustomTableColumnSchema();
		column.setTableID(tableID);
		ZCCustomTableColumnSet cols = column.query();
		return replaceSelector(content, cols, tableID, divSelectorFlag);
	}

	public static String replaceSelector(String content, ZCCustomTableColumnSet cols, long tableID,
			boolean divSelectorFlag) {
		Document doc = XMLUtil.htmlToXercesDocument(content);
		for (int i = 0; i < cols.size(); i++) {
			ZCCustomTableColumnSchema column = cols.get(i);
			String code = FieldPrefix + column.getCode();
			String inputType = column.getInputType();
			if (inputType.equals("S")) {// 下拉框
				Element element = doc.getElementById(code);
				// 替换成系统div形式的下拉框
				if ("S".equalsIgnoreCase(inputType)) {
					String listOption = column.getListOptions();
					String[] arr = listOption == null ? null : listOption.split("\\n");
					if (divSelectorFlag) {
						String options = HtmlUtil.arrayToOptions(arr, null, true);
						HTMLDivElement div = (HTMLDivElement) doc.createElement("div");
						div.setAttribute("ztype", "select");
						div.setAttribute("id", code);
						div.setAttribute("style", element.getAttribute("style"));
						div.setAttribute("onchange", element.getAttribute("onchange"));
						Text text = doc.createTextNode(options);
						div.appendChild(text);
						element.getParentNode().replaceChild(div, element);
					} else {
						HTMLSelectElement select = (HTMLSelectElement) doc.createElement("select");
						select.setAttribute("id", code);
						select.setAttribute("name", code);
						select.setAttribute("style", "width:125px;" + element.getAttribute("style"));
						select.setAttribute("onchange", element.getAttribute("onchange"));

						HTMLOptionElement option = (HTMLOptionElement) doc.createElement("option");
						option.setValue("");
						select.appendChild(option);

						for (int j = 0; j < arr.length; j++) {
							option = (HTMLOptionElement) doc.createElement("option");
							option.setValue(arr[j]);
							Text text = doc.createTextNode(arr[j]);
							option.appendChild(text);
							select.appendChild(option);
						}
						element.getParentNode().replaceChild(select, element);
					}
				}
			}
		}
		content = XMLUtil.toHTML(doc);
		content = StringUtil.replaceAllIgnoreCase(content, "<html>", "");
		content = StringUtil.replaceAllIgnoreCase(content, "</html>", "");
		content = StringUtil.replaceAllIgnoreCase(content, "<body>", "");
		content = StringUtil.replaceAllIgnoreCase(content, "</body>", "");
		content = content.trim();
		return StringUtil.htmlDecode(content);
	}

	public void reInit() {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID($V("ID"));
		if (!table.fill()) {
			Response.setError("错误的自定义表ID:" + $V("ID"));
			return;
		}
		table.setFormContent(null);
		String html = getEditorFormContent(table);
		$S("HTML", html);
	}

	public void save() {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID($V("ID"));
		if (!table.fill()) {
			Response.setError("错误的自定义表ID:" + $V("ID"));
			return;
		}
		table.setFormContent($V("HTML"));
		if (table.update()) {
			Response.setMessage("保存成功!");
		} else {
			Response.setMessage("保存失败!");
		}
	}

	// 返回DataTable
	public static DataTable getArticleCustomData(String tArticleID) {
		return new QueryBuilder(
				"select code,textvalue,prop1 as Type from zdcolumnvalue where FormCode='Sys_CMS' and ArticleID=?",
				tArticleID).executeDataTable();
	}

	public static DataTable getArticleCustomData(Long tArticleID) {
		return new QueryBuilder(
				"select code,textvalue,prop1 as Type from zdcolumnvalue where FormCode='Sys_CMS' and ArticleID=?",
				tArticleID.longValue()).executeDataTable();
	}
}