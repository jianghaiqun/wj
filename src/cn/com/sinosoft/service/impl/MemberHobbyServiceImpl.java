package cn.com.sinosoft.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.MemberHobbyDao;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MemberHobby;
import cn.com.sinosoft.service.MemberHobbyService;
@Service
public class MemberHobbyServiceImpl extends BaseServiceImpl<MemberHobby, String> implements MemberHobbyService{

	@Resource
	private MemberHobbyDao memberHobbyDao;
	@Resource
	public void setBaseDao(MemberHobbyDao memberHobbyDao) {
		super.setBaseDao(memberHobbyDao);
	}

}
