package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.ProductInsAttribute;
import cn.com.sinosoft.entity.ProductInsType;


/**
 * Dao接口 - 商品属性
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT4D051F4491A9917665E897135777FB7C
 * ============================================================================
 */

public interface ProductInsAttributeDao extends BaseDao<ProductInsAttribute, String> {
	
	/**
	 * 获取已启用的商品属性.
	 * 
	 * @return 已启用的商品属性集合.
	 */
	public List<ProductInsAttribute> getEnabledProductInsAttributeList();
	
	/**
	 * 根据商品类型获取已启用的商品属性.
	 * 
	 * @return 已启用的商品属性集合.
	 */
	public List<ProductInsAttribute> getEnabledProductInsAttributeList(ProductInsType productInsType);
	
	/**
	 * 根据商品类型、商品名称查找，若不存在则返回null
	 * 
	 * @param productInsType
	 *            商品类型
	 * 
	 * @param name
	 *            商品属性名称 
	 * 
	 */
	public ProductInsAttribute getProductInsAttribute(ProductInsType productInsType, String name);

}