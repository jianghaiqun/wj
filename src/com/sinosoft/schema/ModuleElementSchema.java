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

/**
 * 表名称：模块元素中间信息表<br>
 * 表代码：ModuleElement<br>
 * 表主键：Id<br>
 */
public class ModuleElementSchema extends Schema {
	private String Id;

	private String ModuleCode;

	private String ElementCode;

	private Integer OrderFlag;

	private Date OperateDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("ModuleCode", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("ElementCode", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.INTEGER, 3, 0 , 0 , false , false),
		new SchemaColumn("OperateDate", DataColumn.DATETIME, 4, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ModuleElement";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ModuleElement values(?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ModuleElement set Id=?,ModuleCode=?,ElementCode=?,OrderFlag=?,OperateDate=? where Id=?";

	protected static final String _DeleteSQL = "delete from ModuleElement  where Id=?";

	protected static final String _FillAllSQL = "select * from ModuleElement  where Id=?";

	public ModuleElementSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[5];
	}

	protected Schema newInstance(){
		return new ModuleElementSchema();
	}

	protected SchemaSet newSet(){
		return new ModuleElementSet();
	}

	public ModuleElementSet query() {
		return query(null, -1, -1);
	}

	public ModuleElementSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ModuleElementSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ModuleElementSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ModuleElementSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ModuleCode = (String)v;return;}
		if (i == 2){ElementCode = (String)v;return;}
		if (i == 3){if(v==null){OrderFlag = null;}else{OrderFlag = new Integer(v.toString());}return;}
		if (i == 4){OperateDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ModuleCode;}
		if (i == 2){return ElementCode;}
		if (i == 3){return OrderFlag;}
		if (i == 4){return OperateDate;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段ModuleCode的值，该字段的<br>
	* 字段名称 :模块编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModuleCode() {
		return ModuleCode;
	}

	/**
	* 设置字段ModuleCode的值，该字段的<br>
	* 字段名称 :模块编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModuleCode(String moduleCode) {
		this.ModuleCode = moduleCode;
    }

	/**
	* 获取字段ElementCode的值，该字段的<br>
	* 字段名称 :元素编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getElementCode() {
		return ElementCode;
	}

	/**
	* 设置字段ElementCode的值，该字段的<br>
	* 字段名称 :元素编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setElementCode(String elementCode) {
		this.ElementCode = elementCode;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序标记<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.intValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序标记<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(int orderFlag) {
		this.OrderFlag = new Integer(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序标记<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Integer(orderFlag);
    }

	/**
	* 获取字段OperateDate的值，该字段的<br>
	* 字段名称 :操作日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getOperateDate() {
		return OperateDate;
	}

	/**
	* 设置字段OperateDate的值，该字段的<br>
	* 字段名称 :操作日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperateDate(Date operateDate) {
		this.OperateDate = operateDate;
    }

}