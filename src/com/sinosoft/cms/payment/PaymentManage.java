/**
 * 
 */
package com.sinosoft.cms.payment;

import com.google.common.io.BaseEncoding;
import com.google.common.io.Files;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ClaimsFileSchema;
import com.sinosoft.schema.CompanyClaimsInfoSchema;
import com.sinosoft.schema.CompanyClaimsPayInfoSchema;
import com.sinosoft.schema.CustomClaimsDataInfoSchema;
import com.sinosoft.schema.PaymentClaimsInfoSchema;
import com.sinosoft.weixin.WeiXinCommon;

import cn.com.sinosoft.common.email.MessageConstant;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author wangcaiyun
 *
 */
public class PaymentManage extends Page {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Mapx init(Mapx params) {
		params.put("ClaimsStatus", HtmlUtil.codeToOptions("ClaimsStatus", true));
		params.put("supplier", HtmlUtil.codeToOptions("SupplierCode", true));
		return params;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Mapx initDialog(Mapx params) {
		params.put("ClaimsStatus", HtmlUtil.codeToOptions("ClaimsStatus", false));
		params.put("ClaimsCaseType", HtmlUtil.codeToOptions("ClaimsCaseType", false));
		params.put("ClaimsReturnType", HtmlUtil.codeToOptions("ClaimsReturnType", false));
		params.put("showFlag", HtmlUtil.codeToOptions("isShowFlag", false));

		String id = params.getString("id");
		if (StringUtil.isNotEmpty(id) ) {
			PaymentClaimsInfoSchema schema = new PaymentClaimsInfoSchema();
			schema.setid(id);
			if (schema.fill()) {
				params.putAll(schema.toMapx());
				params.put("applicationDate", DateUtil.toString(schema.getapplicationDate(), "yyyy-MM-dd"));
				if (schema.getmodifyDate() != null) {
					params.put("ModifyDate", DateUtil.toString(schema.getmodifyDate(), "yyyy-MM-dd HH:mm:ss"));
				}
				params.put("claimsItemsTypeName", new QueryBuilder("select TypeName from FemClaimsItemsType where TypeCode=?",schema.getclaimsItemsType()).executeString());
				params.put("courierFirmName", new QueryBuilder("select CodeName from zdcode where CodeType='LogisticsCom' and ParentCode='LogisticsCom' and CodeValue=?",schema.getcourierFirm()).executeString());
			}
		}
		return params;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Mapx initDialog1(Mapx params) {
		Mapx mapx =new QueryBuilder("select codeValue, codeName from dictionary where codeType='kindOfLoss' and insuranceCode='2049'").executeDataTable().toMapx("codeValue", "codeName");
		params.put("KindOfLoss1", HtmlUtil.mapxToOptions(mapx, true));
		mapx =new QueryBuilder("select codeValue, codeName from dictionary where codeType='claimReason' and insuranceCode='2049'").executeDataTable().toMapx("codeValue", "codeName");
		params.put("ClaimReason1", HtmlUtil.mapxToOptions(mapx, true));
		String id = params.getString("id");
		if (StringUtil.isNotEmpty(id)) {
			PaymentClaimsInfoSchema schema = new PaymentClaimsInfoSchema();
			schema.setid(id);
			if (schema.fill()) {
				params.putAll(schema.toMapx());
				if (StringUtil.isEmpty(schema.getflightNo())) {
					DataTable dt = new QueryBuilder("select i.flightNo, i.flightTime from sdinformationrisktype r, sdinformationinsured i where r.policyNo=? and r.sdinformationinsured_id=i.id", schema.getpolicyNo()).executeDataTable();
					if (dt != null && dt.getRowCount() > 0) {
						if (StringUtil.isNotEmpty(dt.getString(0, 0))) {
							params.put("flightNo", dt.getString(0, 0));
							params.put("flightTime", dt.getString(0, 1));
						}
					}
				}
			}
			
		}
		return params;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx initClaimsData(Mapx params) {
		// params.put("allowType", "doc,docx,xls,xlsx,ppt,pptx,pdf,rar,zip,txt,jpg,gif,jpeg,png,bmp,tif");
		params.put("allowType", "jpg,gif,jpeg,png,bmp,tif");
		
		//允许上传附件大小设置
		String fileSize = "5242880"; //默认5M;
		params.put("fileMaxSize", fileSize);
		long size = Long.parseLong(fileSize);
		String fileMaxSizeHuman = "";
		if (size < 1048576) {
			fileMaxSizeHuman = NumberUtil.round(size * 1.0 / 1024, 1) + "K";
		} else if (size < 1073741824) {
			fileMaxSizeHuman = NumberUtil.round(size * 1.0 / 1048576, 1) + "M";
		}
		params.put("fileMaxSizeHuman", fileMaxSizeHuman);
		
		Map<String, String> map = new HashMap<String, String>();
		String claimsItemsId = (String)params.get("claimsItemsId");
		DataTable dt = new QueryBuilder("select Id, ClaimsDataName from FemClaimsData where ClaimsItemsId = ? order by ShowOrder asc ", claimsItemsId).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			int i = 0;
			for (; i < count; i++) {
				map.put(dt.getString(i, 0), dt.getString(i, 1));
			}
		}
		// 取得理赔项目下理赔资料信息
		params.put("ClaimsData", HtmlUtil.mapxToOptions(map, null, false));
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		String orderSn = (String) dga.getParams().get("orderSn");
		String policyNo = (String) dga.getParams().get("policyNo");
		String memberId = (String) dga.getParams().get("memberId");
		String contactName = (String) dga.getParams().get("contactName");
		String contactMobile = (String) dga.getParams().get("contactMobile");
		String contactMail = (String) dga.getParams().get("contactMail");
		String claimsNo = (String) dga.getParams().get("claimsNo");
		String status = (String) dga.getParams().get("status");
		String startCreateDate = (String) dga.getParams().get("startCreateDate");
		String endCreateDate = (String) dga.getParams().get("endCreateDate");
		String startModifyDate = (String) dga.getParams().get("startModifyDate");
		String endModifyDate = (String) dga.getParams().get("endModifyDate");
		String insureName = (String) dga.getParams().get("insureName");
		String insureIdentityId = (String) dga.getParams().get("insureIdentityId");
		String supplierCode = (String) dga.getParams().get("supplierCode");
		//主站不在显示b2b_app的理赔申请
		QueryBuilder qb = new QueryBuilder("select id,claimsNo,claimsItemsId,claimsItemsName,orderSn,policyNo,'' remark,insureName,insureIdentityId,contactName,contactMobile,contactMail,insureRelation,memberId,(select CodeName from zdcode where codetype='ClaimsCaseType' and codevalue=caseType) caseTypeName,status,(select CodeName from zdcode where codetype='ClaimsStatus' and codevalue=status) statusName,(SELECT channelName FROM channelinfo WHERE channelcode= remark1) channelsn, remark2 AS compleStatus , DATE_FORMAT(applicationDate, '%Y-%m-%d') appliDate,ModifyDate,if ((select count(1) from CustomClaimsDataInfo where CustomClaimsDataInfo.claimsNo=PaymentClaimsInfo.claimsNo) > 0, '是', '否') as cusIsUpload,courierFirm,courierNumber,productId,notificationNo,isUpload,(select productName from sdinformation where orderSn=PaymentClaimsInfo.orderSn) productName from PaymentClaimsInfo where status !='00' AND remark1 not in ('b2b_app','b2b_html5') ");
		if (StringUtil.isNotEmpty(orderSn)) {
			qb.append(" and orderSn = '" + orderSn + "'");
		}
		if (StringUtil.isNotEmpty(policyNo)) {
			qb.append(" and policyNo = '" + policyNo + "'");
		}
		if (StringUtil.isNotEmpty(memberId)) {
			qb.append(" and memberId = '" + memberId + "'");
		}
		if (StringUtil.isNotEmpty(contactName)) {
			qb.append(" and contactName like '%" + contactName + "%'");
		}
		if (StringUtil.isNotEmpty(contactMobile)) {
			qb.append(" and contactMobile = '" + contactMobile + "'");
		}
		if (StringUtil.isNotEmpty(contactMail)) {
			qb.append(" and contactMail = '" + contactMail + "'");
		}
		if (StringUtil.isNotEmpty(claimsNo)) {
			qb.append(" and claimsNo = '" + claimsNo + "'");
		}
		if (StringUtil.isNotEmpty(status)) {
			qb.append(" and status = '" + status + "'");
		}

		if (StringUtil.isNotEmpty(startCreateDate) && StringUtil.isNotEmpty(endCreateDate)) {
			qb.append(" and applicationDate >='" + startCreateDate + " 00:00:00' and applicationDate <='" + endCreateDate + " 23:59:59' ");
		} else if (StringUtil.isNotEmpty(startCreateDate) && StringUtil.isEmpty(endCreateDate)) {
			qb.append(" and applicationDate >='" + startCreateDate + " 00:00:00' ");
		} else if (StringUtil.isEmpty(startCreateDate) && StringUtil.isNotEmpty(endCreateDate)) {
			qb.append(" and applicationDate <='" + endCreateDate + " 23:59:59' ");
		}

		if (StringUtil.isNotEmpty(startModifyDate) && StringUtil.isNotEmpty(endModifyDate)) {
			qb.append(" and ModifyDate >='" + startModifyDate + " 00:00:00' and ModifyDate <='" + endModifyDate + " 23:59:59' ");
		} else if (StringUtil.isNotEmpty(startModifyDate) && StringUtil.isEmpty(endModifyDate)) {
			qb.append(" and ModifyDate >='" + startModifyDate + " 00:00:00' ");
		} else if (StringUtil.isEmpty(startModifyDate) && StringUtil.isNotEmpty(endModifyDate)) {
			qb.append(" and ModifyDate <='" + endModifyDate + " 23:59:59' ");
		}

		if (StringUtil.isNotEmpty(insureName)) {
			qb.append(" and insureName like '%" + insureName + "%'");
		}
		if (StringUtil.isNotEmpty(insureIdentityId)) {
			qb.append(" and insureIdentityId = '" + insureIdentityId + "'");
		}
		if (StringUtil.isNotEmpty(supplierCode)) {
			qb.append(" and productId like '" + supplierCode + "%'");
		}
		qb.append(" order by find_in_set(status,'01,02,05,03,04,07,06'), returnDesc desc, applicationDate asc");
		
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		if (dt != null) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if(StringUtil.isNotEmpty(dt.getString(i, "compleStatus"))){
					dt.set(i, "compleStatus", "已补充"+dt.getString(i, "compleStatus")+"次");
				}else{
					dt.set(i, "compleStatus", "-");//remark2
				}
				if ("06".equals(dt.getString(i, "status"))) {
					dt.set(i, "compleStatus", "已结案");
				} else {
					if (StringUtil.isNotEmpty(dt.getString(i, "courierFirm")) && StringUtil.isNotEmpty(dt.getString(i, "courierNumber"))) {
						dt.set(i, "compleStatus", "已邮寄");
					}
				}
				// 保全记录查询
				String queryRemark = "SELECT remark, OperateTime, OperateName FROM sdremark WHERE prop1='"
						+ dt.getString(i, "id")
						+ "' ORDER BY OperateTime DESC";
				QueryBuilder qbr = new QueryBuilder(queryRemark);
				DataTable dtr = qbr.executeDataTable();
				String remark = "";
				if (dtr != null && dtr.getRowCount() > 0) {
					for (int j = 0; j < dtr.getRowCount(); j++) {
						int a = j + 1;
						remark += a + ", " + dtr.getString(j, "remark") + "  "
								+ dtr.getString(j, "OperateTime") + "  "
								+ dtr.getString(j, "OperateName") + " && ";
					}
					dt.set(i, "remark", remark);
				}
				if ("Y".equals(dt.getString(i, "isUpload"))) {
					dt.set(i, "isUpload", "是");
				} else {
					dt.set(i, "isUpload", "");
				}
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	public static void dg2DataBind(DataGridAction dga) {
		String startDate = (String) dga.getParams().get("startDate") + " 00:00:00";
		String endDate = (String) dga.getParams().get("endDate") + " 23:59:59";
		QueryBuilder qb = new QueryBuilder("select DATE_FORMAT(applicationDate, ?) abnormalTime, GROUP_CONCAT(DISTINCT insureName) insureNames, insureIdentityId, count(1) cliamsCount from PaymentClaimsInfo where applicationDate >= ? and applicationDate <= ? group by abnormalTime, insureIdentityId having cliamsCount > ? order by abnormalTime asc ");
		// 取得理赔异常统计的设置
		DataTable dt = new QueryBuilder("select CodeValue, Memo from zdcode where CodeType='ClaimsAbnormal' and ParentCode='ClaimsAbnormal'").executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			String unit = "";
			String limit = "";
			for (int i = 0; i < dt.getRowCount(); i++) {
				if ("unit".equals(dt.getString(i, "CodeValue"))) {
					unit = dt.getString(i, "Memo");
				} else if ("limit".equals(dt.getString(i, "CodeValue"))) {
					limit = dt.getString(i, "Memo");
				}
			}
			if (StringUtil.isEmpty(unit)) {
				return;
			}
			if (StringUtil.isEmpty(limit)) {
				return;
			} else if (!NumberUtil.isInt(limit)) {
				return;
			}
			
			String format = "";
			if ("月".equals(unit)) {
				format = "%Y年%m月";
			} else if ("周".equals(unit)) {
				format = "%Y年%u周";
			}
			if (StringUtil.isNotEmpty(format)) {
				qb.add(format);
				qb.add(startDate);
				qb.add(endDate);
				qb.add(limit);
				dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());;
				dga.setTotal(qb);
				dga.bindData(dt);
			}
		}
	}
	
