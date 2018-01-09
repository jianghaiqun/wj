<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.points.IntegralSystem.initEditIntegral">
	<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<title></title>
	<script src="../Framework/Main.js"></script>
		<script type="text/javascript" src="../template/common/js/jquery.js"></script>
	<script type="text/javascript">
	jQuery(document).ready(function(){
		var type=jQuery("#type").val();
		if (type == '04') {
			jQuery("#PointsNumTd").text('加成比例：');
			jQuery("#PointsNum").attr("verify","加成比例|PositiveNumber&&NotNull");
		} else {
			jQuery("#PointsNumTd").text('积分数：');
			jQuery("#PointsNum").attr("verify","积分数|Int&&NotNull");
		}
	});
	</script>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body class="dialogBody">
	<form id="form2">
	<table width="100%" height="100%" border="0">
		<tr>
			<td valign="middle">
			<input value="${type}" type="hidden" id="type" name="type" />
			<table width="580" height="197" align="center" cellpadding="2" cellspacing="0" border="0">
				<tr height="10">
					<input name="Id" type="hidden" id="Id" value="${Id}"/>
					<td colspan="4"></td>
				</tr>
				<tr height="30">
					<td align="right">会员等级：</td>
					<td>
						<z:select id="MemberGrade" style="width:100px;" >${MemberGradeList}</z:select>
					</td>
					<td align="right"></td>
					<td>
					</td>
				</tr>
				<tr height="30">
					<td align="right">会员行为：</td>
					<td>
						<z:select id="MemberAct" style="width:100px;" verify="会员行为|NotNull">${MemberActList}</z:select>
					</td>
					<td align="right">积分赠送规则：</td>
					<td>
						<z:select id="PointsGive" style="width:100px;" verify="积分赠送规则|NotNull" onChange="changePointNum()">${PointsGiveList}</z:select>
					</td>
				</tr>
				<tr height="30">
					<td align="right">状态：</td>
					<td>
						<z:select id="Status" style="width:100px;" verify="状态|NotNull"  >${EnableList}</z:select>
					</td>
					<td align="right" id="PointsNumTd">积分数：</td>
					<td><input name="PointsNum" type="text" class="input1" id="PointsNum"
						value="${PointsNum}" size="10" verify="积分数|Int&&NotNull"/></td>
				</tr>
				<tr height="30">
					<td align="right">开始时间：</td>
					<td><input name="StartDate" type="text"  style="width:100px;" id="StartDate" value="${StartDate}"  ztype="date"/></td>
					<td align="right">截止时间：</td>
					<td><input name="EndDate" type="text"  style="width:100px;" id="EndDate" value="${EndDate}"    ztype="date"/></td>
				</tr>
				<tr height="30">
					<td align="right">文案描述：</td>
					<td colspan="3"><input name="PointDes" type="text" class="input1" id="PointDes"
						value="${PointDes}" size="60" verify="文案描述|Length<50&&NotNull"/></td>
				</tr>
				<tr>
					<td colspan="5" align="center" height="10"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</form>
	</body>
	</html>
</z:init>
