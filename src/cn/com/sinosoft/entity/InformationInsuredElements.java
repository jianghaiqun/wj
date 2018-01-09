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
public class InformationInsuredElements extends BaseEntity {
	private static final long serialVersionUID = 5548059880337563574L;
	private InformationInsured informationInsured;// 被保人
	private String ElementsType;//投保要素类型
	private String ElementsValue;//投保要素值
	private String elementsSn;//投保要素-计划编码

	/**
	 * @return the elementsSn
	 */
	public String getElementsSn() {
		return elementsSn;
	}
	/**
	 * @param elementsSn the elementsSn to set
	 */
	public void setElementsSn(String elementsSn) {
		this.elementsSn = elementsSn;
	}
	/**
	 * @return the elementsType
	 */
	public String getElementsType() {
		return ElementsType;
	}
	/**
	 * @param elementsType the elementsType to set
	 */
	public void setElementsType(String elementsType) {
		ElementsType = elementsType;
	}
	/**
	 * @return the elementsValue
	 */
	public String getElementsValue() {
		return ElementsValue;
	}
	/**
	 * @param elementsValue the elementsValue to set
	 */
	public void setElementsValue(String elementsValue) {
		ElementsValue = elementsValue;
	}


	
	@ManyToOne(fetch = FetchType.LAZY)
	public InformationInsured getInformationInsured() {
		return informationInsured;
	}
	public void setInformationInsured(InformationInsured informationInsured) {
		this.informationInsured = informationInsured;
	}
}