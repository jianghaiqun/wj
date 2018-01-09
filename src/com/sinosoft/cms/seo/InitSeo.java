package com.sinosoft.cms.seo;

import com.sinosoft.framework.ResponseImpl;
import com.sinosoft.framework.messages.LongTimeTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

/**
 * 初始化.
 * 
 * @author congzn
 * 
 */
public class InitSeo {
	private static final Logger logger = LoggerFactory.getLogger(InitSeo.class);

	/**
	 * 是否去除注释.
	 */
	public static boolean isAnnotationHandle = true;
	
	public static int newPage;

	private final static String u1 = "baidu";
	private final static String u2 = "360";

	private static String p1_url_baidu;
	private static String p1_url_360;

	private static String p2_begin_baidu;
	private static String p2_begin_360;

	public static String p3_end_baidu;
	public static String p3_end_360;

	private static String p4_tag_baidu;
	private static String p4_tag_360;

	private static String p5_TheOnlyTag_baidu;
	private static String p5_TheOnlyTag_360;

	private static String p6_url_360;
	
	private static String[] p7_properties_baidu;
	private static String[] p7_properties_360;

	private static String p8_sleepTime_baidu;
	private static String p8_sleepTime_360;

	private static String h1_baidu;
	private static String h2_baidu;
	private static String h3_baidu;
	private static String h4_baidu;
	private static String h5_baidu;
	// 备用字段
	private static String h6_baidu;
	private static String h7_baidu;

	private static String h1_360;
	private static String h2_360;
	private static String h3_360;
	private static String h4_360;
	private static String h5_360;
	// 备用字段
	private static String h6_360;
	private static String h7_360;
	
	public static String seo_date;
	
	public static ResponseImpl response;

	private static HashMap<String, Object> parameMap = new HashMap<String, Object>();

