package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.LotteryAct;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.LotteryActService;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("shop")
public class LotteryAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 481568370413531502L;

	private String realName;
	private String mobile;
	private List<LotteryAct> lotteryActList = new ArrayList<LotteryAct>();

	@Resource
	private LotteryActService lotteryActService;

	/**
	 * 跳转到抽奖页面
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
	 * 跳转到抽奖页面
	 * 
	 * @return
	 */

	public String toIndex() {

		lotteryActList = lotteryActService.getListByWin("RESULT", "Y", "h00001");
		for (int i = 0; i < lotteryActList.size(); i++) {
			String mobile = lotteryActList.get(i).getMobile();
			if (StringUtil.isNotEmpty(mobile)) {
				lotteryActList.get(i).setMobile(mobile.substring(0, 3) + "****" + mobile.substring(7, 11));
			}

		}

		return "index";
	}

	public String checkUser() {

		String memberId = (String) getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtil.isEmpty(memberId)) {
			return ajaxText("notlogin");
		}

		return ajaxText("login");
	}

	public String checkUserChoice() {
		String memberId = (String) getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		List<LotteryAct> list = lotteryActService.getListByMemberId(memberId, "JOIN", "N", "h00001");// 取到横向接口里 有参加抽奖资格 并没有使用的记录
		Integer n = list.size();
		if (n == 0) {
			return ajaxText("noChoice");

		}

		return ajaxText("choice");
	}

	/**
	 * ajax抽奖
	 * 
	 * @return
	 */
	public String toDo1() {
		try {
			String memberId = (String) getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME);
			if (StringUtil.isEmpty(memberId)) {
				return ajaxText("notlogin");
			}
			List<LotteryAct> list = lotteryActService.getListByMemberId(memberId, "JOIN", "N", "h00001");// 取到横向接口里 有参加抽奖资格 并没有使用的记录
			Integer n = list.size();
			if (n == 0) {
				return ajaxText("对不起！您还没有抽奖资格，请继续关注我们的活动！<br/> 【小提示】每购买任意一份保险即可增加一次抽奖机会喔~");

			}

			LotteryAct lotteryAct = list.get(0);
			// GetDBdata db = new GetDBdata();
			// String ss = "select applicantName,applicantMobile from informationappnt where information_id in(select id from information where orderItem_id in (select id from orderitem where order_id in(select id from orders where ordersn='"
			// + lotteryAct.getOrderNo() + "')))";
			// SSRS temp = db.execSQL(ss);
			// String realName =temp.GetText(0, 0);
			// String mobile=temp.GetText(0, 1);
			// if (StringUtil.isEmpty(realName) || StringUtil.isEmpty(mobile)) {
			// return ajaxText("出错啦！");
			// }

			// if (!isOut("CardNo")) {// 奖品已发完
			// lotteryAct.setUseType("Y");
			// lotteryActService.update(lotteryAct);
			// return ajaxText("恭喜您！获得双飞游和IPod的抽奖机会！还有" + (n - 1) + "次机会");
			// }
			// 参数：得奖临界点类型
			if (!isLucky("CardLine")) {
				lotteryAct.setUseType("Y");
				lotteryActService.update(lotteryAct);
				setSession("LotteryActId", lotteryAct.getId());
				return ajaxText("恭喜您！获得赢取【海南双飞游/iPod shuffle】抽奖资格！您还有" + (n - 1) + "次抽奖机会！<br/>请您认真填写一下信息，稍后我们的客服人员会与您联系，谢谢！");
			} else {
				lotteryAct.setUseType("Y");
				lotteryActService.update(lotteryAct);
				LotteryAct lotteryAct1 = new LotteryAct();
				lotteryAct1.setType("Y");
				lotteryAct1.setAwards("3");
				lotteryAct1.setOrderNo(lotteryAct.getOrderNo());
				lotteryAct1.setMemberId(memberId);
				// lotteryAct1.setRealName(realName);
				// lotteryAct1.setMobile(mobile);
				lotteryAct1.setActCode("h00001");// 活动编码
				lotteryAct1.setRecordType("RESULT");
				String lotteryActId = lotteryActService.save(lotteryAct1);
				setSession("LotteryActId", lotteryActId);
				return ajaxText("恭喜您！获得【50元的手机充值卡】一张！您还有" + (n - 1) + "次抽奖机会！<br/>请您认真填写一下信息，稍后我们的客服人员会与您联系，谢谢！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ajaxText("error");

		}
	}

	private void notice(String realName, String mobile) {
		ActionUtil actionUtil = new ActionUtil();// 发送短信给用户、邮件给客服人员
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("ToName", realName);
		data.put("ToMobileNO", mobile);
		String sql = "select value from zdconfig where type=?";
		String[] temp = {"serviceEmail"};  
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
		List<LotteryAct> list = lotteryActService.getListByAllUse("JOIN", "Y", "h00001");
		// 是否中奖计算
		if (list.size() == 0) {
			return true;
		} else if ((list.size()) % (Integer.parseInt(value)) == 0) {
			return true;
		} else {
			return false;
		}
	}

	// private boolean isOut(String type) {
	// // 获取总奖品个数
	// String sql = "select value from zdconfig where type='" + type + "'";
	// GetDBdata db = new GetDBdata();
	// String value = "";
	// try {
	// value = db.getOneValue(sql);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// List<LotteryAct> list = lotteryActService.getListByCondition("h00001", "Y", "3", "RESULT");
	// if (list.size() == Integer.parseInt(value)) {
	// return false;
	// }
	//
	// return true;
	//
	// }

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

	public List<LotteryAct> getLotteryActList() {
		return lotteryActList;
	}

	public void setLotteryActList(List<LotteryAct> lotteryActList) {
		this.lotteryActList = lotteryActList;
	}

}
