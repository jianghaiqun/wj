<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-我的邀请</title>
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<script type="text/javascript"
	src="<%=Config.getValue("JsResourcePath")%>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body class="up-bg-qh">
<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span>
			<a
				href='<%=Config.getFrontServerContextPath()%>' target='_self'>
				<span
				class="orange">您现在的位置：首页</span>
			</a>
			<span class="separator">></span>
			<a
				href='member_center!index.action' target='_self'>会员中心</a>
			<span
				class="separator">></span>
			<span class="separator1">我的邀请</span>
		</div>
		<s:iterator id="list" value="#request.recommInfo">
		<div class="member_con">
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right ">
				<div class="member_boxs">
					<div class="member-htit"><span class="member-titsc">推荐规则</span></div>
					<ul class="recommend_gz">
						<li>1、开心保网为您生成专属推荐链接。</li>
					<s:if test="#list.recommRegCount != null && #list.recommRegCount !='' && #list.recommRegCount !='0'">
						<li>2、通过您的分享链接，在被推荐人注册并<span class="hight-col">验证手机</span>后，您将获得<span class="hight-col"><s:property value="#list.recommRegCount"/></span>个积分；
						<s:if test="#list.recommRegisterCount != null && #list.recommRegisterCount !='' && #list.recommRegisterCount !='0'">
							推荐好友注册上限<span class="hight-col"><s:property value="#list.recommRegisterCount" /></span>人。
						</s:if>
						</li>
						<li>3、若此推荐与其他活动冲突时，以活动具体规则为准。</li>
					</s:if>
					<s:else>
						<s:if test="#list.recommBuyPoints != null && #list.recommBuyPoints !='' && #list.recommBuyPoints !='0'">
							<li>2、通过您的分享链接，在被推荐人投保成功并起保后，作为推荐人您将获得推荐用户首次购买获得积分的<s:property value="#list.recommBuyPoints" />；</li>
							<li>3、若此推荐与其他活动冲突时，以活动具体规则为准；</li>
						</s:if>
						<s:else>
							<li>2、若此推荐与其他活动冲突时，以活动具体规则为准；</li>
						</s:else>
					</s:else>
					</ul>
					<div class="friend_recommend">
						<ul class="friend-recul">
							<li>推荐好友注册已获得<span><s:property value="#list.getRecommRegCount" /></span>个积分；</li>
          					<li>推荐好友注册后进行购买已获得<span><s:property value="#list.getRecommRegBuyCount" /></span>个积分。</li>
          					<li><a href="point!newList.action#member_commantable">详情查看</a></li>
						</ul>
					</div>
					<div class="member-htit"><span class="member-titsc">推荐途径</span></div>
					<div class="recommend_gz">
						<h3 class="recommend_h3">发送专属推荐链接</h3>
						<span name=""  class="recommend_gzs" id="clipboard"><s:property value="#list.recommDesc" /><s:property value="#list.recommUrl" />
						</span>
						<a href="###" class="recommend_a copyLink" data-clipboard-target="clipboard"  >复制</a>
						<div class="bshare-custom">
							<a title="分享到QQ空间" class="bshare-qzone"></a>
							<a title="分享到新浪微博" class="bshare-sinaminiblog"></a>
						</div>
						<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
						<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>

						<div class="clear"></div>
						<h3 class="recommend_h3">邮件推荐（最多可增加至10个好友）</h3>
						<ul class="email_list">
							<li>
								<input type="text" id="tj_mail" verify="电子邮箱|NOTNULL&RECOEMAIL" onblur="verifyElement('电子邮箱|NOTNULL&RECOEMAIL',this.value,this.id);"   onfocus="if (this.value == '填写您的好友邮件地址即可') {this.value = '';}" value="填写您的好友邮件地址即可"  class="friend_email">
								<span class="add_email_bt">增加地址</span><label class="requireField"></label>
							</li>
						</ul>
						<a href="javascript:void(0);" class="recommend_a" onclick="sendEmail()">发送</a>
			            <input type="hidden" id="recommTitle" value="<s:property value="#list.recommTitle" />" />
			            <input type="hidden" id="recommUrl" value="<s:property value="#list.recommUrl" />" />
			            <input type="hidden" id="recommPic" value="<s:property value="#list.recommPic" />" />
			            <input type="hidden" id="recommDesc" value="<s:property value="#list.recommDesc" />" />
			            <input type="hidden" id="recommMailTitle" value="<s:property value="#list.recommMailTitle" />" />
						<div class="h40"></div>
					</div>
				</div>
			</div>
		</div>
		</s:iterator>
	</div>
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.cookie.js"></script>
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/Common.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/new_member.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/ZeroClipboard.min.js"></script>
	<a class="bshareDiv" onclick="javascript:return false;"></a>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/VerifyInput.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/new_member.js"></script>
 </body>

 <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
