package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

import java.util.Date;

import java.math.BigDecimal;

/**
 * 表名称：财务结算明细<br>
 * 表代码：financialsettlementdetails<br>
 * 表主键：policyno, appstatus, premium1st, premium2nd, fee1st, fee2nd, insurancecompanyname, invoicedate<br>
 */
public class financialsettlementdetailsSchema extends Schema {
	private String batchnumber;

	private String policyno;

	private String insurancetype;

	private String insurancesubtype;

	private String channel;

	private String subchannel;

	private String channeltype;

	private Date insuredate;

	private Date svalidate;

	private Date evalidate;

	private String appstatus;

	private BigDecimal premium1st;

	private BigDecimal premium2nd;

	private BigDecimal feeratio1st;

	private BigDecimal feeratio2nd;

	private BigDecimal fee1st;

	private BigDecimal fee2nd;

	private BigDecimal coupondiscount;

	private BigDecimal activitydiscount;

	private BigDecimal pointdiscount;

	private BigDecimal totaldiscount;

	private String premiumbatchnumber;

	private String feebatchnumber;

	private String insurancecompanyname;

	private String insurancecompanytype;

	private Date invoicedate;

	private String branchcode;

	private Integer state;

	private Date createddatetime;

	private Date modifieddatetime;

	private String createduser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("batchnumber", DataColumn.STRING, 0, 32 , 0 , true , false),
		new SchemaColumn("policyno", DataColumn.STRING, 1, 32 , 0 , true , true),
		new SchemaColumn("insurancetype", DataColumn.STRING, 2, 20 , 0 , true , false),
		new SchemaColumn("insurancesubtype", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("channel", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("subchannel", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("channeltype", DataColumn.STRING, 6, 10 , 0 , true , false),
		new SchemaColumn("insuredate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("svalidate", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("evalidate", DataColumn.DATETIME, 9, 0 , 0 , false , false),
		new SchemaColumn("appstatus", DataColumn.STRING, 10, 20 , 0 , true , true),
		new SchemaColumn("premium1st", DataColumn.BIGDECIMAL, 11, 12 , 0 , true , true),
		new SchemaColumn("premium2nd", DataColumn.BIGDECIMAL, 12, 12 , 0 , true , true),
		new SchemaColumn("feeratio1st", DataColumn.BIGDECIMAL, 13, 8 , 0 , false , false),
		new SchemaColumn("feeratio2nd", DataColumn.BIGDECIMAL, 14, 8 , 0 , false , false),
		new SchemaColumn("fee1st", DataColumn.BIGDECIMAL, 15, 12 , 0 , true , true),
		new SchemaColumn("fee2nd", DataColumn.BIGDECIMAL, 16, 12 , 0 , true , true),
		new SchemaColumn("coupondiscount", DataColumn.BIGDECIMAL, 17, 12 , 0 , false , false),
		new SchemaColumn("activitydiscount", DataColumn.BIGDECIMAL, 18, 12 , 0 , false , false),
		new SchemaColumn("pointdiscount", DataColumn.BIGDECIMAL, 19, 12 , 0 , false , false),
		new SchemaColumn("totaldiscount", DataColumn.BIGDECIMAL, 20, 12 , 0 , false , false),
		new SchemaColumn("premiumbatchnumber", DataColumn.STRING, 21, 20 , 0 , false , false),
		new SchemaColumn("feebatchnumber", DataColumn.STRING, 22, 20 , 0 , false , false),
		new SchemaColumn("insurancecompanyname", DataColumn.STRING, 23, 32 , 0 , true , true),
		new SchemaColumn("insurancecompanytype", DataColumn.STRING, 24, 20 , 0 , true , false),
		new SchemaColumn("invoicedate", DataColumn.DATETIME, 25, 0 , 0 , true , true),
		new SchemaColumn("branchcode", DataColumn.STRING, 26, 32 , 0 , false , false),
		new SchemaColumn("state", DataColumn.INTEGER, 27, 0 , 0 , false , false),
		new SchemaColumn("createddatetime", DataColumn.DATETIME, 28, 0 , 0 , true , false),
		new SchemaColumn("modifieddatetime", DataColumn.DATETIME, 29, 0 , 0 , true , false),
		new SchemaColumn("createduser", DataColumn.STRING, 30, 32 , 0 , true , false)
	};

	public static final String _TableCode = "financialsettlementdetails";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into financialsettlementdetails values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update financialsettlementdetails set batchnumber=?,policyno=?,insurancetype=?,insurancesubtype=?,channel=?,subchannel=?,channeltype=?,insuredate=?,svalidate=?,evalidate=?,appstatus=?,premium1st=?,premium2nd=?,feeratio1st=?,feeratio2nd=?,fee1st=?,fee2nd=?,coupondiscount=?,activitydiscount=?,pointdiscount=?,totaldiscount=?,premiumbatchnumber=?,feebatchnumber=?,insurancecompanyname=?,insurancecompanytype=?,invoicedate=?,branchcode=?,state=?,createddatetime=?,modifieddatetime=?,createduser=? where policyno=? and appstatus=? and premium1st=? and premium2nd=? and fee1st=? and fee2nd=? and insurancecompanyname=? and invoicedate=?";

	protected static final String _DeleteSQL = "delete from financialsettlementdetails  where policyno=? and appstatus=? and premium1st=? and premium2nd=? and fee1st=? and fee2nd=? and insurancecompanyname=? and invoicedate=?";

	protected static final String _FillAllSQL = "select * from financialsettlementdetails  where policyno=? and appstatus=? and premium1st=? and premium2nd=? and fee1st=? and fee2nd=? and insurancecompanyname=? and invoicedate=?";

	public financialsettlementdetailsSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[31];
	}

	protected Schema newInstance(){
		return new financialsettlementdetailsSchema();
	}

	protected SchemaSet newSet(){
		return new financialsettlementdetailsSet();
	}

	public financialsettlementdetailsSet query() {
		return query(null, -1, -1);
	}

	public financialsettlementdetailsSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public financialsettlementdetailsSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public financialsettlementdetailsSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (financialsettlementdetailsSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){batchnumber = (String)v;return;}
		if (i == 1){policyno = (String)v;return;}
		if (i == 2){insurancetype = (String)v;return;}
		if (i == 3){insurancesubtype = (String)v;return;}
		if (i == 4){channel = (String)v;return;}
		if (i == 5){subchannel = (String)v;return;}
		if (i == 6){channeltype = (String)v;return;}
		if (i == 7){insuredate = (Date)v;return;}
		if (i == 8){svalidate = (Date)v;return;}
		if (i == 9){evalidate = (Date)v;return;}
		if (i == 10){appstatus = (String)v;return;}
		if (i == 11){if(v==null){premium1st = null;}else{premium1st =  ((BigDecimal)v) ;}return;}
		if (i == 12){if(v==null){premium2nd = null;}else{premium2nd =  ((BigDecimal)v) ;}return;}
		if (i == 13){if(v==null){feeratio1st = null;}else{feeratio1st =  ((BigDecimal)v) ;}return;}
		if (i == 14){if(v==null){feeratio2nd = null;}else{feeratio2nd =  ((BigDecimal)v) ;}return;}
		if (i == 15){if(v==null){fee1st = null;}else{fee1st =  ((BigDecimal)v) ;}return;}
		if (i == 16){if(v==null){fee2nd = null;}else{fee2nd =  ((BigDecimal)v) ;}return;}
		if (i == 17){if(v==null){coupondiscount = null;}else{coupondiscount =  ((BigDecimal)v) ;}return;}
		if (i == 18){if(v==null){activitydiscount = null;}else{activitydiscount =  ((BigDecimal)v) ;}return;}
		if (i == 19){if(v==null){pointdiscount = null;}else{pointdiscount =  ((BigDecimal)v) ;}return;}
		if (i == 20){if(v==null){totaldiscount = null;}else{totaldiscount =  ((BigDecimal)v) ;}return;}
		if (i == 21){premiumbatchnumber = (String)v;return;}
		if (i == 22){feebatchnumber = (String)v;return;}
		if (i == 23){insurancecompanyname = (String)v;return;}
		if (i == 24){insurancecompanytype = (String)v;return;}
		if (i == 25){invoicedate = (Date)v;return;}
		if (i == 26){branchcode = (String)v;return;}
		if (i == 27){if(v==null){state = null;}else{state = new Integer(v.toString());}return;}
		if (i == 28){createddatetime = (Date)v;return;}
		if (i == 29){modifieddatetime = (Date)v;return;}
		if (i == 30){createduser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return batchnumber;}
		if (i == 1){return policyno;}
		if (i == 2){return insurancetype;}
		if (i == 3){return insurancesubtype;}
		if (i == 4){return channel;}
		if (i == 5){return subchannel;}
		if (i == 6){return channeltype;}
		if (i == 7){return insuredate;}
		if (i == 8){return svalidate;}
		if (i == 9){return evalidate;}
		if (i == 10){return appstatus;}
		if (i == 11){return premium1st;}
		if (i == 12){return premium2nd;}
		if (i == 13){return feeratio1st;}
		if (i == 14){return feeratio2nd;}
		if (i == 15){return fee1st;}
		if (i == 16){return fee2nd;}
		if (i == 17){return coupondiscount;}
		if (i == 18){return activitydiscount;}
		if (i == 19){return pointdiscount;}
		if (i == 20){return totaldiscount;}
		if (i == 21){return premiumbatchnumber;}
		if (i == 22){return feebatchnumber;}
		if (i == 23){return insurancecompanyname;}
		if (i == 24){return insurancecompanytype;}
		if (i == 25){return invoicedate;}
		if (i == 26){return branchcode;}
		if (i == 27){return state;}
		if (i == 28){return createddatetime;}
		if (i == 29){return modifieddatetime;}
		if (i == 30){return createduser;}
		return null;
	}

	/**
	* 获取字段batchnumber的值，该字段的<br>
	* 字段名称 :导入批次号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getbatchnumber() {
		return batchnumber;
	}

	/**
	* 设置字段batchnumber的值，该字段的<br>
	* 字段名称 :导入批次号<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setbatchnumber(String batchnumber) {
		this.batchnumber = batchnumber;
    }

	/**
	* 获取字段policyno的值，该字段的<br>
	* 字段名称 :保单号码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getpolicyno() {
		return policyno;
	}

	/**
	* 设置字段policyno的值，该字段的<br>
	* 字段名称 :保单号码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setpolicyno(String policyno) {
		this.policyno = policyno;
    }

	/**
	* 获取字段insurancetype的值，该字段的<br>
	* 字段名称 :保监险种类别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getinsurancetype() {
		return insurancetype;
	}

	/**
	* 设置字段insurancetype的值，该字段的<br>
	* 字段名称 :保监险种类别<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setinsurancetype(String insurancetype) {
		this.insurancetype = insurancetype;
    }

	/**
	* 获取字段insurancesubtype的值，该字段的<br>
	* 字段名称 :保健险种细分类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getinsurancesubtype() {
		return insurancesubtype;
	}

	/**
	* 设置字段insurancesubtype的值，该字段的<br>
	* 字段名称 :保健险种细分类<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsurancesubtype(String insurancesubtype) {
		this.insurancesubtype = insurancesubtype;
    }

	/**
	* 获取字段channel的值，该字段的<br>
	* 字段名称 :一级渠道<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getchannel() {
		return channel;
	}

	/**
	* 设置字段channel的值，该字段的<br>
	* 字段名称 :一级渠道<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setchannel(String channel) {
		this.channel = channel;
    }

	/**
	* 获取字段subchannel的值，该字段的<br>
	* 字段名称 :二级渠道<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getsubchannel() {
		return subchannel;
	}

	/**
	* 设置字段subchannel的值，该字段的<br>
	* 字段名称 :二级渠道<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsubchannel(String subchannel) {
		this.subchannel = subchannel;
    }

	/**
	* 获取字段channeltype的值，该字段的<br>
	* 字段名称 :渠道归属<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getchanneltype() {
		return channeltype;
	}

	/**
	* 设置字段channeltype的值，该字段的<br>
	* 字段名称 :渠道归属<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setchanneltype(String channeltype) {
		this.channeltype = channeltype;
    }

	/**
	* 获取字段insuredate的值，该字段的<br>
	* 字段名称 :承保日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getinsuredate() {
		return insuredate;
	}

	/**
	* 设置字段insuredate的值，该字段的<br>
	* 字段名称 :承保日期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setinsuredate(Date insuredate) {
		this.insuredate = insuredate;
    }

	/**
	* 获取字段svalidate的值，该字段的<br>
	* 字段名称 :保单生效日期起期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getsvalidate() {
		return svalidate;
	}

	/**
	* 设置字段svalidate的值，该字段的<br>
	* 字段名称 :保单生效日期起期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setsvalidate(Date svalidate) {
		this.svalidate = svalidate;
    }

	/**
	* 获取字段evalidate的值，该字段的<br>
	* 字段名称 :保单生效日期止期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getevalidate() {
		return evalidate;
	}

	/**
	* 设置字段evalidate的值，该字段的<br>
	* 字段名称 :保单生效日期止期<br>
	* 数据类型 :date<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setevalidate(Date evalidate) {
		this.evalidate = evalidate;
    }

	/**
	* 获取字段appstatus的值，该字段的<br>
	* 字段名称 :保单状态<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getappstatus() {
		return appstatus;
	}

	/**
	* 设置字段appstatus的值，该字段的<br>
	* 字段名称 :保单状态<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setappstatus(String appstatus) {
		this.appstatus = appstatus;
    }

	/**
	* 获取字段premium1st的值，该字段的<br>
	* 字段名称 :新单保费<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public BigDecimal getpremium1st() {
		return premium1st;
	}

	/**
	* 设置字段premium1st的值，该字段的<br>
	* 字段名称 :新单保费<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setpremium1st(BigDecimal premium1st) {
		this.premium1st = premium1st;
    }

	/**
	* 获取字段premium2nd的值，该字段的<br>
	* 字段名称 :续期保费<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public BigDecimal getpremium2nd() {
		return premium2nd;
	}

	/**
	* 设置字段premium2nd的值，该字段的<br>
	* 字段名称 :续期保费<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setpremium2nd(BigDecimal premium2nd) {
		this.premium2nd = premium2nd;
    }

	/**
	* 获取字段feeratio1st的值，该字段的<br>
	* 字段名称 :新单手续费比率<br>
	* 数据类型 :decimal(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getfeeratio1st() {
		return feeratio1st;
	}

	/**
	* 设置字段feeratio1st的值，该字段的<br>
	* 字段名称 :新单手续费比率<br>
	* 数据类型 :decimal(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfeeratio1st(BigDecimal feeratio1st) {
		this.feeratio1st = feeratio1st;
    }

	/**
	* 获取字段feeratio2nd的值，该字段的<br>
	* 字段名称 :续期手续费比率<br>
	* 数据类型 :decimal(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getfeeratio2nd() {
		return feeratio2nd;
	}

	/**
	* 设置字段feeratio2nd的值，该字段的<br>
	* 字段名称 :续期手续费比率<br>
	* 数据类型 :decimal(8)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfeeratio2nd(BigDecimal feeratio2nd) {
		this.feeratio2nd = feeratio2nd;
    }

	/**
	* 获取字段fee1st的值，该字段的<br>
	* 字段名称 :新单手续费金额<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public BigDecimal getfee1st() {
		return fee1st;
	}

	/**
	* 设置字段fee1st的值，该字段的<br>
	* 字段名称 :新单手续费金额<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setfee1st(BigDecimal fee1st) {
		this.fee1st = fee1st;
    }

	/**
	* 获取字段fee2nd的值，该字段的<br>
	* 字段名称 :续期手续费金额<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public BigDecimal getfee2nd() {
		return fee2nd;
	}

	/**
	* 设置字段fee2nd的值，该字段的<br>
	* 字段名称 :续期手续费金额<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setfee2nd(BigDecimal fee2nd) {
		this.fee2nd = fee2nd;
    }

	/**
	* 获取字段coupondiscount的值，该字段的<br>
	* 字段名称 :保单优惠券<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getcoupondiscount() {
		return coupondiscount;
	}

	/**
	* 设置字段coupondiscount的值，该字段的<br>
	* 字段名称 :保单优惠券<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setcoupondiscount(BigDecimal coupondiscount) {
		this.coupondiscount = coupondiscount;
    }

	/**
	* 获取字段activitydiscount的值，该字段的<br>
	* 字段名称 :保单活动优惠<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getactivitydiscount() {
		return activitydiscount;
	}

	/**
	* 设置字段activitydiscount的值，该字段的<br>
	* 字段名称 :保单活动优惠<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setactivitydiscount(BigDecimal activitydiscount) {
		this.activitydiscount = activitydiscount;
    }

	/**
	* 获取字段pointdiscount的值，该字段的<br>
	* 字段名称 :保单积分抵值<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal getpointdiscount() {
		return pointdiscount;
	}

	/**
	* 设置字段pointdiscount的值，该字段的<br>
	* 字段名称 :保单积分抵值<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpointdiscount(BigDecimal pointdiscount) {
		this.pointdiscount = pointdiscount;
    }

	/**
	* 获取字段totaldiscount的值，该字段的<br>
	* 字段名称 :总优惠<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public BigDecimal gettotaldiscount() {
		return totaldiscount;
	}

	/**
	* 设置字段totaldiscount的值，该字段的<br>
	* 字段名称 :总优惠<br>
	* 数据类型 :decimal(12)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void settotaldiscount(BigDecimal totaldiscount) {
		this.totaldiscount = totaldiscount;
    }

	/**
	* 获取字段premiumbatchnumber的值，该字段的<br>
	* 字段名称 :保费批次号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getpremiumbatchnumber() {
		return premiumbatchnumber;
	}

	/**
	* 设置字段premiumbatchnumber的值，该字段的<br>
	* 字段名称 :保费批次号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setpremiumbatchnumber(String premiumbatchnumber) {
		this.premiumbatchnumber = premiumbatchnumber;
    }

	/**
	* 获取字段feebatchnumber的值，该字段的<br>
	* 字段名称 :手续费批次号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getfeebatchnumber() {
		return feebatchnumber;
	}

	/**
	* 设置字段feebatchnumber的值，该字段的<br>
	* 字段名称 :手续费批次号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setfeebatchnumber(String feebatchnumber) {
		this.feebatchnumber = feebatchnumber;
    }

	/**
	* 获取字段insurancecompanyname的值，该字段的<br>
	* 字段名称 :保险公司名称<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getinsurancecompanyname() {
		return insurancecompanyname;
	}

	/**
	* 设置字段insurancecompanyname的值，该字段的<br>
	* 字段名称 :保险公司名称<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setinsurancecompanyname(String insurancecompanyname) {
		this.insurancecompanyname = insurancecompanyname;
    }

	/**
	* 获取字段insurancecompanytype的值，该字段的<br>
	* 字段名称 :保险公司性质<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getinsurancecompanytype() {
		return insurancecompanytype;
	}

	/**
	* 设置字段insurancecompanytype的值，该字段的<br>
	* 字段名称 :保险公司性质<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setinsurancecompanytype(String insurancecompanytype) {
		this.insurancecompanytype = insurancecompanytype;
    }

	/**
	* 获取字段invoicedate的值，该字段的<br>
	* 字段名称 :开票日期<br>
	* 数据类型 :date<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public Date getinvoicedate() {
		return invoicedate;
	}

	/**
	* 设置字段invoicedate的值，该字段的<br>
	* 字段名称 :开票日期<br>
	* 数据类型 :date<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setinvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
    }

	/**
	* 获取字段branchcode的值，该字段的<br>
	* 字段名称 :机构编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getbranchcode() {
		return branchcode;
	}

	/**
	* 设置字段branchcode的值，该字段的<br>
	* 字段名称 :机构编码<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setbranchcode(String branchcode) {
		this.branchcode = branchcode;
    }

	/**
	* 获取字段state的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getstate() {
		if(state==null){return 0;}
		return state.intValue();
	}

	/**
	* 设置字段state的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstate(int state) {
		this.state = new Integer(state);
    }

	/**
	* 设置字段state的值，该字段的<br>
	* 字段名称 :状态<br>
	* 数据类型 :int<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setstate(String state) {
		if (state == null){
			this.state = null;
			return;
		}
		this.state = new Integer(state);
    }

	/**
	* 获取字段createddatetime的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getcreateddatetime() {
		return createddatetime;
	}

	/**
	* 设置字段createddatetime的值，该字段的<br>
	* 字段名称 :创建时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setcreateddatetime(Date createddatetime) {
		this.createddatetime = createddatetime;
    }

	/**
	* 获取字段modifieddatetime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getmodifieddatetime() {
		return modifieddatetime;
	}

	/**
	* 设置字段modifieddatetime的值，该字段的<br>
	* 字段名称 :修改时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setmodifieddatetime(Date modifieddatetime) {
		this.modifieddatetime = modifieddatetime;
    }

	/**
	* 获取字段createduser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getcreateduser() {
		return createduser;
	}

	/**
	* 设置字段createduser的值，该字段的<br>
	* 字段名称 :创建人<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setcreateduser(String createduser) {
		this.createduser = createduser;
    }

}