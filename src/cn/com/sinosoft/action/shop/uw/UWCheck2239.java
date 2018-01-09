package cn.com.sinosoft.action.shop.uw;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.jdt.ParseXMLToMapNew;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDInformationSet;
import com.sinosoft.schema.SDOrderItemOthSchema;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 0007-太平网销核保处理接口
 * 
 * @author wangchangyang
 * 
 */
public class UWCheck2239 implements UWCheckInterfaceNew {
 
	@Override 
	public Map<String,String> dealData(SDInformationSchema sdinformation,String insuredSn){
		String orderSn = sdinformation.getorderSn();
		Map<String, String> map = new HashMap<String, String>();
		SDInformationRiskTypeSchema sdInformationRiskType = UsersUWCheck.getSDInformationRiskType(orderSn,sdinformation.getinformationSn(),insuredSn);
		SDOrderItemOthSchema sdOrderItemOth = UsersUWCheck.getSDOrderItemOth(orderSn, sdinformation.getinformationSn(),insuredSn);
		if (StringUtils.isEmpty(sdinformation.getorderSn())) {
			throw new NullPointerException(); 
		}
		String rtnMessage = "";
		try {
			ParseXMLToMapNew parse = new ParseXMLToMapNew();
			String strFlag = null;
			boolean isB2b = PubFun.getChannelsnByOrdersn(orderSn);
			if(isB2b){
				strFlag = "03";
			}else{
				strFlag = "01";
			}
			Map<String, Object> resMap = parse.dealData(strFlag, sdinformation.getinsuranceCompany(), orderSn,insuredSn);
			String passFlag = resMap.get("passFlag").toString();
			if ("pass".equals(passFlag)) {
				sdInformationRiskType.setapplyPolicyNo(resMap.get("applyPolicyNo").toString());
				UsersUWCheck.updateUWCheckFlag(orderSn,sdinformation.getinformationSn(),insuredSn,"Y");
				sdOrderItemOth.settpySysPaySn(String.valueOf(resMap.get("tpySysPaySn")));
				UsersUWCheck.updateSDOrderItemOth(sdOrderItemOth);
			} else if ("nopass".equals(passFlag)) {
				rtnMessage = "您的投保信息未通过审核，失败原因为【" + resMap.get("rtnMessage").toString() + "】";
				UsersUWCheck.updateUWCheckFlag(orderSn,sdinformation.getinformationSn(),insuredSn,"N");
				map.put("passFlag", "0");
				map.put("rtnMessage", rtnMessage);
				return map;
			}
			map.put("passFlag", "1");
			map.put("rtnMessage", "恭喜你，你的投保信息成功通过核保！");
			sdInformationRiskType.setinsurerFlag(resMap.get("PA_RSLT_CODE") + "");
			sdInformationRiskType.setinsureMsg(resMap.get("PA_RSLT_MESG") + "");
			if (resMap.get("startDate") != null) {
				sdInformationRiskType.setsvaliDate(DateUtil.parseDateTime(resMap.get("startDate").toString()));//更新生效日期
			}
			if (resMap.get("endDate") != null) {
				sdInformationRiskType.setevaliDate(DateUtil.parseDateTime(resMap.get("endDate").toString()));//更新失效日期
			}
			UsersUWCheck.updateSDInformationRiskType(sdInformationRiskType);
			if(resMap.get("startDate") != null && resMap.get("endDate") != null){
				updateInfomation(resMap, sdinformation.getinformationSn());
			}
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			UsersUWCheck.updateUWCheckFlag(orderSn,sdinformation.getinformationSn(),insuredSn,"N");
			map.put("passFlag", "0");
			map.put("rtnMessage", "核保异常");
			return map;
		}
	}
	/**
	 * 修改数据方法
	 * 
	 * @param reMap
	 * @param infomationSn
	 */
	private void updateInfomation(Map<String, Object> reMap, String infomationSn) {
		try {
			Transaction transaction = new Transaction();
			SDInformationSchema sdInformationSchema = new SDInformationSchema();
			SDInformationSet sdInformationSet = new SDInformationSet();
			sdInformationSet = sdInformationSchema.query(new QueryBuilder("where informationSn = ? " , infomationSn));
			if (!sdInformationSet.isEmpty() && sdInformationSet.size() > 0) {
				sdInformationSchema.setstartDate(DateUtil.parseDateTime(reMap.get("startDate").toString()));// 保单保费
				sdInformationSchema.setendDate(DateUtil.parseDateTime(reMap.get("endDate").toString()));// 保单保费
				transaction.add(sdInformationSchema, Transaction.UPDATE);
				transaction.commit();
			}
		} catch (Exception e) {
			logger.error("类InsureTransfer方法updateResultMap()出现异常" + e.getMessage(), e);
		}
	}

}
