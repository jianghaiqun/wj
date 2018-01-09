package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;


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
public class SDOrderItemOth extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6685774078967579140L;
	
	private String	OrderitemSn	;//	订单项目编号
	private String	OrderSn	;//	订单编号
	private String	RecognizeeSn	;//	被保人编号
	private String	TpySn	;//	外部流水号
	private String	TpySysSn	;//	外部系统订单号
	private String	TpySysPaySn	;//	外部系统支付流水号
	
	private SDInformationInsured  sdinformationinsured;//被保人表
	
	@ManyToOne(fetch = FetchType.LAZY) 
	public SDInformationInsured getSdinformationinsured() {
		return sdinformationinsured;
	}
	public void setSdinformationinsured(SDInformationInsured sdinformationinsured) {
		this.sdinformationinsured = sdinformationinsured;
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
	public String getRecognizeeSn() {
		return RecognizeeSn;
	}
	public void setRecognizeeSn(String recognizeeSn) {
		RecognizeeSn = recognizeeSn;
	}
	public String getTpySn() {
		return TpySn;
	}
	public void setTpySn(String tpySn) {
		TpySn = tpySn;
	}
	public String getTpySysSn() {
		return TpySysSn;
	}
	public void setTpySysSn(String tpySysSn) {
		TpySysSn = tpySysSn;
	}
	public String getTpySysPaySn() {
		return TpySysPaySn;
	}
	public void setTpySysPaySn(String tpySysPaySn) {
		TpySysPaySn = tpySysPaySn;
	}
	
}