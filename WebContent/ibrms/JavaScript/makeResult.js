var rowCountR = 0; // 定制逻辑中行的数量
var isMenuUnfoldedR = false; // "如果"是否在被点击之后展开下拉菜单
var preEventNodeR = null; // 触发产生下拉框事件的节点
var spanNodeEnabledR = true;// 节点能否响应鼠标点击事件的标志
var InputNodeEnabledR = true;
// 用于标识相应节点所要携带的数据结构
var BOMIndex = new Array('id', 'EnName', 'ChName');
var BOMItemIndex = new Array('id', 'EnName', 'ChName', 'MatchType', 'Source',
		'isHierarchical');
var OperatorIndex = new Array('id', 'EnName', 'ChName', 'MatchType',
		'ResultType', 'ParaType', 'IsNextOperator');
var LinkIndex = new Array('id', 'EnName', 'ChName');
var ParenIndex = new Array('id', 'EnName', 'ChName');
var InputIndex = new Array('id', 'EnName', 'ChName','TableValue');
// //连接型运算符数组
var LinkArrayR = new Array();

// 表示"如果"后面是否有规则
var hasRuleR = false;
var hasResult = false;
// 后台查询缓存
var queryCacheR = new Array();

var SQLStatementR;// 存储规则的SQL
var CreateTableR;// 存储建表语句的SQL
var ViewParaR;// 存储规则反显示所需要的参数
var BOMSArrayR = new Array();// 存储规则所用的BOM
var SQLParaArrayR = new Array();// 存储规则反解析的参数
var RuleDesInChR;// 存储规则的中文名
var ColumnHeadArrayR = new Array();// 存储决策表列头所要显示的中文名
var ColumnMetasChArrayR = new Array();// 存储单列规则中文的的数组
var ColumnDataTypeArrayR = new Array();// 存储决策表列的数据类型
var TableColumnNameArrayR = new Array();// 存储DT表中的列明，在决策表中用于列的Index
var ColumnMultipleArrayR = new Array();// 存储决策表所的运算符是否为“其中之一”
var BaseColumnArrayR = new Array();// 存储决策表的列是否为基础词条
var MetaNodeChNameArrayR = new Array();// 存储扫描到节点的中文名
var spanNodeArrayR = new Array();// 存储规则拼写中扫描到的节点
var InputNodeArrayR = new Array();// 存储规则定制逻辑中Input节点
var BaseBOMItemSourceArrayR = new Array();
var InputNodesR = new Array();
var DTColumnRecurArrayR = new Array();// 存储建表过程中列名重复的次数

function initI18nR()
{
	LinkArrayR[0] = new Array("and", ("并且"));
	LinkArrayR[1] = new Array("or", ("或者"));
}
function hightLightR() {
	// 节点在数据输入时不响应操作
	if (!spanNodeEnabledR) {
		return;
	}
	// 节点字体变成斜体，鼠标变成手型，字体闪烁。
	else {
		var ob = event.srcElement;
		ob.style.fontStyle = "italic";
		ob.style.cursor = 'hand';
		ob.style.textDecoration = 'blink';
	}
}

function normalLightR() {
	// 节点在数据输入时不响应操作
	if (!spanNodeEnabledR) {
		return;
	}
	// 节点字体变成正常体，鼠标变成默认型，不闪烁。
	else {
		var ob = event.srcElement;
		ob.style.fontStyle = "normal";
		ob.style.cursor = 'default';
		ob.style.textDecoration = '';
	}
}

function  MenuUnfoldedR(){
	isMenuUnfoldedR = true;
	hasRuleR = true;
}

function showResult() {
	if (spanNodeEnabledR) {
		if (!isMenuUnfoldedR)// 菜单未打开
		{
			unFoldMenuR(); // 展开菜单
			isMenuUnfoldedR = true;// 设置记录标识为打开状态
		} else {
			foldMenuR(); // 折叠菜单
			isMenuUnfoldedR = false;// 设置记录标识为折叠状态
		}
	}
}

function unFoldMenuR() {
	// 创建“DIV”节点、“新条件”节点、”增加条件“节点

	var nextNode = document.getElementById("Result").nextSibling;
	if (hasRuleR) {
		nextNode.style.display = '';
	} else {
		var divNode = document.createElement("div");
		divNode.setAttribute("id", "ResultZone");
		var newLine = createNewAditionLineR();
		var buttonNode = createAddLineButtonR();
		// 获取当前节点的父节点以及下一个兄弟节点
		var pareNode = event.srcElement.parentNode;
		var nextNode = event.srcElement.nextSibling;
		// 将“条件”节点以及“增加条件”节点插入到“form”节点之后

		divNode.appendChild(newLine);
		divNode.appendChild(buttonNode);
		// 将form节点插入到当前节点之后
		pareNode.insertBefore(divNode, nextNode);
		hasRuleR = true;
	}
}

// 折叠菜单函数
function foldMenuR() {
	// 获取当前节点的父节点和下一个兄弟节点
	var nextNode = event.srcElement.nextSibling;
	nextNode.style.display = 'none';
}

// 创建新条件函数
function createNewAditionLineR() {
	var divNode = document.createElement("div");// 创建一个"div"节点(一个条件就在一行中,以"div"标识)
	divNode.align="left"; 
	// 当条件不是第一个条件的时候，添加连接词
	//Dialog.alert(rowCountR);
	if (rowCountR > 0) {
		//结果行 不要连接词
		var linkNode = createOriginSpanNodeR('Link', false);
		divNode.appendChild(linkNode);
	} else {
		var spanNode = createOriginSpanNodeR('Spacer');
		divNode.appendChild(spanNode);
		spanNode = createOriginSpanNodeR('Spacer');
		divNode.appendChild(spanNode);
		spanNode = createOriginSpanNodeR('Spacer');
		divNode.appendChild(spanNode);
	}
	// 创建非库中节点
	var spacerNode = createOriginSpanNodeR('Spacer');
	divNode.appendChild(spacerNode);
	var spanNode = createOriginSpanNodeR('BOM', false);
	
	divNode.appendChild(spanNode);

	var spacerNode = createOriginSpanNodeR('Spacer');
	divNode.appendChild(spacerNode);

	var buttonNode = createDeleteButtonNodeR(); // 创建一个可以删除本条件的按钮节点
	divNode.appendChild(buttonNode);
	rowCountR++;
	return divNode;
}
// 创建一个删除本条件的按钮
function createDeleteButtonNodeR() {
	var buttonNode = document.createElement("input");
	// 添加Button节点的鼠标响应事件
	buttonNode.setAttribute("type", "button");
	buttonNode.setAttribute("id", "DelButton");
	buttonNode.onclick = function() {
		deleteLineR();
	}
	buttonNode.style.background = 'url(./resources/img/delete.jpg)';
	buttonNode.style.width = '35';
	buttonNode.style.height = '32';
	return buttonNode;
}

function deleteLineR() {
	if (!spanNodeEnabledR) {
		return;
	}
	var fatherNode = event.srcElement.parentNode;

	var grandNode = fatherNode.parentNode;
	destroyMenuR();

	grandNode.removeChild(fatherNode);
	rowCountR--;
	if (rowCountR > 0) {
		var firstLine = grandNode.firstChild;
		var linkNode = firstLine.firstChild;
		if (linkNode.id == 'Link') {
			var spanNode = createOriginSpanNodeR("Spacer");
			firstLine.replaceChild(spanNode, linkNode);
		}
		if(!linkNode.id){
			linkNode = linkNode.nextSibling;
			if (linkNode.id == 'Link') {
				var spanNode = createOriginSpanNode("Spacer");
				firstLine.replaceChild(spanNode, linkNode);
			}
		}
	}

}
// 创建一个能够增加一个新条件的按钮
function createAddLineButtonR() {
	var buttonNode = document.createElement("input");
	// 添加Button节点的鼠标响应事件
	buttonNode.setAttribute("type", "button");
	buttonNode.setAttribute("id", "AddButton");
	buttonNode.onclick = function() {
		addNewLineR();
	}
	buttonNode.style.background = 'url(./resources/img/add.jpg)';
	buttonNode.style.width = '35';
	buttonNode.style.height = '32';
	return buttonNode;
}

// 增加一个新条件
function addNewLineR() {
	if (!spanNodeEnabledR) {
		return;
	}
	var divNode = createNewAditionLineR();
	var ob = event.srcElement;
	var fatherNode = ob.parentNode;
	fatherNode.insertBefore(divNode, ob);
}
function disableInputNodesR() {
	InputNodeEnabledR = true;
}

function disableSpanNodesR() {
	spanNodeEnabledR = false;// 节点能否响应鼠标点击事件的标志

}

function enableInputNodesR() {
	InputNodeEnabledR = false;
}

function enableSpanNodesR() {
	spanNodeEnabledR = true;// 节点能否响应鼠标点击事件的标志

}
/*
 * 创建Span节点的函数是本组件的重点 Span节点的数据结构如下： id:
 * 用于标识节点的类型，例如BOM，BOMItem，Operator，Pathesis，Input，Link EnName：用于记录节点的英文名
 * ChName: 用于记录节点的中文名 MatchType: 用于记录运算符与词条的匹配类型， ParaType: 用于记录运算符后携带的参数类型，
 * Source：用于记录基础词条的取值来源， ResultType：用于记录运算符的运算结果类型
 * IsNextOperator：用于记录运算符后面能否接运算符的标志 isMenu是节点是否为下拉菜单中节点的标志，true表示“是”，false表示“否”
 */
function createSpanNodeR(paraArray, isMenu) {

	var spanNode = document.createElement("span");
	spanNode.style.float = "left";
	spanNode.align="left";
	//spanNode.style.cursor = 'hand';
	//spanNode.style.fontSize = '18px';

	var nodeType = '';
	var displayText = '';

	// 添加"spanNode"节点的属性,比如鼠标响应事件
	if (!(!!paraArray)) {
		Dialog.alert(("创建节点传递的参数出错！"));
		return;
	}
	
	for ( var i = 0, length = paraArray.length; i < length; i++) {
		spanNode.setAttribute(paraArray[i].attribute, paraArray[i].value);
		if (paraArray[i].attribute == 'id')
			nodeType = paraArray[i].value;
		if (paraArray[i].attribute == 'ChName') {
			displayText = paraArray[i].value;
		}
	}

	if (!isMenu) // 判断
	{
		// 如果是
		if (nodeType != 'Input' && nodeType != 'AddInput'
				&& nodeType != "Delete") {
			
			spanNode.onmousedown = function() {
				popMenuR();
			}
		} else if (nodeType == 'Input') {
			spanNode.onmousedown = function() {
				//modify by lilei 只能通过决策表的形式录入
				changeToInputR();
			}
		} else if (nodeType == 'AddInput') {
			spanNode.onclick = function() {
				addInput();
			}
		} else if (nodeType == "Delete") {
			spanNode.onclick = function() {
				deleteInput();
			}
		}
	} else {
		spanNode.onmousedown = function() {
			handleClickR();
		}
	}
	// 创建并添加"Text"内容
	if (spanNode.id == "Spacer") {
		spanNode.innerHTML = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp';
	} else {
		spanNode.onmouseout = function() {
			normalLightR();
		}
		spanNode.onmouseover = function() {
			hightLightR();
		}
		
		spanNode.innerHTML = displayText;
	}
	return spanNode;
}

