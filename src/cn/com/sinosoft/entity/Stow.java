package cn.com.sinosoft.entity;
/**
 * 
 * @author wugq
 *
 */
public class Stow {
	public static final int LIST_COUNT = 10; 
	private String ProductName;
	private String InsureName;
	private String IsPublish;
	private String HtmlPath;
	private String createDate;
	private String productGenera;
	private String id;
	private String productId;
	private String adaptPeopleInfoV3;
	// 原价
	private String basePrem;
	// 折后价
	private String disPrem;
	//同比收藏价格便宜了
	private String cheapPrem;
	
	public String getCheepPrem() {
		return cheapPrem;
	}
	public void setCheepPrem(String cheepPrem) {
		this.cheapPrem = cheepPrem;
	}
	private String configFlag;//配置标记，如果是'1'表示是使用OrderConfigAction处理
	
	public String getConfigFlag() {
		return configFlag;
	}
	public void setConfigFlag(String configFlag) {
		this.configFlag = configFlag;
	}
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
	public void setProductGenera(String productGenera) {
		this.productGenera = productGenera;
	}
	public String getProductGenera() {
		return productGenera;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductId() {
		return productId;
	}
	public String getAdaptPeopleInfoV3() {
		return adaptPeopleInfoV3;
	}
	public void setAdaptPeopleInfoV3(String adaptPeopleInfoV3) {
		this.adaptPeopleInfoV3 = adaptPeopleInfoV3;
	}
	public String getBasePrem() {
		return basePrem;
	}
	public void setBasePrem(String basePrem) {
		this.basePrem = basePrem;
	}
	public String getDisPrem() {
		return disPrem;
	}
	public void setDisPrem(String disPrem) {
		this.disPrem = disPrem;
	}
	
}
