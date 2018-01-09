package cn.com.sinosoft.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsuranceOrderCall {

	private final static Logger logger = LoggerFactory.getLogger(InsuranceOrderCall.class);

	public static void main(String[] args) throws Exception{
	    try {
		    //接口url地址
		    URL url = new URL("http://127.0.0.1/httpServer?type=tradeParter");
		    
		    HttpURLConnection g_URLConnection = (HttpURLConnection) url.openConnection();
		    g_URLConnection.setRequestProperty("content-type", "text/html;charset=utf-8");  
		    g_URLConnection.setDoOutput(true);
		    g_URLConnection.setDoInput(true);
		    
		    //打开文件,该文件是请求报文.
		    FileInputStream fis = new FileInputStream("D:/InsuranceConnectMessage.xml");
		    String byteTemp = fis.toString();
		    
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

		    // ThreeDES是加密类
		    ThreeDES td = new ThreeDES();
		    String s = new String(bos.toByteArray());
		    s = td.createEncryptor(s);
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
		    str = td.createDecryptor(str);
			logger.info("解密后返回报文：{}", str);
		} catch (Exception e) {
	    	logger.error(e.getMessage());
		}
}
}
