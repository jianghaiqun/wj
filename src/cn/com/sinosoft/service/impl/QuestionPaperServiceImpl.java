package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.QuestionPaperDao;
import cn.com.sinosoft.dao.TradeInformationDao;
import cn.com.sinosoft.entity.QuestionPaper;
import cn.com.sinosoft.service.QuestionPaperService;

@Service
public class QuestionPaperServiceImpl extends BaseServiceImpl<QuestionPaper, String> implements QuestionPaperService {
	@Resource
	TradeInformationDao QuestionPaperDao;

	@Resource
	public void setQuestionPaperDao(QuestionPaperDao questionPaperDao) {
		super.setBaseDao(questionPaperDao);
	}

	@Override     
	public void updateQuestion(QuestionPaper q, QuestionPaper newQP) {
		q.setApplicantMobile(newQP.getApplicantMobile());
		q.setApplicantName(newQP.getApplicantName());
		q.setCannualincome(newQP.getCannualincome());
		q.setCarBrand1(newQP.getCarBrand1());
		q.setCarBrand2(newQP.getCarBrand2());
		q.setCarBrand3(newQP.getCarBrand3());
		q.setCarNum(newQP.getCarNum());
		q.setChildrenCount(newQP.getChildrenCount());
		q.setCmisrevenues(newQP.getCmisrevenues());
		q.setCtotalincome(newQP.getCtotalincome());
		q.setEffective(newQP.getEffective());
		q.setFamilySituation(newQP.getFamilySituation());
		q.setGpropertyValue1(newQP.getGpropertyValue1());
		q.setGpropertyValue2(newQP.getGpropertyValue2());
		q.setInsurePurpose(newQP.getInsurePurpose());
		q.setLannualincome(newQP.getLannualincome());
		q.setLivedTime(newQP.getLivedTime());
		q.setLiveStatus(newQP.getLiveStatus());
		q.setLmisrevenues(newQP.getLmisrevenues());
		q.setLTannualincome(newQP.getLTannualincome());
		q.setLTmisrevenues(newQP.getLTmisrevenues());
		q.setLtotalincome(newQP.getLtotalincome());
		q.setOtherInsurePurpose(newQP.getOtherInsurePurpose());
		q.setLTtotalincome(newQP.getLTtotalincome());
		q.setPeopleCount(newQP.getPeopleCount());
		q.setBuyPrice(newQP.getBuyPrice());
		q.setRentPrice(newQP.getRentPrice());
		q.setPropertyAdress1(newQP.getPropertyAdress1());
		q.setPropertyAdress2(newQP.getPropertyAdress2());
		q.setPropertyName1(newQP.getPropertyName1());
		q.setPropertyName2(newQP.getPropertyName2());
		q.setPropertyValue1(newQP.getPropertyValue1());
		q.setPropertyValue2(newQP.getPropertyValue2());
		q.setRecognizeeArea1(newQP.getRecognizeeArea1());
		q.setRecognizeeArea2(newQP.getRecognizeeArea2());
		q.setRecognizeeArea3(newQP.getRecognizeeArea3());
		q.setServiceYear(newQP.getServiceYear());
		q.setSupInformation(newQP.getSupInformation());
		q.setInsuranceCompany1(newQP.getInsuranceCompany1());
		q.setPeriod1(newQP.getPeriod1());
		q.setAmnt1(newQP.getAmnt1());
		q.setCValiDate1(newQP.getCValiDate1());
		q.setInsuranceCompany2(newQP.getInsuranceCompany2());
		q.setPeriod2(newQP.getPeriod2());
		q.setAmnt2(newQP.getAmnt2());
		q.setCValiDate2(newQP.getCValiDate2());
		q.setInsuranceCompany3(newQP.getInsuranceCompany3());
		q.setPeriod3(newQP.getPeriod3());
		q.setAmnt3(newQP.getAmnt3());
		q.setCValiDate3(newQP.getCValiDate3());
		q.setInsuranceCompany4(newQP.getInsuranceCompany4());
		q.setPeriod4(newQP.getPeriod4());
		q.setAmnt4(newQP.getAmnt4());
		q.setCValiDate4(newQP.getCValiDate4());
		q.setInsuranceCompany5(newQP.getInsuranceCompany5());
		q.setPeriod5(newQP.getPeriod5());
		q.setAmnt5(newQP.getAmnt5());
		q.setCValiDate5(newQP.getCValiDate5());
		q.setInsureStatus(newQP.getInsureStatus());
		q.setPartnerName(newQP.getPartnerName());
		q.setIncomeSource(newQP.getIncomeSource());
		q.setIncomeSum(newQP.getIncomeSum());
		q.setIncomeSourceDetail(newQP.getIncomeSourceDetail());
		q.setAssetStatus(newQP.getAssetStatus());
		q.setDebtKind1(newQP.getDebtKind1());
		q.setDebtMoney1(newQP.getDebtMoney1());
		q.setRepayContent1(newQP.getRepayContent1());
		q.setDebtKind2(newQP.getDebtKind2());
		q.setDebtMoney2(newQP.getDebtMoney2());
		q.setRepayContent2(newQP.getRepayContent2());
		q.setDebtKind3(newQP.getDebtKind3());
		q.setDebtMoney3(newQP.getDebtMoney3());
		q.setRepayContent3(newQP.getRepayContent3());
		q.setWorkStatus(newQP.getWorkStatus());
		q.setOperationStatus(newQP.getOperationStatus());
		q.setMoveDisorder(newQP.getMoveDisorder());
		q.setDrugStatus(newQP.getDrugStatus());
		q.setRiskyBehaviour(newQP.getRiskyBehaviour());
		q.setWorkPlaceStatus(newQP.getWorkPlaceStatus());
	}

}
