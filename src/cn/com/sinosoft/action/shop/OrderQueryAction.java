 package cn.com.sinosoft.action.shop;

 import cn.com.sinosoft.common.FinancingCheck;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.LicaiBaoxianService;
import cn.com.sinosoft.service.SDOrderService;
import cn.com.sinosoft.service.impl.LicaiBaoxianServiceImpl;
import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.JdbcTemplateData;

import com.finance.util.JedisCommonUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.CancelContService;
import com.sinosoft.orders.CancelCont;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.schema.CancelContRefundSchema;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import com.wangjin.cms.orders.QueryOrders;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.sinosoft.framework.utility.DateUtil.getCurrentDate;

 /**
 * 会员查询订单处理类
 * ============================================================================
 * 
 * KEY:SINOSOFT66590FE3DF97C81824D62C168A48301D
 * ============================================================================
 */

@ParentPackage("member")
public class OrderQueryAction extends BaseShopAction {

	private static final long serialVersionUID = 2553137844831167917L;
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	@Resource
	private SDOrderService sdorderService;
	@Resource
	private BindInfoForLoginService bindInfoForLoginService;
	private SDOrder sdorder = new SDOrder();// 改造后订单，add by cuishg
	
	private int page = 1;
	public static final int pagesize = 10;
	private int count;
	private int lastpage;
	private Order order = new Order();// 订单
	private String orderSn;// 订单号
	private String ldate;// 出单起期
	private String hdate;// 出单止期
	private String orderStatus;// 订单状态
	private String applicant;// 投保人
	private List<Map> listOrders;
	private String callType;
	private int newPageSize;
	private String orderFlag;
	// 需要和并行的订单的paysn的集合
	private List<String> paysn_rowspan_list = new ArrayList<String>();
	// 查询过paysn的同一笔支付总金额的集合,减少数据库交互次数
	private List<Map<String, String>> payamount_list = new ArrayList<Map<String, String>>();

	/**
	 * 显示 收支 记录
	 */
	private List<Map<String, String>> listRecord = new ArrayList<Map<String, String>>();

	/**
	 * 理财险专用的变量
	 */
	private Map<String, String> recordMap = new HashMap<String, String>();
	private String sdate = "";
	private String edate = "";
	private String record_status = "";
	private String record_status_str = "";
	private String phoneCode;
	private String cardFlag = "";
	private String cardid = "";

