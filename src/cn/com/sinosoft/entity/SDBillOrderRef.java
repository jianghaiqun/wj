package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 实体类：邮寄地址信息 
 *
 */
@Entity
@Table(name = "sdbillorderref")
public class SDBillOrderRef extends BaseEntity {

	private static final long serialVersionUID = -2321328939976906697L;

	private String billId;//发票Id
	private String memberId;//会员Id
	private String orderSn;//订单号
	
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
}
