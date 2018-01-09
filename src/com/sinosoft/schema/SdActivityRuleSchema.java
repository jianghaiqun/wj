package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.math.BigDecimal;

/**
 * 表名称：活动规则表<br>
 * 表代码：SdActivityRule<br>
 * 表主键：ID<br>
 */
public class SdActivityRuleSchema extends Schema {
	private Integer ID;

	private String ActivitySn;

	private BigDecimal StartAmount;

	private BigDecimal EndAmount;

	private BigDecimal ActivityData;

	private String MemberRule;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.INTEGER, 0, 0 , 0 , true , true),
		new SchemaColumn("ActivitySn", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("StartAmount", DataColumn.BIGDECIMAL, 2, 20 , 0 , false , false),
		new SchemaColumn("EndAmount", DataColumn.BIGDECIMAL, 3, 20 , 0 , false , false),
		new SchemaColumn("ActivityData", DataColumn.BIGDECIMAL, 4, 20 , 0 , false , false),
		new SchemaColumn("MemberRule", DataColumn.STRING, 5, 20 , 0 , false , false)
	};

	public static final String _TableCode = "SdActivityRule";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SdActivityRule values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SdActivityRule set ID=?,ActivitySn=?,StartAmount=?,EndAmount=?,ActivityData=?,MemberRule=? where ID=?";

	protected static final String _DeleteSQL = "delete from SdActivityRule  where ID=?";

	protected static final String _FillAllSQL = "select * from SdActivityRule  where ID=?";

	public SdActivityRuleSchema(){
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
		return new SdActivityRuleSchema();
	}

	protected SchemaSet newSet(){
		return new SdActivityRuleSet();
	}

	public SdActivityRuleSet query() {
		return query(null, -1, -1);
	}

	public SdActivityRuleSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SdActivityRuleSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SdActivityRuleSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SdActivityRuleSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Integer(v.toString());}return;}
		if (i == 1){ActivitySn = (String)v;return;}
		if (i == 2){if(v==null){StartAmount = null;}else{StartAmount =  ((BigDecimal)v) ;}return;}
		if (i == 3){if(v==null){EndAmount = null;}else{EndAmount =  ((BigDecimal)v) ;}return;}
		if (i == 4){if(v==null){ActivityData = null;}else{ActivityData =  ((BigDecimal)v) ;}return;}
		if (i == 5){MemberRule = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ActivitySn;}
		if (i == 2){return StartAmount;}
		if (i == 3){return EndAmount;}
		if (i == 4){return ActivityData;}
		if (i == 5){return MemberRule;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public int getID() {
		if(ID==null){return 0;}
		return ID.intValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(int iD) {
		this.ID = new Integer(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Integer(iD);
    }

	/**
	* 获取字段ActivitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getActivitySn() {
		return ActivitySn;
	}

	/**
	* 设置字段ActivitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setActivitySn(String activitySn) {
		this.ActivitySn = activitySn;
    }

	/**
	* 获取字段StartAmount的值，该字段的<br>
	* 字段名称 :起始金额<br>
	* 数据类型 :decimal(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getStartAmount() {
		return StartAmount;
	}

	/**
	* 设置字段StartAmount的值，该字段的<br>
	* 字段名称 :起始金额<br>
	* 数据类型 :decimal(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStartAmount(BigDecimal startAmount) {
		this.StartAmount = startAmount;
    }

	/**
	* 获取字段EndAmount的值，该字段的<br>
	* 字段名称 :结束金额<br>
	* 数据类型 :decimal(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getEndAmount() {
		return EndAmount;
	}

	/**
	* 设置字段EndAmount的值，该字段的<br>
	* 字段名称 :结束金额<br>
	* 数据类型 :decimal(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEndAmount(BigDecimal endAmount) {
		this.EndAmount = endAmount;
    }

	/**
	* 获取字段ActivityData的值，该字段的<br>
	* 字段名称 :活动内容<br>
	* 数据类型 :decimal(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getActivityData() {
		return ActivityData;
	}

	/**
	* 设置字段ActivityData的值，该字段的<br>
	* 字段名称 :活动内容<br>
	* 数据类型 :decimal(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setActivityData(BigDecimal activityData) {
		this.ActivityData = activityData;
    }

	/**
	* 获取字段MemberRule的值，该字段的<br>
	* 字段名称 :适用会员级别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemberRule() {
		return MemberRule;
	}

	/**
	* 设置字段MemberRule的值，该字段的<br>
	* 字段名称 :适用会员级别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemberRule(String memberRule) {
		MemberRule = memberRule;
	}

}