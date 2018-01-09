package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：会员团单表<br>
 * 表代码：MemberGroup<br>
 * 表主键：memberId<br>
 */
public class MemberGroupSchema extends Schema {
	private String memberId;

	private Integer groupCount;

	private Date statisticsEndDate;

	private String remark1;

	private String remark2;

	private Date createDate;

	private String createUser;

	private Date modifyDate;

	private String modifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("memberId", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("groupCount", DataColumn.INTEGER, 1, 11 , 0 , false , false),
		new SchemaColumn("statisticsEndDate", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 8, 20 , 0 , false , false)
	};

	public static final String _TableCode = "MemberGroup";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into MemberGroup values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update MemberGroup set memberId=?,groupCount=?,statisticsEndDate=?,remark1=?,remark2=?,createDate=?,createUser=?,modifyDate=?,modifyUser=? where memberId=?";

	protected static final String _DeleteSQL = "delete from MemberGroup  where memberId=?";

	protected static final String _FillAllSQL = "select * from MemberGroup  where memberId=?";

	public MemberGroupSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[9];
	}

	protected Schema newInstance(){
		return new MemberGroupSchema();
	}

	protected SchemaSet newSet(){
		return new MemberGroupSet();
	}

	public MemberGroupSet query() {
		return query(null, -1, -1);
	}

	public MemberGroupSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public MemberGroupSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public MemberGroupSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (MemberGroupSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){memberId = (String)v;return;}
		if (i == 1){if(v==null){groupCount = null;}else{groupCount = new Integer(v.toString());}return;}
		if (i == 2){statisticsEndDate = (Date)v;return;}
		if (i == 3){remark1 = (String)v;return;}
		if (i == 4){remark2 = (String)v;return;}
		if (i == 5){createDate = (Date)v;return;}
		if (i == 6){createUser = (String)v;return;}
		if (i == 7){modifyDate = (Date)v;return;}
		if (i == 8){modifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return memberId;}
		if (i == 1){return groupCount;}
		if (i == 2){return statisticsEndDate;}
		if (i == 3){return remark1;}
		if (i == 4){return remark2;}
		if (i == 5){return createDate;}
		if (i == 6){return createUser;}
		if (i == 7){return modifyDate;}
		if (i == 8){return modifyUser;}
		return null;
	}

	/**
	* 获取字段memberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getmemberId() {
		return memberId;
	}

	/**
	* 设置字段memberId的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setmemberId(String memberId) {
		this.memberId = memberId;
    }

	/**
	* 获取字段groupCount的值，该字段的<br>
	* 字段名称 :团单数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getgroupCount() {
		if(groupCount==null){return 0;}
		return groupCount.intValue();
	}

	/**
	* 设置字段groupCount的值，该字段的<br>
	* 字段名称 :团单数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgroupCount(int groupCount) {
		this.groupCount = new Integer(groupCount);
    }

	/**
	* 设置字段groupCount的值，该字段的<br>
	* 字段名称 :团单数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgroupCount(String groupCount) {
		if (groupCount == null){
			this.groupCount = null;
			return;
		}
		this.groupCount = new Integer(groupCount);
    }

	/**
	* 获取字段statisticsEndDate的值，该字段的<br>
	* 字段名称 :统计截止时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getstatisticsEndDate() {
		return statisticsEndDate;
	}

	/**
	* 设置字段statisticsEndDate的值，该字段的<br>
	* 字段名称 :统计截止时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatisticsEndDate(Date statisticsEndDate) {
		this.statisticsEndDate = statisticsEndDate;
    }

	/**
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :备份字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :备份字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

	/**
	* 获取字段remark2的值，该字段的<br>
	* 字段名称 :备份字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark2() {
		return remark2;
	}

	/**
	* 设置字段remark2的值，该字段的<br>
	* 字段名称 :备份字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark2(String remark2) {
		this.remark2 = remark2;
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
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(20)<br>
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
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
    }

}