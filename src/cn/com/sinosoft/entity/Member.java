package cn.com.sinosoft.entity;

import cn.com.sinosoft.util.SystemConfigUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.MapKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实体类 - 会员
 * ============================================================================
 *
 *
 *
 *
 *
 *
 *  KEY:SINOSOFT0F83FA217E05DC57A1792709497F7841
 * ============================================================================
 */

@Entity
public class Member extends BaseEntity {

	private static final long serialVersionUID = 1533130686714725835L;

	public static final String LOGIN_MEMBER_ID_SESSION_NAME = "loginMemberId";// 保存登录会员ID的Session名称
	public static final String LOGIN_MEMBER_USERNAME_COOKIE_NAME = "loginMemberUsername";// 保存登录会员用户名的Cookie名称
	public static final String LOGIN_MEMBER = "loginMember";// 保存登录会员用户名的Cookie名称
	public static final String LOGIN_REDIRECTION_URL_SESSION_NAME = "redirectionUrl";// 保存登录来源URL的Session名称
	public static final String PASSWORD_RECOVER_KEY_SEPARATOR = "_";// 密码找回Key分隔符
	public static final int PASSWORD_RECOVER_KEY_PERIOD = 10080;// 密码找回Key有效时间（单位：分钟）
	public static final String LOGIN_MEMBER_LOGINFLAG = "loginFlag";// 保存登录会员用户名的Cookie名称

	private String hasUpdate;//有修改的字段
	private String isfirstMC;//是否第一次进入会员中心
	private String fromWap;//会员来源 wap：wap站 tb：淘宝
	private String username;// 用户名
	private String password;// 密码
	private String email="";// E-mail
	private String safeQuestion;// 密码保护问题
	private String safeAnswer;// 密码保护问题答案
	private Integer point;// 积分
	private BigDecimal deposit;// 预存款
	private String isAccountEnabled;// 账号是否启用
	private String isAccountLocked;// 账号是否锁定
	private Integer loginFailureCount;// 连续登录失败的次数
	private Date lockedDate;// 账号锁定日期
	private String registerIp;// 注册IP
	private String loginIp;// 最后登录IP
	private Date loginDate;// 最后登录日期
	private String passwordRecoverKey;// 密码找回Key
	private String szMemberUserId;// 移动商城用户id
	//吴高强2012-06-20添加开始
	private String mobileNO="";// 手机号
	private String QQNO;// QQ号
	private String realName;// 真实姓名
	private String IDType;// ID类型
	private String IDNO;// ID号
	private String sex;// 性别
	private String birthday;// 生日
	private String location;// 所在地
	private String address;// 联系地址
	private String zipcode;// 邮政编码
	private String personalURL;// 个人网址
	private String telephoneNO;// 电话号码
	private String faxNO;//传真号
	private String marriageStatus;//婚姻状况
	private String industryType;//行业类型
	private String position;//职位
	private Set<MemberHobby> hobby;//爱好
	private String VIPFrom;//会员来源
	private String VIPChannel;//会员渠道
	private String VIPType;//会员类型
	private String registerType;//注册方式
	private Integer expiricalValue;//经验值
	private String isEmailBinding;//邮箱是否绑定
	private String isMobileNOBinding;//手机是否绑定
	private Integer currentValidatePoint;//当前可用积分
	private Integer usedPoint;//已用积分
	private String pictruePath;//图片路径
	private String fullDegree;//信息完整度
	//吴高强2012-06-20添加结束
	private MemberRank memberRank;// 会员等级
	private Map<MemberAttribute, String> memberAttributeMapStore;// 会员注册项储存
	private Set<Receiver> receiverSet;// 收货地址
	private Set<Product> favoriteProductSet;// 收藏夹商品
	private Set<CartItem> cartItemSet;// 购物车项
	private Set<Message> inboxMessageSet;// 收件箱消息
	private Set<Message> outboxMessageSet;// 发件箱消息
//	private Set<Order> orderSet;// 订单
	private Set<Deposit> depositSet;// 预存款
	private Set<Present> favoritePresentSet;// 收藏夹礼品
//	private Set<VIPBrowserHistory> VIPBrowserHistory;// 浏览记录
	private Date verifyEmailSendDate ;// 验证邮件发送时间  用于判断邮件有效期 add by fhz 20130125
	private Date retrieveEmailSendDate ;// 找回邮件发送时间  用于判断邮件有效期 add by fhz 20130125
	private String retrieveEmailvalid;//找回密码有效性连接不能超过两次
	private String headPicPath;

    private BindInfoForLogin mBindInfoForLogin;
    private Set<SDRelationAppnt> sdrelationappntSet;//投保人信息
    private Set<SDRelationRecognizee> sdrelationrecognizeeSet;//被保人信息

