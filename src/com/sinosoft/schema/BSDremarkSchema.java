package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：bsdremark<br>
 * 表代码：bsdremark<br>
 * 表主键：id<br>
 */
public class BSDremarkSchema extends Schema {
	private String id;

	private String upperId;

	private Date OperateTime;

	private String orderSn;

	private String prop1;

	private String prop2;

	private String OperateType;

	private String remark;

	private String OperateName;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 288 , 0 , true , true),
		new SchemaColumn("upperId", DataColumn.STRING, 1, 288 , 0 , false , false),
		new SchemaColumn("OperateTime", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 3, 180 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 4, 180 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 5, 180 , 0 , false , false),
		new SchemaColumn("OperateType", DataColumn.STRING, 6, 180 , 0 , false , false),
		new SchemaColumn("remark", DataColumn.STRING, 7, 9000 , 0 , false , false),
		new SchemaColumn("OperateName", DataColumn.STRING, 8, 900 , 0 , false , false)
	};

	public static final String _TableCode = "bsdremark";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into bsdremark values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update bsdremark set id=?,upperId=?,OperateTime=?,orderSn=?,prop1=?,prop2=?,OperateType=?,remark=?,OperateName=? where id=?";

	protected static final String _DeleteSQL = "delete from bsdremark  where id=?";

	protected static final String _FillAllSQL = "select * from bsdremark  where id=?";

	public BSDremarkSchema(){
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
		return new BSDremarkSchema();
	}

	protected SchemaSet newSet(){
		return new BSDremarkSet();
	}

	public BSDremarkSet query() {
		return query(null, -1, -1);
	}

	public BSDremarkSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BSDremarkSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BSDremarkSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BSDremarkSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){upperId = (String)v;return;}
		if (i == 2){OperateTime = (Date)v;return;}
		if (i == 3){orderSn = (String)v;return;}
		if (i == 4){prop1 = (String)v;return;}
		if (i == 5){prop2 = (String)v;return;}
		if (i == 6){OperateType = (String)v;return;}
		if (i == 7){remark = (String)v;return;}
		if (i == 8){OperateName = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return upperId;}
		if (i == 2){return OperateTime;}
		if (i == 3){return orderSn;}
		if (i == 4){return prop1;}
		if (i == 5){return prop2;}
		if (i == 6){return OperateType;}
		if (i == 7){return remark;}
		if (i == 8){return OperateName;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar (288)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar (288)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段upperId的值，该字段的<br>
	* 字段名称 :upperId<br>
	* 数据类型 :varchar (288)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getupperId() {
		return upperId;
	}

	/**
	* 设置字段upperId的值，该字段的<br>
	* 字段名称 :upperId<br>
	* 数据类型 :varchar (288)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setupperId(String upperId) {
		this.upperId = upperId;
    }

	/**
	* 获取字段OperateTime的值，该字段的<br>
	* 字段名称 :OperateTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getOperateTime() {
		return OperateTime;
	}

	/**
	* 设置字段OperateTime的值，该字段的<br>
	* 字段名称 :OperateTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperateTime(Date operateTime) {
		this.OperateTime = operateTime;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :orderSn<br>
	* 数据类型 :varchar (180)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :orderSn<br>
	* 数据类型 :varchar (180)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar (180)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar (180)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar (180)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar (180)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段OperateType的值，该字段的<br>
	* 字段名称 :OperateType<br>
	* 数据类型 :varchar (180)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOperateType() {
		return OperateType;
	}

	/**
	* 设置字段OperateType的值，该字段的<br>
	* 字段名称 :OperateType<br>
	* 数据类型 :varchar (180)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperateType(String operateType) {
		this.OperateType = operateType;
    }

	/**
	* 获取字段remark的值，该字段的<br>
	* 字段名称 :remark<br>
	* 数据类型 :varchar (9000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark() {
		return remark;
	}

	/**
	* 设置字段remark的值，该字段的<br>
	* 字段名称 :remark<br>
	* 数据类型 :varchar (9000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark(String remark) {
		this.remark = remark;
    }

	/**
	* 获取字段OperateName的值，该字段的<br>
	* 字段名称 :OperateName<br>
	* 数据类型 :varchar (900)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOperateName() {
		return OperateName;
	}

	/**
	* 设置字段OperateName的值，该字段的<br>
	* 字段名称 :OperateName<br>
	* 数据类型 :varchar (900)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperateName(String operateName) {
		this.OperateName = operateName;
    }

}