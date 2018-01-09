package cn.com.sinosoft.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 实体类 - 地区
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTB1172CAE4AF504F014EB1546FF7D255E
 * ============================================================================
 */

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "parent_id"})})
public class Area extends BaseEntity {

	private static final long serialVersionUID = -2158109459123036967L;
	
	public static final String PATH_SEPARATOR = ",";// 树路径分隔符

	private String name;// 地区名称
	private String path;// 树路径
	
	private Area parent;// 上级地区
	private Set<Area> children;// 下级地区
	private String areaValue;// 地区名称
	private String insuranceCompany;//保险公司
	private String productId;//产品编码
   
	/**
	 * @return the areaValue
	 */
	public String getAreaValue() {
		return areaValue;
	}

	/**
	 * @param areaValue the areaValue to set
	 */
	public void setAreaValue(String areaValue) {
		this.areaValue = areaValue;
	}

	/**
	 * @return the insuranceCompany
	 */
	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	/**
	 * @param insuranceCompany the insuranceCompany to set
	 */
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = true)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("name asc")
	public Set<Area> getChildren() {
		return children;
	}

	public void setChildren(Set<Area> children) {
		this.children = children;
	}

	// 获取分类层级（顶级分类：0）
	@Transient
	public Integer getLevel() {
		return path.split(PATH_SEPARATOR).length - 1;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


}