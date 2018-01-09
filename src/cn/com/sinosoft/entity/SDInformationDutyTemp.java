/**
 * 
 */
package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangcaiyun
 *
 */
@Entity
@Table(name = "SDInformationDutyTemp")
public class SDInformationDutyTemp extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8909021512196672715L;

	private String DutySerials;

	private String OrderSn;

	private String DutySn;

	private String Amnt;

	private String Premium;
	
	private String DiscountRates;
	
	private String DiscountPrice;
	
	private String Remark1;

	private String Remark2;

	private String Remark3;

	public String getDutySerials() {
		return DutySerials;
	}

	public void setDutySerials(String dutySerials) {
		DutySerials = dutySerials;
	}

	public String getOrderSn() {
		return OrderSn;
	}

	public void setOrderSn(String orderSn) {
		OrderSn = orderSn;
	}

	public String getDutySn() {
		return DutySn;
	}

	public void setDutySn(String dutySn) {
		DutySn = dutySn;
	}

	public String getAmnt() {
		return Amnt;
	}

	public void setAmnt(String amnt) {
		Amnt = amnt;
	}

	public String getPremium() {
		return Premium;
	}

	public void setPremium(String premium) {
		Premium = premium;
	}

	public String getDiscountRates() {
		return DiscountRates;
	}

	public void setDiscountRates(String discountRates) {
		DiscountRates = discountRates;
	}

	public String getDiscountPrice() {
		return DiscountPrice;
	}

	public void setDiscountPrice(String discountPrice) {
		DiscountPrice = discountPrice;
	}

	public String getRemark1() {
		return Remark1;
	}

	public void setRemark1(String remark1) {
		Remark1 = remark1;
	}

	public String getRemark2() {
		return Remark2;
	}

	public void setRemark2(String remark2) {
		Remark2 = remark2;
	}

	public String getRemark3() {
		return Remark3;
	}

	public void setRemark3(String remark3) {
		Remark3 = remark3;
	}
}
