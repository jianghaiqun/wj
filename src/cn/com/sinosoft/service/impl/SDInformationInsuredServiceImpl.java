package cn.com.sinosoft.service.impl;


import cn.com.sinosoft.dao.SDInformationInsuredDao;
import cn.com.sinosoft.entity.Dictionary;
import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationBnf;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.OccupationService;
import cn.com.sinosoft.service.SDInformationInsuredService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service实现类 - 被保人
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTA46293E39B40E5C54C6BC841B973A701
 * ============================================================================
 */

@Service
public class SDInformationInsuredServiceImpl extends BaseServiceImpl<SDInformationInsured, String> implements SDInformationInsuredService {

	@Resource
	private SDInformationInsuredDao sdinformationInsuredDao;
	@Resource 
	private OccupationService occupationService;
	@Resource
	private AreaService areaService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	public void setBaseDao(SDInformationInsuredDao sdinformationInsuredDao) {
		super.setBaseDao(sdinformationInsuredDao);
	}
	@Override
	public SDInformationInsured getByOrParentId(String informationId) {
		return sdinformationInsuredDao.getByOrParentId(informationId);
	}
	@Override
	public List<List<InsuredShow>> createShowInformationInsured(   
			Set<SDInformationInsured> sdinformationInsuredSet ,String comCode) {
		List<List<InsuredShow>> insureds = new ArrayList<List<InsuredShow>>();
		if(sdinformationInsuredSet != null && sdinformationInsuredSet.size()>0){
			for(SDInformationInsured i : sdinformationInsuredSet){
				if(i!=null){
					insureds.add(getInsuredShow(i,comCode));
				}
			}
		}
		return insureds;
	}
	@Override
	public List<List<InsuredShow>> createShowInformationInsuredNew(   
			Set<SDInformationInsured> sdinformationInsuredSet ,String comCode,String ordersn) {
		List<List<InsuredShow>> insureds = new ArrayList<List<InsuredShow>>();
		if(sdinformationInsuredSet != null && sdinformationInsuredSet.size()>0){
			for(SDInformationInsured i : sdinformationInsuredSet){
				if(i!=null){
					insureds.add(getInsuredShow(i,comCode));
				}
			}

			String offlineCode =  new QueryBuilder("SELECT offlinecode FROM underwriting_offline_healthinfo WHERE ordersn='"+ordersn+"'").executeString();
			if(StringUtil.isNotEmpty(offlineCode)){
				for(int i = 0; i<insureds.size(); i++){
					insureds.get(i).add(createInsuredShow("核保编码",offlineCode));
				}
			}
		}

		return insureds;
	}
	private List<InsuredShow> getInsuredShow(SDInformationInsured i , String comCode) {   
		List<InsuredShow> is = new ArrayList<InsuredShow>();
		//is.add(createInsuredShow("showName",i.getRecognizeeName()))
		if(i.getRecognizeeName()!=null && !"".equals(i.getRecognizeeName())){
			is.add(createInsuredShow("姓名",i.getRecognizeeName()));
		}
		if(i.getRecognizeeEnName()!=null && !"".equals(i.getRecognizeeEnName())){
			is.add(createInsuredShow("英文名或拼音",i.getRecognizeeEnName()));
		}
		if(i.getRecognizeeSexName()!=null && !"".equals(i.getRecognizeeSexName())){
			is.add(createInsuredShow("性别",i.getRecognizeeSexName()));
		}
		if(i.getRecognizeeBirthday()!=null && !"".equals(i.getRecognizeeBirthday())){
			is.add(createInsuredShow("出生日期",i.getRecognizeeBirthday()));
		}
		if(i.getRecognizeeAppntRelationName()!=null && !"".equals(i.getRecognizeeAppntRelationName())){
			is.add(createInsuredShow("与投保人关系",i.getRecognizeeAppntRelationName()));
		}
		if(i.getRecognizeeIdentityTypeName()!=null && !"".equals(i.getRecognizeeIdentityTypeName())){
			is.add(createInsuredShow("证件类型",i.getRecognizeeIdentityTypeName()));
		}
		if(i.getRecognizeeIdentityId()!=null && !"".equals(i.getRecognizeeIdentityId())){
			is.add(createInsuredShow("证件号码",i.getRecognizeeIdentityId()));
		} 
		if(i.getRecognizeeStartID()!=null && !"".equals(i.getRecognizeeStartID()) && i.getRecognizeeEndID()!=null && !"".equals(i.getRecognizeeEndID())){
			String RecognizeeEndID = i.getRecognizeeEndID();
			if("  9999-12-31  ".indexOf(RecognizeeEndID) >=0 ){
				RecognizeeEndID = "长期";
			}
			is.add(createInsuredShow(" 证件有效期",(i.getRecognizeeStartID()+"  至     "+ RecognizeeEndID )));
		}
		if(i.getRecognizeeArea3()!=null && !"".equals(i.getRecognizeeArea3())){
			is.add(createInsuredShowByArea(i.getRecognizeeArea3()));
		}else{
			if(i.getRecognizeeArea2()!=null && !"".equals(i.getRecognizeeArea2())){
				is.add(createInsuredShowByArea(i.getRecognizeeArea2()));
			}
		}
		// 出发地
		if(i.getRecognizeeOrigin3()!=null && !"".equals(i.getRecognizeeOrigin3())){
			is.add(createInsuredShowByOrigin(i.getRecognizeeOrigin3()));
		}else{
			if(i.getRecognizeeOrigin2()!=null && !"".equals(i.getRecognizeeOrigin2())){
				is.add(createInsuredShowByOrigin(i.getRecognizeeOrigin2()));
			}
		}
		// 目的地
		if(i.getRecognizeeDestination3()!=null && !"".equals(i.getRecognizeeDestination3())){
			is.add(createInsuredShowByDestination(i.getRecognizeeDestination3()));
		}else{
			if(i.getRecognizeeDestination2()!=null && !"".equals(i.getRecognizeeDestination2())){
				is.add(createInsuredShowByDestination(i.getRecognizeeDestination2()));
			}
		}
		if(i.getRecognizeeMobile()!=null && !"".equals(i.getRecognizeeMobile())){
			is.add(createInsuredShow("手机号码",i.getRecognizeeMobile()));
		}
		if(i.getRecognizeeMail()!=null && !"".equals(i.getRecognizeeMail())){
			is.add(createInsuredShow("电子邮箱",i.getRecognizeeMail()));
		}
		if(i.getRecognizeeOccupation3()!=null && !"".equals(i.getRecognizeeOccupation3())){
			is.add(createInsuredShowByOccupation(i.getRecognizeeOccupation3()));
		}
		if(i.getRecognizeeAddress()!=null && !"".equals(i.getRecognizeeAddress())){
			is.add(createInsuredShow("联系地址",i.getRecognizeeAddress()));
		}
		if(i.getRecognizeeTel()!=null && !"".equals(i.getRecognizeeTel())){
			is.add(createInsuredShow("固定电话",i.getRecognizeeTel()));
		} 
		if(i.getFlightNo()!=null && !"".equals(i.getFlightNo())){
			is.add(createInsuredShow("航班号",i.getFlightNo()));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(i.getFlightTime()!=null){
			try {
				is.add(createInsuredShow("起飞时间",sdf.format(sdf.parse(i.getFlightTime()))));
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
		}
		//手动输入邮编订单预览页需要显示 2013-07-24 by heyang
		if(comCode.equals("2043")){
			if(i.getRecognizeeZipCode()!=null && !"".equals(i.getRecognizeeZipCode())){
				is.add(createInsuredShow("邮编",i.getRecognizeeZipCode()));
			} 
		}
		QueryBuilder qbScope = new QueryBuilder(
				"SELECT codevalue FROM zdcode WHERE codetype='singleDestination' AND parentcode='singleDestination'");
		DataTable dtScope = qbScope.executeDataTable();
		boolean singleFlag=false;
		if (dtScope != null && dtScope.getRowCount() > 0) {
			for (int j = 0; j < dtScope.getRowCount(); j++) {
				if(dtScope.getString(j, "codevalue").equals(comCode)){
					singleFlag=true;
					break;
				}
			}
		}
		if((i.getDestinationCountry()==null||"".equals(i.getDestinationCountry())) && (i.getDestinationCountryText()!=null&&!"".equals(i.getDestinationCountryText()))){
			is.add(createInsuredShow("旅游目的地",i.getDestinationCountryText()));
		}
		if(singleFlag && (i.getDestinationCountryText()!=null&&!"".equals(i.getDestinationCountryText()))){
			is.add(createInsuredShow("旅游目的地",i.getDestinationCountryText()));
		}
		if(i.getRecognizeeIsMarry()!=null && !"".equals(i.getRecognizeeIsMarry())){
			is.add(createInsuredShow("婚否",i.getRecognizeeIsMarry()));
		} 
		if(i.getSchoolOrCompany()!=null && !"".equals(i.getSchoolOrCompany())){
			is.add(createInsuredShow("留学学校或境外工作公司",i.getSchoolOrCompany()));
		}
		if(i.getOutGoingParpose()!=null && !"".equals(i.getOutGoingParpose())){
			is.add(createInsuredShow("出行目的",i.getOutGoingParpose()));
		}
		/*if(i.getRecognizeeFirstName()!=null && !"".equals(i.getRecognizeeFirstName())&&i.getRecognizeeLashName()!=null && !"".equals(i.getRecognizeeLashName())){
			is.add(createInsuredShow("姓名拼音:",i.getRecognizeeFirstName()+" "+i.getRecognizeeLashName()));
		}*/  
		if(i.getDriverSchoolName()!=null && !"".equals(i.getDriverSchoolName())){
			is.add(createInsuredShow("驾校名称",i.getDriverSchoolName()));
		}
		if(i.getDriverNo()!=null && !"".equals(i.getDriverNo())){
			is.add(createInsuredShow("学员编号",i.getDriverNo()));
		} 
		if(i.getFlightLocation()!=null && !"".equals(i.getFlightLocation())){
			is.add(createInsuredShow("起飞地点 ",i.getFlightLocation()));
		}
		/*if(i.getSdinformationRiskTypeSet().iterator().next().getPolicyNo()!=null && !"".equals(i.getSdinformationRiskTypeSet().iterator().next().getPolicyNo())){
			is.add(createInsuredShow("保单号",i.getSdinformationRiskTypeSet().iterator().next().getPolicyNo()));
		}*/
		if(i.getHeight()!=null && !"".equals(i.getHeight())){
			is.add(createInsuredShow("身高",i.getHeight()+" cm"));
		}
		if(i.getWeight()!=null && !"".equals(i.getWeight())){
			is.add(createInsuredShow("体重",i.getWeight()+ " kg"));
		}
		if(i.getOverseasOccupation()!=null && !"".equals(i.getOverseasOccupation())){
			is.add(createInsuredShow("境外工作职业",i.getOverseasOccupation()));
		}
		if(i.getTravelType()!=null && !"".equals(i.getTravelType())){
			is.add(createInsuredShow("旅行种类",i.getTravelType()));
		}
		if(i.getTravelMode()!=null && !"".equals(i.getTravelMode())){
			is.add(createInsuredShow("旅行方式",i.getTravelMode()));
		}
		if(i.getNationality()!=null && !"".equals(i.getNationality())){
			is.add(createInsuredShow("国籍",getNationalityName(i.getNationality(),comCode)));
		}
		if(i.getHaveBuy()!=null && !"".equals(i.getHaveBuy()) ){
			if((comCode.equals("2042"))){
				is.add(createInsuredShow("身故保险金额",i.getHaveBuy()+" 元"));
			}else{
				is.add(createInsuredShow("身故保险金额",i.getHaveBuy()+" 万元"));
			}
		}
		if(i.getSocialSecurity()!=null && !"".equals(i.getSocialSecurity())){
			Dictionary tdictionary =	dictionaryService.getNameByCodeValue(comCode, "socialSecurity", i.getSocialSecurity());
			if(tdictionary!=null){
				is.add(createInsuredShow("是否有医保",tdictionary.getCodeName()));
			}
		}
		if(StringUtil.isNotEmpty(i.getCarPlateNo())){
			is.add(createInsuredShow("车牌号码",i.getCarPlateNo()));
		}
		
		return is;
	}
	private String getNationalityName(String nationality,String comCode) {
		return dictionaryService.getNameByCodeType(comCode, "nationality", nationality);
	}
	@Override
	public String getInsuredToCountry(SDInformationInsured tSDInformationInsured){
		String tInsuredToCountry = "";
		if(tSDInformationInsured.getDestinationCountry()!=null && !"".equals(tSDInformationInsured.getDestinationCountry())){
			tInsuredToCountry = createInsuredShowByCountry(tSDInformationInsured.getDestinationCountry());
		}else{
			if(tSDInformationInsured.getDestinationCountryText()!=null && !"".equals(tSDInformationInsured.getDestinationCountryText())){
				tInsuredToCountry = tSDInformationInsured.getDestinationCountryText();
			}
		}
		return tInsuredToCountry;
	}

	private String createInsuredShowByCountry(String destinationCountry) {
		String str = "";
		if(destinationCountry!=""&&!"".equals(destinationCountry)){
			String[] destinate = destinationCountry.split(",");
			if(destinate!=null && destinate.length>0){
				for(int i=0; i<destinate.length;i++){
					String str1 = dictionaryService.findCountryNameByCode("CountryCode",destinate[i].trim());
					if(str1!=null && !"".equals(str1)){
						str = str + str1 + ",";
					}
				}
			}
		}
		if(str!=null && str.length()>0){
			str = str.substring(0, str.length()-1);
		}
		return str;
	}
	@Override
	public SDInformationInsured getInsuredByAppnt(SDInformation sdinf,SDInformationAppnt sdapp) {
		SDInformationInsured sdinformationinsured = new SDInformationInsured();
		String recognizeeSn = PubFun.GetSDInsuredSn();
		sdinformationinsured.setRecognizeeSn(recognizeeSn);
		sdinformationinsured.setInformationSn(sdinf.getInformationSn());
		sdinformationinsured.setRecognizeeName(sdapp.getApplicantName());
		sdinformationinsured.setRecognizeeIdentityType(sdapp.getApplicantIdentityType());
		sdinformationinsured.setRecognizeeIdentityId(sdapp.getApplicantIdentityId());
		sdinformationinsured.setRecognizeeIdentityTypeName(sdapp.getApplicantIdentityTypeName());
		sdinformationinsured.setRecognizeeSex(sdapp.getApplicantSex());
		sdinformationinsured.setRecognizeeSexName(sdapp.getApplicantSexName());
		sdinformationinsured.setRecognizeeMail(sdapp.getApplicantMail());
		sdinformationinsured.setRecognizeeArea1(sdapp.getApplicantArea1());
		sdinformationinsured.setRecognizeeArea2(sdapp.getApplicantArea2());
		sdinformationinsured.setRecognizeeArea3(sdapp.getApplicantArea3());
		sdinformationinsured.setRecognizeeBirthday(sdapp.getApplicantBirthday());
		sdinformationinsured.setRecognizeeMobile(sdapp.getApplicantMobile());
		sdinformationinsured.setRecognizeeAddress(sdapp.getApplicantAddress());
		sdinformationinsured.setRecognizeeZipCode(sdapp.getApplicantZipCode());
		int tInsuredAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(sdapp.getApplicantBirthday());
		sdinformationinsured.setRecognizeeAge(String.valueOf(tInsuredAge));
		String recognizeeAppntRelation = this.dictionaryService.getCodeValueByCodeName(sdinf.getInsuranceCompany(), "Relationship", "本人");
		sdinformationinsured.setRecognizeeAppntRelation(recognizeeAppntRelation);
		sdinformationinsured.setRecognizeeAppntRelationName("本人");
		if (sdapp.getApplicantOccupation1() != null && !"".equals(sdapp.getApplicantOccupation1())) {
			sdinformationinsured.setRecognizeeOccupation1(sdapp.getApplicantOccupation1());
		} else {
			sdinformationinsured.setRecognizeeOccupation1(sdinformationinsured.getRecognizeeOccupation1());
		}
		if (sdapp.getApplicantOccupation2() != null && !"".equals(sdapp.getApplicantOccupation2())) {
			sdinformationinsured.setRecognizeeOccupation2(sdapp.getApplicantOccupation2());
		} else {
			sdinformationinsured.setRecognizeeOccupation2(sdinformationinsured.getRecognizeeOccupation2());
		}
		if (sdapp.getApplicantOccupation3() != null && !"".equals(sdapp.getApplicantOccupation3())) {
			sdinformationinsured.setRecognizeeOccupation3(sdapp.getApplicantOccupation3());
		} else {
			sdinformationinsured.setRecognizeeOccupation3(sdinformationinsured.getRecognizeeOccupation3());
		}
		sdinformationinsured.setInformationSn(sdinf.getInformationSn());
		sdinformationinsured.setSdinformation(sdinf);
		return sdinformationinsured;
	}
	private InsuredShow createInsuredShowByArea(String recognizeeArea2) {
		if(recognizeeArea2!=null && !"".equals(recognizeeArea2)){
			return createInsuredShow("所在地区", areaService.getAreaName(recognizeeArea2));
		}
		return null;
	}
	private InsuredShow createInsuredShowByOrigin(String originArea2) {
		if(originArea2!=null && !"".equals(originArea2)){
			return createInsuredShow("出发地", areaService.getAreaName(originArea2));
		}
		return null;
	}
	private InsuredShow createInsuredShowByDestination(String destinationArea2) {
		if(destinationArea2!=null && !"".equals(destinationArea2)){
			return createInsuredShow("目的地", areaService.getAreaName(destinationArea2));
		}
		return null;
	}
	private InsuredShow createInsuredShowByOccupation(String occupation3) {
		if(occupation3!=null && !"".equals(occupation3)){
			return createInsuredShow("职业", occupationService.getOccupationName(occupation3));
		}
		return null;
	}
	private InsuredShow createInsuredShow(String name, String value) {
		InsuredShow is = new InsuredShow();
		is.setShowName(name);
		is.setShowValue(value);
		return is;
	}
	
	public List<List<InsuredShow>> createBnfShow(Set<SDInformationInsured> sdinformationInsuredSet) {
		List<List<InsuredShow>> bnfs = new ArrayList<List<InsuredShow>>();
		if(sdinformationInsuredSet != null && sdinformationInsuredSet.size()>0){
			Set<SDInformationBnf> bnfSet;
			List<InsuredShow> is;
			for(SDInformationInsured i : sdinformationInsuredSet){
				if(i != null){
					bnfSet = i.getSdinformationbnfSet();
					if (bnfSet != null && bnfSet.size()>0) {
						for(SDInformationBnf ibnf : bnfSet){
							if (ibnf != null) {
								is = new ArrayList<InsuredShow>();
								if(StringUtil.isNotEmpty(ibnf.getBnfName())){
									is.add(createInsuredShow("姓名",ibnf.getBnfName()));
								}
								if(StringUtil.isNotEmpty(ibnf.getBnfSexName())){
									is.add(createInsuredShow("性别",ibnf.getBnfSexName()));
								}
								if(StringUtil.isNotEmpty(ibnf.getBnfBirthday())){
									is.add(createInsuredShow("出生日期",ibnf.getBnfBirthday()));
								}
								if(StringUtil.isNotEmpty(ibnf.getRelationToInsuredName())){
									is.add(createInsuredShow("与被保人关系",ibnf.getRelationToInsuredName()));
								}
								if(StringUtil.isNotEmpty(ibnf.getBnfIDTypeName())){
									is.add(createInsuredShow("证件类型",ibnf.getBnfIDTypeName()));
								}
								if(StringUtil.isNotEmpty(ibnf.getBnfIDNo())){
									is.add(createInsuredShow("证件号码",ibnf.getBnfIDNo()));
								}
								if(StringUtil.isNotEmpty(ibnf.getBnfStartID()) && StringUtil.isNotEmpty(ibnf.getBnfEndID())){
									String BnfEndID = ibnf.getBnfEndID();
									if("  9999-12-31  ".indexOf(BnfEndID) >=0 ){
										BnfEndID = "长期";
									}
									is.add(createInsuredShow(" 证件有效期",ibnf.getBnfStartID()+"  至     "+BnfEndID));
								}
								if(StringUtil.isNotEmpty(ibnf.getBenePer())){
									is.add(createInsuredShow("受益比例",ibnf.getBenePer() + "%"));
								}
								
								bnfs.add(is);
							}
						}
					}
				}
			}
		}
		
		return bnfs;
	}
	
	@Override
	public List<SDInformationInsured> getListByOrParentId(String informationId) {
		return sdinformationInsuredDao.getListByOrParentId(informationId);
	}
	
	@Override
	public List<SDInformationInsured> getListByOrderSn(String orderSn) {
		return sdinformationInsuredDao.getListByOrderSn(orderSn);
	}
	
}