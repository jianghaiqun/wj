package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：保单信息表<br>
 * 表代码：SDInformationRiskType<br>
 * 表主键：Id<br>
 */
public class SDInformationRiskTypeSchema extends Schema {
	private String Id;

	private Date createDate;

	private Date modifyDate;

	private String orderSn;

	private String informationSn;

	private String recognizeeSn;

	private String applicantSn;

	private String policyNo;

	private String applyPolicyNo;

	private String riskCode;

	private String riskName;

	private String amnt;

	private String timePrem;

	private String mult;

	private Date svaliDate;

	private Date evaliDate;

	private String periodFlag;

	private String period;

	private String electronicCout;

	private String electronicPath;

	private String insurerFlag;

	private String insureMsg;

	private String insureDate;

	private String balanceStatus;

	private String balanceFlag;

	private String balanceMsg;

	private Date balanceDate;

	private String appStatus;

	private String changeStatus;

	private String noticeNo;

	private String validateCode;

	private String insuranceTransCode;

	private String insuranceBankCode;

	private String insuranceBankSeriNO;

	private String insuranceBRNO;

	private String insuranceTELLERNO;

	private String returnPremiums;

	private String productPrice;

	private String sdinformationinsured_id;

	private String sdorder_id;

	private Integer operationflag;

	private Date cancelDate;

	private String couponValue;

	private String integralValue;

	private String activityValue;

	private String payPrice;
	
