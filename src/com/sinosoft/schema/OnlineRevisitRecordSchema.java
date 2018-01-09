package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：在线回访记录<br>
 * 表代码：onlinerevisitrecord<br>
 * 表主键：id<br>
 */
public class OnlineRevisitRecordSchema extends Schema {

    private Long id;

    private String applicantName;

    private String applicantMobile;

    private String productName;

    private String orderSn;

    private Date createDate;

    private Date modifyDate;

    private String status;

    private String prop1;

    private String prop2;

    private String prop3;

    private String prop4;

    public static final SchemaColumn[] _Columns = new SchemaColumn[] {
        new SchemaColumn("id", DataColumn.LONG, 0, 0 , 0 , false, true),
        new SchemaColumn("applicantName", DataColumn.STRING, 1, 50 , 0 , true, false),
        new SchemaColumn("applicantMobile", DataColumn.STRING, 2, 13 , 0 , true, false),
        new SchemaColumn("productName", DataColumn.STRING, 3, 200 , 0 , true, false),
        new SchemaColumn("orderSn", DataColumn.STRING, 4, 25 , 0 , true, false),
        new SchemaColumn("createDate", DataColumn.DATETIME, 5, 0 , 0 , true, false),
        new SchemaColumn("modifyDate", DataColumn.DATETIME, 6, 0 , 0 , false, false),
        new SchemaColumn("status", DataColumn.STRING, 7, 1 , 0 , true, false),
        new SchemaColumn("prop1", DataColumn.STRING, 8, 20 , 0 , false, false),
        new SchemaColumn("prop2", DataColumn.STRING, 9, 20 , 0 , false, false),
        new SchemaColumn("prop3", DataColumn.STRING, 10, 20 , 0 , false, false),
        new SchemaColumn("prop4", DataColumn.STRING, 11, 20 , 0 , false, false)
    };

    public static final String _TableCode = "onlinerevisitrecord";

    public static final String _NameSpace = "com.sinosoft.schema";

    protected static final String _InsertAllSQL = "insert into onlinerevisitrecord values(?,?,?,?,?,?,?,?,?,?,?,?)";

    protected static final String _UpdateAllSQL = "update onlinerevisitrecord set id=?,applicantName=?,applicantMobile=?,productName=?,orderSn=?,createDate=?,modifyDate=?,status=?,prop1=?,prop2=?,prop3=?,prop4=? where id=?";

    protected static final String _DeleteSQL = "delete from onlinerevisitrecord  where id=?";

    protected static final String _FillAllSQL = "select * from onlinerevisitrecord  where id=?";

    public OnlineRevisitRecordSchema(){
        TableCode = _TableCode;
        NameSpace = _NameSpace;
        Columns = _Columns;
        InsertAllSQL = _InsertAllSQL;
        UpdateAllSQL = _UpdateAllSQL;
        DeleteSQL = _DeleteSQL;
        FillAllSQL = _FillAllSQL;
        HasSetFlag = new boolean[12];
    }

    protected Schema newInstance(){
        return new OnlineRevisitRecordSchema();
    }

    protected SchemaSet newSet(){
        return new OnlineRevisitRecordSet();
    }

    public OnlineRevisitRecordSet query() {
        return query(null, -1, -1);
    }

    public OnlineRevisitRecordSet query(QueryBuilder qb) {
        return query(qb, -1, -1);
    }

    public OnlineRevisitRecordSet query(int pageSize, int pageIndex) {
        return query(null, pageSize, pageIndex);
    }

    public OnlineRevisitRecordSet query(QueryBuilder qb , int pageSize, int pageIndex){
        return (OnlineRevisitRecordSet)querySet(qb , pageSize , pageIndex);
    }

    public void setV(int i, Object v) {
        if (i == 0){id = (Long)v;return;}
        if (i == 1){applicantName = (String)v;return;}
        if (i == 2){applicantMobile = (String)v;return;}
        if (i == 3){productName = (String)v;return;}
        if (i == 4){orderSn = (String)v;return;}
        if (i == 5){createDate = (Date)v;return;}
        if (i == 6){modifyDate = (Date)v;return;}
        if (i == 7){status = (String)v;return;}
        if (i == 8){prop1 = (String)v;return;}
        if (i == 9){prop2 = (String)v;return;}
        if (i == 10){prop3 = (String)v;return;}
        if (i == 11){prop4 = (String)v;return;}
    }

