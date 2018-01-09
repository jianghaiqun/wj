package com.sinosoft.framework.controls;

import com.sinosoft.framework.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PageBarTag extends TagSupport {
	private static final Logger logger = LoggerFactory.getLogger(PageBarTag.class);

	private static final long serialVersionUID = 1L;

	private String target;

	private int type;// 1,2,3

	private int total;

	private int pageIndex;

	private int pageSize;

	private boolean afloat;

	public int doStartTag() throws JspException {
		try {
			total = Integer.parseInt((String) pageContext.getAttribute(target
					+ Constant.DataGridPageTotal));
			pageIndex = Integer.parseInt((String) pageContext
					.getAttribute(target + Constant.DataGridPageIndex));
			pageSize = Integer.parseInt((String) pageContext
					.getAttribute(target + Constant.Size));
			String html = null;
			html = getPageBarHtml(target, type, total, pageSize, pageIndex);
			html = html + "<script>DataGrid.setParam('" + target
					+ "','PageBarType'," + type + ");";
			if(this.afloat){
				html = html + "new Afloat($('_PageBar_" + target + "').parentNode);";
			}
			html = html + "</script>";
			type = 0;// 必须清除,同一实例会被重复使用
			pageContext.getOut().write(html);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return SKIP_BODY;
	}

	public static String getPageBarHtml(String target, int type, int total,
			int pageSize, int pageIndex) {
		if (type == 2) {
			return getType2(target, total, pageSize, pageIndex);
		} else if (type == 1) {
			return getType1(target, total, pageSize, pageIndex);
		} else {
			return getDefault(target, total, pageSize, pageIndex);
		}
	}

	private static String getDefault(String target, int total, int pageSize,
			int pageIndex) {
		StringBuffer sb = new StringBuffer();
		int totalPages = new Double(Math.ceil(total * 1.0 / pageSize))
				.intValue();
		sb
				.append("<div id='_PageBar_"
						+ target
						+ "' class=\"pageBarWrap\" style=\"clear:both;bottom:0px; height:30px; line-height:30px;\">");
		sb.append("<div class=\"pageBar\" style='float:right;font-family:Tahoma'>");
		if (pageIndex > 0) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.firstPage('"
							+ target + "');\">第一页</a>&nbsp;|&nbsp;");
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.previousPage('"
							+ target + "');\">上一页</a>&nbsp;|&nbsp;");
		} else {
			sb.append("第一页&nbsp;|&nbsp;");
			sb.append("上一页&nbsp;|&nbsp;");
		}
		if (totalPages != 0 && pageIndex + 1 != totalPages) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.nextPage('"
							+ target + "');\">下一页</a>&nbsp;|&nbsp;");
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.lastPage('"
							+ target + "');\">最末页</a>");
		} else {
			sb.append("下一页&nbsp;|&nbsp;");
			sb.append("最末页&nbsp;&nbsp;");
		}

		sb
				.append("&nbsp;&nbsp;转到第&nbsp;<input id='_PageBar_Index' type='text' class='inputText' style='width:40px' ");
		sb.append("onKeyUp=\"value=value.replace(/\\D/g,'')\">&nbsp;页");
		sb
				.append("&nbsp;&nbsp;<input type='button' onclick=\"if(!/^\\d+$/.test($V('_PageBar_Index'))"
						+ "||$V('_PageBar_Index')<1||$V('_PageBar_Index')>"
						+ totalPages
						+ "){alert('错误的页码');$('_PageBar_Index').focus();}"
						+ "else{var pageIndex = ($V('_PageBar_Index')-1)>0?$V('_PageBar_Index')-1:0;"
						+ "DataList.setParam('"
						+ target
						+ "','"
						+ Constant.DataGridPageIndex
						+ "',pageIndex);DataList.loadData('"
						+ target
						+ "');}\" class='inputButton' value='跳转'>");
		sb.append("</div>");
		sb.append("<div style='float:left;font-family:Tahoma'>");
		sb.append("共 " + total + " 条记录，每页 " + pageSize + " 条，当前第 "
				+ (totalPages == 0 ? 0 : (pageIndex + 1)) + " / " + totalPages
				+ " 页</div>");
		sb.append("<div style='clear:both'></div></div>");
		return sb.toString();
	}

	private static String getType1(String target, int total, int pageSize,
			int pageIndex) {
		StringBuffer sb = new StringBuffer();
		int totalPages = new Double(Math.ceil(total * 1.0 / pageSize))
				.intValue();
		sb
				.append("<div id='_PageBar_"
						+ target
						+ "' class=\"pageBarWrap\" style=\"clear:both;bottom:0px; height:30px; line-height:30px;\">");
		sb.append("<div class=\"pageBar\" style='float:right;font-family:Tahoma'>");
		if (pageIndex > 0) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.firstPage('"
							+ target + "');\">第一页</a>&nbsp;|&nbsp;");
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.previousPage('"
							+ target + "');\">上一页</a>&nbsp;|&nbsp;");
		} else {
			sb.append("第一页&nbsp;|&nbsp;");
			sb.append("上一页&nbsp;|&nbsp;");
		}
		if (totalPages != 0 && pageIndex + 1 != totalPages) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.nextPage('"
							+ target + "');\">下一页</a>&nbsp;|&nbsp;");
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.lastPage('"
							+ target + "');\">最末页</a>");
		} else {
			sb.append("下一页&nbsp;|&nbsp;");
			sb.append("最末页&nbsp;&nbsp;");
		}
		sb.append("</div>");
		sb.append("<div style='float:left;font-family:Tahoma'>");
		sb.append("共 " + total + " 条记录，每页 " + pageSize + " 条，当前第 "
				+ (totalPages == 0 ? 0 : (pageIndex + 1)) + " / " + totalPages
				+ " 页</div>");
		sb.append("<div style='clear:both'></div></div>");
		return sb.toString();
	}

	private static String getType2(String target, int total, int pageSize,
			int pageIndex) {
		StringBuffer sb = new StringBuffer();
		int pageCount = new Double(Math.ceil(total * 1.0 / pageSize))
				.intValue();
		int start = pageIndex - 9 < 1 ? 1 : pageIndex - 9;
		int end = pageIndex + 9 > pageCount ? pageCount : pageIndex + 9;
		if (end == 0) {
			end = 1;
		}
		sb.append("<div id='_PageBar_" + target + "'>");
		if (start > 1) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataList.previousPage('"
							+ target + "');\">上一页</a>&nbsp;");
		}
		for (int i = start; i <= end; i++) {
			if (i == pageIndex + 1) {
				sb.append("&nbsp;<span>" + i + "</span>&nbsp;");
			} else {
				sb
						.append("&nbsp;<span><a href='javascript:void(0);' onclick=\"DataList.setParam('"
								+ target
								+ "','"
								+ Constant.DataGridPageIndex
								+ "',"
								+ i
								+ ");DataList.loadData('"
								+ target
								+ "');\">" + i + "</a></span>&nbsp;");
			}
		}
		if (pageIndex < pageCount && pageCount != 1) {
			sb
					.append("&nbsp;<a href='javascript:void(0);' onclick=\"DataList.nextPage('"
							+ target + "');\">下一页</a>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isAfloat() {
		return afloat;
	}

	public void setAfloat(boolean afloat) {
		this.afloat = afloat;
	}
}
