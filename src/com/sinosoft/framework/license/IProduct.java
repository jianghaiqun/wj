package com.sinosoft.framework.license;

 
public interface IProduct {
	/**
	 * @return 返回产品代码
	 */
	public String getAppCode();

	/**
	 * @return 返回产品名称
	 */
	public String getAppName();

	/**
	 * @return 返回产品主版本号
	 */
	public float getMainVersion();

	/**
	 * @return 返回产品次版本号
	 */
	public float getMinorVersion();
}
