package cn.com.sinosoft.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 
 * 实体类：标的主表关联Information
 *
 */



@Entity
public class InformationItemMain extends BaseEntity {

	private static final long serialVersionUID = 2254343156222601246L;

	private InformationInsured informationInsured;// 基本信息
	private String RiskCode;// 险种代码
	private String SupplierCode;// 保险公司编码
	private String ItemName;// 标的名称
	private String ItemCode;//标的项目类别代码
	
	private Set<InformationItemHouse> informationItemHouseSet;// 房屋信息
	private Set<InformationItemProp> informationItemPropSet;// 财产信息
	private Set<InformationItemCargo> informationItemCargoSet;// 运输信息

	/**
	 * @return the riskCode
	 */
	public String getRiskCode() {
		return RiskCode;
	}
	/**
	 * @param riskCode the riskCode to set
	 */
	public void setRiskCode(String riskCode) {
		RiskCode = riskCode;
	}
	/**
	 * @return the supplierCode
	 */
	public String getSupplierCode() {
		return SupplierCode;
	}
	/**
	 * @param supplierCode the supplierCode to set
	 */
	public void setSupplierCode(String supplierCode) {
		SupplierCode = supplierCode;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return ItemName;
	}
	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return ItemCode;
	}
	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		ItemCode = itemCode;
	}

	
	@OneToMany(mappedBy = "informationItemMain", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<InformationItemHouse> getInformationItemHouseSet() {
		return informationItemHouseSet;
	}
	public void setInformationItemHouseSet(Set<InformationItemHouse> informationItemHouseSet) {
		this.informationItemHouseSet = informationItemHouseSet;
	}

	@OneToMany(mappedBy = "informationItemMain", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<InformationItemCargo> getInformationItemCargoSet() {
		return informationItemCargoSet;
	}
	public void setInformationItemCargoSet(Set<InformationItemCargo> informationItemCargoSet) {
		this.informationItemCargoSet = informationItemCargoSet;
	}
	
	@OneToMany(mappedBy = "informationItemMain", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<InformationItemProp> getInformationItemPropSet() {
		return informationItemPropSet;
	}
	public void setInformationItemPropSet(Set<InformationItemProp> informationItemPropSet) {
		this.informationItemPropSet = informationItemPropSet;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public InformationInsured getInformationInsured() {
		return informationInsured;
	}

	public void setInformationInsured(InformationInsured informationInsured) {
		this.informationInsured = informationInsured;
	}

}