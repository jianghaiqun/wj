package cn.com.sinosoft.util;

import cn.com.sinosoft.action.shop.BaseWapShopAction;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.service.OrderConfigNewService;
import cn.com.sinosoft.service.SDOrderService;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * wap核保工具类
 */
public class WapUnderWritingUtil {
	private static final Logger logger = LoggerFactory.getLogger(WapUnderWritingUtil.class);
    /**
     * Wap站内部核保方法
     * @return 
     */
    public static List<String> underWriting (Map<String,Object> tPARAMETERS, Map<String, Object> mServiceMap) {
    	
    	logger.info("---------开始进行内部核保---------");
    	//TODO
		logger.info("---------内部核保入参---------");
    	//System.out.println(tPARAMETERS);
    	
    	List<String> errCodes = new ArrayList<String>();
    	
    	try{
    		
	    	SDOrderService sdorderService = (SDOrderService) mServiceMap.get("SDOrderService");
	    	OrderConfigNewService orderConfigNewService = (OrderConfigNewService) mServiceMap.get("OrderConfigNewService");
	    	   	
	    	//解析入参
	    	//投保要素
	    	Map<String, Object> tPolicyInfo = BaseWapShopAction.getJsonToListMap("PolicyInfo", tPARAMETERS).get(0);
	    	//投保人信息
			Map<String, Object> tHolderInfo = BaseWapShopAction.getJsonToListMap("HolderInfo", tPARAMETERS).get(0);
			//多被保人信息
			List<Map<String, Object>> dtInsuredInfos = BaseWapShopAction.getJsonToListMap("InsuredInfos", tPARAMETERS);
			String premium = tPolicyInfo.get("Premium").toString();
			BigDecimal mTotalAmnt = new BigDecimal("0.00");
			for(Map<String, Object> tInsuredInfos : dtInsuredInfos){
				//产品编码
				String productId = tPolicyInfo.get("ProdCode").toString();
				
				Map<String, Object> params = sdorderService.getProductInformation(productId, "N", "");
				//保费试算
				String[] baseInformations = (String[])params.get("baseInformation");
		    	SDInformation sdinf = WapShoppingUtil.getSDInformation(tPolicyInfo,params);
				
				//保单起期
				Date policyDateFrom = DateUtil.parse(tPolicyInfo.get("PolicyDateFrom").toString(),"yyyy-MM-dd HH:mm:ss");
				//保单止期
				Date policyDateTo = DateUtil.parse(tPolicyInfo.get("PolicyDateTo").toString(),"yyyy-MM-dd HH:mm:ss");
				
				//校验保险起期与保险止期
				errCodes = compare_date(policyDateFrom,policyDateTo,errCodes);
				
				//投保人身份证校验
				errCodes = Identity("0",String.valueOf(tHolderInfo.get("AppntPayperType")),String.valueOf(tHolderInfo.get("AppntPayperNumber")),errCodes);
				
				//投保人年龄校验
				errCodes = ApplicantAge(String.valueOf(tHolderInfo.get("AppntBirthday")),errCodes);
				
				//投被保人关系
				errCodes = Relation(String.valueOf(tInsuredInfos.get("RecognizeeRelationShipCode")),productId,errCodes);	
				
				//投保人性别校验
				errCodes = checkSex(baseInformations,errCodes,String.valueOf(tInsuredInfos
						.get("IsHolderSelf")),String.valueOf(tHolderInfo.get("AppnttGender")),String.valueOf(tInsuredInfos.get("RecognizeeGenderName")));
				//被保人身份证校验
				errCodes = Identity("1",String.valueOf(tInsuredInfos.get("RecognizeePayperType")),String.valueOf(tInsuredInfos.get("RecognizeePayperNumber")),errCodes);
				
				/*//购买份数
				int buyCopies = Integer.parseInt(String.valueOf(tInsuredInfos.get("BuyCopies")));
				//限购份数
				int limitCount = dealLimitCount(sdinf, orderConfigNewService);*/
				
				/*//限购份数校验
				if(buyCopies > limitCount){
					errCodes.add("UnderWriting000023");
				}*/
				
				Map<String, Object> paramter = new HashMap<String, Object>();
				paramter = sdorderService.getProductInformation(productId, "N", "");// 产品ID
				
				// 获取投保人表信息 sdInformationAppnt
				SDInformationAppnt sdInformationAppnt = WapShoppingUtil.getSDInformationAppnt(
						tHolderInfo, paramter,mServiceMap,tPolicyInfo.get("ProdCode").toString());
				
				List<SDInformationInsured> insuredList = WapShoppingUtil.getSDInformationInsured(dtInsuredInfos, mServiceMap, tPolicyInfo, sdInformationAppnt);
				//被保人限购份数校验
				if(!isProductDownShelf(sdinf,insuredList)){
					errCodes.add("UnderWriting000023");
				}
				
				String flightTime = "";
				if(StringUtil.isNotEmpty(String.valueOf(tInsuredInfos.get("TakeOffTime")))){
					flightTime = String.valueOf(tInsuredInfos.get("TakeOffTime"));
					//起飞时间
					errCodes = DepartureTime(flightTime,policyDateFrom,policyDateTo,errCodes);
				} 
				
				List<OrderRiskAppFactor> riskAppFactior = (List<OrderRiskAppFactor>)params.get("riskAppFactor");
				List<OrderDutyFactor> dutyFactor = (List<OrderDutyFactor>)params.get("dutyFactor");
				List<SDInformationInsured> insuredlist = new ArrayList<SDInformationInsured>();
				SDInformationInsured sdInsured = new SDInformationInsured();
				if(StringUtil.isNotEmpty(String.valueOf(tInsuredInfos.get("RecognizeeBirthday")))){
					sdInsured.setRecognizeeBirthday(String.valueOf(tInsuredInfos.get("RecognizeeBirthday")));
				}else{
					sdInsured.setRecognizeeBirthday(String.valueOf(tHolderInfo.get("AppntBirthday")));
				}
				insuredlist.add(sdInsured);
				BigDecimal totalAmnt = WapShoppingUtil.relCalPrem(baseInformations, riskAppFactior, dutyFactor, sdinf, insuredlist, orderConfigNewService, sdorderService);
				mTotalAmnt = mTotalAmnt.add(totalAmnt);
				/*//实际支付金额
				String tActualAmountPaid = String.valueOf(tPolicyInfo.get("ActualAmountPaid"));
				//活动优惠（打折、满减）
				String tCouponAmount = String.valueOf(tPolicyInfo.get("CouponAmount"));
				//优惠劵优惠
				String tPointAmount = String.valueOf(tPolicyInfo.get("PointAmount"));
				//积分抵值优惠
				String tActiveAmount = String.valueOf(tPolicyInfo.get("ActiveAmount"));*/
				
				if(errCodes!=null && errCodes.size()>=1){
					break;
				}
			}
			
			BigDecimal newPrem = new BigDecimal(premium).setScale(2, BigDecimal.ROUND_HALF_UP);
			if(newPrem.compareTo(mTotalAmnt)!=0){
				errCodes.add("UnderWriting000026");
			}
			logger.info("---------内部核保完毕---------");
    	}catch(Exception e){
			logger.error("wap站内部核保模块异常"+e.getMessage(), e);
    		errCodes.clear();
    		errCodes.add("G000001");
    		return errCodes;
    	}
    	
    	return errCodes;
    }
    
