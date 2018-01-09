package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：交易失败缓存表<br>
 * 表代码：SDTradeErrorCache<br>
 * 表备注：<br>
交易失败缓存表交易失败缓存表<br>
<br>
 * 表主键：ID<br>
 */
public class SDTradeErrorCacheSchema extends Schema {
	private String ID;

	private String OrderSn;

	private String CompanyName;

	private String insuredSn;

	private String SendDate;

	private String ErrorMessage;

	private String CreateDate;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("OrderSn", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("CompanyName", DataColumn.STRING, 2, 32 , 0 , false , false),
		new SchemaColumn("insuredSn", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("SendDate", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("ErrorMessage", DataColumn.STRING, 5, 500 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 200 , 0 , false , false)
	};

	public static final String _TableCode = "SDTradeErrorCache";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDTradeErrorCache values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDTradeErrorCache set ID=?,OrderSn=?,CompanyName=?,insuredSn=?,SendDate=?,ErrorMessage=?,CreateDate=?,Prop1=?,Prop2=? where ID=?";

	protected static final String _DeleteSQL = "delete from SDTradeErrorCache  where ID=?";

	protected static final String _FillAllSQL = "select * from SDTradeErrorCache  where ID=?";

	public SDTradeErrorCacheSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[9];
	}

	protected Schema newInstance(){
		return new SDTradeErrorCacheSchema();
	}

	protected SchemaSet newSet(){
		return new SDTradeErrorCacheSet();
	}

	public SDTradeErrorCacheSet query() {
		return query(null, -1, -1);
	}

	public SDTradeErrorCacheSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDTradeErrorCacheSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDTradeErrorCacheSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDTradeErrorCacheSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){OrderSn = (String)v;return;}
		if (i == 2){CompanyName = (String)v;return;}
		if (i == 3){insuredSn = (String)v;return;}
		if (i == 4){SendDate = (String)v;return;}
		if (i == 5){ErrorMessage = (String)v;return;}
		if (i == 6){CreateDate = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return OrderSn;}
		if (i == 2){return CompanyName;}
		if (i == 3){return insuredSn;}
		if (i == 4){return SendDate;}
		if (i == 5){return ErrorMessage;}
		if (i == 6){return CreateDate;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
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
	* 获取字段CompanyName的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCompanyName() {
		return CompanyName;
	}

	/**
	* 设置字段CompanyName的值，该字段的<br>
	* 字段名称 :保险公司<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCompanyName(String companyName) {
		this.CompanyName = companyName;
    }

	/**
	* 获取字段insuredSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuredSn() {
		return insuredSn;
	}

	/**
	* 设置字段insuredSn的值，该字段的<br>
	* 字段名称 :被保人编号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuredSn(String insuredSn) {
		this.insuredSn = insuredSn;
    }

	/**
	* 获取字段SendDate的值，该字段的<br>
	* 字段名称 :发送时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendDate() {
		return SendDate;
	}

	/**
	* 设置字段SendDate的值，该字段的<br>
	* 字段名称 :发送时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendDate(String sendDate) {
		this.SendDate = sendDate;
    }

	/**
	* 获取字段ErrorMessage的值，该字段的<br>
	* 字段名称 :异常信息<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getErrorMessage() {
		return ErrorMessage;
	}

	/**
	* 设置字段ErrorMessage的值，该字段的<br>
	* 字段名称 :异常信息<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setErrorMessage(String errorMessage) {
		this.ErrorMessage = errorMessage;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(String createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

}