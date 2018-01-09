package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：筛选条件表<br>
 * 表代码：FEMSearchConditionInfo<br>
 * 表主键：Id<br>
 */
public class FEMSearchConditionInfoSchema extends Schema {
	private String Id;

	private String SearchCode;

	private String SearchName;

	private String UpperId;

	private String Intersection;

	private String IsMultipleChoice;

	private String SearchOrder;

	private String SearchLevel;

	private String SubNodeNum;

	private String SubColumnCategory;

	private String ERiskType;

	private String SearchGroupID;

	private String Extra1;

	private String Extra2;

	private String Extra3;

	private String Extra4;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("SearchCode", DataColumn.STRING, 1, 60 , 0 , false , false),
		new SchemaColumn("SearchName", DataColumn.STRING, 2, 60 , 0 , false , false),
		new SchemaColumn("UpperId", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("Intersection", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("IsMultipleChoice", DataColumn.STRING, 5, 8 , 0 , false , false),
		new SchemaColumn("SearchOrder", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("SearchLevel", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("SubNodeNum", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("SubColumnCategory", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("ERiskType", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("SearchGroupID", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("Extra1", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("Extra2", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("Extra3", DataColumn.STRING, 14, 100 , 0 , false , false),
		new SchemaColumn("Extra4", DataColumn.STRING, 15, 100 , 0 , false , false)
	};

	public static final String _TableCode = "FEMSearchConditionInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into FEMSearchConditionInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update FEMSearchConditionInfo set Id=?,SearchCode=?,SearchName=?,UpperId=?,Intersection=?,IsMultipleChoice=?,SearchOrder=?,SearchLevel=?,SubNodeNum=?,SubColumnCategory=?,ERiskType=?,SearchGroupID=?,Extra1=?,Extra2=?,Extra3=?,Extra4=? where Id=?";

	protected static final String _DeleteSQL = "delete from FEMSearchConditionInfo  where Id=?";

	protected static final String _FillAllSQL = "select * from FEMSearchConditionInfo  where Id=?";

	public FEMSearchConditionInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[16];
	}

	protected Schema newInstance(){
		return new FEMSearchConditionInfoSchema();
	}

	protected SchemaSet newSet(){
		return new FEMSearchConditionInfoSet();
	}

	public FEMSearchConditionInfoSet query() {
		return query(null, -1, -1);
	}

	public FEMSearchConditionInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public FEMSearchConditionInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public FEMSearchConditionInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (FEMSearchConditionInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){SearchCode = (String)v;return;}
		if (i == 2){SearchName = (String)v;return;}
		if (i == 3){UpperId = (String)v;return;}
		if (i == 4){Intersection = (String)v;return;}
		if (i == 5){IsMultipleChoice = (String)v;return;}
		if (i == 6){SearchOrder = (String)v;return;}
		if (i == 7){SearchLevel = (String)v;return;}
		if (i == 8){SubNodeNum = (String)v;return;}
		if (i == 9){SubColumnCategory = (String)v;return;}
		if (i == 10){ERiskType = (String)v;return;}
		if (i == 11){SearchGroupID = (String)v;return;}
		if (i == 12){Extra1 = (String)v;return;}
		if (i == 13){Extra2 = (String)v;return;}
		if (i == 14){Extra3 = (String)v;return;}
		if (i == 15){Extra4 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return SearchCode;}
		if (i == 2){return SearchName;}
		if (i == 3){return UpperId;}
		if (i == 4){return Intersection;}
		if (i == 5){return IsMultipleChoice;}
		if (i == 6){return SearchOrder;}
		if (i == 7){return SearchLevel;}
		if (i == 8){return SubNodeNum;}
		if (i == 9){return SubColumnCategory;}
		if (i == 10){return ERiskType;}
		if (i == 11){return SearchGroupID;}
		if (i == 12){return Extra1;}
		if (i == 13){return Extra2;}
		if (i == 14){return Extra3;}
		if (i == 15){return Extra4;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :流水ID<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :流水ID<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段SearchCode的值，该字段的<br>
	* 字段名称 :检索条件编码<br>
	* 数据类型 :VARCHAR2(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSearchCode() {
		return SearchCode;
	}

	/**
	* 设置字段SearchCode的值，该字段的<br>
	* 字段名称 :检索条件编码<br>
	* 数据类型 :VARCHAR2(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearchCode(String searchCode) {
		this.SearchCode = searchCode;
    }

	/**
	* 获取字段SearchName的值，该字段的<br>
	* 字段名称 :检索条件名称<br>
	* 数据类型 :VARCHAR2(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSearchName() {
		return SearchName;
	}

	/**
	* 设置字段SearchName的值，该字段的<br>
	* 字段名称 :检索条件名称<br>
	* 数据类型 :VARCHAR2(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearchName(String searchName) {
		this.SearchName = searchName;
    }

	/**
	* 获取字段UpperId的值，该字段的<br>
	* 字段名称 :上级编码ID<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUpperId() {
		return UpperId;
	}

	/**
	* 设置字段UpperId的值，该字段的<br>
	* 字段名称 :上级编码ID<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUpperId(String upperId) {
		this.UpperId = upperId;
    }

	/**
	* 获取字段Intersection的值，该字段的<br>
	* 字段名称 :产品集合标志<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIntersection() {
		return Intersection;
	}

	/**
	* 设置字段Intersection的值，该字段的<br>
	* 字段名称 :产品集合标志<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIntersection(String intersection) {
		this.Intersection = intersection;
    }

	/**
	* 获取字段IsMultipleChoice的值，该字段的<br>
	* 字段名称 :是否多选<br>
	* 数据类型 :VARCHAR2(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsMultipleChoice() {
		return IsMultipleChoice;
	}

	/**
	* 设置字段IsMultipleChoice的值，该字段的<br>
	* 字段名称 :是否多选<br>
	* 数据类型 :VARCHAR2(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsMultipleChoice(String isMultipleChoice) {
		this.IsMultipleChoice = isMultipleChoice;
    }

	/**
	* 获取字段SearchOrder的值，该字段的<br>
	* 字段名称 :检索条件顺序<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSearchOrder() {
		return SearchOrder;
	}

	/**
	* 设置字段SearchOrder的值，该字段的<br>
	* 字段名称 :检索条件顺序<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearchOrder(String searchOrder) {
		this.SearchOrder = searchOrder;
    }

	/**
	* 获取字段SearchLevel的值，该字段的<br>
	* 字段名称 :检索条件级别<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSearchLevel() {
		return SearchLevel;
	}

	/**
	* 设置字段SearchLevel的值，该字段的<br>
	* 字段名称 :检索条件级别<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearchLevel(String searchLevel) {
		this.SearchLevel = searchLevel;
    }

	/**
	* 获取字段SubNodeNum的值，该字段的<br>
	* 字段名称 :子节点数<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSubNodeNum() {
		return SubNodeNum;
	}

	/**
	* 设置字段SubNodeNum的值，该字段的<br>
	* 字段名称 :子节点数<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSubNodeNum(String subNodeNum) {
		this.SubNodeNum = subNodeNum;
    }

	/**
	* 获取字段SubColumnCategory的值，该字段的<br>
	* 字段名称 :所属子栏目类别<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSubColumnCategory() {
		return SubColumnCategory;
	}

	/**
	* 设置字段SubColumnCategory的值，该字段的<br>
	* 字段名称 :所属子栏目类别<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSubColumnCategory(String subColumnCategory) {
		this.SubColumnCategory = subColumnCategory;
    }

	/**
	* 获取字段ERiskType的值，该字段的<br>
	* 字段名称 :所属电商大类<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getERiskType() {
		return ERiskType;
	}

	/**
	* 设置字段ERiskType的值，该字段的<br>
	* 字段名称 :所属电商大类<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setERiskType(String eRiskType) {
		this.ERiskType = eRiskType;
    }

	/**
	* 获取字段SearchGroupID的值，该字段的<br>
	* 字段名称 :检索排续号<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSearchGroupID() {
		return SearchGroupID;
	}

	/**
	* 设置字段SearchGroupID的值，该字段的<br>
	* 字段名称 :检索排续号<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearchGroupID(String searchGroupID) {
		this.SearchGroupID = searchGroupID;
    }

	/**
	* 获取字段Extra1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :VARCHAR2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getExtra1() {
		return Extra1;
	}

	/**
	* 设置字段Extra1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :VARCHAR2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setExtra1(String extra1) {
		this.Extra1 = extra1;
    }

	/**
	* 获取字段Extra2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :VARCHAR2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getExtra2() {
		return Extra2;
	}

	/**
	* 设置字段Extra2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :VARCHAR2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setExtra2(String extra2) {
		this.Extra2 = extra2;
    }

	/**
	* 获取字段Extra3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :VARCHAR2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getExtra3() {
		return Extra3;
	}

	/**
	* 设置字段Extra3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :VARCHAR2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setExtra3(String extra3) {
		this.Extra3 = extra3;
    }

	/**
	* 获取字段Extra4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :VARCHAR2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getExtra4() {
		return Extra4;
	}

	/**
	* 设置字段Extra4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :VARCHAR2(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setExtra4(String extra4) {
		this.Extra4 = extra4;
    }

}