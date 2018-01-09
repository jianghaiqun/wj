package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：筛选地址关联表<br>
 * 表代码：SDSearchAddress<br>
 * 表主键：ID<br>
 */
public class SDSearchAddressSchema extends Schema {
	private String ID;

	private String AddressCode;

	private String ERiskType;

	private String SearchID;

	private String SearchAddress;

	private String Prop1;

	private String Prop2;

	private Date CreateDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("AddressCode", DataColumn.STRING, 1, 500 , 0 , false , false),
		new SchemaColumn("ERiskType", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("SearchID", DataColumn.STRING, 3, 500 , 0 , false , false),
		new SchemaColumn("SearchAddress", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 7, 0 , 0 , true , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 8, 0 , 0 , false , false)
	};

	public static final String _TableCode = "SDSearchAddress";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDSearchAddress values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDSearchAddress set ID=?,AddressCode=?,ERiskType=?,SearchID=?,SearchAddress=?,Prop1=?,Prop2=?,CreateDate=?,ModifyDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from SDSearchAddress  where ID=?";

	protected static final String _FillAllSQL = "select * from SDSearchAddress  where ID=?";

	public SDSearchAddressSchema(){
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
		return new SDSearchAddressSchema();
	}

	protected SchemaSet newSet(){
		return new SDSearchAddressSet();
	}

	public SDSearchAddressSet query() {
		return query(null, -1, -1);
	}

	public SDSearchAddressSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDSearchAddressSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDSearchAddressSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDSearchAddressSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){AddressCode = (String)v;return;}
		if (i == 2){ERiskType = (String)v;return;}
		if (i == 3){SearchID = (String)v;return;}
		if (i == 4){SearchAddress = (String)v;return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){Prop2 = (String)v;return;}
		if (i == 7){CreateDate = (Date)v;return;}
		if (i == 8){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return AddressCode;}
		if (i == 2){return ERiskType;}
		if (i == 3){return SearchID;}
		if (i == 4){return SearchAddress;}
		if (i == 5){return Prop1;}
		if (i == 6){return Prop2;}
		if (i == 7){return CreateDate;}
		if (i == 8){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段AddressCode的值，该字段的<br>
	* 字段名称 :地址编码<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddressCode() {
		return AddressCode;
	}

	/**
	* 设置字段AddressCode的值，该字段的<br>
	* 字段名称 :地址编码<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddressCode(String addressCode) {
		this.AddressCode = addressCode;
    }

	/**
	* 获取字段ERiskType的值，该字段的<br>
	* 字段名称 :险种类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getERiskType() {
		return ERiskType;
	}

	/**
	* 设置字段ERiskType的值，该字段的<br>
	* 字段名称 :险种类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setERiskType(String eRiskType) {
		this.ERiskType = eRiskType;
    }

	/**
	* 获取字段SearchID的值，该字段的<br>
	* 字段名称 :筛选编码<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSearchID() {
		return SearchID;
	}

	/**
	* 设置字段SearchID的值，该字段的<br>
	* 字段名称 :筛选编码<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearchID(String searchID) {
		this.SearchID = searchID;
    }

	/**
	* 获取字段SearchAddress的值，该字段的<br>
	* 字段名称 :筛选地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSearchAddress() {
		return SearchAddress;
	}

	/**
	* 设置字段SearchAddress的值，该字段的<br>
	* 字段名称 :筛选地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearchAddress(String searchAddress) {
		this.SearchAddress = searchAddress;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}