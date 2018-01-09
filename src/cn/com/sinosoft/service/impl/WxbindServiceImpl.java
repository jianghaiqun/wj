package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.dao.WxbindDao;
import cn.com.sinosoft.entity.Wxbind;
import cn.com.sinosoft.service.WxbindService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
public class WxbindServiceImpl extends BaseServiceImpl<Wxbind, String> implements WxbindService {

	@Resource
	private WxbindDao mWxbindDao;

	@Resource
	public void setBaseDao(WxbindDao userDao) {
		super.setBaseDao(userDao);
	}
	public Wxbind getWxbindByOpenID(String openID){
		
		return mWxbindDao.getWxbindByOpenID(openID);
	}

	public void bindOpenIdAndMemberId(Wxbind wxbind) {
		mWxbindDao.bindOpenIdAndMemberId(wxbind);
	}
}