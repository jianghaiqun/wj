package cn.com.sinosoft.entity;

import javax.persistence.Entity;


/**
 * 实体类 - 用户需求
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT7603DDA2CB064D51962786F66C07F0DB
 * ============================================================================
 */

@Entity
public class CustomerDemand extends BaseEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7519486823153844426L;
	
	public static final int MAX_INSUREDNAME_LIST_COUNT = 20;// 为谁投保==最大数
	public static final int MAX_INSURANCETYPE_LIST_COUNT = 20;// 险种类型==最大数
	public static final int MAX_GUARANTEEPERIOD_LIST_COUNT = 20;// 保障期限==最大数
	
	private String customername;// 用户名
	private String phone;// 电话
	private String email;// E-mail
	private String insuredname;// 被保人
	private String insuredage;// 被保人年龄
	private String insurancetype;// 险种类型
	private String guaranteeperiod;// 保障期限
	
	public CustomerDemand() {
		super();
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getInsuredname() {
		return insuredname;
	}
	public void setInsuredname(String insuredname) {
		this.insuredname = insuredname;
	}
	public String getInsuredage() {
		return insuredage;
	}
	public void setInsuredage(String insuredage) {
		this.insuredage = insuredage;
	}
	public String getInsurancetype() {
		return insurancetype;
	}
	public void setInsurancetype(String insurancetype) {
		this.insurancetype = insurancetype;
	}
	public String getGuaranteeperiod() {
		return guaranteeperiod;
	}
	public void setGuaranteeperiod(String guaranteeperiod) {
		this.guaranteeperiod = guaranteeperiod;
	}


}