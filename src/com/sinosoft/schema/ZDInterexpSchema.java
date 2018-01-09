package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：互动经验<br>
 * 表代码：ZDInterexp<br>
 * 表主键：UserName<br>
 */
public class ZDInterexpSchema extends Schema {
	private String UserName;

	private Integer LoginCount;

	private Integer ReplyCountD;

	private Integer ReplyCountY;

	private Integer PublishCountD;

	private Integer PublishCountY;

	private Integer CommentCountD;

	private Integer CommentCountY;

	private Integer SurveyCountD;

	private Integer SurveyCountY;

	private Integer SubEmailCount;

	private Integer RegisterCount;

	private Integer InformationCount;

	private Integer MicroblogCount;

	private Date OperateDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("LoginCount", DataColumn.INTEGER, 1, 0 , 0 , false , false),
		new SchemaColumn("ReplyCountD", DataColumn.INTEGER, 2, 0 , 0 , false , false),
		new SchemaColumn("ReplyCountY", DataColumn.INTEGER, 3, 0 , 0 , false , false),
		new SchemaColumn("PublishCountD", DataColumn.INTEGER, 4, 0 , 0 , false , false),
		new SchemaColumn("PublishCountY", DataColumn.INTEGER, 5, 0 , 0 , false , false),
		new SchemaColumn("CommentCountD", DataColumn.INTEGER, 6, 0 , 0 , false , false),
		new SchemaColumn("CommentCountY", DataColumn.INTEGER, 7, 0 , 0 , false , false),
		new SchemaColumn("SurveyCountD", DataColumn.INTEGER, 8, 0 , 0 , false , false),
		new SchemaColumn("SurveyCountY", DataColumn.INTEGER, 9, 0 , 0 , false , false),
		new SchemaColumn("SubEmailCount", DataColumn.INTEGER, 10, 0 , 0 , false , false),
		new SchemaColumn("RegisterCount", DataColumn.INTEGER, 11, 0 , 0 , false , false),
		new SchemaColumn("InformationCount", DataColumn.INTEGER, 12, 0 , 0 , false , false),
		new SchemaColumn("MicroblogCount", DataColumn.INTEGER, 13, 0 , 0 , false , false),
		new SchemaColumn("OperateDate", DataColumn.DATETIME, 14, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDInterexp";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDInterexp values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDInterexp set UserName=?,LoginCount=?,ReplyCountD=?,ReplyCountY=?,PublishCountD=?,PublishCountY=?,CommentCountD=?,CommentCountY=?,SurveyCountD=?,SurveyCountY=?,SubEmailCount=?,RegisterCount=?,InformationCount=?,MicroblogCount=?,OperateDate=? where UserName=?";

	protected static final String _DeleteSQL = "delete from ZDInterexp  where UserName=?";

	protected static final String _FillAllSQL = "select * from ZDInterexp  where UserName=?";

	public ZDInterexpSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[15];
	}

	protected Schema newInstance(){
		return new ZDInterexpSchema();
	}

	protected SchemaSet newSet(){
		return new ZDInterexpSet();
	}

	public ZDInterexpSet query() {
		return query(null, -1, -1);
	}

