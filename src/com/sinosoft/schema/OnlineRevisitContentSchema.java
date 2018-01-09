package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：在线回访内容配置<br>
 * 表代码：onlinerevisitcontent<br>
 * 表主键：id<br>
 */
public class OnlineRevisitContentSchema extends Schema {

    private Long id;

    private String productId;

    private String content;

    private String prop1;

    private String prop2;

    private String prop3;

    private String prop4;

    public static final SchemaColumn[] _Columns = new SchemaColumn[] {
        new SchemaColumn("id", DataColumn.LONG, 0, 0 , 0 , false, true),
        new SchemaColumn("productId", DataColumn.STRING, 1, 50 , 0 , true, false),
        new SchemaColumn("content", DataColumn.STRING, 2, 0 , 0 , false, false),
        new SchemaColumn("prop1", DataColumn.STRING, 3, 20 , 0 , false, false),
        new SchemaColumn("prop2", DataColumn.STRING, 4, 20 , 0 , false, false),
        new SchemaColumn("prop3", DataColumn.STRING, 5, 20 , 0 , false, false),
        new SchemaColumn("prop4", DataColumn.STRING, 6, 20 , 0 , false, false)
    };

    public static final String _TableCode = "onlinerevisitcontent";

    public static final String _NameSpace = "com.sinosoft.schema";

    protected static final String _InsertAllSQL = "insert into onlinerevisitcontent values(?,?,?,?,?,?,?)";

    protected static final String _UpdateAllSQL = "update onlinerevisitcontent set id=?,productId=?,content=?,prop1=?,prop2=?,prop3=?,prop4=? where id=?";

    protected static final String _DeleteSQL = "delete from onlinerevisitcontent  where id=?";

    protected static final String _FillAllSQL = "select * from onlinerevisitcontent  where id=?";

    public OnlineRevisitContentSchema(){
        TableCode = _TableCode;
        NameSpace = _NameSpace;
        Columns = _Columns;
        InsertAllSQL = _InsertAllSQL;
        UpdateAllSQL = _UpdateAllSQL;
        DeleteSQL = _DeleteSQL;
        FillAllSQL = _FillAllSQL;
        HasSetFlag = new boolean[7];
    }

    protected Schema newInstance(){
        return new OnlineRevisitContentSchema();
    }

    protected SchemaSet newSet(){
        return new OnlineRevisitContentSet();
    }

    public OnlineRevisitContentSet query() {
        return query(null, -1, -1);
    }

    public OnlineRevisitContentSet query(QueryBuilder qb) {
        return query(qb, -1, -1);
    }

    public OnlineRevisitContentSet query(int pageSize, int pageIndex) {
        return query(null, pageSize, pageIndex);
    }

    public OnlineRevisitContentSet query(QueryBuilder qb , int pageSize, int pageIndex){
        return (OnlineRevisitContentSet)querySet(qb , pageSize , pageIndex);
    }

    public void setV(int i, Object v) {
        if (i == 0){id = (Long)v;return;}
        if (i == 1){productId = (String)v;return;}
        if (i == 2){content = (String)v;return;}
        if (i == 3){prop1 = (String)v;return;}
        if (i == 4){prop2 = (String)v;return;}
        if (i == 5){prop3 = (String)v;return;}
        if (i == 6){prop4 = (String)v;return;}
    }

    public Object getV(int i) {
        if (i == 0){return id;}
        if (i == 1){return productId;}
        if (i == 2){return content;}
        if (i == 3){return prop1;}
        if (i == 4){return prop2;}
        if (i == 5){return prop3;}
        if (i == 6){return prop4;}
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
    * 获取字段content的值，该字段的<br>
    * 字段名称 :内容<br>
    * 数据类型 :text<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getContent() {
        return content;
    }

    /**
    * 设置字段content的值，该字段的<br>
    * 字段名称 :内容<br>
    * 数据类型 :text<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setContent(String content) {
        this.content = content;
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
