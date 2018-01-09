package cn.com.sinosoft.common;

import cn.com.sinosoft.entity.SDInformationDuty;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDInformationProperty;

import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.FMCheckFieldSchema;
import com.sinosoft.schema.FMCheckFieldSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

public class ShopValidate {
	private static final Logger logger = LoggerFactory.getLogger(ShopValidate.class);
	public ShopValidate(){
		 
	}
	public ShopValidate(ShopCheckField tShopCheckField, FMCheckFieldSchema tFMCheckFieldSchema,String aFlag){ 
		
		mShopCheckField = tShopCheckField;
		mFMCheckFieldSchema = tFMCheckFieldSchema;
	}
	/*
	 * 购买流程校验类
	 */
 
	public CErrors mErrors = new CErrors(); //错误处理类
	private ShopCheckField mShopCheckField = new ShopCheckField();//计算要素
	private FMCheckFieldSet mFMCheckFieldSet = new FMCheckFieldSet();//校验集合
	private FMCheckFieldSchema mFMCheckFieldSchema = new FMCheckFieldSchema();//校验集合
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	
	public String BaseCalculate()
    {
        String tReturn = ""; //校验是否通过的标志
        
        //计算基礎指标數據
        ShopCalculator tShopCalculator = new ShopCalculator();
        
         //设置计算编码 
        tShopCalculator.setCalCode(this.mFMCheckFieldSchema.getCalCode());
        logger.info("校验编码:{}", this.mFMCheckFieldSchema.getCalCode());
           
        //增加基本要素（除被保人）
        addValBase(tShopCalculator); 
        
        //被保人信息校验
        if("01".equals(String.valueOf(mFMCheckFieldSchema.getValType()))){
        	for(SDInformationInsured tSDInformationInsured:this.mShopCheckField.getInsList()){
        		//增加被保人校验基本要素
        		if(tSDInformationInsured!=null){
        			 addValBaseIns(tShopCalculator,tSDInformationInsured,null);
                     tReturn = tShopCalculator.calculate();
                     
                     if("N".equals(tReturn)){return tReturn;}
        		}
               
        	}
        	if(this.mShopCheckField.getSdproList()!=null){
        		for(SDInformationProperty sdPro : this.mShopCheckField.getSdproList()){
        			if(sdPro!=null){
        				addValBaseIns(tShopCalculator,null,sdPro);
        				tReturn = tShopCalculator.calculate();
        				if("N".equals(tReturn)){return tReturn;}
        			}
        		}
        	}
        }else{
        	tReturn = tShopCalculator.calculate();
        }
        //计算
        
        //处理结果
        if (tShopCalculator.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tShopCalculator.mErrors);
            CError tError = new CError();
            tError.moduleName = "CalIndexN";
            tError.functionName = "Calculator";
            tError.errorMessage = "计算指标失败!";
            this.mErrors.addOneError(tError);
            return "0";
        }
        if (tReturn == null || tReturn.trim().equals(""))
        {
            tReturn = "0";
        }
        logger.info("tReturn================={}", tReturn);
        return tReturn;
    }
	/**
     * 向计算类中填入被保人基本计算要素
     *
     * @param cCal SalesCalculator
     */
	 private void addValBaseIns(ShopCalculator cCal,SDInformationInsured cSDInformationInsured,SDInformationProperty cSDInformationProperty){
		    if(cSDInformationInsured != null){
		    	cCal.addBasicFactor("InsBirthday", cSDInformationInsured.getRecognizeeBirthday());//投保人生日
		    	//根据被保人生日计算投保人年龄
		    	int tInsAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(cSDInformationInsured.getRecognizeeBirthday());
		    	String tInsAgeNum = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAgeNum(cSDInformationInsured.getRecognizeeBirthday());
		    	
		    	cCal.addBasicFactor("InsAgeNum", tInsAgeNum);
		    	cCal.addBasicFactor("InsAge", String.valueOf(tInsAge));
		    	cCal.addBasicFactor("InsSex", cSDInformationInsured.getRecognizeeSexName());
		    	cCal.addBasicFactor("InsIDType", cSDInformationInsured.getRecognizeeIdentityType());
		    	cCal.addBasicFactor("InsIDNo", cSDInformationInsured.getRecognizeeIdentityId());
		    	cCal.addBasicFactor("InsOccupation1", cSDInformationInsured.getRecognizeeOccupation1());//被保人职业
		    	cCal.addBasicFactor("InsOccupation2", cSDInformationInsured.getRecognizeeOccupation2());//被保人职业
		    	cCal.addBasicFactor("InsOccupation3", cSDInformationInsured.getRecognizeeOccupation3());//被保人职业
		    	cCal.addBasicFactor("InsAddress1", cSDInformationInsured.getRecognizeeArea1());//被保人所在省
		    	cCal.addBasicFactor("InsAddress2", cSDInformationInsured.getRecognizeeArea2());//被保人所在市
		    	cCal.addBasicFactor("InsAddress3", cSDInformationInsured.getRecognizeeArea3());//被保人所在区
		    	cCal.addBasicFactor("InsAddress", cSDInformationInsured.getRecognizeeAddress());//被保人地址
		    	cCal.addBasicFactor("Relation", cSDInformationInsured.getRecognizeeAppntRelationName());//被保人地址
		    	cCal.addBasicFactor("recognizeeIdentityId", cSDInformationInsured.getRecognizeeIdentityId());//被保人证件号
		    	cCal.addBasicFactor("RecognizeeName", cSDInformationInsured.getRecognizeeName());//被保人姓名
		    	cCal.addBasicFactor("recognizeeBirthday", cSDInformationInsured.getRecognizeeBirthday());//被保人姓名
		    	
		    	cCal.addBasicFactor("InsMail", cSDInformationInsured.getRecognizeeMail());//被保人邮箱
		    	cCal.addBasicFactor("InsEnName", cSDInformationInsured.getRecognizeeEnName());//被保人英文名
		    	cCal.addBasicFactor("InsMobile", cSDInformationInsured.getRecognizeeMobile());//被保人手机号码
		    	cCal.addBasicFactor("InsStartID", cSDInformationInsured.getRecognizeeStartID());//被保人证件有效起始日期
		    	cCal.addBasicFactor("InsEndID", cSDInformationInsured.getRecognizeeEndID());//被保人证件有效终止日期
		    	if(cSDInformationInsured.getRecognizeeMul()!=null && !"".equals(cSDInformationInsured.getRecognizeeMul())){
		    		cCal.addBasicFactor("BuyCount", String.valueOf(Integer.parseInt(cSDInformationInsured.getRecognizeeMul())-1));//购买份数
		    	}else{
		    		cCal.addBasicFactor("BuyCount", "0");//购买份数
		    	} 
		    	cCal.addBasicFactor("InsHeight", cSDInformationInsured.getHeight());// 身高
		    	cCal.addBasicFactor("InsWeight", cSDInformationInsured.getWeight());// 体重
		    }
		    if(cSDInformationProperty != null){
		    	cCal.addBasicFactor("HourseAge", cSDInformationProperty.getHourseAge());//房龄
		    	cCal.addBasicFactor("HourseProvince", cSDInformationProperty.getPropertyArea1());//房屋所在省
		    	cCal.addBasicFactor("HourseCity", cSDInformationProperty.getPropertyArea2());//房屋所在市 
		    	cCal.addBasicFactor("HourseAddress", cSDInformationProperty.getPropertyAdress());//房屋所在地址
		    }
		    cCal.addBasicFactor("flightNo", cSDInformationInsured.getFlightNo());//航班号
		    cCal.addBasicFactor("flightTime", cSDInformationInsured.getFlightTime());//起飞时间
	        
	 }
	/**
     * 向计算类中填入基本计算要素
     *
     * @param cCal SalesCalculator
     */
    private void addValBase(ShopCalculator cCal)
    {
    	 
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	cCal.addBasicFactor("ProductId", mShopCheckField.getSdinf().getProductId());//产品编码
        cCal.addBasicFactor("InsuredCompanySn", mShopCheckField.getSdinf().getInsuranceCompany());//保险公司编码
    	cCal.addBasicFactor("EffectiveDate", sdf.format(mShopCheckField.getSdinf().getStartDate()));//保单生效日期
        cCal.addBasicFactor("FailDate", sdf.format(mShopCheckField.getSdinf().getEndDate()));//保单失效日期
        cCal.addBasicFactor("RiskType", mShopCheckField.getSubRiskType());//产品小类
        cCal.addBasicFactor("MRiskType", mShopCheckField.getSdinf().getRiskType());//产品中类
        cCal.addBasicFactor("ComRiskCode", mShopCheckField.getSdinf().getProductOutCode());//产品中类
        cCal.addBasicFactor("isRenewalOrder", StringUtil.isNotEmpty(mShopCheckField.getSdorder().getRenewalId())?"Y":"N");// 是否是续保订单
        
             
        
        
        //投保人信息
        cCal.addBasicFactor("applicantBirthday", mShopCheckField.getApp().getApplicantBirthday());//投保人生日
        cCal.addBasicFactor("AppBirthday", mShopCheckField.getApp().getApplicantBirthday());//投保人生日
        //根据投保人生日计算投保人年龄
        int tAppAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(mShopCheckField.getApp().getApplicantBirthday());
        cCal.addBasicFactor("AppAge", String.valueOf(tAppAge));
        cCal.addBasicFactor("AppSex", mShopCheckField.getApp().getApplicantSexName());
        cCal.addBasicFactor("AppOccupation1", mShopCheckField.getApp().getApplicantOccupation1());//投保人职业
        cCal.addBasicFactor("AppOccupation2", mShopCheckField.getApp().getApplicantOccupation2());//投保人职业
        cCal.addBasicFactor("AppOccupation3", mShopCheckField.getApp().getApplicantOccupation3());//投保人职业
        cCal.addBasicFactor("AppAddress1", mShopCheckField.getApp().getApplicantArea1());//投保人所在省
        cCal.addBasicFactor("AppAddress2", mShopCheckField.getApp().getApplicantArea2());//投保人所在市
        cCal.addBasicFactor("AppAddress3", mShopCheckField.getApp().getApplicantArea3());//投保人所在区
        cCal.addBasicFactor("AppAddress", mShopCheckField.getApp().getApplicantAddress());//投保人详细地址
        cCal.addBasicFactor("AppLicantIdentityId", mShopCheckField.getApp().getApplicantIdentityId());//投保人证件
        cCal.addBasicFactor("ApplicantName", mShopCheckField.getApp().getApplicantName());//投保人姓名
        cCal.addBasicFactor("ApplicantMail", mShopCheckField.getApp().getApplicantMail());//邮箱
        cCal.addBasicFactor("ApplicantEnName", mShopCheckField.getApp().getApplicantEnName());//英文名
    	cCal.addBasicFactor("ApplicantIdentityType", mShopCheckField.getApp().getApplicantIdentityType());//证件类型
    	cCal.addBasicFactor("ApplicantMobile", mShopCheckField.getApp().getApplicantMobile());//手机号
    	cCal.addBasicFactor("ApplicantStartID", mShopCheckField.getApp().getApplicantStartID());//证件有效起始日期
    	cCal.addBasicFactor("ApplicantEndID", mShopCheckField.getApp().getApplicantEndID());//证件有效终止日期
        
    	//责任信息
    	String dutyAmount = null;
    	List<SDInformationDuty> dutyList = mShopCheckField.getDutyList();
    	if(null != dutyList && dutyList.size()>0){
    		BigDecimal sumAmount = BigDecimal.ZERO;
    		for(SDInformationDuty duty : dutyList){
    			String dutyAmountStr =  duty.getAmt().trim();
    			if(null != dutyAmountStr && dutyAmountStr.length()>0 ){
    				sumAmount = sumAmount.add(new BigDecimal(dutyAmountStr));
    			}
    		}
    		dutyAmount = sumAmount.toString();
    	}
    	cCal.addBasicFactor("DutyAmount", dutyAmount);//保额
        /** 被保人信息
         
        cCal.addBasicFactor("InsBirthday", mShopCheckField.getIns().getRecognizeeBirthday());//投保人生日
        //根据投保人生日计算投保人年龄
        int tInsAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(mShopCheckField.getApp().getApplicantBirthday());
        cCal.addBasicFactor("InsAge", String.valueOf(tInsAge));
        cCal.addBasicFactor("InsSex", mShopCheckField.getIns().getRecognizeeSex());
        cCal.addBasicFactor("InsOccupation1", mShopCheckField.getIns().getRecognizeeOccupation1());//被保人职业
        cCal.addBasicFactor("InsOccupation2", mShopCheckField.getIns().getRecognizeeOccupation2());//被保人职业
        cCal.addBasicFactor("InsOccupation3", mShopCheckField.getIns().getRecognizeeOccupation3());//被保人职业
        cCal.addBasicFactor("InsAddress1", mShopCheckField.getIns().getRecognizeeArea1());//被保人所在省
        cCal.addBasicFactor("InsAddress2", mShopCheckField.getIns().getRecognizeeArea1());//被保人所在市
        cCal.addBasicFactor("InsAddress2", mShopCheckField.getIns().getRecognizeeAddress());//被保人地址
*/        
        
    	cCal.addBasicFactor("TEST", "KXB测试用");//保险公司编码
        
    }
	
    
    
    
    
    
    public CErrors getmErrors() {
		return mErrors;
	}


	public void setmErrors(CErrors mErrors) {
		this.mErrors = mErrors;
	}


	public ShopCheckField getmShopCheckField() {
		return mShopCheckField;
	}


	public void setmShopCheckField(ShopCheckField mShopCheckField) {
		this.mShopCheckField = mShopCheckField;
	}


	public FMCheckFieldSet getmFMCheckFieldSet() {
		return mFMCheckFieldSet;
	}


	public void setmFMCheckFieldSet(FMCheckFieldSet mFMCheckFieldSet) {
		this.mFMCheckFieldSet = mFMCheckFieldSet;
	}


	public FMCheckFieldSchema getmFMCheckFieldSchema() {
		return mFMCheckFieldSchema;
	}


	public void setmFMCheckFieldSchema(FMCheckFieldSchema mFMCheckFieldSchema) {
		this.mFMCheckFieldSchema = mFMCheckFieldSchema;
	}



	public static void main(String[] args){
    	//AD0102
    	ShopValidate tShopValidate = new ShopValidate();
    	tShopValidate.mFMCheckFieldSchema.setCalCode("A0001");
    	tShopValidate.BaseCalculate();
    	
    }
}
