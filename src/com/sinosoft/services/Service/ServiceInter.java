package com.sinosoft.services.Service;

public interface ServiceInter {
	public String service(String tXMLStr);
	public String Insurance (String tXMLStr);
	public String DataUpload(String tXMLStr);
	public String other(String tXMLStr) throws Exception;
	public String GtBaoDanNo(String tXMLStr);
	public String cardCheck(String tXMLStr);
	public String cancelTry(String tXMLStr);
	public String serviceNew (String tXMLStr);
	public String downloadInvoice(String jsonStr);
//	public String upLoadFileToMaYi(String startDate, String endDate);
	public String execute(String transData);
	
}
