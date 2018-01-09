package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：邮件发送日志表<br>
 * 表代码：SCSendEmailLog<br>
 * 表主键：Id<br>
 */
public class SCSendEmailLogSchema extends Schema {
	private String Id;

	private String Subject;

	private String Email;

	private String UserName;

	private String ServiceBussNo;

	private Date MakeDate;

	private String MakeTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("Subject", DataColumn.STRING, 1, 200 , 0 , true , false),
		new SchemaColumn("Email", DataColumn.STRING, 2, 50 , 0 , true , false),
		new SchemaColumn("UserName", DataColumn.STRING, 3, 80 , 0 , false , false),
		new SchemaColumn("ServiceBussNo", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("MakeDate", DataColumn.DATETIME, 5, 0 , 0 , true , false),
		new SchemaColumn("MakeTime", DataColumn.STRING, 6, 8 , 0 , true , false)
	};

	public static final String _TableCode = "SCSendEmailLog";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SCSendEmailLog values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SCSendEmailLog set Id=?,Subject=?,Email=?,UserName=?,ServiceBussNo=?,MakeDate=?,MakeTime=? where Id=?";

	protected static final String _DeleteSQL = "delete from SCSendEmailLog  where Id=?";

	protected static final String _FillAllSQL = "select * from SCSendEmailLog  where Id=?";

	public SCSendEmailLogSchema(){
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
		return new SCSendEmailLogSchema();
	}

	protected SchemaSet newSet(){
		return new SCSendEmailLogSet();
	}

	public SCSendEmailLogSet query() {
		return query(null, -1, -1);
	}

	public SCSendEmailLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SCSendEmailLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SCSendEmailLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SCSendEmailLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){Subject = (String)v;return;}
		if (i == 2){Email = (String)v;return;}
		if (i == 3){UserName = (String)v;return;}
		if (i == 4){ServiceBussNo = (String)v;return;}
		if (i == 5){MakeDate = (Date)v;return;}
		if (i == 6){MakeTime = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return Subject;}
		if (i == 2){return Email;}
		if (i == 3){return UserName;}
		if (i == 4){return ServiceBussNo;}
		if (i == 5){return MakeDate;}
		if (i == 6){return MakeTime;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段Subject的值，该字段的<br>
	* 字段名称 :Subject<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getSubject() {
		return Subject;
	}

	/**
	* 设置字段Subject的值，该字段的<br>
	* 字段名称 :Subject<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSubject(String subject) {
		this.Subject = subject;
    }

	/**
	* 获取字段Email的值，该字段的<br>
	* 字段名称 :Email<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getEmail() {
		return Email;
	}

	/**
	* 设置字段Email的值，该字段的<br>
	* 字段名称 :Email<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setEmail(String email) {
		this.Email = email;
    }

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(80)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段ServiceBussNo的值，该字段的<br>
	* 字段名称 :ServiceBussNo<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getServiceBussNo() {
		return ServiceBussNo;
	}

	/**
	* 设置字段ServiceBussNo的值，该字段的<br>
	* 字段名称 :ServiceBussNo<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setServiceBussNo(String serviceBussNo) {
		this.ServiceBussNo = serviceBussNo;
    }

	/**
	* 获取字段MakeDate的值，该字段的<br>
	* 字段名称 :MakeDate<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getMakeDate() {
		return MakeDate;
	}

	/**
	* 设置字段MakeDate的值，该字段的<br>
	* 字段名称 :MakeDate<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMakeDate(Date makeDate) {
		this.MakeDate = makeDate;
    }

	/**
	* 获取字段MakeTime的值，该字段的<br>
	* 字段名称 :MakeTime<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getMakeTime() {
		return MakeTime;
	}

	/**
	* 设置字段MakeTime的值，该字段的<br>
	* 字段名称 :MakeTime<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMakeTime(String makeTime) {
		this.MakeTime = makeTime;
    }

}