function addInput() {
	if (!InputNodeEnabledR)
		return;
	var srcNode = event.srcElement;
	var pareNode = srcNode.parentNode;

	if (pareNode && pareNode.id) {
		if (pareNode.id == "InputNodesR") {
			var spanNode = createOriginSpanNodeR("comma");
			pareNode.insertBefore(spanNode, srcNode);
			spanNode = createOriginSpanNodeR("Input");
			pareNode.insertBefore(spanNode, srcNode);
		}
	}

}
function deleteInput() {
	var obNode = preEventNodeR;
	var pareNode = obNode.parentNode;
	if (pareNode && pareNode.id == "InputNodesR") {
		var firstInput = pareNode.firstChild;

		while (firstInput.id != "Input") {
			firstInput = firstInput.nextSibling;
		}
		if (firstInput == obNode) {
			Dialog.alert(("不能删除第一个节点！"));
			return;
		} else {
			pareNode.removeChild(obNode.previousSibling);
			pareNode.removeChild(obNode);
		}
	}
}
function popMenuR() {
	// 记录被点击的源节点
	preEventNodeR = event.srcElement;
	
	var spanArray = new Array();
	var clickedNode = event.srcElement;
	// 判断鼠标被点击的左右键
	var mouseButton = event.button;
	// 判断被点击节点的类型
	var Id = clickedNode.id;
	if (!Id || Id == 'Spacer') {
		return;
	}
	// 处于数据输入状态，不响应鼠标事件
	if (!spanNodeEnabledR) {

		if (Id == "Input" && mouseButton == 2) {

			var pareNode = preEventNodeR.parentNode;
			var firstInputNode = pareNode.firstChild;
			while (firstInputNode.id != "Input") {
				firstInputNode = firstInputNode.nextSibling;
			}
			if (firstInputNode == preEventNodeR) {
				return;
			}
			var spanNode = createOriginSpanNodeR("Delete");
			spanArray[spanArray.length] = spanNode;
		} else
			return;
	} else {
		if (mouseButton == 1)// 鼠标左击
		{
			if (Id == 'BOM') {
				spanArray = prepareMenu_LeftR('BOM');
			} else if (Id == 'BOMItem')
				spanArray = prepareMenu_LeftR('BOMItem');
			else if (Id == 'Operator')
				spanArray = prepareMenu_LeftR('Operator');
			else if (Id == 'Link')
				spanArray = prepareMenu_LeftR('Link');
		} else if (mouseButton == 2)// 鼠标右击
		{
			if (Id == 'BOM')
				spanArray = prepareMenu_RightR('BOM');
			else if (Id == 'BOMItem')
				spanArray = prepareMenu_RightR('BOMItem');
			else if (Id == 'Operator') {
				//modify by lilei 为了处理删除的情况
				//if (preEventNodeR.ParaType == "Null"
				//		|| preEventNodeR.ParaType == "Null") {
					spanArray = prepareMenu_RightR('Operator');
				//}
			} else if (Id == 'Input')
				spanArray = prepareMenu_RightR('Input');
			else if (Id == 'Left_Paren')
				spanArray = prepareMenu_RightR('Left_Paren');
			else if (Id == 'Right_Paren')
				spanArray = prepareMenu_RightR('Right_Paren');
			else if (Id == 'Link')
				spanArray = prepareMenu_RightR('Link');
			else if (Id == 'InputRightScope')
				spanArray = prepareMenu_RightR('InputRightScope');
		}
	}
	displayMenuR(spanArray);
	document.oncontextmenu = function() {
		if (event.srcElement == preEventNodeR)
			return false;
	}
}

function displayMenuR(spanArray) {
	if (spanArray == null || spanArray.length == 0) {
		return;
	}
	var position_x = preEventNodeR.offsetLeft+20;//此处因为框架问题+20，正常是不需要加的
	var position_y = preEventNodeR.offsetTop + preEventNodeR.offsetHeight+30;//此处因为框架问题+30，正常是不需要加的

	var disNode = document.getElementById("display");

	while (disNode.firstChild) {
		disNode.removeChild(disNode.firstChild);
	}

	for ( var i = 0; i < spanArray.length; i++) {

		disNode.appendChild(spanArray[i]);

		var brNode = document.createElement('br');
		brNode.clear = "left";
		disNode.appendChild(brNode);
	}
	disNode.style.left = position_x;
	disNode.style.top = position_y;
	disNode.style.zIndex = "10";
	disNode.style.display = "";

	document.onclick = function() {

		var srcNode = event.srcElement;
		if (srcNode != preEventNodeR && srcNode != disNode) {
			destroyMenuR();
		}
	}
}

function changeToInputR() {

	preEventNodeR = event.srcElement;
	var clickedNode = event.srcElement;
	var mouseButton = event.button;
	// 将其它Input节点转化成Span节点
	convertInputsToSpansR();
	if (mouseButton == 1) {
		if (!InputNodeEnabledR)// '请输入一个值'节点在规则逻辑定制过程中不响应鼠标左击操作
		{
			return;
		} else // '请输入一个值'节点在数据录入过程中响应鼠标左击事件
		{
			var BOMItemNode = null;
			var OperatorNode = null;
			var currNode = clickedNode;
			if (!!currNode.parentNode.id
					&& currNode.parentNode.id == "InputNodesR") {
				currNode = currNode.parentNode;
			}
			while (!!currNode) {
				if (currNode.id == "BOMItem") {
					BOMItemNode = currNode;
					break;
				} else if (currNode.id == "Operator") {
					OperatorNode = currNode;
	
				}
				currNode = currNode.previousSibling;
			}

			if (!!BOMItemNode) {
				// 如果输入值是基础词条
				if (!!BOMItemNode.Source) {
					var InputMenu = new Array();
					var strSQL = BOMItemNode.Source;
					InputMenu = getInputSource(strSQL);
					var spanArray = prepareBaseMenuR(InputMenu);
					displayMenuR(spanArray);
				}
				// 如果输入值不是基础词条
				else {
					//modify by lilei 不让界面录值
					//var dataType = OperatorNode.ParaType;
					//displayInputR(clickedNode, dataType);
					/*
					 * preEventNodeR.innerHTML=""; field =new
					 * Ext.form.DateField({renderTo:preEventNodeR , format:
					 * 'Y-m-d H:i:s',width:150 });
					 */
				}
			}
		}
	} else {
		popMenuR();
	}
}

function displayInputR(obNode, dataType) {

	var fatherNode = obNode.parentNode;

	var spanNode = document.createElement("span");
	spanNode.id = "Input";

	var inputNode = document.createElement('input');
	inputNode.setAttribute('type', 'input');
	inputNode.setAttribute('id', 'Input');
	inputNode.setAttribute('Editable', 'Yes');
	inputNode.setAttribute('size', '18');

	if (!!obNode.EnName) {
		inputNode.value = obNode.EnName;
	}
	inputNode.onkeydown = function() {
		handleKeyR(this);
	}
	fatherNode.replaceChild(inputNode, obNode);
	inputNode.focus();
	inputNode.select();

	document.onclick = function() {
		convertInputsToSpansR();
	}
}
function handleKeyR(inputNode) {
	getInputNodesR();

	if (event.keyCode == 13) {
		convertInputsToSpansR();
	}
}
function getIndexOf(inputNode) {
	for ( var i = 0; i < InputNodesR.length; i++) {
		if (InputNodesR[i] == inputNode) {
			return i;
		}
	}
}
function convertInputsToSpansR() {
	var spanNode;
	var type;
	getInputNodesR();
	for ( var i = 0; i < InputNodesR.length; i++) {
		spanNode = InputNodesR[i];
		type = ColumnDataTypeArrayR[i];
		if (spanNode.id == "Input") {
			changInputToSpanR(spanNode, type);
		} else {
			var childNode = spanNode.firstChild;
			while (childNode) {
				if (childNode.id == "Input", type) {
					changInputToSpanR(childNode);
				}
				childNode = childNode.nextSibling;
			}
		}
	}

}
function changInputToSpanR(inputNode, type) {
	if (!!inputNode.Editable) {
		if (inputNode.Editable == "Yes") {

			var numRegExp = /^(([1-9][0-9]*)|(0)|((([1-9][0-9]*)|[0-9])\.[0-9]+))$/;
			var dateRegExp = /^\d{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([12][0-9])|31) (([01][0-9])|(2[0-3])):([0-5][0-9]):([0-5][0-9])$/
			var spanNode = null;
			if (inputNode.value != '') {
				if (type == "Number") {
					if (!numRegExp.test(inputNode.value)) {
						Dialog.alert(("请输入正确的数字格式！"));
						return;
					}

				} else if (type == "Date") {
					if (!dateRegExp.test(inputNode.value)) {
						Dialog.alert(("请输入正确的日期格式！"));
						return;
					}
				}
				var paraArray = new Array();
				paraArray[0] = {
					attribute :'id',
					value :'Input'
				};
				paraArray[1] = {
					attribute :'EnName',
					value :inputNode.value
				};
				paraArray[2] = {
					attribute :'ChName',
					value :inputNode.value
				};
				paraArray[3] = {
						attribute :'TableValue',
						value :''
					};
				spanNode = createSpanNodeR(paraArray, false);
			} else {
				spanNode = createOriginSpanNodeR('Input');
			}

			try {
				var fatherNode = inputNode.parentNode;
				fatherNode.replaceChild(spanNode, inputNode);
			} catch (e) {

			}
		}
	}
}

function handleClickR()// 用下拉框中的节点取代事件源节点，并在必要时生成一些新节点
{
	var srcNode = event.srcElement;

	if (srcNode.EnName != preEventNodeR.EnName) {
		// 将新节点信息取代原事件节点信息
		var fatherNode = preEventNodeR.parentNode;

		if (srcNode.id == "BOM") {
			deleteNodesAfter(preEventNodeR);
			appendNewNodesR('BOM', preEventNodeR, srcNode);
		} else if (srcNode.id == "BOMItem") {
			deleteNodesAfter(preEventNodeR);

			appendNewNodesR('BOMItem', preEventNodeR, srcNode);
		} else if (srcNode.id == "Operator") {
			deleteNodesAfter(preEventNodeR);
			appendNewNodesR('Operator', preEventNodeR, srcNode);
		} else if (srcNode.id == 'Link') {
		}
		if (srcNode.id == 'Input') {
			srcNode.onmousedown = function() {
				changeToInputR();
			}
		} else {
			srcNode.onmousedown = function() {
				popMenuR();
			}
		}
		fatherNode.replaceChild(srcNode, preEventNodeR);
		destroyMenuR();
	}
}

