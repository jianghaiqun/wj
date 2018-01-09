package cn.com.sinosoft.action.shop;



public class AlipayLoginAction extends BaseShopAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4937197641273486971L;

	public String aouth() throws Exception{
		this.getRequest().getSession().setAttribute("backurl", this.getRequest().getParameter("loginBackURL"));
    	this.getRequest().getSession().setAttribute("comCode", "Alipay");

        
		return "login";
	}

}
