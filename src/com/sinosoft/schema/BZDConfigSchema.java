package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：配置项表备份<br>
 * 表代码：BZDConfig<br>
 * 表主键：Type, BackupNo<br>
 */
public class BZDConfigSchema extends Schema {
	private String Type;

	private String Name;

	private String Value;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Type", DataColumn.STRING, 0, 40 , 0 , true , true),
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Value", DataColumn.STRING, 2, 200 , 0 , true , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 7, 400 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 8, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 9, 200 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 12, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 13, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 14, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 15, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDConfig";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into BZDConfig values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDConfig set Type=?,Name=?,Value=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Memo=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where Type=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDConfig  where Type=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDConfig  where Type=? and BackupNo=?";

	public BZDConfigSchema(){
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
		return new BZDConfigSchema();
	}

	protected SchemaSet newSet(){
		return new BZDConfigSet();
	}

	public BZDConfigSet query() {
		return query(null, -1, -1);
	}

	public BZDConfigSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDConfigSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDConfigSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDConfigSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Type = (String)v;return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){Value = (String)v;return;}
		if (i == 3){Prop1 = (String)v;return;}
		if (i == 4){Prop2 = (String)v;return;}
		if (i == 5){Prop3 = (String)v;return;}
		if (i == 6){Prop4 = (String)v;return;}
		if (i == 7){Memo = (String)v;return;}
		if (i == 8){AddTime = (Date)v;return;}
		if (i == 9){AddUser = (String)v;return;}
		if (i == 10){ModifyTime = (Date)v;return;}
		if (i == 11){ModifyUser = (String)v;return;}
		if (i == 12){BackupNo = (String)v;return;}
		if (i == 13){BackupOperator = (String)v;return;}
		if (i == 14){BackupTime = (Date)v;return;}
		if (i == 15){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Type;}
		if (i == 1){return Name;}
		if (i == 2){return Value;}
		if (i == 3){return Prop1;}
		if (i == 4){return Prop2;}
		if (i == 5){return Prop3;}
		if (i == 6){return Prop4;}
		if (i == 7){return Memo;}
		if (i == 8){return AddTime;}
		if (i == 9){return AddUser;}
		if (i == 10){return ModifyTime;}
		if (i == 11){return ModifyUser;}
		if (i == 12){return BackupNo;}
		if (i == 13){return BackupOperator;}
		if (i == 14){return BackupTime;}
		if (i == 15){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :配置项类别<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :配置项类别<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :配置项名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :配置项名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段Value的值，该字段的<br>
	* 字段名称 :配置项值<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getValue() {
		return Value;
	}

	/**
	* 设置字段Value的值，该字段的<br>
	* 字段名称 :配置项值<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setValue(String value) {
		this.Value = value;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :配置项属性1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :配置项属性1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :配置项属性2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :配置项属性2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :配置项属性3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :配置项属性3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :配置项属性4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :配置项属性4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(400)<br>
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
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :增加人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :增加人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
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