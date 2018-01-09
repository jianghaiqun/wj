package cn.com.sinosoft.action.shop;

import weibo4j.Oauth;
import weibo4j.util.BareBonesBrowserLaunch;


public class SinaLoginAction extends BaseShopAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4937197641273486971L;

	public String aouth() throws Exception{
		
		//"localhost:8080/index.shtml"
    	this.getRequest().getSession().setAttribute("backurl", this.getRequest().getParameter("loginBackURL"));
    	this.getRequest().getSession().setAttribute("comCode", "Sina");
		Oauth oauth = new Oauth();
		//BareBonesBrowserLaunch.openURL(oauth.authorize("code","",""));
		this.getResponse().sendRedirect(oauth.authorize("code","",""));
		
		/*System.out.println(oauth.authorize("code","",""));
		System.out.print("Hit enter when it's done.[Enter]:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String code = br.readLine();
		System.out.println("code : "+code);
		LogUtil.info("code: " + code);
		try{
			LogUtil.info(oauth.getAccessTokenByCode(code));
		} catch (WeiboException e) {
			if(401 == e.getStatusCode()){
				LogUtil.error("Unable to get the access token.");
			}else{
				e.printStackTrace();
			}
		}*/
		return null;
	}

}
