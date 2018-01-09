package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：客户理赔资料信息<br>
 * 表代码：CustomClaimsDataInfo<br>
 * 表主键：id<br>
 */
public class CustomClaimsDataInfoSchema extends Schema {
	private String id;

	private String claimsNo;

	private String femClaimsDataId;

	private String claimsDataName;

	private Integer photoNum;

	private String isMust;

	private String claimsForm;

	private String claimsItemsId;

	private String claimsDataType;

	private String remark1;

	private String remark2;

	private String remark3;

	private String createUser;

	private Date createDate;

	private String modifyUser;

	private Date modifyDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("claimsNo", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("femClaimsDataId", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("claimsDataName", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("photoNum", DataColumn.INTEGER, 4, 11 , 0 , false , false),
		new SchemaColumn("isMust", DataColumn.STRING, 5, 2 , 0 , false , false),
		new SchemaColumn("claimsForm", DataColumn.STRING, 6, 255 , 0 , false , false),
		new SchemaColumn("claimsItemsId", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("claimsDataType", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("remark1", DataColumn.STRING, 9, 255 , 0 , false , false),
		new SchemaColumn("remark2", DataColumn.STRING, 10, 255 , 0 , false , false),
		new SchemaColumn("remark3", DataColumn.STRING, 11, 255 , 0 , false , false),
		new SchemaColumn("createUser", DataColumn.STRING, 12, 255 , 0 , false , false),
		new SchemaColumn("createDate", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("modifyUser", DataColumn.STRING, 14, 255 , 0 , false , false),
		new SchemaColumn("modifyDate", DataColumn.DATETIME, 15, 0 , 0 , false , false)
	};

	public static final String _TableCode = "CustomClaimsDataInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into CustomClaimsDataInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update CustomClaimsDataInfo set id=?,claimsNo=?,femClaimsDataId=?,claimsDataName=?,photoNum=?,isMust=?,claimsForm=?,claimsItemsId=?,claimsDataType=?,remark1=?,remark2=?,remark3=?,createUser=?,createDate=?,modifyUser=?,modifyDate=? where id=?";

	protected static final String _DeleteSQL = "delete from CustomClaimsDataInfo  where id=?";

	protected static final String _FillAllSQL = "select * from CustomClaimsDataInfo  where id=?";

	public CustomClaimsDataInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[16];
	}

	protected Schema newInstance(){
		return new CustomClaimsDataInfoSchema();
	}

	protected SchemaSet newSet(){
		return new CustomClaimsDataInfoSet();
	}

	public CustomClaimsDataInfoSet query() {
		return query(null, -1, -1);
	}

	public CustomClaimsDataInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public CustomClaimsDataInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public CustomClaimsDataInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (CustomClaimsDataInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){claimsNo = (String)v;return;}
		if (i == 2){femClaimsDataId = (String)v;return;}
		if (i == 3){claimsDataName = (String)v;return;}
		if (i == 4){if(v==null){photoNum = null;}else{photoNum = new Integer(v.toString());}return;}
		if (i == 5){isMust = (String)v;return;}
		if (i == 6){claimsForm = (String)v;return;}
		if (i == 7){claimsItemsId = (String)v;return;}
		if (i == 8){claimsDataType = (String)v;return;}
		if (i == 9){remark1 = (String)v;return;}
		if (i == 10){remark2 = (String)v;return;}
		if (i == 11){remark3 = (String)v;return;}
		if (i == 12){createUser = (String)v;return;}
		if (i == 13){createDate = (Date)v;return;}
		if (i == 14){modifyUser = (String)v;return;}
		if (i == 15){modifyDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return claimsNo;}
		if (i == 2){return femClaimsDataId;}
		if (i == 3){return claimsDataName;}
		if (i == 4){return photoNum;}
		if (i == 5){return isMust;}
		if (i == 6){return claimsForm;}
		if (i == 7){return claimsItemsId;}
		if (i == 8){return claimsDataType;}
		if (i == 9){return remark1;}
		if (i == 10){return remark2;}
		if (i == 11){return remark3;}
		if (i == 12){return createUser;}
		if (i == 13){return createDate;}
		if (i == 14){return modifyUser;}
		if (i == 15){return modifyDate;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getid() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setid(String id) {
		this.id = id;
    }

	/**
	* 获取字段claimsNo的值，该字段的<br>
	* 字段名称 :理赔单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimsNo() {
		return claimsNo;
	}

	/**
	* 设置字段claimsNo的值，该字段的<br>
	* 字段名称 :理赔单号<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsNo(String claimsNo) {
		this.claimsNo = claimsNo;
    }

	/**
	* 获取字段femClaimsDataId的值，该字段的<br>
	* 字段名称 :产品中心理赔资料Id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfemClaimsDataId() {
		return femClaimsDataId;
	}

	/**
	* 设置字段femClaimsDataId的值，该字段的<br>
	* 字段名称 :产品中心理赔资料Id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfemClaimsDataId(String femClaimsDataId) {
		this.femClaimsDataId = femClaimsDataId;
    }

	/**
	* 获取字段claimsDataName的值，该字段的<br>
	* 字段名称 :理赔资料名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimsDataName() {
		return claimsDataName;
	}

	/**
	* 设置字段claimsDataName的值，该字段的<br>
	* 字段名称 :理赔资料名称<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsDataName(String claimsDataName) {
		this.claimsDataName = claimsDataName;
    }

	/**
	* 获取字段photoNum的值，该字段的<br>
	* 字段名称 :照片张数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getphotoNum() {
		if(photoNum==null){return 0;}
		return photoNum.intValue();
	}

	/**
	* 设置字段photoNum的值，该字段的<br>
	* 字段名称 :照片张数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setphotoNum(int photoNum) {
		this.photoNum = new Integer(photoNum);
    }

	/**
	* 设置字段photoNum的值，该字段的<br>
	* 字段名称 :照片张数<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setphotoNum(String photoNum) {
		if (photoNum == null){
			this.photoNum = null;
			return;
		}
		this.photoNum = new Integer(photoNum);
    }

	/**
	* 获取字段isMust的值，该字段的<br>
	* 字段名称 :是否必填<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getisMust() {
		return isMust;
	}

	/**
	* 设置字段isMust的值，该字段的<br>
	* 字段名称 :是否必填<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setisMust(String isMust) {
		this.isMust = isMust;
    }

	/**
	* 获取字段claimsForm的值，该字段的<br>
	* 字段名称 :理赔申请表<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimsForm() {
		return claimsForm;
	}

	/**
	* 设置字段claimsForm的值，该字段的<br>
	* 字段名称 :理赔申请表<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsForm(String claimsForm) {
		this.claimsForm = claimsForm;
    }

	/**
	* 获取字段claimsItemsId的值，该字段的<br>
	* 字段名称 :理赔项目ID<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimsItemsId() {
		return claimsItemsId;
	}

	/**
	* 设置字段claimsItemsId的值，该字段的<br>
	* 字段名称 :理赔项目ID<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsItemsId(String claimsItemsId) {
		this.claimsItemsId = claimsItemsId;
    }

	/**
	* 获取字段claimsDataType的值，该字段的<br>
	* 字段名称 :理赔资料类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getclaimsDataType() {
		return claimsDataType;
	}

	/**
	* 设置字段claimsDataType的值，该字段的<br>
	* 字段名称 :理赔资料类型<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setclaimsDataType(String claimsDataType) {
		this.claimsDataType = claimsDataType;
    }

	/**
	* 获取字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark1() {
		return remark1;
	}

	/**
	* 设置字段remark1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark1(String remark1) {
		this.remark1 = remark1;
    }

	/**
	* 获取字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark2() {
		return remark2;
	}

	/**
	* 设置字段remark2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark2(String remark2) {
		this.remark2 = remark2;
    }

	/**
	* 获取字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getremark3() {
		return remark3;
	}

	/**
	* 设置字段remark3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setremark3(String remark3) {
		this.remark3 = remark3;
    }

	/**
	* 获取字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcreateUser() {
		return createUser;
	}

	/**
	* 设置字段createUser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateUser(String createUser) {
		this.createUser = createUser;
    }

	/**
	* 获取字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getcreateDate() {
		return createDate;
	}

	/**
	* 设置字段createDate的值，该字段的<br>
	* 字段名称 :创建日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcreateDate(Date createDate) {
		this.createDate = createDate;
    }

	/**
	* 获取字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyUser() {
		return modifyUser;
	}

	/**
	* 设置字段modifyUser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
    }

	/**
	* 获取字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifyDate() {
		return modifyDate;
	}

	/**
	* 设置字段modifyDate的值，该字段的<br>
	* 字段名称 :修改日期<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
    }

}