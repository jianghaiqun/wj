package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：产品信息表<br>
 * 表代码：OtProductInfo<br>
 * 表主键：id<br>
 */
public class OtProductInfoSchema extends Schema {
	private String id;

	private String productName;

	private String shortName;

	private String productPrice;

	private Date startDate;

	private Date endDate;

	private String productType;

	private String productClassify;

	private String moduleType;

	private Integer reserveDay;

	private String state;

	private Date shelfTime;

	private String birthland;

	private String destination;

	private String prompt;

	private String productDesc;

	private Long sortNum;

	private String remark1;

	private String remark2;

	private String remark3;

	private String remark4;

	private String remark5;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("productName", DataColumn.STRING, 1, 255 , 0 , false , false),
		new SchemaColumn("shortName", DataColumn.STRING, 2, 255 , 0 , false , false),
		new SchemaColumn("productPrice", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("startDate", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("endDate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("productType", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("productClassify", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("moduleType", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("reserveDay", DataColumn.INTEGER, 9, 5 , 0 , false , false),
		new SchemaColumn("state", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("shelfTime", DataColumn.DATETIME, 11, 0 , 0 , false , false),
		new SchemaColumn("birthland", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("destination", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("prompt", DataColumn.STRING, 14, 1000 , 0 , false , false),
		new SchemaColumn("productDesc", DataColumn.CLOB, 15, 0 , 0 , false , false),
		new SchemaColumn("sortNum", DataColumn.LONG, 16, 20 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 18, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("remark4", DataColumn.STRING, 20, 255 , 0 , false , false),
		new SchemaColumn("remark5", DataColumn.STRING, 21, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 22, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 23, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 24, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 25, 0 , 0 , false , false)
	};

	public static final String _TableCode = "OtProductInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into OtProductInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update OtProductInfo set id=?,productName=?,shortName=?,productPrice=?,startDate=?,endDate=?,productType=?,productClassify=?,moduleType=?,reserveDay=?,state=?,shelfTime=?,birthland=?,destination=?,prompt=?,productDesc=?,sortNum=?,remark1=?,remark2=?,remark3=?,remark4=?,remark5=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from OtProductInfo  where id=?";

	protected static final String _FillAllSQL = "select * from OtProductInfo  where id=?";

	public OtProductInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[26];
	}

	protected Schema newInstance(){
		return new OtProductInfoSchema();
	}

	protected SchemaSet newSet(){
		return new OtProductInfoSet();
	}

	public OtProductInfoSet query() {
		return query(null, -1, -1);
	}

	public OtProductInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public OtProductInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public OtProductInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (OtProductInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){productName = (String)v;return;}
		if (i == 2){shortName = (String)v;return;}
		if (i == 3){productPrice = (String)v;return;}
		if (i == 4){startDate = (Date)v;return;}
		if (i == 5){endDate = (Date)v;return;}
		if (i == 6){productType = (String)v;return;}
		if (i == 7){productClassify = (String)v;return;}
		if (i == 8){moduleType = (String)v;return;}
		if (i == 9){if(v==null){reserveDay = null;}else{reserveDay = new Integer(v.toString());}return;}
		if (i == 10){state = (String)v;return;}
		if (i == 11){shelfTime = (Date)v;return;}
		if (i == 12){birthland = (String)v;return;}
		if (i == 13){destination = (String)v;return;}
		if (i == 14){prompt = (String)v;return;}
		if (i == 15){productDesc = (String)v;return;}
		if (i == 16){if(v==null){sortNum = null;}else{sortNum = new Long(v.toString());}return;}
		if (i == 17){remark1 = (String)v;return;}
		if (i == 18){remark2 = (String)v;return;}
		if (i == 19){remark3 = (String)v;return;}
		if (i == 20){remark4 = (String)v;return;}
		if (i == 21){remark5 = (String)v;return;}
		if (i == 22){createUser = (String)v;return;}
		if (i == 23){createDate = (Date)v;return;}
		if (i == 24){modifyUser = (String)v;return;}
		if (i == 25){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return productName;}
		if (i == 2){return shortName;}
		if (i == 3){return productPrice;}
		if (i == 4){return startDate;}
		if (i == 5){return endDate;}
		if (i == 6){return productType;}
		if (i == 7){return productClassify;}
		if (i == 8){return moduleType;}
		if (i == 9){return reserveDay;}
		if (i == 10){return state;}
		if (i == 11){return shelfTime;}
		if (i == 12){return birthland;}
		if (i == 13){return destination;}
		if (i == 14){return prompt;}
		if (i == 15){return productDesc;}
		if (i == 16){return sortNum;}
		if (i == 17){return remark1;}
		if (i == 18){return remark2;}
		if (i == 19){return remark3;}
		if (i == 20){return remark4;}
		if (i == 21){return remark5;}
		if (i == 22){return createUser;}
		if (i == 23){return createDate;}
		if (i == 24){return modifyUser;}
		if (i == 25){return modifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :产品编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段productName的值，该字段的<br>
	* 字段名称 :产品名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductName() {
		return productName;
	}

	/**
	* 设置字段productName的值，该字段的<br>
	* 字段名称 :产品名<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductName(String productName) {
		this.productName = productName;
    }

	/**
	* 获取字段shortName的值，该字段的<br>
	* 字段名称 :产品简称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getshortName() {
		return shortName;
	}

	/**
	* 设置字段shortName的值，该字段的<br>
	* 字段名称 :产品简称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshortName(String shortName) {
		this.shortName = shortName;
    }

	/**
	* 获取字段productPrice的值，该字段的<br>
	* 字段名称 :产品初始价格<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductPrice() {
		return productPrice;
	}

	/**
	* 设置字段productPrice的值，该字段的<br>
	* 字段名称 :产品初始价格<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductPrice(String productPrice) {
		this.productPrice = productPrice;
    }

	/**
	* 获取字段startDate的值，该字段的<br>
	* 字段名称 :产品有效起期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getstartDate() {
		return startDate;
	}

	/**
	* 设置字段startDate的值，该字段的<br>
	* 字段名称 :产品有效起期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstartDate(Date startDate) {
		this.startDate = startDate;
    }

	/**
	* 获取字段endDate的值，该字段的<br>
	* 字段名称 :产品有效止期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getendDate() {
		return endDate;
	}

	/**
	* 设置字段endDate的值，该字段的<br>
	* 字段名称 :产品有效止期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setendDate(Date endDate) {
		this.endDate = endDate;
    }

	/**
	* 获取字段productType的值，该字段的<br>
	* 字段名称 :产品类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductType() {
		return productType;
	}

	/**
	* 设置字段productType的值，该字段的<br>
	* 字段名称 :产品类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductType(String productType) {
		this.productType = productType;
    }

	/**
	* 获取字段productClassify的值，该字段的<br>
	* 字段名称 :产品分类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductClassify() {
		return productClassify;
	}

	/**
	* 设置字段productClassify的值，该字段的<br>
	* 字段名称 :产品分类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductClassify(String productClassify) {
		this.productClassify = productClassify;
    }

	/**
	* 获取字段moduleType的值，该字段的<br>
	* 字段名称 :所属模块<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmoduleType() {
		return moduleType;
	}

	/**
	* 设置字段moduleType的值，该字段的<br>
	* 字段名称 :所属模块<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmoduleType(String moduleType) {
		this.moduleType = moduleType;
    }

	/**
	* 获取字段reserveDay的值，该字段的<br>
	* 字段名称 :提前预定天数<br>
	* 数据类型 :int(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getreserveDay() {
		if(reserveDay==null){return 0;}
		return reserveDay.intValue();
	}

	/**
	* 设置字段reserveDay的值，该字段的<br>
	* 字段名称 :提前预定天数<br>
	* 数据类型 :int(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreserveDay(int reserveDay) {
		this.reserveDay = new Integer(reserveDay);
    }

	/**
	* 设置字段reserveDay的值，该字段的<br>
	* 字段名称 :提前预定天数<br>
	* 数据类型 :int(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setreserveDay(String reserveDay) {
		if (reserveDay == null){
			this.reserveDay = null;
			return;
		}
		this.reserveDay = new Integer(reserveDay);
    }

	/**
	* 获取字段state的值，该字段的<br>
	* 字段名称 :上架状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstate() {
		return state;
	}

	/**
	* 设置字段state的值，该字段的<br>
	* 字段名称 :上架状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstate(String state) {
		this.state = state;
    }

	/**
	* 获取字段shelfTime的值，该字段的<br>
	* 字段名称 :上架时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getshelfTime() {
		return shelfTime;
	}

	/**
	* 设置字段shelfTime的值，该字段的<br>
	* 字段名称 :上架时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setshelfTime(Date shelfTime) {
		this.shelfTime = shelfTime;
    }

	/**
	* 获取字段birthland的值，该字段的<br>
	* 字段名称 :出发地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbirthland() {
		return birthland;
	}

	/**
	* 设置字段birthland的值，该字段的<br>
	* 字段名称 :出发地<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbirthland(String birthland) {
		this.birthland = birthland;
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
	* 获取字段prompt的值，该字段的<br>
	* 字段名称 :温馨提示<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprompt() {
		return prompt;
	}

	/**
	* 设置字段prompt的值，该字段的<br>
	* 字段名称 :温馨提示<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprompt(String prompt) {
		this.prompt = prompt;
    }

	/**
	* 获取字段productDesc的值，该字段的<br>
	* 字段名称 :产品描述<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductDesc() {
		return productDesc;
	}

	/**
	* 设置字段productDesc的值，该字段的<br>
	* 字段名称 :产品描述<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductDesc(String productDesc) {
		this.productDesc = productDesc;
    }

	/**
	* 获取字段sortNum的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getsortNum() {
		if(sortNum==null){return 0;}
		return sortNum.longValue();
	}

	/**
	* 设置字段sortNum的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsortNum(long sortNum) {
		this.sortNum = new Long(sortNum);
    }

	/**
	* 设置字段sortNum的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsortNum(String sortNum) {
		if (sortNum == null){
			this.sortNum = null;
			return;
		}
		this.sortNum = new Long(sortNum);
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
	* 获取字段remark4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark4() {
		return remark4;
	}

	/**
	* 设置字段remark4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark4(String remark4) {
		this.remark4 = remark4;
    }

	/**
	* 获取字段remark5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark5() {
		return remark5;
	}

	/**
	* 设置字段remark5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark5(String remark5) {
		this.remark5 = remark5;
    }

	/**
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

}