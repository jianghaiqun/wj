package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.action.shop.CpsAction;
import cn.com.sinosoft.action.shop.OrderConfigNewAction;
import cn.com.sinosoft.entity.Dictionary;
import cn.com.sinosoft.entity.HealthyInfo;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDInformationRiskType;
import cn.com.sinosoft.entity.SDInsuredHealth;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.HealthyInfoService;
import cn.com.sinosoft.service.OrderConfigNewService;
import cn.com.sinosoft.service.SDInsuredHealthService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.util.CookieUtil;
import cn.com.sinosoft.util.OrderCheckIDUtil;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.CountryChineseSpelling;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OrderConfigNewServiceImpl implements OrderConfigNewService{
	private static final Logger logger = LoggerFactory.getLogger(OrderConfigNewServiceImpl.class);

	@Resource 
	private DictionaryService dictionaryService; 
	@Resource
	private AreaService areaService; 
	@Resource
	private SDOrderService sdorderService; 

	@Override
	public String totleAmount(List<OrderDutyFactor> dutyFactor) {
		List backup = new ArrayList();
		if (dutyFactor.size() > 0) {
			for (OrderDutyFactor orderDutyFactor : dutyFactor) {
				for (FEMDutyAmntPremList fEMDutyAmntPremList : orderDutyFactor
						.getFdAmntPremList()) {
					backup.add(fEMDutyAmntPremList.getBackUp1());
				}
				if (orderDutyFactor.getFdAmntPremList() == null
						|| orderDutyFactor.getFdAmntPremList().get(0) == null) {
					FEMDutyAmntPremList fEMDutyAmntPremList = new FEMDutyAmntPremList();
					List<FEMDutyAmntPremList> fdap = new ArrayList<FEMDutyAmntPremList>();
					fdap.add(fEMDutyAmntPremList);
					orderDutyFactor.setFdAmntPremList(fdap);
				}
			}
		}
		Integer backNum = 0;
		for (int i = 0; i < backup.size(); i++) {
			if (backup.get(i) != null) {
				backNum = backNum + Integer.parseInt(backup.get(i).toString());
			}
		}
		String amntNum = backNum.toString();
		return amntNum;
	}
	@Override
	public SDOrder updateOrder(SDOrder oldOrder, SDOrder order) {
		 
		oldOrder.setProductTotalPrice(order.getProductTotalPrice());
		oldOrder.setTotalAmount(order.getTotalAmount());
		oldOrder.setPayAmount(order.getPayAmount()); 
		oldOrder.setPayPrice(order.getPayPrice());
		oldOrder.setOrderStatus(order.getOrderStatus());
		oldOrder.setOffsetPoint(order.getOffsetPoint());
		oldOrder.setModifyDate(new Date());
		return oldOrder;
	}
	@Override
	public SDInformationAppnt updateInformationAppnt(SDInformation sdinf,SDInformationAppnt ifa,
			SDInformationAppnt informationAppnt) {
		ifa.setApplicantName(informationAppnt.getApplicantName());
		ifa.setApplicantIdentityType(informationAppnt.getApplicantIdentityType());
		String applicantIdentityTypeName = this.dictionaryService.getNameByCodeType(sdinf.getInsuranceCompany(), "certificate", informationAppnt.getApplicantIdentityType());
		String applicantSexName = this.dictionaryService.getNameByCodeType(sdinf.getInsuranceCompany(), "Sex", informationAppnt.getApplicantSex());
		
		ifa.setApplicantIdentityTypeName(applicantIdentityTypeName);
		ifa.setApplicantIdentityId(informationAppnt.getApplicantIdentityId());
		ifa.setApplicantStartID(informationAppnt.getApplicantStartID());
		ifa.setApplicantEndID(informationAppnt.getApplicantEndID());
		// 证件有效期勾选长期时
		if (StringUtil.isNotEmpty(informationAppnt.getApplicantStartID()) && StringUtil.isEmpty(informationAppnt.getApplicantEndID())) {
			ifa.setApplicantEndID("9999-12-31");
		}
		ifa.setApplicantMobile(informationAppnt.getApplicantMobile());
		ifa.setApplicantSex(informationAppnt.getApplicantSex());
		ifa.setApplicantSexName(applicantSexName);
		ifa.setApplicantBirthday(informationAppnt.getApplicantBirthday());
		ifa.setApplicantMail(informationAppnt.getApplicantMail());
		ifa.setApplicantArea1(informationAppnt.getApplicantArea1());
		ifa.setApplicantArea2(informationAppnt.getApplicantArea2());
		ifa.setApplicantArea3(informationAppnt.getApplicantArea3());
		ifa.setSocialSecurity(informationAppnt.getSocialSecurity());
		if(StringUtil.isNotEmpty(informationAppnt.getApplicantZipCode())){
			ifa.setApplicantZipCode(informationAppnt.getApplicantZipCode());
		}
		OrderConfigNewAction t = new OrderConfigNewAction();
		if(StringUtil.isNotEmpty(informationAppnt.getApplicantArea1())){
			ifa.setApplicantZipCode(t.getZipInfo(this.areaService.getAreaName(informationAppnt.getApplicantArea1()), this.areaService.getAreaName(informationAppnt.getApplicantArea2())));
		}
		
	 	ifa.setApplicantMobile(informationAppnt.getApplicantMobile());
		ifa.setApplicantTel(informationAppnt.getApplicantTel()); 
		ifa.setApplicantAddress(informationAppnt.getApplicantAddress());
		ifa.setApplicantLastName(informationAppnt.getApplicantLastName());
		ifa.setApplicantFirstName(informationAppnt.getApplicantFirstName());
		ifa.setApplicantEnName(informationAppnt.getApplicantEnName());
		ifa.setApplicantOccupation1(informationAppnt.getApplicantOccupation1());
		ifa.setApplicantOccupation2(informationAppnt.getApplicantOccupation2());
		ifa.setApplicantOccupation3(informationAppnt.getApplicantOccupation3());
		ifa.setInvoiceHeading(informationAppnt.getInvoiceHeading());
		ifa.setBankCode(informationAppnt.getBankCode());
		ifa.setBankAccNo(informationAppnt.getBankAccNo());
		ifa.setAccName(informationAppnt.getAccName());
		if(StringUtil.isNotEmpty(informationAppnt.getApplicantIncome())){
			ifa.setApplicantIncome(informationAppnt.getApplicantIncome());
		}
		
		return ifa;
	}
	@Override
	public SDInformationInsured updateInformationInsured(SDInformation sdinf,
			SDInformationInsured infs1, SDInformationInsured informationInsureds) {
		
		 
		String recognizeeIdentityTypeName = this.dictionaryService.getNameByCodeType(sdinf.getInsuranceCompany(),"certificate",informationInsureds.getRecognizeeIdentityId());
		String recognizeeAppntRelationName = this.dictionaryService.getNameByCodeType(sdinf.getInsuranceCompany(),"Relationship",informationInsureds.getRecognizeeAppntRelation());
		String recognizeeSexName = this.dictionaryService.getNameByCodeType(sdinf.getInsuranceCompany(),"Sex",informationInsureds.getRecognizeeSex());
		if(informationInsureds.getRecognizeeName()!=null && !"".equals(informationInsureds.getRecognizeeName())){
			infs1.setRecognizeeName(informationInsureds.getRecognizeeName());
		}
		if(informationInsureds.getRecognizeeName()!=null && !"".equals(informationInsureds.getRecognizeeName())){
			infs1.setRecognizeeEnName(CountryChineseSpelling.convert(informationInsureds.getRecognizeeName()));
		}
		if(informationInsureds.getRecognizeeIdentityType()!=null && !"".equals(informationInsureds.getRecognizeeIdentityType())){
			infs1.setRecognizeeIdentityType(informationInsureds.getRecognizeeIdentityType());
		} 
		if(informationInsureds.getRecognizeeIdentityTypeName()!=null &&!"".equals(informationInsureds.getRecognizeeIdentityTypeName())){
			infs1.setRecognizeeIdentityTypeName(informationInsureds.getRecognizeeIdentityTypeName());
		}
	    if(informationInsureds.getRecognizeeIdentityId()!=null &&!"".equals(informationInsureds.getRecognizeeIdentityId())){
	    	infs1.setRecognizeeIdentityId(informationInsureds.getRecognizeeIdentityId());
	    }
		if(informationInsureds.getRecognizeeSex()!=null &&!"".equals(informationInsureds.getRecognizeeSex())){
			infs1.setRecognizeeSex(informationInsureds.getRecognizeeSex());
		}
		if(recognizeeSexName!=null && !"".equals(recognizeeSexName)){
			infs1.setRecognizeeSexName(recognizeeSexName);
		}
		if(informationInsureds.getRecognizeeBirthday()!=null && !"".equals(informationInsureds.getRecognizeeBirthday())){
			infs1.setRecognizeeBirthday(informationInsureds.getRecognizeeBirthday());
		}
		if(informationInsureds.getRecognizeeMobile()!=null && !"".equals(informationInsureds.getRecognizeeMobile())){
			infs1.setRecognizeeMobile(informationInsureds.getRecognizeeMobile());
		}
		if(informationInsureds.getRecognizeeTel()!=null &&!"".equals(informationInsureds.getRecognizeeTel())){
			infs1.setRecognizeeTel(informationInsureds.getRecognizeeTel());
		}
		if(informationInsureds.getRecognizeeMail()!=null &&!"".equals(informationInsureds.getRecognizeeMail())){
			infs1.setRecognizeeMail(informationInsureds.getRecognizeeMail());
		}
		if(informationInsureds.getRecognizeeZipCode()!=null &&!"".equals(informationInsureds.getRecognizeeZipCode())){
			infs1.setRecognizeeZipCode(informationInsureds.getRecognizeeZipCode());
		}
		if(informationInsureds.getRecognizeeOccupation1()!=null &&!"".equals(informationInsureds.getRecognizeeOccupation1())){
			infs1.setRecognizeeOccupation1(informationInsureds.getRecognizeeOccupation1());
		}
		if(informationInsureds.getRecognizeeOccupation2()!=null &&!"".equals(informationInsureds.getRecognizeeOccupation2())){
			infs1.setRecognizeeOccupation2(informationInsureds.getRecognizeeOccupation2());
		}
		if(informationInsureds.getRecognizeeOccupation3()!=null &&!"".equals(informationInsureds.getRecognizeeOccupation3())){
			infs1.setRecognizeeOccupation3(informationInsureds.getRecognizeeOccupation3());
		}
		if(informationInsureds.getRecognizeeArea1()!=null &&!"".equals(informationInsureds.getRecognizeeArea1())){
			infs1.setRecognizeeArea1(informationInsureds.getRecognizeeArea1());
		}
		if(informationInsureds.getRecognizeeArea2()!=null &&!"".equals(informationInsureds.getRecognizeeArea2())){
			infs1.setRecognizeeArea2(informationInsureds.getRecognizeeArea2());
		}
		if(informationInsureds.getRecognizeeArea3()!=null &&!"".equals(informationInsureds.getRecognizeeArea3())){
			infs1.setRecognizeeArea3(informationInsureds.getRecognizeeArea3());
		}
		if(informationInsureds.getRecognizeeStartID()!=null &&!"".equals(informationInsureds.getRecognizeeStartID())){
			infs1.setRecognizeeStartID(informationInsureds.getRecognizeeStartID());
		}
		if(informationInsureds.getRecognizeeEndID()!=null &&!"".equals(informationInsureds.getRecognizeeEndID())){
			infs1.setRecognizeeEndID(informationInsureds.getRecognizeeEndID());
		}
		if(informationInsureds.getSchoolOrCompany()!=null && !"".equals(informationInsureds.getSchoolOrCompany())){
			infs1.setSchoolOrCompany(informationInsureds.getSchoolOrCompany());
		}
	    if(informationInsureds.getOutGoingParpose()!=null &&!"".equals(informationInsureds.getOutGoingParpose())){
	    	infs1.setOutGoingParpose(informationInsureds.getOutGoingParpose());
	    }
		if(informationInsureds.getOutGoingParpose()!=null &&!"".equals(informationInsureds.getOutGoingParpose())){
			infs1.setOutGoingParpose(informationInsureds.getOutGoingParpose());
		}
		if(informationInsureds.getRecognizeeName()!=null &&!"".equals(informationInsureds.getRecognizeeName())){
			infs1.setRecognizeeFirstName(CountryChineseSpelling.convertFirstName(informationInsureds.getRecognizeeName()));
			infs1.setRecognizeeLashName(CountryChineseSpelling.convertLastName(informationInsureds.getRecognizeeName()));
		}
		if(informationInsureds.getRecognizeeAddress()!=null &&!"".equals(informationInsureds.getRecognizeeAddress())){
			infs1.setRecognizeeAddress(informationInsureds.getRecognizeeAddress());
		}
		if(informationInsureds.getFlightNo()!=null &&!"".equals(informationInsureds.getFlightNo())){
			infs1.setFlightNo(informationInsureds.getFlightNo());
		}
		if(informationInsureds.getFlightTime()!=null &&!"".equals(informationInsureds.getFlightTime())){
			infs1.setFlightTime(informationInsureds.getFlightTime());
		}
		if(informationInsureds.getDriverSchoolName()!=null &&!"".equals(informationInsureds.getDriverSchoolName())){
			infs1.setDriverSchoolName(informationInsureds.getDriverSchoolName());
		}
		if(informationInsureds.getDriverNo()!=null &&!"".equals(informationInsureds.getDriverNo())){
			infs1.setDriverNo(informationInsureds.getDriverNo());
		}
		if(informationInsureds.getDestinationCountry()!=null &&!"".equals(informationInsureds.getDestinationCountry())){
			infs1.setDestinationCountry(informationInsureds.getDestinationCountry());
		}
		if(informationInsureds.getDestinationCountryText()!=null &&!"".equals(informationInsureds.getDestinationCountryText())){
			infs1.setDestinationCountryText(informationInsureds.getDestinationCountryText());
		}
		if(informationInsureds.getFlightLocation()!=null &&!"".equals(informationInsureds.getFlightLocation())){
			infs1.setFlightLocation(informationInsureds.getFlightLocation()); 
		}
		if(informationInsureds.getIsSelf()!=null &&!"".equals(informationInsureds.getIsSelf())){
			infs1.setIsSelf(informationInsureds.getIsSelf());
		}
		if(informationInsureds.getHeight()!=null &&!"".equals(informationInsureds.getHeight())){
			infs1.setHeight(informationInsureds.getHeight());
		}
		if(informationInsureds.getWeight()!=null &&!"".equals(informationInsureds.getWeight())){
			infs1.setWeight(informationInsureds.getWeight());
		}
		if(informationInsureds.getOverseasOccupation()!=null &&!"".equals(informationInsureds.getOverseasOccupation())){
			infs1.setOverseasOccupation(informationInsureds.getOverseasOccupation());
		}
		if(informationInsureds.getNationality()!=null &&!"".equals(informationInsureds.getNationality())){
			infs1.setNationality(informationInsureds.getNationality());
		}
		if(informationInsureds.getTravelMode()!=null &&!"".equals(informationInsureds.getTravelMode())){
			infs1.setTravelMode(informationInsureds.getTravelMode());
		}
		if(informationInsureds.getTravelType()!=null &&!"".equals(informationInsureds.getTravelType())){
			infs1.setTravelType(informationInsureds.getTravelType());
		}
		if(informationInsureds.getHaveBuy()!=null &&!"".equals(informationInsureds.getHaveBuy())){
			infs1.setHaveBuy(informationInsureds.getHaveBuy());
		}
		if(informationInsureds.getRecognizeePrem()!=null&&!"".equals(informationInsureds.getRecognizeePrem())){
			infs1.setRecognizeePrem(informationInsureds.getRecognizeePrem());
		}
		if(informationInsureds.getRecognizeeOperate()!=null &&!"".equals(informationInsureds.getRecognizeeOperate())){
			infs1.setRecognizeeOperate(informationInsureds.getRecognizeeOperate());
		} 
		if(informationInsureds.getDiscountPrice()!=null&&!"".equals(informationInsureds.getDiscountPrice())){
			infs1.setDiscountPrice(informationInsureds.getDiscountPrice());
		}
		
		return infs1;
	}
	@Override
	public String getBrithdayByFactor(String startdate,String age) {
		if(StringUtil.isEmpty(startdate)){
			startdate=PubFun.getCurrentDate();
		}
		String birthday ="1991-01-01";
		if (age != null && !"".equals(age)) {
			String ages[] = age.split("-");
			//参数为单独年龄情况 ，如18Y
			if(ages.length>1){
				if(ages[0].endsWith("D") || ages[0].endsWith("M") || ages[1].endsWith("D") || ages[0].endsWith("M")){
					age = "1";
				}
			}
			if(ages[0].endsWith("D")){ 
				age=ages[0].substring(0, ages[0].indexOf("D"));
				if ("0".equals(age)) {
					age = "1";
				} 
				birthday = PubFun.calSDate(startdate, 0 - Integer.parseInt(age), "D");
			}else if(ages[0].endsWith("M")){
				age=ages[0].substring(0, ages[0].indexOf("M"));
				if ("0".equals(age)) {
					age = "1";
				} 
				birthday = PubFun.calSDate(startdate, 0 - Integer.parseInt(age), "M");
			}else if(ages[0].endsWith("Y")){
				age=ages[0].substring(0, ages[0].indexOf("Y"));
				if ("0".equals(age)) {
					birthday = PubFun.calSDate(startdate, -1, "Y");
					birthday = PubFun.calSDate(birthday, 1, "D");
				} else {
					birthday = PubFun.calSDate(startdate, 0 - Integer.parseInt(age), "Y");
				}
			}else{
				birthday = age;
			}
		}
		return birthday;
	}
	@Override
	public SDInformation updateInformation(SDInformation infs,
			SDInformation information) {
		infs.setStartDate(information.getStartDate());
		OrderConfigNewAction t = new OrderConfigNewAction();
		infs.setEndDate(t.getEndDate(information.getEndDate()));
		infs.setModifyDate(new Date());
		// TODO Auto-generated method stub
		
		return infs;
	}
	@Override
	public boolean validateOccup(String occupId, String productId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean validateAge(String BirthdayId, String productId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Map<String, String> CalPrice(String RiskCode, Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getCountryText2007(String cComCode, String cCountryCode) {

		List<HashMap<String, String>> countryList = new ArrayList<HashMap<String, String>>();
		String shenGenEn = "";
		String shenGenCh = "";
		String unShenGenEn = "";
		String unShenGenCh = "";
		String dest = "";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select codeEnName,codeName,flagType from dictionary where codetype='CountryCode' and insuranceCode = ? and codeValue in (");
			if(StringUtil.isNotEmpty(cCountryCode)){
				String[] s = cCountryCode.split(",");
				for(int i=0;i<s.length;i++ ){
					if(i!=(s.length-1)){
						sb.append("'"+s[i]+"',");
					}else{
						sb.append("'"+s[i]+"'");
					}
				}
			}
			sb.append(")");
			String[] sqltemp = {cComCode};
			countryList = new GetDBdata().query(sb.toString(),sqltemp);
			Iterator<HashMap<String, String>> countryIt = countryList.iterator();
			while (countryIt.hasNext()) {
				HashMap<String, String> countryMap = (HashMap<String, String>) countryIt.next();
				if ("Y".equals(countryMap.get("flagType"))) {
					shenGenEn += countryMap.get("codeEnName") + ",";
					shenGenCh += countryMap.get("codeName") + ",";
				} else if ("N".equals(countryMap.get("flagType"))) {
					unShenGenEn += countryMap.get("codeEnName") + ",";
					unShenGenCh += countryMap.get("codeName") + ",";
				}
			}
			if (StringUtil.isNotEmpty(unShenGenCh) && StringUtil.isNotEmpty(unShenGenEn)) {
				dest = shenGenCh + "申根协议国家" + " " + shenGenEn + "SCHENGEN STATES" + " " + unShenGenCh.substring(0, unShenGenCh.lastIndexOf(",")) + " "
						+ unShenGenEn.substring(0, unShenGenEn.lastIndexOf(","));
			} else {
				dest = shenGenCh + "申根协议国家" + " " + shenGenEn + "SCHENGEN STATES" + " ";
			}
		} catch (Exception e) {
			logger.info("查询目的地异常" + e.getMessage(), e);
		}
		return dest;
	}
	@Override
	public String getSchengenCountryText(String cComCode, String cCountryCode,String cProductID) {

		List<HashMap<String, String>> countryList = new ArrayList<HashMap<String, String>>(); 
		String shenGen = "";
		String unShenGen = "";
		String dest = "";
		try {
			int t = new QueryBuilder(" SELECT COUNT(1) FROM dictionary WHERE codetype='CountryCode' AND productId = ? ",cProductID).executeInt();
			String wherepart = " and insuranceCode = '"+cComCode+"'";
			if(t>=1){
				wherepart = " and productId = '"+cProductID+"'";
			}
			StringBuffer sb = new StringBuffer();
			sb.append("select codeEnName,codeName,flagType from dictionary where codetype='CountryCode' "+wherepart+" and codeValue in (");
			if(StringUtil.isNotEmpty(cCountryCode)){
				String[] s = cCountryCode.split(",");
				for(int i=0;i<s.length;i++ ){
					if(i!=(s.length-1)){
						sb.append("'"+s[i]+"',");
					}else{
						sb.append("'"+s[i]+"'");
					}
				}
			}
			sb.append(")");
			//String[] sqltemp = {cComCode};
			//countryList = new GetDBdata().query(sb.toString(),sqltemp);
			countryList = new GetDBdata().query(sb.toString());
			Iterator<HashMap<String, String>> countryIt = countryList.iterator();
			boolean schengenFlag = false;
			while (countryIt.hasNext()) {
				HashMap<String, String> countryMap = (HashMap<String, String>) countryIt.next();
				if ("Y".equals(countryMap.get("flagType"))) {
                    if(countryMap.get("codeEnName").toUpperCase().indexOf("SCHENGEN")==-1){
                    	shenGen += countryMap.get("codeName")+"/"+countryMap.get("codeEnName")+"，";
                    }else{
                    	schengenFlag = true;
                    }
				} else if ("N".equals(countryMap.get("flagType"))||"".equals(countryMap.get("flagType"))) {

					unShenGen += countryMap.get("codeName")+"/"+countryMap.get("codeEnName")+"，";
				}
			}
			String shenGenChar = "申根国家/Schengen ";
			if (StringUtil.isNotEmpty(shenGen)) {
				dest = dest+shenGen+shenGenChar;
			}
			if(StringUtil.isEmpty(shenGen)&&schengenFlag){
				dest = dest+shenGenChar;
			}
		    if (StringUtil.isNotEmpty(unShenGen)) {
				dest = dest+unShenGen;
			}
		} catch (Exception e) {
			logger.error("查询目的地异常" + e.getMessage(), e);
		}
		dest = dest.substring(0, dest.length()-1)+";";
		return dest;
	}
	@Override
	public String getCountryText1015(String comCode, String destinationCountry) {
		String[] country = destinationCountry.split(",");
       String countryEn = "";
       if(country!=null && country.length>0){
           for(String s : country){
              Dictionary d = dictionaryService.getNameByCodeValue(comCode, "CountryCode", s.trim());
              countryEn = d.getCodeEnName();
              if("Y".equals(d.getFlagType().trim())){
                  return countryEn;
              }
           }
       }
       return countryEn;
	}
	/**
	 * 被保人批量导入
	 * @param cPath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getInsuredList(String comCode,String productID,String cPath,Map<String,Object> cMap, String memberId){
		  HSSFWorkbook workbook;//工作簿
		  FileInputStream finput = null;
		  List<SDInformationInsured> insuredList = new ArrayList<SDInformationInsured>();
		  List<SDInformationInsured> insuredErrorList = new ArrayList<SDInformationInsured>();
		  Map<String,Object> mMap = new HashMap<String,Object>();
		  Map<String,Object> insuredPremMap = new HashMap<String,Object>(); 
		  String effdate = String.valueOf(cMap.get("effdate"));
		  String gErrorMsg = "";
		  String warnMessage = "";
		  boolean selfFlag = true;//是否为本人标志true:不是
		  boolean maxFlag = true;//是否超过导入数量最大限制 （太平洋 50人，其他没有核保的公司为200人）
		  double proTolPrem = 0.0;// 原保费
		  double disPrem = 0.0;// 保险公司打折后保费
		  double insuredPrem = 0.0;// 打折活动后保费
	      try {
			  finput = new FileInputStream(cPath);
			  POIFSFileSystem fs = null;
			  fs = new POIFSFileSystem(finput);
		      workbook = new HSSFWorkbook(fs);
			  //获得指定的sheet
			  Sheet sheet = workbook.getSheetAt(0);
			  //获得sheet总行数
			  int rowCount = sheet.getLastRowNum();
			  if(rowCount>214)rowCount=214;
			  logger.info("found excel rows count:{}", rowCount);
			  if(rowCount < 15){
				  mMap.put("state", "2");
				  mMap.put("errorMsg", "请使用正确的模板进行上传操作！(原因:模板行数小于15行)");
			      return mMap;
			  }
			  Row rowMax = sheet.getRow(214);
			  int maxCount=200;
			  if("2011".equals(comCode)){
				  rowMax = sheet.getRow(64);
				  maxCount=50;
			  }
			  if(rowMax!=null && !"".equals(rowMax)){
				  if(rowMax.getCell(2)!=null && !"".equals(rowMax.getCell(2))){
					  Cell cellMax = rowMax.getCell(2);
					  if(cellMax.getStringCellValue()!=null && !"".equals(cellMax.getStringCellValue())){
						  maxFlag = false; 
					  }
				  } 
			  } 
			  if(!maxFlag){
				  mMap.put("state", "2");
				  mMap.put("errorMsg", "上传人数超过上限("+maxCount+"人)，请重新上传！");
			      return mMap;
			  }
			  /*if(rowCount >= 64){
				  mMap.put("state", "2");
				  mMap.put("errorMsg", "上传人数超过上限(上限50人)，请重新上传！");
			      return mMap;
			  }*/
			  //根据产品判断使用模板是否正确
			  String keyValue = this.getExcelKeyValue(productID);
			  boolean keyFlag1 = false;
			  boolean keyFlag2 = false;
			  if(!"".equals(keyValue)||keyValue!=null){
				  String[] arr = keyValue.split(";");
				  for(int i=0;i<arr.length;i++){
					  Row row = sheet.getRow(13);
					  int cellCount = row.getLastCellNum();
					  boolean keyFlag1_1=false;
					  for(int j=2;j<cellCount;j++){
						  String v = String.valueOf(getCellString(row.getCell(j))).trim();
						  if(!"".equals(arr[i])&&!"null".equals(arr[i])&&arr[i]!=null&&!"".equals(v)&&!"null".equals(v)&&v!=null){
							  if(arr[i].equals(v)){
								  keyFlag1_1 = true;
								  break;
							  }
						  }
					  }
					  if(keyFlag1_1){keyFlag1=true;}else{keyFlag1=false;}
					  if(!keyFlag1){
						  mMap.put("state", "2");
						  mMap.put("errorMsg", "请不要恶意更改上传模板，请使用正确的模板进行导入");
					      mMap.put("insuredPrem", new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP));
						  mMap.put("proTolPrem", new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP));
						  return mMap;
					  }
				  }
			  }
			  Row row1 = sheet.getRow(13);
			  int cellCount1 = row1.getLastCellNum();
			  for(int j=2;j<cellCount1;j++){
				  if(!"".equals(keyValue)||keyValue!=null){
					  String[] arr = keyValue.split(";");
					  boolean keyFlag2_1=false;
					  for(int i=0;i<arr.length;i++){
						  String v = String.valueOf(getCellString(row1.getCell(j))).trim();
						  if(!"".equals(arr[i])&&!"null".equals(arr[i])&&arr[i]!=null&&!"".equals(v)&&!"null".equals(v)&&v!=null){
							  if(arr[i].equals(v)){
								  keyFlag2_1 = true;
								  break;
							  }
						  }
					  }
					  if(keyFlag2_1){keyFlag2=true;}else{keyFlag2=false;}
					  if(!keyFlag2){
						  mMap.put("state", "2");
						  mMap.put("errorMsg", "请不要恶意更改上传模板，请使用正确的模板进行导入");
					      mMap.put("insuredPrem", new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP));
						  mMap.put("proTolPrem", new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP));
						  return mMap;
					  }
				  }
			  }
			  String limitAge = this.getSectionAge(productID);//产品年龄限制
			  if ("Y".equals((String)cMap.get("complicatedFlag"))) {
				  String textAge = (String)cMap.get("textAge");
				  if (StringUtil.isNotEmpty(textAge)) {
					  limitAge = textAge;
				  }
			  }
			  
			  //遍历行row
			  int insured_index=0;
			  for (int rowIndex = 14; rowIndex <= rowCount; rowIndex++) {
				String errorMsg = "";
				SDInformationInsured insured = new SDInformationInsured();
				boolean idTypeFlag = false;//是否身份证
				boolean valadateFlag = true;//是否有错误信息 true:无
			    //获得行对象
			    Row row = sheet.getRow(rowIndex);
			    if(null != row){
			    	if(String.valueOf(getCellString(row.getCell(2))).trim()==null || "".equals(String.valueOf(getCellString(row.getCell(2))).trim()) || "null".equals(String.valueOf(getCellString(row.getCell(2))).trim())){
			    		break;
				    }
			      //获得本行中单元格的个数
			      int cellCount = row.getLastCellNum();
			      String birthday_value = "";
			      String cardSex = "";
			      boolean isInvalidIdCard = false;//身份证号无效标识
			      //遍历列cell
			      for (short cellIndex = 2; cellIndex < cellCount; cellIndex++) {
			        Cell cell = row.getCell(cellIndex);
			        /*if(getCellString(cell)==null || "null".equals(getCellString(cell)) || "".equals(getCellString(cell))){
			        	continue;
			        }*/
			        //获得指定单元格中的数据
			        String cellValue = String.valueOf(getCellString(cell)).trim();
			        if(StringUtil.isEmpty(cellValue)){
			        	cellValue = "";
			        }
			        String cellTrueValue = "";
			        if(cellIndex==2){
			        	//姓名
			        	String cErrorMsg = "";
			        	insured.setRecognizeeName(cellValue);
			        	if(StringUtil.isEmpty(cellValue)){
			        		cErrorMsg  += "姓名不能为空;";
			        		valadateFlag = false;
			        	}else{
			        		cErrorMsg = cErrorMsg+checkValue("姓名",cellValue);
			        		if(cErrorMsg.indexOf("非法字符")!=-1){
			        			valadateFlag = false;
			        		}else{
					        	//姓名校验规则
			        			cellValue = cellValue.replace("/\\s+/g"," ");
			        			String resultName = checkName(cellValue);
					        	if (!resultName.isEmpty()) {
					        		cErrorMsg  += resultName;
					        		valadateFlag = false;
								}
			        		}
			        	}

			        	if(StringUtil.isNotEmpty(cErrorMsg)){
			        		errorMsg = errorMsg+"<i id='sdrecognizeeName_"+insured_index+"_error'>"+cErrorMsg+"</i>";
			        	}
			        	continue;
			        }
			        if(cellIndex==3){
			        	//与投保人关系:根据excel中的值、保险公司、产品id找到对应的编码值
			        	String cErrorMsg = "";
			        	Map<String, String> codesInfo= dictionaryService.getCodeNamesInfo(productID, comCode, "Relationship");
	        			if (codesInfo == null || codesInfo.isEmpty()) {
	        				return returnError("2", "上传模版错误，请从页面上下载正确的模版填写！(原因:该产品或保险公司未在后台设置与投保人关系基础信息)");
	        			} else {
	        				if (codesInfo.containsKey(cellValue)) {
	        					cellTrueValue = codesInfo.get(cellValue);
	        				}
	        				if(StringUtil.isEmpty(cellTrueValue)){
	        					return returnError("2", "上传模版错误，请从页面上下载正确的模版填写！(原因:与投保人关系为空)");
				        	}
	        			}
	        			
			        	if (StringUtil.isEmpty(cellValue)){
			        		cErrorMsg += "与投保人关系不能为空;";
			        		valadateFlag = false;
			        	}else if(cellValue.indexOf("本人")!=-1){
			        		if(!selfFlag){
			        			cErrorMsg += "与投保人关系为“本人”的不能多于1人;";
			        			valadateFlag = false;
			        		}
			        		selfFlag = false;
			        		cErrorMsg = cErrorMsg+checkValue("投保人关系",cellValue);
			        		if(cErrorMsg.indexOf("非法字符")!=-1){
			        			valadateFlag = false;
			        		}
			        	}else{
			        		cErrorMsg = cErrorMsg+checkValue("投保人关系",cellValue);
			        		if(cErrorMsg.indexOf("非法字符")!=-1){
			        			valadateFlag = false;
			        		}
			        	}
			        	
			        	if(StringUtil.isNotEmpty(cErrorMsg)){
			        		errorMsg = errorMsg+"<i id='sdrecognizeeRelationName_"+insured_index+"_error'>"+cErrorMsg+"</i>";
			        	}
			        	insured.setRecognizeeAppntRelation(cellTrueValue);
			        	insured.setRecognizeeAppntRelationName(cellValue);
			        	continue;
			        }
			        if(cellIndex==4){
			        	//证件类型:根据excel中的值、保险公司、产品id找到对应的编码值
			        	String cErrorMsg = "";
			        	Map<String, String> codesInfo= dictionaryService.getCodeNamesInfo(productID, comCode, "certificate");
	        			if (codesInfo == null || codesInfo.isEmpty()) {
	        				return returnError("2", "上传模版错误，请从页面上下载正确的模版填写！(原因:该产品或保险公司未在后台设置证件类型基础信息)");
	        			} else {
	        				if (codesInfo.containsKey(cellValue)) {
	        					cellTrueValue = codesInfo.get(cellValue);
	        				}
	        				if(StringUtil.isEmpty(cellTrueValue)){
	        					return returnError("2", "上传模版错误，请从页面上下载正确的模版填写！(原因:证件类型为空)");
				        	}
	        			}
			        	insured.setRecognizeeIdentityType(cellTrueValue);
			        	insured.setRecognizeeIdentityTypeName(cellValue);
			        	if(StringUtil.isEmpty(cellValue)){
			        		cErrorMsg  += "证件类型不能为空;";
			        		valadateFlag = false;
			        	}else{
			        		cErrorMsg = cErrorMsg+checkValue("证件类型",cellValue);
			        		if(cErrorMsg.indexOf("非法字符")!=-1){
			        			valadateFlag = false;
			        		}
			        	}
			        	
			        	if("身份证".equals(cellValue)){
			        		idTypeFlag = true;
			        	}
			        	if(StringUtil.isNotEmpty(cErrorMsg)){
			        		errorMsg = errorMsg+"<i id='sdrecognizeeTypeName_"+insured_index+"_error'>"+cErrorMsg+"</i>";
			        	}
			        	continue;
			        }
			        if(cellIndex==5){
			        	//证件号码
			        	String cErrorMsg = "";
			        	cellValue = cellValue.replaceAll(" ", "").toUpperCase();//证件号码空格自动纠错
			        	insured.setRecognizeeIdentityId(cellValue);
			        	if(idTypeFlag){
			        		if(StringUtil.isEmpty(cellValue)||!isIdentityCard(cellValue)){
			        			cErrorMsg += "请输入正确的身份证号;";
			        		valadateFlag = false;
			        		isInvalidIdCard = true;
			        		}else{
			        			cErrorMsg = cErrorMsg+checkValue("证件号码",cellValue);
			        			if(cErrorMsg.indexOf("非法字符")!=-1){
				        			valadateFlag = false;
				        		}else{
				        		if (cellValue.substring(cellValue.length()-1).equals("x")) {     
				        			cErrorMsg += "身份证无效，身份证号码最后一位请输入大写：X";                                       
				        	        valadateFlag = false;                                                                    
				        	        } 
				        		}
			        			//校验身份证与输入生日是否一致
			        			String year = cellValue.substring(6, 10);       
			        			String month = cellValue.substring(10, 12);      
			        			String day = cellValue.substring(12, 14);
			        			birthday_value = year+"-"+month+"-"+day;
			        			String sexno = ""; 
			        			if(cellValue.length()==18){
			        				sexno=cellValue.substring(16,17);
			        			}else if(cellValue.length()==15){
			        				sexno=cellValue.substring(14,15);
			        			}
			        			int tempid=Integer.parseInt(sexno) % 2;
			        			if(tempid==0){
			        				cardSex = "女";
			        			}else{
			        				cardSex= "男";
			        			}
			        		}
			        	}else{
			        		if(StringUtil.isEmpty(cellValue)){
			        			cErrorMsg += "请输入正确的证件号码;";
				        		valadateFlag = false;
				        	}else{
				        		if (cellValue.length() >2 && cellValue.length()<40) {
					        		cErrorMsg = cErrorMsg+checkValue("证件号码",cellValue);
					        		if(cErrorMsg.indexOf("非法字符")!=-1){
						        			valadateFlag = false;
						        	}
								}else{
				        			cErrorMsg += "其他的证件类型长度在3-40字符之间;";
					        		valadateFlag = false;
								}

				        	}
			        	}
			        	if(insuredList!=null && insuredList.size()>=1){
			        		for(int i=0;i<insuredList.size();i++){
			        			if(StringUtil.isNotEmpty(insured.getRecognizeeIdentityType())){
			        				if(insured.getRecognizeeIdentityType().equals(insuredList.get(i).getRecognizeeIdentityType())
			        						&&insured.getRecognizeeIdentityId().equals(insuredList.get(i).getRecognizeeIdentityId())){
			        					cErrorMsg += "与被保人"+insuredList.get(i).getRecognizeeName()+"证件相同;";
			        					valadateFlag = false;
			        				}
			        			}
				        	}
			        	}
			        	if(StringUtil.isNotEmpty(cErrorMsg)){
			        		errorMsg = errorMsg+"<i id='sdrecognizeeId_"+insured_index+"_error'>"+cErrorMsg+"</i>";
			        	}
			        	continue;
			        }
			        if(cellIndex==6){
			        	//年龄校验
			        	String cErrorMsg = "";
			        	
			        	if (StringUtil.isEmpty(cellValue)) { 
			        		cErrorMsg += "请输入出生日期;";
			        		valadateFlag = false;
			        	}else{
			        		if (idTypeFlag && !isInvalidIdCard) {
				        		if (!birthday_value.trim().equals(cellValue.trim())) {
					        		cErrorMsg += "身份证计算的出生日期与输入的出生日不一致！";
					        		valadateFlag = false;
								}
							}

			        		if(productID.startsWith("1087") || productID.startsWith("1001") || productID.startsWith("1004") || productID.startsWith("2043")){
			        			if(!validateAgeNew(cellValue, limitAge)){
				        			cErrorMsg += "此年龄不能购买本产品;";
					        		valadateFlag = false;
				        		}
							}else{
								if(!validateAgeNewBaseOnEffectiveDate(cellValue, limitAge, ((SDInformation)cMap.get("sdinformation")).getStartDate())){
									cErrorMsg += "此年龄不能购买本产品;";
					        		valadateFlag = false;
				        		}
							}
			        		
			        		cErrorMsg = cErrorMsg+checkValue("出生日期",cellValue);
			        		if(cErrorMsg.indexOf("非法字符")!=-1){
			        			valadateFlag = false;
			        		}
			        	}
			        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			        	if(StringUtil.isNotEmpty(cellValue)){
			        		cellValue = sdf.format(sdf.parse(cellValue.replaceAll("/", "-")));
			        	}
			        	//出生日期
			        	insured.setRecognizeeBirthday(cellValue);
			        	if(StringUtil.isNotEmpty(cErrorMsg)){
			        		errorMsg = errorMsg+"<i id='sdrecognizeeBirthday_"+insured_index+"_error'>"+cErrorMsg+"</i>";
			        	}
			        	continue;
			        }
			        if(cellIndex==7){
			        	String cErrorMsg = "";
			        	if(StringUtil.isEmpty(cellValue)){
			        		cErrorMsg += "性别不能为空;";
			        		valadateFlag = false;
			        	}else{
			        		if (idTypeFlag && !isInvalidIdCard) {
				        		if (!cellValue.equals(cardSex)) {
				        			cErrorMsg += "性别 与身份证性别不符;";
					        		valadateFlag = false;
								}
							}

			        		if(!"男".equals(cellValue)&&!"女".equals(cellValue)){
			        			cErrorMsg += "性别输入有误;";
				        		valadateFlag = false;
				        	}
			        		cErrorMsg = cErrorMsg+checkValue("性别",cellValue);
			        		if(cErrorMsg.indexOf("非法字符")!=-1){
			        			valadateFlag = false;
			        		}
			        	}
			        	//与性别:根据excel中的值、保险公司、产品id找到对应的编码值
			        	cellTrueValue = this.dictionaryService.getCodeValueByCodeName(comCode, "Sex", cellValue);
			        	insured.setRecognizeeSex(cellTrueValue);
			        	insured.setRecognizeeSexName(cellValue);
			        	if(StringUtil.isNotEmpty(cErrorMsg)){
			        		errorMsg = errorMsg+"<i id='sdrecognizeeSexName_"+insured_index+"_error'>"+cErrorMsg+"</i>";
			        	}
			        	
			        }
			        if(cellIndex==8){
			        	//手机号
			        	String cErrorMsg = "";
			        	if (StringUtil.isEmpty(cellValue)){
			        		cErrorMsg += "请输入手机号码;";
			        		valadateFlag = false;
			        	}else{
			        		try{
				        		BigDecimal bd = new BigDecimal(cellValue); 
				        		cellValue = bd.toPlainString();
				        		cellValue = cellValue.replaceAll(" ", "");//手机号录入空格纠错处理
				        		cErrorMsg = cErrorMsg+checkMobile(cellValue);
				        		cErrorMsg = cErrorMsg+checkValue("手机号码",cellValue);
				        		if(cErrorMsg.indexOf("非法字符")!=-1||cErrorMsg.indexOf("请输入正确的手机号")!=-1){
				        			valadateFlag = false;
				        		}
			        		}catch(NumberFormatException e){
			        			cErrorMsg += "请输入正确的手机号;";
				        		valadateFlag = false;
			        		}
			        	}
			        	insured.setRecognizeeMobile(cellValue);
			        	if(StringUtil.isNotEmpty(cErrorMsg)){
			        		errorMsg = errorMsg+"<i id='sdrecognizeeMobile_"+insured_index+"_error'>"+cErrorMsg+"</i>";
			        	}
			        	continue;
			        }
			        if(cellIndex==9){
			        	String flagValue = sheet.getRow(13).getCell(9).getStringCellValue();
			        	if(StringUtil.isNotEmpty(flagValue)){
			        		if(flagValue.indexOf("航班号")!=-1){
			        			String cErrorMsg = "";
			        			insured.setFlightNo(cellValue);
			        			if(StringUtil.isEmpty(cellValue)){
			        				cErrorMsg += "航班号不能为空;";
					        		valadateFlag = false;
			        			}
			        			cErrorMsg = cErrorMsg+checkValue("航班号",cellValue);
			        			if(cErrorMsg.indexOf("非法字符")!=-1){
				        			valadateFlag = false;
				        		}
			        			if(StringUtil.isNotEmpty(cErrorMsg)){
					        		errorMsg = errorMsg+"<i id='sdrecognizeeflightNo_"+insured_index+"_error'>"+cErrorMsg+"</i>";
					        	}
			        		} 
			        		if(flagValue.indexOf("英文名")!=-1){
			        			String cErrorMsg = "";
			        			insured.setRecognizeeEnName(cellValue);
			        			if(StringUtil.isEmpty(cellValue)){
			        				cErrorMsg += "英文名不能为空;";
					        		valadateFlag = false;
			        			}
			        			cErrorMsg = cErrorMsg+checkValue("英文名",cellValue);
			        			if(cErrorMsg.indexOf("非法字符")!=-1){
				        			valadateFlag = false;
				        		}
			        			cErrorMsg = cErrorMsg+checkEnName("英文名",cellValue);
			        			if(cErrorMsg.indexOf("英文名")!=-1){
				        			valadateFlag = false;
				        		}
			        			if(StringUtil.isNotEmpty(cErrorMsg)){
					        		errorMsg = errorMsg+"<i id='sdrecognizeeEnName_"+insured_index+"_error'>"+cErrorMsg+"</i>";
					        	}
			        		}
			        		if(flagValue.indexOf("旅游目的地")!=-1){
			        			String cErrorMsg = "";
			        			Map<String, String> codesInfo= dictionaryService.getCodeNamesInfo(productID, comCode, "CountryCode");
			        			if (codesInfo == null || codesInfo.isEmpty()) {
			        				return returnError("2", "上传模版错误，请从页面上下载正确的模版填写！(原因:该产品或保险公司未在后台设置旅游目的地基础信息)");
			        			} else {
			        				if (codesInfo.containsKey(cellValue)) {
			        					cellTrueValue = codesInfo.get(cellValue);
			        				}
			        				if(StringUtil.isEmpty(cellTrueValue) && codesInfo.size() == 1){
				        				for(Entry<String, String> vo : codesInfo.entrySet()){ 
				        					cellTrueValue = vo.getValue();
				        					cellValue = vo.getKey();
				        		        }
						        	}else if(StringUtil.isEmpty(cellTrueValue)){
						        		return returnError("2", "上模版填写！(原因:旅游目的地为空)");
						        	}
			        			}
			        			insured.setDestinationCountry(cellTrueValue);
			        			insured.setDestinationCountryText(cellValue);
			        			if(StringUtil.isEmpty(cellValue)){
			        				cErrorMsg += "旅游目的地不能为空;";
					        		valadateFlag = false;
			        			}
			        			cErrorMsg = cErrorMsg+checkValue("旅游目的地",cellValue);
			        			if(cErrorMsg.indexOf("非法字符")!=-1){
				        			valadateFlag = false;
				        		}
			        			if(StringUtil.isNotEmpty(cErrorMsg)){
					        		errorMsg = errorMsg+"<i id='sdrecognizeedestinationCountryText_"+insured_index+"_error'>"+cErrorMsg+"</i>";
					        	}
			        		}
			        	}
			        }
			        if(cellIndex==10){
			        	String flagValue = sheet.getRow(13).getCell(10).getStringCellValue();
			        	if(StringUtil.isNotEmpty(flagValue)){
			        		if(flagValue.indexOf("旅游目的地")!=-1){
			        			String cErrorMsg = "";
			        			Map<String, String> codesInfo= dictionaryService.getCodeNamesInfo(productID, comCode, "CountryCode");
			        			if (codesInfo == null || codesInfo.isEmpty()) {
			        				return returnError("2", "上传模版错误，请从页面上下载正确的模版填写！(原因:该产品或保险公司未在后台设置旅游目的地基础信息)");
			        			} else {
			        				if (codesInfo.containsKey(cellValue)) {
			        					cellTrueValue = codesInfo.get(cellValue);
			        				}
			        				if(StringUtil.isEmpty(cellTrueValue) && codesInfo.size() == 1){
				        				for(Entry<String, String> vo : codesInfo.entrySet()){ 
				        					cellTrueValue = vo.getValue();
				        					cellValue = vo.getKey(); 
				        		        }
						        	}else if(StringUtil.isEmpty(cellTrueValue)){
						        		return returnError("2", "上传模版错误，请从页面上下载正确的模版填写！(原因:旅游目的地为空)");
						        	}
			        			}
			        			
			        			insured.setDestinationCountry(cellTrueValue);
			        			insured.setDestinationCountryText(cellValue);
			        			if(StringUtil.isEmpty(cellValue)){
			        				cErrorMsg += "旅游目的地不能为空;";
					        		valadateFlag = false;
			        			}
			        			
			        			cErrorMsg = cErrorMsg+checkValue("旅游目的地",cellValue);
			        			if(cErrorMsg.indexOf("非法字符")!=-1){
				        			valadateFlag = false;
				        		}
			        			if(StringUtil.isNotEmpty(cErrorMsg)){
					        		errorMsg = errorMsg+"<i id='sdrecognizeedestinationCountryText_"+insured_index+"_error'>"+cErrorMsg+"</i>";
					        	}
			        		}
			        		if(flagValue.indexOf("起飞时间")!=-1){
			        			String cErrorMsg = "";
			        			if(StringUtil.isEmpty(cellValue)){
			        				cErrorMsg += "起飞时间不能为空;";
					        		valadateFlag = false;
			        			}else{
			        				cErrorMsg = cErrorMsg+checkValue("起飞时间",cellValue);
			        				if(cErrorMsg.indexOf("非法字符")!=-1){
					        			valadateFlag = false;
					        		}else{
					        			insured.setFlightTime(cellValue+":00");
					        		}
			        			}
			        			if(StringUtil.isNotEmpty(cErrorMsg)){
					        		errorMsg = errorMsg+"<i id='sdrecognizeeflightTime_"+insured_index+"_error'>"+cErrorMsg+"</i>";
					        	}
			        		}
			        	}
			        }
			        if(cellIndex==11){
			        	String flagValue = sheet.getRow(13).getCell(11).getStringCellValue();
			        	if(StringUtil.isNotEmpty(flagValue)){
			        		if(flagValue.indexOf("起飞地点")!=-1){
			        			String cErrorMsg = "";
			        			insured.setFlightLocation(cellValue);
			        			if(StringUtil.isEmpty(cellValue)){
			        				cErrorMsg += "起飞地点不能为空;";
					        		valadateFlag = false;
			        			}else{
			        				cErrorMsg = cErrorMsg+checkValue("起飞地点",cellValue);
			        				if(cErrorMsg.indexOf("非法字符")!=-1){
					        			valadateFlag = false;
					        		}
			        			}
			        			if(StringUtil.isNotEmpty(cErrorMsg)){
					        		errorMsg = errorMsg+"<i id='sdrecognizeeflightLocation_"+insured_index+"_error'>"+cErrorMsg+"</i>";
					        	}
			        		} 
			        	}
			        } 
			        //根据excel模板配置判断是否需要处理一下信息
			        //职业、地区：旅游险下这两项都不要
			        //安盛:旅游目的地
			        //航班号、起飞时间
			        //航班号、起飞时间、起飞地点
			      }
			      cMap.put("insuredBirthDay", insured.getRecognizeeBirthday());
			      cMap.put("insuredSex", insured.getRecognizeeSex());
			      String insuredSex = insured.getRecognizeeSex();
			      String realSecAge = null;
			      String dateNow = PubFun.getCurrentDate();
			 		if(productID.startsWith("1087") || productID.startsWith("1001") || productID.startsWith("1004") || productID.startsWith("2043")){
			 			realSecAge = getRealSectionAge(insured.getRecognizeeBirthday(), limitAge,dateNow);
					}else{
						  realSecAge = getRealSectionAge(insured.getRecognizeeBirthday(), limitAge, effdate);
						 
					}
			        if(!"-1".equals(realSecAge)){
			        	if(String.valueOf(insuredPremMap.get(insuredSex+"-"+realSecAge))!=null && !"null".equals(String.valueOf(insuredPremMap.get(insuredSex+"-"+realSecAge))) && !"".equals(String.valueOf(insuredPremMap.get(insuredSex+"-"+realSecAge)))){
			        		Map<String,Object> tempMap = (Map<String,Object>)insuredPremMap.get(insuredSex+"-"+realSecAge);
			        		insured.setDiscountPrice(String.valueOf(tempMap.get("retCountPrem")));
			        		insured.setRecognizeePrem(String.valueOf(tempMap.get("retPrem")));
			        		insured.setRecognizeeTotalPrem(String.valueOf(tempMap.get("productPrem")));
							if (tempMap.containsKey("errMessage")
									&& StringUtil.isNotEmpty((String) tempMap
											.get("errMessage"))) {
								errorMsg += tempMap.get("errMessage");
								valadateFlag = false;
							} else {
								if ("0".equals(String.valueOf(tempMap
										.get("retCountPrem")))
										|| "0".equals(String.valueOf(tempMap
												.get("productPrem")))) {
									errorMsg = errorMsg + "保费试算失败，请输入正确的日期!";
									valadateFlag = false;
								}
							}
			        	}else{
			        		Map<String,Object> rMap = null;
			        		try{
			        			rMap = this.calPrem(cMap, memberId);
			        		}catch(Exception e){
								logger.error(e.getMessage(), e);
			        		}
			        		
							if (rMap.containsKey("errMessage")
									&& StringUtil.isNotEmpty((String) rMap
											.get("errMessage"))) {
								errorMsg += rMap.get("errMessage");
								valadateFlag = false;
							} else {
								if ("0".equals(String.valueOf(rMap
										.get("retCountPrem")))
										|| "0".equals(String.valueOf(rMap
												.get("productPrem")))) {
									errorMsg = errorMsg + "保费试算失败，请输入正确的日期!";
									valadateFlag = false;
								}
							}
							
							if (rMap.containsKey("warnMessage")
									&& StringUtil.isNotEmpty((String) rMap
											.get("warnMessage"))) {
								if (StringUtil.isEmpty(warnMessage)) {
									warnMessage = (String) rMap.get("warnMessage");
								} else if (warnMessage.indexOf((String) rMap.get("warnMessage")) < 0) {
									warnMessage += ("<br>" + rMap.get("warnMessage"));
								}
							}
							
			        		insured.setDiscountPrice(String.valueOf(rMap.get("retCountPrem")));
			        		insured.setRecognizeePrem(String.valueOf(rMap.get("retPrem")));
			        		insured.setRecognizeeTotalPrem(String.valueOf(rMap.get("productPrem")));
			        		insuredPremMap.put(insuredSex+"-"+realSecAge, rMap);
			        	}
			        	insuredPrem = insuredPrem + Double.parseDouble(insured.getDiscountPrice());
			        	disPrem = disPrem + Double.parseDouble(insured.getRecognizeePrem());
			        	proTolPrem = proTolPrem + Double.parseDouble(insured.getRecognizeeTotalPrem());
			        }else{
			        	insured.setRecognizeePrem(String.valueOf("0"));
		        		insured.setRecognizeeTotalPrem(String.valueOf("0"));
		        		insured.setDiscountPrice("0");
			        }
			      if(valadateFlag&&insured!=null&&!"".equals(insured)){
			    	  //保费试算
			    	  insuredList.add(insured);
			      }else if((!valadateFlag)&&insured!=null&&!"".equals(insured)){
			    	  //错误信息存储
			    	  if(errorMsg!=null && !"".equals(errorMsg)){
			  	    	errorMsg ="<div id='recognizee_"+insured_index+"' >被保人"+insured.getRecognizeeName()+"的录入项有错误 :"+errorMsg+"</div>";
			  	    	gErrorMsg = gErrorMsg+errorMsg;
			  	      }
			    	  logger.error("被保人错误信息：{};错误所在行：{}", errorMsg, rowIndex);
			    	  insuredErrorList.add(insured);
			    	  insuredList.add(insured);
			      }
			    } 
			    insured_index = insured_index+1;
			  }
		} catch (Exception e) {
			  logger.error(e.getMessage(), e);
		}
		if(insuredErrorList!=null && insuredErrorList.size()>=1){
			mMap.put("state", "1");
			mMap.put("errorMsg", gErrorMsg); 
		}else{
			mMap.put("state", "0");
			mMap.put("successMsg", "被保人信息导入成功!");
		} 
		mMap.put("warnMessage", warnMessage);
		mMap.put("insuredPrem", new BigDecimal(insuredPrem).setScale(2, BigDecimal.ROUND_HALF_UP));
		mMap.put("proTolPrem", new BigDecimal(proTolPrem).setScale(2, BigDecimal.ROUND_HALF_UP));
		mMap.put("disPrem", new BigDecimal(disPrem).setScale(2, BigDecimal.ROUND_HALF_UP));
		mMap.put("insuredList", insuredList);
		return mMap;
	} 
	
	private Map<String,Object> returnError(String state, String errorMsg) {
		Map<String,Object> mMap = new HashMap<String,Object>();
		mMap.put("state", state);
		mMap.put("errorMsg", errorMsg);
	    return mMap;
	}
	/**
	 * 得到excel列值
	 * @param cell
	 * @return
	 */
	@Override
	public Object getCellString(Cell cell) {
		  // TODO Auto-generated method stub
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  Object result = null;
		  if(cell != null){
		    //单元格类型：Numeric:0,String:1,Formula:2,Blank:3,Boolean:4,Error:5
		    int cellType = cell.getCellType();
		    switch (cellType) {
		    case Cell.CELL_TYPE_STRING:
		      result = cell.getRichStringCellValue().getString();
		      break;
		    case Cell.CELL_TYPE_NUMERIC:
		    	if(DateUtil.isCellDateFormatted(cell)){
			    	  result = sdf.format(cell.getDateCellValue());
		    	}else{
		    		 result = cell.getNumericCellValue();
		    	}
		      break;
		    case Cell.CELL_TYPE_FORMULA:
		      
		      cell.getCellFormula();
		      try{
		    	  if(DateUtil.isCellDateFormatted(cell)){
			    	  result = sdf.format(cell.getDateCellValue());
			      }else{
			    	  result = cell.getStringCellValue();
			      }
		      }catch(Exception e){
		    	  result = cell.getStringCellValue();
		      }
		      break;
		    case Cell.CELL_TYPE_BOOLEAN:
		      result = cell.getBooleanCellValue();
		      break;
		    case Cell.CELL_TYPE_BLANK:
		      result = null;
		      break;
		    case Cell.CELL_TYPE_ERROR:
		      result = null;
		      break;
		    default:
		      break;
		    }
		  }
		  return result;
		}

	 @Override
	 public boolean validateAgeNew( String BirthdayId , String sectionage) {
			try {
				Date start, end = null;
				boolean tFlag = false;
				SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
				// 多个断段间
				if(sectionage==null || "".equals(sectionage)){
					tFlag = true;
					return tFlag;
				}
				if (sectionage.indexOf("|") != -1) {
					String[] op = sectionage.split("\\|");
					for (int i = 0; i < op.length; i++) {
						if (op[i].indexOf("-") != -1) {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("D", "")), "D", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("D", "")) - 1, "D", new Date());
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("D", "")), "D", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", new Date());
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("D", "")), "D", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", new Date());
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("M", "")), "M", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", new Date());
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("M", "")), "M", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", new Date());
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("Y", "")), "Y", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", new Date());
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							}
						} else {

						}
					}
				} else {
					String[] op = sectionage.split("\\|");
					for (int i = 0; i < op.length; i++) {
						if (op[i].indexOf("-") != -1) {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("D", "")), "D", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("D", "")) - 1, "D", new Date());
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("D", "")), "D", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", new Date());
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("D", "")), "D", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", new Date());
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("M", "")), "M", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", new Date());
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("M", "")), "M", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", new Date());
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("Y", "")), "Y", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", new Date());
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							}
						} else {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("D", "")), "D", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("D", "")) - 1, "D", new Date());
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("D", "")), "D", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", new Date());
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("D", "")), "D", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", new Date());
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("M", "")), "M", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", new Date());
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("M", "")), "M", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", new Date());
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[0].replaceAll("Y", "")), "Y", new Date());
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(new Date(), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", new Date());
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							}
						}
					}

				}

				return tFlag;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			}
		}
	 @Override
	 public boolean validateAgeNewBaseOnEffectiveDate( String BirthdayId , String sectionage,Date effectiveDate) {
			try {
				Date start, end = null;
				boolean tFlag = false;
				SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
				// 多个断段间
				if(sectionage==null || "".equals(sectionage)){
					tFlag = true;
					return tFlag;
				}
				if (sectionage.indexOf("|") != -1) {
					String[] op = sectionage.split("\\|");
					for (int i = 0; i < op.length; i++) {
						if (op[i].indexOf("-") != -1) {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("D", "")), "D", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("D", "")) - 1, "D", effectiveDate);
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("D", "")), "D", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", effectiveDate);
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("D", "")), "D", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", effectiveDate);
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("M", "")), "M", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", effectiveDate);
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("M", "")), "M", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y",effectiveDate);
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("Y", "")), "Y", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", effectiveDate);
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							}
						} else {

						}
					}
				} else {
					String[] op = sectionage.split("\\|");
					for (int i = 0; i < op.length; i++) {
						if (op[i].indexOf("-") != -1) {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("D", "")), "D", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("D", "")) - 1, "D", effectiveDate);
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("D", "")), "D", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M",effectiveDate);
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("D", "")), "D", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", effectiveDate);
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("M", "")), "M", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", effectiveDate);
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("M", "")), "M", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", effectiveDate);
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("Y", "")), "Y", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", effectiveDate);
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							}
						} else {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("D", "")), "D", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("D", "")) - 1, "D", effectiveDate);
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("D", "")), "D", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", effectiveDate);
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("D", "")), "D", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", effectiveDate);
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("M", "")), "M", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", effectiveDate);
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("M", "")), "M", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", effectiveDate);
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[0].replaceAll("Y", "")), "Y", effectiveDate);
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(effectiveDate, -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", effectiveDate);
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							}
						}
					}

				}

				return tFlag;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			}
		}
		
		public static boolean compare_date(String startdate, String enddate,String birthday){
			try {
				SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
				Date start = sdf_1.parse(startdate);
				Date end = sdf_1.parse(enddate);
				Date Dbirthday = sdf_1.parse(birthday);
				if(Dbirthday.before(start) && Dbirthday.after(end)){
					return true;
				}else{
					return false;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			}
		}
	  
	  private boolean limitAge(String mBirthDay, String mLimitAge)
	  {
	    Date dt1 = new Date();
	    Date dt2 = new Date(mBirthDay.replace('-', '/'));
	    long i = (dt1.getTime() - dt2.getTime()) / 86400000L;
	    int age = (int)(i / 365L);

	    String minAgeTemp = mLimitAge.split("-")[0];
	    int minAge = Integer.parseInt(minAgeTemp.substring(0, minAgeTemp.length() - 1));
	    String maxAgeTemp = mLimitAge.split("-")[1];
	    int maxAge = Integer.parseInt(maxAgeTemp.substring(0, maxAgeTemp.length() - 1));

	    return (age >= minAge) && (age <= maxAge);
	  }
	  /**
		 * 判断是否为身份证，精确校验
	  * **/
	  @Override
	  public boolean isIdentityCard(String idno) {
		if (StringUtil.isEmpty(idno)) {
			return false;
		}
		OrderCheckIDUtil ocu = new OrderCheckIDUtil();
		try {
			if(ocu.IDCardValidate(idno)!=null && !"".equals(ocu.IDCardValidate(idno))){
				return false;
			}
			if (idno.endsWith("x")) {
			    return false;
            }
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		
		return true;
	  }
	  /**
	   * 职业校验
	   * @param occupId
	   * @param productId
	   * @return
	   */
	  private boolean validateOccupNew(String occupId, String productId) {
			try {
				String sql = "select grade from occupation where id='" + occupId + "'";
				GetDBdata db = new GetDBdata();
				String grade = db.getOneValue(sql);
				if (StringUtil.isEmpty(grade)) {
					logger.warn("职业编码为空无验证");
					return false;
				} else {
					int start, end = 0;
					String sql_occup = "select occup from sdproduct where productid=?";
					String[] sql_occupTemp = {productId};
					String occup = db.getOneValue(sql_occup,sql_occupTemp);
					// 没有限制职业
					if (StringUtil.isEmpty(occup)) {
						return true;
					} else {
						if (occup.indexOf("-") != -1) {
							String[] op = occup.split("-");
							start = Integer.parseInt(op[0]);
							end = Integer.parseInt(op[1]);
						} else {
							start = end = Integer.parseInt(occup);
						}
					}
					int g = Integer.parseInt(grade);
					Object[] argArr = {g, start, end};
					logger.info("职业校验比较：选择的职业等级{} 规定的等级：{} -- {}", argArr);
					if (g >= start && g <= end) {
						return true;

					} else {
						return false;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return false;
			}
		}
	 /**
	  * 保费试算
	  * @param cMap 保费试算参数
	  * @return
	  */
	 @SuppressWarnings({ "unchecked", "finally" })
	 @Override
	public Map<String,Object> calPrem(Map<String,Object> cMap, String memberId){
			// String [] factorValue =new String [1];
		    SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
		    Map<String,Object> mMap = new HashMap<String,Object>();
			String[] baseInformations = (String[])cMap.get("baseInformations");//产品基本信息
			List<OrderRiskAppFactor> riskAppFactior = (List<OrderRiskAppFactor>)cMap.get("riskAppFactior");//产品投保要素
			List<OrderDutyFactor> dutyFactor = (List<OrderDutyFactor>)cMap.get("dutyFactor");//产品责任信息
			SDInformation sdi = (SDInformation)cMap.get("sdinformation");//订单项信息
			String insureJson = (String)cMap.get("insureJson");//投保要素信息
			String dutyJson = (String)cMap.get("dutyJson");//责任信息
			String productId = (String)cMap.get("productId");//产品ID
			String insuredBirthDay = (String)cMap.get("insuredBirthDay");//被保人生日
			String insuredSex = (String)cMap.get("insuredSex");//被保人生日
			String channelsn = (String)cMap.get("channelsn");//渠道：wap、wj
			String complicatedFlag = (String)cMap.get("complicatedFlag");//复杂产品标识
			String dutyTempSerials = (String)cMap.get("dutyTempSerials");
            String effective = "";
			String productID = null;
			String retCountPrem  ="";
			String productPrem = "";
			for (int i = 0; riskAppFactior != null && i < riskAppFactior.size(); i++) {
				OrderRiskAppFactor oraf = riskAppFactior.get(i);
				productID = oraf.getProductCode();
			}

			String productID_ = null;
			for (int i = 0; dutyFactor != null && i < dutyFactor.size(); i++) {
				OrderDutyFactor odf = dutyFactor.get(i);
				productID_ = odf.getProductCode();
			}

			if (StringUtil.isNotEmpty(productID) && StringUtil.isNotEmpty(productID_)) {
				if (!productID.equals(productID_)) { 
				}
			}
			String pID = null;
			if (StringUtil.isNotEmpty(productID)) {
				pID = productID;
			}
			if (StringUtil.isNotEmpty(productID_)) {
				pID = productID_;
			}
			if (StringUtil.isEmpty(channelsn)) {
				channelsn = "wj";
			}
			
			List<OrderDutyFactor> dutyFactorLast = new ArrayList<OrderDutyFactor>();
			try {
				insureJson = java.net.URLDecoder.decode(insureJson, "utf-8");

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			JSONObject insureJsonJsonArray = JSONObject.fromObject(insureJson);

			Double totlePrem = 0.0;// 总保费
			Double countPrice = 0.0;// 折扣后保费 
			Double DiscountRate = 0.0;// 折扣率
			// 返回前台数据
			Map<String, Object> price = new HashMap<String, Object>();
			List<String> returnList = new ArrayList<String>();

			for (int i = 0; i < riskAppFactior.size(); i++) {
				// OrderRiskAppFactor orderRiskAppFactor =
				// riskAppFactior.get(i);
				if ("TextAge".equals(riskAppFactior.get(i).getFactorType())) {
					String textage = insuredBirthDay;
					String factorValueTemp = insuredBirthDay;
					if ("".equals(factorValueTemp) || factorValueTemp == null) {
						FEMRiskFactorList femr = riskAppFactior.get(i).getFactorValue().get(0);
						List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
						FEMRiskFactorList riskFactor = new FEMRiskFactorList();
						if ("".equals(textage)) {
							if (femr != null && femr.getFactorValue() != null) {
								riskFactor.setFactorValue(getBrithdayByFactor(effective ,femr.getFactorValue()));
							} else {
								riskFactor.setFactorValue("1991-01-01");
							}
						} else {
							riskFactor.setFactorValue(factorValueTemp.toString());
						}
						riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
						riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
						riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
						factorValue.add(riskFactor);
						riskAppFactior.get(i).setFactorValue(factorValue);
					} else {
						riskAppFactior.get(i).getFactorValue().get(0);
						List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
						FEMRiskFactorList riskFactor = new FEMRiskFactorList();
						if (factorValueTemp != null)
							riskFactor.setFactorValue(factorValueTemp.toString());
						riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
						riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
						riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
						factorValue.add(riskFactor);
						riskAppFactior.get(i).setFactorValue(factorValue);
					}
				} else if ("Sex".equals(riskAppFactior.get(i).getFactorType())) {
					String factorValueTemp = insuredSex;
					riskAppFactior.get(i).getFactorValue().get(0);
					List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
					FEMRiskFactorList riskFactor = new FEMRiskFactorList();
					if (factorValueTemp != null)
					riskFactor.setFactorValue(factorValueTemp.toString());
					riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
					riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
					riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
					factorValue.add(riskFactor);
					riskAppFactior.get(i).setFactorValue(factorValue);
				}else {
					Object factorValueTemp = insureJsonJsonArray.get(riskAppFactior.get(i).getFactorType());
					riskAppFactior.get(i).getFactorValue().get(0);
					List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
					FEMRiskFactorList riskFactor = new FEMRiskFactorList();
					if (factorValueTemp != null)
						riskFactor.setFactorValue(factorValueTemp.toString());
					riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
					riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
					riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
					factorValue.add(riskFactor);
					riskAppFactior.get(i).setFactorValue(factorValue);
				}
			}
			// 复杂产品处理
			if ("Y".equals(complicatedFlag)) {
				Map<String, String> dutyMap = new HashMap<String, String>();
				if (StringUtil.isNotEmpty(dutyTempSerials)) {
					String sql = "select DutySn,Amnt from sdinformationdutytemp where DutySerials = ?";
					DataTable dt = new QueryBuilder(sql, dutyTempSerials).executeDataTable();
					if (dt != null && dt.getRowCount() > 0) {
						int rowCount = dt.getRowCount();
						for (int i = 0; i < rowCount; i++) {
							dutyMap.put(dt.getString(i, 0), dt.getString(i, 1));
						}
					}
					for (OrderDutyFactor orderDutyFactor : dutyFactor) {
						String dutyValueTemp = dutyMap.get(orderDutyFactor.getDutyCode());
						List<FEMDutyAmntPremList> fdAmntPremList = new ArrayList<FEMDutyAmntPremList>();
						if (StringUtil.isNotEmpty(dutyValueTemp)) { 
						    for(FEMDutyAmntPremList femd:orderDutyFactor.getFdAmntPremList()){
						    	if(dutyValueTemp.equals(femd.getBackUp1())){
						    		fdAmntPremList.add(femd);
						    	}
						    } 
							orderDutyFactor.setFdAmntPremList(fdAmntPremList);
							dutyFactorLast.add(orderDutyFactor);
						}
					}
				}
			} else {
				try {
					dutyJson = java.net.URLDecoder.decode(dutyJson, "utf-8");
	
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				JSONObject dutyJsonJsonArray = JSONObject.fromObject(dutyJson);
	
				for (OrderDutyFactor orderDutyFactor : dutyFactor) {
					String dutyValueTemp = dutyJsonJsonArray.get(orderDutyFactor.getDutyCode()).toString();
					if (orderDutyFactor.getFdAmntPremList() != null) {
						//orderDutyFactor.getFdAmntPremList().clear();
					}
					List<FEMDutyAmntPremList> fdAmntPremList = new ArrayList<FEMDutyAmntPremList>();
					if ("nvalue".equals(dutyValueTemp)) {
						// System.out.println("dutyValueTemp=" + dutyValueTemp);
					} else {
						for(FEMDutyAmntPremList femd:orderDutyFactor.getFdAmntPremList()){
					    	if(dutyValueTemp.equals(femd.getBackUp1())){
					    		fdAmntPremList.add(femd);
					    	}
					    } 
						orderDutyFactor.setFdAmntPremList(fdAmntPremList);
						dutyFactorLast.add(orderDutyFactor);
					}
				}
			}
			if (dutyFactorLast.size() == 0) {
				dutyFactorLast = null;
			}
			Map<String, Object> paramter = new HashMap<String, Object>();
			paramter.put("baseInformation", baseInformations);
			paramter.put("riskAppFactor", riskAppFactior);
			paramter.put("dutyFactor", dutyFactorLast);
			paramter.put("complicatedFlag", complicatedFlag);
			if(sdi.getStartDate()!=null){
				/**
				 * 保费试算根据保险公司判断是起保日期还是当前日期
				 * heyang
				 * 2013-7-19
				 */
				String startdate = sdf_1.format(sdi.getStartDate());
				if(productId.startsWith("1087") || productId.startsWith("1001") || productId.startsWith("1004") || productId.startsWith("2043")){
					 startdate = PubFun.getCurrentDate();
				}
				paramter.put("effective", startdate);
			}
			try{
				Map<String, Object> mapPrem = sdorderService.getProductPremDutyAmounts(paramter);
				
				if (mapPrem.containsKey("errMessage") && StringUtil.isNotEmpty((String)mapPrem.get("errMessage"))) {
					mMap.put("errMessage", mapPrem.get("errMessage"));
					mMap.put("retCountPrem", "0");
					mMap.put("productPrem", "0");
					mMap.put("retPrem", "0");
					return mMap;
				}
				
				if (mapPrem.containsKey("warnMessage") && StringUtil.isNotEmpty((String)mapPrem.get("warnMessage"))) {
					mMap.put("warnMessage", mapPrem.get("warnMessage"));
				}
				retCountPrem = String.valueOf(mapPrem.get("countPrice").toString());//折后保费
			    productPrem = String.valueOf(mapPrem.get("totlePrem").toString());//原保费

				totlePrem = totlePrem + Double.parseDouble(mapPrem.get("totlePrem").toString());// 总保费
				countPrice = countPrice + Double.parseDouble(mapPrem.get("countPrice").toString());// 折扣后保费
				//dutyAmounts = dutyAmounts + Double.parseDouble(mapPrem.get("dutyAmounts").toString());// 责任明细
				price.put("retDutyAmounts", mapPrem.get("dutyAmounts"));// 责任明细
				if (mapPrem.get("DiscountRate").toString() == null || "".equals(mapPrem.get("DiscountRate").toString())) {
					DiscountRate = 0.0;
				} else {
					DiscountRate = Double.parseDouble(mapPrem.get("DiscountRate").toString());// 折扣率
				}
			
				//return retCountPrem;
				mMap.put("productPrem", productPrem);
				mMap.put("retPrem", retCountPrem);// 折前保费
				mMap.put("retCountPrem", ActivityCalculate.ProductCalculate(productId,sdi.getOrderSn(),retCountPrem, channelsn, memberId));// 折扣后保费
				
			}catch(Exception e){
				logger.error(e.getMessage(), e);
				mMap.put("retCountPrem", "0");
				mMap.put("productPrem", "0");
				mMap.put("retPrem", "0");
			}finally{
				return mMap;
			}
		}
	 @Override
	 public String getSectionAge(String cProductId){
		 
		    String sql = "SELECT sectionage FROM sdproduct WHERE productid = ? ";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(cProductId);
			if(String.valueOf(qb.executeString())!=null && !"".equals(String.valueOf(qb.executeString()))){
				return String.valueOf(qb.executeString());
			}else{
				return "";
			}
		 
	 }
	 @Override
	 public String getRealSectionAge(String BirthdayId,String sectionage,String effective){
		    SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
			int start, end = 0;
			int days = 0;//生效日期与生日之间相差天数
			int months = 0;//生效日期与生日之间相差月数
	        int years = 0;//年龄
	        String tSectionAge = "";
			if(sectionage==null || "".equals(sectionage)){
				return "default";
			}
			if(StringUtil.isEmpty(BirthdayId)){
				return "-1";
			}
			try {
				days = OrderConfigNewServiceImpl.getDateSpace(sdf_1.parse(BirthdayId),sdf_1.parse(effective));
				months = OrderConfigNewServiceImpl.getMonthSpace(sdf_1.parse(BirthdayId), sdf_1.parse(effective));
		        years = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(sdf_1.parse(BirthdayId), sdf_1.parse(effective));
				
				// 多个断段间
				if (sectionage.indexOf("|") != -1) {
					String[] op = sectionage.split("\\|");
					for (int i = 0; i < op.length; i++) {
						if (op[i].indexOf("-") != -1) {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = Integer.parseInt(age[0].replaceAll("D", ""));
								end = Integer.parseInt(age[1].replaceAll("D", ""));
								if(days>=start && days<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = Integer.parseInt(age[0].replaceAll("D", ""));
								end = Integer.parseInt(age[1].replaceAll("M", ""));
								if(days>=start && months<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = Integer.parseInt(age[0].replaceAll("D", ""));
								end = Integer.parseInt(age[1].replaceAll("Y", ""));
								if(days>=start && years<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = Integer.parseInt(age[0].replaceAll("M", ""));
								end = Integer.parseInt(age[1].replaceAll("M", ""));
								if(months>=start && months<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = Integer.parseInt(age[0].replaceAll("M", ""));
								end = Integer.parseInt(age[1].replaceAll("Y", ""));
								if(months>=start && years<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = Integer.parseInt(age[0].replaceAll("Y", ""));
								end = Integer.parseInt(age[1].replaceAll("Y", ""));
								if(years>=start && years<=end){
									tSectionAge = op[i];
								}
							}
						}  
					}
				} else {
					String[] op = sectionage.split("\\|");
					for (int i = 0; i < op.length; i++) {
						if (op[i].indexOf("-") != -1) {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = Integer.parseInt(age[0].replaceAll("D", ""));
								end = Integer.parseInt(age[1].replaceAll("D", ""));
								if(days>=start && days<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = Integer.parseInt(age[0].replaceAll("D", ""));
								end = Integer.parseInt(age[1].replaceAll("M", ""));
								if(days>=start && months<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = Integer.parseInt(age[0].replaceAll("D", ""));
								end = Integer.parseInt(age[1].replaceAll("Y", ""));
								if(days>=start && years<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = Integer.parseInt(age[0].replaceAll("M", ""));
								end = Integer.parseInt(age[1].replaceAll("M", ""));
								if(months>=start && months<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = Integer.parseInt(age[0].replaceAll("M", ""));
								end = Integer.parseInt(age[1].replaceAll("Y", ""));
								if(months>=start && years<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = Integer.parseInt(age[0].replaceAll("Y", ""));
								end = Integer.parseInt(age[1].replaceAll("Y", ""));
								if(years>=start && years<=end){
									tSectionAge = op[i];
								}
							}
						} else {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = Integer.parseInt(age[0].replaceAll("D", ""));
								end = Integer.parseInt(age[1].replaceAll("D", ""));
								if(days>=start && days<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = Integer.parseInt(age[0].replaceAll("D", ""));
								end = Integer.parseInt(age[1].replaceAll("M", ""));
								if(days>=start && months<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = Integer.parseInt(age[0].replaceAll("D", ""));
								end = Integer.parseInt(age[1].replaceAll("Y", ""));
								if(days>=start && years<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = Integer.parseInt(age[0].replaceAll("M", ""));
								end = Integer.parseInt(age[1].replaceAll("M", ""));
								if(months>=start && months<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = Integer.parseInt(age[0].replaceAll("M", ""));
								end = Integer.parseInt(age[1].replaceAll("Y", ""));
								if(months>=start && years<=end){
									tSectionAge = op[i];
								}
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = Integer.parseInt(age[0].replaceAll("Y", ""));
								end = Integer.parseInt(age[1].replaceAll("Y", ""));
								if(years>=start && years<=end){
									tSectionAge = op[i];
								}
							}
						}
					}

			    }
		    } catch (Exception e) {
				logger.error(e.getMessage(), e);
		 }
		return tSectionAge;
	 }
	 /**
	  * 计算相差月数
	  * @param date1 出生日期
	  * @param date2 保单起保日期
	  * @return
	  * @throws ParseException
	  */
	 public static int getMonthSpace(Date date1, Date date2)throws ParseException {

			/*int result = 0;

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();

			c1.setTime(date1);
			c2.setTime(date2);

			result = c2.get(Calendar.DAY_OF_MONTH) - c1.get(Calendar.DAY_OF_MONTH);

			return result == 0 ? 1 : Math.abs(result);*/
		 return (int)Math.rint(Double.parseDouble(String.valueOf(getDateSpace(date1,date2)))/30);

		}
	    /**
	     * 计算相差天数
	     * @param date1 出生日期
	     * @param date2 生效日期
	     * @return
	     * @throws ParseException
	     */
		public static int getDateSpace(Date date1, Date date2)throws ParseException { 
			Calendar cal = Calendar.getInstance();     
		    cal.setTime(date1);     
			long time1 = cal.getTimeInMillis();                  
			cal.setTime(date2);     
			long time2 = cal.getTimeInMillis();          
			long between_days=(time2-time1)/(1000*3600*24);     
			     
			return Integer.parseInt(String.valueOf(between_days));       
		}
		/**
		  * 计算相差月数
		  * @param date1 出生日期
		  * @param date2 保单起保日期
		  * @return
		  * @throws ParseException
		  */
		 public static int getYearSpace(Date date1, Date date2)throws ParseException {

				/*int result = 0;

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();

				c1.setTime(date1);
				c2.setTime(date2);

				result = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

				return result == 0 ? 1 : Math.abs(result);*/
			 return (int)Math.rint(Double.parseDouble(String.valueOf(getDateSpace(date1,date2)))/365);

			}
		/**
		 * 特殊字符校验
		 * @param cell 校验名称
		 * @param temp 校验内容
		 * @return
		 */
		@Override
		public boolean checkCellValue(String cell,String temp){
		    String regEx="[`~!@#$%^&*+=|{}''\\[\\]<>/?~！@#￥%……&*+|{}【】‘：”“’？]";
	        Pattern    p    =    Pattern.compile(regEx);     
	        Matcher    m    =    p.matcher(temp);   
	        if(m.find()){
	            return false;
	        }
            return true;
		}
		/**
		 * 校验英文名
		 * @param cell 校验名称
		 * @param temp 校验内容
		 * @return
		 */
		@Override
		public String checkEnName(String cell,String temp){
			String result = "";
			Pattern p = Pattern.compile("[^[A-Za-z][A-Za-z\\s]*[A-Za-z]$]{2,20}");
		    Matcher m  = p.matcher(temp);   
	        if(!m.matches()){
	        	result = cell+"请正确输入英文名或拼音！";
	        }
            return result;
		}
		/**
		 * 手机号校验
		 * @param mobile
		 * @return
		 */
		@Override
		 public boolean isMobile(String mobile){
			//当前运营商号段分配
			//中国移动号段 1340-1348 135 136 137 138 139 150 151 152 157 158 159 187 188 147
			//中国联通号段 130 131 132 155 156 185 186 145
			//中国电信号段 133 1349 153 180 189
			String regular = "\\d{11}";
			Pattern pattern = Pattern.compile(regular);
			Matcher matcher = pattern.matcher(mobile);
			if(!matcher.matches()){
			   return false;
			}
			return true;
		 }
		 @Override
		 public String getExcelKeyValue(String productId){
			 String sql = "SELECT a.keyValue FROM productexceltemp a,producttempinfo b WHERE a.Id = b.ExcelTemplate AND b.ProductId=? ";
				QueryBuilder qb = new QueryBuilder(sql);
				qb.add(productId);
				if(String.valueOf(qb.executeString())!=null && !"".equals(String.valueOf(qb.executeString()))){
					return String.valueOf(qb.executeString());
				}else{
					return "";
				}
		 }
		 /**
		  * 对于购买多份的被保人--用于copy被保人信息
		  * @param oldInsured
		  * @return
		  */
		 public Map<String,Object> copySDInsured(SDInformationInsured oldInsured,SDInformationRiskType oldRiskType,int index){
			 
			 SDInformationInsured newInsured = new SDInformationInsured();
			 SDInformationRiskType newRiskType = new SDInformationRiskType();
			 Map<String,Object> insuredMap = new HashMap<String,Object>();

			 newInsured = copyInsuredInfo(oldInsured);
			 newInsured.setRecognizeeSn(com.sinosoft.cms.pub.PubFun.GetSDInsuredSn());
			 newInsured.setInsuredSn(oldInsured.getInsuredSn()+"_"+index);
			 newRiskType = copyRiskType(oldRiskType);
			 newRiskType.setRecognizeeSn(newInsured.getRecognizeeSn());
			 
			 insuredMap.put("insured", newInsured);
			 insuredMap.put("risktype", newRiskType);
			 return insuredMap;
		 }
		 public SDInformationInsured copyInsuredInfo(SDInformationInsured oldInsured){
			 SDInformationInsured newInsured = new SDInformationInsured();
			 
			 newInsured.setOrderSn(oldInsured.getOrderSn());
			 newInsured.setInformationSn(oldInsured.getInformationSn());
			 newInsured.setRecognizeeSn(oldInsured.getRecognizeeSn());
			 newInsured.setRecognizeeAppntRelation(oldInsured.getRecognizeeAppntRelation());
			 newInsured.setRecognizeeAppntRelationName(oldInsured.getRecognizeeAppntRelationName());
			 newInsured.setRecognizeeName(oldInsured.getRecognizeeName());
			 newInsured.setRecognizeeEnName(oldInsured.getRecognizeeEnName());
			 newInsured.setRecognizeeLashName(oldInsured.getRecognizeeLashName());
			 newInsured.setRecognizeeFirstName(oldInsured.getRecognizeeFirstName());
			 newInsured.setRecognizeeIdentityType(oldInsured.getRecognizeeIdentityType());
			 newInsured.setRecognizeeIdentityTypeName(oldInsured.getRecognizeeIdentityTypeName());
			 newInsured.setRecognizeeIdentityId(oldInsured.getRecognizeeIdentityId());
			 newInsured.setRecognizeeSex(oldInsured.getRecognizeeSex());
			 newInsured.setRecognizeeSexName(oldInsured.getRecognizeeSexName());
			 newInsured.setRecognizeeBirthday(oldInsured.getRecognizeeBirthday());
			 newInsured.setRecognizeeMobile(oldInsured.getRecognizeeMobile());
			 newInsured.setRecognizeeTel(oldInsured.getRecognizeeTel());
			 newInsured.setRecognizeeMail(oldInsured.getRecognizeeMail());
			 newInsured.setRecognizeeOccupation1(oldInsured.getRecognizeeOccupation1());
			 newInsured.setRecognizeeOccupation2(oldInsured.getRecognizeeOccupation2());
			 newInsured.setRecognizeeOccupation3(oldInsured.getRecognizeeOccupation3());
			 newInsured.setRecognizeeArea1(oldInsured.getRecognizeeArea1());
			 newInsured.setRecognizeeArea2(oldInsured.getRecognizeeArea2());
			 newInsured.setRecognizeeAddress(oldInsured.getRecognizeeAddress());
			 newInsured.setRecognizeeZipCode(oldInsured.getRecognizeeZipCode());
			 newInsured.setRecognizeeIsMarry(oldInsured.getRecognizeeIsMarry());
			 newInsured.setRecognizeeAge(oldInsured.getRecognizeeAge());
			 newInsured.setSchoolOrCompany(oldInsured.getSchoolOrCompany());
			 newInsured.setOutGoingParpose(oldInsured.getOutGoingParpose());
			 newInsured.setFlightNo(oldInsured.getFlightNo());
			 newInsured.setFlightTime(oldInsured.getFlightTime());
			 newInsured.setFlightLocation(oldInsured.getFlightLocation());
			 newInsured.setDriverSchoolName(oldInsured.getDriverSchoolName());
			 newInsured.setDriverNo(oldInsured.getDriverNo());
			 newInsured.setDestinationCountry(oldInsured.getDestinationCountry());
			 newInsured.setDestinationCountryText(oldInsured.getDestinationCountryText());
			 newInsured.setIsSelf(oldInsured.getIsSelf());
			 newInsured.setHeight(oldInsured.getHeight());
			 newInsured.setWeight(oldInsured.getWeight());
			 newInsured.setOverseasOccupation(oldInsured.getOverseasOccupation());
			 newInsured.setTravelType(oldInsured.getTravelType());
			 newInsured.setTravelMode(oldInsured.getTravelMode());
			 newInsured.setNationality(oldInsured.getNationality());
			 newInsured.setHaveBuy(oldInsured.getHaveBuy());
			 newInsured.setUwCheckFlag(oldInsured.getUwCheckFlag());
			 newInsured.setRemark(oldInsured.getRemark());
			 newInsured.setInsuredSn(oldInsured.getInsuredSn());
			 newInsured.setRecognizeePrem(oldInsured.getRecognizeePrem());
			 newInsured.setRecognizeeOperate(oldInsured.getRecognizeeOperate());
			 newInsured.setMulInsuredFlag(oldInsured.getMulInsuredFlag());
			 newInsured.setRecognizeeTotalPrem(oldInsured.getRecognizeeTotalPrem());
			 newInsured.setRecognizeeArea3(oldInsured.getRecognizeeArea3());
			 newInsured.setRecognizeeStartID(oldInsured.getRecognizeeStartID());
			 newInsured.setRecognizeeEndID(oldInsured.getRecognizeeEndID());
			 newInsured.setRecognizeeMul(oldInsured.getRecognizeeMul());
			 newInsured.setSdinformation(oldInsured.getSdinformation());
			 newInsured.setRecognizeeKey(oldInsured.getRecognizeeKey());
			 newInsured.setDiscountPrice(oldInsured.getDiscountPrice());
			 return newInsured;
			 
		 } 
		 public SDInformationRiskType copyRiskType(SDInformationRiskType oldRiskType){
			 
			 SDInformationRiskType newRiskType = new SDInformationRiskType();
			 newRiskType.setOrderSn(oldRiskType.getOrderSn());
			 newRiskType.setInformationSn(oldRiskType.getInformationSn());
			 newRiskType.setRecognizeeSn(oldRiskType.getRecognizeeSn());
			 newRiskType.setApplicantSn(oldRiskType.getApplicantSn());
			 newRiskType.setPolicyNo(oldRiskType.getPolicyNo());
			 newRiskType.setApplyPolicyNo(oldRiskType.getApplyPolicyNo());
			 newRiskType.setRiskCode(oldRiskType.getRiskCode());
			 newRiskType.setRiskName(oldRiskType.getRiskName());
			 newRiskType.setAmnt(oldRiskType.getAmnt());
			 newRiskType.setTimePrem(oldRiskType.getTimePrem());
			 newRiskType.setMult(oldRiskType.getMult());
			 newRiskType.setSvaliDate(oldRiskType.getSvaliDate());
			 newRiskType.setEvaliDate(oldRiskType.getEvaliDate());
			 newRiskType.setPeriodFlag(oldRiskType.getPeriodFlag());
			 newRiskType.setPeriod(oldRiskType.getPeriod());
			 newRiskType.setElectronicCout(oldRiskType.getElectronicCout());
			 newRiskType.setElectronicPath(oldRiskType.getElectronicPath());
			 newRiskType.setInsurerFlag(oldRiskType.getInsurerFlag());
			 newRiskType.setInsureMsg(oldRiskType.getInsureMsg());
			 newRiskType.setInsureDate(oldRiskType.getInsureDate());
			 newRiskType.setBalanceStatus(oldRiskType.getBalanceStatus());
			 newRiskType.setBalanceFlag(oldRiskType.getBalanceFlag());
			 newRiskType.setBalanceMsg(oldRiskType.getBalanceMsg());
			 newRiskType.setBalanceDate(oldRiskType.getBalanceDate());
			 newRiskType.setAppStatus(oldRiskType.getAppStatus());
			 newRiskType.setNoticeNo(oldRiskType.getNoticeNo());
			 newRiskType.setProductPrice(oldRiskType.getProductPrice());
			 newRiskType.setValidateCode(oldRiskType.getValidateCode());
			 newRiskType.setInsuranceTransCode(oldRiskType.getInsuranceTransCode());
			 newRiskType.setInsuranceBankCode(oldRiskType.getInsuranceBankCode());
			 newRiskType.setInsuranceBankSeriNO(oldRiskType.getInsuranceBankSeriNO());
			 newRiskType.setInsuranceBRNO(oldRiskType.getInsuranceBRNO());
			 newRiskType.setInsuranceTELLERNO(oldRiskType.getInsuranceTELLERNO());
			 newRiskType.setSdorder(oldRiskType.getSdorder());
			 return newRiskType;
		 }
		 /**
		  * 判断页面是否需要显示“购买份数”录入项
		  * @param sdinf 订单详细表
		  * @return
		  */
			public boolean buyCountFlag(SDInformation sdinf){
				
				String sql = " SELECT 1 FROM HealthyInfo WHERE productId=?  "+
			                 " UNION "+
						     " SELECT 1 FROM zdconfig WHERE TYPE = 'questionPaper' AND VALUE = ? ";
				QueryBuilder qb = new QueryBuilder(sql);
				qb.add(sdinf.getProductId());
				qb.add(sdinf.getProductId());
				if (StringUtil.isNotEmpty(qb.executeString())||"11".equals(sdinf.getRiskType())) {
					return false;
				}
				return true;
			}
			
			//此方法咱不要删除--校验保险期限时使用
			/**
			 * 根据保险起期、止期、保障期限判断保单是否合法
			 * @param sdinf
			 * @return
			 */
            public boolean checkEnsure(SDInformation sdinf){
            	
            	SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
				Date startDate = sdinf.getStartDate();
				Date endDate = sdinf.getEndDate();
				String ensure = sdinf.getEnsure();
				String ensureLimit = sdinf.getEnsureLimitType();
				String endValue = "";
				int key = 0;
				if(ensure.indexOf("-")!=-1){
					endValue = ensure.split("-")[1].substring(0,ensure.split("-")[1].length()-1);
				}else{
					endValue = ensure.substring(0,ensure.split("-")[0].length()-1);
				}
				try{
					if("Y".equals(ensureLimit)){
						key = OrderConfigNewServiceImpl.getYearSpace(sdf_1.parse(sdf_1.format(startDate)), sdf_1.parse(sdf_1.format(endDate)));
					}else if("M".equals(ensureLimit)){
						key = OrderConfigNewServiceImpl.getMonthSpace(sdf_1.parse(sdf_1.format(startDate)), sdf_1.parse(sdf_1.format(endDate)));
					}else if("D".equals(ensureLimit)){
						key = OrderConfigNewServiceImpl.getDateSpace(sdf_1.parse(sdf_1.format(startDate)), sdf_1.parse(sdf_1.format(endDate)))+1;
					}
				}catch(Exception e){
					logger.error(e.getMessage(), e);
				}
				if(key!=Integer.parseInt(endValue)){
					return false;
				}
            	return true;
			}
            public static void main(String[] args) {
            	String regular = "^(13[0-9]{9}|15[5-9][0-9]{8}|15[0-3][0-9]{8}|18[0-9][0-9]{8}|147[0-9]{8}|17[6-8][0-9]{8})$";
        		Pattern pattern = Pattern.compile(regular);
        		String result = "11";
        		Matcher matcher = pattern.matcher("18125456233");
        		if(!matcher.matches()){
        			result = "请输入正确的手机号！";
        		}
//				SDInformation sd = new SDInformation();
//				SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
//				
//				try {
//					sd.setStartDate(sdf_1.parse(("2013-01-01")));
//					sd.setEndDate(sdf_1.parse(("2014-12-31")));
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				sd.setEnsure("2Y");
//				sd.setEnsureLimitType("Y");
//				OrderConfigNewServiceImpl a = new OrderConfigNewServiceImpl();
//				System.out.println(a.checkEnsure(sd));
			}
        	public List<SDInsuredHealth> getInsuredHealthy(HealthyInfoService healthyInfoService, SDInsuredHealthService sdinsuredHealthService, SDInformation sdinf) {
        		String productId = sdinf.getProductId();
        		String comCode = sdinf.getInsuranceCompany();
        		List<SDInsuredHealth> sdinsuredHealthList = new ArrayList<SDInsuredHealth>();
        		List<HealthyInfo> healthList = healthyInfoService.findByComAndProduct(productId, comCode);
        		sdinsuredHealthList = sdinsuredHealthService.createShowInformation(healthList);
        		return sdinsuredHealthList;
        	}
			@Override
			public String getInsuredSn(String orderSn, int index,
					String comCode) {
				String insuredSn = orderSn+"_"+index;
				if("1004".equals(comCode)){
					insuredSn = orderSn+"_"+index+"_"+System.currentTimeMillis();
				}
				return insuredSn;
			}
	/**
	 * 手机号校验
	 * @param mobile
	 * @return
	 */
	private String checkMobile(String mobile){
		//当前运营商号段分配
		//中国移动号段 1340-1348 135 136 137 138 139 150 151 152 157 158 159 187 188 147
		//中国联通号段 130 131 132 155 156 185 186 145
		//中国电信号段 133 1349 153 180 189
		// String regular = "\\d{11}";
		//String regular = "^(13[0-9]{9}|15[5-9][0-9]{8}|15[0-3][0-9]{8}|18[0-9][0-9]{8}|147[0-9]{8}|17[6-8][0-9]{8})$";
		String regular = "^1(3|4|5|7|8)\\d{9}$";
		Pattern pattern = Pattern.compile(regular);
		String result = "";
		Matcher matcher = pattern.matcher(mobile);
		if(!matcher.matches()){
			result = "请输入正确的手机号！";
		}
		return result;
	}

	/**
	 * 特殊字符校验
	 * @param cell 校验名称
	 * @param temp 校验内容
	 * @return
	 */
	private String checkValue(String cell,String temp){
		String result = "";
		//String regex = "script|iframe|^[^\"',，。!@#$%&*()0-9]*$";
		//String regEx="[`~!@#$%^&*()+=|{}';',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		String regEx="[`~!@#$%^&*+=|{}''\\[\\]<>/?~！@#￥%……&*+|{}【】‘：”“’？]";
		Pattern    p    =    Pattern.compile(regEx);
		Matcher    m    =    p.matcher(temp);
		if(m.find()){
			result = cell+"含有非法字符！";
		}
		return result;
	}
	/**
	 * 姓名规则校验 <br/>
	 * @param temp
	 * @return
	 */
	private String checkName(String temp){
		String result = "";
		String firststr = temp.substring(0,1);
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");//第一位是否是中文
		Matcher m = p.matcher(firststr);
		if (m.find()) {
			//是中文名字
			if (temp.length()>=2 && temp.length() <= 20) {
				if (!temp.matches("^[\u4E00-\u9FA5]+(\\.)?[\u4E00-\u9FA5]+$")) {
					result = "被保人姓名不正确，中文姓名不能存在特殊字符。针对少数民族可以在中间添加英文点（.），仅限于名字中间!;";
				}
			}else{
				result = "请正确填写2-20位中文姓名！";
			}
		}else{
			//英文名字
			if (temp.length()>= 2 && temp.length() <=20) {
				if (!temp.matches("^[A-Za-z][A-Za-z\\s]*[A-Za-z]$")) {
					result = "被保人姓名不正确，英文姓名不能存在其他特殊字符，中间可以有空格!;";
				}
			}else{
				result = "请正确填写2-20位英文姓名！";
			}

		}
		return result;
	}
	@Override
	public String getChannelSn(HttpServletRequest request,String cChannelSn) {

		
		Cookie ck;
		String channelSn = cChannelSn;
		if(StringUtil.isEmpty(channelSn)){
			channelSn = "wj";
		}
		ck = CookieUtil.getCookieByName(request, "cpsUserId");
		if (!StringUtil.isEmpty(ck)) {
			if (!StringUtil.isEmpty(ck.getValue())) {
				String cpsUserId = ck.getValue();
				ck = CookieUtil.getCookieByName(request, "cpsUserSource");
				if (!StringUtil.isEmpty(ck)) {
					String cpsUserource = ck.getValue();
					String partners_uid = "";
					if(cpsUserource.indexOf("_")!=-1){
						String[] arr = cpsUserource.split("_");
						if ("emar_cps".equals(cpsUserource)) {
							channelSn = "cps_01";
						}else if ("59miao".equals(cpsUserource)) {
							channelSn = "cps_04";
						}else{
							channelSn = "cps_"+arr[1];  
						}
						 
						
					}
					//判断从cookie中来源渠道是否为空
					Cookie ckcha = CookieUtil.getCookieByName(request, "channelSn"); 
					if (!StringUtil.isEmpty(ckcha)) {
						if (!StringUtil.isEmpty(ckcha.getValue())) {
							channelSn = ckcha.getValue();
						}
					}
					if(StringUtil.isNotEmpty(CpsAction.isPartners(cpsUserId, "1"))){
						Cookie p_ck = CookieUtil.getCookieByName(request, "partners_uid");
						if (!StringUtil.isEmpty(p_ck)) {
							partners_uid = p_ck.getValue();
						}
						if(StringUtil.isNotEmpty(partners_uid)){
							channelSn = "cps_"+cpsUserId;
						}
					}
				}
			}
		}
		return channelSn;
	}
}
