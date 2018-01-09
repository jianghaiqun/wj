package cn.com.sinosoft.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.InformationInsuredDao;
import cn.com.sinosoft.entity.InformationInsured;
import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.InformationInsuredService;
import cn.com.sinosoft.service.OccupationService;

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
public class InformationInsuredServiceImpl extends BaseServiceImpl<InformationInsured, String> implements InformationInsuredService {

	@Resource
	private InformationInsuredDao informationInsuredDao;
	@Resource 
	private OccupationService occupationService;
	@Resource
	private AreaService areaService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	public void setBaseDao(InformationInsuredDao informationInsuredDao) {
		super.setBaseDao(informationInsuredDao);
	}
	@Override
	public InformationInsured getByOrParentId(String informationId) {
		return informationInsuredDao.getByOrParentId(informationId);
	}
	@Override
	public List<List<InsuredShow>> createShowInformationInsured(   
			Set<InformationInsured> informationInsuredSet ,String comCode) {
		List<List<InsuredShow>> insureds = new ArrayList<List<InsuredShow>>();
		if(informationInsuredSet != null && informationInsuredSet.size()>0){
			for(InformationInsured i : informationInsuredSet){
				if(i!=null){
					insureds.add(getInsuredShow(i,comCode));
				}
			}
		}
		return insureds;
	}
	private List<InsuredShow> getInsuredShow(InformationInsured i , String comCode) {   
		List<InsuredShow> is = new ArrayList<InsuredShow>();
		is.add(createInsuredShow("showName",i.getRecognizeeName()));
		SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
		if(i.getRecognizeeName()!=null && !"".equals(i.getRecognizeeName())){
			is.add(createInsuredShow("姓名",i.getRecognizeeName()));
		}
		if(i.getRecognizeeEnName()!=null && !"".equals(i.getRecognizeeEnName())){
			is.add(createInsuredShow("英文名或拼音",i.getRecognizeeEnName()));
		}
		if(i.getRecognizeeIdentityTypeName()!=null && !"".equals(i.getRecognizeeIdentityTypeName())){
			is.add(createInsuredShow("证件类型",i.getRecognizeeIdentityTypeName()));
		}
		if(i.getRecognizeeIdentityId()!=null && !"".equals(i.getRecognizeeIdentityId())){
			is.add(createInsuredShow("证件号码",i.getRecognizeeIdentityId()));
		}
		if(i.getRecognizeeSexName()!=null && !"".equals(i.getRecognizeeSexName())){
			is.add(createInsuredShow("性别",i.getRecognizeeSexName()));
		}
		if(i.getRecognizeeBirthday()!=null && !"".equals(i.getRecognizeeBirthday())){
			is.add(createInsuredShow("出生日期",i.getRecognizeeBirthday()));
		}
		if(i.getRecognizeeMobile()!=null && !"".equals(i.getRecognizeeMobile())){
			is.add(createInsuredShow("手机号码",i.getRecognizeeMobile()));
		}
		if(i.getRecognizeeTel()!=null && !"".equals(i.getRecognizeeTel())){
			is.add(createInsuredShow("固定电话",i.getRecognizeeTel()));
		}
		if(i.getRecognizeeMail()!=null && !"".equals(i.getRecognizeeMail())){
			is.add(createInsuredShow("电子邮箱",i.getRecognizeeMail()));
		}
		if(i.getRecognizeeZipCode()!=null && !"".equals(i.getRecognizeeZipCode())){
			is.add(createInsuredShow("邮编",i.getRecognizeeZipCode()));
		}
		if(i.getOccupation3()!=null && !"".equals(i.getOccupation3())){
			is.add(createInsuredShowByOccupation(i.getOccupation3()));
		}
		if(i.getRecognizeeArea2()!=null && !"".equals(i.getRecognizeeArea2())){
			is.add(createInsuredShowByArea(i.getRecognizeeArea2()));
		}
		if(i.getRecognizeeIsMarry()!=null && !"".equals(i.getRecognizeeIsMarry())){
			is.add(createInsuredShow("婚否",i.getRecognizeeIsMarry()));
		}
		if(i.getRecognizeeAppntRelationName()!=null && !"".equals(i.getRecognizeeAppntRelationName())){
			is.add(createInsuredShow("与投保人关系",i.getRecognizeeAppntRelationName()));
		}
		if(i.getSchoolOrCompany()!=null && !"".equals(i.getSchoolOrCompany())){
			is.add(createInsuredShow("留学学校或境外工作公司",i.getSchoolOrCompany()));
		}
		if(i.getOutGoingParpose()!=null && !"".equals(i.getOutGoingParpose())){
			is.add(createInsuredShow("出行目的",i.getOutGoingParpose()));
		}
		if(i.getRecognizeeFirstEnName()!=null && !"".equals(i.getRecognizeeFirstEnName())&&i.getRecognizeeLastEnName()!=null && !"".equals(i.getRecognizeeLastEnName())){
			is.add(createInsuredShow("姓名拼音",i.getRecognizeeFirstEnName()+" "+i.getRecognizeeLastEnName()));
		}
		if(i.getRecognizeeAddress()!=null && !"".equals(i.getRecognizeeAddress())){
			is.add(createInsuredShow("联系地址",i.getRecognizeeAddress()));
		}
		if(i.getFlightNo()!=null && !"".equals(i.getFlightNo())){
			is.add(createInsuredShow("航班号",i.getFlightNo()));
		}
		if(i.getFlightTime()!=null){
			is.add(createInsuredShow("起飞时间",f2.format(i.getFlightTime())));
		}
		if(i.getDriverSchoolName()!=null && !"".equals(i.getDriverSchoolName())){
			is.add(createInsuredShow("驾校名称",i.getDriverSchoolName()));
		}
		if(i.getDriverNo()!=null && !"".equals(i.getDriverNo())){
			is.add(createInsuredShow("学员编号",i.getDriverNo()));
		}
		if(i.getDestinationCountry()!=null && !"".equals(i.getDestinationCountry())){
			is.add(createInsuredShowByCountry(i.getDestinationCountry()));
		}else{
			if(i.getDestinationCountryText()!=null && !"".equals(i.getDestinationCountryText())){
				is.add(createInsuredShow("目的地",i.getDestinationCountryText()));
			}
		}
		if(i.getFlightLocation()!=null && !"".equals(i.getFlightLocation())){
			is.add(createInsuredShow("起飞地点 ",i.getFlightLocation()));
		}
		if(i.getContNo()!=null && !"".equals(i.getContNo())){
			is.add(createInsuredShow("保单号",i.getContNo()));
		}
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
		if(i.getHaveBuy()!=null && !"".equals(i.getHaveBuy())){
			is.add(createInsuredShow("身故保险金额",i.getHaveBuy()+" 万元"));
		}
		return is;
	}
	private String getNationalityName(String nationality,String comCode) {
		return dictionaryService.getNameByCodeType(comCode, "nationality", nationality);
	}
	private InsuredShow createInsuredShowByCountry(String destinationCountry) {
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
		return createInsuredShow("目的地",str);
	}
	private InsuredShow createInsuredShowByArea(String recognizeeArea2) {
		if(recognizeeArea2!=null && !"".equals(recognizeeArea2)){
			return createInsuredShow("所在地区", areaService.getAreaName(recognizeeArea2));
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
	
}