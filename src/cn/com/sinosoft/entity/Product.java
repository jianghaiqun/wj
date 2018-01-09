package cn.com.sinosoft.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.MapKey;

import cn.com.sinosoft.bean.ProductImage;
import cn.com.sinosoft.util.SystemConfigUtil;

/**
 * 实体类 - 商品
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTA422025AB3BAEE5940EB4488D12B6691
 * ============================================================================
 */

@Entity
public class Product extends BaseEntity {

	private static final long serialVersionUID = 4858058186018438872L;
	
	// 重量单位（克、千克、吨）
	public enum WeightUnit {
		g, kg, t
	}

	public static final int MAX_BEST_PRODUCT_LIST_COUNT = 20;// 精品商品列表最大商品数
	public static final int MAX_NEW_PRODUCT_LIST_COUNT = 20;// 新品商品列表最大商品数
	public static final int MAX_HOT_PRODUCT_LIST_COUNT = 20;// 热销商品列表最大商品数
	public static final int DEFAULT_PRODUCT_LIST_PAGE_SIZE = 12;// 商品列表默认每页显示数
	
	private String productSn;// 货号
	private String name;// 商品名称
	private BigDecimal price;// 商品价格
	private BigDecimal marketPrice;// 市场价格
	private Double weight = 0d;// 商品重量
	private WeightUnit weightUnit;// 重量单位
	private Integer store;// 商品库存数量
	private Integer freezeStore;// 被占用库存数
	private Integer point;// 积分
	private Boolean isMarketable;// 是否上架
	private Boolean isBest;// 是否为精品商品
	private Boolean isNew;// 是否为新品商品
	private Boolean isHot;// 是否为热销商品
	private String description;// 投保须知
	private String compensation;// 理赔须知
//	private String safeClause;// 保障条款
	
	private String metaKeywords;// 页面关键词
	private String metaDescription;// 页面描述
	private String htmlFilePath;// HTML静态文件路径
	private String szHtmlFilePath;// HTML静态文件路径
	private String productImageListStore;// 商品图片路径存储
	private String duty;	//新添加商品保险条款
	private String app;	//新添加理赔申请书下载
	private String professionalPath;// 商品专家点评 存储【文章id】
	private String videoPath;// 商品视频介绍 存储【文章id】
	
	
	private ProductCategory productCategory;// 商品分类
	private Brand brand;// 保险公司简介
	private ProductType productType;// 商品类型
	private Map<ProductAttribute, String> productattrib;// 商品属性存储
	private ProductInsType productInsType;// 商品类型
	private Map<ProductInsAttribute, String> productInsattrib;// 商品属性存储
	private Set<Member> favoriteMemberSet; // 收藏夹会员
	private Set<CartItem> cartItemSet;// 购物车项
//	private Set<OrderItem> orderItemSet;// 订单项
	private Set<DeliveryItem> deliveryItemSet;// 物流项
	//商城后加
	private Set<Channel> channelSet;//渠道
	private String crowd;// 适合人群
	private String timeLimit;//保障期限
	private String specialty;//产品特点
	private String benefit;//保障利益
	
	
	public String getProfessionalPath() {
		return professionalPath;
	}

	public void setProfessionalPath(String professionalPath) {
		this.professionalPath = professionalPath;
	}
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	@Column(nullable = false, unique = true)
	public String getProductSn() {
		return productSn;
	}

	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getPrice() {
		return price;
	}

	// 精度处理
	public void setPrice(BigDecimal price) {
		this.price = SystemConfigUtil.getPriceScaleBigDecimal(price);
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	// 精度处理
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = SystemConfigUtil.getPriceScaleBigDecimal(marketPrice);
	}
	
