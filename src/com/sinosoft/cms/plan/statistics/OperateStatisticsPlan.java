package com.sinosoft.cms.plan.statistics;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.HtmlTable;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.zd_statistics_planSchema;
import com.sinosoft.schema.zd_statistics_plan_recordSchema;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 实现类
 * Created by dongsheng on 2017/3/23.
 */
public class OperateStatisticsPlan extends AbstractOperateStatisticsPlan {

    public boolean reExecuteStatisticsPlan(String recordIdStr) {
        if (StringUtils.isBlank(recordIdStr) || !StringUtils.isNumeric(recordIdStr)) return false;
        long recordId = Long.parseLong(recordIdStr);
        zd_statistics_plan_recordSchema record = new zd_statistics_plan_recordSchema();
        record.setid(recordId);
        record.fill();
        Date startDate = record.getstart_date();
        Date endDate = record.getend_date();
        long planId = record.getstatistics_plan_id();
        if (planId <= 0) return false;
        zd_statistics_planSchema plan = new zd_statistics_planSchema();
        plan.setid(planId);
        plan.fill();
        byte[] bytes = plan.getgrid_data();
        DataGridAction dga = (DataGridAction) deserialize(bytes);
        Mapx params = dga.getParams();
        String className = params.getString("className");
        buildMatcherInstance(className);
        params.put(getMatcher().getStartDatetime(), DateFormatUtils.ISO_DATE_FORMAT.format(startDate));
        params.put(getMatcher().getEndDatetime(), DateFormatUtils.ISO_DATE_FORMAT.format(endDate));
        executeStatisticsPlan(dga, plan, record);
        record.setmanually_execute_user(User.getUserName());
        return record.update();
    }

    @Override
    public boolean executeStatisticsPlan(DataGridAction dga, zd_statistics_planSchema plan, zd_statistics_plan_recordSchema record) {
        Mapx params = dga.getParams();
        String strWidths = params.getString("_ZVING_Widths");
        String strIndexes = params.getString("_ZVING_Indexes");
        String method = params.getString(Constant.Method);
        try {
            int index = method.lastIndexOf('.');
            String className = method.substring(0, index);
            method = method.substring(index + 1);
            Class<?> aClass = Class.forName(className);
            Method aClassMethod = aClass.getMethod(method, DataGridAction.class);
            Object o = aClass.newInstance();
            aClassMethod.invoke(o, dga);
        } catch (Exception e) {
            logger.error("数据查询失败" + e.getMessage(), e);
            record.setquery_status(StatisticsPlanConstant.ERROR);
            return false;
        }
        // 有可能加入了一个空白行，需要去掉
        HtmlTable ht = dga.getTable();
        if (ht.getChildren()
                .size() > 0
                && "blank".equalsIgnoreCase(ht.getTR(ht.getChildren()
                .size() - 1)
                .getAttribute("ztype"))) {
            ht.removeTR(ht.getChildren()
                    .size() - 1);
        }
        File folderFile = createFileFolder(record.getcreate_datetime());
        String startDateString = DateFormatUtils.ISO_DATE_FORMAT.format(record.getstart_date());
        String endDateString = DateFormatUtils.ISO_DATE_FORMAT.format(record.getend_date());
        String fileNameString = startDateString + "-" + endDateString + "-" + System.currentTimeMillis() + ".xls";
        File downloadFile = new File(folderFile, fileNameString);
        FileOutputStream fbos = null;
        try {
            if (!downloadFile.exists()) {
                downloadFile.createNewFile();
            }
            fbos = new FileOutputStream(downloadFile);

            if (ht.getChildren()
                    .size() > 0
                    && "blank".equalsIgnoreCase(ht.getTR(ht.getChildren()
                    .size() - 1)
                    .getAttribute("ztype"))) {
                ht.removeTR(ht.getChildren()
                        .size() - 1);
            }
            HtmlUtil.htmlTableToExcel(fbos, ht, strWidths.split(","), strIndexes.split(","), null);
        } catch (IOException e) {
            logger.error("生成EXCEL失败" + e.getMessage(), e);
            record.setquery_status(StatisticsPlanConstant.ERROR);
            return false;
        } finally {
            if (fbos != null)
                try {
                    fbos.flush();
                    fbos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
        }
        record.setdownload_file_name(fileNameString);
        record.setdownload_file_path(downloadFile.getPath());
        record.setquery_status(StatisticsPlanConstant.SUCCESS);
        return true;
    }

    /**
     * 创建下载文件所在文件夹.
     *
     * @param createDate
     * @return
     */
    File createFileFolder(Date createDate) {
        // 生产文件夹
        String folderPath = Config.getValue("StatisticsFilePath") + "/" + DateFormatUtils.ISO_DATE_FORMAT.format(new Date());
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }


    @Override
    public DataGridAction buildDataGridAction(Mapx<String, Object> params) throws Exception {
        String id = params.getString(Constant.ID);
        String tagBody = params.getString(Constant.TagBody);
//        String pageIndex = params.getString(Constant.DataGridPageIndex);
//        String pageSize = params.getString(Constant.Size);
//        String pageTotal = params.getString(Constant.DataGridPageTotal);
//        String pageFlag = params.getString(Constant.Page);
        String method = params.getString(Constant.Method);
        DataGridAction dga = new DataGridAction();
        HtmlTable table = new HtmlTable();
        if (tagBody != null && !tagBody.equals("")) {
            tagBody = StringUtil.htmlDecode(tagBody);
        }
        dga.setTemplate(table);
        dga.setMethod(method);
        dga.setID(id);
        dga.setTagBody(tagBody);
        dga.setPageFlag(true);
        dga.setPageIndex(0);
//        dga.setPageSize(Integer.parseInt(pageTotal));
        dga.setPageSize(0);
        table.parseHtml(dga.getTagBody());
        dga.setTemplate(table);
        dga.parse();
        // Current.init(getRequest(), getResponse(), method);
        dga.setParams(params);
        // dga.Response = Current.getResponse();
        return dga;
    }


}
