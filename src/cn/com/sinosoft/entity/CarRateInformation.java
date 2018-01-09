package cn.com.sinosoft.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author LiuXin
 *
 */
@Entity
public class CarRateInformation extends BaseEntity{

	private static final long serialVersionUID = -8861974999493821641L;
	/**
	 * 保险公司编码
	 */
	@Column(name = "COMPANYCODE", length = 20)
	private String companyCode;
	/**
	 * 车辆行驶区域
	 */
	@Column(name = "REGIONCODE", length = 20)
	private String regionCode;
	/**
	 * 费率模板类型
	 */
	@Column(name = "MODELTYPE", length = 20)
	private String modelType;
	/**
	 * 保险公司折扣系数
	 */
	@Column(name = "DICOUNT")
	private Double dicount;
	/**
	 * 是否激活
	 * Y：激活；N：未激活。
	 */
	@Column(name = "FLAG", length = 20)
	private String flag;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public Double getDicount() {
		return dicount;
	}
	public void setDicount(Double dicount) {
		this.dicount = dicount;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	} 

}
