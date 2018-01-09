package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * 
 * 实体类：投保要素
 *
 */



@Entity
public class SDInformationInsuredElements extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3834996748532310078L;
	
	private String	recognizeeSn	;//	被保人编号
	private String	informationSn	;//	订单详细表编码
	private String	orderSn	;//	订单编号
	private String	elementsType	;//	投保要素类型
	private String	elementsValue	;//	投保要素值
	private String	elementsSn	;//	投保要素-计划编码
	
	private SDInformationInsured sdinformationInsured;//被保人信息
	
	
	
	@ManyToOne(fetch = FetchType.LAZY) 
	public SDInformationInsured getSdinformationInsured() {
		return sdinformationInsured;
	}
	public void setSdinformationInsured(SDInformationInsured sdinformationInsured) {
		this.sdinformationInsured = sdinformationInsured;
	}
	public String getRecognizeeSn() {
		return recognizeeSn;
	}
	public void setRecognizeeSn(String recognizeeSn) {
		this.recognizeeSn = recognizeeSn;
	}
	public String getInformationSn() {
		return informationSn;
	}
	public void setInformationSn(String informationSn) {
		this.informationSn = informationSn;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	public String getElementsType() {
		return elementsType;
	}
	public void setElementsType(String elementsType) {
		this.elementsType = elementsType;
	}
	public String getElementsValue() {
		return elementsValue;
	}
	public void setElementsValue(String elementsValue) {
		this.elementsValue = elementsValue;
	}
	public String getElementsSn() {
		return elementsSn;
	}
	public void setElementsSn(String elementsSn) {
		this.elementsSn = elementsSn;
	}

}