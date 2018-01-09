package cn.com.sinosoft.service.impl;

import java.util.Date;

import javax.annotation.Resource;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sinosoft.framework.utility.StringUtil;

import cn.com.sinosoft.dao.MemberDao;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.util.CommonUtil;

/**
 * Service实现类 - 会员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT9A3ACBDD2D3A5E96E54D057769342EAF
 * ============================================================================
 */

@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, String> implements MemberService {

	@Resource
	private MemberDao memberDao;

	@Resource
	public void setBaseDao(MemberDao userDao) {
		super.setBaseDao(userDao);
	}
	
	public boolean isExistByUsername(String username) {
		return memberDao.isExistByUsername(username);
	}
	
	public Member getMemberByUsername(String username) {
		return memberDao.getMemberByUsername(username);
	}
	
	public boolean verifyMember(String username, String password) {
		Member member = getMemberByLoginName(username);
		if ((member != null)&&(member.getPassword().equals(DigestUtils.md5Hex(password)))) {
			return true;
		} else {
			return false;
		}
	}
	
	public String buildPasswordRecoverKey() {
		return System.currentTimeMillis() + Member.PASSWORD_RECOVER_KEY_SEPARATOR + CommonUtil.getUUID() + DigestUtils.md5Hex(CommonUtil.getRandomString(10));
	}
	
	public Date getPasswordRecoverKeyBuildDate(String passwordRecoverKey) {
		long time = Long.valueOf(StringUtils.substringBefore(passwordRecoverKey, Member.PASSWORD_RECOVER_KEY_SEPARATOR));
		return new Date(time);
	}
//-------------------------吴高强添加--------------------------------------------
	@Override
	public boolean isExistByMailbox(String mailbox) {
		return memberDao.isExistByMailbox(mailbox);
	}

	@Override
	public boolean isExistByMobileNO(String mobileNo) {
		return memberDao.isExistByMobileNO(mobileNo);
	}
	
	@Override
	public boolean isExistByQQNO(String QQNO) {
		return memberDao.isExistByQQNO(QQNO);
	}

	@Override
	public Member getUser(Member member) {
		return memberDao.getUser(member);
	}

	@Override
	public Member getMemberByLoginName(String loginName) {
		return memberDao.getMemberByLoginName(loginName);
	}

	@Override
	public boolean isExistByUserName(String userName) {
		return memberDao.isExistByUserName(userName);
	}
	
	
	//-------------------------fhz添加-----------------begin
	@Override
	public Member getMemberByLoginNameNoBinding(String userName) {
		return memberDao.getMemberByLoginNameNoBinding(userName);
	}
	
	
	//不需要绑定 fhz
	@Override
	public boolean verifyMemberNoNeedBinding(Member member, String password) {
		if ((member != null)&&(member.getPassword().equals(DigestUtils.md5Hex(password)))) {
			return true;
		} else {
			return false;
		}
	}
	//-------------------------fhz添加-----------------end
	
	@Override
	public boolean isExistByRecommentMemberIdAndIP(String recommentMemberId, String ip) {
		return memberDao.isExistByRecommentMemberIdAndIP(recommentMemberId, ip);
	}
	
	@Override
	public boolean isExistByMemberId(String memberId) {
		return memberDao.isExistByMemberId(memberId);
	}

	@Override
	public String getNickNameForMail(Member member) {
		String realName = "尊敬的会员您好:";
		if(member != null){
			if (StringUtil.isNotEmpty(member.getRealName())) {
				realName = "尊敬的&nbsp;" + member.getRealName() + "&nbsp;您好:";
			}
		}
		return realName;
	}
}