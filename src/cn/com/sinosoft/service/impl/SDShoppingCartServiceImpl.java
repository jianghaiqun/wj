package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.action.shop.uw.UsersUWCheck;
import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.SDShoppingCartDao;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDShoppingCart;
import cn.com.sinosoft.service.ProductPeriodService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.SDShoppingCartService;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.sms.messageinterface.pubfun.PubFun;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class SDShoppingCartServiceImpl extends BaseServiceImpl<SDShoppingCart,String> implements SDShoppingCartService{
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Resource
	private SDOrderService sdorderService;
	@Resource
	private ProductPeriodService productPeriodService;
	@Resource
	private SDShoppingCartDao sdShoppingCartDao;
	@Resource
	public void setSdShoppingCartDao(SDShoppingCartDao sdShoppingCartDao) {
		super.setBaseDao(sdShoppingCartDao);
	}
	@Override
	public SDShoppingCart createCartByOrder(SDOrder sdorder) {
		SDShoppingCart shopCart = new SDShoppingCart();
		shopCart.setOrderSn(sdorder.getOrderSn());
		shopCart.setMemberId(sdorder.getMemberId());
		return shopCart;
	}
	@Override
	public boolean checkInsuredFromShopCart(SDOrder sdorder) {
		String memberId = sdorder.getMemberId();
		SDInformation sdinformation = sdorder.getSdinformationSet().iterator().next();
		List<SDInformationInsured> insuredList = new ArrayList<SDInformationInsured>(sdinformation.getSdinformationinsuredSet());
		
		if(StringUtil.isNotEmpty(memberId)){
			String productId = sdinformation.getProductId();
			String sql = "select a.id from sdinformationinsured a,SDShoppingCart b,sdinformation c where a.ordersn=c.ordersn and a.ordersn = b.ordersn and c.productId='"+productId
					+"' and b.memberId = '"+memberId+"' and a.recognizeeIdentityType=? and a.recognizeeIdentityId=?";
			for(SDInformationInsured insured : insuredList){
				String idtype = insured.getRecognizeeIdentityType();
				String idNo = insured.getRecognizeeIdentityId();
				DataTable dt = sdShoppingCartDao.getInfByIDAndType(sql,idtype,idNo);
				if(dt!= null && dt.getRowCount()>0){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	@Override
	public boolean checkChannelFromShopCart(SDOrder sdorder) {
		String memberId = sdorder.getMemberId();
		if(StringUtil.isNotEmpty(memberId)){
			String sql = "select a.channelsn from sdorders a,SDShoppingCart b,sdinformation c where a.ordersn=c.ordersn and a.ordersn = b.ordersn "
					+" and b.memberId = '"+memberId+"' limit 1 ";
			com.sinosoft.framework.data.QueryBuilder qb = new com.sinosoft.framework.data.QueryBuilder(sql);
			DataTable dt = qb.executeDataTable();
			if(dt!=null && dt.getRowCount()>=1){
				if(!dt.getString(0, "channelsn").equals(sdorder.getChannelsn())){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	@Override
	public boolean checkProduct(String orderSn) {
		if (StringUtil.isNotEmpty(orderSn)) {
			int count = new com.sinosoft.framework.data.QueryBuilder("select count(1) from zdcode where CodeType='NoShoppCart.Product' and ParentCode='NoShoppCart.Product' and CodeValue = (select productId from sdinformation where orderSn=?)", orderSn).executeInt();
			if (count > 0) {
				return false;
			}
		}
		
		return true;
	}
	public int getShopCartNo(String memId) {
		if(StringUtil.isNotEmpty(memId)){
			String sql = "select count(1) from SDShoppingCart a,sdorders b where a.ordersn=b.ordersn and b.orderStatus<7 and a.memberId = '"+ memId +"'";
			DataTable dt = sdShoppingCartDao.getShopCartNo(sql);
			if(dt!=null && dt.getRowCount()>0){
				return dt.getInt(0, 0);
			}
		}
		return 0;
	}
	@Override
	public List<SDShoppingCart> getShowShopCartList(String memberId) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("memberId","=",memberId));
		List<SDShoppingCart> list = sdShoppingCartDao.findByQBs(qbs, "createDate", "desc");
		List<SDShoppingCart> returnList = new ArrayList<SDShoppingCart>();
		for(SDShoppingCart shopCart : list){
			SDShoppingCart sdcart = getShopCartByOrderSn(shopCart.getOrderSn());
			if(sdcart!=null){
				returnList.add(sdcart);
			}
		}
		return returnList;
	}
	
	@Override
	public boolean checkPeriodEffectiveness(SDOrder sdorder) {
		if(sdorder!=null){
			SDInformation sdinformation = sdorder.getSdinformationSet().iterator().next();
			if(sdinformation != null){
				int startPeriod = 1;
				String productId = sdinformation.getProductId();
				String comCode = sdinformation.getInsuranceCompany();
				Date checkStartDay = sdinformation.getStartDate();
				String stp = productPeriodService.getStartPeriod(comCode, productId);
				if(StringUtil.isNotEmpty(stp)){
					startPeriod = Integer.parseInt(stp);
				}
				String newDate = sdf.format(PubFun.calDate(new Date(), startPeriod, "D",null));
				if(StringUtil.isNotEmpty(newDate)){
					newDate = newDate + " 00:00:00";
				}
				try {
					Date startDate = sdf.parse(newDate);
					if(checkStartDay.getTime()-startDate.getTime()>=0){
						return true;
					}
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}
			}
			
		}
		return false;
	}
	
	@Override
	public List<SDShoppingCart> getDeleteInfoByOrderSn(String orderSn) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("orderSn","=",orderSn));
		return sdShoppingCartDao.findByQBs(qbs, "id", "asc");
	}
	
	@SuppressWarnings("static-access")
	@Override
	public Map<String, String> checkOrderByOrderSn(String orderSn,String KID) {
		SDOrder sdorder = sdorderService.getOrderByOrderSn(orderSn.trim());
		SDInformation sdinformation = sdorder.getSdinformationSet().iterator().next();
		if(sdinformation != null){
			HashMap<String,String> map = new HashMap<String, String>();
			String uwCheckFlag = sdShoppingCartDao.getuwCheckFlagByComCode(sdinformation.getInsuranceCompany());
			if(!"N".equals(uwCheckFlag)){
				UsersUWCheck uwCheck = new UsersUWCheck();
				Map<String,Object> tMap = uwCheck.moreUWCheck(sdorder.getOrderSn());
				String tPassFlag = tMap.get("passFlag").toString();
				String tMessage  ="";
				if(!"0".equals(tPassFlag)){
					map.put("rtnMessage", "noRtnMssage");
				}else{
					@SuppressWarnings("unchecked")
					List<Map<String, String>> tList = (List<Map<String, String>>)tMap.get("result");
					for(Map<String,String> m:tList){
						if("0".equals(m.get("passFlag"))){
							tMessage = tMessage+m.get("RecognizeeName")+","+m.get("rtnMessage")+";<br/>";
						}
					}
					map.put("rtnMessage", tMessage);
				} 
				map.put("productName", sdinformation.getProductName());
				map.put("passFlag", tPassFlag);
				map.put("KID", KID); 
			}else{
				map.put("passFlag", "1");
			}
			return map;
		}else{
			return null;
		}
	}
	
	
	@Override
	public SDShoppingCart getShopCartByOrderSn(String orderSn) {
		if(StringUtil.isNotEmpty(orderSn)){
			SDShoppingCart shopCart = new SDShoppingCart();
			SDOrder sdorder = sdorderService.getOrderByOrderSn(orderSn.trim());
			if(sdorder.getOrderStatus().ordinal()<7){
				SDInformation sdinformation = sdorder.getSdinformationSet().iterator().next();
				SDInformationAppnt appnt = sdinformation.getSdinformationappntSet().iterator().next();
				DataTable insuredNameDt = sdShoppingCartDao.getInsuredByOrderSn(orderSn);
				DataTable insuredNoDt = sdShoppingCartDao.getInsuredNoByOrderSn(orderSn);
				shopCart.setOrderSn(orderSn);
				shopCart.setTotleAmount(sdorder.getTotalAmount());
				shopCart.setProductId(sdinformation.getProductId());
				shopCart.setProductName(sdinformation.getProductName());
				shopCart.setStartDate(sdf.format(sdinformation.getStartDate()));
				shopCart.setEndDate(sdf.format(sdinformation.getEndDate()));
				shopCart.setAppntName(appnt.getApplicantName());
				shopCart.setAppntEmail(appnt.getApplicantMail());
				shopCart.setProductTotalPrice(sdorder.getProductTotalPrice().toString());
				shopCart.setDiscountRates(sdorder.getDiscountRates());
				shopCart.setRiskType(sdinformation.getRiskType());
				String allInsuredName = "";
				if(insuredNameDt.getRowCount()>0){
					shopCart.setFirstInsuredName(insuredNameDt.getString(0, 0));
					shopCart.setRecognizeeMul(insuredNoDt.getString(0, 0));
					if(insuredNameDt.getRowCount()>1){
						for(int i =0;i<insuredNameDt.getRowCount();i++){
							allInsuredName = allInsuredName + insuredNameDt.getString(i, 0) + ",";
						}
					}
					if(StringUtil.isNotEmpty(allInsuredName)){
						allInsuredName = allInsuredName.substring(0, allInsuredName.length()-1);
					}
				}
				shopCart.setAllInsuredName(allInsuredName);
				if(!checkPeriodEffectiveness(sdorder)){
					shopCart.setIsEffective("N");
				}else{
					shopCart.setIsEffective("Y");
				}
				Map<String,String> map = sdShoppingCartDao.getProductInFo(sdinformation.getProductId());
				shopCart.setProductLog(map.get("logo"));
				shopCart.setDetailPath(map.get("detailUrl"));
				return shopCart;
			}
		}
		return null;
	}
	@Override
	public double getShopTotleAmount(List<SDShoppingCart> cartList) {
		BigDecimal totle = new BigDecimal(0.0);
		for(SDShoppingCart shopCart : cartList){
			totle = totle.add(shopCart.getTotleAmount());
		}
		return totle.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}
	
	@Override
	public void deleteShopCartInfo(List<SDOrder> sdorderList) {
		if(sdorderList!=null && sdorderList.size()>0){
			StringBuffer sb = new StringBuffer();
			sb.append("delete from SDShoppingCart where orderSn in (");
			for(int i=0;i<sdorderList.size();i++){
				sb.append("'"+sdorderList.get(i).getOrderSn()+"'");
				if(i<sdorderList.size()-1){
					sb.append(",");
				}
			}
			sb.append(")");
			//sdShoppingCartDao.deleteShopCartBySql(sb.toString());
			com.sinosoft.framework.data.QueryBuilder qb = new com.sinosoft.framework.data.QueryBuilder(sb.toString());
			qb.executeNoQuery();
			
		}
		
		
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}

}
