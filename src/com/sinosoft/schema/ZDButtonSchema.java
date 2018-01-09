package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：菜单按钮表<br>
 * 表代码：ZDButton<br>
 * 表主键：ID<br>
 */
public class ZDButtonSchema extends Schema {
	private Long ID;

	private Long ParentID;

	private String URL;

	private String Code;

	private String ButtonID;

	private String OnClick;

	private String Priv;

	private String Icon;

	private String Name;

	private String Type;

	private String Disabled;

	private Long OrderFlag;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	private String Prop1;

	private String Prop2;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("ParentID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("URL", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Code", DataColumn.STRING, 3, 40 , 0 , true , false),
		new SchemaColumn("ButtonID", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("OnClick", DataColumn.STRING, 5, 10 , 0 , true , false),
		new SchemaColumn("Priv", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("Icon", DataColumn.STRING, 7, 40 , 0 , false , false),
		new SchemaColumn("Name", DataColumn.STRING, 8, 40 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("Disabled", DataColumn.STRING, 10, 10 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 11, 20 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 17, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 18, 50 , 0 , false , false)
	};

	public static final String _TableCode = "ZDButton";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDButton values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDButton set ID=?,ParentID=?,URL=?,Code=?,ButtonID=?,OnClick=?,Priv=?,Icon=?,Name=?,Type=?,Disabled=?,OrderFlag=?,Memo=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=?,Prop1=?,Prop2=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZDButton  where ID=?";

	protected static final String _FillAllSQL = "select * from ZDButton  where ID=?";

	public ZDButtonSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[19];
	}

	protected Schema newInstance(){
		return new ZDButtonSchema();
	}

	protected SchemaSet newSet(){
		return new ZDButtonSet();
	}

	public ZDButtonSet query() {
		return query(null, -1, -1);
	}

	public ZDButtonSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDButtonSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDButtonSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDButtonSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){ParentID = null;}else{ParentID = new Long(v.toString());}return;}
		if (i == 2){URL = (String)v;return;}
		if (i == 3){Code = (String)v;return;}
		if (i == 4){ButtonID = (String)v;return;}
		if (i == 5){OnClick = (String)v;return;}
		if (i == 6){Priv = (String)v;return;}
		if (i == 7){Icon = (String)v;return;}
		if (i == 8){Name = (String)v;return;}
		if (i == 9){Type = (String)v;return;}
		if (i == 10){Disabled = (String)v;return;}
		if (i == 11){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 12){Memo = (String)v;return;}
		if (i == 13){AddTime = (Date)v;return;}
		if (i == 14){AddUser = (String)v;return;}
		if (i == 15){ModifyTime = (Date)v;return;}
		if (i == 16){ModifyUser = (String)v;return;}
		if (i == 17){Prop1 = (String)v;return;}
		if (i == 18){Prop2 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ParentID;}
		if (i == 2){return URL;}
		if (i == 3){return Code;}
		if (i == 4){return ButtonID;}
		if (i == 5){return OnClick;}
		if (i == 6){return Priv;}
		if (i == 7){return Icon;}
		if (i == 8){return Name;}
		if (i == 9){return Type;}
		if (i == 10){return Disabled;}
		if (i == 11){return OrderFlag;}
		if (i == 12){return Memo;}
		if (i == 13){return AddTime;}
		if (i == 14){return AddUser;}
		if (i == 15){return ModifyTime;}
		if (i == 16){return ModifyUser;}
		if (i == 17){return Prop1;}
		if (i == 18){return Prop2;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :流水号<br>
	* 数据类型 :bigint(20)<br>
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
	* 获取字段ParentID的值，该字段的<br>
	* 字段名称 :父按钮ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getParentID() {
		if(ParentID==null){return 0;}
		return ParentID.longValue();
	}

	/**
	* 设置字段ParentID的值，该字段的<br>
	* 字段名称 :父按钮ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParentID(long parentID) {
		this.ParentID = new Long(parentID);
    }

	/**
	* 设置字段ParentID的值，该字段的<br>
	* 字段名称 :父按钮ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParentID(String parentID) {
		if (parentID == null){
			this.ParentID = null;
			return;
		}
		this.ParentID = new Long(parentID);
    }

	/**
	* 获取字段URL的值，该字段的<br>
	* 字段名称 :页面路径URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* 设置字段URL的值，该字段的<br>
	* 字段名称 :页面路径URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* 获取字段Code的值，该字段的<br>
	* 字段名称 :按钮编码<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* 设置字段Code的值，该字段的<br>
	* 字段名称 :按钮编码<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCode(String code) {
		this.Code = code;
    }

	/**
	* 获取字段ButtonID的值，该字段的<br>
	* 字段名称 :按钮ID<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getButtonID() {
		return ButtonID;
	}

	/**
	* 设置字段ButtonID的值，该字段的<br>
	* 字段名称 :按钮ID<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setButtonID(String buttonID) {
		this.ButtonID = buttonID;
    }

	/**
	* 获取字段OnClick的值，该字段的<br>
	* 字段名称 :按钮事件<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getOnClick() {
		return OnClick;
	}

	/**
	* 设置字段OnClick的值，该字段的<br>
	* 字段名称 :按钮事件<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOnClick(String onClick) {
		this.OnClick = onClick;
    }

	/**
	* 获取字段Priv的值，该字段的<br>
	* 字段名称 :按钮权限<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPriv() {
		return Priv;
	}

	/**
	* 设置字段Priv的值，该字段的<br>
	* 字段名称 :按钮权限<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPriv(String priv) {
		this.Priv = priv;
    }

	/**
	* 获取字段Icon的值，该字段的<br>
	* 字段名称 :按钮图片<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIcon() {
		return Icon;
	}

	/**
	* 设置字段Icon的值，该字段的<br>
	* 字段名称 :按钮图片<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIcon(String icon) {
		this.Icon = icon;
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :按钮名称<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :按钮名称<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :按钮类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :按钮类型<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段Disabled的值，该字段的<br>
	* 字段名称 :不可点击<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDisabled() {
		return Disabled;
	}

	/**
	* 设置字段Disabled的值，该字段的<br>
	* 字段名称 :不可点击<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDisabled(String disabled) {
		this.Disabled = disabled;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序字符串<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序字符串<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序字符串<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :增加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :增加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :增加人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :增加人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
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

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段一<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用字段一<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段二<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用字段二<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

}