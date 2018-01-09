package com.sinosoft.framework.controls;

import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Current;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.lang.reflect.Method;
import java.util.regex.Matcher;

public class InitTag extends BodyTagSupport {
	private static final Logger logger = LoggerFactory.getLogger(InitTag.class);

	private static final long serialVersionUID = 1L;

	private String method;

	private Mapx map;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
		/* ${_ZVING_LICENSE_CODE_} */
	}

	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			if (method == null || method.equals("")) {
				map = ServletUtil.getParameterMap(request);
			} else {
				int index = method.lastIndexOf('.');
				String className = method.substring(0, index);
				method = method.substring(index + 1);
				Class c = Class.forName(className);
				Method m = c.getMethod(method, new Class[] { Mapx.class });
				Mapx params = ServletUtil.getParameterMap(request);
				Object o = m.invoke(null, new Object[] { params });
				if (o == null) {
					o = new Mapx();
				}
				if (!(o instanceof Mapx)) {
					throw new RuntimeException("调用z:init指定的method时发现返回类型不是Mapx");
				}
				map = (Mapx) o;
				Current.setVariable("InitParams", map);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		//return EVAL_PAGE;
		return EVAL_BODY_BUFFERED;
	}

	public int doAfterBody() throws JspException {
		String content = this.getBodyContent().getString();
		try {
			content = HtmlUtil.replacePlaceHolder(content, map, true);
			// 替换${@Field}
			Matcher matcher = Constant.PatternSpeicalField.matcher(content);
			StringBuffer sb = new StringBuffer();
			int lastEndIndex = 0;
			while (matcher.find(lastEndIndex)) {
				sb.append(content.substring(lastEndIndex, matcher.start()));
				Object v = map.get(matcher.group(1));
				if (v != null && !v.equals("")) {
					if (matcher.group().indexOf('#') > 0) {
						sb.append(StringUtil.javaEncode(v.toString()));
					} else {
						sb.append(v.toString());
					}
				} else {
					sb.append(content.substring(matcher.start(), matcher.end()));
				}
				lastEndIndex = matcher.end();
			}
			sb.append(content.substring(lastEndIndex));
			content = sb.toString();
			this.getPreviousOut().print(content);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return EVAL_PAGE;
		
	}
}
