<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/struts-tags" %>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();
    String serverContext = Config.getServerContext();%>

<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>续保管理</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css" />
    <script src="../Framework/Main.js"></script>
    <script src="../Framework/OrderTree.js"></script>
    <script src="../wwwroot/kxb/js/Common.js"></script>
    <script type="text/javascript" src="../template/common/js/jquery.js"></script>
    <script>
    
    Page.onLoad(function(){
    	var RealName = $V("RealName");
    	
    	 if(RealName=='客服主管'|| RealName =='管理员组'){
    			jQuery("#switTh").show();
    			jQuery("#switTd").show();
    			jQuery("#assignUserTh").show();
    			jQuery("#assignUserTd").show();
    	 }else{
    			jQuery("#switTh").hide();
    			jQuery("#switTd").hide();
    			jQuery("#assignUserTh").hide();
    			jQuery("#assignUserTd").hide();
    	 }
    	
    });
    	
        var KID = "<%=KID%>";
        //订单查询
        function doSearch(){
            var sd = $V("createDate");
            var ed = $V("endCreateDate");
            var cc = $V("call_count");
            var ct = $V("call_connect");
            var ci = $V("continueInsure");
            var cs = $NV("contant");
            var mid = $V("mid");
            var check_flag=jQuery('#one_year_order').is(':checked');

            var all=sd+ed+cc+ct+ci+cs;
            if(all == null || all == ''){
                Dialog.alert("查询条件不能为空,至少要有一个查询条件！");
                return;
            }
            if(ed < sd){
                Dialog.alert("结束日期不能小于开始日期！");
                return;
            }

            DataGrid.setParam("dg1",Constant.PageIndex,0);
            DataGrid.setParam("dg1","createDate",sd);
            DataGrid.setParam("dg1","endCreateDate",ed);
            DataGrid.setParam("dg1","callCount",cc);
            DataGrid.setParam("dg1","callConnect",ct);
            DataGrid.setParam("dg1","continueInsure",ci);
            DataGrid.setParam("dg1","channelsn",cs);
            DataGrid.setParam("dg1","mid",mid);
            
            DataGrid.setParam("dg1","MailConfigRiskType",$V("MailConfigRiskType"));
            DataGrid.setParam("dg1","OrderStatus",$V("OrderStatus"));
            DataGrid.setParam("dg1","ApprovalStatus",$V("ApprovalStatus"));
            DataGrid.setParam("dg1","assignUser",$V("assignUser"));

            if(check_flag==true){
                DataGrid.setParam("dg1","oneYearOrder",1);
            }else{
                DataGrid.setParam("dg1","oneYearOrder",0);
            }
            DataGrid.loadData("dg1");
        }
        //保全记录
        function baoquanEdit(orderSn,id){
            var arr = DataGrid.getSelectedData("dg1");
            if(!arr || arr.getRowCount() == 0){
                Dialog.alert("请先选择要查看的条目！");
                return;
            }
            if(!arr||arr.getRowCount()>=2){
                Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
                return;
            }
            if(orderSn ==null){
                orderSn=arr.get(0,"OrderSn");
            }
            if(id ==null){
                id=arr.get(0,"id");
            }

            var d = new Dialog("Diag1");
            d.Width = 550;
            d.Height = 450;
            d.Title = "保全记录";
            d.URL = "DataService/BaoquanEdit.jsp?orderSn="+orderSn+"&id="+id;
            d.ShowMessageRow = true;
            d.MessageTitle = "保全记录 ";
            d.show();
            d.OKButton.hide();
            d.CancelButton.value="关闭";
        }

        //显示订单详细信息
        function showOrderDetail(orderSn) {
            var queryID = orderSn;
            //alert(KID);
            var KKID = '';
            var dc = new DataCollection();
            dc.add("KID", KID + queryID);
            var method = "cn.com.sinosoft.util.StringUtilC.md5Hex";

            Server.sendRequest(method, dc, function(response) {
                //alert(response.get("KID"));
                KKID = response.get("KID");
                var win = window.open(
                        '../shop/order_config_new!linkOrderDetails.action?orderSn='
                        + queryID + "&KID=" + KKID, '_blank');
                win.focus();
            });

        }

        // 显示订单渠道
        function showchannel(){
            var check_flag=jQuery('#show_channel').is(':checked');
            if(check_flag==true){
                jQuery("#channel_tree").show();
            }else{
                jQuery("#channel_tree").hide();
            }
        }

        // 呼出状态增加
        function addCallStatus(){
            var arr = DataGrid.getSelectedData("dg1");
            
         
	    	var dr = DataGrid.getSelectedValue("dg1");

            if (!arr || arr.getRowCount() == 0) {
                Dialog.alert("请先选择要编辑的条目！");
                return;
            }
	console.log(dr.join());
            var orderSn = arr.get(0, "orderSn");
            var memberId = arr.get(0, "memberId");
            var d = new Dialog("Diag2");
            d.Width = 450;
            d.Height = 250;
            d.Title = "呼出状态增加";
            d.URL = "DataService/AddCallStatus.jsp?orderSn="+dr.join()+"&memberId="+memberId;
            d.show();
            d.OKButton.hide();
            d.CancelButton.value="关闭";
        }

        // 导出excel
        function exportExl() {
            DataGrid.toExcel("dg1",1);
        }

        // 显示呼出记录
        function showCallRecord(orderSn,callConnect) {
            if (callConnect == '-') {
                return;
            }
            var d = new Dialog("Diag3");
            d.Width = 600;
            d.Height = 400;
            d.Title = "呼出记录";
            d.URL = "DataService/ShowCallRecord.jsp?orderSn="+orderSn;
            d.show();
            d.OKButton.hide();
            d.CancelButton.value="关闭";
        }
        
        
        
        
        
	    function Mark(){
	        
	    	
	    	var arr = DataGrid.getSelectedValue("dg1");
	    	var DT_Template = DataGrid.getSelectedData("dg1");
	    	if(!DT_Template || DT_Template.getRowCount() == 0){
	    		Dialog.alert("请选择续保订单！");
	    		return;
	    	}
	    	if(DT_Template.get(0,"userName")){
	    		Dialog.alert("此订单已分配给"+DT_Template.get(0,"userName"));
	    		return;
	    	}
	    	 
		        var diag = new Dialog("Diag133");
		        diag.Width = 800;
		        diag.Height = 580;
		        diag.Title = "分配";
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
	    		Dialog.alert("请选择分配人订单！");
	    		return;
	    	}
	    	var arr = DataGrid.getSelectedValue("dg1");
	    	
			var dc = $DW.Form.getData("form2");

			 dc.add("dg1", 	 $DW.DataGrid.getSelectedValue("dg1"));
			 dc.add("orderSns", arr.join());		 
			 
			Server.sendRequest("com.sinosoft.cms.dataservice.ContinueInsureManage.distribution",dc,
						function(response) {	Dialog.alert(	response.Message,function() {
				 
									if (response.Status == 1) {
											$D.close();
											DataGrid.loadData('dg1');
									}
								})
						});
				}

	    function delSave(){
	    	var DT_Template = DataGrid.getSelectedData("dg1");
	    	if(!DT_Template || DT_Template.getRowCount() == 0){
	    		Dialog.alert("请选择删除分配人订单！");
	    		return;
	    	}
	    	var arr = DataGrid.getSelectedValue("dg1");
	    	
			 Dialog.confirm("确定删除选择的订单中的分配人吗？",function(){
			var dc = new DataCollection();
 
			 dc.add("orderSns", arr.join());		 

				Server.sendRequest("com.sinosoft.cms.dataservice.ContinueInsureManage.delDistribution",dc,
							function(response) {	Dialog.alert(	response.Message,function() {
					 
										if (response.Status == 1) {
												$D.close();
												DataGrid.loadData('dg1');
										}
									})
							});
				});

			}

    </script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td id="channel_tree" style="display: none">
            <table width="50" border="0" cellspacing="0" cellpadding="6"
                   class="blockTable">
                <tr>
                    <td style="padding: 6px;"  width="50" class="blockTd">
                    <z:tree id="tree1" style="height:440px;width:120px;"
                            method="com.sinosoft.platform.Channel.treeDataBind"
                            level="3" lazy="true" resizeable="true">
                        <img src="../Framework/Images/icon_drag.gif" width="16" height="16">
                            <p cid='${ID}' level="${TreeLevel}">
                                <input type="checkbox" name="contant" id='contant_${ID}' value='${ChannelCode}' onClick="onCheck(this);">
                                <label for="contant_${ID}"><span id="span_${ID}">${Name}</span> </label>
                            </p>
                    </z:tree>
                    </td>
                </tr>
            </table>
        </td>
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
                <tr>
                    <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 续保管理</td>
                </tr>
                <tr>
                    <td>
                        <z:init method="com.sinosoft.cms.dataservice.ContinueInsureManage.init">
                            <table  cellspacing="0" cellpadding="3">
                                <tr>
                                    <th>保单到期日 从：</th>
                                    <td> <input name="createDate" type="text" id="createDate" value="${createDate}" style="width:100px" ztype="date"></td>
                                    <th>至：</th>
                                    <td><input name="endCreateDate" type="text" id="endCreateDate" value="${endCreateDate}"style="width:100px" ztype="date"></td>
                                    <th>呼出次数</th>
                                    <td><z:select style="width:100px;" name="callCount" id="call_count">
                                        <option value=""></option>
                                        <option value="1">1次</option>
                                        <option value="2">2次</option>
                                        <option value="3">3次</option>
                                        <option value="4">4次</option>
                                        <option value="5">5次以上</option>
                                    </z:select></td>

                                    <th>是否接听</th>
                                    <td><z:select style="width:100px;"><select name="callConnect" id="call_connect" >
                                        <option value=""></option>
                                        <option value="1">是</option>
                                        <option value="2">否</option>
                                    </select></z:select></td>
                                    <th>是否续保</th>
                                    <td><z:select style="width:100px;"><select name="continueInsure" id="continueInsure" >
                                        <option value=""></option>
                                        <option value="1">是</option>
                                        <option value="2">否</option>
                                    </select></z:select></td>
                                    <td>会员ID：</td>
                                    <td> <input type="text" name="mid" id="mid" style="width:100px" /></td>
                                    <td><input type="checkbox" onclick="showchannel()" id="show_channel"/>订单渠道</td>
                                    <td><input type="checkbox" id="one_year_order" checked/>一年续保订单</td>
                                </tr>
                                 
                                 
                                 <tr>
                                    
                                    <th>品类：</th>
                              
                                    <td><z:select style="width:100px;" name="MailConfigRiskType" id="MailConfigRiskType">${MailConfigRiskType}</z:select></td>
     			  
                                     <th>订单状态：</th>
                                  
                                    <td><z:select style="width:100px;" name="OrderStatus" id="OrderStatus">${OrderStatus}</z:select></td>
                               
                                      	
                                        
	                                    <th id="switTh">分配状态：</th>
	                                    <td id="switTd"><z:select style="width:100px;" name="ApprovalStatus" id="ApprovalStatus">${ApprovalStatus}</z:select>
	                                    <input type="hidden" id="RealName" name="RealName" value="${RealName}"/></td>
                                        <th id="assignUserTh">被分配人：</th>
                                        <td id="assignUserTd"> <input type="text" name="assignUser" id="assignUser" style="width:100px" /></td>
                                  </tr>
                   					
                                <tr>
                                    <td  colspan="11" nowrap>
                                        <z:tButtonPurview>
                                            ${_DataService_ContinueInsureManage_Button}
                                        </z:tButtonPurview>
                                    </td>
                                </tr>
                            </table>
                        </z:init>
                    </td>
                </tr>
                <tr>
                    <td style="padding: 0px 5px;">
                        <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
                            <tr>
                                <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
                                    <%--<z:datagrid id="dg1" method="com.wangjin.cms.orders.QueryOrders.orderInquery" size="20" scroll="true" lazy="true">--%>
                                    <z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.ContinueInsureManage.orderInquery" size="20" scroll="true" lazy="true">
                                        <table width="100%" cellpadding="2" cellspacing="0"
                                               class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="480px" >
                                            <tr ztype="head" class="dataTableHead">
                                                <td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
                                                <td width="15" ztype="selector" field="orderSn" style="text-align:center;">&nbsp;</td>
                                                <td width="120" style="text-align:center;"><b>订单编号</b></td>
                                                <td width="45" style="text-align:center;"><b>品类</b></td>
                                                <td width="55" style="text-align:center;"><b>呼出次数</b></td>
                                                <td width="55" style="text-align:center;"><b>是否接听</b></td>
                                                <td width="55" style="text-align:center;"><b>是否续保</b></td>
                                                <td width="180" style="text-align:center;"><b>续保新订单号</b></td>
                                                <td width="55" style="text-align:center;"><b>呼出记录</b></td>
                                                <td width="120" style="text-align:center;"><b>渠道订单号</b></td>
                                                <td width="145" style="text-align:center;"><b>保单号</b></td>
                                                <td width="55" style="text-align:center;"><b>保全记录</b></td>
                                                <td width="50" style="text-align:center;"><b>投保人</b></td>
                                                <td width="70" style="text-align:center;"><b>被保险人数</b></td>
                                                <td width="120" style="text-align:center;"><b>起保日期</b></td>
                                                <td width="120" style="text-align:center;"><b>修改时间</b></td>
                                                <td width="150" style="text-align:center;"><b>产品名称</b></td>
                                                <td width="55" style="text-align:center;"><b>产品计划</b></td>
                                                <td width="50" style="text-align:center;"><b>保费</b></td>
                                                <td width="50" style="text-align:center;"><b>已付费</b></td>
                                                <td width="110" style="text-align:center;"><b>会员ID</b></td>
                                                <td width="55" style="text-align:center;"><b>分配状态</b></td>
                                                <td width="55" style="text-align:center;"><b>订单状态</b></td>
                                                <td width="55" style="text-align:center;"><b>被分配人</b></td>
                                                <td width="145" style="text-align:center;"><b>优惠券号</b></td>
                                                <td width="100" style="text-align:center;"><b>优惠券优惠金额</b></td>
                                                <td width="145" style="text-align:center;"><b>活动号</b></td>
                                                <td width="80" style="text-align:center;"><b>活动优惠金额</b></td>
                                                <td width="90" style="text-align:center;"><b>积分抵值金额</b></td>
                                                <td width="80" style="text-align:center;"><b>是否报案</b></td>
                                                <td width="80" style="text-align:center;"><b>渠道</b></td>
                                                <td width="150" style="text-align:center;"><b>自定义活动描述</b></td>


                                            </tr>
                                            <tr  style="background-color:#F9FBFC">
                                                <td width="3%">&nbsp;</td>
                                                <td style="text-align:center;">&nbsp;</td>
                                                <td style="text-align:center;" title="${orderSn}"><a  style="cursor: hand;" onClick="showOrderDetail('${OrderSn}')">${orderSn}</a></td>
                                                <td style="text-align:center;" title="${productGenera}">${productGenera}</td>
                                                <td style="text-align:center;" title="${callCount}">${callCount}</td>
                                                <td style="text-align:center;" title="${callConnect}"><a  style="cursor: hand;" onClick="showCallRecord('${orderSn}','${callConnect}')">${callConnect}</a></td>
                                                <td style="text-align:center;" title="${continueInsure}">${continueInsure}</td>
                                                <td style="text-align:center;" title="${newOrderSn}">${newOrderSn}</td>
                                                <td style="text-align:center;" title="${callRecord}">${callRecord}</td>
                                                <td title="${tbTradeSeriNo}">${tbTradeSeriNo}</td>
                                                <td title="${policyNo}">${policyNo}</td>
                                                <td style="text-align:center;" title="${remark}">${remark}</td>
                                                <td style="text-align:center;" title="${ApplicantName}">${ApplicantName}</td>
                                                <td style="text-align:center;" title="${recognizeeNu}">${recognizeeNu}</td>
                                                <td style="text-align:center;" title="${svalidate}">${svalidate}</td>
                                                <td style="text-align:center;" title="${ModifyDate}">${ModifyDate}</td>
                                                <td title="${ProductName}">${ProductName}</td>
                                                <td title="${planName}">${planName}</td>
                                                <td style="text-align:right;" title="${totalAmount}">${totalAmount}</td>
                                                <td style="text-align:right;" title="${PayPrice}">${PayPrice}</td>
                                                <td style="text-align:right;" title="${mid}">${mid}</td>
                                                 <td style="text-align:right;" title="${distributionState}">${distributionState}</td>
                                                 
                                                
                                                <td style="text-align:center;" title="${orderstatusname}">${orderstatusname}</td>
                                                <td style="text-align:right;" title="${userName}">${userName}</td>
                                                
                                                <td style="text-align:right;" title="${couponSn}">${couponSn}</td>
                                                <td style="text-align:right;" title="${orderCoupon}">${orderCoupon}</td>
                                                <td style="text-align:right;" title="${ActivitySn}">${ActivitySn}</td>
                                                <td style="text-align:right;" title="${orderActivity}">${orderActivity}</td>
                                                <td style="text-align:right;" title="${offsetPoint}">${offsetPoint}</td>
                                                <td style="text-align:center;" title="${paymentReport}">${paymentReport}</td>
                                                <td style="text-align:center;" title="${channelName}">${channelName}</td>
                                                <td style="text-align:center;" title="${diyActivityDescription}">${diyActivityDescription}</td>
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
        </td>
    </tr>
</table>
</body>
</html>
