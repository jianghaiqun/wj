var querycount=1;
var allcardate=null;
function $F(fctId){
	return document.getElementById(fctId);
}

function F(objName)
{
    return document.getElementById(objName);
}

function c$(fctId,fctClassName){
	var varTempDivObj=$F(fctId);
	//alert(varTempDivObj);
	if(!varTempDivObj){
		//GetIframe("ifm"+fctId);
		varTempDivObj=document.createElement("div");
		varTempDivObj.id=fctId;
		if(fctClassName && fctClassName!="")varTempDivObj.className=fctClassName;
		document.body.appendChild(varTempDivObj);
	}
	return varTempDivObj;
}

function GetIframe(fctIfmId,fctVisibility,fctTop,fctLeft,fctWidth,fctHeight){
	var frmCity=$F(fctIfmId);
	if(!frmCity){
		frmCity=document.createElement("iframe");
		frmCity.id=fctIfmId;
		frmCity.style.position="absolute";
		frmCity.style.zIndex="1";
		frmCity.style.visibility="hidden";
		document.body.appendChild(frmCity);
	}
	if(fctTop)frmCity.style.top=fctTop+"px";
	if(fctLeft)frmCity.style.left=fctLeft+"px";
	if(fctWidth)frmCity.style.width=fctWidth+"px";
	if(fctHeight)frmCity.style.height=fctHeight+"px";
	if(fctVisibility)frmCity.style.visibility=(document.all?fctVisibility:"hidden	");
	return frmCity;
}

function getPosition(obj){
	var top=0;
	var left=0;
	var width=obj.offsetWidth;
	var height=obj.offsetHeight;
	while(obj.offsetParent){
		top+=obj.offsetTop;
		left+=obj.offsetLeft;
		obj=obj.offsetParent;
	}
	return{"top":top,"left":left,"width":width,"height":height};
}


function GetValueToInputObj(fctThisObj){
	if(!fctThisObj)return null;
	var varThisObjAutoInput=(fctThisObj.getAttributeNode("value_to_input")?fctThisObj.getAttributeNode("value_to_input").value:"");
	if(varThisObjAutoInput=="")return null;
	return $F(varThisObjAutoInput);
}


function AutoNextInputAct(fctThisObj,fctAct){
	var varNextInput=fctThisObj.getAttributeNode("nextinput");
	if(varNextInput && varNextInput!=""){
		if(document.all){
			eval("$F('"+varNextInput.value+"')."+fctAct+"()");
		}else{
			var evt = document.createEvent("MouseEvents");
			evt.initEvent(fctAct,true,true);
			$F(varNextInput.value).dispatchEvent(evt);
		}
		$F(varNextInput.value).focus();
	}
}

function AddFunToObj(fctObj,fctAct,fctFunction){
	if(fctObj.addEventListener){
		fctObj.addEventListener(fctAct.replace("on",""),function(e){
			e.cancelBubble=!eval(fctFunction);
		},false);
	}else if(fctObj.attachEvent){
		fctObj.attachEvent(fctAct,function(){
			return eval(fctFunction);
		});
	}
}
document.write('\
	<style type="text/css">\
		#divAddressMenu {line-height:20px;text-align:left;position:absolute;visibility:hidden;z-index:10000;;overflow:hidden;width:172px;background:#fff;border:solid #1bd9c3 1px;border-top:gray solid 1px;font-size:12px;}\
		#divAddressMenu h4{border-bottom:dotted #CCCCCC 1px;color:#999999;font-size:12px; font-weight:100; padding:2px 2px 0 2px; margin:0;}\
		#divAddressMenu div{padding:1px;}\
		#divAddressMenu a {display:block;width:162px !important;width:100%;padding:1px 2px 2px 2px;cursor:default;text-decoration:none;color:#008e91;background-color:none;}\
		#divAddressMenu a span{float:right;font-family:vernada }\
		#divAddressMenu a:hover {border:solid #1bd9c3 1px;background-color:#ecfafa;}\
	    .sel {border:solid 1px #1bd9c3;background-color:#ccf }\
	</style>\
');
var PageId=0,PageNum;

