package com.sinosoft.jdt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.codehaus.jackson.map.ObjectMapper;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import cn.com.sinosoft.util.SpringUtil;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.services.Service.ServiceInter;
public class ServiceClient {
	private static final Logger logger = LoggerFactory.getLogger(ServiceClient.class);

	private static final String ECHOTEST_FLAG="OK";
	/**
	 * 执行接口调用
	 * 
	 * @param xmlStr
	 */
	public static Document execute(Document paramter, String encoding) {
		/* zhangjinquan 11180 为了保障最内部方法一致，修改此处调用新的方法 2012-10-21 */
		return execute(paramter, encoding, Config.interfaceMap.get("InsureTransfer").toString(), "service");
	}
	
	/**
	 * 投保要素简化一期,美亚，安联，史带，人保四种产品service改为serviceNew
	 * @author guozhichao
	 */
	@SuppressWarnings("unchecked")
	public static Document execute(Document paramter, String encoding,String serviceURL,String methodName,String companyCode) {
		//根据保险公司编码判断是否调用新方法
		Mapx<String, String> codes =  CacheManager.getMapx("Code", "NewServiceComcode");
		if(methodName.equals("service") && codes.containsKey(companyCode)){
			methodName = "serviceNew";
			serviceURL =  Config.interfaceMap.getString("InsureTransferNew");
		}
		return execute(paramter,encoding,serviceURL,methodName);
	}
	
	/**
	 * 执行接口调用
	 * 
	 * @param xmlStr
	 */
	public static Document execute(Document paramter, String encoding,String serviceURL,String methodName) {
		String resultStr = "";
		ByteArrayOutputStream bo = null;
		PrintWriter pw = null;
		StringReader sr = null;
		
		try {
			Document doc = null;
			ServiceInter servicesInter = (ServiceInter)SpringUtil.getBean("jdtService");
			Format format = Format.getPrettyFormat();
			format.setEncoding(encoding);// 设置xml文件的字符, 解决中文问题
			XMLOutputter xmlout = new XMLOutputter(format);
			bo = new ByteArrayOutputStream();
			pw = new PrintWriter(bo);
			xmlout.output(paramter, pw);
			if(null == servicesInter){
				// System.out.println("====================接口参数====================");
				// xmlout.output(paramter, new PrintWriter(System.out));
				Service service = new Service();
				Call call = (Call) service.createCall();
				call.setOperationName(new QName(serviceURL, methodName));
				call.setTargetEndpointAddress(new URL(serviceURL));
				resultStr = (String) call.invoke(new Object[] { bo.toString()});
			}else{
				/**
				Class<? extends ServiceInter> clazz = servicesInter.getClass();
				Method method = clazz.getMethod(methodName, String.class);
				method.invoke(servicesInter, bo.toString());
				 */
				if("service".equals(methodName)){
					resultStr = servicesInter.service(bo.toString());
				}else if("Insurance".equals(methodName)){
					resultStr = servicesInter.Insurance(bo.toString());
				}else if("DataUpload".equals(methodName)){
					resultStr = servicesInter.DataUpload(bo.toString());
				}else if("other".equals(methodName)){
					resultStr = servicesInter.other(bo.toString());
				}else if("cardCheck".equals(methodName)){
					resultStr = servicesInter.cardCheck(bo.toString());
				}else if("cancelTry".equals(methodName)){
					resultStr = servicesInter.cancelTry(bo.toString());
				}else if("serviceNew".equals(methodName)){
					resultStr = servicesInter.serviceNew(bo.toString());
				}else {
					resultStr = servicesInter.service(bo.toString());
				}
			}
			sr = new StringReader(resultStr);
			InputSource is = new InputSource(sr);
			doc = (new SAXBuilder()).build(is);
			/* zhangjinquan 11180 增加流的关闭处理 */
//			System.out.println("==================接口返回参数======================");
//			xmlout.output(doc, System.out);
			return doc;
		} catch (Exception e) {
			logger.error("调用经代通接口访问WebService发生异常"+e.getMessage(), e);
			return null;
		}finally{
			try {
				pw.close();
				bo.close();
				sr.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	/**
	 * 传输xml或者json格式数据，调用dubbo
	 * response返回json串
	 */
	public static Object execute(Object paramter, String encoding, String dataType,String methodName) {
		Object result = null;
		try {
			if("xml".equals(dataType)){
				result = execute((Document)paramter, encoding, null, methodName);
			}/*else if("upLoadFileToMaYi".equals(methodName)){
				ServiceInter servicesInter = (ServiceInter)SpringUtil.getBean("asyncJdtService");
				String[] dateArry = (String[]) paramter;
				result = servicesInter.upLoadFileToMaYi(dateArry[0], dateArry[1]);
			}*/
			else{
				ServiceInter servicesInter = (ServiceInter)SpringUtil.getBean("jdtService");
				if("downloadInvoice".equals(methodName)){
					result = servicesInter.downloadInvoice((String)paramter);
				}else{
					ObjectMapper mapper = new ObjectMapper();
					
					Map<String, Object> requestData = new HashMap<String, Object>();
					Map<String, String> headerData = new HashMap<String, String>();
					headerData.put("interfaceCode", methodName);
					headerData.put("transferTime", DateUtil.getCurrentDateTime());
					requestData.put("head", headerData);
					requestData.put("body", (Map)mapper.readValue((String)paramter, Map.class));
					String returnData = servicesInter.execute(mapper.writeValueAsString(requestData));
					if(StringUtil.isNotEmpty(returnData)){
						Map map =mapper.readValue(returnData, Map.class);
						if ("2049-RenewalSearch".equals(methodName)) {
							result = new Object[]{map};
						}else {
							String responseCode = (String) ((Map)map.get("head")).get("responseCode");
							String responseMessage = (String) ((Map)map.get("head")).get("responseMessage");
							result = new String[]{responseCode, responseMessage};
						}
					}else{
						if ("2049-RenewalSearch".equals(methodName)) {
							Map<String, Object> maps = new HashMap<String, Object>();
							Map<String, String> map = new HashMap<String, String>();
							map.put("responseCode", "01");
							map.put("responseMessage", "调用jdt服务异常");
							maps.put("head", map);
							maps.put("body", "");
							result = new Object[]{maps}; 
						}else {
							result = new String[]{"01", "调用jdt服务异常"};
						}
					}
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("调用经代通接口访问WebService发生异常"+e.getMessage(), e);
			return null;
		}
		
	}

}
