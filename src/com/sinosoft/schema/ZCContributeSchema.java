package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：投稿表<br>
 * 表代码：ZCContribute<br>
 * 表主键：ID<br>
 */
public class ZCContributeSchema extends Schema {
	private Long ID;

	private Long AuthorID;

	private Long AritcleID;

	private String Memo;

	private Date ContributeDate;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("AuthorID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("AritcleID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("Memo", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("ContributeDate", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 8, 50 , 0 , false , false)
	};

	public static final String _TableCode = "ZCContribute";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZCContribute values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCContribute set ID=?,AuthorID=?,AritcleID=?,Memo=?,ContributeDate=?,Prop1=?,Prop2=?,Prop3=?,Prop4=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCContribute  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCContribute  where ID=?";

	public ZCContributeSchema(){
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
		return new ZCContributeSchema();
	}

	protected SchemaSet newSet(){
		return new ZCContributeSet();
	}

	public ZCContributeSet query() {
		return query(null, -1, -1);
	}

	public ZCContributeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCContributeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCContributeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCContributeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){AuthorID = null;}else{AuthorID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){AritcleID = null;}else{AritcleID = new Long(v.toString());}return;}
		if (i == 3){Memo = (String)v;return;}
		if (i == 4){ContributeDate = (Date)v;return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){Prop2 = (String)v;return;}
		if (i == 7){Prop3 = (String)v;return;}
		if (i == 8){Prop4 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return AuthorID;}
		if (i == 2){return AritcleID;}
		if (i == 3){return Memo;}
		if (i == 4){return ContributeDate;}
		if (i == 5){return Prop1;}
		if (i == 6){return Prop2;}
		if (i == 7){return Prop3;}
		if (i == 8){return Prop4;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段AuthorID的值，该字段的<br>
	* 字段名称 :投稿人ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getAuthorID() {
		if(AuthorID==null){return 0;}
		return AuthorID.longValue();
	}

	/**
	* 设置字段AuthorID的值，该字段的<br>
	* 字段名称 :投稿人ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAuthorID(long authorID) {
		this.AuthorID = new Long(authorID);
    }

	/**
	* 设置字段AuthorID的值，该字段的<br>
	* 字段名称 :投稿人ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAuthorID(String authorID) {
		if (authorID == null){
			this.AuthorID = null;
			return;
		}
		this.AuthorID = new Long(authorID);
    }

	/**
	* 获取字段AritcleID的值，该字段的<br>
	* 字段名称 :文章ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getAritcleID() {
		if(AritcleID==null){return 0;}
		return AritcleID.longValue();
	}

	/**
	* 设置字段AritcleID的值，该字段的<br>
	* 字段名称 :文章ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAritcleID(long aritcleID) {
		this.AritcleID = new Long(aritcleID);
    }

	/**
	* 设置字段AritcleID的值，该字段的<br>
	* 字段名称 :文章ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAritcleID(String aritcleID) {
		if (aritcleID == null){
			this.AritcleID = null;
			return;
		}
		this.AritcleID = new Long(aritcleID);
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* 获取字段ContributeDate的值，该字段的<br>
	* 字段名称 :投稿日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getContributeDate() {
		return ContributeDate;
	}

	/**
	* 设置字段ContributeDate的值，该字段的<br>
	* 字段名称 :投稿日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContributeDate(Date contributeDate) {
		this.ContributeDate = contributeDate;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

}