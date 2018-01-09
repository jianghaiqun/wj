package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：PurchaseRelaInfo<br>
 * 表代码：PurchaseRelaInfo<br>
 * 表主键：id<br>
 */
public class PurchaseRelaInfoSchema extends Schema {
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

	private String Description;

	private String OrderFlag;

	private String DataNode;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("MakeDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("IsMustInput", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("InputType", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("InputName", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("InputCode", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("InfoType", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("IsModify", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("ValidateInfo", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("ElementID", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Description", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("DataNode", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 14, 50 , 0 , false , false)
	};

	public static final String _TableCode = "PurchaseRelaInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PurchaseRelaInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PurchaseRelaInfo set id=?,MakeDate=?,IsMustInput=?,InputType=?,InputName=?,InputCode=?,InfoType=?,IsModify=?,ValidateInfo=?,ElementID=?,Description=?,OrderFlag=?,DataNode=?,Prop1=?,Prop2=? where id=?";

	protected static final String _DeleteSQL = "delete from PurchaseRelaInfo  where id=?";

	protected static final String _FillAllSQL = "select * from PurchaseRelaInfo  where id=?";

	public PurchaseRelaInfoSchema(){
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
		return new PurchaseRelaInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PurchaseRelaInfoSet();
	}

	public PurchaseRelaInfoSet query() {
		return query(null, -1, -1);
	}

	public PurchaseRelaInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PurchaseRelaInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PurchaseRelaInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PurchaseRelaInfoSet)querySet(qb , pageSize , pageIndex);
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
		if (i == 10){Description = (String)v;return;}
		if (i == 11){OrderFlag = (String)v;return;}
		if (i == 12){DataNode = (String)v;return;}
		if (i == 13){Prop1 = (String)v;return;}
		if (i == 14){Prop2 = (String)v;return;}
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
		if (i == 10){return Description;}
		if (i == 11){return OrderFlag;}
		if (i == 12){return DataNode;}
		if (i == 13){return Prop1;}
		if (i == 14){return Prop2;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(20)<br>
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
	* 字段名称 :IsMustInput<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsMustInput() {
		return IsMustInput;
	}

	/**
	* 设置字段IsMustInput的值，该字段的<br>
	* 字段名称 :IsMustInput<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsMustInput(String isMustInput) {
		this.IsMustInput = isMustInput;
    }

	/**
	* 获取字段InputType的值，该字段的<br>
	* 字段名称 :InputType<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInputType() {
		return InputType;
	}

	/**
	* 设置字段InputType的值，该字段的<br>
	* 字段名称 :InputType<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInputType(String inputType) {
		this.InputType = inputType;
    }

	/**
	* 获取字段InputName的值，该字段的<br>
	* 字段名称 :InputName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInputName() {
		return InputName;
	}

	/**
	* 设置字段InputName的值，该字段的<br>
	* 字段名称 :InputName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInputName(String inputName) {
		this.InputName = inputName;
    }

	/**
	* 获取字段InputCode的值，该字段的<br>
	* 字段名称 :InputCode<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInputCode() {
		return InputCode;
	}

	/**
	* 设置字段InputCode的值，该字段的<br>
	* 字段名称 :InputCode<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInputCode(String inputCode) {
		this.InputCode = inputCode;
    }

	/**
	* 获取字段InfoType的值，该字段的<br>
	* 字段名称 :InfoType<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInfoType() {
		return InfoType;
	}

	/**
	* 设置字段InfoType的值，该字段的<br>
	* 字段名称 :InfoType<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInfoType(String infoType) {
		this.InfoType = infoType;
    }

	/**
	* 获取字段IsModify的值，该字段的<br>
	* 字段名称 :IsModify<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsModify() {
		return IsModify;
	}

	/**
	* 设置字段IsModify的值，该字段的<br>
	* 字段名称 :IsModify<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsModify(String isModify) {
		this.IsModify = isModify;
    }

	/**
	* 获取字段ValidateInfo的值，该字段的<br>
	* 字段名称 :ValidateInfo<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getValidateInfo() {
		return ValidateInfo;
	}

	/**
	* 设置字段ValidateInfo的值，该字段的<br>
	* 字段名称 :ValidateInfo<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setValidateInfo(String validateInfo) {
		this.ValidateInfo = validateInfo;
    }

	/**
	* 获取字段ElementID的值，该字段的<br>
	* 字段名称 :ElementID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getElementID() {
		return ElementID;
	}

	/**
	* 设置字段ElementID的值，该字段的<br>
	* 字段名称 :ElementID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setElementID(String elementID) {
		this.ElementID = elementID;
    }

	/**
	* 获取字段Description的值，该字段的<br>
	* 字段名称 :单位说明<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDescription() {
		return Description;
	}

	/**
	* 设置字段Description的值，该字段的<br>
	* 字段名称 :单位说明<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDescription(String description) {
		this.Description = description;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :元素排序<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderFlag() {
		return OrderFlag;
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :元素排序<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		this.OrderFlag = orderFlag;
    }

	/**
	* 获取字段DataNode的值，该字段的<br>
	* 字段名称 :数据节点<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDataNode() {
		return DataNode;
	}

	/**
	* 设置字段DataNode的值，该字段的<br>
	* 字段名称 :数据节点<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDataNode(String dataNode) {
		this.DataNode = dataNode;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

}