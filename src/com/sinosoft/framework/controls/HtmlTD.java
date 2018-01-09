package com.sinosoft.framework.controls;

import java.util.regex.Matcher;

/**
 * 注意：需兼容TH/TD
 * 
 */
public class HtmlTD extends HtmlElement {
	private HtmlTR parent;

	public HtmlTD() {
		this(null);
	}

	public HtmlTD(HtmlTR parent) {
		this.parent = parent;
		ElementType = HtmlElement.TD;
		TagName = "td";
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

	public String getVAlign() {
		return (String) Attributes.get("vAlign");
	}

	public void setVAlign(String vAlign) {
		Attributes.put("vAlign", vAlign);
	}

	public void setColSpan(String colSpan) {
		setAttribute("colSpan", colSpan);
	}

	public String getColSpan() {
		return getAttribute("colSpan");
	}

	public void setRowSpan(String rowSpan) {
		setAttribute("rowSpan", rowSpan);
	}

	public String getRowSpan() {
		return getAttribute("rowSpan");
	}

	public int getCellIndex() {
		for (int i = 0; i < ParentElement.Children.size(); i++) {
			if (ParentElement.Children.get(i).equals(this)) {
				return i;
			}
		}
		throw new RuntimeException("得到RowIndex时发生错误");
	}

	public void parseHtml(String html) throws Exception {
		Matcher m = HtmlTable.PTD.matcher(html);
		if (!m.find()) {
			throw new Exception(TagName + "解析html时发生错误");
		}
		TagName = m.group(1);
		String attrs = m.group(2);

		// 清空原有属性和子元素
		Attributes.clear();
		Children.clear();

		Attributes = parseAttr(attrs);
		InnerHTML = m.group(3).trim();

		if (parent != null) {
			String newHtml = parent.restoreInnerTable(this.InnerHTML);
			if (newHtml.equals(this.InnerHTML)) {
				if (parent.getParent() != null) {
					setInnerHTML(parent.getParent().restoreInnerTable(
							this.InnerHTML));
				}
			} else {
				setInnerHTML(newHtml);
			}
		}
	}

	public HtmlTR getParent() {
		return parent;
	}

	public boolean isHead() {
		return TagName.equalsIgnoreCase("th");
	}

	public void setHead(boolean isHead) {
		if (isHead) {
			TagName = "th";
		} else {
			TagName = "tr";
		}
	}
}
