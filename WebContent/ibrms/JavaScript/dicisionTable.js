var needD = true;
var length = 2;
var fm = Ext.form;
var sm = new Ext.grid.CheckboxSelectionModel();
var rowId = new Ext.grid.RowNumberer();
var strJson = [ rowId, sm ];
var dataId = new Array();
var data = [ [], [] ];
var cm = null;
var grid = null;
var ds = null;
var rowDisable=false;
/*var dataType = new Array();

var modDisType = new Array();*/

function displayDicTable(destination,jsonData) {
	// 隐藏相关按钮
	length = 2;
	strJson = [ rowId, sm ];
	needD = true;
	dataId.length=0;
	getBaseBomItems();
	var width=40;
	
	for ( var i = 0; i < ColumnHeadArray.length; i++) {
		var newJson = null;
		width+=200;
		if (ColumnDataTypeArray[i]== "String") {
			var editor;
			if(BaseBOMItemSourceArray[i]!="")
			{
				var store = new Ext.data.JsonStore({
		            fields: ['code', 'name'],
		            root: "rows",
		            url   : "getComboxData.jsp?sql="+BaseBOMItemSourceArray[i]
		           });
				if(!ColumnMultipleArray[i])
				{
					editor= new Ext.form.ComboBox({
				    	  store: store,
				    	  width:100,
				    	  valueField : "name",
				    	  displayField:'name',
				     	  typeAhead: true,
				     	  mode: 'remote',
				          lazyInit: true,
				          forceSelection: true,
				          triggerAction: 'all',
				          emptyText:'',
				          listClass: 'x-combo-list-small'
				       });
				}
				else
				{
					editor= new Ext.form.MultiComboBox({
						width : 200,
						store : store,
						valueField : "name",
						displayField : "name",
						labelSeparator : '：',
						displaySeparator : ';',
						valueSeparator : ';',
						maxHeight : 100,
						mode : 'remote',
						value : '1,2',
						forceSelection : true,
						hiddenName : 'test',
						editable : true,
						triggerAction : 'all',
						allowBlank : false
					});
				}
			}
			else 
			{
				editor=new fm.TextField({
					   blankText:"",
					   allowBlank:false
				   });
			}
			newJson = {
				header :ColumnHeadArray[i],
				dataIndex :TableColumnNameArray[i],
				width :200,
				align :'center',
				editor :editor
			};
		} else if (ColumnDataTypeArray[i]== "Date") {
			newJson = {
				header :ColumnHeadArray[i],
				dataIndex :TableColumnNameArray[i],
				width :200,
				align :'center',
				renderer :Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
				editor :new fm.DateField( {
					format :'Y-m-d H:i:s'
				})
			};
		} else if (ColumnDataTypeArray[i] == "Number" || ColumnDataTypeArray[i]== "INT") {
			newJson = {
				header :ColumnHeadArray[i],
				dataIndex :TableColumnNameArray[i],
				width :200,
				allowDecimals :false,
				align :'center',
				editor :new fm.NumberField( {
					decimalPrecision :6,
                    allowBlank :true,
					allowNegative :false,
					style :'text-align:left'
				})
			};
		}
		strJson[strJson.length] = newJson;
		dataId[i] = {
			name :TableColumnNameArray[i]
		};
	}
	cm = new Ext.grid.ColumnModel(strJson);

	var Plant = Ext.data.Record.create(dataId);
	if(destination=="init")
	{
		var resultReader = new Ext.data.JsonReader({
		                                        totalProperty: "totalProperty",
		                                        root: "rows",
		                                        id  : "id"
		                                    }, Plant);
	   
		   ds = new Ext.data.Store({
			    reader: resultReader,
		        proxy:  new Ext.data.MemoryProxy(jsonData)
		    });
	}
	else
	{		
		ds=new Ext.data.Store( {
			proxy :new Ext.data.MemoryProxy(data),
			reader :new Ext.data.ArrayReader( {}, dataId)
		});
	}
	grid = new Ext.grid.EditorGridPanel( {
		ds :ds,
		cm :cm,
		sm :sm,
		autoHeight :true,
		autoScroll :true,
		stripeRows :true,
		width :width,
		clicksToEdit:1,
		tbar : [ {
			text :("增加一行"),
			handler : function()
		{
			if(rowDisable)
			{
				return;
			}
			var recordId={};
			
			var p = new Plant(recordId);
			
			for(var j=0;j<TableColumnNameArray.length;j++)
			{
				p.set(TableColumnNameArray[j],"");
			}
			grid.stopEditing();
			ds.insert(0, p);
			grid.reconfigure(ds, cm);
			grid.startEditing(0, 2);
		}
		}, {
			text :("删除选中行"),
			handler : function() {
			if(rowDisable)
			{
				return;
			}
			    if(!confirm(("确认要删除选中的数据吗？")))
			    {
			    	return ;
			    }
				grid.stopEditing();
				var selctions = sm.getSelections();
				for ( var i = 0; i < selctions.length; i++) {
					ds.remove(selctions[i]);
				}
				grid.reconfigure(ds, cm);
			}
		} ],
		 bbar: new Ext.PagingToolbar({
		        pageSize: 10,
		        store: ds,
		        displayInfo: true,
		        displayMsg: ("显示第 {0} 条到 {1} 条记录，一共 {2} 条"),
		        emptyMsg: ("没有记录")
		    })

	});
	ds.load();

	sm.addListener("rowselect", RowToLogic);
	//grid.addListener("afteredit", CellToLogic);
	grid.addListener("beforeedit", InitLogic);

	var gridNode = document.getElementById('grid-example');
	while (gridNode.firstChild)
		gridNode.removeChild(gridNode.firstChild);

	grid.render('grid-example');
	
    checkNodesState(flag);
    if(flag==0)
    {
    	disableEditable();
    }
}

