package com.sinosoft.jdt.cc;

import java.util.Map;

/**
 * ClassName: CCDealInterface <br/>
 * Function: 理财险接口. <br/>
 * date: 2016年5月25日 上午10:41:45 <br/>
 *
 * @author wwy
 * @version 
 */
public interface CCDealInterface
{
	/**
	 * cardCheck:小额验签. <br/>
	 *
	 * @author wwy
	 * @param resultMap
	 * @return
	 */
	public Map<String, Object> cardCheck(Map <String, Object> resultMap);
	
	/**
	 * canceltry:退保. <br/>
	 *
	 * @author wwy
	 * @param resultMap
	 * @return
	 */
	public Map<String, Object> canceltry(Map <String, Object> resultMap);
}
