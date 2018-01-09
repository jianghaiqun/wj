package com.sinosoft.cms.present;

import com.sinosoft.forward.ShopDispatchAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import net.sf.json.JSONObject;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PresentAction extends ShopDispatchAction {
	private final static int PAGECOUNT = 3;

	/**
	 * 前台礼品列表页面;动态ajax加载
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String PresentList(ServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String PresentType = request.getParameter("PresentType");
		String IntegralStart = request.getParameter("IntegralStart");
		String IntegralEnd = request.getParameter("IntegralEnd");

		String page_ = request.getParameter("page");
		int page = 1;
		if (null != page_ && !"".equals(page_)) {
			page = Integer.parseInt(page_);

		}

		int start = (page - 1) * PAGECOUNT;
		int end = page * PAGECOUNT;

		String sql = " select listcontent from present  where 1=1   ";
		if (isNum(IntegralStart)) {
			sql += " and point>= " + IntegralStart;
		}
		if (isNum(IntegralEnd)) {
			sql += " and point<= " + IntegralEnd;
		}

		String temp = "";
		DataTable presentDt = new QueryBuilder(sql).executeDataTable();

		int total = 0;
		if (presentDt != null)
			total = presentDt.getRowCount();
		if (end > total)
			end = total;
		int record = (total / PAGECOUNT) + ((total % PAGECOUNT) == 0 ? 0 : 1);
		if (presentDt == null || record == 0) {
			map.put("content", "<font color='red'>未查询到礼品!</font>");
			return JSONObject.fromObject(map).toString();
		}

		if (record != 0 && page > record) {
			map.put("content", "<font color='red'>错误的页数!</font>");
			return JSONObject.fromObject(map).toString();
		}

		for (int j = start; j < end; j++) {
			temp += presentDt.getString(j, "listcontent");
		}

		logger.info("礼品列表：{} -> {}", start, end);
		logger.info("当前页数{} 总页数：{}", page, record);
		map.put("content", "<ul>" + temp + "</ul>" + getPageBar(total, PAGECOUNT, page, record));
		return JSONObject.fromObject(map).toString();
	}

	/**
	 * 分页函数
	 * 
	 * @param Total
	 * @param PageSize
	 * @param PageIndex
	 * @param PageCount
	 * @return
	 */
	public String getPageBar(int Total, int PageSize, int PageIndex, int PageCount) {
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' border='0' class='noBorder' align='center'><tr>");
		sb.append("<td height='18' align='center' valign='middle' style='border-width: 0px;color:#525252'>");
		sb.append("共" + Total + "条记录，每页" + PageSize + "条，当前第<span class='fc_ch1'>" + + ((Total == 0) ? 0: (PageIndex + 1)) + "</span>/<span class='fc_ch1'>" + PageCount + "</span>页&nbsp;&nbsp;&nbsp;&nbsp;");
		if (PageIndex > 1) {
			sb.append("<a href='#' onclick='page(1);'><span class='fc_ch1'>第一页</span></a>|");
			sb.append("<a href='#' onclick='page(" + (PageIndex - 1) + ");'><span class='fc_ch1'>上一页</span></a>|");
		} else {
			sb.append("<span class='fc_hui2'>第一页</span>|");
			sb.append("<span class='fc_hui2'>上一页</span>|");
		}
		if (PageIndex != PageCount && PageCount > 1) {
			sb.append("<a href='#' onclick='page(" + (PageIndex + 1) + ");'><span class='fc_ch1'>下一页</span></a>|");
			sb.append("<a href='#' onclick='page(" + (PageCount) + ");'><span class='fc_ch1'>最末页</span></a>");
		} else {
			sb.append("<span class='fc_hui2'>下一页</span>|");
			sb.append("<span class='fc_hui2'>最末页</span>");
		}

		sb.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_0'  type='text' size='4' style='width:30px' ");
		sb.append("style='' >页");
		sb.append("<script type=\"text/javascript\" >function openpage(){var page = document.getElementById('_PageBar_Index_0').value;");
		sb.append("if(page>=1 && page<=").append(PageCount).append("){ page(page);}else {alert('错误的页码!');}");
		sb.append("}</script>");

		sb.append("&nbsp;&nbsp;<input type='button' onclick='openpage();'  style='' value='跳转'></td>");
		sb.append("</tr></table>");
		return sb.toString();
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param var
	 * @return
	 */
	public static boolean isNum(String var) {
		if (null == var || "".equals(var))
			return false;

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(var);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}
}
