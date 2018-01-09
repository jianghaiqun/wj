package cn.com.sinosoft.entity;

import java.io.File;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import cn.com.sinosoft.util.SystemConfigUtil;


/**
 * Bean类 - 订单项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT51EFC4FAC53E35EE0A29EEC65F82EE9C
 * ============================================================================
 */

@Entity
public class OrderItem extends BaseEntity {
	
	private static final long serialVersionUID = 5030818078599298690L;
	
	private String productId;// 商品id
	private String productName;// 商品名称
	private BigDecimal productPrice;// 商品价格
	private String productHtmlFilePath;// 商品HTML静态文件路径
	private Integer productQuantity;// 商品数量
	private Set<Information> informationSet;// 做级联删除用
	
	private String coutNo;//保单号
	private String electronicCout;//电子保单
	private File electronic;//电子保单File
	
	private Order order;// 订单
//	private Product product;// 商品
	/**
	 * valid
	 * orderItem状态
	 * 默认为valid = 0 ，即：无效状态
	 * 在用户填写完所有orderItem中的投保信息后,将valid = 1
	 */
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	private Boolean valid = false;// orderItem状态 
	

	@Column(updatable = false, nullable = false)
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	
	// 精度处理
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = SystemConfigUtil.getPriceScaleBigDecimal(productPrice);
	}
	
	@Column(nullable = false, updatable = false)
	public String getProductHtmlFilePath() {
		return productHtmlFilePath;
	}
	
	public void setProductHtmlFilePath(String productHtmlFilePath) {
		this.productHtmlFilePath = productHtmlFilePath;
	}
	
	@Column(nullable = false)
	public Integer getProductQuantity() {
		return productQuantity;
	}
	
	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
//配置与information的级联删除关系 开始
	@OneToMany(mappedBy = "orderItem", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<Information> getInformationSet() {
		return informationSet;
	}
	
	public void setInformationSet(Set<Information> informationSet) {
		this.informationSet = informationSet;
	}
//配置与information的级联删除关系 结束
	
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	public Product getProduct() {
//		return product;
//	}
//	
//	public void setProduct(Product product) {
//		this.product = product;
//	}
	
	// 获取小计价格
	@Transient
	public BigDecimal getSubtotalPrice() {
		BigDecimal subtotalPrice = productPrice.multiply(new BigDecimal(productQuantity.toString()));
		return SystemConfigUtil.getOrderScaleBigDecimal(subtotalPrice);
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getCoutNo() {
		return coutNo;
	}

	public void setCoutNo(String coutNo) {
		this.coutNo = coutNo;
	}

	public String getElectronicCout() {
		return electronicCout;
	}

	public void setElectronicCout(String electronicCout) {
		this.electronicCout = electronicCout;
	}

	public File getElectronic() {
		return electronic;
	}

	public void setElectronic(File electronic) {
		this.electronic = electronic;
	}
}