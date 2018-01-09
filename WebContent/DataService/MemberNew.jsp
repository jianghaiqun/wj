<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>

function addSave(){
	if($DW.Verify.hasError()){
	  return;
     }
	var Password=$DW.$V("Password");
	if(!/^((\d+[a-zA-Z_]\w*)|([a-zA-Z_]+\d\w*))$/.test(Password)){
		Dialog.alert("密码只能由6-16个英文、数字、及下划线任两个组合组成！");
		return;
	}
	var dc = $DW.Form.getData("form2");
	// dc.add("Gender",$DW.$NV("Gender"));
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.add",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
		if(response.Status==1){
			$D.close();
			DataGrid.loadData('dg1');
		}
		});
	});
}

function view(id){
    var arr = DataGrid.getSelectedValue("dg1");
	if(id&&id!=""){
		arr = new Array();
		arr[0] = id;
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要查看的会员！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 1200;
	diag.Height = 600;
	diag.Title = "会员信息查看";
	diag.URL = "DataService/MemberEditDialog.jsp?flag=0&id="+arr[0];
	diag.onLoad = function(){
		$DW.init();
	};  
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看会员信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","realName",$V("realName"));
	DataGrid.setParam("dg1","SEmail",$V("SEmail"));
	DataGrid.setParam("dg1","applicantName",$V("applicantName"));
	DataGrid.setParam("dg1","applicantEmail",$V("applicantEmail"));
	DataGrid.setParam("dg1","mobileNo",$V("mobileNo"));
	DataGrid.setParam("dg1","IDNO",$V("IDNO"));
	DataGrid.setParam("dg1","orderSn",$V("orderSn"));
	DataGrid.setParam("dg1","standardDate",$V("standardDate"));
	DataGrid.setParam("dg1","statisticsDate",$V("statisticsDate"));
	DataGrid.setParam("dg1","fromWap",$V("fromWap"));
	
	
	DataGrid.setParam("dg1","createDate",$V("createDate"));
	DataGrid.setParam("dg1","endCreateDate",$V("endCreateDate"));


	if( $V("createDate") == null || $V("createDate") == "" ){
		Dialog.alert("请填写会员创建时间！");
		return;
	}
	if($V("endCreateDate") == null || $V("endCreateDate") == "" ){
		Dialog.alert("请填写会员创建时间！");
		return;
	}
	if( $V("standardDate") == null || $V("standardDate") == "" ){
		Dialog.alert("请填写基准时间！");
		return;
	}
	if($V("statisticsDate") == null || $V("statisticsDate") == "" ){
		Dialog.alert("请填写统计时间段！");
		return;
	}

	if($V("REnd") != null && parseInt($V("statisticsDate")) < parseInt($V("REnd"))){
		Dialog.alert("统计时间必须大于R的结束时间！");
		return;
	}
	// 积分区间
	DataGrid.setParam("dg1","pointsStart",$NV("pointsStart"));
	DataGrid.setParam("dg1","pointsEnd",$NV("pointsEnd"));
	// 团单数区间
	DataGrid.setParam("dg1","groupStart",$NV("groupStart"));
	DataGrid.setParam("dg1","groupEnd",$NV("groupEnd"));
	// 会员渠道
	DataGrid.setParam("dg1","channelsn",$NV("contant"));
	// R值区间
	DataGrid.setParam("dg1","RStart",$NV("RStart"));
	DataGrid.setParam("dg1","REnd",$NV("REnd"));
	// M值区间
	DataGrid.setParam("dg1","MStart",$NV("MStart"));
	DataGrid.setParam("dg1","MEnd",$NV("MEnd"));
	// F值区间
	DataGrid.setParam("dg1","FStart",$NV("FStart"));
	DataGrid.setParam("dg1","FEnd",$NV("FEnd"));
	// 累计实际支付金额区间
	DataGrid.setParam("dg1","amountStart",$NV("amountStart"));
	DataGrid.setParam("dg1","amountEnd",$NV("amountEnd"));
	//累计实际购买次数区间
	DataGrid.setParam("dg1","countStart",$NV("countStart"));
	DataGrid.setParam("dg1","countEnd",$NV("countEnd"));
	DataGrid.setParam("dg1","vipCheckbox",$V("vipCheckbox"));
	DataGrid.setParam("dg1","checkvip",$NV("checkvip"));
	
	DataGrid.setParam("dg1","Search","search");
	DataGrid.loadData("dg1");
}

function doCheck(status){
	if(status==""){
		return;
	}
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要审核的会员！");
		return;
	}
	var dc = new DataCollection();	
	dc.add("UserNames",arr.join(","));
	//dc.add("Status",status);
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.doCheck",dc,function(){
		var response = Server.getResponse();
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			DataGrid.loadData('dg1');
		}
	});
}

