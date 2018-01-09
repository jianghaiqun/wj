package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class TrailProduct extends BaseEntity {

	private static final long serialVersionUID = 595138091721347683L;
	private String serialNumber;
	private String productCode;
	private String productName;
	private String initPrem;
	private String comName;
	private MyTrailNonAuto myTrailNonAuto;

	
	@ManyToOne(fetch = FetchType.LAZY)
	public MyTrailNonAuto getMyTrailNonAuto() {
		return myTrailNonAuto;
	}

	public void setMyTrailNonAuto(MyTrailNonAuto myTrailNonAuto) {
		this.myTrailNonAuto = myTrailNonAuto;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getInitPrem() {
		return initPrem;
	}

	public void setInitPrem(String initPrem) {
		this.initPrem = initPrem;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}
}
