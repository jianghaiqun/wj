<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-保单管理</title>
<!-- 会员中心公共CSS -->
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"/>
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>

<body class="up-bg-qh">
<form id="orderInfoForm" name="orderInfoForm">
<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
<div class="wrapper">
<div class="daohang"><span class="daohang_home"></span> <a
	href='<%=Config.getFrontServerContextPath() %>' target='_self'><span class="orange">您现在的位置：首页</span></a><span
	class="separator">></span><a href='member_center!index.action' target='_self'>会员中心</a>><span
	class="separator"></span><span class="separator1">保单管理</span></div>
<div class="member_con">
<div class="member_left">
<input type="hidden" id="out_member" value="member"/>
<jsp:include page="member_center_left.jsp"></jsp:include>
</div>
<div class="member_right bor_sild">
<div class="member_boxs"><!-- 我的比价开始-->
<div class="member-htit"><span class="member-titsc">保单管理</span></div>
<div class="clear h20"></div>
<div class="member_orderlist" id="member_commantable">
<table cellspacing="0" cellpadding="0" border="0" align="center" class="member_nearorderTable member_ordersT" id="order_table">
	<tbody>
		<tr class="m_order_th">
			<th width="100">被保人姓名</th>
			<th width="240">产品名称</th>
			<th width="110">起保日期</th>
			<th width="110">到期日期</th>
			<th width="90">保单状态</th>
			<th width="70">保单操作</th>
		</tr>

		<s:iterator id='rd' value="#request.rdList">
			
			<input type="hidden" value="<s:property value="#rd.orderSn" />" name="orderSn" id="orderSn"/>
			<input type="hidden" value="<s:property value="#rd.riskCode" />" name="riskCode" id="riskCode"/>
			
			
			<tr>
				<td><span class="member_comprenameCase">
				<p><s:property value="#rd.recognizeeName" /></p>

				</span></td>
				<td><span class="member_comparecompanyCase">
				<p><s:property value="#rd.riskName" /></p>
				</span></td>
				<td><span class="member_ordertimeCase"> <s:property
					value="#rd.svaliDate" /> </span></td>
				<td><span class="member_ordertimeCase">
                        <s:if test="#rd.evaliDate.indexOf('9999年12月31日') != -1 ">
                            终身
                        </s:if>
                        <s:else>
                            <s:property value="#rd.evaliDate" />
                        </s:else>
                    </span></td>
				<td><span class="member_ordertimeCase">
					<s:if test="#rd.appStatus==1">
						已承保
					</s:if> <s:if test="#rd.appStatus==2">
						已撤单
					</s:if> <s:if test="#rd.appStatus==3">
						未起保
					</s:if> <s:if test="#rd.appStatus==4">
						已到期
					</s:if> </span></td>
				<td><div class="m_ding_action">
				<s:if test='#rd.electronicPath !=null && #rd.electronicPath != ""'>
					<span class="bd_down" onclick = "openFile('<s:property value="#rd.electronicPath"/>')">保单下载</span>
				</s:if>
				<s:else>
					<s:if test="#rd.riskCode == '2034' or #rd.riskCode == '2049'">
						<span class="bd_down" id = '<s:property value="#rd.id"/>' onclick = "getElectronicPath('<s:property value="#rd.recognizeeSn" />','<s:property value="#rd.id"/>')">保单下载</span>
					</s:if>
				</s:else>
				<s:if test="#rd.appStatus==3 && #rd.riskCode != '2248'">
					<span class="m_ck_qx" onclick="cancelCont('<s:property value="#rd.id"/>')">保单取消</span>
				</s:if>
				</div></td>
			</tr>
		</s:iterator>
		
	</tbody>
</table>
		
		<input type="hidden" value="<s:property value="page" />" name="page" id="page"/>
		<input type="hidden" value="<s:property value="lastpage" />" name="lastpage" id="lastpage"/>
</div>
<div class="clear h20"></div>
<s:if test="#request.lastpage > 1">
<div class="plpage">
	<!--    翻页    -->
	<div  class="page_area">
		<div id="pagination">
			<ul>
				<s:if test="#request.page==1">
					<li class="page_prev"><a href="#"><span class="default">上一页</span></a></li>
				</s:if>
				<s:else>
					<li class="page_prev"><a href="javascript:jumppage('<s:property value="#request.page-1"/>')"><span class="">上一页</span></a></li>
				</s:else>
				<s:iterator id="pageFootMap" status="st" value="#request.pageFootList">
					<li class="<s:property value="#pageFootMap.class" />">
						<s:if test="#pageFootMap.index==\"...\"">
							<span><s:property value="#pageFootMap.index"/></span>
						</s:if>
						<s:else>
							<a href="javascript:jumppage('<s:property value="#pageFootMap.index"/>')">
			        			<span><s:property value="#pageFootMap.index"/></span>
			        		</a>
						</s:else>
	        		</li>
	        	</s:iterator>
	        	<s:if test="#request.page==#request.lastpage || #request.lastpage == 0">
					<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
				</s:if>
				<s:else>
					<li class="page_next"><a href="javascript:jumppage('<s:property value="#request.page+1"/>')"><span class="">下一页</span></a></li>
				</s:else>
			</ul>
		</div>
	</div>