	public ZDInterexpSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDInterexpSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDInterexpSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDInterexpSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){if(v==null){LoginCount = null;}else{LoginCount = new Integer(v.toString());}return;}
		if (i == 2){if(v==null){ReplyCountD = null;}else{ReplyCountD = new Integer(v.toString());}return;}
		if (i == 3){if(v==null){ReplyCountY = null;}else{ReplyCountY = new Integer(v.toString());}return;}
		if (i == 4){if(v==null){PublishCountD = null;}else{PublishCountD = new Integer(v.toString());}return;}
		if (i == 5){if(v==null){PublishCountY = null;}else{PublishCountY = new Integer(v.toString());}return;}
		if (i == 6){if(v==null){CommentCountD = null;}else{CommentCountD = new Integer(v.toString());}return;}
		if (i == 7){if(v==null){CommentCountY = null;}else{CommentCountY = new Integer(v.toString());}return;}
		if (i == 8){if(v==null){SurveyCountD = null;}else{SurveyCountD = new Integer(v.toString());}return;}
		if (i == 9){if(v==null){SurveyCountY = null;}else{SurveyCountY = new Integer(v.toString());}return;}
		if (i == 10){if(v==null){SubEmailCount = null;}else{SubEmailCount = new Integer(v.toString());}return;}
		if (i == 11){if(v==null){RegisterCount = null;}else{RegisterCount = new Integer(v.toString());}return;}
		if (i == 12){if(v==null){InformationCount = null;}else{InformationCount = new Integer(v.toString());}return;}
		if (i == 13){if(v==null){MicroblogCount = null;}else{MicroblogCount = new Integer(v.toString());}return;}
		if (i == 14){OperateDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return LoginCount;}
		if (i == 2){return ReplyCountD;}
		if (i == 3){return ReplyCountY;}
		if (i == 4){return PublishCountD;}
		if (i == 5){return PublishCountY;}
		if (i == 6){return CommentCountD;}
		if (i == 7){return CommentCountY;}
		if (i == 8){return SurveyCountD;}
		if (i == 9){return SurveyCountY;}
		if (i == 10){return SubEmailCount;}
		if (i == 11){return RegisterCount;}
		if (i == 12){return InformationCount;}
		if (i == 13){return MicroblogCount;}
		if (i == 14){return OperateDate;}
		return null;
	}

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :用户id<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :用户id<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段LoginCount的值，该字段的<br>
	* 字段名称 :当日登录次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getLoginCount() {
		if(LoginCount==null){return 0;}
		return LoginCount.intValue();
	}

	/**
	* 设置字段LoginCount的值，该字段的<br>
	* 字段名称 :当日登录次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLoginCount(int loginCount) {
		this.LoginCount = new Integer(loginCount);
    }

	/**
	* 设置字段LoginCount的值，该字段的<br>
	* 字段名称 :当日登录次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLoginCount(String loginCount) {
		if (loginCount == null){
			this.LoginCount = null;
			return;
		}
		this.LoginCount = new Integer(loginCount);
    }

	/**
	* 获取字段ReplyCountD的值，该字段的<br>
	* 字段名称 :当日回复次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getReplyCountD() {
		if(ReplyCountD==null){return 0;}
		return ReplyCountD.intValue();
	}

	/**
	* 设置字段ReplyCountD的值，该字段的<br>
	* 字段名称 :当日回复次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyCountD(int replyCountD) {
		this.ReplyCountD = new Integer(replyCountD);
    }

	/**
	* 设置字段ReplyCountD的值，该字段的<br>
	* 字段名称 :当日回复次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyCountD(String replyCountD) {
		if (replyCountD == null){
			this.ReplyCountD = null;
			return;
		}
		this.ReplyCountD = new Integer(replyCountD);
    }

	/**
	* 获取字段ReplyCountY的值，该字段的<br>
	* 字段名称 :本年度回复次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getReplyCountY() {
		if(ReplyCountY==null){return 0;}
		return ReplyCountY.intValue();
	}

	/**
	* 设置字段ReplyCountY的值，该字段的<br>
	* 字段名称 :本年度回复次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyCountY(int replyCountY) {
		this.ReplyCountY = new Integer(replyCountY);
    }

	/**
	* 设置字段ReplyCountY的值，该字段的<br>
	* 字段名称 :本年度回复次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyCountY(String replyCountY) {
		if (replyCountY == null){
			this.ReplyCountY = null;
			return;
		}
		this.ReplyCountY = new Integer(replyCountY);
    }

	/**
	* 获取字段PublishCountD的值，该字段的<br>
	* 字段名称 :当日发布内容次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getPublishCountD() {
		if(PublishCountD==null){return 0;}
		return PublishCountD.intValue();
	}

	/**
	* 设置字段PublishCountD的值，该字段的<br>
	* 字段名称 :当日发布内容次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPublishCountD(int publishCountD) {
		this.PublishCountD = new Integer(publishCountD);
    }

	/**
	* 设置字段PublishCountD的值，该字段的<br>
	* 字段名称 :当日发布内容次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPublishCountD(String publishCountD) {
		if (publishCountD == null){
			this.PublishCountD = null;
			return;
		}
		this.PublishCountD = new Integer(publishCountD);
    }

	/**
	* 获取字段PublishCountY的值，该字段的<br>
	* 字段名称 :本年度发布内容次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getPublishCountY() {
		if(PublishCountY==null){return 0;}
		return PublishCountY.intValue();
	}

	/**
	* 设置字段PublishCountY的值，该字段的<br>
	* 字段名称 :本年度发布内容次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPublishCountY(int publishCountY) {
		this.PublishCountY = new Integer(publishCountY);
    }

	/**
	* 设置字段PublishCountY的值，该字段的<br>
	* 字段名称 :本年度发布内容次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPublishCountY(String publishCountY) {
		if (publishCountY == null){
			this.PublishCountY = null;
			return;
		}
		this.PublishCountY = new Integer(publishCountY);
    }

	/**
	* 获取字段CommentCountD的值，该字段的<br>
	* 字段名称 :当日评论次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getCommentCountD() {
		if(CommentCountD==null){return 0;}
		return CommentCountD.intValue();
	}

	/**
	* 设置字段CommentCountD的值，该字段的<br>
	* 字段名称 :当日评论次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommentCountD(int commentCountD) {
		this.CommentCountD = new Integer(commentCountD);
    }

	/**
	* 设置字段CommentCountD的值，该字段的<br>
	* 字段名称 :当日评论次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommentCountD(String commentCountD) {
		if (commentCountD == null){
			this.CommentCountD = null;
			return;
		}
		this.CommentCountD = new Integer(commentCountD);
    }

	/**
	* 获取字段CommentCountY的值，该字段的<br>
	* 字段名称 :本年度评论次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getCommentCountY() {
		if(CommentCountY==null){return 0;}
		return CommentCountY.intValue();
	}

	/**
	* 设置字段CommentCountY的值，该字段的<br>
	* 字段名称 :本年度评论次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommentCountY(int commentCountY) {
		this.CommentCountY = new Integer(commentCountY);
    }

	/**
	* 设置字段CommentCountY的值，该字段的<br>
	* 字段名称 :本年度评论次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommentCountY(String commentCountY) {
		if (commentCountY == null){
			this.CommentCountY = null;
			return;
		}
		this.CommentCountY = new Integer(commentCountY);
    }

	/**
	* 获取字段SurveyCountD的值，该字段的<br>
	* 字段名称 :当日调查问卷次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getSurveyCountD() {
		if(SurveyCountD==null){return 0;}
		return SurveyCountD.intValue();
	}

	/**
	* 设置字段SurveyCountD的值，该字段的<br>
	* 字段名称 :当日调查问卷次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSurveyCountD(int surveyCountD) {
		this.SurveyCountD = new Integer(surveyCountD);
    }

	/**
	* 设置字段SurveyCountD的值，该字段的<br>
	* 字段名称 :当日调查问卷次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSurveyCountD(String surveyCountD) {
		if (surveyCountD == null){
			this.SurveyCountD = null;
			return;
		}
		this.SurveyCountD = new Integer(surveyCountD);
    }

	/**
	* 获取字段SurveyCountY的值，该字段的<br>
	* 字段名称 :本年度调查问卷次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getSurveyCountY() {
		if(SurveyCountY==null){return 0;}
		return SurveyCountY.intValue();
	}

	/**
	* 设置字段SurveyCountY的值，该字段的<br>
	* 字段名称 :本年度调查问卷次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSurveyCountY(int surveyCountY) {
		this.SurveyCountY = new Integer(surveyCountY);
    }

	/**
	* 设置字段SurveyCountY的值，该字段的<br>
	* 字段名称 :本年度调查问卷次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSurveyCountY(String surveyCountY) {
		if (surveyCountY == null){
			this.SurveyCountY = null;
			return;
		}
		this.SurveyCountY = new Integer(surveyCountY);
    }

	/**
	* 获取字段SubEmailCount的值，该字段的<br>
	* 字段名称 :订阅邮件次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getSubEmailCount() {
		if(SubEmailCount==null){return 0;}
		return SubEmailCount.intValue();
	}

	/**
	* 设置字段SubEmailCount的值，该字段的<br>
	* 字段名称 :订阅邮件次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSubEmailCount(int subEmailCount) {
		this.SubEmailCount = new Integer(subEmailCount);
    }

	/**
	* 设置字段SubEmailCount的值，该字段的<br>
	* 字段名称 :订阅邮件次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSubEmailCount(String subEmailCount) {
		if (subEmailCount == null){
			this.SubEmailCount = null;
			return;
		}
		this.SubEmailCount = new Integer(subEmailCount);
    }

	/**
	* 获取字段RegisterCount的值，该字段的<br>
	* 字段名称 :注册次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getRegisterCount() {
		if(RegisterCount==null){return 0;}
		return RegisterCount.intValue();
	}

	/**
	* 设置字段RegisterCount的值，该字段的<br>
	* 字段名称 :注册次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRegisterCount(int registerCount) {
		this.RegisterCount = new Integer(registerCount);
    }

	/**
	* 设置字段RegisterCount的值，该字段的<br>
	* 字段名称 :注册次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRegisterCount(String registerCount) {
		if (registerCount == null){
			this.RegisterCount = null;
			return;
		}
		this.RegisterCount = new Integer(registerCount);
    }

	/**
	* 获取字段InformationCount的值，该字段的<br>
	* 字段名称 :完善资料次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getInformationCount() {
		if(InformationCount==null){return 0;}
		return InformationCount.intValue();
	}

	/**
	* 设置字段InformationCount的值，该字段的<br>
	* 字段名称 :完善资料次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInformationCount(int informationCount) {
		this.InformationCount = new Integer(informationCount);
    }

	/**
	* 设置字段InformationCount的值，该字段的<br>
	* 字段名称 :完善资料次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInformationCount(String informationCount) {
		if (informationCount == null){
			this.InformationCount = null;
			return;
		}
		this.InformationCount = new Integer(informationCount);
    }

	/**
	* 获取字段MicroblogCount的值，该字段的<br>
	* 字段名称 :关注微博次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getMicroblogCount() {
		if(MicroblogCount==null){return 0;}
		return MicroblogCount.intValue();
	}

	/**
	* 设置字段MicroblogCount的值，该字段的<br>
	* 字段名称 :关注微博次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMicroblogCount(int microblogCount) {
		this.MicroblogCount = new Integer(microblogCount);
    }

	/**
	* 设置字段MicroblogCount的值，该字段的<br>
	* 字段名称 :关注微博次数<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMicroblogCount(String microblogCount) {
		if (microblogCount == null){
			this.MicroblogCount = null;
			return;
		}
		this.MicroblogCount = new Integer(microblogCount);
    }

	/**
	* 获取字段OperateDate的值，该字段的<br>
	* 字段名称 :操作时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getOperateDate() {
		return OperateDate;
	}

	/**
	* 设置字段OperateDate的值，该字段的<br>
	* 字段名称 :操作时间<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperateDate(Date operateDate) {
		this.OperateDate = operateDate;
    }

}