function destroyMenuR() {
	var disNode = document.getElementById("display");
	if (disNode.style.display != 'none') {
		while (disNode.firstChild) {
			disNode.removeChild(disNode.firstChild);
		}

		disNode.style.display = "none";
	}
}
function deleteNodesAfter(ob) {
	var fromNode = ob;
	var fatherNode = fromNode.parentNode;
	var lastNode = fatherNode.lastChild;
	// 需要针对实际情况进行修正
	while (fromNode.nextSibling != lastNode
			&& fromNode.nextSibling.id != 'Link'
			&& fromNode.nextSibling.id != 'Left_Paren'
			&& fromNode.nextSibling.id != 'Right_Paren') {
		fatherNode.removeChild(fromNode.nextSibling);
	}
}

function appendNewNodesR(id, preEventNodeR, srcNode) {
	var Id = id;
	var srNode = srcNode;
	if (Id == 'BOM') {
		if (preEventNodeR.nextSibling.id != 'BOMItem') {
			var fatherNode = preEventNodeR.parentNode;
			// 创建一个BOMItem节点
			var spanNode = null;
			spanNode = createOriginSpanNodeR('BOMItem');
			fatherNode.insertBefore(spanNode, preEventNodeR.nextSibling);

			spanNode = createOriginSpanNodeR('Spacer');
			fatherNode.insertBefore(spanNode, preEventNodeR.nextSibling);
		}
	} else if (Id == 'BOMItem') {
		var fatherNode = preEventNodeR.parentNode;
		
		var preNode = preEventNodeR.previousSibling;
		var createNext = true;
		while (preNode && preNode.id != 'Link') {
			if (preNode.id == 'Operator' && preNode.ResultType == 'Boolean') {
				createNext = false;
				break;
			}
//			if (preNode.id == 'Operator') {
//				createNext = false;
//				break;
//			}
			preNode = preNode.previousSibling;
		}
		// 创建一个BOMItem节点
		if (createNext) {
			var spanNode = null;
			spanNode = createOriginSpanNodeR('Operator');
			fatherNode.insertBefore(spanNode, preEventNodeR.nextSibling);
			spanNode = createOriginSpanNodeR('Spacer');
			fatherNode.insertBefore(spanNode, preEventNodeR.nextSibling);
		}
	} else if (Id == 'Operator') {
		var spanNode = null;
		var fatherNode = preEventNodeR.parentNode;
		if (srNode.IsNextOperator == 0) {
			return;
		}

//		else if (srNode.IsNextOperator == 1) {
//			spanNode = createOriginSpanNodeR('Operator');
//			fatherNode.insertBefore(spanNode, preEventNodeR.nextSibling);
//			spanNode = createOriginSpanNodeR('Spacer');
//			fatherNode.insertBefore(spanNode, preEventNodeR.nextSibling);
//		} 
		else if (srNode.IsNextOperator == 1) {
//			if (srNode.ResultType == 'Boolean'||srNode.ResultType == 'Null') {
				spanNode = createOriginSpanNodeR('Input');
				fatherNode.insertBefore(spanNode, preEventNodeR.nextSibling);
				spanNode = createOriginSpanNodeR('Spacer');
				fatherNode.insertBefore(spanNode, preEventNodeR.nextSibling);
//			}
//			else {
//				spanNode = createOriginSpanNodeR('BOM');
//				fatherNode.insertBefore(spanNode, preEventNodeR.nextSibling);
//				spanNode = createOriginSpanNodeR('Spacer');
//				fatherNode.insertBefore(spanNode, preEventNodeR.nextSibling);
//			}
		}

	}
}

function createInputNodes() {

	var InputNodesR = createOriginSpanNodeR('InputNodes');

	var spanNode = createOriginSpanNodeR('InputLeftScope');
	InputNodesR.appendChild(spanNode);
	spanNode = createOriginSpanNodeR('Spacer');
	InputNodesR.appendChild(spanNode);
	spanNode = createOriginSpanNodeR('Input');
	InputNodesR.appendChild(spanNode);
	spanNode = createOriginSpanNodeR("AddInput");
	InputNodesR.appendChild(spanNode);
	spanNode = createOriginSpanNodeR('Spacer');
	InputNodesR.appendChild(spanNode);
	spanNode = createOriginSpanNodeR('InputRightScope');

	InputNodesR.appendChild(spanNode);
	return InputNodesR;

}

function createOriginSpanNodeR(id) {
	var reSpanNode = null;
	var paraArray = new Array();
	if (id == 'BOM') {
		paraArray[0] = {
			attribute :'id',
			value :'BOM'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :("请选择对象")
		};
	} else if (id == 'BOMItem') {
		paraArray[0] = {
			attribute :'id',
			value :'BOMItem'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :("的")
		};
	} else if (id == 'Operator') {
		paraArray[0] = {
			attribute :'id',
			value :'Operator'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :("请选择操作符")
		};
	} else if (id == 'Input') {
		paraArray[0] = {
			attribute :'id',
			value :'Input'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :("请输入值")
		};
		paraArray[2] = {
			attribute :'EnName',
			value :''
		};
		paraArray[3] = {
				attribute :'TableValue',
				value :''
		};

	} else if (id == 'Link') {
		paraArray[0] = {
			attribute :'id',
			value :'Link'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :("并且")
		};
		paraArray[2] = {
			attribute :'EnName',
			value :'and'
		};
	} else if (id == 'Left_Paren') {
		paraArray[0] = {
			attribute :'id',
			value :'Left_Paren'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :'('
		};
		paraArray[2] = {
			attribute :'EnName',
			value :'('
		};
	} else if (id == 'Right_Paren') {
		paraArray[0] = {
			attribute :'id',
			value :'Right_Paren'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :')'
		};
		paraArray[2] = {
			attribute :'EnName',
			value :')'
		};
	} else if (id == 'Spacer') {
		paraArray[0] = {
			attribute :'id',
			value :'Spacer'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :''
		};
	} else if (id == 'InputRightScope') {
		paraArray[0] = {
			attribute :'id',
			value :'InputRightScope'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :'}'
		};
		paraArray[2] = {
			attribute :'EnName',
			value :'}'
		};
	} else if (id == 'InputLeftScope') {
		paraArray[0] = {
			attribute :'id',
			value :'InputLeftScope'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :'{'
		};
		paraArray[2] = {
			attribute :'EnName',
			value :'{'
		};
	} else if (id == 'InputNodes') {
		paraArray[0] = {
			attribute :'id',
			value :'InputNodes'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :''
		};
		paraArray[2] = {
			attribute :'EnName',
			value :''
		};
	} else if (id == "AddInput") {
		paraArray[0] = {
			attribute :'id',
			value :'AddInput'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :'&nbsp;&nbsp;'
		};
		paraArray[2] = {
			attribute :'EnName',
			value :'+'
		};
	} else if (id == "comma") {
		paraArray[0] = {
			attribute :'id',
			value :'comma'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :'&nbsp;&nbsp;'
		};
		paraArray[2] = {
			attribute :'EnName',
			value :';'
		};
	} else if (id == "Delete") {
		paraArray[0] = {
			attribute :'id',
			value :'Delete'
		};
		paraArray[1] = {
			attribute :'ChName',
			value :("删除")
		};
		paraArray[2] = {
			attribute :'EnName',
			value :'Delete'
		};
	}

	reSpanNode = createSpanNodeR(paraArray, false);
	return reSpanNode;
}

function prepareMenu_LeftR(id) {
	var Id = id;
	var reSpanArray = new Array();
	var spanNode = null;
	
	if (Id == 'BOM') {
		var BOMArray = getBOMArray();
		var paraArray = new Array();
		if (BOMArray != null) {
			paraArray[0] = {
				attribute :'id',
				value :'BOM'
			};
			for ( var i = 0, length = BOMArray.length; i < length; i++) {
				for ( var j = 0, len = BOMArray[i].length; j < len; j++) {
					paraArray[j + 1] = {
						attribute :BOMIndex[j + 1],
						value :BOMArray[i][j]
					};
				}
				
				spanNode = createSpanNodeR(paraArray, true);
				reSpanArray[reSpanArray.length] = spanNode;
			}
		} else {
			Dialog.alert(("系统出错：BOM获取不成功！"));
		}
	} else if (Id == 'BOMItem') {
		var infArray = getNodeInformationR(preEventNodeR, 'BOMItem');

		var BOMItemArray = new Array();
		BOMItemArray = getBOMItemArray(infArray);

		var paraArray = new Array();

		if (BOMItemArray != null) {
			paraArray[0] = {
				attribute :'id',
				value :'BOMItem'
			};
			for ( var i = 0, length = BOMItemArray.length; i < length; i++) {
				for ( var j = 0, len = BOMItemArray[i].length; j < len; j++) {
					paraArray[j + 1] = {
						attribute :BOMItemIndex[j + 1],
						value :BOMItemArray[i][j]
					};
				}
				spanNode = createSpanNodeR(paraArray, true);
				reSpanArray[reSpanArray.length] = spanNode;
			}
		}
	} else if (Id == 'Operator') {
		var infArray = getNodeInformationR(preEventNodeR, 'Operator');

		var OperatorArray = new Array();
		OperatorArray = getOperatorArrayR(infArray);

		var paraArray = new Array();

		if (OperatorArray != null) {
			paraArray[0] = {
				attribute :'id',
				value :'Operator'
			};
			for ( var i = 0, length = OperatorArray.length; i < length; i++) {
				for ( var j = 0, len = OperatorArray[i].length; j < len; j++) {
					paraArray[j + 1] = {
						attribute :OperatorIndex[j + 1],
						value :OperatorArray[i][j]
					};
				}
				spanNode = createSpanNodeR(paraArray, true);
				reSpanArray[reSpanArray.length] = spanNode;
			}
		}
	} else if (Id == 'Link') {
		var paraArray = new Array();
		if (LinkArrayR != null) {
			for ( var i = 0, length = LinkArrayR.length; i < length; i++) {
				paraArray[0] = {
					attribute :'id',
					value :'Link'
				};
				for ( var i = 0, length = LinkArrayR.length; i < length; i++) {
					for ( var j = 0, len = LinkArrayR[i].length; j < len; j++) {
						paraArray[j + 1] = {
							attribute :LinkIndex[j + 1],
							value :LinkArrayR[i][j]
						};
					}
					spanNode = createSpanNodeR(paraArray, true);
					reSpanArray[reSpanArray.length] = spanNode;
				}
			}
		}

	}
	return reSpanArray;

}
/*
 * 01代表增加左括号， 02代表增加右括号， 03代表删除节点本身 04代表将BOM转换成Input 05代表将Input转换成BOM 06代表增加运算符
 * 07代表增加条件 08代表增加Input节点 09增加BOM
 */
