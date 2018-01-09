package cn.com.sinosoft.entity;

import javax.persistence.Entity;
/**
 * 
 * @author wugq  退款基础信息表
 *
 */
@Entity
public class RefundBase extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6658725571014643656L;
	private String cmdId;//交易方式
	private String version;//版本号
	private String merId;//商户号
	private String bgRetUrl;//支付平台 异步返回
    private String geteWay;//支付网关
    private String returnType;//支付方式
    private String cert;//密钥
    private String formUrl;
	public String getCmdId() {
		return cmdId;
	}

	public void setCmdId(String cmdId) {
		this.cmdId = cmdId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getBgRetUrl() {
		return bgRetUrl;
	}

	public void setBgRetUrl(String bgRetUrl) {
		this.bgRetUrl = bgRetUrl;
	}

	public void setGeteWay(String geteWay) {
		this.geteWay = geteWay;
	}

	public String getGeteWay() {
		return geteWay;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public String getCert() {
		return cert;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public String getFormUrl() {
		return formUrl;
	}

}
