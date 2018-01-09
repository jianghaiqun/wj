package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：保单变更信息记录<br>
 * 表代码：PolicyChangeInfo<br>
 * 表主键：id<br>
 */
public class PolicyChangeInfoSchema extends Schema {
	private String id;

	private Date createDate;

	private String newPaySn;

	private String newOrderSn;

	private String oldPaySn;

	private String oldOrderSn;

	private String initPaySn;

	private String initOrderSn;

	private String changeType;

	private String beforeValue;

	private String afterValue;

	private String createUser;

	private String remark1;

	private String remark2;

	private String remark3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("newPaySn", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("newOrderSn", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("oldPaySn", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("oldOrderSn", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("initPaySn", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("initOrderSn", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("changeType", DataColumn.STRING, 8, 2 , 0 , false , false),
		new SchemaColumn("beforeValue", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("afterValue", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 14, 50 , 0 , false , false)
	};

	public static final String _TableCode = "PolicyChangeInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PolicyChangeInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PolicyChangeInfo set id=?,createDate=?,newPaySn=?,newOrderSn=?,oldPaySn=?,oldOrderSn=?,initPaySn=?,initOrderSn=?,changeType=?,beforeValue=?,afterValue=?,createUser=?,remark1=?,remark2=?,remark3=? where id=?";

	protected static final String _DeleteSQL = "delete from PolicyChangeInfo  where id=?";

	protected static final String _FillAllSQL = "select * from PolicyChangeInfo  where id=?";

	public PolicyChangeInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[15];
	}

	protected Schema newInstance(){
		return new PolicyChangeInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PolicyChangeInfoSet();
	}

	public PolicyChangeInfoSet query() {
		return query(null, -1, -1);
	}

	public PolicyChangeInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PolicyChangeInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PolicyChangeInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PolicyChangeInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){newPaySn = (String)v;return;}
		if (i == 3){newOrderSn = (String)v;return;}
		if (i == 4){oldPaySn = (String)v;return;}
		if (i == 5){oldOrderSn = (String)v;return;}
		if (i == 6){initPaySn = (String)v;return;}
		if (i == 7){initOrderSn = (String)v;return;}
		if (i == 8){changeType = (String)v;return;}
		if (i == 9){beforeValue = (String)v;return;}
		if (i == 10){afterValue = (String)v;return;}
		if (i == 11){createUser = (String)v;return;}
		if (i == 12){remark1 = (String)v;return;}
		if (i == 13){remark2 = (String)v;return;}
		if (i == 14){remark3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return newPaySn;}
		if (i == 3){return newOrderSn;}
		if (i == 4){return oldPaySn;}
		if (i == 5){return oldOrderSn;}
		if (i == 6){return initPaySn;}
		if (i == 7){return initOrderSn;}
		if (i == 8){return changeType;}
		if (i == 9){return beforeValue;}
		if (i == 10){return afterValue;}
		if (i == 11){return createUser;}
		if (i == 12){return remark1;}
		if (i == 13){return remark2;}
		if (i == 14){return remark3;}
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
	* 获取字段newPaySn的值，该字段的<br>
	* 字段名称 :新交易流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getnewPaySn() {
		return newPaySn;
	}

	/**
	* 设置字段newPaySn的值，该字段的<br>
	* 字段名称 :新交易流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setnewPaySn(String newPaySn) {
		this.newPaySn = newPaySn;
    }

	/**
	* 获取字段newOrderSn的值，该字段的<br>
	* 字段名称 :新订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getnewOrderSn() {
		return newOrderSn;
	}

	/**
	* 设置字段newOrderSn的值，该字段的<br>
	* 字段名称 :新订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setnewOrderSn(String newOrderSn) {
		this.newOrderSn = newOrderSn;
    }

	/**
	* 获取字段oldPaySn的值，该字段的<br>
	* 字段名称 :旧交易流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoldPaySn() {
		return oldPaySn;
	}

	/**
	* 设置字段oldPaySn的值，该字段的<br>
	* 字段名称 :旧交易流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoldPaySn(String oldPaySn) {
		this.oldPaySn = oldPaySn;
    }

	/**
	* 获取字段oldOrderSn的值，该字段的<br>
	* 字段名称 :旧订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoldOrderSn() {
		return oldOrderSn;
	}

	/**
	* 设置字段oldOrderSn的值，该字段的<br>
	* 字段名称 :旧订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoldOrderSn(String oldOrderSn) {
		this.oldOrderSn = oldOrderSn;
    }

	/**
	* 获取字段initPaySn的值，该字段的<br>
	* 字段名称 :初始交易流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinitPaySn() {
		return initPaySn;
	}

	/**
	* 设置字段initPaySn的值，该字段的<br>
	* 字段名称 :初始交易流水号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinitPaySn(String initPaySn) {
		this.initPaySn = initPaySn;
    }

	/**
	* 获取字段initOrderSn的值，该字段的<br>
	* 字段名称 :初始订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinitOrderSn() {
		return initOrderSn;
	}

	/**
	* 设置字段initOrderSn的值，该字段的<br>
	* 字段名称 :初始订单号<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinitOrderSn(String initOrderSn) {
		this.initOrderSn = initOrderSn;
    }

	/**
	* 获取字段changeType的值，该字段的<br>
	* 字段名称 :变更类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchangeType() {
		return changeType;
	}

	/**
	* 设置字段changeType的值，该字段的<br>
	* 字段名称 :变更类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchangeType(String changeType) {
		this.changeType = changeType;
    }

	/**
	* 获取字段beforeValue的值，该字段的<br>
	* 字段名称 :变更前Value<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbeforeValue() {
		return beforeValue;
	}

	/**
	* 设置字段beforeValue的值，该字段的<br>
	* 字段名称 :变更前Value<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
    }

	/**
	* 获取字段afterValue的值，该字段的<br>
	* 字段名称 :变更后Value<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getafterValue() {
		return afterValue;
	}

	/**
	* 设置字段afterValue的值，该字段的<br>
	* 字段名称 :变更后Value<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setafterValue(String afterValue) {
		this.afterValue = afterValue;
    }

	/**
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :操作用户<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :操作用户<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
    }

	/**
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

	/**
	* 获取字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark2() {
		return remark2;
	}

	/**
	* 设置字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark2(String remark2) {
		this.remark2 = remark2;
    }

	/**
	* 获取字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark3() {
		return remark3;
	}

	/**
	* 设置字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark3(String remark3) {
		this.remark3 = remark3;
    }

}