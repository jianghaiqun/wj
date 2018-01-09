package cn.com.sinosoft.entity;

import javax.persistence.Entity;

/**
 * 产品录入页面实体类配置
 * 
 * @author liuxin
 * 
 */
@Entity
public class PageLayout extends BaseEntity {

	private static final long serialVersionUID = 8707875628036016747L;
	private String comCode;// 保险公司编码
	private String riskCode;// 产品小类编码
	private String returnPage;// 返回页面
	private String productId;//特殊产品id

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getReturnPage() {
		return returnPage;
	}

	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
