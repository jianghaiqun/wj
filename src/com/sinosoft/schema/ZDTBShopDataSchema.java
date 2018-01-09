package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：ZDTBShopData<br>
 * 表代码：ZDTBShopData<br>
 * 表主键：Id<br>
 */
public class ZDTBShopDataSchema extends Schema {
	private String Id;

	private String ItemName;

	private String ItemID;

	private String ShopId;

	private String ShopName;

	private String InsType;

	private String InsTypeName;

	private String TSellCount;

	private String SellCount;

	private String Price;

	private String Prop1;

	private String Prop2;

	private Date CreateDate;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 96 , 0 , true , true),
		new SchemaColumn("ItemName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("ItemID", DataColumn.STRING, 2, 60 , 0 , false , false),
		new SchemaColumn("ShopId", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("ShopName", DataColumn.STRING, 4, 300 , 0 , false , false),
		new SchemaColumn("InsType", DataColumn.STRING, 5, 6 , 0 , false , false),
		new SchemaColumn("InsTypeName", DataColumn.STRING, 6, 15 , 0 , false , false),
		new SchemaColumn("TSellCount", DataColumn.STRING, 7, 15 , 0 , false , false),
		new SchemaColumn("SellCount", DataColumn.STRING, 8, 15 , 0 , false , false),
		new SchemaColumn("Price", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 10, 60 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 11, 60 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 13, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDTBShopData";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDTBShopData values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDTBShopData set Id=?,ItemName=?,ItemID=?,ShopId=?,ShopName=?,InsType=?,InsTypeName=?,TSellCount=?,SellCount=?,Price=?,Prop1=?,Prop2=?,CreateDate=?,ModifyDate=? where Id=?";

	protected static final String _DeleteSQL = "delete from ZDTBShopData  where Id=?";

	protected static final String _FillAllSQL = "select * from ZDTBShopData  where Id=?";

	public ZDTBShopDataSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[14];
	}

	protected Schema newInstance(){
		return new ZDTBShopDataSchema();
	}

	protected SchemaSet newSet(){
		return new ZDTBShopDataSet();
	}

	public ZDTBShopDataSet query() {
		return query(null, -1, -1);
	}

	public ZDTBShopDataSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDTBShopDataSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDTBShopDataSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDTBShopDataSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ItemName = (String)v;return;}
		if (i == 2){ItemID = (String)v;return;}
		if (i == 3){ShopId = (String)v;return;}
		if (i == 4){ShopName = (String)v;return;}
		if (i == 5){InsType = (String)v;return;}
		if (i == 6){InsTypeName = (String)v;return;}
		if (i == 7){TSellCount = (String)v;return;}
		if (i == 8){SellCount = (String)v;return;}
		if (i == 9){Price = (String)v;return;}
		if (i == 10){Prop1 = (String)v;return;}
		if (i == 11){Prop2 = (String)v;return;}
		if (i == 12){CreateDate = (Date)v;return;}
		if (i == 13){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ItemName;}
		if (i == 2){return ItemID;}
		if (i == 3){return ShopId;}
		if (i == 4){return ShopName;}
		if (i == 5){return InsType;}
		if (i == 6){return InsTypeName;}
		if (i == 7){return TSellCount;}
		if (i == 8){return SellCount;}
		if (i == 9){return Price;}
		if (i == 10){return Prop1;}
		if (i == 11){return Prop2;}
		if (i == 12){return CreateDate;}
		if (i == 13){return ModifyDate;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(96)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(96)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段ItemName的值，该字段的<br>
	* 字段名称 :ItemName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getItemName() {
		return ItemName;
	}

	/**
	* 设置字段ItemName的值，该字段的<br>
	* 字段名称 :ItemName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setItemName(String itemName) {
		this.ItemName = itemName;
    }

	/**
	* 获取字段ItemID的值，该字段的<br>
	* 字段名称 :ItemID<br>
	* 数据类型 :varchar(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getItemID() {
		return ItemID;
	}

	/**
	* 设置字段ItemID的值，该字段的<br>
	* 字段名称 :ItemID<br>
	* 数据类型 :varchar(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setItemID(String itemID) {
		this.ItemID = itemID;
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
	* 获取字段ShopName的值，该字段的<br>
	* 字段名称 :ShopName<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShopName() {
		return ShopName;
	}

	/**
	* 设置字段ShopName的值，该字段的<br>
	* 字段名称 :ShopName<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShopName(String shopName) {
		this.ShopName = shopName;
    }

	/**
	* 获取字段InsType的值，该字段的<br>
	* 字段名称 :InsType<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsType() {
		return InsType;
	}

	/**
	* 设置字段InsType的值，该字段的<br>
	* 字段名称 :InsType<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsType(String insType) {
		this.InsType = insType;
    }

	/**
	* 获取字段InsTypeName的值，该字段的<br>
	* 字段名称 :InsTypeName<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsTypeName() {
		return InsTypeName;
	}

	/**
	* 设置字段InsTypeName的值，该字段的<br>
	* 字段名称 :InsTypeName<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsTypeName(String insTypeName) {
		this.InsTypeName = insTypeName;
    }

	/**
	* 获取字段TSellCount的值，该字段的<br>
	* 字段名称 :TSellCount<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTSellCount() {
		return TSellCount;
	}

	/**
	* 设置字段TSellCount的值，该字段的<br>
	* 字段名称 :TSellCount<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTSellCount(String tSellCount) {
		this.TSellCount = tSellCount;
    }

	/**
	* 获取字段SellCount的值，该字段的<br>
	* 字段名称 :SellCount<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSellCount() {
		return SellCount;
	}

	/**
	* 设置字段SellCount的值，该字段的<br>
	* 字段名称 :SellCount<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSellCount(String sellCount) {
		this.SellCount = sellCount;
    }

	/**
	* 获取字段Price的值，该字段的<br>
	* 字段名称 :Price<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPrice() {
		return Price;
	}

	/**
	* 设置字段Price的值，该字段的<br>
	* 字段名称 :Price<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPrice(String price) {
		this.Price = price;
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