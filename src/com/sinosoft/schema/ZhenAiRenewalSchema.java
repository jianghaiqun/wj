package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：臻爱产品续费中间表<br>
 * 表代码：zhenAiRenewal<br>
 * 表主键：ID<br>
 */
public class ZhenAiRenewalSchema extends Schema {

    private String ID;

    private Date createDate;

    private String policyNo;

    private String productId;

    private String planCode;

    private String choseProductId;

    private String chosePlanCode;

    private String endDate;

    private String withPolicyholderRelation;

    private String applicantName;

    private String applicantMobile;

    private String applicantMail;

    private String applicantIdentityType;

    private String applicantIdentityId;

    private String applicantBirthDate;

    private String applicantSex;

    private String recognizeeName;

    private String recognizeeMobile;

    private String recognizeeMail;

    private String recognizeeIdentityType;

    private String recognizeeIdentityId;

    private String recognizeeBirthDate;

    private String recognizeeSex;

    private String channelsn;

    private String cspNo;

    private String prop1;

    private String prop2;

    private String prop3;

    private String prop4;

    private String prop5;

    private String prop6;

    private String prop7;

    public static final SchemaColumn[] _Columns = new SchemaColumn[] {
        new SchemaColumn("ID", DataColumn.STRING, 0, 50 , 0 , true, true),
        new SchemaColumn("createDate", DataColumn.DATETIME, 1, 0 , 0 , true, false),
        new SchemaColumn("policyNo", DataColumn.STRING, 2, 100 , 0 , true, false),
        new SchemaColumn("productId", DataColumn.STRING, 3, 50 , 0 , true, false),
        new SchemaColumn("planCode", DataColumn.STRING, 4, 50 , 0 , true, false),
        new SchemaColumn("choseProductId", DataColumn.STRING, 5, 50 , 0 , true, false),
        new SchemaColumn("chosePlanCode", DataColumn.STRING, 6, 50 , 0 , true, false),
        new SchemaColumn("endDate", DataColumn.STRING, 7, 100 , 0 , true, false),
        new SchemaColumn("withPolicyholderRelation", DataColumn.STRING, 8, 100 , 0 , true, false),
        new SchemaColumn("applicantName", DataColumn.STRING, 9, 100 , 0 , true, false),
        new SchemaColumn("applicantMobile", DataColumn.STRING, 10, 100 , 0 , true, false),
        new SchemaColumn("applicantMail", DataColumn.STRING, 11, 100 , 0 , true, false),
        new SchemaColumn("applicantIdentityType", DataColumn.STRING, 12, 100 , 0 , true, false),
        new SchemaColumn("applicantIdentityId", DataColumn.STRING, 13, 100 , 0 , true, false),
        new SchemaColumn("applicantBirthDate", DataColumn.STRING, 14, 100 , 0 , true, false),
        new SchemaColumn("applicantSex", DataColumn.STRING, 15, 100 , 0 , true, false),
        new SchemaColumn("recognizeeName", DataColumn.STRING, 16, 100 , 0 , true, false),
        new SchemaColumn("recognizeeMobile", DataColumn.STRING, 17, 100 , 0 , true, false),
        new SchemaColumn("recognizeeMail", DataColumn.STRING, 18, 100 , 0 , true, false),
        new SchemaColumn("recognizeeIdentityType", DataColumn.STRING, 19, 100 , 0 , true, false),
        new SchemaColumn("recognizeeIdentityId", DataColumn.STRING, 20, 100 , 0 , true, false),
        new SchemaColumn("recognizeeBirthDate", DataColumn.STRING, 21, 100 , 0 , false, false),
        new SchemaColumn("recognizeeSex", DataColumn.STRING, 22, 100 , 0 , false, false),
        new SchemaColumn("channelsn", DataColumn.STRING, 23, 100 , 0 , true, false),
        new SchemaColumn("cspNo", DataColumn.STRING, 24, 100 , 0 , true, false),
        new SchemaColumn("prop1", DataColumn.STRING, 25, 500 , 0 , false, false),
        new SchemaColumn("prop2", DataColumn.STRING, 26, 500 , 0 , false, false),
        new SchemaColumn("prop3", DataColumn.STRING, 27, 500 , 0 , false, false),
        new SchemaColumn("prop4", DataColumn.STRING, 28, 500 , 0 , false, false),
        new SchemaColumn("prop5", DataColumn.STRING, 29, 500 , 0 , false, false),
        new SchemaColumn("prop6", DataColumn.STRING, 30, 500 , 0 , false, false),
        new SchemaColumn("prop7", DataColumn.STRING, 31, 500 , 0 , false, false)
    };

