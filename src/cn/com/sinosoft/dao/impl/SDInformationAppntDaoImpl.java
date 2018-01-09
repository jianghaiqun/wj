package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.SDInformationAppntDao;
import cn.com.sinosoft.entity.SDInformationAppnt;

/**
 * Dao实现类 - 运输信息
 * ============================================================================
 * 
 *
 * 
 *
 * 
 *
 * KEY:SINOSOFT64B5A05594CB1C3B74C8A9B4F94F5991
 * ============================================================================
 */

@Repository
public class SDInformationAppntDaoImpl extends
		BaseDaoImpl<SDInformationAppnt, String> implements
		SDInformationAppntDao {
	@Override
	public SDInformationAppnt getByOrParentId(String informationId) {
		String hql = "from SDInformationAppnt as informationAppnt where informationAppnt.sdinformaiton.id = ?";
		return (SDInformationAppnt) getSession().createQuery(hql)
				.setParameter(0, informationId).uniqueResult();
	}

	@Override
	public List<String> getPaidAppntNameByMemberId(String memberId) {
		String sql = "SELECT s4.applicantName "
				+ "   FROM SDOrders s1, SDInformationAppnt s4, SDInformation s5, TradeInformation s6 "
				+ "   WHERE s1.orderstatus>=7 AND s1.orderstatus<>8 AND s1.ordersn=s5.ordersn "
				+ "   AND s6.ordid=s5.ordersn AND receivedate IS NOT NULL "
				+ "   AND s4.informationSn=s5.informationSn AND s1.memberid=? "
				+ "   GROUP BY s4.applicantName "
				+ "   ORDER BY MIN(receivedate)";
		return getSession().createSQLQuery(sql).setParameter(0, memberId)
				.list();
	}

	@Override
	public SDInformationAppnt getByAppntNameAndMemberId(String applicantName,
			String memberId) {
		String sql = "SELECT s4.id "
				+ "   FROM SDOrders s1, SDInformationAppnt s4, SDInformation s5 "
				+ "   WHERE s1.orderstatus>=7 AND s1.orderstatus<>8 AND s1.ordersn=s5.ordersn "
				+ "   AND s4.informationSn=s5.informationSn AND s1.memberid = ? AND s4.applicantName = ? "
				+ "   ORDER BY s4.modifyDate DESC LIMIT 1";
		String id = (String) getSession().createSQLQuery(sql)
				.setParameter(0, memberId).setParameter(1, applicantName)
				.uniqueResult();
		return get(id);
	}
}