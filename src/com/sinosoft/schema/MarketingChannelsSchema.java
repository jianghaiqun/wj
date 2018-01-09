package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：营销推广渠道表<br>
 * 表代码：MarketingChannels<br>
 * 表主键：Serial<br>
 */
public class MarketingChannelsSchema extends Schema {
	private String Serial;

	private String ConfigSerial;

	private String ChannelCode;

	private String SendAddress;

	private String CompanySerial;

	private String CompanyName;

	private String Remark;

	private String AdvSerial;

	private String TerminalCode;

	private String ProductMajorCategory;

	private String ProductCategory;

	private String ChannelTypeCode;

	private String AdvTypeCode;

	private String ShowFormCode;

	private String ChannelNameCode;

	private String TerminalName;

	private String ProductMajorCategoryName;

	private String ProductCategoryName;

	private String ChannelTypeName;

	private String AdvTypeName;

	private String ShowFormName;

	private String ChannelName;

	private String Insurancecompany;

	private String ChannelCodeHiden;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Serial", DataColumn.STRING, 0, 32 , 0 , true , true),
		new SchemaColumn("ConfigSerial", DataColumn.STRING, 1, 50 , 0 , true , false),
		new SchemaColumn("ChannelCode", DataColumn.STRING, 2, 20 , 0 , true , false),
		new SchemaColumn("SendAddress", DataColumn.STRING, 3, 300 , 0 , false , false),
		new SchemaColumn("CompanySerial", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("CompanyName", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("Remark", DataColumn.STRING, 6, 2000 , 0 , false , false),
		new SchemaColumn("AdvSerial", DataColumn.STRING, 7, 30 , 0 , false , false),
		new SchemaColumn("TerminalCode", DataColumn.STRING, 8, 30 , 0 , false , false),
		new SchemaColumn("ProductMajorCategory", DataColumn.STRING, 9, 30 , 0 , false , false),
		new SchemaColumn("ProductCategory", DataColumn.STRING, 10, 30 , 0 , false , false),
		new SchemaColumn("ChannelTypeCode", DataColumn.STRING, 11, 30 , 0 , false , false),
		new SchemaColumn("AdvTypeCode", DataColumn.STRING, 12, 30 , 0 , false , false),
		new SchemaColumn("ShowFormCode", DataColumn.STRING, 13, 30 , 0 , false , false),
		new SchemaColumn("ChannelNameCode", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("TerminalName", DataColumn.STRING, 15, 30 , 0 , false , false),
		new SchemaColumn("ProductMajorCategoryName", DataColumn.STRING, 16, 30 , 0 , false , false),
		new SchemaColumn("ProductCategoryName", DataColumn.STRING, 17, 30 , 0 , false , false),
		new SchemaColumn("ChannelTypeName", DataColumn.STRING, 18, 30 , 0 , false , false),
		new SchemaColumn("AdvTypeName", DataColumn.STRING, 19, 30 , 0 , false , false),
		new SchemaColumn("ShowFormName", DataColumn.STRING, 20, 30 , 0 , false , false),
		new SchemaColumn("ChannelName", DataColumn.STRING, 21, 30 , 0 , false , false),
		new SchemaColumn("Insurancecompany", DataColumn.STRING, 22, 30 , 0 , false , false),
		new SchemaColumn("ChannelCodeHiden", DataColumn.STRING, 23, 20 , 0 , false , false)
	};

	public static final String _TableCode = "MarketingChannels";

	public static final String _NameSpace = "com.sinosoft.schema";

	protected static final String _InsertAllSQL = "insert into MarketingChannels values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update MarketingChannels set Serial=?,ConfigSerial=?,ChannelCode=?,SendAddress=?,CompanySerial=?,CompanyName=?,Remark=?,AdvSerial=?,TerminalCode=?,ProductMajorCategory=?,ProductCategory=?,ChannelTypeCode=?,AdvTypeCode=?,ShowFormCode=?,ChannelNameCode=?,TerminalName=?,ProductMajorCategoryName=?,ProductCategoryName=?,ChannelTypeName=?,AdvTypeName=?,ShowFormName=?,ChannelName=?,Insurancecompany=?,ChannelCodeHiden=? where Serial=?";

	protected static final String _DeleteSQL = "delete from MarketingChannels  where Serial=?";

	protected static final String _FillAllSQL = "select * from MarketingChannels  where Serial=?";

	public MarketingChannelsSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[24];
	}

	protected Schema newInstance(){
		return new MarketingChannelsSchema();
	}

	protected SchemaSet newSet(){
		return new MarketingChannelsSet();
	}

	public MarketingChannelsSet query() {
		return query(null, -1, -1);
	}

	public MarketingChannelsSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public MarketingChannelsSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public MarketingChannelsSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (MarketingChannelsSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Serial = (String)v;return;}
		if (i == 1){ConfigSerial = (String)v;return;}
		if (i == 2){ChannelCode = (String)v;return;}
		if (i == 3){SendAddress = (String)v;return;}
		if (i == 4){CompanySerial = (String)v;return;}
		if (i == 5){CompanyName = (String)v;return;}
		if (i == 6){Remark = (String)v;return;}
		if (i == 7){AdvSerial = (String)v;return;}
		if (i == 8){TerminalCode = (String)v;return;}
		if (i == 9){ProductMajorCategory = (String)v;return;}
		if (i == 10){ProductCategory = (String)v;return;}
		if (i == 11){ChannelTypeCode = (String)v;return;}
		if (i == 12){AdvTypeCode = (String)v;return;}
		if (i == 13){ShowFormCode = (String)v;return;}
		if (i == 14){ChannelNameCode = (String)v;return;}
		if (i == 15){TerminalName = (String)v;return;}
		if (i == 16){ProductMajorCategoryName = (String)v;return;}
		if (i == 17){ProductCategoryName = (String)v;return;}
		if (i == 18){ChannelTypeName = (String)v;return;}
		if (i == 19){AdvTypeName = (String)v;return;}
		if (i == 20){ShowFormName = (String)v;return;}
		if (i == 21){ChannelName = (String)v;return;}
		if (i == 22){Insurancecompany = (String)v;return;}
		if (i == 23){ChannelCodeHiden = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Serial;}
		if (i == 1){return ConfigSerial;}
		if (i == 2){return ChannelCode;}
		if (i == 3){return SendAddress;}
		if (i == 4){return CompanySerial;}
		if (i == 5){return CompanyName;}
		if (i == 6){return Remark;}
		if (i == 7){return AdvSerial;}
		if (i == 8){return TerminalCode;}
		if (i == 9){return ProductMajorCategory;}
		if (i == 10){return ProductCategory;}
		if (i == 11){return ChannelTypeCode;}
		if (i == 12){return AdvTypeCode;}
		if (i == 13){return ShowFormCode;}
		if (i == 14){return ChannelNameCode;}
		if (i == 15){return TerminalName;}
		if (i == 16){return ProductMajorCategoryName;}
		if (i == 17){return ProductCategoryName;}
		if (i == 18){return ChannelTypeName;}
		if (i == 19){return AdvTypeName;}
		if (i == 20){return ShowFormName;}
		if (i == 21){return ChannelName;}
		if (i == 22){return Insurancecompany;}
		if (i == 23){return ChannelCodeHiden;}
		return null;
	}

