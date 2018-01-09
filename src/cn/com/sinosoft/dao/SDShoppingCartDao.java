package cn.com.sinosoft.dao;

import java.util.Map;

import cn.com.sinosoft.entity.SDShoppingCart;

import com.sinosoft.framework.data.DataTable;

public interface SDShoppingCartDao extends BaseDao<SDShoppingCart,String>{

	/**
	 * 
	 * 根据sql查询购物车中商品数量
	 */
	public DataTable getShopCartNo(String sql);

	/**
	 * 
	 * 根据被保人证件类型和证件号码查询购物车中订单是否已经给该被保人投过保
	 */
	public DataTable getInfByIDAndType(String sql, String idtype, String idNo);

	/**
	 * 
	 * 根据保险公司编码返回是否需要核保标记
	 */
	public String getuwCheckFlagByComCode(String comCode);

	/**
	 * 
	 * 根据订单号查询被保人人信息，去掉重复
	 */
	public DataTable getInsuredByOrderSn(String orderSn);

	/**
	 * 
	 * 根据sql删除购物车表中的数据
	 */
	public void deleteShopCartBySql(String deleteSql);

	/**
	 * 根据产品编码查询产品logo
	 */
	public Map<String,String> getProductInFo(String productId);

	/**
	 * 根据订单号查询份数
	 */
	public DataTable getInsuredNoByOrderSn(String orderSn);

}