    public static final String _TableCode = "zhenAiRenewal";

    public static final String _NameSpace = "com.sinosoft.schema";

    protected static final String _InsertAllSQL = "insert into zhenAiRenewal values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    protected static final String _UpdateAllSQL = "update zhenAiRenewal set ID=?,createDate=?,policyNo=?,productId=?,planCode=?,choseProductId=?,chosePlanCode=?,endDate=?,withPolicyholderRelation=?,applicantName=?,applicantMobile=?,applicantMail=?,applicantIdentityType=?,applicantIdentityId=?,applicantBirthDate=?,applicantSex=?,recognizeeName=?,recognizeeMobile=?,recognizeeMail=?,recognizeeIdentityType=?,recognizeeIdentityId=?,recognizeeBirthDate=?,recognizeeSex=?,channelsn=?,cspNo=?,prop1=?,prop2=?,prop3=?,prop4=?,prop5=?,prop6=?,prop7=? where id=?";

    protected static final String _DeleteSQL = "delete from zhenAiRenewal  where id=?";

    protected static final String _FillAllSQL = "select * from zhenAiRenewal  where id=?";

    public ZhenAiRenewalSchema(){
        TableCode = _TableCode;
        NameSpace = _NameSpace;
        Columns = _Columns;
        InsertAllSQL = _InsertAllSQL;
        UpdateAllSQL = _UpdateAllSQL;
        DeleteSQL = _DeleteSQL;
        FillAllSQL = _FillAllSQL;
        HasSetFlag = new boolean[32];
    }

    protected Schema newInstance(){
        return new ZhenAiRenewalSchema();
    }

    protected SchemaSet newSet(){
        return new ZhenAiRenewalSet();
    }

    public ZhenAiRenewalSet query() {
        return query(null, -1, -1);
    }

    public ZhenAiRenewalSet query(QueryBuilder qb) {
        return query(qb, -1, -1);
    }

    public ZhenAiRenewalSet query(int pageSize, int pageIndex) {
        return query(null, pageSize, pageIndex);
    }

    public ZhenAiRenewalSet query(QueryBuilder qb , int pageSize, int pageIndex){
        return (ZhenAiRenewalSet)querySet(qb , pageSize , pageIndex);
    }

    public void setV(int i, Object v) {
        if (i == 0){ID = (String)v;return;}
        if (i == 1){createDate = (Date)v;return;}
        if (i == 2){policyNo = (String)v;return;}
        if (i == 3){productId = (String)v;return;}
        if (i == 4){planCode = (String)v;return;}
        if (i == 5){choseProductId = (String)v;return;}
        if (i == 6){chosePlanCode = (String)v;return;}
        if (i == 7){endDate = (String)v;return;}
        if (i == 8){withPolicyholderRelation = (String)v;return;}
        if (i == 9){applicantName = (String)v;return;}
        if (i == 10){applicantMobile = (String)v;return;}
        if (i == 11){applicantMail = (String)v;return;}
        if (i == 12){applicantIdentityType = (String)v;return;}
        if (i == 13){applicantIdentityId = (String)v;return;}
        if (i == 14){applicantBirthDate = (String)v;return;}
        if (i == 15){applicantSex = (String)v;return;}
        if (i == 16){recognizeeName = (String)v;return;}
        if (i == 17){recognizeeMobile = (String)v;return;}
        if (i == 18){recognizeeMail = (String)v;return;}
        if (i == 19){recognizeeIdentityType = (String)v;return;}
        if (i == 20){recognizeeIdentityId = (String)v;return;}
        if (i == 21){recognizeeBirthDate = (String)v;return;}
        if (i == 22){recognizeeSex = (String)v;return;}
        if (i == 23){channelsn = (String)v;return;}
        if (i == 24){cspNo = (String)v;return;}
        if (i == 25){prop1 = (String)v;return;}
        if (i == 26){prop2 = (String)v;return;}
        if (i == 27){prop3 = (String)v;return;}
        if (i == 28){prop4 = (String)v;return;}
        if (i == 29){prop5 = (String)v;return;}
        if (i == 30){prop6 = (String)v;return;}
        if (i == 31){prop7 = (String)v;return;}
    }

