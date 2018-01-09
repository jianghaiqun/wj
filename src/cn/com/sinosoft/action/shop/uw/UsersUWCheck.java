package cn.com.sinosoft.action.shop.uw;

import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.SDInformationInsuredSchema;
import com.sinosoft.schema.SDInformationInsuredSet;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDOrderItemOthSchema;
import com.sinosoft.schema.SDOrderItemOthSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersUWCheck {
	private static final Logger logger = LoggerFactory.getLogger(UsersUWCheck.class);
	/**
	 * 多被保人核保
	 */
	public static Map<String, Object> moreUWCheck(String orderSn) {
		// 根据订单号检索被保人
		Map<String, Object> result = new HashMap<String, Object>();
		List<SDInformationSchema> sdlist = getSDinformationByOrderSn(orderSn);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = "SELECT COUNT(1) FROM sdinformationinsured WHERE ordersn  = ?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(orderSn);
		String insuredCount = qb.executeString();
		// 当passFlag为“1”时表示核保通过，当passFlag为“0”时表示核保不通过，
		String passFlag = "0";
		Transaction trans = new Transaction();
		//记录核保结果
		String uwDeleteSQL = " DELETE FROM UWCheckLog WHERE orderSn= ?";
		QueryBuilder qbdel = new QueryBuilder(uwDeleteSQL);
		qbdel.add(orderSn);
		trans.add(qbdel);
		trans.commit();
		int t=0;
		for (SDInformationSchema sd : sdlist) {
			for (Map<String, String> map_in : getInsuredSnListByInformationSn(orderSn, sd.getinformationSn())) {
				UsersUWCheckTask tUsersUWCheckTask = new UsersUWCheckTask();
				Thread thread2=new Thread(tUsersUWCheckTask); 
				tUsersUWCheckTask.setMap_in(map_in);
				tUsersUWCheckTask.setmSDInformationSchema(sd);
				thread2.start();
				t = t+1;
			}
		}
		String uwInsuredCount="";
		String querySQL = "";
		int kk=1; 
		while(String.valueOf(t).equals(insuredCount)&&!String.valueOf(uwInsuredCount).equals(insuredCount)){
			kk= kk + 1;
			if(kk<=120){//如果20分钟还没有核保结束，则直接跳出循环，返回核保异常
				String countSQL = "SELECT COUNT(1) FROM UWCheckLog WHERE orderSn = ?";
				QueryBuilder qb1 = new QueryBuilder(countSQL);
				qb1.add(orderSn);
				uwInsuredCount = qb1.executeString();
				try { Thread.sleep ( 1000 ) ; 
				} catch (InterruptedException ie){}
			}else{
				
				break;
			}
		} 
		querySQL = "SELECT orderSn,informationSn,insuredSn,insuredName,passFlag,rtnMessage FROM UWCheckLog WHERE orderSn = ?";
		QueryBuilder qb2 = new QueryBuilder(querySQL);
		qb2.add(orderSn);
		DataTable dt = qb2.executeDataTable();
		for(int i=0;i<dt.getRowCount();i++){
			Map<String, String> map = new HashMap<String, String>();
			map.put("OrderSn", dt.getString(i, 0));
			map.put("InformationSn", dt.getString(i, 1));
			map.put("InsuredSn", dt.getString(i, 2));
			map.put("RecognizeeName", dt.getString(i, 3));
			map.put("passFlag", dt.getString(i, 4));
			map.put("rtnMessage", dt.getString(i, 5));
			if (dt.getString(i, 4).equals("1")) {
				passFlag = "1";
			}
			list.add(map);
		}
		result.put("result", list);
		result.put("passFlag", passFlag);
		logger.info("核保结果：result==={}", result);
		return result;
	}


	/**
	 * 单被保人核保
	 */
	public static Map<String, String> UWCheck(SDInformationSchema sdinformation, String insuredSn) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String sql = "select Memo from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add("UWCheckClassName");
			qb.add("UWCheckClassName");
			qb.add(sdinformation.getinsuranceCompany());
			String className = qb.executeString();
			if (StringUtil.isEmpty(className)) {
				logger.error("核保异常，没有配置核保类路径！");
				map.put("passFlag", "0");
				map.put("rtnMessage", "投保，没有配置核保类路径！");
				return map;
			}
			Class<?> uwCheckClass = Class.forName(className);
			UWCheckInterfaceNew uwCheck = (UWCheckInterfaceNew) uwCheckClass.newInstance();
			return uwCheck.dealData(sdinformation, insuredSn);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (SecurityException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
		}

		logger.error("调用核保类时发生异常！");
		// String passFlags = "{passFlag:'nopass',rtnMessage:'noRtnMssage'}";
		// JSONObject jsonArrays = JSONObject.fromObject(passFlags);
		// String jsonstrs = jsonArrays.toString();

		map.put("passFlag", "0");
		map.put("rtnMessage", "调用核保类时发生异常！");

		// return ajaxJson(jsonstrs);
		return map;
	}
	
	/**
	 * 多被保人核保-重发
	 */
	public static Map<String, Object> moreUWCheckRetransmit(String orderSn) {
		// 根据订单号检索被保人
		Map<String, Object> result = new HashMap<String, Object>();
		List<SDInformationSchema> sdlist = getSDinformationByOrderSn(orderSn);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = "SELECT COUNT(1) FROM sdinformationinsured WHERE ordersn  = ?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(orderSn);
		String insuredCount = qb.executeString();
		// 当passFlag为“1”时表示核保通过，当passFlag为“0”时表示核保不通过，
		String passFlag = "1";
		Transaction trans = new Transaction();
		
		int t=0;
		for (SDInformationSchema sd : sdlist) {
			for (Map<String, String> map_in : reGetInsuredSnListByInformationSn(orderSn, sd.getinformationSn())) {
				UsersUWCheckTask tUsersUWCheckTask = new UsersUWCheckTask();
				Thread thread2=new Thread(tUsersUWCheckTask); 
				tUsersUWCheckTask.setMap_in(map_in);
				tUsersUWCheckTask.setmSDInformationSchema(sd);
				thread2.start();
				t = t+1;
			}
		}
		String uwInsuredCount="";
		String querySQL = "";
		int kk=1; 
		while(String.valueOf(t).equals(insuredCount)&&!String.valueOf(uwInsuredCount).equals(insuredCount)){
			kk= kk + 1;
			if(kk<=120){//如果20分钟还没有核保结束，则直接跳出循环，返回核保异常
				String countSQL = "SELECT COUNT(1) FROM UWCheckLog WHERE orderSn = ?";
				QueryBuilder qb1 = new QueryBuilder(countSQL);
				qb1.add(orderSn);
				uwInsuredCount = qb1.executeString();
				try { Thread.sleep ( 1000 ) ; 
				} catch (InterruptedException ie){}
			}else{
				
				break;
			}
		} 
		querySQL = "SELECT orderSn,informationSn,insuredSn,insuredName,passFlag,rtnMessage FROM UWCheckLog WHERE orderSn = ? ";
		QueryBuilder qb2 = new QueryBuilder(querySQL);
		qb2.add(orderSn);
		DataTable dt = qb2.executeDataTable();
		for(int i=0;i<dt.getRowCount();i++){
			Map<String, String> map = new HashMap<String, String>();
			map.put("OrderSn", dt.getString(i, 0));
			map.put("InformationSn", dt.getString(i, 1));
			map.put("InsuredSn", dt.getString(i, 2));
			map.put("RecognizeeName", dt.getString(i, 3));
			map.put("passFlag", dt.getString(i, 4));
			map.put("rtnMessage", dt.getString(i, 5));
			if (dt.getString(i, 4).equals("0")) {
				passFlag = "0";
			}
			list.add(map);
		}
		result.put("result", list);
		result.put("passFlag", passFlag);
		logger.info("核保结果：result==={}", result);
		return result;
	}

	/*
	 * 根据订单详细表（产品表）的订单详细表sn来获取该单所有被保人流水号-重发
	 */
	public static List<Map<String, String>> reGetInsuredSnListByInformationSn(String orderSn, String InformationSn) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = "SELECT sd.InsuredSn,sd.recognizeeName FROM sdinformationinsured sd, UWCheckLog uw  WHERE sd.insuredSn=uw.`insuredSn`  and  uw.passFlag='0' and sd.orderSn=? and sd.informationSn=?";
		QueryBuilder qb = new QueryBuilder(sql, orderSn, InformationSn);
		DataTable dt = qb.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("InsuredSn", dt.getString(i, "insuredSn"));
			map.put("RecognizeeName", dt.getString(i, "recognizeeName"));
			list.add(map);
		}
		//更新核保结果
		Transaction trans = new Transaction();
		String uwDeleteSQL = " DELETE FROM UWCheckLog WHERE passFlag='0' and orderSn= ?";
		QueryBuilder qbdel = new QueryBuilder(uwDeleteSQL);
		qbdel.add(orderSn);
		trans.add(qbdel);
		trans.commit();
		return list;
	}
	/*
	 * 根据订单详细表（产品表）的订单详细表sn来获取该单所有被保人流水号
	 */
	public static List<Map<String, String>> getInsuredSnListByInformationSn(String orderSn, String InformationSn) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = "select InsuredSn,recognizeeName from sdinformationinsured where orderSn=? and informationSn=?";
		QueryBuilder qb = new QueryBuilder(sql, orderSn, InformationSn);
		DataTable dt = qb.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("InsuredSn", dt.getString(i, "insuredSn"));
			map.put("RecognizeeName", dt.getString(i, "recognizeeName"));
			list.add(map);
		}
		return list;
	}

	public static SDOrderItemOthSchema getSDOrderItemOth(String orderSn, String informationSn, String insuredSn) {

		String sql_recognizeeSn = "select RecognizeeSn from SDInformationInsured where OrderSn=? and InformationSn=? and InsuredSn=?";
		QueryBuilder qb_recognizeeSn = new QueryBuilder(sql_recognizeeSn);
		qb_recognizeeSn.add(orderSn);
		qb_recognizeeSn.add(informationSn);
		qb_recognizeeSn.add(insuredSn);
		String recognizeeSn = qb_recognizeeSn.executeOneValue().toString();

		SDOrderItemOthSchema sdOrderItemOth = new SDOrderItemOthSchema();
		SDOrderItemOthSet sdOrderItemOthSet = new SDOrderItemOthSet();
		sdOrderItemOthSet = sdOrderItemOth.query(new QueryBuilder("where OrderSn=? and RecognizeeSn=? ", orderSn,
				recognizeeSn));

		if (sdOrderItemOthSet != null && sdOrderItemOthSet.size() > 0) {
			return sdOrderItemOthSet.get(0);
		} else {
			return null;
		}
	}

	public static void updateSDOrderItemOth(SDOrderItemOthSchema sdOrderItemOth) {
		Transaction trans = new Transaction();
		trans.add(sdOrderItemOth, Transaction.UPDATE);
		if (!trans.commit()) {
			logger.error("更新SDOrderItemOthSchema失败！");
		}
	}

	public static void updateSDInformationRiskType(SDInformationRiskTypeSchema sdInformationRiskTypeSchema) {
		Transaction trans = new Transaction();
		trans.add(sdInformationRiskTypeSchema, Transaction.UPDATE);
		if (!trans.commit()) {
			logger.error(
					"更新表sdInformationRiskTypeSchema时，没有成功，orderID：{}", sdInformationRiskTypeSchema.getorderSn());
		}
	}

	public static SDInformationRiskTypeSchema getSDInformationRiskType(String orderSn, String informationSn,
			String insuredSn) {

		String sql = "select * from SDInformationRiskType where OrderSn='" + orderSn + "' and InformationSn='"
				+ informationSn + "' and RecognizeeSn=(select RecognizeeSn from SDInformationInsured where OrderSn='"
				+ orderSn + "' and InformationSn='" + informationSn + "' AND InsuredSn='" + insuredSn + "')";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		SDInformationRiskTypeSchema os = new SDInformationRiskTypeSchema();
		if (null != dt & dt.getRowCount() > 0) {
			os.setValue(dt.getDataRow(0));
		}
		return os;
	}

	public static List<SDInformationSchema> getSDinformationByOrderSn(String orderSn) {

		List<SDInformationSchema> list = new ArrayList<SDInformationSchema>();
		String sql = "select * from SDinformation where orderSn=?";
		QueryBuilder qb = new QueryBuilder(sql, orderSn);
		DataTable dt = qb.executeDataTable();
		for (DataRow dr : dt) {
			SDInformationSchema sd = new SDInformationSchema();
			sd.setValue(dr);
			list.add(sd);
		}
		return list;
	}

	public static void updateUWCheckFlag(String orderSn, String informationSn, String insuredSn, String flag) {
		// String sql = "select * from SDInformationInsured where OrderSn='" +
		// orderSn + "' and InformationSn='"
		// + informationSn + "' and InsuredSn='" + insuredSn + "'";
		// QueryBuilder qb = new QueryBuilder(sql);
		// DataTable dt = qb.executeDataTable();
		Transaction trans = new Transaction();
		SDInformationInsuredSchema os = new SDInformationInsuredSchema();

		SDInformationInsuredSchema sd = new SDInformationInsuredSchema();
		SDInformationInsuredSet sdSet = new SDInformationInsuredSet();
		sdSet = sd.query(new QueryBuilder("where OrderSn='" + orderSn + "' and InformationSn='" + informationSn
				+ "' and InsuredSn='" + insuredSn + "'"));

		if (sdSet != null && sdSet.size() > 0) {
			os = sdSet.get(0);
		}
		// if (dt.getRowCount() > 0) {
		// os.setValue(dt.getDataRow(0));
		os.setuwCheckFlag(flag);
		trans.add(os, Transaction.UPDATE);
		if (!trans.commit()) {
			logger.error(
					"更新SDInformationInsured表的UWCheckFlag出错，orderSn：{}，InsuredSn：{}", orderSn, insuredSn);
		}
		// } else {
		// LogUtil.getLogger().warn(
		// "更新SDInformationInsured表的UWCheckFlag时，更新的该条数据不存在orderSn：" + orderSn +
		// " insuredSn:" + insuredSn
		// + " insuredSn:" + insuredSn);
		// }
	}

	public static String getUWCheckFlagByOrderSn(String orderSn, String informationSn, String insuredSn) {
		String sql = "select UWCheckFlag from SDInformationInsured where OrderSn='" + orderSn + "' and InformationSn='"
				+ informationSn + "' and InsuredSn='" + insuredSn + "'";
		QueryBuilder qb = new QueryBuilder(sql);
		String flag = qb.executeString();
		if (StringUtil.isNotEmpty(flag)) {
			return flag;
		}
		return null;
	}

	// 根据字符串输出JSON，返回null
	// public static String ajaxJson(String jsonString) {
	// return ajax(jsonString, "text/html");
	// }

	// AJAX输出，返回null
	// public static String ajax(String content, String type) {
	// try {
	// HttpServletResponse response = ServletActionContext.getResponse();
	// response.setContentType(type + ";charset=UTF-8");
	// response.setHeader("Pragma", "No-cache");
	// response.setHeader("Cache-Control", "no-cache");
	// response.setDateHeader("Expires", 0);
	// response.getWriter().write(content);
	// response.getWriter().flush();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	
	

	public static void main(String[] args) {

		// List<Map<String, String>> list = moreUWCheck("2013000011113743");
		// List<Map<String, String>> list = moreUWCheck("2013000011113778");
		Map<String, Object> list = moreUWCheck("2013000011113785");
		// List<Map<String, String>> list = moreUWCheck("2013000011113789");
//		System.out.println("-------------------------------------------");
//		for (Map<String, String> map : (List<Map<String, String>>)list.get("result")) {
//			System.out.println(map.get("OrderSn"));
//			System.out.println(map.get("InformationSn"));
//			System.out.println(map.get("InsuredSn"));
//			System.out.println(map.get("RecognizeeName"));
//			System.out.println(map.get("passFlag"));
//			System.out.println(map.get("rtnMessage"));
//		}
//		System.out.println("-------------------------------------------");
		// System.out.println(getUWCheckFlagByOrderSn("2013000011113001","3323001"));
		//
		// updateUWCheckFlag("2013000011113001","3323001","Y");
		// getInsuredSnListByInformationSn("3323001");

		// SDOrderItemOthSchema
		// sd=getSDOrderItemOth("2013000011113701","2013000000000061","2013000011113701_023");
		// System.out.print(sd.getRecognizeeSn());
	}
}
