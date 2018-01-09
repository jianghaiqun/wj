package cn.com.sinosoft.action.shop;

import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;

import java.io.IOException;

public class TencentLoginAction extends BaseShopAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4937197641273486971L;

	public String aouth() throws IOException{
		 this.getResponse().setContentType("text/html;charset=utf-8");
	        try {
	        	//"localhost:8080/index.shtml"
	        	this.getRequest().getSession().setAttribute("backurl", this.getRequest().getParameter("loginBackURL"));
	        	this.getRequest().getSession().setAttribute("comCode", "Tencent");
	        	//配置跳转路径  *
	        	this.getResponse().sendRedirect(new Oauth().getAuthorizeURL(this.getRequest()));
	        } catch (QQConnectException e) {
				logger.error(e.getMessage(), e);
	        }
		
		return null;
	}

}
