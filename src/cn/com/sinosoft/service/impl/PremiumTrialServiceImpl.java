package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.CarMenuDao;
import cn.com.sinosoft.dao.CarRateInformationDao;
import cn.com.sinosoft.dao.CityDao;
import cn.com.sinosoft.dao.DictDao;
import cn.com.sinosoft.dao.FDInsComDao;
import cn.com.sinosoft.dao.InsuranceDao;
import cn.com.sinosoft.dao.RateDao;
import cn.com.sinosoft.entity.CarMenu;
import cn.com.sinosoft.entity.CarRateInformation;
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.Dict;
import cn.com.sinosoft.entity.FDInsCom;
import cn.com.sinosoft.entity.Insurance;
import cn.com.sinosoft.entity.PremiumRate;
import cn.com.sinosoft.entity.SDtargetInformation;
import cn.com.sinosoft.service.PremiumTrialService;
import cn.com.sinosoft.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author LiuXin
 *
 */
@Service
public class PremiumTrialServiceImpl implements PremiumTrialService{
	private static final Logger logger = LoggerFactory.getLogger(PremiumTrialServiceImpl.class);
	@Resource
	private DictDao dictDao;
	@Resource
	private CityDao cityDao;
	@Resource
	private FDInsComDao fdInsComDao;
	@Resource
	private CarRateInformationDao carRateInformationDao;
	@Resource
	private CarMenuDao carMenuDao;
	@Resource
	private RateDao rateDao;
	@Resource
	private InsuranceDao insuranceDao;
	/**
	 * 得到保单失效日期
	 */
	@Override
	public Date addYear(Date start, int i) {
		Calendar now = Calendar.getInstance();
		now.setTime(start);
		now.add(Calendar.YEAR, i);
		now.add(Calendar.DAY_OF_YEAR, -1);
		return now.getTime();
	}
	/**
	 * 算出使用年限
	 */
	@Override
	public long getYearNO(Date dayE, Date dayS) {
		long ret = -1;
		long ds = dayS.getTime();
		long de = dayE.getTime();
		ret = (de - ds) / (1000 * 60 * 60 * 24) / 365;
		return ret;
	}
	/**
	 * 根据参数选择相应的字典数据
	 */
	@Override
	public List<Dict> getDictsByCode(String code) {
		List<Dict> list = new ArrayList<Dict>();
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("dictTypeSerial","=",code));
		list = dictDao.findByQBs(qbs, "dictSerial", "asc");
		return list;
	}
	/**
	 * 得到已经导入费率并且激活的保费试算信息
	 */
	@Override
	public List<CarRateInformation> getCarRateInformations(String regionCode) {
		List<CarRateInformation> list = new ArrayList<CarRateInformation>();
		if(regionCode!=null&&!"".equals(regionCode)){
			List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
			qbs.add(createQB("regionCode","=",regionCode));
			qbs.add(createQB("flag","=","Y"));
			list = carRateInformationDao.findByQBs(qbs, "companyCode", "asc");
		}
		return list;
	}
	
	@Override
	public List<CarMenu> getCarMenus() {
		return carMenuDao.getAll();
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
	@Override
	public List<City> getRegionCode() {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("placeType","=","01"));
		return cityDao.findByQBs(qbs, "id", "asc");
	}
	@Override
	public List<FDInsCom> getCompanys() {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("insClass","=","01"));
		return fdInsComDao.findByQBs(qbs, "supplierCode", "desc");
	}
	@Override
	public Insurance getAllPremium(SDtargetInformation sdTInformation,
			CarRateInformation carRI ,CarMenu cm) {
		Insurance insurance = getNewInsurance(cm);
		Double totle = 0.0;
		Double commercialPremium = 0.0;
		Double notDeductible = 0.0;
		insurance.setSerialNumber(sdTInformation.getId());
		insurance.setDicount(carRI.getDicount());
		insurance.setCompany(carRI.getCompanyCode());
		insurance.setRegionCode(carRI.getRegionCode());
		if(cm.getR002_premium()==0.0){
			insurance.setR002_premium(sdTInformation.getCarPrice());
		}
		if("Y".equals(cm.getR001())){
			insurance.setPremium_R001(getDoubleValue(tryCalculate("R001",false,carRI,sdTInformation,cm)));
			commercialPremium += insurance.getPremium_R001();
		}else {
			insurance.setPremium_R001(0.0);
		}
		if("Y".equals(cm.getR002())){
			insurance.setPremium_R002(getDoubleValue(tryCalculate("R002",false,carRI,sdTInformation,cm)));
			commercialPremium += insurance.getPremium_R002();
		}else {
			insurance.setPremium_R002(0.0);
		}
		if("Y".equals(cm.getR0030())){
			insurance.setPremium_R0030(getDoubleValue(tryCalculate("R0030",false,carRI,sdTInformation,cm)));
			commercialPremium += insurance.getPremium_R0030();
		}else {
			insurance.setPremium_R0030(0.0);
		}
		if("Y".equals(cm.getR0031())){
			insurance.setPremium_R0031(getDoubleValue(tryCalculate("R0031",false,carRI,sdTInformation,cm)));
			commercialPremium += insurance.getPremium_R0031();
		}else {
			insurance.setPremium_R0031(0.0);
		}
		if("Y".equals(cm.getR004())){
			insurance.setPremium_R004(getDoubleValue(tryCalculate("R004",false,carRI,sdTInformation,cm)));
			commercialPremium += insurance.getPremium_R004();
		}else {
			insurance.setPremium_R004(0.0);
		}
		if("Y".equals(cm.getR006())){
			insurance.setPremium_R006(getDoubleValue(tryCalculate("R006",false,carRI,sdTInformation,cm)));
			commercialPremium += insurance.getPremium_R006();
		}else {
			insurance.setPremium_R006(0);
		}
		if("Y".equals(cm.getR008())){
			insurance.setPremium_R008(getDoubleValue(tryCalculate("R008",false,carRI,sdTInformation,cm)));
			commercialPremium += insurance.getPremium_R008();
		}else {
			insurance.setPremium_R008(0.0);
		}
		if("Y".equals(cm.getSclar())){
			insurance.setPremium_SCLAR(getDoubleValue(tryCalculate("SCLAR",false,carRI,sdTInformation,cm)));
			commercialPremium += insurance.getPremium_SCLAR();
		}else{
			insurance.setPremium_SCLAR(0.0);
		}
		if("Y".equals(cm.getR001())&&"Y".equals(cm.getNR001())){
			insurance.setNonDeductible_R001(getDoubleValue(tryCalculate("R001",true,carRI,sdTInformation,cm)));
			notDeductible += insurance.getNonDeductible_R001();
		}else{
			insurance.setNonDeductible_R001(0.0);
		}
		if("Y".equals(cm.getR002())&&"Y".equals(cm.getNR002())){
			insurance.setNonDeductible_R002(getDoubleValue(tryCalculate("R002",true,carRI,sdTInformation,cm)));
			notDeductible += insurance.getNonDeductible_R002();
		}else{
			insurance.setNonDeductible_R002(0.0);
		}
		if("Y".equals(cm.getR0030())&&"Y".equals(cm.getNR0030())){
			insurance.setNonDeductible_R0030(getDoubleValue(tryCalculate("R0030",true,carRI,sdTInformation,cm)));
			notDeductible += insurance.getNonDeductible_R0030();
		}else{
			insurance.setNonDeductible_R0030(0.0);
		}
		if("Y".equals(cm.getR0031())&&"Y".equals(cm.getNR0031())){
			insurance.setNonDeductible_R0031(getDoubleValue(tryCalculate("R0031",true,carRI,sdTInformation,cm)));
			notDeductible += insurance.getNonDeductible_R0031();
		}else{
			insurance.setNonDeductible_R0031(0.0);
		}
		if("Y".equals(cm.getR004())&&"Y".equals(cm.getNR004())){
			insurance.setNonDeductible_R004(getDoubleValue(tryCalculate("R004",true,carRI,sdTInformation,cm)));
			notDeductible += insurance.getNonDeductible_R004();
		}else{
			insurance.setNonDeductible_R004(0.0);
		}
		if("Y".equals(cm.getR008())&&"Y".equals(cm.getNR008())){
			insurance.setNonDeductible_R008(getDoubleValue(tryCalculate("R008",true,carRI,sdTInformation,cm)));
			notDeductible += insurance.getNonDeductible_R008();
		}else{
			insurance.setNonDeductible_R008(0.0);
		}
		if(insurance.getDicount()!=null&&insurance.getDicount()>0.0){
			insurance.setCommercialPremium(getDoubleValue((commercialPremium+notDeductible)*insurance.getDicount()));
		}else{
			insurance.setCommercialPremium(getDoubleValue(commercialPremium+notDeductible));
		}
		insurance.setNotDeductible(getDoubleValue(notDeductible));
		totle = insurance.getCommercialPremium();
		insurance.setTotle(getDoubleValue(totle));
		return insurance;
	}
	
	public double tryCalculate(String InsuranceCode, boolean isTrailer,
			CarRateInformation carRI ,SDtargetInformation information,CarMenu cm){
		double premium = 0.00;
		String modelCode = information.getModelsCode();
		String selectType= getRateType(information.getModelsCode());
		if(modelCode.equals("C006")){
			information.setModelsCode("C007");
		}else if(modelCode.equals("C012")){
			information.setModelsCode("C008");
		}else if(modelCode.equals("C013")){
			information.setModelsCode("C009");
		}else if(modelCode.equals("C014")){
			information.setModelsCode("C010");
		}
		if ("R001".equals(InsuranceCode)) {// （一）第三者责任险
			if (!isTrailer) {
				if(selectType.equals("B")||selectType.equals("E")){
					return this.caculateOne(carRI,information,selectType,cm)*0.5;
				}else{
					return this.caculateOne(carRI,information,selectType,cm);
				}
			} else {
				double rate = caculateFive(carRI,"R001");
				if(rate>0){
					premium = this.caculateOne(carRI,information,selectType,cm)* rate;
				}else{
					premium = this.caculateOne(carRI,information,selectType,cm)* 0.15;
				}
				if(selectType.equals("B")||selectType.equals("E")){
					return premium*0.5;
				}else{
					return premium;
				}
			}

		} else if ("R002".equals(InsuranceCode)) {// （二）机动车损失保险
			if (!isTrailer) {
				if(selectType.equals("B")||selectType.equals("E")){
					return this.caculateTwo(carRI,information,selectType,cm)*0.5;
				}else{
					return this.caculateTwo(carRI,information,selectType,cm);
				}
			} else {
				double rate = caculateFive(carRI,"R002");
				if(rate>0){
					premium = this.caculateTwo(carRI,information,selectType,cm) * rate;
				}else{
					premium = this.caculateTwo(carRI,information,selectType,cm) * 0.15;
				}
				if(selectType.equals("B")||selectType.equals("E")){
					return premium*0.5;
				}else{
					return premium;
				}
			}

		} else if ("R0030".equals(InsuranceCode)
				|| "R0031".equals(InsuranceCode)) {// （三）车上人员责任险
			if(!isTrailer){
				return this.caculateThree(carRI,information,selectType,cm,InsuranceCode);
			}else{
				double rate = caculateFive(carRI,"R003");
				if(rate>0){
					premium = this.caculateThree(carRI,information,selectType,cm,InsuranceCode)* rate;
				}else{
					premium = this.caculateThree(carRI,information,selectType,cm,InsuranceCode)* 0.15;
				}
				return premium;
			}

		} else if ("R004".equals(InsuranceCode)) {// （四）盗抢险
			if (!isTrailer) {
				if(selectType.equals("B")||selectType.equals("E")){
					return this.caculateFour(carRI,information,selectType,cm)*0.5;
				}else{
					return this.caculateFour(carRI,information,selectType,cm);
				}
			} else {
				double rate = caculateFive(carRI,"R004");
				if(rate>0){
					premium = this.caculateFour(carRI,information,selectType,cm)* rate;
				}else{
					premium = this.caculateFour(carRI,information,selectType,cm)* 0.20;
				}
				if(selectType.equals("B")||selectType.equals("E")){
					return premium*0.5;
				}else{
					return premium;
				}
			}

		}  else if ("R006".equals(InsuranceCode)) {// （六）玻璃单独破碎险
			if(selectType.equals("D")){
				return this.caculateSix(carRI,information,selectType,cm)*1.1;
			}else{
				return this.caculateSix(carRI,information,selectType,cm);
			}
		} else if ("R008".equals(InsuranceCode)) {// （八）车身划痕损失险
            if(!isTrailer){
            	return this.caculateEight(carRI,information,selectType,cm);
            }else{
            	double rate = caculateFive(carRI,"R008");
            	if(rate>0){
            		premium = this.caculateEight(carRI,information,selectType,cm) * rate;
            	}else{
            		premium = this.caculateEight(carRI,information,selectType,cm) * 0.15;
            	}
				return premium;
            }
		} else if ("WDLAR".equals(InsuranceCode)) {// 涉水行驶损失附加险

			return this.caculateWDLAR(carRI,information,selectType,cm);
		} else if ("SCLAR".equals(InsuranceCode)) {// 自燃损失附加险

			return this.caculateSCLAR(carRI,information,selectType,cm);
		}
		return premium;
	}
	/**
	 * 强险计算
	 * 
	 */
