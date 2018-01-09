function pclose1(){
		document.getElementById("poccont1").style.display="none";
		document.getElementById("pocbtn1").innerHTML='<a onclick="popen1()" href="#">展开</a>'
}
function popen1(){
		document.getElementById("poccont1").style.display="";
		document.getElementById("pocbtn1").innerHTML='<a onclick="pclose1()" href="#">收起</a>'
}
function pclose2(){
		document.getElementById("poccont2").style.display="none";
		document.getElementById("pocbtn2").innerHTML='<a onclick="popen2()" href="#">展开</a>'
}
function popen2(){
		document.getElementById("poccont2").style.display="";
		document.getElementById("pocbtn2").innerHTML='<a onclick="pclose2()" href="#">收起</a>'
}
function pclose3(){
		document.getElementById("poccont3").style.display="none";
		document.getElementById("pocbtn3").innerHTML='<a onclick="popen3()" href="#">展开</a>'
}
function popen3(){
		document.getElementById("poccont3").style.display="";
		document.getElementById("pocbtn3").innerHTML='<a onclick="pclose3()" href="#">收起</a>'
}
//会员中心 投被保人 展开收缩
function contoc(){
		document.getElementById("cont1").style.display="";
        document.getElementById("contbtn").innerHTML='<a onclick="contoc2()" href="#" onfocus="this.blur()"><img src="../images/arrow2down.gif" /></a>'
		
}
function contoc2(){
		document.getElementById("cont1").style.display="none";
        document.getElementById("contbtn").innerHTML='<a onclick="contoc()" href="#" onfocus="this.blur()"><img src="../images/arrow2up.gif" /></a>'
}

function contocb(){
		document.getElementById("cont2").style.display="";
        document.getElementById("contbtn2").innerHTML='<a onclick="contocb2()" href="#" onfocus="this.blur()"><img src="../images/arrow2down.gif" /></a>'
		
}
function contocb2(){
		document.getElementById("cont2").style.display="none";
        document.getElementById("contbtn2").innerHTML='<a onclick="contocb()" href="#" onfocus="this.blur()"><img src="../images/arrow2up.gif" /></a>'
}

//结算 展开收缩
function settlcontclose1(){
		document.getElementById("settlcontwrap1").style.display="none";
		document.getElementById("hidbtn1").innerHTML='<a onclick="settlcontopen1()" href="#" onfocus="this.blur()"></a>'
		
}
function settlcontopen1(){
		document.getElementById("settlcontwrap1").style.display="";
		document.getElementById("hidbtn1").innerHTML='<a onclick="settlcontclose1()" href="#" onfocus="this.blur()"></a>'
		
}
function settlcontclose2(){
		document.getElementById("settlcontwrap2").style.display="none";
		document.getElementById("hidbtn2").innerHTML='<a onclick="settlcontopen2()" href="#" onfocus="this.blur()"></a>'
		
}
function settlcontopen2(){
		document.getElementById("settlcontwrap2").style.display="";
		document.getElementById("hidbtn2").innerHTML='<a onclick="settlcontclose2()" href="#" onfocus="this.blur()"></a>'
		
}
function settlcontclose3(){
		document.getElementById("settlcontwrap3").style.display="none";
		document.getElementById("hidbtn3").innerHTML='<a onclick="settlcontopen3()" href="#" onfocus="this.blur()"></a>'
		
}
function settlcontopen3(){
		document.getElementById("settlcontwrap3").style.display="";
		document.getElementById("hidbtn3").innerHTML='<a onclick="settlcontclose3()" href="#" onfocus="this.blur()"></a>'
		
}