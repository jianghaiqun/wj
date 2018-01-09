package cn.com.sinosoft.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author LiuXin
 *
 */
@Entity
@Table(name = "FDINSCOM")
public class FDInsCom implements Serializable{

	private static final long serialVersionUID = -6732110611000511636L;
	/**
	 * 保险公司编码
	 */
	@Id
	@Column(name = "SUPPLIERCODE", length = 20, nullable = true)
	private String supplierCode;
	/**
	 * 保险公司显示编码	
	 */
	@Column(name = "SUPPLIERSHOWCODE", length = 20)
	private String supplierShowCode;
	/**
	 * 保险公司类别	
	 */
	@Column(name = "INSTYPE", length = 10)
	private String insType;
	/**
	 * 保险公司级别	
	 */
	@Column(name = "INSCLASS", length = 2)
	private String insClass;
	/**
	 * 保险公司中文名称	
	 */
	@Column(name = "SUPPLIERNAME", length = 200)
	private String supplierName;
	/**
	 * 保险公司英文名称	
	 */
	@Column(name = "SUPPLIERENGNAME", length = 200)
	private String supplierEngName;
	/**
	 * 保险公司简称	
	 */
	@Column(name = "SHORTNAME", length = 150)
	private String shortName;
	/**
	 * 保险公司上级单位	
	 */
	@Column(name = "INSSUPERCODE", length = 20)
	private String insSuperCode;
	/**
	 * 客服热线	
	 */
	@Column(name = "HOTLINE", length = 30)
	private String hotline;
	/**
	 * 成立日期	
	 */
	@Column(name = "FOUNDDATE")
	private Date foundDate;
	/**
	 * 备注
	 */
	@Column(name = "REMARK", length = 80)
	private String remark;
	/**
	 * 保险公司地址编码	
	 */
	@Column(name = "INSADDRESSCODE", length = 10)
	private String insAddressCode;
	/**
	 * 密码
	 */
	@Column(name = "PASSWORD", length = 128)
	private String password;
	/**
	 * 国别
	 */
	@Column(name = "COUNTRYCODE", length = 10)
	private String countryCode;
	/**
	 * 所在市	
	 */
	@Column(name = "CITYCODE", length = 10)
	private String cityCode;
	/**
	 * 所在省	
	 */
	@Column(name = "PROVINCECODE", length = 10)
	private String provinceCode;
	/**
	 * 地区
	 */
	@Column(name = "AREACODE", length = 10)
	private String areaCode;
	/**
	 * 保险公司地址	
	 */
	@Column(name = "INSADDRESS", length = 200)
	private String insAddress;
	/**
	 * 保险公司邮编	
	 */
	@Column(name = "INSZIPCODE", length = 20)
	private String insZipCode;
	/**
	 * 总人数	
	 */
	@Column(name = "PEOPLES")
	private Integer peoples;
	/**
	 * 注册资本	
	 * 
	 */
	@Column(name = "RGTMONEY", precision = 2, length = 16)
	private Double rgtMoney	;
	/**
	 * 资产总额	
	 */
	@Column(name = "ASSET", precision = 2, length = 16)
	private Double asset;
	/**
	 * 净资产收益率	
	 */
	@Column(name = "NETPROFITRATE", precision = 4, length = 16)
	private Double netProfitRate;
	/**
	 * 主营业务	
	 */
	@Column(name = "MAINBUSSINESS", length = 60)
	private String mainBussiness;
	/**
	 * 银行帐号	
	 */
	@Column(name = "BANKACCNO", length = 40)
	private String bankAccNo;
	/**
	 * 银行编码	
	 */
	@Column(name = "BANKCODE", length = 10)
	private String bankCode;
	/**
	 * 付款方式	
	 */
	@Column(name = "GETFLAG", length = 2)
	private String getFlag;
	/**
	 * 负责人	
	 */
	@Column(name = "SATRAP", length = 30)
	private String satrap;
	/**
	 * 法人	
	 */
	@Column(name = "CORPORATION", length = 20)
	private String corporation;
	/**
	 * 机构分布区域	
	 */
	@Column(name = "ComAera", length = 30)
	private String comAera;
	/**
	 * 单位传真	
	 */
	@Column(name = "FAX", length = 18)
	private String fax;
	/**
	 * 单位电话	
	 */
	@Column(name = "PHONE", length = 18)
	private String phone;
	/**
	 * 公司e_mail	
	 */
	@Column(name = "EMAIL", length = 20)
	private String email;
	/**
	 * 公司网址	
	 */
	@Column(name = "WEBSITE", length = 50)
	private String webSite;
	/**
	 * 黑名单标记	
	 */
	@Column(name = "BLACKLISTFLAG", length = 2)
	private String blacklistFlag;
	/**
	 * 状态
	 */
	@Column(name = "STATUS", length = 10)
	private String status;
	/**
	 * 联系人1	
	 */
	@Column(name = "LINKMAN1", length = 20)
	private String linkMan1;
	/**
	 * 部门1	
	 */
	@Column(name = "DEPARTMENT1", length = 30)
	private String department1;
	/**
	 * 职务1	
	 */
	@Column(name = "HEADSHIP1", length = 30)
	private String headShip1;
	/**
	 * 手机1	
	 */
	@Column(name = "MOBILE1", length = 20)
	private String mobile1;
	/**
	 * 联系电话1	
	 */
	@Column(name = "PHONE1", length = 30)
	private String phone1;
	/**
	 * E_Mail1	
	 */
	@Column(name = "E_Mail1", length = 20)
	private String e_mail1;
	/**
	 * 传真1	
	 */
	@Column(name = "Fax1", length = 30)
	private String fax1;
	/**
	 * 联络人1客户编码	
	 */
	@Column(name = "LINKCUSTOMERNO1", length = 20)
	private String linkCustomerNo1;
	/**
	 * 联系人2	
	 */
	@Column(name = "LINKMAN2", length = 20)
	private String linkMan2;
	/**
	 * 部门2	
	 */
	@Column(name = "DEPARTMENT2", length = 30)
	private String department2;
	/**
	 * 职务2	
	 */
	@Column(name = "HEADSHIP2", length = 30)
	private String headShip2;
	/**
	 * 手机2	
	 */
	@Column(name = "MOBILE2", length = 20)
	private String mobile2;
	/**
	 * 联系电话2	
	 */
	@Column(name = "PHONE2", length = 30)
	private String phone2;
	/**
	 * E_Mail2	
	 */
	@Column(name = "E_Mail2", length = 20)
	private String	e_mail2;
	/**
	 * 联络人2客户编码	
	 */
	@Column(name = "LINKCUSTOMERNO2", length = 20)
	private String linkCustomerNo2;
	/**
	 * 传真2	
	 */
	@Column(name = "FAX2", length = 30)
	private String fax2;
	/**
	 * 联系人3	
	 */
	@Column(name = "LINKMAN3", length = 20)
	private String linkMan3;
	/**
	 * 手机3	
	 */
	@Column(name = "MOBILE3", length = 20)
	private String mobile3;
	/**
	 * 联络人3客户编码	
	 */
	@Column(name = "LINKCUSTOMERNO3", length = 20)
	private String linkCustomerNo3;
	/**
	 * 联系人4	
	 */
	@Column(name = "LinkMan4", length = 20)
	private String linkMan4;
	/**
	 * 手机4	
	 */
	@Column(name = "MOBILE4", length = 20)
	private String mobile4;
	/**
	 * 联络人4客户编码	
	 */
	@Column(name = "LINKCUSTOMERNO4", length = 20)
	private String linkCustomerNo4;
	/**
	 * 操作员代码	
	 */
	@Column(name = "OPERATOR", length = 80)
	private String operator;
	/**
	 * 入机日期	
	 */
	@Column(name = "MAKEDATE")
	private Date makeDate;
	/**
	 * 入机时间	
	 */
	@Column(name = "MAKETIME", length = 8)
	private String makeTime;
	/**
	 * 最后一次修改日期	
	 */
	@Column(name = "MODIFYDATE")
	private Date modifyDate;
	/**
	 * 最后一次修改时间	
	 */
	@Column(name = "MODIFYTIME", length = 8)
	private String modifyTime;
	/**
	 * 客户编码	
	 */
	@Column(name = "CUSTOMERNO", length = 20)
	private String customerNo;
	/**
	 * 保险公司停业标志	
	 */
	@Column(name = "STOPFLG", length = 2)
	private String StopFlg;
	/**
	 * 所属总公司	
	 */
	@Column(name = "MASTERINSCOM", length = 20)
	private String masterInscom;
	/**
	 * 自动续保标记	
	 */
	@Column(name = "AUTORENEWFLAG", length = 2)
	private String autoRenewFlag;
	/**
	 * 是否同步标志	
	 */
	@Column(name = "SYNCHROFLAG", length = 2)
	private String synchroFlag;
	/**
	 * 银行网点	
	 */
	@Column(name = "BANKADDRESS", length = 200)
	private String bankAddress;
	/**
	 * 保险公司介绍	
	 */
	@Column(name = "SUPPLIERDESC", length = 500)
	private String supplierDesc;
	/**
	 * 保险公司小类	
	 */
	@Column(name = "INSCATEGORY", length = 2)
	private String insCategory;
	/**
	 * LOGO相对地址	
	 */
	@Column(name = "LOGORELAPATH", length = 200)
	private String logoRelaPath;
	/**
	 * LOGO绝对地址	
	 */
	@Column(name = "LOGOREALPATH", length = 200)
	private String logoRealPath;
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierShowCode() {
		return supplierShowCode;
	}
	public void setSupplierShowCode(String supplierShowCode) {
		this.supplierShowCode = supplierShowCode;
	}
	public String getInsType() {
		return insType;
	}
	public void setInsType(String insType) {
		this.insType = insType;
	}
	public String getInsClass() {
		return insClass;
	}
	public void setInsClass(String insClass) {
		this.insClass = insClass;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierEngName() {
		return supplierEngName;
	}
	public void setSupplierEngName(String supplierEngName) {
		this.supplierEngName = supplierEngName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getInsSuperCode() {
		return insSuperCode;
	}
	public void setInsSuperCode(String insSuperCode) {
		this.insSuperCode = insSuperCode;
	}
	public String getHotline() {
		return hotline;
	}
	public void setHotline(String hotline) {
		this.hotline = hotline;
	}
	public Date getFoundDate() {
		return foundDate;
	}
	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getInsAddressCode() {
		return insAddressCode;
	}
	public void setInsAddressCode(String insAddressCode) {
		this.insAddressCode = insAddressCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getInsAddress() {
		return insAddress;
	}
	public void setInsAddress(String insAddress) {
		this.insAddress = insAddress;
	}
	public String getInsZipCode() {
		return insZipCode;
	}
	public void setInsZipCode(String insZipCode) {
		this.insZipCode = insZipCode;
	}
	public Integer getPeoples() {
		return peoples;
	}
	public void setPeoples(Integer peoples) {
		this.peoples = peoples;
	}
	public Double getRgtMoney() {
		return rgtMoney;
	}
	public void setRgtMoney(Double rgtMoney) {
		this.rgtMoney = rgtMoney;
	}
	public Double getAsset() {
		return asset;
	}
	public void setAsset(Double asset) {
		this.asset = asset;
	}
	public Double getNetProfitRate() {
		return netProfitRate;
	}
	public void setNetProfitRate(Double netProfitRate) {
		this.netProfitRate = netProfitRate;
	}
	public String getMainBussiness() {
		return mainBussiness;
	}
	public void setMainBussiness(String mainBussiness) {
		this.mainBussiness = mainBussiness;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getGetFlag() {
		return getFlag;
	}
	public void setGetFlag(String getFlag) {
		this.getFlag = getFlag;
	}
	public String getSatrap() {
		return satrap;
	}
	public void setSatrap(String satrap) {
		this.satrap = satrap;
	}
	public String getCorporation() {
		return corporation;
	}
	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}
	public String getComAera() {
		return comAera;
	}
	public void setComAera(String comAera) {
		this.comAera = comAera;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public String getBlacklistFlag() {
		return blacklistFlag;
	}
	public void setBlacklistFlag(String blacklistFlag) {
		this.blacklistFlag = blacklistFlag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLinkMan1() {
		return linkMan1;
	}
	public void setLinkMan1(String linkMan1) {
		this.linkMan1 = linkMan1;
	}
	public String getDepartment1() {
		return department1;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	public String getHeadShip1() {
		return headShip1;
	}
	public void setHeadShip1(String headShip1) {
		this.headShip1 = headShip1;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getE_mail1() {
		return e_mail1;
	}
	public void setE_mail1(String e_mail1) {
		this.e_mail1 = e_mail1;
	}
	public String getFax1() {
		return fax1;
	}
	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}
	public String getLinkCustomerNo1() {
		return linkCustomerNo1;
	}
	public void setLinkCustomerNo1(String linkCustomerNo1) {
		this.linkCustomerNo1 = linkCustomerNo1;
	}
	public String getLinkMan2() {
		return linkMan2;
	}
	public void setLinkMan2(String linkMan2) {
		this.linkMan2 = linkMan2;
	}
	public String getDepartment2() {
		return department2;
	}
	public void setDepartment2(String department2) {
		this.department2 = department2;
	}
	public String getHeadShip2() {
		return headShip2;
	}
	public void setHeadShip2(String headShip2) {
		this.headShip2 = headShip2;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getE_mail2() {
		return e_mail2;
	}
	public void setE_mail2(String e_mail2) {
		this.e_mail2 = e_mail2;
	}
	public String getLinkCustomerNo2() {
		return linkCustomerNo2;
	}
	public void setLinkCustomerNo2(String linkCustomerNo2) {
		this.linkCustomerNo2 = linkCustomerNo2;
	}
	public String getFax2() {
		return fax2;
	}
	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}
	public String getLinkMan3() {
		return linkMan3;
	}
	public void setLinkMan3(String linkMan3) {
		this.linkMan3 = linkMan3;
	}
	public String getMobile3() {
		return mobile3;
	}
	public void setMobile3(String mobile3) {
		this.mobile3 = mobile3;
	}
	public String getLinkCustomerNo3() {
		return linkCustomerNo3;
	}
	public void setLinkCustomerNo3(String linkCustomerNo3) {
		this.linkCustomerNo3 = linkCustomerNo3;
	}
	public String getLinkMan4() {
		return linkMan4;
	}
	public void setLinkMan4(String linkMan4) {
		this.linkMan4 = linkMan4;
	}
	public String getMobile4() {
		return mobile4;
	}
	public void setMobile4(String mobile4) {
		this.mobile4 = mobile4;
	}
	public String getLinkCustomerNo4() {
		return linkCustomerNo4;
	}
	public void setLinkCustomerNo4(String linkCustomerNo4) {
		this.linkCustomerNo4 = linkCustomerNo4;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getMakeDate() {
		return makeDate;
	}
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}
	public String getMakeTime() {
		return makeTime;
	}
	public void setMakeTime(String makeTime) {
		this.makeTime = makeTime;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getStopFlg() {
		return StopFlg;
	}
	public void setStopFlg(String stopFlg) {
		StopFlg = stopFlg;
	}
	public String getMasterInscom() {
		return masterInscom;
	}
	public void setMasterInscom(String masterInscom) {
		this.masterInscom = masterInscom;
	}
	public String getAutoRenewFlag() {
		return autoRenewFlag;
	}
	public void setAutoRenewFlag(String autoRenewFlag) {
		this.autoRenewFlag = autoRenewFlag;
	}
	public String getSynchroFlag() {
		return synchroFlag;
	}
	public void setSynchroFlag(String synchroFlag) {
		this.synchroFlag = synchroFlag;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public String getSupplierDesc() {
		return supplierDesc;
	}
	public void setSupplierDesc(String supplierDesc) {
		this.supplierDesc = supplierDesc;
	}
	public String getInsCategory() {
		return insCategory;
	}
	public void setInsCategory(String insCategory) {
		this.insCategory = insCategory;
	}
	public String getLogoRelaPath() {
		return logoRelaPath;
	}
	public void setLogoRelaPath(String logoRelaPath) {
		this.logoRelaPath = logoRelaPath;
	}
	public String getLogoRealPath() {
		return logoRealPath;
	}
	public void setLogoRealPath(String logoRealPath) {
		this.logoRealPath = logoRealPath;
	}

	
}
