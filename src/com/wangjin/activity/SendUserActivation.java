package com.wangjin.activity;

import cn.com.sinosoft.common.email.MessageConstant;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.UserActivationRecordSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SendUserActivation {
	private static final Logger logger = LoggerFactory.getLogger(SendUserActivation.class);

	public static String TestEmail="";//测试帐号 email
	public static String TestMobileNo="";//测试帐号 MobileNo
	
	/**
	 * @Title: activityActivationtimer
	 * @Description: TODO(接受并执行定时任务)
	 * @author guanyulong
	 */
	public void activityActivationtimer(List<Map<String, String>> activitySnlist){
		getTestMember();
		String runDateTime="";//执行时间
		String runActivity="";//活动编码
		String runType=""; // 6折扣  8自定义
		String runActivityEndtime="";//活动结束时间
		for(int i=0;i<activitySnlist.size();i++){
			runDateTime=activitySnlist.get(i).get("runtime");
			runType=activitySnlist.get(i).get("type");
			runActivity=activitySnlist.get(i).get("activitySn");
			runActivityEndtime=activitySnlist.get(i).get("endtime");
			Timer timer = new Timer(); 
			timer.schedule(new task(timer,runDateTime,runActivity,runType,runActivityEndtime), DateUtil.parse(runDateTime, "yyyy-MM-dd HH:mm:ss"));
			Object[] argArr = {runDateTime, runType, runActivity, runActivity};
			logger.info("活动发布，用户激活提醒时间：{}   type={}   activity={} 活动编码：{} 状态： 待执行", argArr);
		}
		
		
 
	}
	
	/**
	 * @Title: task
	 * @Description: TODO(定时任务)
	 * @author guanyulong
	 */
	   class task extends TimerTask {
		   Timer timer;
		   String runDateTime="";//执行时间
		   String runActivity="";//活动编码
		   String runType=""; // 6折扣  8自定义
		   String runActivityEndtime="";//活动结束时间
		   task(Timer timer, String tRunDateTime, String tRunActivity, String tRunType, String tRunActivityEndtime){     
			   this.timer = timer; 
			   runDateTime = tRunDateTime;
			   runActivity = tRunActivity;
			   runType = tRunType;
			   runActivityEndtime = tRunActivityEndtime;
	   }
	   public void run() {
			   SendAndSaveUserActivation(runActivity ,runType,runActivityEndtime,TestEmail,TestMobileNo);     
		   }  
	   }
	   
	   
	   
	/**
	 * @Title: SendAndSaveUserActivation
	 * @Description: TODO(开始执行用户激活提醒)
	 * @author guanyulong
	 */   
	public void SendAndSaveUserActivation(String activity,String type,String activityEndtime,String TestEmail,String TestMobileNo){
		SimpleDateFormat time = new SimpleDateFormat("yy-MM-dd HH:mm:ss");     
		logger.info("开始执行用户激活提醒...." +"提醒时间：{}活动编码{}", time.format(new Date()), activity);
		
		List<UserActivationRecordSchema> Collection = getCollection(getCollectionSql(activity,type,TestEmail,TestMobileNo));
		List<UserActivationRecordSchema> StayPay = getStayPay(getStayPaySql(activity,type,TestEmail,TestMobileNo));
		List<UserActivationRecordSchema> SaveUserActivation = MaxSet(Collection,StayPay);
		if(SaveUserActivation!=null && SaveUserActivation.size()>0){
			SendAndSave(activity,SaveUserActivation,activityEndtime);
		}else{
			logger.info("活动激活用户项目没有数据");
		}

	}
	
	
	/**
	 * @Title: getCollection
	 * @Description: TODO(取出收藏夹内满足激活条件的用户，订单信息)
	 * @return List<UserActivationRecordSchema> 返回类型
	 * @author guanyulong
	 */
	public List<UserActivationRecordSchema> getCollection(String sql) {
		List<UserActivationRecordSchema> Collection = new ArrayList<UserActivationRecordSchema>();
		UserActivationRecordSchema userActivationRecord = new UserActivationRecordSchema();
		DataTable dt_collection = new QueryBuilder(sql).executeDataTable();
		if (dt_collection.getRowCount() > 0) {
			int j = 0;// 记录同会员降价最多那行
			String useMemberid = dt_collection.getString(0, "MemberID"); // 会员id
			double Cheap = 0.00;
			double CollectionPrice = 0.00;// 收藏价格
			double payPrice = 0.00;// 现价
			//String sendWay = "";// 发送方式
			for (int i = 0; i < dt_collection.getRowCount(); i++) {
				CollectionPrice = Double.valueOf(dt_collection.get(i).getString("CollectionPrice"));
				// 折扣活动 降价=收藏价-现价 自定义活动 降价= 收藏价格-市价
				payPrice = Double.valueOf(dt_collection.get(i) .getString("ActivityData"));

				if ((CollectionPrice - payPrice) <= 0.00) {
					continue;
				}
				if (!useMemberid.equals(dt_collection.get(i).get("MemberID"))) {
					if(!"".equals(useMemberid) ){
					userActivationRecord = new UserActivationRecordSchema();
					userActivationRecord.setcategory("收藏");
					userActivationRecord.setactivationKey(dt_collection.get(j).getString("activationKey"));
					userActivationRecord.setmemberid(dt_collection.get(j).getString("MemberID"));
					userActivationRecord.setcreateDate(new Date());
					userActivationRecord.setbasePrice(dt_collection.get(j).getString("BasePremValue"));
					userActivationRecord.setpayPrice(dt_collection.get(j).getString("ActivityData"));
					userActivationRecord.setoldPrice(dt_collection.get(j).getString("CollectionPrice"));
					userActivationRecord.setProductGenera(dt_collection.get(j).getString("ProductGenera"));
					if (StringUtil.isNotEmpty(dt_collection.get(j).getString("mobileNo"))) {
						userActivationRecord.setsendWay("mobileNo");
						userActivationRecord.setsendTo(dt_collection.get(j).getString("mobileNo"));
						userActivationRecord.setproductUrl(Config.getValue("WapServerContext")+"/member/collection.shtml?banner_id=dx-scjiangjiatixing");
					} else {
						userActivationRecord.setsendWay("email");
						userActivationRecord.setsendTo(dt_collection.get(j).getString("email"));
						userActivationRecord.setproductUrl(Config.getServerContext()+"shop/stow!queryScan.action?banner_id=edm-scjiangjiatixing");
					}
					userActivationRecord.setsendStatus("N");// 还未发送
					userActivationRecord.setproductName(dt_collection.get(j) .getString("productname"));
					Collection.add(userActivationRecord);
					Cheap = 0.00;
					}

				}
				useMemberid = dt_collection.get(i).getString("MemberID");
				if (Cheap < (CollectionPrice - payPrice)) {
					Cheap = CollectionPrice - payPrice;
					j = i;
				}

			}
			CollectionPrice = Double.valueOf(dt_collection.get(j).getString("CollectionPrice"));
			// 折扣活动 降价=收藏价-现价 自定义活动 降价= 收藏价格-市价
			payPrice = Double.valueOf(dt_collection.get(j) .getString("ActivityData"));
			if ((CollectionPrice - payPrice) > 0.00) {
				userActivationRecord = new UserActivationRecordSchema();
				userActivationRecord.setcategory("收藏");
				userActivationRecord.setactivationKey(dt_collection.get(j).getString("activationKey"));
				userActivationRecord.setmemberid(dt_collection.get(j).getString( "MemberID"));
				userActivationRecord.setcreateDate(new Date());
				userActivationRecord.setbasePrice(dt_collection.get(j).getString( "BasePremValue"));
				userActivationRecord.setpayPrice(dt_collection.get(j).getString( "ActivityData"));
				userActivationRecord.setoldPrice(dt_collection.get(j).getString( "CollectionPrice"));
				userActivationRecord.setProductGenera(dt_collection.get(j) .getString("ProductGenera"));
				if (StringUtil.isNotEmpty(dt_collection.get(j).getString("mobileNo"))) {
					userActivationRecord.setsendWay("mobileNo");
					userActivationRecord.setsendTo(dt_collection.get(j).getString("mobileNo"));
					userActivationRecord.setproductUrl(Config.getValue("WapServerContext")+"/member/collection.shtml?banner_id=dx-scjiangjiatixing");
				} else {
					userActivationRecord.setsendWay("email");
					userActivationRecord.setsendTo(dt_collection.get(j).getString("email"));
					userActivationRecord.setproductUrl(Config.getServerContext()+"/shop/stow!queryScan.action?banner_id=edm-scjiangjiatixing");
				}
				userActivationRecord.setsendStatus("N");// 还未发送
				userActivationRecord.setproductName(dt_collection.get(j).getString( "productname"));
				Collection.add(userActivationRecord);
			}
		} else {
			Collection = null;
		}
		return Collection;
	}

	/**
	 * @Title: getCollectionSql
	 * @Description: TODO(取出收藏夹sql)
	 * @author guanyulong
	 */
	public String  getCollectionSql(String activity,String type ,String TestEmail,String TestMobileNo ){
		String sql = "SELECT DISTINCT  pc.ProductID, pro.productname,pc.ID AS activationKey,pc.MemberID,pro.remark4 AS BasePremValue, ";
		if(type.equals("6")){
			sql = sql + " IFNULL((SELECT s03.ActivityData FROM  SdActivityRule s03 WHERE  s01.activitySn=s03.activitysn AND CASE WHEN ( s03.MemberRule IS NOT NULL  AND s03.MemberRule != '') THEN"
					+ " (FIND_IN_SET((CASE WHEN m.vipFlag != 'Y'  THEN m.grade  ELSE 'VIP' END ) ,s03.MemberRule ))ELSE 1=1 END LIMIT 1) ,10)*pro.remark4/10  AS ActivityData,";
		}else if(type.equals("8")){
			sql = sql + " IFNULL((SELECT DISTINCT s03.ActivityData FROM  SdActivityRule s03 ,SdProductActivityLink s02,SDCouponActivityInfo s01 WHERE s01.ActivitySn=s02.ActivitySn AND s01.ActivitySn=s03.ActivitySn"
					+ " AND DATE_FORMAT(s01.starttime , '%Y-%m-%d %H:%i:%s') <=  DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s') AND DATE_FORMAT(s01.endtime , '%Y-%m-%d %H:%i:%s') >=  DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s') "
					+ " AND TYPE ='6' AND s01.status='3' AND s02.productid =pc.ProductID AND CASE WHEN ( s03.MemberRule IS NOT NULL  AND s03.MemberRule != '') THEN "
					+ "(FIND_IN_SET((CASE WHEN m.vipFlag != 'Y'  THEN m.grade  ELSE 'VIP' END ),s03.MemberRule ))ELSE 1=1 END LIMIT 1) ,10)*pro.remark4/10  AS ActivityData,";
		}
		sql= sql + " pc.CollectionPrice AS CollectionPrice ,pro.ProductGenera ,m.mobileNo ,m.email "
				+ " FROM ProductCollection pc, SDProduct pro, SdProductActivityLink s01, member m "
				+ " WHERE  pc.productId=pro.productId  AND  m.id = pc.MemberID  AND  s01.ProductID =pc.ProductID AND pro.IsPublish ='Y' "
				+ " AND s01.ActivitySn= '"+activity+"'  AND pc.CollectionPrice IS NOT NULL AND DATE_FORMAT(pc.createdate, '%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(DATE_ADD(NOW(), INTERVAL - 90 DAY), '%Y-%m-%d %H:%i:%s')"
				+ " AND m.fromwap IN('wj','wap_wx','wap_ht','kxb_app') AND !(m.mobileNo IS NULL AND m.email IS NULL) AND pro.ProductGenera NOT IN "
				+ " ( SELECT DISTINCT  pro1.ProductGenera  FROM sdorders s2 , SDInformation s3 ,SDProduct pro1  "
				+ " WHERE s2.memberid=pc.memberid AND s3.productid=pro1.productid AND s2.ordersn=s3.ordersn AND s2.orderstatus IN ('7','10','12','14')  "
				+ " AND DATE_FORMAT(s2.modifydate, '%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(DATE_ADD(NOW(), INTERVAL - 30 DAY), '%Y-%m-%d %H:%i:%s') AND DATE_FORMAT(s2.modifydate, '%Y-%m-%d %H:%i:%s') <= DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s') ) "
				+ " AND NOT EXISTS( SELECT 1 FROM UserActivationRecord u "
				+ " WHERE DATE_FORMAT(u.createdate, '%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(DATE_ADD(NOW(), INTERVAL - 30 DAY), '%Y-%m-%d %H:%i:%s')  AND u.memberid = pc.memberId AND  u.sendstatus='Y' ) ";
				if(StringUtil.isNotEmpty(TestEmail) && !"''".equals(TestEmail)){
					sql = sql +" AND m.email NOT IN ("+TestEmail+") ";
				}
				if(StringUtil.isNotEmpty(TestMobileNo) && !"''".equals(TestMobileNo)){
					sql = sql +" AND m.mobileNo NOT IN ("+TestMobileNo+") ";
				}
				sql = sql + " ORDER BY pc.memberId DESC";
		return sql;
		
	}
	
	/**
	 * @Title: getStayPay
	 * @Description: TODO(取出暂存、待支付满足激活条件的用户，订单信息)
	 * @return List<UserActivationRecordSchema> 返回类型
	 * @author guanyulong
	 */
	public List<UserActivationRecordSchema> getStayPay(String sql){
	List<UserActivationRecordSchema> Collection = new ArrayList<UserActivationRecordSchema>();
	UserActivationRecordSchema userActivationRecord = new UserActivationRecordSchema();
	DataTable dt_collection=new QueryBuilder(sql).executeDataTable();
	if(dt_collection.getRowCount()>0){
		Map<String, String> InsuranceCompanyDiscount = getInsuranceCompanyDiscount(dt_collection);
		int j=0;//记录同会员降价最多那行
		String useMemberid=""; //会员id
		double Cheap = 0.00;
		double oldPrice=0.00;//暂存(应支付价格)
		String ProductDiscount="0.00";//保险公司折扣
		double payPrice=0.0;//现在应支付价格
		double DiscountAll = 1.00;
		for(int i=0;i<dt_collection.getRowCount() ;i++){
			ProductDiscount =InsuranceCompanyDiscount.get(dt_collection.get(i).getString("ProductID"));
			if(StringUtil.isEmpty(ProductDiscount)){
				ProductDiscount="1";//不存在保险公司打折
			}
			oldPrice = Double.valueOf(dt_collection.get(i).getString("oldPrice"));
			DiscountAll =  Double.valueOf(ProductDiscount) * Double.valueOf(dt_collection.get(i).getString("ActivityData"))/10.00 ; //保险公司折扣 *本地折扣
			//折扣活动 降价=收藏价-现价  自定义活动 降价= 收藏价格-市价
		    payPrice= Double.valueOf(dt_collection.get(i).getString("BasePremValue")) * DiscountAll;  //原价*折扣
			if ((oldPrice - payPrice) <= 0.00) {
				continue;
			}
			if(!useMemberid.equals(dt_collection.get(i).get("MemberID"))){
				Cheap=0.00;
				if(!"".equals(useMemberid) ){
					userActivationRecord = new UserActivationRecordSchema();
					userActivationRecord.setactivationKey(dt_collection.get(j).getString("activationKey"));
					userActivationRecord.setmemberid(dt_collection.get(j).getString("MemberID"));
					userActivationRecord.setcreateDate(new Date());
					userActivationRecord.setbasePrice(dt_collection.get(j).getString("BasePremValue"));
					userActivationRecord.setpayPrice(String.valueOf(payPrice));
					userActivationRecord.setoldPrice(dt_collection.get(j).getString("oldPrice"));
					userActivationRecord.setProductGenera(dt_collection.get(j).getString("ProductGenera"));
					if (StringUtil.isNotEmpty(dt_collection.get(j).getString("mobileNo"))) {
						userActivationRecord.setsendWay("mobileNo");
						userActivationRecord.setsendTo(dt_collection.get(j).getString("mobileNo"));
						if("4".equals(dt_collection.get(j).getString("orderStatus"))){
						userActivationRecord.setcategory("暂存");
						userActivationRecord.setproductUrl(Config.getValue("WapServerContext")+"/buy/update.shtml?"
								+ "ordersn="+dt_collection.get(j).getString("orderSn")
								+"&KID="+StringUtil.md5Hex(PubFun.getKeyValue() + dt_collection.get(j).getString("orderSn"))
								+"&banner_id=dx-dzfjiangjiatixing");
						}else{
							userActivationRecord.setcategory("待支付");
							userActivationRecord.setproductUrl(Config.getValue("WapServerContext")+"/buy/paysure.shtml?"
									+ "ordersn="+dt_collection.get(j).getString("orderSn")
									+"&KID="+StringUtil.md5Hex(PubFun.getKeyValue() + dt_collection.get(j).getString("orderSn"))
									+"&banner_id=dx-dzfjiangjiatixing");
						}
					} else {
						userActivationRecord.setsendWay("email");
						userActivationRecord.setsendTo(dt_collection.get(j).getString("email"));
						if("4".equals(dt_collection.get(j).getString("orderStatus"))){
							userActivationRecord.setcategory("暂存");
							userActivationRecord.setproductUrl(Config.getServerContext()
									+"/shop/order_config_new!keepInput.action?"
									+ "orderSn="+dt_collection.get(j).getString("orderSn")
									+"&KID="+StringUtil.md5Hex(PubFun.getKeyValue() + dt_collection.get(j).getString("orderSn"))
									+"&banner_id=edm-dzfjiangjiatixing");
						}else{//=5
							userActivationRecord.setcategory("待支付");
							userActivationRecord.setproductUrl(Config.getServerContext()+ 
									"/shop/order_config_new!pay.action?"
									+ "orderSn="+dt_collection.get(j).getString("orderSn")
									+"&orderId="+dt_collection.get(j).getString("activationKey")
									+"&banner_id=edm-dzfjiangjiatixing");
						}
					}
					userActivationRecord.setsendStatus("N");//还未发送
					userActivationRecord.setproductName(dt_collection.get(j).getString("productname"));
					Collection.add(userActivationRecord);
					UpdateOrdersPrice(dt_collection.get(i).getString("orderSn"),DiscountAll);
				}

			}
			useMemberid=dt_collection.get(i).getString("MemberID");
			if(Cheap<(oldPrice- payPrice)){
				Cheap=oldPrice- payPrice;
				j=i;
			}
			
			
		}
		ProductDiscount =InsuranceCompanyDiscount.get(dt_collection.get(j).getString("ProductID"));
		if(StringUtil.isEmpty(ProductDiscount)){
			ProductDiscount="1";//不存在保险公司打折
		}
		DiscountAll =  Double.valueOf(ProductDiscount) * Double.valueOf(dt_collection.get(j).getString("ActivityData"))/10.00 ; //保险公司折扣 *本地折扣
		oldPrice = Double.valueOf(dt_collection.get(j).getString("oldPrice"));
	    payPrice= Double.valueOf(dt_collection.get(j).getString("BasePremValue")) *  DiscountAll;
		if ((oldPrice - payPrice) > 0.00 ) {
			userActivationRecord = new UserActivationRecordSchema();
			userActivationRecord.setactivationKey(dt_collection.get(j).getString("activationKey"));
			userActivationRecord.setmemberid(dt_collection.get(j).getString("MemberID"));
			userActivationRecord.setcreateDate(new Date());
			userActivationRecord.setbasePrice(dt_collection.get(j).getString("BasePremValue"));
			userActivationRecord.setpayPrice(String.valueOf(payPrice));
			userActivationRecord.setoldPrice(dt_collection.get(j).getString("oldPrice"));
			userActivationRecord.setProductGenera(dt_collection.get(j).getString("ProductGenera"));
			if (StringUtil.isNotEmpty(dt_collection.get(j).getString("mobileNo"))) {
				userActivationRecord.setsendWay("mobileNo");
				userActivationRecord.setsendTo(dt_collection.get(j).getString("mobileNo"));
				if("4".equals(dt_collection.get(j).getString("orderStatus"))){
				userActivationRecord.setcategory("暂存");
				userActivationRecord.setproductUrl(Config.getValue("WapServerContext")+"/buy/update.shtml?"
						+ "ordersn="+dt_collection.get(j).getString("orderSn")
						+"&KID="+StringUtil.md5Hex(PubFun.getKeyValue() + dt_collection.get(j).getString("orderSn"))
						+"&banner_id=dx-dzfjiangjiatixing");
				}else{
					userActivationRecord.setcategory("待支付");
					userActivationRecord.setproductUrl(Config.getValue("WapServerContext")+"/buy/paysure.shtml?"
							+ "ordersn="+dt_collection.get(j).getString("orderSn")
							+"&KID="+StringUtil.md5Hex(PubFun.getKeyValue() + dt_collection.get(j).getString("orderSn"))
							+"&banner_id=dx-dzfjiangjiatixing");
				}
			} else {
				userActivationRecord.setsendWay("email");
				userActivationRecord.setsendTo(dt_collection.get(j).getString("email"));
				if("4".equals(dt_collection.get(j).getString("orderStatus"))){
					userActivationRecord.setcategory("暂存");
					userActivationRecord.setproductUrl(Config.getServerContext()
							+"/shop/order_config_new!keepInput.action?"
							+ "orderSn="+dt_collection.get(j).getString("orderSn")
							+"&KID="+StringUtil.md5Hex(PubFun.getKeyValue() + dt_collection.get(j).getString("orderSn"))
							+"&banner_id=edm-dzfjiangjiatixing");
				}else{//=5
					userActivationRecord.setcategory("待支付");
					userActivationRecord.setproductUrl(Config.getServerContext()+ 
							"/shop/order_config_new!pay.action?"
							+ "orderSn="+dt_collection.get(j).getString("orderSn")
							+"&orderId="+dt_collection.get(j).getString("activationKey")
							+"&banner_id=edm-dzfjiangjiatixing");
				}
			}
			userActivationRecord.setsendStatus("N");//还未发送
			userActivationRecord.setproductName(dt_collection.get(j).getString("productname"));
			Collection.add(userActivationRecord);
			UpdateOrdersPrice(dt_collection.get(j).getString("orderSn"),DiscountAll);
		}
	
	}else{
		Collection=null;
	}
	return Collection;
	}
	
	/**
	 * @Title: getStayPaySql
	 * @Description: TODO(取出待支付、暂存sql)
	 * @author guanyulong
	 */
	public String  getStayPaySql(String activity,String type ,String TestEmail,String TestMobileNo ){
		String sql=" SELECT DISTINCT  o2.ProductID, pro.productname, o1.ID AS activationKey,o1.ordersn,o1.MemberID,o1.productTotalPrice  AS BasePremValue,o1.payprice  AS oldPrice, ";
		if(type.equals("6")){
			sql=sql + " IFNULL((SELECT s03.ActivityData FROM  SdActivityRule s03 WHERE  s01.activitySn=s03.activitysn  AND CASE WHEN ( s03.MemberRule IS NOT NULL  AND s03.MemberRule != '') THEN"
					+ " (FIND_IN_SET((CASE WHEN m.vipFlag != 'Y'  THEN m.grade  ELSE 'VIP' END ),s03.MemberRule ))ELSE 1=1 END LIMIT 1) ,10)  AS ActivityData, ";
		}else if(type.equals("8")){
			sql=sql + " IFNULL((SELECT DISTINCT s03.ActivityData FROM  SdActivityRule s03 ,SdProductActivityLink s02,SDCouponActivityInfo s01"
					+ " WHERE s01.ActivitySn=s02.ActivitySn AND s01.ActivitySn=s03.ActivitySn AND s01.TYPE ='6' AND s01.status='3'"
					+ " AND DATE_FORMAT(s01.starttime , '%Y-%m-%d %H:%i:%s') <=  DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')  AND DATE_FORMAT(s01.endtime , '%Y-%m-%d %H:%i:%s') >=  DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s')"
					+ " AND s02.productid =o2.ProductID AND CASE WHEN ( s03.MemberRule IS NOT NULL  AND s03.MemberRule != '') THEN"
					+ " (FIND_IN_SET((CASE WHEN m.vipFlag != 'Y'  THEN m.grade  ELSE 'VIP' END ),s03.MemberRule ))ELSE 1=1 END LIMIT 1) ,10)  AS ActivityData,";
		}
		sql=sql + "pro.ProductGenera,m.mobileNo,m.email,o1.orderStatus"
				+ " FROM sdorders o1,SDInformation o2, SDProduct pro, SdProductActivityLink s01,member m"
				+ " WHERE   o2.productId=pro.productId AND o1.ordersn=o2.ordersn AND o1.orderStatus IN ('4','5')"
				+ " AND  s01.ProductID =o2.ProductID AND  m.id = o1.MemberID AND pro.IsPublish ='Y' "
				+ " AND s01.ActivitySn= '"+activity+"'"
				+ " AND DATE_FORMAT(o1.createdate, '%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(DATE_ADD(NOW(), INTERVAL - 60 DAY), '%Y-%m-%d %H:%i:%s')"
				+ " AND m.fromwap IN('wj','wap_wx','wap_ht','kxb_app')  AND !(m.mobileNo IS NULL AND m.email IS NULL) AND pro.ProductGenera NOT IN "
				+ " ( SELECT DISTINCT  pro1.ProductGenera  FROM sdorders s2 , SDInformation s3 ,SDProduct pro1  "
				+ " WHERE s2.memberid=o1.memberid AND s3.productid=pro1.productid AND s2.ordersn=s3.ordersn AND s2.orderstatus IN ('7','10','12','14')  "
				+ " AND DATE_FORMAT(s2.modifydate, '%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(DATE_ADD(NOW(), INTERVAL - 30 DAY), '%Y-%m-%d %H:%i:%s') AND DATE_FORMAT(s2.modifydate, '%Y-%m-%d %H:%i:%s') <= DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s') ) "
				+ " AND NOT EXISTS( SELECT 1 FROM UserActivationRecord u WHERE DATE_FORMAT(u.createdate, '%Y-%m-%d %H:%i:%s') >= DATE_FORMAT(DATE_ADD(NOW(), INTERVAL - 30 DAY), '%Y-%m-%d %H:%i:%s') "
				+ " AND u.memberid = o1.memberId AND  u.sendstatus='Y' ) ";
		if(StringUtil.isNotEmpty(TestEmail) && !"''".equals(TestEmail)){
			sql = sql +" AND m.email NOT IN ("+TestEmail+") ";
		}
		if(StringUtil.isNotEmpty(TestMobileNo) && !"''".equals(TestMobileNo) ){
			sql = sql +" AND m.mobileNo NOT IN ("+TestMobileNo+") ";
		}
		sql = sql + " ORDER BY o1.memberId DESC ";
		return sql;
	}
	
	/**
	 * @Title: MaxSet
	 * @Description: TODO(记录信息并发送信息)
	 * @return void 返回类型
	 * @author guanyulong
	 */
	public List<UserActivationRecordSchema> MaxSet(List<UserActivationRecordSchema> Collection ,List<UserActivationRecordSchema> StayPay ){
		List<UserActivationRecordSchema> MaxSet = new ArrayList<UserActivationRecordSchema>();
		boolean isSaved = true;
		if((StayPay==null || StayPay.size()<=0)&&(Collection==null || Collection.size()<=0)){
			return null;
		}
		if(StayPay==null || StayPay.size()<=0){
			MaxSet.addAll(Collection);
			return MaxSet;
		}else if(Collection==null || Collection.size()<=0){
			MaxSet.addAll(StayPay);
			return MaxSet;
		}
		MaxSet.addAll(StayPay);
		for (UserActivationRecordSchema coll : Collection) {
			for (UserActivationRecordSchema pay : StayPay) {
				if(coll.getmemberid().equals(pay.getmemberid())) {//收藏和暂存会员都存在
					isSaved = false;
					break;
				}
			}
			if(isSaved){
				MaxSet.add(coll);
			}
		}

		return MaxSet;
		 
	}
	
	/**
	 * @Title: MaxSet
	 * @Description: TODO(发送邮件或短信)
	 * @return void 返回类型
	 * @author guanyulong
	 */
	public void SendAndSave(String activitySn,List<UserActivationRecordSchema> saveUserActivation,String activityEndtime){
		for(int i=0;i<saveUserActivation.size();i++){
			saveUserActivation.get(i).setid(NoUtil.getMaxNo("userActivationRecord"));
			saveUserActivation.get(i).setactivitySn(activitySn);
			try{
				sendMassage(saveUserActivation.get(i),activityEndtime);
				saveUserActivation.get(i).setsendStatus("Y");
				saveUserActivation.get(i).insert();
			}catch(Exception e){
				logger.error("信息发送失败 ："+e.getMessage(), e);
				saveUserActivation.get(i).setsendStatus("N");
				saveUserActivation.get(i).insert();
			}
		}
		logger.info("活动激活用户结束");

	}
	/**
	 * @Title: MaxSet
	 * @Description: TODO(发送邮件或短信)
	 * @return void 返回类型
	 * @author guanyulong
	 */
	public void sendMassage(UserActivationRecordSchema saveUserActivation,String activityEndtime){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			saveUserActivation.setoldPrice(new DecimalFormat("######0.00").format(Double.valueOf(saveUserActivation.getoldPrice())));
			saveUserActivation.setpayPrice(new DecimalFormat("######0.00").format(Double.valueOf(saveUserActivation.getpayPrice())));
			saveUserActivation.setbasePrice(new DecimalFormat("######0.00").format(Double.valueOf(saveUserActivation.getbasePrice())));
			activityEndtime = formatter.format(formatter.parse(activityEndtime));
		} catch (ParseException e) {//e.printStackTrace();
			logger.error("终止日期或价格有误" + e.getMessage(), e);
		}
		UserActivationRecordSchema userActivationRecord = new UserActivationRecordSchema();
		String productUrl = "";
		String sendway = "";
		String sendTo = "";
		String massage = "";
		String cheap = "";

		userActivationRecord=saveUserActivation;
		cheap = String.valueOf(new DecimalFormat("######0.00").format(Double.valueOf(userActivationRecord.getoldPrice()) - Double.valueOf(userActivationRecord.getpayPrice())));
		sendway=userActivationRecord.getsendWay();
		sendTo=userActivationRecord.getsendTo();
		productUrl=userActivationRecord.getproductUrl().replace("//", "/").replace("http:/", "http://").replace("https:/", "https://");
		if(sendway.contains("mobileNo")){//发送短信
			if("收藏".equals(userActivationRecord.getcategory())){
				massage = "亲爱的会员:您收藏的"+userActivationRecord.getproductName()+"降价了"+cheap+"元，快来看一看吧！活动截止日期为:"+activityEndtime+"，详情登陆收藏夹:"+productUrl;
			}else{//暂存
				massage = "亲爱的会员:您的"+userActivationRecord.getproductName()+"订单已优惠("+userActivationRecord.getoldPrice()+"-"+userActivationRecord.getpayPrice()+")元，活动截止日期为:"+activityEndtime+",详情查看 "+productUrl;
			}
			ActionUtil.sendGeneralSms(sendTo, massage);
		}else{//发送email
			String sendWay = null;
			if("收藏".equals(userActivationRecord.getcategory())){
				sendWay = "收藏夹";
				massage= "您收藏的"+userActivationRecord.getproductName()+"降价了"+cheap+"元，快来看一看吧！活动截止日期为:"+activityEndtime+"，详情登陆<a href='"+productUrl+"'>收藏夹</a>";
			}else{//暂存
				sendWay = "详情查看";
				massage= "您的"+userActivationRecord.getproductName()+"订单已优惠("+userActivationRecord.getoldPrice()+"-"+userActivationRecord.getpayPrice()+")元，活动截止日期为:"+activityEndtime+",<a href='"+productUrl+"'>详情查看</a>";
			}
			Map<String,Object> mailParam = new HashMap<String,Object>();
			mailParam.put(MessageConstant.PARAM_SUBJECT_NAME, "开心保-降价提醒邮件");
			mailParam.put("massage", massage);
			mailParam.put("productUrl", productUrl);
			mailParam.put("sendWay", sendWay);
			ActionUtil.sendMail("wj00411", sendTo, mailParam);
		}
		saveUserActivation.setsandMassage(massage);
	}
	
	/**
	 * @Title: getInsuranceCompanyDiscount
	 * @Description: TODO(取保险公司折扣)
	 * @return void 返回类型
	 * @author guanyulong
	 */
	public Map<String, String> getInsuranceCompanyDiscount(DataTable dt){
		String ProductIDs="";
		String ProductID="";
		String Discount="";
		Map<String, String> InsuranceCompanyDiscount = new HashMap<String, String>();
		for(int i=0;i<dt.getRowCount();i++){
			if(!ProductIDs.contains(dt.get(i).getString("ProductID"))){		
				ProductIDs = ProductIDs+ "'"+dt.get(i).getString("ProductID")+"',";
			}
		}
		ProductIDs = ProductIDs.substring(0, ProductIDs.length()-1); 
		DataTable dt2 = new QueryBuilder(" SELECT DISTINCT z2.TextValue,z1.prop4 FROM zcarticle z1,zdcolumnvalue z2 WHERE z1.id = z2.relaid AND z1.prop4 in("+ProductIDs+")  AND ColumnCode = 'DiscountRate'  ").executeDataTable();
		for(int i=0;i<dt2.getRowCount();i++){
			ProductID = dt2.get(i).getString("prop4");
			if(StringUtil.isNotEmpty(dt2.get(i).getString("TextValue"))){
				Discount = dt2.get(i).getString("TextValue");
			}else{
				Discount ="1";
			}
			InsuranceCompanyDiscount.put(ProductID, Discount);
		}
		
		return InsuranceCompanyDiscount; 
		
	}
	

	/**
	 * @Title: getTestMember
	 * @Description: TODO(取出测试账号信息)
	 * @author guanyulong
	 */
	public void getTestMember(){
		String TestMember[] = Config.getValue("TestMember").split(";");
		String TestEmail = "";
		String TestMobileNo = "";
		for(int i=0;i<TestMember.length;i++){
			if(TestMember[i].contains("@")){
				TestEmail=TestEmail+"'"+TestMember[i]+"',";
			}else{
				TestMobileNo=TestMobileNo+"'"+TestMember[i]+"',";
			}
		}
		if(StringUtil.isNotEmpty(TestEmail)){
			TestEmail=TestEmail.substring(0,TestEmail.length()-1);
		}
		if(StringUtil.isNotEmpty(TestMobileNo)){
			TestMobileNo=TestMobileNo.substring(0,TestMobileNo.length()-1);
		}
		this.TestEmail = TestEmail;
		this.TestMobileNo = TestMobileNo;
	}
	
	
	public void UpdateOrdersPrice(String ordersn,double DiscountAll){
		Transaction trans = new Transaction();
		String SDInformationRiskTypeSQL = " SELECT id,productPrice FROM  SDInformationRiskType WHERE ordersn='"+ordersn+"'";
		String SDInformationInsuredSQL= " SELECT id,recognizeeTotalPrem FROM  SDInformationInsured WHERE ordersn='"+ordersn+"'";
		String SDOrdersSQL = "  SELECT id,productTotalPrice FROM sdorders WHERE ordersn='"+ordersn+"'";
		DataTable dt1=new QueryBuilder(SDInformationRiskTypeSQL).executeDataTable();
		String updateSql="";
		for(int x=0;x<dt1.getRowCount();x++){
			updateSql="UPDATE SDInformationRiskType SET timePrem = '"
		               +new DecimalFormat("######0.00").format(Double.valueOf(dt1.get(x).getString("productPrice"))*DiscountAll)
		               +"' WHERE id='"+dt1.get(x).getString("id")+"'";
			trans.add(new QueryBuilder(updateSql));
			
		}
		DataTable dt2=new QueryBuilder(SDInformationInsuredSQL).executeDataTable();
		for(int x=0;x<dt2.getRowCount();x++){
			updateSql="UPDATE SDInformationInsured SET discountPrice = '"
		               +new DecimalFormat("######0.00").format(Double.valueOf(dt2.get(x).getString("recognizeeTotalPrem"))*DiscountAll)
		               +"' WHERE id='"+dt2.get(x).getString("id")+"'";
			trans.add(new QueryBuilder(updateSql));
			
		}
		DataTable dt3=new QueryBuilder(SDOrdersSQL).executeDataTable();
		updateSql="UPDATE sdorders SET payprice = '"
	               +new DecimalFormat("######0.00").format(Double.valueOf(dt3.get(0).getString("productTotalPrice"))*DiscountAll)
	               +"' WHERE id='"+dt3.get(0).getString("id")+"'";
		trans.add(new QueryBuilder(updateSql));
		if(!trans.commit()){
			logger.error("价格更新失败");
		}
	}
	
}
