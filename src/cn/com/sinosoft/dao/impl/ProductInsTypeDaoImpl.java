package cn.com.sinosoft.dao.impl;

import java.util.Set;


import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.ProductInsTypeDao;
import cn.com.sinosoft.entity.Product;
import cn.com.sinosoft.entity.ProductInsType;

/**
 * Dao实现类 - 商品类型
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT98FDAF6620D898936043F11DC7A029CC
 * ============================================================================
 */

@Repository
public class ProductInsTypeDaoImpl extends BaseDaoImpl<ProductInsType, String> implements ProductInsTypeDao {

	// 关联处理
	@Override
	public void delete(ProductInsType productInsType) {
		Set<Product> productSet = productInsType.getProductSet();
		for (Product product : productSet) {
			product.setProductInsType(null);
			product.setProductAttributeMap(null);
		}
		super.delete(productInsType);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		ProductInsType productInsType = super.load(id);
		this.delete(productInsType);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			ProductInsType productInsType = super.load(id);
			this.delete(productInsType);
		}
	}

}