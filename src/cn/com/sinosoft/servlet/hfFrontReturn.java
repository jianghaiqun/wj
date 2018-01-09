package cn.com.sinosoft.servlet;

import chinapnr.SecureLink;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.Order.OrderStatus;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.sinosoft.framework.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class hfFrontReturn extends HttpServlet{
	private static final Logger logger = LoggerFactory.getLogger(hfFrontReturn.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 5171437333176641380L;
	public boolean getTradeInformationByOrdSn(String ordSn) {
		boolean flag = false;
		String sql = "select ordid from tradeinformation  where ordid=?";    
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
	public Order getOrderByOrderSn(String ordSn) {
		Order order = null;
		String sql = "select OrderStatus from orders  where ordersn= ? ";
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
				order.setOrderStatus((OrderStatus)map.get("OrderStatus"));
			}
		
		return order;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
	    String 	CmdId = request.getParameter("CmdId");			//消息类型
	    String 	MerId = request.getParameter("MerId");	 			//商户号
	    String 	RespCode = request.getParameter("RespCode"); 		//应答返回码
	    String 	TrxId = request.getParameter("TrxId"); 				//钱管家交易唯一标识
	    String 	OrdAmt = request.getParameter("OrdAmt");  			//金额
	    String 	CurCode = request.getParameter("CurCode"); 			//币种
	    String 	Pid = request.getParameter("Pid");					//商品编号
	    String 	OrdId = request.getParameter("OrdId"); 				//订单号
	    String 	MerPriv = request.getParameter("MerPriv");			//商户私有域
	    String 	RetType = request.getParameter("RetType");			//返回类型
	    String 	DivDetails = request.getParameter("DivDetails"); 	//分账明细
	    String 	GateId = request.getParameter("GateId");  			//银行ID
	    String 	ChkValue = request.getParameter("ChkValue"); 		//签名信息 
	    String result="";
		try {
			// 验签
			String MerKeyFile = Config.getClassesPath() + "PgPubk.key";// 此key正式时需要修改成他们给的正式key
			String MerData = CmdId + MerId + RespCode + TrxId + OrdAmt
					+ CurCode + Pid + OrdId + MerPriv + RetType + DivDetails
					+ GateId; // 参数顺序不能错
			SecureLink sl = new SecureLink();
			int ret = sl.VeriSignMsg(MerKeyFile, MerData, ChkValue);
			boolean old = getTradeInformationByOrdSn(OrdId);
			Order  order = getOrderByOrderSn(OrdId);
			if (ret != 0) {
				result="返回的签名不对";
			} else {
				if (RespCode.equals("000000")) {
					result="success";
				}else{
					result="fail";
				}
				}
			request.setAttribute("result", result);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/finish.jsp"); 
			dispatcher .forward(request, response); 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		request.setCharacterEncoding("GBK");
	    String 	CmdId = request.getParameter("CmdId");			//消息类型
	    String 	MerId = request.getParameter("MerId");	 			//商户号
	    String 	RespCode = request.getParameter("RespCode"); 		//应答返回码
	    String 	TrxId = request.getParameter("TrxId"); 				//钱管家交易唯一标识
	    String 	OrdAmt = request.getParameter("OrdAmt");  			//金额
	    String 	CurCode = request.getParameter("CurCode"); 			//币种
	    String 	Pid = request.getParameter("Pid");					//商品编号
	    String 	OrdId = request.getParameter("OrdId"); 				//订单号
	    String 	MerPriv = request.getParameter("MerPriv");			//商户私有域
	    String 	RetType = request.getParameter("RetType");			//返回类型
	    String 	DivDetails = request.getParameter("DivDetails"); 	//分账明细
	    String 	GateId = request.getParameter("GateId");  			//银行ID
	    String 	ChkValue = request.getParameter("ChkValue"); 		//签名信息 
	    String result="";
		try {
			// 验签
			String MerKeyFile = Config.getClassesPath() + "PgPubk.key";// 此key正式时需要修改成他们给的正式key
			String MerData = CmdId + MerId + RespCode + TrxId + OrdAmt
					+ CurCode + Pid + OrdId + MerPriv + RetType + DivDetails
					+ GateId; // 参数顺序不能错
			SecureLink sl = new SecureLink();
			int ret = sl.VeriSignMsg(MerKeyFile, MerData, ChkValue);
			boolean old = getTradeInformationByOrdSn(OrdId);
			Order  order = getOrderByOrderSn(OrdId);
			if (ret != 0) {
				result="返回的签名不对";
			} else {
				if (RespCode.equals("000000")) {
					result="success";
				}else{
					result="fail";
				}
				}
			request.setAttribute("result", result);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/finish.jsp"); 
			dispatcher .forward(request, response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	
	}
}