<a class="bshareDiv" onclick="javascript:return false;"></a>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
<script type="text/javascript" charset="utf-8">
	bShare.addEntry({
	    title:jQuery("#recommTitle").val(),
	    url:jQuery("#recommUrl").val(),
	    summary:jQuery("#recommDesc").val(),
	    pic:jQuery("#recommPic").val()
	});
	function sendEmail() {
		var emails = jQuery(".email_list input");
		var str = ""
		var flag= true;
			emails.each(function() {
				if (jQuery(this).val() != '填写您的好友邮件地址即可') {
					if (!verifyElement('电子邮箱|NOTNULL&RECOEMAIL', jQuery(this).val(), this.id)) {
						flag = false;
					} else {
						str += jQuery(this).val() + ";";
					}
				}
				
			});
			
			if (!flag) {
				return;
			}
			
			if (str=='') {
				alert("请输入电子邮箱!");
				return;
			}
			str = str.substr(0, str.length - 1);
			
			art.dialog({
				id: 'artRecommend',
			    title: '提示',
			    width:200,
			    content: '正在发送...'
			    
			});
			
			jQuery.ajax({
				type : "POST",
				url : sinosoft.base
						+ '/shop/member_recommend!sendMail.action?emailAdress='
						+ str + '&title=' + jQuery("#recommTitle").val()
						+ '&recommUrl=' + jQuery("#recommUrl").val()  ,
				dataType : "json",
				async : false,
				success : function(data) {
					art.dialog.list['artRecommend'].content(data.msg);
				}
			});
		        
		    
		
	
	}
	
	jQuery(document).ready(
			function() {
				var isIE = 0/* @cc_on+1@ */;

				if (!isIE) {

						var clip = new ZeroClipboard(jQuery(".copyLink"), {
							moviePath : "../../js/ZeroClipboard.swf"
						});

						clip.on("complete", function(client, args) {
							alert("代码已成功复制到剪贴板");
						});
					
					

				} else {
					jQuery(".copyLink").click(
							function(e) {

								e.preventDefault();

								window.clipboardData.setData("Text",
										jQuery("#clipboard").text());
								alert("代码已成功复制到剪贴板");

							});
				}

				// 邮件推荐
				jQuery(".add_email_bt")
						.click(
								function() {
									var emaillist = jQuery(".email_list li").length;
									var pd = jQuery(this).text();
									if (pd == "增加地址") {
										if (emaillist >= 10) {
											alert("最多可增加10个好友");
										} else {

											var s = [
													'<li><input type="text" id="tj_mail_'
															+ emaillist
															+ '"   verify="电子邮箱|NOTNULL&RECOEMAIL" onblur="verifyElement(\'电子邮箱|NOTNULL&RECOEMAIL\',this.value,this.id);"   onfocus="if (this.value == \'填写您的好友邮件地址即可\') {this.value = \'\';}" value="填写您的好友邮件地址即可"  class="friend_email">',
													'						<span class="rel_email_bt">减少地址</span><label class="requireField"></label></li>' ]
													.join("");
											jQuery('.email_list')
													.append(
															jQuery(s)
																	.clone(
																			true))
													.find(".rel_email_bt")
													.live(
															"click",
															function() {
																jQuery(this)
																		.parent()
																		.remove();
															});

										}
									}

								});

			});
</script>
</html>