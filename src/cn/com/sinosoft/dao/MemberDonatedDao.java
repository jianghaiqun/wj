package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.InformationAppnt;
import cn.com.sinosoft.entity.InformationInsured;
import cn.com.sinosoft.entity.MemberDonated;

public interface MemberDonatedDao extends BaseDao<MemberDonated,String> {

	public boolean isInsuredByOrder(String productId,	InformationAppnt infoAppnt);
}
