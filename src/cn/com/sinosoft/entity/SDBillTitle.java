/**
 * 
 */
package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 实体类：发票台头信息 （与用户绑定）
 *
 */
@Entity
@Table(name = "sdbilltitle")
public class SDBillTitle extends BaseEntity {

	private static final long serialVersionUID = -7660113834353668149L;

	private String memberId;//所属用户id

	private String name;//姓名

	private String isDefault;//是否默认
	private String modifyUser;//更新者Id
	private String createUser;//创建者Id


	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}


}
