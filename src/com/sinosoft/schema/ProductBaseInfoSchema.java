package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：产品详细页基本信息表<br>
 * 表代码：ProductBaseInfo<br>
 * 表主键：id<br>
 */
public class ProductBaseInfoSchema extends Schema {
	private String id;

	private String ProductID;

	private String BaseInfo;

	private String FrontInfo;

	private String TailInfo;

	private String IsFlag;

	private String ComFlag;

	private String ComCode;

	private String Prop1;

	private String CreateDate;

	private String ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("ProductID", DataColumn.STRING, 1, 32 , 0 , false , false),
		new SchemaColumn("BaseInfo", DataColumn.CLOB, 2, 0 , 0 , false , false),
		new SchemaColumn("FrontInfo", DataColumn.CLOB, 3, 0 , 0 , false , false),
		new SchemaColumn("TailInfo", DataColumn.CLOB, 4, 0 , 0 , false , false),
		new SchemaColumn("IsFlag", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("ComFlag", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("ComCode", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.CLOB, 8, 0 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 9, 32 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.STRING, 10, 32 , 0 , false , false)
	};

	public static final String _TableCode = "ProductBaseInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ProductBaseInfo values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ProductBaseInfo set id=?,ProductID=?,BaseInfo=?,FrontInfo=?,TailInfo=?,IsFlag=?,ComFlag=?,ComCode=?,Prop1=?,CreateDate=?,ModifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from ProductBaseInfo  where id=?";

	protected static final String _FillAllSQL = "select * from ProductBaseInfo  where id=?";

	public ProductBaseInfoSchema(){
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
		return new ProductBaseInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ProductBaseInfoSet();
	}

	public ProductBaseInfoSet query() {
		return query(null, -1, -1);
	}

	public ProductBaseInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ProductBaseInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ProductBaseInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ProductBaseInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){ProductID = (String)v;return;}
		if (i == 2){BaseInfo = (String)v;return;}
		if (i == 3){FrontInfo = (String)v;return;}
		if (i == 4){TailInfo = (String)v;return;}
		if (i == 5){IsFlag = (String)v;return;}
		if (i == 6){ComFlag = (String)v;return;}
		if (i == 7){ComCode = (String)v;return;}
		if (i == 8){Prop1 = (String)v;return;}
		if (i == 9){CreateDate = (String)v;return;}
		if (i == 10){ModifyDate = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return ProductID;}
		if (i == 2){return BaseInfo;}
		if (i == 3){return FrontInfo;}
		if (i == 4){return TailInfo;}
		if (i == 5){return IsFlag;}
		if (i == 6){return ComFlag;}
		if (i == 7){return ComCode;}
		if (i == 8){return Prop1;}
		if (i == 9){return CreateDate;}
		if (i == 10){return ModifyDate;}
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
	* 获取字段ProductID的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductID() {
		return ProductID;
	}

	/**
	* 设置字段ProductID的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductID(String productID) {
		this.ProductID = productID;
    }

	/**
	* 获取字段BaseInfo的值，该字段的<br>
	* 字段名称 :产品基本信息<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBaseInfo() {
		return BaseInfo;
	}

	/**
	* 设置字段BaseInfo的值，该字段的<br>
	* 字段名称 :产品基本信息<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBaseInfo(String baseInfo) {
		this.BaseInfo = baseInfo;
    }

	/**
	* 获取字段FrontInfo的值，该字段的<br>
	* 字段名称 :头部信息<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFrontInfo() {
		return FrontInfo;
	}

	/**
	* 设置字段FrontInfo的值，该字段的<br>
	* 字段名称 :头部信息<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFrontInfo(String frontInfo) {
		this.FrontInfo = frontInfo;
    }

	/**
	* 获取字段TailInfo的值，该字段的<br>
	* 字段名称 :尾部信息<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTailInfo() {
		return TailInfo;
	}

	/**
	* 设置字段TailInfo的值，该字段的<br>
	* 字段名称 :尾部信息<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTailInfo(String tailInfo) {
		this.TailInfo = tailInfo;
    }

	/**
	* 获取字段IsFlag的值，该字段的<br>
	* 字段名称 :是否允许使用品牌信息<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y:允许 N:不允许<br>
	*/
	public String getIsFlag() {
		return IsFlag;
	}

	/**
	* 设置字段IsFlag的值，该字段的<br>
	* 字段名称 :是否允许使用品牌信息<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y:允许 N:不允许<br>
	*/
	public void setIsFlag(String isFlag) {
		this.IsFlag = isFlag;
    }

	/**
	* 获取字段ComFlag的值，该字段的<br>
	* 字段名称 :是否关联品牌<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y:关联品牌 N:不关联品牌<br>
	*/
	public String getComFlag() {
		return ComFlag;
	}

	/**
	* 设置字段ComFlag的值，该字段的<br>
	* 字段名称 :是否关联品牌<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y:关联品牌 N:不关联品牌<br>
	*/
	public void setComFlag(String comFlag) {
		this.ComFlag = comFlag;
    }

	/**
	* 获取字段ComCode的值，该字段的<br>
	* 字段名称 :品牌编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	所关联的品牌编码即保险公司编码<br>
	*/
	public String getComCode() {
		return ComCode;
	}

	/**
	* 设置字段ComCode的值，该字段的<br>
	* 字段名称 :品牌编码<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	所关联的品牌编码即保险公司编码<br>
	*/
	public void setComCode(String comCode) {
		this.ComCode = comCode;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(String createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :ModifyDate<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(String modifyDate) {
		this.ModifyDate = modifyDate;
    }

}