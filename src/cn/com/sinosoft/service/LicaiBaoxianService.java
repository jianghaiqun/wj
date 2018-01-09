package cn.com.sinosoft.service;

import java.util.Map;



/**
 * Service业务类 理财保险小额验签 小额验签查询 退保试算
 * ============================================================================
 *  
 * ============================================================================
 */

public interface LicaiBaoxianService {
	
	/**
	 * cardCheckSend: 小额验签发送. <br/>
	 *
	 * @author wwy
	 * @param param
	 * @return
	 */
	public Map<String, Object> cardCheckSend(Map<String, Object> param);
	
	/**
	 * cardCheckSearch:小额验签查询. <br/>
	 *
	 * @author wwy
	 * @param param
	 * @return
	 */
	public Map<String, Object> cardCheckSearch(Map<String, Object> param);
	
	/**
	 * canceltry:退保试算. <br/>
	 *
	 * @author wwy
	 * @param param
	 * @return
	 */
	public Map<String, Object> canceltry(Map<String, Object> param);
	
	/**
	 * checkBankInfo:(校验银行卡信息). <br/>
	 *
	 * @author wwy
	 * @param param
	 * @return
	 */
	public Map<String, Object> checkBankInfo(Map<String, Object> param);
	
	/**
	 * obtainVCode:调用百年接口获取验证码. <br/>
	 *
	 * @author wwy
	 * @param params
	 * @return
	 */
	public Map<String, Object> obtainVCode(Map<String, Object> params); 
	
	/**
	 * checkVCode:校验验证码. <br/>
	 *
	 * @author wwy
	 * @param params
	 * @return
	 */
	public Map<String, Object> checkVCode(Map<String, Object> params);
}