function GetCityList(fctThisObj){
	var varMenuObj=c$("divAddressMenu");
	var varThisObj=fctThisObj;
	if(varThisObj.id=="menuPageS"||varThisObj.id=="menuPageE"){
		varThisObj=varMenuObj.obj;
	}else{
		PageId=0;
	}
	var varAddressList = allcardate;

    if(document.getElementById("CityList") != null && document.getElementById("CityList").value != "")
        varAddressList=document.getElementById("CityList").value; 
	var varThisObjAutoInput=GetValueToInputObj(varThisObj); 
	if(varThisObjAutoInput)varThisObjAutoInput.value="";
	
	var varObjValue=varThisObj.value;
	var varThisObjAdd=(varThisObj.getAttributeNode("mod_address_suggest")?varThisObj.getAttributeNode("mod_address_suggest").value:"");
	var varData=(varObjValue==""?(varThisObjAdd==""?varAddressList:varThisObjAdd):varAddressList);
	var varHtmlStr="",varCityDataSplit=varData.split("@"),varCityDataSplitI,varCityDataSplitIu,varNextPageStr="";
	
	varMenuObj.obj=varThisObj;
	var varPageRCount=(varThisObj.getAttributeNode("pagecount")?parseInt(varThisObj.getAttributeNode("pagecount").value,10):8);
	
	var varThisPageI=0
	for(var i=1;i<varCityDataSplit.length-1;i++){
		varCityDataSplitI=varCityDataSplit[i];
		
		varCityDataSplitISplit=varCityDataSplitI.split("|");
		
		if(varCityDataSplitI.toUpperCase().indexOf(varObjValue.toUpperCase())==0|| varCityDataSplitISplit[2].toUpperCase().indexOf(varObjValue.toUpperCase())==0|| varCityDataSplitISplit[1].toUpperCase().indexOf(varObjValue.toUpperCase())>=0 || varObjValue=="" || i==varObjValue){ // || varCityDataSplitI.toLowerCase().indexOf(varObjValue.toLowerCase())>=0
		    
			varThisPageI+=1;
			if(varThisPageI>PageId*varPageRCount && varThisPageI<=(PageId+1)*varPageRCount){
				//varCityDataSplitISplit=varCityDataSplitI.split("|");
				varHtmlStr+="<a href='javascript:;' onclick='GetCity("+varThisPageI+")' id='menuA"+varThisPageI+"' title='"+varCityDataSplitI+"'><span>"+varCityDataSplitISplit[0]+"&nbsp;&nbsp;</span>"+varCityDataSplitISplit[1]+"</a>";//("+varCityDataSplitISplit[2]+")
			}			
		}
	}
	
	PageNum=parseInt(varThisPageI/varPageRCount)+1;
	
	if(varThisPageI>varPageRCount){
		varNextPageStr="&nbsp;<span id=menuPageS style="+(PageId>0?"cursor:pointer;":"color:#ccc;")+"font-family:Verdana>&lt;&lt;&nbsp;上一页</span><span style='margin:0 8px 0 8px'>"+(PageId+1)+"/"+PageNum+"</span>"
		varNextPageStr+="<span id=menuPageE style="+(varThisPageI>(PageId+1)*varPageRCount?"cursor:pointer;":"color:#ccc;")+"font-family:Verdana>下一页&nbsp;&gt;&gt;</span>";
	}
	var varThisObjPosition=getPosition(varThisObj);
	with(varMenuObj){
		style.top=varThisObjPosition.top+varThisObjPosition.height+"px";
		style.left=varThisObjPosition.left+"px";
		style.visibility="visible";
		innerHTML="<div><h4 style='font-family:vernada'>输入中文/拼音或→←↑↓选择</h4>"+(varHtmlStr==""?"<nobr>‘"+varObjValue+"’没有相应城市信息。</nobr>":varHtmlStr+varNextPageStr)+"</div>";
	}
	
	return false;
}

function GetCity(fctI){
	var varMenuObj=c$("divAddressMenu");
	var varThisObj=varMenuObj.obj;
	var varMenuValue=$F("menuA"+fctI).title;
	varMenuValue=varMenuValue.split("|"); 
	varThisObj.value=varMenuValue[1];
	carNo =varMenuValue[4];
	//var hidObj = $F('hotelcity');
	//hidObj.value=varMenuValue[3]; hidObj.select();
	if($("#carNb").attr('disabled')==false){
		$F('carNb').value=varMenuValue[4];
	}	
	//$F("hfCityLevel").value = varMenuValue[4];
	//try{ CityChanged(e); } catch(e){ CityChanged(e); }      //外部接口
}

