package com.sinosoft.cms.seo;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SeoDataSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 处理HTML内容.
 * 
 * @author congzn.
 * @date 2013-10-29
 */
public class HandleHTML{
	private static final Logger logger = LoggerFactory.getLogger(HandleHTML.class);

	private static int beginIndex = 0;

	// private static int jl= 0;

	/**
	 * 解析HTML.
	 * 
	 * @param str
	 */
	public static void getContent(String content, String type,String url) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Object[] argArr = {url, type, content.length()};
		logger.info("开始解析 URL: {}|类型: {}|HLTML字符数: {}", argArr);
		if (InitSeo.isAnnotationHandle) {
			content = annotationHandle(content);
		}

		String p4 = InitSeo.getConfigValue("p4_tag", type).toString();

		if (content.indexOf(p4) != -1) {

			String[] strArr = content.split(p4);

			// 分离开后循环取值
			for (int i = 0; i < strArr.length; i++) {
				if (isOnlyOneTag(strArr[i], type)) {
					list.add(parsingString(strArr[i], type));
				}
			}

		}
		if(list.size() > 0){
			saveData(list, type, url);
		}else{
			logger.info("*****************解析HTML异常*******************");
			logger.info("URL：{}", url);
			logger.info("*********************END************************");
			//UrlQueue.addElem(url); 重新加入列队
		}
		

	}

	/**
	 * 解析字符串.
	 * 
	 * @param str
	 */
	private static HashMap<String, String> parsingString(String str, String type) {
		
		HashMap<String, String> hm = new HashMap<String, String>();
		String[] strArr = (String[]) InitSeo.getConfigValue("p7_property", type);
		String p4 = InitSeo.getConfigValue("p4_tag", type).toString();
		if ("1".equals(type) && str.indexOf(p4) != -1) {
			str = str.substring(str.indexOf(p4), str.length());
		}

		for (int i = 0; i < strArr.length; i++) {

			String h = InitSeo.getConfigValue("h" + (i + 1), type).toString();
			// 暂时注释.数据可能存在误差
			// if(i == 0 && jl == 0){
			// jl = 1;
			// str =
			// str.substring(str.indexOf("<td rowspan=50 valign=\"top\">"),
			// str.indexOf(h));
			// }
			String resStr = processingRule(h, str);
			str = str.substring(beginIndex, str.length());
			hm.put(strArr[i], resStr);
		}

		return hm;
	}

	/**
	 * 解析字符串规则. 按照HTML语法规则.
	 * 
	 * @param str
	 * @return
	 */
	private static String processingRule(String indexOfStr , String str) {
		int length = str.length();
		String result = null;
		if (str.indexOf(indexOfStr) != -1) {
			String temp = str.substring(str.indexOf(indexOfStr)+indexOfStr.length(), length);
			result = temp.substring(temp.indexOf(">") + 1, temp.indexOf("</"));
			beginIndex = str.length()-temp.length();
		}

		return result;
	}

	/**
	 * 替换注释.
	 * @param str
	 * @return
	 */
	private static String annotationHandle(String str) {

		StringBuffer sb = new StringBuffer();
		String begin = "<!--";
		String end = "-->";

		while (str.indexOf(begin) != -1) {
			String t = str.substring(0, str.indexOf(begin));
			sb.append(t.trim());
			String t1 = str.substring(str.indexOf(begin), str.length());
			String t2 = t1.substring(t1.indexOf(end) + 3, t1.length());
			str = t2.trim();
		}
		
		sb.append(str);
		return sb.toString();
	}

	/**
	 * 是否包含唯一标记. p5_TheOnlyTag_baidu p5_TheOnlyTag_360
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isOnlyOneTag(String str, String type) {
		String p5 = InitSeo.getConfigValue("p5_TheOnlyTag", type).toString();
		if (!StringUtil.isEmpty(p5) && str.indexOf(p5) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * 保存数据.
	 * @param list
	 * @param type
	 */
	private static void saveData(List<HashMap<String, String>> list, String type,String url) {
		Transaction ts = new Transaction();
		
		String page = subPage(url);
		
		String[] strArr = (String[]) InitSeo.getConfigValue("p7_property", type);
		SeoDataSchema sd = null;
		
		QueryBuilder qb = new QueryBuilder("delete from seodata where page = ? and createdate = ? and type = ? ");
		qb.add(page);
		qb.add(InitSeo.seo_date);
		qb.add(type);
		ts.add(qb);
		logger.info("date:{}|type:{}", InitSeo.seo_date, type);
		// keyWord,Ranking,Searches,theNumber,Title
		String value = null;
		for (int i = 0; i < list.size(); i++) {
			sd = new SeoDataSchema();
			sd.setid(NoUtil.getMaxNo(""));
			for (int j = 0; j < strArr.length; j++) {
				value = list.get(i).get(strArr[j]);
				switch (j) {
				case 0:
					sd.setkeyWord(value);
					break;
				case 1:
					sd.setranking(value);
					break;
				case 2:
					sd.setsearches(value);
					break;
				case 3:
					if("1".equals(type)){
						sd.settheNumber(value);
					}
					else if("2".equals(type)){
						sd.setKR(value);
					}
					break;
				case 4:
					sd.settitle(SpecialChar(value));
					break;
				case 5:
					sd.setprop1(value);
					break;
				case 6:
					sd.setprop2(value);
					break;
				default:
					break;
				}
			}
			sd.settype(type);
			sd.setpage(page);
			sd.setcreateDate(InitSeo.seo_date);
			ts.add(sd, Transaction.INSERT);
		}
		ts.commit();
		
	}
	
	/**
	 * 特殊字符处理（影响CMS分页）.
	 * ....处理.
	 * @param str
	 * @return
	 */
	public static String SpecialChar(String str){
		if(str.lastIndexOf("..") != -1){
				str = str.substring(0,str.lastIndexOf("..")-3);
		}
		return str;
	}
	
	/**
	 * 根据URL截取当前页码.
	 * @param str
	 * @return
	 */
	public static String subPage(String str){
		String res = null;
		int index = str.lastIndexOf("/position/");
		String temp  = str.substring(0,index);
		res = temp.substring(temp.lastIndexOf("/")+1,temp.length());
		return res;
	}

}
