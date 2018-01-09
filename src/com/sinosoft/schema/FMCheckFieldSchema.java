package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：FMCheckField<br>
 * 表代码：FMCheckField<br>
 * 表主键：RiskVer, FieldName, SerialNo, RiskCode<br>
 */
public class FMCheckFieldSchema extends Schema {
	/**
	 * 
	 */
	private static final long serialVersionUID = -846620705966710589L;

	private String CompanySn;

	private String ProductId;

	private String RiskVer;

	private String FieldName;

	private String SerialNo;

	private String RiskCode;

	private String CalCode;

	private String PageLocation;

	private String Location;

	private String Msg;

	private String MsgFlag;

	private String UpdFlag;

	private String ValiFlag;

	private String ReturnValiFlag;

	private String ValType;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("CompanySn", DataColumn.STRING, 0, 20 , 0 , false , false),
		new SchemaColumn("ProductId", DataColumn.STRING, 1, 20 , 0 , false , false),
		new SchemaColumn("RiskVer", DataColumn.STRING, 2, 8 , 0 , true , true),
		new SchemaColumn("FieldName", DataColumn.STRING, 3, 20 , 0 , true , true),
		new SchemaColumn("SerialNo", DataColumn.STRING, 4, 20 , 0 , true , true),
		new SchemaColumn("RiskCode", DataColumn.STRING, 5, 20 , 0 , true , true),
		new SchemaColumn("CalCode", DataColumn.STRING, 6, 6 , 0 , false , false),
		new SchemaColumn("PageLocation", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Location", DataColumn.STRING, 8, 2 , 0 , false , false),
		new SchemaColumn("Msg", DataColumn.STRING, 9, 500 , 0 , false , false),
		new SchemaColumn("MsgFlag", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("UpdFlag", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("ValiFlag", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("ReturnValiFlag", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("ValType", DataColumn.STRING, 14, 5 , 0 , false , false)
	};

	public static final String _TableCode = "FMCheckField";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into FMCheckField values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update FMCheckField set CompanySn=?,ProductId=?,RiskVer=?,FieldName=?,SerialNo=?,RiskCode=?,CalCode=?,PageLocation=?,Location=?,Msg=?,MsgFlag=?,UpdFlag=?,ValiFlag=?,ReturnValiFlag=?,ValType=? where RiskVer=? and FieldName=? and SerialNo=? and RiskCode=?";

	protected static final String _DeleteSQL = "delete from FMCheckField  where RiskVer=? and FieldName=? and SerialNo=? and RiskCode=?";

	protected static final String _FillAllSQL = "select * from FMCheckField  where RiskVer=? and FieldName=? and SerialNo=? and RiskCode=?";

	public FMCheckFieldSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[15];
	}

	protected Schema newInstance(){
		return new FMCheckFieldSchema();
	}

	protected SchemaSet newSet(){
		return new FMCheckFieldSet();
	}

	public FMCheckFieldSet query() {
		return query(null, -1, -1);
	}

	public FMCheckFieldSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public FMCheckFieldSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public FMCheckFieldSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (FMCheckFieldSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){CompanySn = (String)v;return;}
		if (i == 1){ProductId = (String)v;return;}
		if (i == 2){RiskVer = (String)v;return;}
		if (i == 3){FieldName = (String)v;return;}
		if (i == 4){SerialNo = (String)v;return;}
		if (i == 5){RiskCode = (String)v;return;}
		if (i == 6){CalCode = (String)v;return;}
		if (i == 7){PageLocation = (String)v;return;}
		if (i == 8){Location = (String)v;return;}
		if (i == 9){Msg = (String)v;return;}
		if (i == 10){MsgFlag = (String)v;return;}
		if (i == 11){UpdFlag = (String)v;return;}
		if (i == 12){ValiFlag = (String)v;return;}
		if (i == 13){ReturnValiFlag = (String)v;return;}
		if (i == 14){ValType = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return CompanySn;}
		if (i == 1){return ProductId;}
		if (i == 2){return RiskVer;}
		if (i == 3){return FieldName;}
		if (i == 4){return SerialNo;}
		if (i == 5){return RiskCode;}
		if (i == 6){return CalCode;}
		if (i == 7){return PageLocation;}
		if (i == 8){return Location;}
		if (i == 9){return Msg;}
		if (i == 10){return MsgFlag;}
		if (i == 11){return UpdFlag;}
		if (i == 12){return ValiFlag;}
		if (i == 13){return ReturnValiFlag;}
		if (i == 14){return ValType;}
		return null;
	}

	/**
	* 获取字段CompanySn的值，该字段的<br>
	* 字段名称 :保险公司编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCompanySn() {
		return CompanySn;
	}

	/**
	* 设置字段CompanySn的值，该字段的<br>
	* 字段名称 :保险公司编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCompanySn(String companySn) {
		this.CompanySn = companySn;
    }

	/**
	* 获取字段ProductId的值，该字段的<br>
	* 字段名称 :产品Id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductId() {
		return ProductId;
	}

	/**
	* 设置字段ProductId的值，该字段的<br>
	* 字段名称 :产品Id<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductId(String productId) {
		this.ProductId = productId;
    }

	/**
	* 获取字段RiskVer的值，该字段的<br>
	* 字段名称 :险种版本<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getRiskVer() {
		return RiskVer;
	}

	/**
	* 设置字段RiskVer的值，该字段的<br>
	* 字段名称 :险种版本<br>
	* 数据类型 :varchar(8)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setRiskVer(String riskVer) {
		this.RiskVer = riskVer;
    }

	/**
	* 获取字段FieldName的值，该字段的<br>
	* 字段名称 :控制字段名称<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	该字段为需要校验的字段的英文名称。该字段在一个界面中必须唯一。<br>
	*/
	public String getFieldName() {
		return FieldName;
	}

	/**
	* 设置字段FieldName的值，该字段的<br>
	* 字段名称 :控制字段名称<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	该字段为需要校验的字段的英文名称。该字段在一个界面中必须唯一。<br>
	*/
	public void setFieldName(String fieldName) {
		this.FieldName = fieldName;
    }

	/**
	* 获取字段SerialNo的值，该字段的<br>
	* 字段名称 :序号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getSerialNo() {
		return SerialNo;
	}

	/**
	* 设置字段SerialNo的值，该字段的<br>
	* 字段名称 :序号<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setSerialNo(String serialNo) {
		this.SerialNo = serialNo;
    }

	/**
	* 获取字段RiskCode的值，该字段的<br>
	* 字段名称 :险种编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getRiskCode() {
		return RiskCode;
	}

	/**
	* 设置字段RiskCode的值，该字段的<br>
	* 字段名称 :险种编码<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setRiskCode(String riskCode) {
		this.RiskCode = riskCode;
    }

	/**
	* 获取字段CalCode的值，该字段的<br>
	* 字段名称 :算法<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	保额算保费<br>
	*/
	public String getCalCode() {
		return CalCode;
	}

	/**
	* 设置字段CalCode的值，该字段的<br>
	* 字段名称 :算法<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	保额算保费<br>
	*/
	public void setCalCode(String calCode) {
		this.CalCode = calCode;
    }

	/**
	* 获取字段PageLocation的值，该字段的<br>
	* 字段名称 :页面位置<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	如果该字段为空或null，则表示处理所有页面，否则只是处理本页面的校验。 TBINPUT#TBTYPEGC 团单合同自动复核规则 TBINPUT#TBTYPEIC 个单合同自动复核规则 TBINPUT#TBTYPEG 团单险种自动复核规则 TBINPUT#TBTYPEI 个单险种自动复核规则<br>
	*/
	public String getPageLocation() {
		return PageLocation;
	}

	/**
	* 设置字段PageLocation的值，该字段的<br>
	* 字段名称 :页面位置<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	如果该字段为空或null，则表示处理所有页面，否则只是处理本页面的校验。 TBINPUT#TBTYPEGC 团单合同自动复核规则 TBINPUT#TBTYPEIC 个单合同自动复核规则 TBINPUT#TBTYPEG 团单险种自动复核规则 TBINPUT#TBTYPEI 个单险种自动复核规则<br>
	*/
	public void setPageLocation(String pageLocation) {
		this.PageLocation = pageLocation;
    }

	/**
	* 获取字段Location的值，该字段的<br>
	* 字段名称 :事件位置<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	B--Before Field 、A--After Field<br>
	*/
	public String getLocation() {
		return Location;
	}

	/**
	* 设置字段Location的值，该字段的<br>
	* 字段名称 :事件位置<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	B--Before Field 、A--After Field<br>
	*/
	public void setLocation(String location) {
		this.Location = location;
    }

	/**
	* 获取字段Msg的值，该字段的<br>
	* 字段名称 :提示信息<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMsg() {
		return Msg;
	}

	/**
	* 设置字段Msg的值，该字段的<br>
	* 字段名称 :提示信息<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMsg(String msg) {
		this.Msg = msg;
    }

	/**
	* 获取字段MsgFlag的值，该字段的<br>
	* 字段名称 :提示标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--是,表示需要将提示信息显示出来 N--否，表示不显示提示信息。 1--执行标志，表示需要执行返回的相应函数。<br>
	*/
	public String getMsgFlag() {
		return MsgFlag;
	}

	/**
	* 设置字段MsgFlag的值，该字段的<br>
	* 字段名称 :提示标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--是,表示需要将提示信息显示出来 N--否，表示不显示提示信息。 1--执行标志，表示需要执行返回的相应函数。<br>
	*/
	public void setMsgFlag(String msgFlag) {
		this.MsgFlag = msgFlag;
    }

	/**
	* 获取字段UpdFlag的值，该字段的<br>
	* 字段名称 :修改变量标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--是，表示需要将屏幕上的相关信息进行修改。 N--否<br>
	*/
	public String getUpdFlag() {
		return UpdFlag;
	}

	/**
	* 设置字段UpdFlag的值，该字段的<br>
	* 字段名称 :修改变量标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--是，表示需要将屏幕上的相关信息进行修改。 N--否<br>
	*/
	public void setUpdFlag(String updFlag) {
		this.UpdFlag = updFlag;
    }

	/**
	* 获取字段ValiFlag的值，该字段的<br>
	* 字段名称 :有效标志<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	该字段描述哪些结果对于该控制是有效的，如果有多个有效值，则通过“;”将多个有效值分开。<br>
	*/
	public String getValiFlag() {
		return ValiFlag;
	}

	/**
	* 设置字段ValiFlag的值，该字段的<br>
	* 字段名称 :有效标志<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	该字段描述哪些结果对于该控制是有效的，如果有多个有效值，则通过“;”将多个有效值分开。<br>
	*/
	public void setValiFlag(String valiFlag) {
		this.ValiFlag = valiFlag;
    }

	/**
	* 获取字段ReturnValiFlag的值，该字段的<br>
	* 字段名称 :返回值有效标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--是，表示执行成功，并且需要进一步处理 N--否，表示不成功，不进行其他任何处理。<br>
	*/
	public String getReturnValiFlag() {
		return ReturnValiFlag;
	}

	/**
	* 设置字段ReturnValiFlag的值，该字段的<br>
	* 字段名称 :返回值有效标志<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	Y--是，表示执行成功，并且需要进一步处理 N--否，表示不成功，不进行其他任何处理。<br>
	*/
	public void setReturnValiFlag(String returnValiFlag) {
		this.ReturnValiFlag = returnValiFlag;
    }

	/**
	* 获取字段ValType的值，该字段的<br>
	* 字段名称 :校验类别<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	01：被保人信息校验 其他：非被保人信息校验<br>
	*/
	public String getValType() {
		return ValType;
	}

	/**
	* 设置字段ValType的值，该字段的<br>
	* 字段名称 :校验类别<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	* 备注信息 :<br>
	01：被保人信息校验 其他：非被保人信息校验<br>
	*/
	public void setValType(String valType) {
		this.ValType = valType;
    }

}