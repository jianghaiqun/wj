package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：作者详细信息表<br>
 * 表代码：AuthorDetailInfo<br>
 * 表主键：id<br>
 */
public class AuthorDetailInfoSchema extends Schema {
	private String id;

	private String authorName;

	private String authorSex;

	private String destination;

	private String city;

	private String code;

	private String articleLink;

	private String source;

	private String contactTool;

	private String contactType;

	private String cooperationTime;

	private String payType;

	private String payRules;

	private String productName;

	private String branchInnerCode;

	private String remark1;

	private String remark2;

	private String remark3;

	private Date createDate;

	private String createUser;

	private Date modifyDate;

	private String modifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("authorName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("authorSex", DataColumn.STRING, 2, 2 , 0 , false , false),
		new SchemaColumn("destination", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("city", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("code", DataColumn.STRING, 5, 255 , 0 , false , false),
		new SchemaColumn("articleLink", DataColumn.STRING, 6, 255 , 0 , true , false),
		new SchemaColumn("source", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("contactTool", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("contactType", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("cooperationTime", DataColumn.STRING, 10, 10 , 0 , false , false),
		new SchemaColumn("payType", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("payRules", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("productName", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("branchInnerCode", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 18, 0 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 19, 100 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 20, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 21, 100 , 0 , false , false)
	};

	public static final String _TableCode = "AuthorDetailInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into AuthorDetailInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update AuthorDetailInfo set id=?,authorName=?,authorSex=?,destination=?,city=?,code=?,articleLink=?,source=?,contactTool=?,contactType=?,cooperationTime=?,payType=?,payRules=?,productName=?,branchInnerCode=?,remark1=?,remark2=?,remark3=?,createDate=?,createUser=?,modifyDate=?,modifyUser=? where id=?";

	protected static final String _DeleteSQL = "delete from AuthorDetailInfo  where id=?";

	protected static final String _FillAllSQL = "select * from AuthorDetailInfo  where id=?";

	public AuthorDetailInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[22];
	}

	protected Schema newInstance(){
		return new AuthorDetailInfoSchema();
	}

	protected SchemaSet newSet(){
		return new AuthorDetailInfoSet();
	}

	public AuthorDetailInfoSet query() {
		return query(null, -1, -1);
	}

	public AuthorDetailInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public AuthorDetailInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public AuthorDetailInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (AuthorDetailInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){authorName = (String)v;return;}
		if (i == 2){authorSex = (String)v;return;}
		if (i == 3){destination = (String)v;return;}
		if (i == 4){city = (String)v;return;}
		if (i == 5){code = (String)v;return;}
		if (i == 6){articleLink = (String)v;return;}
		if (i == 7){source = (String)v;return;}
		if (i == 8){contactTool = (String)v;return;}
		if (i == 9){contactType = (String)v;return;}
		if (i == 10){cooperationTime = (String)v;return;}
		if (i == 11){payType = (String)v;return;}
		if (i == 12){payRules = (String)v;return;}
		if (i == 13){productName = (String)v;return;}
		if (i == 14){branchInnerCode = (String)v;return;}
		if (i == 15){remark1 = (String)v;return;}
		if (i == 16){remark2 = (String)v;return;}
		if (i == 17){remark3 = (String)v;return;}
		if (i == 18){createDate = (Date)v;return;}
		if (i == 19){createUser = (String)v;return;}
		if (i == 20){modifyDate = (Date)v;return;}
		if (i == 21){modifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return authorName;}
		if (i == 2){return authorSex;}
		if (i == 3){return destination;}
		if (i == 4){return city;}
		if (i == 5){return code;}
		if (i == 6){return articleLink;}
		if (i == 7){return source;}
		if (i == 8){return contactTool;}
		if (i == 9){return contactType;}
		if (i == 10){return cooperationTime;}
		if (i == 11){return payType;}
		if (i == 12){return payRules;}
		if (i == 13){return productName;}
		if (i == 14){return branchInnerCode;}
		if (i == 15){return remark1;}
		if (i == 16){return remark2;}
		if (i == 17){return remark3;}
		if (i == 18){return createDate;}
		if (i == 19){return createUser;}
		if (i == 20){return modifyDate;}
		if (i == 21){return modifyUser;}
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
	* 获取字段authorName的值，该字段的<br>
	* 字段名称 :姓名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getauthorName() {
		return authorName;
	}

	/**
	* 设置字段authorName的值，该字段的<br>
	* 字段名称 :姓名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setauthorName(String authorName) {
		this.authorName = authorName;
    }

	/**
	* 获取字段authorSex的值，该字段的<br>
	* 字段名称 :性别<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getauthorSex() {
		return authorSex;
	}

	/**
	* 设置字段authorSex的值，该字段的<br>
	* 字段名称 :性别<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setauthorSex(String authorSex) {
		this.authorSex = authorSex;
    }

	/**
	* 获取字段destination的值，该字段的<br>
	* 字段名称 :目的地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdestination() {
		return destination;
	}

	/**
	* 设置字段destination的值，该字段的<br>
	* 字段名称 :目的地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdestination(String destination) {
		this.destination = destination;
    }

	/**
	* 获取字段city的值，该字段的<br>
	* 字段名称 :所在城市<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcity() {
		return city;
	}

	/**
	* 设置字段city的值，该字段的<br>
	* 字段名称 :所在城市<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcity(String city) {
		this.city = city;
    }

	/**
	* 获取字段code的值，该字段的<br>
	* 字段名称 :代码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcode() {
		return code;
	}

	/**
	* 设置字段code的值，该字段的<br>
	* 字段名称 :代码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcode(String code) {
		this.code = code;
    }

	/**
	* 获取字段articleLink的值，该字段的<br>
	* 字段名称 :帖子链接<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getarticleLink() {
		return articleLink;
	}

	/**
	* 设置字段articleLink的值，该字段的<br>
	* 字段名称 :帖子链接<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setarticleLink(String articleLink) {
		this.articleLink = articleLink;
    }

	/**
	* 获取字段source的值，该字段的<br>
	* 字段名称 :来源<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsource() {
		return source;
	}

	/**
	* 设置字段source的值，该字段的<br>
	* 字段名称 :来源<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsource(String source) {
		this.source = source;
    }

	/**
	* 获取字段contactTool的值，该字段的<br>
	* 字段名称 :联系工具<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcontactTool() {
		return contactTool;
	}

	/**
	* 设置字段contactTool的值，该字段的<br>
	* 字段名称 :联系工具<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontactTool(String contactTool) {
		this.contactTool = contactTool;
    }

	/**
	* 获取字段contactType的值，该字段的<br>
	* 字段名称 :联系方式<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcontactType() {
		return contactType;
	}

	/**
	* 设置字段contactType的值，该字段的<br>
	* 字段名称 :联系方式<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcontactType(String contactType) {
		this.contactType = contactType;
    }

	/**
	* 获取字段cooperationTime的值，该字段的<br>
	* 字段名称 :合作时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcooperationTime() {
		return cooperationTime;
	}

	/**
	* 设置字段cooperationTime的值，该字段的<br>
	* 字段名称 :合作时间<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcooperationTime(String cooperationTime) {
		this.cooperationTime = cooperationTime;
    }

	/**
	* 获取字段payType的值，该字段的<br>
	* 字段名称 :支付方式<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayType() {
		return payType;
	}

	/**
	* 设置字段payType的值，该字段的<br>
	* 字段名称 :支付方式<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayType(String payType) {
		this.payType = payType;
    }

	/**
	* 获取字段payRules的值，该字段的<br>
	* 字段名称 :支付规则<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayRules() {
		return payRules;
	}

	/**
	* 设置字段payRules的值，该字段的<br>
	* 字段名称 :支付规则<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayRules(String payRules) {
		this.payRules = payRules;
    }

	/**
	* 获取字段productName的值，该字段的<br>
	* 字段名称 :推荐险种<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductName() {
		return productName;
	}

	/**
	* 设置字段productName的值，该字段的<br>
	* 字段名称 :推荐险种<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductName(String productName) {
		this.productName = productName;
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