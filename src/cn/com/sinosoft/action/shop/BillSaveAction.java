package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDBillInfo;
import cn.com.sinosoft.entity.SDBillTitle;
import cn.com.sinosoft.entity.SDDeliverAddress;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.SDBillTitleService;
import cn.com.sinosoft.service.SDDeliverAddressService;
import cn.com.sinosoft.service.SDOrderService;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDRemarkSchema;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台Action类 - 开具发票
 */
public class BillSaveAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4681069613613697836L;
	
	@Resource
	private BindInfoForLoginService bindInfoForLoginService;
	@Resource
	private AreaService areaService;
	@Resource
	private SDDeliverAddressService mSDDeliverAddressService;
	@Resource
	private SDOrderService sDOrderService;
	@Resource
	private SDBillTitleService mSDBillTitleService;

	private String memberID = "";// 登录会员ID
	private String billType;
	private String billTypeName;
	private List<SDDeliverAddress> deliverAddressList = new ArrayList<SDDeliverAddress>();// 邮寄地址信息表
	private int deliverAddressCount = 0;// 已有邮寄地址数量
	private int leftdeliverAddressCount = 0;// 可添加邮寄地址数量
	
	// 订单额 大于 200 标记
	private boolean moreThan200Flag = false;
	// 抬头数量
	private int billTitleCount = 0;
	// 可添加抬头数量
	private int leftbillTitleCount = 0;
	private String billRequireFlag;
	
	private SDOrder order;
	private List<SDBillTitle> billTitles = new ArrayList<SDBillTitle>();
	
	// 投保人名称
	private String applicantName;
	
	private String deliverProvinceName;
	private String deliverCityName;
	private String deliverDetailAddr;
	private String deliverName;
	private String deliverTel;
	private String deliverZipCode;
	private String titleName;
	
	private File fileUpload;//上传文件
	private String fileUploadContentType;//格式同上"fileName"+ContentType 
	private String fileUploadFileName;//格式同上"fileName"+FileName    
	private String orderSns;
	private String titleId;
	private String billReqUrl;

	public String getBillReqUrl() {
		return billReqUrl;
	}

	public void setBillReqUrl(String billReqUrl) {
		this.billReqUrl = billReqUrl;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getOrderSns() {
		return orderSns;
	}

	public void setOrderSns(String orderSns) {
		this.orderSns = orderSns;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}
    public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getDeliverProvinceName() {
		return deliverProvinceName;
	}

	public void setDeliverProvinceName(String deliverProvinceName) {
		this.deliverProvinceName = deliverProvinceName;
	}

	public String getDeliverCityName() {
		return deliverCityName;
	}

	public void setDeliverCityName(String deliverCityName) {
		this.deliverCityName = deliverCityName;
	}

	public String getDeliverDetailAddr() {
		return deliverDetailAddr;
	}

	public void setDeliverDetailAddr(String deliverDetailAddr) {
		this.deliverDetailAddr = deliverDetailAddr;
	}

	public String getDeliverName() {
		return deliverName;
	}

	public void setDeliverName(String deliverName) {
		this.deliverName = deliverName;
	}

	public String getDeliverTel() {
		return deliverTel;
	}

	public void setDeliverTel(String deliverTel) {
		this.deliverTel = deliverTel;
	}

	public String getDeliverZipCode() {
		return deliverZipCode;
	}

	public void setDeliverZipCode(String deliverZipCode) {
		this.deliverZipCode = deliverZipCode;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String enterBilling() {
		String billType = getParameter("billType");
		String orderSn = getParameter("orderSn");
		billRequireFlag = getParameter("billRequireFlag");
		orderSns = orderSn;
		String view = "error";
		
		Member mMember = this.getLoginMember();
		memberID = mMember.getId();

		this.setBillType(billType);
		Map<String, String> billTypeMap = getBillTypeMap();
		this.setBillTypeName(billTypeMap.get(billType));

		deliverAddressList=  mSDDeliverAddressService.getSDDeliverAddressInfo(memberID); 

		if (deliverAddressList != null && deliverAddressList.size() >= 1) {
			deliverAddressCount = deliverAddressList.size();
		}
		leftdeliverAddressCount = 5 - deliverAddressCount;
		
		//发票申请表下载地址
		billReqUrl =  new QueryBuilder("SELECT VALUE FROM zdconfig WHERE TYPE ='BillRequireTemplatePath'").executeString();
		
		String[] orderSnArray = orderSn.split(",");
		if(orderSnArray.length==1){
			// 订单
			SDOrder o = sDOrderService.getOrderByOrderSn(orderSn);
			this.setOrder(o);
			BigDecimal totalAmount = o.getTotalAmount();
			if (null != totalAmount && totalAmount.compareTo(new BigDecimal("200")) >= 0) {
				this.setMoreThan200Flag(true);
			}
			// 投保人姓名
			QueryBuilder queryOrder = new QueryBuilder(
					"select appnt.applicantName,info.insuranceCompany from sdinformation info,sdinformationappnt appnt, sdorders o where "
							+ " info.informationSn = appnt.informationSn "
							+ " and info.orderSn = o.orderSn "
							+ " and o.orderSn = '" + orderSn + "'");
			DataTable applicantDt = queryOrder.executeDataTable();
			if(applicantDt != null && applicantDt.getRowCount()>0){
				titleName =  applicantDt.getString(0, 0);
				billReqUrl = billReqUrl + applicantDt.getString(0, 1) + ".pdf";
			}
		}else {
			BigDecimal totalAmount = new BigDecimal(0);
			List<String> orderSnList = Arrays.asList(orderSnArray);
			for (String orderSnItem : orderSnList) {
				// 订单
				SDOrder o = sDOrderService.getOrderByOrderSn(orderSnItem);
				this.setOrder(o);
				if (null != o.getTotalAmount()) {
					totalAmount = totalAmount.add(o.getTotalAmount());
				}
			}
			if (totalAmount.compareTo(new BigDecimal("200")) >= 0) {
				this.setMoreThan200Flag(true);
			}	
			// 投保人姓名
			QueryBuilder queryOrder = new QueryBuilder(
					"select appnt.applicantName from sdinformation info,sdinformationappnt appnt, sdorders o where "
							+ " info.informationSn = appnt.informationSn "
							+ " and info.orderSn = o.orderSn "
							+ " and o.orderSn in ('" + orderSn.replace(",", "','") + "')");
			titleName = queryOrder.executeString();
			
		}
		// 发票抬头
		List<SDBillTitle> billTitleList = mSDBillTitleService.getSDBillTitle(memberID);
		if (null != billTitleList) {
			for(int i = 0; i < billTitleList.size(); i++){
				billTitles.add(billTitleList.get(i));
			}
		}
		if (billTitles != null && billTitles.size() >= 1) {
			billTitleCount = billTitles.size();
		}
		leftbillTitleCount = 10 - billTitleCount;
		
		view  = "billing";
		return view;
	}

	/**
	 * 获取发票类型名称
	 * 
	 * @return
	 */
	private Map<String, String> getBillTypeMap(){
		DataTable dtBillType = new QueryBuilder("SELECT c.codeValue,c.codeName FROM zdcode c WHERE c.ParentCode = 'billType' ").executeDataTable();
		Map<String, String> billTypeMap = new HashMap<String, String>();
		if (dtBillType != null && dtBillType.getRowCount() > 0) {
			int len = dtBillType.getRowCount();
			for (int i = 0; i < len; i++) {
				billTypeMap.put(dtBillType.get(i, 0).toString(), (String)dtBillType.get(i, 1));
			}
		}
		return billTypeMap;
	}
		
	/**
	 * 发票申请时数据保存处理
	 */
	public String saveBillInfo(){

		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = new JSONObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}

		Member mMember = this.getLoginMember();
		String destPath = getRequest().getSession().getServletContext().getRealPath("upload");
		String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		String sitUrl = getRequest().getContextPath().replace("//", "/");
		
		String basePath = Config.getFrontServerContextPath() + "/" +sitUrl;
		String savePath = "/" + mMember.getId() +"/" + dateStr + fileUploadFileName;
		
		try {
			SDBillInfo billInfo = new SDBillInfo();
			StringBuffer sb = new StringBuffer();
			String[] orderSnArr = orderSns.split(",");

			for (int i = 0; i < orderSnArr.length; i++) {
				sb.append("'");
				sb.append(orderSnArr[i]);
				sb.append("'");
				sb.append(",");
			}
			if(!orderSns.isEmpty()){
				String totalAmount= new QueryBuilder("SELECT SUM(totalamount) FROM sdorders WHERE orderSn IN ( " + sb.deleteCharAt(sb.length() -1).toString() + ")").executeString();
				if(!totalAmount.isEmpty()){
					billInfo.setBillAmount(totalAmount);
				}
			}
			if("04".equals(billType)){
				billInfo.setBillReqUrl(basePath + "/upload" +savePath);
			}
			if("01".equals(billType)){
				titleName = "定额";
			}
			
			billInfo.setBillTitle(titleName);
			billInfo.setBillType(billType);
			billInfo.setDeliverCity(deliverCityName);
			billInfo.setDeliverDate(dateStr);
			billInfo.setDeliverDetailAddr(deliverDetailAddr);
			billInfo.setDeliverName(deliverName);
			billInfo.setDeliverProvince(deliverProvinceName);
			billInfo.setDeliverTel(deliverTel);
			billInfo.setDeliverZipCode(deliverZipCode);
			billInfo.setMemberId(mMember.getId());
			billInfo.setModifyUser(mMember.getUsername());
			billInfo.setStatus("01");
			
			billInfo.setOrderSns(orderSns);
			Map<String,String> result=saveBillInfo(billInfo);
			String flag=result.get("flag");
			if("true".equals(flag)){
				if(fileUpload != null && "04".equals(billType)){
					FileUtils.copyFile(fileUpload, new File(destPath + savePath));	
				}
				tData.put("tFlag", "Suc");
				tData.put("Msg", "申请成功！");
			}else{
				tData.put("tFlag", "Err");
				String message=result.get("message");
				if(StringUtil.isEmpty(message)){
					tData.put("Msg", "申请失败！");
				}else{
					tData.put("Msg",message);
				}
				
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			tData.put("tFlag", "Err");
			tData.put("Msg", "申请失败！");
		}

		JSONObject jsonObject = new JSONObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	/**
	 * 申请信息保存
	 */
	private Map<String,String> saveBillInfo(SDBillInfo billInfo) {
		Map<String,String> result=new HashMap<String,String>();
		result.put("flag", "true");
		String orderSns = billInfo.getOrderSns();
		String[] orderSnArr = orderSns.split(",");
		Transaction trans = new Transaction();
		Date updateDateTime = new Date();
		String billId = NoUtil.getMaxNo("sdbillinfo");
		String insertBillInfoSql = "insert into sdbillinfo(id,billAmount,billReqUrl,billTitle,billType,deliverCity,deliverDate,deliverDetailAddr,deliverName,deliverProvince,deliverTel,deliverZipCode,memberId,status,modifyUser,updateDate,createUser,createDate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		QueryBuilder qbBillInsert = new QueryBuilder(insertBillInfoSql);
		
		qbBillInsert.add(billId);
		qbBillInsert.add(billInfo.getBillAmount());
		qbBillInsert.add(billInfo.getBillReqUrl());
		qbBillInsert.add(billInfo.getBillTitle());
		qbBillInsert.add(billInfo.getBillType());
		qbBillInsert.add(billInfo.getDeliverCity());
		qbBillInsert.add(billInfo.getDeliverDate());
		qbBillInsert.add(billInfo.getDeliverDetailAddr());
		qbBillInsert.add(billInfo.getDeliverName());
		qbBillInsert.add(billInfo.getDeliverProvince());
		qbBillInsert.add(billInfo.getDeliverTel());
		qbBillInsert.add(billInfo.getDeliverZipCode());
		qbBillInsert.add(billInfo.getMemberId());
		qbBillInsert.add(billInfo.getStatus());
		
		qbBillInsert.add(billInfo.getMemberId());
		qbBillInsert.add(updateDateTime);
		qbBillInsert.add(billInfo.getMemberId());
		qbBillInsert.add(updateDateTime);

		trans.add(qbBillInsert);
		String remark = "【发票索要】";
		if (StringUtil.isNotEmpty(billInfo.getDeliverProvince())) {
			remark += billInfo.getDeliverProvince();
		}
		if (StringUtil.isNotEmpty(billInfo.getDeliverCity())) {
			remark += billInfo.getDeliverCity();
		}
		if (StringUtil.isNotEmpty(billInfo.getDeliverDetailAddr())) {
			remark += billInfo.getDeliverDetailAddr();
		}
		remark += (","+billInfo.getDeliverName());
		remark += (","+billInfo.getDeliverTel()+",自助申请");
		for (int i = 0; i < orderSnArr.length; i++) {
			orderSns = orderSnArr[i];
			String selectBillInfoSql = "select count(1) from sdbillinfo s1 , sdbillorderref s2 where s1.id=s2.billId and s1.status!='04' and  s2.orderSn = ? ";
			QueryBuilder qbSelectBillInfo = new QueryBuilder(selectBillInfoSql);
			qbSelectBillInfo.add(orderSns);
			
			int row=qbSelectBillInfo.executeInt();
			if (row> 0) {
				result.put("flag", "false");
				result.put("message", "订单："+orderSns+"已申请开具发票，不能重复申请！");
				return result;
//				Map<String, Object> map = new HashMap<String, Object>();
//				DataColumn[] col = dtBillInfo.getDataRow(0).getDataColumns();
//				for (int j = 0; j < col.length; j++) {
//					map.put((String)col[j].getColumnName(), dtBillInfo.get(0, col[j].getColumnName()));
//				}
//				billId = (String)map.get("id");
//				
//				String updateBillInfoSql = "update sdbillinfo set billAmount=?,billReqUrl=?,billTitle=?,billType=?,deliverCity=?,deliverDate=?,deliverDetailAddr=?,deliverName=?,deliverProvince=?,deliverTel=?,deliverZipCode=?,memberId=?,status=?,modifyUser=?,modifyDate=? where id=?";
//				QueryBuilder qbBillUpdate = new QueryBuilder(updateBillInfoSql);
//				qbBillUpdate.add(billInfo.getBillAmount());
//				qbBillUpdate.add(billInfo.getBillReqUrl());
//				qbBillUpdate.add(billInfo.getBillTitle());
//				qbBillUpdate.add(billInfo.getBillType());
//				qbBillUpdate.add(billInfo.getDeliverCity());
//				qbBillUpdate.add(billInfo.getDeliverDate());
//				qbBillUpdate.add(billInfo.getDeliverDetailAddr());
//				qbBillUpdate.add(billInfo.getDeliverName());
//				qbBillUpdate.add(billInfo.getDeliverProvince());
//				qbBillUpdate.add(billInfo.getDeliverTel());
//				qbBillUpdate.add(billInfo.getDeliverZipCode());
//				qbBillUpdate.add(billInfo.getMemberId());
//				qbBillUpdate.add(billInfo.getStatus());
//				qbBillUpdate.add(billInfo.getMemberId());
//				qbBillUpdate.add(updateDateTime);
//				
//				qbBillUpdate.add(billId);
//				trans.add(qbBillUpdate);
//								
//				String deleteBillOrderRefSql = "delete from sdbillorderref where billId=?";
//				QueryBuilder qbBillDelete = new QueryBuilder(deleteBillOrderRefSql);
//				qbBillDelete.add(billId);
//				trans.add(qbBillDelete);
			}
			
			String insertBillOrderRefSql = "insert into sdbillorderref(id,billId,memberId,orderSn,modifyUser,modifyDate,createUser,createDate) values(?,?,?,?,?,?,?,?)";
			QueryBuilder qbBillOrderRefInsert = new QueryBuilder(insertBillOrderRefSql);
			String billOrderRefId  = NoUtil.getMaxNo("sdbillorderref");
			qbBillOrderRefInsert.add(billOrderRefId);
			qbBillOrderRefInsert.add(billId);
			qbBillOrderRefInsert.add(billInfo.getMemberId());
			qbBillOrderRefInsert.add(orderSns);
			qbBillOrderRefInsert.add(billInfo.getMemberId());
			qbBillOrderRefInsert.add(updateDateTime);
			qbBillOrderRefInsert.add(billInfo.getMemberId());
			qbBillOrderRefInsert.add(updateDateTime);
			
			trans.add(qbBillOrderRefInsert);
			
			// 加保全记录
			SDRemarkSchema schema = new SDRemarkSchema();
			schema.setid(PubFun.GetNRemarkId());
			schema.setremark(remark);
			schema.setorderSn(orderSns);
			schema.setOperateName("");
			schema.setOperateTime(updateDateTime);
			schema.setOperateType("add");
			schema.setprop1("");
			schema.setprop2("");
			schema.setupperId("");
			trans.add(schema, Transaction.INSERT);
		}

		if(!trans.commit()){
			result.put("flag", "false");
		}
		return result;
	}
	
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
	
	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBillTypeName() {
		return billTypeName;
	}

	public void setBillTypeName(String billTypeName) {
		this.billTypeName = billTypeName;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public List<SDDeliverAddress> getDeliverAddressList() {
		return deliverAddressList;
	}

	public void setDeliverAddressList(List<SDDeliverAddress> deliverAddressList) {
		this.deliverAddressList = deliverAddressList;
	}

	public int getDeliverAddressCount() {
		return deliverAddressCount;
	}

	public void setDeliverAddressCount(int deliverAddressCount) {
		this.deliverAddressCount = deliverAddressCount;
	}

	public int getLeftdeliverAddressCount() {
		return leftdeliverAddressCount;
	}

	public void setLeftdeliverAddressCount(int leftdeliverAddressCount) {
		this.leftdeliverAddressCount = leftdeliverAddressCount;
	}

	public SDOrder getOrder() {
		return order;
	}

	public void setOrder(SDOrder order) {
		this.order = order;
	}

	public boolean isMoreThan200Flag() {
		return moreThan200Flag;
	}

	public void setMoreThan200Flag(boolean moreThan200Flag) {
		this.moreThan200Flag = moreThan200Flag;
	}

	public int getBillTitleCount() {
		return billTitleCount;
	}

	public void setBillTitleCount(int billTitleCount) {
		this.billTitleCount = billTitleCount;
	}

	public int getLeftbillTitleCount() {
		return leftbillTitleCount;
	}

	public void setLeftbillTitleCount(int leftbillTitleCount) {
		this.leftbillTitleCount = leftbillTitleCount;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getBillRequireFlag() {
		return billRequireFlag;
	}

	public void setBillRequireFlag(String billRequireFlag) {
		this.billRequireFlag = billRequireFlag;
	}

	public List<SDBillTitle> getBillTitles() {
		return billTitles;
	}

	public void setBillTitles(List<SDBillTitle> billTitles) {
		this.billTitles = billTitles;
	}

	
}
