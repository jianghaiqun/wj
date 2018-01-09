<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.sinosoft.webservice.productCode.ProductCodeWebServiceStub"%>
<%@page import="com.sinosoft.webservice.ProductWebservice"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<script src="../Framework/Main.js"></script>
<%
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("SupplierCode", request.getParameter("SupplierCode"));
	map.put("RiskProp", request.getParameter("RiskProp"));
	String RiskCode = request.getParameter("RiskCode");
	if (StringUtil.isNotEmpty(RiskCode)) {
		String[] risk = RiskCode.split(",");
		map.put("RiskCode", risk);
	}
	ProductCodeWebServiceStub.ProductCodeResponse re = ProductWebservice.ProductCodeServiceImpl(map);
	if (re == null) {
		out.println("<script>Dialog.closeEx();Dialog.alert('无同步结果');</script>");
	} else {
		ProductCodeWebServiceStub.ResultDTO rd = re.getResultDTO();
		if ("1".equals(rd.getResultCode())) {
			out.println("<script>Dialog.closeEx();Dialog.alert('"+rd.getResultInfoDesc()+"');</script>");
		} else {
			if (re.getRiskCode() == null || re.getRiskCode().length <= 0) {
				out.println("<script>Dialog.closeEx();Dialog.alert('无同步结果');</script>");
			} else {
				String[] riskcodes = re.getRiskCode();
				String risk = "";
				for(int i = 0; i < riskcodes.length; i++) {
					risk += riskcodes[i] + " ";
				}
				System.out.println(risk);
				out.println("<script>Dialog.closeEx();Dialog.alert('"+risk+"');</script>");
			}
		}
	}

%>