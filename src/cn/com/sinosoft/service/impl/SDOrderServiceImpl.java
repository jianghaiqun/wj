package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.action.shop.uw.UsersUWCheck;
import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.dao.SDOrderDao;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.service.SDOrderService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.fDInsCom.FDInsComServiceStub;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorRela;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.CalProductPrem;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service实现类 - 订单
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTDD7076EB801A97081A271D1D0D7AF8F7
 * ============================================================================
 */

@Service
public class SDOrderServiceImpl extends BaseServiceImpl<SDOrder, String> implements SDOrderService {
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	@Resource
	private SDOrderDao sdorderDao;

	@Resource
	public void setBaseDao(SDOrderDao sdorderDao) { 
		super.setBaseDao(sdorderDao);
	}
	
	public String getLastOrderSn() {
		return sdorderDao.getLastOrderSn();
	}
	
	public Pager getOrderPager(Member member, Pager pager) {
		return sdorderDao.getOrderPager(member, pager);
	}
	
//	public Long getUnprocessedOrderCount() {
//		return orderDao.getUnprocessedOrderCount();
//		
//	}
	
//	public Long getPaidUnshippedOrderCount() {
//		return orderDao.getPaidUnshippedOrderCount();
//		
//	}

	// 重写对象，保存时自动设置订单编号
	@Override
	public String save(SDOrder order) {
//		order.setOrderSn(SerialNumberUtil.buildOrderSn());
		return super.save(order);
	}

	@Override
	public Order getLastOrder() {
		return sdorderDao.getLastOrder();
		
	}
	//====吴高强添加开始===============
	/**
	 * 
	 * @param orderSn 订单编号
	 * @param orderStatus 订单状态
	 * @param applicant  投保人
	 * @param hdate     下单止期
	 * @param ldate     下单起期
	 * @param pager    Pager对象
	 * @param member   Member对象
	 * @return  根据条件筛选订单
	 */
	
	public Pager getOrderPager(String orderSn,String orderStatus, String applicant,Date hdate,Date ldate, Pager pager,Member member){
		return sdorderDao.getOrderPager( orderSn, orderStatus, applicant, hdate, ldate,  pager, member);
	}
	public SDOrder getOrderByOrderSn(String orderSn){
		return sdorderDao.getOrderByOrderSn(orderSn);
		
	}
	
	@Override
	public List<SDOrder> getOrderByPaySn(String paySn) {
		return sdorderDao.getOrderByPaySn(paySn);
	}

