package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：模板标签关联表备份<br>
 * 表代码：BZCTemplateTagRela<br>
 * 表主键：ID, TemplateID, BackupNo<br>
 */
public class BZCTemplateTagRelaSchema extends Schema {
	private Long ID;

	private Long TemplateID;

	private Long SiteID;

	private String Prop1;

	private String Prop2;

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
		new SchemaColumn("TemplateID", DataColumn.LONG, 1, 0 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 5, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 6, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 9, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 10, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 11, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 12, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCTemplateTagRela";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into BZCTemplateTagRela values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCTemplateTagRela set ID=?,TemplateID=?,SiteID=?,Prop1=?,Prop2=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and TemplateID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCTemplateTagRela  where ID=? and TemplateID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCTemplateTagRela  where ID=? and TemplateID=? and BackupNo=?";

	public BZCTemplateTagRelaSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[13];
	}

	protected Schema newInstance(){
		return new BZCTemplateTagRelaSchema();
	}

	protected SchemaSet newSet(){
		return new BZCTemplateTagRelaSet();
	}

	public BZCTemplateTagRelaSet query() {
		return query(null, -1, -1);
	}

	public BZCTemplateTagRelaSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCTemplateTagRelaSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCTemplateTagRelaSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCTemplateTagRelaSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){TemplateID = null;}else{TemplateID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 3){Prop1 = (String)v;return;}
		if (i == 4){Prop2 = (String)v;return;}
		if (i == 5){AddUser = (String)v;return;}
		if (i == 6){AddTime = (Date)v;return;}
		if (i == 7){ModifyUser = (String)v;return;}
		if (i == 8){ModifyTime = (Date)v;return;}
		if (i == 9){BackupNo = (String)v;return;}
		if (i == 10){BackupOperator = (String)v;return;}
		if (i == 11){BackupTime = (Date)v;return;}
		if (i == 12){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return TemplateID;}
		if (i == 2){return SiteID;}
		if (i == 3){return Prop1;}
		if (i == 4){return Prop2;}
		if (i == 5){return AddUser;}
		if (i == 6){return AddTime;}
		if (i == 7){return ModifyUser;}
		if (i == 8){return ModifyTime;}
		if (i == 9){return BackupNo;}
		if (i == 10){return BackupOperator;}
		if (i == 11){return BackupTime;}
		if (i == 12){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :标签ID<br>
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
	* 字段名称 :标签ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :标签ID<br>
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
	* 获取字段TemplateID的值，该字段的<br>
	* 字段名称 :模板ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getTemplateID() {
		if(TemplateID==null){return 0;}
		return TemplateID.longValue();
	}

	/**
	* 设置字段TemplateID的值，该字段的<br>
	* 字段名称 :模板ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setTemplateID(long templateID) {
		this.TemplateID = new Long(templateID);
    }

	/**
	* 设置字段TemplateID的值，该字段的<br>
	* 字段名称 :模板ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setTemplateID(String templateID) {
		if (templateID == null){
			this.TemplateID = null;
			return;
		}
		this.TemplateID = new Long(templateID);
    }

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :所属站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getSiteID() {
		if(SiteID==null){return 0;}
		return SiteID.longValue();
	}

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :所属站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :所属站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
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