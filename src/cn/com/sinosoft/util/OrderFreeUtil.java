package cn.com.sinosoft.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.TradeInformation;
import cn.com.sinosoft.service.SDOrderService;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.inter.ActivityCalculate;

/**
 * 多线程处理承保也业务--主站春节赠险
 */
public class OrderFreeUtil extends Thread{

	private SDOrder sorder;
	private TradeInformation old;
	private SDOrderService sdorderService;
	private HttpServletRequest request;
	private Member member;
	public OrderFreeUtil(SDOrder tSDOrder,TradeInformation tTradeInformation,SDOrderService tSDOrderService,
			HttpServletRequest tHttpServletRequest,Member tMember){
		sorder = tSDOrder;
		old  =tTradeInformation;
		sdorderService = tSDOrderService;
		request = tHttpServletRequest;
		member = tMember;
	}
	@Override
	public void run() {
		WapShoppingUtil.callInsureTransfer(sorder,old,sorder.getOrderSn(),sdorderService,request,member);
		SDInformation info = sorder.getSdinformationSet().iterator().next();
		String channelSn = "free";
		// 处理产品销量
		WapShoppingUtil.addProductSalesVolume(String.valueOf(info.getProductId()));
		//处理支付成功后，分单
		ActivityCalculate.activityeSplit(sorder.getPaySn(),channelSn);
		//处理积分
		ActivityCalculate.TransactionDealIntegral(sorder.getPaySn(),channelSn);
		//处理支付后活动：满送、买送、高倍积分
		//List<SDOrder> sdorderList = new ArrayList<SDOrder>();
		//sdorderList.add(sorder);
		//ActivityCalculate.TransactionDeal(sdorderList, sorder.getMemberId(), sorder.getChannelsn());
		new QueryBuilder("update sdorders set orderstatus='7' where orderSn=?",sorder.getOrderSn()).executeNoQuery();
	}
	
	
}
