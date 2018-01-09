package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.LotteryAct;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.LotteryActService;
import cn.com.sinosoft.service.MemberService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.ibrms.RuleExp;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDExpCalendarSchema;
import com.sinosoft.schema.memberSchema;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("shop")
public class ExpLotteryAction extends BaseShopAction {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5187923658916169241L;
	private String realName;
	private String mobile;
	private String memberId;
	private String actCode;

	@Resource
	private LotteryActService lotteryActService;

	@Resource
	private MemberService memberService;
	
	/**
	 * 录入中将信息
	 * 
	 * @return
	 */
	public String toDo2() {
		String id = (String) getSession().get("LotteryActId");
		LotteryAct lotteryAct = lotteryActService.get(id);
		if (StringUtil.isEmpty(realName) || StringUtil.isEmpty(mobile)) {
			return ajaxText("真实姓名和手机号不能为空！");
		}
		if (!StringUtil.isMobileNO(mobile)) {
			return ajaxText("请输入正确手机号!！");
		}
		lotteryAct.setRealName(realName);
		lotteryAct.setMobile(mobile);
		lotteryActService.update(lotteryAct);
		if (StringUtil.isNotEmpty(lotteryAct.getAwards())) {
			notice(realName, mobile);
		}
		return ajaxText("success");
	}
	/**
	 * 获取中奖者名单
	 * 
	 * @return
	 */

	public String getWin() {

		List<LotteryAct> lotteryActList = lotteryActService.getListByWin("RESULT", "Y");
		String winnerHtml="";
		if(lotteryActList.size()>10){
			winnerHtml="<ul>";
			for (int i = 0; i < lotteryActList.size(); i++) {
				winnerHtml+="<li>";
				winnerHtml+="<div class=\"ListNumberID_1\">"+(i+1)+"</div>";
				winnerHtml+="<div class=\"ListTxt\">";
				winnerHtml+="<a href=\"#\">";
				String mobile = lotteryActList.get(i).getMobile();
				String sql = "select codename from zdcode z where z.codetype=? and codevalue=?";
				String[] temp = {"PriceType",lotteryActList.get(i).getAwards()};
				GetDBdata db = new GetDBdata();
				String value = "";
				try {
					value = db.getOneValue(sql,temp);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				if (StringUtil.isNotEmpty(mobile)) {
					winnerHtml+=mobile.substring(0, 3) + "****" + mobile.substring(7, 11)+"抽奖获得"+value;
				}
				winnerHtml+="</a>";
				winnerHtml+="</div>";
				winnerHtml+="<div class=\"clear\"></div></li>";
			}
			winnerHtml+="</ul>";
		}else{
		
		}

		return ajaxText(winnerHtml);
	}
	/**
	 * ajax抽奖
	 * 
	 * @return
	 */
	public String toDo() {
		memberId = (String) getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if(StringUtil.isEmpty(memberId)){
			return ajaxText("notlogin");
		}
		Member member = memberService.get(memberId);

		int temp = RuleExp.getExp(memberId,"", 0, actCode);// 通过规则引擎获取经验扣除值
		if (member.getExpiricalValue() < -temp) {
			return ajaxText("对不起！您的经验值还没有达到抽奖资格！");

		}

		LotteryAct lotteryAct1 = new LotteryAct();
		dealMember(temp);
		// 参数：得奖临界点类型
		if (!isLucky("ExpCardLine")) {
			lotteryAct1.setType("N");
			lotteryAct1.setAwards("");
			lotteryAct1.setMemberId(memberId);
			lotteryAct1.setActCode(actCode);// 活动编码
			lotteryAct1.setRecordType("RESULT");
			lotteryActService.save(lotteryAct1);
			return ajaxText("no");
		} else {
			lotteryAct1 = new LotteryAct();
			lotteryAct1.setType("Y");
			lotteryAct1.setAwards("3");
			lotteryAct1.setMemberId(memberId);
			lotteryAct1.setActCode(actCode);// 活动编码
			lotteryAct1.setRecordType("RESULT");
			String lotteryActId = lotteryActService.save(lotteryAct1);
			setSession("LotteryActId", lotteryActId);
			return ajaxText("yes");
		}

	}
	private void dealMember(int newExp){
		SDExpCalendarSchema tSDExpCalendarSchema = new SDExpCalendarSchema();
		tSDExpCalendarSchema.setID(NoUtil.getMaxID("ExperienceID") + "");
		tSDExpCalendarSchema.setMemberId(memberId);
		tSDExpCalendarSchema.setExperience(newExp + "");
		tSDExpCalendarSchema.setSource("9");
		tSDExpCalendarSchema.setManner("1");
		tSDExpCalendarSchema.setCreateDate(PubFun.getCurrentDate());
		tSDExpCalendarSchema.setCreateTime(PubFun.getCurrentTime());
		tSDExpCalendarSchema.insert();

		memberSchema tmemberSchema = new memberSchema();
		tmemberSchema.setid(memberId);
		tmemberSchema.fill();
		tmemberSchema.setexpiricalValue(tmemberSchema.getexpiricalValue() + newExp);
		tmemberSchema.update();
	}
	private void notice(String realName, String mobile) {
		ActionUtil actionUtil = new ActionUtil();// 发送短信给用户、邮件给客服人员
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("ToName", realName);
		data.put("ToMobileNO", mobile);
		String[] temp = {"serviceEmail"};
		String sql = "select value from zdconfig where type=?";
		GetDBdata db = new GetDBdata();
		String value = "";
		try {
			value = db.getOneValue(sql,temp);    
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Member member = new Member();
		member.setEmail(value);
		data.put("Member", member);
//		actionUtil.deal("wj00040", data, getRequest());

	}
	private boolean isLucky(String type) {
		// 获取得奖临界值zdconfig
		String sql = "select value from zdconfig where type=?";
		String[] temp = {type};
		GetDBdata db = new GetDBdata();
		String value = "";
		try {
			value = db.getOneValue(sql,temp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		List<LotteryAct> list = lotteryActService.getListByAllActCode(actCode);
		// 是否中奖计算
		if (list.size() == 0) {
			return true;
		} else if ((list.size()) % (Integer.parseInt(value)) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getActCode() {
		return actCode;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}

}
