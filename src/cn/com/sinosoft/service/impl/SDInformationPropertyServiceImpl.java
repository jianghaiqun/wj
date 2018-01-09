package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.SDInformationPropertyDao;
import cn.com.sinosoft.entity.Dictionary;
import cn.com.sinosoft.entity.InsuredShow;
import cn.com.sinosoft.entity.SDInformationProperty;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.DictionaryService;
import cn.com.sinosoft.service.SDInformationPropertyService;

import com.sinosoft.framework.utility.StringUtil;

@Service
public class SDInformationPropertyServiceImpl extends BaseServiceImpl<SDInformationProperty,String> implements SDInformationPropertyService{
	@Resource
	private SDInformationPropertyDao sdInformationPropertyDao;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private AreaService areaService;

	public void setSdInformationPropertyDao(
			SDInformationPropertyDao sdInformationPropertyDao) {
		super.setBaseDao(sdInformationPropertyDao);
	}

	@Override
	public List<InsuredShow> getcreateShowOneProperty(
			SDInformationProperty sdp, String comCode, String productId) {
		List<InsuredShow> is = new ArrayList<InsuredShow>();
		if(sdp!=null){
			if(StringUtil.isNotEmpty(sdp.getHourseType())){
				Dictionary d = dictionaryService.get(sdp.getHourseType());
				InsuredShow i = null;
				if(d!=null&&StringUtil.isNotEmpty(d.getCodeName())){
					i =createInsuredShow("房屋类型",d.getCodeName());
				}else{
					i = createInsuredShow("房屋类型",sdp.getHourseType());
				}
				is.add(i);
			}
			if(StringUtil.isNotEmpty(sdp.getHourseAge())){
				Dictionary d = dictionaryService.get(sdp.getHourseAge());
				InsuredShow i = null;
				if(d!=null&&StringUtil.isNotEmpty(d.getCodeName())){
					i =createInsuredShow("房屋类型",d.getCodeName());
				}else{
					i = createInsuredShow("房屋类型",sdp.getHourseAge());
				}
				is.add(i);
			}
			if(StringUtil.isNotEmpty(sdp.getHourseNo())){
				is.add(createInsuredShow("房产证号",sdp.getHourseNo()));
			}
			if(StringUtil.isNotEmpty(sdp.getPropertyZip())){
				is.add(createInsuredShow("财产所在地邮编",sdp.getPropertyZip()));
			}
			if(StringUtil.isNotEmpty(sdp.getCarPlateNo())){
				is.add(createInsuredShow("车牌号",sdp.getCarPlateNo()));
			}
			if(StringUtil.isNotEmpty(sdp.getLicenseNumber())){
				is.add(createInsuredShow("驾驶证号",sdp.getLicenseNumber()));
			}
			if(StringUtil.isNotEmpty(sdp.getChassisNumber())){
				is.add(createInsuredShow("车架号",sdp.getChassisNumber()));
			}
			StringBuffer sb = new StringBuffer();
			if(StringUtil.isNotEmpty(sdp.getPropertyArea1())){
				sb.append(areaService.getAreaName(sdp.getPropertyArea1()));
			}
			if(StringUtil.isNotEmpty(sdp.getPropertyArea2())){
				sb.append(areaService.getAreaName(sdp.getPropertyArea2()));
			}
			if(StringUtil.isNotEmpty(sdp.getPropertyArea3())){
				sb.append(areaService.getAreaName(sdp.getPropertyArea3()));
			}
			if(StringUtil.isNotEmpty(sdp.getPropertyAdress())){
				sb.append(sdp.getPropertyAdress());
			}
			if(sb.length()>0){
				is.add(createInsuredShow("财产地址",sb.toString()));
			}
		}
		return is;
	}
	private InsuredShow createInsuredShow(String name, String value) {
		InsuredShow is = new InsuredShow();
		is.setShowName(name);
		is.setShowValue(value);
		return is;
	}

	@Override
	public SDInformationProperty getByInsuredId(String insuredId) {
		return sdInformationPropertyDao.getByInsuredId(insuredId);
	}

}
