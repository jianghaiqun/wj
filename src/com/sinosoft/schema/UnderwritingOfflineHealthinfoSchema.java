package com.sinosoft.schema;

import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.orm.SchemaColumn;
import com.sinosoft.framework.data.QueryBuilder;
import java.util.Date;

/**
 * 表名称：线下核保健康信息<br>
 * 表代码：underwriting_offline_healthinfo<br>
 * 表主键：id<br>
 */
public class UnderwritingOfflineHealthinfoSchema extends Schema {

    private Integer id;

    private Integer infoId;

    private String offlineCode;

    private String orderSn;

    private String sex;

    private String age;

    private String birthday;

    private String IdType;

    private String IdNo;

    private String height;

    private String weight;

    private String firsOnsetTime;

    private String mainSymptoms;

    private String diseaseName;

    private String attackDate;

    private String attackFrequency;

    private String attackLastDate;

    private String isTreat;

    private String stopTreat;

    private String treatSurgery;

    private String treatDrug;

    private String treatPhysical;

    private String treatOther;

    private String treatEffect;

    private String otherSupplement;

    private String prop1;

    private String prop2;

    public static final SchemaColumn[] _Columns = new SchemaColumn[] {
        new SchemaColumn("id", DataColumn.INTEGER, 0, 0 , 0 , false, true),
        new SchemaColumn("info_id", DataColumn.INTEGER, 1, 0 , 0 , true, false),
        new SchemaColumn("offlineCode", DataColumn.STRING, 2, 10 , 0 , false, false),
        new SchemaColumn("orderSn", DataColumn.STRING, 3, 20 , 0 , false, false),
        new SchemaColumn("sex", DataColumn.STRING, 4, 5 , 0 , true, false),
        new SchemaColumn("age", DataColumn.STRING, 5, 5 , 0 , true, false),
        new SchemaColumn("birthday", DataColumn.STRING, 6, 12 , 0 , false, false),
        new SchemaColumn("IdType", DataColumn.STRING, 7, 5 , 0 , false, false),
        new SchemaColumn("IdNo", DataColumn.STRING, 8, 20 , 0 , false, false),
        new SchemaColumn("height", DataColumn.STRING, 9, 5 , 0 , false, false),
        new SchemaColumn("weight", DataColumn.STRING, 10, 5 , 0 , false, false),
        new SchemaColumn("firsOnsetTime", DataColumn.STRING, 11, 12 , 0 , false, false),
        new SchemaColumn("mainSymptoms", DataColumn.STRING, 12, 400 , 0 , false, false),
        new SchemaColumn("diseaseName", DataColumn.STRING, 13, 200 , 0 , false, false),
        new SchemaColumn("attackDate", DataColumn.STRING, 14, 20 , 0 , false, false),
        new SchemaColumn("attackFrequency", DataColumn.STRING, 15, 20 , 0 , false, false),
        new SchemaColumn("attackLastDate", DataColumn.STRING, 16, 20 , 0 , false, false),
        new SchemaColumn("isTreat", DataColumn.STRING, 17, 5 , 0 , true, false),
        new SchemaColumn("stopTreat", DataColumn.STRING, 18, 12 , 0 , false, false),
        new SchemaColumn("treatSurgery", DataColumn.STRING, 19, 50 , 0 , false, false),
        new SchemaColumn("treatDrug", DataColumn.STRING, 20, 50 , 0 , false, false),
        new SchemaColumn("treatPhysical", DataColumn.STRING, 21, 50 , 0 , false, false),
        new SchemaColumn("treatOther", DataColumn.STRING, 22, 50 , 0 , false, false),
        new SchemaColumn("treatEffect", DataColumn.STRING, 23, 5 , 0 , false, false),
        new SchemaColumn("otherSupplement", DataColumn.STRING, 24, 50 , 0 , false, false),
        new SchemaColumn("prop1", DataColumn.STRING, 25, 20 , 0 , false, false),
        new SchemaColumn("prop2", DataColumn.STRING, 26, 20 , 0 , false, false)
    };

    public static final String _TableCode = "underwriting_offline_healthinfo";

    public static final String _NameSpace = "com.sinosoft.schema";

