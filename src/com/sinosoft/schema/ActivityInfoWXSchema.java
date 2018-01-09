package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：活动基础信息表<br>
 * 表代码：ActivityInfoWX<br>
 * 表主键：id<br>
 */
public class ActivityInfoWXSchema extends Schema {
	private String id;

	private String name;

	private String description;

	private String type;

	private String status;

	private Date startTime;

	private Date stopTime;

	private String gamePeriod;

	private String productId;

	private String productImage;

	private String productName;

	private String productDescribe;

	private String ruleText;

	private String createStaff;

	private Date createDate;

	private String modifyStaff;

	private Date modifyDate;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String activitylogo1;

	private String activitylogo2;

	private String sharetitle;

	private String sharedescribe;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("name", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("description", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("type", DataColumn.STRING, 3, 5 , 0 , false , false),
		new SchemaColumn("status", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("startTime", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("stopTime", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("gamePeriod", DataColumn.STRING, 7, 5 , 0 , false , false),
		new SchemaColumn("productId", DataColumn.STRING, 8, 32 , 0 , false , false),
		new SchemaColumn("productImage", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("productName", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("productDescribe", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("ruleText", DataColumn.STRING, 12, 500 , 0 , false , false),
		new SchemaColumn("createStaff", DataColumn.STRING, 13, 20 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 14, 0 , 0 , false , false),
		new SchemaColumn("modifyStaff", DataColumn.STRING, 15, 20 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 16, 0 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 17, 500 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 18, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 19, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 20, 100 , 0 , false , false),
		new SchemaColumn("activitylogo1", DataColumn.STRING, 21, 100 , 0 , false , false),
		new SchemaColumn("activitylogo2", DataColumn.STRING, 22, 100 , 0 , false , false),
		new SchemaColumn("sharetitle", DataColumn.STRING, 23, 50 , 0 , false , false),
		new SchemaColumn("sharedescribe", DataColumn.STRING, 24, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ActivityInfoWX";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ActivityInfoWX values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ActivityInfoWX set id=?,name=?,description=?,type=?,status=?,startTime=?,stopTime=?,gamePeriod=?,productId=?,productImage=?,productName=?,productDescribe=?,ruleText=?,createStaff=?,createDate=?,modifyStaff=?,modifyDate=?,prop1=?,prop2=?,prop3=?,prop4=?,activitylogo1=?,activitylogo2=?,sharetitle=?,sharedescribe=? where id=?";

	protected static final String _DeleteSQL = "delete from ActivityInfoWX  where id=?";

	protected static final String _FillAllSQL = "select * from ActivityInfoWX  where id=?";

	public ActivityInfoWXSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[25];
	}

	protected Schema newInstance(){
		return new ActivityInfoWXSchema();
	}

	protected SchemaSet newSet(){
		return new ActivityInfoWXSet();
	}

	public ActivityInfoWXSet query() {
		return query(null, -1, -1);
	}

	public ActivityInfoWXSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ActivityInfoWXSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ActivityInfoWXSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ActivityInfoWXSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){name = (String)v;return;}
		if (i == 2){description = (String)v;return;}
		if (i == 3){type = (String)v;return;}
		if (i == 4){status = (String)v;return;}
		if (i == 5){startTime = (Date)v;return;}
		if (i == 6){stopTime = (Date)v;return;}
		if (i == 7){gamePeriod = (String)v;return;}
		if (i == 8){productId = (String)v;return;}
		if (i == 9){productImage = (String)v;return;}
		if (i == 10){productName = (String)v;return;}
		if (i == 11){productDescribe = (String)v;return;}
		if (i == 12){ruleText = (String)v;return;}
		if (i == 13){createStaff = (String)v;return;}
		if (i == 14){createDate = (Date)v;return;}
		if (i == 15){modifyStaff = (String)v;return;}
		if (i == 16){modifyDate = (Date)v;return;}
		if (i == 17){prop1 = (String)v;return;}
		if (i == 18){prop2 = (String)v;return;}
		if (i == 19){prop3 = (String)v;return;}
		if (i == 20){prop4 = (String)v;return;}
		if (i == 21){activitylogo1 = (String)v;return;}
		if (i == 22){activitylogo2 = (String)v;return;}
		if (i == 23){sharetitle = (String)v;return;}
		if (i == 24){sharedescribe = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return name;}
		if (i == 2){return description;}
		if (i == 3){return type;}
		if (i == 4){return status;}
		if (i == 5){return startTime;}
		if (i == 6){return stopTime;}
		if (i == 7){return gamePeriod;}
		if (i == 8){return productId;}
		if (i == 9){return productImage;}
		if (i == 10){return productName;}
		if (i == 11){return productDescribe;}
		if (i == 12){return ruleText;}
		if (i == 13){return createStaff;}
		if (i == 14){return createDate;}
		if (i == 15){return modifyStaff;}
		if (i == 16){return modifyDate;}
		if (i == 17){return prop1;}
		if (i == 18){return prop2;}
		if (i == 19){return prop3;}
		if (i == 20){return prop4;}
		if (i == 21){return activitylogo1;}
		if (i == 22){return activitylogo2;}
		if (i == 23){return sharetitle;}
		if (i == 24){return sharedescribe;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段name的值，该字段的<br>
	* 字段名称 :活动名称<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getname() {
		return name;
	}

	/**
	* 设置字段name的值，该字段的<br>
	* 字段名称 :活动名称<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setname(String name) {
		this.name = name;
    }

	/**
	* 获取字段description的值，该字段的<br>
	* 字段名称 :活动描述<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getdescription() {
		return description;
	}

	/**
	* 设置字段description的值，该字段的<br>
	* 字段名称 :活动描述<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setdescription(String description) {
		this.description = description;
    }

	/**
	* 获取字段type的值，该字段的<br>
	* 字段名称 :活动类型<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettype() {
		return type;
	}

	/**
	* 设置字段type的值，该字段的<br>
	* 字段名称 :活动类型<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settype(String type) {
		this.type = type;
    }

	/**
	* 获取字段status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstatus() {
		return status;
	}

	/**
	* 设置字段status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatus(String status) {
		this.status = status;
    }

	/**
	* 获取字段startTime的值，该字段的<br>
	* 字段名称 :开始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getstartTime() {
		return startTime;
	}

	/**
	* 设置字段startTime的值，该字段的<br>
	* 字段名称 :开始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstartTime(Date startTime) {
		this.startTime = startTime;
    }

	/**
	* 获取字段stopTime的值，该字段的<br>
	* 字段名称 :截止时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getstopTime() {
		return stopTime;
	}

	/**
	* 设置字段stopTime的值，该字段的<br>
	* 字段名称 :截止时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstopTime(Date stopTime) {
		this.stopTime = stopTime;
    }

	/**
	* 获取字段gamePeriod的值，该字段的<br>
	* 字段名称 :游戏时长<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getgamePeriod() {
		return gamePeriod;
	}

	/**
	* 设置字段gamePeriod的值，该字段的<br>
	* 字段名称 :游戏时长<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgamePeriod(String gamePeriod) {
		this.gamePeriod = gamePeriod;
    }

	/**
	* 获取字段productId的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductId() {
		return productId;
	}

	/**
	* 设置字段productId的值，该字段的<br>
	* 字段名称 :产品ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductId(String productId) {
		this.productId = productId;
    }

	/**
	* 获取字段productImage的值，该字段的<br>
	* 字段名称 :产品图片<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductImage() {
		return productImage;
	}

	/**
	* 设置字段productImage的值，该字段的<br>
	* 字段名称 :产品图片<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductImage(String productImage) {
		this.productImage = productImage;
    }

	/**
	* 获取字段productName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductName() {
		return productName;
	}

	/**
	* 设置字段productName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductName(String productName) {
		this.productName = productName;
    }

	/**
	* 获取字段productDescribe的值，该字段的<br>
	* 字段名称 :产品描述<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductDescribe() {
		return productDescribe;
	}

	/**
	* 设置字段productDescribe的值，该字段的<br>
	* 字段名称 :产品描述<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductDescribe(String productDescribe) {
		this.productDescribe = productDescribe;
    }

	/**
	* 获取字段ruleText的值，该字段的<br>
	* 字段名称 :活动规则数据<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getruleText() {
		return ruleText;
	}

	/**
	* 设置字段ruleText的值，该字段的<br>
	* 字段名称 :活动规则数据<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setruleText(String ruleText) {
		this.ruleText = ruleText;
    }

	/**
	* 获取字段createStaff的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateStaff() {
		return createStaff;
	}

	/**
	* 设置字段createStaff的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateStaff(String createStaff) {
		this.createStaff = createStaff;
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
	* 获取字段modifyStaff的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyStaff() {
		return modifyStaff;
	}

	/**
	* 设置字段modifyStaff的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyStaff(String modifyStaff) {
		this.modifyStaff = modifyStaff;
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
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(500)<br>
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
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* 获取字段activitylogo1的值，该字段的<br>
	* 字段名称 :活动logo1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getactivitylogo1() {
		return activitylogo1;
	}

	/**
	* 设置字段activitylogo1的值，该字段的<br>
	* 字段名称 :活动logo1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setactivitylogo1(String activitylogo1) {
		this.activitylogo1 = activitylogo1;
    }

	/**
	* 获取字段activitylogo2的值，该字段的<br>
	* 字段名称 :活动logo2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getactivitylogo2() {
		return activitylogo2;
	}

	/**
	* 设置字段activitylogo2的值，该字段的<br>
	* 字段名称 :活动logo2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setactivitylogo2(String activitylogo2) {
		this.activitylogo2 = activitylogo2;
    }

	/**
	* 获取字段sharetitle的值，该字段的<br>
	* 字段名称 :分享标题<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsharetitle() {
		return sharetitle;
	}

	/**
	* 设置字段sharetitle的值，该字段的<br>
	* 字段名称 :分享标题<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsharetitle(String sharetitle) {
		this.sharetitle = sharetitle;
    }

	/**
	* 获取字段sharedescribe的值，该字段的<br>
	* 字段名称 :分享描述<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsharedescribe() {
		return sharedescribe;
	}

	/**
	* 设置字段sharedescribe的值，该字段的<br>
	* 字段名称 :分享描述<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsharedescribe(String sharedescribe) {
		this.sharedescribe = sharedescribe;
    }

}