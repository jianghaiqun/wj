package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：PartnerPolicyReq<br>
 * 表代码：PartnerPolicyReq<br>
 * 表主键：id<br>
 */
public class PartnerPolicyReqSchema extends Schema {
	private String id;

	private String orderSn;

	private String productId;

	private String productOutId;

	private String accountNo;

	private String accountName;

	private String idCardNo;

	private String policyholderMobile;

	private String policyholderEmail;

	private String policyholderAddress;

	private String payAmount;

	private String expiredType;

	private Date payDateTime;

	private Date dealConfirmDateTime;

	private String paySn;

	private String fundTransferSn;

	private String policyNo;

	private String fundType;

	private String totalAmount;

	private String principalAmount;

	private String incomeAmount;

	private Date signDate;

	private String comment;

	private String electronicPolicyURL;

	private Date hesitationEndDate;

	private String isDataCorrect;

	private String isInsureSuccess;

	private String partnerId;

	private Date createDatetime;

	private Date modifyDatetime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("orderSn", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("productId", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("productOutId", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("accountNo", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("accountName", DataColumn.STRING, 5, 32 , 0 , false , false),
		new SchemaColumn("idCardNo", DataColumn.STRING, 6, 36 , 0 , false , false),
		new SchemaColumn("policyholderMobile", DataColumn.STRING, 7, 32 , 0 , false , false),
		new SchemaColumn("policyholderEmail", DataColumn.STRING, 8, 128 , 0 , false , false),
		new SchemaColumn("policyholderAddress", DataColumn.STRING, 9, 128 , 0 , false , false),
		new SchemaColumn("payAmount", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("expiredType", DataColumn.STRING, 11, 1 , 0 , false , false),
		new SchemaColumn("payDateTime", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("dealConfirmDateTime", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("paySn", DataColumn.STRING, 14, 32 , 0 , false , false),
		new SchemaColumn("fundTransferSn", DataColumn.STRING, 15, 32 , 0 , false , false),
		new SchemaColumn("policyNo", DataColumn.STRING, 16, 32 , 0 , false , false),
		new SchemaColumn("fundType", DataColumn.STRING, 17, 32 , 0 , false , false),
		new SchemaColumn("totalAmount", DataColumn.STRING, 18, 20 , 0 , false , false),
		new SchemaColumn("principalAmount", DataColumn.STRING, 19, 20 , 0 , false , false),
		new SchemaColumn("incomeAmount", DataColumn.STRING, 20, 20 , 0 , false , false),
		new SchemaColumn("signDate", DataColumn.DATETIME, 21, 0 , 0 , false , false),
		new SchemaColumn("comment", DataColumn.STRING, 22, 128 , 0 , false , false),
		new SchemaColumn("electronicPolicyURL", DataColumn.STRING, 23, 1024 , 0 , false , false),
		new SchemaColumn("hesitationEndDate", DataColumn.DATETIME, 24, 0 , 0 , false , false),
		new SchemaColumn("isDataCorrect", DataColumn.STRING, 25, 1 , 0 , false , false),
		new SchemaColumn("isInsureSuccess", DataColumn.STRING, 26, 1 , 0 , false , false),
		new SchemaColumn("partnerId", DataColumn.STRING, 27, 20 , 0 , false , false),
		new SchemaColumn("createDatetime", DataColumn.DATETIME, 28, 0 , 0 , false , false),
		new SchemaColumn("modifyDatetime", DataColumn.DATETIME, 29, 0 , 0 , false , false)
	};

	public static final String _TableCode = "PartnerPolicyReq";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PartnerPolicyReq values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PartnerPolicyReq set id=?,orderSn=?,productId=?,productOutId=?,accountNo=?,accountName=?,idCardNo=?,policyholderMobile=?,policyholderEmail=?,policyholderAddress=?,payAmount=?,expiredType=?,payDateTime=?,dealConfirmDateTime=?,paySn=?,fundTransferSn=?,policyNo=?,fundType=?,totalAmount=?,principalAmount=?,incomeAmount=?,signDate=?,comment=?,electronicPolicyURL=?,hesitationEndDate=?,isDataCorrect=?,isInsureSuccess=?,partnerId=?,createDatetime=?,modifyDatetime=? where id=?";

	protected static final String _DeleteSQL = "delete from PartnerPolicyReq  where id=?";

	protected static final String _FillAllSQL = "select * from PartnerPolicyReq  where id=?";

	public PartnerPolicyReqSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[30];
	}

	protected Schema newInstance(){
		return new PartnerPolicyReqSchema();
	}

	protected SchemaSet newSet(){
		return new PartnerPolicyReqSet();
	}

	public PartnerPolicyReqSet query() {
		return query(null, -1, -1);
	}

	public PartnerPolicyReqSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PartnerPolicyReqSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PartnerPolicyReqSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PartnerPolicyReqSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){orderSn = (String)v;return;}
		if (i == 2){productId = (String)v;return;}
		if (i == 3){productOutId = (String)v;return;}
		if (i == 4){accountNo = (String)v;return;}
		if (i == 5){accountName = (String)v;return;}
		if (i == 6){idCardNo = (String)v;return;}
		if (i == 7){policyholderMobile = (String)v;return;}
		if (i == 8){policyholderEmail = (String)v;return;}
		if (i == 9){policyholderAddress = (String)v;return;}
		if (i == 10){payAmount = (String)v;return;}
		if (i == 11){expiredType = (String)v;return;}
		if (i == 12){payDateTime = (Date)v;return;}
		if (i == 13){dealConfirmDateTime = (Date)v;return;}
		if (i == 14){paySn = (String)v;return;}
		if (i == 15){fundTransferSn = (String)v;return;}
		if (i == 16){policyNo = (String)v;return;}
		if (i == 17){fundType = (String)v;return;}
		if (i == 18){totalAmount = (String)v;return;}
		if (i == 19){principalAmount = (String)v;return;}
		if (i == 20){incomeAmount = (String)v;return;}
		if (i == 21){signDate = (Date)v;return;}
		if (i == 22){comment = (String)v;return;}
		if (i == 23){electronicPolicyURL = (String)v;return;}
		if (i == 24){hesitationEndDate = (Date)v;return;}
		if (i == 25){isDataCorrect = (String)v;return;}
		if (i == 26){isInsureSuccess = (String)v;return;}
		if (i == 27){partnerId = (String)v;return;}
		if (i == 28){createDatetime = (Date)v;return;}
		if (i == 29){modifyDatetime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return orderSn;}
		if (i == 2){return productId;}
		if (i == 3){return productOutId;}
		if (i == 4){return accountNo;}
		if (i == 5){return accountName;}
		if (i == 6){return idCardNo;}
		if (i == 7){return policyholderMobile;}
		if (i == 8){return policyholderEmail;}
		if (i == 9){return policyholderAddress;}
		if (i == 10){return payAmount;}
		if (i == 11){return expiredType;}
		if (i == 12){return payDateTime;}
		if (i == 13){return dealConfirmDateTime;}
		if (i == 14){return paySn;}
		if (i == 15){return fundTransferSn;}
		if (i == 16){return policyNo;}
		if (i == 17){return fundType;}
		if (i == 18){return totalAmount;}
		if (i == 19){return principalAmount;}
		if (i == 20){return incomeAmount;}
		if (i == 21){return signDate;}
		if (i == 22){return comment;}
		if (i == 23){return electronicPolicyURL;}
		if (i == 24){return hesitationEndDate;}
		if (i == 25){return isDataCorrect;}
		if (i == 26){return isInsureSuccess;}
		if (i == 27){return partnerId;}
		if (i == 28){return createDatetime;}
		if (i == 29){return modifyDatetime;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	id<br>
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
	* 备注信息 :<br>
	id<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :orderSn<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	orderSn<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :orderSn<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	orderSn<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段productId的值，该字段的<br>
	* 字段名称 :productId<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	productId<br>
	*/
	public String getproductId() {
		return productId;
	}

	/**
	* 设置字段productId的值，该字段的<br>
	* 字段名称 :productId<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	productId<br>
	*/
	public void setproductId(String productId) {
		this.productId = productId;
    }

	/**
	* 获取字段productOutId的值，该字段的<br>
	* 字段名称 :productOutId<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductOutId() {
		return productOutId;
	}

	/**
	* 设置字段productOutId的值，该字段的<br>
	* 字段名称 :productOutId<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductOutId(String productOutId) {
		this.productOutId = productOutId;
    }

	/**
	* 获取字段accountNo的值，该字段的<br>
	* 字段名称 :accountNo<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	accountNo<br>
	*/
	public String getaccountNo() {
		return accountNo;
	}

	/**
	* 设置字段accountNo的值，该字段的<br>
	* 字段名称 :accountNo<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	accountNo<br>
	*/
	public void setaccountNo(String accountNo) {
		this.accountNo = accountNo;
    }

	/**
	* 获取字段accountName的值，该字段的<br>
	* 字段名称 :accountName<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	accountName<br>
	*/
	public String getaccountName() {
		return accountName;
	}

	/**
	* 设置字段accountName的值，该字段的<br>
	* 字段名称 :accountName<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	accountName<br>
	*/
	public void setaccountName(String accountName) {
		this.accountName = accountName;
    }

	/**
	* 获取字段idCardNo的值，该字段的<br>
	* 字段名称 :idCardNo<br>
	* 数据类型 :varchar(36)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	idCardNo<br>
	*/
	public String getidCardNo() {
		return idCardNo;
	}

	/**
	* 设置字段idCardNo的值，该字段的<br>
	* 字段名称 :idCardNo<br>
	* 数据类型 :varchar(36)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	idCardNo<br>
	*/
	public void setidCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
    }

	/**
	* 获取字段policyholderMobile的值，该字段的<br>
	* 字段名称 :policyholderMobile<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	policyholderMobile<br>
	*/
	public String getpolicyholderMobile() {
		return policyholderMobile;
	}

	/**
	* 设置字段policyholderMobile的值，该字段的<br>
	* 字段名称 :policyholderMobile<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	policyholderMobile<br>
	*/
	public void setpolicyholderMobile(String policyholderMobile) {
		this.policyholderMobile = policyholderMobile;
    }

	/**
	* 获取字段policyholderEmail的值，该字段的<br>
	* 字段名称 :policyholderEmail<br>
	* 数据类型 :varchar(128)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	policyholderEmail<br>
	*/
	public String getpolicyholderEmail() {
		return policyholderEmail;
	}

	/**
	* 设置字段policyholderEmail的值，该字段的<br>
	* 字段名称 :policyholderEmail<br>
	* 数据类型 :varchar(128)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	policyholderEmail<br>
	*/
	public void setpolicyholderEmail(String policyholderEmail) {
		this.policyholderEmail = policyholderEmail;
    }

	/**
	* 获取字段policyholderAddress的值，该字段的<br>
	* 字段名称 :policyholderAddress<br>
	* 数据类型 :varchar(128)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	policyholderAddress<br>
	*/
	public String getpolicyholderAddress() {
		return policyholderAddress;
	}

	/**
	* 设置字段policyholderAddress的值，该字段的<br>
	* 字段名称 :policyholderAddress<br>
	* 数据类型 :varchar(128)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	policyholderAddress<br>
	*/
	public void setpolicyholderAddress(String policyholderAddress) {
		this.policyholderAddress = policyholderAddress;
    }

	/**
	* 获取字段payAmount的值，该字段的<br>
	* 字段名称 :payAmount<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	policyPrice<br>
	*/
	public String getpayAmount() {
		return payAmount;
	}

	/**
	* 设置字段payAmount的值，该字段的<br>
	* 字段名称 :payAmount<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	policyPrice<br>
	*/
	public void setpayAmount(String payAmount) {
		this.payAmount = payAmount;
    }

	/**
	* 获取字段expiredType的值，该字段的<br>
	* 字段名称 :expiredType<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	expiredType<br>
	*/
	public String getexpiredType() {
		return expiredType;
	}

	/**
	* 设置字段expiredType的值，该字段的<br>
	* 字段名称 :expiredType<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	expiredType<br>
	*/
	public void setexpiredType(String expiredType) {
		this.expiredType = expiredType;
    }

	/**
	* 获取字段payDateTime的值，该字段的<br>
	* 字段名称 :payDateTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	payDateTime<br>
	*/
	public Date getpayDateTime() {
		return payDateTime;
	}

	/**
	* 设置字段payDateTime的值，该字段的<br>
	* 字段名称 :payDateTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	payDateTime<br>
	*/
	public void setpayDateTime(Date payDateTime) {
		this.payDateTime = payDateTime;
    }

	/**
	* 获取字段dealConfirmDateTime的值，该字段的<br>
	* 字段名称 :dealConfirmDateTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	dealConfirmDateTime<br>
	*/
	public Date getdealConfirmDateTime() {
		return dealConfirmDateTime;
	}

	/**
	* 设置字段dealConfirmDateTime的值，该字段的<br>
	* 字段名称 :dealConfirmDateTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	dealConfirmDateTime<br>
	*/
	public void setdealConfirmDateTime(Date dealConfirmDateTime) {
		this.dealConfirmDateTime = dealConfirmDateTime;
    }

	/**
	* 获取字段paySn的值，该字段的<br>
	* 字段名称 :paySn<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	paySn<br>
	*/
	public String getpaySn() {
		return paySn;
	}

	/**
	* 设置字段paySn的值，该字段的<br>
	* 字段名称 :paySn<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	paySn<br>
	*/
	public void setpaySn(String paySn) {
		this.paySn = paySn;
    }

	/**
	* 获取字段fundTransferSn的值，该字段的<br>
	* 字段名称 :fundTransferSn<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfundTransferSn() {
		return fundTransferSn;
	}

	/**
	* 设置字段fundTransferSn的值，该字段的<br>
	* 字段名称 :fundTransferSn<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfundTransferSn(String fundTransferSn) {
		this.fundTransferSn = fundTransferSn;
    }

	/**
	* 获取字段policyNo的值，该字段的<br>
	* 字段名称 :policyNo<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpolicyNo() {
		return policyNo;
	}

	/**
	* 设置字段policyNo的值，该字段的<br>
	* 字段名称 :policyNo<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpolicyNo(String policyNo) {
		this.policyNo = policyNo;
    }

	/**
	* 获取字段fundType的值，该字段的<br>
	* 字段名称 :fundType<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfundType() {
		return fundType;
	}

	/**
	* 设置字段fundType的值，该字段的<br>
	* 字段名称 :fundType<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfundType(String fundType) {
		this.fundType = fundType;
    }

	/**
	* 获取字段totalAmount的值，该字段的<br>
	* 字段名称 :totalAmount<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettotalAmount() {
		return totalAmount;
	}

	/**
	* 设置字段totalAmount的值，该字段的<br>
	* 字段名称 :totalAmount<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
    }

	/**
	* 获取字段principalAmount的值，该字段的<br>
	* 字段名称 :principalAmount<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprincipalAmount() {
		return principalAmount;
	}

	/**
	* 设置字段principalAmount的值，该字段的<br>
	* 字段名称 :principalAmount<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprincipalAmount(String principalAmount) {
		this.principalAmount = principalAmount;
    }

	/**
	* 获取字段incomeAmount的值，该字段的<br>
	* 字段名称 :incomeAmount<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getincomeAmount() {
		return incomeAmount;
	}

	/**
	* 设置字段incomeAmount的值，该字段的<br>
	* 字段名称 :incomeAmount<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setincomeAmount(String incomeAmount) {
		this.incomeAmount = incomeAmount;
    }

	/**
	* 获取字段signDate的值，该字段的<br>
	* 字段名称 :signDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getsignDate() {
		return signDate;
	}

	/**
	* 设置字段signDate的值，该字段的<br>
	* 字段名称 :signDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsignDate(Date signDate) {
		this.signDate = signDate;
    }

	/**
	* 获取字段comment的值，该字段的<br>
	* 字段名称 :comment<br>
	* 数据类型 :varchar(128)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcomment() {
		return comment;
	}

	/**
	* 设置字段comment的值，该字段的<br>
	* 字段名称 :comment<br>
	* 数据类型 :varchar(128)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcomment(String comment) {
		this.comment = comment;
    }

	/**
	* 获取字段electronicPolicyURL的值，该字段的<br>
	* 字段名称 :electronicPolicyURL<br>
	* 数据类型 :varchar(1024)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getelectronicPolicyURL() {
		return electronicPolicyURL;
	}

	/**
	* 设置字段electronicPolicyURL的值，该字段的<br>
	* 字段名称 :electronicPolicyURL<br>
	* 数据类型 :varchar(1024)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setelectronicPolicyURL(String electronicPolicyURL) {
		this.electronicPolicyURL = electronicPolicyURL;
    }

	/**
	* 获取字段hesitationEndDate的值，该字段的<br>
	* 字段名称 :hesitationEndDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date gethesitationEndDate() {
		return hesitationEndDate;
	}

	/**
	* 设置字段hesitationEndDate的值，该字段的<br>
	* 字段名称 :hesitationEndDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void sethesitationEndDate(Date hesitationEndDate) {
		this.hesitationEndDate = hesitationEndDate;
    }

	/**
	* 获取字段isDataCorrect的值，该字段的<br>
	* 字段名称 :isDataCorrect<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisDataCorrect() {
		return isDataCorrect;
	}

	/**
	* 设置字段isDataCorrect的值，该字段的<br>
	* 字段名称 :isDataCorrect<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisDataCorrect(String isDataCorrect) {
		this.isDataCorrect = isDataCorrect;
    }

	/**
	* 获取字段isInsureSuccess的值，该字段的<br>
	* 字段名称 :isInsureSuccess<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisInsureSuccess() {
		return isInsureSuccess;
	}

	/**
	* 设置字段isInsureSuccess的值，该字段的<br>
	* 字段名称 :isInsureSuccess<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisInsureSuccess(String isInsureSuccess) {
		this.isInsureSuccess = isInsureSuccess;
    }

	/**
	* 获取字段partnerId的值，该字段的<br>
	* 字段名称 :partnerId<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpartnerId() {
		return partnerId;
	}

	/**
	* 设置字段partnerId的值，该字段的<br>
	* 字段名称 :partnerId<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpartnerId(String partnerId) {
		this.partnerId = partnerId;
    }

	/**
	* 获取字段createDatetime的值，该字段的<br>
	* 字段名称 :createDatetime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDatetime() {
		return createDatetime;
	}

	/**
	* 设置字段createDatetime的值，该字段的<br>
	* 字段名称 :createDatetime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
    }

	/**
	* 获取字段modifyDatetime的值，该字段的<br>
	* 字段名称 :modifyDatetime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDatetime() {
		return modifyDatetime;
	}

	/**
	* 设置字段modifyDatetime的值，该字段的<br>
	* 字段名称 :modifyDatetime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
    }

}