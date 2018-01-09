package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：词条表<br>
 * 表代码：LRBOMItem<br>
 * 表主键：Name, BOMName<br>
 */
public class LRBOMItemSchema extends Schema {
	private String Name;

	private String BOMName;

	private String CNName;

	private String Connector;

	private String IsHierarchical;

	private String IsBase;

	private String SourceType;

	private String Source;

	private String CommandType;

	private String PreCheck;

	private String Valid;

	private String Description;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Name", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("BOMName", DataColumn.STRING, 1, 20 , 0 , true , true),
		new SchemaColumn("CNName", DataColumn.STRING, 2, 50 , 0 , true , false),
		new SchemaColumn("Connector", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("IsHierarchical", DataColumn.STRING, 4, 1 , 0 , true , false),
		new SchemaColumn("IsBase", DataColumn.STRING, 5, 1 , 0 , true , false),
		new SchemaColumn("SourceType", DataColumn.STRING, 6, 3 , 0 , false , false),
		new SchemaColumn("Source", DataColumn.STRING, 7, 500 , 0 , false , false),
		new SchemaColumn("CommandType", DataColumn.STRING, 8, 10 , 0 , true , false),
		new SchemaColumn("PreCheck", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Valid", DataColumn.STRING, 10, 1 , 0 , true , false),
		new SchemaColumn("Description", DataColumn.STRING, 11, 300 , 0 , false , false)
	};

	public static final String _TableCode = "LRBOMItem";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into LRBOMItem values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update LRBOMItem set Name=?,BOMName=?,CNName=?,Connector=?,IsHierarchical=?,IsBase=?,SourceType=?,Source=?,CommandType=?,PreCheck=?,Valid=?,Description=? where Name=? and BOMName=?";

	protected static final String _DeleteSQL = "delete from LRBOMItem  where Name=? and BOMName=?";

	protected static final String _FillAllSQL = "select * from LRBOMItem  where Name=? and BOMName=?";

	public LRBOMItemSchema(){
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
		return new LRBOMItemSchema();
	}

	protected SchemaSet newSet(){
		return new LRBOMItemSet();
	}

	public LRBOMItemSet query() {
		return query(null, -1, -1);
	}

	public LRBOMItemSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public LRBOMItemSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public LRBOMItemSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (LRBOMItemSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Name = (String)v;return;}
		if (i == 1){BOMName = (String)v;return;}
		if (i == 2){CNName = (String)v;return;}
		if (i == 3){Connector = (String)v;return;}
		if (i == 4){IsHierarchical = (String)v;return;}
		if (i == 5){IsBase = (String)v;return;}
		if (i == 6){SourceType = (String)v;return;}
		if (i == 7){Source = (String)v;return;}
		if (i == 8){CommandType = (String)v;return;}
		if (i == 9){PreCheck = (String)v;return;}
		if (i == 10){Valid = (String)v;return;}
		if (i == 11){Description = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Name;}
		if (i == 1){return BOMName;}
		if (i == 2){return CNName;}
		if (i == 3){return Connector;}
		if (i == 4){return IsHierarchical;}
		if (i == 5){return IsBase;}
		if (i == 6){return SourceType;}
		if (i == 7){return Source;}
		if (i == 8){return CommandType;}
		if (i == 9){return PreCheck;}
		if (i == 10){return Valid;}
		if (i == 11){return Description;}
		return null;
	}

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :词条英文名<br>
	* 数据类型 :varchar2(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :词条英文名<br>
	* 数据类型 :varchar2(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段BOMName的值，该字段的<br>
	* 字段名称 :BOM<br>
	* 数据类型 :varchar2(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getBOMName() {
		return BOMName;
	}

	/**
	* 设置字段BOMName的值，该字段的<br>
	* 字段名称 :BOM<br>
	* 数据类型 :varchar2(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBOMName(String bOMName) {
		this.BOMName = bOMName;
    }

	/**
	* 获取字段CNName的值，该字段的<br>
	* 字段名称 :词条中文名<br>
	* 数据类型 :varchar2(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCNName() {
		return CNName;
	}

	/**
	* 设置字段CNName的值，该字段的<br>
	* 字段名称 :词条中文名<br>
	* 数据类型 :varchar2(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCNName(String cNName) {
		this.CNName = cNName;
    }

	/**
	* 获取字段Connector的值，该字段的<br>
	* 字段名称 :连接词<br>
	* 数据类型 :VARCHAR2(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getConnector() {
		return Connector;
	}

	/**
	* 设置字段Connector的值，该字段的<br>
	* 字段名称 :连接词<br>
	* 数据类型 :VARCHAR2(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setConnector(String connector) {
		this.Connector = connector;
    }

	/**
	* 获取字段IsHierarchical的值，该字段的<br>
	* 字段名称 :是否层次型数据<br>
	* 数据类型 :VARCHAR2(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0----否，正常数据结构 1----是，层次型数据结构<br>
	*/
	public String getIsHierarchical() {
		return IsHierarchical;
	}

	/**
	* 设置字段IsHierarchical的值，该字段的<br>
	* 字段名称 :是否层次型数据<br>
	* 数据类型 :VARCHAR2(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0----否，正常数据结构 1----是，层次型数据结构<br>
	*/
	public void setIsHierarchical(String isHierarchical) {
		this.IsHierarchical = isHierarchical;
    }

	/**
	* 获取字段IsBase的值，该字段的<br>
	* 字段名称 :是否基础词条<br>
	* 数据类型 :VARCHAR2(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0———基础词条，基础数据，定义界面时需要设值 1——业务系统传入数据，定义界面不需要设值<br>
	*/
	public String getIsBase() {
		return IsBase;
	}

	/**
	* 设置字段IsBase的值，该字段的<br>
	* 字段名称 :是否基础词条<br>
	* 数据类型 :VARCHAR2(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0———基础词条，基础数据，定义界面时需要设值 1——业务系统传入数据，定义界面不需要设值<br>
	*/
	public void setIsBase(String isBase) {
		this.IsBase = isBase;
    }

	/**
	* 获取字段SourceType的值，该字段的<br>
	* 字段名称 :基础数据取值类型<br>
	* 数据类型 :VARCHAR2(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	SQL 或者 java<br>
	*/
	public String getSourceType() {
		return SourceType;
	}

	/**
	* 设置字段SourceType的值，该字段的<br>
	* 字段名称 :基础数据取值类型<br>
	* 数据类型 :VARCHAR2(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	SQL 或者 java<br>
	*/
	public void setSourceType(String sourceType) {
		this.SourceType = sourceType;
    }

	/**
	* 获取字段Source的值，该字段的<br>
	* 字段名称 :基础数据取值来源<br>
	* 数据类型 :VARCHAR2(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	SQL或者java<br>
	*/
	public String getSource() {
		return Source;
	}

	/**
	* 设置字段Source的值，该字段的<br>
	* 字段名称 :基础数据取值来源<br>
	* 数据类型 :VARCHAR2(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	SQL或者java<br>
	*/
	public void setSource(String source) {
		this.Source = source;
    }

	/**
	* 获取字段CommandType的值，该字段的<br>
	* 字段名称 :词条数据类型<br>
	* 数据类型 :varchar2(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	String-字符型 Number-数字型 Date-日期型 yyyy-mm-dd Time-时间型 HH24:MI:SS Boolean-布尔值：1代表真；0代表假<br>
	*/
	public String getCommandType() {
		return CommandType;
	}

	/**
	* 设置字段CommandType的值，该字段的<br>
	* 字段名称 :词条数据类型<br>
	* 数据类型 :varchar2(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	String-字符型 Number-数字型 Date-日期型 yyyy-mm-dd Time-时间型 HH24:MI:SS Boolean-布尔值：1代表真；0代表假<br>
	*/
	public void setCommandType(String commandType) {
		this.CommandType = commandType;
    }

	/**
	* 获取字段PreCheck的值，该字段的<br>
	* 字段名称 :词条预校验<br>
	* 数据类型 :VARCHAR2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	生成的java对象中，对对象初始值的预校验<br>
	*/
	public String getPreCheck() {
		return PreCheck;
	}

	/**
	* 设置字段PreCheck的值，该字段的<br>
	* 字段名称 :词条预校验<br>
	* 数据类型 :VARCHAR2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	生成的java对象中，对对象初始值的预校验<br>
	*/
	public void setPreCheck(String preCheck) {
		this.PreCheck = preCheck;
    }

	/**
	* 获取字段Valid的值，该字段的<br>
	* 字段名称 :有效性<br>
	* 数据类型 :VARCHAR2(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getValid() {
		return Valid;
	}

	/**
	* 设置字段Valid的值，该字段的<br>
	* 字段名称 :有效性<br>
	* 数据类型 :VARCHAR2(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setValid(String valid) {
		this.Valid = valid;
    }

	/**
	* 获取字段Description的值，该字段的<br>
	* 字段名称 :词条描述<br>
	* 数据类型 :varchar2(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDescription() {
		return Description;
	}

	/**
	* 设置字段Description的值，该字段的<br>
	* 字段名称 :词条描述<br>
	* 数据类型 :varchar2(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDescription(String description) {
		this.Description = description;
    }

}