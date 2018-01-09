package cn.com.sinosoft.dao.impl;

import cn.com.sinosoft.dao.MemberDonatedDao;
import cn.com.sinosoft.entity.InformationAppnt;
import cn.com.sinosoft.entity.MemberDonated;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询返回被保人列表
 * @author Administrator
 *
 */
@Repository
public class MemberDonatedDaoImpl extends BaseDaoImpl<MemberDonated, String> implements MemberDonatedDao {
	public boolean isInsuredByOrder(String productId,	InformationAppnt infoAppnt) {
		List list = new ArrayList();
		try {
			String sql = "select * from Orders orders "
				+ "left join OrderItem orderItem on orders.id = orderItem.order_id "
				+ "left join Information info on orderItem.id = info.orderItem_id "
				+ "left join InformationInsured infoInsured on info.id = infoInsured.information_id "
				+ "where ((orders.productId = '"+productId+"' and infoInsured.recognizeeIdentityType = '"+infoAppnt.getApplicantIdentityType()+"' "
				+ "and infoInsured.recognizeeIdentityId = '"+infoAppnt.getApplicantIdentityId()+"') "
				+ "or (orders.productId = '"+productId+"' and infoInsured.recognizeeMobile = '"+infoAppnt.getApplicantMobile()+"')) "
				+ "and orders.insureStatus = '0' and orders.paymentStatus = '2' and orders.orderStatus = '7'";
			list = getSession().createSQLQuery(sql).list();
		} catch (Exception e) {
			logger.info("类MemberDonatedDaoImpl出现异常"+e.getMessage(), e);
		}
		if (list.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public static void main(String[] args) {
		MemberDonatedDaoImpl tMemberDonatedDaoImpl = new MemberDonatedDaoImpl();
		InformationAppnt infoAppnt = new InformationAppnt();
		infoAppnt.setApplicantIdentityType("1");
		infoAppnt.setApplicantIdentityId("230602198311300217");
		infoAppnt.setApplicantMobile("");
//		System.out.println("=================================="+tMemberDonatedDaoImpl.isInsuredByOrder("200701102", infoAppnt));
	}
}
