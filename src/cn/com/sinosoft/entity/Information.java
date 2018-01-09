package cn.com.sinosoft.entity;

import java.util.Date;
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
 * 实体类：投被保人基本信息 ，关联orderItem,以及用户订单界面公共属性的Information
 *
 */



@Entity
public class Information extends BaseEntity {

	private static final long serialVersionUID = 2254343156222601246L;

	private OrderItem orderItem;// 订单
	private String protectionPeriodTy;//保障期限类型
	private String protectionPeriod;//保障期限
	private String allPeriod;
	private Date effective;//生效日期
	private Date fail;//失效日期
	private String appType;//缴费方式
	private String feeYear;// 缴费年期
	
	private Set<InformationInsured> informationInsuredSet;// 被保人信息做级联删除用
	private InformationAppnt informationAppnt;//投保人信息
	private Set<InformationDuty> informationDutyElementsSet;//责任投保要素信息
	
	public String getProtectionPeriod() {
		return protectionPeriod;
	}
	public void setProtectionPeriod(String protectionPeriod) {
		this.protectionPeriod = protectionPeriod;
	}
	public String getProtectionPeriodTy() {
		return protectionPeriodTy;
	}
	public void setProtectionPeriodTy(String protectionPeriodTy) {
		this.protectionPeriodTy = protectionPeriodTy;
	}
	
	public Date getEffective() {
		return effective;
	}
	public void setEffective(Date effective) {
		this.effective = effective;
	}
	public Date getFail() {
		return fail;
	}
	public void setFail(Date fail) {
		this.fail = fail;
	}
	
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getFeeYear() {
		return feeYear;
	}
	public void setFeeYear(String feeYear) {
		this.feeYear = feeYear;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	@OneToMany(mappedBy = "information", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<InformationInsured> getInformationInsuredSet() {
		return informationInsuredSet;
	}
	public void setInformationInsuredSet(Set<InformationInsured> informationInsuredSet) {
		this.informationInsuredSet = informationInsuredSet;
	}
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "information")
	@Cascade(value = { CascadeType.DELETE })
	public InformationAppnt getInformationAppnt() {
		return informationAppnt;
	}

	public void setInformationAppnt(InformationAppnt informationAppnt) {
		this.informationAppnt = informationAppnt;
	}
	@OneToMany(mappedBy = "information", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<InformationDuty> getInformationDutyElementsSet() {
		return informationDutyElementsSet;
	}
	public void setInformationDutyElementsSet(
			Set<InformationDuty> informationDutyElementsSet) {
		this.informationDutyElementsSet = informationDutyElementsSet;
	}
	public String getAllPeriod() {
		return allPeriod;
	}
	public void setAllPeriod(String allPeriod) {
		this.allPeriod = allPeriod;
	}
	
}