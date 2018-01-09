package cn.com.sinosoft.common;

import cn.com.sinosoft.entity.OrderItem;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SzOrderSynchro {
	private final static Logger logger = LoggerFactory.getLogger(SzOrderSynchro.class);
	/** 加密算法 */
	private  String Algorithm = "DESede";
	/**   
     * 订单同步接口
     *    
     * @param
     * @param
     */  
	public String postHttp(HttpServletRequest request, HttpServletResponse response,OrderItem orderItem) throws Exception{
		String responseMsg = "";
		try {
		SzPayUtil szPayUtil = new SzPayUtil();
		HttpURLConnection conn = null;
		String secKey = "xaygwodcjzulmrvhkbfpqsnt"; //移动商城登录用密钥
		JSONObject resMsg = new JSONObject();
		//生成int类型，随机数
		String noncestr = szPayUtil.getNonce();
		//时间戳
		String timestampstr = szPayUtil.getTimestamp();
	    //客户端唯一标识码
	    String ckey = "insurance";
	    //签名串
//	    String signature = ckey+"&"+nonceStr+"&"+timestamp;
	    String orderSn = orderItem.getOrder().getOrderSn();//订单编号
	    String proPrice = orderItem.getProductPrice().toString();//单价
	    String proName = orderItem.getProductName(); //商品名称
	    String totalAmount = orderItem.getOrder().getTotalAmount().toString();//订单总额
	    String proQuantity = orderItem.getProductQuantity().toString();//商品数量
	    String proUrl = orderItem.getProductHtmlFilePath();//商品
//	    String buyerMoblie = orderItem.getOrder().getMember().getUsername();//手机号，在用户表中是username
//	    String szUserId = orderItem.getOrder().getMember().getSzMemberUserId();
	    String merchantName = URLEncoder.encode("中元保险", "UTF-8");
	    String merchantUrl= URLEncoder.encode("http://zs.12580777.com:8080/index_sz.html", "UTF-8");
	    String baseStr = "";
	 	//加密手机号
//	    String moblieDes = DesEncrpty(buyerMoblie,"csecret");
	    baseStr = ""; 
//	    		"ckey="+ckey+"&timestamp="+timestampstr+"&nonce="+noncestr+"&partnersOrderId="+orderSn+"&merchantName="+
//	    merchantName+"&merchantId=2108882"+"&merchantUrl="+merchantUrl+"&price="+proPrice+"&fare=0.00"+"&amount="+totalAmount+"&maxCoin=1000&couponFlag=false&productName="+
//	    URLEncoder.encode(proName, "UTF-8")+"&productNum="+proQuantity+"&imageUrl=1&showUrl="+URLEncoder.encode("http://zs.12580777.com:8080/index_sz.html", "UTF-8")+"&buyerMoblie="+moblieDes+"&buyerId="+szUserId;
	    logger.info("源串===>{}", baseStr);
	    resMsg.put("ckey", ckey);
	    resMsg.put("timestamp", timestampstr);
	    resMsg.put("nonce", noncestr);
	    resMsg.put("ckey", ckey);
	    resMsg.put("partnersOrderId", orderSn);//第三方业务平台订单编号
	    resMsg.put("merchantName", URLEncoder.encode("中元保险", "UTF-8"));//商户名称URLEncoder.encode(s, "UTF-8")
	    resMsg.put("merchantId", "2108882");//商户id
	    resMsg.put("merchantUrl", URLEncoder.encode("http://zs.12580777.com:8080/index_sz.html", "UTF-8"));//URLEncoder.encode(s, "UTF-8")
	    resMsg.put("price", proPrice);//单价
	    resMsg.put("fare", "0.00");//运费
	    resMsg.put("amount", totalAmount);//支付总额
	    resMsg.put("maxCoin", "1000.00");//商城币限额
	    resMsg.put("couponFlag", "false");//是否需要抵用券
	    resMsg.put("productName", URLEncoder.encode(proName, "UTF-8"));//商品名称
	    resMsg.put("productNum", proQuantity);//商品数量
	    resMsg.put("imageUrl", URLEncoder.encode("1", "UTF-8"));//商品图片url
	    resMsg.put("showUrl", URLEncoder.encode(proUrl, "UTF-8"));//商品详情url
//	    resMsg.put("buyerMoblie", buyerMoblie);//用户手机号(接收密码) 需根据附件二的规则进行加密
//	    resMsg.put("buyerId", szUserId);//购买人的ID(必填)
	    resMsg.put("buyerId", 962516);
	    
	  	  
		logger.info("签名前的报文：{}", baseStr);
		String sign_ = szPayUtil.doSign(baseStr, secKey);
		logger.info("签名后的报文：{}", sign_);
		resMsg.put("signature", sign_);
		logger.info("request: {}", resMsg.toString());
//	    URL url = new URL("http://127.0.0.1:8080/httpServer"); 
	    URL url = new URL("http://www.12580777.com/partners/orderSync.do");
	    conn = (HttpURLConnection)url.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setDoOutput(true); 
	    conn.setDoInput(true);
	    conn.setRequestProperty("Charset", "UTF-8");
	    OutputStream buf = conn.getOutputStream();
	    buf = new BufferedOutputStream(buf);
	    OutputStreamWriter out = new  OutputStreamWriter (buf);
	    out.write(resMsg.toString());//json串
	    out.flush();
	    out.close();
	    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
             logger.info( "connect failed!");
	        //接收数据
	         InputStream in = conn.getInputStream();
	         in = new BufferedInputStream(in);
	         InputStreamReader rData = new InputStreamReader(in);
	         BufferedReader br=new BufferedReader(rData); //提供缓冲读取  提高速度
	         String str=br.readLine() ;//读取一行
	         logger.info("===返回的Json串==={}==========", str);
	         Map<String, String> map = new HashMap<String, String>();
	         map =  getJsonBackStr(str);//返回报文
	         String flag = map.get("flag").toString();
	         String msg = map.get("msg").toString();
	         String cartId = map.get("cartId").toString();
	         in.close();
	         br.close();
	         }catch (Exception e) {
				logger.error("发送POST请求出现异常！{}", e.getMessage());
			 }
		
		return responseMsg;
	}
	
	
	/**   
     * 解析Json串  （登陆接口返回报文） 
     *    
     * @param String str json报文
     * @param key  加密使用的key   
     */   
	public Map getJsonBackStr(String str){
		JSONObject jsonBack = JSONObject.fromObject(str);
        Map<String, String> map = new HashMap<String, String>();
        try{
			map.put("flag",jsonBack.getString("flag"));//返回状态编码
			map.put("msg",URLEncoder.encode(jsonBack.getString("msg"), "UTF-8"));//消息
			map.put("cartId",jsonBack.getString("cartId"));//新移动商城订单标识
		}catch (Exception e) {
			logger.error("返回报文解析异常！{}" + e.getMessage());
		}
        
	    return map;
	}
	
    /**   
     * 生成签名数据   
     *    
     * @param data 待加密的数据   
     * @param key  加密使用的key   
     * @throws InvalidKeyException   
     * @throws NoSuchAlgorithmException   
     */    
    public String getSignature(String data,String key) throws Exception{  
    		String HMAC_SHA1 = "HmacSHA1"; 
    		byte[] keyBytes=key.getBytes();  
	        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);     
	        Mac mac = Mac.getInstance(HMAC_SHA1);     
	        mac.init(signingKey);     
	        byte[] rawHmac = mac.doFinal(data.getBytes());  
	        StringBuilder sb=new StringBuilder();  
	        for(byte b:rawHmac){  
	         sb.append(byteToHexString(b));  
	        }  
	        return sb.toString();     
    }  
      
    private String byteToHexString(byte ib){  
	     char[] Digit={  
	       '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'  
	     };  
	     char[] ob=new char[2];  
	     ob[0]=Digit[(ib>>>4)& 0X0f];  
	     ob[1]=Digit[ib & 0X0F];  
	     String s=new String(ob);  
	     return s;           
    }          
    /**   
     * 进行Base64编码   
     *    
     * @param String s   
     * @param key  加密使用的key   
     * @throws InvalidKeyException   
     * @throws NoSuchAlgorithmException   
     */  
    public String getBASE64(String s) {   
    	if (s == null) return null;   
    	return (new sun.misc.BASE64Encoder()).encode( s.getBytes() );   
    	}   
    	  
    	// 将 BASE64 编码的字符串 s 进行解码   
    public String getFromBASE64(String s) {   
    	if (s == null) return null;   
    	BASE64Decoder decoder = new BASE64Decoder();   
    	try {   
    	byte[] b = decoder.decodeBuffer(s);   
    	return new String(b);   
    	} catch (Exception e) {   
    	return null;   
    	}   
    }  
    
	/** Des 加密
	 * @param sourceCode
	 * @return
	 */
	public  String DesEncrpty(String sourceCode, String secKey) {
		byte[] encoded = encryptMode(secKey.getBytes(), sourceCode.getBytes());
		return byte2hex(encoded);
	}
	
	/**使用desede算法加密
	 * @param keybyte
	 * @param src
	 * @return
	 */
	public  byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		}
		catch (java.security.NoSuchAlgorithmException e1) {
			logger.error(e1.getMessage(), e1);
		}
		catch (javax.crypto.NoSuchPaddingException e2) {
			logger.error(e2.getMessage(), e2);
		}
		catch (java.lang.Exception e3) {
			logger.error(e3.getMessage(), e3);
		}
		return null;
	}
	
	public  String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}
}