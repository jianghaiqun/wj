package cn.com.sinosoft.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Bean类 - 礼品相关配置
 * ============================================================================
 *  
 *  
 *  
 * 
 * KEY: SINOSOFTB9BA734EE584276939CE685A225A0330
 * ============================================================================
 */

public class PresentConfig {
	
	// 货币种类（人民币、美元、欧元、英磅、加拿大元、澳元、卢布、港币、新台币、韩元、新加坡元、新西兰元、日元、马元、瑞士法郎、瑞典克朗、丹麦克朗、兹罗提、挪威克朗、福林、捷克克朗、葡币）
	public enum CurrencyType {
		CNY, USD, EUR, GBP, CAD, AUD, RUB, HKD, TWD, KRW, SGD, NZD, JPY, MYR, CHF, SEK, DKK, PLZ, NOK, HUF, CSK, MOP
	};
	
	// 小数位精确方式（四舍五入、向上取整、向下取整）
	public enum RoundType {
		roundHalfUp, roundUp, roundDown
	}
	
	// 库存预占时间点（下订单、订单付款、订单发货）
	public enum StoreFreezeTime {
		order, payment, ship
	}
	
	// 水印位置（无、左上、右上、居中、左下、右下）
	public enum WatermarkPosition {
		no, topLeft, topRight, center, bottomLeft, bottomRight
	}
	
	// 积分获取方式（禁用积分获取、按订单总额计算、为商品单独设置）
	public enum PointType {
		disable, orderAmount, presentSet
	}

	public static final String HOT_SEARCH_SEPARATOR = ",";// 热门搜索分隔符
	public static final String EXTENSION_SEPARATOR = ",";// 文件扩展名分隔符

	public static final String LOGO_UPLOAD_NAME = "logo";// Logo图片文件名称(不包含扩张名)
	public static final String DEFAULT_BIG_PRESENT_IMAGE_FILE_NAME = "default_big_present_image";// 默认商品图片（大）文件名称(不包含扩展名)
	public static final String DEFAULT_SMALL_PRESENT_IMAGE_FILE_NAME = "default_small_present_image";// 默认商品图片（小）文件名称(不包含扩展名)
	public static final String DEFAULT_THUMBNAIL_PRESENT_IMAGE_FILE_NAME = "default_thumbnail_present_image";// 商品缩略图文件名称(不包含扩展名)
	public static final String WATERMARK_IMAGE_FILE_NAME = "watermark";// 水印图片文件名称(不包含扩展名)
	public static final String UPLOAD_IMAGE_DIR = "/upload/image/persent/";// 图片文件上传目录
	public static final String UPLOAD_MEDIA_DIR = "/upload/media/persent/";// 媒体文件上传目录
	public static final String UPLOAD_FILE_DIR = "/upload/file/persent/";// 其它文件上传目录

	private String systemName;// 系统名称
	private String systemVersion;// 系统版本
	private String systemDescription;// 系统描述
	private Boolean isInstalled;// 是否已安装

	private String shopName;// 网店名称
	private String shopUrl;// 网店网址
	private String shopLogo;// 网店Logo
	private String hotSearch;// 热门搜索关键词

	private String metaKeywords;// 首页页面关键词
	private String metaDescription;// 首页页面描述

	private String address;// 联系地址
	private String phone;// 联系电话
	private String zipCode;// 邮编
	private String email;// 联系email
	
	private Integer priceScale;// 商品价格精确位数
	private RoundType priceRoundType;// 商品价格精确方式
	private Integer orderScale;// 订单金额精确位数
	private RoundType orderRoundType;// 订单金额精确方式

	private String certtext;// 备案号
	private Integer storeAlertCount;// 库存报警数量
	private StoreFreezeTime storeFreezeTime;// 库存预占时间点
	private Integer uploadLimit;// 文件上传最大值,0表示无限制,单位KB
	private Boolean isLoginFailureLock; // 是否开启登录失败锁定账号功能
	private Integer loginFailureLockCount;// 同一账号允许连续登录失败的最大次数，超出次数后将锁定其账号
	private Integer loginFailureLockTime;// 账号锁定时间(单位：分钟,0表示永久锁定)
	private Boolean isRegister;// 是否开放注册

