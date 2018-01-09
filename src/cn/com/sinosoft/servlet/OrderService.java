package cn.com.sinosoft.servlet;

import cn.com.sinosoft.common.ThreeDES;
import cn.com.sinosoft.entity.Information;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.entity.OrderItem;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
 
 /**
  *Module:       TestHTTPServer.java
  *Description:  为了验证http接口的调用，编写了一个模拟的http接口
  *Company:      
  *Author:       ptp
  *Date:         Feb 22, 2012
  */
 
 public class OrderService  extends HttpServlet{
	 private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
     private static final long serialVersionUID = 1L;
     	private List<Leaf> elemList = new ArrayList<Leaf>();
     	/**
     	 * 取得Spring容器中的Session对象
     	 * 
     	 * @return
     	 */
     	private Session getSession() {
     		WebApplicationContext wac = ContextLoader
     				.getCurrentWebApplicationContext();
     		SessionFactory sessionFactory = (SessionFactory) wac
     				.getBean("sessionFactory");
     		return sessionFactory.getCurrentSession();
     	}
     	//MD5加密
     	public  String toMessageDigest(String Str) {

     		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
     			    'A', 'B', 'C', 'D', 'E', 'F' };
     			  try {
     			   byte[] strTemp = Str.getBytes();
     			   MessageDigest mdTemp = MessageDigest.getInstance("MD5");
     			   mdTemp.update(strTemp);
     			   byte[] md = mdTemp.digest();
     			   int j = md.length;
     			   char str[] = new char[j * 2];
     			   int k = 0;
     			   for (int i = 0; i < j; i++) {
     			    byte byte0 = md[i];
     			    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
     			    str[k++] = hexDigits[byte0 & 0xf];
     			   }
     			   return new String(str);
     			  } catch (Exception e) {
     			   return null;
     			  }

     		}



     	/**
     	 * 取得订单
     	 */
     	@SuppressWarnings("unchecked")
     	private List<Order> getOrders(String username) {
     		 String hql = "from Order as order where order.member.username=? and  order by order.createDate desc";
     	//	String hql = "from Order as order where order.id!='8a36802e34acb4240134acccbf12000f' order by order.createDate desc";
     		return this.getSession().createQuery(hql).list();

     	}
     	/**
     	 * 取得订单明细
     	 */
     	@SuppressWarnings("unchecked")
     	private List<OrderItem> getOrderItems(String orderId) {
     		String hql = "from OrderItem as item where item.order.id=?";
     		return this.getSession().createQuery(hql).setParameter(0, orderId)
     				.list();

     	}
     	/**
     	 * 取得订单产品
     	 */
     	private Information getInfo(String orderItemId) {
     		String hql = "from Information as info where info.orderItem.id=?";
     		return (Information) this.getSession().createQuery(hql)
     				.setParameter(0, orderItemId).uniqueResult();
     	}

     	/**
     	 * 解密、获取Document对象 创建日期 2012-三月-12
     	 * 
     	 * @author 王晓燕
     	 * @param
     	 * @return String test
     	 */
     	public Document desencryXml(String stringXML) {

     		ThreeDES threeDES = new ThreeDES();
     		Document doc = null;
     		try {
     			stringXML = threeDES.createDecryptor(stringXML);
     			doc = DocumentHelper.parseText(stringXML);
     		} catch (Exception e) {
				logger.error(e.getMessage(), e);
     		}
     		return doc;
     	}

     	/**
     	 * 解析XML报文 创建日期2012-三月-12
     	 * 
     	 * @author 王晓燕
     	 * @param
     	 * @return String test
     	 */
     	 private Element readDocument(Document document) {
     	 Element root = document.getRootElement();// 获取根节点
     	 return root;
     	 }

     	/**
     	 * 递归遍历方法 创建日期 2012-三月-12
     	 * 
     	 * @author 王晓燕
     	 * @param
     	 * @return String test
     	 */
     	@SuppressWarnings("rawtypes")
     	public List<Leaf> getElementList(Element element) {
     		List elements = element.elements();
     		if (elements.size() == 0) {
     			// 没有子元素
     			String xpath = element.getPath();
     			String value = element.getTextTrim();
     			elemList.add(new Leaf(xpath, value));
     		} else {
     			// 有子元素
     			for (Iterator it = elements.iterator(); it.hasNext();) {
     				Element elem = (Element) it.next();
     				// 递归遍历
     				getElementList(elem);
     			}
     		}
     		return elemList;
     	}
     	// xml组合
     		public String getxmlString(String RequestID,String stringXML,int TimeStamp,String Skey) {
     			Date date=new Date();
     			long timestamp=date.getTime();
     			String shey=RequestID+stringXML+TimeStamp+"12580life";
     			String  mdshey=toMessageDigest(shey);
     		
     			String errorinfo=null;
     			if(!RequestID.equals("12580life")){
     				errorinfo="Error_1001";
     				return getErrorRusult(errorinfo);
     			}
     			else if(Skey.equals(mdshey)){
     				errorinfo="Error_1002";
     				return getErrorRusult(errorinfo);
     			}
     			else if(TimeStamp<=timestamp){
     				errorinfo="Error_1003";
     				return getErrorRusult(errorinfo);
     			}
     			else{
     			// 把传过来的字符串转化成xml并得到document对象
     			 Document doc = desencryXml(stringXML);
     			// 获得根节点
     			 Element root = readDocument(doc);
     			// 遍历、解析xml
     			 OrderService orderservice = new OrderService();
     			 List<Leaf> elemList = orderservice.getElementList(root);
     			 String aa = elemList.get(0).getValue();
     			 String result=getRusult("");
     			 return result;
     			}
     		}
     		//查询正确信息返回
     		public String getRusult(String username){
     			StringBuffer sb = new StringBuffer();
     			// 头尾部组合
     			Document document = DocumentHelper.createDocument();
     			Element ZSOrderElement = document.addElement("ZSOrder");
     			List<Order> allOrder = this.getOrders("");

     			for (int i = 0; i < allOrder.size(); i++) {
     				String order = generateOrderDocument(allOrder.get(i), document,ZSOrderElement);
     				if (i == allOrder.size() - 1) {
     					sb.append(order);
     				}

     			}
     			ThreeDES threeDES = new ThreeDES();
     			String s=null;
     			try {
     				 s = threeDES.createEncryptor(sb.toString());
     			} catch (Exception e) {
					logger.error(e.getMessage(), e);
     			}
     			return sb.toString();
     		}
     		//查询错误信息返回
     				public String getErrorRusult(String error){
     					Document document = DocumentHelper.createDocument();
     					Element ReturnCodeElement = document.addElement("ReturnCode");
     					ReturnCodeElement.setText(error);
     					String errorinfo=document.asXML();
     					ThreeDES threeDES = new ThreeDES();
     					String s=null;
     					try {
     						 s = threeDES.createEncryptor(errorinfo);
     					} catch (Exception e) {
							logger.error(e.getMessage(), e);
     					}
     					return errorinfo;
     				}
     			
     	/**
     	 * XML生成 创建日期 2012-三月-12
     	 * 
     	 * @author 王晓燕
     	 * @param
     	 * @param
     	 * @param
     	 * @return String test
     	 */
     	public String generateOrderDocument(Order order, Document document,Element ZSOrderElement) {
     		Element OrderElement = ZSOrderElement.addElement("Order");

     		Element OrderSnElement = OrderElement.addElement("OrderSn");
     		OrderSnElement.setText(order.getOrderSn());
     		Element OrderTimeElement = OrderElement.addElement("OrderTime");
     		OrderTimeElement.setText(order.getCreateDate() + "");
     		Element TotalAmountElement = OrderElement.addElement("TotalAmount");
     		TotalAmountElement.setText(order.getTotalAmount() + "");
     		Element OrderStatusElement = OrderElement.addElement("OrderStatus");
     		OrderStatusElement.setText(order.getOrderStatus() + "");
     		Element DeliveryTypeNameElement = OrderElement
     				.addElement("DeliveryTypeName");
//     		DeliveryTypeNameElement.setText(order.getDeliveryTypeName());
     		Element PaymentStatusElement = OrderElement.addElement("PaymentStatus");
     		PaymentStatusElement.setText(order.getPaymentStatus() + "");
     		Element TotalQuantityElement = OrderElement.addElement("TotalQuantity");
     		TotalQuantityElement.setText(order.getProductTotalQuantity() + "");
     		Element ProductsElement = OrderElement.addElement("Products");

     		List<OrderItem> orderItem = this.getOrderItems(order.getId());
     		for (OrderItem item : orderItem) {
     			logger.info("循环OrderItem：{}", item.getId());
     			Information information = this.getInfo(item.getId());
     			logger.info("取得Information对象：{}", information);
     			Element ProcudctElement = ProductsElement.addElement("Procudct");
     			Element ProductIdElement = ProcudctElement.addElement("ProductId");
//     			ProductIdElement.setText(item.getProductSn());
     			Element ProductNameElement = ProcudctElement
     					.addElement("ProductName");
     			ProductNameElement.setText(item.getProductName());
     			Element PriceElement = ProcudctElement.addElement("Price");
     			PriceElement.setText(item.getProductPrice() + "");
     			Element QuantityElement = ProcudctElement.addElement("Quantity");
     			QuantityElement.setText(item.getProductQuantity() + "");
     			Element ApplicantElement = ProcudctElement.addElement("Applicant");
     			Element ApplicantNameElement = ApplicantElement
     					.addElement("ApplicantName");
//     			ApplicantNameElement.setText(information.getApplicantName());
     			Element ApplicantSexElement = ApplicantElement
     					.addElement("ApplicantSex");
//     			ApplicantSexElement.setText(information.getApplicantSex());
     			Element ApplicantIdentityIdElement = ApplicantElement
     					.addElement("ApplicantIdentityId");
//     			ApplicantIdentityIdElement.setText(information
//     					.getApplicantIdentityId());
     			Element ApplicantBirthdayElement = ApplicantElement
     					.addElement("ApplicantBirthday");
//     			ApplicantBirthdayElement
//     					.setText(information.getApplicantBirthday());
     			Element ApplicantTelElement = ApplicantElement
     					.addElement("ApplicantTel");
//     			ApplicantTelElement.setText(information.getApplicantTel());
     			Element ApplicantMailElement = ApplicantElement
     					.addElement("ApplicantMail");
//     			ApplicantMailElement.setText(information.getApplicantMail());
     			Element RecognizeeElement = ProcudctElement
     					.addElement("Recognizee");
     			Element RecognizeeNameElement = RecognizeeElement
     					.addElement("RecognizeeName");
//     			RecognizeeNameElement.setText(information.getRecognizeeName());
     			Element RecognizeeSexElement = RecognizeeElement
     					.addElement("RecognizeeSex");
//     			RecognizeeSexElement.setText(information.getRecognizeeSex());
     			Element RecognizeeIdentityIdElement = RecognizeeElement
     					.addElement("RecognizeeIdentityId");
//     			RecognizeeIdentityIdElement.setText(information
//     					.getRecognizeeIdentityId());
     			Element RecognizeeBirthdayElement = RecognizeeElement
     					.addElement("RecognizeeBirthday");
//     			RecognizeeBirthdayElement.setText(information
//     					.getRecognizeeBirthday());
     			Element RecognizeeTelElement = RecognizeeElement
     					.addElement("RecognizeeTel");
//     			RecognizeeTelElement.setText(information.getRecognizeeTel());
     			Element RecognizeeMailElement = RecognizeeElement
     					.addElement("RecognizeeMail");
//     			RecognizeeMailElement.setText(information.getRecognizeeMail());

     		}

     		String consumptionXml = document.asXML();
     		return consumptionXml;
     	}


    
    
 
     public void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
         PrintWriter out = response.getWriter();
       //获得输入流
         InputStream in= request.getInputStream();
         in = new BufferedInputStream(in);
         Reader rData = new InputStreamReader(in);
         String s = in.toString();
         int c;
         while((c=rData.read()) != -1)
            out.print((char)c);
         //得到参数，调用方法
         getxmlString("1","2",3,"4");
         out.close();
     }
 }
 /**
  * xml节点数据结构
  */
 class Leaf {
 	private String xpath; //
 	private String value;

 	public Leaf(String xpath, String value) {
 		this.xpath = xpath;
 		this.value = value;
 	}

 	public String getXpath() {
 		return xpath;
 	}

 	public void setXpath(String xpath) {
 		this.xpath = xpath;
 	}

 	public String getValue() {
 		return value;
 	}

 	public void setValue(String value) {
 		this.value = value;
 	}

 }
