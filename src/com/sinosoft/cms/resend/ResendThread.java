package com.sinosoft.cms.resend;

import cn.com.sinosoft.util.Constant;

import com.activemq.ProducerMQ;
import com.alibaba.fastjson.JSON;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.SDOrdersSchema;
import com.sinosoft.schema.SDOrdersSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ResendThread 
 * @Description: TODO(重发-线程类) 
 * @author CongZN 
 * @date 2014-10-11 下午04:59:49 
 *
 * <p> 修改历史</p>
 * <p> 序号 日期 修改人 修改原因</p>
 */
public class ResendThread extends Thread{

	private static final Logger logger = LoggerFactory.getLogger(ResendThread.class);

	private String g_orderSn;
	private String g_companyID;
	private String g_insuredSn;
	
	public ResendThread(String p_orderSn,String p_companyID,String p_insuredSn) {
		g_orderSn = p_orderSn;
		g_companyID = p_companyID;
		g_insuredSn = p_insuredSn;
	}
	
	@Override
	public void run() {
		 resend(g_orderSn, g_companyID,g_insuredSn);
	}
	
	/**
	 * @Title: resend.
	 * @Description: TODO(重发).
	 * @param p_orderSn 订单号.
	 * @param p_companyID 保险公司编码.
	 * @param p_insuredSn 被保人编号. void. 
	 * @author CongZN.
	 */
	public static void resend(String p_orderSn,String p_companyID,String p_insuredSn) {
		try {

			if (StringUtil.isEmpty(p_companyID) || StringUtil.isEmpty(p_orderSn)) {
				logger.warn("订单号：{}|错误信息：保险公司或者订单号为空!", p_orderSn);
				return;
			}
			
			String sql0 = "select orderstatus from sdorders where orderSn = ? ";
			DataTable dt0 = new QueryBuilder(sql0, p_orderSn).executeDataTable();
			if (dt0 == null || dt0.getRowCount() != 1
					|| !"7".equals(dt0.getString(0, 0))) {
				logger.warn("订单号：{}|错误信息：此订单不存在或者未支付!", p_orderSn);
				return;
			}


			// 调用经代通
			ResendInsureTransfer insureTransferResend = new ResendInsureTransfer();
			insureTransferResend.callInsTransInterface(p_orderSn,p_companyID,p_insuredSn);

			String query_InformationRiskTypeSql = "select s1.appStatus,s1.insureMsg from SDInformationRiskType s1,SDInformationInsured s2 where s1.recognizeeSn = s2.recognizeeSn and s2.insuredSn = ? ";
			DataTable result_InformationRiskType = new QueryBuilder(query_InformationRiskTypeSql, p_insuredSn).executeDataTable();
			String errorMsg = "";
			boolean status = false;
			for (int i = 0; i < result_InformationRiskType.getRowCount(); i++) {
				String appStatus = result_InformationRiskType.getString(i, "appStatus");
				String insureMsg = result_InformationRiskType.getString(i, "insureMsg");
				if ("1".equals(appStatus)) {
					status = true;
				}else{
					errorMsg = insureMsg;
				}
			}
			
			if (status) {
				//承保成功 删除后续待发送!
				String sqlGroupType = "SELECT a.ordersn,b.GroupType,a.ChannelSn FROM sdorders a LEFT JOIN sdorderitemoth b ON a.ordersn=b.ordersn WHERE a.ordersn=? limit 1";
				QueryBuilder queryBuilder = new QueryBuilder(sqlGroupType);
				queryBuilder.add(p_orderSn);
				DataTable dt = queryBuilder.executeDataTable();
				// 美行保团单 按照订单号更新数据 bug	0003102: 美行保新平台团单自动从发只更新一条risktype表的记录
				if(dt != null && dt.getRowCount() > 0){
					String channelsn = dt.getString(0, "ChannelSn");
					String groupType = dt.getString(0, 1);
					if((Constant.B2B_HT_CHANNELSN.equals(channelsn)||
							Constant.B2B_HT_CHANNELSN01.equals(channelsn)||
							Constant.B2B_HT_CHANNELSN02.equals(channelsn)||
							Constant.B2C_PC_CHANNELSN.equals(channelsn)||
							Constant.B2C_WAP_CHANNELSN.equals(channelsn)) &&
							(StringUtil.isNotEmpty(groupType)&&"g".equals(groupType))){
						QueryBuilder del_tradeErrorCache = new QueryBuilder("delete from SDTradeErrorCache where ordersn = ?");
						del_tradeErrorCache.add(p_orderSn);
						del_tradeErrorCache.executeNoQuery();
					}else{
						QueryBuilder del_tradeErrorCache = new QueryBuilder("delete from SDTradeErrorCache where insuredSn = ?");
						del_tradeErrorCache.add(p_insuredSn);
						del_tradeErrorCache.executeNoQuery();
					}
				}

				SDOrdersSchema order = new SDOrdersSchema();
				order.setorderSn(p_orderSn);
				SDOrdersSet sdordersSets = order.query();
				order = sdordersSets.get(0);
				
				// 发送消息队列到经代通下载电子保单
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderSn", p_orderSn);
				map.put("policyNo", "");
				if(Constant.B2B_HT_CHANNELSN.equals(order.getchannelsn()) || 
						Constant.B2B_HT_CHANNELSN01.equals(order.getchannelsn()) ||
						Constant.B2B_HT_CHANNELSN02.equals(order.getchannelsn()) ||
						Constant.B2C_PC_CHANNELSN.equals(order.getchannelsn())||
						Constant.B2C_WAP_CHANNELSN.equals(order.getchannelsn())){
					map.put("channelCode", "mxbnew");
				}else{
					map.put("channelCode", order.getchannelsn());
				}
				// cps_58bb不发送短信和邮件
				if(StringUtil.isNotEmpty(Config.getValue("NoSendChannelsn")) && Config.getValue("NoSendChannelsn").indexOf(order.getchannelsn()) != -1){
					map.put("isSendEmail", "N");
				}else{
					map.put("isSendEmail", "Y");
				}
				map.put("isRewrite", "Y");
				map.put("paramMap", null);
				ProducerMQ mq = new ProducerMQ();
				mq.sendToJDT(JSON.toJSONString(map));
				
				//TODO
				//快钱渠道撤单调用接口
				if (Constant.BILL_KQ_CHANNELSN.equals(order.getchannelsn())) {
					try {
						// 发送消息队列到消费者
						Map<String, String> dataSendMap = new HashMap<String, String>();
						dataSendMap.put("type", "1");
						dataSendMap.put("orderSn", p_orderSn);
						dataSendMap.put("operator", "dealBillCallback");
						ProducerMQ mq1 = new ProducerMQ();
						mq1.sendToCallBackPolicy(JSON.toJSONString(dataSendMap));

					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			} else {
				logger.error("订单号：{}|错误信息：承保失败!{}", p_orderSn, errorMsg);
			}

		} catch (Exception e) {
			logger.error("订单号："+p_orderSn+"|错误信息：发送失败!"+e.getMessage(), e);
		}
	}
	

}
