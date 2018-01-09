package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：客服续保呼出记录表<br>
 * 表代码：ServiceCallRecord<br>
 * 表主键：id<br>
 */
public class ServiceCallRecordSchema extends Schema {
	private String id;

	private String orderSn;

	private String servicePerson;

	private Date createDate;

	private Date modifyDate;

	private String callConnect;

	private String remark;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("orderSn", DataColumn.STRING, 1, 25 , 0 , false , false),
		new SchemaColumn("servicePerson", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("callConnect", DataColumn.STRING, 5, 5 , 0 , false , false),
		new SchemaColumn("remark", DataColumn.STRING, 6, 500 , 0 , false , false)
	};

	public static final String _TableCode = "ServiceCallRecord";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ServiceCallRecord values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ServiceCallRecord set id=?,orderSn=?,servicePerson=?,createDate=?,modifyDate=?,callConnect=?,remark=? where id=?";

	protected static final String _DeleteSQL = "delete from ServiceCallRecord  where id=?";

	protected static final String _FillAllSQL = "select * from ServiceCallRecord  where id=?";

	public ServiceCallRecordSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[7];
	}

	protected Schema newInstance(){
		return new ServiceCallRecordSchema();
	}

	protected SchemaSet newSet(){
		return new ServiceCallRecordSet();
	}

	public ServiceCallRecordSet query() {
		return query(null, -1, -1);
	}

	public ServiceCallRecordSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ServiceCallRecordSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ServiceCallRecordSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ServiceCallRecordSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){orderSn = (String)v;return;}
		if (i == 2){servicePerson = (String)v;return;}
		if (i == 3){createDate = (Date)v;return;}
		if (i == 4){modifyDate = (Date)v;return;}
		if (i == 5){callConnect = (String)v;return;}
		if (i == 6){remark = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return orderSn;}
		if (i == 2){return servicePerson;}
		if (i == 3){return createDate;}
		if (i == 4){return modifyDate;}
		if (i == 5){return callConnect;}
		if (i == 6){return remark;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段servicePerson的值，该字段的<br>
	* 字段名称 :客服人员名称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getservicePerson() {
		return servicePerson;
	}

	/**
	* 设置字段servicePerson的值，该字段的<br>
	* 字段名称 :客服人员名称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setservicePerson(String servicePerson) {
		this.servicePerson = servicePerson;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段callConnect的值，该字段的<br>
	* 字段名称 :是否接通<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcallConnect() {
		return callConnect;
	}

	/**
	* 设置字段callConnect的值，该字段的<br>
	* 字段名称 :是否接通<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcallConnect(String callConnect) {
		this.callConnect = callConnect;
    }

	/**
	* 获取字段remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark() {
		return remark;
	}

	/**
	* 设置字段remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark(String remark) {
		this.remark = remark;
    }

}