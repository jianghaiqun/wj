package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：代客支付原始订单存储表<br>
 * 表代码：BuyForCustomerOldSn<br>
 * 表主键：id<br>
 */
public class BuyForCustomerOldSnSchema extends Schema {
	private String id;

	private String Ordersn;

	private String BuyForCustomerFlag;

	private String OldOrderSn;

	private Date AddTime;

	private String AddUser;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("Ordersn", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("BuyForCustomerFlag", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("OldOrderSn", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 8, 100 , 0 , false , false)
	};

	public static final String _TableCode = "BuyForCustomerOldSn";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into BuyForCustomerOldSn values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BuyForCustomerOldSn set id=?,Ordersn=?,BuyForCustomerFlag=?,OldOrderSn=?,AddTime=?,AddUser=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from BuyForCustomerOldSn  where id=?";

	protected static final String _FillAllSQL = "select * from BuyForCustomerOldSn  where id=?";

	public BuyForCustomerOldSnSchema(){
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
		return new BuyForCustomerOldSnSchema();
	}

	protected SchemaSet newSet(){
		return new BuyForCustomerOldSnSet();
	}

	public BuyForCustomerOldSnSet query() {
		return query(null, -1, -1);
	}

	public BuyForCustomerOldSnSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BuyForCustomerOldSnSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BuyForCustomerOldSnSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BuyForCustomerOldSnSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){Ordersn = (String)v;return;}
		if (i == 2){BuyForCustomerFlag = (String)v;return;}
		if (i == 3){OldOrderSn = (String)v;return;}
		if (i == 4){AddTime = (Date)v;return;}
		if (i == 5){AddUser = (String)v;return;}
		if (i == 6){Prop1 = (String)v;return;}
		if (i == 7){Prop2 = (String)v;return;}
		if (i == 8){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return Ordersn;}
		if (i == 2){return BuyForCustomerFlag;}
		if (i == 3){return OldOrderSn;}
		if (i == 4){return AddTime;}
		if (i == 5){return AddUser;}
		if (i == 6){return Prop1;}
		if (i == 7){return Prop2;}
		if (i == 8){return Prop3;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段Ordersn的值，该字段的<br>
	* 字段名称 :当前订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrdersn() {
		return Ordersn;
	}

	/**
	* 设置字段Ordersn的值，该字段的<br>
	* 字段名称 :当前订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrdersn(String ordersn) {
		this.Ordersn = ordersn;
    }

	/**
	* 获取字段BuyForCustomerFlag的值，该字段的<br>
	* 字段名称 :代客支付区分<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBuyForCustomerFlag() {
		return BuyForCustomerFlag;
	}

	/**
	* 设置字段BuyForCustomerFlag的值，该字段的<br>
	* 字段名称 :代客支付区分<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBuyForCustomerFlag(String buyForCustomerFlag) {
		this.BuyForCustomerFlag = buyForCustomerFlag;
    }

	/**
	* 获取字段OldOrderSn的值，该字段的<br>
	* 字段名称 :原始订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOldOrderSn() {
		return OldOrderSn;
	}

	/**
	* 设置字段OldOrderSn的值，该字段的<br>
	* 字段名称 :原始订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOldOrderSn(String oldOrderSn) {
		this.OldOrderSn = oldOrderSn;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}