package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.math.BigDecimal;

import java.util.Date;

import java.math.BigDecimal;

import java.util.Date;

/**
 * 表名称：微信转盘游戏产品表<br>
 * 表代码：Activityproduct<br>
 * 表主键：id<br>
 */
public class ActivityproductSchema extends Schema {
	private String id;

	private String activityId;

	private String productImage;

	private String productUrl;

	private String adduser;

	private Date createdate;

	private String modifyuser;

	private Date modifydate;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("activityId", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("productImage", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("productUrl", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("adduser", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("createdate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("modifyuser", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("modifydate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 11, 100 , 0 , false , false)
	};

	public static final String _TableCode = "Activityproduct";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into Activityproduct values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update Activityproduct set id=?,activityId=?,productImage=?,productUrl=?,adduser=?,createdate=?,modifyuser=?,modifydate=?,prop1=?,prop2=?,prop3=?,prop4=? where id=?";

	protected static final String _DeleteSQL = "delete from Activityproduct  where id=?";

	protected static final String _FillAllSQL = "select * from Activityproduct  where id=?";

	public ActivityproductSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new ActivityproductSchema();
	}

	protected SchemaSet newSet(){
		return new ActivityproductSet();
	}

	public ActivityproductSet query() {
		return query(null, -1, -1);
	}

	public ActivityproductSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ActivityproductSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ActivityproductSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ActivityproductSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){activityId = (String)v;return;}
		if (i == 2){productImage = (String)v;return;}
		if (i == 3){productUrl = (String)v;return;}
		if (i == 4){adduser = (String)v;return;}
		if (i == 5){createdate = (Date)v;return;}
		if (i == 6){modifyuser = (String)v;return;}
		if (i == 7){modifydate = (Date)v;return;}
		if (i == 8){prop1 = (String)v;return;}
		if (i == 9){prop2 = (String)v;return;}
		if (i == 10){prop3 = (String)v;return;}
		if (i == 11){prop4 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return activityId;}
		if (i == 2){return productImage;}
		if (i == 3){return productUrl;}
		if (i == 4){return adduser;}
		if (i == 5){return createdate;}
		if (i == 6){return modifyuser;}
		if (i == 7){return modifydate;}
		if (i == 8){return prop1;}
		if (i == 9){return prop2;}
		if (i == 10){return prop3;}
		if (i == 11){return prop4;}
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
	* 获取字段activityId的值，该字段的<br>
	* 字段名称 :活动id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getactivityId() {
		return activityId;
	}

	/**
	* 设置字段activityId的值，该字段的<br>
	* 字段名称 :活动id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setactivityId(String activityId) {
		this.activityId = activityId;
    }

	/**
	* 获取字段productImage的值，该字段的<br>
	* 字段名称 :产品图片<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductImage() {
		return productImage;
	}

	/**
	* 设置字段productImage的值，该字段的<br>
	* 字段名称 :产品图片<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductImage(String productImage) {
		this.productImage = productImage;
    }

	/**
	* 获取字段productUrl的值，该字段的<br>
	* 字段名称 :产品链接<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductUrl() {
		return productUrl;
	}

	/**
	* 设置字段productUrl的值，该字段的<br>
	* 字段名称 :产品链接<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductUrl(String productUrl) {
		this.productUrl = productUrl;
    }

	/**
	* 获取字段adduser的值，该字段的<br>
	* 字段名称 :adduser<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getadduser() {
		return adduser;
	}

	/**
	* 设置字段adduser的值，该字段的<br>
	* 字段名称 :adduser<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setadduser(String adduser) {
		this.adduser = adduser;
    }

	/**
	* 获取字段createdate的值，该字段的<br>
	* 字段名称 :createdate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreatedate() {
		return createdate;
	}

	/**
	* 设置字段createdate的值，该字段的<br>
	* 字段名称 :createdate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreatedate(Date createdate) {
		this.createdate = createdate;
    }

	/**
	* 获取字段modifyuser的值，该字段的<br>
	* 字段名称 :modifyuser<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyuser() {
		return modifyuser;
	}

	/**
	* 设置字段modifyuser的值，该字段的<br>
	* 字段名称 :modifyuser<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
    }

	/**
	* 获取字段modifydate的值，该字段的<br>
	* 字段名称 :modifydate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifydate() {
		return modifydate;
	}

	/**
	* 设置字段modifydate的值，该字段的<br>
	* 字段名称 :modifydate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifydate(Date modifydate) {
		this.modifydate = modifydate;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop4(String prop4) {
		this.prop4 = prop4;
    }

}