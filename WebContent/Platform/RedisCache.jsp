<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Redis缓存管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function deleRedisCache() {
	if(!check()){
		return false;
	}
	var tip = getTip();
	Dialog.confirm(tip, function() {
		var val = getRadioVal();
		var dc = new DataCollection();	
		dc.add("ProductIDHI", $V("ProductIDHI"));
		dc.add("CompanyIDHI", $V("CompanyIDHI"));
		dc.add("DelType", val);
		Server.sendRequest(
				"com.sinosoft.product.ProductRedisCache.deleRedisCache",
				dc, function(response) {
					Dialog.alert(response.Message);	
				});
	});
}
function getRadioVal() {
	var radio = document.getElementsByName('DelType');
	for (var i = 0; i < radio.length; i++) {
		if(radio[i].checked==true){
			return radio[i].value;
		}
	}
	return '';
}
function check(){
	var val = getRadioVal();
	var product = $V("ProductIDHI");
	var company = $V("CompanyIDHI");
	if(val==1||val==4||val==5||val==6||val==7||val==8){
		if(product==''){
			Dialog.alert('请先输入产品',function(){
				$('ProductIDHI').focus();
			});
			return false;	
		}
	}
	if(val==2){
		if(company==''){
			Dialog.alert('请先选择保险公司',function () {
				$('CompanyIDHI_textField').focus();
			});
			return false;	
		}
	}
	return true;
}
function getTip() {
	var val = getRadioVal();
	var product = $V("ProductIDHI");
	var company = $V("CompanyIDHI");
	if(val==1){
		$S("CompanyIDHI",'');
		return '您确定删除产品['+product+']的缓存吗?';
	}
	if(val==4){
		$S("CompanyIDHI",'');
		return '您确定删除产品['+product+'的投保录入页]的缓存吗?';
	}
	if(val==2){
		$S("ProductIDHI",'');
		return '您确定删除保险公司['+$V('CompanyIDHI_textField')+']的所有产品以及保险公司的缓存吗?';
	}
	if(val==3||!company&&!product){
		return '您确定删除所有缓存吗?';
	}
	if(val==5){
		$S("CompanyIDHI",'');
		return '您确定删除产品['+product+'的详细页]的缓存吗?';
	}
	if(val==6){
		$S("CompanyIDHI",'');
		return '您确定删除产品['+product+'的销量]的缓存吗?';
	}
	if(val==7){
		$S("CompanyIDHI",'');
		return '您确定删除产品['+product+'的评论数]的缓存吗?';
	}
	if(val==8){
		$S("CompanyIDHI",'');
		return '您确定删除产品['+product+'的现金价值]的缓存吗?';
	}
}
window.onload = function(){
	$E.disable('CompanyIDHI');
	var radio = document.getElementsByName('DelType');
	for (var i = 0; i < radio.length; i++) {
			radio[i].onclick = function(){
			var val = this.value;
			if(val==1||val==4||val==5||val==6||val==7||val==8){
				$S("CompanyIDHI",'');
				$E.disable('CompanyIDHI');
				$E.enable('ProductIDHI');
			}
			if(val==2){
				$S("ProductIDHI",'');
				$E.disable('ProductIDHI');
				$E.enable('CompanyIDHI');
			}
			if(val==3){
				$S("CompanyIDHI",'');
				$E.disable('CompanyIDHI');
				$S("ProductIDHI",'');
				$E.disable('ProductIDHI');
			}
		}
	}
}
</script>
</head>
<body>
<z:init method="com.sinosoft.product.ProductRedisCache.init">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
  <tr valign="top">
    <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
      <tr>
        <td valign="middle" class="blockTd"><img src="../Icons/icon018a1.gif" />Redis缓存管理</td>
      </tr>
      <tr>
        <td style="padding:0"><table width="60%" border="0" cellspacing="6" style="border-collapse: separate; border-spacing: 6px;">
          <tr>
            <td width="55%" valign="top"><table width="100%" cellpadding="0" cellspacing="0" class="dataTable">
              <tr class="dataTableHead">
                <td width="20%" height="30" align="left" type="Tree"><b>系统信息项&nbsp;</b></td>
                <td width="60%" height="30" align="left" ><b>产品&保险公司&nbsp;</b></td>
              </tr>
				<tr>
					<td>Redis缓存管理：</td>
					<td style="word-wrap: break-word; word-break:keep-all;height: 40px;color: #ff0000;">
					<div>
						删除指定产品需要输入产品编码，如果删除保险公司的产品需要选择保险公司。
					</div>
					<div>
						全部删除不需要输入数据。
					</div>
					</td>
				</tr>					                
				<tr>
					<td>选择删除类型：</td>
					<td> 
					<p>
						<input checked type="radio" id="type1" name="DelType" value="1"/><label for="type1">删除指定产品</label>
					</p>
					<p>
						<input type="radio" id="type4" name="DelType" value="4"/><label for="type4">删除投保录入页初始化信息</label>
					</p>
					<p>
						<input type="radio" id="type2" name="DelType" value="2"></input><label for="type2">删除保险公司及其产品</label>
					</p>
					<p>
						<input type="radio" id="type5" name="DelType" value="5"></input><label for="type5">删除产品详细页</label>
					</p>
					<p>
						<input type="radio" id="type6" name="DelType" value="6"></input><label for="type6">删除产品销量</label>
					</p>
					<p>
						<input type="radio" id="type7" name="DelType" value="7"></input><label for="type7">删除产品评论数</label>
					</p>
					<p>
						<input type="radio" id="type8" name="DelType" value="8"></input><label for="type8">删除现金价值</label>
					</p>
					<p>
						<input type="radio" id="type3" name="DelType" value="3"></input><label for="type3">删除全部</label>
					</p>
					 </td>
					
				</tr>
				<tr>
					<td></td>
					<td> 
					<span id="CompanyIDHI_span">
						保险公司：<z:select name="CompanyIDHI" id="CompanyIDHI" style="width:150px;">${CompanyIDList}</z:select>
					</span>
					<span id="ProductIDHI_span">
						产品编码：<input name="ProductIDHI" type="text" id="ProductIDHI" value=""   style="width:150px">   
					</span>
					<span>&nbsp;</span>
					 </td>
					
				</tr>
				<tr>
					<td>操作按钮：</td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="deleRedisCache();">
							<img src="../Icons/icon002a9.gif" />清除缓存</z:tbutton>
					</td>
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
</z:init>
</body>
</html>