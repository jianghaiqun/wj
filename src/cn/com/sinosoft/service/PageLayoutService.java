package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.PageLayout;

public interface PageLayoutService extends BaseService<PageLayout,String> {
	/**
	 * 根据保险公司及险种小类编码查询返回页面
	 */
	public String getReturnPage(String comCode,String riskCode);
	/**
	 * 根据保险公司及特殊产品id查询返回页面
	 */
	public String getReturnPageByProduct(String productId , String comCode);
	/**
	 * 根据保险公司查询返回页面
	 */
	public String getReturnPageByCompany(String comCode);
}
