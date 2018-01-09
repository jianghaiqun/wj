package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：评论点赞记录表<br>
 * 表代码：CommentPraisedInfo<br>
 * 表主键：id<br>
 */
public class CommentPraisedInfoSchema extends Schema {
	private String id;

	private Long commentId;

	private Long RelaID;

	private String orderSn;

	private String productId;

	private String productName;

	private String userIP;

	private String isPraised;

	private Date praisedDate;

	private Date cancelDate;

	private String prop1;

	private String prop2;

	private String prop3;

	private String CreateUser;

	private Date CreateDate;

	private String ModifyUser;

	private Date ModifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("commentId", DataColumn.LONG, 1, 20 , 0 , false , false),
		new SchemaColumn("RelaID", DataColumn.LONG, 2, 20 , 0 , false , false),
		new SchemaColumn("orderSn", DataColumn.STRING, 3, 25 , 0 , false , false),
		new SchemaColumn("productId", DataColumn.STRING, 4, 30 , 0 , false , false),
		new SchemaColumn("productName", DataColumn.STRING, 5, 200 , 0 , false , false),
		new SchemaColumn("userIP", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("isPraised", DataColumn.STRING, 7, 2 , 0 , false , false),
		new SchemaColumn("praisedDate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("cancelDate", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("CreateUser", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("CreateDate", DataColumn.DATETIME, 14, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("ModifyDate", DataColumn.DATETIME, 16, 0 , 0 , false , false)
	};

	public static final String _TableCode = "CommentPraisedInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into CommentPraisedInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update CommentPraisedInfo set id=?,commentId=?,RelaID=?,orderSn=?,productId=?,productName=?,userIP=?,isPraised=?,praisedDate=?,cancelDate=?,prop1=?,prop2=?,prop3=?,CreateUser=?,CreateDate=?,ModifyUser=?,ModifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from CommentPraisedInfo  where id=?";

	protected static final String _FillAllSQL = "select * from CommentPraisedInfo  where id=?";

	public CommentPraisedInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[17];
	}

	protected Schema newInstance(){
		return new CommentPraisedInfoSchema();
	}

	protected SchemaSet newSet(){
		return new CommentPraisedInfoSet();
	}

	public CommentPraisedInfoSet query() {
		return query(null, -1, -1);
	}

	public CommentPraisedInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public CommentPraisedInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public CommentPraisedInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (CommentPraisedInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){if(v==null){commentId = null;}else{commentId = new Long(v.toString());}return;}
		if (i == 2){if(v==null){RelaID = null;}else{RelaID = new Long(v.toString());}return;}
		if (i == 3){orderSn = (String)v;return;}
		if (i == 4){productId = (String)v;return;}
		if (i == 5){productName = (String)v;return;}
		if (i == 6){userIP = (String)v;return;}
		if (i == 7){isPraised = (String)v;return;}
		if (i == 8){praisedDate = (Date)v;return;}
		if (i == 9){cancelDate = (Date)v;return;}
		if (i == 10){prop1 = (String)v;return;}
		if (i == 11){prop2 = (String)v;return;}
		if (i == 12){prop3 = (String)v;return;}
		if (i == 13){CreateUser = (String)v;return;}
		if (i == 14){CreateDate = (Date)v;return;}
		if (i == 15){ModifyUser = (String)v;return;}
		if (i == 16){ModifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return commentId;}
		if (i == 2){return RelaID;}
		if (i == 3){return orderSn;}
		if (i == 4){return productId;}
		if (i == 5){return productName;}
		if (i == 6){return userIP;}
		if (i == 7){return isPraised;}
		if (i == 8){return praisedDate;}
		if (i == 9){return cancelDate;}
		if (i == 10){return prop1;}
		if (i == 11){return prop2;}
		if (i == 12){return prop3;}
		if (i == 13){return CreateUser;}
		if (i == 14){return CreateDate;}
		if (i == 15){return ModifyUser;}
		if (i == 16){return ModifyDate;}
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
	* 获取字段commentId的值，该字段的<br>
	* 字段名称 :评论ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getcommentId() {
		if(commentId==null){return 0;}
		return commentId.longValue();
	}

