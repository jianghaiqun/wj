package cn.com.sinosoft.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import cn.com.sinosoft.entity.Product.WeightUnit;
import cn.com.sinosoft.util.SystemConfigUtil;


/**
 * Bean类 - 兑换记录项
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
public class PresentObtain extends BaseEntity {
	
	private static final long serialVersionUID = 5030818078599298690L;
	
	private String presentName;// 礼品名称
	private String presentHtmlFilePath;// 礼品HTML静态文件路径
	private Integer presentQuantity;// 礼品数量
	private Integer deliveryQuantity;// 发货数量
	private Integer point;// 积分
	private BigDecimal costPrice;// 成本价格
	
	private Double productWeight;// 礼品重量
	private WeightUnit productWeightUnit;// 礼品重量单位
	private Integer productTotalQuantity;// 礼品总数
	private String shipName;// 收货人姓名
	private String shipArea;// 收货地区
	private String shipAreaPath;// 收货地区路径
	private String shipAddress;// 收货地址
	private String shipZipCode;// 收货邮编
	private String shipPhone;// 收货电话
	private String shipMobile;// 收货手机
	private String memo;// 附言
	
	private Member member;// 会员
	
	public void setPresentName(String presentName) {
		this.presentName = presentName;
	}
	
	public String getPresentName() {
		return presentName;
	}


	@Column(nullable = false, updatable = false)
	public String getPresentHtmlFilePath() {
		return presentHtmlFilePath;
	}
	
	public void setPresentHtmlFilePath(String presentHtmlFilePath) {
		this.presentHtmlFilePath = presentHtmlFilePath;
	}
	
	@Column(nullable = false)
	public Integer getPresentQuantity() {
		return presentQuantity;
	}
	
	public void setPresentQuantity(Integer presentQuantity) {
		this.presentQuantity = presentQuantity;
	}
	
	@Column(nullable = false)
	public Integer getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Integer deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	@Column(nullable = false)
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	
//	@Column(nullable = false)
	public Double getProductWeight() {
		return productWeight;
	}
	
	public void setProductWeight(Double productWeight) {
		this.productWeight = productWeight;
	}
	
	@Enumerated
//	@Column(nullable = false)
	public WeightUnit getProductWeightUnit() {
		return productWeightUnit;
	}

	public void setProductWeightUnit(WeightUnit productWeightUnit) {
		this.productWeightUnit = productWeightUnit;
	}
	
	@Column(nullable = false)
	public Integer getProductTotalQuantity() {
		return productTotalQuantity;
	}

	public void setProductTotalQuantity(Integer productTotalQuantity) {
		this.productTotalQuantity = productTotalQuantity;
	}

//	@Column(nullable = false)
	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

//	@Column(nullable = false)
	public String getShipArea() {
		return shipArea;
	}

	public void setShipArea(String shipArea) {
		this.shipArea = shipArea;
	}

	public String getShipAreaPath() {
		return shipAreaPath;
	}

	public void setShipAreaPath(String shipAreaPath) {
		this.shipAreaPath = shipAreaPath;
	}

//	@Column(nullable = false)
	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

//	@Column(nullable = false)
	public String getShipZipCode() {
		return shipZipCode;
	}

	public void setShipZipCode(String shipZipCode) {
		this.shipZipCode = shipZipCode;
	}

	public String getShipPhone() {
		return shipPhone;
	}

	public void setShipPhone(String shipPhone) {
		this.shipPhone = shipPhone;
	}

	public String getShipMobile() {
		return shipMobile;
	}

	public void setShipMobile(String shipMobile) {
		this.shipMobile = shipMobile;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getCostPrice() {
		return costPrice;
	}

	// 精度处理
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = SystemConfigUtil.getPriceScaleBigDecimal(costPrice);
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}