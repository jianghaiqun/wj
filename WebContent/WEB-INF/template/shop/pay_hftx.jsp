<!DOCTYPE HTML >
<%@ page contentType="text/html; charset=utf-8" isELIgnored="false"%>
<body onload=javascrip:document.forms[0].submit();>
<div style="display: none">
	<form name="form" method="post" action="${GateUrl }" target="_top">
			<table border=1 width=650>
				<tr>
					<td width=150 height=27>版本号</td>
					<td width=80>Version</td>
					<td width=350><input type="text" name="Version" id="Version" value="${Version}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>消息类型</td>
					<td width=80>CmdId</td>
					<td width=350><input type="text" name="CmdId" id="CmdId" value="${CmdId}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>商户号</td>
					<td width=80>MerId</td>
					<td width=350><input type="text" name="MerId" id="MerId" value="${MerId}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>订单号</td>
					<td width=80>OrdId</td>
					<td width=350><input type="text" name="OrdId" id="OrdId" value="${OrdId}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>订单金额</td>
					<td width=80>OrdAmt</td>
					<td width=350><input type="text" name="OrdAmt" id="OrdAmt" value="${OrdAmt}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>币种</td>
					<td width=80>CurCode</td>
					<td width=350><input type="text" name="CurCode" id="CurCode" value="${CurCode}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>商品编号</td>
					<td width=80>Pid</td>
					<td width=350><input type="text" name="Pid" id="Pid" value="${Pid}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>页面返回地址</td>
					<td width=80>RetUrl</td>
					<td width=350><input type="text" name="RetUrl" id="RetUrl" value="${RetUrl}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>后台返回地址</td>
					<td width=80>BgRetUrl</td>
					<td width=350><input type="text" name="BgRetUrl" id="BgRetUrl" value="${BgRetUrl}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>商户私有数据项</td>
					<td width=80>MerPriv</td>
					<td width=350><input type="text" name="MerPriv" id="MerPriv" value="${MerPriv}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>网关号</td>
					<td width=80>GateId</td>
					<td width=350><input type="text" name="GateId" id="GateId" value="${GateId}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>用户手机号</td>
					<td width=80>UsrMp</td>
					<td width=350><input type="text" name="UsrMp" id="UsrMp" value="${UsrMp}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>分账明细</td>
					<td width=80>DivDetails</td>
					<td width=350><input type="text" name="DivDetails" id="DivDetails" value="${DivDetails}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>付款人用户号</td>
					<td width=80>PayUsrId</td>
					<td width=350><input type="text" name="PayUsrId" id="PayUsrId" value="${PayUsrId}"/></td>
				</tr>
				<tr>
					<td width=150 height=27>数字签名</td>
					<td width=80>ChkValue</td>
						<td width=350><input type="text" name="ChkValue" id="ChkValue" value="${ChkValue}"/></td>
				</tr>
				<tr>
					<td colspan="3" align="center" height=27><input type="submit" name="Submit" value="提交"></td>
				</tr>
			</table>
	</form>
</div>
</body>