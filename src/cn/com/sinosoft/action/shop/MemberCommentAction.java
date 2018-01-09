package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.GiftClassify;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.GiftClassifyService;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("member")
public class MemberCommentAction extends BaseShopAction {

	/**
	 *  
	 */
	private static final long serialVersionUID = -1288982361354327831L;
	private int page = 1;
	public static final int pagesize = 10;
	private int count;
	private int lastpage;
	private int newPageSize;
	private List<Map<String, Object>> listComment;
	private List<Map<String, String>> listExchange;
	private String notCommOrderSn;
	@Resource
	private BindInfoForLoginService bindInfoForLoginService;
	@Resource
	private GiftClassifyService giftClassifyService;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}
	
	public int getNewPageSize() {
		return newPageSize;
	}

	public void setNewPageSize(int newPageSize) {
		this.newPageSize = newPageSize;
	}
	
	public List<Map<String, Object>> getListComment() {
		return listComment;
	}

	public void setListComment(List<Map<String, Object>> listComment) {
		this.listComment = listComment;
	}
	
	public List<Map<String, String>> getListExchange() {
		return listExchange;
	}

	public void setListExchange(List<Map<String, String>> listExchange) {
		this.listExchange = listExchange;
	}

	public String getNotCommOrderSn() {
		return notCommOrderSn;
	}

	public void setNotCommOrderSn(String notCommOrderSn) {
		this.notCommOrderSn = notCommOrderSn;
	}

	public String queryComment(){
		notCommOrderSn = "";
		String orderSn = getRequest().getParameter("orderSn");
		if ("null".equals(orderSn)) {
			orderSn = "";
		}
		String memberId = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Member loginMember = null;
		if(!"tencent".equals(memberId)){
			   loginMember = memberService.load(memberId);
		}else{
			loginMember = bindInfoForLoginService.get(getSession("loginBindId").toString()).getmMember();
		}
		if(loginMember!=null){
			memberId = loginMember.getId();
		}
		
		// 取得热门兑换 3个产品
		List<GiftClassify> giftList = giftClassifyService.hotExchange();
		listExchange = new ArrayList<Map<String, String>>();
		if (giftList != null && giftList.size() > 0) {
			int giftCount = giftList.size();
			Map<String, String> map;
			for (int i = 0; i < giftCount; i++) {
				if (i == 3) {
					break;
				}
				map = new HashMap<String, String>();
				if ("1".equals(giftList.get(i).getType())) {
					map.put("exchangeUrl", giftList.get(i).getLinkUrl());
					
				} else {
					map.put("exchangeUrl", Config.getServerContext() + "/shop/points!integralMallInformation.action?presentID=" + giftList.get(i).getId());
				}
				map.put("logoUrl", Config.getValue("StaticResourcePath") + "/" + giftList.get(i).getLogoUrl());
				if (StringUtil.isNotEmpty(giftList.get(i).getGiftName()) && giftList.get(i).getGiftName().length() > 10) {
					map.put("giftName", giftList.get(i).getGiftName().substring(0, 9) + "…");
				} else {
					map.put("giftName", giftList.get(i).getGiftName());
				}
				map.put("points", giftList.get(i).getPoints());
				
				listExchange.add(map);
			}
		}
		
		String startCont = "select s.orderSn ";
		String startSelect = "select s.orderSn, b.productName, c.URL,"
				+" date_format(s.modifyDate ,'%Y-%m-%d ') as buyDate, p.ProductType,"
				+" s.commentId, '' as commentDis1, '' as commentDis2,"
				+" '' as commentClass, 'display:none' as commentDisplay,'' as isCilck,"
				+" a.Integral, '' as commentDis3, c.logo, '' as trCommentClass";
		StringBuffer sb = new StringBuffer();
		sb.append(" from sdorders s left join SDIntCalendar a on a.memberId = s.memberId ");
		sb.append(" and a.businessid = s.orderSn and a.Source = '1', ");
		sb.append(" sdinformation b, sdsearchrelaproduct c, sdproduct p ");
		sb.append(" where s.orderStatus in ('7', '10') and s.memberId= '" + memberId + "'");
		sb.append(" and s.orderSn = b.orderSn and b.productid = c.productid");
		sb.append(" and c.productid = p.productid ");
		sb.append(" and s.channelsn != 'jfsc' "
				+ " and s.channelsn  NOT IN( SELECT s2.channelcode FROM channelinfo s1, channelinfo s2  "
				+ " WHERE s1.channelcode = 'jfsc' AND s1.InnerChannelCode = s2.ParentInnerChanelCode ) ");
		sb.append("order by s.modifydate desc,s.orderSn desc");

		JdbcTemplateData jtd = new JdbcTemplateData();
		
		// 取得总条数
		String countSql = startCont +sb.toString();
		DataTable dt1 = new QueryBuilder(countSql).executeDataTable();
		if(dt1 == null){
			count = 0;
		}else {
			count = dt1.getRowCount();
			if (StringUtil.isNotEmpty(orderSn)) {
				int i = 0;
				for ( ; i < count; i++) {
					if (orderSn.equals(dt1.getString(i, 0))) {
						break;
					}
				}
				
				if (i < count) {
					page = i/pagesize + 1;
				}
			}
		}
		
		String endSql = " limit " + (page - 1) * pagesize + "," + pagesize;
		// 取得商品评价信息
		String selectSql = startSelect +sb.toString() + endSql;
		try {
			listComment = jtd.obtainData(selectSql);
			
			// 记录第一条未评论的订单
			int commentFlag = -1;
			int i = 0;
			String points = "";
			// 取得评价送积分数
			try {
				PointsCalculate PointsCalculate = new PointsCalculate();
				Map<String, Object> map1 = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH,
						IntegralConstant.POINT_SOURCE_COMMENT, null);
				if (map1.get(IntegralConstant.ACTION_POINTS) != null
						&& !"0".equals(map1.get(IntegralConstant.ACTION_POINTS))) {
					points = map1.get(IntegralConstant.ACTION_POINTS)
							.toString();
				}
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
			
			// 投保目的
			Map<String , Map<String, String>> purposeMap = new HashMap<String , Map<String, String>>();
			String productType = "";
			for (Map<String, Object> map : listComment) {
				// 取得产品图片URL	
				map.put("imgPath", Config.getValue("ProductResourcePath") + map.get("logo"));
				if (commentFlag == -1 && map.get("commentId") == null) {
					commentFlag = i;
				}
				// 有要评论的指定的订单的情况，找到要评论的订单修改相应的显示样式
				if (StringUtil.isNotEmpty(orderSn)) {
					if (orderSn.equals(map.get("orderSn"))) {
						// 打开评论下拉框的样式
						map.put("commentClass", "fb_pl zk_select");
						map.put("commentDisplay", "display:");
						map.put("isCilck", map.get("orderSn"));
					}
				}
				
				if (StringUtil.isEmpty(map.get("Integral"))) {
					map.put("commentDis3", "none");
					map.put("Integral", points);
				}
				
				if (map.get("commentId") == null) {
					notCommOrderSn += map.get("orderSn")+",";
					map.put("commentDis1", "none");
					map.put("commentDis2", "");
					map.put("trCommentClass", "pl_con_boxs");
					map.put("commentClass", "fb_pl");
					
				} else {
					map.put("commentDis2", "none");
					map.put("commentDis1", "");
				}
				// 取得投保目的
				productType = (String)map.get("ProductType");
				if (StringUtil.isNotEmpty(productType)) {
					if (purposeMap.containsKey(productType)) {
						map.put("purpose", purposeMap.get(productType));
					} else {
						purposeMap.put(productType, getPurpose(productType));
						map.put("purpose", purposeMap.get(productType));
					}
				}
				
				i++;
			}
			
			// 未有要评论的指定订单、并且有未评论的情况，第一条未评论的订单展开评论框
			/*if (!flag && commentFlag >= 0) {
				listComment.get(commentFlag).put("commentClass", "fb_pl zk_select");
				listComment.get(commentFlag).put("commentDisplay", "display:");
			}*/
			
			if (StringUtil.isNotEmpty(notCommOrderSn)) {
				notCommOrderSn = notCommOrderSn.substring(0, notCommOrderSn.length());
			}
			this.lastpage = ((count + pagesize-1) / (pagesize));
			
			Map<String, String> param_map = new HashMap<String, String>();
			param_map.put("totalCounts", String.valueOf(count));
			page_Index = String.valueOf(page);
			page_count = String.valueOf(lastpage);
			getPageDataList(param_map);
			
			return "query";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}
	}
	
	public Map<String, String> getPurpose(String productType) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtil.isEmpty(productType)) {
			return map;
		}
		String param = ("Comment."+productType+"Type");
		DataTable dt = new QueryBuilder("select CodeValue, CodeName from zdcode where CodeType=? and ParentCode=? order by CodeOrder asc ", param, param).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				map.put(dt.getString(i, 0), dt.getString(i, 1));
			}
		}
		
		return map;
	}
	
	/**
	 * ajax方式提交评论
	 * 
	 * @return 销售量
	 */
	public String memberSubmit() {
		CommentAction commentAction = new CommentAction();
		return commentAction.commentSubmit();
	}
}
