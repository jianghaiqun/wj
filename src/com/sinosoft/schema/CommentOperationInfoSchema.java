package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：评论排序点赞操作记录表<br>
 * 表代码：CommentOperationInfo<br>
 * 表主键：id<br>
 */
public class CommentOperationInfoSchema extends Schema {
	private String id;

	private Long commentId;

	private String prevSortNum;

	private String prevPraisedNum;

	private String sortNum;

	private String praisedNum;

	private String prop1;

	private String prop2;

	private String prop3;

	private String MakeUser;

	private Date MakeDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 30 , 0 , true , true),
		new SchemaColumn("commentId", DataColumn.LONG, 1, 20 , 0 , false , false),
		new SchemaColumn("prevSortNum", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("prevPraisedNum", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("sortNum", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("praisedNum", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("MakeUser", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("MakeDate", DataColumn.DATETIME, 10, 0 , 0 , false , false)
	};

	public static final String _TableCode = "CommentOperationInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into CommentOperationInfo values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update CommentOperationInfo set id=?,commentId=?,prevSortNum=?,prevPraisedNum=?,sortNum=?,praisedNum=?,prop1=?,prop2=?,prop3=?,MakeUser=?,MakeDate=? where id=?";

	protected static final String _DeleteSQL = "delete from CommentOperationInfo  where id=?";

	protected static final String _FillAllSQL = "select * from CommentOperationInfo  where id=?";

	public CommentOperationInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[11];
	}

	protected Schema newInstance(){
		return new CommentOperationInfoSchema();
	}

	protected SchemaSet newSet(){
		return new CommentOperationInfoSet();
	}

	public CommentOperationInfoSet query() {
		return query(null, -1, -1);
	}

	public CommentOperationInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public CommentOperationInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public CommentOperationInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (CommentOperationInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){if(v==null){commentId = null;}else{commentId = new Long(v.toString());}return;}
		if (i == 2){prevSortNum = (String)v;return;}
		if (i == 3){prevPraisedNum = (String)v;return;}
		if (i == 4){sortNum = (String)v;return;}
		if (i == 5){praisedNum = (String)v;return;}
		if (i == 6){prop1 = (String)v;return;}
		if (i == 7){prop2 = (String)v;return;}
		if (i == 8){prop3 = (String)v;return;}
		if (i == 9){MakeUser = (String)v;return;}
		if (i == 10){MakeDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return commentId;}
		if (i == 2){return prevSortNum;}
		if (i == 3){return prevPraisedNum;}
		if (i == 4){return sortNum;}
		if (i == 5){return praisedNum;}
		if (i == 6){return prop1;}
		if (i == 7){return prop2;}
		if (i == 8){return prop3;}
		if (i == 9){return MakeUser;}
		if (i == 10){return MakeDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :ID<br>
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
	* 获取字段prevSortNum的值，该字段的<br>
	* 字段名称 :修改前排序数<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprevSortNum() {
		return prevSortNum;
	}

	/**
	* 设置字段prevSortNum的值，该字段的<br>
	* 字段名称 :修改前排序数<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprevSortNum(String prevSortNum) {
		this.prevSortNum = prevSortNum;
    }

	/**
	* 获取字段prevPraisedNum的值，该字段的<br>
	* 字段名称 :修改前点赞数<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprevPraisedNum() {
		return prevPraisedNum;
	}

	/**
	* 设置字段prevPraisedNum的值，该字段的<br>
	* 字段名称 :修改前点赞数<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprevPraisedNum(String prevPraisedNum) {
		this.prevPraisedNum = prevPraisedNum;
    }

	/**
	* 获取字段sortNum的值，该字段的<br>
	* 字段名称 :修改后排序数<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsortNum() {
		return sortNum;
	}

	/**
	* 设置字段sortNum的值，该字段的<br>
	* 字段名称 :修改后排序数<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsortNum(String sortNum) {
		this.sortNum = sortNum;
    }

	/**
	* 获取字段praisedNum的值，该字段的<br>
	* 字段名称 :修改后点赞数<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpraisedNum() {
		return praisedNum;
	}

	/**
	* 设置字段praisedNum的值，该字段的<br>
	* 字段名称 :修改后点赞数<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpraisedNum(String praisedNum) {
		this.praisedNum = praisedNum;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段MakeUser的值，该字段的<br>
	* 字段名称 :操作人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMakeUser() {
		return MakeUser;
	}

	/**
	* 设置字段MakeUser的值，该字段的<br>
	* 字段名称 :操作人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMakeUser(String makeUser) {
		this.MakeUser = makeUser;
    }

	/**
	* 获取字段MakeDate的值，该字段的<br>
	* 字段名称 :操作日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getMakeDate() {
		return MakeDate;
	}

	/**
	* 设置字段MakeDate的值，该字段的<br>
	* 字段名称 :操作日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMakeDate(Date makeDate) {
		this.MakeDate = makeDate;
    }

}