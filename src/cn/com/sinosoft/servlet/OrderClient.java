package cn.com.sinosoft.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

 
 /**
  * 
  *Module:       HttpClientUtil.java
  *Description:  以get/post的方式发送数据到指定的http接口---利用httpclient.jar包---HTTP接口的调用
  *Company:      
  *Author:       ptp
  *Date:         Feb 22, 2012
  */
 
public class OrderClient{
	 private static final Logger logger = LoggerFactory.getLogger(OrderClient.class);
     public static  void postHttp(String ClientID,String MSISDN,int TimeStamp,String Skey ){
    	 URL  url=null;
    	 HttpURLConnection conn = null;
    	 try {
			url = new URL("http://127.0.0.1/orderService");
		   //构造好这个URL对象，参数就是你要通信的servlet地址，实际测试的时候，这个URL可以从Properties文件中取得，以增加灵活性
         conn = (HttpURLConnection)url.openConnection();                 //打开，创建Connection对象

         conn.setRequestMethod("POST");                                         //设定请求方式为POST
         conn.setDoOutput(true); 
         //发送数据
         OutputStream buf = conn.getOutputStream();
         buf = new BufferedOutputStream(buf);
         OutputStreamWriter out = new  OutputStreamWriter (buf);

         out.write(ClientID);
         out.write(MSISDN);
         out.write(TimeStamp);
         out.write(Skey);
         
         out.flush();                                                  
         out.close();
         
         
//         //接收数据
//         InputStream in = conn.getInputStream();
//         in = new BufferedInputStream(in);
//         Reader rData = new InputStreamReader(in);
//         int c;
//         while((c=rData.read()) != -1)
//             System.out.print((char)c);
//
//
    	 }
         catch (Exception e) {
 			// TODO Auto-generated catch block
			 logger.error(e.getMessage(), e);
 		}   //一定要设为true,因为要发送数据


     }
     
     

   /* *//**
     * 测试的main方法
     * @param args
*//*
    public static void main(String[] args) {
    

      //打开文件,该文件是请求报文.
		  FileInputStream fis;
		try {
			fis = new FileInputStream("D:/InsuranceConnectMessage.xml");
		
		  String byteTemp = fis.toString();
		  //更改XML元素***********************************************
		  
		  //生成输出流对象
		//  OutputStream v_OutputStream = g_URLConnection.getOutputStream();
		  ByteArrayOutputStream bos = new ByteArrayOutputStream();
		  //写入文件内容
		    int x = 0;
		    while((x = fis.read()) != -1){
		      bos.write(x);
		    }
		  //处理后关闭
		  fis.close();
		  //ThreeDES是加密解密类
		    ThreeDES threeDES = new ThreeDES();
		    String s = new String(bos.toByteArray());
		    s = threeDES.createEncryptor(s);
		    byte[] bytes = s.getBytes();
		  String param1="12580life";    
          String param2=s;
          int param3=10;
          String param4="18ab01ae328631617e8f06ddc99fd525";
          postHttp(param1,param2,param3,param4);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
        

    }*/
}
