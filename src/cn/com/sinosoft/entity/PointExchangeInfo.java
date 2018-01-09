/**
 * 
 */
package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PointExchangeInfo")
public class PointExchangeInfo extends BaseEntity {

	private String productName;

	private String productid;

	private String type;

	private String points;

	private String memberid;

	private String status;

	private String orderSn;
	
	private String mobileNo;

	private String fuLuOrderSn;

	private String fuLuGoodsID;

	private String exchangeQuantity;

	private String cardNo;

	private String cardKey;

	private String cardDeadline;
	
	private String purchasePrice;
	
	private String fuLuOrderStatus;
	
	private String goodsStockID;
	
	private String giftClassifyID;
	
	private String wrongMassage;

	private String prop1;//订单已取消状态：Y

	private String prop2;//渠道

	private String prop3;//福禄二次充值订单号  ordersn_N

	private String prop4;

	private String prop5;

	private String prop6;

	private String prop7;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	public String getFuLuOrderSn() {
		return fuLuOrderSn;
	}

	public void setFuLuOrderSn(String fuLuOrderSn) {
		this.fuLuOrderSn = fuLuOrderSn;
	}

	public String getFuLuGoodsID() {
		return fuLuGoodsID;
	}

	public void setFuLuGoodsID(String fuLuGoodsID) {
		this.fuLuGoodsID = fuLuGoodsID;
	}

	public String getExchangeQuantity() {
		return exchangeQuantity;
	}

	public void setExchangeQuantity(String exchangeQuantity) {
		this.exchangeQuantity = exchangeQuantity;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardKey() {
		return cardKey;
	}

	public void setCardKey(String cardKey) {
		this.cardKey = cardKey;
	}

	public String getCardDeadline() {
		return cardDeadline;
	}

	public void setCardDeadline(String cardDeadline) {
		this.cardDeadline = cardDeadline;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getFuLuOrderStatus() {
		return fuLuOrderStatus;
	}

	public void setFuLuOrderStatus(String fuLuOrderStatus) {
		this.fuLuOrderStatus = fuLuOrderStatus;
	}

	public String getWrongMassage() {
		return wrongMassage;
	}

	public void setWrongMassage(String wrongMassage) {
		this.wrongMassage = wrongMassage;
	}

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public String getProp2() {
		return prop2;
	}

	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}

	public String getProp3() {
		return prop3;
	}

	public void setProp3(String prop3) {
		this.prop3 = prop3;
	}

	public String getProp4() {
		return prop4;
	}

	public void setProp4(String prop4) {
		this.prop4 = prop4;
	}

	public String getProp5() {
		return prop5;
	}

	public void setProp5(String prop5) {
		this.prop5 = prop5;
	}

	public String getProp6() {
		return prop6;
	}

	public void setProp6(String prop6) {
		this.prop6 = prop6;
	}

	public String getProp7() {
		return prop7;
	}

	public void setProp7(String prop7) {
		this.prop7 = prop7;
	}

	public String getGoodsStockID() {
		return goodsStockID;
	}

	public void setGoodsStockID(String goodsStockID) {
		this.goodsStockID = goodsStockID;
	}

	public String getGiftClassifyID() {
		return giftClassifyID;
	}

	public void setGiftClassifyID(String giftClassifyID) {
		this.giftClassifyID = giftClassifyID;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
}