function prepareMenu_RightR(id) {
	var Id = id;
	var reArray = new Array();
	var menuOption = null;

	var obNode = preEventNodeR;

	if (Id == 'BOM') {
		// 添加'('
		menuOption = createMenuOptionR('01');
		reArray[reArray.length] = menuOption;
		// 删除BOM、将BOM转换成Input
		var preNode = obNode.previousSibling;
		var isFirstBOM = true;
		while (preNode && preNode.id != 'Link') {
			if (preNode.id == 'BOM') {
				isFirstBOM = false;
				break;
			}
			preNode = preNode.previousSibling;
		}
		if (!isFirstBOM) {
			menuOption = createMenuOptionR('03');
			reArray[reArray.length] = menuOption;

			menuOption = createMenuOptionR('04');
			reArray[reArray.length] = menuOption;
		}
	} else if (Id == 'BOMItem') {
		// 添加‘(’
		menuOption = createMenuOptionR('02');
		reArray[reArray.length] = menuOption;
		// 增加运算符和增加条件
		var nextNode = obNode.nextSibling;
		if (nextNode == obNode.parentNode.lastNode || nextNode.id != 'Operator') {
			menuOption = createMenuOptionR('06');
			reArray[reArray.length] = menuOption;

			menuOption = createMenuOptionR('07');
			reArray[reArray.length] = menuOption;
		}
	} else if (Id == 'Operator') {
		menuOption = createMenuOptionR('03');
		reArray[reArray.length] = menuOption;		
		menuOption = createMenuOptionR('02');
		reArray[reArray.length] = menuOption;
		menuOption = createMenuOptionR('07');
		reArray[reArray.length] = menuOption;
	} else if (Id == 'Link') {
		menuOption = createMenuOptionR('01');
		reArray[reArray.length] = menuOption;
	} else if (Id == 'Input') {
		menuOption = createMenuOptionR('02');
		reArray[reArray.length] = menuOption;
		menuOption = createMenuOptionR('05');
		reArray[reArray.length] = menuOption;
		menuOption = createMenuOptionR('06');
		reArray[reArray.length] = menuOption;
		menuOption = createMenuOptionR('07');
		reArray[reArray.length] = menuOption;
	} else if (Id == 'Left_Paren' || Id == 'Right_Paren') {
		menuOption = createMenuOptionR('03');
		reArray[reArray.length] = menuOption;
	} else if (Id == 'InputRightScope') {
		menuOption = createMenuOptionR('02');
		reArray[reArray.length] = menuOption;
		menuOption = createMenuOptionR('07');
		reArray[reArray.length] = menuOption;
	}
	return reArray;
}

function prepareBaseMenuR(inforArray) {
	var spanArray = new Array();

	for ( var i = 0, len = inforArray.length; i < len; i++) {
		var paraArray = new Array();

		paraArray[0] = {
			attribute :'id',
			value :'Input'
		};
		paraArray[1] = {
			attribute :'EnName',
			value :inforArray[i][0] + '-' + inforArray[i][1]
		};
		paraArray[2] = {
			attribute :'ChName',
			value :inforArray[i][0] + '-' + inforArray[i][1]
		};
		paraArray[3] = {
				attribute :'TableValue',
				value :''
			};

		spanArray[spanArray.length] = createSpanNodeR(paraArray, true);
	}

	return spanArray;
}

/*
 * 01代表增加左括号， 02代表增加右括号， 03代表删除节点本身 04代表将BOM转换成Input 05代表将Input转换成BOM 06代表增加运算符
 * 07代表增加条件 08代表增加Input节点 09增加BOM
 */
function createMenuOptionR(menuIndex) {
	var spanNode = document.createElement("span");
	spanNode.style.float = "left";
	//spanNode.style.cursor = 'hand';
	//spanNode.style.fontSize = '18px';

	if (menuIndex == '01') {
		spanNode.innerHTML = '增加"("';
		spanNode.onclick = function() {

			var spanN = createOriginSpanNodeR('Left_Paren');
			var fatherNode = preEventNodeR.parentNode;
			if (preEventNodeR.id == 'Link') {
				fatherNode.insertBefore(spanN, preEventNodeR.nextSibling);

				spanN = createOriginSpanNodeR('Spacer');
				fatherNode.insertBefore(spanN, preEventNodeR.nextSibling);
			} else {
				fatherNode.insertBefore(spanN, preEventNodeR);
				spanN = createOriginSpanNodeR('Spacer');
				fatherNode.insertBefore(spanN, preEventNodeR);
			}
		}
	}

	else if (menuIndex == '02') {
		spanNode.innerHTML = '增加")"';
		spanNode.onclick = function() {
			var spanN = createOriginSpanNodeR('Right_Paren');
			var fatherNode = preEventNodeR.parentNode;
			if (fatherNode.id == "InputNodes") {
				var obNode = fatherNode;
				var parNode = fatherNode.parentNode;
				parNode.insertBefore(spanN, obNode.nextSibling);
				spanN = createOriginSpanNodeR('Spacer');
				fatherNode.insertBefore(spanN, obNode.nextSibling);
			} else {
				fatherNode.insertBefore(spanN, preEventNodeR.nextSibling);
				spanN = createOriginSpanNodeR('Spacer');
				fatherNode.insertBefore(spanN, preEventNodeR.nextSibling);
			}

		}

	}

	else if (menuIndex == '03') {

		spanNode.innerHTML = ("删除本节点");

		spanNode.onclick = function() {
			var fatherNode = preEventNodeR.parentNode;
			if (preEventNodeR.id == 'BOM') {
				var nextNode = preEventNodeR.nextSibling;
				if (preEventNodeR.previousSibling.id == 'Operator'
						&& preEventNodeR.previousSibling.ResultType != 'Boolean')
					fatherNode.removeChild(preEventNodeR.previousSibling);
				fatherNode.removeChild(nextNode);
				fatherNode.removeChild(preEventNodeR);
			} else if (preEventNodeR.id == 'BOMItem') {
				var preNode = preEventNodeR.previousSibling;
				if (preNode.previousSibling.id == 'Operator'
						&& preNode.previousSibling.ResultType != 'Boolean')
					fatherNode.removeChild(preNode.previousSibling);
				fatherNode.removeChild(preNode);
				fatherNode.removeChild(preEventNodeR);
			} else if (preEventNodeR.id == 'Operator') {
				var nextNode = preEventNodeR.nextSibling;
				if (nextNode.nextSibling!=null&&nextNode.nextSibling.id == 'BOMItem') {
					fatherNode.removeChild(nextNode.nextSibling);
				}
				//fatherNode.removeChild(nextNode);
				fatherNode.removeChild(preEventNodeR);
				
			} else if (preEventNodeR.id == 'Input') {

			} else if (preEventNodeR.id == 'Left_Paren'
					|| preEventNodeR.id == 'Right_Paren') {
				fatherNode.removeChild(preEventNodeR);
			}
		}
	} else if (menuIndex == '04') {
		spanNode.innerHTML = ("输入一个值");

		spanNode.onclick = function() {
			var spanN = createOriginSpanNodeR('Input');
			var fatherNode = preEventNodeR.parentNode;
			deleteNodesAfter(preEventNodeR);
			fatherNode.replaceChild(spanN, preEventNodeR);
		}
	} else if (menuIndex == '05') {
		spanNode.innerHTML = ("下拉其它BOM");

		spanNode.onclick = function() {
			var spanN = createOriginSpanNodeR('BOM');
			var fatherNode = preEventNodeR.parentNode;
			fatherNode.replaceChild(spanN, preEventNodeR);
		}

	} else if (menuIndex == '06') {
		spanNode.innerHTML = ("增加运算项");
		spanNode.onclick = function() {
			var spanNOp = createOriginSpanNodeR('Operator');
			var spanNBOM = createOriginSpanNodeR('BOM');
			var fatherNode = preEventNodeR.parentNode;

			fatherNode.insertBefore(spanNBOM, preEventNodeR.nextSibling);
			var spanN = createOriginSpanNodeR('Spacer');
			fatherNode.insertBefore(spanN, preEventNodeR.nextSibling);

			fatherNode.insertBefore(spanNOp, preEventNodeR.nextSibling);
			spanN = createOriginSpanNodeR('Spacer');
			fatherNode.insertBefore(spanN, preEventNodeR.nextSibling);
		}
	}

	else if (menuIndex == '07') {
		spanNode.innerHTML = '增加"并且"';

		spanNode.onclick = function() {
			var spanNodeLink = createOriginSpanNodeR('Link');
			var spanNodeBOM = createOriginSpanNodeR('BOM');
			var fatherNode = preEventNodeR.parentNode;

			if (fatherNode.id == "InputNodes") {
				var obNode = fatherNode;
				var parNode = fatherNode.parentNode;
				parNode.insertBefore(spanNodeBOM, obNode.nextSibling);

				var spanN = createOriginSpanNodeR('Spacer');
				parNode.insertBefore(spanN, obNode.nextSibling);

				parNode.insertBefore(spanNodeLink, obNode.nextSibling);

				spanN = createOriginSpanNodeR('Spacer');
				parNode.insertBefore(spanN, obNode.nextSibling);
			} else {
				fatherNode.insertBefore(spanNodeBOM, preEventNodeR.nextSibling);

				var spanN = createOriginSpanNodeR('Spacer');
				fatherNode.insertBefore(spanN, preEventNodeR.nextSibling);

				fatherNode.insertBefore(spanNodeLink, preEventNodeR.nextSibling);

				spanN = createOriginSpanNodeR('Spacer');
				fatherNode.insertBefore(spanN, preEventNodeR.nextSibling);
			}
		}
	}

	else if (menuIndex == '08') {
		spanNode.innerHTML = ("增加输入框");
		spanNode.onclick = function() {
			var spanN = createOriginSpanNodeR('Input');
			var fatherNode = preEventNodeR.parentNode;
			fatherNode.insertBefore(spanN, preEventNodeR.nextSibling);
			spanN = createOriginSpanNodeR('Spacer');
			fatherNode.insertBefore(spanN, preEventNodeR.nextSibling);
		}
	}

	else if (menuIndex == '09') {
		spanNode.innerHTML = ("增加输入框");
		spanNode.onclick = function() {
			var spanN = createOriginSpanNodeR('BOM');
			var fatherNode = preEventNodeR.parentNode;
			fatherNode.insertBefore(spanN, preEventNodeR.nextSibling);
			spanN = createOriginSpanNodeR('Spacer');
			fatherNode.insertBefore(spanN, preEventNodeR.nextSibling);
		}
	}
	return spanNode;
}

