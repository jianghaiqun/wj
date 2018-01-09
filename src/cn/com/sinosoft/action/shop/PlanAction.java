package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.service.impl.OrderConfigNewServiceImpl;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.product.PremCalculate;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyFactor;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FERiskAppFactor;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMDuty;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.ProductInfoResponse;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.CalProductPrem;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.ProductPremResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 前台Action类 - 订单处理
 * ============================================================================
 * 
 * KEY:SINOSOFT66590FE3DF97C81824D62C168A48301D
 * ============================================================================
 */

@ParentPackage("shop")
public class PlanAction extends BaseShopAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//前台传入参数
	private String productId;//产品编码
	private String plan;//计划
	private String textAge;// 投保年龄
	private String period;//保险期间
	//产品信息
	private FMRisk[] fm=null;
	//private FEMRiskFactorList[] mPlanList=null;
	private List<FEMRiskFactorList[]> mNewPlanList=null;
	//private FMDuty[] fMDuty = null;
	private List<FMDuty[]> newFMDuty = null;
    private Map<String, String> planPrice = new HashMap<String, String>();		//各种计划保费
    private Map<String, String> htmlResult = new HashMap<String, String>();//结果html
    private String[] mRiskCode = null;
    private boolean SplitRiskCode = false;
    private String complicatedFlag;
    
    /**
     * 计划对比 生成HTML 返回
     * */
	public String planCompare() {
		
		try {
			//System.out.println("产品编码："+productId+"计划："+plan+"投保年龄："+textAge+"保险期间："+period);
			//产品信息
			mRiskCode = PremCalculate.getProS(productId);
			if(mRiskCode.length>=2){
				SplitRiskCode = true;
			}
			if (StringUtil.isNotEmpty(plan)) {
				plan = java.net.URLDecoder.decode(plan, "utf-8");
			}
			fm=getProductInformation(productId, "N");
			if(fm==null) return null;//如果得到产品信息失败直接返回空
			//得到计划列表
			this.period=PremCalculate.getPeriod(this.period,this.productId);
			mNewPlanList=getPlanList(fm);
			//得到责任信息
			newFMDuty=getDutyList(fm);
			//每种计划的价格
			String memberId = "";
			Member member = getLoginMember();
			if (member != null) {
				memberId = member.getId();
			}
			planPrice=getPlanPrice(memberId);
			//得到要返回的HTML
			String resHtml=getHtml();
			htmlResult.put("result", resHtml);
			return ajaxJson(htmlResult);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 得到要显示的html
	 * @author fhz
	 * */
	private String getHtml() {
		StringBuffer  premHtml=new StringBuffer();
		String productname="";
		for (int m = 0; m < fm.length; m++) {
			if (productId.equals(fm[m].getRiskCode())) {
				productname = fm[m].getRiskName();
				break;
			}
		}
		
		String insuranceCode = productId.substring(0 ,4);
		
		premHtml.append(" <div class=\"plan_box\" id=\"PlanCompare\"> ");
		//premHtml.append(" <h2><span class=\"logo AL_001\">"+fm[0].getRiskName()+"</span></h2>");	
		premHtml.append(" <h2><span class=\"icon_C" + insuranceCode  + " m-list-logo\"></span><span>"+productname+"</span></h2>");
		premHtml.append("<table width=\"100%\" border=\"1\" id=\"crossTable\">");//table 开始
		//根据计划列表得到计划，价格，计划选择html
		Map<String,String>  dynamicHtmlMap =getPlanInfoHtml();
		premHtml.append(dynamicHtmlMap.get("planColHtml"));//得到计划列
		premHtml.append(dynamicHtmlMap.get("priceColHtml"));//得到产品价格列
		//责任描述块开始
		premHtml.append("<tr><td  class=\"light_bg\"></td>");
		premHtml.append(dynamicHtmlMap.get("selectPlanHtml"));		
		premHtml.append("</tr>");
		//责任描述块结束
		//得到各种计划的各种责任的各种保额  开始
		premHtml.append(getPlanDutyHtml());
		//得到底部价格行
		//premHtml.append(dynamicHtmlMap.get("priceBottomHtml"));
		//得到底部选择行
		premHtml.append("<tr><td></td>");
		premHtml.append(dynamicHtmlMap.get("selectPlanHtml"));
		premHtml.append("</tr>");
		premHtml.append("</table></div>");
		//System.out.println(premHtml.toString());
		return premHtml.toString();
	}

	
	
	/**
	 * 得到产品责任明细与各计划保额
	 * **/
	private String getPlanDutyHtml() {
		StringBuffer  planDutyHtml=new StringBuffer();
		// 循环责任
		for (FMDuty duty : newFMDuty.get(0)) {
			FEMDutyFactor dutyFactor = duty.getFEMDutyFactor();
			planDutyHtml.append("<tr>");
			planDutyHtml
					.append("<td  class=\"light_bg\"><div class=\"head_td\">"
							+ duty.getDutyName() + "</div></td> ");
			if (dutyFactor != null) {
				FEMDutyAmntPremList[] amntPremValues = dutyFactor
						.getFEMDutyAmntPremList();
				int tPlanLen = mNewPlanList.size();
				FEMRiskFactorList[] mPlanList = null;
				Map<String, String> infoMap = new HashMap<String, String>();
				// infoMap.put("riskcode", "N");
				for (int pl = 0; pl < tPlanLen; pl++) {
					mPlanList = mNewPlanList.get(pl);
					// 外层还是统一循环计划列表，保持内容一致
					for (int i = 0; i < mPlanList.length; i++) {
						boolean exsitsFlag = false;
						String planRiskCode = mPlanList[i].getRiskCode();
						if (!planRiskCode.equals(dutyFactor.getRiskCode())
								&& !"Y".equals(infoMap.get(planRiskCode))) {
							dutyFactor = getDutyFactorList(planRiskCode,
									mPlanList[i].getFactorValue(), duty);
							if (dutyFactor != null) {
								amntPremValues = dutyFactor
										.getFEMDutyAmntPremList();
							}
						}
						if ("Y".equals(complicatedFlag)) {
							String amnt = "";
							if ("Y".equals(dutyFactor.getIsRelaRiskFactor())) {
								for (int j = 0; j < amntPremValues.length; j++) {
									if (mPlanList[i]
											.getFactorValue()
											.equalsIgnoreCase(
													amntPremValues[j]
															.getAppFactorValue())) {
										if ("".equals(amnt)) {
											amnt = amntPremValues[j].getAmnt();
										}
										if ("Y".equals(amntPremValues[j]
												.getBackUp2())) {
											amnt = amntPremValues[j].getAmnt();
										}
									}
								}
								if ("".equals(amnt)) {
									amnt = "-";
								}
							} else {
								amnt = amntPremValues[0].getAmnt();
								for (int j = 0; j < amntPremValues.length; j++) {
									if ("Y".equals(amntPremValues[j]
											.getBackUp2())) {
										amnt = amntPremValues[j].getAmnt();
									}
								}
							}
							// 判断是否高亮列
							if (plan != null
									&& mPlanList[i].getFactorValue().equals(
											plan)
									&& productId.equals(mPlanList[i]
											.getRiskCode())) {
								planDutyHtml
										.append("<td class=\"hilisgt_plan\">  ");
							} else {
								planDutyHtml.append("<td>");
							}
							planDutyHtml.append(amnt + "</td>");

						} else {
							for (int j = 0; j < amntPremValues.length; j++) {
								if (amntPremValues != null
										&& amntPremValues[j] != null
										&& mPlanList[i] != null
										&& mPlanList[i].getFactorValue() != null
										&& amntPremValues[j]
												.getAppFactorValue() != null
										&& mPlanList[i]
												.getFactorValue()
												.equalsIgnoreCase(
														amntPremValues[j]
																.getAppFactorValue())) {
									// 判断是否高亮列
									if (plan != null
											&& mPlanList[i].getFactorValue()
													.equals(plan)
											&& productId.equals(mPlanList[i]
													.getRiskCode())) {
										planDutyHtml
												.append("<td class=\"hilisgt_plan\">  ");
									} else {
										planDutyHtml.append("<td>");
									}

									planDutyHtml.append(amntPremValues[j]
											.getAmnt() + "</td>");
									exsitsFlag = true;
									break;
								}
							}

							// 如果循环结束语还没有找到匹配的计划，那么输出默认"-"
							if (!exsitsFlag) {
								if (plan != null
										&& mPlanList[i].getFactorValue()
												.equals(plan)) {
									planDutyHtml
											.append("<td class=\"hilisgt_plan\">-</td> ");
								} else {
									planDutyHtml.append("<td>-</td> ");
								}
							}
							infoMap.put(planRiskCode, "Y");
						}
					}
				}

			} else {
				planDutyHtml.append("<td>-</td> ");
			}
			planDutyHtml.append("</tr>");
		}
		
		//System.out.println(planDutyHtml.toString());
		return planDutyHtml.toString();
	}
	private FEMDutyFactor getDutyFactorList(String planRiskCode,String factorCode,FMDuty duty){
		int dutyLen = newFMDuty.size();
		for(int kk=0;kk<dutyLen;kk++){
			FMDuty[] otherDuty = newFMDuty.get(kk);
			int otherLen = otherDuty.length;
			for(int ii=0;ii<otherLen;ii++){
				if(duty.getDutyCode().replace(duty.getFEMDutyFactor().getRiskCode(), planRiskCode).equals(otherDuty[ii].getDutyCode())){
					FEMDutyFactor otherDutyFactor = otherDuty[ii].getFEMDutyFactor();
					return otherDutyFactor;
				}
			}
		}
		FEMDutyFactor otherDutyFactor =duty.getFEMDutyFactor();
		return otherDutyFactor;
	}
	/**
    * 得到产品的计划 将方法整合减少循环次数，
    * 循环计划列表得到了计划名称行，价格行，和选择计划行的代码块
    * @author fhz
    * **/
	private Map<String,String> getPlanInfoHtml() {
		
		Map<String,String> planInfoHtmlMap = new HashMap<String,String>();
		//计划列表头
		StringBuffer  planColHtml=new StringBuffer("<tr><td width=\"170\" class=\"light_bg\"><div class=\"head_td\">计划名称</div></td>");
		//价格列表头
    	StringBuffer  priceColHtml=new StringBuffer("<tr><td class=\"light_bg\"><div class=\"head_td\">产品价格</div></td>");
    	//选择计划代码块
    	StringBuffer  selectPlanHtml=new StringBuffer();
    	StringBuffer priceBottomHtml=new StringBuffer();
		//循环计划
		mNewPlanList.get(0)[0].getRiskCode();
		int tPlanLen=mNewPlanList.size();
		FEMRiskFactorList[] mPlanList=null;
		for(int pl=0;pl<tPlanLen;pl++){
			mPlanList = mNewPlanList.get(pl);
			for (int i = 0; i < mPlanList.length; i++) {
				 //如果是选中计划则初始化时高亮显示
				if (mPlanList[i].getFactorValue() != null && plan != null
						&& mPlanList[i].getFactorValue().equals(plan)
						&& productId.equals(mPlanList[i].getRiskCode())) {
					 planColHtml.append("<td  class=\"hilisgt_plan\">");//计划列表开始
					 priceColHtml.append("<td  class=\"hilisgt_plan\">");//价格列表开始
					 selectPlanHtml.append("<td class=\"hilisgt_plan\">");//选择列表开始
				 }
				 else{
					 planColHtml.append("<td>");
					 priceColHtml.append("<td>");
					 selectPlanHtml.append("<td>");
				 }
				 planColHtml.append(mPlanList[i].getFactorDisplayValue()+"</td>");
				 priceColHtml.append("￥"+planPrice.get(mPlanList[i].getRiskCode() + "-" + mPlanList[i].getFactorValue())+"</td>");
				 String htmlpath = "javascript:void(0);";
				 if(SplitRiskCode){
					 QueryBuilder qb = new QueryBuilder("select HtmlPath from sdproduct where ProductID=?",mPlanList[i].getRiskCode());
					 htmlpath = qb.executeString();
				 }
				 int kk = 0;
				 if(tPlanLen>=2){
					 kk = pl;
				 }else{
					 kk = i;
				 }
				 selectPlanHtml.append("<a href="+htmlpath+"  id=\"Plan_"+kk+"\" class=\"select_plan\" name=\""+mPlanList[i].getFactorValue()+"\" >选择计划</a></td>");
			 }
		}
			 planColHtml.append("</tr>");//计划列表结束
			 
			 //计划对比底部 价格列表，最后一列不同
			 priceBottomHtml.append(priceColHtml);
			 priceBottomHtml.append("</tr>");
			 priceColHtml.append("</tr>"); //价格列表结束
			//装入MAP 返回
			planInfoHtmlMap.put("planColHtml", planColHtml.toString());
			planInfoHtmlMap.put("priceColHtml", priceColHtml.toString());
			planInfoHtmlMap.put("selectPlanHtml", selectPlanHtml.toString());
			planInfoHtmlMap.put("priceBottomHtml", priceBottomHtml.toString());
		 
		return planInfoHtmlMap;
	}


	/**
	 * 得到各种计划价格
	 * @author fhz
	 * @throws Exception 
	 * */
	private  Map<String, String> getPlanPrice(String memberId) {
		Map<String,String> result = new HashMap<String,String>();
		ProductPremResponse ProductPrem;
		
		String tRiskCode[] = mRiskCode;
		int tLen = tRiskCode.length;
		if ("Y".equals(complicatedFlag)) {
			Map<String, String> appFactorMap = new HashMap<String, String>();
			appFactorMap=getAppFactorMap();
			if (tLen > 1) {
				for(int t=0;t<tLen;t++){
					int tPlanLen=mNewPlanList.size();
					FEMRiskFactorList[] mPlanList=null;
					for(int pl=0;pl<tPlanLen;pl++){
						if(tRiskCode[t].equals(mNewPlanList.get(pl)[0].getRiskCode())){
							mPlanList = mNewPlanList.get(pl);
						}
						
					}
					FMRisk fmrisk = null;
					for (int m = 0; m < fm.length; m++) {
						if (tRiskCode[t].equals(fm[m].getRiskCode())) {
							fmrisk = fm[m];
						}
					}
					
					//根据产品编码，原价计算网站活动折后保费
					String activityPriceNew = ActivityCalculate.ProductCalculate(tRiskCode[t],"",fmrisk.getInitPrem(), "wj", memberId);
					result.put(tRiskCode[t] + "-" + mPlanList[0].getFactorValue(), activityPriceNew);
				}
			} else {
				FEMRiskFactorList[] mPlanList = mNewPlanList.get(0);
				CalProductPrem[] calProductPrem = null;
				for(int pl=0;pl<mPlanList.length;pl++){
					calProductPrem = null;
					com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList[] 
							fEMRiskFactorList = 
							new com.sinosoft.webservice.productPrem.ProductPremServiceStub.
							FEMRiskFactorList[appFactorMap.size()];
					Iterator<String> itr = appFactorMap.keySet().iterator();
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
						fEMRiskFactorList[iCount].setFactorValue(appFactorMap.get(key));
						if ("TextAge".equals(fEMRiskFactorList[iCount].getFactorType())) {
							String age = appFactorMap.get(key);
							if (age != null && !"".equals(age) && age.indexOf("Y") >= 0) {
								String birthday = new OrderConfigNewServiceImpl().getBrithdayByFactor(null,age);
								fEMRiskFactorList[iCount].setFactorValue(birthday);
							}
						}
						if ("Plan".equals(fEMRiskFactorList[iCount].getFactorType())) {
								fEMRiskFactorList[iCount].setFactorValue(mPlanList[pl].getFactorValue());
						}
						fEMRiskFactorList[iCount].setIsPremCalFacotor("Y");
						iCount++;
					}
					
					List<CalProductPrem> list = new ArrayList<CalProductPrem>();
					for (int i = 0; i < newFMDuty.get(0).length; i++) {
						FMDuty duty = newFMDuty.get(0)[i];
						CalProductPrem cal = new CalProductPrem();
						cal.setRiskCode(tRiskCode[0]);
						cal.setDutyCode(duty.getDutyCode());
						
						FEMDutyFactor dutyFactor = duty.getFEMDutyFactor();
						if (dutyFactor != null) {
							FEMDutyAmntPremList[] amntPremValues = dutyFactor
									.getFEMDutyAmntPremList();
							String amnt = "";
							if ("Y".equals(dutyFactor.getIsRelaRiskFactor())) {
								for (int j = 0; j < amntPremValues.length; j++) {
									if (mPlanList[pl]
											.getFactorValue()
											.equalsIgnoreCase(
													amntPremValues[j]
															.getAppFactorValue())) {
										if ("".equals(amnt)) {
											amnt = amntPremValues[j].getBackUp1();
										}
										if ("Y".equals(amntPremValues[j]
												.getBackUp2())) {
											amnt = amntPremValues[j].getBackUp1();
										}
									}
								}
							} else {
								amnt = amntPremValues[0].getAmnt();
								for (int j = 0; j < amntPremValues.length; j++) {
									if ("Y".equals(amntPremValues[j]
											.getBackUp2())) {
										amnt = amntPremValues[j].getBackUp1();
									}
								}
							}
							if (!"".equals(amnt) && !"0".equals(amnt)) {
								cal.setAmnt(amnt);
								cal.setFEMRiskFactorList(fEMRiskFactorList);
								list.add(cal);
							}
							
						}
						
					}
					
					if (list.size() > 0) {
						calProductPrem = new CalProductPrem[list.size()];
						for (int i = 0; i < list.size(); i++) {
							calProductPrem[i] = list.get(i);
						}
						Map<String, Object> mp = new HashMap<String, Object>();
						mp.put("CalProductPrem", calProductPrem);
						try {
							ProductPrem = ProductWebservice.ProductPremSereviceImpl(mp, null);
							//根据产品编码，原价计算网站活动折后保费
							String activityPriceNew = ActivityCalculate.ProductCalculate(tRiskCode[0],"", ProductPrem.getDiscountTotalPrice(), "wj", memberId);
							result.put(tRiskCode[0] + "-" + mPlanList[pl].getFactorValue(), activityPriceNew);

						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					}
					
				}
			}
			
		} else {
			Map<String, Map<String, String>> appFactorMapsimple = new HashMap<String, Map<String, String>>();
			appFactorMapsimple=getAppFactorMapSimple();
			for(int t=0;t<tLen;t++){
				mNewPlanList.get(0)[0].getRiskCode();
				int tPlanLen=mNewPlanList.size();
				FEMRiskFactorList[] mPlanList=null;
				for(int pl=0;pl<tPlanLen;pl++){
					if(tRiskCode[t].equals(mNewPlanList.get(pl)[0].getRiskCode())){
						mPlanList = mNewPlanList.get(pl);
					}
				}
				String riskcode = tRiskCode[t];
				Map<String, String> appFactorMap = new HashMap<String, String>();
				appFactorMap=appFactorMapsimple.get(riskcode);
				//为了提高效率这里只调用一次保费计算接口，如果每个计划都调用太慢了
				CalProductPrem[] calProductPrem = new CalProductPrem[mPlanList.length];
				 for (int i = 0; i < mPlanList.length; i++) {
						calProductPrem[i] = new CalProductPrem();
						calProductPrem[i].setRiskCode(riskcode);
						com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList[] 
								fEMRiskFactorList = 
								new com.sinosoft.webservice.productPrem.ProductPremServiceStub.
								FEMRiskFactorList[appFactorMap.size()];
						Iterator<String> itr = appFactorMap.keySet().iterator();
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
							fEMRiskFactorList[iCount].setFactorValue(appFactorMap.get(key));
							if ("TextAge".equals(fEMRiskFactorList[iCount].getFactorType())) {
								String age = appFactorMap.get(key);
								if (age != null && !"".equals(age) && age.indexOf("Y") >= 0) {
	//								age = age.substring(0, age.indexOf("Y"));
	//								if ("0".equals(age)) {
	//									age = "1";
	//								}
	//								String birthday = com.sinosoft.sms.messageinterface.pubfun.PubFun.calSDate(PubFun.getCurrentDate(), 0 - Integer.parseInt(age), "Y");
									String birthday = new OrderConfigNewServiceImpl().getBrithdayByFactor(null,age);
									
									fEMRiskFactorList[iCount].setFactorValue(birthday);
								}
							} else if ("Period".equals(fEMRiskFactorList[iCount].getFactorType())) {
								fEMRiskFactorList[iCount].setFactorValue(period);
								
							}  else if ("Plan".equals(fEMRiskFactorList[iCount].getFactorType())) {
									fEMRiskFactorList[iCount].setFactorValue(mPlanList[i].getFactorValue());
							}
							fEMRiskFactorList[iCount].setIsPremCalFacotor("Y");
							iCount++;
						}
						calProductPrem[i].setFEMRiskFactorList(fEMRiskFactorList);
						
						//泰康的产品 每个计划都要调用一次接口 太坑爹了，那个速度叫个慢啊,最好还是能改一下~~bless
						if(productId.startsWith("1015")){
							Map<String, Object> tkMap = new HashMap<String, Object>();
							CalProductPrem[] tkCalProductPrem=new CalProductPrem[1];
							tkCalProductPrem[0]=calProductPrem[i];
							tkMap.put("CalProductPrem", tkCalProductPrem);
							try {
								ProductPrem = ProductWebservice.ProductPremSereviceImpl(tkMap, null);
								result.put(riskcode + "-" + mPlanList[i].getFactorValue(),ProductPrem.getDiscountTotalPrice() );
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
							}
						}//泰康END
				}
				
				 //如果不是泰康产品，则调用一次保费计算接口就可以得到每个计划的保费
				if(!productId.startsWith("1015")){
					Map<String, Object> mp = new HashMap<String, Object>();
					mp.put("CalProductPrem", calProductPrem);
					try {
						ProductPrem = ProductWebservice.ProductPremSereviceImpl(mp, null);
						String[] dp=ProductPrem.getDiscountPrice();
						for (int j = 0; j < dp.length; j++) {
							//根据产品编码，原价计算网站活动折后保费
							String activityPriceNew = ActivityCalculate.ProductCalculate(riskcode,"",dp[j], "wj", memberId);
							result.put(riskcode + "-" + mPlanList[j].getFactorValue(), activityPriceNew);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			
		}
		return result;
	}

    /**
     * 拼装投保要素map
     * @author fhz
     * **/
	private Map<String,  String> getAppFactorMap() {
		Map<String, String> map = new HashMap<String, String>();
		List<OrderRiskAppFactor> orafs = new ArrayList<OrderRiskAppFactor>();
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
						oraf.setFactorTypeName(faf.getFactorTypeName());
						oraf.setProductCode(productId);
						oraf.setIsPremCalFacotor(isPremCalFacotor);
						oraf.setAppFactorCode(faf.getAppFactorCode());
						oraf.setFactorType(factorType);
						oraf.setFactorValue(femRiskList);
						oraf.setDataType(faf.getDataType());
						orafs.add(oraf);
					}
				}
			}
			for (int i = 0; i < orafs.size(); i++) {
				OrderRiskAppFactor orderRiskAppFactor = orafs.get(i);
				// 拆分保障期限值，用于页面
				if ("Period".equals(orderRiskAppFactor.getFactorType().toString())) {
					List<FEMRiskFactorList> periodList = orderRiskAppFactor.getFactorValue();
					// 填写前台保险期间
					if (this.period != null && !"".equals(this.period)) {
						// 为计算保费做准备
						for (FEMRiskFactorList tempPeriodList : periodList) {
							String tPer = tempPeriodList.getFactorValue();
							if (period.equals(tPer)) {
								map.put(productId + "_" + tempPeriodList.getFactorType(), tempPeriodList.getFactorValue());
							}
						}
					}
				}
				if ("TextAge".equals(orderRiskAppFactor.getFactorType().toString())) {
					List<FEMRiskFactorList> periodList = orderRiskAppFactor.getFactorValue();
					for (FEMRiskFactorList tempPeriodList : periodList) {
						String tPer = tempPeriodList.getFactorValue();
						if (textAge.equals(tPer)) {
							map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), tPer);
						}
					}
				}
				if ("Plan".equals(orderRiskAppFactor.getFactorType().toString())) {
					map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), orderRiskAppFactor.getFactorValue().get(0).getFactorValue());
					if (StringUtils.isNotEmpty(plan)) {
						map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), plan);
					}
				}
				if ("MulPeople".equals(orderRiskAppFactor.getFactorType().toString())) {
					map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), orderRiskAppFactor.getFactorValue().get(0).getFactorValue());
				}
			}
		return map;
	}
	
	
	private Map<String,  Map<String, String>> getAppFactorMapSimple() {
		Map<String, Map<String, String>> mapproduct = new HashMap<String, Map<String, String>>();
		
		
		for (int m = 0; m < fm.length; m++) {
			List<OrderRiskAppFactor> orafs = new ArrayList<OrderRiskAppFactor>();
			Map<String, String> map = new HashMap<String, String>();
			
			if(fm[m].getFERiskAppFactor()!=null&&fm[m].getFERiskAppFactor().length>0){
				for(ProductInfoWebServiceStub.FERiskAppFactor faf : fm[m].getFERiskAppFactor()){
					String isPremCalFacotor ="";
					if(faf.getFEMRiskFactorList()!=null&&faf.getFEMRiskFactorList().length>0){
						isPremCalFacotor = faf.getFEMRiskFactorList()[0].getIsPremCalFacotor();
						List<ProductInfoWebServiceStub.FEMRiskFactorList> femRiskList = new ArrayList<ProductInfoWebServiceStub.FEMRiskFactorList>();
						for(ProductInfoWebServiceStub.FEMRiskFactorList femFactor: faf.getFEMRiskFactorList()){
							femRiskList.add(femFactor);
						}
						OrderRiskAppFactor oraf = new OrderRiskAppFactor();
						String factorType = faf.getFactorType();
						oraf.setFactorTypeName(faf.getFactorTypeName());
						oraf.setProductCode(fm[m].getRiskCode());
						oraf.setIsPremCalFacotor(isPremCalFacotor);
						oraf.setAppFactorCode(faf.getAppFactorCode());
						oraf.setFactorType(factorType);
						oraf.setFactorValue(femRiskList);
						oraf.setDataType(faf.getDataType());
						orafs.add(oraf);
					}
				}
			}
			for (int i = 0; i < orafs.size(); i++) {
				OrderRiskAppFactor orderRiskAppFactor = orafs.get(i);
				// 拆分保障期限值，用于页面
				if ("Period".equals(orderRiskAppFactor.getFactorType().toString())) {
					List<FEMRiskFactorList> periodList = orderRiskAppFactor.getFactorValue();
					// 填写前台保险期间
					if (this.period != null && !"".equals(this.period)) {
						// 为计算保费做准备
						for (FEMRiskFactorList tempPeriodList : periodList) {
							String tPer = tempPeriodList.getFactorValue();
							if (period.equals(tPer)) {
								map.put(fm[m].getRiskCode() + "_" + tempPeriodList.getFactorType(), tempPeriodList.getFactorValue());
							}
						}
					}
				}
				if ("TextAge".equals(orderRiskAppFactor.getFactorType().toString())) {
					List<FEMRiskFactorList> periodList = orderRiskAppFactor.getFactorValue();
					for (FEMRiskFactorList tempPeriodList : periodList) {
						String tPer = tempPeriodList.getFactorValue();
						if (textAge.equals(tPer)) {
							map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), tPer);
						}
					}
				}
				if ("Plan".equals(orderRiskAppFactor.getFactorType().toString())) {
					map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), orderRiskAppFactor.getFactorValue().get(0).getFactorValue());
					
				}
				if ("MulPeople".equals(orderRiskAppFactor.getFactorType().toString())) {
					map.put(orderRiskAppFactor.getAppFactorCode() + "_" + orderRiskAppFactor.getFactorType(), orderRiskAppFactor.getFactorValue().get(0).getFactorValue());
				}
			}
			mapproduct.put(fm[m].getRiskCode(), map);
		}

		return mapproduct;
	}



	/**
	 * 得到产品信息
	 * @author Administrator fhz
	 * **/

		
	private  FMRisk[] getProductInformation(String productCode, String BU1) {
		String[] riskCode = mRiskCode;
		FMRisk[] fmRisk=null;
		Map<String,Object> paramter = new HashMap<String,Object>();
		paramter.put("RiskCode", riskCode);
		paramter.put("BU1", BU1);//判断是否是赠险
		try {
			ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(paramter, null);
			fmRisk=productInfo.getFMRisk();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return fmRisk;
	}
	
	/**
	 * 得到产品包含的计划列表
	 * @author fhz
	 * **/
	private  List<FEMRiskFactorList[]> getPlanList(FMRisk[] fm) {
		FERiskAppFactor[] feRiskAppFactor = fm[0].getFERiskAppFactor();
		int femLen = fm.length;
		List<FEMRiskFactorList[]> mList = new ArrayList<ProductInfoWebServiceStub.FEMRiskFactorList[]>();
		for(int fem=0;fem<femLen;fem++){
			feRiskAppFactor = fm[fem].getFERiskAppFactor();
			FEMRiskFactorList[] tPlanList=null;
			for (int i = 0; i < feRiskAppFactor.length; i++) {
				if("Plan".equalsIgnoreCase(feRiskAppFactor[i].getFactorType())){//得到计划要素的数组
					tPlanList=feRiskAppFactor[i].getFEMRiskFactorList().clone();
					mList.add(tPlanList);
				}
			} 
		}
		
		return mList;
	}
	/**
	 * 得到产品包含的責任列表
	 * @author fhz
	 * **/
	private  List<FMDuty[]> getDutyList(FMRisk[] fmlist) {
		
		int femLen = fmlist.length;
		List<FMDuty[]> mList = new ArrayList<ProductInfoWebServiceStub.FMDuty[]>();
		for(int fem=0;fem<femLen;fem++){
			mList.add(fmlist[fem].getFMDuty());
		}
		return mList;
	}
    public static void main(String[] args) {
    	
		/*Map<String, String> planPrice = new HashMap<String, String>();		//各种计划保费
		PlanAction pa=new PlanAction();
		pa.setProductId("200701042");
		pa.setPlan("00626");
		pa.setTextAge("0Y-65Y");
		pa.setPeriod("365D");
		pa.fm=pa.getProductInformation("200701042", "N");
		pa.newf=pa.fm[0].getFMDuty();
		pa.mPlanList=pa.getPlanList(pa.fm[0].getFERiskAppFactor());
		planPrice=pa.getPlanPrice();
		String x=pa.getPlanDutyHtml();
		Collection c=planPrice.keySet();
		Iterator it=c.iterator();
		while(it.hasNext())
		{
		   String keyc=(String)it.next();
		   System.out.println(keyc+": "+planPrice.get(keyc));	
		}*/
	
    }
    
    
    
    
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getTextAge() {
		return textAge;
	}

	public void setTextAge(String textAge) {
		this.textAge = textAge;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getComplicatedFlag() {
		return complicatedFlag;
	}
	
	public void setComplicatedFlag(String complicatedFlag) {
		this.complicatedFlag = complicatedFlag;
	}
	
}