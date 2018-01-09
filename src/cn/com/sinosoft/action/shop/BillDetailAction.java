package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.LogisticsInfo;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDDeliverAddress;
import cn.com.sinosoft.service.BindInfoForLoginService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台Action类 - 发票详情查看
 */
public class BillDetailAction extends BaseShopAction  {

	private static final long serialVersionUID = -3568910610051220914L;

	private String info_id;// 发票id
	private String addressInfo;//邮寄地址
	private String personInfo;//接收人信息
	private String deliverStatus;//邮寄状态
	private String logisticsId;//物流公司ID
	private String logisticsName;//物流公司名称
	private String wayBillNo;//运单号
	private String logisticsInfoJson;//物流信息Json字符串
	private LogisticsInfo logisticsInfo;//物流信息对象
	private String billTitle;//发票抬头
	private List<String> rowspan_list=new ArrayList<String>();//需要和并行的订单的集合
	private List<Map<Object,Object>> listBills;//发票列表集合
	private SDDeliverAddress mSDDeliverAddress;// 邮寄信息
	private String billReqUrl;//开票申请表Url
	private String billType;//发票类型

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	@Resource
	private BindInfoForLoginService bindInfoForLoginService;
	
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
	public String getBillDetailInfo() {
		if (!checkLogin()) {
			addActionError("请登陆后，再进行此操作！");
			return ERROR;
		}
		
		String memberId = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Member loginMember = null;
		if (!"tencent".equals(memberId)) {
			loginMember = memberService.load(memberId);
		} else {
			loginMember = bindInfoForLoginService.get((String)getSession("loginBindId")).getmMember();
		}
		if (loginMember != null) {
			memberId = loginMember.getId();
		}
		
		String startSelect = "SELECT s2.insuranceCompany,s1.billReqUrl,s1.billTitle,s1.logisticsId,s1.wayBillNo,s1.status,s1.deliverProvince,s1.deliverCity,s1.deliverDetailAddr,s1.deliverZipCode,s1.deliverTel,s1.deliverName,s2.orderSn,s1.id,s2.productName,s4.isPublish,s4.htmlPath,s1.deliverDate,s1.billAmount,s1.billType  FROM sdbillinfo s1 ,sdinformation s2,sdbillorderref s3,sdproduct s4 WHERE s1.id = s3.billId AND s2.orderSn = s3.orderSn AND s2.productId=s4.productId AND s1.memberId=? AND s1.id=?";

		StringBuffer sql_select=new StringBuffer(startSelect);
		sql_select.append("  ORDER BY s1.deliverDate DESC");
		
		try {
			QueryBuilder qb = new QueryBuilder(sql_select.toString());
			qb.add(memberId);
    		qb.add(info_id);
			DataTable dt = qb.executeDataTable();
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

			//邮寄地址信息的取得
			if(listBills.size()>0){
				billType = (String)listBills.get(0).get("billType");
				billTitle = (String)listBills.get(0).get("billTitle");
				billReqUrl = (String)listBills.get(0).get("billReqUrl");
				mSDDeliverAddress = new SDDeliverAddress();
				StringBuffer sb = new StringBuffer();
				mSDDeliverAddress.setProvinceName((String)listBills.get(0).get("deliverProvince"));
				mSDDeliverAddress.setCityName((String)listBills.get(0).get("deliverCity"));
				mSDDeliverAddress.setDetailAddr((String)listBills.get(0).get("deliverDetailAddr"));
				mSDDeliverAddress.setZipCode((String)listBills.get(0).get("deliverZipCode"));
				mSDDeliverAddress.setName((String)listBills.get(0).get("deliverName"));
				mSDDeliverAddress.setTel((String)listBills.get(0).get("deliverTel"));
				if(mSDDeliverAddress.getProvinceName()!=null){
					sb.append(mSDDeliverAddress.getProvinceName());
					sb.append("  ");
					sb.append(mSDDeliverAddress.getCityName());
					sb.append("  ");
					sb.append(mSDDeliverAddress.getDetailAddr());
					sb.append("，");
					sb.append(mSDDeliverAddress.getZipCode());
					addressInfo = sb.toString();	
					personInfo = mSDDeliverAddress.getName() + "，" + mSDDeliverAddress.getTel();
				}
				//邮寄状态
				deliverStatus = (String)listBills.get(0).get("status");
				//物流id
				logisticsId = (String)listBills.get(0).get("logisticsId");
				//运单号
				wayBillNo = (String)listBills.get(0).get("wayBillNo");
			}
			
			//发票状态信息定义取得
			DataTable dtBillType = new QueryBuilder("SELECT c.codeValue,c.codeName FROM zdcode c WHERE c.ParentCode = 'billType' ").executeDataTable();
			Map<String, String> billTypeMap = new HashMap<String, String>();
			if (dtBillType != null && dtBillType.getRowCount() > 0) {
				int len = dtBillType.getRowCount();
				for (int i = 0; i < len; i++) {
					billTypeMap.put((String)dtBillType.get(i, 0), (String)dtBillType.get(i, 1));
				}
			}

			//物流公司名称定义取得
			if("03".equals(deliverStatus) && !logisticsId.isEmpty()){
				QueryBuilder qbLogistics = new QueryBuilder("SELECT c.codeName FROM zdcode c WHERE c.ParentCode = 'LogisticsCom' AND c.codeValue =? ");
				qbLogistics.add(logisticsId);
				logisticsName =qbLogistics.executeString();
			}
			
			logisticsInfoJson = getLogisticsInfo(logisticsId,wayBillNo);
			logisticsInfo =new LogisticsInfo();
			if(logisticsInfoJson!=null){
				logisticsInfo = parseLogisticsInfo(logisticsInfoJson);
			}
			
			//发票信息整理
			for (Map<Object, Object> map : listBills) {

				//校验该订单是否已经赋值rowspan  true为已经赋值
				if(StringUtil.isNotEmpty(String.valueOf(map.get("id")))){
					if(!checkRowspan(String.valueOf(map.get("id")))){
						//table  合并单元格  rowspan属性赋值
						int rowspan=countRowspan(listBills,String.valueOf(map.get("id")));
						if(rowspan==0){
							map.put("row_span_num","1");
						}else{
							map.put("row_span_num",rowspan);
						}
					}else{
						
						map.put("row_span_num","1");
					}
				}else{
					map.put("row_span_num","1");
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
						String billTypeName = (String)billTypeMap.get(map.get("billType"));
						map.put("billTypeName",billTypeName);
					}
				}
			}
			return "detail";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}
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
					i=i+1;
				}
			}
		}
		return i;
	}
	
	/**
	 * 物流信息字符串取得方法
	 * @param logisticsId
	 * @param wayBillNo
	 * @return 物流信息字符串
	 */
	public String getLogisticsInfo(String logisticsId, String wayBillNo){
		String requestUrl = "http://api.ickd.cn/?id=110496&secret=564a243ea386a5e4bf15d053fb132910&type=json&encode=utf8&ord=asc&lang=en&com="+logisticsId+"&nu="+wayBillNo+"&temp=" + Math.random();  
        StringBuffer strBuf;  
        strBuf = new StringBuffer();  
        String jsonStr = null;
        try{  
            URL url = new URL(requestUrl);  
            URLConnection conn = url.openConnection();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8")); 
            String result = null;  
            while ((result = reader.readLine()) != null)  {
            	strBuf.append(result + " ");  
            }
            reader.close();  
            jsonStr = strBuf.toString();
        }catch(MalformedURLException e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
        }catch(IOException e){
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());  
        }     
        return jsonStr;  
	}
	
	
	/**
	 * 解析Json串为物流信息对象
	 * @param info 物流信息JSON串
	 */
	public LogisticsInfo parseLogisticsInfo(String info){
		LogisticsInfo loginsticsInfo = new LogisticsInfo();
		
		JSONObject dataJson;
		try {
			dataJson = new JSONObject(info);
			loginsticsInfo.setStatus(dataJson.getString("status"));
			loginsticsInfo.setMessage(dataJson.getString("message"));
			loginsticsInfo.setErrCode(dataJson.getString("errCode"));
			JSONArray data=dataJson.getJSONArray("data");
			List<LogisticsInfo.Item> itemList = new ArrayList<LogisticsInfo.Item>(); 
			for(int i=0; i< data.length();i++){
				
				JSONObject itemObj =data.getJSONObject(i);
				LogisticsInfo.Item item = new LogisticsInfo.Item(); 
				item.setTime(itemObj.getString("time"));
				item.setContext(itemObj.getString("context"));
				itemList.add(item);
			}
			loginsticsInfo.setData(itemList);
			
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
		}
		return loginsticsInfo;
	}

	public String getLogisticsInfoJson() {
		return logisticsInfoJson;
	}

	public void setLogisticsInfoJson(String logisticsInfoJson) {
		this.logisticsInfoJson = logisticsInfoJson;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

	public String getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(String personInfo) {
		this.personInfo = personInfo;
	}

	public String getDeliverStatus() {
		return deliverStatus;
	}

	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}

	public String getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(String logisticsId) {
		this.logisticsId = logisticsId;
	}

	public String getWayBillNo() {
		return wayBillNo;
	}

	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	
	public List<Map<Object, Object>> getListBills() {
		return listBills;
	}

	public void setListBills(List<Map<Object, Object>> listBills) {
		this.listBills = listBills;
	}

	public SDDeliverAddress getmSDDeliverAddress() {
		return mSDDeliverAddress;
	}

	public void setmSDDeliverAddress(SDDeliverAddress mSDDeliverAddress) {
		this.mSDDeliverAddress = mSDDeliverAddress;
	}


	public String getInfo_id() {
		return info_id;
	}

	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}

	public String getBillTitle() {
		return billTitle;
	}

	public void setBillTitle(String billTitle) {
		this.billTitle = billTitle;
	}

	public LogisticsInfo getLogisticsInfo() {
		return logisticsInfo;
	}

	public void setLogisticsInfo(LogisticsInfo logisticsInfo) {
		this.logisticsInfo = logisticsInfo;
	}

	public String getBillReqUrl() {
		return billReqUrl;
	}

	public void setBillReqUrl(String billReqUrl) {
		this.billReqUrl = billReqUrl;
	}
}