    /**
	 * 比较当前时间和保险起期，保险止期和保险起期
	 * @param idCard 身份证号码
	 * @return 是否正确
	 */
	 public static List<String> compare_date(Date policyDateFrom,Date policyDateTo,List<String> errCodes) {
	    
        Date now =  new Date();
        if (policyDateFrom.getTime() < now.getTime()) {
        	errCodes.add("UnderWriting000001");
        	return errCodes;
        }
        if(policyDateTo.getTime() < policyDateFrom.getTime()){
        	errCodes.add("UnderWriting000002");
        	return errCodes;
        }
	     
        return errCodes;
    }
	 
	 /**
	 * 身份证号码校验
	 * @param idflag 0：投保人   1:被保人
	 * @param idtype 证件类型
	 * @param idCard 身份证号码
	 * @return 是否正确
	 */
	public static List<String> Identity(String idflag,String idtype,String idCard,List<String> errCodes){
			if("身份证".equals(idtype)) {
			
				Calendar c=Calendar.getInstance();//获得系统当前日期
				int  year=c.get(Calendar.YEAR);
				int  month=c.get(Calendar.MONTH)+1;//系统日期从0开始算起
				int  day=c.get(Calendar.DAY_OF_MONTH);
				
			 	String yyyy; //年
				String mm; //月
				String dd; //日
					
			    String id=idCard;
			    int id_length=id.length();
		
			    if (id_length==0)
			    {
			    	if("0".equals(idflag)){
			    		errCodes.add("UnderWriting000003");
			    	}
			    	if("1".equals(idflag)){
			    		errCodes.add("UnderWriting000012");
			    	}
			        return errCodes;
			    }
		
			    if (id_length!=15 && id_length!=18)
			    {
			    	if("0".equals(idflag)){
			    		errCodes.add("UnderWriting000004");
			    	}
			    	if("1".equals(idflag)){
			    		errCodes.add("UnderWriting000013");
			    	}
		    		return errCodes;
			    }
		
			    if (id_length==15)
			    {
			    	 for(int i =0 ;i<id_length;i++)
			    	 {
			    		 if(!Character.isDigit((idCard.charAt(i)))) {
			    			 if("0".equals(idflag)){
						    		errCodes.add("UnderWriting000005");
						    	}
						    	if("1".equals(idflag)){
						    		errCodes.add("UnderWriting000014");
						    	}
				    		 return errCodes;
			    		 }
			    	 	 
			    	 }
			    	
			        yyyy="19"+id.substring(6,8);
			        mm=id.substring(8,10);
			        dd=id.substring(10,12);
		
			        if (Integer.parseInt(mm)>12 || Integer.parseInt(mm)<=0){
			        	if("0".equals(idflag)){
				    		errCodes.add("UnderWriting000006");
				    	}
				    	if("1".equals(idflag)){
				    		errCodes.add("UnderWriting000015");
				    	}
			    		return errCodes;
			        }
		
			        if (Integer.parseInt(dd)>31 || Integer.parseInt(dd)<=0){
			        	if("0".equals(idflag)){
				    		errCodes.add("UnderWriting000007");
				    	}
				    	if("1".equals(idflag)){
				    		errCodes.add("UnderWriting000016");
				    	}
			    		return errCodes;
			        }
		
		
				    if((Integer.parseInt(mm)==4||Integer.parseInt(mm)==6||Integer.parseInt(mm)==9||Integer.parseInt(mm)==11)&&(Integer.parseInt(dd)>30))//4,6,9,11月份日期不能超过30
					{
				    	if("0".equals(idflag)){
				    		errCodes.add("UnderWriting000007");
				    	}
				    	if("1".equals(idflag)){
				    		errCodes.add("UnderWriting000016");
				    	}
			    		return errCodes;
					}
		
					if(Integer.parseInt(mm)==2)//判断2月份
					{
						if(LeapYear(yyyy))
						{
						   if(Integer.parseInt(dd)>29)
						   {
							   if("0".equals(idflag)){
						    		errCodes.add("UnderWriting000007");
						    	}
						    	if("1".equals(idflag)){
						    		errCodes.add("UnderWriting000016");
						    	}
					    		return errCodes;
						   }
						}
						else
						{
						   if(Integer.parseInt(dd)>28)
						   {
							   if("0".equals(idflag)){
						    		errCodes.add("UnderWriting000007");
						    	}
						    	if("1".equals(idflag)){
						    		errCodes.add("UnderWriting000016");
						    	}
					    		return errCodes;
						   }
						}
					}
		
			    }
			    else if (id_length==18)
			    {
		
			    	for(int i =0 ;i<id_length-1;i++)
			    	{
			    	 	if(!Character.isDigit((idCard.charAt(i))))
			    	 	{
			    	 		if("0".equals(idflag)){
					    		errCodes.add("UnderWriting000008");
					    	}
					    	if("1".equals(idflag)){
					    		errCodes.add("UnderWriting000017");
					    	}
				    		return errCodes;
			    	 	}
			    	}
		
			    	if(!Character.isDigit((idCard.charAt(17)))&& !String.valueOf(idCard.charAt(17)).equals("X") && !String.valueOf(idCard.charAt(17)).equals("x") )
			    	{
			    		if("0".equals(idflag)){
				    		errCodes.add("UnderWriting000009");
				    	}
				    	if("1".equals(idflag)){
				    		errCodes.add("UnderWriting000018");
				    	}
			    		return errCodes;
			    	}
			        if ((idCard.indexOf("X") > 0 && idCard.indexOf("X")!=17) || (idCard.indexOf("x")>0 && idCard.indexOf("x")!=17)){
			        	if("0".equals(idflag)){
				    		errCodes.add("UnderWriting000010");
				    	}
				    	if("1".equals(idflag)){
				    		errCodes.add("UnderWriting000019");
				    	}
			    		return errCodes;
			        }
		
			        yyyy=id.substring(6,10);
			        if (Integer.parseInt(yyyy)>year || Integer.parseInt(yyyy)<1900)
			        {
			        	if("0".equals(idflag)){
				    		errCodes.add("UnderWriting000011");
				    	}
				    	if("1".equals(idflag)){
				    		errCodes.add("UnderWriting000020");
				    	}
			    		return errCodes;
			        }
		
			        mm=id.substring(10,12);
			        if (Integer.parseInt(mm)>12 || Integer.parseInt(mm)<=0)
			        {
			        	if("0".equals(idflag)){
				    		errCodes.add("UnderWriting000006");
				    	}
				    	if("1".equals(idflag)){
				    		errCodes.add("UnderWriting000015");
				    	}
			    		return errCodes;
			        }
			        if(Integer.parseInt(yyyy)==year&&Integer.parseInt(mm)>month)
			        {
			        	if("0".equals(idflag)){
				    		errCodes.add("UnderWriting000006");
				    	}
				    	if("1".equals(idflag)){
				    		errCodes.add("UnderWriting000015");
				    	}
			    		return errCodes;
			        }
		
			        dd=id.substring(12,14);
			        if (Integer.parseInt(dd)>31 || Integer.parseInt(dd)<=0)
			        {
			        	if("0".equals(idflag)){
				    		errCodes.add("UnderWriting000007");
				    	}
				    	if("1".equals(idflag)){
				    		errCodes.add("UnderWriting000016");
				    	}
			    		return errCodes;
			        }
		
					if((Integer.parseInt(mm)==4||Integer.parseInt(mm)==6||Integer.parseInt(mm)==9||Integer.parseInt(mm)==11)&&(Integer.parseInt(dd)>30))//4,6,9,11月份日期不能超过30
					{
						if("0".equals(idflag)){
				    		errCodes.add("UnderWriting000007");
				    	}
				    	if("1".equals(idflag)){
				    		errCodes.add("UnderWriting000016");
				    	}
			    		return errCodes;
					}
		
					if(Integer.parseInt(mm)==2)//判断2月份
					{
						if(LeapYear(yyyy))
						{
							 if(Integer.parseInt(dd)>29)
							 {
								 if("0".equals(idflag)){
							    		errCodes.add("UnderWriting000007");
							    	}
							    	if("1".equals(idflag)){
							    		errCodes.add("UnderWriting000016");
							    	}
						    		return errCodes;
							 }
						}
						else
						{
							 if(Integer.parseInt(dd)>28)
							 {
						        	if("0".equals(idflag)){
							    		errCodes.add("UnderWriting000007");
							    	}
							    	if("1".equals(idflag)){
							    		errCodes.add("UnderWriting000016");
							    	}
						    		return errCodes;
							 }
						}
						}
			        if(Integer.parseInt(yyyy)==year&&Integer.parseInt(mm)==month&&Integer.parseInt(dd)>day)
			        {
			        	if("0".equals(idflag)){
				    		errCodes.add("UnderWriting000007");
				    	}
				    	if("1".equals(idflag)){
				    		errCodes.add("UnderWriting000016");
				    	}
			    		return errCodes;
			        }
		
		
			        if ((String.valueOf(id.charAt(17))).equals("x") || String.valueOf(id.charAt(17)).equals("X"))
			        {
			            if (!"x".equals(GetVerifyBit(id)) && !"X".equals(GetVerifyBit(id)))
			            {
				        	if("0".equals(idflag)){
					    		errCodes.add("UnderWriting000009");
					    	}
					    	if("1".equals(idflag)){
					    		errCodes.add("UnderWriting000018");
					    	}
				    		return errCodes;
			            }
		
			        }
			        else{
			            if (!String.valueOf(id.charAt(17)).equals(GetVerifyBit(id)))
			            {
			            	if("0".equals(idflag)){
					    		errCodes.add("UnderWriting000009");
					    	}
					    	if("1".equals(idflag)){
					    		errCodes.add("UnderWriting000018");
					    	}
				    		return errCodes;
			            }
			        }
			    }
			}
			
			return errCodes;
			
	}
    
