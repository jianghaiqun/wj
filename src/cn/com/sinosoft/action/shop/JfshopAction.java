package cn.com.sinosoft.action.shop;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.sinosoft.framework.utility.StringUtil;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.MemberService;

@ParentPackage("shop")
public class JfshopAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5194815009126258836L;
	
	
	private String memberId;

	@Resource
	private MemberService memberService;

	/**
	 * 积分商城会员信息查询
	 * 
	 * @return
	 */
	public String getUser() {
		// String memberId = getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME).toString();
		memberId = "8a71fd1b39b456450139b533fbb100a8";
		if(StringUtil.isEmpty(memberId)){
			return ajaxText("notlogin");
		}
		
		Member member = memberService.get(memberId);
		String name="";
		if(StringUtil.isNotEmpty(member.getUsername())){
			name=member.getUsername();
		}else if(StringUtil.isNotEmpty(member.getEmail())){
			name=member.getEmail();
		}else if(StringUtil.isNotEmpty(member.getMobileNO())){
			name=member.getMobileNO();
		}

		String json="{name:\""+name+"\",point:\""+member.getPoint()+"\",exp:\""+member.getExpiricalValue()+"\"}";
	    return ajaxJson(json);

	}



}
