package cn.com.sinosoft.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 新礼品表 实体类
 * @author lenovo
 *
 */
@Entity
@Table(name = "GiftClassify")
public class GiftClassify extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String giftTitle;

	private String giftName;

	private String lastNum;

	private String type;

	private String logoUrl;

	private String modelType;

	private String productID;

	private String points;
	
	private String giftPrice;

	private String popularity;

	private String recommend;
	
	private Date startDate;

	private Date endDate;

	private String metaDescription;

	private String status;

	private String linkUrl;
	
	private Long orderFlag;
	
	private String flowtype;

	private String FlowSize;

	private String FuLuGoodsID;

	private String PointsExchangeType;

	private String version;

	private String isWap;
	
	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String prop5;

	private String prop6;

	private String prop7;

	private String createUser;

	private String modifyUser;

	public String getGiftTitle() {
		return giftTitle;
	}

	public void setGiftTitle(String giftTitle) {
		this.giftTitle = giftTitle;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getLastNum() {
		return lastNum;
	}

	public void setLastNum(String lastNum) {
		this.lastNum = lastNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(String giftPrice) {
		this.giftPrice = giftPrice;
	}

	public String getPopularity() {
		return popularity;
	}

	public void setPopularity(String popularity) {
		this.popularity = popularity;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Long getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(Long orderFlag) {
		this.orderFlag = orderFlag;
	}
	
	public String getFlowtype() {
		return flowtype;
	}

	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}

	public String getFlowSize() {
		return FlowSize;
	}

	public void setFlowSize(String flowSize) {
		FlowSize = flowSize;
	}

	public String getFuLuGoodsID() {
		return FuLuGoodsID;
	}

	public void setFuLuGoodsID(String fuLuGoodsID) {
		FuLuGoodsID = fuLuGoodsID;
	}

	public String getPointsExchangeType() {
		return PointsExchangeType;
	}

	public void setPointsExchangeType(String pointsExchangeType) {
		PointsExchangeType = pointsExchangeType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIsWap() {
		return isWap;
	}

	public void setIsWap(String isWap) {
		this.isWap = isWap;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
}