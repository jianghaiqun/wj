package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：客服呼出记录汇总<br>
 * 表代码：ServiceCallCollection<br>
 * 表主键：id<br>
 */
public class ServiceCallCollectionSchema extends Schema {
	private String id;

	private String oldOrderSn;

	private String newOrderSn;

	private String callConnect;

	private String callCount;

	private Integer continueStatus;

	private Date lastCallTime;

	private Date createDate;

	private Date modifyDate;

	private String memberId;

	private String remark01;

	private String remark02;

	private String remark03;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("oldOrderSn", DataColumn.STRING, 1, 25 , 0 , false , false),
		new SchemaColumn("newOrderSn", DataColumn.STRING, 2, 25 , 0 , false , false),
		new SchemaColumn("callConnect", DataColumn.STRING, 3, 5 , 0 , false , false),
		new SchemaColumn("callCount", DataColumn.STRING, 4, 5 , 0 , false , false),
		new SchemaColumn("continueStatus", DataColumn.INTEGER, 5, 2 , 0 , false , false),
		new SchemaColumn("lastCallTime", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("memberId", DataColumn.STRING, 9, 32 , 0 , false , false),
		new SchemaColumn("remark01", DataColumn.STRING, 10, 256 , 0 , false , false),
		new SchemaColumn("remark02", DataColumn.STRING, 11, 256 , 0 , false , false),
		new SchemaColumn("remark03", DataColumn.STRING, 12, 256 , 0 , false , false)
	};

	public static final String _TableCode = "ServiceCallCollection";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ServiceCallCollection values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ServiceCallCollection set id=?,oldOrderSn=?,newOrderSn=?,callConnect=?,callCount=?,continueStatus=?,lastCallTime=?,createDate=?,modifyDate=?,memberId=?,remark01=?,remark02=?,remark03=? where id=?";

	protected static final String _DeleteSQL = "delete from ServiceCallCollection  where id=?";

	protected static final String _FillAllSQL = "select * from ServiceCallCollection  where id=?";

	public ServiceCallCollectionSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[13];
	}

	protected Schema newInstance(){
		return new ServiceCallCollectionSchema();
	}

	protected SchemaSet newSet(){
		return new ServiceCallCollectionSet();
	}

	public ServiceCallCollectionSet query() {
		return query(null, -1, -1);
	}

	public ServiceCallCollectionSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ServiceCallCollectionSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ServiceCallCollectionSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ServiceCallCollectionSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){oldOrderSn = (String)v;return;}
		if (i == 2){newOrderSn = (String)v;return;}
		if (i == 3){callConnect = (String)v;return;}
		if (i == 4){callCount = (String)v;return;}
		if (i == 5){if(v==null){continueStatus = null;}else{continueStatus = new Integer(v.toString());}return;}
		if (i == 6){lastCallTime = (Date)v;return;}
		if (i == 7){createDate = (Date)v;return;}
		if (i == 8){modifyDate = (Date)v;return;}
		if (i == 9){memberId = (String)v;return;}
		if (i == 10){remark01 = (String)v;return;}
		if (i == 11){remark02 = (String)v;return;}
		if (i == 12){remark03 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return oldOrderSn;}
		if (i == 2){return newOrderSn;}
		if (i == 3){return callConnect;}
		if (i == 4){return callCount;}
		if (i == 5){return continueStatus;}
		if (i == 6){return lastCallTime;}
		if (i == 7){return createDate;}
		if (i == 8){return modifyDate;}
		if (i == 9){return memberId;}
		if (i == 10){return remark01;}
		if (i == 11){return remark02;}
		if (i == 12){return remark03;}
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
	* 获取字段oldOrderSn的值，该字段的<br>
	* 字段名称 :旧订单号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getoldOrderSn() {
		return oldOrderSn;
	}

	/**
	* 设置字段oldOrderSn的值，该字段的<br>
	* 字段名称 :旧订单号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoldOrderSn(String oldOrderSn) {
		this.oldOrderSn = oldOrderSn;
    }

	/**
	* 获取字段newOrderSn的值，该字段的<br>
	* 字段名称 :新订单号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getnewOrderSn() {
		return newOrderSn;
	}

	/**
	* 设置字段newOrderSn的值，该字段的<br>
	* 字段名称 :新订单号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setnewOrderSn(String newOrderSn) {
		this.newOrderSn = newOrderSn;
    }

	/**
	* 获取字段callConnect的值，该字段的<br>
	* 字段名称 :是否接听<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcallConnect() {
		return callConnect;
	}

	/**
	* 设置字段callConnect的值，该字段的<br>
	* 字段名称 :是否接听<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcallConnect(String callConnect) {
		this.callConnect = callConnect;
    }

	/**
	* 获取字段callCount的值，该字段的<br>
	* 字段名称 :呼出次数<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcallCount() {
		return callCount;
	}

	/**
	* 设置字段callCount的值，该字段的<br>
	* 字段名称 :呼出次数<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcallCount(String callCount) {
		this.callCount = callCount;
    }

	/**
	* 获取字段continueStatus的值，该字段的<br>
	* 字段名称 :续保状态<br>
	* 数据类型 :int(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getcontinueStatus() {
		if(continueStatus==null){return 0;}
		return continueStatus.intValue();
	}

	/**
	* 设置字段continueStatus的值，该字段的<br>
	* 字段名称 :续保状态<br>
	* 数据类型 :int(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontinueStatus(int continueStatus) {
		this.continueStatus = new Integer(continueStatus);
    }

	/**
	* 设置字段continueStatus的值，该字段的<br>
	* 字段名称 :续保状态<br>
	* 数据类型 :int(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontinueStatus(String continueStatus) {
		if (continueStatus == null){
			this.continueStatus = null;
			return;
		}
		this.continueStatus = new Integer(continueStatus);
    }

	/**
	* 获取字段lastCallTime的值，该字段的<br>
	* 字段名称 :最后呼出时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getlastCallTime() {
		return lastCallTime;
	}

	/**
	* 设置字段lastCallTime的值，该字段的<br>
	* 字段名称 :最后呼出时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setlastCallTime(Date lastCallTime) {
		this.lastCallTime = lastCallTime;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

	/**
	* 获取字段memberId的值，该字段的<br>
	* 字段名称 :会员编号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemberId() {
		return memberId;
	}

	/**
	* 设置字段memberId的值，该字段的<br>
	* 字段名称 :会员编号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemberId(String memberId) {
		this.memberId = memberId;
    }

	/**
	* 获取字段remark01的值，该字段的<br>
	* 字段名称 :扩展字段01<br>
	* 数据类型 :varchar(256)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark01() {
		return remark01;
	}

	/**
	* 设置字段remark01的值，该字段的<br>
	* 字段名称 :扩展字段01<br>
	* 数据类型 :varchar(256)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark01(String remark01) {
		this.remark01 = remark01;
    }

	/**
	* 获取字段remark02的值，该字段的<br>
	* 字段名称 :扩展字段2<br>
	* 数据类型 :varchar(256)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark02() {
		return remark02;
	}

	/**
	* 设置字段remark02的值，该字段的<br>
	* 字段名称 :扩展字段2<br>
	* 数据类型 :varchar(256)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark02(String remark02) {
		this.remark02 = remark02;
    }

	/**
	* 获取字段remark03的值，该字段的<br>
	* 字段名称 :扩展字段3<br>
	* 数据类型 :varchar(256)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark03() {
		return remark03;
	}

	/**
	* 设置字段remark03的值，该字段的<br>
	* 字段名称 :扩展字段3<br>
	* 数据类型 :varchar(256)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark03(String remark03) {
		this.remark03 = remark03;
    }

}