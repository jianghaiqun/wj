package cn.com.sinosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;


/**
 * Bean类 - 物流项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTDE21DFA17B86CB2B9C558AB35759D2E1
 * ============================================================================
 */

@Entity
public class PresentDeliveryItem extends BaseEntity {
	
	private static final long serialVersionUID = -6783787752984851646L;
	
	private String presentSn;// 礼品货号
	private String presentName;// 礼品名称
	private String presentHtmlFilePath;// 礼品HTML静态文件路径
	private Integer deliveryQuantity;// 物流数量
	
//	private Present present;// 礼品
//	private Shipping shipping;// 发货
//	private Reship reship;// 退货
	
	@Column(updatable = false, nullable = false)
	public String getPresentSn() {
		return presentSn;
	}
	
	public void setPresentSn(String presentSn) {
		this.presentSn = presentSn;
	}
	
	@Column(updatable = false, nullable = false)
	public String getPresentName() {
		return presentName;
	}
	
	public void setPresentName(String presentName) {
		this.presentName = presentName;
	}
	
	@Column(nullable = false, updatable = false)
	public String getPresentHtmlFilePath() {
		return presentHtmlFilePath;
	}
	
	public void setPresentHtmlFilePath(String presentHtmlFilePath) {
		this.presentHtmlFilePath = presentHtmlFilePath;
	}
	
	@Column(updatable = false, nullable = false)
	public Integer getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Integer deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	public Present getPresent() {
//		return present;
//	}
//
//	public void setPresent(Present present) {
//		this.present = present;
//	}
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	public Shipping getShipping() {
//		return shipping;
//	}
//	
//	public void setShipping(Shipping shipping) {
//		this.shipping = shipping;
//	}
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//	public Reship getReship() {
//		return reship;
//	}
//
//	public void setReship(Reship reship) {
//		this.reship = reship;
//	}
	
}