<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>推荐活动统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script>

function search(){
	var sd = $V("createDate");
    var ed = $V("endCreateDate");
    if (ed < sd) {
        Dialog.alert("结束日期不能小于开始日期！");
        return;
    }
    DataGrid.setParam("dg1", Constant.PageIndex, 0);
    DataGrid.setParam("dg1", "referrerMobileNo", $V("referrerMobileNo"));
    DataGrid.setParam("dg1", "referrerEmail", $V("referrerEmail"));
    DataGrid.setParam("dg1", "recoMobileNo", $V("recoMobileNo"));
    DataGrid.setParam("dg1", "recoEmail", $V("recoEmail"));
    DataGrid.setParam("dg1", "createDate", $V("createDate"));
    DataGrid.setParam("dg1", "endCreateDate", $V("endCreateDate"));
    DataGrid.setParam("dg1", "referrerType", $V("referrerType"));
    DataGrid.setParam("dg1", "batch", $V("batch"));
    DataGrid.setParam("dg1", "couponSn", $V("couponSn"));
    DataGrid.setParam("dg1", "couponStatus", $V("couponStatus"));
    DataGrid.setParam("dg1", "paySn", $V("paySn"));
    DataGrid.setParam("dg1", "channelsn", $NV("contant"));
    DataGrid.loadData("dg1");
}

function showchannel() {
    var check_flag = $('showchannel').checked;
    if (check_flag == true) {
    	$E.show("channeltree")
    } else {
    	$E.hide("channeltree")
    }
}

Page.onLoad(function(){
	chooseReferrerType();
});

function chooseReferrerType() {
	var referrerType=$V("referrerType");
	
	if(referrerType==''){
		$S("batch", "");
		$S("couponSn", "");
		$S("couponStatus", "");
		$E.disable("batch");
		$E.disable("couponSn");
		$E.disable("couponStatus");
		
	}else{
		$E.enable("batch");
		$E.enable("couponSn");
		$E.enable("couponStatus");
	}
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
    	<td id="channeltree" style="display: none">
            <table width="50" border="0" cellspacing="0" cellpadding="6"
                   class="blockTable">
                <tr>
                    <td style="padding: 6px;" width="50" class="blockTd"><z:tree id="tree1"
                                                                                 style="height:440px;width:120px;"
                                                                                 method="com.sinosoft.platform.Channel.treeDataBind"
                                                                                 level="3" lazy="true"
                                                                                 resizeable="true">
                        <img src="../Framework/Images/icon_drag.gif" width="16" height="16">
                        <p cid='${ID}' level="${TreeLevel}"><input type="checkbox"
                                                                   name="contant" id='contant_${ID}'
                                                                   value='${ChannelCode}'
                                                                   onClick="onCheck(this);"><label
                                for="contant_${ID}"><span id="span_${ID}">${Name}</span></label></p>
                    </z:tree></td>
                </tr>
            </table>
        </td>
      	<td>
      		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
    			<tr>
    				<td>
                        <z:init method="com.wangjin.activity.ActivityStatistics.initReco">
                            <table cellspacing="0" cellpadding="3">
                                <tr>
                                	<td>统计时间 从：</td>
                                    <td><input name="createDate" type="text" id="createDate"
                                               value="${createDate}" style="width: 100px" ztype="date"></td>
                                    <td>至：</td>
                                    <td><input name="endCreateDate" type="text"
                                               id="endCreateDate" value="${endCreateDate}"
                                               style="width: 100px" ztype="date"></td>
                                    <td>商家订单号：</td>
                                    <td><input name="paySn" type="text" id="paySn"
                                               value="" style="width: 150px"></td>
                                    <td>订单渠道：</td>
                                    <td><input type="checkbox" onclick="showchannel()"
                                               id="showchannel"/>订单渠道</td>
                                </tr>
                                <tr>
                                	<td>推荐人手机号：</td>
                                    <td><input name="referrerMobileNo" type="text"
                                               id="referrerMobileNo" value="" style="width: 100px"></td>
                                    <td>推荐人邮箱：</td>
                                    <td><input name="referrerEmail" type="text" id="referrerEmail"
                                               value="" style="width: 150px"></td>
                                	<td>被推荐人手机号：</td>
                                    <td><input name="recoMobileNo" type="text"
                                               id="recoMobileNo" value="" style="width: 150px"></td>
                                    <td>被推荐人邮箱：</td>
                                    <td><input name="recoEmail" type="text" id=""recoEmail""
                                               value="" style="width: 150px"></td>
                                </tr>
                                <tr>
                                	<td>优惠券归属：</td>
                                    <td><z:select style="width:100px;" name="referrerType"
                                                  id="referrerType" onChange="chooseReferrerType()">${referrerType}</z:select></td>
                                    <td>优惠券批次：</td>
                                    <td><input name="batch" type="text" 
                                               id="batch" value="" style="width: 100px"/></td>
                                    <td>优惠券码：</td>
                                    <td><input name="couponSn" type="text" 
                                               id="couponSn" value="" style="width: 150px"/></td>
                                    <td>优惠券状态：</td>
                                    <td><z:select style="width:100px;" id="couponStatus" name="couponStatus" >
	                                    	<select>
	                                            <option value=""></option>
	                                            <option value="2">已发放</option>
	                                            <option value="1">已使用</option>
	                                            <option value="5">已过期</option>
	                                        </select>
                                    	</z:select>
                                    </td>
                                    

                                </tr>
                                <tr>
                                    <td colspan="12" nowrap>
                                        <input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
                                    </td>
                                </tr>
                            </table>
                        </z:init>
                    </td>
    			</tr>
				<tr>
             		<td style="padding:0px 5px;">
						<z:datagrid id="dg1" method="com.wangjin.activity.ActivityStatistics.recoDataBind"  size="20" lazy="true">
			              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
			                <tr ztype="head" class="dataTableHead">
			                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
			                  <td width="14%"><strong>推荐人</strong></td>
			                  <td width="14%"><strong>被推荐人</strong></td>
			                  <td width="5%"><strong>优惠券归属</strong></td>
			                  <td width="6%"><strong>优惠券批次</strong></td>
			                  <td width="11%"><strong>优惠券码</strong></td>
			                  <td width="5%"><strong>优惠券状态</strong></td>
			                  <td width="10%"><strong>商家订单号</strong></td>
			                  <td width="5%"><strong>订单渠道</strong></td>
			                </tr>
			                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC"  >
			                  <td>&nbsp;</td>
			                  <td>${referrerInfo}</td>
			                  <td>${recommendedInfo}</td>
			                  <td>${referrerTypeName}</td>
			                  <td>${batch}</td>
			                  <td>${couponSn}</td>
			                  <td>${couponStatusName}</td>
			                  <td>${paySn}</td>
			                  <td>${channelName}</td>
			                </tr>
			                <tr ztype="pagebar">
								<td colspan="6" align="left">${PageBar}</td>
							</tr>
			              </table>
			            </z:datagrid>
            		</td>
         		</tr>
      		</table>
      	</td>
    </tr>
  </table>
</body>
</html>
