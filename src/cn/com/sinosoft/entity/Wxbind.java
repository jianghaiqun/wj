package cn.com.sinosoft.entity;

import javax.persistence.Entity;

/** 
 * 实体类 - 微信绑定
 * ============================================================================
 *  
 *
 *   
 *
 *  
 *
 *  KEY:SINOSOFT0F83FA217E05DC57A1792709497F7841
 * ============================================================================
 */ 

@Entity
public class Wxbind extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5450741279663287425L;
	
	private String memberId;
	private String memAccount;//网站会员账号
	private String openId;//微信唯一标识符
	private String remark;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemAccount() {
		return memAccount;
	}
	public void setMemAccount(String memAccount) {
		this.memAccount = memAccount;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}