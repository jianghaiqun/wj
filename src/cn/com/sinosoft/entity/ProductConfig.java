package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ProductConfig")
public class ProductConfig extends BaseEntity {

	private static final long serialVersionUID = -4273076245961089802L;
	private String riskCode;
	private String comCode;
	private String type;
	private String flag;

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
}
