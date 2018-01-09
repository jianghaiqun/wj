package cn.com.sinosoft.common;

import cn.com.sinosoft.entity.Member;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.QueryBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 拦截器 - 判断会员是否登录
 * ============================================================================
 * 
 * 
 * 
 * 
 * 
 * 
 * KEY:SINOSOFTCF908840E8D9DB3A559EB83A2FE5F57A
 * ============================================================================
 */

public class FinancingInterceptor extends AbstractInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(FinancingInterceptor.class);
	private static final long serialVersionUID = -86246303854807787L;

	String[] check_action = { "buyNow", "buyNowUpdate", "sendDirectUrl", "pay" };

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		String loginMemberId = (String) invocation.getInvocationContext().getSession()
				.get(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		String method = invocation.getProxy().getMethod();
		logger.info("进入拦截器，会员id：{}", loginMemberId);
		if (loginMemberId == null) {
			String queryString = request.getQueryString();
			// 拦截百年理财险
			if (!StringUtils.isEmpty(queryString)&& (queryString.indexOf("productId") != -1 || queryString.indexOf("orderSn") != -1)&& Arrays.asList(check_action).contains(method)) {
				String[] para = queryString.split("&");
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < para.length; i++) {
					String[] pars = para[i].split("=");
					if (pars != null && pars.length > 1) {
						map.put(pars[0], pars[1]);
					}
				}
				// 产品id
				String proid = map.get("productId");
				// 订单号查询产品id
				String osn = map.get("orderSn");
				if (!StringUtils.isEmpty(proid) || !StringUtils.isEmpty(osn)) {
					if (!StringUtils.isEmpty(osn)) {
						String sql = "SELECT productid FROM sdinformation WHERE ordersn  = '" + osn + "' ";
						QueryBuilder qb = new QueryBuilder(sql);
						proid = qb.executeString();
					}
					if (!StringUtils.isEmpty(proid)) {
						String islcx = FinancingCheck.getLcx(proid);
						if ("0".equals(islcx)) {
							GetDBdata db = new GetDBdata();
							String FrontServerContextPath = db.getOneValue("select value from zdconfig where type='FrontServerContextPath'");
							String redirectionUrl = FrontServerContextPath + request.getRequestURI();
							if (StringUtils.isNotEmpty(queryString)) {
								redirectionUrl += "?" + queryString;
							}
							request.getSession().setAttribute(Member.LOGIN_REDIRECTION_URL_SESSION_NAME, redirectionUrl);
							logger.info("进入拦截器，回调地址：{}", redirectionUrl);
							return Action.LOGIN;
						}
					}
				}
			}
		}
		return invocation.invoke();
	}

}