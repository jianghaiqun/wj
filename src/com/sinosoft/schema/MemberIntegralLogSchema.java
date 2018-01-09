package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：积分操作日志<br>
 * 表代码：memberintegrallog<br>
 * 表主键：id<br>
 */
public class MemberIntegralLogSchema extends Schema {

    private Long id;

    private String memberId;

    private Integer oldValidIntegral;

    private Integer revenueIntegral;

    private Integer payoutIntegral;

    private Integer nowValidIntegral;

    private Date modifyDate;

    public static final SchemaColumn[] _Columns = new SchemaColumn[] {
        new SchemaColumn("id", DataColumn.LONG, 0, 0 , 0 , false, true),
        new SchemaColumn("memberId", DataColumn.STRING, 1, 32 , 0 , true, false),
        new SchemaColumn("oldValidIntegral", DataColumn.INTEGER, 2, 0 , 0 , true, false),
        new SchemaColumn("revenueIntegral", DataColumn.INTEGER, 3, 0 , 0 , true, false),
        new SchemaColumn("payoutIntegral", DataColumn.INTEGER, 4, 0 , 0 , false, false),
        new SchemaColumn("nowValidIntegral", DataColumn.INTEGER, 5, 0 , 0 , true, false),
        new SchemaColumn("modifyDate", DataColumn.DATETIME, 6, 0 , 0 , true, false)
    };

    public static final String _TableCode = "memberintegrallog";

    public static final String _NameSpace = "com.sinosoft.schema";

    protected static final String _InsertAllSQL = "insert into memberintegrallog values(?,?,?,?,?,?,?)";

    protected static final String _UpdateAllSQL = "update memberintegrallog set id=?,memberId=?,oldValidIntegral=?,revenueIntegral=?,payoutIntegral=?,nowValidIntegral=?,modifyDate=? where id=?";

    protected static final String _DeleteSQL = "delete from memberintegrallog  where id=?";

    protected static final String _FillAllSQL = "select * from memberintegrallog  where id=?";

    public MemberIntegralLogSchema(){
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
        return new MemberIntegralLogSchema();
    }

    protected SchemaSet newSet(){
        return new MemberIntegralLogSet();
    }

    public MemberIntegralLogSet query() {
        return query(null, -1, -1);
    }

    public MemberIntegralLogSet query(QueryBuilder qb) {
        return query(qb, -1, -1);
    }

    public MemberIntegralLogSet query(int pageSize, int pageIndex) {
        return query(null, pageSize, pageIndex);
    }

    public MemberIntegralLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
        return (MemberIntegralLogSet)querySet(qb , pageSize , pageIndex);
    }

    public void setV(int i, Object v) {
        if (i == 0){id = (Long)v;return;}
        if (i == 1){memberId = (String)v;return;}
        if (i == 2){oldValidIntegral = (Integer)v;return;}
        if (i == 3){revenueIntegral = (Integer)v;return;}
        if (i == 4){payoutIntegral = (Integer)v;return;}
        if (i == 5){nowValidIntegral = (Integer)v;return;}
        if (i == 6){modifyDate = (Date)v;return;}
    }

    public Object getV(int i) {
        if (i == 0){return id;}
        if (i == 1){return memberId;}
        if (i == 2){return oldValidIntegral;}
        if (i == 3){return revenueIntegral;}
        if (i == 4){return payoutIntegral;}
        if (i == 5){return nowValidIntegral;}
        if (i == 6){return modifyDate;}
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
    * 获取字段memberId的值，该字段的<br>
    * 字段名称 :会员id<br>
    * 数据类型 :varchar(32)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getMemberId() {
        return memberId;
    }

    /**
    * 设置字段memberId的值，该字段的<br>
    * 字段名称 :会员id<br>
    * 数据类型 :varchar(32)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
    * 获取字段oldValidIntegral的值，该字段的<br>
    * 字段名称 :原可用积分<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public Integer getOldValidIntegral() {
        return oldValidIntegral;
    }

    /**
    * 设置字段oldValidIntegral的值，该字段的<br>
    * 字段名称 :原可用积分<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setOldValidIntegral(Integer oldValidIntegral) {
        this.oldValidIntegral = oldValidIntegral;
    }

    /**
    * 获取字段revenueIntegral的值，该字段的<br>
    * 字段名称 :收入总积分<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public Integer getRevenueIntegral() {
        return revenueIntegral;
    }

    /**
    * 设置字段revenueIntegral的值，该字段的<br>
    * 字段名称 :收入总积分<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setRevenueIntegral(Integer revenueIntegral) {
        this.revenueIntegral = revenueIntegral;
    }

    /**
    * 获取字段payoutIntegral的值，该字段的<br>
    * 字段名称 :支出总积分<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public Integer getPayoutIntegral() {
        return payoutIntegral;
    }

    /**
    * 设置字段payoutIntegral的值，该字段的<br>
    * 字段名称 :支出总积分<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setPayoutIntegral(Integer payoutIntegral) {
        this.payoutIntegral = payoutIntegral;
    }

    /**
    * 获取字段nowValidIntegral的值，该字段的<br>
    * 字段名称 :更改后可用积分<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public Integer getNowValidIntegral() {
        return nowValidIntegral;
    }

    /**
    * 设置字段nowValidIntegral的值，该字段的<br>
    * 字段名称 :更改后可用积分<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setNowValidIntegral(Integer nowValidIntegral) {
        this.nowValidIntegral = nowValidIntegral;
    }

    /**
    * 获取字段modifyDate的值，该字段的<br>
    * 字段名称 :更改时间<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
    * 设置字段modifyDate的值，该字段的<br>
    * 字段名称 :更改时间<br>
    * 数据类型 :datetime<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