    public Object getV(int i) {
        if (i == 0){return ID;}
        if (i == 1){return createDate;}
        if (i == 2){return policyNo;}
        if (i == 3){return productId;}
        if (i == 4){return planCode;}
        if (i == 5){return choseProductId;}
        if (i == 6){return chosePlanCode;}
        if (i == 7){return endDate;}
        if (i == 8){return withPolicyholderRelation;}
        if (i == 9){return applicantName;}
        if (i == 10){return applicantMobile;}
        if (i == 11){return applicantMail;}
        if (i == 12){return applicantIdentityType;}
        if (i == 13){return applicantIdentityId;}
        if (i == 14){return applicantBirthDate;}
        if (i == 15){return applicantSex;}
        if (i == 16){return recognizeeName;}
        if (i == 17){return recognizeeMobile;}
        if (i == 18){return recognizeeMail;}
        if (i == 19){return recognizeeIdentityType;}
        if (i == 20){return recognizeeIdentityId;}
        if (i == 21){return recognizeeBirthDate;}
        if (i == 22){return recognizeeSex;}
        if (i == 23){return channelsn;}
        if (i == 24){return cspNo;}
        if (i == 25){return prop1;}
        if (i == 26){return prop2;}
        if (i == 27){return prop3;}
        if (i == 28){return prop4;}
        if (i == 29){return prop5;}
        if (i == 30){return prop6;}
        if (i == 31){return prop7;}
        return null;
    }

    /**
    * 获取字段ID的值，该字段的<br>
    * 字段名称 :ID<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :true<br>
    * 是否必填 :true<br>
    */
    public String getID() {
        return ID;
    }

    /**
    * 设置字段ID的值，该字段的<br>
    * 字段名称 :ID<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :true<br>
    * 是否必填 :true<br>
    */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
    * 获取字段createDate的值，该字段的<br>
    * 字段名称 :创建日期<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public Date getCreateDate() {
        return createDate;
    }