	private String policyNoOld;// 原保单号

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("informationSn", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("recognizeeSn", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("applicantSn", DataColumn.STRING, 6, 20 , 0 , true , false),
		new SchemaColumn("policyNo", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("applyPolicyNo", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("riskCode", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("riskName", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("amnt", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("timePrem", DataColumn.STRING, 12, 20 , 0 , false , false),
		new SchemaColumn("mult", DataColumn.STRING, 13, 5 , 0 , false , false),
		new SchemaColumn("svaliDate", DataColumn.DATETIME, 14, 0 , 0 , false , false),
		new SchemaColumn("evaliDate", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("periodFlag", DataColumn.STRING, 16, 20 , 0 , false , false),
		new SchemaColumn("period", DataColumn.STRING, 17, 20 , 0 , false , false),
		new SchemaColumn("electronicCout", DataColumn.STRING, 18, 100 , 0 , false , false),
		new SchemaColumn("electronicPath", DataColumn.STRING, 19, 100 , 0 , false , false),
		new SchemaColumn("insurerFlag", DataColumn.STRING, 20, 50 , 0 , false , false),
		new SchemaColumn("insureMsg", DataColumn.STRING, 21, 1000 , 0 , false , false),
		new SchemaColumn("insureDate", DataColumn.STRING, 22, 20 , 0 , false , false),
		new SchemaColumn("balanceStatus", DataColumn.STRING, 23, 50 , 0 , false , false),
		new SchemaColumn("balanceFlag", DataColumn.STRING, 24, 5 , 0 , false , false),
		new SchemaColumn("balanceMsg", DataColumn.STRING, 25, 50 , 0 , false , false),
		new SchemaColumn("balanceDate", DataColumn.DATETIME, 26, 0 , 0 , false , false),
		new SchemaColumn("appStatus", DataColumn.STRING, 27, 4 , 0 , false , false),
		new SchemaColumn("changeStatus", DataColumn.STRING, 28, 2 , 0 , false , false),
		new SchemaColumn("noticeNo", DataColumn.STRING, 29, 50 , 0 , false , false),
		new SchemaColumn("validateCode", DataColumn.STRING, 30, 50 , 0 , false , false),
		new SchemaColumn("insuranceTransCode", DataColumn.STRING, 31, 50 , 0 , false , false),
		new SchemaColumn("insuranceBankCode", DataColumn.STRING, 32, 50 , 0 , false , false),
		new SchemaColumn("insuranceBankSeriNO", DataColumn.STRING, 33, 50 , 0 , false , false),
		new SchemaColumn("insuranceBRNO", DataColumn.STRING, 34, 50 , 0 , false , false),
		new SchemaColumn("insuranceTELLERNO", DataColumn.STRING, 35, 50 , 0 , false , false),
		new SchemaColumn("returnPremiums", DataColumn.STRING, 36, 20 , 0 , false , false),
		new SchemaColumn("productPrice", DataColumn.STRING, 37, 20 , 0 , false , false),
		new SchemaColumn("sdinformationinsured_id", DataColumn.STRING, 38, 32 , 0 , false , false),
		new SchemaColumn("sdorder_id", DataColumn.STRING, 39, 32 , 0 , false , false),
		new SchemaColumn("operationflag", DataColumn.INTEGER, 40, 2 , 0 , false , false),
		new SchemaColumn("cancelDate", DataColumn.DATETIME, 41, 0 , 0 , false , false),
		new SchemaColumn("couponValue", DataColumn.STRING, 42, 10 , 0 , false , false),
		new SchemaColumn("integralValue", DataColumn.STRING, 43, 10 , 0 , false , false),
		new SchemaColumn("activityValue", DataColumn.STRING, 44, 10 , 0 , false , false),
		new SchemaColumn("payPrice", DataColumn.STRING, 45, 10 , 0 , false , false),
		new SchemaColumn("policyNoOld", DataColumn.STRING, 46, 255 , 0 , false , false)
	};

	public static final String _TableCode = "SDInformationRiskType";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDInformationRiskType values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDInformationRiskType set Id=?,createDate=?,modifyDate=?,orderSn=?,informationSn=?,recognizeeSn=?,applicantSn=?,policyNo=?,applyPolicyNo=?,riskCode=?,riskName=?,amnt=?,timePrem=?,mult=?,svaliDate=?,evaliDate=?,periodFlag=?,period=?,electronicCout=?,electronicPath=?,insurerFlag=?,insureMsg=?,insureDate=?,balanceStatus=?,balanceFlag=?,balanceMsg=?,balanceDate=?,appStatus=?,changeStatus=?,noticeNo=?,validateCode=?,insuranceTransCode=?,insuranceBankCode=?,insuranceBankSeriNO=?,insuranceBRNO=?,insuranceTELLERNO=?,returnPremiums=?,productPrice=?,sdinformationinsured_id=?,sdorder_id=?,operationflag=?,cancelDate=?,couponValue=?,integralValue=?,activityValue=?,payPrice=?,policyNoOld=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDInformationRiskType  where Id=?";

	protected static final String _FillAllSQL = "select * from SDInformationRiskType  where Id=?";

	public SDInformationRiskTypeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[47];
	}

	protected Schema newInstance(){
		return new SDInformationRiskTypeSchema();
	}

	protected SchemaSet newSet(){
		return new SDInformationRiskTypeSet();
	}

	public SDInformationRiskTypeSet query() {
		return query(null, -1, -1);
	}

	public SDInformationRiskTypeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDInformationRiskTypeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDInformationRiskTypeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDInformationRiskTypeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){orderSn = (String)v;return;}
		if (i == 4){informationSn = (String)v;return;}
		if (i == 5){recognizeeSn = (String)v;return;}
		if (i == 6){applicantSn = (String)v;return;}
		if (i == 7){policyNo = (String)v;return;}
		if (i == 8){applyPolicyNo = (String)v;return;}
		if (i == 9){riskCode = (String)v;return;}
		if (i == 10){riskName = (String)v;return;}
		if (i == 11){amnt = (String)v;return;}
		if (i == 12){timePrem = (String)v;return;}
		if (i == 13){mult = (String)v;return;}
		if (i == 14){svaliDate = (Date)v;return;}
		if (i == 15){evaliDate = (Date)v;return;}
		if (i == 16){periodFlag = (String)v;return;}
		if (i == 17){period = (String)v;return;}
		if (i == 18){electronicCout = (String)v;return;}
		if (i == 19){electronicPath = (String)v;return;}
		if (i == 20){insurerFlag = (String)v;return;}
		if (i == 21){insureMsg = (String)v;return;}
		if (i == 22){insureDate = (String)v;return;}
		if (i == 23){balanceStatus = (String)v;return;}
		if (i == 24){balanceFlag = (String)v;return;}
		if (i == 25){balanceMsg = (String)v;return;}
		if (i == 26){balanceDate = (Date)v;return;}
		if (i == 27){appStatus = (String)v;return;}
		if (i == 28){changeStatus = (String)v;return;}
		if (i == 29){noticeNo = (String)v;return;}
		if (i == 30){validateCode = (String)v;return;}
		if (i == 31){insuranceTransCode = (String)v;return;}
		if (i == 32){insuranceBankCode = (String)v;return;}
		if (i == 33){insuranceBankSeriNO = (String)v;return;}
		if (i == 34){insuranceBRNO = (String)v;return;}
		if (i == 35){insuranceTELLERNO = (String)v;return;}
		if (i == 36){returnPremiums = (String)v;return;}
		if (i == 37){productPrice = (String)v;return;}
		if (i == 38){sdinformationinsured_id = (String)v;return;}
		if (i == 39){sdorder_id = (String)v;return;}
		if (i == 40){if(v==null){operationflag = null;}else{operationflag = new Integer(v.toString());}return;}
		if (i == 41){cancelDate = (Date)v;return;}
		if (i == 42){couponValue = (String)v;return;}
		if (i == 43){integralValue = (String)v;return;}
		if (i == 44){activityValue = (String)v;return;}
		if (i == 45){payPrice = (String)v;return;}
		if (i == 46){policyNoOld = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return orderSn;}
		if (i == 4){return informationSn;}
		if (i == 5){return recognizeeSn;}
		if (i == 6){return applicantSn;}
		if (i == 7){return policyNo;}
		if (i == 8){return applyPolicyNo;}
		if (i == 9){return riskCode;}
		if (i == 10){return riskName;}
		if (i == 11){return amnt;}
		if (i == 12){return timePrem;}
		if (i == 13){return mult;}
		if (i == 14){return svaliDate;}
		if (i == 15){return evaliDate;}
		if (i == 16){return periodFlag;}
		if (i == 17){return period;}
		if (i == 18){return electronicCout;}
		if (i == 19){return electronicPath;}
		if (i == 20){return insurerFlag;}
		if (i == 21){return insureMsg;}
		if (i == 22){return insureDate;}
		if (i == 23){return balanceStatus;}
		if (i == 24){return balanceFlag;}
		if (i == 25){return balanceMsg;}
		if (i == 26){return balanceDate;}
		if (i == 27){return appStatus;}
		if (i == 28){return changeStatus;}
		if (i == 29){return noticeNo;}
		if (i == 30){return validateCode;}
		if (i == 31){return insuranceTransCode;}
		if (i == 32){return insuranceBankCode;}
		if (i == 33){return insuranceBankSeriNO;}
		if (i == 34){return insuranceBRNO;}
		if (i == 35){return insuranceTELLERNO;}
		if (i == 36){return returnPremiums;}
		if (i == 37){return productPrice;}
		if (i == 38){return sdinformationinsured_id;}
		if (i == 39){return sdorder_id;}
		if (i == 40){return operationflag;}
		if (i == 41){return cancelDate;}
		if (i == 42){return couponValue;}
		if (i == 43){return integralValue;}
		if (i == 44){return activityValue;}
		if (i == 45){return payPrice;}
		if (i == 46){return policyNoOld;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
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

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段informationSn的值，该字段的<br>
	* 字段名称 :订单明细表编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinformationSn() {
		return informationSn;
	}

	/**
	* 设置字段informationSn的值，该字段的<br>
	* 字段名称 :订单明细表编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinformationSn(String informationSn) {
		this.informationSn = informationSn;
    }

	/**
	* 获取字段recognizeeSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getrecognizeeSn() {
		return recognizeeSn;
	}

	/**
	* 设置字段recognizeeSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setrecognizeeSn(String recognizeeSn) {
		this.recognizeeSn = recognizeeSn;
    }

	/**
	* 获取字段applicantSn的值，该字段的<br>
	* 字段名称 :投保人编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getapplicantSn() {
		return applicantSn;
	}

	/**
	* 设置字段applicantSn的值，该字段的<br>
	* 字段名称 :投保人编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setapplicantSn(String applicantSn) {
		this.applicantSn = applicantSn;
    }

	/**
	* 获取字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	I开头表示个单 G开头表示团单<br>
	*/
	public String getpolicyNo() {
		return policyNo;
	}

	/**
	* 设置字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	I开头表示个单 G开头表示团单<br>
	*/
	public void setpolicyNo(String policyNo) {
		this.policyNo = policyNo;
    }

	/**
	* 获取字段applyPolicyNo的值，该字段的<br>
	* 字段名称 :投保单号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplyPolicyNo() {
		return applyPolicyNo;
	}

	/**
	* 设置字段applyPolicyNo的值，该字段的<br>
	* 字段名称 :投保单号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplyPolicyNo(String applyPolicyNo) {
		this.applyPolicyNo = applyPolicyNo;
    }

	/**
	* 获取字段riskCode的值，该字段的<br>
	* 字段名称 :险种编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getriskCode() {
		return riskCode;
	}

	/**
	* 设置字段riskCode的值，该字段的<br>
	* 字段名称 :险种编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setriskCode(String riskCode) {
		this.riskCode = riskCode;
    }

	/**
	* 获取字段riskName的值，该字段的<br>
	* 字段名称 :险种名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getriskName() {
		return riskName;
	}

	/**
	* 设置字段riskName的值，该字段的<br>
	* 字段名称 :险种名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setriskName(String riskName) {
		this.riskName = riskName;
    }

	/**
	* 获取字段amnt的值，该字段的<br>
	* 字段名称 :保险金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getamnt() {
		return amnt;
	}

	/**
	* 设置字段amnt的值，该字段的<br>
	* 字段名称 :保险金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setamnt(String amnt) {
		this.amnt = amnt;
    }

	/**
	* 获取字段timePrem的值，该字段的<br>
	* 字段名称 :保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettimePrem() {
		return timePrem;
	}

	/**
	* 设置字段timePrem的值，该字段的<br>
	* 字段名称 :保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settimePrem(String timePrem) {
		this.timePrem = timePrem;
    }

	/**
	* 获取字段mult的值，该字段的<br>
	* 字段名称 :份数<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmult() {
		return mult;
	}

	/**
	* 设置字段mult的值，该字段的<br>
	* 字段名称 :份数<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmult(String mult) {
		this.mult = mult;
    }

	/**
	* 获取字段svaliDate的值，该字段的<br>
	* 字段名称 :生效日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getsvaliDate() {
		return svaliDate;
	}

	/**
	* 设置字段svaliDate的值，该字段的<br>
	* 字段名称 :生效日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsvaliDate(Date svaliDate) {
		this.svaliDate = svaliDate;
    }

	/**
	* 获取字段evaliDate的值，该字段的<br>
	* 字段名称 :失效日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getevaliDate() {
		return evaliDate;
	}

	/**
	* 设置字段evaliDate的值，该字段的<br>
	* 字段名称 :失效日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setevaliDate(Date evaliDate) {
		this.evaliDate = evaliDate;
    }

	/**
	* 获取字段periodFlag的值，该字段的<br>
	* 字段名称 :交费年期/年龄标志<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getperiodFlag() {
		return periodFlag;
	}

	/**
	* 设置字段periodFlag的值，该字段的<br>
	* 字段名称 :交费年期/年龄标志<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setperiodFlag(String periodFlag) {
		this.periodFlag = periodFlag;
    }

	/**
	* 获取字段period的值，该字段的<br>
	* 字段名称 :交费年期/年龄<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getperiod() {
		return period;
	}

	/**
	* 设置字段period的值，该字段的<br>
	* 字段名称 :交费年期/年龄<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setperiod(String period) {
		this.period = period;
    }

	/**
	* 获取字段electronicCout的值，该字段的<br>
	* 字段名称 :电子保单保险公司路径<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getelectronicCout() {
		return electronicCout;
	}

	/**
	* 设置字段electronicCout的值，该字段的<br>
	* 字段名称 :电子保单保险公司路径<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setelectronicCout(String electronicCout) {
		this.electronicCout = electronicCout;
    }

	/**
	* 获取字段electronicPath的值，该字段的<br>
	* 字段名称 :电子保单物理路径<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getelectronicPath() {
		return electronicPath;
	}

	/**
	* 设置字段electronicPath的值，该字段的<br>
	* 字段名称 :电子保单物理路径<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setelectronicPath(String electronicPath) {
		this.electronicPath = electronicPath;
    }

	/**
	* 获取字段insurerFlag的值，该字段的<br>
	* 字段名称 :保险公司返回结果标记<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsurerFlag() {
		return insurerFlag;
	}

	/**
	* 设置字段insurerFlag的值，该字段的<br>
	* 字段名称 :保险公司返回结果标记<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsurerFlag(String insurerFlag) {
		this.insurerFlag = insurerFlag;
    }

	/**
	* 获取字段insureMsg的值，该字段的<br>
	* 字段名称 :保险公司返回描述<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsureMsg() {
		return insureMsg;
	}

	/**
	* 设置字段insureMsg的值，该字段的<br>
	* 字段名称 :保险公司返回描述<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsureMsg(String insureMsg) {
		this.insureMsg = insureMsg;
    }

	/**
	* 获取字段insureDate的值，该字段的<br>
	* 字段名称 :保险公司调用时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsureDate() {
		return insureDate;
	}

	/**
	* 设置字段insureDate的值，该字段的<br>
	* 字段名称 :保险公司调用时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsureDate(String insureDate) {
		this.insureDate = insureDate;
    }

	/**
	* 获取字段balanceStatus的值，该字段的<br>
	* 字段名称 :结算状态<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbalanceStatus() {
		return balanceStatus;
	}

	/**
	* 设置字段balanceStatus的值，该字段的<br>
	* 字段名称 :结算状态<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbalanceStatus(String balanceStatus) {
		this.balanceStatus = balanceStatus;
    }

	/**
	* 获取字段balanceFlag的值，该字段的<br>
	* 字段名称 :结算标记<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbalanceFlag() {
		return balanceFlag;
	}

	/**
	* 设置字段balanceFlag的值，该字段的<br>
	* 字段名称 :结算标记<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbalanceFlag(String balanceFlag) {
		this.balanceFlag = balanceFlag;
    }

	/**
	* 获取字段balanceMsg的值，该字段的<br>
	* 字段名称 :结算描述<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbalanceMsg() {
		return balanceMsg;
	}

	/**
	* 设置字段balanceMsg的值，该字段的<br>
	* 字段名称 :结算描述<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbalanceMsg(String balanceMsg) {
		this.balanceMsg = balanceMsg;
    }

	/**
	* 获取字段balanceDate的值，该字段的<br>
	* 字段名称 :结算时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getbalanceDate() {
		return balanceDate;
	}

	/**
	* 设置字段balanceDate的值，该字段的<br>
	* 字段名称 :结算时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
    }

	/**
	* 获取字段appStatus的值，该字段的<br>
	* 字段名称 :承保标记<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getappStatus() {
		return appStatus;
	}

	/**
	* 设置字段appStatus的值，该字段的<br>
	* 字段名称 :承保标记<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setappStatus(String appStatus) {
		this.appStatus = appStatus;
    }

	/**
	* 获取字段changeStatus的值，该字段的<br>
	* 字段名称 :变更标记<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchangeStatus() {
		return changeStatus;
	}

	/**
	* 设置字段changeStatus的值，该字段的<br>
	* 字段名称 :变更标记<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchangeStatus(String changeStatus) {
		this.changeStatus = changeStatus;
    }

	/**
	* 获取字段noticeNo的值，该字段的<br>
	* 字段名称 :财务通知单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getnoticeNo() {
		return noticeNo;
	}

	/**
	* 设置字段noticeNo的值，该字段的<br>
	* 字段名称 :财务通知单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setnoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
    }

	/**
	* 获取字段validateCode的值，该字段的<br>
	* 字段名称 :保单验证码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getvalidateCode() {
		return validateCode;
	}

	/**
	* 设置字段validateCode的值，该字段的<br>
	* 字段名称 :保单验证码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setvalidateCode(String validateCode) {
		this.validateCode = validateCode;
    }

	/**
	* 获取字段insuranceTransCode的值，该字段的<br>
	* 字段名称 :保险公司返回交易流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceTransCode() {
		return insuranceTransCode;
	}

	/**
	* 设置字段insuranceTransCode的值，该字段的<br>
	* 字段名称 :保险公司返回交易流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceTransCode(String insuranceTransCode) {
		this.insuranceTransCode = insuranceTransCode;
    }

	/**
	* 获取字段insuranceBankCode的值，该字段的<br>
	* 字段名称 :保险公司返回银行编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceBankCode() {
		return insuranceBankCode;
	}

	/**
	* 设置字段insuranceBankCode的值，该字段的<br>
	* 字段名称 :保险公司返回银行编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceBankCode(String insuranceBankCode) {
		this.insuranceBankCode = insuranceBankCode;
    }

	/**
	* 获取字段insuranceBankSeriNO的值，该字段的<br>
	* 字段名称 :保险公司返回银行交易流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceBankSeriNO() {
		return insuranceBankSeriNO;
	}

	/**
	* 设置字段insuranceBankSeriNO的值，该字段的<br>
	* 字段名称 :保险公司返回银行交易流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceBankSeriNO(String insuranceBankSeriNO) {
		this.insuranceBankSeriNO = insuranceBankSeriNO;
    }

	/**
	* 获取字段insuranceBRNO的值，该字段的<br>
	* 字段名称 :保险公司返回系列号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceBRNO() {
		return insuranceBRNO;
	}

	/**
	* 设置字段insuranceBRNO的值，该字段的<br>
	* 字段名称 :保险公司返回系列号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceBRNO(String insuranceBRNO) {
		this.insuranceBRNO = insuranceBRNO;
    }

	/**
	* 获取字段insuranceTELLERNO的值，该字段的<br>
	* 字段名称 :保险公司反回商户号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceTELLERNO() {
		return insuranceTELLERNO;
	}

	/**
	* 设置字段insuranceTELLERNO的值，该字段的<br>
	* 字段名称 :保险公司反回商户号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceTELLERNO(String insuranceTELLERNO) {
		this.insuranceTELLERNO = insuranceTELLERNO;
    }

	/**
	* 获取字段returnPremiums的值，该字段的<br>
	* 字段名称 :保险公司返回的保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreturnPremiums() {
		return returnPremiums;
	}

	/**
	* 设置字段returnPremiums的值，该字段的<br>
	* 字段名称 :保险公司返回的保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreturnPremiums(String returnPremiums) {
		this.returnPremiums = returnPremiums;
    }

	/**
	* 获取字段productPrice的值，该字段的<br>
	* 字段名称 :原保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductPrice() {
		return productPrice;
	}

	/**
	* 设置字段productPrice的值，该字段的<br>
	* 字段名称 :原保费<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductPrice(String productPrice) {
		this.productPrice = productPrice;
    }

	/**
	* 获取字段sdinformationinsured_id的值，该字段的<br>
	* 字段名称 :被保人编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsdinformationinsured_id() {
		return sdinformationinsured_id;
	}

	/**
	* 设置字段sdinformationinsured_id的值，该字段的<br>
	* 字段名称 :被保人编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsdinformationinsured_id(String sdinformationinsured_id) {
		this.sdinformationinsured_id = sdinformationinsured_id;
    }

	/**
	* 获取字段sdorder_id的值，该字段的<br>
	* 字段名称 :订单表ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsdorder_id() {
		return sdorder_id;
	}

	/**
	* 设置字段sdorder_id的值，该字段的<br>
	* 字段名称 :订单表ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsdorder_id(String sdorder_id) {
		this.sdorder_id = sdorder_id;
    }

	/**
	* 获取字段operationflag的值，该字段的<br>
	* 字段名称 :操作次数标志<br>
	* 数据类型 :int(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getoperationflag() {
		if(operationflag==null){return 0;}
		return operationflag.intValue();
	}

	/**
	* 设置字段operationflag的值，该字段的<br>
	* 字段名称 :操作次数标志<br>
	* 数据类型 :int(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoperationflag(int operationflag) {
		this.operationflag = new Integer(operationflag);
    }

	/**
	* 设置字段operationflag的值，该字段的<br>
	* 字段名称 :操作次数标志<br>
	* 数据类型 :int(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoperationflag(String operationflag) {
		if (operationflag == null){
			this.operationflag = null;
			return;
		}
		this.operationflag = new Integer(operationflag);
    }

	/**
	* 获取字段cancelDate的值，该字段的<br>
	* 字段名称 :撤单时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcancelDate() {
		return cancelDate;
	}

	/**
	* 设置字段cancelDate的值，该字段的<br>
	* 字段名称 :撤单时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
    }

	/**
	* 获取字段couponValue的值，该字段的<br>
	* 字段名称 :保单优惠券优惠金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcouponValue() {
		return couponValue;
	}

	/**
	* 设置字段couponValue的值，该字段的<br>
	* 字段名称 :保单优惠券优惠金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcouponValue(String couponValue) {
		this.couponValue = couponValue;
    }

	/**
	* 获取字段integralValue的值，该字段的<br>
	* 字段名称 :保单积分抵值金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getintegralValue() {
		return integralValue;
	}

	/**
	* 设置字段integralValue的值，该字段的<br>
	* 字段名称 :保单积分抵值金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setintegralValue(String integralValue) {
		this.integralValue = integralValue;
    }

	/**
	* 获取字段activityValue的值，该字段的<br>
	* 字段名称 :保单活动优惠金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getactivityValue() {
		return activityValue;
	}

	/**
	* 设置字段activityValue的值，该字段的<br>
	* 字段名称 :保单活动优惠金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setactivityValue(String activityValue) {
		this.activityValue = activityValue;
    }

	/**
	* 获取字段payPrice的值，该字段的<br>
	* 字段名称 :保单支付金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayPrice() {
		return payPrice;
	}

	/**
	* 设置字段payPrice的值，该字段的<br>
	* 字段名称 :保单支付金额<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayPrice(String payPrice) {
		this.payPrice = payPrice;
    }

	/**
	* 获取字段policyNoOld的值，该字段的<br>
	* 字段名称 :续保保单原保单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPolicyNoOld() {
		return policyNoOld;
	}

	/**
	* 设置字段policyNoOld的值，该字段的<br>
	* 字段名称 :续保保单原保单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPolicyNoOld(String policyNoOld) {
		this.policyNoOld = policyNoOld;
	}
	
}