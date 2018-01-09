package com.sinosoft.cms.plan.statistics;

import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.zd_statistics_planSchema;
import com.sinosoft.schema.zd_statistics_plan_recordSchema;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 模板方法抽象类.
 * Created by dongsheng on 2017/3/24.
 */
public abstract class AbstractOperateStatisticsPlan extends Page {
    private FieldsNameMatcher matcher;

    AbstractOperateStatisticsPlan() {
        this.matcher = new FieldsNameMatcher().setPrefix(StatisticsPlanConstant.PREFIX);
    }

    /**
     * 由子类实现.创建dga
     *
     * @param params
     * @return
     * @throws Exception
     */
    public abstract DataGridAction buildDataGridAction(Mapx<String, Object> params) throws Exception;

    /**
     * 拿到plan,创建执行的记录->执行查询->回写数据(查询状态,下载文件位置等等)
     *
     * @param dga
     * @return
     */
    public abstract boolean executeStatisticsPlan(DataGridAction dga, zd_statistics_planSchema plan, zd_statistics_plan_recordSchema record);

    /**
     * 模板方法.
     * 创建统计计划主体方法.
     *
     * @return
     */
    public void createStatisticsPlan() {

        Mapx<String, Object> params = Request;
        params = removeParametersPrefix(params);
        String method = params.getString(Constant.Method);
        String className = methodToClassNameString(method);
        params.put("className", className);
        params.put("source_url", Request.getURL());
        try {
            //处理参数.
            buildMatcherInstance(className);
            //根据参数生成dga
            DataGridAction dga = buildDataGridAction(params);
            //业务方法.
            saveStatisticsPlan(dga);
            Response.setMessage("创建成功");
            Response.setStatus(1);
        } catch (IllegalAccessException e) {
            logger.error("参数无法读取"+e.getMessage(), e);
            Response.setStatus(0);
            Response.setMessage("参数无法读取");

        } catch (InvocationTargetException e) {
            logger.error("调用对象异常"+e.getMessage(), e);
            Response.setStatus(0);
            Response.setMessage("调用对象异常");
        } catch (NoSuchMethodException e) {
            logger.error("没有这个方法" + e.getMessage(), e);
            Response.setStatus(0);
            Response.setMessage("没有这个方法");
        } catch (Exception e) {
            logger.error("table.parseHtml(dga.getTagBody());解析异常" + e.getMessage(), e);
            Response.setStatus(0);
            Response.setMessage("table.parseHtml(dga.getTagBody());解析异常");
        }

    }

    private zd_statistics_planSchema saveStatisticsPlan(DataGridAction dga) {
        String method = dga.getParam(Constant.Method);
        String className = dga.getParam("className");
        String planName = dga.getParam("plan_name");
        String period = dga.getParam("period");
        String sourceUrl = dga.getParam("source_url");
        String startDate = dga.getParam(getMatcher().getStartDatetime());
        String endDate = dga.getParam(getMatcher().getEndDatetime());
        zd_statistics_planSchema plan = new zd_statistics_planSchema();
        String userName = User.getUserName();
        Date createDate = new Date();
        plan.setid(NoUtil.getMaxID(StatisticsPlanConstant.STATISTICS_PLAN_TABLE));
        plan.setplan_name(planName);
        //如果不匹配,会抛出异常,终止程序保存.
        period = PeriodEnum.valueOf(period).name();
        plan.setperiod(period);
        plan.setcreate_user(userName);
        plan.setcreate_datetime(createDate);
        plan.setmodify_user(userName);
        plan.setmodify_datetime(createDate);
        plan.setplan_status(StatisticsPlanConstant.START);
        plan.setentrance_function(method);
        plan.setsource_url(sourceUrl);
        byte[] bytes = serialize(dga);
        plan.setgrid_data(bytes);
        plan.setplan_breadcrumbs(className);
        //默认执行日期为创建后的第二天.
        Date nextExecuteDate = DateUtils.addDays(new Date(), 1);
        nextExecuteDate = DateUtils.truncate(nextExecuteDate, Calendar.DAY_OF_MONTH);
        plan.setexecute_datetime(nextExecuteDate);
        if (plan.insert()) {
            return plan;
        } else {
            return null;
        }
    }

