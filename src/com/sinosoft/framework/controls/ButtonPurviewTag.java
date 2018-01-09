package com.sinosoft.framework.controls;

import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Button;
import com.sinosoft.platform.Priv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ButtonPurviewTag extends BodyTagSupport {
	private static final Logger logger = LoggerFactory.getLogger(ButtonPurviewTag.class);

	private static final long	serialVersionUID	= 1L;

	public int doAfterBody() throws JspException {
		long start = System.currentTimeMillis();
		String content = this.getBodyContent().getString();
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isEmpty(content) || content.length() <= 3) {
			sb.append("缺少内容.");

		} else {
			String regex = "\\$\\{(.+?)\\}";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(content);
			String code = "";
			while (m.find()) {
				code = m.group(1);
			}
			Object tZDButton = Button.ButtonCacheMap.get(code);

			if (!StringUtil.isEmpty(tZDButton)) {
				DataTable dt = (DataTable) tZDButton;
				dt = dt.filter(new Filter() {
					public boolean filter(Object obj) {
						DataRow dr = (DataRow) obj;
						return Priv.getPriv(User.getUserName(), Priv.BUTTON, Application.getCurrentSiteID() + "-" + dr.getString("ParentID") + "-" + dr.getString("ID"), Priv.BUTTON_BROWSE);
					}
				});
				if (dt != null && dt.getRowCount() > 0) {
					for (int i = 0; i < dt.getRowCount(); i++) {
						DataRow dr = dt.get(i);
						String img = "<img src='../" + dr.getString("Icon") + "' width=\"20\" height=\"20\"/>";
						sb.append(TButtonTag.getHtml(dr.getString("ButtonID"), dr.getString("OnClick"), dr.getString("Priv"), img, dr.getString("Name"), ("0".equals(dr.getString("Disabled")) ? false
								: true)));
					}
				}
			}

		}
		try {
			this.getPreviousOut().print(sb);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("解析标签时间：{}", (System.currentTimeMillis() - start));
		return EVAL_PAGE;
	}
}
