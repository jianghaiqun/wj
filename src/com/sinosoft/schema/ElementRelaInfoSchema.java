package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：ElementRelaOtherInfo<br>
 * 表代码：ElementRelaInfo<br>
 * 表主键：id<br>
 */
public class ElementRelaInfoSchema extends Schema {
	private String id;

	private Date MakeDate;

	private String IsMustInput;

	private String InputType;

	private String InputName;

	private String InputCode;

	private String InfoType;

	private String IsModify;

	private String ValidateInfo;

	private String ElementID;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("MakeDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("IsMustInput", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("InputType", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("InputName", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("InputCode", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("InfoType", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("IsModify", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("ValidateInfo", DataColumn.STRING, 8, 32 , 0 , false , false),
		new SchemaColumn("ElementID", DataColumn.STRING, 9, 32 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 10, 32 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 11, 32 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 12, 32 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 13, 32 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 14, 32 , 0 , false , false)
	};

	public static final String _TableCode = "ElementRelaInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ElementRelaInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ElementRelaInfo set id=?,MakeDate=?,IsMustInput=?,InputType=?,InputName=?,InputCode=?,InfoType=?,IsModify=?,ValidateInfo=?,ElementID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=? where id=?";

	protected static final String _DeleteSQL = "delete from ElementRelaInfo  where id=?";

	protected static final String _FillAllSQL = "select * from ElementRelaInfo  where id=?";

	public ElementRelaInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[15];
	}

	protected Schema newInstance(){
		return new ElementRelaInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ElementRelaInfoSet();
	}

	public ElementRelaInfoSet query() {
		return query(null, -1, -1);
	}

	public ElementRelaInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ElementRelaInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ElementRelaInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ElementRelaInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){MakeDate = (Date)v;return;}
		if (i == 2){IsMustInput = (String)v;return;}
		if (i == 3){InputType = (String)v;return;}
		if (i == 4){InputName = (String)v;return;}
		if (i == 5){InputCode = (String)v;return;}
		if (i == 6){InfoType = (String)v;return;}
		if (i == 7){IsModify = (String)v;return;}
		if (i == 8){ValidateInfo = (String)v;return;}
		if (i == 9){ElementID = (String)v;return;}
		if (i == 10){Prop1 = (String)v;return;}
		if (i == 11){Prop2 = (String)v;return;}
		if (i == 12){Prop3 = (String)v;return;}
		if (i == 13){Prop4 = (String)v;return;}
		if (i == 14){Prop5 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return MakeDate;}
		if (i == 2){return IsMustInput;}
		if (i == 3){return InputType;}
		if (i == 4){return InputName;}
		if (i == 5){return InputCode;}
		if (i == 6){return InfoType;}
		if (i == 7){return IsModify;}
		if (i == 8){return ValidateInfo;}
		if (i == 9){return ElementID;}
		if (i == 10){return Prop1;}
		if (i == 11){return Prop2;}
		if (i == 12){return Prop3;}
		if (i == 13){return Prop4;}
		if (i == 14){return Prop5;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段MakeDate的值，该字段的<br>
	* 字段名称 :MakeDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getMakeDate() {
		return MakeDate;
	}

	/**
	* 设置字段MakeDate的值，该字段的<br>
	* 字段名称 :MakeDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMakeDate(Date makeDate) {
		this.MakeDate = makeDate;
    }

	/**
	* 获取字段IsMustInput的值，该字段的<br>
	* 字段名称 :是否必录<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--必录 N--不是必录项<br>
	*/
	public String getIsMustInput() {
		return IsMustInput;
	}

	/**
	* 设置字段IsMustInput的值，该字段的<br>
	* 字段名称 :是否必录<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--必录 N--不是必录项<br>
	*/
	public void setIsMustInput(String isMustInput) {
		this.IsMustInput = isMustInput;
    }

	/**
	* 获取字段InputType的值，该字段的<br>
	* 字段名称 :输入项类型(归属)<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	input--输入框 radio--单选 checkbox--多选 select--下拉列表选择 text--文本域 readonlytext--只读文本域<br>
	*/
	public String getInputType() {
		return InputType;
	}

	/**
	* 设置字段InputType的值，该字段的<br>
	* 字段名称 :输入项类型(归属)<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	input--输入框 radio--单选 checkbox--多选 select--下拉列表选择 text--文本域 readonlytext--只读文本域<br>
	*/
	public void setInputType(String inputType) {
		this.InputType = inputType;
    }

	/**
	* 获取字段InputName的值，该字段的<br>
	* 字段名称 :输入项名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInputName() {
		return InputName;
	}

	/**
	* 设置字段InputName的值，该字段的<br>
	* 字段名称 :输入项名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInputName(String inputName) {
		this.InputName = inputName;
    }

	/**
	* 获取字段InputCode的值，该字段的<br>
	* 字段名称 :元素编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInputCode() {
		return InputCode;
	}

	/**
	* 设置字段InputCode的值，该字段的<br>
	* 字段名称 :元素编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInputCode(String inputCode) {
		this.InputCode = inputCode;
    }

	/**
	* 获取字段InfoType的值，该字段的<br>
	* 字段名称 :类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	wap app如果多种类型那么用“，”号隔开，如“wap,app”<br>
	*/
	public String getInfoType() {
		return InfoType;
	}

	/**
	* 设置字段InfoType的值，该字段的<br>
	* 字段名称 :类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	wap app如果多种类型那么用“，”号隔开，如“wap,app”<br>
	*/
	public void setInfoType(String infoType) {
		this.InfoType = infoType;
    }

	/**
	* 获取字段IsModify的值，该字段的<br>
	* 字段名称 :是否可编辑<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--可编辑 N--不可编辑<br>
	*/
	public String getIsModify() {
		return IsModify;
	}

	/**
	* 设置字段IsModify的值，该字段的<br>
	* 字段名称 :是否可编辑<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--可编辑 N--不可编辑<br>
	*/
	public void setIsModify(String isModify) {
		this.IsModify = isModify;
    }

	/**
	* 获取字段ValidateInfo的值，该字段的<br>
	* 字段名称 :校验信息<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getValidateInfo() {
		return ValidateInfo;
	}

	/**
	* 设置字段ValidateInfo的值，该字段的<br>
	* 字段名称 :校验信息<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setValidateInfo(String validateInfo) {
		this.ValidateInfo = validateInfo;
    }

	/**
	* 获取字段ElementID的值，该字段的<br>
	* 字段名称 :关联元素ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	关联元素ID<br>
	*/
	public String getElementID() {
		return ElementID;
	}

	/**
	* 设置字段ElementID的值，该字段的<br>
	* 字段名称 :关联元素ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	关联元素ID<br>
	*/
	public void setElementID(String elementID) {
		this.ElementID = elementID;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

}