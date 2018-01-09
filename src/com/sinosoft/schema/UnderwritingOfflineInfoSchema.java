package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：线下核保信息<br>
 * 表代码：underwriting_offline_info<br>
 * 表主键：id<br>
 */
public class UnderwritingOfflineInfoSchema extends Schema {

    private Integer id;

    private String productId;

    private String productName;

    private String name;

    private String mobile;

    private String email;

    private String insureEmail;

    private String situationDesc;

    private String remark;

    private String operator;

    private String dealStatus;

    private String underwritingStatus;

    private Date finishTime;

    private Date createTime;

    private Date modifyTime;

    private String prop1;

    private String prop2;

    private String prop3;

    public static final SchemaColumn[] _Columns = new SchemaColumn[] {
        new SchemaColumn("id", DataColumn.INTEGER, 0, 0 , 0 , false, true),
        new SchemaColumn("product_id", DataColumn.STRING, 1, 50 , 0 , true, false),
        new SchemaColumn("product_name", DataColumn.STRING, 2, 200 , 0 , true, false),
        new SchemaColumn("name", DataColumn.STRING, 3, 50 , 0 , true, false),
        new SchemaColumn("mobile", DataColumn.STRING, 4, 13 , 0 , true, false),
        new SchemaColumn("email", DataColumn.STRING, 5, 50 , 0 , true, false),
        new SchemaColumn("insure_email", DataColumn.STRING, 6, 50 , 0 , false, false),
        new SchemaColumn("situation_desc", DataColumn.STRING, 7, 0 , 0 , true, false),
        new SchemaColumn("remark", DataColumn.STRING, 8, 0 , 0 , false, false),
        new SchemaColumn("operator", DataColumn.STRING, 9, 100 , 0 , false, false),
        new SchemaColumn("deal_status", DataColumn.STRING, 10, 2 , 0 , true, false),
        new SchemaColumn("underwriting_status", DataColumn.STRING, 11, 2 , 0 , false, false),
        new SchemaColumn("finish_time", DataColumn.DATETIME, 12, 0 , 0 , false, false),
        new SchemaColumn("create_time", DataColumn.DATETIME, 13, 0 , 0 , true, false),
        new SchemaColumn("modify_time", DataColumn.DATETIME, 14, 0 , 0 , false, false),
        new SchemaColumn("prop1", DataColumn.STRING, 15, 255 , 0 , false, false),
        new SchemaColumn("prop2", DataColumn.STRING, 16, 255 , 0 , false, false),
        new SchemaColumn("prop3", DataColumn.STRING, 17, 255 , 0 , false, false)
    };

    public static final String _TableCode = "underwriting_offline_info";

    public static final String _NameSpace = "com.sinosoft.schema";

    protected static final String _InsertAllSQL = "insert into underwriting_offline_info values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    protected static final String _UpdateAllSQL = "update underwriting_offline_info set id=?,product_id=?,product_name=?,name=?,mobile=?,email=?,insure_email=?,situation_desc=?,remark=?,operator=?,deal_status=?,underwriting_status=?,finish_time=?,create_time=?,modify_time=?,prop1=?,prop2=?,prop3=? where id=?";

    protected static final String _DeleteSQL = "delete from underwriting_offline_info  where id=?";

    protected static final String _FillAllSQL = "select * from underwriting_offline_info  where id=?";

    public UnderwritingOfflineInfoSchema(){
        TableCode = _TableCode;
        NameSpace = _NameSpace;
        Columns = _Columns;
        InsertAllSQL = _InsertAllSQL;
        UpdateAllSQL = _UpdateAllSQL;
        DeleteSQL = _DeleteSQL;
        FillAllSQL = _FillAllSQL;
        HasSetFlag = new boolean[18];
    }

    protected Schema newInstance(){
        return new UnderwritingOfflineInfoSchema();
    }

    protected SchemaSet newSet(){
        return new UnderwritingOfflineInfoSet();
    }

