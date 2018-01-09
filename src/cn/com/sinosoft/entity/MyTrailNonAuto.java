package cn.com.sinosoft.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class MyTrailNonAuto extends BaseEntity {

	private static final long serialVersionUID = -8269020929378655810L;
	private String memeberId;
	private String serialNumber;
	private String productType;
	
	private List<TrailProduct> trailProducts;

	public String getMemeberId() {
		return memeberId;
	}

	public void setMemeberId(String memeberId) {
		this.memeberId = memeberId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@OneToMany(mappedBy = "myTrailNonAuto", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public List<TrailProduct> getTrailProducts() {
		return trailProducts;
	}
	public void setTrailProducts(List<TrailProduct> trailProducts) {
		this.trailProducts = trailProducts;
	}

}
