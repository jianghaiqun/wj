package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：栏目所属字段表<br>
 * 表代码：SCCatalogValue<br>
 * 表主键：Id<br>
 */
public class SCCatalogValueSchema extends Schema {
	private String Id;

	private String CTId;

	private String ValueName;

	private String ValueCode;

	private String VerifyType;

	private Integer MaxLength;

	private String InputType;

	private String DefaultValue;

	private String ListOption;

	private String HTML;

	private String IsMandatory;

	private Integer RowSize;

	private Integer ColSize;

	private String Prop1;

	private String Prop2;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("CTId", DataColumn.STRING, 1, 32 , 0 , true , false),
		new SchemaColumn("ValueName", DataColumn.STRING, 2, 50 , 0 , true , false),
		new SchemaColumn("ValueCode", DataColumn.STRING, 3, 20 , 0 , true , false),
		new SchemaColumn("VerifyType", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("MaxLength", DataColumn.INTEGER, 5, 11 , 0 , false , false),
		new SchemaColumn("InputType", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("DefaultValue", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("ListOption", DataColumn.STRING, 8, 1000 , 0 , false , false),
		new SchemaColumn("HTML", DataColumn.CLOB, 9, 0 , 0 , false , false),
		new SchemaColumn("IsMandatory", DataColumn.STRING, 10, 1 , 0 , false , false),
		new SchemaColumn("RowSize", DataColumn.INTEGER, 11, 11 , 0 , false , false),
		new SchemaColumn("ColSize", DataColumn.INTEGER, 12, 11 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 17, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 18, 50 , 0 , false , false)
	};

	public static final String _TableCode = "SCCatalogValue";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SCCatalogValue values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SCCatalogValue set Id=?,CTId=?,ValueName=?,ValueCode=?,VerifyType=?,MaxLength=?,InputType=?,DefaultValue=?,ListOption=?,HTML=?,IsMandatory=?,RowSize=?,ColSize=?,Prop1=?,Prop2=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=? where Id=?";

	protected static final String _DeleteSQL = "delete from SCCatalogValue  where Id=?";

	protected static final String _FillAllSQL = "select * from SCCatalogValue  where Id=?";

	public SCCatalogValueSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[19];
	}

	protected Schema newInstance(){
		return new SCCatalogValueSchema();
	}

	protected SchemaSet newSet(){
		return new SCCatalogValueSet();
	}

	public SCCatalogValueSet query() {
		return query(null, -1, -1);
	}

	public SCCatalogValueSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SCCatalogValueSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SCCatalogValueSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SCCatalogValueSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){CTId = (String)v;return;}
		if (i == 2){ValueName = (String)v;return;}
		if (i == 3){ValueCode = (String)v;return;}
		if (i == 4){VerifyType = (String)v;return;}
		if (i == 5){if(v==null){MaxLength = null;}else{MaxLength = new Integer(v.toString());}return;}
		if (i == 6){InputType = (String)v;return;}
		if (i == 7){DefaultValue = (String)v;return;}
		if (i == 8){ListOption = (String)v;return;}
		if (i == 9){HTML = (String)v;return;}
		if (i == 10){IsMandatory = (String)v;return;}
		if (i == 11){if(v==null){RowSize = null;}else{RowSize = new Integer(v.toString());}return;}
		if (i == 12){if(v==null){ColSize = null;}else{ColSize = new Integer(v.toString());}return;}
		if (i == 13){Prop1 = (String)v;return;}
		if (i == 14){Prop2 = (String)v;return;}
		if (i == 15){AddTime = (Date)v;return;}
		if (i == 16){AddUser = (String)v;return;}
		if (i == 17){ModifyTime = (Date)v;return;}
		if (i == 18){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return CTId;}
		if (i == 2){return ValueName;}
		if (i == 3){return ValueCode;}
		if (i == 4){return VerifyType;}
		if (i == 5){return MaxLength;}
		if (i == 6){return InputType;}
		if (i == 7){return DefaultValue;}
		if (i == 8){return ListOption;}
		if (i == 9){return HTML;}
		if (i == 10){return IsMandatory;}
		if (i == 11){return RowSize;}
		if (i == 12){return ColSize;}
		if (i == 13){return Prop1;}
		if (i == 14){return Prop2;}
		if (i == 15){return AddTime;}
		if (i == 16){return AddUser;}
		if (i == 17){return ModifyTime;}
		if (i == 18){return ModifyUser;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段CTId的值，该字段的<br>
	* 字段名称 :栏目类型编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCTId() {
		return CTId;
	}

	/**
	* 设置字段CTId的值，该字段的<br>
	* 字段名称 :栏目类型编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCTId(String cTId) {
		this.CTId = cTId;
    }

	/**
	* 获取字段ValueName的值，该字段的<br>
	* 字段名称 :字段名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getValueName() {
		return ValueName;
	}

	/**
	* 设置字段ValueName的值，该字段的<br>
	* 字段名称 :字段名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setValueName(String valueName) {
		this.ValueName = valueName;
    }

	/**
	* 获取字段ValueCode的值，该字段的<br>
	* 字段名称 :字段代码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getValueCode() {
		return ValueCode;
	}

	/**
	* 设置字段ValueCode的值，该字段的<br>
	* 字段名称 :字段代码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setValueCode(String valueCode) {
		this.ValueCode = valueCode;
    }

	/**
	* 获取字段VerifyType的值，该字段的<br>
	* 字段名称 :验证类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getVerifyType() {
		return VerifyType;
	}

	/**
	* 设置字段VerifyType的值，该字段的<br>
	* 字段名称 :验证类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVerifyType(String verifyType) {
		this.VerifyType = verifyType;
    }

	/**
	* 获取字段MaxLength的值，该字段的<br>
	* 字段名称 :最大长度<br>
	* 数据类型 :int(11)<br>
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
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMaxLength(int maxLength) {
		this.MaxLength = new Integer(maxLength);
    }

	/**
	* 设置字段MaxLength的值，该字段的<br>
	* 字段名称 :最大长度<br>
	* 数据类型 :int(11)<br>
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
	* 获取字段InputType的值，该字段的<br>
	* 字段名称 :字段类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInputType() {
		return InputType;
	}

	/**
	* 设置字段InputType的值，该字段的<br>
	* 字段名称 :字段类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInputType(String inputType) {
		this.InputType = inputType;
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
	* 获取字段ListOption的值，该字段的<br>
	* 字段名称 :选项<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getListOption() {
		return ListOption;
	}

	/**
	* 设置字段ListOption的值，该字段的<br>
	* 字段名称 :选项<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setListOption(String listOption) {
		this.ListOption = listOption;
    }

	/**
	* 获取字段HTML的值，该字段的<br>
	* 字段名称 :HTML<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHTML() {
		return HTML;
	}

	/**
	* 设置字段HTML的值，该字段的<br>
	* 字段名称 :HTML<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHTML(String hTML) {
		this.HTML = hTML;
    }

	/**
	* 获取字段IsMandatory的值，该字段的<br>
	* 字段名称 :是否必填<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsMandatory() {
		return IsMandatory;
	}

	/**
	* 设置字段IsMandatory的值，该字段的<br>
	* 字段名称 :是否必填<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsMandatory(String isMandatory) {
		this.IsMandatory = isMandatory;
    }

	/**
	* 获取字段RowSize的值，该字段的<br>
	* 字段名称 :RowSize<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getRowSize() {
		if(RowSize==null){return 0;}
		return RowSize.intValue();
	}

	/**
	* 设置字段RowSize的值，该字段的<br>
	* 字段名称 :RowSize<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRowSize(int rowSize) {
		this.RowSize = new Integer(rowSize);
    }

	/**
	* 设置字段RowSize的值，该字段的<br>
	* 字段名称 :RowSize<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRowSize(String rowSize) {
		if (rowSize == null){
			this.RowSize = null;
			return;
		}
		this.RowSize = new Integer(rowSize);
    }

	/**
	* 获取字段ColSize的值，该字段的<br>
	* 字段名称 :ColSize<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getColSize() {
		if(ColSize==null){return 0;}
		return ColSize.intValue();
	}

	/**
	* 设置字段ColSize的值，该字段的<br>
	* 字段名称 :ColSize<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setColSize(int colSize) {
		this.ColSize = new Integer(colSize);
    }

	/**
	* 设置字段ColSize的值，该字段的<br>
	* 字段名称 :ColSize<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setColSize(String colSize) {
		if (colSize == null){
			this.ColSize = null;
			return;
		}
		this.ColSize = new Integer(colSize);
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
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
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
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

}