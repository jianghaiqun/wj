package com.sinosoft.platform;

import java.util.Date;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.license.LicenseInfo;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;

public class License extends Ajax {
	public void getRequest() {
		String customer = $V("Customer");
		$S("Request", LicenseInfo.getLicenseRequest(customer));
	}

	public void saveLicense() {
		String license = $V("License");
		if (LicenseInfo.verifyLicense(license)) {
			FileUtil.writeText(Config.getContextRealPath() + "WEB-INF/classes/license.dat", license);
			LicenseInfo.update();
			Response.setMessage("保存成功!");
		} else {
			Response.setError("无效的许可证!");
		}
	}

	public static boolean needWarning() {
		Date endDate = LicenseInfo.getEndDate();
		if (DateUtil.addMonth(endDate, -3).getTime() < System.currentTimeMillis()) {
			return true;
		}
		return false;
	}
}
