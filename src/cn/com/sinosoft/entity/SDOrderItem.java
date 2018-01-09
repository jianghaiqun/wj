package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;



/**
 * Bean类 - 订单项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT51EFC4FAC53E35EE0A29EEC65F82EE9C
 * ============================================================================
 */

@Entity
public class SDOrderItem extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3572282333462184041L;
	
	private String	OrderitemSn	;//	订单项目编号
	private String	OrderSn	;//	订单编号
	private String	OrderPoint	;//	订单积分
	
	private SDOrder sdorder;
	private String	PointStatus	;//	积分状态(与会员关联使用)
	private String  channelCode ;//渠道编码
	private String  channelAgentCode;//代理人编码
	private String  typeFlag;//投保流程去头尾标志
	
	@OneToOne(fetch = FetchType.LAZY)
	public SDOrder getSdorder() {
		return sdorder;
	}
	public void setSdorder(SDOrder sdorder) {
		this.sdorder = sdorder;
	}
	public String getOrderitemSn() {
		return OrderitemSn;
	}
	public void setOrderitemSn(String orderitemSn) {
		OrderitemSn = orderitemSn;
	}
	 
	public String getOrderSn() {
		return OrderSn;
	}
	public void setOrderSn(String orderSn) {
		OrderSn = orderSn;
	}
	public String getOrderPoint() {
		return OrderPoint;
	}
	public void setOrderPoint(String orderPoint) {
		OrderPoint = orderPoint;
	}
	public String getPointStatus() {
		return PointStatus;
	}
	public void setPointStatus(String pointStatus) {
		PointStatus = pointStatus;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelAgentCode() {
		return channelAgentCode;
	}
	public void setChannelAgentCode(String channelAgentCode) {
		this.channelAgentCode = channelAgentCode;
	}
	public String getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}

}