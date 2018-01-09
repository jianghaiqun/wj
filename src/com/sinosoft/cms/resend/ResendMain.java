package com.sinosoft.cms.resend;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDTradeErrorCacheSchema;
import com.sinosoft.schema.SDTradeErrorCacheSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: ResendUtil 
 * @Description: TODO(重发类) 
 * @author CongZN 
 * @date 2014-10-9 下午04:09:18 
 *
 * <p> 修改历史</p>
 * <p> 序号 日期 修改人 修改原因</p>
 */
public class ResendMain {
	private static final Logger logger = LoggerFactory.getLogger(ResendMain.class);
	/**
	 * 报文发送间隔时间(单位:毫秒,第一次).
	 */
	private static long FIRST_INTERVAL_TIME = 180000;
	/**
	 * 报文发送间隔时间(单位:毫秒).
	 */
	private static long INTERVAL_TIME = 360000;
	/**
	 * 报文重发次数.
	 */
	private static long RESEND_COUNT = 5;
	/**
	 * 线程发送间隔.
	 */
	private static long RESEND_SLEEP = 5000;
	/**
	 * 线程池数量.
	 */
	private static int RESEND_POOL = 20;
	
	/**
	 * 交易重发-所支持保险公司.
	 * 2034-美亚
	 * 2049-安联
	 * 2071-大众		  
	 * 1018-太平养老 
	 * 1061-合众人寿 
	 * 1010-平安养老 
	 * 2096-安盛天平 
	 * 1001-中国人寿 - 暂时无法重发.
	 * 2023-华泰人寿 - 暂时无法重发.
	 * 2007-平安财险
	 * 2074-太阳联合
	 * 2046-永安人寿
	 * 2101-人保财险
	 * 2236-大都会
	 * 2238-易安  - 暂时无法重发
	 * 2241-平安健康 - 暂时无法重发
	 * 2242-新永安 - 暂时无法重发
	 */
	private static final String COMPANY = "2034,2049,2071,1018,1061,1010,2096,2074,2007,2046,2101,2236,2250,2251,2252,2232,2257";
	
	static{
		try {
			String intervalTime= Config.getValue("INTERVAL_TIME");
			String resendCount = Config.getValue("INTERVAL_TIME");
			if(StringUtil.isNotEmpty(intervalTime)){
				INTERVAL_TIME = Long.parseLong(Config.getValue("INTERVAL_TIME"));
			}
			
			if(StringUtil.isNotEmpty(resendCount)){
				RESEND_COUNT = Long.parseLong(Config.getValue("RESEND_COUNT"));
			}
			
		} catch (Exception e) {
			logger.error("com.sinosoft.cms.resend.ResendUtil static 静态初始化异常!" + e.getMessage(), e);
		}
	}
	
	/**
	 * @Title: resendCacheAdd.
	 * @Description: TODO(待发送报文信息缓存-ADD).
	 * @param p_orderSn
	 * @param p_errorMsg void.
	 * @author CongZN.
	 */
	public static void resendCacheAdd(String p_orderSn,String p_companyID,String p_errorMsg){
		
		try {
			
			String[] companyArr = COMPANY.split(",");
			
			boolean state = false;
			for (int k = 0; k < companyArr.length; k++) {
				if (companyArr[k].equals(p_companyID)) {
					state = true;
					break;
				}
			}
			
			if(state){
				
				Transaction ts = new Transaction();
				StringBuffer query_orderDetailSql = new StringBuffer();
				query_orderDetailSql.append("select s1.orderSn,s1.insuredSn,s2.insuranceCompany,s2.startDate from  SDInformationInsured s1 ,SDInformation s2 ");
				query_orderDetailSql.append("where s2.orderSn = s1.orderSn and s1.ordersn = ? ");
				
				QueryBuilder query_orderDetail = new QueryBuilder(query_orderDetailSql.toString());
				query_orderDetail.add(p_orderSn);
				
				DataTable result_orderDetail = query_orderDetail.executeDataTable();
				
				for(int i = 0; i < result_orderDetail.getRowCount(); i++){
					String orderSn = result_orderDetail.getString(i, "orderSn");
					String insuredSn = result_orderDetail.getString(i, "insuredSn");
					String insuranceCompany = result_orderDetail.getString(i, "insuranceCompany");
					String startDate = result_orderDetail.getString(i, "startDate");
					String sendDate = "";
					for(int j = 0; j < RESEND_COUNT; j++){
						sendDate = getSendTime(sendDate, startDate, insuranceCompany);
						if("false".equals(sendDate)){
							break;
						}
						SDTradeErrorCacheSchema sdt = new SDTradeErrorCacheSchema();
						sdt.setID(NoUtil.getMaxID("TradeErrorCache")+"");
						sdt.setOrderSn(orderSn);
						sdt.setCompanyName(insuranceCompany);
						sdt.setinsuredSn(insuredSn);
						sdt.setErrorMessage(p_errorMsg);
						sdt.setSendDate(sendDate);
						sdt.setCreateDate(PubFun.getCurrent());
						ts.add(sdt, Transaction.INSERT);
					}
				}
				
				if(!ts.commit()){
					logger.error("com.sinosoft.cms.resend.ResendMain 方法：resendCacheAdd 数据保存异常!");
				}
				
			}
			
		} catch (Exception e) {
			logger.error("com.sinosoft.cms.resend.ResendMain 方法：resendCacheAdd 数据保存异常!" + e.getMessage(), e);
		}
		
	}
	
