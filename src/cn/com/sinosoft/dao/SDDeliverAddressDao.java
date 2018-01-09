package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.SDDeliverAddress;


/**
 * Dao接口 - 邮寄地址
 *
 */
public interface SDDeliverAddressDao extends BaseDao<SDDeliverAddress, String> {
	public List<SDDeliverAddress> getSDDeliverAddressInfo(String memberId);
	
//	public SDDeliverAddress getSDDeliverAddressById(String addrId);
}