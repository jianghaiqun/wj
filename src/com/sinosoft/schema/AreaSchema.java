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
 * 表名称：销售区域表<br>
 * 表代码：Area<br>
 * 表主键：Id<br>
 */
public class AreaSchema extends Schema {
	private String Id;

	private Date createDate;

	private Date modifyDate;

	private String name;

	private String path;

	private String parent_id;

	private String insuranceCompany;

	private String areaValue;

	private String productId;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , true , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("name", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("path", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("parent_id", DataColumn.STRING, 5, 32 , 0 , false , false),
		new SchemaColumn("insuranceCompany", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("areaValue", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("productId", DataColumn.STRING, 8, 255 , 0 , false , false)
	};

	public static final String _TableCode = "Area";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into Area values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update Area set Id=?,createDate=?,modifyDate=?,name=?,path=?,parent_id=?,insuranceCompany=?,areaValue=?,productId=? where Id=?";

	protected static final String _DeleteSQL = "delete from Area  where Id=?";

	protected static final String _FillAllSQL = "select * from Area  where Id=?";

	public AreaSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[9];
	}

	protected Schema newInstance(){
		return new AreaSchema();
	}

	protected SchemaSet newSet(){
		return new AreaSet();
	}

	public AreaSet query() {
		return query(null, -1, -1);
	}

	public AreaSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public AreaSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public AreaSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (AreaSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){name = (String)v;return;}
		if (i == 4){path = (String)v;return;}
		if (i == 5){parent_id = (String)v;return;}
		if (i == 6){insuranceCompany = (String)v;return;}
		if (i == 7){areaValue = (String)v;return;}
		if (i == 8){productId = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return name;}
		if (i == 4){return path;}
		if (i == 5){return parent_id;}
		if (i == 6){return insuranceCompany;}
		if (i == 7){return areaValue;}
		if (i == 8){return productId;}
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
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :createDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :createDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :modifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :modifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段name的值，该字段的<br>
	* 字段名称 :name<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getname() {
		return name;
	}

	/**
	* 设置字段name的值，该字段的<br>
	* 字段名称 :name<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setname(String name) {
		this.name = name;
    }

	/**
	* 获取字段path的值，该字段的<br>
	* 字段名称 :path<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpath() {
		return path;
	}

	/**
	* 设置字段path的值，该字段的<br>
	* 字段名称 :path<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpath(String path) {
		this.path = path;
    }

	/**
	* 获取字段parent_id的值，该字段的<br>
	* 字段名称 :parent_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getparent_id() {
		return parent_id;
	}

	/**
	* 设置字段parent_id的值，该字段的<br>
	* 字段名称 :parent_id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setparent_id(String parent_id) {
		this.parent_id = parent_id;
    }

	/**
	* 获取字段insuranceCompany的值，该字段的<br>
	* 字段名称 :insuranceCompany<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuranceCompany() {
		return insuranceCompany;
	}

	/**
	* 设置字段insuranceCompany的值，该字段的<br>
	* 字段名称 :insuranceCompany<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
    }

	/**
	* 获取字段areaValue的值，该字段的<br>
	* 字段名称 :areaValue<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getareaValue() {
		return areaValue;
	}

	/**
	* 设置字段areaValue的值，该字段的<br>
	* 字段名称 :areaValue<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setareaValue(String areaValue) {
		this.areaValue = areaValue;
    }

	/**
	* 获取字段productId的值，该字段的<br>
	* 字段名称 :productId<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductId() {
		return productId;
	}

	/**
	* 设置字段productId的值，该字段的<br>
	* 字段名称 :productId<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductId(String productId) {
		this.productId = productId;
    }

}