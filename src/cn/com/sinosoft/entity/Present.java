package cn.com.sinosoft.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import cn.com.sinosoft.bean.PresentImage;
import cn.com.sinosoft.util.SystemConfigUtil;

/**
 * 实体类 - 礼品
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
public class Present extends BaseEntity {

	private static final long serialVersionUID = 4858058186018438872L;

	// 重量单位（克、千克、吨）
	public enum WeightUnit {
		g, kg, t
	}

	public static final int MAX_BEST_PRESENT_LIST_COUNT = 20;// 精品礼品列表最大礼品数
	public static final int MAX_NEW_PRESENT_LIST_COUNT = 20;// 新品礼品列表最大礼品数
	public static final int MAX_HOT_PRESENT_LIST_COUNT = 20;// 热销礼品列表最大礼品数
	public static final int DEFAULT_PRESENT_LIST_PAGE_SIZE = 12;// 礼品列表默认每页显示数

	// private String presentSn;// 货号
	private String name;// 礼品名称
	private BigDecimal marketPrice;// 市场价格
	private BigDecimal costPrice;// 成本价格

	private Double weight = 0d;// 礼品重量
	private WeightUnit weightUnit;// 重量单位
	private Integer store;// 礼品库存数量
	private Integer freezeStore;// 被占用库存数
	private Integer point;// 积分
	private Boolean isMarketable;// 是否上架
	private Boolean isBest;// 是否为精品礼品
	private Boolean isNew;// 是否为新品礼品
	private Boolean isHot;// 是否为热销礼品

	private String metaKeywords;// 页面关键词
	private String metaDescription;// 页面描述
	private String htmlFilePath;// HTML静态文件路径
	private String presentImageListStore;// 礼品图片路径存储
	private PresentCategory presentCategory;// 礼品分类
	private Stock stock;// 库存分类
	private Set<Member> favoriteMemberSet; // 收藏夹会员
	// private Set<PresentDeliveryItem> persentDeliveryItemSet;// 物流项
	private String listcontent;// 列表内容
	private String imageaddress; // 图片地址
	private long presentArticleID; // 图片地址
	private String catalog;//栏目
	private String actCode;//活动编码

	
	
	
	public String getActCode() {
		return actCode;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	@Column(length = 30)
	public long getPresentArticleID() {
		return presentArticleID;
	}

	public void setPresentArticleID(long presentArticleID) {
		this.presentArticleID = presentArticleID;
	}

	// 商城后加
	@Column(length = 5000)
	public String getListcontent() {
		return listcontent;
	}

	public void setListcontent(String listcontent) {
		this.listcontent = listcontent;
	}

	@Column(length = 100)
	public String getImageaddress() {
		return imageaddress;
	}

	public void setImageaddress(String imageaddress) {
		this.imageaddress = imageaddress;
	}

	
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	// 精度处理
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = SystemConfigUtil.getPriceScaleBigDecimal(marketPrice);
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
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

	
	// @Column(nullable = false)
	public Boolean getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	
	// @Column(nullable = false)
	public Boolean getIsBest() {
		return isBest;
	}

	public void setIsBest(Boolean isBest) {
		this.isBest = isBest;
	}

	
	// @Column(nullable = false)
	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	
	// @Column(nullable = false)
	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
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

	public String getHtmlFilePath() {
		return htmlFilePath;
	}

	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}

	
	@Column(length = 10000)
	public String getPresentImageListStore() {
		return presentImageListStore;
	}

	public void setPresentImageListStore(String presentImageListStore) {
		this.presentImageListStore = presentImageListStore;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public PresentCategory getPresentCategory() {
		return presentCategory;
	}

	public void setPresentCategory(PresentCategory presentCategory) {
		this.presentCategory = presentCategory;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoritePresentSet")
	public Set<Member> getFavoriteMemberSet() {
		return favoriteMemberSet;
	}

	public void setFavoriteMemberSet(Set<Member> favoriteMemberSet) {
		this.favoriteMemberSet = favoriteMemberSet;
	}

	// @OneToMany(mappedBy = "present", fetch = FetchType.LAZY)
	// public Set<PresentDeliveryItem> getPersentDeliveryItemSet() {
	// return persentDeliveryItemSet;
	// }
	//
	// public void setPersentDeliveryItemSet(Set<PresentDeliveryItem>
	// persentDeliveryItemSet) {
	// this.persentDeliveryItemSet = persentDeliveryItemSet;
	// }
	// 获取礼品图片
	@SuppressWarnings("unchecked")
	@Transient
	public List<PresentImage> getPresentImageList() {
		if (StringUtils.isEmpty(presentImageListStore)) {
			return null;
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(PresentImage.class);
		JSONArray jsonArray = JSONArray.fromObject(presentImageListStore);
		return (List<PresentImage>) JSONSerializer.toJava(jsonArray, jsonConfig);
	}

	// 设置礼品图片
	@Transient
	public void setPresentImageList(List<PresentImage> presentImageList) {
		if (presentImageList == null || presentImageList.size() == 0) {
			presentImageListStore = null;
			return;
		}
		JSONArray jsonArray = JSONArray.fromObject(presentImageList);
		presentImageListStore = jsonArray.toString();
	}

	/**
	 * 根据礼品图片ID获取礼品图片，未找到则返回null
	 * 
	 * @param PresentImage
	 *            PresentImage对象
	 */
	@Transient
	public PresentImage getPresentImage(String presentImageId) {
		List<PresentImage> presentImageList = getPresentImageList();
		for (PresentImage presentImage : presentImageList) {
			if (StringUtils.equals(presentImageId, presentImage.getId())) {
				return presentImage;
			}
		}
		return null;
	}

	/**
	 * 礼品是否缺货
	 */
	@Transient
	public boolean getIsOutOfStock() {
		if (store != null && freezeStore >= store) {
			return true;
		} else {
			return false;
		}
	}
}