    /**
     * 模板方法
     * 执行统计计划主体方法.
     */
    public void launchStatisticsPlan(zd_statistics_planSchema plan) {
            logger.info("launchStatisticsPlan launch start");
            if (plan == null) {
                return;
            }
            zd_statistics_plan_recordSchema record = new zd_statistics_plan_recordSchema();
            record.setid(NoUtil.getMaxID(StatisticsPlanConstant.STATISTICS_PLAN_RECORD_TABLE_SHORT));
//            record.setdownload_status(StatisticsPlanConstant.READY);
            record.setquery_status(StatisticsPlanConstant.READY);
            record.setstatistics_plan_id(plan.getid());
            record.setcreate_datetime(new Date());
            byte[] bytes = plan.getgrid_data();
            DataGridAction dga = (DataGridAction) deserialize(bytes);
            Mapx params = dga.getParams();
            logger.info("launchStatisticsPlan:157 param:{}", params);
            String className = params.getString("className");
            buildMatcherInstance(className);
            Map<String, Date> dateMap = null;
            try {
                dateMap = dealParamsDate(params);
            } catch (ParseException e) {
                logger.error("参数匹配异常" + e.getMessage(), e);
            }
            record.setstart_date(dateMap.get("queryStartDate"));
            record.setend_date(dateMap.get("queryEndDate"));
            plan.setexecute_datetime(dateMap.get("nextExecuteDate"));
            bytes = serialize(dga);
            plan.setgrid_data(bytes);
            plan.setmodify_datetime(new Date());
            if (!checkStatisticsPlanRecord(record)) {
                return;
            }
            logger.info("launchStatisticsPlan execute start");
            executeStatisticsPlan(dga, plan, record);
            logger.info("launchStatisticsPlan execute end");
            Transaction transaction = new Transaction();
            transaction.add(plan, Transaction.UPDATE);
            transaction.add(record, Transaction.INSERT);
            transaction.commit();
            logger.info("launchStatisticsPlan launch end");
    }


    private boolean checkStatisticsPlanRecord(zd_statistics_plan_recordSchema record) {
        if (record == null) {
            logger.warn("数据不能为空");
            return false;
        }
        if (record.getid() <= 0) {
            logger.warn("数据主键不能为空");
            return false;
        }
        if (record.getstatistics_plan_id() <= 0) {
            logger.warn("关联外键不能为空");
            return false;
        }
        return true;
    }

    /**
     * 参数key处理.
     * 去前缀,并且用户查询参数自动处并配处理.
     *
     * @param params 参数
     * @return
     */
    private Mapx<String, Object> removeParametersPrefix(Mapx<String, Object> params) {
        String prefix = getMatcher().getPrefix();
        int prefixLength = prefix.length();
        Object[] ks = params.keyArray();
        for (Object k1 : ks) {
            String k = k1.toString();
            String standardKey;
            if (k.startsWith(prefix)) {
                Object v = params.get(k);
                standardKey = k.substring(prefixLength);
                params.put(standardKey, v);
                params.remove(k);
            }
        }
        return params;
    }

    /**
     * 根据调用的方法名获取该方法的全类名.
     *
     * @param method
     * @return
     */
    private String methodToClassNameString(String method) {
        if (StringUtils.isEmpty(method)) {
            return null;
        }
        String className = null;
        try {
            int i = method.lastIndexOf('.');
            className = method.substring(0, i);


        } catch (Exception e) {
            logger.error("AbstractOperateStatisticsPlan.buildMatcherInstance" + e.getMessage(), e);
        }
        return className;
    }

