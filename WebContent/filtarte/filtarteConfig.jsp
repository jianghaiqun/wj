<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>


<%@page import="com.sinosoft.cms.api.SearchAPI"%><html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>筛选条件配置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />

<style type="text/css">
.jn-qbbg{position:relative;float:left;overflow:hidden;padding:4px 10px;width:100px;border:1px solid #DFDFDF;
	background:none repeat scroll 0 0 #FAFAFA}
.CsearchConditions .jn-qbbg li{  margin-bottom: 0;}
.jn-sj {height: 11px;}
.jn-k { float: left; margin: 0 0 0 175px;overflow: hidden; width: 163px;}
.jn-sj{position:relative;z-index:2;margin:0 0 -1px 20px; height:11px; *height:auto;overflow:hidden;}
.up_admin_jn {margin-left: -34px;}
.bznx-z{overflow:hidden;display:block;}
.CsearchConditions .jn-qbbg li{float:left;margin-bottom:4px; width:100px; overflow:hidden;}
.CsearchConditions ul li.li_selected span{ background:#F98504; border:1px solid #F98504; color:#fff;}
.CsearchConditions{color:#666; width:700px;margin-left:20px;margin-top:10px}
.CsearchConditions .CConditionName{font-weight:bolder;color:#666;float:left;width:80px;display:inline-block;vertical-align:top;margin-top:10px;width:80px}
.CsearchConditions ul{width:600px;_width:597px;  line-height:30px;list-style:none;padding:4px 0 0 10px;display:inline-block}
.CsearchConditions ul li{display:block;float:left}
.CsearchConditions ul li span{line-height:22px;padding:2px 8px;margin:5px 8px 0 8px;cursor:pointer;display:inline-block;white-space:nowrap;border:1px solid #fff}
.CsearchConditions ul li span:hover{border:1px solid #F90}
.select_nav li.jiange{width:80px}
.select_nav li{overflow:hidden;padding:4px 0px;width:130px; _width:129px;font-size:12px}
.select_nav{display:inline-block;margin-bottom:4px;padding:2px 0 0 10px;width:600px;   list-style:none outside none;line-height:30px}
.admin_shai_list{height: 321px;position: absolute;right:200px;top:18px;width: 200px;}
.rdwh{width:20px;height:20px};
</style>
<script src="../Framework/Main.js"></script>
</head>
<body onload="queryConditions('A','')">

	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr> 
					<td valign="middle" class="blockTd" style="padding: 10px; 30px 4px;"><img
						src="../Icons/icon022a1.gif" width="20" height="20" />筛选条件配置
					</td>
				</tr>
				<tr>
					<td style="padding: 0 30px 4px;">
						<input type="radio" class="rdwh" onclick="queryConditions('A','')" name="riskType" value="A" checked="checked"> 旅游险 
						<input type="radio" class="rdwh" onclick="queryConditions('B','')" name="riskType" value="B"> 意外险 
						<input type="radio" class="rdwh" onclick="queryConditions('D','')" name="riskType" value="D"> 健康险
						<input type="radio" class="rdwh" onclick="queryConditions('E','')" name="riskType" value="E"> 人寿险 
						<input type="radio" class="rdwh" onclick="queryConditions('C','')" name="riskType" value="C"> 家财险</td>
				</tr>
				<tr>
					<td style="padding: 0 30px 4px;">
						<br>
						 名称：&nbsp;<input name="cName" type="text" id="cName" verify="名称|NotNull&&cName"> &nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="Submitbutton"  id="Submitbutton" value="加入" onClick="AddElement('button');">
						<input type="button" name="Submitbutton"  id="Submitbutton" value="生成" onClick="generate()">
						<input type="button" name="resetButton"  id="resetButton" value="重置筛选条件" onclick="resetConditions()">
					</td>
					
				</tr>
				<tr>
					<td>
						<div id="tHtml"></div>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		
		<tr>
			<td style="padding: 2px 10px;"></td>
		</tr>
	
	</table>
		<div id="lableDiv" class="admin_shai_list">
							<label>生成列表(双击取消)</label><a href="###" onclick="removeButtons()">清空生成列表</a>
							<label>----------------------------------------------</label>
		</div>
</body>
<script type="text/javascript">

	//生成.
	function generate(){
		
		 var buttonObjs = document.getElementById("lableDiv").getElementsByTagName("input");
		 var SearchIDs = "";
		 
		 if(buttonObjs.length <= 0){
				alert("列表为空!");
				return;
   		 }
   		 
		 for(var i=0;i<buttonObjs.length;i++){
	           	 var sId = buttonObjs[i].name;
	           	SearchIDs += sId+"@";
	     }
		
		Dialog.confirm("确认要生成？",function(){
			var dc = new DataCollection();
			dc.add("SearchIDs",SearchIDs);
			
			Server.sendRequest("com.sinosoft.product.FiltarteConfig.generate",dc,function(response){
				var taskID = response.get("TaskID");
				var p = new Progress(taskID, "正在生成...");
				p.show();
				removeButtons();
				resetConditions();
				$(cName).value="";
				
			});
		});
		
	}

	//查询筛选条件.
	function queryConditions(riskType,selectValue){
		var dc = new DataCollection();
		dc.add("riskType",riskType);
		dc.add("selectValue",selectValue);
		Server.sendRequest("com.sinosoft.product.FiltarteConfig.queryConditions",dc,function(response){
			$("tHtml").innerHTML = response.get("tHtml");
		});
		return;
	}

	var elementCount = 0;
	//移除标签按钮.
	function removeButton(id){
		var rButton = document.getElementById(id);
		rButton.parentNode.removeChild(rButton);
		var rbr = id.replace("input","br");
		var rbrObj = document.getElementById(rbr);
		rbrObj.parentNode.removeChild(rbrObj);
		$("cName").value="";
	}
	
	 //动态增加表单元素.
    function AddElement(mytype){
        
    	var inputs= document.getElementById("lableDiv").getElementsByTagName("input");
        if(inputs.length >= 15){
				alert("数量限制15个!");
				return;
        }

        //校验.
        if(Verify.hasError()){
			return;
		}

        var cRadio;
		var chkObjs = document.getElementsByName("riskType");
        for(var i=0;i<chkObjs.length;i++){
            if(chkObjs[i].checked){
           	 cRadio = chkObjs[i].value;
                break;
            }
        }

        var parame_group = document.getElementById("p_group").value;

		var parameArr = parame_group.split(',');

		var pList = new Array();
		
		for(var i = 0; i < parameArr.length; i++){
			var tempParame="";
			var first = 0;
			var rPort = document.getElementsByName(parameArr[i]);
			for(var j=0;j<rPort.length;j++){
				if(rPort[j].checked){
						tempParame+=rPort[j].value+"-";
				}
			}
			tempParame = tempParame.substr(0,tempParame.length - 1);
			pList.push(tempParame);
		}

		//名称
        var cName = document.getElementById("cName").value;
        //得到需要被添加的html元素.
        var TemO=document.getElementById("lableDiv");
        //创建一个指定名称（名称指定了html的类型）html元素.
        var newInput = document.createElement("input");
        
        elementCount = elementCount + 1;
        
        //指定input的类型.
        newInput.type=mytype;
          
        //动态生成id.
        newInput.id="input"+elementCount;
        newInput.name=cName+"#"+pList+"#"+cRadio+"00";
        newInput.value=cName;
        newInput.setAttribute("style", "margin-bottom: 10px;width: 186px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;");
        newInput.setAttribute("ondblclick", "removeButton('input"+elementCount+"')");
        newInput.setAttribute("onclick", "unSelect(this)");
        TemO.appendChild(newInput);
             
        var newline= document.createElement("br");
          
        newline.id = "br"+(elementCount);
          
        TemO.appendChild(newline);
        
        document.getElementById("cName").value="";
    } 

	//条件反选-全部和复选框(例：特色保障).
	function clearconditions(name,type){
		if(type == "0"){
			
			var chkObjs = document.getElementsByName(name);
			for ( var i = 0; i < chkObjs.length; i++) {
				if (chkObjs[i].checked) {
					chkObjs[i].checked = false;
				}
			}
			var selectChk = document.getElementById(name+"_all");
			selectChk.checked = true;
			
		}else if(type == "1"){
			//反选全部
			var chkObjs = document.getElementsByName(name);

			var selectCount = 0;
			for ( var i = 0; i < chkObjs.length; i++) {
				if (chkObjs[i].checked) {
					selectCount++;
				}
			}
			
			if(selectCount <= 0){
				var radio = document.getElementById(name+"_all");
				radio.checked = true;
				return;
			}
			
			var radio = document.getElementById(name+"_all");
			radio.checked = false;
		}
	}

	//反选筛选条件.
	function unSelect(input){
		var parameArr = input.name.split('#');
		$(cName).value=parameArr[0];
		var radios = $N("riskType");
		var newStr = parameArr[1].replaceAll("-",",");
		
		for(var i = 0; i < radios.length; i++){
	        if(radios[i].value == parameArr[2].substr(0,1)){
	            radios[i].checked = true;
	            queryConditions(radios[i].value,newStr);
	            break;
	        }
		}
	}

	//通过value值 选中元素(单选、复选)
	function unSelectElement(value){
		 var parame_group = document.getElementById("p_group").value;
		 
			var parameArr = parame_group.split(',');

			var pList = new Array();
			
			for(var i = 0; i < parameArr.length; i++){
				var rPort = document.getElementsByName(parameArr[i]);
				for(var j=0;j<rPort.length;j++){
					if(rPort[j].value == value){
						rPort[j].checked = true;
					}else{
						rPort[j].checked = false;
					}
				}
			
			}
	}

	//替换全部
	String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
	    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
	        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
	    } else {  
	        return this.replace(reallyDo, replaceWith);  
	    }  
	}
	
	//移除生成列表按钮.
	function removeButtons(){
		var buttonObjs= document.getElementById("lableDiv").getElementsByTagName("input");
		var brObjs= document.getElementById("lableDiv").getElementsByTagName("br");
		 var size = buttonObjs.length;
		 for(var i = 0;i < size; i++){
             buttonObjs[0].parentNode.removeChild(buttonObjs[0]);
             brObjs[0].parentNode.removeChild(brObjs[0]);
  		 }
	}

	//重置筛选条件.
	function resetConditions(){
		 var cRadio;
			var chkObjs = document.getElementsByName("riskType");
	        for(var i=0;i<chkObjs.length;i++){
	            if(chkObjs[i].checked){
	           	 chkObjs[i].click();
	                break;
	            }
	        }
	}
	
</script>
</html>