	public static void queryCliamsData(DataGridAction dga) {
		String claimsNo = (String) dga.getParams().get("claimsNo");
		
		QueryBuilder qb = new QueryBuilder("select f.id, d.claimsDataName, f.fileSuffix, f.fileSize, f.filePath, f.fileName, '' downPath,f.remark1,f.remark2 from CustomClaimsDataInfo d, ClaimsFile f where d.claimsNo = ? and f.claimsDataId = d.id order by d.id asc", claimsNo);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			int i = 0;
			String filePath;
			String remark1;
			String remark2;
			for (; i < rowCount; i++) {
				filePath = dt.getString(i, "filePath");
				remark1 = dt.getString(i, "remark1");
				remark2 = dt.getString(i, "remark2");
				if (StringUtil.isNotEmpty(filePath)) {
					dt.set(i, "downPath", filePath);
					dt.set(i, "filePath", filePath.replace(Config.getValue("newPolicyPath"), Config.getFrontServerContextPath()));
					if (StringUtil.isEmpty(remark2)) {
						dt.set(i, "remark2", dt.getString(i, "filePath"));
					} else {
						dt.set(i, "remark2", remark2.replace(Config.getValue("newPolicyPath"), Config.getFrontServerContextPath()));
					}
					
					if (StringUtil.isNotEmpty(remark1)) {
						dt.set(i, "downPath", remark1);
						dt.set(i, "filePath", remark1.replace(Config.getValue("newPolicyPath"), Config.getFrontServerContextPath()));
					}
					
				}
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 向保险公司发送理赔信息邮件
	 */
	public void sendClaimsData() {
		String claimsNo = Request.getString("claimsNo");
		// 查询保险公司理赔邮箱
		String email = "";
		String policyNo = "";
		String insureName = "";
		String claimsItemsName = "";
		String bankUserName = "";
		String bankName = "";
		String caseDesc = "";
		DataTable dt= new QueryBuilder("select f.EMail,p.policyNo,p.insureName,p.claimsItemsName,p.bankName,p.bankUserName,p.caseDesc from paymentclaimsinfo p, sdinformation i, fdinscom f where p.claimsNo=? and p.orderSn=i.orderSn and f.SupplierCode = i.insuranceCompany", claimsNo).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			email = dt.getString(0, "EMail");
			policyNo = dt.getString(0, "policyNo");
			insureName = dt.getString(0, "insureName");
			claimsItemsName = dt.getString(0, "claimsItemsName");
			bankName = dt.getString(0, "bankName");
			bankUserName = dt.getString(0, "bankUserName");
			caseDesc = dt.getString(0, "caseDesc");
		}
		if (StringUtil.isEmpty(email)) {
			Response.setLogInfo(0, "保险公司未设置理赔邮箱，请在产品中心的索赔项目管理菜单设置！");
			return;
		}
		// 查询理赔资料
		dt = new QueryBuilder("select d.claimsDataName, f.filePath, f.remark1 from customclaimsdatainfo d, claimsfile f where d.id = f.claimsDataId and d.claimsNo=? and f.filePath is not null and f.filePath!='' ", claimsNo).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			try {
				// 压缩的文件
				String fileName = policyNo+"_"+insureName+"_"+claimsItemsName+".zip";
		    	String zipFilePath = Config.getValue("newPolicyPath")+"/EPolicy/CustomClaimsData/"+fileName;   	
		    	File zipfile=new File(zipFilePath);
		    	int rowCount = dt.getRowCount();
		    	int i = 0;
		    	byte[] buf=new byte[1024];
	            ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipfile));
	            File file;
		    	for (; i < rowCount; i++) {
		    		if (StringUtil.isNotEmpty(dt.getString(i, "remark1"))) {
		    			file = new File(dt.getString(i, "remark1"));
		    		} else {
		    			file = new File(dt.getString(i, "filePath"));
		    		}
		    		FileInputStream in = new FileInputStream(file);
		            out.putNextEntry(new ZipEntry(System.currentTimeMillis()+file.getName()));
		            int len;
		            while((len = in.read(buf)) > 0){
		                out.write(buf,0,len);
		            }
		            out.closeEntry();
		            in.close();
		    	}
		    	out.close();
		    	String mailText = "你好：<br>请协助处理，如有问题请及时联系我，邮箱：lp@kaixinbao.com，谢谢！<br>开户行："+bankName+"，持有人姓名："+bankUserName+"<br>理赔事由:"+caseDesc+"<br>";
		    	
		    	boolean flag = true;
		    	zipfile=new File(zipFilePath);
		    	double resourcesize = zipfile.length() / 1024.0;
		    	if (resourcesize > 1000 && (resourcesize / 1024.0) > 10) {
		    		mailText += ("理赔资料：<a href='"+zipFilePath.replace(Config.getValue("newPolicyPath"), Config.getFrontServerContextPath())+"' target='_blank'>下载</a>");
		    		ActionUtil.sendGeneralMail(email, policyNo+"_"+insureName+"_"+claimsItemsName, mailText);
		    	} else {
		    		List<Map<String,Object>> attachments = new ArrayList<Map<String,Object>>();
		    		Map<String,Object> attach = new HashMap<String,Object>();
		    		attach.put("name", fileName);
		    		attach.put("path", zipFilePath);
		    		attachments.add(attach);
		    		flag = ActionUtil.sendGeneralMail(email, policyNo+"_"+insureName+"_"+claimsItemsName, mailText, attachments);
		    	}
		    	 
		    	if (flag) {
		    		Response.setLogInfo(1, "发送成功！");
		    	} else {
		    		Response.setLogInfo(0, "发送失败！");
		    	}
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
				Response.setLogInfo(0, "压缩理赔资料异常！"+e.getMessage());
				
			}
			
		} else {
			Response.setLogInfo(0, "无要发送的理赔资料！");
			return;
		}
	}
	
	/**
	 * 查询保险公司理赔进度
	 */
	@SuppressWarnings("unchecked")
	public void queryClaimsInfo() {
		String id = Request.getString("id");
		PaymentClaimsInfoSchema schema = new PaymentClaimsInfoSchema();
		schema.setid(id);
		CompanyClaimsInfoSchema comSchema = new CompanyClaimsInfoSchema();
		if (schema.fill()) {
			comSchema.setclaimsNo(schema.getclaimsNo());
			if (StringUtil.isEmpty(schema.getnotificationNo())) {
				Response.setStatus(0);
				Response.setMessage("该理赔未向保险公司申请，不能查询保险公司理赔进度!");
				return;
			}
			
			String ProductId=schema.getproductId().substring(0, 4);
			if("2049".equals(ProductId)){
				AnLianWebService(schema,comSchema);//安联查询
			}else if("2005".equals(ProductId)||"2101".equals(ProductId)){
				RenBaoWebService(schema);//人保查询
			}
		}else{
			Response.setStatus(0);
			Response.setMessage("理赔申请信息未查询到!");
		}
	}
	
	/**
	 * 安联理赔进度查询
	 */
	@SuppressWarnings("unchecked")
	public void AnLianWebService(PaymentClaimsInfoSchema schema,CompanyClaimsInfoSchema comSchema){
		
	String msg = "";
	
	String returnXml = queryXml2049(schema);
	if (StringUtil.isEmpty(returnXml)) {
		Response.setStatus(0);
		Response.setMessage("保险公司理赔查询失败，返回报文为空!");
		return;
	}
	// 解析返回报文
	try {
		Document doc = DocumentHelper.parseText(returnXml);
		//获取根元素
		Element Packet = doc.getRootElement();
		//获取Head元素
		Element Head = Packet.element("Head");
		
		String responseCode = Head.elementText("ResponseCode");
		// 失败
		if ("0".equals(responseCode)) {
			Response.setStatus(0);
			Response.setMessage("保险公司理赔申请失败，返回错误信息："+Head.elementText("ErrorMessage")+" 错误码："+Head.elementText("ErrorCode"));
			return;
			
			// 成功
		} else {
			// 取得理赔状态
			Mapx<String, String> map = new QueryBuilder("select codeValue, codeName from dictionary where codeType='claimStatus' and insuranceCode='2049'").executeDataTable().toMapx(0, 1);
			Element claim = Packet.element("Body").element("Claim");
			Element claimList = claim.element("ClaimList");
			List<Element> claimInfos = claimList.elements("ClaimInfo");
			Date date = new Date();
			Element baseinfo;
			Element claimStatusInfo;
			Element payInfoList;
			List<Element> payInfos; 
			CompanyClaimsPayInfoSchema paySchema;
			for (Element claimInfo : claimInfos) {
				baseinfo =claimInfo.element("Baseinfo");
				claimStatusInfo = claimInfo.element("ClaimStatusInfoList").element("ClaimStatusInfo");
				
				if (comSchema.fill()) {
					comSchema.setclaimStatus(claimStatusInfo.elementText("ClaimStatus"));
					comSchema.setclaimDescribe(claimStatusInfo.elementText("ClaimDescribe"));
					comSchema.setmodifyDate(date);
					comSchema.update();
				} else {
					comSchema = new CompanyClaimsInfoSchema();
					comSchema.setclaimsNo(schema.getclaimsNo());
					comSchema.setpolicyNo(claim.elementText("PolicyNo"));
					comSchema.setnotificationNo(baseinfo.elementText("NotificationNo"));
					comSchema.setclaimRef(baseinfo.elementText("ClaimRef"));
					comSchema.setclaimStatus(claimStatusInfo.elementText("ClaimStatus"));
					comSchema.setclaimDescribe(claimStatusInfo.elementText("ClaimDescribe"));
					comSchema.setcreateDate(date);
					comSchema.insert();
				}
				
				msg +=("保险公司理赔状态："+map.getString(comSchema.getclaimStatus()) + "<br>状态描述："+comSchema.getclaimDescribe());
				
				
				if ("05".equals(comSchema.getclaimStatus())) {
					payInfoList = claimInfo.element("PayInfoList");
					if (payInfoList != null && payInfoList.hasContent()) {
						payInfos = payInfoList.elements("PayInfo");
						new QueryBuilder("delete from CompanyClaimsPayInfo where notificationNo = ?", comSchema.getnotificationNo()).executeNoQuery();
						for (Element payInfo: payInfos) {
							
							paySchema = new CompanyClaimsPayInfoSchema();
							paySchema.setid(NoUtil.getMaxNo("ComClaimsPayInfoID"));
							paySchema.setnotificationNo(comSchema.getnotificationNo());
							paySchema.setclaimRef(comSchema.getclaimRef());
							paySchema.setpayItem(payInfo.elementText("PayItem"));
							paySchema.setpayAmount(payInfo.elementText("PayAmount"));
							paySchema.setpayAccountName(payInfo.elementText("PayAccountName"));
							msg += ("<br>赔付保障项目："+paySchema.getpayItem()+"、赔付金额："+paySchema.getpayAmount()+"、支付账户名称："+paySchema.getpayAccountName());
							if (payInfo.element("PayTime") != null) {
								paySchema.setpayTime(payInfo.elementText("PayTime"));
								msg += ("、支付日期："+paySchema.getpayTime());
							}
							paySchema.setcreateDate(date);
							paySchema.insert();
						}
					}
					
				}
			}
			
			Response.setStatus(1);
			Response.setMessage(msg);
			return;
		}
		
		
	} catch (DocumentException e) {
		logger.error("保险公司理赔查询返回报文解析失败！理赔单号（"+schema.getclaimsNo()+"）,返回报文："+returnXml + e.getMessage(), e);
		Response.setStatus(0);
		Response.setMessage("保险公司理赔查询返回报文解析失败！");
		return;
	}
	

	}


	/**
	 * RenBaoWebService:(人保理赔进度查询). <br/>
	 * TODO(暂时仅支持查询状态，后续可能增加对接金额。可参照AnLianWebService).<br/>
	 * @author "guanyulong"
	 * @param schema 理赔信息
	 * @param comSchema 理赔进度信息
	 */
	public void RenBaoWebService(PaymentClaimsInfoSchema schema ){
		
	String msg = "";
	String returnXml = queryXml2005(schema);
	if (StringUtil.isEmpty(returnXml)) {
		Response.setStatus(0);
		Response.setMessage("保险公司理赔查询失败，返回报文为空!");
		return;
	}
	// 解析返回报文
	try {
		Document doc = DocumentHelper.parseText(returnXml);
		//获取根元素
		Element Packet = doc.getRootElement();
		//获取Head元素
		Element Head = Packet.element("responsehead");
		
		String errorCode = Head.elementText("errorCode");
		String ErrorMessage= Head.elementText("errorMessage");
		if (!"00".equals(errorCode)) {// 失败
			Response.setStatus(0);
			Response.setMessage("保险公司理赔申请失败，返回错误信息："+ErrorMessage+" 错误码："+errorCode);
			return;
		} else {// 成功
			// 取得理赔状态
			Mapx<String, String> map = new QueryBuilder("select codeValue, codeName from dictionary where codeType='claimStatus' and insuranceCode='2049'").executeDataTable().toMapx(0, 1);
            String caseState = Packet.element("responseInfo").elementText("caseState");
			caseState="0"+caseState; 
			msg +=("保险公司理赔状态："+map.getString(caseState)) ;
			Response.setStatus(1);
			Response.setMessage(msg);
			return;
		}
		
		
	} catch (DocumentException e) {
		logger.error("保险公司理赔查询返回报文解析失败！理赔单号（"+schema.getclaimsNo()+"）,返回报文："+returnXml + e.getMessage(), e);
		Response.setStatus(0);
		Response.setMessage("保险公司理赔查询返回报文解析失败！");
		return;
	}
	

	}
	
	public void condense() {
		DataTable dt = Request.getDataTable("dt");
		String fileName = Request.getString("fileName");
		
		if (dt != null && dt.getRowCount() > 0) {
			try {
	        	int length = dt.getRowCount();
	        	fileName = fileName+".rar";
	        	String zipFilePath = Config.getValue("newPolicyPath")+"/EPolicy/CustomClaimsData/"+fileName;   	
	        	File zipfile=new File(zipFilePath);
	        	
	        	int i = 0;
	        	byte[] buf=new byte[1024];
	            ZipOutputStream outp=new ZipOutputStream(new FileOutputStream(zipfile));
	            File file;
	        	for (; i < length; i++) {
	        		file = new File(dt.getString(i, "downPath"));
	        		FileInputStream in = new FileInputStream(file);
	        		outp.putNextEntry(new ZipEntry(System.currentTimeMillis()+"_"+file.getName()));
	                int len;
	                while((len = in.read(buf)) > 0){
	                	outp.write(buf,0,len);
	                }
	                outp.closeEntry();
	                in.close();
	        	}
	        	outp.close();
	        	Response.setStatus(1);
				Response.setMessage(zipFilePath);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				Response.setStatus(0);
				Response.setMessage("打包失败!"+e.getMessage());
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("请选择要下载的文件!");
		}

	}
	
	
	/**
	 * 向保险公司上传理赔文件
	 */
	public void sendFile() {
		DataTable dt = Request.getDataTable("dt");
		String id = Request.getString("id");
		PaymentClaimsInfoSchema schema = new PaymentClaimsInfoSchema();
		schema.setid(id);
		if (schema.fill()) {
			if (StringUtil.isEmpty(schema.getnotificationNo())) {
				Response.setStatus(0);
				Response.setMessage("保险公司开案号为空，请先开案后在上传文件!");
				return;
			}
			if (dt != null && dt.getRowCount() > 0) {
				int rowCount = dt.getRowCount();
				int i = 0;
				for (; i < rowCount; i++) {
					dt.set(i, "filePath", dt.getString(i, "downPath"));
				}
				Response.setStatus(1);
				Response.setMessage("后台上传图片中!");
				UploadThread uploadTh = new UploadThread(id, schema.getnotificationNo(), dt, Request.getClientIP(), User.getUserName());
				uploadTh.start();
				
			} else {
				Response.setStatus(0);
				Response.setMessage("请选择要上传保险公司的文件!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("未查询到理赔数据!");
		}
		
	}
	
	/**
	 * 向保险公司上传理赔所有文件
	 */
	public void sendAllFile() {
		String id = Request.getString("id");
		PaymentClaimsInfoSchema schema = new PaymentClaimsInfoSchema();
		schema.setid(id);
		if (schema.fill()) {
			if (StringUtil.isEmpty(schema.getnotificationNo())) {
				Response.setStatus(0);
				Response.setMessage("该理赔未向保险公司申请，不能上传文件!");
				return;
			}
			// 上传文件 ，后台自动上传图片中
			DataTable dt = new QueryBuilder("SELECT c.claimsDataName, if (f.remark1 is null, f.filePath, f.remark1) as filePath, f.fileSize FROM customclaimsdatainfo c, claimsfile f WHERE f.claimsDataId=c.id and c.claimsNo=? order by c.claimsDataName ", schema.getclaimsNo()).executeDataTable();
			if (dt == null || dt.getRowCount() == 0) {
				Response.setStatus(0);
				Response.setMessage("无上传文件！");
				return;
			}
			
			Response.setStatus(1);
			Response.setMessage("后台上传图片中!");
			UploadThread uploadTh = new UploadThread(id, schema.getnotificationNo(), dt, Request.getClientIP(), User.getUserName());
			uploadTh.start();
			
		} else {
			Response.setStatus(0);
			Response.setMessage("理赔申请信息未查询到!");
		}
	}
	
	
	/**
	 * 向保险公司申请理赔
	 */
	public void sendClaimsXML() {
		String id = Request.getString("id");
		PaymentClaimsInfoSchema schema = new PaymentClaimsInfoSchema();
		schema.setid(id);
		if (schema.fill()) {
			if (StringUtil.isNotEmpty(schema.getnotificationNo())) {
				Response.setStatus(0);
				Response.setMessage("该理赔已经向保险公司申请过，不能再次申请!");
				return;
			}
			
			schema.sethappenTime(Request.getString("happenTime"));
			schema.sethappenAddress(Request.getString("happenAddress"));
			schema.setkindOfLoss(Request.getString("kindOfLoss"));
			schema.setclaimReason(Request.getString("claimReason"));
			schema.setflightNo(Request.getString("flightNo"));
			schema.setflightTime(Request.getString("flightTime"));
			if (schema.update()) {
				
				// 向保险公司发送开案请求
				String returnXml = sendXml2049(schema);
				if (StringUtil.isEmpty(returnXml)) {
					Response.setStatus(0);
					Response.setMessage("保险公司理赔申请失败，返回报文为空!");
					return;
				}
				// 解析返回报文
				try {
					Document doc = DocumentHelper.parseText(returnXml);
					//获取根元素
					Element Packet = doc.getRootElement();
					//获取Head元素
					Element Head = Packet.element("Head");
					
					String responseCode = Head.elementText("ResponseCode");
					// 失败
					if ("0".equals(responseCode)) {
						Response.setStatus(0);
						Response.setMessage("保险公司理赔申请失败，返回错误信息："+Head.elementText("ErrorMessage")+" 错误码："+Head.elementText("ErrorCode"));
						return;
						
						
					} else {
						Element Body = Packet.element("Body");
						// 成功
						if ("0000".equals(Body.elementText("Result"))) {
							// 报案号
							String notificationNo = Body.elementText("NotificationNo");
							schema.setnotificationNo(notificationNo);
							schema.update();
							Response.setStatus(1);
							Response.setMessage("保险公司理赔申请成功！保险公司返回报案号："+notificationNo+"！");
							
							// 上传文件 ，后台自动上传图片中
							DataTable dt = new QueryBuilder("SELECT c.claimsDataName, if (f.remark1 is null, f.filePath, f.remark1) as filePath, f.fileSize FROM customclaimsdatainfo c, claimsfile f WHERE f.claimsDataId=c.id and c.claimsNo=? order by c.claimsDataName ", schema.getclaimsNo()).executeDataTable();
							if (dt == null || dt.getRowCount() == 0) {
								Response.setMessage(Response.getMessage()+" 无上传文件无法自动上传保险公司，请上传文件后点击保险公司上传文件按钮进行上传！");
								return;
							}
							UploadThread uploadTh = new UploadThread(id, notificationNo, dt, Request.getClientIP(), User.getUserName());
							uploadTh.start();
						} else {
							Response.setStatus(0);
							Response.setMessage("保险公司理赔申请失败，返回错误信息："+Body.elementText("Message")+" 错误码："+Body.elementText("Result"));
							return;
						}
						
					}
					
					
				} catch (DocumentException e) {
					logger.error("保险公司理赔申请返回报文解析失败！理赔单号（"+schema.getclaimsNo()+"）,返回报文："+returnXml + e.getMessage(), e);
					Response.setStatus(0);
					Response.setMessage("保险公司理赔申请返回报文解析失败！");
					return;
				}
				
			} else {
				Response.setStatus(0);
				Response.setMessage("理赔申请信息更新失败!");
				return;
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("理赔申请信息未查询到!");
		}
		
	}
	
	// 理赔资料上传
	public class UploadThread extends Thread {
		private String id;
		private String notificationNo;
		private DataTable dt;
		private String clientIP;
		private String operator;
		
		public UploadThread(String paymentClaimsInfoId, String notifNo, DataTable fileDt, String ip, String userName) {
			this.id = paymentClaimsInfoId;
			this.notificationNo = notifNo;
			this.dt = fileDt;
			this.clientIP = ip;
			this.operator = userName;
		}
		
		@SuppressWarnings("unchecked")
		public void run() {
			PaymentClaimsInfoSchema schema = new PaymentClaimsInfoSchema();
			schema.setid(id);
			if (schema.fill()) {
				String toMail = new QueryBuilder("select CodeValue from zdcode where CodeType='Payment.AlarmMailAddress' and ParentCode='Payment.AlarmMailAddress'").executeString();
				if (StringUtil.isEmpty(toMail)) {
					toMail = "lp@kaixinbao.com";
				}
				DataTable dtAnLian = new QueryBuilder("select CodeValue,Memo from zdcode where CodeType='AnLian.UserInfo' and ParentCode='AnLian.UserInfo'").executeDataTable();
				Map<String, String> map;
				if (dtAnLian != null && dtAnLian.getRowCount() > 0) {
					map = dtAnLian.toMapx(0, 1);
					
				} else {
					logger.warn("理赔单号：{} 上传理赔文件，zdcode表未配置AnLian.UserInfo！", schema.getclaimsNo());
					uploadMail("理赔文件上传配置错误", "zdcode表未配置AnLian.UserInfo", Config.getValue("InsureErrorMail"), schema.getclaimsNo());
					return;
				}
				String user = map.get("User");
				String password = map.get("Password");
				String claimsAdress = map.get("ClaimsAdress");
				String fileLimit = map.get("FileLimit");
				String saveAddress = Config.getValue("ClaimsSaveAddress");
				if (StringUtil.isEmpty(saveAddress)) {
					saveAddress = "/alidata/claims/";
				}
				double intFileLimit = 0.0;
				if (fileLimit.endsWith("GB")) {
					intFileLimit = Double.valueOf(fileLimit.replace("GB", ""))*1024*1024;
				}else if (fileLimit.endsWith("MB")) {
					intFileLimit = Double.valueOf(fileLimit.replace("MB", ""))*1024;
				} else if (fileLimit.endsWith("KB")) {
					intFileLimit = Double.valueOf(fileLimit.replace("KB", ""));
				}
				List<DataTable> dtList = new ArrayList<DataTable>();
				int rowCount = dt.getRowCount();
				int i = 0;
				DataTable dtTemp = new DataTable();
				double fileSizes = 0.0;
				double fileSize = 0.0;
				for (; i < rowCount; i++) {
					
					if (StringUtil.isNotEmpty(dt.getString(i, "fileSize"))) {
						if (dt.getString(i, "fileSize").endsWith("MB")) {
							fileSize = Double.valueOf(dt.getString(i, "fileSize").replace("MB", ""))*1024;
						} else if (dt.getString(i, "fileSize").endsWith("KB")) {
							fileSize = Double.valueOf(dt.getString(i, "fileSize").replace("KB", ""));
						}
						if (fileSizes + fileSize > intFileLimit) {
							dtList.add(dtTemp);
							dtTemp = new DataTable();
							fileSizes = 0.0;
						}
						fileSizes += fileSize;
					}
					
					dtTemp.insertRow(dt.get(i));
				}
				dtList.add(dtTemp);
				
				for (DataTable dt1 : dtList) {
					
					String errMsg = "";
					String files = "";
					String time = DateUtil.getCurrentTime("yyyyMMddHHmmss");
					String now = DateUtil.getCurrentDateTime();
					String transID = schema.getclaimsNo()+"-"+time;
					Document doc = DocumentHelper.createDocument();
					Element root = doc.addElement("Packet").addAttribute("type", "REQUEST").addAttribute("version", "1.0");
					Element head = root.addElement("Head");
					head.addElement("TransType").addText("C014");
					head.addElement("TransID").addText(transID);
					head.addElement("User").addText(user);
					head.addElement("Password").addText(password);
					head.addElement("TransDate").addText(now);
					Element body = root.addElement("Body");
					body.addElement("NotificationNo").addText(notificationNo);
					// 已上传过，进行补传:02
					if ("Y".equals(schema.getisUpload())) {
						body.addElement("UploadType").addText("02");
					} else {
						// 第一次上传:01
						body.addElement("UploadType").addText("01");
					}
					
					// 被保人信息
					Element insuredInfo = body.addElement("InsuredInfoList").addElement("InsuredInfo");
					insuredInfo.addElement("InsuredIdNo").addText(schema.getinsureIdentityId());
					insuredInfo.addElement("InsuredName").addText(schema.getinsureName());
					
					Element mediaInfoList = insuredInfo.addElement("MediaInfoList");
					BaseEncoding baseencoding = BaseEncoding.base64();
					byte[] bytes;
					String fileSource;
					File sourceFile;
					
					String encoded = "";
					i = 0;
					rowCount = dt1.getRowCount();
					for (; i < rowCount; i++) {
						
						fileSource = dt1.getString(i, "filePath");
						try {
							sourceFile = new File(fileSource);
							bytes = Files.toByteArray(sourceFile);
							encoded = baseencoding.encode(bytes);
							Element mediaInfo = mediaInfoList.addElement("MediaInfo");
							mediaInfo.addElement("FileName").addText(dt1.getString(i, "claimsDataName") + "_" + DateUtil.getCurrentDateTime("yyyyMMddHHmmss")+fileSource.substring(fileSource.lastIndexOf(".")));
							mediaInfo.addElement("Media").addCDATA(encoded);
							
							files += (dt1.getString(i, "claimsDataName")+"："+fileSource+"<br>");
						} catch (IOException e) {
							logger.error("向安联保险公司上传文件，文件转字节数组错误！文件路径："+fileSource + e.getMessage(), e);
							errMsg += ("向安联保险公司上传文件，文件转字节数组错误！文件路径："+fileSource+"<br>");
						}
						
					}
					
					String requestXML = doc.asXML();
					String date = DateUtil.getCurrentDate("yyyyMMdd");
					// 保存上传文件请求报文
					String path = saveAddress+"E-I/file/"+date+"/2049/";
					FileUtil.mkdir(path);
					FileUtil.writeText(path+transID+".xml", requestXML);
					
					String returnXML = sendServer(claimsAdress, requestXML);
					
					// 保存上传文件请求返回报文
					path = saveAddress+"E-IReturn/file/"+date+"/2049/";
					FileUtil.mkdir(path);
					FileUtil.writeText(path+transID+".xml", returnXML);
					
					if (StringUtil.isEmpty(returnXML)) {
						errMsg += ("向安联保险公司上传文件，返回报文为空!<br>");
					} else {
						try {
							Document returnDoc = DocumentHelper.parseText(returnXML);
							//获取根元素
							Element Packet = returnDoc.getRootElement();
							//获取Head元素
							Element Head = Packet.element("Head");
							String responseCode = Head.elementText("ResponseCode");
							// 上传失败
							if ("0".equals(responseCode)) {
								errMsg += ("向安联保险公司上传文件失败！错误信息："+Head.elementText("ErrorMessage")+"<br>");
							} else {
								Element Body = Packet.element("Body");
								if (!"Y".equals(schema.getisUpload())) {
									schema.setisUpload("Y");
									schema.update();
								}
								String fileCount = Body.elementText("FileCount");
								
								uploadLog("success", "理赔文件上传保险公司成功"+fileCount+"个文件！", clientIP, operator, schema.getclaimsNo());
								
								// 发送上传成功邮件
								uploadMail("理赔文件上传通知", ("理赔单号："+schema.getclaimsNo()+" 理赔文件上传保险公司成功"+fileCount+"个文件！<br>操作用户："+operator +"、操作时间："+ now+"<br>上传文件：<br>"+files), toMail, schema.getclaimsNo());
							}
							
							
						} catch (Exception e) {
							logger.error("向安联保险公司上传文件，解析返回报文失败！返回报文："+returnXML+",理赔单号："+schema.getclaimsNo() + e.getMessage(), e);
							errMsg += ("向安联保险公司上传文件，解析返回报文失败！返回报文："+returnXML+"<br>");
						}
						
					}
					
					// 有错信息，发送告警邮件
					if (StringUtil.isNotEmpty(errMsg)) {
						uploadLog("fail", errMsg, clientIP, operator, schema.getclaimsNo());
						
						uploadMail("理赔文件上传通知", ("理赔单号："+schema.getclaimsNo()+" 理赔文件上传保险公司失败！<br>操作用户："+operator +"、操作时间：" + now + "<br>"+errMsg+"<br>上传文件：<br>"+files), toMail, schema.getclaimsNo());
					}
				}
				
			}
		}
	}
	
	public static void main(String[] args) throws DocumentException {
		String returnXml = FileUtil.readText("C:\\Users\\wangcaiyun\\Desktop\\LP201707130000324-20170714160047.xml");
		Document doc = DocumentHelper.parseText(returnXml);
		//获取根元素
		Element Packet = doc.getRootElement();
		//获取Head元素
		Element Body = Packet.element("Body");
	
		Element MediaInfoList = Body.element("InsuredInfoList").element("InsuredInfo").element("MediaInfoList");
		List<Element> MediaInfos = MediaInfoList.elements();
		BaseEncoding baseencoding = BaseEncoding.base64();
		int i = 1;
		for (Element ele : MediaInfos) {
			String encoded = ele.elementText("Media");
			String fileName = ele.elementText("FileName");
			byte[] bytes = baseencoding.decode(encoded);
			FileUtil.writeByte("C:\\Users\\wangcaiyun\\Desktop\\"+i+fileName, bytes);
			i++;
		}
		
	}
	
	private void uploadLog(String type, String msg, String clientIP, String operator, String claimsNo) {
		try {
			UserLog.log("ClaimsFileUpload", type, msg,
					clientIP, operator, claimsNo);
			
		} catch(Exception e) {
			logger.info("ClaimsFileUpload：记录用户上传理赔文件失败！理赔单号：{}，用户：{}", claimsNo, operator);
			logger.error(e.getMessage(), e);
		}
	}
	
	private void uploadMail(String subject,String content, String toMail, String claimsNo) {
		try {
			ActionUtil.sendGeneralMail(toMail, subject, content);
		} catch (Exception e) {
			logger.error("理赔单号："+claimsNo+" "+subject+"，发送理赔通知邮件失败！" + e.getMessage(), e);
		}
	}
	
	/**
	 * RenBaoWebService:(人保理赔查询拼装报文与保存报文部分). <br/>
	 * @author "guanyulong"
	 * @param schema 理赔信息
	 * @param comSchema 理赔进度信息
	 */
	private String queryXml2005(PaymentClaimsInfoSchema schema) {///////////////i am do testing
		String time = DateUtil.getCurrentTime("yyyyMMddHHmmss");
		String date = DateUtil.getCurrentDate("yyyyMMdd");
		String dateTime=new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date() );
		String user = new QueryBuilder("select Memo from zdcode where CodeType='RenBao.UserInfo' and CodeValue='User'").executeString();
		String password = new QueryBuilder("select Memo from zdcode where CodeType='RenBao.UserInfo' and CodeValue='Password'").executeString();
		String claimsAdress = new QueryBuilder("select Memo from zdcode where CodeType='RenBao.UserInfo' and CodeValue='ClaimsAdress'").executeString();
		String saveAddress = Config.getValue("ClaimsSaveAddress");
		if (StringUtil.isEmpty(saveAddress)) {
			saveAddress = "/alidata/claims/";
		}
		// 拼装发送报文
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("gb2312");
		Element root = doc.addElement("queryClaimRequest");
		Element head = root.addElement("requesthead");
		head.addElement("user").addText(user);//"发送者用户代码"
		head.addElement("request_type").addText("服务编码");//服务编码
		//head.addElement("requestType").addText("QC");//请求类型
		head.addElement("password").addText(password);//"发送者用户密码"+
		head.addElement("server_version").addText("版本号");
		head.addElement("uuid").addText("唯一标示");
		head.addElement("sender").addText("使用方系统编号");
		head.addElement("flowintime").addText(dateTime);//"当前时间，精确到毫秒"+
		Element requestInfo = root.addElement("requestInfo");
		requestInfo.addElement("plateformCode").addText("平台项目标识");
		requestInfo.addElement("md5Value").addText("Md5加密串");//此节点的值由uuid节点的值拼接policyNo节点的值进行MD5加密获得////////////////
		requestInfo.addElement("policyNo").addText(schema.getpolicyNo());//"保单号"
		requestInfo.addElement("insuredName").addText(schema.getinsureName());//被保人姓名 //非必传
		requestInfo.addElement("identifyNumber").addText(schema.getinsureIdentityId());//被保险人证件号码 //非必传
		
		String requestXML = doc.asXML();
		requestXML = requestXML.replace("encoding=\"" + "gb2312" + "\"" ,"encoding=\"" + "gb2312" + "\""+" "+"standalone=\"" + "yes" + "\"");

		// 保存开案请求报文
		String transID = schema.getclaimsNo()+"-"+time;
		String path = saveAddress+"E-I/query/"+date+"/2005/";
		FileUtil.mkdir(path);
		FileUtil.writeText(path+transID+".xml", requestXML);

		String returnXML = renBaoSendServer(claimsAdress, requestXML);
		
		// 保存开案请求返回报文
		path = saveAddress+"E-IReturn/query/"+date+"/2005/";
		FileUtil.mkdir(path);
		FileUtil.writeText(path+transID+".xml", returnXML);
		return returnXML;
				
	}
	
	/**
	 * 安联理赔进度查询
	 * @param schema
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String queryXml2049(PaymentClaimsInfoSchema schema) {
		String time = DateUtil.getCurrentTime("yyyyMMddHHmmss");
		String date = DateUtil.getCurrentDate("yyyyMMdd");
		DataTable dtAnLian = new QueryBuilder("select CodeValue,Memo from zdcode where CodeType='AnLian.UserInfo' and ParentCode='AnLian.UserInfo'").executeDataTable();
		Map<String, String> map;
		if (dtAnLian != null && dtAnLian.getRowCount() > 0) {
			map = dtAnLian.toMapx(0, 1);
			
		} else {
			logger.warn("理赔单号：{} 查询理赔进度，zdcode表未配置AnLian.UserInfo！", schema.getclaimsNo());
			return "";
		}
		String user = map.get("User");
		String password = map.get("Password");
		String claimsAdress = map.get("ClaimsAdress");
		String saveAddress = Config.getValue("ClaimsSaveAddress");
		if (StringUtil.isEmpty(saveAddress)) {
			saveAddress = "/alidata/claims/";
		}
		
		// 拼装发送报文
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Packet").addAttribute("type", "REQUEST").addAttribute("version", "1.0");
		Element head = root.addElement("Head");
		head.addElement("TransType").addText("Q001");
		String transID = schema.getclaimsNo()+"-"+time;
		head.addElement("TransID").addText(transID);
		head.addElement("User").addText(user);
		head.addElement("Password").addText(password);
		head.addElement("TransDate").addText(DateUtil.getCurrentDateTime());
		Element body = root.addElement("Body");
		Element policyInformation = body.addElement("PolicyInformation");
		policyInformation.addElement("PolicyNo").addText(schema.getpolicyNo());
		policyInformation.addElement("InsuredName").addText(schema.getinsureName());
		policyInformation.addElement("InsuredIdNo").addText(schema.getinsureIdentityId());
		policyInformation.addElement("notificationNo").addText(schema.getnotificationNo());
		
		String requestXML = doc.asXML();
		
		// 保存开案请求报文
		String path = saveAddress+"E-I/query/"+date+"/2049/";
		FileUtil.mkdir(path);
		FileUtil.writeText(path+transID+".xml", requestXML);

		String returnXML = sendServer(claimsAdress, requestXML);
		
		// 保存开案请求返回报文
		path = saveAddress+"E-IReturn/query/"+date+"/2049/";
		FileUtil.mkdir(path);
		FileUtil.writeText(path+transID+".xml", returnXML);
		return returnXML;
				
	}
	/**
	 * 安联申请理赔
	 * @param schema
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String sendXml2049(PaymentClaimsInfoSchema schema) {
		String time = DateUtil.getCurrentTime("yyyyMMddHHmmss");
		String date = DateUtil.getCurrentDate("yyyyMMdd");
		DataTable dtAnLian = new QueryBuilder("select CodeValue,Memo from zdcode where CodeType='AnLian.UserInfo' and ParentCode='AnLian.UserInfo'").executeDataTable();
		Map<String, String> map;
		if (dtAnLian != null && dtAnLian.getRowCount() > 0) {
			map = dtAnLian.toMapx(0, 1);
			
		} else {
			logger.warn("理赔单号：{} 申请理赔，zdcode表未配置AnLian.UserInfo！", schema.getclaimsNo());
			return "";
		}
		String user = map.get("User");
		String password = map.get("Password");
		String claimsAdress = map.get("ClaimsAdress");
		String saveAddress = Config.getValue("ClaimsSaveAddress");
		if (StringUtil.isEmpty(saveAddress)) {
			saveAddress = "/alidata/claims/";
		}
		// 拼装发送报文
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Packet").addAttribute("type", "REQUEST").addAttribute("version", "1.0");
		Element head = root.addElement("Head");
		head.addElement("TransType").addText("C011");
		String transID = schema.getclaimsNo()+"-"+time;
		head.addElement("TransID").addText(transID);
		head.addElement("User").addText(user);
		head.addElement("Password").addText(password);
		head.addElement("TransDate").addText(DateUtil.getCurrentDateTime());
		Element body = root.addElement("Body");
		Element baseinfo = body.addElement("Baseinfo");
		baseinfo.addElement("CaseResource").addText("AG");
		baseinfo.addElement("PolicyNo").addText(schema.getpolicyNo());
		baseinfo.addElement("KindOfLoss").addText(schema.getkindOfLoss());
		baseinfo.addElement("LossDetail").addText(schema.getcaseDesc());
		baseinfo.addElement("LossTime").addText(schema.gethappenTime());
		baseinfo.addElement("AccidentAddress").addText(schema.gethappenAddress());
		baseinfo.addElement("NotificationTime").addText(DateUtil.toDateTimeString(schema.getapplicationDate()));
		baseinfo.addElement("NotificationName").addText(schema.getcontactName());
		baseinfo.addElement("NotificationMobile").addText(schema.getcontactMobile());
		baseinfo.addElement("NotificationMail").addText(schema.getcontactMail());
		// baseinfo.addElement("NotificationPhoneNo").addText(schema.getcontactMobile());
		baseinfo.addElement("AgencyCode").addText(user);
		
		Element customerInfo = body.addElement("CustomerInfoList").addElement("CustomerInfo");
		// 被保人节点信息
		Element insuredInfo = customerInfo.addElement("InsuredInfo");
		insuredInfo.addElement("InsuredIdNo").addText(schema.getinsureIdentityId());
		insuredInfo.addElement("InsuredName").addText(schema.getinsureName());
		customerInfo.addElement("PayeeInfo");
		
		// 航班延误
		if ("KOL170".equals(schema.getkindOfLoss())) {
			Element flightDelayInfo = body.addElement("FlightDelayInfo");
			flightDelayInfo.addElement("ClaimReason").addText(schema.getclaimReason());
			flightDelayInfo.addElement("ClaimReasonDetail").addText(schema.getcaseDesc());
			// 计划起飞时间
			flightDelayInfo.addElement("DeptDatePlan").addText(schema.getflightTime());
			// 航班号
			flightDelayInfo.addElement("FlightNo").addText(schema.getflightNo());

		} else if ("KOL176".equals(schema.getkindOfLoss())) {
			// 行李延误
			Element luggageDelayInfo = body.addElement("LuggageDelayInfo");
			luggageDelayInfo.addElement("ClaimReasonDetail").addText(schema.getcaseDesc());
			
		} else if ("KOL172".equals(schema.getkindOfLoss())) {
			// 门诊&住院
			Element medicalInfo = body.addElement("MedicalInfo");
			medicalInfo.addElement("ClaimReasonDetail").addText(schema.getcaseDesc());
			
		} else if ("KOL175".equals(schema.getkindOfLoss())) {
			// 行程变更
			Element travelChangeInfo = body.addElement("TravelChangeInfo");
			travelChangeInfo.addElement("ClaimReasonDetail").addText(schema.getcaseDesc());
			
		} else if ("KOL177".equals(schema.getkindOfLoss())) {
			// 家财与财务丢失
			Element propertyInfo = body.addElement("PropertyInfo");
			propertyInfo.addElement("ClaimReasonDetail").addText(schema.getcaseDesc());
			
		} else if ("KOL178".equals(schema.getkindOfLoss())) {
			// 其它案件
			Element otherInfo = body.addElement("OtherInfo");
			otherInfo.addElement("ClaimReasonDetail").addText(schema.getcaseDesc());
			
		} else if ("KOL174".equals(schema.getkindOfLoss())) {
			// 紧急救援
			Element rescueInfo = body.addElement("RescueInfo");
			rescueInfo.addElement("ClaimReasonDetail").addText(schema.getcaseDesc());
		}
		
		String requestXML = doc.asXML();
		
		// 保存开案请求报文
		String path = saveAddress+"E-I/apply/"+date+"/2049/";
		FileUtil.mkdir(path);
		FileUtil.writeText(path+transID+".xml", requestXML);

		String returnXML = sendServer(claimsAdress, requestXML);
		
		// 保存开案请求返回报文
		path = saveAddress+"E-IReturn/apply/"+date+"/2049/";
		FileUtil.mkdir(path);
		FileUtil.writeText(path+transID+".xml", returnXML);
		return returnXML;
	}

	private void trustAllHttpsCertificates() throws Exception {  
        
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("/anlian/allianz.jks"), "123456".toCharArray());
        TrustManagerFactory tmf =
        TrustManagerFactory.getInstance("SunX509", "SunJSSE");
        tmf.init(ks);
        javax.net.ssl.TrustManager[] trustAllCerts = tmf.getTrustManagers(); 
        
        javax.net.ssl.TrustManager tm = new SSLkeyShielding();  
        trustAllCerts[0] = tm;  
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext  
                .getInstance("SSL");  
        sc.init(null, trustAllCerts, null);  
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc  
                .getSocketFactory());  
    }

	/**
	 * 安联交互
	 * @param claimsAdress 安联地址
	 * @param requestXML 请求报文
	 * @return 返回报文
	 */
	public String sendServer(String claimsAdress, String requestXML){
		OutputStream out = null;
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(claimsAdress);
			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {	
					return true;
				}
			};
			
			trustAllHttpsCertificates();
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setAllowUserInteraction(true);
			conn.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			conn.setRequestProperty("Content-Type", "text/xml");
			byte[] xmlData = requestXML.getBytes("UTF-8");
			conn.setRequestProperty("Content-length", String.valueOf(xmlData.length));
			
			conn.connect();
			out  = conn.getOutputStream();
			out.write(xmlData);
			InputStream in  = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			
			String str = null ;
			while((str = reader.readLine()) != null){
				sb.append(str);
			}
		} catch (Exception e) {
			logger.error("向安联保险公司请求错误！安联地址："+claimsAdress+",请求报文："+requestXML + e.getMessage(), e);
		}finally{
			try {
				out.flush();
				out.close();
			} catch (Exception e2) {
				logger.error(e2.getMessage(), e2);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * renBaoSendServer:(人保交互，调用人保接口部分). <br/>
	 * @author "guanyulong"
	 * @param claimsAdress 人保地址
	 * @param requestXML 请求报文
	 * @return 返回报文
	 */
	public String renBaoSendServer(String claimsAdress, String requestXML){
        // 远程调用路径
/*        String URL = "http://test.mypicc.com.cn/ecooperation/webservice/QueryClaimWebService";*/
        String result = "";
        String datas=requestXML;
        String interfaceNo="001018";//固定值
        Service service = new Service();
        Call call;
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(claimsAdress); 
            // 调用的方法名
            call.setOperationName("QueryClaimWebService"); 
            
            // 设置参数名
            call.addParameter("interfaceNo",   // 参数名
                    XMLType.XSD_STRING, // 参数类型:String
                    ParameterMode.IN);  // 参数模式：'IN' or 'OUT'
            call.addParameter("datas", XMLType.XSD_STRING, ParameterMode.IN); 
            // 设置返回值类型
            call.setReturnType(XMLType.XSD_STRING); // 返回值类型：String

            result = (String) call.invoke(new Object[] { interfaceNo,datas });// 远程调用,返回信息String格式
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }
        
        return result;
	}
	
	public void saveCliamsInfo() {
		String id = Request.getString("id");
		String oldStatus = "";
		String newStatus = Request.getString("status");
		String oldReceiveDate = "";
		PaymentClaimsInfoSchema schema = new PaymentClaimsInfoSchema();
		schema.setid(id);
		if (schema.fill()) {
			oldStatus = schema.getstatus();
			schema.setinsureName(Request.getString("insureName"));
			schema.setinsureIdentityId(Request.getString("insureIdentityId"));
			schema.setinsureRelation(Request.getString("insureRelation"));
			schema.setcontactName(Request.getString("contactName"));
			schema.setcontactMobile(Request.getString("contactMobile"));
			schema.setcontactMail(Request.getString("contactMail"));
			schema.setstatus(Request.getString("status"));
			schema.setcaseType(Request.getString("caseType"));
			schema.setcaseDesc(Request.getString("caseDesc"));
			oldReceiveDate = schema.getreceiveDate();
			schema.setreceiveDate(Request.getString("receiveDate"));
			schema.setsendAddress(Request.getString("sendAddress"));
			schema.setgiroDate(Request.getString("giroDate"));
			schema.setclaimsMoney(Request.getString("claimsMoney"));
            schema.setcycle(Request.getString("cycle"));
            schema.setisShowFlag(Request.getString("isShowFlag"));
			if ("06".equals(schema.getstatus())) {
				schema.setreturnDesc(null);
			} else if ("03".equals(schema.getstatus()) || "07".equals(schema.getstatus())) {
				schema.setreturnDesc(Request.getString("returnDesc"));
			}
			schema.setmodifyDate(new Date());
			schema.setmodifyUser(User.getUserName());
			if (schema.update()) {
				Response.setStatus(1);
				Response.setMessage("理赔申请信息更新成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("理赔申请信息更新失败!");
				return;
			}
			
			// 取得用户微信ID
			String openId = new QueryBuilder("SELECT openId FROM wxbind WHERE memberId=?", schema.getmemberId()).executeString();
			// 保险类型
			String productType = "";
			String token = "";
			Map<String, Object> param = new HashMap<String, Object>();
			
			// 纸单理赔 签收时间发生变化,发送短信、微信提醒客户
			if ("02".equals(schema.getcaseType()) && StringUtil.isNotEmpty(schema.getreceiveDate()) && !schema.getreceiveDate().equals(oldReceiveDate)) {
				// 发送短信参数
				if (ActionUtil.sendSms("wj00307", schema.getcontactMobile()
						, schema.getclaimsItemsName() + ";" + "已于"+schema.getreceiveDate()+"签收" + ";" + schema.getclaimsNo())) {
					Response.setMessage(Response.getMessage()+"发送已签收短信成功！");
				} else {
					Response.setMessage(Response.getMessage()+"发送已签收短信失败！");
				}
				// 用户微信ID不为空情况的处理
				if (StringUtil.isNotEmpty(openId)) {
					// 取得发送模板ID
					String template_id = new QueryBuilder("SELECT Memo FROM zdcode WHERE CodeType='ClaimsStatus' and ParentCode='ClaimsStatus' and CodeValue=?", schema.getstatus()).executeString();
					if (StringUtil.isNotEmpty(template_id)) {
						// 取得token
						token = WeiXinCommon.ajaxtoken();
						if (StringUtil.isNotEmpty(token)) {
							param.put("touser", openId);
							param.put("template_id", template_id);
							param.put("url", Config.getValue("WapServerContext")+"/member/claims/track.shtml");
							param.put("topcolor", "#000000");
							productType = new QueryBuilder("SELECT ProductGenera FROM sdproduct WHERE ProductID=?", schema.getproductId()).executeString();
							
							// 微信推送 拼装参数
							Map<String, Object> dataParam = new HashMap<String, Object>();
							dataParam.put("first", getWXDataMap("value", "您好，您的"+schema.getclaimsItemsName()+"理赔申请发生状态变更。"));
							dataParam.put("name", getWXDataMap("value", productType));
							dataParam.put("type", getWXDataMap("value", "单号"));
							dataParam.put("number", getWXDataMap("value", schema.getclaimsNo()));
							dataParam.put("status", getWXDataMap("value", "理赔资料已于"+schema.getreceiveDate()+"签收"));
							dataParam.put("remark", getWXDataMap("value", "备注：一般2-3个工作日将有理赔反馈，请随时登陆开心保，关注理赔进度。如有疑问，请拨打4009789789咨询，感谢您的使用。"));
							param.put("data", dataParam);
							if (WeiXinCommon.ajaxSendInfoToUser(token, openId, param)) {
								Response.setMessage(Response.getMessage()+"发送审核微信成功！");
							} else {
								Response.setMessage(Response.getMessage()+"发送审核微信失败！");
							}
						}
					}
				}
			}
			
			// 理赔状态发生变化时发送短信、微信
			if (!oldStatus.equals(newStatus)) {
				// 用户微信ID不为空情况的处理
				if (StringUtil.isEmpty(token) && StringUtil.isNotEmpty(openId)) {
					// 取得发送模板ID
					String template_id = new QueryBuilder("SELECT Memo FROM zdcode WHERE CodeType='ClaimsStatus' and ParentCode='ClaimsStatus' and CodeValue=?", schema.getstatus()).executeString();
					if (StringUtil.isNotEmpty(template_id)) {
						// 取得token
						token = WeiXinCommon.ajaxtoken();
						if (StringUtil.isNotEmpty(token)) {
							param.put("touser", openId);
							param.put("template_id", template_id);
							param.put("url", Config.getValue("WapServerContext")+"/member/claims/track.shtml");
							param.put("topcolor", "#000000");
							productType = new QueryBuilder("SELECT ProductGenera FROM sdproduct WHERE ProductID=?", schema.getproductId()).executeString();
						}
					}
				}
				
				String applicationDate = DateUtil.toString(schema.getapplicationDate(), "yyyy-MM-dd");
				// 发送短信参数
				// 初审
				if ("02".equals(schema.getstatus())) {
					// 短信提示
					if (ActionUtil.sendSms("wj00301", schema.getcontactMobile()
							, schema.getclaimsItemsName() + ";" + schema.getclaimsNo())) {
						Response.setMessage(Response.getMessage()+"发送审核短信成功！");
					} else {
						Response.setMessage(Response.getMessage()+"发送审核短信失败！");
					}
					if (!param.isEmpty()) {
						// 微信推送 拼装参数
						Map<String, Object> dataParam = new HashMap<String, Object>();
						dataParam.put("first", getWXDataMap("value", "您好，您的"+schema.getclaimsItemsName()+"理赔申请发生状态变更。"));
						dataParam.put("name", getWXDataMap("value", productType));
						dataParam.put("type", getWXDataMap("value", "单号"));
						dataParam.put("number", getWXDataMap("value", schema.getclaimsNo()));
						dataParam.put("status", getWXDataMap("value", "已接收申请，审核中。"));
						dataParam.put("remark", getWXDataMap("value", "备注：一般2-3个工作日将有理赔反馈，如有问题，将会及时联系您补充材料，请随时登陆开心保，关注理赔进度。如有疑问，请拨打4009789789咨询，感谢您的使用。"));
						param.put("data", dataParam);
						if (WeiXinCommon.ajaxSendInfoToUser(token, openId, param)) {
							Response.setMessage(Response.getMessage()+"发送审核微信成功！");
						} else {
							Response.setMessage(Response.getMessage()+"发送审核微信失败！");
						}
					}
					
					// 待邮寄
				} else if ("04".equals(schema.getstatus())) {
					// 短信提示
					if (ActionUtil.sendSms("wj00306", schema.getcontactMobile()
							, schema.getclaimsItemsName() + ";" + schema.getsendAddress() + ";" + schema.getclaimsNo())) {
						Response.setMessage(Response.getMessage()+"发送待邮寄短信成功！");
					} else {
						Response.setMessage(Response.getMessage()+"发送待邮寄短信失败！");
					}
					
					// 邮件提醒  
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("sendAddress", schema.getsendAddress());
					data.put("claimsNo", schema.getclaimsNo());
					data.put("insureName", schema.getinsureName());
					data.put("applicationDate", applicationDate);
					data.put("claimsItemsName", schema.getclaimsItemsName());
					data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
					if (ActionUtil.sendMail("wj00308", schema.getcontactMail(), data)) {
						Response.setMessage(Response.getMessage()+"发送待邮寄邮件成功！");
					} else {
						Response.setMessage(Response.getMessage()+"发送待邮寄邮件失败！");
					}
					
					if (!param.isEmpty()) {
						// 微信推送 拼装参数
						Map<String, Object> dataParam = new HashMap<String, Object>();
						dataParam.put("first", getWXDataMap("value", "您的"+schema.getclaimsItemsName()+"理赔申请影像资料审核已经通过，请将您拍照审核提交的纸质资料进行邮寄"));
						dataParam.put("date", getWXDataMap("value", applicationDate));
						dataParam.put("name", getWXDataMap("value", schema.getinsureName()));
						dataParam.put("status", getWXDataMap("value", "请将您拍照审核提交的纸质资料进行邮寄"));
						dataParam.put("address", getWXDataMap("value", schema.getsendAddress()));
						dataParam.put("contact", getWXDataMap("value", "4009-789-789"));
						dataParam.put("remark", getWXDataMap("value", "备注：请及时填写快递单号，跟踪理赔进度。如有疑问请拨打4009789789咨询，感谢您的使用。理赔单号："+schema.getclaimsNo()));
						param.put("data", dataParam);
						if (WeiXinCommon.ajaxSendInfoToUser(token, openId, param)) {
							Response.setMessage(Response.getMessage()+"发送待邮寄微信成功！");
						} else {
							Response.setMessage(Response.getMessage()+"发送待邮寄微信失败！");
						}
					}
					
					// 处理中
				} else if ("05".equals(schema.getstatus())) {
					// 电子理赔
					if ("01".equals(schema.getcaseType())) {
						//  短信提示
						if (ActionUtil.sendSms("wj00302", schema.getcontactMobile()
								, schema.getclaimsItemsName() + ";" + schema.getclaimsNo())) {
							Response.setMessage(Response.getMessage()+"发送处理中短信成功！");
						} else {
							Response.setMessage(Response.getMessage()+"发送处理中短信失败！");
						}
						if (!param.isEmpty()) {
							// 微信推送 拼装参数
							Map<String, Object> dataParam = new HashMap<String, Object>();
							dataParam.put("first", getWXDataMap("value", "您好，您的"+schema.getclaimsItemsName()+"理赔申请发生状态变更。"));
							dataParam.put("name", getWXDataMap("value", productType));
							dataParam.put("type", getWXDataMap("value", "单号"));
							dataParam.put("number", getWXDataMap("value", schema.getclaimsNo()));
							dataParam.put("status", getWXDataMap("value", "初审已经完成，理赔案件正在处理中。"));
							dataParam.put("remark", getWXDataMap("value", "备注：理赔结果一般10-15个工作日通知到您，请耐心等待；如有疑问，请随时拨打4009-789-789咨询，感谢您的使用。"));
							param.put("data", dataParam);
							if (WeiXinCommon.ajaxSendInfoToUser(token, openId, param)) {
								Response.setMessage(Response.getMessage()+"发送处理中微信成功！");
							} else {
								Response.setMessage(Response.getMessage()+"发送处理中微信失败！");
							}
						}
					}
					
					// 已结案
				} else if ("06".equals(schema.getstatus())) {
					String claimsMoney = "";
					Map<String, Object> data = new HashMap<String, Object>();
					//  短信提示
					if (StringUtil.isNotEmpty(schema.getclaimsMoney())) {
						data.put("claimsMoney", schema.getclaimsMoney());
						claimsMoney = "理赔金额："+schema.getclaimsMoney()+"！";
					} else {
						data.put("claimsMoney", "");
					}
					
					if (ActionUtil.sendSms("wj00303", schema.getcontactMobile()
							, schema.getclaimsItemsName() + ";" + claimsMoney + ";" + schema.getclaimsNo())) {
						Response.setMessage(Response.getMessage()+"发送结案短信成功！");
					} else {
						Response.setMessage(Response.getMessage()+"发送结案短信失败！");
					}
					
					// 邮件提醒  
					data.put("claimsMoney", claimsMoney);
					data.put("claimsNo", schema.getclaimsNo());
					data.put("insureName", schema.getinsureName());
					data.put("applicationDate", applicationDate);
					data.put("claimsItemsName", schema.getclaimsItemsName());
					data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
					if (ActionUtil.sendMail("wj00208", schema.getcontactMail(), data)) {
						Response.setMessage(Response.getMessage()+"发送结案邮件成功！");
					} else {
						Response.setMessage(Response.getMessage()+"发送结案邮件失败！");
					}
					
					if (!param.isEmpty()) {
						// 微信推送 拼装参数
						Map<String, Object> dataParam = new HashMap<String, Object>();
						dataParam.put("first", getWXDataMap("value", "您好，您的"+schema.getclaimsItemsName()+"理赔申请已成功。"));
						dataParam.put("name", getWXDataMap("value", productType));
						dataParam.put("type", getWXDataMap("value", "单号"));
						dataParam.put("number", getWXDataMap("value", schema.getclaimsNo()));
						dataParam.put("status", getWXDataMap("value", "理赔成功"));
						dataParam.put("pay", getWXDataMap("value", schema.getclaimsMoney()+"元"));
						String message = "备注：您的【"+schema.getclaimsItemsName()+"】理赔款";
						if (StringUtil.isNotEmpty(schema.getclaimsMoney())) {
							message += (":"+schema.getclaimsMoney()+"元，");
						}
						message += "已打入您申请理赔时提供的账户中，请注意查收！如有疑问，请拨打4009789789咨询，感谢您的使用。";
						dataParam.put("remark", getWXDataMap("value", message));
						param.put("data", dataParam);
						if (WeiXinCommon.ajaxSendInfoToUser(token, openId, param)) {
							Response.setMessage(Response.getMessage()+"发送结案微信成功！");
						} else {
							Response.setMessage(Response.getMessage()+"发送结案微信失败！");
						}
					}
					
					// 退回补充材料
				} else if ("03".equals(schema.getstatus())) {
					//  短信提示
					if (ActionUtil.sendSms("wj00310", schema.getcontactMobile()
							, schema.getclaimsItemsName() + ";" + schema.getreturnDesc() + ";" + schema.getclaimsNo())) {
						Response.setMessage(Response.getMessage()+"发送退回补充材料短信成功！");
					} else {
						Response.setMessage(Response.getMessage()+"发送退回补充材料短信失败！");
					}
					
					// 邮件提醒
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("claimsNo", schema.getclaimsNo());
					data.put("insureName", schema.getinsureName());
					data.put("applicationDate", applicationDate);
					data.put("claimsItemsName", schema.getclaimsItemsName());
					data.put("returnDesc", schema.getreturnDesc());
					data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
					if (ActionUtil.sendMail("wj00311", schema.getcontactMail(), data)) {
						Response.setMessage(Response.getMessage()+"发送退回补充材料邮件成功！");
					} else {
						Response.setMessage(Response.getMessage()+"发送退回补充材料邮件失败！");
					}
					
					if (!param.isEmpty()) {
						// 微信推送 拼装参数
						Map<String, Object> dataParam = new HashMap<String, Object>();
						dataParam.put("first", getWXDataMap("value", "尊敬的开心保用户，您好！您的"+schema.getclaimsItemsName()+"理赔申请经审核需要补充材料。"));
						dataParam.put("date", getWXDataMap("value", applicationDate));
						dataParam.put("name", getWXDataMap("value", schema.getinsureName()));
						dataParam.put("status", getWXDataMap("value", "补充材料"));
						dataParam.put("remark", getWXDataMap("value", "需要补充："+schema.getreturnDesc()+" 如有疑问可拨打4009-789-789咨询。理赔单号："+schema.getclaimsNo()));
						param.put("data", dataParam);
						if (WeiXinCommon.ajaxSendInfoToUser(token, openId, param)) {
							Response.setMessage(Response.getMessage()+"发送退回补充材料微信成功！");
						} else {
							Response.setMessage(Response.getMessage()+"发送退回补充材料微信失败！");
						}
					}
					
					// 拒赔
				}else if ("07".equals(schema.getstatus())) {
					//  短信提示
					if (ActionUtil.sendSms("wj00304", schema.getcontactMobile()
							, schema.getclaimsItemsName() + ";" + schema.getreturnDesc() + ";" + schema.getclaimsNo())) {
						Response.setMessage(Response.getMessage()+"发送拒赔短信成功！");
					} else {
						Response.setMessage(Response.getMessage()+"发送拒赔短信失败！");
					}
					
					// 邮件提醒
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("claimsNo", schema.getclaimsNo());
					data.put("insureName", schema.getinsureName());
					data.put("applicationDate", applicationDate);
					data.put("claimsItemsName", schema.getclaimsItemsName());
					data.put("returnDesc", schema.getreturnDesc());
					data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
					if (ActionUtil.sendMail("wj00209", schema.getcontactMail(), data)) {
						Response.setMessage(Response.getMessage()+"发送拒赔邮件成功！");
					} else {
						Response.setMessage(Response.getMessage()+"发送拒赔邮件失败！");
					}
					
					if (!param.isEmpty()) {
						// 微信推送 拼装参数
						Map<String, Object> dataParam = new HashMap<String, Object>();
						dataParam.put("first", getWXDataMap("value", "尊敬的开心保用户，您好！您的"+schema.getclaimsItemsName()+"理赔申请被拒绝。"));
						dataParam.put("date", getWXDataMap("value", applicationDate));
						dataParam.put("name", getWXDataMap("value", schema.getinsureName()));
						dataParam.put("status", getWXDataMap("value", "拒赔"));
						dataParam.put("remark", getWXDataMap("value", "拒赔原因："+schema.getreturnDesc()+" 如有疑问可拨打4009-789-789咨询。理赔单号："+schema.getclaimsNo()));
						param.put("data", dataParam);
						if (WeiXinCommon.ajaxSendInfoToUser(token, openId, param)) {
							Response.setMessage(Response.getMessage()+"发送拒赔微信成功！");
						} else {
							Response.setMessage(Response.getMessage()+"发送拒赔微信失败！");
						}
					}
				}
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("理赔申请信息未查询到!");
		}
	}
	
	public Map<String, Object> getWXDataMap(String key, String value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		map.put("color", "#000000");
		return map;
	}
	public void delClaimsFile() {
		String id = Request.getString("id");
		ClaimsFileSchema fileSchema = new ClaimsFileSchema();
		fileSchema.setid(id);
		if (fileSchema.fill()) {
			File file = new File(fileSchema.getfilePath());
			if (file.exists()) {
				file.delete();
			}
			if (StringUtil.isNotEmpty(fileSchema.getremark1())) {
				file = new File(fileSchema.getremark1());
				if (file.exists()) {
					file.delete();
				}
			}
			if (StringUtil.isNotEmpty(fileSchema.getremark2())) {
				file = new File(fileSchema.getremark2());
				if (file.exists()) {
					file.delete();
				}
			}
			String claimsDataId = fileSchema.getclaimsDataId();
			if (fileSchema.delete()) {
				Response.setStatus(1);
				Response.setMessage("删除成功！");
				// 理赔资料下无文件则删除
				int count = new QueryBuilder("select count(1) from ClaimsFile where claimsDataId = ? ", claimsDataId).executeInt();
				if (count == 0) {
					CustomClaimsDataInfoSchema schema = new CustomClaimsDataInfoSchema();
					schema.setid(claimsDataId);
					if (schema.fill()) {
						schema.delete();
					}
				}
			} else {
				Response.setStatus(0);
				Response.setMessage("删除失败！");
			}
			
		} else {
			Response.setStatus(0);
			Response.setMessage("理赔申请资料文件未查询到!");
		}

	}
}

class SSLkeyShielding implements javax.net.ssl.TrustManager,  
javax.net.ssl.X509TrustManager {  
public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
return null;  
}  

public boolean isServerTrusted(  
    java.security.cert.X509Certificate[] certs) {  
return true;  
}  

public boolean isClientTrusted(  
    java.security.cert.X509Certificate[] certs) {  
return true;  
}  

public void checkServerTrusted(  
    java.security.cert.X509Certificate[] certs, String authType)  
    throws java.security.cert.CertificateException {  
return;  
}  

public void checkClientTrusted(  
    java.security.cert.X509Certificate[] certs, String authType)  
    throws java.security.cert.CertificateException {  
return;  
}  
} 
