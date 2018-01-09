<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="org.json.JSONArray"%>
<%@ page import="org.json.JSONObject"%>
<%
    String sql = request.getParameter("sql");
    System.out.println("##################查询后台的SQL语句是#################");
    System.out.println(sql);
    DataTable dt = null;

	dt = new QueryBuilder(sql).executeDataTable();
    
    JSONArray array = new JSONArray();
   
    for(int i=0;i<dt.getRowCount();i++){
    	Map map = new HashMap() ;
    	map.put("code",dt.getString(i,0));
    	map.put("name",dt.getString(i,0)+"-"+dt.getString(i,1));
    	array.put(map);
    }
   
    JSONObject obj = new JSONObject();
   
  
   obj.put("rows",array);
   String json= obj.toString();
   System.out.println(json);
   response.getWriter().write(json);
   
%>

