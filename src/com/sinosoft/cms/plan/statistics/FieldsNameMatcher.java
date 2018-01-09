package com.sinosoft.cms.plan.statistics;

import java.io.Serializable;

/**
 * 属性名称匹配类
 * 属性值为实际使用的参数key.
 * Created by dongsheng on 2017/3/23.
 */
public class FieldsNameMatcher implements Serializable {
    private String createdDatetime;
    private String modifiedDatetime;
    private String startDatetime;
    private String endDatetime;
    private String channelSn;
    private String channelSns;
    private String type;
    private String prefix;

    public String getChannelSns() {
        return channelSns;
    }

    public FieldsNameMatcher setChannelSns(String channelSns) {
        this.channelSns = channelSns;
        return this;
    }

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public FieldsNameMatcher setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
        return this;
    }

    public String getModifiedDatetime() {
        return modifiedDatetime;
    }

    public FieldsNameMatcher setModifiedDatetime(String modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
        return this;
    }

    public String getStartDatetime() {
        return startDatetime;
    }

    public FieldsNameMatcher setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
        return this;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public FieldsNameMatcher setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
        return this;
    }

    public String getChannelSn() {
        return channelSn;
    }

    public FieldsNameMatcher setChannelSn(String channelSn) {
        this.channelSn = channelSn;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public FieldsNameMatcher setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getType() {
        return type;
    }

    public FieldsNameMatcher setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "FieldsNameMatcher{" +
                "createdDatetime='" + createdDatetime + '\'' +
                ", modifiedDatetime='" + modifiedDatetime + '\'' +
                ", startDatetime='" + startDatetime + '\'' +
                ", endDatetime='" + endDatetime + '\'' +
                ", channelSn='" + channelSn + '\'' +
                ", channelSns='" + channelSns + '\'' +
                ", type='" + type + '\'' +
                ", prefix='" + prefix + '\'' +
                '}';
    }
}
