package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：受益人表<br>
 * 表代码：SDInformationBnf<br>
 * 表主键：id<br>
 */
public class SDInformationBnfSchema extends Schema {

    private String id;

    private Date createDate;

    private Date modifyDate;

    private String accName;

    private String bankAccNo;

    private String bankCode;

    private String benePer;

    private String beneWay;

    private String bnfBirthday;

    private String bnfIDNo;

    private String bnfIDType;

    private String bnfLot;

    private String bnfName;

    private String bnfNo;

    private String bnfSex;

    private String bnfType;

    private String email;

    private String informationSn;

    private String informationSn2;

    private String mobile;

    private String occupation;

    private String orderSn;

    private String phone;

    private String postalAddress;

    private String recognizeeSn;

    private String relationToInsured;

    private String zipCode;

    private String sdinformationInsured_id;

    private String bnfStartID;

    private String bnfEndID;

    private String bnfIDTypeName;

    private String bnfSexName;

    private String relationToInsuredName;

    private String bnfArea1;

    private String bnfArea2;

    private String bnfArea3;

    private String bnfOccupation1;

    private String bnfOccupation2;

    private String bnfOccupation3;

    public static final SchemaColumn[] _Columns = new SchemaColumn[] {
        new SchemaColumn("id", DataColumn.STRING, 0, 32 , 0 , true, true),
        new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , false, false),
        new SchemaColumn("modifyDate", DataColumn.DATETIME, 2, 0 , 0 , false, false),
        new SchemaColumn("accName", DataColumn.STRING, 3, 50 , 0 , false, false),
        new SchemaColumn("bankAccNo", DataColumn.STRING, 4, 20 , 0 , false, false),
        new SchemaColumn("bankCode", DataColumn.STRING, 5, 20 , 0 , false, false),
        new SchemaColumn("benePer", DataColumn.STRING, 6, 20 , 0 , false, false),
        new SchemaColumn("beneWay", DataColumn.STRING, 7, 20 , 0 , false, false),
        new SchemaColumn("bnfBirthday", DataColumn.STRING, 8, 20 , 0 , false, false),
        new SchemaColumn("bnfIDNo", DataColumn.STRING, 9, 20 , 0 , false, false),
        new SchemaColumn("bnfIDType", DataColumn.STRING, 10, 20 , 0 , false, false),
        new SchemaColumn("bnfLot", DataColumn.STRING, 11, 20 , 0 , false, false),
        new SchemaColumn("bnfName", DataColumn.STRING, 12, 50 , 0 , false, false),
        new SchemaColumn("bnfNo", DataColumn.STRING, 13, 20 , 0 , false, false),
        new SchemaColumn("bnfSex", DataColumn.STRING, 14, 5 , 0 , false, false),
        new SchemaColumn("bnfType", DataColumn.STRING, 15, 20 , 0 , false, false),
        new SchemaColumn("email", DataColumn.STRING, 16, 100 , 0 , false, false),
        new SchemaColumn("informationSn", DataColumn.STRING, 17, 20 , 0 , false, false),
        new SchemaColumn("informationSn2", DataColumn.STRING, 18, 20 , 0 , false, false),
        new SchemaColumn("mobile", DataColumn.STRING, 19, 12 , 0 , false, false),
        new SchemaColumn("occupation", DataColumn.STRING, 20, 100 , 0 , false, false),
        new SchemaColumn("orderSn", DataColumn.STRING, 21, 20 , 0 , false, false),
        new SchemaColumn("phone", DataColumn.STRING, 22, 20 , 0 , false, false),
        new SchemaColumn("postalAddress", DataColumn.STRING, 23, 200 , 0 , false, false),
        new SchemaColumn("recognizeeSn", DataColumn.STRING, 24, 20 , 0 , false, false),
        new SchemaColumn("relationToInsured", DataColumn.STRING, 25, 20 , 0 , false, false),
        new SchemaColumn("zipCode", DataColumn.STRING, 26, 10 , 0 , false, false),
        new SchemaColumn("sdinformationInsured_id", DataColumn.STRING, 27, 32 , 0 , false, false),
        new SchemaColumn("bnfStartID", DataColumn.STRING, 28, 255 , 0 , false, false),
        new SchemaColumn("bnfEndID", DataColumn.STRING, 29, 255 , 0 , false, false),
        new SchemaColumn("bnfIDTypeName", DataColumn.STRING, 30, 255 , 0 , false, false),
        new SchemaColumn("bnfSexName", DataColumn.STRING, 31, 255 , 0 , false, false),
        new SchemaColumn("relationToInsuredName", DataColumn.STRING, 32, 255 , 0 , false, false),
        new SchemaColumn("bnfArea1", DataColumn.STRING, 33, 255 , 0 , false, false),
        new SchemaColumn("bnfArea2", DataColumn.STRING, 34, 255 , 0 , false, false),
        new SchemaColumn("bnfArea3", DataColumn.STRING, 35, 255 , 0 , false, false),
        new SchemaColumn("bnfOccupation1", DataColumn.STRING, 36, 255 , 0 , false, false),
        new SchemaColumn("bnfOccupation2", DataColumn.STRING, 37, 255 , 0 , false, false),
        new SchemaColumn("bnfOccupation3", DataColumn.STRING, 38, 255 , 0 , false, false)
    };

    public static final String _TableCode = "SDInformationBnf";

    public static final String _NameSpace = "com.sinosoft.schema";

    protected static final String _InsertAllSQL = "insert into SDInformationBnf values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    protected static final String _UpdateAllSQL = "update SDInformationBnf set id=?,createDate=?,modifyDate=?,accName=?,bankAccNo=?,bankCode=?,benePer=?,beneWay=?,bnfBirthday=?,bnfIDNo=?,bnfIDType=?,bnfLot=?,bnfName=?,bnfNo=?,bnfSex=?,bnfType=?,email=?,informationSn=?,informationSn2=?,mobile=?,occupation=?,orderSn=?,phone=?,postalAddress=?,recognizeeSn=?,relationToInsured=?,zipCode=?,sdinformationInsured_id=?,bnfStartID=?,bnfEndID=?,bnfIDTypeName=?,bnfSexName=?,relationToInsuredName=?,bnfArea1=?,bnfArea2=?,bnfArea3=?,bnfOccupation1=?,bnfOccupation2=?,bnfOccupation3=? where id=?";

    protected static final String _DeleteSQL = "delete from SDInformationBnf  where id=?";

    protected static final String _FillAllSQL = "select * from SDInformationBnf  where id=?";

    public SDInformationBnfSchema(){
        TableCode = _TableCode;
        NameSpace = _NameSpace;
        Columns = _Columns;
        InsertAllSQL = _InsertAllSQL;
        UpdateAllSQL = _UpdateAllSQL;
        DeleteSQL = _DeleteSQL;
        FillAllSQL = _FillAllSQL;
        HasSetFlag = new boolean[39];
    }

    protected Schema newInstance(){
        return new SDInformationBnfSchema();
    }

    protected SchemaSet newSet(){
        return new SDInformationBnfSet();
    }

    public SDInformationBnfSet query() {
        return query(null, -1, -1);
    }

    public SDInformationBnfSet query(QueryBuilder qb) {
        return query(qb, -1, -1);
    }

    public SDInformationBnfSet query(int pageSize, int pageIndex) {
        return query(null, pageSize, pageIndex);
    }

    public SDInformationBnfSet query(QueryBuilder qb , int pageSize, int pageIndex){
        return (SDInformationBnfSet)querySet(qb , pageSize , pageIndex);
    }

    public void setV(int i, Object v) {
        if (i == 0){id = (String)v;return;}
        if (i == 1){createDate = (Date)v;return;}
        if (i == 2){modifyDate = (Date)v;return;}
        if (i == 3){accName = (String)v;return;}
        if (i == 4){bankAccNo = (String)v;return;}
        if (i == 5){bankCode = (String)v;return;}
        if (i == 6){benePer = (String)v;return;}
        if (i == 7){beneWay = (String)v;return;}
        if (i == 8){bnfBirthday = (String)v;return;}
        if (i == 9){bnfIDNo = (String)v;return;}
        if (i == 10){bnfIDType = (String)v;return;}
        if (i == 11){bnfLot = (String)v;return;}
        if (i == 12){bnfName = (String)v;return;}
        if (i == 13){bnfNo = (String)v;return;}
        if (i == 14){bnfSex = (String)v;return;}
        if (i == 15){bnfType = (String)v;return;}
        if (i == 16){email = (String)v;return;}
        if (i == 17){informationSn = (String)v;return;}
        if (i == 18){informationSn2 = (String)v;return;}
        if (i == 19){mobile = (String)v;return;}
        if (i == 20){occupation = (String)v;return;}
        if (i == 21){orderSn = (String)v;return;}
        if (i == 22){phone = (String)v;return;}
        if (i == 23){postalAddress = (String)v;return;}
        if (i == 24){recognizeeSn = (String)v;return;}
        if (i == 25){relationToInsured = (String)v;return;}
        if (i == 26){zipCode = (String)v;return;}
        if (i == 27){sdinformationInsured_id = (String)v;return;}
        if (i == 28){bnfStartID = (String)v;return;}
        if (i == 29){bnfEndID = (String)v;return;}
        if (i == 30){bnfIDTypeName = (String)v;return;}
        if (i == 31){bnfSexName = (String)v;return;}
        if (i == 32){relationToInsuredName = (String)v;return;}
        if (i == 33){bnfArea1 = (String)v;return;}
        if (i == 34){bnfArea2 = (String)v;return;}
        if (i == 35){bnfArea3 = (String)v;return;}
        if (i == 36){bnfOccupation1 = (String)v;return;}
        if (i == 37){bnfOccupation2 = (String)v;return;}
        if (i == 38){bnfOccupation3 = (String)v;return;}
    }

    public Object getV(int i) {
        if (i == 0){return id;}
        if (i == 1){return createDate;}
        if (i == 2){return modifyDate;}
        if (i == 3){return accName;}
        if (i == 4){return bankAccNo;}
        if (i == 5){return bankCode;}
        if (i == 6){return benePer;}
        if (i == 7){return beneWay;}
        if (i == 8){return bnfBirthday;}
        if (i == 9){return bnfIDNo;}
        if (i == 10){return bnfIDType;}
        if (i == 11){return bnfLot;}
        if (i == 12){return bnfName;}
        if (i == 13){return bnfNo;}
        if (i == 14){return bnfSex;}
        if (i == 15){return bnfType;}
        if (i == 16){return email;}
        if (i == 17){return informationSn;}
        if (i == 18){return informationSn2;}
        if (i == 19){return mobile;}
        if (i == 20){return occupation;}
        if (i == 21){return orderSn;}
        if (i == 22){return phone;}
        if (i == 23){return postalAddress;}
        if (i == 24){return recognizeeSn;}
        if (i == 25){return relationToInsured;}
        if (i == 26){return zipCode;}
        if (i == 27){return sdinformationInsured_id;}
        if (i == 28){return bnfStartID;}
        if (i == 29){return bnfEndID;}
        if (i == 30){return bnfIDTypeName;}
        if (i == 31){return bnfSexName;}
        if (i == 32){return relationToInsuredName;}
        if (i == 33){return bnfArea1;}
        if (i == 34){return bnfArea2;}
        if (i == 35){return bnfArea3;}
        if (i == 36){return bnfOccupation1;}
        if (i == 37){return bnfOccupation2;}
        if (i == 38){return bnfOccupation3;}
        return null;
    }

    /**
    * 获取字段id的值，该字段的<br>
    * 字段名称 :ID<br>
    * 数据类型 :varchar(32)<br>
    * 是否主键 :true<br>
    * 是否必填 :true<br>
    */
    public String getid() {
        return id;
    }

    /**
    * 设置字段id的值，该字段的<br>
    * 字段名称 :ID<br>
    * 数据类型 :varchar(32)<br>
    * 是否主键 :true<br>
    * 是否必填 :true<br>
    */
    public void setid(String id) {
        this.id = id;
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

    /**
    * 获取字段accName的值，该字段的<br>
    * 字段名称 :银行户名<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getaccName() {
        return accName;
    }

    /**
    * 设置字段accName的值，该字段的<br>
    * 字段名称 :银行户名<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setaccName(String accName) {
        this.accName = accName;
    }

    /**
    * 获取字段bankAccNo的值，该字段的<br>
    * 字段名称 :银行帐号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbankAccNo() {
        return bankAccNo;
    }

    /**
    * 设置字段bankAccNo的值，该字段的<br>
    * 字段名称 :银行帐号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

    /**
    * 获取字段bankCode的值，该字段的<br>
    * 字段名称 :银行编码<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbankCode() {
        return bankCode;
    }

    /**
    * 设置字段bankCode的值，该字段的<br>
    * 字段名称 :银行编码<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
    * 获取字段benePer的值，该字段的<br>
    * 字段名称 :受益比例<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbenePer() {
        return benePer;
    }

    /**
    * 设置字段benePer的值，该字段的<br>
    * 字段名称 :受益比例<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbenePer(String benePer) {
        this.benePer = benePer;
    }

    /**
    * 获取字段beneWay的值，该字段的<br>
    * 字段名称 :受益方式<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbeneWay() {
        return beneWay;
    }

    /**
    * 设置字段beneWay的值，该字段的<br>
    * 字段名称 :受益方式<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbeneWay(String beneWay) {
        this.beneWay = beneWay;
    }

    /**
    * 获取字段bnfBirthday的值，该字段的<br>
    * 字段名称 :客户出生日期<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfBirthday() {
        return bnfBirthday;
    }

    /**
    * 设置字段bnfBirthday的值，该字段的<br>
    * 字段名称 :客户出生日期<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfBirthday(String bnfBirthday) {
        this.bnfBirthday = bnfBirthday;
    }

    /**
    * 获取字段bnfIDNo的值，该字段的<br>
    * 字段名称 :证件号码<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfIDNo() {
        return bnfIDNo;
    }

    /**
    * 设置字段bnfIDNo的值，该字段的<br>
    * 字段名称 :证件号码<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfIDNo(String bnfIDNo) {
        this.bnfIDNo = bnfIDNo;
    }

    /**
    * 获取字段bnfIDType的值，该字段的<br>
    * 字段名称 :证件类型<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfIDType() {
        return bnfIDType;
    }

    /**
    * 设置字段bnfIDType的值，该字段的<br>
    * 字段名称 :证件类型<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfIDType(String bnfIDType) {
        this.bnfIDType = bnfIDType;
    }

    /**
    * 获取字段bnfLot的值，该字段的<br>
    * 字段名称 :受益份额<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfLot() {
        return bnfLot;
    }

    /**
    * 设置字段bnfLot的值，该字段的<br>
    * 字段名称 :受益份额<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfLot(String bnfLot) {
        this.bnfLot = bnfLot;
    }

    /**
    * 获取字段bnfName的值，该字段的<br>
    * 字段名称 :客户姓名<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfName() {
        return bnfName;
    }

    /**
    * 设置字段bnfName的值，该字段的<br>
    * 字段名称 :客户姓名<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfName(String bnfName) {
        this.bnfName = bnfName;
    }

    /**
    * 获取字段bnfNo的值，该字段的<br>
    * 字段名称 :受益人序号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfNo() {
        return bnfNo;
    }

    /**
    * 设置字段bnfNo的值，该字段的<br>
    * 字段名称 :受益人序号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfNo(String bnfNo) {
        this.bnfNo = bnfNo;
    }

    /**
    * 获取字段bnfSex的值，该字段的<br>
    * 字段名称 :客户性别<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfSex() {
        return bnfSex;
    }

    /**
    * 设置字段bnfSex的值，该字段的<br>
    * 字段名称 :客户性别<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfSex(String bnfSex) {
        this.bnfSex = bnfSex;
    }

    /**
    * 获取字段bnfType的值，该字段的<br>
    * 字段名称 :受益人类别<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfType() {
        return bnfType;
    }

    /**
    * 设置字段bnfType的值，该字段的<br>
    * 字段名称 :受益人类别<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfType(String bnfType) {
        this.bnfType = bnfType;
    }

    /**
    * 获取字段email的值，该字段的<br>
    * 字段名称 :电子邮件<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getemail() {
        return email;
    }

    /**
    * 设置字段email的值，该字段的<br>
    * 字段名称 :电子邮件<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setemail(String email) {
        this.email = email;
    }

    /**
    * 获取字段informationSn的值，该字段的<br>
    * 字段名称 :订单详细表编码<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getinformationSn() {
        return informationSn;
    }

    /**
    * 设置字段informationSn的值，该字段的<br>
    * 字段名称 :订单详细表编码<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setinformationSn(String informationSn) {
        this.informationSn = informationSn;
    }

    /**
    * 获取字段informationSn2的值，该字段的<br>
    * 字段名称 :订单明细表编号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getinformationSn2() {
        return informationSn2;
    }

    /**
    * 设置字段informationSn2的值，该字段的<br>
    * 字段名称 :订单明细表编号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setinformationSn2(String informationSn2) {
        this.informationSn2 = informationSn2;
    }

    /**
    * 获取字段mobile的值，该字段的<br>
    * 字段名称 :手机<br>
    * 数据类型 :varchar(12)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getmobile() {
        return mobile;
    }

    /**
    * 设置字段mobile的值，该字段的<br>
    * 字段名称 :手机<br>
    * 数据类型 :varchar(12)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setmobile(String mobile) {
        this.mobile = mobile;
    }

    /**
    * 获取字段occupation的值，该字段的<br>
    * 字段名称 :受益人职业<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getoccupation() {
        return occupation;
    }

    /**
    * 设置字段occupation的值，该字段的<br>
    * 字段名称 :受益人职业<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setoccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
    * 获取字段orderSn的值，该字段的<br>
    * 字段名称 :订单编号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getorderSn() {
        return orderSn;
    }

    /**
    * 设置字段orderSn的值，该字段的<br>
    * 字段名称 :订单编号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setorderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
    * 获取字段phone的值，该字段的<br>
    * 字段名称 :固定电话<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getphone() {
        return phone;
    }

    /**
    * 设置字段phone的值，该字段的<br>
    * 字段名称 :固定电话<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setphone(String phone) {
        this.phone = phone;
    }

    /**
    * 获取字段postalAddress的值，该字段的<br>
    * 字段名称 :联系地址<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getpostalAddress() {
        return postalAddress;
    }

    /**
    * 设置字段postalAddress的值，该字段的<br>
    * 字段名称 :联系地址<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setpostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    /**
    * 获取字段recognizeeSn的值，该字段的<br>
    * 字段名称 :被保人编号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getrecognizeeSn() {
        return recognizeeSn;
    }

    /**
    * 设置字段recognizeeSn的值，该字段的<br>
    * 字段名称 :被保人编号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setrecognizeeSn(String recognizeeSn) {
        this.recognizeeSn = recognizeeSn;
    }

    /**
    * 获取字段relationToInsured的值，该字段的<br>
    * 字段名称 :与被保人关系<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getrelationToInsured() {
        return relationToInsured;
    }

    /**
    * 设置字段relationToInsured的值，该字段的<br>
    * 字段名称 :与被保人关系<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setrelationToInsured(String relationToInsured) {
        this.relationToInsured = relationToInsured;
    }

    /**
    * 获取字段zipCode的值，该字段的<br>
    * 字段名称 :邮编<br>
    * 数据类型 :varchar(10)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getzipCode() {
        return zipCode;
    }

    /**
    * 设置字段zipCode的值，该字段的<br>
    * 字段名称 :邮编<br>
    * 数据类型 :varchar(10)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setzipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
    * 获取字段sdinformationInsured_id的值，该字段的<br>
    * 字段名称 :sdinformationInsured_id<br>
    * 数据类型 :varchar(32)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getsdinformationInsured_id() {
        return sdinformationInsured_id;
    }

    /**
    * 设置字段sdinformationInsured_id的值，该字段的<br>
    * 字段名称 :sdinformationInsured_id<br>
    * 数据类型 :varchar(32)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setsdinformationInsured_id(String sdinformationInsured_id) {
        this.sdinformationInsured_id = sdinformationInsured_id;
    }

    /**
    * 获取字段bnfStartID的值，该字段的<br>
    * 字段名称 :bnfStartID<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfStartID() {
        return bnfStartID;
    }

    /**
    * 设置字段bnfStartID的值，该字段的<br>
    * 字段名称 :bnfStartID<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfStartID(String bnfStartID) {
        this.bnfStartID = bnfStartID;
    }

    /**
    * 获取字段bnfEndID的值，该字段的<br>
    * 字段名称 :bnfEndID<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfEndID() {
        return bnfEndID;
    }

    /**
    * 设置字段bnfEndID的值，该字段的<br>
    * 字段名称 :bnfEndID<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfEndID(String bnfEndID) {
        this.bnfEndID = bnfEndID;
    }

    /**
    * 获取字段bnfIDTypeName的值，该字段的<br>
    * 字段名称 :bnfIDTypeName<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfIDTypeName() {
        return bnfIDTypeName;
    }

    /**
    * 设置字段bnfIDTypeName的值，该字段的<br>
    * 字段名称 :bnfIDTypeName<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfIDTypeName(String bnfIDTypeName) {
        this.bnfIDTypeName = bnfIDTypeName;
    }

    /**
    * 获取字段bnfSexName的值，该字段的<br>
    * 字段名称 :bnfSexName<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfSexName() {
        return bnfSexName;
    }

    /**
    * 设置字段bnfSexName的值，该字段的<br>
    * 字段名称 :bnfSexName<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfSexName(String bnfSexName) {
        this.bnfSexName = bnfSexName;
    }

    /**
    * 获取字段relationToInsuredName的值，该字段的<br>
    * 字段名称 :relationToInsuredName<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getrelationToInsuredName() {
        return relationToInsuredName;
    }

    /**
    * 设置字段relationToInsuredName的值，该字段的<br>
    * 字段名称 :relationToInsuredName<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setrelationToInsuredName(String relationToInsuredName) {
        this.relationToInsuredName = relationToInsuredName;
    }

    /**
    * 获取字段bnfArea1的值，该字段的<br>
    * 字段名称 :bnfArea1<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfArea1() {
        return bnfArea1;
    }

    /**
    * 设置字段bnfArea1的值，该字段的<br>
    * 字段名称 :bnfArea1<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfArea1(String bnfArea1) {
        this.bnfArea1 = bnfArea1;
    }

    /**
    * 获取字段bnfArea2的值，该字段的<br>
    * 字段名称 :bnfArea2<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfArea2() {
        return bnfArea2;
    }

    /**
    * 设置字段bnfArea2的值，该字段的<br>
    * 字段名称 :bnfArea2<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfArea2(String bnfArea2) {
        this.bnfArea2 = bnfArea2;
    }

    /**
    * 获取字段bnfArea3的值，该字段的<br>
    * 字段名称 :bnfArea3<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfArea3() {
        return bnfArea3;
    }

    /**
    * 设置字段bnfArea3的值，该字段的<br>
    * 字段名称 :bnfArea3<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfArea3(String bnfArea3) {
        this.bnfArea3 = bnfArea3;
    }

    /**
    * 获取字段bnfOccupation1的值，该字段的<br>
    * 字段名称 :bnfOccupation1<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfOccupation1() {
        return bnfOccupation1;
    }

    /**
    * 设置字段bnfOccupation1的值，该字段的<br>
    * 字段名称 :bnfOccupation1<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfOccupation1(String bnfOccupation1) {
        this.bnfOccupation1 = bnfOccupation1;
    }

    /**
    * 获取字段bnfOccupation2的值，该字段的<br>
    * 字段名称 :bnfOccupation2<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfOccupation2() {
        return bnfOccupation2;
    }

    /**
    * 设置字段bnfOccupation2的值，该字段的<br>
    * 字段名称 :bnfOccupation2<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfOccupation2(String bnfOccupation2) {
        this.bnfOccupation2 = bnfOccupation2;
    }

    /**
    * 获取字段bnfOccupation3的值，该字段的<br>
    * 字段名称 :bnfOccupation3<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getbnfOccupation3() {
        return bnfOccupation3;
    }

    /**
    * 设置字段bnfOccupation3的值，该字段的<br>
    * 字段名称 :bnfOccupation3<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setbnfOccupation3(String bnfOccupation3) {
        this.bnfOccupation3 = bnfOccupation3;
    }
}