function disableEditable()
{
	try{
		for(var i=2;i<cm.getColumnCount();i++)
		{
		    cm.setEditable(i,false);
		}
	}catch(e)
	{
        alert("决策表不可编辑属性初始化出错，请不要再决策表中编辑");
		}
	

	}


function checkNodesState(flag)
{
   switch(flag)
   {
   case 0:
   {
	   disableInputNodes();
	   disableInputNodesR();
	   disableSpanNodes();
	   disableSpanNodesR();
	   hideButtons();
	   hideButtonsR();
	   break;
   }
   case 1:
   {
	   disableSpanNodes();
	   disableSpanNodesR();
	   enableInputNodes();
	   disableSpanNodesR();
	   hideButtons();
	   hideButtonsR();
	   break;
   }
   case 2:
   {
	   enableSpanNodes();
	   enableSpanNodesR();
	   disableInputNodes();
	   disableInputNodesR();
	   showButtons();
	   showButtonsR();
	   break;
   }
   case 4:
   {
	   disableSpanNodes();
	   enableInputNodes();
	   disableSpanNodesR();
	   enableInputNodesR();
	   hideButtons();
	   hideButtonsR();
	   break;
   }
   }
}
function prepareStore(destination)
{
	var reStore =null;
	if(destination=="init")
	{
		reStore= new Ext.data.Store( {
			proxy :new Ext.data.MemoryProxy(data),
			reader :new Ext.data.ArrayReader( {}, dataId)
		});
		
	}else
	{
		reStore= new Ext.data.Store( {
			proxy :new Ext.data.MemoryProxy(data),
			reader :new Ext.data.ArrayReader( {}, dataId)
		});
		
	}
	
}

function getSelectedDatas() {
	var dataArray = sm.getSelections();
	var reData = new Array();
	if (dataArray.length > 1) {
		alert(("您选择的行数大于1，请选择一行！"));
	} else if (dataArray.length == 0) {
		alert(("您还没有选择行，请选择一行！"));

	} else {
		for ( var i = 0; i < dataId.length; i++) {
			var tmp = dataArray[0].get(dataId[i].name);
			if (dataType[i] == 'Date') {
				var dt = new Date(tmp);
				reData[i] = dt.format('Y-m-d H:i:s');
			} else {
				reData[i] = tmp;
			}
		}

	}
	return reData;
}

function getData() {
	getBaseBomItems();
	var data = new Array();
	if (needD) {
		var store = grid.getStore();
		var record = null;
		var message = '';				
		
		for ( var i = 0, length = store.getCount(); i < length; i++) {
			var da = new Array();
			record = store.getAt(i);
			var Integrity = true;
			var rowNull = true;
			for (j = 0, len = dataId.length; j < len; j++) {
				var dad = record.get(dataId[j].name);
				//if(j==len-1)
				//{
				//	 if(Integrity)
				//	 {
				//		 da[j]=dad.substring(0,dad.indexOf("-"));
				//		 data[data.length] = da;
				//	 }
					 
				//}
				// else
				// {
					 var notNull=false;
					 if(dad!=undefined&&dad!=null)
					 {
						 var val=new String(dad);
						 if(val!="")
						 {
							 notNull=true;
						 }
					 }
					 
					 if (notNull) {
						 
							if (BaseColumnArray[j]) {
								var disData = new Array();
								disData=dad.split(";");
								if(ColumnMultipleArray[j])
								{
									da[j]=";";
								}
								else
								{
									da[j]="";
								}
								
								for ( var k = 0; k < disData.length; k++) {
									var obStr=disData[k]
									var index=obStr.indexOf("-");
											da[j] +=  obStr.substring(0,index);
											if(ColumnMultipleArray[j])
											{
												da[j] +=";" ;
											}
								}
							} else {
								if (ColumnDataTypeArray[j] == 'Date') {
									var dt = new Date(dad);
									da[j] = dt.format('Y-m-d H:i:s');
								} else {
									da[j] = dad;
								}
							}
							rowNull = false;
						} else {
								Integrity = false;
						}
					//}
			 }
			if(da==null||da=="")
			{
				//alert(1111);
			}
			else
			{
				data[data.length] = da;
			}
			 
			if (Integrity) {
				
			} else if (!rowNull) {	
				message += ("\n行") + eval(i + 1) + ("的数据 不完整！");
			}
		  }
		if (!!message) {
			var discision = confirm(message + ("\n如果提交将丢失以上数据"));
			if (!discision) {
				return false;
			}
		}
		if (data.length == 0) {
			alert("您在决策表中还没有输入数据,请继续完成!");
			return false;
		}
		return data;
  }else
  {
	  alert(("请先生成决策表！"));
	  return false;
  }
	
}

function dataArrayToJson(dataArray)
{
   	var rowCount=dataArray.length;
   	var columnCount=dataArray[0].length;
   	
   	var json=[];
   	
   	for(var i=0;i<rowCount;i++)
   	{
   		json[i]={};
   		for(var j=0;j<columnCount;j++)
   		{
   			json[i][TableColumnNameArray[j]]=dataArray[i][j];
   		}
   	}
   	return json;
}

