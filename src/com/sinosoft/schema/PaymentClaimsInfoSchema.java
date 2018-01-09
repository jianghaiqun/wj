package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：理赔信息表<br>
 * 表代码：PaymentClaimsInfo<br>
 * 表主键：id<br>
 */
public class PaymentClaimsInfoSchema extends Schema {
	private String id;

	private String claimsNo;

	private String orderSn;

	private String policyNo;

	private String claimsItemsId;

	private String claimsItemsName;

	private String insureName;

	private String insureIdentityId;

	private String insureBirthday;

	private String contactName;

	private String contactMail;

	private String contactMobile;

	private String insureRelation;

	private Date applicationDate;

	private String status;

	private String giroDate;

	private String claimsMoney;

	private String memberId;

	private String caseType;

	private String caseDesc;

	private String referInsureCompany;

	private String sendAddress;

	private String sendDesc;

	private String courierFirm;

	private String courierNumber;

	private String receiveDate;

	private String returnDesc;

	private String productId;

	private String claimsItemsType;

	private String bankName;

	private String bankUserName;

	private String happenTime;

	private String happenAddress;

	private String kindOfLoss;

	private String claimReason;

	private String notificationNo;

	private String isUpload;

	private String flightNo;

	private String flightTime;

	private String cycle;

	private String isShowFlag;

	private String remark1;

	private String remark2;

