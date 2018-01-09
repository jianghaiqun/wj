package com.sinosoft;

import com.sinosoft.framework.license.IProduct;

public class Product implements IProduct {
	public String getAppCode() {
		return "CMS";
	}

	public String getAppName() {
		return "中科软科技股份有限公司";
	}

	public float getMainVersion() {
		return 1.3f;
	}

	public float getMinorVersion() {
		return 1f;
	}

}
