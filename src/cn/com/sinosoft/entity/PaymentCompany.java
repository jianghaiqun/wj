package cn.com.sinosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 实体类 - 保险公司支付宝
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTDA3F0780A7AFA10368BF915483346760
 * ============================================================================
 */

@Entity
public class PaymentCompany extends BaseEntity {

	private static final long serialVersionUID = 6804273962214334149L;
	private PaymentConfig paymentConfig;//支付配置——支付宝
	private Brand brand;//品牌
	private Boolean isDefault;//是否默认
	
	@ManyToOne(fetch = FetchType.LAZY)
	public PaymentConfig getPaymentConfig() {
		return paymentConfig;
	}
	public void setPaymentConfig(PaymentConfig paymentConfig) {
		this.paymentConfig = paymentConfig;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	@Column(nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	

}