	/**
	* 设置字段commentId的值，该字段的<br>
	* 字段名称 :评论ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcommentId(long commentId) {
		this.commentId = new Long(commentId);
    }

	/**
	* 设置字段commentId的值，该字段的<br>
	* 字段名称 :评论ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcommentId(String commentId) {
		if (commentId == null){
			this.commentId = null;
			return;
		}
		this.commentId = new Long(commentId);
    }

	/**
	* 获取字段RelaID的值，该字段的<br>
	* 字段名称 :关联ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getRelaID() {
		if(RelaID==null){return 0;}
		return RelaID.longValue();
	}

	/**
	* 设置字段RelaID的值，该字段的<br>
	* 字段名称 :关联ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRelaID(long relaID) {
		this.RelaID = new Long(relaID);
    }

	/**
	* 设置字段RelaID的值，该字段的<br>
	* 字段名称 :关联ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRelaID(String relaID) {
		if (relaID == null){
			this.RelaID = null;
			return;
		}
		this.RelaID = new Long(relaID);
    }

	/**
	* 获取字段orderSn的值，该字段的<br>
	* 字段名称 :评论订单号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getorderSn() {
		return orderSn;
	}

	/**
	* 设置字段orderSn的值，该字段的<br>
	* 字段名称 :评论订单号<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setorderSn(String orderSn) {
		this.orderSn = orderSn;
    }

	/**
	* 获取字段productId的值，该字段的<br>
	* 字段名称 :评论产品ID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductId() {
		return productId;
	}

	/**
	* 设置字段productId的值，该字段的<br>
	* 字段名称 :评论产品ID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductId(String productId) {
		this.productId = productId;
    }

	/**
	* 获取字段productName的值，该字段的<br>
	* 字段名称 :评论产品名<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getproductName() {
		return productName;
	}

	/**
	* 设置字段productName的值，该字段的<br>
	* 字段名称 :评论产品名<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setproductName(String productName) {
		this.productName = productName;
    }

	/**
	* 获取字段userIP的值，该字段的<br>
	* 字段名称 :用户IP<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getuserIP() {
		return userIP;
	}

	/**
	* 设置字段userIP的值，该字段的<br>
	* 字段名称 :用户IP<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setuserIP(String userIP) {
		this.userIP = userIP;
    }

	/**
	* 获取字段isPraised的值，该字段的<br>
	* 字段名称 :是否点赞<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisPraised() {
		return isPraised;
	}

	/**
	* 设置字段isPraised的值，该字段的<br>
	* 字段名称 :是否点赞<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisPraised(String isPraised) {
		this.isPraised = isPraised;
    }

	/**
	* 获取字段praisedDate的值，该字段的<br>
	* 字段名称 :点赞时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getpraisedDate() {
		return praisedDate;
	}

	/**
	* 设置字段praisedDate的值，该字段的<br>
	* 字段名称 :点赞时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpraisedDate(Date praisedDate) {
		this.praisedDate = praisedDate;
    }

	/**
	* 获取字段cancelDate的值，该字段的<br>
	* 字段名称 :取消点赞时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcancelDate() {
		return cancelDate;
	}

	/**
	* 设置字段cancelDate的值，该字段的<br>
	* 字段名称 :取消点赞时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
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
	* 获取字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCreateUser() {
		return CreateUser;
	}

	/**
	* 设置字段CreateUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateUser(String createUser) {
		this.CreateUser = createUser;
    }

	/**
	* 获取字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateDate() {
		return CreateDate;
	}

	/**
	* 设置字段CreateDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateDate(Date createDate) {
		this.CreateDate = createDate;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyDate() {
		return ModifyDate;
	}

	/**
	* 设置字段ModifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyDate(Date modifyDate) {
		this.ModifyDate = modifyDate;
    }

}