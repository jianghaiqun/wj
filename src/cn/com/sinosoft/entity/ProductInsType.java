package cn.com.sinosoft.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OrderBy;

/**
 * 实体类 - 商品类型
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT9205E70ECB8E9EA3CF1669CBB8C6FCAF
 * ============================================================================
 */

@Entity
public class ProductInsType extends BaseEntity {

	private static final long serialVersionUID = -6173231303962800415L;

	private String name;// 类型名称
	
	private List<ProductInsAttribute> productInsAttributeList;// 商品属性
	private Set<Product> productSet;// 商品

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productInsType")
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy(clause = "orderList asc")
	public List<ProductInsAttribute> getProductInsAttributeList() {
		return productInsAttributeList;
	}

	public void setProductInsAttributeList(List<ProductInsAttribute> productInsAttributeList) {
		this.productInsAttributeList = productInsAttributeList;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productInsType")
	public Set<Product> getProductSet() {
		return productSet;
	}

	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}
	
	// 获得已启用的商品属性
	@Transient
	public List<ProductInsAttribute> getEnabledProductInsAttributeList() {
		if (productInsAttributeList == null) {
			return null;
		}
		List<ProductInsAttribute> enabledProductInsAttributeList = new ArrayList<ProductInsAttribute>();
		for (ProductInsAttribute productInsAttribute : productInsAttributeList) {
			if (productInsAttribute.getIsEnabled()) {
				enabledProductInsAttributeList.add(productInsAttribute);
			}
		}
		return enabledProductInsAttributeList;
	}

}