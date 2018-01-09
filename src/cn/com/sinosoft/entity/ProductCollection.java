package cn.com.sinosoft.entity;

import javax.persistence.Entity;

@Entity
public class ProductCollection extends BaseEntity{
	private String productID;
	private String memberID;
	private String Remark1;
	private String collectionPrice;
	
	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getRemark1() {
		return Remark1;
	}

	public void setRemark1(String remark1) {
		this.Remark1 = remark1;
	}
	
	public String getcollectionPrice() {
		return collectionPrice;
	}

	public void setcollectionPrice(String collectionPrice) {
		this.collectionPrice = collectionPrice;
	}

}
