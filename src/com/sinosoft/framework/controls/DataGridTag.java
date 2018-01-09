/**
 */
package com.sinosoft.framework.controls;

import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Current;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class DataGridTag extends BodyTagSupport {
	private static final Logger logger = LoggerFactory.getLogger(DataGridTag.class);

	private static final long serialVersionUID = 1L;

	private String method;

	private String sql;

	private String id;

	private boolean page = true;

	private int size;

	private boolean multiSelect = true;

	private boolean autoFill = true;

	private boolean scroll = false;

	private boolean lazy = false;// 默认是否加载数据

	private int cacheSize;

	public void setPageContext(PageContext pc) {
		super.setPageContext(pc);
		this.method = null;
		this.sql = null;
		this.id = null;
		this.page = true;
		this.size = 0;
		this.multiSelect = true;
		this.autoFill = true;
		this.scroll = false;
		this.lazy = false;
		this.cacheSize = 0;
	}

	public int doAfterBody() throws JspException {
		BodyContent body = this.getBodyContent();
		String content = body.getString().trim();
		try {
			if (StringUtil.isEmpty(method) && StringUtil.isEmpty(sql)) {
				throw new RuntimeException("DataGrid既未指定Action，亦未指定SQL");
			}

			DataGridAction dga = new DataGridAction();
			dga.setMethod(method);
			dga.setTagBody(content);

			dga.setID(id);
			dga.setPageFlag(page);
			dga.setMultiSelect(multiSelect);
			dga.setAutoFill(autoFill);
			dga.setScroll(scroll);
			dga.setCacheSize(cacheSize);
			dga.setLazy(lazy);

			HtmlTable table = new HtmlTable();
			table.parseHtml(content);
			dga.setTemplate(table);
			dga.parse();

			if (page) {
				dga.setPageIndex(0);
				if (StringUtil.isNotEmpty(dga.getParam(Constant.DataGridPageIndex))) {
					dga.setPageIndex(Integer.parseInt(dga.getParam((Constant.DataGridPageIndex))));
				}
				if (dga.getPageIndex() < 0) {
					dga.setPageIndex(0);
				}
				dga.setPageSize(size);
			}

			if (lazy) {
				dga.bindData(new DataTable());// 默认不加载
			} else {
				HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
				HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

				Current.init(request, response, method);
				dga.setParams(Current.getRequest());
				dga.Response = Current.getResponse();

				if (StringUtil.isNotEmpty(sql)) {
					method = "com.sinosoft.framework.controls.DataGridPage.sqlBind";
					dga.getParams().put(Constant.DataGridSQL, sql);
				}

				Current.invokeMethod(method, new Object[] { dga });
			}
			getPreviousOut().write(dga.getHtml());
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
		return EVAL_PAGE;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

	public boolean isAutoFill() {
		return autoFill;
	}

	public void setAutoFill(boolean autoFill) {
		this.autoFill = autoFill;
	}

	public boolean isScroll() {
		return scroll;
	}

	public void setScroll(boolean scroll) {
		this.scroll = scroll;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}
}
