package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：热点词汇表备份<br>
 * 表代码：BZCKeyword<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCKeywordSchema extends Schema {
	private Long ID;

	private String Keyword;

	private Long SiteId;

	private String KeywordType;

	private String LinkUrl;

	private String LinkTarget;

	private String LinkAlt;

	private Long HintCount;

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
	
	private Long SearchCode;
	
	private Long PriorityLevel;
	
	private String EmployFlag;
	
	private String BelongCategory;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("Keyword", DataColumn.STRING, 1, 200 , 0 , true , false),
		new SchemaColumn("SiteId", DataColumn.LONG, 2, 0 , 0 , true , false),
		new SchemaColumn("KeywordType", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("LinkUrl", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("LinkTarget", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("LinkAlt", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("HintCount", DataColumn.LONG, 7, 0 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 12, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 13, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 14, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("SearchCode", DataColumn.LONG, 16, 0 , 0 , false , false),
		new SchemaColumn("PriorityLevel", DataColumn.LONG, 17, 0 , 0 , false , false),
		new SchemaColumn("EmployFlag", DataColumn.STRING, 18, 5 , 0 , false , false),
		new SchemaColumn("BelongCategory", DataColumn.STRING, 19, 5 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 20, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 21, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 22, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 23, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCKeyword";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into BZCKeyword values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCKeyword set ID=?,Keyword=?,SiteId=?,KeywordType=?,LinkUrl=?,LinkTarget=?,LinkAlt=?,HintCount=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,SearchCode=?,PriorityLevel=?,EmployFlag=?,BelongCategory=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCKeyword  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCKeyword  where ID=? and BackupNo=?";

	public BZCKeywordSchema(){
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
		return new BZCKeywordSchema();
	}

	protected SchemaSet newSet(){
		return new BZCKeywordSet();
	}

	public BZCKeywordSet query() {
		return query(null, -1, -1);
	}

	public BZCKeywordSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCKeywordSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCKeywordSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCKeywordSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Keyword = (String)v;return;}
		if (i == 2){if(v==null){SiteId = null;}else{SiteId = new Long(v.toString());}return;}
		if (i == 3){KeywordType = (String)v;return;}
		if (i == 4){LinkUrl = (String)v;return;}
		if (i == 5){LinkTarget = (String)v;return;}
		if (i == 6){LinkAlt = (String)v;return;}
		if (i == 7){if(v==null){HintCount = null;}else{HintCount = new Long(v.toString());}return;}
		if (i == 8){Prop1 = (String)v;return;}
		if (i == 9){Prop2 = (String)v;return;}
		if (i == 10){Prop3 = (String)v;return;}
		if (i == 11){Prop4 = (String)v;return;}
		if (i == 12){AddUser = (String)v;return;}
		if (i == 13){AddTime = (Date)v;return;}
		if (i == 14){ModifyUser = (String)v;return;}
		if (i == 15){ModifyTime = (Date)v;return;}
		if (i == 16){if(v==null){SearchCode = null;}else{SearchCode = new Long(v.toString());}return;}
		if (i == 17){if(v==null){PriorityLevel = null;}else{PriorityLevel = new Long(v.toString());}return;}
		if (i == 18){EmployFlag = (String)v;return;}
		if (i == 19){BelongCategory = (String)v;return;}
		if (i == 20){BackupNo = (String)v;return;}
		if (i == 21){BackupOperator = (String)v;return;}
		if (i == 22){BackupTime = (Date)v;return;}
		if (i == 23){BackupMemo = (String)v;return;}
		
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Keyword;}
		if (i == 2){return SiteId;}
		if (i == 3){return KeywordType;}
		if (i == 4){return LinkUrl;}
		if (i == 5){return LinkTarget;}
		if (i == 6){return LinkAlt;}
		if (i == 7){return HintCount;}
		if (i == 8){return Prop1;}
		if (i == 9){return Prop2;}
		if (i == 10){return Prop3;}
		if (i == 11){return Prop4;}
		if (i == 12){return AddUser;}
		if (i == 13){return AddTime;}
		if (i == 14){return ModifyUser;}
		if (i == 15){return ModifyTime;}
		if (i == 16){return SearchCode;}
		if (i == 17){return PriorityLevel;}
		if (i == 18){return EmployFlag;}
		if (i == 19){return BelongCategory;}
		if (i == 20){return BackupNo;}
		if (i == 21){return BackupOperator;}
		if (i == 22){return BackupTime;}
		if (i == 23){return BackupMemo;}
		
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :关键字ID<br>
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
	* 字段名称 :关键字ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :关键字ID<br>
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
	* 获取字段Keyword的值，该字段的<br>
	* 字段名称 :关键字<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getKeyword() {
		return Keyword;
	}

	/**
	* 设置字段Keyword的值，该字段的<br>
	* 字段名称 :关键字<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setKeyword(String keyword) {
		this.Keyword = keyword;
    }

	/**
	* 获取字段SiteId的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getSiteId() {
		if(SiteId==null){return 0;}
		return SiteId.longValue();
	}

	/**
	* 设置字段SiteId的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteId(long siteId) {
		this.SiteId = new Long(siteId);
    }

	/**
	* 设置字段SiteId的值，该字段的<br>
	* 字段名称 :站点ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteId(String siteId) {
		if (siteId == null){
			this.SiteId = null;
			return;
		}
		this.SiteId = new Long(siteId);
    }

	/**
	* 获取字段KeywordType的值，该字段的<br>
	* 字段名称 :关键字类别<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getKeywordType() {
		return KeywordType;
	}

	/**
	* 设置字段KeywordType的值，该字段的<br>
	* 字段名称 :关键字类别<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setKeywordType(String keywordType) {
		this.KeywordType = keywordType;
    }

	/**
	* 获取字段LinkUrl的值，该字段的<br>
	* 字段名称 :链接地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLinkUrl() {
		return LinkUrl;
	}

	/**
	* 设置字段LinkUrl的值，该字段的<br>
	* 字段名称 :链接地址<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLinkUrl(String linkUrl) {
		this.LinkUrl = linkUrl;
    }

	/**
	* 获取字段LinkTarget的值，该字段的<br>
	* 字段名称 :链接target<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLinkTarget() {
		return LinkTarget;
	}

	/**
	* 设置字段LinkTarget的值，该字段的<br>
	* 字段名称 :链接target<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLinkTarget(String linkTarget) {
		this.LinkTarget = linkTarget;
    }

	/**
	* 获取字段LinkAlt的值，该字段的<br>
	* 字段名称 :链接Alt文字<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLinkAlt() {
		return LinkAlt;
	}

	/**
	* 设置字段LinkAlt的值，该字段的<br>
	* 字段名称 :链接Alt文字<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLinkAlt(String linkAlt) {
		this.LinkAlt = linkAlt;
    }

	/**
	* 获取字段HintCount的值，该字段的<br>
	* 字段名称 :点击次数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getHintCount() {
		if(HintCount==null){return 0;}
		return HintCount.longValue();
	}

	/**
	* 设置字段HintCount的值，该字段的<br>
	* 字段名称 :点击次数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHintCount(long hintCount) {
		this.HintCount = new Long(hintCount);
    }

	/**
	* 设置字段HintCount的值，该字段的<br>
	* 字段名称 :点击次数<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHintCount(String hintCount) {
		if (hintCount == null){
			this.HintCount = null;
			return;
		}
		this.HintCount = new Long(hintCount);
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
	
	/**
	* 获取字段SearchCode的值，该字段的<br>
	* 字段名称 :搜索值<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Long getSearchCode() {
		return SearchCode;
	}
	
	/**
	* 设置字段SearchCode的值，该字段的<br>
	* 字段名称 :搜索值<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearchCode(Long SearchCode) {
		this.SearchCode = SearchCode;
	}
	
	/**
	* 获取字段PriorityLevel的值，该字段的<br>
	* 字段名称 :搜索值<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Long getPriorityLevel() {
		return PriorityLevel;
	}
	
	/**
	* 设置字段PriorityLevel的值，该字段的<br>
	* 字段名称 :搜索值<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPriorityLevel(Long PriorityLevel) {
		this.PriorityLevel = PriorityLevel;
	}
	
	/**
	* 获取字段EmployFlag的值，该字段的<br>
	* 字段名称 :是否收录<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEmployFlag() {
		return EmployFlag;
	}
	
	/**
	* 设置字段EmployFlag的值，该字段的<br>
	* 字段名称 :是否收录<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEmployFlag(String EmployFlag) {
		this.EmployFlag = EmployFlag;
	}
	
	/**
	* 获取字段BelongCategory的值，该字段的<br>
	* 字段名称 :所属分类<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBelongCategory() {
		return BelongCategory;
	}
	
	/**
	* 设置字段BelongCategory的值，该字段的<br>
	* 字段名称 :所属分类<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBelongCategory(String BelongCategory) {
		this.BelongCategory = BelongCategory;
	}
	
}