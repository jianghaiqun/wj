/**
 * <p>程序名称: Common.js</p>
 * <p>程序功能: 公用函数变量定义 </p>
 * <p>注释更新人: 胡博</p>
 * <p>最近更新日期: 2002-10-2</p>
 * <p>注意：所有的变量类型为VAR，在JAVA中表示为STRING<p>
 */

/** 日期分隔符,初始值=":" */
var DATEVALUEDELIMITER=":";
/** 域名与域值的分隔符,初始值=":" */
var NAMEVALUEDELIMITER=":";
/** 初始值=":" */
var SBCCASECOLON="：";
/** 域之间的分隔符,初始值="|" */
var FIELDDELIMITER="|";
/** 初始值="｜" */
var SBCCASEVERTICAL="｜";
/** 记录之间的分隔符,初始值="^" */
var RECORDDELIMITER="^";
/** 每一页最大显示的行数,初始值="10" */
var MAXSCREENLINES=10;
/** 内存中存储的最大的页数,初始值="20" */
var MAXMEMORYPAGES=20;
/** 修改(颜色),初始值="FFFF00" */
var BGCOLORU="FFFF00";
/** 添加(颜色),初始值="#00F0F0" */
var BGCOLORI="#00F0F0";
/** 删除(颜色),初始值="#778899" */
var BGCOLORD="#778899";
/** 快捷菜单最大项数 */
var MAXMENUSHORTNUM = 3;

var DELAY_MILLS = 2000;
var WINDOWSSIZE = "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px";
/**
 * 在String对象上添加trim方法
 */
String.prototype.trim = function()
{
	//利用正则表达式去除头尾的空格
	return this.replace(/(^\s*)|(\s*$)/g,"");
};

Number.prototype.toFixed = function(len){
	var str = this + "";
	var start = str.indexOf(".");
	if(start == -1){//如果没有小数
		return Math.floor(this);
	}
	var decimal = str.split('.')[1];
	if(decimal && decimal.length <= len){//小数位数不足
		return Number(this);
	}
	// 需要四舍五入
	var tempStr = str.slice(0, start + len + 1);// 取子串到需要保留的位数
	var temp = Number(tempStr.replace(".",""));
	if(str.substr(start + len + 1, 1) >= 5){
		if(temp > 0)
		{
			temp = temp + 1;
		}
		else
		{
			temp = temp - 1;
		}
	}
	tempStr = temp + "";
	tempStr = tempStr.slice(0, tempStr.length - len) + "." + tempStr.slice(-len);
	return Number(tempStr);
};

/**
 * 将输入框里一串数字转换成日期格式
 * 如果输入一串8为的数字，则将其转换成日期格式
 * Example: function changeNumToDate(dateElement)
 * @param dateElement 存放图片的对象或框架或页面
 * @return 日期格式，这里不校验日期正确性
 */
function changeNumToDate(dateElement){
	var strDate = dateElement.value;
	var patrn=/^[0-9]{8}$/;
	if (patrn.exec(strDate)){
		dateElement.value = strDate.slice(0,4) + "-" + strDate.slice(4,6) + "-" + strDate.slice(6,8);
	}
}

/**
 * 给时间控件添加onblur方法，
 * onblur调用的方法给一串字符转换成时间格式 如：2009-02-13
 */
function initElementAddOnblur(){
	var tFormCount = document.forms.length;
	for(var fm=0;fm<tFormCount;fm++){
		var theElements=document.forms[fm].elements;
		for(var i=0;i<theElements.length;i++){
			if(theElements[i].className && theElements[i].className == "coolDatePicker"){
				var onblurName = theElements[i].attributes["onblur"];// 获取所以的onblur方法
				if(!onblurName || !onblurName.nodeValue || onblurName.nodeValue.indexOf("changeNumToDate") == -1){// 避免方法changeNumToDate重复绑定
					theElements[i].attachEvent("onblur",function(){changeNumToDate(event.srcElement);});
				} else {
					
				}
			}
		}
	}
}

/**
* 比较两个查询日期间隔是否需要限制,如果是生产环境并且间隔超出限制天数返回false
* @param startDate 日期字符串,格式必须为“YYYY-MM-DD”
* @param endDate   日期字符串,格式必须为“YYYY-MM-DD”
* @param strFileName  报表名
* 每增加一个报表限制需要在fdcoderela表中增加一条相应的记录配置限制天数例如：
* insert into fdcoderela (RELATYPE, CODE1, CODE2, CODE3, DESCRIPTION, OTHERSIGN) 
* 	values ('queryFileName', '报表名', '天数', '0', '报表中文名', '');
*/
function checkDateByDBType(startDate,endDate,strFileName){
	var tSql1 = "select Sysvarvalue from FDSysVar where Sysvar = 'checkDBType'";
	var tDBTypeResult = easyExecSql(tSql1, 1, 0, 1);
	if(tDBTypeResult == "1"){// 生产库，查询库中此记录不能同步
		var interval = dateDiff(startDate,endDate,'D');//日期间隔
		var tSql2 = "select code2 from fdcoderela a where a.relatype='queryFileName' and a.code1='"+strFileName+"'";
		var tDays = easyExecSql(tSql2, 1, 0, 1);//查找设定天数
		if(tDays != null && tDays != ""){
			if(parseInt(tDays) < interval + 1){
				alert("该时间范围数据量过大，请将时间范围控制在" + tDays + "天内！");
				return false;
			}
		}
	}
	return true;  
}



/**
 * 更换图片
 * Example: function changeImage(image,gif)
 * @param image 存放图片的对象或框架或页面
 * @param gif 图片的全路径
 */
function changeImage(image,gif)
{
	image.src=gif;
}
/**
* 替换字符串函数
* <p><b>Example: </b><p>
* <p>replace("Minim123Minim", "123", "Minim") returns "MinimMinimMinim"<p>
* @param strExpression 字符串表达式
* @param strFind 被替换的子字符串
* @param strReplaceWith 替换的目标字符串，即用strReplaceWith字符串替换掉strFind
* @return 返回替换后的字符串表达式
*/
function replace( strMain, strFind, strReplaceWith){
	var strReturn  = "";
    var intStartIndex = 0,
        intEndIndex   = 0;

    if(strMain==null || strMain == "" || typeof strMain!="string")
      return "";

    while((intEndIndex=strMain.indexOf(strFind,intStartIndex))>-1)
    {
      strReturn = strReturn + strMain.substring(intStartIndex,intEndIndex) + strReplaceWith;
      intStartIndex = intEndIndex + strFind.length;
    }
    strReturn = strReturn + strMain.substring(intStartIndex,strMain.length);
    return strReturn;
}
/**
 * 去掉字符串头尾空格
 * Example: trim(" Minim ") returns "Minim"<p>
 * @param strValue 字符串表达式
 * @return 头尾无空格的字符串表达式
 */
function trim(s)
{
	//使用正则表达式进行全局替换
	return s.replace(/(^\s*)|(\s*$)/g,"");
}

/**
 * 对输入域是否是正整数的校验
 * Example: isInteger("Minim") returns false;isInteger("123") returns true
 * @param strValue 输入数值表达式或字符串表达式
 * @return 布尔值（true--是整数, false--不是整数）
 */
function isInteger(strValue)
{
	//使用正则表达式进行判断
	var chkExp=/^\d+$/;
	return (chkExp.test(strValue));
}

/**
 * 对输入域是否是正数的校验
 * Example: isNumeric("Minim") returns false;isNumeric("123.1") returns true
 * @param strValue 输入数值表达式或字符串表达式
 * @return 布尔值（true--是数字, false--不是数字）
 */
function isNumeric(strValue)
{
	//使用正则表达式进行判断
	strValue = strValue.trim();
	if(strValue.substring(0, 1) == "-")
    {
      strValue = strValue.substring(1);
    }
	var chkExp=/^\d+(\.\d+)?$/;
	return (chkExp.test(strValue));
}

/**
 * 离开域时的数字校验
 * Example: checkNumber(HTML.Form.Object.Name)
 * @param Field HTML页面的对象名称
 * @return true或产生一个“errorMessage("请输入合法的数字")”
 */
function checkNumber(Field)
{
	var strValue=Field.value;
	if(trim(strValue)!="" && !isNumeric(strValue))
	{
		errorMessage("请输入合法的数字");
		Field.focus();
		Field.select();
		return false;
	}
	return true;
}
/**
 * 判断字符是否在s中
 */
function isCharsInBag(s, bag)
{
	var i;
	for(i = 0; i < s.length; i++)
	{
		var c = s.charAt(i);
		if(bag.indexOf(c) == -1) return false;
	}
	return true;
}
/**
 * 日期的合法判断
 * Example: isLegalDate("2002", "10", "03") returns true;isLegalDate("Minim", "10", "03") returns false
 * @param y 年份字符串
 * @param m 月份字符串
 * @param d 日期字符串
 * @return 布尔值（true--合法日期, false--非法日期）
 */
function isLegalDate(y,m,d)
{
	if(isNaN(parseInt(y,10)) || isNaN(parseInt(m,10)) || isNaN(parseInt(d,10)))
	{
		return false;
	}
	var dt = new Date(parseInt(y,10),parseInt(m,10)-1,parseInt(d,10));
	if(dt.getFullYear()==parseInt(y,10) && dt.getMonth()==parseInt(m,10)-1 && dt.getDate()==parseInt(d,10))
	{
		return true;
	}
	else
	{
		return false;
	}
}
/**
 * 对输入域是否是日期的校验
 * Example: isDate("2002-10-03") returns true;isDate("2002/10/03") returns false
 * @param date 日期字符串,格式必须为“yyyy-mm-dd”
 * @return 布尔值（true--合法日期, false--非法日期）
 */
function isDate(date)
{
  var r = date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);     
  if(r==null)    return   false;     
  var  d=  new  Date(r[1], r[3]-1, r[4]);     
  return  (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);  
}
/**
 * 比较两个日期字符串
 * Example: compareDate("2002-10-03", "2002-10-03") returns 0;compareDate("2002-10-03", "2001-10-03") returns 1
 * @param date1 日期字符串,格式必须为“yyyy-mm-dd”
 * @param date2 日期字符串,格式必须为“yyyy-mm-dd”
 * @return date1=date2则返回0 , date1>date2则返回1 , date1<date2则返回2
 */
function compareDate(date1,date2)
{
	var strValue=date1.split("-");
	var date1Temp=new Date(strValue[0],strValue[1]-1,strValue[2]);
	strValue=date2.split("-");
	var date2Temp=new Date(strValue[0],strValue[1]-1,strValue[2]); //lanjun 2007/2/1 js中的月为0~11
	if(date1Temp.getTime()==date2Temp.getTime())
	{
		return 0;
	}
	else if(date1Temp.getTime()>date2Temp.getTime())
		{
			return 1;
		}
		else
		{
			return 2;
		}
}
/**
 * 对span的显示、隐藏
 * Example: showPage(HTML.ImageObject, HTML.SpanObject.ID)
 * @param img 显示图片的HTML对象
 * @param spanID HTML中SPAN对象的ID
 * @return 如果页面SPAN可见，则将其隐藏，并显示表示关闭的图片；反之
 */
function showPage(img,spanID)
{
	if(spanID.style.display=="")
	{
		//关闭
		spanID.style.display="none";
		img.src="../common/images/butCollapse.gif";
	}
	else
	{
		//打开
		spanID.style.display="";
		img.src="../common/images/butExpand.gif";
	}
}
/**
 * 对span的显示、隐藏
 * Example: showPage(HTML.ImageObject, HTML.SpanObject.ID)
 * @param td 显示td的HTML对象
 * @param spanID HTML中SPAN对象的ID
 * @return 如果页面SPAN可见，则将其隐藏，并显示表示关闭的图片；反之
 * 主要是使文字和图片都可以触发显示和隐藏功能
 */
function showPage2(td,spanID)
{
	if(spanID.style.display=="")
	{
		//关闭
		spanID.style.display="none";
		//下面只支持td下有一个img，可升级
		td.all[0].src="../common/images/butCollapse.gif";
	}
	else
	{
		//打开
		spanID.style.display="";
		//下面只支持td下有一个img，可升级
		td.all[0].src="../common/images/butExpand.gif";
	}
}
/**
 * 打开一个窗口
 * Example: openWindow("www.163.com", null)
 * @param strURL 新窗口的完整路径（URL）或相对路径
 * @param strName 指定窗口名，可以为空
 * @return 返回新建窗口的句柄
 */
