
<%@page import="com.sinosoft.framework.*"%>

<%@page import="com.sinosoft.workflow.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.framework.data.*"%>
<%
String user = request.getParameter("user");
	String pwd = request.getParameter("pwd");
	
	QueryBuilder qbRole = null;
String rolehql="select  RoleCode from    zduserrole where  UserName ='"+
     user+"'";
	qbRole = new QueryBuilder(rolehql);

	String dtRole=qbRole.executeString();
	if(dtRole==null){
		dtRole="0";
	}
	
	
QueryBuilder qb = null;
String hql="select   c.auu   AS  M ,c.arole as ar,c.st  as ss  from    ("+
     "select  concat(',',b.allowUser,',')  as  auu , concat(',',b.allowRole,',')  as arole ,b.State as st    from  ZWStep  as b where  (b.State='Underway'  or  b.State='Unread'))  as  c    where c.auu  LIKE '%,"+
     user+",%'  or c.arole like '%,"+dtRole+",%'";
	qb = new QueryBuilder(hql);

	DataTable dt=qb.executeDataTable();
	int mun=0;
	if(dt!=null){
			mun=dt.getRowCount();
	}
	out.print(mun);
%>