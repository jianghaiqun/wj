package cn.com.sinosoft.entity;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 
 * 实体类：货运信息
 *
 */



@Entity
public class InformationItemCargo extends BaseEntity {

	private static final long serialVersionUID = 2254343156222601246L;

	private InformationItemMain informationItemMain;// 标的
	private String name; //名称
	private String quantity; //包装及数量
	private String ladingNo; //提单号
	private String invoiceNo; //发票号
	private String invoiceCurrency;//发票金额币别
	private String invoiceAmount; //发票金额
	private String conveyance; //装载运输工具
	private String shipNoteNo; //起运通知书编号
	private String carryBillNo; //货票运单号
	private String bLName; //运具名称
	private String bLNo; //运具牌号
	private String transferConveyance; //转运工具
	private Date startDate; //起运日期
	private String startSiteCode; //起始地编码
	private String startSiteName; //起始地名称
	private String viaSiteCode; //中转地编码
	private String viaSiteName; //中转地名称
	private String endSiteCode; //终止地编码
	private String endSiteName; //终止地名称
	private String voyageNo; //航次
	private String goodsPrice; //货物价值
	private String remark; //备注
	private String address; //所在地
	
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the ladingNo
	 */
	public String getLadingNo() {
		return ladingNo;
	}
	/**
	 * @param ladingNo the ladingNo to set
	 */
	public void setLadingNo(String ladingNo) {
		this.ladingNo = ladingNo;
	}
	/**
	 * @return the invoiceNo
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}
	/**
	 * @param invoiceNo the invoiceNo to set
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	/**
	 * @return the invoiceCurrency
	 */
	public String getInvoiceCurrency() {
		return invoiceCurrency;
	}
	/**
	 * @param invoiceCurrency the invoiceCurrency to set
	 */
	public void setInvoiceCurrency(String invoiceCurrency) {
		this.invoiceCurrency = invoiceCurrency;
	}
	/**
	 * @return the invoiceAmount
	 */
	public String getInvoiceAmount() {
		return invoiceAmount;
	}
	/**
	 * @param invoiceAmount the invoiceAmount to set
	 */
	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	/**
	 * @return the conveyance
	 */
	public String getConveyance() {
		return conveyance;
	}
	/**
	 * @param conveyance the conveyance to set
	 */
	public void setConveyance(String conveyance) {
		this.conveyance = conveyance;
	}
	/**
	 * @return the shipNoteNo
	 */
	public String getShipNoteNo() {
		return shipNoteNo;
	}
	/**
	 * @param shipNoteNo the shipNoteNo to set
	 */
	public void setShipNoteNo(String shipNoteNo) {
		this.shipNoteNo = shipNoteNo;
	}
	/**
	 * @return the carryBillNo
	 */
	public String getCarryBillNo() {
		return carryBillNo;
	}
	/**
	 * @param carryBillNo the carryBillNo to set
	 */
	public void setCarryBillNo(String carryBillNo) {
		this.carryBillNo = carryBillNo;
	}
	/**
	 * @return the bLName
	 */
	public String getbLName() {
		return bLName;
	}
	/**
	 * @param bLName the bLName to set
	 */
	public void setbLName(String bLName) {
		this.bLName = bLName;
	}
	/**
	 * @return the bLNo
	 */
	public String getbLNo() {
		return bLNo;
	}
	/**
	 * @param bLNo the bLNo to set
	 */
	public void setbLNo(String bLNo) {
		this.bLNo = bLNo;
	}
	/**
	 * @return the transferConveyance
	 */
	public String getTransferConveyance() {
		return transferConveyance;
	}
	/**
	 * @param transferConveyance the transferConveyance to set
	 */
	public void setTransferConveyance(String transferConveyance) {
		this.transferConveyance = transferConveyance;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the startSiteCode
	 */
	public String getStartSiteCode() {
		return startSiteCode;
	}
	/**
	 * @param startSiteCode the startSiteCode to set
	 */
	public void setStartSiteCode(String startSiteCode) {
		this.startSiteCode = startSiteCode;
	}
	/**
	 * @return the startSiteName
	 */
	public String getStartSiteName() {
		return startSiteName;
	}
	/**
	 * @param startSiteName the startSiteName to set
	 */
	public void setStartSiteName(String startSiteName) {
		this.startSiteName = startSiteName;
	}
	/**
	 * @return the viaSiteCode
	 */
	public String getViaSiteCode() {
		return viaSiteCode;
	}
	/**
	 * @param viaSiteCode the viaSiteCode to set
	 */
	public void setViaSiteCode(String viaSiteCode) {
		this.viaSiteCode = viaSiteCode;
	}
	/**
	 * @return the viaSiteName
	 */
	public String getViaSiteName() {
		return viaSiteName;
	}
	/**
	 * @param viaSiteName the viaSiteName to set
	 */
	public void setViaSiteName(String viaSiteName) {
		this.viaSiteName = viaSiteName;
	}
	/**
	 * @return the endSiteCode
	 */
	public String getEndSiteCode() {
		return endSiteCode;
	}
	/**
	 * @param endSiteCode the endSiteCode to set
	 */
	public void setEndSiteCode(String endSiteCode) {
		this.endSiteCode = endSiteCode;
	}
	/**
	 * @return the endSiteName
	 */
	public String getEndSiteName() {
		return endSiteName;
	}
	/**
	 * @param endSiteName the endSiteName to set
	 */
	public void setEndSiteName(String endSiteName) {
		this.endSiteName = endSiteName;
	}
	/**
	 * @return the voyageNo
	 */
	public String getVoyageNo() {
		return voyageNo;
	}
	/**
	 * @param voyageNo the voyageNo to set
	 */
	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}
	/**
	 * @return the goodsPrice
	 */
	public String getGoodsPrice() {
		return goodsPrice;
	}
	/**
	 * @param goodsPrice the goodsPrice to set
	 */
	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the area
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param area the area to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	public InformationItemMain getInformationItemMain() {
		return informationItemMain;
	}
	public void setInformationItemMain(InformationItemMain informationItemMain) {
		this.informationItemMain = informationItemMain;
	}
}