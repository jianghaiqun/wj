package cn.com.sinosoft.util;

import com.sinosoft.framework.utility.StringUtil;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class WapErrorUtil {
	private static final Logger logger = LoggerFactory.getLogger(WapErrorUtil.class);

    private static Properties props = new Properties();
    

    static {
	try {
	    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("waperrorinfos.properties");
	    InputStreamReader rd = new InputStreamReader(is, "UTF-8");
	    props.load(rd);
	   // props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("waperrorinfos.properties"));
	} catch (IOException e) {
		logger.error(e.getMessage(), e);
	}
    }


    /**
     * @Method: dealErrorInfo
     * @Description:  统一的错误返回方法
     * @param: errCodes  非空 ,错误编码 
     * @param: cRequestType 接口名称编码
     * @param: resultsMap Map<String,Object>    RESULTS 对应的返回信息,由接口规范而定,可以为 null
     * @throws:
     * @MakeDate:2014-2-20 : 上午09:50:02
     */
    public static Map<String, Object> dealErrorInfo(List<String> errCodes,String cRequestType,Map<String, Object> resultsMap) {

	//ERRORS ---> value
	Map<String, Object> errorsMap = new HashMap<String, Object>();
	JSONArray errorsInfo = new JSONArray();
	String errorCode="";
	String errorMsg="";
	if(errCodes.size()>0){
	    for(String errCode : errCodes){
		String errInfo = props.get(errCode).toString();
		errorCode+=errCode+";";
		errorMsg+=errInfo+";";
		errorsMap.put(errCode, errInfo);
		errorsInfo.put(errorsMap);
	    }
	}
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	resultMap.put("USER", "kxb");
	resultMap.put("REQUESTTYPE", cRequestType);
	resultMap.put("STATYS", "false");
	resultMap.put("RESULTS", resultsMap);
	resultMap.put("ERRORSKEY", errorCode);
	resultMap.put("ERRORSMSG", errorMsg);
	return resultMap;
    }
    
    /**
 	 * 不需要返回结果数据的异常处理 
     * @param arr 错误编码数组
     * @param cRequestType 请求类型
     * @return
     */
    public static Map<String, Object> dealErrorInfo(String[] arr,
			String cRequestType) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		JSONArray errorsInfo = new JSONArray();
		String errorCode="";
		String errorMsg="";
		if (arr.length >= 1) {
			for (int i = 0; i < arr.length; i++) {
				Map<String, Object> errorMap = new HashMap<String, Object>();
				String error = props.get(arr[i]).toString();
				if(StringUtil.isEmpty(error)){
					error = "未知错误，请联系客服！";
				}
				errorCode+=arr[i]+";";
				errorMsg+=error+";";
				errorMap.put(arr[i], error);
				errorsInfo.put(errorMap);
			}
		} else {
			return null;
		}

		resultMap.put("USER", "kxb");
		resultMap.put("REQUESTTYPE", cRequestType);	
		resultMap.put("STATYS", "false");
		resultMap.put("ERRORSKEY", errorCode);
		resultMap.put("ERRORSMSG", errorMsg);
		return resultMap;
	}
    
    /**
     * @param args
     */
    public static void main(String[] args) {
	// TODO Auto-generated method stub
	/*
	 * try { props.load(Thread.currentThread().getContextClassLoader()
	 * .getResourceAsStream("waperrorinfos.properties"));
	 * System.out.println(props.get("A00002")); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); }
	 */
	//System.out.println(props.get("A00001"));
	
	String errInfo = "错误信息";
	String cRequestType = "KXBJRT0008";
	Map<String, Object> errResultInfo = null;
	
	ArrayList<String> errCodes = new ArrayList<String>();
	errCodes.add("wapShopping00002");
	Map aa = dealErrorInfo(errCodes, cRequestType, null);
	
//	System.out.println(aa);
	
	
    }

}
