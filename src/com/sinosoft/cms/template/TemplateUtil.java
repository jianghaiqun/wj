package com.sinosoft.cms.template;

import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;

public class TemplateUtil {
	public static String getCatalogNames(String innerCode) {
		return getCatalogNames(innerCode, "/");
	}

	public static String getCatalogNames(String innerCode, String spliter) {
		return getCatalogNames(innerCode, spliter, true, true);
	}

	public static String getCatalogNames(String innerCode, String spliter, boolean descFlag, boolean siteNameFlag) {
		StringBuffer sb = new StringBuffer();
		if (descFlag) {
			while (innerCode.length() > 0) {
				sb.append(CatalogUtil.getNameByInnerCode(innerCode));
				sb.append(spliter);
				innerCode = innerCode.substring(0, innerCode.length() - 6);
			}
		} else {
			int length = 6;
			while (length <= innerCode.length()) {
				sb.append(CatalogUtil.getNameByInnerCode(innerCode.substring(0, length)));
				sb.append(spliter);
				length += 6;
			}
		}
		if (siteNameFlag) {
			sb.append(SiteUtil.getName(CatalogUtil.getSiteIDByInnerCode(innerCode)));
			return sb.toString();
		} else {
			return sb.substring(0, sb.length() - spliter.length());
		}
	}
}
