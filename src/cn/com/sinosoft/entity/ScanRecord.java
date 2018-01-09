package cn.com.sinosoft.entity;

public class ScanRecord {
private String sid;
private String ProductName;
private String InsureName;
private String IsPublish;
private String HtmlPath;
private String createDate;
private String productId;
public String getProductName() {
	return ProductName;
}
public void setProductName(String productName) {
	ProductName = productName;
}
public String getInsureName() {
	return InsureName;
}
public void setInsureName(String insureName) {
	InsureName = insureName;
}
public String getIsPublish() {
	return IsPublish;
}
public void setIsPublish(String isPublish) {
	IsPublish = isPublish;
}
public String getHtmlPath() {
	return HtmlPath;
}
public void setHtmlPath(String htmlPath) {
	HtmlPath = htmlPath;
}
public String getCreateDate() {
	return createDate;
}
public void setCreateDate(String createDate) {
	this.createDate = createDate;
}
public void setSid(String sid) {
	this.sid = sid;
}
public String getSid() {
	return sid;
}
public void setProductId(String productId) {
	this.productId = productId;
}
public String getProductId() {
	return productId;
}
	
}
