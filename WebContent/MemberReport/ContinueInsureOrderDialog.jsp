<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>订单信息</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css" />
    <script src="../Framework/Main.js"></script>

    <script>
		    function Mark(){
			        
		    	
		    	var arr = DataGrid.getSelectedValue("dg1");


		    	var DT_Template = DataGrid.getSelectedData("dg1");
		    	if(!DT_Template || DT_Template.getRowCount() == 0){
		    		Dialog.alert("请选择续保订单！");
		    		return;
		    	}
		    	if(DT_Template.get(0,"userName")){
		    		Dialog.alert("请勿重复标记！");
		    		return;
		    	}
		    	 
			        var diag = new Dialog("Diag133");
			        diag.Width = 800;
			        diag.Height = 580;
			        diag.Title = "标记人";
			        diag.URL = "MemberReport/mark.jsp?orderSns="+arr.join();
			        diag.onLoad = function(){
			    		$DW.$("ordersn").focus();
			    	};
			    	diag.OKEvent = addSave;
			    	diag.show();
			
			}
		    
		    
		    function addSave(){
		    	
		    	
		    	var DT_Template = DataGrid.getSelectedData("dg1");
		    	if(!DT_Template || DT_Template.getRowCount() == 0){
		    		Dialog.alert("请选择标记人！");
		    		return;
		    	}
		    	var arr = DataGrid.getSelectedValue("dg1");
		    	
				var dc = $DW.Form.getData("form2");

				 dc.add("dg1", 	 $DW.DataGrid.getSelectedValue("dg1"));
				 dc.add("orderSns", arr.join());		 
				 
				Server.sendRequest("com.sinosoft.cms.memberreport.ContinueInsureCount.mark",dc,
							function(response) {	Dialog.alert(	response.Message,function() {
					 
										if (response.Status == 1) {
												$D.close();
												DataGrid.loadData('dg1');
										}
									})
							});
					}
		</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
       style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td>
            <z:init>
            <table width="100%" border="0" cellspacing="0" cellpadding="6"
                   class="blockTable">
                <tr>
                    <td>
                        <z:tbutton onClick="Mark()"><img src="../Icons/icon021a3.gif" />标记人</z:tbutton>
                    </td>
                </tr>
                <tr>
                    <td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">

                        <z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.ContinueInsureCount.getOrderSDialog"
                                    size="20" lazy="false" autoFill="true">
                            <table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
                                <tr ztype="head" class="dataTableHead">
                                    <td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
               					     <td width="15" ztype="selector" field="ordersn"  style="text-align:center;">&nbsp;</td>
                                    <td width="120" style="text-align:center;"><b>订单编号</b></td>
                                    <td width="50" style="text-align:center;"><b>标记人</b></td>
                                    <td width="50" style="text-align:center;"><b>投保人</b></td>
                                    <td width="70" style="text-align:center;"><b>被保险人数</b></td>
                                    <td width="120" style="text-align:center;"><b>起保日期</b></td>
                                    <td width="120" style="text-align:center;"><b>修改时间</b></td>
                                    <td width="150" style="text-align:center;"><b>产品名称</b></td>
                                    <td width="55" style="text-align:center;"><b>产品计划</b></td>
                                    <td width="50" style="text-align:center;"><b>保费</b></td>
                                    <td width="50" style="text-align:center;"><b>已付费</b></td>
                                    <td width="110" style="text-align:center;"><b>会员ID</b></td>
                                    <td width="55" style="text-align:center;"><b>订单状态</b></td>
                                    <td width="145" style="text-align:center;"><b>优惠券号</b></td>
                                    <td width="100" style="text-align:center;"><b>优惠券优惠金额</b></td>
                                    <td width="145" style="text-align:center;"><b>活动号</b></td>
                                    <td width="80" style="text-align:center;"><b>活动优惠金额</b></td>
                                    <td width="90" style="text-align:center;"><b>积分抵值金额</b></td>
                                    <td width="80" style="text-align:center;"><b>订单来源渠道</b></td>
                                    <td width="150" style="text-align:center;"><b>会员来源渠道</b></td>
                                    <td width="150" style="text-align:center;"><b>险种</b></td>
                                </tr>
                                <tr  style="background-color:#F9FBFC">
                                    <td width="3%">&nbsp;</td>
                                     <td style="text-align:center;">&nbsp;</td>
                                    <td style="text-align:center;" title="${ordersn}">${ordersn}</td>
                                    <td style="text-align:center;" title="${userName}">${userName}</td>
                                    <td style="text-align:center;" title="${applicantName}">${applicantName}</td>
                                    <td style="text-align:center;" title="${insuredNum}">${insuredNum}</td>
                                    <td style="text-align:center;" title="${startdate}">${startdate}</td>
                                    <td style="text-align:center;" title="${modifyDate}">${modifyDate}</td>
                                    <td title="${productname}">${productname}</td>
                                    <td title="${planName}">${planName}</td>
                                    <td style="text-align:right;" title="${premSum}">${premSum}</td>
                                    <td style="text-align:right;" title="${payPrice}">${payPrice}</td>
                                    <td style="text-align:right;" title="${MID}">${MID}</td>
                                    <td style="text-align:center;" title="${CodeName}">${CodeName}</td>
                                    <td style="text-align:right;" title="${couponsn}">${couponsn}</td>
                                    <td style="text-align:right;" title="${couponAmount}">${couponAmount}</td>
                                    <td style="text-align:right;" title="${activitySn}">${activitySn}</td>
                                    <td style="text-align:right;" title="${activitySum}">${activitySum}</td>
                                    <td style="text-align:right;" title="${integralSum}">${integralSum}</td>
                                    <td style="text-align:center;" title="${orderchannel}">${orderchannel}</td>
                                    <td style="text-align:center;" title="${memberchannel}">${memberchannel}</td>
                                    <td style="text-align:center;" title="${ProductGenera}">${ProductGenera}</td>
                                </tr>
                                <tr ztype="pagebar">
                                    <td height="25" colspan="11">${PageBar}</td>
                                </tr>
                            </table>
                        </z:datagrid></td>

                </tr>
            </table>
            </z:init>
        </td>
    </tr>
</table>
</body>
</html>