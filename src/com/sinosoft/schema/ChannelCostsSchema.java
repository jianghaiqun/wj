package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：营销渠道费用维护表<br>
 * 表代码：ChannelCosts<br>
 * 表主键：ID<br>
 */
public class ChannelCostsSchema extends Schema {
	private String ID;

	private String CostType;

	private String SCost;

	private String TransNode;

	private String FixedCosts;

	private String ChannelCode;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("CostType", DataColumn.STRING, 1, 30 , 0 , false , false),
		new SchemaColumn("SCost", DataColumn.STRING, 2, 30 , 0 , false , false),
		new SchemaColumn("TransNode", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("FixedCosts", DataColumn.STRING, 4, 30 , 0 , false , false),
		new SchemaColumn("ChannelCode", DataColumn.STRING, 5, 30 , 0 , false , false)
	};

	public static final String _TableCode = "ChannelCosts";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ChannelCosts values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ChannelCosts set ID=?,CostType=?,SCost=?,TransNode=?,FixedCosts=?,ChannelCode=? where ID=?";

	protected static final String _DeleteSQL = "delete from ChannelCosts  where ID=?";

	protected static final String _FillAllSQL = "select * from ChannelCosts  where ID=?";

	public ChannelCostsSchema(){
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
		return new ChannelCostsSchema();
	}

	protected SchemaSet newSet(){
		return new ChannelCostsSet();
	}

	public ChannelCostsSet query() {
		return query(null, -1, -1);
	}

	public ChannelCostsSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ChannelCostsSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ChannelCostsSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ChannelCostsSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){CostType = (String)v;return;}
		if (i == 2){SCost = (String)v;return;}
		if (i == 3){TransNode = (String)v;return;}
		if (i == 4){FixedCosts = (String)v;return;}
		if (i == 5){ChannelCode = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return CostType;}
		if (i == 2){return SCost;}
		if (i == 3){return TransNode;}
		if (i == 4){return FixedCosts;}
		if (i == 5){return ChannelCode;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :主键<br>
	* 数据类型 :VARCHAR2(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :主键<br>
	* 数据类型 :VARCHAR2(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段CostType的值，该字段的<br>
	* 字段名称 :费用模式<br>
	* 数据类型 :VARCHAR2(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCostType() {
		return CostType;
	}

	/**
	* 设置字段CostType的值，该字段的<br>
	* 字段名称 :费用模式<br>
	* 数据类型 :VARCHAR2(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCostType(String costType) {
		this.CostType = costType;
    }

	/**
	* 获取字段SCost的值，该字段的<br>
	* 字段名称 :单价<br>
	* 数据类型 :VARCHAR2(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSCost() {
		return SCost;
	}

	/**
	* 设置字段SCost的值，该字段的<br>
	* 字段名称 :单价<br>
	* 数据类型 :VARCHAR2(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSCost(String sCost) {
		this.SCost = sCost;
    }

	/**
	* 获取字段TransNode的值，该字段的<br>
	* 字段名称 :转化节点<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTransNode() {
		return TransNode;
	}

	/**
	* 设置字段TransNode的值，该字段的<br>
	* 字段名称 :转化节点<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTransNode(String transNode) {
		this.TransNode = transNode;
    }

	/**
	* 获取字段FixedCosts的值，该字段的<br>
	* 字段名称 :固定费用<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFixedCosts() {
		return FixedCosts;
	}

	/**
	* 设置字段FixedCosts的值，该字段的<br>
	* 字段名称 :固定费用<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFixedCosts(String fixedCosts) {
		this.FixedCosts = fixedCosts;
    }

	/**
	* 获取字段ChannelCode的值，该字段的<br>
	* 字段名称 :渠道编码<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChannelCode() {
		return ChannelCode;
	}

	/**
	* 设置字段ChannelCode的值，该字段的<br>
	* 字段名称 :渠道编码<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChannelCode(String channelCode) {
		this.ChannelCode = channelCode;
    }

}