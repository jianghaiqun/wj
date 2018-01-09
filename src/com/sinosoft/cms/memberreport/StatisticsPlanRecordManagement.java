package com.sinosoft.cms.memberreport;

import com.sinosoft.cms.plan.statistics.OperateStatisticsPlan;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 统计计划记录管理..
 * Created by dongsheng on 2017/3/21.
 */
public class StatisticsPlanRecordManagement extends Page {

    public static Mapx init(Mapx params) {
        Date now = new Date();
        params.put("startDate", DateFormatUtils.ISO_DATE_FORMAT.format(now));
        params.put("endDate", DateFormatUtils.ISO_DATE_FORMAT.format(now));
        return params;
    }


    public  void dg1DataBind(DataGridAction dga) {
        String startDate = dga.getParam("startDate");
        String endDate = dga.getParam("endDate");
        String createDate = dga.getParam("createDate");
        QueryBuilder qb = new QueryBuilder();
        StringBuilder sb = new StringBuilder("select r.id,r.download_file_name,r.download_file_path,r.download_status,r.query_status,r.start_date,r.end_date,r.create_datetime  rc,p.plan_name,p.plan_status,p.source_url,p.create_user,p.create_datetime pc,TRIM(LEADING '/alidata/kxb' FROM r.download_file_path) download_url")
                .append(" from zd_statistics_plan_record r,zd_statistics_plan p")
                .append(" where r.statistics_plan_id=p.id");
        if (StringUtils.isNotBlank(startDate)) {
            sb.append(" and r.start_date>=?");
            qb.add(startDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            sb.append(" and r.end_date<=?");
            qb.add(endDate);
        }
        if (StringUtils.isNotBlank(createDate)) {
            sb.append(" and Date(r.create_datetime)=?");
            qb.add(createDate);
        }
        String sql = sb.toString();
        qb.setSQL(sql);
        dga.setTotal(qb);
        DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
        Mapx map = new Mapx();
        map.put("0", "默认");
        map.put("1", "成功");
        map.put("-1", "失败");
        dt.decodeColumn("query_status", map);
//        dt.decodeColumn("download_status", map);
        dga.bindData(dt);
    }


    public void reExecuteStatisticsPlan(){
        String recordIdStr = $V("recordIdStr");
        OperateStatisticsPlan operateStatisticsPlan=new OperateStatisticsPlan();
        operateStatisticsPlan.reExecuteStatisticsPlan(recordIdStr);

    }
}
