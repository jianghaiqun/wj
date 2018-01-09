package com.sinosoft.platform.meta;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.service.FixedCodeType;

public class DataType extends FixedCodeType {
	public static final String ShortText = "ShortText";
	public static final String MediumText = "MediumText";
	public static final String LargeText = "LargeText";
	public static final String ClobText = "ClobText";
	public static final String Long = "Long";
	public static final String Double = "Double";
	public static final String Datetime = "Datetime";
	public String DataType;
	public int Count;
	private static Mapx<String, Integer> types = null;

	public DataType() {
		super("DataType", "数据类型", false, false);
		addFixedItem("ClobText", "字符串(不限长度)", null);
		addFixedItem("Datetime", "日期时间", null);
		addFixedItem("Double", "双字节浮点", null);
		addFixedItem("LargeText", "字符串(2000)", null);
		addFixedItem("Long", "长整型", null);
		addFixedItem("MediumText", "字符串(200)", null);
		addFixedItem("ShortText", "字符串(50)", null);
	}

	public static boolean isClobText(String type) {
		return "ClobText".equals(type);
	}

	public static boolean isDate(String type) {
		return "Datetime".equals(type);
	}

	public static boolean isDouble(String type) {
		return "Double".equals(type);
	}

	public static boolean isLargeText(String type) {
		return "LargeText".equals(type);
	}

	public static boolean isLong(String type) {
		return "Long".equals(type);
	}

	public static boolean isMediumText(String type) {
		return "MediumText".equals(type);
	}

	public static boolean isShortText(String type) {
		return "ShortText".equals(type);
	}

	public static Mapx<String, Integer> getFieldTypes() {
		if (types == null) {
			types = new Mapx();
			types.put("ShortText", Integer.valueOf(25));
			types.put("MediumText", Integer.valueOf(25));
			types.put("LargeText", Integer.valueOf(4));
			types.put("ClobText", Integer.valueOf(1));
			types.put("Long", Integer.valueOf(10));
			types.put("Double", Integer.valueOf(10));
			types.put("Datetime", Integer.valueOf(10));
		}
		return types;
	}
}