	/**
	 * 判断传入的年份是否为闰年
	 * @param year
	 * @return 是否为闰年
	 */
	public static boolean LeapYear (String year) {
		
		boolean flag=false;
		int years=Integer.parseInt(year);
		if (years % 400 == 0  )   
        {   
			flag=true;
        }
		else if (years % 4 == 0 && years % 100 != 0){
			
			flag=true;
		}
            
		return flag; 
		
	}
	
	/**
	 * 当身份证号为18位时：根据身份证好的前17位判断第18位的value
	 * @param 身份证号码
	 * @return 第18位的value
	 */
	 private static String GetVerifyBit(String id){
		   
		 	String result = "";
		    int nNum=0;
		   
		    String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };     
		    String Ai=id.substring(0, 17);
		    int TotalmulAiWi = 0;     
			for (int i = 0; i < 17; i++) {     
				TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i)))* Integer.parseInt(Wi[i]);     
			} 
		    
		    nNum=TotalmulAiWi%11;
		    switch (nNum) {
		       case 0 :
		          result="1";
		          break;
		       case 1 :
		          result="0";
		          break;
		       case 2 :
		          result="X";
		          break;
		       case 3 :
		          result="9";
		          break;
		       case 4 :
		          result="8";
		          break;
		       case 5 :
		          result="7";
		          break;
		       case 6 :
		          result="6";
		          break;
		       case 7 :
		          result="5";
		          break;
		       case 8 :
		          result="4";
		          break;
		       case 9 :
		          result="3";
		          break;
		       case 10 :
		          result="2";
		          break;
		    }
		  
		    return result;
		}
	 
	 /**
		 * 投保人年龄是否大于等于18岁校验
		 * @param age 投保人年龄
		 * @return
		 * @throws Exception 
		 */
		public static List<String> ApplicantAge(String ApplicantBrithday,List<String> errCodes) {
			
			Date brityD= DateUtil.parse(ApplicantBrithday);
			int age = 0;
			try {
				age = PubFun.getAge(brityD);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			
			if(ApplicantBrithday!=null && !ApplicantBrithday.equals("")) {
			//投保人年龄
				if (age<18){
					errCodes.add("UnderWriting000021");
					return errCodes;
				}
			}
			return errCodes;
		}
	
		/**
		 * 投被保人关系
		 * @param age 关系编码
		 * @param productid 产品编码
		 */
		public static List<String> Relation(String relation,String productid,List<String> errCodes){
			GetDBdata db = new GetDBdata();
			String result="";
			String resultall="";
			if(relation!=null && !relation.equals("")) {
				try{
				result=db.getOneValue("SELECT codeValue FROM dictionary WHERE codetype = 'Relationship' AND productid ='"+productid+"' and codeValue='"+relation+"'  limit 1  ") ;
				resultall=db.getOneValue("SELECT codeValue FROM dictionary WHERE codetype = 'Relationship' AND productid ='"+productid+"'  limit 1  ") ;
				//先根据产品查 如果产品不为空 但对应的关系为空 则返回错误
				if((result==null || result.equals("")) && (resultall!=null && !resultall.equals(""))){
					errCodes.add("UnderWriting000022");
					return errCodes;
					
				}
				//再根据保险公司查 如果保险公司不为空 但对应的关系为空 则返回错误
				else if (resultall==null || resultall.equals("") ){
					result=db.getOneValue("SELECT codeValue FROM dictionary WHERE codetype = 'Relationship' AND insurancecode =substr('"+productid+"',1,4) and codeValue='"+relation+"'  limit 1  ");
					resultall=db.getOneValue("SELECT codeValue FROM dictionary WHERE codetype = 'Relationship' AND insurancecode =substr('"+productid+"',1,4)  limit 1  ");
					if((result==null || result.equals("")) && (resultall!=null && !resultall.equals(""))){
						errCodes.add("UnderWriting000022");
						return errCodes;
					}
				}
				}catch(Exception e){
					logger.error("wap站内部核保模块异常"+e.getMessage(), e);
				}
			}
			return errCodes;
		}	
		/**
		 * 被保人性别
		 * @param age 关系编码
		 * @param productid 产品编码
		 */
		public static List<String> checkSex(String[] baseInfo,List<String> errCodes,String isSelf,String appGender,String insuredGender){
			String insurTypeChild = baseInfo[7];
			if ("22".equals(insurTypeChild) || "23".equals(insurTypeChild)
					|| "24".equals(insurTypeChild)
					|| "25".equals(insurTypeChild)
					|| "26".equals(insurTypeChild)) {
				if ("Y".equals(isSelf)) {
					if ("男".equals(appGender)) {
						errCodes.add("UnderWriting000027");
						return errCodes;
					}
				} else {
					if ("男".equals(insuredGender)) {
						errCodes.add("UnderWriting000027");
						return errCodes;
					}
				}
			}
			return errCodes;
		}
		
		/*
		public static int dealLimitCount(SDInformation sdinf,OrderConfigNewService orderConfigNewService){
			int limitCount = 1;
			String sql = "select LimitCount,Occup,SectionAge,IsPublish from sdproduct where productid=?";
			String[] sqltemp = { sdinf.getProductId() };
			GetDBdata db = new GetDBdata();
			List<HashMap<String, String>> sdproduct;
			try {
				sdproduct = db.query(sql, sqltemp);
				 
				HashMap<String, String> product = sdproduct.get(0);
				if(product.get("LimitCount")==null || "".equals(product.get("LimitCount"))){
					limitCount=10;
				}else{
					limitCount = Integer.parseInt(product.get("LimitCount"));
				    if(limitCount>=11){
				    	limitCount=10;
				    }
				}
				if(!orderConfigNewService.buyCountFlag(sdinf)){
					limitCount = 1;
				}
			}catch(Exception e){
				LogUtil.error("wap站内部核保模块异常"+e.getMessage());
				logger.error(e.getMessage(), e);
			}
			return limitCount;
		}*/	
		
		/**
		 * 得到产品限购份数--判断页面是否需要显示“购买份数”录入项
		 * @param cProductID 产品ID
		 */
		@SuppressWarnings("unused")
		private static boolean isProductDownShelf(SDInformation sdinformation,List<SDInformationInsured> insuredList) {
			String productId = "";
			String productName = "";
			if (sdinformation != null) {
				productId = sdinformation.getProductId();
				productName = sdinformation.getProductName();
			}
			if (null == productId) {
				return false;
			}
			String sql = "select LimitCount,Occup,SectionAge,IsPublish from sdproduct where productid=?";
			String[] sqltemp = { productId };
			GetDBdata db = new GetDBdata();
			List<HashMap<String, String>> sdproduct;
			try {
				sdproduct = db.query(sql, sqltemp);
				if (sdproduct == null || sdproduct.size() != 1) {
					return false;
				}
				HashMap<String, String> product = sdproduct.get(0);
				String IsPublish = product.get("IsPublish");
				if (StringUtil.isEmpty(IsPublish) || "N".equals(IsPublish)) {
					return false;
				}
				/*String qixianSql = "select startDate, endDate from sdinformation where id =? ";
				String[] qixianSqltemp = { sdinformation.getId() };
				List<Map<String, Object>> qixain = db.queryObj(qixianSql,
						qixianSqltemp);
				if (qixain == null || qixain.size() != 1) {
					return false;
				}*/
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sfTime = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				/*Map<String, Object> qx = qixain.get(0);*/
				String qxfail = DateUtil.toDateTimeString(sdinformation.getEndDate());
				String qxeffective = DateUtil.toDateTimeString(sdinformation.getStartDate());
				/*if (qx.get("endDate") != null) {
					qxfail = qx.get("endDate").toString();
					qxeffective = qx.get("startDate").toString();
				}*/
				String tDate = "";
				if (productId.equals("204201002")) {
					tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
							.calSDate(sf.format(new Date()), 0, "D");
				} else {
					tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
							.calSDate(sf.format(new Date()), 1, "D");
				}
				Date nowDate = com.sinosoft.sms.messageinterface.pubfun.PubFun
						.StringToDate(tDate);
				if (nowDate.getTime() > sfTime.parse(qxfail).getTime()) {
					return false;
				}
				String LimitCount = product.get("LimitCount");
				logger.info("保险公司限购份数：{}", LimitCount);
				if (StringUtil.isNotEmpty(LimitCount) && !"0".equals(LimitCount)) {
					for (SDInformationInsured sdinsured : insuredList) {
						String insuredName = sdinsured.getRecognizeeName();
						String insuredIdType = sdinsured
								.getRecognizeeIdentityType();
						String insuredIdNo = sdinsured.getRecognizeeIdentityId();
						String backup_ = "select count(b.id) from sdinformation a, sdinformationinsured b,SDInformationRiskType c  "
								+ "where a.informationSn = b.informationSn and c.recognizeeSn = b.recognizeeSn "
								+ "and c.appStatus='1' and (('"
								+ qxfail
								+ "'>=a.startDate and a.startDate>='"
								+ qxeffective
								+ "') or ('"
								+ qxeffective
								+ "'<=a.endDate and a.endDate<='"
								+ qxfail
								+ "')) "
								+ "and b.recognizeeIdentityType=? and b.recognizeeIdentityId=? and a.productId=?";
						String[] backupTemp = { insuredIdType, insuredIdNo,
								productId };
						logger.info("保险公司限购份数（sql）：{}", backup_);
						String backup = db.getOneValue(backup_, backupTemp);
						logger.info("保险公司限购份数的值：{} -- {}", backup, LimitCount);
						if (StringUtil.isNotEmpty(backup)) {
							if (Integer.parseInt(backup) >= Integer
									.parseInt(LimitCount)) {
								return false;
							}
						}
					}
				}
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				return false;
			}
		}
		
		/**
		 * 起飞时间校验 起飞时间应大于等于保险起期 小于等于保险止期
		 * @param flightTime 起飞时间
		 * @param insuranceBeginTime 保险起期
		 * @param insuranceEndTime 保险止期
		 * @return 是否正确
		 */
		public static List<String> DepartureTime(String flightTime,Date insuranceBeginTime,Date insuranceEndTime,List<String> errCodes){

			if (flightTime!=null && !flightTime.equals("")){
				
				if(flightTime.length()!=19){
					errCodes.add("UnderWriting000024");
					return errCodes;
				}else if(!"-".equals(flightTime.substring(4, 5)) || !"-".equals(flightTime.substring(7, 8)) ){
					errCodes.add("UnderWriting000024");
					return errCodes;
				}else{
					
					String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
					Pattern p = Pattern.compile(eL);
					  Matcher m = p.matcher(flightTime);
					  boolean b = m.matches();
					  if (!b) {
						  errCodes.add("UnderWriting000024");
						  return errCodes;
					  }
					  
					  Date dflightTime = DateUtil.parse(flightTime,"yyyy-MM-dd HH:mm:ss");
					  if(dflightTime.getTime() < insuranceBeginTime.getTime() || dflightTime.getTime() > insuranceEndTime.getTime()){
						  errCodes.add("UnderWriting000025");
						  return errCodes;
					  }
				}
			}
			
			return errCodes;
			
		}
		
    public static void main(String[] args) {
    	
	}

}
