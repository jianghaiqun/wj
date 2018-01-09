package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class SDInformationProperty extends BaseEntity {

	private static final long serialVersionUID = 8868034309733021001L;
	private String recognizeeSn;// 被保人编号
	private String hourseType;// 房屋类型
	private String hourseNo;// 房产证号
	private String carPlateNo;// 车牌号
	private String licenseNumber;// 驾驶证号
	private String chassisNumber;// 车架号
	private String propertyArea1;// 财产所在地址省
	private String propertyArea2;// 财产所在地址市
	private String propertyArea3;// 财产所在地址区
	private String propertyAdress;// 财产所在地址
	private String propertyZip;// 财产所在地邮编
	private String propertyToRecognizee;//财产与被保人关系
	private String hourseAge;//房屋年龄
	private String remark1;// 备注
	private SDInformationInsured sdinformationinsured;

	
	public String getRecognizeeSn() {
		return recognizeeSn;
	}

	public void setRecognizeeSn(String recognizeeSn) {
		this.recognizeeSn = recognizeeSn;
	}

	public String getHourseType() {
		return hourseType;
	}

	public void setHourseType(String hourseType) {
		this.hourseType = hourseType;
	}

	public String getHourseNo() {
		return hourseNo;
	}

	public void setHourseNo(String hourseNo) {
		this.hourseNo = hourseNo;
	}

	public String getCarPlateNo() {
		return carPlateNo;
	}

	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getChassisNumber() {
		return chassisNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}

	public String getPropertyArea1() {
		return propertyArea1;
	}

	public void setPropertyArea1(String propertyArea1) {
		this.propertyArea1 = propertyArea1;
	}

	public String getPropertyArea2() {
		return propertyArea2;
	}

	public void setPropertyArea2(String propertyArea2) {
		this.propertyArea2 = propertyArea2;
	}

	public String getPropertyAdress() {
		return propertyAdress;
	}

	public void setPropertyAdress(String propertyAdress) {
		this.propertyAdress = propertyAdress;
	}

	public String getPropertyZip() {
		return propertyZip;
	}

	public void setPropertyZip(String propertyZip) {
		this.propertyZip = propertyZip;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	@OneToOne(fetch = FetchType.LAZY)
	public SDInformationInsured getSdinformationinsured() {
		return sdinformationinsured;
	}

	public void setSdinformationinsured(
			SDInformationInsured sdinformationinsured) {
		this.sdinformationinsured = sdinformationinsured;
	}

	public String getPropertyToRecognizee() {
		return propertyToRecognizee;
	}

	public void setPropertyToRecognizee(String propertyToRecognizee) {
		this.propertyToRecognizee = propertyToRecognizee;
	}

	public String getHourseAge() {
		return hourseAge;
	}

	public void setHourseAge(String hourseAge) {
		this.hourseAge = hourseAge;
	}

	public String getPropertyArea3() {
		return propertyArea3;
	}

	public void setPropertyArea3(String propertyArea3) {
		this.propertyArea3 = propertyArea3;
	}
	
}
