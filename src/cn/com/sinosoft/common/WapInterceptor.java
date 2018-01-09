package cn.com.sinosoft.common;

import cn.com.sinosoft.action.shop.BaseWapShopAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截器 - 去除页面参数字符串两端的空格
 * ============================================================================
 *
 *
 *
 *
 *
 *
 * KEY:SINOSOFTF08210E5AFEDF7D9575CD16EF7D4704D
 * ============================================================================
 */

public class WapInterceptor extends AbstractInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(WapInterceptor.class);
    private static final long serialVersionUID = 2365641900033439481L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
        Object requestType = null ;
        String gName = "";
        String gPassWord = "";
        JSONObject jsonObject = null;
        HashMap<String, Object> map = new HashMap<String, Object>();
        try{
            String[] valueArray = (String[]) parameters.get("GNAME");
            gName = valueArray[0];
            valueArray = (String[]) parameters.get("GPARSSWORD");
            gPassWord = valueArray[0];
            valueArray = (String[]) parameters.get("jsonRequest");
            jsonObject = new JSONObject(valueArray[0]);
            requestType = jsonObject.get("REQUESTTYPE");

            for (String key : parameters.keySet()) {
                Object value = parameters.get(key);
                if (value instanceof String[]) {
                    String[] valueArray1 = (String[]) value;
                    for (int i = 0; i < valueArray.length; i++) {
                        valueArray1[i] =StringUtil.trim(valueArray1[i]);
                    }
                    parameters.put(key, valueArray1);
                }
            }
            parameters.remove("GNAME");
            parameters.remove("GPARSSWORD");
            try{
                //校验约定用户名&密码
                if(!BaseWapShopAction.GNAME.equals(gName) || !BaseWapShopAction.GPASSWORD.equals(gPassWord)){
                    map.put("REQUESTTYPE", requestType);
                    map.put("STATYS", "false");
                    JSONArray arr = new JSONArray();
                    Map<String,Object> errorMap = new HashMap<String,Object>();
                    errorMap.put("error", "约定用户名或密码错误，请核实！");
                    arr.put(errorMap);
                    //map.put("ERRORS", arr);
                    map.put("ERRORSKEY", "");
                    map.put("ERRORSMSG", "约定用户名或密码错误，请核实！");
                    return ajaxJson(map);
                }
                //校验约定请求类型
                if(!BaseWapShopAction.requestTypeMap.keySet().contains(requestType)){
                    map.put("REQUESTTYPE", requestType);
                    map.put("STATYS", "false");
                    JSONArray arr = new JSONArray();
                    Map<String,Object> errorMap = new HashMap<String,Object>();
                    errorMap.put("error", "约定请求类型错误，请核实！");
                    arr.put(errorMap);
                    //map.put("ERRORS", arr);
                    map.put("ERRORSKEY", "");
                    map.put("ERRORSMSG", "约定请求类型错误，请核实！");
                    return ajaxJson(map);
                }
            }catch (Exception e) {
                logger.error("struts2拦截器异常!" + e.getMessage(), e);
            }
            String actionResultString = invocation.invoke();
            //System.out.println("wap返回-----"+actionResultString);
            // 重复提交则不记录日志
            if ("invalid.token".equalsIgnoreCase(actionResultString))
            {
                return actionResultString;
            } else{
                return null;
            }
        }catch(Exception e){
            logger.error("wap站解析参数错误-"+e.getMessage(), e);
            map.put("REQUESTTYPE", requestType);
            map.put("STATYS", "false");
            JSONArray arr = new JSONArray();
            Map<String,Object> errorMap = new HashMap<String,Object>();
            errorMap.put("error", "wap站解析参数错误，请检查参数:"+e.getMessage());
            arr.put(errorMap);
            //map.put("ERRORS", arr);
            map.put("ERRORSKEY", "");
            map.put("ERRORSMSG", "wap站解析参数错误，请检查参数:"+e.getMessage());
            return ajaxJson(map);

        }

    }
    // 根据Map输出JSON，返回null
    public String ajaxJson(Map<String, Object> jsonMap) {
        JSONObject jsonObject = new JSONObject(jsonMap);
        return ajax(jsonObject.toString(), "text/html");
    }
    // AJAX输出，返回null
    public String ajax(String content, String type) {
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType(type + ";charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


}
