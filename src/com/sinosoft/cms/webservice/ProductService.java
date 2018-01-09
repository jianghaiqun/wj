package com.sinosoft.cms.webservice;

public interface ProductService {

	/**
	 * 上架产品
	 * 
	 * @param productID
	 *            产品ID
	 * @return
	 */
	public boolean publishProduct(String productID);

	/**
	 * 下架产品
	 * 
	 * @param productID
	 *            产品ID
	 * @return
	 */
	public boolean downProduct(String productID);
}
