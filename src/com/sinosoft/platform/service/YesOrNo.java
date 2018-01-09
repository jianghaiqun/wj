package com.sinosoft.platform.service;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;

public class YesOrNo extends FixedCodeType {
	public static final String Yes = "Y";
	public static final String No = "N";

	public YesOrNo() {
		super("YesOrNo", "是/否", false, false);
		addFixedItem("Y", "是", getYesIcon());
		addFixedItem("N", "否", getNoIcon());
	}

	public static boolean isYes(String str) {
		return "Y".equals(str);
	}

	public static boolean isNo(String str) {
		return !isYes(str);
	}

	public static String getYesIcon() {
		return Config.getContextPath() + "Icons/yes.gif";
	}

	public static String getNoIcon() {
		return Config.getContextPath() + "Icons/no.gif";
	}

	public static void decodeYesOrNoIcon(DataTable dt, String column) {
		decodeYesOrNoIcon(dt, column, true);
	}

	public static void decodeYesOrNoIcon(DataTable dt, String column, boolean showYesIconOnly) {
		String newColumnName = column + "Icon";
		if (!dt.containsColumn(newColumnName)) {
			dt.insertColumn(newColumnName);
		}
		for (DataRow dr : dt) {
			String v = dr.getString(column);
			if ((isYes(v)) || ("true".equals(v))) {
				dr.set(newColumnName, "<img src='" + getYesIcon() + "' />");
			}
			if ((showYesIconOnly) || ((!isNo(v)) && (!"no".equals(v))))
				continue;
			dr.set(newColumnName, "<img src='" + getNoIcon() + "' />");
		}
	}
}