    protected static final String _InsertAllSQL = "insert into underwriting_offline_healthinfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    protected static final String _UpdateAllSQL = "update underwriting_offline_healthinfo set id=?,info_id=?,offlineCode=?,orderSn=?,sex=?,age=?,birthday=?,IdType=?,IdNo=?,height=?,weight=?,firsOnsetTime=?,mainSymptoms=?,diseaseName=?,attackDate=?,attackFrequency=?,attackLastDate=?,isTreat=?,stopTreat=?,treatSurgery=?,treatDrug=?,treatPhysical=?,treatOther=?,treatEffect=?,otherSupplement=?,prop1=?,prop2=? where id=?";

    protected static final String _DeleteSQL = "delete from underwriting_offline_healthinfo  where id=?";

    protected static final String _FillAllSQL = "select * from underwriting_offline_healthinfo  where id=?";

    public UnderwritingOfflineHealthinfoSchema(){
        TableCode = _TableCode;
        NameSpace = _NameSpace;
        Columns = _Columns;
        InsertAllSQL = _InsertAllSQL;
        UpdateAllSQL = _UpdateAllSQL;
        DeleteSQL = _DeleteSQL;
        FillAllSQL = _FillAllSQL;
        HasSetFlag = new boolean[27];
    }

    protected Schema newInstance(){
        return new UnderwritingOfflineHealthinfoSchema();
    }

    protected SchemaSet newSet(){
        return new UnderwritingOfflineHealthinfoSet();
    }

    public UnderwritingOfflineHealthinfoSet query() {
        return query(null, -1, -1);
    }

    public UnderwritingOfflineHealthinfoSet query(QueryBuilder qb) {
        return query(qb, -1, -1);
    }

    public UnderwritingOfflineHealthinfoSet query(int pageSize, int pageIndex) {
        return query(null, pageSize, pageIndex);
    }

    public UnderwritingOfflineHealthinfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
        return (UnderwritingOfflineHealthinfoSet)querySet(qb , pageSize , pageIndex);
    }

    public void setV(int i, Object v) {
        if (i == 0){id = (Integer)v;return;}
        if (i == 1){infoId = (Integer)v;return;}
        if (i == 2){offlineCode = (String)v;return;}
        if (i == 3){orderSn = (String)v;return;}
        if (i == 4){sex = (String)v;return;}
        if (i == 5){age = (String)v;return;}
        if (i == 6){birthday = (String)v;return;}
        if (i == 7){IdType = (String)v;return;}
        if (i == 8){IdNo = (String)v;return;}
        if (i == 9){height = (String)v;return;}
        if (i == 10){weight = (String)v;return;}
        if (i == 11){firsOnsetTime = (String)v;return;}
        if (i == 12){mainSymptoms = (String)v;return;}
        if (i == 13){diseaseName = (String)v;return;}
        if (i == 14){attackDate = (String)v;return;}
        if (i == 15){attackFrequency = (String)v;return;}
        if (i == 16){attackLastDate = (String)v;return;}
        if (i == 17){isTreat = (String)v;return;}
        if (i == 18){stopTreat = (String)v;return;}
        if (i == 19){treatSurgery = (String)v;return;}
        if (i == 20){treatDrug = (String)v;return;}
        if (i == 21){treatPhysical = (String)v;return;}
        if (i == 22){treatOther = (String)v;return;}
        if (i == 23){treatEffect = (String)v;return;}
        if (i == 24){otherSupplement = (String)v;return;}
        if (i == 25){prop1 = (String)v;return;}
        if (i == 26){prop2 = (String)v;return;}
    }

    public Object getV(int i) {
        if (i == 0){return id;}
        if (i == 1){return infoId;}
        if (i == 2){return offlineCode;}
        if (i == 3){return orderSn;}
        if (i == 4){return sex;}
        if (i == 5){return age;}
        if (i == 6){return birthday;}
        if (i == 7){return IdType;}
        if (i == 8){return IdNo;}
        if (i == 9){return height;}
        if (i == 10){return weight;}
        if (i == 11){return firsOnsetTime;}
        if (i == 12){return mainSymptoms;}
        if (i == 13){return diseaseName;}
        if (i == 14){return attackDate;}
        if (i == 15){return attackFrequency;}
        if (i == 16){return attackLastDate;}
        if (i == 17){return isTreat;}
        if (i == 18){return stopTreat;}
        if (i == 19){return treatSurgery;}
        if (i == 20){return treatDrug;}
        if (i == 21){return treatPhysical;}
        if (i == 22){return treatOther;}
        if (i == 23){return treatEffect;}
        if (i == 24){return otherSupplement;}
        if (i == 25){return prop1;}
        if (i == 26){return prop2;}
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
    * 获取字段offlineCode的值，该字段的<br>
    * 字段名称 :线下核保编码<br>
    * 数据类型 :varchar(10)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getOfflineCode() {
        return offlineCode;
    }

    /**
    * 设置字段offlineCode的值，该字段的<br>
    * 字段名称 :线下核保编码<br>
    * 数据类型 :varchar(10)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setOfflineCode(String offlineCode) {
        this.offlineCode = offlineCode;
    }

    /**
    * 获取字段orderSn的值，该字段的<br>
    * 字段名称 :订单号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getOrderSn() {
        return orderSn;
    }

    /**
    * 设置字段orderSn的值，该字段的<br>
    * 字段名称 :订单号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
    * 获取字段sex的值，该字段的<br>
    * 字段名称 :性别<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getSex() {
        return sex;
    }

    /**
    * 设置字段sex的值，该字段的<br>
    * 字段名称 :性别<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
    * 获取字段age的值，该字段的<br>
    * 字段名称 :年龄<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getAge() {
        return age;
    }

    /**
    * 设置字段age的值，该字段的<br>
    * 字段名称 :年龄<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setAge(String age) {
        this.age = age;
    }

    /**
    * 获取字段birthday的值，该字段的<br>
    * 字段名称 :生日<br>
    * 数据类型 :varchar(12)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getBirthday() {
        return birthday;
    }

    /**
    * 设置字段birthday的值，该字段的<br>
    * 字段名称 :生日<br>
    * 数据类型 :varchar(12)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
    * 获取字段IdType的值，该字段的<br>
    * 字段名称 :证件类型<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getIdType() {
        return IdType;
    }

    /**
    * 设置字段IdType的值，该字段的<br>
    * 字段名称 :证件类型<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setIdType(String IdType) {
        this.IdType = IdType;
    }

    /**
    * 获取字段IdNo的值，该字段的<br>
    * 字段名称 :证件号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getIdNo() {
        return IdNo;
    }

    /**
    * 设置字段IdNo的值，该字段的<br>
    * 字段名称 :证件号<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setIdNo(String IdNo) {
        this.IdNo = IdNo;
    }

    /**
    * 获取字段height的值，该字段的<br>
    * 字段名称 :身高<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getHeight() {
        return height;
    }

    /**
    * 设置字段height的值，该字段的<br>
    * 字段名称 :身高<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
    * 获取字段weight的值，该字段的<br>
    * 字段名称 :体重<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getWeight() {
        return weight;
    }

    /**
    * 设置字段weight的值，该字段的<br>
    * 字段名称 :体重<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
    * 获取字段firsOnsetTime的值，该字段的<br>
    * 字段名称 :首次发病时间<br>
    * 数据类型 :varchar(12)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getFirsOnsetTime() {
        return firsOnsetTime;
    }

    /**
    * 设置字段firsOnsetTime的值，该字段的<br>
    * 字段名称 :首次发病时间<br>
    * 数据类型 :varchar(12)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setFirsOnsetTime(String firsOnsetTime) {
        this.firsOnsetTime = firsOnsetTime;
    }

    /**
    * 获取字段mainSymptoms的值，该字段的<br>
    * 字段名称 :主要症状<br>
    * 数据类型 :varchar(400)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getMainSymptoms() {
        return mainSymptoms;
    }

    /**
    * 设置字段mainSymptoms的值，该字段的<br>
    * 字段名称 :主要症状<br>
    * 数据类型 :varchar(400)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setMainSymptoms(String mainSymptoms) {
        this.mainSymptoms = mainSymptoms;
    }

    /**
    * 获取字段diseaseName的值，该字段的<br>
    * 字段名称 :医院诊断的疾病名称<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getDiseaseName() {
        return diseaseName;
    }

    /**
    * 设置字段diseaseName的值，该字段的<br>
    * 字段名称 :医院诊断的疾病名称<br>
    * 数据类型 :varchar(200)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    /**
    * 获取字段attackDate的值，该字段的<br>
    * 字段名称 :发作持续时间<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getAttackDate() {
        return attackDate;
    }

    /**
    * 设置字段attackDate的值，该字段的<br>
    * 字段名称 :发作持续时间<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setAttackDate(String attackDate) {
        this.attackDate = attackDate;
    }

    /**
    * 获取字段attackFrequency的值，该字段的<br>
    * 字段名称 :发作次数或频率<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getAttackFrequency() {
        return attackFrequency;
    }

    /**
    * 设置字段attackFrequency的值，该字段的<br>
    * 字段名称 :发作次数或频率<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setAttackFrequency(String attackFrequency) {
        this.attackFrequency = attackFrequency;
    }

    /**
    * 获取字段attackLastDate的值，该字段的<br>
    * 字段名称 :最近一次发作时间<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getAttackLastDate() {
        return attackLastDate;
    }

    /**
    * 设置字段attackLastDate的值，该字段的<br>
    * 字段名称 :最近一次发作时间<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setAttackLastDate(String attackLastDate) {
        this.attackLastDate = attackLastDate;
    }

    /**
    * 获取字段isTreat的值，该字段的<br>
    * 字段名称 :目前是否仍接受治疗<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public String getIsTreat() {
        return isTreat;
    }

    /**
    * 设置字段isTreat的值，该字段的<br>
    * 字段名称 :目前是否仍接受治疗<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :true<br>
    */
    public void setIsTreat(String isTreat) {
        this.isTreat = isTreat;
    }

    /**
    * 获取字段stopTreat的值，该字段的<br>
    * 字段名称 :停止治疗的时间<br>
    * 数据类型 :varchar(12)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getStopTreat() {
        return stopTreat;
    }

    /**
    * 设置字段stopTreat的值，该字段的<br>
    * 字段名称 :停止治疗的时间<br>
    * 数据类型 :varchar(12)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setStopTreat(String stopTreat) {
        this.stopTreat = stopTreat;
    }

    /**
    * 获取字段treatSurgery的值，该字段的<br>
    * 字段名称 :治疗的方法_手术名称<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getTreatSurgery() {
        return treatSurgery;
    }

    /**
    * 设置字段treatSurgery的值，该字段的<br>
    * 字段名称 :治疗的方法_手术名称<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setTreatSurgery(String treatSurgery) {
        this.treatSurgery = treatSurgery;
    }

    /**
    * 获取字段treatDrug的值，该字段的<br>
    * 字段名称 :治疗的方法_药物名称<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getTreatDrug() {
        return treatDrug;
    }

    /**
    * 设置字段treatDrug的值，该字段的<br>
    * 字段名称 :治疗的方法_药物名称<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setTreatDrug(String treatDrug) {
        this.treatDrug = treatDrug;
    }

    /**
    * 获取字段treatPhysical的值，该字段的<br>
    * 字段名称 :治疗的方法_物理名称<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getTreatPhysical() {
        return treatPhysical;
    }

    /**
    * 设置字段treatPhysical的值，该字段的<br>
    * 字段名称 :治疗的方法_物理名称<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setTreatPhysical(String treatPhysical) {
        this.treatPhysical = treatPhysical;
    }

    /**
    * 获取字段treatOther的值，该字段的<br>
    * 字段名称 :治疗的方法_其他名称<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getTreatOther() {
        return treatOther;
    }

    /**
    * 设置字段treatOther的值，该字段的<br>
    * 字段名称 :治疗的方法_其他名称<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setTreatOther(String treatOther) {
        this.treatOther = treatOther;
    }

    /**
    * 获取字段treatEffect的值，该字段的<br>
    * 字段名称 :治疗效果<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getTreatEffect() {
        return treatEffect;
    }

    /**
    * 设置字段treatEffect的值，该字段的<br>
    * 字段名称 :治疗效果<br>
    * 数据类型 :varchar(5)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setTreatEffect(String treatEffect) {
        this.treatEffect = treatEffect;
    }

    /**
    * 获取字段otherSupplement的值，该字段的<br>
    * 字段名称 :其他补充说明<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getOtherSupplement() {
        return otherSupplement;
    }

    /**
    * 设置字段otherSupplement的值，该字段的<br>
    * 字段名称 :其他补充说明<br>
    * 数据类型 :varchar(50)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setOtherSupplement(String otherSupplement) {
        this.otherSupplement = otherSupplement;
    }

    /**
    * 获取字段prop1的值，该字段的<br>
    * 字段名称 :备用1<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp1() {
        return prop1;
    }

    /**
    * 设置字段prop1的值，该字段的<br>
    * 字段名称 :备用1<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    /**
    * 获取字段prop2的值，该字段的<br>
    * 字段名称 :备用2<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public String getProp2() {
        return prop2;
    }

    /**
    * 设置字段prop2的值，该字段的<br>
    * 字段名称 :备用2<br>
    * 数据类型 :varchar(20)<br>
    * 是否主键 :false<br>
    * 是否必填 :false<br>
    */
    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }
}
