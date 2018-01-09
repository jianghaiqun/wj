package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：礼品表<br>
 * 表代码：GiftClassify<br>
 * 表主键：id<br>
 */
public class GiftClassifySchema extends Schema {
	private String id;

	private String giftTitle;

	private String giftName;

	private String lastNum;

	private String type;

	private String logoUrl;

	private String modelType;

	private String productID;

	private String points;

	private String giftPrice;

	private String popularity;

	private String recommend;

	private Date startDate;

	private Date endDate;

	private String metaDescription;

	private String status;

	private String linkUrl;

	private Long orderFlag;

	private String flowtype;

	private String FlowSize;

	private String FuLuGoodsID;

	private String PointsExchangeType;

	private String version;

	private String isWap;

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
		new SchemaColumn("giftTitle", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("giftName", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("lastNum", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("type", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("logoUrl", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("modelType", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("productID", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("points", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("giftPrice", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("popularity", DataColumn.STRING, 10, 30 , 0 , false , false),
		new SchemaColumn("recommend", DataColumn.STRING, 11, 10 , 0 , false , false),
		new SchemaColumn("startDate", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("endDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("metaDescription", DataColumn.CLOB, 14, 0 , 0 , false , false),
		new SchemaColumn("status", DataColumn.STRING, 15, 10 , 0 , false , false),
		new SchemaColumn("linkUrl", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("orderFlag", DataColumn.LONG, 17, 20 , 0 , false , false),
		new SchemaColumn("flowtype", DataColumn.STRING, 18, 10 , 0 , false , false),
		new SchemaColumn("FlowSize", DataColumn.STRING, 19, 10 , 0 , false , false),
		new SchemaColumn("FuLuGoodsID", DataColumn.STRING, 20, 10 , 0 , false , false),
		new SchemaColumn("PointsExchangeType", DataColumn.STRING, 21, 10 , 0 , false , false),
		new SchemaColumn("version", DataColumn.STRING, 22, 255 , 0 , false , false),
		new SchemaColumn("isWap", DataColumn.STRING, 23, 20 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 24, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 25, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 26, 255 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 27, 255 , 0 , false , false),
		new SchemaColumn("prop5", DataColumn.STRING, 28, 255 , 0 , false , false),
		new SchemaColumn("prop6", DataColumn.STRING, 29, 255 , 0 , false , false),
		new SchemaColumn("prop7", DataColumn.STRING, 30, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 31, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 32, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 33, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 34, 0 , 0 , false , false)
	};

	public static final String _TableCode = "GiftClassify";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into GiftClassify values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update GiftClassify set id=?,giftTitle=?,giftName=?,lastNum=?,type=?,logoUrl=?,modelType=?,productID=?,points=?,giftPrice=?,popularity=?,recommend=?,startDate=?,endDate=?,metaDescription=?,status=?,linkUrl=?,orderFlag=?,flowtype=?,FlowSize=?,FuLuGoodsID=?,PointsExchangeType=?,version=?,isWap=?,prop1=?,prop2=?,prop3=?,prop4=?,prop5=?,prop6=?,prop7=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from GiftClassify  where id=?";

	protected static final String _FillAllSQL = "select * from GiftClassify  where id=?";

	public GiftClassifySchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[35];
	}

	protected Schema newInstance(){
		return new GiftClassifySchema();
	}

	protected SchemaSet newSet(){
		return new GiftClassifySet();
	}

	public GiftClassifySet query() {
		return query(null, -1, -1);
	}

	public GiftClassifySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public GiftClassifySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public GiftClassifySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (GiftClassifySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){giftTitle = (String)v;return;}
		if (i == 2){giftName = (String)v;return;}
		if (i == 3){lastNum = (String)v;return;}
		if (i == 4){type = (String)v;return;}
		if (i == 5){logoUrl = (String)v;return;}
		if (i == 6){modelType = (String)v;return;}
		if (i == 7){productID = (String)v;return;}
		if (i == 8){points = (String)v;return;}
		if (i == 9){giftPrice = (String)v;return;}
		if (i == 10){popularity = (String)v;return;}
		if (i == 11){recommend = (String)v;return;}
		if (i == 12){startDate = (Date)v;return;}
		if (i == 13){endDate = (Date)v;return;}
		if (i == 14){metaDescription = (String)v;return;}
		if (i == 15){status = (String)v;return;}
		if (i == 16){linkUrl = (String)v;return;}
		if (i == 17){if(v==null){orderFlag = null;}else{orderFlag = new Long(v.toString());}return;}
		if (i == 18){flowtype = (String)v;return;}
		if (i == 19){FlowSize = (String)v;return;}
		if (i == 20){FuLuGoodsID = (String)v;return;}
		if (i == 21){PointsExchangeType = (String)v;return;}
		if (i == 22){version = (String)v;return;}
		if (i == 23){isWap = (String)v;return;}
		if (i == 24){prop1 = (String)v;return;}
		if (i == 25){prop2 = (String)v;return;}
		if (i == 26){prop3 = (String)v;return;}
		if (i == 27){prop4 = (String)v;return;}
		if (i == 28){prop5 = (String)v;return;}
		if (i == 29){prop6 = (String)v;return;}
		if (i == 30){prop7 = (String)v;return;}
		if (i == 31){createUser = (String)v;return;}
		if (i == 32){createDate = (Date)v;return;}
		if (i == 33){modifyUser = (String)v;return;}
		if (i == 34){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return giftTitle;}
		if (i == 2){return giftName;}
		if (i == 3){return lastNum;}
		if (i == 4){return type;}
		if (i == 5){return logoUrl;}
		if (i == 6){return modelType;}
		if (i == 7){return productID;}
		if (i == 8){return points;}
		if (i == 9){return giftPrice;}
		if (i == 10){return popularity;}
		if (i == 11){return recommend;}
		if (i == 12){return startDate;}
		if (i == 13){return endDate;}
		if (i == 14){return metaDescription;}
		if (i == 15){return status;}
		if (i == 16){return linkUrl;}
		if (i == 17){return orderFlag;}
		if (i == 18){return flowtype;}
		if (i == 19){return FlowSize;}
		if (i == 20){return FuLuGoodsID;}
		if (i == 21){return PointsExchangeType;}
		if (i == 22){return version;}
		if (i == 23){return isWap;}
		if (i == 24){return prop1;}
		if (i == 25){return prop2;}
		if (i == 26){return prop3;}
		if (i == 27){return prop4;}
		if (i == 28){return prop5;}
		if (i == 29){return prop6;}
		if (i == 30){return prop7;}
		if (i == 31){return createUser;}
		if (i == 32){return createDate;}
		if (i == 33){return modifyUser;}
		if (i == 34){return modifyDate;}
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
	* 获取字段giftTitle的值，该字段的<br>
	* 字段名称 :礼品标题<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getgiftTitle() {
		return giftTitle;
	}

	/**
	* 设置字段giftTitle的值，该字段的<br>
	* 字段名称 :礼品标题<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgiftTitle(String giftTitle) {
		this.giftTitle = giftTitle;
    }

	/**
	* 获取字段giftName的值，该字段的<br>
	* 字段名称 :礼品名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getgiftName() {
		return giftName;
	}

	/**
	* 设置字段giftName的值，该字段的<br>
	* 字段名称 :礼品名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgiftName(String giftName) {
		this.giftName = giftName;
    }

	/**
	* 获取字段lastNum的值，该字段的<br>
	* 字段名称 :库存数量<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getlastNum() {
		return lastNum;
	}

	/**
	* 设置字段lastNum的值，该字段的<br>
	* 字段名称 :库存数量<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setlastNum(String lastNum) {
		this.lastNum = lastNum;
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
	* 获取字段logoUrl的值，该字段的<br>
	* 字段名称 :礼品图片<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getlogoUrl() {
		return logoUrl;
	}

	/**
	* 设置字段logoUrl的值，该字段的<br>
	* 字段名称 :礼品图片<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setlogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
    }

	/**
	* 获取字段modelType的值，该字段的<br>
	* 字段名称 :归属模块<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodelType() {
		return modelType;
	}

	/**
	* 设置字段modelType的值，该字段的<br>
	* 字段名称 :归属模块<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodelType(String modelType) {
		this.modelType = modelType;
    }

	/**
	* 获取字段productID的值，该字段的<br>
	* 字段名称 :保险产品ID<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductID() {
		return productID;
	}

	/**
	* 设置字段productID的值，该字段的<br>
	* 字段名称 :保险产品ID<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductID(String productID) {
		this.productID = productID;
    }

	/**
	* 获取字段points的值，该字段的<br>
	* 字段名称 :积分兑换值<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpoints() {
		return points;
	}

	/**
	* 设置字段points的值，该字段的<br>
	* 字段名称 :积分兑换值<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpoints(String points) {
		this.points = points;
    }

	/**
	* 获取字段giftPrice的值，该字段的<br>
	* 字段名称 :礼品价值<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getgiftPrice() {
		return giftPrice;
	}

	/**
	* 设置字段giftPrice的值，该字段的<br>
	* 字段名称 :礼品价值<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setgiftPrice(String giftPrice) {
		this.giftPrice = giftPrice;
    }

	/**
	* 获取字段popularity的值，该字段的<br>
	* 字段名称 :人气<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpopularity() {
		return popularity;
	}

	/**
	* 设置字段popularity的值，该字段的<br>
	* 字段名称 :人气<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpopularity(String popularity) {
		this.popularity = popularity;
    }

	/**
	* 获取字段recommend的值，该字段的<br>
	* 字段名称 :推荐属性<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y:推荐 其他：不是推荐<br>
	*/
	public String getrecommend() {
		return recommend;
	}

	/**
	* 设置字段recommend的值，该字段的<br>
	* 字段名称 :推荐属性<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y:推荐 其他：不是推荐<br>
	*/
	public void setrecommend(String recommend) {
		this.recommend = recommend;
    }

	/**
	* 获取字段startDate的值，该字段的<br>
	* 字段名称 :开始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getstartDate() {
		return startDate;
	}

	/**
	* 设置字段startDate的值，该字段的<br>
	* 字段名称 :开始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstartDate(Date startDate) {
		this.startDate = startDate;
    }

	/**
	* 获取字段endDate的值，该字段的<br>
	* 字段名称 :结束时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getendDate() {
		return endDate;
	}

	/**
	* 设置字段endDate的值，该字段的<br>
	* 字段名称 :结束时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setendDate(Date endDate) {
		this.endDate = endDate;
    }

	/**
	* 获取字段metaDescription的值，该字段的<br>
	* 字段名称 :温馨提示<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmetaDescription() {
		return metaDescription;
	}

	/**
	* 设置字段metaDescription的值，该字段的<br>
	* 字段名称 :温馨提示<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
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
	* 获取字段linkUrl的值，该字段的<br>
	* 字段名称 :产品跳转地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getlinkUrl() {
		return linkUrl;
	}

	/**
	* 设置字段linkUrl的值，该字段的<br>
	* 字段名称 :产品跳转地址<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setlinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
    }

	/**
	* 获取字段orderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getorderFlag() {
		if(orderFlag==null){return 0;}
		return orderFlag.longValue();
	}

	/**
	* 设置字段orderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderFlag(long orderFlag) {
		this.orderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段orderFlag的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderFlag(String orderFlag) {
		if (orderFlag == null){
			this.orderFlag = null;
			return;
		}
		this.orderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段flowtype的值，该字段的<br>
	* 字段名称 :流量类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getflowtype() {
		return flowtype;
	}

	/**
	* 设置字段flowtype的值，该字段的<br>
	* 字段名称 :流量类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setflowtype(String flowtype) {
		this.flowtype = flowtype;
    }

	/**
	* 获取字段FlowSize的值，该字段的<br>
	* 字段名称 :流量大小<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFlowSize() {
		return FlowSize;
	}

	/**
	* 设置字段FlowSize的值，该字段的<br>
	* 字段名称 :流量大小<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFlowSize(String flowSize) {
		this.FlowSize = flowSize;
    }

	/**
	* 获取字段FuLuGoodsID的值，该字段的<br>
	* 字段名称 :福禄商品编号<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFuLuGoodsID() {
		return FuLuGoodsID;
	}

	/**
	* 设置字段FuLuGoodsID的值，该字段的<br>
	* 字段名称 :福禄商品编号<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFuLuGoodsID(String fuLuGoodsID) {
		this.FuLuGoodsID = fuLuGoodsID;
    }

	/**
	* 获取字段PointsExchangeType的值，该字段的<br>
	* 字段名称 :模式<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPointsExchangeType() {
		return PointsExchangeType;
	}

	/**
	* 设置字段PointsExchangeType的值，该字段的<br>
	* 字段名称 :模式<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPointsExchangeType(String pointsExchangeType) {
		this.PointsExchangeType = pointsExchangeType;
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
	* 获取字段isWap的值，该字段的<br>
	* 字段名称 :是否wap显示<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisWap() {
		return isWap;
	}

	/**
	* 设置字段isWap的值，该字段的<br>
	* 字段名称 :是否wap显示<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisWap(String isWap) {
		this.isWap = isWap;
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