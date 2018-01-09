package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：保险公司返回信息<br>
 * 表代码：InsuredCompanyReturnData<br>
 * 表主键：ID<br>
 */
public class InsuredCompanyReturnDataSchema extends Schema {
	private String ID;

	private String applyPolicyNo;

	private String policyNo;

	private String noticeNo;

	private String validateCode;

	private Double totalPremium;

	private String orderSn;

	private String tradeSeriNO;

	private String appStatus;

	private String insuranceCode;

	private String insuranceTransCode;

	private String insuranceBankCode;

	private String insuranceBRNO;

	private String insuranceBankSeriNO;

	private String insuranceTELLERNO;

	private String insuranceAreaCode;

	private String insuranceACCTDate;

	private String insuranceACCTTime;

	private String insuranceResultCode;

	private String insuranceResultMsg;

	private String currentDate;

	private Date createDate;

	private Date modifyDate;

	private String settleState;

	private String remark;

	private String settleStatus;

	private String settleMgr;

	private String policyPath;

	private String remark1;

	private String remark2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("applyPolicyNo", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("policyNo", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("noticeNo", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("validateCode", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("totalPremium", DataColumn.DOUBLE, 5, 0 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("tradeSeriNO", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("appStatus", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("insuranceCode", DataColumn.STRING, 9, 30 , 0 , false , false),
		new SchemaColumn("insuranceTransCode", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("insuranceBankCode", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("insuranceBRNO", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("insuranceBankSeriNO", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("insuranceTELLERNO", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("insuranceAreaCode", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("insuranceACCTDate", DataColumn.STRING, 16, 20 , 0 , false , false),
		new SchemaColumn("insuranceACCTTime", DataColumn.STRING, 17, 20 , 0 , false , false),
		new SchemaColumn("insuranceResultCode", DataColumn.STRING, 18, 50 , 0 , false , false),
		new SchemaColumn("insuranceResultMsg", DataColumn.STRING, 19, 500 , 0 , false , false),
		new SchemaColumn("currentDate", DataColumn.STRING, 20, 50 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 21, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 22, 0 , 0 , false , false),
		new SchemaColumn("settleState", DataColumn.STRING, 23, 5 , 0 , false , false),
		new SchemaColumn("remark", DataColumn.STRING, 24, 50 , 0 , false , false),
		new SchemaColumn("settleStatus", DataColumn.STRING, 25, 5 , 0 , false , false),
		new SchemaColumn("settleMgr", DataColumn.STRING, 26, 500 , 0 , false , false),
		new SchemaColumn("policyPath", DataColumn.STRING, 27, 255 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 28, 50 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 29, 50 , 0 , false , false)
	};

	public static final String _TableCode = "InsuredCompanyReturnData";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into InsuredCompanyReturnData values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update InsuredCompanyReturnData set ID=?,applyPolicyNo=?,policyNo=?,noticeNo=?,validateCode=?,totalPremium=?,orderSn=?,tradeSeriNO=?,appStatus=?,insuranceCode=?,insuranceTransCode=?,insuranceBankCode=?,insuranceBRNO=?,insuranceBankSeriNO=?,insuranceTELLERNO=?,insuranceAreaCode=?,insuranceACCTDate=?,insuranceACCTTime=?,insuranceResultCode=?,insuranceResultMsg=?,currentDate=?,createDate=?,modifyDate=?,settleState=?,remark=?,settleStatus=?,settleMgr=?,policyPath=?,remark1=?,remark2=? where ID=?";

	protected static final String _DeleteSQL = "delete from InsuredCompanyReturnData  where ID=?";

	protected static final String _FillAllSQL = "select * from InsuredCompanyReturnData  where ID=?";

	public InsuredCompanyReturnDataSchema(){
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
		return new InsuredCompanyReturnDataSchema();
	}

	protected SchemaSet newSet(){
		return new InsuredCompanyReturnDataSet();
	}

	public InsuredCompanyReturnDataSet query() {
		return query(null, -1, -1);
	}

	public InsuredCompanyReturnDataSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public InsuredCompanyReturnDataSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public InsuredCompanyReturnDataSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (InsuredCompanyReturnDataSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){applyPolicyNo = (String)v;return;}
		if (i == 2){policyNo = (String)v;return;}
		if (i == 3){noticeNo = (String)v;return;}
		if (i == 4){validateCode = (String)v;return;}
		if (i == 5){totalPremium = (Double)v;return;}
		if (i == 6){orderSn = (String)v;return;}
		if (i == 7){tradeSeriNO = (String)v;return;}
		if (i == 8){appStatus = (String)v;return;}
		if (i == 9){insuranceCode = (String)v;return;}
		if (i == 10){insuranceTransCode = (String)v;return;}
		if (i == 11){insuranceBankCode = (String)v;return;}
		if (i == 12){insuranceBRNO = (String)v;return;}
		if (i == 13){insuranceBankSeriNO = (String)v;return;}
		if (i == 14){insuranceTELLERNO = (String)v;return;}
		if (i == 15){insuranceAreaCode = (String)v;return;}
		if (i == 16){insuranceACCTDate = (String)v;return;}
		if (i == 17){insuranceACCTTime = (String)v;return;}
		if (i == 18){insuranceResultCode = (String)v;return;}
		if (i == 19){insuranceResultMsg = (String)v;return;}
		if (i == 20){currentDate = (String)v;return;}
		if (i == 21){createDate = (Date)v;return;}
		if (i == 22){modifyDate = (Date)v;return;}
		if (i == 23){settleState = (String)v;return;}
		if (i == 24){remark = (String)v;return;}
		if (i == 25){settleStatus = (String)v;return;}
		if (i == 26){settleMgr = (String)v;return;}
		if (i == 27){policyPath = (String)v;return;}
		if (i == 28){remark1 = (String)v;return;}
		if (i == 29){remark2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return applyPolicyNo;}
		if (i == 2){return policyNo;}
		if (i == 3){return noticeNo;}
		if (i == 4){return validateCode;}
		if (i == 5){return totalPremium;}
		if (i == 6){return orderSn;}
		if (i == 7){return tradeSeriNO;}
		if (i == 8){return appStatus;}
		if (i == 9){return insuranceCode;}
		if (i == 10){return insuranceTransCode;}
		if (i == 11){return insuranceBankCode;}
		if (i == 12){return insuranceBRNO;}
		if (i == 13){return insuranceBankSeriNO;}
		if (i == 14){return insuranceTELLERNO;}
		if (i == 15){return insuranceAreaCode;}
		if (i == 16){return insuranceACCTDate;}
		if (i == 17){return insuranceACCTTime;}
		if (i == 18){return insuranceResultCode;}
		if (i == 19){return insuranceResultMsg;}
		if (i == 20){return currentDate;}
		if (i == 21){return createDate;}
		if (i == 22){return modifyDate;}
		if (i == 23){return settleState;}
		if (i == 24){return remark;}
		if (i == 25){return settleStatus;}
		if (i == 26){return settleMgr;}
		if (i == 27){return policyPath;}
		if (i == 28){return remark1;}
		if (i == 29){return remark2;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :记录ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :记录ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段applyPolicyNo的值，该字段的<br>
	* 字段名称 :投保单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getapplyPolicyNo() {
		return applyPolicyNo;
	}

	/**
	* 设置字段applyPolicyNo的值，该字段的<br>
	* 字段名称 :投保单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setapplyPolicyNo(String applyPolicyNo) {
		this.applyPolicyNo = applyPolicyNo;
    }

	/**
	* 获取字段policyNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
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
	*/
	public void setpolicyNo(String policyNo) {
		this.policyNo = policyNo;
    }

	/**
	* 获取字段noticeNo的值，该字段的<br>
	* 字段名称 :财务通知单<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getnoticeNo() {
		return noticeNo;
	}

	/**
	* 设置字段noticeNo的值，该字段的<br>
	* 字段名称 :财务通知单<br>
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
	* 获取字段totalPremium的值，该字段的<br>
	* 字段名称 :保单保费<br>
	* 数据类型 :double<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public double gettotalPremium() {
		return totalPremium;
	}

	/**
	* 设置字段totalPremium的值，该字段的<br>
	* 字段名称 :保单保费<br>
	* 数据类型 :double<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settotalPremium(double totalPremium) {
		this.totalPremium = totalPremium;
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
	* 获取字段tradeSeriNO的值，该字段的<br>
	* 字段名称 :支付银行或平台返回的交易的流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettradeSeriNO() {
		return tradeSeriNO;
	}

	/**
	* 设置字段tradeSeriNO的值，该字段的<br>
	* 字段名称 :支付银行或平台返回的交易的流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settradeSeriNO(String tradeSeriNO) {
		this.tradeSeriNO = tradeSeriNO;
    }

	/**
	* 获取字段appStatus的值，该字段的<br>
	* 字段名称 :投保状态<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getappStatus() {
		return appStatus;
	}

	/**
	* 设置字段appStatus的值，该字段的<br>
	* 字段名称 :投保状态<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setappStatus(String appStatus) {
		this.appStatus = appStatus;
    }

	/**
	* 获取字段insuranceCode的值，该字段的<br>
	* 字段名称 :保险公司编码<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceCode() {
		return insuranceCode;
	}

	/**
	* 设置字段insuranceCode的值，该字段的<br>
	* 字段名称 :保险公司编码<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
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
	* 获取字段insuranceBRNO的值，该字段的<br>
	* 字段名称 :保险公司返回银行系列号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceBRNO() {
		return insuranceBRNO;
	}

	/**
	* 设置字段insuranceBRNO的值，该字段的<br>
	* 字段名称 :保险公司返回银行系列号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceBRNO(String insuranceBRNO) {
		this.insuranceBRNO = insuranceBRNO;
    }

	/**
	* 获取字段insuranceBankSeriNO的值，该字段的<br>
	* 字段名称 :保险公司返回银行流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceBankSeriNO() {
		return insuranceBankSeriNO;
	}

	/**
	* 设置字段insuranceBankSeriNO的值，该字段的<br>
	* 字段名称 :保险公司返回银行流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceBankSeriNO(String insuranceBankSeriNO) {
		this.insuranceBankSeriNO = insuranceBankSeriNO;
    }

	/**
	* 获取字段insuranceTELLERNO的值，该字段的<br>
	* 字段名称 :保险公司返回商户号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceTELLERNO() {
		return insuranceTELLERNO;
	}

	/**
	* 设置字段insuranceTELLERNO的值，该字段的<br>
	* 字段名称 :保险公司返回商户号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceTELLERNO(String insuranceTELLERNO) {
		this.insuranceTELLERNO = insuranceTELLERNO;
    }

	/**
	* 获取字段insuranceAreaCode的值，该字段的<br>
	* 字段名称 :保险公司返回地区编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceAreaCode() {
		return insuranceAreaCode;
	}

	/**
	* 设置字段insuranceAreaCode的值，该字段的<br>
	* 字段名称 :保险公司返回地区编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceAreaCode(String insuranceAreaCode) {
		this.insuranceAreaCode = insuranceAreaCode;
    }

	/**
	* 获取字段insuranceACCTDate的值，该字段的<br>
	* 字段名称 :保险公司返回投保日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceACCTDate() {
		return insuranceACCTDate;
	}

	/**
	* 设置字段insuranceACCTDate的值，该字段的<br>
	* 字段名称 :保险公司返回投保日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceACCTDate(String insuranceACCTDate) {
		this.insuranceACCTDate = insuranceACCTDate;
    }

	/**
	* 获取字段insuranceACCTTime的值，该字段的<br>
	* 字段名称 :保险公司返回投保时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceACCTTime() {
		return insuranceACCTTime;
	}

	/**
	* 设置字段insuranceACCTTime的值，该字段的<br>
	* 字段名称 :保险公司返回投保时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceACCTTime(String insuranceACCTTime) {
		this.insuranceACCTTime = insuranceACCTTime;
    }

	/**
	* 获取字段insuranceResultCode的值，该字段的<br>
	* 字段名称 :保险公司返回投保结果编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceResultCode() {
		return insuranceResultCode;
	}

	/**
	* 设置字段insuranceResultCode的值，该字段的<br>
	* 字段名称 :保险公司返回投保结果编码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceResultCode(String insuranceResultCode) {
		this.insuranceResultCode = insuranceResultCode;
    }

	/**
	* 获取字段insuranceResultMsg的值，该字段的<br>
	* 字段名称 :保险公司返回投保结果信息<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceResultMsg() {
		return insuranceResultMsg;
	}

	/**
	* 设置字段insuranceResultMsg的值，该字段的<br>
	* 字段名称 :保险公司返回投保结果信息<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceResultMsg(String insuranceResultMsg) {
		this.insuranceResultMsg = insuranceResultMsg;
    }

	/**
	* 获取字段currentDate的值，该字段的<br>
	* 字段名称 :当前日期<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcurrentDate() {
		return currentDate;
	}

	/**
	* 设置字段currentDate的值，该字段的<br>
	* 字段名称 :当前日期<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcurrentDate(String currentDate) {
		this.currentDate = currentDate;
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
	* 获取字段settleState的值，该字段的<br>
	* 字段名称 :结算标记<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsettleState() {
		return settleState;
	}

	/**
	* 设置字段settleState的值，该字段的<br>
	* 字段名称 :结算标记<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsettleState(String settleState) {
		this.settleState = settleState;
    }

	/**
	* 获取字段remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark() {
		return remark;
	}

	/**
	* 设置字段remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark(String remark) {
		this.remark = remark;
    }

	/**
	* 获取字段settleStatus的值，该字段的<br>
	* 字段名称 :结算结果状态<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsettleStatus() {
		return settleStatus;
	}

	/**
	* 设置字段settleStatus的值，该字段的<br>
	* 字段名称 :结算结果状态<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
    }

	/**
	* 获取字段settleMgr的值，该字段的<br>
	* 字段名称 :结算信息<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsettleMgr() {
		return settleMgr;
	}

	/**
	* 设置字段settleMgr的值，该字段的<br>
	* 字段名称 :结算信息<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsettleMgr(String settleMgr) {
		this.settleMgr = settleMgr;
    }

	/**
	* 获取字段policyPath的值，该字段的<br>
	* 字段名称 :保险公司保单下载路径<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpolicyPath() {
		return policyPath;
	}

	/**
	* 设置字段policyPath的值，该字段的<br>
	* 字段名称 :保险公司保单下载路径<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpolicyPath(String policyPath) {
		this.policyPath = policyPath;
    }

	/**
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :备注1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :备注1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

	/**
	* 获取字段remark2的值，该字段的<br>
	* 字段名称 :备注2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark2() {
		return remark2;
	}

	/**
	* 设置字段remark2的值，该字段的<br>
	* 字段名称 :备注2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark2(String remark2) {
		this.remark2 = remark2;
    }

}