    private Integer preLoginPoints;// 上次登录显示的积分

    private String recommendMemId;// 推荐会员ID

    private String recommendFlag;// 推荐标识 Y:已推荐

    private Integer recommendRegPoints;// 推荐好友注册得积分数

    private Integer recommendBuyPoints;// 推荐好友购买得积分数

    private Integer overduePoints;// 过期的积分数

    private Integer aboutToExpirePoints;// 即将过期的积分数

    private String aboutToExpireDate;// 过期日期

    private String grade;// 会员等级

    private String consumeAmount;// 消费金额

    private Integer buyCount;// 购买次数

    private Date loginDateAfterUngrade;// 升级后初次登录日期

    private String isBuy;// 是否购买

    private String accuEndDate;// 统计会员有效订单数截止时间

    private String birthYear;// 已享受生日月最新年月

    private String vipFlag;// vip标识

    private String version;// 版本号

    public String getFromWap() {
		return fromWap;
	}

	public void setFromWap(String fromWap) {
		this.fromWap = fromWap;
	}

    @OneToMany(mappedBy = "mMember", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
    public Set<SDRelationRecognizee> getSdrelationrecognizeeSet() {
		return sdrelationrecognizeeSet;
	}

	public void setSdrelationrecognizeeSet(
			Set<SDRelationRecognizee> sdrelationrecognizeeSet) {
		this.sdrelationrecognizeeSet = sdrelationrecognizeeSet;
	}

	@OneToOne(fetch = FetchType.LAZY)
	public BindInfoForLogin getmBindInfoForLogin() {
		return mBindInfoForLogin;
	}

	public void setmBindInfoForLogin(BindInfoForLogin mBindInfoForLogin) {
		this.mBindInfoForLogin = mBindInfoForLogin;
	}
	@OneToMany(mappedBy = "mMember", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<SDRelationAppnt> getSdrelationappntSet() {
		return sdrelationappntSet;
	}

	public void setSdrelationappntSet(Set<SDRelationAppnt> sdrelationappntSet) {
		this.sdrelationappntSet = sdrelationappntSet;
	}

	public String getRetrieveEmailvalid() {
		return retrieveEmailvalid;
	}

	public void setRetrieveEmailvalid(String retrieveEmailvalid) {
		this.retrieveEmailvalid = retrieveEmailvalid;
	}

	public Date getRetrieveEmailSendDate() {
		return retrieveEmailSendDate;
	}

	public void setRetrieveEmailSendDate(Date retrieveEmailSendDate) {
		this.retrieveEmailSendDate = retrieveEmailSendDate;
	}

	public Date getVerifyEmailSendDate() {
		return verifyEmailSendDate;
	}

	public void setVerifyEmailSendDate(Date verifyEmailSendDate) {
		this.verifyEmailSendDate = verifyEmailSendDate;
	}

	public String getMobileNO() {
		return mobileNO;
	}

	public void setMobileNO(String mobileNO) {
		this.mobileNO = mobileNO;
	}
	
	public String getQQNO() {
		return QQNO;
	}

	public void setQQNO(String qQNO) {
		QQNO = qQNO;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIDType() {
		return IDType;
	}

	public void setIDType(String iDType) {
		IDType = iDType;
	}

	public String getIDNO() {
		return IDNO;
	}

	public void setIDNO(String iDNO) {
		IDNO = iDNO;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPersonalURL() {
		return personalURL;
	}

	public void setPersonalURL(String personalURL) {
		this.personalURL = personalURL;
	}

	public String getTelephoneNO() {
		return telephoneNO;
	}

	public void setTelephoneNO(String telephoneNO) {
		this.telephoneNO = telephoneNO;
	}

	public String getFaxNO() {
		return faxNO;
	}

	public void setFaxNO(String faxNO) {
		this.faxNO = faxNO;
	}

	public String getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(String marriageStatus) {
		this.marriageStatus = marriageStatus;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	

	public String getVIPFrom() {
		return VIPFrom;
	}

	public Integer getCurrentValidatePoint() {
		return currentValidatePoint;
	}

	public void setCurrentValidatePoint(Integer currentValidatePoint) {
		this.currentValidatePoint = currentValidatePoint;
	}

	public Integer getUsedPoint() {
		return usedPoint;
	}

	public void setUsedPoint(Integer usedPoint) {
		this.usedPoint = usedPoint;
	}

	public String getPictruePath() {
		return pictruePath;
	}

	public void setPictruePath(String pictruePath) {
		 this.pictruePath = pictruePath;
	}

	public String getFullDegree() {
		return fullDegree;
	}

	public void setFullDegree(String fullDegree) {
		this.fullDegree = fullDegree;
	}

	public void setVIPFrom(String vIPFrom) {
		VIPFrom = vIPFrom;
	}

	public String getVIPChannel() {
		return VIPChannel;
	}

	public void setVIPChannel(String vIPChannel) {
		VIPChannel = vIPChannel;
	}

	public String getVIPType() {
		return VIPType;
	}

	public void setVIPType(String vIPType) {
		VIPType = vIPType;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getIsEmailBinding() {
		return isEmailBinding;
	}

	public void setIsEmailBinding(String isEmailBinding) {
		this.isEmailBinding = isEmailBinding;
	}

	public String getIsMobileNOBinding() {
		return isMobileNOBinding;
	}

	public void setIsMobileNOBinding(String isMobileNOBinding) {
		this.isMobileNOBinding = isMobileNOBinding;
	}

	public Integer getExpiricalValue() {
		return expiricalValue;
	}

	public void setExpiricalValue(Integer expiricalValue) {
		this.expiricalValue = expiricalValue;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSafeQuestion() {
		return safeQuestion;
	}

	public void setSafeQuestion(String safeQuestion) {
		this.safeQuestion = safeQuestion;
	}

	public String getSafeAnswer() {
		return safeAnswer;
	}

	public void setSafeAnswer(String safeAnswer) {
		this.safeAnswer = safeAnswer;
	}
	
//	@Column(nullable = false)
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

//	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getDeposit() {
		return deposit;
	}

	// 精度处理
	public void setDeposit(BigDecimal deposit) {
		this.deposit = SystemConfigUtil.getOrderScaleBigDecimal(deposit);
	}
	
	@Column(nullable = false)
	public String getIsAccountEnabled() {
		return isAccountEnabled;
	}

	public void setIsAccountEnabled(String isAccountEnabled) {
		this.isAccountEnabled = isAccountEnabled;
	}

	@Column(nullable = false)
	public String getIsAccountLocked() {
		return isAccountLocked;
	}

	public void setIsAccountLocked(String isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	@Column(nullable = false)
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}
	
	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}
	
	@Column(nullable = false, updatable = false)
	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	public String getPasswordRecoverKey() {
		return passwordRecoverKey;
	}

	public void setPasswordRecoverKey(String passwordRecoverKey) {
		this.passwordRecoverKey = passwordRecoverKey;
	}
	
	public String getSzMemberUserId() {
		return szMemberUserId;
	}

	public void setSzMemberUserId(String szMemberUserId) {
		this.szMemberUserId = szMemberUserId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = true)
	public MemberRank getMemberRank() {
		return memberRank;
	}

	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}
	
	public String getHeadPicPath() {
		return headPicPath;
	}

	public void setHeadPicPath(String headPicPath) {
		this.headPicPath = headPicPath;
	}

	@CollectionOfElements
	@MapKey(targetElement = MemberAttribute.class)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade(value = { CascadeType.DELETE })
	public Map<MemberAttribute, String> getMemberAttributeMapStore() {
		return memberAttributeMapStore;
	}

	public void setMemberAttributeMapStore(Map<MemberAttribute, String> memberAttributeMapStore) {
		this.memberAttributeMapStore = memberAttributeMapStore;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<Receiver> getReceiverSet() {
		return receiverSet;
	}

	public void setReceiverSet(Set<Receiver> receiverSet) {
		this.receiverSet = receiverSet;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("name desc")
	public Set<Product> getFavoriteProductSet() {
		return favoriteProductSet;
	}

	public void setFavoriteProductSet(Set<Product> favoriteProductSet) {
		this.favoriteProductSet = favoriteProductSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<CartItem> getCartItemSet() {
		return cartItemSet;
	}

	public void setCartItemSet(Set<CartItem> cartItemSet) {
		this.cartItemSet = cartItemSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "toMember")
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<Message> getInboxMessageSet() {
		return inboxMessageSet;
	}

	public void setInboxMessageSet(Set<Message> inboxMessageSet) {
		this.inboxMessageSet = inboxMessageSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fromMember")
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<Message> getOutboxMessageSet() {
		return outboxMessageSet;
	}

	public void setOutboxMessageSet(Set<Message> outboxMessageSet) {
		this.outboxMessageSet = outboxMessageSet;
	}

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
//	@OrderBy("createDate desc")
//	public Set<Order> getOrderSet() {
//		return orderSet;
//	 }
//
//	public void setOrderSet(Set<Order> orderSet) {
//		this.orderSet = orderSet;
//	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate desc")
	public Set<Deposit> getDepositSet() {
		return depositSet;
	}

	public void setDepositSet(Set<Deposit> depositSet) {
		this.depositSet = depositSet;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("name desc")
	public Set<Present> getFavoritePresentSet() {
		return favoritePresentSet;
	}

	public void setFavoritePresentSet(Set<Present> favoritePresentSet) {
		this.favoritePresentSet = favoritePresentSet;
	}

	// 获取会员注册项
	@SuppressWarnings("unchecked")
	@Transient
	public Map<MemberAttribute, List<String>> getMemberAttributeMap() {
		if (memberAttributeMapStore == null || memberAttributeMapStore.size() == 0) {
			return null;
		}
		Map<MemberAttribute, List<String>> memberAttributeMap = new HashMap<MemberAttribute, List<String>>();
		for (MemberAttribute memberAttribute : memberAttributeMapStore.keySet()) {
			String memberAttributeValueStore = memberAttributeMapStore.get(memberAttribute);
			if (StringUtils.isNotEmpty(memberAttributeValueStore)) {
				JSONArray jsonArray = JSONArray.fromObject(memberAttributeMapStore.get(memberAttribute));
				memberAttributeMap.put(memberAttribute, (List<String>) JSONSerializer.toJava(jsonArray));
			} else {
				memberAttributeMap.put(memberAttribute, null);
			}
		}
		return memberAttributeMap;
	}

	// 设置会员注册项
	@Transient
	public void setMemberAttributeMap(Map<MemberAttribute, List<String>> memberAttributeMap) {
		if (memberAttributeMap == null || memberAttributeMap.size() == 0) {
			memberAttributeMapStore = null;
			return;
		}
		Map<MemberAttribute, String> memberAttributeMapStore = new HashMap<MemberAttribute, String>();
		for (MemberAttribute memberAttribute : memberAttributeMap.keySet()) {
			List<String> memberAttributeValueList = memberAttributeMap.get(memberAttribute);
			if (memberAttributeValueList != null && memberAttributeValueList.size() > 0) {
				JSONArray jsonArray = JSONArray.fromObject(memberAttributeValueList);
				memberAttributeMapStore.put(memberAttribute, jsonArray.toString());
			} else {
				memberAttributeMapStore.put(memberAttribute, "");
			}
		}
		this.memberAttributeMapStore = memberAttributeMapStore;
	}

	public void setHobby(Set<MemberHobby> hobby) {
		this.hobby = hobby;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
	@OrderBy("createDate desc")
	public Set<MemberHobby> getHobby() {
		return hobby;
	}

	public String getIsfirstMC() {
		return isfirstMC;
	}

	public void setIsfirstMC(String isfirstMC) {
		this.isfirstMC = isfirstMC;
	}

	public String getHasUpdate() {
		return hasUpdate;
	}

	public void setHasUpdate(String hasUpdate) {
		this.hasUpdate = hasUpdate;
	}

	public Integer getPreLoginPoints() {
		return preLoginPoints;
	}

	public void setPreLoginPoints(Integer preLoginPoints) {
		this.preLoginPoints = preLoginPoints;
	}

	public String getRecommendMemId() {
		return recommendMemId;
	}

	public void setRecommendMemId(String recommendMemId) {
		this.recommendMemId = recommendMemId;
	}

	public String getRecommendFlag() {
		return recommendFlag;
	}

	public void setRecommendFlag(String recommendFlag) {
		this.recommendFlag = recommendFlag;
	}

	public Integer getRecommendRegPoints() {
		return recommendRegPoints;
	}

	public void setRecommendRegPoints(Integer recommendRegPoints) {
		this.recommendRegPoints = recommendRegPoints;
	}

	public Integer getRecommendBuyPoints() {
		return recommendBuyPoints;
	}

	public void setRecommendBuyPoints(Integer recommendBuyPoints) {
		this.recommendBuyPoints = recommendBuyPoints;
	}

	public Integer getOverduePoints() {
		return overduePoints;
	}

	public void setOverduePoints(Integer overduePoints) {
		this.overduePoints = overduePoints;
	}

	public Integer getAboutToExpirePoints() {
		return aboutToExpirePoints;
	}

	public void setAboutToExpirePoints(Integer aboutToExpirePoints) {
		this.aboutToExpirePoints = aboutToExpirePoints;
	}

	public String getAboutToExpireDate() {
		return aboutToExpireDate;
	}

	public void setAboutToExpireDate(String aboutToExpireDate) {
		this.aboutToExpireDate = aboutToExpireDate;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(String consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public Date getLoginDateAfterUngrade() {
		return loginDateAfterUngrade;
	}

	public void setLoginDateAfterUngrade(Date loginDateAfterUngrade) {
		this.loginDateAfterUngrade = loginDateAfterUngrade;
	}

	public String getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(String isBuy) {
		this.isBuy = isBuy;
	}

	public String getAccuEndDate() {
		return accuEndDate;
	}

	public void setAccuEndDate(String accuEndDate) {
		this.accuEndDate = accuEndDate;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public String getVipFlag() {
		return vipFlag;
	}

	public void setVipFlag(String vipFlag) {
		this.vipFlag = vipFlag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}