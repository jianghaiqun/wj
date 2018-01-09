package cn.com.sinosoft.service.impl;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.CouponInfoDao;
import cn.com.sinosoft.entity.CouponInfo;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.service.CouponInfoService;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActivityCalculate;


@Service
public class CouponInfoServiceImpl extends BaseServiceImpl<CouponInfo, String> implements CouponInfoService {
	
	@Resource
	private CouponInfoDao couponInfoDao;
	
	@Resource
	public void setBaseDao(CouponInfoDao couponInfoDao) {
		super.setBaseDao(couponInfoDao);
	}
	
	@Override
	public CouponInfo getCouponInfoByOrderSn(String orderSn) {
		
		return couponInfoDao.getCouponInfoByOrderSn(orderSn);
		
	}

	@Override
	public String couponVerify(CouponInfo coupon,String productId,SDOrder sdorder) {
		
		String message = "success";
		
		//优惠劵有效性校验
		if(coupon == null){
			message = "请输入有效的优惠劵号";
			return message;
		}
		
		DataTable productRiskCode = couponInfoDao.getRiskCodeFromProductCenter(productId);
		
		String productCompanyCode = StringUtil.noNull(couponInfoDao.getCompanyCodeFromProductCenter(productId));
		
		String[] couponRiskCodeArr = StringUtil.noNull(coupon.getRiskCode()).split(",");
		
		String[] couponCompanyCodeArr = StringUtil.noNull(coupon.getInsuranceCompany()).split(",");
		
		String[] couponProductArr = StringUtil.noNull(coupon.getProduct()).split(",");
		
		List<SDOrder> paramterList = new ArrayList<SDOrder>();
		paramterList.add(sdorder);
		Map<String, Map<String, Object>> activity_product_info1 = ActivityCalculate.ProductShoppingCartInfo(paramterList,sdorder.getChannelsn(),true);
		
		//进行优惠劵状态校验
		if(!"2".equals(coupon.getStatus())){//不是已发放状态
			message = "此优惠劵为无效优惠劵";
			return message;
		}
		
		//进行日期校验
		Date startTime = coupon.getStartTime();
		Date endTime = coupon.getEndTime();
		Date now = DateUtil.parseDateTime(DateUtil.getCurrentDateTime());
		
		if(now.before(startTime) || now.after(endTime)){
			message = "此优惠劵不在有效期内，请到会员中心查看优惠劵的有效期";
			return message;
		}
		
//		//进行使用限制金额校验
//		BigDecimal payAmount = coupon.getPayAmount();
//		BigDecimal totalAmount = sdorder.getTotalAmount();
//		int amountFlag = totalAmount.compareTo(payAmount);
//		if(amountFlag == -1){
//			message = "此订单支付金额没有达到优惠劵使用金额的标准";
//			return message;
//		}
		
		//进行公司校验
		boolean companyCodeCheck = false;
		if(StringUtil.isEmpty(couponCompanyCodeArr[0])){
			companyCodeCheck = true;
		}else{
			for(int i=0; i<couponCompanyCodeArr.length; i++){
				if(productCompanyCode.equals(couponCompanyCodeArr[i])){
					companyCodeCheck = true;
					break;
				}
			}
		}
		
		if(!companyCodeCheck){
			message = "此优惠劵不能支付当前保险公司的产品";
			return message;
		}
		
		//进行险种检验
		boolean riskCodeCheck = false;
		if(StringUtil.isEmpty(couponRiskCodeArr[0])){
			riskCodeCheck = true;
		}else{
			if(companyCodeCheck){
				a:for(int i=0; i<couponRiskCodeArr.length; i++){
					for(int j=0; j<productRiskCode.getRowCount(); j++){
						if(couponRiskCodeArr[i].equals(productRiskCode.get(j, "subRisktype"))){
							riskCodeCheck = true;
							break a;
						}
					}
				}
			}
		}
		
		if(!riskCodeCheck){
			message = "此优惠劵不能支付当前保险类别的产品";
			return message;
		}
		//进行产品检验
		boolean productCheck = false;
		if(StringUtil.isEmpty(couponProductArr[0])){
			productCheck = true;
		}else{
			for(int i=0; i<couponProductArr.length; i++){
				if(productId.equals(couponProductArr[i])){
					productCheck = true;
					break;
				}
			}
		}
		if(!productCheck){
			message = "此优惠劵不能支付当前产品";
			return message;
		}
		
		// 查询订单参与的活动，判断优惠券是否可以与活动共用
		if(activity_product_info1!=null && activity_product_info1.size()>0){
			//遍历优惠信息Map
			Set keySet = activity_product_info1.keySet();   
		    for(Iterator it = keySet.iterator();it.hasNext();){
		    	//活动号（包含“_no_activity”）
		    	String activitysn=String.valueOf(it.next());
		    	//满减活动
		    	if(!"_no_activity".equals(activitysn)){
		    		if (!"Y".equalsIgnoreCase(coupon.getProp1())) {
		    			message = "此优惠劵不能支付当前产品";
		    			return message;
		    		}
		    	}
		    	
		    }  
		}
		//判断优惠卷本渠道是否可用
		if(StringUtil.isEmpty(sdorder.getChannelsn())){
			message = "激活失败，渠道获取异常！";
			return message;
		}else if((coupon.getChannelSn()).indexOf(sdorder.getChannelsn()) ==-1){
			message = "激活失败，此优惠劵不支持本渠道！";
			return message;
		}
		return message;
	}

	@Override
	public CouponInfo getCouponInfoByCouponSn(String couponSn) {
		
		return couponInfoDao.getCouponInfoByCouponSn(couponSn);
		
	}
	
	/**
	 * 支付订单时，优惠劵验证方法
	 *            
	 * @return 验证结果
	 */
	public boolean couponVerifyForPay(CouponInfo coupon) {
		
		//进行优惠劵状态校验
		if(!"2".equals(coupon.getStatus())){
			return false;
		}
		
		//进行日期校验
		Date startTime = coupon.getStartTime();
		Date endTime = coupon.getEndTime();
		Date now = DateUtil.parseDateTime(DateUtil.getCurrentDateTime());
		
		if(now.before(startTime) || now.after(endTime)){
			return false;
		}
		
		return true;
	}
	
}
