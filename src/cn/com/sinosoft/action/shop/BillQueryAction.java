package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.BindInfoForLoginService;
import com.sinosoft.cms.dataservice.BillInfoManage;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import net.sf.json.JSONObject;

/**
 * 前台Action类 - 发票信息检索
 */
@ParentPackage("member")
public class BillQueryAction extends BaseShopAction  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6081540794203043343L;

	public static final int pagesize = 10;
	private int page = 1;
	private int histPage = 1;
	private int count;
	private int lastpage; 
	private int histCount;
	private int histLastpage;
	private List<Map<Object,Object>> listOrders;//订单列表集合
	private List<Map<Object,Object>> listBills;//发票列表集合
	private String queryFlag;//查询flag
	private List<String> rowspan_list=new ArrayList<String>();//需要和并行的订单的集合
	private String info_id;// 发票id
	private String mergeOrdersInfo;//合并订单的订单id，发票类型，投保人信息字符串
	private String buyNoneFlag;
	private String prompt;// 温馨提示
	
	public String getBuyNoneFlag() {
		return buyNoneFlag;
	}
	public void setBuyNoneFlag(String buyNoneFlag) {
		this.buyNoneFlag = buyNoneFlag;
	}
	public String getMergeOrdersInfo() {
		return mergeOrdersInfo;
	}
	public void setMergeOrdersInfo(String mergeOrdersInfo) {
		this.mergeOrdersInfo = mergeOrdersInfo;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	@Resource
	private BindInfoForLoginService bindInfoForLoginService;
	
	@Resource
	private AreaService areaService;

	/**
	 * 检查当前用户是否登录成功
	 * 
	 * @return 判断是否登录
	 */
	public boolean checkLogin() {
		Member member = getLoginMember();

		if (member == null || "".equals(member)) {
			return false;
		}
		return true;
	}
	/**
	 * 发票查询订单列表
	* @Title: memberqueryorder
	* @Description: (发票查询订单列表) 
	* @return String    返回类型 
	* @author 
	 */
	public String queryBill() {
		Member loginMember = getLoginMember();
		if (loginMember == null) {
			addActionError("请登陆后，再进行此操作！");
			return ERROR;
		}
		String memberId = loginMember.getId();
		
		try {
			queryFlag="order";
	
			//判断当用户没有是否购买过产品
			String selectBuyCont = "select s1.orderSn FROM sdorders s1 WHERE s1.memberid=? ";
			QueryBuilder selectBuyContQB = new QueryBuilder(selectBuyCont);
			selectBuyContQB.add(memberId);
			DataTable dtSelectBuyCont = selectBuyContQB.executeDataTable();
			if (dtSelectBuyCont.getRowCount() > 0) {
				buyNoneFlag = "0";
			} else {
				buyNoneFlag = "1";
			}
			
			memberId = loginMember.getId();
			//分页查询
			String limit_sql=" limit " + (page - 1) * pagesize + "," + pagesize;
		
			StringBuffer selectCont = new StringBuffer("select count(1) FROM sdorders s1 INNER JOIN sdinformation s2 ON s1.orderSn = s2.orderSn INNER JOIN sdproduct s3  ON s2.productId = s3.productId INNER JOIN sdinformationappnt s5 ON s5.informationsn = s2.informationsn LEFT JOIN zdcode z1 ON z1.CodeType='orderstatus' AND z1.CodeValue=s1.orderStatus WHERE  (s1.orderStatus = '7'||s1.orderStatus = '10') AND s1.memberid='"+ memberId + "'");
			selectCont.append("  AND NOT EXISTS (SELECT 1 FROM sdbillorderref s3,sdbillinfo s4 WHERE s3.billId = s4.id AND (s4.status IS NULL || s4.status <> '04') AND s3.orderSn = s1.orderSn AND s3.memberId='"+ memberId +"')");
			String startSelect = "SELECT s5.applicantName,s2.insuranceCompany,s2.productName, s3.htmlPath,s3.isPublish, TRUNCATE(s1.totalamount,2) as totalAmount, z1.codeName, s1.orderSn, s1.createDate  FROM sdorders s1 INNER JOIN sdinformation s2 ON s1.orderSn = s2.orderSn INNER JOIN sdproduct s3  ON s2.productId = s3.productId INNER JOIN sdinformationappnt s5 ON s5.informationsn = s2.informationsn LEFT JOIN zdcode z1 ON z1.CodeType='orderstatus' AND z1.CodeValue=s1.orderStatus WHERE  (s1.orderStatus = '7'||s1.orderStatus = '10') AND s1.memberid='"+ memberId + "'";

			StringBuffer sql_select=new StringBuffer(startSelect);
			sql_select.append("  AND NOT EXISTS (SELECT 1 FROM sdbillorderref s3,sdbillinfo s4 WHERE s3.billId = s4.id AND (s4.status IS NULL || s4.status <> '04') AND s3.orderSn = s1.orderSn AND s3.memberId='"+ memberId +"')");
			sql_select.append("  ORDER BY s1.createdate DESC");
			sql_select.append(limit_sql);

			String countSql = selectCont.toString();
			DataTable dt1 = new QueryBuilder(countSql).executeDataTable();
			if (dt1.getRowCount() > 0) {
				count = dt1.getInt(0, 0);
			} else {
				count = 0;
			}
			DataTable dt = new QueryBuilder(sql_select.toString()).executeDataTable();
			listOrders = new ArrayList<Map<Object,Object>>();
			
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
			
			DataTable dtBillType = new QueryBuilder("SELECT c.codeValue,c.codeName FROM zdcode c WHERE c.ParentCode = 'billType' ").executeDataTable();
			Map<String, Object> billTypeMap = new HashMap<String, Object>();
			if (dtBillType != null && dtBillType.getRowCount() > 0) {
				int len = dtBillType.getRowCount();
				for (int i = 0; i < len; i++) {
					
					billTypeMap.put((String)dtBillType.get(i, 0), dtBillType.get(i, 1));
				}
			}
			
			for (Map<Object, Object> map : listOrders) {
				
				// 淘宝的订单在会员中心不可点击产品名称链接到详细页
				if (String.valueOf(map.get("orderSn")).startsWith("TB")) {
					map.put("isPublish","N");
				}
				//KID
				map.put("KID", StringUtil.md5Hex(PubFun.getKeyValue() + map.get("orderSn")));
				//发票类型
				StringBuffer typeBuffer = new StringBuffer();
				if(billTypeMap.values().size()>0 && !StringUtil.isEmpty(map.get("insuranceCompany"))){
					DataTable dtKind =  new QueryBuilder("SELECT c.memo FROM zdcode c WHERE c.ParentCode = 'companyBillType' AND c.CodeValue = '"+(String)map.get("insuranceCompany") + "'").executeDataTable();
					if(dtKind!=null && dtKind.getRowCount()>0){
						String memo = dtKind.getString(0, 0);
						map.put("billType",memo);
						String[] memoArray =memo.split(",");
						if(memoArray.length>0){
							for(int i = 0; i < memoArray.length;i++){
								if(billTypeMap.containsKey(memoArray[i]))
								typeBuffer.append(billTypeMap.get(memoArray[i]));
								typeBuffer.append("<br>");	
							}
						}
					}
					String billTypes = typeBuffer.toString();
					if(!billTypes.isEmpty()){
						billTypes = billTypes.substring(0, billTypes.length()-1);
					}
					map.put("billTypeName",billTypes);
				}
				
				
			}
			
			// 取得发票特别提示信息
			DataTable promptDt = new QueryBuilder("SELECT CodeName, Memo FROM zdcode WHERE CodeType='Bill.Prompt' and ParentCode = 'Bill.Prompt' and CodeValue='prompt'").executeDataTable();
			if (promptDt != null && promptDt.getRowCount() > 0) {
				// 展示时间范围
				String showTime = promptDt.getString(0, 0);
				// 展示信息
				String info = promptDt.getString(0, 1);
				boolean flag = true;
				// 设置展示时间范围的情况 判断现在是否展示提示信息
				if (StringUtil.isNotEmpty(showTime)) {
					int index = showTime.indexOf("/");
					if (index >= 0) {
						String startTime = showTime.substring(0, index);
						String endTime = showTime.substring(index+1);
						
						if (StringUtil.isNotEmpty(endTime)) {
							if (DateUtil.compare(endTime, DateUtil.getCurrentDateTime(), "yyyy-MM-dd HH:mm:ss") <= 0) {
								flag = false;
							}
						}
						if (StringUtil.isNotEmpty(startTime)) {
							if (DateUtil.compare(startTime, DateUtil.getCurrentDateTime(), "yyyy-MM-dd HH:mm:ss") > 0) {
								flag = false;
							}
						}
					}
					
				} 
				if (flag) {
					prompt = info;
				}
			}
			
			this.lastpage = ((count + pagesize - 1) / (pagesize));
			setActionAlias("bill_query!queryBill.action");
			Map<String, String> param_map = new HashMap<String, String>();
			param_map.put("totalCounts", String.valueOf(count));
			page_count = String.valueOf(lastpage);
			page_Index = String.valueOf(page);
			getPageDataList(param_map);
			HttpServletRequest request = ServletActionContext.getRequest();	
			request.setAttribute("pageFootList1", getPageFootList());
			return "query";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}
	}
	
	/**
	 * 发票查询发票历史列表
	* @Title: queryBillHist
	* @Description: (发票查询发票历史列表) 
	* @return String    返回类型 
	 */
	public String queryBillHist() {
		Member loginMember = getLoginMember();
		if (loginMember == null) {
			addActionError("请登陆后，再进行此操作！");
			return ERROR;
		}

		queryFlag="bill";
		String memberId = loginMember.getId();
		
		//跳转URL
		String URL="query";
		//分页查询
		String limit_sql=" limit " + (page - 1) * pagesize + "," + pagesize;
	
		StringBuffer selectCont = new StringBuffer("select count(1) FROM sdbillinfo s1 ,sdinformation s2,sdbillorderref s3,sdproduct s4 WHERE s1.id = s3.billId AND s2.orderSn = s3.orderSn AND s2.productId=s4.productId AND s1.memberId='"+ memberId + "'");
		String startSelect = "SELECT s2.orderSn,s1.id,s2.productName,s4.isPublish,s4.htmlPath,s1.status,s1.deliverDate,s1.billAmount,s1.billType  FROM sdbillinfo s1 ,sdinformation s2,sdbillorderref s3,sdproduct s4 WHERE s1.id = s3.billId AND s2.orderSn = s3.orderSn AND s2.productId=s4.productId AND s1.memberId='"+ memberId + "'";

		StringBuffer sql_select=new StringBuffer(startSelect);
		sql_select.append("  ORDER BY s1.deliverDate DESC");
		sql_select.append(limit_sql);

		try {
			String countSql = selectCont.toString();
			DataTable dt1 = new QueryBuilder(countSql).executeDataTable();
			if (dt1.getRowCount() > 0) {
				histCount = dt1.getInt(0, 0);
			} else {
				histCount = 0;
			}
			DataTable dt = new QueryBuilder(sql_select.toString()).executeDataTable();
			listBills = new ArrayList<Map<Object,Object>>();
			
			if (dt != null && dt.getRowCount() > 0) {
				int len = dt.getRowCount();
				for (int i = 0; i < len; i++) {
					Map<Object, Object> map = new HashMap<Object, Object>();
					DataColumn[] col = dt.getDataRow(i).getDataColumns();
					for (int j = 0; j < col.length; j++) {
						map.put(col[j].getColumnName(), dt.get(i, col[j].getColumnName()));
					}
					listBills.add(map);
				}
			}

			//发票类型信息取得
			DataTable dtBillType = new QueryBuilder("SELECT c.codeValue,c.codeName FROM zdcode c WHERE c.ParentCode = 'billType' ").executeDataTable();
			Map<String, Object> billTypeMap = new HashMap<String, Object>();
			if (dtBillType != null && dtBillType.getRowCount() > 0) {
				int len = dtBillType.getRowCount();
				for (int i = 0; i < len; i++) {
					
					billTypeMap.put((String)dtBillType.get(i, 0), dtBillType.get(i, 1));
				}
			}
			
			//发票申请状态信息取得
			DataTable dtBillStatus = new QueryBuilder("SELECT c.codeValue,c.codeName FROM zdcode c WHERE c.ParentCode = 'billStatus' ").executeDataTable();
			Map<String, Object> billStatusMap = new HashMap<String, Object>();
			if (dtBillStatus != null && dtBillStatus.getRowCount() > 0) {
				int len = dtBillStatus.getRowCount();
				for (int i = 0; i < len; i++) {
					
					billStatusMap.put((String)dtBillStatus.get(i, 0), dtBillStatus.get(i, 1));
				}
			}
			
			for (Map<Object, Object> map : listBills) {
				
				//发票申请状态
				if((String)map.get("status")!=null){
					if(billStatusMap.containsKey(map.get("status"))){
						map.put("billStatusName",billStatusMap.get(map.get("status")));
					}
				}
				
				// 淘宝的订单在会员中心不可点击产品名称链接到详细页
				if (String.valueOf(map.get("orderSn")).startsWith("TB")) {
					map.put("isPublish","N");
				}
				//KID
				map.put("KID", StringUtil.md5Hex(PubFun.getKeyValue() + map.get("orderSn")));
				//发票类型
				if(billTypeMap.values().size()>0 && !StringUtil.isEmpty(map.get("billType"))){
					if(billTypeMap.containsKey(map.get("billType"))){
						map.put("billTypeName",billTypeMap.get(map.get("billType")));
					}
				}
				//校验该订单是否已经赋值rowspan  true为已经赋值
				if(StringUtil.isNotEmpty(String.valueOf(map.get("id")))){
					if(!checkRowspan(String.valueOf(map.get("id")))){
						//table  合并单元格  rowspan属性赋值
						int rowspan=countRowspan(listBills,String.valueOf(map.get("id")));
						if(rowspan==0){
							map.put("row_span_num","2");
						}else{
							map.put("row_span_num",rowspan);
						}
					}else{
						
						map.put("row_span_num","2");
					}
				}else{
					map.put("row_span_num","2");
				}
			}
			this.lastpage = ((histCount + pagesize - 1) / (pagesize));
			setActionAlias("bill_query!queryBillHist.action");
			Map<String, String> param_map = new HashMap<String, String>();
			param_map.put("totalCounts", String.valueOf(histCount));
			page_count = String.valueOf(lastpage);
			page_Index = String.valueOf(page);
			getPageDataList(param_map);
			HttpServletRequest request = ServletActionContext.getRequest();	
			request.setAttribute("pageFootList", getPageFootList());
			return URL;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}
	}
	
	/**
	 * 
	* @Title: cancelBillInfo 
	* @Description: (取消发票申请) 
	* @return String    返回类型 
	 */
	public String cancelBillInfo(){
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = new JSONObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		try {
			QueryBuilder qb_update = new QueryBuilder("update sdbillinfo set status=? where id=?");
			qb_update.add("04");
			qb_update.add(info_id);
			int num = qb_update.executeNoQuery();
			if (num != 1) {
				tData.put("tFlag", "Err");
				tData.put("Msg", "撤销发票申请失败！");
			}
			
			// 保全记录增加：发票索要撤销
			String remark = "发票索要撤销";
			BillInfoManage billMan = new BillInfoManage();
			billMan.addRemak(info_id, remark, "");
			
			tData.put("tFlag", "Suc");
		} catch (Exception e) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "撤销发票申请失败！");
			logger.error(e.getMessage(), e);
		}

		JSONObject jsonObject = new JSONObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	/**
	 * 
	* @Title: checkRowspan 
	* @Description: (校验该支付流水号是否已经赋值rowspan  true为已经赋值) 
	* @return boolean    返回类型 
	 */
	private boolean checkRowspan(String orderSn){
		for (int i = 0; i < rowspan_list.size(); i++) {
			if(String.valueOf(rowspan_list.get(i)).trim().equals(orderSn)){
				return true;
			}
		}
		rowspan_list.add(orderSn);
		return false;
	}

	/**
	 * 
	* @Title: countRowspan 
	* @Description: (获得该发票id对应订单的table的合并的行数) 
	* @return String    返回类型 
	 */
	private int countRowspan(List<Map<Object,Object>> listOrders2,String billId){
		int i=0;
		for (Map<Object, Object> map:listOrders2) {
			if(StringUtil.isNotEmpty(String.valueOf(map.get("id")))){
				if(String.valueOf(map.get("id")).equals(billId)){
					i=i+2;
				}
			}
		}
		return i;
	}
	

	/**
	 * 合并补开时发票类型，被保人校验
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String ajaxValidateMerge() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = new JSONObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}

		List<OrderBillTypeInfo> orderBillTypeInfoList = new ArrayList<OrderBillTypeInfo>();
		OrderBillTypeInfo orderBillTypeInfo =null;
		try {
			if(mergeOrdersInfo != null && mergeOrdersInfo != ""){
				JSONArray arr = new JSONArray(mergeOrdersInfo);
				JSONObject itemJSONObj = null;
				if(arr!=null){
					for(int i = 0; i< arr.length();i++){
						itemJSONObj = (JSONObject)arr.get(i);
						orderBillTypeInfo = new OrderBillTypeInfo();
						orderBillTypeInfo.setOrderSn(itemJSONObj.getString("orderSn"));
						orderBillTypeInfo.setBillType(itemJSONObj.getString("billType"));
						orderBillTypeInfo.setApplicantName(itemJSONObj.getString("applicantName"));
						orderBillTypeInfo.setInsuranceCompany(itemJSONObj.getString("insuranceCompany"));
						orderBillTypeInfoList.add(orderBillTypeInfo);
					}
				}
				//订单号list
				List<String> orderSnSectionList = new ArrayList<String>();
				//发票类型交集list
				ArrayList<String> typeIntersectionList = new ArrayList<String>();
				String types = null;
				//发票类型Array
				String[] typeArray = null;
				//投保人姓名
				String applicantName = null;
				//保险公司Code
				String insuranceCompany =null;
				int i = 0;
				List<String> tempTypeList = null;
				// 投保人是否一致标识
				String applicantNameFlag = "true";
				boolean hasIntersectionFlag = true;
				for (OrderBillTypeInfo billTypeInfo : orderBillTypeInfoList) {
					orderSnSectionList.add(billTypeInfo.getOrderSn());
					types = billTypeInfo.getBillType();
					if(types != null){
						typeArray = types.split(",");
						if(i == 0){
							for (String type : typeArray) {
								typeIntersectionList.add(type);
							}
							applicantName = billTypeInfo.getApplicantName();
							insuranceCompany = billTypeInfo.getInsuranceCompany();
						}else{
							ArrayList<String> typeIntersectionCopyList = (ArrayList<String>) typeIntersectionList.clone();
							for (String typeItem : typeIntersectionCopyList) {
								tempTypeList = Arrays.asList(typeArray);
								if(tempTypeList.contains(typeItem)){
									continue;
								}else{
									typeIntersectionList.remove(typeItem);
								}
							}
							if(typeIntersectionList.size()==0){
								hasIntersectionFlag = false;
								break;
							}

							if(applicantName!=null && !applicantName.equals(billTypeInfo.getApplicantName())){
								applicantName = null;
								applicantNameFlag = "false";
							}
							if(insuranceCompany!=null && !insuranceCompany.equals(billTypeInfo.getInsuranceCompany())){
								insuranceCompany = null;
							}
						}
					}
					i++;
				}
				if(!hasIntersectionFlag){
					// 不同类型发票
					tData.put("tFlag", "Err");
					tData.put("Msg", "BILLTYPECHECKERR1");
				}else if(insuranceCompany==null){
					// 不同公司 
					tData.put("tFlag", "Err");
					tData.put("Msg", "BILLTYPECHECKERR2");
				}else if(isApplicantNotMatch(typeIntersectionList, applicantName)){
					// 抬头不同
					tData.put("tFlag", "Err");
					tData.put("Msg", "BILLTYPECHECKERR3");
				}else{
					tData.put("tFlag", "Suc");
					tData.put("applicantNameFlag", applicantNameFlag);
					
					StringBuffer sb = new StringBuffer();
					for (String type : typeIntersectionList) {
						sb.append(type);
						sb.append(",");
					}
					tData.put("TypeData", sb.deleteCharAt(sb.length() -1).toString());
					sb = new StringBuffer();
					for (String orderSn : orderSnSectionList) {
						sb.append(orderSn);
						sb.append(",");
					}
					tData.put("OrderSns", sb.deleteCharAt(sb.length() -1).toString());
				}
				JSONObject jsonObject = new JSONObject(tData);
				return ajax(jsonObject.toString(), "text/html");
			}
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
		}catch(Exception ex){
			logger.error(ex.getMessage(), ex);
		}

		JSONObject jsonObject = new JSONObject(tData);

		return ajax(jsonObject.toString(), "text/html");
	}
	
	
	/**
	 * 被保人姓名不同的check
	 * @param typeIntersectionList 类型list
	 * @param applicantName 被保人姓名
	 * @return  boolean 不同的flag
	 */
	private boolean isApplicantNotMatch(ArrayList<String> typeIntersectionList, String applicantName){
		if(typeIntersectionList.size()==1 && typeIntersectionList.contains("03") && applicantName == null){
			return true;
		}else{
			return false;
		}
	}
	
	public List<Map<Object, Object>> getListBills() {
		return listBills;
	}
	
	public void setListBills(List<Map<Object, Object>> listBills) {
		this.listBills = listBills;
	}
	public int getHistPage() {
		return histPage;
	}

	public void setHistPage(int histPage) {
		this.histPage = histPage;
	}

	public int getHistCount() {
		return histCount;
	}

	public void setHistCount(int histCount) {
		this.histCount = histCount;
	}

	public int getHistLastpage() {
		return histLastpage;
	}

	public void setHistLastpage(int histLastpage) {
		this.histLastpage = histLastpage;
	}

	public String getQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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

	public List<Map<Object,Object>> getListOrders() {
		return listOrders;
	}

	public void setListOrders(List<Map<Object,Object>> listOrders) {
		this.listOrders = listOrders;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}

	public String getInfo_id() {
		return info_id;
	}
	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}
	public class OrderBillTypeInfo{

		private String orderSn;
		private String billType;
		private String applicantName;
		private String insuranceCompany;
		public String getInsuranceCompany() {
			return insuranceCompany;
		}
		public void setInsuranceCompany(String insuranceCompany) {
			this.insuranceCompany = insuranceCompany;
		}
		public String getOrderSn() {
			return orderSn;
		}
		public void setOrderSn(String orderSn) {
			this.orderSn = orderSn;
		}
		public String getBillType() {
			return billType;
		}
		public void setBillType(String billType) {
			this.billType = billType;
		}
		public String getApplicantName() {
			return applicantName;
		}
		public void setApplicantName(String applicantName) {
			this.applicantName = applicantName;
		}
	}
}