	public String cancel() {

		sdorder = sdorderService.getOrderByOrderSn(orderSn);
		if (sdorder == null) {
			addActionError("系统不存在该订单");
			return ERROR;
		}

		// 判断订单状态是否为暂存或是待支付
		SDOrderStatus sdOrderStatus = sdorder.getOrderStatus();
		if (sdOrderStatus != SDOrderStatus.temptorysave && sdOrderStatus != SDOrderStatus.prepay) {
			addActionError("该订单状态已经更新，不能取消订单。");
			return ERROR;
		}
		sdorder.setOrderStatus(SDOrderStatus.invalid);
		sdorderService.update(sdorder);
		orderSn = "";
		String memberId = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Member loginMember = null;
		if (!"tencent".equals(memberId)) {
			loginMember = memberService.load(memberId);
		} else {
			loginMember = bindInfoForLoginService.get(getSession("loginBindId").toString()).getmMember();
		}
		if (loginMember != null) {
			memberId = loginMember.getId();
		}
		String sql = " select s1.id,s1.createDate, s1.orderStatus ,(select CodeName from zdcode where codetype='orderstatus' and  parentcode='status' and  codevalue= s1.orderstatus ) as CodeName "
				+ "  ,TRUNCATE(s1.totalamount,2) totalAmount ,s2.productName ,s1.orderSn ,s3.IsPublish,s2.insuranceCompany, "
				+ " (case when exists(select 1 from zdcode where codetype='ConfigInput' and parentcode='ConfigInput' and codevalue=s2.InsuranceCompany)  then '1' else '0' end) as configFlag,"
				+ " (case when exists(select 1 from zdcode where codetype='UWCheckClassName' and parentcode='UWCheckClassName' and codevalue=s2.InsuranceCompany) then '1' else '0' end) as needUWCheckFlag,s3.HtmlPath,s1.paysn "
				+ " from sdorders s1,sdinformation s2,sdproduct s3 where s1.memberid=? and s2.ordersn=s1.ordersn and s2.productid=s3.productid  ";
		String sqlPart = "";
		if (ldate != null && !"".equals(ldate)) {
			sqlPart = sqlPart + "and s1.createdate >= '" + ldate + " 00:00:00'";
		}
		if (hdate != null && !"".equals(hdate)) {
			sqlPart = sqlPart + "and s1.createdate <= '" + hdate + " 23:59:59'";
		}
		if (orderStatus != null && !"".equals(orderStatus) && !"99".equals(orderStatus)) {
			sqlPart = sqlPart + " and  s1.orderstatus='" + orderStatus + "'";
		}
		if (orderSn != null && !"".equals(orderSn)) {
			sqlPart = sqlPart + "and s1.orderSn='" + orderSn + "'";
		}
		if (applicant != null && !"".equals(applicant)) {
			sqlPart = sqlPart
					+ "and s2.informationSn in (select informationsn from sdinformationappnt where applicantname like '%"
					+ applicant + "%' )";
		}
		String sqlPart2 = " order by s1.modifydate desc limit " + (page - 1) * pagesize + "," + pagesize;

		String sql3 = sql + sqlPart;
		sql = sql + sqlPart + sqlPart2;
		String[] temp = { memberId };
		JdbcTemplateData jtd = new JdbcTemplateData();
		try {
			listOrders = jtd.obtainData(sql3, temp);
			count = listOrders.size();
			listOrders = jtd.obtainData(sql, temp);
			for (Map map : listOrders) {
				map.put("KID", StringUtil.md5Hex(PubFun.getKeyValue() + map.get("orderSn")));
			}
			this.lastpage = ((count + Order.DEFAULT_ORDER_LIST_PAGE_SIZE - 1) / (Order.DEFAULT_ORDER_LIST_PAGE_SIZE));
			return "list";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * 
	 * @Title: queryOrder
	 * @Description: TODO(我的订单查询)
	 * @return String 返回类型
	 * @author
	 */
	public String queryOrder() {

		if (orderStatus != null && !"".equals(orderStatus) && !"99".equals(orderStatus)) {
			orderFlag = orderStatus;
		}
		getOrderInfo();
		return "query";
	}

	public String queryOrderlist() {

		page_Index = getRequest().getParameter("page_Index");
		if (StringUtil.isEmpty(page_Index)) {
			page = 1;
		} else {
			page = Integer.valueOf(page_Index);
		}
		orderStatus = getRequest().getParameter("orderStatus");
		getOrderInfo();
		if ("11".equals(orderStatus) || "12".equals(orderStatus) || "13".equals(orderStatus)) {
			return "newlist_risktype";
		} else {
			return "newlist";
		}

	}

	/**
	 * 
	 * @Title: queryNoPayOrder
	 * @Description: TODO(未支付订单)
	 * @return String 返回类型
	 * @author
	 */

	public String queryNoPayOrder() {

		orderStatus = "5,6";
		getOrderInfo();
		return "nopay";
	}

	/**
	 * 
	 * @Title: queryEffectOrder
	 * @Description: TODO(待生效保单)
	 * @return String 返回类型
	 * @author
	 */
	public String queryNoEffectOrder() {

		orderStatus = "11";
		getOrderInfo();
		return "noeffect";
	}

	/**
	 * 
	 * @Title: queryEffectingOrder
	 * @Description: TODO(生效中保单)
	 * @return String 返回类型
	 * @author
	 */
	public String queryEffectingOrder() {

		orderStatus = "12";
		getOrderInfo();
		return "effecting";
	}

	/**
	 * 
	 * @Title: queryOutEffectOrder
	 * @Description: TODO(已过期保单)
	 * @return String 返回类型
	 * @author
	 */
	public String queryOutEffectOrder() {

		orderStatus = "13";
		getOrderInfo();
		return "outeffect";
	}

	public String oldList() {

		String memberId = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Member loginMember = null;
		if (!"tencent".equals(memberId)) {
			loginMember = memberService.load(memberId);
		} else {
			loginMember = bindInfoForLoginService.get(getSession("loginBindId").toString()).getmMember();
		}
		if (loginMember != null) {
			memberId = loginMember.getId();
		}
		String startCont = "select count(1) ";
		String startSelect = "select a.id,a.createDate,a.orderStatus,d.CodeName," +
				"TRUNCATE(a.totalamount,2) totalAmount,b.productName,a.orderSn,c.IsPublish," +
				"b.insuranceCompany,c.HtmlPath ,''needUWCheckFlag,'' epathFlag , a.paySn, a.commentId ";
		StringBuffer sb = new StringBuffer();
		sb.append("from sdorders a , sdinformation b ,sdproduct c ,zdcode d,sdinformationappnt e ");
		sb.append("where a.ordersn = b.ordersn and b.informationsn = e.informationsn and b.productid = c.productid and d.codetype='orderstatus' and d.codevalue = a.orderstatus ");
		sb.append("and a.memberid='" + memberId + "' ");
		if (ldate != null && !"".equals(ldate.trim())) {
			if (ldate.indexOf("下单时间") == -1) {
				sb.append(" and a.createdate >= '" + ldate + " 00:00:00'");
			}
		}
		if (hdate != null && !"".equals(hdate.trim())) {
			sb.append(" and a.createdate <= '" + hdate + " 23:59:59'");
		}
		if (orderStatus != null && !"".equals(orderStatus) && !"99".equals(orderStatus)) {
			sb.append(" and  a.orderstatus='" + orderStatus + "'");
		}
		if (orderSn != null && !"".equals(orderSn.trim())) {
			if (orderSn.indexOf("订单编号") == -1) {
				sb.append(" and a.orderSn='" + orderSn + "' ");
			}
		}
		if (applicant != null && !"".equals(applicant.trim())) {
			if (applicant.indexOf("投保") == -1) {
				sb.append(" and e.applicantname like '%" + applicant + "%' ");
			}
		}
		String endSql = "";
		if ("1".equals(callType)) {
			endSql = " order by a.createDate desc limit " + (page - 1) * newPageSize + "," + newPageSize;
		} else {
			endSql = " order by a.modifydate desc limit " + (page - 1) * pagesize + "," + pagesize;
		}
		JdbcTemplateData jtd = new JdbcTemplateData();
		try {
			String countSql = startCont + sb.toString();
			DataTable dt1 = new QueryBuilder(countSql).executeDataTable();
			if (dt1.getRowCount() > 0) {
				count = dt1.getInt(0, 0);
			} else {
				count = 0;
			}
			String selectSql = startSelect + sb.toString() + endSql;
			logger.info("===会员订单查询=={}", selectSql);
			listOrders = jtd.obtainData(selectSql);
			logger.info("==========订单号的长度为:{}", listOrders.size());
			for (Map map : listOrders) {
				map.put("KID", StringUtil.md5Hex(PubFun.getKeyValue() + map.get("orderSn")));
				map.put("needUWCheckFlag", getneedUWCheckFlag(map.get("insuranceCompany") + ""));
				map.put("epathFlag", getPolicyFlag(map.get("orderSn") + "", map.get("insuranceCompany")+ ""));
				if (String.valueOf(map.get("paySn")).endsWith("D") || String.valueOf(map.get("paySn")).endsWith("00")) {
					map.put("shopcartshowflag", "0");
				} else {
					map.put("shopcartshowflag", "1");
				}
			}
			this.lastpage = ((count + pagesize - 1) / (pagesize));
			return "list";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	@SuppressWarnings("rawtypes")
	public String memberQueryTripOrder() {

		Member loginMember = getLoginMember();
		String memberId = "";
		if (loginMember != null) {
			memberId = loginMember.getId();
		}

		StringBuffer selb = new StringBuffer();
		selb.append("select orderSn, orderStatus, totalPrice, travelNum, productId, productName, ");
		selb.append("(select CodeName from zdcode where CodeType='TripOrderStatus' and ParentCode='TripOrderStatus' and CodeValue=orderStatus) orderStatusName, ");
		selb.append("birthland, destination, '' as HtmlPath, '' as payUrl, '' as completeUrl, '' as orderUrl ");
		String where_sql = "from otorderinfo where memberId = ? order by createDate desc ";
		String limit_sql = "limit " + (page - 1) * pagesize + "," + pagesize;
		count = new QueryBuilder("select count(1) " + where_sql, memberId).executeInt();
		DataTable dt = new QueryBuilder(selb.toString() + where_sql + limit_sql, memberId).executeDataTable();
		listOrders = new ArrayList<Map>();
		if (dt != null && dt.getRowCount() > 0) {
			String tripPath = Config.getValue("TripContextPath");
			String tripPayBase = Config.getValue("tripPayBase");
			int len = dt.getRowCount();
			String param = "";
			for (int i = 0; i < len; i++) {
				Map<Object, Object> map = new HashMap<Object, Object>();
				DataColumn[] col = dt.getDataRow(i).getDataColumns();
				for (int j = 0; j < col.length; j++) {
					map.put(col[j].getColumnName(), dt.get(i, col[j].getColumnName()));
				}
				map.put("HtmlPath", (tripPath + "/nonInsurance_detail.shtml?productId=" + dt.getString(i, "productId")));
				param = "orderSn=" + dt.getString(i, "orderSn") + "&KID="
						+ StringUtil.md5Hex(PubFun.getKeyValue() + dt.getString(i, "orderSn"));
				map.put("payUrl", (tripPayBase + "/shop/payonline.shtml?" + param));
				map.put("completeUrl", (tripPath + "/shop/initTravelPeopleInfo.shtml?" + param));
				map.put("orderUrl", (tripPath + "/member/order_detail.shtml?" + param));

				listOrders.add(map);
			}
		}
		this.lastpage = ((count + pagesize - 1) / (pagesize));
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(count));
		page_Index = String.valueOf(page);
		page_count = String.valueOf(lastpage);
		getPageDataList(param_map);
		return "tripList";
	}

	/**
	 * 
	 * memberLcxOrder:(查询银行卡信息). <br/>
	 *
	 * @author chouweigao
	 * @return
	 */
	public String financingBank() {

		Member loginMember = getLoginMember();
		String memberId = "";
		if (loginMember != null) {
			memberId = loginMember.getId();
		} else {
			return "";
		}

		if (cardFlag.equals("Y")) {
			String sql = "update SDCardBind set UseFlag = 'N' where id= ? and memberid=?";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(cardid);
			qb.add(memberId);
			qb.executeNoQuery();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DISTINCT 	");
		sb.append("	  CONCAT(SUBSTR(BankAccNo, 1, 4),'*******',SUBSTR(BankAccNo, LENGTH(BankAccNo) - 3, 4)) AS bankno, ");
		sb.append("	  b.AccName AS username,	");
		sb.append("	  z.CodeName AS bankname,	");
		sb.append("	  b.BankCode AS bankcode,	");
		sb.append("	  b.id AS id,	");
		sb.append(" (SELECT  CONCAT(orderstatus,'')   FROM	DirectPayBankInfo d 	LEFT JOIN sdorders s 	   ON d.ordersn = s.ordersn 	WHERE b.BankAccNo = d.bankno  AND s.orderStatus in ('5', '6', '7', '10', '12', '14') LIMIT 1) AS bankstatus,  	");
		sb.append(" (SELECT  CONCAT(orderstatus,'')   FROM	DirectPayBankInfo d 	LEFT JOIN sdorders s 	   ON d.ordersn = s.ordersn 	WHERE b.BankAccNo = d.bankno  AND s.orderStatus in ('6', '7', '10', '12', '14') LIMIT 1) AS orderexists ");
		sb.append("	FROM	   SDCardBind b 	");
		sb.append("	  LEFT JOIN zdcode z 	");
		sb.append("	    ON b.BankCode = z.CodeValue 	");
		sb.append("	WHERE z.codetype = 'LicaiBaoxianBankCode' 	");
		sb.append("	  AND b.BindFlag = 'Y' 	");
		sb.append("	  AND b.useflag = 'Y' 	");
		sb.append("	  and memberid	= '" + memberId + "'");
		//sb.append(" ORDER BY bankstatus DESC");
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				String bankno = dt.getString(i, "bankno");
				String username = dt.getString(i, "username");
				String bankname = dt.getString(i, "bankname");
				String id = dt.getString(i, "id");
				String bankstatus = dt.getString(i, "bankstatus");
				String orderexists = dt.getString(i, "orderexists");
				String bankcode = dt.getString(i, "bankcode");
				// 银行卡信息
				Map<String, String> resultMap = new HashMap<String, String>();
				resultMap.put("bankno", bankno);
				resultMap.put("username", username);
				resultMap.put("bankname", bankname);
				resultMap.put("id", id);
				resultMap.put("bankstatus", bankstatus);
				resultMap.put("orderexists", orderexists);
				resultMap.put("bankcode", bankcode);
				listRecord.add(resultMap);
			}
		}
		return "financingBank";
	}

	/**
	 * 
	 * financingSurrender:(理财险申请退保). <br/>
	 *
	 * @author chouweigao
	 * @return
	 */
	public String financingSurrender() {

		Member loginMember = getLoginMember();
		String memberId = "";
		if (loginMember != null) {
			memberId = loginMember.getId();
		} else {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT 	");
		sb.append("	  f.ordersn AS ordersn,	");
		sb.append("	  policyno,	");
		sb.append("	  total,	");
		sb.append("	  principal,	");
		sb.append("	  income,	");
		sb.append("	  InsStatus,	");
		sb.append("	  i.productname AS productname,	");
		sb.append("	  d.bankName AS bankname,	");
		sb.append("	  CONCAT(SUBSTR(d.bankNo, 1, 4),'*******',SUBSTR(d.bankNo, LENGTH(d.bankNo) - 3, 4))   AS bankno ,	");
		sb.append("	  applicantmobile 	");
		sb.append("	FROM	");
		sb.append("	  financinginfo f,	");
		sb.append("	  sdorders s ,	");
		sb.append("	  sdinformation i,	");
		sb.append("	  sdinformationappnt p,	");
		sb.append("	  DirectPayBankInfo d	");
		sb.append("	WHERE 	");
		sb.append("	i.informationsn=p.informationsn	");
		sb.append("	AND s.ordersn=d.ordersn 	");
		sb.append("	AND s.ordersn=i.ordersn	");
		sb.append("	AND f.ordersn = s.ordersn 	");
		sb.append("	AND s.ordersn ='" + orderSn + "'");
		sb.append("	AND s.memberid ='" + memberId + "'");
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			String osn = dt.getString(0, "ordersn");
			String pno = dt.getString(0, "policyno");
			String principal = dt.getString(0, "principal");
			String total = dt.getString(0, "total");
			String productname = dt.getString(0, "productname");
			String income = dt.getString(0, "income");
			String bankname = dt.getString(0, "bankname");
			String bankno = dt.getString(0, "bankno");
			String mobile = dt.getString(0, "applicantmobile");
			String InsStatus = dt.getString(0, "InsStatus");

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderSn", osn);
			LicaiBaoxianService lcx = new LicaiBaoxianServiceImpl();
			Map<String, Object> result = lcx.canceltry(param);
			String succ = (String) result.get("status");
			String ss = "0.0";
			String sm = "0.0";
			if (succ.equals("0")) {
				// 保费试算
				sm =String.valueOf(result.get("SumMoney"));
				BigDecimal d1 = new BigDecimal(sm);
				BigDecimal d2 = new BigDecimal(principal);
				BigDecimal bd = d1.subtract(d2).setScale(2, BigDecimal.ROUND_HALF_UP);
				ss = bd.toString();
			} else {
				ss = income;
			}
			String phone = mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4);
			recordMap.put("ordersn", osn);
			recordMap.put("policyno", pno);
			recordMap.put("productname", productname);
			recordMap.put("total", total);
			recordMap.put("principal", principal);
			recordMap.put("income", ss);
			recordMap.put("bankname", bankname);
			recordMap.put("bankno", bankno);
			recordMap.put("mobile", mobile);
			recordMap.put("phone", phone);
			recordMap.put("ss", sm);
			recordMap.put("InsStatus", InsStatus);

		} else {
			recordMap.put("InsStatus", "");
		}
		return "financingSurrender";

	}

	/**
	 * 
	 * 发送验证码到手机或邮箱
	 */
	public String sendCode() {

		String svcode = VCFactory();
		setSession("SurrenderCode", svcode);
		String rgtype = getParameter("rtype");
		String way = getParameter("ways");// 邮箱号或手机号
		if ("1".equals(rgtype)) {
			if (SVCToPhone(svcode, way, "Y")) {
				return ajaxText("发送成功");
			}
		}
		return ajaxText("发送失败");
	}

	/**
	 * 
	 * checkCode:(手机验证码). <br/>
	 *
	 * @author chouweigao
	 * @return
	 */
	public String checkCode() {

		Member loginMember = getLoginMember();
		String memberId = "";
		if (loginMember != null) {
			memberId = loginMember.getId();
		} else {
			return "";
		}
		// 退保验证码
		String code = (String) getSession("SurrenderCode");
		Map<String, String> resultMap = new HashMap<String, String>();
		if (code.equals(phoneCode)) {
			StringBuffer sb = new StringBuffer();
			sb.append("	UPDATE 	 financinginfo 	");
			sb.append("	SET	  insstatus = 2, modifyDate = now(), cancelDate = date_format(now(),'%Y-%m-%d %H:%i:%s') ");
			sb.append("	WHERE 	");
			sb.append("	  ordersn = '" + orderSn + "' 	");
			sb.append("	  AND ordersn IN 	");
			sb.append("	  (	  SELECT 	  ordersn 	 FROM	sdorders 	");
			sb.append("	  WHERE 	memberid = '" + memberId + "' )	");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			qb.executeNoQuery();
			resultMap.put("status", "0");
		} else {
			resultMap.put("status", "1");
			resultMap.put("msg", "验证码错误");
		}
		JSONObject jsonObject = new JSONObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 
	 * financingSurrenderEffect:(理财险已退保). <br/>
	 *
	 * @author chouweigao
	 * @return
	 */
	public String financingSurrenderEffect() {

		Map<String, String> map = getSurrenderEffect();
		map.put("biz", "newSurrenderEffect");
		LcxPage(map);
		return "financingSurrenderEffect";
	}

	public String newSurrenderEffect() {

		Map<String, String> map = getSurrenderEffect();
		map.put("biz", "newSurrenderEffect");
		LcxPage(map);
		return "newSurrenderEffect";
	}

	public Map<String, String> getSurrenderEffect() {

		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 2; i++) {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT 	");
			if (i == 0) {
				sb.append("count(1)	");
			} else {
				sb.append("	  IFNULL(d.ordersn,'') AS ordersn,	");
				sb.append("	  total AS total,	");
				sb.append("	  d.payPrice AS principal,	");
				sb.append("	  income AS income,	");
				sb.append("	  b.productname AS productname,	");
				sb.append("	  DATE_FORMAT(b.startdate, '%Y-%m-%d') AS startdate,	");
				sb.append("	  IFNULL(DATE_FORMAT(a.modifydate, '%Y-%m-%d'),'') AS modifydate,	");
				sb.append("	  a.insstatus AS insstatus,	");
				sb.append("	  RIGHT(p.bankNo, 4) AS bankno,	");
				sb.append("	  p.bankName AS bankname ,	");
				sb.append("	  b.productid AS productid 	,");
				sb.append("	  dp.htmlPath ");
			}
			sb.append("FROM	");
			sb.append("	  sdorders d,	");
			sb.append("	  sdproduct dp,	");
			sb.append("	  sdinformation b,	");
			sb.append("	  DirectPayBankInfo p 	");
			sb.append("LEFT JOIN FinancingInfo a 	");
			sb.append("ON p.ordersn = a.ordersn 	");
			sb.append("WHERE d.ordersn = p.ordersn 	");
			sb.append("	  AND b.ordersn = p.ordersn 	");
			sb.append("	  AND dp.productid = b.productid 	");
			sb.append("	  AND d.orderstatus IN ('9')	");
			sb.append("	  AND a.insstatus IN ('3')	");
			sb.append("	  AND FIND_IN_SET(b.productid, (SELECT VALUE FROM zdconfig WHERE TYPE = 'LicaiBaoxianProducts')) ");
			sb.append("	  AND d.memberid =?");
			if (i == 0) {
				map.put("countSql", sb.toString());
			} else {
				map.put("pageSql", sb.toString());
			}
		}
		return map;
	}

	/**
	 * 
	 * financingRecord:(理财金记录). <br/>
	 *
	 * @author chouweigao
	 * @return
	 */
	public String financingRecord() {

		Member loginMember = getLoginMember();
		String memberId = "";
		if (loginMember != null) {
			memberId = loginMember.getId();
		} else {
			return "";
		}
		// 理财总额
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT 	");
		sb.append("	CASE	");
		sb.append("	    WHEN ROUND(SUM(a.Total),2) IS NULL    THEN 0 	");
		sb.append("	    ELSE ROUND(SUM(a.Total),2)  END AS Total,	");
		sb.append("	 CASE	");
		sb.append("	    WHEN ROUND(SUM(a.Principal),2) IS NULL    THEN 0 	");
		sb.append("	    ELSE ROUND(SUM(a.Principal),2) END AS Principal,   	");
		sb.append("	 CASE	");
		sb.append("	    WHEN ROUND(SUM(a.Income),2) IS NULL    THEN 0 	");
		sb.append("	    ELSE ROUND(SUM(a.Income),2) END AS Income	");
		sb.append("	FROM	");
		sb.append("	  FinancingInfo a,	");
		sb.append("	  SdOrders b 	");
		sb.append("	WHERE 	");
		sb.append("	  a.orderSn = b.orderSn 	");
		sb.append("	  AND a.InsStatus != '3' 	");
		sb.append("	  AND b.memberid = 	");
		sb.append("'" + memberId + "'");
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			String total = dt.getString(0, "Total");
			String principal = dt.getString(0, "Principal");
			String income = dt.getString(0, "Income");
			recordMap.put("total", total);
			recordMap.put("principal", principal);
			recordMap.put("income", income);
		}
		// 历史收益
		sb = new StringBuffer();
		sb.append("	SELECT 	");
		sb.append("	CASE	");
		sb.append("	    WHEN SUM(a.Income) IS NULL    THEN 0 	");
		sb.append("	    ELSE SUM(a.Income)  END AS HistoryIncome	");
		sb.append("	FROM 	");
		sb.append("	FinancingInfo a, SdOrders b 	");
		sb.append("	WHERE 	");
		sb.append("	a.orderSn = b.orderSn AND a.InsStatus = '3' AND b.memberid = ");
		sb.append("'" + memberId + "'");
		qb = new QueryBuilder(sb.toString());
		dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			String historyIncome = dt.getString(0, "HistoryIncome");
			recordMap.put("historyIncome", historyIncome);
		}
		recordMap.put("nowdate", getCurrentDate());
		Map<String, String> map = getRecord();
		map.put("biz", "newRecord");
		LcxPage(map);
		return "financingRecord";
	}
	
	public String newRecord() {

		Map<String, String> map = getRecord();
		map.put("biz", "newRecord");
		LcxPage(map);
		return "newRecord";
	}

	public Map<String, String> getRecord() {

		
		sdate = getRequest().getParameter("sdate");
		edate = getRequest().getParameter("edate");
		record_status = getRequest().getParameter("record_status");
		record_status_str = getRequest().getParameter("record_status_str");
		
		// 显示交易记录
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 2; i++) {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT 	");
			if (i == 0) {
				sb.append("count(1)	");
			} else {
				sb.append("	DATE_FORMAT(a.CreateDate,'%Y-%m-%d %H:%i') AS cdate, a.principal, a.total, a.recordStatus, b.riskName, c.bankName	");
			}
			sb.append("	FROM 	");
			sb.append("	FinancingRecord a, SDInformationRiskType b, DirectPayBankInfo c 	");
			sb.append("	WHERE 	");
			sb.append("	a.OrderSn = b.OrderSn 	");
			sb.append("AND b.OrderSn = c.OrderSn	");
			if (!StringUtil.isEmpty(sdate)) {
				sb.append("AND DATE_FORMAT(a.CreateDate,'%Y-%m-%d')>='" + sdate + "' ");
			}
			if (!StringUtil.isEmpty(edate)) {
				sb.append("AND '" + edate + "'>=DATE_FORMAT(a.CreateDate,'%Y-%m-%d')	");
			}
			if (!StringUtil.isEmpty(record_status)) {
				sb.append("AND RecordStatus='" + record_status + "'	");
			}
			sb.append("AND a.memberId = ? ");
			sb.append("ORDER BY a.CreateDate DESC	");

			if (i == 0) {
				map.put("countSql", sb.toString());
			} else {
				map.put("pageSql", sb.toString());
			}
		}
		return map;
	}

	

	/**
	 * 
	 * financingManager:(理财金管理). <br/>
	 *
	 * @author chouweigao
	 * @return
	 */
	public String financingManager() {

		Map<String, String> map = getManager();
		map.put("biz", "newManager");
		LcxPage(map);
		return "financingManager";
	}

	public String newManager() {

		Map<String, String> map = getManager();
		map.put("biz", "newManager");
		LcxPage(map);
		return "newManager";
	}

	public Map<String, String> getManager() {

		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 2; i++) {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT 	");
			if (i == 0) {
				sb.append("count(1)	");
			} else {
				sb.append("IFNULL(d.ordersn,'') AS ordersn,	");
				sb.append("IFNULL(total,'0') AS total,");
				sb.append("d.payPrice AS principal,	");
				sb.append("IFNULL(income,'0') AS income	,");
				sb.append("b.productname AS productname,	");
				sb.append("DATE_FORMAT(b.startdate, '%Y-%m-%d') AS startdate,	");
				sb.append("IFNULL(DATE_FORMAT(a.modifydate, '%Y-%m-%d'),'') AS modifydate,	");
				sb.append("IFNULL(a.insstatus,'') AS insstatus,");
				sb.append("RIGHT(p.bankNo, 4) AS bankno,	");
				sb.append("p.bankName AS bankname ,	");
				sb.append("b.productid AS productid 	,");
				sb.append("dp.htmlPath ");
			}
			sb.append("FROM	");
			sb.append("	  sdorders d,	");
			sb.append("	  sdproduct dp,	");
			sb.append("	  sdinformation b,	");
			sb.append("	  DirectPayBankInfo p 	");
			sb.append("LEFT JOIN FinancingInfo a 	");
			sb.append("ON p.ordersn = a.ordersn 	");
			sb.append("WHERE d.ordersn = p.ordersn 	");
			sb.append("	  AND b.ordersn = p.ordersn 	");
			sb.append("	  AND dp.productid = b.productid 	");
			sb.append("	  AND d.orderstatus IN ('6', '7')	");
			sb.append("	  AND FIND_IN_SET(b.productid, (SELECT VALUE FROM zdconfig WHERE TYPE = 'LicaiBaoxianProducts')) ");
			sb.append("	  AND d.memberid = ?");
			sb.append("	  ORDER BY d.modifydate desc");
			if (i == 0) {
				map.put("countSql", sb.toString());
			} else {
				map.put("pageSql", sb.toString());
			}
		}
		return map;
	}

	/**
	 * 
	 * LcxPage:(理财险分页). <br/>
	 * 
	 * @author chouweigao
	 * @param map
	 * @return
	 */
	public String LcxPage(Map<String, String> map) {

		// 取得会员id
		Member loginMember = getLoginMember();
		String memberId = "";
		if (loginMember != null) {
			memberId = loginMember.getId();
		} else {
			return "";
		}
		// 分页数据
		page_Index = getRequest().getParameter("page_Index");
		if (StringUtil.isEmpty(page_Index)) {
			page = 1;
		} else {
			page = Integer.valueOf(page_Index);
		}

		// 获得业务
		String biz = map.get("biz");
		
		
		String limit_sql = " limit " + (page - 1) * pagesize + "," + pagesize;
		// 总数sql
		String countSql = map.get("countSql");
		count = new QueryBuilder(countSql, memberId).executeInt();
		// 分页sql
		String pageSql = map.get("pageSql");
		QueryBuilder qb = new QueryBuilder(pageSql + limit_sql, memberId);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				DataColumn[] col = dt.getDataRow(i).getDataColumns();
				// 结果集
				HashMap<String, String> resultMap = new HashMap<String, String>();
				for (int j = 0; j < col.length; j++) {
					resultMap.put(col[j].getColumnName(), dt.getString(i, col[j].getColumnName()));
				}
				// financingManager:(理财金管理)
				if (biz.contains("Manager")) {
					String ordersn = resultMap.get("ordersn");
					resultMap.put("KID", StringUtil.md5Hex(PubFun.getKeyValue() + ordersn));
				} else if (biz.contains("Effect")) {// 退保
					String ordersn = resultMap.get("ordersn");
					String bankno = resultMap.get("bankno");
					String bankname = resultMap.get("bankname");
					resultMap.put("KID", StringUtil.md5Hex(PubFun.getKeyValue() + ordersn));
					resultMap.put("bankname", bankname + "(尾号" + bankno + ")");
				} else if (biz.contains("Record")) {
					String recordStatus = resultMap.get("recordStatus");
					String riskName = resultMap.get("riskName");
					String bankName = resultMap.get("bankName");
					if (recordStatus.equals("0")) {
						resultMap.put("recordDetail", "投资 | " + riskName);
					} else if (recordStatus.equals("1")) {
						resultMap.put("recordDetail", "取现 | " + bankName);
					}
				}
				// 保存结果集的list
				listRecord.add(resultMap);
			}
		}
		this.lastpage = ((count + pagesize - 1) / (pagesize));
		page_Index = String.valueOf(page);
		page_count = String.valueOf(lastpage);
		setActionAlias("order_query!" + biz + ".action");
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(count));
		param_map.put("idalias", "member_ordertable");
		getPageDataList(param_map);
		return biz;
	}


	public void getOrderInfo() {

		try {

			if (StringUtil.isNotEmpty(ldate)) {
				ldate = java.net.URLDecoder.decode(ldate, "UTF-8");
			}
			if (StringUtil.isNotEmpty(applicant)) {
				applicant = java.net.URLDecoder.decode(applicant, "UTF-8");
			}
			if (StringUtil.isNotEmpty(orderSn)) {
				orderSn = java.net.URLDecoder.decode(orderSn, "UTF-8");
			}

		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return;
		}
		String memberId = "";
		Member loginMember = getLoginMember();
		if (loginMember != null) {
			memberId = loginMember.getId();
		} else {
			return;
		}
		setActionAlias("order_query!queryOrderlist.action");
		// 会员中心首页初始化查询
		String limit_sql = " limit " + (page - 1) * pagesize + "," + pagesize;
		// 查看全部订单分页查询
		if ("1".equals(callType)) {
			limit_sql = " limit " + (page - 1) * newPageSize + "," + newPageSize;
		}

		StringBuffer startCont = new StringBuffer(
				"select count(1) from  (select DISTINCT COALESCE(a.paysn,a.id) AS paysn  from sdorders a left join tradesummaryinfo t on a.PaySn=t.PaySn ,sdinformation b,sdinformationappnt e  where  a.ordersn = b.ordersn and b.informationsn = e.informationsn and memberid='"
						+ memberId + "' ");

		String startSelect = "select a.id,a.createDate,a.orderStatus,d.CodeName,a.payPrice, round(a.totalamount,2) totalAmount,b.productId,b.productName,a.orderSn,c.IsPublish,c.IsUpdateFlag,c.IsUpdateDate,a.activitySn,e.applicantname,"
				+ "a.modifydate,b.insuranceCompany,c.HtmlPath ,''needUWCheckFlag,'' epathFlag , a.paySn, a.commentId,a.couponSn,a.offsetPoint,a.orderCoupon,a.orderIntegral,a.sumCoupon ,a.sumIntegral,t.PointSumAmount,shop.memberid ,a.channelsn,b.startDate,b.endDate,b.ensureLimitType ,a.policyNo  ";
		StringBuffer sql_where = new StringBuffer();
		if (ldate != null && !"".equals(ldate.trim()) && !"undefined".equals(ldate)) {
			if (ldate.indexOf("支付时间") == -1) {
				sql_where
						.append(" and UNIX_TIMESTAMP(DATE_FORMAT(t.CreateDate,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT('"
								+ ldate + " 00:00:00','%Y-%m-%d %H:%i:%s'))");
			}
		}
		if (hdate != null && !"".equals(hdate.trim()) && !"undefined".equals(hdate)) {
			sql_where
					.append(" and UNIX_TIMESTAMP(DATE_FORMAT(t.CreateDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('"
							+ hdate + " 23:59:59','%Y-%m-%d %H:%i:%s'))");
		}
		if (orderStatus != null && !"".equals(orderStatus) && !"99".equals(orderStatus) && !"undefined".equals(orderStatus)) {
			if ("11".equals(orderStatus)) {// 保单待生效
				// sql_where.append(" and UNIX_TIMESTAMP(DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s'))>UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s')) and a.orderstatus in ('7','10','12','14') ");
				sql_where
						.append(" and DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s')>DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') and a.orderstatus in ('7','10','12','14') ");
				setActionAlias("order_query!queryOrderlist.action?orderStatus=11");
			} else if ("12".equals(orderStatus)) {// 保单生效中
				// sql_where.append(" and UNIX_TIMESTAMP(DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s')) and a.orderstatus in ('7','10','12','14') ");
				// sql_where.append(" and UNIX_TIMESTAMP(DATE_FORMAT(b.endDate,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'))");
				sql_where
						.append(" and DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s')<=DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') and a.orderstatus in ('7','10','12','14') ");
				sql_where.append(" and DATE_FORMAT(b.endDate,'%Y-%m-%d %H:%i:%s')>=DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s')");
				setActionAlias("order_query!queryOrderlist.action?orderStatus=12");
			} else if ("13".equals(orderStatus)) {// 保单已过期
				// sql_where.append(" and UNIX_TIMESTAMP(DATE_FORMAT(b.endDate,'%Y-%m-%d %H:%i:%s'))<UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') ) and a.orderstatus in ('7','10','12','14') ");
				sql_where
						.append(" and DATE_FORMAT(b.endDate,'%Y-%m-%d %H:%i:%s')<DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') and a.orderstatus in ('7','10','12','14') ");
				setActionAlias("order_query!queryOrderlist.action?orderStatus=13");

			} else {
				sql_where.append(" and  a.orderstatus in(" + orderStatus + ")");
				setActionAlias("order_query!queryOrderlist.action?orderStatus=" + orderStatus + "");
			}
		}
		if (orderSn != null && !"".equals(orderSn.trim()) && !"undefined".equals(orderSn)) {
			if (orderSn.indexOf("订单编号") == -1) {
				sql_where.append(" and a.orderSn='" + orderSn + "' ");
			}
		}
		if (applicant != null && !"".equals(applicant.trim()) && !"undefined".equals(applicant)) {
			if (applicant.indexOf("投保") == -1) {
				sql_where.append(" and e.applicantname like '%" + applicant + "%' ");
			}
		}
		// 兑换的订单不查询order_query
		sql_where.append(" and a.channelsn!='jfsc'  "
				+ "  AND ( CASE WHEN  (a.renewalId IS NOT NULL  OR a.renewalId != '') AND a.orderStatus in ('7','9','10','11','12','13','14') THEN 1=1 ELSE  a.renewalId IS NULL  OR a.renewalId = '' END )  ");
		sql_where.append(" and a.channelsn NOT IN( SELECT b1.channelcode FROM channelinfo a1, channelinfo b1  WHERE a1.channelcode = 'jfsc' AND a1.InnerChannelCode = b1.ParentInnerChanelCode )  ");
		// 非删除状态的订单
		sql_where.append(" AND (a.dellFlag IS NULL OR a.dellFlag='') ");

		// paysn查询sql
		StringBuffer sql_paysn = new StringBuffer(
				" SELECT   DISTINCT COALESCE(IF(a.paysn='',NULL,a.paysn),a.id) AS pay_sn,a.paysn FROM sdorders a  LEFT JOIN tradesummaryinfo t ON a.PaySn = t.PaySn, sdinformation b, sdinformationappnt e  WHERE a.ordersn = b.ordersn AND b.informationsn = e.informationsn and a.memberid='"
						+ memberId + "' ");

		sql_paysn.append(sql_where);
		sql_paysn.append("  ORDER BY a.createdate DESC ");
		sql_paysn.append(limit_sql);
		// 查询paysn，无支付流水号的订单用订单表id代替
		DataTable dt_paysn = new QueryBuilder(sql_paysn.toString()).executeDataTable();
		String str_paysn = "";
		String str_id = "";
		for (int i = 0; i < dt_paysn.getRowCount(); i++) {
			String pay_sn = dt_paysn.getString(i, "pay_sn");
			String paysn = dt_paysn.getString(i, "paysn");
			if (StringUtil.isEmpty(paysn)) {
				str_id = str_id + "'" + pay_sn + "'" + ",";
			} else {
				str_paysn = str_paysn + "'" + pay_sn + "'" + ",";
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("  FROM ( SELECT t1.* ,(SELECT t3.policyNo FROM sdinformationrisktype t3 WHERE  t1.orderSn = t3.orderSn  LIMIT 1) AS policyNo "
				+ "FROM sdorders t1 LEFT JOIN teamuserorder t2 ON t1.orderSn = t2.orderSn WHERE (paysn IN (  ");
		if (StringUtil.isNotEmpty(str_paysn)) {
			sb.append(str_paysn.substring(0, str_paysn.length() - 1) + ")");
		} else {
			sb.append("'none')");
		}
		sb.append(" OR t1.id IN  (");
		if (StringUtil.isNotEmpty(str_id)) {
			sb.append(str_id.substring(0, str_id.length() - 1) + "))");
		} else {
			sb.append("'none'))");
		}
		sb.append(" AND t2.orderSn is null ");//暂时屏蔽团险订单
		sb.append(" ORDER BY t1.createdate DESC) ");
		sb.append(" a left join tradesummaryinfo t on a.PaySn=t.PaySn LEFT JOIN sdshoppingcart shop on a.ordersn=shop.ordersn, sdinformation b ,sdproduct c ,zdcode d,sdinformationappnt e ");
		sb.append("where  a.ordersn = b.ordersn and b.informationsn = e.informationsn and b.productid = c.productid and d.codetype='orderstatus' and d.codevalue = a.orderstatus ");
		sb.append(sql_where);
		sb.append(" order by a.createDate desc,a.activitySn desc ");

		try {
			String countSql = startCont.toString() + sql_where + ") as tab";
			DataTable dt1 = new QueryBuilder(countSql).executeDataTable();
			if (dt1.getRowCount() > 0) {
				count = dt1.getInt(0, 0);
			} else {
				count = 0;
			}
			String selectSql = startSelect + sb.toString();
			DataTable dt = new QueryBuilder(selectSql).executeDataTable();
			listOrders = new ArrayList<Map>();

			if (dt != null && dt.getRowCount() > 0) {
				int len = dt.getRowCount();
				for (int i = 0; i < len; i++) {
					Map<Object, Object> map = new HashMap<Object, Object>();
					DataColumn[] col = dt.getDataRow(i).getDataColumns();
					for (int j = 0; j < col.length; j++) {

						String colnam = col[j].getColumnName();
						map.put(colnam, dt.get(i, col[j].getColumnName()));
						// 理财险判断
						if (colnam.equals("productId")) {
							String val = (String) dt.get(i, col[j].getColumnName());
							String lcx = FinancingCheck.getLcx(val);
							map.put("isLcx", lcx);
						}

					}
					listOrders.add(map);
				}
			}
			// 取得加入购物车的订单
			Map<String, String> shopCartOrder = new HashMap<String, String>();
			DataTable shoppcartDt = new QueryBuilder("SELECT orderSn FROM sdshoppingcart where memberId = ?", memberId)
					.executeDataTable();
			if (shoppcartDt != null && shoppcartDt.getRowCount() > 0) {
				int len = shoppcartDt.getRowCount();
				for (int i = 0; i < len; i++) {
					shopCartOrder.put(shoppcartDt.getString(i, 0), "");
				}
			}
			// listOrders = jtd.obtainData(selectSql);
			// 格式化list中的分页数据，将paysn相同的订单信息依次排列
			listOrders = formatListByPaysn(listOrders, listOrders);
			Map map_message = new HashMap();
			for (Map<Object, Object> map : listOrders) {
				// 保单是否已超出保险期间
				String nowtime = DateUtil.getCurrentDateTime();
				String startDate = String.valueOf(map.get("startDate"));
				String endDate = String.valueOf(map.get("endDate"));
				if (DateUtil.compare(startDate, nowtime, DateUtil.Format_DateTime) <= 0
						&& DateUtil.compare(endDate, nowtime, DateUtil.Format_DateTime) >= 0) {
					map.put("order_status", "12");
				} else if (DateUtil.compare(startDate, nowtime, DateUtil.Format_DateTime) < 0
						&& DateUtil.compare(endDate, nowtime, DateUtil.Format_DateTime) < 0) {
					map.put("order_status", "13");
				} else if (DateUtil.compare(endDate, nowtime, DateUtil.Format_DateTime) > 0
						&& DateUtil.compare(startDate, nowtime, DateUtil.Format_DateTime) > 0) {
					map.put("order_status", "11");
				} else {
					return;
				}
				String channelsn = String.valueOf(map.get("channelsn"));
				// 判断是否是主站
				if ("wj".equals(channelsn)) {
					map.put("wjFlag", "true");
				} else {
					map.put("wjFlag", "");
				}

				// 淘宝的订单在会员中心不可点击产品名称链接到详细页
				if (String.valueOf(map.get("orderSn")).startsWith("TB")) {
					map.put("IsPublish", "N");
				}
				String activity_message = "";
				// 积分抵值金额
				BigDecimal point_amount = new BigDecimal("0");
				map.put("KID", StringUtil.md5Hex(PubFun.getKeyValue() + map.get("orderSn")));
				map.put("needUWCheckFlag", getneedUWCheckFlag(map.get("insuranceCompany") + ""));
				map.put("epathFlag", getPolicyFlag(map.get("orderSn") + "", map.get("insuranceCompany")+ ""));
				if (shopCartOrder.containsKey(map.get("orderSn"))) {
					map.put("shopcartshowflag", "1");
				} else {
					map.put("shopcartshowflag", "0");
				}

				// 查询实际支付总金额
				String payamount = getPayPriceByPaysn(String.valueOf(map.get("paySn")));
				// 查询保费总金额
				String sumPrice = getPayAmountByPaysn(String.valueOf(map.get("paySn")));
				if ("0".equals(payamount)) {
					if (StringUtil.isNotEmpty(String.valueOf(map.get("paySn")))) {
						QueryBuilder qb_payamount = new QueryBuilder(
								"select  round(sum(payPrice),2),round(sum(totalamount),2) from sdorders where paysn=?",
								String.valueOf(map.get("paySn")));
						DataTable dt_payamount = qb_payamount.executeDataTable();
						if (dt_payamount.getRowCount() > 0) {
							map.put("payamount", dt_payamount.getString(0, 0).replaceAll(",", ""));
							map.put("sumPrice", dt_payamount.getString(0, 1).replaceAll(",", ""));
						} else {
							logger.error("会员中心订单查询支付流水号查询不到数据，支付流水号为：{}", String.valueOf(map.get("paySn")));
						}
					} else {
						map.put("payamount", String.valueOf(map.get("payPrice")));
						map.put("sumPrice", String.valueOf(map.get("totalamount")));
					}
				} else {
					map.put("payamount", payamount);
					map.put("sumPrice", sumPrice);
				}

				// 无优惠活动
				if (map.get("activitySn") == null || ("null".equals(String.valueOf(map.get("activitySn"))))
						|| (String.valueOf(map.get("activitySn")).length() == 0)
						|| ("0".equals(String.valueOf(map.get("activitySn")).trim()))) {
					map.put("activity_message", "");
				} else {// 活动优惠
					if (StringUtil.isEmpty(map_message.get(map.get("paySn")))) {
						DataTable dtALL = new QueryBuilder("SELECT activitySn FROM sdorders WHERE paysn='"
								+ map.get("paySn") + "' group by activitySn").executeDataTable();
						if (dtALL != null && dtALL.getRowCount() > 0) {
							int row = dtALL.getRowCount();
							activity_message = "";
							for (int i = 0; i < row; i++) {
								QueryBuilder qb_activity = new QueryBuilder(
										"select description,accumulation,(SELECT codename FROM zdcode WHERE codetype = 'activity.type' AND codevalue =sdcouponactivityinfo.type ) typeName from  sdcouponactivityinfo where activitysn=? ",
										dtALL.getString(i, "activitySn").trim());
								DataTable dt_activity = qb_activity.executeDataTable();
								if (dt_activity != null && dt_activity.getRowCount() > 0) {
									// 获得活动描述
									activity_message += "<b>" + dt_activity.getString(0, 2) + "：</b>"
											+ String.valueOf(dt_activity.getString(0, 0) + "<br>");
								}
							}
							map_message.put(map.get("paySn"), activity_message);
						}
					} else {
						activity_message = String.valueOf(map_message.get(map.get("paySn")));
					}
				}
				// 无优惠活动
				if (map.get("couponSn") == null || ("null".equals(String.valueOf(map.get("couponSn"))))
						|| (String.valueOf(map.get("couponSn")).length() == 0)
						|| ("0".equals(String.valueOf(map.get("couponSn")).trim()))) {
					map.put("coupon_message", "");
				} else {
					// 活动优惠
					if (String.valueOf(map.get("couponSn")).trim().startsWith("HD")) {
						QueryBuilder qb_activity = new QueryBuilder(
								"select description,accumulation,(SELECT codename FROM zdcode WHERE codetype = 'activity.type' AND codevalue =sdcouponactivityinfo.type ) typeName from  sdcouponactivityinfo where activitysn=? ",
								String.valueOf(map.get("couponSn")).trim());
						DataTable dt_activity = qb_activity.executeDataTable();
						// 获得活动描述
						activity_message = "<b>" + dt_activity.getString(0, 2) + "：</b>"
								+ String.valueOf(dt_activity.getString(0, 0) + "<br>");
						map.put("coupon_message", "");
					} else {// 优惠券优惠
						QueryBuilder qb_coupon = new QueryBuilder(
								"select direction,parvalue from  couponinfo where couponSn=? ", String.valueOf(
										map.get("couponSn")).trim());
						DataTable dt_coupon = qb_coupon.executeDataTable();
						if (dt_coupon != null && dt_coupon.getRowCount() > 0) {
							map.put("coupon_message", String.valueOf(dt_coupon.getString(0, 0)));
						}
					}
				}
				map.put("activity_message", activity_message);
				// 积分抵值
				if (map.get("offsetPoint") == null || ("null".equals(String.valueOf(map.get("offsetPoint"))))
						|| (String.valueOf(map.get("offsetPoint")).length() == 0)
						|| ("0".equals(String.valueOf(map.get("offsetPoint")).trim()))) {
					map.put("point_message", "");
				} else {
					String point = String.valueOf(map.get("offsetPoint")).trim();
					// 积分换算一元单位
					String orderIntegral = String.valueOf(map.get("sumIntegral"));
					if (StringUtil.isNotEmpty(orderIntegral)) {
						point_amount = new BigDecimal(orderIntegral.trim());
					}
					String PointSumAmount = String.valueOf(map.get("PointSumAmount"));
					if (StringUtil.isNotEmpty(PointSumAmount)) {
						point_amount = new BigDecimal(PointSumAmount.trim());
					}
					map.put("point_message", "使用" + point + "积分,抵值" + String.valueOf(point_amount) + "元");
				}
				// 将优惠金额(活动、优惠券、积分)从支付金额中抵消
				BigDecimal pay_amount = new BigDecimal(StringUtil.isEmpty(map.get("payamount")) ? "0.00"
						: String.valueOf(map.get("payamount")));
				// 订单是否有优惠(活动、优惠券、积分)抵消情况的标识位 Y：有抵消
				String couponFlag = "";
				if (pay_amount.compareTo(new BigDecimal(StringUtil.isEmpty(map.get("sumPrice")) ? "0.00" : String
						.valueOf(map.get("sumPrice")))) == 0) {
					couponFlag = "N";
				} else {
					couponFlag = "Y";
				}
				map.put("payamount", String.valueOf(pay_amount));
				map.put("couponFlag", couponFlag);
				// 已支付7、已撤销9、有撤单10、已退保11、有退保12，除以上状态外，其余的状态全部没有付款
				if ("7".equals(String.valueOf(map.get("orderStatus")))
						|| Integer.valueOf(String.valueOf(map.get("orderStatus"))) >= 9) {
					map.put("orderStatusFlag", "Y");
				} else {
					map.put("orderStatusFlag", "N");
				}
				// 校验该支付流水号是否已经赋值rowspan true为已经赋值
				if (StringUtil.isNotEmpty(String.valueOf(map.get("paySn")))) {
					if (!checkRowspan(String.valueOf(map.get("paySn")))) {
						// table 合并单元格 rowspan属性赋值
						int rowspan = countRowspan(listOrders, String.valueOf(map.get("paySn")));
						if (rowspan == 0) {
							map.put("row_span_num", "1");
						} else {
							map.put("row_span_num", rowspan);
						}
					} else {
						// 优惠信息赋值
						for (Map<Object, Object> map_mess : listOrders) {
							if (String.valueOf(map.get("paySn")).equals(String.valueOf(map_mess.get("paySn")))) {
								if (StringUtil.isEmpty(String.valueOf(map_mess.get("activity_message")))
										&& StringUtil.isNotEmpty(String.valueOf(map.get("activity_message")))) {
									map_mess.put("activity_message", map.get("activity_message"));
								}
								if (StringUtil.isEmpty(String.valueOf(map_mess.get("coupon_message")))
										&& StringUtil.isNotEmpty(String.valueOf(map.get("coupon_message")))) {
									map_mess.put("coupon_message", map.get("coupon_message"));
								}
								if (StringUtil.isEmpty(String.valueOf(map_mess.get("point_message")))
										&& StringUtil.isNotEmpty(String.valueOf(map.get("point_message")))) {
									map_mess.put("point_message", map.get("point_message"));
								}
							}
						}
						map.put("row_span_num", "1");
					}
				} else {
					map.put("row_span_num", "1");
				}
				//已支付订单中 安联臻爱订单 ,符合续期条件 再次购买按钮变成续期按钮，
				
				if(map.get("productName").toString().indexOf("安联臻爱")>=0){
					if("6".equals(map.get("orderStatus").toString()) || "7".equals(map.get("orderStatus").toString())  || 
						"10".equals(map.get("orderStatus").toString())  || "12".equals(map.get("orderStatus").toString())  || "14".equals(map.get("orderStatus").toString()) ){
						Date MaxDate = DateUtil.addDay(DateUtil.strToDate(endDate, "yyyy-MM-dd HH:mm:ss"), 6);
						Date MinDate = DateUtil.addDay(DateUtil.addMonth(DateUtil.strToDate(endDate, "yyyy-MM-dd HH:mm:ss"), -2),-1);
						Date nowDate = new Date();
						if(DateUtil.belongCalendar(nowDate,MinDate,MaxDate)){
							map.put("zhenAiXuQi", "1");
							//map.put("policyNo", map.get("policyNo"));
							map.put("xuBaoUrl", (Config.getValue("ContextPath") + "wj/shop/renewal_insurance!zhenAiChoseProductPlan.action?policyNo=" + map.get("policyNo")));
						}
					}
				}
				// 查询过得总金额存储，以便下次使用，减少数据库访问次数
				Map map_payamount = new HashMap();
				map_payamount.put("sumPrice", String.valueOf(map.get("sumPrice")));
				Map map_payprice = new HashMap();
				map_payprice.put("payamount", String.valueOf(map.get("payamount")));
				payamount_list.add(map_payamount);
				payamount_list.add(map_payprice);

				// 通过比较产品更新时间与下单时间，来判断前端是否要弹出提示层
				String isUpdateFlag = String.valueOf(map.get("IsUpdateFlag"));
				String createDate = String.valueOf(map.get("createDate"));
				String isUpdateDate = String.valueOf(map.get("IsUpdateDate"));
				String orderStatus = String.valueOf(map.get("orderStatus"));

				// 如果产品状态是暂存或是待支付，产品中心设置了更新标识为“Y”, 并且更新时间大于下单时间，设置更新提示层标识
				if ( ("4".equals(orderStatus) || "5".equals(orderStatus)) &&"Y".equals(isUpdateFlag)
						&& DateUtil.compare(isUpdateDate, createDate, DateUtil.Format_DateTime) >= 0) {
					// 更新提示层标识
					map.put("updateWarningFlag", "Y");
				}

			}
			this.lastpage = ((count + pagesize - 1) / (pagesize));
			page_count = String.valueOf(lastpage);
			Map<String, String> param_map = new HashMap<String, String>();
			param_map.put("totalCounts", String.valueOf(count));
			param_map.put("idalias", "member_ordertable");
			getPageDataList(param_map);
			orderFlag = orderStatus;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
		}
	}

	/**
	 * 
	 * @Title: memberdeder
	 * @Description: TODO(会员中心查询订单列表)
	 * @return String 返回类型
	 * @author
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String memberqueryorder() {

		try {
			if (StringUtil.isNotEmpty(ldate)) {
				ldate = java.net.URLDecoder.decode(ldate, "UTF-8");
			}
			if (StringUtil.isNotEmpty(applicant)) {
				applicant = java.net.URLDecoder.decode(applicant, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}
		String memberId = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Member loginMember = null;
		if (!"tencent".equals(memberId)) {
			loginMember = memberService.load(memberId);
		} else {
			loginMember = bindInfoForLoginService.get(getSession("loginBindId").toString()).getmMember();
		}
		if (loginMember != null) {
			memberId = loginMember.getId();
		}
		// 跳转URL
		String URL = "list";
		// 会员中心首页初始化查询
		String limit_sql = " limit " + (page - 1) * pagesize + "," + pagesize;
		// 查看全部订单分页查询
		if ("1".equals(callType)) {
			limit_sql = " limit " + (page - 1) * newPageSize + "," + newPageSize;
		}
		StringBuffer startCont = new StringBuffer(
				"select count(1) from  (select DISTINCT COALESCE(a.paysn,a.id) AS paysn  from sdorders a left join tradesummaryinfo t on a.PaySn=t.PaySn ,sdinformation b,sdinformationappnt e  where  a.ordersn = b.ordersn and b.informationsn = e.informationsn and memberid='"
						+ memberId + "' ");
		String startSelect = "select a.id,a.createDate,a.orderStatus,d.CodeName,a.payPrice, round(a.totalamount,2) totalAmount,b.productId,b.productName,a.orderSn,c.IsPublish,a.activitySn,"
				+ "a.modifydate,b.insuranceCompany,c.HtmlPath ,''needUWCheckFlag,'' epathFlag , a.paySn, a.commentId,a.couponSn,a.offsetPoint,a.orderCoupon,a.orderIntegral,a.sumCoupon ,a.sumIntegral,t.PointSumAmount,shop.memberid ,a.channelsn,b.startDate,b.endDate  ";
		StringBuffer sql_where = new StringBuffer();
		if (ldate != null && !"".equals(ldate.trim()) && !"undefined".equals(ldate)) {
			if (ldate.indexOf("支付时间") == -1) {
				sql_where
						.append(" and UNIX_TIMESTAMP(DATE_FORMAT(t.CreateDate,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT('"
								+ ldate + " 00:00:00','%Y-%m-%d %H:%i:%s'))");
			}
		}
		if (hdate != null && !"".equals(hdate.trim()) && !"undefined".equals(hdate)) {
			sql_where
					.append(" and UNIX_TIMESTAMP(DATE_FORMAT(t.CreateDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('"
							+ hdate + " 23:59:59','%Y-%m-%d %H:%i:%s'))");
		}
		if (orderStatus != null && !"".equals(orderStatus) && !"99".equals(orderStatus) && !"undefined".equals(orderStatus)) {
			if ("11".equals(orderStatus)) {// 保单待生效
				sql_where
						.append(" and UNIX_TIMESTAMP(DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s'))>UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s')) and a.orderstatus in ('7','10','12','14') ");
				URL = "list_risktype";
			} else if ("12".equals(orderStatus)) {// 保单生效中
				sql_where
						.append(" and UNIX_TIMESTAMP(DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s')) and a.orderstatus in ('7','10','12','14') ");
				sql_where
						.append(" and UNIX_TIMESTAMP(DATE_FORMAT(b.endDate,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'))");
				URL = "list_risktype";
			} else if ("13".equals(orderStatus)) {// 保单已过期
				sql_where
						.append(" and UNIX_TIMESTAMP(DATE_FORMAT(b.endDate,'%Y-%m-%d %H:%i:%s'))<UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') ) and a.orderstatus in ('7','10','12','14') ");
				URL = "list_risktype";
			} else {
				sql_where.append(" and  a.orderstatus='" + orderStatus + "'");
			}
		}
		if (orderSn != null && !"".equals(orderSn.trim()) && !"undefined".equals(orderSn)) {
			if (orderSn.indexOf("订单编号") == -1) {
				sql_where.append(" and a.orderSn='" + orderSn + "' ");
			}
		}
		if (applicant != null && !"".equals(applicant.trim()) && !"undefined".equals(applicant)) {
			if (applicant.indexOf("投保") == -1) {
				sql_where.append(" and e.applicantname like '%" + applicant + "%' ");
			}
		}
		// 兑换的订单不查询
		sql_where.append(" and a.channelsn!='jfsc' ");
		sql_where.append(" and a.channelsn NOT IN( SELECT b1.channelcode FROM channelinfo a1, channelinfo b1  WHERE a1.channelcode = 'jfsc' AND a1.InnerChannelCode = b1.ParentInnerChanelCode )  ");
		// 非删除状态的订单
		sql_where.append(" AND (a.dellFlag IS NULL OR a.dellFlag='') ");
		// paysn查询sql
		StringBuffer sql_paysn = new StringBuffer(
				" SELECT   DISTINCT COALESCE(IF(a.paysn='',NULL,a.paysn),a.id) AS pay_sn,a.paysn FROM sdorders a  LEFT JOIN tradesummaryinfo t ON a.PaySn = t.PaySn, sdinformation b, sdinformationappnt e  WHERE a.ordersn = b.ordersn AND b.informationsn = e.informationsn and a.memberid='"
						+ memberId + "' ");
		sql_paysn.append(sql_where);
		sql_paysn.append("  ORDER BY a.createdate DESC ");
		sql_paysn.append(limit_sql);
		// 查询paysn，无支付流水号的订单用订单表id代替
		DataTable dt_paysn = new QueryBuilder(sql_paysn.toString()).executeDataTable();
		String str_paysn = "";
		String str_id = "";
		for (int i = 0; i < dt_paysn.getRowCount(); i++) {
			String pay_sn = dt_paysn.getString(i, "pay_sn");
			String paysn = dt_paysn.getString(i, "paysn");
			if (StringUtil.isEmpty(paysn)) {
				str_id = str_id + "'" + pay_sn + "'" + ",";
			} else {
				str_paysn = str_paysn + "'" + pay_sn + "'" + ",";
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("from  (SELECT * FROM sdorders t1 WHERE paysn IN (");
		if (StringUtil.isNotEmpty(str_paysn)) {
			sb.append(str_paysn.substring(0, str_paysn.length() - 1) + ")");
		} else {
			sb.append("'none')");
		}
		sb.append(" OR id IN  (");
		if (StringUtil.isNotEmpty(str_id)) {
			sb.append(str_id.substring(0, str_id.length() - 1) + ")");
		} else {
			sb.append("'none')");
		}
		sb.append(" ORDER BY t1.createdate DESC) ");
		sb.append(" a left join tradesummaryinfo t on a.PaySn=t.PaySn LEFT JOIN sdshoppingcart shop on a.ordersn=shop.ordersn, sdinformation b ,sdproduct c ,zdcode d,sdinformationappnt e ");
		sb.append("where a.ordersn = b.ordersn and b.informationsn = e.informationsn and b.productid = c.productid and d.codetype='orderstatus' and d.codevalue = a.orderstatus ");
		sb.append(sql_where);
		sb.append(" order by a.createDate desc,a.activitySn desc ");
		JdbcTemplateData jtd = new JdbcTemplateData();
		try {
			String countSql = startCont.toString() + sql_where + ") as tab";
			DataTable dt1 = new QueryBuilder(countSql).executeDataTable();
			if (dt1.getRowCount() > 0) {
				count = dt1.getInt(0, 0);
			} else {
				count = 0;
			}
			String selectSql = startSelect + sb.toString();
			DataTable dt = new QueryBuilder(selectSql).executeDataTable();
			listOrders = new ArrayList<Map>();

			if (dt != null && dt.getRowCount() > 0) {
				int len = dt.getRowCount();
				for (int i = 0; i < len; i++) {
					Map<Object, Object> map = new HashMap<Object, Object>();
					DataColumn[] col = dt.getDataRow(i).getDataColumns();
					for (int j = 0; j < col.length; j++) {
						map.put(col[j].getColumnName(), dt.get(i, col[j].getColumnName()));
					}
					listOrders.add(map);
				}
			}
			// listOrders = jtd.obtainData(selectSql);
			// 格式化list中的分页数据，将paysn相同的订单信息依次排列
			listOrders = formatListByPaysn(listOrders, listOrders);
			Map map_message = new HashMap();
			for (Map<Object, Object> map : listOrders) {
				// 保单是否已超出保险期间
				String nowtime = DateUtil.getCurrentDateTime();
				String startDate = String.valueOf(map.get("startDate"));
				String endDate = String.valueOf(map.get("endDate"));
				if (DateUtil.compare(startDate, nowtime, DateUtil.Format_DateTime) <= 0
						&& DateUtil.compare(endDate, nowtime, DateUtil.Format_DateTime) >= 0) {
					map.put("order_status", "12");
				} else if (DateUtil.compare(startDate, nowtime, DateUtil.Format_DateTime) < 0
						&& DateUtil.compare(endDate, nowtime, DateUtil.Format_DateTime) < 0) {
					map.put("order_status", "11");
				} else if (DateUtil.compare(endDate, nowtime, DateUtil.Format_DateTime) > 0
						&& DateUtil.compare(startDate, nowtime, DateUtil.Format_DateTime) > 0) {
					map.put("order_status", "13");
				} else {
					return null;
				}
				String channelsn = String.valueOf(map.get("channelsn"));
				// 判断是否是主站
				if ("wj".equals(channelsn)) {
					map.put("wjFlag", "true");
				} else {
					map.put("wjFlag", "");
				}

				// 淘宝的订单在会员中心不可点击产品名称链接到详细页
				if (String.valueOf(map.get("orderSn")).startsWith("TB")) {
					map.put("IsPublish", "N");
				}
				String activity_message = "";
				// 积分抵值金额
				BigDecimal point_amount = new BigDecimal("0");
				map.put("KID", StringUtil.md5Hex(PubFun.getKeyValue() + map.get("orderSn")));
				map.put("needUWCheckFlag", getneedUWCheckFlag(map.get("insuranceCompany") + ""));
				map.put("epathFlag", getPolicyFlag(map.get("orderSn") + "", map.get("insuranceCompany")+ ""));
				if (String.valueOf(map.get("paySn")).endsWith("D") || String.valueOf(map.get("paySn")).endsWith("00")
						|| String.valueOf(map.get("orderSn")).startsWith("wp")) {
					map.put("shopcartshowflag", "0");
				} else {
					map.put("shopcartshowflag", "1");
				}
				// 查询实际支付总金额
				String payamount = getPayPriceByPaysn(String.valueOf(map.get("paySn")));
				// 查询保费总金额
				String sumPrice = getPayAmountByPaysn(String.valueOf(map.get("paySn")));
				if ("0".equals(payamount)) {
					if (StringUtil.isNotEmpty(String.valueOf(map.get("paySn")))) {
						QueryBuilder qb_payamount = new QueryBuilder(
								"select  round(sum(payPrice),2),round(sum(totalamount),2) from sdorders where paysn=?",
								String.valueOf(map.get("paySn")));
						DataTable dt_payamount = qb_payamount.executeDataTable();
						if (dt_payamount.getRowCount() > 0) {
							map.put("payamount", dt_payamount.getString(0, 0).replaceAll(",", ""));
							map.put("sumPrice", dt_payamount.getString(0, 1).replaceAll(",", ""));
						} else {
							logger.error("会员中心订单查询支付流水号查询不到数据，支付流水号为：{}", String.valueOf(map.get("paySn")));
						}
					} else {
						map.put("payamount", String.valueOf(map.get("payPrice")));
						map.put("sumPrice", String.valueOf(map.get("totalamount")));
					}
				} else {
					map.put("payamount", payamount);
					map.put("sumPrice", sumPrice);
				}

				// 无优惠活动
				if (map.get("activitySn") == null || ("null".equals(String.valueOf(map.get("activitySn"))))
						|| (String.valueOf(map.get("activitySn")).length() == 0)
						|| ("0".equals(String.valueOf(map.get("activitySn")).trim()))) {
					map.put("activity_message", "");
				} else {// 活动优惠
					if (StringUtil.isEmpty(map_message.get(map.get("paySn")))) {
						DataTable dtALL = new QueryBuilder("SELECT activitySn FROM sdorders WHERE paysn='"
								+ map.get("paySn") + "' group by activitySn").executeDataTable();
						if (dtALL != null && dtALL.getRowCount() > 0) {
							int row = dtALL.getRowCount();
							activity_message = "";
							for (int i = 0; i < row; i++) {
								QueryBuilder qb_activity = new QueryBuilder(
										"select description,accumulation,(SELECT codename FROM zdcode WHERE codetype = 'activity.type' AND codevalue =sdcouponactivityinfo.type ) typeName from  sdcouponactivityinfo where activitysn=? ",
										dtALL.getString(i, "activitySn").trim());
								DataTable dt_activity = qb_activity.executeDataTable();
								if (dt_activity != null && dt_activity.getRowCount() > 0) {
									// 获得活动描述
									activity_message += "<b>" + dt_activity.getString(0, 2) + "：</b>"
											+ String.valueOf(dt_activity.getString(0, 0) + "<br>");
								}
							}
							map_message.put(map.get("paySn"), activity_message);
						}
					} else {
						activity_message = String.valueOf(map_message.get(map.get("paySn")));
					}
				}
				// 无优惠活动
				if (map.get("couponSn") == null || ("null".equals(String.valueOf(map.get("couponSn"))))
						|| (String.valueOf(map.get("couponSn")).length() == 0)
						|| ("0".equals(String.valueOf(map.get("couponSn")).trim()))) {
					map.put("coupon_message", "");
				} else {
					// 活动优惠
					if (String.valueOf(map.get("couponSn")).trim().startsWith("HD")) {
						QueryBuilder qb_activity = new QueryBuilder(
								"select description,accumulation,(SELECT codename FROM zdcode WHERE codetype = 'activity.type' AND codevalue =sdcouponactivityinfo.type ) typeName from  sdcouponactivityinfo where activitysn=? ",
								String.valueOf(map.get("couponSn")).trim());
						DataTable dt_activity = qb_activity.executeDataTable();
						// 获得活动描述
						activity_message = "<b>" + dt_activity.getString(0, 2) + "：</b>"
								+ String.valueOf(dt_activity.getString(0, 0) + "<br>");
						map.put("coupon_message", "");
					} else {// 优惠券优惠
						QueryBuilder qb_coupon = new QueryBuilder(
								"select direction,parvalue from  couponinfo where couponSn=? ", String.valueOf(
										map.get("couponSn")).trim());
						DataTable dt_coupon = qb_coupon.executeDataTable();
						map.put("coupon_message", String.valueOf(dt_coupon.getString(0, 0)));
					}
				}
				map.put("activity_message", activity_message);
				// 积分抵值
				if (map.get("offsetPoint") == null || ("null".equals(String.valueOf(map.get("offsetPoint"))))
						|| (String.valueOf(map.get("offsetPoint")).length() == 0)
						|| ("0".equals(String.valueOf(map.get("offsetPoint")).trim()))) {
					map.put("point_message", "");
				} else {
					String point = String.valueOf(map.get("offsetPoint")).trim();
					// 积分换算一元单位
					String orderIntegral = String.valueOf(map.get("sumIntegral"));
					if (StringUtil.isNotEmpty(orderIntegral)) {
						point_amount = new BigDecimal(orderIntegral.trim());
					}
					String PointSumAmount = String.valueOf(map.get("PointSumAmount"));
					if (StringUtil.isNotEmpty(PointSumAmount)) {
						point_amount = new BigDecimal(PointSumAmount.trim());
					}
					map.put("point_message", "使用" + point + "积分,抵值" + String.valueOf(point_amount) + "元");
				}
				// 将优惠金额(活动、优惠券、积分)从支付金额中抵消
				BigDecimal pay_amount = new BigDecimal(StringUtil.isEmpty(map.get("payamount")) ? "0.00"
						: String.valueOf(map.get("payamount")));
				// 订单是否有优惠(活动、优惠券、积分)抵消情况的标识位 Y：有抵消
				String couponFlag = "";
				if (pay_amount.compareTo(new BigDecimal(StringUtil.isEmpty(map.get("sumPrice")) ? "0.00" : String
						.valueOf(map.get("sumPrice")))) == 0) {
					couponFlag = "N";
				} else {
					couponFlag = "Y";
				}
				map.put("payamount", String.valueOf(pay_amount));
				map.put("couponFlag", couponFlag);
				// 已支付7、已撤销9、有撤单10、已退保11、有退保12，除以上状态外，其余的状态全部没有付款
				if ("7".equals(String.valueOf(map.get("orderStatus")))
						|| Integer.valueOf(String.valueOf(map.get("orderStatus"))) >= 9) {
					map.put("orderStatusFlag", "Y");
				} else {
					map.put("orderStatusFlag", "N");
				}
				// 校验该支付流水号是否已经赋值rowspan true为已经赋值
				if (StringUtil.isNotEmpty(String.valueOf(map.get("paySn")))) {
					if (!checkRowspan(String.valueOf(map.get("paySn")))) {
						// table 合并单元格 rowspan属性赋值
						int rowspan = countRowspan(listOrders, String.valueOf(map.get("paySn")));
						if (rowspan == 0) {
							map.put("row_span_num", "1");
						} else {
							map.put("row_span_num", rowspan);
						}
					} else {
						// 优惠信息赋值
						for (Map<Object, Object> map_mess : listOrders) {
							if (String.valueOf(map.get("paySn")).equals(String.valueOf(map_mess.get("paySn")))) {
								if (StringUtil.isEmpty(String.valueOf(map_mess.get("activity_message")))
										&& StringUtil.isNotEmpty(String.valueOf(map.get("activity_message")))) {
									map_mess.put("activity_message", map.get("activity_message"));
								}
								if (StringUtil.isEmpty(String.valueOf(map_mess.get("coupon_message")))
										&& StringUtil.isNotEmpty(String.valueOf(map.get("coupon_message")))) {
									map_mess.put("coupon_message", map.get("coupon_message"));
								}
								if (StringUtil.isEmpty(String.valueOf(map_mess.get("point_message")))
										&& StringUtil.isNotEmpty(String.valueOf(map.get("point_message")))) {
									map_mess.put("point_message", map.get("point_message"));
								}
							}
						}
						map.put("row_span_num", "1");
					}
				} else {
					map.put("row_span_num", "1");
				}
				// 查询过得总金额存储，以便下次使用，减少数据库访问次数
				Map map_payamount = new HashMap();
				map_payamount.put("sumPrice", String.valueOf(map.get("sumPrice")));
				Map map_payprice = new HashMap();
				map_payprice.put("payamount", String.valueOf(map.get("payamount")));
				payamount_list.add(map_payamount);
				payamount_list.add(map_payprice);
			}
			this.lastpage = ((count + pagesize - 1) / (pagesize));
			orderFlag = orderStatus;
			return URL;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String memberquerychangeorder() {

		int pagesize = 4;
		String memberId = "";
		Member loginMember = getLoginMember();
		if (loginMember != null) {
			memberId = loginMember.getId();
		}
		setActionAlias("order_query!memberquerychangeorder.action");

		// 跳转URL
		String URL = "exchangeList";
		// 会员中心首页初始化查询
		String limit_sql = " limit " + (page - 1) * pagesize + "," + pagesize;

		StringBuffer startCont = new StringBuffer(
				"select count(1) from sdorders a ,sdinformation b where a.ordersn = b.ordersn and a.memberid='" + memberId
						+ "' ");
		String startSelect = "select a.id,a.createDate,a.orderStatus,round(a.totalamount,2) totalAmount,b.productName,a.orderSn,c.IsPublish,"
				+ "b.insuranceCompany,c.JFHtmlPath ,''needUWCheckFlag,'' epathFlag , a.channelsn, b.startDate,b.endDate  ";
		StringBuffer sql_where = new StringBuffer();
		sql_where.append(" and (a.channelsn='jfsc'");
		sql_where.append(" or a.channelsn IN( SELECT b1.channelcode FROM channelinfo a1, channelinfo b1  WHERE a1.channelcode = 'jfsc' AND a1.InnerChannelCode = b1.ParentInnerChanelCode ))  ");
		if ("11".equals(orderStatus)) {// 保单待生效
			sql_where
					.append(" and UNIX_TIMESTAMP(DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s'))>UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s')) ");
		} else if ("12".equals(orderStatus)) {// 保单生效中
			sql_where
					.append(" and UNIX_TIMESTAMP(DATE_FORMAT(b.startDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s')) ");
			sql_where
					.append(" and UNIX_TIMESTAMP(DATE_FORMAT(b.endDate,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s'))");
		} else if ("13".equals(orderStatus)) {// 保单已过期
			sql_where
					.append(" and UNIX_TIMESTAMP(DATE_FORMAT(b.endDate,'%Y-%m-%d %H:%i:%s'))<UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') ) ");
		}
		sql_where.append(" and  a.orderstatus in ('7','10','12','14')");

		// 非删除状态的订单
		sql_where.append(" AND (a.dellFlag IS NULL OR a.dellFlag='') ");

		StringBuffer sb = new StringBuffer();
		sb.append("from sdorders a, sdinformation b, sdproduct c where a.ordersn = b.ordersn and b.productid = c.productid  and a.memberid='"
				+ memberId + "' ");
		sb.append(sql_where);
		sb.append(" order by a.createDate desc");
		sb.append(limit_sql);

		try {
			String countSql = startCont.toString() + sql_where;
			DataTable dt1 = new QueryBuilder(countSql).executeDataTable();
			if (dt1.getRowCount() > 0) {
				count = dt1.getInt(0, 0);
			} else {
				count = 0;
			}
			String selectSql = startSelect + sb.toString();
			DataTable dt = new QueryBuilder(selectSql).executeDataTable();
			listOrders = new ArrayList<Map>();

			if (dt != null && dt.getRowCount() > 0) {
				int len = dt.getRowCount();
				for (int i = 0; i < len; i++) {
					Map<Object, Object> map = new HashMap<Object, Object>();
					DataColumn[] col = dt.getDataRow(i).getDataColumns();
					for (int j = 0; j < col.length; j++) {
						map.put(col[j].getColumnName(), dt.get(i, col[j].getColumnName()));
					}
					listOrders.add(map);
				}
			}

			for (Map<Object, Object> map : listOrders) {
				// 保单是否已超出保险期间
				String nowtime = DateUtil.getCurrentDateTime();
				String startDate = String.valueOf(map.get("startDate"));
				String endDate = String.valueOf(map.get("endDate"));
				if (DateUtil.compare(startDate, nowtime, DateUtil.Format_DateTime) <= 0
						&& DateUtil.compare(endDate, nowtime, DateUtil.Format_DateTime) >= 0) {
					map.put("order_status", "12");
				} else if (DateUtil.compare(startDate, nowtime, DateUtil.Format_DateTime) < 0
						&& DateUtil.compare(endDate, nowtime, DateUtil.Format_DateTime) < 0) {
					map.put("order_status", "13");
				} else if (DateUtil.compare(endDate, nowtime, DateUtil.Format_DateTime) > 0
						&& DateUtil.compare(startDate, nowtime, DateUtil.Format_DateTime) > 0) {
					map.put("order_status", "11");
				}

				map.put("KID", StringUtil.md5Hex(PubFun.getKeyValue() + map.get("orderSn")));
				map.put("needUWCheckFlag", getneedUWCheckFlag(map.get("insuranceCompany") + ""));
				map.put("epathFlag", getPolicyFlag(map.get("orderSn") + "", map.get("insuranceCompany")+ ""));
			}
			this.lastpage = ((count + pagesize - 1) / (pagesize));
			orderFlag = orderStatus;
			page_Index = String.valueOf(page);
			page_count = String.valueOf(lastpage);
			Map<String, String> param_map = new HashMap<String, String>();
			param_map.put("totalCounts", String.valueOf(count));
			getPageDataList(param_map);
			return URL;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * 
	 * @Title: countRowspan
	 * @Description: TODO(获得实际支付金额的table的合并的行数)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	@SuppressWarnings("unchecked")
	private int countRowspan(List<Map> listOrders2, String paysn) {

		int i = 0;
		for (Map<Object, Object> map : listOrders2) {
			if (StringUtil.isNotEmpty(String.valueOf(map.get("paySn")))) {
				if (String.valueOf(map.get("paySn")).equals(paysn)) {
					i = i + 2;
				}
			}
		}
		return i - 1;
	}

	/**
	 * 
	 * @Title: checkRowspan
	 * @Description: TODO(校验该支付流水号是否已经赋值rowspan true为已经赋值)
	 * @return boolean 返回类型
	 * @author zhangjing
	 */
	private boolean checkRowspan(String paysn) {

		for (int i = 0; i < paysn_rowspan_list.size(); i++) {
			if (String.valueOf(paysn_rowspan_list.get(i)).trim().equals(paysn)) {
				return true;
			}
		}
		paysn_rowspan_list.add(paysn);
		return false;
	}

	/**
	 * 
	 * @Title: getPayAmountByPaysn
	 * @Description: TODO(通过支付paysn查询是否已查询过订单总金额,减少数据库交互次数)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private String getPayAmountByPaysn(String paysn) {

		for (Map<String, String> map : payamount_list) {
			if (String.valueOf(map.get("paysn")).equals(paysn)) {
				return map.get("totalamount");
			}
		}
		return "0";
	}

	/**
	 * 
	 * @Title: getPayPriceByPaysn
	 * @Description: TODO(通过支付paysn查询是否已查询过支付总金额,减少数据库交互次数)
	 * @return String 返回类型
	 * @author jiaomengying
	 */
	private String getPayPriceByPaysn(String paysn) {

		for (Map<String, String> map : payamount_list) {
			if (String.valueOf(map.get("paysn")).equals(paysn)) {
				return map.get("payamount");
			}
		}
		return "0";
	}

	/**
	 * 
	 * @Title: formatListByPaysn
	 * @Description: TODO(将分页的结果根据paysn依次排序)
	 * @return List 返回类型
	 * @author zhangjing
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })

	private List<Map> formatListByPaysn(List<Map> list, List<Map> list_bak) {

		for (int i = 0; i < list.size(); i++) {
			String paysn = String.valueOf(list.get(i).get("paySn"));
			if (StringUtil.isNotEmpty(paysn)) {
				for (int k = 0; k < list_bak.size(); k++) {
					if (i == k) {

						continue;
					} else {
						String paysn_bak = String.valueOf(list_bak.get(k).get("paySn"));
						if (StringUtil.isNotEmpty(paysn_bak)) {
							if (paysn.trim().equals(paysn_bak.trim())) {
								Map<Object, Object> map_add = list_bak.get(k);
								list.remove(k);
								list.add(i + 1, map_add);
								i++;
							}
						}
					}
				}
			}
		}
		return list;
	}

    /**
     *
     * @param ordersn
     * @return
     */
	public String getPolicyFlag(String ordersn, String comCode) {

	    // 因为有地方调用不要comCode,所以不需要的地方传值null，用此进行逻辑判断
	    boolean bCheckElectronicPathFlag = checkElectronicPathFlag(ordersn);
        if (comCode == null) {
            if (bCheckElectronicPathFlag) {
                return "1";
            } else {
                return "0";
            }
        } else {
            boolean bCheckPolicyFlag = checkPolicyFlag(ordersn, comCode);

            if (bCheckElectronicPathFlag || bCheckPolicyFlag) {
                return "1";
            }
            return "0";
        }
	}

    /**
     * 判断保单是否有电子保单下载
     *
     * @param ordersn
     * @return
     */
	private boolean checkElectronicPathFlag(String ordersn) {
        if (StringUtil.isNotEmpty(ordersn)) {
            String sql = "select electronicpath from sdinformationrisktype where electronicpath is not null and electronicpath <> '' and ordersn = ? ";
            DataTable dt = new QueryBuilder(sql, ordersn).executeDataTable();
            if (dt.getRowCount() > 0) {
                return true;
            } else {
                sql = "select riskCode from sdinformationrisktype where (riskCode Like '2049%' or riskCode Like '2034%') and appStatus = 1 and ordersn = ? ";
                dt = new QueryBuilder(sql, ordersn).executeDataTable();
                if (dt.getRowCount() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

     /**
      * 判断保险公司可以退保，并且有保单号存在
      *
      * @param ordersn
      * @return
      */
    private boolean checkPolicyFlag (String ordersn, String comCode) {
        //判断保险公司是否可以退保
        String sql = "select Memo from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
        QueryBuilder qb = new QueryBuilder(sql);
        qb.add("TBDealCancelClassName");
        qb.add("TBDealCancelClassName");
        qb.add(comCode);
        String className = qb.executeString();

        //
        sql = "SELECT COUNT(*) FROM sdinformationrisktype WHERE ordersn = ? AND appStatus = '1'";
        qb = new QueryBuilder(sql, ordersn);
        int count = qb.executeInt();
        if (StringUtil.isNotEmpty(className) && count > 0) {
            return true;
        }
        return false;
    }

	public String getneedUWCheckFlag(String comCode) {

		if (StringUtil.isNotEmpty(comCode)) {
			String sql = "select prop1 from zdcode where CodeType='UWCheckClassName' and ParentCode='UWCheckClassName' and CodeValue=?";
			DataTable dt = new QueryBuilder(sql, comCode).executeDataTable();
			if (dt.getRowCount() > 0) {
				String flag = dt.getString(0, 0);
				if (!"N".equals(flag)) {
					return "1";
				}
			}
		}
		return "0";
	}

	/**
	 * 
	 * @return 根据条件筛选查询的结果
	 */
	public String list() {

		return queryOrder();
	}

	/**
	 * 日期比较.
	 * 
	 * @param startDate
	 * @param endDate
	 * @param sysDate
	 * @return
	 * @throws ParseException
	 */
	public static String compare_date(String startDate, String endDate,
			String sysDate) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// 系统日期
		Long sysd = sdf.parse(sysDate).getTime();
		Long sd = sdf.parse(startDate).getTime();
		Long ed = sdf.parse(endDate).getTime();
		// 1.已承保 2.已撤单 3.未起保 4.已到期

		if (sysd > sd && sysd < ed) {
			return "1";
		} else if (sysd < sd) {
			return "3";
		}

		return "4";
	}

	/**
	 * 格式化日期.
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String formatDate(String date) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日");
		Date d = sdf.parse(date);
		return f.format(d);
	}

	/**
	 * 保单路径替换.
	 * 
	 * @param oldPath
	 * @return
	 */
	public static String replacePath(String oldPath) {

		String newPath = "";
		if (!StringUtil.isEmpty(oldPath)) {
			String serverPath = Config.getValue("newPolicyPath");
			newPath = oldPath.replace(serverPath, "");
		}

		return newPath;
	}

	/**
	 * 保单下载.
	 * 
	 * @return
	 */
	public String receiptsDownload() {

		if (getLoginMember() == null) {
			addActionError("请先登录");
			return ERROR;
		}
		HttpServletRequest request = ServletActionContext.getRequest();

		QueryBuilder query;

		StringBuffer queryRecrips = new StringBuffer();
		queryRecrips
				.append("select s.id,s1.recognizeeSn,s.riskCode,s.ordersn,s1.recognizeeName,riskName,svaliDate,evaliDate,electronicPath,appStatus,date_format(now(),'%Y-%m-%d %H:%i:%S') as sysDate,s.createDate  "
						+ "from SDInformationRiskType s,SDInformationInsured s1 "
						+ "where s.recognizeeSn = s1.recognizeeSn and s.ordersn = ? ");

		try {

			query = new QueryBuilder(queryRecrips.toString(), orderSn);
			DataTable dt = query.executeDataTable();
			count = dt.getRowCount();
			int pagenew = ((count + Order.DEFAULT_ORDER_LIST_PAGE_SIZE - 1) / (Order.DEFAULT_ORDER_LIST_PAGE_SIZE - 1));
			int remainder = (count + Order.DEFAULT_ORDER_LIST_PAGE_SIZE - 1) % (Order.DEFAULT_ORDER_LIST_PAGE_SIZE - 1);
			if (0 == remainder) {
				this.lastpage = pagenew - 1;
			} else {
				this.lastpage = pagenew;
			}
			queryRecrips.append(" order by createDate desc limit " + (page - 1) * pagesize + "," + pagesize + "");

			query = new QueryBuilder(queryRecrips.toString(), orderSn);
			dt = query.executeDataTable();

			List<HashMap<String, String>> rdList = new ArrayList<HashMap<String, String>>();

			for (int i = 0; i < dt.getRowCount(); i++) {
				HashMap<String, String> dataHashMap = new HashMap<String, String>();
				dataHashMap.put("id", dt.getString(i, "id"));
				dataHashMap.put("recognizeeSn", dt.getString(i, "recognizeeSn"));
				dataHashMap.put("orderSn", dt.getString(i, "ordersn"));
				dataHashMap.put("recognizeeName", dt.getString(i, "recognizeeName"));
				dataHashMap.put("riskName", dt.getString(i, "riskName"));

				dataHashMap.put("createDate", dt.getString(i, "createDate"));

				String appStatus = dt.getString(i, "appStatus");
				String svaliDate = dt.getString(i, "svaliDate");
				String evaliDate = dt.getString(i, "evaliDate");
				String electronicPath = dt.getString(i, "electronicPath");
				String riskCode = dt.getString(i, "riskCode");
				String sysDate = dt.getString(i, "sysDate");

				// 1.已承保 2.已撤单 3.未起保 4.已到期
				if ("1".equals(appStatus) || "3".equals(appStatus) || "4".equals(appStatus)) {
					String status = compare_date(svaliDate, evaliDate, sysDate);
					dataHashMap.put("appStatus", status);
				} else if ("2".equals(appStatus)) {
                    dataHashMap.put("appStatus", appStatus);
                }
				dataHashMap.put("svaliDate", formatDate(svaliDate));
				dataHashMap.put("evaliDate", formatDate(evaliDate));
				dataHashMap.put("electronicPath", electronicPath);
				dataHashMap.put("riskCode", StringUtil.isEmpty(riskCode) ? "" : riskCode.substring(0, 4));
				dataHashMap.put("sysDate", sysDate);

				rdList.add(dataHashMap);
			}
			Map<String, String> param_map = new HashMap<String, String>();
			param_map.put("totalCounts", String.valueOf(count));
			page_Index = String.valueOf(page);
			page_count = String.valueOf(lastpage);
			getPageDataList(param_map);
			request.setAttribute("rdList", rdList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ERROR;
		}

		return "phdownload";
	}

	/**
	 * 电子保单下载处理
	 */
	public String electronicDownload() {

		// 交易返回报文
		Document doc = new Document();

		Map<String, String> resultMap = new HashMap<String, String>();

		try {

			HttpServletRequest request = ServletActionContext.getRequest();
			String recognizeeSn = request.getParameter("recognizeeSn");

			// 针对浏览器回退校验
			String sql = "select a.electronicpath,a.policyNo,a.insureDate,a.createDate,a.informationSn from sdinformationrisktype a where a.recognizeeSn = ?";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(recognizeeSn);

			DataTable dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				String electronicpath = dt.getString(0, 0);
				String serialNo = dt.getString(0, 1);
				String insureDate = dt.getString(0, 2);
				String informationSn = dt.getString(0, 4);
				if (StringUtil.isEmpty(electronicpath)) {

					String riskCode = request.getParameter("riskCode");
					String id = request.getParameter("id");

					// 保险公司编号
					String comCode = riskCode.substring(0, 4);
					// 请求参数
					String param = "";
					// 调用经代通电子保单下载接口
					QueryOrders qo = new QueryOrders();

					if (StringUtil.isEmpty(insureDate)) {
						insureDate = DateUtil.toString(dt.getDate(0, 3), "yyyyMMdd");
					} else {
						insureDate = insureDate.substring(0, 10).replace("-", "");
					}
					NameValuePair serialNoP = new NameValuePair("serialNo", serialNo);
					NameValuePair comCodeP = new NameValuePair("comCode", comCode);
					NameValuePair insureDateP = new NameValuePair("insureDate", insureDate);
					NameValuePair policyPath = new NameValuePair("policyPath", "");
					QueryBuilder qb1 = new QueryBuilder(
							"select (CASE WHEN riskType='11' THEN 'jc' ELSE 'ly' END) from sdinformation where informationSn = ?");
					qb1.add(informationSn);
					String channel = qb1.executeString();
					NameValuePair channelP = new NameValuePair("channel", channel);
					NameValuePair[] pair = new NameValuePair[] { serialNoP, comCodeP, insureDateP, policyPath, channelP };
					String responseXml = qo.electronicDownloadInterface(param, pair);
					doc = new SAXBuilder().build(new ByteArrayInputStream(responseXml.getBytes("GBK")));

					String isSuccessful = doc.getRootElement().getChild("Head").getChildTextTrim("isSuccessful");
					String message = doc.getRootElement().getChild("Head").getChildTextTrim("message");

					// 更新保单表
					if ("Y".equals(isSuccessful)) {

						SDInformationRiskTypeSchema riskType = new SDInformationRiskTypeSchema();
						riskType.setId(id);
						riskType.fill();

						electronicpath = doc.getRootElement().getChild("Body").getChildTextTrim("policyPath");
						riskType.setelectronicPath(electronicpath);

						if (riskType.update()) {
							resultMap.put("STATYS", "1");
							resultMap.put("policyPath", electronicpath);
						} else {
							resultMap.put("STATYS", "0");
							resultMap.put("Message", "系统异常，请联系客服！");
						}

					} else {
						logger.error("会员中心-电子下载失败-流水号:{},错误信息:{}", serialNo, message);

						resultMap.put("STATYS", "0");
						resultMap.put("Message", "保单下载失败，请联系客服！");
					}
				} else {
					resultMap.put("STATYS", "1");
					resultMap.put("policyPath", electronicpath);
				}
			} else {
				resultMap.put("STATYS", "0");
				resultMap.put("Message", "系统异常，无保单数据，请联系客服！");
			}

		} catch (Exception e) {
			resultMap.put("STATYS", "0");
			resultMap.put("Message", "系统异常，请联系客服！");
			logger.error(e.getMessage(), e);
		}

		JSONObject jsonObject = new JSONObject(resultMap);
		return ajax(jsonObject.toString(), "text/html");

	}

	/**
	 * 
	 * @Title: dellMemberCenterOrder
	 * @Description: TODO(删除会员中心订单)
	 * @return String 返回类型
	 * @author
	 */
	public String dellMemberCenterOrder() {

		Map<String, Object> map = new HashMap<String, Object>();
		String memberId = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Member loginMember = null;
		if (!"tencent".equals(memberId)) {
			loginMember = memberService.load(memberId);
		} else {
			loginMember = bindInfoForLoginService.get(getSession("loginBindId").toString()).getmMember();
		}
		if (loginMember != null) {
			memberId = loginMember.getId();
		}
		if (StringUtil.isEmpty(memberId)) {
			map.put("status", "NOLOG");// 未登录
		}
		else if (StringUtil.isEmpty(orderSn)) {
			map.put("status", "NOORDER");// 订单信息为空
		} else {
			QueryBuilder qb_dell = new QueryBuilder(" update sdorders set dellflag='Y' where memberid=? and ordersn=? ");
			qb_dell.add(memberId);
			qb_dell.add(orderSn);
			if (qb_dell.executeNoQuery() == 1) {
				map.put("status", "Y");
			} else {
				map.put("status", "N");
			}
			if ("1".equals(request.getParameter("pointExchangeFlag"))) {
				qb_dell = new QueryBuilder(" update PointExchangeInfo set prop1='Y' where memberid=? and ordersn=? ");
				qb_dell.add(memberId);
				qb_dell.add(orderSn);
				if (qb_dell.executeNoQuery() == 1) {
					map.put("status", "Y");
				} else {
					map.put("status", "N");
				}
			}
		}
		JSONObject jsonObject = new JSONObject(map);
		return ajax(jsonObject.toString(), "text/html");
	}

     /**
      * 判断是否可以进行撤单
      * 撤单条件：
      * 1.已经和网金结算中心结算完成
      * 2.保险公司支持在线撤单，有撤单接口
      *
      * @return
      */
	public String checkCancelCont () {
        Map<String, Object> map = new HashMap<String, Object>();

        // 保单表主键ID
        String id = getParameter("id");

        // 判断是否和网金结算中心结算完成
        String sql = "select a.appStatus,a.balanceStatus,b.insuranceCompany,c.applicantMobile " +
				"from sdinformationrisktype a, SDInformation b, SDInformationAppnt c " +
				"where a.id = ? and a.orderSn = b.orderSn and a.applicantSn = c.applicantSn";
        DataTable dt = new QueryBuilder(sql, id).executeDataTable();
        String appStatus = null;
        String balanceStatus = null;
        String comCode = null;
		String applicantMobile = null;
        if (dt != null && dt.getRowCount() > 0) {
            appStatus = dt.getString(0, "appStatus");
            balanceStatus = dt.getString(0, "balanceStatus");
            comCode = dt.getString(0, "insuranceCompany");
			applicantMobile = dt.getString(0, "applicantMobile");
        }
        if (!"0".equals(balanceStatus) && "1".equals(appStatus)) {
            map.put("status", "0");
            map.put("message", "该订单30分钟内尚未结算，不能取消");
            JSONObject jsonObject = new JSONObject(map);
            return ajax(jsonObject.toString(), "text/html");
        }

        //判断保险公司是否可以退保
        sql = "select Memo from zdcode where CodeType=? and ParentCode=? and CodeValue=?";
        QueryBuilder qb = new QueryBuilder(sql);
        qb.add("TBDealCancelClassName");
        qb.add("TBDealCancelClassName");
        qb.add(comCode);
        String className = qb.executeString();

        if (StringUtil.isEmpty(className)) {
            map.put("status", "0");
            map.put("message", "该保险公司暂不支持保单取消功能，如有疑问请联系在线客服");
            JSONObject jsonObject = new JSONObject(map);
            return ajax(jsonObject.toString(), "text/html");
        }

        map.put("status", "1");
		map.put("phone", applicantMobile);
        JSONObject jsonObject = new JSONObject(map);
        return ajax(jsonObject.toString(), "text/html");
    }

	 /**
	  *	保单取消时候，发送短信验证码
	  *
	  * @return
	  */
    public String cancelContSendCode() {
		String svcode = VCFactory();

		setSession("cc_code", svcode);
		String rgtype = getParameter("rtype");
		String way = getParameter("ways");// 手机号
		String id = getParameter("id");
		if (StringUtil.isMobileNO(way) && !"1".equals(rgtype)) {
			return ajaxText("手机号校验失败");
		}
		if ("1".equals(rgtype)) {
		    // 当前时间
            String currentDate = DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss");
		    // 获取发送验证码次数
			String strCount = JedisCommonUtil.getString(3, id + "_count");
            // 第一次发送验证码
			if (StringUtil.isEmpty(strCount)) {
			    // 记录发送验证码次数
				JedisCommonUtil.setString(3, id + "_count", "1");
                // 记录第一次发送验证码时间
				JedisCommonUtil.setString(3, id + "_time", currentDate);
			} else {
			    // 非第一次发送验证码
                // 获取发送验证码的次数
				int count = Integer.parseInt(strCount);
                // 获取第一次发送验证码的时间
                String firstSendTime = JedisCommonUtil.getString(3, id + "_time");
                long lFirstSendTime = DateUtil.parse(firstSendTime, "yyyy-MM-dd HH:mm:ss").getTime();
                long lCurrentTime = DateUtil.parse(currentDate, "yyyy-MM-dd HH:mm:ss").getTime();
                long dayMilli = 1000 * 24 * 60 * 60;// 一天的毫秒数

                // 如果与第一次发送验证码的时间间隔大于24小时，清除缓存，重新记录
                if ((lCurrentTime - lFirstSendTime) > dayMilli) {
                    // 清除缓存
                    JedisCommonUtil.remove(3, id + "_count");
                    JedisCommonUtil.remove(3, id + "_time");
                    // 重新记录
                    JedisCommonUtil.setString(3, id + "_count", "1");
                    JedisCommonUtil.setString(3, id + "_time", currentDate);
                } else {
                    if (count < 5) {
                        // 如果发送次数小于5，则增加发送次数
                        JedisCommonUtil.setString(3, id + "_count", String.valueOf(count + 1));
                    } else {
                        // 如果发送次数大于5，则不发送，提示信息
                        return ajaxText("您今天该保单验证码发送已超过5次，<br />请及时联系客服。");
                    }
                }
			}

			// 发送验证码
			if (ActionUtil.sendSms("wj00402", way, svcode)) {
				return ajaxText("发送成功");
			}
		}
		return ajaxText("发送失败");
	}

	 /**
	  * 用户中心的保单管理，保单取消功能
	  * @return
	  */
	public String cancelCont () {
		Map<String, Object> map = new HashMap<String, Object>();
		// 校验手机验证码
		String svcode = (String) getSession("cc_code");
		String phoneCode = getParameter("phoneCode");

		if (svcode == null || !svcode.equals(phoneCode)) {
			map.put("status", "1");
			map.put("msg", "验证码错误");
			JSONObject jsonObject = new JSONObject(map);
			return ajax(jsonObject.toString(), "text/html");
		}

		// 保单取消
		// 保单表主键ID
		String id = getParameter("id");

		String insuredSn = "";
		String comCode = "";
		String appStatus = "";
		String ordersn = "";
		String riskTypeId = "";
		String flag = "";
		String returnIntegral = "";

		String sql = "select b.insuredSn,a.insuranceCompany , c.appStatus ,a.ordersn ,c.id, d.channelsn ,d.memberId ,a.productId,a.startdate,a.enddate "
				+ "from SDInformation a, sdorders d, sdinformationinsured b ,sdinformationrisktype c "
				+ "where a.informationSn=b.informationSn and b.recognizeeSn = c.recognizeeSn and a.orderSn = d.orderSn and c.id=?";
		DataTable dt = new QueryBuilder(sql, id).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			insuredSn = dt.getString(0, "insuredSn");
			comCode = dt.getString(0, "insuranceCompany");
			appStatus = dt.getString(0, "appStatus");
			ordersn = dt.getString(0, "ordersn");
			riskTypeId = dt.getString(0, "id");
			sdate = dt.getString(0, "startdate");
			edate = dt.getString(0, "enddate");
		}
		if (("1".equals(appStatus) || "3".equals(appStatus)) && StringUtil.isNotEmpty(insuredSn)
				&& StringUtil.isNotEmpty(comCode)) {
			CancelContService tCancelContService = new CancelContService();
			// 与保险公司进行撤单处理
			flag = tCancelContService.callInsTransInterface(comCode, ordersn, insuredSn, riskTypeId);
			// 保险公司撤单成功后 更新系统数据状态
			if ("SUCCESS".equals(flag)) {
				// 更新保单表的撤单信息
				SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
				sdRiskTypeSchema.setId(riskTypeId);
				if (sdRiskTypeSchema.fill()) {
					sdRiskTypeSchema.setappStatus("4");// 撤单成功后状态更新为2
					sdRiskTypeSchema.setcancelDate(new Date());
					sdRiskTypeSchema.update();
				}
				// 更新订单表的订单状态
				tCancelContService.changeSDOrders(ordersn);
						/*
						 * 与保险公司撤单成功后查找是否使用了积分抵值，分为购物车和非购物车方式
						 * 1、非购物车，该订单的所有保单全部撤消后才可以返还抵值积分
						 * 2、购物车，该购物车的所有订单的所有保单全部撤消后才可以返还抵值积分
						 */
				CancelCont cancelCont = new CancelCont();
				returnIntegral = cancelCont.returnPoint(sdRiskTypeSchema);
				// 冻结积分清零
				cancelCont.calculatePointToZero(sdRiskTypeSchema);
			}

		} else if ("2".equals(appStatus)) {
			// 网金结算中心撤单失败时，可以重新撤单
			map.put("status", "1");
			map.put("msg", "此订单已经撤单！");
			JSONObject jsonObject = new JSONObject(map);
			return ajax(jsonObject.toString(), "text/html");
		} else if ("4".equals(appStatus)) {
			flag = "SUCCESS";
			// 取得撤单回退积分数
			String Integral = new QueryBuilder(
					"select Integral from SDIntCalendar where prop1 = ? and Source = ?",
					riskTypeId,
					IntegralConstant.POINT_SOURCE_OFFSET_POINT)
					.executeString();
			if (StringUtil.isNotEmpty(Integral)) {
				BigDecimal PointScalerUnit = new BigDecimal(
						Config.getConfigValue("PointScalerUnit"));
				returnIntegral = new BigDecimal(Integral).divide(
						PointScalerUnit, 2, BigDecimal.ROUND_HALF_UP)
						.toString();
			}

		} else {
			map.put("status", "1");
			map.put("msg", "保单取消失败！请联系客服。");
			JSONObject jsonObject = new JSONObject(map);
			return ajax(jsonObject.toString(), "text/html");
		}
		if ("SUCCESS".equals(flag)) {
			if (StringUtil.isNotEmpty(riskTypeId)) {
				CancelContService tCancelContService = new CancelContService();
				SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
				SDInformationRiskTypeSet sdRiskTypeSet = new SDInformationRiskTypeSet();
				sdRiskTypeSet = sdRiskTypeSchema.query(new QueryBuilder("where ID = '" + riskTypeId + "'"));
				if (!sdRiskTypeSet.isEmpty() && sdRiskTypeSet.size() == 1) {
					sdRiskTypeSchema = sdRiskTypeSet.get(0);
					if ("4".equals(sdRiskTypeSchema.getappStatus()) && !"2".equals(sdRiskTypeSchema.getbalanceStatus())) {
						sdRiskTypeSchema.setbalanceStatus("2");
						sdRiskTypeSchema.update();
						// 表示与保险公司对接退保成功
						String returnActivity = "";
						if (tCancelContService.callProductInterface(ordersn, "", PubFun.getCurrentDate(), "02",
								sdRiskTypeSchema.getpolicyNo(), riskTypeId, returnIntegral, returnActivity)) {
							// 检查保单是否已经存在
							sql = "SELECT COUNT(*) FROM cancelcontrefund WHERE policyNo = ?";
							int countTemp = new QueryBuilder(sql, sdRiskTypeSchema.getpolicyNo()).executeInt();
							if (countTemp == 1) {
								logger.info("保单已经撤单成功！");
								map.put("status", "0");
								map.put("msg", "取消保单成功，<br />3个工作日内原路退回退保保费");
								JSONObject jsonObject = new JSONObject(map);
								return ajax(jsonObject.toString(), "text/html");
							}
							// 插入撤单退费表数据
							CancelContRefundSchema cancelContRefundSchema = new CancelContRefundSchema();
							cancelContRefundSchema.setId(CommonUtil.getUUID());
							cancelContRefundSchema.setOrderSn(ordersn);
							cancelContRefundSchema.setPolicyNo(sdRiskTypeSchema.getpolicyNo());

							// 设置状态，0：待审核；1：已审核；2：已退款；
							cancelContRefundSchema.setStatus("0");
                            // 设置撤单来源：0：会员前端撤单；1：CMS后台撤单；
							cancelContRefundSchema.setCancelFrom("0");
							// 设置保费与实际支付保费
							cancelContRefundSchema.setPrem(sdRiskTypeSchema.gettimePrem());
							cancelContRefundSchema.setPayPrem(sdRiskTypeSchema.getpayPrice());
							Date currentDate = new Date();
							cancelContRefundSchema.setRepealDate(currentDate);
							cancelContRefundSchema.setCreateDate(currentDate);
							cancelContRefundSchema.setModifyDate(currentDate);
							cancelContRefundSchema.insert();

                            // 撤单成功后，删除缓存记录的发送验证码时间与次数
                            JedisCommonUtil.remove(3, id + "_count");
                            JedisCommonUtil.remove(3, id + "_time");
                            
                            //同步cps信息
        					CancelCont tCancelCont = new CancelCont();
        					try {
        						tCancelCont.cancelCpsOrder(ordersn);
        					} catch (MalformedURLException e) {
        						
        						e.printStackTrace();
        						
        					}
							logger.info("撤单成功！");
							map.put("status", "0");
							map.put("msg", "取消保单成功，<br />3个工作日内原路退回退保保费");
							JSONObject jsonObject = new JSONObject(map);
							return ajax(jsonObject.toString(), "text/html");

						} else {
							logger.error("撤单结算中心调用失败！");
							map.put("status", "1");
							map.put("msg", "保单取消失败！请联系客服。");
							JSONObject jsonObject = new JSONObject(map);
							return ajax(jsonObject.toString(), "text/html");
						}
					}
				}
			} else {
				logger.error("订单有误！");
				map.put("status", "1");
				map.put("msg", "保单取消失败！请联系客服。");
				JSONObject jsonObject = new JSONObject(map);
				return ajax(jsonObject.toString(), "text/html");
			}
		} else if ("NOCONFIG".equals(flag)) {
			logger.error("此公司的订单未开放撤单功能！请联系技术人员。");
			map.put("status", "1");
			map.put("msg", "保单取消失败！请联系客服。");
			JSONObject jsonObject = new JSONObject(map);
			return ajax(jsonObject.toString(), "text/html");
		} else {
			String errorMsg = "保单取消失败！请联系客服。";
			if(flag.indexOf("|")!=-1){
				errorMsg = flag.substring(flag.indexOf("|")+1);
			}
			logger.error("撤单经代通调用失败！");
			map.put("status", "1");
			map.put("msg", errorMsg);
			JSONObject jsonObject = new JSONObject(map);
			return ajax(jsonObject.toString(), "text/html");
		}

		map.put("status", "1");
		map.put("msg", "保单取消失败！请联系客服。");
		JSONObject jsonObject = new JSONObject(map);
		return ajax(jsonObject.toString(), "text/html");
	}

	public Order getOrder() {

		return order;
	}

	public void setOrder(Order order) {

		this.order = order;
	}

	public int getPage() {

		return page;
	}

	public void setPage(int page) {

		this.page = page;
	}

	public String getOrderSn() {

		return orderSn;
	}

	public void setOrderSn(String orderSn) {

		this.orderSn = orderSn;
	}

	public String getLdate() {

		return ldate;
	}

	public void setLdate(String ldate) {

		this.ldate = ldate;
	}

	public String getHdate() {

		return hdate;
	}

	public void setHdate(String hdate) {

		this.hdate = hdate;
	}

	public String getOrderStatus() {

		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {

		this.orderStatus = orderStatus;
	}

	public String getApplicant() {

		return applicant;
	}

	public void setApplicant(String applicant) {

		this.applicant = applicant;
	}

	public static int getPagesize() {

		return pagesize;
	}

	public int getCount() {

		return count;
	}

	public void setCount(int count) {

		this.count = count;
	}

	public List<Map> getListOrders() {

		return listOrders;
	}

	public void setListOrders(List<Map> listOrders) {

		this.listOrders = listOrders;
	}

	public int getLastpage() {

		return lastpage;
	}

	public void setLastpage(int lastpage) {

		this.lastpage = lastpage;
	}

	public SDOrder getSdorder() {

		return sdorder;
	}

	public void setSdorder(SDOrder sdorder) {

		this.sdorder = sdorder;
	}

	public String getCallType() {

		return callType;
	}

	public void setCallType(String callType) {

		this.callType = callType;
	}

	public int getNewPageSize() {

		return newPageSize;
	}

	public void setNewPageSize(int newPageSize) {

		this.newPageSize = newPageSize;
	}

	public String getOrderFlag() {

		return orderFlag;
	}

	public void setOrderFlag(String orderFlag) {

		this.orderFlag = orderFlag;
	}

	public Map<String, String> getRecordMap() {

		return recordMap;
	}

	public void setRecordMap(Map<String, String> recordMap) {

		this.recordMap = recordMap;
	}

	public String getSdate() {

		return sdate;
	}

	public void setSdate(String sdate) {

		this.sdate = sdate;
	}

	public String getEdate() {

		return edate;
	}

	public void setEdate(String edate) {

		this.edate = edate;
	}

	public String getRecord_status() {

		return record_status;
	}

	public void setRecord_status(String record_status) {

		this.record_status = record_status;
	}

	public List<Map<String, String>> getListRecord() {

		return listRecord;
	}

	public void setListRecord(List<Map<String, String>> listRecord) {

		this.listRecord = listRecord;
	}

	public String getPhoneCode() {

		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {

		this.phoneCode = phoneCode;
	}

	/**
	 * 
	 * @return 生成六位验证码
	 */
	public String VCFactory() {

		int length = 6;
		char[] arr = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(arr[rand.nextInt(arr.length)]);
		}
		return sb.toString();
	}

	/**
	 * 发送验证码到手机
	 * 
	 * @param svcode
	 *            验证码
	 * @param way
	 *            手机号
	 * @return 是否发送成功
	 */

	public boolean SVCToPhone(String svcode, String way, String isModify) {
		boolean flag = false;
		if (ActionUtil.sendSms("wj00402", way, svcode)) {// 退保验证码
			if (getSession("sendValidateCode") != null) {
				getSession().remove("sendValidateCode");
				getSession().remove("sendTime");
				setSession("sendTime", System.currentTimeMillis());
				setSession("sendValidateCode", svcode);
			} else {
				setSession("sendTime", System.currentTimeMillis());
				setSession("sendValidateCode", svcode);
			}
			flag = true;
		}
		return flag;
	}

	public String getRecord_status_str() {

		return record_status_str;
	}

	public void setRecord_status_str(String record_status_str) {

		this.record_status_str = record_status_str;
	}

	public String getCardFlag() {

		return cardFlag;
	}

	public void setCardFlag(String cardFlag) {

		this.cardFlag = cardFlag;
	}

	public String getCardid() {

		return cardid;
	}

	public void setCardid(String cardid) {

		this.cardid = cardid;
	}
}
