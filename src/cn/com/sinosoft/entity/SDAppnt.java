package cn.com.sinosoft.entity;

import javax.persistence.Entity;

/**
 * 
 * 实体类：中英赠险使用
 *
 */



@Entity
public class SDAppnt extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String appntName;//姓名
	private String appntSex;//性别
	private String appntSexName;//
	private String appntBirthDay;//生日
	private String appntArea1;//一级地区
	private String appntArea2;//二级地区
	private String appntMobile;//手机号
	private String appntEmail;//邮箱
	private String channelSn;//渠道 :□主站    □wap站  □淘宝   □CPS    □旅行社 微信
	private String activityType;//活动类型：□满减    □折扣     □满送   □买送   □赠券   □多倍积分   □实物类   □站外卡券 赠险
	private String giveFlag;//是否已领取 Y:领取
	
	
	
	public String getAppntName() {
		return appntName;
	}
	public void setAppntName(String appntName) {
		this.appntName = appntName;
	}
	public String getAppntSex() {
		return appntSex;
	}
	public void setAppntSex(String appntSex) {
		this.appntSex = appntSex;
	}
	public String getAppntSexName() {
		return appntSexName;
	}
	public void setAppntSexName(String appntSexName) {
		this.appntSexName = appntSexName;
	}
	public String getAppntBirthDay() {
		return appntBirthDay;
	}
	public void setAppntBirthDay(String appntBirthDay) {
		this.appntBirthDay = appntBirthDay;
	}
	public String getAppntArea1() {
		return appntArea1;
	}
	public void setAppntArea1(String appntArea1) {
		this.appntArea1 = appntArea1;
	}
	public String getAppntArea2() {
		return appntArea2;
	}
	public void setAppntArea2(String appntArea2) {
		this.appntArea2 = appntArea2;
	}
	public String getAppntMobile() {
		return appntMobile;
	}
	public void setAppntMobile(String appntMobile) {
		this.appntMobile = appntMobile;
	}
	public String getAppntEmail() {
		return appntEmail;
	}
	public void setAppntEmail(String appntEmail) {
		this.appntEmail = appntEmail;
	}
	public String getChannelSn() {
		return channelSn;
	}
	public void setChannelSn(String channelSn) {
		this.channelSn = channelSn;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getGiveFlag() {
		return giveFlag;
	}
	public void setGiveFlag(String giveFlag) {
		this.giveFlag = giveFlag;
	}
	
}