package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.Member;


/**
 * Dao接口 - 会员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT7DE6DBC156621DE89E663E0E451C2E85
 * ============================================================================
 */

public interface MemberDao extends BaseDao<Member, String> {
	
	/**
	 * 根据用户名判断此用户是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByUsername(String username);
	
	/**
	 * 根据用户名获取会员对象，若会员不存在，则返回null（不区分大小写）
	 * 
	 */
	public Member getMemberByUsername(String username);
//--------------------吴高强添加--------------------------------------	
	/**
	 * 判断此邮箱是否存在
	 * 
	 */
	public boolean isExistByMailbox(String mailbox);
	/**
	 * 根据邮箱获取会员对象，若会员不存在，则返回null（不区分大小写）
	 * 
	 */
	public Member getMemberByMailbox(String mailbox);
	/**
	 * 判断此手机号是否存在
	 * 
	 */
	public boolean isExistByMobileNO(String mobileNo);
	
	/**
	 * 根据手机号获取会员对象，若会员不存在，则返回null（不区分大小写）
	 * 
	 */
	public Member getMemberByMobileNO(String mobileNo);
	/**
	 * 判断此QQ号是否存在
	 * 
	 */
	public boolean isExistByQQNO(String QQNO);
	/**
	 * 根据QQ号获取会员对象，若会员不存在，则返回null（不区分大小写）
	 * 
	 */
	public Member getMemberByQQNO(String QQNO);
    /**
	 * 得到用户
	 * 
	 */
	public Member getUser(Member member);
	 /**
	 * 用户在登陆页面输入的来获取用户
	 * 
	 */
	public Member getMemberByLoginName(String loginName);
	/**
	 * 判断此用户名是否存在
	 * 
	 */
	public boolean isExistByUserName(String userName);
	
	/***
	 * 判断用户是否存在，不需要绑定，手机和邮箱
	 * @author fhz
	 * */
	public Member getMemberByEMail(String mobileNo);
	public Member getMemberByMobile(String mobileNo);
	
	/***
	 * 登录名得到用户，手机和邮箱且不需要绑定
	 * @author fhz
	 * */
	public Member getMemberByLoginNameNoBinding(String userName) ;
	
	/**
	 * 根据推荐会员id和ip查询是否同一ip注册过
	 * 
	 */
	public boolean isExistByRecommentMemberIdAndIP(String recommentMemberId, String ip);
	
	/**
	 * 判断会员是否存在
	 * 
	 */
	public boolean isExistByMemberId(String memberId);
}