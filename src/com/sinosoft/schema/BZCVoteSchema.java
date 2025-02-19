package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：投票表备份<br>
 * 表代码：BZCVote<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCVoteSchema extends Schema {
	private Long ID;

	private String Code;

	private Long SiteID;

	private String Title;

	private Long Total;

	private Date StartTime;

	private Date EndTime;

	private String IPLimit;

	private String VerifyFlag;

	private Integer Width;

	private Long RelaCatalogID;

	private Long VoteCatalogID;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("Code", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("Title", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("Total", DataColumn.LONG, 4, 0 , 0 , true , false),
		new SchemaColumn("StartTime", DataColumn.DATETIME, 5, 0 , 0 , true , false),
		new SchemaColumn("EndTime", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("IPLimit", DataColumn.STRING, 7, 1 , 0 , true , false),
		new SchemaColumn("VerifyFlag", DataColumn.STRING, 8, 1 , 0 , true , false),
		new SchemaColumn("Width", DataColumn.INTEGER, 9, 0 , 0 , false , false),
		new SchemaColumn("RelaCatalogID", DataColumn.LONG, 10, 0 , 0 , false , false),
		new SchemaColumn("VoteCatalogID", DataColumn.LONG, 11, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 16, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 17, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 18, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 19, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 20, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 21, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 22, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 23, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCVote";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into BZCVote values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCVote set ID=?,Code=?,SiteID=?,Title=?,Total=?,StartTime=?,EndTime=?,IPLimit=?,VerifyFlag=?,Width=?,RelaCatalogID=?,VoteCatalogID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCVote  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCVote  where ID=? and BackupNo=?";

	public BZCVoteSchema(){
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
		return new BZCVoteSchema();
	}

	protected SchemaSet newSet(){
		return new BZCVoteSet();
	}

	public BZCVoteSet query() {
		return query(null, -1, -1);
	}

	public BZCVoteSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCVoteSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCVoteSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCVoteSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Code = (String)v;return;}
		if (i == 2){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 3){Title = (String)v;return;}
		if (i == 4){if(v==null){Total = null;}else{Total = new Long(v.toString());}return;}
		if (i == 5){StartTime = (Date)v;return;}
		if (i == 6){EndTime = (Date)v;return;}
		if (i == 7){IPLimit = (String)v;return;}
		if (i == 8){VerifyFlag = (String)v;return;}
		if (i == 9){if(v==null){Width = null;}else{Width = new Integer(v.toString());}return;}
		if (i == 10){if(v==null){RelaCatalogID = null;}else{RelaCatalogID = new Long(v.toString());}return;}
		if (i == 11){if(v==null){VoteCatalogID = null;}else{VoteCatalogID = new Long(v.toString());}return;}
		if (i == 12){Prop1 = (String)v;return;}
		if (i == 13){Prop2 = (String)v;return;}
		if (i == 14){Prop3 = (String)v;return;}
		if (i == 15){Prop4 = (String)v;return;}
		if (i == 16){AddUser = (String)v;return;}
		if (i == 17){AddTime = (Date)v;return;}
		if (i == 18){ModifyUser = (String)v;return;}
		if (i == 19){ModifyTime = (Date)v;return;}
		if (i == 20){BackupNo = (String)v;return;}
		if (i == 21){BackupOperator = (String)v;return;}
		if (i == 22){BackupTime = (Date)v;return;}
		if (i == 23){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Code;}
		if (i == 2){return SiteID;}
		if (i == 3){return Title;}
		if (i == 4){return Total;}
		if (i == 5){return StartTime;}
		if (i == 6){return EndTime;}
		if (i == 7){return IPLimit;}
		if (i == 8){return VerifyFlag;}
		if (i == 9){return Width;}
		if (i == 10){return RelaCatalogID;}
		if (i == 11){return VoteCatalogID;}
		if (i == 12){return Prop1;}
		if (i == 13){return Prop2;}
		if (i == 14){return Prop3;}
		if (i == 15){return Prop4;}
		if (i == 16){return AddUser;}
		if (i == 17){return AddTime;}
		if (i == 18){return ModifyUser;}
		if (i == 19){return ModifyTime;}
		if (i == 20){return BackupNo;}
		if (i == 21){return BackupOperator;}
		if (i == 22){return BackupTime;}
		if (i == 23){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :投票ID<br>
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
	* 字段名称 :投票ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :投票ID<br>
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
	* 获取字段Code的值，该字段的<br>
	* 字段名称 :投票代码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* 设置字段Code的值，该字段的<br>
	* 字段名称 :投票代码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCode(String code) {
		this.Code = code;
    }

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :所属站点ID<br>
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
	* 字段名称 :所属站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :所属站点ID<br>
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
	* 字段名称 :投票标题<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* 设置字段Title的值，该字段的<br>
	* 字段名称 :投票标题<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* 获取字段Total的值，该字段的<br>
	* 字段名称 :投票总次数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getTotal() {
		if(Total==null){return 0;}
		return Total.longValue();
	}

	/**
	* 设置字段Total的值，该字段的<br>
	* 字段名称 :投票总次数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTotal(long total) {
		this.Total = new Long(total);
    }

	/**
	* 设置字段Total的值，该字段的<br>
	* 字段名称 :投票总次数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTotal(String total) {
		if (total == null){
			this.Total = null;
			return;
		}
		this.Total = new Long(total);
    }

	/**
	* 获取字段StartTime的值，该字段的<br>
	* 字段名称 :投票开始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getStartTime() {
		return StartTime;
	}

	/**
	* 设置字段StartTime的值，该字段的<br>
	* 字段名称 :投票开始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setStartTime(Date startTime) {
		this.StartTime = startTime;
    }

	/**
	* 获取字段EndTime的值，该字段的<br>
	* 字段名称 :投票结束时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getEndTime() {
		return EndTime;
	}

	/**
	* 设置字段EndTime的值，该字段的<br>
	* 字段名称 :投票结束时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEndTime(Date endTime) {
		this.EndTime = endTime;
    }

	/**
	* 获取字段IPLimit的值，该字段的<br>
	* 字段名称 :是否限制IP<br>
	* 数据类型 :char(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIPLimit() {
		return IPLimit;
	}

	/**
	* 设置字段IPLimit的值，该字段的<br>
	* 字段名称 :是否限制IP<br>
	* 数据类型 :char(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIPLimit(String iPLimit) {
		this.IPLimit = iPLimit;
    }

	/**
	* 获取字段VerifyFlag的值，该字段的<br>
	* 字段名称 :验证码标志<br>
	* 数据类型 :char(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getVerifyFlag() {
		return VerifyFlag;
	}

	/**
	* 设置字段VerifyFlag的值，该字段的<br>
	* 字段名称 :验证码标志<br>
	* 数据类型 :char(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVerifyFlag(String verifyFlag) {
		this.VerifyFlag = verifyFlag;
    }

	/**
	* 获取字段Width的值，该字段的<br>
	* 字段名称 :显示宽度<br>
	* 数据类型 :smallint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getWidth() {
		if(Width==null){return 0;}
		return Width.intValue();
	}

	/**
	* 设置字段Width的值，该字段的<br>
	* 字段名称 :显示宽度<br>
	* 数据类型 :smallint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWidth(int width) {
		this.Width = new Integer(width);
    }

	/**
	* 设置字段Width的值，该字段的<br>
	* 字段名称 :显示宽度<br>
	* 数据类型 :smallint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWidth(String width) {
		if (width == null){
			this.Width = null;
			return;
		}
		this.Width = new Integer(width);
    }

	/**
	* 获取字段RelaCatalogID的值，该字段的<br>
	* 字段名称 :关联栏目<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getRelaCatalogID() {
		if(RelaCatalogID==null){return 0;}
		return RelaCatalogID.longValue();
	}

	/**
	* 设置字段RelaCatalogID的值，该字段的<br>
	* 字段名称 :关联栏目<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRelaCatalogID(long relaCatalogID) {
		this.RelaCatalogID = new Long(relaCatalogID);
    }

	/**
	* 设置字段RelaCatalogID的值，该字段的<br>
	* 字段名称 :关联栏目<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRelaCatalogID(String relaCatalogID) {
		if (relaCatalogID == null){
			this.RelaCatalogID = null;
			return;
		}
		this.RelaCatalogID = new Long(relaCatalogID);
    }

	/**
	* 获取字段VoteCatalogID的值，该字段的<br>
	* 字段名称 :投票栏目<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getVoteCatalogID() {
		if(VoteCatalogID==null){return 0;}
		return VoteCatalogID.longValue();
	}

	/**
	* 设置字段VoteCatalogID的值，该字段的<br>
	* 字段名称 :投票栏目<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVoteCatalogID(long voteCatalogID) {
		this.VoteCatalogID = new Long(voteCatalogID);
    }

	/**
	* 设置字段VoteCatalogID的值，该字段的<br>
	* 字段名称 :投票栏目<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVoteCatalogID(String voteCatalogID) {
		if (voteCatalogID == null){
			this.VoteCatalogID = null;
			return;
		}
		this.VoteCatalogID = new Long(voteCatalogID);
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
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
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
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :最后修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :最后修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* 获取字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getBackupNo() {
		return BackupNo;
	}

	/**
	* 设置字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBackupNo(String backupNo) {
		this.BackupNo = backupNo;
    }

	/**
	* 获取字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBackupOperator() {
		return BackupOperator;
	}

	/**
	* 设置字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupOperator(String backupOperator) {
		this.BackupOperator = backupOperator;
    }

	/**
	* 获取字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getBackupTime() {
		return BackupTime;
	}

	/**
	* 设置字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupTime(Date backupTime) {
		this.BackupTime = backupTime;
    }

	/**
	* 获取字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackupMemo() {
		return BackupMemo;
	}

	/**
	* 设置字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackupMemo(String backupMemo) {
		this.BackupMemo = backupMemo;
    }

}