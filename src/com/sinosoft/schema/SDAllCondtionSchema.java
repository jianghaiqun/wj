package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：筛选条件组合<br>
 * 表代码：SDAllCondtion<br>
 * 表主键：Id<br>
 */
public class SDAllCondtionSchema extends Schema {
	private Long Id;

	private String SearchID;

	private String ERiskSubType;

	private String SearchName;

	private Date CreateDate;

	private Date PublishDate;

	private Integer ProductCount;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("SearchID", DataColumn.STRING, 1, 500 , 0 , false , false),
		new SchemaColumn("ERiskSubType", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("SearchName", DataColumn.STRING, 3, 1000 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 4, 0 , 0 , true , false),
		new SchemaColumn("PublishDate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("ProductCount", DataColumn.INTEGER, 6, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 20 , 0 , false , false)
	};

	public static final String _TableCode = "SDAllCondtion";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDAllCondtion values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDAllCondtion set Id=?,SearchID=?,ERiskSubType=?,SearchName=?,CreateDate=?,PublishDate=?,ProductCount=?,Prop1=?,Prop2=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDAllCondtion  where Id=?";

	protected static final String _FillAllSQL = "select * from SDAllCondtion  where Id=?";

	public SDAllCondtionSchema(){
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
		return new SDAllCondtionSchema();
	}

	protected SchemaSet newSet(){
		return new SDAllCondtionSet();
	}

	public SDAllCondtionSet query() {
		return query(null, -1, -1);
	}

	public SDAllCondtionSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDAllCondtionSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDAllCondtionSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDAllCondtionSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (Long)v;return;}
		if (i == 1){SearchID = (String)v;return;}
		if (i == 2){ERiskSubType = (String)v;return;}
		if (i == 3){SearchName = (String)v;return;}
		if (i == 4){CreateDate = (Date)v;return;}
		if (i == 5){PublishDate = (Date)v;return;}
		if (i == 6){if(v==null){ProductCount = null;}else{ProductCount = new Integer(v.toString());}return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return SearchID;}
		if (i == 2){return ERiskSubType;}
		if (i == 3){return SearchName;}
		if (i == 4){return CreateDate;}
		if (i == 5){return PublishDate;}
		if (i == 6){return ProductCount;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :流水ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getId() {
		if(Id==null){return 0;}
		return Id.longValue();
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :流水ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(long id) {
		this.Id = new Long(id);
    }

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :流水ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		if (id == null){
			this.Id = null;
			return;
		}
		this.Id = new Long(id);
    }

	/**
	* 获取字段SearchID的值，该字段的<br>
	* 字段名称 :筛选ID<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSearchID() {
		return SearchID;
	}

	/**
	* 设置字段SearchID的值，该字段的<br>
	* 字段名称 :筛选ID<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearchID(String searchID) {
		this.SearchID = searchID;
    }

	/**
	* 获取字段ERiskSubType的值，该字段的<br>
	* 字段名称 :栏目分类<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getERiskSubType() {
		return ERiskSubType;
	}

	/**
	* 设置字段ERiskSubType的值，该字段的<br>
	* 字段名称 :栏目分类<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setERiskSubType(String eRiskSubType) {
		this.ERiskSubType = eRiskSubType;
    }

	/**
	* 获取字段SearchName的值，该字段的<br>
	* 字段名称 :标题<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSearchName() {
		return SearchName;
	}

	/**
	* 设置字段SearchName的值，该字段的<br>
	* 字段名称 :标题<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearchName(String searchName) {
		this.SearchName = searchName;
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
	* 获取字段PublishDate的值，该字段的<br>
	* 字段名称 :发布时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getPublishDate() {
		return PublishDate;
	}

	/**
	* 设置字段PublishDate的值，该字段的<br>
	* 字段名称 :发布时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPublishDate(Date publishDate) {
		this.PublishDate = publishDate;
    }

	/**
	* 获取字段ProductCount的值，该字段的<br>
	* 字段名称 :产品个数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getProductCount() {
		if(ProductCount==null){return 0;}
		return ProductCount.intValue();
	}

	/**
	* 设置字段ProductCount的值，该字段的<br>
	* 字段名称 :产品个数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductCount(int productCount) {
		this.ProductCount = new Integer(productCount);
    }

	/**
	* 设置字段ProductCount的值，该字段的<br>
	* 字段名称 :产品个数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductCount(String productCount) {
		if (productCount == null){
			this.ProductCount = null;
			return;
		}
		this.ProductCount = new Integer(productCount);
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

}