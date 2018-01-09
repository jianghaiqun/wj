package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台Action类 - 保单处理　
 * 
 * KEY:SINOSOFT66590FE3DF97C81824D62C168A48301D
 */

@ParentPackage("member")
public class InsureAction extends BaseShopAction {
	private static final long serialVersionUID = 2553137844831167917L;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private int page = 1;
	public static final int pagesize = 10;
	private int count;
	private int lastpage;
	private String orderSn;// 订单号
	private String ldate;// 出单起期
	private String hdate;// 出单止期
	private String orderStatus;// 订单状态
	private String applicant;// 投保人
	private List<Map> listOrders;

	/**
	 * 
	 * @return 根据条件筛选查询的结果
	 */
	public String list() {
		if (getLoginMember() == null) {
			addActionError("请先登录");
			return ERROR;
		}
		
		String memberId = getLoginMember().getId();
		String sql = "select distinct a.id, a.createdate, e.codename, a.orderStatus , a.totalamount,d.productname,d.ispublish ,a.orderSn ,c.InsuranceCompany,(case when exists(select 1 from zdcode where codetype='ConfigInput' and parentcode='ConfigInput' and codevalue=c.InsuranceCompany) then '1' else '0' end) as configFlag,(case when exists(select 1 from zdcode where codetype='UWCheckClassName' and parentcode='UWCheckClassName' and codevalue=c.InsuranceCompany) then '1' else '0' end) as needUWCheckFlag from sdorders a, SDInformationRiskType b,sdinformation c, sdproduct d ,zdcode e,sdinformationappnt f where a.OrderSn=b.OrderSn and b.InformationSn=c.InformationSn and c.ProductId =d.ProductId and b.applicantSn = f.applicantSn and e.codevalue= a.orderstatus and e.codetype='orderstatus' and e.parentcode='status'and a.memberid=?";
		String sqlPart = "";
		if (ldate != null && !"".equals(ldate)) {
			sqlPart = sqlPart + "and a.createdate >= '" + ldate + " 00:00:00'";
		}
		if (hdate != null && !"".equals(hdate)) {
			sqlPart = sqlPart + "and a.createdate <= '" + hdate + " 23:59:59'";
		}
		if (orderStatus != null && !"".equals(orderStatus)
				&& !"99".equals(orderStatus)) {
			sqlPart = sqlPart + " and  e.codevalue='" + orderStatus + "'";
		}
		if (orderSn != null && !"".equals(orderSn)) {
			sqlPart = sqlPart + "and a.orderSn='" + orderSn + "'";
		}
		if (applicant != null && !"".equals(applicant)) {
			sqlPart = sqlPart + "and f.applicantname='" + applicant + "'";
		}
		String sqlPart2 = " order by createDate desc limit " + (page - 1)
				* pagesize + "," + pagesize;
		String sql3 = sql + sqlPart;
		sql = sql + sqlPart + sqlPart2;
		String[] temp = { memberId };

		JdbcTemplateData jtd = new JdbcTemplateData();

		try {
			listOrders = jtd.obtainData(sql3, temp);
			count = listOrders.size();
			listOrders = jtd.obtainData(sql, temp);
			for (Map map : listOrders) {
				map.put("KID", StringUtil.md5Hex(PubFun.getKeyValue()
						+ map.get("orderSn")));
			}
			this.lastpage = ((count + Order.DEFAULT_ORDER_LIST_PAGE_SIZE - 1) / (Order.DEFAULT_ORDER_LIST_PAGE_SIZE));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("系统异常:" + e.getMessage());
			return ERROR;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ActionUtil.dealAction("wj00038", map, getRequest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return "list";
		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

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

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getLdate() {
		return ldate;
	}

	public void setLdate(String ldate) {
		this.ldate = ldate;
	}

	public String getHdate() {
		return hdate;
	}

	public void setHdate(String hdate) {
		this.hdate = hdate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public List<Map> getListOrders() {
		return listOrders;
	}

	public void setListOrders(List<Map> listOrders) {
		this.listOrders = listOrders;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static int getPagesize() {
		return pagesize;
	}

}