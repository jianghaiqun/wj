<%--
  Created by IntelliJ IDEA.
  User: dongsheng
  Date: 2017/6/12
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../Include/Init.jsp" %>
<%@ page import="com.sinosoft.cms.settlement.FinancialSettlementStatisticsImport" %>
<%
    FinancialSettlementStatisticsImport importer = new FinancialSettlementStatisticsImport();
    importer.fileUpload(request, response);
%>
