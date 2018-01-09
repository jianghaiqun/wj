<%@ taglib uri="controls" prefix="z"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单凭证下载</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
    body{
	    background-image:none;
    }
    .cmt{        
	    overflow:hidden;
    }
</style>
</head>

<body>
<%
String path = request.getParameter("OrderPath");
String filename = request.getParameter("filename");
%>
<form action="./FileDownLoad.jsp?filepath=<%=path%>&filename=<%=filename%>" method=post name=fm target="fraSubmit"> 
    <div class="cmt">
        <div class="bd bgwhite">
            <div class="tbg">
            	<br>
                <p align ="center" ><button  onclick="fm.submit();">点击下载</button></p>
            </div>
        </div>
    </div>
</form>
</body>
</html>
