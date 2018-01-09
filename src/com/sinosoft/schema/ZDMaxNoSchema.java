package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：最大号表<br>
 * 表代码：ZDMaxNo<br>
 * 表主键：NoType, NoSubType<br>
 */

public class ZDMaxNoSchema extends Schema {
	private String NoType;

	private String NoSubType;

	private Long MaxValue;

	private Long Length;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("NoType", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("NoSubType", DataColumn.STRING, 1, 255 , 0 , true , true),
		new SchemaColumn("MaxValue", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("Length", DataColumn.LONG, 3, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMaxNo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDMaxNo values(?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMaxNo set NoType=?,NoSubType=?,`MaxValue`=?,`Length`=? where NoType=? and NoSubType=?";

	protected static final String _DeleteSQL = "delete from ZDMaxNo  where NoType=? and NoSubType=?";

	protected static final String _FillAllSQL = "select * from ZDMaxNo  where NoType=? and NoSubType=?";

	public ZDMaxNoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[4];
	}

	protected Schema newInstance(){
		return new ZDMaxNoSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMaxNoSet();
	}

	public ZDMaxNoSet query() {
		return query(null, -1, -1);
	}

	public ZDMaxNoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMaxNoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMaxNoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMaxNoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){NoType = (String)v;return;}
		if (i == 1){NoSubType = (String)v;return;}
		if (i == 2){if(v==null){MaxValue = null;}else{MaxValue = new Long(v.toString());}return;}
		if (i == 3){if(v==null){Length = null;}else{Length = new Long(v.toString());}return;}
	}

	public Object getV(int i) {
		if (i == 0){return NoType;}
		if (i == 1){return NoSubType;}
		if (i == 2){return MaxValue;}
		if (i == 3){return Length;}
		return null;
	}

	/**
	* 获取字段NoType的值，该字段的<br>
	* 字段名称 :最大号类别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getNoType() {
		return NoType;
	}

	/**
	* 设置字段NoType的值，该字段的<br>
	* 字段名称 :最大号类别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setNoType(String noType) {
		this.NoType = noType;
    }

	/**
	* 获取字段NoSubType的值，该字段的<br>
	* 字段名称 :最大号子类别<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getNoSubType() {
		return NoSubType;
	}

	/**
	* 设置字段NoSubType的值，该字段的<br>
	* 字段名称 :最大号子类别<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setNoSubType(String noSubType) {
		this.NoSubType = noSubType;
    }

	/**
	* 获取字段MaxValue的值，该字段的<br>
	* 字段名称 :当前最大号<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getMaxValue() {
		if(MaxValue==null){return 0;}
		return MaxValue.longValue();
	}

	/**
	* 设置字段MaxValue的值，该字段的<br>
	* 字段名称 :当前最大号<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMaxValue(long maxValue) {
		this.MaxValue = new Long(maxValue);
    }

	/**
	* 设置字段MaxValue的值，该字段的<br>
	* 字段名称 :当前最大号<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMaxValue(String maxValue) {
		if (maxValue == null){
			this.MaxValue = null;
			return;
		}
		this.MaxValue = new Long(maxValue);
    }

	/**
	* 获取字段Length的值，该字段的<br>
	* 字段名称 :号码长度<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getLength() {
		if(Length==null){return 0;}
		return Length.longValue();
	}

	/**
	* 设置字段Length的值，该字段的<br>
	* 字段名称 :号码长度<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLength(long length) {
		this.Length = new Long(length);
    }

	/**
	* 设置字段Length的值，该字段的<br>
	* 字段名称 :号码长度<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLength(String length) {
		if (length == null){
			this.Length = null;
			return;
		}
		this.Length = new Long(length);
    }

}