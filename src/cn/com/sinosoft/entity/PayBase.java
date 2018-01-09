package cn.com.sinosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 支持基础信息类
 * 
 * @author guobin
 */
@Entity
public class PayBase extends BaseEntity {
	private static final long serialVersionUID = 3655736097247923778L;
	/**
	 * 支付类型
	 */
	@Column(name = "PAYTYPE", nullable = true)
	private String payType;
	/**
	 * 版本
	 */
	@Column(name = "VERSION")
	private String version;
	/**
	 * 商户号
	 */
	@Column(name = "MERID")
	private String MerId;

	/**
	 * 页面返回地址
	 */
	@Column(name = "RETURL")
	private String RetUrl;

	/**
	 * 后台返回地址
	 */
	@Column(name = "BGRETURL")
	private String BgRetUrl;

	/**
	 * 网关号
	 */
	@Column(name = "GATEID")
	private String GateId;

	/**
	 * 密钥
	 */
	@Column(name = "CERT")
	private String Cert;

	/**
	 * 访问路径
	 */
	@Column(name = "GATEURL")
	private String GateUrl;

	/**
	 * 访问系统内部的方法
	 */
	@Column(name = "RETURNURL")
	private String ReturnUrl;

	/**
	 * 类型
	 */
	@Column(name = "PAYTYPE")
	private String Type;

	/**
	 * 图片
	 */
	@Column(name = "IMAGE")
	private String Image;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION")
	private String Description;

	/**
	 * 排序
	 */
	@Column(name = "ORDERFLAG")
	private int orderflag;

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMerId() {
		return MerId;
	}

	public void setMerId(String merId) {
		MerId = merId;
	}

	public String getRetUrl() {
		return RetUrl;
	}

	public void setRetUrl(String retUrl) {
		RetUrl = retUrl;
	}

	public String getBgRetUrl() {
		return BgRetUrl;
	}

	public void setBgRetUrl(String bgRetUrl) {
		BgRetUrl = bgRetUrl;
	}

	public String getGateId() {
		return GateId;
	}

	public void setGateId(String gateId) {
		GateId = gateId;
	}

	public String getCert() {
		return Cert;
	}

	public void setCert(String cert) {
		Cert = cert;
	}

	public String getGateUrl() {
		return GateUrl;
	}

	public void setGateUrl(String gateUrl) {
		GateUrl = gateUrl;
	}

	public String getReturnUrl() {
		return ReturnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		ReturnUrl = returnUrl;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public int getOrderflag() {
		return orderflag;
	}

	public void setOrderflag(int orderflag) {
		this.orderflag = orderflag;
	}

}
