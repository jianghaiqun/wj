package cn.com.sinosoft.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/**
 * 实体类 - 角色
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT310BB6F1DF8D1724C0490E718AA13D4D
 * ============================================================================
 */

@Entity
public class Role extends BaseEntity {

	private static final long serialVersionUID = -6614052029623997372L;

	private String name;// 角色名称
	private String value;// 角色标识
	private Boolean isSystem;// 是否为系统内置角色
	private String description;// 描述
	
	private Set<Admin> adminSet;// 管理员
	private Set<Cresource> resourceSet;// 资源

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = false, unique = true)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(nullable = false, updatable = false)
	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	@Column(length = 5000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roleSet")
	public Set<Admin> getAdminSet() {
		return adminSet;
	}

	public void setAdminSet(Set<Admin> adminSet) {
		this.adminSet = adminSet;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	public Set<Cresource> getResourceSet() {
		return resourceSet;
	}

	public void setResourceSet(Set<Cresource> resourceSet) {
		this.resourceSet = resourceSet;
	}

}