package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：订单总项表<br>
 * 表代码：SDCancelReturnData<br>
 * 表主键：Id<br>
 */
public class SDCancelReturnDataSchema extends Schema {
	private String Id;

	private String ContNo;

	private String OrderSn;

	private String InsuranceCode;

	private String OrderStatus;

	private String CancelStatus;

	private String InsuranceTransCode;

	private String InsuranceBankCode;

	private String InsuranceBRNO;

	private String InsuranceBankSeriNO;

	private String InsuranceTELLERNO;

	private String InsuranceAreaCode;

	private String InsuranceACCTDate;

	private String InsuranceACCTTime;

	private String InsuranceResultCode;

	private String InsuranceResultMsg;

	private String SettleState;

	private String SettleMgr;

	private String SettleStatus;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("ContNo", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("OrderSn", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("InsuranceCode", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("OrderStatus", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("CancelStatus", DataColumn.STRING, 5, 2 , 0 , false , false),
		new SchemaColumn("InsuranceTransCode", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("InsuranceBankCode", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("InsuranceBRNO", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("InsuranceBankSeriNO", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("InsuranceTELLERNO", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("InsuranceAreaCode", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("InsuranceACCTDate", DataColumn.STRING, 12, 20 , 0 , false , false),
		new SchemaColumn("InsuranceACCTTime", DataColumn.STRING, 13, 20 , 0 , false , false),
		new SchemaColumn("InsuranceResultCode", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("InsuranceResultMsg", DataColumn.STRING, 15, 500 , 0 , false , false),
		new SchemaColumn("SettleState", DataColumn.STRING, 16, 5 , 0 , false , false),
		new SchemaColumn("SettleMgr", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("SettleStatus", DataColumn.STRING, 18, 255 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 19, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 20, 30 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 21, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 22, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 23, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 24, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 25, 50 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 26, 50 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 27, 50 , 0 , false , false)
	};

	public static final String _TableCode = "SDCancelReturnData";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDCancelReturnData values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDCancelReturnData set Id=?,ContNo=?,OrderSn=?,InsuranceCode=?,OrderStatus=?,CancelStatus=?,InsuranceTransCode=?,InsuranceBankCode=?,InsuranceBRNO=?,InsuranceBankSeriNO=?,InsuranceTELLERNO=?,InsuranceAreaCode=?,InsuranceACCTDate=?,InsuranceACCTTime=?,InsuranceResultCode=?,InsuranceResultMsg=?,SettleState=?,SettleMgr=?,SettleStatus=?,AddTime=?,AddUser=?,ModifyTime=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDCancelReturnData  where Id=?";

	protected static final String _FillAllSQL = "select * from SDCancelReturnData  where Id=?";

	public SDCancelReturnDataSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[28];
	}

	protected Schema newInstance(){
		return new SDCancelReturnDataSchema();
	}

	protected SchemaSet newSet(){
		return new SDCancelReturnDataSet();
	}

	public SDCancelReturnDataSet query() {
		return query(null, -1, -1);
	}

	public SDCancelReturnDataSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDCancelReturnDataSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDCancelReturnDataSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDCancelReturnDataSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ContNo = (String)v;return;}
		if (i == 2){OrderSn = (String)v;return;}
		if (i == 3){InsuranceCode = (String)v;return;}
		if (i == 4){OrderStatus = (String)v;return;}
		if (i == 5){CancelStatus = (String)v;return;}
		if (i == 6){InsuranceTransCode = (String)v;return;}
		if (i == 7){InsuranceBankCode = (String)v;return;}
		if (i == 8){InsuranceBRNO = (String)v;return;}
		if (i == 9){InsuranceBankSeriNO = (String)v;return;}
		if (i == 10){InsuranceTELLERNO = (String)v;return;}
		if (i == 11){InsuranceAreaCode = (String)v;return;}
		if (i == 12){InsuranceACCTDate = (String)v;return;}
		if (i == 13){InsuranceACCTTime = (String)v;return;}
		if (i == 14){InsuranceResultCode = (String)v;return;}
		if (i == 15){InsuranceResultMsg = (String)v;return;}
		if (i == 16){SettleState = (String)v;return;}
		if (i == 17){SettleMgr = (String)v;return;}
		if (i == 18){SettleStatus = (String)v;return;}
		if (i == 19){AddTime = (Date)v;return;}
		if (i == 20){AddUser = (String)v;return;}
		if (i == 21){ModifyTime = (Date)v;return;}
		if (i == 22){Prop1 = (String)v;return;}
		if (i == 23){Prop2 = (String)v;return;}
		if (i == 24){Prop3 = (String)v;return;}
		if (i == 25){Prop4 = (String)v;return;}
		if (i == 26){Prop5 = (String)v;return;}
		if (i == 27){Prop6 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ContNo;}
		if (i == 2){return OrderSn;}
		if (i == 3){return InsuranceCode;}
		if (i == 4){return OrderStatus;}
		if (i == 5){return CancelStatus;}
		if (i == 6){return InsuranceTransCode;}
		if (i == 7){return InsuranceBankCode;}
		if (i == 8){return InsuranceBRNO;}
		if (i == 9){return InsuranceBankSeriNO;}
		if (i == 10){return InsuranceTELLERNO;}
		if (i == 11){return InsuranceAreaCode;}
		if (i == 12){return InsuranceACCTDate;}
		if (i == 13){return InsuranceACCTTime;}
		if (i == 14){return InsuranceResultCode;}
		if (i == 15){return InsuranceResultMsg;}
		if (i == 16){return SettleState;}
		if (i == 17){return SettleMgr;}
		if (i == 18){return SettleStatus;}
		if (i == 19){return AddTime;}
		if (i == 20){return AddUser;}
		if (i == 21){return ModifyTime;}
		if (i == 22){return Prop1;}
		if (i == 23){return Prop2;}
		if (i == 24){return Prop3;}
		if (i == 25){return Prop4;}
		if (i == 26){return Prop5;}
		if (i == 27){return Prop6;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段ContNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContNo() {
		return ContNo;
	}

	/**
	* 设置字段ContNo的值，该字段的<br>
	* 字段名称 :保单号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContNo(String contNo) {
		this.ContNo = contNo;
    }

	/**
	* 获取字段OrderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderSn() {
		return OrderSn;
	}

	/**
	* 设置字段OrderSn的值，该字段的<br>
	* 字段名称 :订单号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderSn(String orderSn) {
		this.OrderSn = orderSn;
    }

	/**
	* 获取字段InsuranceCode的值，该字段的<br>
	* 字段名称 :保险公司代码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceCode() {
		return InsuranceCode;
	}

	/**
	* 设置字段InsuranceCode的值，该字段的<br>
	* 字段名称 :保险公司代码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceCode(String insuranceCode) {
		this.InsuranceCode = insuranceCode;
    }

	/**
	* 获取字段OrderStatus的值，该字段的<br>
	* 字段名称 :订单状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderStatus() {
		return OrderStatus;
	}

	/**
	* 设置字段OrderStatus的值，该字段的<br>
	* 字段名称 :订单状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderStatus(String orderStatus) {
		this.OrderStatus = orderStatus;
    }

	/**
	* 获取字段CancelStatus的值，该字段的<br>
	* 字段名称 :退保状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCancelStatus() {
		return CancelStatus;
	}

	/**
	* 设置字段CancelStatus的值，该字段的<br>
	* 字段名称 :退保状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCancelStatus(String cancelStatus) {
		this.CancelStatus = cancelStatus;
    }

	/**
	* 获取字段InsuranceTransCode的值，该字段的<br>
	* 字段名称 :insuranceTransCode<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceTransCode() {
		return InsuranceTransCode;
	}

	/**
	* 设置字段InsuranceTransCode的值，该字段的<br>
	* 字段名称 :insuranceTransCode<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceTransCode(String insuranceTransCode) {
		this.InsuranceTransCode = insuranceTransCode;
    }

	/**
	* 获取字段InsuranceBankCode的值，该字段的<br>
	* 字段名称 :insuranceBankCode<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceBankCode() {
		return InsuranceBankCode;
	}

	/**
	* 设置字段InsuranceBankCode的值，该字段的<br>
	* 字段名称 :insuranceBankCode<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceBankCode(String insuranceBankCode) {
		this.InsuranceBankCode = insuranceBankCode;
    }

	/**
	* 获取字段InsuranceBRNO的值，该字段的<br>
	* 字段名称 :insuranceBRNO<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceBRNO() {
		return InsuranceBRNO;
	}

	/**
	* 设置字段InsuranceBRNO的值，该字段的<br>
	* 字段名称 :insuranceBRNO<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceBRNO(String insuranceBRNO) {
		this.InsuranceBRNO = insuranceBRNO;
    }

	/**
	* 获取字段InsuranceBankSeriNO的值，该字段的<br>
	* 字段名称 :insuranceBankSeriNO<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceBankSeriNO() {
		return InsuranceBankSeriNO;
	}

	/**
	* 设置字段InsuranceBankSeriNO的值，该字段的<br>
	* 字段名称 :insuranceBankSeriNO<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceBankSeriNO(String insuranceBankSeriNO) {
		this.InsuranceBankSeriNO = insuranceBankSeriNO;
    }

	/**
	* 获取字段InsuranceTELLERNO的值，该字段的<br>
	* 字段名称 :insuranceTELLERNO<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceTELLERNO() {
		return InsuranceTELLERNO;
	}

	/**
	* 设置字段InsuranceTELLERNO的值，该字段的<br>
	* 字段名称 :insuranceTELLERNO<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceTELLERNO(String insuranceTELLERNO) {
		this.InsuranceTELLERNO = insuranceTELLERNO;
    }

	/**
	* 获取字段InsuranceAreaCode的值，该字段的<br>
	* 字段名称 :insuranceAreaCode<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceAreaCode() {
		return InsuranceAreaCode;
	}

	/**
	* 设置字段InsuranceAreaCode的值，该字段的<br>
	* 字段名称 :insuranceAreaCode<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceAreaCode(String insuranceAreaCode) {
		this.InsuranceAreaCode = insuranceAreaCode;
    }

	/**
	* 获取字段InsuranceACCTDate的值，该字段的<br>
	* 字段名称 :insuranceACCTDate<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceACCTDate() {
		return InsuranceACCTDate;
	}

	/**
	* 设置字段InsuranceACCTDate的值，该字段的<br>
	* 字段名称 :insuranceACCTDate<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceACCTDate(String insuranceACCTDate) {
		this.InsuranceACCTDate = insuranceACCTDate;
    }

	/**
	* 获取字段InsuranceACCTTime的值，该字段的<br>
	* 字段名称 :insuranceACCTTime<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceACCTTime() {
		return InsuranceACCTTime;
	}

	/**
	* 设置字段InsuranceACCTTime的值，该字段的<br>
	* 字段名称 :insuranceACCTTime<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceACCTTime(String insuranceACCTTime) {
		this.InsuranceACCTTime = insuranceACCTTime;
    }

	/**
	* 获取字段InsuranceResultCode的值，该字段的<br>
	* 字段名称 :insuranceResultCode<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceResultCode() {
		return InsuranceResultCode;
	}

	/**
	* 设置字段InsuranceResultCode的值，该字段的<br>
	* 字段名称 :insuranceResultCode<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceResultCode(String insuranceResultCode) {
		this.InsuranceResultCode = insuranceResultCode;
    }

	/**
	* 获取字段InsuranceResultMsg的值，该字段的<br>
	* 字段名称 :insuranceResultMsg<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsuranceResultMsg() {
		return InsuranceResultMsg;
	}

	/**
	* 设置字段InsuranceResultMsg的值，该字段的<br>
	* 字段名称 :insuranceResultMsg<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsuranceResultMsg(String insuranceResultMsg) {
		this.InsuranceResultMsg = insuranceResultMsg;
    }

	/**
	* 获取字段SettleState的值，该字段的<br>
	* 字段名称 :结算中心状态<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSettleState() {
		return SettleState;
	}

	/**
	* 设置字段SettleState的值，该字段的<br>
	* 字段名称 :结算中心状态<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSettleState(String settleState) {
		this.SettleState = settleState;
    }

	/**
	* 获取字段SettleMgr的值，该字段的<br>
	* 字段名称 :settleMgr<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSettleMgr() {
		return SettleMgr;
	}

	/**
	* 设置字段SettleMgr的值，该字段的<br>
	* 字段名称 :settleMgr<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSettleMgr(String settleMgr) {
		this.SettleMgr = settleMgr;
    }

	/**
	* 获取字段SettleStatus的值，该字段的<br>
	* 字段名称 :settleStatus<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSettleStatus() {
		return SettleStatus;
	}

	/**
	* 设置字段SettleStatus的值，该字段的<br>
	* 字段名称 :settleStatus<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSettleStatus(String settleStatus) {
		this.SettleStatus = settleStatus;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* 获取字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* 设置字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

}