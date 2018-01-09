package cn.com.sinosoft.entity;
 

/**
 * 
 * @author wugq 
 *
 */



public class ZCComment {
	
	public static final int LIST_COUNT = 10; 
	private String ProductName;
	private String InsureName;
	private String IsPublish;
	private String HtmlPath;
	private String createDate;
	private String productGenera;
	private  String title;
	private String content;
	private String url;
	private String replyContent;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
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
	public String getProductGenera() {
		return productGenera;
	}
	public void setProductGenera(String productGenera) {
		this.productGenera = productGenera;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}