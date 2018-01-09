package cn.com.sinosoft.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.SDInformationAppntDao;
import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.OccupationService;
import cn.com.sinosoft.service.SDInformationAppntService;

import com.sinosoft.framework.utility.StringUtil;

/**
 * Service实现类 - 品牌
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
public class SDInformationAppntServiceImpl extends BaseServiceImpl<SDInformationAppnt, String> implements SDInformationAppntService {

	@Resource
	private SDInformationAppntDao sdinformationAppntDao;
	@Resource 
	private OccupationService occupationService;
	@Resource
	private AreaService areaService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	public void setBaseDao(SDInformationAppntDao sdinformationAppntDao) {
		super.setBaseDao(sdinformationAppntDao);
	}
	@Override
	public SDInformationAppnt getByOrParentId(String informationId) {
		return sdinformationAppntDao.getByOrParentId(informationId);
	}
	//@Override
	public List<List<InsuredShow>> createShowInformationAppnt(Set<SDInformationAppnt> sdinformationAppntSet ,String comCode) {
		List<List<InsuredShow>> appnts = new ArrayList<List<InsuredShow>>();
		if(sdinformationAppntSet != null && sdinformationAppntSet.size()>0){
			for(SDInformationAppnt i : sdinformationAppntSet){
				if(i!=null){
					appnts.add(getAppntShow(i,comCode));
				}
			}
		}
		return appnts;
	}
	/**
	 * 重新组装投保人信息
	 * @param i
	 * @param comCode
	 * @return 返回组装后的结果集合
	 */
	private List<InsuredShow> getAppntShow(SDInformationAppnt i , String comCode) {   
		List<InsuredShow> is = new ArrayList<InsuredShow>();
		if(i.getApplicantName()!=null && !"".equals(i.getApplicantName())){
			is.add(createInsuredShow("姓名",i.getApplicantName()));
		}
		if(i.getApplicantEnName()!=null && !"".equals(i.getApplicantEnName())){
			is.add(createInsuredShow("英文名或拼音",i.getApplicantEnName()));
		}
		if(i.getApplicantIdentityTypeName()!=null && !"".equals(i.getApplicantIdentityTypeName())){
			is.add(createInsuredShow("证件类型",i.getApplicantIdentityTypeName()));
		}
		if(i.getApplicantIdentityId()!=null && !"".equals(i.getApplicantIdentityId())){
			is.add(createInsuredShow("证件号码",i.getApplicantIdentityId()));
		}
		if(i.getApplicantStartID()!=null && !"".equals(i.getApplicantStartID()) && i.getApplicantEndID()!=null && !"".equals(i.getApplicantEndID())){
			String ApplicantEndID = i.getApplicantEndID();
			if("  9999-12-31  ".indexOf(ApplicantEndID) >=0 ){
				ApplicantEndID = "长期";
			}
			is.add(createInsuredShow("证件有效期",(i.getApplicantStartID()+"  至      "+ ApplicantEndID)));
		}
		if(i.getApplicantSexName()!=null && !"".equals(i.getApplicantSexName())){
			is.add(createInsuredShow("性别",i.getApplicantSexName()));
		}
		if(i.getApplicantBirthday()!=null && !"".equals(i.getApplicantBirthday())){
			is.add(createInsuredShow("出生日期",i.getApplicantBirthday()));
		}
		if(i.getApplicantMobile()!=null && !"".equals(i.getApplicantMobile())){
			is.add(createInsuredShow("手机号码",i.getApplicantMobile()));
		}
		if(i.getApplicantTel()!=null && !"".equals(i.getApplicantTel())){
			is.add(createInsuredShow("固定电话",i.getApplicantTel()));
		}
		if(i.getApplicantMail()!=null && !"".equals(i.getApplicantMail())){
			is.add(createInsuredShow("电子邮箱",i.getApplicantMail()));
		}
		//mod by wangenjian 投保人 预览页面 瑞泰和富德的产品显示邮编 20151216
		if(comCode.equals("1036")||comCode.equals("2100")){
			
			if(i.getApplicantZipCode()!=null && !"".equals(i.getApplicantZipCode())){
				is.add(createInsuredShow("邮编",i.getApplicantZipCode()));
			}
		}
		if(i.getApplicantOccupation3()!=null && !"".equals(i.getApplicantOccupation3())){
			is.add(createAppntShowByOccupation(i.getApplicantOccupation3()));
		}
		if(i.getApplicantArea2()!=null && !"".equals(i.getApplicantArea2()) && StringUtil.isEmpty(i.getApplicantArea3())){
			is.add(createAppntShowByArea(i.getApplicantArea2()));
		}
		if(i.getApplicantArea3()!=null && !"".equals(i.getApplicantArea3())){
			is.add(createAppntShowByArea3(i.getApplicantArea3()));
		}
		if(i.getApplicantAddress()!=null && !"".equals(i.getApplicantAddress())){
			is.add(createInsuredShow("联系地址",i.getApplicantAddress()));
		} 
		if(i.getSocialSecurity()!=null && !"".equals(i.getSocialSecurity())){
			is.add(createInsuredShow("是否有医保",dictionaryService.getNameByCodeValue(comCode, "socialSecurity", i.getSocialSecurity()).getCodeName()));
		}
		if(i.getApplicantIncome()!=null && !"".equals(i.getApplicantIncome())){
			is.add(createInsuredShow("年收入",i.getApplicantIncome()+" 万"));
		}
		return is;
	}
	private InsuredShow createInsuredShow(String name, String value) {
		InsuredShow is = new InsuredShow();
		is.setShowName(name);
		is.setShowValue(value);
		return is;
	}
	private InsuredShow createAppntShowByArea(String recognizeeArea2) {
		if(recognizeeArea2!=null && !"".equals(recognizeeArea2)){
			return createInsuredShow("所在地区", areaService.getAreaName(recognizeeArea2));
		}
		return null;
	}
	private InsuredShow createAppntShowByArea3(String recognizeeArea3) {
		if(recognizeeArea3!=null && !"".equals(recognizeeArea3)){
			return createInsuredShow("所在地区", areaService.getAreaName(recognizeeArea3));
		}
		return null;
	}
	private InsuredShow createAppntShowByOccupation(String occupation3) {
		if(occupation3!=null && !"".equals(occupation3)){
			return createInsuredShow("职业", occupationService.getOccupationName(occupation3));
		}
		return null;
	}
	
	@Override
	public List<String> getPaidAppntNameByMemberId(String memberId) {
		return sdinformationAppntDao.getPaidAppntNameByMemberId(memberId);
	}

	@Override
	public SDInformationAppnt getByAppntNameAndMemberId(String applicantName,
			String memberId) {
		return sdinformationAppntDao.getByAppntNameAndMemberId(applicantName,
				memberId);
	}
}