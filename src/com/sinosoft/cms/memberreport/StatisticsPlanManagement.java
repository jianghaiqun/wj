package com.sinosoft.cms.memberreport;

import com.sinosoft.cms.plan.statistics.StatisticsPlanConstant;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.schema.zd_statistics_planSchema;
import com.sinosoft.schema.zd_statistics_plan_recordSchema;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 统计计划管理..
 * Created by dongsheng on 2017/3/21.
 */
public class StatisticsPlanManagement extends Page {

    public static Mapx init(Mapx params) {
        Date now = new Date();
        params.put("startDate", DateFormatUtils.ISO_DATE_FORMAT.format(now));
        params.put("endDate", DateFormatUtils.ISO_DATE_FORMAT.format(now));
        return params;
    }


    public void dg1DataBind(DataGridAction dga) {
        String startDate = dga.getParam("startDate");
        String endDate = dga.getParam("endDate");
        QueryBuilder qb = new QueryBuilder();
        StringBuilder sb = new StringBuilder("select id,plan_name,source_url,execute_datetime,plan_status ,period ,create_datetime,create_user,modify_datetime,modify_user")
                .append(" from zd_statistics_plan p")
                .append(" where Date(p.create_datetime)>=? ")
                .append(" and Date(p.create_datetime)<=? ");
        qb.add(startDate);
        qb.add(endDate);
        String sql = sb.toString();
        qb.setSQL(sql);
        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
        Mapx map = new Mapx();
        map.put("0", "就绪");
        map.put("1", "已开始");
        map.put("-1", "已停用");
        dt.decodeColumn("plan_status", map);
        dt.decodeDateColumn("execute_datetime");
        dga.bindData(dt);

    }

    public void delete() {
        String planIdStr = $V("planIdStr");
        if (StringUtils.isBlank(planIdStr)) return;
        long planId = Long.parseLong(planIdStr);
        zd_statistics_planSchema plan = new zd_statistics_planSchema();
        plan.setid(planId);;
        plan.delete();
        QueryBuilder qb=new QueryBuilder("delete from zd_statistics_plan_record where statistics_plan_id=?");
        qb.add(planId);
        qb.executeNoQuery();
    }
}
