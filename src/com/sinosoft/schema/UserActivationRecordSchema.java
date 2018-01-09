package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：用户激活记录表<br>
 * 表代码：UserActivationRecord<br>
 * 表主键：id<br>
 */
public class UserActivationRecordSchema extends Schema {
	private String id;

	private String category;

	private String activationKey;

	private String memberid;

	private Date createDate;

	private String activitySn;

	private String basePrice;

	private String payPrice;

	private String oldPrice;

	private String ProductGenera;

	private String sendWay;

	private String sendTo;

	private String sandMassage;

	private String sendStatus;

	private String productName;

	private String productUrl;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("category", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("activationKey", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("memberid", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 4, 0 , 0 , true , false),
		new SchemaColumn("activitySn", DataColumn.STRING, 5, 100 , 0 , true , false),
		new SchemaColumn("basePrice", DataColumn.STRING, 6, 100 , 0 , true , false),
		new SchemaColumn("payPrice", DataColumn.STRING, 7, 100 , 0 , true , false),
		new SchemaColumn("oldPrice", DataColumn.STRING, 8, 200 , 0 , true , false),
		new SchemaColumn("ProductGenera", DataColumn.STRING, 9, 500 , 0 , true , false),
		new SchemaColumn("sendWay", DataColumn.STRING, 10, 100 , 0 , true , false),
		new SchemaColumn("sendTo", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("sandMassage", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("sendStatus", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("productName", DataColumn.STRING, 14, 100 , 0 , false , false),
		new SchemaColumn("productUrl", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 16, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 17, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 18, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 19, 100 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 20, 100 , 0 , false , false)
	};

	public static final String _TableCode = "UserActivationRecord";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into UserActivationRecord values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update UserActivationRecord set id=?,category=?,activationKey=?,memberid=?,createDate=?,activitySn=?,basePrice=?,payPrice=?,oldPrice=?,ProductGenera=?,sendWay=?,sendTo=?,sandMassage=?,sendStatus=?,productName=?,productUrl=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=? where id=?";

	protected static final String _DeleteSQL = "delete from UserActivationRecord  where id=?";

	protected static final String _FillAllSQL = "select * from UserActivationRecord  where id=?";

	public UserActivationRecordSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[21];
	}

	protected Schema newInstance(){
		return new UserActivationRecordSchema();
	}

	protected SchemaSet newSet(){
		return new UserActivationRecordSet();
	}

	public UserActivationRecordSet query() {
		return query(null, -1, -1);
	}

	public UserActivationRecordSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public UserActivationRecordSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public UserActivationRecordSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (UserActivationRecordSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){category = (String)v;return;}
		if (i == 2){activationKey = (String)v;return;}
		if (i == 3){memberid = (String)v;return;}
		if (i == 4){createDate = (Date)v;return;}
		if (i == 5){activitySn = (String)v;return;}
		if (i == 6){basePrice = (String)v;return;}
		if (i == 7){payPrice = (String)v;return;}
		if (i == 8){oldPrice = (String)v;return;}
		if (i == 9){ProductGenera = (String)v;return;}
		if (i == 10){sendWay = (String)v;return;}
		if (i == 11){sendTo = (String)v;return;}
		if (i == 12){sandMassage = (String)v;return;}
		if (i == 13){sendStatus = (String)v;return;}
		if (i == 14){productName = (String)v;return;}
		if (i == 15){productUrl = (String)v;return;}
		if (i == 16){Prop1 = (String)v;return;}
		if (i == 17){Prop2 = (String)v;return;}
		if (i == 18){Prop3 = (String)v;return;}
		if (i == 19){Prop4 = (String)v;return;}
		if (i == 20){Prop5 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return category;}
		if (i == 2){return activationKey;}
		if (i == 3){return memberid;}
		if (i == 4){return createDate;}
		if (i == 5){return activitySn;}
		if (i == 6){return basePrice;}
		if (i == 7){return payPrice;}
		if (i == 8){return oldPrice;}
		if (i == 9){return ProductGenera;}
		if (i == 10){return sendWay;}
		if (i == 11){return sendTo;}
		if (i == 12){return sandMassage;}
		if (i == 13){return sendStatus;}
		if (i == 14){return productName;}
		if (i == 15){return productUrl;}
		if (i == 16){return Prop1;}
		if (i == 17){return Prop2;}
		if (i == 18){return Prop3;}
		if (i == 19){return Prop4;}
		if (i == 20){return Prop5;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段category的值，该字段的<br>
	* 字段名称 :类别(收藏/待支付)<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getcategory() {
		return category;
	}

	/**
	* 设置字段category的值，该字段的<br>
	* 字段名称 :类别(收藏/待支付)<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setcategory(String category) {
		this.category = category;
    }

	/**
	* 获取字段activationKey的值，该字段的<br>
	* 字段名称 :暂存或收藏关键字<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getactivationKey() {
		return activationKey;
	}

	/**
	* 设置字段activationKey的值，该字段的<br>
	* 字段名称 :暂存或收藏关键字<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setactivationKey(String activationKey) {
		this.activationKey = activationKey;
    }

	/**
	* 获取字段memberid的值，该字段的<br>
	* 字段名称 :会员id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getmemberid() {
		return memberid;
	}

	/**
	* 设置字段memberid的值，该字段的<br>
	* 字段名称 :会员id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setmemberid(String memberid) {
		this.memberid = memberid;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段activitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getactivitySn() {
		return activitySn;
	}

	/**
	* 设置字段activitySn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setactivitySn(String activitySn) {
		this.activitySn = activitySn;
    }

	/**
	* 获取字段basePrice的值，该字段的<br>
	* 字段名称 :原价<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getbasePrice() {
		return basePrice;
	}

	/**
	* 设置字段basePrice的值，该字段的<br>
	* 字段名称 :原价<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setbasePrice(String basePrice) {
		this.basePrice = basePrice;
    }

	/**
	* 获取字段payPrice的值，该字段的<br>
	* 字段名称 :现价<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getpayPrice() {
		return payPrice;
	}

	/**
	* 设置字段payPrice的值，该字段的<br>
	* 字段名称 :现价<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setpayPrice(String payPrice) {
		this.payPrice = payPrice;
    }

	/**
	* 获取字段oldPrice的值，该字段的<br>
	* 字段名称 :存储(收藏/暂存)价格<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getoldPrice() {
		return oldPrice;
	}

	/**
	* 设置字段oldPrice的值，该字段的<br>
	* 字段名称 :存储(收藏/暂存)价格<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setoldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
    }

	/**
	* 获取字段ProductGenera的值，该字段的<br>
	* 字段名称 :保险品类<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getProductGenera() {
		return ProductGenera;
	}

	/**
	* 设置字段ProductGenera的值，该字段的<br>
	* 字段名称 :保险品类<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setProductGenera(String productGenera) {
		this.ProductGenera = productGenera;
    }

	/**
	* 获取字段sendWay的值，该字段的<br>
	* 字段名称 :发送方式<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getsendWay() {
		return sendWay;
	}

	/**
	* 设置字段sendWay的值，该字段的<br>
	* 字段名称 :发送方式<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setsendWay(String sendWay) {
		this.sendWay = sendWay;
    }

	/**
	* 获取字段sendTo的值，该字段的<br>
	* 字段名称 :发送地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsendTo() {
		return sendTo;
	}

	/**
	* 设置字段sendTo的值，该字段的<br>
	* 字段名称 :发送地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsendTo(String sendTo) {
		this.sendTo = sendTo;
    }

	/**
	* 获取字段sandMassage的值，该字段的<br>
	* 字段名称 :发送内容<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsandMassage() {
		return sandMassage;
	}

	/**
	* 设置字段sandMassage的值，该字段的<br>
	* 字段名称 :发送内容<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsandMassage(String sandMassage) {
		this.sandMassage = sandMassage;
    }

	/**
	* 获取字段sendStatus的值，该字段的<br>
	* 字段名称 :发送状态<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsendStatus() {
		return sendStatus;
	}

	/**
	* 设置字段sendStatus的值，该字段的<br>
	* 字段名称 :发送状态<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
    }

	/**
	* 获取字段productName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductName() {
		return productName;
	}

	/**
	* 设置字段productName的值，该字段的<br>
	* 字段名称 :产品名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductName(String productName) {
		this.productName = productName;
    }

	/**
	* 获取字段productUrl的值，该字段的<br>
	* 字段名称 :产品链接<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductUrl() {
		return productUrl;
	}

	/**
	* 设置字段productUrl的值，该字段的<br>
	* 字段名称 :产品链接<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductUrl(String productUrl) {
		this.productUrl = productUrl;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :备用5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :备用5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

}