    public UnderwritingOfflineInfoSet query() {
        return query(null, -1, -1);
    }

    public UnderwritingOfflineInfoSet query(QueryBuilder qb) {
        return query(qb, -1, -1);
    }

    public UnderwritingOfflineInfoSet query(int pageSize, int pageIndex) {
        return query(null, pageSize, pageIndex);
    }

    public UnderwritingOfflineInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
        return (UnderwritingOfflineInfoSet)querySet(qb , pageSize , pageIndex);
    }

    public void setV(int i, Object v) {
        if (i == 0){id = (Integer)v;return;}
        if (i == 1){productId = (String)v;return;}
        if (i == 2){productName = (String)v;return;}
        if (i == 3){name = (String)v;return;}
        if (i == 4){mobile = (String)v;return;}
        if (i == 5){email = (String)v;return;}
        if (i == 6){insureEmail = (String)v;return;}
        if (i == 7){situationDesc = (String)v;return;}
        if (i == 8){remark = (String)v;return;}
        if (i == 9){operator = (String)v;return;}
        if (i == 10){dealStatus = (String)v;return;}
        if (i == 11){underwritingStatus = (String)v;return;}
        if (i == 12){finishTime = (Date)v;return;}
        if (i == 13){createTime = (Date)v;return;}
        if (i == 14){modifyTime = (Date)v;return;}
        if (i == 15){prop1 = (String)v;return;}
        if (i == 16){prop2 = (String)v;return;}
        if (i == 17){prop3 = (String)v;return;}
    }

    public Object getV(int i) {
        if (i == 0){return id;}
        if (i == 1){return productId;}
        if (i == 2){return productName;}
        if (i == 3){return name;}
        if (i == 4){return mobile;}
        if (i == 5){return email;}
        if (i == 6){return insureEmail;}
        if (i == 7){return situationDesc;}
        if (i == 8){return remark;}
        if (i == 9){return operator;}
        if (i == 10){return dealStatus;}
        if (i == 11){return underwritingStatus;}
        if (i == 12){return finishTime;}
        if (i == 13){return createTime;}
        if (i == 14){return modifyTime;}
        if (i == 15){return prop1;}
        if (i == 16){return prop2;}
        if (i == 17){return prop3;}
        return null;
    }

    /**
    * 获取字段id的值，该字段的<br>
    * 字段名称 :主键<br>
    * 数据类型 :int<br>
    * 是否主键 :true<br>
    * 是否必填 :false<br>
    */
    public Integer getId() {
        return id;
    }

    /**
    * 设置字段id的值，该字段的<br>
    * 字段名称 :主键<br>
    * 数据类型 :int<br>
    * 是否主键 :true<br>
    * 是否必填 :false<br>
    */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
    * 获取字段productId的值，该字段的<br>
    * 字段名称 :产品id<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getProductId() {
        return productId;
    }

    /**
    * 设置字段productId的值，该字段的<br>
    * 字段名称 :产品id<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
    * 获取字段productName的值，该字段的<br>
    * 字段名称 :产品名称<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getProductName() {
        return productName;
    }

    /**
    * 设置字段productName的值，该字段的<br>
    * 字段名称 :产品名称<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
    * 获取字段name的值，该字段的<br>
    * 字段名称 :姓名<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getName() {
        return name;
    }

    /**
    * 设置字段name的值，该字段的<br>
    * 字段名称 :姓名<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * 获取字段mobile的值，该字段的<br>
    * 字段名称 :手机号<br>
    * 数据类型 :varchar(13)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getMobile() {
        return mobile;
    }

    /**
    * 设置字段mobile的值，该字段的<br>
    * 字段名称 :手机号<br>
    * 数据类型 :varchar(13)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
    * 获取字段email的值，该字段的<br>
    * 字段名称 :客户邮箱<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getEmail() {
        return email;
    }

    /**
    * 设置字段email的值，该字段的<br>
    * 字段名称 :客户邮箱<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
    * 获取字段insureEmail的值，该字段的<br>
    * 字段名称 :保险公司邮箱<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getInsureEmail() {
        return insureEmail;
    }

    /**
    * 设置字段insureEmail的值，该字段的<br>
    * 字段名称 :保险公司邮箱<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setInsureEmail(String insureEmail) {
        this.insureEmail = insureEmail;
    }

    /**
    * 获取字段situationDesc的值，该字段的<br>
    * 字段名称 :情况说明<br>
    * 数据类型 :text<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getSituationDesc() {
        return situationDesc;
    }

    /**
    * 设置字段situationDesc的值，该字段的<br>
    * 字段名称 :情况说明<br>
    * 数据类型 :text<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setSituationDesc(String situationDesc) {
        this.situationDesc = situationDesc;
    }

    /**
    * 获取字段remark的值，该字段的<br>
    * 字段名称 :备注<br>
    * 数据类型 :text<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getRemark() {
        return remark;
    }

    /**
    * 设置字段remark的值，该字段的<br>
    * 字段名称 :备注<br>
    * 数据类型 :text<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
    * 获取字段operator的值，该字段的<br>
    * 字段名称 :操作人<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getOperator() {
        return operator;
    }

    /**
    * 设置字段operator的值，该字段的<br>
    * 字段名称 :操作人<br>
    * 数据类型 :varchar(100)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
    * 获取字段dealStatus的值，该字段的<br>
    * 字段名称 :处理状态('0':未审核,'1':审核中,'2':已完结)<br>
    * 数据类型 :varchar(2)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getDealStatus() {
        return dealStatus;
    }

    /**
    * 设置字段dealStatus的值，该字段的<br>
    * 字段名称 :处理状态('0':未审核,'1':审核中,'2':已完结)<br>
    * 数据类型 :varchar(2)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    /**
    * 获取字段underwritingStatus的值，该字段的<br>
    * 字段名称 :核保状态('0':未通过,'1':已通过)<br>
    * 数据类型 :varchar(2)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getUnderwritingStatus() {
        return underwritingStatus;
    }

    /**
    * 设置字段underwritingStatus的值，该字段的<br>
    * 字段名称 :核保状态('0':未通过,'1':已通过)<br>
    * 数据类型 :varchar(2)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setUnderwritingStatus(String underwritingStatus) {
        this.underwritingStatus = underwritingStatus;
    }

    /**
    * 获取字段finishTime的值，该字段的<br>
    * 字段名称 :完结时间<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
    * 设置字段finishTime的值，该字段的<br>
    * 字段名称 :完结时间<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    /**
    * 获取字段createTime的值，该字段的<br>
    * 字段名称 :创建时间<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public Date getCreateTime() {
        return createTime;
    }

    /**
    * 设置字段createTime的值，该字段的<br>
    * 字段名称 :创建时间<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
    * 获取字段modifyTime的值，该字段的<br>
    * 字段名称 :修改时间<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
    * 设置字段modifyTime的值，该字段的<br>
    * 字段名称 :修改时间<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
    * 获取字段prop1的值，该字段的<br>
    * 字段名称 :备用字段1<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp1() {
        return prop1;
    }

    /**
    * 设置字段prop1的值，该字段的<br>
    * 字段名称 :备用字段1<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    /**
    * 获取字段prop2的值，该字段的<br>
    * 字段名称 :备用字段2<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp2() {
        return prop2;
    }

    /**
    * 设置字段prop2的值，该字段的<br>
    * 字段名称 :备用字段2<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    /**
    * 获取字段prop3的值，该字段的<br>
    * 字段名称 :备用字段3<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp3() {
        return prop3;
    }

    /**
    * 设置字段prop3的值，该字段的<br>
    * 字段名称 :备用字段3<br>
    * 数据类型 :varchar(255)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp3(String prop3) {
        this.prop3 = prop3;
    }
}
