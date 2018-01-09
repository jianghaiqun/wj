package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：站点地图<br>
 * 表代码：SiteMap<br>
 * 表主键：SiteMapID<br>
 */
public class SiteMapSchema extends Schema {
	private String SiteMapID;

	private String SiteMapName;

	private Date date;

	private String ChangeFreq;

	private String Priority;

	private Date datetime;

	private String remark1;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("SiteMapID", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("SiteMapName", DataColumn.STRING, 1, 200 , 0 , false , false),
		new SchemaColumn("date", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("ChangeFreq", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("Priority", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("datetime", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 6, 200 , 0 , false , false)
	};

	public static final String _TableCode = "SiteMap";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SiteMap values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SiteMap set SiteMapID=?,SiteMapName=?,date=?,ChangeFreq=?,Priority=?,datetime=?,remark1=? where SiteMapID=?";

	protected static final String _DeleteSQL = "delete from SiteMap  where SiteMapID=?";

	protected static final String _FillAllSQL = "select * from SiteMap  where SiteMapID=?";

	public SiteMapSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[7];
	}

	protected Schema newInstance(){
		return new SiteMapSchema();
	}

	protected SchemaSet newSet(){
		return new SiteMapSet();
	}

	public SiteMapSet query() {
		return query(null, -1, -1);
	}

	public SiteMapSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SiteMapSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SiteMapSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SiteMapSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){SiteMapID = (String)v;return;}
		if (i == 1){SiteMapName = (String)v;return;}
		if (i == 2){date = (Date)v;return;}
		if (i == 3){ChangeFreq = (String)v;return;}
		if (i == 4){Priority = (String)v;return;}
		if (i == 5){datetime = (Date)v;return;}
		if (i == 6){remark1 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return SiteMapID;}
		if (i == 1){return SiteMapName;}
		if (i == 2){return date;}
		if (i == 3){return ChangeFreq;}
		if (i == 4){return Priority;}
		if (i == 5){return datetime;}
		if (i == 6){return remark1;}
		return null;
	}

	/**
	* 获取字段SiteMapID的值，该字段的<br>
	* 字段名称 :站点地图ID<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getSiteMapID() {
		return SiteMapID;
	}

	/**
	* 设置字段SiteMapID的值，该字段的<br>
	* 字段名称 :站点地图ID<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setSiteMapID(String siteMapID) {
		this.SiteMapID = siteMapID;
    }

	/**
	* 获取字段SiteMapName的值，该字段的<br>
	* 字段名称 :站点地图名称<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSiteMapName() {
		return SiteMapName;
	}

	/**
	* 设置字段SiteMapName的值，该字段的<br>
	* 字段名称 :站点地图名称<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteMapName(String siteMapName) {
		this.SiteMapName = siteMapName;
    }

	/**
	* 获取字段date的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getdate() {
		return date;
	}

	/**
	* 设置字段date的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdate(Date date) {
		this.date = date;
    }

	/**
	* 获取字段ChangeFreq的值，该字段的<br>
	* 字段名称 :修改频率<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChangeFreq() {
		return ChangeFreq;
	}

	/**
	* 设置字段ChangeFreq的值，该字段的<br>
	* 字段名称 :修改频率<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChangeFreq(String changeFreq) {
		this.ChangeFreq = changeFreq;
    }

	/**
	* 获取字段Priority的值，该字段的<br>
	* 字段名称 :优先级(节点)<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPriority() {
		return Priority;
	}

	/**
	* 设置字段Priority的值，该字段的<br>
	* 字段名称 :优先级(节点)<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPriority(String priority) {
		this.Priority = priority;
    }

	/**
	* 获取字段datetime的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getdatetime() {
		return datetime;
	}

	/**
	* 设置字段datetime的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :DATE<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdatetime(Date datetime) {
		this.datetime = datetime;
    }

	/**
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :VARCHAR(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

}