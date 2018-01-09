package cn.com.sinosoft.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity 
public class Partnerinfo  implements Serializable{

	private String id;

	private String partnerId;

	private String partnerName;

	private String partnerStatus;

	private String partnerKey;

	private String balance;

	private String remark;

	private String telphone;

	private String operUserId;

	private Date createTime;

	private Date updateTime;

	private String payType;

	private String channelSn;

	private String returnUrl;

	private String returnErrorUrl;

	private String bgReturnUrl;

	@Id
	@Column
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId == null ? null : partnerId.trim();
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName == null ? null : partnerName.trim();
	}

	public String getPartnerStatus() {
		return partnerStatus;
	}

	public void setPartnerStatus(String partnerStatus) {
		this.partnerStatus = partnerStatus == null ? null : partnerStatus
				.trim();
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey == null ? null : partnerKey.trim();
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance == null ? null : balance.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone == null ? null : telphone.trim();
	}

	public String getOperUserId() {
		return operUserId;
	}

	public void setOperUserId(String operUserId) {
		this.operUserId = operUserId == null ? null : operUserId.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType == null ? null : payType.trim();
	}

	public String getChannelSn() {
		return channelSn;
	}

	public void setChannelSn(String channelSn) {
		this.channelSn = channelSn == null ? null : channelSn.trim();
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getReturnErrorUrl() {
		return returnErrorUrl;
	}

	public void setReturnErrorUrl(String returnErrorUrl) {
		this.returnErrorUrl = returnErrorUrl;
	}

	public String getBgReturnUrl() {
		return bgReturnUrl;
	}

	public void setBgReturnUrl(String bgReturnUrl) {
		this.bgReturnUrl = bgReturnUrl;
	}

}