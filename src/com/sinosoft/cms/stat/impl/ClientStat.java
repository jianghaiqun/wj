package com.sinosoft.cms.stat.impl;

import java.util.regex.Pattern;

import com.sinosoft.cms.stat.AbstractStat;
import com.sinosoft.cms.stat.Visit;
import com.sinosoft.cms.stat.VisitCount;
import com.sinosoft.framework.utility.StringUtil;

/**
 */
public class ClientStat extends AbstractStat {
	private static final String[] avgSubTypes = new String[] { "StickTime" };

	private static final String Type = "Client";

	public String getStatType() {
		return Type;
	}

	public String[] getAverageSubTypes() {
		return avgSubTypes;
	}

	Pattern srPattern = Pattern.compile("\\d{2,4}x\\d{2,4}", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public void deal(Visit v) {
		if ("Unload".equals(v.Event)) {
			return;
		} else {
			if (StringUtil.isEmpty(v.FlashVersion)) {
				v.FlashVersion = "其他";
			} else {
				v.FlashVersion = v.FlashVersion.replaceAll("(\\%20)+", " ");
				if (v.FlashVersion.indexOf(" ") > 0) {
					v.FlashVersion = v.FlashVersion.substring(0, v.FlashVersion.indexOf(" "));
				}
			}
			String cd = v.ColorDepth;
			if (StringUtil.isEmpty(cd)) {
				cd = "其他";
			} else {
				cd = cd.replaceAll("\\D", "");
				if (cd.equals("8") || cd.equals("16") || cd.equals("24") || cd.equals("32")) {
					cd = cd + "-bit";
				} else {
					cd = "其他";
				}
			}
			v.ColorDepth = cd;

			String sr = v.Screen;
			if (StringUtil.isNotEmpty(sr)) {
				if (sr.indexOf(",") > 0) {
					sr = sr.substring(0, sr.indexOf(","));
				}
				if (!srPattern.matcher(sr).matches()) {
					sr = "其他";
				}
			} else {
				sr = "其他";
			}

			VisitCount.getInstance().add(v.SiteID, Type, "ColorDepth", v.ColorDepth);
			VisitCount.getInstance().add(v.SiteID, Type, "OS", v.OS);
			VisitCount.getInstance().add(v.SiteID, Type, "Browser", v.Browser);
			VisitCount.getInstance().add(v.SiteID, Type, "Language", v.Language);
			VisitCount.getInstance().add(v.SiteID, Type, "FlashVersion", v.FlashVersion);
			VisitCount.getInstance().add(v.SiteID, Type, "JavaEnabled", String.valueOf(v.JavaEnabled));
			VisitCount.getInstance().add(v.SiteID, Type, "CookieEnabled", String.valueOf(v.CookieEnabled));
			VisitCount.getInstance().add(v.SiteID, Type, "Screen", sr);
		}
	}
}
