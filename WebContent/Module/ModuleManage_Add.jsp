<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>元素管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>


function search(){
	DataGrid.setParam("module_dg5",Constant.PageIndex,0);
	DataGrid.setParam("module_dg5","ElementType",$V("ElementType"));
	DataGrid.setParam("module_dg5","ElementName",$V("ElementName"));
	DataGrid.setParam("module_dg5","ElementContent",$V("ElementContent"));
	DataGrid.loadData("module_dg5");
}

</script>
</head>
<body>
<z:init method="com.sinosoft.module.Module.initModuleList">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
          <tr>
            <td valign="middle" class="blockTd"><img src="../Icons/icon022a1.gif" width="20" height="20" />元素列表</td>
          </tr>
          <tr>
				<td style="padding:2px 10px;">
					<div style="float:left;"> 
			    		 元素类型&nbsp;<z:select id='ElementType'>${ElementTypeList}</z:select>&nbsp;
						 元素名称&nbsp;<input name="ElementName" type="text" id="ElementName"> &nbsp;
						 元素内容&nbsp;<input name="ElementContent" type="text" id="ElementContent"> &nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				    </div>
				</td>
			</tr>
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="module_dg5" method="com.sinosoft.module.Module.dg1DataBind" page="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="25%"><strong>元素名称</strong></td>
                  <td width="10%"><strong>元素类型</strong></td>
                  <td width="17%"><strong>备注</strong></td>
                  <td width="15%"><strong>创建时间</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" ondblclick="edit();">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${ElementName}</td>
                  <td>${ElementTypeName} </td>
                  <td>${Memo}</td>
                  <td> ${CreateDate}</td>
                </tr>
              </table>
            </z:datagrid></td>
          </tr>
      </table></td>
    </tr>
  </table>
 </z:init>
</body>
</html>
