package cn.com.sinosoft.dao.impl;
 
import java.util.List;
import java.util.Set;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.sinosoft.framework.GetDBdata;

import cn.com.sinosoft.dao.MemberDao;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Order;


/**
 * Dao实现类 - 会员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTAC8DDA6F41F51B7BA7B541180E9FE7F7
 * ============================================================================
 */

@Repository
public class MemberDaoImpl extends BaseDaoImpl<Member, String> implements MemberDao {

	@SuppressWarnings("unchecked")
	public boolean isExistByUsername(String username) {
		String hql = "from Member members where lower(members.username) = lower(?)";
		Member member = (Member) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
		if (member != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Member getMemberByUsername(String username) {
		String hql = "from Member members where lower(members.username) = lower(?)";
		return (Member) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
	}
	
	// 关联处理
	@Override
	public void delete(Member member) {
//		Set<Order> orderSet = member.getOrderSet();
//		if (orderSet != null) {
//			for (Order order : orderSet) {
//				order.setMember(null);
//			}
//		}
		super.delete(member);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Member member = load(id);
		this.delete(member);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Member member = load(id);
			this.delete(member);
		}
	}
//--------------------------吴高强添加-------------------------------------------
	@SuppressWarnings("unchecked")
	public boolean isExistByMobileNO(String mobileNo) {
		//手机号是否被绑定 或者 被注册
		String queryPhone = "select * from member where  mobileNO=?";
		GetDBdata db = new GetDBdata();
		String[] temp = {mobileNo};
		try {
			List listResult = db.query(queryPhone,temp);
			if(listResult.size()>0){
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean isExistByQQNO(String QQNO) {
		String hql = "from Member members where members.QQNO = ? ";
		Member member = (Member)getSession().createQuery(hql).setParameter(0, QQNO).uniqueResult();
		if (member != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean isExistByMailbox(String mailbox) {
		GetDBdata bdata = new GetDBdata();
		//判断邮箱是否存在 或 被注册
		String sql = "select * from member where email = ?";
		String[] temp = {mailbox};
		try {
			List list = bdata.query(sql,temp);
			if(list.size() > 0){
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public Member getUser(Member member) {
		Member memberzs = new Member();
		String hql=null;
	    if(member.getUsername()!=null&&!"".equals(member.getUsername())){
	    	if(isExistByUsername(member.getUsername())){
		     hql = "from Member members where members.username = ? ";
		     memberzs = (Member) getSession().createQuery(hql).setParameter(0, member.getUsername()).uniqueResult();
		     } 
	    	}
	    if(member.getQQNO()!=null&&!"".equals(member.getQQNO())){
	    	if(isExistByQQNO(member.getQQNO())){
		     hql = "from Member members where members.QQNO = ? ";
		     memberzs = (Member) getSession().createQuery(hql).setParameter(0, member.getQQNO()).uniqueResult();
		     } 
	    	}
	    if(member.getEmail()!=null&&!"".equals(member.getEmail())){
	    	if(isExistByMailbox(member.getEmail())){
		     hql = "from Member members where members.email = ? ";
		     memberzs = (Member) getSession().createQuery(hql).setParameter(0, member.getEmail()).uniqueResult();
		     } 
	    	}
	    if(member.getMobileNO()!=null&&!"".equals(member.getMobileNO())){
	    	if(isExistByMobileNO(member.getMobileNO())){
		     hql = "from Member members where members.mobileNO = ? ";
		     memberzs = (Member) getSession().createQuery(hql).setParameter(0, member.getMobileNO()).uniqueResult();
		     } 
	    	}
		return memberzs;
	}

	@Override
	public Member getMemberByMailbox(String mailbox) {
		String hql = "from Member members where members.email =? ";
		//String hql = "from Member members where members.email =? and members.isEmailBinding= 'Y' ";
		return (Member) getSession().createQuery(hql).setParameter(0, mailbox).uniqueResult();
	}

	@Override
	public Member getMemberByMobileNO(String mobileNo) {
		String hql = "from Member members where members.mobileNO =?  ";
		//wap站需要
		//String hql = "from Member members where members.mobileNO =? and members.isMobileNOBinding='Y' ";
		return (Member) getSession().createQuery(hql).setParameter(0, mobileNo).uniqueResult();
	}
	
	
	//fhz 判断邮箱用户是否存在，不需要绑定
	@Override
	public Member getMemberByEMail(String mailbox) {
//		String hql = "from Member members where (members.isEmailBinding='Y' or members.registerType='0') and  members.email =?  ";
		String hql = "from Member members where  members.email =?  ";
		return (Member) getSession().createQuery(hql).setParameter(0, mailbox).uniqueResult();
	}

	//fhz 判断手机用户是否存在，不需要绑定
	@Override
	public Member getMemberByMobile(String mobileNo) {
//		String hql = "from Member members where  (members.isMobileNOBinding='Y' or members.registerType='1') and  members.mobileNO =? ";
		String hql = "from Member members where    members.mobileNO =? ";
		return (Member) getSession().createQuery(hql).setParameter(0, mobileNo).uniqueResult();
	}

	@Override
	public Member getMemberByQQNO(String QQNO) {
		String hql = "from Member members where members.QQNO =? ";
		return (Member) getSession().createQuery(hql).setParameter(0, QQNO).uniqueResult();
	}

	@Override
	public Member getMemberByLoginName(String loginName) {
		Member mber=null;
		mber=getMemberByMailbox(loginName);
	   if(mber==null||"".equals(mber)){
			mber=getMemberByMobileNO(loginName);
			
		}
//		if(mber==null||"".equals(mber)){
//			mber=getMemberByQQNO(loginName);
//			
//		}
		if(mber==null||"".equals(mber)){
			mber=getMemberByUsername(loginName);
			
		}
		return mber;
	}
	
	//fhz 
	@Override
	public Member getMemberByLoginNameNoBinding(String userName) {
		Member mber=null;
		mber=getMemberByEMail(userName);
	   if(mber==null||"".equals(mber)){
			mber=getMemberByMobile(userName);
		}
		return mber;
	}

	@Override
	public boolean isExistByUserName(String userName) {
		String hql = "from Member members where members.username = ? ";
		Member member = (Member) getSession().createQuery(hql).setParameter(0, userName).uniqueResult();
		if (member != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isExistByMemberId(String memberId) {
		String hql = "from Member members where members.id = ? ";
		Member member = (Member) getSession().createQuery(hql).setParameter(0, memberId).uniqueResult();
		if (member != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据推荐会员id和ip查询是否同一ip注册过
	 * 
	 */
	@Override
	public boolean isExistByRecommentMemberIdAndIP(String recommentMemberId, String ip) {
		String hql = "from Member members where registerIp = ? and recommendMemId = ?";
		Query qu = getSession().createQuery(hql).setParameter(0, ip);
		qu.setParameter(1, recommentMemberId);
		List list = qu.list();
		if (list.size()>=1) {
			return true;
		} else {
			return false;
		}
	}
}