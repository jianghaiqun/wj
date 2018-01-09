package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：统计计划<br>
 * 表代码：zd_statistics_plan<br>
 * 表主键：id<br>
 */
public class zd_statistics_planSchema extends Schema {
	private Long id;

	private String plan_name;

	private String plan_breadcrumbs;

	private Date create_datetime;

	private Date modify_datetime;

	private String remark;

	private String entrance_function;

	private String period;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String prop5;

	private byte[] grid_data;

	private String source_url;

	private String create_user;

	private String modify_user;

	private Integer plan_status;

	private Date execute_datetime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("plan_name", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("plan_breadcrumbs", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("create_datetime", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("modify_datetime", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("remark", DataColumn.STRING, 5, 500 , 0 , false , false),
		new SchemaColumn("entrance_function", DataColumn.STRING, 6, 300 , 0 , false , false),
		new SchemaColumn("period", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("prop5", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("grid_data", DataColumn.BLOB, 13, 0 , 0 , false , false),
		new SchemaColumn("source_url", DataColumn.STRING, 14, 300 , 0 , false , false),
		new SchemaColumn("create_user", DataColumn.STRING, 15, 32 , 0 , false , false),
		new SchemaColumn("modify_user", DataColumn.STRING, 16, 32 , 0 , false , false),
		new SchemaColumn("plan_status", DataColumn.INTEGER, 17, 0 , 0 , false , false),
		new SchemaColumn("execute_datetime", DataColumn.DATETIME, 18, 0 , 0 , false , false)
	};

	public static final String _TableCode = "zd_statistics_plan";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into zd_statistics_plan values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update zd_statistics_plan set id=?,plan_name=?,plan_breadcrumbs=?,create_datetime=?,modify_datetime=?,remark=?,entrance_function=?,period=?,prop1=?,prop2=?,prop3=?,prop4=?,prop5=?,grid_data=?,source_url=?,create_user=?,modify_user=?,plan_status=?,execute_datetime=? where id=?";

	protected static final String _DeleteSQL = "delete from zd_statistics_plan  where id=?";

	protected static final String _FillAllSQL = "select * from zd_statistics_plan  where id=?";

	public zd_statistics_planSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[19];
	}

	protected Schema newInstance(){
		return new zd_statistics_planSchema();
	}

	protected SchemaSet newSet(){
		return new zd_statistics_planSet();
	}

	public zd_statistics_planSet query() {
		return query(null, -1, -1);
	}

	public zd_statistics_planSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public zd_statistics_planSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public zd_statistics_planSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (zd_statistics_planSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){id = null;}else{id = new Long(v.toString());}return;}
		if (i == 1){plan_name = (String)v;return;}
		if (i == 2){plan_breadcrumbs = (String)v;return;}
		if (i == 3){create_datetime = (Date)v;return;}
		if (i == 4){modify_datetime = (Date)v;return;}
		if (i == 5){remark = (String)v;return;}
		if (i == 6){entrance_function = (String)v;return;}
		if (i == 7){period = (String)v;return;}
		if (i == 8){prop1 = (String)v;return;}
		if (i == 9){prop2 = (String)v;return;}
		if (i == 10){prop3 = (String)v;return;}
		if (i == 11){prop4 = (String)v;return;}
		if (i == 12){prop5 = (String)v;return;}
		if (i == 13){grid_data = (byte[])v;return;}
		if (i == 14){source_url = (String)v;return;}
		if (i == 15){create_user = (String)v;return;}
		if (i == 16){modify_user = (String)v;return;}
		if (i == 17){if(v==null){plan_status = null;}else{plan_status = new Integer(v.toString());}return;}
		if (i == 18){execute_datetime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return plan_name;}
		if (i == 2){return plan_breadcrumbs;}
		if (i == 3){return create_datetime;}
		if (i == 4){return modify_datetime;}
		if (i == 5){return remark;}
		if (i == 6){return entrance_function;}
		if (i == 7){return period;}
		if (i == 8){return prop1;}
		if (i == 9){return prop2;}
		if (i == 10){return prop3;}
		if (i == 11){return prop4;}
		if (i == 12){return prop5;}
		if (i == 13){return grid_data;}
		if (i == 14){return source_url;}
		if (i == 15){return create_user;}
		if (i == 16){return modify_user;}
		if (i == 17){return plan_status;}
		if (i == 18){return execute_datetime;}
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
	* 获取字段plan_name的值，该字段的<br>
	* 字段名称 :plan_name<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getplan_name() {
		return plan_name;
	}

	/**
	* 设置字段plan_name的值，该字段的<br>
	* 字段名称 :plan_name<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setplan_name(String plan_name) {
		this.plan_name = plan_name;
    }

	/**
	* 获取字段plan_breadcrumbs的值，该字段的<br>
	* 字段名称 :plan_breadcrumbs<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getplan_breadcrumbs() {
		return plan_breadcrumbs;
	}

	/**
	* 设置字段plan_breadcrumbs的值，该字段的<br>
	* 字段名称 :plan_breadcrumbs<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setplan_breadcrumbs(String plan_breadcrumbs) {
		this.plan_breadcrumbs = plan_breadcrumbs;
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
	* 获取字段modify_datetime的值，该字段的<br>
	* 字段名称 :modify_datetime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodify_datetime() {
		return modify_datetime;
	}

	/**
	* 设置字段modify_datetime的值，该字段的<br>
	* 字段名称 :modify_datetime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodify_datetime(Date modify_datetime) {
		this.modify_datetime = modify_datetime;
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
	* 获取字段entrance_function的值，该字段的<br>
	* 字段名称 :entrance_function<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getentrance_function() {
		return entrance_function;
	}

	/**
	* 设置字段entrance_function的值，该字段的<br>
	* 字段名称 :entrance_function<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setentrance_function(String entrance_function) {
		this.entrance_function = entrance_function;
    }

	/**
	* 获取字段period的值，该字段的<br>
	* 字段名称 :period<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getperiod() {
		return period;
	}

	/**
	* 设置字段period的值，该字段的<br>
	* 字段名称 :period<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setperiod(String period) {
		this.period = period;
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
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* 获取字段prop5的值，该字段的<br>
	* 字段名称 :prop5<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop5() {
		return prop5;
	}

	/**
	* 设置字段prop5的值，该字段的<br>
	* 字段名称 :prop5<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop5(String prop5) {
		this.prop5 = prop5;
    }

	/**
	* 获取字段grid_data的值，该字段的<br>
	* 字段名称 :grid_data<br>
	* 数据类型 :blob<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public byte[] getgrid_data() {
		return grid_data;
	}

	/**
	* 设置字段grid_data的值，该字段的<br>
	* 字段名称 :grid_data<br>
	* 数据类型 :blob<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgrid_data(byte[] grid_data) {
		this.grid_data = grid_data;
    }

	/**
	* 获取字段source_url的值，该字段的<br>
	* 字段名称 :source_url<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsource_url() {
		return source_url;
	}

	/**
	* 设置字段source_url的值，该字段的<br>
	* 字段名称 :source_url<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsource_url(String source_url) {
		this.source_url = source_url;
    }

	/**
	* 获取字段create_user的值，该字段的<br>
	* 字段名称 :create_user<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreate_user() {
		return create_user;
	}

	/**
	* 设置字段create_user的值，该字段的<br>
	* 字段名称 :create_user<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreate_user(String create_user) {
		this.create_user = create_user;
    }

	/**
	* 获取字段modify_user的值，该字段的<br>
	* 字段名称 :modify_user<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodify_user() {
		return modify_user;
	}

	/**
	* 设置字段modify_user的值，该字段的<br>
	* 字段名称 :modify_user<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodify_user(String modify_user) {
		this.modify_user = modify_user;
    }

	/**
	* 获取字段plan_status的值，该字段的<br>
	* 字段名称 :plan_status<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getplan_status() {
		if(plan_status==null){return 0;}
		return plan_status.intValue();
	}

	/**
	* 设置字段plan_status的值，该字段的<br>
	* 字段名称 :plan_status<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setplan_status(int plan_status) {
		this.plan_status = new Integer(plan_status);
    }

	/**
	* 设置字段plan_status的值，该字段的<br>
	* 字段名称 :plan_status<br>
	* 数据类型 :integer<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setplan_status(String plan_status) {
		if (plan_status == null){
			this.plan_status = null;
			return;
		}
		this.plan_status = new Integer(plan_status);
    }

	/**
	* 获取字段execute_datetime的值，该字段的<br>
	* 字段名称 :execute_datetime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getexecute_datetime() {
		return execute_datetime;
	}

	/**
	* 设置字段execute_datetime的值，该字段的<br>
	* 字段名称 :execute_datetime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setexecute_datetime(Date execute_datetime) {
		this.execute_datetime = execute_datetime;
    }

}