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

/**
 * 表名称：产品行程价格信息表<br>
 * 表代码：OtProductPrice<br>
 * 表主键：id<br>
 */
public class OtProductPriceSchema extends Schema {
	private String id;

	private Date travelDate;

	private String price;

	private Integer storeNum;

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
		new SchemaColumn("travelDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("price", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("storeNum", DataColumn.INTEGER, 3, 11 , 0 , false , false),
		new SchemaColumn("productId", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 11, 0 , 0 , false , false)
	};

	public static final String _TableCode = "OtProductPrice";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into OtProductPrice values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update OtProductPrice set id=?,travelDate=?,price=?,storeNum=?,productId=?,prop1=?,prop2=?,prop3=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from OtProductPrice  where id=?";

	protected static final String _FillAllSQL = "select * from OtProductPrice  where id=?";

	public OtProductPriceSchema(){
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
		return new OtProductPriceSchema();
	}

	protected SchemaSet newSet(){
		return new OtProductPriceSet();
	}

	public OtProductPriceSet query() {
		return query(null, -1, -1);
	}

	public OtProductPriceSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public OtProductPriceSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public OtProductPriceSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (OtProductPriceSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){travelDate = (Date)v;return;}
		if (i == 2){price = (String)v;return;}
		if (i == 3){if(v==null){storeNum = null;}else{storeNum = new Integer(v.toString());}return;}
		if (i == 4){productId = (String)v;return;}
		if (i == 5){prop1 = (String)v;return;}
		if (i == 6){prop2 = (String)v;return;}
		if (i == 7){prop3 = (String)v;return;}
		if (i == 8){createUser = (String)v;return;}
		if (i == 9){createDate = (Date)v;return;}
		if (i == 10){modifyUser = (String)v;return;}
		if (i == 11){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return travelDate;}
		if (i == 2){return price;}
		if (i == 3){return storeNum;}
		if (i == 4){return productId;}
		if (i == 5){return prop1;}
		if (i == 6){return prop2;}
		if (i == 7){return prop3;}
		if (i == 8){return createUser;}
		if (i == 9){return createDate;}
		if (i == 10){return modifyUser;}
		if (i == 11){return modifyDate;}
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
	* 获取字段travelDate的值，该字段的<br>
	* 字段名称 :出发日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date gettravelDate() {
		return travelDate;
	}

	/**
	* 设置字段travelDate的值，该字段的<br>
	* 字段名称 :出发日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settravelDate(Date travelDate) {
		this.travelDate = travelDate;
    }

	/**
	* 获取字段price的值，该字段的<br>
	* 字段名称 :单价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprice() {
		return price;
	}

	/**
	* 设置字段price的值，该字段的<br>
	* 字段名称 :单价<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprice(String price) {
		this.price = price;
    }

	/**
	* 获取字段storeNum的值，该字段的<br>
	* 字段名称 :库存<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getstoreNum() {
		if(storeNum==null){return 0;}
		return storeNum.intValue();
	}

	/**
	* 设置字段storeNum的值，该字段的<br>
	* 字段名称 :库存<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstoreNum(int storeNum) {
		this.storeNum = new Integer(storeNum);
    }

	/**
	* 设置字段storeNum的值，该字段的<br>
	* 字段名称 :库存<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstoreNum(String storeNum) {
		if (storeNum == null){
			this.storeNum = null;
			return;
		}
		this.storeNum = new Integer(storeNum);
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