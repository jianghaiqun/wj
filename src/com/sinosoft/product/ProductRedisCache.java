/**
 * Project Name:wj
 * File Name:ProductRedisCache.java
 * Package Name:com.sinosoft.product
 * Date:2016年3月24日上午9:48:08
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.product; 

import java.util.ArrayList;
import java.util.List;

import com.finance.util.JedisCommonUtil;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;

import cn.com.sinosoft.util.RedisConsts;

/**
 * ClassName:ProductRedisCache <br/>
 * Function:产品redis缓存管理类 <br/>
 * Date:2016年3月24日 上午9:48:08 <br/>
 *
 * @author:ciyuanshuang
 */
public class ProductRedisCache extends Page {
	//删除产品
	private static final int DELE_PRODUCT = 1;
	//删除投保录入页
	private static final int DELE_PRODUCT_PAGE = 4;
	//保险公司和产品
	private static final int DELE_COMPANY_PRODUCT = 2;
	//全部产品和公司
	private static final int DELE_ALL = 3;
	//删除产品详细页
	private static final int DELE_DETAIL_PAGE = 5;
	//销量
	private static final int DELE_SALESVLUME = 6;
	//评论数
	private static final int DELE_COMMENTCOUNT = 7;
	//现金价值
	private static final int DELE_CASHVALUE = 8;
	
	/**
	 * init:页面初始化. <br/>
	 * @author ciyuanshuang
	 * @param params
	 * @return
	 */
	public static Mapx<String,Object> init(Mapx<String,Object> params) {
		params.put("CompanyIDList",HtmlUtil.codeToOptions("SupplierCode", true));
		return params;
	}
	/**
	 * deleRedisCache:清除缓存. <br/>
	 * @author ciyuanshuang
	 * @param dga
	 */
	public void deleRedisCache() {
		boolean removed = false;
		// 删除类型
		int deleType = this.Request.getInt("DelType");
		// 产品编码
		String productIDHI = this.Request.getString("ProductIDHI");
		// 保险公司
		String companyIDHI = this.Request.getString("CompanyIDHI");
		
		// 删除指定产品
		if (deleType == DELE_PRODUCT) {
			removed = delePRODUCT(productIDHI);
		}
		// 删除投保录入页初始化信息
		else if (deleType == DELE_PRODUCT_PAGE) {
			String key = productIDHI + Constant.BUYINIT_ALIAS;
			removed = JedisCommonUtil.remove(0, new String[] { key });
		}
		// 删除某个保险公司下产品的缓存
		else if (deleType == DELE_COMPANY_PRODUCT) {
			removed = deleCompanyAndProductRedisCache(companyIDHI);
		}
		// 删除详细页
		else if (deleType == DELE_DETAIL_PAGE) {
			String key = productIDHI + Constant.PRODUCTDETAIL_ALIAS;
			removed = JedisCommonUtil.remove(0, new String[] { key });
		}
		// 全部删除（所有公司所有产品）
		else if (deleType == DELE_ALL) {
			removed = deleAllCompanyRedisCache();
		}
		// 删除产品销量
		else if (deleType == DELE_SALESVLUME) {
			String key = RedisConsts.KEY_PREFIX_PRODUCTATTR + productIDHI;
			removed = JedisCommonUtil.hdel(0, key, RedisConsts.MAPKEY_PRODUCTATTR_SALESVLUME);
		}
		// 删除产品评论数
		else if (deleType == DELE_COMMENTCOUNT) {
			String key = RedisConsts.KEY_PREFIX_PRODUCTATTR + productIDHI;
			removed = JedisCommonUtil.hdel(0, key, RedisConsts.MAPKEY_PRODUCTATTR_COMMENTCOUNT);
		}
		// 删除产品现金价值
		else if (deleType == DELE_CASHVALUE) {
			String key = "PRODUCT_CASHVALUE_" + productIDHI;
			removed = JedisCommonUtil.remove(0, new String[] { key });
		}

		if (removed) {
			this.Response.setMessage("清除缓存成功");
		} else {
			this.Response.setError("清除缓存出错");
		}
	}
	
