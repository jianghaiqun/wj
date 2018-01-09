package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：UV访问量<br>
 * 表代码：UVAccessStatistics<br>
 * 表主键：ID<br>
 */
public class UVAccessStatisticsSchema extends Schema {
	private String ID;

	private String IP;

	private String AccessDate;

	private String AccessTime;

	private String LeaveDate;

	private String LeaveTime;

	private String MemberID;

	private String Channel;

	private String TerminalType;

	private String OSType;

	private String ExplorerType;

	private String ScreenResolution;

	private String ISSupportCookie;

	private String ISSupportFlash;

	private String Language;

	private String SessionID;

	private String Area;

	private String Entry;

	private String Referer;

	private String IsJumpOrClose;

	private String Remark;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("IP", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("AccessDate", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("AccessTime", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("LeaveDate", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("LeaveTime", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("MemberID", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Channel", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("TerminalType", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("OSType", DataColumn.STRING, 9, 30 , 0 , false , false),
		new SchemaColumn("ExplorerType", DataColumn.STRING, 10, 30 , 0 , false , false),
		new SchemaColumn("ScreenResolution", DataColumn.STRING, 11, 30 , 0 , false , false),
		new SchemaColumn("ISSupportCookie", DataColumn.STRING, 12, 10 , 0 , false , false),
		new SchemaColumn("ISSupportFlash", DataColumn.STRING, 13, 10 , 0 , false , false),
		new SchemaColumn("Language", DataColumn.STRING, 14, 20 , 0 , false , false),
		new SchemaColumn("SessionID", DataColumn.STRING, 15, 100 , 0 , false , false),
		new SchemaColumn("Area", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("Entry", DataColumn.STRING, 17, 100 , 0 , false , false),
		new SchemaColumn("Referer", DataColumn.STRING, 18, 300 , 0 , false , false),
		new SchemaColumn("IsJumpOrClose", DataColumn.STRING, 19, 10 , 0 , false , false),
		new SchemaColumn("Remark", DataColumn.STRING, 20, 50 , 0 , false , false)
	};

	public static final String _TableCode = "UVAccessStatistics";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into UVAccessStatistics values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update UVAccessStatistics set ID=?,IP=?,AccessDate=?,AccessTime=?,LeaveDate=?,LeaveTime=?,MemberID=?,Channel=?,TerminalType=?,OSType=?,ExplorerType=?,ScreenResolution=?,ISSupportCookie=?,ISSupportFlash=?,Language=?,SessionID=?,Area=?,Entry=?,Referer=?,IsJumpOrClose=?,Remark=? where ID=?";

	protected static final String _DeleteSQL = "delete from UVAccessStatistics  where ID=?";

	protected static final String _FillAllSQL = "select * from UVAccessStatistics  where ID=?";

	public UVAccessStatisticsSchema(){
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
		return new UVAccessStatisticsSchema();
	}

	protected SchemaSet newSet(){
		return new UVAccessStatisticsSet();
	}

	public UVAccessStatisticsSet query() {
		return query(null, -1, -1);
	}

	public UVAccessStatisticsSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public UVAccessStatisticsSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public UVAccessStatisticsSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (UVAccessStatisticsSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){IP = (String)v;return;}
		if (i == 2){AccessDate = (String)v;return;}
		if (i == 3){AccessTime = (String)v;return;}
		if (i == 4){LeaveDate = (String)v;return;}
		if (i == 5){LeaveTime = (String)v;return;}
		if (i == 6){MemberID = (String)v;return;}
		if (i == 7){Channel = (String)v;return;}
		if (i == 8){TerminalType = (String)v;return;}
		if (i == 9){OSType = (String)v;return;}
		if (i == 10){ExplorerType = (String)v;return;}
		if (i == 11){ScreenResolution = (String)v;return;}
		if (i == 12){ISSupportCookie = (String)v;return;}
		if (i == 13){ISSupportFlash = (String)v;return;}
		if (i == 14){Language = (String)v;return;}
		if (i == 15){SessionID = (String)v;return;}
		if (i == 16){Area = (String)v;return;}
		if (i == 17){Entry = (String)v;return;}
		if (i == 18){Referer = (String)v;return;}
		if (i == 19){IsJumpOrClose = (String)v;return;}
		if (i == 20){Remark = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return IP;}
		if (i == 2){return AccessDate;}
		if (i == 3){return AccessTime;}
		if (i == 4){return LeaveDate;}
		if (i == 5){return LeaveTime;}
		if (i == 6){return MemberID;}
		if (i == 7){return Channel;}
		if (i == 8){return TerminalType;}
		if (i == 9){return OSType;}
		if (i == 10){return ExplorerType;}
		if (i == 11){return ScreenResolution;}
		if (i == 12){return ISSupportCookie;}
		if (i == 13){return ISSupportFlash;}
		if (i == 14){return Language;}
		if (i == 15){return SessionID;}
		if (i == 16){return Area;}
		if (i == 17){return Entry;}
		if (i == 18){return Referer;}
		if (i == 19){return IsJumpOrClose;}
		if (i == 20){return Remark;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :记录ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :记录ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段IP的值，该字段的<br>
	* 字段名称 :IP地址<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* 设置字段IP的值，该字段的<br>
	* 字段名称 :IP地址<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
    }

	/**
	* 获取字段AccessDate的值，该字段的<br>
	* 字段名称 :访问日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAccessDate() {
		return AccessDate;
	}

	/**
	* 设置字段AccessDate的值，该字段的<br>
	* 字段名称 :访问日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAccessDate(String accessDate) {
		this.AccessDate = accessDate;
    }

	/**
	* 获取字段AccessTime的值，该字段的<br>
	* 字段名称 :访问时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAccessTime() {
		return AccessTime;
	}

	/**
	* 设置字段AccessTime的值，该字段的<br>
	* 字段名称 :访问时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAccessTime(String accessTime) {
		this.AccessTime = accessTime;
    }

	/**
	* 获取字段LeaveDate的值，该字段的<br>
	* 字段名称 :离开日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLeaveDate() {
		return LeaveDate;
	}

	/**
	* 设置字段LeaveDate的值，该字段的<br>
	* 字段名称 :离开日期<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLeaveDate(String leaveDate) {
		this.LeaveDate = leaveDate;
    }

	/**
	* 获取字段LeaveTime的值，该字段的<br>
	* 字段名称 :离开时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLeaveTime() {
		return LeaveTime;
	}

	/**
	* 设置字段LeaveTime的值，该字段的<br>
	* 字段名称 :离开时间<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLeaveTime(String leaveTime) {
		this.LeaveTime = leaveTime;
    }

	/**
	* 获取字段MemberID的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemberID() {
		return MemberID;
	}

	/**
	* 设置字段MemberID的值，该字段的<br>
	* 字段名称 :会员ID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemberID(String memberID) {
		this.MemberID = memberID;
    }

	/**
	* 获取字段Channel的值，该字段的<br>
	* 字段名称 :渠道<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChannel() {
		return Channel;
	}

	/**
	* 设置字段Channel的值，该字段的<br>
	* 字段名称 :渠道<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChannel(String channel) {
		this.Channel = channel;
    }

	/**
	* 获取字段TerminalType的值，该字段的<br>
	* 字段名称 :终端类型<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTerminalType() {
		return TerminalType;
	}

	/**
	* 设置字段TerminalType的值，该字段的<br>
	* 字段名称 :终端类型<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTerminalType(String terminalType) {
		this.TerminalType = terminalType;
    }

	/**
	* 获取字段OSType的值，该字段的<br>
	* 字段名称 :操作系统<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOSType() {
		return OSType;
	}

	/**
	* 设置字段OSType的值，该字段的<br>
	* 字段名称 :操作系统<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOSType(String oSType) {
		this.OSType = oSType;
    }

	/**
	* 获取字段ExplorerType的值，该字段的<br>
	* 字段名称 :浏览器类型<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getExplorerType() {
		return ExplorerType;
	}

	/**
	* 设置字段ExplorerType的值，该字段的<br>
	* 字段名称 :浏览器类型<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setExplorerType(String explorerType) {
		this.ExplorerType = explorerType;
    }

	/**
	* 获取字段ScreenResolution的值，该字段的<br>
	* 字段名称 :屏幕分辨率<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getScreenResolution() {
		return ScreenResolution;
	}

	/**
	* 设置字段ScreenResolution的值，该字段的<br>
	* 字段名称 :屏幕分辨率<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setScreenResolution(String screenResolution) {
		this.ScreenResolution = screenResolution;
    }

	/**
	* 获取字段ISSupportCookie的值，该字段的<br>
	* 字段名称 :是否支持cookie<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getISSupportCookie() {
		return ISSupportCookie;
	}

	/**
	* 设置字段ISSupportCookie的值，该字段的<br>
	* 字段名称 :是否支持cookie<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setISSupportCookie(String iSSupportCookie) {
		this.ISSupportCookie = iSSupportCookie;
    }

	/**
	* 获取字段ISSupportFlash的值，该字段的<br>
	* 字段名称 :是否支持flash<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getISSupportFlash() {
		return ISSupportFlash;
	}

	/**
	* 设置字段ISSupportFlash的值，该字段的<br>
	* 字段名称 :是否支持flash<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setISSupportFlash(String iSSupportFlash) {
		this.ISSupportFlash = iSSupportFlash;
    }

	/**
	* 获取字段Language的值，该字段的<br>
	* 字段名称 :语言<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLanguage() {
		return Language;
	}

	/**
	* 设置字段Language的值，该字段的<br>
	* 字段名称 :语言<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLanguage(String language) {
		this.Language = language;
    }

	/**
	* 获取字段SessionID的值，该字段的<br>
	* 字段名称 :Session地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSessionID() {
		return SessionID;
	}

	/**
	* 设置字段SessionID的值，该字段的<br>
	* 字段名称 :Session地址<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSessionID(String sessionID) {
		this.SessionID = sessionID;
    }

	/**
	* 获取字段Area的值，该字段的<br>
	* 字段名称 :地区<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getArea() {
		return Area;
	}

	/**
	* 设置字段Area的值，该字段的<br>
	* 字段名称 :地区<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setArea(String area) {
		this.Area = area;
    }

	/**
	* 获取字段Entry的值，该字段的<br>
	* 字段名称 :入口<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEntry() {
		return Entry;
	}

	/**
	* 设置字段Entry的值，该字段的<br>
	* 字段名称 :入口<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEntry(String entry) {
		this.Entry = entry;
    }

	/**
	* 获取字段Referer的值，该字段的<br>
	* 字段名称 :Referer<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReferer() {
		return Referer;
	}

	/**
	* 设置字段Referer的值，该字段的<br>
	* 字段名称 :Referer<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReferer(String referer) {
		this.Referer = referer;
    }

	/**
	* 获取字段IsJumpOrClose的值，该字段的<br>
	* 字段名称 :跳出或关闭<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsJumpOrClose() {
		return IsJumpOrClose;
	}

	/**
	* 设置字段IsJumpOrClose的值，该字段的<br>
	* 字段名称 :跳出或关闭<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsJumpOrClose(String isJumpOrClose) {
		this.IsJumpOrClose = isJumpOrClose;
    }

	/**
	* 获取字段Remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark() {
		return Remark;
	}

	/**
	* 设置字段Remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark(String remark) {
		this.Remark = remark;
    }

}