	/**
	* 获取字段Serial的值，该字段的<br>
	* 字段名称 :序列号<br>
	* 数据类型 :VARCHAR2(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getSerial() {
		return Serial;
	}

	/**
	* 设置字段Serial的值，该字段的<br>
	* 字段名称 :序列号<br>
	* 数据类型 :VARCHAR2(32)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setSerial(String serial) {
		this.Serial = serial;
    }

	/**
	* 获取字段ConfigSerial的值，该字段的<br>
	* 字段名称 :营销推广配置表序列号<br>
	* 数据类型 :VARCHAR2(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getConfigSerial() {
		return ConfigSerial;
	}

	/**
	* 设置字段ConfigSerial的值，该字段的<br>
	* 字段名称 :营销推广配置表序列号<br>
	* 数据类型 :VARCHAR2(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setConfigSerial(String configSerial) {
		this.ConfigSerial = configSerial;
    }

	/**
	* 获取字段ChannelCode的值，该字段的<br>
	* 字段名称 :渠道编码<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getChannelCode() {
		return ChannelCode;
	}

	/**
	* 设置字段ChannelCode的值，该字段的<br>
	* 字段名称 :渠道编码<br>
	* 数据类型 :VARCHAR2(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setChannelCode(String channelCode) {
		this.ChannelCode = channelCode;
    }

	/**
	* 获取字段SendAddress的值，该字段的<br>
	* 字段名称 :投放地址<br>
	* 数据类型 :VARCHAR(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendAddress() {
		return SendAddress;
	}

	/**
	* 设置字段SendAddress的值，该字段的<br>
	* 字段名称 :投放地址<br>
	* 数据类型 :VARCHAR(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendAddress(String sendAddress) {
		this.SendAddress = sendAddress;
    }

	/**
	* 获取字段CompanySerial的值，该字段的<br>
	* 字段名称 :渠道公司编码<br>
	* 数据类型 :VARCHAR2(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCompanySerial() {
		return CompanySerial;
	}

	/**
	* 设置字段CompanySerial的值，该字段的<br>
	* 字段名称 :渠道公司编码<br>
	* 数据类型 :VARCHAR2(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCompanySerial(String companySerial) {
		this.CompanySerial = companySerial;
    }

	/**
	* 获取字段CompanyName的值，该字段的<br>
	* 字段名称 :渠道公司名称<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCompanyName() {
		return CompanyName;
	}

	/**
	* 设置字段CompanyName的值，该字段的<br>
	* 字段名称 :渠道公司名称<br>
	* 数据类型 :VARCHAR(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCompanyName(String companyName) {
		this.CompanyName = companyName;
    }

	/**
	* 获取字段Remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :VARCHAR2(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemark() {
		return Remark;
	}

	/**
	* 设置字段Remark的值，该字段的<br>
	* 字段名称 :备注<br>
	* 数据类型 :VARCHAR2(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemark(String remark) {
		this.Remark = remark;
    }

	/**
	* 获取字段AdvSerial的值，该字段的<br>
	* 字段名称 :广告编号<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdvSerial() {
		return AdvSerial;
	}

	/**
	* 设置字段AdvSerial的值，该字段的<br>
	* 字段名称 :广告编号<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdvSerial(String advSerial) {
		this.AdvSerial = advSerial;
    }

	/**
	* 获取字段TerminalCode的值，该字段的<br>
	* 字段名称 :营销终端代码<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTerminalCode() {
		return TerminalCode;
	}

	/**
	* 设置字段TerminalCode的值，该字段的<br>
	* 字段名称 :营销终端代码<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTerminalCode(String terminalCode) {
		this.TerminalCode = terminalCode;
    }

	/**
	* 获取字段ProductMajorCategory的值，该字段的<br>
	* 字段名称 :产品大类代码<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductMajorCategory() {
		return ProductMajorCategory;
	}

	/**
	* 设置字段ProductMajorCategory的值，该字段的<br>
	* 字段名称 :产品大类代码<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductMajorCategory(String productMajorCategory) {
		this.ProductMajorCategory = productMajorCategory;
    }

	/**
	* 获取字段ProductCategory的值，该字段的<br>
	* 字段名称 :产品细类代码<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductCategory() {
		return ProductCategory;
	}

	/**
	* 设置字段ProductCategory的值，该字段的<br>
	* 字段名称 :产品细类代码<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductCategory(String productCategory) {
		this.ProductCategory = productCategory;
    }

	/**
	* 获取字段ChannelTypeCode的值，该字段的<br>
	* 字段名称 :渠道类型代码<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChannelTypeCode() {
		return ChannelTypeCode;
	}

	/**
	* 设置字段ChannelTypeCode的值，该字段的<br>
	* 字段名称 :渠道类型代码<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChannelTypeCode(String channelTypeCode) {
		this.ChannelTypeCode = channelTypeCode;
    }

	/**
	* 获取字段AdvTypeCode的值，该字段的<br>
	* 字段名称 :广告类型代码<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdvTypeCode() {
		return AdvTypeCode;
	}

	/**
	* 设置字段AdvTypeCode的值，该字段的<br>
	* 字段名称 :广告类型代码<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdvTypeCode(String advTypeCode) {
		this.AdvTypeCode = advTypeCode;
    }

	/**
	* 获取字段ShowFormCode的值，该字段的<br>
	* 字段名称 :展现形式代码<br>
	* 数据类型 :VARCHAR2(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShowFormCode() {
		return ShowFormCode;
	}

	/**
	* 设置字段ShowFormCode的值，该字段的<br>
	* 字段名称 :展现形式代码<br>
	* 数据类型 :VARCHAR2(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShowFormCode(String showFormCode) {
		this.ShowFormCode = showFormCode;
    }

	/**
	* 获取字段ChannelNameCode的值，该字段的<br>
	* 字段名称 :渠道名称代码<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChannelNameCode() {
		return ChannelNameCode;
	}

	/**
	* 设置字段ChannelNameCode的值，该字段的<br>
	* 字段名称 :渠道名称代码<br>
	* 数据类型 :VARCHAR(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChannelNameCode(String channelNameCode) {
		this.ChannelNameCode = channelNameCode;
    }

	/**
	* 获取字段TerminalName的值，该字段的<br>
	* 字段名称 :营销终端名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTerminalName() {
		return TerminalName;
	}

	/**
	* 设置字段TerminalName的值，该字段的<br>
	* 字段名称 :营销终端名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTerminalName(String terminalName) {
		this.TerminalName = terminalName;
    }

	/**
	* 获取字段ProductMajorCategoryName的值，该字段的<br>
	* 字段名称 :产品大类名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductMajorCategoryName() {
		return ProductMajorCategoryName;
	}

	/**
	* 设置字段ProductMajorCategoryName的值，该字段的<br>
	* 字段名称 :产品大类名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductMajorCategoryName(String productMajorCategoryName) {
		this.ProductMajorCategoryName = productMajorCategoryName;
    }

	/**
	* 获取字段ProductCategoryName的值，该字段的<br>
	* 字段名称 :产品细类名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProductCategoryName() {
		return ProductCategoryName;
	}

	/**
	* 设置字段ProductCategoryName的值，该字段的<br>
	* 字段名称 :产品细类名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProductCategoryName(String productCategoryName) {
		this.ProductCategoryName = productCategoryName;
    }

	/**
	* 获取字段ChannelTypeName的值，该字段的<br>
	* 字段名称 :渠道类型名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChannelTypeName() {
		return ChannelTypeName;
	}

	/**
	* 设置字段ChannelTypeName的值，该字段的<br>
	* 字段名称 :渠道类型名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChannelTypeName(String channelTypeName) {
		this.ChannelTypeName = channelTypeName;
    }

	/**
	* 获取字段AdvTypeName的值，该字段的<br>
	* 字段名称 :广告类型名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAdvTypeName() {
		return AdvTypeName;
	}

	/**
	* 设置字段AdvTypeName的值，该字段的<br>
	* 字段名称 :广告类型名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAdvTypeName(String advTypeName) {
		this.AdvTypeName = advTypeName;
    }

	/**
	* 获取字段ShowFormName的值，该字段的<br>
	* 字段名称 :展现形式名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShowFormName() {
		return ShowFormName;
	}

	/**
	* 设置字段ShowFormName的值，该字段的<br>
	* 字段名称 :展现形式名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShowFormName(String showFormName) {
		this.ShowFormName = showFormName;
    }

	/**
	* 获取字段ChannelName的值，该字段的<br>
	* 字段名称 :渠道名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChannelName() {
		return ChannelName;
	}

	/**
	* 设置字段ChannelName的值，该字段的<br>
	* 字段名称 :渠道名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChannelName(String channelName) {
		this.ChannelName = channelName;
    }

	/**
	* 获取字段Insurancecompany的值，该字段的<br>
	* 字段名称 :保险公司名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInsurancecompany() {
		return Insurancecompany;
	}

	/**
	* 设置字段Insurancecompany的值，该字段的<br>
	* 字段名称 :保险公司名称<br>
	* 数据类型 :VARCHAR(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInsurancecompany(String insurancecompany) {
		this.Insurancecompany = insurancecompany;
    }

	/**
	* 获取字段ChannelCodeHiden的值，该字段的<br>
	* 字段名称 :渠道加密编码<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getChannelCodeHiden() {
		return ChannelCodeHiden;
	}

	/**
	* 设置字段ChannelCodeHiden的值，该字段的<br>
	* 字段名称 :渠道加密编码<br>
	* 数据类型 :VARCHAR(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setChannelCodeHiden(String channelCodeHiden) {
		this.ChannelCodeHiden = channelCodeHiden;
    }

}