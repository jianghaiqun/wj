package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

import java.math.BigDecimal;

import java.util.Date;

import java.math.BigDecimal;

/**
 * 表名称：游记总体统计<br>
 * 表代码：TravelNotesStatistics<br>
 * 表主键：id<br>
 */
public class TravelNotesStatisticsSchema extends Schema {
	private String id;

	private String statisticalTime;

	private String flow;

	private Integer orderNum;

	private BigDecimal sumPrem;

	private String buyAuthor;

	private Integer authorOrderNum;

	private BigDecimal authorSumPrem;

	private String branchInnerCode;

	private Integer convertNum;

	private String remark1;

	private String remark2;

	private String remark3;

	private Date createDate;

	private String createUser;

	private Date modifyDate;

	private String modifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("statisticalTime", DataColumn.STRING, 1, 10 , 0 , false , false),
		new SchemaColumn("flow", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("orderNum", DataColumn.INTEGER, 3, 11 , 0 , false , false),
		new SchemaColumn("sumPrem", DataColumn.BIGDECIMAL, 4, 10 , 0 , false , false),
		new SchemaColumn("buyAuthor", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("authorOrderNum", DataColumn.INTEGER, 6, 11 , 0 , false , false),
		new SchemaColumn("authorSumPrem", DataColumn.BIGDECIMAL, 7, 10 , 0 , false , false),
		new SchemaColumn("branchInnerCode", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("convertNum", DataColumn.INTEGER, 9, 11 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 14, 100 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 16, 100 , 0 , false , false)
	};

	public static final String _TableCode = "TravelNotesStatistics";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into TravelNotesStatistics values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update TravelNotesStatistics set id=?,statisticalTime=?,flow=?,orderNum=?,sumPrem=?,buyAuthor=?,authorOrderNum=?,authorSumPrem=?,branchInnerCode=?,convertNum=?,remark1=?,remark2=?,remark3=?,createDate=?,createUser=?,modifyDate=?,modifyUser=? where id=?";

	protected static final String _DeleteSQL = "delete from TravelNotesStatistics  where id=?";

	protected static final String _FillAllSQL = "select * from TravelNotesStatistics  where id=?";

	public TravelNotesStatisticsSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[17];
	}

	protected Schema newInstance(){
		return new TravelNotesStatisticsSchema();
	}

	protected SchemaSet newSet(){
		return new TravelNotesStatisticsSet();
	}

	public TravelNotesStatisticsSet query() {
		return query(null, -1, -1);
	}

	public TravelNotesStatisticsSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public TravelNotesStatisticsSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public TravelNotesStatisticsSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (TravelNotesStatisticsSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){statisticalTime = (String)v;return;}
		if (i == 2){flow = (String)v;return;}
		if (i == 3){if(v==null){orderNum = null;}else{orderNum = new Integer(v.toString());}return;}
		if (i == 4){if(v==null){sumPrem = null;}else{sumPrem =  ((BigDecimal)v) ;}return;}
		if (i == 5){buyAuthor = (String)v;return;}
		if (i == 6){if(v==null){authorOrderNum = null;}else{authorOrderNum = new Integer(v.toString());}return;}
		if (i == 7){if(v==null){authorSumPrem = null;}else{authorSumPrem =  ((BigDecimal)v) ;}return;}
		if (i == 8){branchInnerCode = (String)v;return;}
		if (i == 9){if(v==null){convertNum = null;}else{convertNum = new Integer(v.toString());}return;}
		if (i == 10){remark1 = (String)v;return;}
		if (i == 11){remark2 = (String)v;return;}
		if (i == 12){remark3 = (String)v;return;}
		if (i == 13){createDate = (Date)v;return;}
		if (i == 14){createUser = (String)v;return;}
		if (i == 15){modifyDate = (Date)v;return;}
		if (i == 16){modifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return statisticalTime;}
		if (i == 2){return flow;}
		if (i == 3){return orderNum;}
		if (i == 4){return sumPrem;}
		if (i == 5){return buyAuthor;}
		if (i == 6){return authorOrderNum;}
		if (i == 7){return authorSumPrem;}
		if (i == 8){return branchInnerCode;}
		if (i == 9){return convertNum;}
		if (i == 10){return remark1;}
		if (i == 11){return remark2;}
		if (i == 12){return remark3;}
		if (i == 13){return createDate;}
		if (i == 14){return createUser;}
		if (i == 15){return modifyDate;}
		if (i == 16){return modifyUser;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段statisticalTime的值，该字段的<br>
	* 字段名称 :统计时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstatisticalTime() {
		return statisticalTime;
	}

	/**
	* 设置字段statisticalTime的值，该字段的<br>
	* 字段名称 :统计时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatisticalTime(String statisticalTime) {
		this.statisticalTime = statisticalTime;
    }

	/**
	* 获取字段flow的值，该字段的<br>
	* 字段名称 :流量<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getflow() {
		return flow;
	}

	/**
	* 设置字段flow的值，该字段的<br>
	* 字段名称 :流量<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setflow(String flow) {
		this.flow = flow;
    }

	/**
	* 获取字段orderNum的值，该字段的<br>
	* 字段名称 :订单量<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getorderNum() {
		if(orderNum==null){return 0;}
		return orderNum.intValue();
	}

	/**
	* 设置字段orderNum的值，该字段的<br>
	* 字段名称 :订单量<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderNum(int orderNum) {
		this.orderNum = new Integer(orderNum);
    }

	/**
	* 设置字段orderNum的值，该字段的<br>
	* 字段名称 :订单量<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderNum(String orderNum) {
		if (orderNum == null){
			this.orderNum = null;
			return;
		}
		this.orderNum = new Integer(orderNum);
    }

	/**
	* 获取字段sumPrem的值，该字段的<br>
	* 字段名称 :总保费<br>
	* 数据类型 :decimal(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getsumPrem() {
		return sumPrem;
	}

	/**
	* 设置字段sumPrem的值，该字段的<br>
	* 字段名称 :总保费<br>
	* 数据类型 :decimal(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsumPrem(BigDecimal sumPrem) {
		this.sumPrem = sumPrem;
    }

	/**
	* 获取字段buyAuthor的值，该字段的<br>
	* 字段名称 :购买作者<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbuyAuthor() {
		return buyAuthor;
	}

	/**
	* 设置字段buyAuthor的值，该字段的<br>
	* 字段名称 :购买作者<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbuyAuthor(String buyAuthor) {
		this.buyAuthor = buyAuthor;
    }

	/**
	* 获取字段authorOrderNum的值，该字段的<br>
	* 字段名称 :作者订单量<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getauthorOrderNum() {
		if(authorOrderNum==null){return 0;}
		return authorOrderNum.intValue();
	}

	/**
	* 设置字段authorOrderNum的值，该字段的<br>
	* 字段名称 :作者订单量<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setauthorOrderNum(int authorOrderNum) {
		this.authorOrderNum = new Integer(authorOrderNum);
    }

	/**
	* 设置字段authorOrderNum的值，该字段的<br>
	* 字段名称 :作者订单量<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setauthorOrderNum(String authorOrderNum) {
		if (authorOrderNum == null){
			this.authorOrderNum = null;
			return;
		}
		this.authorOrderNum = new Integer(authorOrderNum);
    }

	/**
	* 获取字段authorSumPrem的值，该字段的<br>
	* 字段名称 :作者保费<br>
	* 数据类型 :decimal(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getauthorSumPrem() {
		return authorSumPrem;
	}

	/**
	* 设置字段authorSumPrem的值，该字段的<br>
	* 字段名称 :作者保费<br>
	* 数据类型 :decimal(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setauthorSumPrem(BigDecimal authorSumPrem) {
		this.authorSumPrem = authorSumPrem;
    }

	/**
	* 获取字段branchInnerCode的值，该字段的<br>
	* 字段名称 :联系人所属机构编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbranchInnerCode() {
		return branchInnerCode;
	}

	/**
	* 设置字段branchInnerCode的值，该字段的<br>
	* 字段名称 :联系人所属机构编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbranchInnerCode(String branchInnerCode) {
		this.branchInnerCode = branchInnerCode;
    }

	/**
	* 获取字段convertNum的值，该字段的<br>
	* 字段名称 :辅助转化数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getconvertNum() {
		if(convertNum==null){return 0;}
		return convertNum.intValue();
	}

	/**
	* 设置字段convertNum的值，该字段的<br>
	* 字段名称 :辅助转化数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setconvertNum(int convertNum) {
		this.convertNum = new Integer(convertNum);
    }

	/**
	* 设置字段convertNum的值，该字段的<br>
	* 字段名称 :辅助转化数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setconvertNum(String convertNum) {
		if (convertNum == null){
			this.convertNum = null;
			return;
		}
		this.convertNum = new Integer(convertNum);
    }

	/**
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

	/**
	* 获取字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark2() {
		return remark2;
	}

	/**
	* 设置字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark2(String remark2) {
		this.remark2 = remark2;
    }

	/**
	* 获取字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark3() {
		return remark3;
	}

	/**
	* 设置字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark3(String remark3) {
		this.remark3 = remark3;
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
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
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
	* 获取字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
    }

}