	/**
	 * @Title: getSendTime.
	 * @Description: TODO(报文发送获取间隔时间).
	 * @param p_lastTime 下一次发送时间.
	 * @param p_effective 起保日期.
	 * @param companyID 保险公司ID.
	 * @return String 1.返回下一次发送时间 2.false 下一次发送超过起保日期.
	 * @author CongZN.
	 */
	public static String getSendTime(String p_lastTime,String p_effective, String companyID){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			long ms = 0;
			
			if(StringUtil.isEmpty(p_lastTime)){
				ms = date.getTime() + FIRST_INTERVAL_TIME;
			}else{
				date = sdf.parse(p_lastTime);
				ms = date.getTime() + INTERVAL_TIME;
			}
			
			// 百年航意险和平安财险过了起保日期也可以承保
			if (!"2237".equals(companyID) && !"2007".equals(companyID)) {
				if(ms >= sdf.parse(p_effective).getTime()){
					return "false";
				}
			}
			
			date.setTime(ms);
		} catch (Exception e) {
			logger.error("com.sinosoft.cms.resend.ResendMain 方法：getSendTime 时间获取异常!" + e.getMessage(), e);
		}
		
		return sdf.format(date);
	}
	
	/**
	 * @Title: executResend.
	 * @Description: TODO(执行重发). void.
	 * @author CongZN.
	 */
	public static void executResend(){
		
		try {
			
			SDTradeErrorCacheSchema sdt = new SDTradeErrorCacheSchema();
			QueryBuilder query_tradeErrorCache = new QueryBuilder(" where SendDate < NOW() ");
			SDTradeErrorCacheSet sdtset = sdt.query(query_tradeErrorCache);
			
			Transaction ts = new Transaction();
			
			ExecutorService pool = Executors.newFixedThreadPool(RESEND_POOL);
			for(int i = 0; i < sdtset.size(); i++){
				SDTradeErrorCacheSchema rsdt = sdtset.get(i);
				if(isInsured(rsdt.getinsuredSn())){
					//未承保-调用重发-线程池、多线程发送
					Thread.sleep(RESEND_SLEEP);
					Thread t1 = new ResendThread(rsdt.getOrderSn(),rsdt.getCompanyName(),rsdt.getinsuredSn());
					pool.execute(t1);
					rsdt.deleteAndBackup("定时任务", "");
				}else{
					QueryBuilder del_tradeErrorCache = new QueryBuilder("delete from SDTradeErrorCache where insuredSn = ?");
					del_tradeErrorCache.add(rsdt.getinsuredSn());
					ts.add(del_tradeErrorCache);
				}
			}
			
			pool.shutdown();
			
			if(!ts.commit()){
				logger.error("com.sinosoft.cms.resend.ResendMain 方法：executResend 数据保存异常!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	/**
	 * @Title: isInsured.
	 * @Description: TODO(判断被保人是否承保或撤单).
	 * @param p_insured
	 * @return boolean  true : 可重发.
	 * @author CongZN. 
	 */
	public static boolean isInsured(String p_insured){
		String queryInsuredSql = "select s2.appStatus from SDInformationInsured s1, SDInformationRiskType s2 where s1.id = s2.sdinformationinsured_id and s1.insuredSn = ? ";
		QueryBuilder query_insured = new QueryBuilder(queryInsuredSql);
		query_insured.add(p_insured);
		String result_appStatus = query_insured.executeString();
		if("0".equals(result_appStatus) || StringUtil.isEmpty(result_appStatus)){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
//		resendCacheAdd("2013000000012987","1001","asdfasdf");
//		executResend();
	}
	
}
