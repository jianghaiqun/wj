package cn.com.sinosoft.entity;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class ZdrecordCps extends BaseEntity {

	private static final long serialVersionUID = -4204132177005910325L;
	private String ipAddress;
	private String productId;
	private String memberId;
	private String jumpAddress;
	private String remark;
	private Date operDate;
	private String operTime;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getJumpAddress() {
		return jumpAddress;
	}

	public void setJumpAddress(String jumpAddress) {
		this.jumpAddress = jumpAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getOperDate() {
		return operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

}