function getNodeInformationR(node, id) {
	// 将传入的参数进行函数内部存储，避免在使用过程对参数的修改
	var startNode = node;
	var nodeId = id;

	//var fatherNode = node.parentNode;

	var reArray = new Array();
	if (nodeId == 'BOMItem') {
		var BOMFinished = false;
		var OperatorFinished = false;

		var BOMName = '';
		var opParameter = '';

		var preNode = startNode.previousSibling;
		while (preNode && preNode.id != 'Link') {
			if (!BOMFinished && preNode.id == 'BOM') {
				BOMName = preNode.EnName;
				BOMFinished = true;
			}
			if (!OperatorFinished && preNode.id == 'Operator') {
				opParameter = preNode.ParaType;
				OperatorFinished = true;
			}
			preNode = preNode.previousSibling;
		}
		reArray[0] = BOMName;
		reArray[1] = opParameter;
	} else if (nodeId == 'Operator') {
		
		var BOMItemFinished = false;
		var OperatorFinished = false;
		var hasBoolean = false;
		var firstOperator = true;

		var CommandType = '';
		var opParameter = '';
		var isHierarchical = 0;

		var preNode = startNode.previousSibling;
		while (preNode && preNode.id != 'Link') {
			//Dialog.alert("符号选择-"+preNode.id);
			if (preNode.id != 'Spacer') {
				if (firstOperator) {
					if (preNode.id == 'Operator') {
		
						// CommandType = preNode.ParaType;
						CommandType = preNode.ResultType;
						if(CommandType=="Number"||CommandType=="String"||CommandType=="Date")
						{
							break;
						}
					}
				} else
					firstOperator = false;
			}
			if (!BOMItemFinished && preNode.id == 'BOMItem') {
				CommandType = preNode.MatchType;
				isHierarchical = preNode.isHierarchical;
				BOMFinished = true;
			}
			if (!OperatorFinished && preNode.id == 'Operator') {

				opParameter = preNode.ParaType;
				OperatorFinished = true;
			}
			if (preNode.id == 'Operator' && preNode.ResultType == 'Boolean') {
				hasBoolean = true;
			}
			preNode = preNode.previousSibling;
		}
		reArray[0] = CommandType;
		reArray[1] = opParameter;
		reArray[2] = hasBoolean;
		reArray[3] = isHierarchical;
	}
	return reArray;
}
// 获取后台BOM数据
function getBOMArray() {
	var BOMArray = new Array();
	var sql = "select Name,CNName from lRBOM where valid='1' order by BOMLevel,FBOM";
	// 条件
	BOMArray = getAndPrepareDataR(sql);

	return BOMArray;
}
// 包含对查询的缓存
function getAndPrepareDataR(sql) {
	var reArray = new Array();

	for ( var i = 0; i < queryCacheR.length; i++) {
		if (queryCacheR[i][0] == sql) {
			return queryCacheR[i][1];
		}
	}
	var str = easyQueryVer3(sql);
	reArray = decodeEasyQueryResult(str);

	var len = queryCacheR.length;
	queryCacheR[len] = new Array();
	queryCacheR[len][0] = sql;
	queryCacheR[len][1] = reArray;

	return reArray;

}
// 根据BOM获取后台BOMItem
function getBOMItemArray(paraArray) {
	var BOMItemArray = new Array();
	var sql = "";

	if (paraArray[0] == null || paraArray[0] == '') {
		Dialog.alert(("获取后台BOMItem所需要的参数出错：不知道BOMItem所属的BOM"));
	} else {
		if (paraArray[1] != null && paraArray[1] != '') {
			sql = "select Name,CONCAT(connector,CNName),CommandType,Source,isHierarchical from lRBOMItem where BOMName='"
					+ paraArray[0] + "' and CommandType='" + paraArray[1] + "'  order by CNName";
		} else {
			sql = "select Name,CONCAT(connector,CNName),CommandType,Source,isHierarchical from lRBOMItem where BOMName='"
					+ paraArray[0] + "'  order by CNName";
		}
		BOMItemArray = getAndPrepareDataR(sql);
	}
	return BOMItemArray;
}

// 根据BOMItem类型获取运算符
// 'id','EnName','ChName','MatchType','ResultType','ParaType','IsNextOperator'
function getOperatorArrayR(paraArray) {
	var OperatorArray = new Array();
	var commandtype = paraArray[0];
	
	var sql = "select Implenmation,Display,CommandType,ResultType,ParaType,ParaNum from LRCommand where commtype in ('0','2') and resulttype != 'Boolean' and CommandType='"
			+ commandtype + "'";
	if (paraArray[0] == null || paraArray[0] == '') {
		Dialog.alert(("获取后台Operatror所需要的参数出错：不知道Operator所属的CommandType"));

		return null;
	} else {
		if (paraArray[1] != null && paraArray[1] != '') {
			sql += " and ParaType='" + paraArray[1] + "'";
		}
	}
	OperatorArray = getAndPrepareDataR(sql+ " order by name");
	try {
		for ( var i = 0, len = OperatorArray.length; i < len; i++) {
			for ( var j = 0, len1 = OperatorArray[i].length; j < len1; j++) {
				OperatorArray[i][j] = OperatorArray[i][j].replace(new RegExp(
						'@@SinQuot', 'gm'), "'");
			}
		}
	} catch (e) {
		Dialog.alert(("运算符查找出错！"));
	}
	return OperatorArray;
}

function getInputSource(sql) {
	var reArray = new Array();
	sql = sql.replace(new RegExp('@@SinQuot', 'gm'), "'");
	var str = easyQueryVer3(sql);
	reArray = decodeEasyQueryResult(str);
	return reArray;
}

/*
 * 
 * 
 * 
 * 
 */
// 用于扫描节点过程中对数据进行暂存
var stack = new Array();
// 用于生成DT表中的列名中处理重复列名问题
var reCurArray = new Array();
// 用于生成决策表表头信息时对信息进行缓存
var disCol = new Array();
// 用于记录定制逻辑中所有Input节点，以减少决策表数据与定制逻辑中数据的交互问题

var RuleChArray = new Array();

function checkOutRuleR() {
	var ruleNodes = document.getElementById('ResultZone');
	if (!ruleNodes) {
		Dialog.alert(("您还没有定制规则，请定制完整后再检测规则定制的完整性3"));
		return;
	}
	var ruleNode = ruleNodes.firstChild;
	var endNode = ruleNodes.lastChild;
	if (!ruleNode || ruleNode == endNode) {
		Dialog.alert(("您还没有定制规则，请定制完整后再检测规则定制的完整性4"));
		return;
	}
	var row = 0;
	var errorMessage = '';
	while (ruleNode != endNode) {
		row++;
		var BoolOpNum = 0;
		var paren = 0;
		var spanNode = ruleNode.firstChild;
		if (!spanNode) {
			continue;
		}
		var lastNode = ruleNode.lastChild;
		var i = 0;
		while (spanNode != lastNode) {
			i++;
			if (spanNode.id == "Link") {
				BoolOpNum = 0;
			}
			if (spanNode.id == "Left_Paren") {
				paren++;
			}
			if (spanNode.id == "Right_Paren") {
				paren--;
			}
			// 对运算符类型的判断
			if (spanNode.id == "Operator") { // 逻辑型运算符的个数
				if (spanNode.ResultType == 'Boolean') {
					BoolOpNum++;
				}
				if (spanNode.ResultType == 'Over') {
					BoolOpNum++;
				}
				var opParaNum = spanNode.ParaNum;
				// 看连接运算符左右的参数是否正确
				// 一元运算符只能是：前一个是词条，后一个是“删除本条件”
				// 二元运算符只能是：前后两个都是完整的词条，或者其中一个是词条另一个Input，或者是前一个是必须接运算符的运算符后一个是Input或者是BOM
				// 变态运算符只能是：前一个是完整的词条，后一个是运算符
				if (opParaNum == 0) {
					// 找到往前第一个不是Spacer的节点
					var BOMItemNode = null;
					var startNode = spanNode;
					while (startNode.id == 'Spacer') {
						startNode = startNode.previousSibling;
					}
					BOMItemNode = startNode;
					// 往后找，找到第一个id不是Spacer的节点
					startNode = spanNode;
					while (startNode.id == 'Spacer') {
						startNode = startNode.previousSibling;
					}
					var lastNode = startNode;

					if (!(BOMItemNode.id == 'BOMItem' && (lastNode.id == 'link'
							|| lastNode.id == undefined || lastNode.id == 'Parentheses')))
							errorMessage += ("行") + row + ("的运算符连接的参数不正确\n");
				} else if (opParaNum == 1) {

				} else if (opParaNum == 2) {

				}
			}
			spanNode = spanNode.nextSibling;
		}
		if (BoolOpNum != 1) {
			errorMessage += ("行") + row + ("含有") + BoolOpNum + ("个运算结果为逻辑型的运算符!\n");
		}
		if (paren != 0) {
			errorMessage += ("行") + row + ("的括号不匹配！\n");
		}
		ruleNode = ruleNode.nextSibling;
	}
	if (errorMessage != '') {
		Dialog.alert(errorMessage);
		return false;
	} else
		return true;
}
/*
 * 函数传入参数为空 函数返回值：函数的返回值是一个数组（reArray） reArray[0]携带拼写好的SQLStatement
 * reArray[1]携带拼写好的createTable reArray[2]携带拼写好的决策表的表头
 */
