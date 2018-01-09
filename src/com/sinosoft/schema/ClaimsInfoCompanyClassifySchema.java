package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：理赔资料保险公司二级分类<br>
 * 表代码：ClaimsInfoCompanyClassify<br>
 * 表主键：id<br>
 */
public class ClaimsInfoCompanyClassifySchema extends Schema {
	private String id;

	private String name;

	private String comCode;

	private Long orderFlag;

	private String prop1;

	private String prop2;

	private String prop3;

	private String CreateUser;

	private Date CreateDate;

	private String ModifyUser;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("name", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("comCode", DataColumn.STRING, 2, 30 , 0 , false , false),
		new SchemaColumn("orderFlag", DataColumn.LONG, 3, 20 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 10, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ClaimsInfoCompanyClassify";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ClaimsInfoCompanyClassify values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ClaimsInfoCompanyClassify set id=?,name=?,comCode=?,orderFlag=?,prop1=?,prop2=?,prop3=?,CreateUser=?,CreateDate=?,ModifyUser=?,ModifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from ClaimsInfoCompanyClassify  where id=?";

	protected static final String _FillAllSQL = "select * from ClaimsInfoCompanyClassify  where id=?";

	public ClaimsInfoCompanyClassifySchema(){
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
		return new ClaimsInfoCompanyClassifySchema();
	}

	protected SchemaSet newSet(){
		return new ClaimsInfoCompanyClassifySet();
	}

	public ClaimsInfoCompanyClassifySet query() {
		return query(null, -1, -1);
	}

	public ClaimsInfoCompanyClassifySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ClaimsInfoCompanyClassifySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ClaimsInfoCompanyClassifySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ClaimsInfoCompanyClassifySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){name = (String)v;return;}
		if (i == 2){comCode = (String)v;return;}
		if (i == 3){if(v==null){orderFlag = null;}else{orderFlag = new Long(v.toString());}return;}
		if (i == 4){prop1 = (String)v;return;}
		if (i == 5){prop2 = (String)v;return;}
		if (i == 6){prop3 = (String)v;return;}
		if (i == 7){CreateUser = (String)v;return;}
		if (i == 8){CreateDate = (Date)v;return;}
		if (i == 9){ModifyUser = (String)v;return;}
		if (i == 10){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return name;}
		if (i == 2){return comCode;}
		if (i == 3){return orderFlag;}
		if (i == 4){return prop1;}
		if (i == 5){return prop2;}
		if (i == 6){return prop3;}
		if (i == 7){return CreateUser;}
		if (i == 8){return CreateDate;}
		if (i == 9){return ModifyUser;}
		if (i == 10){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段name的值，该字段的<br>
	* 字段名称 :分类名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getname() {
		return name;
	}

	/**
	* 设置字段name的值，该字段的<br>
	* 字段名称 :分类名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setname(String name) {
		this.name = name;
    }

	/**
	* 获取字段comCode的值，该字段的<br>
	* 字段名称 :所属保险公司<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcomCode() {
		return comCode;
	}

	/**
	* 设置字段comCode的值，该字段的<br>
	* 字段名称 :所属保险公司<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcomCode(String comCode) {
		this.comCode = comCode;
    }

	/**
	* 获取字段orderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getorderFlag() {
		if(orderFlag==null){return 0;}
		return orderFlag.longValue();
	}

	/**
	* 设置字段orderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderFlag(long orderFlag) {
		this.orderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段orderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderFlag(String orderFlag) {
		if (orderFlag == null){
			this.orderFlag = null;
			return;
		}
		this.orderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateUser(String createUser) {
		this.CreateUser = createUser;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}