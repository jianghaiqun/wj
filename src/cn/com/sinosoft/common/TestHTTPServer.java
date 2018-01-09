package cn.com.sinosoft.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

/**
  *Module:       TestHTTPServer.java
  *Description:  为了验证http接口的调用，编写了一个模拟的http接口
  *Company:      
  *Author:       ptp
  *Date:         Feb 22, 2012
  */
 
 public class TestHTTPServer  extends HttpServlet{
     private final static Logger logger = LoggerFactory.getLogger(TestHTTPServer.class);
     private static final long serialVersionUID = 1L;
 
//     public void doGet(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
// 
//         PrintWriter out = response.getWriter();
//         String type = request.getParameter("type");
//         System.out.println("type=" + type);
//         out.close();
//     }
 
     public void doPost(HttpServletRequest request, HttpServletResponse response)
             throws ServletException, IOException {
    	 logger.info("----进入server端dopost方法-----");
    	//获得输入流
    	 InputStream in= request.getInputStream();
    	 FileOutputStream outlog=new FileOutputStream("d:\\test.log");
    	 PrintWriter out = response.getWriter();
         
           
           in = new BufferedInputStream(in);
           Reader rData = new InputStreamReader(in);
           String s = in.toString();
           int c;
           while((c=rData.read()) != -1)
              out.print((char)c);
           //得到参数，调用方法
           out.close();
           in.close();
     }
 }