	static {
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			seo_date = sdf.format(date);

			Properties props = new Properties();
			InputStream inputStream = InitSeo.class.getResourceAsStream("/SEO.properties");
			props.load(inputStream);

			p1_url_baidu = props.getProperty("p1_url_baidu");
			p1_url_360 = props.getProperty("p1_url_360");
			parameMap.put("p1_url_baidu", p1_url_baidu);
			parameMap.put("p1_url_360", p1_url_360);

			p2_begin_baidu = props.getProperty("p2_begin_baidu");
			p2_begin_360 = props.getProperty("p2_begin_360");
			parameMap.put("p2_begin_baidu", p2_begin_baidu);
			parameMap.put("p2_begin_360", p2_begin_360);

			p3_end_baidu = props.getProperty("p3_end_baidu");
			p3_end_360 = props.getProperty("p3_end_360");
			parameMap.put("p3_end_baidu", p3_end_baidu);
			parameMap.put("p3_end_360", p3_end_360);

			p4_tag_baidu = props.getProperty("p4_tag_baidu");
			p4_tag_360 = props.getProperty("p4_tag_360");
			parameMap.put("p4_tag_baidu", p4_tag_baidu);
			parameMap.put("p4_tag_360", p4_tag_360);

			p5_TheOnlyTag_baidu = props.getProperty("p5_TheOnlyTag_baidu");
			p5_TheOnlyTag_360 = props.getProperty("p5_TheOnlyTag_360");
			parameMap.put("p5_TheOnlyTag_baidu", p5_TheOnlyTag_baidu);
			parameMap.put("p5_TheOnlyTag_360", p5_TheOnlyTag_360);

			p6_url_360 = props.getProperty("p6_url_360");
			parameMap.put("p6_url_360", p6_url_360);

			p7_properties_baidu = props.getProperty("p7_properties_baidu")
					.split(",");
			p7_properties_360 = props.getProperty("p7_properties_360").split(
					",");

			p8_sleepTime_baidu = props.getProperty("p8_sleepTime_baidu");
			p8_sleepTime_360 = props.getProperty("p8_sleepTime_360");

			parameMap.put("p7_property_baidu", p7_properties_baidu);
			parameMap.put("p7_property_360", p7_properties_360);

			h1_baidu = props.getProperty("h1_baidu");
			h2_baidu = props.getProperty("h2_baidu");
			h3_baidu = props.getProperty("h3_baidu");
			h4_baidu = props.getProperty("h4_baidu");
			h5_baidu = props.getProperty("h5_baidu");

			h6_baidu = props.getProperty("h6_baidu");
			h7_baidu = props.getProperty("h7_baidu");

			parameMap.put("h1_baidu", h1_baidu);
			parameMap.put("h2_baidu", h2_baidu);
			parameMap.put("h3_baidu", h3_baidu);
			parameMap.put("h4_baidu", h4_baidu);
			parameMap.put("h5_baidu", h5_baidu);

			parameMap.put("h6_baidu", h6_baidu);
			parameMap.put("h7_baidu", h7_baidu);

			h1_360 = props.getProperty("h1_360");
			h2_360 = props.getProperty("h2_360");
			h3_360 = props.getProperty("h3_360");
			h4_360 = props.getProperty("h4_360");
			h5_360 = props.getProperty("h5_360");

			h6_360 = props.getProperty("h6_360");
			h7_360 = props.getProperty("h7_360");

			parameMap.put("h1_360", h1_360);
			parameMap.put("h2_360", h2_360);
			parameMap.put("h3_360", h3_360);
			parameMap.put("h4_360", h4_360);
			parameMap.put("h5_360", h5_360);

			parameMap.put("h6_360", h6_360);
			parameMap.put("h7_360", h7_360);

			logger.info("参数初始化完成.");
		} catch (IOException e) {
			logger.error("初始化参数异常:" + e.getMessage(), e);
		}

	}

	/**
	 * 手动执行抓取.
	 * 
	 * @param type
	 * @throws InterruptedException
	 */
	public static void execute(LongTimeTask lTT ,String type,ResponseImpl r) throws InterruptedException {
		response = r;
		if ("1".equals(type)) {
			executGrab(  lTT , p1_url_baidu, p2_begin_baidu, p3_end_baidu,
					p8_sleepTime_baidu, type, u1);
		} else if ("2".equals(type)) {
			executGrab(lTT , p1_url_360, p2_begin_360, p3_end_360, p8_sleepTime_360,
					type, u2);
		}
	}
	
	/**
	 * 定时任务-执行抓取.
	 * 
	 * @param type
	 * @throws InterruptedException
	 */
	public static void executeTiming(String type) throws InterruptedException {
		
		if ("1".equals(type)) {
			executGrab(null ,p1_url_baidu, p2_begin_baidu, p3_end_baidu,
					p8_sleepTime_baidu, type, u1);
		} else if ("2".equals(type)) {
			executGrab(null ,p1_url_360, p2_begin_360, p3_end_360, p8_sleepTime_360,
					type, u2);
		}
	}

	/**
	 * 执行抓取.
	 * 
	 * @param url
	 * @param begin
	 *            起始位置.
	 * @param end
	 *            结束位置.
	 * @param sleep
	 *            线程等待.
	 * @param type
	 *            类型.
	 * @throws NumberFormatException
	 * @throws InterruptedException
	 */
	private static void executGrab(LongTimeTask lTT , String url, String begin, String end,
			String sleep, String type, String sign)
			throws NumberFormatException, InterruptedException {

		newPage= Integer.parseInt(end) - Integer.parseInt(begin) + 1;

		/**************多线程Begin****************/
//		UrlDataHanding[] url_Handings = new UrlDataHanding[newPage];

//		for (int i = 0; i < url_Handings.length; i++) {
//			int pageCount = Integer.parseInt(begin) + i;
//			UrlQueue.addElem(MessageFormat.format(url, pageCount));
//			url_Handings[i] = new UrlDataHanding();
//			url_Handings[i].setType(type);
//			
//			Thread.sleep(Integer.parseInt(sleep));
//			LogUtil.info(sign + "线程开启 等待时间(毫秒):" + sleep+"|pageCount:"+pageCount+"|UrlQueue.size:"+UrlQueue.size());
//			new Thread(url_Handings[i]).start();
//			if(Integer.parseInt(p3_end_baidu) == pageCount){
//				LogUtil.info("********************抓取结束*******************!");
//				response.Status = 1;
//				return;
//			}
//		}
		/**************多线程End*****************/
		
		
		/**************未使用线程****************/
		UrlDataHanding udh = new UrlDataHanding();
		for (int i = 0; i < newPage; i++) {
			int pageCount = Integer.parseInt(begin) + i;
			String newURL = MessageFormat.format(url, pageCount);
			Thread.sleep(Integer.parseInt(sleep));
			Object[] argArr = {sign, sleep, pageCount};
			logger.info("{}未使用线程-开始抓取 等待时间(毫秒):{}|pageCount:{}", argArr);
			udh.dataHanding(newURL, type);
			if(lTT != null){
				lTT.setCurrentInfo("正在抓取(" + (i + 1) + "/" + newPage + ")" );
				lTT.setPercent(Integer.valueOf((i + 1) * 100 / (newPage + 1)));
			}
		}
		return;
	}
	
	/**
	 * 通过参数和类型 返回VALUE
	 * 
	 * @param parame
	 * @param type
	 * @return
	 */
	public static Object getConfigValue(String parame, String type) {
		Object resStr = null;
		if ("1".equals(type)) {
			resStr = parameMap.get(parame + "_" + u1);
		} else if ("2".equals(type)) {
			resStr = parameMap.get(parame + "_" + u2);
		}
		return resStr;
	}

}