//	private double caculateStrongRisk(Map<String, Object> paraMap,SDtargetInformation sdTInformation) {
//		double premium = 0;
//		StringBuilder sb = new StringBuilder();
//		sb.append("select * from PREMIUMRATE where RISK_CODE='JQX'");
//		sb.append(" and MODELS_CODE='" + paraMap.get("vehicle") + "'");
//		if(((String)paraMap.get("selectType")).equals("A")){
//			sb.append(" and USED_CODE ='" + sdTInformation.getCarUsed() + "'");//是否营业
//			sb.append(" and PERTAIN_CODE ='" + sdTInformation.getCarProperties() + "'");//所属性质
//			sb.append(" and PASSENGER_LOWER<='" + sdTInformation.getPassenger() + "'");
//			sb.append(" and PASSERNGER_UPPER>'" + sdTInformation.getPassenger() + "'");
//		}else if(((String)paraMap.get("selectType")).equals("B")||((String)paraMap.get("selectType")).equals("C")){
//			sb.append(" and USED_CODE='" + sdTInformation.getCarUsed() + "'");//是否营业
//			sb.append(" and LOAD_LOWER<='" + sdTInformation.getLoad() + "'");
//			sb.append(" and LOAD_UPPER>'" + sdTInformation.getLoad()+ "'");
//		}
//		try {
//			log.info("STRONG RISK:" + sb.toString());
//			PremiumRate pr = rateDao.getPremiumOrRate(sb.toString());
//			if (pr != null) {
//				premium = pr.getPremium();
//				
//				if (null != paraMap.get("forcefloat")) {
//					premium = premium
//							* (1 + Double.valueOf(paraMap.get("forcefloat").toString()));
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		log.info("STRONG RISK Premium:" + premium);
//		return premium;
//	}
	/**
	 * 计算第三责任险保险费
	 * 
	 */
	private double caculateOne(CarRateInformation carRI,SDtargetInformation sdTInformation,String selectType,CarMenu cm) {

		double premium = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("select * from PREMIUMRATE where RISKCODE='R001'");
		sb.append(" and MODELTYPE='" + carRI.getModelType() + "'");
		sb.append(" and REGIONCODE='" + carRI.getRegionCode() + "'");
		sb.append(" and MODELSCODE='" + sdTInformation.getModelsCode() + "'");
		if("A".equals(selectType)){
			sb.append(" and USEDCODE ='" + sdTInformation.getCarUsed() + "'");//是否营业
			sb.append(" and PERTAINCODE ='" + sdTInformation.getCarProperties() + "'");//所属性质
			sb.append(" and PASSENGERLOWER<='" + sdTInformation.getPassenger() + "'");
			sb.append(" and PASSERNGERUPPER>'" + sdTInformation.getPassenger() + "'");
		}else if("B".equals(selectType)||"C".equals(selectType)){
			sb.append(" and USEDCODE='" + sdTInformation.getCarUsed() + "'");//是否营业
			sb.append(" and LOADLOWER<='" + sdTInformation.getApprovedLoad() + "'");
			sb.append(" and LOADUPPER>'" + sdTInformation.getApprovedLoad()+ "'");
		}
		try {
			StringBuilder sql = new StringBuilder();
			StringBuilder sql_50 = new StringBuilder();
			// 需要考虑责任限额为100W以上的特殊处理情况
			int temp = 1;
			if (cm.getR001_premium() != 0.0) {
				double amount = cm.getR001_premium();
				if (amount > 100) {
					temp = (int) ((amount - 100) / 50);
					sql_50.append(sb);
					sql_50.append(" and SUMASSURED='50'");
					sql.append(sb);
					sql.append(" and SUMASSURED='100'");
				} else {
					sql.append(sb);
					sql.append(" and SUMASSURED='" + amount + "'");
				}
			}
			logger.info("Query InsuranceCode=R001 is:{}", sql.toString());
			PremiumRate pr = rateDao.getPremiumOrRate(sql.toString());
			if(pr!=null){
				premium = pr.getPremium();
			}

			if (sql_50.length() > 0) {
				double premium_50 = 0;
				logger.info("Query InsuranceCode=R001 where insuredAmount>100 is:{}", sql_50.toString());
				if(rateDao.getPremiumOrRate(sql_50.toString())!=null){
					premium_50 = rateDao.getPremiumOrRate(sql_50.toString()).getPremium();
				}
				if (premium > 0 && premium_50 > 0) {
					premium = premium + 0.9 * temp * (premium - premium_50);
				}
			}
		} catch (NumberFormatException ne) {
			logger.error(ne.getMessage(), ne);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Premium where InsuranceCode=R001 is:{}......", premium);
		return premium;
	}
	/**
	 * 机动车损失保费计算:保费 ＝ 基础保费 ＋ 保险金额 × 费率。一般为新车购置价
	 * 
	 * @param paraMap
	 * @return
	 */
	private double caculateTwo(CarRateInformation carRI,SDtargetInformation sdTInformation,String selectType,CarMenu cm) {

		double premium = 0;
		double rate = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("select * from PREMIUMRATE where  RISKCODE='R002'");
		sb.append(" and MODELTYPE='" + carRI.getModelType() + "'");
		sb.append(" and REGIONCODE='" + carRI.getRegionCode() + "'");
		sb.append(" and MODELSCODE='" + sdTInformation.getModelsCode() + "'");
		if("A".equals(selectType)){
			sb.append(" and USEDCODE ='" + sdTInformation.getCarUsed() + "'");//是否营业
			sb.append(" and PERTAINCODE ='" + sdTInformation.getCarProperties() + "'");//所属性质
			sb.append(" and PASSENGERLOWER<='" + sdTInformation.getPassenger() + "'");
			sb.append(" and PASSERNGERUPPER>'" + sdTInformation.getPassenger() + "'");
		}else if("B".equals(selectType)||"C".equals(selectType)){
			sb.append(" and USEDCODE ='" + sdTInformation.getCarUsed() + "'");//是否营业
			sb.append(" and LOADLOWER<='" + sdTInformation.getApprovedLoad() + "'");
			sb.append(" and LOADUPPER>'" + sdTInformation.getApprovedLoad()+ "'");
		}
		try {
			if(selectType.equals("A")||selectType.equals("B")||selectType.equals("C")||selectType.equals("D")||selectType.equals("E")||selectType.equals("F")){
				sb.append(" and AGELOWER<='" + sdTInformation.getUsedYear() + "'");
				sb.append(" and AGEUPPER>'" + sdTInformation.getUsedYear() + "'");
			}
			logger.info("R002:{}", sb.toString());
			PremiumRate pr = rateDao.getPremiumOrRate(sb.toString());
			if (pr != null) {
				premium = pr.getPremium();
				rate = pr.getRate();
				if(cm.getR002_premium()!=0.0){
					premium = premium + cm.getR002_premium() * rate;
				}else{
					premium = premium + sdTInformation.getCarPrice() * rate;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("R002 premium:{}", premium);
		return premium;
	}
	/**
	 * 驾驶人保费 = 每次事故责任限额 × 费率 乘客保费 = 每次事故每人责任限额 × 费率 × 投保乘客座位数
	 * 每次事故责任限额、每次事故每人责任限额最低为1000元，且限额必须是1000元的整数倍。
	 * 
	 * @param paraMap
	 * @return
	 */
	private double caculateThree(CarRateInformation carRI,SDtargetInformation sdTInformation,String selectType,CarMenu cm,String InsuranceCode) {
		// 需要分开驾驶人和乘客两种情况
		double premium = 0;
		double rate = 0;
		StringBuilder sb = new StringBuilder();
		if (InsuranceCode != null) {
			sb.append("select * from PREMIUMRATE where RISKCODE='R003'");
			sb.append(" and MODELTYPE='" + carRI.getModelType() + "'");
			sb.append(" and REGIONCODE='" + carRI.getRegionCode() + "'");
			sb.append(" and MODELSCODE='" + sdTInformation.getModelsCode() + "'");
			if("A".equals(selectType)){
				sb.append(" and USEDCODE ='" + sdTInformation.getCarUsed() + "'");//是否营业
				sb.append(" and PERTAINCODE ='" + sdTInformation.getCarProperties() + "'");//所属性质
				sb.append(" and PASSENGERLOWER<='" + sdTInformation.getPassenger() + "'");
				sb.append(" and PASSERNGERUPPER>'" + sdTInformation.getPassenger() + "'");
			}else if("B".equals(selectType)||"C".equals(selectType)){
				sb.append(" and USEDCODE ='" + sdTInformation.getCarUsed() + "'");//是否营业
				sb.append(" and LOADLOWER<='" + sdTInformation.getApprovedLoad() + "'");
				sb.append(" and LOADUPPER>'" + sdTInformation.getApprovedLoad()+ "'");
			}
			if ("R0030".equals(InsuranceCode)) {
				sb.append(" and RATETYPE = '" + Constant.RT_DRIVER + "'");
			}else if("R0031".equals(InsuranceCode)){
				sb.append(" and RATETYPE = '" + Constant.RT_PASSENGER + "'");
			}
			logger.info("R003:{}", sb.toString());
			try {
				if(rateDao.getPremiumOrRate(sb.toString())!=null){
					rate = rateDao.getPremiumOrRate(sb.toString()).getRate();
				}
				if ("R0030".equals(InsuranceCode)) {
					premium = cm.getR0030_premium()* rate;
				}
				if ("R0031".equals(InsuranceCode)) {// 乘客
					premium = cm.getR0031_premium()* rate;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		logger.info("Calculate R003 is:{}", premium);
		return premium;
	}
	/**
	 * 盗抢险
	 * 
	 * @param paraMap
	 * @return
	 */
	private double caculateFour(CarRateInformation carRI,SDtargetInformation sdTInformation,String selectType,CarMenu cm) {
		double premium = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("select * from PREMIUMRATE where RISKCODE='R004'");
		sb.append(" and MODELTYPE='" + carRI.getModelType() + "'");
		sb.append(" and REGIONCODE='" + carRI.getRegionCode() + "'");
		sb.append(" and MODELSCODE='" + sdTInformation.getModelsCode() + "'");
		if("A".equals(selectType)){
			sb.append(" and USEDCODE ='" + sdTInformation.getCarUsed() + "'");//是否营业
			sb.append(" and PERTAINCODE ='" + sdTInformation.getCarProperties() + "'");//所属性质
			sb.append(" and PASSENGERLOWER<='" + sdTInformation.getPassenger() + "'");
			sb.append(" and PASSERNGERUPPER>'" + sdTInformation.getPassenger() + "'");
		}else if("B".equals(selectType)||"C".equals(selectType)){
			sb.append(" and USEDCODE ='" + sdTInformation.getCarUsed() + "'");//是否营业
			sb.append(" and LOADLOWER<='" + sdTInformation.getApprovedLoad() + "'");
			sb.append(" and LOADUPPER>'" + sdTInformation.getApprovedLoad()+ "'");
		}
		try {
			logger.info("R004:{}", sb.toString());
			PremiumRate pr = rateDao.getPremiumOrRate(sb.toString());
			if (pr != null) {
				premium = pr.getPremium();
				double rate = pr.getRate();
				if(cm.getR004_premium()!=0.0){
					premium = premium + cm.getR004_premium() * rate;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("R004 Premium:{}", premium);
		return premium;
	}
	/**
	 * 玻璃破碎险
	 * 
	 * @param paraMap
	 * @return
	 */
	private double caculateSix(CarRateInformation carRI,SDtargetInformation sdTInformation,String selectType,CarMenu cm) {

		double premium = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("select * from PREMIUMRATE where RISKCODE='R006'");
		sb.append(" and MODELTYPE='" + carRI.getModelType() + "'");
		sb.append(" and REGIONCODE='" + carRI.getRegionCode() + "'");
		sb.append(" and MODELSCODE='" + sdTInformation.getModelsCode() + "'");
		if("A".equals(selectType)){
			sb.append(" and USEDCODE ='" + sdTInformation.getCarUsed() + "'");//是否营业
			sb.append(" and PERTAINCODE ='" + sdTInformation.getCarProperties() + "'");//所属性质
			sb.append(" and PASSENGERLOWER<='" + sdTInformation.getPassenger() + "'");
			sb.append(" and PASSERNGERUPPER>'" + sdTInformation.getPassenger() + "'");
		}else if("B".equals(selectType)||"C".equals(selectType)){
			sb.append(" and USEDCODE ='" + sdTInformation.getCarUsed() + "'");//是否营业
			sb.append(" and LOADLOWER<='" + sdTInformation.getApprovedLoad() + "'");
			sb.append(" and LOADUPPER>'" + sdTInformation.getApprovedLoad()+ "'");
		}
		try {
			String glassType = cm.getR006_type();
			if (glassType!=null&&"0".equals(glassType)) {// 国产玻璃
				sb.append(" and RATETYPE = '" + Constant.RT_GLASS_LOCAL +"'");
			} else if(glassType!=null&&"1".equals(glassType)){// 进口玻璃
				sb.append(" and RATETYPE = '" + Constant.RT_GLASS_IMPORT +"'");
			}
			logger.info("R006:{}", sb.toString());
			PremiumRate pr = rateDao.getPremiumOrRate(sb.toString());
			if (pr != null) {
				double rate = pr.getRate();
				premium = sdTInformation.getCarPrice()* rate;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("R006 Premium:{}", premium);
		return premium;
	}
	/**
	 * 车身划痕损失险
	 * 
	 * @param paraMap
	 * @return
	 */
	private double caculateEight(CarRateInformation carRI,SDtargetInformation sdTInformation,String selectType,CarMenu cm) {

		double premium = 0;
		double purchasePrice = sdTInformation.getCarPrice();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from PREMIUMRATE where RISKCODE='R008'");
		sb.append(" and MODELTYPE='" + carRI.getModelType() + "'");
		sb.append(" and REGIONCODE='" + carRI.getRegionCode() + "'");
		sb.append(" and SUMASSURED='" + cm.getR008_premium() + "'");
		try {
			sb.append(" and AGELOWER<='" + sdTInformation.getUsedYear() + "'");
			sb.append(" and AGEUPPER>'" + sdTInformation.getUsedYear() + "'");
			sb.append(" and PRICELOWER<='" + purchasePrice + "'");
			sb.append(" and PRICEUPPER>'" + purchasePrice + "'");
			logger.info("R008:{}", sb.toString());
			PremiumRate pr = rateDao.getPremiumOrRate(sb.toString());
			if (pr != null) {
				premium = pr.getPremium();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("R008 premium:{}", premium);
		return premium;
	}
	/**
	 * 增加涉水行驶损失附加险
	 * 
	 * @param paraMap
	 * @return
	 */
	private double caculateWDLAR(CarRateInformation carRI,SDtargetInformation sdTInformation,String selectType,CarMenu cm) {
		double premium = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("select * from PDInsuranceRate where insurancecode='WDLAR'");
		sb.append(" and MODELTYPE='" + carRI.getModelType() + "'");
		sb.append(" and REGIONCODE='" + carRI.getRegionCode() + "'");
		sb.append("supplierCode='" + ""+ "' and ");
		try {
			logger.info("Query InsuranceCode=WDLAR is:{}", sb.toString());
			PremiumRate pr = rateDao.getPremiumOrRate(sb.toString());
			if (pr != null) {
				premium = pr.getPremium();
				logger.info("Get Premium from DB is:{}-----", premium);
			}
		} catch (NumberFormatException ne) {
			logger.error(ne.getMessage(), ne);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Premium where InsuranceCode=WDLAR is:{}......", premium);
		return premium;
	}

	/**
	 * 自燃损失附加险
	 * 
	 * @param paraMap
	 * @return
	 */
	private double caculateSCLAR(CarRateInformation carRI,SDtargetInformation sdTInformation,String selectType,CarMenu cm) {

		double premium = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("select * from PREMIUMRATE where RISKCODE='SCLAR'");
		sb.append(" and MODELTYPE='" + carRI.getModelType() + "'");
		sb.append(" and REGIONCODE='" + carRI.getRegionCode() + "'");
		sb.append(" and USEDCODE ='" + sdTInformation.getCarUsed() + "'");//是否营业
		sb.append(" and MODELSCODE='" + sdTInformation.getModelsCode() + "'");
		sb.append(" and AGELOWER<='" + sdTInformation.getUsedYear() + "'");
		sb.append(" and AGEUPPER>'" + sdTInformation.getUsedYear() + "'");
		try {
			logger.info("SCLAR:{}", sb.toString());
			PremiumRate pr = rateDao.getPremiumOrRate(sb.toString());
			if (pr != null) {
				double rate = pr.getRate();
				if(cm.getSclar_premium()!=0.0){
					premium = cm.getSclar_premium()* rate;
				}else{
					premium = sdTInformation.getCarPrice()* rate;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("SCLAR Premium:{}", premium);
		return premium;
	}
	/**
	 * 不计免赔率特约条款
	 * 
	 * 保费 ＝ 适用本条款的险种标准保费 × 费率
	 * 
	 * @param paraMap
	 * @return
	 */
	private double caculateFive(CarRateInformation carRI,String code) {
		double rate = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("select * from PREMIUMRATE where 1=1");
		sb.append(" and RISKCODE='R005'");
		sb.append(" and MODELTYPE='" + carRI.getModelType() + "'");
		sb.append(" and REGIONCODE='" + carRI.getRegionCode() + "'");
		sb.append(" and APPLYRISKCODE = '"+code+"'");
		try {
			PremiumRate pr = rateDao.getPremiumOrRate(sb.toString());
			if (pr != null) {
				rate = pr.getRate();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("====不计免赔={}:{}",code, rate);
		return rate;
	}
	private String getRateType(String type){
		String selectType = "";
		if(type.equals("C001")||type.equals("C002")||type.equals("C003")||type.equals("C004")||type.equals("C005")){
			selectType = "A";
		}else if(type.equals("C006")){
			selectType = "B";
		}if(type.equals("C007")) {
			selectType = "C";
		}else if(type.equals("C008")||type.equals("C009")||type.equals("C010")||type.equals("C011")){
			selectType = "D";
		}if(type.equals("C012")||type.equals("C013")||type.equals("C014")) {
			selectType = "E";
		}else if(type.equals("C015")) {
			selectType = "F";
		}else if(type.equals("C016")||type.equals("C017")||type.equals("C018")||type.equals("C019")) {
			selectType = "G";
		}
		return selectType;
	}
	private Insurance getNewInsurance(CarMenu cm) {
		Insurance insurance = new Insurance();
		insurance.setCarMenuCode(cm.getCode());
		insurance.setR001(cm.getR001());
		insurance.setR001_premium(cm.getR001_premium());
		insurance.setR002(cm.getR002());
		insurance.setR002_premium(cm.getR002_premium());
		insurance.setR0030(cm.getR0030());
		insurance.setR0030_premium(cm.getR0030_premium());
		insurance.setR0031(cm.getR0031());
		insurance.setR0031_premium(cm.getR0031_premium());
		insurance.setR004(cm.getR004());
		insurance.setR004_premium(cm.getR004_premium());
		insurance.setR006(cm.getR006());
		insurance.setR006_type(cm.getR006_type());
		insurance.setR008(cm.getR008());
		insurance.setR008_premium(cm.getR008_premium());
		insurance.setSclar(cm.getSclar());
		insurance.setSclar_premium(cm.getSclar_premium());
		insurance.setNR001(cm.getNR001());
		insurance.setNR002(cm.getNR002());
		insurance.setNR0030(cm.getNR0030());
		insurance.setNR0031(cm.getNR0031());
		insurance.setNR004(cm.getNR004());
		insurance.setNR008(cm.getNR008());
		insurance.setModifyDate(new Date());
		insurance.setCreateDate(new Date());
		return insurance;
	}
	/**
	 * 小数位限制
	 */
	private double getDoubleValue(double d){
		return new Double( new DecimalFormat(".00").format(d));
	}
	@Override
	public Insurance getPremiumDatail(String comCode, String cmType,
			String serialNumber) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("serialNumber","=",serialNumber));
		qbs.add(createQB("carMenuCode","=",cmType));
		qbs.add(createQB("company","=",comCode));
		List<Insurance> list = insuranceDao.findByQBs(qbs, "serialNumber", "desc");
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public void deleteOldPremium(String sql) {
		insuranceDao.deleteOldPremium(sql);
	}
	
}
