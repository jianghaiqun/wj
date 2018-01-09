package com.sinosoft.datachannel;

import com.sinosoft.framework.GetDBdata;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.LotteryActSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ActivityService {
	private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);
	public void execute() {
		logger.info("定时活动抽奖开始！");
		String sql = "select memberId,ordersn from orders where orderstatus='7' and memberid !='' and totalamount!=0 and date_format(createdate,'%Y%m%d')>='20120918' and date_format(createdate,'%Y%m%d')<='20121031' and ordersn not in (select orderno from lotteryact where actCode='h00001'  and orderno is not null)";
		GetDBdata db = new GetDBdata();
		List<HashMap<String, String>> a = new ArrayList<HashMap<String, String>>();
		try {
			a = db.query(sql);
		} catch (Exception e) {
			logger.error("定时活动抽奖查询" + e.getMessage(), e);
		}	
		for (int i = 0; i < a.size(); i++) {
			String memberId = a.get(i).get("memberId");
			String orderNo = a.get(i).get("ordersn");
			LotteryActSchema tLotteryActSchema = new LotteryActSchema();
			tLotteryActSchema.setid(NoUtil.getMaxNo("LotteryActID", 12));
			tLotteryActSchema.setorderNo(orderNo);
			tLotteryActSchema.setmemberId(memberId);
			tLotteryActSchema.setactCode("h00001");
			tLotteryActSchema.setrecordType("JOIN");
			tLotteryActSchema.setuseType("N");
			tLotteryActSchema.setcreateDate(new Date());
			if (!tLotteryActSchema.insert()) {
				logger.warn("活动数据插入有误！");
			}
		}
		
//		String sql1="create table actwin as select min(id) as col1 from lotteryact group by orderno";
//		String sql2="delete from lotteryact where id not in (select col1 from actwin)";
//		String sql3="drop table actwin";
//		GetDBdata db1 = new GetDBdata();
//		try {
//			db1.execUpdateSQL(sql1, "");
//			db1.execUpdateSQL(sql2, "");
//			db1.execUpdateSQL(sql3, "");
//		} catch (Exception e) {
//			LogUtil.getLogger().error("活动数据去重有误！");
//		}
//		
		logger.info("定时活动抽奖结束！");
	}

}
