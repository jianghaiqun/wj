package cn.com.sinosoft.entity;

import javax.persistence.Entity;

/**
 * 
 * 实体类：数组字典—区分保险公司
 *
 */



@Entity
public class Dictionary extends BaseEntity {

	private static final long serialVersionUID = 2254343156222601246L;

	  
	private String codeType; //类别代码值
	private String codeTypeName; //类别名称
	private String codeValue; //属性值
	private String codeName; //属性中文
	private String codeEnName; //英文缩写
	private String InsuranceCode; //保险公司
	private String flagType;//是否申根国家标志 Y是N是
	private String productId;//产品id
	private String comFlag;//常用地区标志Y是 N不是
	
	/**
	 * @return the codeEnName
	 */
	public String getCodeEnName() {
		return codeEnName;
	}
	/**
	 * @param codeEnName the codeEnName to set
	 */
	public void setCodeEnName(String codeEnName) {
		this.codeEnName = codeEnName;
	}

	/**
	 * @return the codeType
	 */
	public String getCodeType() {
		return codeType;
	}
	/**
	 * @param codeType the codeType to set
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	/**
	 * @return the codeTypeName
	 */
	public String getCodeTypeName() {
		return codeTypeName;
	}
	/**
	 * @param codeTypeName the codeTypeName to set
	 */
	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
	}
	/**
	 * @return the codeValue
	 */
	public String getCodeValue() {
		return codeValue;
	}
	/**
	 * @param codeValue the codeValue to set
	 */
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	/**
	 * @return the codeName
	 */
	public String getCodeName() {
		return codeName;
	}
	/**
	 * @param codeName the codeName to set
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	/**
	 * @return the insuranceCode
	 */
	public String getInsuranceCode() {
		return InsuranceCode;
	}
	/**
	 * @param insuranceCode the insuranceCode to set
	 */
	public void setInsuranceCode(String insuranceCode) {
		InsuranceCode = insuranceCode;
	}
	public String getFlagType() {
		return flagType;
	}
	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getComFlag() {
		return comFlag;
	}
	public void setComFlag(String comFlag) {
		this.comFlag = comFlag;
	}
	
	
}