    public Object getV(int i) {
        if (i == 0){return id;}
        if (i == 1){return applicantName;}
        if (i == 2){return applicantMobile;}
        if (i == 3){return productName;}
        if (i == 4){return orderSn;}
        if (i == 5){return createDate;}
        if (i == 6){return modifyDate;}
        if (i == 7){return status;}
        if (i == 8){return prop1;}
        if (i == 9){return prop2;}
        if (i == 10){return prop3;}
        if (i == 11){return prop4;}
        return null;
    }

    /**
    * 获取字段id的值，该字段的<br>
    * 字段名称 :主键<br>
    * 数据类型 :bigint<br>
    * 是否主键 :true<br>
    * 是否必填 :false<br>
    */
    public Long getId() {
        return id;
    }

    /**
    * 设置字段id的值，该字段的<br>
    * 字段名称 :主键<br>
    * 数据类型 :bigint<br>
    * 是否主键 :true<br>
    * 是否必填 :false<br>
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * 获取字段applicantName的值，该字段的<br>
    * 字段名称 :投保人姓名<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getApplicantName() {
        return applicantName;
    }

    /**
    * 设置字段applicantName的值，该字段的<br>
    * 字段名称 :投保人姓名<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    /**
    * 获取字段applicantMobile的值，该字段的<br>
    * 字段名称 :投保人手机号<br>
    * 数据类型 :varchar(13)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getApplicantMobile() {
        return applicantMobile;
    }

    /**
    * 设置字段applicantMobile的值，该字段的<br>
    * 字段名称 :投保人手机号<br>
    * 数据类型 :varchar(13)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setApplicantMobile(String applicantMobile) {
        this.applicantMobile = applicantMobile;
    }

    /**
    * 获取字段productName的值，该字段的<br>
    * 字段名称 :购买产品名称<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getProductName() {
        return productName;
    }

    /**
    * 设置字段productName的值，该字段的<br>
    * 字段名称 :购买产品名称<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
    * 获取字段orderSn的值，该字段的<br>
    * 字段名称 :订单号<br>
    * 数据类型 :varchar(25)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getOrderSn() {
        return orderSn;
    }

    /**
    * 设置字段orderSn的值，该字段的<br>
    * 字段名称 :订单号<br>
    * 数据类型 :varchar(25)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
    * 获取字段createDate的值，该字段的<br>
    * 字段名称 :创建时间<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public Date getCreateDate() {
        return createDate;
    }

    /**
    * 设置字段createDate的值，该字段的<br>
    * 字段名称 :创建时间<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
    * 获取字段modifyDate的值，该字段的<br>
    * 字段名称 :修改时间<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
    * 设置字段modifyDate的值，该字段的<br>
    * 字段名称 :修改时间<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
    * 获取字段status的值，该字段的<br>
    * 字段名称 :状态('1':回访成功,'0':未回访)<br>
    * 数据类型 :char(1)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getStatus() {
        return status;
    }

    /**
    * 设置字段status的值，该字段的<br>
    * 字段名称 :状态('1':回访成功,'0':未回访)<br>
    * 数据类型 :char(1)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
    * 获取字段prop1的值，该字段的<br>
    * 字段名称 :备用字段1<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp1() {
        return prop1;
    }

    /**
    * 设置字段prop1的值，该字段的<br>
    * 字段名称 :备用字段1<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    /**
    * 获取字段prop2的值，该字段的<br>
    * 字段名称 :备用字段2<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp2() {
        return prop2;
    }

    /**
    * 设置字段prop2的值，该字段的<br>
    * 字段名称 :备用字段2<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    /**
    * 获取字段prop3的值，该字段的<br>
    * 字段名称 :备用字段3<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp3() {
        return prop3;
    }

    /**
    * 设置字段prop3的值，该字段的<br>
    * 字段名称 :备用字段3<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp3(String prop3) {
        this.prop3 = prop3;
    }

    /**
    * 获取字段prop4的值，该字段的<br>
    * 字段名称 :备用字段4<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp4() {
        return prop4;
    }

    /**
    * 设置字段prop4的值，该字段的<br>
    * 字段名称 :备用字段4<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp4(String prop4) {
        this.prop4 = prop4;
    }
}
