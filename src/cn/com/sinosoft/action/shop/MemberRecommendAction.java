/**
 * 
 */
package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangcaiyun
 * 
 */
@ParentPackage("member")
public class MemberRecommendAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 67962907322401685L;

	/**
	 * 跳转到我的推荐页面
	 * 
	 * @return
	 */
	public String query() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Member Member = getLoginMember();
		if (Member != null) {
//			if ("Y".equals(Member.getRecommendFlag())) {
				getRecommendInfo(request, Member);
//				return "recomm";
//			} else {
//				return "noRecomm";
//			}
		}

		return "recomm";
	}

	/**
	 * 跳转已推荐页面
	 * 
	 * @return
	 */
	public String queryRecomm() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Member Member = getLoginMember();
		if (Member != null) {
			getRecommendInfo(request, Member);
		}
		return "recomm";
	}

	/**
	 * 取得推荐信息
	 * 
	 * @param request
	 * @param member
	 */
	private void getRecommendInfo(HttpServletRequest request, Member member) {
		List<HashMap<String, String>> recommInfo = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		PointsCalculate PointsCalculate = new PointsCalculate();
		Map<String, Object> map1;
		// 取得注册好友送积分数量
		try {
			map1 = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH, IntegralConstant.POINT_SOURCE_RECOMMEND_REGISTER, null);
			if (map1.get(IntegralConstant.ACTION_POINTS) != null && !"0".equals(map1.get(IntegralConstant.ACTION_POINTS))) {
				map.put("recommRegCount", String.valueOf(map1.get(IntegralConstant.ACTION_POINTS)));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		String recommRegisterCount = Config.getValue("RecommRegisterCount");
		map.put("recommRegisterCount", recommRegisterCount);
		// 取得注册好友购买送积分比例
		String recommBuyPoints = Config.getValue("RecommBuyPoints");
		if (StringUtil.isNotEmpty(recommBuyPoints)) {
			recommBuyPoints = subZeroAndDot(new BigDecimal(recommBuyPoints).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			recommBuyPoints += "%";
		}
		map.put("recommBuyPoints", recommBuyPoints);

		// 取得推荐注册好友获得的积分
		if (member.getRecommendRegPoints() == null || member.getRecommendRegPoints() == 0) {
			map.put("getRecommRegCount", "0");
		} else {
			map.put("getRecommRegCount", String.valueOf(member.getRecommendRegPoints()));
		}

		// 取得推荐注册好友购买获得的积分
		if (member.getRecommendBuyPoints() == null || member.getRecommendBuyPoints() == 0) {
			map.put("getRecommRegBuyCount", "0");
		} else {
			map.put("getRecommRegBuyCount", String.valueOf(member.getRecommendBuyPoints()));
		}

		// 推荐地址
		String url = Config.getFrontServerContextPath() + "/" + Config.getValue("RecommendUrl") + "/" + member.getId();
		map.put("recommUrl", url);

		// 取得推荐标题、推荐描述、推荐图片信息
		String sql = "select CodeValue,Memo from zdcode where CodeType='Member.Recommend' and ParentCode='Member.Recommend'";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				// 取得微博推荐标题
				if ("title".equals(dt.getString(i, 0))) {
					map.put("recommTitle", dt.getString(i, 1));
				}
				// 取得推荐描述
				if ("desc".equals(dt.getString(i, 0))) {
					map.put("recommDesc", dt.getString(i, 1));
				}
				// 取得推荐图片
				if ("pic".equals(dt.getString(i, 0))) {
					map.put("recommPic", dt.getString(i, 1));
				}
				// 取得邮件推荐标题
				if ("mailTitle".equals(dt.getString(i, 0))) {
					map.put("recommMailTitle", dt.getString(i, 1));
				}
			}
		}

		recommInfo.add(map);
		request.setAttribute("recommInfo", recommInfo);
	}

	public String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}

	/**
	 * 发送推荐邮件
	 * 
	 * @return
	 */
	public String sendMail() {
		Map<String, String> jsonMap = new HashMap<String, String>();

		String emailAdress = getParameter("emailAdress");
		String recommUrl = getParameter("recommUrl");
		if (StringUtil.isEmpty(emailAdress)) {
			jsonMap.put("status", "fail");
			jsonMap.put("msg", "请填写邮件地址！");
			return ajaxJson(jsonMap);
		}
		String[] emailAdresses = emailAdress.split(";");
		int len = emailAdresses.length;
		for (int i = 0; i < len; i++) {
			if (!StringUtil.isMail(emailAdresses[i])) {
				jsonMap.put("status", "fail");
				jsonMap.put("msg", "您的邮件地址有误，请仔细查看!");
				return ajaxJson(jsonMap);
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();

		String sql = "select CodeValue,Memo  from zdcode where CodeType='Member.Recommend' ";
		sql += "and ParentCode='Member.Recommend' and ( CodeValue='mailContent' or CodeValue='mailTitle' )";

		DataTable dt = new QueryBuilder(sql).executeDataTable();

		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				// 取得邮件内容
				if ("mailContent".equals(dt.getString(i, 0))) {
					map.put("content", dt.getString(i, 1));
				}

				// 取得微博推荐标题
				if ("mailTitle".equals(dt.getString(i, 0))) {
					map.put("title", dt.getString(i, 1));
				}
			}
		}

		// 推荐链接
		map.put("recommUrl", recommUrl);
		
		if (!ActionUtil.sendMail("wj00120", emailAdress, map)) {
			jsonMap.put("status", "fail");
			jsonMap.put("msg", "发送失败，检查一下网络再发送吧！");
			return ajaxJson(jsonMap);
		} else {
			jsonMap.put("status", "success");
			jsonMap.put("msg", "发送成功!");
			return ajaxJson(jsonMap);
		}
	}
}
