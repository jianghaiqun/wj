package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：BZDUser<br>
 * 表代码：BZDUser<br>
 * 表主键：UserName, BackupNo<br>
 */
public class BZDUserSchema extends Schema {
	private String UserName;

	private String RealName;

	private String Password;

	private String BranchInnerCode;

	private String IsBranchAdmin;

	private String Status;

	private String Type;

	private String Email;

	private String Tel;

	private String Mobile;

	private String Prop1;

	private String Prop2;

	private String Prop6;

	private String Prop5;

	private String Prop4;

	private String Prop3;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	private String UserSign;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("RealName", DataColumn.STRING, 1, 200 , 0 , false , false),
		new SchemaColumn("Password", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("IsBranchAdmin", DataColumn.STRING, 4, 1 , 0 , true , false),
		new SchemaColumn("Status", DataColumn.STRING, 5, 50 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 6, 1 , 0 , true , false),
		new SchemaColumn("Email", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 14, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 15, 100 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 16, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 17, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 18, 200 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 19, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 20, 200 , 0 , false , false),
		new SchemaColumn("UserSign", DataColumn.STRING, 21, 10 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 22, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 23, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 24, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 25, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDUser";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into BZDUser values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDUser set UserName=?,RealName=?,Password=?,BranchInnerCode=?,IsBranchAdmin=?,Status=?,Type=?,Email=?,Tel=?,Mobile=?,Prop1=?,Prop2=?,Prop6=?,Prop5=?,Prop4=?,Prop3=?,Memo=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=?,UserSign=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where UserName=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDUser  where UserName=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDUser  where UserName=? and BackupNo=?";

	public BZDUserSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[26];
	}

	protected Schema newInstance(){
		return new BZDUserSchema();
	}

	protected SchemaSet newSet(){
		return new BZDUserSet();
	}

	public BZDUserSet query() {
		return query(null, -1, -1);
	}

	public BZDUserSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDUserSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDUserSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDUserSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){RealName = (String)v;return;}
		if (i == 2){Password = (String)v;return;}
		if (i == 3){BranchInnerCode = (String)v;return;}
		if (i == 4){IsBranchAdmin = (String)v;return;}
		if (i == 5){Status = (String)v;return;}
		if (i == 6){Type = (String)v;return;}
		if (i == 7){Email = (String)v;return;}
		if (i == 8){Tel = (String)v;return;}
		if (i == 9){Mobile = (String)v;return;}
		if (i == 10){Prop1 = (String)v;return;}
		if (i == 11){Prop2 = (String)v;return;}
		if (i == 12){Prop6 = (String)v;return;}
		if (i == 13){Prop5 = (String)v;return;}
		if (i == 14){Prop4 = (String)v;return;}
		if (i == 15){Prop3 = (String)v;return;}
		if (i == 16){Memo = (String)v;return;}
		if (i == 17){AddTime = (Date)v;return;}
		if (i == 18){AddUser = (String)v;return;}
		if (i == 19){ModifyTime = (Date)v;return;}
		if (i == 20){ModifyUser = (String)v;return;}
		if (i == 21){UserSign = (String)v;return;}
		if (i == 22){BackupNo = (String)v;return;}
		if (i == 23){BackupOperator = (String)v;return;}
		if (i == 24){BackupTime = (Date)v;return;}
		if (i == 25){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return RealName;}
		if (i == 2){return Password;}
		if (i == 3){return BranchInnerCode;}
		if (i == 4){return IsBranchAdmin;}
		if (i == 5){return Status;}
		if (i == 6){return Type;}
		if (i == 7){return Email;}
		if (i == 8){return Tel;}
		if (i == 9){return Mobile;}
		if (i == 10){return Prop1;}
		if (i == 11){return Prop2;}
		if (i == 12){return Prop6;}
		if (i == 13){return Prop5;}
		if (i == 14){return Prop4;}
		if (i == 15){return Prop3;}
		if (i == 16){return Memo;}
		if (i == 17){return AddTime;}
		if (i == 18){return AddUser;}
		if (i == 19){return ModifyTime;}
		if (i == 20){return ModifyUser;}
		if (i == 21){return UserSign;}
		if (i == 22){return BackupNo;}
		if (i == 23){return BackupOperator;}
		if (i == 24){return BackupTime;}
		if (i == 25){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段RealName的值，该字段的<br>
	* 字段名称 :RealName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRealName() {
		return RealName;
	}

	/**
	* 设置字段RealName的值，该字段的<br>
	* 字段名称 :RealName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRealName(String realName) {
		this.RealName = realName;
    }

	/**
	* 获取字段Password的值，该字段的<br>
	* 字段名称 :Password<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getPassword() {
		return Password;
	}

	/**
	* 设置字段Password的值，该字段的<br>
	* 字段名称 :Password<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setPassword(String password) {
		this.Password = password;
    }

	/**
	* 获取字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :BranchInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* 设置字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :BranchInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
    }

	/**
	* 获取字段IsBranchAdmin的值，该字段的<br>
	* 字段名称 :IsBranchAdmin<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIsBranchAdmin() {
		return IsBranchAdmin;
	}

	/**
	* 设置字段IsBranchAdmin的值，该字段的<br>
	* 字段名称 :IsBranchAdmin<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIsBranchAdmin(String isBranchAdmin) {
		this.IsBranchAdmin = isBranchAdmin;
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段Email的值，该字段的<br>
	* 字段名称 :Email<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEmail() {
		return Email;
	}

	/**
	* 设置字段Email的值，该字段的<br>
	* 字段名称 :Email<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEmail(String email) {
		this.Email = email;
    }

	/**
	* 获取字段Tel的值，该字段的<br>
	* 字段名称 :Tel<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTel() {
		return Tel;
	}

	/**
	* 设置字段Tel的值，该字段的<br>
	* 字段名称 :Tel<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTel(String tel) {
		this.Tel = tel;
    }

	/**
	* 获取字段Mobile的值，该字段的<br>
	* 字段名称 :Mobile<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMobile() {
		return Mobile;
	}

	/**
	* 设置字段Mobile的值，该字段的<br>
	* 字段名称 :Mobile<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMobile(String mobile) {
		this.Mobile = mobile;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* 设置字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :ModifyTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :ModifyTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段UserSign的值，该字段的<br>
	* 字段名称 :UserSign<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUserSign() {
		return UserSign;
	}

	/**
	* 设置字段UserSign的值，该字段的<br>
	* 字段名称 :UserSign<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUserSign(String userSign) {
		this.UserSign = userSign;
    }

	/**
	* 获取字段BackupNo的值，该字段的<br>
	* 字段名称 :BackupNo<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getBackupNo() {
		return BackupNo;
	}

	/**
	* 设置字段BackupNo的值，该字段的<br>
	* 字段名称 :BackupNo<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBackupNo(String backupNo) {
		this.BackupNo = backupNo;
    }

	/**
	* 获取字段BackupOperator的值，该字段的<br>
	* 字段名称 :BackupOperator<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBackupOperator() {
		return BackupOperator;
	}

	/**
	* 设置字段BackupOperator的值，该字段的<br>
	* 字段名称 :BackupOperator<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupOperator(String backupOperator) {
		this.BackupOperator = backupOperator;
    }

	/**
	* 获取字段BackupTime的值，该字段的<br>
	* 字段名称 :BackupTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getBackupTime() {
		return BackupTime;
	}

	/**
	* 设置字段BackupTime的值，该字段的<br>
	* 字段名称 :BackupTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupTime(Date backupTime) {
		this.BackupTime = backupTime;
    }

	/**
	* 获取字段BackupMemo的值，该字段的<br>
	* 字段名称 :BackupMemo<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackupMemo() {
		return BackupMemo;
	}

	/**
	* 设置字段BackupMemo的值，该字段的<br>
	* 字段名称 :BackupMemo<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackupMemo(String backupMemo) {
		this.BackupMemo = backupMemo;
    }

}