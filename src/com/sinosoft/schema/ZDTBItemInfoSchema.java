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
 * 表名称：ZDTBItemInfo<br>
 * 表代码：ZDTBItemInfo<br>
 * 表主键：ItemId<br>
 */
public class ZDTBItemInfoSchema extends Schema {
	private String ItemId;

	private String ShopId;

	private String IsNew;

	private String Prop1;

	private String Prop2;

	private Date CreateDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ItemId", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("ShopId", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("IsNew", DataColumn.STRING, 2, 1 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 3, 60 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 4, 60 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 6, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDTBItemInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDTBItemInfo values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDTBItemInfo set ItemId=?,ShopId=?,IsNew=?,Prop1=?,Prop2=?,CreateDate=?,ModifyDate=? where ItemId=?";

	protected static final String _DeleteSQL = "delete from ZDTBItemInfo  where ItemId=?";

	protected static final String _FillAllSQL = "select * from ZDTBItemInfo  where ItemId=?";

	public ZDTBItemInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[7];
	}

	protected Schema newInstance(){
		return new ZDTBItemInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ZDTBItemInfoSet();
	}

	public ZDTBItemInfoSet query() {
		return query(null, -1, -1);
	}

	public ZDTBItemInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDTBItemInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDTBItemInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDTBItemInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ItemId = (String)v;return;}
		if (i == 1){ShopId = (String)v;return;}
		if (i == 2){IsNew = (String)v;return;}
		if (i == 3){Prop1 = (String)v;return;}
		if (i == 4){Prop2 = (String)v;return;}
		if (i == 5){CreateDate = (Date)v;return;}
		if (i == 6){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ItemId;}
		if (i == 1){return ShopId;}
		if (i == 2){return IsNew;}
		if (i == 3){return Prop1;}
		if (i == 4){return Prop2;}
		if (i == 5){return CreateDate;}
		if (i == 6){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段ItemId的值，该字段的<br>
	* 字段名称 :ItemId<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getItemId() {
		return ItemId;
	}

	/**
	* 设置字段ItemId的值，该字段的<br>
	* 字段名称 :ItemId<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setItemId(String itemId) {
		this.ItemId = itemId;
    }

	/**
	* 获取字段ShopId的值，该字段的<br>
	* 字段名称 :ShopId<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShopId() {
		return ShopId;
	}

	/**
	* 设置字段ShopId的值，该字段的<br>
	* 字段名称 :ShopId<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShopId(String shopId) {
		this.ShopId = shopId;
    }

	/**
	* 获取字段IsNew的值，该字段的<br>
	* 字段名称 :IsNew<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsNew() {
		return IsNew;
	}

	/**
	* 设置字段IsNew的值，该字段的<br>
	* 字段名称 :IsNew<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsNew(String isNew) {
		this.IsNew = isNew;
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

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}