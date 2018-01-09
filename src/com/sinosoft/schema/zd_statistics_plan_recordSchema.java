package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.util.Date;

/**
 * 表名称：统计计划执行轨迹<br>
 * 表代码：zd_statistics_plan_record<br>
 * 表主键：id<br>
 */
public class zd_statistics_plan_recordSchema extends Schema {
	private Long id;

	private String download_file_name;

	private String download_file_path;

	private Integer download_status;

	private Integer query_status;

	private Date start_date;

	private Date end_date;

	private String remark;

	private Date create_datetime;

	private String prop1;

	private String prop2;

	private String prop3;

	private Long statistics_plan_id;

	private String manually_execute_user;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("download_file_name", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("download_file_path", DataColumn.STRING, 2, 300 , 0 , false , false),
		new SchemaColumn("download_status", DataColumn.INTEGER, 3, 0 , 0 , false , false),
		new SchemaColumn("query_status", DataColumn.INTEGER, 4, 0 , 0 , false , false),
		new SchemaColumn("start_date", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("end_date", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("remark", DataColumn.STRING, 7, 500 , 0 , false , false),
		new SchemaColumn("create_datetime", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("statistics_plan_id", DataColumn.LONG, 12, 0 , 0 , false , false),
		new SchemaColumn("manually_execute_user", DataColumn.STRING, 13, 32 , 0 , false , false)
	};

	public static final String _TableCode = "zd_statistics_plan_record";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into zd_statistics_plan_record values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update zd_statistics_plan_record set id=?,download_file_name=?,download_file_path=?,download_status=?,query_status=?,start_date=?,end_date=?,remark=?,create_datetime=?,prop1=?,prop2=?,prop3=?,statistics_plan_id=?,manually_execute_user=? where id=?";

	protected static final String _DeleteSQL = "delete from zd_statistics_plan_record  where id=?";

	protected static final String _FillAllSQL = "select * from zd_statistics_plan_record  where id=?";

	public zd_statistics_plan_recordSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[14];
	}

	protected Schema newInstance(){
		return new zd_statistics_plan_recordSchema();
	}

	protected SchemaSet newSet(){
		return new zd_statistics_plan_recordSet();
	}

	public zd_statistics_plan_recordSet query() {
		return query(null, -1, -1);
	}

	public zd_statistics_plan_recordSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public zd_statistics_plan_recordSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public zd_statistics_plan_recordSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (zd_statistics_plan_recordSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){id = null;}else{id = new Long(v.toString());}return;}
		if (i == 1){download_file_name = (String)v;return;}
		if (i == 2){download_file_path = (String)v;return;}
		if (i == 3){if(v==null){download_status = null;}else{download_status = new Integer(v.toString());}return;}
		if (i == 4){if(v==null){query_status = null;}else{query_status = new Integer(v.toString());}return;}
		if (i == 5){start_date = (Date)v;return;}
		if (i == 6){end_date = (Date)v;return;}
		if (i == 7){remark = (String)v;return;}
		if (i == 8){create_datetime = (Date)v;return;}
		if (i == 9){prop1 = (String)v;return;}
		if (i == 10){prop2 = (String)v;return;}
		if (i == 11){prop3 = (String)v;return;}
		if (i == 12){if(v==null){statistics_plan_id = null;}else{statistics_plan_id = new Long(v.toString());}return;}
		if (i == 13){manually_execute_user = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return download_file_name;}
		if (i == 2){return download_file_path;}
		if (i == 3){return download_status;}
		if (i == 4){return query_status;}
		if (i == 5){return start_date;}
		if (i == 6){return end_date;}
		if (i == 7){return remark;}
		if (i == 8){return create_datetime;}
		if (i == 9){return prop1;}
		if (i == 10){return prop2;}
		if (i == 11){return prop3;}
		if (i == 12){return statistics_plan_id;}
		if (i == 13){return manually_execute_user;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getid() {
		if(id==null){return 0;}
		return id.longValue();
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(long id) {
		this.id = new Long(id);
    }

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		if (id == null){
			this.id = null;
			return;
		}
		this.id = new Long(id);
    }

	/**
	* 获取字段download_file_name的值，该字段的<br>
	* 字段名称 :download_file_name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdownload_file_name() {
		return download_file_name;
	}

	/**
	* 设置字段download_file_name的值，该字段的<br>
	* 字段名称 :download_file_name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdownload_file_name(String download_file_name) {
		this.download_file_name = download_file_name;
    }

	/**
	* 获取字段download_file_path的值，该字段的<br>
	* 字段名称 :download_file_path<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdownload_file_path() {
		return download_file_path;
	}

	/**
	* 设置字段download_file_path的值，该字段的<br>
	* 字段名称 :download_file_path<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdownload_file_path(String download_file_path) {
		this.download_file_path = download_file_path;
    }

	/**
	* 获取字段download_status的值，该字段的<br>
	* 字段名称 :download_status<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getdownload_status() {
		if(download_status==null){return 0;}
		return download_status.intValue();
	}

	/**
	* 设置字段download_status的值，该字段的<br>
	* 字段名称 :download_status<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdownload_status(int download_status) {
		this.download_status = new Integer(download_status);
    }

	/**
	* 设置字段download_status的值，该字段的<br>
	* 字段名称 :download_status<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdownload_status(String download_status) {
		if (download_status == null){
			this.download_status = null;
			return;
		}
		this.download_status = new Integer(download_status);
    }

	/**
	* 获取字段query_status的值，该字段的<br>
	* 字段名称 :query_status<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getquery_status() {
		if(query_status==null){return 0;}
		return query_status.intValue();
	}

	/**
	* 设置字段query_status的值，该字段的<br>
	* 字段名称 :query_status<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setquery_status(int query_status) {
		this.query_status = new Integer(query_status);
    }

	/**
	* 设置字段query_status的值，该字段的<br>
	* 字段名称 :query_status<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setquery_status(String query_status) {
		if (query_status == null){
			this.query_status = null;
			return;
		}
		this.query_status = new Integer(query_status);
    }

	/**
	* 获取字段start_date的值，该字段的<br>
	* 字段名称 :start_date<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getstart_date() {
		return start_date;
	}

	/**
	* 设置字段start_date的值，该字段的<br>
	* 字段名称 :start_date<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstart_date(Date start_date) {
		this.start_date = start_date;
    }

	/**
	* 获取字段end_date的值，该字段的<br>
	* 字段名称 :end_date<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getend_date() {
		return end_date;
	}

	/**
	* 设置字段end_date的值，该字段的<br>
	* 字段名称 :end_date<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setend_date(Date end_date) {
		this.end_date = end_date;
    }

	/**
	* 获取字段remark的值，该字段的<br>
	* 字段名称 :remark<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark() {
		return remark;
	}

	/**
	* 设置字段remark的值，该字段的<br>
	* 字段名称 :remark<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark(String remark) {
		this.remark = remark;
    }

	/**
	* 获取字段create_datetime的值，该字段的<br>
	* 字段名称 :create_datetime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreate_datetime() {
		return create_datetime;
	}

	/**
	* 设置字段create_datetime的值，该字段的<br>
	* 字段名称 :create_datetime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreate_datetime(Date create_datetime) {
		this.create_datetime = create_datetime;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段statistics_plan_id的值，该字段的<br>
	* 字段名称 :statistics_plan_id<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getstatistics_plan_id() {
		if(statistics_plan_id==null){return 0;}
		return statistics_plan_id.longValue();
	}

	/**
	* 设置字段statistics_plan_id的值，该字段的<br>
	* 字段名称 :statistics_plan_id<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatistics_plan_id(long statistics_plan_id) {
		this.statistics_plan_id = new Long(statistics_plan_id);
    }

	/**
	* 设置字段statistics_plan_id的值，该字段的<br>
	* 字段名称 :statistics_plan_id<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatistics_plan_id(String statistics_plan_id) {
		if (statistics_plan_id == null){
			this.statistics_plan_id = null;
			return;
		}
		this.statistics_plan_id = new Long(statistics_plan_id);
    }

	/**
	* 获取字段manually_execute_user的值，该字段的<br>
	* 字段名称 :manually_execute_user<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmanually_execute_user() {
		return manually_execute_user;
	}

	/**
	* 设置字段manually_execute_user的值，该字段的<br>
	* 字段名称 :manually_execute_user<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmanually_execute_user(String manually_execute_user) {
		this.manually_execute_user = manually_execute_user;
    }

}