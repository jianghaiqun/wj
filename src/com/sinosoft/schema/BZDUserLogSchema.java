package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：用户日志表备份<br>
 * 表代码：BZDUserLog<br>
 * 表主键：UserName, LogID, BackupNo<br>
 */
public class BZDUserLogSchema extends Schema {
	private String UserName;

	private Long LogID;

	private String IP;

	private String LogType;

	private String SubType;

	private String LogMessage;

	private String Memo;

	private Date AddTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("LogID", DataColumn.LONG, 1, 0 , 0 , true , true),
		new SchemaColumn("IP", DataColumn.STRING, 2, 40 , 0 , false , false),
		new SchemaColumn("LogType", DataColumn.STRING, 3, 20 , 0 , true , false),
		new SchemaColumn("SubType", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("LogMessage", DataColumn.STRING, 5, 400 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 6, 40 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 7, 0 , 0 , true , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 8, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 9, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 10, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 11, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDUserLog";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into BZDUserLog values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDUserLog set UserName=?,LogID=?,IP=?,LogType=?,SubType=?,LogMessage=?,Memo=?,AddTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where UserName=? and LogID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDUserLog  where UserName=? and LogID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDUserLog  where UserName=? and LogID=? and BackupNo=?";

	public BZDUserLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new BZDUserLogSchema();
	}

	protected SchemaSet newSet(){
		return new BZDUserLogSet();
	}

	public BZDUserLogSet query() {
		return query(null, -1, -1);
	}

	public BZDUserLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDUserLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDUserLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDUserLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){if(v==null){LogID = null;}else{LogID = new Long(v.toString());}return;}
		if (i == 2){IP = (String)v;return;}
		if (i == 3){LogType = (String)v;return;}
		if (i == 4){SubType = (String)v;return;}
		if (i == 5){LogMessage = (String)v;return;}
		if (i == 6){Memo = (String)v;return;}
		if (i == 7){AddTime = (Date)v;return;}
		if (i == 8){BackupNo = (String)v;return;}
		if (i == 9){BackupOperator = (String)v;return;}
		if (i == 10){BackupTime = (Date)v;return;}
		if (i == 11){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return LogID;}
		if (i == 2){return IP;}
		if (i == 3){return LogType;}
		if (i == 4){return SubType;}
		if (i == 5){return LogMessage;}
		if (i == 6){return Memo;}
		if (i == 7){return AddTime;}
		if (i == 8){return BackupNo;}
		if (i == 9){return BackupOperator;}
		if (i == 10){return BackupTime;}
		if (i == 11){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :用户名<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :用户名<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段LogID的值，该字段的<br>
	* 字段名称 :记录ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getLogID() {
		if(LogID==null){return 0;}
		return LogID.longValue();
	}

	/**
	* 设置字段LogID的值，该字段的<br>
	* 字段名称 :记录ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setLogID(long logID) {
		this.LogID = new Long(logID);
    }

	/**
	* 设置字段LogID的值，该字段的<br>
	* 字段名称 :记录ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setLogID(String logID) {
		if (logID == null){
			this.LogID = null;
			return;
		}
		this.LogID = new Long(logID);
    }

	/**
	* 获取字段IP的值，该字段的<br>
	* 字段名称 :用户当前IP<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* 设置字段IP的值，该字段的<br>
	* 字段名称 :用户当前IP<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
    }

	/**
	* 获取字段LogType的值，该字段的<br>
	* 字段名称 :操作类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getLogType() {
		return LogType;
	}

	/**
	* 设置字段LogType的值，该字段的<br>
	* 字段名称 :操作类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setLogType(String logType) {
		this.LogType = logType;
    }

	/**
	* 获取字段SubType的值，该字段的<br>
	* 字段名称 :子操作类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSubType() {
		return SubType;
	}

	/**
	* 设置字段SubType的值，该字段的<br>
	* 字段名称 :子操作类型<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSubType(String subType) {
		this.SubType = subType;
    }

	/**
	* 获取字段LogMessage的值，该字段的<br>
	* 字段名称 :操作内容<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLogMessage() {
		return LogMessage;
	}

	/**
	* 设置字段LogMessage的值，该字段的<br>
	* 字段名称 :操作内容<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLogMessage(String logMessage) {
		this.LogMessage = logMessage;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :增加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :增加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getBackupNo() {
		return BackupNo;
	}

	/**
	* 设置字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBackupNo(String backupNo) {
		this.BackupNo = backupNo;
    }

	/**
	* 获取字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBackupOperator() {
		return BackupOperator;
	}

	/**
	* 设置字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupOperator(String backupOperator) {
		this.BackupOperator = backupOperator;
    }

	/**
	* 获取字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getBackupTime() {
		return BackupTime;
	}

	/**
	* 设置字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupTime(Date backupTime) {
		this.BackupTime = backupTime;
    }

	/**
	* 获取字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackupMemo() {
		return BackupMemo;
	}

	/**
	* 设置字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackupMemo(String backupMemo) {
		this.BackupMemo = backupMemo;
    }

}