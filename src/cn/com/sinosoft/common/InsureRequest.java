package cn.com.sinosoft.common;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class InsureRequest {
	private final static Logger logger = LoggerFactory.getLogger(InsureRequest.class);
	public static void main(String[] args) throws Exception{
		 try {
			
		    
		    //打开文件,该文件是请求报文.
		    FileInputStream fis = new FileInputStream("D:/请求报文.xml");
		    SAXReader saxReader = new SAXReader();  
		    
		    Document document = saxReader.read(fis);
//		    Element rootElement=document.getRootElement();//根节点
//		    
//		    for(Iterator iterator=rootElement.elementIterator();iterator.hasNext();){  
//	            Element TXLifeRequest=(Element)iterator.next();  //TXLifeRequest节点
//	            TXLifeRequest.element("TransRefGUID").setText("");//TransRefGUID 交易流水号 
//	            TXLifeRequest.element("TransType").setAttributeValue("tc", "103");//交易码
//	            TXLifeRequest.element("TransType").setText("New Business Submission");
//	            TXLifeRequest.element("TransMode").setAttributeValue("tc", "2");//交易模式
//	            TXLifeRequest.element("TransMode").setText("Original");
//	            TXLifeRequest.element("TransExeDate").setText("2012-03-21");//交易时间
//	            TXLifeRequest.element("TransExeTime").setText("13:38:55");//交易时间
//	            Element oLifE =  TXLifeRequest.element("OLifE");//OLifE节点
//	            
//	            Element holding = oLifE.element("Holding");
//	            Element Policy = holding.element("Policy");
//	            Policy.element("ApplicantMode").setAttributeValue("tc", "1");//<!-- 出单类型 1.自助 2.远程 4.激活卡 -->
//	            Policy.element("FeeStatus").setAttributeValue("tc", "1");//<!-- 收费状态 -->	
//	            Policy.element("FeeStatus").setText("OVERDUE") ;
//	            Policy.element("ProductCode").setText("E-QNHKBZ");//<!-- 产品代码 -->
//	            Policy.element("PlanName").setText("e时代全年航空保障计划");//<!-- 产品名称 -->	
//	            Policy.element("EffDate").setText("2012-03-21");//<!-- 生效日期 -->	
//	            Policy.element("BenefitPeriod").setText("2");//<!-- 保障期间 -->
//	            Element life = Policy.element("Life");
//	            life.element("PolicyPaymentType").setText("ALIPAY");//<!-- 支付类型 -->
//	            life.element("ProductLevel").setAttributeValue("tc", "0");
//	            life.element("ProductLevel").setText("COMMON");//  <!-- 产品档次（电商提供的枚举值，根据每个产品不同枚举值不同） -->	
//	            life.element("TravelType").setAttributeValue("tc", "3");//<!-- 旅游类型:1出境旅游 2入境旅游 3国内旅游 -->
//	            life.element("TravelType").setText("CISBORDER");
//	            life.element("Departure").setText("北京");//<!-- 出发地 -->
//	            life.element("Destination").setText("上海,广东,海南");//<!-- 出发地 -->
//	            
//	            List<Element> listRelation = document.selectNodes("//OLifE/Relation");
//	            
//	            for(Element n : listRelation){//遍历同名同属性节点"Relation"
//	            	String temp = n.attribute("OriginatingObjectID").getText();
//	            	n.element("OriginatingObjectType").setAttributeValue("tc", "4");
//	            	n.element("OriginatingObjectType").setText("Holding");
//	            	n.element("RelatedObjectType").setAttributeValue("tc", "4");
//	            	n.element("RelatedObjectType").setText("Holding");
//	            	n.element("RelationRoleCode").setAttributeValue("tc", "4");
//	            	n.element("RelationRoleCode").setText("Holding");
////	            	System.out.println("temp ===="+temp);
////	            	//循环得到其子元素
////	            	for(Iterator<Element> i = n.elements().iterator();i.hasNext();){
////	            		Element element = (Element)i.next();
////	            		//打印这个子元素的name和text
////	            		System.out.println(element.getName()+":"+element.getText());
////	            	}
//	            }
//	            List<Element> listParty = document.selectNodes("//OLifE/Party");
//	            for(Element a : listParty){//遍历同名同属性节点"Relation"
////	            	String temp = a.attribute("OriginatingObjectID").getText();
//	            	a.element("FullName").setText("Holding");//投保人姓名
//	            	a.element("GovtID").setText("123");//投保人证件号码
//	            	a.element("GovtIDTC").setText("Holding");//证件类型
//	            	a.element("GovtIDTC").setAttributeValue("tc", "1");
//	            	a.element("Person").element("Gender").setAttributeValue("tc", "1");//投保人性别
//	            	a.element("Person").element("Gender").setText("男");
//	            	a.element("Person").element("BirthDate").setText("1985-07-05");
//	            	a.element("Phone").setAttributeValue("id", "Phone_2");
//	            	a.element("Phone").element("PhoneTypeCode").setAttributeValue("tc", "12");
//	            	a.element("Phone").element("PhoneTypeCode").setText("12");
//	            	a.element("Phone").element("DialNumber").setText("12");//电话
//	            	a.element("Phone").element("Email").setText("12");
//	            }
//	            //如果未成年人投保	
//	            oLifE.element("FormInstance").element("DocumentControlType").setText("1");//<!--表单类型 1:告知表单 2:发票 -->
//	            oLifE.element("FormInstance").element("DocumentControlNumber").setText("123456");//<!--表单印刷号 -->
//	            oLifE.element("FormInstance").element("FormResponse").element("QuestionText").setText("被保险人是否已拥有");
//	            oLifE.element("FormInstance").element("FormResponse").element("ResponseCode").setText("Y");
//	            oLifE.element("FormInstance").element("FormResponse").element("ResponseText").setText("0,人保寿险,2011-01-01");
//	            
//	            Element oLifEExtension =  TXLifeRequest.element("OLifEExtension");//OLifE节点
//	            oLifEExtension.element("CarrierCode").setText("PICC");
//	            oLifEExtension.element("Branch").setText("JJSH20070012");
//	            oLifEExtension.element("BankCode").setText("SUZHOUYIDONG");
//	        }  
//	    
//		    	
//		    String byteTemp = fis.toString();
//		    String y = document.asXML();
//		    System.out.println("请求XML模板："+y);
		    Properties prop = System.getProperties();
		    prop.setProperty("http.proxyHost", "10.182.0.2");
		    prop.setProperty("http.proxyPort", "3128");
			//这个URL是对外开放的,用于合作伙伴连接请求.如果需要联调测试，请提前通知我们
		    URL url = new URL("http://e.picclife.com/picc/Partner/RemoteBusinessProcessController.jspx?_action=insure&partner=PolicyShort");
		    
		    HttpURLConnection g_URLConnection = (HttpURLConnection) url.openConnection();
		    g_URLConnection.setRequestProperty("content-type", "text/html;charset=utf-8");  
		    g_URLConnection.setDoOutput(true);
		    g_URLConnection.setDoInput(true);
		    //生成输出流对象
		    OutputStream v_OutputStream = g_URLConnection.getOutputStream();
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    
		    //写入文件内容
		    int x = 0;
		    while((x = fis.read()) != -1){
		      bos.write(x);
		    }
		    fis.close();

		    logger.info("加密前发送报文 ：{}", new String(bos.toByteArray()));

		    // DESPlus是加密类
		    DESPlus desPlus = new DESPlus();
		    String s = new String(bos.toByteArray());
		    s = desPlus.encrypt(s);
		    byte[] bytes = s.getBytes();
		    logger.info("加密后发送报文 ：{}", s);

		    v_OutputStream.write(bytes);
		    //刷新输出流
		    v_OutputStream.flush();

		    //获得服务器相应的输入流
		    InputStream g_return = g_URLConnection.getInputStream();
		    ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
		    while ((x = g_return.read()) != -1) {
		    	bos2.write(x);
		    }
		    bos2.flush();

		    //关闭URL
		    v_OutputStream.close();
		    g_return.close();
		    String str = new String(bos2.toByteArray());
		    logger.info("解密前返回报文：{}", str);
		    str = desPlus.decrypt(str);
		    logger.info("解密后返回报文：{}", str);
		} catch (Exception e) {
		 	logger.error(e.getMessage());
		}
	}
}
