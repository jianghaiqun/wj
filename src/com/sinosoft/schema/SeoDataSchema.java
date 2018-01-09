package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：SeoData<br>
 * 表代码：SeoData<br>
 * 表主键：id<br>
 */
public class SeoDataSchema extends Schema {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String keyWord;

	private Integer ranking;

	private Integer searches;

	private Integer theNumber;

	private String title;

	private String type;

	private Integer KR;

	private String createDate;

	private String page;

	private String prop1;

	private String prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.INTEGER, 0, 0 , 0 , true , true),
		new SchemaColumn("keyWord", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("ranking", DataColumn.INTEGER, 2, 0 , 0 , false , false),
		new SchemaColumn("searches", DataColumn.INTEGER, 3, 0 , 0 , false , false),
		new SchemaColumn("theNumber", DataColumn.INTEGER, 4, 0 , 0 , false , false),
		new SchemaColumn("title", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("type", DataColumn.STRING, 6, 1 , 0 , false , false),
		new SchemaColumn("KR", DataColumn.INTEGER, 7, 0 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.STRING, 8, 0 , 0 , false , false),
		new SchemaColumn("page", DataColumn.STRING, 9, 4 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 11, 200 , 0 , false , false)
	};

	public static final String _TableCode = "SeoData";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SeoData values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SeoData set id=?,keyWord=?,ranking=?,searches=?,theNumber=?,title=?,type=?,KR=?,createDate=?,page=?,prop1=?,prop2=? where id=?";

	protected static final String _DeleteSQL = "delete from SeoData  where id=?";

	protected static final String _FillAllSQL = "select * from SeoData  where id=?";

	public SeoDataSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new SeoDataSchema();
	}

	protected SchemaSet newSet(){
		return new SeoDataSet();
	}

	public SeoDataSet query() {
		return query(null, -1, -1);
	}

	public SeoDataSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SeoDataSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SeoDataSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SeoDataSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){id = null;}else{id = new Integer(v.toString());}return;}
		if (i == 1){keyWord = (String)v;return;}
		if (i == 2){if(v==null){ranking = null;}else{ranking = new Integer(v.toString());}return;}
		if (i == 3){if(v==null){searches = null;}else{searches = new Integer(v.toString());}return;}
		if (i == 4){if(v==null){theNumber = null;}else{theNumber = new Integer(v.toString());}return;}
		if (i == 5){title = (String)v;return;}
		if (i == 6){type = (String)v;return;}
		if (i == 7){if(v==null){KR = null;}else{KR = new Integer(v.toString());}return;}
		if (i == 8){createDate = (String)v;return;}
		if (i == 9){page = (String)v;return;}
		if (i == 10){prop1 = (String)v;return;}
		if (i == 11){prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return keyWord;}
		if (i == 2){return ranking;}
		if (i == 3){return searches;}
		if (i == 4){return theNumber;}
		if (i == 5){return title;}
		if (i == 6){return type;}
		if (i == 7){return KR;}
		if (i == 8){return createDate;}
		if (i == 9){return page;}
		if (i == 10){return prop1;}
		if (i == 11){return prop2;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public int getid() {
		if(id==null){return 0;}
		return id.intValue();
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(int id) {
		this.id = new Integer(id);
    }

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :int<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		if (id == null){
			this.id = null;
			return;
		}
		this.id = new Integer(id);
    }

	/**
	* 获取字段keyWord的值，该字段的<br>
	* 字段名称 :keyWord<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	关键字<br>
	*/
	public String getkeyWord() {
		return keyWord;
	}

	/**
	* 设置字段keyWord的值，该字段的<br>
	* 字段名称 :keyWord<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	关键字<br>
	*/
	public void setkeyWord(String keyWord) {
		this.keyWord = keyWord;
    }

	/**
	* 获取字段ranking的值，该字段的<br>
	* 字段名称 :ranking<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	排名<br>
	*/
	public int getranking() {
		if(ranking==null){return 0;}
		return ranking.intValue();
	}

	/**
	* 设置字段ranking的值，该字段的<br>
	* 字段名称 :ranking<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	排名<br>
	*/
	public void setranking(int ranking) {
		this.ranking = new Integer(ranking);
    }

	/**
	* 设置字段ranking的值，该字段的<br>
	* 字段名称 :ranking<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	排名<br>
	*/
	public void setranking(String ranking) {
		if (ranking == null){
			this.ranking = null;
			return;
		}
		this.ranking = new Integer(ranking);
    }

	/**
	* 获取字段searches的值，该字段的<br>
	* 字段名称 :searches<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	搜索量<br>
	*/
	public int getsearches() {
		if(searches==null){return 0;}
		return searches.intValue();
	}

	/**
	* 设置字段searches的值，该字段的<br>
	* 字段名称 :searches<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	搜索量<br>
	*/
	public void setsearches(int searches) {
		this.searches = new Integer(searches);
    }

	/**
	* 设置字段searches的值，该字段的<br>
	* 字段名称 :searches<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	搜索量<br>
	*/
	public void setsearches(String searches) {
		if (searches == null){
			this.searches = null;
			return;
		}
		this.searches = new Integer(searches);
    }

	/**
	* 获取字段theNumber的值，该字段的<br>
	* 字段名称 :theNumber<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	收录量<br>
	*/
	public int gettheNumber() {
		if(theNumber==null){return 0;}
		return theNumber.intValue();
	}

	/**
	* 设置字段theNumber的值，该字段的<br>
	* 字段名称 :theNumber<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	收录量<br>
	*/
	public void settheNumber(int theNumber) {
		this.theNumber = new Integer(theNumber);
    }

	/**
	* 设置字段theNumber的值，该字段的<br>
	* 字段名称 :theNumber<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	收录量<br>
	*/
	public void settheNumber(String theNumber) {
		if (theNumber == null){
			this.theNumber = null;
			return;
		}
		this.theNumber = new Integer(theNumber);
    }

	/**
	* 获取字段title的值，该字段的<br>
	* 字段名称 :title<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	标题<br>
	*/
	public String gettitle() {
		return title;
	}

	/**
	* 设置字段title的值，该字段的<br>
	* 字段名称 :title<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	标题<br>
	*/
	public void settitle(String title) {
		this.title = title;
    }

	/**
	* 获取字段type的值，该字段的<br>
	* 字段名称 :type<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	类型:1 baidu 2.360<br>
	*/
	public String gettype() {
		return type;
	}

	/**
	* 设置字段type的值，该字段的<br>
	* 字段名称 :type<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	类型:1 baidu 2.360<br>
	*/
	public void settype(String type) {
		this.type = type;
    }

	/**
	* 获取字段KR的值，该字段的<br>
	* 字段名称 :KR<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	KR<br>
	*/
	public int getKR() {
		if(KR==null){return 0;}
		return KR.intValue();
	}

	/**
	* 设置字段KR的值，该字段的<br>
	* 字段名称 :KR<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	KR<br>
	*/
	public void setKR(int kR) {
		this.KR = new Integer(kR);
    }

	/**
	* 设置字段KR的值，该字段的<br>
	* 字段名称 :KR<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	KR<br>
	*/
	public void setKR(String kR) {
		if (kR == null){
			this.KR = null;
			return;
		}
		this.KR = new Integer(kR);
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :createDate<br>
	* 数据类型 :varchar<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	创建时间<br>
	*/
	public String getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :createDate<br>
	* 数据类型 :varchar<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	创建时间<br>
	*/
	public void setcreateDate(String createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段page的值，该字段的<br>
	* 字段名称 :page<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpage() {
		return page;
	}

	/**
	* 设置字段page的值，该字段的<br>
	* 字段名称 :page<br>
	* 数据类型 :varchar(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpage(String page) {
		this.page = page;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

}