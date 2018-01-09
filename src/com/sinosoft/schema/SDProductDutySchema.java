package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：SDProductDuty<br>
 * 表代码：SDProductDuty<br>
 * 表主键：ProductID, DutyCode<br>
 */
public class SDProductDutySchema extends Schema {
	private String ProductID;

	private String DutyCode;

	private String DutyName;

	private String DutyHtmlWap;

	private String OrderBy;

	private Date CreateDate;

	private Date ModifyDate;

	private String Operater;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ProductID", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("DutyCode", DataColumn.STRING, 1, 20 , 0 , true , true),
		new SchemaColumn("DutyName", DataColumn.STRING, 2, 10000 , 0 , false , false),
		new SchemaColumn("DutyHtmlWap", DataColumn.STRING, 3, 10000 , 0 , false , false),
		new SchemaColumn("OrderBy", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("Operater", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 8, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 9, 200 , 0 , false , false)
	};

	public static final String _TableCode = "SDProductDuty";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDProductDuty values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDProductDuty set ProductID=?,DutyCode=?,DutyName=?,DutyHtmlWap=?,OrderBy=?,CreateDate=?,ModifyDate=?,Operater=?,Prop1=?,Prop2=? where ProductID=? and DutyCode=?";

	protected static final String _DeleteSQL = "delete from SDProductDuty  where ProductID=? and DutyCode=?";

	protected static final String _FillAllSQL = "select * from SDProductDuty  where ProductID=? and DutyCode=?";

	public SDProductDutySchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[10];
	}

	protected Schema newInstance(){
		return new SDProductDutySchema();
	}

	protected SchemaSet newSet(){
		return new SDProductDutySet();
	}

	public SDProductDutySet query() {
		return query(null, -1, -1);
	}

	public SDProductDutySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDProductDutySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDProductDutySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDProductDutySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ProductID = (String)v;return;}
		if (i == 1){DutyCode = (String)v;return;}
		if (i == 2){DutyName = (String)v;return;}
		if (i == 3){DutyHtmlWap = (String)v;return;}
		if (i == 4){OrderBy = (String)v;return;}
		if (i == 5){CreateDate = (Date)v;return;}
		if (i == 6){ModifyDate = (Date)v;return;}
		if (i == 7){Operater = (String)v;return;}
		if (i == 8){Prop1 = (String)v;return;}
		if (i == 9){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ProductID;}
		if (i == 1){return DutyCode;}
		if (i == 2){return DutyName;}
		if (i == 3){return DutyHtmlWap;}
		if (i == 4){return OrderBy;}
		if (i == 5){return CreateDate;}
		if (i == 6){return ModifyDate;}
		if (i == 7){return Operater;}
		if (i == 8){return Prop1;}
		if (i == 9){return Prop2;}
		return null;
	}

	/**
	* 获取字段ProductID的值，该字段的<br>
	* 字段名称 :ProductID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getProductID() {
		return ProductID;
	}

	/**
	* 设置字段ProductID的值，该字段的<br>
	* 字段名称 :ProductID<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setProductID(String productID) {
		this.ProductID = productID;
    }

	/**
	* 获取字段DutyCode的值，该字段的<br>
	* 字段名称 :DutyCode<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getDutyCode() {
		return DutyCode;
	}

	/**
	* 设置字段DutyCode的值，该字段的<br>
	* 字段名称 :DutyCode<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setDutyCode(String dutyCode) {
		this.DutyCode = dutyCode;
    }

	/**
	* 获取字段DutyName的值，该字段的<br>
	* 字段名称 :DutyName<br>
	* 数据类型 :VARCHAR(10000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyName() {
		return DutyName;
	}

	/**
	* 设置字段DutyName的值，该字段的<br>
	* 字段名称 :DutyName<br>
	* 数据类型 :VARCHAR(10000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyName(String dutyName) {
		this.DutyName = dutyName;
    }

	/**
	* 获取字段DutyHtmlWap的值，该字段的<br>
	* 字段名称 :Wap责任保障单条HTML<br>
	* 数据类型 :VARCHAR(10000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDutyHtmlWap() {
		return DutyHtmlWap;
	}

	/**
	* 设置字段DutyHtmlWap的值，该字段的<br>
	* 字段名称 :Wap责任保障单条HTML<br>
	* 数据类型 :VARCHAR(10000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDutyHtmlWap(String dutyHtmlWap) {
		this.DutyHtmlWap = dutyHtmlWap;
    }

	/**
	* 获取字段OrderBy的值，该字段的<br>
	* 字段名称 :OrderBy<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderBy() {
		return OrderBy;
	}

	/**
	* 设置字段OrderBy的值，该字段的<br>
	* 字段名称 :OrderBy<br>
	* 数据类型 :VARCHAR(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderBy(String orderBy) {
		this.OrderBy = orderBy;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :DATETIME<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

	/**
	* 获取字段Operater的值，该字段的<br>
	* 字段名称 :Operater<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOperater() {
		return Operater;
	}

	/**
	* 设置字段Operater的值，该字段的<br>
	* 字段名称 :Operater<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperater(String operater) {
		this.Operater = operater;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

}