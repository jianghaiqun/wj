package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：会员级别表备份<br>
 * 表代码：BZDMemberLevel<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZDMemberLevelSchema extends Schema {
	private Long ID;

	private String Name;

	private String Type;

	private Float Discount;

	private String IsDefault;

	private Integer TreeLevel;

	private Long Score;

	private String IsValidate;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 2, 10 , 0 , true , false),
		new SchemaColumn("Discount", DataColumn.FLOAT, 3, 3 , 1 , true , false),
		new SchemaColumn("IsDefault", DataColumn.STRING, 4, 1 , 0 , true , false),
		new SchemaColumn("TreeLevel", DataColumn.INTEGER, 5, 0 , 0 , true , false),
		new SchemaColumn("Score", DataColumn.LONG, 6, 0 , 0 , true , false),
		new SchemaColumn("IsValidate", DataColumn.STRING, 7, 1 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 8, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 9, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 11, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 12, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 13, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 14, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 15, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDMemberLevel";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into BZDMemberLevel values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDMemberLevel set ID=?,Name=?,Type=?,Discount=?,IsDefault=?,TreeLevel=?,Score=?,IsValidate=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDMemberLevel  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDMemberLevel  where ID=? and BackupNo=?";

	public BZDMemberLevelSchema(){
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
		return new BZDMemberLevelSchema();
	}

	protected SchemaSet newSet(){
		return new BZDMemberLevelSet();
	}

	public BZDMemberLevelSet query() {
		return query(null, -1, -1);
	}

	public BZDMemberLevelSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDMemberLevelSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDMemberLevelSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDMemberLevelSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){Type = (String)v;return;}
		if (i == 3){if(v==null){Discount = null;}else{Discount = new Float(v.toString());}return;}
		if (i == 4){IsDefault = (String)v;return;}
		if (i == 5){if(v==null){TreeLevel = null;}else{TreeLevel = new Integer(v.toString());}return;}
		if (i == 6){if(v==null){Score = null;}else{Score = new Long(v.toString());}return;}
		if (i == 7){IsValidate = (String)v;return;}
		if (i == 8){AddUser = (String)v;return;}
		if (i == 9){AddTime = (Date)v;return;}
		if (i == 10){ModifyUser = (String)v;return;}
		if (i == 11){ModifyTime = (Date)v;return;}
		if (i == 12){BackupNo = (String)v;return;}
		if (i == 13){BackupOperator = (String)v;return;}
		if (i == 14){BackupTime = (Date)v;return;}
		if (i == 15){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return Type;}
		if (i == 3){return Discount;}
		if (i == 4){return IsDefault;}
		if (i == 5){return TreeLevel;}
		if (i == 6){return Score;}
		if (i == 7){return IsValidate;}
		if (i == 8){return AddUser;}
		if (i == 9){return AddTime;}
		if (i == 10){return ModifyUser;}
		if (i == 11){return ModifyTime;}
		if (i == 12){return BackupNo;}
		if (i == 13){return BackupOperator;}
		if (i == 14){return BackupTime;}
		if (i == 15){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :会员级别ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :会员级别ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :会员级别ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :会员级别名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :会员级别名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :会员级别类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :会员级别类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段Discount的值，该字段的<br>
	* 字段名称 :折扣<br>
	* 数据类型 :float(3,1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public float getDiscount() {
		if(Discount==null){return 0;}
		return Discount.floatValue();
	}

	/**
	* 设置字段Discount的值，该字段的<br>
	* 字段名称 :折扣<br>
	* 数据类型 :float(3,1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDiscount(float discount) {
		this.Discount = new Float(discount);
    }

	/**
	* 设置字段Discount的值，该字段的<br>
	* 字段名称 :折扣<br>
	* 数据类型 :float(3,1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDiscount(String discount) {
		if (discount == null){
			this.Discount = null;
			return;
		}
		this.Discount = new Float(discount);
    }

	/**
	* 获取字段IsDefault的值，该字段的<br>
	* 字段名称 :是否默认<br>
	* 数据类型 :char(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIsDefault() {
		return IsDefault;
	}

	/**
	* 设置字段IsDefault的值，该字段的<br>
	* 字段名称 :是否默认<br>
	* 数据类型 :char(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIsDefault(String isDefault) {
		this.IsDefault = isDefault;
    }

	/**
	* 获取字段TreeLevel的值，该字段的<br>
	* 字段名称 :级别<br>
	* 数据类型 :smallint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getTreeLevel() {
		if(TreeLevel==null){return 0;}
		return TreeLevel.intValue();
	}

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :级别<br>
	* 数据类型 :smallint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTreeLevel(int treeLevel) {
		this.TreeLevel = new Integer(treeLevel);
    }

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :级别<br>
	* 数据类型 :smallint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTreeLevel(String treeLevel) {
		if (treeLevel == null){
			this.TreeLevel = null;
			return;
		}
		this.TreeLevel = new Integer(treeLevel);
    }

	/**
	* 获取字段Score的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getScore() {
		if(Score==null){return 0;}
		return Score.longValue();
	}

	/**
	* 设置字段Score的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setScore(long score) {
		this.Score = new Long(score);
    }

	/**
	* 设置字段Score的值，该字段的<br>
	* 字段名称 :积分<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setScore(String score) {
		if (score == null){
			this.Score = null;
			return;
		}
		this.Score = new Long(score);
    }

	/**
	* 获取字段IsValidate的值，该字段的<br>
	* 字段名称 :是否有效<br>
	* 数据类型 :char(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIsValidate() {
		return IsValidate;
	}

	/**
	* 设置字段IsValidate的值，该字段的<br>
	* 字段名称 :是否有效<br>
	* 数据类型 :char(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIsValidate(String isValidate) {
		this.IsValidate = isValidate;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
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