function composeSQLR() {
	// 获取规则区域的节点
	var ruleNodes = document.getElementById('ResultZone');
	
	// 获取规则的第一个条件
	var ruleNode = ruleNodes.firstChild;
	// 获取规则的最后一个条件
	var endNode = ruleNodes.lastChild;

	// 对拼SQL过程中需要使用到的变量进行初始化

	initParaBeforeComposeSQLR();

	var xmlDoc = new ActiveXObject("Msxml2.DOMDocument.3.0");
	var xmlRule = xmlDoc.createElement("Rule");
	xmlDoc.appendChild(xmlRule);
	var xmlCondition = xmlDoc.createElement("Condition");
	var MetaNode;

	while (ruleNode != endNode) {
		// 获取条件的第一个节点和最后一个节点
		var spanNode = ruleNode.firstChild;
		var lastNode = ruleNode.lastChild;
		// 用于记录规则中一个条件的sql字符串
		xmlCondition = xmlDoc.createElement("Condition");

		MetaNodeChNameArrayR.length = 0;

		while (spanNode != lastNode) {
			if (spanNode.id == 'Spacer') {
				spanNode = spanNode.nextSibling;
				continue;
			}

			MetaNodeChNameArrayR[MetaNodeChNameArrayR.length] = spanNode;
		
			// 有优化的余地
			MetaNode = composeXMLR(xmlDoc, spanNode);
			//Dialog.alert(spanNode.id + ":"+spanNode.EnName+ ":"+spanNode.ChName);   
	       for(var   j=0;j<MetaNode.attributes.length;j++)   
           {   
              //Dialog.alert(MetaNode.attributes[j].name + ":"+MetaNode.attributes[j].value);   
           }   
				
			xmlCondition.appendChild(MetaNode);
			
			if (spanNode.id == "BOM") {
				if (spanNodeArrayR.length != 0) {
					comASQLR();
				}
				spanNodeArrayR.push(spanNode);
			}

			else if (spanNode.id == "BOMItem") {
				spanNodeArrayR.push(spanNode);
			} else if (spanNode.id == "Operator") {
				spanNodeArrayR.push(spanNode);
				if(spanNode.EnName == "is null"||spanNode.EnName == "is not null"  )
				{
					comASQLR();
				}
			} else if (spanNode.id == "Link") {
				if (spanNodeArrayR.length != 0) {
					comASQLR();
				}
				spanNodeArrayR.push(spanNode);
				ColumnMetasChArrayR.length = 0;
			} else if (spanNode.id == "Left_Paren") {
				spanNodeArrayR.push(spanNode);
			} else if (spanNode.id == "Right_Paren") {
				spanNodeArrayR.push(spanNode);
			} else if (spanNode.id == "Input" || spanNode.id == "InputNodes") {
				spanNodeArrayR.push(spanNode);
				comASQLR();
			}
			spanNode = spanNode.nextSibling;
		
		}
		if (spanNodeArrayR.length != 0) {
			comASQLR();
		}
		xmlRule.appendChild(xmlCondition);
		composeRuleDesInChR();

		ruleNode = ruleNode.nextSibling;
	}
	ViewParaR = xmlRule.xml;
	//Dialog.alert(RuleDesInChR)
	//Dialog.alert(ViewParaR);
	return completeParaAfterComposeR();
}

function comASQLR() {
	var reStr = '';
	var str = '';
	var spanNode = null;
	var outCol = '';
	var op = '';
	var finished = false;
	// 用于记录扫描到节点的英文名
	var lparenStr = '';
	var rparenStr = '';

	var opStr = '';
	var opStr1 = '';
	// 用于记录扫描到节点的中文名

	var disColCh = '';
	var BOMItemType = "";
	var InputType = "";

	var pareNumber = 0;
	// aler("堆栈stack的长度是："+stack.length);
	for ( var i = 0, len = spanNodeArrayR.length; i < len; i++) {
		var spanNode = spanNodeArrayR[i];
		if (!(!!spanNode))
			continue;

		if (spanNode.id == "Link") {
			comR(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM',
					InputType, BOMItemType);
			SQLStatementR += ' ' + spanNode.getAttribute('EnName') + ' ';
		} else if (spanNode.id == "Left_Paren") {
			if (lparenStr != '') {
				SQLStatementR += spanNode.getAttribute('EnName');
			}
			lparenStr = spanNode.getAttribute('EnName');
			pareNumber += 1;
		} else if (spanNode.id == "Right_Paren") {
			rparenStr = spanNode.getAttribute('EnName');
			pareNumber -= 1;
		} else if (spanNode.id == "BOM") {
			outCol += spanNode.getAttribute('EnName');
			BOMNode = spanNode.getAttribute('EnName');
			ColumnMetasChArrayR[ColumnMetasChArrayR.length] = spanNode.ChName;
		} else if (spanNode.id == "BOMItem") {
			outCol += '_' + spanNode.getAttribute('EnName');
			BOMItemNode = spanNode.getAttribute('EnName');
			BOMItemType = spanNode.MatchType;

			ColumnMetasChArrayR[ColumnMetasChArrayR.length] = spanNode.ChName;
		} else if (spanNode.id == "Operator") {
			if (opStr == '') {
				opStr = spanNode.getAttribute('EnName');
			} else {
				opStr1 = spanNode.getAttribute('EnName');
			}
			InputType = spanNode.ParaType;
			ColumnMetasChArrayR[ColumnMetasChArrayR.length] = spanNode.ChName;
		} else if (spanNode.id == "Input" || spanNode.id == "InputNodes") {
			var rArray = comR(lparenStr, outCol, rparenStr, opStr, opStr1,
					'isInput', InputType, BOMItemType);
			finished = true;
			for ( var j = 0; j < ColumnMetasChArrayR.length; j++) {
				disColCh += ColumnMetasChArrayR[j];
			}
			ColumnMetasChArrayR.length = 0;
		}
				//Dialog.alert("lparenStr:"+lparenStr+"/outCol:"+outCol+"/rparenStr:"+rparenStr+"/opStr:"+opStr+"/opStr1:"+opStr1+"/InputType:"+InputType
		//+"/BOMItemType:"+BOMItemType);
	}
	if (!finished) {
		comR(lparenStr, outCol, rparenStr, opStr, opStr1, 'isBOM', InputType,
				BOMItemType);
	}
	spanNodeArrayR.length = 0;

	/*
	 * if (reCreateTable != '') { CreateTableR+= reCreateTable; }
	 */
	if (disColCh != '') {
		ColumnHeadArrayR[ColumnHeadArrayR.length] = disColCh;
		ColumnDataTypeArrayR[ColumnDataTypeArrayR.length] = InputType;
	}
	
}

/*
 * 函数传入参数分别是： lparenStr：左括号的英文名 outCol： 格式为BOM.BOMItem rparenStr：右括号的英文名 opStr：
 * 运算符的英文名 flag： 是否需要生成决策表中的一列的标识，Input表示要，BOM表示不要 dataType：
 * BOM.BOMItem中BOMItem的数据类型，用于falg为Input时需要创建决策条表的情况 函数返回值：函数的返回值是一个数组（reArray）
 * reArray[0]携带拼写好的SQLStatement reArray[1]携带拼写好的createTable
 */

function comR(lparenStr, outCol, rparenStr, opStr, opStr1, flag, InputType,
		BOMItemType) {
	var reStr = '';
	var reCreateTable = '';
	var temp = '';

	if (outCol != '') {
		getBOMAndBOMItemR(outCol);
	}

	if (lparenStr != '' && rparenStr != '') {
		lparenStr = '';
		rparenStr = '';
	}
	if (outCol != '') {
		if (BOMItemType == 'Date') {
			temp = lparenStr + "to_date(?" + outCol.replace('_', '.')
					+ "?,'yyyy-mm-dd hh24:mi:ss')" + rparenStr;
		} else {
			temp = lparenStr + '?' + outCol.replace('_', '.') + '?' + rparenStr;
		}

	} else {
		reStr += lparenStr + rparenStr;
	}

	var indexLeft = opStr.indexOf('#');
	var indexRight = opStr.indexOf('@');

	if (flag == 'isInput') {
		var i = getRecurR(outCol);

		if (opStr.indexOf('$' >= 0)) {
			if (outCol.indexOf('ManageCom') >= 0) {
				opStr = opStr.replace('$', 1);
			} else {
				opStr = opStr.replace('$', 0);
			}
		}
		if (indexLeft >= 0 && temp != '') {
			opStr = opStr.replace('#', temp);
			if (opStr1 != '') {
				hasTwo = true;
			}

			if (indexRight >= 0) {

				opStr = opStr.replace('@', outCol + i);

				reStr += opStr + " ";
			} else {
				reStr += opStr + opStr1 + " " + outCol + i + ' ';
			}
		} else {
			reStr += temp + " " + opStr + " " + outCol + i + ' ';
		}
		if (InputType != '') {

			if (InputType == 'Date') {
				reCreateTable = outCol + i + ' Date';
			} else if (InputType == 'INT' || InputType == 'Number') {
				reCreateTable = outCol + i + ' Number';
			} else if (InputType == 'String') {
				reCreateTable = outCol + i + ' varchar2(1000)';
			}
			TableColumnNameArrayR[TableColumnNameArrayR.length] = (outCol + i).toUpperCase();
		}
	} else {
		if (indexLeft < 0 && indexRight < 0 && opStr1 == '') {
			reStr += temp + " " + opStr;
		} else {
			if (indexLeft >= 0 && temp != '') {
				temp = opStr.replace('#', temp);
			}
			reStr += temp + " " + opStr1;
		}

	}
	SQLStatementR += reStr;
	if (reCreateTable != "") {
		CreateTableR += reCreateTable + ",";
	}
	//Dialog.alert("SQLStatementR:"+SQLStatementR);
	//Dialog.alert("CreateTableR:"+CreateTableR);
}

function initParaBeforeComposeSQLR() {
	// 存储规则的SQL
	SQLStatementR = "select RuleId,UWLevel,Result from #DTTable# where ";
	// 创建DT表的语句
	CreateTableR = "create table #DTTable# (";

	// 存储规则反显示所需要的参数
	ViewParaR = "";
	// 存储规则所用的BOM
	BOMSArrayR.length = 0;
	// 存储规则反解析的参数
	SQLParaArrayR.length = 0;
	// 存储规则的中文名
	RuleDesInChR = ("如果，");
	// 存储决策表列头所要显示的中文名
	ColumnHeadArrayR.length = 0;
	// 存储决策表列的数据类型
	ColumnDataTypeArrayR.length = 0;
	// 存储DT表中列名。
	TableColumnNameArrayR.length = 0;
	// 存储决策表所的运算符是否为“其中之一”
	ColumnMultipleArrayR.length = 0;
	// 存储决策表的列是否为基础词条
	BaseColumnArrayR.length = 0;
	// 存储扫描到节点的中文名
	MetaNodeChNameArrayR.length = 0;
	// 存储扫描节点的数组
	spanNodeArrayR.length = 0;
}

function completeParaAfterComposeR() {
	// 创建DT表的语句
	CreateTableR += 'Result varchar2(4000),UWLevel varchar2(20),RuleId varchar2(20))';

	RuleDesInChR += ("那么#");

	ColumnHeadArrayR[ColumnHeadArrayR.length] = ("自核结论");
	ColumnHeadArrayR[ColumnHeadArrayR.length] = ("转人工核保级别");

	ColumnDataTypeArrayR[ColumnDataTypeArrayR.length] = "String";
	TableColumnNameArrayR[TableColumnNameArrayR.length] = "Result";

	ColumnDataTypeArrayR[ColumnDataTypeArrayR.length] = "String";
	TableColumnNameArrayR[TableColumnNameArrayR.length] = "UWLevel";

	return checkColumnLength();
}