</div>
<div class="clear h20"></div>
</s:if>
<!-- 我的比价 结束--></div>
</div>
</div>
</div>
</form>
<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
<script type="text/javascript">
    function openFile (path){

        var arr = path.split("/");
        var filename = arr[arr.length -1];

        window.open ("../../wj/FileDownLoad.jsp?filepath=" + path + '&filename=' + filename);

    }

    function getElectronicPath(recognizeeSn,id){

        var riskCode = jQuery("#riskCode").val();

        //ajax 访问
        $("#orderInfoForm").ajaxSubmit( {
            type : "get",
            url : "order_query!electronicDownload.action?recognizeeSn="+recognizeeSn+"&riskCode="+riskCode+"&id="+id,
            dataType : "json",
            beforeSend:function(){
                jQuery("#"+id).html("正在下载...");
            },
            success : function(data) {
                if(data.STATYS == '1'){
                    var policyPath = data.policyPath;
                    var arr = policyPath.split("/");
                    var filename = arr[arr.length -1];
                    //jQuery("#"+id).after('<a target="_blank" href=' + path + '/' + policyPath + '>保单下载</a>');
                    jQuery("#"+id).after('<a href= ../../wj/FileDownLoad.jsp?filepath=' + policyPath + '&filename=' + filename + '>保单下载</a>');
                    jQuery("#"+id).remove();
                    openFile(policyPath);
                }else{
                    jQuery("#"+id).html("保单下载");
                    jQuery.tip(data.Message);
                }
            }
        });
    }

    function jumppage(pageindex) {
        var ordersn=jQuery("#orderSn").val();
        location.href="order_query!receiptsDownload.action?page="+pageindex+"&orderSn="+ordersn+"";
    }

    function cancelCont(id) {
        jQuery.ajax({
            url:"order_query!checkCancelCont.action?id="+id,
            type:"post",
            dataType:"json",
            async: false,
            success:function(data) {
                if (data.status == "0") {
                    artDialogTip(data.message);
                }else if (data.status == "1") {
                    artDialogMsg(id, data.phone);
                }
            }
        });
    }

    //artDialog 提示框
    function artDialogTip(tipTxt){
        art.dialog({
            id:'shaop_car2',
            title: '提示',
            content: '<div style="padding:10px 40px; text-align: center;">' + tipTxt + '</div>',
            fixed: true,
            button: [
                {
                    name: '确认'
                }
            ]
        });
    }

    //artDialog 确认框
    function artDialogMsg(id, phoneNum){
        art.dialog({
            id:'shaop_car',
            title: '提示',
            content: '<div style="padding:10px 40px; text-align: center;">保单如果取消，被保险人将不再享有保险保障<br />确认取消保单请验证投保人身份取消保单' +
            '<p style="text-align: left; color: #fd8824; font-weight: bold;font-size: 14px; padding: 10px 0 11px;">' + phoneNum + '</p>' +
            '<p class="code_wrap" style="overflow: hidden;"><input id="msg_nbr" value="请输入手机收到的验证码" style="float: left; width: 140px; height: 26px; padding: 0 5px; color: #888; border: 1px solid #eee; line-height: 26px; border-radius: 3px;" type="text"><span class="code_again" style="float: right;">点击发送验证码</span></p>' +
            '</div>',
            fixed: true,
            button: [
                {
                    name: '提交',
                    callback: function () {
                        var sendCode = jQuery('#msg_nbr').val();
                        if (sendCode != "" && sendCode != null) {
                            jQuery.ajax({
                                url: "order_query!cancelCont.action?id="+id+"&phoneCode="+sendCode,
                                type: "post",
                                dataType: "json",
                                success: function(data){
                                    var status = data.status;
                                    var msg = data.msg;
                                    if (status == 0) {
                                        art.dialog({
                                            id:'shaop_car',
                                            title: '提示',
                                            content: '<div style="padding:10px 40px; text-align: center;">' + msg + '</div>',
                                            fixed: true,
                                            button: [
                                                {
                                                    name: '确认',
                                                    callback: function () {
                                                        window.location.reload();
                                                    }

                                                }
                                            ]
                                        });
                                    }else{
                                        artDialogTip(msg);
                                    }

                                }
                            });
                        } else {
                            art.dialog.alert("请输入手机收到的验证码");
                        }
                    }
                }
            ]

        });

        jQuery('#msg_nbr').bind('focus', function() {
            if(this.value == '请输入手机收到的验证码') this.value = '';
        });
        jQuery('#msg_nbr').bind('blur', function() {
            if(this.value == '') this.value = '请输入手机收到的验证码';
        });

        // 倒计时
        new timeCountDown({
            whichPage: 'sign',
            deadLine: 60,
			endHtml: '点击发送验证码',
            sendCode: function () {
                if(phoneNum!=null && phoneNum!=""){
                    jQuery.ajax({
                        url: "order_query!cancelContSendCode.action?rtype=1&ways="+phoneNum+"&id="+id,
                        type: "post",
                        dataType: "html",
                        success: function(data){
                            artDialogTip(data)
                            //art.dialog.alert(data);
                        }
                    });
                }
            }
        });
    }


</script>
</body>
</html>
