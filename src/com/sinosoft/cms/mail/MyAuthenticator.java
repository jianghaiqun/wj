package com.sinosoft.cms.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
 * @ClassName: MyAuthenticator
 * @Description: TODO(邮件发送校验身份)
 * @author CongZN.
 * @date 2014-8-27 上午10:32:54
 *
 * <p> 修改历史</p>
 * <p> 序号 日期 修改人 修改原因</p>
 */
public class MyAuthenticator extends Authenticator{
	
		String userName=null;
		String password=null;

	    public MyAuthenticator(String username, String password) {
	        this.userName = username;
	        this.password = password;
	    }
	    
	    protected PasswordAuthentication getPasswordAuthentication(){
	        return new PasswordAuthentication(userName, password);
	    }
}