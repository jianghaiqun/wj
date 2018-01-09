package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：页面元素信息表<br>
 * 表代码：ModuleElementInfo<br>
 * 表主键：Id<br>
 */
public class ModuleElementInfoSchema extends Schema {
	private String Id;

	private String ElementName;

	private String ElementType;

	private String ElementContent;

	private String Memo;

	private Date CreateDate;

	private Date ModifyDate;

	private String Remark1;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("ElementName", DataColumn.STRING, 1, 32 , 0 , true , false),
		new SchemaColumn("ElementType", DataColumn.STRING, 2, 4 , 0 , true , false),
		new SchemaColumn("ElementContent", DataColumn.CLOB, 3, 0 , 0 , true , false),
		new SchemaColumn("Memo", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("Remark1", DataColumn.STRING, 7, 20 , 0 , false , false)
	};

	public static final String _TableCode = "ModuleElementInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ModuleElementInfo values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ModuleElementInfo set Id=?,ElementName=?,ElementType=?,ElementContent=?,Memo=?,CreateDate=?,ModifyDate=?,Remark1=? where Id=?";

	protected static final String _DeleteSQL = "delete from ModuleElementInfo  where Id=?";

	protected static final String _FillAllSQL = "select * from ModuleElementInfo  where Id=?";

	public ModuleElementInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[8];
	}

	protected Schema newInstance(){
		return new ModuleElementInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ModuleElementInfoSet();
	}

	public ModuleElementInfoSet query() {
		return query(null, -1, -1);
	}

	public ModuleElementInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ModuleElementInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ModuleElementInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ModuleElementInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ElementName = (String)v;return;}
		if (i == 2){ElementType = (String)v;return;}
		if (i == 3){ElementContent = (String)v;return;}
		if (i == 4){Memo = (String)v;return;}
		if (i == 5){CreateDate = (Date)v;return;}
		if (i == 6){ModifyDate = (Date)v;return;}
		if (i == 7){Remark1 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ElementName;}
		if (i == 2){return ElementType;}
		if (i == 3){return ElementContent;}
		if (i == 4){return Memo;}
		if (i == 5){return CreateDate;}
		if (i == 6){return ModifyDate;}
		if (i == 7){return Remark1;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段ElementName的值，该字段的<br>
	* 字段名称 :元素名称<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getElementName() {
		return ElementName;
	}

	/**
	* 设置字段ElementName的值，该字段的<br>
	* 字段名称 :元素名称<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setElementName(String elementName) {
		this.ElementName = elementName;
    }

	/**
	* 获取字段ElementType的值，该字段的<br>
	* 字段名称 :元素类型<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getElementType() {
		return ElementType;
	}

	/**
	* 设置字段ElementType的值，该字段的<br>
	* 字段名称 :元素类型<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setElementType(String elementType) {
		this.ElementType = elementType;
    }

	/**
	* 获取字段ElementContent的值，该字段的<br>
	* 字段名称 :元素内容<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getElementContent() {
		return ElementContent;
	}

	/**
	* 设置字段ElementContent的值，该字段的<br>
	* 字段名称 :元素内容<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setElementContent(String elementContent) {
		this.ElementContent = elementContent;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段Remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark1() {
		return Remark1;
	}

	/**
	* 设置字段Remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark1(String remark1) {
		this.Remark1 = remark1;
    }

}