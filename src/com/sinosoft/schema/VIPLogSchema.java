package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：VIP升降级记录表<br>
 * 表代码：VIPLog<br>
 * 表主键：id<br>
 */
public class VIPLogSchema extends Schema {
	private String id;

	private String memberId;

	private String operation;

	private String operaUser;

	private Date operaTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("memberId", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("operation", DataColumn.STRING, 2, 40 , 0 , false , false),
		new SchemaColumn("operaUser", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("operaTime", DataColumn.DATETIME, 4, 0 , 0 , false , false)
	};

	public static final String _TableCode = "VIPLog";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into VIPLog values(?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update VIPLog set id=?,memberId=?,operation=?,operaUser=?,operaTime=? where id=?";

	protected static final String _DeleteSQL = "delete from VIPLog  where id=?";

	protected static final String _FillAllSQL = "select * from VIPLog  where id=?";

	public VIPLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[5];
	}

	protected Schema newInstance(){
		return new VIPLogSchema();
	}

	protected SchemaSet newSet(){
		return new VIPLogSet();
	}

	public VIPLogSet query() {
		return query(null, -1, -1);
	}

	public VIPLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public VIPLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public VIPLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (VIPLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){memberId = (String)v;return;}
		if (i == 2){operation = (String)v;return;}
		if (i == 3){operaUser = (String)v;return;}
		if (i == 4){operaTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return memberId;}
		if (i == 2){return operation;}
		if (i == 3){return operaUser;}
		if (i == 4){return operaTime;}
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
	* 获取字段memberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemberId() {
		return memberId;
	}

	/**
	* 设置字段memberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemberId(String memberId) {
		this.memberId = memberId;
    }

	/**
	* 获取字段operation的值，该字段的<br>
	* 字段名称 :操作<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoperation() {
		return operation;
	}

	/**
	* 设置字段operation的值，该字段的<br>
	* 字段名称 :操作<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoperation(String operation) {
		this.operation = operation;
    }

	/**
	* 获取字段operaUser的值，该字段的<br>
	* 字段名称 :操作人<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoperaUser() {
		return operaUser;
	}

	/**
	* 设置字段operaUser的值，该字段的<br>
	* 字段名称 :操作人<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoperaUser(String operaUser) {
		this.operaUser = operaUser;
    }

	/**
	* 获取字段operaTime的值，该字段的<br>
	* 字段名称 :操作时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getoperaTime() {
		return operaTime;
	}

	/**
	* 设置字段operaTime的值，该字段的<br>
	* 字段名称 :操作时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoperaTime(Date operaTime) {
		this.operaTime = operaTime;
    }

}