function openWindow(strURL,strName)
{
	var strWidth  = screen.width-screen.width*0.2;
	var strHeight = screen.height-screen.height*0.3;
	var strTop = screen.height*0.1;
	var strLeft = screen.width*0.1;
	var newWindow = window.open(strURL,strName,'width='+strWidth+',height='+strHeight+',top='+strTop+',left='+strLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	newWindow.focus();
	return newWindow;
}
/**
 * 分割代码并放在select域里（基本上已经被取缔）
 * Example: setOption("name", "1=Minim&2=Hzm");将在下拉框中看到可选项Minim和Hzm
 * @param selectName HTML的select对象名
 * @param strValue 包含select对象显示内容的字符串，串的格式必须为: value1=text1&value2=text2，以“&"号分隔
 */
function setOption(selectName,strValue)
{
	var arrayField=strValue.split("&");
	var i=0;
	fm.all(selectName).length = 0;
	while(i<arrayField.length)
	{
		var option=document.createElement("option");
		var arrayTemp=arrayField[i].split("=");
		var strFieldName=arrayTemp[0];
		var strFieldValue=unescape(arrayTemp[1]);
		option.value=strFieldName;
		option.text=strFieldValue;
		fm.all(selectName).add(option);
		i++;
	}
}
/**
 * 获取日期对象
 * @param strDate 日期字符串
 * @param splitOp 分割符
 * @return 返回日期对象
 */
function getDate(strDate, splitOp) {
	if(splitOp == null) splitOp = "-";
	var arrDate = strDate.split(splitOp);
	if(arrDate[1].length == 1) arrDate[1] = "0" + arrDate[1];
	if(arrDate[2].length == 1) arrDate[2] = "0" + arrDate[2];
	return new Date(arrDate[0], arrDate[1]-1, arrDate[2]); //lanjun 2007/2/1 js中的月为0~11
}

/**
 * 计算两个日期的差,返回差的月数(M)或天数(D)(其中天数除2.29这一天)
 * Example: dateDiff("2002-10-1", "2002-10-3", "D") returns "2";dateDiff("2002-1-1", "2002-10-3", "M") returns "9"
 * @param dateStart 减日期
 * @param dateEnd 被减日期
 * @param MD 标记，“M”为要求返回差的月数；“D”为要求返回差的天数
 * @return 返回两个日期差的月数(M)或天数(D)
 */
function dateDiff(dateStart,dateEnd,MD)
{
	if(dateStart==""||dateEnd=="")
	{
		return false;
	}
	if(typeof(dateStart) == "string")
	{
		dateStart = getDate(dateStart);
	}
	if(typeof(dateEnd) == "string")
	{
		dateEnd = getDate(dateEnd);
	}
	var i;
	if(MD=="D")
	{
		//按天计算差
		var endD = dateEnd.getDate();
		var endM = dateEnd.getMonth();
		var endY = dateEnd.getFullYear();
		var startD = dateStart.getDate();
		var startM = dateStart.getMonth();
		var startY = dateStart.getFullYear();
		var startT=new Date(startY,startM,startD);
		var endT=new Date(endY,endM,endD);
		var diffDay=(endT.valueOf()-startT.valueOf())/86400000;
		return diffDay;
	}
	else
	{
		//按月计算差
		var endD = dateEnd.getDate();
		var endM = dateEnd.getMonth();
		var endY = dateEnd.getFullYear();
		var startD = dateStart.getDate();
		var startM = dateStart.getMonth();
		var startY = dateStart.getFullYear();
		if(endD>=startD)
		{
			return(endY-startY)*12 +(endM-startM) + 1;
		}
		else
		{
			return(endY-startY)*12 +(endM-startM);
		}
	}
}

/**
 * 对小数点后第三位四舍五入
 * Example: mathRound(123.456) returns 123.46
 * @param intValue 整型数值
 * @return 对小数点后第三位四舍五入后的整型数值
*/
function mathRound(x)
{
	var v = Math.round(x * 100) ;
	v = v/100;
	return v;
}

/**
 * 对数字按0.00格式化
 * Example: pointTwo(123.456) returns 123.45;pointTwo(123) returns 123.00
 * @param intValue 整型数值
 * @return 按0.00格式化后的整型数值
 */
function pointTwo(s)
{
	var v = s.toString();
	var len = v.length;
	var index = v.indexOf(".");
	if(index==-1)
	{
		v = v + ".00";
		return v;
	}
	else
	{
		if(len-index==3)
		{
			return v;
		}
		else if(len-index==2)
			{
				v = v +"0";
				return v;
			}
			else if(len-index==1)
				{
					v = v + "00";
					return v;
				}
				else
				{
					return v.substring(0,index+3);
				}
	}
}


/**
 * 对数字按0.0000格式化
 * Example: pointFour(123.456789) returns 123.4567;pointFour(123) returns 123.0000
 * @param intValue 整型数值
 * @return 按0.0000格式化后的整型数值
 */
function pointFour(s)
{
	var v = Math.round(parseFloat(s) * 10000)/10000;
	v = v.toString();
	var len = v.length;
	var index = v.indexOf(".");
	if(index==-1)
	{
		v = v + ".0000";
		return v;
	}
	else
	{
		if(len-index==5)
		{
			return v;
		}
		else if(len-index==4)
			{
				v = v +"0";
				return v;
			}
			else if(len-index==3)
				{
					v = v + "00";
					return v;
				}
				else if(len-index==2)
					{
						v = v + "000";
						return v;
					}
					else if(len-index==1)
						{
							v = v + "0000";
							return v;
						}
						else
						{
							return v.substring(0,index+5);
						}
	}
}

/**
 * 在浏览器中弹出一个alert框显示错误信息
 * @param strErrMsg 要显示的错误信息字符串
 */
function errorMessage(strErrMsg)
{
	alert(strErrMsg);
}

/**
 * 对输入域是否是日期的校验(日期格式xxxx/xx/xx)，建议修改，与isDate函数合并
 * Example: isDateI("2004/10/4") returns true;isDateI("2004-10-4") returns false
 * @param date 格式必须为“YYYY/MM/DD”的日期字符串
 * @return 布尔值（true--合法日期, false--非法日期）
 */
function isDateI(date)
{
	var strValue;
	strValue=date.split("/");
	if(strValue.length!=3) return false;
	if(!isInteger(strValue[0]) || !isInteger(strValue[1]) || !isInteger(strValue[2])) return false;
	var intYear=eval(strValue[0]);
	var intMonth=eval(strValue[1]);
	var intDay=eval(strValue[2]);
	if(intYear<0 || intYear>9999 || intMonth<0 || intMonth>12 || intDay<0 || intDay>31) return false;
	return true;
}

/**
 * 对输入域是否是日期的校验(日期格式xxxxxxxx)，建议修改，与isDate函数合并
 * Example: isDateI("2004104") returns true
 * <p>Other returns false<p>
 */
function isDateN(date)
{
	var strValue;
	strValue=new Array();
	strValue[0]=date.substring(0, 4);
	strValue[1]=date.substring(4, 6);
	strValue[2]=date.substring(6, 8);
	if(strValue.length!=3)
	{
		return false;
	}
	if(!isInteger(strValue[0]) || !isInteger(strValue[1]) || !isInteger(strValue[2]))
	{
		return false;
	}
	var intYear=eval(strValue[0]);
	var intMonth=eval(strValue[1]);
	var intDay=eval(strValue[2]);
	if(!isLegalDate(intYear,intMonth,intDay))
	{
		return false;
	}
	return true;
}

/**
 * 得到当前的系统时间：
 * splitOp 为分割符，Example：
 * splitOp='-' 则日期格式为 年-月-日
 * splitOp='/' 则日期格式为 年/月/日
 * splitOp如果为空，则默认是：'-'
 */
function getCurrentDate(splitOp)
{
	var CurrentDate = "";
	var strURL = "../common/jsp/CurrentDate.jsp";
	var strPara = "CurFlag=CurDate";
	Request = new ActiveXObject("Microsoft.XMLHTTP");
	Request.open("POST",strURL, false);
	Request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	Request.send(strPara);
	try
	{
		CurrentDate = Request.responseText;
	}
	catch(e1)
	{
		alert("取服务器时间超时，请重试！");
		return "";
	}
  if(CurrentDate==null || CurrentDate=='')
  {
    alert("取服务器时间超时，请重试！");
    return "";
  }
  if(splitOp==null || trim(splitOp)=='')
  {
    return CurrentDate;
  }
  CurrentDate = CurrentDate.replace("-",splitOp);
  CurrentDate = CurrentDate.replace("-",splitOp);
  return CurrentDate;
}

/**
 * 得到当前的系统时间：
 * splitOp 为分割符，Example：
 * splitOp=':' 则日期格式为 时：分：秒
 * splitOp='/' 则日期格式为 时/分/秒
 * splitOp如果为空，则默认是：'：'
 */
function getCurrentTime(splitOp)
{
	var CurrentTime = "";
	var strURL = "../common/jsp/CurrentDate.jsp";
	var strPara = "CurFlag=CurTime";
	Request = new ActiveXObject("Microsoft.XMLHTTP");
	Request.open("POST",strURL, false);
	Request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	Request.send(strPara);
	try
	{
		CurrentTime = Request.responseText;
	}
	catch(e1)
	{
		alert("取服务器时间超时，请重试！");
		return "";
	}
  if(CurrentTime==null || CurrentTime=='')
  {
    alert("取服务器时间超时，请重试！");
    return "";
  }
  if(splitOp==null || trim(splitOp)=='')
  {
    return CurrentTime;
  }
  CurrentTime = CurrentTime.replace(":",splitOp);
  CurrentTime = CurrentTime.replace(":",splitOp);
  return CurrentTime;
}

/**
 * 获取字符串的部分子串，该函数得到c_Str中的第c_i个以c_Split分割的字符串
 * Example: getStr("Minim|Hzm|Yt|", "2", "|") returns "Hzm"
 * @param c_Str 有分隔规则的字符串
 * @param c_i 取第几个分隔子串
 * @param c_Split 分隔符
 * @return 返回第c_i个分隔子串
 */
function getStr(c_Str , c_i ,c_Split)
{
	var t_Str1, t_Str2 , t_strOld;
	var i, i_Start, j_End;
	t_Str1 = c_Str;
	t_Str2 = c_Split;
	i = 0;
	try
	{
		while(i < c_i)
		{
			i_Start = t_Str1.indexOf(t_Str2,0);
			if(i_Start >= 0)
			{
				i = i + 1;
				t_strOld = t_Str1;
				t_Str1 = t_Str1.substring(i_Start+t_Str2.length,t_Str1.length);
			}
			else
			{
				if(i != c_i - 1)
				{
					t_Str1="";
				}
				break;
			}
		}
		if(i_Start >= 0)
		{
			t_Str1=t_strOld.substring(0,i_Start);
		}
	}
	catch(ex)
	{
		t_Str1="";
	}
	return t_Str1;
}

/**
 * 判断号码类型，如个人保单号，集体保单号（有在使用，但是此方法应该淘汰，或者进行改进）
 * 在“项目规范_约定”的“新旧号码对照.xls”中规定“号码的[12,13]位”为代码类型标志;
 * Example: getCodeType("abcdefghijk11asdfasdf") returns "11"
 * @param strCode 含代码字符串
 * @return 号码字符串
 */
function getCodeType(strCode)
{
	if((strCode == null) ||(strCode == ""))
	{
		return "00";
	}
	else
	{
		//在“项目规范_约定”的“新旧号码对照.xls”中规定“号码的[12,13]位”为代码类型标志
		return strCode.substring(11, 13);
	}
}

/**
 * 判断输入号码中包含的类型号码和指定类型号码是否一致（有在使用，但是此方法应该淘汰，或者进行改进）
 * 在“项目规范_约定”的“新旧号码对照.xls”中规定“号码的[12,13]位”为代码类型标志;
 * Example: judgeCodeType("abcdefghijk11asdfasdf", "11") returns ture
 * @param strCode 含代码字符串
 * @param strType 类型号码，参照“新旧号码对照.xls”
 * @return 布尔值（true--一致, false--不一致）
 */
function judgeCodeType(strCode, strType)
{
	if((strCode == null) ||(strCode == "") ||(strType == null) ||(strType == ""))
	{
		return false;
	}
	else
	{
		return(getCodeType(strCode).compareTo(strType) == 0);
	}
}

/**
 * 清空界面上的所有输入栏
 * Example: EmptyFormElements()
 */
function emptyFormElements()
{
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	//遍历所有FORM
	for(formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//遍历FORM中的所有ELEMENT
		for(elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			if(window.document.forms[formsNum].elements[elementsNum].type == "text")
			{
				window.document.forms[formsNum].elements[elementsNum].value = "";
			}
		}
	}
}

/**
 * 将界面上的所有输入栏中为"undefined"清空
 * Example: EmptyFormElements()
 */
function emptyUndefined()
{
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	//遍历所有FORM
	for(formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//遍历FORM中的所有ELEMENT
		for(elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			if((window.document.forms[formsNum].elements[elementsNum].value == "undefined" || window.document.forms[formsNum].elements[elementsNum].value == "null") && (window.document.forms[formsNum].elements[elementsNum].type == "text" || window.document.forms[formsNum].elements[elementsNum].type == "textarea"))
			{
				window.document.forms[formsNum].elements[elementsNum].value = "";
			}
		}
	}
}

/**
 * 使用一维数组中存放的索引来过滤二维数组
 * Example: chooseArray({{1，2}，{3，4}}, {0}) returns {{1}，{3}}
 * @param dataArray 存放数据的二维数组
 * @param filterArray 存放有索引的一维数组
 * @return 按一维数组中的索引过滤过的二维数组
 */
function chooseArray(dataArray, filterArray)
{
	var arrResult = new Array();
	var recordNum, filterNum;
	try
	{
		for(recordNum=0; recordNum<dataArray.length; recordNum++)
		{
			arrResult[recordNum] = new Array();
			for(filterNum=0; filterNum<filterArray.length; filterNum++)
			{
				arrResult[recordNum].push(dataArray[recordNum][filterArray[filterNum]]);
			}
		}
	}
	catch(ex)
	{
		alert("chooseArray处理出现错误！");
	}
	return arrResult;
}

/**
 * 把js文件中的字符转换成特殊字符
 */
function Conversion(strIn)
{
	var strOut;
	strOut=replace(strIn,"@@Enter","\r\n");
	strIn=strOut;
	strOut=replace(strIn,"@@DouQuot","\"");
	strIn=strOut;
	strOut=replace(strIn,"@@SinQuot","\'");
	return strOut;
}

/**
 * 根据代码选择的代码查找并显示名称
 */
function showCodeName()
{
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var strEvent = "";	//保存onDoubleClick事件代码
	var urlStr = "";
	var sFeatures = "";
	var strCode = "";	//代码选择
	var strQueryResult = "";	//代码选择的查询结果集
	var arrCode = null;	//拆分数组
	var strCodeValue = "";	//代码值
	var cacheFlag = false;	//内存中有数据标志
	var strCodeSelect = "";
	//寻找主窗口
	var win = searchMainWindow(this);
	if(win == false)
	{
		win = this;
	}
	//遍历所有FORM
	var tForCount = window.document.forms.length;
	for(formsNum=0; formsNum<tForCount; formsNum++)
	{
		//遍历FORM中的所有ELEMENT
		var tEleCount = window.document.forms[formsNum].elements.length;
		for(elementsNum=0; elementsNum<tEleCount; elementsNum++)
		{
			//寻找代码选择元素
			if(window.document.forms[formsNum].elements[elementsNum].className == "codeno")
			{
				//取出代码值
				strCodeValue = window.document.forms[formsNum].elements[elementsNum].value;
				//空值则不处理
				if(strCodeValue == "") continue;
				//虚拟数据源处理
				if(window.document.forms[formsNum].elements[elementsNum].CodeData != null)
				{
					strQueryResult = window.document.forms[formsNum].elements[elementsNum].CodeData;
				}
				else
				{
					//从后台取数据
					//取出CODESELECT代码
					strEvent = window.document.forms[formsNum].elements[elementsNum].ondblclick;
					strCode = new String(strEvent);
					strCode = strCode.substring(strCode.indexOf("showCodeList") + 14);
					strCode = strCode.substring(0, strCode.indexOf("'"));
					//如果内存中有数据，从内存中取数据
					if(win.parent.VD.gVCode.getVar(strCode))
					{
						arrCode = win.parent.VD.gVCode.getVar(strCode);
						cacheFlag = true;
					}
					else
					{
						if(strCode=="AgentCode"||strCode=="OccupationCode9")
						{
							//由于销售人员数据和职业类别数据的数据量较大，无条件遍历查询时会严重影响汉化显示速度
							//特对他们的汉化查询进行了单独处理（有条件单条查询，结果不会再缓存）
							urlStr = "../common/jsp/CodeQueryXML.jsp?codeType=" + strCode+"&codeField="+strCode+"&codeConditon="+strCodeValue;
						}
						else
						{
							urlStr = "../common/jsp/CodeQueryXML.jsp?codeType=" + strCode;
						}
						Request = new ActiveXObject("Microsoft.XMLHTTP");
						Request.open("GET", urlStr, false);
						Request.send(null);
						try
						{
							strQueryResult = Request.responseText.trim();
						}
						catch(e1)
						{
							alert("代码汉化报错："+e1.message);
						}
					}
				}
				//拆分成数组
				try
				{
					if(!cacheFlag)
					{
						try
						{
							arrCode = decodeEasyQueryResult(strQueryResult,0,1);
						}
						catch(ex)
						{
							alert("页面缺少引用EasyQueryVer3.js");
						}
						strCodeSelect = "";
						var arr2 = new Array();
						var tArrLength = arrCode.length;
						for(i=0; i<tArrLength; i++)
						{
							if(i%100==0)
							{
								arr2.push(strCodeSelect);
								strCodeSelect = "";
							}
							strCodeSelect = strCodeSelect + "<option value=" + arrCode[i][0] + ">" + arrCode[i][0] + "-" + arrCode[i][1] + "</option>";
						}
						arr2.push(strCodeSelect);
						strCodeSelect = "";
						tArrLength = arr2.length;
						for(i=0; i<tArrLength; i++)
						{
							strCodeSelect =  strCodeSelect + arr2[i];
						}
						if(strCode=="AgentCode" ||strCode=="OccupationType9")
						{
							//由于销售人员数据和职业类别数据的数据量较大，无条件遍历查询时会严重影响汉化显示速度
							//特对他们的汉化查询进行了单独处理（有条件单条查询，结果不会再缓存）
						}
						else
						{
							//将拆分好的数据放到内存中
							win.parent.VD.gVCode.addArrVar(strCode, "", arrCode);
							//无论是否有数据从服务器端得到,都设置该变量
							win.parent.VD.gVCode.addVar(strCode+"Select","",strCodeSelect);
						}
					}
					cacheFlag = false;
					tArrLength = arrCode.length;
					for(i=0; i<tArrLength; i++)
					{
						if(strCodeValue == arrCode[i][0])
						{
							window.document.forms[formsNum].elements[elementsNum].value = arrCode[i][0];
							window.document.forms[formsNum].elements[elementsNum+1].value = arrCode[i][1];
							break;
						}
					}
				}
				catch(ex)
				{}
			}
			//显示title
			if(window.document.forms[formsNum].elements[elementsNum].type == "text")
			{
				window.document.forms[formsNum].elements[elementsNum].title = window.document.forms[formsNum].elements[elementsNum].value;
			}
		}
	}
}

/**
 * 根据代码选择的代码查找并显示名称，显示指定的一个
 * strCode - 代码选择的代码
 * showObjCode - 代码存放的界面对象
 * showObjName - 要显示名称的界面对象
 */
function showOneCodeName(strCode, showObjCode, showObjName)
{
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var urlStr = "";
	var sFeatures = "";
	var strQueryResult = "";	//代码选择的查询结果集
	var arrCode = null;	//拆分数组
	var strCodeValue = "";	//代码值
	var cacheFlag = false;	//内存中有数据标志
	var showObjn;
	var showObjc;
	if(showObjName == null) showObjName = strCode;
	//遍历所有FORM
	var tFomCount = window.document.forms.length;
	for(formsNum=0; formsNum<tFomCount; formsNum++)
	{
		//遍历FORM中的所有ELEMENT
		var tEleCount = window.document.forms[formsNum].elements.length;
		for(elementsNum=0; elementsNum<tEleCount; elementsNum++)
		{
			//寻找代码选择元素
			if(window.document.forms[formsNum].elements[elementsNum].name == showObjCode)
			{
				showObjc = window.document.forms[formsNum].elements[elementsNum];
			}
			if(window.document.forms[formsNum].elements[elementsNum].name == showObjName)
			{
				showObjn = window.document.forms[formsNum].elements[elementsNum];
				break;
			}
		}
	}
	//如果代码栏的数据不为空，才查询，否则不做任何操作
	if(showObjc.value != "")
	{
		//寻找主窗口
		var win = searchMainWindow(this);
		if(win == false)
		{
			win = this;
		}
		//如果内容中有数据，从内容中取数据
		if(win.parent.VD.gVCode.getVar(strCode))
		{
			arrCode = win.parent.VD.gVCode.getVar(strCode);
			cacheFlag = true;
		}
		else
		{
			urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode;
			sFeatures = "status:no;help:0;close:0;dialogWidth:150px;dialogHeight:0px;dialogLeft:-1;dialogTop:-1;resizable=1";
			//连接数据库进行CODE查询，返回查询结果给strQueryResult
			strQueryResult = window.showModalDialog(urlStr, "", sFeatures);
		}
		//拆分成数组
		try
		{
			if(!cacheFlag)
			{
				try
				{
					arrCode = decodeEasyQueryResult(strQueryResult);
				}
				catch(ex)
				{
					alert("页面缺少引用EasyQueryVer3.js");
				}
				strCodeSelect = "";
				for(i=0; i<arrCode.length; i++)
				{
					strCodeSelect = strCodeSelect + "<option value=" + arrCode[i][0] + ">";
					strCodeSelect = strCodeSelect + arrCode[i][0] + "-" + arrCode[i][1];
					strCodeSelect = strCodeSelect + "</option>";
				}
				//将拆分好的数据放到内存中
				win.parent.VD.gVCode.addArrVar(strCode, "", arrCode);
				//无论是否有数据从服务器端得到,都设置该变量
				win.parent.VD.gVCode.addVar(strCode+"Select","",strCodeSelect);
			}
			cacheFlag = false;
			for(i=0; i<arrCode.length; i++)
			{
				if(showObjc.value == arrCode[i][0])
				{
					showObjn.value = arrCode[i][1];
				}
			}
		}
		catch(ex)
		{}
	}
}

/**
 * 以年龄和性别校验身份证号的函数
 * 参数，输入的证件号码，出生日期，性别
 * 返回  布尔值
 */
function chkIdNo(iIdNo, iBirthday ,iSex)
{
	var tmpStr="";
	var idDate="";
	var tmpInt=0;
	var strReturn = "";
	iIdNo = trim(iIdNo);
	iBirthday = trim(iBirthday);
	iSex = trim(iSex);
	if((iIdNo.length!=15) &&(iIdNo.length!=18))
	{
		strReturn = "输入的身份证号位数错误";
		return strReturn;
	}
	if(!(isDate(iBirthday)))
	{
		strReturn = "输入的日期格式错误";
		return strReturn;
	}
	//转换日期格式到yy－mm－dd，by Minim
	var arrDate = iBirthday.split("-");
	if(arrDate[1].length == 1) arrDate[1] = "0" + arrDate[1];
	if(arrDate[2].length == 1) arrDate[2] = "0" + arrDate[2];
	iBirthday = arrDate[0] + "-" + arrDate[1] + "-" + arrDate[2];
	if(iSex!="1" && iSex!="2" && iSex!="9")
	{
		strReturn = "输入的性别不明确";
		return strReturn;
	}
	if(iIdNo.length==15)
	{
		tmpStr=iIdNo.substring(6,12);
		tmpStr= "19" + tmpStr;
		tmpStr= tmpStr.substring(0,4) + "-" + tmpStr.substring(4,6) + "-" + tmpStr.substring(6);
		if(iBirthday == tmpStr)
		{
			if(iSex=="1")
			{
				tmpInt = parseInt(iIdNo.substring(14));
				tmpInt = tmpInt % 2;
				if(tmpInt==0)
				{
					strReturn = "输入的性别与身份证号的信息不一致";
					return strReturn;
				}
			}
			else if(iSex=="2")
			{
				tmpInt = parseInt(iIdNo.substring(14));
				tmpInt = tmpInt % 2;
				if(tmpInt!=0)
				{
					strReturn = "输入的性别与身份证号的信息不一致";
					return strReturn;
				}
			}
			else
			{
				strReturn = "证件类型为身份证号码时，请输入确定的性别类型！";
				return strReturn;
			}
		}
		else
		{
			strReturn = "输入的生日与身份证号的信息不一致";
			return strReturn;
		}
		return strReturn;
	}
	if(iIdNo.length==18)
	{
		tmpStr=iIdNo.substring(6,14);
		tmpStr= tmpStr.substring(0,4) + "-" + tmpStr.substring(4,6) + "-" + tmpStr.substring(6);
		if(iBirthday == tmpStr)
		{
			if(iSex=="1")
			{
				tmpInt = parseInt(iIdNo.substring(16,17));
				tmpInt = tmpInt % 2;
				if(tmpInt==0)
				{
					strReturn = "输入的性别与身份证号的信息不一致";
					return strReturn;
				}
			}
			else if(iSex=="2")
			{
				tmpInt = parseInt(iIdNo.substring(16,17));
				tmpInt = tmpInt % 2;
				if(tmpInt!=0)
				{
					strReturn = "输入的性别与身份证号的信息不一致";
					return strReturn;
				}
			}
			else
			{
				strReturn = "证件类型为身份证号码时，请输入确定的性别类型！";
				return strReturn;
			}
		}
		else
		{
			strReturn = "输入的生日与身份证号的信息不一致";
			return strReturn;
		}
		return strReturn;
	}
}

/**
 * 严格校验身份证号码
 * 兰军 2005-7-2 17:05
 * 公民身份号码是特征组合码，
 * 由十七位数字本体码和一位数字校验码组成。
 * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，
 * 三位数字顺序码和一位数字校验码。顺序码的奇数分给男性，偶数分给女性。
 * 校验码是根据前面十七位数字码，按照ISO7064:1983.MOD11-2校验码计算出来的检验码。
 */
function checkIdCard(idCard)
{
	var SystemDate=new Date();
	var year=SystemDate.getFullYear();
	var month=SystemDate.getMonth()+1;
	var day=SystemDate.getDate();
	var yyyy; //年
	var mm; //月
	var dd; //日
	var birthday; //生日
	var sex; //性别
	var id=idCard;
	var id_length=id.length;
	if (id_length==0)
	{
		alert("请输入身份证号码!");
		return false;
	}
	if (id_length!=15 && id_length!=18)
	{
		alert("身份证号长度应为15位或18位！");
		return false;
	}
	if (id_length==15)
	{
		for(var i =0 ;i<id_length;i++)
		{
			if(isNaN(idCard.charAt(i)))
			{
				alert("15位身份证号中不能有字符！");
				return false;
			}
		}
		yyyy="19"+id.substring(6,8);
		mm=id.substring(8,10);
		dd=id.substring(10,12);
		if (mm>12 || mm<=0)
		{
			alert("身份证号月份非法！");
			return false;
		}
		if (dd>31 || dd<=0)
		{
			alert("身份证号日期非法！");
			return false;
		}
		//4,6,9,11月份日期不能超过30
		if((mm==4||mm==6||mm==9||mm==11)&&(dd>30))
		{
			alert("身份证号日期非法！");
			return false;
		}
		//判断2月份
		if(mm==2)
		{
			if(LeapYear(yyyy))
			{
				if(dd>29)
				{
					alert("身份证号日期非法！");
					return false;
				}
			}
			else
			{
				if(dd>28)
				{
					alert("身份证号日期非法！");
					return false;
				}
			}
		}
	}
	else
	{
		for(var i =0 ;i<id_length-1;i++)
		{
			if(isNaN(idCard.charAt(i)))
			{
				alert("身份证号中前17位中不能有字符！");
				return false;
			}
		}
		if(isNaN(idCard.charAt(17))&& idCard.charAt(17) !="X" && idCard.charAt(17) !="x" )
		{
			alert("身份证校验错误，请认真检查！");
			return false;
		}
		if (idCard.indexOf("X") > 0 && idCard.indexOf("X")!=17 || idCard.indexOf("x")>0 && idCard.indexOf("x")!=17)
		{
			alert("身份证中\"X\"输入位置不正确！");
			return false;
		}
		yyyy=id.substring(6,10);
		if (yyyy>year || yyyy<1900)
		{
			alert("身份证号年度非法！");
			return false;
		}
		mm=id.substring(10,12);
		if (mm>12 || mm<=0)
		{
			alert("身份证号月份非法！");
			return false;
		}
		if(yyyy==year&&mm>month)
		{
			alert("身份证号月份非法！");
			return false;
		}
		dd=id.substring(12,14);
		if (dd>31 || dd<=0)
		{
			alert("身份证号日期非法！");
			return false;
		}
		//4,6,9,11月份日期不能超过30
		if((mm==4||mm==6||mm==9||mm==11)&&(dd>30))
		{
			alert("身份证号日期非法！");
			return false;
		}
		//判断2月份
		if(mm==2)
		{
			if(LeapYear(yyyy))
			{
				if(dd>29)
				{
					alert("身份证号日期非法！");
					return false;
				}
			}
			else
			{
				if(dd>28)
				{
					alert("身份证号日期非法！");
					return false;
				}
			}
		}
		if(yyyy==year&&mm==month&&dd>day)
		{
			alert("身份证号日期非法！");
			return false;
		}
		if (id.charAt(17)=="x" || id.charAt(17)=="X")
		{
			if ("x"!=GetVerifyBit(id) && "X"!=GetVerifyBit(id))
			{
				alert("身份证校验错误，请认真检查！");
				return false;
			}
		}
		else
		{
			if (id.charAt(17)!=GetVerifyBit(id))
			{
				alert("身份证校验错误，请认真检查！");
				return false;
			}
		}
	}
	return true;
}

/**
 * 严格校验身份证号码
 * 兰军 2005-7-2 17:05
 * 公民身份号码是特征组合码，
 * 由十七位数字本体码和一位数字校验码组成。
 * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，
 * 三位数字顺序码和一位数字校验码。顺序码的奇数分给男性，偶数分给女性。
 * 校验码是根据前面十七位数字码，按照ISO7064:1983.MOD11-2校验码计算出来的检验码。
 * 返回提示信息而不是直接报错
 */
function checkIdCard2(idCard)
{
	var errMess = "";
	var SystemDate=new Date();
	var year=SystemDate.getFullYear();
	var month=SystemDate.getMonth()+1;
	var day=SystemDate.getDate();
	var yyyy; //年
	var mm; //月
	var dd; //日
	var birthday; //生日
	var sex; //性别
	var id=idCard;
	var id_length=id.length;
	if (id_length==0)
	{
		errMess = "请输入身份证号码!";
		return errMess;
	}
	if (id_length!=15 && id_length!=18)
	{
		errMess = "身份证号长度应为15位或18位！";
		return errMess;
	}
	if (id_length==15)
	{
		for(var i =0 ;i<id_length;i++)
		{
			if(isNaN(idCard.charAt(i)))
			{
				errMess = "15位身份证号中不能有字符！";
				return errMess;
			}
		}
		yyyy="19"+id.substring(6,8);
		mm=id.substring(8,10);
		dd=id.substring(10,12);
		if (mm>12 || mm<=0)
		{
			errMess = "身份证号月份非法！";
			return errMess;
		}
		if (dd>31 || dd<=0)
		{
			errMess = "身份证号日期非法！";
			return errMess;
		}
		//4,6,9,11月份日期不能超过30
		if((mm==4||mm==6||mm==9||mm==11)&&(dd>30))
		{
			errMess = "身份证号日期非法！";
			return errMess;
		}
		//判断2月份
		if(mm==2)
		{
			if(LeapYear(yyyy))
			{
				if(dd>29)
				{
					errMess = "身份证号日期非法！";
					return errMess;
				}
			}
			else
			{
				if(dd>28)
				{
					errMess = "身份证号日期非法！";
					return errMess;
				}
			}
		}
	}
	else
	{
		for(var i =0 ;i<id_length-1;i++)
		{
			if(isNaN(idCard.charAt(i)))
			{
				errMess = "身份证号中前17位中不能有字符！";
				return errMess;
			}
		}
		if(isNaN(idCard.charAt(17))&& idCard.charAt(17) !="X" && idCard.charAt(17) !="x" )
		{
			errMess = "身份证校验错误，请认真检查！";
			return errMess;
		}
		if (idCard.indexOf("X") > 0 && idCard.indexOf("X")!=17 || idCard.indexOf("x")>0 && idCard.indexOf("x")!=17)
		{
			errMess = "身份证中\"X\"输入位置不正确！";
			return errMess;
		}
		yyyy=id.substring(6,10);
		if (yyyy>year || yyyy<1900)
		{
			errMess = "身份证号年度非法！";
			return errMess;
		}
		mm=id.substring(10,12);
		if (mm>12 || mm<=0)
		{
			errMess = "身份证号月份非法！";
			return errMess;
		}
		if(yyyy==year&&mm>month)
		{
			errMess = "身份证号月份非法！";
			return errMess;
		}
		dd=id.substring(12,14);
		if (dd>31 || dd<=0)
		{
			errMess = "身份证号日期非法！";
			return errMess;
		}
		//4,6,9,11月份日期不能超过30
		if((mm==4||mm==6||mm==9||mm==11)&&(dd>30))
		{
			errMess = "身份证号日期非法！";
			return errMess;
		}
		//判断2月份
		if(mm==2)
		{
			if(LeapYear(yyyy))
			{
				if(dd>29)
				{
					errMess = "身份证号日期非法！";
					return errMess;
				}
			}
			else
			{
				if(dd>28)
				{
					errMess = "身份证号日期非法！";
					return errMess;
				}
			}
		}
		if(yyyy==year&&mm==month&&dd>day)
		{
			errMess = "身份证号日期非法！";
			return errMess;
		}
		if (id.charAt(17)=="x" || id.charAt(17)=="X")
		{
			if ("x"!=GetVerifyBit(id) && "X"!=GetVerifyBit(id))
			{
				errMess = "身份证校验错误，请认真检查！";
				return errMess;
			}
		}
		else
		{
			if (id.charAt(17)!=GetVerifyBit(id))
			{
				errMess = "身份证校验错误，请认真检查！";
				return errMess;
			}
		}
	}
	return errMess;
}

/**
 * 计算身份证校验码
 * 兰军 2005-7-2 17:06
 * 原理:
 * ∑(a[i]*W[i]) mod 11 ( i = 2, 3, ..., 18 )(1)
 * "*" 表示乘号
 * i--------表示身份证号码每一位的序号，从右至左，最左侧为18，最右侧为1。
 * a[i]-----表示身份证号码第 i 位上的号码
 * W[i]-----表示第 i 位上的权值 W[i] = 2^(i-1) mod 11
 * 计算公式 (1) 令结果为 R
 * 根据下表找出 R 对应的校验码即为要求身份证号码的校验码C。
 * R 0 1 2 3 4 5 6 7 8 9 10
 * C 1 0 X 9 8 7 6 5 4 3 2
 * X 就是 10，罗马数字中的 10 就是 X
 * 15位转18位中,计算校验位即最后一位
 */
function GetVerifyBit(id)
{
	var result;
	var nNum=eval(id.charAt(0)*7+id.charAt(1)*9+id.charAt(2)*10+id.charAt(3)*5+id.charAt(4)*8+id.charAt(5)*4+id.charAt(6)*2+id.charAt(7)*1+id.charAt(8)*6+id.charAt(9)*3+id.charAt(10)*7+id.charAt(11)*9+id.charAt(12)*10+id.charAt(13)*5+id.charAt(14)*8+id.charAt(15)*4+id.charAt(16)*2);
	nNum=nNum%11;
	switch (nNum)
	{
		case 0 :
			result="1";
			break;
		case 1 :
			result="0";
			break;
		case 2 :
			result="X";
			break;
		case 3 :
			result="9";
			break;
		case 4 :
			result="8";
			break;
		case 5 :
			result="7";
			break;
		case 6 :
			result="6";
			break;
		case 7 :
			result="5";
			break;
		case 8 :
			result="4";
			break;
		case 9 :
			result="3";
			break;
		case 10 :
			result="2";
			break;
	}
	return result;
}

/**
 * 判断是否闰年
 * 参数intYear代表年份的值
 * returntrue:是闰年false:不是闰年
 */
function LeapYear(intYear)
{
	if(intYear%100==0)
	{
		if(intYear%400==0)
		{
			return true;
		}
	}
	else
	{
		if((intYear%4)==0)
		{
			return true;
		}
	}
	return false;
}

/**
 * 通过身份证号的得到出生日期函数
 * 参数，输入的证件号码
 * 返回  出生日期
 */
function getBirthdatByIdNo(iIdNo)
{
	var tmpStr="";
	var idDate="";
	var tmpInt=0;
	var strReturn = "";
	iIdNo = trim(iIdNo);
	if((iIdNo.length!=15) &&(iIdNo.length!=18))
	{
		strReturn = "输入的身份证号位数错误";
		return strReturn;
	}
	if(iIdNo.length==15)
	{
		tmpStr=iIdNo.substring(6,12);
		tmpStr= "19" + tmpStr;
		tmpStr= tmpStr.substring(0,4) + "-" + tmpStr.substring(4,6) + "-" + tmpStr.substring(6);
		return tmpStr;
	}
	else
	{
		tmpStr=iIdNo.substring(6,14);
		tmpStr= tmpStr.substring(0,4) + "-" + tmpStr.substring(4,6) + "-" + tmpStr.substring(6);
		return tmpStr;
	}
}

/**
 * 计算投保人年龄，判定是否异常
 * 正常返回true，异常返回false
 */
function CheckAppAge(birthday)
{
	var i = calAge(birthday);
	var minAge = 18;
	var maxAge = 150;
	var appStartAge = $("#appStartAge").val();
	var appEndAge = $("#appEndAge").val();
	if(appStartAge != ""){
		minAge = parseInt(appStartAge);
	}
	if(appEndAge != ""){
		maxAge = parseInt(appEndAge);
	}

	if (i > maxAge ||i < minAge)
	{
		return false;
	}
	else
	{
		return true;
	}
}
/**
 * 计算年龄
 * @param birthday
 * @returns {Boolean}
 */
function CheckAge(birthday)
{
	var i = calAge(birthday);
	if (i>150 ||i<0)
	{
		return false;
	}
	else
	{
		return true;
	}
}

/**
 * 用出生日期计算年龄，目前不支持yyyymmdd模式
 * 参数，出生日期yy－mm－dd
 * 返回  年龄
 */
function calAge(birthday)
{
	var arrBirthday = birthday.split("-");
	if (arrBirthday.length == 1)
	{
		if(arrBirthday[0].length != 8)
		{
			return "";
		}
		var arrBirthdays = new Array();
		arrBirthdays[0] = arrBirthday[0].substring(0, 4);
		arrBirthdays[1] = arrBirthday[0].substring(4, 6);
		arrBirthdays[2] = arrBirthday[0].substring(6, 8);
		var today = new Date();
		var arrToday = new Array();
		arrToday[0] = today.getFullYear();
		arrToday[1] = today.getMonth() + 1;
		arrToday[2] = today.getDate();
		var age = arrToday[0] - arrBirthdays[0] - 1;
		//当前月大于出生月
		if(arrToday[1] > arrBirthdays[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthdays[1])
		{
			//当前月小于出生月
			return age;
		}
		else if(arrToday[2] >= arrBirthdays[2])
		{
			//当前月等于出生月的时候，看出生日
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
	else
	{
		if(arrBirthday[1].length == 1)
		{
			arrBirthday[1] = "0" + arrBirthday[1];
		}
		if(arrBirthday[2].length == 1)
		{
			arrBirthday[2] = "0" + arrBirthday[2];
		}
		var today = new Date();
		var arrToday = new Array();
		arrToday[0] = today.getFullYear();
		arrToday[1] = today.getMonth() + 1;
		arrToday[2] = today.getDate();
		var age = arrToday[0] - arrBirthday[0] - 1;
		//当前月大于出生月
		if(arrToday[1] > arrBirthday[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthday[1])
		{
			//当前月小于出生月
			return age;
		}
		else if(arrToday[2] >= arrBirthday[2])
		{
			//当前月等于出生月的时候，看出生日
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
}

/**
 * 用出生日期和保单申请日期计算年龄
 * 参数，出生日期yy－mm－dd，yyyymmdd
 * 返回  年龄
 */
function calAgeNew(birthday,applyday)
{
	var arrBirthday = birthday.split("-");
	var arrApplyday = applyday.split("-");
	if (arrBirthday.length == 1&&arrApplyday.length == 1)
	{
		if(arrBirthday[0].length != 8||arrApplyday[0].length!= 8)
		{
			return "";
		}
		var arrBirthdays = new Array();
		arrBirthdays[0] = arrBirthday[0].substring(0, 4);
		arrBirthdays[1] = arrBirthday[0].substring(4, 6);
		arrBirthdays[2] = arrBirthday[0].substring(6, 8);
		var arrToday = new Array();
		arrToday[0] = arrApplyday[0].substring(0, 4);
		arrToday[1] = arrApplyday[0].substring(4, 6);
		arrToday[2] = arrApplyday[0].substring(6, 8);
		var age = arrToday[0] - arrBirthdays[0] - 1;
		//当前月大于出生月
		if(arrToday[1] > arrBirthdays[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthdays[1])
		{
			//当前月小于出生月
			return age;
		}
		else if(arrToday[2] >= arrBirthdays[2])
		{
			//当前月等于出生月的时候，看出生日
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
	else if(arrBirthday.length == 1&&arrApplyday.length != 1)
	{
		if(arrBirthday[0].length != 8)
		{
			return "";
		}
		var arrBirthdays = new Array();
		arrBirthdays[0] = arrBirthday[0].substring(0, 4);
		arrBirthdays[1] = arrBirthday[0].substring(4, 6);
		arrBirthdays[2] = arrBirthday[0].substring(6, 8);
		if(arrApplyday[1].length == 1)
		{
			arrApplyday[1] = "0" + arrApplyday[1];
		}
		if(arrApplyday[2].length == 1)
		{
			arrApplyday[2] = "0" + arrApplyday[2];
		}
		var arrToday = new Array();
		arrToday[0] = arrApplyday[0];
		arrToday[1] = arrApplyday[1];
		arrToday[2] = arrApplyday[2];
		var age = arrToday[0] - arrBirthdays[0] - 1;
		//当前月大于出生月
		if(arrToday[1] > arrBirthdays[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthdays[1])
		{
			//当前月小于出生月
			return age;
		}
		else if(arrToday[2] >= arrBirthdays[2])
		{
			//当前月等于出生月的时候，看出生日
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
	else if(arrBirthday.length != 1&&arrApplyday.length == 1)
	{
		if(arrApplyday[0].length != 8)
		{
			return "";
		}
		if(arrBirthday[1].length == 1)
		{
			arrBirthday[1] = "0" + arrBirthday[1];
		}
		if(arrBirthday[2].length == 1)
		{
			arrBirthday[2] = "0" + arrBirthday[2];
		}
		var arrToday = new Array();
		arrToday[0] = arrApplyday[0].substring(0, 4);
		arrToday[1] = arrApplyday[0].substring(4, 6);
		arrToday[2] = arrApplyday[0].substring(6, 8);
		var age = arrToday[0] - arrBirthday[0] - 1;
		//当前月大于出生月
		if(arrToday[1] > arrBirthday[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthday[1])
		{
			//当前月小于出生月
			return age;
		}
		else if(arrToday[2] >= arrBirthday[2])
		{
			//当前月等于出生月的时候，看出生日
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
	else if(arrBirthday.length != 1&&arrApplyday.length != 1)
	{
		if(arrBirthday[1].length == 1)
		{
			arrBirthday[1] = "0" + arrBirthday[1];
		}
		if(arrBirthday[2].length == 1)
		{
			arrBirthday[2] = "0" + arrBirthday[2];
		}
		if(arrApplyday[1].length == 1)
		{
			arrApplyday[1] = "0" + arrApplyday[1];
		}
		if(arrApplyday[2].length == 1)
		{
			arrApplyday[2] = "0" + arrApplyday[2];
		}
		var arrToday = new Array();
		arrToday[0] = arrApplyday[0];
		arrToday[1] = arrApplyday[1];
		arrToday[2] = arrApplyday[2];
		var age = arrToday[0] - arrBirthday[0] - 1;
		//当前月大于出生月
		if(arrToday[1] > arrBirthday[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthday[1])
		{
			//当前月小于出生月
			return age;
		}
		else if(arrToday[2] >= arrBirthday[2])
		{
			//当前月等于出生月的时候，看出生日
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
}

/**
 * 搜寻主窗口，用于CodeSelect缓存数据
 */
function searchMainWindow(win)
{
	if(typeof(win) != "object")
	{
		return false;
	}
	if(win.top.name == "FH")
	{
		return win.top;
	}
	return searchMainWindow(win.top.opener);
}

//校验暂交费号发放
function verifyTempfeeNo(tempfeeNo)
{
	//去系统表LDSysVar中查询Sysvar字段名为checkNewType的纪录，判断是否需要去查询单证状态表
	var tSql = "select Sysvarvalue from LDSysVar where Sysvar='CheckNewType'";
	var tResult = easyExecSql(tSql, 1, 0, 1);
	//为了校验，设置ldsysvar变量为3
	if(tResult=="1" || tResult=="3")
	{
		//如果查到该纪录，表明需要查询单证状态表
		//去单证状态表里查询该号码是否有效,暂交费收据号
		var strSql = "select CertifyCode from LZCardTrack where Startno<='"+tempfeeNo+"' and Endno>='"+tempfeeNo+"' and Receivecom = 'D"+fm.all('AgentCode').value+"' and StateFlag='0' and CertifyCode in(select CertifyCode from LMCertifyDes where CertifyClass2 = '0')";
		var strResult=easyQueryVer3(strSql, 1, 0, 1);
		if(!strResult)
		{
			alert("该单证（单证编码为："+tempfeeNo+" ）没有发放给该销售人员（"+fm.all('AgentCode').value+"）!");
			return false;
		}
	}
	return true;
}

//校验印刷号发放
function verifyPrtNo(prtNo)
{
	//去系统表LDSysVar中查询Sysvar字段名为checkNewType的纪录，判断是否需要去查询单证状态表
	var tSql = "select Sysvarvalue from LDSysVar where Sysvar='CheckNewType'";
	var tResult = easyExecSql(tSql, 1, 0, 1);
	if(tResult=="2" || tResult=="3")
	{
		//如果查到该纪录，表明需要查询单证状态表
		//去单证状态表里查询该号码是否有效,投保单印刷号码
		var strSql = "select CertifyCode from LZCardTrack where Startno<='"+prtNo+"' and Endno>='"+prtNo+"' and Receivecom = 'D"+fm.all('AgentCode').value+"' and StateFlag='0'";
		var strResult=easyQueryVer3(strSql, 1, 0, 1);
		if(!strResult)
		{
			alert("该单证（单证编码为："+prtNo+" ）没有发放给该销售人员（"+fm.all('AgentCode').value+"）!");
			return false;
		}
	}
	return true;
}

/**
 * 显示元素的Title信息（解决信息较多无法直接在界面元素中获得所有信息的问题）
 */
function showTitle()
{
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var strEvent = "";	//保存onDoubleClick事件代码
	var urlStr = "";
	var sFeatures = "";
	var strCode = "";	//代码选择
	var strQueryResult = "";	//代码选择的查询结果集
	var arrCode = null;	//拆分数组
	var strCodeValue = "";	//代码值
	var cacheFlag = false;	//内存中有数据标志
	var strCodeSelect = "";
	//寻找主窗口
	var win = searchMainWindow(this);
	if (win == false) win = this;
	//遍历所有FORM
	var tFormCount = window.document.forms.length;
	for(formsNum=0; formsNum<tFormCount; formsNum++)
	{
		//遍历FORM中的所有ELEMENT
		var tEleCount = window.document.forms[formsNum].elements.length;
		for(elementsNum=0; elementsNum<tEleCount; elementsNum++)
		{
			//寻找代码选择元素
			if(window.document.forms[formsNum].elements[elementsNum].className == "code")
			{
				//取出代码值
				strCodeValue = window.document.forms[formsNum].elements[elementsNum].value;
				//空值则不处理
				if(strCodeValue == "") continue;
			}
			//显示title
			if(window.document.forms[formsNum].elements[elementsNum].type == "text")
			{
				window.document.forms[formsNum].elements[elementsNum].title = window.document.forms[formsNum].elements[elementsNum].value;
			}
		}
	}
}

/**
 * 窗口聚焦函数
 * 参数：show：要显示的窗体
 */
function myonfocus(show)
{
	if(show!=null)
	{
		try
		{
			show.focus();
		}
		catch(ex)
		{
			show=null;
		}
	}
}

//初始化控件类型
function initElementtype(){
	var tFormCount = document.forms.length;
	for(var fm=0;fm<tFormCount;fm++)
	{
		var theElements=document.forms[fm].elements;
		for(var i=0;i<theElements.length;i++)
		{
			if(theElements[i].elementtype && theElements[i].elementtype.indexOf("nacessary")!=-1)
			{
				theElements[i].insertAdjacentHTML("afterEnd","<font id='"+theElements[i].name+"n' color=red>&nbsp;*</font>");
			}
			if(theElements[i].elementtype && theElements[i].elementtype.indexOf("misty")!=-1)
			{
				theElements[i].insertAdjacentHTML("afterEnd","<font color=red>&nbsp;?</font>");
			}
			if(theElements[i].readOnly)
			{
				theElements[i].tabIndex=-1;
			}
		}
	}
	initElementAddOnblur();
}

//根据身份证号取得性别 update 2004-12-09 wzw
function getSexByIDNo(iIdNo)
{
	var tSex="";
	var strReturn="";
	if((iIdNo.length!=15) &&(iIdNo.length!=18))
	{
		strReturn = "输入的身份证号位数错误";
		return strReturn;
	}
	var tmpInt=0;
	if(iIdNo.length==15)
	{
		tmpInt = parseInt(iIdNo.substring(14));
	}
	if(iIdNo.length==18)
	{
		tmpInt = parseInt(iIdNo.substring(16,17));
	}
	tmpInt = tmpInt % 2;
	if(tmpInt==0)
	{
		tSex="1";
	}
	else
	{
		tSex="0";
	}
	return tSex;
}

/**
 * 根据设置开启新窗口
 */
function OpenWindowNew(strurl,windowname,opentype,width,height)
{
	if(opentype=="left")
	{
		window.open(strurl,windowname,'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(width=='undefined' || width==null )
	{
		width=800;
	}
	if(height=='undefined'||  height==null)
	{
		height=500;
	}
	if(opentype=="middle")
	{
		var iWidth=width; //模态窗口宽度
		var iHeight=height;//模态窗口高度
		var iTop=(window.screen.height-iHeight)/2;
		var iLeft=(window.screen.width-iWidth)/2;
		window.open(strurl,windowname,'width='+iWidth+',height='+iHeight+',top='+iTop+',left='+iLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

/**
 * 输入日期格式为yyyymmdd
 * 输出日期格式为yyyy-mm-dd
 */
function modifydate(strDate)
{
	var stadate;
	if ( strDate!='')
	{
		if (!isDate(strDate))
		{
			var year=strDate.substring(0,4);
			var month=strDate.substring(4,6);
			var day=strDate.substring(6);
			stadate=year+'-'+month+'-'+day;
		}
		else
		{
			stadate=strDate;
		}
		return stadate;
	}
}

//取得电话号码
function getfullphone(phoneno)
{
	var arrphone = new Array();
	arrphone[0]="86";
	arrphone[1]="";
	arrphone[2]="";
	arrphone[3]="";
	if(phoneno!="")
	{
		arrphone[1]=phoneno.substring(0,phoneno.indexOf("-"));
		if(phoneno.indexOf("-")==phoneno.lastIndexOf("-"))
		{
			arrphone[2]=phoneno.substring(phoneno.indexOf("-")+1,phoneno.length);
			arrphone[3]="";
		}
		else
		{
			arrphone[2]=phoneno.substring(phoneno.indexOf("-")+1,phoneno.lastIndexOf("-"));
			arrphone[3]=phoneno.substring(phoneno.lastIndexOf("-")+1,phoneno.length);
		}
	}
	return arrphone;
}

/*
 * 下面的代码是更改页面title提示显示样式的代码
 * 默认的显示title时间太短不爽
 * update 2004-12-09 wzw
 */
ToolTipGlobal={id:0,getId:function(o){this.all[this.all.length]=o;return this.id++;},all:[]};
function ToolTip(defaultOpacity,font,BGround,color,border,offsetOn,offsetOff)
{
	this.id = ToolTipGlobal.getId(this);
	this.defaultOpacity = defaultOpacity;
	this.opacity = defaultOpacity;
	this.font = font;
	this.BGround = BGround;	//title显示的背景颜色
	this.border = border;
	this.timerOn = null;
	this.timerOff = null;
	this.offsetOn = offsetOn;
	this.offsetOff = offsetOff;
	this.control = null;
	var o = this;
	//window.attachEvent("onload",function(){ o.setup();});
}
ToolTip.prototype.fadeOn = function()
{
	window.clearTimeout(this.timerOff);
	this.timerOn = window.setTimeout("ToolTipGlobal.all["+this.id+"].fade(1)",this.offsetOn[1]);
};
ToolTip.prototype.fadeOff = function()
{
	window.clearTimeout(this.timerOn);
	this.timerOff = window.setTimeout("ToolTipGlobal.all["+this.id+"].fade(0)",this.offsetOff[1]);
};
ToolTip.prototype.setOpacity = function(x)
{
	this.opacity = x;
	this.control.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity="+ x +") progid:DXImageTransform.Microsoft.Shadow(color='#FF444444', Direction=135, Strength=3)";
};
ToolTip.prototype.fade = function(x)
{
	var o = this.control;
	var ox = this.opacity;
	if(x)
	{
		if(ox + this.offsetOn[0] <100)
		{
			this.setOpacity(ox+this.offsetOn[0]);
			this.fadeOn();
		}
		else
		{
			this.setOpacity(100);
		}
	}
	else
	{
		if(ox - this.offsetOff[0]>this.defaultOpacity)
		{
			this.setOpacity(ox - this.offsetOn[0]);
			this.fadeOff();
		}
		else
		{
			this.setOpacity(this.defaultOpacity);
			o.style.visibility = "hidden";
		}
	}
};
ToolTip.prototype.setup = function()
{
	var o = document.createElement("div");
	var oThis = this;
	with(o.style)
	{
		position = "absolute";
		top = "0px";
		left = "0px";
		font = this.font;
		zIndex = 99999;
		background = this.BGround;
		color = this.color;
		border = this.border;
		padding = "2px 4px";
		visibility = "hidden";
	}
	document.body.appendChild(o);
	this.control = o;
	document.attachEvent("onmouseover",function(){
		var e = window.event.srcElement;

		if(e!=null && e.title != "")
		{
			e.tip = e.title;
			e.title = "";
		}
		if(e!=null && typeof(e.tip) != "undefined" && e.tip != null)
		{
			o.innerHTML = e.tip;
			oThis.setOpacity(oThis.defaultOpacity);
			var x,y,docheight,docwidth,dh,dw;
			x = window.event.clientX + document.body.scrollLeft;	//触发事件所在横坐标
			y = window.event.clientY + document.body.scrollTop;	//触发事件所在纵坐标
			docheight = document.body.clientHeight;	//触发事件的所在区域高度
			docwidth  = document.body.clientWidth;	//触发事件的所在区域宽度
			dh =(o.offsetHeight + y) - docheight;
			dw =(o.offsetWidth + x)  - docwidth;
			if(dw > 0)
			{
				o.style.left = x;
			}
			else
			{
				o.style.left = x;
			}
			if(dh > 0)
			{
				o.style.top = y  - 5 ;
			}
			else
			{
				o.style.top  = y  + 10 ;
			}
			o.style.visibility = "visible";
			oThis.fadeOn();
		}
	});
	//鼠标移开目标的时候触发事件
	document.attachEvent("onmouseout",function(){
		var e = window.event.srcElement;
		if(e!=null && typeof(e.tip) != "undefined" && e.tip != null)
		{
//			oThis.fadeOff();
			//隐藏titile信息
			o.style.visibility = "hidden";
		}
	});
};
var tooltip = new ToolTip(20,"9pt Arial 宋体","#ffffdd","#000000","1px solid #000000",[8,20],[8,20]);

//延迟恢复按钮
//elementID-按钮的标志
//mills －延迟的时间
//lanjun 2006/09/02
function setEnable(elementID,mills)
{
	try
	{
		//alert(elementID);
		if(mills == null || mills == "")
		{
			mills = DELAY_MILLS;//默认2000ms
		}
	  setTimeout("enableButton('"+elementID+"')",mills);
  }
  catch(ex)
  {
  	//alert(ex.message);
  }
}

//恢复按钮
function enableButton(elementID)
{
	try
	{
		document.getElementById(elementID).disabled = false;
	}
	catch(ex)
	{
	}
}
function getChnMoney(value)
{
  var intFen,i;
  var strArr,strCheck,strFen,strDW,strNum,strBig,strNow;
  // 数据为空时返回"零"
  if(trim(value)=="")
  {
    return "零";
  }
  if (isNaN(value)) //数据非法时提示，并返回空串
  {
    strErr = "数据"+value+"非法！";
    alert(strErr);
    return "";
  }
  strCheck = value+".";
  strArr = strCheck.split(".");
  strCheck = strArr[0];
  if(strCheck.length>12) //数据大于等于一万亿时提示无法处理
  {
    strErr = "数据"+value+"过大，无法处理！";
    alert(strErr);
    return "";
  }
  try
  {
    i = 0;
    strBig = "";
    intFen = value*100; //转换为以分为单位的数值
    strFen = intFen.toString();
    strArr = strFen.split(".");
    strFen = strArr[0];
    intFen = strFen.length; //获取长度
    strArr = strFen.split(""); //将各个数值分解到数组内
    while(intFen!=0) //分解并转换
    {
      i = i+1;
      switch(i) //选择单位
      {
        case 1:strDW = "分";break;
        case 2:strDW = "角";break;
        case 3:strDW = "元";break;
        case 4:strDW = "拾";break;
        case 5:strDW = "佰";break;
        case 6:strDW = "仟";break;
        case 7:strDW = "万";break;
        case 8:strDW = "拾";break;
        case 9:strDW = "佰";break;
        case 10:strDW = "仟";break;
        case 11:strDW = "亿";break;
        case 12:strDW = "拾";break;
        case 13:strDW = "佰";break;
        case 14:strDW = "仟";break;
      }
      switch (strArr[intFen-1]) //选择数字
      {
        case "1":strNum = "壹";break;
        case "2":strNum = "贰";break;
        case "3":strNum = "叁";break;
        case "4":strNum = "肆";break;
        case "5":strNum = "伍";break;
        case "6":strNum = "陆";break;
        case "7":strNum = "柒";break;
        case "8":strNum = "捌";break;
        case "9":strNum = "玖";break;
        case "0":strNum = "零";break;
      }
  
      //处理特殊情况
      strNow = strBig.split("");
      //分为零时的情况
      if((i==1)&&(strArr[intFen-1]=="0"))
      strBig = "整";
      //角为零时的情况
      else if((i==2)&&(strArr[intFen-1]=="0"))
      {
        //角分同时为零时的情况
        if(strBig!="整")
        {
          strBig = "零"+strBig;
        }
      }
      //元为零的情况
      else if((i==3)&&(strArr[intFen-1]=="0"))
      {
        strBig = "元"+strBig;
      }
      //拾－仟中一位为零且其前一位（元以上）不为零的情况时补零
      else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="元"))
      {
        strBig = "零"+strBig;
      }
      //拾－仟中一位为零且其前一位（元以上）也为零的情况时跨过
      else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零"))
      {

      } 
      //拾－仟中一位为零且其前一位是元且为零的情况时跨过
      else if((i<7)&&(i>3)&&(strArr[intFen-1]=="0")&&(strNow[0]=="元"))
      {
      	
      }
      //当万为零时必须补上万字
      else if((i==7)&&(strArr[intFen-1]=="0"))
      {
        strBig ="万"+strBig;
      }
      //拾万－仟万中一位为零且其前一位（万以上）不为零的情况时补零
      else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="万"))
      {
        strBig = "零"+strBig;
      }
      //拾万－仟万中一位为零且其前一位（万以上）也为零的情况时跨过
      else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="万"))
      {
      	
      }
      //拾万－仟万中一位为零且其前一位为万位且为零的情况时跨过
      else if((i<11)&&(i>7)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零"))
      {
      	
      }
      //万位为零且存在仟位和十万以上时，在万仟间补零
      else if((i<11)&&(i>8)&&(strArr[intFen-1]!="0")&&(strNow[0]=="万")&&(strNow[2]=="仟"))
      {
        strBig = strNum+strDW+"万零"+strBig.substring(1,strBig.length);
      }
      //单独处理亿位
      else if(i==11)
      {
      //亿位为零且万全为零存在仟位时，去掉万补为零
        if((strArr[intFen-1]=="0")&&(strNow[0]=="万")&&(strNow[2]=="仟"))
        {
          strBig ="亿"+"零"+strBig.substring(1,strBig.length);
        }
        //亿位为零且万全为零不存在仟位时，去掉万
        else if((strArr[intFen-1]=="0")&&(strNow[0]=="万")&&(strNow[2]!="仟"))
        {
          strBig ="亿"+strBig.substring(1,strBig.length);
        }
        //亿位不为零且万全为零存在仟位时，去掉万补为零
        else if((strNow[0]=="万")&&(strNow[2]=="仟"))
        {
          strBig = strNum+strDW+"零"+strBig.substring(1,strBig.length);
        }
        //亿位不为零且万全为零不存在仟位时，去掉万 
        else if((strNow[0]=="万")&&(strNow[2]!="仟"))
        {
          strBig = strNum+strDW+strBig.substring(1,strBig.length); 
        }
        //其他正常情况
        else
        {
          strBig = strNum+strDW+strBig;
        }
      }
      //拾亿－仟亿中一位为零且其前一位（亿以上）不为零的情况时补零
      else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]!="零")&&(strNow[0]!="亿"))
      {
        strBig = "零"+strBig;
      }
      //拾亿－仟亿中一位为零且其前一位（亿以上）也为零的情况时跨过
      else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="亿"))
      {
      	
      }
      //拾亿－仟亿中一位为零且其前一位为亿位且为零的情况时跨过
      else if((i<15)&&(i>11)&&(strArr[intFen-1]=="0")&&(strNow[0]=="零"))
      {
      	
      }
      //亿位为零且不存在仟万位和十亿以上时去掉上次写入的零
      else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="零")&&(strNow[1]=="亿")&&(strNow[3]!="仟"))
      {
        strBig = strNum+strDW+strBig.substring(1,strBig.length);
      }
      //亿位为零且存在仟万位和十亿以上时，在亿仟万间补零
      else if((i<15)&&(i>11)&&(strArr[intFen-1]!="0")&&(strNow[0]=="零")&&(strNow[1]=="亿")&&(strNow[3]=="仟"))
      {
        strBig = strNum+strDW+"亿零"+strBig.substring(2,strBig.length);
      }
      else
      {
        strBig = strNum+strDW+strBig;
      }
      strFen = strFen.substring(0,intFen-1);
      intFen = strFen.length;
      strArr = strFen.split("");
    }
    return strBig;
  }
  catch(err)
  {
    return ""; //若失败则返回原值
  } 
}
  
  /**
* 从LDCode取得代码对应的名称
* CodeType:字段类型
* Code:代码
* 如果存在返回名称,反之返回""
*/
function getNameFromLDCode(CodeType,Code){
	var tCodeType=CodeType.toLowerCase();
	var tCode=Code;

	if(tCode!='' && tCode!=null){
		var arrSelected=new Array();

		var strSQL = "";
		strSQL = "select CodeName from FDCode where CodeType='"+tCodeType+"' and Code='"+tCode+"'";

		var strQueryResult = easyQueryVer3(strSQL,1,0,1);
		arrSelected = decodeEasyQueryResult(strQueryResult,0,1);

		if(arrSelected==null)
			return("");
		else
			return(arrSelected[0][0]);
	}
	else{
		return("");
	}
}

//恢复字符（判定是否用escape转换的，如果不是则用自行的替换函数）
function unescapes(ls_string){
	if((ls_string.indexOf("%u") == -1)&&(ls_string.indexOf("%20") == -1)&&(ls_string.indexOf("%0D%0A") == -1)){
		//估计不需要我做什么操作了，如果使用民生的编码体系，使用民生的体系，好像还是会有一定的问题，可能是没有编译类
		ls_string = replace(ls_string,"@E!","\r\n");
		ls_string = replace(ls_string,"@SQ!","\'");
		ls_string = replace(ls_string,"@DQ!","\"");
	}
	else{
		ls_string = unescape(ls_string);
	}
	return ls_string;
}

function NullOrBlank(sVal){
	if (trim(sVal)=='' || (trim(sVal)==null)) 
	return true;
	return false;
}

//替换字符（回车、单引号、双引号），有待考证
function escapes(ls_string)
{
	ls_string = replace(ls_string,"\r\n","@E!");
	ls_string = replace(ls_string,"\'","@SQ!");
	ls_string = replace(ls_string,"\"","@DQ!");
	return ls_string;
}

//字符转换成数字
function charToNum(str)
{
	var rtnNum=0;
	str = String(str);
	var tStr=replace(str,",","");
	rtnNum=parseFloat(tStr);
	return rtnNum;
}

//字符转换成数字,如果字符串为null,将字符串转换为0
function charToNum2(str)
{
	if(isNaN(str)){  
		return 0;
	}
	if(str==''){
		return 0;
	}
	else{
		var rtnNum=0;
		str = String(str);
		var tStr=replace(str,",","");
		rtnNum=parseFloat(tStr);
		return rtnNum;
	}
}
/*字符转换成数字,如果字符串为null,将字符串转换为0
 支持负数
	如果str 为负数, 返回的结果为负数
*/
function charToNum3(str)
{
	if(isNaN(str)){  
		return 0;
	}
	if(str==''){
		return 0;
	}
	var str1=String(str);
	if(str1.substring(0,1)=="-")
	{
		var str2 = String(str1.substring(1));//截掉负号
		var rtnNum=0;
		var tStr=replace(str2,",","");
		rtnNum=parseFloat(tStr);
		return -rtnNum;
	}
	else{
		var rtnNum=0;
		str = String(str);
		var tStr=replace(str,",","");
		rtnNum=parseFloat(tStr);
		return rtnNum;
	}
}

/**
* 查询指定字段的值
* tableName:表名
* fieldName:字段名
* fieldValue:查询条件
* returnField:返回字段名
* 如果存在返回名称,反之返回null
*/
function getNameByCode(returnField,tableName,fieldName,fieldValue){
	var strSQL = "";
	if(fieldValue!='' && fieldValue!=null){
		strSQL = "select "+returnField+" from "+tableName+" where "+fieldName+" = '"+fieldValue+"'";
		strQueryResult = easyQueryVer3(strSQL,1,0,1);
		//判断是否查询成功
		if (!strQueryResult){
			return("");
		}
		var arrReturn = new Array();
		arrReturn = decodeEasyQueryResult(strQueryResult,0,1);
		return arrReturn[0][0];
	}
	else{
		return("");
	}
}

/**
* 从LDCodeShow取得代码对应的名称
* CodeType:字段类型
* Code:代码
* 如果存在返回名称,反之返回""
*/
function getNameFromLDCodeShow(CodeType,Code){
	var tCodeType=CodeType.toLowerCase();
	var tCode=Code;

	if(tCode!='' && tCode!=null){
		var arrSelected=new Array();

		var strSQL = "";
		strSQL = "select CodeName from LDCodeShow where CodeType='"+tCodeType+"' and Code='"+tCode+"' and LanguageType='"+LanguageType+"'";

		var strQueryResult = easyQueryVer3(strSQL,1,0,1);
		arrSelected = decodeEasyQueryResult(strQueryResult,0,1);

		if(arrSelected==null)
			return("");
		else
			return(arrSelected[0][0]);
	}
	else{
		return("");
	}
}


/**
* 从LDUser1取得代码对应的内容
* UserCode:代码
* 如果存在返回二维数组,反之返回null
*/
function getValueFromLDUser1(UserCode){
	var tUserCode=UserCode;

	if(tUserCode!='' && tUserCode!=null){
		var arrSelected=new Array();
		var strSQL = "";
		strSQL = "select UserShowCode,UserName,Name1,Name2,Name3,RoleFlag "
		        +"from LDUser1 where 1=1 and UserCode='"+tUserCode+"' and RoleFlag in ('01','02')";
		        
		var strQueryResult = easyQueryVer3(strSQL,1,0,1);
		arrSelected = decodeEasyQueryResult(strQueryResult,0,1);

		if(arrSelected==null)
			return null;
		else
			return arrSelected;
	}
	else{
		return null;
	}
}

//数字转换成字符
function numToChar(num)
{
	var rtnStr="";
	rtnStr=formatNumber(num,'#,###.##');
	rtnStr=getInt(rtnStr);
	return rtnStr;
}

/**
  * 格式化数字
  * @param number 数字
  * @param pattern 样式
  * 举例：<p>formatNumber(12432.419,'#,###.##') returns "12432.42"<p>
  * @return 根据样式返回格式化后的数字
*/
function formatNumber(number,pattern)
{
	var str			= number.toString();
	var strInt;
	var strFloat;
	var formatInt;
	var formatFloat;
	if(/\./g.test(pattern))
	{
		formatInt		= pattern.split('.')[0];
		formatFloat		= pattern.split('.')[1];
	}
	else
	{
		formatInt		= pattern;
		formatFloat		= null;
	}

	if(/\./g.test(str))
	{
		if(formatFloat!=null)
		{
			var tempFloat	= Math.round(parseFloat('0.'+str.split('.')[1])*Math.pow(10,formatFloat.length))/Math.pow(10,formatFloat.length);
			strInt		= (Math.floor(number)+Math.floor(tempFloat)).toString();				
			strFloat	= /\./g.test(tempFloat.toString())?tempFloat.toString().split('.')[1]:'0';			
		}
		else
		{
			strInt		= Math.round(number).toString();
			strFloat	= '0';
		}
	}
	else
	{
		strInt		= str;
		strFloat	= '0';
	}
	if(formatInt!=null)
	{
		var outputInt	= '';
		var zero		= formatInt.match(/0*$/)[0].length;
		var comma		= null;
		if(/,/g.test(formatInt))
		{
			comma		= formatInt.match(/,[^,]*/)[0].length-1;
		}
		var newReg		= new RegExp('(\\d{'+comma+'})','g');

		if(strInt.length<zero)
		{
			outputInt		= new Array(zero+1).join('0')+strInt;
			outputInt		= outputInt.substr(outputInt.length-zero,zero);
		}
		else
		{
			outputInt		= strInt;
		}

		var 
		outputInt			= outputInt.substr(0,outputInt.length%comma)+outputInt.substring(outputInt.length%comma).replace(newReg,(comma!=null?',':'')+'$1');
		outputInt			= outputInt.replace(/^,/,'');

		strInt	= outputInt;
	}

	if(formatFloat!=null)
	{
		var outputFloat	= '';
		var zero		= formatFloat.match(/^0*/)[0].length;

		if(strFloat.length<zero)
		{
			outputFloat		= strFloat+new Array(zero+1).join('0');
			//outputFloat		= outputFloat.substring(0,formatFloat.length);
			var outputFloat1	= outputFloat.substring(0,zero);
			var outputFloat2	= outputFloat.substring(zero,formatFloat.length);
			outputFloat		= outputFloat1+outputFloat2.replace(/0*$/,'');
		}
		else
		{
			outputFloat		= strFloat.substring(0,formatFloat.length);
		}

		strFloat	= outputFloat;
	}
	else
	{
		if(pattern!='' || (pattern=='' && strFloat=='0'))
		{
			strFloat	= '';
		}
	}

	return strInt+(strFloat==''?'':'.'+strFloat);
}


//如果一个字符串数字中小数点后全为零，则去掉小数点及零
function getInt(Value)
{
	var result="";
	var mflag=true;
	var m=0;
	m=Value.lastIndexOf(".");
	if(m==-1){
		result=Value;
	}
	else{
		for (var i=m+1;i<=Value.length-1;i++){
			if (Value.charAt(i)!='0'){
				result=Value;
				mflag=false;
				break;
			}
		}
		
		if (mflag==true){
			result=Value.substring(0,m);
		}
	}
	
	return result;
}
/**
* 从LDUser取得代码对应的名称
* UserDescription:描述
* UserCode:代码
* 如果存在返回名称,反之返回""
*/
function getNameFromLDUser(UserDescription,UserCode){
	var tUserDescription=UserDescription;
	var tUserCode=UserCode;

	if(tUserCode!='' && tUserCode!=null){
		var arrSelected=new Array();

		var strSQL = "";
		strSQL = "select UserName from LDUser where UserDescription='"+tUserDescription+"' and UserCode='"+tUserCode+"'";

		var strQueryResult = easyQueryVer3(strSQL,1,0,1);
		arrSelected = decodeEasyQueryResult(strQueryResult,0,1);

		if(arrSelected==null)
			return("");
		else
			return(arrSelected[0][0]);
	}
	else{
		return("");
	}
}

/**
* 从LDUser1取得代码对应的名称
* UserDescription:描述
* UserCode:代码
* 如果存在返回名称,反之返回""
*/
function getNameFromLDUser1(UserDescription,UserCode){
	var tUserDescription=UserDescription;
	var tUserCode=UserCode;

	if(tUserCode!='' && tUserCode!=null){
		var arrSelected=new Array();
		var strSQL = "";
		strSQL = "select UserName from LDUser1 where UserDescription='"+tUserDescription+"' and UserCode='"+tUserCode+"'";
		var strQueryResult = easyQueryVer3(strSQL,1,0,1);
		arrSelected = decodeEasyQueryResult(strQueryResult,0,1);

		if(arrSelected==null)
			return("");
		else
			return(arrSelected[0][0]);
	}
	else{
		return("");
	}
}

/**
* 从LDUser取得代码对应的内容
* UserCode:代码
* RoleFlag:角色类型
* 如果存在返回二维数组,反之返回null
*/
function getValueFromLDUser(UserCode,RoleFlag){
	var tUserCode=UserCode;
	var tRoleFlag=RoleFlag;

	if(tUserCode!='' && tUserCode!=null){
		var arrSelected=new Array();
		var strSQL = "";
		strSQL = "select UserShowCode,UserName,Name1,Name2,Name3,RoleFlag "
		        +"from LDUser where 1=1 and UserCode='"+tUserCode+"' and RoleFlag in "+tRoleFlag;
		        
		var strQueryResult = easyQueryVer3(strSQL,1,0,1);
		arrSelected = decodeEasyQueryResult(strQueryResult,0,1);

		if(arrSelected==null)
			return null;
		else
			return arrSelected;
	}
	else{
		return null;
	}
}

/**
* 从JCClient取得客户编码对应的内容
* ClientNo:客户编码
* 如果存在返回二维数组,反之返回null
*/
function getValueFromJCClient(ClientNo){
	var tClientNo=ClientNo;

	if(tClientNo!='' && tClientNo!=null){
		var arrSelected=new Array();
		var strSQL = "";
		strSQL = "select ClientShowNo,ClientName,Status,RoleFlag "
		        +"from JCClient where 1=1 and ClientNo='"+tClientNo+"'";
		        
		var strQueryResult = easyQueryVer3(strSQL,1,0,1);
		arrSelected = decodeEasyQueryResult(strQueryResult,0,1);

		if(arrSelected==null)
			return null;
		else
			return arrSelected;
	}
	else{
		return null;
	}
}
/**
* 从LMRisk取得险种编码对应的险种名称
* RiskCode:险种编码
* 如果存在返回险种名称,反之返回""
*/
function getNameFromLMRisk(RiskCode){
	var tRiskCode=RiskCode;

	if(tRiskCode!='' && tRiskCode!=null){
		var arrSelected=new Array();

		var strSQL = "";
		strSQL = "select RiskName from LMRisk where RiskCode='"+tRiskCode+"'";

		var strQueryResult = easyQueryVer3(strSQL,1,0,1);
		arrSelected = decodeEasyQueryResult(strQueryResult,0,1);

		if(arrSelected==null)
			return("");
		else
			return(arrSelected[0][0]);
	}
	else{
		return("");
	}
}
/**
* 查询指定值在数据库中存不存在
* tableName:表名
* fieldName:字段名
* fieldValue:查询条件
* 如果存在返回true,反之返回false
*/
function checkValueIsExsit(tableName,fieldName,fieldValue){
	var strSQL = "";
	strSQL = "select "+fieldName+" from "+tableName+" where "+fieldName+" = '"+fieldValue+"'";
	strQueryResult = easyQueryVer3(strSQL,1,0,1);
	//判断是否查询成功
	if (!strQueryResult){
		return false;
	}
	return true;
}

//根据身份证号计算生日
function calBirthByIDNo(IDNo)
{
	var tIDNo="";
	
	if (typeof(IDNo) == "object")
		tIDNo = IDNo.value;
	else
		tIDNo = IDNo;
		
	if(!checkIdCard(tIDNo))
		return "";
		
	if(tIDNo.length==15)
	{
		var tBirthday=tIDNo.substring(6,12);
		var year="19"+tBirthday.substring(0,2);
		var month=tBirthday.substring(2,4);
		var day=tBirthday.substring(4,6);
			
		tBirthday=year+"-"+month+"-"+day;
		
		if(!checkValidDate(year,month,day))
		{
			alert("没有"+tBirthday+"这一天！");
			if (typeof(IDNo) == "object")
				IDNo.value="";
				
			return "";
		}
		
		return tBirthday;
	}
	
	if(tIDNo.length==18)
	{
		var tBirthday=tIDNo.substring(6,14);
		var year=tBirthday.substring(0,4);
		var month=tBirthday.substring(4,6);
		var day=tBirthday.substring(6,8);
		
		tBirthday=year+"-"+month+"-"+day;
		
		if(!checkValidDate(year,month,day))
		{
			alert("没有"+tBirthday+"这一天！");
			if (typeof(IDNo) == "object")
				IDNo.value="";			
			
			return "";
		}		
		
		return tBirthday;
	}	
	
}
//根据身份证号计算性别
//参数：flag：0返回性别代码；1返回性别名称
function calSexByIDNo(IDNo,flag)
{
	var tIDNo="";
	
	if (typeof(IDNo) == "object")
		tIDNo = IDNo.value;
	else
		tIDNo = IDNo;	
	
	if(!isValidIDNo(tIDNo))
		return "";
		
	if(tIDNo.length==15)
	{
		var tSex=tIDNo.substring(14,15);
		if(tSex=="1" || tSex=="3" || tSex=="5" || tSex=="7" || tSex=="9")
			if(flag=="0")
				return "1";
			else
				return "男性";
				
		if(tSex=="0" || tSex=="2" || tSex=="4" || tSex=="6" || tSex=="8")
			if(flag=="0")
				return "2";
			else
				return "女性";
		
	}
	
	if(tIDNo.length==18)
	{
		var tSex=tIDNo.substring(16,17);
		if(tSex=="1" || tSex=="3" || tSex=="5" || tSex=="7" || tSex=="9")
			if(flag=="0")
				return "1";
			else
				return "男性";
				
		if(tSex=="0" || tSex=="2" || tSex=="4" || tSex=="6" || tSex=="8")
			if(flag=="0")
				return "2";
			else
				return "女性";
		
	}	
		
}
//用来判定画面中的所有有值的codeselect框中的内容是否存在
function checkcode(){
	removeAllError();
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var strEvent = "";	//保存onDoubleClick事件代码
	var urlStr = "";
	var sFeatures = "";
	var strCode = "";	//代码选择
	var aCode = "";	//代码
	var bCode = "";	//查询条件值
	var cCode = "";	//查询条件对象
	var strQueryResult = "";	//代码选择的查询结果集
	var arrCode = null;	//拆分数组
	var strCodeValue = "";	//代码值
	var cacheFlag = false;	//数据校验标志
	var strCodeSelect = "";
	//寻找主窗口
	var win = searchMainWindow(this);
	if (win == false){ win = this; }
	//遍历所有FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++){
		//遍历FORM中的所有ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++){
			//寻找代码选择元素
			if ((window.document.forms[formsNum].elements[elementsNum].className == "code")||(window.document.forms[formsNum].elements[elementsNum].className == "codeno")){
				//取出代码值
				strCodeValue = window.document.forms[formsNum].elements[elementsNum].value;
				//空值则不处理
				if (strCodeValue == "") continue;
				//虚拟数据源处理，同样是和数组进行一一匹配，找到符合的就通过，如果全部遍历后仍无匹配则报错
				if (window.document.forms[formsNum].elements[elementsNum].CodeData != null){
					strQueryResult = window.document.forms[formsNum].elements[elementsNum].CodeData;
					cacheFlag = false;
					arrCode = decodeEasyQueryResult(strQueryResult);
					for(var m=0;m<arrCode.length;m++){
						if (strCodeValue==arrCode[m][0]){
							cacheFlag = true;
							continue;
						}
					}
					if (cacheFlag==false){
						alert(window.document.forms[formsNum].elements[elementsNum].title+"代码选择错误！");
						window.document.forms[formsNum].elements[elementsNum].focus();
						return false;
						break;
					}
				}
				else{
					//得到ondblclick的函数值
					strEvent = window.document.forms[formsNum].elements[elementsNum].ondblclick;
					if (strEvent == null) continue;
					strCode = new String(strEvent);
					strCode = strCode.substring(strCode.indexOf("showCodeList") + 14);
					aCode = strCode.substring(0, strCode.indexOf("'"));	//得到查询类型
					//如果有null出现，则表示将会有查询条件（但是还不确定一定有，因为有可能只有强制刷新）
					if (strCode.indexOf("null")!=-1){
						strCode = strCode.substring(strCode.indexOf("null") + 5);
						bCode = strCode.substring(0, strCode.indexOf(","));
						strCode = strCode.substring(strCode.indexOf(",")+2);
						cCode = strCode.substring(0, strCode.indexOf("'"));	//得到查询条件所要查询的字段
						if (bCode.indexOf("'")!=-1){
							bCode = bCode.substring(1,bCode.length-1);	//得到查询条件字段所匹配的值
						}
						else{
							bCode = eval(bCode);
						}
						if (bCode == "null") bCode = "";	//出现null的情况
						if (cCode == "null") cCode = "";
					}
					else{
						bCode = "";	//没有查询条件的情况
						cCode = "";
					}
					strCode = aCode + bCode + cCode;	//拼写查询条件，缓存的存储方式为类型＋匹配字段值+匹配字段
					//如果内容中有数据，匹配数据和实际数据，无则跳过，可能是因为别的双击带出的
					if (win.parent.VD.gVCode.getVar(strCode) != false){
						cacheFlag = false;
						arrCode = win.parent.VD.gVCode.getVar(strCode);
						for(var m=0;m<arrCode.length;m++){
							if (strCodeValue==arrCode[m][0]){
								cacheFlag = true;
								continue;
								//如果发现有匹配的则立即跳出
							}
						}
						if (cacheFlag==false){
							addError(window.document.forms[formsNum].elements[elementsNum].name+"Name",window.document.forms[formsNum].elements[elementsNum].title+"代码选择错误！");
							//alert(window.document.forms[formsNum].elements[elementsNum].title+"代码选择错误！")
							window.document.forms[formsNum].elements[elementsNum].focus();
							return false;
							break;
						}
					}
					else{
						continue;	//无所谓的一句话
					}
				}
			}
		}
	}
	return true;
}

//判断计算公式是否存在非法符号
//return 布尔值（true--公式合法, false--存在非法符号）
function checkFormula(strValue)
{
	var FormulaNum="1234567890.+-*/%()";
	var i;
	if(strValue==null ||strValue=="") return true;
	for(i=0;i<strValue.length;i++){
		if(FormulaNum.indexOf(strValue.charAt(i))<0) return false;
	}
	
	return true;
}

/**
* 判断项目是否授权  保单信息保存前调用
* ProjCode:项目代码
* 如果已经授权返回"true"，否则返回"false"
*/
function checkProjectAuthorize(ProjCode){
	var tProjCode=ProjCode;
	if(tProjCode!=null && tProjCode!=''){
		var arrResult=new Array();
		var sql="";
		sql="select ClientNo from JCProjMain where ProjCode='"+tProjCode+"'";
		var strResult=easyQueryVer3(sql,1,0,1);
		arrResult = decodeEasyQueryResult(strResult);

		if(arrResult!=null){
			//存在客户
			var tClientNo=arrResult[0][0];
			var tClientType=tClientNo.charAt(0);
			if(tClientType=='U'){
				//是单位客户
				var arrSelected=new Array();
				var strSQL = "";
				strSQL = "select * from JCGetBidSub where ProjCode='"+tProjCode+"' and RiskStat='1' ";
				var strQueryResult = easyQueryVer3(strSQL,1,0,1);
				arrSelected = decodeEasyQueryResult(strQueryResult);

				if(arrSelected==null){
					//项目没有中标信息
					parent.fraInterface.focus();
					alert("该项目还没有中标信息,请重新输入！");
					return false;
				}
				else{
					//项目已经有中标信息了
					return true;
				}
			}
			else if(tClientType=='P'){
					//是个人客户
					var arrSelected=new Array();
					var strSQL = "";
					strSQL = "select * from JCProjAuthor where ProjCode='"+tProjCode+"'";
					var strQueryResult = easyQueryVer3(strSQL,1,0,1);
					arrSelected = decodeEasyQueryResult(strQueryResult);

					if(arrSelected==null){
						//项目没有授权
						parent.fraInterface.focus();
						alert("该项目还没有授权,请重新输入！");
						return false;
					}
					else{
						//项目已经授权
						return true;
					}
				}
				else{
					//客户类型未知
					parent.fraInterface.focus();
					alert("该项目对应的客户编号有误,请查看！");
					return false;
				}
		}
		else{
			//不存在客户
			parent.fraInterface.focus();
			alert("该项目下没有客户,请重新输入！");
			return false;
		}
	}
	else{
		return true;
	}
}

/**
* 判断项目是否授权  寻价招投标保存前调用
* ProjCode:项目代码
* 如果已经授权返回"true"，否则返回"false"
*/
function checkProjectAuthorizeManage(ProjCode){
	var tProjCode=ProjCode;
	if(tProjCode!=null && tProjCode!=''){
		var arrResult=new Array();
		var sql="";
		sql="select ClientNo from JCProjMain where ProjCode='"+tProjCode+"'";
		var strResult=easyQueryVer3(sql,1,0,1);
		arrResult = decodeEasyQueryResult(strResult);

		if(arrResult!=null){
			//存在客户
			var tClientNo=arrResult[0][0];
			var tClientType=tClientNo.charAt(0);
			if(tClientType=='U'){
				//是单位客户
				var arrSelected=new Array();
				var strSQL = "";
				strSQL = "select * from JCProjAuthor where ProjCode='"+tProjCode+"'";
				var strQueryResult = easyQueryVer3(strSQL,1,0,1);
				if(!strQueryResult){
					//项目没有授权
					parent.fraInterface.focus();
					alert("该项目还没有授权,请重新输入！");
					return false;
				}
				else{
					//已授权检查是否有保险建议书
					strSQL = "select * from JCdoc where ProjCode='" + tProjCode +"' and doctypecode='0006'";
					var strQueryResult = easyQueryVer3(strSQL,1,0,1);
					if ( !strQueryResult) 
					{
						alert("该项目还没有保险建议书！");
						return false;
					}
					return true;
				}
			}
			else if(tClientType=='P'){
				//是个人客户
				return true;
				}
				else{
					//客户类型未知
					parent.fraInterface.focus();
					alert("该项目对应的客户编号有误,请查看！");
					return false;
				}
		}
		else{
			//不存在客户
			parent.fraInterface.focus();
			alert("该项目下没有客户,请重新输入！");
			return false;
		}
	}
	else{
		return true;
	}
}

/**
* 判断项目是否授权  咨询费用保存前调用
* ProjCode:项目代码
* 如果已经授权返回"true"，否则返回"false"
*/
function checkProjectAuthorizeQuery(ProjCode){
	var tProjCode=ProjCode;
	if(tProjCode!=null && tProjCode!=''){
		var arrSelected=new Array();
		var strSQL = "";
		strSQL = "select * from JCProjAuthor where ProjCode='"+tProjCode+"'";
		var strQueryResult = easyQueryVer3(strSQL,1,0,1);
		arrSelected = decodeEasyQueryResult(strQueryResult);

		if(arrSelected==null){
			parent.fraInterface.focus();
			alert("该项目还没有授权,请重新输入！");
			return false;
		}
		else{
			//已授权检查是否有保险建议书
					strSQL = "select * from JCdoc where ProjCode='" + tProjCode +"' and doctypecode='0006'";
					var strQueryResult = easyQueryVer3(strSQL,1,0,1);
					if ( !strQueryResult) 
					{
						alert("该项目还没有保险建议书！");
						return false;
					}
			return true;
		}
	}
	else{
		return true;
	}
}

/**
* 日期的合法判断
* <p>checkValidDate("2002","10","03") returns true<p>
* <p>checkValidDate("2001","02","28") returns true<p>
* <p>checkValidDate("2001","02","29") returns false<p>
* <p>checkValidDate("2004","02","29") returns true<p>
* @param year 年份字符串
* @param month 月份字符串
* @param day 日期字符串
* @return 布尔值（true--合法日期, false--非法日期）
*/
function checkValidDate(year,month,day)
{
	var dt = new Date(Number(year),Number(month)-1,Number(day));

	if(dt.getFullYear()==Number(year) && dt.getMonth()==Number(month)-1 && dt.getDate()==Number(day))
		return true;
	else
		return false;	
}
//将中文,全角符号转为英文,半角符号
function cnToEn(cnStr)
{
	var enStr="";
	var tempStr = cnStr;
	tempStr = replace(tempStr,"１","1");
	tempStr = replace(tempStr,"２","2");
	tempStr = replace(tempStr,"３","3");
	tempStr = replace(tempStr,"４","4");
	tempStr = replace(tempStr,"５","5");
	tempStr = replace(tempStr,"６","6");
	tempStr = replace(tempStr,"７","7");
	tempStr = replace(tempStr,"８","8");
	tempStr = replace(tempStr,"９","9");
	tempStr = replace(tempStr,"０","0");
	tempStr = replace(tempStr,"＋","+");
	tempStr = replace(tempStr,"－","-");
	tempStr = replace(tempStr,"＊","*");
	tempStr = replace(tempStr,"／","/");
	tempStr = replace(tempStr,"％","%");
	tempStr = replace(tempStr,"（","(");
	tempStr = replace(tempStr,"）",")");
	tempStr = replace(tempStr,"\r\n","");
	
	enStr = tempStr;
	
	return enStr;
}
/**
* 比较两个日期字符串(日期格式xxxx/xx/xx)
* <p><b>Example: </b><p>
* <p>compareDateI("2002/10/03", "2002/10/03") returns 0<p>
* <p>compareDateI("2002/10/03", "2001/10/03") returns 1<p>
* @param date1 日期字符串,格式必须为“yyyy/mm/dd”
* @param date2 日期字符串,格式必须为“yyyy/mm/dd”
* @return date1=date2则返回0 , date1>date2则返回1 , date1<date2则返回2
*/
function compareDateI(date1,date2){
	var strValue=date1.split("/");
	var date1Temp=new Date(strValue[0],strValue[1],strValue[2]);
	strValue=date2.split("/");
	var date2Temp=new Date(strValue[0],strValue[1],strValue[2]);
	if(date1Temp.getTime()==date2Temp.getTime())
		return 0;
	else if(date1Temp.getTime()>date2Temp.getTime())
			return 1;
		else
			return 2;
}

/**
* 把传入的其他类型的日期格式转换为"2003-03-01"的格式
* 目前支持"2003/03/01"和"20030301"的格式转换
* <p><b>Example: </b><p>
* <p>isDate("2003/03/01") returns "2003-03-01"<p>
* <p>isDate("20030301") returns "2003-03-01"<p>
* @param date 日期字符串,格式可以为“yyyy/mm/dd”，也可以为“yyyymmdd”
* @return String "2003-03-01"
*/
function convertDate(vDate){
	var strValue;
	var dateValue;
	var strDate=vDate.value;
	if(strDate.indexOf("/")>=0){
		strValue=strDate.split("/");
		dateValue=strValue[0]+"-"+strValue[1]+"-"+strValue[2];
	}
	else
		dateValue=strDate.substring(0,4)+"-"+strDate.substring(4,6)+"-"+strDate.substring(6,8);
	vDate.value=dateValue;
}

/**
* 返回一个格式化日期字符串
* <p><b>Example: </b><p>
* <p>dateToString("2002-10-4") returns "2002/10/4"<p>
* @param date 日期型变量
* @return “YYYY/MM/DD”格式化日期字符串
*/
function dateToString(d){
	return  d.getFullYear() +"/"+(d.getMonth()<9?("0"+(d.getMonth()+1)):(d.getMonth()+1)) +"/"+(d.getDate()<10?("0"+d.getDate()):d.getDate());
}

/**
* 返回一个格式化日期字符串
* <p><b>Example: </b><p>
* <p>dateToString("2002-10-4") returns "2002-10-4"<p>
* @param date 日期型变量
* @return “YYYY-MM-DD”格式化日期字符串
*/
function dateToString2(d){
	return  d.getFullYear() +"-"+(d.getMonth()<9?("0"+(d.getMonth()+1)):(d.getMonth()+1)) +"-"+(d.getDate()<10?("0"+d.getDate()):d.getDate());
}

/**
* 将字符串解析成为一个数组，该字符串的头部有返回信息
* <p><b>Example: 未测试，请确认例子的正确性</b><p>
* <p>decodeString("Minim|1^Hzm|2^Yt|3") returns "3，Minim,1,Hzm,2,Yt,3"<p>
* @param strValue 需要解析的字符串,通常是查询数据库返回的结果字符串
* @return 如果执行成功，则返回以记录为行，字段为列的二唯数组，如果执行不成功，则返回false
*/
function decodeString(strValue){
	var i,i1,j,j1;
	var strValue;	//存放服务器端返回的代码数据
	var arrField;
	var arrRecord;
	var arrCode = new Array();	//存放初始化变量时用
	var t_Str;
	try{
		arrRecord = strValue.split(RECORDDELIMITER);	//拆分字符串，形成返回的数组
		t_Str = getStr(arrRecord[0],1,FIELDDELIMITER);
		//如果不为0表示服务器端执行发生错误
		if (t_Str!="0"){
			return false;
		}
		i1=arrRecord.length;
		for (i=1;i<i1;i++){
			arrField  = arrRecord[i].split(FIELDDELIMITER);	//拆分字符串,将每个纪录拆分为一个数组
			j1=arrField.length;
			arrCode[i-1] = new Array();
			for (j=0;j<j1;j++){
				arrCode[i-1][j] = arrField[j];
			}
		}
	}
	catch(ex){
		return false;
	}
	return arrCode;
}

/**
* 将字符串解析成为一个数组，该字符串的头部没有有返回信息
* <p><b>Example: 未测试，请确认例子的正确性</b><p>
* <p>decodeStringNoHead("Minim|1^Hzm|2^Yt|3") returns "Minim,1,Hzm,2,Yt,3"<p>
* @param strValue 需要解析的字符串,通常是查询数据库返回的结果字符串
* @return 如果执行成功，则返回以记录为行，字段为列的二唯数组，如果执行不成功，则返回false
*/
function decodeStringNoHead(strValue){
	var i,i1,j,j1;
	var strValue;	//存放服务器端返回的代码数据
	var arrField;
	var arrRecord;
	var arrCode = new Array();	//存放初始化变量时用
	var t_Str;
	if(strValue==null || strValue=="")
	return false;
	try{
		arrRecord = strValue.split(RECORDDELIMITER);	//拆分字符串，形成返回的数组
		i1=arrRecord.length;
		for (i=0;i<i1;i++){
			arrField  = arrRecord[i].split(FIELDDELIMITER);	//拆分字符串,将每个纪录拆分为一个数组
			j1=arrField.length;
			arrCode[i] = new Array();
			for (j=0;j<j1;j++){
				arrCode[i][j] = arrField[j];
			}
		}
	}
	catch(ex){
		return false;
	}
	return arrCode;
}

//sql语句字符连接
//返回值：
function getConcat(Field1,Field2)
{
    var tDBType = DBType;   //DBType在UsrCheck.jsp中定义的全局变量
    var sqlPart = "";
    if(Field1==null||Field1=="") return sqlPart;
    if(Field2==null||Field2=="") return sqlPart;
    if(tDBType=="SQLSERVER")
    {
    	sqlPart = Field1 + "+" + Field2;
    	return sqlPart;
    }
    if(tDBType=="ORACLE")
    {
    	sqlPart = Field1 + "||" + Field2;
    	return sqlPart;
    }	
    return sqlPart;
}
/**
* 从LDCode取得代码对应的名称
* CodeType:字段类型
* Code:代码
* 如果存在返回名称,反之返回""
*/
function getNameFromLDCodeEx(CodeType,Code,CodeAlias){
	var tCodeType=CodeType.toLowerCase();
	var tCode=Code;

	if(tCode!='' && tCode!=null){
		var arrSelected=new Array();

		var strSQL = "";
		strSQL = "select CodeName from FDCode where CodeType='"+tCodeType+"' and Code='"+tCode+"' and CodeAlias='"+CodeAlias+"'"; 

		var strQueryResult = easyQueryVer3(strSQL,1,0,1);
		arrSelected = decodeEasyQueryResult(strQueryResult,0,1);

		if(arrSelected==null)
			return("");
		else
			return(arrSelected[0][0]);
	}
	else{
		return("");
	}
}

/**
* 从JTCodeList取得代码的级别
* ItemCode:代码
* ItemType:类型
* 如果有子节点返回"Y"，否则反户"N"，如果查询失败返回"F"
*/
function getParentItemCode(ItemCode,ItemType){
	var tItemCode=ItemCode;
	var tItemType=ItemType;

	var arrSelected=new Array();
	var strSQL = "";
	strSQL = "select subflag from JTCodeList where ItemCode='"+tItemCode+"' and ItemType='"+tItemType+"'";
	var strQueryResult = easyQueryVer3(strSQL,1,0,1);
	arrSelected = decodeEasyQueryResult(strQueryResult);

	if(arrSelected==null)
		return "F";
	else
		if(arrSelected[0][0]=="0")
			return "N";
		else
			return "Y";
}
//sql语句获取系统日期时间
//@param: Type   0- 日期 ， 1- 时间
function getSysdatetime(DatetimeType)
{
    var tDBType = DBType;
    var sqlPart = "";
    if(DatetimeType==null||DatetimeType=="") return sqlPart;
    if(tDBType=="SQLSERVER")
    {
    	//日期
    	if(DatetimeType=='0')   
    	{
    	    sqlPart = "convert(char(10),getdate(),20)";
    	    return sqlPart;
    	}
    	//时间
    	if(DatetimeType=='1')   
    	{
    	    sqlPart = "convert(char(8),getdate(),108)";
    	    return sqlPart;
    	}
    }
    if(tDBType=="ORACLE")       
    {
    	//日期
    	if(DatetimeType=='0')   
    	{
    	    sqlPart = "to_char(sysdate,'yyyy-mm-dd')";
    	    return sqlPart;
    	}
    	//时间
    	if(DatetimeType=='1')   
    	{
    	    sqlPart = "to_char(sysdate,'hh24:mm:ss')";
    	    return sqlPart;
    	}
    }	
    return sqlPart;
}
//sql语句字符转换成数字
function getToNumber(Field1)
{
    var tDBType = DBType;
    var sqlPart = "";
    if(Field1==null||Field1=="") return sqlPart;
    if(tDBType=="SQLSERVER")
    {
    	sqlPart = "cast(" + Field1 + " as numeric)";
    	return sqlPart;
    }
    if(tDBType=="ORACLE")
    {
    	sqlPart = "TO_NUMBER(" + Field1 + ")";
    	return sqlPart;
    }	
    return sqlPart;
}
/**
* 通过将span设置为不可见“NONE”隐藏数值
*/
function hideOldValue(){
	spanOldValue.style.display ='none';
}
//对输入域是否是日期的校验，不同的连接字段-，/
function isCodeDate(date){
	var strValue;
	strValue=date.split("/");
	if(strValue.length!=3) return false;
	if(!isInteger(strValue[0]) || !isInteger(strValue[1]) || !isInteger(strValue[2])) return false;
	var intYear=eval(strValue[0]);
	var intMonth=eval(strValue[1]);
	var intDay=eval(strValue[2]);
	if(intYear<0 || intYear>9999 || intMonth<0 || intMonth>12 || intDay<0 || intDay>31) return false;
	return true;
}

function isDateObject(object){
	var strReturn=isDate(object.value);
	if(strReturn=="false"){
		object.value="";
		return false;
	}
	else{
		object.value=strReturn;
		return true;
	}
}
/**
* 对输入域是否是百分数的校验
* <p><b>Example: </b><p>
* <p>isRat("123.1") returns false<p>
* @param strValue 输入数值表达式或字符串表达式
* @return 布尔值（true--是数字, false--不是数字）
*/
function isRat(strValue){

    if ( isNumeric(strValue) )
      if ( eval(strValue)>=0 && eval(strValue)<=100 )
	     return true;
	return false;
}
//有效身份证号判断:正确返回true;否则返回false
function isValidIDNo(tIDNo)
{
	if(tIDNo.length!=15 && tIDNo.length!=18)
		return false;
	return true;
}
/**
* 显示打印窗口，主要是统一打印窗口的样式
* <p><b>Example: </b><p>
* <p>printWindow("../print.jsp", null)<p>
* @param strURL 新窗口的完整路径（URL）或相对路径
* @param strWindowName 指定窗口名，可以为空
* @return 返回新建窗口的句柄
*/
function printWindow(strURL,strWindowName){
	var pageWidth=screen.availWidth-10;
	var pageHeight=screen.availHeight-30;
	if (pageWidth<100){
		pageWidth = 100;
	}
	if (pageHeight<100){
		pageHeight = 100;
	}
	var newWindow = window.open(strURL,strWindowName,'width='+pageWidth+',height='+pageHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
	newWindow.focus();
	return newWindow;
}
/**
* 将空值输入域设为给定值，isMulti表示该域是否为多个输入域
* <p><b>Example: </b><p>
* <p>setEmpty([name1,name2], [Minim, Hzm], 2)<p>
* @param FieldName HTML页面的对象名称
* @param FieldValue 要负给对象的值
* @param isMulti 标志传入的是单个对象还是对象数组
*/
function setEmpty(FieldName,FieldValue,isMulti){
	var i = 0;
	if (!isMulti){
		if (fm.all(FieldName).value == "")
			fm.all(FieldName).value = FieldValue;
	}
	else{
		for(i = 0; i< fm.all(FieldName).length; i++){
			theField = fm.all(FieldName)[i];
			if (trim(theField.value) == "" || eval(theField.value) == 0)
				theField.value = FieldValue;
		}
	}
}
/**
* 给新***代码赋值 --代码维护模块专用onblur=setNewCode(this)
* @param field HTML页面的对象名称
*/
function setNewCode(field){
	if(trim(fm.all("new"+field.name).value)==""){
		fm.all("new"+field.name).value = field.value;
	}
}

/**
* 对图片的显示、隐藏
* @param imgID HTML中可显示图片的对象的ID
* @param stl 控制显示或隐藏的标志，“”为显示，“none”为隐藏
*/
function showImg(imgID,stl){
	document.all(imgID).style.display = stl;
}

/**
* 将保存的数值在指定的坐标（ex,ey）中，通过span显示出来
* @param oldValue 保存的数值
* @param ex X坐标
* @param ey Y坐标
*/
function showOldValue(oldValue,ex,ey){
	spanOldValue.innerHTML = oldValue;
	spanOldValue.style.left=ex;
	spanOldValue.style.top=ey;
	spanOldValue.style.display ='';
}

/**
*showPage函数的扩展
*@param focusMark 要定位的书签，如：'#markName';
*/
function showPageLoc(img,spanID,focusMark){
	if(spanID.style.display==""){
		spanID.style.display="none";
		img.src="../common/images/butCollapse.gif";
	}
	else{
		spanID.style.display="";
		img.src="../common/images/butExpand.gif";
		window.location=focusMark;
	}
}
/**
* 对span的显示only
* <p><b>Example: </b><p>
* <p>showPageOnly(HTML.ImageObject, HTML.SpanObject.ID)<p>
* @param img 显示图片的HTML对象
* @param spanID HTML中SPAN对象的ID
*/
function showPageOnly(img,spanID){
	spanID.style.display="";
	img.src="/Images/piccSh/butExpand.gif";
}

/**
* 对格式字符串进行解析,返回一个关联数组
* <p><b>Example: </b><p>
* <p>splitField("Minim:123|Hzm:456|") returns arrayReturn[Minim]=123;arrayReturn[Hzm]=456<p>
* @param record 格式字符串 FieldName:FieldValue|
* @return 关联数组 array[FieldName]=FieldValue
*/
function splitField(record){
	var arrayField=record.split(FIELDDELIMITER);
	var arrayReturn=new Array();
	var i;
	for(i=0;i<arrayField.length-1;i++){
		var arrayNameValuePair=arrayField[i].split(NAMEVALUEDELIMITER);	//分割出一对域名和域值
		arrayReturn[arrayNameValuePair[0]]=arrayNameValuePair[1];
	}
	return arrayReturn;
}

/**
* 检测页面所有数值,百分数，日期,型输入是否合法.
*页面元素id命名遵从规则：数字用Amt或Num结尾，百分数用Rat或Wgh结尾，日期用Date或Day结尾
*不区分大小写
*要求非空加前缀'!'
* <p><b>Example: </b><p>
* @return 布尔值（true--是数字, false--不是数字）
*/
function verifyCheck(){
	removeAllError();
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var elementName;
	var elementId;
	var NullBlank;
	var curElement;
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++){
		//遍历FORM中的所有ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++){
			
			curElement=window.document.forms[formsNum].elements[elementsNum];
			
			elementName=curElement.name.toUpperCase() ;
			elementId= curElement.id.toUpperCase();
			
			//判断元素是否非空
			NullBlank=NullOrBlank(curElement.value);
			//是否可空
			if (  elementId.indexOf("!")!=-1 )
			{
			   	if (NullBlank)
			     	{   
			     	    if(curElement.className == "codeno")
			     	    addError(elementName+"Name",curElement.title+"不能为空");
			     	    else
			     	    addError(elementName,curElement.title+"不能为空");
			        	curElement.focus();
			        	return false;
			     	}
			     }
			////数字
			if (elementId.lastIndexOf("AMT")>=0||elementId.lastIndexOf("NUM")>=0 )
			{
				if (!NullBlank && !isNumeric(curElement.value) )
				{          addError(elementName,curElement.title+"输入数字有误！");
						curElement.focus();
						return false;
					
				}
			}
			else if (elementId.lastIndexOf("RAT")>=0 ||elementId.lastIndexOf("WGH")>=0){
			//百分数
			if (!NullBlank && !isRat(curElement.value) ){
				         addError(elementName,curElement.title+"输入不是小于100的数字！");
						curElement.focus();
						return false;
				}
			}
		else if (elementId.lastIndexOf("DATE")>=0 || elementId.lastIndexOf("DAY")>=0){
			//日期
			if (!NullBlank && isDate(curElement.value)=="false" ){
					        addError(elementName,curElement.title+"输入有误！");
						curElement.focus();
						return false;
					
				}
				}
			}
		}

	return true;
}

//add by xyw 
//增加显示鼠标标签显示
function showtitle(obj)
{
	obj.title =obj.value;
}

//add by xyw 
//增加显示鼠标标签显示
function showtitleHidden(obj , aa)
{
	obj.title =  aa;
}

//增加js中数字的加减乘除算法
//例如：x=fixMath(值1,x,'+')
function fixMath(m,n,op)   
{ 
    var   a = (m+ " "); 
    var   b = (n+ " "); 
    var   x = 1; 
    var   y = 1; 
    var   c = 1; 
    if(a.indexOf(".")> 0)   { 
        x = Math.pow(10, a.length - a.indexOf(".") - 1); 
    } 
    if(b.indexOf(".")> 0)   { 
        y = Math.pow(10, b.length - b.indexOf(".") - 1); 
    } 
    switch(op) 
    { 
        case '+': 
        case '-': 
            c = Math.max(x,y); 
            m = Math.round(m*c); 
            n = Math.round(n*c); 
            break; 
        case '*': 
            c = x*y;
            m = Math.round(m*x); 
            n = Math.round(n*y); 
            break; 
        case '/': 
            c = Math.max(x,y); 
            m = Math.round(m*c); 
            n = Math.round(n*c); 
            c = 1; 
            break; 
    } 
    return   eval("("+m+op+n+ ")/"+c); 
}

function checkLicenseNo(sLicenseNo)
{
	if(sLicenseNo.toUpperCase()=="0")
	{
	}else
	{
		var tLicenseNo=sLicenseNo.substring(0,2);
		var tProvinceFull = "山东绵阳广安阿坝重庆吉林山西河南广西邛崃南充万源巴中西藏河北江苏自贡泸州内江华蓥贵州陕西香港塔什凉山简阳锡林四川广元遂宁青海云南塔城台湾什邡黑龙湖北浙江崇州攀枝眉山巴音辽宁上海湖南成都绵竹达州宁夏新疆哈密澳门海南内蒙天津广东安徽福建宜宾乐山甘孜北京江西德阳资阳甘肃";
		
		if(tLicenseNo.toUpperCase()=="WJ"||tProvinceFull.indexOf(tLicenseNo) >= 0)//特殊的武警车牌号以Wj开头
	    {
	    }else
	    {
	    	var tLicen1 = sLicenseNo.substring(0,1);
	    	if(!isProvinceOrOther(tLicen1)) {
	    		return false;
	    	}
	    }
	}
	
	return true;
}

function isProvinceOrOther(strValue)
{
	if(strValue==null ||strValue==""){
		return false;	
	} else {
		var tProvince="京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼军甲空午海未北庚沈己兰辛济壬南寅广戌成辰乙丙戍申使";
		if(tProvince.indexOf(strValue) < 0) {
			return false;
		}
	}
	return true;
}

function isABC(strValue)
{
	var tABC="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	var i;
	if(strValue==null ||strValue=="") 
		return false;
	for(i=0;i<strValue.length;i++){
		if(tABC.indexOf(strValue.charAt(i))<0) 
			return false;
	}
	
	return true;
}

function isNo(strValue)
{
	var tNo="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	var i;
	if(strValue==null ||strValue=="") 
		return false;
	for(i=0;i<strValue.length;i++){
		if(tNo.indexOf(strValue.charAt(i))<0) 
			return false;
	}
	
	return true;
}


/**
 * 根据一个日期，计算X年Y月Z日后的日期
 * Example: calDate("2008-10-1","1","1","1") returns "2009-11-2";calDate("2008-10-1","-1","-1","1") returns "2007-9-2"
 * @param baseDate 被处理日期
 * @param year 加减年数
 * @param month 加减月数
 * @param day 加减天数
 * @return retDate 返回baseDate的year年month月day日后的日期
 */
function calDate(baseDate,year,month,day)
{
	var retDate = "";
	if(isDate(baseDate))
	{
		var baseyear = (baseDate).substring(0,4);
		var basemonth = (baseDate).substring(5,7);
		var baseday = (baseDate).substring(8,10);
		
		var baseyear = charToNum(baseyear)+charToNum2(year);
		var basemonth = charToNum(basemonth)+charToNum2(month)-1;
		var baseday = charToNum(baseday)+charToNum2(day);
				
		var tDate = new Date(baseyear,basemonth,baseday);
		baseyear = tDate.getYear();
		basemonth = charToNum(tDate.getMonth())+1;
		baseday = tDate.getDate();
		
		if(numToChar(basemonth).length==1)
		{
			basemonth = "0"+basemonth;
		}
		
		if(numToChar(baseday).length==1)
		{
			baseday = "0"+baseday;
		}
		
		retDate = baseyear+"-"+basemonth+"-"+baseday;
	}
	
	return retDate;
}



function onKeyDown()
{ 
  var blnEevntCancel = false ;   
    
  if(window.event && window.event.altKey 
        && (window.event.keyCode == 8 || window.event.keyCode == 37 || window.event.keyCode == 39 ))
  {   
          blnEevntCancel   =   true   ;   
  }   
  if(window.event.keyCode == 8)   
  {   
  	if(window.event.srcElement.tagName.toUpperCase() == "TEXTAREA")
  	{   
	  if(window.event.srcElement.readOnly == true)   
	  {   
	      blnEevntCancel = true   ;   
	  }   
    }   
  else if(window.event.srcElement.tagName.toUpperCase() == "INPUT" && event.srcElement.type.toUpperCase() == "TEXT")
  {   
	  if(window.event.srcElement.readOnly == true)
	  {   
	      blnEevntCancel =  true   ;   
	  }   
  }   
  else{   
        blnEevntCancel   =   true   ;   
  }   
  }   
  if(blnEevntCancel == true)
  {   
    window.event.cancelBubble = true;   
    window.event.returnValue = false;   
     return  false;   
  }  
  return true; 
}

function _checkContNo(tMasterInscom,tContNoLen)
{
	var sql="select ContNoLenMode from FDInsContNo where MasterInscom='"+tMasterInscom+"'";
	
	var result=easyQueryVer3(sql,1,0,1);
	var resultArr=new Array();
	resultArr=decodeEasyQueryResult(result,0,0);
	if(resultArr!=null)
	{
		if(resultArr[0][0]=="01")
		{
			sql="select * from FDInsContNo where MasterInscom='"+tMasterInscom+"' and ContNoLen='"+tContNoLen+"'";
			
			result=easyQueryVer3(sql,1,0,1);
			if(!result)
			{
				return false;
			}
		}
		else if(resultArr[0][0]=="02")
		{
			sql="select * from FDInsContNo where MasterInscom='"+tMasterInscom+"' and DLen<='"+tContNoLen+"' and UpLen>='"+tContNoLen+"'";
			
			result=easyQueryVer3(sql,1,0,1);
			if(!result)
			{
				return false;
			}
		}
	}
	
	return true;	
}

function isNo2(strValue)
{
	var tNo="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-";
	var i;
	if(strValue==null ||strValue=="") 
		return false;
	for(i=0;i<strValue.length;i++){
		if(tNo.indexOf(strValue.toUpperCase().charAt(i))<0) 
			return false;
	}
	
	return true;
}


function checkMoney( m1, m2){
	if(m1<m2){
		alert("交费金额不能比应收总金额小！");
		return false;
	}
	return true;
	
}