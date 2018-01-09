package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.entity.InformationAppnt;
import cn.com.sinosoft.entity.InformationInsured;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.service.OrderConfigService;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.CalProductPrem;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.ProductPremResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class OrderConfigServiceImpl implements OrderConfigService{
	private static final Logger logger = LoggerFactory.getLogger(OrderConfigServiceImpl.class);

	public boolean validateAge(String BirthdayId, String productId,Date effective) {
		
		try {
			GetDBdata db = new GetDBdata();
			int start, end = 0;
			String sql_sectionage = "select sectionage from sdproduct where productid='" + productId + "'";
			String sectionage = db.getOneValue(sql_sectionage);
			ArrayList<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();
			Map<String, Integer> map = new HashMap<String, Integer>();
			// 没有限制年龄
			if (StringUtil.isEmpty(sectionage)) {
				return true;
			} else {
				// 多个断段间
				if (sectionage.indexOf("|") != -1) {
					String[] op = sectionage.split("\\|");
					for (int i = 0; i < op.length; i++) {
						map = new HashMap<String, Integer>();
						if (op[i].indexOf("-") != -1) {
							String[] age = op[i].split("-");
							map.put("start", Integer.parseInt(age[0].replace("Y", "")));
							map.put("end", Integer.parseInt(age[1].replace("Y", "")));
							list.add(map);
						}
					}
				} else {
					if (sectionage.indexOf("-") != -1) {
						String[] age = sectionage.split("-");
						map.put("start", Integer.parseInt(age[0].replace("Y", "")));
						map.put("end", Integer.parseInt(age[1].replace("Y", "")));
						list.add(map);
					} else {
						map.put("start", Integer.parseInt(sectionage.replace("Y", "")));
						map.put("end", Integer.parseInt(sectionage.replace("Y", "")));
						list.add(map);
					}
				}
			}
			int g = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(BirthdayId,effective);

			logger.info("年龄校验比较：选择的年龄{} 规定的年龄：{}", g, sectionage);
			boolean flag = false;
			for (Map<String, Integer> map1 : list) {
				start = map1.get("start");
				end = map1.get("end");
				if (g >= start && g <= end) {
					return true;
				} else {
					flag = false;
				}
			}
			return flag;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	public boolean validateOccup(String occupId, String productId) {
		try {
			String sql = "select grade from occupation where id='" + occupId + "'";
			GetDBdata db = new GetDBdata();
			String grade = db.getOneValue(sql);
			if (StringUtil.isEmpty(grade)) {
				logger.warn("职业编码为空无验证");
				return false;
			} else {
				int start, end = 0;
				String sql_occup = "select occup from sdproduct where productid='" + productId + "'";
				String occup = db.getOneValue(sql_occup);
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
	public Map<String,String>  CalPrice(String RiskCode,Map<String, String> map) {
		try {
			String riskcode = RiskCode;
			CalProductPrem[] calProductPrem = new CalProductPrem[1];
			calProductPrem[0] = new CalProductPrem();
			calProductPrem[0].setRiskCode(riskcode);
			com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList[] 
					fEMRiskFactorList = 
					new com.sinosoft.webservice.productPrem.ProductPremServiceStub.
					FEMRiskFactorList[map.size()];
			Iterator<String> itr = map.keySet().iterator();
			String key = "";
			int iCount = 0;
			while (itr.hasNext()) {
				key = itr.next();
				if ("RiskCode".equals(key)) {
					continue;
				}
				fEMRiskFactorList[iCount] = new com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList();
				fEMRiskFactorList[iCount].setAppFactorCode(key.split("_")[0]);
				fEMRiskFactorList[iCount].setFactorType(key.split("_")[1]);
				fEMRiskFactorList[iCount].setFactorValue(map.get(key));
				if ("TextAge".equals(fEMRiskFactorList[iCount].getFactorType())) {
					String age = map.get(key);
					if (age != null && !"".equals(age) && age.indexOf("Y") >= 0) {
						age = age.substring(0, age.indexOf("Y"));
						if ("0".equals(age)) {
							age = "1";
						}
						String birthday = com.sinosoft.sms.messageinterface.pubfun.PubFun.calSDate(PubFun.getCurrentDate(), 0 - Integer.parseInt(age), "Y");
						fEMRiskFactorList[iCount].setFactorValue(birthday);
					}
				}
				fEMRiskFactorList[iCount].setIsPremCalFacotor("Y");
				iCount++;
			}
			calProductPrem[0].setFEMRiskFactorList(fEMRiskFactorList);
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("CalProductPrem", calProductPrem);
			Map<String,String> result = new HashMap<String,String>();
			ProductPremResponse ProductPrem;
			ProductPrem = ProductWebservice.ProductPremSereviceImpl(mp, null);
			result.put("Prem", ProductPrem.getTotalPrice()+"");// 原价
			result.put("DiscountPrice", ProductPrem.getDiscountTotalPrice()+"");//折扣价
			if(ProductPrem.getDiscountRate()!=null && ProductPrem.getDiscountRate().length>0){
				result.put("DiscountRate", ProductPrem.getDiscountRate()[0]+"");//折扣率
			}else{
				result.put("DiscountRate", "");//折扣率
			}
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
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
	public Order updateOrder(Order oldOrder, Order order) {
		oldOrder.seteRiskType(order.geteRiskType());
		oldOrder.setSubRiskTypeCode(order.getSubRiskTypeCode());
		oldOrder.setProductTotalPrice(order.getProductTotalPrice());
		oldOrder.setTotalAmount(order.getProductTotalPrice());
		oldOrder.setPaidAmount(order.getPaidAmount());
		oldOrder.setBrkRiskCode(order.getBrkRiskCode());
		oldOrder.setBrkRiskName(order.getBrkRiskName());
		oldOrder.setModifyDate(new Date());
		return oldOrder;
	}
	@Override
	public InformationAppnt updateInformationAppnt(InformationAppnt ifa,
			InformationAppnt informationAppnt) {
		ifa.setApplicantName(informationAppnt.getApplicantName());
		ifa.setApplicantIdentityType(informationAppnt.getApplicantIdentityType());
		ifa.setApplicantIdentityTypeName(informationAppnt.getApplicantIdentityTypeName());
		ifa.setApplicantIdentityId(informationAppnt.getApplicantIdentityId());
		ifa.setApplicantSex(informationAppnt.getApplicantSex());
		ifa.setApplicantSexName(informationAppnt.getApplicantSexName());
		ifa.setApplicantBirthday(informationAppnt.getApplicantBirthday());
		ifa.setApplicantMail(informationAppnt.getApplicantMail());
		ifa.setApplicantArea1(informationAppnt.getApplicantArea1());
		ifa.setApplicantArea2(informationAppnt.getApplicantArea2());
		ifa.setApplicantZipCode(informationAppnt.getApplicantZipCode());
		ifa.setApplicantMobile(informationAppnt.getApplicantMobile());
		ifa.setApplicantTel(informationAppnt.getApplicantTel());
		ifa.setApplicantMobile2(informationAppnt.getApplicantMobile2());
		ifa.setApplicantHomePhone(informationAppnt.getApplicantHomePhone());
		ifa.setApplicantEnName(informationAppnt.getApplicantEnName());
		ifa.setApplicantAddress(informationAppnt.getApplicantAddress());
		ifa.setApplicantLiveAddress(informationAppnt.getApplicantLiveAddress());
		ifa.setApplicantFirstEnName(informationAppnt.getApplicantFirstEnName());
		ifa.setApplicantLastEnName(informationAppnt.getApplicantLastEnName());
		ifa.setApplicantOccupation1(informationAppnt.getApplicantOccupation1());
		ifa.setApplicantOccupation2(informationAppnt.getApplicantOccupation2());
		ifa.setApplicantOccupation3(informationAppnt.getApplicantOccupation3());
		ifa.setInvoiceheading(informationAppnt.getInvoiceheading());
		return ifa;
	}
	@Override
	public InformationInsured updateInformationInsured(
			InformationInsured infs1, InformationInsured informationInsureds) {
		infs1.setRecognizeeName(informationInsureds.getRecognizeeName());
		infs1.setRecognizeeEnName(informationInsureds.getRecognizeeEnName());
		infs1.setRecognizeeIdentityType(informationInsureds.getRecognizeeIdentityType());
		infs1.setRecognizeeIdentityTypeName(informationInsureds.getRecognizeeIdentityTypeName());
		infs1.setRecognizeeIdentityId(informationInsureds.getRecognizeeIdentityId());
		infs1.setRecognizeeSex(informationInsureds.getRecognizeeSex());
		infs1.setRecognizeeSexName(informationInsureds.getRecognizeeSexName());
		infs1.setRecognizeeBirthday(informationInsureds.getRecognizeeBirthday());
		infs1.setRecognizeeMobile(informationInsureds.getRecognizeeMobile());
		infs1.setRecognizeeTel(informationInsureds.getRecognizeeTel());
		infs1.setRecognizeeMail(informationInsureds.getRecognizeeMail());
		infs1.setRecognizeeZipCode(informationInsureds.getRecognizeeZipCode());
		infs1.setOccupation1(informationInsureds.getOccupation1());
		infs1.setOccupation2(informationInsureds.getOccupation2());
		infs1.setOccupation3(informationInsureds.getOccupation3());
		infs1.setRecognizeeArea1(informationInsureds.getRecognizeeArea1());
		infs1.setRecognizeeArea2(informationInsureds.getRecognizeeArea2());
		infs1.setRecognizeeIsMarry(informationInsureds.getRecognizeeIsMarry());
		infs1.setRecognizeeAppntRelation(informationInsureds.getRecognizeeAppntRelation());
		infs1.setRecognizeeAppntRelationName(informationInsureds.getRecognizeeAppntRelationName());
		infs1.setSchoolOrCompany(informationInsureds.getSchoolOrCompany());
		infs1.setOutGoingParpose(informationInsureds.getOutGoingParpose());
		infs1.setRecognizeeFirstEnName(informationInsureds.getRecognizeeFirstEnName());
		infs1.setRecognizeeLastEnName(informationInsureds.getRecognizeeLastEnName());
		infs1.setRecognizeeAddress(informationInsureds.getRecognizeeAddress());
		infs1.setRecognizeeLiveAddress(informationInsureds.getRecognizeeLiveAddress());
		infs1.setFlightNo(informationInsureds.getFlightNo());
		infs1.setFlightTime(informationInsureds.getFlightTime());
		infs1.setDriverSchoolName(informationInsureds.getDriverSchoolName());
		infs1.setDriverNo(informationInsureds.getDriverNo());
		infs1.setDestinationCountry(informationInsureds.getDestinationCountry());
		infs1.setDestinationCountryText(informationInsureds.getDestinationCountryText());
		infs1.setFlightLocation(informationInsureds.getFlightLocation());
		infs1.setContNo(informationInsureds.getContNo());
		infs1.setIsSelf(informationInsureds.getIsSelf());
		infs1.setHeight(informationInsureds.getHeight());
		infs1.setWeight(informationInsureds.getWeight());
		infs1.setOverseasOccupation(informationInsureds.getOverseasOccupation());
		infs1.setNationality(informationInsureds.getNationality());
		infs1.setTravelMode(informationInsureds.getTravelMode());
		infs1.setTravelType(informationInsureds.getTravelType());
		infs1.setHaveBuy(informationInsureds.getHaveBuy());
		return infs1;
	}
	@Override
	public String getBrithdayByFactor(String age) {
		String birthday ="1991-01-01";
		if (age != null && !"".equals(age)) {
			if(age.indexOf("Y") >= 0){
				age = age.substring(0, age.indexOf("Y"));
				if ("0".equals(age)) {
					age = "1";
				}
				birthday = PubFun.calSDate(PubFun.getCurrentDate(), 0 - Integer.parseInt(age), "Y");
			}else{
				birthday = age;
			}
		}
		return birthday;
	}
	
}
