package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：图片关联表<br>
 * 表代码：ZCImageRela<br>
 * 表备注：<br>
在文章中或者图片播放器中上传图片，此时RelaID为文章或者播放器等的ID；
一张图片可以有多个RelaID，即一个ID对应多个RelaID，例如，这张图片即是一篇文章的图片也是一个图片播放器的图片；
在文章中或者图片播放器中上传图片，此时RelaID为文章或者播放器等的ID；<br>
一张图片可以有多个RelaID，即一个ID对应多个RelaID，例如，这张图片即是一篇文章的图片也是一个图片播放器的图片；<br>
<br>
 * 表主键：ID, RelaID, RelaType<br>
 */
public class ZCImageRelaSchema extends Schema {
	private Long ID;

	private Long RelaID;

	private String RelaType;

	private Long OrderFlag;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 0 , 0 , true , true),
		new SchemaColumn("RelaID", DataColumn.LONG, 1, 0 , 0 , true , true),
		new SchemaColumn("RelaType", DataColumn.STRING, 2, 20 , 0 , true , true),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 3, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 4, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 5, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 7, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCImageRela";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into ZCImageRela values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCImageRela set ID=?,RelaID=?,RelaType=?,OrderFlag=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=? and RelaID=? and RelaType=?";

	protected static final String _DeleteSQL = "delete from ZCImageRela  where ID=? and RelaID=? and RelaType=?";

	protected static final String _FillAllSQL = "select * from ZCImageRela  where ID=? and RelaID=? and RelaType=?";

	public ZCImageRelaSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[8];
	}

	protected Schema newInstance(){
		return new ZCImageRelaSchema();
	}

	protected SchemaSet newSet(){
		return new ZCImageRelaSet();
	}

	public ZCImageRelaSet query() {
		return query(null, -1, -1);
	}

	public ZCImageRelaSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCImageRelaSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCImageRelaSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCImageRelaSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){RelaID = null;}else{RelaID = new Long(v.toString());}return;}
		if (i == 2){RelaType = (String)v;return;}
		if (i == 3){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 4){AddUser = (String)v;return;}
		if (i == 5){AddTime = (Date)v;return;}
		if (i == 6){ModifyUser = (String)v;return;}
		if (i == 7){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return RelaID;}
		if (i == 2){return RelaType;}
		if (i == 3){return OrderFlag;}
		if (i == 4){return AddUser;}
		if (i == 5){return AddTime;}
		if (i == 6){return ModifyUser;}
		if (i == 7){return ModifyTime;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :图片ID<br>
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
	* 字段名称 :图片ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :图片ID<br>
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
	* 字段名称 :图片关联ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getRelaID() {
		if(RelaID==null){return 0;}
		return RelaID.longValue();
	}

	/**
	* 设置字段RelaID的值，该字段的<br>
	* 字段名称 :图片关联ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setRelaID(long relaID) {
		this.RelaID = new Long(relaID);
    }

	/**
	* 设置字段RelaID的值，该字段的<br>
	* 字段名称 :图片关联ID<br>
	* 数据类型 :bigint<br>
	* 是否主键 :true<br>
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
	* 获取字段RelaType的值，该字段的<br>
	* 字段名称 :图片关联类别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0-文章图片<br>
	1-图片播放器图片<br>
	2-图片链接<br>
	3-站点Logo<br>
	4-栏目Logo<br>
	*/
	public String getRelaType() {
		return RelaType;
	}

	/**
	* 设置字段RelaType的值，该字段的<br>
	* 字段名称 :图片关联类别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	0-文章图片<br>
	1-图片播放器图片<br>
	2-图片链接<br>
	3-站点Logo<br>
	4-栏目Logo<br>
	*/
	public void setRelaType(String relaType) {
		this.RelaType = relaType;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序值<br>
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
	* 字段名称 :排序值<br>
	* 数据类型 :bigint<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :排序值<br>
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