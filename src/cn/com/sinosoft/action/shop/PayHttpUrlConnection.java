package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.service.PayCallBackService;
import cn.com.sinosoft.util.SpringUtil;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付后台回调跳.
 * 
 * @author congzn.
 * @date 2013-11-27.
 */
public class PayHttpUrlConnection {
	private static final Logger logger = LoggerFactory.getLogger(PayHttpUrlConnection.class);

	/**
	 * 支付后台回调跳转至PayCallBackAction.
	 * 
	 * @param orderSn
	 *            订单号,必填.
	 * @param payAmount
	 *            支付金额,必填.
	 * @param TrxId
	 *            支付流水号 非必填.
	 * @param ChkValue
	 *            签名,非必填.
	 * @param Btype
	 *            区分回调方式,必填. 1."runjdt" 2.回写成功标记"success".
	 * @param payType
	 *            支付方式.
	 * @return
	 */
	public static String veerAction(String paySn, String payAmount, String TrxId, String ChkValue, String Btype,
			String payType) {
		HttpServletRequest request = ServletActionContext.getRequest();
		PayCallBackService pcbs = (PayCallBackService) SpringUtil.getBean("payCallBackService");

		String result = pcbs.doPay(paySn, payAmount, TrxId, ChkValue, Btype,  payType, null, request);

		return result;
	}
	/**
	 * 支付后台回调跳转至PayCallBackAction.
	 *
	 * @param orderSn
	 *            订单号,必填.
	 * @param payAmount
	 *            支付金额,必填.
	 * @param TrxId
	 *            支付流水号 非必填.
	 * @param ChkValue
	 *            签名,非必填.
	 * @param Btype
	 *            区分回调方式,必填. 1."runjdt" 2.回写成功标记"success".
	 * @param payType
	 *            支付方式.
	 * @param openId
	 *            openId.
	 * @return
	 */
	public static String veerAction(String paySn, String payAmount, String TrxId, String ChkValue, String Btype,
									String payType, String openId) {
		HttpServletRequest request = ServletActionContext.getRequest();
		PayCallBackService pcbs = (PayCallBackService) SpringUtil.getBean("payCallBackService");

		String result = pcbs.doPay(paySn, payAmount, TrxId, ChkValue, Btype,  payType, openId, request);

		return result;
	}
	/**
	 * 拼接请求参数.
	 * 
	 * @param sb
	 * @param name
	 * @param value
	 */
	public static void addPair(StringBuffer sb, String name, Object value) {

		sb.append(name + "=" + value + "&");
	}

	public static void sendErrorMail(String paysn, String ErrMsg, String payType) {
		try {
			GetDBdata db1 = new GetDBdata();
			String receiver = db1
					.getOneValue("select value from zdconfig where type='InsureErrorMail'");
			if(StringUtil.isNotEmpty(receiver)){
				Map<String, Object> map = new HashMap<String, Object>();
				String ordersn = "";
				String sql = "";

				sql = "select b.productName,a.totalAmount,b.startDate,a.orderSn from SDOrders a, SDInformation b where a.orderSn = b.orderSn and a.paysn='"
						+ paysn + "'";

				map.put("PaySn", paysn);
				map.put("ErrMsg", ErrMsg);
				map.put("payType", payType);
				DataTable dt = new QueryBuilder(sql).executeDataTable();
				if (dt.getRowCount() > 0) {
					map.put("ProductName", dt.getString(0, 0));
					map.put("TotalAmount", dt.getString(0, 1));
					map.put("Effective", dt.getString(0, 2).substring(0, 11));
					map.put("OrderSn", dt.getString(0, 3));
					ordersn = dt.getString(0, 3);
				} else {
					logger.warn("未查询到订单相关信息");
				}

				String sql_policyno = "SELECT recognizeeSn FROM SDInformationRiskType a WHERE (a.appstatus='0' OR a.appstatus='' OR a.appstatus IS NULL ) and a.ordersn ='"
						+ ordersn + "'";
				DataTable dt_policyno = new QueryBuilder(sql_policyno).executeDataTable();
				StringBuffer insurancedid = new StringBuffer();
				if (dt_policyno.getRowCount() > 0) {
					for (int i = 0; i < dt_policyno.getRowCount(); i++) {
						insurancedid.append(dt_policyno.getString(i, 0) + "<br/>");
					}
				} else {
					logger.warn("未查询到订单相关信息");
				}
				map.put("InsuredSn", String.valueOf(insurancedid));
				ActionUtil.sendMail("wj00152", receiver, map);
			}
		} catch (Exception e) {
			logger.error("出现异常:" + "sendErrorMail方法出现异常" + e.getMessage(), e);
		}
	}

}
