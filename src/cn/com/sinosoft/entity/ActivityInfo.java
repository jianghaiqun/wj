package cn.com.sinosoft.entity;

import java.util.Date;

import javax.persistence.Entity;
/**
 * 
* @ClassName: ActivityInfo 
* @Description: TODO(实体类：支付页面活动信息) 
* @author zhangjing 
* @date 2013-12-23 下午02:43:37 
*
 */
@Entity
public class ActivityInfo extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7315343648190652974L;

	private String Id;

	private String activitysn;

	private String title;

	private String description;

	private String couponnum;

	private String batch;

	private String type;

	private String riskcode;

	private String insurancecompany;

	private String payamount;

	private Date starttime;

	private Date endtime;

	private String accumulation;

	private String status;

	private String autocreate;

	private String channelsn;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String createuser;

	private Date createtime;

	private String modifyuser;

	private Date modifytime;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getActivitysn() {
		return activitysn;
	}

	public void setActivitysn(String activitysn) {
		this.activitysn = activitysn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCouponnum() {
		return couponnum;
	}

	public void setCouponnum(String couponnum) {
		this.couponnum = couponnum;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRiskcode() {
		return riskcode;
	}

	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}

	public String getInsurancecompany() {
		return insurancecompany;
	}

	public void setInsurancecompany(String insurancecompany) {
		this.insurancecompany = insurancecompany;
	}

	public String getPayamount() {
		return payamount;
	}

	public void setPayamount(String payamount) {
		this.payamount = payamount;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getAccumulation() {
		return accumulation;
	}

	public void setAccumulation(String accumulation) {
		this.accumulation = accumulation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAutocreate() {
		return autocreate;
	}

	public void setAutocreate(String autocreate) {
		this.autocreate = autocreate;
	}

	public String getChannelsn() {
		return channelsn;
	}

	public void setChannelsn(String channelsn) {
		this.channelsn = channelsn;
	}

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public String getProp2() {
		return prop2;
	}

	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}

	public String getProp3() {
		return prop3;
	}

	public void setProp3(String prop3) {
		this.prop3 = prop3;
	}

	public String getProp4() {
		return prop4;
	}

	public void setProp4(String prop4) {
		this.prop4 = prop4;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getModifyuser() {
		return modifyuser;
	}

	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	
	
}