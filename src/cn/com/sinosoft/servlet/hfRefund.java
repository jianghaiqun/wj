package cn.com.sinosoft.servlet;

import chinapnr.SecureLink;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.Order.PaymentStatus;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.sinosoft.framework.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class hfRefund extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(hfRefund.class);

	/**
	 * 
	 */

	public boolean getTradeInformationByOrdSn(String ordSn) {
		boolean flag = false;
		String sql = "select ordid from tradeinformation  where ordid= ? ";   
		JdbcTemplateData jtd = new JdbcTemplateData();
		List<Map> list = new ArrayList<Map>();
		try {
			String sqltemp[] = new String[] { ordSn };   
			list = jtd.obtainData(sql , sqltemp);
			
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		
			Iterator<Map> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				flag = true;
			}

		return flag;
	}

	private static final long serialVersionUID = 5036164950899149419L;

	public Order getOrderByOrderSn(String ordSn) {
		Order order = null;
		String sql = "select Paymentstatus from orders  where ordersn= ? ";
		JdbcTemplateData jtd = new JdbcTemplateData();
		List<Map> list = new ArrayList<Map>();
		try {
			String sqltemp[] = new String[] { ordSn };
			list = jtd.obtainData(sql , sqltemp);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		
			Iterator<Map> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				order=new Order();
				order.setPaymentStatus((PaymentStatus)map.get("Paymentstatus"));
			}
		
		return order;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("GBK");
		response.setContentType("text/html;charset=GBK");
		String CmdId = request.getParameter("CmdId").trim(); // 消息类型
		String OrdId = request.getParameter("OrdId").trim(); // 退款订单号
		String OldOrdId = request.getParameter("OldOrdId").trim(); // 原交易订单号
		String RespCode = request.getParameter("RespCode").trim(); // 商户号
		String ErrMsg = request.getParameter("ErrMsg").trim(); // 应答错误描述
		String ChkValue = request.getParameter("ChkValue").trim(); // 签名
		String result;
		try {
			// 验签
			String MerKeyFile = Config.getClassesPath() + "PgPubk.key";

			String MerData = CmdId + OrdId + OldOrdId + RespCode + ErrMsg; // 参数顺序不能错
			SecureLink sl = new SecureLink();
			int ret = sl.VeriSignMsg(MerKeyFile, MerData, ChkValue);
			boolean old = getTradeInformationByOrdSn(OldOrdId);
			Order  order = getOrderByOrderSn(OldOrdId);
			JdbcTemplateData jtd = new JdbcTemplateData();
			if (!old) {
				String error = "不存在此交易信息";

			}
			if (order==null) {
				String error = "不存在此交易信息";

			}
			if (old && order!=null) {
				 if (ret != 0) {
				result = "1";// 交易失败
				
				java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				java.util.Date currentTime = new java.util.Date();
				String receiveDate2 = formatter.format(currentTime);
				String sql = "update tradeinformation set ReceiveDate2= ? ,ReturnSign2= ? ,TradeResult= ? ,PayStatus='3',ErrorMsg2='签名验证失败'"
						+ " where  ordid= ? ";
				String sqltemp[] = new String[] { receiveDate2 , ChkValue,  result , OldOrdId};
				jtd.updateOrSaveOrDelete(sql , sqltemp);

				 } else {
					if (RespCode.equals("000000")) {
						if (order.getPaymentStatus() == Enum.valueOf(
								PaymentStatus.class, "refunded")) {
						} else {
							result = "0";// 表示成功
							
							String hsql = "update orders set PaymentStatus='4'"
									+ " where  ordersn=  ? ";
							String sqltemp[] = new String[] { OldOrdId };
							jtd.updateOrSaveOrDelete(hsql , sqltemp);
							java.text.SimpleDateFormat formatter2 = new java.text.SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							java.util.Date currentTime2 = new java.util.Date();
							String receiveDate3 = formatter2.format(currentTime2);
							String hhsql = "update tradeinformation set ReceiveDate2= ? ,ReturnSign2= ? ,TradeResult= ? ,PayStatus='3'"
									+ " where  ordid=  ? ";
							
							String sqltemp1[] = new String[] { receiveDate3, receiveDate3, result, OldOrdId };
							
							jtd.updateOrSaveOrDelete(hhsql , sqltemp1);
						}
					} else {
						result = "1";// 表示失败
						java.text.SimpleDateFormat formatter3 = new java.text.SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						java.util.Date currentTime3 = new java.util.Date();
						String receiveDate3 = formatter3.format(currentTime3);
						String hhhsql = "update tradeinformation set ReceiveDate2= ? ,ReturnSign2= ? ,TradeResult= ? ,PayStatus='3' ,ErrorMsg2= ? " +
								" where  ordid= ? ";
						String sqltemp1[] = new String[] { receiveDate3, receiveDate3, result, ErrMsg, OldOrdId };
						jtd.updateOrSaveOrDelete(hhhsql , sqltemp1);
	
					}
				}
			 }
			response.getWriter().print("RECV_ORD_ID_" + OldOrdId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		response.setContentType("text/html;charset=GBK");
		String CmdId = request.getParameter("CmdId").trim(); // 消息类型
		String OrdId = request.getParameter("OrdId").trim(); // 退款订单号
		String OldOrdId = request.getParameter("OldOrdId").trim(); // 原交易订单号
		String RespCode = request.getParameter("RespCode").trim(); // 商户号
		String ErrMsg = request.getParameter("ErrMsg").trim(); // 应答错误描述
		String ChkValue = request.getParameter("ChkValue").trim(); // 签名
		String ErrMsg2 = new String(ErrMsg.getBytes("ISO8859-1"), "GBK");
		String result;
		try {
			// 验签
			String MerKeyFile = Config.getClassesPath() + "PgPubk.key";

			String MerData = CmdId + OrdId + OldOrdId + RespCode + ErrMsg; // 参数顺序不能错
			SecureLink sl = new SecureLink();
			int ret = sl.VeriSignMsg(MerKeyFile, MerData, ChkValue);
			boolean old = getTradeInformationByOrdSn(OldOrdId);
			Order  order = getOrderByOrderSn(OldOrdId);
			JdbcTemplateData jtd = new JdbcTemplateData();
			if (!old) {
				String error = "不存在此交易信息";

			}
			if (order==null) {
				String error = "不存在此交易信息";

			}
			if (old && order!=null) {
				 if (ret != 0) {
				result = "1";// 交易失败
				
				java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				java.util.Date currentTime = new java.util.Date();
				String receiveDate2 = formatter.format(currentTime);
				String sql = "update tradeinformation set ReceiveDate2= ? ,ReturnSign2= ? ,TradeResult= ? ,PayStatus='3',ErrorMsg2='签名验证失败'"
						+ " where  ordid= ? ";
				String sqltemp[] = new String[] { receiveDate2, ChkValue , result ,OldOrdId};
				jtd.updateOrSaveOrDelete(sql , sqltemp);

				 } else {
				if (RespCode.equals("000000")) {
					if (order.getPaymentStatus() == Enum.valueOf(
							PaymentStatus.class, "refunded")) {
					} else {
						result = "0";// 表示成功
						
						String hsql = "update orders set PaymentStatus='4'"
								+ " where  ordersn= ? ";
						String sqltemp[] = new String[] {  OldOrdId};
						jtd.updateOrSaveOrDelete(hsql , sqltemp);
						java.text.SimpleDateFormat formatter2 = new java.text.SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						java.util.Date currentTime2 = new java.util.Date();
						String receiveDate3 = formatter2.format(currentTime2);
						String hhsql = "update tradeinformation set ReceiveDate2= ? ,ReturnSign2= ? ,TradeResult= ? ,PayStatus='3'"
								+ " where  ordid=  ? ";
						String sqltemp1[] = new String[] {  receiveDate3 ,ChkValue , result ,OldOrdId };
						jtd.updateOrSaveOrDelete(hhsql , sqltemp1);
					}
				} else {
					result = "1";// 表示失败
					java.text.SimpleDateFormat formatter3 = new java.text.SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					java.util.Date currentTime3 = new java.util.Date();
					String receiveDate3 = formatter3.format(currentTime3);
					String hhhsql = "update tradeinformation set ReceiveDate2= ? ,ReturnSign2= ? ,TradeResult= ? ,PayStatus='3' ,ErrorMsg2= ? "
							+ " where  ordid=  ? ";
					String sqltemp[] = new String[] { receiveDate3 , ChkValue , result , ErrMsg ,OldOrdId };
					jtd.updateOrSaveOrDelete(hhhsql , sqltemp);

				}
			}
			 }
			response.getWriter().print("RECV_ORD_ID_" + OldOrdId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
