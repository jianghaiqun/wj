package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：保费计划信息<br>
 * 表代码：PlanAmountInfo<br>
 * 表主键：PlanYear, RiskType<br>
 */
public class PlanAmountInfoSchema extends Schema {
	private String PlanYear;

	private String RiskType;

	private String RiskTypeName;

	private String MonP1;

	private String MonF1;

	private String MonP2;

	private String MonF2;

	private String MonP3;

	private String MonF3;

	private String MonP4;

	private String MonF4;

	private String MonP5;

	private String MonF5;

	private String MonP6;

	private String MonF6;

	private String MonP7;

	private String MonF7;

	private String MonP8;

	private String MonF8;

	private String MonP9;

	private String MonF9;

	private String MonP10;

	private String MonF10;

	private String MonP11;

	private String MonF11;

	private String MonP12;

	private String MonF12;

	private String AllYearP;

	private String AllYearF;

	private Date CreateDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("PlanYear", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("RiskType", DataColumn.STRING, 1, 20 , 0 , true , true),
		new SchemaColumn("RiskTypeName", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("MonP1", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("MonF1", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("MonP2", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("MonF2", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("MonP3", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("MonF3", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("MonP4", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("MonF4", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("MonP5", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("MonF5", DataColumn.STRING, 12, 20 , 0 , false , false),
		new SchemaColumn("MonP6", DataColumn.STRING, 13, 20 , 0 , false , false),
		new SchemaColumn("MonF6", DataColumn.STRING, 14, 20 , 0 , false , false),
		new SchemaColumn("MonP7", DataColumn.STRING, 15, 20 , 0 , false , false),
		new SchemaColumn("MonF7", DataColumn.STRING, 16, 20 , 0 , false , false),
		new SchemaColumn("MonP8", DataColumn.STRING, 17, 20 , 0 , false , false),
		new SchemaColumn("MonF8", DataColumn.STRING, 18, 20 , 0 , false , false),
		new SchemaColumn("MonP9", DataColumn.STRING, 19, 20 , 0 , false , false),
		new SchemaColumn("MonF9", DataColumn.STRING, 20, 20 , 0 , false , false),
		new SchemaColumn("MonP10", DataColumn.STRING, 21, 20 , 0 , false , false),
		new SchemaColumn("MonF10", DataColumn.STRING, 22, 20 , 0 , false , false),
		new SchemaColumn("MonP11", DataColumn.STRING, 23, 20 , 0 , false , false),
		new SchemaColumn("MonF11", DataColumn.STRING, 24, 20 , 0 , false , false),
		new SchemaColumn("MonP12", DataColumn.STRING, 25, 20 , 0 , false , false),
		new SchemaColumn("MonF12", DataColumn.STRING, 26, 20 , 0 , false , false),
		new SchemaColumn("AllYearP", DataColumn.STRING, 27, 20 , 0 , false , false),
		new SchemaColumn("AllYearF", DataColumn.STRING, 28, 20 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 29, 0 , 0 , false , false)
	};

	public static final String _TableCode = "PlanAmountInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into PlanAmountInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update PlanAmountInfo set PlanYear=?,RiskType=?,RiskTypeName=?,MonP1=?,MonF1=?,MonP2=?,MonF2=?,MonP3=?,MonF3=?,MonP4=?,MonF4=?,MonP5=?,MonF5=?,MonP6=?,MonF6=?,MonP7=?,MonF7=?,MonP8=?,MonF8=?,MonP9=?,MonF9=?,MonP10=?,MonF10=?,MonP11=?,MonF11=?,MonP12=?,MonF12=?,AllYearP=?,AllYearF=?,CreateDate=? where PlanYear=? and RiskType=?";

	protected static final String _DeleteSQL = "delete from PlanAmountInfo  where PlanYear=? and RiskType=?";

	protected static final String _FillAllSQL = "select * from PlanAmountInfo  where PlanYear=? and RiskType=?";

	public PlanAmountInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[30];
	}

	protected Schema newInstance(){
		return new PlanAmountInfoSchema();
	}

	protected SchemaSet newSet(){
		return new PlanAmountInfoSet();
	}

	public PlanAmountInfoSet query() {
		return query(null, -1, -1);
	}

	public PlanAmountInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public PlanAmountInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public PlanAmountInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (PlanAmountInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){PlanYear = (String)v;return;}
		if (i == 1){RiskType = (String)v;return;}
		if (i == 2){RiskTypeName = (String)v;return;}
		if (i == 3){MonP1 = (String)v;return;}
		if (i == 4){MonF1 = (String)v;return;}
		if (i == 5){MonP2 = (String)v;return;}
		if (i == 6){MonF2 = (String)v;return;}
		if (i == 7){MonP3 = (String)v;return;}
		if (i == 8){MonF3 = (String)v;return;}
		if (i == 9){MonP4 = (String)v;return;}
		if (i == 10){MonF4 = (String)v;return;}
		if (i == 11){MonP5 = (String)v;return;}
		if (i == 12){MonF5 = (String)v;return;}
		if (i == 13){MonP6 = (String)v;return;}
		if (i == 14){MonF6 = (String)v;return;}
		if (i == 15){MonP7 = (String)v;return;}
		if (i == 16){MonF7 = (String)v;return;}
		if (i == 17){MonP8 = (String)v;return;}
		if (i == 18){MonF8 = (String)v;return;}
		if (i == 19){MonP9 = (String)v;return;}
		if (i == 20){MonF9 = (String)v;return;}
		if (i == 21){MonP10 = (String)v;return;}
		if (i == 22){MonF10 = (String)v;return;}
		if (i == 23){MonP11 = (String)v;return;}
		if (i == 24){MonF11 = (String)v;return;}
		if (i == 25){MonP12 = (String)v;return;}
		if (i == 26){MonF12 = (String)v;return;}
		if (i == 27){AllYearP = (String)v;return;}
		if (i == 28){AllYearF = (String)v;return;}
		if (i == 29){CreateDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return PlanYear;}
		if (i == 1){return RiskType;}
		if (i == 2){return RiskTypeName;}
		if (i == 3){return MonP1;}
		if (i == 4){return MonF1;}
		if (i == 5){return MonP2;}
		if (i == 6){return MonF2;}
		if (i == 7){return MonP3;}
		if (i == 8){return MonF3;}
		if (i == 9){return MonP4;}
		if (i == 10){return MonF4;}
		if (i == 11){return MonP5;}
		if (i == 12){return MonF5;}
		if (i == 13){return MonP6;}
		if (i == 14){return MonF6;}
		if (i == 15){return MonP7;}
		if (i == 16){return MonF7;}
		if (i == 17){return MonP8;}
		if (i == 18){return MonF8;}
		if (i == 19){return MonP9;}
		if (i == 20){return MonF9;}
		if (i == 21){return MonP10;}
		if (i == 22){return MonF10;}
		if (i == 23){return MonP11;}
		if (i == 24){return MonF11;}
		if (i == 25){return MonP12;}
		if (i == 26){return MonF12;}
		if (i == 27){return AllYearP;}
		if (i == 28){return AllYearF;}
		if (i == 29){return CreateDate;}
		return null;
	}

	/**
	* 获取字段PlanYear的值，该字段的<br>
	* 字段名称 :年份<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getPlanYear() {
		return PlanYear;
	}

	/**
	* 设置字段PlanYear的值，该字段的<br>
	* 字段名称 :年份<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setPlanYear(String planYear) {
		this.PlanYear = planYear;
    }

	/**
	* 获取字段RiskType的值，该字段的<br>
	* 字段名称 :分项类别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getRiskType() {
		return RiskType;
	}

	/**
	* 设置字段RiskType的值，该字段的<br>
	* 字段名称 :分项类别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setRiskType(String riskType) {
		this.RiskType = riskType;
    }

	/**
	* 获取字段RiskTypeName的值，该字段的<br>
	* 字段名称 :分项类别名称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRiskTypeName() {
		return RiskTypeName;
	}

	/**
	* 设置字段RiskTypeName的值，该字段的<br>
	* 字段名称 :分项类别名称<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRiskTypeName(String riskTypeName) {
		this.RiskTypeName = riskTypeName;
    }

	/**
	* 获取字段MonP1的值，该字段的<br>
	* 字段名称 :一月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonP1() {
		return MonP1;
	}

	/**
	* 设置字段MonP1的值，该字段的<br>
	* 字段名称 :一月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonP1(String monP1) {
		this.MonP1 = monP1;
    }

	/**
	* 获取字段MonF1的值，该字段的<br>
	* 字段名称 :一月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonF1() {
		return MonF1;
	}

	/**
	* 设置字段MonF1的值，该字段的<br>
	* 字段名称 :一月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonF1(String monF1) {
		this.MonF1 = monF1;
    }

	/**
	* 获取字段MonP2的值，该字段的<br>
	* 字段名称 :二月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonP2() {
		return MonP2;
	}

	/**
	* 设置字段MonP2的值，该字段的<br>
	* 字段名称 :二月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonP2(String monP2) {
		this.MonP2 = monP2;
    }

	/**
	* 获取字段MonF2的值，该字段的<br>
	* 字段名称 :二月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonF2() {
		return MonF2;
	}

	/**
	* 设置字段MonF2的值，该字段的<br>
	* 字段名称 :二月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonF2(String monF2) {
		this.MonF2 = monF2;
    }

	/**
	* 获取字段MonP3的值，该字段的<br>
	* 字段名称 :三月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonP3() {
		return MonP3;
	}

	/**
	* 设置字段MonP3的值，该字段的<br>
	* 字段名称 :三月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonP3(String monP3) {
		this.MonP3 = monP3;
    }

	/**
	* 获取字段MonF3的值，该字段的<br>
	* 字段名称 :三月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonF3() {
		return MonF3;
	}

	/**
	* 设置字段MonF3的值，该字段的<br>
	* 字段名称 :三月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonF3(String monF3) {
		this.MonF3 = monF3;
    }

	/**
	* 获取字段MonP4的值，该字段的<br>
	* 字段名称 :四月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonP4() {
		return MonP4;
	}

	/**
	* 设置字段MonP4的值，该字段的<br>
	* 字段名称 :四月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonP4(String monP4) {
		this.MonP4 = monP4;
    }

	/**
	* 获取字段MonF4的值，该字段的<br>
	* 字段名称 :四月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonF4() {
		return MonF4;
	}

	/**
	* 设置字段MonF4的值，该字段的<br>
	* 字段名称 :四月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonF4(String monF4) {
		this.MonF4 = monF4;
    }

	/**
	* 获取字段MonP5的值，该字段的<br>
	* 字段名称 :五月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonP5() {
		return MonP5;
	}

	/**
	* 设置字段MonP5的值，该字段的<br>
	* 字段名称 :五月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonP5(String monP5) {
		this.MonP5 = monP5;
    }

	/**
	* 获取字段MonF5的值，该字段的<br>
	* 字段名称 :五月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonF5() {
		return MonF5;
	}

	/**
	* 设置字段MonF5的值，该字段的<br>
	* 字段名称 :五月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonF5(String monF5) {
		this.MonF5 = monF5;
    }

	/**
	* 获取字段MonP6的值，该字段的<br>
	* 字段名称 :六月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonP6() {
		return MonP6;
	}

	/**
	* 设置字段MonP6的值，该字段的<br>
	* 字段名称 :六月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonP6(String monP6) {
		this.MonP6 = monP6;
    }

	/**
	* 获取字段MonF6的值，该字段的<br>
	* 字段名称 :六月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonF6() {
		return MonF6;
	}

	/**
	* 设置字段MonF6的值，该字段的<br>
	* 字段名称 :六月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonF6(String monF6) {
		this.MonF6 = monF6;
    }

	/**
	* 获取字段MonP7的值，该字段的<br>
	* 字段名称 :七月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonP7() {
		return MonP7;
	}

	/**
	* 设置字段MonP7的值，该字段的<br>
	* 字段名称 :七月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonP7(String monP7) {
		this.MonP7 = monP7;
    }

	/**
	* 获取字段MonF7的值，该字段的<br>
	* 字段名称 :七月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonF7() {
		return MonF7;
	}

	/**
	* 设置字段MonF7的值，该字段的<br>
	* 字段名称 :七月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonF7(String monF7) {
		this.MonF7 = monF7;
    }

	/**
	* 获取字段MonP8的值，该字段的<br>
	* 字段名称 :八月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonP8() {
		return MonP8;
	}

	/**
	* 设置字段MonP8的值，该字段的<br>
	* 字段名称 :八月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonP8(String monP8) {
		this.MonP8 = monP8;
    }

	/**
	* 获取字段MonF8的值，该字段的<br>
	* 字段名称 :八月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonF8() {
		return MonF8;
	}

	/**
	* 设置字段MonF8的值，该字段的<br>
	* 字段名称 :八月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonF8(String monF8) {
		this.MonF8 = monF8;
    }

	/**
	* 获取字段MonP9的值，该字段的<br>
	* 字段名称 :九月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonP9() {
		return MonP9;
	}

	/**
	* 设置字段MonP9的值，该字段的<br>
	* 字段名称 :九月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonP9(String monP9) {
		this.MonP9 = monP9;
    }

	/**
	* 获取字段MonF9的值，该字段的<br>
	* 字段名称 :九月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonF9() {
		return MonF9;
	}

	/**
	* 设置字段MonF9的值，该字段的<br>
	* 字段名称 :九月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonF9(String monF9) {
		this.MonF9 = monF9;
    }

	/**
	* 获取字段MonP10的值，该字段的<br>
	* 字段名称 :十月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonP10() {
		return MonP10;
	}

	/**
	* 设置字段MonP10的值，该字段的<br>
	* 字段名称 :十月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonP10(String monP10) {
		this.MonP10 = monP10;
    }

	/**
	* 获取字段MonF10的值，该字段的<br>
	* 字段名称 :十月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonF10() {
		return MonF10;
	}

	/**
	* 设置字段MonF10的值，该字段的<br>
	* 字段名称 :十月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonF10(String monF10) {
		this.MonF10 = monF10;
    }

	/**
	* 获取字段MonP11的值，该字段的<br>
	* 字段名称 :十一月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonP11() {
		return MonP11;
	}

	/**
	* 设置字段MonP11的值，该字段的<br>
	* 字段名称 :十一月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonP11(String monP11) {
		this.MonP11 = monP11;
    }

	/**
	* 获取字段MonF11的值，该字段的<br>
	* 字段名称 :十一月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonF11() {
		return MonF11;
	}

	/**
	* 设置字段MonF11的值，该字段的<br>
	* 字段名称 :十一月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonF11(String monF11) {
		this.MonF11 = monF11;
    }

	/**
	* 获取字段MonP12的值，该字段的<br>
	* 字段名称 :十二月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonP12() {
		return MonP12;
	}

	/**
	* 设置字段MonP12的值，该字段的<br>
	* 字段名称 :十二月保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonP12(String monP12) {
		this.MonP12 = monP12;
    }

	/**
	* 获取字段MonF12的值，该字段的<br>
	* 字段名称 :十二月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMonF12() {
		return MonF12;
	}

	/**
	* 设置字段MonF12的值，该字段的<br>
	* 字段名称 :十二月手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMonF12(String monF12) {
		this.MonF12 = monF12;
    }

	/**
	* 获取字段AllYearP的值，该字段的<br>
	* 字段名称 :全年保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllYearP() {
		return AllYearP;
	}

	/**
	* 设置字段AllYearP的值，该字段的<br>
	* 字段名称 :全年保费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllYearP(String allYearP) {
		this.AllYearP = allYearP;
    }

	/**
	* 获取字段AllYearF的值，该字段的<br>
	* 字段名称 :全年手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllYearF() {
		return AllYearF;
	}

	/**
	* 设置字段AllYearF的值，该字段的<br>
	* 字段名称 :全年手续费计划<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllYearF(String allYearF) {
		this.AllYearF = allYearF;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

}