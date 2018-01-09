package cn.com.sinosoft.entity;

import java.sql.Date;
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
 * 实体类：房屋信息
 *
 */



@Entity
public class InformationItemHouse extends BaseEntity {

	private static final long serialVersionUID = 2254343156222601246L;

	private InformationItemMain informationItemMain;// 标的
	private String structure; //房屋结构
	private String buildArea; //建筑面积
	private String unitValue; //每平米价格
	private String sumValue; //总价款
	private Date buildTime; //建造时间
	private String useage; //用途
	private String remark; //备注
	private String address; //房屋所在地
	
	
	/**
	 * @return the area
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param area the area to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the structure
	 */
	public String getStructure() {
		return structure;
	}
	/**
	 * @param structure the structure to set
	 */
	public void setStructure(String structure) {
		this.structure = structure;
	}
	/**
	 * @return the buildArea
	 */
	public String getBuildArea() {
		return buildArea;
	}
	/**
	 * @param buildArea the buildArea to set
	 */
	public void setBuildArea(String buildArea) {
		this.buildArea = buildArea;
	}
	/**
	 * @return the unitValue
	 */
	public String getUnitValue() {
		return unitValue;
	}
	/**
	 * @param unitValue the unitValue to set
	 */
	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}
	/**
	 * @return the sumValue
	 */
	public String getSumValue() {
		return sumValue;
	}
	/**
	 * @param sumValue the sumValue to set
	 */
	public void setSumValue(String sumValue) {
		this.sumValue = sumValue;
	}
	/**
	 * @return the buildTime
	 */
	public Date getBuildTime() {
		return buildTime;
	}
	/**
	 * @param buildTime the buildTime to set
	 */
	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}
	/**
	 * @return the useage
	 */
	public String getUseage() {
		return useage;
	}
	/**
	 * @param useage the useage to set
	 */
	public void setUseage(String useage) {
		this.useage = useage;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	public InformationItemMain getInformationItemMain() {
		return informationItemMain;
	}
	public void setInformationItemMain(InformationItemMain informationItemMain) {
		this.informationItemMain = informationItemMain;
	}
}