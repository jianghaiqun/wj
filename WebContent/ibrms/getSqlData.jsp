<%@include file="../Include/Init.jsp"%>
<%@ page import="com.sinosoft.framework.*"%>
<%

		try {
			String sql = request.getParameter("SQL");

			DataTable dt = null;

			dt = new QueryBuilder(sql).executeDataTable();

			StringBuffer sb = new StringBuffer();
			sb.append("0|" + dt.getRowCount() + "^");
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < dt.getColCount(); j++) {
					if (j == dt.getColCount() - 1) {
						sb.append(dt.getString(i, j));
					} else {
						sb.append(dt.getString(i, j) + "|");
					}
				}
				if (i != dt.getRowCount() - 1) {
					sb.append("^");
				}
			}
			response.getWriter().write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("");
		}

%>
