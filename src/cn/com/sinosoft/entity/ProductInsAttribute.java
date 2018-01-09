package cn.com.sinosoft.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;

/**
 * 实体类 - 商品属性
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT683FD9A1F4936F9B8563BDB221B35259
 * ============================================================================
 */

@Entity
@Table(
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"name", "productInsType_id"})
	}
)
public class ProductInsAttribute extends BaseEntity {

	private static final long serialVersionUID = 2989102568413246571L;
	
	// 属性类型：
	public enum AttributeInsType {
		text, number, alphaint, select, checkbox, date
	}

	private String name;// 属性名称
	private AttributeInsType attributeInsType;// 属性类型
	private Boolean isRequired;// 是否必填
	private Boolean isEnabled;// 是否启用
	private Integer orderList;// 排序
	private String attributeOptionStore;// 可选项储存
	
	private ProductInsType productInsType;// 商品类型

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated
	@Column(nullable = false)
	public AttributeInsType getAttributeInsType() {
		return attributeInsType;
	}

	public void setAttributeInsType(AttributeInsType attributeInsType) {
		this.attributeInsType = attributeInsType;
	}

	@Column(nullable = false)
	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	@Column(nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(nullable = false)
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	
	public String getAttributeOptionStore() {
		return attributeOptionStore;
	}

	public void setAttributeOptionStore(String attributeOptionStore) {
		this.attributeOptionStore = attributeOptionStore;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public ProductInsType getProductInsType() {
		return productInsType;
	}

	public void setProductInsType(ProductInsType productInsType) {
		this.productInsType = productInsType;
	}

	// 获取可选项
	@SuppressWarnings("unchecked")
	@Transient
	public List<String> getAttributeOptionList() {
		if (StringUtils.isEmpty(attributeOptionStore)) {
			return null;
		}
		JSONArray jsonArray = JSONArray.fromObject(attributeOptionStore);
		return (List<String>) JSONSerializer.toJava(jsonArray);
	}
	
	// 设置可选项
	@Transient
	public void setAttributeOptionList(List<String> attributeOptionList) {
		if (attributeOptionList == null || attributeOptionList.size() == 0) {
			attributeOptionStore = null;
			return;
		}
		JSONArray jsonArray = JSONArray.fromObject(attributeOptionList);
		attributeOptionStore = jsonArray.toString();
	}

}