package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：论坛会员表<br>
 * 表代码：ZCForumMember<br>
 * 表主键：UserName<br>
 */
public class ZCForumMemberSchema extends Schema {
	private String UserName;

	private Long SiteID;

	private Long AdminID;

	private Long UserGroupID;

	private Long DefinedID;

	private String NickName;

	private Long ThemeCount;

	private Long ReplyCount;

	private String HeadImage;

	private String UseSelfImage;

	private String Status;

	private Long ForumScore;

	private String ForumSign;

	private Date LastLoginTime;

	private Date LastLogoutTime;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 0 , 0 , false , false),
		new SchemaColumn("AdminID", DataColumn.LONG, 2, 0 , 0 , false , false),
		new SchemaColumn("UserGroupID", DataColumn.LONG, 3, 0 , 0 , false , false),
		new SchemaColumn("DefinedID", DataColumn.LONG, 4, 0 , 0 , false , false),
		new SchemaColumn("NickName", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("ThemeCount", DataColumn.LONG, 6, 0 , 0 , false , false),
		new SchemaColumn("ReplyCount", DataColumn.LONG, 7, 0 , 0 , false , false),
		new SchemaColumn("HeadImage", DataColumn.STRING, 8, 500 , 0 , false , false),
		new SchemaColumn("UseSelfImage", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("ForumScore", DataColumn.LONG, 11, 0 , 0 , false , false),
		new SchemaColumn("ForumSign", DataColumn.STRING, 12, 1000 , 0 , false , false),
		new SchemaColumn("LastLoginTime", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("LastLogoutTime", DataColumn.DATETIME, 14, 0 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 17, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 18, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 19, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 20, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 21, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 22, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCForumMember";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZCForumMember values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCForumMember set UserName=?,SiteID=?,AdminID=?,UserGroupID=?,DefinedID=?,NickName=?,ThemeCount=?,ReplyCount=?,HeadImage=?,UseSelfImage=?,Status=?,ForumScore=?,ForumSign=?,LastLoginTime=?,LastLogoutTime=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where UserName=?";

	protected static final String _DeleteSQL = "delete from ZCForumMember  where UserName=?";

	protected static final String _FillAllSQL = "select * from ZCForumMember  where UserName=?";

	public ZCForumMemberSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[23];
	}

	protected Schema newInstance(){
		return new ZCForumMemberSchema();
	}

	protected SchemaSet newSet(){
		return new ZCForumMemberSet();
	}

	public ZCForumMemberSet query() {
		return query(null, -1, -1);
	}

	public ZCForumMemberSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCForumMemberSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCForumMemberSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCForumMemberSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){AdminID = null;}else{AdminID = new Long(v.toString());}return;}
		if (i == 3){if(v==null){UserGroupID = null;}else{UserGroupID = new Long(v.toString());}return;}
		if (i == 4){if(v==null){DefinedID = null;}else{DefinedID = new Long(v.toString());}return;}
		if (i == 5){NickName = (String)v;return;}
		if (i == 6){if(v==null){ThemeCount = null;}else{ThemeCount = new Long(v.toString());}return;}
		if (i == 7){if(v==null){ReplyCount = null;}else{ReplyCount = new Long(v.toString());}return;}
		if (i == 8){HeadImage = (String)v;return;}
		if (i == 9){UseSelfImage = (String)v;return;}
		if (i == 10){Status = (String)v;return;}
		if (i == 11){if(v==null){ForumScore = null;}else{ForumScore = new Long(v.toString());}return;}
		if (i == 12){ForumSign = (String)v;return;}
		if (i == 13){LastLoginTime = (Date)v;return;}
		if (i == 14){LastLogoutTime = (Date)v;return;}
		if (i == 15){prop1 = (String)v;return;}
		if (i == 16){prop2 = (String)v;return;}
		if (i == 17){prop3 = (String)v;return;}
		if (i == 18){prop4 = (String)v;return;}
		if (i == 19){AddUser = (String)v;return;}
		if (i == 20){AddTime = (Date)v;return;}
		if (i == 21){ModifyUser = (String)v;return;}
		if (i == 22){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return SiteID;}
		if (i == 2){return AdminID;}
		if (i == 3){return UserGroupID;}
		if (i == 4){return DefinedID;}
		if (i == 5){return NickName;}
		if (i == 6){return ThemeCount;}
		if (i == 7){return ReplyCount;}
		if (i == 8){return HeadImage;}
		if (i == 9){return UseSelfImage;}
		if (i == 10){return Status;}
		if (i == 11){return ForumScore;}
		if (i == 12){return ForumSign;}
		if (i == 13){return LastLoginTime;}
		if (i == 14){return LastLogoutTime;}
		if (i == 15){return prop1;}
		if (i == 16){return prop2;}
		if (i == 17){return prop3;}
		if (i == 18){return prop4;}
		if (i == 19){return AddUser;}
		if (i == 20){return AddTime;}
		if (i == 21){return ModifyUser;}
		if (i == 22){return ModifyTime;}
		return null;
	}

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :用户名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :用户名<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getSiteID() {
		if(SiteID==null){return 0;}
		return SiteID.longValue();
	}

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段AdminID的值，该字段的<br>
	* 字段名称 :系统组ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getAdminID() {
		if(AdminID==null){return 0;}
		return AdminID.longValue();
	}

	/**
	* 设置字段AdminID的值，该字段的<br>
	* 字段名称 :系统组ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdminID(long adminID) {
		this.AdminID = new Long(adminID);
    }

	/**
	* 设置字段AdminID的值，该字段的<br>
	* 字段名称 :系统组ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdminID(String adminID) {
		if (adminID == null){
			this.AdminID = null;
			return;
		}
		this.AdminID = new Long(adminID);
    }

	/**
	* 获取字段UserGroupID的值，该字段的<br>
	* 字段名称 :积分组ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getUserGroupID() {
		if(UserGroupID==null){return 0;}
		return UserGroupID.longValue();
	}

	/**
	* 设置字段UserGroupID的值，该字段的<br>
	* 字段名称 :积分组ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUserGroupID(long userGroupID) {
		this.UserGroupID = new Long(userGroupID);
    }

	/**
	* 设置字段UserGroupID的值，该字段的<br>
	* 字段名称 :积分组ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUserGroupID(String userGroupID) {
		if (userGroupID == null){
			this.UserGroupID = null;
			return;
		}
		this.UserGroupID = new Long(userGroupID);
    }

	/**
	* 获取字段DefinedID的值，该字段的<br>
	* 字段名称 :自定义组ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getDefinedID() {
		if(DefinedID==null){return 0;}
		return DefinedID.longValue();
	}

	/**
	* 设置字段DefinedID的值，该字段的<br>
	* 字段名称 :自定义组ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDefinedID(long definedID) {
		this.DefinedID = new Long(definedID);
    }

	/**
	* 设置字段DefinedID的值，该字段的<br>
	* 字段名称 :自定义组ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDefinedID(String definedID) {
		if (definedID == null){
			this.DefinedID = null;
			return;
		}
		this.DefinedID = new Long(definedID);
    }

	/**
	* 获取字段NickName的值，该字段的<br>
	* 字段名称 :昵称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getNickName() {
		return NickName;
	}

	/**
	* 设置字段NickName的值，该字段的<br>
	* 字段名称 :昵称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setNickName(String nickName) {
		this.NickName = nickName;
    }

	/**
	* 获取字段ThemeCount的值，该字段的<br>
	* 字段名称 :发帖数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getThemeCount() {
		if(ThemeCount==null){return 0;}
		return ThemeCount.longValue();
	}

	/**
	* 设置字段ThemeCount的值，该字段的<br>
	* 字段名称 :发帖数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setThemeCount(long themeCount) {
		this.ThemeCount = new Long(themeCount);
    }

	/**
	* 设置字段ThemeCount的值，该字段的<br>
	* 字段名称 :发帖数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setThemeCount(String themeCount) {
		if (themeCount == null){
			this.ThemeCount = null;
			return;
		}
		this.ThemeCount = new Long(themeCount);
    }

	/**
	* 获取字段ReplyCount的值，该字段的<br>
	* 字段名称 :回复数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getReplyCount() {
		if(ReplyCount==null){return 0;}
		return ReplyCount.longValue();
	}

	/**
	* 设置字段ReplyCount的值，该字段的<br>
	* 字段名称 :回复数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyCount(long replyCount) {
		this.ReplyCount = new Long(replyCount);
    }

	/**
	* 设置字段ReplyCount的值，该字段的<br>
	* 字段名称 :回复数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyCount(String replyCount) {
		if (replyCount == null){
			this.ReplyCount = null;
			return;
		}
		this.ReplyCount = new Long(replyCount);
    }

	/**
	* 获取字段HeadImage的值，该字段的<br>
	* 字段名称 :论坛头像<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHeadImage() {
		return HeadImage;
	}

	/**
	* 设置字段HeadImage的值，该字段的<br>
	* 字段名称 :论坛头像<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHeadImage(String headImage) {
		this.HeadImage = headImage;
    }

	/**
	* 获取字段UseSelfImage的值，该字段的<br>
	* 字段名称 :是否使用自定义头像<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUseSelfImage() {
		return UseSelfImage;
	}

	/**
	* 设置字段UseSelfImage的值，该字段的<br>
	* 字段名称 :是否使用自定义头像<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUseSelfImage(String useSelfImage) {
		this.UseSelfImage = useSelfImage;
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :用户状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :用户状态<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* 获取字段ForumScore的值，该字段的<br>
	* 字段名称 :论坛积分<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getForumScore() {
		if(ForumScore==null){return 0;}
		return ForumScore.longValue();
	}

	/**
	* 设置字段ForumScore的值，该字段的<br>
	* 字段名称 :论坛积分<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setForumScore(long forumScore) {
		this.ForumScore = new Long(forumScore);
    }

	/**
	* 设置字段ForumScore的值，该字段的<br>
	* 字段名称 :论坛积分<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setForumScore(String forumScore) {
		if (forumScore == null){
			this.ForumScore = null;
			return;
		}
		this.ForumScore = new Long(forumScore);
    }

	/**
	* 获取字段ForumSign的值，该字段的<br>
	* 字段名称 :论坛签名<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getForumSign() {
		return ForumSign;
	}

	/**
	* 设置字段ForumSign的值，该字段的<br>
	* 字段名称 :论坛签名<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setForumSign(String forumSign) {
		this.ForumSign = forumSign;
    }

	/**
	* 获取字段LastLoginTime的值，该字段的<br>
	* 字段名称 :最后登陆时间<br>
	* 数据类型 :dateTime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getLastLoginTime() {
		return LastLoginTime;
	}

	/**
	* 设置字段LastLoginTime的值，该字段的<br>
	* 字段名称 :最后登陆时间<br>
	* 数据类型 :dateTime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLastLoginTime(Date lastLoginTime) {
		this.LastLoginTime = lastLoginTime;
    }

	/**
	* 获取字段LastLogoutTime的值，该字段的<br>
	* 字段名称 :最后退出时间<br>
	* 数据类型 :dateTime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getLastLogoutTime() {
		return LastLogoutTime;
	}

	/**
	* 设置字段LastLogoutTime的值，该字段的<br>
	* 字段名称 :最后退出时间<br>
	* 数据类型 :dateTime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLastLogoutTime(Date lastLogoutTime) {
		this.LastLogoutTime = lastLogoutTime;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :预留字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :预留字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :预留字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :预留字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :预留字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :预留字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :预留字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :预留字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :添加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

}