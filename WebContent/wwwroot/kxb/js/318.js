function ZCMSAD(PositionID) {
  this.ID        = PositionID;
  this.PosID  = 0; 
  this.ADID		  = 0;
  this.ADType	  = "";
  this.ADName	  = "";
  this.ADContent = "";
  this.PaddingLeft = 0;
  this.PaddingTop  = 0;
  this.Width = 0;
  this.Height = 0;
  this.IsHitCount = "N";
  this.UploadFilePath = "";
  this.URL = "";
  this.SiteID = 0;
  this.ShowAD  = showADContent;
}

function adClick(ADID,ADURL) {
	var sp = document.createElement("SCRIPT");
	sp.src = this.URL+"?SiteID="+this.SiteID+"&ADID="+ADID+"&URL="+ADURL;
	document.body.appendChild(sp);
}

function showADContent() {
  var content = this.ADContent;
  var str = "<div id='ZCMSAD_"+this.PosID+"' style='width:"+this.Width+"px; height:"+this.Height+"px;'>";
  var AD = eval('('+content+')');
  if (this.ADType == "image") {
	  str += "<a href='"+AD.Images[0].imgADLinkUrl+"'  onClick='adClick(\""+this.ADID+"\",\""+AD.Images[0].imgADLinkUrl+"\")' target='"+((AD.imgADLinkTarget == "Old") ? "_self" : "_blank") + "'>";
	  str += "<img title='"+AD.Images[0].imgADAlt+"' src='"+this.UploadFilePath+AD.Images[0].ImgPath+"' width='"+this.Width+"' height='"+this.Height+"' style='border:0px;'>";
	  str += "</a>";
  }else if(this.ADType == "flash"){
	  str += "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' width='"+this.Width+"' height='"+this.Height+"' id='FlashAD_"+this.ADID+"'>";
	  str += "<param name='movie' value='"+this.UploadFilePath+AD.Flashes[0].SwfFilePath+"' />"; 
      str += "<param name='quality' value='high' />";
      str += "<param name='wmode' value='transparent'/>";
      str += "<param name='swfversion' value='8.0.35.0' />";
	  str += "<embed wmode='transparent' src='"+this.UploadFilePath+AD.Flashes[0].SwfFilePath+"' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='"+this.Width+"' height='"+this.Height+"'></embed>";
      str += "</object>";	  
  }
  str += "</div>";
  document.write(str);
}
 
var cmsAD_318 = new ZCMSAD('cmsAD_318'); 
cmsAD_318.PosID = 318; 
cmsAD_318.ADID = 17; 
cmsAD_318.ADType = "image"; 
cmsAD_318.ADName = "&#24320;&#24515;&#20445;&#39318;&#39029;_&#21491;&#19979;"; 
cmsAD_318.ADContent = "{'Images':[{'imgADLinkUrl':'http://www.baidu.com','imgADAlt':'&#24320;&#24515;&#20445;&#39318;&#39029;_&#21491;&#19979;','ImgPath':'upload/Image/tpbf/2500024063.jpg'}],'imgADLinkTarget':'New','Count':'1','showAlt':'Y'}"; 
cmsAD_318.URL = "http://localhost:8080/wj/Services/ADClick.jsp"; 
cmsAD_318.SiteID = 221; 
cmsAD_318.Width = 244; 
cmsAD_318.Height = 300; 
cmsAD_318.UploadFilePath = "http://localhost:8080/"; 
cmsAD_318.ShowAD();
