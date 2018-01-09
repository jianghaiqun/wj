<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script>
var Type = "<%=request.getParameter("Type")%>";
function getSiteInfo(func){
	var url = $V("ServerAddr");
	var dc = new DataCollection();
	dc.add("ServerAddr",url);
	dc.add("Type",Type);
	Dialog.wait("请等待...");
	Server.sendRequest("com.sinosoft.datachannel.CatalogInfoService.getRemoteSiteInfo",dc,function(response){
		Dialog.endWait();
		var dt = response.get("SiteTable");
		$("SiteID").clear();
		if(dt){
			for(var i=0;i<dt.getRowCount();i++){
		   		$("SiteID").add(dt.Rows[i].get("Name"),dt.Rows[i].get("ID"));
			}
			$("SiteID").selectedIndex = 0;
			if(func){
				func();
			}
		}else{
			Dialog.alert("获取数据失败!");	
		}
	});
}
function getCatalogInfo(func){
	if(!$V("SiteID")){
		return;
	}
	var url = $V("ServerAddr");
	var dc = new DataCollection();
	dc.add("ServerAddr",url);
	dc.add("Type",Type);
	dc.add("SiteID",$V("SiteID"));
	Dialog.wait("请等待...");
	Server.sendRequest("com.sinosoft.datachannel.CatalogInfoService.getRemoteCatalogInfo",dc,function(response){
		Dialog.endWait();
		var dt = response.get("CatalogTable");		
		$("CatalogID").clear();
		if(dt){
			for(var i=0;i<dt.getRowCount();i++){
			   $("CatalogID").add(dt.Rows[i].get("Name"),dt.Rows[i].get("ID"));
			}
			$("CatalogID").selectedIndex = 0;
			if(func){
				func();
			}
		}else{
			Dialog.alert("获取数据失败!");	
		}
	});
}

</script>
</head>
<body class="dialogBody">
<form id="form2">
<table width="100%" align="center" cellpadding="2" cellspacing="0">
	<tr>
	  <td height="25" valign="middle" class="tdgrey1">&nbsp;&nbsp;</td>
    </tr>
    <tr>
	  <td height="35" valign="middle" class="tdgrey1">&nbsp;&nbsp;选择要分发到的网站群栏目</td>
    </tr>
    <tr>
	  <td height="15" valign="middle" class="tdgrey1">&nbsp;&nbsp;</td>
    </tr>
	<tr>
	  <td height="35" align="center" class="tdgrey2">
        <table width="98%" cellpadding="2" cellspacing="0" class="dataTable">
          <tr ztype="head" class="dataTableHead">
            <td width="1%" ztype="Selector" field="MD5"></td>
            <td width="49%"><b>站点名称</b></td>
            <td width="49%"><b>栏目名称</b></td>
            <td width="1%"></td>
          </tr>
         <tr>
         	<td></td>
         	<td>
         	<z:select id="SiteID" verify="NotNull" listHeight="300" onChange="getCatalogInfo()" 
         	style="width:160px" method="com.sinosoft.datachannel.CatalogInfoService.getLocalSites">
         	 </z:select>
         	 <input type="button" name="Submit" value="获取站点" onClick="getSiteInfo()">
         	</td>
         	<td>
         		<z:select id="CatalogID" verify="NotNull" listHeight="300" style="width:160px"> 
         		</z:select>
         		<input type="button" name="Submit2" value="获取栏目" onClick="getCatalogInfo()"> 
         	</td>
         	<td height="35" class="tdgrey2" style="display: none;">
         	<input name="ServerAddr" type="text" verify="NotNull" id="ServerAddr" value="localhost" style="width:250px">
         	</td>
         </tr>
        </table>
	    </td>
    </tr>
</table>
</form>
</body>
</html>