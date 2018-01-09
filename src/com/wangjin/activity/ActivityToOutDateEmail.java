package com.wangjin.activity;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityToOutDateEmail extends ConfigEanbleTaskManager {
	public static final String CODE = "com.wangjin.activity.ActivityToOutDateEmail";

	public boolean isRunning(long id) {
		return false;
	}

	/**
	 * 
	* @Title: deal 
	* @Description: TODO(待过期活动邮件提醒定时任务,处理高倍积分、网站折扣活动；活动结束前12小时邮件提醒) 
	* @return boolean    返回类型 
	* @author cshg
	 */
	public boolean deal() {
		String sql = "SELECT id,activitysn,description,starttime,endtime FROM sdcouponactivityinfo WHERE UNIX_TIMESTAMP(endtime) <= UNIX_TIMESTAMP(DATE_ADD(NOW(), INTERVAL 12 HOUR)) AND UNIX_TIMESTAMP(endtime) > UNIX_TIMESTAMP(NOW())  AND `status` ='3' AND `type` IN('6','7')   " ;
		QueryBuilder querybuilder = new QueryBuilder(sql);
		DataTable dt = querybuilder.executeDataTable();
		Map<String, String> activeInfoMap;
		List<Map<String, String>> activeInfoList = new ArrayList<Map<String, String>>();
		int dtLen = dt.getRowCount();
		if (dtLen > 0) {
			for(int i=0;i<dtLen;i++){
				activeInfoMap = new HashMap<String, String>();
				activeInfoMap.put("activitysn", dt.getString(i, 1));
				activeInfoMap.put("description", dt.getString(i, 2));
				activeInfoMap.put("starttime", dt.getString(i, 3));
				activeInfoMap.put("endtime", dt.getString(i, 4));
				activeInfoList.add(activeInfoMap);
			}
			if(activeInfoList.size()>=1){
				String toEmail = Config.getValue("ActiveOutDateEmail");
				if(StringUtil.isNotEmpty(toEmail)){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ActiveInfoList", activeInfoList);
					if(!ActionUtil.sendMail("wj00100", toEmail, map)){
						logger.error("待过期活动提醒邮件发送失败！");
					}
				}
				
			}
			return true;
		} else {
			logger.info("未查询到待过期活动");
			return false;
		}
	}

	/**
	 * 
	* @Title: execute 
	* @Description: TODO(定时任务调用主方法) 
	* @return void    返回类型 
	* @author zhangjing
	 */
	public void execute(long id) {
		// 活动过期提醒
		if ("0".equals(id + "")) {
			if (deal()) {
				logger.info("待过期活动邮件提醒发送成功！");
			}
		}
	}

	@Override
	public Mapx<String, String> getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "待过期活动邮件提醒定时任务");
		return map;
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "待过期活动邮件提醒定时任务";
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.wangjin.activity.ActivityToOutDateEmail";
	}
}
