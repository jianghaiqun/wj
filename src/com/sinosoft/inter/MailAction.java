package com.sinosoft.inter;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @ClassName: MailAction.
 * @Description: TODO(邮件发送公共类).
 * @author CongZN. 
 * @date 2014-8-27 下午04:02:40 
 *
 * <p> 修改历史</p>
 * <p> 序号 日期 修改人 修改原因</p>
 */
public class MailAction {
	private static final Logger logger = LoggerFactory.getLogger(MailAction.class);
	/**
	 * @Title: parseTemplate.
	 * @Description: TODO(通过flt文件用html片段展示数据).
	 * @param templateFile 模板文件(路径).
	 * @param data 发送内容.
	 * @return
	 * @throws Exception String .
	 * @author CongZN.
	 */
	public static String parseTemplate(String templateFile, Map<String, Object> data) throws Exception {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(Config.getContextRealPath()));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setEncoding(Locale.CHINA, "UTF-8");
		Template temp = cfg.getTemplate(templateFile);
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(temp, data);
		return text;
	}
	
	/**
	 * @Title: shoppingCartData.
	 * @Description: TODO(待支付-购物车,封装发送内容).
	 * @param p_PaySn void .
	 * @author CongZN.
	 */
	public static Map<String, Object> shoppingCartData(String p_PaySn,Map<String, Object> p_data){
		
		Map<String, String> ordersInfoMap;
		List<Map<String, String>> ordersInfoList = new ArrayList<Map<String, String>>();
		String channelSn = (String)p_data.get("ChannelSn");
		String orderSn = null;
		String productName = null;
		
		StringBuffer query_OrdersSql = new StringBuffer();
		query_OrdersSql.append("select s2.ordersn,s1.productName,s3.applicantName,s2.totalAmount, count(1) as MulNum ");
		query_OrdersSql.append("from sdinformation s1,sdorders s2,SDInformationAppnt s3, SDInformationrisktype s4 ");
		query_OrdersSql.append("where s1.orderSn = s2.orderSn and s1.orderSn = s4.orderSn ");
		query_OrdersSql.append("and s1.informationSn = s3.informationSn ");
		
		if("wj".equals(channelSn)){
			query_OrdersSql.append("and s2.paySn = ? ");
		}else if("wap".equals(channelSn)){
			query_OrdersSql.append("and s2.orderSn = ? ");
		}else{
			query_OrdersSql.append("and s2.paySn = ? ");
		}
		query_OrdersSql.append(" group by s2.ordersn ");
		StringBuffer query_PayUrlSql = new StringBuffer();
		query_PayUrlSql.append("select ");
		query_PayUrlSql.append("(select z.value from zdconfig z where type= 'PayUrl') as PayUrl, ");
		query_PayUrlSql.append("(select z.value from zdconfig z where type= 'shopPayUrl') as shopPayUrl ");
		query_PayUrlSql.append("from dual ");
		
		QueryBuilder query_OrdersInfo = new QueryBuilder();
		query_OrdersInfo.setSQL(query_OrdersSql.toString());
		query_OrdersInfo.add(p_PaySn);
		
		DataTable result_OrdersInfo = query_OrdersInfo.executeDataTable();
		
		QueryBuilder query_PayUrl = new QueryBuilder();
		query_PayUrl.setSQL(query_PayUrlSql.toString());
		DataTable result_PayUrl = query_PayUrl.executeDataTable();
		String payPath = null;
		StringBuffer appendOrderSn = new StringBuffer();
		
		for(int i = 0; i < result_OrdersInfo.getRowCount(); i++){
			ordersInfoMap = new HashMap<String, String>();
			orderSn = result_OrdersInfo.getString(i, "ordersn");
			productName = result_OrdersInfo.getString(i, "productName");
			
			ordersInfoMap.put("OrderSn", orderSn);
			ordersInfoMap.put("ProductName", productName);
			
			ordersInfoMap.put("ApplicantName", result_OrdersInfo.getString(i, "applicantName"));
			ordersInfoMap.put("MulNum", result_OrdersInfo.getString(i, "MulNum"));
			
			appendOrderSn.append(orderSn+",");
			ordersInfoList.add(ordersInfoMap);
		}
		
		appendOrderSn.deleteCharAt(appendOrderSn.length() - 1);
		
		if(result_PayUrl.getRowCount() > 0){
			String KID = StringUtil.md5Hex(PubFun.getKeyValue() + orderSn);
			if("wap".equals(channelSn)){
				payPath = result_PayUrl.getString(0, "shopPayUrl");
				payPath = payPath + "?checkOrderSn="+appendOrderSn+"&KID="+KID+"&paySn="+p_PaySn+"&payType=0";
			}else if(result_OrdersInfo.getRowCount() == 1){
				payPath = result_PayUrl.getString(0, "PayUrl");
				payPath = payPath + "?orderSn="+appendOrderSn+"&KID="+KID+"&paySn="+p_PaySn;
			}else{
				payPath = result_PayUrl.getString(0, "shopPayUrl");
				payPath = payPath + "?checkOrderSn="+appendOrderSn+"&KID="+KID+"&paySn="+p_PaySn;
			}
		} 
		
		p_data.put("PayPath", payPath);
		
		p_data.put("OrderSn", orderSn);
		p_data.put("ProductName", productName);
		
		p_data.put("StandardDate", PubFun.getCurrentDateStandard());
		p_data.put("OrdersInfoList", ordersInfoList);
		
		return p_data;
	}
	
	/**
	 * @Title: deleteCommentMail.
	 * @Description: TODO(通过订单号,删除待发送评论邮件).
	 * @param p_OrderSn void.
	 * @author CongZN.
	 */
	public static void deleteCommentMail(String p_OrderSn){
		try {
			StringBuffer del_CommentMailSQL = new StringBuffer();
			del_CommentMailSQL.append("delete from SDCacheErroerMail where mailType = 'a0097' and PaySn = (select paySn from SDOrders where orderSn = ?)");
			QueryBuilder del_CommentMail = new QueryBuilder(del_CommentMailSQL.toString());
			del_CommentMail.add(p_OrderSn);
			del_CommentMail.executeNoQuery();
			logger.info("删除待发送评论邮件,删除成功! OrderSn:{}", p_OrderSn);
		} catch (Exception e) {
			logger.error("发送邮件-删除待发送评论邮件方法,异常!" + e.getMessage(), e);
		}
	}
	
	/**
	 * @Title: deleteNoPaymentMail.
	 * @Description: TODO(通过支付流水号删除待支付邮件).
	 * @param p_paySn void.
	 * @author CongZN.
	 */
	public static void deleteNoPaymentMail(String p_paySn){
		try {
			List<String> resultList = queryPaySn(p_paySn);
			Transaction ts = new Transaction();
			for(int i = 0; i < resultList.size(); i++){
				QueryBuilder delMail = new QueryBuilder("delete from SDCacheErroerMail where mailType = 'a0010' and PaySn = ?");
				String paySn = resultList.get(i);
				delMail.add(paySn);
				ts.add(delMail);
			}
			
			if(ts.commit()) {
				logger.info("待支付邮件删除成功! paySn:{}", p_paySn);
			}else{
				logger.error("待支付邮件删除失败! paySn:{}", p_paySn);
			}
			
		} catch (Exception e) {
			logger.error("待支付邮件删除失败! paySn:"+p_paySn + e.getMessage(), e);
		}
	}
	
	/**
	 * @Title: deleteNoPaymentMail.
	 * @Description: TODO(通过旧支付流水号删除待支付邮件).
	 * @param p_paySn void.
	 * @author CongZN.
	 */
	public static void deleteOldNoPaymentMail(String p_paySn){
		if (StringUtil.isEmpty(p_paySn)) {
			return;
		}
		try {
			Transaction ts = new Transaction();
			QueryBuilder delMail = new QueryBuilder("delete from SDCacheErroerMail where mailType = 'a0010' and PaySn = ?");
			delMail.add(p_paySn);
			ts.add(delMail);
			if(ts.commit()) {
				logger.info("重复待支付邮件删除成功! paySn:{}", p_paySn);
			}else{
				logger.error("重复待支付邮件删除失败! paySn:{}", p_paySn);
			}
			
		} catch (Exception e) {
			logger.error("重复待支付邮件删除失败! paySn:" + p_paySn + e.getMessage(), e);
		}
	}
	
	/**
	 * @Title: queryPaySn.
	 * @Description: TODO(通过支付流水号查询这笔支付所使用过的流水号).
	 * @param p_paySn
	 * @return List<String>.
	 * @author CongZN.
	 */
	public static List<String> queryPaySn(String p_paySn){
		try {
			List<String> resultList = new ArrayList<String>();
			QueryBuilder queryPaySn = new QueryBuilder("select paySn from tradeinfo where orderSn = (select orderSn from tradeinfo where paySn = ?)");
			queryPaySn.add(p_paySn);
			DataTable result_paySn = queryPaySn.executeDataTable();
			for(int i = 0; i < result_paySn.getRowCount(); i++){
				resultList.add(result_paySn.getString(i, "paySn"));
			}
			return resultList;
		} catch (Exception e) {
			logger.error("MailAction.queryPaySn 查询支付流水号异常! p_paySn:"+p_paySn + e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * @Title: ylQueryPaySn.
	 * @Description: TODO(银联通过订单号查询支付流水号(银联特殊处理)!).
	 * @param p_orderSn
	 * @return String.
	 * @author CongZN.
	 */
	public static String ylQueryPaySn(String p_orderSn){
		try {
			QueryBuilder queryPaySn = new QueryBuilder("select paySn from tradeinfo where payType='ylzf' and orderSn = ?");
			queryPaySn.add(p_orderSn);
			return queryPaySn.executeString();
		} catch (Exception e) {
			logger.error("MailAction.ylQueryPaySn 查询支付流水号异常! p_orderSn:"+p_orderSn + e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * @Title: deleteNoPaymentMail.
	 * @Description: TODO(通过支付流水号更新待支付邮件).
	 * @param p_paySn void.
	 * @author CongZN.
	 */
	public static void updateNoPaymentMail(String p_oldPaySn,String p_newPaySn,String p_MailType){
		try {
			if(StringUtil.isNotEmpty(p_oldPaySn)){
				QueryBuilder update_CacheErroerMail = new QueryBuilder("update SDCacheErroerMail set paySn = ?,ModifyDate = ? where mailType = ? and PaySn = ?");
				update_CacheErroerMail.add(p_newPaySn);
				update_CacheErroerMail.add(PubFun.getCurrent());
				update_CacheErroerMail.add(p_MailType);
				update_CacheErroerMail.add(p_oldPaySn);
				update_CacheErroerMail.executeNoQuery();
			}
		} catch (Exception e) {
			logger.error("待支付邮件更新失败! paySn:"+p_newPaySn + e.getMessage(), e);
		}
	}
	
	
}