	@Column(nullable = false)
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Enumerated
	@Column(nullable = false)
	public WeightUnit getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(WeightUnit weightUnit) {
		this.weightUnit = weightUnit;
	}
	
	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}
	
	@Column(nullable = false)
	public Integer getFreezeStore() {
		return freezeStore;
	}

	public void setFreezeStore(Integer freezeStore) {
		this.freezeStore = freezeStore;
	}

	@Column(nullable = false)
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		if (point == null || point < 0) {
			point = 0;
		}
		this.point = point;
	}

	
	@Column(nullable = false)
	public Boolean getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	
	@Column(nullable = false)
	public Boolean getIsBest() {
		return isBest;
	}

	public void setIsBest(Boolean isBest) {
		this.isBest = isBest;
	}

	
	@Column(nullable = false)
	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	
	@Column(nullable = false)
	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}
	
	@Column(length = 10000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 2011.11.21 update
	 * 理赔须知
	 */
	@Column(length = 10000)
	public String getCompensation() {
		return compensation;
	}

	public void setCompensation(String compensation) {
		this.compensation = compensation;
	}

	public String getCrowd() {
		return crowd;
	}

	public void setCrowd(String crowd) {
		this.crowd = crowd;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getBenefit() {
		return benefit;
	}

	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}

	@Column(length = 5000)
	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	@Column(length = 5000)
	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}
	
	@Column(nullable = false, updatable = false)
	public String getHtmlFilePath() {
		return htmlFilePath;
	}

	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}
	
	@Column(nullable = false, updatable = false)
	public String getSzHtmlFilePath() {
		return szHtmlFilePath;
	}

	public void setSzHtmlFilePath(String szHtmlFilePath) {
		this.szHtmlFilePath = szHtmlFilePath;
	}

	@Column(length = 10000)
	public String getProductImageListStore() {
		return productImageListStore;
	}

	public void setProductImageListStore(String productImageListStore) {
		this.productImageListStore = productImageListStore;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	public ProductInsType getProductInsType() {
		return productInsType;
	}
	
	public void setProductInsType(ProductInsType productInsType) {
		this.productInsType = productInsType;
	}
	@CollectionOfElements
	@MapKey(targetElement = ProductAttribute.class)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade(value = { CascadeType.DELETE })
	public Map<ProductAttribute, String> getProductattrib() {
		return productattrib;
	}

	public void setProductattrib(Map<ProductAttribute, String> productattrib) {
		this.productattrib = productattrib;
	}
//=================================================================================s

	@CollectionOfElements
	@MapKey(targetElement = ProductInsAttribute.class)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade(value = { CascadeType.DELETE })
	public Map<ProductInsAttribute, String> getProductInsattrib() {
		return productInsattrib;
	}
	
	public void setProductInsattrib(Map<ProductInsAttribute, String> productInsattrib) {
		this.productInsattrib = productInsattrib;
	}
//============================================================================================================
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="favoriteProductSet")
	public Set<Member> getFavoriteMemberSet() {
		return favoriteMemberSet;
	}

	public void setFavoriteMemberSet(Set<Member> favoriteMemberSet) {
		this.favoriteMemberSet = favoriteMemberSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	@Cascade(value = { CascadeType.DELETE })
	public Set<CartItem> getCartItemSet() {
		return cartItemSet;
	}

	public void setCartItemSet(Set<CartItem> cartItemSet) {
		this.cartItemSet = cartItemSet;
	}
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
//	public Set<OrderItem> getOrderItemSet() {
//		return orderItemSet;
//	}

//	public void setOrderItemSet(Set<OrderItem> orderItemSet) {
//		this.orderItemSet = orderItemSet;
//	}

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	public Set<DeliveryItem> getDeliveryItemSet() {
		return deliveryItemSet;
	}

	public void setDeliveryItemSet(Set<DeliveryItem> deliveryItemSet) {
		this.deliveryItemSet = deliveryItemSet;
	}
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@OrderBy("name asc")
	public Set<Channel> getChannelSet() {
		return channelSet;
	}

	public void setChannelSet(Set<Channel> channelSet) {
		this.channelSet = channelSet;
	}
	// 获取商品图片
	@SuppressWarnings("unchecked")
	@Transient
	public List<ProductImage> getProductImageList() {
		if (StringUtils.isEmpty(productImageListStore)) {
			return null;
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(ProductImage.class);
		JSONArray jsonArray = JSONArray.fromObject(productImageListStore);
		return (List<ProductImage>) JSONSerializer.toJava(jsonArray, jsonConfig);
	}
	
	// 设置商品图片
	@Transient
	public void setProductImageList(List<ProductImage> productImageList) {
		if (productImageList == null || productImageList.size() == 0) {
			productImageListStore = null;
			return;
		}
		JSONArray jsonArray = JSONArray.fromObject(productImageList);
		productImageListStore = jsonArray.toString();
	}
	
/*/====================添加保险条款===========================================
	// 获取条款图片
	@SuppressWarnings("unchecked")
	@Transient
	public List<DutyDownLoad> getDutyDownLoadList() {
		if (StringUtils.isEmpty(dutyDownLoadPath)) {
			return null;
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(DutyDownLoad.class);
		JSONArray jsonArray = JSONArray.fromObject(dutyDownLoadPath);
		return (List<DutyDownLoad>) JSONSerializer.toJava(jsonArray, jsonConfig);
	}
	
	// 设置商品条款图片
	@Transient
	public void setDutyDownLoadList(List<DutyDownLoad> dutyDownLoadList) {
		if (dutyDownLoadList == null || dutyDownLoadList.size() == 0) {
			dutyDownLoadPath = null;
			return;
		}
		JSONArray jsonArray = JSONArray.fromObject(dutyDownLoadList);
		dutyDownLoadPath = jsonArray.toString();
	}
//====================添加保险条款===========================================*/
	// 获取属性
	@SuppressWarnings("unchecked")
	@Transient
	public Map<ProductAttribute, List<String>> getProductAttributeMap() {
		if (productattrib == null || productattrib.size() == 0) {
			return null;
		}
		Map<ProductAttribute, List<String>> productAttributeMap = new HashMap<ProductAttribute, List<String>>();
		for (ProductAttribute productAttribute : productattrib.keySet()) {
			String productAttributeValueStore = productattrib.get(productAttribute);
			if (StringUtils.isNotEmpty(productAttributeValueStore)) {
				JSONArray jsonArray = JSONArray.fromObject(productattrib.get(productAttribute));
				productAttributeMap.put(productAttribute, (List<String>) JSONSerializer.toJava(jsonArray));
			} else {
				productAttributeMap.put(productAttribute, null);
			}
		}
		return productAttributeMap;
	}

	// 设置商品属性
	@Transient
	public void setProductAttributeMap(Map<ProductAttribute, List<String>> productAttributeMap) {
		if (productAttributeMap == null || productAttributeMap.size() == 0) {
			productattrib = null;
			return;
		}
		Map<ProductAttribute, String> productattrib = new HashMap<ProductAttribute, String>();
		for (ProductAttribute productAttribute : productAttributeMap.keySet()) {
			List<String> productAttributeValueList = productAttributeMap.get(productAttribute);
			if (productAttributeValueList != null && productAttributeValueList.size() > 0) {
				JSONArray jsonArray = JSONArray.fromObject(productAttributeValueList);
				productattrib.put(productAttribute, jsonArray.toString());
			} else {
				productattrib.put(productAttribute, "");
			}
		}
		this.productattrib = productattrib;
	}
	// 获取属性Ins
	@SuppressWarnings("unchecked")
	@Transient
	public Map<ProductInsAttribute, List<String>> getProductInsAttributeMap() {
		if (productInsattrib == null || productInsattrib.size() == 0) {
			return null;
		}
		Map<ProductInsAttribute, List<String>> productInsAttributeMap = new HashMap<ProductInsAttribute, List<String>>();
		for (ProductInsAttribute productInsAttribute : productInsattrib.keySet()) {
			String productInsAttributeValueStore = productInsattrib.get(productInsAttribute);
			if (StringUtils.isNotEmpty(productInsAttributeValueStore)) {
				JSONArray jsonArray = JSONArray.fromObject(productInsattrib.get(productInsAttribute));
				productInsAttributeMap.put(productInsAttribute, (List<String>) JSONSerializer.toJava(jsonArray));
			} else {
				productInsAttributeMap.put(productInsAttribute, null);
			}
		}
		return productInsAttributeMap;
	}
	
	// 设置商品属性Ins
	@Transient
	public void setProductInsAttributeMap(Map<ProductInsAttribute, List<String>> productInsAttributeMap) {
		if (productInsAttributeMap == null || productInsAttributeMap.size() == 0) {
			productInsattrib = null;
			return;
		}
		Map<ProductInsAttribute, String> productInsattrib = new HashMap<ProductInsAttribute, String>();
		for (ProductInsAttribute productInsAttribute : productInsAttributeMap.keySet()) {
			List<String> productInsAttributeValueList = productInsAttributeMap.get(productInsAttribute);
			if (productInsAttributeValueList != null && productInsAttributeValueList.size() > 0) {
				JSONArray jsonArray = JSONArray.fromObject(productInsAttributeValueList);
				productInsattrib.put(productInsAttribute, jsonArray.toString());
			} else {
				productInsattrib.put(productInsAttribute, "");
			}
		}
		this.productInsattrib = productInsattrib;
	}
	
	/**
	 * 根据商品图片ID获取商品图片，未找到则返回null
	 * 
	 * @param ProductImage
	 *            ProductImage对象
	 */
	@Transient
	public ProductImage getProductImage(String productImageId) {
		List<ProductImage> productImageList = getProductImageList();
		for (ProductImage productImage : productImageList) {
			if (StringUtils.equals(productImageId, productImage.getId())) {
				return productImage;
			}
		}
		return null;
	}
/*/==========================保险条款======================================	
	@Transient
	public DutyDownLoad getDutyDownLoad(String dutyDownLoadId) {
		List<DutyDownLoad> dutyDownLoadList = getDutyDownLoadList();
		for (DutyDownLoad dutyDownLoad : dutyDownLoadList) {
			if (StringUtils.equals(dutyDownLoadId, dutyDownLoad.getId())) {
				return dutyDownLoad;
			}
		}
		return null;
	}*/
	
	/**
	 * 商品是否缺货
	 */
	@Transient
	public boolean getIsOutOfStock() {
		if (store != null && freezeStore >= store) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 获取优惠价格，若member对象为null则返回原价格
	 */
	@Transient
	public BigDecimal getPreferentialPrice(Member member) {
		if (member != null) {
			BigDecimal preferentialPrice = price.multiply(new BigDecimal(member.getMemberRank().getPreferentialScale().toString()).divide(new BigDecimal("100")));
			return SystemConfigUtil.getPriceScaleBigDecimal(preferentialPrice);
		} else {
			return price;
		}
	}
	

}