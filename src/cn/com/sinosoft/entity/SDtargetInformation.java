package cn.com.sinosoft.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author LiuXin
 *
 */
@Entity
@Table(name = "SDTARGETINFORMATION")
public class SDtargetInformation implements Serializable {
	private static final long serialVersionUID = 7696245357927100934L;
	/**
	 * 车险试算流水号
	 * 主键
	 */
	@Id
	@Column(name = "ID", length = 30, nullable = true)
	private String id;
	/**
	 * 会员id
	 */
	@Column(name = "MEMBERID", length = 32)
	private String memberId;
	/**
	 * 车辆行驶区域
	 */
	@Column(name = "REGIONCODE", length = 20)
	private String regionCode;
	/**
	 * 车牌号
	 */
	@Column(name = "LICENSENO", length = 20)
	private String licenseNo ;
	/**
	 * 购车时间
	 */
	@Column(name = "CARBUYTIME")
	private Date carBuyTime;
	/**
	 * 车辆型号
	 */
	@Column(name = "MODELSCODE", length = 20)
	private String modelsCode;
	/**
	 * 车辆品牌型号
	 */
	@Column(name = "CARBRAND", length = 20)
	private String carBrand;
	/**
	 * 新车购置价
	 */
	@Column(name = "CARPRICE", precision = 4, length = 15)
	private Double carPrice;
	/**
	 * 核定载客
	 */
	@Column(name = "PASSENGER", length = 6)
	private Integer passenger;
	/**
	 * 核定载重
	 */
	@Column(name = "APPROVEDLOAD", precision = 2, length = 5)
	private Double approvedLoad;
	/**
	 * 车辆使用时间
	 */
	@Column(name = "USEDYEAR", length = 6)
	private Long usedYear;
	/**
	 * 车辆使用性质
	 */
	@Column(name = "CARUSED", length = 20)
	private String carUsed;
	/**
	 * 车辆所属性质
	 */
	@Column(name = "CARPROPERTIES", length = 20)
	private String carProperties;
	/**
	 * 上年度投保公司
	 */
	@Column(name = "LASTINSURECOM", length = 20)
	private String lastInsureCom;
	/**
	 * 车主姓名（投保人）
	 */
	@Column(name = "INSUREDER", length = 60)
	private String insureder;
	/**
	 * 被保人生日
	 */
	@Column(name = "BIRTHDAY")
	private Date birthday;
	/**
	 * 被保人手机
	 */
	@Column(name = "SEX", length = 2)
	private String sex;
	/**
	 * 被保人手机
	 */
	@Column(name = "TELNO", length = 20)
	private String telNo;
	/**
	 * 被保人E-Mail
	 */
	@Column(name = "EMAIL", length = 50)
	private String email;
	/**
	 * 创建时间
	 */
	@Column(name = "CREATEDATE")
	private Date createDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public Date getCarBuyTime() {
		return carBuyTime;
	}
	public void setCarBuyTime(Date carBuyTime) {
		this.carBuyTime = carBuyTime;
	}
	public String getModelsCode() {
		return modelsCode;
	}
	public void setModelsCode(String modelsCode) {
		this.modelsCode = modelsCode;
	}
	public String getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	public Double getCarPrice() {
		return carPrice;
	}
	public void setCarPrice(Double carPrice) {
		this.carPrice = carPrice;
	}
	public Integer getPassenger() {
		return passenger;
	}
	public void setPassenger(Integer passenger) {
		this.passenger = passenger;
	}
	public Double getApprovedLoad() {
		return approvedLoad;
	}
	public void setApprovedLoad(Double approvedLoad) {
		this.approvedLoad = approvedLoad;
	}
	public Long getUsedYear() {
		return usedYear;
	}
	public void setUsedYear(Long usedYear) {
		this.usedYear = usedYear;
	}
	public String getCarUsed() {
		return carUsed;
	}
	public void setCarUsed(String carUsed) {
		this.carUsed = carUsed;
	}
	public String getCarProperties() {
		return carProperties;
	}
	public void setCarProperties(String carProperties) {
		this.carProperties = carProperties;
	}
	public String getLastInsureCom() {
		return lastInsureCom;
	}
	public void setLastInsureCom(String lastInsureCom) {
		this.lastInsureCom = lastInsureCom;
	}
	public String getInsureder() {
		return insureder;
	}
	public void setInsureder(String insureder) {
		this.insureder = insureder;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
