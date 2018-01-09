package com.sinosoft.cmcore.tag;

import java.util.Date;

import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

public class RootModifierHandler implements ModifierHandler {
	public Object deal(Object value, Mapx map) {
		if (value == null) {
			return "";
		}
		if (map == null) {
			if (value instanceof Date) {
				return DateUtil.toDateTimeString((Date) value);
			}
			return value;
		} else {
			String format = map.getString("Format");
			String length = map.getString("Length");
			if (StringUtil.isNotEmpty(format) && value instanceof Date) {
				return DateUtil.toString((Date) value, format);
			}
			if (StringUtil.isNotEmpty(length) && StringUtil.isDigit(length)) {
				return StringUtil.subStringEx(value.toString(), Integer.parseInt(length));
			}
		}
		return "";
	}
}
