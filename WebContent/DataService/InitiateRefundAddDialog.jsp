<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <title></title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css" />
    <script src="../Framework/Main.js"></script>
	<script type="text/javascript" src="../template/common/js/jquery.js"></script>
    <script type="text/javascript">
    	function doOrderSearch(){
    		var diag = new Dialog("Diag2");
    		diag.Width = 680;
    		diag.Height = 350;
    		diag.Title = "订单列表";
    		diag.URL = "DataService/OrderSelectDialog.jsp";
    		diag.OKEvent = selectOrder;
    		diag.show();
    	}

    	function selectOrder(){
    		var arr = $DW.DataGrid.getSelectedValue("dg2");
    		if(!arr||arr.length==0){
    			Dialog.alert("请选中订单！");
    			return;
    		}
    		jQuery("tr[id^='PolicyNoTR_']").remove();
    		
    		var orderSn = $DW.DataGrid.getSelectedValueByColumns("dg2", "orderSn");
    		var policyNo = $DW.DataGrid.getSelectedValueByColumns("dg2", "policyNo");
    		var payPrice = $DW.DataGrid.getSelectedValueByColumns("dg2", "payPrice");
    		$S("OrderSn", orderSn.split(",")[0]);
    		var rowsPolicyNo = policyNo.split(",");
    		var rowsPayPrice = payPrice.split(",");
    		
			$S("num", rowsPolicyNo.length);
    		// 增加保单行数据
    		var PolicyRows = "";
    		for (var i = 1; i < rowsPolicyNo.length; i++) {
    			PolicyRows += "<tr id=\"PolicyNoTR_" + i + "\"><td align=\"right\">保单号：</td><td height=\"30\"> <input id=\"PolicyNo_" + i + "\" name=\"PolicyNo_" + i + "\" type=\"text\"" +
    				" class=\"input1\" readonly=\"readonly\" verify=\"保单号|NotNull\"/></td>" +
    				" <td height=\"30\"><input id=\"RefundAmount_" + i + "\" name=\"RefundAmount_" + i + "\" type=\"text\" class=\"input1\" size=\"10\"" +
    				" verify=\"返现金额|NotNull&&NUM\"/><input id=\"RefundAmounthidden" + i + "\" name=\"RefundAmounthidden" + i + "\" type=\"hidden\" /></td></tr>";
    		}
    		jQuery("#PolicyNoTR").after(PolicyRows);
    		for (var i = 0; i < rowsPolicyNo.length; i++) {
    			$S("PolicyNo_" + i, rowsPolicyNo[i]);
    			if ($V("RefundType") == '1') {
    				$S("RefundAmount_" + i, rowsPayPrice[i]);
    			} else {
    				$S("RefundAmounthidden" + i, rowsPayPrice[i]);
    			}
    		}
    		
    		$D.close();
    	}
    	function copy(){
    		if (jQuery("#copyAmount").attr("checked")==true) {
    			jQuery("input[id^='RefundAmount_']").each(function (){
    				jQuery(this).val(jQuery("#RefundAmount_0").val());
    			});
    		}
    	}
    	function RefundTypeChange(){
    		$S("point", "");
    		if ($V("RefundType") == '0') {
    			jQuery("#pointInfo").show();
    		} else {
    			jQuery("#pointInfo").hide();
    			if ($V("RefundType") == '2' || $V("RefundType") == '1') {
    				jQuery("#Prop1TR").show();
    				jQuery("#Prop2TR").show();
    				if ($V("RefundType") == '2') {
    					jQuery("#spanProp1").show();
        				jQuery("#spanProp2").show();
    				} else {
    					jQuery("#spanProp1").hide();
        				jQuery("#spanProp2").hide();
    				}
    			} else {
    				jQuery("#Prop1TR").hide();
    				jQuery("#Prop2TR").hide();
    			}
    		}
    	}
    	function deal(){
    		if ($V("point") == '') {
    			alert("请先输入返现比例");
    			return;
    		}
    		var i = 0;
    		jQuery("input[id^='RefundAmount_']").each(function (){
    			jQuery(this).val($V("RefundAmounthidden" + i++) * $V("point"));
    		});
    	}
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body class="dialogBody">
   <form id="AddForm">
   <input type="hidden" id="Approval" name="Approval" value="0"/>
       <table width="500" align="center" cellpadding="2" cellspacing="0">
           <tr>
               <td width="117" height="10"></td>
               <td width="160"></td>
               <td height="30"></td>
           </tr>
           <tr>
               <td align="right">返现类型：</td>
               <td height="30">
               	<z:select>
               		<select name="RefundType" id="RefundType" onchange="RefundTypeChange()">
	                	<option value="0">优惠返现</option>
	                	<option value="1">代客支付</option>
	                	<option value="2">淘宝测试</option>
	                	<option value="3">生效后退保</option>
               		</select>
               	</z:select>
               </td>
               <td></td>
           </tr>
           <tr>
               <td align="right">订单号：</td>
               <td height="30">
                  <input id="OrderSn" name="OrderSn" type="text" value="" class="input1" readonly="readonly" verify="订单号|NotNull"/>
               </td height="30">
               <td align="left"><input type="button" name="OrderSearch" value="订单选择" onClick="doOrderSearch()" id="OrderSearch">
                   <input type="checkbox" name="copyAmount" id="copyAmount" value="Y" onclick="copy()" />金额复制
				   <input type="hidden" id="num" name="num" value="1">
               </td>
           </tr>
           <tr id="pointInfo">
               <td align="right">优惠返现比例：</td>
               <td height="30">
                  <input id="point" name="point" type="text" value="" class="input1" />
               </td height="30">
               <td align="left"><input type="button" name="dealAmount" value="计算返现金额" onClick="deal()" id="dealAmount">
               </td>
           </tr>
           <tr id="PolicyNoTR">
               <td align="right">保单号：</td>
               <td height="30">
                   <input id="PolicyNo_0" name="PolicyNo_0" type="text" value="" class="input1" readonly="readonly" verify="保单号|NotNull"/>
               </td>
               <td align="left"><input id="RefundAmount_0" name="RefundAmount_0" type="text" value="" class="input1" size="10" verify="返现金额|NotNull&&NUM"/>
               		<input id="RefundAmounthidden0" name="RefundAmounthidden0" type="hidden" value=""/>
               </td>
           </tr>
           <tr id="Prop1TR">
               <td align="right">支付宝名称：</td>
               <td height="30">
                   <input id="Prop1" name="Prop1" type="text" value="" class="input1"/><span id="spanProp1" style="color:red;display: none;">&nbsp;*</span>
               </td>
           </tr>
           <tr id="Prop2TR">
               <td align="right">支付宝账号：</td>
               <td align="left">
                   <input id="Prop2" name="Prop2" type="text" value="" class="input1"/><span id="spanProp2" style="color:red;display: none;">&nbsp;*</span>
               </td>
           </tr>
           
           <tr>
               <td align="right">备注：</td>
               <td align="left">
                   <input id="Remark" name="Remark" type="text" value="" class="input1"/>
               </td>
           </tr>
       </table>
   </form>
</body>
</html>
