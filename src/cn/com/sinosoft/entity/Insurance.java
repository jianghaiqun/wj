package cn.com.sinosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author LiuXin
 *
 */
@Entity
public class Insurance extends BaseEntity {
	private static final long serialVersionUID = -2269807260224657983L;
	/**
	 * 流水号
	 */
	private String serialNumber;
	/**
	 * 会员id
	 */
	private String memberId;
	/**
	 * 套餐编号
	 */
	private String carMenuCode;
	/**
	 * 投保地区
	 */
	@Column(name = "REGIONCODE", length = 30)
	private String regionCode;
	/**
	 * 投保的保险公司
	 */
	@Column(name = "COMPANY", length = 30)
	private String company;
	/**
	 * 第三责任险
	 */
	@Column(name = "R001", length = 1)
	private String r001;
	/**
	 * 第三责任险保额
	 */
	@Column(name = "R001_PREMIUM", precision = 4, length = 15)
	private double r001_premium;
	/**
	 * 第三责任险保费
	 */
	@Column(name = "PREMIUM_R001", precision = 2, length = 15)
	private double premium_R001;
	/**
	 * 机动车损失险
	 */
	@Column(name = "R002", length = 1)
	private String r002;
	/**
	 * 机动车损失险保额
	 */
	@Column(name = "R002_PREMIUM", precision = 4, length = 15)
	private double r002_premium;
	/**
	 * 车辆损失险保费
	 */
	@Column(name = "PREMIUM_R002", precision = 2, length = 15)
	private double premium_R002;
	/**
	 * 	司机责任险
	 */
	@Column(name = "R0030", length = 1)
	private String r0030;
	/**
	 * 	司机责任险保额
	 */
	@Column(name = "R0030_PREMIUM", precision = 4, length = 15)
	private double r0030_premium;
	/**
	 * 司机保费
	 */
	@Column(name = "PREMIUM_R0030", precision = 2, length = 15)
	private double premium_R0030;
	/**
	 * 乘客责任险
	 */
	@Column(name = "R0031", length = 1)
	private String r0031;
	/**
	 * 乘客责任险保额
	 */
	@Column(name = "R0031_PREMIUM", precision = 4, length = 15)
	private double r0031_premium;
	/**
	 * 乘客保费
	 */
	@Column(name = "PREMIUM_R0031", precision = 2, length = 15)
	private double premium_R0031;
	/**
	 * 盗抢险
	 */
	@Column(name = "R004", length = 1)
	private String r004;
	/**
	 * 盗抢险保额
	 */
	@Column(name = "R004_PREMIUM", precision = 4, length = 15)
	private double r004_premium;
	/**
	 * 盗抢险保费
	 */
	@Column(name = "PREMIUM_R004", precision = 2, length = 15)
	private double premium_R004;
	/**
	 * 玻璃单独破碎险
	 */
	@Column(name = "R006", length = 1)
	private String r006;
	/**
	 * 玻璃单类别
	 * 0=国产；1=进口
	 */
	@Column(name = "R006_TYPE", length = 1)
	private String r006_type;
	/**
	 * 单独玻璃险保费
	 */
	@Column(name = "PREMIUM_R006", precision = 2, length = 15)
	private double premium_R006;
	/**
	 * 车身划痕险
	 */
	@Column(name = "R008", length = 1)
	private String r008;
	/**
	 * 车身划痕险保额
	 */
	@Column(name = "R008_PREMIUM", precision = 4, length = 15)
	private double r008_premium;
	/**
	 * 车身划痕险保费
	 */
	@Column(name = "PREMIUM_R008", precision = 2, length = 15)
	private double premium_R008;
	/**
	 * 涉水险保费
	 */
	@Column(name = "PREMIUM_WDLAR", precision = 2, length = 15)
	private double premium_WDLAR;
	/**
	 * 自然损失险
	 */
	@Column(name = "SCLAR", length = 1)
	private String sclar;
	/**
	 * 自然损失险保额
	 */
	@Column(name = "SCLAR_PREMIUM", precision = 4, length = 15)
	private Double sclar_premium;
	/**
	 * 自然损失险保费
	 */
	@Column(name = "PREMIUM_SCLAR", precision = 2, length = 15)
	private double premium_SCLAR;
	/**
	 * 商业险优惠前总保费
	 */
	@Column(name = "COMMERCIALPREMIUM", precision = 2, length = 15)
	private double commercialPremium;
	/**
	 * 第三责任不计免赔
	 */
	@Column(name = "NR001", length = 1)
	private String NR001;
	/**
	 * 第三责任险不计免赔保费
	 */
	@Column(name = "NONDEDUCTIBLE_R001", precision = 2, length = 15)
	private double nonDeductible_R001;
	/**
	 * 机动车损失不计免赔
	 */
	@Column(name = "NR002", length = 1)
	private String NR002;
	/**
	 * 车辆损失险不计免赔保费
	 */
	@Column(name = "NONDEDUCTIBLE_R002", precision = 2, length = 15)
	private double nonDeductible_R002;
	/**
	 * 司机责任不计免赔
	 */
	@Column(name = "NR0030", length = 1)
	private String NR0030;
	/**
	 * 司机不计免赔保费
	 */
	@Column(name = "NONDEDUCTIBLE_R0030", precision = 2, length = 15)
	private double nonDeductible_R0030;
	/**
	 * 乘客责任不计免赔
	 */
	@Column(name = "NR0031", length = 1)
	private String NR0031;
	/**
	 * 乘客不计免赔保费 
	 */
	@Column(name = "NONDEDUCTIBLE_R0031", precision = 2, length = 15)
	private double nonDeductible_R0031;
	/**
	 * 盗抢险不计免赔
	 */
	@Column(name = "NR004", length = 1)
	private String NR004;
	/**
	 * 盗抢险保费 
	 */
	@Column(name = "NONDEDUCTIBLE_R004", precision = 2, length = 15)
	private double nonDeductible_R004;
	/**
	 * 车身划痕不计免赔
	 */
	@Column(name = "NR008", length = 1)
	private String NR008;
	/**
	 * 车身划痕损失险
	 */
	@Column(name = "NONDEDUCTIBLE_R008", precision = 2, length = 15)
	private double nonDeductible_R008;
	/**
	 * 不计免赔总保费
	 */
	@Column(name = "NOTDEDUCTIBLE", precision = 2, length = 15)
	private double notDeductible; 
	/**
	 * 商业险优惠后总保费
	 */
	@Column(name = "TOTLE", precision = 2, length = 15)
	private double totle;
	/**
	 * 折扣率
	 */
	@Column(name = "DICOUNT", precision = 2, length = 15)
	private Double dicount;
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public double getPremium_R001() {
		return premium_R001;
	}
	public void setPremium_R001(double premium_R001) {
		this.premium_R001 = premium_R001;
	}
	public double getPremium_R002() {
		return premium_R002;
	}
	public void setPremium_R002(double premium_R002) {
		this.premium_R002 = premium_R002;
	}
	public double getPremium_R0030() {
		return premium_R0030;
	}
	public void setPremium_R0030(double premium_R0030) {
		this.premium_R0030 = premium_R0030;
	}
	public double getPremium_R0031() {
		return premium_R0031;
	}
	public void setPremium_R0031(double premium_R0031) {
		this.premium_R0031 = premium_R0031;
	}
	public double getPremium_R004() {
		return premium_R004;
	}
	public void setPremium_R004(double premium_R004) {
		this.premium_R004 = premium_R004;
	}
	public double getPremium_R006() {
		return premium_R006;
	}
	public void setPremium_R006(double premium_R006) {
		this.premium_R006 = premium_R006;
	}
	public double getPremium_R008() {
		return premium_R008;
	}
	public void setPremium_R008(double premium_R008) {
		this.premium_R008 = premium_R008;
	}
	public double getPremium_WDLAR() {
		return premium_WDLAR;
	}
	public void setPremium_WDLAR(double premium_WDLAR) {
		this.premium_WDLAR = premium_WDLAR;
	}
	public double getPremium_SCLAR() {
		return premium_SCLAR;
	}
	public void setPremium_SCLAR(double premium_SCLAR) {
		this.premium_SCLAR = premium_SCLAR;
	}
	public double getCommercialPremium() {
		return commercialPremium;
	}
	public void setCommercialPremium(double commercialPremium) {
		this.commercialPremium = commercialPremium;
	}
	public double getNonDeductible_R001() {
		return nonDeductible_R001;
	}
	public void setNonDeductible_R001(double nonDeductible_R001) {
		this.nonDeductible_R001 = nonDeductible_R001;
	}
	public double getNonDeductible_R002() {
		return nonDeductible_R002;
	}
	public void setNonDeductible_R002(double nonDeductible_R002) {
		this.nonDeductible_R002 = nonDeductible_R002;
	}
	public double getNonDeductible_R0030() {
		return nonDeductible_R0030;
	}
	public void setNonDeductible_R0030(double nonDeductible_R0030) {
		this.nonDeductible_R0030 = nonDeductible_R0030;
	}
	public double getNonDeductible_R0031() {
		return nonDeductible_R0031;
	}
	public void setNonDeductible_R0031(double nonDeductible_R0031) {
		this.nonDeductible_R0031 = nonDeductible_R0031;
	}
	public double getNonDeductible_R004() {
		return nonDeductible_R004;
	}
	public void setNonDeductible_R004(double nonDeductible_R004) {
		this.nonDeductible_R004 = nonDeductible_R004;
	}
	public double getNonDeductible_R008() {
		return nonDeductible_R008;
	}
	public void setNonDeductible_R008(double nonDeductible_R008) {
		this.nonDeductible_R008 = nonDeductible_R008;
	}
	public double getNotDeductible() {
		return notDeductible;
	}
	public void setNotDeductible(double notDeductible) {
		this.notDeductible = notDeductible;
	}
	public double getTotle() {
		return totle;
	}
	public void setTotle(double totle) {
		this.totle = totle;
	}
	public Double getDicount() {
		return dicount;
	}
	public void setDicount(Double dicount) {
		this.dicount = dicount;
	}
	public String getR001() {
		return r001;
	}
	public void setR001(String r001) {
		this.r001 = r001;
	}
	public double getR001_premium() {
		return r001_premium;
	}
	public void setR001_premium(double r001_premium) {
		this.r001_premium = r001_premium;
	}
	public String getR002() {
		return r002;
	}
	public void setR002(String r002) {
		this.r002 = r002;
	}
	public double getR002_premium() {
		return r002_premium;
	}
	public void setR002_premium(double r002_premium) {
		this.r002_premium = r002_premium;
	}
	public String getR0030() {
		return r0030;
	}
	public void setR0030(String r0030) {
		this.r0030 = r0030;
	}
	public double getR0030_premium() {
		return r0030_premium;
	}
	public void setR0030_premium(double r0030_premium) {
		this.r0030_premium = r0030_premium;
	}
	public String getR0031() {
		return r0031;
	}
	public void setR0031(String r0031) {
		this.r0031 = r0031;
	}
	public double getR0031_premium() {
		return r0031_premium;
	}
	public void setR0031_premium(double r0031_premium) {
		this.r0031_premium = r0031_premium;
	}
	public String getR004() {
		return r004;
	}
	public void setR004(String r004) {
		this.r004 = r004;
	}
	public double getR004_premium() {
		return r004_premium;
	}
	public void setR004_premium(double r004_premium) {
		this.r004_premium = r004_premium;
	}
	public String getR006() {
		return r006;
	}
	public void setR006(String r006) {
		this.r006 = r006;
	}
	public String getR006_type() {
		return r006_type;
	}
	public void setR006_type(String r006_type) {
		this.r006_type = r006_type;
	}
	public String getR008() {
		return r008;
	}
	public void setR008(String r008) {
		this.r008 = r008;
	}
	public double getR008_premium() {
		return r008_premium;
	}
	public void setR008_premium(double r008_premium) {
		this.r008_premium = r008_premium;
	}
	public String getSclar() {
		return sclar;
	}
	public void setSclar(String sclar) {
		this.sclar = sclar;
	}
	public Double getSclar_premium() {
		return sclar_premium;
	}
	public void setSclar_premium(Double sclar_premium) {
		this.sclar_premium = sclar_premium;
	}
	public String getNR001() {
		return NR001;
	}
	public void setNR001(String nR001) {
		NR001 = nR001;
	}
	public String getNR002() {
		return NR002;
	}
	public void setNR002(String nR002) {
		NR002 = nR002;
	}
	public String getNR0030() {
		return NR0030;
	}
	public void setNR0030(String nR0030) {
		NR0030 = nR0030;
	}
	public String getNR0031() {
		return NR0031;
	}
	public void setNR0031(String nR0031) {
		NR0031 = nR0031;
	}
	public String getNR004() {
		return NR004;
	}
	public void setNR004(String nR004) {
		NR004 = nR004;
	}
	public String getNR008() {
		return NR008;
	}
	public void setNR008(String nR008) {
		NR008 = nR008;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getCarMenuCode() {
		return carMenuCode;
	}
	public void setCarMenuCode(String carMenuCode) {
		this.carMenuCode = carMenuCode;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
}
