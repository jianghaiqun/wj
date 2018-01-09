<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>专题静态页面</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function publishProductSortInsureTopic()
{
	Dialog.confirm
	(
		"您确认要生成保险公司专题静态页面吗，生成时间可能较长？",
		function()
		{
			Server.sendRequest(
				"com.sinosoft.cms.template.custom.ProductSortInsureTopicPublish.publish",
				null,
				function(response)
				{
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在生成...");
					p.show();
				}
			);
		}
	);
}
function publishOne()
{
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要发布的保险公司！");
		return;
	}
	Dialog.confirm
	(
		"您确认要生成保险公司专题静态页面吗，生成时间可能较长？",
		function()
		{
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Server.sendRequest(
				"com.sinosoft.cms.template.custom.ProductSortInsureTopicPublish.publish",
				dc,
				function(response)
				{
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在生成...");
					p.show();
				}
			);
		}
	);
}
function searchCom(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","ComName",$V("ComName"));
	DataGrid.loadData("dg1");
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
  <tr valign="top">
    <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
      <tr>
        <td valign="middle" class="blockTd"><img src="../Icons/icon018a1.gif" />专题静态页面管理</td>
      </tr>
      <tr>
        <td style="padding:0"><table width="60%" border="0" cellspacing="6" style="border-collapse: separate; border-spacing: 6px;">
          <tr>
            <td width="55%" valign="top"><table width="100%" cellpadding="0" cellspacing="0" class="dataTable">
              <tr class="dataTableHead">
                <td width="36%" height="30" align="left" type="Tree"><b>系统信息项&nbsp;</b></td>
                <td width="64%" type="Data" field="count"><b>操作按钮</b></td>
              </tr>
				<tr>
					<td>保险公司专题静态页面生成：</td>
					<td><z:tbutton id="BtnPublishZhuanTi" priv="site_browse" onClick="publishProductSortInsureTopic();">
							<img src="../Icons/icon002a9.gif" />生成全部</z:tbutton><span style="color:#ff0000 ">为保险公司专题提供静态页面</span></td>
				</tr>
		</table></td>
          </tr>
        </table>
          <br>
          <p>&nbsp;</p>
          </td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
      		<tr>
				<td style="padding:2px 10px;">
					<div style="float:left;"> 
			    		 保险公司名称&nbsp;<input name="ComName" type="text" id="ComName"> &nbsp;
			    		<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="searchCom()"> &nbsp; &nbsp;
			    		<input type="button" name="Submitbutton1" id="Submitbutton1" value="生成" onClick="publishOne()">
				    </div>
				</td>
			</tr>
			<%-- <tr>
				<td style="padding:2px 10px;">
					<div style="float:left;"> 
						<z:tbutton onClick="publishOne()"><img src="../Icons/icon022a4.gif" width="20" height="20"/>生成</z:tbutton>
						<z:tbutton id="BtnPublishZhuanTi" priv="site_browse" onClick="publishProductSortInsureTopic();">
							<img src="../Icons/icon002a9.gif" />生成全部</z:tbutton>
				    </div>
				</td>
			</tr> --%>
			 <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="dg1" method="com.sinosoft.cms.template.custom.ProductSortInsureTopicPublish.dg1DataBind"  size="15" autoFill="false">
              <table width="60%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="6%" ztype="selector" field="id">&nbsp;</td>
                  <td style="display:none;" width="10%"><strong>ID</strong></td>
                  <td width="36%"><strong>保险公司编码</strong></td>
                  <td width="64%"><strong>保险公司名称</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" ondblclick="edit();">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td style="display:none;">${ID}</td>
                  <td>${ComCode} </td>
                  <td>${ComName}</td>
                </tr>
                <tr ztype="pagebar">
					<td colspan="6" align="left">${PageBar}</td>
				</tr>
              </table>
            </z:datagrid></td>
          </tr>
      </table></td>
    </tr>
  </table>
</body>
</html>