package com.sinosoft.framework.utility.weixin.bean;
/** 
 * 微信通用接口凭证 
 *  
 * @author  
 * @date 2014-06-11 
 */  
public class AccessToken {  
    // 获取到的凭证  
    private String token;  
    // 凭证有效时间，单位：秒  ,一般为7200秒，即2小时
    private int expiresIn;  
  
    public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }  
  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }  
}  