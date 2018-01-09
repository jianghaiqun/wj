package com.sinosoft.cms.dataservice;

import com.sinosoft.cms.plan.statistics.FieldNameMatchable;
import com.sinosoft.cms.plan.statistics.FieldsNameMatcher;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;
import com.wangjin.infoseeker.QueryUtil;

/**
 * ClassName: CustomerAddStatisticNew <br/>
 * Function: 客户新增数量. <br/>
 * date: 2016年4月25日 下午2:34:59 <br/>
 *
 * @author wwy
 */
public class CustomerAddStatisticNew extends Page implements FieldNameMatchable {

    /**
     * dg1DataBind:客户新增数量查询. <br/>
     *
     * @param dga
     * @author wwy
     */
    public static void dg1DataBind(DataGridAction dga) {

        String beginDate = dga.getParams()
                .getString("startDate") + " 00:00:00";
        String endDate = dga.getParams()
                .getString("endDate") + " 23:59:59";
        String from = dga.getParams()
                .getString("From");
        int fromValue = Integer.parseInt(from);
        String Wedo = dga.getParams()
                .getString("Wedo");
        String channelsn = dga.getParam("contant");

        if (StringUtil.isNotEmpty(channelsn) && ((channelsn.indexOf("xb2b_ht") > 0)
                || (channelsn.indexOf("xb2c_pc") > 0) || (channelsn.indexOf("xb2c_wap") > 0))) {
            channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht")
                    .replaceAll("xb2c_pc", "b2c_pc")
                    .replaceAll("xb2c_wap", "b2c_wap");
        }

        //查询维度:id,phone,email.
        String sqlOfApplicantDimension, sqlOfRecognizeDimension;
        QueryBuilder qb = new QueryBuilder();
        if ("id".equals(Wedo)) {
            sqlOfApplicantDimension = "applicantIdentityId";
            sqlOfRecognizeDimension = "recognizeeIdentityId";
        } else if ("phone".equals(Wedo)) {
            sqlOfApplicantDimension = "applicantMobile";
            sqlOfRecognizeDimension = "recognizeeMobile";
        } else {
            sqlOfApplicantDimension = "applicantMail";
            sqlOfRecognizeDimension = "recognizeeMail";
        }

        qb.append("SELECT COUNT(1) AS count, IFNULL((SELECT i.channelName FROM ChannelInfo i WHERE i.channelcode = a.channelsn), '无') AS channalName, a.channelsn\n");
        qb.append(" FROM (\n");

        switch (fromValue) {
            case 3:
            case 1:
                String sqlOfApplicant = new StringBuilder().append("SELECT\n")
                        .append(sqlOfApplicantDimension)
                        .append(" Wedo\n")
                        .append(",channelSn as channelsn\n")
                        .append("FROM DataPrecipitationOrder\n")
                        .append("WHERE orderStatus >= 7 AND orderStatus != 8\n")
                        .append("GROUP BY\n")
                        .append(sqlOfApplicantDimension)
                        .append(" HAVING MIN(receiveDate) >= ?\n")
                        .append("       AND MIN(receiveDate) <= ? \n")
                        .toString();
                qb.append(sqlOfApplicant);
                qb.add(beginDate);
                qb.add(endDate);
            case Integer.MIN_VALUE:
                if (fromValue == 1) {
                    break;
                } else {
                    qb.append(" UNION \n");
                }
            case 2:
                String sqlOfRecognize = new StringBuilder().append("SELECT\n")
                        .append(sqlOfRecognizeDimension)
                        .append(" Wedo\n")
                        .append(",channelSn as channelsn\n")
                        .append("FROM DataPrecipitationOrder\n")
                        .append("WHERE orderStatus >= 7 AND orderStatus != 8\n")
                        .append("GROUP BY\n")
                        .append(sqlOfRecognizeDimension)
                        .append(" HAVING MIN(receiveDate) >=?\n")
                        .append("       AND MIN(receiveDate) <= ? \n")
                        .toString();
                qb.append(sqlOfRecognize);
                qb.add(beginDate);
                qb.add(endDate);
                break;
            default:
                break;
        }
        qb.append(" ) a WHERE 1 = 1");
        if (StringUtil.isNotEmpty(channelsn)) {
            String channel = QueryUtil.getChannelInfo(channelsn, null);
            qb.append(" and EXISTS (SELECT 1 FROM ChannelInfo WHERE ChannelCode = a.channelsn and ChannelCode IN (" + channel + "))");
        }
        qb.append(" GROUP BY a.channelsn ");
//        System.out.println("客户增量统计：" + qb.getSQL());
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
        dga.setTotal(qb);
        dga.bindData(dt);
    }

