package cn.com.sinosoft.entity;

import javax.persistence.Entity;

@Entity
public class LotteryAct extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6490411123428005759L;
	private String orderNo;
	private String realName;
	private String mobile;
	private String eMail;
	private String adress;
	private String memberId;
	private String actCode;
	private String bussNo;
	private String type;
	private String awards;
	private String recordType;
	private String useType;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getActCode() {
		return actCode;
	}
	public void setActCode(String actCode) {
		this.actCode = actCode;
	}
	public String getBussNo() {
		return bussNo;
	}
	public void setBussNo(String bussNo) {
		this.bussNo = bussNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAwards() {
		return awards;
	}
	public void setAwards(String awards) {
		this.awards = awards;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	
	
	
	
	
	
	
	

}
