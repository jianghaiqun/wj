package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.dao.ChannelDao;
import cn.com.sinosoft.entity.Channel;
import cn.com.sinosoft.service.ChannelService;
import cn.com.sinosoft.util.SpringUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterSecurityInterceptor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service实现类 - 渠道
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT5780B32776CB0A6FF3A3530C4BC96D54
 * ============================================================================
 */

@Service
public class ChannelServiceImpl extends BaseServiceImpl<Channel, String> implements ChannelService {
	
	@Resource
	ChannelDao channelDao;

	@Resource
	public void setBaseDao(ChannelDao channelDao) {
		super.setBaseDao(channelDao);
	}
	
	// 重写方法，删除时刷新SpringSecurity权限信息
	@Override
	public void delete(Channel channel) {
		channelDao.delete(channel);
		channelDao.flush();
		flushSpringSecurity();
	}

	// 重写方法，删除时刷新SpringSecurity权限信息
	@Override
	public void delete(String id) {
		Channel channel = channelDao.load(id);
		this.delete(channel);
	}

	// 重写方法，删除时刷新SpringSecurity权限信息
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Channel channel = channelDao.load(id);
			channelDao.delete(channel);
		}
		channelDao.flush();
		flushSpringSecurity();
	}

	// 重写方法，保存时刷新SpringSecurity权限信息
	@Override
	public String save(Channel channel) {
		String id = channelDao.save(channel);
		channelDao.flush();
		channelDao.clear();
		flushSpringSecurity();
		return id;
	}

	// 重写方法，更新时刷新SpringSecurity权限信息
	@Override
	public void update(Channel channel) {
		channelDao.update(channel);
		channelDao.flush();
		flushSpringSecurity();
	}
	
	// 刷新SpringSecurity权限信息
	private void flushSpringSecurity() {
		try {
			FactoryBean factoryBean = (FactoryBean)SpringUtil.getBean("&adminSecurityDefinitionSource");
			FilterInvocationDefinitionSource filterInvocationDefinitionSource = (FilterInvocationDefinitionSource) factoryBean.getObject();
		    FilterSecurityInterceptor filterSecurityInterceptor = (FilterSecurityInterceptor) SpringUtil.getBean("filterSecurityInterceptor");
		    filterSecurityInterceptor.setObjectDefinitionSource(filterInvocationDefinitionSource);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}