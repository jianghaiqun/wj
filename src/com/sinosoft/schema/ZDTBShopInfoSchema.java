package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：ZDTBShopInfo<br>
 * 表代码：ZDTBShopInfo<br>
 * 表主键：Id<br>
 */
public class ZDTBShopInfoSchema extends Schema {
	private String Id;

	private String ParentId;

	private String ShopName;

	private String ShopUrl;

	private String Level;

	private String Prop1;

	private String Prop2;

	private Date CreateDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("ParentId", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("ShopName", DataColumn.STRING, 2, 30 , 0 , false , false),
		new SchemaColumn("ShopUrl", DataColumn.STRING, 3, 60 , 0 , false , false),
		new SchemaColumn("Level", DataColumn.STRING, 4, 1 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 60 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 6, 60 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 7, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDTBShopInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDTBShopInfo values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDTBShopInfo set Id=?,ParentId=?,ShopName=?,ShopUrl=?,Level=?,Prop1=?,Prop2=?,CreateDate=? where Id=?";

	protected static final String _DeleteSQL = "delete from ZDTBShopInfo  where Id=?";

	protected static final String _FillAllSQL = "select * from ZDTBShopInfo  where Id=?";

	public ZDTBShopInfoSchema(){
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
		return new ZDTBShopInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ZDTBShopInfoSet();
	}

	public ZDTBShopInfoSet query() {
		return query(null, -1, -1);
	}

	public ZDTBShopInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDTBShopInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDTBShopInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDTBShopInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ParentId = (String)v;return;}
		if (i == 2){ShopName = (String)v;return;}
		if (i == 3){ShopUrl = (String)v;return;}
		if (i == 4){Level = (String)v;return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){Prop2 = (String)v;return;}
		if (i == 7){CreateDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ParentId;}
		if (i == 2){return ShopName;}
		if (i == 3){return ShopUrl;}
		if (i == 4){return Level;}
		if (i == 5){return Prop1;}
		if (i == 6){return Prop2;}
		if (i == 7){return CreateDate;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段ParentId的值，该字段的<br>
	* 字段名称 :ParentId<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getParentId() {
		return ParentId;
	}

	/**
	* 设置字段ParentId的值，该字段的<br>
	* 字段名称 :ParentId<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setParentId(String parentId) {
		this.ParentId = parentId;
    }

	/**
	* 获取字段ShopName的值，该字段的<br>
	* 字段名称 :ShopName<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShopName() {
		return ShopName;
	}

	/**
	* 设置字段ShopName的值，该字段的<br>
	* 字段名称 :ShopName<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShopName(String shopName) {
		this.ShopName = shopName;
    }

	/**
	* 获取字段ShopUrl的值，该字段的<br>
	* 字段名称 :ShopUrl<br>
	* 数据类型 :varchar(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShopUrl() {
		return ShopUrl;
	}

	/**
	* 设置字段ShopUrl的值，该字段的<br>
	* 字段名称 :ShopUrl<br>
	* 数据类型 :varchar(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShopUrl(String shopUrl) {
		this.ShopUrl = shopUrl;
    }

	/**
	* 获取字段Level的值，该字段的<br>
	* 字段名称 :Level<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLevel() {
		return Level;
	}

	/**
	* 设置字段Level的值，该字段的<br>
	* 字段名称 :Level<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLevel(String level) {
		this.Level = level;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

}