<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>首页</title>
<link href="style/css/main.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript> 
 //图片滚动展示 Start
 var counts = 3;
 //大图
 img1 = new Image();
 img1.src = 'images/indexpic01.jpg';
 img2 = new Image();
 img2.src = 'images/indexpic02.jpg';
 img3 = new Image();
 img3.src = 'images/indexpic03.jpg';
 
 var smallImg = new Array();
 //小图
 smallImg[0] = 'images/index_adb1.gif';
 smallImg[1] = 'images/index_adb2.gif';
 smallImg[2] = 'images/index_adb3.gif';
 
 //链接地址
 url1 = new Image();
 url1.src = '#';
 url2 = new Image();
 url2.src = '#';
 url3 = new Image();
 url3.src = '#';
 //alt值
 alt1 = new Image();
 alt1.alt = '泛华安享人生保障卡';
 alt2 = new Image();
 alt2.alt = '境外旅游保险';
 alt3 = new Image();
 alt3.alt = '泛华-生命 安心保障卡';
 //
 var nn = 1;
 var key = 0;
 function change_img() {
  if (key == 0) {
   key = 1;
  } else if (document.all) {
   document.getElementById("pic").filters[0].Apply();
   document.getElementById("pic").filters[0].Play(duration = 2);
  }
  eval('document.getElementById("pic").src=img' + nn + '.src');
  eval('document.getElementById("url").href=url' + nn + '.src');
  eval('document.getElementById("pic").alt=alt' + nn + '.alt');
  if (nn == 1) {
   document.getElementById("url").target = "_blank";
   document.getElementById("url").style.cursor = "pointer";
  } else {
   document.getElementById("url").target = "_blank"
   document.getElementById("url").style.cursor = "pointer"
  }
// 
  for ( var i = 1; i <= counts; i++) {
   document.getElementById("xxjdjj" + i).className = 'axx';
  }
  document.getElementById("xxjdjj" + nn).className = 'bxx';
  nn++;
  if (nn > counts) {
   nn = 1;
  }
  tt = setTimeout('change_img()', 4000);
 }
 function changeimg(n) {
  nn = n;
  window.clearInterval(tt);
  change_img();
 }
 function ImageShow() {
  document.write('<div class="picshow_main">');
  document.write('<div><a id="url"><img id="pic" class="imgbig" /></a></div>');
  document.write('<div class="picshow_change">');
  for ( var i = 0; i < counts; i++) {
   document.write('<a href="javascript:changeimg(' + (i + 1)
     + ');" id="xxjdjj' + (i + 1)
     + '" class="axx" target="_self"><img src="' + smallImg[i]
     + '"></a>');
  }
  document.write('</div></div>');
  change_img();
 }
 //图片滚动展示 End
</SCRIPT>
<SCRIPT language="javascript" type="text/javascript"> 
 ImageShow();
</SCRIPT>
</head>
<body>
</body>
</html>