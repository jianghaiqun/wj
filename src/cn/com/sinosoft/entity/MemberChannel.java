package cn.com.sinosoft.entity;

import javax.persistence.Entity;

@Entity
public class MemberChannel extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2508394170437773071L;
	private String memberId;
	private String channelId;
	private String type;
	private String orderId;
	private String subType;
	private String channelWay;
	
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getChannelWay() {
		return channelWay;
	}
	public void setChannelWay(String channelWay) {
		this.channelWay = channelWay;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	
	

}
