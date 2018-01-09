package cn.com.sinosoft.entity;

import javax.persistence.Entity;

/**
 * 实体类 - 管理员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT7603DDA2CB064D51962786F66C07F0DB
 * ============================================================================
 */

@Entity
public class OrderProduct extends BaseEntity{
	
	private static final long serialVersionUID = 4625047339257505565L;
	
	private String orderId;// 订单号
	private String productId;// 产品号
	private String productInsAttrName;// 产品属性名称
	private String productInsAttrContent;// 产品属性填充内容


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductInsAttrName() {
		return productInsAttrName;
	}

	public void setProductInsAttrName(String productInsAttrName) {
		this.productInsAttrName = productInsAttrName;
	}

	public String getProductInsAttrContent() {
		return productInsAttrContent;
	}

	public void setProductInsAttrContent(String productInsAttrContent) {
		this.productInsAttrContent = productInsAttrContent;
	}



}