package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 表名称：淘宝新注册会员统计<br>
 * 表代码：taobaomemberanalysis<br>
 * 表主键：id<br>
 */
public class TaobaoMemberAnalysisSchema extends Schema {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    private String year;

    private String month;

    private Integer registerCount;

    private Integer repurchaseCount;

    private String convertRatio;

    private String repurchasePremium;

    public static final SchemaColumn[] _Columns = new SchemaColumn[] {
        new SchemaColumn("id", DataColumn.LONG, 0, 0 , 0 , false, true),
        new SchemaColumn("year", DataColumn.STRING, 1, 4 , 0 , false, false),
        new SchemaColumn("month", DataColumn.STRING, 2, 2 , 0 , false, false),
        new SchemaColumn("registerCount", DataColumn.INTEGER, 3, 0 , 0 , false, false),
        new SchemaColumn("repurchaseCount", DataColumn.INTEGER, 4, 0 , 0 , false, false),
        new SchemaColumn("convertRatio", DataColumn.STRING, 5, 20 , 0 , false, false),
        new SchemaColumn("repurchasePremium", DataColumn.STRING, 6, 20 , 0 , false, false)
    };

    public static final String _TableCode = "taobaomemberanalysis";

    public static final String _NameSpace = "com.sinosoft.schema";

    protected static final String _InsertAllSQL = "insert into taobaomemberanalysis values(?,?,?,?,?,?,?)";

    protected static final String _UpdateAllSQL = "update taobaomemberanalysis set id=?,year=?,month=?,registerCount=?,repurchaseCount=?,convertRatio=?,repurchasePremium=? where id=?";

    protected static final String _DeleteSQL = "delete from taobaomemberanalysis  where id=?";

    protected static final String _FillAllSQL = "select * from taobaomemberanalysis  where id=?";

    public TaobaoMemberAnalysisSchema(){
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
        return new TaobaoMemberAnalysisSchema();
    }

    protected SchemaSet newSet(){
        return new TaobaoMemberAnalysisSet();
    }

    public TaobaoMemberAnalysisSet query() {
        return query(null, -1, -1);
    }

    public TaobaoMemberAnalysisSet query(QueryBuilder qb) {
        return query(qb, -1, -1);
    }

    public TaobaoMemberAnalysisSet query(int pageSize, int pageIndex) {
        return query(null, pageSize, pageIndex);
    }

    public TaobaoMemberAnalysisSet query(QueryBuilder qb , int pageSize, int pageIndex){
        return (TaobaoMemberAnalysisSet)querySet(qb , pageSize , pageIndex);
    }

    public void setV(int i, Object v) {
        if (i == 0){id = (Long)v;return;}
        if (i == 1){year = (String)v;return;}
        if (i == 2){month = (String)v;return;}
        if (i == 3){registerCount = (Integer)v;return;}
        if (i == 4){repurchaseCount = (Integer)v;return;}
        if (i == 5){convertRatio = (String)v;return;}
        if (i == 6){repurchasePremium = (String)v;return;}
    }

    public Object getV(int i) {
        if (i == 0){return id;}
        if (i == 1){return year;}
        if (i == 2){return month;}
        if (i == 3){return registerCount;}
        if (i == 4){return repurchaseCount;}
        if (i == 5){return convertRatio;}
        if (i == 6){return repurchasePremium;}
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
    * 获取字段year的值，该字段的<br>
    * 字段名称 :年份<br>
    * 数据类型 :varchar(4)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getYear() {
        return year;
    }

    /**
    * 设置字段year的值，该字段的<br>
    * 字段名称 :年份<br>
    * 数据类型 :varchar(4)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setYear(String year) {
        this.year = year;
    }

    /**
    * 获取字段month的值，该字段的<br>
    * 字段名称 :月份<br>
    * 数据类型 :varchar(2)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getMonth() {
        return month;
    }

    /**
    * 设置字段month的值，该字段的<br>
    * 字段名称 :月份<br>
    * 数据类型 :varchar(2)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
    * 获取字段registerCount的值，该字段的<br>
    * 字段名称 :注册会员数<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public Integer getRegisterCount() {
        return registerCount;
    }

    /**
    * 设置字段registerCount的值，该字段的<br>
    * 字段名称 :注册会员数<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setRegisterCount(Integer registerCount) {
        this.registerCount = registerCount;
    }

    /**
    * 获取字段repurchaseCount的值，该字段的<br>
    * 字段名称 :其他渠道复购数<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public Integer getRepurchaseCount() {
        return repurchaseCount;
    }

    /**
    * 设置字段repurchaseCount的值，该字段的<br>
    * 字段名称 :其他渠道复购数<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setRepurchaseCount(Integer repurchaseCount) {
        this.repurchaseCount = repurchaseCount;
    }

    /**
    * 获取字段convertRatio的值，该字段的<br>
    * 字段名称 :转化率<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getConvertRatio() {
        return convertRatio;
    }

    /**
    * 设置字段convertRatio的值，该字段的<br>
    * 字段名称 :转化率<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setConvertRatio(String convertRatio) {
        this.convertRatio = convertRatio;
    }

    /**
    * 获取字段repurchasePremium的值，该字段的<br>
    * 字段名称 :其他渠道产品保费<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getRepurchasePremium() {
        return repurchasePremium;
    }

    /**
    * 设置字段repurchasePremium的值，该字段的<br>
    * 字段名称 :其他渠道产品保费<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setRepurchasePremium(String repurchasePremium) {
        this.repurchasePremium = repurchasePremium;
    }
}