function checkColumnLength() {
	var message = '';
	if (Length(SQLStatementR) > 2000 || Length(BOMSArrayR.toString()) > 500
			|| Length(SQLParaArrayR.toString()) > 2000
			|| Length(RuleDesInChR) > 2000) {
		message = ("定制的规则太长，请对规则进行精简！");
	}
	if (message != '') {
		Dialog.alert(message);
		return false;
	} else {
		return true;
	}

}

function Length(str) {
	var i, sum;
	sum = 0;
	for (i = 0; i < str.length; i++) {
		if ((str.charCodeAt(i) >= 0) && (str.charCodeAt(i) <= 255))
			sum = sum + 1;
		else
			sum = sum + 2;
	}
	return sum;
}

function compactBOMArray(BOMEnName) {
	var i = 0;
	var len = BOMSArrayR.length;
	for (i = 0; i < len; i++) {
		if (BOMSArrayR[i] == BOMEnName)
			break;
	}
	if (i >= len) {
		BOMSArrayR[len] = BOMEnName;
	}
}

function compactSQLParaArrayR(SQLPara) {
	var i = 0;
	var len = SQLParaArrayR.length;
	for (i = 0; i < len; i++) {
		if (SQLParaArrayR[i] == SQLPara)
			break;
	}
	if (i >= len) {
		SQLParaArrayR[len] = SQLPara;
	}
}
function getBOMAndBOMItemR(BOMAndBOMItem) {
	var result = BOMAndBOMItem.split("_");
	var BOMStr = result[0];
	var BOMItemStr = result[1];

	if (BOMStr != '') {
		compactBOMArray(BOMStr);
		if (BOMItemStr != '') {
			compactSQLParaArrayR(BOMStr + "." + BOMItemStr);
		}
	}
}
function getRecurR(str) {
	var i = 0;
	var index = 0;
	for (i = 0, len = reCurArray.length; i < len; i++) {

		if (reCurArray[i][0] == str) {

			reCurArray[i][1]++;
			index = reCurArray[i][1];
			break;
		}
	}
	if (i == reCurArray.length) {
		reCurArray[i] = new Array();
		reCurArray[i][0] = str;
		reCurArray[i][1] = 0;
		index = reCurArray[i][1];
	}
	return index;
}

function composeXMLR(xmlDoc, spanNode) {
	var xmlNode = xmlDoc.createElement("MetaNode");
	if (spanNode.id == 'BOM') {
		for ( var i = 0; i < BOMIndex.length; i++) {
			var att = spanNode.getAttribute(BOMIndex[i]);
			xmlNode.setAttribute(BOMIndex[i], att);
		}
	} else if (spanNode.id == 'BOMItem') {
		for ( var i = 0; i < BOMItemIndex.length; i++) {
			var att = spanNode.getAttribute(BOMItemIndex[i]);
			att = att.replace(new RegExp("'", "gm"), "''");
			xmlNode.setAttribute(BOMItemIndex[i], att);
		}
	} else if (spanNode.id == 'Operator') {
		for ( var i = 0; i < OperatorIndex.length; i++) {
			var att = spanNode.getAttribute(OperatorIndex[i]);
			xmlNode.setAttribute(OperatorIndex[i], att);
		}
	} else if (spanNode.id == 'Link') {
		for ( var i = 0; i < LinkIndex.length; i++) {
			var att = spanNode.getAttribute(LinkIndex[i]);
			xmlNode.setAttribute(LinkIndex[i], att);
		}
	} else if (spanNode.id == 'Left_Paren' || spanNode.id == 'Right_Paren') {
		for ( var i = 0; i < ParenIndex.length; i++) {
			var att = spanNode.getAttribute(ParenIndex[i]);
			xmlNode.setAttribute(ParenIndex[i], att);
		}
	} else if (spanNode.id == 'Input' || spanNode.id == 'InputNodes') {
		for ( var i = 0; i < InputIndex.length; i++) {
			var att = spanNode.getAttribute(InputIndex[i]);
			xmlNode.setAttribute(InputIndex[i], att);
		}
	}
	return xmlNode;
}
function composeRuleDesInChR() {
	var spanNode;
	for ( var i = 0, len = MetaNodeChNameArrayR.length; i < len; i++) {
		spanNode = MetaNodeChNameArrayR[i];
		if (spanNode.id != "Input" && spanNode.id != "InputNodesR") {
			RuleDesInChR += spanNode.ChName;
		} else {
			RuleDesInChR += "#，";
		}
	}
}

// 审核规则定制的接口
function comfirmLogicR() {
	// 审核规则是否定制完整
	if (checkOutRuleR()) {

		if (composeSQLR()) {
			initDataArrayR();
			
			displayDicTable();

			var submitData = document.getElementById('submitData');
			submitData.disabled = false;
			var logicToTable = document.getElementById('logicToTable');
			logicToTable.disabled = false;
			var modifyLogic = document.getElementById('modifyLogic');
			modifyLogic.disabled = false;
			var displayDisicionTable = document
					.getElementById('displayDisicionTable');
			displayDisicionTable.disabled = true;
		}

		else {

			return;
		}

	}
}

function getInputNodesR() {
	var ruleNodes = document.getElementById('ResultZone');
	// 获取规则的第一个条件
	var ruleNode = ruleNodes.firstChild;
	// 获取规则的最后一个条件
	var endNode = ruleNodes.lastChild;
	InputNodesR.length = 0;
	while (ruleNode != endNode) {
		var spanNode = ruleNode.firstChild;
		var lastNode = ruleNode.lastChild;

		while (spanNode != lastNode) {
			if (spanNode.id == 'Input' || spanNode.id == 'InputNodes') {
				InputNodesR[InputNodesR.length] = spanNode;
			}
			spanNode = spanNode.nextSibling;
		}
		ruleNode = ruleNode.nextSibling;
	}
	InputNodesR[InputNodesR.length] = document.getElementById('Result');
}

function getBaseBomItemsR() {
	getInputNodesR();
	BaseColumnArrayR.length = 0;
	BaseBOMItemSourceArrayR.length = 0;
	ColumnMultipleArrayR.length = 0;
	var inputNode;
	var firstBomItem;
	for ( var i = 0; i < InputNodesR.length - 1; i++) {
		inputNode = InputNodesR[i];
		while (inputNode.id != 'BOMItem') {
			inputNode = inputNode.previousSibling;
		}
		firstBomItem = inputNode;
		if (!!firstBomItem.Source) {
			BaseColumnArrayR[i] = true;

			inputNode = InputNodesR[i];
			while (inputNode.id != 'Operator') {
				inputNode = inputNode.previousSibling;
			}
//			if (inputNode.EnName == "instrExt(@,#,$)>0"
//					|| inputNode.EnName == "instrExt(@,#,$)=0") {
//				ColumnMultipleArrayR[i] = true;
//			} else {
			ColumnMultipleArrayR[i] = false;
			//}
		} else {
			BaseColumnArrayR[i] = false;
			ColumnMultipleArrayR[i] = false;
		}
		BaseBOMItemSourceArrayR[BaseBOMItemSourceArrayR.length] = firstBomItem.Source;
	}
	// 用于记录自核结论那一列
	BaseColumnArrayR[BaseColumnArrayR.length] = false;
	ColumnMultipleArrayR[ColumnMultipleArrayR.length] = false;
	BaseBOMItemSourceArrayR[BaseBOMItemSourceArrayR.length] = "";
    /**zy  自核不要 start**/
	//var sql = "select sysvarvalue from ldsysvar  where sysvar='ibrmsUWLevel'";
	//var reArray = getAndPrepareDataR(sql);
	//var UWSource = reArray[0][0];
	
	//BaseColumnArrayR[BaseColumnArrayR.length] = true;
	//ColumnMultipleArrayR[ColumnMultipleArrayR.length] = false;
	//BaseBOMItemSourceArrayR[BaseBOMItemSourceArrayR.length] = "";
	//Dialog.alert(1)
	/**zy  自核不要 end**/
}

function dataToTableR() {
	getInputNodesR();
	var dataD = new Array();
	if (InputNodesR.length != 0) {
		for ( var i = 0; i < InputNodesR.length - 1; i++) {
			if (InputNodesR[i].id != "InputNodes") {
				if (!!InputNodesR[i].EnName) {
					if (ColumnDataTypeArrayR[i] == 'Date') {
						var dt = new Date(InputNodesR[i].EnName);
						dataD[i] = dt.format('Y-m-d H:i:s');
					} else {
						dataD[i] = InputNodesR[i].EnName;
					}
				}
			} else {
				var firstNode = InputNodesR[i].firstChild;
				var first = true;
				while (firstNode) {
					if (firstNode.id == "Input") {
						if (first) {
							dataD[i] = firstNode.EnName;
							first = false;
						} else {
							dataD[i] += ";" + firstNode.EnName;
						}
					}
					firstNode = firstNode.nextSibling;
				}
			}
		}
		if (!!InputNodesR[InputNodesR.length - 1].value) {
			dataD[InputNodesR.length - 1] = InputNodesR[InputNodesR.length - 1].value;
		}

	}
	//Dialog.alert(dataD.toString());
	var dataArray = sm.getSelections();
	if (dataArray.length > 1) {
		Dialog.alert(("您选择的行数大于1，请选择一行！"));
	} else if (dataArray.length == 0) {
		Dialog.alert(("您还没有选择行，请选择一行！"));

	} else {

		// dataD[dataD.length]=dataArray[0].get(dataId[dataId.length-1].name);
		var dIndex = grid.getStore().indexOf(dataArray[0]);
		data = getData();
		data[dIndex] = dataD;
		ds = new Ext.data.Store( {
			proxy :new Ext.data.MemoryProxy(data),
			reader :new Ext.data.ArrayReader( {}, dataId)
		});
		ds.load();
		grid.reconfigure(ds, cm);

	}

}
function troggleDataR(tData) {
	if (InputNodesR.length == tData.length) {
		for ( var i = 0; i < tData.length - 1; i++) {
			if (InputNodesR[i].id != "InputNodesR") {
				if (!!tData[i]) {
					InputNodesR[i].EnName = tData[i];
					InputNodesR[i].innerHTML = tData[i];
				}
			} else {
				if (!!tData[i]) {
					var strArray = tData[i].split(";");
					j = 0;
					var firstNode = InputNodesR[i].firstChild;
					while (firstNode) {
						if (firstNode.id == "Input") {
							if (!!strArray[j]) {
								firstNode.EnName = strArray[j];
								firstNode.innerHTML = strArray[j];
							} else {
								firstNode.EnName = "";
								firstNode.innerHTML = ("请输入一个值");
							}
							j++;
						}
						firstNode = firstNode.nextSibling;
					}

				}
			}
		}
		InputNodesR[InputNodesR.length - 1].value = tData[tData.length - 1];
	} else {
		Dialog.alert(("获取的数据与要显示的数据不能匹配，请联系系统管理员"));
	}
}
function dataToLogicR() {
	getInputNodesR();
	var tData = getSelectedDatas();
	troggleDataR(tData);
}

