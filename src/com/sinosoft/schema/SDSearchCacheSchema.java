package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：SDSearchCache<br>
 * 表代码：SDSearchCache<br>
 * 表主键：ID<br>
 */
public class SDSearchCacheSchema extends Schema {
	private Long ID;

	private String ConditionCode;

	private String ConditionName;

	private String SearchType;

	private String CacheURL;

	private String RealPath;

	private String CreateDate;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("ConditionCode", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("ConditionName", DataColumn.STRING, 2, 150 , 0 , true , false),
		new SchemaColumn("SearchType", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("CacheURL", DataColumn.STRING, 4, 150 , 0 , false , false),
		new SchemaColumn("RealPath", DataColumn.STRING, 5, 300 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.STRING, 6, 20 , 0 , true , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 9, 50 , 0 , false , false)
	};

	public static final String _TableCode = "SDSearchCache";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDSearchCache values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDSearchCache set ID=?,ConditionCode=?,ConditionName=?,SearchType=?,CacheURL=?,RealPath=?,CreateDate=?,Prop1=?,Prop2=?,Prop3=? where ID=?";

	protected static final String _DeleteSQL = "delete from SDSearchCache  where ID=?";

	protected static final String _FillAllSQL = "select * from SDSearchCache  where ID=?";

	public SDSearchCacheSchema(){
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
		return new SDSearchCacheSchema();
	}

	protected SchemaSet newSet(){
		return new SDSearchCacheSet();
	}

	public SDSearchCacheSet query() {
		return query(null, -1, -1);
	}

	public SDSearchCacheSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDSearchCacheSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDSearchCacheSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDSearchCacheSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (Long)v;return;}
		if (i == 1){ConditionCode = (String)v;return;}
		if (i == 2){ConditionName = (String)v;return;}
		if (i == 3){SearchType = (String)v;return;}
		if (i == 4){CacheURL = (String)v;return;}
		if (i == 5){RealPath = (String)v;return;}
		if (i == 6){CreateDate = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
		if (i == 9){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ConditionCode;}
		if (i == 2){return ConditionName;}
		if (i == 3){return SearchType;}
		if (i == 4){return CacheURL;}
		if (i == 5){return RealPath;}
		if (i == 6){return CreateDate;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		if (i == 9){return Prop3;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段ConditionCode的值，该字段的<br>
	* 字段名称 :ConditionCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getConditionCode() {
		return ConditionCode;
	}

	/**
	* 设置字段ConditionCode的值，该字段的<br>
	* 字段名称 :ConditionCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setConditionCode(String conditionCode) {
		this.ConditionCode = conditionCode;
    }

	/**
	* 获取字段ConditionName的值，该字段的<br>
	* 字段名称 :ConditionName<br>
	* 数据类型 :varchar(150)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getConditionName() {
		return ConditionName;
	}

	/**
	* 设置字段ConditionName的值，该字段的<br>
	* 字段名称 :ConditionName<br>
	* 数据类型 :varchar(150)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setConditionName(String conditionName) {
		this.ConditionName = conditionName;
    }

	/**
	* 获取字段SearchType的值，该字段的<br>
	* 字段名称 :SearchType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSearchType() {
		return SearchType;
	}

	/**
	* 设置字段SearchType的值，该字段的<br>
	* 字段名称 :SearchType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearchType(String searchType) {
		this.SearchType = searchType;
    }

	/**
	* 获取字段CacheURL的值，该字段的<br>
	* 字段名称 :CacheURL<br>
	* 数据类型 :varchar(150)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCacheURL() {
		return CacheURL;
	}

	/**
	* 设置字段CacheURL的值，该字段的<br>
	* 字段名称 :CacheURL<br>
	* 数据类型 :varchar(150)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCacheURL(String cacheURL) {
		this.CacheURL = cacheURL;
    }

	/**
	* 获取字段RealPath的值，该字段的<br>
	* 字段名称 :RealPath<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRealPath() {
		return RealPath;
	}

	/**
	* 设置字段RealPath的值，该字段的<br>
	* 字段名称 :RealPath<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRealPath(String realPath) {
		this.RealPath = realPath;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :CreateDate<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCreateDate(String createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}