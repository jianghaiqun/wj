package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class TBStatusUpdate extends Page {
	
	private String  comId = "";
	
	/**
	 * 淘宝状态更新
	 */
	public void tbStatusUpdate() {
		
		try{
			
			String orderSn = $V("OrderSn");
			
			if ("TB".indexOf(orderSn) >= 0) {
				Response.setLogInfo(0, "只有淘宝交易成功的订单，才可以使用此项功能！");
				return;
			}
			String appStatus="0";
			String policyNofrom="";
			String sql = "select appStatus,policyNo from sdinformationRisktype where orderSn = ?";
			DataTable dt = new QueryBuilder(sql,orderSn).executeDataTable();
			if (dt == null || dt.getRowCount() == 0) {
				Response.setStatus(0);
				Response.setMessage("保单表数据为空!");
				return;
			}else{
				
			 appStatus = dt.getString(0, 0);
			 policyNofrom = dt.getString(0, 1);
			 
			}
			
			if (!"1".equals(appStatus)) {
				Response.setLogInfo(0, "只有淘宝交易成功的订单，才可以使用此项功能！");
				return;
			}

			if (StringUtil.isEmpty(policyNofrom)) {
				Response.setLogInfo(0, "只有淘宝交易成功的订单且有保单号，才可以使用此项功能！");
				return;
			}
			sql = "select b.ProductType from sdinformation a, jdproductc b where a.productId = b.ERiskID and a.orderSn = ?";
			String productType = new QueryBuilder(sql, orderSn).executeString();
			String xml = reponseResultTB(orderSn);
			boolean result = taoBaoAsync(xml, "?comId=" + comId + "&productType=" + productType);
			 
			if(!result){
				Response.setLogInfo(0, "连接淘宝错误，请重试！");
				return;
			}else{
				Response.setLogInfo(1, "淘宝状态更新成功！");
				return;
			}
		}catch(Exception e){
			logger.error("淘宝状态更新错误：" + e.getMessage(), e);
			Response.setLogInfo(0, "淘宝状态更新失败");
			return;
		}
	}
	
	/**
	 * 组装状态更新回调报文
	 * 
	 * @return 组装报文
	 * @throws Exception
	 */
	private String reponseResultTB(String orderSn)
			throws Exception {
		
		String currentDate = DateUtil.getCurrentDate();
		String currentTime = DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss");
		Element rootPackageList = new Element("PackageList");
		Element rootPackage = new Element("Package");
		Document doc = new Document(rootPackageList); // 要创建的XML文档 - doc

		// 取得唯一编码
		String sql = "select a.remark,a.tbComCode,a.tbTradeSeriNo,a.totalAmount,b.applyPolicyNo,b.policyNo,b.electronicpath,b.riskCode,b.insureDate,a.createDate " +
				"from sdorders a join sdinformationriskType b on a.orderSn = b.orderSn where a.orderSn = ?";
		String sqltemp[] = { orderSn };
		List<HashMap<String, String>> list = new GetDBdata()
				.query(sql, sqltemp);
		// 唯一编码
		String uuId = "";
		// 淘宝交易流水号
		String taoBaoSerial = "";
		// 总保费
		String totalPremium = "";
		//保单号
		String policyNo = "";
		// 电子保单
		String electronicpath = "";
		// 产品编码
		String riskcode = "";
		// 承包时间
		String insureDate = "";
		
		if (list.size() > 0) {
			uuId = list.get(0).get("remark");
			comId = list.get(0).get("tbComCode");
			taoBaoSerial = list.get(0).get("tbTradeSeriNo");
			totalPremium = list.get(0).get("totalAmount");
			policyNo = list.get(0).get("policyNo");
			electronicpath = list.get(0).get("electronicpath");
			riskcode = list.get(0).get("riskCode");
			insureDate = list.get(0).get("insureDate");
			if (StringUtil.isEmpty(insureDate)) {
				insureDate = list.get(0).get("createDate");
			}
		}
		
		// header 信息
		Element headerElement = new Element("Header");

		// 交易类型(必填)
		Element RequestTypeElement = new Element("RequestType");
		RequestTypeElement.setText("12");
		headerElement.addContent(RequestTypeElement);

		// 唯一编码(必填)
		Element UUIDElement = new Element("UUID");
		UUIDElement.setText(uuId);
		headerElement.addContent(UUIDElement);

		// 交互保险公司编码(必填)
		Element ComIdElement = new Element("ComId");
		ComIdElement.setText(comId);
		headerElement.addContent(ComIdElement);

		// 发送方编号(必填)
		Element FromElement = new Element("From");
		// FromElement.setText("taobao");
		FromElement.setText(comId);
		headerElement.addContent(FromElement);

		// 发送时间(必填)
		Element SendTimeElement = new Element("SendTime");
		SendTimeElement.setText(currentTime);
		headerElement.addContent(SendTimeElement);

		// 淘宝交易流水号(必填)
		Element TaoBaoSerialElement = new Element("TaoBaoSerial");
		TaoBaoSerialElement.setText(taoBaoSerial);
		headerElement.addContent(TaoBaoSerialElement);

		// 保险公司流水号(非必填)
		Element ComSerialElement = new Element("ComSerial");
		//ComSerialElement.setText(insured.getorderSn());
		headerElement.addContent(ComSerialElement);

		// end header

		// Callback 信息
		Element CallbackElement = new Element("Callback");

		// Policy
		Element PolicyElement = new Element("Policy");
		CallbackElement.addContent(PolicyElement);

		// 淘宝订单号(必填)
		Element TBOrderIdElement = new Element("TBOrderId");
		TBOrderIdElement.setText(taoBaoSerial);
		PolicyElement.addContent(TBOrderIdElement);

		// 保单号(必填)
		Element PolicyNoElement = new Element("PolicyNo");
		PolicyNoElement.setText(policyNo);
		PolicyElement.addContent(PolicyNoElement);

		// 投保单号（TB订单号-必填）
		Element ProposalNoElement = new Element("ProposalNo");
		ProposalNoElement.setText(orderSn);
		PolicyElement.addContent(ProposalNoElement);

		// 总保费(必填)
		Element TotalPremiumElement = new Element("TotalPremium");
		if (!StringUtil.isEmpty(totalPremium)) {
			// 以分为单位
			TotalPremiumElement.setText(String.valueOf((int) (Double
					.parseDouble(totalPremium) * 100)));
		}

		PolicyElement.addContent(TotalPremiumElement);

		// 出单是否成功(必填)
		Element IsSuccessElement = new Element("IsSuccess");
		IsSuccessElement.setText("1");
		PolicyElement.addContent(IsSuccessElement);

		// 出单失败信息(非必填)
		Element FailReasonElement = new Element("FailReason");
		PolicyElement.addContent(FailReasonElement);

		// 电子保单地址(非必填)
		Element PolicyUrlElement = new Element("PolicyUrl");
		String PolicyUrl = "";
		if (StringUtil.isEmpty(electronicpath)) {
			String insureYear = insureDate.substring(0, 4);
			String insureMonth = insureDate.substring(5, 7);
			String pdfName = new QueryBuilder(
					"select MD5(policyNo) from sdinformationriskType where policyNo = ?",
					policyNo).executeString();
			PolicyUrl += Config.getValue("newPolicyPath") + File.separator
					+ "EPolicy" + File.separator + riskcode.substring(0, 4)
					+ File.separator + insureYear + File.separator
					+ insureMonth + File.separator + pdfName + ".pdf";
			// 更新电子保单路径
			Transaction ts = new Transaction();
			QueryBuilder qb =new QueryBuilder();
			qb.setSQL("update sdinformationriskType set electronicpath = ? where policyNo = ? and  orderSn = ?  ");
			qb.add(PolicyUrl);
			qb.add(policyNo);
			qb.add(orderSn);
			ts.add(qb);
			ts.commit();
		}
		PolicyUrl = electronicpath.replace(Config.getValue("newPolicyPath"),
				Config.getFrontServerContextPath());
		PolicyUrlElement.setText(PolicyUrl);
		PolicyElement.addContent(PolicyUrlElement);

		// 账务日期(非必填)
		Element AccountDateElement = new Element("AccountDate");
		AccountDateElement.setText(currentDate);
		PolicyElement.addContent(AccountDateElement);

		// 出单时间(非必填)
		Element IssuedTimeElement = new Element("IssuedTime");
		IssuedTimeElement.setText(currentTime);
		PolicyElement.addContent(IssuedTimeElement);

		rootPackage.addContent(headerElement);
		rootPackage.addContent(CallbackElement);
		rootPackageList.addContent(rootPackage);
		
		return transformXMLToString(doc);
	}
	
	/**
	 * 将Document对象转换成字符串
	 * 
	 * @return 关单报文
	 * @throws Exception
	 */
	public String transformXMLToString(Document document) {
		try {
			XMLOutputter xmlout = new XMLOutputter();
			Format tFormat = Format.getPrettyFormat();
			tFormat.setEncoding("GBK");
			xmlout.setFormat(tFormat);
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			xmlout.output(document, bo);
			String xmlStr = bo.toString();
			return xmlStr;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 淘宝状态更新回调
	 * 
	 * @param returnXml
	 *            请求报文
	 * @param param
	 *            GET参数
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	public boolean taoBaoAsync(String returnXml, String param) throws IOException {
		HttpClient httpClient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true));
		String url = Config.interfaceMap.getString("OrderUpdateAccessServlet");
		PostMethod post = new PostMethod(url + param);
		boolean bl = false;
		InputStream in_tb = null;
		try{
			in_tb = new ByteArrayInputStream(returnXml.getBytes("GBK"));
			post.setRequestBody(in_tb);
			httpClient.executeMethod(post);
			
			String result = post.getResponseBodyAsString().trim();
			if("success".equals(result)){
				bl = true;
			}
		}catch(Exception e){
			logger.error("淘宝状态更新回调错误：" + e.getMessage(), e);
		}finally{
			post.releaseConnection();
			if(in_tb != null){
				in_tb.close();
			}
		}
		return bl;
	}
	
}
