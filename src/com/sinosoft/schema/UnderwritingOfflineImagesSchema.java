package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：线下核保图片信息<br>
 * 表代码：underwriting_offline_images<br>
 * 表主键：id<br>
 */
public class UnderwritingOfflineImagesSchema extends Schema {

    private Integer id;

    private Integer infoId;

    private String imageUrl;

    private String imageSize;

    private String imageDesc;

    private String thumUrl1;

    private String thumUrl2;

    private String operator;

    private Date createTime;

    private Date modifyTime;

    public static final SchemaColumn[] _Columns = new SchemaColumn[] {
        new SchemaColumn("id", DataColumn.INTEGER, 0, 0 , 0 , false, true),
        new SchemaColumn("info_id", DataColumn.INTEGER, 1, 0 , 0 , true, false),
        new SchemaColumn("image_url", DataColumn.STRING, 2, 200 , 0 , true, false),
        new SchemaColumn("image_size", DataColumn.STRING, 3, 20 , 0 , true, false),
        new SchemaColumn("image_desc", DataColumn.STRING, 4, 200 , 0 , false, false),
        new SchemaColumn("thum_url1", DataColumn.STRING, 5, 500 , 0 , false, false),
        new SchemaColumn("thum_url2", DataColumn.STRING, 6, 500 , 0 , false, false),
        new SchemaColumn("operator", DataColumn.STRING, 7, 100 , 0 , false, false),
        new SchemaColumn("create_time", DataColumn.DATETIME, 8, 0 , 0 , true, false),
        new SchemaColumn("modify_time", DataColumn.DATETIME, 9, 0 , 0 , false, false)
    };

    public static final String _TableCode = "underwriting_offline_images";

    public static final String _NameSpace = "com.sinosoft.schema";

    protected static final String _InsertAllSQL = "insert into underwriting_offline_images values(?,?,?,?,?,?,?,?,?,?)";

    protected static final String _UpdateAllSQL = "update underwriting_offline_images set id=?,info_id=?,image_url=?,image_size=?,image_desc=?,thum_url1=?,thum_url2=?,operator=?,create_time=?,modify_time=? where id=?";

    protected static final String _DeleteSQL = "delete from underwriting_offline_images  where id=?";

    protected static final String _FillAllSQL = "select * from underwriting_offline_images  where id=?";

    public UnderwritingOfflineImagesSchema(){
        TableCode = _TableCode;
        NameSpace = _NameSpace;
        Columns = _Columns;
        InsertAllSQL = _InsertAllSQL;
        UpdateAllSQL = _UpdateAllSQL;
        DeleteSQL = _DeleteSQL;
        FillAllSQL = _FillAllSQL;
        HasSetFlag = new boolean[10];
    }

    protected Schema newInstance(){
        return new UnderwritingOfflineImagesSchema();
    }

    protected SchemaSet newSet(){
        return new UnderwritingOfflineImagesSet();
    }

    public UnderwritingOfflineImagesSet query() {
        return query(null, -1, -1);
    }

    public UnderwritingOfflineImagesSet query(QueryBuilder qb) {
        return query(qb, -1, -1);
    }

    public UnderwritingOfflineImagesSet query(int pageSize, int pageIndex) {
        return query(null, pageSize, pageIndex);
    }

    public UnderwritingOfflineImagesSet query(QueryBuilder qb , int pageSize, int pageIndex){
        return (UnderwritingOfflineImagesSet)querySet(qb , pageSize , pageIndex);
    }

    public void setV(int i, Object v) {
        if (i == 0){id = (Integer)v;return;}
        if (i == 1){infoId = (Integer)v;return;}
        if (i == 2){imageUrl = (String)v;return;}
        if (i == 3){imageSize = (String)v;return;}
        if (i == 4){imageDesc = (String)v;return;}
        if (i == 5){thumUrl1 = (String)v;return;}
        if (i == 6){thumUrl2 = (String)v;return;}
        if (i == 7){operator = (String)v;return;}
        if (i == 8){createTime = (Date)v;return;}
        if (i == 9){modifyTime = (Date)v;return;}
    }

    public Object getV(int i) {
        if (i == 0){return id;}
        if (i == 1){return infoId;}
        if (i == 2){return imageUrl;}
        if (i == 3){return imageSize;}
        if (i == 4){return imageDesc;}
        if (i == 5){return thumUrl1;}
        if (i == 6){return thumUrl2;}
        if (i == 7){return operator;}
        if (i == 8){return createTime;}
        if (i == 9){return modifyTime;}
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
    * 获取字段infoId的值，该字段的<br>
    * 字段名称 :线下核保信息id<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public Integer getInfoId() {
        return infoId;
    }

    /**
    * 设置字段infoId的值，该字段的<br>
    * 字段名称 :线下核保信息id<br>
    * 数据类型 :int<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    /**
    * 获取字段imageUrl的值，该字段的<br>
    * 字段名称 :图片地址<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
    * 设置字段imageUrl的值，该字段的<br>
    * 字段名称 :图片地址<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
    * 获取字段imageSize的值，该字段的<br>
    * 字段名称 :图片大小<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getImageSize() {
        return imageSize;
    }

    /**
    * 设置字段imageSize的值，该字段的<br>
    * 字段名称 :图片大小<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    /**
    * 获取字段imageDesc的值，该字段的<br>
    * 字段名称 :图片描述<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getImageDesc() {
        return imageDesc;
    }

    /**
    * 设置字段imageDesc的值，该字段的<br>
    * 字段名称 :图片描述<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    /**
    * 获取字段thumUrl1的值，该字段的<br>
    * 字段名称 :缩略图地址1<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getThumUrl1() {
        return thumUrl1;
    }

    /**
    * 设置字段thumUrl1的值，该字段的<br>
    * 字段名称 :缩略图地址1<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setThumUrl1(String thumUrl1) {
        this.thumUrl1 = thumUrl1;
    }

    /**
    * 获取字段thumUrl2的值，该字段的<br>
    * 字段名称 :缩略图地址2<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getThumUrl2() {
        return thumUrl2;
    }

    /**
    * 设置字段thumUrl2的值，该字段的<br>
    * 字段名称 :缩略图地址2<br>
    * 数据类型 :varchar(500)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setThumUrl2(String thumUrl2) {
        this.thumUrl2 = thumUrl2;
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
}
