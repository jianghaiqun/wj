package com.sinosoft.platform.meta;

import com.sinosoft.platform.service.FixedCodeType;

public class ControlType extends FixedCodeType {
	public static final String Text = "Text";
	public static final String Selector = "Select";
	public static final String TextArea = "TextArea";
	public static final String Radio = "Radio";
	public static final String Checkbox = "Checkbox";
	public static final String DateSelector = "Date";
	public static final String DateTimeSelector = "DateTime";
	public static final String TimeSelector = "Time";

	public ControlType() {
		super("ControlType", "控件类型", true, false);
		addFixedItem("Checkbox", "多选框", null);
		addFixedItem("Date", "日期选择框", null);
		addFixedItem("DateTime", "日期时间选择框", null);
		addFixedItem("Radio", "单选框", null);
		addFixedItem("Select", "下拉框", null);
		addFixedItem("Text", "文本框", null);
		addFixedItem("TextArea", "多行文本框", null);
	}

	public boolean isText(String type) {
		return "Text".equals(type);
	}

	public boolean isSelector(String type) {
		return "Select".equals(type);
	}

	public boolean isTextArea(String type) {
		return "TextArea".equals(type);
	}

	public boolean isRadio(String type) {
		return "Radio".equals(type);
	}

	public boolean isCheckbox(String type) {
		return "Checkbox".equals(type);
	}

	public boolean isDateSelector(String type) {
		return "Date".equals(type);
	}

	public boolean isDateTimeSelector(String type) {
		return "DateTime".equals(type);
	}
}