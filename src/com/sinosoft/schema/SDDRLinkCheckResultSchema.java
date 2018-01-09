package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：达人帖子检查结果表<br>
 * 表代码：SDDRLinkCheckResult<br>
 * 表主键：Id<br>
 */
public class SDDRLinkCheckResultSchema extends Schema {
	/**
	 * serialVersionUID:.
	 */
	private static final long serialVersionUID = 2002153060818023008L;

	private String Id;

	private String Link;

	private String Platform;

	private String IsExist;

	private String PageNo;

	private String Keyword;

	private Date CheckTime;

	private String AddUser;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("Link", DataColumn.STRING, 1, 500 , 0 , false , false),
		new SchemaColumn("Platform", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("IsExist", DataColumn.STRING, 3, 2 , 0 , false , false),
		new SchemaColumn("PageNo", DataColumn.STRING, 4, 5 , 0 , false , false),
		new SchemaColumn("Keyword", DataColumn.STRING, 5, 40 , 0 , false , false),
		new SchemaColumn("CheckTime", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 13, 500 , 0 , false , false)
	};

	public static final String _TableCode = "SDDRLinkCheckResult";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDDRLinkCheckResult values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDDRLinkCheckResult set Id=?,Link=?,Platform=?,IsExist=?,PageNo=?,Keyword=?,CheckTime=?,AddUser=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDDRLinkCheckResult  where Id=?";

	protected static final String _FillAllSQL = "select * from SDDRLinkCheckResult  where Id=?";

	public SDDRLinkCheckResultSchema(){
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
		return new SDDRLinkCheckResultSchema();
	}

	protected SchemaSet newSet(){
		return new SDDRLinkCheckResultSet();
	}

	public SDDRLinkCheckResultSet query() {
		return query(null, -1, -1);
	}

	public SDDRLinkCheckResultSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDDRLinkCheckResultSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDDRLinkCheckResultSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDDRLinkCheckResultSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){Link = (String)v;return;}
		if (i == 2){Platform = (String)v;return;}
		if (i == 3){IsExist = (String)v;return;}
		if (i == 4){PageNo = (String)v;return;}
		if (i == 5){Keyword = (String)v;return;}
		if (i == 6){CheckTime = (Date)v;return;}
		if (i == 7){AddUser = (String)v;return;}
		if (i == 8){Prop1 = (String)v;return;}
		if (i == 9){Prop2 = (String)v;return;}
		if (i == 10){Prop3 = (String)v;return;}
		if (i == 11){Prop4 = (String)v;return;}
		if (i == 12){Prop5 = (String)v;return;}
		if (i == 13){Prop6 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return Link;}
		if (i == 2){return Platform;}
		if (i == 3){return IsExist;}
		if (i == 4){return PageNo;}
		if (i == 5){return Keyword;}
		if (i == 6){return CheckTime;}
		if (i == 7){return AddUser;}
		if (i == 8){return Prop1;}
		if (i == 9){return Prop2;}
		if (i == 10){return Prop3;}
		if (i == 11){return Prop4;}
		if (i == 12){return Prop5;}
		if (i == 13){return Prop6;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段Link的值，该字段的<br>
	* 字段名称 :帖子地址<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLink() {
		return Link;
	}

	/**
	* 设置字段Link的值，该字段的<br>
	* 字段名称 :帖子地址<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLink(String link) {
		this.Link = link;
    }

	/**
	* 获取字段Platform的值，该字段的<br>
	* 字段名称 :平台<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPlatform() {
		return Platform;
	}

	/**
	* 设置字段Platform的值，该字段的<br>
	* 字段名称 :平台<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPlatform(String platform) {
		this.Platform = platform;
    }

	/**
	* 获取字段IsExist的值，该字段的<br>
	* 字段名称 :是否匹配<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsExist() {
		return IsExist;
	}

	/**
	* 设置字段IsExist的值，该字段的<br>
	* 字段名称 :是否匹配<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsExist(String isExist) {
		this.IsExist = isExist;
    }

	/**
	* 获取字段PageNo的值，该字段的<br>
	* 字段名称 :第几页<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPageNo() {
		return PageNo;
	}

	/**
	* 设置字段PageNo的值，该字段的<br>
	* 字段名称 :第几页<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPageNo(String pageNo) {
		this.PageNo = pageNo;
    }

	/**
	* 获取字段Keyword的值，该字段的<br>
	* 字段名称 :匹配关键字<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getKeyword() {
		return Keyword;
	}

	/**
	* 设置字段Keyword的值，该字段的<br>
	* 字段名称 :匹配关键字<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setKeyword(String keyword) {
		this.Keyword = keyword;
    }

	/**
	* 获取字段CheckTime的值，该字段的<br>
	* 字段名称 :查询时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCheckTime() {
		return CheckTime;
	}

	/**
	* 设置字段CheckTime的值，该字段的<br>
	* 字段名称 :查询时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCheckTime(Date checkTime) {
		this.CheckTime = checkTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :查询人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :查询人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* 获取字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* 设置字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

}