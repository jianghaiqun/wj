package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：报纸版面新闻关联表<br>
 * 表代码：ZCPaperPageNewsRela<br>
 * 表主键：PageID, ArticleID<br>
 */
public class ZCPaperPageNewsRelaSchema extends Schema {
	private Long PageID;

	private Long ArticleID;

	private String Coords;

	private String Link;

	private String Memo;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("PageID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("ArticleID", DataColumn.LONG, 1, 0 , 0 , true , true),
		new SchemaColumn("Coords", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("Link", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 4, 1000 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 9, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 10, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 12, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCPaperPageNewsRela";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZCPaperPageNewsRela values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCPaperPageNewsRela set PageID=?,ArticleID=?,Coords=?,Link=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where PageID=? and ArticleID=?";

	protected static final String _DeleteSQL = "delete from ZCPaperPageNewsRela  where PageID=? and ArticleID=?";

	protected static final String _FillAllSQL = "select * from ZCPaperPageNewsRela  where PageID=? and ArticleID=?";

	public ZCPaperPageNewsRelaSchema(){
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
		return new ZCPaperPageNewsRelaSchema();
	}

	protected SchemaSet newSet(){
		return new ZCPaperPageNewsRelaSet();
	}

	public ZCPaperPageNewsRelaSet query() {
		return query(null, -1, -1);
	}

	public ZCPaperPageNewsRelaSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCPaperPageNewsRelaSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCPaperPageNewsRelaSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCPaperPageNewsRelaSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){PageID = null;}else{PageID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){ArticleID = null;}else{ArticleID = new Long(v.toString());}return;}
		if (i == 2){Coords = (String)v;return;}
		if (i == 3){Link = (String)v;return;}
		if (i == 4){Memo = (String)v;return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){Prop2 = (String)v;return;}
		if (i == 7){Prop3 = (String)v;return;}
		if (i == 8){Prop4 = (String)v;return;}
		if (i == 9){AddUser = (String)v;return;}
		if (i == 10){AddTime = (Date)v;return;}
		if (i == 11){ModifyUser = (String)v;return;}
		if (i == 12){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return PageID;}
		if (i == 1){return ArticleID;}
		if (i == 2){return Coords;}
		if (i == 3){return Link;}
		if (i == 4){return Memo;}
		if (i == 5){return Prop1;}
		if (i == 6){return Prop2;}
		if (i == 7){return Prop3;}
		if (i == 8){return Prop4;}
		if (i == 9){return AddUser;}
		if (i == 10){return AddTime;}
		if (i == 11){return ModifyUser;}
		if (i == 12){return ModifyTime;}
		return null;
	}

	/**
	* 获取字段PageID的值，该字段的<br>
	* 字段名称 :版面ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getPageID() {
		if(PageID==null){return 0;}
		return PageID.longValue();
	}

	/**
	* 设置字段PageID的值，该字段的<br>
	* 字段名称 :版面ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setPageID(long pageID) {
		this.PageID = new Long(pageID);
    }

	/**
	* 设置字段PageID的值，该字段的<br>
	* 字段名称 :版面ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setPageID(String pageID) {
		if (pageID == null){
			this.PageID = null;
			return;
		}
		this.PageID = new Long(pageID);
    }

	/**
	* 获取字段ArticleID的值，该字段的<br>
	* 字段名称 :文档ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getArticleID() {
		if(ArticleID==null){return 0;}
		return ArticleID.longValue();
	}

	/**
	* 设置字段ArticleID的值，该字段的<br>
	* 字段名称 :文档ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setArticleID(long articleID) {
		this.ArticleID = new Long(articleID);
    }

	/**
	* 设置字段ArticleID的值，该字段的<br>
	* 字段名称 :文档ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setArticleID(String articleID) {
		if (articleID == null){
			this.ArticleID = null;
			return;
		}
		this.ArticleID = new Long(articleID);
    }

	/**
	* 获取字段Coords的值，该字段的<br>
	* 字段名称 :坐标<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCoords() {
		return Coords;
	}

	/**
	* 设置字段Coords的值，该字段的<br>
	* 字段名称 :坐标<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCoords(String coords) {
		this.Coords = coords;
    }

	/**
	* 获取字段Link的值，该字段的<br>
	* 字段名称 :链接<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLink() {
		return Link;
	}

	/**
	* 设置字段Link的值，该字段的<br>
	* 字段名称 :链接<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLink(String link) {
		this.Link = link;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
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

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

}