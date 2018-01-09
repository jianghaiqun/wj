package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：产品图片信息<br>
 * 表代码：OtProductImageInfo<br>
 * 表主键：id<br>
 */
public class OtProductImageInfoSchema extends Schema {
	private String id;

	private String imagePath;

	private String imageName;

	private String productId;

	private String prop1;

	private String prop2;

	private String prop3;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("imagePath", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("imageName", DataColumn.STRING, 2, 30 , 0 , false , false),
		new SchemaColumn("productId", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 10, 0 , 0 , false , false)
	};

	public static final String _TableCode = "OtProductImageInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into OtProductImageInfo values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update OtProductImageInfo set id=?,imagePath=?,imageName=?,productId=?,prop1=?,prop2=?,prop3=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from OtProductImageInfo  where id=?";

	protected static final String _FillAllSQL = "select * from OtProductImageInfo  where id=?";

	public OtProductImageInfoSchema(){
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
		return new OtProductImageInfoSchema();
	}

	protected SchemaSet newSet(){
		return new OtProductImageInfoSet();
	}

	public OtProductImageInfoSet query() {
		return query(null, -1, -1);
	}

	public OtProductImageInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public OtProductImageInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public OtProductImageInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (OtProductImageInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){imagePath = (String)v;return;}
		if (i == 2){imageName = (String)v;return;}
		if (i == 3){productId = (String)v;return;}
		if (i == 4){prop1 = (String)v;return;}
		if (i == 5){prop2 = (String)v;return;}
		if (i == 6){prop3 = (String)v;return;}
		if (i == 7){createUser = (String)v;return;}
		if (i == 8){createDate = (Date)v;return;}
		if (i == 9){modifyUser = (String)v;return;}
		if (i == 10){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return imagePath;}
		if (i == 2){return imageName;}
		if (i == 3){return productId;}
		if (i == 4){return prop1;}
		if (i == 5){return prop2;}
		if (i == 6){return prop3;}
		if (i == 7){return createUser;}
		if (i == 8){return createDate;}
		if (i == 9){return modifyUser;}
		if (i == 10){return modifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :图片ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :图片ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段imagePath的值，该字段的<br>
	* 字段名称 :图片路径<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getimagePath() {
		return imagePath;
	}

	/**
	* 设置字段imagePath的值，该字段的<br>
	* 字段名称 :图片路径<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setimagePath(String imagePath) {
		this.imagePath = imagePath;
    }

	/**
	* 获取字段imageName的值，该字段的<br>
	* 字段名称 :图片名称<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getimageName() {
		return imageName;
	}

	/**
	* 设置字段imageName的值，该字段的<br>
	* 字段名称 :图片名称<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setimageName(String imageName) {
		this.imageName = imageName;
    }

	/**
	* 获取字段productId的值，该字段的<br>
	* 字段名称 :所属产品<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductId() {
		return productId;
	}

	/**
	* 设置字段productId的值，该字段的<br>
	* 字段名称 :所属产品<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductId(String productId) {
		this.productId = productId;
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
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

}