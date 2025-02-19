package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.FriendLink;


/**
 * Service接口 - 友情链接
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTC862876613564B73F604B8D80A5BB9B4
 * ============================================================================
 */

public interface FriendLinkService extends BaseService<FriendLink, String> {

	/**
	 * 获取所有图片友情链接集合;
	 * 
	 * @return 图片友情链接集合
	 * 
	 */
	public List<FriendLink> getPictureFriendLinkList();
	
	/**
	 * 获取所有文字友情链接集合;
	 * 
	 * @return 图片友情链接集合
	 * 
	 */
	public List<FriendLink> getTextFriendLinkList();

}