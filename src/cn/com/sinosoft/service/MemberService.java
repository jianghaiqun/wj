package cn.com.sinosoft.service;

import java.util.Date;

import cn.com.sinosoft.entity.Member;


/**
 * Service接口 - 会员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT868582BB07E8457F3171FCCADB94B449
 * ============================================================================
 */

public interface MemberService extends BaseService<Member, String> {
	
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
	
	/**
	 * 根据用户名、密码验证会员
	 * 
	 * @param username
	 *            用户名
	 *            
	 * @param password
	 *            密码
	 * 
	 * @return 验证是否通过
	 */
	public boolean verifyMember(String username, String password);
	
	/**
	 * 生成密码找回Key
	 * 
	 * @return 密码找回Key
	 */
	public String buildPasswordRecoverKey();
	
	/**
	 * 根据密码找回Key获取生成日期
	 * 
	 * @return 生成日期
	 */
	public Date getPasswordRecoverKeyBuildDate(String passwordRecoverKey);
	
	
//--------------------------------吴高强添加---------------------------------------
	/**
	 * 判断此邮箱是否存在
	 * 
	 */
	public boolean isExistByMailbox(String mailbox);
   
	/**
	 * 判断此手机号是否存在
	 * 
	 */
	public boolean isExistByMobileNO(String mobileNo);
	

	/**
	 * 判断此QQ号是否存在
	 * 
	 */
	public boolean isExistByQQNO(String QQNO);
	
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
	
	//-------------------------fhz添加-----------------begin
	 /**
	 * 用户在登陆页面输入的来获取用户
	 * 只判断手机和邮箱，且不需要验证
	 * fhz
	 */
	public Member getMemberByLoginNameNoBinding(String loginName);
	public boolean verifyMemberNoNeedBinding(Member member, String password);
	//-------------------------fhz添加-----------------end
	
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
	
	/**
	 * getNickNameForMail:(获取会员称呼，用户邮件开头). <br/>
	 * @author guozc
	 * @param member
	 * @return
	 */
	String getNickNameForMail(Member member);
   
}