function checkAddress(username){
	var diag = new Dialog("Diag1");
	diag.Width = 750;
	diag.Height = 320;
	diag.Title = "会员地址列表";
	diag.URL = "DataService/MemberAddr.jsp?UserName="+username;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}
function doCollect(id){
    var arr = DataGrid.getSelectedValue("dg1");
	if(id&&id!=""){
		arr = new Array();
		arr[0] = id;
		alert(id);
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择查询的产品所属会员！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 360;
	diag.Title = "查看修改产品信息";
	diag.URL = "DataService/Mycollect.jsp?id="+arr[0];
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看修改产品信息";
	//diag.Message = "修改会员级别、姓名、分数、折扣等";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function searchPoint() {
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要查看积分明细所属的会员！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 1100;
	diag.Height = 450;
	diag.Title = "查看积分明细";
	diag.URL = "DataService/MemIntegralDetail.jsp?memberId="+arr[0];
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看积分明细";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function orderDetail(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择订单所属会员！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 1100;
	diag.Height = 450;
	diag.Title = "查看会员订单信息";
	diag.URL = "DataService/MemberOrderInquiry.jsp?memberid="+arr[0];
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看会员订单信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}
function sendResetPWD(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择一条记录！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var data = DataGrid.getSelectedData("dg1");
	var dc = new DataCollection();	
	var email = data.get(0,"email");
	dc.add("email",email);
	//dc.add("Status",status);
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.sendPwdResetRUL",dc,function(){
		var response = Server.getResponse();
		if(response.Status==1){
			Dialog.alert(response.Message);
			DataGrid.loadData('dg1');
		}else{
			Dialog.alert(response.Message);
		}
	});
	
	// fm1.mobileNOOrEmail.value = email;
	// fm1.action = "";
	// fm1.submit();
	// location.href = sinosoft.base+"/shop/member!sendPasswordRecoverMail.action?mobileNoOrEmail="+email;
}
function showMemberVip () {
	if (jQuery('#vipCheckbox').attr('checked')) {
		jQuery("#memberVip").show();
	} else {
		jQuery("#memberVip").hide();
	}
}
function showchannel(){
	var check_flag=jQuery('#showchannel').is(':checked');
	if(check_flag==true){
		jQuery("#channeltree").show();
	}else{
		jQuery("#channeltree").hide();
	}
}
</script>

</head>
<body>
<form action="../shop/member!sendPasswordRecoverMail.action" name="fm1" method="post" id="passwordRecoverForm">
	<input type="hidden" name="mobileNOOrEmail" id="mobileNOOrEmail" value="">