function RowToLogicR(sm, rowIndex, r) {
	if (!InputNodeEnabledR) {
		return;
	}
	getInputNodesR();
	var len = dataId.length - 1;
	var da = null;
	if (InputNodesR.length == len) {
		for ( var i = 0; i < len - 1; i++) {
			da = r.get(dataId[i].name);
			singleDataToLogicR(da, i);
		}
		InputNodesR[len - 1].value = r.get(dataId[len - 1].name);
	}
}
function CellToLogicR(e) {
	if (!InputNodeEnabledR) {
		return;
	}

	var nowValue = e.value;
	var orgValue = e.originalValue;
	if (nowValue != orgValue) {
		var col = e.column - 2;
		getInputNodesR();
		if (col == InputNodesR.length) {
			return;
		}
		if (col != InputNodesR.length - 1) {
			singleDataToLogicR(nowValue, col);
		} else {
			InputNodesR[InputNodesR.length - 1].value = nowValue;
		}
	}
}

function InitLogicR(e) {
	if (!InputNodeEnabledR) {
		return;
	}
	var r = e.record;
	getInputNodesR();
	var len = dataId.length - 1;
	var da = null;
	if (InputNodesR.length == len) {
		for ( var i = 0; i < len - 1; i++) {
			da = r.get(dataId[i].name);
			singleDataToLogicR(da, i);
		}
		InputNodesR[len - 1].value = r.get(dataId[len - 1].name);
	}
}

function singleDataToLogicR(da, i) {
	if (InputNodesR[i].id != "InputNodes") {

		InputNodesR[i].EnName = da;
		if (da != "" && da != undefined && da != null) {
			if (ColumnDataTypeArrayR[i] == "Date") {
				var dt = new Date(da);
				da = dt.format('Y-m-d H:i:s');
			}
			InputNodesR[i].innerHTML = da;
			InputNodesR[i].ChName = da;
			InputNodesR[i].EnName = da;
		} else {
			InputNodesR[i].innerHTML = ("请输入一个值");
		}
	} else {
		if (da != "" && da != undefined && da != null) {
			var strArray = da.split(";");
			var len = strArray.length;
			j = 0;
			var firstNode = InputNodesR[i].firstChild;
			while (firstNode) {
				if (j >= len) {
					while (firstNode.nextSibling.id == "Input"
							|| firstNode.nextSibling.id == "comma") {
						InputNodesR[i].removeChild(firstNode.nextSibling);
					}
				} else if (firstNode.id == "Input") {
					j++;
					if (j == len) {
						continue;
					}

				}
				if (firstNode.id == "AddInput") {
					break;
				}
				firstNode = firstNode.nextSibling;
			}
			if (j < len) {
				for (; j < len; j++) {
					var spanNode = createOriginSpanNodeR("comma");
					InputNodesR[i].insertBefore(spanNode, firstNode);
					spanNode = createOriginSpanNodeR("Input");
					InputNodesR[i].insertBefore(spanNode, firstNode);
				}
			}
			j = 0;
			firstNode = InputNodesR[i].firstChild;
			while (firstNode) {
				if (firstNode.id == "Input") {
					firstNode.EnName = strArray[j];
					if (!!strArray[j]) {
						firstNode.innerHTML = strArray[j];
					} else {
						firstNode.innerHTML = ("请输入一个值");
					}
					j++;
				}
				firstNode = firstNode.nextSibling;
			}
		} else {
			var firstNode = InputNodesR[i].firstChild;

			while (firstNode) {
				if (firstNode.id == "Input") {
					firstNode.EnName = "";
					firstNode.innerHTML = ("请输入一个值");
					break;
				} else {
					firstNode = firstNode.nextSibling;
				}
			}
			while (firstNode.nextSibling.id != "AddInput") {
				InputNodesR[i].removeChild(firstNode.nextSibling);
			}
		}
	}
}

function showButtonsR() {
	setButtonsStateR('Enable');
}
function hideButtonsR() {
	setButtonsStateR('Disable');
}
function setButtonsStateR(displayType) {
	// 获取规则区域的节点
	var ruleNodes = document.getElementById('ResultZone');
	// 获取规则的第一个条件
	var ruleNode = ruleNodes.firstChild;
	// 获取规则的最后一个条件
	var endNode = ruleNodes.lastChild;

	var buttonNode = null;
	if (displayType == 'Enable') {
		state = '';
	} else {
		state = 'none';
	}
	while (ruleNode != endNode) {
		buttonNode = ruleNode.lastChild;
		buttonNode1 = ruleNode.firstChild;
		if (buttonNode.id == "AddButton" || buttonNode.id == "DelButton") {
			//buttonNode.disable = state;
			buttonNode.style.display = state;
		}
		if(buttonNode1!=null&&buttonNode1.id == "Link"){
			var brNode = document.createElement("br");
			brNode.clear = "left";
			ruleNode.insertBefore(brNode,buttonNode1);
		}
		ruleNode = ruleNode.nextSibling;
	}
	//endNode.disable = state;
	endNode.style.display = state;
}

function convertXMLToRuleR(xmlFile) {
	try {
		var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");

		xmlDoc.async = false;
		xmlDoc.loadXML(xmlFile);

	} catch (e) {
		Dialog.alert(("您的浏览器不支持xml文件读取,于是本页面禁止您的操作,推荐使用IE5.0以上可以解决此问题!"));
		return;
	}
	if (xmlDoc == null) {
		Dialog.alert(("您的浏览器不支持xml文件读取,于是本页面禁止您的操作,推荐使用IE5.0以上可以解决此问题!"));
		return;
	}
	// 创建条件DIV区域
	var RuleZone = document.createElement("div");
	RuleZone.setAttribute('id', 'RuleZone');
	var IfNode = document.getElementById("Result");
	IfNode.parentNode.insertBefore(RuleZone, IfNode.nextSibling);
	// 创建一条规则
	var ruleDiv = null;
	var ruleNode = xmlDoc.getElementsByTagName("Condition");
	var ruleMetaNodes = null;
	var ruleMetaNode = null;
	var spanNode = null;
	/* var disCol = new Array(); */
	var DTHeader = new Array();
	ColumnHeadArrayR.length = 0;
	ColumnDataTypeArrayR.length = 0;
	rowCountR = 0;
	for ( var i = 0, len = ruleNode.length; i < len; i++) {
		ruleDiv = document.createElement("div");
		try {
			ruleMetaNodes = ruleNode[i].childNodes;
			DTHeader.length = 0;
			for ( var j = 0; j < ruleMetaNodes.length; j++) {
				ruleMetaNode = ruleMetaNodes[j];
				if (ruleMetaNode.getAttribute('id') == "Link") {
					DTHeader.length = 0;
				} else {
					DTHeader[DTHeader.length] = ruleMetaNode;

					if (ruleMetaNode.getAttribute('id') == "Input"
							|| ruleMetaNode.getAttribute('id') == "InputNodes") {

						composeDTHeaderR(DTHeader);
					}
				}
				spanNode = createIntegritySpanNodeR(ruleMetaNode);
				ruleDiv.appendChild(spanNode);

				spanNode = createOriginSpanNodeR("Spacer");
				ruleDiv.appendChild(spanNode);
			}
			spanNode = createDeleteButtonNodeR();
			ruleDiv.appendChild(spanNode);
		} catch (e) {
			Dialog.alert(("创建节点时出错！"));
		}
		RuleResultZoneendChild(ruleDiv);
		rowCountR++;
	}
//	spanNode = createAddLineButton();
//	RuleZone.appendChild(spanNode);
//
//	ColumnHeadArrayR[ColumnHeadArrayR.length] = ("自核不通过");
//	ColumnDataTypeArrayR[ColumnDataTypeArrayR.length] = "String";
//
//	ColumnHeadArrayR[ColumnHeadArrayR.length] = ("M0000072410");
//	ColumnDataTypeArrayR[ColumnDataTypeArrayR.length] = "String";
}

function createIntegritySpanNodeR(xmlNode) {
	var nodeId = xmlNode.getAttribute("id");
	var reSpanNode;
	var paraArray = new Array();

	if (nodeId == "BOM") {
		paraArray = prepareParaArrayR(xmlNode, BOMIndex);
		reSpanNode = createSpanNodeR(paraArray, false);
	} else if (nodeId == "BOMItem") {
		paraArray = prepareParaArrayR(xmlNode, BOMItemIndex);
		reSpanNode = createSpanNodeR(paraArray, false);
	} else if (nodeId == "Operator") {
		paraArray = prepareParaArrayR(xmlNode, OperatorIndex);
		reSpanNode = createSpanNodeR(paraArray, false);
	} else if (nodeId == "Link") {
		paraArray = prepareParaArrayR(xmlNode, LinkIndex);
		reSpanNode = createSpanNodeR(paraArray, false);
	} else if (nodeId == "Left_Paren") {
		paraArray = prepareParaArrayR(xmlNode, ParenIndex);
		reSpanNode = createSpanNodeR(paraArray, false);
	} else if (nodeId == "Right_Paren") {
		paraArray = prepareParaArrayR(xmlNode, ParenIndex);
		reSpanNode = createSpanNodeR(paraArray, false);
	} else if (nodeId == "Input") {
		paraArray = prepareParaArrayR(xmlNode, InputIndex);
		reSpanNode = createSpanNodeR(paraArray, false);
	} else if (nodeId == "InputNodes") {
		reSpanNode = createInputNodes();
	}
	return reSpanNode;
}
function prepareParaArrayR(xmlNode, IndexArray) {
	var reArray = new Array();

	var i = 0;
	var len = 0;
	var att = '';
	for (i = 0, len = IndexArray.length; i < len; i++) {

		att = xmlNode.getAttribute(IndexArray[i]);
		if (IndexArray[i] == "Source") {
			att = att.replace(new RegExp("''", "gm"), "'");
		}
		reArray[i] = {
			attribute :IndexArray[i],
			value :att
		};
	}
	return reArray;
}

function composeDTHeaderR(DTHeader) {

	var reArray = new Array();

	var head = '';
	var dataType = '';

	var i = 0;
	for (i = 0; i < DTHeader.length - 1; i++) {
		head += DTHeader[i].getAttribute("ChName");
	}
	ColumnHeadArray[ColumnHeadArray.length] = head;
	ColumnDataTypeArray[ColumnDataTypeArray.length] = DTHeader[i - 1].getAttribute("MatchType");
}

function initDataArrayR() {
	for ( var i = 0; i < 10; i++) {
		data[i] = [];
	}
}
