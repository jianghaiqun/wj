package cn.com.sinosoft.action.shop;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.ProductCollection;
import cn.com.sinosoft.service.FavoritesService;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;


@ParentPackage("shop")
public class FavoritesAction extends BaseShopAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8324607596264978496L;
	
	
	private String productId;
	
	@Resource
	private FavoritesService favoritesService;

	public String initFavorites() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if(StringUtil.isEmpty(id)){
			return ajaxText("0");
		}
		ProductCollection productCollection=new ProductCollection();
		productCollection.setProductID(productId);
		productCollection.setMemberID(id);
		if (favoritesService.contains(productCollection)) {
			return ajaxText("1");
		}else{
			return ajaxText("0");
		}
	}
	
	public String initSemFavorites() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		Member loginMember = getLoginMember();
		if (loginMember == null || StringUtil.isEmpty(loginMember.getId())) {
			return ajaxJson(jsonMap);
		}
		
		String productIDS = getParameter("ProductIDS");
		if (StringUtil.isEmpty(productIDS)) {
			return ajaxJson(jsonMap);
		}
		productIDS = productIDS.replaceAll(",", "','");
		DataTable dt = new QueryBuilder("select ProductID from productCollection where MemberID=? and ProductID in ('"+productIDS+"')", loginMember.getId()).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			String riskcode = "";
			for (int i = 0; i < count; i++) {
				riskcode = dt.getString(i, 0);
				jsonMap.put(riskcode, riskcode);
			}
		}
		return ajaxJson(jsonMap);
	}
	
	public String add() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if(StringUtil.isEmpty(id)){
			return ajaxText("notlogin");
		}
		ProductCollection productCollection=new ProductCollection();
		productCollection.setProductID(productId);
		productCollection.setMemberID(id);
		productCollection.setcollectionPrice(new AjaxPriceAction().queryAjaxPrice(productId, "wj", id));//增加收藏价格
		if (favoritesService.contains(productCollection)) {
			return ajaxText("您已经收藏过此商品!");
		} else {
			//productCollection.setCreateDate(new Date());
			String flag=favoritesService.save(productCollection) ;
			if(StringUtil.isEmpty(flag)){
				return ajaxText("商品收藏失败!");
			}else{
				Map<String, Object> map = new HashMap<String, Object>();
				Member member=new Member();
				member.setId(id);
				map.put("Member", member);
				ActionUtil.dealAction("wj00020", map,  getRequest());
				
				return ajaxText("收藏成功，可以到会员中心，我的收藏中查看！");
			}
		}
	}
	
	public String addOrder() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if(StringUtil.isEmpty(id)){
			return ajaxText("notlogin");
		}
		
		String orderSns = getRequest().getParameter("orderSns");
		if (StringUtil.isNotEmpty(orderSns)) {
			orderSns = "'"+orderSns.substring(1).replace(",", "','") + "'";
			DataTable dt = new QueryBuilder(
					"select productId from sdinformation where orderSn in ("
							+ orderSns
							+ ") and NOT EXISTS	(SELECT ProductID FROM ProductCollection WHERE MemberID = ? AND ProductID = sdinformation.productId)",
					id).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int rowCount = dt.getRowCount();
				ProductCollection productCollection;
				for (int i = 0; i < rowCount; i++) {
					productCollection=new ProductCollection();
					productCollection.setProductID(dt.getString(i, 0));
					productCollection.setMemberID(id);
					favoritesService.save(productCollection);
				}
			}

		}
		return ajaxText("");
	}
	
	/**
	 * 加入收藏 跨域写法
	 * @return
	 */
	public String addsubmitp() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		HttpServletRequest request = ServletActionContext.getRequest();
		String jsonpname = request.getParameter("callback");
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("status", "0");
		if (StringUtil.isEmpty(id)) {
			jsonMap.put("status", "1");
			jsonMap.put("msg", "notlogin");
			return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
		}

		ProductCollection productCollection = new ProductCollection();
		productCollection.setProductID(productId);
		productCollection.setMemberID(id);

		if (favoritesService.contains(productCollection)) {
			jsonMap.put("status", "1");
			jsonMap.put("msg", "您已经收藏过此商品!");
			return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");

		} else {
			String flag = favoritesService.save(productCollection);
			if (StringUtil.isEmpty(flag)) {
				jsonMap.put("status", "1");
				jsonMap.put("msg", "商品收藏失败!");
				return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");

			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				Member member = new Member();
				member.setId(id);
				map.put("Member", member);
				ActionUtil.dealAction("wj00020", map, getRequest());

				jsonMap.put("status", "1");
				jsonMap.put("msg", "收藏成功，可以到会员中心，我的收藏中查看！");
				return ajaxHtml(jsonpname + "(" + JSONObject.fromObject(jsonMap).toString() + ");");
			}
		}
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}



}