</form>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td id="channeltree" style="display: none">
				<table width="50" border="0" cellspacing="0" cellpadding="6"
					class="blockTable">
					<tr>
						<td style="padding: 6px;"  width="50" class="blockTd"><z:tree id="tree1"
							style="height:440px;width:120px;"
							method="com.sinosoft.platform.Channel.treeDataBindForVip"
							level="3" lazy="true" resizeable="true">
							<img src="../Framework/Images/icon_drag.gif" width="16" height="16">
							<p cid='${ID}' level="${TreeLevel}"><input type="checkbox"
								name="contant" id='contant_${ID}' value='${ChannelCode}'
								onClick="onCheck(this);"><label for="contant_${ID}"><span id="span_${ID}">${Name}</span></label></p>
						</z:tree></td>
					</tr>
				</table>
			</td>
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />会员列表</td>
				</tr>
			<tr>
				<td>
				<z:init method="com.sinosoft.cms.dataservice.Member.init">
				<table>
					<tr>
						<td>真实姓名：</td>
						<td><input name="realName" type="text" id="realName" value=""
							style="width: 90px" class="inputText"></td>
						<td>E-mail：</td>
						<td><input name="SEmail" type="text" id="SEmail" value=""
							style="width: 90px" class="inputText"></td>
						<td>投保人姓名：</td>
						<td><input name="applicantName" type="text" id="applicantName" value=""
							style="width: 90px" class="inputText"></td>
						<td>投保人邮箱：</td>
						<td><input name="applicantEmail" type="text" id="applicantEmail" value=""
							style="width: 90px" class="inputText"></td>
						<td>手机号码：</td>
						<td><input name="mobileNo" type="text" id="mobileNo" value=""
							style="width: 90px" class="inputText"></td>	
						<td>可用积分：</td>
						<td>
							<input name="pointsStart" type="text" id="pointsStart" value="" style="width: 50px" class="inputText">
							<input name="pointsEnd" type="text" id="pointsEnd" value="" style="width: 50px" class="inputText">
						</td>
						<td></td>
					</tr>
					<tr>	
						<td>证件号码：</td>
						<td><input name="IDNO" type="text" id="IDNO" value=""
							style="width: 90px" class="inputText"></td>
						<td>订单号：</td>
						<td><input name="orderSn" type="text" id="orderSn" value=""
							style="width: 90px" class="inputText"></td>
							<td>基准时间：</td>
						<td><input name="standardDate" type="text" id="standardDate" value="${standardDate}"
							style="width: 90px" class="inputText" ztype="date"></td>
							<td>统计时间：</td>
						<td><input name="statisticsDate" type="text" id="statisticsDate" value="${statisticsDate}"
							style="width: 90px" class="inputText"></td>
							<td>会员来源：</td>
						<td><z:select name="fromWap" id="fromWap">
										<span value="" selected></span>
										<span value="1">淘宝</span>
										<span value="2">主站</span>
							</z:select></td>
						<td>包含团单数：</td>
						<td>
							<input name="groupStart" type="text" id="groupStart" value="" style="width: 50px" class="inputText">
							<input name="groupEnd" type="text" id="groupEnd" value="" style="width: 50px" class="inputText">
						</td>
				        <td><input type="checkbox" onclick="showchannel()" id="showchannel" name="showchannel"/>会员来源渠道</td>
					</tr>
					<tr>	
						<td>R：</td>
						<td>
							<input name="RStart" type="text" id="RStart" value="" style="width: 50px" class="inputText" verify="R值|NUM">
							<input name="REnd" type="text" id="REnd" value="" style="width: 50px" class="inputText" verify="R值|NUM">
						</td>
						<td>M：</td>
						<td>
							<input name="MStart" type="text" id="MStart" value="" style="width: 50px" class="inputText" verify="金额|NUM">
							<input name="MEnd" type="text" id="MEnd" value="" style="width: 50px" class="inputText" verify="金额|NUM">
						</td>
						<td>F：</td>
						<td>
							<input name="FStart" type="text" id="FStart" value="" style="width: 50px" class="inputText" verify="消费次数|NUM">
							<input name="FEnd" type="text" id="FEnd" value="" style="width: 50px" class="inputText" verify="消费次数|NUM">
						</td>	
						<td>累计实际支付金额：</td>
						<td>
							<input name="amountStart" type="text" id="amountStart" value="" style="width: 50px" class="inputText">
							<input name="amountEnd" type="text" id="amountEnd" value="" style="width: 50px" class="inputText">
						</td>
						<td>累计实际购买次数：</td>
						<td>
							<input name="countStart" type="text" id="countStart" value="" style="width: 50px" class="inputText">
							<input name="countEnd" type="text" id="countEnd" value="" style="width: 50px" class="inputText">
						</td>
						<td><input type="checkbox" onclick="showMemberVip()" id="vipCheckbox" name="vipCheckbox" value="1"/>会员等级</td>
						<td id="memberVip" colspan="2" style="display:none;">
				        	<input type="checkbox" name="checkvip" value="K0"/>普通会员
				          	<input type="checkbox" name="checkvip" value="K1"/>k1会员
				          	<input type="checkbox" name="checkvip" value="K2"/>k2会员
				          	<input type="checkbox" name="checkvip" value="VIP"/>vip会员
				        </td>
				         <td>会员创建时间：</td>
						<td><input name="createDate" type="text" id="createDate" value="${createDate}"
							style="width: 90px" class="inputText" ztype="date"> 至：<input name="endCreateDate" type="text" id="endCreateDate" value="${endCreateDate}"
							style="width: 90px" class="inputText" ztype="date"></td>	 
					</tr>
					<tr>
               			<td><input type="button" name="Submit" value="查询" onClick="doSearch()"></td>
            		</tr>
				</table>
				</z:init>
				</td>
				</tr>
				<tr>
                    <td>
                    <z:tbutton onClick="doCollect()"><img src="../Icons/icon021a4.gif" width="20" height="20" />产品收藏</z:tbutton>
                    <z:tbutton onClick="orderDetail()"><img src="../Icons/icon021a2.gif" width="20" height="20" />订单信息</z:tbutton>
                    <z:tbutton onClick="sendResetPWD()"><img src="../Icons/icon021a2.gif" width="20" height="20" />发送密码重置</z:tbutton>
                    <z:tbutton onClick="searchPoint()"><img src="../Icons/icon021a2.gif" width="20" height="20" />查看积分明细</z:tbutton>
                    </td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
					
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.Member.dg2DataBind" size="20" lazy="true">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id">&nbsp;</td>
								<td width="60"><b>真实姓名</b></td>
								<td width="30"><b>性别</b></td>
								<td width="90"><b>生日</b></td>
								<td width="120"><b>地址</b></td>
								<td width="120"><b>E-mail</b></td>
								<td width="120"><b>证件号码</b></td>
								<td width="90"><b>手机号码</b></td>
								<td width="150"><b>R(最近一次购买时间间隔)</b></td>
								<td width="180"><b>M(累计金额)(含部分撤单保费)</b></td>
								<td width="100"><b>F(购买次数)</b></td>
								<td width="110"><b>K(会员等级基数)</b></td>
								<td width="80"><b>会员价值等级</b></td>
								<td width="100"><b>第一投保人姓名</b></td>
								<td width="90"><b>生日</b></td>
								<td width="30"><b>性别</b></td>
								<td width="100"><b>手机号码</b></td>
								<td width="120"><b>邮箱</b></td>
								<td width="120"><b>累计实际支付金额</b></td>
								<td width="150"><b>累计实际购买次数</b></td>
								<td width="60"><b>会员等级</b></td>
								<td width="60"><b>冻结积分</b></td>
								<td width="120"><b>积分商城消耗积分</b></td>
								<td width="120"><b>购买抵值消耗积分</b></td>
								<td width="60"><b>可用积分</b></td>
								<td width="120"><b>推荐注册获得积分</b></td>
								<td width="120"><b>推荐购买获得积分</b></td>
								<td width="70"><b>包含团单数</b></td>
								<td width="120"><b>最近登录时间</b></td>
								<td width="120"><b>会员创建时间</b></td>
								<td width="100"><b>会员来源</b></td>
							</tr>
							<tr onDblClick="view('${id}')" style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td>&nbsp;</td>
								<td title="${realName}">${realName}</td>
								<td title="${sex}">${sex}</td>
								<td title="${birthday}">${birthday}</td>
								<td title="${address}">${address}</td>
								<td title="${email}">${email}</td>
								<td title="${IDNO}">${IDNO}</td>
								<td title="${mobileNo}">${mobileNo}</td>
								<td title="${RValue}">${RValue}</td>
								<td title="${MValue}">${MValue}</td>
								<td title="${FValue}">${FValue}</td>
								<td title="${KValue}">${KValue}</td>
								<td title="${memberrank}">${memberrank}</td>
								<td title="${applicantName}">${applicantName}</td>
								<td title="${applicantBirthday}">${applicantBirthday}</td>
								<td title="${applicantSex}">${applicantSex}</td>
								<td title="${applicantMobile}">${applicantMobile}</td>
								<td title="${applicantMail}">${applicantMail}</td>
								<td title="${consumeAmount}">${consumeAmount}</td>
								<td title="${buyCount}">${buyCount}</td>
								<td title="${gradeName}">${gradeName}</td>
								<td title="${point}">${point}</td>
								<td title="${exchangeUsed}">${exchangeUsed}</td>
								<td title="${buyUsed}">${buyUsed}</td>
								<td title="${currentValidatePoint}">${currentValidatePoint}</td>
								<td title="${recommendRegPoints}">${recommendRegPoints}</td>
								<td title="${recommendBuyPoints}">${recommendBuyPoints}</td>
								<td title="${groupCount}">${groupCount}</td>
								<td title="${loginDate}">${loginDate}</td>
								<td title="${createdate}">${createdate}</td>
								<td title="${fromName}">${fromName}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>