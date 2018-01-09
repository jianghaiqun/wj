package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.service.MailService;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@ParentPackage("shop")
public class SendMailAction extends BaseShopAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5113874148518719252L;
	@Resource
	private MailService mailService;

	public void deal() {
		try {
//			System.out.println("************************1111111111111111");
			HttpServletRequest request = getRequest();
//			Map map = request.getParameterMap();
//			Set<Map.Entry<String, Object>> set = map.entrySet();
//			for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
//				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
//				System.out.println("***:" + entry.getKey() + "-" + entry.getValue());
//			}

			
			Map<String, Object> data = new HashMap<String, Object>();
			String subject=request.getParameter("subject");
			String templateFilePath=request.getParameter("templateFilePath");
			String toMail=request.getParameter("toMail");
			Map requestParams = request.getParameterMap();
			for (Object m : requestParams.keySet()) {
				if(requestParams.get(m)!=null){
					if(requestParams.get(m).getClass().getName().equals("java.lang.String")){
						if (StringUtil.isNotEmpty((String) data.get(m))) {
							requestParams.put(String.valueOf(m), java.net.URLDecoder.decode((String) requestParams.get(m), "utf8"));
						}
					}
				}
			}
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				//System.out.println("***:" + name + "-" + valueStr);
				if("path".equals(name)){
					valueStr=valueStr.replace("#", "&");
				} 
				if(name.equals("MemberName")){
//					System.out.println(valueStr+"*************开始");
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
//					System.out.println(valueStr+"*************结束");
				}
				if(name.equals("policyNo")){
//					System.out.println(valueStr+"*************开始");
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
//					System.out.println(valueStr+"*************结束");
				}
				if(name.equals("ToName")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("ProductName")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("ApplicantName")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("UserName")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("InsuredSn")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("InsuredName")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("InsuranceCompany")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("FileName")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("sdn")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("direction")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("direction")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("direction")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("direction")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("title")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("name")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("sex")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("content")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("ErrMsg")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				if(name.equals("detail")){
					valueStr = java.net.URLDecoder.decode(valueStr,"utf8");
				}
				data.put(name, valueStr);
			}
			//邮件样式修改给${front}赋值，临时方法
			GetDBdata db = new GetDBdata();
			String sql ="select Value from zdconfig where Type = ?";
			String[] temp = {"FrontServerContextPath"};
			try {
				String result = db.getOneValue(sql, temp);
				data.put("front", result);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}		
			mailService.sendMail(subject, templateFilePath, data, toMail);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
