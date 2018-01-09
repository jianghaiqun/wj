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
 * 实体类：财产信息
 *
 */



@Entity
public class InformationItemProp extends BaseEntity {

	private static final long serialVersionUID = 2254343156222601246L;

	private InformationItemMain informationItemMain;// 标的
	private String name; //名称
	private String amt; //数量
	private String insurePrice; //投保定价方式
	private String remark; //备注
	private String address; //所在地
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the amt
	 */
	public String getAmt() {
		return amt;
	}
	/**
	 * @param amt the amt to set
	 */
	public void setAmt(String amt) {
		this.amt = amt;
	}
	/**
	 * @return the insurePrice
	 */
	public String getInsurePrice() {
		return insurePrice;
	}
	/**
	 * @param insurePrice the insurePrice to set
	 */
	public void setInsurePrice(String insurePrice) {
		this.insurePrice = insurePrice;
	}
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