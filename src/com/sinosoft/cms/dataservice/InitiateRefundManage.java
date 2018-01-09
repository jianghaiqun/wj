package com.sinosoft.cms.dataservice;

import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.ExcelReadUtil;
import com.sinosoft.aeonlife.AeonlifeService;
import com.sinosoft.aeonlife.model.Aeon;
import com.sinosoft.datachannel.RefundDealService;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.InitiateRefundSchema;
import com.sinosoft.schema.InitiateRefundSet;
import com.sinosoft.schema.RefundFileInfoSchema;
import com.sinosoft.schema.RefundFileInfoSet;
import com.sinosoft.schema.RefundResultInfoSchema;
import com.sinosoft.schema.RefundResultInfoSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 
 * @author wangwenying
 * 
 */
public class InitiateRefundManage extends Page {
	
	public static void dg1DataBind(DataGridAction dga) {
		String createStartDate = (String) dga.getParams().get("createStartDate");
		String createEndDate = (String) dga.getParams().get("createEndDate");
		String approvalStartDate = (String) dga.getParams().get("approvalStartDate");
		String approvalEndDate = (String) dga.getParams().get("approvalEndDate");
		
		String orderSn = (String) dga.getParams().get("orderSn");
		String policyNo = (String) dga.getParams().get("policyNo");
		String refundType = (String) dga.getParams().get("refundType");
		String approval = (String) dga.getParams().get("approval");
		String remark = (String) dga.getParams().get("remark");
		QueryBuilder qb = new QueryBuilder("SELECT i.*, CASE WHEN i.RefundType = '0' THEN '优惠返现' WHEN i.RefundType = '1' THEN '代客支付' WHEN i.RefundType = '2' THEN '淘宝测试' WHEN i.RefundType = '3' THEN '生效后退保' END AS RefundTypeName,  "
				+ " CASE i.Approval WHEN '0' THEN '未审批' WHEN '1' THEN '已通过' WHEN '2' THEN '不通过' ELSE '未知状态' END AS ApprovalName, "
				+ " r.productprice,DATE_FORMAT(r.svaliDate, '%Y-%m-%d') as svaliDate, DATE_FORMAT(r.insureDate, '%Y-%m-%d') as insureDate, p.InsureName "
				+ " from InitiateRefund i  "
				+ " JOIN sdinformationrisktype r ON i.ordersn = r.ordersn AND i.policyno = r.policyno "
				+ " LEFT JOIN sdproduct p ON p.productId = r.riskcode "
				+ " where 1=1 ");
		if (StringUtil.isNotEmpty(orderSn)) {
			qb.append(" and i.orderSn = ?", orderSn.trim());
		}
		if (StringUtil.isNotEmpty(policyNo)) {
			qb.append(" and i.policyNo = ?", policyNo.trim());
		}
		if (StringUtil.isNotEmpty(refundType)) {
			qb.append(" and i.refundType = ?", refundType);
		}
		if (StringUtil.isNotEmpty(approval)) {
			qb.append(" and i.approval = ?", approval);
		}
		if (StringUtil.isNotEmpty(remark)) {
			qb.append(" and i.Remark like ? ", "%" + remark.trim() + "%");
		}
		if (StringUtil.isNotEmpty(createStartDate)) {
			qb.append(" AND UNIX_TIMESTAMP(DATE_FORMAT(i.AddTime,'%Y-%m-%d'))>=UNIX_TIMESTAMP(DATE_FORMAT('" + createStartDate.trim() + "','%Y-%m-%d')) ");
		}
		if (StringUtil.isNotEmpty(createEndDate)) {
			qb.append(" AND UNIX_TIMESTAMP(DATE_FORMAT(i.AddTime,'%Y-%m-%d'))<=UNIX_TIMESTAMP(DATE_FORMAT('" + createEndDate.trim() + "','%Y-%m-%d')) ");
		}
		if (StringUtil.isNotEmpty(approvalStartDate)) {
			qb.append(" AND UNIX_TIMESTAMP(DATE_FORMAT(i.ApprovalTime,'%Y-%m-%d'))>=UNIX_TIMESTAMP(DATE_FORMAT('" + approvalStartDate.trim() + "','%Y-%m-%d')) ");
		}
		if (StringUtil.isNotEmpty(approvalEndDate)) {
			qb.append(" AND UNIX_TIMESTAMP(DATE_FORMAT(i.ApprovalTime,'%Y-%m-%d'))<=UNIX_TIMESTAMP(DATE_FORMAT('" + approvalEndDate.trim() + "','%Y-%m-%d')) ");
		}
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		qb.append(" order by i.AddTime desc, i.orderSn desc");
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 订单列表
	 * 
	 * @param dga
	 */
	public static void dg2DataBind(DataGridAction dga) {
		String orderSn = (String) dga.getParams().get("orderSn");
		String tbTradeSeriNo = (String) dga.getParams().get("tbTradeSeriNo");
		String policyNo = (String) dga.getParams().get("policyNo");
		QueryBuilder qb = new QueryBuilder("select o.orderSn, o.tbTradeSeriNo, t.policyNo, t.payPrice from sdorders o, "
				+ " sdinformationrisktype t where o.ordersn=t.ordersn and o.orderStatus >= '7' ");
		if (StringUtil.isNotEmpty(orderSn)) {
			qb.append(" and o.orderSn = ?", orderSn.trim());
		}
		if (StringUtil.isNotEmpty(tbTradeSeriNo)) {
			qb.append(" and o.tbTradeSeriNo like ? ", "%" + tbTradeSeriNo.trim());
		}
		if (StringUtil.isNotEmpty(policyNo)) {
			qb.append(" and t.policyNo = ? ", policyNo.trim());
		}
	
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		qb.append(" order by o.createdate desc");
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 退款结果信息
	 * 
	 * @param dga
	 */
	public static void dg3DataBind(DataGridAction dga) {
		String OrderSn = (String) dga.getParams().get("OrderSn");
		String PaySn = (String) dga.getParams().get("PaySn");
		String PayType = (String) dga.getParams().get("PayType");
		String StartDate = (String) dga.getParams().get("StartDate");
		String EndDate = (String) dga.getParams().get("EndDate");
		QueryBuilder qb = new QueryBuilder("select r.*,o.orderSn as osn, p.description from RefundResultInfo r LEFT JOIN sdorders o ON r.paysn = o.paysn LEFT JOIN paybase p ON o.paytype = p.paytype where 1=1 ");
		if (StringUtil.isNotEmpty(OrderSn)) {
			qb.append(" and o.orderSn = ?", OrderSn.trim());
		}
		if (StringUtil.isNotEmpty(PaySn)) {
			qb.append(" and r.paySn like ? ", "%" + PaySn.trim() + "%");
		}
		if (StringUtil.isNotEmpty(PayType)) {
			qb.append(" and p.description like ? ", "%" + PayType.trim() + "%");
		}

		if (StringUtil.isNotEmpty(StartDate)) {
			qb.append(" AND UNIX_TIMESTAMP(DATE_FORMAT(r.AddTime,'%Y-%m-%d'))>=UNIX_TIMESTAMP(DATE_FORMAT('" + StartDate.trim() + "','%Y-%m-%d')) ");
		}
		if (StringUtil.isNotEmpty(EndDate)) {
			qb.append(" AND UNIX_TIMESTAMP(DATE_FORMAT(r.AddTime,'%Y-%m-%d'))<=UNIX_TIMESTAMP(DATE_FORMAT('" + EndDate.trim() + "','%Y-%m-%d')) ");
		}
		
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		qb.append(" order by r.AddTime desc");
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 代客支付订单撤单信息
	 * 
	 * @param dga
	 */
	public static void dg4DataBind(DataGridAction dga) {
		String OrderSn = (String) dga.getParams().get("OrderSn");
		String PaySn = (String) dga.getParams().get("PaySn");
		String StartDate = (String) dga.getParams().get("StartDate");
		String EndDate = (String) dga.getParams().get("EndDate");
		String StartTime = (String) dga.getParams().get("StartTime");
		String EndTime = (String) dga.getParams().get("EndTime");
		String startDate = StartDate + " " + StartTime;
		String endDate = EndDate + " " + EndTime;
		QueryBuilder qb = new QueryBuilder("SELECT o.orderSn AS orderSn, o.paySn AS paySn, t.payPrice AS refundAmount, o.payType AS payType,"
				+ " (SELECT p.description FROM payBase p WHERE o.payType = p.payType) as payTypeName, t.insureDate AS insureDate,"
				+ " t.policyNo AS policyNo, t.cancelDate AS cancelDate, o.channelsn AS channelsn, tr.tradeCheckSeriNo, "
				+ " (SELECT GROUP_CONCAT(remark) FROM sdremark re WHERE re.orderSn = o.orderSn) AS remark "
				+ " FROM sdorders o, sdinformationrisktype t, tradeinformation tr "
				+ " WHERE o.orderStatus >= 7 AND o.orderStatus NOT IN ('8','13','14') AND t.appStatus in ('2', '3', '4') AND o.ordersn = t.ordersn AND o.ordersn = tr.ordId AND (t.cancelDate IS NOT NULL OR t.cancelDate <> '') "
				+ " AND (EXISTS "
				+ " (SELECT 1 FROM sdremark r WHERE o.ordersn = r.ordersn AND (r.remark LIKE '%代客投保%' OR r.remark LIKE '%重出订单%' "
				+ " OR r.remark LIKE '%协助客户投保%' OR r.remark LIKE '%代客出单%' OR r.remark LIKE '%代客支付%' OR r.remark LIKE '%返还代支付%')) "
				+ " OR EXISTS (SELECT 1 FROM BuyForCustomerOldSn b WHERE o.ordersn = b.ordersn AND b.BuyForCustomerFlag IN ('Y','N')  OR find_in_set(o.ordersn, b.OldOrderSn)))");
		// 特殊处理，A单客户支付 B单客服代客支付 C订单变更。
		if (StringUtil.isNotEmpty(OrderSn)) {
			qb.append(" and o.orderSn = ?", OrderSn.trim());
		}
		if (StringUtil.isNotEmpty(PaySn)) {
			qb.append(" and o.paySn = ? ", PaySn.trim());
		}

		if (StringUtil.isNotEmpty(StartDate)) {
			qb.append(" AND UNIX_TIMESTAMP(DATE_FORMAT(t.cancelDate,'%Y-%m-%d %H:%i:%s'))>=UNIX_TIMESTAMP(DATE_FORMAT('" + startDate.trim() + "','%Y-%m-%d %H:%i:%s')) ");
		}
		if (StringUtil.isNotEmpty(EndDate)) {
			qb.append(" AND UNIX_TIMESTAMP(DATE_FORMAT(t.cancelDate,'%Y-%m-%d %H:%i:%s'))<=UNIX_TIMESTAMP(DATE_FORMAT('" + endDate.trim() + "','%Y-%m-%d %H:%i:%s')) ");
		}
		
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		qb.append(" order by t.cancelDate desc");
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 增加
	 */
	public void add() {
		String OrderSn = (String) Request.get("OrderSn");
		Transaction tr = new Transaction();
		
		String num = (String) Request.get("num");
		
		String RefundType = (String) Request.get("RefundType");
		for (int i = 0; i < Integer.valueOf(num); i++) {
			String PolicyNo = (String) Request.get("PolicyNo_" + i);
			String RefundAmount = (String) Request.get("RefundAmount_" + i);
			QueryBuilder qbi = new QueryBuilder("select count(1) from InitiateRefund where OrderSn = ? and PolicyNo = ?", OrderSn, PolicyNo);
			
			int count = qbi.executeInt();
			if (count > 0) {
				Response.setLogInfo(0, "该保单已存在!保单号：" + PolicyNo + ", 请先删除！");
				return;
			}
			QueryBuilder qb = new QueryBuilder(" SELECT t.payPrice FROM sdinformationrisktype t WHERE t.ordersn = ? AND t.PolicyNo = ? ", OrderSn, PolicyNo);
			String payPrice = qb.executeString();
			
			if (StringUtil.isEmpty(payPrice)) {
				Response.setLogInfo(0, "该订单未支付或存在异常，请确认!保单号：" + PolicyNo);
				return;
			}
			if ((new BigDecimal(RefundAmount)).compareTo(new BigDecimal(payPrice)) > 0) {
				Response.setLogInfo(0, "退现金额必须小于或等于支付金额!保单号：" + PolicyNo);
				return;
			}
			// 生效后退保
			if ("3".equals(RefundType)) {
				QueryBuilder qb1 = new QueryBuilder(" SELECT * FROM fccontnew WHERE state ='03' AND contno = ? ", PolicyNo);
				DataTable dt = qb1.executeDataTable();
				
				if (dt.getRowCount() == 0) {
					Response.setLogInfo(0, "该订单未进行人工退保，请确认!保单号：" + PolicyNo);
					return;
				}
			}

			InitiateRefundSchema schema = new InitiateRefundSchema();
			schema.setOrderSn(OrderSn);
			schema.setid(CommonUtil.getUUID());
			schema.setValue(Request);
			schema.setPolicyNo(PolicyNo);
			schema.setRefundAmount(RefundAmount);
			schema.setAddTime(new Date());
			schema.setAddUser(User.getUserName());
			
			tr.add(schema, Transaction.INSERT);
		}
		
		if (tr.commit()) {
			Response.setLogInfo(1, "添加成功");
		} else {
			Response.setLogInfo(0, "添加失败!");
		}
	}
	
	/**
	 * 删除 
	 */
	public void del() {
		String ids = $V("IDs");
		String[] idArr = ids.split(",");
		String sqlIds = "";
		for (int i = 0; i < idArr.length; i++) {
			sqlIds += "'" + idArr[i] + "',";
		}
		sqlIds = sqlIds.substring(0, sqlIds.length() - 1);
		
		Transaction trans = new Transaction();
		InitiateRefundSchema schema = new InitiateRefundSchema();
		InitiateRefundSet set = schema.query(new QueryBuilder("where id in (" + sqlIds + ")"));
		for (int i = 0; i < set.size(); i++) {
			schema = set.get(i);
			if ("1".equals(schema.getApproval())) {
				Response.setLogInfo(1, "已审批通过数据不能删除！");
				return;
			}
		}
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		}
		else{
			Response.setLogInfo(0, "删除失败");
		}
	}
	
	/**
	 * 删除 
	 */
	public void delRefundResultInfo() {
		String ids = $V("IDs");
		String[] idArr = ids.split(",");
		String sqlIds = "";
		for (int i = 0; i < idArr.length; i++) {
			sqlIds += "'" + idArr[i] + "',";
		}
		sqlIds = sqlIds.substring(0, sqlIds.length() - 1);
		
		Transaction trans = new Transaction();
		RefundResultInfoSchema schema = new RefundResultInfoSchema();
		RefundResultInfoSet set = schema.query(new QueryBuilder("where id in (" + sqlIds + ")"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		}
		else{
			Response.setLogInfo(0, "删除失败");
		}
	}
	/**
	 *  审批通过
	 */
	public void approvalYes() {
		String ids = $V("IDs");
		String[] idArr = ids.split(",");
		String sqlIds = "";
		for (int i = 0; i < idArr.length; i++) {
			sqlIds += "'" + idArr[i] + "',";
		}
		sqlIds = sqlIds.substring(0, sqlIds.length() - 1);
		
		Transaction trans = new Transaction();
		InitiateRefundSchema schema = new InitiateRefundSchema();
		InitiateRefundSet set = schema.query(new QueryBuilder("where id in (" + sqlIds + ")"));
		for (int i = 0; i < set.size(); i++) {
			InitiateRefundSchema temp = set.get(i);
			temp.setApproval("1");
			temp.setApprovalTime(new Date());
		}
		trans.add(set, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "审批成功");
		}
		else{
			Response.setLogInfo(0, "审批失败");
		}
	}

	/**
	 *  审批不通过
	 */
	public void approvalNo() {
		String ids = $V("IDs");
		String[] idArr = ids.split(",");
		String sqlIds = "";
		for (int i = 0; i < idArr.length; i++) {
			sqlIds += "'" + idArr[i] + "',";
		}
		sqlIds = sqlIds.substring(0, sqlIds.length() - 1);
		
		Transaction trans = new Transaction();
		InitiateRefundSchema schema = new InitiateRefundSchema();
		InitiateRefundSet set = schema.query(new QueryBuilder("where id in (" + sqlIds + ")"));
		for (int i = 0; i < set.size(); i++) {
			InitiateRefundSchema temp = set.get(i);
			temp.setApproval("2");
			temp.setApprovalTime(new Date());
		}
		trans.add(set, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "审批成功");
		}
		else{
			Response.setLogInfo(0, "审批失败");
		}
	}
	
	/**
	 * 导入淘宝优惠记录
	 */
	public void improtTBCouponInfo(){
		
		String FilePath = (String) Request.get("FilePath");
		if (StringUtil.isEmpty(FilePath)) {
			Response.setLogInfo(1, "文件上传失败！");
			return;
		}

		File file = new File(Config.getContextRealPath() + "/temp/" + FilePath);
		if (file.exists() && file.isFile()) {
			try {
				List<Map<String, String>> data = ExcelReadUtil.getData(file, 1);
				logger.info("excel解析完成。");
				if (data.size() > 1) {
					for (int i = 0; i < data.size(); i++) {
						insertInfo(data.get(i));
					}
				}
			} catch (Exception e) {
				logger.error("文件解析异常" + e.getMessage(), e);
			}
		} else {
			Response.setLogInfo(1, "文件不存在！");
			return;
		}
		// 删除文件
		FileUtil.delete(file);
		Response.setLogInfo(0, "导入成功！");
	}

	/**
	 * 导入退款结果记录
	 */
	public void improtRefundResult(){
		
		String FilePath = (String) Request.get("FilePath");
		if (StringUtil.isEmpty(FilePath)) {
			Response.setLogInfo(1, "文件上传失败！");
			return;
		}

		RefundResultInfoSet set = new RefundResultInfoSet();
		String errorMessage = "";
		String[] filePaths = FilePath.split(",");
		for (int i = 0; i < filePaths.length; i++) {
			File file = new File(Config.getContextRealPath() + "/temp/" + filePaths[i]);
			if (file.exists() && file.isFile()) {
				String ext = getExtension(filePaths[i]);
				if ("xls,xlsx".indexOf(ext) >= 0) {
				
					try {
						List<Map<String, String>> result = ExcelReadUtil.getData(file, 0);
						logger.info("excel解析完成。size:{}", result.size());
						if (null != result && result.size() > 1) {
							
							for (int j = 1; j < result.size(); j++) {
								Map<String, String> row = result.get(j);

								// 批次号
								String batchNo = row.get("0");
								if (StringUtil.isEmpty(batchNo)) {
									break;
								}
								// 商家订单号
								String paySn = row.get("1");
								RefundResultInfoSchema schema = new RefundResultInfoSchema();
								
								schema.setPaySn(paySn);
								schema.setProp1(batchNo);
								// 退款金额
								schema.setRefundAmount(row.get("2"));
								// 退款状态
								schema.setstatus(row.get("3"));
								// 退款日期
								String strRefundDate = row.get("4");
								Date refundDate = new Date();
								try {
									refundDate = DateUtil.parseDateTime(strRefundDate, "yyyy-MM-dd HH:mm:ss");
								} catch (Exception e) {
									logger.error("退款日期转化异常！" + e.getMessage(), e);
								}
								schema.setRefundDate(refundDate);
								// 备注
								schema.setRemark(row.get("8"));
								
								RefundResultInfoSchema querySchema = new RefundResultInfoSchema();
								RefundResultInfoSet rriset = querySchema.query(new QueryBuilder("where Prop1 = ? and PaySn = ?", batchNo, paySn));
								if (rriset.size() > 0) {
									schema.setid(rriset.get(0).getid());
									schema.setAddTime(rriset.get(0).getAddTime());
									schema.setAddUser(rriset.get(0).getAddUser());
									schema.setModifyTime(new Date());
									schema.setModifyUser(User.getUserName());
								} else{
									schema.setid(CommonUtil.getUUID());
									schema.setAddTime(new Date());
									schema.setAddUser(User.getUserName());
								}
								
								set.add(schema);
							}
						}
					}catch (Exception e) {
						logger.error("文件解析异常!" + e.getMessage(), e);
						errorMessage = "文件解析异常！";
					}
				
				/*if ("xls,xlsx".indexOf(ext) >= 0) {
					try {
						List<Map<String, String>> result = ExcelReadUtil.getData(file, 0);
						LogUtil.info("excel解析完成。size:" + result.size());
						if (null != result && result.size() > 0) {
							Map<String, String> row0 = result.get(0);
							String cell = row0.get("0");
							
							if (StringUtil.isNotEmpty(cell)) {
								// 易宝支付
								if ("商户系统－退款记录报表－下载查询结果".equals(cell.trim())) {
									YBZF_Deal(result, set);
								} 
								// 支付宝支付
								else if ("批次号".equals(cell.trim())) {
									ZFB_Deal(result, set);
								} 
								// 银联支付
								else if (cell.trim().indexOf("日期:") >= 0) {
									YL_Deal(result, set);
								} else {
									LogUtil.error("未知文件内容，首格内容是：" + cell);
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						LogUtil.error("文件解析异常!");
						errorMessage = "文件解析异常！";
					}
				} 
				// 银联
				else if ("xml".equals(ext)) {

					List<Map<String, String>> result = null;
					try {
						result = xmlParser(file);
					} catch (Exception e) {
						e.printStackTrace();
						Response.setLogInfo(1, "xml文件解析异常！");
						break;
					} 
					YL_XML_Deal(result, set);
				}
				// 微信
				else if ("csv".equals(ext) || "txt".equals(ext)) {
					List<Map<String, String>> list = null;
					try {
						list = new CsvFileParser().ReadFileToList(file);
					} catch (Exception e1) {
						e1.printStackTrace();
						Response.setLogInfo(1, "csv或者txt,解析异常！11");
						break;
					}
					if (list.size() > 0) {
						if (list.get(0).get("0").startsWith("#起始日期：")) {
							List<Map<String, String>> result = null;
							try {
								result = readFileToList(file);
							} catch (Exception e) {
								e.printStackTrace();
								Response.setLogInfo(1, "csv或者txt,解析异常！22");
								break;
							} 
							WX_Deal(result, set);
						}
					}
					
				}*/
				}else {
					Response.setLogInfo(1, "文件格式不正确！");
					errorMessage = "文件格式不正确！";
					break;
				}
			} else {
				Response.setLogInfo(1, "文件不存在！");
				errorMessage = "文件不存在！";
				break;
			}
		}
		// 删除文件
		for (int i = 0; i < filePaths.length; i++) {

			File file = new File(Config.getContextRealPath() + "/temp/" + filePaths[i]);
			if (file.exists() && file.isFile()) {
				FileUtil.delete(file);
			}
		}
		if (StringUtil.isNotEmpty(errorMessage)) {
			Response.setLogInfo(1, errorMessage);
		} else {
			if (set.deleteAndInsert()) {
				Response.setLogInfo(0, "导入成功！");
			} else {
				Response.setLogInfo(0, "导入失败！");
			}
		}
	}
	
	/**
	 * 易宝支付处理
	 * 
	 * @param result
	 * @param set
	 */
	private void YBZF_Deal(List<Map<String, String>> result, RefundResultInfoSet set){
		
		if (result.size() > 2) {
			for (int i = 2; i < result.size(); i++) {
				Map<String, String> row = result.get(i);
				
				RefundResultInfoSchema schema = new RefundResultInfoSchema();
				schema.setid(CommonUtil.getUUID());
				String paySn = row.get("1");
				if (StringUtil.isNotEmpty(paySn)) {
					// 商家支付号
					schema.setPaySn(paySn);
					
					QueryBuilder qb = new QueryBuilder(" select orderSn from sdorders where paySn = ? limit 1", paySn);
					String orderSn = qb.executeString();
					schema.setOrderSn(orderSn);
				}
				// 退款日期
				String strRefundDate = row.get("4");
				Date refundDate = new Date();
				try {
					refundDate = DateUtil.parseDateTime(strRefundDate, "yyyy-MM-dd HH:mm:ss");
				} catch (Exception e) {
					logger.error("退款日期转化异常！" + e.getMessage(), e);
				}
				schema.setRefundDate(refundDate);
				
				// 退款金额
				schema.setRefundAmount(row.get("5"));
				// 退款状态
				String strStatus = row.get("8");
				schema.setstatus(strStatus);
				
				schema.setResult("已申请退款");
				
				// 退款平台
				schema.setRefundPlatform("易宝支付");
				schema.setAddTime(new Date());
				schema.setAddUser(User.getUserName());
				
				set.add(schema);
			}
		}
	}
	
	/**
	 * 支付宝支付处理
	 * 
	 * @param result
	 * @param set
	 */
	private void ZFB_Deal(List<Map<String, String>> result, RefundResultInfoSet set){
		
		if (result.size() > 3) {
			for (int i = 3; i < result.size(); i++) {
				Map<String, String> row = result.get(i);
				
				RefundResultInfoSchema schema = new RefundResultInfoSchema();
				schema.setid(CommonUtil.getUUID());
				String paySn = row.get("0");
				if (StringUtil.isNotEmpty(paySn)) {
					// 商家支付号
					schema.setPaySn(paySn);
					
					QueryBuilder qb = new QueryBuilder(" select orderSn from sdorders where paySn = ? limit 1", paySn);
					String orderSn = qb.executeString();
					schema.setOrderSn(orderSn);
				}
				// 退款日期
				schema.setRefundDate(new Date());
				
				// 退款金额
				schema.setRefundAmount(row.get("2"));
				// 退款状态
				String strStatus = row.get("4");
				schema.setstatus(strStatus);

				// 退款平台
				schema.setRefundPlatform("支付宝支付");
				
				schema.setResult(row.get("5"));
				schema.setPayee(row.get("7"));
				schema.setAddTime(new Date());
				schema.setAddUser(User.getUserName());
				
				set.add(schema);
			}
		}
	}
	
	/**
	 * 银联支付处理
	 * 
	 * @param result
	 * @param set
	 */
	private void YL_Deal(List<Map<String, String>> result, RefundResultInfoSet set){
		
		if (result.size() > 2) {
			for (int i = 2; i < result.size(); i++) {
				Map<String, String> row = result.get(i);
				
				RefundResultInfoSchema schema = new RefundResultInfoSchema();
				schema.setid(CommonUtil.getUUID());
				String paySn = row.get("3");
				if (StringUtil.isNotEmpty(paySn)) {
					// 商家支付号
					schema.setPaySn(paySn);
					
					QueryBuilder qb = new QueryBuilder(" select orderSn from sdorders where paySn = ? limit 1", paySn);
					String orderSn = qb.executeString();
					schema.setOrderSn(orderSn);
				}
				// 退款日期
				String strRefundDate = row.get("9");
				if (StringUtil.isNotEmpty(strRefundDate)) {

					Date refundDate = DateUtil.parseDateTime(strRefundDate, "yyyyMMdd");
					schema.setRefundDate(refundDate);
				}
				
				// 退款金额
				schema.setRefundAmount(row.get("0"));
				// 退款状态
				String strStatus = row.get("4");
				schema.setstatus(strStatus);
				
				schema.setResult("已申请退款");

				// 退款平台
				schema.setRefundPlatform("银联支付");
				
				schema.setAddTime(new Date());
				schema.setAddUser(User.getUserName());
				
				set.add(schema);
			}
		}
	}

	/**
	 * 银联支付处理
	 * 
	 * @param result
	 * @param set
	 */
	private void YL_XML_Deal(List<Map<String, String>> result, RefundResultInfoSet set){
		
		if (result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map<String, String> row = result.get(i);
				
				RefundResultInfoSchema schema = new RefundResultInfoSchema();
				schema.setid(CommonUtil.getUUID());
				String paySn = row.get("OrdId");
				if (StringUtil.isNotEmpty(paySn)) {
					// 商家支付号
					schema.setPaySn(paySn);
					
					QueryBuilder qb = new QueryBuilder(" select orderSn from sdorders where paySn = ? limit 1", paySn);
					String orderSn = qb.executeString();
					schema.setOrderSn(orderSn);
				}
				// 退款日期
				String strRefundDate = row.get("RefundRequestDate");
				if (StringUtil.isNotEmpty(strRefundDate)) {

					Date refundDate = DateUtil.parseDateTime(strRefundDate, "yyyy-MM-dd");
					schema.setRefundDate(refundDate);
				}
				
				// 退款金额
				schema.setRefundAmount(row.get("RefundAmt"));
				// 退款状态
				String strStatus = row.get("StatDesc");
				schema.setstatus(strStatus);
				
				schema.setResult("已申请退款");

				// 退款平台
				schema.setRefundPlatform("银联支付");
				
				schema.setAddTime(new Date());
				schema.setAddUser(User.getUserName());
				
				set.add(schema);
			}
		}
	}

	/**
	 * 微信支付处理
	 * 
	 * @param result
	 * @param set
	 */
	private void WX_Deal(List<Map<String, String>> result, RefundResultInfoSet set){
		
		if (result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Map<String, String> row = result.get(i);
				
				RefundResultInfoSchema schema = new RefundResultInfoSchema();
				schema.setid(CommonUtil.getUUID());
				String paySn = row.get("8");
				if (StringUtil.isNotEmpty(paySn)) {
					paySn = paySn.replace("`", "");
					// 商家支付号
					schema.setPaySn(paySn);
					
					QueryBuilder qb = new QueryBuilder(" select orderSn from sdorders where paySn = ? limit 1", paySn);
					String orderSn = qb.executeString();
					schema.setOrderSn(orderSn);
				}
				// 退款日期
				String strRefundDate = row.get("4");
				if (StringUtil.isNotEmpty(strRefundDate)) {

					Date refundDate = DateUtil.parseDateTime(strRefundDate, "yyyy-MM-dd");
					schema.setRefundDate(refundDate);
				}
				
				// 退款金额
				schema.setRefundAmount(row.get("5"));
				// 退款状态
				String strStatus = row.get("3");
				if (StringUtil.isNotEmpty(strStatus)) {
					strStatus = strStatus.replaceAll("\"", "");
					schema.setstatus(strStatus);
				}
				
				schema.setResult("已申请退款");

				// 退款平台
				schema.setRefundPlatform("微信支付");
				
				schema.setAddTime(new Date());
				schema.setAddUser(User.getUserName());
				
				set.add(schema);
			}
		}
	}
	
