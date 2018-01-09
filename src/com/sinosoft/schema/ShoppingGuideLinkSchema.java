package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：导购链接<br>
 * 表代码：ShoppingGuideLink<br>
 * 表主键：Id<br>
 */
public class ShoppingGuideLinkSchema extends Schema {
	private String Id;

	private String ParentId;

	private String Name;

	private String Link;

	private String Image;

	private Long TreeLevel;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("ParentId", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("Name", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("Link", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("Image", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("TreeLevel", DataColumn.LONG, 5, 20 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 12, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ShoppingGuideLink";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ShoppingGuideLink values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ShoppingGuideLink set Id=?,ParentId=?,Name=?,Link=?,Image=?,TreeLevel=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=?,Prop1=?,Prop2=?,Prop3=? where Id=?";

	protected static final String _DeleteSQL = "delete from ShoppingGuideLink  where Id=?";

	protected static final String _FillAllSQL = "select * from ShoppingGuideLink  where Id=?";

	public ShoppingGuideLinkSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[13];
	}

	protected Schema newInstance(){
		return new ShoppingGuideLinkSchema();
	}

	protected SchemaSet newSet(){
		return new ShoppingGuideLinkSet();
	}

	public ShoppingGuideLinkSet query() {
		return query(null, -1, -1);
	}

	public ShoppingGuideLinkSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ShoppingGuideLinkSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ShoppingGuideLinkSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ShoppingGuideLinkSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){ParentId = (String)v;return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){Link = (String)v;return;}
		if (i == 4){Image = (String)v;return;}
		if (i == 5){if(v==null){TreeLevel = null;}else{TreeLevel = new Long(v.toString());}return;}
		if (i == 6){AddTime = (Date)v;return;}
		if (i == 7){AddUser = (String)v;return;}
		if (i == 8){ModifyTime = (Date)v;return;}
		if (i == 9){ModifyUser = (String)v;return;}
		if (i == 10){Prop1 = (String)v;return;}
		if (i == 11){Prop2 = (String)v;return;}
		if (i == 12){Prop3 = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return ParentId;}
		if (i == 2){return Name;}
		if (i == 3){return Link;}
		if (i == 4){return Image;}
		if (i == 5){return TreeLevel;}
		if (i == 6){return AddTime;}
		if (i == 7){return AddUser;}
		if (i == 8){return ModifyTime;}
		if (i == 9){return ModifyUser;}
		if (i == 10){return Prop1;}
		if (i == 11){return Prop2;}
		if (i == 12){return Prop3;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段ParentId的值，该字段的<br>
	* 字段名称 :ParentId<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getParentId() {
		return ParentId;
	}

	/**
	* 设置字段ParentId的值，该字段的<br>
	* 字段名称 :ParentId<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setParentId(String parentId) {
		this.ParentId = parentId;
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段Link的值，该字段的<br>
	* 字段名称 :Link<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLink() {
		return Link;
	}

	/**
	* 设置字段Link的值，该字段的<br>
	* 字段名称 :Link<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLink(String link) {
		this.Link = link;
    }

	/**
	* 获取字段Image的值，该字段的<br>
	* 字段名称 :Image<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getImage() {
		return Image;
	}

	/**
	* 设置字段Image的值，该字段的<br>
	* 字段名称 :Image<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setImage(String image) {
		this.Image = image;
    }

	/**
	* 获取字段TreeLevel的值，该字段的<br>
	* 字段名称 :TreeLevel<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getTreeLevel() {
		if(TreeLevel==null){return 0;}
		return TreeLevel.longValue();
	}

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :TreeLevel<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTreeLevel(long treeLevel) {
		this.TreeLevel = new Long(treeLevel);
    }

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :TreeLevel<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTreeLevel(String treeLevel) {
		if (treeLevel == null){
			this.TreeLevel = null;
			return;
		}
		this.TreeLevel = new Long(treeLevel);
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :ModifyTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :ModifyTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

}