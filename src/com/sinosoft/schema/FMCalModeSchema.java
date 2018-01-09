package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：FMCalMode<br>
 * 表代码：FMCalMode<br>
 * 表主键：CalCode<br>
 */
public class FMCalModeSchema extends Schema {
	private String CalCode;

	private String RiskCode;

	private String OutRiskCode;

	private String Type;

	private String CalSQL;

	private String Remark;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("CalCode", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("RiskCode", DataColumn.STRING, 1, 20 , 0 , true , false),
		new SchemaColumn("OutRiskCode", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 3, 2 , 0 , true , false),
		new SchemaColumn("CalSQL", DataColumn.STRING, 4, 3000 , 0 , true , false),
		new SchemaColumn("Remark", DataColumn.STRING, 5, 500 , 0 , false , false)
	};

	public static final String _TableCode = "FMCalMode";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into FMCalMode values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update FMCalMode set CalCode=?,RiskCode=?,OutRiskCode=?,Type=?,CalSQL=?,Remark=? where CalCode=?";

	protected static final String _DeleteSQL = "delete from FMCalMode  where CalCode=?";

	protected static final String _FillAllSQL = "select * from FMCalMode  where CalCode=?";

	public FMCalModeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[6];
	}

	protected Schema newInstance(){
		return new FMCalModeSchema();
	}

	protected SchemaSet newSet(){
		return new FMCalModeSet();
	}

	public FMCalModeSet query() {
		return query(null, -1, -1);
	}

	public FMCalModeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public FMCalModeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public FMCalModeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (FMCalModeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){CalCode = (String)v;return;}
		if (i == 1){RiskCode = (String)v;return;}
		if (i == 2){OutRiskCode = (String)v;return;}
		if (i == 3){Type = (String)v;return;}
		if (i == 4){CalSQL = (String)v;return;}
		if (i == 5){Remark = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return CalCode;}
		if (i == 1){return RiskCode;}
		if (i == 2){return OutRiskCode;}
		if (i == 3){return Type;}
		if (i == 4){return CalSQL;}
		if (i == 5){return Remark;}
		return null;
	}

	/**
	* 获取字段CalCode的值，该字段的<br>
	* 字段名称 :算法编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1-3位：险种号码 4位：业务类型（该位不一定按照此规则来编写，现在赔付都是3） 1 承保 2 领取 3 赔付 4 现金价值 5 单证描述 5-6位 顺序号<br>
	*/
	public String getCalCode() {
		return CalCode;
	}

	/**
	* 设置字段CalCode的值，该字段的<br>
	* 字段名称 :算法编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	1-3位：险种号码 4位：业务类型（该位不一定按照此规则来编写，现在赔付都是3） 1 承保 2 领取 3 赔付 4 现金价值 5 单证描述 5-6位 顺序号<br>
	*/
	public void setCalCode(String calCode) {
		this.CalCode = calCode;
    }

	/**
	* 获取字段RiskCode的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getRiskCode() {
		return RiskCode;
	}

	/**
	* 设置字段RiskCode的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRiskCode(String riskCode) {
		this.RiskCode = riskCode;
    }

	/**
	* 获取字段OutRiskCode的值，该字段的<br>
	* 字段名称 :外部产编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOutRiskCode() {
		return OutRiskCode;
	}

	/**
	* 设置字段OutRiskCode的值，该字段的<br>
	* 字段名称 :外部产编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOutRiskCode(String outRiskCode) {
		this.OutRiskCode = outRiskCode;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :算法类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :算法类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段CalSQL的值，该字段的<br>
	* 字段名称 :CalSQL<br>
	* 数据类型 :varchar(3000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCalSQL() {
		return CalSQL;
	}

	/**
	* 设置字段CalSQL的值，该字段的<br>
	* 字段名称 :CalSQL<br>
	* 数据类型 :varchar(3000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCalSQL(String calSQL) {
		this.CalSQL = calSQL;
    }

	/**
	* 获取字段Remark的值，该字段的<br>
	* 字段名称 :算法描述<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark() {
		return Remark;
	}

	/**
	* 设置字段Remark的值，该字段的<br>
	* 字段名称 :算法描述<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark(String remark) {
		this.Remark = remark;
    }

}