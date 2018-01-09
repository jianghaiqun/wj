package cn.com.sinosoft.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
/**
 * @author LiuXin
 *
 */
@Entity
public class CarMenu extends BaseEntity{

	private static final long serialVersionUID = 1412007515129897511L;
	/**
	 * 套餐编码
	 */
	@Column(name = "CODE", length = 20)
	private String code;
	/**
	 * 套餐名称
	 */
	@Column(name = "NAME", length = 100)
	private String name;
	
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
	 * 第三责任不计免赔
	 */
	@Column(name = "NR001", length = 1)
	private String NR001;
	/**
	 * 机动车损失不计免赔
	 */
	@Column(name = "NR002", length = 1)
	private String NR002;
	/**
	 * 司机责任不计免赔
	 */
	@Column(name = "NR0030", length = 1)
	private String NR0030;
	/**
	 * 乘客责任不计免赔
	 */
	@Column(name = "NR0031", length = 1)
	private String NR0031;
	/**
	 * 盗抢险不计免赔
	 */
	@Column(name = "NR004", length = 1)
	private String NR004;
	/**
	 * 车身划痕不计免赔
	 */
	@Column(name = "NR008", length = 1)
	private String NR008;
	
	/**
	 * 套餐标志
	 */
	@Column(name = "FLAG", length = 1)
	private String flag;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
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
	public String getSclar() {
		return sclar;
	}
	public void setSclar(String sclar) {
		this.sclar = sclar;
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
	public Double getSclar_premium() {
		return sclar_premium;
	}
	public void setSclar_premium(Double sclar_premium) {
		this.sclar_premium = sclar_premium;
	}

}
