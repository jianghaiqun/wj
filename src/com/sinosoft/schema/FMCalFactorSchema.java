package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：FMCalFactor<br>
 * 表代码：FMCalFactor<br>
 * 表主键：CalCode, FactorCode<br>
 */
public class FMCalFactorSchema extends Schema {
	private String CalCode;

	private String FactorCode;

	private String FactorName;

	private String FactorType;

	private String FactorGrade;

	private String FactorCalCode;

	private String FactorDefault;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("CalCode", DataColumn.STRING, 0, 6 , 0 , true , true),
		new SchemaColumn("FactorCode", DataColumn.STRING, 1, 6 , 0 , true , true),
		new SchemaColumn("FactorName", DataColumn.STRING, 2, 60 , 0 , true , false),
		new SchemaColumn("FactorType", DataColumn.STRING, 3, 2 , 0 , true , false),
		new SchemaColumn("FactorGrade", DataColumn.STRING, 4, 2 , 0 , true , false),
		new SchemaColumn("FactorCalCode", DataColumn.STRING, 5, 6 , 0 , false , false),
		new SchemaColumn("FactorDefault", DataColumn.STRING, 6, 30 , 0 , false , false)
	};

	public static final String _TableCode = "FMCalFactor";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into FMCalFactor values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update FMCalFactor set CalCode=?,FactorCode=?,FactorName=?,FactorType=?,FactorGrade=?,FactorCalCode=?,FactorDefault=? where CalCode=? and FactorCode=?";

	protected static final String _DeleteSQL = "delete from FMCalFactor  where CalCode=? and FactorCode=?";

	protected static final String _FillAllSQL = "select * from FMCalFactor  where CalCode=? and FactorCode=?";

	public FMCalFactorSchema(){
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
		return new FMCalFactorSchema();
	}

	protected SchemaSet newSet(){
		return new FMCalFactorSet();
	}

	public FMCalFactorSet query() {
		return query(null, -1, -1);
	}

	public FMCalFactorSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public FMCalFactorSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public FMCalFactorSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (FMCalFactorSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){CalCode = (String)v;return;}
		if (i == 1){FactorCode = (String)v;return;}
		if (i == 2){FactorName = (String)v;return;}
		if (i == 3){FactorType = (String)v;return;}
		if (i == 4){FactorGrade = (String)v;return;}
		if (i == 5){FactorCalCode = (String)v;return;}
		if (i == 6){FactorDefault = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return CalCode;}
		if (i == 1){return FactorCode;}
		if (i == 2){return FactorName;}
		if (i == 3){return FactorType;}
		if (i == 4){return FactorGrade;}
		if (i == 5){return FactorCalCode;}
		if (i == 6){return FactorDefault;}
		return null;
	}

	/**
	* 获取字段CalCode的值，该字段的<br>
	* 字段名称 :CalCode<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getCalCode() {
		return CalCode;
	}

	/**
	* 设置字段CalCode的值，该字段的<br>
	* 字段名称 :CalCode<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setCalCode(String calCode) {
		this.CalCode = calCode;
    }

	/**
	* 获取字段FactorCode的值，该字段的<br>
	* 字段名称 :要素编码<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getFactorCode() {
		return FactorCode;
	}

	/**
	* 设置字段FactorCode的值，该字段的<br>
	* 字段名称 :要素编码<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setFactorCode(String factorCode) {
		this.FactorCode = factorCode;
    }

	/**
	* 获取字段FactorName的值，该字段的<br>
	* 字段名称 :要素名称<br>
	* 数据类型 :varchar(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getFactorName() {
		return FactorName;
	}

	/**
	* 设置字段FactorName的值，该字段的<br>
	* 字段名称 :要素名称<br>
	* 数据类型 :varchar(60)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setFactorName(String factorName) {
		this.FactorName = factorName;
    }

	/**
	* 获取字段FactorType的值，该字段的<br>
	* 字段名称 :要素类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1--基本要素、2--扩展要素，或别的分类。<br>
	*/
	public String getFactorType() {
		return FactorType;
	}

	/**
	* 设置字段FactorType的值，该字段的<br>
	* 字段名称 :要素类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1--基本要素、2--扩展要素，或别的分类。<br>
	*/
	public void setFactorType(String factorType) {
		this.FactorType = factorType;
    }

	/**
	* 获取字段FactorGrade的值，该字段的<br>
	* 字段名称 :要素优先级<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1--最高级 2--高级 ......类推<br>
	*/
	public String getFactorGrade() {
		return FactorGrade;
	}

	/**
	* 设置字段FactorGrade的值，该字段的<br>
	* 字段名称 :要素优先级<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1--最高级 2--高级 ......类推<br>
	*/
	public void setFactorGrade(String factorGrade) {
		this.FactorGrade = factorGrade;
    }

	/**
	* 获取字段FactorCalCode的值，该字段的<br>
	* 字段名称 :要素算法编码<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFactorCalCode() {
		return FactorCalCode;
	}

	/**
	* 设置字段FactorCalCode的值，该字段的<br>
	* 字段名称 :要素算法编码<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFactorCalCode(String factorCalCode) {
		this.FactorCalCode = factorCalCode;
    }

	/**
	* 获取字段FactorDefault的值，该字段的<br>
	* 字段名称 :默认值<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFactorDefault() {
		return FactorDefault;
	}

	/**
	* 设置字段FactorDefault的值，该字段的<br>
	* 字段名称 :默认值<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFactorDefault(String factorDefault) {
		this.FactorDefault = factorDefault;
    }

}