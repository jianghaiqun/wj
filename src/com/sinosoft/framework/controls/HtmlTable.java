package com.sinosoft.framework.controls;

import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlTable extends HtmlElement {

	public static final Pattern PTable = Pattern.compile("^\\s*<table(.*?)>(.*)</table>\\s*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern PInnerTable = Pattern.compile("<table(.*?)>(.*?)</table>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern PTR = Pattern.compile("^\\s*<tr(.*?)>(.*)</tr>\\s*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern PTRPre = Pattern.compile("<tr.*?>.*?</tr>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern PTD = Pattern.compile("^\\s*<(td|th)(.*?)>(.*)</(td|th)>\\s*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern PTDPre = Pattern.compile("<(td|th).*?>.*?</(td|th)>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final String ProtectedTableStart = "<!--_ZVING_INNERTABLE_PROTECTED_";

	private ArrayList pList = null;

	public HtmlTable() {
		ElementType = HtmlElement.TABLE;
		TagName = "table";
	}

	public void addTR(HtmlTR tr) {
		addChild(tr);
	}

	public HtmlTR getTR(int index) {
		return (HtmlTR) Children.get(index);
	}

	public void removeTR(int index) {
		if (index < 0 || index > Children.size()) {
			throw new RuntimeException("错误的索引");
		}
		Children.remove(index);
	}

	public void removeColumn(int index) {
		for (int i = 0; i < Children.size(); i++) {
			HtmlTR tr = getTR(i);
			if (index < tr.Children.size()) {
				tr.removeTD(index);
			}
		}
	}

	public void setWidth(int width) {
		Attributes.put("width", new Integer(width));
	}

	public int getWidth() {
		return ((Integer) Attributes.get("width")).intValue();
	}

	public void setHeight(int height) {
		Attributes.put("height", new Integer(height));
	}

	public int getHeight() {
		return ((Integer) Attributes.get("height")).intValue();
	}

	public void setAlign(String align) {
		Attributes.put("align", align);
	}

	public String getAlign() {
		return (String) Attributes.get("align");
	}

	public void setBgColor(String bgColor) {
		Attributes.put("bgColor", bgColor);
	}

	public String getBgColor() {
		return (String) Attributes.get("bgColor");
	}

	public void setBackgroud(String backgroud) {
		Attributes.put("backgroud", backgroud);
	}

	public String getBackgroud() {
		return (String) Attributes.get("backgroud");
	}

	public void setCellSpacing(String cellSpacing) {
		setAttribute("cellSpacing", cellSpacing);
	}

	public String getCellSpacing() {
		return getAttribute("cellSpacing");
	}

	public void setCellPadding(String cellPadding) {
		setAttribute("cellPadding", cellPadding);
	}

	public String getCellPadding() {
		return getAttribute("cellPadding");
	}

	public void parseHtml(String html) throws Exception {
		Matcher m = PTable.matcher(html);
		if (!m.find()) {
			throw new Exception("Table解析html时发生错误");
		}
		String attrs = m.group(1);
		String trs = m.group(2).trim();

		// 清空原有属性和子元素
		Attributes.clear();
		Children.clear();

		Attributes = parseAttr(attrs);

		// 保护单元格里的Table
		m = PInnerTable.matcher(trs);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			if (pList == null) {
				pList = new ArrayList();
			}
			pList.add(m.group(0));
			lastEndIndex = m.end();
		}
		if (pList != null) {
			for (int i = 0; i < pList.size(); i++) {
				trs = StringUtil.replaceEx(trs, pList.get(i).toString(), HtmlTable.ProtectedTableStart + i + "-->");
			}
		}

		// 解析行
		m = PTRPre.matcher(trs);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String t = trs.substring(m.start(), m.end());
			HtmlTR tr = new HtmlTR(this);
			tr.parseHtml(t);
			addTR(tr);
			lastEndIndex = m.end();
		}
	}

	public String restoreInnerTable(String html) {
		if (pList == null || pList.size() == 0) {
			return html;
		}
		String[] arr = StringUtil.splitEx(html, ProtectedTableStart);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (StringUtil.isNotEmpty(arr[i])) {
				if (i != 0) {
					int index = Integer.parseInt(arr[i].substring(0, arr[i].indexOf("-")));
					sb.append(pList.get(index).toString());
					arr[i] = arr[i].substring(arr[i].indexOf(">") + 1);
				}
				sb.append(arr[i]);
			}
		}
		return sb.toString();
	}

	public String getOuterHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<");
		sb.append(TagName);
		Object[] ks = Attributes.keyArray();
		Object[] vs = Attributes.valueArray();
		for (int i = 0; i < Attributes.size(); i++) {
			if (vs[i] != null) {
				sb.append(" ");
				sb.append(ks[i]);
				sb.append("=\"");
				sb.append(vs[i]);
				sb.append("\"");
			}
		}

		sb.append(">\n");
		if (this.getChildren().size() > 0 && getTR(0).getChildren().size() > 0) {
			if (getTR(0).getTD(0).isHead()) {// 需要添加thead
				sb.append("<thead>\n");
			}
			sb.append(getTR(0).getOuterHtml());
			if (getTR(0).getTD(0).isHead()) {// 需要添加thead
				sb.append("</thead>\n");
				if (Children.size() > 1) {
					sb.append("<tbody>\n");
				}
			}
			for (int i = 1; i < Children.size(); i++) {
				sb.append(((HtmlElement) Children.get(i)).getOuterHtml());
			}
			if (getTR(0).getTD(0).isHead() && Children.size() > 1) {// 最后添加tbody
				sb.append("</tbody>\n");
			}
		}
		sb.append("</");
		sb.append(TagName);
		sb.append(">");
		return sb.toString();
	}

	public static void test() {
		HtmlTable table = new HtmlTable();
		try {
			String html = FileUtil.readText("G:/Test.txt");
			table.parseHtml(html);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