	private List<Map<String, String>> readFileToList(File file) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> mapStr;
		InputStream in = new FileInputStream(file);
		InputStreamReader inReader = new InputStreamReader(in, "gbk");
		BufferedReader reader = new BufferedReader(inReader);
		String line = null;
		while((line = reader.readLine())!=null){
			String[] lables = line.split(",");
			
			if (lables.length > 2 && !"退款申请时间".equals(lables[0])) {
				mapStr = new HashMap<String, String>();
				for (int i = 0; i < lables.length; i++) {
					mapStr.put(String.valueOf(i), lables[i]);
				}
				list.add(mapStr);
			}
		}
		
		reader.close();
		inReader.close();
		in.close();
		return list;
	}
	
	/**
	 * xml解析
	 * 
	 * @param file
	 * @return
	 */
	private List<Map<String, String>> xmlParser (File file) throws Exception {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document document = builder.parse(file);
		
		Element rootElement = document.getDocumentElement();

		NodeList nodes = rootElement.getElementsByTagName("ROW");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap nmap = node.getAttributes();

				Node attrRefundAmt = nmap.getNamedItem("RefundAmt");
				Map<String, String> map = new HashMap<String, String>();
				if (null != attrRefundAmt) {
					map.put("RefundAmt", attrRefundAmt.getNodeValue());
				}
				Node attrOrdId = nmap.getNamedItem("OrdId");
				if (null != attrOrdId) {
					map.put("OrdId", attrOrdId.getNodeValue());
				}
				Node attrStatDesc = nmap.getNamedItem("StatDesc");
				if (null != attrStatDesc) {
					map.put("StatDesc", attrStatDesc.getNodeValue());
				}
				Node attrRefundRequestDate = nmap.getNamedItem("RefundRequestDate");
				if (null != attrRefundRequestDate) {
					map.put("RefundRequestDate", attrRefundRequestDate.getNodeValue());
				}
				
				if (!map.isEmpty()) {
					result.add(map);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 插入数据
	 * 
	 * @param rowInfo
	 * @return
	 */
	private boolean insertInfo (Map<String, String> rowInfo) {
		
		InitiateRefundSchema schema = new InitiateRefundSchema();
		schema.setOrderSn(StringUtil.trim(rowInfo.get("0")));
		schema.setPolicyNo(StringUtil.trim(rowInfo.get("1")));
		InitiateRefundSet set = schema.query();

		schema.setRefundType("0");
		schema.setPolicyNo(StringUtil.trim(rowInfo.get("1")));
		schema.setRefundAmount(StringUtil.trim(rowInfo.get("4")));
		schema.setRemark(rowInfo.get("7"));
		// 默认审批通过
		schema.setApproval("1");
		schema.setApprovalTime(new Date());
		schema.setAddTime(new Date());
		schema.setAddUser(User.getUserName());
		if (set.size() > 0) {
			//System.out.println(schema.getOrderSn()+":"+schema.getPolicyNo());
			schema.setid(set.get(0).getid());
			return schema.update();
		} else {
			schema.setid(CommonUtil.getUUID());
			return schema.insert();
		}
	}
	
	/**
	 * 创建退款文件
	 */
	public void refundFileCreate(){
		
		String StartDate = (String) Request.get("StartDate");
		String StartTime = (String) Request.get("StartTime");
		String EndDate = (String) Request.get("EndDate");
		String EndTime = (String) Request.get("EndTime");

		String startDate = StartDate + " " + StartTime;
		String endDate = EndDate + " " + EndTime;
		RefundDealService service = new RefundDealService();
		String result = service.refundFileCreate(startDate, endDate);
		if ("SUCCESS".equals(result)) {
			Response.setLogInfo(0, "创建完成！");
		}
		else {
			Response.setLogInfo(1, result);
		}
	}

	/**
	 * 删除 
	 */
	public void delFileInfo() {
		String ids = $V("IDs");
		String[] idArr = ids.split(",");
		String sqlIds = "";
		for (int i = 0; i < idArr.length; i++) {
			sqlIds += "'" + idArr[i] + "',";
		}
		sqlIds = sqlIds.substring(0, sqlIds.length() - 1);
		
		Transaction trans = new Transaction();
		RefundFileInfoSchema schema = new RefundFileInfoSchema();
		RefundFileInfoSet set = schema.query(new QueryBuilder("where id in (" + sqlIds + ")"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		}
		else{
			Response.setLogInfo(0, "删除失败");
		}
	}


	/**
	 * 获取后缀名
	 * 
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * 编辑页面初始化
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initDialog(Mapx params) {
		String id = params.getString("Id");
		if (StringUtil.isNotEmpty(id)) {
			RefundResultInfoSchema refundResultInfoSchema = new RefundResultInfoSchema();
			refundResultInfoSchema.setid(id);
			if (refundResultInfoSchema.fill()){
				params = refundResultInfoSchema.toMapx();
				Date RefundDate = refundResultInfoSchema.getRefundDate();
				if (null != RefundDate) {
					params.put("tRefundDate", DateUtil.toString(RefundDate, DateUtil.Format_Date));
					params.put("RefundTime", DateUtil.toTimeString(RefundDate));
				}
			}
		}
		return params;
	}
	
	/**
	 * 退款结果信息保存
	 */
	public void save() {
		String id = $V("Id");
		Transaction trans = new Transaction();
		if (StringUtil.isEmpty(id)) {
			Response.setLogInfo(0, "传入数据错误！");
			return;
		}
		RefundResultInfoSchema refundResultInfoSchema = new RefundResultInfoSchema();
		refundResultInfoSchema.setid(id);
		if (!refundResultInfoSchema.fill()) {
			Response.setLogInfo(0, id + "退款结果不存在！");
			return;
		}

		refundResultInfoSchema.setValue(Request);
		String date = $V("tRefundDate");
		String time = $V("RefundTime");
		if (StringUtil.isNotEmpty(date) && StringUtil.isNotEmpty(time)) {
			refundResultInfoSchema.setRefundDate(DateUtil.parse(date + " " + time, DateUtil.Format_DateTime));
		}
		
		refundResultInfoSchema.setModifyUser(User.getUserName());
		refundResultInfoSchema.setModifyTime(new Date());
		trans.add(refundResultInfoSchema, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}
	
	/**
	 * 导入理财数据
	 */
	public void improtLCData(){
		
		String FilePath = (String) Request.get("FilePath");
		if (StringUtil.isEmpty(FilePath)) {
			Response.setLogInfo(1, "文件上传失败！");
			return;
		}

		RefundResultInfoSet set = new RefundResultInfoSet();
		String errorMessage = "";
		String[] filePaths = FilePath.split(",");
		for (int i = 0; i < filePaths.length; i++) {
			File file = new File(Config.getContextRealPath() + "/temp/" + filePaths[i]);
			if (file.exists() && file.isFile()) {
				String ext = getExtension(filePaths[i]);
				if ("xls,xlsx".indexOf(ext) >= 0) {
				
					try {
						List<Map<String, String>> data = ExcelReadUtil.getData(file, 0);
						logger.info("excel解析完成。size:{}", data.size());
						if (null != data && data.size() > 1) {
							
							int column = data.get(0).size();
							// 删除第一个元素，删除列标题
							data.remove(0);
							if (column == 13) {
								Map<String, Object> map = new HashMap<String, Object>();
								for(int j = 0; j < data.size(); j++){
									Map<String, String> policyData = data.get(j);
									
									// 渠道
									String channelSn = policyData.get("12");
									if (StringUtil.isEmpty(channelSn)) {
										Response.setLogInfo(1, "第" + (j + 1) + "行跳出, 渠道没有设置");
										break;
									} else {
										if (channelSn.getBytes().length != channelSn.length()) {
											Response.setLogInfo(1, "第" + (j + 1) + "行跳出, 渠道没有设置为渠道编码");
											break;
										}
										StringUtil.trim(channelSn);
									}
									
									Aeon aeon = new Aeon();
									String res_ordersn = "LCBX" + policyData.get("7");
									aeon.setAeonOrderSn(res_ordersn);
									aeon.setAeonName(policyData.get("1"));
									aeon.setAeonCardNum(policyData.get("2"));
									aeon.setAeonPhone(policyData.get("3"));
									aeon.setAeonMail(policyData.get("4"));
									aeon.setAeonAdd(policyData.get("5"));
									aeon.setAeonAmount(policyData.get("6"));
									aeon.setAeonPolicyNo(policyData.get("7"));
									aeon.setAeonProductName("百年稳赢保两全保险（万能型）");
									aeon.setAeonPolicyPath("");
									Date insureDate = DateUtil.parse(DateUtil.toString(DateUtil.parse(policyData.get("8"), DateUtil.Format_DateTime)), DateUtil.Format_Date);
									Random r1 = new Random();
									insureDate = DateUtil.addHour(insureDate, r1.nextInt(24));
									insureDate = DateUtil.addMinute(insureDate, r1.nextInt(60));
									insureDate = DateUtil.addSecond(insureDate, r1.nextInt(60));
									aeon.setCancelDate(insureDate);
									Date startDate = DateUtil.parse(DateUtil.toString(DateUtil.parse(policyData.get("9"), DateUtil.Format_DateTime)), DateUtil.Format_Date);
									aeon.setAeonStartDate(startDate);
									Date endDate = DateUtil.parse(DateUtil.toString(DateUtil.parse(policyData.get("10"), DateUtil.Format_DateTime)), DateUtil.Format_Date);
									aeon.setAeonEndDate(DateUtil.addSecond(endDate, -1));
									aeon.setAeonPolicyStauts("1");
									aeon.setChannels(channelSn);
									map.put(res_ordersn, aeon);
								}
								if (map != null) {
									// 保存订单
									AeonlifeService ass = new AeonlifeService();
									ass.executeSave(map);
								}
							}
						}
					}catch (Exception e) {
						logger.error("文件解析异常!" + e.getMessage(), e);
						errorMessage = "文件解析异常！";
					}
				}else {
					Response.setLogInfo(1, "文件格式不正确！");
					errorMessage = "文件格式不正确！";
					break;
				}
			} else {
				Response.setLogInfo(1, "文件不存在！");
				errorMessage = "文件不存在！";
				break;
			}
		}
		// 删除文件
		for (int i = 0; i < filePaths.length; i++) {

			File file = new File(Config.getContextRealPath() + "/temp/" + filePaths[i]);
			if (file.exists() && file.isFile()) {
				FileUtil.delete(file);
			}
		}
		if (StringUtil.isNotEmpty(errorMessage)) {
			Response.setLogInfo(1, errorMessage);
		} else {
			if (set.deleteAndInsert()) {
				Response.setLogInfo(0, "导入成功！");
			} else {
				Response.setLogInfo(0, "导入失败！");
			}
		}
	}
}
