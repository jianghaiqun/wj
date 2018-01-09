<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	$("dg2").afterEdit = function(tr,dr){
		if(Verify.hasError()){
			return;
		}
		 
		dr.set("travelName" ,$V("travelName"));
		dr.set("travelEnName" ,$V("travelEnName"));
		dr.set("identityTypeName" ,$("identityTypeName").getText());
		dr.set("identityType" ,$V("identityTypeName"));
		dr.set("identityId" ,$V("identityId"));
		if($("identityTypeName").getText()!='身份证'){
				dr.set("identityStartDate" ,$V("identityStartDate"));
				dr.set("identityEndDate" ,$V("identityEndDate"));
		}
		dr.set("sexName" ,$("sexName").getText());
		dr.set("sex" ,$V("sexName"));
		dr.set("birthday" ,$V("birthday"));
		dr.set("mobile" ,$V("mobile"));
		dr.set("email" ,$V("email"));
			 
		return true;
	};
});
</script>
</head>
<body>
	<form id="form2">
         <z:init method="com.sinosoft.cms.travel.OrderManage.initEditInfo">
			<table width="100%" cellpadding="2" cellspacing="0"  class="blockTable" >
			      <tr> 
			            <td>
			                  <fieldset>
				                    <table width="100%" cellpadding="2" cellspacing="0" >
										<tr> <td valign="middle" class="blockTd"><img src="../Icons/icon021a1.gif" /> 订单基本信息</td></tr>
										<tr>
										    <input type="hidden" id="id" name="id" value="${id}" style="width:150px" />
											<td height="25" align="right" class="tdgrey1">订单号码：</td>
											<td height="25">
								            <input name="orderSn" type="text" value="${orderSn}" style="width:150px"  id="orderSn"  disabled="true"/>
								            </td>
								            <td height="25" align="right" class="tdgrey1">产品：</td>
											<td height="25">
								            <input name="productName" type="text" value="${productName}" style="width:160px"  id="productName"  disabled="true"/>
								            </td>
								            <td height="25" align="right" class="tdgrey1">承保公司：</td>
											<td height="25">
								            <input name="totalPrice" type="text" value="${totalPrice}" style="width:150px"  id="totalPrice"  disabled="true"/>
								            </td>
								         </tr>
								         <tr>
								            <td height="25" align="right" class="tdgrey1">出行日期：</td>
											<td height="25">
								            <input name="travelDate" type="text" value="${travelDate}" style="width:150px"  id="travelDate"  disabled="true"/>
								            </td>
								            <td height="25" align="right" class="tdgrey1">出行人数：</td>
											<td height="25">
								            <input name="travelNum" type="text" value="${travelNum}" style="width:160px"  id="travelNum"  disabled="true"/>
								            </td>
								         </tr>  
								         <tr>
									         <td height="25" align="right" class="tdgrey1">联系人姓名：</td>
											<td height="25">
											<input name="contactName" type="text" value="${contactName}" style="width:150px" class="inputText" id="contactName"  disabled="true" / >
											</td>
									         <td height="25" align="right" class="tdgrey1">联系人电话：</td>
												<td height="25">
								                <input name="contactPhone" type="text" value="${contactPhone}" style="width:160px" class="inputText" id="contactPhone"  disabled="true"/>
								                </td>
									         <td height="25" align="right" class="tdgrey1">联系人邮箱：</td>
												<td height="25">
								                <input name="contactEmail" type="text" value="${contactEmail}" style="width:150px" class="inputText" id="contactEmail"  disabled="true"/>
								                </td>
								         </tr>	
								         <tr>
								            <td height="25" align="right" class="tdgrey1">备注：</td>
											<td height="25" colspan="5">
								            	<textarea rows="2" cols="100" readonly="readonly">${comment}</textarea>
								            </td>
								         </tr> 
						              </table>
						       </fieldset>
					     
					   </td>
		          </tr>
		    </table>
			             
		        <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 出行人基本信息（双击可进行修改）</td>
		        </tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.sinosoft.cms.travel.OrderManage.initEditInfo2"  >
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="480px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id">&nbsp;</td>
								<td  style="text-align:center;" width="50"><b>姓名</b></td>
								<td  style="text-align:center;" width="50"><b>英文名</b></td>
								<td  style="text-align:center;"><b>证件类型名</b></td>
								<td  style="display: none;" ><b>证件类型编码</b></td>
								<td  style="text-align:center;"><b>证件号</b></td>
								<td  style="text-align:center;"><b>证件有效起期</b></td>
								<td  style="text-align:center;"><b>证件有效止期</b></td>
								<td   style="display: none;" ><b>性别编码</b></td>
								<td  style="text-align:center;" width="80" ><b>性别</b></td>
								<td  style="text-align:center;" width="100"><b>出日</b></td>
								<td  style="text-align:center;"><b>手机号</b></td>
								<td  style="text-align:center;"><b>邮箱</b></td>
							</tr>
							<tr style="background-color:#F9FBFC"  >
								<td width="3%">&nbsp;</td>
								<td>&nbsp;</td>
								<td  title="${travelName}">${travelName}</td>
								<td  title="${travelEnName}">${travelEnName}</td>
								<td  title="${identityTypeName}">${identityTypeName}</td>
								<td  title="${identityType}" style="display: none;">${identityType}</td>
								<td  title="${identityId}">${identityId}</td>
								<td  title="${identityStartDate}">${identityStartDate}</td>
								<td  title="${identityEndDate}">${identityEndDate}</td>
								<td  title="${sex}" style="display: none;">${sex}</td>
								<td  title="${sexName}">${sexName}</td>
								<td  title="${birthday}">${birthday}</td>
								<td  title="${mobile}">${mobile}</td>
								<td  title="${email}">${email}</td>
							</tr>
							
							<tr ztype="edit" bgcolor="#E1F3FF">
								<td width="3%">&nbsp;</td>
								<td>&nbsp;</td>
								<td  ><input name="travelName" id="travelName" value="${travelName}"  size="5"  ></td>
								<td  ><input name="travelEnName" id="travelEnName" value="${travelEnName}"   size="5" ></td>
								<td  >
									<z:select  name="identityTypeName" id="identityTypeName" value="${identityType}"  style="width:100px;" >${@identityList}</z:select>
								</td>
								<td name="identityType" id="identityType"   style="display: none;">${identityType}</td>
								<td  ><input name="identityId" id="identityId" value="${identityId}" size="15"  ></td>
								<td  ><input name="identityStartDate" id="identityStartDate" value="${identityStartDate}"  ztype='date' readonly="readonly" size="12" ></td>
								<td  ><input name="identityEndDate" id="identityEndDate"  value="${identityEndDate}"  ztype='date' readonly="readonly" size="12" ></td>
								<td   name="sex" id="sex"  style="display: none;">${sex}</td>
								<td  >
									<z:select  name="sexName" id="sexName" value="${sex}"  style="width:50px;"  >${@sexList}</z:select>
								</td>
							
								<td  ><input name="birthday" id="birthday" value="${birthday}" ztype='date' readonly="readonly" size="12" ></td>
								<td  ><input name="mobile" id="mobile" value="${mobile}" width="100%" ></td>
								<td  ><input name="email" id="email" value="${email}"  width="100%"></td>
							</tr>
							
						</table>
					</z:datagrid></td>
					</tr>
				</table>
		 </z:init>	
	</form>
</body>
</html>