<%@page import="java.util.HashMap" %>
<%@page import="java.io.File" %>
<%@page import="com.sinosoft.framework.data.DataTable" %>
<%@page import="com.sinosoft.framework.data.QueryBuilder" %>
<%@page import="com.sinosoft.framework.utility.FileUtil" %>
<%@page import="com.sinosoft.framework.utility.StringUtil" %>
<%
	/*
	File file = new File(application.getRealPath("Services/bxppB1ProductList.html"));
	String text = FileUtil.readText(file).replaceAll("'", "\\\\'").replaceAll("\r\n", "\n").replaceAll("^\n$","");
	int index = text.indexOf("\n");
	int lastIndex = -1;
	while (index != -1)
	{
		String tt = text.substring(lastIndex + 1, index);
		//System.out.println("----"+tt.length()+"----"+tt);
		if (StringUtil.isNotEmpty(tt))
		{
			out.println("document.write(\'" + tt + "\');");
		}
		lastIndex = index;
		index = text.indexOf("\n", index + 1);
	}
	*/
	//保险公司简称
	String alias = request.getParameter("alias");
	//保险公司专题文章id和保险公司编码
	String parentcode = request.getParameter("id");
	//指定险种类别编码
	String riskType = request.getParameter("producttype");
	
	String sql = "select codevalue type,(select codename from zdcode z2 where z2.codetype='ProductType' and z2.parentcode='ProductType' and z2.codevalue=z1.codevalue) name,z1.codeorder count,memo href from zdcode z1 where codetype='ArticleRelaLink' and parentcode=? order by codevalue";
	QueryBuilder qb = new QueryBuilder(sql);
	qb.add(parentcode);
	DataTable dt = qb.executeDataTable();
	int typeCont = 0;
	out.println("document.write(\'<h3 class=\"title_left\"><i class=\"icon\"></i>产品分类</h3>\\\n\\\t<div class=\"con_box_left\">\\\n\\\t\\\t<ul>\');");
	if ((null != dt) && (0 < (typeCont = dt.getRowCount())))
	{
		int j = 0;
		for (int i=0; i<typeCont; i++)
		{
			StringBuffer sb = new StringBuffer();
			String type = dt.getString(i, 0);
			String name = dt.getString(i, 1);
			String count = dt.getString(i, 2);
			String href = dt.getString(i, 3);
			//一级栏目显示
			if (type.endsWith("00"))
			{
				//查询二级栏目下有无产品
				String tynow =type.substring(0,1);
				String queryCount = "SELECT codeorder  FROM zdcode WHERE parentcode='"+parentcode+"' AND codevalue LIKE '"+tynow+"%'  AND codevalue !='"+tynow+"00'";
				QueryBuilder qbr = new QueryBuilder(queryCount);
				DataTable dte = qbr.executeDataTable();
				int countAll=0;
				if(dte!=null && dte.getRowCount()>0){
					for(int k=0; k<dte.getRowCount(); k++){
						countAll += Integer.parseInt(dte.getString(k, "codeorder"));
					}
				}
				//二级栏目下有产品
				if(countAll>0){
				if (i>0)
				{
					sb.append("</dl>");
				}
				sb.append("<dl class=\"bx_l_list\"><dt>");
				sb.append("<a href=\"").append(href).append("\">").append(alias).append(name).append("</a>");
				sb.append("</dt>");
				j++;
				}
			}//二级栏目显示
			else
			{
				if (!"0".equals(count))
				{
					sb.append("<dd>");
					sb.append("<a href=\"").append(href).append("\" ");
					if (type.equalsIgnoreCase(riskType))
					{
						sb.append(" style=\"color:red;\" ");
					}
					sb.append(">").append(name).append("<em>(").append(count).append(")</em>").append("</a>");
					sb.append("</dd>");
				}
				
			}

			out.println("document.write(\'\\\n\\\t\\\t" + sb.toString() + "\');");
		}
	}
	out.println("document.write(\'\\\n\\\t</div>\');");
%>