<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] /> 
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>职业类别搜索</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/>
<script language="javascript" type="text/javascript">
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="up-bg-qh">
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
    <div class="g-weaper">
        <div class="daohang">
      <span class="daohang_home"></span>
      <a target="_self" href="../"><span class="orange">您所在的位置：职业类别</span></a>
    </div>
      <div class="classes_con">
          <div class="classes_box" id="class_nav">
            <ul>
              <li>搜索</li>
              <li><input type="text" id="classSearch" class="classtext"  value="" defaultval="请输入职业名称或类别（中文）" autocomplete="off" />
                  <label for="classSearch" class="classsearch_p">请输入职业名称或类别（中文）</label></li>
              <li><span class="classes_up desable_btn"></span><span class="classes_down desable_btn"></span></li>
              <li><span class="classhelp zixun "  vlpageid="xiaoneng" >客服帮我找</span></li>
              <#if (occDownAddr??) && (occDownAddr?length gt 0)>
              	<li><a href="${occDownAddr}" class="class_down">下载</a></li>
              </#if>
            </ul>
          </div>
          <div class="classec_fs">可投保职业 ${OccupLevel}类</div>
          <div class="classecd">
            <table>
              <thead>
                <tr>
                  <th>大分类</th>
                  <th>中分类</th>
                  <th>小分类</th>
                  <th>职业等级</th>
                </tr>
              </thead>
              <tbody>
              	  <#list threeLevelOccupations as occ>
              	  		<#if occ_index == 0>
              	  			<tr>
              	  				 <td rowspan="${occ.l1Count}" class="class_tc">${occ.level1}</td>
              	  				 <td rowspan="${occ.l2Count}" class="class_tc">${occ.level2}</td>
                  				 <td  class="class_td_s">${occ.level3}</td>
                  				 <td>${occ.grade}</td>
              	  			</tr>
              	  		<#else>
              	  			<#if occ.level1 == threeLevelOccupations[occ_index-1].level1>
              	  				<#if occ.level2 == threeLevelOccupations[occ_index-1].level2>
              	  					<tr>
					                  <td class="class_td_s">${occ.level3}</td>
					                  <td>${occ.grade}</td>
					                </tr>
              	  				<#else>
					                <tr>
					                  <td rowspan="${occ.l2Count}" class="class_tc">${occ.level2}</td>
					                  <td  class="class_td_s">${occ.level3}</td>
					                  <td>${occ.grade}</td>
					                </tr>
              	  				</#if>
              	  			<#else>
              	  				<tr>
	              	  				 <td rowspan="${occ.l1Count}" class="class_tc">${occ.level1}</td>
	              	  				 <td rowspan="${occ.l2Count}" class="class_tc">${occ.level2}</td>
	                  				 <td  class="class_td_s">${occ.level3}</td>
	                  				 <td>${occ.grade}</td>
	              	  			</tr>
              	  			</#if>
              	  		</#if>
                  </#list> 
              </tbody>
            </table>
          </div>
      </div>
    </div>
    


   <div class="clear"></div>
<!-- 底部开始 -->
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<!-- 底部结束 -->
<script type="text/javascript" src="http://resource.kaixinbao.com/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.cookie.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.form.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jqueryupdate.js"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/re_base.js"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/re_header.js"></script>
<script type="text/javascript" src="${shopStaticPath}/artDialog.js"></script>
<script type="text/javascript" src="${shopStaticPath}/login.js"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/re_footer.js"></script> 
<script type="text/javascript" src="${shopStaticPath}/redesign/xiaoneng_CustomerService.js?version=20160930"></script>
<script type="text/javascript" src="http://dl.ntalker.com/js/xn6/ntkfstat.js?siteid=kf_9401" charset="utf-8"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/re_class.js"></script> 

<script>

jQuery(document).ready(function($) {
   jQuery("#class_nav").fixedNavigation({
      fixName: "fixed"
    });
    
   new keywordSel();
});
 

  
</script>
</body>
</html>