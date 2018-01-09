package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：LRCommand<br>
 * 表代码：LRCommand<br>
 * 表主键：Name<br>
 */
public class LRCommandSchema extends Schema {
	private String Name;

	private String Display;

	private String Implenmation;

	private String Valid;

	private String CommandType;

	private Number ParaNum;

	private String ParaType;

	private String ResultType;

	private String Description;

	private String CommType;

	private String CommDetail;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Name", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("Display", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Implenmation", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Valid", DataColumn.STRING, 3, 1 , 0 , true , false),
		new SchemaColumn("CommandType", DataColumn.STRING, 4, 10 , 0 , true , false),
		new SchemaColumn("ParaNum", DataColumn.INTEGER, 5, 0 , 0 , true , false),
		new SchemaColumn("ParaType", DataColumn.STRING, 6, 10 , 0 , true , false),
		new SchemaColumn("ResultType", DataColumn.STRING, 7, 10 , 0 , true , false),
		new SchemaColumn("Description", DataColumn.STRING, 8, 300 , 0 , false , false),
		new SchemaColumn("CommType", DataColumn.STRING, 9, 1 , 0 , false , false),
		new SchemaColumn("CommDetail", DataColumn.STRING, 10, 300 , 0 , false , false)
	};

	public static final String _TableCode = "LRCommand";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into LRCommand values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update LRCommand set Name=?,Display=?,Implenmation=?,Valid=?,CommandType=?,ParaNum=?,ParaType=?,ResultType=?,Description=?,CommType=?,CommDetail=? where Name=?";

	protected static final String _DeleteSQL = "delete from LRCommand  where Name=?";

	protected static final String _FillAllSQL = "select * from LRCommand  where Name=?";

	public LRCommandSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[11];
	}

	protected Schema newInstance(){
		return new LRCommandSchema();
	}

	protected SchemaSet newSet(){
		return new LRCommandSet();
	}

	public LRCommandSet query() {
		return query(null, -1, -1);
	}

	public LRCommandSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public LRCommandSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public LRCommandSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (LRCommandSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Name = (String)v;return;}
		if (i == 1){Display = (String)v;return;}
		if (i == 2){Implenmation = (String)v;return;}
		if (i == 3){Valid = (String)v;return;}
		if (i == 4){CommandType = (String)v;return;}
		if (i == 5){ParaNum = (Number)v;return;}
		if (i == 6){ParaType = (String)v;return;}
		if (i == 7){ResultType = (String)v;return;}
		if (i == 8){Description = (String)v;return;}
		if (i == 9){CommType = (String)v;return;}
		if (i == 10){CommDetail = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Name;}
		if (i == 1){return Display;}
		if (i == 2){return Implenmation;}
		if (i == 3){return Valid;}
		if (i == 4){return CommandType;}
		if (i == 5){return ParaNum;}
		if (i == 6){return ParaType;}
		if (i == 7){return ResultType;}
		if (i == 8){return Description;}
		if (i == 9){return CommType;}
		if (i == 10){return CommDetail;}
		return null;
	}

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :符号的名称<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :符号的名称<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段Display的值，该字段的<br>
	* 字段名称 :符号的界面展现<br>
	* 数据类型 :varchar2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	符号在界面上的展示样式<br>
	*/
	public String getDisplay() {
		return Display;
	}

	/**
	* 设置字段Display的值，该字段的<br>
	* 字段名称 :符号的界面展现<br>
	* 数据类型 :varchar2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	符号在界面上的展示样式<br>
	*/
	public void setDisplay(String display) {
		this.Display = display;
    }

	/**
	* 获取字段Implenmation的值，该字段的<br>
	* 字段名称 :技术实现<br>
	* 数据类型 :varchar2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	生成SQL时符号的运算法则<br>
	*/
	public String getImplenmation() {
		return Implenmation;
	}

	/**
	* 设置字段Implenmation的值，该字段的<br>
	* 字段名称 :技术实现<br>
	* 数据类型 :varchar2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	生成SQL时符号的运算法则<br>
	*/
	public void setImplenmation(String implenmation) {
		this.Implenmation = implenmation;
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
	* 获取字段CommandType的值，该字段的<br>
	* 字段名称 :运算数据类型<br>
	* 数据类型 :varchar2(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	String-字符型 Number-数字型 Date-日期型 Time-时间型 HH24:MI:SS<br>
	*/
	public String getCommandType() {
		return CommandType;
	}

	/**
	* 设置字段CommandType的值，该字段的<br>
	* 字段名称 :运算数据类型<br>
	* 数据类型 :varchar2(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	String-字符型 Number-数字型 Date-日期型 Time-时间型 HH24:MI:SS<br>
	*/
	public void setCommandType(String commandType) {
		this.CommandType = commandType;
    }

	/**
	* 获取字段ParaNum的值，该字段的<br>
	* 字段名称 :参数个数<br>
	* 数据类型 :Number<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Number getParaNum() {
		return ParaNum;
	}

	/**
	* 设置字段ParaNum的值，该字段的<br>
	* 字段名称 :参数个数<br>
	* 数据类型 :Number<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParaNum(Number paraNum) {
		this.ParaNum = paraNum;
    }

	/**
	* 获取字段ParaType的值，该字段的<br>
	* 字段名称 :参数类型<br>
	* 数据类型 :varchar2(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getParaType() {
		return ParaType;
	}

	/**
	* 设置字段ParaType的值，该字段的<br>
	* 字段名称 :参数类型<br>
	* 数据类型 :varchar2(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParaType(String paraType) {
		this.ParaType = paraType;
    }

	/**
	* 获取字段ResultType的值，该字段的<br>
	* 字段名称 :运算结果类型<br>
	* 数据类型 :varchar2(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	String-字符型 Number-数字型 Date-日期型 Time-时间型 HH24:MI:SS Boolean-逻辑值<br>
	*/
	public String getResultType() {
		return ResultType;
	}

	/**
	* 设置字段ResultType的值，该字段的<br>
	* 字段名称 :运算结果类型<br>
	* 数据类型 :varchar2(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	String-字符型 Number-数字型 Date-日期型 Time-时间型 HH24:MI:SS Boolean-逻辑值<br>
	*/
	public void setResultType(String resultType) {
		this.ResultType = resultType;
    }

	/**
	* 获取字段Description的值，该字段的<br>
	* 字段名称 :符号的描述信息<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDescription() {
		return Description;
	}

	/**
	* 设置字段Description的值，该字段的<br>
	* 字段名称 :符号的描述信息<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDescription(String description) {
		this.Description = description;
    }

	/**
	* 获取字段CommType的值，该字段的<br>
	* 字段名称 :函数类型<br>
	* 数据类型 :CHAR(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCommType() {
		return CommType;
	}

	/**
	* 设置字段CommType的值，该字段的<br>
	* 字段名称 :函数类型<br>
	* 数据类型 :CHAR(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommType(String commType) {
		this.CommType = commType;
    }

	/**
	* 获取字段CommDetail的值，该字段的<br>
	* 字段名称 :函数参数明细<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCommDetail() {
		return CommDetail;
	}

	/**
	* 设置字段CommDetail的值，该字段的<br>
	* 字段名称 :函数参数明细<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommDetail(String commDetail) {
		this.CommDetail = commDetail;
    }

}