/**
 * 
 */
package cn.com.sinosoft.action.exchange;

import cn.com.sinosoft.action.shop.BaseShopAction;
import cn.com.sinosoft.service.ExchangeGoodsService;
import cn.com.sinosoft.service.MemberService;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangcaiyun
 *
 */
@ParentPackage("exchange")
public class ExchangeGoodsAction extends BaseShopAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1045440739169336521L;
	
	@Resource
	protected MemberService memberService;
	@Resource
	protected ExchangeGoodsService exchangeGoodsService;
	
	public Map<String, Object> doExchange() {
		String param = getRequest().getParameter("exchangeInfo");
		Map<String, Object> checkResult = exchangeGoodsService.checkParam(param);
		if (ERROR.equals(checkResult.get(STATUS))) {
			return checkResult;
		}
		@SuppressWarnings("unchecked")
		Map<String, String> paramMap = (Map<String, String>)checkResult.get(MESSAGE);
		return exchangeGoodsService.doExchange(paramMap);
	}
	
	public void notifyFulu() {
		logger.info("福禄回调开始=================================");
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, String> map = new HashMap<String, String>();
		// 交易完成时间
		map.put("chargetime", request.getParameter("ChargeTime"));
		// 合作商家订单号
		map.put("customerorderno", request.getParameter("CustomerOrderNo"));
		// 福禄订单号
		map.put("orderno", request.getParameter("OrderNo"));
		// 充值描述
		map.put("remark", request.getParameter("ReMark"));
		// 订单状态(成功,失败)
		map.put("status", request.getParameter("Status"));
		// 签名
		String sign = request.getParameter("Sign");
		if (exchangeGoodsService.notifyFulu(map, sign)) {
			
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			PrintWriter out = response.getWriter();
			out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?><root><ret><status>True</status></ret></root>");
		} catch (IOException e) {
			logger.error("ExchangeGoodsAction--notifyFulu 福禄回调返回数据异常！" + e.getMessage(), e);
		};
		
	}
}
