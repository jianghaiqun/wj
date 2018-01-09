package com.sinosoft.datachannel;

import com.sinosoft.cms.plan.statistics.OperateStatisticsPlan;
import com.sinosoft.cms.plan.statistics.StatisticsPlanConstant;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.schema.zd_statistics_planSchema;
import com.sinosoft.schema.zd_statistics_planSet;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 统计计划定时任务执行类.
 * Created by dongsheng on 2017/3/28.
 */
public class StatisticsPlanMananger extends ConfigEanbleTaskManager {
    private final static Logger logger = LoggerFactory.getLogger(StatisticsPlanMananger.class);
    public static final String CODE = "com.sinosoft.datachannel.StatisticsPlanMananger";

    @Override
    public Mapx getConfigEnableTasks() {
        Mapx<String, String> map = new Mapx<String, String>();
        map.put("0", "统计计划定时任务");
        return map;
    }

    @Override
    public void execute(String paramString) {
        int route = Integer.parseInt(paramString);
        switch (route) {
            case 0:
                scanStatisticsPlan();
                break;
            default:
                break;
        }
    }

    private void scanStatisticsPlan() {
    	// 数据沉淀
    	dataPrecipitation();
    	
        zd_statistics_planSchema planSchema = new zd_statistics_planSchema();
        QueryBuilder qb = new QueryBuilder();
        qb.setSQL("where plan_status=?");
        qb.add(StatisticsPlanConstant.START);
        zd_statistics_planSet set = planSchema.query(qb);
        for (int i = 0; i < set.size(); i++) {
            zd_statistics_planSchema schema = set.get(i);
            Date date = schema.getexecute_datetime();
            if (date != null && DateUtils.isSameDay(new Date(), date)) {
                launchStatisticsPlan(schema);
            }

        }
    }

    private void launchStatisticsPlan(zd_statistics_planSchema schema) {
        OperateStatisticsPlan plan = new OperateStatisticsPlan();
        plan.launchStatisticsPlan(schema);
    }


	/**
	 * dataPrecipitation:数据沉淀. <br/>
	 * 
	 * @author wwy
	 */
	private void dataPrecipitation() {
    	try {
    		
    		String sqlDelete = "delete from dataprecipitationorder_1;";
    		Transaction trans = new Transaction();
    		trans.add(new QueryBuilder(sqlDelete));
    		
    		String sql = "INSERT INTO dataprecipitationorder(id,orderSn,policyNo,memberId,timePrem,sdorId,orderStatus,orderStatusName,appStatus,channelSn,fromwap,receiveDate,receiveDate2, "
    				+ "orderCreateDate,memberCreateDate,insureDate,informationSn,applicantSn,recognizeeSn,startDate,endDate,applicantName,applicantIdentityType,applicantIdentityTypeName, "
    				+ "applicantIdentityId,applicantMobile,applicantMail,applicantCreateDate,recognizeeName,recognizeeIdentityType,recognizeeIdentityTypeName,recognizeeIdentityId,recognizeeMobile, "
    				+ "recognizeeMail,recognizeeCreateDate,productId,productName,plan,planName,productPrice,couponsn,activitySn,couponValue,integralValue,activityValue,payPrice, "
    				+ "insuranceCompany,cancelDate,memberMobile,memberMail,CreateDate,ModifyDate,orderCreateDate_30) "
    				+ "(SELECT REPLACE(UUID(),'-',''),o.orderSn,r.policyNo,o.memberid,r.timePrem,r.id,o.orderStatus,h.CodeName,r.appStatus,o.channelSn,m.fromwap, "
    				+ "max(tr.receiveDate),max(left(tr.receiveDate,10)),o.createDate,m.createDate,r.insureDate,i.informationSn,a.applicantSn,ins.recognizeeSn,i.startDate,i.endDate, "
    				+ "a.applicantName,a.applicantIdentityType,a.applicantIdentityTypeName,a.applicantIdentityId,a.applicantMobile,a.applicantMail, "
    				+ "a.createDate,ins.recognizeeName,ins.recognizeeIdentityType,ins.recognizeeIdentityTypeName,ins.recognizeeIdentityId,ins.recognizeeMobile, "
    				+ "ins.recognizeeMail,ins.createDate,i.productId,i.productName,i.planCode,i.planName,o.productTotalPrice,o.couponsn,o.activitySn, "
    				+ "r.couponValue,r.integralValue,r.activityValue,r.payPrice,i.insuranceCompany,r.cancelDate,m.mobileno,m.email,now(),now(),DATE_ADD(o.createdate,INTERVAL - 30 DAY) "
    				+ "FROM sdorders o left join member m on m.id = o.memberid, sdinformation i, sdinformationrisktype r, sdinformationappnt a, sdinformationinsured ins, tradeinformation tr, zdcode h "
    				+ "WHERE o.orderSn = i.orderSn AND o.orderSn = r.orderSn AND o.orderSn = ins.orderSn AND i.informationSn = a.informationSn AND r.orderSn = ins.orderSn AND r.recognizeeSn = ins.recognizeeSn "
    				+ "AND r.orderSn = tr.ordId AND o.orderStatus >= 7 AND tr.payStatus = '1' AND h.CodeType = 'orderstatus' AND h.codevalue = o.orderstatus group by r.id "
    				+ "having not EXISTS (select 1 from dataprecipitationorder d where d.sdorId = r.id)) ";
    		QueryBuilder qb = new QueryBuilder(sql);
    		trans.add(qb);
    		if (trans.commit()) {
    			logger.info("数据沉淀数据插入成功");
    		} else {
    			logger.error("数据沉淀处理失败");
    		}
    	} catch (Exception e) {
    		logger.error("数据沉淀处理异常" + e.getMessage(), e);
    	}
    	
    	try {
    		String sql = "update dataprecipitationorder d, sdorders o, sdinformationrisktype r "
    				+ " set d.orderStatus = o.orderStatus, d.appStatus = r.appStatus, d.cancelDate = r.cancelDate "
    				+ " where d.orderSn = o.orderSn and o.orderSn = r.orderSn and d.sdorId = r.id "
    				+ " and DATE_FORMAT(r.cancelDate, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d') ";
    		QueryBuilder qb = new QueryBuilder(sql);
    		Transaction trans = new Transaction();
    		trans.add(qb);
    		if (trans.commit()) {
    			logger.info("数据沉淀数据撤单变更成功");
    		} else {
    			logger.error("数据沉淀撤单变更处理失败");
    		}
    	} catch (Exception e) {
    		logger.error("数据沉淀撤单变更处理异常" + e.getMessage(), e);
    	}
    }
    
    @Override
    public boolean isRunning(String paramString) {
        return false;
    }

    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public String getID() {
        return CODE;
    }

    @Override
    public String getName() {
        return "统计定时任务";
    }
}