	private String remark3;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("claimsNo", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("policyNo", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("claimsItemsId", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("claimsItemsName", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("insureName", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("insureIdentityId", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("insureBirthday", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("contactName", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("contactMail", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("contactMobile", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("insureRelation", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("applicationDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("status", DataColumn.STRING, 14, 10 , 0 , false , false),
		new SchemaColumn("giroDate", DataColumn.STRING, 15, 10 , 0 , false , false),
		new SchemaColumn("claimsMoney", DataColumn.STRING, 16, 10 , 0 , false , false),
		new SchemaColumn("memberId", DataColumn.STRING, 17, 32 , 0 , false , false),
		new SchemaColumn("caseType", DataColumn.STRING, 18, 100 , 0 , false , false),
		new SchemaColumn("caseDesc", DataColumn.CLOB, 19, 0 , 0 , false , false),
		new SchemaColumn("referInsureCompany", DataColumn.STRING, 20, 2 , 0 , false , false),
		new SchemaColumn("sendAddress", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("sendDesc", DataColumn.CLOB, 22, 0 , 0 , false , false),
		new SchemaColumn("courierFirm", DataColumn.STRING, 23, 50 , 0 , false , false),
		new SchemaColumn("courierNumber", DataColumn.STRING, 24, 100 , 0 , false , false),
		new SchemaColumn("receiveDate", DataColumn.STRING, 25, 10 , 0 , false , false),
		new SchemaColumn("returnDesc", DataColumn.CLOB, 26, 0 , 0 , false , false),
		new SchemaColumn("productId", DataColumn.STRING, 27, 20 , 0 , false , false),
		new SchemaColumn("claimsItemsType", DataColumn.STRING, 28, 20 , 0 , false , false),
		new SchemaColumn("bankName", DataColumn.STRING, 29, 255 , 0 , false , false),
		new SchemaColumn("bankUserName", DataColumn.STRING, 30, 255 , 0 , false , false),
		new SchemaColumn("happenTime", DataColumn.STRING, 31, 20 , 0 , false , false),
		new SchemaColumn("happenAddress", DataColumn.STRING, 32, 255 , 0 , false , false),
		new SchemaColumn("kindOfLoss", DataColumn.STRING, 33, 255 , 0 , false , false),
		new SchemaColumn("claimReason", DataColumn.STRING, 34, 255 , 0 , false , false),
		new SchemaColumn("notificationNo", DataColumn.STRING, 35, 100 , 0 , false , false),
		new SchemaColumn("isUpload", DataColumn.STRING, 36, 2 , 0 , false , false),
		new SchemaColumn("flightNo", DataColumn.STRING, 37, 50 , 0 , false , false),
		new SchemaColumn("flightTime", DataColumn.STRING, 38, 20 , 0 , false , false),
		new SchemaColumn("cycle", DataColumn.STRING, 39, 10 , 0 , false , false),
		new SchemaColumn("isShowFlag", DataColumn.STRING, 40, 10 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 41, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 42, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 43, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 44, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 45, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 46, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 47, 0 , 0 , false , false)
	};

	public static final String _TableCode = "PaymentClaimsInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PaymentClaimsInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PaymentClaimsInfo set id=?,claimsNo=?,orderSn=?,policyNo=?,claimsItemsId=?,claimsItemsName=?,insureName=?,insureIdentityId=?,insureBirthday=?,contactName=?,contactMail=?,contactMobile=?,insureRelation=?,applicationDate=?,status=?,giroDate=?,claimsMoney=?,memberId=?,caseType=?,caseDesc=?,referInsureCompany=?,sendAddress=?,sendDesc=?,courierFirm=?,courierNumber=?,receiveDate=?,returnDesc=?,productId=?,claimsItemsType=?,bankName=?,bankUserName=?,happenTime=?,happenAddress=?,kindOfLoss=?,claimReason=?,notificationNo=?,isUpload=?,flightNo=?,flightTime=?,cycle=?,isShowFlag=?,remark1=?,remark2=?,remark3=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from PaymentClaimsInfo  where id=?";

	protected static final String _FillAllSQL = "select * from PaymentClaimsInfo  where id=?";

	public PaymentClaimsInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[48];
	}

	protected Schema newInstance(){
		return new PaymentClaimsInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PaymentClaimsInfoSet();
	}

	public PaymentClaimsInfoSet query() {
		return query(null, -1, -1);
	}

	public PaymentClaimsInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PaymentClaimsInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PaymentClaimsInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PaymentClaimsInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){claimsNo = (String)v;return;}
		if (i == 2){orderSn = (String)v;return;}
		if (i == 3){policyNo = (String)v;return;}
		if (i == 4){claimsItemsId = (String)v;return;}
		if (i == 5){claimsItemsName = (String)v;return;}
		if (i == 6){insureName = (String)v;return;}
		if (i == 7){insureIdentityId = (String)v;return;}
		if (i == 8){insureBirthday = (String)v;return;}
		if (i == 9){contactName = (String)v;return;}
		if (i == 10){contactMail = (String)v;return;}
		if (i == 11){contactMobile = (String)v;return;}
		if (i == 12){insureRelation = (String)v;return;}
		if (i == 13){applicationDate = (Date)v;return;}
		if (i == 14){status = (String)v;return;}
		if (i == 15){giroDate = (String)v;return;}
		if (i == 16){claimsMoney = (String)v;return;}
		if (i == 17){memberId = (String)v;return;}
		if (i == 18){caseType = (String)v;return;}
		if (i == 19){caseDesc = (String)v;return;}
		if (i == 20){referInsureCompany = (String)v;return;}
		if (i == 21){sendAddress = (String)v;return;}
		if (i == 22){sendDesc = (String)v;return;}
		if (i == 23){courierFirm = (String)v;return;}
		if (i == 24){courierNumber = (String)v;return;}
		if (i == 25){receiveDate = (String)v;return;}
		if (i == 26){returnDesc = (String)v;return;}
		if (i == 27){productId = (String)v;return;}
		if (i == 28){claimsItemsType = (String)v;return;}
		if (i == 29){bankName = (String)v;return;}
		if (i == 30){bankUserName = (String)v;return;}
		if (i == 31){happenTime = (String)v;return;}
		if (i == 32){happenAddress = (String)v;return;}
		if (i == 33){kindOfLoss = (String)v;return;}
		if (i == 34){claimReason = (String)v;return;}
		if (i == 35){notificationNo = (String)v;return;}
		if (i == 36){isUpload = (String)v;return;}
		if (i == 37){flightNo = (String)v;return;}
		if (i == 38){flightTime = (String)v;return;}
		if (i == 39){cycle = (String)v;return;}
		if (i == 40){isShowFlag = (String)v;return;}
		if (i == 41){remark1 = (String)v;return;}
		if (i == 42){remark2 = (String)v;return;}
		if (i == 43){remark3 = (String)v;return;}
		if (i == 44){createUser = (String)v;return;}
		if (i == 45){createDate = (Date)v;return;}
		if (i == 46){modifyUser = (String)v;return;}
		if (i == 47){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return claimsNo;}
		if (i == 2){return orderSn;}
		if (i == 3){return policyNo;}
		if (i == 4){return claimsItemsId;}
		if (i == 5){return claimsItemsName;}
		if (i == 6){return insureName;}
		if (i == 7){return insureIdentityId;}
		if (i == 8){return insureBirthday;}
		if (i == 9){return contactName;}
		if (i == 10){return contactMail;}
		if (i == 11){return contactMobile;}
		if (i == 12){return insureRelation;}
		if (i == 13){return applicationDate;}
		if (i == 14){return status;}
		if (i == 15){return giroDate;}
		if (i == 16){return claimsMoney;}
		if (i == 17){return memberId;}
		if (i == 18){return caseType;}
		if (i == 19){return caseDesc;}
		if (i == 20){return referInsureCompany;}
		if (i == 21){return sendAddress;}
		if (i == 22){return sendDesc;}
		if (i == 23){return courierFirm;}
		if (i == 24){return courierNumber;}
		if (i == 25){return receiveDate;}
		if (i == 26){return returnDesc;}
		if (i == 27){return productId;}
		if (i == 28){return claimsItemsType;}
		if (i == 29){return bankName;}
		if (i == 30){return bankUserName;}
		if (i == 31){return happenTime;}
		if (i == 32){return happenAddress;}
		if (i == 33){return kindOfLoss;}
		if (i == 34){return claimReason;}
		if (i == 35){return notificationNo;}
		if (i == 36){return isUpload;}
		if (i == 37){return flightNo;}
		if (i == 38){return flightTime;}
		if (i == 39){return cycle;}
		if (i == 40){return isShowFlag;}
		if (i == 41){return remark1;}
		if (i == 42){return remark2;}
		if (i == 43){return remark3;}
		if (i == 44){return createUser;}
		if (i == 45){return createDate;}
		if (i == 46){return modifyUser;}
		if (i == 47){return modifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段claimsNo的值，该字段的<br>
	* 字段名称 :理赔单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getclaimsNo() {
		return claimsNo;
	}

	/**
	* 设置字段claimsNo的值，该字段的<br>
	* 字段名称 :理赔单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setclaimsNo(String claimsNo) {
		this.claimsNo = claimsNo;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpolicyNo() {
		return policyNo;
	}

	/**
	* 设置字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpolicyNo(String policyNo) {
		this.policyNo = policyNo;
    }

	/**
	* 获取字段claimsItemsId的值，该字段的<br>
	* 字段名称 :理赔项目ID<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimsItemsId() {
		return claimsItemsId;
	}

	/**
	* 设置字段claimsItemsId的值，该字段的<br>
	* 字段名称 :理赔项目ID<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsItemsId(String claimsItemsId) {
		this.claimsItemsId = claimsItemsId;
    }

	/**
	* 获取字段claimsItemsName的值，该字段的<br>
	* 字段名称 :理赔项目名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimsItemsName() {
		return claimsItemsName;
	}

	/**
	* 设置字段claimsItemsName的值，该字段的<br>
	* 字段名称 :理赔项目名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsItemsName(String claimsItemsName) {
		this.claimsItemsName = claimsItemsName;
    }

	/**
	* 获取字段insureName的值，该字段的<br>
	* 字段名称 :被保人姓名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsureName() {
		return insureName;
	}

	/**
	* 设置字段insureName的值，该字段的<br>
	* 字段名称 :被保人姓名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsureName(String insureName) {
		this.insureName = insureName;
    }

	/**
	* 获取字段insureIdentityId的值，该字段的<br>
	* 字段名称 :被保人证件号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsureIdentityId() {
		return insureIdentityId;
	}

	/**
	* 设置字段insureIdentityId的值，该字段的<br>
	* 字段名称 :被保人证件号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsureIdentityId(String insureIdentityId) {
		this.insureIdentityId = insureIdentityId;
    }

	/**
	* 获取字段insureBirthday的值，该字段的<br>
	* 字段名称 :被保人生日<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsureBirthday() {
		return insureBirthday;
	}

	/**
	* 设置字段insureBirthday的值，该字段的<br>
	* 字段名称 :被保人生日<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsureBirthday(String insureBirthday) {
		this.insureBirthday = insureBirthday;
    }

	/**
	* 获取字段contactName的值，该字段的<br>
	* 字段名称 :联系人姓名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcontactName() {
		return contactName;
	}

	/**
	* 设置字段contactName的值，该字段的<br>
	* 字段名称 :联系人姓名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontactName(String contactName) {
		this.contactName = contactName;
    }

	/**
	* 获取字段contactMail的值，该字段的<br>
	* 字段名称 :联系人邮箱<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcontactMail() {
		return contactMail;
	}

	/**
	* 设置字段contactMail的值，该字段的<br>
	* 字段名称 :联系人邮箱<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontactMail(String contactMail) {
		this.contactMail = contactMail;
    }

	/**
	* 获取字段contactMobile的值，该字段的<br>
	* 字段名称 :联系人手机号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcontactMobile() {
		return contactMobile;
	}

	/**
	* 设置字段contactMobile的值，该字段的<br>
	* 字段名称 :联系人手机号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
    }

	/**
	* 获取字段insureRelation的值，该字段的<br>
	* 字段名称 :申请人与被保人关系<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsureRelation() {
		return insureRelation;
	}

	/**
	* 设置字段insureRelation的值，该字段的<br>
	* 字段名称 :申请人与被保人关系<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsureRelation(String insureRelation) {
		this.insureRelation = insureRelation;
    }

	/**
	* 获取字段applicationDate的值，该字段的<br>
	* 字段名称 :申请日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getapplicationDate() {
		return applicationDate;
	}

	/**
	* 设置字段applicationDate的值，该字段的<br>
	* 字段名称 :申请日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
    }

	/**
	* 获取字段status的值，该字段的<br>
	* 字段名称 :理赔状态<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstatus() {
		return status;
	}

	/**
	* 设置字段status的值，该字段的<br>
	* 字段名称 :理赔状态<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatus(String status) {
		this.status = status;
    }

	/**
	* 获取字段giroDate的值，该字段的<br>
	* 字段名称 :理赔款转账时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getgiroDate() {
		return giroDate;
	}

	/**
	* 设置字段giroDate的值，该字段的<br>
	* 字段名称 :理赔款转账时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgiroDate(String giroDate) {
		this.giroDate = giroDate;
    }

	/**
	* 获取字段claimsMoney的值，该字段的<br>
	* 字段名称 :理赔金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimsMoney() {
		return claimsMoney;
	}

	/**
	* 设置字段claimsMoney的值，该字段的<br>
	* 字段名称 :理赔金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsMoney(String claimsMoney) {
		this.claimsMoney = claimsMoney;
    }

	/**
	* 获取字段memberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemberId() {
		return memberId;
	}

	/**
	* 设置字段memberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemberId(String memberId) {
		this.memberId = memberId;
    }

	/**
	* 获取字段caseType的值，该字段的<br>
	* 字段名称 :案件类型<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcaseType() {
		return caseType;
	}

	/**
	* 设置字段caseType的值，该字段的<br>
	* 字段名称 :案件类型<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcaseType(String caseType) {
		this.caseType = caseType;
    }

	/**
	* 获取字段caseDesc的值，该字段的<br>
	* 字段名称 :案件描述<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcaseDesc() {
		return caseDesc;
	}

	/**
	* 设置字段caseDesc的值，该字段的<br>
	* 字段名称 :案件描述<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
    }

	/**
	* 获取字段referInsureCompany的值，该字段的<br>
	* 字段名称 :提交保险公司<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreferInsureCompany() {
		return referInsureCompany;
	}

	/**
	* 设置字段referInsureCompany的值，该字段的<br>
	* 字段名称 :提交保险公司<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreferInsureCompany(String referInsureCompany) {
		this.referInsureCompany = referInsureCompany;
    }

	/**
	* 获取字段sendAddress的值，该字段的<br>
	* 字段名称 :客户邮寄地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsendAddress() {
		return sendAddress;
	}

	/**
	* 设置字段sendAddress的值，该字段的<br>
	* 字段名称 :客户邮寄地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
    }

	/**
	* 获取字段sendDesc的值，该字段的<br>
	* 字段名称 :客户邮寄补充说明<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsendDesc() {
		return sendDesc;
	}

	/**
	* 设置字段sendDesc的值，该字段的<br>
	* 字段名称 :客户邮寄补充说明<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsendDesc(String sendDesc) {
		this.sendDesc = sendDesc;
    }

	/**
	* 获取字段courierFirm的值，该字段的<br>
	* 字段名称 :客户邮寄快递<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcourierFirm() {
		return courierFirm;
	}

	/**
	* 设置字段courierFirm的值，该字段的<br>
	* 字段名称 :客户邮寄快递<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcourierFirm(String courierFirm) {
		this.courierFirm = courierFirm;
    }

	/**
	* 获取字段courierNumber的值，该字段的<br>
	* 字段名称 :客户邮寄单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcourierNumber() {
		return courierNumber;
	}

	/**
	* 设置字段courierNumber的值，该字段的<br>
	* 字段名称 :客户邮寄单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcourierNumber(String courierNumber) {
		this.courierNumber = courierNumber;
    }

	/**
	* 获取字段receiveDate的值，该字段的<br>
	* 字段名称 :邮寄签收时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreceiveDate() {
		return receiveDate;
	}

	/**
	* 设置字段receiveDate的值，该字段的<br>
	* 字段名称 :邮寄签收时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
    }

	/**
	* 获取字段returnDesc的值，该字段的<br>
	* 字段名称 :退回原因<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreturnDesc() {
		return returnDesc;
	}

	/**
	* 设置字段returnDesc的值，该字段的<br>
	* 字段名称 :退回原因<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreturnDesc(String returnDesc) {
		this.returnDesc = returnDesc;
    }

	/**
	* 获取字段productId的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductId() {
		return productId;
	}

	/**
	* 设置字段productId的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductId(String productId) {
		this.productId = productId;
    }

	/**
	* 获取字段claimsItemsType的值，该字段的<br>
	* 字段名称 :理赔项目分类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimsItemsType() {
		return claimsItemsType;
	}

	/**
	* 设置字段claimsItemsType的值，该字段的<br>
	* 字段名称 :理赔项目分类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsItemsType(String claimsItemsType) {
		this.claimsItemsType = claimsItemsType;
    }

	/**
	* 获取字段bankName的值，该字段的<br>
	* 字段名称 :银行卡开户行名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbankName() {
		return bankName;
	}

	/**
	* 设置字段bankName的值，该字段的<br>
	* 字段名称 :银行卡开户行名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbankName(String bankName) {
		this.bankName = bankName;
    }

	/**
	* 获取字段bankUserName的值，该字段的<br>
	* 字段名称 :银行卡持有人姓名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbankUserName() {
		return bankUserName;
	}

	/**
	* 设置字段bankUserName的值，该字段的<br>
	* 字段名称 :银行卡持有人姓名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
    }

	/**
	* 获取字段happenTime的值，该字段的<br>
	* 字段名称 :出险时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gethappenTime() {
		return happenTime;
	}

	/**
	* 设置字段happenTime的值，该字段的<br>
	* 字段名称 :出险时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void sethappenTime(String happenTime) {
		this.happenTime = happenTime;
    }

	/**
	* 获取字段happenAddress的值，该字段的<br>
	* 字段名称 :出险地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gethappenAddress() {
		return happenAddress;
	}

	/**
	* 设置字段happenAddress的值，该字段的<br>
	* 字段名称 :出险地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void sethappenAddress(String happenAddress) {
		this.happenAddress = happenAddress;
    }

	/**
	* 获取字段kindOfLoss的值，该字段的<br>
	* 字段名称 :保险公司损失类型编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getkindOfLoss() {
		return kindOfLoss;
	}

	/**
	* 设置字段kindOfLoss的值，该字段的<br>
	* 字段名称 :保险公司损失类型编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setkindOfLoss(String kindOfLoss) {
		this.kindOfLoss = kindOfLoss;
    }

	/**
	* 获取字段claimReason的值，该字段的<br>
	* 字段名称 :保险公司理赔原因编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimReason() {
		return claimReason;
	}

	/**
	* 设置字段claimReason的值，该字段的<br>
	* 字段名称 :保险公司理赔原因编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimReason(String claimReason) {
		this.claimReason = claimReason;
    }

	/**
	* 获取字段notificationNo的值，该字段的<br>
	* 字段名称 :保险公司报案号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getnotificationNo() {
		return notificationNo;
	}

	/**
	* 设置字段notificationNo的值，该字段的<br>
	* 字段名称 :保险公司报案号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setnotificationNo(String notificationNo) {
		this.notificationNo = notificationNo;
    }

	/**
	* 获取字段isUpload的值，该字段的<br>
	* 字段名称 :是否已上传文件<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisUpload() {
		return isUpload;
	}

	/**
	* 设置字段isUpload的值，该字段的<br>
	* 字段名称 :是否已上传文件<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisUpload(String isUpload) {
		this.isUpload = isUpload;
    }

	/**
	* 获取字段flightNo的值，该字段的<br>
	* 字段名称 :航班号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getflightNo() {
		return flightNo;
	}

	/**
	* 设置字段flightNo的值，该字段的<br>
	* 字段名称 :航班号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setflightNo(String flightNo) {
		this.flightNo = flightNo;
    }

	/**
	* 获取字段flightTime的值，该字段的<br>
	* 字段名称 :计划起飞时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getflightTime() {
		return flightTime;
	}

	/**
	* 设置字段flightTime的值，该字段的<br>
	* 字段名称 :计划起飞时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setflightTime(String flightTime) {
		this.flightTime = flightTime;
    }

	/**
	* 获取字段cycle的值，该字段的<br>
	* 字段名称 :处理周期<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcycle() {
		return cycle;
	}

	/**
	* 设置字段cycle的值，该字段的<br>
	* 字段名称 :处理周期<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcycle(String cycle) {
		this.cycle = cycle;
    }

	/**
	* 获取字段isShowFlag的值，该字段的<br>
	* 字段名称 :是否展示<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisShowFlag() {
		return isShowFlag;
	}

	/**
	* 设置字段isShowFlag的值，该字段的<br>
	* 字段名称 :是否展示<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisShowFlag(String isShowFlag) {
		this.isShowFlag = isShowFlag;
    }

	/**
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

	/**
	* 获取字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark2() {
		return remark2;
	}

	/**
	* 设置字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark2(String remark2) {
		this.remark2 = remark2;
    }

	/**
	* 获取字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark3() {
		return remark3;
	}

	/**
	* 设置字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark3(String remark3) {
		this.remark3 = remark3;
    }

	/**
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

}