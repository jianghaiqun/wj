package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.LotteryAwardSchema;
import com.sinosoft.schema.LotteryInfoSchema;
import com.sinosoft.schema.LotteryWinnerSchema;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ParentPackage("shop") 
public class LotteryActivityAction extends BaseShopAction {
	private static final long serialVersionUID = 1L;
	private String GotType;
	
	/**
	 * 
	* @Title: clickTurn 
	* @Description: TODO(点击转盘-CJ) 
	* @return String    返回类型 
	* @author jiaomengying
	 */
	public synchronized  String clickTurn(){
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = getRequest();
		// 验证是否登录
		String memberId = (String) request.getSession().getAttribute(
				Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtil.isEmpty(memberId)) {
			map.put(STATUS, "notLogin");
			map.put("text", "请登录");
			return ajax(JSONObject.fromObject(map).toString(),"text/html");
		}
		
		memberSchema memberSchemas = new memberSchema();
		memberSchemas.setid(memberId);
		memberSet memberSets = memberSchemas.query();
		if ((null == memberSets) || (0 == memberSets.size())) {
			map.put(STATUS, "notLogin");
			map.put("text", "请登录");
			return ajax(JSONObject.fromObject(map).toString(),"text/html");
		}
		Date date=new Date();
	    SimpleDateFormat matter=new SimpleDateFormat("dd");
	    String currentDate = matter.format(date);
		Transaction tran = new Transaction();
		// 抽奖明细
		LotteryInfoSchema lotteryInfo = new LotteryInfoSchema();
		lotteryInfo.setMemberId(memberId);
		lotteryInfo.setCurrentDay(currentDate);
		lotteryInfo.setGotType(GotType);
		lotteryInfo.fill();
		if(!lotteryInfo.fill()){
			lotteryInfo.setClickTimes("1");
			lotteryInfo.setLottTime(date);
			lotteryInfo.setGotValue(GotType);
			lotteryInfo.setShareTimes("0");
			tran.add(lotteryInfo,Transaction.INSERT);
			//首次点击转盘
			map = initRandom(tran,memberId);
			
		}else{
			int clickTimes = Integer.parseInt(lotteryInfo.getClickTimes());
			int shareTimes = Integer.parseInt(lotteryInfo.getShareTimes());
			if(clickTimes<2 && clickTimes<=shareTimes){
				int clicktimes = Integer.parseInt(lotteryInfo.getClickTimes())+1;
				lotteryInfo.setClickTimes(String.valueOf(clicktimes));
				tran.add(lotteryInfo,Transaction.UPDATE);
				//第二次点击转盘
				map = initRandom(tran,memberId);
			}else if(clickTimes==2){
				map.put("status", "N");
			}else{
				map.put("status", "X");
			}
		}
		tran.commit();
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	/**
	 * 
	* @Title: clickCoupon 
	* @Description: TODO(领取优惠券-2、5、10、25) 
	* @return String    返回类型 
	* @author jiaomengying
	 */
	public  String clickCoupon(){
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = getRequest();
		// 验证是否登录
		String memberId = (String) request.getSession().getAttribute(
				Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtil.isEmpty(memberId)) {
			map.put(STATUS, "notLogin");
			map.put("text", "请登录");
			return ajax(JSONObject.fromObject(map).toString(),"text/html");
		}
		
		memberSchema memberSchemas = new memberSchema();
		memberSchemas.setid(memberId);
		memberSet memberSets = memberSchemas.query();
		if ((null == memberSets) || (0 == memberSets.size())) {
			map.put(STATUS, "notLogin");
			map.put("text", "请登录");
			return ajax(JSONObject.fromObject(map).toString(),"text/html");
		}
		Date date=new Date();
	    SimpleDateFormat matter=new SimpleDateFormat("dd");
	    String currentDate = matter.format(date);
		Transaction tran = new Transaction();
		// 抽奖明细
		LotteryInfoSchema lotteryInfo = new LotteryInfoSchema();
		lotteryInfo.setMemberId(memberId);
		lotteryInfo.setCurrentDay(currentDate);
		lotteryInfo.setGotType(GotType);
		lotteryInfo.fill();
		if(!lotteryInfo.fill()){
			lotteryInfo.setClickTimes("1");
			lotteryInfo.setLottTime(date);
			lotteryInfo.setGotValue(GotType);
			lotteryInfo.setShareTimes("0");
			tran.add(lotteryInfo,Transaction.INSERT);
			// 首次领券
			map = sendCoupon("activitySn"+GotType);
			if("N".equals(map.get("status"))){
				map.put("detail", map.get("text"));
				return ajax(JSONObject.fromObject(map).toString(),"text/html");
			}
		}else{
			int clickTimes = Integer.parseInt(lotteryInfo.getClickTimes());
			int shareTimes = Integer.parseInt(lotteryInfo.getShareTimes());
			if(clickTimes<2 && clickTimes<=shareTimes){
				int clicktimes = Integer.parseInt(lotteryInfo.getClickTimes())+1;
				lotteryInfo.setClickTimes(String.valueOf(clicktimes));
				tran.add(lotteryInfo,Transaction.UPDATE);
				//第二次领券
				map = sendCoupon("activitySn"+GotType);
				if("N".equals(map.get("status"))){
					map.put("detail", map.get("text"));
					return ajax(JSONObject.fromObject(map).toString(),"text/html");
				}
			}else if(clickTimes==2){
				map.put("status", "N");
			}else{
				map.put("status", "X");
				map.put("gotType", GotType);
			}
		}
		tran.commit();
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	   /**
		* 
		* @Title: initRandom 
		* @Description: TODO(获取转盘机率  随机数) 
		* @return String    返回类型 
		* @author jiaomengying
		*/
		public Map<String, Object> initRandom(Transaction tran,String memberId){
			double rand = Math.floor(Math.random()*1000+1);
			int awardGrade = 0;
			int angle = 0;
			if(rand>0 && rand<=20){//2%
				awardGrade = 1;
				angle = 316;
			}else if(rand>20 && rand<=40){//2%
				awardGrade = 2;
				angle = 0;
			}else if(rand>40 && rand <=60){//2%
				awardGrade = 3;
				angle = 87;
			}else if(rand>60 && rand <=330){//27%
				awardGrade = 4;
				angle = 223;
			}else if(rand>330 && rand <=600){//27%
				awardGrade = 5;
				angle = 134;
			}else if(rand>600 && rand <=730){//13%
				awardGrade = 6;
				angle = 43;
			}else if(rand>730 && rand <=1000){//27%
				awardGrade = 7;
				angle = 177;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			//相应中奖奖品数量减1
			map = lotteryAward(awardGrade,tran);
			String text ="";
			String message ="";
			memberSchema memberSchemas = new memberSchema();
			memberSchemas.setid(memberId);
			memberSchemas.fill();
			String username = StringUtil.isEmpty(memberSchemas.getemail())?memberSchemas.getmobileNO():memberSchemas.getemail();
			if("N".equals(map.get("status"))){
				text = String.valueOf(map.get("detail"));
			}else{
				message = "<p class=\"dj_sf\">恭喜您获得开心保周年庆奖品，" +
							"请于三个工作日内使用会员注册邮箱账号将个人信息（姓名、地址、邮编、联系电话）发送至开心保客服邮箱kf@kaixinbao.com，请快快完善您的会员信息，莫与大奖擦肩而过哦！<br>如有疑问请点击联系网页右侧开心保‘在线客服’</p>";
				text = "恭喜"+username+"获得<em>"+map.get("detail")+"</em>";
			}
			if(!StringUtil.isEmpty(map.get("angle"))){
				angle = Integer.parseInt(String.valueOf(map.get("angle")));
			}
			map.put("angle",angle);
			map.put("text",text);
			map.put("message",message);
			map.put("status","Y");
			return  map;
		}
		/**
		 * 
		* @Title: sendCoupon 
		* @Description: TODO(优惠券 发送方法) 
		* @return Map<String,Object>    返回类型 
		* @author jiaomengying
		 */
		public Map<String,Object> sendCoupon(String value){
			Map<String,Object> map = new HashMap<String,Object>();
			String activitysn = Config.getValue(value);
			HashMap<String,String> mapCoupon = new MemberSendCouponAction().sendCouponDeal(activitysn);
			map.put("status", mapCoupon.get("status"));
			return map;
		}
		
		/**
		 * 
		* @Title: lotteryAward
		* @Description: TODO(中奖操作) 
		* @return String    返回类型 
		* @author jiaomengying
		 */
		public Map<String, Object> lotteryAward(int awards,Transaction tran){
			Map<String, Object> map = new HashMap<String, Object>();
			int m = 0;
			int d = 0;   
			Date date=new Date();
		    SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd");
		    String str[] = matter.format(date).split("-");
		   try{
			   m = Integer.parseInt(str[1].trim());
			   d = Integer.parseInt(str[2].trim());
		   }catch(Exception e){
			   logger.error("中奖日期转换出错！" + e.getMessage(), e);
		   }
			QueryBuilder qb = new QueryBuilder("SELECT * FROM lotteryaward where AwardQuantum =? ");
			String param = "";
			if(m==8){
				if(d>=2 && d<=16){
					param = "02";
				}else if(d>=17 && d<=19){
					param = "17";
				}else if(d>=20 && d<=30){
					param = "20";
				}
			}
			qb.add(param);
			DataTable dt = qb.executeDataTable();
			String detail = "10积分";
			String number = "0";
			String id = "0";
			switch(awards){
			 case 1:
				 id = dt.getString(0, 0);
				 detail = dt.getString(0, 1);
				 number = dt.getString(0, 2);
				 break;
			 case 2:
				 id = dt.getString(1, 0);
				 detail = dt.getString(1, 1);
				 number = dt.getString(1, 2);
				 break;
			 case 3:
				 id = dt.getString(2, 0);
				 detail = dt.getString(2, 1);
				 number = dt.getString(2, 2);
				 break;
			 case 4:
				 detail = "意外险10元代金券（100减10元）";
				 number = "1";
				 break;
			 case 5:
				 detail = "旅游险10元代金券（100减10元）";
				 number = "2";
				 break;
			 case 6:
				 detail = "家财险10元代金券（100减10元）";
				 number = "3";
				 break;
			 case 7:
				 detail = "10积分";
				 break;
			}
			// 中二，三，四等奖
			if(awards<=3){
				if("500积分".equals(detail)){
					//积分绑定会员 并更新积分流水记录表
					obtailIntegral(500,tran);
				}
				try{
					//奖品表 相应奖品数量减1
					LotteryAwardSchema LotteryAward = new LotteryAwardSchema();
					LotteryAward.setID(id);
					LotteryAward.setAwardQuantum(param);
					if(LotteryAward.fill()){
						int awardNum = Integer.parseInt(LotteryAward.getAwardNumber());
						if(awardNum<1){
							obtailIntegral(10,tran);
							detail = "10积分";
							map.put("angle", 177);
						}else{
							LotteryAward.setAwardNumber(String.valueOf(awardNum-1));
							tran.add(LotteryAward, Transaction.UPDATE);
						}
					}
				}catch(Exception e){
					logger.error(e.getMessage(), e);
				}
			// 中优惠券
			}else if(awards>=4 && awards<7){
				map = sendCoupon("activitySn"+GotType+ number);
				if("N".equals(map.get("status"))){
					map.put("detail", map.get("text"));
					return map;
				}
			// 中积分
			}else{
				//积分绑定会员 并更新积分流水记录表
				obtailIntegral(10,tran);
			}
			// 中奖信息表中数据更新
			lotteryWinner(date,tran,detail,"CJ");
			map.put("detail", detail);
			return map;
		}
		
		/**
		 * 
		* @Title: lotteryWinner 
		* @Description: TODO(中奖人相应信息) 
		* @return void    返回类型 
		* @author jiaomengying
		 */
		public void  lotteryWinner(Date date,Transaction tran,String detail,String gotType){
			HttpServletRequest request = getRequest();
			String memberId = (String) request.getSession().getAttribute(
					Member.LOGIN_MEMBER_ID_SESSION_NAME);
			
			//中奖信息
			memberSchema member = new memberSchema();
			member.setid(memberId);
			member.fill();
			LotteryWinnerSchema lotteryWinner = new LotteryWinnerSchema();
			lotteryWinner.setAward(detail);
			lotteryWinner.setWinnerEmail(member.getemail());
			lotteryWinner.setWinnerId(memberId);
			lotteryWinner.setWinnerMobil(member.getmobileNO());
			lotteryWinner.setWinnerName(member.getrealName());
			lotteryWinner.setWinTime(date);
			tran.add(lotteryWinner,Transaction.INSERT);
		}
		
		/**
		 * 
		* @Title: obtailIntegral 
		* @Description: TODO(抽奖奖励积分) 
		* @return void    返回类型 
		* @author jiaomengying
		 */
		public void obtailIntegral(int integral,Transaction tran){
			HttpServletRequest request = getRequest();
			String memberId = (String) request.getSession().getAttribute(
					Member.LOGIN_MEMBER_ID_SESSION_NAME);
			try{
				memberSchema member = new memberSchema();
				member.setid(memberId);
				member.fill();
				member.setcurrentValidatePoint(integral+member.getcurrentValidatePoint());
				tran.add(member,Transaction.UPDATE);
			}catch(Exception e){
				logger.error("抽奖奖励积分，绑定会员失败！" + e.getMessage(), e);
			}
			
			SDIntCalendarSchema calendar = new SDIntCalendarSchema();
			calendar.setID(String.valueOf(NoUtil.getMaxID("IntID")));
			calendar.setMemberId(memberId);
			calendar.setIntegral(String.valueOf(integral));
			calendar.setSource("18");// "18"-表示抽獎活動獎勵
			calendar.setManner("0");// 表示收入
			calendar.setStatus("0");
			calendar.setCreateDate(PubFun.getCurrentDate());
			calendar.setCreateTime(PubFun.getCurrentTime());
			tran.add(calendar,Transaction.INSERT);
		}

		/**
		 * 
		* @Title: shareTo 
		* @Description: TODO(点击分享，修改分享次数) 
		* @return void    返回类型 
		* @author jiaomengying
		 */
		public String shareTo(){
			Map<String, Object> map = new HashMap<String, Object>();
			HttpServletRequest request = getRequest();
			// 验证是否登录
			String memberId = (String) request.getSession().getAttribute(
					Member.LOGIN_MEMBER_ID_SESSION_NAME);
			if (StringUtil.isEmpty(memberId)) {
				map.put(STATUS, "notLogin");
				map.put("text", "请登录");
				return ajax(JSONObject.fromObject(map).toString(),"text/html");
			}
			
			memberSchema memberSchemas = new memberSchema();
			memberSchemas.setid(memberId);
			memberSet memberSets = memberSchemas.query();
			if ((null == memberSets) || (0 == memberSets.size())) {
				map.put(STATUS, "notLogin");
				map.put("text", "请登录");
				return ajax(JSONObject.fromObject(map).toString(),"text/html");
			}
			
			Date date=new Date();
		    SimpleDateFormat matter=new SimpleDateFormat("dd");
		    String currentDate = matter.format(date);
			LotteryInfoSchema lotteryInfo = new LotteryInfoSchema();
			Transaction tran = new Transaction();
			try{
				lotteryInfo.setMemberId(memberId);
				lotteryInfo.setGotType(GotType);
				lotteryInfo.setCurrentDay(currentDate);
				lotteryInfo.fill();
				int times = Integer.parseInt(lotteryInfo.getShareTimes())+1;
				lotteryInfo.setShareTimes(String.valueOf(times));
				tran.add(lotteryInfo,Transaction.UPDATE);
			}catch(Exception e){
				logger.error(e.getMessage(), e);
				map.put("text", "分享失败，请重新操作");
				return ajax(JSONObject.fromObject(map).toString(),"text/html");
			}
			if(tran.commit()){
				map.put("text", "分享成功");
			}else{
				map.put("text", "分享失败，请重新操作");
			}
			return ajax(JSONObject.fromObject(map).toString(),"text/html");
		}
		
		public String lotteryWinnerInfo(){
			Map<String,Object> map = new HashMap<String,Object>();
			DataTable dt = new QueryBuilder("SELECT * FROM lotterywinner ORDER BY wintime DESC LIMIT 0,20").executeDataTable();
			String content = "";
			if(dt!=null && dt.getRowCount()>0){
				int row = dt.getRowCount();
				for(int i=0;i<row;i++){
					String email = StringUtil.isEmpty(dt.getString(i, "WinnerEmail"))?dt.getString(i, "WinnerMobil"):dt.getString(i, "WinnerEmail");
					int start = 0;
					if(email.indexOf("@")!=-1){
						start = email.indexOf("@");
					}else{
						start = email.length()-4;
					}
					email = email.substring(0,4)+"XXXX"+email.substring(start,email.length());
					content+="<li title=\"恭喜"+email+"获得"+dt.getString(i, "Award")+"\">恭喜"+email+"获得"+dt.getString(i, "Award")+"</li>";
				}
			}
			map.put("content", content);
			return ajax(JSONObject.fromObject(map).toString(),"text/html");
		}
		public String getGotType() {
			return GotType;
		}

		public void setGotType(String gotType) {
			GotType = gotType;
		}
		
		/**
		 * 抽取红包
		* @Title: clickIntegral 
		* @Description: TODO(这里用一句话描述这个方法的作用) 
		* @return String    返回类型 
		* @author XXX
		 */
		
	public synchronized String clickIntegral() {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = getRequest();
		// 验证是否登录
		String memberId = (String) request.getSession().getAttribute(
				Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtil.isEmpty(memberId)) {
			map.put(STATUS, "notLogin");
			map.put("text", "请登录");
			return ajax(JSONObject.fromObject(map).toString(), "text/html");
		}

		memberSchema memberSchemas = new memberSchema();
		memberSchemas.setid(memberId);
		memberSet memberSets = memberSchemas.query();
		if ((null == memberSets) || (0 == memberSets.size())) {
			map.put(STATUS, "notLogin");
			map.put("text", "请登录");
			return ajax(JSONObject.fromObject(map).toString(), "text/html");
		}
		memberSchemas = memberSets.get(0);

		String sql = "select count(1) from lotteryInfo where GotType = 'Double11'";
		String sqlWhere = " and MemberId='" + memberId + "'";
		DataTable dt1 = new QueryBuilder(sql).executeDataTable();
		DataTable dt2 = new QueryBuilder(sql + sqlWhere).executeDataTable();
		if (dt2 == null || "0".equals(dt2.getString(0, 0))) {
			// 没领取过的用户 第一次领取积分红包
			int rowCount = Integer.parseInt(dt1.getString(0, 0)) + 1;
			int integeral = 0;
			String status = "Y";
			if (rowCount % 5000 == 0 && rowCount % 100 == 0) {
				integeral = 10000;
			} else if (rowCount % 5000 == 0) {
				integeral = 10000;
			} else if (rowCount % 100 == 0) {
				integeral = 1000;
				status = "X";
			} else {
				integeral = 100;
				status = "Z";
			}
			try {

				// 领取积分明细记录
				Date date = new Date();
				SimpleDateFormat matter = new SimpleDateFormat("dd");
				String currentDate = matter.format(date);
				LotteryInfoSchema lotteryInfo = new LotteryInfoSchema();
				Transaction tran = new Transaction();
				lotteryInfo.setMemberId(memberId);
				lotteryInfo.setCurrentDay(currentDate);
				lotteryInfo.setGotType("Double11");
				lotteryInfo.setGotValue(String.valueOf(integeral));
				tran.add(lotteryInfo, Transaction.INSERT);
				// 领取积分会员信息明细记录
				LotteryWinnerSchema lotteryWinner = new LotteryWinnerSchema();
				lotteryWinner.setAward("14-双11-" + integeral + "积分");
				lotteryWinner.setWinnerEmail(memberSchemas.getemail());
				lotteryWinner.setWinnerId(memberId);
				lotteryWinner.setWinnerMobil(memberSchemas.getmobileNO());
				lotteryWinner.setWinnerName(memberSchemas.getrealName());
				lotteryWinner.setWinTime(date);
				tran.add(lotteryWinner, Transaction.INSERT);
				// 记录积分轨迹
				SDIntCalendarSchema calendar = new SDIntCalendarSchema();
				calendar.setID(String.valueOf(NoUtil.getMaxID("IntID")));
				calendar.setMemberId(memberId);
				calendar.setIntegral(String.valueOf(integeral));
				calendar.setSource("19");// "19"-表示领取红包积分活动
				calendar.setManner("0");// 表示收入
				calendar.setStatus("0");
				calendar.setCreateDate(PubFun.getCurrentDate());
				calendar.setCreateTime(PubFun.getCurrentTime());
				tran.add(calendar, Transaction.INSERT);
				// 修改会员表中会员可用积分
				integeral += memberSchemas.getcurrentValidatePoint();
				memberSchemas.setcurrentValidatePoint(integeral);
				tran.add(memberSchemas, Transaction.UPDATE);

				if (tran.commit()) {
					map.put(STATUS, status);
				} else {
					map.put(STATUS, "N");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				map.put(STATUS, "N");
				return ajax(JSONObject.fromObject(map).toString(), "text/html");
			}

		} else {
			// 已经领取过 积分红包
			map.put(STATUS, "M");
		}
		return ajax(JSONObject.fromObject(map).toString(), "text/html");
	}
		
}