	//====吴高强添加结束===============
	/**
	 * 产品信息查询
	 */
	@Override
	public Map<String, Object> getProductInformation(String productCode, String BU1 ,String channelCode) {
		String[] riskCode = {productCode};
		List<OrderRiskAppFactor> orafs = new ArrayList<OrderRiskAppFactor>();
		List<OrderDutyFactor> odfs = new ArrayList<OrderDutyFactor>();  
		List<FEMDutyAmntPremList> fdapAll = new ArrayList<FEMDutyAmntPremList>();  
		FEMRiskFactorRela[] ffrelas = null;
		String[] BaseInformation = new String[17];
		Map<String,Object> paramter = new HashMap<String,Object>();
		paramter.put("RiskCode", riskCode);
		paramter.put("BU1", BU1);//判断是否是赠险
		if("02".equals(channelCode)){
			paramter.put("RiskProp", "F");
		}
		try {
			ProductInfoWebServiceStub.ProductInfoResponse prifr = ProductWebservice.ProductInfoSereviceImpl(paramter, null);
			ProductInfoWebServiceStub.FMRisk[] fm= prifr.getFMRisk();
			if(fm.length>0){
				BaseInformation[0] = fm[0].getRiskCode();//产品编码
				BaseInformation[1] = fm[0].getRiskName();//产品名称
				BaseInformation[2] = fm[0].getSupplierCode();//保险公司编码
				BaseInformation[3] = getCompanyName(fm[0].getSupplierCode());//保险公司简称
				BaseInformation[4] = fm[0].getOutRiskCode();//保险公司产品编码
				BaseInformation[5] = fm[0].getRiskKind2();//产品大类
				BaseInformation[6] = fm[0].getInitPrem();//产品初始价格
				BaseInformation[7] = fm[0].getRiskKind3();//产品小类
				BaseInformation[8] = Config.interfaceMap.getString("companyLog") + fm[0].getERiskPicRelaPath();
				BaseInformation[9] = Double.toString(fm[0].getFeeRate());
				BaseInformation[10] = fm[0].getBackUp2();//折扣比率
				BaseInformation[11] = fm[0].getDiscountPrice();//折扣价格
				BaseInformation[12] = fm[0].getBrkKindCode();//险种编码
				BaseInformation[13] = fm[0].getBrkRiskCode();//计划编码
				BaseInformation[14] = fm[0].getAutoRelatedClause();//是否cps产品标识
				BaseInformation[15] = fm[0].getOptionalClause();//cps产品跳转地址
				BaseInformation[16] = fm[0].getRemark();//产品原名称
				if(fm[0].getFEMRiskFactorRela()!=null){
					ffrelas = fm[0].getFEMRiskFactorRela();
				}
				
				if(fm[0].getFERiskAppFactor()!=null&&fm[0].getFERiskAppFactor().length>0){
					for(ProductInfoWebServiceStub.FERiskAppFactor faf : fm[0].getFERiskAppFactor()){
						String isPremCalFacotor ="";
						if(faf.getFEMRiskFactorList()!=null&&faf.getFEMRiskFactorList().length>0){
							isPremCalFacotor = faf.getFEMRiskFactorList()[0].getIsPremCalFacotor();
							List<ProductInfoWebServiceStub.FEMRiskFactorList> femRiskList = new ArrayList<ProductInfoWebServiceStub.FEMRiskFactorList>();
							for(ProductInfoWebServiceStub.FEMRiskFactorList femFactor: faf.getFEMRiskFactorList()){
								femRiskList.add(femFactor);
							}
							OrderRiskAppFactor oraf = new OrderRiskAppFactor();
							String factorType = faf.getFactorType();
							oraf.setFactorTypeName(faf.getFactorTypeName());//投保要素类别名称
							oraf.setProductCode(productCode);//产品编码
							oraf.setIsPremCalFacotor(isPremCalFacotor);//投保要素是否参与保费计算
							oraf.setAppFactorCode(faf.getAppFactorCode());//投保要素编码
							oraf.setFactorType(factorType);//投保要素类型
							oraf.setFactorValue(femRiskList);//投保要素值列表
							oraf.setDataType(faf.getDataType());//投保要素值类型
							orafs.add(oraf);
						}
					}
				}
				if(fm[0].getFMDuty()!=null&&fm[0].getFMDuty().length>0){
					for(ProductInfoWebServiceStub.FMDuty fmd : fm[0].getFMDuty()){
						String dutyCode = fmd.getDutyCode();
						String dutyName = fmd.getDutyName();
						String dutyFullName = fmd.getDutyFullName();
						String dutyDefine = fmd.getDefine();
						if(fmd.getFEMDutyFactor()!=null){
							if(fmd.getFEMDutyFactor().getFEMDutyAmntPremList()!=null){
								OrderDutyFactor odf = new OrderDutyFactor();
								odf.setProductCode(productCode);//产品编码
								List<FEMDutyAmntPremList> fdap = new ArrayList<FEMDutyAmntPremList>(); 
								for(ProductInfoWebServiceStub.FEMDutyAmntPremList fapl : fmd.getFEMDutyFactor().getFEMDutyAmntPremList()){
									fdap.add(fapl);
									if(fapl!=null){
										fdapAll.add(fapl);
									}
								}
								odf.setDudtyFactorName(dutyName);//产品责任/险别名称
								odf.setDutyFullName(dutyFullName);//保险公司责任全称
								odf.setDutyFactorID(fmd.getFEMDutyFactor().getDutyFactorID());//责任投保要素ID
								odf.setDefine(dutyDefine);//责任/险别说明
								odf.setDutyCode(dutyCode);//责任/险别代码
								odf.setDataType(fmd.getFEMDutyFactor().getDataType());//值类型
								odf.setIsRelaRiskFactor(fmd.getFEMDutyFactor().getIsRelaRiskFactor());//是否关联产品级投保要素
								odf.setSupplierDutyCode(fmd.getSupplierDutyCode());//保险公司责任/险别编码
								odf.setIsPremCalFacotor(fmd.getFEMDutyFactor().getIsPremCalFacotor());//投保要素是否用于保费计算
								odf.setFdAmntPremList(fdap);//责任投保要素值数组
								odf.setCurrency(fmd.getCurrency());//主副险标志
								odf.setDutyEnName(fmd.getDutyEnName());//责任英文名
								odf.setEnCoverage(fmd.getEnDefine());//责任英文说明
								odf.setOrderFlag(fmd.getShowOrder());//排序
								odfs.add(odf);
							}
						}
					}
				}
			}
			paramter.put("baseInformation", BaseInformation);
			paramter.put("riskAppFactor", orafs);
			paramter.put("dutyFactor", odfs);
			paramter.put("ffrelas", ffrelas);  
			paramter.put("fdapAll", fdapAll);  
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			paramter.put("error", "1");
		}
		return paramter;
	}


