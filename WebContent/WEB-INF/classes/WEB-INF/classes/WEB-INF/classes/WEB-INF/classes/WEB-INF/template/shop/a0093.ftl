<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=gbk" />
        <title>保险测试结果</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body style="margin: 0; padding: 0; background:#fff;">
    <style>
    img{ border:none;}
    </style> 
   <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td>
            <table align="center" border="1" cellpadding="0" cellspacing="0" width="650" style="border-collapse: collapse; border:1px solid #ccc;">
                <tr>
                    <td bgcolor="#fff;">
                       <div class="happy_con">
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
            <td style="width:450px; "   bgcolor="#ffffff">
                 <a href="http://www.kaixinbao.com" target="_blank";><img src="${serverpath}images/edm_logo_01.jpg" alt="开心保" width="284" height="71"  /></a>
            </td>
            <td  bgcolor="#ffffff">
               <a href="http://www.kaixinbao.com" target="_blank"; style="font-size:12px; color:#929292; text-decoration:none">开心保首页</a> <a href="http://www.kaixinbao.com/about/" target="_blank" style="font-size:12px; color:#929292; text-decoration:none">帮助中心</a> <a href="http://www.kaixinbao.com/other/about/" target="_blank" style="font-size:12px; color:#929292; text-decoration:none">关于开心保</a>
            </td>
        </tr>
        </table>
        </div>
                    </td>
                </tr>
                <tr>
                    <td  bgcolor="#ffffff">
