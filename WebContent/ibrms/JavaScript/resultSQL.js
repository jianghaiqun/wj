function ajaxSyncCall(urlStr, paramsStr) {   
    var obj;   
    var value;   
    if (window.ActiveXObject) {   
        obj = new ActiveXObject('Microsoft.XMLHTTP');   
    } else if (window.XMLHttpRequest) {   
        obj = new XMLHttpRequest();   
    }   
    obj.open('POST', urlStr, false);   
    obj.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');   
    obj.send(paramsStr);
    return obj.responseText;
}  


//使用时，需要在fm下创建 <input type="hidden" name="strResult" />
function easyQueryVer3(sql) {
	if (Verify.hasError()) {
		return;
	}
	return ajaxSyncCall("../ibrms/getSqlData.jsp","SQL="+sql);
}
/**
 * 拆分约定格式字符串至二维数组函数
 * @return 二维数组，失败返回NULL
 * 后三项无用，为之前框架使用，此次为移植规则引擎使用
 */
function decodeEasyQueryResult(strResult, notUseEasyQuery, notUseTurnPage, otherTurnPage)
{
    var arrEasyQuery = new Array();
    var arrRecord = new Array();
    var arrField = new Array();
    var recordNum, fieldNum, i, j;
    var cTurnPage;
    if (typeof(otherTurnPage)=="object")
    {
        cTurnPage = otherTurnPage;
    }
    else
    {
        try
        {
            cTurnPage = this;
        }
        catch(e)
        {}
    }
    if (typeof(strResult) == "undefined" || strResult == "" || strResult == false)
    {
        return null;
    }

    //公用常量处理，增强容错性
    if (typeof(RECORDDELIMITER) == "undefined") RECORDDELIMITER = "^";
    if (typeof(FIELDDELIMITER) == "undefined") FIELDDELIMITER = "|";
    try
    {
        arrRecord = strResult.split(RECORDDELIMITER);   //拆分查询结果，得到记录数组
        //特殊处理，查询结果字符串首位存有所有满足条件记录总数信息
        if ((typeof(notUseTurnPage)=="undefined" || notUseTurnPage==0) && typeof(cTurnPage)=="object")
        {
            cTurnPage.queryAllRecordCount = arrRecord[0].substring(arrRecord[0].indexOf(FIELDDELIMITER) + 1);
        }
        if(typeof(notUseEasyQuery)=="undefined" || notUseEasyQuery=="" || notUseEasyQuery==0)
        {
            if (arrRecord[0].substring(arrRecord[0].indexOf(FIELDDELIMITER) + 1) != "" && arrRecord[0].substring(arrRecord[0].indexOf(FIELDDELIMITER) + 1) == 0)
            {
                return null;
            }
            arrRecord.shift();
        }
        recordNum = arrRecord.length;
//      for(i=0; i<recordNum; i++)
        var i=0;
        while(i<recordNum)
        {
            arrField = arrRecord[i].split(FIELDDELIMITER);  //拆分记录，得到字段数组
            fieldNum = arrField.length;
            arrEasyQuery[i] = new Array();
//          for(j=0; j<fieldNum; j++)
            var j=0;
            while(j<fieldNum)
            {
                arrEasyQuery[i][j] = arrField[j].trim();   //形成以行为记录，列为字段的二维数组
                j++;
            }
            i++;
        }

    }
    catch(ex)
    {
        alert("拆分数据失败！错误原因是：" + ex);
        return null;
    }
    //配合翻页控制进行显示数据处理
    if ((typeof(notUseTurnPage)=="undefined" || notUseTurnPage==0) && typeof(cTurnPage)=="object" && cTurnPage.useSimulation==0)
    {
        var arrQueryResultNum = arrEasyQuery.length;
//      for (i=cTurnPage.dataBlockNum; i<arrQueryResultNum; i++)
        var i=cTurnPage.dataBlockNum;
        while(i<arrQueryResultNum)
        {
            arrEasyQuery.pop();
            i++;
        }
    }
    else if ((typeof(notUseTurnPage)=="undefined" || notUseTurnPage==0) && typeof(cTurnPage)=="object" && cTurnPage.useSimulation==1)
        {
            cTurnPage.blockPageNum = cTurnPage.queryAllRecordCount / cTurnPage.pageLineNum;
        }
    return arrEasyQuery;
}
