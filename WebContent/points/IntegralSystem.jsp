<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分设置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
 

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if( ele.id == 'PointDes' || ele.id == 'MemberAct'|| ele.id == 'Submitbutton'){
			search();
		}
	}
}

function search(){
	DataGrid.setParam("integral_dg1",Constant.PageIndex,0);
	DataGrid.setParam("integral_dg1","MemberAct",$V("MemberAct"));
	DataGrid.setParam("integral_dg1","PointDes",$V("PointDes"));
	DataGrid.loadData("integral_dg1");
}

Page.onLoad(function(){
	$("integral_dg1").afterEdit = function(tr,dr){
		
		if(Verify.hasError()){
	 		return;
    	}
		
		dr.set("GivePoints",$V("GivePoints"));
		dr.set("BuyPoints",$V("BuyPoints"));  
		
		
		return true;
	}
});

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 300;
	diag.Title = "新建积分规则";
	diag.URL = "points/IntegralAddDialog.jsp";
	diag.onLoad = function(){
		  
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form1");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.points.IntegralSystem.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("integral_dg1");
			}
		});
	});
}


function del(){
	var arr = DataGrid.getSelectedValue("integral_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("删除后可能影响流程，确认要删除？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.points.IntegralSystem.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("integral_dg1");
				}
			});
		});
	});
}


function edit(){
	var arr = DataGrid.getSelectedValue("integral_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 350;
	diag.Title = "编辑" ;
	diag.URL = "points/IntegralEditDialog.jsp?Id="+arr[0];
	diag.onLoad = function(){
		$DW.$("ElementName").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }
	
	Server.sendRequest("com.sinosoft.points.IntegralSystem.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("integral_dg1");
			}
		})
	});
}

</script>
</head>
<body>
<z:init method="com.sinosoft.points.IntegralSystem.initIntegralSystem">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
    	  <tr>
					<td style="padding:0 8px 4px;">
		    			 <z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>新建</z:tbutton>
		    			 <z:tbutton onClick="edit()"><img src="../Icons/icon022a4.gif" width="20" height="20"/>编辑</z:tbutton>
       		   			 <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
            		</td>
          </tr>
          <tr>
				<td style="padding:2px 10px;">
					<div style="float:left;"> 
			    		 会员行为&nbsp;<z:select id='MemberAct'>${MemberActList}</z:select>&nbsp;
						 规则描述&nbsp;<input name="PointDes" type="text" id="PointDes"> &nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				    </div>
				</td>
			</tr>
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="integral_dg1" method="com.sinosoft.points.IntegralSystem.IntegralSystemSearch"  size="15" autoFill="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="20%"><strong>会员行为</strong></td>
                  <td width="20%"><strong>会员等级</strong></td>
                  <td width="10%"><strong>积分赠送规则</strong></td>
                  <td width="20%"><strong>时间</strong></td>
                  <td width="5%"><strong>状态</strong></td>
                  <td width="5%"><strong>积分数</strong></td>
                  <td width="30%"><strong>文案描述</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC"  >
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${MemberActName}</td>
                  <td>${gradeName}</td>
                  <td>${PointsGiveName}</td>
                  <td>${StartDate}-${EndDate}</td>
                  <td> ${Status}</td>
                  <td> ${PointsNum}</td>
                  <td> ${PointDes}</td>
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
 </z:init>
</body>
</html>
