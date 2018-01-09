/**
 * Project Name:wj
 * File Name:PartnerInfo.java
 * Package Name:cn.com.sinosoft.dao
 * Date:2016年8月16日上午9:19:58
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.dao;

import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.Partnerinfo;

/**
 * ClassName:PartnerInfo <br/>
 * Function:合作商户DAO层. <br/>
 * Date:2016年8月16日 上午9:19:58 <br/>
 *
 * @author:xialianfu
 */
public interface PartnerInfoDao extends BaseDao<Partnerinfo, String> {

	/**
	 * 
	 * selectPartnerInfoByPaySn:(通过交易流水号，关联sdorders表渠道编号channelsn，查询合作商户信息表集合). <br/>
	 *
	 * @author xialianfu
	 * @param strPaySn
	 * @return
	 */
	public List<Partnerinfo> selectPartnerInfoByPaySn(String strPaySn);
	
	
	/**
	 * 
	 * selectDetailByPaySn:(通过交易流水号，关联sdorders表渠道编号channelsn，查询合作商户信息表集合MAP). <br/>
	 *
	 * @author xialianfu
	 * @param strPaySn
	 * @return
	 */
	public List<Map<String, Object>> selectDetailByPaySn(String strPaySn);

}