    /**
    * 设置字段createDate的值，该字段的<br>
    * 字段名称 :创建日期<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
    * 获取字段policyNo的值，该字段的<br>
    * 字段名称 :保单编号<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getPolicyNo() {
        return policyNo;
    }

    /**
    * 设置字段policyNo的值，该字段的<br>
    * 字段名称 :保单编号<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    /**
    * 获取字段productId的值，该字段的<br>
    * 字段名称 :产品编码<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getProductId() {
        return productId;
    }

    /**
    * 设置字段productId的值，该字段的<br>
    * 字段名称 :产品编码<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
    * 获取字段planCode的值，该字段的<br>
    * 字段名称 :计划编码<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getPlanCode() {
        return planCode;
    }

    /**
    * 设置字段planCode的值，该字段的<br>
    * 字段名称 :计划编码<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    /**
    * 获取字段choseProductId的值，该字段的<br>
    * 字段名称 :选择的计划编码<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getChoseProductId() {
        return choseProductId;
    }

    /**
    * 设置字段choseProductId的值，该字段的<br>
    * 字段名称 :选择的计划编码<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setChoseProductId(String choseProductId) {
        this.choseProductId = choseProductId;
    }

    /**
    * 获取字段chosePlanCode的值，该字段的<br>
    * 字段名称 :选择的产品编码<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getChosePlanCode() {
        return chosePlanCode;
    }

    /**
    * 设置字段chosePlanCode的值，该字段的<br>
    * 字段名称 :选择的产品编码<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setChosePlanCode(String chosePlanCode) {
        this.chosePlanCode = chosePlanCode;
    }

    /**
    * 获取字段endDate的值，该字段的<br>
    * 字段名称 :终保日期<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getEndDate() {
        return endDate;
    }

    /**
    * 设置字段endDate的值，该字段的<br>
    * 字段名称 :终保日期<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
    * 获取字段withPolicyholderRelation的值，该字段的<br>
    * 字段名称 :投被保人关系<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getWithPolicyholderRelation() {
        return withPolicyholderRelation;
    }

    /**
    * 设置字段withPolicyholderRelation的值，该字段的<br>
    * 字段名称 :投被保人关系<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setWithPolicyholderRelation(String withPolicyholderRelation) {
        this.withPolicyholderRelation = withPolicyholderRelation;
    }

    /**
    * 获取字段applicantName的值，该字段的<br>
    * 字段名称 :投保人姓名<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getApplicantName() {
        return applicantName;
    }

    /**
    * 设置字段applicantName的值，该字段的<br>
    * 字段名称 :投保人姓名<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    /**
    * 获取字段applicantMobile的值，该字段的<br>
    * 字段名称 :投保人手机号<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getApplicantMobile() {
        return applicantMobile;
    }

    /**
    * 设置字段applicantMobile的值，该字段的<br>
    * 字段名称 :投保人手机号<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setApplicantMobile(String applicantMobile) {
        this.applicantMobile = applicantMobile;
    }

    /**
    * 获取字段applicantMail的值，该字段的<br>
    * 字段名称 :投保人邮箱<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getApplicantMail() {
        return applicantMail;
    }

    /**
    * 设置字段applicantMail的值，该字段的<br>
    * 字段名称 :投保人邮箱<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setApplicantMail(String applicantMail) {
        this.applicantMail = applicantMail;
    }

    /**
    * 获取字段applicantIdentityType的值，该字段的<br>
    * 字段名称 :投保人证件类型<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getApplicantIdentityType() {
        return applicantIdentityType;
    }

    /**
    * 设置字段applicantIdentityType的值，该字段的<br>
    * 字段名称 :投保人证件类型<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setApplicantIdentityType(String applicantIdentityType) {
        this.applicantIdentityType = applicantIdentityType;
    }

    /**
    * 获取字段applicantIdentityId的值，该字段的<br>
    * 字段名称 :投保人证件号码<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getApplicantIdentityId() {
        return applicantIdentityId;
    }

    /**
    * 设置字段applicantIdentityId的值，该字段的<br>
    * 字段名称 :投保人证件号码<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setApplicantIdentityId(String applicantIdentityId) {
        this.applicantIdentityId = applicantIdentityId;
    }

    /**
    * 获取字段applicantBirthDate的值，该字段的<br>
    * 字段名称 :投保人生日<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getApplicantBirthDate() {
        return applicantBirthDate;
    }

    /**
    * 设置字段applicantBirthDate的值，该字段的<br>
    * 字段名称 :投保人生日<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setApplicantBirthDate(String applicantBirthDate) {
        this.applicantBirthDate = applicantBirthDate;
    }

    /**
    * 获取字段applicantSex的值，该字段的<br>
    * 字段名称 :投保人性别<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getApplicantSex() {
        return applicantSex;
    }

    /**
    * 设置字段applicantSex的值，该字段的<br>
    * 字段名称 :投保人性别<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setApplicantSex(String applicantSex) {
        this.applicantSex = applicantSex;
    }

    /**
    * 获取字段recognizeeName的值，该字段的<br>
    * 字段名称 :被保人姓名<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getRecognizeeName() {
        return recognizeeName;
    }

    /**
    * 设置字段recognizeeName的值，该字段的<br>
    * 字段名称 :被保人姓名<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setRecognizeeName(String recognizeeName) {
        this.recognizeeName = recognizeeName;
    }

    /**
    * 获取字段recognizeeMobile的值，该字段的<br>
    * 字段名称 :被保人手机号<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getRecognizeeMobile() {
        return recognizeeMobile;
    }

    /**
    * 设置字段recognizeeMobile的值，该字段的<br>
    * 字段名称 :被保人手机号<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setRecognizeeMobile(String recognizeeMobile) {
        this.recognizeeMobile = recognizeeMobile;
    }

    /**
    * 获取字段recognizeeMail的值，该字段的<br>
    * 字段名称 :被保人邮箱<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getRecognizeeMail() {
        return recognizeeMail;
    }

    /**
    * 设置字段recognizeeMail的值，该字段的<br>
    * 字段名称 :被保人邮箱<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setRecognizeeMail(String recognizeeMail) {
        this.recognizeeMail = recognizeeMail;
    }

    /**
    * 获取字段recognizeeIdentityType的值，该字段的<br>
    * 字段名称 :被保人证件类型<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getRecognizeeIdentityType() {
        return recognizeeIdentityType;
    }

    /**
    * 设置字段recognizeeIdentityType的值，该字段的<br>
    * 字段名称 :被保人证件类型<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setRecognizeeIdentityType(String recognizeeIdentityType) {
        this.recognizeeIdentityType = recognizeeIdentityType;
    }

    /**
    * 获取字段recognizeeIdentityId的值，该字段的<br>
    * 字段名称 :被保人证件号<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getRecognizeeIdentityId() {
        return recognizeeIdentityId;
    }

    /**
    * 设置字段recognizeeIdentityId的值，该字段的<br>
    * 字段名称 :被保人证件号<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setRecognizeeIdentityId(String recognizeeIdentityId) {
        this.recognizeeIdentityId = recognizeeIdentityId;
    }

    /**
    * 获取字段recognizeeBirthDate的值，该字段的<br>
    * 字段名称 :被保人生日<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getRecognizeeBirthDate() {
        return recognizeeBirthDate;
    }

    /**
    * 设置字段recognizeeBirthDate的值，该字段的<br>
    * 字段名称 :被保人生日<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setRecognizeeBirthDate(String recognizeeBirthDate) {
        this.recognizeeBirthDate = recognizeeBirthDate;
    }

    /**
    * 获取字段recognizeeSex的值，该字段的<br>
    * 字段名称 :被保人性别<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getRecognizeeSex() {
        return recognizeeSex;
    }

    /**
    * 设置字段recognizeeSex的值，该字段的<br>
    * 字段名称 :被保人性别<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setRecognizeeSex(String recognizeeSex) {
        this.recognizeeSex = recognizeeSex;
    }

    /**
    * 获取字段channelsn的值，该字段的<br>
    * 字段名称 :渠道<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getChannelsn() {
        return channelsn;
    }

    /**
    * 设置字段channelsn的值，该字段的<br>
    * 字段名称 :渠道<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setChannelsn(String channelsn) {
        this.channelsn = channelsn;
    }

    /**
    * 获取字段cspNo的值，该字段的<br>
    * 字段名称 :CSP码<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getCspNo() {
        return cspNo;
    }

    /**
    * 设置字段cspNo的值，该字段的<br>
    * 字段名称 :CSP码<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setCspNo(String cspNo) {
        this.cspNo = cspNo;
    }

    /**
    * 获取字段prop1的值，该字段的<br>
    * 字段名称 :备用字段1<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp1() {
        return prop1;
    }

    /**
    * 设置字段prop1的值，该字段的<br>
    * 字段名称 :备用字段1<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    /**
    * 获取字段prop2的值，该字段的<br>
    * 字段名称 :备用字段2<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp2() {
        return prop2;
    }

    /**
    * 设置字段prop2的值，该字段的<br>
    * 字段名称 :备用字段2<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    /**
    * 获取字段prop3的值，该字段的<br>
    * 字段名称 :备用字段3<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp3() {
        return prop3;
    }

    /**
    * 设置字段prop3的值，该字段的<br>
    * 字段名称 :备用字段3<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp3(String prop3) {
        this.prop3 = prop3;
    }

    /**
    * 获取字段prop4的值，该字段的<br>
    * 字段名称 :备用字段4<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp4() {
        return prop4;
    }

    /**
    * 设置字段prop4的值，该字段的<br>
    * 字段名称 :备用字段4<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp4(String prop4) {
        this.prop4 = prop4;
    }

    /**
    * 获取字段prop5的值，该字段的<br>
    * 字段名称 :备用字段5<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp5() {
        return prop5;
    }

    /**
    * 设置字段prop5的值，该字段的<br>
    * 字段名称 :备用字段5<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp5(String prop5) {
        this.prop5 = prop5;
    }

    /**
    * 获取字段prop6的值，该字段的<br>
    * 字段名称 :备用字段6<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp6() {
        return prop6;
    }

    /**
    * 设置字段prop6的值，该字段的<br>
    * 字段名称 :备用字段6<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp6(String prop6) {
        this.prop6 = prop6;
    }

    /**
    * 获取字段prop7的值，该字段的<br>
    * 字段名称 :备用字段7<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp7() {
        return prop7;
    }

    /**
    * 设置字段prop7的值，该字段的<br>
    * 字段名称 :备用字段7<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp7(String prop7) {
        this.prop7 = prop7;
    }
}