	private String watermarkImagePath; // 水印图片路径
	private WatermarkPosition watermarkPosition; // 水印位置
	private Integer watermarkAlpha;// 水印透明度

	private Integer bigPresentImageWidth;// 商品图片（大）宽度
	private Integer bigPresentImageHeight;// 商品图片（大）高度
	private Integer smallPresentImageWidth;// 商品图片（小）宽度
	private Integer smallPresentImageHeight;// 商品图片（小）高度
	private Integer thumbnailPresentImageWidth;// 商品缩略图宽度
	private Integer thumbnailPresentImageHeight;// 商品缩略图高度

	private String defaultBigPresentImagePath;// 默认商品图片（大）
	private String defaultSmallPresentImagePath;// 默认商品图片（小）
	private String defaultThumbnailPresentImagePath;// 默认缩略图

	private String allowedUploadImageExtension;// 允许上传的图片文件扩展名（为空表示不允许上传图片文件）
	private String allowedUploadMediaExtension;// 允许上传的媒体文件扩展名（为空表示不允许上传媒体文件）
	private String allowedUploadFileExtension;// 允许上传的文件扩展名（为空表示不允许上传文件）
	
	private String smtpFromMail;// 发件人邮箱
	private String smtpHost;// SMTP服务器地址
	private Integer smtpPort;// SMTP服务器端口
	private String smtpUsername;// SMTP用户名
	private String smtpPassword;// SMTP密码
	
	private PointType pointType;// 积分获取方式
	private Double pointScale;// 积分换算比率

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getSystemDescription() {
		return systemDescription;
	}

	public void setSystemDescription(String systemDescription) {
		this.systemDescription = systemDescription;
	}

	public Boolean getIsInstalled() {
		return isInstalled;
	}