    public static void dg2DataBind(DataGridAction dga) {

        String beginDate = dga.getParams()
                .getString("startDate") + " 00:00:00";
        String endDate = dga.getParams()
                .getString("endDate") + " 23:59:59";
        String from = dga.getParams()
                .getString("From");
        int fromValue = Integer.parseInt(from);
        String Wedo = dga.getParams()
                .getString("Wedo");
        String channelsn = dga.getParam("contant");

        if (StringUtil.isNotEmpty(channelsn) && ((channelsn.indexOf("xb2b_ht") > 0)
                || (channelsn.indexOf("xb2c_pc") > 0) || (channelsn.indexOf("xb2c_wap") > 0))) {
            channelsn = channelsn.replaceAll("xb2b_ht", "b2b_ht")
                    .replaceAll("xb2c_pc", "b2c_pc")
                    .replaceAll("xb2c_wap", "b2c_wap");
        }

        //查询维度:id,phone,email.
        String sqlOfApplicantDimension, sqlOfRecognizeDimension, sqlOfCommonDimension;
        QueryBuilder qb = new QueryBuilder();
        if ("id".equals(Wedo)) {
            sqlOfApplicantDimension = "applicantIdentityId";
            sqlOfRecognizeDimension = "recognizeeIdentityId";
            sqlOfCommonDimension = "IdentityId";
        } else if ("phone".equals(Wedo)) {
            sqlOfApplicantDimension = "applicantMobile";
            sqlOfRecognizeDimension = "recognizeeMobile";
            sqlOfCommonDimension = "Mobile";
        } else {
            sqlOfApplicantDimension = "applicantMail";
            sqlOfRecognizeDimension = "recognizeeMail";
            sqlOfCommonDimension = "Mail";
        }
        qb.append("SELECT * FROM (\n");
        switch (fromValue) {
            case 3:
            case 1:
                String sqlOfApplicant = new StringBuilder().append("SELECT\n")
                        .append("  orderSn ordersn,\n")
                        .append("  applicantName NAME,\n")
                        .append("  applicantIdentityTypeName IdentityTypeName,\n")
                        .append("  applicantIdentityId IdentityId,\n")
                        .append("  applicantMail Mail,\n")
                        .append("  applicantMobile Mobile,\n")
                        .append("  applicantCreateDate createDate,\n")
                        .append("  channelSn\n")
                        .append("  FROM DataPrecipitationOrder\n")
                        .append("  WHERE orderStatus >= 7 AND orderStatus <> 8\n")
                        .append("  GROUP BY ")
                        .append(sqlOfApplicantDimension)
                        .append("  HAVING\n")
                        .append("  MIN(receiveDate) <= ?\n")
                        .append("  AND MIN(receiveDate) >= ?\n")
                        .append("  AND channelSn =?")
                        .toString();
                qb.append(sqlOfApplicant);
                qb.add(endDate);
                qb.add(beginDate);
                qb.add(channelsn);
            case Integer.MIN_VALUE:
                if (fromValue == 1) {
                    break;
                } else {
                    qb.append(" UNION \n");
                }
            case 2:
                String sqlOfRecognize = new StringBuilder().append("SELECT\n")
                        .append("  orderSn ordersn,\n")
                        .append("  recognizeeName NAME,\n")
                        .append("  recognizeeIdentityTypeName IdentityTypeName,\n")
                        .append("  recognizeeIdentityId IdentityId,\n")
                        .append("  recognizeeMail Mail,\n")
                        .append("  recognizeeMobile Mobile,\n")
                        .append("  recognizeeCreateDate createDate,\n")
                        .append("  channelSn\n")
                        .append("FROM DataPrecipitationOrder")
                        .append("  WHERE orderStatus >= 7 AND orderStatus <> 8\n")
                        .append("  GROUP BY ")
                        .append(sqlOfRecognizeDimension)
                        .append("  HAVING\n")
                        .append("  MIN(receiveDate) <= ?\n")
                        .append("  AND MIN(receiveDate) >= ?\n")
                        .append("  AND channelSn =?")
                        .toString();
                qb.append(sqlOfRecognize);
                qb.add(endDate);
                qb.add(beginDate);
                qb.add(channelsn);
                break;
            default:
                break;
        }
        qb.append(") a GROUP BY ")
                .append(sqlOfCommonDimension)
                .append(" ORDER BY createDate DESC ");
        //System.out.println("客户增量详细：" + query_number.getSQL());
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
        dga.setTotal(qb);
        dga.bindData(dt);
    }


    public static Mapx init(Mapx params) {
        params.put("createDate", PubFun.getCurrentDate());
        params.put("endCreateDate", PubFun.getCurrentDate());
        return params;
    }

    @Override
    public FieldsNameMatcher fieldNameMatch(FieldsNameMatcher matcher) {
        return matcher.setStartDatetime("startDate")
                .setEndDatetime("endDate");
    }
}