    /**
     * 反射获取处理查询类参数不匹配的类.
     *
     * @param reflectClassName
     */
    protected void buildMatcherInstance(String reflectClassName) {
        if (StringUtils.isEmpty(reflectClassName)) {
            return;
        }
        try {
            Class<?> matchableClass = Class.forName(reflectClassName);
            FieldNameMatchable fieldNameMatchable = (FieldNameMatchable) matchableClass.newInstance();
            fieldNameMatchable.fieldNameMatch(getMatcher());
        } catch (IllegalAccessException e) {
            logger.error("没有访问权限" + e.getMessage(), e);
        } catch (InstantiationException e) {
            logger.error("查询类没有实现转换接口.跳过,直接使用用户参数." + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * @param params
     * @throws ParseException
     */
    private Map<String, Date> dealParamsDate(Mapx params) throws ParseException {
        PeriodEnum periodEnum = PeriodEnum.valueOf(params.getString("period"));
        String startDateString = params.getString(getMatcher().getStartDatetime());
        String endDateString = params.getString(getMatcher().getEndDatetime());
        Date queryStartDate;
        Date queryEndDate;
        Date nextExecuteDate = new Date();
        queryStartDate = DateUtils.parseDate(startDateString, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss");
        queryEndDate = DateUtils.parseDate(endDateString, "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss");
        Map<String, Date> dateMap = calculatorNextDate(queryStartDate, queryEndDate, nextExecuteDate, periodEnum);
        nextExecuteDate = dateMap.get("nextExecuteDate");
        queryStartDate = dateMap.get("queryStartDate");
        queryEndDate = dateMap.get("queryEndDate");
        params.put("nextExecuteDate", nextExecuteDate);
        params.put(getMatcher().getStartDatetime(), DateFormatUtils.ISO_DATE_FORMAT.format(queryStartDate));
        params.put(getMatcher().getEndDatetime(), DateFormatUtils.ISO_DATE_FORMAT.format(queryEndDate));
        return dateMap;
    }

    /**
     * 根据本次查询的时间范围,执行时间和周期类型,计算本次查询的开始时间,结束时间,计划下次统计查询时间.
     * 单次执行,无需计算下次执行时间.
     *
     * @param queryStartDate
     * @param queryEndDate
     * @param executeDate
     * @param periodEnum
     * @return
     */
    public Map<String, Date> calculatorNextDate(Date queryStartDate, Date queryEndDate, Date executeDate, PeriodEnum periodEnum) {
        if (executeDate == null)
            executeDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(executeDate);
        Date nextExecuteDate = null;
        switch (periodEnum) {
            case ONCE:
                nextExecuteDate = executeDate;
                break;
            case DAY:
                //由于传入的执行日期是今天.查询范围实在执行的时候才计算的.因此,查询范围是本次的查询范围.
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                queryEndDate = queryStartDate = calendar.getTime();
                calendar.add(Calendar.DAY_OF_MONTH, 2);
                nextExecuteDate = calendar.getTime();
                break;
            case WEEK:
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                queryStartDate = calendar.getTime();
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                queryEndDate = calendar.getTime();
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                calendar.add(Calendar.DATE, 1);
                nextExecuteDate = calendar.getTime();
                break;
            case MONTH:
                calendar.add(Calendar.MONTH, -1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                queryStartDate = calendar.getTime();
                calendar.add(Calendar.MONTH, 2);
                nextExecuteDate = calendar.getTime();
                calendar.add(Calendar.MONTH, -1);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                queryEndDate = calendar.getTime();
                break;
            case YEAR:
                calendar.add(Calendar.YEAR, -1);
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                queryStartDate = calendar.getTime();
                calendar.add(Calendar.YEAR, 2);
                nextExecuteDate = calendar.getTime();
                calendar.add(Calendar.YEAR, -1);
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                queryEndDate = calendar.getTime();
                break;
        }
        if (queryStartDate != null) queryStartDate = DateUtils.truncate(queryStartDate, Calendar.DAY_OF_MONTH);
        if (queryEndDate != null) queryEndDate = DateUtils.truncate(queryEndDate, Calendar.DAY_OF_MONTH);
        if (nextExecuteDate != null) nextExecuteDate = DateUtils.truncate(nextExecuteDate, Calendar.DAY_OF_MONTH);
        Map<String, Date> map = new HashMap<String, Date>();
        map.put("queryStartDate", queryStartDate);
        map.put("queryEndDate", queryEndDate);
        map.put("nextExecuteDate", nextExecuteDate);
        return map;
    }

    /**
     * 序列化对象
     *
     * @param object
     * @return 字节数组
     */
    protected byte[] serialize(Object object) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        byte[] bytes = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            bytes = bos.toByteArray();
        } catch (IOException e) {
            logger.error("对象序列化异常" + e.getMessage(), e);
        } finally {
            try {
                if (bos != null) bos.close();
                if (oos != null) oos.close();
            } catch (IOException e) {
                logger.error("io流关闭异常" + e.getMessage(), e);
            }
        }
        return bytes;
    }


    /**
     * 反序列话对象.
     *
     * @param bytes
     * @return 对象
     */
    protected Object deserialize(byte[] bytes) {
        if (bytes == null) return null;
        ObjectInputStream objectInputStream = null;
        ByteArrayInputStream in = null;
        Object o = null;
        try {
            in = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(in);
            o = objectInputStream.readObject();
        } catch (IOException e) {
            logger.error("对象反序列化流异常" + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (in != null)
                    in.close();
                if (objectInputStream != null)
                    objectInputStream.close();
            } catch (IOException e) {
                logger.error("流关闭异常" + e.getMessage(), e);
            }
        }
        return o;
    }

    public FieldsNameMatcher getMatcher() {
        return matcher;
    }

    public AbstractOperateStatisticsPlan setMatcher(FieldsNameMatcher matcher) {
        this.matcher = matcher;
        return this;
    }

}
