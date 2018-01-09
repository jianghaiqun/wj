package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.InformationAppnt;
import cn.com.sinosoft.entity.InformationInsured;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MemberDonated;

public interface MemberDonatedService extends BaseService<MemberDonated,String>{
	
	public List<MemberDonated> findByQBs(Member tMember, String productId);
	
	public boolean isInsuredByOrder(String productId, InformationAppnt infoAppnt);

	public String saveMemberDonated(MemberDonated memDonated);
}