	public void setIsInstalled(Boolean isInstalled) {
		this.isInstalled = isInstalled;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getHotSearch() {
		return hotSearch;
	}

	public void setHotSearch(String hotSearch) {
		this.hotSearch = hotSearch;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPriceScale() {
		return priceScale;
	}

	public void setPriceScale(Integer priceScale) {
		this.priceScale = priceScale;
	}

	public RoundType getPriceRoundType() {
		return priceRoundType;
	}

	public void setPriceRoundType(RoundType priceRoundType) {
		this.priceRoundType = priceRoundType;
	}

	public Integer getOrderScale() {
		return orderScale;
	}

	public void setOrderScale(Integer orderScale) {
		this.orderScale = orderScale;
	}

	public RoundType getOrderRoundType() {
		return orderRoundType;
	}

	public void setOrderRoundType(RoundType orderRoundType) {
		this.orderRoundType = orderRoundType;
	}

	public String getCerttext() {
		return certtext;
	}

	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}

	public Integer getStoreAlertCount() {
		return storeAlertCount;
	}

	public void setStoreAlertCount(Integer storeAlertCount) {
		this.storeAlertCount = storeAlertCount;
	}

	public StoreFreezeTime getStoreFreezeTime() {
		return storeFreezeTime;
	}

	public void setStoreFreezeTime(StoreFreezeTime storeFreezeTime) {
		this.storeFreezeTime = storeFreezeTime;
	}

	public Integer getUploadLimit() {
		return uploadLimit;
	}

	public void setUploadLimit(Integer uploadLimit) {
		this.uploadLimit = uploadLimit;
	}

	public Boolean getIsLoginFailureLock() {
		return isLoginFailureLock;
	}

	public void setIsLoginFailureLock(Boolean isLoginFailureLock) {
		this.isLoginFailureLock = isLoginFailureLock;
	}

	public Integer getLoginFailureLockCount() {
		return loginFailureLockCount;
	}

	public void setLoginFailureLockCount(Integer loginFailureLockCount) {
		this.loginFailureLockCount = loginFailureLockCount;
	}

	public Integer getLoginFailureLockTime() {
		return loginFailureLockTime;
	}

	public void setLoginFailureLockTime(Integer loginFailureLockTime) {
		this.loginFailureLockTime = loginFailureLockTime;
	}

	public Boolean getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(Boolean isRegister) {
		this.isRegister = isRegister;
	}

	public String getWatermarkImagePath() {
		return watermarkImagePath;
	}

	public void setWatermarkImagePath(String watermarkImagePath) {
		this.watermarkImagePath = watermarkImagePath;
	}

	public WatermarkPosition getWatermarkPosition() {
		return watermarkPosition;
	}

	public void setWatermarkPosition(WatermarkPosition watermarkPosition) {
		this.watermarkPosition = watermarkPosition;
	}

	public Integer getWatermarkAlpha() {
		return watermarkAlpha;
	}

	public void setWatermarkAlpha(Integer watermarkAlpha) {
		this.watermarkAlpha = watermarkAlpha;
	}

	public Integer getBigPresentImageWidth() {
		return bigPresentImageWidth;
	}

	public void setBigPresentImageWidth(Integer bigPresentImageWidth) {
		this.bigPresentImageWidth = bigPresentImageWidth;
	}

	public Integer getBigPresentImageHeight() {
		return bigPresentImageHeight;
	}

	public void setBigPresentImageHeight(Integer bigPresentImageHeight) {
		this.bigPresentImageHeight = bigPresentImageHeight;
	}

	public Integer getSmallPresentImageWidth() {
		return smallPresentImageWidth;
	}

	public void setSmallPresentImageWidth(Integer smallPresentImageWidth) {
		this.smallPresentImageWidth = smallPresentImageWidth;
	}

	public Integer getSmallPresentImageHeight() {
		return smallPresentImageHeight;
	}

	public void setSmallPresentImageHeight(Integer smallPresentImageHeight) {
		this.smallPresentImageHeight = smallPresentImageHeight;
	}

	public Integer getThumbnailPresentImageWidth() {
		return thumbnailPresentImageWidth;
	}

	public void setThumbnailPresentImageWidth(Integer thumbnailPresentImageWidth) {
		this.thumbnailPresentImageWidth = thumbnailPresentImageWidth;
	}

	public Integer getThumbnailPresentImageHeight() {
		return thumbnailPresentImageHeight;
	}

	public void setThumbnailPresentImageHeight(Integer thumbnailPresentImageHeight) {
		this.thumbnailPresentImageHeight = thumbnailPresentImageHeight;
	}

	public String getDefaultBigPresentImagePath() {
		return defaultBigPresentImagePath;
	}

	public void setDefaultBigPresentImagePath(String defaultBigPresentImagePath) {
		this.defaultBigPresentImagePath = defaultBigPresentImagePath;
	}

	public String getDefaultSmallPresentImagePath() {
		return defaultSmallPresentImagePath;
	}

	public void setDefaultSmallPresentImagePath(String defaultSmallPresentImagePath) {
		this.defaultSmallPresentImagePath = defaultSmallPresentImagePath;
	}

	public String getDefaultThumbnailPresentImagePath() {
		return defaultThumbnailPresentImagePath;
	}

	public void setDefaultThumbnailPresentImagePath(String defaultThumbnailPresentImagePath) {
		this.defaultThumbnailPresentImagePath = defaultThumbnailPresentImagePath;
	}

	public String getAllowedUploadImageExtension() {
		return allowedUploadImageExtension;
	}

	public void setAllowedUploadImageExtension(String allowedUploadImageExtension) {
		this.allowedUploadImageExtension = allowedUploadImageExtension;
	}

	public String getAllowedUploadMediaExtension() {
		return allowedUploadMediaExtension;
	}

	public void setAllowedUploadMediaExtension(String allowedUploadMediaExtension) {
		this.allowedUploadMediaExtension = allowedUploadMediaExtension;
	}

	public String getAllowedUploadFileExtension() {
		return allowedUploadFileExtension;
	}

	public void setAllowedUploadFileExtension(String allowedUploadFileExtension) {
		this.allowedUploadFileExtension = allowedUploadFileExtension;
	}

	public String getSmtpFromMail() {
		return smtpFromMail;
	}

	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public PointType getPointType() {
		return pointType;
	}

	public void setPointType(PointType pointType) {
		this.pointType = pointType;
	}

	public Double getPointScale() {
		return pointScale;
	}

	public void setPointScale(Double pointScale) {
		this.pointScale = pointScale;
	}

	// 获取热门搜索关键词集合
	public List<String> getHotSearchList() {
		return StringUtils.isNotEmpty(hotSearch) ? Arrays.asList(hotSearch.split(HOT_SEARCH_SEPARATOR)) : new ArrayList<String>();
	}

}