	/**
	 * 
	 * 获得保险公司名称
	 */
	public String getCompanyName(String comCode) {
		Map<String,Object> paramter = new HashMap<String,Object>();
		paramter.put("SupplierCode", comCode);
		try {
			FDInsComServiceStub.FDInsComResponse comIformation= ProductWebservice.FDInsComServiceImpl(paramter, null);
			if(comIformation!=null&&comIformation.getFDInsComList()!=null&&comIformation.getFDInsComList().length>0&&comIformation.getFDInsComList()[0]!=null){
				return comIformation.getFDInsComList()[0].getShortName();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "";
	}
	/**
	 * 获得产品保费（废弃）
	 */
	@Override
	public Map<String,Object> getProductPrem(Map<String, Object> mp) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> paramter = new HashMap<String,Object>();
		String[] baseInformation = (String[]) mp.get("baseInformation");
		List<OrderRiskAppFactor> orafs = (List<OrderRiskAppFactor>) mp.get("riskAppFactor");
		List<OrderRiskAppFactor> trailOrafs = new ArrayList<OrderRiskAppFactor>();
		List<OrderDutyFactor> odfs = (List<OrderDutyFactor>) mp.get("dutyFactor");
		List<OrderDutyFactor> tialOdfs = new ArrayList<OrderDutyFactor>();
		String productCode = baseInformation[0];
		String effective = mp.get("effective")+"";
		if(StringUtil.isNotEmpty(effective)){
			try {
				effective = sf.format(sf.parse(effective));
			} catch (ParseException e) {
				Long d = new Date().getTime()+1000*60*60*24;
				effective = sf.format(d);
				logger.error(e.getMessage(), e);
			}
		}else{
			Long d = new Date().getTime()+1000*60*60*24;
			effective = sf.format(d);
		}
		if(orafs!=null&&orafs.size()>0){
			for(OrderRiskAppFactor oraf : orafs){
				if(oraf!=null&&oraf.getFactorValue()!=null&&oraf.getFactorValue().get(0).getFactorValue()!=null){
					trailOrafs.add(oraf);
				}
			}
		}
		if(odfs!=null&&odfs.size()>0){
			for(OrderDutyFactor odf : odfs){
				if(odf!=null&&odf.getFdAmntPremList()!=null && "Y".equals(odf.getIsPremCalFacotor()) && odf.getFdAmntPremList().get(0).getBackUp1()!=null){
					tialOdfs.add(odf);
				}
			}
		}
		ProductPremServiceStub.FEMRiskFactorList[] frafactor = new ProductPremServiceStub.FEMRiskFactorList[trailOrafs.size()];
		ProductPremServiceStub.CalProductPrem[] calPPrems = null;
		if(trailOrafs!=null&&trailOrafs.size()>0){
			for(int i=0;i<trailOrafs.size();i++){
				frafactor[i] = getFEMRiskFactorList(trailOrafs.get(i));
			}
		}
		if(tialOdfs!=null&&tialOdfs.size()>0){
			calPPrems = new ProductPremServiceStub.CalProductPrem[tialOdfs.size()];
			for(int i = 0;i<tialOdfs.size();i++){
				calPPrems[i] = getCalProductPrem(tialOdfs.get(i),frafactor,productCode,effective);
			}
		}else{
			calPPrems = new ProductPremServiceStub.CalProductPrem[1];
			calPPrems[0] = getCalProductPrem(null,frafactor,productCode,effective);
		}
		paramter.put("CalProductPrem", calPPrems);
		ProductPremServiceStub.ProductPremResponse ppr;
		double totlePrem = 0.0;
		double feeRate = 0.0;
		double countPrice = 0.0;
		try {
			ppr = ProductWebservice.ProductPremSereviceImpl(paramter, null);
			if(ppr.getTotalPrice()!=null && !"".equals(ppr.getTotalPrice())){
				totlePrem = Double.parseDouble(ppr.getTotalPrice());
			}
			if(ppr.getFeeRate().length>0){
				for(double d : ppr.getFeeRate()){
					feeRate = feeRate + d;
				}
			}
			if(ppr.getDiscountTotalPrice()!=null && !"".equals(ppr.getDiscountTotalPrice())){
				countPrice = Double.parseDouble(ppr.getDiscountTotalPrice());
			}
			map.put("totlePrem", totlePrem);
			map.put("feeRate", feeRate);
			map.put("countPrice", countPrice);//折扣保费
			map.put("dutyAmounts", ppr.getDutyAmount());//责任费用明细
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 获得责任费用明细
	 */
	@Override
	public Map<String,Object> getProductPremDutyAmounts(Map<String, Object> mp) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> paramter = new HashMap<String,Object>();
		String[] baseInformation = (String[]) mp.get("baseInformation");
		List<OrderRiskAppFactor> orafs = (List<OrderRiskAppFactor>) mp.get("riskAppFactor");
		List<OrderRiskAppFactor> trailOrafs = new ArrayList<OrderRiskAppFactor>();
		List<OrderDutyFactor> odfs = (List<OrderDutyFactor>) mp.get("dutyFactor");
		List<OrderDutyFactor> tialOdfs = new ArrayList<OrderDutyFactor>();
		String effective = mp.get("effective")+"";
		String complicatedFlag = (String)mp.get("complicatedFlag");
		if(StringUtil.isNotEmpty(effective)){
			try {
				effective = sf.format(sf.parse(effective));
			} catch (ParseException e) {
				Long d = new Date().getTime()+1000*60*60*24;
				effective = sf.format(d);
				logger.error(e.getMessage(), e);
			}
		}else{
			Long d = new Date().getTime()+1000*60*60*24;
			effective = sf.format(d);
		}
		String productCode = baseInformation[0];
		if(orafs!=null&&orafs.size()>0){
			for(OrderRiskAppFactor oraf : orafs){
				if(oraf!=null&&oraf.getFactorValue()!=null&&oraf.getFactorValue().get(0).getFactorValue()!=null){
					trailOrafs.add(oraf);
				}
			}
		}
		if(odfs!=null&&odfs.size()>0){
			if ("Y".equalsIgnoreCase(complicatedFlag)) {
				for(OrderDutyFactor odf : odfs){
					tialOdfs.add(odf);
				}
			} else {
				for(OrderDutyFactor odf : odfs){
					if(odf!=null&&odf.getFdAmntPremList()!=null&&odf.getFdAmntPremList().get(0).getBackUp1()!=null&&"Y".equals(odf.getIsPremCalFacotor())){
						tialOdfs.add(odf);
					}
				}
			}
			
		}
		ProductPremServiceStub.FEMRiskFactorList[] frafactor = new ProductPremServiceStub.FEMRiskFactorList[trailOrafs.size()];
		ProductPremServiceStub.CalProductPrem[] calPPrems = null;
		if(trailOrafs!=null&&trailOrafs.size()>0){
			for(int i=0;i<trailOrafs.size();i++){
				frafactor[i] = getFEMRiskFactorList(trailOrafs.get(i));
			}
		}
		if(tialOdfs!=null&&tialOdfs.size()>0){
			calPPrems = new ProductPremServiceStub.CalProductPrem[tialOdfs.size()];
			for(int i = 0;i<tialOdfs.size();i++){
				calPPrems[i] = getCalProductPrem(tialOdfs.get(i),frafactor,productCode,effective);
			}
		}else{
			calPPrems = new ProductPremServiceStub.CalProductPrem[1];
			calPPrems[0] = getCalProductPrem(null,frafactor,productCode,effective);
		}
		paramter.put("CalProductPrem", calPPrems);
		ProductPremServiceStub.ProductPremResponse ppr;
		double totlePrem = 0.0;
		double feeRate = 0.0;
		double countPrice = 0.0;
		try {
			ppr = ProductWebservice.ProductPremSereviceImpl(paramter, null);
			if (ppr != null && ppr.getResultDTO() != null) {
                if ("2".equals(ppr.getResultDTO().getResultCode())) {
                	map.put("errMessage", ppr.getResultDTO().getResultInfoDesc());
                	return map;
                } else if ("3".equals(ppr.getResultDTO().getResultCode())) {
                	map.put("warnMessage", ppr.getResultDTO().getResultInfoDesc());
                }
			}
			if(ppr.getTotalPrice()!=null && !"".equals(ppr.getTotalPrice())){
				totlePrem = Double.parseDouble(ppr.getTotalPrice());
			}
			if(ppr.getFeeRate().length>0){
				for(double d : ppr.getFeeRate()){
					feeRate = feeRate + d;
				}
			}
			if(ppr.getDiscountTotalPrice()!=null && !"".equals(ppr.getDiscountTotalPrice())){
				countPrice = Double.parseDouble(ppr.getDiscountTotalPrice());
			}
			if(ppr.getDiscountRate()!=null && ppr.getDiscountRate().length>0){
				map.put("DiscountRate", ppr.getDiscountRate()[0]+"");//折扣率
			}else{
				map.put("DiscountRate", "");//折扣率
			}
			map.put("totlePrem", totlePrem);
			map.put("feeRate", feeRate);
			map.put("countPrice", countPrice);//折扣保费
			map.put("dutyAmounts", ppr.getDutyAmount());//责任费用明细
			
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	/**
     * 
     * 组装保费计算条件
     */
	private CalProductPrem getCalProductPrem(OrderDutyFactor odf,
			FEMRiskFactorList[] frafactor, String productCode,String effective) {
		CalProductPrem cpp = new CalProductPrem();
		cpp.setRiskCode(productCode);
		if(odf!=null){
			cpp.setAmnt(odf.getFdAmntPremList().get(0).getBackUp1());
			cpp.setDutyCode(odf.getDutyCode());
		}
		cpp.setFEMRiskFactorList(frafactor);
		String ss = effective + " 00:00:00";
		cpp.setInsureDate(ss);
		return cpp;
	}
    /**
     * 
     * 组装产品投保要素
     */
	private FEMRiskFactorList getFEMRiskFactorList(
			OrderRiskAppFactor oraf) {
		ProductPremServiceStub.FEMRiskFactorList femflist = new  ProductPremServiceStub.FEMRiskFactorList();
		femflist.setAppFactorCode(oraf.getAppFactorCode());
		femflist.setFactorType(oraf.getFactorType());
		femflist.setFactorValue(oraf.getFactorValue().get(0).getFactorValue());
		femflist.setIsPremCalFacotor(oraf.getIsPremCalFacotor());
		return femflist;
	}

	@Override
	public OrderDutyFactor getNewDutyFactor(OrderDutyFactor df , String dutyValueTemp) {
		OrderDutyFactor odf = new OrderDutyFactor();
		if(df!=null){
			odf.setProductCode(df.getProductCode());
			odf.setDutyCode(df.getDutyCode());
			odf.setDudtyFactorName(df.getDudtyFactorName());
			odf.setDutyFactorID(df.getDutyFactorID());
			odf.setDefine(df.getDefine());
			odf.setIsPremCalFacotor(df.getIsPremCalFacotor());
			odf.setIsRelaRiskFactor(df.getIsRelaRiskFactor());
			odf.setAppFactorCode(df.getAppFactorCode());
			odf.setDataType(df.getDataType());
			odf.setCurrency(df.getCurrency());
			odf.setSupplierDutyCode(df.getSupplierDutyCode());
			List<FEMDutyAmntPremList> fdAmntPremList = new ArrayList<FEMDutyAmntPremList>();
			if(df.getFdAmntPremList()!=null && df.getFdAmntPremList().size()>0){
				for(FEMDutyAmntPremList f : df.getFdAmntPremList()){
					if(dutyValueTemp.equals(f.getBackUp1())){
						FEMDutyAmntPremList fEMDutyAmntPremList = new FEMDutyAmntPremList();
						fEMDutyAmntPremList.setAmnt(f.getAmnt());
						fEMDutyAmntPremList.setBackUp1(f.getBackUp1());
						fEMDutyAmntPremList.setPayPeriod(f.getPayPeriod());
						fEMDutyAmntPremList.setPrem(f.getPrem());
						fEMDutyAmntPremList.setDutyFactorID(f.getDutyFactorID());
						fEMDutyAmntPremList.setAmntPremListID(f.getAmntPremListID());
						fEMDutyAmntPremList.setAppFactorCode(f.getAppFactorCode());
						fEMDutyAmntPremList.setAppFactorValue(f.getAppFactorValue());
						fdAmntPremList.add(fEMDutyAmntPremList);
					}
				}
			}
			odf.setFdAmntPremList(fdAmntPremList);
		}
		return odf;
	}

	@Override
	public String getJRHSURLByConfig(String channelCode) {
		if(StringUtil.isNotEmpty(channelCode)){
			DataTable dt = new QueryBuilder("select jsURl from ZDCooperationConfig where channelCode=?",channelCode).executeDataTable();
			if(dt.getRowCount()>0){
				return dt.getString(0, 0);
			}
		} 
		return "";
	}
	/**
	 * 
	 * 支付后核保
	 * 返回失败单被保人编号集合
	 */
	@Override
	public List<String> checkUnderwriting(SDOrder sorder,HttpServletRequest request) {
		List<String> list = new ArrayList<String>();
		String orderSn = sorder.getOrderSn();
		try {
			
			String comCode = new QueryBuilder("select insuranceCompany from sdinformation where orderSn=?", sorder.getOrderSn()).executeString();
			if(StringUtil.isNotEmpty(comCode)&&StringUtil.isNotEmpty(orderSn)){
				String sql = "select prop2 from zdcode where CodeType='UWCheckClassName' and ParentCode='UWCheckClassName' and CodeValue=?";
				DataTable dt = new QueryBuilder(sql,comCode).executeDataTable();
				String flag = "";
				if(dt.getRowCount()>0){
					flag = dt.getString(0, 0);
				}
				if("Y".equals(flag)){
					UsersUWCheck.moreUWCheck(orderSn);
					String querySQL = "SELECT orderSn,insuredSn,insuredName,rtnMessage FROM UWCheckLog WHERE orderSn = ? and passFlag =?";
					DataTable dt1 = new QueryBuilder(querySQL,orderSn,"0").executeDataTable();
					if(dt1.getRowCount()>0){
						for(int i = 0 ; i < dt1.getRowCount(); i++){
							list.add(dt1.getString(i, 1));
							sendErrorMail(orderSn, dt1.getString(i, 3),dt1.getString(i, 1),request);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			sendErrorMail(orderSn, "订单核保时发生异常","",request);
		}
		return list;
	}

	/**
	 * 
	 * 自动出单的核保
	 * 返回失败单被保人编号集合
	 */
	@Override
	public List<String> checkUnderwriting(String insuranceCompany, String orderSn, HttpServletRequest request) {
		List<String> list = new ArrayList<String>();
		try {
			
			String sql = "select count(1) from zdcode where CodeType='UWCheckClassName' and ParentCode='UWCheckClassName' and CodeValue=?";

			if(new QueryBuilder(sql,insuranceCompany).executeInt()>0){
				UsersUWCheck.moreUWCheck(orderSn);
				String querySQL = "SELECT orderSn,insuredSn,insuredName,rtnMessage FROM UWCheckLog WHERE orderSn = ? and passFlag =?";
				DataTable dt1 = new QueryBuilder(querySQL,orderSn,"0").executeDataTable();
				if(dt1.getRowCount()>0){
					for(int i = 0 ; i < dt1.getRowCount(); i++){
						list.add(dt1.getString(i, 1));
						sendErrorMail(orderSn, dt1.getString(i, 3),dt1.getString(i, 1),request);
					}
				}
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			sendErrorMail(orderSn, "订单核保时发生异常","",request);
		}
		return list;
	}
	
	@Override
	public void sendErrorMail(String orderSn, String errMsg, String insuredSn,
			HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String email = Config.getValue("InsureErrorMail");
			if(StringUtil.isNotEmpty(email)){
				String sql_paysn = "SELECT paysn FROM sdorders a WHERE a.ordersn = '"+orderSn+"'";
				DataTable dt_paysn = new QueryBuilder(sql_paysn).executeDataTable();
				String PaySn= dt_paysn.getString(0, 0);
				if(StringUtil.isNotEmpty(insuredSn)){
					map.put("insuredSn", insuredSn);
				}else{
					map.put("paySn", PaySn);
					map.put("orderSn", orderSn);
				}
				map.put("errmsg", errMsg);
				map.put("receiver", email);
				ActionUtil.sendMessage("wj00031", map);
			}
			ActionUtil.dealAction("wj00031", map, request);
		} catch (Exception e) {
			logger.error("出现异常:" + "sendErrorMail方法出现异常" + e.getMessage(), e);
		}
	}

	@Override
	public void sendErrorMailByPaySn(String paySn, String errMsg,
			String insuredSn, HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String email = Config.getValue("InsureErrorMail");
			if(StringUtil.isNotEmpty(email)){
				map.put("paySn", paySn);
				map.put("errmsg", errMsg);
				map.put("receiver", email);
				ActionUtil.sendMessage("wj00031", map);
			}
			ActionUtil.dealAction("wj00031", map, request);
		} catch (Exception e) {
			logger.error("出现异常:" + "sendErrorMail方法出现异常" + e.getMessage(), e);
		}
		
	}

	@Override
	public String getUWFlagByPaySn(String paySn) {
		String uwFlag = "";
		String paySnFlag = paySn.substring(paySn.length()-1,paySn.length());
		if("D".equals(paySnFlag)){
			List<SDOrder> list = getOrderByPaySn(PubFun.getPaySnUnion(paySn));
			if(list!=null && list.size()==1){
				SDOrder sorder = list.get(0);
				String sql = "select prop1 from zdcode a,sdorders b,sdinformation c where b.ordersn = c.ordersn and a.CodeType='UWCheckClassName' and a.ParentCode='UWCheckClassName' and CodeValue=c.insuranceCompany and b.ordersn=?";
				DataTable dt = new QueryBuilder(sql,sorder.getOrderSn()).executeDataTable();
				String flag = "";
				if(dt.getRowCount()>0){
					flag = dt.getString(0, 0);
					if(StringUtil.isEmpty(flag)||!"N".equals(flag)){
						uwFlag = "1";
					}
				}
			}
		}
		return uwFlag;
	}

	public static void main(String[] args){
		SDOrderServiceImpl ors = new SDOrderServiceImpl();
		ors.getProductInformation("200701102","N","");
	}
	
	public Order getOrderById(String orderId){
		return sdorderDao.getOrderById(orderId);
		
	}
	
	/**
	 * 
	 * 根据订单号查出订单
	 */
	@Override
	public List<SDOrder> getOrderByOrderSns(String OrderSns) {
		return sdorderDao.getOrderByOrderSns(OrderSns);
	}
}