function _Hidden(e){
	e=e?e:event;
	var varMenuObj=c$("divAddressMenu");
	var varThisObj=varMenuObj.obj;
	if(varMenuObj.style.visibility!="hidden"){
		if(e){
			var EventOBJ=(e.srcElement?e.srcElement:e.target);
			if(EventOBJ.id=="menuPageS" && EventOBJ.style.color==""){
				PageId=PageId-1;
				GetCityList(EventOBJ);$F('city').focus(); index = -1;
			}
			if(EventOBJ.id=="menuPageE" && EventOBJ.style.color==""){
				PageId=PageId+1;
				GetCityList(EventOBJ);$F('city').focus(); index = -1;
			}
			if(varThisObj==EventOBJ || EventOBJ.id.indexOf("menuPage")==0 || EventOBJ.id.indexOf("divAddressMenu")==0) return false;
		}
		
		var varThisObjAutoInput=GetValueToInputObj(varThisObj);
		if($F("menuA1")){
			if(!varThisObjAutoInput)varThisObjAutoInput=varThisObj;
			if(varThisObjAutoInput.value=="" || varThisObjAutoInput==varThisObj){
				
			}
		}else if(EventOBJ.id.indexOf("menuA")<0){
			if(varThisObj)varThisObj.value="";
		}
		varMenuObj.style.visibility="hidden";
		//GetIframe("ifm"+varMenuObj.id,"hidden");
	}
}

function HiddenDateBox(){
	if($F("divDateBox")){
		if($F("divDateBox").style.visibility!="hidden" && $F("divDateBox").bodyclick=="1"){
			$F("divDateBox").style.visibility="hidden";
			$F("divDateBox").bodyclick="";
			GetIframe("ifmdivDateBox","hidden");
		}else{
			$F("divDateBox").bodyclick="1";
		}
	}
}
AddFunToObj(window,"onload","AddFunToObj(document,'onclick','_Hidden("+(document.all?"":"e")+");HiddenDateBox();');");

/*键盘选择的处理*/
var index = -1; var lstCity;
function selCity(e){    
    var event = e||window.event;    
    var keyCode = event.charCode||event.keyCode;     
    if(keyCode!=13&&(keyCode<37||keyCode>40)) { GetCityList($F('city')); stopBubble(e); return;}
    lstCity = $F("divAddressMenu"); 

    var lks = lstCity.getElementsByTagName('a');   //选中的城市在列表中序号   
    if(keyCode==13) { if(index<0) index=0; lks[index].onclick(); 
        if( lstCity.style.visibility!="hidden") stopBubble(e);     //选择城市
        lstCity.style.visibility="hidden"; //GetIframe("ifm"+c$("divAddressMenu").id,"hidden"); 
    }
    else if(keyCode==38) {        //向上 ||keyCode==37
        index--; if(index<0) index = lks.length-1; 
   } 
    else if(keyCode==40) {     //向下keyCode==39||
        index++; if(index>=lks.length) index = 0 ;
    }
    else if(keyCode==37){
        PageId--; if(PageId<0) PageId = PageNum-1; GetCityList($F('menuPageS')); index = -1; return;
    }  
    else if(keyCode==39){
        PageId++; if(PageId>=PageNum) PageId = 0; GetCityList($F('menuPageS')); index = -1; return;
    }   
    setCityListStyle(index,lks); 
}

function setCityListStyle(index,lks){
    for(var i=0;i<lks.length;i++) {lks[i].className = '';lks[i].style.color = '#008e91'; }
    lks[index].className = 'sel'; lks[index].style.color = 'red';
}

//输入关键字onblur事件默认选择第一个
function DefaultselCity(){  
    
    var hidObj = $F('hotelcity');   
    lstCity = $F("divAddressMenu");     
	if(hidObj.value=="" && index!=0 && PageNum>1)  return;  //不输入关键字则不执行默认选择
	
    var lks = lstCity.getElementsByTagName('a');   //选中的城市在列表中序号   
   
	if(lks.length==0)  return;     //输入没有匹配的关键字则返回
	
    if(index<0) index=0; lks[index].onclick(); 
    if( lstCity.style.visibility!="hidden") stopBubble(e);     //选择城市
    lstCity.style.visibility="hidden"; //GetIframe("ifm"+c$("divAddressMenu").id,"hidden"); 
    
    setCityListStyle(index,lks); 
}


function stopBubble(e)  //取消事件上冒
{
    if(!document.all)/* 非IE */
    {
        e.stopPropagation();/* 标准W3C的取消冒泡 */
        e.preventDefault(); /* 取消默认行为 */
    }
    else
    {
        event.cancelBubble = true;   /* IE的取消冒泡方式 */
        event.returnValue = false; /* IE的取消默认行为，如<a>的转向地址，类似于return false */
    }
}
function getcardate(){
	var url = sinosoft.base+'/car/transition!queryCarcode.action';
	if(querycount==1){
		querycount++;
		jQuery.post(url, '', function(data) {
			allcardate =data.carcitystr;
		} , 'json');
	}
}