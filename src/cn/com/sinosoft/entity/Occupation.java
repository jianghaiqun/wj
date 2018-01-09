package cn.com.sinosoft.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 
 * @author liuxin 职业维护实体类
 */
@Entity
public class Occupation extends BaseEntity {

	private static final long serialVersionUID = -6615403700437119851L;
	public static final String PATH_SEPARATOR = ",";// 树路径分隔符
	private String code;// 编码
	private String name;// 名字
	private String path;// 树路径
	private Occupation parent;// 上级职业
	private String grade;//职业等级
	private Set<Occupation> children;// 下级职业
	private String insuranceCompany;//保险公司
	private String productId;//产品编码
	
	public String getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	@Column(nullable = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Occupation getParent() {
		return parent;
	}

	public void setParent(Occupation parent) {
		this.parent = parent;
	}
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("name asc")
	public Set<Occupation> getChildren() {
		return children;
	}

	public void setChildren(Set<Occupation> children) {
		this.children = children;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

}
