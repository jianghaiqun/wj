package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：自定义数据表列信息备份<br>
 * 表代码：BZCCustomTableColumn<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCCustomTableColumnSchema extends Schema {
	private Long ID;

	private Long TableID;

	private String Code;

	private String Name;

	private String DataType;

	private Long Length;

	private String isPrimaryKey;

	private String isMandatory;

	private String DefaultValue;

	private String InputType;

	private String CSSStyle;

	private Integer MaxLength;

	private String ListOptions;

	private String isAutoID;

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
		new SchemaColumn("TableID", DataColumn.LONG, 1, 0 , 0 , false , false),
		new SchemaColumn("Code", DataColumn.STRING, 2, 50 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("DataType", DataColumn.STRING, 4, 50 , 0 , true , false),
		new SchemaColumn("Length", DataColumn.LONG, 5, 0 , 0 , false , false),
		new SchemaColumn("isPrimaryKey", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("isMandatory", DataColumn.STRING, 7, 2 , 0 , true , false),
		new SchemaColumn("DefaultValue", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("InputType", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("CSSStyle", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("MaxLength", DataColumn.INTEGER, 11, 0 , 0 , false , false),
		new SchemaColumn("ListOptions", DataColumn.CLOB, 12, 0 , 0 , false , false),
		new SchemaColumn("isAutoID", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 16, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 17, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 18, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 19, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 20, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 21, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 22, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 23, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCCustomTableColumn";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into BZCCustomTableColumn values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCCustomTableColumn set ID=?,TableID=?,Code=?,Name=?,DataType=?,Length=?,isPrimaryKey=?,isMandatory=?,DefaultValue=?,InputType=?,CSSStyle=?,MaxLength=?,ListOptions=?,isAutoID=?,Prop1=?,Prop2=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCCustomTableColumn  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCCustomTableColumn  where ID=? and BackupNo=?";

	public BZCCustomTableColumnSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[24];
	}

	protected Schema newInstance(){
		return new BZCCustomTableColumnSchema();
	}

	protected SchemaSet newSet(){
		return new BZCCustomTableColumnSet();
	}

	public BZCCustomTableColumnSet query() {
		return query(null, -1, -1);
	}

	public BZCCustomTableColumnSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCCustomTableColumnSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCCustomTableColumnSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCCustomTableColumnSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){TableID = null;}else{TableID = new Long(v.toString());}return;}
		if (i == 2){Code = (String)v;return;}
		if (i == 3){Name = (String)v;return;}
		if (i == 4){DataType = (String)v;return;}
		if (i == 5){if(v==null){Length = null;}else{Length = new Long(v.toString());}return;}
		if (i == 6){isPrimaryKey = (String)v;return;}
		if (i == 7){isMandatory = (String)v;return;}
		if (i == 8){DefaultValue = (String)v;return;}
		if (i == 9){InputType = (String)v;return;}
		if (i == 10){CSSStyle = (String)v;return;}
		if (i == 11){if(v==null){MaxLength = null;}else{MaxLength = new Integer(v.toString());}return;}
		if (i == 12){ListOptions = (String)v;return;}
		if (i == 13){isAutoID = (String)v;return;}
		if (i == 14){Prop1 = (String)v;return;}
		if (i == 15){Prop2 = (String)v;return;}
		if (i == 16){AddUser = (String)v;return;}
		if (i == 17){AddTime = (Date)v;return;}
		if (i == 18){ModifyUser = (String)v;return;}
		if (i == 19){ModifyTime = (Date)v;return;}
		if (i == 20){BackupNo = (String)v;return;}
		if (i == 21){BackupOperator = (String)v;return;}
		if (i == 22){BackupTime = (Date)v;return;}
		if (i == 23){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return TableID;}
		if (i == 2){return Code;}
		if (i == 3){return Name;}
		if (i == 4){return DataType;}
		if (i == 5){return Length;}
		if (i == 6){return isPrimaryKey;}
		if (i == 7){return isMandatory;}
		if (i == 8){return DefaultValue;}
		if (i == 9){return InputType;}
		if (i == 10){return CSSStyle;}
		if (i == 11){return MaxLength;}
		if (i == 12){return ListOptions;}
		if (i == 13){return isAutoID;}
		if (i == 14){return Prop1;}
		if (i == 15){return Prop2;}
		if (i == 16){return AddUser;}
		if (i == 17){return AddTime;}
		if (i == 18){return ModifyUser;}
		if (i == 19){return ModifyTime;}
		if (i == 20){return BackupNo;}
		if (i == 21){return BackupOperator;}
		if (i == 22){return BackupTime;}
		if (i == 23){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
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
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
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
	* 获取字段TableID的值，该字段的<br>
	* 字段名称 :表ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getTableID() {
		if(TableID==null){return 0;}
		return TableID.longValue();
	}

	/**
	* 设置字段TableID的值，该字段的<br>
	* 字段名称 :表ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTableID(long tableID) {
		this.TableID = new Long(tableID);
    }

	/**
	* 设置字段TableID的值，该字段的<br>
	* 字段名称 :表ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTableID(String tableID) {
		if (tableID == null){
			this.TableID = null;
			return;
		}
		this.TableID = new Long(tableID);
    }

	/**
	* 获取字段Code的值，该字段的<br>
	* 字段名称 :代码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* 设置字段Code的值，该字段的<br>
	* 字段名称 :代码<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCode(String code) {
		this.Code = code;
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段DataType的值，该字段的<br>
	* 字段名称 :数据类型<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getDataType() {
		return DataType;
	}

	/**
	* 设置字段DataType的值，该字段的<br>
	* 字段名称 :数据类型<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDataType(String dataType) {
		this.DataType = dataType;
    }

	/**
	* 获取字段Length的值，该字段的<br>
	* 字段名称 :数据长度<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getLength() {
		if(Length==null){return 0;}
		return Length.longValue();
	}

	/**
	* 设置字段Length的值，该字段的<br>
	* 字段名称 :数据长度<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLength(long length) {
		this.Length = new Long(length);
    }

	/**
	* 设置字段Length的值，该字段的<br>
	* 字段名称 :数据长度<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLength(String length) {
		if (length == null){
			this.Length = null;
			return;
		}
		this.Length = new Long(length);
    }

	/**
	* 获取字段isPrimaryKey的值，该字段的<br>
	* 字段名称 :是否主键<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsPrimaryKey() {
		return isPrimaryKey;
	}

	/**
	* 设置字段isPrimaryKey的值，该字段的<br>
	* 字段名称 :是否主键<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
    }

	/**
	* 获取字段isMandatory的值，该字段的<br>
	* 字段名称 :是否必填<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIsMandatory() {
		return isMandatory;
	}

	/**
	* 设置字段isMandatory的值，该字段的<br>
	* 字段名称 :是否必填<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
    }

	/**
	* 获取字段DefaultValue的值，该字段的<br>
	* 字段名称 :默认值<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDefaultValue() {
		return DefaultValue;
	}

	/**
	* 设置字段DefaultValue的值，该字段的<br>
	* 字段名称 :默认值<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDefaultValue(String defaultValue) {
		this.DefaultValue = defaultValue;
    }

	/**
	* 获取字段InputType的值，该字段的<br>
	* 字段名称 :表现形式<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInputType() {
		return InputType;
	}

	/**
	* 设置字段InputType的值，该字段的<br>
	* 字段名称 :表现形式<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInputType(String inputType) {
		this.InputType = inputType;
    }

	/**
	* 获取字段CSSStyle的值，该字段的<br>
	* 字段名称 :CSS样式<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCSSStyle() {
		return CSSStyle;
	}

	/**
	* 设置字段CSSStyle的值，该字段的<br>
	* 字段名称 :CSS样式<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCSSStyle(String cSSStyle) {
		this.CSSStyle = cSSStyle;
    }

	/**
	* 获取字段MaxLength的值，该字段的<br>
	* 字段名称 :最大长度<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getMaxLength() {
		if(MaxLength==null){return 0;}
		return MaxLength.intValue();
	}

	/**
	* 设置字段MaxLength的值，该字段的<br>
	* 字段名称 :最大长度<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMaxLength(int maxLength) {
		this.MaxLength = new Integer(maxLength);
    }

	/**
	* 设置字段MaxLength的值，该字段的<br>
	* 字段名称 :最大长度<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMaxLength(String maxLength) {
		if (maxLength == null){
			this.MaxLength = null;
			return;
		}
		this.MaxLength = new Integer(maxLength);
    }

	/**
	* 获取字段ListOptions的值，该字段的<br>
	* 字段名称 :下拉框选项<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getListOptions() {
		return ListOptions;
	}

	/**
	* 设置字段ListOptions的值，该字段的<br>
	* 字段名称 :下拉框选项<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setListOptions(String listOptions) {
		this.ListOptions = listOptions;
    }

	/**
	* 获取字段isAutoID的值，该字段的<br>
	* 字段名称 :是否自动编号<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsAutoID() {
		return isAutoID;
	}

	/**
	* 设置字段isAutoID的值，该字段的<br>
	* 字段名称 :是否自动编号<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsAutoID(String isAutoID) {
		this.isAutoID = isAutoID;
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