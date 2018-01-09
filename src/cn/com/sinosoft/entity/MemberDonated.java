package cn.com.sinosoft.entity;

import javax.persistence.Entity;


/**
 * 实体类 - 会员获赠保险信息表
 * ============================================================================
 * KEY:SINOSOFT112C95F35897FDDCA9E38A76E6DE9B8C
 * ============================================================================
 */

@Entity
public class MemberDonated extends BaseEntity {

	private static final long serialVersionUID = -8541323033439515148L;

	private String memberId;// 会员ID
	private String productId;// 产品ID
	private String orderId;// 已获赠订单号信息
	private String identityType;// 证件类型
	private String identityId;// 证件号码
	private String mobile;// 手机号
	private String email;// 邮箱
	private String remark;// 备注
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}