	/**
	 * delePRODUCT:删除指定产品. <br/>
	 * @author guanyulong
	 * @param productIDHI
	 */
	public boolean delePRODUCT(String productIDHI){
		List<String> keyCodes = new ArrayList<String>(2);
		keyCodes.add(productIDHI+Constant.PRODUCTINFO_ALIAS);
		keyCodes.add(productIDHI + Constant.PRODUCTDETAIL_ALIAS);
		keyCodes.add(RedisConsts.KEY_PREFIX_PRODUCTATTR + productIDHI);//产品销量、评论数量
		keyCodes.add("PRODUCT_CASHVALUE_" + productIDHI);// 产品现金价值
		return JedisCommonUtil.remove(0, (String[])keyCodes.toArray(new String[2]));
	}
	
	/**
	 * deleCompanyRedisCache:根据保险公司删除对应产品的缓存. <br/>
	 * @author ciyuanshuang
	 * @param companyId
	 */
	public boolean deleCompanyAndProductRedisCache(String companyId){
		String findProduct = "SELECT productid FROM sdproduct a WHERE left(productid,4) = '"+companyId+"'";
		QueryBuilder queryBuilder = new QueryBuilder(findProduct);
		DataTable dataTable = queryBuilder.executeDataTable();
		int rowsCount = dataTable.getRowCount();
		//多一个留给保险公司
		List<String> keyCodes = new ArrayList<String>(rowsCount*2+1);
		for(int i=0;i<rowsCount;i++){
			DataRow row = dataTable.get(i);
			keyCodes.add(row.getString("productid")+Constant.PRODUCTINFO_ALIAS);
			keyCodes.add(row.getString("productid")+Constant.BUYINIT_ALIAS);
			keyCodes.add(row.getString("productid")+Constant.PRODUCTDETAIL_ALIAS);
			keyCodes.add(RedisConsts.KEY_PREFIX_PRODUCTATTR + row.getString("productid"));//产品销量、评论数量
			keyCodes.add("PRODUCT_CASHVALUE_" + row.getString("productid"));// 产品现金价值
		}
		keyCodes.add(companyId+Constant.COMPANY_ALIAS);
		//删除了多少个
		return JedisCommonUtil.remove(0, (String[])keyCodes.toArray(new String[rowsCount*2+1]));
	}
	/**
	 * deleAllCompanyRedisCache:删除所有产品和公司的缓存. <br/>
	 * @author ciyuanshuang
	 * @return
	 */
	public boolean deleAllCompanyRedisCache(){
		String findProduct = "SELECT productid FROM sdproduct";
		String findCompany = "SELECT DISTINCT left(productid,4) insurancecompanysn FROM sdproduct ";
		QueryBuilder queryBuilder = new QueryBuilder(findProduct);
		DataTable dataTable = queryBuilder.executeDataTable();
		QueryBuilder queryComBuilder = new QueryBuilder(findCompany);
		DataTable comDataTable = queryComBuilder.executeDataTable();
		int rowsCount = dataTable.getRowCount();
		int comRowsCount = comDataTable.getRowCount();
		//产品+公司+产品页
		int length = rowsCount+comRowsCount+rowsCount;
		//所有产品和所有保险公司
		List<String> keyCodes = new ArrayList<String>(length);
		for(int i=0;i<rowsCount;i++){
			DataRow row = dataTable.get(i);
			keyCodes.add(row.getString("productid")+Constant.PRODUCTINFO_ALIAS);
			keyCodes.add(row.getString("productid")+Constant.BUYINIT_ALIAS);
			keyCodes.add(row.getString("productid")+Constant.PRODUCTDETAIL_ALIAS);
			keyCodes.add(RedisConsts.KEY_PREFIX_PRODUCTATTR + row.getString("productid"));//产品销量、评论数量
			keyCodes.add("PRODUCT_CASHVALUE_" + row.getString("productid"));// 产品现金价值
		}
		for(int i=0;i<comRowsCount;i++){
			DataRow row = comDataTable.get(i);
			keyCodes.add(row.getString("insurancecompanysn")+Constant.COMPANY_ALIAS);
		}
		//删除了多少个
		return JedisCommonUtil.remove(0, (String[])keyCodes.toArray(new String[length]));
	}
	
}
