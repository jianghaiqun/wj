package com.sinosoft.framework.controls;

import com.sinosoft.framework.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class TabTag extends BodyTagSupport {
	private static final Logger logger = LoggerFactory.getLogger(TabTag.class);

	private static final long serialVersionUID = 1L;

	public int doAfterBody() throws JspException {
		String content = this.getBodyContent().getString();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"6\" class=\"blockTable\">");
			sb.append("  <tr>");
			sb.append("    <td height=\"26\" valign=\"middle\" class=\"blockTd\">");
			sb.append("    <table width=\"100%\" border='0' cellpadding='0' cellspacing='0' style=\"background:url("
					+ Config.getContextPath()
					+ "Framework/Images/divchildtabBarBg.gif) repeat-x left bottom; margin-bottom:1px;\">");
			sb.append("    <tr>");
			sb.append("      <td valign=\"bottom\" height='30' style=\"padding:0 0 0 6px;_padding:0 0 0 2px;\">");
			sb.append(content);
			sb.append("			</td>");
			sb.append("   </tr>");
			sb.append("   </table>");
			sb.append("   </td>");
			sb.append("  </tr>");
			sb.append("  <tr>");
			sb.append("     <td style=\"padding:2px 6px;\">");

			String[] arr = content.split("<\\/div>\\s*<div ");
			String selectedID = null;
			for (int i = 0; i < arr.length; i++) {
				String html = arr[i].trim();
				if (i == 0) {
					html += "</div>";
				} else if (i == arr.length - 1) {
					html = "<div " + html;
				} else {
					html = "<div " + html + "</div>";
				}
				HtmlDiv div = new HtmlDiv();
				div.parseHtml(html);
				if (div.getAttribute("class").equals("divchildtabCurrent")) {
					sb.append("<iframe src=\"" + div.getAttribute("src")
							+ "\" width=\"100%\" height=\"0\" id=\"_ChildTabFrame_" + div.getAttribute("id")
							+ "\" frameborder=\"0\" scrolling=\"auto\" allowtransparency=\"true\"></iframe>");
					selectedID = div.getAttribute("id").toString();
				} else {
					sb.append("<iframe src=\"" + div.getAttribute("src")
							+ "\" width='100%' height='0' id=\"_ChildTabFrame_" + div.getAttribute("id")
							+ "\" frameborder=\"0\" scrolling=\"auto\" allowtransparency=\"true\"></iframe>");
				}
			}
			sb.append("     </td>");
			sb.append("  </tr>");
			sb.append("</table>");
			HtmlScript script = new HtmlScript();
			script.setInnerHTML("Page.onLoad(function(){Tab.initFrameHeight(\"_ChildTabFrame_" + selectedID
					+ "\");},5);");
			sb.append(script.getOuterHtml());
			this.getPreviousOut().print(sb);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return EVAL_PAGE;
	}
}
