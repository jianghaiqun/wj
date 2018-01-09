package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：评论表<br>
 * 表代码：ZCComment<br>
 * 表主键：ID<br>
 */
public class ZCCommentSchema extends Schema {
	private Long ID;

	private Long RelaID;

	private Long CatalogID;

	private Long CatalogType;

	private Long SiteID;

	private String Title;

	private String Content;

	private String AddUser;

	private String AddUserIP;

	private Date AddTime;

	private String VerifyFlag;

	private String VerifyUser;

	private Date VerifyTime;

	private String Prop1;

	private String Prop2;

	private Long SupporterCount;

	private Long AntiCount;

	private String SupportAntiIP;

	private String ReplyContent;

	private String Score;

	private String IsBuy;

	private String DescribeScore;

	private String PolicyScore;

	private String ClientScore;

	private String CoverageScore;

	private Integer sortNum;

	private Integer praisedNum;

	private String Purpose;

	private String memGrade;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("RelaID", DataColumn.LONG, 1, 0 , 0 , true , false),
		new SchemaColumn("CatalogID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("CatalogType", DataColumn.LONG, 3, 0 , 0 , true , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 4, 0 , 0 , true , false),
		new SchemaColumn("Title", DataColumn.STRING, 5, 100 , 0 , true , false),
		new SchemaColumn("Content", DataColumn.CLOB, 6, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 7, 200 , 0 , true , false),
		new SchemaColumn("AddUserIP", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 9, 0 , 0 , true , false),
		new SchemaColumn("VerifyFlag", DataColumn.STRING, 10, 1 , 0 , false , false),
		new SchemaColumn("VerifyUser", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("VerifyTime", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("SupporterCount", DataColumn.LONG, 15, 0 , 0 , false , false),
		new SchemaColumn("AntiCount", DataColumn.LONG, 16, 0 , 0 , false , false),
		new SchemaColumn("SupportAntiIP", DataColumn.CLOB, 17, 0 , 0 , false , false),
		new SchemaColumn("ReplyContent", DataColumn.STRING, 18, 1000 , 0 , false , false),
		new SchemaColumn("Score", DataColumn.STRING, 19, 5 , 0 , false , false),
		new SchemaColumn("IsBuy", DataColumn.STRING, 20, 2 , 0 , false , false),
		new SchemaColumn("DescribeScore", DataColumn.STRING, 21, 2 , 0 , false , false),
		new SchemaColumn("PolicyScore", DataColumn.STRING, 22, 2 , 0 , false , false),
		new SchemaColumn("ClientScore", DataColumn.STRING, 23, 2 , 0 , false , false),
		new SchemaColumn("CoverageScore", DataColumn.STRING, 24, 2 , 0 , false , false),
		new SchemaColumn("sortNum", DataColumn.INTEGER, 25, 11 , 0 , false , false),
		new SchemaColumn("praisedNum", DataColumn.INTEGER, 26, 11 , 0 , false , false),
		new SchemaColumn("Purpose", DataColumn.STRING, 27, 255 , 0 , false , false),
		new SchemaColumn("memGrade", DataColumn.STRING, 28, 255 , 0 , false , false)
	};

	public static final String _TableCode = "ZCComment";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZCComment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCComment set ID=?,RelaID=?,CatalogID=?,CatalogType=?,SiteID=?,Title=?,Content=?,AddUser=?,AddUserIP=?,AddTime=?,VerifyFlag=?,VerifyUser=?,VerifyTime=?,Prop1=?,Prop2=?,SupporterCount=?,AntiCount=?,SupportAntiIP=?,ReplyContent=?,Score=?,IsBuy=?,DescribeScore=?,PolicyScore=?,ClientScore=?,CoverageScore=?,sortNum=?,praisedNum=?,Purpose=?,memGrade=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCComment  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCComment  where ID=?";

	public ZCCommentSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[29];
	}

	protected Schema newInstance(){
		return new ZCCommentSchema();
	}

	protected SchemaSet newSet(){
		return new ZCCommentSet();
	}

	public ZCCommentSet query() {
		return query(null, -1, -1);
	}

	public ZCCommentSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCCommentSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCCommentSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCCommentSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){RelaID = null;}else{RelaID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 3){if(v==null){CatalogType = null;}else{CatalogType = new Long(v.toString());}return;}
		if (i == 4){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 5){Title = (String)v;return;}
		if (i == 6){Content = (String)v;return;}
		if (i == 7){AddUser = (String)v;return;}
		if (i == 8){AddUserIP = (String)v;return;}
		if (i == 9){AddTime = (Date)v;return;}
		if (i == 10){VerifyFlag = (String)v;return;}
		if (i == 11){VerifyUser = (String)v;return;}
		if (i == 12){VerifyTime = (Date)v;return;}
		if (i == 13){Prop1 = (String)v;return;}
		if (i == 14){Prop2 = (String)v;return;}
		if (i == 15){if(v==null){SupporterCount = null;}else{SupporterCount = new Long(v.toString());}return;}
		if (i == 16){if(v==null){AntiCount = null;}else{AntiCount = new Long(v.toString());}return;}
		if (i == 17){SupportAntiIP = (String)v;return;}
		if (i == 18){ReplyContent = (String)v;return;}
		if (i == 19){Score = (String)v;return;}
		if (i == 20){IsBuy = (String)v;return;}
		if (i == 21){DescribeScore = (String)v;return;}
		if (i == 22){PolicyScore = (String)v;return;}
		if (i == 23){ClientScore = (String)v;return;}
		if (i == 24){CoverageScore = (String)v;return;}
		if (i == 25){if(v==null){sortNum = null;}else{sortNum = new Integer(v.toString());}return;}
		if (i == 26){if(v==null){praisedNum = null;}else{praisedNum = new Integer(v.toString());}return;}
		if (i == 27){Purpose = (String)v;return;}
		if (i == 28){memGrade = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return RelaID;}
		if (i == 2){return CatalogID;}
		if (i == 3){return CatalogType;}
		if (i == 4){return SiteID;}
		if (i == 5){return Title;}
		if (i == 6){return Content;}
		if (i == 7){return AddUser;}
		if (i == 8){return AddUserIP;}
		if (i == 9){return AddTime;}
		if (i == 10){return VerifyFlag;}
		if (i == 11){return VerifyUser;}
		if (i == 12){return VerifyTime;}
		if (i == 13){return Prop1;}
		if (i == 14){return Prop2;}
		if (i == 15){return SupporterCount;}
		if (i == 16){return AntiCount;}
		if (i == 17){return SupportAntiIP;}
		if (i == 18){return ReplyContent;}
		if (i == 19){return Score;}
		if (i == 20){return IsBuy;}
		if (i == 21){return DescribeScore;}
		if (i == 22){return PolicyScore;}
		if (i == 23){return ClientScore;}
		if (i == 24){return CoverageScore;}
		if (i == 25){return sortNum;}
		if (i == 26){return praisedNum;}
		if (i == 27){return Purpose;}
		if (i == 28){return memGrade;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :评论ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :评论ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :评论ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段RelaID的值，该字段的<br>
	* 字段名称 :评论关联ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getRelaID() {
		if(RelaID==null){return 0;}
		return RelaID.longValue();
	}

	/**
	* 设置字段RelaID的值，该字段的<br>
	* 字段名称 :评论关联ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRelaID(long relaID) {
		this.RelaID = new Long(relaID);
    }

	/**
	* 设置字段RelaID的值，该字段的<br>
	* 字段名称 :评论关联ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRelaID(String relaID) {
		if (relaID == null){
			this.RelaID = null;
			return;
		}
		this.RelaID = new Long(relaID);
    }

	/**
	* 获取字段CatalogID的值，该字段的<br>
	* 字段名称 :栏目ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getCatalogID() {
		if(CatalogID==null){return 0;}
		return CatalogID.longValue();
	}

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :栏目ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogID(long catalogID) {
		this.CatalogID = new Long(catalogID);
    }

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :栏目ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogID(String catalogID) {
		if (catalogID == null){
			this.CatalogID = null;
			return;
		}
		this.CatalogID = new Long(catalogID);
    }

	/**
	* 获取字段CatalogType的值，该字段的<br>
	* 字段名称 :栏目类型<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getCatalogType() {
		if(CatalogType==null){return 0;}
		return CatalogType.longValue();
	}

	/**
	* 设置字段CatalogType的值，该字段的<br>
	* 字段名称 :栏目类型<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogType(long catalogType) {
		this.CatalogType = new Long(catalogType);
    }

	/**
	* 设置字段CatalogType的值，该字段的<br>
	* 字段名称 :栏目类型<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogType(String catalogType) {
		if (catalogType == null){
			this.CatalogType = null;
			return;
		}
		this.CatalogType = new Long(catalogType);
    }

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
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
	* 是否必填 :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段Title的值，该字段的<br>
	* 字段名称 :评论标题<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* 设置字段Title的值，该字段的<br>
	* 字段名称 :评论标题<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* 获取字段Content的值，该字段的<br>
	* 字段名称 :评论内容<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* 设置字段Content的值，该字段的<br>
	* 字段名称 :评论内容<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :添加者<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段AddUserIP的值，该字段的<br>
	* 字段名称 :评论者IP<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUserIP() {
		return AddUserIP;
	}

	/**
	* 设置字段AddUserIP的值，该字段的<br>
	* 字段名称 :评论者IP<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUserIP(String addUserIP) {
		this.AddUserIP = addUserIP;
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
	* 获取字段VerifyFlag的值，该字段的<br>
	* 字段名称 :审核标志<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getVerifyFlag() {
		return VerifyFlag;
	}

	/**
	* 设置字段VerifyFlag的值，该字段的<br>
	* 字段名称 :审核标志<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVerifyFlag(String verifyFlag) {
		this.VerifyFlag = verifyFlag;
    }

	/**
	* 获取字段VerifyUser的值，该字段的<br>
	* 字段名称 :审核人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getVerifyUser() {
		return VerifyUser;
	}

	/**
	* 设置字段VerifyUser的值，该字段的<br>
	* 字段名称 :审核人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVerifyUser(String verifyUser) {
		this.VerifyUser = verifyUser;
    }

	/**
	* 获取字段VerifyTime的值，该字段的<br>
	* 字段名称 :审核时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getVerifyTime() {
		return VerifyTime;
	}

	/**
	* 设置字段VerifyTime的值，该字段的<br>
	* 字段名称 :审核时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVerifyTime(Date verifyTime) {
		this.VerifyTime = verifyTime;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段SupporterCount的值，该字段的<br>
	* 字段名称 :支持人数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getSupporterCount() {
		if(SupporterCount==null){return 0;}
		return SupporterCount.longValue();
	}

	/**
	* 设置字段SupporterCount的值，该字段的<br>
	* 字段名称 :支持人数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSupporterCount(long supporterCount) {
		this.SupporterCount = new Long(supporterCount);
    }

	/**
	* 设置字段SupporterCount的值，该字段的<br>
	* 字段名称 :支持人数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSupporterCount(String supporterCount) {
		if (supporterCount == null){
			this.SupporterCount = null;
			return;
		}
		this.SupporterCount = new Long(supporterCount);
    }

	/**
	* 获取字段AntiCount的值，该字段的<br>
	* 字段名称 :反对人数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getAntiCount() {
		if(AntiCount==null){return 0;}
		return AntiCount.longValue();
	}

	/**
	* 设置字段AntiCount的值，该字段的<br>
	* 字段名称 :反对人数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAntiCount(long antiCount) {
		this.AntiCount = new Long(antiCount);
    }

	/**
	* 设置字段AntiCount的值，该字段的<br>
	* 字段名称 :反对人数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAntiCount(String antiCount) {
		if (antiCount == null){
			this.AntiCount = null;
			return;
		}
		this.AntiCount = new Long(antiCount);
    }

	/**
	* 获取字段SupportAntiIP的值，该字段的<br>
	* 字段名称 :支持反对者IP<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSupportAntiIP() {
		return SupportAntiIP;
	}

	/**
	* 设置字段SupportAntiIP的值，该字段的<br>
	* 字段名称 :支持反对者IP<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSupportAntiIP(String supportAntiIP) {
		this.SupportAntiIP = supportAntiIP;
    }

	/**
	* 获取字段ReplyContent的值，该字段的<br>
	* 字段名称 :回复内容<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReplyContent() {
		return ReplyContent;
	}

	/**
	* 设置字段ReplyContent的值，该字段的<br>
	* 字段名称 :回复内容<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyContent(String replyContent) {
		this.ReplyContent = replyContent;
    }

	/**
	* 获取字段Score的值，该字段的<br>
	* 字段名称 :总评分<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getScore() {
		return Score;
	}

	/**
	* 设置字段Score的值，该字段的<br>
	* 字段名称 :总评分<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setScore(String score) {
		this.Score = score;
    }

	/**
	* 获取字段IsBuy的值，该字段的<br>
	* 字段名称 :是否购买<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsBuy() {
		return IsBuy;
	}

	/**
	* 设置字段IsBuy的值，该字段的<br>
	* 字段名称 :是否购买<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsBuy(String isBuy) {
		this.IsBuy = isBuy;
    }

	/**
	* 获取字段DescribeScore的值，该字段的<br>
	* 字段名称 :保障程度<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDescribeScore() {
		return DescribeScore;
	}

	/**
	* 设置字段DescribeScore的值，该字段的<br>
	* 字段名称 :保障程度<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDescribeScore(String describeScore) {
		this.DescribeScore = describeScore;
    }

	/**
	* 获取字段PolicyScore的值，该字段的<br>
	* 字段名称 :保障价格<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPolicyScore() {
		return PolicyScore;
	}

	/**
	* 设置字段PolicyScore的值，该字段的<br>
	* 字段名称 :保障价格<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPolicyScore(String policyScore) {
		this.PolicyScore = policyScore;
    }

	/**
	* 获取字段ClientScore的值，该字段的<br>
	* 字段名称 :售后服务<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getClientScore() {
		return ClientScore;
	}

	/**
	* 设置字段ClientScore的值，该字段的<br>
	* 字段名称 :售后服务<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setClientScore(String clientScore) {
		this.ClientScore = clientScore;
    }

	/**
	* 获取字段CoverageScore的值，该字段的<br>
	* 字段名称 :保障范围<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCoverageScore() {
		return CoverageScore;
	}

	/**
	* 设置字段CoverageScore的值，该字段的<br>
	* 字段名称 :保障范围<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCoverageScore(String coverageScore) {
		this.CoverageScore = coverageScore;
    }

	/**
	* 获取字段sortNum的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getsortNum() {
		if(sortNum==null){return 0;}
		return sortNum.intValue();
	}

	/**
	* 设置字段sortNum的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsortNum(int sortNum) {
		this.sortNum = new Integer(sortNum);
    }

	/**
	* 设置字段sortNum的值，该字段的<br>
	* 字段名称 :排序<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsortNum(String sortNum) {
		if (sortNum == null){
			this.sortNum = null;
			return;
		}
		this.sortNum = new Integer(sortNum);
    }

	/**
	* 获取字段praisedNum的值，该字段的<br>
	* 字段名称 :点赞数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getpraisedNum() {
		if(praisedNum==null){return 0;}
		return praisedNum.intValue();
	}

	/**
	* 设置字段praisedNum的值，该字段的<br>
	* 字段名称 :点赞数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpraisedNum(int praisedNum) {
		this.praisedNum = new Integer(praisedNum);
    }

	/**
	* 设置字段praisedNum的值，该字段的<br>
	* 字段名称 :点赞数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpraisedNum(String praisedNum) {
		if (praisedNum == null){
			this.praisedNum = null;
			return;
		}
		this.praisedNum = new Integer(praisedNum);
    }

	/**
	* 获取字段Purpose的值，该字段的<br>
	* 字段名称 :投保目的<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPurpose() {
		return Purpose;
	}

	/**
	* 设置字段Purpose的值，该字段的<br>
	* 字段名称 :投保目的<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPurpose(String purpose) {
		this.Purpose = purpose;
    }

	/**
	* 获取字段memGrade的值，该字段的<br>
	* 字段名称 :会员等级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmemGrade() {
		return memGrade;
	}

	/**
	* 设置字段memGrade的值，该字段的<br>
	* 字段名称 :会员等级<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmemGrade(String memGrade) {
		this.memGrade = memGrade;
    }

}