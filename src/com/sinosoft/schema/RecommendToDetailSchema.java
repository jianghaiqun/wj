package com.sinosoft.schema;

import java.util.Date;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.orm.SchemaSet;

/**
 * 表名称：详细页推荐产品<br>
 * 表代码：RecommendToDetail<br>
 * 表主键：id<br>
 */
public class RecommendToDetailSchema extends Schema {
	private String id;

	private Date MakeDate;

	private Date ModifyDate;

	private String ProductID;

	private String ArticleID;

	private String RelaArticleID;

	private String CatalogID;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("MakeDate", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("ProductID", DataColumn.STRING, 3, 32 , 0 , false , false),
		new SchemaColumn("ArticleID", DataColumn.STRING, 4, 32 , 0 , false , false),
		new SchemaColumn("RelaArticleID", DataColumn.STRING, 5, 32 , 0 , false , false),
		new SchemaColumn("CatalogID", DataColumn.STRING, 6, 32 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 32 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 32 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 9, 32 , 0 , false , false)
	};

	public static final String _TableCode = "RecommendToDetail";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into RecommendToDetail values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update RecommendToDetail set id=?,MakeDate=?,ModifyDate=?,ProductID=?,ArticleID=?,RelaArticleID=?,CatalogID=?,Prop1=?,Prop2=?,Prop3=? where id=?";

	protected static final String _DeleteSQL = "delete from RecommendToDetail  where id=?";

	protected static final String _FillAllSQL = "select * from RecommendToDetail  where id=?";

	public RecommendToDetailSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[10];
	}

	protected Schema newInstance(){
		return new RecommendToDetailSchema();
	}

	protected SchemaSet newSet(){
		return new RecommendToDetailSet();
	}

	public RecommendToDetailSet query() {
		return query(null, -1, -1);
	}

	public RecommendToDetailSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public RecommendToDetailSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public RecommendToDetailSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (RecommendToDetailSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){MakeDate = (Date)v;return;}
		if (i == 2){ModifyDate = (Date)v;return;}
		if (i == 3){ProductID = (String)v;return;}
		if (i == 4){ArticleID = (String)v;return;}
		if (i == 5){RelaArticleID = (String)v;return;}
		if (i == 6){CatalogID = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
		if (i == 9){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return MakeDate;}
		if (i == 2){return ModifyDate;}
		if (i == 3){return ProductID;}
		if (i == 4){return ArticleID;}
		if (i == 5){return RelaArticleID;}
		if (i == 6){return CatalogID;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		if (i == 9){return Prop3;}
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
	* 获取字段MakeDate的值，该字段的<br>
	* 字段名称 :MakeDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getMakeDate() {
		return MakeDate;
	}

	/**
	* 设置字段MakeDate的值，该字段的<br>
	* 字段名称 :MakeDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMakeDate(Date makeDate) {
		this.MakeDate = makeDate;
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

	/**
	* 获取字段ProductID的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--必录 N--不是必录项<br>
	*/
	public String getProductID() {
		return ProductID;
	}

	/**
	* 设置字段ProductID的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--必录 N--不是必录项<br>
	*/
	public void setProductID(String productID) {
		this.ProductID = productID;
    }

	/**
	* 获取字段ArticleID的值，该字段的<br>
	* 字段名称 :文章ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	input--输入框 radio--单选 checkbox--多选 select--下拉列表选择 text--文本域 readonlytext--只读文本域<br>
	*/
	public String getArticleID() {
		return ArticleID;
	}

	/**
	* 设置字段ArticleID的值，该字段的<br>
	* 字段名称 :文章ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	input--输入框 radio--单选 checkbox--多选 select--下拉列表选择 text--文本域 readonlytext--只读文本域<br>
	*/
	public void setArticleID(String articleID) {
		this.ArticleID = articleID;
    }

	/**
	* 获取字段RelaArticleID的值，该字段的<br>
	* 字段名称 :关联文章ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRelaArticleID() {
		return RelaArticleID;
	}

	/**
	* 设置字段RelaArticleID的值，该字段的<br>
	* 字段名称 :关联文章ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRelaArticleID(String relaArticleID) {
		this.RelaArticleID = relaArticleID;
    }

	/**
	* 获取字段CatalogID的值，该字段的<br>
	* 字段名称 :栏目ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	wap app如果多种类型那么用“，”号隔开，如“wap,app”<br>
	*/
	public String getCatalogID() {
		return CatalogID;
	}

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :栏目ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	wap app如果多种类型那么用“，”号隔开，如“wap,app”<br>
	*/
	public void setCatalogID(String catalogID) {
		this.CatalogID = catalogID;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}