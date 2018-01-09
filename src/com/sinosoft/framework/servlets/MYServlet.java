package com.sinosoft.framework.servlets;

import cn.com.sinosoft.util.DownloadNet;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.ParseXMLToObject;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 经代通美亚接口
 * 
 * @author sinosoft
 * 
 */
public class MYServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(MYServlet.class);

	private static final long serialVersionUID = 1138717953245092512L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "未收到请求数据";
		OutputStream out = response.getOutputStream();
		try {
			ParseXMLToObject tParseXMLToObject = new ParseXMLToObject();
			Document document = tParseXMLToObject.deealStreamToXML(request);
			if (document == null) {
				result = "xml为空（未收到请求数据）--- time：" + PubFun.getCurrentDate() + " " + PubFun.getCurrentTime() + "";
				logger.error(result);
				throw new NullPointerException();
			}
			// 重新获取订单号
			Element root = document.getRootElement();
			if (null == root) {
				result = "获取美亚电子保单接口root节点为空";
				logger.error("获取美亚电子保单接口root节点为空");
				throw new NullPointerException();
			}

			/* 取得header部分 */
			Element eleHeader = root.getChild("Header");
			if (null == eleHeader) {
				result = "获取美亚电子保单接口Header节点为空";
				logger.error("获取美亚电子保单接口Header节点为空");
				throw new NullPointerException();
			}
			/* 取得Response部分 */
			Element eleResponse = root.getChild("Response");
			/* 取得订单号 */
			String orderSn = eleHeader.getChildTextTrim("BK_SERIAL");
			String insureSn = eleHeader.getChildTextTrim("BK_SERIAL");
			if(StringUtil.isEmpty(insureSn)){
				throw new NullPointerException("获取美亚电子保单接口 "+ "返回报文BK_SERIAL节点为空！");
			}
			if(StringUtil.isNotEmpty(orderSn) && (orderSn.indexOf("_")!=-1)){
				orderSn = orderSn.substring(0, orderSn.indexOf("_"));
			}
			if(StringUtil.isNotEmpty(insureSn) && insureSn.startsWith("TB")){
				insureSn = orderSn+"_1";
			}
			List<Element> listorderInfo = eleResponse.getChildren("OrderInfo");
			int iorderInfoSize = listorderInfo.size();
			if (0 == iorderInfoSize) {
				throw new NullPointerException("获取美亚电子保单接口 "+ "返回报文没有orderInfo节点！");
			}
			Element eleOrderInfo = (Element)listorderInfo.get(0);
			if (eleOrderInfo.getContentSize() == 0) {
				throw new NullPointerException("获取美亚电子保单接口 " + "返回报文orderInfo缺少子节点！");
			}		
			List<Element> listPolicyInfo = eleOrderInfo.getChildren("policyInfo");
			int iPolicyInfoSize = listPolicyInfo.size();
			if (0 == iPolicyInfoSize) {
				throw new NullPointerException("获取美亚电子保单接口 " + "返回报文没有policyInfo节点！");
			}
			Element elePolicyInfo = (Element)listPolicyInfo.get(0);
			if (elePolicyInfo.getContentSize() == 0) {
				throw new NullPointerException("获取美亚电子保单接口 " + "返回报文policyInfo缺少子节点！");
			}
			/* 取得电子保单路径 */
			String policyPath = elePolicyInfo.getChildTextTrim("noticeNo");
			if(StringUtil.isEmpty(policyPath)){
				Map<String,Object> map = new HashMap<String,Object>();
				GetDBdata db = new GetDBdata();
				String[] temp = {insureSn};
				List<HashMap<String, String>> dbResult = db.query("select a.policyNo,c.productName,b.totalAmount from sdinformationRiskType a join SDInformationInsured d on a.recognizeeSn = d.recognizeeSn join sdinformation c on a.informationSn = c.informationSn join sdorders b on c.orderSn = b.orderSn where d.insuredSn = ? ", temp);
				HashMap<String, String> hm = dbResult.get(0);
				map.put("OrderSn", orderSn);
				map.put("PolicyNo", hm.get("policyNo"));
				map.put("ProductName", hm.get("productName"));
				map.put("TotalAmount", hm.get("totalAmount"));
				map.put("ErrMsg", "获取经代通美亚电子保单路径失败");
				String ErrMsg = getmsg(map);
				ActionUtil.sendAlarmMail(ErrMsg);
			}
			List<HashMap<String,String>> insuredSnList = null;
			List<HashMap<String,String>> pathList = new ArrayList<HashMap<String,String>>();
			DownloadNet db=new DownloadNet();
			if(StringUtil.isNotEmpty(policyPath)){
				String sql = "select id,recognizeeSn, insuredSn,recognizeeName from SDInformationInsured where insuredSn=?";
				String[] sqltemp = {insureSn};
				insuredSnList = new GetDBdata().query(sql,sqltemp);
				for(HashMap<String,String> insuredSnMap : insuredSnList){
					String insuredSn = insuredSnMap.get("insuredSn");
					String recognizeeName = insuredSnMap.get("recognizeeName");
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("path", policyPath);
					map.put("recognizeeName", recognizeeName);
					map.put("insuredSn",insuredSn);
					pathList.add(map);
					int i = policyPath.lastIndexOf("/")+1;
					String name = policyPath.substring(i, policyPath.length());
					db.saveOrdersPrint(orderSn, name, policyPath, insuredSn,"下载开始");
				}
				db.getGeneratepolicy(insuredSnList,pathList,orderSn ,"2034");
				QueryBuilder qb = new QueryBuilder("WHERE sdinformationinsured_id=(SELECT id FROM sdinformationinsured WHERE insuredsn=?)");
				qb.add(insureSn);
				SDInformationRiskTypeSet sdRiskTypeSet = new SDInformationRiskTypeSchema().query(qb);
				sdRiskTypeSet.get(0).setelectronicPath(policyPath);
				if(sdRiskTypeSet.get(0).update()){
					logger.info("美亚电子保单路径保存成功");
				}else{
					logger.error("美亚电子保单路径保存失败");
				}
			}
			result = "success";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result += "error:" + e.getMessage();
		} finally {
			out.write(result.getBytes("utf-8"));
			out.flush();
			out.close();
		}
	}

	/**
	 * 拼装table报错信息表格
	 * @return
	 */
	public String getmsg(Map<String, Object> map){

		String msgInf="<table border=\"1\" >"
				+"<tr>"
				+"<td>订单号</td>"
				+"<td>保单号</td>"
				+"<td>产品名称</td>"
				+"<td>保费</td>"
				+"<td>错误信息</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"+map.get("OrderSn")+"</td>"
				+"<td>"+map.get("PolicyNo")+"</td>"
				+"<td>"+map.get("ProductName")+"</td>"
				+"<td>"+map.get("TotalAmount")+"</td>"
				+"<td>"+map.get("ErrMsg")+"</td>"
				+"</tr>"
				+"</table>";
		return msgInf;
	}
}
