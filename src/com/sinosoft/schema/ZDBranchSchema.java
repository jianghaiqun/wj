package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：组织机构表<br>
 * 表代码：ZDBranch<br>
 * 表主键：BranchInnerCode<br>
 */
public class ZDBranchSchema extends Schema {
	private String BranchInnerCode;

	private String BranchCode;

	private String ParentInnerCode;

	private String Type;

	private Long OrderFlag;

	private String Name;

	private Long TreeLevel;

	private String IsLeaf;

	private String Phone;

	private String Fax;

	private String Manager;

	private String Leader1;

	private String Leader2;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("BranchCode", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("ParentInnerCode", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 3, 1 , 0 , true , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 4, 0 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 5, 100 , 0 , true , false),
		new SchemaColumn("TreeLevel", DataColumn.LONG, 6, 0 , 0 , true , false),
		new SchemaColumn("IsLeaf", DataColumn.STRING, 7, 2 , 0 , false , false),
		new SchemaColumn("Phone", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("Fax", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("Manager", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Leader1", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("Leader2", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 17, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 18, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 19, 200 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 20, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 21, 200 , 0 , false , false)
	};

	public static final String _TableCode = "ZDBranch";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZDBranch values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDBranch set BranchInnerCode=?,BranchCode=?,ParentInnerCode=?,Type=?,OrderFlag=?,Name=?,TreeLevel=?,IsLeaf=?,Phone=?,Fax=?,Manager=?,Leader1=?,Leader2=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Memo=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=? where BranchInnerCode=?";

	protected static final String _DeleteSQL = "delete from ZDBranch  where BranchInnerCode=?";

	protected static final String _FillAllSQL = "select * from ZDBranch  where BranchInnerCode=?";

	public ZDBranchSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[22];
	}

	protected Schema newInstance(){
		return new ZDBranchSchema();
	}

	protected SchemaSet newSet(){
		return new ZDBranchSet();
	}

	public ZDBranchSet query() {
		return query(null, -1, -1);
	}

	public ZDBranchSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDBranchSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDBranchSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDBranchSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){BranchInnerCode = (String)v;return;}
		if (i == 1){BranchCode = (String)v;return;}
		if (i == 2){ParentInnerCode = (String)v;return;}
		if (i == 3){Type = (String)v;return;}
		if (i == 4){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 5){Name = (String)v;return;}
		if (i == 6){if(v==null){TreeLevel = null;}else{TreeLevel = new Long(v.toString());}return;}
		if (i == 7){IsLeaf = (String)v;return;}
		if (i == 8){Phone = (String)v;return;}
		if (i == 9){Fax = (String)v;return;}
		if (i == 10){Manager = (String)v;return;}
		if (i == 11){Leader1 = (String)v;return;}
		if (i == 12){Leader2 = (String)v;return;}
		if (i == 13){Prop1 = (String)v;return;}
		if (i == 14){Prop2 = (String)v;return;}
		if (i == 15){Prop3 = (String)v;return;}
		if (i == 16){Prop4 = (String)v;return;}
		if (i == 17){Memo = (String)v;return;}
		if (i == 18){AddTime = (Date)v;return;}
		if (i == 19){AddUser = (String)v;return;}
		if (i == 20){ModifyTime = (Date)v;return;}
		if (i == 21){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return BranchInnerCode;}
		if (i == 1){return BranchCode;}
		if (i == 2){return ParentInnerCode;}
		if (i == 3){return Type;}
		if (i == 4){return OrderFlag;}
		if (i == 5){return Name;}
		if (i == 6){return TreeLevel;}
		if (i == 7){return IsLeaf;}
		if (i == 8){return Phone;}
		if (i == 9){return Fax;}
		if (i == 10){return Manager;}
		if (i == 11){return Leader1;}
		if (i == 12){return Leader2;}
		if (i == 13){return Prop1;}
		if (i == 14){return Prop2;}
		if (i == 15){return Prop3;}
		if (i == 16){return Prop4;}
		if (i == 17){return Memo;}
		if (i == 18){return AddTime;}
		if (i == 19){return AddUser;}
		if (i == 20){return ModifyTime;}
		if (i == 21){return ModifyUser;}
		return null;
	}

	/**
	* 获取字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :内部编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* 设置字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :内部编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
    }

	/**
	* 获取字段BranchCode的值，该字段的<br>
	* 字段名称 :机构编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBranchCode() {
		return BranchCode;
	}

	/**
	* 设置字段BranchCode的值，该字段的<br>
	* 字段名称 :机构编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBranchCode(String branchCode) {
		this.BranchCode = branchCode;
    }

	/**
	* 获取字段ParentInnerCode的值，该字段的<br>
	* 字段名称 :父级内部编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getParentInnerCode() {
		return ParentInnerCode;
	}

	/**
	* 设置字段ParentInnerCode的值，该字段的<br>
	* 字段名称 :父级内部编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParentInnerCode(String parentInnerCode) {
		this.ParentInnerCode = parentInnerCode;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :机构类型<br>
	* 数据类型 :char(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :机构类型<br>
	* 数据类型 :char(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序字段<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序字段<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序字段<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段TreeLevel的值，该字段的<br>
	* 字段名称 :级别<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getTreeLevel() {
		if(TreeLevel==null){return 0;}
		return TreeLevel.longValue();
	}

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :级别<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTreeLevel(long treeLevel) {
		this.TreeLevel = new Long(treeLevel);
    }

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :级别<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTreeLevel(String treeLevel) {
		if (treeLevel == null){
			this.TreeLevel = null;
			return;
		}
		this.TreeLevel = new Long(treeLevel);
    }

	/**
	* 获取字段IsLeaf的值，该字段的<br>
	* 字段名称 :是否叶子节点<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsLeaf() {
		return IsLeaf;
	}

	/**
	* 设置字段IsLeaf的值，该字段的<br>
	* 字段名称 :是否叶子节点<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsLeaf(String isLeaf) {
		this.IsLeaf = isLeaf;
    }

	/**
	* 获取字段Phone的值，该字段的<br>
	* 字段名称 :电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPhone() {
		return Phone;
	}

	/**
	* 设置字段Phone的值，该字段的<br>
	* 字段名称 :电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPhone(String phone) {
		this.Phone = phone;
    }

	/**
	* 获取字段Fax的值，该字段的<br>
	* 字段名称 :传真<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFax() {
		return Fax;
	}

	/**
	* 设置字段Fax的值，该字段的<br>
	* 字段名称 :传真<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFax(String fax) {
		this.Fax = fax;
    }

	/**
	* 获取字段Manager的值，该字段的<br>
	* 字段名称 :主管人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getManager() {
		return Manager;
	}

	/**
	* 设置字段Manager的值，该字段的<br>
	* 字段名称 :主管人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setManager(String manager) {
		this.Manager = manager;
    }

	/**
	* 获取字段Leader1的值，该字段的<br>
	* 字段名称 :上级主管人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLeader1() {
		return Leader1;
	}

	/**
	* 设置字段Leader1的值，该字段的<br>
	* 字段名称 :上级主管人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLeader1(String leader1) {
		this.Leader1 = leader1;
    }

	/**
	* 获取字段Leader2的值，该字段的<br>
	* 字段名称 :上级分管人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLeader2() {
		return Leader2;
	}

	/**
	* 设置字段Leader2的值，该字段的<br>
	* 字段名称 :上级分管人<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLeader2(String leader2) {
		this.Leader2 = leader2;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用属性1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用属性1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用属性2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用属性2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用属性3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用属性3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用属性4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用属性4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(100)<br>
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
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :增加时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :增加人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :增加人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
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
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

}