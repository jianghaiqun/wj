package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：交易表<br>
 * 表代码：OtTradeInformation<br>
 * 表主键：id<br>
 */
public class OtTradeInformationSchema extends Schema {
	private String id;

	private String tradeSeriNO;

	private String payStatus;

	private String payType;

	private String sendDate;

	private String receiveDate;

	private String errorMsg;

	private String tradeAmount;

	private String orderSns;

	private String remark1;

	private String remark2;

	private String remark3;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("tradeSeriNO", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("payStatus", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("payType", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("sendDate", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("receiveDate", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("errorMsg", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("tradeAmount", DataColumn.STRING, 7, 32 , 0 , false , false),
		new SchemaColumn("orderSns", DataColumn.CLOB, 8, 0 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 15, 0 , 0 , false , false)
	};

	public static final String _TableCode = "OtTradeInformation";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into OtTradeInformation values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update OtTradeInformation set id=?,tradeSeriNO=?,payStatus=?,payType=?,sendDate=?,receiveDate=?,errorMsg=?,tradeAmount=?,orderSns=?,remark1=?,remark2=?,remark3=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from OtTradeInformation  where id=?";

	protected static final String _FillAllSQL = "select * from OtTradeInformation  where id=?";

	public OtTradeInformationSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[16];
	}

	protected Schema newInstance(){
		return new OtTradeInformationSchema();
	}

	protected SchemaSet newSet(){
		return new OtTradeInformationSet();
	}

	public OtTradeInformationSet query() {
		return query(null, -1, -1);
	}

	public OtTradeInformationSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public OtTradeInformationSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public OtTradeInformationSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (OtTradeInformationSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){tradeSeriNO = (String)v;return;}
		if (i == 2){payStatus = (String)v;return;}
		if (i == 3){payType = (String)v;return;}
		if (i == 4){sendDate = (String)v;return;}
		if (i == 5){receiveDate = (String)v;return;}
		if (i == 6){errorMsg = (String)v;return;}
		if (i == 7){tradeAmount = (String)v;return;}
		if (i == 8){orderSns = (String)v;return;}
		if (i == 9){remark1 = (String)v;return;}
		if (i == 10){remark2 = (String)v;return;}
		if (i == 11){remark3 = (String)v;return;}
		if (i == 12){createUser = (String)v;return;}
		if (i == 13){createDate = (Date)v;return;}
		if (i == 14){modifyUser = (String)v;return;}
		if (i == 15){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return tradeSeriNO;}
		if (i == 2){return payStatus;}
		if (i == 3){return payType;}
		if (i == 4){return sendDate;}
		if (i == 5){return receiveDate;}
		if (i == 6){return errorMsg;}
		if (i == 7){return tradeAmount;}
		if (i == 8){return orderSns;}
		if (i == 9){return remark1;}
		if (i == 10){return remark2;}
		if (i == 11){return remark3;}
		if (i == 12){return createUser;}
		if (i == 13){return createDate;}
		if (i == 14){return modifyUser;}
		if (i == 15){return modifyDate;}
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
	* 获取字段tradeSeriNO的值，该字段的<br>
	* 字段名称 :交易流水号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettradeSeriNO() {
		return tradeSeriNO;
	}

	/**
	* 设置字段tradeSeriNO的值，该字段的<br>
	* 字段名称 :交易流水号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settradeSeriNO(String tradeSeriNO) {
		this.tradeSeriNO = tradeSeriNO;
    }

	/**
	* 获取字段payStatus的值，该字段的<br>
	* 字段名称 :支付状态<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayStatus() {
		return payStatus;
	}

	/**
	* 设置字段payStatus的值，该字段的<br>
	* 字段名称 :支付状态<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayStatus(String payStatus) {
		this.payStatus = payStatus;
    }

	/**
	* 获取字段payType的值，该字段的<br>
	* 字段名称 :支付类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayType() {
		return payType;
	}

	/**
	* 设置字段payType的值，该字段的<br>
	* 字段名称 :支付类型<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayType(String payType) {
		this.payType = payType;
    }

	/**
	* 获取字段sendDate的值，该字段的<br>
	* 字段名称 :发送时间<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsendDate() {
		return sendDate;
	}

	/**
	* 设置字段sendDate的值，该字段的<br>
	* 字段名称 :发送时间<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsendDate(String sendDate) {
		this.sendDate = sendDate;
    }

	/**
	* 获取字段receiveDate的值，该字段的<br>
	* 字段名称 :接收时间<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getreceiveDate() {
		return receiveDate;
	}

	/**
	* 设置字段receiveDate的值，该字段的<br>
	* 字段名称 :接收时间<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
    }

	/**
	* 获取字段errorMsg的值，该字段的<br>
	* 字段名称 :错误信息<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String geterrorMsg() {
		return errorMsg;
	}

	/**
	* 设置字段errorMsg的值，该字段的<br>
	* 字段名称 :错误信息<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void seterrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
    }

	/**
	* 获取字段tradeAmount的值，该字段的<br>
	* 字段名称 :交易金额<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettradeAmount() {
		return tradeAmount;
	}

	/**
	* 设置字段tradeAmount的值，该字段的<br>
	* 字段名称 :交易金额<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
    }

	/**
	* 获取字段orderSns的值，该字段的<br>
	* 字段名称 :订单编码<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSns() {
		return orderSns;
	}

	/**
	* 设置字段orderSns的值，该字段的<br>
	* 字段名称 :订单编码<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSns(String orderSns) {
		this.orderSns = orderSns;
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