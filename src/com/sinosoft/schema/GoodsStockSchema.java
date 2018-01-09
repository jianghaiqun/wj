package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：商品库存表<br>
 * 表代码：GoodsStock<br>
 * 表主键：id<br>
 */
public class GoodsStockSchema extends Schema {
	private String id;

	private String giftID;

	private String goodsName;

	private String cardNo;

	private String passWord;

	private String payPoints;

	private String memberid;

	private Date outDate;

	private String status;

	private String type;

	private String insuredId;

	private String memo;

	private String version;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String prop5;

	private String prop6;

	private String prop7;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("giftID", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("goodsName", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("cardNo", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("passWord", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("payPoints", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("memberid", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("outDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("status", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("type", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("insuredId", DataColumn.STRING, 10, 32 , 0 , false , false),
		new SchemaColumn("memo", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("version", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("prop5", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("prop6", DataColumn.STRING, 18, 255 , 0 , false , false),
		new SchemaColumn("prop7", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 20, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 21, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 22, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 23, 0 , 0 , false , false)
	};

	public static final String _TableCode = "GoodsStock";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into GoodsStock values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update GoodsStock set id=?,giftID=?,goodsName=?,cardNo=?,passWord=?,payPoints=?,memberid=?,outDate=?,status=?,type=?,insuredId=?,memo=?,version=?,prop1=?,prop2=?,prop3=?,prop4=?,prop5=?,prop6=?,prop7=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from GoodsStock  where id=?";

	protected static final String _FillAllSQL = "select * from GoodsStock  where id=?";

	public GoodsStockSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[24];
	}

	protected Schema newInstance(){
		return new GoodsStockSchema();
	}

	protected SchemaSet newSet(){
		return new GoodsStockSet();
	}

	public GoodsStockSet query() {
		return query(null, -1, -1);
	}

	public GoodsStockSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public GoodsStockSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public GoodsStockSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (GoodsStockSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){giftID = (String)v;return;}
		if (i == 2){goodsName = (String)v;return;}
		if (i == 3){cardNo = (String)v;return;}
		if (i == 4){passWord = (String)v;return;}
		if (i == 5){payPoints = (String)v;return;}
		if (i == 6){memberid = (String)v;return;}
		if (i == 7){outDate = (Date)v;return;}
		if (i == 8){status = (String)v;return;}
		if (i == 9){type = (String)v;return;}
		if (i == 10){insuredId = (String)v;return;}
		if (i == 11){memo = (String)v;return;}
		if (i == 12){version = (String)v;return;}
		if (i == 13){prop1 = (String)v;return;}
		if (i == 14){prop2 = (String)v;return;}
		if (i == 15){prop3 = (String)v;return;}
		if (i == 16){prop4 = (String)v;return;}
		if (i == 17){prop5 = (String)v;return;}
		if (i == 18){prop6 = (String)v;return;}
		if (i == 19){prop7 = (String)v;return;}
		if (i == 20){createUser = (String)v;return;}
		if (i == 21){createDate = (Date)v;return;}
		if (i == 22){modifyUser = (String)v;return;}
		if (i == 23){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return giftID;}
		if (i == 2){return goodsName;}
		if (i == 3){return cardNo;}
		if (i == 4){return passWord;}
		if (i == 5){return payPoints;}
		if (i == 6){return memberid;}
		if (i == 7){return outDate;}
		if (i == 8){return status;}
		if (i == 9){return type;}
		if (i == 10){return insuredId;}
		if (i == 11){return memo;}
		if (i == 12){return version;}
		if (i == 13){return prop1;}
		if (i == 14){return prop2;}
		if (i == 15){return prop3;}
		if (i == 16){return prop4;}
		if (i == 17){return prop5;}
		if (i == 18){return prop6;}
		if (i == 19){return prop7;}
		if (i == 20){return createUser;}
		if (i == 21){return createDate;}
		if (i == 22){return modifyUser;}
		if (i == 23){return modifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段giftID的值，该字段的<br>
	* 字段名称 :礼品ID<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getgiftID() {
		return giftID;
	}

	/**
	* 设置字段giftID的值，该字段的<br>
	* 字段名称 :礼品ID<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgiftID(String giftID) {
		this.giftID = giftID;
    }

	/**
	* 获取字段goodsName的值，该字段的<br>
	* 字段名称 :商品名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getgoodsName() {
		return goodsName;
	}

	/**
	* 设置字段goodsName的值，该字段的<br>
	* 字段名称 :商品名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgoodsName(String goodsName) {
		this.goodsName = goodsName;
    }

	/**
	* 获取字段cardNo的值，该字段的<br>
	* 字段名称 :卡号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcardNo() {
		return cardNo;
	}

	/**
	* 设置字段cardNo的值，该字段的<br>
	* 字段名称 :卡号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcardNo(String cardNo) {
		this.cardNo = cardNo;
    }

	/**
	* 获取字段passWord的值，该字段的<br>
	* 字段名称 :密码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpassWord() {
		return passWord;
	}

	/**
	* 设置字段passWord的值，该字段的<br>
	* 字段名称 :密码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpassWord(String passWord) {
		this.passWord = passWord;
    }

	/**
	* 获取字段payPoints的值，该字段的<br>
	* 字段名称 :实际兑换值<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayPoints() {
		return payPoints;
	}

	/**
	* 设置字段payPoints的值，该字段的<br>
	* 字段名称 :实际兑换值<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayPoints(String payPoints) {
		this.payPoints = payPoints;
    }

	/**
	* 获取字段memberid的值，该字段的<br>
	* 字段名称 :兑换人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemberid() {
		return memberid;
	}

	/**
	* 设置字段memberid的值，该字段的<br>
	* 字段名称 :兑换人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemberid(String memberid) {
		this.memberid = memberid;
    }

	/**
	* 获取字段outDate的值，该字段的<br>
	* 字段名称 :有效期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getoutDate() {
		return outDate;
	}

	/**
	* 设置字段outDate的值，该字段的<br>
	* 字段名称 :有效期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setoutDate(Date outDate) {
		this.outDate = outDate;
    }

	/**
	* 获取字段status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstatus() {
		return status;
	}

	/**
	* 设置字段status的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatus(String status) {
		this.status = status;
    }

	/**
	* 获取字段type的值，该字段的<br>
	* 字段名称 :类别<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettype() {
		return type;
	}

	/**
	* 设置字段type的值，该字段的<br>
	* 字段名称 :类别<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settype(String type) {
		this.type = type;
    }

	/**
	* 获取字段insuredId的值，该字段的<br>
	* 字段名称 :被保人Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsuredId() {
		return insuredId;
	}

	/**
	* 设置字段insuredId的值，该字段的<br>
	* 字段名称 :被保人Id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuredId(String insuredId) {
		this.insuredId = insuredId;
    }

	/**
	* 获取字段memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemo() {
		return memo;
	}

	/**
	* 设置字段memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemo(String memo) {
		this.memo = memo;
    }

	/**
	* 获取字段version的值，该字段的<br>
	* 字段名称 :更新版本号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getversion() {
		return version;
	}

	/**
	* 设置字段version的值，该字段的<br>
	* 字段名称 :更新版本号<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setversion(String version) {
		this.version = version;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* 获取字段prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop5() {
		return prop5;
	}

	/**
	* 设置字段prop5的值，该字段的<br>
	* 字段名称 :备用字段5<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop5(String prop5) {
		this.prop5 = prop5;
    }

	/**
	* 获取字段prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop6() {
		return prop6;
	}

	/**
	* 设置字段prop6的值，该字段的<br>
	* 字段名称 :备用字段6<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop6(String prop6) {
		this.prop6 = prop6;
    }

	/**
	* 获取字段prop7的值，该字段的<br>
	* 字段名称 :备用字段7<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop7() {
		return prop7;
	}

	/**
	* 设置字段prop7的值，该字段的<br>
	* 字段名称 :备用字段7<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop7(String prop7) {
		this.prop7 = prop7;
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