<p  bgcolor="#EEEEEE" style="  line-height: 1.8em;  padding-top：10px; padding-bottom:10px; display:block;  font-size: 12px;  background: #EEEEEE;   margin: 0px;  text-align: center;"> <b style="font-size: 14px;   display:block; height:22px; padding-top:10px;">亲爱的:${username}</b>
感谢您参加开心保保险测试考试，您的成绩如下：<br>
<a href="${searchpath}" target="_blank">${searchpath}</a> </p>
					<#if ('${password}'!='0')!>
					<p style="padding:3px 135px; font-size:14px; color:#7d7d7d; text-align:left; line-height:24px;">
		        		您已经是我们的会员，您可以使用以下账号登陆<br>
						账号：${username}<br>
						密码： ${password}（此密码为随机生成，建议您立即更换密码）<br>
					</p>
					</#if>
					<#if ('${warn_one}'!='')!>
	                    <table  cellpadding="0" cellspacing="0"  style="width:580px; margin:16px auto 10px; border:1px solid #E7E7E7; ">
	                        <tr>
	                            <td style=" border-bottom:1px solid #E7E7E7;   padding-left:10px;"><span style="display:block; float:left; line-height:34px;">${title_one}</span><img src="${mail_start_one}"    alt=""  style="vertical-align:middle; padding-left:10px; display:block; float:left;" /></td>
	                            <td style="border-bottom:1px solid #E7E7E7; text-align:right; color:#FF1C1C;">${warn_one}</td>
	                        </tr>
	                        <tr>
	                            <td colspan="2"><p style="padding:8px; background:#FFFFE3; color:#EDB800; font-size:12px; margin:0px; line-height:1.6em;">${content_one}</p>
	                            	<table width="532px" style="margin:0px auto; " border="0">
	                                  <tr>
	                                  ${htmlOne}
	                                  </tr>
	                                </table>
	                            <a href="${product_list_one}" target="_blank" style="text-align:center; color:#828282; display:block; height:45px; line-height:45px; border-top:1px dashed #ccc; width:90%; margin:0 auto; text-decoration:none;">查看更多</a>
	                            </td>
	                        </tr>
	                    </table>
					</#if>
					<#if ('${warn_two}'!='')!>
						<table  cellpadding="0" cellspacing="0"  style="width:580px; margin:16px auto 10px; border:1px solid #E7E7E7; ">
	                        <tr>
	                            <td style=" border-bottom:1px solid #E7E7E7;  padding-left:10px;"><span style="display:block; float:left; line-height:34px;">${title_two}</span><img src="${mail_start_two}"    alt=""  style="vertical-align:middle; padding-left:10px; display:block; float:left;" /></td>
	                            <td style="border-bottom:1px solid #E7E7E7; text-align:right; color:#FF1C1C;">${warn_two}</td>
	                        </tr>
	                        <tr>
	                            <td colspan="2"><p style="padding:8px; background:#FFFFE3; color:#EDB800; font-size:12px; margin:0px; line-height:1.6em;">${content_two}</p>
	                            
	                            	<table width="532px" style="margin:0px auto; " border="0">
	                                  <tr>
	                                    ${htmlTwo}
	                                  </tr>
	                                </table>
	
	                            <a href="${product_list_two}" target="_blank" style="text-align:center; color:#828282; display:block; height:45px; line-height:45px; border-top:1px dashed #ccc; width:90%; margin:0 auto; text-decoration:none;">查看更多</a>
	                            </td>
	                           
	                        </tr>
	                    </table>
                    </#if>
                    <#if ('${warn_three}'!='')!>
						<table  cellpadding="0" cellspacing="0"  style="width:580px; margin:16px auto 10px; border:1px solid #E7E7E7; ">
	                        <tr>
	                            <td style=" border-bottom:1px solid #E7E7E7;  padding-left:10px;"><span style="display:block; float:left; line-height:34px;">${title_three}</span><img src="${mail_start_three}"    alt=""  style="vertical-align:middle; padding-left:10px; display:block; float:left;" /></td>
	                            <td style="border-bottom:1px solid #E7E7E7; text-align:right; color:#FF1C1C;">${warn_three}</td>
	                        </tr>
	                        <tr>
	                            <td colspan="2"><p style="padding:8px; background:#FFFFE3; color:#EDB800; font-size:12px; margin:0px; line-height:1.6em;">${content_three}</p>
	                            
	                            	<table width="532px" style="margin:0px auto; " border="0">
	                                  <tr>
	                                   ${htmlThree}
	                                  </tr>
	                                </table>
	
	                            <a href="${product_list_three}" target="_blank" style="text-align:center; color:#828282; display:block; height:45px; line-height:45px; border-top:1px dashed #ccc; width:90%; margin:0 auto; text-decoration:none;">查看更多</a>
	                            </td>
	                           
	                        </tr>
	                    </table>
                    </#if>
                    <#if ('${warn_four}'!='')!>
						<table  cellpadding="0" cellspacing="0"  style="width:580px; margin:16px auto 10px; border:1px solid #E7E7E7; ">
	                        <tr>
	                            <td style=" border-bottom:1px solid #E7E7E7;  padding-left:10px;"><span style="display:block; float:left; line-height:34px;">${title_four}</span><img src="${mail_start_four}"    alt=""  style="vertical-align:middle; padding-left:10px; display:block; float:left;" /></td>
	                            <td style="border-bottom:1px solid #E7E7E7; text-align:right; color:#FF1C1C;">${warn_four}</td>
	                        </tr>
	                        <tr>
	                            <td colspan="2"><p style="padding:8px; background:#FFFFE3; color:#EDB800; font-size:12px; margin:0px; line-height:1.6em;">${content_four}</p>
	                            
	                            	<table width="532px" style="margin:0px auto; " border="0">
	                                  <tr>
	                                   ${htmlFour}
	                                  </tr>
	                                </table>
	
	                            <a href="${product_list_four}" target="_blank" style="text-align:center; color:#828282; display:block; height:45px; line-height:45px; border-top:1px dashed #ccc; width:90%; margin:0 auto; text-decoration:none;">查看更多</a>
	                            </td>
	                           
	                        </tr>
	                    </table>
	                </#if>
	                <#if ('${warn_five}'!='')!>
						<table  cellpadding="0" cellspacing="0"  style="width:580px; margin:16px auto 10px; border:1px solid #E7E7E7; ">
	                        <tr>
	                            <td style=" border-bottom:1px solid #E7E7E7;  padding-left:10px;"><span style="display:block; float:left; line-height:34px;">${title_five}</span><img src="${mail_start_five}"    alt=""  style="vertical-align:middle; padding-left:10px; display:block; float:left;" /></td>
	                            <td style="border-bottom:1px solid #E7E7E7; text-align:right; color:#FF1C1C;">${warn_five}</td>
	                        </tr>
	                        <tr>
	                            <td colspan="2"><p style="padding:8px; background:#FFFFE3; color:#EDB800; font-size:12px; margin:0px; line-height:1.6em;">${content_five}</p>
	                            
	                            	<table width="532px" style="margin:0px auto; " border="0">
	                                  <tr>
	                                   ${htmlFive}
	                                  </tr>
	                                </table>
	
	                            <a href="${product_list_five}" target="_blank" style="text-align:center; color:#828282; display:block; height:45px; line-height:45px; border-top:1px dashed #ccc; width:90%; margin:0 auto; text-decoration:none;">查看更多</a>
	                            </td>
	                           
	                        </tr>
	                    </table>
                    </#if>
                    <#if ('${htmlSix}'!='')!>
						<table  cellpadding="0" cellspacing="0"  style="width:580px; margin:16px auto 10px; border:1px solid #E7E7E7; ">
	                        <tr>
	                            <td style=" border-bottom:1px solid #E7E7E7;  padding-left:10px;"><span style="display:block; float:left; line-height:34px;">车&nbsp;&nbsp;&nbsp;&nbsp;险</span></td>
	                            <td style="border-bottom:1px solid #E7E7E7; text-align:right; color:#FF1C1C;"><font size="2">建议投保，开心保为您提供如下产品!</font></td>
	                        </tr>
	                        <tr>
	                            <td colspan="2"><p style="padding:8px; background:#FFFFE3; color:#EDB800; font-size:12px; margin:0px; line-height:1.6em;"></p>
	                            
	                            	<table width="532px" style="margin:0px auto; " border="0">
	                                  <tr>
	                                    ${htmlSix}
	                                  </tr>
	                                </table>
	
	                            <a href="${product_list_six}" target="_blank" style="text-align:center; color:#828282; display:block; height:45px; line-height:45px; border-top:1px dashed #ccc; width:90%; margin:0 auto; text-decoration:none;">查看更多</a>
	                            </td>
	                           
	                        </tr>
	                    </table>
	              </#if>
                    </td>
                </tr>
                <tr >
                    <td bgcolor="#6b6b6b" style="color:#fff; padding:8px 0; font-size:12px; text-align:center;    line-height: 160%;">
                       客服电话：4009-789-789   客服邮箱：kf@kaixinbao.com   开心保官网：www.kaixinbao.com<br>
如有任何疑问或问题，欢迎随时联系我们 <a href="http://e.weibo.com/kaixinbao2012
" target="_blank"><img src="${serverpath}images/sina_18.gif" width="56" height="20" style=" vertical-align:middle"  alt="新浪微博"/></a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>