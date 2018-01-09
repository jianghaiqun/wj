package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

/**
 * 表名称：优惠劵买赠活动表<br>
 * 表代码：SDCouponActivityInfo<br>
 * 表主键：Id<br>
 */
public class SDCouponActivityInfoSchema extends Schema {
	/**
	 *
	 */
	private static final long serialVersionUID = -307633171906532899L;

	private String Id;

	private String activitysn;

	private String title;

	private String description;

	private String couponnum;

	private String batch;

	private String type;

	private String riskcode;

	private String insurancecompany;

	private String payamount;

	private Date starttime;

	private Date endtime;

	private String accumulation;

	private String status;

	private String autocreate;

	private String channelsn;

	private String prop1;

	private String prop2;

	private String prop3; //折扣，自定义 提醒时间

	private String prop4;

	private String createuser;

	private Date createtime;

	private String modifyuser;

	private Date modifytime;

	private String product;

	private String GroupbuyWhether;

	private String GroupbuyNum;

	private String memberChannel;

	private String memberRule;

	private String ruleDescription;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("activitysn", DataColumn.STRING, 1, 32 , 0 , true , false),
		new SchemaColumn("title", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("description", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("couponnum", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("batch", DataColumn.STRING, 5, 32 , 0 , false , false),
		new SchemaColumn("type", DataColumn.STRING, 6, 5 , 0 , false , false),
		new SchemaColumn("riskcode", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("insurancecompany", DataColumn.STRING, 8, 255 , 0 , false , false),
		new SchemaColumn("payamount", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("starttime", DataColumn.DATETIME, 10, 0 , 0 , true , false),
		new SchemaColumn("endtime", DataColumn.DATETIME, 11, 0 , 0 , true , false),
		new SchemaColumn("accumulation", DataColumn.STRING, 12, 5 , 0 , false , false),
		new SchemaColumn("status", DataColumn.STRING, 13, 10 , 0 , false , false),
		new SchemaColumn("autocreate", DataColumn.STRING, 14, 5 , 0 , false , false),
		new SchemaColumn("channelsn", DataColumn.STRING, 15, 255 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 17, 255 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 18, 255 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 19, 255 , 0 , false , false),
		new SchemaColumn("createuser", DataColumn.STRING, 20, 50 , 0 , true , false),
		new SchemaColumn("createtime", DataColumn.DATETIME, 21, 0 , 0 , true , false),
		new SchemaColumn("modifyuser", DataColumn.STRING, 22, 50 , 0 , false , false),
		new SchemaColumn("modifytime", DataColumn.DATETIME, 23, 0 , 0 , false , false),
		new SchemaColumn("product", DataColumn.STRING, 24, 10000 , 0 , false , false),
		new SchemaColumn("GroupbuyWhether", DataColumn.STRING, 25, 10 , 0 , false , false),
		new SchemaColumn("GroupbuyNum", DataColumn.STRING, 26, 10 , 0 , false , false),
		new SchemaColumn("memberChannel", DataColumn.STRING, 27, 10 , 0 , false , false),
		new SchemaColumn("memberRule", DataColumn.STRING, 28, 10 , 0 , false , false),
		new SchemaColumn("ruleDescription", DataColumn.STRING, 29, 500 , 0 , false , false)
	};

	public static final String _TableCode = "SDCouponActivityInfo";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into SDCouponActivityInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update SDCouponActivityInfo set Id=?,activitysn=?,title=?,description=?,couponnum=?,batch=?,type=?,riskcode=?,insurancecompany=?,payamount=?,starttime=?,endtime=?,accumulation=?,status=?,autocreate=?,channelsn=?,prop1=?,prop2=?,prop3=?,prop4=?,createuser=?,createtime=?,modifyuser=?,modifytime=?,product=?,GroupbuyWhether=?,GroupbuyNum=?,memberChannel=?,memberRule=?,ruleDescription=? where Id=?";

	protected static final String _DeleteSQL = "delete from SDCouponActivityInfo  where Id=?";

	protected static final String _FillAllSQL = "select * from SDCouponActivityInfo  where Id=?";

	public SDCouponActivityInfoSchema(){
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
		return new SDCouponActivityInfoSchema();
	}

	protected SchemaSet newSet(){
		return new SDCouponActivityInfoSet();
	}

	public SDCouponActivityInfoSet query() {
		return query(null, -1, -1);
	}

	public SDCouponActivityInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public SDCouponActivityInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public SDCouponActivityInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (SDCouponActivityInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){activitysn = (String)v;return;}
		if (i == 2){title = (String)v;return;}
		if (i == 3){description = (String)v;return;}
		if (i == 4){couponnum = (String)v;return;}
		if (i == 5){batch = (String)v;return;}
		if (i == 6){type = (String)v;return;}
		if (i == 7){riskcode = (String)v;return;}
		if (i == 8){insurancecompany = (String)v;return;}
		if (i == 9){payamount = (String)v;return;}
		if (i == 10){starttime = (Date)v;return;}
		if (i == 11){endtime = (Date)v;return;}
		if (i == 12){accumulation = (String)v;return;}
		if (i == 13){status = (String)v;return;}
		if (i == 14){autocreate = (String)v;return;}
		if (i == 15){channelsn = (String)v;return;}
		if (i == 16){prop1 = (String)v;return;}
		if (i == 17){prop2 = (String)v;return;}
		if (i == 18){prop3 = (String)v;return;}
		if (i == 19){prop4 = (String)v;return;}
		if (i == 20){createuser = (String)v;return;}
		if (i == 21){createtime = (Date)v;return;}
		if (i == 22){modifyuser = (String)v;return;}
		if (i == 23){modifytime = (Date)v;return;}
		if (i == 24){product = (String)v;return;}
		if (i == 25){GroupbuyWhether = (String)v;return;}
		if (i == 26){GroupbuyNum = (String)v;return;}
		if (i == 27){memberChannel = (String)v;return;}
		if (i == 28){memberRule = (String)v;return;}
		if (i == 29){ruleDescription = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return activitysn;}
		if (i == 2){return title;}
		if (i == 3){return description;}
		if (i == 4){return couponnum;}
		if (i == 5){return batch;}
		if (i == 6){return type;}
		if (i == 7){return riskcode;}
		if (i == 8){return insurancecompany;}
		if (i == 9){return payamount;}
		if (i == 10){return starttime;}
		if (i == 11){return endtime;}
		if (i == 12){return accumulation;}
		if (i == 13){return status;}
		if (i == 14){return autocreate;}
		if (i == 15){return channelsn;}
		if (i == 16){return prop1;}
		if (i == 17){return prop2;}
		if (i == 18){return prop3;}
		if (i == 19){return prop4;}
		if (i == 20){return createuser;}
		if (i == 21){return createtime;}
		if (i == 22){return modifyuser;}
		if (i == 23){return modifytime;}
		if (i == 24){return product;}
		if (i == 25){return GroupbuyWhether;}
		if (i == 26){return GroupbuyNum;}
		if (i == 27){return memberChannel;}
		if (i == 28){return memberRule;}
		if (i == 29){return ruleDescription;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段activitysn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getactivitysn() {
		return activitysn;
	}

	/**
	* 设置字段activitysn的值，该字段的<br>
	* 字段名称 :活动编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setactivitysn(String activitysn) {
		this.activitysn = activitysn;
    }

	/**
	* 获取字段title的值，该字段的<br>
	* 字段名称 :活动名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String gettitle() {
		return title;
	}

	/**
	* 设置字段title的值，该字段的<br>
	* 字段名称 :活动名称<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void settitle(String title) {
		this.title = title;
    }

	/**
	* 获取字段description的值，该字段的<br>
	* 字段名称 :活动描述<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getdescription() {
		return description;
	}

	/**
	* 设置字段description的值，该字段的<br>
	* 字段名称 :活动描述<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setdescription(String description) {
		this.description = description;
    }

	/**
	* 获取字段couponnum的值，该字段的<br>
	* 字段名称 :优惠券数量<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getcouponnum() {
		return couponnum;
	}

	/**
	* 设置字段couponnum的值，该字段的<br>
	* 字段名称 :优惠券数量<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcouponnum(String couponnum) {
		this.couponnum = couponnum;
    }

	/**
	* 获取字段batch的值，该字段的<br>
	* 字段名称 :优惠券批次<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbatch() {
		return batch;
	}

	/**
	* 设置字段batch的值，该字段的<br>
	* 字段名称 :优惠券批次<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbatch(String batch) {
		this.batch = batch;
    }

	/**
	* 获取字段type的值，该字段的<br>
	* 字段名称 :活动类型<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String gettype() {
		return type;
	}

	/**
	* 设置字段type的值，该字段的<br>
	* 字段名称 :活动类型<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settype(String type) {
		this.type = type;
    }

	/**
	* 获取字段riskcode的值，该字段的<br>
	* 字段名称 :活动险种<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getriskcode() {
		return riskcode;
	}

	/**
	* 设置字段riskcode的值，该字段的<br>
	* 字段名称 :活动险种<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setriskcode(String riskcode) {
		this.riskcode = riskcode;
    }

	/**
	* 获取字段insurancecompany的值，该字段的<br>
	* 字段名称 :活动公司<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsurancecompany() {
		return insurancecompany;
	}

	/**
	* 设置字段insurancecompany的值，该字段的<br>
	* 字段名称 :活动公司<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsurancecompany(String insurancecompany) {
		this.insurancecompany = insurancecompany;
    }

	/**
	* 获取字段payamount的值，该字段的<br>
	* 字段名称 :消费金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpayamount() {
		return payamount;
	}

	/**
	* 设置字段payamount的值，该字段的<br>
	* 字段名称 :消费金额<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpayamount(String payamount) {
		this.payamount = payamount;
    }

	/**
	* 获取字段starttime的值，该字段的<br>
	* 字段名称 :起始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getstarttime() {
		return starttime;
	}

	/**
	* 设置字段starttime的值，该字段的<br>
	* 字段名称 :起始时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setstarttime(Date starttime) {
		this.starttime = starttime;
    }

	/**
	* 获取字段endtime的值，该字段的<br>
	* 字段名称 :终止时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getendtime() {
		return endtime;
	}

	/**
	* 设置字段endtime的值，该字段的<br>
	* 字段名称 :终止时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setendtime(Date endtime) {
		this.endtime = endtime;
    }

	/**
	* 获取字段accumulation的值，该字段的<br>
	* 字段名称 :是否累计<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getaccumulation() {
		return accumulation;
	}

	/**
	* 设置字段accumulation的值，该字段的<br>
	* 字段名称 :是否累计<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setaccumulation(String accumulation) {
		this.accumulation = accumulation;
    }

	/**
	* 获取字段status的值，该字段的<br>
	* 字段名称 :活动状态<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getstatus() {
		return status;
	}

	/**
	* 设置字段status的值，该字段的<br>
	* 字段名称 :活动状态<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstatus(String status) {
		this.status = status;
    }

	/**
	* 获取字段autocreate的值，该字段的<br>
	* 字段名称 :自动生成<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getautocreate() {
		return autocreate;
	}

	/**
	* 设置字段autocreate的值，该字段的<br>
	* 字段名称 :自动生成<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setautocreate(String autocreate) {
		this.autocreate = autocreate;
    }

	/**
	* 获取字段channelsn的值，该字段的<br>
	* 字段名称 :渠道编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchannelsn() {
		return channelsn;
	}

	/**
	* 设置字段channelsn的值，该字段的<br>
	* 字段名称 :渠道编码<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchannelsn(String channelsn) {
		this.channelsn = channelsn;
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :备用字段1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :备用字段2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :备用字段3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getprop4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :备用字段4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setprop4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* 获取字段createuser的值，该字段的<br>
	* 字段名称 :申请人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getcreateuser() {
		return createuser;
	}

	/**
	* 设置字段createuser的值，该字段的<br>
	* 字段名称 :申请人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setcreateuser(String createuser) {
		this.createuser = createuser;
    }

	/**
	* 获取字段createtime的值，该字段的<br>
	* 字段名称 :申请时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getcreatetime() {
		return createtime;
	}

	/**
	* 设置字段createtime的值，该字段的<br>
	* 字段名称 :申请时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setcreatetime(Date createtime) {
		this.createtime = createtime;
    }

	/**
	* 获取字段modifyuser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getmodifyuser() {
		return modifyuser;
	}

	/**
	* 设置字段modifyuser的值，该字段的<br>
	* 字段名称 :修改人<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
    }

	/**
	* 获取字段modifytime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getmodifytime() {
		return modifytime;
	}

	/**
	* 设置字段modifytime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setmodifytime(Date modifytime) {
		this.modifytime = modifytime;
    }
	/**
	* 获取字段product的值，该字段的<br>
	* 字段名称 :产品<br>
	* 数据类型 :varchar(10000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getproduct() {
		return product;
	}

	/**
	* 设置字段product的值，该字段的<br>
	* 字段名称 :产品<br>
	* 数据类型 :varchar(10000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setproduct(String product) {
		this.product = product;
    }
	/**
	* 获取字段GroupbuyWhether的值，该字段的<br>
	* 字段名称 :是否参加团购<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getGroupbuyWhether() {
		return GroupbuyWhether;
	}
	/**
	* 设置字段GroupbuyWhether的值，该字段的<br>
	* 字段名称 :是否参加团购<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setGroupbuyWhether(String groupbuyWhether) {
		GroupbuyWhether = groupbuyWhether;
	}
	/**
	* 获取字段GroupbuyNum的值，该字段的<br>
	* 字段名称 :团购单数<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getGroupbuyNum() {
		return GroupbuyNum;
	}
	/**
	* 设置字段GroupbuyNum的值，该字段的<br>
	* 字段名称 :是否参加团购<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setGroupbuyNum(String groupbuyNum) {
		GroupbuyNum = groupbuyNum;
	}

	/**
	* 获取字段memberChannel的值，该字段的<br>
	* 字段名称 :会员频道区分<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemberChannel() {
		return memberChannel;
	}

	/**
	* 设置字段memberChannel的值，该字段的<br>
	* 字段名称 :会员频道区分<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemberChannel(String memberChannel) {
		this.memberChannel = memberChannel;
	}

	/**
	* 获取字段memberRule的值，该字段的<br>
	* 字段名称 :会员等级<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemberRule() {
		return memberRule;
	}

	/**
	* 设置字段memberRule的值，该字段的<br>
	* 字段名称 :会员等级<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemberRule(String memberRule) {
		this.memberRule = memberRule;
	}

	/**
	* 获取字段ruleDescription的值，该字段的<br>
	* 字段名称 :活动规则描述<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRuleDescription() {
		return ruleDescription;
	}

	/**
	* 获取字段ruleDescription的值，该字段的<br>
	* 字段名称 :活动规则描述<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
}