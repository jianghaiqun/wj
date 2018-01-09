package cn.com.sinosoft.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.SDDeliverAddressDao;
import cn.com.sinosoft.entity.SDDeliverAddress;

/**
 * 
 * Dao实现类 - 邮寄地址信息
 *
 */

@SuppressWarnings("unchecked")
@Repository
public class SDDeliverAddressDaoImpl extends BaseDaoImpl<SDDeliverAddress, String> implements SDDeliverAddressDao {

	@Override
	public List<SDDeliverAddress> getSDDeliverAddressInfo(String memberId) {
		String hql = "from SDDeliverAddress as addr where addr.memberId=? order by isDefault desc, modifyDate desc ";
		List<SDDeliverAddress> tSDDeliverAddress = new ArrayList<SDDeliverAddress>();
		tSDDeliverAddress = (List<SDDeliverAddress>) getSession().createQuery(hql).setParameter(0,memberId).list();
		return tSDDeliverAddress;
	}

	/*@Override
	public SDDeliverAddress getSDDeliverAddressById(String addrId) {
		String hql = "from SDDeliverAddress as addr where addr.id=? ";
		SDDeliverAddress tSDDeliverAddress = new SDDeliverAddress();
		tSDDeliverAddress = (SDDeliverAddress) getSession().createQuery(hql).setParameter(0,addrId).uniqueResult();
		return tSDDeliverAddress;
	}*/
}