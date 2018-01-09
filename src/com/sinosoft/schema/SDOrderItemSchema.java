package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：订单项表<br>
 * 表代码：SDOrderItem<br>
 * 表主键：id<br>
 */
public class SDOrderItemSchema extends Schema {
	private String id;

	private Date createDate;

	private Date modifyDate;

	private String orderitemSn;

	private String orderSn;

	private String orderPoint;

	private String pointStatus;

	private String channelCode;

	private String channelAgentCode;

	private String typeFlag;

	private String sdorder_id;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , true , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("orderitemSn", DataColumn.STRING, 3, 25 , 0 , true , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 4, 25 , 0 , true , false),
		new SchemaColumn("orderPoint", DataColumn.STRING, 5, 20 , 0 , true , false),
		new SchemaColumn("pointStatus", DataColumn.STRING, 6, 1 , 0 , true , false),
		new SchemaColumn("channelCode", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("channelAgentCode", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("typeFlag", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("sdorder_id", DataColumn.STRING, 10, 32 , 0 , false , false)
	};

	public static final String _TableCode = "SDOrderItem";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDOrderItem values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDOrderItem set id=?,createDate=?,modifyDate=?,orderitemSn=?,orderSn=?,orderPoint=?,pointStatus=?,channelCode=?,channelAgentCode=?,typeFlag=?,sdorder_id=? where id=?";

	protected static final String _DeleteSQL = "delete from SDOrderItem  where id=?";

	protected static final String _FillAllSQL = "select * from SDOrderItem  where id=?";

	public SDOrderItemSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[11];
	}

	protected Schema newInstance(){
		return new SDOrderItemSchema();
	}

	protected SchemaSet newSet(){
		return new SDOrderItemSet();
	}

	public SDOrderItemSet query() {
		return query(null, -1, -1);
	}

	public SDOrderItemSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDOrderItemSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDOrderItemSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDOrderItemSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){createDate = (Date)v;return;}
		if (i == 2){modifyDate = (Date)v;return;}
		if (i == 3){orderitemSn = (String)v;return;}
		if (i == 4){orderSn = (String)v;return;}
		if (i == 5){orderPoint = (String)v;return;}
		if (i == 6){pointStatus = (String)v;return;}
		if (i == 7){channelCode = (String)v;return;}
		if (i == 8){channelAgentCode = (String)v;return;}
		if (i == 9){typeFlag = (String)v;return;}
		if (i == 10){sdorder_id = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return createDate;}
		if (i == 2){return modifyDate;}
		if (i == 3){return orderitemSn;}
		if (i == 4){return orderSn;}
		if (i == 5){return orderPoint;}
		if (i == 6){return pointStatus;}
		if (i == 7){return channelCode;}
		if (i == 8){return channelAgentCode;}
		if (i == 9){return typeFlag;}
		if (i == 10){return sdorder_id;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段orderitemSn的值，该字段的<br>
	* 字段名称 :订单项目编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getorderitemSn() {
		return orderitemSn;
	}

	/**
	* 设置字段orderitemSn的值，该字段的<br>
	* 字段名称 :订单项目编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setorderitemSn(String orderitemSn) {
		this.orderitemSn = orderitemSn;
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :订单编号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段orderPoint的值，该字段的<br>
	* 字段名称 :订单积分<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getorderPoint() {
		return orderPoint;
	}

	/**
	* 设置字段orderPoint的值，该字段的<br>
	* 字段名称 :订单积分<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setorderPoint(String orderPoint) {
		this.orderPoint = orderPoint;
    }

	/**
	* 获取字段pointStatus的值，该字段的<br>
	* 字段名称 :积分状态(与会员关联使用)<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getpointStatus() {
		return pointStatus;
	}

	/**
	* 设置字段pointStatus的值，该字段的<br>
	* 字段名称 :积分状态(与会员关联使用)<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setpointStatus(String pointStatus) {
		this.pointStatus = pointStatus;
    }

	/**
	* 获取字段channelCode的值，该字段的<br>
	* 字段名称 :渠道编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchannelCode() {
		return channelCode;
	}

	/**
	* 设置字段channelCode的值，该字段的<br>
	* 字段名称 :渠道编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchannelCode(String channelCode) {
		this.channelCode = channelCode;
    }

	/**
	* 获取字段channelAgentCode的值，该字段的<br>
	* 字段名称 :渠道人员编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchannelAgentCode() {
		return channelAgentCode;
	}

	/**
	* 设置字段channelAgentCode的值，该字段的<br>
	* 字段名称 :渠道人员编号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchannelAgentCode(String channelAgentCode) {
		this.channelAgentCode = channelAgentCode;
    }

	/**
	* 获取字段typeFlag的值，该字段的<br>
	* 字段名称 :投保流程去头尾标志<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettypeFlag() {
		return typeFlag;
	}

	/**
	* 设置字段typeFlag的值，该字段的<br>
	* 字段名称 :投保流程去头尾标志<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
    }

	/**
	* 获取字段sdorder_id的值，该字段的<br>
	* 字段名称 :sdorderId<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsdorder_id() {
		return sdorder_id;
	}

	/**
	* 设置字段sdorder_id的值，该字段的<br>
	* 字段名称 :sdorderId<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsdorder_id(String sdorder_id) {
		this.sdorder_id = sdorder_id;
    }

}