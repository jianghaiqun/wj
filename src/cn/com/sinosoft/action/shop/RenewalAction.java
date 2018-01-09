package cn.com.sinosoft.action.shop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import cn.com.sinosoft.service.RenewalService;

import com.sinosoft.framework.utility.StringUtil;


@ParentPackage("shop")
public class RenewalAction extends BaseShopAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2713798732340284652L;

	/**
	 * 续保传入参数
	 */
	private Map<String, String> renewalMap = new HashMap<String, String>();
	
	/**
	 * 续保传出结果
	 */
	private Map<String, Object> rwResultsMap = new HashMap<String, Object>();
	/**
	 * 续保传入参数
	 */
	private String policyNo;
	
	@Resource
	private RenewalService renewalService;
	/**
	 * 
	 * 续保查询
	 * @return
	 */
	public void queryRenewal() {
		
		Map<String, String> map = new HashMap<String, String>();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		map.put("policyNo", policyNo);
		rwResultsMap = renewalService.queryRenewal(map);
		
		Map<String, Object> tData = new HashMap<String, Object>();
		if (rwResultsMap == null || rwResultsMap.size() == 0) {
			tData.put("Status", "1");
			tData.put("Msg", "系统出现异常导致查询失败!");
			
		}else {
			Map<String, Object> responseMap =(Map<String, Object>) rwResultsMap.get("head");
			
			if(StringUtil.isNotEmpty(policyNo)&&!"0".equals(policyNo)){
				
				if ("00".equals(responseMap.get("responseCode"))) {
					tData.put("Status", "0");
					tData.put("Msg", "成功");
				}else {
					tData.put("Status", "1");
					tData.put("Msg", responseMap.get("responseMessage"));
				}
			}else{
				tData.put("Status", "1");
				tData.put("Msg", "失败");
			}
			
		}
		
		JSONObject jsonObject  = JSONObject.fromObject(tData);
		try {
			response.getWriter().print(jsonObject.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	/**
	 * 续保
	 */
	public String renewal() {
		
		rwResultsMap = renewalService.renewal(renewalMap);
		ObjectMapper mapper = new ObjectMapper(); 
		String json = ""; 
		try {
			json = mapper.writeValueAsString(rwResultsMap);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	public static void main(String[] args) {
		/*RenewalAction renewalAction = new RenewalAction();
		renewalAction.queryRenewal();*/
	}
	public Map<String, String> getRenewalMap() {
		return renewalMap;
	}
	public void setRenewalMap(Map<String, String> renewalMap) {
		this.renewalMap = renewalMap;
	}
	public Map<String, Object> getRwResultsMap() {
		return rwResultsMap;
	}
	public void setRwResultsMap(Map<String, Object> rwResultsMap) {
		this.rwResultsMap = rwResultsMap;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	
	
}
