package cn.com.sinosoft.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 
 * 实体类：邮寄地址信息 
 *
 */
public class SDBillInfo extends BaseEntity {

	private static final long serialVersionUID = -8921842979770117359L;

	private String memberId;//会员Id
	private String status;//发票状态
	private String deliverDate;//邮寄时间
	private String billType;//发票类型
	private String billAmount;//发票金额
	private String deliverAddrId;//邮寄地址
	private String logisticsId;//物流公司ID
	private String wayBillNo;//运单号
	private String billTitle;//发票抬头
	private String billReqUrl;//开票申请表Url
	private String deliverName;//邮寄姓名
	private String deliverTel;//邮寄电话
	private String deliverProvince;//邮寄地址省
	private String deliverCity;//邮寄地址省
	private String deliverSection;//邮寄地区
	private String deliverDetailAddr;//详细地址
	private String deliverZipCode;//邮编
	private String modifyUser;//更新者Id
	private String createUser;//创建者Id
	private String pro1;//备用1
	private String pro2;//备用2
	
	private String orderSns;//关联订单号
	
	public String getOrderSns() {
		return orderSns;
	}
	public void setOrderSns(String orderSns) {
		this.orderSns = orderSns;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}
	public String getDeliverAddrId() {
		return deliverAddrId;
	}
	public void setDeliverAddrId(String deliverAddrId) {
		this.deliverAddrId = deliverAddrId;
	}
	public String getLogisticsId() {
		return logisticsId;
	}
	public void setLogisticsId(String logisticsId) {
		this.logisticsId = logisticsId;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getBillTitle() {
		return billTitle;
	}
	public void setBillTitle(String billTitle) {
		this.billTitle = billTitle;
	}
	public String getBillReqUrl() {
		return billReqUrl;
	}
	public void setBillReqUrl(String billReqUrl) {
		this.billReqUrl = billReqUrl;
	}
	public String getDeliverName() {
		return deliverName;
	}
	public void setDeliverName(String deliverName) {
		this.deliverName = deliverName;
	}
	public String getDeliverTel() {
		return deliverTel;
	}
	public void setDeliverTel(String deliverTel) {
		this.deliverTel = deliverTel;
	}
	public String getDeliverProvince() {
		return deliverProvince;
	}
	public void setDeliverProvince(String deliverProvince) {
		this.deliverProvince = deliverProvince;
	}
	public String getDeliverCity() {
		return deliverCity;
	}
	public void setDeliverCity(String deliverCity) {
		this.deliverCity = deliverCity;
	}
	public String getDeliverSection() {
		return deliverSection;
	}
	public void setDeliverSection(String deliverSection) {
		this.deliverSection = deliverSection;
	}
	public String getDeliverDetailAddr() {
		return deliverDetailAddr;
	}
	public void setDeliverDetailAddr(String deliverDetailAddr) {
		this.deliverDetailAddr = deliverDetailAddr;
	}
	public String getDeliverZipCode() {
		return deliverZipCode;
	}
	public void setDeliverZipCode(String deliverZipCode) {
		this.deliverZipCode = deliverZipCode;
	}

	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getPro1() {
		return pro1;
	}
	public void setPro1(String pro1) {
		this.pro1 = pro1;
	}
	public String getPro2() {
		return pro2;
	}
	public void setPro2(String pro2) {
		this.pro2 = pro2;
	}
}
