package cn.com.sinosoft.dao.impl;

import java.util.List;


import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.dao.ChannelDao;
import cn.com.sinosoft.entity.Channel;

/**
 * Dao实现类 - 商品分类
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT218DE2372F1FD61042DB651AAC5D5594
 * ============================================================================
 */

@Repository
public class ChannelDaoImpl extends BaseDaoImpl<Channel, String> implements ChannelDao {

	
	// 重写方法
	@Override
	public String save(Channel channel) {
		String id = super.save(channel);
		super.update(channel);
		return id;
	}
	
	// 重写方法
	@Override
	public void update(Channel channel) {
		
		super.update(channel);
	}
	
	// 根据orderList排序
	@SuppressWarnings("unchecked")
	@Override
	public List<Channel> getAll() {
		String hql = "from Channel channel ";
		return getSession().createQuery(hql).list();
	}

	// 根据orderList排序
	@Override
	@SuppressWarnings("unchecked")
	public List<Channel> getList(String propertyName, Object value) {
		String hql = "from Channel channel where channel." + propertyName + "=? order by channel.name asc channel.id desc";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}

	// 根据orderList排序
	@Override
	public Pager findByPager(Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Channel.class);
		return this.findByPager(pager, detachedCriteria);
	}

}
