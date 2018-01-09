package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinosoft.framework.utility.StringUtil;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.MemberDonatedDao;
import cn.com.sinosoft.entity.InformationAppnt;
import cn.com.sinosoft.entity.InformationInsured;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MemberDonated;
import cn.com.sinosoft.service.MemberDonatedService;

@Service
public class MemberDonatedServiceImpl extends
		BaseServiceImpl<MemberDonated, String> implements MemberDonatedService {
	@Resource
	private MemberDonatedDao memberDonatedDao;

	@Resource
	public void setMemberDonatedDao(MemberDonatedDao memberDonatedDao) {
		super.setBaseDao(memberDonatedDao);
	}
	
	/**
	 * 查询赠送表返回list
	 * @param sericalNo
	 * @return
	 */
	public List<MemberDonated> findByQBs(Member tMember, String outRiskCode) {
		ArrayList<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("memberId","=",tMember.getId()));
		qbs.add(createQB("productId","=",outRiskCode));
		if(StringUtil.isNotEmpty(tMember.getEmail())){
			qbs.add(createQB("email","=",tMember.getEmail()));
		} else if(StringUtil.isNotEmpty(tMember.getMobileNO())){
			qbs.add(createQB("mobile","=",tMember.getMobileNO()));
		}
		return memberDonatedDao.findByQBs(qbs, "id", "asc");
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}

	/**
	 * 返回被保人列表是否存在标志
	 */
	@Override
	public boolean isInsuredByOrder(String productId,	InformationAppnt infoAppnt) {
		return memberDonatedDao.isInsuredByOrder(productId, infoAppnt);
	}

	/**
	 * 保存赠送会员信息
	 */
	@Override
	public String saveMemberDonated(MemberDonated memDonated) {
		return memberDonatedDao.save(memDonated);
	}

}
