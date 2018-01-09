package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：淘宝会员复购详情<br>
 * 表代码：taobaomemberrepurchasedetail<br>
 * 表主键：id<br>
 */
public class TaobaoMemberRepurchaseDetailSchema extends Schema {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long analysisId;

    private String productId;

    private String productName;

    private String prem;

    private Date orderDate;

    public static final SchemaColumn[] _Columns = new SchemaColumn[] {
        new SchemaColumn("id", DataColumn.LONG, 0, 0 , 0 , true, true),
        new SchemaColumn("analysisId", DataColumn.LONG, 1, 0 , 0 , false, false),
        new SchemaColumn("productId", DataColumn.STRING, 2, 50 , 0 , false, false),
        new SchemaColumn("productName", DataColumn.STRING, 3, 200 , 0 , false, false),
        new SchemaColumn("prem", DataColumn.STRING, 4, 20 , 0 , false, false),
        new SchemaColumn("orderDate", DataColumn.DATETIME, 5, 0 , 0 , false, false)
    };

    public static final String _TableCode = "taobaomemberrepurchasedetail";

    public static final String _NameSpace = "com.sinosoft.schema";

    protected static final String _InsertAllSQL = "insert into taobaomemberrepurchasedetail values(?,?,?,?,?,?)";

    protected static final String _UpdateAllSQL = "update taobaomemberrepurchasedetail set id=?,analysisId=?,productId=?,productName=?,prem=?,orderDate=? where id=?";

    protected static final String _DeleteSQL = "delete from taobaomemberrepurchasedetail  where id=?";

    protected static final String _FillAllSQL = "select * from taobaomemberrepurchasedetail  where id=?";

    public TaobaoMemberRepurchaseDetailSchema(){
        TableCode = _TableCode;
        NameSpace = _NameSpace;
        Columns = _Columns;
        InsertAllSQL = _InsertAllSQL;
        UpdateAllSQL = _UpdateAllSQL;
        DeleteSQL = _DeleteSQL;
        FillAllSQL = _FillAllSQL;
        HasSetFlag = new boolean[6];
    }

    protected Schema newInstance(){
        return new TaobaoMemberRepurchaseDetailSchema();
    }

    protected SchemaSet newSet(){
        return new TaobaoMemberRepurchaseDetailSet();
    }

    public TaobaoMemberRepurchaseDetailSet query() {
        return query(null, -1, -1);
    }

    public TaobaoMemberRepurchaseDetailSet query(QueryBuilder qb) {
        return query(qb, -1, -1);
    }

    public TaobaoMemberRepurchaseDetailSet query(int pageSize, int pageIndex) {
        return query(null, pageSize, pageIndex);
    }

    public TaobaoMemberRepurchaseDetailSet query(QueryBuilder qb , int pageSize, int pageIndex){
        return (TaobaoMemberRepurchaseDetailSet)querySet(qb , pageSize , pageIndex);
    }

    public void setV(int i, Object v) {
        if (i == 0){id = (Long)v;return;}
        if (i == 1){analysisId = (Long)v;return;}
        if (i == 2){productId = (String)v;return;}
        if (i == 3){productName = (String)v;return;}
        if (i == 4){prem = (String)v;return;}
        if (i == 5){orderDate = (Date)v;return;}
    }

    public Object getV(int i) {
        if (i == 0){return id;}
        if (i == 1){return analysisId;}
        if (i == 2){return productId;}
        if (i == 3){return productName;}
        if (i == 4){return prem;}
        if (i == 5){return orderDate;}
        return null;
    }

    /**
    * 获取字段id的值，该字段的<br>
    * 字段名称 :主键<br>
    * 数据类型 :bigint<br>
    * 是否主键 :true<br>
    * 是否必填 :true<br>
    */
    public Long getId() {
        return id;
    }

    /**
    * 设置字段id的值，该字段的<br>
    * 字段名称 :主键<br>
    * 数据类型 :bigint<br>
    * 是否主键 :true<br>
    * 是否必填 :true<br>
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
    * 获取字段analysisId的值，该字段的<br>
    * 字段名称 :统计id<br>
    * 数据类型 :bigint<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public Long getAnalysisId() {
        return analysisId;
    }

    /**
    * 设置字段analysisId的值，该字段的<br>
    * 字段名称 :统计id<br>
    * 数据类型 :bigint<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setAnalysisId(Long analysisId) {
        this.analysisId = analysisId;
    }

    /**
    * 获取字段productId的值，该字段的<br>
    * 字段名称 :产品id<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProductId() {
        return productId;
    }

    /**
    * 设置字段productId的值，该字段的<br>
    * 字段名称 :产品id<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
    * 获取字段productName的值，该字段的<br>
    * 字段名称 :产品名称<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProductName() {
        return productName;
    }

    /**
    * 设置字段productName的值，该字段的<br>
    * 字段名称 :产品名称<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
    * 获取字段prem的值，该字段的<br>
    * 字段名称 :保费<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getPrem() {
        return prem;
    }

    /**
    * 设置字段prem的值，该字段的<br>
    * 字段名称 :保费<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setPrem(String prem) {
        this.prem = prem;
    }

    /**
    * 获取字段orderDate的值，该字段的<br>
    * 字段名称 :下单日期<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
    * 设置字段orderDate的值，该字段的<br>
    * 字段名称 :下单日期<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
