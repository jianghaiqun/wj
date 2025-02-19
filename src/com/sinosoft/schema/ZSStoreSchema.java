package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：连锁分店表<br>
 * 表代码：ZSStore<br>
 * 表主键：StoreCode<br>
 */
public class ZSStoreSchema extends Schema {
	private String StoreCode;

	private String ParentCode;

	private String Name;

	private String Alias;

	private Long TreeLevel;

	private Long SiteID;

	private Long OrderFlag;

	private String URL;

	private String Info;

	private String Country;

	private String Province;

	private String City;

	private String District;

	private String Address;

	private String ZipCode;

	private String Tel;

	private String Fax;

	private String Mobile;

	private String Contacter;

	private String ContacterEmail;

	private String ContacterTel;

	private String ContacterMobile;

	private String ContacterQQ;

	private String ContacterMSN;

	private String Memo;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("StoreCode", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("ParentCode", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Alias", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("TreeLevel", DataColumn.LONG, 4, 0 , 0 , true , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 5, 0 , 0 , true , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 6, 0 , 0 , true , false),
		new SchemaColumn("URL", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Info", DataColumn.STRING, 8, 2000 , 0 , false , false),
		new SchemaColumn("Country", DataColumn.STRING, 9, 30 , 0 , false , false),
		new SchemaColumn("Province", DataColumn.STRING, 10, 6 , 0 , false , false),
		new SchemaColumn("City", DataColumn.STRING, 11, 6 , 0 , false , false),
		new SchemaColumn("District", DataColumn.STRING, 12, 6 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 13, 400 , 0 , false , false),
		new SchemaColumn("ZipCode", DataColumn.STRING, 14, 10 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 15, 20 , 0 , false , false),
		new SchemaColumn("Fax", DataColumn.STRING, 16, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 17, 30 , 0 , false , false),
		new SchemaColumn("Contacter", DataColumn.STRING, 18, 40 , 0 , false , false),
		new SchemaColumn("ContacterEmail", DataColumn.STRING, 19, 100 , 0 , false , false),
		new SchemaColumn("ContacterTel", DataColumn.STRING, 20, 20 , 0 , false , false),
		new SchemaColumn("ContacterMobile", DataColumn.STRING, 21, 20 , 0 , false , false),
		new SchemaColumn("ContacterQQ", DataColumn.STRING, 22, 20 , 0 , false , false),
		new SchemaColumn("ContacterMSN", DataColumn.STRING, 23, 50 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 24, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 25, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 26, 200 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 27, 200 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 28, 200 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 29, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 30, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 31, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 32, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZSStore";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZSStore values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZSStore set StoreCode=?,ParentCode=?,Name=?,Alias=?,TreeLevel=?,SiteID=?,OrderFlag=?,URL=?,Info=?,Country=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Fax=?,Mobile=?,Contacter=?,ContacterEmail=?,ContacterTel=?,ContacterMobile=?,ContacterQQ=?,ContacterMSN=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where StoreCode=?";

	protected static final String _DeleteSQL = "delete from ZSStore  where StoreCode=?";

	protected static final String _FillAllSQL = "select * from ZSStore  where StoreCode=?";

	public ZSStoreSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[33];
	}

	protected Schema newInstance(){
		return new ZSStoreSchema();
	}

	protected SchemaSet newSet(){
		return new ZSStoreSet();
	}

	public ZSStoreSet query() {
		return query(null, -1, -1);
	}

	public ZSStoreSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZSStoreSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZSStoreSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZSStoreSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){StoreCode = (String)v;return;}
		if (i == 1){ParentCode = (String)v;return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){Alias = (String)v;return;}
		if (i == 4){if(v==null){TreeLevel = null;}else{TreeLevel = new Long(v.toString());}return;}
		if (i == 5){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 6){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 7){URL = (String)v;return;}
		if (i == 8){Info = (String)v;return;}
		if (i == 9){Country = (String)v;return;}
		if (i == 10){Province = (String)v;return;}
		if (i == 11){City = (String)v;return;}
		if (i == 12){District = (String)v;return;}
		if (i == 13){Address = (String)v;return;}
		if (i == 14){ZipCode = (String)v;return;}
		if (i == 15){Tel = (String)v;return;}
		if (i == 16){Fax = (String)v;return;}
		if (i == 17){Mobile = (String)v;return;}
		if (i == 18){Contacter = (String)v;return;}
		if (i == 19){ContacterEmail = (String)v;return;}
		if (i == 20){ContacterTel = (String)v;return;}
		if (i == 21){ContacterMobile = (String)v;return;}
		if (i == 22){ContacterQQ = (String)v;return;}
		if (i == 23){ContacterMSN = (String)v;return;}
		if (i == 24){Memo = (String)v;return;}
		if (i == 25){Prop1 = (String)v;return;}
		if (i == 26){Prop2 = (String)v;return;}
		if (i == 27){Prop3 = (String)v;return;}
		if (i == 28){Prop4 = (String)v;return;}
		if (i == 29){AddUser = (String)v;return;}
		if (i == 30){AddTime = (Date)v;return;}
		if (i == 31){ModifyUser = (String)v;return;}
		if (i == 32){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return StoreCode;}
		if (i == 1){return ParentCode;}
		if (i == 2){return Name;}
		if (i == 3){return Alias;}
		if (i == 4){return TreeLevel;}
		if (i == 5){return SiteID;}
		if (i == 6){return OrderFlag;}
		if (i == 7){return URL;}
		if (i == 8){return Info;}
		if (i == 9){return Country;}
		if (i == 10){return Province;}
		if (i == 11){return City;}
		if (i == 12){return District;}
		if (i == 13){return Address;}
		if (i == 14){return ZipCode;}
		if (i == 15){return Tel;}
		if (i == 16){return Fax;}
		if (i == 17){return Mobile;}
		if (i == 18){return Contacter;}
		if (i == 19){return ContacterEmail;}
		if (i == 20){return ContacterTel;}
		if (i == 21){return ContacterMobile;}
		if (i == 22){return ContacterQQ;}
		if (i == 23){return ContacterMSN;}
		if (i == 24){return Memo;}
		if (i == 25){return Prop1;}
		if (i == 26){return Prop2;}
		if (i == 27){return Prop3;}
		if (i == 28){return Prop4;}
		if (i == 29){return AddUser;}
		if (i == 30){return AddTime;}
		if (i == 31){return ModifyUser;}
		if (i == 32){return ModifyTime;}
		return null;
	}

	/**
	* 获取字段StoreCode的值，该字段的<br>
	* 字段名称 :商家编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getStoreCode() {
		return StoreCode;
	}

	/**
	* 设置字段StoreCode的值，该字段的<br>
	* 字段名称 :商家编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setStoreCode(String storeCode) {
		this.StoreCode = storeCode;
    }

	/**
	* 获取字段ParentCode的值，该字段的<br>
	* 字段名称 :父编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getParentCode() {
		return ParentCode;
	}

	/**
	* 设置字段ParentCode的值，该字段的<br>
	* 字段名称 :父编码<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParentCode(String parentCode) {
		this.ParentCode = parentCode;
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :商家名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :商家名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段Alias的值，该字段的<br>
	* 字段名称 :商家别名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAlias() {
		return Alias;
	}

	/**
	* 设置字段Alias的值，该字段的<br>
	* 字段名称 :商家别名<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAlias(String alias) {
		this.Alias = alias;
    }

	/**
	* 获取字段TreeLevel的值，该字段的<br>
	* 字段名称 :层级<br>
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
	* 字段名称 :层级<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTreeLevel(long treeLevel) {
		this.TreeLevel = new Long(treeLevel);
    }

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :层级<br>
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
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序权值<br>
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
	* 字段名称 :排序权值<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序权值<br>
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
	* 获取字段URL的值，该字段的<br>
	* 字段名称 :商家URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* 设置字段URL的值，该字段的<br>
	* 字段名称 :商家URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* 获取字段Info的值，该字段的<br>
	* 字段名称 :商家介绍<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* 设置字段Info的值，该字段的<br>
	* 字段名称 :商家介绍<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
    }

	/**
	* 获取字段Country的值，该字段的<br>
	* 字段名称 :商家国家<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCountry() {
		return Country;
	}

	/**
	* 设置字段Country的值，该字段的<br>
	* 字段名称 :商家国家<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCountry(String country) {
		this.Country = country;
    }

	/**
	* 获取字段Province的值，该字段的<br>
	* 字段名称 :商家省份<br>
	* 数据类型 :char(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProvince() {
		return Province;
	}

	/**
	* 设置字段Province的值，该字段的<br>
	* 字段名称 :商家省份<br>
	* 数据类型 :char(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProvince(String province) {
		this.Province = province;
    }

	/**
	* 获取字段City的值，该字段的<br>
	* 字段名称 :商家城市<br>
	* 数据类型 :char(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCity() {
		return City;
	}

	/**
	* 设置字段City的值，该字段的<br>
	* 字段名称 :商家城市<br>
	* 数据类型 :char(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCity(String city) {
		this.City = city;
    }

	/**
	* 获取字段District的值，该字段的<br>
	* 字段名称 :区县<br>
	* 数据类型 :char(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDistrict() {
		return District;
	}

	/**
	* 设置字段District的值，该字段的<br>
	* 字段名称 :区县<br>
	* 数据类型 :char(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDistrict(String district) {
		this.District = district;
    }

	/**
	* 获取字段Address的值，该字段的<br>
	* 字段名称 :商家地址<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* 设置字段Address的值，该字段的<br>
	* 字段名称 :商家地址<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddress(String address) {
		this.Address = address;
    }

	/**
	* 获取字段ZipCode的值，该字段的<br>
	* 字段名称 :商家邮编<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getZipCode() {
		return ZipCode;
	}

	/**
	* 设置字段ZipCode的值，该字段的<br>
	* 字段名称 :商家邮编<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setZipCode(String zipCode) {
		this.ZipCode = zipCode;
    }

	/**
	* 获取字段Tel的值，该字段的<br>
	* 字段名称 :商家固定电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTel() {
		return Tel;
	}

	/**
	* 设置字段Tel的值，该字段的<br>
	* 字段名称 :商家固定电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTel(String tel) {
		this.Tel = tel;
    }

	/**
	* 获取字段Fax的值，该字段的<br>
	* 字段名称 :商家传真<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFax() {
		return Fax;
	}

	/**
	* 设置字段Fax的值，该字段的<br>
	* 字段名称 :商家传真<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFax(String fax) {
		this.Fax = fax;
    }

	/**
	* 获取字段Mobile的值，该字段的<br>
	* 字段名称 :商家移动电话<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMobile() {
		return Mobile;
	}

	/**
	* 设置字段Mobile的值，该字段的<br>
	* 字段名称 :商家移动电话<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMobile(String mobile) {
		this.Mobile = mobile;
    }

	/**
	* 获取字段Contacter的值，该字段的<br>
	* 字段名称 :联系人<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContacter() {
		return Contacter;
	}

	/**
	* 设置字段Contacter的值，该字段的<br>
	* 字段名称 :联系人<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContacter(String contacter) {
		this.Contacter = contacter;
    }

	/**
	* 获取字段ContacterEmail的值，该字段的<br>
	* 字段名称 :联系人邮箱<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContacterEmail() {
		return ContacterEmail;
	}

	/**
	* 设置字段ContacterEmail的值，该字段的<br>
	* 字段名称 :联系人邮箱<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContacterEmail(String contacterEmail) {
		this.ContacterEmail = contacterEmail;
    }

	/**
	* 获取字段ContacterTel的值，该字段的<br>
	* 字段名称 :联系人固定电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContacterTel() {
		return ContacterTel;
	}

	/**
	* 设置字段ContacterTel的值，该字段的<br>
	* 字段名称 :联系人固定电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContacterTel(String contacterTel) {
		this.ContacterTel = contacterTel;
    }

	/**
	* 获取字段ContacterMobile的值，该字段的<br>
	* 字段名称 :联系人移动电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContacterMobile() {
		return ContacterMobile;
	}

	/**
	* 设置字段ContacterMobile的值，该字段的<br>
	* 字段名称 :联系人移动电话<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContacterMobile(String contacterMobile) {
		this.ContacterMobile = contacterMobile;
    }

	/**
	* 获取字段ContacterQQ的值，该字段的<br>
	* 字段名称 :联系人QQ<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContacterQQ() {
		return ContacterQQ;
	}

	/**
	* 设置字段ContacterQQ的值，该字段的<br>
	* 字段名称 :联系人QQ<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContacterQQ(String contacterQQ) {
		this.ContacterQQ = contacterQQ;
    }

	/**
	* 获取字段ContacterMSN的值，该字段的<br>
	* 字段名称 :联系人MSN<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContacterMSN() {
		return ContacterMSN;
	}

	/**
	* 设置字段ContacterMSN的值，该字段的<br>
	* 字段名称 :联系人MSN<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContacterMSN(String contacterMSN) {
		this.ContacterMSN = contacterMSN;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :备用属性1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :备用属性1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :备用属性2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :备用属性2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :备用属性3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :备用属性3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :备用属性4<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :备用属性4<br>
	* 数据类型 :varchar(200)<br>
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

}