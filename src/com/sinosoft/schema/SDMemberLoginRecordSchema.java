package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：会员登录记录表<br>
 * 表代码：SDMemberLoginRecord<br>
 * 表主键：SSOID<br>
 */
public class SDMemberLoginRecordSchema extends Schema {
	private String SSOID;

	private String MemberID;

	private String LastOprTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("SSOID", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("MemberID", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("LastOprTime", DataColumn.STRING, 2, 50 , 0 , false , false)
	};

	public static final String _TableCode = "SDMemberLoginRecord";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDMemberLoginRecord values(?,?,?)";

	protected static final String _UpdateAllSQL = "update SDMemberLoginRecord set SSOID=?,MemberID=?,LastOprTime=? where SSOID=?";

	protected static final String _DeleteSQL = "delete from SDMemberLoginRecord  where SSOID=?";

	protected static final String _FillAllSQL = "select * from SDMemberLoginRecord  where SSOID=?";

	public SDMemberLoginRecordSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[3];
	}

	protected Schema newInstance(){
		return new SDMemberLoginRecordSchema();
	}

	protected SchemaSet newSet(){
		return new SDMemberLoginRecordSet();
	}

	public SDMemberLoginRecordSet query() {
		return query(null, -1, -1);
	}

	public SDMemberLoginRecordSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDMemberLoginRecordSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDMemberLoginRecordSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDMemberLoginRecordSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){SSOID = (String)v;return;}
		if (i == 1){MemberID = (String)v;return;}
		if (i == 2){LastOprTime = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return SSOID;}
		if (i == 1){return MemberID;}
		if (i == 2){return LastOprTime;}
		return null;
	}

	/**
	* 获取字段SSOID的值，该字段的<br>
	* 字段名称 :登录标识<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getSSOID() {
		return SSOID;
	}

	/**
	* 设置字段SSOID的值，该字段的<br>
	* 字段名称 :登录标识<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setSSOID(String sSOID) {
		this.SSOID = sSOID;
    }

	/**
	* 获取字段MemberID的值，该字段的<br>
	* 字段名称 :会员编码<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemberID() {
		return MemberID;
	}

	/**
	* 设置字段MemberID的值，该字段的<br>
	* 字段名称 :会员编码<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemberID(String memberID) {
		this.MemberID = memberID;
    }

	/**
	* 获取字段LastOprTime的值，该字段的<br>
	* 字段名称 :最后一次操作时间<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLastOprTime() {
		return LastOprTime;
	}

	/**
	* 设置字段LastOprTime的值，该字段的<br>
	* 字段名称 :最后一次操作时间<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLastOprTime(String lastOprTime) {
		this.LastOprTime = lastOprTime;
    }

}