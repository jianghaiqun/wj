<!DOCTYPE html >
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营销活动后台 - 开心保保险网</title>
<%

String serverContext = Config.getFrontServerContextPath(); 
String serverPath = Config.getContextPath();
String staticPath = Config.getValue("StaticResourcePath");
String jsStaticPath = Config.getValue("JsResourcePath");
String articleId = request.getParameter("ArticleID");

%>
<link href="<%=staticPath %>/style/redesign/re_header.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=staticPath %>/style/activity.css"/>
<link rel="stylesheet" type="text/css" href="<%=staticPath %>/style/new_logo.css"/>
<link rel="stylesheet" type="text/css" href="<%=staticPath %>/style/redesign/re_list.css"/>
<link rel="stylesheet" type="text/css" href="<%=staticPath %>/style/mod_style.css"/>
<script>
var complicatedFlag = "N";
</script>
</head>
<z:init method="com.sinosoft.cms.dataservice.ProductListYX.initEidt">
<body style="background-color:${Color}">
<div id="wrapper">
	<div id="headerArea">
		<div class="headerCont">
			<h1><a href="#"><img src="<%=staticPath %>/images/sem/semlog_02.gif" alt="开心保 买保险就是买开心" /></a></h1>
			<p class="tel">4009-789-789</p>
			<ul id="menuArea">
			${navHTML}
			</ul>
			<p id="btnMenu"><a href="#">导航修改</a></p>		
		</div>
		<input type="hidden" id="articleId" value="${ArticleID}"/>
		<input type="hidden" id="catalogId" value="${CatalogId}"/>
		<input type="hidden" id="frontURL" value="<%=serverPath%>"/>
		<input type="hidden" id="staticUrl" value="<%=staticPath%>"/>
	<!-- end headerArea --></div>
	
	<div id="mainArea">
		<div id="setColor">
			<p>页面颜色：<select id="selColor">
							 <option value="">无</option>
							 <option value="white">白色</option>
							 <option value="yellow">黄色</option>
							 <option value="blue">蓝色</option>
							 <option value="green">绿色</option>
							 <option value="orange">橙色</option>
							 <option value="red">红色</option>
							 <option value="gray">灰色</option>
						  </select></p>
			<p>自定义色：<input class="txtColor" id="txtColor" type="text" value="${txtColor}" /><a id="sbtPreview" href="#">预览</a></p>
			<p><a id="sbtColor" href="#" onclick="saveColor();">确定</a></p>
		<!-- end setColor --></div>
		
		<div id="bnrShow">
			<p class="imgBnr"><a href="${PictrueLink}">
			<img src="${PictruePath}" alt="" id="imageSrc"/></a></p>
			</a>
			<p class="text edit" style="background-color:${WordLeftBack};color:${WordLeftColor}">${WordLeft}</p>
			<p id="btnBnr"><a href="#">替换图片</a></p>
		</div>
		<div id="textIntro">
			<p><span>自定义文字：</span><input type="text" value="${WordRight}" id="rightWords"/></p>
			<p><span>自定义链接：</span><input type="text" value="${WordRightLink}" id="rightLink"/></p>
			<p class="btn"><a href="#" onclick="saveRightWords();">保存</a></p>
		</div>

		<div class="productList">${txtHtml}</div>
		
		<div id="addProduct" class="section">
			<p><a href="#">添加模块</a></p>
		</div>
		<div id="saveProduct" class="section">
			<p><a href="###" onclick="saveInfo();">保存模块</a></p>
		</div>
		
		<div id="introArea" class="section">
			<p class="hide"><a href="#" onclick="hidPre();" id="hidPre">隐藏</a></p>
			<input type="hidden" id="hidFlag" value="${PrePicHid}">
			<div class="control" id="pre">
			<div class="introCont">
				<p class="showImg"><img src="${preImg}" alt="" id="preImgSrc" height="336px" width="482px"/></p>
				<p class="btn"><a href="#">上传图片</a></p>
			</div>
			
			<div id="order_con"></div>
			</div>
			
		<!-- end introArea --></div>
		
		<div id="serviceArea" class="section">
			<div class="guide_clm">
				<p class="ttl">理赔指引</p>
				<p class="img"><img alt="" src="<%=staticPath %>/images/sem/img_guide_clm.gif"></p>
				<ol>
					<li>
						<strong>1 出险报案</strong>
						发生保险事故后，请第一时间拨打开心保客服电话报案。开心保为您开通客服专线<span>4009-789-789</span>、在线客服以及客服邮箱<span><a href="mailto:kf@kaixinbao.com">kf@kaixinbao.com</a></span>等多种报案协助申请方式。
					</li>
					<li>
						<strong>2 理赔申请</strong>
						收集并提交相关材料办理理赔事项。
					</li>
					<li class="last">
						<strong>3 结案赔付</strong> 等待理赔结果通知（一般案件会在10个工作日左右结案）。
					</li>
				</ol>
			</div>
		</div>
		
		<div id="menuBox">
			<div class="menuBg">
				<ul class="menuCont">
					${menuCont}
				</ul>
				<ul class="menuBtn">
					<li><a class="edit" href="#">编辑</a></li>
				</ul>
			</div>
		<!-- end menuBox --></div>
		
	<!-- end mainArea --></div>

	<div id="editMenu" style="display: none;">
		<p class="ttl">编辑导航栏</p>
		<ul class="editList"></ul>
		<div class="editArea">
			<p>编辑内容：<input class="editText" type="text" /></p>
			<p>连接地址：<input class="editLink" type="text" /></p>
			<p class="btnEdit"><a href="#">完成</a><span class="prmp">编辑成功！</span></p>
		</div>
		<p class="btnArea">
			<a class="add" href="#">添加</a>
			<a class="del" href="#">删除</a>
			<a class="submit" href="#" onclick="addNav();">确认修改</a>
		</p>
		<p class="close"><a href="#"><img src="<%=staticPath %>/images/btn_close.gif" alt="" /></a></p>
	<!-- end editMenu --></div>
	<form id="bannerForm" name="bannerForm"  action="<%=serverPath %>/shop/product_marketing!onUploadPhoto.action" enctype="multipart/form-data">
	<div id="editBnr" style="display: none;">
		<p class="ttl">图片上传</p>
		<p class="intro">宽度可选择2000与980两种。图片宽度自适应</p>
		<div class="editCont">
			<p class="image">
			<img src="${PictruePaths}" alt="" id="bannerImg" width="186px" height="164px"/>
				<span class="dark">点击更换图片</span>
				<input type="file" class="fileUp" id="uploads" name="uploads" onchange="onUploadPhoto(this,'banner')"> 
				<div id="info"></div>
			</p>
			<p><span>链接地址：</span><input type="text" class="urlText" id="linkTxt" value="${PictrueLink}" /></p>
			<p><span>文字编辑：</span><input type="text" class="txtText" id="wordTxt" value="${WordLeft}" /></p>
			<p><span>文字颜色：</span><input type="text" class="colText" id="wordColor" value="${WordLeftColor}" /></p>
			<p><span>文字背景色：</span><input type="text" class="colText" id="wordBackColor" value="${WordLeftBack}" /></p>
		</div>
		<p class="btns"><a class="save" href="#" onclick="saveBanner();">保存</a></p>
		<p class="close"><a href="#"><img src="<%=staticPath %>/images/btn_close.gif" alt="" /></a></p>
	</div>
	</form>
	<div id="popupEdit" style="display: none;">
	<input type="hidden" value ="" id="editPro">
		<p class="ttl">编辑商品</p>
		<p><span>产品编号：</span><input class="proId" type="text" /></p>
		<p><span>产品备注：</span><textarea class="proRemark"></textarea></p>
		<p class="btn"><a href="#" onclick="savePro();">确定</a></p>
		<p class="close"><a href="#"><img src="<%=staticPath %>/images/btn_close.gif" alt="" /></a></p>
	<!-- end popupEdit--></div>
	<div id="cmtPopup" style="display: none;">
		<p class="ttl">添加评论</p>
		<p><span>评论内容：</span><textarea id="commentId"></textarea></p>
		<p class="btn"><a href="#" onclick="saveCom();">确定</a></p>
		<p class="close"><a href="#"><img src="<%=staticPath %>/images/btn_close.gif" alt="" /></a></p>						
	<!-- end cmtPopup--></div>
	<form id="preForm" name="preForm"  action="<%=serverPath %>/shop/product_marketing!onUploadPhoto.action" enctype="multipart/form-data">
	<div id="editIntro" style="display: none;">
		<p class="ttl">图片上传</p>
		<p class="intro">宽度为482px，高度为336px</p>
		<div class="editCont">
			<p class="image">
				<img src="${preImgs}" alt="" id="preImg" height="168px" width="241px"/>
				<span class="dark">点击更换图片</span>
				<input type="file" class="fileUp" id="uploads" name="uploads" onchange="onUploadPhoto(this,'pre')"> 
			</p>
		</div>
		<p class="btns"><a class="save" href="#" onclick="savePrePicture();">上传</a></p>
		<p class="close"><a href="#"><img src="<%=staticPath %>/images/btn_close.gif" alt="" /></a></p>
	<!-- end editBnr --></div></form>
	
	<div id="dark" style="display: none;"></div>
<!-- end wrapper --></div>
<s:include value="http://www.kaixinbao.com/block/kxb_footer_new_common.shtml" ></s:include>
<script type="text/javascript" src="<%=jsStaticPath %>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/jqueryupdate.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/redesign/re_base.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/redesign/re_header.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/redesign/re_footer.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/artDialog.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/login.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/jquery.qtip.js"></script>
<script	type="text/javascript" src="<%=jsStaticPath %>/js/iframeTools.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/premcalculate.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/productCompare.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/tabchange.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/stencil/stencil_order.js"></script>
<script type="text/javascript" src="<%=jsStaticPath %>/js/plugin.js"></script>
<script type="text/javascript">
jQuery(document).ready(function() {

	jQuery("#btnMenu").menuBoxEdit();
	jQuery("#bnrShow").editBnrPic();
	jQuery("#setColor").bgSetColor();
	jQuery(".productList").productBoxEdit();
	jQuery("#introArea").upLoadPicture();
	jQuery("#menuBox").editMenuBox();
	
	
});
</script>
</body>
</z:init>
</html>